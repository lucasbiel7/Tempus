
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorarioImpressao;
import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.control.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.DataHorario.Semestre;
import br.com.QuadroDeHorario.model.util.DataHorario.Turno;
import br.com.QuadroDeHorario.model.util.Mensagem;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente1;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente2;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente3;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente4;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente5;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ImprimirQuadroController implements Initializable {

    @FXML
    private AnchorPane apContainer;
    @FXML
    private VBox quadro;
    @FXML
    private ComboBox<DataHorario.Semestre> cbSemestre;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private GridPane gpMeses;
    @FXML
    private ProgressIndicator piLoader;
    @FXML
    private Label lbNomeQuadro;
    @FXML
    private Label lbSemestre;
    @FXML
    private Label lbData;
    @FXML
    private Label lbEscola;
    @FXML
    private ImageView ivEscola;
    @FXML
    private ComboBox<Turno> cbTurno;
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

    //Tabela matéria Horário
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

    private Task<Void> carregarTabelas;
    //Fim Matéria Horario
    private ObservableList<DataHorario.Semestre> semestres = FXCollections.observableArrayList();
    private ObservableList<MateriaHorario> materiaHorarios = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Turno> turnos = FXCollections.observableArrayList();

    private Usuario usuario;
    private Turma turma;
    private Turma turmaEspelho;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Object[] parametros = (Object[]) apContainer.getUserData();
            stage = (Stage) apContainer.getScene().getWindow();
            if (parametros != null) {
                for (Object parametro : parametros) {
                    if (parametro instanceof Usuario) {
                        usuario = (Usuario) parametro;
                    }
                    if (parametro instanceof Turma) {
                        turma = (Turma) parametro;
                        if (turma.getTurno().equals(Turno.DIURNO)) {
                            cbTurno.setVisible(true);
                            cbTurno.getSelectionModel().select(Turno.MANHA);
                            turnos.remove(Turno.NOITE);
                        } else {
                            cbTurno.getSelectionModel().select(turma.getTurno());
                            cbTurno.setVisible(false);
                        }
                    }
                    if (parametro instanceof DataHorario.Turno) {
                        cbTurno.getSelectionModel().select((Turno) parametro);
                    }
                }
                carregarTabelas();
            }
            carregarCabecalho();
            tcInstrutor.setText(turma != null ? "Instrutor" : "Turma");
        });
        VariaveisDoSistema nomeDoPrograma = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.ESCOLA);
        if (nomeDoPrograma != null) {
            lbEscola.setText(nomeDoPrograma.getValor().replace("\\n", ""));
            if (nomeDoPrograma.getFoto() != null) {
                ivEscola.setImage(new Image(new ByteArrayInputStream(nomeDoPrograma.getFoto())));
            } else {
                ivEscola.setImage(null);
            }
        }

        cbSemestre.setItems(semestres);
        semestres.setAll(Semestre.values());
        cbSemestre.getSelectionModel().select(DataHorario.Semestre.semestreAtual());
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
        //Tabela MateriaHorario
        tcSigla.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().toString()));
        tcDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria().toString()));
        tcInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> {
            if (turma != null) {
                return new SimpleStringProperty(param.getValue().getMateriaTurmaInstrutorSemestre().getInstrutor() != null ? param.getValue().getMateriaTurmaInstrutorSemestre().getInstrutor().toString() : "");
            } else {
                return new SimpleStringProperty(param.getValue().getMateriaTurmaInstrutorSemestre().getTurma().toString());
            }
        });
        tcCargaHoraria.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria().getCargaHoraria())));
        tcCargaDisciplina.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorDisciplinaTurma(param.getValue().getMateriaTurmaInstrutorSemestre().getMateria(), param.getValue().getMateriaTurmaInstrutorSemestre().getTurma()).size())));
        tcCargaInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<MateriaHorario, String> param) -> {
            return new SimpleStringProperty(String.valueOf(new AulaDAO().pegarPorMateria(param.getValue()).size()));
        });
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
                    getChildren().stream().forEach((node) -> {
                        node.setStyle("-fx-text-fill: rgb(" + item.getRed() + "," + item.getGreen() + "," + item.getBlue() + ");");
                    });
                }
            }
        });
        tvMateriaHorario.setItems(materiaHorarios);
        cbTurno.setItems(turnos);
        turnos.setAll(Turno.values());
        turnos.remove(Turno.DIURNO);
    }

    @FXML
    private void cbSemestreActionEvent(ActionEvent actionEvent) {
        carregarMateriaHorario();
        carregarCabecalho();
        carregarTabelas();
    }

    @FXML
    private void spAnoMouseReleased(MouseEvent mouseEvent) {
        cbSemestreActionEvent(null);
    }

    @FXML
    private void cbTurnoActionEvent(ActionEvent actionEvent) {
        cbSemestreActionEvent(null);
    }

    //Metodos de evento
    //Metodos de carregamento
    //Cabecalho
    public void carregarCabecalho() {
        if (turma != null) {
            lbNomeQuadro.setText("Turma: " + turma.getDescricao());
        }
        if (usuario != null) {
            lbNomeQuadro.setText("Instrutor: " + usuario.getNome());
        }
        lbSemestre.setText(cbSemestre.getSelectionModel().getSelectedItem() + "/" + spAno.getValue() + (cbTurno.getSelectionModel().getSelectedItem() != null ? (" Turno: " + cbTurno.getSelectionModel().getSelectedItem()) : ""));
        lbData.setText(sdfData.format(new Date()));
        carregarMateriaHorario();
    }

    //Todas as tabelas 
    public void carregarTabelas() {
//        TabelaHorario.ambienteSelecionado = null;
//        TabelaHorario.materiaHorarioSelecionado = null;
        carregarTabelas = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateProgress(0, 8);
                Object dados = null;
                if (turma != null) {
                    dados = turma;
                    Platform.runLater(() -> {
                        materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                    });
                    if (turmaEspelho != null) {
                        Platform.runLater(() -> {
                            materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                        });
                    }
                } else if (usuario != null && cbTurno.getSelectionModel().getSelectedItem() != null) {
                    dados = usuario;
                    Platform.runLater(() -> {
                        materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorInstrutorTurnoSemestreAno(usuario, cbTurno.getSelectionModel().getSelectedItem(), cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
                    });
                }
                Platform.runLater(() -> {
                    gpMeses.getChildren().clear();
                });
                updateProgress(1, 8);
                for (int i = 1; i < 7; i++) {
                    TabelaHorarioImpressao tabelaHorarioImpressao = new TabelaHorarioImpressao((cbSemestre.getSelectionModel().getSelectedItem().equals(Semestre.SEMESTRE1)) ? i : i + 6, spAno.getValue(), dados, cbTurno.getSelectionModel().getSelectedItem());
                    final int linha = i - 1;
                    Platform.runLater(() -> {
                        gpMeses.add(tabelaHorarioImpressao, 0, linha);
                    });
                    updateProgress(i + 1, 8);
                }
                updateProgress(8, 8);
                return null;
            }
        };
        piLoader.progressProperty().bind(carregarTabelas.progressProperty());
        carregarTabelas.setOnSucceeded((WorkerStateEvent event) -> {
            piLoader.setVisible(false);
            event.consume();
        });
        if (carregarTabelas != null) {
            if (carregarTabelas.isRunning()) {
                carregarTabelas.cancel();
            }
        }
        new Thread(carregarTabelas, "Carregando imprimir Quadro").start();
    }

    //CarregarMateriaHoratrio
    public void carregarMateriaHorario() {
        if (turma != null) {
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
        } else if (usuario != null) {
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorInstrutorTurnoSemestreAno(usuario, cbTurno.getSelectionModel().getSelectedItem(), cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
        }
    }

    @FXML
    private void btImprimirActionEvent(ActionEvent actionEvent) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 20, 0, 20, 0);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        WritableImage wi = quadro.snapshot(new SnapshotParameters(), (wi = null));
        ImageView imageView = new ImageView(wi);
        imageView.setFitHeight(pageLayout.getPrintableHeight());
        imageView.setFitWidth(pageLayout.getPrintableWidth());
        imageView.setPreserveRatio(true);
        if (printerJob.showPrintDialog(stage)) {
            boolean sucess = printerJob.printPage(pageLayout, imageView);
            printerJob.endJob();
            if (sucess) {
                Mensagem.showInformation("Impressão concluída", "Impressão do quadro foi executada com sucesso!");
            } else {
                Mensagem.showInformation("Impressão falhou", "ocorreu um erro ao realizar a impressão do quadro\n"
                        + "de horário. Verifique a existencia da impressora!");
            }
        }
    }

    //Classes aninhadas
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
                        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else if (item != null) {
                        setText(item.getNome());
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
                    } else {
                        setText("");
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
