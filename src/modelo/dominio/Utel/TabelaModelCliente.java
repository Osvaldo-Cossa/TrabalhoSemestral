/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Utel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.dominio.Modelo.Cliente;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class TabelaModelCliente extends AbstractTableModel {

    private List<Cliente> listaCliente;
    private String[] colunas = {"ID", "NOME", "TELEFONE", "MORADA"};

    public TabelaModelCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    @Override
    public int getRowCount() {
        return  listaCliente.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int coluna, int linha) {
    Cliente cliente = listaCliente.get(linha);
    switch(coluna){
    case 1: return cliente.getId();
    case 2: return cliente.getNome();
    case 3: return cliente.getTelefone();
    case 4: return cliente.getMorada();
        default: return "";
    }
    }
public List<Cliente> getCliente(){
return  listaCliente;
}
    @Override
    public String getColumnName(int coluna){
return colunas[coluna];
}
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; 
    }
}
