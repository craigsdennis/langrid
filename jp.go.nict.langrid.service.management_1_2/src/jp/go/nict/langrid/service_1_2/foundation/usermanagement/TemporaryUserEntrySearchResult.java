/*
 * $Id: TemporaryUserEntrySearchResult.java 225 2010-10-03 00:23:14Z t-nakaguchi $
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
package jp.go.nict.langrid.service_1_2.foundation.usermanagement;

import java.io.Serializable;

import jp.go.nict.langrid.service_1_2.foundation.SearchResult;

/**
 * 
 * Returns the result of the temporary user data search.
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 225 $
 */
public class TemporaryUserEntrySearchResult
extends SearchResult
implements Serializable
{
	/**
	 * 
	 * Constructor.
	 * 
	 */
	public TemporaryUserEntrySearchResult(){
	}

	/**
	 * 
	 * Constructor.
	 * @param elements Array of temporary user entries
	 * @param totalCount Total number of temporary user entry data
	 * @param totalCountFixed Whether or not we fixed the total number
	 * 
	 */
	public TemporaryUserEntrySearchResult(
			TemporaryUserEntry[] elements, int totalCount
			, boolean totalCountFixed)
	{
		super(totalCount, totalCountFixed);
		this.elements = elements;
	}

	/**
	 * 
	 * Sets temporary user entry array.
	 * @param elements User entry array specified
	 * 
	 */
	public void setElements(TemporaryUserEntry[] elements){
		this.elements = elements;
	}

	/**
	 * 
	 * Returns temporary user entry array.
	 * @return Array of user entries
	 * 
	 */
	public TemporaryUserEntry[] getElements(){
		return elements;
	}

	private TemporaryUserEntry[] elements = new TemporaryUserEntry[]{};

	private static final long serialVersionUID = -6434451952600835258L;
}
