var tbody='';

function getProductList(){
	 var sendData = {
			    "pName" : "bhanu",
			     "lName" :"pratap"
			   }
	$.ajax({
		url: "getProductList",
		async:true,
		data:sendData,
		success: function(result){
		console.log(result)
		 $.each(result.products,function(i,items){
			tbody += "<tr class='test'><td><input type='radio' id='radio' name='delete' value='"+items.pid+"'/></td><td>"+items.pid+"</td>" +
					"<td>"+items.product_name+"</td><td>"+items.product_type+"</td><td>"+items.product_price+"</td></tr>";
		 });
		$("#products").append(tbody);
    }});	
}

function editProduct(){
	$('tbody').on('click','tr.test',function(){
		var id=$('input[name=delete]:checked').val();
		$.ajax({
			url: "editProduct?id="+id,
			success: function(result){
				$('#pid').val(result.pid);
				$('#p_name').val(result.product_name);
				$('#p_type').val(result.product_type);
				$('#p_price').val(result.product_price);
			console.log(result); },
			error: function(error){
				console.log(error);
			}
		});	
	});
}

function deleteProduct(){
	$('#delete').on('click',function(){
		var id=$('input[name=delete]:checked').val();
		$.ajax({
			url: "deleteProduct?id="+id,
			success : function(result){
				location.reload();
				console.log(result);				
			}
		});
	});
}

function genReport(){
	$('#report').on('click',function(){		
		var id=$('input[name=delete]:checked').val();
		
		console.log("clicked"+id);
		$.ajax({
			url:"singleProductReport?id="+id,
			success:function(result){
				var win = window.open('http://localhost:8080/report/productReport.pdf', '_blank');						
				if (win) {
				    //Browser has allowed it to be opened
				    win.focus();
				} else {
				    //Browser has blocked it
				    alert('Please allow popups for this website');
				}
			},
			error:function(err){
				console.log(err);
			}
		});
	});
}

/*var app= angular.module('myApp',[]);
app.controller('mtCtr',function($scope){
	$scope.name="";
});
*/
$(document).ready(function(){
	getProductList();
	editProduct();
	deleteProduct();
	genReport();
});