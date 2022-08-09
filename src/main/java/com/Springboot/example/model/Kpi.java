package com.Springboot.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="kpi")
public class Kpi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_kpi")
	public Long id_kpi;
	@Column(name = "name_kpi")
	public String name_kpi ;
	@Column(name= "subject_area")
	public String subject_area ;
	@Column(name="type")
	public String type ;
	@Column(name ="seuil")
	public float seuil ;
	@Column(name="seuil_gap")
	public Float seuil_gap;
	@Column(name="seuil_dataQuality")
	public Float seuil_dataQuality;

    @OneToMany(mappedBy="kpi",cascade= CascadeType.ALL, fetch = FetchType.EAGER)

	private List<Requete> requetess = new ArrayList<Requete>();




	public List<Requete> getRequetess() {
		return requetess;
	}

	public void setRequetess(List<Requete> requetess) {
		this.requetess = requetess;
	}

	public Long getId_kpi() {
		return id_kpi;
	}
	
	public void setId_kpi(Long id_kpi) {
		this.id_kpi = id_kpi;
	}
	public String getName_kpi() {
		return name_kpi;
	}
	public void setName_kpi(String name_kpi) {
		this.name_kpi = name_kpi;
	}
	public String getSubject_area() {
		return subject_area;
	}
	public void setSubject_area(String subject_area) {
		this.subject_area = subject_area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getSeuil() {
		return seuil;
	}
	public void setSeuil(float seuil) {
		this.seuil = seuil;
	}

	public void setSeuil_gap(Float seuil_gap) {
		this.seuil_gap = seuil_gap;
	}

	public void setSeuil_dataQuality(Float seuil_dataQuality) {
		this.seuil_dataQuality = seuil_dataQuality;
	}

	public Float getSeuil_gap() {
		return seuil_gap;
	}

	public Float getSeuil_dataQuality() {
		return seuil_dataQuality;
	}

	public Kpi(Long id_kpi, String name_kpi, String subject_area, String type, float seuil, float seuil_gap, float seuil_dataQuality) {
		this.id_kpi = id_kpi;
		this.name_kpi = name_kpi;
		this.subject_area = subject_area;
		this.type = type;
		this.seuil = seuil;
		this.seuil_gap = seuil_gap;
		this.seuil_dataQuality = seuil_dataQuality;
	}

	public Kpi() {
		super();
	}
	@Override
	public String toString() {
		return "Kpi [id_kpi=" + id_kpi + ", name_kpi=" + name_kpi + ", subject_area=" + subject_area + ", type=" + type
				+ ", seuil=" + seuil + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kpi other = (Kpi) obj;
		if (id_kpi == null) {
			if (other.id_kpi != null)
				return false;
		} else if (!id_kpi.equals(other.id_kpi))
			return false;
		if (name_kpi == null) {
			if (other.name_kpi != null)
				return false;
		} else if (!name_kpi.equals(other.name_kpi))
			return false;
		if (requetess == null) {
			if (other.requetess != null)
				return false;
		} else if (!requetess.equals(other.requetess))
			return false;
		if (Float.floatToIntBits(seuil) != Float.floatToIntBits(other.seuil))
			return false;
		if (subject_area == null) {
			if (other.subject_area != null)
				return false;
		} else if (!subject_area.equals(other.subject_area))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	
}
