package com.alth.gulimall.search.controller;

import com.alth.gulimall.search.service.MallSearchService;
import com.alth.gulimall.search.vo.SearchParam;
import com.alth.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description search
 * @Date 2022/2/8 12:35 PM
 * @Created by bobo
 */
@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping({"","/list.html"})
    public String listPage(SearchParam param, Model model) {
        //根据传递过来的参数 去Es中检索商品
        SearchResult searchResult = mallSearchService.search(param);
        model.addAttribute("result", searchResult);
        return "list";
    }
}
