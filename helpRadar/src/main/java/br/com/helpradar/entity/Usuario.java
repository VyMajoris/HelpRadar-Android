package br.com.helpradar.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Usuario {
	private Long userId;
	
	private String nome;
	
	
	//string para facilitar na conversão entre o server/app/rest/map
	private String latitude;
	private String longitude;
	
	
	
	private String telefone;
	private String email;
	
	private TipoUsuario tipoUsuario;
	
	private Identificacao identificacao;
	
	private Set<Avaliacao> avaliacao = new HashSet<Avaliacao>(); 
	
	
	

	private Set<Especialidade> especialidade = new HashSet<Especialidade>();

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	




	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public Identificacao getIdentificacao() {
		return identificacao;
	}


	public void setIdentificacao(Identificacao identificacao) {
		this.identificacao = identificacao;
	}


	public Set<Avaliacao> getAvaliacao() {
		return avaliacao;
	}


	public void setAvaliacao(Set<Avaliacao> avaliacao) {
		this.avaliacao = avaliacao;
	}


	public Set<Especialidade> getEspecialidade() {
		return especialidade;
	}


	public void setEspecialidade(Set<Especialidade> especialidade) {
		this.especialidade = especialidade;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}  

}
