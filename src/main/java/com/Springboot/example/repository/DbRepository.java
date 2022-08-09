package com.Springboot.example.repository;


import com.Springboot.example.model.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbRepository extends JpaRepository<Database, Long> {

    Database findDatabaseByName(String name);

    Database findDatabaseById(long id);

	
}
