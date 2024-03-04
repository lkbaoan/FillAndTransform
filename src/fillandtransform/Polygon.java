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
// TODO: Fill comment
package fillandtransform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polygon {

    float red, green, blue;
    List<Integer[]> vertices;
    List<Transform> transformation;
    List<Edge> edges;

    public Polygon() {
        red = 0.0f;
        green = 0.0f;
        blue = 0.0f;
        vertices = new ArrayList<>();
        transformation = new ArrayList<>();
        edges = new ArrayList<>();
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

    public int getVerticesSize() {
        return vertices.size();
    }

    public void addEdge(int x1, int y1, int x2, int y2) {
        Edge edge = new Edge(x1, y1, x2, y2);
        edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getYMin() {
        int yMin = edges.get(0).getMinY();
        for (Edge edge : edges) {
            if (yMin > edge.getMinY()) {
                yMin = edge.getMinY();
            }
        }
        return yMin;
    }

    public int getYMax() {
        int yMax = edges.get(0).getMaxY();
        for (Edge edge : edges) {
            if (yMax < edge.getMaxY()) {
                yMax = edge.getMaxY();
            }
        }
        return yMax;
    }

    public void addTransformation(Transform trans) {
        transformation.add(trans);
    }

    public Iterator<Transform> getTransformation() {
        return transformation.iterator();
    }
}
