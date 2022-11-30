package br.com.floricultura.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.floricultura.model.Fornecedor;
import br.com.floricultura.dao.FornecedorDAO;
import br.com.floricultura.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class FornecedorController implements Initializable {

	@FXML
	private Button btnAddFornecedor;

	@FXML
	private TableColumn<Fornecedor, Integer> columnCodigo;

	@FXML
	private TableColumn<Fornecedor, String> columnBairro;

	@FXML
	private TableColumn<Fornecedor, String> columnEmail;

	@FXML
	private TableColumn<Fornecedor, String> columnCelular;

	@FXML
	private TableColumn<Fornecedor, String> columnCidade;

	@FXML
	private TableColumn<Fornecedor, String> columnCpfCnpj;

	@FXML
	private TableColumn<Fornecedor, String> columnEndereco;

	@FXML
	private TableColumn<Fornecedor, String> columnNomeFantasia;

	@FXML
	private TableColumn<Fornecedor, Integer> columnNumero;

	@FXML
	private TableColumn<Fornecedor, Void> columnOperacao;

	@FXML
	private TableColumn<Fornecedor, String> columnRazaoSocial;

	@FXML
	private AnchorPane funcionarioView;

	@FXML
	private TableView<Fornecedor> fornecedorTableView;

	@FXML
	private TextField txtFilter;

	public static Fornecedor selectedFornecedor;

	@FXML
	void handleBntAddFornecedorAction(ActionEvent event) {
		SiderMenuController menu = new SiderMenuController();
		selectedFornecedor = null;
		menu.switchPane(Constants.FORNECEDOR_FORM);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		fornecedorTableView.setItems(getFornecedores());
		initTable();
	}

	private void initTable() {

		columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoFornecedor"));
		columnCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
		columnRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
		columnNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		// columnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
		// columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		// columnEndereco.setCellValueFactory(new PropertyValueFactory<>("logradouro"));

		// columnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
		// columnCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));

		Callback<TableColumn<Fornecedor, Void>, TableCell<Fornecedor, Void>> cellFactory = new Callback<TableColumn<Fornecedor, Void>, TableCell<Fornecedor, Void>>() {
			@Override
			public TableCell<Fornecedor, Void> call(final TableColumn<Fornecedor, Void> param) {
				return new TableCell<Fornecedor, Void>() {

					private final Button btn = new Button("Visualizar");
					// FXMLFornecedorFormController c = new FXMLFornecedorFormController();

					{
						btn.setOnAction((ActionEvent event) -> {
							selectedFornecedor = getTableView().getItems().get(getIndex());
							SiderMenuController menu = new SiderMenuController();

							menu.switchPane(Constants.FORNECEDOR_FORM);

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

		FilteredList<Fornecedor> dadosFiltrados = new FilteredList<Fornecedor>(getFornecedores(), p -> true);

		txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {

			dadosFiltrados.setPredicate(fornecedor -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				if (fornecedor.getNomeFantasia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (fornecedor.getNomeFantasia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(fornecedor.getCpfCnpj()).indexOf(newValue) != -1)
					return true;
				else
					return false;
			});
		});

		SortedList<Fornecedor> sortedData = new SortedList<>(dadosFiltrados);

		sortedData.comparatorProperty().bind(fornecedorTableView.comparatorProperty());

		fornecedorTableView.setItems(sortedData);

		columnOperacao.setCellFactory(cellFactory);

	}

	public ObservableList<Fornecedor> getFornecedores() {
		FornecedorDAO dao = new FornecedorDAO();

		ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

		List<Fornecedor> listaProdutos = dao.listarFornecedores();
		if (listaProdutos != null) {
			for (Fornecedor fornecedor : listaProdutos) {
				fornecedor.getCodigoFornecedor();
				fornecedor.getRazaoSocial();
				fornecedor.getNomeFantasia();
				fornecedor.getCpfCnpj();

				fornecedores.addAll(fornecedor);
			}
		}

		return fornecedores;

	}

}
