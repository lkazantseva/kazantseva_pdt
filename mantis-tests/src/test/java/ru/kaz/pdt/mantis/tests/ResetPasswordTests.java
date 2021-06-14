package ru.kaz.pdt.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaz.pdt.mantis.model.UserData;
import ru.kaz.pdt.mantis.model.Users;
import ru.kaz.pdt.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {
  private int maxId;

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetPassword() throws Exception {
    long now = System.currentTimeMillis();
    String newPassword = "Password" + now;

    int i = 0;
    Users contactList = app.db().users();

    UserData selectUser = new UserData();
    for (UserData contact : contactList) {
      if (!(contact.getUsername().equals("administrator"))) {
        selectUser = contact;
        break;
      }
      i += 1;
      if (i == contactList.size()) {
        String email = String.format("user%s@localhost", now);
        String newUser = String.format("user%s", now);
        String password = "password";
        app.registration().start(newUser, email);
        List<MailMessage> mailRegisterMessages = app.mail().waitForMail(2, 10000);
        MailMessage mailMessage = mailRegisterMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        String confirmationLink = findConfirmationLink(mailMessage, email);
        app.registration().finish(confirmationLink, newUser, password);
        Users contactListAfter = app.db().users();
        for (UserData eachUser : contactListAfter) {
          if (eachUser.getId() > maxId) {
            selectUser = eachUser;
          }
        }
      }
    }

    String admin = "administrator";
    String password = "root";
    app.adminHelper().loginInUi(admin, password);
    app.adminHelper().goToManagerUsers();
    app.adminHelper().managerResetPassword(selectUser);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    MailMessage mailMessage = mailMessages.get(mailMessages.size() - 1);
    String confirmationLink = findConfirmationLink(mailMessage, selectUser.getEmail());

    app.adminHelper().changePassword(confirmationLink, selectUser.getUsername(), newPassword);
    assertTrue(app.newSession().login(selectUser.getUsername(), newPassword));
  }

  private String findConfirmationLink(MailMessage mailMessage, String email) {
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
