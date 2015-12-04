
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.PermissaoGrupoDAO;
import br.com.QuadroDeHorario.control.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Grupo;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoGrupo;
import br.com.QuadroDeHorario.model.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarPermissaoController implements Initializable {

    @FXML
    private ComboBox<Grupo> cbGrupo;
    @FXML
    private TableView<PermissaoGrupo> tvPermissaoGrupo;
    @FXML
    private TableColumn<PermissaoGrupo, String> tcPermissaoGrupo;
    @FXML
    private TableColumn<PermissaoGrupo, PermissaoGrupo> tcHabilitadoGrupo;

    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private TableView<PermissaoUsuario> tvPermissaoUsuario;
    @FXML
    private TableColumn<PermissaoUsuario, String> tcPermissaoUsuario;
    @FXML
    private TableColumn<PermissaoUsuario, PermissaoUsuario> tcHabilitadoUsuario;

    private ObservableList<Grupo> grupos = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<PermissaoGrupo> permissaoGrupo = FXCollections.observableArrayList();
    private ObservableList<PermissaoUsuario> permissaoUsuario = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setar lista nos componentes 
        cbGrupo.setItems(grupos);
        cbUsuario.setItems(usuarios);
        tvPermissaoGrupo.setItems(permissaoGrupo);
        tvPermissaoUsuario.setItems(permissaoUsuario);
        //Carregar Listas
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        grupos.setAll(new GrupoDAO().pegarTodos());
        //Configurando as tabelas
        tcPermissaoGrupo.setCellValueFactory((TableColumn.CellDataFeatures<PermissaoGrupo, String> param) -> new SimpleObjectProperty<>(param.getValue().getId().getPermissao().toString()));
        tcHabilitadoGrupo.setCellValueFactory((TableColumn.CellDataFeatures<PermissaoGrupo, PermissaoGrupo> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcHabilitadoGrupo.setCellFactory((TableColumn<PermissaoGrupo, PermissaoGrupo> param) -> new TableCell<PermissaoGrupo, PermissaoGrupo>() {

            @Override
            protected void updateItem(PermissaoGrupo item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox cbHabilitado = new CheckBox();
                    cbHabilitado.setSelected(item.isHabilitado());
                    setGraphic(cbHabilitado);
                    cbHabilitado.setOnAction((ActionEvent event) -> {
                        item.setHabilitado(cbHabilitado.isSelected());
                        new PermissaoGrupoDAO().editar(item);
                    });
                }
            }
        });
        //Tabela Usuario
        tcPermissaoUsuario.setCellValueFactory((TableColumn.CellDataFeatures<PermissaoUsuario, String> param) -> new SimpleObjectProperty<>(param.getValue().getId().getPermissao().toString()));
        tcHabilitadoUsuario.setCellValueFactory((TableColumn.CellDataFeatures<PermissaoUsuario, PermissaoUsuario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcHabilitadoUsuario.setCellFactory((TableColumn<PermissaoUsuario, PermissaoUsuario> param) -> new TableCell<PermissaoUsuario, PermissaoUsuario>() {

            @Override
            protected void updateItem(PermissaoUsuario item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox cbHabilitado = new CheckBox();
                    cbHabilitado.setSelected(item.isHabilitado());
                    setGraphic(cbHabilitado);
                    cbHabilitado.setOnAction((ActionEvent event) -> {
                        item.setHabilitado(cbHabilitado.isSelected());
                        new PermissaoUsuarioDAO().editar(item);
                    });
                }
            }
        });
    }

    @FXML
    private void cbGrupoActionEvent(ActionEvent actionEvent) {
        if (cbGrupo.getSelectionModel().getSelectedItem() != null) {
            for (Permissao permissao : Permissao.values()) {
                PermissaoGrupo permissaoGrupo = new PermissaoGrupoDAO().pegarPorId(new PermissaoGrupo.PermissaoGrupoID(cbGrupo.getSelectionModel().getSelectedItem(), permissao));
                if (permissaoGrupo == null) {
                    permissaoGrupo = new PermissaoGrupo();
                    permissaoGrupo.setId(new PermissaoGrupo.PermissaoGrupoID(cbGrupo.getSelectionModel().getSelectedItem(), permissao));
                    permissaoGrupo.setHabilitado(true);
                    new PermissaoGrupoDAO().cadastrar(permissaoGrupo);
                }
            }
            permissaoGrupo.setAll(new PermissaoGrupoDAO().pegarTodosPorGrupo(cbGrupo.getSelectionModel().getSelectedItem()));
            usuarios.setAll(new UsuarioDAO().pegarPorGrupo(cbGrupo.getSelectionModel().getSelectedItem()));
            cbUsuario.getSelectionModel().clearSelection();
        } else {
            usuarios.setAll(new UsuarioDAO().pegarTodos());
        }
    }

    @FXML
    private void cbUsuarioActionEvent(ActionEvent actionEvent) {
        if (cbUsuario.getSelectionModel().getSelectedItem() != null) {
            for (Permissao permissao : Permissao.values()) {
                PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarPorId(new PermissaoUsuario.PermissaoUsuarioID(cbUsuario.getSelectionModel().getSelectedItem(), permissao));
                if (permissaoUsuario == null) {
                    permissaoUsuario = new PermissaoUsuario();
                    PermissaoGrupo permissaoGrupo = new PermissaoGrupoDAO().pegarPorId(new PermissaoGrupo.PermissaoGrupoID(cbUsuario.getSelectionModel().getSelectedItem().getGrupo(), permissao));
                    if (permissaoGrupo != null) {
                        permissaoUsuario.setHabilitado(permissaoGrupo.isHabilitado());
                    } else {
                        permissaoUsuario.setHabilitado(true);
                    }
                    permissaoUsuario.setId(new PermissaoUsuario.PermissaoUsuarioID(cbUsuario.getSelectionModel().getSelectedItem(), permissao));
                    new PermissaoUsuarioDAO().cadastrar(permissaoUsuario);
                }
            }
            permissaoUsuario.setAll(new PermissaoUsuarioDAO().pegarTodosPorUsuario(cbUsuario.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void btAplicarAlteracoesActionEvent(ActionEvent actionEvent) {
        Button btAplicarAlteracoes = (Button) actionEvent.getSource();
        btAplicarAlteracoes.setText("Aguarde...");
        new Thread(() -> {
            if (cbGrupo.getSelectionModel().getSelectedItem() != null) {
                for (Usuario usuario : new UsuarioDAO().pegarPorGrupo(cbGrupo.getSelectionModel().getSelectedItem())) {
                    for (Permissao permissao : Permissao.values()) {
                        PermissaoGrupo permissaoGrupo = new PermissaoGrupoDAO().pegarPorGrupoPermissao(usuario.getGrupo(), permissao);
                        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(usuario, permissao);
                        if (permissaoUsuario == null) {
                            permissaoUsuario = new PermissaoUsuario();
                            permissaoUsuario.setId(new PermissaoUsuario.PermissaoUsuarioID(usuario, permissao));
                            permissaoUsuario.setHabilitado(permissaoGrupo.isHabilitado());
                            new PermissaoUsuarioDAO().cadastrar(permissaoUsuario);
                        } else {
                            permissaoUsuario.setHabilitado(permissaoGrupo.isHabilitado());
                            new PermissaoUsuarioDAO().editar(permissaoUsuario);
                        }
                    }
                }
            }
            Platform.runLater(() -> {
                btAplicarAlteracoes.setText("Aplicar alteração em todas as pessoas do grupo");
            });

        }).start();

    }
}
