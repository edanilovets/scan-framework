package com.scan.framework.customerportal.sandbox;

import com.scan.framework.customerportal.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class IteratorListTest {
  public static void main(String[] args) {
    
    List<UserData> users = new ArrayList<>();
    users.add(new UserData().withName("Eugene").withLogin("11").withEmail("eugene@scanco.com").withPassword("111111"));
    users.add(new UserData().withName("Anna").withLogin("22").withEmail("anna@scanco.com").withPassword("222222"));
    users.add(new UserData().withName("Antony").withLogin("33").withEmail("antony@scanco.com").withPassword("333333"));
    users.add(new UserData().withName("Gleb").withLogin("44").withEmail("gleb@scanco.com").withPassword("444444"));
    users.add(new UserData().withName("Katrin").withLogin("55").withEmail("katrin@scanco.com").withPassword("555555"));
  
    for (UserData user : users) {
      System.out.println(user);
    }
 
  }
  
}
