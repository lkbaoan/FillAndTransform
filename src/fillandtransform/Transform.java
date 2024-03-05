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

//public class Transform implements Comparable<Transform> {
public class Transform {

    char type;
    double[] coordinate;

    public Transform(double c1, double c2) {
        type = 't';
        coordinate = new double[2];
        coordinate[0] = c1;
        coordinate[1] = c2;
    }

    public Transform(double c1, double c2, double c3) {
        type = 'r';
        coordinate = new double[3];
        coordinate[0] = c1;
        coordinate[1] = c2;
        coordinate[2] = c3;
    }

    public Transform(double c1, double c2, double c3, double c4) {
        type = 's';
        coordinate = new double[4];
        coordinate[0] = c1;
        coordinate[1] = c2;
        coordinate[2] = c3;
        coordinate[3] = c4;
    }

    public char getType() {
        return type;
    }

    public double[] getCoordinate() {
        return coordinate;
    }

//    @Override
//    public int compareTo(Transform t) {
//        int val = 0;
//        switch (type) {
//            case 't':
//                if (t.type != 't') {
//                    val = 1;
//                }
//                break;
//            case 'r':
//                if (t.type == 't') {
//                    val = -1;
//                } else if (t.type == 's') {
//                    val = 1;
//                }
//                break;
//            case 's':
//                if (t.type != 's') {
//                    val = -1;
//                }
//        }
//        return val;
//    }
}
