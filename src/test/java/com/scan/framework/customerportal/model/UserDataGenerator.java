package com.scan.framework.customerportal.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class UserDataGenerator {
  public static void main(String[] args) throws IOException {
    
    int num = 4;
    String format = "csv";
    
    List<UserData> users = generateUsers(num);
    switch (format) {
      case "csv": {
        File file = new File("src/test/resources/users.csv");
        saveAsCsv(users, file);
        break;
      }
      case "xml": {
        File file = new File("src/test/resources/users.xml");
        saveAsXml(users, file);
        break;
      }
      case "json": {
        File file = new File("src/test/resources/users.json");
        saveAsJson(users, file);
        break;
      }
      default:
        System.out.println("Unrecognized format of file" + format);
        break;
    }
    
    
  }
  
  private static void saveAsJson(List<UserData> users, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(users);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }
  
  private static void saveAsXml(List<UserData> users, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.alias("user", UserData.class);
    String xml = xstream.toXML(users);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }
  
  private static void saveAsCsv(List<UserData> users, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (UserData user : users) {
      writer.write(String.format("%s;%s;%s;%s\n", user.getLogin(), user.getName(), user.getEmail(), user.getPassword()));
    }
    writer.close();
  }
  
  private static List<UserData> generateUsers(int num) {
    List<UserData> users = new ArrayList<>();
    for (int i = 0; i < num; i++) {
        users.add(new UserData().withLogin("L" + i).withName("User" + i).withEmail("email" + i + "@mail.com").withPassword("Scan2017co!"));
    }
    return users;
  }
}
