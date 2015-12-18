
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorario;
import br.com.QuadroDeHorario.control.TabelaHorarioImpressao;
import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.DataHorario.Semestre;
import br.com.QuadroDeHorario.model.util.Mensagem;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente1;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente2;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente3;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente4;
import static br.com.QuadroDeHorario.view.QuadroDeHorarioController.corAmbiente5;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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

    //Cabecalho
    @FXML
    private Label lbNomeQuadro;
    @FXML
    private Label lbSemestre;
    @FXML
    private Label lbData;
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

    //Fim Matéria Horario
    private ObservableList<DataHorario.Semestre> semestres = FXCollections.observableArrayList();
    private ObservableList<MateriaHorario> materiaHorarios = FXCollections.observableArrayList();

    private TabelaHorarioImpressao thiMes1;
    private TabelaHorarioImpressao thiMes2;
    private TabelaHorarioImpressao thiMes3;
    private TabelaHorarioImpressao thiMes4;
    private TabelaHorarioImpressao thiMes5;
    private TabelaHorarioImpressao thiMes6;

    private Usuario usuario;
    private DataHorario.Turno turno;
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
                    }
                    if (parametro instanceof DataHorario.Turno) {
                        turno = (DataHorario.Turno) parametro;
                    }
                }
                carregarTabelas();
            }
            carregarCabecalho();
            tcInstrutor.setText(turma != null ? "Instrutor" : "Turma");
        });
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
        lbSemestre.setText(cbSemestre.getSelectionModel().getSelectedItem() + "/" + spAno.getValue() + (turno != null ? (" Turno: " + turno) : ""));
        lbData.setText(sdfData.format(new Date()));
        carregarMateriaHorario();
    }

    //Todas as tabelas 
    public void carregarTabelas() {
        Object dados = null;
        if (turma != null) {
            dados = turma;
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
            if (turmaEspelho != null) {
                materiaHorarios.addAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turmaEspelho, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
            }
            TabelaHorarioImpressao.turno = null;
        } else if (usuario != null && turno != null) {
            dados = usuario;
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorInstrutorTurnoSemestreAno(usuario, turno, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
            TabelaHorarioImpressao.turno = turno;
        }
        int mesInicial;
        if (cbSemestre.getSelectionModel().getSelectedItem().equals(DataHorario.Semestre.SEMESTRE1)) {
            mesInicial = 1;
        } else {
            mesInicial = 7;
        }
        thiMes1 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        mesInicial++;
        thiMes2 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        mesInicial++;
        thiMes3 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        mesInicial++;
        thiMes4 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        mesInicial++;
        thiMes5 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        mesInicial++;
        thiMes6 = new TabelaHorarioImpressao(mesInicial, spAno.getValue(), dados);
        TabelaHorario.ambienteSelecionado = null;
        TabelaHorario.materiaHorarioSelecionado = null;
        Platform.runLater(() -> {
            gpMeses.getChildren().clear();
            gpMeses.addRow(0, thiMes1);
            gpMeses.addRow(1, thiMes2);
            gpMeses.addRow(2, thiMes3);
            gpMeses.addRow(3, thiMes4);
            gpMeses.addRow(4, thiMes5);
            gpMeses.addRow(5, thiMes6);
        });
    }

    //CarregarMateriaHoratrio
    public void carregarMateriaHorario() {
        if (turma != null) {
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorTurmaSemestreAno(turma, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
        } else if (usuario != null) {
            materiaHorarios.setAll(new MateriaHorarioDAO().pegarTodosPorInstrutorTurnoSemestreAno(usuario, turno, cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()));
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
