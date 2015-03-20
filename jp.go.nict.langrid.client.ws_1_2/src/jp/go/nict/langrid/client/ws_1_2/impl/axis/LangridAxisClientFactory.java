package jp.go.nict.langrid.client.ws_1_2.impl.axis;

import SpeechTranslation.langrid.kyotou.service.KyotouLangridSpeechTranslationLocator;
import jp.go.nict.langrid.client.ClientFactory;
import jp.go.nict.langrid.client.axis.AxisClientFactory;
import jp.go.nict.langrid.service_1_2.adjacencypair.AdjacencyPairService;
import jp.go.nict.langrid.service_1_2.backtranslation.BackTranslationService;
import jp.go.nict.langrid.service_1_2.backtranslation.BackTranslationWithTemporalDictionaryService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.BilingualDictionaryHeadwordsExtractionService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.BilingualDictionaryService;
import jp.go.nict.langrid.service_1_2.bilingualdictionary.BilingualDictionaryWithLongestMatchSearchService;
import jp.go.nict.langrid.service_1_2.conceptdictionary.ConceptDictionaryService;
import jp.go.nict.langrid.service_1_2.dependencyparser.DependencyParserService;
import jp.go.nict.langrid.service_1_2.dependencyparser.MorphemesDependencyParserService;
import jp.go.nict.langrid.service_1_2.dictionary.DictionaryService;
import jp.go.nict.langrid.service_1_2.keyphraseextract.KeyphraseExtractService;
import jp.go.nict.langrid.service_1_2.languageidentification.LanguageIdentificationService;
import jp.go.nict.langrid.service_1_2.morphologicalanalysis.MorphologicalAnalysisService;
import jp.go.nict.langrid.service_1_2.multihoptranslation.MultihopTranslationService;
import jp.go.nict.langrid.service_1_2.namedentitytagging.NamedEntityTaggingService;
import jp.go.nict.langrid.service_1_2.paralleltext.MetadataForParallelTextService;
import jp.go.nict.langrid.service_1_2.paralleltext.ParallelTextService;
import jp.go.nict.langrid.service_1_2.paralleltext.ParallelTextWithEmbeddedMetadataService;
import jp.go.nict.langrid.service_1_2.paralleltext.ParallelTextWithExternalMetadataService;
import jp.go.nict.langrid.service_1_2.paralleltext.ParallelTextWithIdService;
import jp.go.nict.langrid.service_1_2.paraphrase.ParaphraseService;
import jp.go.nict.langrid.service_1_2.pictogramdictionary.PictogramDictionaryService;
import jp.go.nict.langrid.service_1_2.qualityestimation.QualityEstimationService;
import jp.go.nict.langrid.service_1_2.similaritycalculation.SimilarityCalculationService;
import jp.go.nict.langrid.service_1_2.speech.SpeechRecognitionService;
import jp.go.nict.langrid.service_1_2.speech.TextToSpeechService;
import jp.go.nict.langrid.service_1_2.speechtranslation.SpeechTranslationService;
import jp.go.nict.langrid.service_1_2.templateparalleltext.TemplateParallelTextService;
import jp.go.nict.langrid.service_1_2.textsummarize.TextSummarizeService;
import jp.go.nict.langrid.service_1_2.transformer.StringToDictMatchingMethodTransformer;
import jp.go.nict.langrid.service_1_2.transformer.StringToPartOfSpeechTransformer;
import jp.go.nict.langrid.service_1_2.translation.AsyncTranslationService;
import jp.go.nict.langrid.service_1_2.translation.AsyncTranslationWithTemporalDictionaryService;
import jp.go.nict.langrid.service_1_2.translation.TranslationService;
import jp.go.nict.langrid.service_1_2.translation.TranslationWithInternalDictionaryService;
import jp.go.nict.langrid.service_1_2.translation.TranslationWithTemporalDictionaryService;
import jp.go.nict.langrid.service_1_2.translationselection.TranslationSelectionService;
import localhost.jp_go_nict_langrid_webapps_atomic.services.TranslationSelection.TranslationSelectionServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_langrid_p2p.services.QualityEstimation.QualityEstimationServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.AsyncTranslation.AsyncTranslationServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.AsyncTranslationWithTemporalDictionary.AsyncTranslationWithTemporalDictionaryServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.KeyphraseExtract.KeyphraseExtractServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.MorphemesDependencyParser.MorphemesDependencyParserServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.NamedEntityTagging.NamedEntityTaggingServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.TextSummarize.TextSummarizeServiceServiceLocator;
import localhost.jp_go_nict_langrid_webapps_mock.services.TranslationWithInternalDictionary.TranslationWithInternalDictionaryServiceServiceLocator;
import localhost.service_mock.services.SpeechRecognition.SpeechRecognitionServiceLocator;
import localhost.service_mock.services.TemplateParallelText.TemplateParallelTextServiceLocator;
import localhost.service_mock.services.TextToSpeech.TextToSpeechServiceLocator;
import localhost.wrapper_mock_1_2.services.BackTranslation.BackTranslation_ServiceLocator;
import localhost.wrapper_mock_1_2.services.BackTranslationWithTemporalDictionary.BackTranslationWithTemporalDictionaryServiceLocator;
import localhost.wrapper_mock_1_2.services.BilingualDictionaryWithLongestMatchSearch.BilingualDictionaryWithLongestMatchSearchServiceLocator;
import localhost.wrapper_mock_1_2.services.Dictionary.Dictionary_ServiceLocator;
import localhost.wrapper_mock_1_2.services.LanguageIdentification.LanguageIdentificationServiceLocator;
import localhost.wrapper_mock_1_2.services.MetadataForParallelText.MetadataForParallelTextServiceLocator;
import localhost.wrapper_mock_1_2.services.MorphologicalAnalysis.MorphologicalAnalysis_ServiceLocator;
import localhost.wrapper_mock_1_2.services.MultihopTranslation.MultihopTranslation_ServiceLocator;
import localhost.wrapper_mock_1_2.services.ParallelTextWithEmbeddedMetadata.ParallelTextWithEmbeddedMetadataServiceLocator;
import localhost.wrapper_mock_1_2.services.ParallelTextWithExternalMetadata.ParallelTextWithExternalMetadataServiceLocator;
import localhost.wrapper_mock_1_2.services.ParallelTextWithId.ParallelTextWithIdServiceLocator;
import localhost.wrapper_mock_1_2.services.SimilarityCalculation.SimilarityCalculation_ServiceLocator;
import localhost.wrapper_mock_1_2.services.Translation.Translation_ServiceLocator;
import localhost.wrapper_mock_1_2.services.TranslationWithTemporalDictionary.TranslationWithTemporalDictionaryServiceLocator;
import localhost.wrapper_mock_1_2_N.services.AdjacencyPair.AdjacencyPairServiceLocator;
import localhost.wrapper_mock_1_2_N.services.BilingualDictionary.BilingualDictionaryServiceLocator;
import localhost.wrapper_mock_1_2_N.services.BilingualDictionaryHeadwordsExtraction.BilingualDictionaryHeadwordsExtractionServiceLocator;
import localhost.wrapper_mock_1_2_N.services.ConceptDictionary.ConceptDictionaryServiceLocator;
import localhost.wrapper_mock_1_2_N.services.DependencyParser.DependencyParserServiceLocator;
import localhost.wrapper_mock_1_2_N.services.ParallelText.ParallelTextServiceLocator;
import localhost.wrapper_mock_1_2_N.services.Paraphrase.ParaphraseServiceLocator;
import localhost.wrapper_mock_1_2_N.services.PictogramDictionary.PictogramDictionaryServiceLocator;

public class LangridAxisClientFactory{
	public static ClientFactory getInstance(){
		return factory;
	}

	public static void setUp(AxisClientFactory f){
		f.registerStub(AdjacencyPairService.class, AdjacencyPairServiceLocator.class, "getAdjacencyPair");
		f.registerStub(AsyncTranslationService.class, AsyncTranslationServiceServiceLocator.class, "getAsyncTranslation");
		f.registerStub(AsyncTranslationWithTemporalDictionaryService.class, AsyncTranslationWithTemporalDictionaryServiceServiceLocator.class, "getAsyncTranslationWithTemporalDictionary");
		f.registerStub(BackTranslationService.class, BackTranslation_ServiceLocator.class, "getBackTranslation");
		f.registerStub(BackTranslationWithTemporalDictionaryService.class, BackTranslationWithTemporalDictionaryServiceLocator.class, "getBackTranslationWithTemporalDictionary");
		f.registerStub(BilingualDictionaryService.class, BilingualDictionaryServiceLocator.class, "getBilingualDictionary");
		f.registerStub(BilingualDictionaryHeadwordsExtractionService.class, BilingualDictionaryHeadwordsExtractionServiceLocator.class, "getBilingualDictionaryHeadwordsExtraction");
		f.registerStub(BilingualDictionaryWithLongestMatchSearchService.class, BilingualDictionaryWithLongestMatchSearchServiceLocator.class, "getBilingualDictionaryWithLongestMatchSearch");
		f.registerStub(ConceptDictionaryService.class, ConceptDictionaryServiceLocator.class, "getConceptDictionary");
		f.registerStub(DependencyParserService.class, DependencyParserServiceLocator.class, "getDependencyParser");
		f.registerStub(DictionaryService.class, Dictionary_ServiceLocator.class, "getDictionary");
		f.registerStub(KeyphraseExtractService.class, KeyphraseExtractServiceServiceLocator.class, "getKeyphraseExtract");
		f.registerStub(LanguageIdentificationService.class, LanguageIdentificationServiceLocator.class, "getLanguageIdentification");
		f.registerStub(MetadataForParallelTextService.class, MetadataForParallelTextServiceLocator.class, "getMetadataForParallelText");
		f.registerStub(MorphologicalAnalysisService.class, MorphologicalAnalysis_ServiceLocator.class, "getMorphologicalAnalysis");
		f.registerStub(MorphemesDependencyParserService.class, MorphemesDependencyParserServiceServiceLocator.class, "getMorphemesDependencyParser");
		f.registerStub(MultihopTranslationService.class, MultihopTranslation_ServiceLocator.class, "getMultihopTranslation");
		f.registerStub(NamedEntityTaggingService.class, NamedEntityTaggingServiceServiceLocator.class, "getNamedEntityTagging");
		f.registerStub(ParallelTextService.class, ParallelTextServiceLocator.class, "getParallelText");
		f.registerStub(ParallelTextWithEmbeddedMetadataService.class, ParallelTextWithEmbeddedMetadataServiceLocator.class, "getParallelTextWithEmbeddedMetadata");
		f.registerStub(ParallelTextWithExternalMetadataService.class, ParallelTextWithExternalMetadataServiceLocator.class, "getParallelTextWithExternalMetadata");
		f.registerStub(ParallelTextWithIdService.class, ParallelTextWithIdServiceLocator.class, "getParallelTextWithId");
		f.registerStub(ParaphraseService.class, ParaphraseServiceLocator.class, "getParaphrase");
		f.registerStub(PictogramDictionaryService.class, PictogramDictionaryServiceLocator.class, "getPictogramDictionary");
		f.registerStub(QualityEstimationService.class, QualityEstimationServiceServiceLocator.class, "getQualityEstimation");
		f.registerStub(SimilarityCalculationService.class, SimilarityCalculation_ServiceLocator.class, "getSimilarityCalculation");
		f.registerStub(SpeechRecognitionService.class, SpeechRecognitionServiceLocator.class, "getSpeechRecognition");
		f.registerStub(SpeechTranslationService.class, KyotouLangridSpeechTranslationLocator.class, "getSpeechTranslation");
		f.registerStub(TemplateParallelTextService.class, TemplateParallelTextServiceLocator.class, "getTemplateParallelText");
		f.registerStub(TextSummarizeService.class, TextSummarizeServiceServiceLocator.class, "getTextSummarize");
		f.registerStub(TextToSpeechService.class, TextToSpeechServiceLocator.class, "getTextToSpeech");
		f.registerStub(TranslationService.class, Translation_ServiceLocator.class, "getTranslation");
		f.registerStub(TranslationSelectionService.class, TranslationSelectionServiceServiceLocator.class, "getTranslationSelection");
		f.registerStub(TranslationWithInternalDictionaryService.class, TranslationWithInternalDictionaryServiceServiceLocator.class, "getTranslationWithInternalDictionary");
		f.registerStub(TranslationWithTemporalDictionaryService.class, TranslationWithTemporalDictionaryServiceLocator.class, "getTranslationWithTemporalDictionary");
		f.getConverter().addTransformerConversion(new StringToPartOfSpeechTransformer());
		f.getConverter().addTransformerConversion(new StringToDictMatchingMethodTransformer());
	}

	private static ClientFactory factory;
	static{
		AxisClientFactory f = new AxisClientFactory();
		setUp(f);
		factory = f;
	}
}
