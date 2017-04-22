/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.dao;


import com.sg.trustworthyvendingmachine.model.Items;
import java.util.List;

/**
 *
 * @author feng
 */
public interface VenderDao {
    
    List<Items> getAllItems() throws PersistenceException;
    
    void addItem(String itemId, Items item) throws PersistenceException;
    
    Items dispenseItem(Items item) throws PersistenceException;
    
    List<Items> checkInventory(List<Items> itemList) throws PersistenceException;
    
    Items getInventory(String itemId) throws PersistenceException;
    
    void save() throws PersistenceException;
}
