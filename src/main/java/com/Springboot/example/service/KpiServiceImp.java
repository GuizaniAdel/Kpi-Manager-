package com.Springboot.example.service;


import com.Springboot.example.model.Kpi;
import com.Springboot.example.repository.KpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class KpiServiceImp implements KpiService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private KpiRepository kpirepository ;
	
	@Override
	public void createOrUpdatekpi(Kpi kpi) {
		kpirepository.save(toEntitykpi(kpi));
		
	}

	private Kpi toEntitykpi(Kpi kpi) {
		    Kpi k = new Kpi();
		    k.setId_kpi(kpi.getId_kpi());
	        k.setName_kpi(kpi.getName_kpi());
	        k.setSeuil(kpi.getSeuil());
	        k.setSubject_area(kpi.getSubject_area());
	        k.setType(kpi.getType());
	        return k;
	}

	@Override
	public List<Kpi> getAllkpi() {
		List<Kpi> kpis = kpirepository.findAll();
		
		return kpis;
	}

	@Override
	public void deleteKpi(Long id_kpi) {
	 kpirepository.deleteById(id_kpi);
		
	}
	@SuppressWarnings("unchecked")
	@Override
    public List<Kpi> getFathers(){
		List<Kpi> listFathers ;
		
		listFathers=  em.createQuery("select d from Kpi d where d.type='Father'").getResultList();
	   System.out.println(listFathers);
	   return listFathers;
   }

	
}
