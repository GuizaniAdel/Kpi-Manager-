package com.Springboot.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Springboot.example.model.Database1;
import com.Springboot.example.model.Vue_Globale;
@Repository
public interface Database1Repository extends JpaRepository< Database1, Integer> {

	void save(Vue_Globale r);

	
}
