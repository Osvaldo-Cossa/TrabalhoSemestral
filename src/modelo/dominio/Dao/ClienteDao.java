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
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Modelo.Cliente;

/**
 *
 * @author Hp ProBook
 */
public class ClienteDao {

    private final ConexaoMysql conexaoMysql;

    public ClienteDao() {
        this.conexaoMysql = new ConexaoMysql();
    }

    public String salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (id, nome, telefone ,morada) VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, cliente);
            int resultado = preparedStatement.executeUpdate();
            if (resultado == 1) {
                return String.format("cliente salvo com sucesso");
            } else {
                return String.format("nao foi Possivel guardar os Dados do cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Erro: %s", e.getMessage());
        }

    }

    public String editar(Cliente cliente) {
        String sql = String.format("UPDATE cliente SET nome =?, telefone=?, morada=? WHERE id=%s", cliente.getId());

        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            preparedStatementSet(preparedStatement, cliente);
            int resultado = preparedStatement.executeUpdate();
            if (resultado == 1) {
                return String.format("cliente Editado com sucesso");
            } else {
                return String.format("nao foi Possivel Editar os Dados do cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Erro: %s", e.getMessage());
        }
    }

    private void preparedStatementSet(PreparedStatement preparedStatement, Cliente cliente) throws SQLException {
        preparedStatement.setString(2, cliente.getNome());
        preparedStatement.setString(3, cliente.getTelefone());
        preparedStatement.setString(4, cliente.getMorada());

        if (cliente.getId() != 0) {
            preparedStatement.setLong(4, cliente.getId());
        }
    }

    private List<Cliente> todosClientes() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> todoscliente = new ArrayList<>();
        try {
            ResultSet resultSet = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                todoscliente.add(getCliente(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(String.format("Erro: %s", e.getMessage()));
        }
        return todoscliente;
    }

    private Cliente getCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getLong("id"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setTelefone(resultSet.getString("telefone"));
        cliente.setMorada(resultSet.getString("morada"));
        return cliente;
    }

    private String deletarClientePeloId(Long id) {
        String sql = String.format("UPDATE cliente SET nome=?, telefone=?,morada=? WHERE id= %d", id);
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareCall(sql);
            int resultado = preparedStatement.executeUpdate();
            if (resultado == 1) {
                return "Cliente Deletado com sucesso";
            } else {
                return "nao foi possivel Deletar o cliente";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Erro: %s", e.getMessage());
        }
    }

    private Cliente buscarClientePeloId(Long id) {
        String sql = String.format("SELECT cliente WHERE id=%d", id);
        try {
            ResultSet resultSet = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                return getCliente(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(String.format("Erro: %s",e.getMessage()));
        }
        return null;
    }

    private Cliente buscarClientePeloNome(String nome) {
        String sql = String.format("SELECT cliente WHERE nome =%s", nome);

        try {
            ResultSet resultSet = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                return getCliente(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Erro: %s", e.getMessage()));
        }
        return null;
    }

}
