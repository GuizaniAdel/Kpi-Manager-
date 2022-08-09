package com.Springboot.example.service;



import com.Springboot.example.model.Kpi;

import java.util.List;

public interface KpiService {
	
	public void createOrUpdatekpi(Kpi kpi);
	 
    public List<Kpi> getAllkpi();

    public void deleteKpi(Long id);

    public List<Kpi> getFathers();
}
