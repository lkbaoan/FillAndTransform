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
// TODO: Fill comment
package fillandtransform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class FillAndTransform {

    final int HEIGHT = 480;
    final int WIDTH = 640;
    final int CENTER_X = WIDTH / 2;
    final int CENTER_Y = HEIGHT / 2;

    // method: main
    // purpose: to read file and start the program
    public static void main(String[] args) {
        FillAndTransform program = new FillAndTransform();
        List<Polygon> drawList = new ArrayList<>();

        // read line from file to arraylist and counting type of command.
        try {
            // Read file from current project directory
            File file = new File("./src/fillandtransform/coordinates.txt");
            Scanner read = new Scanner(file);
            String str;
            Polygon poly = new Polygon();
            boolean first = true;
            Transform trans;
            boolean firstEdge = true;
            int firstX = 0, firstY = 0;
            int prevx = 0, prevy = 0;
            while (read.hasNextLine()) {
                str = read.nextLine();
                String[] splitted = str.trim().split("\\s+");
                int x, y;
                System.out.println(Arrays.toString(splitted));
                if (isNumeric(splitted[0])) {
                    if (firstEdge) {
                        firstEdge = false;
                        firstX = Integer.parseInt(splitted[0]);
                        firstY = Integer.parseInt(splitted[1]);
                        prevx = firstX;
                        prevy = firstY;
                    } else {
                        x = Integer.parseInt(splitted[0]);
                        y = Integer.parseInt(splitted[1]);
                        poly.addEdge(prevx, prevy, x, y);
                        prevx = x;
                        prevy = y;
                    }
                    poly.addVertice(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));

                } else if (splitted[0].equals("T")) {
                    poly.addEdge(prevx, prevy, firstX, firstY);
                    firstEdge = true;
                } else if (splitted[0].equals("P")) {
                    if (first) {
                        first = false;
                    } else {
                        drawList.add(poly);
                    }
                    poly = new Polygon();

                    poly.setColor(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
                } else if (splitted[0].equals("r")) {
                    trans = new Transform(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]), Double.parseDouble(splitted[3]));
                    poly.addTransformation(trans);
                } else if (splitted[0].equals("s")) {
                    trans = new Transform(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]), Double.parseDouble(splitted[3]), Double.parseDouble(splitted[4]));
                    poly.addTransformation(trans);
                } else if (splitted[0].equals("t")) {
                    trans = new Transform(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]));
                    poly.addTransformation(trans);
                }

            }
            drawList.add(poly);
            read.close();
            System.out.println(drawList.size());
            Iterator<Polygon> ite = drawList.iterator();
            int i = 0;
            while (ite.hasNext()) {
                Polygon p = ite.next();
                System.out.printf("Color %f %f %f\n", p.getRed(), p.getGreen(), p.getBlue());
                Iterator<Edge> iv = p.getEdges().iterator();
                while (iv.hasNext()) {
//                    System.out.println(Arrays.toString(iv.next()));
                    Edge e = iv.next();
                    System.out.printf("(%d,%d) (%d,%d)\n", e.getX1(), e.getY1(), e.getX2(), e.getY2());
                }
                Iterator<Transform> it = p.getTransformation().iterator();
                while (it.hasNext()) {
                    Transform t = it.next();
                    System.out.printf("%c", t.getType());
                    System.out.println(Arrays.toString(t.getCoordinate()));
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------");
        List<Integer[]> vertices;

        for (int i = 0; i < drawList.size(); i++) {
            for (Transform trans : drawList.get(i).getTransformation()) {
                vertices = drawList.get(i).getVertices();
                switch (trans.type) {
//                            case 'r' ->
//                                drawList.get(i).updateVertices(rotation(drawList.get(i).getVertices(), trans.getCoordinate()[0], (int) trans.getCoordinate()[1], (int) trans.getCoordinate()[2]));
                    case 't' ->
                        vertices = translate(vertices, (int) trans.getCoordinate()[0], (int) trans.getCoordinate()[1]);

//                                drawList.get(i).updateVertices(translate(drawList.get(i).getVertices(), (int) trans.getCoordinate()[0], (int) trans.getCoordinate()[1]));
//                            case 's' ->
//                                drawList.get(i).updateVertices(scale(drawList.get(i).getVertices(), trans.getCoordinate()[0], trans.getCoordinate()[1], (int) trans.getCoordinate()[2], (int) trans.getCoordinate()[3]));
//                            default ->
//                                throw new AssertionError();
                }
                if (!vertices.isEmpty()) {
                    for (int a = 0; a < vertices.size(); a++) {
                        System.out.println(Arrays.toString(vertices.get(a)));
                    }
                } else {
                    System.out.println("No vertices");
                }
                drawList.get(i).updateVertices(vertices);
                System.out.println();
            }
        }

        // start program with 2D array of command
        program.start(drawList);

    }

    private static boolean isNumeric(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *
     * @param vertices
     * @param tX
     * @param ty
     */
    private static List<Integer[]> translate(List<Integer[]> vertices, int tX, int tY) {
//        List<Integer[]> translatedVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
//            Integer[] point = vertices.get(i);
//            point[0] += tX;
//            point[1] += tY;
//            translatedVertices.add(point);
            vertices.get(i)[0] += tX;
            vertices.get(i)[1] += tY;
        }
        return vertices;
    }

    private List<Integer[]> rotation(List<Integer[]> vertices, double rotationAngle, int pivotX, int pivotY) {
        List<Integer[]> rotatedVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            Integer[] point = vertices.get(i);
            float x = (float) (pivotX + (point[0] - pivotX) * Math.cos(rotationAngle) - (point[1] - pivotY) * Math.sin(rotationAngle));
            float y = (float) (pivotY + (point[0] - pivotX) * Math.sin(rotationAngle) - (point[1] - pivotY) * Math.cos(rotationAngle));
            Integer[] newPoint = {(int) x, (int) y};
            rotatedVertices.add(newPoint);
        }
        return rotatedVertices;
    }

    private List<Integer[]> scale(List<Integer[]> vertices, double scaleX, double scaleY, int pivotX, int pivotY) {
        List<Integer[]> scaledVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            Integer[] point = vertices.get(i);
            float x = (float) (point[0] * scaleX + pivotX * (1 - scaleX));
            float y = (float) (point[1] * scaleY + pivotY * (1 - scaleY));
            Integer[] newPoint = {(int) x, (int) y};
            scaledVertices.add(newPoint);
        }
        return scaledVertices;
    }

    private void scanLine(Polygon poly) {
//        System.out.printf("Size: %d\n", poly.getEdges().size());
        List<Edge> globalEdge = initializeGlobalEdge(poly);
        List<Edge> activeEdge = new ArrayList<>();
        List<Integer> intersection = new ArrayList<>();
        int yMin = poly.getYMin();
        int yMax = poly.getYMax();
//        System.out.printf("y Min: %d\tyMax: %d\n", yMin, yMax);

        glColor3f(poly.getRed(), poly.getGreen(), poly.getBlue());

        for (int scanLine = yMin; scanLine <= yMax; scanLine++) {
//            boolean evenParity = false;
            activeEdge = activeEdge(activeEdge, globalEdge, scanLine);
            intersection = getIntersection(intersection, activeEdge, scanLine);
            if (scanLine == 160) {
                System.out.println("AT Y = 160");
                for (int i = 0; i < activeEdge.size(); i++) {
                    int x1 = activeEdge.get(i).getX1();
                    int y1 = activeEdge.get(i).getY1();
                    int x2 = activeEdge.get(i).getX2();
                    int y2 = activeEdge.get(i).getY2();
//                    double xA = activeEdge.get(i).getAssociateX();
                    System.out.printf("Active: (%d, %d) (%d, %d) \n", x1, y1, x2, y2);
                }

            }

            for (int i = 0; i < intersection.size(); i += 2) {
                int x1 = intersection.get(i);
                if (i + 1 >= intersection.size()) {
                    break;
                }
                int x2 = intersection.get(i + 1);
//                glBegin(GL_LINES);
//                glVertex2f(x1, scanLine);
//                glVertex2f(x2, scanLine);
//                glEnd();

                for (int j = x1; j < x2; j++) {
                    glBegin(GL_POINTS);
                    glVertex2d(j, scanLine);
                    glEnd();
                }
            }
        }
    }

    private List<Integer> getIntersection(List<Integer> intersection, List<Edge> activeEdge, int currentY) {
        intersection.clear();

        for (Edge e : activeEdge) {
            int x = (int) (e.getAssociateX() + ((currentY - e.getMinY()) * e.get1OverM()));
            intersection.add(x);
        }
        Collections.sort(intersection);
        return intersection;
    }

    private List<Edge> activeEdge(List<Edge> activeEdges, List<Edge> globalEdge, int y) {
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

    private void updateActiveEdge(List<Edge> activeEdge) {
        for (Edge e : activeEdge) {
            e.updateAssociateX();
            System.out.printf("%.1f\t", e.getAssociateX());
        }
        System.out.println("");
        Collections.sort(activeEdge);
    }

    private boolean isActive(Edge edge, int currentY) {
        return edge.getMinY() < currentY && currentY <= edge.getMaxY();
    }

    private List<Edge> initializeGlobalEdge(Polygon poly) {
        List<Edge> allEdge = poly.getEdges();
        Collections.sort(allEdge);
        for (int i = 0; i < allEdge.size(); i++) {
            try {
                if (Double.isInfinite(allEdge.get(i).get1OverM())) {
                    allEdge.remove(i);
                }
//                System.out.printf("%d %d %d %.1f\n", allEdge.get(i).getMinY(), allEdge.get(i).getMaxY(), allEdge.get(i).getAssociateX(), allEdge.get(i).get1OverM());
            } catch (ArithmeticException e) {
            }
        }
//        System.out.println("---");

        return allEdge;
    }

    // method: start
    // purpose: start a new window and render graphics
    public void start(List<Polygon> drawList) {
        try {
            createWindow();
            initGL();
            render(drawList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method: createWindow
    // purpose: create a new window display with set size and title
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.setTitle("Program 1");
        Display.create();
    }

    // method: initGL
    // purpose: initilize openGL task
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(-CENTER_X, CENTER_X, -CENTER_Y, CENTER_Y, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    // method: render
    // purpose: render graphics based on command
    private void render(List<Polygon> drawList) {
        glColor3f(1.0f, 1.0f, 0.0f);
        glPointSize(1);
        // close when press "Q" or click close window
        while (!Keyboard.isKeyDown(Keyboard.KEY_Q) && !Display.isCloseRequested()) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                // render each command from the array
                for (int i = 0; i < drawList.size(); i++) {
                    glPushMatrix();
                    Polygon p = drawList.get(i);
                    for (Transform t : p.getTransformation()) {
                        switch (t.type) {
                            case 't' ->
                                glTranslatef((float) t.getCoordinate()[0], (float) t.getCoordinate()[1], 0);
                            case 'r' ->
                                glRotatef((float) t.getCoordinate()[0], (float) t.getCoordinate()[1], (float) t.getCoordinate()[2], 1);
                            case 's' ->
                                glScalef((float) t.getCoordinate()[0], (float) t.getCoordinate()[1], 0);
//                            default -> {
//                            }
                        }
                    }
                    scanLine(drawList.get(i));
                    glPopMatrix();
                }

                Display.update();
                Display.sync(60);
            } catch (Exception e) {
            }
        }
        Display.destroy();
    }
}
