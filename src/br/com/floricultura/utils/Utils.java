package br.com.floricultura.utils;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Utils {
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static BigDecimal convertDataMoney(String value) {
		return new BigDecimal(value.replace(".", "").replace(",", ".")).setScale(2);
		
	}

	public static Stage currentStageKey(KeyEvent event) {
		// TODO Auto-generated method stub
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static String formataDados(String dado){
		   dado = dado.replaceAll("[^0-9]", "");
		   //dado = dado.replaceAll(")", "");
		   return dado;
		}
	
	
}
