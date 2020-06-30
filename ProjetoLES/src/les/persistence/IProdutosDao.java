package les.persistence;

import java.sql.SQLException;
import java.util.List;

import les.entity.Produto;

public interface IProdutosDao {

	public List<Produto> buscaProdutos() throws SQLException;
	
	public Produto buscaProduto(int cod) throws SQLException;
	
	public void insereProduto(Produto p) throws SQLException;
	
	public void excluiProduto(Produto p) throws SQLException;

	List<Produto> buscaProdutosCompra() throws SQLException;
	
}
