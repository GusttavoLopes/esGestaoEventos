package com.eventoapp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity // Anotando a classe pois essa classe vai ser uma tabela no nosso banco de dados.
public class Evento implements Serializable { // Para o id funcionar certinho colocamos o implements Serializable
	
	private static final long serialVersionUID = 1L; // atributo estático para o id funcionar 
	
	@Id // Anotação para que o codigo (private long codigo;) seja um id
	@GeneratedValue(strategy= GenerationType.AUTO) // Para gerar o id automaticamente
	private long codigo;
	
	@NotEmpty // Quer dizer que o nome não vai ser vazio. Para utilizar teve que colocar uma dependência no pom.xml
	private String nome;
	
	@NotEmpty // Quer dizer que o local não vai ser vazio. Para utilizar teve que colocar uma dependência no pom.xml
	private String local;
	
	@NotEmpty // Quer dizer que o data não vai ser vazio. Para utilizar teve que colocar uma dependência no pom.xml
	private String data;
	
	@NotEmpty // Quer dizer que o horario não vai ser vazio. Para utilizar teve que colocar uma dependência no pom.xml
	private String horario;
	
	@OneToMany // A tabela evento para a tabela convidado é uma relação de 1 para n, ou seja 1 evento com muitos convidados (OneToMany)
	private List<Convidado> convidados;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	
}
