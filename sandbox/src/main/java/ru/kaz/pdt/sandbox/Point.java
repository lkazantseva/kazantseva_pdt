package ru.kaz.pdt.sandbox;

public class Point {
  public double x;
  public double y;


  public Point (double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point P) {
    return Math.sqrt((P.x - this.x)*(P.x-this.x)+(P.y-this.y)*(P.y-this.y));
  }
}



