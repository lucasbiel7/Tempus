/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import br.com.QuadroDeHorario.control.Seguranca;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author OCTI01
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String login;
    private String nome;
    private String senha;
    @Basic(optional = false)
    private boolean ativo = true;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacao;
    @Lob
    private byte[] foto;
    private String modalidade;
    @JoinColumn(name = "unidade_id", referencedColumnName = "id")
    @ManyToOne
    private Unidade unidadeId;
    @JoinColumn(name = "grupo_id", referencedColumnName = "id")
    @ManyToOne
    private Grupo grupo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private TokenCode tokenCode;
    private String telefone;
    private String celular;
    private String endereco;
    private String observacao;
    private int cargaHoraria;
    private boolean manha;
    private boolean tarde;
    private boolean noite;
    private boolean logado;
    private String sistemaLogado;
    @Column(unique = true)
    private String cartao;
    @OneToMany(mappedBy = "materiaTurmaInstrutorSemestre.instrutor", cascade = CascadeType.REMOVE)
    private List<MateriaHorario> materiaHorarios;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Escolaridade> escolaridades;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<EmprestaChave> usuarioAmbientes;
    @OneToMany(mappedBy = "id.usuario", cascade = CascadeType.REMOVE)
    private List<PermissaoUsuario> permissaoUsuarios;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, boolean ativo) {
        this.id = id;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Seguranca.criptografar(senha);
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getUltimaModificacao() {
        return ultimaModificacao;
    }

    public void setUltimaModificacao(Date ultimaModificacao) {
        this.ultimaModificacao = ultimaModificacao;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public Unidade getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Unidade unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TokenCode getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(TokenCode tokenCode) {
        this.tokenCode = tokenCode;
    }

    public boolean isManha() {
        return manha;
    }

    public void setManha(boolean manha) {
        this.manha = manha;
    }

    public boolean isTarde() {
        return tarde;
    }

    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    public boolean isNoite() {
        return noite;
    }

    public void setNoite(boolean noite) {
        this.noite = noite;
    }

    public String getSistemaLogado() {
        return sistemaLogado;
    }

    public void setSistemaLogado(String sistemaLogado) {
        this.sistemaLogado = sistemaLogado;
    }

    public List<Escolaridade> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(List<Escolaridade> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public List<MateriaHorario> getMateriaHorarios() {
        return materiaHorarios;
    }

    public void setMateriaHorarios(List<MateriaHorario> materiaHorarios) {
        this.materiaHorarios = materiaHorarios;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
