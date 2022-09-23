package com.Springboot.example.repository;

import com.Springboot.example.model.Kpi;
import com.Springboot.example.model.Rsl_test_sys;
import com.Springboot.example.model.Vue_Globale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Rsl_test_sysRepository extends JpaRepository<Rsl_test_sys, Integer> {

	void save(Vue_Globale r);
	
	
//	@Query("select p from rsl_test_sys p where p.id_kpi LIKE :?")
//	List <Rsl_test_sys>  findById_kpi (Long id_kpi) ;
	
	 

	}

