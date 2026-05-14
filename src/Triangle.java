//Since screens are 2D, we will use a triangle to build a 3D shape because any three
//points in space always form a flat surface.

import java.awt.Color; // tool that handles colors

public class Triangle {
    Vertex v1; //first corner of the triangle
    Vertex v2; //second corner
    Vertex v3; //third corner
    Color color; //the color used to draw/fill this triangle
    double averageZ; //stores the depth of the triangle to determine what is in front

    //Constructor: Binds three vertices and a color together as one object
    Triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
    //method to calculate how far the triangle is from the viewer's eye
    void calculateAverageZ(Matrix3 transform) {
        //rotate all three vertices to their new positions in 3D space
        Vertex v1t = transform.transform(v1); //transform the first vertex
        Vertex v2t = transform.transform(v2); //transform the second vertex
        Vertex v3t = transform.transform(v3); //transform the third vertex

        //calculate the average depth (Z-axis) of the three rotated points
        //we add the Z values together and divide by three to find the center depth
        this.averageZ = (v1t.z + v2t.z + v3t.z) / 3.0;
    }
}
