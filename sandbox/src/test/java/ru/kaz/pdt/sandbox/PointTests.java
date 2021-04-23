package ru.kaz.pdt.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance() {
    Point P1 = new Point (0,1);
    Point P2 = new Point(2, -2);

    Assert.assertEquals(P1.distance(P2), 3.605551275463989);
  }
}
