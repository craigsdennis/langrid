/*
 * $Id: OverUseMonitorService.java 225 2010-10-03 00:23:14Z t-nakaguchi $
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
package jp.go.nict.langrid.service_1_2.foundation.overusemonitoring;

import java.util.Calendar;

import jp.go.nict.langrid.service_1_2.AccessLimitExceededException;
import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.NoAccessPermissionException;
import jp.go.nict.langrid.service_1_2.ServiceConfigurationException;
import jp.go.nict.langrid.service_1_2.UnknownException;
import jp.go.nict.langrid.service_1_2.foundation.Order;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 225 $
 */
public interface OverUseMonitorService {
	/**
	 * 
	 * 
	 */
	void clearOverUseLimits()
		throws AccessLimitExceededException, NoAccessPermissionException
		, ServiceConfigurationException, UnknownException
		;

	/**
	 * 
	 * 
	 */
	OverUseLimit[] listOverUseLimits(Order[] orders)
		throws AccessLimitExceededException, InvalidParameterException
		, NoAccessPermissionException, ServiceConfigurationException
		, UnknownException
		;

	/**
	 * 
	 * 
	 */
	void setOverUseLimit(
			String period, String limitType, int limitValue)
	throws AccessLimitExceededException, InvalidParameterException
	, NoAccessPermissionException
	, ServiceConfigurationException, UnknownException
	;

	/**
	 * 
	 * 
	 */
	void deleteOverUseUseLimit(
			String period, String limitType)
	throws AccessLimitExceededException, InvalidParameterException
	, NoAccessPermissionException
	, ServiceConfigurationException, UnknownException
	;

	/**
	 * 
	 * 
	 */
	OverUseStateSearchResult searchOverUseState(
			int startIndex, int maxCount
			, Calendar startDateTime, Calendar endDateTime
			, Order[] orders
			)
	throws AccessLimitExceededException, InvalidParameterException
	, NoAccessPermissionException
	, ServiceConfigurationException, UnknownException
	;
}
