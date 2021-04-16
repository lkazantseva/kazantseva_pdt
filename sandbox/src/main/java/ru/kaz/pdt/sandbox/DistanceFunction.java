package ru.kaz.pdt.sandbox;

public class DistanceFunction {
  public static void main(String[] args) {
    Point P = new Point(2,3,6,8);

    System.out.println("Расстояние между двумя точками " + "=" + distance(P));
  }
  public static double distance(Point P) {
    return Math.sqrt((P.p3-P.p1)*(P.p3-P.p1)+(P.p4-P.p2)*(P.p4-P.p2));
  }
}
