package com.Springboot.example.service;




import com.Springboot.example.model.Database2;
import com.Springboot.example.model.Vue_Globale;

import java.util.List;

public interface Database2Service {
	
	public void createOrUpdateDatabae2(Database2 kpi);
	 
    public List<Database2> getAllkpi();

    public void deleteDatabase2(Integer id);
    
    public List<Vue_Globale> getCompKpi() throws Exception;
    
    public List<Vue_Globale> getCompKpi2() throws Exception;
    
    public boolean VeriferExis(String val_dim, String f,String val_kpii);

    public void InsertOrUpdate(Database2 r);

    public void deleteDatabase2All();

	 

}
