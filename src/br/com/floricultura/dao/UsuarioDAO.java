package br.com.floricultura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.model.Usuario;

public class UsuarioDAO {
	private Connection conexao = null;
	private Conexao con = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	public void incluirUsuario(Usuario usuario) {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		try {

			String sql = "INSERT INTO USUARIO( NOMEUSUARIO, LOGINUSUARIO, SENHAUSUARIO, NIVELACESSO, STATUSUSUARIO)"
					+ "VALUES(?, ?, ?, ?,?)";

			statement = conexao.prepareStatement(sql);
		
			statement.setString(1, usuario.getNomeCompleto());
			statement.setString(2, usuario.getLogin());
			statement.setString(3, usuario.getSenha());
			statement.setString(4, usuario.getNivelAcesso());
			statement.setString(5, "ATIVO");
			
			statement.executeUpdate();

		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.fechaConexao();
			
		}
	}

	public boolean alterarUsuario(Usuario usuario) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;
	
		System.out.println(usuario.getNomeCompleto());
		System.out.println(usuario.getLogin());
		System.out.println(usuario.getSenha());
		System.out.println(usuario.getNivelAcesso());
		System.out.println(usuario.getCodigoUsuario());
		try {

			String sql = "UPDATE USUARIO SET NOMEUSUARIO = ?, LOGINUSUARIO = ?, SENHAUSUARIO = ?, NIVELACESSO = ? WHERE CODUSUARIO = ?";
	
			statement = conexao.prepareStatement(sql);
			
			statement.setString(1, usuario.getNomeCompleto());
			statement.setString(2, usuario.getLogin());
			statement.setString(3, usuario.getSenha());
			statement.setString(4, usuario.getNivelAcesso());
			statement.setInt(5,  usuario.getCodigoUsuario());

			statement.executeUpdate();
			
			retorno = true;
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.fechaConexao();
			
		}
	
		return retorno;
	}

	public boolean excluirUsuario(int codigoUsuario) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;
		
		try {

			String sql = "UPDATE USUARIO SET STATUSUSUARIO = ? WHERE CODUSUARIO = ?";
	
			statement = conexao.prepareStatement(sql);
			statement.setString(1, "INATIVO");
			statement.setInt(2, codigoUsuario);
		
			statement.executeUpdate();
			
			retorno = true;
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.fechaConexao();
			
		}
	
		return retorno;
	}

	public List<Usuario> listarUsuarios() {

		con = new Conexao();
		conexao = con.conectarBanco();
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		try {
			
			statement = conexao.prepareStatement("SELECT * FROM USUARIO");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Usuario usuario = new Usuario();
				usuario.setCodigoUsuario(resultSet.getInt("CODUSUARIO"));
				usuario.setLogin(resultSet.getString("LOGINUSUARIO"));
				usuario.setNomeCompleto(resultSet.getString("NOMEUSUARIO"));
				usuario.setNivelAcesso(resultSet.getString("NIVELACESSO"));
				usuario.setStatusUsuario(resultSet.getString("STATUSUSUARIO"));
				usuario.setSenha(resultSet.getString("SENHAUSUARIO"));
				
				//usuario.setDataCadastro(LocalDate.parse(resultSet.getDate("DATACADASTRADA").toString()));
				
				listaUsuarios.add(usuario);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.fechaConexao();
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		return listaUsuarios;
	}
}
