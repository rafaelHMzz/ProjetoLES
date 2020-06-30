package les.persistence;

import java.sql.SQLException;

import les.entity.Compra;

public interface IComprasDao {
	
	public void insereCompra(Compra c) throws SQLException;
	
}
