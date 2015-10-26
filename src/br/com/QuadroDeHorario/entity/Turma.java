/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.entity;

import br.com.QuadroDeHorario.util.DataHorario.Turno;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t")})
public class Turma implements Serializable {
   
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String descricao;
    @Basic(optional = false)
    private boolean ativo = true;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne
    private Curso curso;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fim;
    @OneToOne
    private Turma turmaPrincipal;
    private boolean espelho;
    private String email;
    private Turno turno;
    private int modulo;
    @ManyToOne
    private Projeto projeto;
    @OneToMany(mappedBy = "materiaTurmaIntrutorSemestre.turma")
    private List<MateriaHorario> materiaHorarios;
    @OneToMany(mappedBy = "turma")
    private List<ObservacaoAula> observacaoAulas;

    public Turma() {
    }

    public Turma(Integer id) {
        this.id = id;
    }

    public Turma(Integer id, boolean ativo) {
        this.id = id;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Turma getTurmaPrincipal() {
        return turmaPrincipal;
    }

    public void setTurmaPrincipal(Turma turmaPrincipal) {
        this.turmaPrincipal = turmaPrincipal;
    }

    public boolean isEspelho() {
        return espelho;
    }

    public void setEspelho(boolean espelho) {
        this.espelho = espelho;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<MateriaHorario> getMateriaHorarios() {
        return materiaHorarios;
    }

    public void setMateriaHorarios(List<MateriaHorario> materiaHorarios) {
        this.materiaHorarios = materiaHorarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
