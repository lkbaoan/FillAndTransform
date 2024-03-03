/** *************************************************************
 * file: polygon.java
 * author: A. Le
 * class: CS 4450 – Computer Graphics
 *
 * assignment: program 2
 * date last modified: 03/06/2024
 *
 * purpose: This file create an object that hold information of the polygon as
 * well as its transformation.
 *
 *************************************************************** */
package fillandtransform;

import java.util.ArrayList;
import java.util.Iterator;

public class Polygon {

    float red, green, blue;
    ArrayList<Integer[]> vertices;
    ArrayList<Transform> transformation;

    public Polygon() {
        red = 0.0f;
        green = 0.0f;
        blue = 0.0f;
        vertices = new ArrayList<>();
        transformation = new ArrayList<>();
    }

    // setter and getter for color
    public void setColor(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public void addVertice(int v1, int v2) {
        Integer[] v = {v1, v2};
        vertices.add(v);
    }

    public Iterator<Integer[]> getVertices() {
        return vertices.iterator();
    }

    public void addTransformation(Transform trans) {
        transformation.add(trans);
    }

    public Iterator<Transform> getTransformation() {
        return transformation.iterator();
    }
}
