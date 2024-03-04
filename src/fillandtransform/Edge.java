/** *************************************************************
 * file: BasicDraw.java
 * author: A. Le
 * class: CS 4450 – Computer Graphics
 *
 * assignment: program 2
 * date last modified: 03/06/2024
 *
 * purpose:
 *
 *************************************************************** */
// TODO: Fill comment
package fillandtransform;

public class Edge implements Comparable<Edge> {

    private int x1, y1;
    private int x2, y2;
    private int dx, dy;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        dx = x2 - x1;
        dy = y2 - y1;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public int getDX() {
        return dx;
    }

    public int getDY() {
        return dy;
    }

    public int getMinY() {
        return y2 > y1 ? y1 : y2;
    }

    public int getMaxY() {
        return y2 >= y1 ? y2 : y1;
    }

    public int getAssociateX() {
        return y2 > y1 ? x1 : x2;
    }

    public double getSlope() {
        return ((double) dy / (double) dx);
    }

    public double get1OverM() {
        return ((double) dx / (double) dy);
    }

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
