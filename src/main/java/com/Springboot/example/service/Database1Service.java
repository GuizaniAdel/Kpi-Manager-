package com.Springboot.example.service;



import com.Springboot.example.model.Database1;

import com.Springboot.example.model.Vue_Globale;

import java.util.List;

public interface Database1Service {
	
	public void createOrUpdateDatabae1(Database1 kpi);
	 
    public List<Database1> getAllkpi();

    public void deleteDatabase1(Integer id);
    
    public List<Vue_Globale> getCompKpi() throws Exception;
    
    public List<Vue_Globale> getCompKpi2() throws Exception;
    
    public boolean VeriferExis(String val_dim, String f,String val_kpii);

    public void InsertOrUpdate(Database1 r);

	public void deleteDatabase1All();

	 

}
