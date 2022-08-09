package com.Springboot.example.repository;


import com.Springboot.example.model.Vue_Globale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RslRepository extends JpaRepository<Vue_Globale, Integer> {

}
