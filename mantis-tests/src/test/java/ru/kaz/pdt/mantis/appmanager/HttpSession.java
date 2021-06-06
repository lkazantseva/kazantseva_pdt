package ru.kaz.pdt.mantis.appmanager;

import org.omg.CORBA.NameValuePair;
import org.openqa.selenium.remote.http.HttpClient;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }

  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
    List<NameValuePair> params = new ArrayList<>();
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params));
    CloseableHttpResponse response = httpclient.execute(post);
    String body = getTextFrom(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));
  }
  private String getTextFrom(CloseableHttpResponse response) {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
    CloseableHttpResponse response = httpclient.execute(get);
    String body = getTextFrom(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));

  }


}
