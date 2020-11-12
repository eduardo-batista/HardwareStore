package br.unitins.hardwarestore.controller;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.hardwarestore.dao.ProdutoDAO;
import br.unitins.hardwarestore.model.Categoria;
import br.unitins.hardwarestore.model.Produto;

@Named
@ViewScoped
public class ProdutoController extends Controller<Produto> implements Serializable {

	private static final long serialVersionUID = -3955368378894625110L;
	
	public ProdutoController() {
		super(new ProdutoDAO());
	}

	@Override
	public Produto getEntity() {
		if (entity == null)
			entity = new Produto();
		return entity;
	}
	
	public Categoria[] getListaCategoria() {
		return Categoria.values();
	}	
}
