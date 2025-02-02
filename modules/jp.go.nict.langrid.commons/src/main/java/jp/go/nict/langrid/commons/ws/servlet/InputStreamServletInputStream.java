/*
 * $Id: InputStreamServletInputStream.java 194 2010-10-02 11:50:58Z t-nakaguchi $
 *
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2010 NICT Language Grid Project.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package jp.go.nict.langrid.commons.ws.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * 
 * 
 * @author Takao Nakaguchi
 * @author $Author: t-nakaguchi $
 * @version $Revision: 194 $
 */
public class InputStreamServletInputStream extends ServletInputStream {
	/**
	 * 
	 * 
	 */
	public InputStreamServletInputStream(InputStream stream){
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		int ret = stream.read();
		this.finished = ret == -1;
		return ret;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int ret = stream.read(b);
		this.finished = ret == -1;
		return ret;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int ret = stream.read(b, off, len);
		this.finished = ret == -1;
		return ret;
	}

	@Override
	public void close() throws IOException {
		stream.close();
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		this.readListener = readListener;
		try {
			this.readListener.onDataAvailable();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ReadListener readListener;
	private boolean finished;
	private InputStream stream;
}
