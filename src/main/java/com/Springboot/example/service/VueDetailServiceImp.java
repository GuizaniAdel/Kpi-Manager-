package com.Springboot.example.service;

import com.Springboot.example.model.Vue_Detaillé;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Service
public class VueDetailServiceImp implements VueDetailSevice {

    @PersistenceContext
    EntityManager em ;
    @Transactional
    @Override
    public void InsertOrUpdate(Vue_Detaillé r) {
        em.createNativeQuery("INSERT into vue_detaille" +
                " (code_requete,date,gap,groupement,name_kpi,val_kpi1,val_kpi2) " +
                "VALUES\n" +
                "(?,?,?,?,?,?,?)\n" +
                "ON CONFLICT  (date,code_requete,groupement)\n" +
                "DO update set val_kpi1=excluded.val_kpi1 , val_kpi2=excluded.val_kpi2 , gap=excluded.gap ")
                .setParameter(2,r.date)
                .setParameter(1,r.code_requete)
                .setParameter(3,r.gap)
                .setParameter(5,r.name_kpi)
                .setParameter(4,r.groupement)
                .setParameter(6,r.val_kpi1)
                .setParameter(7,r.val_kpi2)
                .executeUpdate()   ;
    }
}
