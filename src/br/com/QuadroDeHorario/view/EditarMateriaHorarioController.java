/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AmbienteDAO;
import br.com.QuadroDeHorario.dao.AmbienteRecursosDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.dao.MateriaRecursosDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.dao.UsuarioMateriaDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.entity.MateriaHorario;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbienteID;
import br.com.QuadroDeHorario.entity.MateriaRecursos;
import br.com.QuadroDeHorario.entity.Recurso;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.entity.UsuarioMateria;
import br.com.QuadroDeHorario.entity.UsuarioMateria.Tipo;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EditarMateriaHorarioController implements Initializable {

    @FXML
    private RadioButton rbCompetencia;
    @FXML
    private RadioButton rbPreferencia;
    @FXML
    private RadioButton rbInteresse;
    @FXML
    private RadioButton rbTodos;
    @FXML
    private ComboBox<Usuario> cbInstrutor;
    @FXML
    private TableView<Ambiente> tvAmbiente;
    @FXML
    private TableColumn<Ambiente, String> tcNome;
    @FXML
    private TableView<Recurso> tvRecursosAmbiente;
    @FXML
    private TableColumn<Recurso, String> tcNomeRecursoAmbiente;
    @FXML
    private TableView<Recurso> tvRecursosDisciplina;
    @FXML
    private TableColumn<Recurso, String> tcNomeRecursoDisciplina;
    @FXML
    private TableView<Ambiente> tvAmbiente1;
    @FXML
    private TableColumn<Ambiente, String> tcNomeAmbiente1;
    @FXML
    private TableView<Ambiente> tvAmbiente2;
    @FXML
    private TableColumn<Ambiente, String> tcNomeAmbiente2;
    @FXML
    private TableView<Ambiente> tvAmbiente3;
    @FXML
    private TableColumn<Ambiente, String> tcNomeAmbiente3;
    @FXML
    private TableView<Ambiente> tvAmbiente4;
    @FXML
    private TableColumn<Ambiente, String> tcNomeAmbiente4;
    @FXML
    private TableView<Ambiente> tvAmbiente5;
    @FXML
    private TableColumn<Ambiente, String> tcNomeAmbiente5;
    @FXML
    private ColorPicker cpCorFonte;
    private MateriaHorario materiaHorario;
    private Stage stage;
    private ObservableList<Usuario> instrutores = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente = FXCollections.observableArrayList();
    private ObservableList<Recurso> recursoDisciplina = FXCollections.observableArrayList();
    private ObservableList<Recurso> recursoAmbiente = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente1 = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente2 = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente3 = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente4 = FXCollections.observableArrayList();
    private ObservableList<Ambiente> ambiente5 = FXCollections.observableArrayList();
    @FXML
    private AnchorPane apContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) rbCompetencia.getScene().getWindow();
            materiaHorario = (MateriaHorario) apContainer.getUserData();
            cpCorFonte.setValue(Color.rgb(materiaHorario.getRed(), materiaHorario.getGreen(), materiaHorario.getBlue()));
            for (MateriaHorarioAmbiente materiaHorarioAmbiente : new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorario(materiaHorario)) {
                switch (materiaHorarioAmbiente.getNumero()) {
                    case 1:
                        ambiente1.clear();
                        ambiente1.add(materiaHorarioAmbiente.getId().getAmbiente());
                        break;
                    case 2:
                        ambiente2.clear();
                        ambiente2.add(materiaHorarioAmbiente.getId().getAmbiente());
                        break;
                    case 3:
                        ambiente3.clear();
                        ambiente3.add(materiaHorarioAmbiente.getId().getAmbiente());
                        break;
                    case 4:
                        ambiente4.clear();
                        ambiente4.add(materiaHorarioAmbiente.getId().getAmbiente());
                        break;
                    case 5:
                        ambiente5.clear();
                        ambiente5.add(materiaHorarioAmbiente.getId().getAmbiente());
                        break;
                }
                if (materiaHorario.getMateriaTurmaIntrutorSemestre().getInstrutor() != null) {
                    UsuarioMateria usuarioMateria = new UsuarioMateriaDAO().pegarTodosPorUsuarioMateria(materiaHorario.getMateriaTurmaIntrutorSemestre().getInstrutor(), materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria());
                    if (usuarioMateria != null) {
                        rbCompetencia.setSelected(usuarioMateria.getTipo().equals(Tipo.COMPETENCIA));
                        rbInteresse.setSelected(usuarioMateria.getTipo().equals(Tipo.INTERESSE));
                        rbPreferencia.setSelected(usuarioMateria.getTipo().equals(Tipo.PREFERENCIA));
                    } else {
                        rbTodos.setSelected(true);
                    }
                    rbRadiosActionEvent(null);
                    cbInstrutor.getSelectionModel().select(materiaHorario.getMateriaTurmaIntrutorSemestre().getInstrutor());
                } else {
                    rbRadiosActionEvent(null);
                }
            }
        });
        cbInstrutor.setItems(instrutores);
        tvAmbiente.setItems(ambiente);
        tvRecursosAmbiente.setItems(recursoAmbiente);
        tvRecursosDisciplina.setItems(recursoDisciplina);
        tvAmbiente1.setItems(ambiente1);
        tvAmbiente2.setItems(ambiente2);
        tvAmbiente3.setItems(ambiente3);
        tvAmbiente4.setItems(ambiente4);
        tvAmbiente5.setItems(ambiente5);
        //Colunas Ambientes
        tcNomeAmbiente1.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeAmbiente2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeAmbiente3.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeAmbiente4.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeAmbiente5.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //Colorir Ambientes
        tcNomeAmbiente1.setCellFactory((TableColumn<Ambiente, String> param) -> new TableCell<Ambiente, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                setText(item);
                setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        tcNomeAmbiente2.setCellFactory((TableColumn<Ambiente, String> param) -> new TableCell<Ambiente, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                setText(item);
                setBackground(new Background(new BackgroundFill(Color.rgb(153, 255, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        tcNomeAmbiente3.setCellFactory((TableColumn<Ambiente, String> param) -> new TableCell<Ambiente, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                setText(item);
                setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 102), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        tcNomeAmbiente4.setCellFactory((TableColumn<Ambiente, String> param) -> new TableCell<Ambiente, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                setText(item);
                setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        tcNomeAmbiente5.setCellFactory((TableColumn<Ambiente, String> param) -> new TableCell<Ambiente, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                setText(item);
                setBackground(new Background(new BackgroundFill(Color.rgb(255, 153, 255), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        //Outras colunas 
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeRecursoDisciplina.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcNomeRecursoAmbiente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //Carregar Listas
        ambiente.setAll(new AmbienteDAO().pegarTodos());
        tvAmbiente1.setItems(ambiente1);
        tvAmbiente2.setItems(ambiente2);
        tvAmbiente3.setItems(ambiente3);
        tvAmbiente4.setItems(ambiente4);
        tvAmbiente5.setItems(ambiente5);

    }

    @FXML
    private void tvAmbientesMouseReleased(MouseEvent mouseEvent) {
        recursoAmbiente.clear();
        Ambiente ambiente = tvAmbiente.getSelectionModel().getSelectedItem();
        if (ambiente != null) {
            for (AmbienteRecursos ambienteRecursos : new AmbienteRecursosDAO().pegarPorAmbiente(ambiente)) {
                recursoAmbiente.add(ambienteRecursos.getId().getRecurso());
            }
        }
    }

    @FXML
    private void btRemoverAmbiente1ActionEvent(ActionEvent actionEvent) {
        ambiente1.clear();
    }

    @FXML
    private void btRemoverAmbiente2ActionEvent(ActionEvent actionEvent) {
        ambiente2.clear();
    }

    @FXML
    private void btRemoverAmbiente3ActionEvent(ActionEvent actionEvent) {
        ambiente3.clear();
    }

    @FXML
    private void btRemoverAmbiente4ActionEvent(ActionEvent actionEvent) {
        ambiente4.clear();
    }

    @FXML
    private void btRemoverAmbiente5ActionEvent(ActionEvent actionEvent) {
        ambiente5.clear();
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        materiaHorario.setRed((int) (cpCorFonte.getValue().getRed() * 255));
        materiaHorario.setBlue((int) (cpCorFonte.getValue().getBlue() * 255));
        materiaHorario.setGreen((int) (cpCorFonte.getValue().getGreen() * 255));
        materiaHorario.getMateriaTurmaIntrutorSemestre().setInstrutor(cbInstrutor.getSelectionModel().getSelectedItem());
        if (materiaHorario.getMateriaTurmaIntrutorSemestre().getInstrutor() == null) {
            Mensagem.showError("Selecione instrutor", "Não é permitido salvar alteração com professor nullo!");

        } else {
            new MateriaHorarioDAO().editar(materiaHorario);
            List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosSaveDelete(materiaHorario);
            for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
                new MateriaHorarioAmbienteDAO().excluir(materiaHorarioAmbiente);
            }
            //Ambiente 1
            if (!ambiente1.isEmpty()) {
                if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true))) {
                    new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true));
                } else {
                    new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true));
                }
            }
            //Ambiente 2
            if (!ambiente2.isEmpty()) {
                if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true))) {
                    new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true));
                } else {
                    new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true));
                }
            }
            //Ambiente 3
            if (!ambiente3.isEmpty()) {
                if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true))) {
                    new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true));
                } else {
                    new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true));
                }
            }
            //Ambiente 4
            if (!ambiente4.isEmpty()) {
                if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true))) {
                    new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true));
                } else {
                    new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true));
                }
            }
            //Ambiente 5
            if (!ambiente5.isEmpty()) {
                if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true))) {
                    new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true));
                } else {
                    new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true));
                }
            }
            stage.close();
        }
    }

    @FXML
    private void btAdicionarSubstitutoActionEvent(ActionEvent actionEvent) {
        materiaHorario.setId(0);
        materiaHorario.getMateriaTurmaIntrutorSemestre().setInstrutor(cbInstrutor.getSelectionModel().getSelectedItem());
        materiaHorario.setSubistito(true);
        materiaHorario.setNumeroSubstituto(new MateriaHorarioDAO().pegarTodosPorTurmaMateria(materiaHorario.getMateriaTurmaIntrutorSemestre().getTurma(), materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria()).size());
        materiaHorario.setRed((int) (cpCorFonte.getValue().getRed() * 255));
        materiaHorario.setBlue((int) (cpCorFonte.getValue().getBlue() * 255));
        materiaHorario.setGreen((int) (cpCorFonte.getValue().getGreen() * 255));
        new MateriaHorarioDAO().cadastrar(materiaHorario);
        List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosSaveDelete(materiaHorario);
        for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
            new MateriaHorarioAmbienteDAO().excluir(materiaHorarioAmbiente);
        }
        //Ambiente 1
        if (!ambiente1.isEmpty()) {
            if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true))) {
                new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true));
            } else {
                new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente1.getItems().get(0)), 1, true));
            }
        }
        //Ambiente 2
        if (!ambiente2.isEmpty()) {
            if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true))) {
                new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true));
            } else {
                new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente2.getItems().get(0)), 2, true));
            }
        }
        //Ambiente 3
        if (!ambiente3.isEmpty()) {
            if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true))) {
                new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true));
            } else {
                new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente3.getItems().get(0)), 3, true));
            }
        }
        //Ambiente 4
        if (!ambiente4.isEmpty()) {
            if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true))) {
                new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true));
            } else {
                new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente4.getItems().get(0)), 4, true));
            }
        }
        //Ambiente 5
        if (!ambiente5.isEmpty()) {
            if (!materiaHorarioAmbientes.contains(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true))) {
                new MateriaHorarioAmbienteDAO().cadastrar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true));
            } else {
                new MateriaHorarioAmbienteDAO().editar(new MateriaHorarioAmbiente(new MateriaHorarioAmbienteID(materiaHorario, tvAmbiente5.getItems().get(0)), 5, true));
            }
        }
        stage.close();
    }

    @FXML
    private void meAdicionarAmbienteMouseReleased(MouseEvent mouseEvent) {
        Object tabela = mouseEvent.getSource();
        Ambiente ambienteSelecionado = tvAmbiente.getSelectionModel().getSelectedItem();
        if (tvAmbiente.getSelectionModel().getSelectedItem() != null) {
            if (!ambiente1.contains(ambienteSelecionado) && !ambiente2.contains(ambienteSelecionado) && !ambiente3.contains(ambienteSelecionado) && !ambiente4.contains(ambienteSelecionado) && !ambiente5.contains(ambienteSelecionado)) {
                if (tabela.equals(tvAmbiente1)) {
                    ambiente1.clear();
                    ambiente1.add(tvAmbiente.getSelectionModel().getSelectedItem());
                } else if (tabela.equals(tvAmbiente2)) {
                    ambiente2.clear();
                    ambiente2.add(tvAmbiente.getSelectionModel().getSelectedItem());
                } else if (tabela.equals(tvAmbiente3)) {
                    ambiente3.clear();
                    ambiente3.add(tvAmbiente.getSelectionModel().getSelectedItem());
                } else if (tabela.equals(tvAmbiente4)) {
                    ambiente4.clear();
                    ambiente4.add(tvAmbiente.getSelectionModel().getSelectedItem());
                } else if (tabela.equals(tvAmbiente5)) {
                    ambiente5.clear();
                    ambiente5.add(tvAmbiente.getSelectionModel().getSelectedItem());
                }
            } else {
                Mensagem.showError("Ambiente duplicado", "Este ambiente já foi selecionado");
            }
        }
    }

    @FXML
    private void rbRadiosActionEvent(ActionEvent actionEvent) {
        UsuarioMateria.Tipo tipo;
        if (rbCompetencia.isSelected()) {
            tipo = UsuarioMateria.Tipo.COMPETENCIA;
        } else if (rbInteresse.isSelected()) {
            tipo = UsuarioMateria.Tipo.INTERESSE;
        } else if (rbPreferencia.isSelected()) {
            tipo = UsuarioMateria.Tipo.PREFERENCIA;
        } else {
            tipo = Tipo.OUTROS;
        }
        instrutores.clear();
        if (tipo == Tipo.OUTROS) {
            for (Usuario usuarios : new UsuarioDAO().pegarTodos()) {
                instrutores.add(usuarios);
            }
        } else {
            for (UsuarioMateria pe : new UsuarioMateriaDAO().pegarTodosPorMateriaTipo(materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria(), tipo)) {
                instrutores.add(pe.getId().getUsuario());
            }
        }
        recursoDisciplina.clear();
        for (MateriaRecursos materiaRecursos : new MateriaRecursosDAO().pegarTodosPorMateria(materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria())) {
            recursoDisciplina.add(materiaRecursos.getId().getRecurso());
        }
    }

}
