package com.Springboot.example.service;

import com.Springboot.example.model.Rsl_test_sys;
import com.Springboot.example.model.Vue_Globale;
import com.Springboot.example.repository.Rsl_test_sysRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class Rsl_test_sysServiceImp implements Rsl_test_sysService {
	
	@PersistenceContext
	public EntityManager em;
	@Autowired
	private Rsl_test_sysRepository Rsl_test_sysrepository ;
	@Override
	public void createOrUpdateRsl_test_sys(Rsl_test_sys Rsl_test_sys) {
		Rsl_test_sysrepository.save(toEntitykpi(Rsl_test_sys));
		
	}
	
	private Rsl_test_sys toEntitykpi(Rsl_test_sys Rsl_test_sys) {
		Rsl_test_sys r = new Rsl_test_sys();
		    r.setIdKpi(Rsl_test_sys.getIdKpi());
		    r.setDate(Rsl_test_sys.getDate());
		    r.setSysteme(Rsl_test_sys.getSysteme());
		    r.setVal_kpi(Rsl_test_sys.getVal_kpi());
		    r.setVal_dim(Rsl_test_sys.getVal_dim());
	        return r;
	}
	@Override
	public List<Rsl_test_sys> getAllkpi() {
	     return Rsl_test_sysrepository.findAll();
	}
//	@Override
//	public void deleteRsl_test_sys(Integer id) {
//		Rsl_test_sysrepository.deleteById(id);
//		
//	}
//	@RequestMapping("deleteall")
//	public void deleteRsl_test_sysAll() {
//		Rsl_test_sysrepository.deleteAll();
//		return rslt;
//	}


	

	@SuppressWarnings("unchecked")
	@Override
	public List<Vue_Globale> getCompKpi() throws Exception {
		//Requete qui compare les kpi qui ont le meme dim et kpi_id avec leur seuil
		List<Vue_Globale> rslt = new ArrayList<Vue_Globale>();
		Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/GreenPlumDHW"
                ,"postgres","root");
        
        PreparedStatement ps = conn.prepareStatement("select k.name_kpi as name, count(distinct(a.val_kpi)) as val1,count(distinct(b.val_kpi)) as val2, sum(distinct(a.val_kpi)) as val3 ,sum(distinct(b.val_kpi)) as val4, a.idkpi as Code_requete ,a.date as Date \r\n" + 
        		"from rsl_test_sys as a LEFT OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi INNER JOIN kpi as k on a.idkpi=k.id_kpi \r\n" + 
        		"and a.date=b.date \r\n" + 
        		"and a.system='Postgres' and b.system='SqlServer' \r\n" +
        		"group by a.system, a.idkpi,a.date,k.seuil, k.name_kpi \r\n" +
        		"having (sum(a.val_kpi) -sum(b.val_kpi)) <> k.seuil \r\n" +
        		"order by a.date\r\n" + 
        		" ");
        PreparedStatement ps1 =conn.prepareStatement
        		("select  a.idkpi,a.date, count(distinct(a.val_kpi))as val5 \r\n" + 
        				"from rsl_test_sys a , rsl_test_sys b ,kpi k where k.id_kpi=a.idkpi \r\n" + 
        				"and a.idkpi=b.idkpi\r\n" + 
        				"and a.system='Postgres' and b.system='SqlServer'\r\n" + 
        				"and a.val_kpi = b.val_kpi and a.valeur_dim=b.valeur_dim \r\n" + 
        				"and a.date=b.date\r\n" + 
        				"group by a.date , a.idkpi,k.seuil\r\n" + 
        				"\r\n " +
        				"Except \r\n" +
        				"select a.idkpi,a.date, count(distinct(a.val_kpi)),count(distinct(b.val_kpi)) \r\n" + 
        				"from rsl_test_sys a , rsl_test_sys b ,kpi k where k.id_kpi=a.idkpi \r\n" + 
        				"and a.idkpi=b.idkpi\r\n" + 
        				"and a.system='Postgres' and b.system='SqlServer'\r\n" + 
        				"and a.date=b.date\r\n" + 
        				"group by a.date , a.idkpi,k.seuil\r\n" + 
        				"order by idkpi");
        PreparedStatement ps2 =conn.prepareStatement
        		("select  a.idkpi,a.date, count(distinct(a.val_kpi))as val5 \r\n" + 
        				"from rsl_test_sys a , rsl_test_sys b ,kpi k where k.id_kpi=a.idkpi \r\n" + 
        				"and a.idkpi=b.idkpi\r\n" + 
        				"and a.system='Postgres' and b.system='SqlServer'\r\n" + 
        				"and a.val_kpi = b.val_kpi and a.valeur_dim=b.valeur_dim \r\n" + 
        				"and a.date=b.date \r\n" +
        				"group by a.date , a.idkpi,k.seuil \r\n" +
        				"\r\n" + 
        				"Except \r\n" +
        				"select a.idkpi,a.date, count(distinct(a.val_kpi)),count(distinct(b.val_kpi)) \r\n" + 
        				"from rsl_test_sys a , rsl_test_sys b ,kpi k where k.id_kpi=a.idkpi \r\n" + 
        				"and a.idkpi=b.idkpi\r\n" + 
        				"and a.system='Postgres' and b.system='SqlServer'\r\n" + 
        				"and a.date=b.date\r\n" + 
        				"group by a.date , a.idkpi,k.seuil\r\n" + 
        				"order by idkpi");

        ResultSet rs = ps.executeQuery();
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
       
        while ( rs.next() && rs1.next()  )
        {
          Vue_Globale blog = new  Vue_Globale();
          blog.setDate(rs.getString("Date"));
          blog.setCode_requete(rs.getLong("Code_requete"));
          blog.setNbreRecordOk(rs1.getInt("val5"));
    //      blog.setVal_kpi1( rs.getLong("val3"));
   //       blog.setVal_kpi2(rs.getLong("val4"));
          blog.setName_kpi(rs.getString("name"));
          blog.setLoad1(rs.getString("val1"));
          blog.setLoad2(rs.getString("val2"));
      
         blog.setGap((rs.getLong("val4")- rs.getLong("val3")));
         java.text.DecimalFormat df = new java.text.DecimalFormat("###");
         System.out.println();
         BigDecimal bigD = new BigDecimal((Math.abs (rs.getDouble("val4")- rs.getDouble("val3")) *100)/(Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
         blog.setGAP_par_100(df.format(bigD));
          rslt.add(blog);
        }
       
        
	
		return rslt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Vue_Globale> getCompKpi2() throws Exception {
		//Requete qui compare les kpi qui ont le meme dim et kpi_id avec leur seuil
		List<Vue_Globale> rslt = new ArrayList<Vue_Globale>();
		Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/GreenPlumDHW"
                ,"postgres","root");
        
        PreparedStatement ps = conn.prepareStatement("select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as valeur1" +
        		" , b.val_kpi as valeur2 , k.name_kpi as name from rsl_test_sys a ,rsl_test_sys b ,kpi k \r\n" + 
				"where b.valeur_dim = a.valeur_dim \r\n" + 
				"and b.idkpi = a.idkpi \r\n" + 
				"and a.idkpi = k.id_kpi and abs(b.val_kpi - a.val_kpi) > k.seuil ");

        ResultSet rs = ps.executeQuery();
     
        while ( rs.next() )
        {
          Vue_Globale blog = new  Vue_Globale();
          blog.setDate(rs.getString("Date"));
          blog.setCode_requete(rs.getLong("Code_requete"));
         
       //   blog.setVal_kpi1( rs.getLong("valeur1"));
      //    blog.setVal_kpi2(rs.getLong("valeur2"));
          blog.setName_kpi(rs.getString("name"));
         

         blog.setGap((rs.getLong("valeur1")- rs.getLong("valeur2")));
         java.text.DecimalFormat df = new java.text.DecimalFormat("###");
         System.out.println();
         BigDecimal bigD = new BigDecimal((Math.abs (rs.getDouble("valeur1")- rs.getDouble("valeur2")) *100)/(Math.max(rs.getDouble("valeur1"), rs.getDouble("valeur2"))));
         blog.setGAP_par_100(df.format(bigD));
          rslt.add(blog);
        }
       
        
	
		return rslt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean VeriferExis(String val_dim, String date, String system) {

	List<Rsl_test_sys>	k = new ArrayList<Rsl_test_sys>();
		k= em.createQuery("SELECT c FROM Rsl_test_sys c WHERE c.val_dim LIKE :valdim And c.date LIKE :date AND c.systeme LIKE :system")
		.setParameter("valdim", val_dim)
		.setParameter("date", date)
		.setParameter("system", system)
		.getResultList();
		if(k.size()>=1) {
			System.out.println();
			return true ;
		}else {
		System.out.println(k);
		return false;
	}
	}

	@Transactional
	@Override
	public void InsertOrUpdate(Rsl_test_sys r) {
//		em.createNativeQuery("INSERT into rsl_test_sys \n" +
//				"(date,idkpi,system,valeur_dim,val_kpi,id_kpi)\n" +
//				"VALUES\n" +
//				"(?,?,?,?,?,?)\n") 
				//"ON CONFLICT  (date,idkpi,system,valeur_dim)\n" +
//				//"DO update set val_kpi=excluded.val_kpi")
//				.setParameter(1,r.date)
//				.setParameter(2,r.idKpi)
//				.setParameter(3,r.systeme)
//				.setParameter(4,r.val_dim)
//				.setParameter(5,r.val_kpi)
//		        .setParameter(6,r.getKpi().id_kpi)
//				.executeUpdate()   ;
	}





}
