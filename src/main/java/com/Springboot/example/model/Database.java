package com.Springboot.example.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="database")
public class Database implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id ;
	@Column(name="name")
	public String name ;
	@Column(name="username")
	public String username;
	@Column(name="password")
	public String password ;
	@Column(name="port")
	public int port ;
	@Column(name="url")
	public String url;
	@Column(name="system")
	public String system;
    @OneToMany(mappedBy="id_databasee",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
	 private List<Requete> requetes = new ArrayList<Requete>();

	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public Database(String name, String username, String password, int port, String url , String system) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.port = port;
		this.url = url;
		this.system=system;
	}
	public Database() {
		super();
	}
	@Override
	public String toString() {
		return "Database [name=" + name + ", username=" + username + ", password=" + password + ", port=" + port
				+ ", url=" + url + ", system=" + system + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Database other = (Database) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public List<Requete> getRequetes() {
		return requetes;
	}
	public void setRequetes(List<Requete> requetes) {
		this.requetes = requetes;
	}
	
	

}
