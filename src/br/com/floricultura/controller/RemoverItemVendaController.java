package br.com.floricultura.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.model.Carrinho;
import br.com.floricultura.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class RemoverItemVendaController implements Initializable {

	@FXML
	private Button brnAcresentar;

	@FXML
	private Button btnAprovar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnDecrescentar;

	@FXML
	private Button btnAcecrescentar;

	@FXML
	private FontAwesomeIconView lblSair;

	@FXML
	private TextField txtCodigoBarras;

	@FXML
	private TextField txtCodigoProduto;

	@FXML
	private TextField txtCodigoSupervisor;

	@FXML
	private TextField txtQuantidade;

	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	private ObservableList<Carrinho> listaItens = FXCollections.observableArrayList();
	private ObservableList<Carrinho> listaNova = FXCollections.observableArrayList();
	List<Carrinho> itensEcontrados = FXCollections.observableArrayList();

	private int quantidade = 0;

	private int quantidadeInicial = 0;

	@FXML
	private TextField txtDescricaoProduto;

	@FXML
	void onActionAprovar(ActionEvent event) {
		int qtd = Integer.parseInt(txtQuantidade.getText());
		int codigo = Integer.parseInt(txtCodigoProduto.getText());

		Carrinho carrinho = corrigirLista(codigo);

		if (qtd == carrinho.getQuantidade()) {
			VendaController.list.clear();
		} else {
			carrinho.setQuantidade(carrinho.getQuantidade() - qtd);
		}

		for (Carrinho c : itensEcontrados) {
			if (qtd == c.getQuantidade()) {
				itensEcontrados.clear();
			} else {
				itensEcontrados.clear();
				c.setQuantidade(c.getQuantidade() - qtd);
				
				c.setValorTotal(c.getValorUnitario().multiply(new BigDecimal(c.getQuantidade())).setScale(2));
				itensEcontrados.add(c);
			}
		}

		for (int i = 0; i < listaItens.size(); i++) {
			if (listaItens.get(i).getCodigoProduto() == codigo) {
				listaItens.remove(i);
				i--;
			}

		}

		listaItens.addAll(itensEcontrados);
		/*
		for (int i = 0; i < listaItens.size(); i++) {
			Carrinho c = new Carrinho();

			for (int j = 1; j < listaItens.size(); j++) {
				if (listaItens.get(j) != null) {
					if (listaItens.get(i).getCodigoProduto() == listaItens.get(j).getCodigoProduto()) {
						c.setCodigoProduto(listaItens.get(i).getCodigoProduto());
						c.setDescricaoProduto(listaItens.get(i).getDescricaoProduto());
						c.setNumeroItem(listaItens.get(i).getNumeroItem());
						c.setValorUnitario(listaItens.get(i).getValorUnitario());
						c.setQuantidade(listaItens.get(i).getQuantidade() + listaItens.get(j).getQuantidade());
						c.setValorTotal(c.getValorUnitario().multiply(new BigDecimal(c.getQuantidade())).setScale(2));
						System.out.println("T" + listaItens.size());
						System.out.println("T" + j);
						listaItens.remove(j);
					

					}

				}
			}
			// listaItens.remove(i);
			listaItens.add(c);

		}*/
		VendaController.list.clear();
		reoganizarCarrinho();
		// VendaController.list.addAll(listaNovas);
		notifyDatachangeListeners();
		Utils.currentStage(event).close();

	}

	private void notifyDatachangeListeners() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}
	}

	private void reoganizarCarrinho() {
		ObservableList<Carrinho> listaOrganizada = FXCollections.observableArrayList();
		int count = 0;
		for (Carrinho carrinho : listaItens) {
			count++;
			carrinho.setNumeroItem(count);
			listaOrganizada.add(carrinho);

		}
		VendaController.itemNumero = count+1;
		recalcularValor(listaOrganizada);
		VendaController.list.addAll(listaOrganizada);
	}

	private void recalcularValor(ObservableList<Carrinho> listaOrganizada) {
		BigDecimal valorTotal = new BigDecimal(0.00).setScale(2);
		for (Carrinho carrinho : listaOrganizada) {

			valorTotal = carrinho.getValorTotal().add(valorTotal);
		}
		
		VendaController.valorReceber = valorTotal.subtract(VendaController.totalRecebido);
		VendaController.valorTotal = valorTotal;
		
	}

	private Carrinho corrigirLista(int codigoProduto) {
		Carrinho c = new Carrinho();

		for (Carrinho carrinho : itensEcontrados) {
			if (carrinho.getCodigoProduto() == codigoProduto) {
				c.setCodigoProduto(codigoProduto);
				c.setDescricaoProduto(carrinho.getDescricaoProduto());
				c.setNumeroItem(carrinho.getNumeroItem());
				c.setQuantidade(carrinho.getQuantidade());
				c.setValorUnitario(carrinho.getValorUnitario());
				c.setValorTotal(carrinho.getValorTotal());
			}
		}

		return c;
	}

	@FXML
	void onActionCancelar(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@FXML
	void onActionCodigoProduto(ActionEvent event) {

	}

	@FXML
	void onActionCodigoSupervisor(ActionEvent event) {

	}

	@FXML
	void onActionQuantidade(ActionEvent event) {
		txtQuantidade.textProperty().addListener((observable, oldValue, newValue) -> {
			quantidade = Integer.parseInt(newValue);
			this.txtQuantidade.setText("" + quantidade);
		});
	}

	@FXML
	void onKeyPressedAprovar(KeyEvent event) {

	}

	@FXML
	void onKeyPressedCancelar(KeyEvent event) {

	}

	@FXML
	void onMouseClikedSair(MouseEvent event) {

	}

	@FXML
	void onMouseClickedAcrescentar(MouseEvent event) {
		this.quantidade++;
		this.txtQuantidade.setText("" + quantidade);
		if (quantidadeInicial == Integer.parseInt(txtQuantidade.getText())) {
			brnAcresentar.setDisable(true);
		} else {
			brnAcresentar.setDisable(false);
		}
	}

	@FXML
	void onMouseClickedDecrescentar(MouseEvent event) {
		if (this.quantidade > 1) {
			this.quantidade--;
			this.txtQuantidade.setText("" + quantidade);
		}

		if (quantidadeInicial == Integer.parseInt(txtQuantidade.getText())) {
			brnAcresentar.setDisable(true);
		} else {
			brnAcresentar.setDisable(false);
		}
	}

	@FXML
	void onKeyTypedQuantidade(KeyEvent event) {

		txtQuantidade.textProperty().addListener((observable, oldValue, newValue) -> {
			quantidade = Integer.parseInt(newValue);
			this.txtQuantidade.setText("" + quantidade);
		});
	}

	@FXML
	void onKeyPressedAnchor(KeyEvent event) {
		System.out.println("ESC " + event.getCode());

		KeyCode keyCode = event.getCode();

		switch (keyCode) {
		case ESCAPE:
			Utils.currentStageKey(event).close();
			break;
		case F10:
			Utils.currentStageKey(event).close();
			break;
		case F11:
			// Utils.currentStageKey(event).close();
			break;
		default:
			break;
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		txtCodigoProduto.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				{
					if (!newPropertyValue && txtCodigoProduto.getText().length() > 0) {
						searchProduct(Integer.parseInt(txtCodigoProduto.getText()));
					}
				}
			}

		});
	}

	public void updateFormData() {

	}

	public ObservableList<Carrinho> getItens() {
		return listaNova;

	}

	private void searchProduct(int codigoProduto) {
		Carrinho c = new Carrinho();

		for (Carrinho carrinho : listaItens) {
			if (carrinho.getCodigoProduto() == codigoProduto) {
				c.setCodigoProduto(codigoProduto);
				c.setDescricaoProduto(carrinho.getDescricaoProduto());
				c.setNumeroItem(carrinho.getNumeroItem());
				c.setValorUnitario(carrinho.getValorUnitario());
				c.setValorTotal(carrinho.getValorTotal());
				// quantidade++ ;
				quantidade = carrinho.getQuantidade() + quantidade;

			}
		}
		c.setQuantidade(quantidade);

		itensEcontrados.add(c);

		txtCodigoBarras.setText(String.valueOf(c.getCodigoProduto()));
		txtDescricaoProduto.setText(String.valueOf(c.getDescricaoProduto()));
		quantidadeInicial = quantidade;
		txtQuantidade.setText(String.valueOf(quantidade));
		if (quantidadeInicial == Integer.parseInt(txtQuantidade.getText())) {
			brnAcresentar.setDisable(true);
		} else {
			brnAcresentar.setDisable(false);
		}

	}

	public void setItens(ObservableList<Carrinho> items) {
		System.out.println("TES " + items.get(0).getCodigoProduto());
		listaItens.addAll(items);

	}
	/*
	 * private void recalcularValor(listaOrganizada) { BigDecimal valorPago = new
	 * BigDecimal(this.txtTotalPagar.getText().replace(".", "").replace(",",
	 * ".")).setScale(2);
	 * 
	 * VendaController.valorTotal = valorPago.subtract(valor); VendaController.troco
	 * = valor.subtract(valorPago); VendaController.totalRecebido = valor;
	 * VendaController.valorReceber = valor.subtract(valorPago);
	 * 
	 * 
	 * notifyDatachangeListeners(); System.out.println("SUB " +
	 * this.txtTotalPagar.getText()); Pagamento pagamento = new Pagamento();
	 * 
	 * pagamento.setFormaPagamento(cmbFormaPagamento.getValue().toString());
	 * pagamento.setTotalItens(Integer.parseInt(txtTotalItens.getText()));
	 * pagamento.setValorPago(valorPago);
	 * 
	 * listaPagamento.add(pagamento);
	 * if(valor.subtract(valorPago).compareTo(BigDecimal.ZERO) == 0) {
	 * 
	 * } Utils.currentStage(event).close(); }
	 */

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
	}

}
