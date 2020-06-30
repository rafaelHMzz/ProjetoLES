package les.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import les.entity.Usuario;

public class UsuarioDao implements IUsuarioDao{
	
	Connection c = new GenericDao().getConnection();
	
	@Override
	public Usuario buscaUsuario(String email, String senha) throws SQLException {
		String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, email);
		ps.setString(2, senha);
		ResultSet rs = ps.executeQuery();
		Usuario u = null;
		if(rs.next()) {
			u = new Usuario();
			u.setEmail(rs.getString("email"));
			u.setSenha(rs.getString("senha"));
		} else {
			u = new Usuario();
			u.setEmail("vazio");
		}
		
		return u;
	}

	@Override
	public Usuario buscaAdm(String email, String senha) throws SQLException {
		String sql = "SELECT * FROM adm WHERE email = ? AND senha = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, email);
		ps.setString(2, senha);
		ResultSet rs = ps.executeQuery();
		Usuario u = null;
		if(rs.next()) {
			u = new Usuario();
			u.setEmail(rs.getString("email"));
			u.setSenha(rs.getString("senha"));
		} else {
			u = new Usuario();
			u.setEmail("vazio");
		}
		
		return u;
	}
	
}
