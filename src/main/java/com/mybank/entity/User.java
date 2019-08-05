package com.mybank.entity;

import com.mybank.Address;
import com.mybank.entity.date.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "bank_users")
public class User {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Type(type = "com.mybank.entity.date.DateType")
    @Column(name = "birthday")
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    private List<CreditCard> cardList;
}
