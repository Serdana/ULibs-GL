package main.java.ulibs.gl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** A few utility methods for converting arrays into their respective buffers
 * @author -Unknown-
 */
public class BufferUtils {
	/** Creates a {@link ByteBuffer} from the given array
	 * @param array The array to turn into a {@link ByteBuffer}
	 * @return A {@link ByteBuffer} with the data from given array
	 */
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}
	
	/** Creates a {@link FloatBuffer} from the given array
	 * @param array The array to turn into a {@link FloatBuffer}
	 * @return A {@link FloatBuffer} with the data from given array
	 */
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}
	
	/** Creates a {@link IntBuffer} from the given array
	 * @param array The array to turn into a {@link IntBuffer}
	 * @return A {@link IntBuffer} with the data from given array
	 */
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
}
