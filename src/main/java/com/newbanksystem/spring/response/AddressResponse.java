package com.newbanksystem.spring.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponse implements Serializable {

    /* Mapeando os Dados recebidos do VIACEP
    *   Informando apenas os dados desejados da API Consumida */

    private String cep;
    private String localidade;
    private String uf;
    private String bairro;
    private String logradouro;

}
