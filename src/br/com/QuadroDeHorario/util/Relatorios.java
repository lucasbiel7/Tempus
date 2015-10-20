/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author OCTI01
 */
public class Relatorios {

    private String nome;
    private Map<String, Object> parametros;

    public Relatorios(String nome, Map<String, Object> parametros) {
        this.nome = nome;
        this.parametros = parametros;
        for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
            if (lookAndFeelInfo.getName().equalsIgnoreCase("nimbus")) {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    UIManager.getLookAndFeelDefaults().put("background", new Color(118, 138, 165));
                    break;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/br/com/QuadroDeHorario/report/" + nome + ".jasper"), parametros, new JREmptyDataSource());
            painelRelatorio = new PainelRelatorio(jasperPrint, Locale.ROOT);
        } catch (JRException e) {
            System.err.println(e.getMessage());
        }
    }

    public Relatorios(String nome, Map<String, Object> parametros, TableModel tableModel) {
        this(nome, parametros);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/br/com/QuadroDeHorario/report/" + nome + ".jasper"), parametros, new JRTableModelDataSource(tableModel));
            painelRelatorio = new PainelRelatorio(jasperPrint, Locale.ROOT);
        } catch (JRException e) {
            System.err.println(e.getMessage());
        }
    }

    public void carregarPainelSwingNode(SwingNode swingNode) {
        Platform.runLater(() -> {
            SwingUtilities.invokeLater(() -> {
                swingNode.setContent(this.getPainelRelatorio());
                if (swingNode.getContent() != null) {
                    swingNode.getContent().revalidate();
                    swingNode.getContent().repaint();
                }
            });
        });
    }
    private PainelRelatorio painelRelatorio;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }

    public PainelRelatorio getPainelRelatorio() {
        return painelRelatorio;
    }

    public void setPainelRelatorio(PainelRelatorio painelRelatorio) {
        this.painelRelatorio = painelRelatorio;
    }

    public class PainelRelatorio extends JRViewer {

        public PainelRelatorio(JasperPrint jrPrint, Locale locale) {
            super(jrPrint, locale);
            btnSave.setVisible(false);
        }

        public PainelRelatorio(JasperPrint jrPrint, Locale locale, boolean editar) {
            this(jrPrint, locale);
            btnPrint.setVisible(false);
            btnActualSize.setVisible(false);
        }
    }
}
