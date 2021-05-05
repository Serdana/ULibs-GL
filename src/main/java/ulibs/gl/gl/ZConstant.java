package main.java.ulibs.gl.gl;

public class ZConstant {
	public static final ZConstant Z_WORLD = new ZConstant();
	public static final ZConstant Z_WORLD_ENTITY = new ZConstant();
	public static final ZConstant Z_WORLD_PLAYER = new ZConstant();
	public static final ZConstant Z_WORLD_OVERLAY = new ZConstant();
	public static final ZConstant Z_HUD_BACKGROUND = new ZConstant();
	public static final ZConstant Z_HUD_BASE = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL0 = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL1 = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL2 = new ZConstant();
	public static final ZConstant Z_HUD_TEXT = new ZConstant();
	public static final ZConstant Z_HUD_MOUSE = new ZConstant();
	public static final ZConstant Z_HUD_MOUSE_TEXT = new ZConstant();
	
	private static float zz = 0;
	
	public final float z;
	
	private ZConstant() {
		this.z = zz;
		zz += 0.1f;
	}
}
