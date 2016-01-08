package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.ObservacaoAulaDAO;
import br.com.QuadroDeHorario.model.MesCalendario;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.ObservacaoAula;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.GerenciarImagem;
import br.com.QuadroDeHorario.model.util.Mensagem;
import br.com.QuadroDeHorario.view.QuadroDeHorarioController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author OCTI01
 */
public class TabelaHorario extends TableView<MesCalendario> {

    //Nome do mês
    private TableColumn<MesCalendario, Aula> tcNomeMes;
    private ContextMenu cmOpcoes;
    private MenuItem miExcluir;
    private ObservableList<MesCalendario> aulas = FXCollections.observableArrayList();
    private SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat nomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat nomeMes = new SimpleDateFormat("MMMM");
    private SimpleDateFormat numeroDia = new SimpleDateFormat("dd");
    private Date inicio, fim;
    public static Ambiente ambienteSelecionado;
    public static MateriaHorario materiaHorarioSelecionado;
    public static Turma turma;
    public static Turma turmaEspelho;
    public static boolean aulasGeminadas;
    public static boolean autoPreencher;
    private static boolean clicou;
    public static DataHorario.Turno turno;
    private static boolean cargaHoraria;
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private List<TableColumn<MesCalendario, Aula>> colunas;
    private int mes;
    private ContextMenu cmObservacao;
    private MenuItem miExcluirObservacao;

    public TabelaHorario(int mes, int ano) {
        try {
            this.mes = mes;
            inicio = data.parse("01/" + mes + "/" + ano);
            fim = data.parse("01/" + (mes + 1) + "/" + ano);
            tcNomeMes = new TableColumn<>(nomeMes.format(inicio) + "(" + mes + ")");
            getColumns().add(tcNomeMes);
            setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
            colunas = new ArrayList<>();
            carregarColunas();
        } catch (ParseException ex) {
            Logger.getLogger(TabelaHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
        miExcluir = new MenuItem("Excluir");
        cmOpcoes = new ContextMenu(miExcluir);
        miExcluirObservacao = new MenuItem("Excluir observação");
        cmObservacao = new ContextMenu(miExcluirObservacao);
        setEditable(true);
        this.getSelectionModel().cellSelectionEnabledProperty().setValue(Boolean.TRUE);
        this.setPrefSize(USE_PREF_SIZE, 195);
        setSortPolicy(null);
        setItems(aulas);
        carregarDados();
        tcNomeMes.getColumns().addListener(new ListChangeListener<TableColumn<MesCalendario, ?>>() {
            private boolean suspender;

            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<MesCalendario, ?>> c) {
                c.next();
                if (c.wasReplaced() && !suspender) {
                    suspender = true;
                    tcNomeMes.getColumns().setAll(colunas);
                    suspender = false;
                }
            }
        });
    }

    private TableColumn<MesCalendario, Aula> adicionarColuna(Date dia) {
        TableColumn<MesCalendario, Aula> tcNome = new TableColumn<>(nomeDia.format(dia));
        TableColumn<MesCalendario, Aula> tcDia = new TableColumn<>(numeroDia.format(dia));
        tcNome.getColumns().add(tcDia);
        tcNomeMes.getColumns().add(tcNome);
        tcDia.setCellValueFactory(new PropertyValueFactory<>("dia" + Integer.parseInt(numeroDia.format(dia))));
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dia, true);
        if (!calendarios.isEmpty()) {
            String eventos = "";
            for (Calendario calendario : calendarios) {
                eventos += calendario.getId().getEvento().getNome() + "\n";
            }
            Label label = new Label(tcNome.getText());
            label.setTooltip(new Tooltip(eventos));
            Color[] todasCores = new Color[calendarios.size()];
            int i = 0;
            for (Calendario calendario : calendarios) {
                String[] cores = calendario.getId().getEvento().getColor().split("@");
                todasCores[i] = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
                i++;
            }
            if (todasCores.length < 2) {
                tcNome.setStyle("-fx-background-color: " + FxMananger.toRGB(todasCores[0]) + ";");
                String hexa = Integer.toHexString((int) (todasCores[0].getRed() * 255)) + Integer.toHexString((int) (todasCores[0].getGreen() * 255)) + Integer.toHexString((int) (todasCores[0].getBlue() * 255));
                int value = Integer.valueOf(hexa, 16);
                int valorMaximo = Integer.valueOf("FFFFFF", 16);
                if (valorMaximo - value > value) {
                    label.setTextFill(Color.rgb(255, 255, 255));
                } else {
                    tcNome.getStyleClass().add("fonte-preta");
                }
            } else {
                String hexa = Integer.toHexString((int) (todasCores[1].getRed() * 255)) + Integer.toHexString((int) (todasCores[1].getGreen() * 255)) + Integer.toHexString((int) (todasCores[1].getBlue() * 255));
                int value = Integer.valueOf(hexa, 16);
                int valorMaximo = Integer.valueOf("FFFFFF", 16);
                if (valorMaximo - value > value) {
                    label.setTextFill(Color.rgb(255, 255, 255));
                } else {
                    tcNome.getStyleClass().add("fonte-preta");
                }
                tcNome.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, " + FxMananger.toRGB(todasCores[0]) + "  0% , " + FxMananger.toRGB(todasCores[1]) + " 30%," + FxMananger.toRGB(todasCores.length > 2 ? todasCores[2] : todasCores[1]) + " 100%)");
            }
            tcNome.setGraphic(label);
            tcNome.setText("");
        }
        tcDia.setCellFactory(new RenderDia(dia));
        if (!tcNome.getText().equals("Dom")) {
            tcDia.setOnEditStart(new SelecionarDia(dia));
        }
        tcDia.setUserData(tcDia.getText());
        tcDia.sortableProperty().set(false);
        adicionarObservacao(tcDia, turma, dia);
        return tcNome;
    }

    private void adicionarObservacao(TableColumn<MesCalendario, Aula> tcNome, Turma turma, Date dia) {
        final List<ObservacaoAula> observacaoAulas = new ObservacaoAulaDAO().pegarPorDiaTurma(dia, turma);
        if (!observacaoAulas.isEmpty()) {
            String observacao = "";
            Label label = new Label((String) tcNome.getUserData());
            for (ObservacaoAula observacaoAula : observacaoAulas) {
                observacao += observacaoAula.getObservacao();
            }
            label.setTooltip(new Tooltip(observacao));
            tcNome.setGraphic(label);
            tcNome.setText("");
            label.setGraphic(new ImageView(new Image(GerenciarImagem.carregarImagem("information.png"), 10, 10, true, true)));
            label.setContentDisplay(ContentDisplay.RIGHT);
            label.setOnMouseReleased((MouseEvent event) -> {
                if (event.isPopupTrigger()) {
                    miExcluirObservacao.setOnAction((ActionEvent actionEvent) -> {
                        new ObservacaoAulaDAO().excluir(observacaoAulas.get(0));
                        adicionarObservacao(tcNome, turma, dia);
                        actionEvent.consume();
                    });
                    cmObservacao.show(this, event.getScreenX(), event.getScreenY());
                } else {
                    Platform.runLater(() -> {
                        FxMananger.show("AdicionarObservacao", "Adicionar observação", true, false, observacaoAulas.get(0));
                        adicionarObservacao(tcNome, turma, dia);
                    });
                }
                event.consume();
            });
        } else {
            tcNome.setGraphic(null);
            tcNome.setText(DataHorario.toDay(dia));
        }
    }

    private void carregarColunas() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicio);
        while (calendar.getTime().before(fim)) {
            colunas.add(adicionarColuna(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void cadastrarAula(Date dia, DataHorario.Horario horario) throws Exception {
        Aula aula = new Aula();
        aula.setMateriaHorario(materiaHorarioSelecionado);
        aula.setId(new Aula.DataHorarioTurnoTurma(dia, horario, turno, materiaHorarioSelecionado.getMateriaTurmaInstrutorSemestre().getTurma()));
        aula.setAmbiente(ambienteSelecionado);
        Aula aulaExistente = new AulaDAO().pegarPorId(aula.getId());
        List<Aula> aulasAmbiente = new AulaDAO().validarAmbiente(aula);
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dia, true, false);
        List<CalendarioAmbiente> calendarioAmbientes = new CalendarioAmbienteDAO().pegarTodosPorDataAmbienteTurno(aula.getId().getDataAula(), aula.getAmbiente(), turno);
        List<CalendarioUsuario> calendarioUsuarios = new CalendarioUsuarioDAO().pegarTodosPorUsuarioData(aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor(), aula.getId().getDataAula(), turno);
        if (aulasAmbiente.isEmpty() && calendarioAmbientes.isEmpty() && calendarioUsuarios.isEmpty() && calendarios.isEmpty()) {
            List<Aula> aulasInstrutor = new AulaDAO().validarInstrutor(aula);
            if (aulasInstrutor.isEmpty()) {
                if (new AulaDAO().pegarPorDisciplinaTurma(materiaHorarioSelecionado.getMateriaTurmaInstrutorSemestre().getMateria(), materiaHorarioSelecionado.getMateriaTurmaInstrutorSemestre().getTurma()).size() + 1 > materiaHorarioSelecionado.getMateriaTurmaInstrutorSemestre().getMateria().getCargaHoraria() && !cargaHoraria) {
                    if (Mensagem.showConfirmation("Carga horária excedida", "A Carga Horária para essa materia foi excedida deseja continuar?")) {
                        cargaHoraria = true;
                        try {
                            if (aulaExistente == null) {
                                try {
                                    new AulaDAO().cadastrar(aula);
                                } catch (ConstraintViolationException e) {
                                    Mensagem.showError("Aula cadastrada",
                                            "Está aula ja foi registrada no sistema tempus, caso não esteja\n"
                                            + "aparecendo no quadro reinicie o progama!");
                                }
                            } else {
                                aula.setId(aulaExistente.getId());
                                new AulaDAO().editar(aula);
                            }
                            inserirNaLista(aula, horario);
                        } catch (ConstraintViolationException e) {
                            Mensagem.showError("Aula cadastrada",
                                    "Está Aula já foi cadastrada e não é permitido duplicar,\n "
                                    + "por favor aguarde a aula carregar no quadro ou reinicie o sistema! ");
                        }
                    }
                } else {
                    if (aulaExistente == null) {
                        try {
                            new AulaDAO().cadastrar(aula);
                        } catch (ConstraintViolationException e) {
                            Mensagem.showError("Aula cadastrada",
                                    "Está aula ja foi registrada no sistema tempus, caso não esteja\n"
                                    + "aparecendo no quadro reinicie o progama!");
                        }
                    } else {
                        aula.setId(aulaExistente.getId());
                        new AulaDAO().editar(aula);
                    }
                    inserirNaLista(aula, horario);
                }
            } else {
                Mensagem.showError("Impossível cadastrar(Instrutor)",
                        "Não foi possível cadastrar aula no dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()) + "\n"
                        + " no " + aula.getId().getHorario() + ". O instrutor(a) " + aulasInstrutor.get(0).getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor()
                        + "\n já possui aula na turma " + aulasInstrutor.get(0).getId().getTurma() + "!");
            }
        } else {
            if (!calendarios.isEmpty()) {
                Mensagem.showError("Impossível cadastrar(Dia não letivo)",
                        "Esse dia não foi programado como dia letivo.\n"
                        + "Motivo: " + calendarios.get(0).getId().getEvento() + "\n"
                        + "que está marcado que não será dia letivo.");
            }
            if (!aulasAmbiente.isEmpty()) {
                Mensagem.showError("Impossível cadastrar(Ambiente)",
                        "Não foi possível cadastrar aula no dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()) + "\n"
                        + " no " + aula.getId().getHorario() + ". A turma " + aulasAmbiente.get(0).getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma() + "\n"
                        + " já possui aula nessa ambiente!");
            }
            if (!calendarioAmbientes.isEmpty()) {
                Mensagem.showError("Impossível cadastrar(Evento Ambiente)",
                        "Não foi possível cadastrar a aula no dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()) + "\n"
                        + " no " + aula.getId().getHorario() + ". O evento " + calendarioAmbientes.get(0).getId().getCalendario().getId().getEvento().getNome() + " já \n"
                        + "alocou esse ambiente!");
            }
            if (!calendarioUsuarios.isEmpty()) {
                Mensagem.showError("Impossível cadastrar(Evento usuário)",
                        "Não foi possivel cadastrar aula no dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()) + "\n"
                        + "no " + aula.getId().getHorario() + ". O evento " + calendarioUsuarios.get(0).getId().getCalendario().getId().getEvento() + " já\n"
                        + "alocou esse funcionário!");
            }
        }
    }

    public MesCalendario inserirDados(MesCalendario mesCalendario, Date dataAula, DataHorario.Horario horario, Aula aula) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAula);
        switch (calendar.get(Calendar.DAY_OF_MONTH)) {
            case 1:
                mesCalendario.setDia1(aula);
                break;
            case 2:
                mesCalendario.setDia2(aula);
                break;
            case 3:
                mesCalendario.setDia3(aula);
                break;
            case 4:
                mesCalendario.setDia4(aula);
                break;
            case 5:
                mesCalendario.setDia5(aula);
                break;
            case 6:
                mesCalendario.setDia6(aula);
                break;
            case 7:
                mesCalendario.setDia7(aula);
                break;
            case 8:
                mesCalendario.setDia8(aula);
                break;
            case 9:
                mesCalendario.setDia9(aula);
                break;
            case 10:
                mesCalendario.setDia10(aula);
                break;
            case 11:
                mesCalendario.setDia11(aula);
                break;
            case 12:
                mesCalendario.setDia12(aula);
                break;
            case 13:
                mesCalendario.setDia13(aula);
                break;
            case 14:
                mesCalendario.setDia14(aula);
                break;
            case 15:
                mesCalendario.setDia15(aula);
                break;
            case 16:
                mesCalendario.setDia16(aula);
                break;
            case 17:
                mesCalendario.setDia17(aula);
                break;
            case 18:
                mesCalendario.setDia18(aula);
                break;
            case 19:
                mesCalendario.setDia19(aula);
                break;
            case 20:
                mesCalendario.setDia20(aula);
                break;
            case 21:
                mesCalendario.setDia21(aula);
                break;
            case 22:
                mesCalendario.setDia22(aula);
                break;
            case 23:
                mesCalendario.setDia23(aula);
                break;
            case 24:
                mesCalendario.setDia24(aula);
                break;
            case 25:
                mesCalendario.setDia25(aula);
                break;
            case 26:
                mesCalendario.setDia26(aula);
                break;
            case 27:
                mesCalendario.setDia27(aula);
                break;
            case 28:
                mesCalendario.setDia28(aula);
                break;
            case 29:
                mesCalendario.setDia29(aula);
                break;
            case 30:
                mesCalendario.setDia30(aula);
                break;
            case 31:
                mesCalendario.setDia31(aula);
                break;
        }
        return mesCalendario;
    }

    private void carregarDados() {
        aulas.clear();
        for (DataHorario.Horario horario : DataHorario.Horario.values()) {
            MesCalendario mesCalendario = new MesCalendario(horario);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                Aula aula;
                if (turmaEspelho != null) {
                    aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turmaEspelho, turno);
                    if (aula == null) {
                        aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turma, turno);
                    }
                } else {
                    aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turma, turno);
                }
                inserirDados(mesCalendario, calendar.getTime(), horario, aula);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            aulas.add(mesCalendario);
        }

    }

    public void removerDaLista(Aula aula) {
        for (MesCalendario mesCalendario : getItems()) {
            if (mesCalendario.getHorario().equals(aula.getId().getHorario())) {
                getItems().set(getItems().indexOf(mesCalendario), inserirDados(mesCalendario, aula.getId().getDataAula(), aula.getId().getHorario(), null));
                break;
            }
        }
    }

    public void inserirNaLista(Aula aula, DataHorario.Horario horario) {
        for (MesCalendario item : getItems()) {
            if (item.getHorario().equals(aula.getId().getHorario())) {
                getItems().set(getItems().indexOf(item), inserirDados(item, aula.getId().getDataAula(), horario, aula));
                break;
            }
        }
    }

    public class ExcluirAulas implements EventHandler<ActionEvent> {

        private Aula aula;

        public ExcluirAulas(Aula aula) {
            this.aula = aula;
        }

        @Override
        public void handle(ActionEvent event) {
            if (aulasGeminadas && autoPreencher) {
                if (Mensagem.showConfirmation("Excluir todas aulas geminadas e recorrentes!", "Você tem certeza que deseja excluir todas aulas\n"
                        + " germinadas e recorrentes a partir do dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()))) {
                    for (DataHorario.Horario horario : DataHorario.Horario.values()) {
                        Calendar inicio = Calendar.getInstance();
                        inicio.setTime(aula.getId().getDataAula());
                        int mes = inicio.get(Calendar.MONTH);
                        while (inicio.get(Calendar.MONTH) <= mes) {
                            Aula aulaSeguida = new AulaDAO().pegarPorId(new Aula.DataHorarioTurnoTurma(inicio.getTime(), horario, turno, aula.getId().getTurma()));
                            if (aulaSeguida != null) {
                                new AulaDAO().excluir(aulaSeguida);
                                removerDaLista(aulaSeguida);
                            }
                            inicio.add(Calendar.DAY_OF_MONTH, 7);
                        }
                    }
                }
            } else if (aulasGeminadas) {
                String menssagem = "Você realmente deseja excluir todas aulas no dia " + new SimpleDateFormat("dd/MM/yyyy").format(aula.getId().getDataAula()) + "\n"
                        + "(Nesse caso apagará todas as aulas do dia mesmo não sendo mesmo conteúdo)";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, menssagem, ButtonType.YES, new ButtonType("Sim, com observação"), ButtonType.NO);
                alert.setTitle("Deseja Continuar?");
                ((Stage) (alert.getDialogPane().getScene().getWindow())).getIcons().add(FxMananger.image);
                alert.setHeaderText("Excluir aulas geminadas!");
                alert.setContentText(menssagem);
                ButtonType botao = alert.showAndWait().get();
                if (!botao.equals(ButtonType.NO)) {
                    for (DataHorario.Horario horario : DataHorario.Horario.values()) {
                        Aula aulaSeguida = new AulaDAO().pegarPorId(new Aula.DataHorarioTurnoTurma(aula.getId().getDataAula(), horario, turno, aula.getId().getTurma()));
                        if (aulaSeguida != null) {
                            new AulaDAO().excluir(aulaSeguida);
                            removerDaLista(aulaSeguida);
                        }
                    }
                    if (!botao.equals(ButtonType.YES)) {
                        FxMananger.show("AdicionarObservacao", "Adicionar observação", true, false, aula);
                    }
                }
            } else if (autoPreencher) {
                if (Mensagem.showConfirmation("Excluir aulas recorrentes!", "Você realmente deseja excluir todas aulas recorrentes?\n"
                        + "(Todas aulas desse horário no dia da semana no mês serão removidos a partir da data selecionada )")) {
                    Calendar inicio = Calendar.getInstance();
                    inicio.setTime(aula.getId().getDataAula());
                    int mes = inicio.get(Calendar.MONTH);
                    while (inicio.get(Calendar.MONTH) <= mes) {
                        Aula aulaSeguida = new AulaDAO().pegarPorId(new Aula.DataHorarioTurnoTurma(inicio.getTime(), aula.getId().getHorario(), turno, aula.getId().getTurma()));
                        if (aulaSeguida != null) {
                            new AulaDAO().excluir(aulaSeguida);
                            removerDaLista(aulaSeguida);
                        }
                        inicio.add(Calendar.DAY_OF_MONTH, 7);
                    }
                }
            } else if (Mensagem.showConfirmation("Excluir aula", "Você realmente deseja excluir a seguinte aula?\n"
                    + "Turma: " + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma() + "\n"
                    + "Ambiente: " + aula.getAmbiente() + "\n"
                    + "Horário: " + aula.getId().getHorario() + "\n"
                    + "Instrutor: " + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor() + "\n"
                    + "Data: " + sdfData.format(aula.getId().getDataAula())
                    + "")) {
                new AulaDAO().excluir(aula);
                removerDaLista(aula);
            }
        }
    }

    private class RenderDia implements Callback<TableColumn<MesCalendario, Aula>, TableCell<MesCalendario, Aula>> {

        private Date dia;

        public RenderDia(Date dia) {
            this.dia = dia;
        }

        @Override
        public TableCell<MesCalendario, Aula> call(TableColumn<MesCalendario, Aula> param) {
            return new TableCell<MesCalendario, Aula>() {
                @Override
                protected void updateItem(Aula item, boolean empty) {
                    setTextAlignment(TextAlignment.CENTER);
                    setAlignment(Pos.CENTER);
                    if (empty) {
                        setText("");
                    } else if (item != null) {
                        setText(item.toString());
                        setTooltip(new Tooltip(item.toString() + "\n"
                                + item.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getMateria().toString()));
                        setTextFill(Color.rgb(item.getMateriaHorario().getRed(), item.getMateriaHorario().getGreen(), item.getMateriaHorario().getBlue()));
                        List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorario(item.getMateriaHorario());
                        int numeroAmbiente = 0;
                        for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
                            if (materiaHorarioAmbiente.getId().getAmbiente().equals(item.getAmbiente())) {
                                numeroAmbiente = materiaHorarioAmbiente.getNumero();
                            }
                        }
                        setOnMouseReleased((MouseEvent event) -> {
                            if (event.isPopupTrigger()) {
                                miExcluir.setOnAction(new ExcluirAulas(item));
                                cmOpcoes.show(TabelaHorario.this, event.getScreenX(), event.getScreenY());
                            }
                        });
                        switch (numeroAmbiente) {
                            case 1:
                                setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente1, CornerRadii.EMPTY, Insets.EMPTY)));
                                break;
                            case 2:
                                setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente2, CornerRadii.EMPTY, Insets.EMPTY)));
                                break;
                            case 3:
                                setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente3, CornerRadii.EMPTY, Insets.EMPTY)));
                                break;
                            case 4:
                                setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente4, CornerRadii.EMPTY, Insets.EMPTY)));
                                break;
                            case 5:
                                setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente5, CornerRadii.EMPTY, Insets.EMPTY)));
                                break;
                        }
                    } else {
                        setText("");
                        setOnMouseReleased(null);
                        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        if (param.getParentColumn().getText().equalsIgnoreCase("Sáb")) {
                            setBackground(new Background(new BackgroundFill(Color.rgb(224, 224, 224), CornerRadii.EMPTY, Insets.EMPTY)));
                        } else if (param.getParentColumn().getText().equalsIgnoreCase("Dom")) {
                            setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                }
            };
        }
    }

    private class SelecionarDia implements EventHandler<TableColumn.CellEditEvent<MesCalendario, Aula>> {

        private Date dia;

        public SelecionarDia(Date dia) {
            this.dia = dia;
        }

        @Override
        public void handle(TableColumn.CellEditEvent<MesCalendario, Aula> event) {
            if (!clicou) {
                if (ambienteSelecionado == null || turma == null || materiaHorarioSelecionado == null) {
                    Mensagem.showError("Selecionar Ambiente/Componente curricular", "É necessário selecionar ambiente/componente curricular\n para poder cadastrar uma aula!");
                } else {
                    clicou = true;
                    if (!autoPreencher) {
                        if (aulasGeminadas) {
                            for (DataHorario.Horario horario : DataHorario.Horario.values()) {
                                try {
                                    cadastrarAula(dia, horario);
                                } catch (Exception ex) {
                                    break;
                                }
                            }
                        } else {
                            try {
                                cadastrarAula(dia, getSelectionModel().getSelectedItem().getHorario());
                            } catch (Exception ex) {
                            }
                        }
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dia);
                        while (calendar.getTime().before(fim)) {
                            if (aulasGeminadas) {
                                for (DataHorario.Horario horario : DataHorario.Horario.values()) {
                                    try {
                                        cadastrarAula(calendar.getTime(), horario);
                                    } catch (Exception ex) {
                                        break;
                                    }
                                }
                            } else {
                                try {
                                    cadastrarAula(calendar.getTime(), getSelectionModel().getSelectedItem().getHorario());
                                } catch (Exception ex) {
                                    break;
                                }
                            }
                            calendar.add(Calendar.DAY_OF_MONTH, 7);
                        }
                    }
                    clicou = false;
                }
            }
            cargaHoraria = false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.mes;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TabelaHorario other = (TabelaHorario) obj;
        if (this.mes != other.mes) {
            return false;
        }
        return true;
    }
}
