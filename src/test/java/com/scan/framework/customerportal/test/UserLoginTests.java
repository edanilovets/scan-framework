package com.scan.framework.customerportal.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scan.framework.customerportal.model.UserData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class UserLoginTests extends TestBase{
  
  @Test (dataProvider = "usersProviderJSON")
  public void userLoginTest(UserData user){
    Assert.assertTrue(app.logIn(user.getName(), user.getPassword()));
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
