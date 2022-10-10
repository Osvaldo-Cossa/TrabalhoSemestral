/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dominio.Dao.AutenticaoaDoUsuario;
import modelo.dominio.Dao.UsuarioDao;
import modelo.dominio.Dao.negocioException;

import modelo.dominio.Formulario.FormularioUsuario;
import modelo.dominio.Modelo.Perfil;

import modelo.dominio.Modelo.Usuario;
import modelo.dominio.Utel.TabelaModelUsuario;

/**
 *
 * @author Hp ProBook
 */
public class UsuarioController implements ActionListener {

    private final FormularioUsuario formularioUsuario;

    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private AutenticaoaDoUsuario autenticaoaDoUsuario;
    private TabelaModelUsuario tabelaModelUsuario;

    



    public UsuarioController(FormularioUsuario iniFormulario) {
        this.formularioUsuario = iniFormulario;
        //this.formularioHomeUsuario = formularioHomeUsuario;
        this.autenticaoaDoUsuario = new AutenticaoaDoUsuario();
        this.usuarioDao = new UsuarioDao();
        iniciarCombo();
       // actualizarTabelaUsuario(usuarioDao.todosUsuarios());
       }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accao = e.getActionCommand().toLowerCase();

        switch (accao) {
            case "adicionar":
                adcionar();
                break;
            case "editar":
                editar();
                break;
            case "salvar":
                salvar();
                break;

        }
    }
    private void actualizarTabelaUsuario(List<Usuario> usuarios){
    this.tabelaModelUsuario = new TabelaModelUsuario(usuarios);
    this.formularioHomeUsuario.getUsuarioTabelModel().setModel(tabelaModelUsuario);
    }

    public Usuario usuarioLogado() {
        Long idUsuario = Long.valueOf(this.formularioUsuario.getIdUsuario().getText());
        return usuarioDao.buscarUsuarioPeloId(idUsuario);
    }

    private void adcionar() {
        Usuario usuarioLogado = usuarioLogado();

        if (autenticaoaDoUsuario.temPremissao(usuarioLogado)) {
            mostrarTelaUsuario();
        }
    }

    private void editar() {
        Usuario usuarioLogado = usuarioLogado();

        if (autenticaoaDoUsuario.temPremissao(usuarioLogado)) {
            if (this.usuario != null) {

                String idString = this.formularioUsuario.getIdUsuario().getText();
                String nome = this.formularioUsuario.getNomeUsuario().getText();
                String login = this.formularioUsuario.getLogiinUsuario().getText();
                String senha = this.formularioUsuario.getSenhaUsuario().getText();
                String Telefone = this.formularioUsuario.getTelefoneUsuario().getText();
                String perfil = this.formularioUsuario.getPerfilUsuario().getSelectedItem().toString();
                Long id = Long.valueOf(idString);

                validacaoDeCampos(nome, "nome");
                validacaoDeCampos(login, "Usuario");
                validacaoDeCampos(senha, "senha");
                validacaoDeCampos(Telefone, "Telefone");
                validacaoDoPerfil();

                Usuario usuarioTemp = new Usuario(id, senha, login, Perfil.valueOf(perfil), true, null, null, Telefone, nome);
                if (this.formularioUsuario.getInActivoUsuario().isSelected()) {
                    usuarioTemp.mudarEstado();
                    String messagem = usuarioDao.salvar(usuarioTemp);
                    if (messagem.startsWith("usuario")) {
                        messagemNaTela(messagem, Color.GREEN);
                        limparCampos();
                    } else {
                        messagemNaTela(messagem, Color.RED);
                    }
                }

                mostrarTelaUsuario();
                String messagem = usuarioDao.salvar(usuarioTemp);
                if (messagem.startsWith("usuario")) {
                    messagemNaTela(messagem, Color.GREEN);
                    limparCampos();
                } else {
                    messagemNaTela(messagem, Color.RED);
                }
            } else {
                JOptionPane.showMessageDialog(null, "selecione um usuario na tabela");
            }

        }
    }

    private void preecherOsValoresDoFormularioUsuario() {
        this.formularioUsuario.getIdUsuario().setText(String.valueOf(this.usuario.getId()));
        this.formularioUsuario.getNomeUsuario().setText(this.usuario.getNome());
        this.formularioUsuario.getLogiinUsuario().setText(this.usuario.getUsuario());
        this.formularioUsuario.getSenhaUsuario().setText("");
        this.formularioUsuario.getTelefoneUsuario().setText(this.usuario.getTelefone());
        this.formularioUsuario.getPerfilUsuario().setSelectedItem(this.usuario.getPerfil());
        if (usuario.isEstado()) {
            this.formularioUsuario.getActivoUsuario().setSelected(true);
        } else {
            this.formularioUsuario.getInActivoUsuario().setSelected(true);
        }
    }

  
    private void salvar() {
       
        String nome = this.formularioUsuario.getNomeUsuario().getText();
        String login = this.formularioUsuario.getLogiinUsuario().getText();
        String senha = this.formularioUsuario.getSenhaUsuario().getText();
        String Telefone = this.formularioUsuario.getTelefoneUsuario().getText();
        String perfil = this.formularioUsuario.getPerfilUsuario().getSelectedItem().toString();
        

        validacaoDeCampos(nome, "nome");
        validacaoDeCampos(login, "Usuario");
        validacaoDeCampos(senha, "senha");
        validacaoDeCampos(Telefone, "Telefone");
        validacaoDoPerfil();

        Usuario usuarioTemp = new Usuario(0, senha, login, Perfil.valueOf(perfil), true, null, null, Telefone, nome);
        if (this.formularioUsuario.getInActivoUsuario().isSelected()) {
            usuarioTemp.mudarEstado();
        }

        String messagem = usuarioDao.salvar(usuarioTemp);
        if (messagem.startsWith("Usuario")) {
            messagemNaTela(messagem, Color.GREEN);
            limparCampos();
        } else {
            messagemNaTela(messagem, Color.RED);
        }
    }

    private void validacaoDeCampos(String campo, String nomedaVariavel) {
        if (campo.isEmpty()) {
            String messagem = String.format("Preencher todos os campos ", nomedaVariavel);
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }

    }

    private void messagemNaTela(String messagem, Color color) {
        this.formularioUsuario.getMessagemErroUsuario().setText(messagem);
        this.formularioUsuario.getMessagemErroUsuario().setBackground(color);
        this.formularioUsuario.getMessagemErroUsuario().setForeground(color);
    }

    private void mostrarTelaUsuario() {
        this.formularioUsuario.pack();
        this.formularioUsuario.setLocationRelativeTo(null);
        this.formularioUsuario.setVisible(true);
    }
    private void ocultarTelaUsuario() {
        this.formularioUsuario.pack();
        this.formularioUsuario.setLocationRelativeTo(null);
        this.formularioUsuario.setVisible(false);
    }

    private void validacaoDoPerfil() {
        if (this.formularioUsuario.getPerfilUsuario().getSelectedIndex() == 0) {
            String messagem = "deve prencher perfil";
            messagemNaTela(messagem, Color.RED);
            throw new negocioException(messagem);
        }
    }

    private void limparCampos() {
        this.formularioUsuario.getIdUsuario().setText("0");
       this.formularioUsuario.getNomeUsuario().setText("");
       this.formularioUsuario.getLogiinUsuario().setText("");
       this.formularioUsuario.getSenhaUsuario().setText("");
       this.formularioUsuario.getTelefoneUsuario().setText("");
       this.formularioUsuario.getPerfilUsuario().setSelectedItem(0);
       
    }

    private void iniciarCombo() {
        this.formularioUsuario.getPerfilUsuario().removeAllItems();
        this.formularioUsuario.getPerfilUsuario().addItem("seleciona um Perfil");
        for (Perfil perfil : Perfil.values()) {
            this.formularioUsuario.getPerfilUsuario().addItem(perfil);
        }
    }

}
