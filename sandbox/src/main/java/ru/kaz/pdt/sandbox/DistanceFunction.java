package ru.kaz.pdt.sandbox;

public class DistanceFunction {
  public static void main(String[] args) {
    Point P = new Point(2,1,2,6);

    System.out.println("Расстояние между двумя точками(" + P.x1 + ","+ P.y1 + ") и (" + P.x2 + "," + P.y2 + ") =" + P.distance());
  }
}
