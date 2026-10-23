package org.lwjgl.util.vector;

public class Vector4f extends Vector {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector4f other) {
        this(other.x, other.y, other.z, other.w);
    }

    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public Vector4f normalise(Vector4f dest) {
        float length = length();
        if (length == 0.0F) {
            return (dest == null ? this : dest).set(0, 0, 0, 0);
        }
        return (dest == null ? this : dest).set(x / length, y / length, z / length, w / length);
    }
}
