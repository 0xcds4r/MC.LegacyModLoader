package org.lwjgl.util.vector;

import java.io.Serializable;

/**
 * LWJGL2 vector shim used by shaded legacy libraries. Only the operations that
 * old render code actually calls are implemented; this is plain math and does
 * not touch modern rendering.
 */
public class Vector3f extends Vector implements Serializable {
    public float x;
    public float y;
    public float z;

    public Vector3f() {
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f other) {
        this(other.x, other.y, other.z);
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f set(Vector3f other) {
        return set(other.x, other.y, other.z);
    }

    public Vector3f add(Vector3f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3f sub(Vector3f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3f scale(float factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    public Vector3f negate() {
        return scale(-1.0F);
    }

    public Vector3f cross(Vector3f other) {
        return cross(this, other, this);
    }

    public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            dest = new Vector3f();
        }
        float x = left.y * right.z - left.z * right.y;
        float y = left.z * right.x - left.x * right.z;
        float z = left.x * right.y - left.y * right.x;
        return dest.set(x, y, z);
    }

    public float dot(Vector3f other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3f normalise(Vector3f dest) {
        float length = length();
        if (length == 0.0F) {
            return dest == null ? this : dest.set(0, 0, 0);
        }
        return (dest == null ? this : dest).set(x / length, y / length, z / length);
    }

    public Vector3f normalise() {
        return normalise(this);
    }

    public Vector3f translate(float dx, float dy, float dz) {
        return add(new Vector3f(dx, dy, dz));
    }
}
