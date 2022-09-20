package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25;
	

	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingId); // vai excluir o produto de id 1

		Optional<Product> result = repository.findById(existingId); // vai tentar buscar o produto de id 1
		Assertions.assertFalse(result.isPresent()); // e verifica que dentro do result não possui nenhum objeto.

	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		Assertions.assertNotNull(product.getId()); // verifica que o id não é nulo
		
		Assertions.assertEquals(countTotalProducts +1 , product.getId()); // verifica que o id é o 26
	}
}
