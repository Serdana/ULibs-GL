package main.java.ulibs.gl.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import main.java.ulibs.gl.gl.geometry.GeoData;

public class VertexArrayData {
	protected final List<GeoData> datas = new ArrayList<GeoData>();
	
	private float[] verticesCache = new float[0], tcsCache = new float[0];
	private int[] indicesCache = new int[0];
	protected boolean requiresCacheReset = true;
	
	@SuppressWarnings("unchecked")
	public <S extends VertexArrayData> S add(GeoData data) {
		datas.add(data);
		requiresCacheReset = true;
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public <S extends VertexArrayData> S set(List<GeoData> datas) {
		reset();
		for (GeoData data : datas) {
			add(data);
		}
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public <S extends VertexArrayData> S set(GeoData data) {
		reset();
		add(data);
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public <S extends VertexArrayData> S set(VertexArrayData datas) {
		reset();
		this.datas.addAll(datas.datas);
		return (S) this;
	}
	
	public void reset() {
		datas.clear();
		requiresCacheReset = true;
	}
	
	protected float[] getVertices() {
		if (requiresCacheReset) {
			resetCache();
		}
		return verticesCache;
	}
	
	protected int[] getIndices() {
		if (requiresCacheReset) {
			resetCache();
		}
		return indicesCache;
	}
	
	protected float[] getTcs() {
		if (requiresCacheReset) {
			resetCache();
		}
		return tcsCache;
	}
	
	private void resetCache() {
		requiresCacheReset = false;
		
		int verticiesSize = 0;
		for (GeoData gd : datas) {
			verticiesSize += gd.vertices.length;
		}
		
		FloatBuffer buf0 = FloatBuffer.allocate(verticiesSize);
		for (GeoData gd : datas) {
			buf0.put(gd.vertices);
		}
		
		buf0.flip();
		verticesCache = buf0.array();
		
		int indicesSize = 0;
		for (GeoData gd : datas) {
			indicesSize += gd.indices.length;
		}
		
		IntBuffer buf1 = IntBuffer.allocate(indicesSize);
		for (int j = 0; j < datas.size(); j++) {
			int[] ids = datas.get(j).indices.clone();
			for (int i = 0; i < ids.length; i++) {
				ids[i] = ids[i] + j * 4;
			}
			
			buf1.put(ids);
		}
		
		buf1.flip();
		indicesCache = buf1.array();
		
		int tcsSize = 0;
		for (GeoData gd : datas) {
			tcsSize += gd.tcs.length;
		}
		
		FloatBuffer buf2 = FloatBuffer.allocate(tcsSize);
		for (GeoData gd : datas) {
			buf2.put(gd.tcs);
		}
		
		buf2.flip();
		tcsCache = buf2.array();
	}
}
