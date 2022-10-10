/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Conexoes;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Hp ProBook
 */
public interface  Conexoes {
    public Connection obterConexao() throws SQLException;
    public void fecharConexao()throws SQLException;
}
