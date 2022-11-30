package br.com.floricultura.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import com.jfoenix.controls.JFXButton;

import br.com.floricultura.dao.FornecedorDAO;
import br.com.floricultura.model.Contato;
import br.com.floricultura.model.Endereco;
import br.com.floricultura.model.Fornecedor;
import br.com.floricultura.utils.Alerts;
import br.com.floricultura.utils.BuscarCep;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.MaskFieldUtil;
import br.com.floricultura.utils.Utils;
import br.com.floricultura.utils.ValidatorCpfCnpj;
import br.com.floricultura.utils.ValidatorField;
//import br.com.floricultura.utils.MaskedTextField;
//import br.com.floricultura.utils.TextFieldFormatter;
import br.com.floricultura.utils.Constants.OperationType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class FornecedorFormController implements Initializable {

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private ComboBox<String> cmbUF;
	
	@FXML
	private ComboBox<String> cmbStatus;
	
    @FXML
    private ComboBox<String> cmbTipoEmpresa;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtCEP;

	@FXML
	private TextField txtCelular;
	
	@FXML
	private TextField txtCelular1;

	@FXML
	private TextField txtCidade;

	@FXML
	private TextField txtCodigoFornecedor;

	@FXML
	private TextField txtComplemento;

	@FXML
	private TextField txtCpfCnpj;

	@FXML
	private DatePicker txtDataInclusao;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtInscEstadual;

	@FXML
	private TextField txtInscMunincipal;

	@FXML
	private TextField txtLogradouro;

	@FXML
	private TextField txtNomeFantasia;

	@FXML
	private TextField txtNumero;

	@FXML
	private TextField txtRazaoSocial;

	@FXML
	private TextField txtTelefone;
	
	@FXML
	private TextField txtTelefone1;
    
    
    @FXML
    private Label lblError;

	private OperationType flagOperacao = OperationType.CREATE;
	ValidationSupport validation = new ValidationSupport();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Fornecedor fornecedor = FornecedorController.selectedFornecedor;
		if (fornecedor != null) {
			setCampos(fornecedor);
			desabilitarCampos();
			flagOperacao = OperationType.UPDATE;
		} else {
			habilitarCampos();

		}
		
		setTextFieldMaxLength();
		
		//testeInclusao();
		
		MaskFieldUtil.cepField(txtCEP);
		MaskFieldUtil.numericField(txtNumero);
		MaskFieldUtil.cellField(txtCelular);
		MaskFieldUtil.cellField(txtCelular1);
		MaskFieldUtil.phoneField(txtTelefone);
		MaskFieldUtil.phoneField(txtTelefone1);
		MaskFieldUtil.cpfCnpjField(txtCpfCnpj);
		
		
		txtDataInclusao.setValue(LocalDate.now());
		txtDataInclusao.setDisable(true);
		String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
				"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
		cmbUF.getItems().addAll(estados);
		
		cmbUF.getSelectionModel().select(0);
		
		cmbStatus.getItems().addAll("ATIVO", "INATIVO");
		
		cmbStatus.getSelectionModel().select(0);
		
		cmbTipoEmpresa.getItems().addAll("Sede", "Filial");
		
		cmbTipoEmpresa.getSelectionModel().select(0);
		
		validation.setErrorDecorationEnabled(false);
		txtCpfCnpj.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				{
					if (!newPropertyValue && txtCpfCnpj.getText().length() > 0) {
						validate();
					}
				}
			}

		});
		
		txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				{
					if (!newPropertyValue && txtEmail.getText().length() > 0) {
						
						if(!ValidatorField.validaEmail(txtEmail.getText())){
							Alerts.showAlert("E-mail", null, "E-mail inválido", AlertType.INFORMATION);
						}
					}
				}
			}

		});
		
		
	
		txtCEP.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				{
					if (!newPropertyValue && txtCEP.getText().length() > 0) {
						validateCep();
					}
				}
			}

		});

	}
/*
	private void testeInclusao() {
		txtRazaoSocial.setText("Flor de Lis LTDA");
		txtNomeFantasia.setText("Flor de Lis");
		String cnpjTeste = "23466587000124";
	 
		String cnpj = cnpjTeste.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
		txtCpfCnpj.setText(cnpj);
		
		MaskFieldUtil.cnpjField(txtCpfCnpj);
		txtInscEstadual.setText("123456");
		txtInscMunincipal.setText("78931011");
		txtEmail.setText("teste@teste.com");
	
		txtCEP.setText("02073120");
		txtLogradouro.setText("Rua Emílio Rodrigues");
		txtNumero.setText(String.valueOf(10));
		txtCidade.setText("São Paulo");
		txtBairro.setText("Vila Paiva");
		txtTelefone.setText("1122222222");
		txtCelular.setText("11999999999");
		cmbUF.setValue("SP");
		
	}
*/
	private void setTextFieldMaxLength() {
		
		ValidatorField.limitarTamanhoCampo(txtCpfCnpj, 18);
		ValidatorField.limitarTamanhoCampo(txtRazaoSocial, 50);
		ValidatorField.limitarTamanhoCampo(txtNomeFantasia, 50);
		ValidatorField.limitarTamanhoCampo(txtCEP, 9);
		ValidatorField.limitarTamanhoCampo(txtLogradouro, 50);
		ValidatorField.limitarTamanhoCampo(txtNumero, 4);
		ValidatorField.limitarTamanhoCampo(txtComplemento, 50);
		ValidatorField.limitarTamanhoCampo(txtBairro, 30);
		ValidatorField.limitarTamanhoCampo(txtCidade, 30);
		ValidatorField.limitarTamanhoCampo(txtInscEstadual, 20);
		ValidatorField.limitarTamanhoCampo(txtInscMunincipal, 20);
		ValidatorField.limitarTamanhoCampo(txtEmail, 50);
		ValidatorField.limitarTamanhoCampo(txtTelefone, 13);
		ValidatorField.limitarTamanhoCampo(txtTelefone1, 13);
		ValidatorField.limitarTamanhoCampo(txtCelular, 14);
		ValidatorField.limitarTamanhoCampo(txtCelular1, 14);
		
		
	}

	private void validate() {

		String validator = ValidatorCpfCnpj.removeCaracteresEspeciais(txtCpfCnpj.getText());

		if (validator.length() == 11) {
			if (!ValidatorCpfCnpj.isCPF(validator)) {
				Alerts.showAlert("CPF", null, "Inválido", AlertType.INFORMATION);
			} 
		} else if (validator.length() == 14) {
			if (!ValidatorCpfCnpj.isCNPJ(validator)) {
				Alerts.showAlert("CNPJ", null, "Inválido", AlertType.INFORMATION);
			} 
		} else {
			Alerts.showAlert("CPF/CNPJ", null, "Inválido", AlertType.INFORMATION);
		}
		
	}
	
	private void validateCep() {

		Endereco cep = BuscarCep.buscarCep(txtCEP.getText());
		
		txtBairro.setText(cep.getBairro());
		txtLogradouro.setText(cep.getLogradouro());
		txtCidade.setText(cep.getCidade());
		cmbUF.setValue(cep.getUf().toUpperCase());
		
		
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
	}

	@FXML
	void onMouseClickedSalvar(MouseEvent event) {
		operation();

	}

	@FXML
	void onMouseClickedVoltar(MouseEvent event) {
		voltarTela();
	}

	private Fornecedor getFornecedor() {
		Fornecedor fornecedor = new Fornecedor();
		Endereco endereco = new Endereco();
		
		List<Contato> listaContato = new ArrayList<Contato>();

		if (flagOperacao != OperationType.CREATE) {
			fornecedor.setCodigoFornecedor(Integer.parseInt(txtCodigoFornecedor.getText()));
		}
		 
		fornecedor.setNomeFantasia(txtNomeFantasia.getText());
		fornecedor.setRazaoSocial(txtRazaoSocial.getText());
		fornecedor.setCpfCnpj(Utils.formataDados(txtCpfCnpj.getText()));
		fornecedor.setInscEstadual(txtInscEstadual.getText());
		fornecedor.setInscMunicipal(txtInscMunincipal.getText());
		fornecedor.setDataInclusao(txtDataInclusao.getValue());
		fornecedor.setEmail(txtEmail.getText());
		fornecedor.setStatusFornecedor(cmbStatus.getValue());
		fornecedor.setTipoEmpresa(cmbTipoEmpresa.getValue());

		endereco.setCep(Utils.formataDados(txtCEP.getText()));
		endereco.setLogradouro(txtLogradouro.getText());
		endereco.setNumero(Integer.parseInt(txtNumero.getText()));
		endereco.setComplemento(txtComplemento.getText());
		endereco.setBairro(txtBairro.getText());
		endereco.setCidade(txtCidade.getText());
		endereco.setUf(cmbUF.getValue().toString());
		
		if(!txtTelefone.getText().isEmpty()) {
			Contato contato = new Contato();
			contato.setNumeroContato(Utils.formataDados(txtTelefone.getText()));
			contato.setTipo("FIXO");
			contato.setStatusContato("ATIVO");
			listaContato.add(contato);
		}
		
		if(!txtTelefone1.getText().isEmpty()) {
			Contato contato = new Contato();
			contato.setNumeroContato(Utils.formataDados(txtTelefone1.getText()));
			contato.setTipo("FIXO");
			contato.setStatusContato("ATIVO");
			listaContato.add(contato);
		}
		
	
		if(!txtCelular.getText().isEmpty()) {
			Contato contato = new Contato();
			contato.setNumeroContato(Utils.formataDados(txtCelular.getText()));
			contato.setTipo("MOVEL");
			contato.setStatusContato("ATIVO");
			listaContato.add(contato);
		}
		
		if(!txtCelular1.getText().isEmpty()) {
			Contato contato = new Contato();
			contato.setNumeroContato(Utils.formataDados(txtCelular1.getText()));
			contato.setTipo("MOVEL");
			contato.setStatusContato("ATIVO");
			listaContato.add(contato);
		}
		
		fornecedor.setEndereco(endereco);
		fornecedor.setListaContato(listaContato);

		return fornecedor;
	}

	void operation() {
		FornecedorDAO dao = new FornecedorDAO();
		System.out.println(camposVazios().isEmpty());
		if(camposVazios().isEmpty()) {
			switch (flagOperacao) {

			case UPDATE:
				dao.alterarFornecedor(getFornecedor());
				break;

			case DELETE:
				dao.excluirFornecedor(getFornecedor().getCodigoFornecedor());
				break;

			default:
				dao.incluirFornecedor(getFornecedor());
				break;
			}
			
			limparCampos();

		}else {
			Alerts.showAlert("Campos", null, "Verfique os campos em vermelho.", AlertType.INFORMATION);
		}

	}

	void limparCampos() {
		txtCodigoFornecedor.setText("");
		txtRazaoSocial.setText("");
		txtNomeFantasia.setText("");
		txtCpfCnpj.setText("");
		txtInscEstadual.setText("");
		txtInscMunincipal.setText("");

		txtCEP.setText("");
		txtLogradouro.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		txtCidade.setText("");
		txtBairro.setText("");
		// UF
		txtCelular.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
		txtDataInclusao.setValue(LocalDate.now());
	}

	private void habilitarCampos() {

		txtRazaoSocial.setDisable(false);
		txtNomeFantasia.setDisable(false);
		txtCpfCnpj.setDisable(false);
		txtInscEstadual.setDisable(false);
		txtInscMunincipal.setDisable(false);
		txtCEP.setDisable(false);
		txtLogradouro.setDisable(false);
		txtNumero.setDisable(false);
		txtComplemento.setDisable(false);
		txtCidade.setDisable(false);
		txtBairro.setDisable(false);
		txtCelular.setDisable(false);
		txtCelular1.setDisable(false);
		txtTelefone.setDisable(false);
		txtTelefone1.setDisable(false);
		txtEmail.setDisable(false);
		txtDataInclusao.setDisable(false);
		
		cmbUF.setDisable(true);
		cmbStatus.setDisable(true);
		cmbTipoEmpresa.setDisable(true);

	}

	private void desabilitarCampos() {

		txtRazaoSocial.setDisable(true);
		txtNomeFantasia.setDisable(true);
		txtCpfCnpj.setDisable(true);
		txtInscEstadual.setDisable(true);
		txtInscMunincipal.setDisable(true);
		txtCEP.setDisable(true);
		txtLogradouro.setDisable(true);
		txtNumero.setDisable(true);
		txtComplemento.setDisable(true);
		txtCidade.setDisable(true);
		txtBairro.setDisable(true);
		txtCelular.setDisable(true);
		txtCelular1.setDisable(true);
		txtTelefone.setDisable(true);
		txtTelefone1.setDisable(true);
		txtEmail.setDisable(true);
		txtDataInclusao.setDisable(true);
		
		cmbUF.setDisable(true);
		cmbStatus.setDisable(true);
		cmbTipoEmpresa.setDisable(true);

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

	private void setCampos(Fornecedor fornecedor) {

		txtCodigoFornecedor.setText(String.valueOf(fornecedor.getCodigoFornecedor()));
		txtRazaoSocial.setText(fornecedor.getRazaoSocial());
		txtNomeFantasia.setText(fornecedor.getNomeFantasia());
		
		if(fornecedor.getCpfCnpj().length() == 14) {
			String cnpj = fornecedor.getCpfCnpj().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
			txtCpfCnpj.setText(cnpj);
		}
		
		if(fornecedor.getCpfCnpj().length() == 11) {
			String cpf = fornecedor.getCpfCnpj().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3-$4");
			txtCpfCnpj.setText(cpf);
		}
		MaskFieldUtil.cnpjField(txtCpfCnpj);
		txtInscEstadual.setText(fornecedor.getInscEstadual());
		txtInscMunincipal.setText(fornecedor.getInscMunicipal());
		txtEmail.setText(fornecedor.getEmail());
		txtDataInclusao.setValue(fornecedor.getDataInclusao());
/*
		txtCEP.setText(fornecedor.getEndereco().getCep());
		txtLogradouro.setText(fornecedor.getEndereco().getLogradouro());
		txtNumero.setText(String.valueOf(fornecedor.getEndereco().getNumero()));
		txtComplemento.setText(fornecedor.getEndereco().getComplemento());
		txtCidade.setText(fornecedor.getEndereco().getCidade());
		txtBairro.setText(fornecedor.getEndereco().getBairro());*/
		// UF
		/*
		for (int i = 0; i < fornecedor.getListaContato().size(); i ++) {
			
			if(fornecedor.getListaContato().get(i).getTipo() == "FIXO") {
				if(txtTelefone.getText().isEmpty()) {
					txtTelefone.setText(fornecedor.getListaContato().get(i).getNumeroContato());
				}else {
					txtTelefone1.setText(fornecedor.getListaContato().get(i).getNumeroContato());
				}
				
			}
			
			if(fornecedor.getListaContato().get(i).getTipo() == "MOVEL") {
				if(txtCelular.getText().isEmpty()) {
					txtCelular.setText(fornecedor.getListaContato().get(i).getNumeroContato());
				}else {
					txtCelular1.setText(fornecedor.getListaContato().get(i).getNumeroContato());
				}
				
			}
		}
	
*/
	}

	@FXML
	void onKeyReleasedCelular1(KeyEvent event) {

		/*
		 * TextFieldFormatter tff = new TextFieldFormatter();
		 * tff.setMask("(##)#####-####"); tff.setCaracteresValidos("0123456789");
		 * tff.setTf(txtCelular); tff.formatter();
		 */
	}

	@FXML
	void onKeyReleasedCpfCnpj(KeyEvent event) {
		/*
		 * txtCpfCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
		 * System.out.println(newValue); if(newValue.length() > 11) {
		 * txtCpfCnpj.setPlaceholder(' '); txtCpfCnpj.setMask("##.###.###/####-##"); }
		 * 
		 * 
		 * });
		 */

	}

	@FXML
	void onKeyTypedCpfCnpj(KeyEvent event) {
		/*
		 * System.out.println("TESTE");
		 * txtCpfCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
		 * System.out.println(newValue); if(newValue.length() > 11) {
		 * System.out.println(newValue);
		 * 
		 * txtCpfCnpj.setPlaceholder(' '); txtCpfCnpj.setMask("##.###.###/####-##");
		 * }else { System.out.println(newValue); }
		 * 
		 * 
		 * });
		 */

	}

	@FXML
	void onKeyReleasedEmail1(KeyEvent event) {

	}

	@FXML
	void onKeyReleasedInscEstadual(KeyEvent event) {

	}

	@FXML
	void onKeyReleasedIscMunicipal(KeyEvent event) {

	}

	@FXML
	void onKeyReleasedTelefone1(KeyEvent event) {

	}
	/*
	private void validationFieldsOnSubmit(TextField textField) {
		

		if(textField.getText().isBlank() || textField.getText().isEmpty()) {
			//textField.setStyle("-fx-border-color: #FD0E01;" + "-fx-border-width: 1; " + "-fx-background-color: #EF9793; ");
			textField.setStyle("-fx-border-color: #FD0E01;" + "-fx-border-width: 1; ");
			Label label = new Label();
			label.setText("Campo vazio");
			label.setLayoutX(textField.getLayoutX()+80);
			label.setLayoutY(textField.getLayoutY() + 50);
			textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					{
						if (newPropertyValue) {
							System.out.println("OI");
							
						} else {
							
						}
					}
				}

			});
			
			validation.registerValidator(txtCpfCnpj, Validator.createEmptyValidator("emptyName", Severity.ERROR));
			validation.setErrorDecorationEnabled(true);
			
		}else {

			validation.errorDecorationEnabledProperty().bind(txtCpfCnpj.focusedProperty());
			validation.redecorate();
		}
	}*/
	
	public List<TextField> camposVazios() {
		List<TextField> listField = new ArrayList<TextField>();
		List<TextField> listErrors = new ArrayList<TextField>();
		
		listField.add(txtCpfCnpj);
		listField.add(txtRazaoSocial);
		listField.add(txtNomeFantasia);
		listField.add(txtCEP);
		listField.add(txtLogradouro);
		listField.add(txtNumero);
		listField.add(txtBairro);
		listField.add(txtCidade);
		listField.add(txtInscEstadual);
		listField.add(txtInscMunincipal);
		listField.add(txtEmail);
		listField.add(txtCelular);
		listField.add(txtTelefone);
		
		
		for (TextField textField : listField) {
		
			if(textField.getText() == null || textField.getText().trim().isEmpty()) {
				textField.setStyle("-fx-border-color: #FD0E01;" + "-fx-border-width: 1; ");
				listErrors.add(textField);
			}else {
				textField.setStyle(null);
			}
			
		}
		return listErrors;
	}

	private void voltarTela() {
		SiderMenuController menu = new SiderMenuController();
		menu.switchPane(Constants.FORNECEDOR);
	}

}
