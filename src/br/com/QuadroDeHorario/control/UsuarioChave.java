/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.EmprestaChave;
import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.List;

/**
 *
 * @author OCTI01
 */
public class UsuarioChave {

    public static final int INSTRUTOR = 2;
    public static final int CORDENACAO = 1;
    public static final int COMPETIDOR = 7;

    public static boolean validar(EmprestaChave usuarioAmbiente) {
        if (usuarioAmbiente != null) {
            List<Aula> aulas;
            switch (usuarioAmbiente.getUsuario().getGrupo().getId()) {
                case INSTRUTOR:
                    aulas = new AulaDAO().pegarPorDiaInstrutor(usuarioAmbiente.getUsuario(), new UsuarioDAO().dataAtual(), DataHorario.Turno.getTurnoAtual());
                    DataHorario.Horario.getHorarioAtual();
                    aulas.removeIf((Aula t) -> (!t.getAmbiente().equals(usuarioAmbiente.getAmbiente()) || !t.getId().getHorario().equals(DataHorario.Horario.getHorarioAtual())));
                    return !aulas.isEmpty() || ambienteVazio(usuarioAmbiente);
                case CORDENACAO:
                    return true;
                case COMPETIDOR:
                    return ambienteVazio(usuarioAmbiente);
            }
        }
        return false;
    }

    public static boolean ambienteVazio(EmprestaChave emprestaChave) {
        List<Aula> aulas = new AulaDAO().pegarPorAmbienteDia(new UsuarioDAO().dataAtual(), emprestaChave.getAmbiente(), DataHorario.Turno.getTurnoAtual());
        return aulas.isEmpty();
    }

}
