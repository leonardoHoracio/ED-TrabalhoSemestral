package br.com.floricultura.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.floricultura.dao.ProdutoDAO;
import br.com.floricultura.model.Produto;
import br.com.floricultura.utils.Constants;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class ProdutoController implements Initializable{

    public static Produto selectedProduto;

	@FXML
    private JFXButton btnAddProduto;
    
    @FXML
    private TableView<Produto> tableViewProduto;

    @FXML
    private TableColumn<Produto, String> colunmCategoria;

    @FXML
    private TableColumn<Produto, Integer> colunmCodigoProduto;

    @FXML
    private TableColumn<Produto, LocalDate> colunmDataVencimento;

    @FXML
    private TableColumn<Produto, String> colunmFornecedor;

    @FXML
    private TableColumn<Produto, String> colunmNomeProduto;

    @FXML
    private TableColumn<Produto, Void> colunmOperacao;

    @FXML
    private TableColumn<Produto, Integer> colunmQuantidade;

    @FXML
    private TableColumn<Produto, String> colunmSubCategoria;

    @FXML
    private TableColumn<Produto, BigDecimal> colunmValorCompra;

    @FXML
    private TableColumn<Produto, BigDecimal> colunmValorVenda;

    @FXML
    private TextField txtFilterProduto;

    @FXML
    void onMouseClickedAddProduto(MouseEvent event) {
    	SiderMenuController menu = new SiderMenuController();
    	selectedProduto = null;
		menu.switchPane(Constants.PRODUTO_FORM);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableViewProduto.setItems(getProdutos());
		initTable();
	}

	 
	private void initTable() {
		
		colunmCodigoProduto.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("codigoProduto"));
		colunmNomeProduto.setCellValueFactory(new PropertyValueFactory<Produto, String>("nomeProduto"));
		colunmFornecedor.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigoFornecedor"));
		colunmCategoria.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigoCategoria"));
		colunmQuantidade.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidadeProduto"));
		colunmValorCompra.setCellValueFactory(new PropertyValueFactory<Produto, BigDecimal>("valorCompra"));
		colunmValorVenda.setCellValueFactory(new PropertyValueFactory<Produto, BigDecimal>("valorVenda"));
		colunmSubCategoria.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigoCategoria"));
		colunmDataVencimento.setCellValueFactory(new PropertyValueFactory<Produto, LocalDate>("dataCadastro"));
		
		
		Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> cellFactory = new Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>>() {
			@Override
			public TableCell<Produto, Void> call(final TableColumn<Produto, Void> param) {
				return new TableCell<Produto, Void>() {

					private final Button btn = new Button("Visualizar");
					//ProdutoFormController c =  new ProdutoFormController();
					
					{
						btn.setOnAction((ActionEvent event) -> {
							selectedProduto = getTableView().getItems().get(getIndex());
							SiderMenuController menu = new SiderMenuController();
							/*ProdutoModel data = getTableView().getItems().get(getIndex());
							
							c.initData(data);
							*/
							menu.switchPane(Constants.PRODUTO_FORM);
							
							try {
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							
							setGraphic(btn);
						}
					}
				};
			}
		};
		
		
		FilteredList<Produto> dadosFiltrados = new FilteredList<Produto>(getProdutos(), p -> true);

		txtFilterProduto.textProperty().addListener((observable, oldValue, newValue) -> {

			dadosFiltrados.setPredicate(produto -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				if (produto.getNomeProduto().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(produto.getDataCadastro()).indexOf(newValue) != -1)
					return true;
				else
					return false;
			});
		});

		SortedList<Produto> sortedData = new SortedList<>(dadosFiltrados);

		sortedData.comparatorProperty().bind(tableViewProduto.comparatorProperty());


		tableViewProduto.setItems(sortedData);

		
		colunmOperacao.setCellFactory(cellFactory);
		
		
	}
	
	public ObservableList<Produto> getProdutos() {
		ObservableList<Produto> produtos = FXCollections.observableArrayList();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		List<Produto> listaProdutos = produtoDAO.listarProdutos();
		
		for (Produto produto : listaProdutos) {
			
			produto.getCodigoProduto();
			produto.getNomeProduto();
			produto.getCodigoFornecedor();
			produto.getQuantidadeProduto();
			produto.getCodigoCategoria();
			produto.getStatusProduto();
			produto.getValorCompra();
			produto.getValorVenda();
			produto.getDataCadastro();
			produtos.add(produto);
		}

		return produtos;

	}
}
