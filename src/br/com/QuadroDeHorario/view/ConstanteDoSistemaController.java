/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.SistemaDAO;
import br.com.QuadroDeHorario.control.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.model.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.model.util.Efeito;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConstanteDoSistemaController implements Initializable {

    @FXML
    private Spinner<Integer> spOcupacaoMinima;
    @FXML
    private CheckBox cbKeyGuardian;
    @FXML
    private TextField tfNomeEscola;
    @FXML
    private ImageView ivIconeFoto;
    @FXML
    private Label lbArrasteImage;
    private byte[] foto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spOcupacaoMinima.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spOcupacaoMinima.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spOcupacaoMinima.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() - steps <= 0) {
                    setValue(0);
                } else {
                    setValue(getValue() - steps);
                }
            }

            @Override
            public void increment(int steps) {
                if (spOcupacaoMinima.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spOcupacaoMinima.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() + steps > 100) {
                    setValue(100);
                } else {
                    setValue(getValue() + steps);
                }
            }
        });
        VariaveisDoSistema ocupacao = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.OCUPACAO);
        if (ocupacao != null) {
            spOcupacaoMinima.getValueFactory().setValue(Integer.parseInt(ocupacao.getValor()));
        }
        VariaveisDoSistema keyGuardian = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.KEYGUARDIAN);
        if (keyGuardian != null) {
            cbKeyGuardian.setSelected(Boolean.valueOf(keyGuardian.getValor()));
        }
        VariaveisDoSistema nomeEscola = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.ESCOLA);
        if (nomeEscola != null) {
            tfNomeEscola.setText(nomeEscola.getValor());
            foto = nomeEscola.getFoto();
            if (foto != null) {
                ivIconeFoto.setImage(new Image(new ByteArrayInputStream(foto)));
                lbArrasteImage.setText("");
            }
        }
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        VariaveisDoSistema ocupacao = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.OCUPACAO);
        if (ocupacao == null) {
            ocupacao = new VariaveisDoSistema();
            ocupacao.setNome(VariaveisDoSistema.NOME.OCUPACAO.toString());
            ocupacao.setValor(String.valueOf(spOcupacaoMinima.getValue()));
            ocupacao.setSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
            new VariaveisDoSistemaDAO().cadastrar(ocupacao);
        } else {
            ocupacao.setValor(String.valueOf(spOcupacaoMinima.getValue()));
            new VariaveisDoSistemaDAO().editar(ocupacao);
        }
        VariaveisDoSistema keyGuardian = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.KEYGUARDIAN);
        if (keyGuardian == null) {
            keyGuardian = new VariaveisDoSistema();
            keyGuardian.setNome(VariaveisDoSistema.NOME.KEYGUARDIAN.toString());
            keyGuardian.setValor(String.valueOf(cbKeyGuardian.isSelected()));
            keyGuardian.setSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
            new VariaveisDoSistemaDAO().cadastrar(keyGuardian);
        } else {
            keyGuardian.setValor(String.valueOf(cbKeyGuardian.isSelected()));
            new VariaveisDoSistemaDAO().editar(keyGuardian);
        }
        VariaveisDoSistema nomeDaEscola = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.ESCOLA);
        if (nomeDaEscola == null) {
            nomeDaEscola = new VariaveisDoSistema();
            nomeDaEscola.setNome(VariaveisDoSistema.NOME.ESCOLA.toString());
            nomeDaEscola.setValor(tfNomeEscola.getText());
            nomeDaEscola.setSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
            nomeDaEscola.setFoto(foto);
            new VariaveisDoSistemaDAO().cadastrar(nomeDaEscola);
        } else {
            nomeDaEscola.setFoto(foto);
            nomeDaEscola.setValor(tfNomeEscola.getText());
            new VariaveisDoSistemaDAO().editar(nomeDaEscola);
        }
        Efeito.nomeDoPrograma = nomeDaEscola;
        Mensagem.showInformation("Constantes alteradas", "Todos os valores das constantes do sistema foram \n"
                + "atualizas com sucesso!");
    }

    @FXML
    private void ivIconeFotoOnDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
        dragEvent.consume();
    }

    @FXML
    private void ivIconeFotoOnDragDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            if (!dragboard.getFiles().isEmpty()) {
                try {
                    File file = dragboard.getFiles().get(0);
                    if (file.getName().matches("^(.*\\.(png|jpg|jpeg|gif)+)$")) {
                        foto = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                        lbArrasteImage.setText("");
                        ivIconeFoto.setImage(new Image(new ByteArrayInputStream(foto)));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ConstanteDoSistemaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }

    @FXML
    private void btProcurarImagemActionEvent(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(tfNomeEscola.getScene().getWindow());
        if (file != null) {
            try {
                foto = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                lbArrasteImage.setText("");
                ivIconeFoto.setImage(new Image(new ByteArrayInputStream(foto)));
            } catch (IOException ex) {
                Logger.getLogger(ConstanteDoSistemaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btApagarActionEvent(ActionEvent actionEvent) {
        foto = null;
        lbArrasteImage.setText("Arraste a imagem aqui");
        ivIconeFoto.setImage(null);
    }
}
