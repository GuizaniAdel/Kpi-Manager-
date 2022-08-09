package com.Springboot.example.service;



import com.Springboot.example.model.Database;

import java.util.List;

public interface DbService {
	 public String createOrUpdateDb(Database database);
	 
     public List<Database> getAllDb();

     public void deleteDatabase(Long id);
     
     public List<Database> getOracleDb();
     
     public List<Database> getSqlServerDb();
     
     public List<Database> getPostgresDb();

     public String VerifyConnexion(Database db);
     
     
}
