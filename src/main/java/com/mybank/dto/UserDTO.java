package com.mybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mybank.entity.Address;
import com.mybank.entity.CreditCard;
import com.mybank.entity.date.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull(message = "Login is required")
    private String login;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Surname is required")
    private String surname;

    @NotNull(message = "Birthday is required")
    private Date birthday;

    @NotNull(message = "Address is required")
    private Address address;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Phone is required")
    private String phone;

    private List<CreditCard> cardList; //todo ask?

    @NotNull(message = "Password id required")
    private String password;

    private boolean blocked; // todo reason??
}
