/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.dominio.Dao.AutenticaoaDoUsuario;
import modelo.dominio.Formulario.TelaLogin;
import modelo.dominio.Modelo.LoginDTO;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class LoginController implements ActionListener{
    private final TelaLogin loginForm;
    private final AutenticaoaDoUsuario autenticaoaDoUsuario;

    public LoginController(TelaLogin login) {
        this.loginForm = login;
        this.autenticaoaDoUsuario = new AutenticaoaDoUsuario();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String accao = ae.getActionCommand().toLowerCase();
        switch(accao){
            case "entrar": login(); break;
            case "cancelar": cancelar(); break;
        }
    }
    
    public void login(){
    String userName = loginForm.getTextoUsuarioLogin().getText();
    String userSenha = loginForm.getTextoUsuarioSenha().getText();
    
    if(userName.equals("") || userSenha.equals("")){
      loginForm.getLabolUsuario().setText("Username e senha devem ser preenchido");
      return;
         }
    
        LoginDTO login = new LoginDTO();
        Usuario usuario = autenticaoaDoUsuario.login(login);
        
        if(usuario !=null){
        JOptionPane.showMessageDialog(null, "bem-vindo");
        }else{loginForm.getLabolUsuario().setText("Username e senha incorrecta");}
    }
    private void cancelar(){
    int confirmar = JOptionPane.showConfirmDialog(null, "tens a certeza que deseja sair?"," sair Login",JOptionPane.YES_NO_OPTION);
    if(confirmar==JOptionPane.YES_OPTION)
        System.exit(0);
      
    }
    private void limpar(){
    loginForm.getTextoUsuarioLogin().setText("");
    loginForm.getTextoUsuarioSenha().setText("");
    
    }
}
