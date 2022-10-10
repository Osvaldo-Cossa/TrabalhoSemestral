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
import java.util.ArrayList;
import java.util.List;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Conexoes.Conexoes;
import modelo.dominio.Modelo.Perfil;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class UsuarioDao {

    private final ConexaoMysql conexao;

    public UsuarioDao() {
        this.conexao = new ConexaoMysql();
    }

    public String salvar(Usuario usuario) {
        if (usuario.getId() == 0) {
            return adicionar(usuario);
        } else {
            return editar(usuario);
        }
    }

    private String adicionar(Usuario usuario) {
        String sql = "INSERT INTO usuario(nome, usuario, senha, perfil,telefone, estado) VALUES (?, ?, ?, ?, ?,?)";

        Usuario usuarioTemp = buscarUsuarioPeloUsername(usuario.getNome());

        if (usuarioTemp != null) {
            return String.format("Error: usuario %s ja existe no banco de dados", usuario.getNome());
        }

        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

            preparedStatementSet(preparedStatement, usuario);

            int resultado = preparedStatement.executeUpdate();

            return resultado == 1 ? "Usuario adicionado com sucesso." : "Nao foi possivel adiconar usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private String editar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ?, perfil = ?, estado = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

            preparedStatementSet(preparedStatement, usuario);

            int resultado = preparedStatement.executeUpdate();

            return resultado == 1 ? "Usuario editado com sucesso." : "Nao foi possivel editar usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private void preparedStatementSet(PreparedStatement preparedStatement, Usuario usuario) throws SQLException {

        preparedStatement.setString(1, usuario.getNome());
        preparedStatement.setString(2, usuario.getUsuario());
        preparedStatement.setString(3, usuario.getSenha());
        preparedStatement.setString(4, usuario.getTelefone());
        preparedStatement.setString(5, usuario.getPerfil().name());
        preparedStatement.setBoolean(6, usuario.isEstado());

        if (usuario.getId() != 0L) {
            preparedStatement.setLong(6, usuario.getId());
        }

    }

    public List<Usuario> todosUsuarios() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();

            while (result.next()) {
                usuarios.add(getUsuario(result));
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return usuarios;
    }

    private Usuario getUsuario(ResultSet result) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setId(result.getLong("id"));
        usuario.setNome(result.getString("nome"));
        usuario.setUsuario(result.getString("usuario"));
        usuario.setPerfil(Perfil.valueOf(result.getString("perfil")));
        usuario.setSenha(result.getString("senha"));
        usuario.setTelefone(result.getString("telefone"));
        usuario.setEstado(result.getBoolean("estado"));
        usuario.setDataHoraDecriacao(result.getObject("data_hora_criacao", LocalDateTime.class));
        usuario.setUltimoLogin(result.getObject("ultimo_login", LocalDateTime.class));

        return usuario;
    }

    public Usuario buscarUsuarioPeloId(Long id) {
        String sql = String.format("SELECT * FROM usuario WHERE id = %d", id);

        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();

            if (result.next()) {
                return getUsuario(result);
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return null;
    }

    public Usuario buscarUsuarioPeloUsername(String username) {
        String sql = "SELECT * FROM usuario WHERE usuario = ?";

        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return getUsuario(result);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return null;
    }

    public String deleteUsuarioPeloId(Long id) {
        String sql = String.format("DELETE FROM usuario WHERE id = %d", id);

        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

            int resultado = preparedStatement.executeUpdate();

            return resultado == 1 ? "Usuario apagado com sucesso" : "Nao foi possivel apagar";

        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    public void actualizarUltimoLogin(Usuario usuario) {
        String sql = "UPDATE usuario SET ultimo_login = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

            preparedStatement.setString(1, LocalDateTime.now().toString());
            preparedStatement.setLong(2, usuario.getId());

            int resultado = preparedStatement.executeUpdate();

            System.out.println(String.format("Actualizacao do ultimo login: %s",
                    resultado == 1 ? "Com sucesso!!!" : "Sem Sucesso."));
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }

}
