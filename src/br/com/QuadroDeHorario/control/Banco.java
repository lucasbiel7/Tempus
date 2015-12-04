/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.model.util.ParametrosBanco;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author OCTI01
 */
public class Banco {

    private static SessionFactory sessionFactory;

    static {
        buildSessionFactory(ParametrosBanco.carregarPropriedades());
    }

    public static void buildSessionFactory(Properties proprieadades) {
        try {
            sessionFactory = new Configuration().setProperties(proprieadades).configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
