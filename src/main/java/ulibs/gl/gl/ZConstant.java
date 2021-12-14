package main.java.ulibs.gl.gl;

//TODO write docs
@SuppressWarnings("javadoc")
public class ZConstant {
	public static final ZConstant Z_BACKGROUND = new ZConstant();
	public static final ZConstant Z_WORLD = new ZConstant();
	public static final ZConstant Z_WORLD_OVERLAY_0 = new ZConstant();
	public static final ZConstant Z_WORLD_ENTITY = new ZConstant();
	public static final ZConstant Z_WORLD_PARTICLE_0 = new ZConstant();
	public static final ZConstant Z_WORLD_PLAYER = new ZConstant();
	public static final ZConstant Z_WORLD_PARTICLE_1 = new ZConstant();
	public static final ZConstant Z_WORLD_OVERLAY_1 = new ZConstant();
	public static final ZConstant Z_HUD_BACKGROUND = new ZConstant();
	public static final ZConstant Z_HUD_BASE = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL_0 = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL_1 = new ZConstant();
	public static final ZConstant Z_HUD_DETAIL_2 = new ZConstant();
	public static final ZConstant Z_HUD_TEXT = new ZConstant();
	public static final ZConstant Z_HUD_MOUSE = new ZConstant();
	public static final ZConstant Z_HUD_MOUSE_TEXT = new ZConstant();
	
	private static float zz = 0;
	
	public final float z;
	
	private ZConstant() {
		this.z = zz;
		zz += 0.1f;
	}
	
	public ZConstant(float z) {
		this.z = z;
	}
}
