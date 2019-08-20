package com.mybank.repository;

import com.mybank.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByCountryAndCityAndStreetAndPostcode(String country, String city, String street, int postcode);
}
