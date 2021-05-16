package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.addressbook.model.ContactData;
import ru.kaz.pdt.addressbook.model.Contacts;
import ru.kaz.pdt.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditionsForCreationTests() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
    app.goTo().homePage();
  }

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    app.goTo().addNewContactPage();
    File photo = new File("src/test/resources/pdt.png");
    ContactData contact = new ContactData()
            .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89094567898")
            .withAddress("г. Москва, ул. Тверская, д. 5").withEmail("ivanovivan@yandex.ru")
            .withPhoto(photo).withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo
            (before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all();
    app.goTo().addNewContactPage();
    ContactData contact = new ContactData()
            .withFirstname("Ivan'").withLastname("Ivanov'").withMobilePhone("8909456789t").withEmail("ivanovivan@yandex.ru").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
