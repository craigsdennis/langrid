/*
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2013 NICT Language Grid Project.
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
package jp.go.nict.langrid.client;

import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

import jp.go.nict.langrid.commons.cs.binding.BindingNode;

public interface RequestAttributes {
	void setUserId(String userId);
	void setPassword(String password);
	void setAuthMethod(AuthMethod method);
	void setConnectTimeout(int timeoutMillis);
	void setTimeout(int timeoutMillis);

	/**
	 * Set a boolean value for requestContentCompression.
	 * If true, client compress request content before send it to server and
	 * add Content-Encoding header to request headers.
	 * default: false.
	 * @param requestContentCompression
	 */
	void setRequestContentCompression(boolean requestContentCompression);
	void setRequestContentCompressionThreashold(int bytes);
	/**
	 * Set a algorithm of request content compression.
	 * Currently "gzip" and "deflate" are supported.
	 * default: deflate.
	 * @param algorithm
	 */
	void setRequestContentCompressionAlgorithm(String algorithm);
	/**
	 * Set a boolean value for responseContentCompression.
	 * If true, client may request response content compression to server and
	 * decompress compressed content before it is parses as response.
	 * default: true.
	 * @param responseContentCompression
	 */
	void setResponseContentCompression(boolean responseContentCompression);
	void addRequestMimeHeader(String name, String value);
	void addRequestMimeHeaders(Map<String, Object> headers);
	void addRequestRpcHeader(String namespace, String name, String value);
	void addRequestRpcHeaders(Map<QName, Object> headers);
	void setUserParam(String param);
	Collection<BindingNode> getTreeBindings();
}
