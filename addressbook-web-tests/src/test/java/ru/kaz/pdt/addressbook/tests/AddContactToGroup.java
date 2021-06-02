package ru.kaz.pdt.addressbook.tests;

import org.hibernate.SessionFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.Contacts;
import ru.kaz.pdt.addressbook.model.GroupData;
import ru.kaz.pdt.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroup extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1"));
    }
    if (app.db().contacts().size() == 0) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89094567898")
              .withAddress("Moscow").withEmail("ivanovivan@yandex.ru"), true);
    }
  }

  @Test
  public void addContactToGroupTest() {
    Groups groups = app.db().groups();
    GroupData modifyGroup = new GroupData();
    modifyGroup = app.db().groups().iterator().next();
    app.db().contacts();
    Contacts getContactListBefore = app.db().contacts();
    for (ContactData contact: getContactListBefore) {
      if (contact.getGroups().size() == 0) {
        contact.inGroup(groups.iterator().next());
        app.goTo().homePage();
        app.contact().addContactToGroup(contact);
      }
      Contacts getContactListAfter = app.db().contacts();
      assertThat(getContactListAfter, equalTo(getContactListBefore.withAdded(contact.inGroup(modifyGroup))));

    }
  }
}