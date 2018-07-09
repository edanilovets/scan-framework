package com.scan.framework.customerportal.test;

import com.scan.framework.customerportal.model.ItemData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class InventoryTests {
  
  @Test
  public void itemsSyncTest() throws IOException {
  
    Properties p = new Properties();
    p.load(new FileReader(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\environment.properties")));
    
    String url1 = p.getProperty("mysql.url") + ":" + p.getProperty("mysql.port") +
            "/" + p.getProperty("mysql.db") + "?user=" + p.getProperty("mysql.user") +
            "&password=" + p.getProperty("mysql.userpass") ;
    
    String url2 = p.getProperty("sqlserv.url") + ":" + p.getProperty("sqlserv.port") +
            ";databaseName=" + p.getProperty("sqlserv.db") + ";user=" + p.getProperty("sqlserv.user") + ";password=" +
            p.getProperty("sqlserv.userpass");
    
    List<ItemData> itemsDB1 = new ArrayList<>();
    List<ItemData> itemsDB2 = new ArrayList<>();
    
    try {
      Connection conn;
      conn = DriverManager.getConnection(url1);
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT CompanyName, WarehouseID, BinCode, ItemCode, QtyOnHand, QtyAvailable FROM AcuWebIt_SCANCOQA57.vItemWhseBin");
      
      while (rs.next()) {
        itemsDB1.add(new ItemData().withCompany(rs.getString("CompanyName")).withWarehouse(rs.getString("WarehouseID"))
                .withBin(rs.getString("BinCode")).withItem(rs.getString("ItemCode")).withQtyOnHand(rs.getInt("QtyOnHand"))
                .withQtyAvail(rs.getInt("QtyAvailable")));
      }
      rs.close();
      st.close();
      conn.close();
    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    
    try {
      Connection conn;
      conn = DriverManager.getConnection(url2);
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT ALL [CompanyID],[SiteID],[LocationID],[InventoryID],[QtyOnHand],[QtyAvail] FROM [Acumatica2018R1].[dbo].[INLocationStatus]");
      while (rs.next()) {
        itemsDB2.add(new ItemData().withCompany(rs.getString("CompanyID")).withWarehouse(rs.getString("SiteID"))
                .withBin(rs.getString("LocationID")).withItem(rs.getString("InventoryID")).withQtyOnHand(rs.getInt("QtyOnHand"))
                .withQtyAvail(rs.getInt("QtyAvail")));
      }
      rs.close();
      st.close();
      conn.close();
    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    
    String[] filterItems = {"1-NOTTRACK","1-LREX","1-LRF","1-LU","1-SRF","1-SRENTER","1-SRSEQ","1-SUENT"};
    String[] filterBins = {"R1S1", "R1S2", "R1S3"};
    
    changeIdToName(itemsDB2);
    filterByCompany(itemsDB1, "Company");
    filterByCompany(itemsDB2, "Company");
    itemsDB1 = filterByItems(itemsDB1, filterItems);
    itemsDB2 = filterByItems(itemsDB2, filterItems);
    itemsDB1 = filterByBins(itemsDB1, filterBins);
    itemsDB2 = filterByBins(itemsDB2, filterBins);
    removeZeroQtyElements(itemsDB1);
    removeZeroQtyElements(itemsDB2);
    sortItems(itemsDB1);
    sortItems(itemsDB2);
    saveAsCsv(itemsDB1, "itemsDB1.csv");
    saveAsCsv(itemsDB2, "itemsDB2.csv");

    Assert.assertEquals(itemsDB1,itemsDB2);
  }
  
  private void filterByCompany(List<ItemData> items, String company) {
    items.removeIf(i -> (!i.getCompany().equals(company)));
  }
  
  private List<ItemData> filterByBins(List<ItemData> items, String[] filterBins) {
    List<ItemData> newItems = new ArrayList<>();
    for (int i=0;i<filterBins.length;i++) {
      for (ItemData item : items) {
        if (item.getBin().equals(filterBins[i])) {
          newItems.add(new ItemData().withCompany(item.getCompany()).withWarehouse(item.getWarehouse())
                  .withBin(item.getBin()).withItem(item.getItem()).withQtyOnHand(item.getQtyOnHand()).withQtyAvail(item.getQtyAvail()));
        }
      }
    }
    return newItems;
  }
  
  private List<ItemData> filterByItems(List<ItemData> items, String[] filterItems) {
    List<ItemData> newItems = new ArrayList<>();
    for (int i=0;i<filterItems.length;i++) {
      for (ItemData item : items) {
        if (item.getItem().equals(filterItems[i])) {
          newItems.add(new ItemData().withCompany(item.getCompany()).withWarehouse(item.getWarehouse())
          .withBin(item.getBin()).withItem(item.getItem()).withQtyOnHand(item.getQtyOnHand()).withQtyAvail(item.getQtyAvail()));
        }
      }
    }
    return newItems;
  }
  
  private void changeIdToName(List<ItemData> items) {
    for (ItemData i : items) {
      if (i.getCompany().equals("2")) i.setCompany("Company");
      if (i.getWarehouse().equals("154")) i.setWarehouse("WHOLESALE");
      if (i.getWarehouse().equals("159")) i.setWarehouse("RETAIL");
      if (i.getBin().equals("155")) i.setBin("R1S1");
      if (i.getBin().equals("156")) i.setBin("R1S2");
      if (i.getBin().equals("157")) i.setBin("R1S3");
      if (i.getItem().equals("10040")) i.setItem("1-NOTTRACK");
      if (i.getItem().equals("10041")) i.setItem("1-LREX");
      if (i.getItem().equals("10042")) i.setItem("1-LRF");
      if (i.getItem().equals("10043")) i.setItem("1-LU");
      if (i.getItem().equals("10044")) i.setItem("1-SRF");
      if (i.getItem().equals("10045")) i.setItem("1-SRENTER");
      if (i.getItem().equals("10046")) i.setItem("1-SRSEQ");
      if (i.getItem().equals("10047")) i.setItem("1-SUENT");
      
    }
  }
  
  private void removeZeroQtyElements(List<ItemData> items) {
    items.removeIf(i -> (i.getQtyOnHand() == 0) && (i.getQtyAvail() == 0));
  }
  
  private void sortItems(List<ItemData> items) {
    items.sort(Comparator.comparing(ItemData::getItem));
  }
  
  private void saveAsCsv(List<ItemData> items, String filename) throws IOException {
    File itemsFile = new File("src/test/resources/" + filename);
    Writer writer = new FileWriter(itemsFile);
    for (ItemData i : items) {
      writer.write(String.format("%s;%s;%s;%s;%s\n", i.getCompany(), i.getWarehouse(),
              i.getBin(), i.getItem(), i.getQtyOnHand()));
    }
    writer.close();
  }
}
