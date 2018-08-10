//品牌服务
app.service("brandService", function($http) {

	this.findPage = function(page, size) {
		return $http.get('../brand/findAll.do?page=' + page + '&size=' + size);
	}

	this.findOne = function(id) {
		return $http.get('../brand/findOne.do?id=' + id);
	}

	this.add = function(entity) {
		return $http.get('../brand/add.do', entity);
	}

	this.update = function(entity) {
		return $http.get('../brand/update.do', entity);
	}

	this.dele = function(ids) {
		return $http.post('../brand/delete.do?ids=' + ids);
	}

	this.search = function(page, size, searchEntity) {
		return $http.post('../brand/search.do?page=' + page + '&size=' + size,
				searchEntity);
	}
});