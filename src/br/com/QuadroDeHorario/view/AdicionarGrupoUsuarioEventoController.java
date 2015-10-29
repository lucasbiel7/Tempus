/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.CalendarioDAO;
import br.com.QuadroDeHorario.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.dao.EventoDAO;
import br.com.QuadroDeHorario.dao.GrupoDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.entity.CalendarioUsuarioID;
import br.com.QuadroDeHorario.entity.Evento;
import br.com.QuadroDeHorario.entity.Grupo;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.DataHorario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AdicionarGrupoUsuarioEventoController implements Initializable {

    @FXML
    private AnchorPane apContainer;
    @FXML
    private ComboBox<Evento> cbEventos;
    @FXML
    private ComboBox<Grupo> cbGrupo;
    @FXML
    private ListView<Calendario> lvCalendarios;
    @FXML
    private ListView<Calendario> lvCalendariosAdicionados;
    @FXML
    private ListView<Usuario> lvUsuario;
    @FXML
    private ListView<Usuario> lvUsuarioAdicionado;

    private Stage stage;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuariosAdicionados = FXCollections.observableArrayList();
    private ObservableList<Calendario> calendarios = FXCollections.observableArrayList();
    private ObservableList<Calendario> calendariosAdicionados = FXCollections.observableArrayList();
    private ObservableList<Evento> eventos = FXCollections.observableArrayList();
    private ObservableList<Grupo> grupos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Vai executar so depois dos componentes terem sido renderizados então relaxa que ta chegando os dados
        Platform.runLater(() -> {
            stage = (Stage) apContainer.getScene().getWindow();
            Object[] parametros = (Object[]) apContainer.getUserData();
            for (Object parametro : parametros) {
                if (parametro instanceof Evento) {
                    cbEventos.getSelectionModel().select((Evento) parametro);
                }
            }
        });
        //Configurando as listas nos componentes
        lvCalendarios.setItems(calendarios);
        lvCalendariosAdicionados.setItems(calendariosAdicionados);
        lvUsuario.setItems(usuarios);
        lvUsuarioAdicionado.setItems(usuariosAdicionados);
        cbEventos.setItems(eventos);
        cbGrupo.setItems(grupos);
        //Carregando as listas primarias
        eventos.setAll(new EventoDAO().pegarTodos());
        grupos.setAll(new GrupoDAO().pegarTodos());
        lvCalendarios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvCalendariosAdicionados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvUsuario.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvUsuarioAdicionado.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void cbEventoActionEvent(ActionEvent actionEvent) {
        if (cbEventos.getSelectionModel().getSelectedItem() != null) {
            calendarios.setAll(new CalendarioDAO().pegarPorEvento(cbEventos.getSelectionModel().getSelectedItem()));
            for (Calendario calendariosAdicionado : calendariosAdicionados) {
                calendarios.remove(calendariosAdicionado);
            }
        } else {
            calendarios.clear();
        }
    }

    @FXML
    private void cbGrupoActionEvent(ActionEvent actionEvent) {
        if (cbGrupo.getSelectionModel().getSelectedItem() != null) {
            usuarios.setAll(new UsuarioDAO().pegarPorGrupo(cbGrupo.getSelectionModel().getSelectedItem()));
            for (Usuario usuariosAdicionado : usuariosAdicionados) {
                usuarios.remove(usuariosAdicionado);
            }
        } else {
            usuarios.clear();
        }
    }

    //Botões Controladores das listas do evento :D
    @FXML
    private void btAdicionarUmCalendarioActionEvent(ActionEvent actionEvent) {
        ObservableList<Calendario> calendarios = lvCalendarios.getSelectionModel().getSelectedItems();
        if (calendarios != null) {
            calendariosAdicionados.addAll(calendarios);
            this.calendarios.removeAll(calendarios);
        }
    }

    @FXML
    private void btAdicionarTodosCalendarioActionEvent(ActionEvent actionEvent) {
        calendariosAdicionados.addAll(calendarios);
        calendarios.clear();
    }

    @FXML
    private void btRemoverUmCalendarioActionEvent(ActionEvent actionEvent) {
        ObservableList<Calendario> calendarios = lvCalendariosAdicionados.getSelectionModel().getSelectedItems();
        if (calendarios != null) {
            for (Calendario calendario : calendarios) {
                if (calendario.getId().getEvento().equals(cbEventos.getSelectionModel().getSelectedItem())) {
                    this.calendarios.add(calendario);
                }
            }
            calendariosAdicionados.removeAll(calendarios);
        }
    }

    @FXML
    private void btRemoverTodosCalendarioActionEvent(ActionEvent actionEvent) {
        for (Calendario calendario : calendariosAdicionados) {
            if (calendario.getId().getEvento().equals(cbEventos.getSelectionModel().getSelectedItem())) {
                calendarios.add(calendario);
            }
        }
        calendariosAdicionados.clear();
    }

    //Botões controladores da Lista de pessoas agrupadas por grupo
    @FXML
    private void btAdicionarUmUsuarioActionEvent(ActionEvent actionEvent) {
        ObservableList<Usuario> usuarios = lvUsuario.getSelectionModel().getSelectedItems();
        if (usuarios != null) {
            usuariosAdicionados.addAll(usuarios);
            this.usuarios.removeAll(usuarios);
        }
    }

    @FXML
    private void btRemoverUmUsuarioActionEvent(ActionEvent actionEvent) {
        ObservableList<Usuario> usuarios = lvUsuarioAdicionado.getSelectionModel().getSelectedItems();
        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
                if (usuario.getGrupo().equals(cbGrupo.getSelectionModel().getSelectedItem())) {
                    this.usuarios.addAll(usuarios);
                }
            }
            usuariosAdicionados.removeAll(usuarios);
        }
    }

    @FXML
    private void btAdicionarTodosUsuarioActionEvent(ActionEvent actionEvent) {
        usuariosAdicionados.addAll(usuarios);
        usuarios.clear();
    }

    @FXML
    private void btRemoverTodosUsuarioActionEvent(ActionEvent actionEvent) {
        for (Usuario usuario : usuariosAdicionados) {
            if (usuario.getGrupo().equals(cbGrupo.getSelectionModel().getSelectedItem())) {
                usuarios.add(usuario);
            }
        }
        usuariosAdicionados.clear();
    }

    //Botões de gerenciar
    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        Button botao = (Button) actionEvent.getSource();
        botao.setText("Aguarde...");
        for (Calendario calendariosAdicionado : calendariosAdicionados) {
            for (Usuario usuariosAdicionado : usuariosAdicionados) {
                try {
                    CalendarioUsuario calendarioUsuario = new CalendarioUsuario(new CalendarioUsuarioID(calendariosAdicionado, usuariosAdicionado));
                    calendarioUsuario.setManha(usuariosAdicionado.isManha() && new AulaDAO().pegarPorDiaInstrutorTurnos(usuariosAdicionado, calendariosAdicionado.getId().getDataAcontecimento(), DataHorario.Turno.MANHA).isEmpty());
                    calendarioUsuario.setTarde(usuariosAdicionado.isTarde() && new AulaDAO().pegarPorDiaInstrutorTurnos(usuariosAdicionado, calendariosAdicionado.getId().getDataAcontecimento(), DataHorario.Turno.TARDE).isEmpty());
                    calendarioUsuario.setNoite(usuariosAdicionado.isNoite() && new AulaDAO().pegarPorDiaInstrutorTurnos(usuariosAdicionado, calendariosAdicionado.getId().getDataAcontecimento(), DataHorario.Turno.NOITE).isEmpty());
                    if (new CalendarioUsuarioDAO().pegarPorId(calendarioUsuario.getId()) == null) {
                        new CalendarioUsuarioDAO().cadastrar(calendarioUsuario);
                    } else {
                        new CalendarioUsuarioDAO().editar(calendarioUsuario);
                    }
                } catch (ConstraintViolationException cv) {
                    System.err.println(cv.getMessage());
                }
            }
        }
        stage.close();
    }
}
