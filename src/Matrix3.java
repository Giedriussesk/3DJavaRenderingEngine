//this is the mathematical class.  In 3D graphics, Moving or rotating
//an object means multiplying its coordinates by a grid of numbers called a Matrix


public class Matrix3 {
    //A 1D array used to represent a 3x3 mathematical matrix (9 total values)
    double[] values;

    Matrix3(double[] values) {
        this.values = values;
    }
    //This method combines two different rotations (like tilting and spinning)
    //into one single transformation matrix
    Matrix3 multiply(Matrix3 other) {
        double[] result = new double[9]; //temporary array for the new values
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    //this is the mathematical dot-product of rows and columns
                    result[row * 3 + col] += this.values[row * 3 + i] * other.values[i * 3 + col];

                }
            }
        }
        return new Matrix3(result);
    }
    //this method takes a 3D point (Vertex) and calculations its new position
    //after the rotation matrix is applied to it
    Vertex transform(Vertex in) {
        return new Vertex(
                in.x * values[0] + in.y * values[3] + in.z * values[6],
                in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }
}
