package br.com.floricultura.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.floricultura.dao.UsuarioDAO;
import br.com.floricultura.model.Usuario;
import br.com.floricultura.utils.Alerts;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.ValidatorField;
import br.com.floricultura.utils.Constants.NivelAcesso;
import br.com.floricultura.utils.Constants.OperationType;
import br.com.floricultura.utils.EnviarEmail;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;

import javafx.scene.control.DatePicker;

public class UsuarioFormController implements Initializable {
	@FXML
	private PasswordField txtSenha;
	
	@FXML
	private PasswordField txtConfirmaSenha;
	
	@FXML
	private TextField txtNomeCompleto;
	
	@FXML
	private TextField txtLogin;
	
	@FXML
	private DatePicker txtDataCadastro;
	
	@FXML
	private TextField txtCodigo;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private ComboBox<NivelAcesso> cmbNivelAcesso;
	
	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnAlterar;
	
	@FXML
	private Button btnExcluir;
	
	@FXML
	private Button btnLimpar;
	
	@FXML
	private Button btnVoltar;

	private OperationType flagOperacao = OperationType.CREATE;

	@FXML
	private void onActionSalvar(ActionEvent event) throws Exception {
		operation();
	}

	@FXML
	private void onActionAlterar(ActionEvent event) {
		flagOperacao = OperationType.UPDATE;
		habilitarCampos();
	}

	@FXML
	private void onActionExcluir(ActionEvent event) throws Exception {
		flagOperacao = OperationType.DELETE;
		operation();
	}

	@FXML
	private void onActionLimpar(ActionEvent event) {
		flagOperacao = OperationType.READ;
	
		if(UsuarioController.selectedUsuario == null) {
			limparCampos();
		}
		
	}

	@FXML
	private void onActionVoltar(ActionEvent event) {
		EnviarEmail email = new EnviarEmail();
		email.enviarEmail();
		SiderMenuController menu = new SiderMenuController();
		menu.switchPane(Constants.USUARIO);
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Usuario usuario = UsuarioController.selectedUsuario;
		if(Usuario.usuarioLogado.getNivelAcesso().equalsIgnoreCase("OPERACIONAL")) {
			setCampos(Usuario.usuarioLogado);
		}else if (usuario != null) {
			setCampos(usuario);
			desabilitarCampos();
			flagOperacao = OperationType.UPDATE;
		} else {
			habilitarCampos();

		}

		cmbNivelAcesso.getItems().addAll(NivelAcesso.ADMINISTRADOR, NivelAcesso.CAIXA, NivelAcesso.OPERACIONAL);
		cmbNivelAcesso.getSelectionModel().select(0);
		txtDataCadastro.setValue(LocalDate.now());
		txtDataCadastro.getEditor().setStyle("-fx-alignment: center;");
		setTextFieldMaxLength();

		txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				{
					if (!newPropertyValue && txtEmail.getText().length() > 0) {

						if (!ValidatorField.validaEmail(txtEmail.getText())) {
							Alerts.showAlert("E-mail", null, "E-mail inválido", AlertType.INFORMATION);
						}
					}
				}
			}

		});
	}

	void operation() throws Exception {

		UsuarioDAO dao = new UsuarioDAO();

		if (camposVazios().isEmpty()) {
			switch (flagOperacao) {

			case UPDATE:
				dao.alterarUsuario(getUsuarioDataForm());
				voltarTela();
				break;

			case DELETE:
				dao.excluirUsuario(getUsuarioDataForm().getCodigoUsuario());
				voltarTela();
				break;
			case CREATE:
				dao.incluirUsuario(getUsuarioDataForm());
				break;

			default:
				Alerts.showAlert("Erro de Operação", null, "Erro de operações de Inclusão, Exclusão e Alterção", AlertType.ERROR);
				break;
			}

			limparCampos();

		} else {
			Alerts.showAlert("Campos", null, "Verfique os campos em vermelho.", AlertType.INFORMATION);
		}

	}

	private void setTextFieldMaxLength() {

		ValidatorField.limitarTamanhoCampo(txtLogin, 20);
		ValidatorField.limitarTamanhoCampo(txtNomeCompleto, 50);
		ValidatorField.limitarTamanhoCampo(txtSenha, 8);
		ValidatorField.limitarTamanhoCampo(txtConfirmaSenha, 8);
		ValidatorField.limitarTamanhoCampo(txtEmail, 50);

	}

	private void limparCampos() {
		txtCodigo.setText("");
		txtLogin.setText("");
		txtNomeCompleto.setText("");
		txtSenha.setText("");
		txtConfirmaSenha.setText("");
		txtEmail.setText("");
		cmbNivelAcesso.getSelectionModel().select(0);
		txtDataCadastro.setValue(LocalDate.now());
	}

	private void habilitarCampos() {

		txtSenha.setDisable(false);
		txtConfirmaSenha.setDisable(false);
		txtLogin.setDisable(false);
		txtNomeCompleto.setDisable(false);
		txtEmail.setDisable(false);
		cmbNivelAcesso.setDisable(false);

	}

	private void desabilitarCampos() {

		txtSenha.setDisable(true);
		txtConfirmaSenha.setDisable(true);
		txtNomeCompleto.setDisable(true);
		txtLogin.setDisable(true);
		txtEmail.setDisable(true);
		cmbNivelAcesso.setDisable(true);

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

	private void setCampos(Usuario usuario) {

		txtCodigo.setText(String.valueOf(usuario.getCodigoUsuario()));
		txtLogin.setText(usuario.getLogin());
		txtNomeCompleto.setText(usuario.getNomeCompleto());
		txtSenha.setText(usuario.getSenha());
		txtConfirmaSenha.setText(usuario.getSenha());
		txtEmail.setText(usuario.getEmail());
		txtDataCadastro.setValue(usuario.getDataCadastro());
		// cmbNivelAcesso.setValue(usuario.getNivelAcesso());

	}

	private List<TextField> camposVazios() {
		List<TextField> listField = new ArrayList<TextField>();
		List<TextField> listErrors = new ArrayList<TextField>();

		listField.add(txtSenha);
		listField.add(txtConfirmaSenha);
		listField.add(txtNomeCompleto);
		listField.add(txtLogin);
		listField.add(txtEmail);

		for (TextField textField : listField) {

			if (textField.getText() == null || textField.getText().trim().isEmpty()) {
				textField.setStyle("-fx-border-color: #FD0E01;" + "-fx-border-width: 1; ");
				listErrors.add(textField);
			} else {
				textField.setStyle(null);
			}

		}
		return listErrors;
	}

	private Usuario getUsuarioDataForm() throws Exception {
		Usuario usuario = new Usuario();

		if (flagOperacao != OperationType.CREATE) {
			usuario.setCodigoUsuario(Integer.parseInt(txtCodigo.getText()));
		}
		if (validarSenhas()) {
			usuario.setSenha(txtSenha.getText());
		} else {
			Alerts.showAlert(null, null, null, null);
			throw new Exception("Senhas não são iguais!");
		}
		usuario.setLogin(txtLogin.getText());
		usuario.setEmail(txtEmail.getText());
		usuario.setNomeCompleto(txtNomeCompleto.getText());
		usuario.setNivelAcesso(cmbNivelAcesso.getValue().toString());

		return usuario;
	}

	private boolean validarSenhas() {
		return txtSenha.getText().equalsIgnoreCase(txtConfirmaSenha.getText());
	}

	private void voltarTela() {
		SiderMenuController menu = new SiderMenuController();
		menu.switchPane(Constants.USUARIO);
	}

}
