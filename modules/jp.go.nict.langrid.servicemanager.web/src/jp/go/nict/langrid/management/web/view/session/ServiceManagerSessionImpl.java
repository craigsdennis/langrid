/*
 * $Id: ServiceManagerSessionImpl.java 1506 2015-03-02 16:03:34Z t-nakaguchi $
 * 
 * This is a program for Language Grid Core Node. This combines multiple language resources and
 * provides composite language services. Copyright (C) 2005-2008 NICT Language Grid Project.
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package jp.go.nict.langrid.management.web.view.session;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import jp.go.nict.langrid.management.web.model.enumeration.UserRole;
import jp.go.nict.langrid.management.web.model.exception.ServiceManagerException;
import jp.go.nict.langrid.management.web.model.service.ServiceFactory;
import jp.go.nict.langrid.management.web.model.service.UserService;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

/**
 * 
 * 
 * @author Masaaki Kamiya
 * @author $Author: t-nakaguchi $
 * @version $Revision: 1506 $
 */
public final class ServiceManagerSessionImpl extends WebSession
implements ServiceManagerSession {
	/**
	 * 
	 * 
	 */
	public ServiceManagerSessionImpl(Request request) {
		super(request);
	}

	public final boolean authenticate(String gridId, String userId, String password)
	throws ServiceManagerException {
		try {
			UserService service = ServiceFactory.getInstance().getUserService(gridId);
			if(service.authenticate(userId, password)) {
				this.userId = userId;
				this.password = password;
				this.userGridId = gridId;
				this.userRoles = service.getUserRoles(userId);
				this.isAdministrater = userRoles.contains(UserRole.ADMINISTRATOR);
				int expiredDay = ServiceFactory.getInstance().getGridService()
					.getPasswordExpiredDay();
				isExpired = service.isShouldChangePassword(userId, expiredDay);
				return true;
			}
		} catch(ServiceManagerException e) {
			clearAuthenticatedParameter();
//			if(!e.getErrorCode().equals(LangridError.E002.name())) {
//				throw e;
//			}
		}
		return false;
	}

	public void clearAuthenticatedParameter() {
		if(isLogin()) {
			userId = null;
			password = null;
			isLoginedAccess = false;
			isAdministrater = false;
			isExpired = false;
			invalidateNow();
		}
	}

	public String getPassword() {
		return password;
	}

	public String getSessionId() {
		return getId();
	}

	public String getUserId() {
		return userId;
	}

	public boolean isAdministrater() {
		return isAdministrater;
	}

	public boolean isExpiredPassword() {
		return isExpired;
	}

	public boolean isLogin() {
		return userId != null && password != null;
	}

	public boolean isLoginedAccess() {
		return isLoginedAccess;
	}

	public void setLoginedAccess(boolean init) {
		this.isLoginedAccess = init;
	}

	@Override
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setIsExpiredPassword(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public void setPassword(String newPass) {
		this.password = newPass;
	}

	@Override
	public String getUserGridId() {
		return userGridId;
	}

	/**
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Set<UserRole> userRoles = Collections.EMPTY_SET;
	private boolean isAdministrater = false;
	private boolean isExpired = false;
	/**
	 * 
	 * 
	 */
	private boolean isLoginedAccess = false;
	private String password;
	private String userId;
	private String userGridId;
	private static final long serialVersionUID = 1L;
}
