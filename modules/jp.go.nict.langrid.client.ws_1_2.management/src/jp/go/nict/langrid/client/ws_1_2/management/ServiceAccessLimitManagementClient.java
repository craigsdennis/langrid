/*
 * $Id: ServiceAccessLimitManagementClient.java 370 2011-08-19 10:10:18Z t-nakaguchi $
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
package jp.go.nict.langrid.client.ws_1_2.management;

import jp.go.nict.langrid.client.ws_1_2.ServiceClient;
import jp.go.nict.langrid.client.ws_1_2.error.LangridException;
import jp.go.nict.langrid.service_1_2.foundation.serviceaccesslimitmanagement.AccessLimit;
import jp.go.nict.langrid.service_1_2.foundation.serviceaccesslimitmanagement.AccessLimitSearchResult;
import jp.go.nict.langrid.service_1_2.foundation.serviceaccesslimitmanagement.typed.LimitType;
import jp.go.nict.langrid.service_1_2.foundation.typed.Order;
import jp.go.nict.langrid.service_1_2.foundation.typed.Period;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 370 $
 */
public interface ServiceAccessLimitManagementClient extends ServiceClient{
	/**
	 * 
	 * 
	 */
	void clear() throws LangridException; 
	
	/**
	 * 
	 * 
	 */
	AccessLimitSearchResult searchAccessLimits(
			int startIndex, int maxCount
			, String userId, String serviceId
			, Order[] orders
			)
		throws LangridException;

	/**
	 * 
	 * 
	 */
	public AccessLimit[] getMyAccessLimits(String serviceId)
	throws LangridException;

	/**
	 * 
	 * 
	 */
	void setAccessLimit(String userId, String serviceId,
			Period period, LimitType limitType, int limitCount
			)
	throws LangridException;

	/**
	 * 
	 * Deletes access restriction.
	 * @param userId User ID
	 * @param serviceId Service ID
	 * @param period Units of period
	 * @param limitType Restriction type
	 * @param limitCount Restriction value
	 * @throws LangridException
	 * 
	 */
	void deleteAccessLimit(String userId, String serviceId
			, Period period, LimitType limitType)
		throws LangridException;
}
