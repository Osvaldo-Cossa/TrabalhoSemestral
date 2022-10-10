/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Utel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.dominio.Modelo.Categoria;

/**
 *
 * @author Hp ProBook
 */
public class TabelaCadastro extends AbstractTableModel{
    private List<Categoria> todosCategorias;
    private final String [] colunas={"id","nome","Descricao"};

    public TabelaCadastro(List<Categoria> categorias) {
        this.todosCategorias = categorias;
    }

    
    @Override
    public int getRowCount() {
    return this.todosCategorias.size();
    }

    @Override
    public int getColumnCount() {
    return  colunas.length;
    }

    @Override
    public Object getValueAt(int colunas, int linha) {
       Categoria categoria = todosCategorias.get(linha);
       switch(colunas){
           case 1: return categoria.getId();
           case 2: return categoria.getNome();
           case 3: return categoria.getDescricao();
           default: return "";
       }
    }
    public List<Categoria> getCadastro(){
    return  todosCategorias;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String getColumnName(int coluna){
    return colunas[coluna];
    }
}
