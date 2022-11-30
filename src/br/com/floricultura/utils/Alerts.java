package br.com.floricultura.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public static boolean showAlertConfirmation(String titulo, String header, String mensagem) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(mensagem);
		Optional<ButtonType> result = alert.showAndWait();

		return result.get() == ButtonType.OK;

	}
}
