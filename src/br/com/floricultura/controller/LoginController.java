package br.com.floricultura.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.floricultura.dao.LoginDAO;
import br.com.floricultura.model.Usuario;
import br.com.floricultura.utils.Alerts;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtSenha;
	
	@FXML
	private Button btnLogar;
	
	@FXML
	private void onActionBtnLogar(ActionEvent event) {
		/*if(txtUsuario.getText() == null || txtSenha.getText() == null){
			
		}else {
			
		}*/
		
		if(isLogin()) {
			Stage stage = new Stage();
			StackPane root = null;
			try {
				root = FXMLLoader.load(getClass().getResource(Constants.MAINVIEW));
				
				Scene scene = new Scene(root);
				scene.setFill(Color.TRANSPARENT);
				//stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(scene);
				
				stage.show();
				Utils.currentStage(event).close();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			
		}else {
			Alerts.showAlert("Login",  null, "Usuário ou Senha inválido", AlertType.INFORMATION);
		}
	}
	
	private boolean isLogin() {
		LoginDAO dao = new LoginDAO();
		Usuario.usuarioLogado = dao.logar(txtUsuario.getText(), txtSenha.getText());
		
		return Usuario.usuarioLogado != null;
			
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		
	}

}
