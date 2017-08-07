package com.ufcg.si1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import exceptions.ObjetoInvalidoException;

/**
 * Queixa possui identificador único que deve ser gerado de forma automática.
 * 
 * A queixa será registrada por um cidadão que deve identificar-se através do
 * objeto Pessoa. Informar endereço da queixa. Informar detalhes através da
 * descrição.
 * 
 * Campo de situação informará ao sistema se ela está aberta, fechada ou em
 * andamento através do enumaration QueixaStatus.
 * 
 * As queixas só devem ser fechadas pelos administrados, que devem comentar
 * sempre que a queixa for fechada.
 */
@Entity
@Table(name = "TB_QUEIXA")
public class Queixa implements Serializable {

	private static final long serialVersionUID = 8981354144693127107L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull
	@Column(name = "comentario")
	private String comentario;
	
	@NotNull
	@Column(name = "solicitante")
	private Pessoa solicitante;
	
	@NotNull
	@Column(name = "endereco")
	private Endereco endereco;
	
	@NotNull
	@Column(name = "situacao")
	private QueixaStatus situacao;

	public Queixa() {
		this.situacao = QueixaStatus.ABERTA;
	}

	public Queixa(String descricao, Pessoa solicitante, Endereco endereco) {
		this.descricao = descricao;
		this.situacao = QueixaStatus.ABERTA;
		this.solicitante = solicitante;
		this.endereco = endereco;
	}

	public void abrir() throws ObjetoInvalidoException {
		if (this.situacao == QueixaStatus.EM_ANDAMENTO || this.situacao == QueixaStatus.FECHADA)
			this.situacao = QueixaStatus.ABERTA;
		else
			throw new ObjetoInvalidoException("Queixa já está aberta");
	}

	public void fechar(String comentario) throws ObjetoInvalidoException {
		if (this.situacao == QueixaStatus.EM_ANDAMENTO || this.situacao == QueixaStatus.ABERTA) {
			this.situacao = QueixaStatus.FECHADA;
			this.comentario = comentario;
		} else {
			throw new ObjetoInvalidoException("Queixa já está fechada");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Pessoa getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Pessoa solicitante) {
		this.solicitante = solicitante;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public QueixaStatus getSituacao() {
		return situacao;
	}

	public void setSituacao(QueixaStatus situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {

			return true;
		} else if (obj == null || getClass() != obj.getClass()) {

			return false;
		}

		Queixa other = (Queixa) obj;

		return (this.id == other.id);
	}

}
