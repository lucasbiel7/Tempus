/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.GrupoDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.FxMananger;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarInstrutorController implements Initializable {

    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private TableColumn<Usuario, String> tcNome;
    @FXML
    private TableColumn<Usuario, Integer> tcJanelas;
    @FXML
    private TableColumn<Usuario, Integer> tcAulas;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private Date data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvUsuario.setItems(usuarios);
        Platform.runLater(() -> {
            data = (Date) ((Parent) tvUsuario.getParent().getUserData()).getUserData();
            usuarios.setAll(new UsuarioDAO().pegarPorGrupo(new GrupoDAO().pegarGrupo("Instrutor")));
        });
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcJanelas.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, Integer> param) -> new SimpleObjectProperty<>(param.getValue().getCargaHoraria() - new AulaDAO().pegarPorDiaInstrutor(param.getValue(), data).size()));
        tcAulas.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, Integer> param) -> {
            return new SimpleObjectProperty<>(new AulaDAO().pegarPorDiaInstrutor(param.getValue(), data).size());
        });
    }

    @FXML
    private void tvUsuarioMouseReleased(MouseEvent mouseEvent) {
        if (tvUsuario.getSelectionModel().getSelectedItem() != null) {
            AnchorPane anchorPane = (AnchorPane) tvUsuario.getParent().getUserData();
            Object[] dados = new Object[]{tvUsuario.getSelectionModel().getSelectedItem(), data};
            FxMananger.insertPane(anchorPane, "DetalheInstrutor", dados);
        }
    }
    
    
}
