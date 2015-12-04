/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LixeiraUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private TableColumn<Usuario, String> tcNome;
    @FXML
    private TableColumn<Usuario, String> tcEmail;
    @FXML
    private TableColumn<Usuario, String> tcGrupo;
    @FXML
    private TableColumn<Usuario, String> tcLogin;
    @FXML
    private TableColumn<Usuario, String> tcTelefone;
    @FXML
    private TableColumn<Usuario, String> tcCelular;
    @FXML
    private TableColumn<Usuario, String> tcEndereco;
    @FXML
    private TableColumn<Usuario, String> tcCargaHoraria;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvUsuario.setItems(usuarios);
        usuarios.setAll(new UsuarioDAO().pegarTodosLixeira());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tcGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

    }

    @FXML
    private void btRestaurarActionEvent(ActionEvent actionEvent) {
        usuario = tvUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            if (Mensagem.showConfirmation("Restaurar usuário", "Você realmente deseja restaurar este usuário?")) {
                usuario.setAtivo(true);
                new UsuarioDAO().editar(usuario);
            }
            usuarios.setAll(new UsuarioDAO().pegarTodosLixeira());
        }
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        usuario = tvUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            if (Mensagem.showConfirmation("Excluir permanentemente", "Você realmente deseja excluir permanentemente o usuário?")) {
                new GenericaDAO<Usuario>().excluir(usuario);
                usuarios.setAll(new UsuarioDAO().pegarTodosLixeira());
            }
        }
    }
}
