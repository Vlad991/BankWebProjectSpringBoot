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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cards_seq")
    @SequenceGenerator(name="cards_seq", initialValue = 1414000000, allocationSize = 1) // todo ??????? 0000 00000000 0000
    private Long id;

    @Type(type = "com.mybank.entity.carddate.CreditCardDateType")
    @Column(name = "date")
    private CreditCardDate date;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client; // only client!!! todo

    @Column(name = "code", unique = true)
    private int code;  // 000 todo card code type

    @Column(name = "sum")
    private int sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CreditCardStatus status;

    //private pin; todo
}
