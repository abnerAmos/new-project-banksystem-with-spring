package com.newbanksystem.spring.services.impl;

import com.newbanksystem.spring.client.ViaCepClient;
import com.newbanksystem.spring.exceptions.AccountAlreadyExistException;
import com.newbanksystem.spring.exceptions.AddressNotFoundException;
import com.newbanksystem.spring.exceptions.DocumentInvalidException;
import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.models.Address;
import com.newbanksystem.spring.models.Client;
import com.newbanksystem.spring.repositories.AccountRepository;
import com.newbanksystem.spring.repositories.AddressRepository;
import com.newbanksystem.spring.repositories.ClientRepository;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.response.AddressResponse;
import com.newbanksystem.spring.services.BankService;
import com.newbanksystem.spring.utils.InvalidDocumentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.newbanksystem.spring.utils.DateUtil.stringToLocalDate;
import static com.newbanksystem.spring.utils.RandomNumberUtil.generateRandomNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService { /* Onde será implementado a Lógica de negócio */

    private final ViaCepClient viacepClient;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;

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
                .password(Integer.valueOf(request.getPassword()))
                .build();
        Account savedAccount = accountRepository.save(account);

        log.info("BankServiceImpl.createAccount end - account={}", account);

        return account;
    }

    private Integer generateAccountNumber() {

        Integer number = generateRandomNumber();
        while (accountRepository.findFirstByNumber(number).isPresent()) {
            number = generateRandomNumber();
        }
        return number;
    }

    private Optional<AddressResponse> getAddress(AccountRequest request) {
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