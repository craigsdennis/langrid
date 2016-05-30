/*
 * $Id: PacEngine.java 1261 2014-10-20 06:24:20Z t-nakaguchi $
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
package jp.go.nict.langrid.commons.net.proxy.pac;

import java.net.Proxy;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jp.go.nict.langrid.commons.net.proxy.ProxyUtil;

/**
 * 
 * 
 * @author $Author: t-nakaguchi $
 * @version $Revision: 1261 $
 */
public class PacEngine {
	/**
	 * 
	 * 
	 */
	public PacEngine(String pacScript){
		try{
			engine = new ScriptEngineManager().getEngineByName("js");
			engine.put("utils", new PacUtilFunctions());
			engine.put("log", scriptLogger);
			engine.eval(proxyUtils);
			engine.eval(pacScript);
		} catch(ScriptException e){
			logger.log(Level.WARNING, "javascript error when executing PAC script", e);
		}
	}

	/**
	 * 
	 * 
	 */
	public List<Proxy> findProxyForUri(URI uri)
		throws ProxyFormatException, UnknownHostException
	{
		try{
			String full = uri.toString();
			String host = uri.getHost();
			return PacUtil.toProxies(engine.eval(
					"FindProxyForURL('" + full + "', '" + host + "');"
					).toString());
		} catch(Exception e){
			StackTraceElement[] stes = e.getStackTrace();
			logger.log(Level.WARNING, String.format(
					"(%s:%d): %s",
					stes[0].getFileName(), stes[0].getLineNumber(),
					e.getMessage()
					));
			return ProxyUtil.DIRECT;
		}
	}

	/**
	 * 
	 * 
	 */
	public static String getPacUtilScript(){
		return proxyUtils;
	}

	Object evaluate(String script) throws ScriptException{
		return engine.eval(script);
	}

	private ScriptEngine engine;

	/**
	 * 
	 * 
	 ***** BEGIN LICENSE BLOCK *****
	 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
	 *
	 * The contents of this file are subject to the Mozilla Public License Version
	 * 1.1 (the "License"); you may not use this file except in compliance with
	 * the License. You may obtain a copy of the License at
	 * http://www.mozilla.org/MPL/
	 *
	 * Software distributed under the License is distributed on an "AS IS" basis,
	 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
	 * for the specific language governing rights and limitations under the
	 * License.
	 *
	 * The Original Code is mozilla.org code.
	 *
	 * The Initial Developer of the Original Code is
	 * Netscape Communications Corporation.
	 * Portions created by the Initial Developer are Copyright (C) 1998
	 * the Initial Developer. All Rights Reserved.
	 *
	 * Contributor(s):
	 *   Akhil Arora <akhil.arora@sun.com>
	 *   Tomi Leppikangas <Tomi.Leppikangas@oulu.fi>
	 *   Darin Fisher <darin@meer.net>
	 *
	 * Alternatively, the contents of this file may be used under the terms of
	 * either the GNU General Public License Version 2 or later (the "GPL"), or
	 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
	 * in which case the provisions of the GPL or the LGPL are applicable instead
	 * of those above. If you wish to allow use of your version of this file only
	 * under the terms of either the GPL or the LGPL, and not to allow others to
	 * use your version of this file under the terms of the MPL, indicate your
	 * decision by deleting the provisions above and replace them with the notice
	 * and other provisions required by the GPL or the LGPL. If you do not delete
	 * the provisions above, a recipient may use your version of this file under
	 * the terms of any one of the MPL, the GPL or the LGPL.
	 *
	 * ***** END LICENSE BLOCK *****
	 */
	private static String proxyUtils = 
		"function dnsResolve(host){\n" +
		"    return '' + utils.dnsResolve(host);" +
		"}\n" +

		"function dnsDomainIs(host, domain) {\n" +
		"    return (host.length >= domain.length &&\n" +
		"            host.substring(host.length - domain.length) == domain);\n" +
		"}\n" +

		"function dnsDomainLevels(host) {\n" +
		"    return host.split('.').length-1;\n" +
		"}\n" +

		"function convert_addr(ipchars) {\n"+
		"    var bytes = ipchars.split('.');\n"+
		"    var result = ((bytes[0] & 0xff) << 24) |\n"+
		"                 ((bytes[1] & 0xff) << 16) |\n"+
		"                 ((bytes[2] & 0xff) <<  8) |\n"+
		"                  (bytes[3] & 0xff);\n"+
		"    return result;\n"+
		"}\n"+

		"function isInNet(ipaddr, pattern, maskstr) {\n"+
		"    var test = (/^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$/).exec(ipaddr);\n"+
		"    if (test == null) {\n"+
		"        ipaddr = dnsResolve(ipaddr);\n"+
		"        if (ipaddr == null)\n"+
		"            return false;\n"+
		"    } else if (test[1] > 255 || test[2] > 255 || \n"+
		"               test[3] > 255 || test[4] > 255) {\n"+
		"        return false;    // not an IP address\n"+
		"    }\n"+
		"    var host = convert_addr(ipaddr);\n"+
		"    var pat  = convert_addr(pattern);\n"+
		"    var mask = convert_addr(maskstr);\n"+
		"    return ((host & mask) == (pat & mask));\n"+
		"    \n"+
		"}\n"+

		"function isPlainHostName(host) {\n" +
		"    return (host.search('\\\\.') == -1);\n" +
		"}\n" +

		"function isResolvable(host) {\n" +
		"    var ip = dnsResolve(host);\n" +
		"    return (ip != null);\n" +
		"}\n" +

		"function localHostOrDomainIs(host, hostdom) {\n" +
		"    return (host == hostdom) ||\n" +
		"           (hostdom.lastIndexOf(host + '.', 0) == 0);\n" +
		"}\n" +

		"function shExpMatch(url, pattern) {\n" +
		"   pattern = pattern.replace(/\\./g, '\\\\.');\n" +
		"   pattern = pattern.replace(/\\*/g, '.*');\n" +
		"   pattern = pattern.replace(/\\?/g, '.');\n" +
		"   var newRe = new RegExp('^'+pattern+'$');\n" +
		"   return newRe.test(url);\n" +
		"}\n" +

		"var wdays = new Array('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT');\n" +

		"var monthes = new Array('JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC');\n"+

		"function weekdayRange() {\n" +
		"    function getDay(weekday) {\n" +
		"        for (var i = 0; i < 6; i++) {\n" +
		"            if (weekday == wdays[i]) \n" +
		"                return i;\n" +
		"        }\n" +
		"        return -1;\n" +
		"    }\n" +
		"    var date = new Date();\n" +
		"    var argc = arguments.length;\n" +
		"    var wday;\n" +
		"    if (argc < 1)\n" +
		"        return false;\n" +
		"    if (arguments[argc - 1] == 'GMT') {\n" +
		"        argc--;\n" +
		"        wday = date.getUTCDay();\n" +
		"    } else {\n" +
		"        wday = date.getDay();\n" +
		"    }\n" +
		"    var wd1 = getDay(arguments[0]);\n" +
		"    var wd2 = (argc == 2) ? getDay(arguments[1]) : wd1;\n" +
		"    return (wd1 == -1 || wd2 == -1) ? false\n" +
		"                                    : (wd1 <= wday && wday <= wd2);\n" +
		"}\n" +

		"function dateRange() {\n" +
		"    function getMonth(name) {\n" +
		"        for (var i = 0; i < 6; i++) {\n" +
		"            if (name == monthes[i])\n" +
		"                return i;\n" +
		"        }\n" +
		"        return -1;\n" +
		"    }\n" +
		"    var date = new Date();\n" +
		"    var argc = arguments.length;\n" +
		"    if (argc < 1) {\n" +
		"        return false;\n" +
		"    }\n" +
		"    var isGMT = (arguments[argc - 1] == 'GMT');\n" +
		"\n" +
		"    if (isGMT) {\n" +
		"        argc--;\n" +
		"    }\n" +
		"    // function will work even without explict handling of this case\n" +
		"    if (argc == 1) {\n" +
		"        var tmp = parseInt(arguments[0]);\n" +
		"        if (isNaN(tmp)) {\n" +
		"            return ((isGMT ? date.getUTCMonth() : date.getMonth()) ==\n" +
		"getMonth(arguments[0]));\n" +
		"        } else if (tmp < 32) {\n" +
		"            return ((isGMT ? date.getUTCDate() : date.getDate()) == tmp);\n" +
		"        } else { \n" +
		"            return ((isGMT ? date.getUTCFullYear() : date.getFullYear()) ==\n" +
		"tmp);\n" +
		"        }\n" +
		"    }\n" +
		"    var year = date.getFullYear();\n" +
		"    var date1, date2;\n" +
		"    date1 = new Date(year,  0,  1,  0,  0,  0);\n" +
		"    date2 = new Date(year, 11, 31, 23, 59, 59);\n" +
		"    var adjustMonth = false;\n" +
		"    for (var i = 0; i < (argc >> 1); i++) {\n" +
		"        var tmp = parseInt(arguments[i]);\n" +
		"        if (isNaN(tmp)) {\n" +
		"            var mon = getMonth(arguments[i]);\n" +
		"            date1.setMonth(mon);\n" +
		"        } else if (tmp < 32) {\n" +
		"            adjustMonth = (argc <= 2);\n" +
		"            date1.setDate(tmp);\n" +
		"        } else {\n" +
		"            date1.setFullYear(tmp);\n" +
		"        }\n" +
		"    }\n" +
		"    for (var i = (argc >> 1); i < argc; i++) {\n" +
		"        var tmp = parseInt(arguments[i]);\n" +
		"        if (isNaN(tmp)) {\n" +
		"            var mon = getMonth(arguments[i]);\n" +
		"            date2.setMonth(mon);\n" +
		"        } else if (tmp < 32) {\n" +
		"            date2.setDate(tmp);\n" +
		"        } else {\n" +
		"            date2.setFullYear(tmp);\n" +
		"        }\n" +
		"    }\n" +
		"    if (adjustMonth) {\n" +
		"        date1.setMonth(date.getMonth());\n" +
		"        date2.setMonth(date.getMonth());\n" +
		"    }\n" +
		"    if (isGMT) {\n" +
		"    var tmp = date;\n" +
		"        tmp.setFullYear(date.getUTCFullYear());\n" +
		"        tmp.setMonth(date.getUTCMonth());\n" +
		"        tmp.setDate(date.getUTCDate());\n" +
		"        tmp.setHours(date.getUTCHours());\n" +
		"        tmp.setMinutes(date.getUTCMinutes());\n" +
		"        tmp.setSeconds(date.getUTCSeconds());\n" +
		"        date = tmp;\n" +
		"    }\n" +
		"    return ((date1 <= date) && (date <= date2));\n" +
		"}\n" +

		"function timeRange() {\n" +
		"    var argc = arguments.length;\n" +
		"    var date = new Date();\n" +
		"    var isGMT= false;\n"+
		"\n" +
		"    if (argc < 1) {\n" +
		"        return false;\n" +
		"    }\n" +
		"    if (arguments[argc - 1] == 'GMT') {\n" +
		"        isGMT = true;\n" +
		"        argc--;\n" +
		"    }\n" +
		"\n" +
		"    var hour = isGMT ? date.getUTCHours() : date.getHours();\n" +
		"    var date1, date2;\n" +
		"    date1 = new Date();\n" +
		"    date2 = new Date();\n" +
		"\n" +
		"    if (argc == 1) {\n" +
		"        return (hour == arguments[0]);\n" +
		"    } else if (argc == 2) {\n" +
		"        return ((arguments[0] <= hour) && (hour <= arguments[1]));\n" +
		"    } else {\n" +
		"        switch (argc) {\n" +
		"        case 6:\n" +
		"            date1.setSeconds(arguments[2]);\n" +
		"            date2.setSeconds(arguments[5]);\n" +
		"        case 4:\n" +
		"            var middle = argc >> 1;\n" +
		"            date1.setHours(arguments[0]);\n" +
		"            date1.setMinutes(arguments[1]);\n" +
		"            date2.setHours(arguments[middle]);\n" +
		"            date2.setMinutes(arguments[middle + 1]);\n" +
		"            if (middle == 2) {\n" +
		"                date2.setSeconds(59);\n" +
		"            }\n" +
		"            break;\n" +
		"        default:\n" +
		"          throw 'timeRange: bad number of arguments'\n" +
		"        }\n" +
		"    }\n" +
		"\n" +
		"    if (isGMT) {\n" +
		"        date.setFullYear(date.getUTCFullYear());\n" +
		"        date.setMonth(date.getUTCMonth());\n" +
		"        date.setDate(date.getUTCDate());\n" +
		"        date.setHours(date.getUTCHours());\n" +
		"        date.setMinutes(date.getUTCMinutes());\n" +
		"        date.setSeconds(date.getUTCSeconds());\n" +
		"    }\n" +
		"    return ((date1 <= date) && (date <= date2));\n" +
		"}\n";

	private static Logger logger = Logger.getLogger(PacEngine.class.getName());
	private static Logger scriptLogger = Logger.getLogger(PacEngine.class.getName() + "$Script");
}
