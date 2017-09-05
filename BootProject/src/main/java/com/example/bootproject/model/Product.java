package com.example.bootproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
public class Product {
	@Id
	@GeneratedValue
	Integer pid;
	
	@NotEmpty
	String product_name;

	@NotEmpty(message="field must not be Empty")
	String product_type;
	@NotEmpty
	String product_price;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public Product(Integer pid, String product_name, String product_type, String product_price) {
		super();
		this.pid = pid;
		this.product_name = product_name;
		this.product_type = product_type;
		this.product_price = product_price;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", product_name=" + product_name + ", product_type=" + product_type
				+ ", product_price=" + product_price + "]";
	}	
	
	
}