package br.com.floricultura.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.opencsv.CSVWriter;

import br.com.floricultura.connection.Conexao;
import br.com.floricultura.dao.ProdutoDAO;
import br.com.floricultura.model.Produto;
import br.com.floricultura.model.TipoRelatorio;
import br.com.floricultura.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RelatoriosController implements Initializable {

	@FXML
	private Button btnGerar;

	@FXML
	private Button btnLimpar;

	@FXML
	private Button btnvoltar;

	@FXML
	private ComboBox<TipoRelatorio> cmbTipoRelatorio;

	@FXML
	private RadioButton radioCSV;

	@FXML
	private RadioButton radioPDF;

	@FXML
	private DatePicker txtDataFinal;

	@FXML
	private DatePicker txtDataInicial;

	@FXML
	void onActionbtnGerar(ActionEvent event) {

		LocalDate dataInicial = txtDataInicial.getValue();
		LocalDate dataFinal = txtDataFinal.getValue();

		String sql = "SELECT ";

		switch (cmbTipoRelatorio.getValue()) {

		case VENDAS:

			break;

		case PRODUTOS:
			sql += "CODPRODUTO, NOMEPRODUTO, QUANTIDADEPRODUTO,DATAVALIDADE, DATACADASTRADA "
					+ "FROM PRODUTO WHERE DATAVALIDADE BETWEEN CONVERT(DATE, \'" + dataInicial
					+ "\') AND CONVERT(DATE, \'" + dataFinal + "\')";
			break;

		case ENTRADAS_PRODUTOS:

			break;
		case SAIDA_PRODUTOS:

			System.out.println(sql);
			break;

		default:
			break;
		}

		if(radioPDF.isSelected()) {

			gerarPDF(sql);
		}
		
		if(radioCSV.isSelected()) {
			gerarCSV(event, sql);
		}
	}

	@FXML
	void onActionbtnVoltar(ActionEvent event) {
		
	}

	@FXML
	void onActionLimpar(ActionEvent event) {

	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		txtDataInicial.setValue(LocalDate.now());
		txtDataFinal.setValue(LocalDate.now());

		cmbTipoRelatorio.getItems().addAll(TipoRelatorio.PRODUTOS,TipoRelatorio.VENDAS, TipoRelatorio.ENTRADAS_PRODUTOS,
				TipoRelatorio.SAIDA_PRODUTOS);

		Callback<ListView<TipoRelatorio>, ListCell<TipoRelatorio>> tipoRelatorioFactory = lv -> new ListCell<TipoRelatorio>() {
			@Override
			protected void updateItem(TipoRelatorio item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescricao());
			}
		};
		cmbTipoRelatorio.setCellFactory(tipoRelatorioFactory);
		cmbTipoRelatorio.setButtonCell(tipoRelatorioFactory.call(null));
		cmbTipoRelatorio.getSelectionModel().select(0);

	}

	@SuppressWarnings("deprecation")
	private void gerarPDF(String sql) {
		Connection conexao = null;
		Conexao con = new Conexao();
		conexao = con.conectarBanco();
		try {

			JasperDesign japerDesign = JRXmlLoader.load(getClass().getResourceAsStream("../reports/Produtos.jrxml"));

			JRDesignQuery updateQuery = new JRDesignQuery();

			updateQuery.setText(sql);

			japerDesign.setQuery(updateQuery);

			JasperReport jasperReport = JasperCompileManager.compileReport(japerDesign);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexao);

			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

			jasperViewer.setTitle("Produtos");
			jasperViewer.show();

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void gerarCSV(ActionEvent event, String sql) {
		FileChooser fileChooser = new FileChooser();
		Stage parentStage = Utils.currentStage(event);
		
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			
			fileChooser.setTitle("Salvar Arquivo");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Formato CSV", "*csv"));
			
			File file = fileChooser.showSaveDialog(parentStage.getScene().getWindow());
			String fileName = file.getAbsolutePath();
			Path CSV_PATH = Paths.get(fileName +".csv");
			
			CSVWriter csvWriter = new CSVWriter(Files.newBufferedWriter(CSV_PATH, StandardCharsets.UTF_8), ';',
					CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

			String[] headers = { "Códido do Produto", "Nome do Produto", "Quantidade", "Data de Validade" };
			List<String[]> produtos = new ArrayList<String[]>();
			produtos.add(headers);

			Iterator<Produto> it = produtoDAO.buscarProdutoSQL(sql).iterator();
			while (it.hasNext()) {
				Produto p = it.next();
				produtos.add(new String[] { String.valueOf(p.getCodigoProduto()), p.getNomeProduto(),
						String.valueOf(p.getQuantidadeProduto()), String.valueOf(p.getDataCadastro()),
						String.valueOf(p.getDataCadastro()) });
			}
			csvWriter.writeAll(produtos);

			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
