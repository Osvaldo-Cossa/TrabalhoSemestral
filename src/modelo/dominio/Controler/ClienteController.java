/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Dao.ClienteDao;
import modelo.dominio.Dao.negocioException;
import modelo.dominio.Formulario.FormularioDoCliente;
import modelo.dominio.Modelo.Cliente;
import modelo.dominio.Utel.TabelaModelUsuario;

/**
 *
 * @author Hp ProBook
 */
public class ClienteController implements ActionListener{

    private final FormularioDoCliente formularioDoCliente;
    private ConexaoMysql conexaoMysql;
    private Cliente cliente;
    private ClienteDao clienteDao;

    public ClienteController(FormularioDoCliente formularioDoCliente1) {
        this.formularioDoCliente = formularioDoCliente1;
        conexaoMysql = new ConexaoMysql();

    }

    private void salvar() {
        Cliente cliente = pegarValorDoFormulario();
        if (cliente.getId() == 0) {
            String messagem = clienteDao.salvar(cliente);
            messagemNaTela(messagem, Color.GREEN);
        } else {
            String messagem = clienteDao.editar(cliente);
            messagemNaTela(messagem, Color.green);
        }
    }

    private Cliente pegarValorDoFormulario() {
        Long id = Long.valueOf(this.formularioDoCliente.getClienteId().getText());
        String nome = this.formularioDoCliente.getClienteNome().getText();
        String telefone = this.formularioDoCliente.getClienteTelefone().getText();
        String morada = this.formularioDoCliente.getClienteEndereco().getText();
        validarCampos(nome);
        validarCampos(telefone);
        validarCampos(morada);
        
        Cliente clienteTemp = new Cliente(0, nome, 0, morada, telefone, null, null);
        return cliente;
    }

    private void messagemNaTela(String messagem, Color color) {
        this.formularioDoCliente.getClienteErro().setText(messagem);
        this.formularioDoCliente.getClienteErro().setBackground(color);
        this.formularioDoCliente.getClienteErro().setForeground(color);
    }

    
    private void validarCampos(String nome) {
       if(nome.isEmpty()){
    String messagem = "preenche tudos os campos";  
           messagemNaTela(nome, Color.RED);
           throw  new negocioException(messagem);
       }
    }
   private void mostrarTelaUsuario() {
        this.formularioDoCliente.pack();
        this.formularioDoCliente.setLocationRelativeTo(null);
        this.formularioDoCliente.setVisible(true);
    }
    private void ocultarTelaUsuario() {
        this.formularioDoCliente.pack();
        this.formularioDoCliente.setLocationRelativeTo(null);
        this.formularioDoCliente.setVisible(false);
    }
    private void actualizarTabelaCliente(){
       this. = new TabelaModelUsuario(usuarios);
    this.formularioHome.getUsuarioTabelModel().setModel(tabelaModelUsuario);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {

    }

}
