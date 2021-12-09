package main.java.ulibs.gl.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.common.utils.ICopyable;

public class QuadData implements ICopyable<QuadData> {
	public static final float[] DEFAULT_TCS = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };
	public static final int[] DEFAULT_INDICES = new int[] { 0, 1, 2, 2, 3, 0 };
	
	protected float[] vertices = new float[0], tcs = new float[0];
	protected int[] indices = new int[0];
	protected int size;
	
	public QuadData() {
		this(new float[0], new int[0], new float[0], 0);
	}
	
	protected QuadData(float[] vertices, int[] indices, float[] tcs, int size) {
		this.vertices = vertices;
		this.indices = indices;
		this.tcs = tcs;
		this.size = size;
	}
	
	private QuadData(QuadData data) {
		this(data.vertices, data.indices, data.tcs, data.size);
	}
	
	public void reset() {
		vertices = new float[0];
		tcs = new float[0];
		indices = new int[0];
		size = 0;
	}
	
	public <T extends QuadData> T addAll(QuadData data) {
		return addAll(data.vertices, data.indices, data.tcs);
	}
	
	public <T extends QuadData> T addAll(float[] vertices, int[] indices, float[] tcs) {
		return addVerticesAndSafeIndices(vertices, indices).addTcs(tcs);
	}
	
	public <T extends QuadData> T addVerticesWithDefaults(float[] vertices) {
		return addDefaultIndices().addVertices(vertices).addDefaultTcs();
	}
	
	public <T extends QuadData> T addVerticesAndSafeIndices(float[] vertices, int[] indices) {
		return addIndices(indices).addVertices(vertices);
	}
	
	public <T extends QuadData> T addDefaultTcs() {
		return addTcs(DEFAULT_TCS);
	}
	
	public <T extends QuadData> T addDefaultIndices() {
		return addIndices(DEFAULT_INDICES);
	}
	
	public void setAll(QuadData data) {
		this.vertices = data.vertices;
		this.tcs = data.tcs;
		this.indices = data.indices;
	}
	
	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}
	
	public void setTcs(float[] tcs) {
		this.tcs = tcs;
	}
	
	public void setIndices(int[] indices) {
		this.indices = indices;
	}
	
	protected float[] combine(float[] v1, float[] v2) {
		FloatBuffer buf = FloatBuffer.wrap(new float[v1.length + v2.length]);
		buf.put(v1);
		buf.put(v2);
		buf.flip();
		
		return buf.array();
	}
	
	protected int[] combine(int[] v1, int[] v2) {
		IntBuffer buf = IntBuffer.wrap(new int[v1.length + v2.length]);
		buf.put(v1);
		buf.put(v2);
		buf.flip();
		
		return buf.array();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends QuadData> T addVertices(float[] vertices) {
		this.vertices = combine(this.vertices, vertices);
		size += vertices.length / 12;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends QuadData> T addTcs(float[] tcs) {
		this.tcs = combine(this.tcs, tcs);
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends QuadData> T addIndices(int[] indices) {
		int[] ids = indices.clone();
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i] + size * 4;
		}
		
		this.indices = combine(this.indices, ids);
		return (T) this;
	}
	
	public static float[] createCenteredVertex(float x, float y, ZConstant z, float w, float h, float xo, float yo) {
		if (!MathH.within(xo, x, w) || !MathH.within(yo, y, h)) {
			Console.print(WarningType.Error, "xo/yo cannot be less than x/y or more than w/h!");
			return new float[0];
		}
		
		return new float[] { x - xo, y - yo, z.z, x - xo, y + h - yo, z.z, x + w - xo, y + h - yo, z.z, x + w - xo, y - yo, z.z };
	}
	
	public static float[] createCenteredVertex(int x, int y, ZConstant z, int w, int h, int xo, int yo) {
		return createCenteredVertex((float) x, (float) y, z, (float) w, (float) h, (float) xo, (float) yo);
	}
	
	public static float[] createCenteredVertex(Vec2i pos, ZConstant z, Vec2i size, Vec2i origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2f pos, ZConstant z, Vec2f size, Vec2i origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2f pos, ZConstant z, Vec2i size, Vec2i origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2i pos, ZConstant z, Vec2f size, Vec2i origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2i pos, ZConstant z, Vec2i size, Vec2f origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2f pos, ZConstant z, Vec2f size, Vec2f origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2f pos, ZConstant z, Vec2i size, Vec2f origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createCenteredVertex(Vec2i pos, ZConstant z, Vec2f size, Vec2f origin) {
		return createCenteredVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY(), origin.getX(), origin.getY());
	}
	
	public static float[] createVertex(float x, float y, ZConstant z, float w, float h) {
		return new float[] { x, y, z.z, x, y + h, z.z, x + w, y + h, z.z, x + w, y, z.z };
	}
	
	public static float[] createVertex(int x, int y, ZConstant z, int w, int h) {
		return createVertex((float) x, (float) y, z, (float) w, (float) h);
	}
	
	public static float[] createVertex(Vec2i pos, ZConstant z, Vec2i size) {
		return createVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY());
	}
	
	public static float[] createVertex(Vec2f pos, ZConstant z, Vec2f size) {
		return createVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY());
	}
	
	public static float[] createVertex(Vec2f pos, ZConstant z, Vec2i size) {
		return createVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY());
	}
	
	public static float[] createVertex(Vec2i pos, ZConstant z, Vec2f size) {
		return createVertex(pos.getX(), pos.getY(), z, size.getX(), size.getY());
	}
	
	public float[] getVertices() {
		return vertices;
	}
	
	public int[] getIndices() {
		return indices;
	}
	
	public float[] getTcs() {
		return tcs;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public QuadData copy() {
		return new QuadData(this);
	}
}
