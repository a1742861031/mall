package com.alth.gulimall.search.service;

import com.alth.gulimall.search.vo.SearchParam;
import com.alth.gulimall.search.vo.SearchResult;

/**
 * @Description 搜索service
 * @Date 2022/2/8 12:43 PM
 * @Created by bobo
 */
public interface MallSearchService {

    SearchResult search(SearchParam param);
}
