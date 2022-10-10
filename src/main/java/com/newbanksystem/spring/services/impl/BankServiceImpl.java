package com.newbanksystem.spring.services.impl;

import com.newbanksystem.spring.cache.model.WithdrawLimit;
import com.newbanksystem.spring.cache.repository.WithdrawLimitRepository;
import com.newbanksystem.spring.client.ViaCepClient;
import com.newbanksystem.spring.exceptions.AccountAlreadyExistException;
import com.newbanksystem.spring.exceptions.AddressNotFoundException;
import com.newbanksystem.spring.exceptions.AccountValidationException;
import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.models.Address;
import com.newbanksystem.spring.models.Client;
import com.newbanksystem.spring.repositories.AccountRepository;
import com.newbanksystem.spring.repositories.AddressRepository;
import com.newbanksystem.spring.repositories.ClientRepository;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.request.TransferRequest;
import com.newbanksystem.spring.response.AddressResponse;
import com.newbanksystem.spring.services.BankService;
import com.newbanksystem.spring.services.TokenService;
import com.newbanksystem.spring.utils.InvalidDocumentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.newbanksystem.spring.utils.DateUtil.stringToLocalDate;
import static com.newbanksystem.spring.utils.RandomNumberUtil.generateRandomNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService { /* Onde será implementado a Lógica de negócio */

    @Value("${app.withdraw.daily-limit}")
    private String withdrawDailyLimit;

    private final ViaCepClient viacepClient;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final WithdrawLimitRepository withdrawLimitRepository;
    private final TokenService tokenService;

    @Override
    public Account createAccount(AccountRequest request) { /* Onde será construido a implementação */
        log.info("BankServiceImpl.createAccount init - request={}", request);

        InvalidDocumentUtil.checkCpf(request.getDocument());

        accountRepository.findFirstByClientDocumentOrClientEmail(request.getDocument(), request.getEmail())
                .ifPresent(account -> {
                    String accountMessageError = "Conta já existente para o CPF e/ou Email informado - Numero da conta: ";
                    throw new AccountAlreadyExistException(accountMessageError + account.getNumber());
                });

        AddressResponse addressResponse = getAddress(request)
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado"));

        Address address = Address
                .builder()
                .cep(request.getCep())
                .address(addressResponse.getLogradouro())
                .city(addressResponse.getLocalidade())
                .state(addressResponse.getUf())
                .number(request.getNumber())
                .district(addressResponse.getBairro())
                .secondAddress(request.getSecondAddress())
                .build();
        Address savedAddress = addressRepository.save(address);

        Client client = Client
                .builder()
                .email(request.getEmail())
                .address(savedAddress)
                .name(request.getName())
                .document(request.getDocument())
                .phone(Long.parseLong(request.getPhone()))
                .birthdate(stringToLocalDate(request.getBirthdate(), "dd/MM/yyyy"))
                .build();
        Client savedClient = clientRepository.save(client);

        Account account = Account
                .builder()
                .number(generateAccountNumber())
                .client(savedClient)
                .balance(BigDecimal.valueOf(0))
                .registration(LocalDateTime.now())
                .password(request.getPassword())
                .build();
        Account savedAccount = accountRepository.save(account);

        log.info("BankServiceImpl.createAccount end - account={}", account);

        return account;
    }

    @Override
    public void deposit(Integer accountNumber, BigDecimal value) {

        Account account = accountRepository             // Buscando conta existente no DB
                .findFirstByNumber(accountNumber)
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        if (Objects.nonNull(account.getDeactivation())) // Verificando se a conta esta ativa
            throw new AccountValidationException("Conta informada desativada");

        BigDecimal total = account.getBalance().add(value);
        account.setBalance(total);

        accountRepository.save(account);
    }

    @Override
    public void withdraw(Integer accountNumber, BigDecimal value, String token) {

        tokenService.validateToken(token, accountNumber);

        checkWithdrawDailyLimit(accountNumber, value);  // Verifica a quantidade de valor sacado

        Account account = accountRepository             // Buscando conta existente no DB
                .findFirstByNumber(accountNumber)
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        if (Objects.nonNull(account.getDeactivation())) // Verificando se a conta esta ativa
            throw new AccountValidationException("Conta informada desativada");

        if (account.getBalance().compareTo(value) < 0)  // Compara o saldo da conta com o valor a ser sacado
            throw new AccountValidationException("Saldo insuficiente");

        BigDecimal newValue = account.getBalance().subtract(value); // Subtrai o valor da conta
        account.setBalance(newValue);

        accountRepository.save(account);    // Salva os dados no DB da conta

        addWithdrawToLimitControl(accountNumber, value);    // Salva o Valor de saque no Cache
    }

    @Override
    public void transfer(TransferRequest transferRequest, String token) {

        checkWithdrawDailyLimit(transferRequest.getFromAccount(), transferRequest.getAmmount());

        // Verificando conta de Origem

        Account from = accountRepository
                .findFirstByNumber(transferRequest.getFromAccount())
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        if (Objects.nonNull(from.getDeactivation()))
            throw new AccountValidationException("Conta informada desativada");

        // Verificando conta de Destino

        Account to = accountRepository
                .findFirstByNumber(transferRequest.getToAccount())
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        if (Objects.nonNull(to.getDeactivation()))
            throw new AccountValidationException("Conta informada desativada");

        // Verificando se há saldo na conta Origem

        if (from.getBalance().compareTo(transferRequest.getAmmount()) < 0)
            throw new AccountValidationException("Saldo insuficiente para operação");

        // Subtraindo valor da conta de Origem

        BigDecimal newValue = from.getBalance().subtract(transferRequest.getAmmount());
        from.setBalance(newValue);

        // Inserindo o valor na conta Destino

        BigDecimal newBalance = to.getBalance().add(transferRequest.getAmmount());
        to.setBalance(newBalance);

        accountRepository.save(from);
        accountRepository.save(to);

        // Salva o Valor de saque no Cache

        addWithdrawToLimitControl(transferRequest.getFromAccount(), transferRequest.getAmmount());
    }

    private void addWithdrawToLimitControl(Integer accountNumber, BigDecimal value) {

        WithdrawLimit limit = withdrawLimitRepository
                .findById(accountNumber)
                .orElse(new WithdrawLimit().accountNumber(accountNumber));

        limit.addWithdraws(value);

        withdrawLimitRepository.save(limit);
    }

    private void checkWithdrawDailyLimit(Integer accountNumber, BigDecimal value) {

        withdrawLimitRepository.findById(accountNumber).ifPresent(withdrawLimit -> {

            // Soma as retiradas
            BigDecimal totalPartial = withdrawLimit.getWithdraws()
                    .stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Soma o valor a ser sacado, com o total que ja foi sacado
            BigDecimal total = totalPartial.add(value);

            BigDecimal dailyLimit = new BigDecimal(this.withdrawDailyLimit);
            if (total.compareTo(dailyLimit) > 0) {
                throw new AccountValidationException("Limite de saque excedido");
            }
        });
    }

    private Integer generateAccountNumber() {   // Gera um numero Randomico

        Integer number = generateRandomNumber();
        while (accountRepository.findFirstByNumber(number).isPresent()) {
            number = generateRandomNumber();
        }
        return number;
    }

    private Optional<AddressResponse> getAddress(AccountRequest request) { // Busca o CEP via API na ViaCep
        try {
            AddressResponse addressResponse = viacepClient.getAddressByCep(request.getCep());

            if (addressResponse.isErro()) {
                return Optional.empty();
            }

            return Optional.of(addressResponse);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}