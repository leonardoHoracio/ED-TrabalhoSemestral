package br.com.floricultura.model;

import java.math.BigDecimal;

public class Pagamento {
	int codigoPagamento;
	String formaPagamento;
	int totalItens;
	BigDecimal valorPago;
	BigDecimal totalPagar;
	
	public int getCodigoPagamento() {
		return codigoPagamento;
	}
	public void setCodigoPagamento(int codigoPagamento) {
		this.codigoPagamento = codigoPagamento;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public int getTotalItens() {
		return totalItens;
	}
	public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	public BigDecimal getTotalPagar() {
		return totalPagar;
	}
	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}
	
}
