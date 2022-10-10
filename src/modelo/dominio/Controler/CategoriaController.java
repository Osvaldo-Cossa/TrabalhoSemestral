/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Dao.CategoriasDao;
import modelo.dominio.Dao.negocioException;
import modelo.dominio.Formulario.FormularioHome;
import modelo.dominio.Formulario.RegistroDeCadastro;
import modelo.dominio.Modelo.Categoria;
import modelo.dominio.Utel.TabelaCadastro;

/**
 *
 * @author Hp ProBook
 */
public class CategoriaController implements ActionListener {

    private ConexaoMysql conexaoMysql;
    private final RegistroDeCadastro formulariocategoria;
    private CategoriasDao categoriasDao;
    private Categoria categoria;
    private List<Categoria> todasCategorias;
    private final FormularioHome formularioHome;

    public CategoriaController() {
        this.formulariocategoria = null;
        this.formularioHome = null;
    }
    

    public CategoriaController(FormularioHome formularioHome1, RegistroDeCadastro registroDeCadastro) {
       this.conexaoMysql = new ConexaoMysql();
        this.categoriasDao = new CategoriasDao();
        this.formularioHome = formularioHome1;
    this.formulariocategoria = registroDeCadastro;
    }

    
    

    private void salvar() {
        Categoria categoriaTemp = pegarDadosDoFormulario();
        String messagem = categoriasDao.salvar(categoriaTemp);
        if (messagem.startsWith("Categoria")) {
            mensagemNaTela(messagem, Color.GREEN);
            actualizarTabelaCagastro();
            limpatCampos();
        } else {
            mensagemNaTela(messagem, Color.red);
        }
    }

    private void editar() {
        Categoria categoriaTemp = pegarDadosDoFormulario();

        String messagem = categoriasDao.salvar(categoria);

        if (messagem.startsWith("Categoria")) {
            mensagemNaTela(messagem, Color.GREEN);
            actualizarTabelaCagastro();
            limpatCampos();
        } else {
            mensagemNaTela(messagem, Color.RED);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Categoria pegarDadosDoFormulario() {
        Categoria categoria = new Categoria();

        long idString = Long.valueOf(this.formulariocategoria.getCategoriaId().getText());
        categoria.setNome(this.formulariocategoria.getCategoriaNome().getText());
        categoria.setDescricao(this.formulariocategoria.getCategoriaDescricao().getText());
        if (idString > 0) {
            categoria.setId(idString);
        } else {
            categoria.setId(0);
        }
        return categoria;
    }

    private void mensagemNaTela(String messagem, Color color) {
        this.formulariocategoria.getCategoriaMessageErro().setText(messagem);
        this.formulariocategoria.getCategoriaMessageErro().setBackground(color);
        this.formulariocategoria.getCategoriaMessageErro().setForeground(color);
    }

    private void actualizarTabelaCagastro() {
        this.todasCategorias = categoriasDao.todasCategorias();
        TabelaCadastro tabelaCadastro = new TabelaCadastro(this.todasCategorias);
        this.formularioHome.getTabelaCategoria().setModel(tabelaCadastro);
    }

    private void limpatCampos() {
        this.formulariocategoria.getCategoriaId().setText("0");
        this.formulariocategoria.getCategoriaNome().setText("");
        this.formulariocategoria.getCategoriaDescricao().setText("");
    }

    private void deletarCadastro() {
        if (categoria != null) {
            String messagem = categoriasDao.deleteCategoriaPeloId(this.categoria.getId());
            if (messagem.startsWith("Categoria")) {
                mensagemNaTela(messagem, Color.GREEN);
                limpatCampos();
            } else {
                mensagemNaTela(messagem, Color.RED);
            }
        }
    }

}
