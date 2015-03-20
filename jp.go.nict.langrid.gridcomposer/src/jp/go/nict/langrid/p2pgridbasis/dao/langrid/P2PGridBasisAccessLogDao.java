/*
 * $Id: P2PGridBasisAccessLogDao.java 401 2011-08-25 01:11:16Z t-nakaguchi $
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
package jp.go.nict.langrid.p2pgridbasis.dao.langrid;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import jp.go.nict.langrid.dao.AccessLogSearchResult;
import jp.go.nict.langrid.dao.AccessLogDao;
import jp.go.nict.langrid.dao.DaoContext;
import jp.go.nict.langrid.dao.DaoException;
import jp.go.nict.langrid.dao.GenericHandler;
import jp.go.nict.langrid.dao.MatchingCondition;
import jp.go.nict.langrid.dao.Order;
import jp.go.nict.langrid.dao.entity.AccessLog;
import jp.go.nict.langrid.p2pgridbasis.controller.ControllerException;
import jp.go.nict.langrid.p2pgridbasis.controller.P2PGridController;
import jp.go.nict.langrid.p2pgridbasis.controller.jxta.JXTAController;
import jp.go.nict.langrid.p2pgridbasis.dao.DataDao;
import jp.go.nict.langrid.p2pgridbasis.dao.DataDaoException;
import jp.go.nict.langrid.p2pgridbasis.dao.UnmatchedDataTypeException;
import jp.go.nict.langrid.p2pgridbasis.data.Data;
import jp.go.nict.langrid.p2pgridbasis.data.langrid.AccessLogData;
import jp.go.nict.langrid.p2pgridbasis.data.langrid.DataConvertException;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 401 $
 */
public class P2PGridBasisAccessLogDao implements DataDao, AccessLogDao {
	/**
	 * The constructor.
	 * @param dao
	 * @param context
	 */
	public P2PGridBasisAccessLogDao(AccessLogDao dao, DaoContext context) {
		this.dao = dao;
		this.daoContext = context;
	}

	private P2PGridController getController() throws ControllerException{
		if (controller == null) {
			controller = JXTAController.getInstance();
		}

		return controller;
	}

	public void setEntityListener() {
		logger.debug("### AccessLog : setEntityListener ###");
		daoContext.addEntityListener(AccessLog.class, handler);
		daoContext.addTransactionListener(handler);
	}

	public void removeEntityListener() {
		logger.debug("### AccessLog : removeEntityListener ###");
		daoContext.removeTransactionListener(handler);
		daoContext.removeEntityListener(AccessLog.class, handler);
	}

	synchronized public boolean updateDataSource(Data data) throws DataDaoException, UnmatchedDataTypeException {
		return updateDataTarget(data);
	}

	synchronized public boolean updateDataTarget(Data data) throws DataDaoException, UnmatchedDataTypeException {
		logger.debug("[Log] : " + data.getId());
		if(data.getClass().equals(AccessLogData.class) == false) {
			throw new UnmatchedDataTypeException(AccessLogData.class.toString(), data.getClass().toString());
		}

 		if(data.getAttributes().getKeys().contains("IsDeleted") &&
				data.getAttributes().getValue("IsDeleted").equals("true")) {
			logger.error("Delete");
/*
 * 
 * 
			try {
				logger.debug("delete data");
				AccessLogData accessLogData = (AccessLogData)data;
				AccessLog log = accessLogData.getAccessLog();
				removeEntityListener();
				dao.deleteAccessLogBefore(log.getDateTime());
				setEntityListener();
				getController().publish(data);
			} catch (DataConvertException e) {
				throw new DataDaoException(e);
			} catch (ServiceNotFoundException e) {
 				// 
				// 
				try {
					getController().publish(data);
				} catch (ControllerException e1) {
					e1.printStackTrace();
				}
			} catch (DaoException e) {
				throw new DataDaoException(e);
			} catch (ParseException e) {
				throw new DataDaoException(e);
			} catch (ControllerException e) {
				throw new DataDaoException(e);
			}
			return;
 */
		}
		AccessLog log= null;
		try {
			AccessLogData accessLogData = (AccessLogData)data;
			log = accessLogData.getAccessLog();
			if(registCheck(log) == false){
				logger.debug("DateTime of 24 Hours ago");
				return false;
			}
			if(!log.getServiceAndNodeGridId().equals(getController().getSerlfGridId())){
				return false;
			}
			logger.debug("New");
			removeEntityListener();
			daoContext.beginTransaction();
			try{
				if(dao.isLogExistByNodeIds(log.getServiceAndNodeGridId(), log.getNodeId(), log.getNodeLocalId())){
					dao.updateAccessLogByNodeIds(log);
				} else{
					int nlid = log.getNodeLocalId();
					dao.addAccessLog(log);
					log.setNodeLocalId(nlid);
				}
			} finally{
				daoContext.commitTransaction();
			}
			setEntityListener();

			getController().logSummaryAdd(data);
			return true;
		} catch (ParseException e) {
			throw new DataDaoException(e);
		} catch (DaoException e) {
			throw new DataDaoException(e);
		} catch (DataConvertException e) {
			throw new DataDaoException(e);
		} catch (ControllerException e) {
			throw new DataDaoException(e);
		}
	}

	private boolean registCheck(AccessLog log) {
		long dataTimeMillis = log.getDateTime().getTimeInMillis();
		long nowTimeMillis  = Calendar.getInstance().getTimeInMillis();
		final long dayMills = 24 * 60 * 60 * 1000;
		boolean ret = true;

		if((nowTimeMillis - dataTimeMillis) > dayMills){
			ret = false;
		}
		return ret;
	}

	@Override
	public void clear() throws DaoException {
		dao.clear();
	}

	@Override
	public boolean isLogExist(int logId) throws DaoException {
		return dao.isLogExist(logId);
	}

	@Override
	public void addAccessLog(AccessLog log) throws DaoException {
		dao.addAccessLog(log);
	}

	@Override
	public void deleteAccessLogBefore(Calendar dateTime) throws DaoException {
		dao.deleteAccessLogBefore(dateTime);
	}

	@Override
	public void deleteAccessLogsOfGrid(String gridId) throws DaoException {
		dao.deleteAccessLogsOfGrid(gridId);
	}

	@Override
	public void deleteAccessLogOfNode(String nodeGridId, String nodeId)
			throws DaoException {
		dao.deleteAccessLogOfNode(nodeGridId, nodeId);
	}

	@Override
	public void deleteAccessLogsOfService(String serviceGridId, String serviceId)
			throws DaoException {
		dao.deleteAccessLogsOfService(serviceGridId, serviceId);
	}

	@Override
	public void deleteAccessLogOfUser(String userGridId, String userId)
			throws DaoException {
		dao.deleteAccessLogOfUser(userGridId, userId);
	}

	@Override
	public List<AccessLog> listAccessLogsNewerThanOrEqualsTo(
			String serviceGridId, Calendar dateTime) throws DaoException {
		return dao.listAccessLogsNewerThanOrEqualsTo(serviceGridId, dateTime);
	}

	@Override
	public AccessLogSearchResult searchAccessLog(int startIndex, int maxCount,
			String userGridId, String userId, String serviceGridId,
			String[] serviceIds, Calendar startDateTime, Calendar endDateTime,
			MatchingCondition[] conditions, Order[] orders) throws DaoException {
		return dao.searchAccessLog(startIndex, maxCount, userGridId, userId, serviceGridId, serviceIds, startDateTime, endDateTime, conditions, orders);
	}

	@Override
	public AccessLogSearchResult searchLimitOverAccessLog(int startIndex, int maxCount,
		String userGridId, String userId, String serviceGridId, String[] serviceIds,
		Calendar startDateTime, Calendar endDateTime, MatchingCondition[] conditions,
		Order[] orders, int limitCount) throws DaoException {
		return dao.searchLimitOverAccessLog(startIndex, maxCount, userGridId, userId, serviceGridId, serviceIds, startDateTime, endDateTime, conditions, orders, limitCount);
	}

	@Override
	public boolean isLogExistByNodeIds(String gridId, String nodeId, int nodeLocalId)
			throws DaoException {
		return dao.isLogExistByNodeIds(gridId, nodeId, nodeLocalId);
	}

	@Override
	public void updateAccessLogByNodeIds(AccessLog log) throws DaoException {
		dao.updateAccessLogByNodeIds(log);
	}

	private AccessLogDao dao;
	private DaoContext daoContext;
	private P2PGridController controller;
	private GenericHandler<AccessLog> handler = new GenericHandler<AccessLog>(){
		protected boolean onNotificationStart() {
			try{
				daoContext.beginTransaction();
				return true;
			} catch (DaoException e) {
				logger.error("failed to access dao.", e);
				return false;
			}
		}

		protected void doUpdate(Serializable id, Set<String> modifiedProperties){
// 試しに共有しないようにしてみる。
/* 
			try{
				getController().logDataPublish(new AccessLogData(
						daoContext.loadEntity(AccessLog.class, id)
						));
				logger.info("published[AccessLog(id=" + id + ")]");
			} catch(ControllerException e){
				logger.error("failed to publish instance.", e);
			} catch(DaoException e){
				logger.error("failed to access dao.", e);
			} catch(DataConvertException e){
				logger.error("failed to convert data.", e);
			}
*/
		}

		protected void doRemove(Serializable id){
			logger.error("doRemove[AccessLog(id=" + id + ")]");
/*
 * 
 * 
			try{
				getController().revoke(new DataID(
						AccessLogData._IDPrefix
						+ id.toString()));
				logger.info("revoked[AccessLog(id=" + id + ")]");
			} catch(ControllerException e){
				logger.error("failed to revoke instance.", e);
			} catch (DataNotFoundException e) {
				e.printStackTrace();
			}
 */
		}

		protected void onNotificationEnd(){
			try{
				daoContext.commitTransaction();
			} catch (DaoException e) {
				logger.error("failed to access dao.", e);
			}
		}
	};

	static private Logger logger = Logger.getLogger(P2PGridBasisAccessLogDao.class);
}