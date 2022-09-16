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
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne // Informando qual o tipo de relação da tabela
    @JoinColumn(name = "address_id", referencedColumnName = "id") // "name" é a FK, "reference..." é a Referencia
    private Address address;

    private String name;
    private String birthday;
    private Integer phone;
    private String email;
    private String document;

}