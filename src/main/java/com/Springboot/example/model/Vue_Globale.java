package com.Springboot.example.model;


import javax.persistence.*;

@Entity
@Table(name = "vue_globale")
public class Vue_Globale {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id ;
	public String Date ;
	public Long Code_requete;

	public Long val_kpi1 ;
	public Long val_kpi2 ;
	public String load1;
	public String load2;
	public String name_kpi ;
	public Long Gap;
	public String GAP_par_100;
	public int NbreRecordOk ;
	public int NbreRecordNotOk;
	public int FoundDB1 ;
	public int FoundDB2;
	public String tempsExec ;
	public String dateExec ;
	public  String DataQualite;
	@Column(name = "acceptation")
	public String acceptation;


	public String getAcceptation() {
		return acceptation;
	}

	public void setAcceptation(String acceptation) {
		this.acceptation = acceptation;
	}

	public  String getDataQualite() {
		return DataQualite;
	}
	public void setDataQualite( String d) {
		DataQualite = d;
	}
	public String getDateExec() {
		return dateExec;
	}
	public void setDateExec(String dateExec) {
		this.dateExec = dateExec;
	}
	public  String getTempsExec() {
		return tempsExec;
	}
	public void setTempsExec( String tempsExec) {
		this.tempsExec = tempsExec;
	}
	public int getFoundDB1() {
		return FoundDB1;
	}
	public void setFoundDB1(int foundDB1) {
		FoundDB1 = foundDB1;
	}
	public int getFoundDB2() {
		return FoundDB2;
	}
	public void setFoundDB2(int foundDB2) {
		FoundDB2 = foundDB2;
	}
	public int getNbreRecordOk() {
		return NbreRecordOk;
	}
	public void setNbreRecordOk(int nbreRecordOk) {
		NbreRecordOk = nbreRecordOk;
	}
	public int getNbreRecordNotOk() {
		return NbreRecordNotOk;
	}
	public void setNbreRecordNotOk(int nbreRecordNotOk) {
		NbreRecordNotOk = nbreRecordNotOk;
	}
	public Long getGap() {
		return Gap;
	}
	public void setGap(Long gap) {
		Gap = gap;
	}
	public String getGAP_par_100() {
		return GAP_par_100;
	}
	public void setGAP_par_100(String gAP_par_100) {
		GAP_par_100 = gAP_par_100;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Long getCode_requete() {
		return Code_requete;
	}
	public void setCode_requete(Long code_requete) {
		Code_requete = code_requete;
	}

	public Long getVal_kpi1() {
		return val_kpi1;
	}
	public void setVal_kpi1(Long val_kpi1) {
		this.val_kpi1 = val_kpi1;
	}
	public Long getVal_kpi2() {
		return val_kpi2;
	}
	public void setVal_kpi2(Long val_kpi2) {
		this.val_kpi2 = val_kpi2;
	}
	public String getLoad1() {
		return load1;
	}
	public void setLoad1(String load1) {
		this.load1 = load1;
	}
	public String getLoad2() {
		return load2;
	}
	public void setLoad2(String load2) {
		this.load2 = load2;
	}
	public String getName_kpi() {
		return name_kpi;
	}
	public void setName_kpi(String name_kpi) {
		this.name_kpi = name_kpi;
	}

	public int getId() {
		return id;
	}
}
