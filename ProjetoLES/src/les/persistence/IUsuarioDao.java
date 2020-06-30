package les.persistence;

import java.sql.SQLException;

import les.entity.Usuario;

public interface IUsuarioDao {
	
	public Usuario buscaUsuario(String email, String senha) throws SQLException;
	public Usuario buscaAdm(String email, String senha) throws SQLException;
	
}
