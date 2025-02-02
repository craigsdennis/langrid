/*
 * $Id: ServletContextParameterContext.java 194 2010-10-02 11:50:58Z t-nakaguchi $
 *
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2009 NICT Language Grid Project.
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
package jp.go.nict.langrid.commons.ws.param;

import javax.servlet.ServletContext;

import jp.go.nict.langrid.commons.parameter.ParameterContext;

/**
 * 
 * 
 * @author Masaaki Kamiya
 * @author $Author: t-nakaguchi $
 * @version $Revision: 194 $
 */
public class ServletContextParameterContext extends ParameterContext{
	/**
	 * 
	 * 
	 */
	public ServletContextParameterContext(ServletContext context){
		this.context = context;
	}

	public String getValue(String name) {
		return context.getInitParameter(name);
	}

	private ServletContext context;
}
