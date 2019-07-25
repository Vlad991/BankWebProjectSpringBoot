package com.mybank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    private Date birthday;

    private Address address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    private List<CreditCard> cardList;
}
