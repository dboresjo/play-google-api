package com.boresjo.play.api.google.recommender

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleCloudRecommenderV1ReliabilityProjectionRisksEnum: Conversion[List[Schema.GoogleCloudRecommenderV1ReliabilityProjection.RisksEnum], Option[List[Schema.GoogleCloudRecommenderV1ReliabilityProjection.RisksEnum]]] = (fun: List[Schema.GoogleCloudRecommenderV1ReliabilityProjection.RisksEnum]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1RecommendationStateInfo: Conversion[Schema.GoogleCloudRecommenderV1RecommendationStateInfo, Option[Schema.GoogleCloudRecommenderV1RecommendationStateInfo]] = (fun: Schema.GoogleCloudRecommenderV1RecommendationStateInfo) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1RecommendationInsightReference: Conversion[List[Schema.GoogleCloudRecommenderV1RecommendationInsightReference], Option[List[Schema.GoogleCloudRecommenderV1RecommendationInsightReference]]] = (fun: List[Schema.GoogleCloudRecommenderV1RecommendationInsightReference]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1RecommendationContent: Conversion[Schema.GoogleCloudRecommenderV1RecommendationContent, Option[Schema.GoogleCloudRecommenderV1RecommendationContent]] = (fun: Schema.GoogleCloudRecommenderV1RecommendationContent) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1RecommendationPriorityEnum: Conversion[Schema.GoogleCloudRecommenderV1Recommendation.PriorityEnum, Option[Schema.GoogleCloudRecommenderV1Recommendation.PriorityEnum]] = (fun: Schema.GoogleCloudRecommenderV1Recommendation.PriorityEnum) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1Impact: Conversion[List[Schema.GoogleCloudRecommenderV1Impact], Option[List[Schema.GoogleCloudRecommenderV1Impact]]] = (fun: List[Schema.GoogleCloudRecommenderV1Impact]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1Impact: Conversion[Schema.GoogleCloudRecommenderV1Impact, Option[Schema.GoogleCloudRecommenderV1Impact]] = (fun: Schema.GoogleCloudRecommenderV1Impact) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1RecommendationStateInfoStateEnum: Conversion[Schema.GoogleCloudRecommenderV1RecommendationStateInfo.StateEnum, Option[Schema.GoogleCloudRecommenderV1RecommendationStateInfo.StateEnum]] = (fun: Schema.GoogleCloudRecommenderV1RecommendationStateInfo.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1InsightTypeGenerationConfig: Conversion[Schema.GoogleCloudRecommenderV1InsightTypeGenerationConfig, Option[Schema.GoogleCloudRecommenderV1InsightTypeGenerationConfig]] = (fun: Schema.GoogleCloudRecommenderV1InsightTypeGenerationConfig) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1RecommenderGenerationConfig: Conversion[Schema.GoogleCloudRecommenderV1RecommenderGenerationConfig, Option[Schema.GoogleCloudRecommenderV1RecommenderGenerationConfig]] = (fun: Schema.GoogleCloudRecommenderV1RecommenderGenerationConfig) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1Insight: Conversion[List[Schema.GoogleCloudRecommenderV1Insight], Option[List[Schema.GoogleCloudRecommenderV1Insight]]] = (fun: List[Schema.GoogleCloudRecommenderV1Insight]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1InsightStateInfoStateEnum: Conversion[Schema.GoogleCloudRecommenderV1InsightStateInfo.StateEnum, Option[Schema.GoogleCloudRecommenderV1InsightStateInfo.StateEnum]] = (fun: Schema.GoogleCloudRecommenderV1InsightStateInfo.StateEnum) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1ValueMatcher: Conversion[Schema.GoogleCloudRecommenderV1ValueMatcher, Option[Schema.GoogleCloudRecommenderV1ValueMatcher]] = (fun: Schema.GoogleCloudRecommenderV1ValueMatcher) => Option(fun)
		given putMapStringSchemaGoogleCloudRecommenderV1ValueMatcher: Conversion[Map[String, Schema.GoogleCloudRecommenderV1ValueMatcher], Option[Map[String, Schema.GoogleCloudRecommenderV1ValueMatcher]]] = (fun: Map[String, Schema.GoogleCloudRecommenderV1ValueMatcher]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1Operation: Conversion[List[Schema.GoogleCloudRecommenderV1Operation], Option[List[Schema.GoogleCloudRecommenderV1Operation]]] = (fun: List[Schema.GoogleCloudRecommenderV1Operation]) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1Recommendation: Conversion[List[Schema.GoogleCloudRecommenderV1Recommendation], Option[List[Schema.GoogleCloudRecommenderV1Recommendation]]] = (fun: List[Schema.GoogleCloudRecommenderV1Recommendation]) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1OperationGroup: Conversion[List[Schema.GoogleCloudRecommenderV1OperationGroup], Option[List[Schema.GoogleCloudRecommenderV1OperationGroup]]] = (fun: List[Schema.GoogleCloudRecommenderV1OperationGroup]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1InsightCategoryEnum: Conversion[Schema.GoogleCloudRecommenderV1Insight.CategoryEnum, Option[Schema.GoogleCloudRecommenderV1Insight.CategoryEnum]] = (fun: Schema.GoogleCloudRecommenderV1Insight.CategoryEnum) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1InsightSeverityEnum: Conversion[Schema.GoogleCloudRecommenderV1Insight.SeverityEnum, Option[Schema.GoogleCloudRecommenderV1Insight.SeverityEnum]] = (fun: Schema.GoogleCloudRecommenderV1Insight.SeverityEnum) => Option(fun)
		given putListSchemaGoogleCloudRecommenderV1InsightRecommendationReference: Conversion[List[Schema.GoogleCloudRecommenderV1InsightRecommendationReference], Option[List[Schema.GoogleCloudRecommenderV1InsightRecommendationReference]]] = (fun: List[Schema.GoogleCloudRecommenderV1InsightRecommendationReference]) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1InsightStateInfo: Conversion[Schema.GoogleCloudRecommenderV1InsightStateInfo, Option[Schema.GoogleCloudRecommenderV1InsightStateInfo]] = (fun: Schema.GoogleCloudRecommenderV1InsightStateInfo) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1SecurityProjection: Conversion[Schema.GoogleCloudRecommenderV1SecurityProjection, Option[Schema.GoogleCloudRecommenderV1SecurityProjection]] = (fun: Schema.GoogleCloudRecommenderV1SecurityProjection) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1SustainabilityProjection: Conversion[Schema.GoogleCloudRecommenderV1SustainabilityProjection, Option[Schema.GoogleCloudRecommenderV1SustainabilityProjection]] = (fun: Schema.GoogleCloudRecommenderV1SustainabilityProjection) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1ImpactCategoryEnum: Conversion[Schema.GoogleCloudRecommenderV1Impact.CategoryEnum, Option[Schema.GoogleCloudRecommenderV1Impact.CategoryEnum]] = (fun: Schema.GoogleCloudRecommenderV1Impact.CategoryEnum) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1ReliabilityProjection: Conversion[Schema.GoogleCloudRecommenderV1ReliabilityProjection, Option[Schema.GoogleCloudRecommenderV1ReliabilityProjection]] = (fun: Schema.GoogleCloudRecommenderV1ReliabilityProjection) => Option(fun)
		given putSchemaGoogleCloudRecommenderV1CostProjection: Conversion[Schema.GoogleCloudRecommenderV1CostProjection, Option[Schema.GoogleCloudRecommenderV1CostProjection]] = (fun: Schema.GoogleCloudRecommenderV1CostProjection) => Option(fun)
		given putSchemaGoogleTypeMoney: Conversion[Schema.GoogleTypeMoney, Option[Schema.GoogleTypeMoney]] = (fun: Schema.GoogleTypeMoney) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
