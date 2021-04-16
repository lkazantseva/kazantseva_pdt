package ru.kaz.pdt.sandbox;

public class Point {
  public static void main(String[] args) {
    PointData P = new PointData(2,3,6,8);

    System.out.println("Расстояние между двумя точками " + "=" + distance(P));
  }
  public static double distance(PointData P) {
    return Math.sqrt((P.p3-P.p1)*(P.p3-P.p1)+(P.p4-P.p2)*(P.p4-P.p2));
  }
}
