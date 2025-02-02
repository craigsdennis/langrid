package jp.go.nict.langrid.commons.ws.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class OutputStreamServletOutputStream extends ServletOutputStream{
	public OutputStreamServletOutputStream(OutputStream os){
		this.os = os;
	}

	@Override
	public void write(int b) throws IOException {
		os.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		os.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		os.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		os.flush();
	}

	@Override
	public void close() throws IOException {
		os.close();
	}

	private OutputStream os;

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		this.writeListener = writeListener;
		try {
			writeListener.onWritePossible();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private WriteListener writeListener;
}
