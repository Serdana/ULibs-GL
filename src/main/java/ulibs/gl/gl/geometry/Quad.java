package main.java.ulibs.gl.gl.geometry;

import main.java.ulibs.common.math.IVec2;
import main.java.ulibs.gl.gl.ZConstant;

public class Quad extends GeoData {
	public static final int[] DEFAULT_INDICES = new int[] { 0, 1, 2, 2, 3, 0 };
	
	public Quad(float x, float y, float w, float h, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		super(createVertex(x, y, w, h, z), DEFAULT_INDICES.clone(), new float[] { tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3 });
	}
	
	public Quad(IVec2 pos, IVec2 size, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public Quad(IVec2 pos, float w, float h, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(pos.xFloat(), pos.yFloat(), w, h, z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public Quad(float x, float y, IVec2 size, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(x, y, size.xFloat(), size.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public Quad(float x, float y, float w, float h, ZConstant z, float[] tcs) {
		this(x, y, w, h, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public Quad(IVec2 pos, IVec2 size, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public Quad(IVec2 pos, float w, float h, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), w, h, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public Quad(float x, float y, IVec2 size, ZConstant z, float[] tcs) {
		this(x, y, size.xFloat(), size.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public Quad(float x, float y, float w, float h, ZConstant z) {
		this(x, y, w, h, z, DEFAULT_TCS);
	}
	
	public Quad(IVec2 pos, IVec2 size, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), z, DEFAULT_TCS);
	}
	
	public Quad(IVec2 pos, float w, float h, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), w, h, z, DEFAULT_TCS);
	}
	
	public Quad(float x, float y, IVec2 size, ZConstant z) {
		this(x, y, size.xFloat(), size.yFloat(), z, DEFAULT_TCS);
	}
	
	private static float[] createVertex(float x, float y, float w, float h, ZConstant z) {
		return new float[] { x, y, z.z, x, y + h, z.z, x + w, y + h, z.z, x + w, y, z.z };
	}
}
