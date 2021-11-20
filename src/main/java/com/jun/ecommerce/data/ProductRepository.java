package com.jun.ecommerce.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.jun.ecommerce.domain.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID>{

}
