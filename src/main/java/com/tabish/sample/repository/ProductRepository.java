package com.tabish.sample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tabish.sample.entity.Product;
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
}
