package com.newbanksystem.spring.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer number;
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @Column(name = "registration_dt", nullable = false)
    private LocalDateTime registration;
    @Column(name = "deactivation_dt")
    private LocalDateTime deactivation;

    @Column(nullable = false, precision = 6) // É possivel fazer a identicação da coluna, dando uma seguranca a mais
    private String password;

}
