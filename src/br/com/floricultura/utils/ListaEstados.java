package br.com.floricultura.utils;

import javafx.collections.ObservableList;

public class ListaEstados {
	public static ObservableList<String[]> listaEstados() {

		ObservableList<String[]>  estadosLista = null;
		String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
				"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
		estadosLista.addAll(estados);
		
		return estadosLista;
	}

}
