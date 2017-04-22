/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.trustworthyvendingmachine.dao;


import com.sg.trustworthyvendingmachine.model.Items;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author feng
 */
public class VenderDaoImpl implements VenderDao{
    
    Map<String, Items> invMap = new HashMap<>();
    String INVENTORY_FILE;
    String DELIMITER;
    
    public VenderDaoImpl(String file, String delimiter){
        INVENTORY_FILE = file;
        DELIMITER = delimiter;
    }
    
    @Override
    public List<Items> getAllItems() throws PersistenceException {
        loadInventory();
        return new ArrayList<Items>(invMap.values());
    }

    
    @Override
    public Items dispenseItem(Items item) throws PersistenceException{
        loadInventory();
        Items dispensedItem = invMap.remove(item.getItemId());
        return dispensedItem;
    }
    
    @Override
    public void addItem(String itemId, Items item) throws PersistenceException{
        loadInventory();
        invMap.put(itemId, item);
        saveInventory();
    }
    
    @Override
    public List<Items> checkInventory(List<Items> itemList) throws PersistenceException{
        return itemList
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }
    
    @Override //For testing only
    public void save() throws PersistenceException{
        saveInventory();
    }
    
    @Override
    public Items getInventory(String itemId) throws PersistenceException{
        return invMap.get(itemId);
    }
    
    private void loadInventory() throws PersistenceException{
        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException ex){
            throw new PersistenceException("Could not load inventory data. ", ex);
        }
        String currentLine;
        String[] currentToken = new String[4];
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            
            Items currentItem = new Items();
            currentItem.setItemId(currentToken[0]);
            currentItem.setName(currentToken[1]);
            currentItem.setPrice(new BigDecimal(currentToken[2]));
            currentItem.setQuantity(Integer.parseInt(currentToken[3]));
            invMap.put(currentItem.getItemId(), currentItem);
        }
        scanner.close();
    }

    private void saveInventory() throws PersistenceException{
        PrintWriter output;
        try{
            output = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException ex){
            throw new PersistenceException("Could not save inventory data. ", ex);
        }
        List<Items> itemList = this.getAllItems();
        for (Items currentItem : itemList){
            output.print(currentItem.getItemId() + DELIMITER +
                    currentItem.getName() + DELIMITER +
                    currentItem.getPrice() + DELIMITER +
                    currentItem.getQuantity() + DELIMITER + "\n");
            output.flush();
        }
        output.close();
    }


}
