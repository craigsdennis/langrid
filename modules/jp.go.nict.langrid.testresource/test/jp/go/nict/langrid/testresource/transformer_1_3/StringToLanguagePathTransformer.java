/*
 * $Id: StringToLanguagePathTransformer.java 120 2010-04-24 04:47:40Z t-nakaguchi $
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
package jp.go.nict.langrid.testresource.transformer_1_3;

import jp.go.nict.langrid.commons.transformer.TransformationException;
import jp.go.nict.langrid.commons.transformer.Transformer;
//import jp.go.nict.langrid.service.nlp.ws_1_3.LanguagePath;
import jp.go.nict.langrid.service_1_2.LanguagePath;

public class StringToLanguagePathTransformer
implements Transformer<String, LanguagePath>
{
	public LanguagePath transform(String value)
	throws TransformationException {
		return new LanguagePath(value.split(" "));
	}
}
