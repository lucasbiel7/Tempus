
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorario;
import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.EventoDAO;
import br.com.QuadroDeHorario.control.dao.MateriaDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.control.dao.TurmaDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Evento;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
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
    @FXML
    private MenuItem miExcluir;
    @FXML
    private Button btProximoModulo;
    @FXML
    private Button btContinuarProximoSemestre;

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
    @FXML
    private Label lbDisciplinaDividida;

    private ObservableList<Evento> eventos = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Semestre> semestre = FXCollections.observableArrayList();
    private ObservableList<MateriaHorario> materiaHorarios = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Turno> turnosDiurmos = FXCollections.observableArrayList();

    private Turma turma;
    private Turma turmaEspelho;
    private SimpleObjectProperty<MateriaHorario> materiaHorario;
    private SimpleObjectProperty<Ambiente> ambiente;
    private SimpleObjectProperty<DataHorario.Turno> turno;

    //Isso aqui nao devia entrar no fxml mas entra
    //Nao sei porque um dia descubro acredito que e por causa que é constante
    //Enfim fazer o que né
    //Devia ter feito uma lista mas dexei pra outro dia
    public static final Color corAmbiente1 = Color.WHITE;
    public static final Color corAmbiente2 = Color.rgb(153, 255, 153);
    public static final Color corAmbiente3 = Color.rgb(255, 255, 102);
    public static final Color corAmbiente4 = Color.rgb(204, 204, 255);
    public static final Color corAmbiente5 = Color.rgb(255, 153, 255);
    public Task<Void> carregarDados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            turma = (Turma) apContainer.getUserData();
            btProximoModulo.setVisible(!turma.isEspelho());
            btContinuarProximoSemestre.setVisible(!turma.isEspelho());
            lbDisciplinaDividida.setVisible(turma.isEspelho());
            if (turma.isEspelho()) {
                turmaEspelho = turma;
                turma = turma.getTurmaPrincipal();
            }
            progressIndicator.setProgress(0d);
            if (turma.getTurno().equals(DataHorario.Turno.DIURNO)) {
                cbTurno.getSelectionModel().select(DataHorario.Turno.MANHA);
            } else {
                cbTurno.getSelectionModel().select(turma.getTurno());
            }
            cbTurno.setVisible(turma.getTurno() == DataHorario.Turno.DIURNO);
            if (turma != null) {
                Calendar calendar = Calendar.getInstance();
                List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarPorTurma(turma);
                materiaHorarios.removeIf((MateriaHorario materiaHorario) -> materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getModulo() != turma.getModulo());
                if (!materiaHorarios.isEmpty()) {
                    System.out.println(materiaHorarios.isEmpty());
                    spAno.getValueFactory().setValue(materiaHorarios.get(materiaHorarios.size() - 1).getAno());
                    cbSemestre.getSelectionModel().select(materiaHorarios.get(materiaHorarios.size() - 1).getMateriaTurmaInstrutorSemestre().getSemestre());
                }
                if (turma.getModulo() == 0) {
                    cbSemestre.getSelectionModel().select(DataHorario.Semestre.setSemestre(turma.getInicio()));
                    calendar.setTime(turma.getInicio());
                    spAno.getValueFactory().setValue(calendar.get(Calendar.YEAR));
                    turma.setModulo(1);
                    new TurmaDAO().editar(turma);
                    if (turmaEspelho == null) {
                        Turma turmaEspelho = new TurmaDAO().pegarPorTurmaPrincipal(turma);
                        if (turmaEspelho != null) {
                            turmaEspelho.setModulo(1);
                            new TurmaDAO().editar(turmaEspelho);
                            carregarMateria(turmaEspelho);
                        }
                    }
                }
                carregarMateria(turma);
                if (turmaEspelho != null) {
                    if (turmaEspelho.getModulo() == 0) {
                        cbSemestre.getSelectionModel().select(DataHorario.Semestre.setSemestre(turmaEspelho.getInicio()));
                        calendar.setTime(turma.getInicio());
                        spAno.getValueFactory().setValue(calendar.get(Calendar.YEAR));
                        turmaEspelho.setModulo(1);
                        new TurmaDAO().editar(turmaEspelho);
                    }
                    carregarMateria(turmaEspelho);
                }
                if (turma.getTurno().equals(DataHorario.Turno.DIURNO)) {
                    cbTurno.getSelectionModel().select(DataHorario.Turno.MANHA);
                } else {
                    cbTurno.getSelectionModel().select(turma.getTurno());
                }
                carregarTabelas();
            }
        });
        //Carregar componentes
        ambiente = new SimpleObjectProperty<>();
        materiaHorario = new SimpleObjectProperty<>();
        turno = new SimpleObjectProperty<>();
        turno.bind(cbTurno.valueProperty());
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
        tcDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria().toString()));
        tcInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaInstrutorSemestre().getInstrutor() != null ? param.getValue().getMateriaTurmaInstrutorSemestre().getInstrutor().toString() : ""));
        tcCargaHoraria.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria().getCargaHoraria())));
        tcCargaDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorDisciplinaTurma(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria(), turma).size())));
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
        turnosDiurmos.setAll(DataHorario.Turno.MANHA, DataHorario.Turno.TARDE);
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
    }

    @FXML
    private void tvMateriaHorarioActionEvent(ActionEvent actionEvent) {
        materiaHorario.set(tvMateriaHorario.getSelectionModel().getSelectedItem());
        if (materiaHorario != null) {
            FxMananger.show("EditarMateriaHorario", "Editar componente curricular do horário", true, false, materiaHorario.get());
            carregarTabelas();
        }
    }

    @FXML
    private void btProximoModuloActionEvent(ActionEvent actionEvent) {
        boolean valido = true;
        //Validar disciplinas
        List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarTodosPorTurma(turma);
        materiaHorarios.removeIf((MateriaHorario materiaHorario) -> materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getModulo() != turma.getModulo());
        for (MateriaHorario materiaHorario : materiaHorarios) {
            if (new AulaDAO().pegarPorDisciplinaTurma(materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria(), turma).size() < materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getCargaHoraria()) {
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
                    if (cbSemestre.getSelectionModel().getSelectedItem().equals(DataHorario.Semestre.SEMESTRE1)) {
                        passarDeModulo(spAno.getValue(), DataHorario.Semestre.SEMESTRE2);
                    } else {
                        passarDeModulo(spAno.getValue() + 1, DataHorario.Semestre.SEMESTRE1);
                    }
                } else if (t.equals(btSemestre)) {
                    passarDeModulo(spAno.getValue(), cbSemestre.getSelectionModel().getSelectedItem());
                }
            });
        } else if (!modulos) {
            Mensagem.showError("Curso completo", "Não há mais módulos para o curso " + turma.getCurso() + "!");
        } else {
            Mensagem.showError("Módulo incompleto", "Não é possível ir para o próximo módulo porque possui\n"
                    + "componentes curriculares que não alcançaram o plano de curso.");
        }
    }

    @FXML
    private void btContinuarModuloProximoSemestreActionEvent(ActionEvent actionEvent) {
        continuarProximoSemestre(turma, false);
        Turma turmaEspelho = new TurmaDAO().pegarPorTurmaPrincipal(turma);
        if (turmaEspelho != null) {
            continuarProximoSemestre(turmaEspelho, true);
        }
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        if (tvMateriaHorario.getSelectionModel().getSelectedItem() != null) {
            MateriaHorario materiaHorario = tvMateriaHorario.getSelectionModel().getSelectedItem();
            if (materiaHorario.isSubistito()) {
                if (Mensagem.showConfirmation("Excluir componente curricular do quadro", "Cuidado!\n"
                        + "Ao excluir um componente curricular do quadro de horário você estará apagando TODAS as \n"
                        + "aulas registradas para o componente curricular/Instrutor. Deseja Continuar?")) {
                    new MateriaHorarioDAO().excluir(materiaHorario);
                    carregarTabelas();
                }
            } else {
                Mensagem.showError("Componente curricular principal", "Não é possível apagar o componente curricular principal, só será\n"
                        + " possível nos componente curricular em que os intrutores são substitutos!");
            }
        } else {
            Mensagem.showError("Selecione um componente curricular", "Para excluir um componente curricular é necessário primeiro seleciona-lo!");
        }
    }

    @FXML
    private void cbTurnoActionEvent(ActionEvent actionEvent) {
        carregarTabelas();
    }

    public void carregarTabelas() {
        new Thread(() -> {
            ambiente.set(null);
            materiaHorario.set(null);
            Platform.runLater(() -> {
                if (turmaEspelho != null) {
                    lbTurma.setText("Turma\n" + turmaEspelho.getDescricao() + "-" + turmaEspelho.getModulo());
                    lbTurma.setTooltip(new Tooltip("Início " + DataHorario.toDate(turmaEspelho.getInicio()) + "\naté " + DataHorario.toDate(turmaEspelho.getFim())));
                } else {
                    lbTurma.setText("Turma\n" + turma.getDescricao() + "-" + turma.getModulo());
                    lbTurma.setTooltip(new Tooltip("Início " + DataHorario.toDate(turma.getInicio()) + "\naté " + DataHorario.toDate(turma.getFim())));
                }
                materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                if (turmaEspelho != null) {
                    materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                }
                eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(spAno.getValue(), true));
            });
        }, "Carregando disciplinas do semestre").start();
        if (carregarDados != null) {
            if (carregarDados.isRunning()) {
                carregarDados.cancel();
            }
        }
        carregarDados = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateProgress(0, 6);
                Platform.runLater(() -> {
                    gpMeses.getChildren().clear();
                });
                if (cbSemestre.getSelectionModel().getSelectedItem() != null) {
                    for (int i = 1; i < 7; i++) {
                        TabelaHorario tabelaHorario = new TabelaHorario(
                                cbSemestre.getSelectionModel().getSelectedItem().equals(DataHorario.Semestre.SEMESTRE1) ? i : i + 6, spAno.getValue(),
                                turma,
                                turmaEspelho,
                                cbAutoPreencher.selectedProperty(),
                                cbAulasGerminadas.selectedProperty(),
                                materiaHorario,
                                ambiente,
                                turno);
                        tabelaHorario.addEventHandler(EventType.ROOT, new CarregarMateriaHorario());
                        int posicao = i - 1;
                        Platform.runLater(() -> {
                            if (!gpMeses.getChildren().contains(tabelaHorario)) {
                                gpMeses.add(tabelaHorario, 0, posicao);
                            }
                        });
                        updateProgress(i, 6);
                    }
                }
                return null;
            }
        };
        progressIndicator.progressProperty().bind(carregarDados.progressProperty());
        carregarDados.setOnSucceeded((WorkerStateEvent event) -> {
            progressIndicator.setVisible(false);
        });
        if (!carregarDados.isRunning()) {
            new Thread(carregarDados, "Carregando dados...").start();
        }
    }

    public void continuarProximoSemestre(Turma turma, boolean espelho) {
        DataHorario.Semestre ultimoSemestre = null;
        int anoSemestre = 0;
        List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarTodosPorTurma(turma);
        materiaHorarios.removeIf((MateriaHorario materiaHorario) -> materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getModulo() != turma.getModulo());
        for (MateriaHorario materiaHorario : materiaHorarios) {
            if (new AulaDAO().pegarPorDisciplinaTurma(materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria(), (espelho ? turma.getTurmaPrincipal() : turma)).size() < materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getCargaHoraria()) {
                materiaHorario.setId(0);
                materiaHorario.setMateriaHorarioAmbientes(new ArrayList<>());
                materiaHorario.setAulas(new ArrayList<>());
                if (materiaHorario.getMateriaTurmaInstrutorSemestre().getSemestre().equals(DataHorario.Semestre.SEMESTRE1)) {
                    materiaHorario.getMateriaTurmaInstrutorSemestre().setSemestre(DataHorario.Semestre.SEMESTRE2);
                    ultimoSemestre = DataHorario.Semestre.SEMESTRE2;
                    anoSemestre = materiaHorario.getAno();
                } else {
                    materiaHorario.getMateriaTurmaInstrutorSemestre().setSemestre(DataHorario.Semestre.SEMESTRE1);
                    ultimoSemestre = DataHorario.Semestre.SEMESTRE1;
                    anoSemestre = materiaHorario.getAno() + 1;
                    materiaHorario.setAno(materiaHorario.getAno() + 1);
                }
                new MateriaHorarioDAO().cadastrar(materiaHorario);
            }
        }
        Mensagem.showInformation("Semestre no próximo semestre",
                "A turma " + turma + " terá continuação de seu módulo no semestre " + ultimoSemestre + "\n"
                + "do ano " + anoSemestre + ".");
    }

    private void passarDeModulo(Integer ano, DataHorario.Semestre semestre) {
        turma.setModulo(turma.getModulo() + 1);
        if (turma.getCurso().getModulo() >= turma.getModulo()) {
            new TurmaDAO().editar(turma);
            for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turma.getCurso(), turma.getModulo())) {
                MateriaHorario materiaHorario = new MateriaHorario();
                materiaHorario.getMateriaTurmaInstrutorSemestre().setTurma(turma);
                materiaHorario.getMateriaTurmaInstrutorSemestre().setSemestre(semestre);
                materiaHorario.getMateriaTurmaInstrutorSemestre().setMateria(materia);
                materiaHorario.setAno(ano);
                materiaHorario.setRed(new Random().nextInt(255));
                materiaHorario.setGreen(new Random().nextInt(255));
                materiaHorario.setBlue(new Random().nextInt(255));
                if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turma, materia).isEmpty()) {
                    new MateriaHorarioDAO().cadastrar(materiaHorario);
                }
            }
            Turma turmaEspelho = new TurmaDAO().pegarPorTurmaPrincipal(turma);
            if (turmaEspelho != null) {
                turmaEspelho.setModulo(turmaEspelho.getModulo() + 1);
                if (turmaEspelho.getCurso().getModulo() >= turmaEspelho.getModulo()) {
                    new TurmaDAO().editar(turmaEspelho);
                    for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turmaEspelho.getCurso(), turmaEspelho.getModulo())) {
                        MateriaHorario materiaHorario = new MateriaHorario();
                        materiaHorario.getMateriaTurmaInstrutorSemestre().setTurma(turmaEspelho);
                        materiaHorario.getMateriaTurmaInstrutorSemestre().setSemestre(semestre);
                        materiaHorario.getMateriaTurmaInstrutorSemestre().setMateria(materia);
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
        }
    }

    private void carregarMateria(Turma turma) {
        for (Materia materia : new MateriaDAO().pegarTodosPorCursoModulo(turma.getCurso(), turma.getModulo())) {
            MateriaHorario materiaHorario = new MateriaHorario();
            materiaHorario.setAno(spAno.getValue());
            materiaHorario.getMateriaTurmaInstrutorSemestre().setMateria(materia);
            materiaHorario.getMateriaTurmaInstrutorSemestre().setTurma(turma);
            materiaHorario.getMateriaTurmaInstrutorSemestre().setSemestre(DataHorario.Semestre.setSemestre(turma.getInicio()));
            materiaHorario.setRed(new Random().nextInt(255));
            materiaHorario.setGreen(new Random().nextInt(255));
            materiaHorario.setBlue(new Random().nextInt(255));
            if (new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turma, materia).isEmpty()) {
                new MateriaHorarioDAO().cadastrar(materiaHorario);
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
                                    System.out.println("Enchendo a lista de materias horarios da turma espelho");
                                    materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                                }
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
            lbSelecionado.setText("Seleção no Quadro de componentes curriculares/Ambiente\n"
                    + "Componente curricular: " + (materiaHorario == null ? "Não selecionado" : materiaHorario.getMateriaTurmaInstrutorSemestre().getMateria().getNome()) + "\n"
                    + "Ambiente: " + ambiente);
            QuadroDeHorarioController.this.ambiente.set(ambiente);
            QuadroDeHorarioController.this.materiaHorario.set(materiaHorario);
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
