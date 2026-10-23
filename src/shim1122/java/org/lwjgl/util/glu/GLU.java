package org.lwjgl.util.glu;

import java.lang.reflect.Proxy;

/**
 * Minimal LWJGL2 GLU shim. Tessellation is not performed; the APIs exist so old
 * shaded libraries link. Rendering callbacks are recorded but never invoked.
 */
public final class GLU {
    public static final int GLU_TESS_BEGIN = 100100;
    public static final int GLU_TESS_VERTEX = 100101;
    public static final int GLU_TESS_END = 100102;
    public static final int GLU_TESS_ERROR = 100103;
    public static final int GLU_TESS_EDGE_FLAG = 100104;
    public static final int GLU_TESS_COMBINE = 100105;
    public static final int GLU_TESS_BEGIN_DATA = 100106;
    public static final int GLU_TESS_VERTEX_DATA = 100107;
    public static final int GLU_TESS_END_DATA = 100108;
    public static final int GLU_TESS_ERROR_DATA = 100109;
    public static final int GLU_TESS_EDGE_FLAG_DATA = 100110;
    public static final int GLU_TESS_COMBINE_DATA = 100111;
    public static final int GLU_TESS_WINDING_ODD = 100130;
    public static final int GLU_TESS_WINDING_NONZERO = 100131;
    public static final int GLU_TESS_WINDING_POSITIVE = 100132;
    public static final int GLU_TESS_WINDING_NEGATIVE = 100133;
    public static final int GLU_TESS_WINDING_ABS_GEQ_TWO = 100134;
    public static final int GLU_TESS_WINDING_RULE = 100140;
    public static final int GLU_TESS_BOUNDARY_ONLY = 100141;
    public static final int GLU_TESS_TOLERANCE = 100142;

    private GLU() {
    }

    public static GLUtessellator gluNewTess() {
        return (GLUtessellator) Proxy.newProxyInstance(
                GLU.class.getClassLoader(),
                new Class<?>[]{GLUtessellator.class},
                (proxy, method, args) -> {
                    if (method.getReturnType() == boolean.class) {
                        return false;
                    }
                    if (method.getReturnType() == int.class) {
                        return 0;
                    }
                    if (method.getReturnType() == double.class) {
                        return 0.0D;
                    }
                    return null;
                });
    }

    public static void gluDeleteTess(GLUtessellator tessellator) {
    }

    public static String gluErrorString(int errorCode) {
        return "GLU error " + errorCode;
    }
}
