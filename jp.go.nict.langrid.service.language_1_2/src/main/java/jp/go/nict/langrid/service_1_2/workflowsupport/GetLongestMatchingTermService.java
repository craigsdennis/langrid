/*
 * $Id:SpecializedTranslationService.java 3287 2006-10-12 18:35:26 +0900 (木, 12 10 2006) koyama $
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
package jp.go.nict.langrid.service_1_2.workflowsupport;

import java.util.Calendar;

import jp.go.nict.langrid.commons.rpc.intf.Parameter;
import jp.go.nict.langrid.service_1_2.AccessLimitExceededException;
import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.LanguageNotUniquelyDecidedException;
import jp.go.nict.langrid.service_1_2.NoAccessPermissionException;
import jp.go.nict.langrid.service_1_2.NoValidEndpointsException;
import jp.go.nict.langrid.service_1_2.ProcessFailedException;
import jp.go.nict.langrid.service_1_2.ServerBusyException;
import jp.go.nict.langrid.service_1_2.ServiceNotActiveException;
import jp.go.nict.langrid.service_1_2.ServiceNotFoundException;
import jp.go.nict.langrid.service_1_2.UnsupportedLanguageException;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.Translation;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.TranslationWithPosition;
import jp.go.nict.langrid.service_1_2.morphologicalanalysis.Morpheme;

/**
 * 
 * 
 * @author $Author:koyama $
 * @version $Revision:3287 $
 */
public interface GetLongestMatchingTermService {
	/**
	 * 
	 * 
	 */
	TranslationWithPosition[] getLongestMatchingTerm(
			@Parameter(name="headLang") String headLang
			, @Parameter(name="morphemes") Morpheme[] morphemes
			, @Parameter(name="startIndex") int startIndex
			, @Parameter(name="translations") Translation[] translations)
	throws AccessLimitExceededException, InvalidParameterException
	, LanguageNotUniquelyDecidedException, NoAccessPermissionException
	, NoValidEndpointsException, ProcessFailedException
	, ServerBusyException, ServiceNotActiveException
	, ServiceNotFoundException, UnsupportedLanguageException
	;

	/**
	 * 
	 * 
	 */
	Calendar getLastUpdate()
	throws AccessLimitExceededException, NoAccessPermissionException
	, NoValidEndpointsException, ProcessFailedException
	, ServerBusyException, ServiceNotActiveException
	, ServiceNotFoundException
	;
}
