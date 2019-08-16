package com.mybank.entity;

import com.mybank.entity.carddate.CreditCardDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "creditcards")
public class CreditCard implements Serializable {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private String number; // todo validate 0000 0000 0000 0000 (validator)

    @Type(type = "com.mybank.entity.carddate.CreditCardDateType")
    @Column(name = "date", nullable = false)
    private CreditCardDate date;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client; // only client!!! todo

    @Column(name = "code", nullable = false)
    private int code;  // 000 todo card code type

    @Column(name = "sum")
    private int sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CreditCardStatus status;

    //private pin; todo
}
