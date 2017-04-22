/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.dao;

import com.sg.trustworthyvendingmachine.model.Money;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author feng
 */
public class MoneyDaoImpl implements MoneyDao {
    
    List<Money> insertedTotal = new ArrayList<>();
    List<BigDecimal> machineTotal = new ArrayList<>();
    String TEMP_MONEY_FILE;
    
    public MoneyDaoImpl(String file){
        TEMP_MONEY_FILE = file;
    }
    
    
    @Override
    public void addMoney(Money coin) throws PersistenceException{
        insertedTotal.clear();
        loadInsertedMoney();
        insertedTotal.add(coin);
        saveInsertedMoney();
    }
    
    @Override
    public void removeMoney() throws PersistenceException {
        loadInsertedMoney();
        insertedTotal.clear();
        saveInsertedMoney();
    }
    
    @Override
    public void storeMoneyToMachine(BigDecimal price) throws PersistenceException{
        loadInsertedMoney();
        insertedTotal.clear();
        machineTotal.add(price);
        saveInsertedMoney();
    }
    
    @Override
    public BigDecimal calculateInsertedTotal() throws PersistenceException{
        insertedTotal.clear();
        loadInsertedMoney();
        BigDecimal total = new BigDecimal(0);
        for(Money currentMoney : insertedTotal){
            BigDecimal coin = new BigDecimal("" + currentMoney.getCoin());
            total = total.add(coin);
        }
        return total;
    }
    
    @Override
    public List<Money> getMoney() throws PersistenceException{
        return insertedTotal;
    }
    
    

    private void saveInsertedMoney() throws PersistenceException{
        PrintWriter output;
        try{
            output = new PrintWriter(new FileWriter(TEMP_MONEY_FILE));
        } catch (IOException ex){
            throw new PersistenceException("Could not save money amount. ", ex);
        }
        List<Money> moneyList = this.getMoney();
        for(Money currentMoney : moneyList){
            output.print(currentMoney.getCoin().setScale(2, RoundingMode.HALF_UP) + "\n");
            output.flush();
        }
        output.close();
    }

    private void loadInsertedMoney() throws PersistenceException{
        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(TEMP_MONEY_FILE)));
        } catch (FileNotFoundException ex){
            throw new PersistenceException("Could not load money amount. ", ex);
        }
        String currentLine;
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            Money currentMoney = new Money();
            currentMoney.setCoin(new BigDecimal(currentLine).setScale(2, RoundingMode.HALF_UP));
            insertedTotal.add(currentMoney);
        }
        scanner.close();
    }
    
    @Override //For testing only
    public void clearMachineMoney() throws PersistenceException{
        machineTotal.clear();
    }
    
    @Override //dido
    public void saveInserted() throws PersistenceException{
        saveInsertedMoney();
    }
    
    @Override //dido
    public void saveMachine() throws PersistenceException{
    }
    
    @Override //dido
    public BigDecimal getMachineTotal() throws PersistenceException{
        BigDecimal machineSum = new BigDecimal("0");
        for(BigDecimal currentBD: machineTotal){
            machineSum = machineSum.add(currentBD);
        }
        return machineSum;
    }
    
    @Override //dido
    public void loadMachine() throws PersistenceException{
    }
}
