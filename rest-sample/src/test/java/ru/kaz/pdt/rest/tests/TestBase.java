package ru.kaz.pdt.rest.tests;

import com.solidfire.gson.Gson;
import com.solidfire.gson.JsonElement;
import com.solidfire.gson.JsonParser;
import com.solidfire.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Set;

public class TestBase {
  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.stop();
  }

  private boolean  isIssueOpen(int issueId) throws IOException {
    String issueJson = getExecutor().execute(Request
            .Get(app.getProperty("web.baseURL") + "/" + issueId + ".json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(issueJson);
    JsonElement issue = parsed.getAsJsonObject().get("issues");
    Set<Issue> newIssue = new Gson().fromJson(issue, new TypeToken<Set<Issue>>() {
    }.getType());
    String status = newIssue.stream().map((p) -> (p).getStatus()).findFirst().get().toString();
    System.out.println(status);
    return ! status.equals("Closed") && ! status.equals("Resolved");
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}

