package ru.kaz.pdt.addressbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() throws Exception {
    selectContactToBeDeleted();
    deleteSelectedContact();
    returnToHomePage();
  }

}
