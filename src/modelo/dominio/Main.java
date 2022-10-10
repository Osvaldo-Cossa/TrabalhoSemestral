/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio;

import java.sql.SQLException;
import java.time.LocalDateTime;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Conexoes.Conexoes;
import modelo.dominio.Dao.UsuarioDao;
import modelo.dominio.Modelo.Perfil;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class Main {
    public static void main(String[] args) throws SQLException {
       // Usuario usuario = new Usuario(0, "pedro samuel", "123", "samuel", Perfil.Admin, null, null);
        UsuarioDao usuarioDao = new UsuarioDao();
    //    String messagem = ""+usuarioDao.buscarUsuarioPeloNome("samuel");
     //   System.out.println(messagem);
    }
}
