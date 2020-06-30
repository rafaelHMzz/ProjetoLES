package les.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import les.entity.Compra;

public class ComprasDao implements IComprasDao{
	
	Connection c = new GenericDao().getConnection();
	
	public List<Compra> buscaCompras() throws SQLException {
		List<Compra> lista = new ArrayList<Compra>();
		String sql = "SELECT * FROM compra";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Compra compra = new Compra();
			compra.setCod_barras(rs.getInt("cod_barras"));
			compra.setNome(rs.getString("nome"));
			compra.setTipo(rs.getString("tipo"));
			compra.setQnt(rs.getInt("quantidade"));
			compra.setPreco(rs.getFloat("preco"));
			compra.setTotal(rs.getFloat("total"));
			lista.add(compra);
		}
		return lista;
	}
	
	@Override
	public void insereCompra(Compra compra) throws SQLException {
		String sql = "{CALL sp_grava_compras(?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setInt(1, compra.getCod_barras());
		cs.setString(2, compra.getNome());
		cs.setString(3, compra.getTipo());
		cs.setInt(4, compra.getQnt());
		cs.setFloat(5, compra.getPreco());
		cs.setFloat(6, compra.getTotal());
		cs.execute();
		cs.close();
	}

}
