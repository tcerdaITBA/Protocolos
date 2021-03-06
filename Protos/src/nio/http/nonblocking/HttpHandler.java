package nio.http.nonblocking;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public abstract class HttpHandler {	
	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;
	private ByteBuffer processedBuffer;
	private SelectionKey connectedPeerKey;
	
	public HttpHandler(int readBufferSize, ByteBuffer writeBuffer, ByteBuffer processedBuffer) {
		this.readBuffer = ByteBuffer.allocate(readBufferSize);
		this.writeBuffer = writeBuffer;
		this.processedBuffer = processedBuffer;
	}
	
	abstract protected void processRead(SelectionKey key);
	abstract protected void process(ByteBuffer inputBuffer, SelectionKey key);
	abstract protected void processWrite(ByteBuffer inputBuffer, SelectionKey key);
	
	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}
	
	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}
	
	public ByteBuffer getProcessedBuffer() {
		return processedBuffer;
	}
		
	public SelectionKey getConnectedPeerKey() {
		return connectedPeerKey;
	}
	
	public void setConnectedPeerKey(SelectionKey connectedPeerKey) {
		this.connectedPeerKey = connectedPeerKey;
	}
	
	public void handleRead(SelectionKey key) {
		processRead(key);
		readBuffer.flip();    // pasa a modo lectura
		process(readBuffer, key);
		readBuffer.compact();  // pasa a modo escritura
	}

	public void handleWrite(SelectionKey key) {
		writeBuffer.flip();
		processWrite(writeBuffer, key);
		writeBuffer.compact();
	}
}
