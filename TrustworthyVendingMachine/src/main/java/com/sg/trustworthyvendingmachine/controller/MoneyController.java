/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.controller;

import com.sg.trustworthyvendingmachine.dao.PersistenceException;
import com.sg.trustworthyvendingmachine.model.Money;
import com.sg.trustworthyvendingmachine.service.ServiceLayer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author feng
 */
@Controller
@RequestMapping(value="/Money")
public class MoneyController {
    
    ServiceLayer service;
    BigDecimal dollar = new BigDecimal(1.00);
    BigDecimal quarter = new BigDecimal(0.25);
    BigDecimal dime = new BigDecimal(0.10);
    BigDecimal nickel = new BigDecimal(0.05);
    BigDecimal penny = new BigDecimal(0.01);
    BigDecimal unknown = new BigDecimal(0.00);
    
    @Inject
    public MoneyController(ServiceLayer service){
        this.service = service;
    }
    
    @RequestMapping(value="/insertDollar", method=RequestMethod.POST)
    public String addDollar(Model model) throws PersistenceException{
        Money coin = new Money();
        coin.setCoin(new BigDecimal(1.00));
        service.addMoney(coin);
        BigDecimal total = service.calculateInsertedTotal();
        model.addAttribute("total", total);
        return "redirect:/";
    }
    
    @RequestMapping(value="/insertedTotal", method=RequestMethod.POST)
    public String insertedMoney(@RequestParam("amount") String inserted) throws PersistenceException{
        Money coin = new Money();
        if(inserted.equals("dollar")){
            coin.setCoin(dollar);
        }else if(inserted.equals("quarter")){
            coin.setCoin(quarter);
        }else if(inserted.equals("dime")){
            coin.setCoin(dime);
        }else if(inserted.equals("nickel")){
            coin.setCoin(nickel);
        }else{
            coin.setCoin(unknown);
        }
        service.addMoney(coin);
        return "redirect:/";
    }

    
    @RequestMapping(value="/refund", method=RequestMethod.GET)
    public String refund(Model model, RedirectAttributes redirectModel) throws PersistenceException{
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal total = service.calculateInsertedTotal();
        String message="Here is your refund!";
        String changes="";
        if(total.compareTo(zero) != 1){
            message="Nice Try!";
        }else{
            changes = service.refundChanges(total);
        }
        service.removeMoney();
        redirectModel.addFlashAttribute("messages", message);
        redirectModel.addFlashAttribute("changes", changes);
        return "redirect:/?action=refund";
    }
}