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
      if (app.db().contacts().size() == 0) {
        app.goTo().addNewContactPage();
        app.contact().create(new ContactData()
                .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89094567898")
                .withAddress("Moscow").withEmail("ivanovivan@yandex.ru").inGroup(app.db().groups().iterator().next()), true);
      } else {
        app.goTo().homePage();
        app.contact().selectContactById(app.db().contacts().iterator().next().getId());
        app.contact().addContactToGroup();
      }
    }
  }

  @Test
  public void removeContactFromGroupTest() {
    ContactData modifiedContact = getContactWithGroup();
    GroupData modifiedGroup = modifiedContact.getGroups().iterator().next();
    app.goTo().homePage();
    app.contact().selectGroupForContacts(modifiedGroup.getId());
    app.contact().selectContactById(modifiedContact.getId());
    app.contact().deleteGroupForContact(modifiedContact);
    Groups groupBefore = modifiedContact.ActionsWithGroup(modifiedGroup, false).getGroups();
    Contacts getContactListAfter = app.db().contacts();
    int givenId = modifiedContact.getId();
    Groups newGroupsList = getContactListAfter.stream().filter(c -> c.getId() == givenId).findFirst().get().getGroups();
    MatcherAssert.assertThat(groupBefore, CoreMatchers.equalTo(newGroupsList));
  }
}