package com.example.customer_service.service;

import com.example.customer_service.dto.CustomerRequestDTO;
import com.example.customer_service.dto.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO getCustomerById(Long id);
    List<CustomerResponseDTO> getAllCustomers();
    Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy);
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerResponseDTO);
    void deleteCustomer(Long id);
}
