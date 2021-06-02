package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.Contacts;
import ru.kaz.pdt.addressbook.model.GroupData;
import ru.kaz.pdt.addressbook.model.Groups;

public class RemoveContactFromGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditionsForRemoveContactFromGroupTests(ContactData contact) {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1"));
    }
    if (app.db().contacts().size() == 0) {
      contact.inGroup(groups.iterator().next());
      app.goTo().addNewContactPage();
      app.contact().create(contact, true);
    }

  }
}