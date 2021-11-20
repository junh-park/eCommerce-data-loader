package com.jun.ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.jun.ecommerce.connection.DataStaxAstraProperties;
import com.jun.ecommerce.data.ProductRepository;
import com.jun.ecommerce.domain.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class ECommerceDataLoaderApplication {
	
	@Autowired
	ProductRepository productRepository;
	
	@Value("${sampledata.location}")
	private String sampleDataLocation;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceDataLoaderApplication.class, args);
	}
	
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties) {
		Path bundle = properties.getSecureConnectBundle().toPath();
		return builder-> builder.withCloudSecureConnectBundle(bundle);
	}
	
	@PostConstruct
	public void init() { 
		log.info("Application has started");

//		Product product = new Product();
//		product.setDesc("a");
//		product.setImageUrl("here");
//		product.setName("desk" );
//		product.setPrice(4);
//		product.setManufacturer("i did");
//		
//		productRepository.save(product);
		Path path = Paths.get(sampleDataLocation);
		String[] header = {"product_name","product_category", "manufacturer", "product_price", "product_description", "product_image_url"};
		try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {
			List<CSVRecord> parser = CSVFormat.DEFAULT.withHeader(header)
				.withFirstRecordAsHeader().parse(reader).getRecords();
			
			try {
				parser.stream().forEach(record -> productMapper(record));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void productMapper(CSVRecord record) {
		Product product = new Product();
		product.setName(record.get("product_name"));
		product.setCategory(record.get("product_category"));
		product.setManufacturer(record.get("manufacturer"));
		product.setPrice(Double.parseDouble(record.get("product_price")));
		product.setDesc(record.get("product_description"));
		
		String imageUrl = record.get("product_image_url");
		int index = imageUrl.indexOf('|');
		
		if (index == -1) {
			product.setImageUrl(imageUrl);
		} else {
			product.setImageUrl(imageUrl.substring(0,index));
		}
		
		productRepository.save(product);
		System.out.println("Product saved: " + product.getName() + "\r");
	}
}
