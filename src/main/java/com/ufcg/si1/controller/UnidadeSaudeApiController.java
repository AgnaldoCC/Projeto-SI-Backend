package com.ufcg.si1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.UnidadeSaudeService;
import com.ufcg.si1.service.UnidadeSaudeServiceImpl;
import com.ufcg.si1.util.CustomErrorType;
import com.ufcg.si1.util.ObjWrapper;

import br.edu.ufcg.Hospital;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoJaExistenteException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UnidadeSaudeApiController {

	@Autowired
	private UnidadeSaudeService unidadeSaudeService;

	/**
	 * Busca todas as unidades de saude.
	 */
	@RequestMapping(value = "/unidade", method = RequestMethod.GET)
	public ResponseEntity<List<UnidadeDeSaude>> getAllUnidades() {
		List<UnidadeDeSaude> unidades = unidadeSaudeService.findAll();
		HttpStatus status = HttpStatus.NOT_FOUND;
		if (!unidades.isEmpty())
			status = HttpStatus.OK;
		return new ResponseEntity<>(unidades, status);
	}

	/**
	 * Salva uma nova Unidade de Saude.
	 */
	@RequestMapping(value = "/unidade", method = RequestMethod.POST)
	public ResponseEntity<UnidadeDeSaude> incluirUnidadeSaude(@RequestBody UnidadeDeSaude unidadeDeSaude) {
		UnidadeDeSaude unidadeDeSaudeBD = unidadeSaudeService.save(unidadeDeSaude);
		HttpStatus status = HttpStatus.CONFLICT;
		if (unidadeDeSaudeBD != null)
			status = HttpStatus.CREATED;
		return new ResponseEntity<UnidadeDeSaude>(unidadeDeSaudeBD, status);
	}

	/**
	 * Consulta uma unidade de saude com o id passado.
	 */
	@RequestMapping(value = "/unidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaude(@PathVariable("id") long id) throws ObjetoInexistenteException {

		UnidadeDeSaude us;
		try {
			us = unidadeSaudeService.findById(id);
		} catch (ObjetoInexistenteException ine) {
			return new ResponseEntity(new CustomErrorType("Unidade with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(us, HttpStatus.OK);
	}

	/**
	 * Busca uma unidade de saude pelo bairro.
	 */
	@RequestMapping(value = "/unidade/busca", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {

		List<UnidadeDeSaude> us;

		try {
			us = unidadeSaudeService.getByBairro(bairro);
		} catch (ObjetoInexistenteException exp) {
			return new ResponseEntity(new CustomErrorType("Unidade with bairro " + bairro + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<UnidadeDeSaude>>(us, HttpStatus.OK);
	}

	/**
	 * Metodo para calcular a media medico de pacientes por dia, que eh a razao entre
	 * o numero de atendentes da unidade pela taxa diaria de atendimentos.
	 */
	@RequestMapping(value = "/unidade/media/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> calcularMediaMedicoPacienteDia(@PathVariable("id") long id) {

		UnidadeDeSaude unidade;
		
		try {
			unidade = unidadeSaudeService.findById(id);
		}catch(ObjetoInexistenteException exp) {
			return new ResponseEntity<ObjWrapper<Double>>(HttpStatus.NOT_FOUND);
		}
		
		double c = (double) unidade.getAtendentes() / unidade.getTaxaDiariaAtendimento();
		return new ResponseEntity<ObjWrapper<Double>>(new ObjWrapper<Double>(new Double(c)), HttpStatus.OK);
	}

}
