package ru.kaz.pdt.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() throws Exception {
    app.selectContactToBeDeleted();
    app.deleteSelectedContact();
    app.returnToHomePage();
  }

}
