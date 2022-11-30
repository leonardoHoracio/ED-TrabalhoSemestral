package br.com.floricultura.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import br.com.floricultura.application.Main;
import br.com.floricultura.dao.ProdutoDAO;
import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.model.Carrinho;
import br.com.floricultura.model.Pagamento;
import br.com.floricultura.model.Produto;
import br.com.floricultura.utils.Alerts;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.MaskFieldUtil;
import br.com.floricultura.utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendaController implements Initializable, DataChangeListener {
	
	@FXML
	private StackPane vendaView;

	@FXML
	private AnchorPane anchorPaneView;

	@FXML
	private TableView<Carrinho> tableViewVenda;

	@FXML
	private TableColumn<Carrinho, Integer> colunmNumeroItem;

	@FXML
	private TableColumn<Carrinho, Integer> colunmCodigoProduto;

	@FXML
	private TableColumn<Carrinho, String> colunmDescricao;

	@FXML
	private TableColumn<Carrinho, Integer> colunmQuantidade;

	@FXML
	private TableColumn<Carrinho, BigDecimal> colunmValorUnitario;

	@FXML
	private TableColumn<Carrinho, BigDecimal> colunmValorTotal;

	@FXML
	private Label lblCodigoBarras;

	@FXML
	private Label lblValorUnitario;

	@FXML
	private Label lblTotalItem;

	@FXML
	private Label lblCodigoProduto;

	@FXML
	private Label lblSubtotal;

	@FXML
	private Label lblTotalRecebido;

	@FXML
	private Label lblTroco;

	@FXML
	private Button btnSimular;

	@FXML
	private Button btnPagamento;

	@FXML
	private Button btnCancelarVenda;

	@FXML
	private Button btnRemoverProduto;

	@FXML
	private Button btnVoltar;
	
    @FXML
    private Button btnAplicarDesconto;

	@FXML
	private TextField txtCodigoBarras;

	@FXML
	private TextField txtQuantidadeProduto;

	@FXML
	private TextField txtCodigoProduto;

	@FXML
	private TextField txtValorReceber;

	@FXML
    private Spinner<Integer> spnDesconto;

	@FXML
	private TextField txtValorRecebido;

	@FXML
	private TextField txtTroco;

	@FXML
	private TextField txtValorUnitario;

	@FXML
	private TextField txtValorItem;

	@FXML
	private TextField txtValorTotal;
	public static MainController mainController ;

	public static int itemNumero = 1;
	public static BigDecimal valorTotal = new BigDecimal(0.0);
	public static BigDecimal troco = new BigDecimal(0.0);
	public static BigDecimal totalRecebido = new BigDecimal(0.0);
	public static BigDecimal valorReceber = new BigDecimal(0.0);
	private int desconto = 0;

	public static ObservableList<Carrinho> list = FXCollections.observableArrayList();

	ObservableList<Pagamento> listaPagamento = FXCollections.observableArrayList();

	Produto produto;

	@FXML
	private void onActionBtnSimular(ActionEvent event) {

		int quantidade = 1;

		Carrinho item = new Carrinho();

		produto = getRandomElement();
		item.setNumeroItem(itemNumero++);
		item.setCodigoProduto(produto.getCodigoProduto());
		item.setDescricaoProduto(produto.getNomeProduto());
		item.setValorTotal(somarValor(item.getValorTotal() == null ? new BigDecimal(0.0) : item.getValorTotal(),
				produto.getValorVenda(), quantidade).setScale(2));
		item.setQuantidade(quantidade);

		item.setValorUnitario(produto.getValorVenda().setScale(2));
		list.addAll(item);
		tableViewVenda.setItems(list);

		this.txtValorTotal.setText(String.valueOf(valorTotal.setScale(2)));

		if (!this.txtValorRecebido.getText().isEmpty()) {
			this.txtValorReceber.setText(
					String.valueOf(valorTotal.setScale(2).subtract(VendaController.totalRecebido.setScale(2))));
		}

		desabilitarCampos();
	}

	@FXML
	private void onActionBtnPagamento(ActionEvent event) {

		Stage parentStage = Utils.currentStage(event);

		openPagamento(parentStage);

	}

	@FXML
	private void onActionBtnCancelarVenda(ActionEvent event) {
		System.out.println("onActionBtnCancelarVenda");
	}

	@FXML
	private void onActionBtnRemoverProduto(ActionEvent event) {

		// Stage parentStage = Utils.currentStage(event);

		// createDialogFormRemoverItem(Constants.REMOVER_ITEM_VENDA, parentStage);
	}
	
	@FXML
	private  void onActionBtnAplicarDesconto(ActionEvent event) {
		desconto = this.spnDesconto.getValue();
		BigDecimal totalPocentagem = new BigDecimal(100); 
		BigDecimal desc = valorTotal.multiply(new BigDecimal(desconto));
		BigDecimal divide = desc.divide(totalPocentagem);

		this.txtValorTotal.setText(String.valueOf(valorTotal.subtract(divide).setScale(2)));
		this.txtValorReceber.setText(String.valueOf(valorTotal.subtract(divide).setScale(2)));
		valorTotal = valorTotal.subtract(divide).setScale(2);
	}

	@FXML
	private void onActionBtnVoltar(ActionEvent event) {
		System.out.println("onActionBtnVoltar");
	}

	private Pagamento calculoPagamento() {

		Pagamento pagamento = new Pagamento();
		
		pagamento.setTotalItens(itemNumero - 1);
		pagamento.setTotalPagar(valorTotal.setScale(2));
		pagamento.setValorPago(convertDataMoney(this.txtValorRecebido.getText()));
		
		return pagamento;
		
	}

	private BigDecimal convertDataMoney(String value) {
		return new BigDecimal(value.replace(",", "."));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		limparCampos();

		txtValorReceber.setAlignment(Pos.CENTER_RIGHT);
		txtValorTotal.setAlignment(Pos.CENTER_RIGHT);
		txtValorRecebido.setAlignment(Pos.CENTER_RIGHT);
		txtTroco.setAlignment(Pos.CENTER_RIGHT);
		SpinnerValueFactory<Integer> spn = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		this.spnDesconto.setValueFactory(spn);
		desabilitarCampos();

		if (!list.isEmpty()) {
			tableViewVenda.setItems(list);
		}
		MaskFieldUtil.monetaryField(txtValorReceber);
		initTable();
		Main.telaAtual = Constants.VENDA;

		MainController.temporaryPane.getChildren().isEmpty();

		txtCodigoProduto.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (!newPropertyValue && txtCodigoProduto.getText().length() > 0
						&& txtCodigoBarras.getText().isEmpty()) {
					searchProduct(Integer.parseInt(txtCodigoProduto.getText()));
				}
			}

		});

		txtCodigoBarras.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (!newPropertyValue && txtCodigoBarras.getText().length() > 0
						&& txtCodigoProduto.getText().isEmpty()) {
					searchProduct(Integer.parseInt(txtCodigoBarras.getText()));
				}
			}

		});

		txtQuantidadeProduto.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				
					if (!newPropertyValue
							&& (txtCodigoBarras.getText().length() > 0 || txtCodigoProduto.getText().length() > 0)) {
						calcProduct(Integer.parseInt(txtQuantidadeProduto.getText()));
					}
				
			}

		});
	}

	public Produto getRandomElement() {
		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> list = dao.listarProdutos();

		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}

	private void searchProduct(int codigoProduto) {
		ProdutoDAO dao = new ProdutoDAO();
		produto = dao.buscarProdutoPorId(codigoProduto);

		txtCodigoBarras.setText(String.valueOf(produto.getCodigoProduto()));

		txtCodigoProduto.setText(String.valueOf(produto.getCodigoProduto()));

		if (!txtQuantidadeProduto.getText().isEmpty()) {
			calcProduct(Integer.parseInt(txtQuantidadeProduto.getText()));
		}

	}

	private void calcProduct(int quantidade) {

		Carrinho item = new Carrinho();
		
		item.setNumeroItem(itemNumero++);
		item.setCodigoProduto(produto.getCodigoProduto());
		item.setDescricaoProduto(produto.getNomeProduto());
		item.setValorTotal(somarValor(item.getValorTotal() == null ? new BigDecimal(0.0) : item.getValorTotal(),
				produto.getValorVenda(), quantidade).setScale(2));
		item.setQuantidade(quantidade);

		item.setValorUnitario(produto.getValorVenda().setScale(2));
		list.addAll(item);
		tableViewVenda.setItems(list);

		this.txtValorUnitario.setText(String.valueOf(produto.getValorVenda().setScale(2)));
		this.txtValorItem.setText(String.valueOf(item.getValorTotal()));
		this.txtValorTotal.setText(String.valueOf(valorTotal));
		this.txtValorReceber.setText(String.valueOf(valorTotal));
		desabilitarCampos();

	}

	private void limparCampos() {
		this.txtCodigoBarras.setText("");
		this.txtCodigoProduto.setText("");
		this.txtQuantidadeProduto.setText("0");
		this.txtValorItem.setText("0,00");
		this.txtValorUnitario.setText("0,00");
		
		this.txtValorRecebido.setText("0,00");
		this.txtValorReceber.setText("0,00");

		this.txtValorTotal.setText("0,00");
		this.txtTroco.setText("0,00");

	}
	
	private void setMasks() {
		this.txtCodigoBarras.setText("");
		this.txtCodigoProduto.setText("");
		this.txtQuantidadeProduto.setText("");
		this.txtValorItem.setText("");
		this.txtValorUnitario.setText("");
		
		this.txtValorRecebido.setText("");
		this.txtValorReceber.setText("");

		this.txtValorTotal.setText("");
		this.txtTroco.setText("");

	}
	
	private void limparValores() {
		itemNumero = 1;
		valorTotal = new BigDecimal(0.0);
		troco = new BigDecimal(0.0);
		totalRecebido = new BigDecimal(0.0);
		valorReceber = new BigDecimal(0.0);
	}
	
	private void habilitarCampos() {
		this.txtCodigoBarras.setDisable(false);
		this.txtQuantidadeProduto.setDisable(false);
		this.txtCodigoProduto.setDisable(false);
		this.txtValorUnitario.setDisable(false);
		this.txtValorItem.setDisable(false);
	}

	private void desabilitarCampos() {
		this.txtCodigoBarras.setDisable(true);
		this.txtQuantidadeProduto.setDisable(true);
		this.txtCodigoProduto.setDisable(true);
		this.txtValorUnitario.setDisable(true);
		this.txtValorItem.setDisable(true);
	}

	private void initTable() {

		colunmNumeroItem.setCellValueFactory(new PropertyValueFactory<Carrinho, Integer>("numeroItem"));
		colunmCodigoProduto.setCellValueFactory(new PropertyValueFactory<Carrinho, Integer>("codigoProduto"));
		colunmDescricao.setCellValueFactory(new PropertyValueFactory<Carrinho, String>("descricaoProduto"));
		colunmQuantidade.setCellValueFactory(new PropertyValueFactory<Carrinho, Integer>("quantidade"));
		colunmValorUnitario.setCellValueFactory(new PropertyValueFactory<Carrinho, BigDecimal>("valorUnitario"));
		colunmValorTotal.setCellValueFactory(new PropertyValueFactory<Carrinho, BigDecimal>("valorTotal"));

	}

	private BigDecimal somarValor(BigDecimal valorAtual, BigDecimal valoProduto, int quantidade) {
		valorTotal = valoProduto.multiply(new BigDecimal(quantidade)).add(valorTotal);
		return valoProduto.multiply(new BigDecimal(quantidade)).add(valorAtual);

	}

	@FXML
	void onKeyPresed(KeyEvent event) {
		vendaView.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				

			}

		});
	}

	@FXML
	void onKeyPressedAnchor(KeyEvent event) {
		
		Stage parentStage = Utils.currentStageKey(event);

		Pagamento pagamento = new Pagamento();

		pagamento.setTotalItens(itemNumero - 1);
		pagamento.setTotalPagar(valorTotal.setScale(2));
		pagamento.setValorPago(new BigDecimal("0.00").setScale(2));
	

		KeyCode keyCode = event.getCode();

		switch (keyCode) {
		
		case F1:
				limparCampos();
				limparValores();
			break;
		case F2:

			if (tableViewVenda.getItems().isEmpty()) {
				Alerts.showAlert("Pagamento", null, "Nenhum produdo infomado!", AlertType.WARNING);
			} else {
				openPagamento(parentStage);
			}
			break;
		case F3:
			/*
			 * if(tableViewVenda.getItems().isEmpty()) { Alerts.showAlert("Pagamento", null,
			 * "Nenhum produdo infomado!", AlertType.WARNING); }else {
			 * createDialogForm(Constants.PAGAMENTO, parentStage, pagamento);
			 * 
			 * }
			 */
			break;
		case F4:
			// createDialogFormRemoverItem(Constants.REMOVER_ITEM_VENDA, parentStage);
			break;
		case F5:

			if (tableViewVenda.getItems().isEmpty()) {
				Alerts.showAlert("Remover Produto", null, "Nenhum produdo infomado!", AlertType.WARNING);
			} else {
				createDialogFormRemoverItem(Constants.REMOVER_ITEM_VENDA, parentStage, tableViewVenda);
			}
			break;
		case F7:
			// createDialogFormAddItem(createDialogFormAddItem(String absoluteName, Stage parentStage, TableView<Carrinho> tableViewVenda) {, parentStage,
			// tableViewVenda);
			limparCampos();
			habilitarCampos();
			break;
		case F9:
			if (!tableViewVenda.getItems().isEmpty() && Alerts.showAlertConfirmation("Cancelar Venda", null,"Você Deseja Cancelar Venda?" )) {
					limparCampos();
					limparValores();
					VendaController.list.clear();
			}
			
		break;
		case F20:
			createDialogFormAddItem(Constants.QUANTIDADE_ITEM,  parentStage, tableViewVenda);
			setMasks();
		break;

		default:
			break;
		}

	}

	private void openPagamento(Stage parentStage) {
		
		createDialogForm(Constants.PAGAMENTO, parentStage, calculoPagamento());
	}

	private void createDialogForm(String absoluteName, Stage parentStage, Pagamento pagamento) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PagamentoController controller = loader.getController();
			controller.setPagamento(pagamento);
			controller.updateFormData();
			controller.subscribeDataChangeListener(this);

			Stage dialogStage = new Stage();

			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erros loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	private void createDialogFormAddItem(String absoluteName, Stage parentStage, TableView<Carrinho> tableViewVenda) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			QuantidadeItemController controller = new QuantidadeItemController();
			// controller.setItens(tableViewVenda.getItems());
			controller.subscribeDataChangeListener(this);

			Stage dialogStage = new Stage();
			pane.setStyle(" -fx-border-color: #610B0B; -fx-border-width: 2;");
			// dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);

			// dialogStage.initStyle(StageStyle.UNDECORATED);

			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erros loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	private void createDialogFormRemoverItem(String absoluteName, Stage parentStage,
			TableView<Carrinho> tableViewVenda) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			RemoverItemVendaController controller = loader.getController();
			controller.setItens(tableViewVenda.getItems());
			controller.subscribeDataChangeListener(this);

			Stage dialogStage = new Stage();
			pane.setStyle(" -fx-border-color: #610B0B; -fx-border-width: 2;");
			// dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);

			// dialogStage.initStyle(StageStyle.UNDECORATED);

			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erros loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	public void setValoTotal(BigDecimal valorTotal) {
		VendaController.valorTotal = valorTotal;
		// lblCodigoBarras = new Label();
		// this.lblCodigoBarras.setText("TESTE");

		System.out.println("TESTE");
		// tableView.getItems().clear();
	}

	@Override
	public void onDataChanged() {
		
		if (VendaController.valorTotal.setScale(2).equals(new BigDecimal("0.00").setScale(2))) {
			tableViewVenda.getItems().clear();
		} else {
			this.txtValorTotal.setText(String.valueOf(VendaController.valorTotal.setScale(2)));
		}

		if (VendaController.troco.setScale(2).compareTo(BigDecimal.ZERO) > 0) {
			this.txtTroco.setText(String.valueOf(VendaController.troco.setScale(2)));
		} else {
			this.txtTroco.setText(String.valueOf("0,00"));
		}

		this.txtValorRecebido.setText(String.valueOf(VendaController.totalRecebido.setScale(2)));
		this.txtValorReceber.setText(String.valueOf(VendaController.valorReceber.setScale(2)));

		if (VendaController.valorReceber.setScale(2).compareTo(BigDecimal.ZERO) == 0) {
			limparCampos();
			limparValores();
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Pagamento");
	        alert.setHeaderText("Pagamento");
	        alert.setContentText("Pagamento.");
	      
	        
	        Thread thread = new Thread(() -> {
	            try {
	
	                Thread.sleep(3000);
	                if (alert.isShowing()) {
	                    Platform.runLater(() -> alert.close());
	                }
	            } catch (Exception exp) {
	                exp.printStackTrace();
	            }
	        });
	        thread.setDaemon(true);
	        thread.start();
	        @SuppressWarnings("unused")
			Optional<ButtonType> result = alert.showAndWait();
	        
		}
	}

	public void setTable(ObservableList<Carrinho> carrinho) {
		list.addAll(carrinho);
	}

}
