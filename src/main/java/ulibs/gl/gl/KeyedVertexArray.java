package main.java.ulibs.gl.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.gl.gl.geometry.GeoData;
import main.java.ulibs.gl.utils.BufferUtils;
import main.java.ulibs.gl.utils.exceptions.GLException;
import main.java.ulibs.gl.utils.exceptions.GLException.Reason;

/** Similar to {@link VertexArray} but instead has control over the elements inside
 * @author -Unknown-
 * @param <T> The Key to use for the {@link VertexArrayData} map
 */
public class KeyedVertexArray<T> {
	private final Map<T, VertexArrayData> map = new LinkedHashMap<T, VertexArrayData>();
	private int vao, vbo, ibo, tbo, count;
	private boolean wasSetup = false, requiresCacheReset = true;
	
	private float[] verticesCache = new float[0], tcsCache = new float[0];
	private int[] indicesCache = new int[0];
	
	public KeyedVertexArray<T> setObjectData(T t, VertexArrayData data) {
		if (!map.containsKey(t)) {
			map.put(t, data);
			return this;
		}
		
		map.get(t).set(data);
		requiresCacheReset = true;
		return this;
	}
	
	public KeyedVertexArray<T> setObjectData(T t, GeoData geodata) {
		VertexArrayData data = new VertexArrayData().add(geodata);
		if (!map.containsKey(t)) {
			map.put(t, data);
		}
		
		map.get(t).set(data);
		requiresCacheReset = true;
		return this;
	}
	
	/** Removed the given key from the map
	 * @param t The key to remove
	 */
	public void removeObject(int t) {
		map.remove(t);
		requiresCacheReset = true;
	}
	
	/** Sets up everything to be ready for rendering
	 * <br>
	 * Must be called before using! */
	public void setup() {
		float[] vertices = getVertices();
		float[] tcs = getTcs();
		int[] indices = getIndices();
		
		count = indices.length;
		if (!wasSetup) {
			wasSetup = true;
			vao = GL46.glGenVertexArrays();
			vbo = GL46.glGenBuffers();
			tbo = GL46.glGenBuffers();
			ibo = GL46.glGenBuffers();
		}
		
		GL46.glBindVertexArray(vao);
		
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
		GL46.glBufferData(GL46.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL46.GL_DYNAMIC_DRAW);
		GL46.glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL46.GL_FLOAT, false, 0, 0);
		GL46.glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, tbo);
		GL46.glBufferData(GL46.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(tcs), GL46.GL_DYNAMIC_DRAW);
		GL46.glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL46.GL_FLOAT, false, 0, 0);
		GL46.glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);
		
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL46.GL_DYNAMIC_DRAW);
		
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
		GL46.glBindVertexArray(0);
	}
	
	/** Binds self to be used for rendering
	 * <br>
	 * Must be called before using! */
	public void bind() {
		if (!wasSetup) {
			Console.print(WarningType.FatalError, "KeyedVertexArray was not setup!", new GLException(Reason.notSetupVertexArray));
		}
		
		GL46.glBindVertexArray(vao);
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, ibo);
	}
	
	/** Unbinds self and frees resources
	 * <br>
	 * Should be called when done! */
	public void unbind() {
		GL46.glBindVertexArray(0);
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	/** Draws the stored data
	 * <br>
	 * Must call {@link #bind} before using! */
	public void draw() {
		GL46.glDrawElements(GL46.GL_TRIANGLES, count, GL46.GL_UNSIGNED_INT, 0);
	}
	
	public void drawOnce() {
		bind();
		draw();
		unbind();
	}
	
	public void reset() {
		wasSetup = false;
		vao = 0;
		vbo = 0;
		ibo = 0;
		tbo = 0;
		count = 0;
		map.clear();
		requiresCacheReset = true;
	}
	
	private float[] getVertices() {
		if (requiresCacheReset) {
			resetCache();
		}
		return verticesCache;
	}
	
	private int[] getIndices() {
		if (requiresCacheReset) {
			resetCache();
		}
		return indicesCache;
	}
	
	private float[] getTcs() {
		if (requiresCacheReset) {
			resetCache();
		}
		return tcsCache;
	}
	
	private void resetCache() {
		requiresCacheReset = false;
		
		int verticiesSize = 0;
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				verticiesSize += gd.vertices.length;
			}
		}
		
		FloatBuffer buf0 = FloatBuffer.allocate(verticiesSize);
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				buf0.put(gd.vertices);
			}
		}
		
		buf0.flip();
		verticesCache = buf0.array();
		
		int indicesSize = 0;
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				indicesSize += gd.indices.length;
			}
		}
		
		IntBuffer buf1 = IntBuffer.allocate(indicesSize);
		int count = 0;
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				int[] ids = gd.indices.clone();
				for (int i = 0; i < ids.length; i++) {
					ids[i] = ids[i] + count * 4;
				}
				
				buf1.put(ids);
				count++;
			}
		}
		
		buf1.flip();
		indicesCache = buf1.array();
		
		int tcsSize = 0;
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				tcsSize += gd.tcs.length;
			}
		}
		
		FloatBuffer buf2 = FloatBuffer.allocate(tcsSize);
		for (VertexArrayData data : map.values()) {
			for (GeoData gd : data.datas) {
				buf2.put(gd.tcs);
			}
		}
		
		buf2.flip();
		tcsCache = buf2.array();
	}
}
