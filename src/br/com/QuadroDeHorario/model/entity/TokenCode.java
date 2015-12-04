/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import br.com.QuadroDeHorario.control.Security;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author OCTI01
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TokenCode.findAll", query = "SELECT t FROM TokenCode t")})
public class TokenCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChange;
    private String token;
    private boolean ativo = true;
    @Id
    @OneToOne
    private Usuario usuario;

    public TokenCode() {
    }

    public TokenCode(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = Security.criptografar(token);
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.usuario);
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
        final TokenCode other = (TokenCode) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.QuadroDeHorario.entity.TokenCode[ usuarioId=" + usuario.getId() + " ]";
    }

}
