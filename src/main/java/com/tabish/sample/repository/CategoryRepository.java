package com.tabish.sample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tabish.sample.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
