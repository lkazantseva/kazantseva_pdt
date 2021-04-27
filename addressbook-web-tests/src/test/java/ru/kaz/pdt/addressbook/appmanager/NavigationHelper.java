package ru.kaz.pdt.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends BaseHelper {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    }
    click(By.linkText("groups"));
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id ("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void gotoAddNewContactPage() {
    if (isElementPresent(By.id("content"))
            && isElementPresent(By.name("theform"))) {
      return;
    }
    click(By.linkText("add new"));
  }
}
