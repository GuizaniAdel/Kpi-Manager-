package com.Springboot.example.service;

import com.Springboot.example.model.Database;
import com.Springboot.example.model.Kpi;
import com.Springboot.example.model.Requete;

public interface RequeteService {

    public String TesterRequete(Database db, Requete r , Kpi k,String DateDeb,String DateFin, String[] valdims,String[] aliasvaldim ) throws ClassNotFoundException;
}
