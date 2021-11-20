package com.jun.ecommerce.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(value = "product_by_id")
public class Product {

	@Id
	@PrimaryKeyColumn(name = "prodict_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.UUID)
	private UUID id;
	@Column("product_name")
	@CassandraType(type = Name.TEXT)
	private String name;
	@Column("product_category")
	@CassandraType(type = Name.TEXT)
	private String category;
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

	public Product() {
		this.id = UUID.randomUUID();
	}
	public Product(String name, String category, String manufacturer, double price, String desc, String imageUrl) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.category = category;
		this.manufacturer = manufacturer;
		this.price = price;
		this.desc = desc;
		this.imageUrl = imageUrl;
	}
}
