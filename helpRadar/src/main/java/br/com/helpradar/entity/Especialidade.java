package br.com.helpradar.entity;

import java.util.HashSet;
import java.util.Set;



public class Especialidade {

	
	private int id;
	
	 private Set<Usuario> usuario = new HashSet<Usuario>();  
	
	
	/**
	 * @param usuario
	 * @param nomeEspecialidade
	 */
	public Especialidade(Set<Usuario> usuario, String nomeEspecialidade) {
		super();
		this.usuario = usuario;
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public Especialidade() {
		super();
	}

	private String nomeEspecialidade;

	public String getNomeEspecialidade() {
		return nomeEspecialidade;
	}

	public Especialidade(String nomeEspecialidade) {
		super();
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
