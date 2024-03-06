/** *************************************************************
 * file: BasicDraw.java
 * author: A. Le
 * class: CS 4450 – Computer Graphics
 *
 * assignment: program 2
 * date last modified: 03/06/2024
 *
 * purpose: This program accept a file as an argument as the input then draw line, shape,
 * color based on the value from the file.
 *
 *************************************************************** */
package fillandtransform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class FillAndTransform {

    final int HEIGHT = 480;
    final int WIDTH = 640;
    final int CENTER_X = WIDTH / 2;
    final int CENTER_Y = HEIGHT / 2;

    /**
     * Method:main
     * Purpose: to read file and start the program.
     *
     * @param args un-used.
     */
    public static void main(String[] args) {
        FillAndTransform program = new FillAndTransform();
        List<Polygon> drawList = new ArrayList<>();

        // read line from file to arraylist and counting type of command.
        try {
            // Open file from current project directory
            File file = new File("./src/fillandtransform/coordinates.txt");
            Scanner read = new Scanner(file);
            String str;
            Polygon poly = new Polygon();
            boolean first = true;
            Transform trans;
            boolean firstEdge = true;
            float firstX = 0, firstY = 0;
            float prevx = 0, prevy = 0;
            // Read until end of file
            while (read.hasNextLine()) {
                str = read.nextLine();
                String[] splitted = str.trim().split("\\s+");
                float x, y;
                // Add to edges 
                if (isNumeric(splitted[0])) {
                    if (firstEdge) {
                        firstEdge = false;
                        firstX = Float.parseFloat(splitted[0]);
                        firstY = Float.parseFloat(splitted[1]);
                        prevx = firstX;
                        prevy = firstY;
                    } else {
                        x = Float.parseFloat(splitted[0]);
                        y = Float.parseFloat(splitted[1]);
                        poly.addEdge(prevx, prevy, x, y);
                        prevx = x;
                        prevy = y;
                    }

                } else if (splitted[0].equals("T")) {
                    poly.addEdge(prevx, prevy, firstX, firstY);
                    firstEdge = true;
                } // Set color
                else if (splitted[0].equals("P")) {
                    if (first) {
                        first = false;
                    } else {
                        drawList.add(poly);
                    }
                    poly = new Polygon();

                    poly.setColor(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
                } // Add to rotate
                else if (splitted[0].equals("r")) {
                    trans = new Transform(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
                    poly.addTransformation(trans);
                } // Add to scale
                else if (splitted[0].equals("s")) {
                    trans = new Transform(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]), Float.parseFloat(splitted[4]));
                    poly.addTransformation(trans);
                } // Add to translate
                else if (splitted[0].equals("t")) {
                    trans = new Transform(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]));
                    poly.addTransformation(trans);
                }

            }
            drawList.add(poly);
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // start program with list of command
        program.start(drawList);

    }

    /**
     * Method: isNumeric
     * Purpose: Check if string is a number.
     *
     * @param str string to be evaluated.
     * @return True if it is a number, False otherwise.
     */
    private static boolean isNumeric(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method: scanLine
     * Purpose: Calculate the coordinate for the polygon using scan line method.
     *
     * @param poly Polygon to be calculated.
     * @return A list of coordinates to be rendered.
     */
    private List<Float[]> scanLine(Polygon poly) {
        List<Float[]> returnCoordinate = new ArrayList<>();
        List<Edge> globalEdge = initializeGlobalEdge(poly);

        List<Edge> activeEdge = new ArrayList<>();
        List<Float> intersection = new ArrayList<>();

        float yMin = poly.getYMin();
        float yMax = poly.getYMax();

        for (float scanLine = yMin; scanLine <= yMax; scanLine++) {
            activeEdge = activeEdge(activeEdge, globalEdge, scanLine);
            intersection = getIntersection(intersection, activeEdge, scanLine);

            for (int i = 0; i < intersection.size(); i += 2) {
                float x1 = intersection.get(i);
                if (i + 1 >= intersection.size()) {
                    break;
                }
                float x2 = intersection.get(i + 1);
                for (float j = x1; j < x2; j++) {
                    Float[] xy = {j, scanLine};
                    returnCoordinate.add(xy);
                }
            }
        }
        return returnCoordinate;
    }

    /**
     * Method: getIntersection
     * Purpose: Get a list of value that the scan line cross with the vertices.
     *
     * @param intersection list of old intersection.
     * @param activeEdge list of active edges.
     * @param currentY current scan line y coordinate.
     * @return A list of new x coordinates the scan line cross with vertices.
     */
    private List<Float> getIntersection(List<Float> intersection, List<Edge> activeEdge, float currentY) {
        intersection.clear();

        for (Edge e : activeEdge) {
            float x = (float) (e.getAssociateX() + ((currentY - e.getMinY()) * e.get1OverM()));
            intersection.add(x);
        }
        Collections.sort(intersection);
        return intersection;
    }

    /**
     * Method: activeEdge
     * Purpose: Get a list of active edge that the scan line currently cross.
     *
     * @param activeEdges list of current active edge.
     * @param globalEdge list of global edge.
     * @param y scan line coordinate.
     * @return a new list of active edge.
     */
    private List<Edge> activeEdge(List<Edge> activeEdges, List<Edge> globalEdge, float y) {
        for (Edge e : globalEdge) {
            if (isActive(e, y)) {
                if (!activeEdges.contains(e)) {
                    activeEdges.add(e);
                }
            } else {
                activeEdges.remove(e);
            }
        }
        return activeEdges;
    }

    /**
     * Method: isActive
     * Purpose: Check if the edge is active.
     *
     * @param edge to be checked.
     * @param currentY scan line y coordinate.
     * @return True if edge is active, False otherwise.
     */
    private boolean isActive(Edge edge, float currentY) {
        return edge.getMinY() < currentY && currentY <= edge.getMaxY();
    }

    /**
     * Method: initializeGlobalEdge
     * Purpose: Initialize the global edge sorted.
     *
     * @param poly polygon with that contain all edges.
     * @return A list of sorted edge.
     */
    private List<Edge> initializeGlobalEdge(Polygon poly) {
        List<Edge> allEdge = poly.getEdges();
        Collections.sort(allEdge);
        for (int i = 0; i < allEdge.size(); i++) {
            try {
                if (Double.isInfinite(allEdge.get(i).get1OverM())) {
                    allEdge.remove(i);
                }
            } catch (ArithmeticException e) {
            }
        }
        return allEdge;
    }

    /**
     * Method: Start
     * Purpose: Start a new window and render graphics.
     *
     * @param drawList list of polygon to be rendered.
     */
    public void start(List<Polygon> drawList) {
        try {
            createWindow();
            initGL();
            render(drawList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: createWindow
     * Purpose: create a new window display with set size
     * and title.
     *
     * @throws Exception if fail to create window.
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.setTitle("Program 2");
        Display.create();
    }

    /**
     * Method: initGL
     * Purpose: initialize openGL task.
     */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // Set center coordinate at the center of the window.
        glOrtho(-CENTER_X, CENTER_X, -CENTER_Y, CENTER_Y, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    /**
     * Method: render
     * Purpose: render graphics based on polygon.
     *
     * @param drawList list of polygon to be rendered.
     */
    private void render(List<Polygon> drawList) {
        glColor3f(1.0f, 1.0f, 0.0f);
        glPointSize(2);

        // Calculate coordinate to be rendered.
        List<List<Float[]>> coordinates = new ArrayList<>();
        for (int i = 0; i < drawList.size(); i++) {
            coordinates.add(scanLine(drawList.get(i)));
        }
        // close when press "Q" or click close window
        while (!Keyboard.isKeyDown(Keyboard.KEY_Q) && !Display.isCloseRequested()) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                for (int j = 0; j < coordinates.size(); j++) {
                    Polygon p = drawList.get(j);

                    glPushMatrix();
                    glColor3f(p.getRed(), p.getGreen(), p.getBlue());
                    applyTransformation(p.getTransformation());
                    drawPoints(coordinates.get(j));
                    glPopMatrix();
                }
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
            }
        }
        Display.destroy();
    }

    /**
     * Method: drawPoints
     * Purpose: render the points on screen.
     *
     * @param coordinates list of coordinate to be rendered.
     */
    private void drawPoints(List<Float[]> coordinates) {
        glBegin(GL_POINTS);
        for (int i = 0; i < coordinates.size(); i++) {
            glVertex2f(coordinates.get(i)[0], coordinates.get(i)[1]);
        }
        glEnd();
    }

    /**
     * Method: applyTransformation
     * Purpose: apply transformation to of the
     * polygon.
     *
     * @param transform stack transformation information.
     */
    private void applyTransformation(Stack<Transform> transform) {
        Stack<Transform> stack = (Stack<Transform>) transform.clone();
        while (!stack.empty()) {
            Transform trans = stack.pop();
            switch (trans.type) {
                case 't' ->
                    glTranslatef((float) trans.getTransformInfo()[0], (float) trans.getTransformInfo()[1], 0);
                case 'r' ->
                    glRotatef((float) trans.getTransformInfo()[0], (float) trans.getTransformInfo()[1], (float) trans.getTransformInfo()[2], 1);
                case 's' ->
                    glScalef((float) trans.getTransformInfo()[0], (float) trans.getTransformInfo()[1], 0);
            }
        }
    }
}
