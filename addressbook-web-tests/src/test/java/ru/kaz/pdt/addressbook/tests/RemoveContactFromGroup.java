package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.Contacts;
import ru.kaz.pdt.addressbook.model.GroupData;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroup extends TestBase {

  private ContactData getContactWithGroup() {
    for (Iterator<ContactData> it = app.db().contacts().iterator(); it.hasNext(); ) {
      ContactData contact = it.next();
      if (contact.getGroups().size() != 0) {
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
    if (getContactWithGroup() == null) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89094567898")
              .withAddress("Moscow").withEmail("ivanovivan@yandex.ru").inGroup(app.db().groups().iterator().next()), true);
    }
  }

  @Test
  public void addContactToGroupTest() {
    ContactData modifiedContact = getContactWithGroup();
    GroupData modifiedGroup = modifiedContact.getGroups().iterator().next();
    Contacts getContactListBefore = app.db().contacts().without(modifiedContact);
    app.goTo().homePage();
    app.contact().selectGroupForContacts(modifiedGroup.getId());
    app.contact().selectContactById(modifiedContact.getId());
    app.contact().deleteGroupForContact();
    Contacts getContactListAfter = app.db().contacts();
    assertThat(getContactListAfter, equalTo(getContactListBefore.withAdded(modifiedContact.withoutGroup(modifiedGroup))));
  }

}