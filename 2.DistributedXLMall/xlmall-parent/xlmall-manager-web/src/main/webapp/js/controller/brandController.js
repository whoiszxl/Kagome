app.controller('brandController', function($scope, brandService) {

	// 重新调用查询品牌的接口
	$scope.reloadList = function() {
		// 切换页码
		// $scope.findPage( $scope.paginationConf.currentPage,
		// $scope.paginationConf.itemsPerPage);
		$scope.search($scope.paginationConf.currentPage,
				$scope.paginationConf.itemsPerPage);
	}

	// 分页控件配置
	$scope.paginationConf = {
		currentPage : 1,
		totalItems : 10,
		itemsPerPage : 10,
		perPageOptions : [ 10, 20, 30, 40, 50 ],
		onChange : function() {
			$scope.reloadList();// 重新加载
		}
	};

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

	// 删除时候用的，选中的ID的集合
	$scope.selectIds = [];

	// 更新复选
	$scope.updateSelection = function($event, id) {
		if ($event.target.checked) { // 被选中，添加到数组
			$scope.selectIds.push(id);
		} else {
			var idx = $scope.selectIds.indexOf(id);// 找到这个id在数组中的位置
			$scope.selectIds.splice(idx, 1);// 删除操作
		}
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
