package com.Springboot.example.repository;

import com.Springboot.example.model.Rsl_test_sys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Rsl_test_sysRepository extends JpaRepository<Rsl_test_sys, Integer> {

}
