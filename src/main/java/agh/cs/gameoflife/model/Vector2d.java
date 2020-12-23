package agh.cs.gameoflife.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean precedes(Vector2d other){
        return this.getX() <= other.getX() && this.getY() <= other.getY() && this.getX() <= other.getY() && this.getY() <= other.getX();
    }

    boolean follows(Vector2d other){
        return this.getX() >= other.getX() && this.getY() >= other.getY() && this.getX() >= other.getY() && this.getY() >= other.getX();
    }
    Vector2d upperRight(Vector2d other){
        int x = Math.max(this.getX(), other.getX());
        int y = Math.max(this.getY(), other.getY());
        return new Vector2d(x, y);
    }

    Vector2d lowerLeft(Vector2d other){
        int x = Math.min(this.getX(), other.getX());
        int y = Math.min(this.getY(), other.getY());
        return new Vector2d(x, y);
    }

    Vector2d add(Vector2d other){
        return new Vector2d(this.getX() + other.getX(), this.getY() + other.getY());
    }

    Vector2d subtract(Vector2d other){
        return new Vector2d(this.getX() - other.getX(), this.getY() - other.getY());
    }

    Vector2d opposite(){
        return new Vector2d(-this.getX(), -this.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2d)) return false;
        Vector2d vector2d = (Vector2d) o;
        return getX() == vector2d.getX() &&
                getY() == vector2d.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
