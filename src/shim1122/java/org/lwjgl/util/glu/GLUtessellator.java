package org.lwjgl.util.glu;

public interface GLUtessellator {
    void gluDeleteTess();

    void gluTessProperty(int which, double value);

    void gluGetTessProperty(int which, double[] value, int offset);

    void gluTessNormal(double x, double y, double z);

    void gluTessCallback(int which, GLUtessellatorCallback callback);

    void gluTessVertex(double[] coords, int offset, Object data);

    void gluTessBeginPolygon(Object data);

    void gluTessBeginContour();

    void gluTessEndContour();

    void gluTessEndPolygon();
}
