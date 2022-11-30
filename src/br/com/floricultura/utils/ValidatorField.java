package br.com.floricultura.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ValidatorField {
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
	public static void limitarTamanhoCampo(TextField textField, int tamanho) {
		textField.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
		            
		            	if (textField.getText() != null && textField.getText().length() > tamanho) {
			                String s = textField.getText().substring(0, tamanho);
			                textField.setText(s);
			                
			            }
		        	
		        }
		    });
	}
	
public static boolean validaEmail(String email) {
	    
	    if (email != null && email.length() > 0) {
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(email);
	        if (matcher.matches()) {
	        	return true;
	        }
	    }
	    return false;
	}
}
