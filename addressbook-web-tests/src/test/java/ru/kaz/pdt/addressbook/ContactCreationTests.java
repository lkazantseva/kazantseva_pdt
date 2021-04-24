package ru.kaz.pdt.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    gotoAddNewContactPage();
    fillContactForm(new ContactData("Ivan", "Ivanov", "89094567898", "ivanovivan@yandex.ru"));
    submitContactCreation();
    returnToHomePage();
  }

}
