package br.com.floricultura.model;

public class Categoria {
	
	private int codigoCategoria;
	private String descricaoCategoria;
	private String statusCategoria;
	
	public int getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}
	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	public String getStatusCategoria() {
		return statusCategoria;
	}
	public void setStatusCategoria(String statusCategoria) {
		this.statusCategoria = statusCategoria;
	}
	@Override
	public String toString() {
		return "Categoria [codigoCategoria=" + codigoCategoria + ", descricaoCategoria=" + descricaoCategoria + "]";
	}
	
	
}
