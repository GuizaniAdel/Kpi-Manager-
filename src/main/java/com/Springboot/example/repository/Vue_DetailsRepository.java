package com.Springboot.example.repository;


import com.Springboot.example.model.Vue_Detaillé;
import com.Springboot.example.model.Vue_Globale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Vue_DetailsRepository extends JpaRepository<Vue_Detaillé, Integer> {

	void save(Vue_Globale r);



}
