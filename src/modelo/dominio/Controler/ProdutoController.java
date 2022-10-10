/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Dao.CategoriasDao;
import modelo.dominio.Dao.ProdutoDao;
import modelo.dominio.Dao.UsuarioDao;
import modelo.dominio.Dao.negocioException;
import modelo.dominio.Formulario.FormularioProdutos;
import modelo.dominio.Modelo.Categoria;
import modelo.dominio.Modelo.Produto;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class ProdutoController {

    private final FormularioProdutos formularioProdutos;
    private ConexaoMysql conexaoMysql;
    private ProdutoDao produtoDao;
    private UsuarioDao usuarioDao;
    private CategoriasDao categoriasDao;
    private Produto produto;
    private Categoria categoria;
    private List<Produto> listaProdutos;

    public ProdutoController(FormularioProdutos formularioProdutos) {
        this.produtoDao = new ProdutoDao();
        this.produto = new Produto();
        this.categoriasDao = new CategoriasDao();
        this.formularioProdutos = formularioProdutos;

    }

    private Produto pegarValoresDoFormulario() {
        Usuario usuarioTemp = usuarioLogado();
        String idString = this.formularioProdutos.getProdutoId().getText();
        String nomeString = this.formularioProdutos.getProdutoNome().getText();
        String precoString = this.formularioProdutos.getProdutoPreco().getText();
        String quantidadeString = this.formularioProdutos.getProdutoQuantidade().getText();
        String descricaoString = this.formularioProdutos.getProdutoDescricao().getText();
        String categoriaString = this.formularioProdutos.getProdutoCategoria().getSelectedItem() + "";

        validarCampos(nomeString, "nome");
        validarCampos(precoString, "preco");
        validarCampos(quantidadeString, "quantidade");

        Long id = Long.valueOf(idString);
        BigDecimal preco = validacaoDePrecoSeENumero(quantidadeString);
        Integer quantidade = validacaoDeQuantidadeSeENumero(quantidadeString);
        Categoria categoria = categoriasDao.buscarCategoriaPeloNome(nomeString);

        validacaoDeQuantidade(quantidade);
        validacaoDePreco(preco);
        validacaoDeCategoria();

        return new Produto(0, nomeString, descricaoString, preco, categoria, 0, usuarioTemp, LocalDateTime.MIN);

    }

    private Usuario usuarioLogado() {
        Long usuarioLogadoId = Long.valueOf(this.formularioProdutos.getProdutoId().getText());
        return usuarioDao.buscarUsuarioPeloId(usuarioLogadoId);
    }

    private void validarCampos(String campo, String nomeDaVariavel) {
        if (campo.isEmpty()) {
            String messagem = String.format("Deve preencher todos campos", nomeDaVariavel);
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }
    }

    private Integer validacaoDeQuantidadeSeENumero(String quantidadeString) {
        try {
            Integer quantidade = Integer.valueOf(quantidadeString);
            return quantidade;
        } catch (NumberFormatException e) {
            messagemNaTela("deve inserir um numero nesse campo", Color.RED);
            return 0;
        }
    }

    private BigDecimal validacaoDePrecoSeENumero(String quantidadeString) {
        try {
            BigDecimal preco = new BigDecimal(quantidadeString);
            return preco;
        } catch (NumberFormatException e) {
            messagemNaTela("deve inserir um numero nesse campo", Color.RED);
            return BigDecimal.ONE;
        }

    }

    private void validacaoDePreco(BigDecimal preco) {
        if (preco.doubleValue() < 1) {
            String messagem = String.format("Deve inserir um numero maior que zero:");
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }
    }

    private void validacaoDeQuantidade(Integer quantidade) {
        if (quantidade < 1) {
            String messagem = String.format("Deve inserir um numero maior que Zero");
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }
    }

    private void messagemNaTela(String messagem, Color color) {
        this.formularioProdutos.getProdutoMessagemErro().setText(messagem);
        this.formularioProdutos.getProdutoMessagemErro().setBackground(color);
        this.formularioProdutos.getProdutoMessagemErro().setForeground(color);
    }

    private void validacaoDeCategoria() {
        if (this.formularioProdutos.getProdutoCategoria().getSelectedIndex() == 0) {
            String messagem = String.format("deve selecionar a categoria ");
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }
    }
}
