/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.service;;

import com.sg.trustworthyvendingmachine.dao.PersistenceException;
import com.sg.trustworthyvendingmachine.model.Items;
import com.sg.trustworthyvendingmachine.model.Money;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author feng
 */
public interface ServiceLayer {
    void addMoney(Money coin)  throws PersistenceException;
    
    String refundChanges(BigDecimal total);
    
    List<Money> getMoney() throws PersistenceException;
    
    void removeMoney() throws PersistenceException;
    
    void storeMoneyToMachine(BigDecimal price) throws PersistenceException;

    List<Items> getAllItems() throws PersistenceException;
    
    void addItem(String itemId, Items item) throws PersistenceException;
    
    Items dispenseItem(Items item) throws PersistenceException;
    
    List<Items> checkInventory (List<Items> itemList)throws PersistenceException;
    
    BigDecimal getChange(BigDecimal insertedTotal, Items item)throws InsufficientFundException;
    
    BigDecimal getSelectedItemPrice(Items item) throws PersistenceException;
    
    Items decrementInventory(Items item) throws PersistenceException;
    
    BigDecimal calculateInsertedTotal() throws PersistenceException;
    
    List<BigDecimal> getChangeInCoins(BigDecimal changes) throws PersistenceException;
    
    Items getInventory(String itemId) throws PersistenceException;
}
