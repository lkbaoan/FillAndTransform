/** *************************************************************
 * file: Edge.java
 * author: A. Le
 * class: CS 4450 – Computer Graphics
 *
 * assignment: program 2
 * date last modified: 03/06/2024
 *
 * purpose: the file serve as an edge that connect 2 point.
 *
 *************************************************************** */
package fillandtransform;

public class Edge implements Comparable<Edge> {

    private float x1, y1;
    private float x2, y2;
    private float dx, dy;
    private float associateX;

    /**
     * Method: Edge
     * Purpose: Constructor for Edge.
     *
     * @param x1 x coordinate for first point.
     * @param y1 y coordinate for first point.
     * @param x2 x coordinate for second point.
     * @param y2 y coordinate for second point.
     */
    public Edge(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        dx = x2 - x1;
        dy = y2 - y1;

        associateX = y2 > y1 ? x1 : x2;
    }

    /**
     * Method: getX1
     * Purpose: Getter for x of first point.
     *
     * @return float value of x first point.
     */
    public float getX1() {
        return x1;
    }

    /**
     * Method: getX2
     * Purpose: Getter for x of second point.
     *
     * @return float value of x second point.
     */
    public float getX2() {
        return x2;
    }

    /**
     * Method: getY1
     * Purpose: Getter for y of first point.
     *
     * @return float value of y first point.
     */
    public float getY1() {
        return y1;
    }

    /**
     * Method: getY2
     * Purpose: Getter for y of second point.
     *
     * @return float value of y second point.
     */
    public float getY2() {
        return y2;
    }

    /**
     * Method: getDX
     * Purpose: Getter for different of x first point and x second point.
     *
     * @return float value of dx.
     */
    public float getDX() {
        return dx;
    }

    /**
     * Method: getDY
     * Purpose: Getter for different of y first point and y second point.
     *
     * @return float value of dy.
     */
    public float getDY() {
        return dy;
    }

    /**
     * Method: getMinY
     * Purpose: Return minimum of two y values of the two points.
     *
     * @return float value of minimum y.
     */
    public float getMinY() {
        return y2 > y1 ? y1 : y2;
    }

    /**
     * Method: getMaxY
     * Purpose: Return maximum of two y value of the two points.
     *
     * @return float value of maximum y.
     */
    public float getMaxY() {
        return y2 > y1 ? y2 : y1;
    }

    /**
     * Method: getAssociateX
     * Purpose: Return x value associate with the minimum y.
     *
     * @return float value of x.
     */
    public float getAssociateX() {
        return associateX;
    }

    /**
     * Method: getSlope
     * Purpose: Return the slope of the edge.
     *
     * @return double value of the slope.
     */
    public double getSlope() {
        return ((double) dy / (double) dx);
    }

    /**
     * Method: get1OverM
     * Purpose: Return 1/slope of the edge.
     *
     * @return double value of the 1/slope.
     */
    public double get1OverM() {
        return ((double) dx / (double) dy);
    }

    /**
     * Method: compareTo
     * Purpose: Compare to two edge together.
     *
     * @param e Edge to be compared to.
     * @return integer. 0: equal, 1: bigger, -1: smaller.
     */
    @Override
    public int compareTo(Edge e) {
        int val = 0;
        if (getMinY() > e.getMinY()) {
            val = 1;
        } else if (getMinY() < e.getMinY()) {
            val = -1;
        } else {
            if (getAssociateX() > e.getAssociateX()) {
                val = 1;
            } else {
                val = -1;
            }
        }
        return val;
    }
}
