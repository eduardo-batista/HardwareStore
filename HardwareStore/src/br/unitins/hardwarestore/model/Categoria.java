package br.unitins.hardwarestore.model;

public enum Categoria {

	CONSUMIVEL(1, "Consum�vel"), VESTUARIO(2, "Vestu�rio"), RECARREGAVEL(3, "Recarreg�vel");
	
	private int id;
	private String label;
	
	Categoria(int id, String label){
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}

	public static Categoria valueOf(int id) {
		for (Categoria categoria : values()) {
			if (id == categoria.getId())
				return categoria;
		}
		return null;
	}
}
