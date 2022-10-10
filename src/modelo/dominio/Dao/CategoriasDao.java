/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Modelo.Categoria;

/**
 *
 * @author Hp ProBook
 */
public class CategoriasDao {

    private final ConexaoMysql conexaoMysql;

    public CategoriasDao() {
        this.conexaoMysql = new ConexaoMysql();
    }

    public String salvar(Categoria categoria) {
        if (categoria.getId() == 0) {
            return adcionar(categoria);
        } else {
            return editar(categoria);
        }
    }

    private String adcionar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome, descricao) VALUES(?,?)";

        Categoria categoriaTemp = buscarCategoriaPeloNome(categoria.getNome());
        if (categoriaTemp != null) {
            return String.format("Error: %s ja existe no banco de dados", categoriaTemp.getNome());
        }
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, categoria);
            int resultado = preparedStatement.executeUpdate();
            if (resultado == 1) {
                return String.format("categoria adcionado com sucesso");
            } else {
                return String.format("Nao foi Possivel adcinoar Categoria");
            }
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }

    }

    public String editar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome =?, descricao = ?";
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, categoria);
            int resultado = preparedStatement.executeUpdate();
            if (resultado == 1) {
                return String.format("Categoria editado com sucesso");
            } else {
                return String.format("nao foi Possivel edtitar a categoria");
            }
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    public Categoria buscarCategoriaPeloNome(String nome) {
       String sql = String.format("SELECT * FORM categoria WHERE nome= %s", nome);
        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()){
            return getCategoria(result);
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }
    public Categoria buscarCategoriaPeloId(Long id){
    String sql = String.format("SELECT * FORM categoria WHERE id = %d", id);
        try {
            ResultSet resultSet = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            if(resultSet.next()){
            return  getCategoria(resultSet);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    private void preparedStatementSet(PreparedStatement preparedStatement, Categoria categoria) throws SQLException {
        preparedStatement.setString(1, categoria.getNome());
        preparedStatement.setString(2, categoria.getDescricao());
        if (categoria.getId() != 0) {
            preparedStatement.setLong(3, categoria.getId());
        }
    }

    private Categoria getCategoria(ResultSet result) throws SQLException{
       Categoria categoria = new Categoria();
       categoria.setId(result.getLong("id"));
       categoria.setNome(result.getString("nome"));
       categoria.setDescricao(result.getString("descricao"));
       return categoria;
    }
public List<Categoria> todasCategorias() {
        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();

            while (result.next()) {
                categorias.add(getCategoria(result));
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return categorias;
    }
    public String deleteCategoriaPeloId(Long id) {
        String sql = String.format("UPDATE categoria SET nome =?, descricao = ? WHERE id= %d", id);
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);

            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Categoria apagado com sucesso." : "Nao foi possivel apagar categoria";
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
            if(e.getMessage().startsWith("Cannot delete or update a parent row")) {
                return "Nao foi possivel apagar - Categoria associado ao produto";
            }
            return String.format("Error: %s", e.getMessage());
        }
    }
}
