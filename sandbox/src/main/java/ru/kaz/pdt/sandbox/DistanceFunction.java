package ru.kaz.pdt.sandbox;

public class DistanceFunction {
  public static void main(String[] args) {
    Point P = new Point(0,1,2,-2);

    System.out.println("Расстояние между двумя точками(" + P.x1 + ","+ P.y1 + ") и (" + P.x2 + "," + P.y2 + ") =" + P.distance());
  }
}
