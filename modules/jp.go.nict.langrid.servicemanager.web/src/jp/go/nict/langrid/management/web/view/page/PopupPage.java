/*
 * $Id: PopupPage.java 406 2011-08-25 02:12:29Z t-nakaguchi $
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
package jp.go.nict.langrid.management.web.view.page;

import jp.go.nict.langrid.management.web.utility.resource.MessageUtil;
import jp.go.nict.langrid.management.web.view.page.other.NewsPage;
import jp.go.nict.langrid.management.web.view.page.other.OverviewLogOutPage;
import jp.go.nict.langrid.management.web.view.session.ServiceManagerSession;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.basic.Label;

/**
 * 
 * 
 * @author Masaaki Kamiya
 * @author $Author: t-nakaguchi $
 * @version $Revision: 406 $
 */
public class PopupPage extends BasePage {
	/**
	 * 
	 * 
	 */
	public PopupPage() {
		add(new Label("ServiceManagerCopyright", MessageUtil.getServiceManagerCopyright())
			.setEscapeModelStrings(false));
	}

	protected Class<? extends Page> getDefaultPageClass() {
		ServiceManagerSession sms = (ServiceManagerSession)getSession();
		if(sms.isLogin()) {
			return NewsPage.class;
		}
		return OverviewLogOutPage.class;
	}

	protected void redirectTop() {
		throw new RestartResponseException(OverviewLogOutPage.class);
	}

	private static final long serialVersionUID = 1L;
}
