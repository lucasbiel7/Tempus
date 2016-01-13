/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class SobreController implements Initializable {

    @FXML
    private Text tSobre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tSobre.setText("\tO Tempus foi iniciado em 2012 pela turma AIPJ07 do curso de aprendizagem em\n"
                + " programação JAVA em Belo Horizonte na unidade CETEL - Centro Tecnológico de eletro\n"
                + " eletrônica César Rodrigues – onde sua versão se chamava “Timesheet”. Nessa versão que foi\n"
                + "elaborado como ficariam as funcionalidades do sistema, em 2013 os alunos do projeto\n"
                + " finalizaram o curso trabalharam mais alguns meses no projeto, entretanto o projeto não\n"
                + " conseguiu alcançar sua conclusão para a implantação.\n"
                + "\n"
                + "\tO projeto “Timesheet” ficou parado ate final do ano de 2014 onde foi renascido pela\n"
                + "equipe da Olimpíada do conhecimento na modalidade #09-TI Soluções de software. A primeira\n"
                + "manutenção foi no sistema já desenvolvido foi adicionado as novas funcionalidades \n"
                + "requisitadas e consertado alguns erros e bug que havia no sistema, quando o sistema estava\n"
                + "quase concluído começou a encontrar limitação da tecnologia usada e o sistema foi\n"
                + "desenvolvido novamente utilizando outra tecnologia em março de 2015.\n\n"
                + "\tO novo sistema agora utilizando interfaces ricas teve que ter seu nome alterado por\n"
                + "que já existia um sistema que era usado com esse nome, foi quando surgiu o nome Tempus. Em\n"
                + "novembro de 2015 o sistema Tempus foi implantado pela primeira vez na escola, ainda\n"
                + "para teste, e no primeiro semestre de 2016 já funcionava em produção.\n\n"
                + "\n\n"
                + "Desenvolvedor\n"
                + "Lucas Gabriel de Souza Dutra\n"
                + "\nColaboradores\n"
                + "Pollyana Pimentel Reis\n"
                + "Natália Trindade de Souza\n"
                + "José Lucimar do Nascimento\n");
    }

}
