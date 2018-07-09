package com.scan.framework.customerportal.model;

import java.util.Objects;

public class ItemData {
  private String Company;
  private String Warehouse;
  private String Bin;
  private String Item;
  private int QtyOnHand;
  private int QtyAvail;
  
  public String getCompany() {
    return Company;
  }
  
  public ItemData withCompany(String companyName) {
    Company = companyName;
    return this;
  }
  
  public void setCompany(String company) {
    Company = company;
  }
  
  public void setWarehouse(String warehouse) {
    Warehouse = warehouse;
  }
  
  public void setBin(String bin) {
    Bin = bin;
  }
  
  public void setItem(String item) {
    Item = item;
  }
  
  public String getWarehouse() {
    return Warehouse;
  }
  
  public ItemData withWarehouse(String warehouseName) {
    Warehouse = warehouseName;
    return this;
  }
  
  public String getBin() {
    return Bin;
  }
  
  public ItemData withBin(String binName) {
    Bin = binName;
    return this;
  }
  
  public String getItem() {
    return Item;
  }
  
  public ItemData withItem(String itemName) {
    Item = itemName;
    return this;
  }
  
  public int getQtyOnHand() {
    return QtyOnHand;
  }
  
  public ItemData withQtyOnHand(int qtyOnHand) {
    QtyOnHand = qtyOnHand;
    return this;
  }
  
  public int getQtyAvail() {
    return QtyAvail;
  }
  
  public ItemData withQtyAvail(int qtyAvail) {
    QtyAvail = qtyAvail;
    return this;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ItemData)) return false;
    ItemData that = (ItemData) o;
    return getQtyOnHand() == that.getQtyOnHand() &&
            Objects.equals(getCompany(), that.getCompany()) &&
            Objects.equals(getWarehouse(), that.getWarehouse()) &&
            Objects.equals(getBin(), that.getBin()) &&
            Objects.equals(getItem(), that.getItem());
  }
  
  @Override
  public int hashCode() {
    
    return Objects.hash(getCompany(), getWarehouse(), getBin(), getItem(), getQtyOnHand());
  }
  
  @Override
  public String toString() {
    return "ItemData{" +
            "Company='" + Company + '\'' +
            ", Warehouse='" + Warehouse + '\'' +
            ", Bin='" + Bin + '\'' +
            ", Item='" + Item + '\'' +
            ", QtyOnHand=" + QtyOnHand +
            ", QtyAvail=" + QtyAvail +
            '}';
  }
  
}
