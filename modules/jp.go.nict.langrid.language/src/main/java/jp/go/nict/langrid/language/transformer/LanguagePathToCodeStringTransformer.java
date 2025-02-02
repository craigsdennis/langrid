/*
 * $Id: LanguagePathToCodeStringTransformer.java 217 2010-10-02 14:45:56Z t-nakaguchi $
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
package jp.go.nict.langrid.language.transformer;

import jp.go.nict.langrid.commons.transformer.TransformationException;
import jp.go.nict.langrid.commons.transformer.Transformer;
import jp.go.nict.langrid.language.LanguagePath;
import jp.go.nict.langrid.language.util.LanguagePathUtil;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 217 $
 */
public class LanguagePathToCodeStringTransformer
implements Transformer<LanguagePath, String>{
	public String transform(LanguagePath value) throws TransformationException {
		if(value == null) return null;
		return LanguagePathUtil.encodeLanguagePath(value);
	}
}
