package les.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import les.entity.Produto;

public class ProdutosDao implements IProdutosDao{
	
	Connection c = new GenericDao().getConnection();
	
	@Override
	public List<Produto> buscaProdutos() throws SQLException {
		List<Produto> lista = new ArrayList<Produto>();
		String sql = "SELECT * FROM produtos";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setCod_barras(rs.getInt("cod_barras"));
			p.setNome(rs.getString("nome"));
			p.setTipo(rs.getString("tipo"));
			p.setQnt(rs.getInt("quantidade"));
			p.setPreco(rs.getFloat("preco"));
			System.out.println(p.getCod_barras());
			lista.add(p);
		}
		return lista;
	}
	
	@Override
	public List<Produto> buscaProdutosCompra() throws SQLException {
		List<Produto> lista = new ArrayList<Produto>();
		String sql = "SELECT * FROM produtos WHERE quantidade > ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, 0);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setCod_barras(rs.getInt("cod_barras"));
			p.setNome(rs.getString("nome"));
			p.setTipo(rs.getString("tipo"));
			p.setQnt(rs.getInt("quantidade"));
			p.setPreco(rs.getFloat("preco"));
			System.out.println(p.getCod_barras());
			lista.add(p);
		}
		return lista;
	}

	@Override
	public Produto buscaProduto(int cod) throws SQLException {
		Produto p = new Produto();
		String sql = "SELECT * FROM produtos WHERE cod_barras = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			p.setCod_barras(rs.getInt("cod_barras"));
			p.setNome(rs.getString("nome"));
			p.setTipo(rs.getString("tipo"));
			p.setQnt(rs.getInt("quantidade"));
			p.setPreco(rs.getFloat("preco"));
		} 
		return p;
	}

	@Override
	public void insereProduto(Produto p) throws SQLException {
		String sql = "{CALL sp_grava_produtos(?, ?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setInt(1, p.getCod_barras());
		cs.setString(2, p.getNome());
		cs.setString(3, p.getTipo());
		cs.setInt(4, p.getQnt());
		cs.setFloat(5, p.getPreco());
		cs.execute();
		cs.close();
	}

	@Override
	public void excluiProduto(Produto p) throws SQLException {
		String sql = "DELETE FROM produtos WHERE cod_barras = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCod_barras());
		ps.execute();
		ps.close();
	}

}
