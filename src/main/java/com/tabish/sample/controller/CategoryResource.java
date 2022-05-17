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

import com.tabish.sample.entity.Category;
import com.tabish.sample.repository.CategoryRepository;
import com.tabish.sample.responses.SuccessResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CategoryResource {

	public static final int PAGE_SIZE = 10;

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping(value = "/categories")
	public List<Category> getAll(@RequestParam(name = "page") int pageNo) {
		Pageable paging = PageRequest.of(pageNo, PAGE_SIZE);

		Page<Category> pagedResult = categoryRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Category>();
		}

	}

	@GetMapping(path = "/categories/{di}")
	public Category get(@PathVariable("di") int id) {
		return categoryRepository.findById(id).get();
	}

	@PostMapping(value = "/categories")
	public SuccessResponse addCategory(@RequestBody final Category category) {
		SuccessResponse response = new SuccessResponse();

		Category savedCategory = categoryRepository.save(category);
		if (savedCategory != null) {
			response.setStatus(1);
			response.setMsg("category added");
		} else {
			response.setStatus(0);
			response.setMsg("failed to add category");
		}
		return response;
	}

	@PutMapping(path = "/categories/{di}")
	public SuccessResponse update(@PathVariable int id, @RequestBody Category category) {
		SuccessResponse response = new SuccessResponse();
		if (categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);
			if (categoryRepository.save(category) != null) {
				response.setStatus(1);
				response.setMsg("category updated");
			} else {
				response.setStatus(0);
				response.setMsg("failed to update category");
			}
		} else {
			response.setStatus(0);
			response.setMsg("no category exist with this id");
		}

		return response;
	}

	@DeleteMapping(path = "/categories/{di}")
	public SuccessResponse delete(@PathVariable int id) {
		SuccessResponse response = new SuccessResponse();
		try {
			categoryRepository.deleteById(id);
		} catch (Exception e) {
			response.setStatus(0);
			response.setMsg("failed to delete category");
		}
		response.setMsg("category deleted");
		response.setStatus(1);

		return response;
	}
}
