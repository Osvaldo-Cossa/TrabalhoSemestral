/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Utel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.dominio.Modelo.Usuario;

/**
 *
 * @author Hp ProBook
 */
public class TabelaModelUsuario extends AbstractTableModel {

    private List<Usuario> listaUsuario;
    private String[] colunas = {"ID", "NOME", "LOGIN", "TELEFONE", "MORADA"};

    public TabelaModelUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    @Override
    public int getRowCount() {
        return  listaUsuario.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int coluna, int linha) {
    Usuario usuario = listaUsuario.get(linha);
    switch(coluna){
    case 1: return usuario.getId();
    case 2: return usuario.getNome();
    case 3: return usuario.getUsuario();
    case 4: return usuario.getTelefone();
    case 5: return usuario.getMorada();
    default: return "";
    }
    }
public List<Usuario> getUsuario(){
return  listaUsuario;
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
