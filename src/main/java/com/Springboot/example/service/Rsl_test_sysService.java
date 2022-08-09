package com.Springboot.example.service;



import com.Springboot.example.model.Rsl_test_sys;
import com.Springboot.example.model.Vue_Globale;

import java.util.List;

public interface Rsl_test_sysService {
	
	public void createOrUpdateRsl_test_sys(Rsl_test_sys kpi);
	 
    public List<Rsl_test_sys> getAllkpi();

    public void deleteRsl_test_sys(Integer id);
    
    public List<Vue_Globale> getCompKpi() throws Exception;
    
    public List<Vue_Globale> getCompKpi2() throws Exception;
    
    public boolean VeriferExis(String val_dim, String f,String val_kpii);

    public void InsertOrUpdate(Rsl_test_sys r);

}
