/*
 * $Id: Federation.java 388 2011-08-23 10:24:50Z t-nakaguchi $
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
package jp.go.nict.langrid.dao.entity;

import java.net.URL;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * @author Takao Nakaguchi
 * @author $Author: t-nakaguchi $
 * @version $Revision: 388 $
 */
@Entity
@IdClass(FederationPK.class)
public class Federation
extends UpdateManagedEntity
{
	/**
	 * 
	 * 
	 */
	public Federation(){
	}

	/**
	 * 
	 * 
	 */
	public Federation(
			String sourceGridId, String targetGridId
			) {
		this.sourceGridId = sourceGridId;
		this.targetGridId = targetGridId;
	}

	/**
	 * 
	 * 
	 */
	public Federation(String sourceGridId, String targetGridId,
			String sourceGridName, String targetGridName,
			String targetGridUserId, String targetGridAccessToken,
			boolean requesting, String targetGridOrganization,
			URL targetGridHomepageUrl, boolean connected) {
		this.sourceGridId = sourceGridId;
		this.targetGridId = targetGridId;
		this.sourceGridName = sourceGridName;
		this.targetGridName = targetGridName;
		this.targetGridUserId = targetGridUserId;
		this.targetGridAccessToken = targetGridAccessToken;
		this.requesting = requesting;
		this.targetGridOrganization = targetGridOrganization;
		this.connected = connected;
		setTargetGridHomepageUrl(targetGridHomepageUrl);
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
	public String getSourceGridId() {
		return sourceGridId;
	}

	/**
	 * 
	 * 
	 */
	public void setSourceGridId(String sourceGridId) {
		this.sourceGridId = sourceGridId;
	}

	/**
	 * 
	 * 
	 */
	public String getTargetGridId() {
		return targetGridId;
	}

	/**
	 * 
	 * 
	 */
	public void setTargetGridId(String targetGridId) {
		this.targetGridId = targetGridId;
	}

	/**
	 * 
	 * 
	 */
	public String getTargetGridUserId() {
		return targetGridUserId;
	}

	/**
	 * 
	 * 
	 */
	public void setTargetGridUserId(String targetGridUserId) {
		this.targetGridUserId = targetGridUserId;
	}

	/**
	 * 
	 * 
	 */
	public String getTargetGridAccessToken() {
		return targetGridAccessToken;
	}

	/**
	 * 
	 * 
	 */
	public void setTargetGridAccessToken(String targetGridAccessToken) {
		this.targetGridAccessToken = targetGridAccessToken;
	}

	/**
	 * 
	 * 
	 */
	public boolean isRequesting() {
		return requesting;
	}

	/**
	 * 
	 * 
	 */
	public void setRequesting(boolean requesting) {
		this.requesting = requesting;
	}
	
	public String getTargetGridOrganization() {
		return targetGridOrganization;
	}

	public void setTargetGridOrganization(String targetGridOrganization) {
		this.targetGridOrganization = targetGridOrganization;
	}

	public URL getTargetGridHomepageUrl() {
		if(targetGridHomepageUrl == null) return null;
		return targetGridHomepageUrl.getValue();
	}

	public void setTargetGridHomepageUrl(URL targetGridHomepageUrl) {
		if(targetGridHomepageUrl == null){
			this.targetGridHomepageUrl = null;
		} else if(this.targetGridHomepageUrl == null){
			this.targetGridHomepageUrl = new EmbeddableStringValueClass<URL>(targetGridHomepageUrl);
		} else{
			this.targetGridHomepageUrl.setValue(targetGridHomepageUrl);
		}
	}
	public String getSourceGridName() {
		return sourceGridName;
	}

	public void setSourceGridName(String sourceGridName) {
		this.sourceGridName = sourceGridName;
	}

	public String getTargetGridName() {
		return targetGridName;
	}

	public void setTargetGridName(String targetGridName) {
		this.targetGridName = targetGridName;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public boolean isConnected() {
		return connected;
	}
	public boolean isSymmetric() {
		return symmetric != null && symmetric;
	}
	public void setSymmetric(boolean symmetric) {
		this.symmetric = symmetric;
	}
	public boolean isTransitive() {
		return transitive != null && transitive;
	}
	public void setTransitive(boolean transitive) {
		this.transitive = transitive;
	}

	@Id
	private String sourceGridId;
	@Id
	private String targetGridId;

	private String sourceGridName;
	private String targetGridName;
	
	private String targetGridUserId;
	private String targetGridAccessToken;
	private boolean requesting;
	
	private String targetGridOrganization;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="stringValue", column=@Column(name="targetGridHomepageUrlStringValue"))
		, @AttributeOverride(name="clazz", column=@Column(name="targetGridHomepageUrlClazz"))
	})
	private EmbeddableStringValueClass<URL> targetGridHomepageUrl;
	
	private boolean connected;
	@Column(name="symmetricRel")
	private Boolean symmetric = false;
	private Boolean transitive = false;
}
