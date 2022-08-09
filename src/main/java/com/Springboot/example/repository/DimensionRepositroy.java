package com.Springboot.example.repository;

import com.Springboot.example.model.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimensionRepositroy extends JpaRepository<Dimension, Long> {
}
