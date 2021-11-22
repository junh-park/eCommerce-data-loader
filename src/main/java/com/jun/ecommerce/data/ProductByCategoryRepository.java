package com.jun.ecommerce.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.jun.ecommerce.domain.ProductByCategory;

public interface ProductByCategoryRepository extends CassandraRepository<ProductByCategory, String>{
	
}
