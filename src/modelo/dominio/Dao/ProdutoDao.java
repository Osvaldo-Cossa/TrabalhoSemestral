/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Modelo.Categoria;
import modelo.dominio.Modelo.Cliente;
import modelo.dominio.Modelo.Perfil;
import modelo.dominio.Modelo.Produto;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class ProdutoDao {

    private final ConexaoMysql conexaoMysql;

    public ProdutoDao() {
        this.conexaoMysql = new ConexaoMysql();
    }

    public String salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome,descriaca_produto,preco,quantidade,categoria_id,usuario_id) VALUES(?,?,?,?,?,?)";
        Produto produtoTemp = buscarProdutoPeloNome(produto.getNome());
        if (produtoTemp != null) {
            return String.format("ja existe esse produtos na base de dados", produto.getId());
        }
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, produto);
            int resultodo = preparedStatement.executeUpdate();
            if (resultodo == 1) {
                return "Produto salvo com sucesso";
            } else {
                return "Nao foi Possivel salvar o Produto";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Erro: %s", e.getMessage());
        }

    }

    public String editar(Produto produto) {
        String sql = String.format("UPDATE produto SET nome=?,descriaca_produto=?,preco=?,quantidade=?,categoria_id=?,usuario_id=? WHERE id= %d", produto.getId());
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, produto);
            int resultodo = preparedStatement.executeUpdate();
            if (resultodo == 1) {
                return "Produto salvo com sucesso";
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(String.format("Erro: %s", e.getMessage()));
        }
        return null;
    }

    private void preparedStatementSet(PreparedStatement preparedStatement, Produto produto) throws SQLException {
        preparedStatement.setString(1, produto.getNome());
        preparedStatement.setString(2, produto.getDescricao());
        preparedStatement.setBigDecimal(3, produto.getPreco());
        preparedStatement.setInt(4, produto.getQuatidade());
        preparedStatement.setLong(5, produto.getCategodia().getId());
        preparedStatement.setLong(6, produto.getUsuario().getId());

        if (produto.getId() != 0) {
            preparedStatement.setLong(7, produto.getId());
        }

    }

    public Produto buscarProdutoPeloNome(String nome) {
        String sql = String.format("SELECT produto FROM WHERE nome = %s ", nome);

        try {

            ResultSet resultSet = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();

            if (resultSet.next()) {
                return getProduto(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Produto getProduto(ResultSet resultSet) throws SQLException {
        Produto produtoTemp = new Produto();
        Usuario usuario = new Usuario();
        Categoria categoria = new Categoria();

        categoria.setId(resultSet.getLong("c.id"));
        categoria.setNome(resultSet.getString("c.nome"));
        categoria.setDescricao(resultSet.getString("c.descricao"));

        usuario.setId(resultSet.getLong("u.id"));
        usuario.setNome(resultSet.getString("u.nome"));

        produtoTemp.setId(resultSet.getLong("id"));
        produtoTemp.setNome(resultSet.getString("nome"));
        produtoTemp.setPreco(resultSet.getBigDecimal("preco"));
        produtoTemp.setDescricao(resultSet.getString("descrica_produto"));
        produtoTemp.setQuatidade(resultSet.getInt("quantidade"));
        produtoTemp.setDataHoraCriacao(resultSet.getObject("data_hora_criacao", LocalDateTime.class));
        produtoTemp.setUsuario(usuario);
        return produtoTemp;
    }

    public String actualizarQuantidade(Long idProduto, Integer quantidade, Long usuarioId) {
        String sql = "UPDATE produto SET quantidade = ?, usuario_id = ? WHERE id  = ?";
        System.out.println("Venda: " + idProduto + "QTD: " + quantidade + "USUARIO: " + usuarioId);
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);

            preparedStatement.setInt(1, quantidade);
            preparedStatement.setLong(2, usuarioId);
            preparedStatement.setLong(3, idProduto);

            int resultado = preparedStatement.executeUpdate();

            if (resultado == 1) {
                return "Quantidade do produto editado com sucesso";
            } else {
                return "Nao foi possivel editar quantidade do produto";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Error: %s", e.getMessage());
        }
    }
}
