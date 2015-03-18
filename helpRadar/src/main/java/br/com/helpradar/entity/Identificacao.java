package br.com.helpradar.entity;

import java.util.List;


public class Identificacao {

	
	private int id;

	public Identificacao(){

	}


	//pecas de roupas
	//sera guardada no banco todas as peça para mostrar por meio de auto-complete para o assisente re-inserir

	private List<String> pecas;







	/**
	 * @param id
	 * @param pecas
	 */
	public Identificacao(List<String> pecas) {
		super();

		this.pecas = pecas;
	}



	public void setId(int id) {
		this.id = id;
	}



	public List<String> getPecas() {
		return pecas;
	}

	public void setPecas(List<String> pecas) {
		this.pecas = pecas;
	}


	public int getId() {
		return id;
	}





}
