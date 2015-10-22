
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorario;
import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.EventoDAO;
import br.com.QuadroDeHorario.dao.MateriaDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.dao.TurmaDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.Evento;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.MateriaHorario;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.util.DataHorario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class QuadroDeHorarioController implements Initializable {

    //Eventos
    @FXML
    private TableView<Evento> tvEvento;
    @FXML
    private TableColumn<Evento, Evento> tcNomeEvento;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private ComboBox<DataHorario.Semestre> cbSemestre;
    @FXML
    private Label lbTurma;
    @FXML
    private CheckBox cbAulasGerminadas;
    @FXML
    private CheckBox cbAutoPreencher;
    @FXML
    private ComboBox<DataHorario.Turno> cbTurno;
    //MateriaHorario
    @FXML
    private TableView<MateriaHorario> tvMateriaHorario;
    @FXML
    private TableColumn<MateriaHorario, String> tcSigla;
    @FXML
    private TableColumn<MateriaHorario, String> tcDisciplina;
    @FXML
    private TableColumn<MateriaHorario, String> tcInstrutor;
    @FXML
    private TableColumn<MateriaHorario, Ambiente> tcAmbiente1;
    @FXML
    private TableColumn<MateriaHorario, Ambiente> tcAmbiente2;
    @FXML
    private TableColumn<MateriaHorario, Ambiente> tcAmbiente3;
    @FXML
    private TableColumn<MateriaHorario, Ambiente> tcAmbiente4;
    @FXML
    private TableColumn<MateriaHorario, Ambiente> tcAmbiente5;
    @FXML
    private TableColumn<MateriaHorario, String> tcCargaHoraria;
    @FXML
    private TableColumn<MateriaHorario, String> tcCargaInstrutor;
    @FXML
    private TableColumn<MateriaHorario, String> tcDataInicio;
    @FXML
    private TableColumn<MateriaHorario, String> tcDataFim;
    @FXML
    private TableColumn<MateriaHorario, String> tcCargaDisciplina;
    @FXML
    private GridPane gpMeses;
    @FXML
    private AnchorPane apContainer;
    @FXML
    private Label lbSelecionado;
    @FXML
    private ProgressIndicator progressIndicator;

    private ObservableList<Evento> eventos = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Semestre> semestre = FXCollections.observableArrayList();
    private ObservableList<MateriaHorario> materiaHorarios = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Turno> turnosDiurmos = FXCollections.observableArrayList();

    private TabelaHorario thMes1;
    private TabelaHorario thMes2;
    private TabelaHorario thMes3;
    private TabelaHorario thMes4;
    private TabelaHorario thMes5;
    private TabelaHorario thMes6;
    private Turma turma;
    private Turma turmaEspelho;
    private MateriaHorario materiaHorario;
    public static final Color corAmbiente1 = Color.WHITE;
    public static final Color corAmbiente2 = Color.rgb(153, 255, 153);
    public static final Color corAmbiente3 = Color.rgb(255, 255, 102);
    public static final Color corAmbiente4 = Color.rgb(204, 204, 255);
    public static final Color corAmbiente5 = Color.rgb(255, 153, 255);
    public Task<Void> carregarDados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 101; i++) {
                    progressIndicator.setProgress(i / 100.0d);
                    Thread.sleep(100);
                }
                progressIndicator.setVisible(false);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuadroDeHorarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        Platform.runLater(() -> {
            turma = (Turma) apContainer.getUserData();
            if (turma.isEspelho()) {
                turmaEspelho = turma;
                turma = turma.getTurmaPrincipal();
            }
            if (turma.getTurno().equals(DataHorario.Turno.diurno)) {
                cbTurno.getSelectionModel().select(DataHorario.Turno.manha);
            } else {
                cbTurno.getSelectionModel().select(turma.getTurno());
            }
            cbTurno.setVisible(turma.getTurno() == DataHorario.Turno.diurno);
            if (turma != null) {
                cbSemestre.getSelectionModel().select(DataHorario.Semestre.setSemestre(turma.getInicio()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(turma.getInicio());
                spAno.getValueFactory().setValue(calendar.get(Calendar.YEAR));
                if (turma.getModulo() == 0) {
                    turma.setModulo(1);
                    for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turma.getCurso(), turma.getModulo())) {
                        MateriaHorario materiaHorario = new MateriaHorario();
                        materiaHorario.setAno(spAno.getValue());
                        materiaHorario.getMateriaTurmaIntrutorSemestre().setMateria(materia);
                        materiaHorario.getMateriaTurmaIntrutorSemestre().setTurma(turma);
                        materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(DataHorario.Semestre.setSemestre(turma.getInicio()));
                        materiaHorario.setRed(new Random().nextInt(255));
                        materiaHorario.setGreen(new Random().nextInt(255));
                        materiaHorario.setBlue(new Random().nextInt(255));
                        if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turma, materia).isEmpty()) {
                            new MateriaHorarioDAO().cadastrar(materiaHorario);
                        }
                    }
                    new TurmaDAO().editar(turma);
                }
                if (turmaEspelho != null) {
                    if (turmaEspelho.getModulo() == 0) {
                        turmaEspelho.setModulo(1);
                        for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turma.getCurso(), turma.getModulo())) {
                            MateriaHorario materiaHorario = new MateriaHorario();
                            materiaHorario.setAno(spAno.getValue());
                            materiaHorario.getMateriaTurmaIntrutorSemestre().setMateria(materia);
                            materiaHorario.getMateriaTurmaIntrutorSemestre().setTurma(turmaEspelho);
                            materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(cbSemestre.getSelectionModel().getSelectedItem());
                            materiaHorario.setRed(new Random().nextInt(255));
                            materiaHorario.setGreen(new Random().nextInt(255));
                            materiaHorario.setBlue(new Random().nextInt(255));
                            if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turmaEspelho, materia).isEmpty()) {
                                new MateriaHorarioDAO().cadastrar(materiaHorario);
                            }
                        }
                        new TurmaDAO().editar(turmaEspelho);
                    }
                }
                TabelaHorario.turma = turma;
                TabelaHorario.turmaEspelho = turmaEspelho;
                if (turma.getTurno().equals(DataHorario.Turno.diurno)) {
                    cbTurno.getSelectionModel().select(DataHorario.Turno.manha);
                } else {
                    cbTurno.getSelectionModel().select(turma.getTurno());
                }
                carregarDados.run();
            }
        });
        //Carregar componentes
        carregarDados = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                synchronized (turma) {
                    carregarTabelas();
                    turma.notifyAll();
                }
                return null;
            }
        };
        tvEvento.setItems(eventos);
        cbSemestre.setItems(semestre);
        tvMateriaHorario.setItems(materiaHorarios);
        spAno.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() + steps);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        spAno.getValueFactory().setValue(calendar.get(Calendar.YEAR));
        //Carregar listas
        eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(spAno.getValue(), true));
        semestre.setAll(DataHorario.Semestre.values());
        //Tabela Evento
        tcNomeEvento.setCellValueFactory((TableColumn.CellDataFeatures<Evento, Evento> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcNomeEvento.setCellFactory((TableColumn<Evento, Evento> param) -> new TableCell<Evento, Evento>() {
            @Override
            protected void updateItem(Evento item, boolean empty) {
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    String[] cores = item.getColor().split("@");
                    Color color = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
                    Circle circle = new Circle(5d);
                    circle.setFill(color);
                    circle.setStroke(color);
                    setGraphic(circle);
                }
            }
        });
        //Tabela MateriaHorario
        tcSigla.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().toString()));
        tcDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria().toString()));
        tcInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaIntrutorSemestre().getInstrutor() != null ? param.getValue().getMateriaTurmaIntrutorSemestre().getInstrutor().toString() : ""));
        tcCargaHoraria.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria().getCargaHoraria())));
        tcCargaDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorDisciplinaTurma(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria(), turma).size())));
        tcCargaInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorMateria(param.getValue()).size())));
        tcDataInicio.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> {
            List<Aula> aulas = new AulaDAO().pegarPorMateria(param.getValue());
            Aula aula;
            if (!aulas.isEmpty()) {
                aula = aulas.get(0);
                return new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()));
            }
            return new SimpleStringProperty();
        });
        tcDataFim.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> {
            List<Aula> aulas = new AulaDAO().pegarPorMateria(param.getValue());
            Aula aula;
            if (!aulas.isEmpty()) {
                aula = aulas.get(aulas.size() - 1);
                return new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()));
            }
            return new SimpleStringProperty();
        });

        tcAmbiente1.setCellFactory(new RenderAmbiente(1));
        tcAmbiente2.setCellFactory(new RenderAmbiente(2));
        tcAmbiente3.setCellFactory(new RenderAmbiente(3));
        tcAmbiente4.setCellFactory(new RenderAmbiente(4));
        tcAmbiente5.setCellFactory(new RenderAmbiente(5));
        //Inserir Ambiente
        tcAmbiente1.setCellValueFactory(new InserirValorAmbiente(1));
        tcAmbiente2.setCellValueFactory(new InserirValorAmbiente(2));
        tcAmbiente3.setCellValueFactory(new InserirValorAmbiente(3));
        tcAmbiente4.setCellValueFactory(new InserirValorAmbiente(4));
        tcAmbiente5.setCellValueFactory(new InserirValorAmbiente(5));
        tvMateriaHorario.getSelectionModel().cellSelectionEnabledProperty().setValue(true);
        tvMateriaHorario.setRowFactory((TableView<MateriaHorario> param) -> new TableRow<MateriaHorario>() {
            @Override
            protected void updateItem(MateriaHorario item, boolean empty) {
                if (!empty) {
                    getChildren().stream().forEach((n) -> {
                        n.setStyle("-fx-text-fill:rgb(" + item.getRed() + "," + item.getGreen() + "," + item.getBlue() + ");");
                    });
                }
            }
        });
        cbTurno.setItems(turnosDiurmos);
        turnosDiurmos.setAll(DataHorario.Turno.manha, DataHorario.Turno.tarde);
    }

    @FXML

    private void spAnoActionEvent(MouseEvent actionEvent) {
        carregarTabelas();
    }

    @FXML
    private void cbSemestreActionEvent(ActionEvent actionEvent) {
        carregarTabelas();
    }

    @FXML
    private void rbChekActionEvent(ActionEvent actionEvent) {
        TabelaHorario.autoPreencher = cbAutoPreencher.isSelected();
        TabelaHorario.aulasGeminadas = cbAulasGerminadas.isSelected();
    }

    @FXML
    private void tvMateriaHorarioActionEvent(ActionEvent actionEvent) {
        materiaHorario = tvMateriaHorario.getSelectionModel().getSelectedItem();
        if (materiaHorario != null) {
            FxMananger.show("EditarMateriaHorario", "Editar disciplina do horário", true, false, materiaHorario);
            carregarTabelas();
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
            if (turmaEspelho != null) {
                materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
            }
        }
    }

    @FXML
    private void btProximoModuloActionEvent(ActionEvent actionEvent) {
        boolean valido = true;
        //Validar disciplinas
        for (MateriaHorario materiaHorario : new MateriaHorarioDAO().pegarTodosPorTurma(turma)) {
            if (new AulaDAO().pegarPorDisciplinaTurma(materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria(), turma).size() < materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria().getCargaHoraria()) {
                valido = false;
                break;
            }
        }
        boolean modulos = turma.getModulo() + 1 <= turma.getCurso().getModulo();
        if (valido && modulos) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deseja Continuar?");
            ((Stage) (alert.getDialogPane().getScene().getWindow())).getIcons().add(FxMananger.image);
            alert.setHeaderText("Próximo módulo");
            alert.setContentText("Deseja continuar neste semestre ou no próximo?");
            ButtonType btProximoSemestre = new ButtonType("Próximo semestre");
            ButtonType btSemestre = new ButtonType("Neste semestre");
            ButtonType btCancelar = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(btProximoSemestre, btSemestre, btCancelar);
            alert.showAndWait().ifPresent((ButtonType t) -> {
                if (t.equals(btProximoSemestre)) {
                    if (cbSemestre.getSelectionModel().getSelectedItem().equals(DataHorario.Semestre.semestre1)) {
                        passarDeModulo(spAno.getValue(), DataHorario.Semestre.semestre2);
                    } else {
                        passarDeModulo(spAno.getValue() + 1, DataHorario.Semestre.semestre1);
                    }
                } else if (t.equals(btSemestre)) {
                    passarDeModulo(spAno.getValue(), cbSemestre.getSelectionModel().getSelectedItem());
                }
            });
        } else if (!modulos) {
            Mensagem.showError("Curso completo", "Não há mais módulos para o curso " + turma.getCurso() + "!");
        } else {
            Mensagem.showError("Módulo incompleto", "Não é possível ir para o próximo módulo porque possui\n"
                    + "disciplinas que não alcançaram a carga horária.");
        }
    }

    @FXML
    private void btContinuarModuloProximoSemestreActionEvent(ActionEvent actionEvent) {
        for (MateriaHorario materiaHorario : new MateriaHorarioDAO().pegarTodosPorTurma(turma)) {
            if (new AulaDAO().pegarPorDisciplinaTurma(materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria(), turma).size() < materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria().getCargaHoraria()) {
                materiaHorario.setId(0);
                if (materiaHorario.getMateriaTurmaIntrutorSemestre().getSemestre().equals(DataHorario.Semestre.semestre1)) {
                    materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(DataHorario.Semestre.semestre2);
                } else {
                    materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(DataHorario.Semestre.semestre1);
                    materiaHorario.setAno(materiaHorario.getAno() + 1);
                }
                new MateriaHorarioDAO().cadastrar(materiaHorario);
            }
        }
    }

    @FXML
    private void cbTurnoActionEvent(ActionEvent actionEvent) {
        TabelaHorario.turno = cbTurno.getSelectionModel().getSelectedItem();
        carregarTabelas();
    }

    public void carregarTabelas() {
        materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
        if (turmaEspelho != null) {
            materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
        }
        eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(spAno.getValue(), true));
        int mesInicial;
        if (cbSemestre.getSelectionModel().getSelectedItem() != null) {
            if (cbSemestre.getSelectionModel().getSelectedItem().equals(DataHorario.Semestre.semestre1)) {
                mesInicial = 1;
            } else {
                mesInicial = 7;
            }

            thMes1 = new TabelaHorario(mesInicial, spAno.getValue());
            mesInicial++;
            thMes2 = new TabelaHorario(mesInicial, spAno.getValue());
            mesInicial++;
            thMes3 = new TabelaHorario(mesInicial, spAno.getValue());
            mesInicial++;
            thMes4 = new TabelaHorario(mesInicial, spAno.getValue());
            mesInicial++;
            thMes5 = new TabelaHorario(mesInicial, spAno.getValue());
            mesInicial++;
            thMes6 = new TabelaHorario(mesInicial, spAno.getValue());
            TabelaHorario.ambienteSelecionado = null;
            TabelaHorario.materiaHorarioSelecionado = null;
            TabelaHorario.turno = cbTurno.getSelectionModel().getSelectedItem();
            lbSelecionado.setText("");
            thMes1.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            thMes2.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            thMes3.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            thMes4.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            thMes5.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            thMes6.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
            Platform.runLater(() -> {
                gpMeses.getChildren().clear();
                gpMeses.addRow(0, thMes1);
                gpMeses.addRow(1, thMes2);
                gpMeses.addRow(2, thMes3);
                gpMeses.addRow(3, thMes4);
                gpMeses.addRow(4, thMes5);
                gpMeses.addRow(5, thMes6);
            });
        }
        if (turmaEspelho != null) {
            lbTurma.setText("Turma\n" + turmaEspelho.getDescricao() + "-" + turmaEspelho.getModulo());
        } else {
            lbTurma.setText("Turma\n" + turma.getDescricao() + "-" + turma.getModulo());
        }
    }

    private void passarDeModulo(Integer ano, DataHorario.Semestre semestre) {
        turma.setModulo(turma.getModulo() + 1);
        if (turmaEspelho != null) {
            turmaEspelho.setModulo(turmaEspelho.getModulo() + 1);
            if (turmaEspelho.getCurso().getModulo() >= turmaEspelho.getModulo()) {
                for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turmaEspelho.getCurso(), turmaEspelho.getModulo())) {
                    MateriaHorario materiaHorario = new MateriaHorario();
                    materiaHorario.getMateriaTurmaIntrutorSemestre().setTurma(turmaEspelho);
                    materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(semestre);
                    materiaHorario.getMateriaTurmaIntrutorSemestre().setMateria(materia);
                    materiaHorario.setAno(ano);
                    materiaHorario.setRed(new Random().nextInt(255));
                    materiaHorario.setGreen(new Random().nextInt(255));
                    materiaHorario.setBlue(new Random().nextInt(255));
                    if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turmaEspelho, materia).isEmpty()) {
                        new MateriaHorarioDAO().cadastrar(materiaHorario);
                    }
                }
            }
        }
        if (turma.getCurso().getModulo() >= turma.getModulo()) {
            new TurmaDAO().editar(turma);
            for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turma.getCurso(), turma.getModulo())) {
                MateriaHorario materiaHorario = new MateriaHorario();
                materiaHorario.getMateriaTurmaIntrutorSemestre().setTurma(turma);
                materiaHorario.getMateriaTurmaIntrutorSemestre().setSemestre(semestre);
                materiaHorario.getMateriaTurmaIntrutorSemestre().setMateria(materia);
                materiaHorario.setAno(ano);
                if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turma, materia).isEmpty()) {
                    new MateriaHorarioDAO().cadastrar(materiaHorario);
                }
            }
        }
    }

    public class CarregarMateriaHorario implements EventHandler<Event> {

        @Override
        public void handle(Event event) {
            if (event instanceof MouseEvent) {
                MouseEvent mouseEvent = (MouseEvent) event;
                if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                    new Thread(new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            synchronized (mouseEvent) {
                                Thread.sleep(((MouseEvent) event).isPopupTrigger() ? 5000 : 1000);
                                materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                                if (turmaEspelho != null) {
                                    materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                                }
                                materiaHorarios.notifyAll();
                            }
                            return null;
                        }
                    }).start();
                }
            }
        }
    }

    public class SelecionarAmbiente implements EventHandler<MouseEvent> {

        private Ambiente ambiente;

        public SelecionarAmbiente(Ambiente ambiente) {
            this.ambiente = ambiente;
        }

        @Override
        public void handle(MouseEvent event) {
            MateriaHorario materiaHorario = tvMateriaHorario.getSelectionModel().getSelectedItem();
            lbSelecionado.setText("Seleção no Quadro Disciplina/Ambiente\n"
                    + "Disciplina: " + (materiaHorario == null ? "Não selecionado" : materiaHorario.getMateriaTurmaIntrutorSemestre().getMateria().getNome()) + "\n"
                    + "Ambiente: " + ambiente);
            TabelaHorario.ambienteSelecionado = ambiente;
            TabelaHorario.materiaHorarioSelecionado = materiaHorario;
        }
    }

    public class RenderAmbiente implements Callback<TableColumn<MateriaHorario, Ambiente>, TableCell<MateriaHorario, Ambiente>> {

        int coluna;

        public RenderAmbiente(int coluna) {
            this.coluna = coluna;
        }

        @Override
        public TableCell<MateriaHorario, Ambiente> call(TableColumn<MateriaHorario, Ambiente> param) {
            return new TableCell<MateriaHorario, Ambiente>() {
                @Override
                protected void updateItem(Ambiente item, boolean empty) {
                    if (empty) {
                        setText("");
                    } else if (item != null) {
                        setText(item.getNome());
                        setOnMouseClicked(new SelecionarAmbiente(item));
                    } else {
                        setText("");
                    }
                    switch (coluna) {
                        case 1:
                            setBackground(new Background(new BackgroundFill(corAmbiente1, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 2:
                            setBackground(new Background(new BackgroundFill(corAmbiente2, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 3:
                            setBackground(new Background(new BackgroundFill(corAmbiente3, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 4:
                            setBackground(new Background(new BackgroundFill(corAmbiente4, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 5:
                            setBackground(new Background(new BackgroundFill(corAmbiente5, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                    }
                }
            };
        }
    }

    public class InserirValorAmbiente implements Callback<TableColumn.CellDataFeatures<MateriaHorario, Ambiente>, ObservableValue<Ambiente>> {

        int coluna;

        public InserirValorAmbiente(int coluna) {
            this.coluna = coluna;
        }

        @Override
        public ObservableValue<Ambiente> call(TableColumn.CellDataFeatures<MateriaHorario, Ambiente> param) {
            MateriaHorarioAmbiente materiaHorarioAmbiente = new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorarioNumero(param.getValue(), coluna);
            return new SimpleObjectProperty<>(materiaHorarioAmbiente == null ? null : materiaHorarioAmbiente.getId().getAmbiente());
        }

    }
}
