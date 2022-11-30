package br.com.floricultura.utils;

public enum NomesTelas {

	HOME("", "DashBoard"), VENDAS("", "Tela de Vendas"), ESTOQUE("", "Tela de Estoque"), PRODUTOS("", "Tela de Produtos"),
	PRODUTO_FORM("", "Tela de Formulário de Produtos"), CLIENTES("", "Tela de Clientes"),
	CLIENTES_FORM("", "Tela de Formulário de Clientes"), FORNECEDORES("", "Tela de Forncedores"),
	FORNECEDORES_FORM("", "Tela de Formulário de Forncedores"), CATEGORIAS("categoriaAnchorPane", "Tela de Categorias"),
	CATEGORIAS_FORM("", "Tela de Formulário de Categorias"), USUARIOS("", "Tela de Usuários"),
	USUARIOS_FORM("", "Tela de Formulário de Usuários"), RELATORIOS("", "Tela de Relatórios");

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
