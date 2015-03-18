package br.com.helpradar.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;



public class Avaliacao {

	/**
	 * @param usuario
	 * @param descricao
	 * @param dataAvaliacao
	 * @param titulo
	 * @param nota
	 */
	public Avaliacao(Set<Usuario> usuario, String descricao,
			Calendar dataAvaliacao, String titulo, Integer nota, String nomeEspecialidade) {
		super();
		this.usuario = usuario;
		this.descricao = descricao;
		this.dataAvaliacao = dataAvaliacao;
		this.titulo = titulo;
		this.nota = nota;
		this.nomeEspecialidade =  nomeEspecialidade;
	}
	
	public Avaliacao(){
		
	}
private String nomeEspecialidade;

	private int id;
	
	 
	 private Set<Usuario> usuario = new HashSet<Usuario>(); 
	

	private String descricao;

	//será provida a Data do sistema no input, mas o usuário poderá alterar
	private Calendar dataAvaliacao;

	private String titulo;


	// 1..5 Integer. para poder ser null
	private Integer nota;



	

	public Avaliacao(int id, String descricao, Calendar dataAvaliacao,
			String titulo, Integer nota) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataAvaliacao = dataAvaliacao;
		this.titulo = titulo;
		this.nota = nota;
	}


	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Integer getNota() {
		return nota;
	}


	public void setNota(Integer nota) {
		this.nota = nota;
	}


	public Calendar getDataAvaliacao() {
		return dataAvaliacao;
	}


	public void setDataAvaliacao(Calendar dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}


	public Set<Usuario> getUsuario() {
		return usuario;
	}


	public void setUsuario(Set<Usuario> usuario) {
		this.usuario = usuario;
	}

	public String getNomeEspecialidade() {
		return nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}






	
	
	

	
	

}
