/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Hp ProBook
 */
public class VendasItem {
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private BigDecimal total;
    private BigDecimal desconto;

    public VendasItem() {
    }

    public VendasItem(Venda venda, Produto produto, int quantidade, BigDecimal total, BigDecimal desconto) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.total = total;
        this.desconto = desconto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
    
}
