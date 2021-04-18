package ru.kaz.pdt.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test

  public void testDistance() {
    Point P = new Point (2,1,2,6);
    Assert.assertEquals(P.distance(), 5.0);
  }
}
