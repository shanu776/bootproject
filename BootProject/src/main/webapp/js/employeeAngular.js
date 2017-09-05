 function employeeController($scope,$http) {  
            var url = "getEmployeeList";
           
            $http.get(url).success( function(response) { 
    	     	console.log(response);
    	        $scope.employees = response.employees;
    	     });
/*=================================For Sending Employee Data To Server==============================================*/	
	$scope.submit = function(){
		$scope.formData = [{
			"name": $scope.name,
			"email": $scope.email,
			"phone": $scope.phone,
			"password":$scope.password
		}];
		
		var response = $http.post('saveAngEmp', $scope.formData);
		response.success(function(data) {
			console.log(data);
		});
		response.error(function(err){
			console.log(err);
		});
	}
}