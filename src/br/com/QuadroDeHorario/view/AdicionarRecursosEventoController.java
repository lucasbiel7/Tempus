/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.AmbienteDAO;
import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioAmbienteDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbienteID;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuarioID;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import static br.com.QuadroDeHorario.model.util.DataHorario.Turno.MANHA;
import static br.com.QuadroDeHorario.model.util.DataHorario.Turno.NOITE;
import static br.com.QuadroDeHorario.model.util.DataHorario.Turno.TARDE;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AdicionarRecursosEventoController implements Initializable {

    //Tabela ambiente alocado
    @FXML
    private TableView<CalendarioAmbiente> tvAmbiente;
    @FXML
    private TableColumn<CalendarioAmbiente, String> tcAmbienteAlocado;
    @FXML
    private TableColumn<CalendarioAmbiente, String> tcNomeAmbiente;
    @FXML
    private TableColumn<CalendarioAmbiente, CalendarioAmbiente> tcManhaAmbiente;
    @FXML
    private TableColumn<CalendarioAmbiente, CalendarioAmbiente> tcTardeAmbiente;
    @FXML
    private TableColumn<CalendarioAmbiente, CalendarioAmbiente> tcNoiteAmbiente;
    //tabela usuário alocado
    @FXML
    private TableView<CalendarioUsuario> tvUsuario;
    @FXML
    private TableColumn<CalendarioUsuario, String> tcUsuarioAlocado;
    @FXML
    private TableColumn<CalendarioUsuario, String> tcNomeUsuario;
    @FXML
    private TableColumn<CalendarioUsuario, CalendarioUsuario> tcManhaUsuario;
    @FXML
    private TableColumn<CalendarioUsuario, CalendarioUsuario> tcTardeUsuario;
    @FXML
    private TableColumn<CalendarioUsuario, CalendarioUsuario> tcNoiteUsuario;
    //Combos 
    @FXML
    private ComboBox<Ambiente> cbAmbiente;
    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private ComboBox<Calendario> cbCalendario;
    //Controle de turnos
    @FXML
    private CheckBox cbManha;
    @FXML
    private CheckBox cbTarde;
    @FXML
    private CheckBox cbNoite;
    //Listas para as tabelas
    private ObservableList<Ambiente> ambientes = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<Calendario> calendarios = FXCollections.observableArrayList();
    private ObservableList<CalendarioAmbiente> calendarioAmbientes = FXCollections.observableArrayList();
    private ObservableList<CalendarioUsuario> calendarioUsuarios = FXCollections.observableArrayList();

    private Date dataSelecionada;
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            dataSelecionada = (Date) cbAmbiente.getParent().getUserData();
            calendarios.setAll(new CalendarioDAO().pegarTodosPorData(dataSelecionada));
            cbCalendario.getSelectionModel().selectFirst();
            calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
            calendarioUsuarios.setAll(new CalendarioUsuarioDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
        });
        cbAmbiente.setItems(ambientes);
        //Configurar Colunas Ambiente
        tcNomeAmbiente.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcManhaAmbiente.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioAmbiente, CalendarioAmbiente> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcTardeAmbiente.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioAmbiente, CalendarioAmbiente> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcNoiteAmbiente.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioAmbiente, CalendarioAmbiente> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcManhaAmbiente.setCellFactory(new HorarioAmbienteTableRender(DataHorario.Turno.MANHA));
        tcTardeAmbiente.setCellFactory(new HorarioAmbienteTableRender(DataHorario.Turno.TARDE));
        tcNoiteAmbiente.setCellFactory(new HorarioAmbienteTableRender(DataHorario.Turno.NOITE));
        //Configurar Colunas Usuarios
        //Setando os valores das colunas
        tcNomeUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcManhaUsuario.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioUsuario, CalendarioUsuario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcTardeUsuario.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioUsuario, CalendarioUsuario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcNoiteUsuario.setCellValueFactory((TableColumn.CellDataFeatures<CalendarioUsuario, CalendarioUsuario> param) -> new SimpleObjectProperty<>(param.getValue()));
        //Render das colunas
        tcManhaUsuario.setCellFactory(new HorarioUsuarioTableRender(DataHorario.Turno.MANHA));
        tcTardeUsuario.setCellFactory(new HorarioUsuarioTableRender(DataHorario.Turno.TARDE));
        tcNoiteUsuario.setCellFactory(new HorarioUsuarioTableRender(DataHorario.Turno.NOITE));
        cbUsuario.setItems(usuarios);
        cbCalendario.setItems(calendarios);
        tvAmbiente.setItems(calendarioAmbientes);
        ambientes.setAll(new AmbienteDAO().pegarTodos());
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        tvUsuario.setItems(calendarioUsuarios);
    }

    @FXML
    private void btAdicionarAmbienteActionEvent(ActionEvent actionEvent) {
        if (cbAmbiente.getSelectionModel().getSelectedItem() != null) {
            List<DataHorario.Turno> turnos = new ArrayList<>();
            if (cbManha.isSelected()) {
                turnos.add(DataHorario.Turno.MANHA);
            }
            if (cbTarde.isSelected()) {
                turnos.add(DataHorario.Turno.TARDE);
            }
            if (cbNoite.isSelected()) {
                turnos.add(DataHorario.Turno.NOITE);
            }
            List<Aula> aulas = new AulaDAO().pegarPorAmbienteDia(dataSelecionada, cbAmbiente.getSelectionModel().getSelectedItem(), turnos.toArray(new DataHorario.Turno[turnos.size()]));
            List<CalendarioAmbiente> calendarioAmbientes = new CalendarioAmbienteDAO().pegarTodosPorDataAmbiente(dataSelecionada, cbAmbiente.getSelectionModel().getSelectedItem());
            if (aulas.isEmpty() && calendarioAmbientes.isEmpty()) {
                CalendarioAmbiente calendarioAmbiente = new CalendarioAmbiente(new CalendarioAmbienteID(cbCalendario.getSelectionModel().getSelectedItem(), cbAmbiente.getSelectionModel().getSelectedItem()));
                calendarioAmbiente.setManha(cbManha.isSelected());
                calendarioAmbiente.setTarde(cbTarde.isSelected());
                calendarioAmbiente.setNoite(cbNoite.isSelected());
                if (new CalendarioAmbienteDAO().pegarPorId(calendarioAmbiente.getId()) == null) {
                    new CalendarioAmbienteDAO().cadastrar(calendarioAmbiente);
                } else {
                    new CalendarioAmbienteDAO().editar(calendarioAmbiente);
                }
                this.calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
                Mensagem.showInformation("Ambiente alocado!", "O ambiente " + cbAmbiente.getSelectionModel().getSelectedItem() + " foi alocado corretamente ao\n "
                        + "evento " + cbCalendario.getSelectionModel().getSelectedItem().getId().getEvento().getNome() + " no dia " + sdfData.format(cbCalendario.getSelectionModel().getSelectedItem().getId().getDataAcontecimento()));
            } else {
                String aulasDia = "";
                String calendarioAmbienteDia = "";
                for (Aula aula : aulas) {
                    aulasDia += "Turma: " + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma() + " Horário: " + aula.getId().getHorario() + " Turno: " + aula.getId().getTurno() + "\n";
                }
                for (CalendarioAmbiente calendarioAmbiente : calendarioAmbientes) {
                    calendarioAmbienteDia += "Evento: " + calendarioAmbiente.getId().getCalendario().getId().getEvento().getNome() + " Ambiente: " + calendarioAmbiente.getId().getAmbiente().getNome() + "";
                }
                if (!aulas.isEmpty()) {
                    Mensagem.showInformation("Aulas no ambiente... ", "No dia " + sdfData.format(dataSelecionada) + " haverá as seguintes aulas: \n" + aulasDia);
                }
                if (!calendarioAmbientes.isEmpty()) {
                    Mensagem.showInformation("Eventos no ambiente... ", "No dia " + sdfData.format(dataSelecionada) + " haverá os seguintes eventos: \n" + calendarioAmbienteDia);
                }
            }
        } else {
            Mensagem.showError("Selecione ambiente", "Nenhum ambiente foi selecionado para ser adicionado ao evento!");
        }
    }

    @FXML
    private void btAdicionarUsuarioActionEvent(ActionEvent actionEvent) {
        if (cbUsuario.getSelectionModel().getSelectedItem() != null) {
            List<DataHorario.Turno> turnos = new ArrayList<>();
            if (cbManha.isSelected()) {
                turnos.add(DataHorario.Turno.MANHA);
            }
            if (cbTarde.isSelected()) {
                turnos.add(DataHorario.Turno.TARDE);
            }
            if (cbNoite.isSelected()) {
                turnos.add(DataHorario.Turno.NOITE);
            }
            List<Aula> aulas = new AulaDAO().pegarPorDiaInstrutorTurnos(cbUsuario.getSelectionModel().getSelectedItem(), dataSelecionada, turnos.toArray(new DataHorario.Turno[]{}));
            List<CalendarioUsuario> calendarioUsuarios = new CalendarioUsuarioDAO().pegarTodosPorUsuarioData(cbUsuario.getSelectionModel().getSelectedItem(), dataSelecionada);
            if (aulas.isEmpty() && calendarioUsuarios.isEmpty()) {
                CalendarioUsuario calendarioUsuario = new CalendarioUsuario(new CalendarioUsuarioID(cbCalendario.getSelectionModel().getSelectedItem(), cbUsuario.getSelectionModel().getSelectedItem()));
                calendarioUsuario.setManha(cbManha.isSelected());
                calendarioUsuario.setTarde(cbTarde.isSelected());
                calendarioUsuario.setNoite(cbNoite.isSelected());
                if (new CalendarioUsuarioDAO().pegarPorId(calendarioUsuario.getId()) == null) {
                    new CalendarioUsuarioDAO().cadastrar(calendarioUsuario);
                } else {
                    new CalendarioUsuarioDAO().editar(calendarioUsuario);
                }
                Mensagem.showInformation("Usuário alocado", "O usuário " + cbUsuario.getSelectionModel().getSelectedItem().getNome() + " foi alocado ao evento\n"
                        + cbCalendario.getSelectionModel().getSelectedItem().getId().getEvento().getNome() + " corretamente!");
                this.calendarioUsuarios.setAll(new CalendarioUsuarioDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
            } else {
                String msgCalendarioUsuario = "No dia " + sdfData.format(dataSelecionada) + " haverá os seguintes eventos: \n";
                String msgAula = "No dia " + sdfData.format(dataSelecionada) + " haverá as seguintes aulas: \n";
                for (CalendarioUsuario calendarioUsuario : calendarioUsuarios) {
                    msgCalendarioUsuario += calendarioUsuario.getId().getCalendario().getId().getEvento().getNome() + " Usuário: " + calendarioUsuario.getId().getUsuario().getNome() + "\n";
                }
                for (Aula aula : aulas) {
                    msgAula += "Turma:" + aula.getMateriaHorario() + " " + aula.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getInstrutor().getNome() + " " + aula.getId().getHorario() + " Turno:" + aula.getId().getTurno() + "\n";
                }
                if (!aulas.isEmpty()) {
                    Mensagem.showInformation("Usuário com aulas ...", msgAula);
                }
                if (!calendarioUsuarios.isEmpty()) {
                    Mensagem.showInformation("Eventos com o usuário", msgCalendarioUsuario);
                }
            }
        } else {
            Mensagem.showError("Selecione usuário", "Nenhum usuário foi selecionado!");
        }
    }

    @FXML
    private void miRemoverAmbienteActionEvent(ActionEvent actionEvent) {
        if (tvAmbiente.getSelectionModel().getSelectedItem() != null) {
            CalendarioAmbiente calendarioAmbiente = tvAmbiente.getSelectionModel().getSelectedItem();
            new CalendarioAmbienteDAO().excluir(calendarioAmbiente);
            calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void miRemoverUsuarioActionEvent(ActionEvent actionEvent) {
        if (tvUsuario.getSelectionModel().getSelectedItem() != null) {
            CalendarioUsuario calendarioUsuario = tvUsuario.getSelectionModel().getSelectedItem();
            new CalendarioUsuarioDAO().excluir(calendarioUsuario);
            calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void btAdicionarGrupoActionEvent(ActionEvent actionEvent) {
        Calendario calendario = cbCalendario.getSelectionModel().getSelectedItem();
        if (calendario != null) {
            FxMananger.show("AdicionarGrupoUsuarioEvento", "Adicionar grupo de usuario a dias de evento", true, false, new Object[]{calendario.getId().getEvento()});
            calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
            calendarioUsuarios.setAll(new CalendarioUsuarioDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void cbEventoActionEvent(ActionEvent actionEvent) {
        calendarioAmbientes.setAll(new CalendarioAmbienteDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
        calendarioUsuarios.setAll(new CalendarioUsuarioDAO().pegarTodosPorCalendario(cbCalendario.getSelectionModel().getSelectedItem()));
    }

    public class HorarioUsuarioTableRender implements Callback<TableColumn<CalendarioUsuario, CalendarioUsuario>, TableCell<CalendarioUsuario, CalendarioUsuario>> {

        private DataHorario.Turno turno;

        public HorarioUsuarioTableRender(DataHorario.Turno turno) {
            this.turno = turno;
        }

        @Override
        public TableCell<CalendarioUsuario, CalendarioUsuario> call(TableColumn<CalendarioUsuario, CalendarioUsuario> param) {
            return new TableCell<CalendarioUsuario, CalendarioUsuario>() {

                @Override
                protected void updateItem(CalendarioUsuario item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    } else {
                        CheckBox checkBox = new CheckBox();
                        switch (turno) {
                            case MANHA:
                                checkBox.setSelected(item.isManha());
                                break;
                            case TARDE:
                                checkBox.setSelected(item.isTarde());
                                break;
                            case NOITE:
                                checkBox.setSelected(item.isNoite());
                                break;
                        }
                        checkBox.setOnAction((ActionEvent event) -> {
                            List<Aula> aulas = new AulaDAO().pegarPorDiaInstrutorTurnos(item.getId().getUsuario(), dataSelecionada, turno);
                            if (aulas.isEmpty() || !checkBox.isSelected()) {
                                switch (turno) {
                                    case MANHA:
                                        item.setManha(checkBox.isSelected());
                                        break;
                                    case TARDE:
                                        item.setTarde(checkBox.isSelected());
                                        break;
                                    case NOITE:
                                        item.setNoite(checkBox.isSelected());
                                        break;
                                }
                                new CalendarioUsuarioDAO().editar(item);
                            } else {
                                Mensagem.showError("Impossivel alocar nesse Horário", "Não é possível alocar nesse horário.\n"
                                        + "O instrutor " + item.getId().getUsuario() + "\n"
                                        + " possui aulas nesse turno!");
                                checkBox.setSelected(false);
                            }
                        });
                        setGraphic(checkBox);
                    }
                }
            };
        }
    }

    public class HorarioAmbienteTableRender implements Callback<TableColumn<CalendarioAmbiente, CalendarioAmbiente>, TableCell<CalendarioAmbiente, CalendarioAmbiente>> {

        private DataHorario.Turno turno;

        public HorarioAmbienteTableRender(DataHorario.Turno turno) {
            this.turno = turno;
        }

        @Override
        public TableCell<CalendarioAmbiente, CalendarioAmbiente> call(TableColumn<CalendarioAmbiente, CalendarioAmbiente> param) {
            return new TableCell<CalendarioAmbiente, CalendarioAmbiente>() {

                @Override
                protected void updateItem(CalendarioAmbiente item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText("");
                    } else {
                        CheckBox checkBox = new CheckBox();
                        switch (turno) {
                            case MANHA:
                                checkBox.setSelected(item.isManha());
                                break;
                            case TARDE:
                                checkBox.setSelected(item.isTarde());
                                break;
                            case NOITE:
                                checkBox.setSelected(item.isNoite());
                                break;
                        }
                        checkBox.setOnAction((ActionEvent event) -> {
                            List<Aula> aulas = new AulaDAO().pegarPorAmbienteDia(dataSelecionada, item.getId().getAmbiente(), turno);
                            if (aulas.isEmpty() || !checkBox.isSelected()) {
                                switch (turno) {
                                    case MANHA:
                                        item.setManha(checkBox.isSelected());
                                        break;
                                    case TARDE:
                                        item.setTarde(checkBox.isSelected());
                                        break;
                                    case NOITE:
                                        item.setNoite(checkBox.isSelected());
                                        break;
                                }
                                new CalendarioAmbienteDAO().editar(item);
                            } else {
                                Mensagem.showError("Ambiente indisponível", "O ambiente já foi alocado para aulas nesse turno.\n"
                                        + "Vá em \"Quadro de Horário\" para desmarcar as aulas \nda turma " + aulas.get(0).getId().getTurma() + ".");
                                checkBox.setSelected(false);
                            }
                        });
                        setGraphic(checkBox);
                    }
                }
            };
        }
    }
}
