package ru.kaz.pdt.sandbox;

public class DistanceFunction {
  public static void main(String[] args) {
    Point P1 = new Point (0,1);
    Point P2 = new Point(2, -2);
    System.out.println("Расстояние между двумя точками(" + P1.x + "," + P1.y + ") и" + "(" + P2.x + ", " + P2.y + ") =" + P1.distance(P2));
  }
  }

