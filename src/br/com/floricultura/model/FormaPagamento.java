package br.com.floricultura.model;

public class FormaPagamento {
	int codigoForma;
	String forma;
	
	public FormaPagamento() {
		
	}
	
	public FormaPagamento(int codigo, String forma) {
		this.codigoForma = codigo;
		this.forma = forma;
	}
	
	public int getCodigoForma() {
		return codigoForma;
	}
	public void setCodigoForma(int codigoForma) {
		this.codigoForma = codigoForma;
	}
	public String getForma() {
		return forma;
	}
	public void setForma(String forma) {
		this.forma = forma;
	}
	@Override
	public String toString() {
		return "FormaPagamento [codigoForma=" + codigoForma + ", forma=" + forma + "]";
	}
	
	
}
