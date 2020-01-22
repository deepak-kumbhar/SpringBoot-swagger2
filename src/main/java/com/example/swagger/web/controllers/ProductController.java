package com.example.swagger.web.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.swagger.business.services.ProductService;
import com.example.swagger.persistence.domain.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@Api(value = "onlinestore", description = "Operations pertaining to products in Online Store")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ProductController {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Get list of products.
	 *  
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "View a list of available products", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/list")
	public Iterable<Product> list(Model model) {
		return productService.listAllProducts();
	}

	/**
	 * Search product by Id.
	 *  
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @param id
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "Search a product with an ID", response = Product.class)
	@GetMapping(value = "/show/{id}")
	public Product showProduct(@PathVariable Integer id, Model model) {
		return productService.getProductById(id);
	}

	/**
	 * Add product.
	 *  
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @param product
	 * @return
	 */
	@ApiOperation(value = "Add a product")
	@PostMapping(value = "/add")
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		productService.saveProduct(product);
		return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
	}

	/**
	 * Update product.
	 *  
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @param id
	 * @param product
	 * @return
	 */
	@ApiOperation(value = "Update a product")
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
		Product storedProduct = productService.getProductById(id);
		storedProduct.setDescription(product.getDescription());
		storedProduct.setImageUrl(product.getImageUrl());
		storedProduct.setPrice(product.getPrice());
		productService.saveProduct(storedProduct);
		return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
	}

	/**
	 * Delete product.
	 *  
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Delete a product")
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);

	}

}
