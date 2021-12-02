package main.java.ulibs.gl.math;

import java.nio.FloatBuffer;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.math.Vec3f;
import main.java.ulibs.common.utils.ICopyable;
import main.java.ulibs.gl.utils.BufferUtils;

public class Matrix4f implements ICopyable<Matrix4f> {
	public static final int SIZE = 16;
	public float[] elements = new float[SIZE];
	
	public static Matrix4f identity() {
		Matrix4f mat = new Matrix4f();
		for (int i = 0; i < SIZE; i++) {
			mat.elements[i] = 0f;
		}
		
		mat.elements[0 + 0 * 4] = 1f;
		mat.elements[1 + 1 * 4] = 1f;
		mat.elements[2 + 2 * 4] = 1f;
		mat.elements[3 + 3 * 4] = 1f;
		
		return mat;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f mat = identity();
		
		mat.elements[0 + 0 * 4] = 2f / (right - left);
		mat.elements[1 + 1 * 4] = 2f / (top - bottom);
		mat.elements[2 + 2 * 4] = 2f / (near - far);
		
		mat.elements[0 + 3 * 4] = (left + right) / (left - right);
		mat.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		mat.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return mat;
	}
	
	public static Matrix4f translate(Vec2f vec) {
		return translate(identity(), new Vec3f(vec.getX(), vec.getY(), 0));
	}
	
	public static Matrix4f translate(Vec2i vec) {
		return translate(identity(), new Vec3f(vec.getX(), vec.getY(), 0));
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec2f vec) {
		return translate(mat, new Vec3f(vec.getX(), vec.getY(), 0));
	}
	
	public static Matrix4f translate(Vec3f vec) {
		return translate(identity(), vec);
	}
	
	public static Matrix4f translate(Matrix4f mat, Vec3f vec) {
		mat.elements[0 + 3 * 4] += vec.getX();
		mat.elements[1 + 3 * 4] += vec.getY();
		mat.elements[2 + 3 * 4] += vec.getZ();
		return mat;
	}
	
	public static Matrix4f rotate(float angle) {
		Matrix4f mat = identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		mat.elements[0 + 0 * 4] = cos;
		mat.elements[1 + 0 * 4] = sin;
		mat.elements[0 + 1 * 4] = -sin;
		mat.elements[1 + 1 * 4] = cos;
		
		return mat;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f mat = new Matrix4f();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
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
