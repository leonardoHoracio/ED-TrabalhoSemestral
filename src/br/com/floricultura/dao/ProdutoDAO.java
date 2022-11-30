package br.com.floricultura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.model.Produto;

public class ProdutoDAO {
	private Connection conexao = null;
	Conexao con = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	public void incluirProduto(Produto produto) {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		try {

			String sql = "INSERT INTO PRODUTO( NOMEPRODUTO, PRECOCOMPRAPRODUTO, PRECOVENDAPRODUTO, QUANTIDADEPRODUTO, CODCATEGORIA, CODSUBCATEGORIA,"
					+ "CODFORNECEDOR, STATUSPRODUTO, CODIGOBARRASPRODUTO, DATACADASTRADA, DATAVALIDADE)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?,?, GETDATE(), GETDATE())";

			statement = conexao.prepareStatement(sql);
		
			statement.setString(1, produto.getNomeProduto());
			statement.setBigDecimal(2, produto.getValorCompra().setScale(2));
			statement.setBigDecimal(3, produto.getValorVenda().setScale(2));
			statement.setInt(4, produto.getQuantidadeProduto());
			statement.setInt(5, produto.getCodigoCategoria());
			statement.setInt(6, produto.getCodigoCategoria());
			statement.setInt(7, produto.getCodigoFornecedor());
			statement.setString(8, "ATIVO");
			statement.setString(9, "123456789");
			
			
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
	
	public boolean alterarProduto(Produto produto) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;
	
		
		try {

			String sql = "UPDATE PRODUTO SET NOMEPRODUTO = ?, VALORPRODUTO = ?, QUANTIDADEPRODUTO = ?, STATUSPRODUTO = ? WHERE CODPRODUTO = ?";
	
			statement = conexao.prepareStatement(sql);
			
			statement.setString(1, produto.getNomeProduto());
			statement.setBigDecimal(2, produto.getValorCompra());
			statement.setInt(3,  produto.getQuantidadeProduto());
			statement.setString(4, "ATIVO");
			statement.setInt(5,  produto.getCodigoProduto());

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
	
	public boolean excluirProduto(int codigoProduto) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;
		
		try {

			String sql = "UPDATE PRODUTO SET STATUSPRODUTO = ? WHERE CODPRODUTO = ?";
	
			statement = conexao.prepareStatement(sql);
			statement.setString(1, "INATIVO");
			statement.setInt(2, codigoProduto);
		
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
	
	
	public Produto buscarProdutoPorId(int codigoProduto) {
		con = new Conexao();
		conexao = con.conectarBanco();

	
		try {

			String sql = "SELECT * FROM PRODUTO WHERE CODPRODUTO = '" + codigoProduto + "'";
			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Produto produto = new Produto();
				
				produto.setCodigoProduto(resultSet.getInt("CODPRODUTO"));
				produto.setNomeProduto(resultSet.getString("NOMEPRODUTO"));
				produto.setValorCompra(resultSet.getBigDecimal("PRECOCOMPRAPRODUTO"));
				produto.setValorVenda(resultSet.getBigDecimal("PRECOVENDAPRODUTO"));
				produto.setCodigoCategoria(resultSet.getInt("CODCATEGORIA"));
				produto.setCodigoFornecedor(resultSet.getInt("CODFORNECEDOR"));
				produto.setQuantidadeProduto(resultSet.getInt("QUANTIDADEPRODUTO"));
				produto.setStatusProduto(resultSet.getString("STATUSPRODUTO"));
				produto.setDataCadastro(LocalDate.parse(resultSet.getDate("DATACADASTRADA").toString()));
				
				return produto;
			}
			
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
	
		return null;
		
	}
	
	
	public List<Produto> buscarProdutoSQL(String sql) {
		con = new Conexao();
		conexao = con.conectarBanco();
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		try {

			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Produto produto = new Produto();
				produto.setCodigoProduto(resultSet.getInt("CODPRODUTO"));
				produto.setNomeProduto(resultSet.getString("NOMEPRODUTO"));
				produto.setQuantidadeProduto(resultSet.getInt("QUANTIDADEPRODUTO"));
				produto.setDataCadastro(LocalDate.parse(resultSet.getDate("DATACADASTRADA").toString()));
				produto.setDataCadastro(LocalDate.parse(resultSet.getDate("DATAVALIDADE").toString()));
				listaProdutos.add(produto);

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
		return listaProdutos;
		
	}
	
	public List<Produto> listarProdutos() {
		
		
		con = new Conexao();
		conexao = con.conectarBanco();
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		try {

			String sql = "SELECT * FROM PRODUTO";
			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Produto produto = new Produto();
				produto.setCodigoProduto(resultSet.getInt("CODPRODUTO"));
				produto.setNomeProduto(resultSet.getString("NOMEPRODUTO"));
				produto.setValorCompra(resultSet.getBigDecimal("PRECOCOMPRAPRODUTO"));
				produto.setValorVenda(resultSet.getBigDecimal("PRECOVENDAPRODUTO"));
				produto.setCodigoCategoria(resultSet.getInt("CODCATEGORIA"));
				produto.setCodigoFornecedor(resultSet.getInt("CODFORNECEDOR"));
				produto.setQuantidadeProduto(resultSet.getInt("QUANTIDADEPRODUTO"));
				produto.setStatusProduto(resultSet.getString("STATUSPRODUTO"));
				produto.setDataCadastro(LocalDate.parse(resultSet.getDate("DATACADASTRADA").toString()));
			
				listaProdutos.add(produto);

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
		return listaProdutos;
		
	}
	
}
