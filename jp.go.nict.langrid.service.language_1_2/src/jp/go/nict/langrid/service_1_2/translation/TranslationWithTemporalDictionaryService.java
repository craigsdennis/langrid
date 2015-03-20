/*
 * $Id: TranslationWithTemporalDictionaryService.java 903 2013-07-12 04:29:01Z t-nakaguchi $
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
package jp.go.nict.langrid.service_1_2.translation;

import jp.go.nict.langrid.commons.rpc.intf.Parameter;
import jp.go.nict.langrid.commons.rpc.intf.Service;
import jp.go.nict.langrid.service_1_2.AccessLimitExceededException;
import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.LanguagePairNotUniquelyDecidedException;
import jp.go.nict.langrid.service_1_2.NoAccessPermissionException;
import jp.go.nict.langrid.service_1_2.NoValidEndpointsException;
import jp.go.nict.langrid.service_1_2.ProcessFailedException;
import jp.go.nict.langrid.service_1_2.ServerBusyException;
import jp.go.nict.langrid.service_1_2.ServiceNotActiveException;
import jp.go.nict.langrid.service_1_2.ServiceNotFoundException;
import jp.go.nict.langrid.service_1_2.UnsupportedLanguagePairException;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.Translation;

/**
 * 
 * The interface for translation using a temporary dictionary.
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 903 $
 */
@Service(namespace="servicegrid:servicetype:nict.nlp:TranslationWithTemporalDictionary")
public interface TranslationWithTemporalDictionaryService {
	/**
	 * 
	 * Run translation.
	 * @param sourceLang Translation source language (RFC3066 compliant)
	 * @param targetLang Target language of the translation (RFC3066 compliant)
	 * @param source String to be translated
	 * @param temporalDict Bilingual dictionary data used during translation
	 * @param dictTargetLang Target language of dictionary data(targetLang)
	 * @return The translated string
	 * @throws AccessLimitExceededException Violated an access restriction
	 * @throws InvalidParameterException An invalid parameter was passed
	 * @throws LanguagePairNotUniquelyDecidedException Multiple candidate language pairs exist
	 * @throws NoAccessPermissionException The user executing the call lacks execution privileges
	 * @throws NoValidEndpointsException There is no valid endpoint
	 * @throws ProcessFailedException Translation process failed
	 * @throws ServerBusyException The server is loaded and cannot process it.
	 * @throws ServiceNotActiveException The service is not active
	 * @throws ServiceNotFoundException The specified service was not found
	 * @throws UnsupportedLanguagePairException An unsupported language pair was specified
	 * 
	 */
	String translate(
			@Parameter(name="sourceLang") String sourceLang
			, @Parameter(name="targetLang") String targetLang
			, @Parameter(name="source") String source
			, @Parameter(name="temporalDict") Translation[] temporalDict
			, @Parameter(name="dictTargetLang") String dictTargetLang)
	throws AccessLimitExceededException, InvalidParameterException
	, LanguagePairNotUniquelyDecidedException, NoAccessPermissionException
	, ProcessFailedException, NoValidEndpointsException
	, ServerBusyException, ServiceNotActiveException
	, ServiceNotFoundException, UnsupportedLanguagePairException
	;
}
