package br.com.floricultura.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.dao.ProdutoDAO;
import br.com.floricultura.model.Carrinho;
import br.com.floricultura.model.Produto;
import br.com.floricultura.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class QuantidadeItemController implements Initializable{

    @FXML
    private TextField txtCodigoBarras;

    @FXML
    private TextField txtCodigoProduto;

    @FXML
    private TextField txtDescricaoProduto;

    @FXML
    private TextField txtQuantidade;
    
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEnviar;

    @FXML
    private Label lblSair;
    
    private ObservableList<Carrinho> listaItens = FXCollections.observableArrayList();
    //private ObservableList<Carrinho> listaNova = FXCollections.observableArrayList();
    private List<DataChangeListener> dataChangeListener = new ArrayList<>();
    Produto produto = new Produto();

    @FXML
    void onActionAcrescentar(ActionEvent event) {

    }

    @FXML
    void onActionCancelar(ActionEvent event) {
    	Utils.currentStage(event).close();
    }

    @FXML
    void onActionCodigoProduto(ActionEvent event) {

    }
    
    /*
     * @FXML
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

     */

    @FXML
    void onActionDecrescentar(ActionEvent event) {

    }

    @FXML
    void onActionEnviar(ActionEvent event) {
    		System.out.println("Enviar QTDE");
    		Carrinho c = new Carrinho();
    		
    		int quantidade = Integer.parseInt(txtQuantidade.getText());
    		BigDecimal valorTotal = produto.getValorVenda().multiply(new BigDecimal(quantidade)).setScale(2);
    		
    		c.setCodigoProduto(produto.getCodigoProduto());
    		c.setDescricaoProduto(produto.getNomeProduto());
    		c.setNumeroItem(1);
    		if(!VendaController.list.isEmpty()) {
    			c.setNumeroItem(VendaController.list.size() + 1);
    		}
    		c.setValorUnitario(produto.getValorVenda());
    		c.setValorTotal(valorTotal);
    		c.setQuantidade(quantidade);
    		listaItens.add(c);
    	
    	/*VendaController v = new VendaController();
    	v.setTable(listaItens);*/
    	
    	VendaController.list.clear();
    	VendaController.list.add(c);
    	VendaController.valorTotal = valorTotal;
    	//reoganizarCarrinho();
		notifyDatachangeListeners();
	
    	Utils.currentStage(event).close();
    }

    private void notifyDatachangeListeners() {
    	for(DataChangeListener listener: dataChangeListener) {
    		listener.onDataChanged();
    	}
		
	}
	/*
	private void reoganizarCarrinho() {
	ObservableList<Carrinho> listaOrganizada = FXCollections.observableArrayList();
		int count = 0;
		for (Carrinho carrinho : listaNova) {
			count ++;
			carrinho.setNumeroItem(count);
			listaOrganizada.add(carrinho);
			
		}
		
		recalcularValor(listaOrganizada);
		VendaController.list.addAll(listaOrganizada);
		VendaController.list.addAll(listaItens);
	}

	private void recalcularValor(ObservableList<Carrinho> listaOrganizada) {
		/*BigDecimal valorTotal = new BigDecimal(0.00).setScale(2);
		for (Carrinho carrinho : listaOrganizada) {
			
			valorTotal = carrinho.getValorTotal().add(valorTotal);
		}
		System.out.println("TOTAL " + valorTotal);
		System.out.println("VendaController.valorReceber " + VendaController.valorReceber);
		
		VendaController.valorReceber = valorTotal.subtract(VendaController.totalRecebido);
		VendaController.valorTotal = valorTotal;
		
	}
*/
	@FXML
    void onActionQuantidade(ActionEvent event) {

    }
 
    @FXML
    void onMouseClickedSair(MouseEvent event) {

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
			//Utils.currentStageKey(event).close();
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
	
	private void searchProduct(int codigoProduto) {
		ProdutoDAO dao = new ProdutoDAO();
		produto = dao.buscarProdutoPorId(codigoProduto);
		
	
		txtCodigoBarras.setText(String.valueOf(produto.getCodigoProduto()));

		txtCodigoProduto.setText(String.valueOf(produto.getCodigoProduto()));

		txtDescricaoProduto.setText(produto.getNomeProduto());
		
		
	}

	public void setItens(ObservableList<Carrinho> items) {
		System.out.println("TES " + items.get(0).getCodigoProduto());
		listaItens.addAll(items);
		
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
	}

	
}
