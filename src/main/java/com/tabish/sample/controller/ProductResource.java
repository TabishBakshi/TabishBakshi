package com.tabish.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tabish.sample.entity.Product;
import com.tabish.sample.repository.ProductRepository;
import com.tabish.sample.responses.SuccessResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProductResource {

	public static final int PAGE_SIZE = 10;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping(value = "/products")
	public List<Product> getAll(@RequestParam(name = "page") int pageNo) {
		Pageable paging = PageRequest.of(pageNo, PAGE_SIZE);
		Page<Product> pagedResult = productRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Product>();
		}

	}

	@GetMapping(path = "/products/{di}")
	public Product get(@PathVariable("di") int id) {
		return productRepository.findById(id).get();
	}

	@PostMapping(value = "/products")
	public SuccessResponse addProduct(@RequestBody final Product category) {
		SuccessResponse response = new SuccessResponse();

		Product savedProduct = productRepository.save(category);
		if (savedProduct != null) {
			response.setStatus(1);
			response.setMsg("product added");
		} else {
			response.setStatus(0);
			response.setMsg("failed to add product");
		}
		return response;
	}

	@PutMapping(path = "/products/{di}")
	public SuccessResponse update(@PathVariable int id, @RequestBody Product product) {
		SuccessResponse response = new SuccessResponse();
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			if (productRepository.save(product) != null) {
				response.setStatus(1);
				response.setMsg("product updated");
			} else {
				response.setStatus(0);
				response.setMsg("failed to update product");
			}
		} else {
			response.setStatus(0);
			response.setMsg("no category exist with this id");
		}

		return response;
	}

	@DeleteMapping(path = "/products/{di}")
	public SuccessResponse delete(@PathVariable int id) {
		SuccessResponse response = new SuccessResponse();
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			response.setStatus(0);
			response.setMsg("failed to delete product");
		}
		response.setMsg("product deleted");
		response.setStatus(1);

		return response;
	}
}
