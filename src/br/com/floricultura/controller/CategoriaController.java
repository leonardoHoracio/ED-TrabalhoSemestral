package br.com.floricultura.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.floricultura.dao.CategoriaDAO;
import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.model.Categoria;
import br.com.floricultura.utils.Constants.OperationType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class CategoriaController implements Initializable {
	
	@FXML
	private StackPane categoriaAnchorPane;

	@FXML
	private JFXButton btnAlterar;
	

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private TableColumn<Categoria, Integer> columnCodigo;

	@FXML
	private TableColumn<Categoria, String> columnDescricao;

	@FXML
	private TableColumn<Categoria, Void> columnOperacao;

	@FXML
	private TableView<Categoria> tableViewCategoria;

	@FXML
	private TextField txtCodigoCategoria;

	@FXML
	private TextField txtDescricaoCategoria;

	@FXML
	private TextField txtFiltro;

	private OperationType flagOperacao = OperationType.CREATE;
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableViewCategoria.setItems(getCategorias());
		initTable();
	}
	
	

	@FXML
	void onMouseClickedAlterar(MouseEvent event) {
		flagOperacao = OperationType.UPDATE;
		habilitarCampos();
	}

	@FXML
	void onMouseClickedExcluir(MouseEvent event) {
		flagOperacao = OperationType.DELETE;
		operation();
	}

	@FXML
	void onMouseClickedLimpar(MouseEvent event) {
		flagOperacao = OperationType.READ;
		desabilitarCampos();
	}

	@FXML
	void onMouseClickedSalvar(MouseEvent event) {
		operation();
	}

	private void initTable() {

		columnCodigo.setCellValueFactory(new PropertyValueFactory<Categoria, Integer>("codigoCategoria"));
		columnDescricao.setCellValueFactory(new PropertyValueFactory<Categoria, String>("descricaoCategoria"));

		Callback<TableColumn<Categoria, Void>, TableCell<Categoria, Void>> cellFactory = new Callback<TableColumn<Categoria, Void>, TableCell<Categoria, Void>>() {
			@Override
			public TableCell<Categoria, Void> call(final TableColumn<Categoria, Void> param) {
				return new TableCell<Categoria, Void>() {

					private final Button btn = new Button("Preencher");

					{
						btn.setOnAction((ActionEvent event) -> {
							Categoria selectedCategoria = getTableView().getItems().get(getIndex());
							txtCodigoCategoria.setText(String.valueOf(selectedCategoria.getCodigoCategoria()));
							txtDescricaoCategoria.setText(selectedCategoria.getDescricaoCategoria());

							desabilitarCampos();
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

		FilteredList<Categoria> dadosFiltrados = new FilteredList<Categoria>(getCategorias(), c -> true);

		txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {

			dadosFiltrados.setPredicate(categoria -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				if (categoria.getDescricaoCategoria().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});

		SortedList<Categoria> sortedData = new SortedList<>(dadosFiltrados);

		sortedData.comparatorProperty().bind(tableViewCategoria.comparatorProperty());

		tableViewCategoria.setItems(sortedData);

		columnOperacao.setCellFactory(cellFactory);
	}

	private ObservableList<Categoria> getCategorias() {

		ObservableList<Categoria> categorias = FXCollections.observableArrayList();
		CategoriaDAO dao = new CategoriaDAO();

		List<Categoria> listaCategoria = dao.listarCategoria();
		if (listaCategoria != null) {
			for (Categoria categoria : dao.listarCategoria()) {

				categoria.getCodigoCategoria();
				categoria.getDescricaoCategoria();
				categoria.getStatusCategoria();

				categorias.add(categoria);
			}
		}

		return categorias;

	}

	private Categoria getCategoria() {
		Categoria categoria = new Categoria();

		if (flagOperacao != OperationType.CREATE) {
			categoria.setCodigoCategoria(Integer.parseInt(txtCodigoCategoria.getText()));
		}

		categoria.setDescricaoCategoria(txtDescricaoCategoria.getText());

		return categoria;
	}

	void operation() {
		CategoriaDAO dao = new CategoriaDAO();

		switch (flagOperacao) {

		case UPDATE:
			dao.alterarCategoria(getCategoria());
			break;

		case DELETE:
			dao.excluirCategoria(getCategoria().getCodigoCategoria());
			break;

		default:
			dao.incluirCategoria(getCategoria());
			break;
		}
		initTable();
		limparCampos();
	}

	void limparCampos() {
		txtCodigoCategoria.setText(null);
		txtDescricaoCategoria.setText(null);
	}

	private void habilitarCampos() {

		txtDescricaoCategoria.setDisable(false);
		txtFiltro.setDisable(false);
	}

	private void desabilitarCampos() {

		txtDescricaoCategoria.setDisable(true);
		txtFiltro.setDisable(true);
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
	
	private void notifyDatachangeListeners() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}
	}

}
