package main.java.ulibs.gl.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.gl.utils.BufferUtils;
import main.java.ulibs.gl.utils.exceptions.GLException;
import main.java.ulibs.gl.utils.exceptions.GLException.Reason;

/** Similar to {@link VertexArray} but instead has control over the elements inside
 * @author -Unknown-
 * @param <T> The Key to use for the {@link QuadData} map
 */
public class KeyedVertexArray<T> {
	private final Map<T, QuadData> map = new LinkedHashMap<T, QuadData>();
	private int vao, vbo, ibo, tbo, count;
	private boolean wasSetup = false;
	
	/** Sets vertices for the given key
	 * @param t The key to use
	 * @param data The QuadData to set to the given key
	 * @return self
	 */
	public KeyedVertexArray<T> setObjectQuadData(T t, QuadData data) {
		if (!map.containsKey(t)) {
			map.put(t, data);
			return this;
		}
		
		map.get(t).setAll(data);
		return this;
	}
	
	/** Sets vertices/indices/tcs for the given key
	 * @param t The key to use
	 * @param vertices The vertices to set to the given key
	 * @param indices The indices to set to the given key
	 * @param tcs The texture coords to set to the given key
	 * @return self
	 */
	public KeyedVertexArray<T> setObjectQuadData(T t, float[] vertices, int[] indices, float[] tcs) {
		QuadData data = new QuadData().addAll(vertices, indices, tcs);
		if (!map.containsKey(t)) {
			map.put(t, data);
		}
		
		map.get(t).setAll(data);
		return this;
	}
	
	/** Sets vertices for the given key
	 * @param t The key to use
	 * @param vertices The vertices to set to the given key
	 * @return self
	 */
	public KeyedVertexArray<T> setObjectVertices(T t, float[] vertices) {
		if (!map.containsKey(t)) {
			map.put(t, new QuadData());
		}
		
		map.get(t).setVertices(vertices);
		return this;
	}
	
	/** Sets indices for the given key
	 * @param t The key to use
	 * @param indices The indices to set to the given key
	 * @return self
	 */
	public KeyedVertexArray<T> setObjectIndices(T t, int[] indices) {
		if (!map.containsKey(t)) {
			map.put(t, new QuadData());
		}
		
		map.get(t).setIndices(indices);
		return this;
	}
	
	/** Sets texture coords for the given key
	 * @param t The key to use
	 * @param tcs The texture coords to set to the given key
	 * @return self
	 */
	public KeyedVertexArray<T> setObjectTcs(T t, float[] tcs) {
		if (!map.containsKey(t)) {
			map.put(t, new QuadData());
		}
		
		map.get(t).setTcs(tcs);
		return this;
	}
	
	/** Removed the given key from the map
	 * @param t The key to remove
	 */
	public void removeObject(int t) {
		map.remove(t);
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
	}
	
	private float[] getVertices() {
		int verticiesSize = 0;
		for (QuadData l : map.values()) {
			verticiesSize += l.vertices.length;
		}
		
		FloatBuffer buf = FloatBuffer.allocate(verticiesSize);
		for (QuadData l : map.values()) {
			buf.put(l.vertices);
		}
		
		buf.flip();
		return buf.array();
	}
	
	private int[] getIndices() {
		int indicesSize = 0;
		for (QuadData l : map.values()) {
			indicesSize += l.indices.length;
		}
		
		IntBuffer buf = IntBuffer.allocate(indicesSize);
		int size = 0;
		for (QuadData l : map.values()) {
			int[] ids = l.indices.clone();
			for (int i = 0; i < ids.length; i++) {
				ids[i] = ids[i] + size * 4;
			}
			
			buf.put(ids);
			size += l.indices.length / 6;
		}
		
		buf.flip();
		return buf.array();
	}
	
	private float[] getTcs() {
		int tcsSize = 0;
		for (QuadData l : map.values()) {
			tcsSize += l.tcs.length;
		}
		
		FloatBuffer buf = FloatBuffer.allocate(tcsSize);
		for (QuadData l : map.values()) {
			buf.put(l.tcs);
		}
		
		buf.flip();
		return buf.array();
	}
}
