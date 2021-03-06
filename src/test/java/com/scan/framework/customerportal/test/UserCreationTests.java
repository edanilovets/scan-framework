package com.scan.framework.customerportal.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scan.framework.customerportal.model.UserData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



public class UserCreationTests extends TestBase {
  
  @BeforeClass
  public void appLogin(){
    app.logInAsAdmin();
  }
  
  @Test
  public void singleUserCreationTest() {

    UserData newUser  = new UserData().withLogin("94").withName("Eugene94").withEmail("eugene@scanco.com").withPassword("111111");

    app.navigate().usersPage();
    Set<UserData> beforeAdd = app.getAllUsers();
    app.addNewUser(newUser);
    Set<UserData> afterAdd = app.getAllUsers();
    beforeAdd.add(newUser);
    
    //Compares 3 fields: Login, Name, Email
    Assert.assertEquals(beforeAdd, afterAdd);
    
    //app.outputSet(beforeAdd);
    //app.outputSet(afterAdd);
  }
  
  @Test (dataProvider = "usersProviderJSON")
  public void multipleUsersCreationTest(UserData user){
    
    app.navigate().usersPage();
    Set<UserData> beforeAdd = app.getAllUsers();
    app.addNewUser(user);
    Set<UserData> afterAdd = app.getAllUsers();
    beforeAdd.add(user);
  
    //Compares 3 fields: Login, Name, Email
    Assert.assertEquals(beforeAdd, afterAdd);
  }
  
  @DataProvider
  public Iterator<Object[]> usersProviderJSON() throws IOException {
  
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.json")));
    StringBuilder json = new StringBuilder();
    String line = reader.readLine();
    while (line != null) {
      json.append(line);
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<UserData> users = gson.fromJson(json.toString(), new TypeToken<List<UserData>>(){}.getType());
    return users.stream().map((u) -> new Object[] {u}).collect(Collectors.toList()).iterator();
  }
  
}
