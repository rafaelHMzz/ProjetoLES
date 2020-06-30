package les.entity;

public class Produto {
	private int cod_barras;
	private String nome;
	private String tipo;
	private int qnt;
	private Float preco;
	
	public int getCod_barras() {
		return cod_barras;
	}
	public void setCod_barras(int cod_barras) {
		this.cod_barras = cod_barras;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
	public Float getPreco() {
		return preco;
	}
	public void setPreco(Float preco) {
		this.preco = preco;
	}
}
