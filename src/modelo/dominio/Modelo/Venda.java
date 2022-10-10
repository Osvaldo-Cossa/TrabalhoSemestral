/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *
 * @author Hp ProBook
 */
public class Venda {
    private long id;
    private Cliente cliente;
    private Usuario usuario;
    private BigDecimal totalDaVenda;
    private BigDecimal valorPago;
    private BigDecimal descontoTotal;
    private BigDecimal troco;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime ultimaActualizacao;
    private HashMap<String,VendasItem> vendasItem;

    public HashMap<String, VendasItem> getVendasItem() {
        return vendasItem;
    }

    public void setVendasItem(HashMap<String, VendasItem> vendasItem) {
        this.vendasItem = vendasItem;
    }

    public Venda() {
    }

    public Venda(long id, Cliente cliente, Usuario usuario, BigDecimal totalDaVenda, BigDecimal valorPago, BigDecimal descontoTotal, BigDecimal troco, LocalDateTime dataHoraCriacao, LocalDateTime ultimaActualizacao) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.totalDaVenda = totalDaVenda;
        this.valorPago = valorPago;
        this.descontoTotal = descontoTotal;
        this.troco = troco;
        this.dataHoraCriacao = dataHoraCriacao;
        this.ultimaActualizacao = ultimaActualizacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotalDaVenda() {
        return totalDaVenda;
    }

    public void setTotalDaVenda(BigDecimal totalDaVenda) {
        this.totalDaVenda = totalDaVenda;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(BigDecimal descontoTotal) {
        this.descontoTotal = descontoTotal;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public LocalDateTime getUltimaActualizacao() {
        return ultimaActualizacao;
    }

    public void setUltimaActualizacao(LocalDateTime ultimaActualizacao) {
        this.ultimaActualizacao = ultimaActualizacao;
    }
    
    
}
