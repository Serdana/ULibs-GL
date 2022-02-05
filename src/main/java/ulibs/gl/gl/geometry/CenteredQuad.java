package main.java.ulibs.gl.gl.geometry;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.IVec2;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.gl.gl.ZConstant;

public class CenteredQuad extends GeoData {
	public static final int[] DEFAULT_INDICES = new int[] { 0, 1, 2, 2, 3, 0 };
	
	public CenteredQuad(float x, float y, float w, float h, float xo, float yo, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
			float ty3) {
		super(createCenteredVertex(x, y, w, h, xo, yo, z), DEFAULT_INDICES.clone(), new float[] { tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3 });
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, IVec2 offset, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, float xo, float yo, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
			float ty3) {
		this(pos.xFloat(), pos.yFloat(), w, h, xo, yo, z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, float xo, float yo, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
			float ty3) {
		this(x, y, size.xFloat(), size.yFloat(), xo, yo, z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(float x, float y, float w, float h, IVec2 offset, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
			float ty3) {
		this(x, y, w, h, offset.xFloat(), offset.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, IVec2 offset, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(pos.xFloat(), pos.yFloat(), w, h, offset.xFloat(), offset.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, float xo, float yo, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), xo, yo, z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, IVec2 offset, ZConstant z, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
		this(x, y, size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, tx0, ty0, tx1, ty1, tx2, ty2, tx3, ty3);
	}
	
	public CenteredQuad(float x, float y, float w, float h, float xo, float yo, ZConstant z, float[] tcs) {
		this(x, y, w, h, xo, yo, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, IVec2 offset, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, float xo, float yo, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), w, h, xo, yo, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, float xo, float yo, ZConstant z, float[] tcs) {
		this(x, y, size.xFloat(), size.yFloat(), xo, yo, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(float x, float y, float w, float h, IVec2 offset, ZConstant z, float[] tcs) {
		this(x, y, w, h, offset.xFloat(), offset.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, IVec2 offset, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), w, h, offset.xFloat(), offset.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, float xo, float yo, ZConstant z, float[] tcs) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), xo, yo, z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, IVec2 offset, ZConstant z, float[] tcs) {
		this(x, y, size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, tcs[0], tcs[1], tcs[2], tcs[3], tcs[4], tcs[5], tcs[6], tcs[7]);
	}
	
	public CenteredQuad(float x, float y, float w, float h, float xo, float yo, ZConstant z) {
		this(x, y, w, h, xo, yo, z, DEFAULT_TCS);
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, IVec2 offset, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, DEFAULT_TCS);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, float xo, float yo, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), w, h, xo, yo, z, DEFAULT_TCS);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, float xo, float yo, ZConstant z) {
		this(x, y, size.xFloat(), size.yFloat(), xo, yo, z, DEFAULT_TCS);
	}
	
	public CenteredQuad(float x, float y, float w, float h, IVec2 offset, ZConstant z) {
		this(x, y, w, h, offset.xFloat(), offset.yFloat(), z, DEFAULT_TCS);
	}
	
	public CenteredQuad(IVec2 pos, float w, float h, IVec2 offset, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), w, h, offset.xFloat(), offset.yFloat(), z, DEFAULT_TCS);
	}
	
	public CenteredQuad(IVec2 pos, IVec2 size, float xo, float yo, ZConstant z) {
		this(pos.xFloat(), pos.yFloat(), size.xFloat(), size.yFloat(), xo, yo, z, DEFAULT_TCS);
	}
	
	public CenteredQuad(float x, float y, IVec2 size, IVec2 offset, ZConstant z) {
		this(x, y, size.xFloat(), size.yFloat(), offset.xFloat(), offset.yFloat(), z, DEFAULT_TCS);
	}
	
	public static float[] createCenteredVertex(float x, float y, float w, float h, float xo, float yo, ZConstant z) {
		if (!MathH.within(xo, x, w) || !MathH.within(yo, y, h)) {
			Console.print(WarningType.Error, "xo/yo cannot be less than x/y or more than w/h!");
			return new float[0];
		}
		
		return new float[] { x - xo, y - yo, z.z, x - xo, y + h - yo, z.z, x + w - xo, y + h - yo, z.z, x + w - xo, y - yo, z.z };
	}
}
