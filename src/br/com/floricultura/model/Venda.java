package br.com.floricultura.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;;

public class Venda {
	
	private int codigoVenda;
	private BigDecimal valorTotal;
	private LocalDate dataHoraVenda;
	private String usuario;
	
	private String cliente;
	private List<ItemVenda> itens;
	public int getCodigoVenda() {
		return codigoVenda;
	}
	public void setCodigoVenda(int codigoVenda) {
		this.codigoVenda = codigoVenda;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public LocalDate getDataHoraVenda() {
		return dataHoraVenda;
	}
	public void setDataHoraVenda(LocalDate dataHoraVenda) {
		this.dataHoraVenda = dataHoraVenda;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public List<ItemVenda> getItens() {
		return itens;
	}
	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	
	
}
