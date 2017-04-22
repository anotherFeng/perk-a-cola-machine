/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.controller;

import com.sg.trustworthyvendingmachine.dao.PersistenceException;
import com.sg.trustworthyvendingmachine.model.Items;
import com.sg.trustworthyvendingmachine.service.ServiceLayer;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author feng
 */
@Controller
public class HomeController {
    ServiceLayer service;
    
    @Inject
    public HomeController(ServiceLayer service){
        this.service = service;
    }
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String displayIventories(Model model) throws PersistenceException{
        List<Items> invList = service.getAllItems();
        model.addAttribute("items", invList);
        BigDecimal total = service.calculateInsertedTotal();
        model.addAttribute("total", total);
        return "index";
    }
    
}
