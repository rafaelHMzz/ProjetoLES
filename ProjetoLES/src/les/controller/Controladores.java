package les.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import les.entity.Compra;
import les.entity.Produto;
import les.entity.ProdutosWrapper;
import les.entity.Usuario;
import les.persistence.ComprasDao;
import les.persistence.ProdutosDao;
import les.persistence.UsuarioDao;

@Controller
public class Controladores {
	
	@RequestMapping("/")
	public ModelAndView telaLogin() {
		String erro = "";
		ModelAndView mv = new ModelAndView("Login");
		mv.addObject("erro", erro);
		return mv;
	}
	
	@RequestMapping("inicioAdm")
	public ModelAndView inicioAdm() throws SQLException {
		ProdutosDao pd = new ProdutosDao();
		ModelAndView mv = new ModelAndView("InicioAdm");
		List<Produto> lista = pd.buscaProdutos();
		mv.addObject("listaProdutos", lista);
		return mv;
	}
	
	@RequestMapping("/validaLogin")
	public ModelAndView validaLogin(@RequestParam(value="email") String email, @RequestParam(value="senha") String senha) throws SQLException {
		ProdutosDao pd = new ProdutosDao();
		ModelAndView mv = null;
		UsuarioDao ud = new UsuarioDao();
		Usuario adm = ud.buscaAdm(email, senha);
		Usuario user = ud.buscaUsuario(email, senha);
		if("vazio".equals(adm.getEmail()) & "vazio".equals(user.getEmail())) {
			mv = new ModelAndView("Login");
			String erro = "Usuário ou Senha inválidos";
			mv.addObject("erro", erro);
		} else if(!"vazio".equals(adm.getEmail())) {
			mv = new ModelAndView("InicioAdm");
			List<Produto> lista = pd.buscaProdutos();
			mv.addObject("listaProdutos", lista);
		} else if(!"vazio".equals(user.getEmail())) {
			mv = new ModelAndView("InicioUser");
			List<Produto> lista = pd.buscaProdutos();
			mv.addObject("listaProdutos", lista);
		}
		return mv;
	}
	
	@RequestMapping("/telaCadastro1")
	public String getCadastro1() {
		return "Cadastro1";
	}
	
	@RequestMapping("/telaCadastro2")
	public ModelAndView getCadastro2(@RequestParam(value="tipo")String tipo) {
		ModelAndView mv = new ModelAndView("Cadastro2");
		mv.addObject("tipo", tipo);
		return mv;
	}
	
	@RequestMapping("/telaCadastro3")
	public ModelAndView getCadastro3(@RequestParam(value="cod") int cod, @RequestParam(value="tipo") String tipo) throws SQLException {
		ModelAndView mv = new ModelAndView("Cadastro3");
		Produto p = new Produto();
		ProdutosDao pd = new ProdutosDao();
		p = pd.buscaProduto(cod);
		if(p.getNome() == null) {
			p.setTipo(tipo);
			p.setCod_barras(cod);
			p.setQnt(0);
		}
		mv.addObject("produto", p);
		return mv;
	}
	
	@RequestMapping("/salvar")
	public String salvarProduto(@ModelAttribute(value="produto") Produto p, @RequestParam(value="cmd") String cmd) throws SQLException {
		ProdutosDao pd = new ProdutosDao();
		if("excluir".equals(cmd)) {
			pd.excluiProduto(p);
		} else if("salvar".equals(cmd)) {
			pd.insereProduto(p);
		}
		return "Cadastro1";
	}
	
	@RequestMapping("/controleEstoque")
	public ModelAndView controleEstoque() throws SQLException {
		ModelAndView mv = new ModelAndView("Estoque");
		ProdutosDao pd = new ProdutosDao();
		List<Produto> lista = pd.buscaProdutos();
		ProdutosWrapper pw = new ProdutosWrapper();
		pw.setListaProdutos(lista);
		mv.addObject("listaEstoque", pw);
		return mv;
	}
	
	@RequestMapping("/salvarEstoque")
	public ModelAndView salvarEstoque(@ModelAttribute(value="listaEstoque") ProdutosWrapper pw) throws SQLException {
		ModelAndView mv = new ModelAndView("InicioAdm");
		ProdutosDao pd = new ProdutosDao();
		List<Produto> lista = pw.getListaProdutos();
		for(Produto p : lista) {
			pd.insereProduto(p);
		}
		mv.addObject("listaProdutos", lista);
		return mv;
	}
	
	@RequestMapping("/sair")
	public String sair() {
		return "Login";
	}
	
	@RequestMapping("/inicioUser")
	public String inicioUser() throws SQLException {
		return "InicioUser";
	}
	
	@RequestMapping("/telaCompra")
	public ModelAndView telaCompra() throws SQLException {
		ModelAndView mv = new ModelAndView("Compra");
		ProdutosDao pd = new ProdutosDao();
		List<Produto> lista = pd.buscaProdutosCompra();
		ProdutosWrapper pw = new ProdutosWrapper();
		pw.setListaProdutos(lista);
		mv.addObject("listaCompra", pw);
		return mv;
	}
	
	@RequestMapping("/realizaCompra")
	public ModelAndView salvaCompra(@ModelAttribute(value="listaCompra") ProdutosWrapper pw) throws SQLException {
		ModelAndView mv = new ModelAndView("TabelaCompra");
		ProdutosDao pd = new ProdutosDao();
		ComprasDao cd = new ComprasDao();
		List<Produto> lista = pw.getListaProdutos();
		for(Produto p : lista) {
			if(p.getQnt() != 0) {
				Produto p2 = pd.buscaProduto(p.getCod_barras());
				Float total = p.getPreco() * p.getQnt();
				Compra c = new Compra();
				c.setCod_barras(p.getCod_barras());
				c.setNome(p.getNome());
				c.setTipo(p.getTipo());
				c.setQnt(p.getQnt());
				c.setPreco(p.getPreco());
				c.setTotal(total);
				cd.insereCompra(c);
				p.setQnt(p2.getQnt() - c.getQnt());
				pd.insereProduto(p);
			}
		}
		List<Compra> lista2 = cd.buscaCompras();
		mv.addObject("listaCompras", lista2);
		return mv;
	}
	
	@RequestMapping("/tabelaCompras")
	public ModelAndView tabelaCompras() throws SQLException {
		ModelAndView mv = new ModelAndView("TabelaCompra");
		ComprasDao cd = new ComprasDao();
		List<Compra> lista2 = cd.buscaCompras();
		mv.addObject("listaCompras", lista2);
		return mv;
	}
	
}
