package br.com.floricultura.model;

import java.math.BigDecimal;

public class Carrinho {
	int numeroItem;
	int codigoProduto;
	String descricaoProduto;
	int quantidade;
	BigDecimal valorUnitario;
	BigDecimal valorTotal;
	
	
	
	public int getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}
	public int getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(int codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
}
