package br.com.floricultura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.model.Usuario;

public class LoginDAO {
	
	private Connection conexao = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	
	public Usuario logar(String login, String senha) {
		Conexao con = new Conexao();
		conexao = con.conectarBanco();
		Usuario usuario = new Usuario();
		System.out.println("login " + login + " senha " + senha);
		try {
			
			statement = conexao.prepareStatement("SELECT * FROM USUARIO WHERE LOGINUSUARIO ='" +login+ "' AND SENHAUSUARIO ='" +senha+"'");
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				System.out.println("resultSet.next(");
				
				usuario.setCodigoUsuario(resultSet.getInt("CODUSUARIO"));
				usuario.setLogin(resultSet.getString("LOGINUSUARIO"));
				usuario.setNomeCompleto(resultSet.getString("NOMEUSUARIO"));
				usuario.setNivelAcesso(resultSet.getString("NIVELACESSO"));
				usuario.setStatusUsuario(resultSet.getString("STATUSUSUARIO"));
				usuario.setSenha(resultSet.getString("SENHAUSUARIO"));
				
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.fechaConexao();
			
		}
		return usuario;
		
	}
}
