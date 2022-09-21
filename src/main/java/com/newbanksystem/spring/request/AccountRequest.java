package com.newbanksystem.spring.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountRequest implements Serializable {

        /*
        Request (Solicitar)
        DTO - Data Transfer Object
        Classe que recebe os dados externos (front (ou postman, caso seja um teste))
        Mapeamento de Objeto JSON
        */

    private String name;
    private String document;
    private String birthdate;
    private String phone;
    private String email;
    private String cep;
    private String number;
    private String district;
    private String secondAddress;
    private String password;
}
