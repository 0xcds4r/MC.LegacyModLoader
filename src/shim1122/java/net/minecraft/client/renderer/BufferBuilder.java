package net.minecraft.client.renderer;

public class BufferBuilder {
    public void begin(int mode, net.minecraft.client.renderer.vertex.VertexFormat format) {
    }

    public BufferBuilder pos(double x, double y, double z) {
        return this;
    }

    public BufferBuilder tex(double u, double v) {
        return this;
    }

    public BufferBuilder color(float r, float g, float b, float a) {
        return this;
    }

    public BufferBuilder color(int r, int g, int b, int a) {
        return this;
    }

    public BufferBuilder normal(float x, float y, float z) {
        return this;
    }

    public void endVertex() {
    }

    public void finishDrawing() {
    }
}
