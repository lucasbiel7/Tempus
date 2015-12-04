/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.CursoDAO;
import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.MateriaDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioMateriaDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.entity.UsuarioMateria;
import br.com.QuadroDeHorario.model.util.Mensagem;
import br.com.QuadroDeHorario.model.util.Relatorios;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioMatrizCompetenciaController implements Initializable {

    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private SwingNode swPrincipal;
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<Curso> cursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbUsuario.setItems(usuarios);
        cbCurso.setItems(cursos);
        usuarios.setAll(new UsuarioDAO().pegarPorGrupo(new GrupoDAO().pegarPorId(2)));
        cursos.setAll(new CursoDAO().pegarTodos());
    }

    @FXML
    private void miRelatorioInstrutorActionEvent(ActionEvent actionEvent) {
        Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario", cbUsuario.getSelectionModel().getSelectedItem().getNome());
            String dados = "<html>";
            String competencia = "&#9630;";
            String preferencia = "&#9677;";
            String interesse = "&#9656;";
            dados += "<b><i>Legenda:</i></b><br/>"
                    + competencia + " " + UsuarioMateria.Tipo.COMPETENCIA + "<br/>"
                    + preferencia + " " + UsuarioMateria.Tipo.PREFERENCIA + "<br/>"
                    + interesse + " " + UsuarioMateria.Tipo.INTERESSE + "<br/><br/>";
            for (Curso curso : new CursoDAO().pegarTodos()) {
                List<UsuarioMateria> usuarioMaterias = new UsuarioMateriaDAO().pegarTodosPorUsuarioAgrupado(usuario, curso);
                if (!usuarioMaterias.isEmpty()) {
                    dados += "<font size=4><b>" + curso.getNome() + " </b></font><br/><br/>";
                    for (UsuarioMateria usuarioMateria : usuarioMaterias) {
                        dados += "<p>" + (usuarioMateria.getTipo() == UsuarioMateria.Tipo.COMPETENCIA ? competencia : usuarioMateria.getTipo() == UsuarioMateria.Tipo.INTERESSE ? interesse : preferencia) + " " + usuarioMateria.getId().getMateria().getNome() + "</p>";
                    }
                    dados += "<br>";
                }
            }
            parametros.put("image", new ImageIcon(getClass().getResource("/br/com/QuadroDeHorario/view/report/logo.png")).getImage());
            
            parametros.put("dados", dados);
            Relatorios relatorios = new Relatorios("MatrizCompetenciaUsuario", parametros);
            relatorios.carregarPainelSwingNode(swPrincipal);
        } else {
            Mensagem.showError("Selecione o usuário", "É necessário selecionar o usuário antes de continuar!");
        }
    }

    @FXML
    private void miRelatorioCursoActionEvent(ActionEvent actionEvent) {
        Curso curso = cbCurso.getSelectionModel().getSelectedItem();
        if (curso != null) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("curso", curso.getNome());
            String dados = "<html>";
            String competencia = "&#9630;";
            String preferencia = "&#9677;";
            String interesse = "&#9656;";
            dados += "<b><i>Legenda:</i></b><br/>"
                    + competencia + " " + UsuarioMateria.Tipo.COMPETENCIA + "<br/>"
                    + preferencia + " " + UsuarioMateria.Tipo.PREFERENCIA + "<br/>"
                    + interesse + " " + UsuarioMateria.Tipo.INTERESSE + "<br/><br/>";
            dados += "<font size=4><b><i>Componente curriculares</i></b></font><br/>";
            for (Materia materia : new MateriaDAO().pegarTodosPorCurso(curso)) {
                dados += "<font size=4><b>" + materia.getNome() + "</b></font><br/>";
                for (UsuarioMateria usuarioMateria : new UsuarioMateriaDAO().pegarTodosPorMateria(materia)) {
                    dados += "<p>" + (usuarioMateria.getTipo() == UsuarioMateria.Tipo.COMPETENCIA ? competencia : usuarioMateria.getTipo() == UsuarioMateria.Tipo.INTERESSE ? interesse : preferencia) + " " + usuarioMateria.getId().getUsuario().getNome() + "</p>";
                }
                dados += "<br/>";
            }
            parametros.put("dados", dados);
            parametros.put("image", new ImageIcon(getClass().getResource("/br/com/QuadroDeHorario/view/report/logo.png")).getImage());
            Relatorios relatorios = new Relatorios("MatrizCompetenciaCurso", parametros);
            relatorios.carregarPainelSwingNode(swPrincipal);
        } else {
            Mensagem.showError("Selecione o curso", "É necessário selecionar o curso antes de continuar!");
        }
    }
}
