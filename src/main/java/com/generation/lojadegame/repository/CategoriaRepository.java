package com.generation.lojadegame.repository;

import com.generation.lojadegame.model.Categoria;
import com.generation.lojadegame.model.Produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	public List<Categoria> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
