package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.gotoAddNewContactPage();
    app.fillContactForm(new ContactData("Ivan", "Ivanov", "89094567898", "ivanovivan@yandex.ru"));
    app.submitContactCreation();
    app.returnToHomePage();
  }

}
