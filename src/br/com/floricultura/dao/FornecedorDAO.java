package br.com.floricultura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.model.Contato;
import br.com.floricultura.model.Fornecedor;

public class FornecedorDAO {

	private Connection conexao = null;
	Conexao con = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	public boolean incluirFornecedor(Fornecedor fornecedor) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;

		try {

			String sql = "INSERT INTO FORNECEDOR( "
					+ "CPFCNPJFORNECEDOR, RAZAOSOCIAL,NOMEFANTASIA, TIPOEMPRESA, INSCRICAOESTADUAL, INSCRICAOMUNICIPAL, STATUSFORNECEDOR, DATAINCLUSAO)"
					+ "VALUES(?, ?, ?, ?, ?, ?,?, GETDATE())";

			statement = conexao.prepareStatement(sql);
			//conexao.prepareCall();

			statement.setString(1, fornecedor.getCpfCnpj());
			statement.setString(2, fornecedor.getRazaoSocial());
			statement.setString(3, fornecedor.getNomeFantasia());
			statement.setString(4, fornecedor.getTipoEmpresa());
			statement.setString(5, fornecedor.getInscEstadual());
			statement.setString(6, fornecedor.getInscMunicipal());
			statement.setString(7, fornecedor.getStatusFornecedor());

			statement.executeUpdate();
			/*
			 * sql = "Declare @id int set @id = (SELECT SCOPE_IDENTITY() )";
			 * 
			 * statement = conexao.prepareStatement(sql); statement.executeUpdate();
			 */
			sql = "DECLARE @CODFORNECEDOR INT "
					+ "SELECT @CODFORNECEDOR = MAX (CODFORNECEDOR) FROM FORNECEDOR "
					+ "SELECT @CODFORNECEDOR "
					+ "INSERT INTO ENDERECO_FORNECEDOR( "
					+ "CODFORNECEDOR, CEP, LOUGRADOURO,NUMERO, COMPLEMENTO, CIDADE, BAIRRO, UF)"
					+ "VALUES(@CODFORNECEDOR, ?, ?, ?, ?, ?, ?, ? )";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, fornecedor.getEndereco().getCep());
			statement.setString(2, fornecedor.getEndereco().getLogradouro());
			statement.setInt(3, fornecedor.getEndereco().getNumero());
			statement.setString(4, fornecedor.getEndereco().getComplemento());
			statement.setString(5, fornecedor.getEndereco().getCidade());
			statement.setString(6, fornecedor.getEndereco().getBairro());
			statement.setString(7, fornecedor.getEndereco().getUf());
			statement.executeUpdate();

			for (Contato contato : fornecedor.getListaContato()) {
				sql = "INSERT INTO CONTATO_FORNECEDOR( " + "CODFORNECEDOR, NUMEROCONTATO, TIPO)" + "VALUES(1, ?, ?)";

				statement = conexao.prepareStatement(sql);
				statement.setString(1, contato.getNumeroContato());
				statement.setString(2, contato.getTipo());

				statement.executeUpdate();
			}

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

	public boolean alterarFornecedor(Fornecedor fornecedor) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;

		try {
			System.out.println(fornecedor.getRazaoSocial());
			System.out.println(fornecedor.getCodigoFornecedor());
			String sql = "UPDATE FORNECEDOR SET NOMEFORNECEDOR = ?, CNPJFORNECEDOR = ? WHERE CODFORNECEDOR = ? AND STATUSFORNECEDOR = 'ATIVO'";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, fornecedor.getRazaoSocial());
			statement.setString(2, fornecedor.getCpfCnpj());
			statement.setInt(3, fornecedor.getCodigoFornecedor());

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

	public boolean excluirFornecedor(int codigoFuncionario) {
		con = new Conexao();
		conexao = con.conectarBanco();
		boolean retorno = false;

		try {

			String sql = "UPDATE FORNECEDOR SET STATUSFORNECEDOR = ? WHERE CODFORNECEDOR = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, "INATIVO");
			statement.setInt(2, codigoFuncionario);

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

	public Fornecedor buscarFornecedorPorId(Fornecedor fornecedor) {
		return null;
	}

	public List<Fornecedor> listarFornecedores() {
		con = new Conexao();
		conexao = con.conectarBanco();

		List<Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();
		try {

			String sql = "SELECT * FROM FORNECEDOR";
			statement = conexao.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCodigoFornecedor(resultSet.getInt("CODFORNECEDOR"));
				fornecedor.setCpfCnpj(resultSet.getString("CPFCNPJFORNECEDOR"));
				fornecedor.setRazaoSocial(resultSet.getString("RAZAOSOCIAL"));
				fornecedor.setNomeFantasia(resultSet.getString("NOMEFANTASIA"));
				fornecedor.setTipoEmpresa(resultSet.getString("TIPOEMPRESA"));
				fornecedor.setInscEstadual(resultSet.getString("INSCRICAOESTADUAL"));
				fornecedor.setInscMunicipal(resultSet.getString("INSCRICAOMUNICIPAL"));
				fornecedor.setStatusFornecedor(resultSet.getString("STATUSFORNECEDOR"));
				// data = LocalDate.parse(resultSet.getString("DATAINCLUSAO"));
				// setDataCadastro(LocalDate.parse(resultSet.getDate("DATACADASTRADA").toString()));

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String data = LocalDate.parse(resultSet.getDate("DATAINCLUSAO").toString()).format(formatter);
				// System.out.println(LocalDate.parse(resultSet.getDate("DATAINCLUSAO").toString()).format(formatter));
				//fornecedor.setDataInclusao(LocalDate.parse(data));
				System.out.println(data);
				listaFornecedor.add(fornecedor);

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
		return listaFornecedor;
	}

}
