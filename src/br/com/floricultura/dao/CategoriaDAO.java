package br.com.floricultura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.model.Categoria;


public class CategoriaDAO {
	
	private Connection conexao = null;
	Conexao con = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	public void incluirCategoria(Categoria categoria) {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		try {

			String sql = "INSERT INTO CATEGORIA( DESCRICAOCATEGORIA,  TIPOCATEGORIA, STATUSCATEGORIA )"
					+ "VALUES(?, ?, ?)";

			statement = conexao.prepareStatement(sql);
			
			statement.setString(1, categoria.getDescricaoCategoria());
			statement.setString(2, "PRINCIPAL");
			statement.setString(3, "ATIVO");
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
	
	public void alterarCategoria(Categoria categoria) {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		try {

			String sql = "UPDATE CATEGORIA SET DESCRICAOCATEGORIA = ? WHERE CODCATEGORIA = ?";
			
			statement = conexao.prepareStatement(sql);
			
			statement.setString(1, categoria.getDescricaoCategoria());
			statement.setInt(2, categoria.getCodigoCategoria());
			
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
	
public void excluirCategoria(int idCategoria ) {
		
	con = new Conexao();
	conexao = con.conectarBanco();
	
	
	try {

		String sql = "UPDATE CATEGORIA SET STATUSCATEGORIA = ? WHERE CODCATEGORIA = ?";

		statement = conexao.prepareStatement(sql);
		statement.setString(1, "INATIVO");
		statement.setInt(2, idCategoria);
	
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
	
	public Categoria buscarCategoria(int codigoCategoria ) {
		
		con = new Conexao();
		conexao = con.conectarBanco();
		
		Categoria categoria;
		
		try {

			String sql = "SELECT * FROM CATEGORIA WHERE CODCATEGORIA = '" + codigoCategoria + "'";
			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				categoria = new Categoria();
				categoria.setCodigoCategoria(resultSet.getInt("CODCATEGORIA"));
				categoria.setStatusCategoria(resultSet.getString("STATUSCATEGORIA"));
				categoria.setDescricaoCategoria(resultSet.getString("DESCRICAOCATEGORIA"));
				
				return categoria;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.fechaConexao();
		}
		return null;
		
	}
	
	public List<Categoria> listarCategoria() {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		List<Categoria> listaCategoria = new ArrayList<>();
		
		try {

			String sql = "SELECT * FROM CATEGORIA WHERE STATUSCATEGORIA <> 'INATIVO' ";
			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Categoria cartegoria = new Categoria();
				cartegoria.setCodigoCategoria(resultSet.getInt("CODCATEGORIA"));
				cartegoria.setDescricaoCategoria(resultSet.getString("DESCRICAOCATEGORIA"));
				cartegoria.setStatusCategoria(resultSet.getString("STATUSCATEGORIA"));
			
				listaCategoria.add(cartegoria);

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
		return listaCategoria;
	}


}
