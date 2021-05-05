package main.java.ulibs.gl.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.gl.utils.BufferUtils;

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
	 * @param vertices The vertices to set to the given key
	 */
	public void setObjectVertices(T t, float[] vertices) {
		map.get(t).setVertices(vertices);
	}
	
	/** Sets indices for the given key
	 * @param t The key to use
	 * @param indices The indices to set to the given key
	 */
	public void setObjectIndices(T t, int[] indices) {
		int n = 0;
		for (Entry<T, QuadData> pair : map.entrySet()) {
			if (pair.getKey().equals(t)) {
				break;
			}
			n++;
		}
		
		int[] ids = indices.clone();
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i] + n * 4;
		}
		
		map.get(t).setIndices(ids);
	}
	
	/** Sets texture coords for the given key
	 * @param t The key to use
	 * @param tcs The texture coords to set to the given key
	 */
	public void setObjectTcs(T t, float[] tcs) {
		map.get(t).setTcs(tcs);
	}
	
	/** Adds a new key to the map
	 * @param t The key to use
	 * @param data The data to set to the given key
	 */
	public void addNewObject(T t, QuadData data) {
		int[] ids = data.indices.clone();
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i] + map.size() * 4;
		}
		
		data.setIndices(ids);
		map.put(t, data);
	}
	
	/** Adds a new key to the map
	 * @param t The key to use
	 * @param vertices The vertices to set to the given key
	 * @param indices The indices to set to the given key
	 * @param tcs The texture coords to set to the given key
	 */
	public void addNewObject(T t, float[] vertices, int[] indices, float[] tcs) {
		addNewObject(t, new QuadData().addAll(vertices, indices, tcs));
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
			Console.print(WarningType.FatalError, "KeyedVertexArray was not setup!", true);
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
		for (QuadData l : map.values()) {
			buf.put(l.indices);
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
