package main.java.ulibs.gl.gl;

import java.awt.Color;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class GLH {
	public static final int TRUE = GL46.GL_TRUE;
	public static final int FALSE = GL46.GL_FALSE;
	
	public static void readBuffer() {
		GL46.glReadBuffer(GL46.GL_FRONT);
	}
	
	public static ByteBuffer readPixels(int x, int y, int w, int h) {
		ByteBuffer buf = BufferUtils.createByteBuffer(w * h * 4);
		GL46.glReadPixels(x, y, w, h, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, buf);
		return buf;
	}
	
	public static void unbindTexture() {
		GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
	}
	
	public static void enableScissors() {
		GL46.glEnable(GL46.GL_SCISSOR_TEST);
	}
	
	public static void disableScissors() {
		GL46.glDisable(GL46.GL_SCISSOR_TEST);
	}
	
	//TODO make this take normal coords instead of upside down GL coords
	public static void scissor(int x, int y, int w, int h) {
		GL46.glScissor(x, y, w, h);
	}
	
	public static void createCapabilities() {
		GL.createCapabilities();
	}
	
	public static void clearColor(Color color) {
		GL46.glClearColor(color.getRed() / 256f, color.getGreen() / 256f, color.getBlue() / 256f, color.getAlpha() / 256f);
	}
	
	//TODO make this use an enum or something to make my life easier!
	public static int getError() {
		return GL46.glGetError();
	}
	
	public static boolean isError(int error) {
		return error != GL46.GL_NO_ERROR;
	}
	
	public static void clearColorDepthBuffer() {
		GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void setViewport(int x, int y, int w, int h) {
		GL46.glViewport(x, y, w, h);
	}
	
	public static void enableDepth() {
		GL46.glEnable(GL46.GL_DEPTH_TEST);
	}
	
	public static void disableDepth() {
		GL46.glDisable(GL46.GL_DEPTH_TEST);
	}
	
	public static void enableBlend() {
		GL46.glEnable(GL46.GL_BLEND);
		GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void disableBlend() {
		GL46.glDisable(GL46.GL_BLEND);
	}
	
	public static void setActiveTexture0() {
		GL46.glActiveTexture(GL46.GL_TEXTURE0);
	}
	
	public static String getVersion() {
		return GL46.glGetString(GL46.GL_VERSION);
	}
	
	public static void unbindShader() {
		GL46.glUseProgram(0);
	}
}
