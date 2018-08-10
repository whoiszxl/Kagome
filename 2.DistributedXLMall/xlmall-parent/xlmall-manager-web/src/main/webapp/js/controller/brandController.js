app.controller('brandController', function($scope, $controller, brandService) {

	$controller('baseController', {$scope:$scope}); //集成基本控制类
	
	$scope.findPage = function(page, size) {
		brandService.findPage().success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;// 更新总记录数
		});
	}

	// 添加或修改品牌
	$scope.save = function() {

		var object = null; // 方法名称
		if ($scope.entity.id != null) {// 如果有ID
			object = brandService.update($scope.entity);
		} else {
			object = brandService.add($scope.entity);
		}

		object.success(function(response) {
			if (response.success) {
				// 重新查询
				$scope.reloadList();// 重新加载
			} else {
				alert(response.message);
			}
		});
	}

	// 查询一个品牌哦
	$scope.findOne = function(id) {
		brandService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}

	// 批量删除
	$scope.dele = function() {
		// 获取到选中的复选框
		brandService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.reloadList();// 重载列表
			}
		});
	}

	// 条件查询
	$scope.searchEntity = {};// 定义搜索对象
	$scope.search = function(page, size) {
		brandService.search(page, size, $scope.searchEntity).success(
				function(response) {
					$scope.paginationConf.totalItems = response.total;// 总记录数
					$scope.list = response.rows;// 给列表变量赋值
				});
	}

});
