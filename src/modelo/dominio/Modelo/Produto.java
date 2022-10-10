/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Hp ProBook
 */
public class Produto {
    private long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Categoria categodia;
    private int quatidade;
    private Usuario usuario;
    private LocalDateTime dataHoraCriacao;

    public Produto() {
    }
    

    public Produto(long id, String nome, String descricao, BigDecimal preco, Categoria categodia, int quatidade, Usuario usuario, LocalDateTime dataHoraCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categodia = categodia;
        this.quatidade = quatidade;
        this.usuario = usuario;
        this.dataHoraCriacao = dataHoraCriacao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Categoria getCategodia() {
        return categodia;
    }

    public void setCategodia(Categoria categodia) {
        this.categodia = categodia;
    }

    public int getQuatidade() {
        return quatidade;
    }

    public void setQuatidade(int quatidade) {
        this.quatidade = quatidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }
    
    
}
