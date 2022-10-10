/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.dominio.Conexoes.ConexaoMysql;
import modelo.dominio.Modelo.Cliente;
import modelo.dominio.Modelo.Produto;
import modelo.dominio.Modelo.Usuario;
import modelo.dominio.Modelo.Venda;
import modelo.dominio.Modelo.VendasItem;

/**
 *
 * @author Hp ProBook
 */
public class VendaDao {
private final ConexaoMysql conexaoMysql;
private ProdutoDao produtoDao;

    public VendaDao() {
        this.conexaoMysql = new ConexaoMysql();
    }
    
     public String salvar(Venda venda) {
        return venda.getId() == 0L ? adicionar(venda) : editar(venda);
    }

    private String adicionar(Venda venda) {
        String sql = "INSERT INTO venda(total_venda, valor_pago, troco, desconto, cliente_id, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            
            preparedStatementSet(preparedStatement, venda);
            
            int resultado = preparedStatement.executeUpdate();
            
            if(resultado == 1) {
                Long idDaVenda = buscarIdDaUltimaVenda();
                System.out.println("ID DA VENDA: " + idDaVenda);
                
                venda.setId(idDaVenda);
                
                venda.getVendasItem()
                        .values()
                        .stream()
                        .forEach(vd -> {
                            vd.setVenda(venda);
                            
                            final int quantidade = vd.getProduto().getQuatidade() - vd.getQuantidade();
                            
                            String mensagem = produtoDao.actualizarQuantidade(vd.getProduto().getId(), 
                                    quantidade, vd.getVenda().getUsuario().getId());
                            
                            System.out.println(mensagem);
                            adicionarVendaItem(vd);
                        });
                
                return "Venda adicionado com sucesso.";
            } else {
                return "Nao foi possivel adiconar venda";
            }
            
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private String editar(Venda venda) {
        String sql = "UPDATE venda SET total_venda = ?, valor_pago = ?, troco = ?, desconto = ?, cliente_id = ?, usuario_id = ?, ultima_actualizacao = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            
            preparedStatementSet(preparedStatement, venda);
            
            int resultado = preparedStatement.executeUpdate();
            
            if(resultado == 1) {           
        venda.getVendasItem().values().stream().forEach(vd -> editarVendaItem(vd));
                
                return "Venda editado com sucesso.";
            } else {
                return "Nao foi possivel editar venda";
            }
            
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
    
    private void preparedStatementSet(PreparedStatement preparedStatement, Venda venda) throws SQLException {
       preparedStatement.setBigDecimal(1, venda.getTotalDaVenda());
       preparedStatement.setBigDecimal(2, venda.getValorPago());
       preparedStatement.setBigDecimal(3, venda.getTroco());
       preparedStatement.setBigDecimal(4, venda.getDescontoTotal());
       preparedStatement.setLong(5, venda.getCliente().getId());
       preparedStatement.setLong(6, venda.getUsuario().getId());
       
       if(venda.getId() != 0L) {
           preparedStatement.setObject(7, LocalDateTime.now());
           preparedStatement.setLong(8, venda.getId());
       }
       
    }
    
    private void adicionarVendaItem(VendasItem vendaItem) {
        String sql = "INSERT INTO venda_item(quantidade, total, desconto, venda_id, produto_id) VALUES(?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            
            preparedStatementSetDetalhes(preparedStatement, vendaItem);
            
            int resultado = preparedStatement.executeUpdate();
            
            String mensagem = resultado == 1 ? "Venda Detalhes adicionado com sucesso." : "Nao foi possivel adiconar Venda detalhes";
            System.out.println(mensagem);
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
    private void editarVendaItem(VendasItem vendaItem) {
        String sql = "UPDATE venda_item SET quantidade  = ?, total  = ?, desconto  = ? WHERE venda_id = ? AND produto_id = ?";
        
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            
            preparedStatementSetDetalhes(preparedStatement, vendaItem);
            
            int resultado = preparedStatement.executeUpdate();
            
            String mensagem = resultado == 1 ? "Venda Detalhes adicionado com sucesso." : "Nao foi possivel adiconar Venda detalhes";
            System.out.println(mensagem);
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
    private void preparedStatementSetDetalhes(PreparedStatement preparedStatement, VendasItem vendaItem) throws SQLException {
       preparedStatement.setInt(1, vendaItem.getQuantidade());
       preparedStatement.setBigDecimal(2, vendaItem.getTotal());
       preparedStatement.setBigDecimal(3, vendaItem.getDesconto());
       preparedStatement.setLong(4, vendaItem.getVenda().getId());
       preparedStatement.setLong(5, vendaItem.getProduto().getId());
    }
    
    
    public List<Venda> todosVendas() {
        String sql = "SELECT * FROM venda v, cliente c, usuario u WHERE v.cliente_id = c.id AND v.usuario_id = u.id ORDER BY v.id";
        List<Venda> vendas = new ArrayList<>();
        
        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            while(result.next()) {
                vendas.add(getVenda(result));
            }
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        
        return vendas;
    }
    
    public List<VendasItem> buscaDetalhesDaVendaPeloId(Long id) {
        String sql = String.format("select * from venda v, venda_item vi, produto p, cliente c, usuario u where v.cliente_id = c.id and v.usuario_id = u.id and vi.venda_id = v.id \n" +
" and vi.produto_id = p.id and v.id = %d", id);
        List<VendasItem> vendaItems = new ArrayList<>();
        
        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            while(result.next()) {
                vendaItems.add(getVendaItem(result));
            }
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        
        return vendaItems;
    }
    
    private VendasItem getVendaItem(ResultSet result) throws SQLException {
        VendasItem vendaItem = new VendasItem();
        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
        Produto produto = new Produto();
        
        cliente.setId(result.getLong("c.id"));
        cliente.setNome(result.getString("c.nome"));
        
        usuario.setId(result.getLong("u.id"));
        usuario.setNome(result.getString("u.nome"));
        
        venda.setId(result.getLong("v.id"));
        venda.setTotalDaVenda(result.getBigDecimal("v.total_venda"));
        venda.setValorPago(result.getBigDecimal("v.valor_pago"));
        venda.setTroco(result.getBigDecimal("v.troco"));
        venda.setDescontoTotal(result.getBigDecimal("v.desconto"));
        venda.setDataHoraCriacao(result.getObject("v.data_hora_criacao", LocalDateTime.class));
        
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        
        produto.setId(result.getLong("p.id"));
        produto.setNome(result.getString("p.nome"));
        produto.setPreco(result.getBigDecimal("p.preco"));
        
        vendaItem.setQuantidade(result.getInt("vi.quantidade"));
        vendaItem.setDesconto(result.getBigDecimal("vi.desconto"));
        vendaItem.setTotal(result.getBigDecimal("vi.total"));
        vendaItem.setVenda(venda);
        vendaItem.setProduto(produto);

        return vendaItem;
    }
    
    private Venda getVenda(ResultSet result) throws SQLException {
        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
        
        cliente.setId(result.getLong("c.id"));
        cliente.setNome(result.getString("c.nome"));
        
        usuario.setId(result.getLong("u.id"));
        usuario.setNome(result.getString("u.nome"));
        
        venda.setId(result.getLong("v.id"));
        venda.setTotalDaVenda(result.getBigDecimal("v.total_venda"));
        venda.setValorPago(result.getBigDecimal("v.valor_pago"));
        venda.setTroco(result.getBigDecimal("v.troco"));
        venda.setDescontoTotal(result.getBigDecimal("v.desconto"));
        venda.setDataHoraCriacao(result.getObject("v.data_hora_criacao", LocalDateTime.class));
        
        venda.setCliente(cliente);
        venda.setUsuario(usuario);

        return venda;
    }
    
    public Usuario buscarUsuarioPeloId(Long id) {
        String sql = String.format("SELECT * FROM usuario WHERE id = %d", id);
        
        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()) {
//                return getUsuario(result);
            }
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        
        return null;
    }
    
    public String deleteUsuarioPeloId(Long id) {
        String sql = String.format("DELETE FROM usuario WHERE id = %d", id);
        
        try {
            PreparedStatement preparedStatement = conexaoMysql.obterConexao().prepareStatement(sql);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario apagado com sucesso" : "Nao foi possivel apagar";
            
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private Long buscarIdDaUltimaVenda() {
        String sql = "SELECT max(id) FROM venda";
        
        try {
            ResultSet result = conexaoMysql.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()) {
                return result.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        
        return null;
    }


}
