package org.lwjgl.util.glu;

public interface GLUtessellatorCallback {
    void begin(int type);

    void end();

    void vertex(Object vertexData);

    void combine(double[] coords, Object[] data, float[] weight, Object[] outData);

    void error(int errnum);

    void beginData(int type, Object polygonData);

    void edgeFlag(boolean boundaryEdge);

    void edgeFlagData(boolean boundaryEdge, Object polygonData);

    void vertexData(Object vertexData, Object polygonData);

    void endData(Object polygonData);
}
