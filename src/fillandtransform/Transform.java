/** *************************************************************
 * file: Transform.java
 * author: A. Le
 * class: CS 4450 – Computer Graphics
 *
 * assignment: program 2
 * date last modified: 03/06/2024
 *
 * purpose: Hold information for the transformation.
 *
 *************************************************************** */
package fillandtransform;

public class Transform {

    char type;
    float[] coordinate;

    /**
     * Method: Transform
     * Purpose: Constructor when its type is translate.
     *
     * @param c1 x value to be translated.
     * @param c2 y value to be translated.
     */
    public Transform(float c1, float c2) {
        type = 't';
        coordinate = new float[2];
        coordinate[0] = c1;
        coordinate[1] = c2;
    }

    /**
     * Method: Transform
     * Purpose: Constructor when its type is rotate.
     *
     * @param c1 angle to be rotated.
     * @param c2 x value of the pivot point.
     * @param c3 y value of the pivot point.
     */
    public Transform(float c1, float c2, float c3) {
        type = 'r';
        coordinate = new float[3];
        coordinate[0] = c1;
        coordinate[1] = c2;
        coordinate[2] = c3;
    }

    /**
     * Method: Transform
     * Purpose: Constructor when its type is scale.
     *
     * @param c1 scaling factor for x.
     * @param c2 scaling factor for y.
     * @param c3 pivot point for x.
     * @param c4 pivot point for y.
     */
    public Transform(float c1, float c2, float c3, float c4) {
        type = 's';
        coordinate = new float[4];
        coordinate[0] = c1;
        coordinate[1] = c2;
        coordinate[2] = c3;
        coordinate[3] = c4;
    }

    /**
     * Method: getType
     * Purpose: Get the type of the transform.
     *
     * @return type of transform.
     */
    public char getType() {
        return type;
    }

    /**
     * Method: getTransformInfo
     * Purpose: Get the transformation information.
     *
     * @return array of transformation information
     */
    public float[] getTransformInfo() {
        return coordinate;
    }

}
