package br.com.floricultura.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import br.com.floricultura.dao.UsuarioDAO;
import br.com.floricultura.model.Usuario;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.Constants.NivelAcesso;
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
import javafx.util.Callback;

public class UsuarioController implements Initializable {

	@FXML
	private Button btnUsuario;

	@FXML
	private TableColumn<Usuario, Integer> columnCodigo;

	@FXML
	private TableColumn<Usuario, LocalDate> columnDataCadastro;

	@FXML
	private TableColumn<Usuario, String> columnEmail;

	@FXML
	private TableColumn<Usuario, String> columnLogin;

	@FXML
	private TableColumn<Usuario, NivelAcesso> columnNivelAcesso;

	@FXML
	private TableColumn<Usuario, String> columnNomeCompleto;
	
	@FXML
	private TableColumn<Usuario, String> columnStatus;

	@FXML
	private TableColumn<Usuario, Void> columnOperacao;

	@FXML
	private TextField txtFilter;
			
	@FXML
	private TableView<Usuario> usuarioTableView;

	public static Usuario selectedUsuario;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		usuarioTableView.setItems(getUsuarios());
		initTable();

	}
	
    @FXML
    void onMouseClickedAddUsuario(MouseEvent event) {
    	SiderMenuController menu = new SiderMenuController();
    	selectedUsuario = null;
		menu.switchPane(Constants.USUARIO_FORM);
    }

	private void initTable() {
		columnCodigo.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("codigoUsuario"));
		columnLogin.setCellValueFactory(new PropertyValueFactory<Usuario, String>("login"));
		columnNomeCompleto.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nomeCompleto"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
		columnDataCadastro.setCellValueFactory(new PropertyValueFactory<Usuario, LocalDate>("dataCadastro"));
		columnNivelAcesso.setCellValueFactory(new PropertyValueFactory<Usuario, NivelAcesso>("nivelAcesso"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<Usuario, String>("statusUsuario"));
		
		Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> cellFactory = new Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>>() {
			@Override
			public TableCell<Usuario, Void> call(final TableColumn<Usuario, Void> param) {
				return new TableCell<Usuario, Void>() {

					private final Button btn = new Button("Visualizar");
					//ProdutoFormController c =  new ProdutoFormController();
					
					{
						btn.setOnAction((ActionEvent event) -> {
							selectedUsuario = getTableView().getItems().get(getIndex());
							SiderMenuController menu = new SiderMenuController();
							/*ProdutoModel data = getTableView().getItems().get(getIndex());
							
							c.initData(data);
							*/
							menu.switchPane(Constants.USUARIO_FORM);
							
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
			}};
			
			FilteredList<Usuario> dadosFiltrados = new FilteredList<Usuario>(getUsuarios(), u -> true);

			txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {

				dadosFiltrados.setPredicate(usuario -> {

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
	
					String lowerCaseFilter = newValue.toLowerCase();
					if (usuario.getNomeCompleto().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (usuario.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1)
						return true;
					else
						return false;
				});
			});

			SortedList<Usuario> sortedData = new SortedList<>(dadosFiltrados);

			sortedData.comparatorProperty().bind(usuarioTableView.comparatorProperty());
			
			usuarioTableView.setItems(sortedData);
			
			columnOperacao.setCellFactory(cellFactory);
	}

	private ObservableList<Usuario> getUsuarios() {
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> listaUsuarios = dao.listarUsuarios();
		
		for (Usuario usuario : listaUsuarios) {
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
}
