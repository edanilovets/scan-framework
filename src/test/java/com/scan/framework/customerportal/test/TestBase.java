package com.scan.framework.customerportal.test;

import com.scan.framework.customerportal.manager.AppManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class TestBase {
  final static AppManager app = new AppManager();
  
  @BeforeClass
  public void appInit() throws IOException {
    app.init();
    app.logIn();
  }
  
  @AfterClass
  public void appStop() {
    app.stop();
  }
  
}
