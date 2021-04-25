package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Petr", "Petrov", "89097865555", "petrpetrov@yandex.ru"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }

}
