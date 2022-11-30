package br.com.floricultura.utils;

public class Constants {
	public static final String LOGIN = "../view/FXMLLogin.fxml";
	public static final String MAINVIEW = "../view/FXMLMain.fxml";
	public static final String SIDERMENU = "../view/FXMLSiderMenu.fxml";
	public static final String VENDA = "../view/FXMLVenda.fxml";
	public static final String PAGAMENTO =  "../view/FXMLPagamento.fxml";
	public static final String REMOVER_ITEM_VENDA = "../view/FXMLRemoverItemVenda.fxml";
	public static final String QUANTIDADE_ITEM =  "../view/FXMLQuantidadeItem.fxml";
	public static final String PRODUTO_FORM = "../view/FXMLProdutoForm.fxml";
	public static final String PRODUTO =  "../view/FXMLProduto.fxml";
	public static final String FORNECEDOR =  "../view/FXMLFornecedor.fxml";
	public static final String FORNECEDOR_FORM =  "../view/FXMLFornecedorForm.fxml";
	public static final String CATEGORIA =  "../view/FXMLCategoria.fxml";
	public static final String RELATORIOS =  "../view/FXMLRelatorios.fxml";
	public static final String RELATORIO_PRODUTO =  "../view/Produtos.jrxml";
	public static final String USUARIO_FORM =  "../view/FXMLUsuarioForm.fxml";
	public static final String USUARIO =  "../view/FXMLUsuario.fxml";
	
	public enum OperationType  {
	    CREATE, READ, UPDATE, DELETE;
	}
	
	public enum NivelAcesso {
	    ADMINISTRADOR, CAIXA, OPERACIONAL;
	}
	
}
