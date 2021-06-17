package ru.kaz.pdt.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("lkazantseva", "kazantseva_pdt")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
      }
    }
  }

