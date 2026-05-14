import javax.swing.*; //for the window (JFrame) and Slider (JSlider)
import java.awt.*; //for the graphics and layout
import java.util.List; //interface for the list of triangles
import java.util.ArrayList; //concrete implementation of the list
import java.awt.geom.Path2D; //helps draw lines and fill shapes between points

public class DemoViewer {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); //create the main application window
        Container pane = frame.getContentPane(); //get the area where we can add components
        pane.setLayout(new BorderLayout()); //set the layout to handle North, South, East, West, Center

        //create a slider for horizontal rotation (heading) from 0 to 360 degrees
        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH); // put the horizontal slider at the bottom

        //create a vertical slider for pitch rotation from -90 to 90 degrees
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST); // put the vertical slider on the right side

        //initialise the list that will hold every triangle making up our 3D car
        List<Triangle> tris = new ArrayList<>();

        //DATA INPUT SECTION
        //use the addBox helper to create the lower chassis (Red)
        addBox(tris, -120, 0, -50, 120, 40, 50, Color.RED);
        //use the addBox helper to create the cabin on top (Blue)
        addBox(tris, -60, -40, -40, 60, 0, 40, Color.BLUE);

        //use the addWheel helper to create 8-sided "round" wheels at specific coordinates
        addWheel(tris, -80, 40, -55); //back Left wheel
        addWheel(tris, -80, 40, 55);  //back Right wheel
        addWheel(tris, 80, 40, -55);  //front Left wheel
        addWheel(tris, 80, 40, 55);   //front Right wheel

        //define the panel where the actual drawing happens
        JPanel renderPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g; //cast to Graphics2D for advanced drawing features
                g2.setColor(Color.BLACK); //set the background color
                g2.fillRect(0, 0, getWidth(), getHeight()); //clear the screen with a black rectangle

                //convert slider degrees into radians for the math functions
                double heading = Math.toRadians(headingSlider.getValue());
                //create a rotation matrix for side to side movement
                Matrix3 headingTransform = new Matrix3(new double[] {
                        Math.cos(heading), 0, Math.sin(heading),
                        0, 1, 0,
                        -Math.sin(heading), 0, Math.cos(heading)
                });

                double pitch = Math.toRadians(pitchSlider.getValue());
                //create a rotation matrix for up-and-down movement
                Matrix3 pitchTransform = new Matrix3(new double[] {
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)
                });

                //combine both rotations into one single transformation matrix
                Matrix3 transform = headingTransform.multiply(pitchTransform);

                //calculate the current depth of every triangle based on the rotation
                for (Triangle t : tris) {
                    t.calculateAverageZ(transform);
                }

                //sort the triangles so the ones furthest away are drawn first
                //this prevents the "back" of the car from appearing in front of the hood
                tris.sort((t1, t2) -> Double.compare(t2.averageZ, t1.averageZ));

                //move the coordinate origin from (0,0) top-left to the center of the screen
                g2.translate(getWidth() / 2, getHeight() / 2);

                //loop through our sorted list and draw each triangle
                for (Triangle t : tris) {
                    //apply the rotation matrix to find the 2D screen position for all 3 corners
                    Vertex v1 = transform.transform(t.v1);
                    Vertex v2 = transform.transform(t.v2);
                    Vertex v3 = transform.transform(t.v3);

                    //trace the shape of the triangle
                    Path2D path = new Path2D.Double();
                    path.moveTo(v1.x, v1.y); //start at first point
                    path.lineTo(v2.x, v2.y); //draw to second
                    path.lineTo(v3.x, v3.y); //draw to third
                    path.closePath();        //close the loop

                    g2.setColor(t.color); //set the brush to the triangle's specific color
                    g2.fill(path);        //fill the shape with color

                    g2.setColor(Color.BLACK); //set brush to black for the outline
                    g2.draw(path);           //draw the wireframe edge
                }
            }
        };

        pane.add(renderPanel, BorderLayout.CENTER); //place the drawing panel in the center

        //tell the program to redraw whenever a slider value changes
        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(600, 600); //set initial window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kill the process when window is closed
        frame.setVisible(true); //show the window
    }

    //HELPER METHODS - logic outside of main

    //creates a box by adding 12 triangles (2 for each of the 6 sides)
    public static void addBox(List<Triangle> tris, double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        //front face
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x2, y1, z1), new Vertex(x2, y2, z1), c));
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x2, y2, z1), new Vertex(x1, y2, z1), c));
        //back face
        tris.add(new Triangle(new Vertex(x1, y1, z2), new Vertex(x2, y1, z2), new Vertex(x2, y2, z2), c));
        tris.add(new Triangle(new Vertex(x1, y1, z2), new Vertex(x2, y2, z2), new Vertex(x1, y2, z2), c));
        //left face
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x1, y1, z2), new Vertex(x1, y2, z2), c));
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x1, y2, z2), new Vertex(x1, y2, z1), c));
        //right face
        tris.add(new Triangle(new Vertex(x2, y1, z1), new Vertex(x2, y1, z2), new Vertex(x2, y2, z2), c));
        tris.add(new Triangle(new Vertex(x2, y1, z1), new Vertex(x2, y2, z2), new Vertex(x2, y2, z1), c));
        //top face
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x2, y1, z1), new Vertex(x2, y1, z2), c));
        tris.add(new Triangle(new Vertex(x1, y1, z1), new Vertex(x2, y1, z2), new Vertex(x1, y1, z2), c));
        //bottom face
        tris.add(new Triangle(new Vertex(x1, y2, z1), new Vertex(x2, y2, z1), new Vertex(x2, y2, z2), c));
        tris.add(new Triangle(new Vertex(x1, y2, z1), new Vertex(x2, y2, z2), new Vertex(x1, y2, z1), c));
    }

    //creates a wheel by arranging 8 triangular slices into an octagon
    public static void addWheel(List<Triangle> tris, double x, double y, double z) {
        int segments = 8; //how many sides the wheel has
        double radius = 25; //wheel size
        for (int i = 0; i < segments; i++) {
            double a1 = Math.toRadians(i * 45); //start angle of the slice
            double a2 = Math.toRadians((i + 1) * 45); //end angle of the slice
            //calculates coordinates for the rim using sine and cosine
            Vertex v1 = new Vertex(x + Math.cos(a1) * radius, y + Math.sin(a1) * radius, z);
            Vertex v2 = new Vertex(x + Math.cos(a2) * radius, y + Math.sin(a2) * radius, z);
            Vertex center = new Vertex(x, y, z); //all slices meet at the hub
            tris.add(new Triangle(v1, v2, center, Color.DARK_GRAY)); //add the slice to the car
        }
    }
}