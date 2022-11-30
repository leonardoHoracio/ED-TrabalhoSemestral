package br.com.floricultura.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Conexao { 
	private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
	private final String URL = "jdbc:sqlserver://localhost:1434;databaseName=AB_FLORICULTURA;sendStringParametersAsUnicode=false;";
	private final String USER = "sa";
	private final String PASS = "@Password123";
	private Connection conexao = null;
	
	public Conexao() {
		conectarBanco();
	}
	
	
	public Connection conectarBanco() {
		try {

			if (conexao != null) {
				//System.out.println("Conexo estabelicida");
				return conexao;
			} else {

				Class.forName(DRIVER);

				conexao = DriverManager.getConnection(URL, USER, PASS);
				// conexao.setAutoCommit(false);

			}

		} catch (ClassNotFoundException erro) {
			//Alert( "Erro na Conexao com o Drive " + erro.toString());

		} catch (SQLException erro) {
			//JOptionPane.showMessageDialog(null, "Erro na Conexao com o Banco " + erro.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}

	public Connection getConnection() {
		JOptionPane.showMessageDialog(null, "Drive" + conexao);
		return conexao;
	}

	public void fechaConexao() {
		if (conexao != null) {

			try {
				conexao.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	public void main(String[] args) {
		conectarBanco();
	}
	
}
