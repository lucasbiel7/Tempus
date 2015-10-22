/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import static br.com.QuadroDeHorario.control.TabelaHorario.turma;
import static br.com.QuadroDeHorario.control.TabelaHorario.turmaEspelho;
import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.CalendarioDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.DataHorario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.MesCalendario;
import br.com.QuadroDeHorario.view.QuadroDeHorarioController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author OCTI01
 */
public class TabelaHorarioImpressao extends TableView<MesCalendario> {

    private TableColumn<MesCalendario, Aula> tcDia1;
    private TableColumn<MesCalendario, Aula> tcDia2;
    private TableColumn<MesCalendario, Aula> tcDia3;
    private TableColumn<MesCalendario, Aula> tcDia4;
    private TableColumn<MesCalendario, Aula> tcDia5;
    private TableColumn<MesCalendario, Aula> tcDia6;
    private TableColumn<MesCalendario, Aula> tcDia7;
    private TableColumn<MesCalendario, Aula> tcDia8;
    private TableColumn<MesCalendario, Aula> tcDia9;
    private TableColumn<MesCalendario, Aula> tcDia10;
    private TableColumn<MesCalendario, Aula> tcDia11;
    private TableColumn<MesCalendario, Aula> tcDia12;
    private TableColumn<MesCalendario, Aula> tcDia13;
    private TableColumn<MesCalendario, Aula> tcDia14;
    private TableColumn<MesCalendario, Aula> tcDia15;
    private TableColumn<MesCalendario, Aula> tcDia16;
    private TableColumn<MesCalendario, Aula> tcDia17;
    private TableColumn<MesCalendario, Aula> tcDia18;
    private TableColumn<MesCalendario, Aula> tcDia19;
    private TableColumn<MesCalendario, Aula> tcDia20;
    private TableColumn<MesCalendario, Aula> tcDia21;
    private TableColumn<MesCalendario, Aula> tcDia22;
    private TableColumn<MesCalendario, Aula> tcDia23;
    private TableColumn<MesCalendario, Aula> tcDia24;
    private TableColumn<MesCalendario, Aula> tcDia25;
    private TableColumn<MesCalendario, Aula> tcDia26;
    private TableColumn<MesCalendario, Aula> tcDia27;
    private TableColumn<MesCalendario, Aula> tcDia28;
    private TableColumn<MesCalendario, Aula> tcDia29;
    private TableColumn<MesCalendario, Aula> tcDia30;
    private TableColumn<MesCalendario, Aula> tcDia31;
    //Nome do Dia

    private TableColumn<MesCalendario, Aula> tcNomeDia1;
    private TableColumn<MesCalendario, Aula> tcNomeDia2;
    private TableColumn<MesCalendario, Aula> tcNomeDia3;
    private TableColumn<MesCalendario, Aula> tcNomeDia4;
    private TableColumn<MesCalendario, Aula> tcNomeDia5;
    private TableColumn<MesCalendario, Aula> tcNomeDia6;
    private TableColumn<MesCalendario, Aula> tcNomeDia7;
    private TableColumn<MesCalendario, Aula> tcNomeDia8;
    private TableColumn<MesCalendario, Aula> tcNomeDia9;
    private TableColumn<MesCalendario, Aula> tcNomeDia10;
    private TableColumn<MesCalendario, Aula> tcNomeDia11;
    private TableColumn<MesCalendario, Aula> tcNomeDia12;
    private TableColumn<MesCalendario, Aula> tcNomeDia13;
    private TableColumn<MesCalendario, Aula> tcNomeDia14;
    private TableColumn<MesCalendario, Aula> tcNomeDia15;
    private TableColumn<MesCalendario, Aula> tcNomeDia16;
    private TableColumn<MesCalendario, Aula> tcNomeDia17;
    private TableColumn<MesCalendario, Aula> tcNomeDia18;
    private TableColumn<MesCalendario, Aula> tcNomeDia19;
    private TableColumn<MesCalendario, Aula> tcNomeDia20;
    private TableColumn<MesCalendario, Aula> tcNomeDia21;
    private TableColumn<MesCalendario, Aula> tcNomeDia22;
    private TableColumn<MesCalendario, Aula> tcNomeDia23;
    private TableColumn<MesCalendario, Aula> tcNomeDia24;
    private TableColumn<MesCalendario, Aula> tcNomeDia25;
    private TableColumn<MesCalendario, Aula> tcNomeDia26;
    private TableColumn<MesCalendario, Aula> tcNomeDia27;
    private TableColumn<MesCalendario, Aula> tcNomeDia28;
    private TableColumn<MesCalendario, Aula> tcNomeDia29;
    private TableColumn<MesCalendario, Aula> tcNomeDia30;
    private TableColumn<MesCalendario, Aula> tcNomeDia31;

    private TableColumn<MesCalendario, Aula> tcNomeMes;
    private ObservableList<MesCalendario> aulas = FXCollections.observableArrayList();

    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat nomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat nomeMes = new SimpleDateFormat("MMMM");
    private SimpleDateFormat numeroDia = new SimpleDateFormat("dd");
    private Date inicio, fim;
    private Usuario instrutor;
    public static DataHorario.Turno turno;

    public TabelaHorarioImpressao(int mes, int ano, Object tipo) {
//        this.getStylesheets().add("/br/com/QuadroDeHorario/view/estilo.css");
//        this.getStyleClass().add("tabelaImpressao");
        try {
            if (tipo instanceof Usuario) {
                instrutor = (Usuario) tipo;
                turma = null;
                turmaEspelho = null;
            } else if (tipo instanceof Turma) {
                turma = (Turma) tipo;
                instrutor = null;
                if (turma.isEspelho()) {
                    turmaEspelho = turma;
                    turma = turmaEspelho.getTurmaPrincipal();
                }
            }
            inicio = data.parse("01/" + mes + "/" + ano);
            fim = data.parse("01/" + (mes + 1) + "/" + ano);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            tcNomeMes = new TableColumn<>(nomeMes.format(inicio) + "(" + mes + ")");
            getColumns().add(tcNomeMes);
            setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
            while (calendar.getTime().before(fim)) {
                switch (calendar.get(Calendar.DAY_OF_MONTH)) {
                    case 1:
                        adicionarColuna(calendar.getTime(), tcNomeDia1, tcDia1);
                        break;
                    case 2:
                        adicionarColuna(calendar.getTime(), tcNomeDia2, tcDia2);
                        break;
                    case 3:
                        adicionarColuna(calendar.getTime(), tcNomeDia3, tcDia3);
                        break;
                    case 4:
                        adicionarColuna(calendar.getTime(), tcNomeDia4, tcDia4);
                        break;
                    case 5:
                        adicionarColuna(calendar.getTime(), tcNomeDia5, tcDia5);
                        break;
                    case 6:
                        adicionarColuna(calendar.getTime(), tcNomeDia6, tcDia6);
                        break;
                    case 7:
                        adicionarColuna(calendar.getTime(), tcNomeDia7, tcDia7);
                        break;
                    case 8:
                        adicionarColuna(calendar.getTime(), tcNomeDia8, tcDia8);
                        break;
                    case 9:
                        adicionarColuna(calendar.getTime(), tcNomeDia9, tcDia9);
                        break;
                    case 10:
                        adicionarColuna(calendar.getTime(), tcNomeDia10, tcDia10);
                        break;
                    case 11:
                        adicionarColuna(calendar.getTime(), tcNomeDia11, tcDia11);
                        break;
                    case 12:
                        adicionarColuna(calendar.getTime(), tcNomeDia12, tcDia12);
                        break;
                    case 13:
                        adicionarColuna(calendar.getTime(), tcNomeDia13, tcDia13);
                        break;
                    case 14:
                        adicionarColuna(calendar.getTime(), tcNomeDia14, tcDia14);
                        break;
                    case 15:
                        adicionarColuna(calendar.getTime(), tcNomeDia15, tcDia15);
                        break;
                    case 16:
                        adicionarColuna(calendar.getTime(), tcNomeDia16, tcDia16);
                        break;
                    case 17:
                        adicionarColuna(calendar.getTime(), tcNomeDia17, tcDia17);
                        break;
                    case 18:
                        adicionarColuna(calendar.getTime(), tcNomeDia18, tcDia18);
                        break;
                    case 19:
                        adicionarColuna(calendar.getTime(), tcNomeDia19, tcDia19);
                        break;
                    case 20:
                        adicionarColuna(calendar.getTime(), tcNomeDia20, tcDia20);
                        break;
                    case 21:
                        adicionarColuna(calendar.getTime(), tcNomeDia21, tcDia21);
                        break;
                    case 22:
                        adicionarColuna(calendar.getTime(), tcNomeDia22, tcDia22);
                        break;
                    case 23:
                        adicionarColuna(calendar.getTime(), tcNomeDia23, tcDia23);
                        break;
                    case 24:
                        adicionarColuna(calendar.getTime(), tcNomeDia24, tcDia24);
                        break;
                    case 25:
                        adicionarColuna(calendar.getTime(), tcNomeDia25, tcDia25);
                        break;
                    case 26:
                        adicionarColuna(calendar.getTime(), tcNomeDia26, tcDia26);
                        break;
                    case 27:
                        adicionarColuna(calendar.getTime(), tcNomeDia27, tcDia27);
                        break;
                    case 28:
                        adicionarColuna(calendar.getTime(), tcNomeDia28, tcDia28);
                        break;
                    case 29:
                        adicionarColuna(calendar.getTime(), tcNomeDia29, tcDia29);
                        break;
                    case 30:
                        adicionarColuna(calendar.getTime(), tcNomeDia30, tcDia30);
                        break;
                    case 31:
                        adicionarColuna(calendar.getTime(), tcNomeDia31, tcDia31);
                        break;
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException ex) {
            Logger.getLogger(TabelaHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
        setEditable(true);
        this.getSelectionModel().cellSelectionEnabledProperty().setValue(Boolean.TRUE);
        setPrefSize(USE_PREF_SIZE, 190);
        setSortPolicy(null);
        setItems(aulas);
        carregarDados();
//        scrollTo(6);
    }

    private void adicionarColuna(Date dia, TableColumn<MesCalendario, Aula> tcNome, TableColumn<MesCalendario, Aula> tcDia) {
        tcNome = new TableColumn<>(nomeDia.format(dia));
        tcDia = new TableColumn<>(numeroDia.format(dia));
        tcNome.getColumns().add(tcDia);
        tcNomeMes.getColumns().add(tcNome);
//        getColumns().add(tcNome);
        tcDia.setCellValueFactory(new PropertyValueFactory<>("dia" + Integer.parseInt(numeroDia.format(dia))));
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dia);

        if (!calendarios.isEmpty()) {
            String eventos = "";
            for (Calendario calendario : calendarios) {
                eventos += calendario.getId().getEvento().getNome() + "\n";
            }
//            Calendario calendario = calendarios.get(0);
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
                    if (empty) {
                        setText("");
                    } else if (item != null) {
                        if (turno != null) {
                            setText(item.toString());
                            setTooltip(new Tooltip(item.getMateriaHorario().getMateriaTurmaIntrutorSemestre().getTurma().getDescricao()));
                        } else {
                            setText(item.toString());
                        }
                        setTextFill(Color.rgb(item.getMateriaHorario().getRed(), item.getMateriaHorario().getGreen(), item.getMateriaHorario().getBlue()));
                        List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorario(item.getMateriaHorario());
                        int numeroAmbiente = 0;
                        for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
                            if (materiaHorarioAmbiente.getId().getAmbiente().equals(item.getAmbiente())) {
                                numeroAmbiente = materiaHorarioAmbiente.getNumero();
                                System.out.println(materiaHorarioAmbiente.getId().getAmbiente());
                                break;
                            }
                        }
//                        System.out.println("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(item.getId().getDataAula()));
//                        System.out.println("Ambiente: "+item.getAmbiente());
//                        System.out.println("Turma: "+item.getId().getTurma());
//                        System.out.println("Número ambiente: " + numeroAmbiente);
//                        System.out.println("Materia Horario: "+item.getMateriaHorario().getId());
//                        System.out.println("_______________________________________________");
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
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                }

            };
        }
    }

    private double linha = 12.0;

    private void carregarDados() {
        aulas.clear();
        for (DataHorario.Horario horario : DataHorario.Horario.values()) {
            MesCalendario mesCalendario = new MesCalendario(horario);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                Aula aula = null;
                if (turma != null) {
                    if (turmaEspelho != null) {
                        aula = new AulaDAO().pegarPorHorarioDiaTurma(mesCalendario.getHorario(), calendar.getTime(), turmaEspelho);
                        if (aula == null) {
                            aula = new AulaDAO().pegarPorHorarioDiaTurma(mesCalendario.getHorario(), calendar.getTime(), turma);
                        }
                    } else {
                        aula = new AulaDAO().pegarPorHorarioDiaTurma(mesCalendario.getHorario(), calendar.getTime(), turma);
                    }
                } else if (instrutor != null) {
                    aula = new AulaDAO().pegarPorInstrutorTurnoDiaHorario(instrutor, turno, calendar.getTime(), mesCalendario.getHorario());
                }
                inserirDados(mesCalendario, calendar.getTime(), horario, aula);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            aulas.add(mesCalendario);
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
}
