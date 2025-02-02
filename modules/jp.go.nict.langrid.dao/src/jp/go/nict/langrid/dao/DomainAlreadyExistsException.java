/*
 * $Id: DomainAlreadyExistsException.java 214 2010-10-02 14:32:38Z t-nakaguchi $
 *
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2005-2009 NICT Language Grid Project.
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
package jp.go.nict.langrid.dao;

/**
 * 
 * 
 * @author Takao Nakaguchi
 * @author $Author: t-nakaguchi $
 * @version $Revision: 214 $
 */
public class DomainAlreadyExistsException extends DaoException {
	/**
	 * 
	 * 
	 */
	public DomainAlreadyExistsException(String domainId) {
		super(toMessage(domainId));
		this.domainId = domainId;
	}

	/**
	 * 
	 * 
	 */
	public DomainAlreadyExistsException(String domainId, Throwable cause){
		super(toMessage(domainId), cause);
		this.domainId = domainId;
	}

	/**
	 * 
	 * 
	 */
	public String getDomainId(){
		return domainId;
	}

	private String domainId;

	private static String toMessage(String domainId){
		return "domain \"" + domainId + "\" is already exists.";
	}

	private static final long serialVersionUID = -5322379700569323479L;
}
