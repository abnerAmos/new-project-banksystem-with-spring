package com.newbanksystem.spring.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/* Annotations Lombok */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

/* Annotations Hibernate */
@Entity
@Table(name = "address")
public class Address implements Serializable {

/*  Modelando uma Entidade com Hibernate JPA.
    Mapeando o Codigo Java para a Tabela(Entidade/Entity) com Hibernate (JPA/Spring Data) */

    @Id // Faz a identificacao de que a proxima "Variavel Membro de Classe" é uma Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Faz o AUTO_INCREMENT
    private Integer id;
    private String city;
    private String state; // Ao não informar @Column o nome da coluna na tabela, a Declaracao deve ter o mesmo nome da coluna.
    private String address;
    @Column(name = "house_number") /* Caso haja diferenca no nome da coluna do Database com do codigo, precisa informar
                                      qual o mesmo nome inserido com @Column */
    private String number;
    private String district;
    private String cep;
    private String secondAddress; /* O Hibernate associa o padrao do banco (snake_case) com o padrao do codigo (camelCase),
                                     nao havendo a necessidade de inserir a annotation @Column */
}
