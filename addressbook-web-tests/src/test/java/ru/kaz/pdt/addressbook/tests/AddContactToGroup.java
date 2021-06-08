package ru.kaz.pdt.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.Contacts;
import ru.kaz.pdt.addressbook.model.GroupData;
import ru.kaz.pdt.addressbook.model.Groups;

import java.util.Iterator;

public class AddContactToGroup extends TestBase {

  private ContactData getContactWithoutGroup() {
    for (Iterator<ContactData> it = app.db().contacts().iterator(); it.hasNext(); ) {
      ContactData contact = it.next();
      if (contact.getGroups().size() == 0) {
        return contact;
      }
    }
    return null;
  }


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1"));
    }
    if (getContactWithoutGroup() == null) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89094567898")
              .withAddress("Moscow").withEmail("ivanovivan@yandex.ru"), true);
    }
  }

  @Test
  public void addContactToGroupTest() {
    GroupData modifiedGroup = app.db().groups().iterator().next();
    ContactData modifiedContact = getContactWithoutGroup();
    app.goTo().homePage();
    app.contact().selectContactById(modifiedContact.getId());
    app.contact().selectGroupForAddingToContact(modifiedGroup.getId());
    app.contact().addContactToGroup();
    Groups groupBefore = modifiedContact.ActionsWithGroup(modifiedGroup, true).getGroups();
    Contacts getContactListAfter = app.db().contacts();
    int givenId = modifiedContact.getId();
    Groups newGroupsList = getContactListAfter.stream().filter(c -> c.getId() == givenId).findFirst().get().getGroups();
    MatcherAssert.assertThat(groupBefore, CoreMatchers.equalTo(newGroupsList));
  }

}