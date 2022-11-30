package br.com.floricultura.model;

import java.time.LocalDate;
import java.util.List;

public class Fornecedor {

	private int codigoFornecedor;
	private String razaoSocial;
	private String nomeFantasia;
	private String cpfCnpj;
	private String inscEstadual;
	private String inscMunicipal;
	private LocalDate dataInclusao;
	private String tipoEmpresa;
	private String statusFornecedor;

	private String email;

	Endereco endereco;

	List<Contato> listaContato;

	public int getCodigoFornecedor() {
		return codigoFornecedor;
	}


	public void setCodigoFornecedor(int codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}


	public String getRazaoSocial() {
		return razaoSocial;
	}


	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}


	public String getNomeFantasia() {
		return nomeFantasia;
	}


	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}


	public String getCpfCnpj() {
		return cpfCnpj;
	}


	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}


	public String getInscEstadual() {
		return inscEstadual;
	}


	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}


	public String getInscMunicipal() {
		return inscMunicipal;
	}


	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}


	public LocalDate getDataInclusao() {
		return dataInclusao;
	}


	public void setDataInclusao(LocalDate dataInclusao) {
		this.dataInclusao = dataInclusao;
	}


	public String getTipoEmpresa() {
		return tipoEmpresa;
	}


	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}


	public String getStatusFornecedor() {
		return statusFornecedor;
	}


	public void setStatusFornecedor(String statusFornecedor) {
		this.statusFornecedor = statusFornecedor;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public List<Contato> getListaContato() {
		return listaContato;
	}


	public void setListaContato(List<Contato> listaContato) {
		this.listaContato = listaContato;
	}


	@Override
	public String toString() {
		return "Fornecedor [codigoFornecedor=" + codigoFornecedor + ", razaoSocial=" + razaoSocial + "]";
	}
}
