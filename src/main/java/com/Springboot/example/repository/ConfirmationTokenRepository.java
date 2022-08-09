package com.Springboot.example.repository;


import com.Springboot.example.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;


public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

	ConfirmationToken findByConfirmationToken(String confirmationToken);

}