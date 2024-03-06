/** *************************************************************
 * file: Polygon.java
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
import java.util.List;
import java.util.Stack;

public class Polygon {

    private float red, green, blue;
    private List<Float[]> vertices;
    private Stack<Transform> transformation;
    private List<Edge> edges;

    /**
     * Method: Polygon
     * Purpose: Constructor for Polygon.
     */
    public Polygon() {
        red = 0.0f;
        green = 0.0f;
        blue = 0.0f;
        vertices = new ArrayList<>();
        transformation = new Stack<>();
        edges = new ArrayList<>();
    }

    /**
     * Method: setColor
     * Purpose: Set color for polygon color.
     *
     * @param r red.
     * @param g green.
     * @param b blue.
     */
    public void setColor(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    /**
     * Method: getRed
     * Purpose: Getter for polygon color.
     *
     * @return color red.
     */
    public float getRed() {
        return red;
    }

    /**
     * Method: getGreen
     * Purpose: Getter for polygon color.
     *
     * @return color green.
     */
    public float getGreen() {
        return green;
    }

    /**
     * Method: getBlue
     * Purpose: Getter for polygon color.
     *
     * @return color blue.
     */
    public float getBlue() {
        return blue;
    }

    /**
     * Method: addEdge
     * Purpose: Add edge between two points.
     *
     * @param x1 x of the first point.
     * @param y1 y of the first point.
     * @param x2 x of the second point.
     * @param y2 y of the second point.
     */
    public void addEdge(float x1, float y1, float x2, float y2) {
        Edge edge = new Edge(x1, y1, x2, y2);
        edges.add(edge);
    }

    /**
     * Method: getEdges
     * Purpose: Get a list of all the edges in the polygon.
     *
     * @return list of edges.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Method: getYMin
     * Purpose: Get the lowest y value in the polygon.
     *
     * @return minimum y value.
     */
    public float getYMin() {
        float yMin = edges.get(0).getMinY();
        for (Edge edge : edges) {
            if (yMin > edge.getMinY()) {
                yMin = edge.getMinY();
            }
        }
        return yMin;
    }

    /**
     * Method: getYMax
     * Purpose: Get the highest y value in the polygon.
     *
     * @return maximum y value.
     */
    public float getYMax() {
        float yMax = edges.get(0).getMaxY();
        for (Edge edge : edges) {
            if (yMax < edge.getMaxY()) {
                yMax = edge.getMaxY();
            }
        }
        return yMax;
    }

    /**
     * Method: addTransformation
     * Purpose: Add transformation information to the polygon.
     *
     * @param trans information of the transformation.
     */
    public void addTransformation(Transform trans) {
        transformation.push(trans);
    }

    /**
     * Method:getTransformation
     * Purpose: Return a stack of the transformation information.
     *
     * @return stack of transformation.
     */
    public Stack<Transform> getTransformation() {
        return transformation;
    }
}
