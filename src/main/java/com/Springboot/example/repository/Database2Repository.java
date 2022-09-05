package com.Springboot.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.Springboot.example.model.Database2;
import com.Springboot.example.model.Vue_Globale;
@Repository
public interface Database2Repository extends JpaRepository< Database2, Integer> {

	void save(Vue_Globale r);

	
}
