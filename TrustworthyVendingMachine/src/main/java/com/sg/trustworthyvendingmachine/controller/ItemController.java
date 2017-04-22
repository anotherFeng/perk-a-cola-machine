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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/Item")
public class ItemController {

    ServiceLayer service;

    @Inject
    public ItemController(ServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/selectItem", method = RequestMethod.GET)
    public String selectItem(@RequestParam("id") String id, Model model, RedirectAttributes redirectModel) throws PersistenceException {
        Items item = service.getInventory(id);
        redirectModel.addFlashAttribute("item", item);
        return "redirect:/";
    }

    @RequestMapping(value = "/vendItem", method = RequestMethod.POST)
    public String vendItem(HttpServletRequest request, Model model, RedirectAttributes redirectModel) throws PersistenceException {
        String id = request.getParameter("item");
        String message = "";
        if(id.length() > 0 ) {
            Items item = service.getInventory(id);
            BigDecimal price = item.getPrice();
            BigDecimal total = service.calculateInsertedTotal();
            if (item.getQuantity() == 0) {
                message = "OUT OF STOCK!";
            } else if (total.compareTo(price) < 0) {
                BigDecimal difference = price.subtract(total);
                message = "Give me "+ difference +" more Yo!";
            } else {
                Items dispensedItem = service.dispenseItem(item);
                Items decremented = service.decrementInventory(dispensedItem);
                service.addItem(decremented.getItemId(), decremented);
                message = "Here is your " + dispensedItem.getName();
                total = total.subtract(dispensedItem.getPrice());
                String changes = service.refundChanges(total);
                service.removeMoney();
                redirectModel.addFlashAttribute("changes", changes);}
        }else{
            message = "Please select an item.";
        }
        redirectModel.addFlashAttribute("messages", message);
        return "redirect:/";
    }
}
