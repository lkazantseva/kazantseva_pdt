package ru.kaz.pdt.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditionsForDeletionTests() {
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData("Ivan", "Ivanov", "89094567898", "ivanovivan@yandex.ru", "test1"), true);
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);
  }
}
