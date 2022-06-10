package org.acme;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ProductService {

	ProductRepository productRepository;

	ModelMapper modelMapper;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.modelMapper = new ModelMapper();
	}
	
	public List<ProductSummaryDTO> findAll() {
		List<Product> products = productRepository.listAll();
		
		return products.stream()
				.map(this::convertToSummaryDto)
				.collect(Collectors.toList());
	}

	public ProductDTO findOne(long id) {
		Product product = productRepository.findById(id);

		return convertToDto(product);
	}

	private ProductDTO convertToDto(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}

	private ProductSummaryDTO convertToSummaryDto(Product product) {
		return modelMapper.map(product, ProductSummaryDTO.class);
	}

	@Transactional
	public void init(@Observes StartupEvent event) {
		Product p = new Product();
		p.setName("Laptop");
		p.setDetails("16Gb RAM, 256 GB SSD");
		p.setProvider("MyLaptops, Inc");
		productRepository.persist(p);
	}
	
}
