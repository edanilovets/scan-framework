package com.scan.framework.customerportal.manager;

import com.scan.framework.customerportal.model.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppManager {
  
  private Properties properties;
  private WebDriver driver;
  private NavigationHelper navigationHelper;
  
  
  public NavigationHelper navigate() {
    return navigationHelper;
  }
  
  public void init() throws IOException {
    
    properties = new Properties();
    properties.load(new FileReader(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\environment.properties")));
    String browser = properties.getProperty("browser");
    
    switch (browser) {
      case "chrome":
        driver = new ChromeDriver();
        break;
      case "firefox":
        driver = new FirefoxDriver();
        break;
      case "ie":
        driver = new InternetExplorerDriver();
        break;
    }
    
    //WebDriverWait wait = new WebDriverWait(driver, 5);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    navigationHelper = new NavigationHelper(driver);
  }
  
  public void stop() {
    driver.quit();
  }
  
  public void logInAsAdmin() {
    driver.get(properties.getProperty("portal.homepage"));
    driver.findElement(By.id("AccountCode")).clear();
    driver.findElement(By.id("AccountCode")).click();
    driver.findElement(By.id("AccountCode")).sendKeys(properties.getProperty("portal.accountcode"));
    driver.findElement(By.id("UserName")).clear();
    driver.findElement(By.id("UserName")).click();
    driver.findElement(By.id("UserName")).sendKeys(properties.getProperty("portal.admin"));
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).sendKeys(properties.getProperty("portal.adminpass"));
    driver.findElement(By.cssSelector("input[value='Log in']")).click();
  }
  
  public boolean logIn(String user, String password) {
    driver.get(properties.getProperty("portal.homepage"));
    driver.findElement(By.id("AccountCode")).clear();
    driver.findElement(By.id("AccountCode")).click();
    driver.findElement(By.id("AccountCode")).sendKeys(properties.getProperty("portal.accountcode"));
    driver.findElement(By.id("UserName")).clear();
    driver.findElement(By.id("UserName")).click();
    driver.findElement(By.id("UserName")).sendKeys(user);
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).sendKeys(password);
    driver.findElement(By.cssSelector("input[value='Log in']")).click();
    
    if (driver.getCurrentUrl().equals("https://acumatica-staged.scancocloud.com/")) {
      return false;
    } else {
      String welcome = driver.findElement(By.cssSelector("#logoutForm div")).getText();
      return welcome.equals("Hello " + user + "!");
    }
    
    
  }
  
  public void logOut() {
    driver.findElement(By.linkText("Log off")).click();
  }
  
  public void addNewUser(UserData user) {
    driver.findElement(By.linkText("NEW")).click();
    //Need to use JS because after sendKeys not all text is in form
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("$('div#AddManyModalParsh + div #Login').attr('value', '" + user.getLogin() + "');" +
            "$('div#AddManyModalParsh + div #Name').attr('value', '" + user.getName() + "');" +
            "$('div#AddManyModalParsh + div #Email').attr('value', '" + user.getEmail() + "');" +
            "$('div#AddManyModalParsh + div #Password').attr('value', '" + user.getPassword() + "');" +
            "$('div#AddManyModalParsh + div #ConfirmPassword').attr('value', '" + user.getPassword() + "');");
    
    js.executeScript("$('div#AddManyModalParsh + div input[type=submit]').click();");
    
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div #Login")).sendKeys(user.getLogin());
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div #Name")).sendKeys(user.getName());
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div #Email")).sendKeys(user.getEmail());
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div #Password")).sendKeys(user.getPassword());
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div #ConfirmPassword")).sendKeys(user.getPassword());
    //driver.findElement(By.cssSelector("div#AddManyModalParsh + div input[type=submit]")).click();
  }
  
  public Set<UserData> getAllUsers() {
    Connection conn;
    Set<UserData> users = null;
    try {
      
      conn = DriverManager.getConnection(properties.getProperty("mysql.url") + ":" + properties.getProperty("mysql.port")
              + "/" + properties.getProperty("mysql.db") + "?user=" + properties.getProperty("mysql.user")
              + "&password=" + properties.getProperty("mysql.userpass"));
      
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT LoginId, FullName, Email FROM tdcUser");
      users = new HashSet<>();
      while (rs.next()) {
        users.add(new UserData().withLogin(rs.getString("LoginId")).withName(rs.getString("FullName"))
                .withEmail(rs.getString("Email")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }
  
  public void outputSet(Set<UserData> users) {
    System.out.println("Output set:");
    for (UserData u : users) {
      System.out.println(u);
    }
  }
  
  
}