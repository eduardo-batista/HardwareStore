package br.unitins.hardwarestore.model;

public enum Sexo {

	MASCULINO(1, "Masculino"), FEMININO(2, "Feminino"), NAOBINARIO(3, "Não-Binário");
	
	private int id;
	private String label;
	
	Sexo(int id, String label){
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
}
