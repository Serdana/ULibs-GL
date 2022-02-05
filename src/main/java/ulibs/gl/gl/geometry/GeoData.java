package main.java.ulibs.gl.gl.geometry;

public abstract class GeoData {
	public static final float[] DEFAULT_TCS = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };
	
	public final float[] vertices, tcs;
	public final int[] indices;
	
	protected GeoData(float[] vertices, int[] indices, float[] tcs) {
		this.vertices = vertices;
		this.indices = indices;
		this.tcs = tcs;
	}
	
	protected GeoData(float[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
		this.tcs = DEFAULT_TCS.clone();
	}
}
