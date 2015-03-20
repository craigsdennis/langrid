/*
 * $Id: OverUseLimit.java 225 2010-10-03 00:23:14Z t-nakaguchi $
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

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 225 $
 */
public class OverUseLimit
implements Serializable
{
	/**
	 * 
	 * 
	 */
	public OverUseLimit(){
	}

	/**
	 * 
	 * 
	 */
	public OverUseLimit(
			String period, String limitType, int limitCount
			) {
		this.period = period;
		this.limitType = limitType;
		this.limitCount = limitCount;
	}

	@Override
	public boolean equals(Object value){
		return EqualsBuilder.reflectionEquals(this, value);
	}

	@Override
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 
	 * 
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 
	 * 
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * 
	 * 
	 */
	public String getLimitType() {
		return limitType;
	}

	/**
	 * 
	 * 
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/**
	 * 
	 * 
	 */
	public int getLimitCount() {
		return limitCount;
	}

	/**
	 * 
	 * 
	 */
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	private String period;
	private String limitType;
	private int limitCount;

	private static final long serialVersionUID = 6995749291905772250L;
}
