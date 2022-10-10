/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Hp ProBook
 */
public class Usuario extends Pessoa{
    private long id;
    private String senha;
    private String usuario;
    private Perfil perfil;
    private boolean estado;
    private LocalDateTime dataHoraDecriacao;
    private LocalDateTime ultimoLogin;

    public Usuario() {
    }

    public Usuario(String nome, int idade, String morada, String telefone, String estadoCivil, String sexo) {
        super(nome, idade, morada, telefone, estadoCivil, sexo);
        
    }

    public Usuario(long id, String senha, String usuario, Perfil perfil, boolean estado, LocalDateTime dataHoraDecriacao, LocalDateTime ultimoLogin,String telefone,String nome) {
      
        this.id = id;
        this.senha = senha;
        this.usuario = usuario;
        this.telefone =telefone;
        this.perfil = perfil;
        this.estado = estado;
        this.dataHoraDecriacao = dataHoraDecriacao;
        this.ultimoLogin = ultimoLogin;
        this.nome = nome;
    }

  

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getDataHoraDecriacao() {
        return dataHoraDecriacao;
    }

    public void setDataHoraDecriacao(LocalDateTime dataHoraDecriacao) {
        this.dataHoraDecriacao = dataHoraDecriacao;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public void reset(){
    this.estado = true;
    }
    
    public void mudarEstado(){
    this.estado = !this.estado;
    }
}
