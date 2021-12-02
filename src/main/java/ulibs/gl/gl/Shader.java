package main.java.ulibs.gl.gl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec3f;
import main.java.ulibs.common.math.Vec4f;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.gl.math.Matrix4f;

public abstract class Shader {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public final String name;
	public final int id;
	private final Matrix4f prMatrix;
	
	private final Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	/**
	 * @param name The name of the Shader
	 * @param internalTitle The internal title package name
	 * @param prMatrix The projection matrix to use
	 */
	public Shader(String name, String internalTitle, Matrix4f prMatrix) {
		this.name = name;
		this.id = load(name, internalTitle);
		this.prMatrix = prMatrix;
	}
	
	public final void setup() {
		bind();
		set("projection_matrix", prMatrix);
		internalSetup();
		GLH.unbindShader();
	}
	
	/** Don't worry about binding/unbinding shader here! That's being handled elsewhere */
	protected abstract void internalSetup();
	
	/** Don't worry about binding/unbinding shader here! That's being handled elsewhere */
	public void onResize() {
		
	}
	
	/** Binds the shader for use */
	public void bind() {
		GL46.glUseProgram(id);
	}
	
	protected void set(String name, int value) {
		GL46.glUniform1i(getUniform(name), value);
	}
	
	protected void set(String name, int[] value) {
		GL46.glUniform1iv(getUniform(name), value);
	}
	
	protected void set(String name, float value) {
		GL46.glUniform1f(getUniform(name), value);
	}
	
	protected void set(String name, Vec2f vec) {
		GL46.glUniform2f(getUniform(name), vec.getX(), vec.getY());
	}
	
	protected void set(String name, Vec3f vec) {
		GL46.glUniform3f(getUniform(name), vec.getX(), vec.getY(), vec.getZ());
	}
	
	protected void set(String name, Vec4f vec) {
		GL46.glUniform4f(getUniform(name), vec.getX(), vec.getY(), vec.getZ(), vec.getW());
	}
	
	protected void set(String name, Matrix4f matrix) {
		GL46.glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	protected int getUniform(String name) {
		if (locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		
		int result = GL46.glGetUniformLocation(id, name);
		if (result == -1) {
			Console.print(WarningType.Error, "Could not find uniform variable '" + name + "'!");
		} else {
			locationCache.put(name, result);
		}
		return result;
	}
	
	private int load(String name, String internalTitle) {
		String vert = loadAsString(Shader.class.getResourceAsStream("/main/resources/" + internalTitle + "/assets/shaders/" + name + ".vert"));
		String frag = loadAsString(Shader.class.getResourceAsStream("/main/resources/" + internalTitle + "/assets/shaders/" + name + ".frag"));
		
		int program = GL46.glCreateProgram(), vertID = GL46.glCreateShader(GL46.GL_VERTEX_SHADER), fragID = GL46.glCreateShader(GL46.GL_FRAGMENT_SHADER);
		GL46.glShaderSource(vertID, vert);
		GL46.glShaderSource(fragID, frag);
		
		GL46.glCompileShader(vertID);
		if (GL46.glGetShaderi(vertID, GL46.GL_COMPILE_STATUS) == GLH.FALSE) {
			Console.print(WarningType.Error, "Failed to compile vertex shader!");
			Console.print(WarningType.Error, GL46.glGetShaderInfoLog(vertID));
			return -1;
		}
		
		GL46.glCompileShader(fragID);
		if (GL46.glGetShaderi(fragID, GL46.GL_COMPILE_STATUS) == GLH.FALSE) {
			Console.print(WarningType.Error, "Failed to compile fragment shader!");
			Console.print(WarningType.Error, GL46.glGetShaderInfoLog(fragID));
			return -1;
		}
		
		GL46.glAttachShader(program, vertID);
		GL46.glAttachShader(program, fragID);
		GL46.glLinkProgram(program);
		GL46.glValidateProgram(program);
		
		GL46.glDeleteShader(vertID);
		GL46.glDeleteShader(fragID);
		
		return program;
	}
	
	private String loadAsString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String buffer = "";
			while ((buffer = r.readLine()) != null) {
				sb.append(buffer + "\n");
			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
