package com.liampace.geom;

import org.joml.Vector2f;

public interface Shape {
    
    public boolean contains(Vector2f position);
    public Bezier2f[] getEdges();
    public AAR getBounds();

}
