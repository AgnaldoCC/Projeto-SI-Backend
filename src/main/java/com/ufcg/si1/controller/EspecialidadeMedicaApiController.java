package com.ufcg.si1.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.pojo.EspecialidadeMedica;
import com.ufcg.si1.pojo.UnidadeDeSaude;
import com.ufcg.si1.service.EspecialidadeMedicaService;
import com.ufcg.si1.service.UnidadeSaudeService;

import exceptions.EspecialidadeMedicaException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EspecialidadeMedicaApiController {

	@Autowired
	private EspecialidadeMedicaService especialidadeMedicaService;
	@Autowired
	private UnidadeSaudeService unidadeSaudeService;

	/**
	 * Busca as especialidades de uma determinada Unidade de Saude.
	 */
	@RequestMapping(value = "/especialidade/unidades/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultaEspecialidadeporUnidadeSaude(@PathVariable("id") long id) {
		Set<UnidadeDeSaude> especialidadesPorUnidadeDeSaude;
		try {
			especialidadesPorUnidadeDeSaude = especialidadeMedicaService.findEspecialidadeMedicaByUnidadeSaude(id);
		} catch (EspecialidadeMedicaException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<UnidadeDeSaude>>(especialidadesPorUnidadeDeSaude, HttpStatus.OK);
	}

	/**
	 * Inclui uma nova especialidade, caso a mesma não exista na API.
	 */
	@RequestMapping(value = "/especialidade", method = RequestMethod.POST)
	public ResponseEntity<?> incluirEspecialidade(@RequestBody EspecialidadeMedica esp) {
		EspecialidadeMedica especialidadeMedica = esp;
		try {
			especialidadeMedica = especialidadeMedicaService.save(esp);
		} catch (EspecialidadeMedicaException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<EspecialidadeMedica>(especialidadeMedica, HttpStatus.CREATED);
	}

	/**
	 * Consulta uma especialidade com um determinado id.
	 */
	@RequestMapping(value = "/especialidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarEspecialidade(@PathVariable("id") long id) {
		EspecialidadeMedica especialidadeMedica;
		try {
			especialidadeMedica = especialidadeMedicaService.findById(id);
		} catch (EspecialidadeMedicaException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EspecialidadeMedica>(especialidadeMedica, HttpStatus.OK);
	}
	
	/**
	 * Busca quais unidades de saúde tem a especialidade passada.
	 */
	@RequestMapping(value = "/especialidade/unidade/{descricao}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarUnidadePorEspecialidade(@PathVariable("descricao") String descricao) {
		List<UnidadeDeSaude> unidades;
		EspecialidadeMedica esp;
		try {
			esp = especialidadeMedicaService.findByDescricao(descricao);
			unidades = unidadeSaudeService.findByEspecialidade(esp);
		} catch (EspecialidadeMedicaException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UnidadeDeSaude>>(unidades, HttpStatus.OK);
	}

}
