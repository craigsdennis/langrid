/*
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
package jp.go.nict.langrid.composite.bilingualdictionary;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.go.nict.langrid.commons.lang.ExceptionUtil;
import jp.go.nict.langrid.commons.transformer.ToStringTransformer;
import jp.go.nict.langrid.commons.util.ArrayUtil;
import jp.go.nict.langrid.commons.util.Pair;
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
import jp.go.nict.langrid.service_1_2.UnsupportedMatchingMethodException;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.BilingualDictionaryService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.Translation;
import jp.go.nict.langrid.service_1_2.typed.MatchingMethod;
import jp.go.nict.langrid.service_1_2.util.validator.LanguagePairValidator;
import jp.go.nict.langrid.service_1_2.util.validator.LanguageValidator;
import jp.go.nict.langrid.service_1_2.util.validator.MatchingMethodValidator;
import jp.go.nict.langrid.service_1_2.util.validator.StringValidator;
import jp.go.nict.langrid.servicecontainer.service.ComponentServiceFactory;
import jp.go.nict.langrid.servicecontainer.service.composite.AbstractCompositeService;
import jp.go.nict.langrid.servicecontainer.service.composite.Invocation;

/**
 * BilingualDictionary cross search implementation.
 * @author Takao Nakaguchi
 */
public class BilingualDictionaryCrossSearch
extends AbstractCompositeService
implements BilingualDictionaryService{
	@Override
	public Iterable<Pair<Invocation, Class<?>>> invocations() {
		List<Pair<Invocation, Class<?>>> ret = new ArrayList<Pair<Invocation,Class<?>>>();
		for(int i = 0; i < 5; i++){
			final int index = i + 1;
			ret.add(new Pair<Invocation, Class<?>>(new Invocation() {
				public Class<? extends Annotation> annotationType() {
					return Invocation.class;
				}
				public boolean required() {
					return false;
				}
				public String name() {
					return "BilingualDictionaryPL" + index;
				}
			}, BilingualDictionaryService.class));
		}
		return ret;
	}

	@Override
	public Translation[] search(String headLang, String targetLang,
			String headWord, String matchingMethod)
	throws AccessLimitExceededException, InvalidParameterException
	, LanguagePairNotUniquelyDecidedException, NoAccessPermissionException
	, NoValidEndpointsException, ProcessFailedException
	, ServerBusyException, ServiceNotActiveException
	, ServiceNotFoundException, UnsupportedLanguagePairException
	, UnsupportedMatchingMethodException
	{
		jp.go.nict.langrid.language.LanguagePair pair = new LanguagePairValidator(
				new LanguageValidator("headLang", headLang)
				, new LanguageValidator("targetLang", targetLang)
				).notNull().trim().notEmpty().getLanguagePair();
		String hw = new StringValidator("headWord", headWord)
				.notNull().trim().getValue();
		String mm = new MatchingMethodValidator(
				"matchingMethod", matchingMethod
				)
				.notNull().trim().notEmpty().getValue();
		String hl = pair.getSource().getCode();
		String tl = pair.getTarget().getCode();

		ComponentServiceFactory factory = getComponentServiceFactory();
		List<Translation> ret = new ArrayList<Translation>();
		for(int i = 1; i <= 5; i++){
			log("invoke " + i + "th dict");
			int c = ret.size();
			String name = "BilingualDictionaryPL" + i;
			try{
				BilingualDictionaryService s = factory.getService(
						name, BilingualDictionaryService.class
						);
				if(s != null){
					for(Translation t : s.search(hl, tl, hw, mm)){
						ret.add(t);
					}
				}
			} catch(ServiceNotActiveException e){
			} catch(ServiceNotFoundException e){
			} catch(Exception e){
				log("exception occurred for the invocation \"" + name + "\": "
						+ ExceptionUtil.getMessageWithStackTrace(e));
			}
			log("invoke " + i + "th dict done with " + (ret.size() - c) + " translations.");
		}
		return ret.toArray(new Translation[]{});
	}

	@Override
	public jp.go.nict.langrid.service_1_2.LanguagePair[] getSupportedLanguagePairs()
			throws AccessLimitExceededException, NoAccessPermissionException,
			NoValidEndpointsException, ProcessFailedException,
			ServerBusyException, ServiceNotActiveException,
			ServiceNotFoundException {
		return new jp.go.nict.langrid.service_1_2.LanguagePair[]{
				new jp.go.nict.langrid.service_1_2.LanguagePair("*", "*")
				};
	}

	@Override
	public String[] getSupportedMatchingMethods()
			throws AccessLimitExceededException, NoAccessPermissionException,
			NoValidEndpointsException, ProcessFailedException,
			ServerBusyException, ServiceNotActiveException,
			ServiceNotFoundException {
		return ArrayUtil.collect(
				MatchingMethod.values()
				, String.class
				, new ToStringTransformer<MatchingMethod>()
				);
	}

	@Override
	public Calendar getLastUpdate() throws AccessLimitExceededException,
			NoAccessPermissionException, NoValidEndpointsException,
			ProcessFailedException, ServerBusyException,
			ServiceNotActiveException, ServiceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
