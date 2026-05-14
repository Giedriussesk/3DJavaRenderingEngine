# 3D Java Renderering Engine (Low-Poly Car)

A custom 3D rendering engine built from scratch in Java, implementing core computer graphics principles without the use of external game engines or libraries. This project was developed as a hands-on exploration of Object-Oriented Programming (OOP) and the mathematical foundations of 3D-to-2D projection.

## Project Overview
While inspired by the "Build Your Own X" curriculum and the Rogach 3D tutorial, I expanded the scope from a simple tetrahedron to a more complex 3D car model. This required managing a significantly larger list of vertices and triangles, as well as solving depth-perception issues associated with 3D rendering.  I used comments in the code to keep track and help me understand the code better line by line.

## Technical Features

### The Painter's Algorithm
For visual accuracy, I used the Painter's Algorithm. Since screens are 2D, the engine must decide which triangles to draw first. By calculating the average depth (Z-coordinate) of each triangle and sorting them from farthest to closest, the engine makes sure that the car's body correctly seals the wheels on the far side.

### Linear Algebra and Matrices
*   **Rotation Matrices**: Implemented custom matrix multiplication to handle 3D transformations.
*   **Dynamic View**: Integrated JSlider components to allow real-time manipulation of Heading and Pitch, rotating the car 360 degrees across multiple axes.

### Object-Oriented Architecture
*   **Vertex**: Represents a single point (x, y, z) in 3D space.
*   **Triangle**: The core building block of the model, grouping three vertices with color data and depth-calculation logic.
*   **Matrix3**: A dedicated class for handling the 3x3 matrix math required for vertex transformation.

## Car Construction
The 3D car is built using organised code that is easy to update:
*   **Chassis and Cabin**: Created using an `addBox` method that translates 3D bounds into 12 distinct triangles.
*   **Octagonal Wheels**: To simulate curved tires using only flat surfaces, I used trigonometry (Sine and Cosine) to generate 8-segment wheels.

## Tech Stack
*   **Language**: Java
*   **GUI**: Java Swing and AWT
*   **Math**: Pure Linear Algebra

## Key Outcomes
*   Projecting 3D coordinates onto a 2D plane using matrix math.
*   Managing complex state and collections within a Java OOP framework.
*   The application of sorting algorithms and depth-buffering logic in graphical rendering performance.

---
*This project was completed as part of a self-study journey in Computer Science fundamentals, focusing on Java, Data Structures, and OOP principles.*
