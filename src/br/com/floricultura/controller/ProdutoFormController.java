package br.com.floricultura.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.floricultura.dao.CategoriaDAO;
import br.com.floricultura.dao.FornecedorDAO;
import br.com.floricultura.dao.ProdutoDAO;
import br.com.floricultura.model.Categoria;
import br.com.floricultura.model.Fornecedor;
import br.com.floricultura.model.Produto;
import br.com.floricultura.utils.Alerts;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.MaskFieldUtil;
import br.com.floricultura.utils.Utils;
import br.com.floricultura.utils.Constants.OperationType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ProdutoFormController implements Initializable {

	@FXML
	private JFXButton btnAddCategoria;

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private JFXButton btnCrescente;

	@FXML
	private JFXButton btnDecrescente;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnNovaCategoria;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private TableView<Categoria> categoriaTableView;

	@FXML
	private JFXComboBox<Categoria> cmbCategoria;
	
	@FXML
	private JFXComboBox<Categoria> cmbSubcategoria;

	@FXML
	private JFXComboBox<Fornecedor> cmbFornecedor;

	@FXML
	private TableColumn<Categoria, Integer> columnCodigo;

	@FXML
	private TableColumn<Categoria, String> columnNome;

	@FXML
	private TableColumn<Categoria, Void> columnOperacao;

	@FXML
	private TextField txtCodigoProduto;

	@FXML
	private TextField txtNomeProduto;

	@FXML
	private TextField txtPrecoCompra;

	@FXML
	private TextField txtPrecoVenda;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtcodigoBarras;

	private int quantidade = 1;

	private OperationType flagOperacao = OperationType.CREATE;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		txtQuantidade.setText("" + this.quantidade);
		MaskFieldUtil.monetaryField(txtPrecoVenda);
		MaskFieldUtil.monetaryField(txtPrecoCompra);
		Produto produto = ProdutoController.selectedProduto;

		if (produto != null) {
			setCampos(produto);
			desabilitarCampos();
			flagOperacao = OperationType.UPDATE;
		} else {
			habilitarCampos();
			
			preencherComboFornecedor();
		}
		
		preencherComboCategorias();
		
		Callback<ListView<Fornecedor>, ListCell<Fornecedor>> fornecedorFactory = lv -> new ListCell<Fornecedor>() {
			@Override
			protected void updateItem(Fornecedor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomeFantasia());
			}
		};
		cmbFornecedor.setCellFactory(fornecedorFactory);
		cmbFornecedor.setButtonCell(fornecedorFactory.call(null));
		
		Callback<ListView<Categoria>, ListCell<Categoria>> categoriaFactory = lv -> new ListCell<Categoria>() {
			@Override
			protected void updateItem(Categoria item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescricaoCategoria());
			}
		};
		cmbCategoria.setCellFactory(categoriaFactory);
		cmbCategoria.setButtonCell(categoriaFactory.call(null));
		cmbSubcategoria.setCellFactory(categoriaFactory);
		cmbSubcategoria.setButtonCell(categoriaFactory.call(null));
		
	}

	@FXML
	void onActionCrescente(MouseEvent event) {
		this.quantidade++;
		this.txtQuantidade.setText("" + quantidade);
	}

	@FXML
	void onActionDecrescente(MouseEvent event) {
		if (this.quantidade > 1) {
			this.quantidade--;
			this.txtQuantidade.setText("" + quantidade);
		}
	}

	@FXML
	void onKeyTypedQuantidade(KeyEvent event) {

		txtQuantidade.textProperty().addListener((observable, oldValue, newValue) -> {
			quantidade = Integer.parseInt(newValue);
			this.txtQuantidade.setText("" + quantidade);
		});
	}

	@FXML
	void onActionSalvar(MouseEvent event) {
		operation();
	}

	@FXML
	void onActionAlterar(MouseEvent event) {
		flagOperacao = OperationType.UPDATE;
		habilitarCampos();
	}

	@FXML
	void onActionExcluir(MouseEvent event) {
		flagOperacao = OperationType.DELETE;
		operation();
	}

	@FXML
	void onActionLimpar(MouseEvent event) {
		flagOperacao = OperationType.READ;
	}

	@FXML
	void onActionVoltar(MouseEvent event) {
		voltarTela();
	}

	
	void preencherComboCategorias() {
		CategoriaDAO dao = new CategoriaDAO();

		for (Categoria categoria : dao.listarCategoria()) {
			cmbCategoria.getItems().add(categoria);
			cmbSubcategoria.getItems().add(categoria);
		}

		cmbCategoria.getSelectionModel().selectFirst();
		
		
	}

	void preencherComboFornecedor() {
		FornecedorDAO daoF = new FornecedorDAO();
		
		for (Fornecedor f : daoF.listarFornecedores()) {
			cmbFornecedor.getItems().add(f);
		}
		cmbFornecedor.getSelectionModel().selectFirst();
	}

	@FXML
	void onActionAddCategoria(MouseEvent event) {

		columnCodigo.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

		columnNome.setCellValueFactory(new PropertyValueFactory<>("descricaoCategoria"));

		ObservableList<Categoria> categorias = categoriaTableView.getItems();

		Categoria categoria = new Categoria();
		categoria.setCodigoCategoria(cmbCategoria.getSelectionModel().getSelectedItem().getCodigoCategoria());
		categoria.setDescricaoCategoria(cmbCategoria.getSelectionModel().getSelectedItem().getDescricaoCategoria());

		categorias.add(categoria);

		categoriaTableView.setItems(categorias);

	}

	private Produto getProduto() {
		Produto produto = new Produto();

		if (flagOperacao != OperationType.CREATE) {
			produto.setCodigoProduto(Integer.parseInt(txtCodigoProduto.getText()));
		}
		
		produto.setNomeProduto(txtNomeProduto.getText());
	
		produto.setQuantidadeProduto(Integer.parseInt(txtQuantidade.getText()));
		produto.setValorCompra(Utils.convertDataMoney(txtPrecoCompra.getText()));
		produto.setValorVenda(Utils.convertDataMoney(txtPrecoVenda.getText()));
	
		produto.setCodigoCategoria(cmbCategoria.getValue().getCodigoCategoria());
		produto.setCodigoSubcategoria(cmbSubcategoria.getValue() == null ? 0 : cmbSubcategoria.getValue().getCodigoCategoria());
		produto.setCodigoFornecedor(cmbFornecedor.getValue().getCodigoFornecedor());

		return produto;
	}

	void operation() {
		ProdutoDAO dao = new ProdutoDAO();

		switch (flagOperacao) {

		case UPDATE:
			dao.alterarProduto(getProduto());
			break;

		case DELETE:
			dao.excluirProduto(getProduto().getCodigoProduto());
			break;

		default:
			if(campoVazio()) {
				Alerts.showAlert("Campos vazios", "Error Campos vazios", "Campos vazios", AlertType.ERROR);
			}else {
				dao.incluirProduto(getProduto());
				Alerts.showAlert("Sucesso", null, "Incluido com sucesso", AlertType.INFORMATION);
			}
			break;
		}
		limparCampos();
	}

	void limparCampos() {
		txtCodigoProduto.setText(null);
		txtNomeProduto.setText(null);
		txtcodigoBarras.setText(null);
		txtQuantidade.setText(null);
		txtPrecoCompra.setText(null);
		txtPrecoVenda.setText(null);
	}

	private void habilitarCampos() {

		txtNomeProduto.setDisable(false);
		txtcodigoBarras.setDisable(false);
		txtQuantidade.setDisable(false);
		txtPrecoCompra.setDisable(false);
		txtPrecoVenda.setDisable(false);

	}

	private void desabilitarCampos() {

		txtNomeProduto.setDisable(true);
		txtcodigoBarras.setDisable(true);
		txtQuantidade.setDisable(true);
		txtPrecoCompra.setDisable(true);
		txtPrecoVenda.setDisable(true);

	}

	void desabilitarBotoes() {

		btnSalvar.setDisable(true);
		btnAlterar.setDisable(true);
		btnLimpar.setDisable(true);
		btnExcluir.setDisable(true);

	}

	void habilitarBotoes() {
		btnSalvar.setDisable(false);
		btnAlterar.setDisable(false);
		// btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		// btnNovo.setDisable(false);

	}
	
	public boolean campoVazio() {
		return txtNomeProduto.getText() == null || txtNomeProduto.getText().trim().isEmpty() 
				|| txtPrecoCompra.getText() == null || txtPrecoCompra.getText().trim().isEmpty() 
				|| txtPrecoVenda.getText() == null || txtPrecoVenda.getText().trim().isEmpty() 
				|| cmbCategoria.getValue() == null ||cmbCategoria.getValue().toString().trim().isEmpty();

	}

	private void setCampos(Produto produto) {

		txtCodigoProduto.setText(String.valueOf(produto.getCodigoProduto()));
		txtNomeProduto.setText(produto.getNomeProduto());
		txtcodigoBarras.setText("Codigo de barras");
		txtQuantidade.setText(String.valueOf(produto.getQuantidadeProduto()));
		txtPrecoCompra.setText(String.valueOf(produto.getValorCompra()));
		txtPrecoVenda.setText(String.valueOf(produto.getValorVenda()));

		
	}

	private void voltarTela() {
		SiderMenuController menu = new SiderMenuController();
		menu.switchPane(Constants.PRODUTO);
	}

}
