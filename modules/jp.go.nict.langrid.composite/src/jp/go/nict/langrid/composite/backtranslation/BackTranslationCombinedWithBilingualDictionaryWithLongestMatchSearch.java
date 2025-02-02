/*
 * This is a program for Language Grid Core Node. This combines multiple language resources and provides composite language services.
 * Copyright (C) 2010 NICT Language Grid Project.
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
package jp.go.nict.langrid.composite.backtranslation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.go.nict.langrid.commons.util.ArrayUtil;
import jp.go.nict.langrid.composite.util.CompositeTranslationUtil;
import jp.go.nict.langrid.language.Language;
import jp.go.nict.langrid.language.LanguagePair;
import jp.go.nict.langrid.service_1_2.AccessLimitExceededException;
import jp.go.nict.langrid.service_1_2.InvalidParameterException;
import jp.go.nict.langrid.service_1_2.LanguagePairNotUniquelyDecidedException;
import jp.go.nict.langrid.service_1_2.NoAccessPermissionException;
import jp.go.nict.langrid.service_1_2.NoValidEndpointsException;
import jp.go.nict.langrid.service_1_2.ProcessFailedException;
import jp.go.nict.langrid.service_1_2.ServerBusyException;
import jp.go.nict.langrid.service_1_2.ServiceNotActiveException;
import jp.go.nict.langrid.service_1_2.ServiceNotFoundException;
import jp.go.nict.langrid.service_1_2.UnsupportedLanguagePairException;
import jp.go.nict.langrid.service_1_2.backtranslation.BackTranslationResult;
import jp.go.nict.langrid.service_1_2.backtranslation.BackTranslationWithTemporalDictionaryService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.BilingualDictionaryWithLongestMatchSearchService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.Translation;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.TranslationWithPosition;
import jp.go.nict.langrid.service_1_2.morphologicalanalysis.Morpheme;
import jp.go.nict.langrid.service_1_2.morphologicalanalysis.MorphologicalAnalysisService;
import jp.go.nict.langrid.service_1_2.translation.TranslationService;
import jp.go.nict.langrid.service_1_2.util.validator.LanguagePairValidator;
import jp.go.nict.langrid.service_1_2.util.validator.LanguageValidator;
import jp.go.nict.langrid.service_1_2.util.validator.StringValidator;
import jp.go.nict.langrid.service_1_2.workflowsupport.SourceAndMorphemesAndCodes;
import jp.go.nict.langrid.servicecontainer.service.composite.AbstractCompositeService;
import jp.go.nict.langrid.servicecontainer.service.composite.Invocation;
import jp.go.nict.langrid.servicecontainer.service.composite.ServiceLoadingFailedException;
import jp.go.nict.langrid.wrapper.workflowsupport.ConstructSourceAndMorphemesAndCodes;
import jp.go.nict.langrid.wrapper.workflowsupport.DefaultMorphologicalAnalysis;
import jp.go.nict.langrid.wrapper.workflowsupport.ReplacementTerm;
import jp.go.nict.langrid.wrapper.workflowsupport.TemporalBilingualDictionaryWithLongestMatchSearch;

/**
 * BackTranslation combined with bilingualdictionary with
 * longest match search service implementation.
 * @author Takao Nakaguchi
 */
public class BackTranslationCombinedWithBilingualDictionaryWithLongestMatchSearch
extends AbstractCompositeService
implements BackTranslationWithTemporalDictionaryService{
	public static class Components{
		@Invocation(name="BilingualDictionaryWithLongestMatchSearchPL")
		private BilingualDictionaryWithLongestMatchSearchService bdict;
		@Invocation(name="MorphologicalAnalysisPL")
		private MorphologicalAnalysisService morph;
		@Invocation(name="ForwardTranslationPL")
		private TranslationService ft;
		@Invocation(name="BackwardTranslationPL")
		private TranslationService bt;
	}

	public BackTranslationCombinedWithBilingualDictionaryWithLongestMatchSearch() {
		super(Components.class);
	}

	@Override
	public BackTranslationResult backTranslate(String sourceLang, String intermediateLang,
			String source, Translation[] temporalDict,
			String dictTargetLang)
	throws AccessLimitExceededException,
	InvalidParameterException, LanguagePairNotUniquelyDecidedException,
	NoAccessPermissionException, ProcessFailedException,
	NoValidEndpointsException, ServerBusyException,
	ServiceNotActiveException, ServiceNotFoundException,
	UnsupportedLanguagePairException
	{
		LanguagePair pair = new LanguagePairValidator(
				new LanguageValidator("sourceLang", sourceLang)
				, new LanguageValidator("intermediateLang", intermediateLang)
				).notNull().trim().notEmpty().getLanguagePair();
		String src = new StringValidator("source", source)
				.notNull().trim().notEmpty().getValue();
		Language dictLang = null;
		if(dictTargetLang != null){
			dictLang = new LanguageValidator("dictTargetLang", dictTargetLang)
					.getLanguage();
		} else{
			dictLang = pair.getTarget();
		}
		Translation[] tempDict = temporalDict;
		if(tempDict == null){
			tempDict = new Translation[]{};
		}

		String sl = pair.getSource().getCode();
		String tl = pair.getTarget().getCode();
		String dl = dictLang.getCode();

		try{
			Components s = loadServices();
			TemporalBilingualDictionaryWithLongestMatchSearch tempDictMatch = new TemporalBilingualDictionaryWithLongestMatchSearch();
			ConstructSourceAndMorphemesAndCodes csmc = getConstructSourceAndMorphemesAndCodes();
			ReplacementTerm rt = new ReplacementTerm();

			Morpheme[] analyzeResult = null;
			{
				info("invoke MorphologicalAnalysis.analyze");
				if(s.morph != null){
					try{
						analyzeResult = s.morph.analyze(sl, src);
					} catch(ServiceNotActiveException e){
						warning("service is not active: " + e.getServiceId());
					} catch(Exception e){
						warning("exception occurred at MorphologicalAnalysisPL.", e);
					}
				}
				if(analyzeResult == null || analyzeResult.length == 0){
					log("analyze result is null. calling DefaultMorphologicalAnalysis.");
					analyzeResult = new DefaultMorphologicalAnalysis().analyze(sl, src);
				}
				log("invoke MorphologicalAnalysis.analyze done(" + analyzeResult.length + "morphs)");
			}

			Collection<TranslationWithPosition> tempDictResult = new ArrayList<TranslationWithPosition>();
			log("invoke TemporalBilingualDictionaryWithLongestMatchSearch.doSearchAllLongestMatchingTerms("
					+ analyzeResult.length + "morphes)");
			try{
				tempDictResult = tempDictMatch.doSearchAllLongestMatchingTerms(
					pair.getSource(), analyzeResult, tempDict);
			} catch(Exception e){
				warning("exception occurred at tempdictmatch.", e);
			}
			log("invoke TemporalBilingualDictionaryWithLongestMatchSearch.doSearchAllLongestMatchingTerms done("
						+ tempDictResult.size() + "translations)");

			log("prepare for ConstructSourceAndMorphemesAndCodes.doConstructSMC()");
			log("invoke ConstructSourceAndMorphemesAndCodes.doConstructSMC("
					+ analyzeResult.length + "morphs,"
					+ tempDictResult.size() + "translations)");
			SourceAndMorphemesAndCodes firstSmc = csmc.doConstructSMC(
					sl, analyzeResult, tempDictResult.toArray(emptyTranslation));
			log("invoke ConstructSourceAndMorphemesAndCodes.doConstructSMC done(" +
					+ firstSmc.getMorphemes().length + "morphs," +
					+ firstSmc.getCodes().length + "codes,"
					+ firstSmc.getTargetWords().length + "targetWords)");

			TranslationWithPosition[] dictResult = new TranslationWithPosition[]{};
			if(s.bdict != null){
				log("invoke BilingualDictionaryWithLongestMatchSearchService.searchLongestMatchingTerms(" +
						firstSmc.getMorphemes().length + "morphs)");
				try{
					dictResult = s.bdict.searchLongestMatchingTerms(
									sl, dl, firstSmc.getMorphemes()
									);
					dictResult = CompositeTranslationUtil.dropInvalidEntries(dictResult, firstSmc.getMorphemes());
				} catch(ServiceNotActiveException e){
					warning("service is not active: " + e.getServiceId());
				} catch(Exception e){
					warning("exception occurred at bdict search.", e);
				}
				log("invoke BilingualDictionaryWithLongestMatchSearchService.searchLongestMatchingTerms done(" +
						dictResult.length + "translations)");
			}

			log("invoke ConstructSourceAndMorphemesAndCodes.doConstructSMC("
					+ firstSmc.getMorphemes().length + "morphs," + dictResult.length + "translations)"
					);
			SourceAndMorphemesAndCodes secondSmc = csmc.doConstructSMC(sl, firstSmc.getMorphemes(), dictResult);
			log("invoke ConstructSourceAndMorphemesAndCodes.doConstructSMC done(" +
					+ secondSmc.getMorphemes().length + "morphs," +
					+ secondSmc.getCodes().length + "codes,"
					+ secondSmc.getTargetWords().length + "targetWords)"
					);

			log("joining mrophemes and codes");
			SourceAndMorphemesAndCodes joinedSmc = new SourceAndMorphemesAndCodes(
					secondSmc.getSource()
					, secondSmc.getMorphemes()
					, ArrayUtil.append(firstSmc.getCodes(), secondSmc.getCodes())
					, ArrayUtil.append(firstSmc.getHeadWords(), secondSmc.getHeadWords())
					, ArrayUtil.append(firstSmc.getTargetWords(), secondSmc.getTargetWords())
			);
			log("joining mrophemes and codes done(" +
					+ joinedSmc.getMorphemes().length + "morphs," +
					+ joinedSmc.getCodes().length + "codes,"
					+ joinedSmc.getTargetWords().length + "targetWords)");

			if(s.ft == null && s.bt == null && sl.equals(tl)){
				// paraphrase
				String fwTranslationResult = joinedSmc.getSource();
				String fwFinalResult = rt.doReplace(tl, fwTranslationResult
						, joinedSmc.getCodes(), joinedSmc.getTargetWords());
				String bwTranslationResult = joinedSmc.getSource();
				String[] headWords = getTargetWordsForReplaceAfterBackTrans(
						tempDictResult, dictResult);
				String bwFinalResult = rt.doReplace(sl, bwTranslationResult
						, joinedSmc.getCodes(), headWords);
				return new BackTranslationResult(fwFinalResult, bwFinalResult);
			}
			if(s.ft == null){
				throw new ProcessFailedException("ForwardTranslationPL not binded.");
			}
			log("invoke TranslationService.translate(" + joinedSmc.getSource().length() + "chars)");
			String fwTranslationResult = CompositeTranslationUtil.translateUntilNocodeAppears(
					sl, tl, joinedSmc, s.ft);
			log("invoke TranslationService.translate done");
			log("replacing codes(" + joinedSmc.getCodes().length + "codes" +
					"," + joinedSmc.getTargetWords().length + "words)");
			String fwFinalResult = rt.doReplace(tl, fwTranslationResult
					, joinedSmc.getCodes(), joinedSmc.getTargetWords());
			log("replacing codes done");

			if(s.bt == null){
				throw new ProcessFailedException("BackwardTranslationPL not binded.");
			}
			log("invoke TranslationService.translate(" + fwTranslationResult.length() + "chars)");
			String bwTranslationResult = CompositeTranslationUtil.translateUntilNocodeAppears(
					tl, sl, CompositeTranslationUtil.createBackwardSmc(joinedSmc, fwTranslationResult), s.bt);
			log("invoke TranslationService.translate done");

			log("prepare for replacing codes()");
			String[] headWords = getTargetWordsForReplaceAfterBackTrans(
					tempDictResult, dictResult);
			log("replacing codes(" + joinedSmc.getCodes().length + "codes" +
					"," + headWords.length + "words)");
			String bwFinalResult = rt.doReplace(sl, bwTranslationResult
					, joinedSmc.getCodes(), headWords);
			log("replacing codes done");

			return new BackTranslationResult(fwFinalResult, bwFinalResult);
		} catch(InvalidParameterException e){
			throw e;
		} catch(ProcessFailedException e){
			warning("process failed.", e);
			throw e;
		} catch(ServiceLoadingFailedException e){
			throw new ServiceNotFoundException(e.getServiceId());
		} catch(Throwable t){
			severe("unknown error occurred.", t);
			throw new ProcessFailedException(t);
		}
	}

	protected ConstructSourceAndMorphemesAndCodes getConstructSourceAndMorphemesAndCodes() {
		return new ConstructSourceAndMorphemesAndCodes();
	}

	protected String[] getTargetWordsForReplaceAfterBackTrans(
			Collection<TranslationWithPosition> tempDictResult
			, TranslationWithPosition[] dictResult){
		List<String> headWordList = new ArrayList<String>();
		for(TranslationWithPosition t : tempDictResult){
			headWordList.add(t.getTranslation().getHeadWord());
		}
		for(TranslationWithPosition t : dictResult){
			headWordList.add(t.getTranslation().getHeadWord());
		}
		return headWordList.toArray(emptyStringArray);
	}

	private static final String[] emptyStringArray = {};
	private static final TranslationWithPosition[] emptyTranslation = {};
}
