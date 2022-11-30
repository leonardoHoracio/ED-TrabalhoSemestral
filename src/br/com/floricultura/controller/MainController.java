package br.com.floricultura.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import br.com.floricultura.dao.UsuarioDAO;
import br.com.floricultura.interfaces.DataChangeListener;
import br.com.floricultura.model.Usuario;
import br.com.floricultura.utils.Constants;
import br.com.floricultura.utils.NomesTelas;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable{

	@FXML
	private AnchorPane contentPane;
	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private JFXDrawer drawner;

	@FXML
	private JFXHamburger hamburguer;

	@FXML
	private StackPane mainStackPane;

	@FXML
	private JFXToolbar toolbar;

	@FXML
	private Label lblNomeUsuario;

	@FXML
	private Label lblNomeTela;

	public static AnchorPane temporaryPane;

	public static NomesTelas nomeTela = NomesTelas.HOME;
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		temporaryPane = contentPane;
		UsuarioDAO dao = new UsuarioDAO();
	
		Usuario.usuarioLogado = dao.listarUsuarios().get(0);	
		lblNomeUsuario.setText(Usuario.usuarioLogado.getNomeCompleto());

		
		initDrawer();
	}

	/*private String getNomeTela(String idTela) {
	
		if(NomesTelas.HOME.getIdTela().equalsIgnoreCase(idTela)) return NomesTelas.HOME.getNomeTela();
		return idTela;
	}
*/
	@FXML
	void onMouseClickedHab(MouseEvent event) {

	}
	
	
	private void initDrawer() {
		drawner.open();
		drawner.setMinWidth(150);
		try {

			VBox menu = FXMLLoader.load(getClass().getResource(Constants.SIDERMENU));

			drawner.setSidePane(menu);

			HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguer);

			transition.setRate(-1);

			hamburguer.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
				transition.setRate(transition.getRate() * -1);
				transition.play();
				if (drawner.isClosed()) {
					drawner.open();
				} else {
					drawner.close();
				}
			});

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	

}
