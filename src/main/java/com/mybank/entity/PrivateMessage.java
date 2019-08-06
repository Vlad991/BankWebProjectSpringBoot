package com.mybank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class PrivateMessage {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "body", length = 255, nullable = false)
    private String message;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;
}
