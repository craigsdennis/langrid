/*
 * $Id: ConstructSource.java 409 2011-08-25 03:12:59Z t-nakaguchi $
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

package jp.go.nict.langrid.wrapper.workflowsupport;

import java.util.logging.Logger;

import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.ProcessFailedException;
import jp.go.nict.langrid.service_1_2.morphologicalanalysis.Morpheme;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.Analysis;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.ChineseAnalysis;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.DefaultAnalysis;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.DefaultAnalysisWithSpace;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.EnglishAnalysis4TreeTagger;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.HangulAnalysis;
import jp.go.nict.langrid.wrapper.workflowsupport.analysis.JapaneseAnalysis;

/**
 * 形態素配列より、中間コードに置き換え文章を作成する
 * ver1.1 タイ語対応の為、デフォルトクラスをスペース有り、無しに修正
 * @author koyama
 * @version 1.1
 */
public class ConstructSource{
	public String doConstructSource(String sourceLang, Morpheme[] morphemes)
	throws InvalidParameterException, ProcessFailedException{
		if (sourceLang == null) {
			throw new InvalidParameterException("sorceLang", "sourceLang is null.");
		} else if (morphemes == null) {
			throw new InvalidParameterException("morphemes", "morphemes is null.");
		}
		try {
			Analysis analysis = null;
			// ここはWebServiceで文字列渡しだったので、たまたまzhが通っていた。特にCNに対応していない。
			// TODO:言語毎の細かい処理が増えるようであれば、マッピングテーブルで対応した方がいい。
			if (sourceLang.startsWith("ja")) {
				analysis = new JapaneseAnalysis();
			} else if (sourceLang.startsWith("ko")) {
				analysis = new HangulAnalysis();
			} else if (sourceLang.startsWith("zh")) {
				analysis = new ChineseAnalysis();
			} else if (sourceLang.startsWith("en")) {
				analysis = new EnglishAnalysis4TreeTagger();
			} else if (sourceLang.startsWith("th")) {
				// 形態素間を直接繋げる
				analysis = new DefaultAnalysis();
			} else {
				// 形態素間で空白を持たせる
				analysis = new DefaultAnalysisWithSpace();
			}
			return analysis.doConstructSource(morphemes);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new ProcessFailedException(e);
		} finally {
//			logger.info("doConstructSMC done in " + (System.currentTimeMillis() - s) + "millis.");
		}
	}

	private static Logger logger = Logger.getLogger(ConstructSource.class.getName());
}
