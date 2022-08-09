package com.Springboot.example.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dimension")
public class Dimension implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id_dim ;
	
	@Column(name = "val_dim")
	public String val_dim ;
	@Column(name = "val_dim_alias")
	public String val_dim_alias;
	@ManyToMany(fetch = FetchType.EAGER ,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
            },
            mappedBy = "dims")
@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Requete> rqs = new ArrayList<Requete>();


	public Long getId_dim() {
		return id_dim;
	}

	public void setId_dim(Long id_dim) {
		this.id_dim = id_dim;
	}

	public List<Requete> getRqs() {
		return rqs;
	}
	public void setRqs(List<Requete> rqs) {
		this.rqs = rqs;
	}
	public Dimension() {
		super();
	}
	public String getVal_dim() {
		return val_dim;
	}
	public void setVal_dim(String val_dim) {
		this.val_dim = val_dim;
	}
	public String getVal_dim_alias() {
		return val_dim_alias;
	}
	public void setVal_dim_alias(String val_dim_alias) {
		this.val_dim_alias = val_dim_alias;
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
		Dimension other = (Dimension) obj;
		if (id_dim == null) {
			if (other.id_dim != null)
				return false;
		} else if (!id_dim.equals(other.id_dim))
			return false;
		
		if (rqs == null) {
			if (other.rqs != null)
				return false;
		} else if (!rqs.equals(other.rqs))
			return false;
		if (val_dim == null) {
			if (other.val_dim != null)
				return false;
		} else if (!val_dim.equals(other.val_dim))
			return false;
		if (val_dim_alias == null) {
			if (other.val_dim_alias != null)
				return false;
		} else if (!val_dim_alias.equals(other.val_dim_alias))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Dimension [id_dim=" + id_dim + ", val_dim=" + val_dim + ", val_dim_alias="
				+ val_dim_alias ;
	}

	
	
	

}
