package com.Springboot.example.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="requete")
public class Requete implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id ;
	@Column(name = "code_requete")
	public int code_requete ;
	@Column(name ="date")
	public String date ;
	@Column(name = "val_kpi")
	public String val_kpi;
	@Column(name = "val_kpi_alias")
	public String val_kpi_alias ;
	@Column(name ="copr")
	public String copr ;
	@Column(name="date_image")
	public String date_image ;
	   @ManyToMany(fetch = FetchType.EAGER,
	            cascade = {


						CascadeType.PERSIST
	            })
	    @JoinTable(name = "requete_dim",
	            joinColumns = { @JoinColumn(name = "id") },
	            inverseJoinColumns = { @JoinColumn(name = "id_dim") })

	    private List<Dimension> dims = new ArrayList<Dimension>();

	   @JoinColumn(name="id_database")
	   @ManyToOne
	   private Database id_databasee;
	   
	    @JoinColumn(name="id_kpi",  nullable=false)
	    @ManyToOne
	   private Kpi kpi;

	public Kpi getKpi() {
			return kpi;
		}

		public void setKpi(Kpi kpi) {
			this.kpi = kpi;
		}

	

	public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	public int getCode_requete() {
		return code_requete;
	}

	public void setCode_requete(int code_requete) {
		this.code_requete = code_requete;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVal_kpi() {
		return val_kpi;
	}

	public void setVal_kpi(String val_kpi) {
		this.val_kpi = val_kpi;
	}

	public String getVal_kpi_alias() {
		return val_kpi_alias;
	}

	public void setVal_kpi_alias(String val_kpi_alias) {
		this.val_kpi_alias = val_kpi_alias;
	}

	public String getCopr() {
		return copr;
	}

	public void setCopr(String copr) {
		this.copr = copr;
	}

	public String getDate_image() {
		return date_image;
	}

	public void setDate_image(String date_image) {
		this.date_image = date_image;
	}

	
	public List<Dimension> getDims() {
		return dims;
	}

	public void setDims(List<Dimension> dims) {
		this.dims = dims;
	}

	public Database getId_databasee() {
		return id_databasee;
	}

	public void setId_databasee(Database id_databasee) {
		this.id_databasee = id_databasee;
	}

	

	public Requete(int code_requete, String date, String val_kpi, String val_kpi_alias, String copr, String date_image,
                   List<Dimension> dims, Database id_databasee, Kpi id_kpii) {
		super();
		this.code_requete = code_requete;
		this.date = date;
		this.val_kpi = val_kpi;
		this.val_kpi_alias = val_kpi_alias;
		this.copr = copr;
		this.date_image = date_image;
		this.dims = dims;
		this.id_databasee = id_databasee;
		this.kpi = id_kpii;
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
		Requete other = (Requete) obj;
		if (code_requete != other.code_requete)
			return false;
		if (copr == null) {
			if (other.copr != null)
				return false;
		} else if (!copr.equals(other.copr))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (date_image == null) {
			if (other.date_image != null)
				return false;
		} else if (!date_image.equals(other.date_image))
			return false;
		if (dims == null) {
			if (other.dims != null)
				return false;
		} else if (!dims.equals(other.dims))
			return false;
		if (id_databasee == null) {
			if (other.id_databasee != null)
				return false;
		} else if (!id_databasee.equals(other.id_databasee))
			return false;
		if (kpi == null) {
			if (other.kpi != null)
				return false;
		} else if (!kpi.equals(other.kpi))
			return false;
		if (val_kpi == null) {
			if (other.val_kpi != null)
				return false;
		} else if (!val_kpi.equals(other.val_kpi))
			return false;
		if (val_kpi_alias == null) {
			if (other.val_kpi_alias != null)
				return false;
		} else if (!val_kpi_alias.equals(other.val_kpi_alias))
			return false;
		return true;
	}

	public Requete() {
		super();
	}

	@Override
	public String toString() {
		return "Requete [id=" + id + ", code_requete=" + code_requete + ", date=" + date + ", val_kpi=" + val_kpi
				+ ", val_kpi_alias=" + val_kpi_alias + ", copr=" + copr + ", date_image=" + date_image + ", dims="
				+ dims + ", id_databasee=" + id_databasee + ", id_kpii=" + kpi + "]";
	}
	


}
