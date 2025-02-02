/*
 * $Id: CompositeServiceTypeDropDownChoice.java 1521 2015-03-10 10:29:09Z t-nakaguchi $
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
package jp.go.nict.langrid.management.web.view.page.language.service.composite.component.choice;

import java.util.List;

import jp.go.nict.langrid.management.web.model.ServiceTypeModel;
import jp.go.nict.langrid.management.web.model.exception.ServiceManagerException;
import jp.go.nict.langrid.management.web.model.service.ServiceFactory;
import jp.go.nict.langrid.management.web.view.page.language.service.component.choice.ServiceTypeDropDownChoice;

/**
 * 
 * 
 * @author Masaaki Kamiya
 * @author $Author: t-nakaguchi $
 * @version $Revision: 1521 $
 */
public class CompositeServiceTypeDropDownChoice extends ServiceTypeDropDownChoice
{
	public CompositeServiceTypeDropDownChoice(String componentId)
	throws ServiceManagerException{
		super(componentId);
	}
	/**
	 * 
	 * 
	 * @throws ServiceManagerException 
	 */
	public CompositeServiceTypeDropDownChoice(String gridId, String componentId)
	throws ServiceManagerException{
		super(gridId, componentId);
	}
	
	@Override
	protected List<ServiceTypeModel> getServiceTypeList(String gridId)
	throws ServiceManagerException {
	   setServiceTypeList(ServiceFactory.getInstance().getServiceTypeService(gridId).getAllList());
	   return super.getServiceTypeList(gridId);
	}

	private static final long serialVersionUID = 1L;
}
