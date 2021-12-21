package rpa.model;

public class Product {

	private String nome;
	private String preco;
	private String url;
	
	public Product() {
		
	}
	
	public Product(String nome, String preco, String url) {
		this.nome = nome;
		this.preco = preco;
		this.url = url;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
