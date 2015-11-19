/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.SerialCommunication;
import static br.com.QuadroDeHorario.control.SerialUtil.serialCommunication;
import br.com.QuadroDeHorario.dao.AmbienteDAO;
import br.com.QuadroDeHorario.dao.AmbienteRecursosDAO;
import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.CalendarioAmbienteDAO;
import br.com.QuadroDeHorario.dao.RecursoDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.entity.AmbienteRecursosID;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.entity.Recurso;
import br.com.QuadroDeHorario.model.SerialConstants;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarAmbienteController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextArea taDescricao;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private ListView<Recurso> lvRecurso;
    @FXML
    private ListView<Recurso> lvRecursoAdicionado;
    @FXML
    private Spinner<Integer> spCapacidade;
    @FXML
    private TableView<Ambiente> tvAmbiente;
    @FXML
    private TableColumn<Ambiente, String> tcNome;
    @FXML
    private TableColumn<Ambiente, String> tcDescricao;
    @FXML
    private TableColumn<Ambiente, Integer> tcCapacidade;
    @FXML
    private TextField tfChave;
    @FXML
    private TextField tfChaveReserva;
    @FXML
    private Button btLerChave;
    @FXML
    private Button btLerChaveReserva;

    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();
    private ObservableList<Recurso> recursosAdicionados = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambientes = FXCollections.observableArrayList();
    private Stage stage;
    @FXML
    private AnchorPane anchorPane;
    private Ambiente ambiente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) tvAmbiente.getScene().getWindow();
        });
        spCapacidade.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spCapacidade.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCapacidade.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                if (spCapacidade.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCapacidade.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                setValue(getValue() + steps);
            }
        });
        spCapacidade.getValueFactory().setValue(0);
        ambientes.setAll(new AmbienteDAO().pegarTodos());
        recursos.setAll(new RecursoDAO().pegarTodos());
        tvAmbiente.setItems(ambientes);
        lvRecurso.setItems(recursos);
        lvRecursoAdicionado.setItems(recursosAdicionados);
        ambiente = new Ambiente();
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
    }

    @FXML
    private void lvRecursoMouseReleased(MouseEvent mouseEvent) {
        Recurso recurso = lvRecurso.getSelectionModel().getSelectedItem();
        if (recurso != null && mouseEvent.getClickCount() > 1) {
            recursosAdicionados.add(recurso);
            recursos.remove(recurso);
        }
    }

    @FXML
    private void lvRecursosAdicionadosMouseReleased(MouseEvent mouseEvent) {
        Recurso recurso = lvRecursoAdicionado.getSelectionModel().getSelectedItem();
        if (recurso != null && mouseEvent.getClickCount() > 1) {
            recursos.add(recurso);
            recursosAdicionados.remove(recurso);
        }
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        try {
            if (!spCapacidade.getEditor().getText().isEmpty()) {
                int valor = Integer.parseInt(spCapacidade.getEditor().getText());
                spCapacidade.getValueFactory().setValue(valor);
            }
        } catch (NumberFormatException e) {
            spCapacidade.getValueFactory().setValue(0);
        }
        ambiente.setNome(tfNome.getText());
        ambiente.setCapacidade(spCapacidade.getValueFactory().getValue());
        ambiente.setDescricao(taDescricao.getText());
        ambiente.setChave(tfChave.getText());
        ambiente.setChave(tfChaveReserva.getText());
        if (ambiente.getId() == 0) {
            new AmbienteDAO().cadastrar(ambiente);
            Mensagem.showInformation("Cadastrado", "Ambiente foi cadastrado com sucesso!");
        } else {
            new AmbienteDAO().editar(ambiente);
            Mensagem.showInformation("Editado", "Ambiente foi editado com sucesso!");
        }
        for (AmbienteRecursos ambienteRecursos : new AmbienteRecursosDAO().pegarPorAmbiente(ambiente)) {
            new AmbienteRecursosDAO().excluir(ambienteRecursos);
        }
        for (Recurso recursosAdicionado : recursosAdicionados) {
            if (new AmbienteRecursosDAO().pegarPorId(new AmbienteRecursosID(ambiente, recursosAdicionado)) != null) {
                new AmbienteRecursosDAO().editar(new AmbienteRecursos(new AmbienteRecursosID(ambiente, recursosAdicionado)));
            } else {
                new AmbienteRecursosDAO().cadastrar(new AmbienteRecursos(new AmbienteRecursosID(ambiente, recursosAdicionado)));
            }
        }
        ambiente = new Ambiente();
        ambientes.setAll(new AmbienteDAO().pegarTodos());
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        ambiente = new Ambiente();
        carregarDados();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        ambiente = tvAmbiente.getSelectionModel().getSelectedItem();
        if (ambiente != null) {
            List<Aula> aulas = new AulaDAO().pegarPorAmbiente(ambiente);
            List<AmbienteRecursos> ambienteRecursoses = new AmbienteRecursosDAO().pegarPorAmbiente(ambiente);
            List<CalendarioAmbiente> calendarioAmbientes = new CalendarioAmbienteDAO().pegarPorAmbiente(ambiente);
            if (Mensagem.showConfirmation("Excluir ambiente!", "Você deseja excluir o ambiente " + ambiente.getNome() + "?" + ((aulas.isEmpty() && ambienteRecursoses.isEmpty() && calendarioAmbientes.isEmpty()) ? "" : "\nEste ambiente possui aulas!"))) {
                new AmbienteDAO().excluir(ambiente);
            }
            ambientes.setAll(new AmbienteDAO().pegarTodos());
            ambiente = new Ambiente();
            carregarDados();
        }
    }

    @FXML
    private void tvAmbienteMouseReleased(MouseEvent mouseEvent) {
        ambiente = tvAmbiente.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void tfPesquisarKeyRelease(KeyEvent keyEvent) {
        ambientes.setAll(new AmbienteDAO().pesquisarPorNome(tfPesquisar.getText()));
    }

    private Timeline procurarChave;

    @FXML
    private void btLerChaveActionEvent(ActionEvent actionEvent) {
        procurarChave = new Timeline(new KeyFrame(Duration.seconds(3d), (ActionEvent event) -> {
//            btLerChave.setDisable(!serialCommunication.isConexao());
//            btLerChaveReserva.setDisable(!serialCommunication.isConexao());
            setChave();
            if (serialCommunication.isConexao()) {
                btLerChave.setText("Ler Chave");
                btLerChaveReserva.setText("Ler Chave");
            } else {
                btLerChave.setText("Conectar");
                btLerChaveReserva.setText("Conectar");
            }
        }));
        procurarChave.setCycleCount(Timeline.INDEFINITE);
        if (serialCommunication == null) {
            serialCommunication = new SerialCommunication();
            ultimoBotao = (Button) actionEvent.getSource();
            new Thread(serialCommunication).start();
            procurarChave.play();
        } else if (serialCommunication.isConexao()) {
            ultimoBotao = (Button) actionEvent.getSource();
            serialCommunication.enviarMensagem(SerialConstants.REQUISITAR_CHAVE);
        } else {
            try {
                serialCommunication.reConectar();
                Thread.sleep(1000);
               
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciarAmbienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    Button ultimoBotao = btLerChave;

    private void carregarDados() {
        recursosAdicionados.clear();
        recursos.setAll(new RecursoDAO().pegarTodos());
        if (ambiente == null) {
            tfNome.setText("");
            taDescricao.setText("");
            spCapacidade.getValueFactory().setValue(0);
            tfChave.clear();
            tfChaveReserva.clear();
        } else {
            tfNome.setText(ambiente.getNome());
            taDescricao.setText(ambiente.getDescricao());
            spCapacidade.getValueFactory().setValue(ambiente.getCapacidade());
            tfChave.setText(ambiente.getChave());
            tfChaveReserva.setText(ambiente.getChaveReserva());
            if (ambiente.getId() != 0) {
                for (AmbienteRecursos ambienteRecursos : new AmbienteRecursosDAO().pegarPorAmbiente(ambiente)) {
                    recursos.remove(ambienteRecursos.getId().getRecurso());
                    recursosAdicionados.add(ambienteRecursos.getId().getRecurso());
                }
            }
        }
    }

    private void setChave() {
        if (serialCommunication.getLeitura() != null) {
            if (ultimoBotao.equals(btLerChave)) {
                tfChave.setText(serialCommunication.getLeitura());
            } else {
                tfChaveReserva.setText(serialCommunication.getLeitura());
            }
            serialCommunication.setLeitura(null);
            serialCommunication.fecharPorta();
        }
    }
}
