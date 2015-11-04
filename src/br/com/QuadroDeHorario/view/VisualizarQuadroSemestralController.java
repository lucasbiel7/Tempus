/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorarioImpressao;
import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.MateriaHorario;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.DataHorario;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente1;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente2;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente3;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente4;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente5;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VisualizarQuadroSemestralController implements Initializable {

    @FXML
    private TableView<MateriaHorario> tvMateriaHorario;
    @FXML
    private TableColumn<MateriaHorario, String> tcSigla;
    @FXML
    private TableColumn<MateriaHorario, String> tcDisciplina;
    @FXML
    private TableColumn<MateriaHorario, String> tcInstrutor;
    @FXML
    private TableColumn<MateriaHorario, String> tcTurma;
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
    private AnchorPane apContainer;
    @FXML
    private VBox vbHorarios;

    private ObservableList<MateriaHorario> materiaHorarios = FXCollections.observableArrayList();
    private Task<Void> carregarTabelas;
    private Turma turma;
    private DataHorario.Turno turno;
    private Usuario usuario;
    private Ambiente ambiente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ((Stage) apContainer.getScene().getWindow()).setFullScreen(true);
            apContainer.getScene().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    ((Stage) apContainer.getScene().getWindow()).close();
                }
            });
            apContainer.setOnZoomFinished((ZoomEvent event) -> {
                Platform.runLater(() -> {
                    ((Stage) apContainer.getScene().getWindow()).close();
                });
                event.consume();
            });
            if (apContainer.getUserData() != null && apContainer.getUserData() instanceof Object[]) {
                Object[] parametros = (Object[]) apContainer.getUserData();
                for (Object parametro : parametros) {
                    if (parametro instanceof Turma) {
                        turma = (Turma) parametro;
                    } else if (parametro instanceof DataHorario.Turno) {
                        turno = (DataHorario.Turno) parametro;
                    } else if (parametro instanceof Usuario) {
                        usuario = (Usuario) parametro;
                        tcInstrutor.setText("Turma");
                    } else if (parametro instanceof Ambiente) {
                        ambiente = (Ambiente) parametro;
                    }
                }
                new Thread(carregarTabelas, "carregarTabelas").start();
            }
        });
        tcSigla.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().toString()));
        tcDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria().toString()));
        tcInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaIntrutorSemestre().getInstrutor() != null ? param.getValue().getMateriaTurmaIntrutorSemestre().getInstrutor().toString() : ""));
        tcTurma.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaIntrutorSemestre().getTurma() != null ? param.getValue().getMateriaTurmaIntrutorSemestre().getTurma().toString() : ""));
        tcCargaHoraria.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria().getCargaHoraria())));
        tcCargaDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorDisciplinaTurma(param.getValue().getMateriaTurmaIntrutorSemestre().getMateria(), param.getValue().getMateriaTurmaIntrutorSemestre().getTurma()).size())));
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
        tvMateriaHorario.setItems(materiaHorarios);
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
        carregarTabelas = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    vbHorarios.getChildren().clear();
                });
                carregarTabelas();
                return getValue();
            }
        };

    }

    private void carregarTabelas() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new UsuarioDAO().dataAtual());
        if (turma != null) {
            //Carrengando matérias horarios que correponde a unidade curricular daquela turma para o semestre atual de acordo com servidor
            //LIKE A STONE(8)
            Platform.runLater(() -> {
                tcTurma.setVisible(false);
            });
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, DataHorario.Semestre.semestreAtual(), calendar.get(Calendar.YEAR)));
            for (int i = 1; i < 7; i++) {
                TabelaHorarioImpressao tabelaHorarioImpressao = new TabelaHorarioImpressao(DataHorario.Semestre.semestreAtual().equals(DataHorario.Semestre.SEMESTRE1) ? i : i + 6, calendar.get(Calendar.YEAR), turma);
                tabelaHorarioImpressao.getStyleClass().remove("tabelaImpressao");
                tabelaHorarioImpressao.setMinSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setPrefSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                Platform.runLater(() -> {
                    vbHorarios.getChildren().add(tabelaHorarioImpressao);
                });
            }
        } else if (usuario != null && turno != null) {
            Platform.runLater(() -> {
                tcInstrutor.setVisible(false);
            });
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorInstrutorTurnoSemestreAno(usuario, turno, DataHorario.Semestre.semestreAtual(), calendar.get(Calendar.YEAR)));
            TabelaHorarioImpressao.turno = turno;
            for (int i = 1; i < 7; i++) {
                TabelaHorarioImpressao tabelaHorarioImpressao = new TabelaHorarioImpressao(DataHorario.Semestre.semestreAtual().equals(DataHorario.Semestre.SEMESTRE1) ? i : i + 6, calendar.get(Calendar.YEAR), usuario);
                tabelaHorarioImpressao.getStyleClass().remove("tabelaImpressao");
                tabelaHorarioImpressao.setMinSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setPrefSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                Platform.runLater(() -> {
                    vbHorarios.getChildren().add(tabelaHorarioImpressao);
                });
            }
        } else if (ambiente != null && turno != null) {
            Platform.runLater(() -> {
                tcAmbiente1.setVisible(false);
                tcAmbiente2.setVisible(false);
                tcAmbiente3.setVisible(false);
                tcAmbiente4.setVisible(false);
                tcAmbiente5.setVisible(false);
            });
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorAmbienteTurnoSemestreAno(ambiente, turno, DataHorario.Semestre.semestreAtual(), calendar.get(Calendar.YEAR)));
            TabelaHorarioImpressao.turno = turno;
            for (int i = 1; i < 7; i++) {
                TabelaHorarioImpressao tabelaHorarioImpressao = new TabelaHorarioImpressao(DataHorario.Semestre.semestreAtual().equals(DataHorario.Semestre.SEMESTRE1) ? i : i + 6, calendar.get(Calendar.YEAR), ambiente);
                tabelaHorarioImpressao.getStyleClass().remove("tabelaImpressao");
                tabelaHorarioImpressao.setMinSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setPrefSize(Control.USE_COMPUTED_SIZE, 195);
                tabelaHorarioImpressao.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                Platform.runLater(() -> {
                    vbHorarios.getChildren().add(tabelaHorarioImpressao);
                });
            }
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
