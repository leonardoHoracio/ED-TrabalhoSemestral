package br.com.floricultura.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Callback;
import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.model.FormaPagamento;
import br.com.floricultura.model.Pagamento;
import br.com.floricultura.utils.MaskFieldUtil;
import br.com.floricultura.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PagamentoController implements Initializable {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnEnviar;

	@FXML
	private FontAwesomeIconView sairTelaPagamento;

	@FXML
	private TextField txtTotalItens;

	@FXML
	private TextField txtTotalPagar;

	@FXML
	private TextField txtTroco;

	@FXML
	private TextField txtValorPagar;

	@FXML
	private TextField txtValorPago;

	@FXML
	private TextField txtTotalCompra;

	@FXML
	private ComboBox<FormaPagamento> cmbFormaPagamento;

	private ObservableList<FormaPagamento> obsList;

	private ObservableList<Pagamento> listaPagamento = FXCollections.observableArrayList();

	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	private Pagamento pagamento;

	private BigDecimal valor = new BigDecimal(0.0);

	private String valorStr = "";

	@FXML
	void onActionBtnCancelar(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@FXML
	void onKeyTypedValorPagar(KeyEvent event) {

		txtValorPagar.textProperty().addListener((observable, oldValue, newValue) -> {
			this.valorStr = newValue;
		});
	}

	@FXML
	void onActionBtnEnviar(ActionEvent event) {
		ajustarPagamento();
		notifyDatachangeListeners();
		Utils.currentStage(event).close();
	}

	private void ajustarPagamento() {

		this.valorStr = valorStr.replace(".", "");

		if (valorStr == null || valorStr.isEmpty()) {
			valor = new BigDecimal(this.txtTotalPagar.getText().replace(".", "").replace(",", ".")).setScale(2);
		} else {
			valor = new BigDecimal(valorStr.replace(",", ".")).setScale(2);
		}

		BigDecimal valorPago = new BigDecimal(this.txtTotalPagar.getText().replace(".", "").replace(",", "."))
				.setScale(2);

		VendaController.totalRecebido = valor.add(VendaController.totalRecebido);

		if (valor.subtract(valorPago).compareTo(BigDecimal.ZERO) > 0) {
			VendaController.troco = valor.subtract(valorPago);
			VendaController.valorReceber = new BigDecimal(0.00);
		} else {

			VendaController.valorReceber = valorPago.subtract(valor);
		}

		//notifyDatachangeListeners();

		Pagamento pagamento = new Pagamento();

		pagamento.setFormaPagamento(cmbFormaPagamento.getValue().toString());
		pagamento.setTotalItens(Integer.parseInt(txtTotalItens.getText()));
		pagamento.setValorPago(valorPago);

		listaPagamento.add(pagamento);

		if (valorPago.subtract(valor).compareTo(BigDecimal.ZERO) <= 0) {
			VendaController.list.clear();
		}

	}

	private void notifyDatachangeListeners() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}
	}

	@FXML
	void onActionValorPagar(ActionEvent event) {
		System.out.println("Valor" + event);
	}

	@FXML
	void onClickedClose(MouseEvent event) {

		Stage stage = (Stage) sairTelaPagamento.getScene().getWindow();
		stage.close();

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		List<FormaPagamento> list = new ArrayList<>();

		list.add(new FormaPagamento(1, "Dinheiro"));
		list.add(new FormaPagamento(2, "Cartão de Crédito"));
		list.add(new FormaPagamento(3, "Cartão de Débito"));
		list.add(new FormaPagamento(4, "Cheque"));
		list.add(new FormaPagamento(5, "PIX"));

		obsList = FXCollections.observableArrayList(list);
		cmbFormaPagamento.setItems(obsList);

		Callback<ListView<FormaPagamento>, ListCell<FormaPagamento>> factory = lv -> new ListCell<FormaPagamento>() {
			@Override
			protected void updateItem(FormaPagamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getForma());
			}
		};

		cmbFormaPagamento.setCellFactory(factory);
		cmbFormaPagamento.setButtonCell(factory.call(null));
		cmbFormaPagamento.getSelectionModel().select(0);

		txtTotalItens.setAlignment(Pos.CENTER_RIGHT);

		MaskFieldUtil.monetaryField(txtTotalPagar);
		MaskFieldUtil.monetaryField(txtTroco);
		MaskFieldUtil.monetaryField(txtValorPagar);
		MaskFieldUtil.monetaryField(txtValorPago);
		MaskFieldUtil.monetaryField(txtTotalCompra);

		txtValorPagar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				
					if (!newPropertyValue) {

						BigDecimal valorPago = new BigDecimal(
								txtValorPagar.getText().replace(".", "").replace(",", ".")).setScale(2);
						BigDecimal totalCompra = new BigDecimal(
								txtTotalPagar.getText().replace(".", "").replace(",", ".")).setScale(2);

						if (valorPago.subtract(totalCompra).compareTo(BigDecimal.ZERO) > 0) {
							txtTroco.setText(String.valueOf(totalCompra.subtract(valorPago)));
						} else {
							txtTroco.setText("0,00");
						}

					}
		
			}

		});

	}

	public void updateFormData() {
		if (pagamento == null) {
			throw new IllegalStateException("Pagamento was null");
		}

		this.txtTotalCompra.setText(String.valueOf(pagamento.getTotalPagar()));
		this.txtTotalItens.setText(String.valueOf(pagamento.getTotalItens()));
		this.txtTotalPagar.setText(String.valueOf(pagamento.getTotalPagar().subtract(pagamento.getValorPago())));
		this.txtValorPagar.setText(String.valueOf(pagamento.getTotalPagar().subtract(pagamento.getValorPago())));
		this.txtValorPago.setText(String.valueOf(pagamento.getValorPago()));
		this.txtTroco.setText("0,00");

	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;

	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
	}

	@FXML
	void onKeyPressedAnchor(KeyEvent event) {

		KeyCode keyCode = event.getCode();

		switch (keyCode) {
		case F10:
			Utils.currentStageKey(event).close();
			break;
		case F11:
			ajustarPagamento();
			notifyDatachangeListeners();
			Utils.currentStageKey(event).close();
			break;
		default:
			break;
		}

	}

}
