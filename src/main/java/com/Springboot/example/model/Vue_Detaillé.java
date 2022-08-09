package com.Springboot.example.model;


import javax.persistence.*;

@Entity
@Table(name = "vue_detaille")
public class Vue_Detaillé {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id ;
    public String date ;
    public long code_requete;
    public String groupement;
    public double val_kpi1;
    public  double val_kpi2;
    public String name_kpi;
    public double gap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCode_requete() {
        return code_requete;
    }

    public void setCode_requete(long code_requete) {
        this.code_requete = code_requete;
    }

    public String getGroupement() {
        return groupement;
    }

    public void setGroupement(String groupement) {
        this.groupement = groupement;
    }

    public double getVal_kpi1() {
        return val_kpi1;
    }

    public void setVal_kpi1(double val_kpi1) {
        this.val_kpi1 = val_kpi1;
    }

    public double getVal_kpi2() {
        return val_kpi2;
    }

    public void setVal_kpi2(double val_kpi2) {
        this.val_kpi2 = val_kpi2;
    }

    public String getName_kpi() {
        return name_kpi;
    }

    public void setName_kpi(String name_kpi) {
        this.name_kpi = name_kpi;
    }

    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }
}
