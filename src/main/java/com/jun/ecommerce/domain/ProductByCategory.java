package com.jun.ecommerce.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import lombok.Data;

@Data
@Table(value = "product_by_category")
public class ProductByCategory {

	@PrimaryKeyColumn(name = "product_category", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private String category;
	
	@CassandraType(type = Name.UUID)
	@PrimaryKeyColumn(name = "product_id",type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private UUID id;
	
	@Column("product_name")
	@CassandraType(type = Name.TEXT)
	private String name;
	
	@Column("manufacturer")
	@CassandraType(type = Name.TEXT)
	private String manufacturer;
	
	@Column("product_price")
	@CassandraType(type = Name.DOUBLE)
	private double price;
	
	@Column("product_description")
	@CassandraType(type = Name.TEXT)
	private String desc;
	
	@Column("product_image_url")
	@CassandraType(type = Name.TEXT)
	private String imageUrl;

	public ProductByCategory() {
		this.id = UUID.randomUUID();
	}
	
	public ProductByCategory(String name, String category, String manufacturer, double price, String desc, String imageUrl) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.category = category;
		this.manufacturer = manufacturer;
		this.price = price;
		this.desc = desc;
		this.imageUrl = imageUrl;
	}
}
