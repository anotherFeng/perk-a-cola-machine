/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.service;

import com.sg.trustworthyvendingmachine.dao.MoneyDao;
import com.sg.trustworthyvendingmachine.dao.PersistenceException;
import com.sg.trustworthyvendingmachine.dao.VenderDao;
import com.sg.trustworthyvendingmachine.model.Items;
import com.sg.trustworthyvendingmachine.model.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author feng
 */
public class ServiceLayerImpl implements ServiceLayer{
    
    MoneyDao moneyDao;
    VenderDao venderDao;
    
    public ServiceLayerImpl(VenderDao venderDao, MoneyDao moneyDao){
        
        this.venderDao = venderDao;
        this.moneyDao = moneyDao;
    }

    @Override
    public BigDecimal getChange(BigDecimal insertedTotal, Items item) throws InsufficientFundException {
        switch (insertedTotal.compareTo(item.getPrice())) {
            case 0:
                return insertedTotal.subtract(item.getPrice());
            case 1:
                return insertedTotal.subtract(item.getPrice());
            default:
                throw new InsufficientFundException("Yo, insert more money!");
                
        }
    }
    
    @Override
    public String refundChanges(BigDecimal total){
            BigDecimal quarter = new BigDecimal("0.25");
            BigDecimal dime = new BigDecimal("0.10");
            BigDecimal nickel = new BigDecimal("0.05");
            BigDecimal penny = new BigDecimal("0.01");
            int quarterInt = 0, dimeInt = 0, nickelInt = 0, pennyInt = 0;
            while (total.compareTo(quarter) == 0 || total.compareTo(quarter) == 1) {
                quarterInt++;
                total = total.subtract(quarter);
            }
            while (total.compareTo(dime) == 0 || total.compareTo(dime) == 1) {
                dimeInt++;
                total = total.subtract(dime);
            }
            while (total.compareTo(nickel) == 0 || total.compareTo(nickel) == 1) {
                nickelInt++;
                total = total.subtract(nickel);
            }
            while (total.compareTo(penny) == 0 || total.compareTo(penny) == 1) {
                pennyInt++;
                total = total.subtract(penny);
            }
            String quarterString = "Quarters: " + quarterInt;
            String dimeString = "\nDimes: " + dimeInt;
            String nickelString = "\nNickels: " + nickelInt;
            String pennyString = "\nPennies: " + pennyInt;
            String changes = quarterString +dimeString+nickelString
                     +pennyString;
            return changes;
    }
    
    @Override
    public List<BigDecimal> getChangeInCoins(BigDecimal changes) throws PersistenceException{
        List<BigDecimal> coins = new ArrayList<>();
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
        while(changes.compareTo(quarter) == 1|| changes.compareTo(quarter)==0){
            coins.add(quarter);
            changes = changes.subtract(quarter);
        }
        while(changes.compareTo(dime)==1|| changes.compareTo(dime)==0){
            coins.add(dime);
            changes = changes.subtract(dime);
        }
        while(changes.compareTo(nickel)==1|| changes.compareTo(nickel)==0){
            coins.add(nickel);
            changes = changes.subtract(nickel);
        }
        return coins;
    }
    
    
        
    @Override
    public Items decrementInventory(Items item) throws PersistenceException{
        item.setQuantity(item.getQuantity() - 1);
        return item;
    }
    
    @Override
    public void addItem(String itemId, Items item) throws PersistenceException{
        venderDao.addItem(itemId, item);
    }

    @Override
    public void addMoney(Money coin) throws PersistenceException{
        moneyDao.addMoney(coin);
    }

    @Override
    public List<Money> getMoney() throws PersistenceException {
        return moneyDao.getMoney();
    }

    @Override
    public void removeMoney() throws PersistenceException {
        moneyDao.removeMoney();
    }

    @Override
    public void storeMoneyToMachine(BigDecimal price) throws PersistenceException {
        moneyDao.storeMoneyToMachine(price);
    }

    @Override
    public List<Items> getAllItems() throws PersistenceException {
        return venderDao.getAllItems();
    }

    @Override
    public Items dispenseItem(Items item) throws PersistenceException {
        return venderDao.dispenseItem(item);
    }
    
    @Override
    public List<Items> checkInventory(List<Items> itemList) throws PersistenceException {
        return venderDao.checkInventory(itemList);
    }
    
    @Override
    public BigDecimal getSelectedItemPrice(Items item) throws PersistenceException{
        return item.getPrice();
    }

    
    @Override
    public BigDecimal calculateInsertedTotal() throws PersistenceException{
        return moneyDao.calculateInsertedTotal();
    }
    
    @Override
    public Items getInventory(String itemId) throws PersistenceException{
        return venderDao.getInventory(itemId);
    }

}
