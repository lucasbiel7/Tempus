/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AmbienteDAO;
import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.EmprestaChaveDAO;
import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.TurmaDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.HorarioDiario;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author OCTI01
 */
public class TabelaVisualizarHorario extends TableView<HorarioDiario> {

    public static DataHorario.Turno turno;
    public static Date dia;
    private TableColumn<HorarioDiario, HorarioDiario> tcPrimeiroHorario;
    private final TableColumn<HorarioDiario, HorarioDiario> tcSegundoHorario;
    private final TableColumn<HorarioDiario, HorarioDiario> tcTerceiroHorario;
    private final TableColumn<HorarioDiario, HorarioDiario> tcQuartoHorario;
    private final TableColumn<HorarioDiario, HorarioDiario> tcQuintoHorario;
    private final TableColumn<HorarioDiario, String> tcTipo;
    private TableColumn<HorarioDiario, String> tcNome;
    private TableColumn<HorarioDiario, String> tcDados;
    private final ObservableList<HorarioDiario> horarioDiarios;
    private final Class classe;

    public TabelaVisualizarHorario(DataHorario.Turno turno, Class classe, Date dia) {
        this.horarioDiarios = FXCollections.observableArrayList();
        this.classe = classe;
        this.dia = dia;
        this.turno = turno;
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        tcTipo = new TableColumn<>(classe.equals(Turma.class) ? "Turmas" : classe.equals(Usuario.class) ? "Instrutor" : "Ambiente");
        tcPrimeiroHorario = new TableColumn<>("Tipo");
        tcPrimeiroHorario = new TableColumn<>(DataHorario.Horario.HORARIO1.toString());
        tcSegundoHorario = new TableColumn<>(DataHorario.Horario.HORARIO2.toString());
        tcTerceiroHorario = new TableColumn<>(DataHorario.Horario.HORARIO3.toString());
        tcQuartoHorario = new TableColumn<>(DataHorario.Horario.HORARIO4.toString());
        tcQuintoHorario = new TableColumn<>(DataHorario.Horario.HORARIO5.toString());
        tcNome = new TableColumn<>("Nome");
        tcDados = new TableColumn<>("Dados");
        tcNome.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, String> param) -> new SimpleStringProperty(param.getValue().getObjeto().toString()));
        tcPrimeiroHorario.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, HorarioDiario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcSegundoHorario.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, HorarioDiario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcTerceiroHorario.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, HorarioDiario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcQuartoHorario.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, HorarioDiario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcQuintoHorario.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, HorarioDiario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcDados.setMaxWidth(100d);
        tcNome.setMaxWidth(100d);
        tcNome.setPrefWidth(100d);
        tcDados.setPrefWidth(100d);
        tcDados.setMinWidth(100d);
        tcNome.setMinWidth(100d);
        setRowFactory((TableView<HorarioDiario> param) -> new TableRow<HorarioDiario>() {
            @Override
            protected void updateItem(HorarioDiario item, boolean empty) {
                setMinHeight(60d);
            }
        });
        tcPrimeiroHorario.setCellFactory(new RenderizarHorarios(1));
        tcSegundoHorario.setCellFactory(new RenderizarHorarios(2));
        tcTerceiroHorario.setCellFactory(new RenderizarHorarios(3));
        tcQuartoHorario.setCellFactory(new RenderizarHorarios(4));
        tcQuintoHorario.setCellFactory(new RenderizarHorarios(5));
        tcDados.setCellValueFactory((TableColumn.CellDataFeatures<HorarioDiario, String> param) -> {
            String sequenciaDados;
            if (param.getValue().getObjeto() instanceof Usuario) {
                sequenciaDados = "Comp. curricular\n"
                        + "Turma\n"
                        + "Ambiente";
            } else if (param.getValue().getObjeto() instanceof Turma) {
                sequenciaDados = "Comp. curricular\n"
                        + "Ambiente\n"
                        + "Instrutor";
            } else {
                sequenciaDados = "Comp. curricular\n"
                        + "Instrutor\n"
                        + "Turma";
            }
            return new SimpleStringProperty(sequenciaDados);
        });
        tcNome.setCellFactory((TableColumn<HorarioDiario, String> param) -> new TableCell<HorarioDiario, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                    setFont(Font.font("system", FontWeight.BOLD, USE_PREF_SIZE));
                    setTextFill(Color.WHITE);
                }
                setBackground(new Background(new BackgroundFill(Color.rgb(130, 151, 178), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        tcDados.setCellFactory((TableColumn<HorarioDiario, String> param) -> new TableCell<HorarioDiario, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                setBackground(new Background(new BackgroundFill(Color.rgb(205, 209, 218), CornerRadii.EMPTY, Insets.EMPTY)));
                setBorder(new Border(new BorderStroke(Color.rgb(229, 233, 244), BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                if (empty) {
                    setText("");
                } else {
                    setText(item);
                }
            }
        });
        getSelectionModel().cellSelectionEnabledProperty().setValue(Boolean.TRUE);
        AnchorPane.setBottomAnchor(this, 0d);
        AnchorPane.setRightAnchor(this, 0d);
        AnchorPane.setLeftAnchor(this, 0d);
        AnchorPane.setTopAnchor(this, 0d);
        carregarColunas();
        carregarDados();
        setItems(horarioDiarios);
    }

    public class RenderizarHorarios implements Callback<TableColumn<HorarioDiario, HorarioDiario>, TableCell<HorarioDiario, HorarioDiario>> {

        private int horario;

        public RenderizarHorarios(int horario) {
            this.horario = horario;
        }

        @Override
        public TableCell<HorarioDiario, HorarioDiario> call(TableColumn<HorarioDiario, HorarioDiario> param) {
            return new TableCell<HorarioDiario, HorarioDiario>() {
                @Override
                protected void updateItem(HorarioDiario item, boolean empty) {
                    setTextAlignment(TextAlignment.CENTER);
                    setAlignment(Pos.CENTER);
                    if (empty) {
                        setText("");
                    } else if (item != null) {
                        Aula aula;
                        switch (horario) {
                            case 1:
                                aula = item.getAula1Horario();
                                break;
                            case 2:
                                aula = item.getAula2Horario();
                                break;
                            case 3:
                                aula = item.getAula3Horario();
                                break;
                            case 4:
                                aula = item.getAula4Horario();
                                break;
                            case 5:
                                aula = item.getAula5Horario();
                                break;
                            default:
                                throw new AssertionError();
                        }
                        if (aula != null) {
                            if (classe.equals(Turma.class)) {
                                setText(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getSigla() + "\n"
                                        + aula.getAmbiente() + "\n"
                                        + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor().getNome());
                                setTooltip(new Tooltip(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getNome()));
                            } else if (classe.equals(Usuario.class)) {
                                setText(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getSigla() + "\n"
                                        + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma().getDescricao() + "\n"
                                        + aula.getAmbiente());
                                setTooltip(new Tooltip(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getNome()));
                            } else if (classe.equals(Ambiente.class)) {
                                setText(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getSigla() + "\n"
                                        + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor().getNome() + "\n"
                                        + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma().getDescricao());
                                setTooltip(new Tooltip(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().getNome()));
                                if (new EmprestaChaveDAO().pegarPorAula(aula).isEmpty()) {
                                    setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                                } else {
                                    setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                                }
                            }
                        } else {
                            setText("");
                            if (classe.equals(Ambiente.class)) {
                                List<CalendarioAmbiente> calendarioAmbientes = new CalendarioAmbienteDAO().pegarTodosPorDataAmbienteTurno(dia, (Ambiente) item.getObjeto(), turno);
                                if (!calendarioAmbientes.isEmpty()) {
                                    setText("Evento\n"
                                            + calendarioAmbientes.get(0).getId().getCalendario().getId().getEvento());
                                    setTooltip(null);
                                } else {
                                    setText("");
                                }
                            } else if (classe.equals(Usuario.class)) {
                                List<CalendarioUsuario> calendarioUsuario = new CalendarioUsuarioDAO().pegarTodosPorUsuarioData((Usuario) item.getObjeto(), dia, turno);
                                if (!calendarioUsuario.isEmpty()) {
                                    setText("Evento\n"
                                            + calendarioUsuario.get(0).getId().getCalendario().getId().getEvento());
                                    setTooltip(null);
                                } else {
                                    setText("");
                                    setTooltip(null);
                                }
                            }
                        }
                    } else {
                        setText("");
                    }
                }
            };
        }
    }

    //MULHEEEER DE FAAASEES(8)
    private void carregarDados() {
        horarioDiarios.clear();
        if (classe.equals(Turma.class)) {
            for (Turma turma : new TurmaDAO().pegarTodasEntreDataTurno(dia, turno)) {
                HorarioDiario horarioDiario = new HorarioDiario();
                horarioDiario.setObjeto(turma);
                horarioDiario.setAula1Horario(new AulaDAO().pegarPorHorarioDiaTurmaTurno(DataHorario.Horario.HORARIO1, dia, turma, turno));
                horarioDiario.setAula2Horario(new AulaDAO().pegarPorHorarioDiaTurmaTurno(DataHorario.Horario.HORARIO2, dia, turma, turno));
                horarioDiario.setAula3Horario(new AulaDAO().pegarPorHorarioDiaTurmaTurno(DataHorario.Horario.HORARIO3, dia, turma, turno));
                horarioDiario.setAula4Horario(new AulaDAO().pegarPorHorarioDiaTurmaTurno(DataHorario.Horario.HORARIO4, dia, turma, turno));
                horarioDiario.setAula5Horario(new AulaDAO().pegarPorHorarioDiaTurmaTurno(DataHorario.Horario.HORARIO5, dia, turma, turno));
                if (turma.isEspelho()) {
                    if (horarioDiario.getAula1Horario() != null || horarioDiario.getAula2Horario() != null || horarioDiario.getAula3Horario() != null || horarioDiario.getAula4Horario() != null || horarioDiario.getAula5Horario() != null) {
                        horarioDiarios.add(horarioDiario);
                    }
                } else {
                    horarioDiarios.add(horarioDiario);
                }
            }
        } else if (classe.equals(Ambiente.class)) {
            for (Ambiente ambiente : new AmbienteDAO().pegarTodos()) {
                HorarioDiario horarioDiario = new HorarioDiario();
                horarioDiario.setObjeto(ambiente);
                horarioDiario.setAula1Horario(new AulaDAO().pegarPorHorarioDiaAmbiente(DataHorario.Horario.HORARIO1, dia, ambiente, turno));
                horarioDiario.setAula2Horario(new AulaDAO().pegarPorHorarioDiaAmbiente(DataHorario.Horario.HORARIO2, dia, ambiente, turno));
                horarioDiario.setAula3Horario(new AulaDAO().pegarPorHorarioDiaAmbiente(DataHorario.Horario.HORARIO3, dia, ambiente, turno));
                horarioDiario.setAula4Horario(new AulaDAO().pegarPorHorarioDiaAmbiente(DataHorario.Horario.HORARIO4, dia, ambiente, turno));
                horarioDiario.setAula5Horario(new AulaDAO().pegarPorHorarioDiaAmbiente(DataHorario.Horario.HORARIO5, dia, ambiente, turno));
                horarioDiarios.add(horarioDiario);
            }
        } else if (classe.equals(Usuario.class)) {
            for (Usuario usuario : new UsuarioDAO().pegarPorGrupoTurno(new GrupoDAO().pegarGrupo("Instrutor"), turno)) {
                HorarioDiario horarioDiario = new HorarioDiario();
                horarioDiario.setObjeto(usuario);
                horarioDiario.setAula1Horario(new AulaDAO().pegarPorDisciplinaUsuario(DataHorario.Horario.HORARIO1, dia, usuario, turno));
                horarioDiario.setAula2Horario(new AulaDAO().pegarPorDisciplinaUsuario(DataHorario.Horario.HORARIO2, dia, usuario, turno));
                horarioDiario.setAula3Horario(new AulaDAO().pegarPorDisciplinaUsuario(DataHorario.Horario.HORARIO3, dia, usuario, turno));
                horarioDiario.setAula4Horario(new AulaDAO().pegarPorDisciplinaUsuario(DataHorario.Horario.HORARIO4, dia, usuario, turno));
                horarioDiario.setAula5Horario(new AulaDAO().pegarPorDisciplinaUsuario(DataHorario.Horario.HORARIO5, dia, usuario, turno));
                horarioDiarios.add(horarioDiario);
            }
        }
    }

    private void carregarColunas() {
        tcTipo.getColumns().setAll(tcNome, tcDados);
        getColumns().setAll(tcTipo, tcPrimeiroHorario, tcSegundoHorario, tcTerceiroHorario, tcQuartoHorario, tcQuintoHorario);
    }
}
