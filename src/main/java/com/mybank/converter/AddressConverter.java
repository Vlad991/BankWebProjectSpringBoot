package com.mybank.converter;

import com.mybank.dto.AddressDTO;
import com.mybank.entity.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressConverter {
    public AddressDTO convertToDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setPostcode(address.getPostcode());
        return addressDTO;
    }

    public Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setPostcode(addressDTO.getPostcode());
        return address;
    }

    public List<AddressDTO> convertToListDto(List<Address> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
