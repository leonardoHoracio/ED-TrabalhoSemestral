package br.com.floricultura.application;

import br.com.floricultura.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static String telaAtual;
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(Constants.MAINVIEW));
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		
		if(telaAtual == Constants.VENDA) {
			
			scene.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.F1) {
			        System.out.println("Massin");
			    }
			});
		}
		scene.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.F1) {
		        System.out.println("Main");
		    }
		});
		
		stage.show();
		
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}


