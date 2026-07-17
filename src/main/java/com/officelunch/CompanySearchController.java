/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * @author 김정민
 */

@RestController
public class CompanySearchController {
    private final CompanySearchService companySearchService;

    public CompanySearchController(CompanySearchService companySearchService){
        this.companySearchService = companySearchService;
    }
    
    @GetMapping("/api/companies/search")
    public List<CompanySearchResult> searchCompanies(
        @RequestParam String query
    ){
        return companySearchService.search(query);
    }
    
    

}
