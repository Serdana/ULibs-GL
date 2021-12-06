package main.java.ulibs.gl.math;

import java.nio.FloatBuffer;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.math.Vec3f;
import main.java.ulibs.common.math.Vec3i;
import main.java.ulibs.common.utils.ICopyable;
import main.java.ulibs.gl.utils.BufferUtils;

public class Matrix4f implements ICopyable<Matrix4f> {
	public static final int SIZE = 16;
	public float[] elements = new float[SIZE];
	
	private Matrix4f() {
		for (int i = 0; i < SIZE; i++) {
			elements[i] = 0f;
		}
	}
	
	public static Matrix4f identity() {
		Matrix4f mat = new Matrix4f();
		mat.elements[0 + 0 * 4] = 1f;
		mat.elements[1 + 1 * 4] = 1f;
		mat.elements[2 + 2 * 4] = 1f;
		mat.elements[3 + 3 * 4] = 1f;
		
		return mat;
	}
	
	public static Matrix4f translate(Matrix4f mat, float x, float y, float z) {
		mat.elements[0 + 3 * 4] += x;
		mat.elements[1 + 3 * 4] += y;
		mat.elements[2 + 3 * 4] += z;
		return mat;
	}
	
	public static Matrix4f newTranslate(Vec2i vec) {
		return translate(Matrix4f.identity(), vec.getX(), vec.getY(), 0);
	}
	
	public static Matrix4f newTranslate(Vec2f vec) {
		return translate(Matrix4f.identity(), vec.getX(), vec.getY(), 0);
	}
	
	public static Matrix4f newTranslate(Vec3i vec) {
		return translate(Matrix4f.identity(), vec.getX(), vec.getY(), vec.getZ());
	}
	
	public static Matrix4f newTranslate(Vec3f vec) {
		return translate(Matrix4f.identity(), vec.getX(), vec.getY(), vec.getZ());
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec2i vec) {
		return translate(mat, vec.getX(), vec.getY(), 0);
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec2f vec) {
		return translate(mat, vec.getX(), vec.getY(), 0);
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec3i vec) {
		return translate(mat, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec3f vec) {
		return translate(mat, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public Matrix4f translate(Vec2i vec) {
		return translate(this, vec.getX(), vec.getY(), 0);
	}
	
	public Matrix4f translate(Vec2f vec) {
		return translate(this, vec.getX(), vec.getY(), 0);
	}
	
	public Matrix4f translate(Vec3i vec) {
		return translate(this, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public Matrix4f translate(Vec3f vec) {
		return translate(this, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public static Matrix4f rotate(Matrix4f mat, float angle) {
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		mat.elements[0 + 0 * 4] = cos;
		mat.elements[1 + 0 * 4] = sin;
		mat.elements[0 + 1 * 4] = -sin;
		mat.elements[1 + 1 * 4] = cos;
		
		return mat;
	}
	
	public static Matrix4f newRotate(float angle) {
		return rotate(Matrix4f.identity(), angle);
	}
	
	public Matrix4f rotate(float angle) {
		return rotate(this, angle);
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f mat = Matrix4f.identity();
		
		mat.elements[0 + 0 * 4] = 2f / (right - left);
		mat.elements[1 + 1 * 4] = 2f / (top - bottom);
		mat.elements[2 + 2 * 4] = 2f / (near - far);
		
		mat.elements[0 + 3 * 4] = (left + right) / (left - right);
		mat.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		mat.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return mat;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f mat = new Matrix4f();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0f;
				for (int e = 0; e < 4; e++) {
					sum += elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				
				mat.elements[x + y * 4] = sum;
			}
		}
		return mat;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}
	
	@Override
	public Matrix4f copy() {
		Matrix4f mat = new Matrix4f();
		for (int i = 0; i < elements.length; i++) {
			mat.elements[i] = elements[i];
		}
		return mat;
	}
}
