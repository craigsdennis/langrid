/*
 * $Id:HibernateAccessRightDao.java 4384 2007-04-03 08:56:48Z nakaguchi $
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
package jp.go.nict.langrid.dao.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * @author Masaaki Kamiya
 * @author $Author: t-nakaguchi $
 * @version $Revision: 207 $
 */
@Embeddable
public class ResourceAttributePK implements Serializable{
	/**
	 * 
	 * 
	 */
	public ResourceAttributePK(){
		recalcHashCode();
	}
	/**
	 * 
	 * 
	 */
	public ResourceAttributePK(
			String gridId, String resourceId, String name
			) {
		this.gridId = gridId;
		this.resourceId = resourceId;
		this.name = name;
		recalcHashCode();
	}

	@Override
	public boolean equals(Object value){
		return EqualsBuilder.reflectionEquals(this, value);
	}

	@Override
	public int hashCode(){
		return hashCode;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 
	 * 
	 */
	public String getGridId() {
		return gridId;
	}

	/**
	 * 
	 * 
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
		recalcHashCode();
	}

	/**
	 * 
	 * 
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * 
	 * 
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * 
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 
	 */
	public void setName(String name) {
		this.name = name;
		recalcHashCode();
	}

	private void recalcHashCode(){
		hashCode = new HashCodeBuilder()
			.append(gridId)
			.append(resourceId)
			.append(name)
			.toHashCode();
	}

	private transient int hashCode;
	private String gridId;
	private String resourceId;
	private String name;

	private static final long serialVersionUID = -3429033728781189053L;
}
