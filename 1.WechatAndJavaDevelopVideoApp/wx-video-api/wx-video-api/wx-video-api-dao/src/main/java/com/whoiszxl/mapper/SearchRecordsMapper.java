package com.whoiszxl.mapper;

import java.util.List;

import com.whoiszxl.pojo.SearchRecords;
import com.whoiszxl.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	
	public List<String> getHotWords();
}