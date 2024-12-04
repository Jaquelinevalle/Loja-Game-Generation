package com.generation.lojadegame.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojadegame.model.Categoria;
import com.generation.lojadegame.repository.CategoriaRepository;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return (ResponseEntity<List<Categoria>>) ResponseEntity.ok(categoriaRepository.findAll());
	}
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
    	return ((Optional<Categoria>) categoriaRepository.findById(id))
    			.map(ResponseEntity::ok)
    			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Categoria>> getByTitulo(@PathVariable String nome) {
    	return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
    }
    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
    	return ResponseEntity.status(HttpStatus.CREATED)
    			.body(categoriaRepository.save(categoria));
    }
    @PutMapping
    public ResponseEntity<Categoria> update(@Valid @RequestBody Categoria categoria) {
    	try {
    		return categoriaRepository.findById(categoria.getId())
    				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
    						.body(categoriaRepository.save(categoria)))
    				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    	}catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
    //delete product
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	Optional<Categoria> temaOptional = categoriaRepository.findById(id);
    	
    	if (temaOptional.isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    	} else {
    		categoriaRepository.deleteById(id);
    	}
    }
}
