/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.CursoDAO;
import br.com.QuadroDeHorario.control.dao.MateriaDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.util.Relatorios;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioGradeDoCursoController implements Initializable {

    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private SwingNode swRelatorio;

    private ObservableList<Curso> cursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursos.setAll(new CursoDAO().pegarTodos());
        cbCurso.setItems(cursos);
    }

    @FXML
    private void cbCursoActionEvent(ActionEvent actionEvent) {
        Curso curso = cbCurso.getSelectionModel().getSelectedItem();
        if (curso != null) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "<html><b>Matriz de curso</b><br/>"
                    + "<font color='blue'>" + curso.getTipoDoCurso().getDescricao() + "</font>");
            parametros.put("curso", curso.getNome());
            parametros.put("cargaHoraria", curso.getCargaHoraria());
            DefaultTableModel dtmTabela = new DefaultTableModel(new String[]{"Componente curricular", "Carga Horária"}, 0);
            for (Materia materia : new MateriaDAO().pegarTodosPorCurso(curso)) {
                dtmTabela.addRow(new Object[]{materia.getNome(), materia.getCargaHoraria()});
            }
            parametros.put("image", new ImageIcon(getClass().getResource("/br/com/QuadroDeHorario/view/report/senaifiemg.png")).getImage());
            Relatorios relatorios = new Relatorios("MatrizDoCurso", parametros, dtmTabela);
            relatorios.carregarPainelSwingNode(swRelatorio);
        }
    }
}
