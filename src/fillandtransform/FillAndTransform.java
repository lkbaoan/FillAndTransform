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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FillAndTransform {

    // method: main
    // purpose: to read file and start the program
    public static void main(String[] args) {
//        FillAndTransform program = new FillAndTransform();
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
            while (read.hasNextLine()) {
                str = read.nextLine();
                String[] splitted = str.trim().split("\\s+");
                System.out.println(Arrays.toString(splitted));
                if (isNumeric(splitted[0])) {
                    poly.addVertice(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
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
                Iterator<Integer[]> iv = p.getVertices();
                while (iv.hasNext()) {
                    System.out.println(Arrays.toString(iv.next()));
                }
                Iterator<Transform> it = p.getTransformation();
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
        // start program with 2D array of command
//        program.start();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
//    // method: start
//    // purpose: start a new window and render graphics
//    public void start(int[][] line, int[][] circle, int[][] ellipse) {
//        try {
//            createWindow();
//            initGL();
//            render(line, circle, ellipse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // method: createWindow
//    // purpose: create a new window display with set size and title
//    private void createWindow() throws Exception {
//        Display.setFullscreen(false);
//        Display.setDisplayMode(new DisplayMode(640, 480));
//        Display.setTitle("Program 1");
//        Display.create();
//    }
//
//    // method: initGL
//    // purpose: initilize openGL task
//    private void initGL() {
//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        glMatrixMode(GL_PROJECTION);
//        glLoadIdentity();
//
//        glOrtho(0, 640, 0, 480, 1, -1);
//
//        glMatrixMode(GL_MODELVIEW);
//        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//    }
//
//    // method: render
//    // purpose: render graphics based on command
//    private void render(int[][] line, int[][] circle, int[][] ellipse) {
//        // close when press "Q" or click close window
//        while (!Keyboard.isKeyDown(Keyboard.KEY_Q) && !Display.isCloseRequested()) {
//            try {
//                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//                glLoadIdentity();
//
//                glColor3f(1.0f, 1.0f, 0.0f);
//                glPointSize(1);
//
//                // render each command from the array
//                for (int[] l : line) {
//                    renderLine(l[0], l[1], l[2], l[3]);
//                }
//                for (int[] l : circle) {
//                    renderCircle(l[0], l[1], l[2]);
//                }
//                for (int[] l : ellipse) {
//                    renderEllipse(l[0], l[1], l[2], l[3]);
//                }
//
//                Display.update();
//                Display.sync(60);
//            } catch (Exception e) {
//            }
//        }
//        Display.destroy();
//    }
//
//    // method: renderLine
//    // purpose: render line from start coordinate and end coordiante
//    private void renderLine(int xStart, int yStart, int xEnd, int yEnd) {
//        int dx = Math.abs(xEnd - xStart);
//        int dy = Math.abs(yEnd - yStart);
//        int x, y, d;
//        d = 2 * dy - dx;
//        // swap start and end if end < start so it will always render from left to right
//        if (xStart > xEnd) {
//            x = xEnd;
//            y = yEnd;
//            xEnd = xStart;
//        } else {
//            x = xStart;
//            y = yStart;
//        }
//        int incrementRight = 2 * dy;
//        int incrementUpRight = 2 * dy - dx;
//        boolean isUp = yEnd > yStart;
//
//        glColor3f(GL_RED, 0, 0);
//
//        while (x < xEnd) {
//            glBegin(GL_POINTS);
//            glVertex2f(x, y);
//            glEnd();
//
//            x++;
//            if (d < 0) {
//                d += incrementRight;
//            } else {
//                if (isUp) {
//                    y++;
//                } else {
//                    y--;
//                }
//                d += incrementUpRight;
//            }
//        }
//    }
//
//    // method: renderCircle
//    // purpose: render a circle with center coordinate and its radius
//    private void renderCircle(int xCenter, int yCenter, int radius) {
//        glColor3f(0, 0, GL_BLUE);
//
//        int x = 0;
//        int y = radius;
//        int p = 1 - radius;
//
//        drawCircle(xCenter, yCenter, x, y);
//        while (x < y) {
//            x++;
//            if (p < 0) {
//                p += 2 * x + 1;
//            } else {
//                y--;
//                p += 2 * x - 2 * y + 1;
//            }
//            drawCircle(xCenter, yCenter, x, y);
//
//        }
//
//    }
//
//    // method: drawCircle
//    // purpose: actual render command for the circle with its center and edge coordinate
//    private void drawCircle(int xCenter, int yCenter, int x, int y) {
//        glBegin(GL_POINTS);
//        glVertex2f(xCenter + x, yCenter + y);
//        glVertex2f(xCenter - x, yCenter + y);
//        glVertex2f(xCenter + x, yCenter - y);
//        glVertex2f(xCenter - x, yCenter - y);
//        glVertex2f(xCenter + y, yCenter + x);
//        glVertex2f(xCenter - y, yCenter + x);
//        glVertex2f(xCenter + y, yCenter - x);
//        glVertex2f(xCenter - y, yCenter - x);
//        glEnd();
//    }
//
//    // method: renderCircle
//    // purpose: render an ellipse with center coordinate and its radius
//    private void renderEllipse(int xCenter, int yCenter, int rx, int ry) {
//        glColor3f(0, GL_GREEN, 0);
//        int rx2 = rx * rx;
//        int ry2 = ry * ry;
//        int twoRx2 = 2 * rx2;
//        int twoRy2 = 2 * ry2;
//        int p;
//        int x = 0;
//        int y = ry;
//        int px = 0;
//        int py = twoRx2 * y;
//
//        drawEllipse(xCenter, yCenter, x, y);
//
//        // Region 1
//        p = (int) (ry2 - (rx2 * ry) + (0.25 * rx2));
//        while (px < py) {
//            x++;
//            px += twoRy2;
//            if (p < 0) {
//                p += ry2 + px;
//            } else {
//                y--;
//                py -= twoRx2;
//                p += ry2 + px - py;
//            }
//            drawEllipse(xCenter, yCenter, x, y);
//        }
//
//        // Region 2
//        p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
//        while (y > 0) {
//            y--;
//            py -= twoRx2;
//            if (p > 0) {
//                p += rx2 - py;
//            } else {
//                x++;
//                px += twoRy2;
//                p += rx2 - py + px;
//            }
//            drawEllipse(xCenter, yCenter, x, y);
//        }
//
//    }
//
//    // method: drawEllipse
//    // purpose: actual render command for the ellipse with its center and edge coordinate
//    private void drawEllipse(int xCenter, int yCenter, int x, int y) {
//        glBegin(GL_POINTS);
//        glVertex2f(xCenter + x, yCenter + y);
//        glVertex2f(xCenter - x, yCenter + y);
//        glVertex2f(xCenter + x, yCenter - y);
//        glVertex2f(xCenter - x, yCenter - y);
//        glEnd();
//    }
}
