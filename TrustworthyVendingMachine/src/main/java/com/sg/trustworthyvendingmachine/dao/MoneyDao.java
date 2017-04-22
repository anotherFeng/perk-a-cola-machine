/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.dao;

import com.sg.trustworthyvendingmachine.model.Money;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author feng
 */
public interface MoneyDao {
    
    void addMoney(Money coin)  throws PersistenceException;
    
    List<Money> getMoney() throws PersistenceException;
    
    void removeMoney() throws PersistenceException;
    
    void storeMoneyToMachine(BigDecimal price) throws PersistenceException;

    BigDecimal calculateInsertedTotal() throws PersistenceException;
    
    void clearMachineMoney() throws PersistenceException;
    
    void saveInserted() throws PersistenceException;
            
    void saveMachine() throws PersistenceException;
    
    BigDecimal getMachineTotal() throws PersistenceException;
    
    void loadMachine() throws PersistenceException;
}
