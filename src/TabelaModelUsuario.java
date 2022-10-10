
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.dominio.Modelo.Usuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hp ProBook
 */
public class TabelaModelUsuario extends AbstractTableModel {

    private List<Usuario> listaUsuario;
    private String[] colunas = {"id", "Nome do usuario", "nome Usuario", "Perfil", "Telefone"};

    public TabelaModelUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

    @Override
    public int getRowCount() {
        return this.listaUsuario.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int colunas, int linhas) {
        Usuario usuario = listaUsuario.get(linhas);

        switch (colunas) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getNome();
            case 2:
                return usuario.getUsuario();
            case 3:
                return usuario.getPerfil();
            case 4:
                return usuario.getTelefone();
            default:
                return "";
        }
    }

}
