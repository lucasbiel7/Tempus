/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AtualizacaoDAO;
import br.com.QuadroDeHorario.dao.SistemaDAO;
import br.com.QuadroDeHorario.entity.Atualizacao;
import br.com.QuadroDeHorario.util.FxMananger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class CadastrarAtualizacaoController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfURL;
    @FXML
    private TextArea taObservacao;
    @FXML
    private TextField tfVersion;
    @FXML
    private CheckBox cbObrigatorio;
    @FXML
    private TextField tfDiretorio;

    /**
     * Initializes the controller class.
     */
    private Atualizacao atualizacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        atualizacao = new Atualizacao();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        atualizacao.setDateRelease(new Date());
        atualizacao.setSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
        atualizacao.setNome(tfNome.getText());
        atualizacao.setUrl(tfURL.getText());
        atualizacao.setObservacao(taObservacao.getText());
        atualizacao.setVersion(Double.parseDouble(tfVersion.getText()));
        atualizacao.setObrigatorio(cbObrigatorio.isSelected());
        new AtualizacaoDAO().cadastrar(atualizacao);
        btCancelarActionEvent(actionEvent);
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) tfNome.getScene().getWindow()).close();
    }

    @FXML
    private void btAdicionarArquivoActionEvent(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Arquivos executaveis", "jar"));
            File file = fileChooser.showOpenDialog(tfDiretorio.getScene().getWindow());
            atualizacao.setJar(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            tfDiretorio.setText(file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(CadastrarAtualizacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
