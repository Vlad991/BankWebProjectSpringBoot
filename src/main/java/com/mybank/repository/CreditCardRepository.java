package com.mybank.repository;

import com.mybank.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByNumber(String number);

    @Query("SELECT number FROM creditcards")
    List<String> getAllNumbers();
}
