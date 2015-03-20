/*
 * $Id: MatchingConditionsValidator.java 225 2010-10-03 00:23:14Z t-nakaguchi $
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
package jp.go.nict.langrid.service_1_2.foundation.util.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.foundation.MatchingCondition;
import jp.go.nict.langrid.service_1_2.typed.MatchingMethod;
import jp.go.nict.langrid.service_1_2.util.ParameterValidator;
import jp.go.nict.langrid.service_1_2.util.validator.AbstractObjectValidator;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 225 $
 */
public class MatchingConditionsValidator
	extends AbstractObjectValidator<MatchingConditionsValidator, Object>
{
	/**
	 * 
	 * 
	 */
	public MatchingConditionsValidator(
			String parameterName, MatchingCondition[] conditions
			)
	{
		super(parameterName, conditions);
		if(conditions == null) return;
		original = conditions;
		int i = 0;
		for(MatchingCondition c : conditions){
			this.conditions.add(new MatchingConditionValidator(
					parameterName + "[" + i++ + "]", c
					));
		}
	}

	public MatchingConditionsValidator notNull()
			throws InvalidParameterException
	{
		super.notNull();
		for(MatchingConditionValidator v : conditions)
			v.notNull();
		return this;
	}

	/**
	 * 
	 * 
	 */
	public MatchingConditionsValidator notEmpty()
			throws InvalidParameterException
	{
		ParameterValidator.arrayNotEmpty(getParameterName(), original);
		for(MatchingConditionValidator v : conditions)
			v.notEmpty();
		return this;
	}

	/**
	 * 
	 * 
	 */
	public jp.go.nict.langrid.service_1_2.foundation.typed.MatchingCondition[]
			getMatchingConditions(Set<MatchingMethod> supportedMatchingMethods)
			throws InvalidParameterException
	{
		List<jp.go.nict.langrid.service_1_2.foundation.typed.MatchingCondition>
			ret = new ArrayList<jp.go.nict.langrid.service_1_2.foundation.typed.MatchingCondition>();
		for(MatchingConditionValidator v : conditions)
			ret.add(v.getMatchingCondition(supportedMatchingMethods));
		return ret.toArray(new jp.go.nict.langrid.service_1_2.foundation.typed.MatchingCondition[]{});
	}

	/**
	 * 
	 * 
	 */
	public boolean isEmpty(){
		return original.length == 0;
	}

	private MatchingCondition[] original;
	private List<MatchingConditionValidator> conditions
			= new ArrayList<MatchingConditionValidator>();
}
