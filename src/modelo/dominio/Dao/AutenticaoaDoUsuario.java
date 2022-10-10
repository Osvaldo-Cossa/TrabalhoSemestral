/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Dao;

import javax.swing.JOptionPane;
import modelo.dominio.Conexoes.Conexoes;
import modelo.dominio.Modelo.LoginDTO;
import modelo.dominio.Modelo.Perfil;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class AutenticaoaDoUsuario {

    private final UsuarioDao usuarioDao;

    public AutenticaoaDoUsuario() {
        this.usuarioDao = new UsuarioDao();
    }
    public boolean temPremissao(Usuario pusuario){
        try {
            premissao(pusuario);
        return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Usuario sem Premissao", 0);
            return false;
        }
       
    }
    
    public void premissao(Usuario pUsuario){
    if(!Perfil.Admin.equals(pUsuario.getPerfil())){
    throw  new negocioException("nao tem premissao para realizar essa accao");
    }
    }
    public Usuario login(LoginDTO loginDTO) {
        Usuario usuario = usuarioDao.buscarUsuarioPeloUsername(loginDTO.getUsuario());
        if (usuario == null || !usuario.isEstado()) {
            return null;
        }
        if (usuario.isEstado() && ValidarSenha(usuario.getSenha(), loginDTO.getSenha())) {
            return usuario;
        }
        return null;
    }

    private boolean ValidarSenha(String senhaUsuario, String senhaLogin) {
        return senhaLogin.equals(senhaLogin);
    }

}
