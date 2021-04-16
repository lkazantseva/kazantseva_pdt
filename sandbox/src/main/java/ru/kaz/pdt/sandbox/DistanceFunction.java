package ru.kaz.pdt.sandbox;

public class DistanceFunction {
  public static void main(String[] args) {
    double p1 = 0;
    double p2 = 1;
    double p3 = 2;
    double p4 = -2;
    System.out.println("Расстояние между двумя точками" + "=" + distance(p1,p2,p3,p4));
  }
  public static double distance(double p1, double p2, double p3, double p4) {
    return Math.sqrt((p3-p1)*(p3-p1)+(p4-p2)*(p4-p2));
  }
}

