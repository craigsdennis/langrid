/*
 * $Id: FromStringTransformer.java 182 2010-10-02 03:16:36Z t-nakaguchi $
 *
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2005-2008 NICT Language Grid Project.
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
package jp.go.nict.langrid.commons.transformer;

import java.lang.reflect.Constructor;

/**
 * 
 * 
 * @author Takao Nakaguchi
 * @author $Author: t-nakaguchi $
 * @version $Revision: 182 $
 */
public class FromStringTransformer<U> implements Transformer<String, U>{
	/**
	 * 
	 * 
	 */
	public FromStringTransformer(Class<U> clazz){
		try{
			ctor = clazz.getConstructor(String.class);
		} catch(NoSuchMethodException e){
			throw new RuntimeException(e);
		}
	}

	public U transform(String value) throws TransformationException {
		return TransformerUtil.fromString(ctor, value);
	}

	private Constructor<U> ctor;
}
