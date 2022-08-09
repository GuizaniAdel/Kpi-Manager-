package com.Springboot.example.service;


import com.Springboot.example.model.Requete;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.model.Database;
import com.Springboot.example.repository.RequeteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DbServiceImpl implements DbService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DbRepository dbrepository ;
	@Autowired
	private RequeteRepository requeteRepository;
	/**
	 * verifier l'existance de database en faisant la connexion et puis l'ajouter et retourne true
	 * sinon il va retourner false
	 * @return
	 */
	@Override
	public String createOrUpdateDb(Database db) {

		 if (db.getSystem().equals("SqlServer"))
		try {
          
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://"+db.getUrl()+":"+db.getPort()+";databaseName="+db.getName()
            ,db.getUsername(),db.getPassword());
             dbrepository.save(toEntity(db));

        } catch (Exception e) {

            System.out.println("Failed to create JDBC db connection " + e.toString() + e.getMessage());
			return "Failed to create JDBC db connection :"+e.getMessage();
        }
		else if (db.getSystem().equals("Postgres")) {
		try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://"+db.getUrl()+":" + db.getPort() + "/"+db.getName()
                    ,db.getUsername(),db.getPassword());
            dbrepository.save(toEntity(db));

        } catch (Exception e) {

            System.out.println("Failed to create JDBC db connection " + e.toString() + e.getMessage());
			return "Failed to create JDBC db connection :"+e.getMessage();
        }
		}
		else if (db.getSystem().equals("Oracle"))
			try {
		          
	            Class.forName("oracle.jdbc.OracleDriver");
	            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+db.getUrl()+":"+db.getPort()+"/"+db.getName()
	            ,db.getUsername(),db.getPassword());
	             dbrepository.save(toEntity(db));

	        } catch (Exception e) {

	            System.out.println("Failed to create JDBC db connection "+ e.getMessage());
				return "Failed to create JDBC db connection :"+e.getMessage();
	        }
		else {
			System.out.println(db);
			System.out.println("no connexions");
			return "no driver";
		}
		return "done";

	}

	private Database toEntity(Database db) {
		  Database entity = new Database();
		    entity.setId(db.getId());
	        entity.setName(db.getName());
	        entity.setUsername(db.getUsername());
	        entity.setPassword(db.getPassword());
	        entity.setPort(db.getPort());
	        entity.setUrl(db.getUrl());
	        entity.setSystem(db.getSystem());
	        return entity;
	}

	@Override
	public List<Database> getAllDb() {
		  List<Database> list = dbrepository.findAll();
         
          return list;
	}

	@Override
	public void deleteDatabase(Long id) {
		Database db= dbrepository.findDatabaseById(id);
		List<Requete> rs= db.getRequetes();
		for(Requete r:rs){
			r.getDims().removeAll(r.getDims());
			em.remove(r);

		}
		em.remove(db);

	}
	@SuppressWarnings("unchecked")
	@Override
    public List<Database> getOracleDb(){
		List<Database> listOracles ;
		
	   listOracles=  em.createQuery("select d from Database d where d.system='Oracle'").getResultList();
	   System.out.println(listOracles);
	   return listOracles;
   }
	@SuppressWarnings("unchecked")
	@Override
    public List<Database> getSqlServerDb(){
		List<Database> listOracles ;
		
	   listOracles=  em.createQuery("select d from Database d where d.system='SqlServer'").getResultList();
	   System.out.println(listOracles);
	   return listOracles;
   }
	@SuppressWarnings("unchecked")
	@Override
    public List<Database> getPostgresDb(){
		List<Database> listOracles ;
		
	   listOracles=  em.createQuery("select d from Database d where d.system='Postgres'").getResultList();
	   System.out.println(listOracles);
	   return listOracles;
   }

	@Override
	public String VerifyConnexion(Database db) {


		switch (db.getSystem()) {
			case "SqlServer":
				try {

					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					Connection con = DriverManager.getConnection("jdbc:sqlserver://" + db.getUrl() + ":" + db.getPort() + ";databaseName=" + db.getName()
							, db.getUsername(), db.getPassword());

				} catch (Exception e) {

					System.out.println("Failed to create JDBC db connection " + e.toString() + e.getMessage());
					return "Failed to create JDBC db connection :" + e.getMessage();
				}
				break;
			case "Postgres":
				try {
					Class.forName("org.postgresql.Driver");
					Connection conn = DriverManager.getConnection("jdbc:postgresql://" + db.getUrl() + ":" + db.getPort() + "/" + db.getName()
							, db.getUsername(), db.getPassword());

				} catch (Exception e) {

					System.out.println("Failed to create JDBC db connection " + e.toString() + e.getMessage());
					return "Failed to create JDBC db connection :" + e.getMessage();
				}
				break;
			case "Oracle":
				try {

					Class.forName("oracle.jdbc.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + db.getUrl() + ":" + db.getPort() + "/" + db.getName()
							, db.getUsername(), db.getPassword());

				} catch (Exception e) {

					System.out.println("Failed to create JDBC db connection " + e.getMessage());
					return "Failed to create JDBC db connection :" + e.getMessage();
				}
				break;
			default:
				System.out.println(db);
				System.out.println("no connexions");
				return "no driver";
		}
		return "Connexion établie";

	}



	public static Connection getConnection(){  
	    Connection con=null;  
	    try{  
	        Class.forName("org.postgresql.Driver");  
	        con=DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4","postgres","root");
	    }catch(Exception e){System.out.println(e);}  
	    return con;  
	}

	public static Database getRecordById(Long id){  
		Database u=null;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from database where id=?");  
	        ps.setLong(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	            u=new Database();  
	            u.setId(rs.getInt("id"));  
	            u.setName(rs.getString("name"));  
	            u.setPassword(rs.getString("password"));  
	            u.setPort(rs.getInt("port"));  
	            u.setUsername(rs.getString("username"));  
	            u.setSystem(rs.getString("system"));  
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return u;  
	}  
	} 

