/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.SerialCommunication;
import br.com.QuadroDeHorario.control.SerialUtil;
import br.com.QuadroDeHorario.dao.CursoDAO;
import br.com.QuadroDeHorario.dao.EscolaridadeDAO;
import br.com.QuadroDeHorario.dao.GrupoDAO;
import br.com.QuadroDeHorario.dao.MateriaDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.dao.UsuarioMateriaDAO;
import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.Escolaridade;
import br.com.QuadroDeHorario.entity.Escolaridade.TipoEscolaridade;
import br.com.QuadroDeHorario.entity.Grupo;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.entity.UsuarioMateria;
import br.com.QuadroDeHorario.entity.UsuarioMateriaID;
import br.com.QuadroDeHorario.model.SerialConstants;
import br.com.QuadroDeHorario.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarUsuarioController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private ComboBox<Grupo> cbGrupo;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfCelular;
    @FXML
    private TextField tfEndereco;
    @FXML
    private Spinner<Integer> spCargaHoraria;
    @FXML
    private TextArea taObservacao;
    @FXML
    private ComboBox<Materia> cbMateria;
    @FXML
    private TextField tfCartao;
    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private ComboBox<UsuarioMateria.Tipo> cbCompetencia;
    @FXML
    private TableView<UsuarioMateria> tvUsuarioMateria;
    @FXML
    private TableColumn<UsuarioMateria, String> tcDisciplina;
    @FXML
    private TableColumn<UsuarioMateria, String> tcCurso;
    @FXML
    private TableColumn<UsuarioMateria, String> tcTipo;
    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private TableColumn<Usuario, String> tcNome;
    @FXML
    private TableColumn<Usuario, String> tcEmail;
    @FXML
    private TableColumn<Usuario, String> tcGrupo;
    @FXML
    private TableColumn<Usuario, String> tcTelefone;
    @FXML
    private TableColumn<Usuario, String> tcCelular;
    @FXML
    private TableColumn<Usuario, String> tcEndereco;
    @FXML
    private TableColumn<Usuario, String> tcCargaHoraria;
    @FXML
    private Circle cFoto;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private CheckBox cbManha;
    @FXML
    private CheckBox cbTarde;
    @FXML
    private CheckBox cbNoite;
    @FXML
    private TextField tfNomeCurso;
    @FXML
    private ComboBox<Escolaridade.TipoEscolaridade> cbEscolaridade;
    @FXML
    private CheckBox cbCompleto;
    @FXML
    private TableView<Escolaridade> tvEscolaridade;
    @FXML
    private TableColumn<Escolaridade, String> tcEscolaridade;
    @FXML
    private TableColumn<Escolaridade, String> tcNomeCurso;
    @FXML
    private TableColumn<Escolaridade, Boolean> tcCompleto;
    @FXML
    private Button btLerCartao;

    private ObservableList<Escolaridade.TipoEscolaridade> tipoEscolaridades = FXCollections.observableArrayList();
    private ObservableList<Escolaridade> escolaridades = FXCollections.observableArrayList();
    private ObservableList<UsuarioMateria.Tipo> tipo = FXCollections.observableArrayList();
    private ObservableList<Materia> materias = FXCollections.observableArrayList();
    private ObservableList<UsuarioMateria> usuarioMaterias = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<Grupo> grupos = FXCollections.observableArrayList();
    private ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private Stage stage;
    private Usuario usuario;
    private Escolaridade escolaridade;

    //Cartao
    private Timeline procuraCartao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (tvUsuario.getScene() != null) {
                stage = (Stage) tvUsuario.getScene().getWindow();
            }
        });
        cbEscolaridade.setItems(tipoEscolaridades);
        tipoEscolaridades.setAll(TipoEscolaridade.values());
        spCargaHoraria.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spCargaHoraria.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCargaHoraria.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() - steps >= 0) {
                    setValue(getValue() - steps);
                }
            }

            @Override
            public void increment(int steps) {
                if (spCargaHoraria.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCargaHoraria.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                setValue(getValue() + steps);
            }
        });
        cursos.setAll(new CursoDAO().pegarTodos());
        cbCurso.setItems(cursos);
        spCargaHoraria.getValueFactory().setValue(0);
        cbCompetencia.setItems(tipo);
        cbGrupo.setItems(grupos);
        cbMateria.setItems(materias);
        tvUsuarioMateria.setItems(usuarioMaterias);
        tvUsuario.setItems(usuarios);
        tcDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCurso.setCellValueFactory((TableColumn.CellDataFeatures<UsuarioMateria, String> param) -> new SimpleStringProperty(param.getValue().getId().getMateria().getCurso().getNome()));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        grupos.setAll(new GrupoDAO().pegarTodos());
        tipo.setAll(UsuarioMateria.Tipo.values());
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        //tabela Usuário
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        tcEndereco.setCellFactory((TableColumn<Usuario, String> param) -> {
            return new TableCell<Usuario, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    setText(item);
                    if (item != null) {
                        setTooltip(new Tooltip(item));
                    }
                }
            };
        });
        //Tabela escolaridade
        tcEscolaridade.setCellValueFactory(new PropertyValueFactory<>("tipoEscolaridade"));
        tcNomeCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tcCompleto.setCellValueFactory(new PropertyValueFactory<>("completo"));
        tcCompleto.setCellFactory((TableColumn<Escolaridade, Boolean> param) -> new TableCell<Escolaridade, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    setAlignment(Pos.CENTER);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });
        tvEscolaridade.setItems(escolaridades);
        usuario = new Usuario();
        btLerCartao.setText("Conectar");
        carregarDados();
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        Materia materia = cbMateria.getSelectionModel().getSelectedItem();
        UsuarioMateria usuarioMateria = new UsuarioMateria(new UsuarioMateriaID(usuario, materia), cbCompetencia.getSelectionModel().getSelectedItem());
        if (!usuarioMaterias.contains(usuarioMateria)) {
            usuarioMaterias.add(usuarioMateria);
        } else {
            usuarioMaterias.remove(usuarioMateria);
            usuarioMaterias.add(usuarioMateria);
        }
        tvUsuarioMateria.getSelectionModel().clearSelection();
        cbCurso.getSelectionModel().clearSelection();
        cbCompetencia.getSelectionModel().clearSelection();
        cbMateria.getSelectionModel().clearSelection();
    }

    @FXML
    private void btFotoActionEvent(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Imagem", "jpg", "png", "gif"));
        fileChooser.setTitle("Imagem de perfil");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                usuario.setFoto(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                cFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(usuario.getFoto()))));
            } catch (IOException ex) {
                Logger.getLogger(GerenciarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        usuario.setCargaHoraria(spCargaHoraria.getValue());
        usuario.setCelular(tfCelular.getText());
        usuario.setEmail(tfEmail.getText());
        usuario.setEndereco(tfEndereco.getText());
        usuario.setGrupo(cbGrupo.getSelectionModel().getSelectedItem());
        usuario.setLogin(tfLogin.getText());
        usuario.setNome(tfNome.getText());
        usuario.setObservacao(taObservacao.getText());
        usuario.setCartao(tfCartao.getText());
        if (!pfSenha.getText().isEmpty()) {
            usuario.setSenha(pfSenha.getText());
        }
        usuario.setManha(cbManha.isSelected());
        usuario.setTarde(cbTarde.isSelected());
        usuario.setNoite(cbNoite.isSelected());
        usuario.setTelefone(tfTelefone.getText());
        if (usuario.getId() == null) {
            if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
                Mensagem.showError("Falha ao cadastrar", "O campo de senha está vazio!");
                return;
            } else {
                new UsuarioDAO().cadastrar(usuario);
                for (Escolaridade escolaridade : escolaridades) {
                    escolaridade.setUsuario(usuario);
                    new EscolaridadeDAO().cadastrar(escolaridade);
                }
                Mensagem.showInformation("Cadastrado", "Usuário cadastrado com sucesso!");
            }
        } else {
            try {
                new UsuarioDAO().editar(usuario);
                Mensagem.showInformation("Editado", "Usuário editado com sucesso!");
            } catch (ConstraintViolationException e) {
                Mensagem.showError("Cartão duplicado", "Não é possível cadastrar o mesmo cartão\n"
                        + " para dois usuários do sistemas");
            }

        }
        for (UsuarioMateria usuarioMateria : new UsuarioMateriaDAO().pegarTodosPorUsuario(usuario)) {
            usuarioMateria.setAtivo(false);
            new UsuarioMateriaDAO().editar(usuarioMateria);
        }
        for (UsuarioMateria usuarioMateria : usuarioMaterias) {
            usuarioMateria.getId().setUsuario(usuario);
            usuarioMateria.setAtivo(true);
            if (new UsuarioMateriaDAO().pegarTodosPorUsuarioMateria(usuario, usuarioMateria.getId().getMateria()) == null) {
                new UsuarioMateriaDAO().cadastrar(usuarioMateria);
            } else {
                new UsuarioMateriaDAO().editar(usuarioMateria);
            }
        }
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        usuario = new Usuario();
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        usuario = new Usuario();
        carregarDados();
    }

    @FXML
    private void tvUsuarioMouseReleased(MouseEvent mouseEvent) {
        usuario = tvUsuario.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void tvUsuarioDisciplinaMouseReleased(MouseEvent mouseEvent) {
        UsuarioMateria usuarioMateria = tvUsuarioMateria.getSelectionModel().getSelectedItem();
        if (usuarioMateria != null) {
            cbCurso.getSelectionModel().select(usuarioMateria.getId().getMateria().getCurso());
            cbCompetencia.getSelectionModel().select(usuarioMateria.getTipo());
            cbMateria.getSelectionModel().select(usuarioMateria.getId().getMateria());
        }
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        usuario = tvUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            if (Mensagem.showConfirmation("Exclusão", "Você tem certeza que deseja excluir esse usuário?")) {
                new UsuarioDAO().excluir(usuario);
            }
            usuarios.setAll(new UsuarioDAO().pegarTodos());
            usuario = new Usuario();
            carregarDados();
        }
    }

    @FXML
    private void cbCursoActionEvent(ActionEvent actionEvent) {
        if (cbCurso.getSelectionModel().getSelectedItem() != null) {
            materias.setAll(new MateriaDAO().pegarTodosPorCurso(cbCurso.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void jtPesquisaKeyRelease(KeyEvent keyEvent) {
        usuarios.setAll(new UsuarioDAO().pegarPorNome(tfPesquisa.getText()));
    }

    @FXML
    private void rbTurnosActionEvent(ActionEvent actionEvent) {
        if (cbManha.isSelected() && cbTarde.isSelected() && cbNoite.isSelected()) {
            spCargaHoraria.getValueFactory().setValue(15);
        } else if ((cbManha.isSelected() && cbTarde.isSelected()) || (cbTarde.isSelected() && cbNoite.isSelected())) {
            spCargaHoraria.getValueFactory().setValue(10);
        } else if (cbManha.isSelected() || cbTarde.isSelected() || cbNoite.isSelected()) {
            spCargaHoraria.getValueFactory().setValue(5);
        } else {
            spCargaHoraria.getValueFactory().setValue(0);
        }

    }

    @FXML
    private void btSalvarNomeCursoActionEvent(ActionEvent actionEvent) {
        if (escolaridade == null) {
            escolaridade = new Escolaridade();
        }
        escolaridade.setCurso(tfNomeCurso.getText());
        escolaridade.setCompleto(cbCompleto.isSelected());
        escolaridade.setTipoEscolaridade(cbEscolaridade.getValue());
        if (usuario == null || usuario.getId() == null) {
            escolaridades.add(escolaridade);
        } else {
            escolaridade.setUsuario(usuario);
            if (escolaridade.getId() == 0) {
                new EscolaridadeDAO().cadastrar(escolaridade);
                //Atualizar a lista da escolaridade do usuario
                escolaridades.setAll(new EscolaridadeDAO().pegarPorUsuario(usuario));
            } else {
                new EscolaridadeDAO().editar(escolaridade);
                //Atualizar a lista da escolaridade do usuario
                escolaridades.setAll(new EscolaridadeDAO().pegarPorUsuario(usuario));
            }
        }
        escolaridade = new Escolaridade();
        tfNomeCurso.setText("");
        cbEscolaridade.getSelectionModel().clearSelection();
        cbCompleto.setSelected(false);
    }

    @FXML
    private void tvEscolaridadeMouseReleased(MouseEvent mouseEvent) {
        escolaridade = tvEscolaridade.getSelectionModel().getSelectedItem();
        if (escolaridade != null) {
            tfNomeCurso.setText(escolaridade.getCurso());
            cbEscolaridade.getSelectionModel().select(escolaridade.getTipoEscolaridade());
            cbCompleto.setSelected(escolaridade.isCompleto());
        }
    }

    @FXML
    private void miExcluirEscolaridadeActionEvent(ActionEvent actionEvent) {
        Escolaridade escolaridade = tvEscolaridade.getSelectionModel().getSelectedItem();
        if (escolaridade != null) {
            if (Mensagem.showConfirmation("Excluir escolaridade!", "Você deseja excluir a seguinte escolaridade: \n"
                    + "Curso: " + escolaridade.getCurso() + "\n"
                    + "Escolaridade: " + escolaridade.getTipoEscolaridade())) {
                new EscolaridadeDAO().excluir(escolaridade);
                escolaridades.setAll(new EscolaridadeDAO().pegarPorUsuario(usuario));
            }
        }
    }

    @FXML
    private void miExcluirUsuarioDisciplinaActionEvent(ActionEvent actionEvent) {
        UsuarioMateria usuarioMateria = tvUsuarioMateria.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            usuarioMaterias.remove(usuarioMateria);
        }
    }

    @FXML
    private void btLerCartaoActionEvent(ActionEvent actionEvent) {
        procuraCartao = new Timeline(new KeyFrame(Duration.seconds(3d), (ActionEvent event) -> {
            if (SerialUtil.serialCommunication.isConexao()) {
                btLerCartao.setText("Ler Cartão");
            } else {
                btLerCartao.setText("Conectar");
            }
            if (SerialUtil.serialCommunication.getLeitura() != null) {
                tfCartao.setText(SerialUtil.serialCommunication.getLeitura());
                SerialUtil.serialCommunication.setLeitura(null);
                SerialUtil.serialCommunication.fecharPorta();
            }
        }));
        procuraCartao.setCycleCount(Timeline.INDEFINITE);
        if (SerialUtil.serialCommunication == null) {
            SerialUtil.serialCommunication = new SerialCommunication();
            btLerCartao.setText("Aguarde dispositivo");
            new Thread(SerialUtil.serialCommunication).start();
            procuraCartao.play();
        } else if (SerialUtil.serialCommunication.isConexao()) {
            SerialUtil.serialCommunication.enviarMensagem(SerialConstants.REQUISITAR_CARTAO);
        } else {
            SerialUtil.serialCommunication.reConectar();
            procuraCartao.play();
        }
    }

    private void carregarDados() {
        tfNomeCurso.setText("");
        cbEscolaridade.getSelectionModel().clearSelection();
        cbCompleto.setSelected(false);
        usuarioMaterias.clear();
        cbCompetencia.getSelectionModel().clearSelection();
        cbMateria.getSelectionModel().clearSelection();
        cbCurso.getSelectionModel().clearSelection();
        if (usuario == null) {
            tfNome.setText("");
            tfCelular.setText("");
            tfEndereco.setText("");
            pfSenha.setText("");
            tfCartao.clear();
            spCargaHoraria.getValueFactory().setValue(0);
            cFoto.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/br/com/QuadroDeHorario/view/image/perfil.png"))));
            tfLogin.setText("");
            tfTelefone.setText("");
            tfEmail.setText("");
            cbManha.setSelected(false);
            cbTarde.setSelected(false);
            cbNoite.setSelected(false);
            cbGrupo.getSelectionModel().clearSelection();
            taObservacao.setText("");
            escolaridades.clear();
        } else {
            tfNome.setText(usuario.getNome());
            tfCelular.setText(usuario.getCelular());
            tfEndereco.setText(usuario.getEndereco());
            tfLogin.setText(usuario.getLogin());
            tfTelefone.setText(usuario.getTelefone());
            tfEmail.setText(usuario.getEmail());
            tfCartao.setText(usuario.getCartao());
            pfSenha.setText("");
            cbManha.setSelected(usuario.isManha());
            cbTarde.setSelected(usuario.isTarde());
            cbNoite.setSelected(usuario.isNoite());
            if (usuario.getId() != null) {
                escolaridades.setAll(new EscolaridadeDAO().pegarPorUsuario(usuario));
            }
            if (usuario.getFoto() != null) {
                cFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(usuario.getFoto()))));
            } else {
                cFoto.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/br/com/QuadroDeHorario/view/image/perfil.png"))));
            }
            spCargaHoraria.getValueFactory().setValue(usuario.getCargaHoraria());
            cbGrupo.getSelectionModel().select(usuario.getGrupo());
            taObservacao.setText(usuario.getObservacao());
            usuarioMaterias.clear();
            if (usuario.getId() != null) {
                usuarioMaterias.setAll(new UsuarioMateriaDAO().pegarTodosPorUsuario(usuario));
            }
        }
    }
}
