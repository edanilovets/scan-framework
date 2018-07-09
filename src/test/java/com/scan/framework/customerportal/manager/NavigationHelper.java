package com.scan.framework.customerportal.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {
  
  private WebDriver driver;
  
  NavigationHelper(WebDriver driver) {
    this.driver = driver;
  }
  
  public void usersPage(){
    driver.findElement(By.linkText("USERS")).click();
  }
}
