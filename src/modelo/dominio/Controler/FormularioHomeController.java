/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dominio.Formulario.FormularioDoCliente;
import modelo.dominio.Formulario.FormularioHome;

/**
 *
 * @author Hp ProBook
 */
public class FormularioHomeController implements ActionListener {

    public final FormularioHome formularioHome;

    public FormularioHomeController(FormularioHome formularioHome) {
        this.formularioHome = formularioHome;
    }

    private void mostrarTelaVenda() {
        this.formularioHome.getVenda().pack();
        this.formularioHome.getVenda().setLocationRelativeTo(null);
        this.formularioHome.getVenda().setVisible(true);
    }
    private void mostrarTelaCliente() {
        FormularioDoCliente formularioDoCliente = new FormularioDoCliente();
        formularioHome.getMessagem().setText("CLIENTE");
        formularioDoCliente.pack();
        formularioDoCliente.setLocationRelativeTo(null);
        formularioDoCliente.setVisible(true); 
}

    @Override
    public void actionPerformed(ActionEvent e) {
        String accao = e.getActionCommand().toLowerCase();
        switch (accao) {
            case "vendas":
                mostrarTelaVenda();
                break;
            case "cliente":
                mostrarTelaCliente();
                break;
        }
    }
}
