/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
public class Calendario implements Serializable {

    @Embeddable
    public static class EventoData implements Serializable {

        @ManyToOne
        private Evento evento;
        @Temporal(javax.persistence.TemporalType.DATE)
        private Date dataAcontecimento;

        public EventoData() {

        }

        public EventoData(Date dataAcontecimento) {
            this.dataAcontecimento = dataAcontecimento;
        }

        public EventoData(Evento evento) {
            this.evento = evento;
        }

        public EventoData(Evento evento, Date dataAcontecimento) {
            this.evento = evento;
            this.dataAcontecimento = dataAcontecimento;
        }

        public Evento getEvento() {
            return evento;
        }

        public void setEvento(Evento evento) {
            this.evento = evento;
        }

        public Date getDataAcontecimento() {
            return dataAcontecimento;
        }

        public void setDataAcontecimento(Date dataAcontecimento) {
            this.dataAcontecimento = dataAcontecimento;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.evento);
            hash = 79 * hash + Objects.hashCode(this.dataAcontecimento);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final EventoData other = (EventoData) obj;
            if (!Objects.equals(this.evento, other.evento)) {
                return false;
            }
            if (!Objects.equals(this.dataAcontecimento, other.dataAcontecimento)) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    @Column(unique = true)
    private EventoData id;
    private boolean ativo = true;

    public Calendario() {
    }

    public Calendario(EventoData eventoData) {
        this.id = eventoData;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public EventoData getId() {
        return id;
    }

    public void setId(EventoData id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Calendario other = (Calendario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getId().getEvento().getNome() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(id.getDataAcontecimento());
    }
}
