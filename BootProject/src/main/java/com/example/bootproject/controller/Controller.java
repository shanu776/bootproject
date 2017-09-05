package com.example.bootproject.controller;

import static org.mockito.Matchers.anyCollection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bootproject.dao.CustomerDaoClass;
import com.example.bootproject.dao.EmployeeDaoClass;
import com.example.bootproject.dao.ProductDaoClass;
import com.example.bootproject.model.Customer;
import com.example.bootproject.model.Employee;
import com.example.bootproject.model.Product;
import com.example.bootproject.report.CustomerReport;
import com.example.bootproject.report.ProductReport;



@org.springframework.stereotype.Controller
public class Controller {
	CustomerDaoClass customerDao;	
	ProductDaoClass productDao;
	EmployeeDaoClass employeeDao;
	
	@Autowired
	public Controller(CustomerDaoClass customerDao, ProductDaoClass productDao, EmployeeDaoClass employeeDao) {
		this.customerDao = customerDao;
		this.productDao = productDao;
		this.employeeDao = employeeDao;
	}
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request){		
		return "index";
	}
	
/*================================================Customer============================================================*/
	
	@RequestMapping(value="/customerIndex")
	public String customerIndex(@ModelAttribute("customer") Customer customer,Model model)
	{
		List<Customer> customers=customerDao.listCustomerData();
		model.addAttribute("customers", customers);
		return "customerIndex";
	}
	@RequestMapping(value="/home")
	public String home(HttpServletRequest request,Model model){
		String value=request.getParameter("input");
		model.addAttribute("message",value+" this is home page");
		return "home";
	}
	
	@RequestMapping(value="/drivedemo")
	public String driveDemo(){
		return "googleDriveDemo";
	}
	
	@RequestMapping(value="/uploadtodrive")
	public String uploadToDrive(){
		/*File fileMetadata = new File();
		fileMetadata.setName("photo.jpg");
		java.io.File filePath = new java.io.File("files/photo.jpg");
		FileContent mediaContent = new FileContent("image/jpeg", filePath);
		File file = driveService.files().create(fileMetadata, mediaContent)
		        .setFields("id")
		        .execute();
		System.out.println("File ID: " + file.getId());*/
		return "googleDriveDemo";
	}
	
	@RequestMapping(value="/addCustomer",method=RequestMethod.POST)
	public String addCustomer(@Valid Customer customer,BindingResult bindingResult,HttpServletRequest request,Model model){
		
		/*String name=request.getParameter("name");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		System.out.println("Result = "+name+" "+email+" "+phone);
		customer=new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPhone(phone);*/
		
		if(bindingResult.hasErrors()){
			model.addAttribute("customers", customerDao.listCustomerData());
			return "customerIndex";
		}
		
		customerDao.saveCustomer(customer);
		/*model.addAttribute("customers", customerDao.listCustomerData());
		model.addAttribute("message","customer data save");*/
		return "redirect:customerIndex.html";
	}
	
	@RequestMapping(value="/deleteCustomer",method=RequestMethod.GET)
	public String deleteCustomer(Customer customer){
		customerDao.deleteCustomerData(customer);
		return "redirect:customerIndex.html";
	}
	
	@RequestMapping(value="/getCustomer")
	public String getCustomer(@RequestParam("id") String id,Model model,Customer customer){
		model.addAttribute("customer", customerDao.getCustomerData(customer.getId()));
		model.addAttribute("customers", customerDao.listCustomerData());
		return "customerIndex";
	}
	
	@RequestMapping(value="/customerReport")
	public String getCustomer(HttpServletRequest request,HttpServletResponse response){
		List<Customer> model=customerDao.listCustomerData();
		new CustomerReport().allCustomerReport(model, request,response);;
		return "redirect:customerIndex.html";
	}
	
	@RequestMapping(value="geninvoice")
	public String genInvoice(HttpServletRequest request,HttpServletResponse response){
		List<Customer> list = Arrays.asList(customerDao.getCustomerData(1));
		new CustomerReport().genrateInvoice(list, request,response);
		return null;
	}
	
/* ===============================================Product Operation===================================== */
	
	@RequestMapping(value="/productIndex")
	public String productIndex(@ModelAttribute("product") Product product)
	{
		return "saveProduct";
	}
	
	@RequestMapping(value="/saveProduct",method=RequestMethod.POST)
	public String saveProduct(@Valid Product product,BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			return "saveProduct";
		}
		productDao.saveProduct(product);
		return "redirect:productIndex.html";
	}

	@ResponseBody
	@RequestMapping(value = "/getProductList")
	public Map<String,Object> getProductList(Model model1,HttpServletRequest request)
	{
		String name=request.getParameter("pName");
		System.out.println(name);
		Map<String,Object> model=new HashMap<>();
		model.put("products",productDao.getProductList());
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/editProduct")
	public Product editProduct(@RequestParam("id") String id){
		Product product=productDao.getProduct(Integer.parseInt(id));	
		System.out.println(product.getProduct_name());
		List<Product> model=new ArrayList<>();
		model.add(product);
		return product;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteProduct")
	public String deleteProduct(@RequestParam("id") String id){
		productDao.deleteProduct(Integer.parseInt(id));
		return "successfully deleted";
	}
	
	@ResponseBody
	@RequestMapping(value="singleProductReport")
	public String singleProductReport(@RequestParam("id") String id1,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<>();
		Integer id=Integer.parseInt(id1);
		System.out.println(id);
		model.put("id", id);		
		new ProductReport().getSingleReport(model, request,response);
		return "genreted";
	}
	
	@RequestMapping(value="allProductReport")
	public String allProductReport(HttpServletRequest request,HttpServletResponse response){
		List<Product> model = productDao.getProductList();
		new ProductReport().getAllProductReport(request,response,model);
		return null;
	}
	
		
/*=========================================EmployeeControler=====================================================*/
	
	@RequestMapping(value="/employeeIndex")
	public String employeeIndex(){
		return "employee";
	}
	
	@RequestMapping(value="saveEmployee",method=RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("employee") Employee employee){
		employeeDao.save(employee);
		return "redirect:employeeIndex.html";
	}
	
	@ResponseBody
	@RequestMapping(value="saveAngEmp",method=RequestMethod.POST)
	public Map<String,Object> saveAngEmp(@RequestBody List<Employee> employee){
		for(Employee emp:employee){
		employeeDao.save(emp);
		}
		Map<String,Object> model = new HashMap<>();
		model.put("employee", employee);
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="getEmployeeList")
	public Map<String,Object> getEmployeeList(){
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("employees", employeeDao.getEmployeeList());
		return model;
	}
		

	@ResponseBody				//bind Map data into json object
	@RequestMapping(value="/getInfo")
	public Map<String,Object> info(@RequestParam("id") String id){
		Map<String,Object> model=new HashMap<>();
		model.put("customers", customerDao.listCustomerData());
		return model;
	}
}