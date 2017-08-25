package com.ufcg.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ufcg.si1.pojo.Cidadao;

public class PessoaTest {
	
	private ArrayList<Cidadao> pessoas;
	
	public PessoaTest() {
		this.pessoas = new ArrayList<Cidadao>();
	}
	
	@Before
	public void setUp() {
		addPessoasTest();
	}
	
	private void addPessoasTest() {
		this.pessoas.add(new Cidadao("Adriano", "adriano@email.com"));
		this.pessoas.add(new Cidadao("Agnaldo", "agnaldo@email.com"));
		this.pessoas.add(new Cidadao("Rubens", "rubens@email.com"));
		this.pessoas.add(new Cidadao("Ronnyldo", "ronnyldo@email.com"));
	}
	
	@Test
	public void nomePessoaTest() {
		assertEquals("Adriano", this.pessoas.get(0).getNome());
		assertEquals("Agnaldo", this.pessoas.get(1).getNome());
		assertEquals("Rubens", this.pessoas.get(2).getNome());
		assertEquals("Ronnyldo", this.pessoas.get(3).getNome());
	}

	@Test
	public void emailPessoaTest() {
		assertEquals("adriano@email.com", this.pessoas.get(0).getEmail());
		assertEquals("agnaldo@email.com", this.pessoas.get(1).getEmail());
		assertEquals("rubens@email.com", this.pessoas.get(2).getEmail());
		assertEquals("ronnyldo@email.com", this.pessoas.get(3).getEmail());
	}
	
	@Test
	public void nullObjectPessoaTest() {
		this.pessoas.add(new Cidadao());
		assertTrue(this.pessoas.get(4).getNome() == null);
		assertTrue(this.pessoas.get(4).getEmail() == null);
	}
	
	@Test
	public void nullSettersPessoaTest() {
		this.pessoas.add(new Cidadao());
		this.pessoas.get(4).setNome("Nome inserido com setter");
		this.pessoas.get(4).setEmail("Email inserido com setter");
		this.pessoas.get(4).setId(62L);
		assertEquals("Nome inserido com setter", this.pessoas.get(4).getNome());
		assertEquals("Email inserido com setter", this.pessoas.get(4).getEmail());
		assertTrue(62 == this.pessoas.get(4).getId());
	}

}
