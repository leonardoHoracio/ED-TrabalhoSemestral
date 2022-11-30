package br.com.floricultura.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.floricultura.dao.UsuarioDAO;
import br.com.floricultura.model.Usuario;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.Constants.NivelAcesso;
import br.com.floricultura.utils.NomesTelas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class SiderMenuController implements Initializable {
	
	@FXML
    private JFXButton btnCategoria;

    @FXML
    private JFXButton btnCliente;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEstoque;

    @FXML
    private JFXButton btnFornecedor;

    @FXML
    private JFXButton btnUsuario;

    @FXML
    private JFXButton btnProduto;

    @FXML
    private JFXButton btnRelatorios;

    @FXML
    private JFXButton btnVendas;
    @FXML
    private MenuItem menuItemUsuarios;
    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	//switchPane(Constants.USUARIO);
    	UsuarioDAO dao = new UsuarioDAO();
		Usuario.usuarioLogado = dao.listarUsuarios().get(1);
    	controleAcesso(Usuario.usuarioLogado.getNivelAcesso().toUpperCase());
    	
	}

    @FXML
    void onActionCategoria(ActionEvent event) {
    	switchPane(Constants.CATEGORIA);
    	
    	
    }

    @FXML
    void onActionCliente(ActionEvent event) {

    }

    @FXML
    void onActionDashboard(ActionEvent event) {
    	
    }

    @FXML
    void onActionEstoque(ActionEvent event) {
    	
    }
    
    @FXML
    void onActionProduto(ActionEvent event) {
    	switchPane(Constants.PRODUTO);
    	
    }

    @FXML
    void onActionFornecedor(ActionEvent event) {
    	switchPane(Constants.FORNECEDOR);
    }

    @FXML
    void onActionUsuario(ActionEvent event) {
    	
    	if(Usuario.usuarioLogado.getNivelAcesso().equalsIgnoreCase("ADMINISTRADOR")) {
    		switchPane(Constants.USUARIO);
    		
    	}else {
    		switchPane(Constants.USUARIO_FORM);
    
    	}
    }

    @FXML
    void onActionRelatorios(ActionEvent event) {
    	switchPane(Constants.RELATORIOS);
    }

    @FXML
    void onActionVendas(ActionEvent event) {
    	switchPane(Constants.VENDA);
    }

	
	public void switchPane(String pane) {
		
		MainController.temporaryPane.getChildren().clear();
		
		try {
			StackPane pane2 = FXMLLoader.load(getClass().getResource(pane));
			
			ObservableList<Node> nodeList= pane2.getChildren();
			MainController.temporaryPane.getChildren().setAll(nodeList);
		
			MainController.nomeTela = NomesTelas.CATEGORIAS;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void controleAcesso(String nivelAcesso) {
		
    	switch (nivelAcesso) {
    	
		case "ADMINISTRADOR":
			
			
			break;
		
		case "OPERACIONAL":
			/*btnDashboard.setDisable(true);
			btnVendas.setDisable(true);
			btnEstoque.setDisable(true);
			btnProduto.setDisable(true);
			btnFornecedor.setDisable(true);
			btnCliente.setDisable(true);
			btnCategoria.setDisable(true);*/
			//btnUsuario.setDisable(true);
			btnRelatorios.setDisable(true);
			
			break;

		default:
			break;
		}
    	
	}
	
}
