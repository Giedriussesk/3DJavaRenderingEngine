//This class stores the locations of a single point in space (x,y,z)

public class Vertex {
    //These hold the position of the point in 3D space
    double x; //Horizontal position
    double y; //Vertical position
    double z; //Depth position (forward/backward)

    //Constructor: creates a new point when given x, y and z values
    Vertex(double x, double y, double z) {
        this.x = x; //assign the input x to this object's x
        this.y = y; //assign the input y to this object's y
        this.z = z; //assign the input z to this object's z
    }
}
