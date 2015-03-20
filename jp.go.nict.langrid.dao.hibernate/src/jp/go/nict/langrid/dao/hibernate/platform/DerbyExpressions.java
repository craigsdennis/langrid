/*
 * $Id:DerbyExpressions.java 4384 2007-04-03 08:56:48Z nakaguchi $
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
package jp.go.nict.langrid.dao.hibernate.platform;

/**
 * 
 * 
 * @author $Author:nakaguchi $
 * @version $Revision:4384 $
 */
public class DerbyExpressions implements SQLExpressions{
	public String getMonthExtraction(String fieldName){
		return "month(" + fieldName + ")";
	}

	public String getDateExtraction(String fieldName){
		return "day(" + fieldName + ")";
	}

	public String getHourExtraction(String fieldName){
		return "hour(" + fieldName + ")";
	}

	public String getTableExclusiveLock(String tableName) {
		return "lock table " + tableName + " in exclusive mode";
	}
}
