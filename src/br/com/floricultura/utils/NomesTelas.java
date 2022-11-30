package br.com.floricultura.utils;

public enum NomesTelas {

	HOME("", "DashBoard"), VENDAS("", "Tela de Vendas"), ESTOQUE("", "Tela de Estoque"), PRODUTOS("", "Tela de Produtos"),
	PRODUTO_FORM("", "Tela de Formul�rio de Produtos"), CLIENTES("", "Tela de Clientes"),
	CLIENTES_FORM("", "Tela de Formul�rio de Clientes"), FORNECEDORES("", "Tela de Forncedores"),
	FORNECEDORES_FORM("", "Tela de Formul�rio de Forncedores"), CATEGORIAS("categoriaAnchorPane", "Tela de Categorias"),
	CATEGORIAS_FORM("", "Tela de Formul�rio de Categorias"), USUARIOS("", "Tela de Usu�rios"),
	USUARIOS_FORM("", "Tela de Formul�rio de Usu�rios"), RELATORIOS("", "Tela de Relat�rios");

	private final String idTela;
	private final String nomeTela;

	NomesTelas(String idTela, String nomeTela) {
		this.idTela = idTela;
		this.nomeTela = nomeTela;
	}

	public String getIdTela() {
		return this.idTela;
	}

	public String getNomeTela() {
		return this.nomeTela;
	}
}
