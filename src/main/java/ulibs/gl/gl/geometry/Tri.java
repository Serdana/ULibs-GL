package main.java.ulibs.gl.gl.geometry;

import main.java.ulibs.gl.gl.ZConstant;

public class Tri extends GeoData {
	public static final int[] DEFAULT_INDICES = new int[] { 0, 1, 2 };
	
	public Tri(float vx0, float vy0, float vx1, float vy1, float vx2, float vy2, ZConstant z, int i0, int i1, int i2, float tx0, float ty0, float tx1, float ty1, float tx2,
			float ty2, float tx3, float ty3) {
		super(new float[] { vx0, vy0, z.z, vx1, vy1, z.z, vx2, vy2, z.z }, new int[] { i0, i1, i2 }, new float[] { tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3 });
	}
	
	public Tri(float vx0, float vy0, float vx1, float vy1, float vx2, float vy2, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
			float ty3) {
		super(new float[] { vx0, vy0, z.z, vx1, vy1, z.z, vx2, vy2, z.z }, DEFAULT_INDICES.clone(), new float[] { tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3 });
	}
	
	public Tri(float vx0, float vy0, float vx1, float vy1, float vx2, float vy2, ZConstant z) {
		super(new float[] { vx0, vy0, z.z, vx1, vy1, z.z, vx2, vy2, z.z }, DEFAULT_INDICES.clone());
	}
}
