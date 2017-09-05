package com.example.bootproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.Product;
@Service
public class ProductDaoClass {

	ProductDao productDao;
	EntityManagerFactory emf;
	
	@Autowired	
	public ProductDaoClass(ProductDao productDao, EntityManagerFactory emf) {
		super();
		this.productDao = productDao;
		this.emf = emf;
	}

	public void saveProduct(Product product){
		productDao.save(product);
	}
	
	public List<Product> getProductList(){
		return (List<Product>) productDao.findAll();
	}
	
	public Product getProduct(Integer id){
		return productDao.findOne(id);
	}
	
	public void deleteProduct(Integer id){
		 productDao.delete(id);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> nativeSql(){
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from product",Product.class);
		List<Product> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
}
