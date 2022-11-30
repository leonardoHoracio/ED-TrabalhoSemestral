package br.com.floricultura.model;

public enum TipoRelatorio {
	VENDAS(1, "Relatórios de Vendas"),
    ENTRADAS_PRODUTOS(2, "Entrada de Produto"),
    SAIDA_PRODUTOS(3, "Saida de Produto"),
	PRODUTOS(4, "Produtos Teste");
	
	 private final int valor;
     private final String descricao;

	TipoRelatorio(int valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}
	
	public int getValor() {
        return this.valor;
    }
    public String getDescricao() {
        return this.descricao;
    }
    

}
