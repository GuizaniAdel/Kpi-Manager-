package com.Springboot.example.repository;


import com.Springboot.example.model.Kpi;
import com.Springboot.example.model.Requete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequeteRepository extends JpaRepository<Requete, Long> {

	Requete findBykpi(Kpi kpi);


}
