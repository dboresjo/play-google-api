package com.boresjo.play.api.google.searchconsole

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaDetectedItems: Conversion[List[Schema.DetectedItems], Option[List[Schema.DetectedItems]]] = (fun: List[Schema.DetectedItems]) => Option(fun)
		given putSchemaRichResultsInspectionResultVerdictEnum: Conversion[Schema.RichResultsInspectionResult.VerdictEnum, Option[Schema.RichResultsInspectionResult.VerdictEnum]] = (fun: Schema.RichResultsInspectionResult.VerdictEnum) => Option(fun)
		given putListSchemaWmxSite: Conversion[List[Schema.WmxSite], Option[List[Schema.WmxSite]]] = (fun: List[Schema.WmxSite]) => Option(fun)
		given putSchemaMobileFriendlyIssueRuleEnum: Conversion[Schema.MobileFriendlyIssue.RuleEnum, Option[Schema.MobileFriendlyIssue.RuleEnum]] = (fun: Schema.MobileFriendlyIssue.RuleEnum) => Option(fun)
		given putSchemaApiDimensionFilterGroupGroupTypeEnum: Conversion[Schema.ApiDimensionFilterGroup.GroupTypeEnum, Option[Schema.ApiDimensionFilterGroup.GroupTypeEnum]] = (fun: Schema.ApiDimensionFilterGroup.GroupTypeEnum) => Option(fun)
		given putListSchemaApiDimensionFilter: Conversion[List[Schema.ApiDimensionFilter], Option[List[Schema.ApiDimensionFilter]]] = (fun: List[Schema.ApiDimensionFilter]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaSearchAnalyticsQueryRequestAggregationTypeEnum: Conversion[Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum, Option[Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum]] = (fun: Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum) => Option(fun)
		given putSchemaSearchAnalyticsQueryRequestTypeEnum: Conversion[Schema.SearchAnalyticsQueryRequest.TypeEnum, Option[Schema.SearchAnalyticsQueryRequest.TypeEnum]] = (fun: Schema.SearchAnalyticsQueryRequest.TypeEnum) => Option(fun)
		given putSchemaSearchAnalyticsQueryRequestSearchTypeEnum: Conversion[Schema.SearchAnalyticsQueryRequest.SearchTypeEnum, Option[Schema.SearchAnalyticsQueryRequest.SearchTypeEnum]] = (fun: Schema.SearchAnalyticsQueryRequest.SearchTypeEnum) => Option(fun)
		given putListSchemaSearchAnalyticsQueryRequestDimensionsEnum: Conversion[List[Schema.SearchAnalyticsQueryRequest.DimensionsEnum], Option[List[Schema.SearchAnalyticsQueryRequest.DimensionsEnum]]] = (fun: List[Schema.SearchAnalyticsQueryRequest.DimensionsEnum]) => Option(fun)
		given putListSchemaApiDimensionFilterGroup: Conversion[List[Schema.ApiDimensionFilterGroup], Option[List[Schema.ApiDimensionFilterGroup]]] = (fun: List[Schema.ApiDimensionFilterGroup]) => Option(fun)
		given putSchemaSearchAnalyticsQueryRequestDataStateEnum: Conversion[Schema.SearchAnalyticsQueryRequest.DataStateEnum, Option[Schema.SearchAnalyticsQueryRequest.DataStateEnum]] = (fun: Schema.SearchAnalyticsQueryRequest.DataStateEnum) => Option(fun)
		given putListSchemaItem: Conversion[List[Schema.Item], Option[List[Schema.Item]]] = (fun: List[Schema.Item]) => Option(fun)
		given putSchemaAmpInspectionResult: Conversion[Schema.AmpInspectionResult, Option[Schema.AmpInspectionResult]] = (fun: Schema.AmpInspectionResult) => Option(fun)
		given putSchemaIndexStatusInspectionResult: Conversion[Schema.IndexStatusInspectionResult, Option[Schema.IndexStatusInspectionResult]] = (fun: Schema.IndexStatusInspectionResult) => Option(fun)
		given putSchemaRichResultsInspectionResult: Conversion[Schema.RichResultsInspectionResult, Option[Schema.RichResultsInspectionResult]] = (fun: Schema.RichResultsInspectionResult) => Option(fun)
		given putSchemaMobileUsabilityInspectionResult: Conversion[Schema.MobileUsabilityInspectionResult, Option[Schema.MobileUsabilityInspectionResult]] = (fun: Schema.MobileUsabilityInspectionResult) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaWmxSitemapContent: Conversion[List[Schema.WmxSitemapContent], Option[List[Schema.WmxSitemapContent]]] = (fun: List[Schema.WmxSitemapContent]) => Option(fun)
		given putSchemaWmxSitemapTypeEnum: Conversion[Schema.WmxSitemap.TypeEnum, Option[Schema.WmxSitemap.TypeEnum]] = (fun: Schema.WmxSitemap.TypeEnum) => Option(fun)
		given putSchemaAmpIssueSeverityEnum: Conversion[Schema.AmpIssue.SeverityEnum, Option[Schema.AmpIssue.SeverityEnum]] = (fun: Schema.AmpIssue.SeverityEnum) => Option(fun)
		given putSchemaApiDimensionFilterOperatorEnum: Conversion[Schema.ApiDimensionFilter.OperatorEnum, Option[Schema.ApiDimensionFilter.OperatorEnum]] = (fun: Schema.ApiDimensionFilter.OperatorEnum) => Option(fun)
		given putSchemaApiDimensionFilterDimensionEnum: Conversion[Schema.ApiDimensionFilter.DimensionEnum, Option[Schema.ApiDimensionFilter.DimensionEnum]] = (fun: Schema.ApiDimensionFilter.DimensionEnum) => Option(fun)
		given putSchemaUrlInspectionResult: Conversion[Schema.UrlInspectionResult, Option[Schema.UrlInspectionResult]] = (fun: Schema.UrlInspectionResult) => Option(fun)
		given putSchemaWmxSitemapContentTypeEnum: Conversion[Schema.WmxSitemapContent.TypeEnum, Option[Schema.WmxSitemapContent.TypeEnum]] = (fun: Schema.WmxSitemapContent.TypeEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaResourceIssue: Conversion[List[Schema.ResourceIssue], Option[List[Schema.ResourceIssue]]] = (fun: List[Schema.ResourceIssue]) => Option(fun)
		given putSchemaTestStatus: Conversion[Schema.TestStatus, Option[Schema.TestStatus]] = (fun: Schema.TestStatus) => Option(fun)
		given putListSchemaMobileFriendlyIssue: Conversion[List[Schema.MobileFriendlyIssue], Option[List[Schema.MobileFriendlyIssue]]] = (fun: List[Schema.MobileFriendlyIssue]) => Option(fun)
		given putSchemaRunMobileFriendlyTestResponseMobileFriendlinessEnum: Conversion[Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum, Option[Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum]] = (fun: Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum) => Option(fun)
		given putSchemaImage: Conversion[Schema.Image, Option[Schema.Image]] = (fun: Schema.Image) => Option(fun)
		given putListSchemaRichResultsIssue: Conversion[List[Schema.RichResultsIssue], Option[List[Schema.RichResultsIssue]]] = (fun: List[Schema.RichResultsIssue]) => Option(fun)
		given putSchemaSearchAnalyticsQueryResponseResponseAggregationTypeEnum: Conversion[Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum, Option[Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum]] = (fun: Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum) => Option(fun)
		given putListSchemaApiDataRow: Conversion[List[Schema.ApiDataRow], Option[List[Schema.ApiDataRow]]] = (fun: List[Schema.ApiDataRow]) => Option(fun)
		given putSchemaMobileUsabilityInspectionResultVerdictEnum: Conversion[Schema.MobileUsabilityInspectionResult.VerdictEnum, Option[Schema.MobileUsabilityInspectionResult.VerdictEnum]] = (fun: Schema.MobileUsabilityInspectionResult.VerdictEnum) => Option(fun)
		given putListSchemaMobileUsabilityIssue: Conversion[List[Schema.MobileUsabilityIssue], Option[List[Schema.MobileUsabilityIssue]]] = (fun: List[Schema.MobileUsabilityIssue]) => Option(fun)
		given putSchemaTestStatusStatusEnum: Conversion[Schema.TestStatus.StatusEnum, Option[Schema.TestStatus.StatusEnum]] = (fun: Schema.TestStatus.StatusEnum) => Option(fun)
		given putSchemaBlockedResource: Conversion[Schema.BlockedResource, Option[Schema.BlockedResource]] = (fun: Schema.BlockedResource) => Option(fun)
		given putSchemaRichResultsIssueSeverityEnum: Conversion[Schema.RichResultsIssue.SeverityEnum, Option[Schema.RichResultsIssue.SeverityEnum]] = (fun: Schema.RichResultsIssue.SeverityEnum) => Option(fun)
		given putListSchemaWmxSitemap: Conversion[List[Schema.WmxSitemap], Option[List[Schema.WmxSitemap]]] = (fun: List[Schema.WmxSitemap]) => Option(fun)
		given putSchemaMobileUsabilityIssueSeverityEnum: Conversion[Schema.MobileUsabilityIssue.SeverityEnum, Option[Schema.MobileUsabilityIssue.SeverityEnum]] = (fun: Schema.MobileUsabilityIssue.SeverityEnum) => Option(fun)
		given putSchemaMobileUsabilityIssueIssueTypeEnum: Conversion[Schema.MobileUsabilityIssue.IssueTypeEnum, Option[Schema.MobileUsabilityIssue.IssueTypeEnum]] = (fun: Schema.MobileUsabilityIssue.IssueTypeEnum) => Option(fun)
		given putSchemaWmxSitePermissionLevelEnum: Conversion[Schema.WmxSite.PermissionLevelEnum, Option[Schema.WmxSite.PermissionLevelEnum]] = (fun: Schema.WmxSite.PermissionLevelEnum) => Option(fun)
		given putListSchemaAmpIssue: Conversion[List[Schema.AmpIssue], Option[List[Schema.AmpIssue]]] = (fun: List[Schema.AmpIssue]) => Option(fun)
		given putSchemaAmpInspectionResultVerdictEnum: Conversion[Schema.AmpInspectionResult.VerdictEnum, Option[Schema.AmpInspectionResult.VerdictEnum]] = (fun: Schema.AmpInspectionResult.VerdictEnum) => Option(fun)
		given putSchemaAmpInspectionResultPageFetchStateEnum: Conversion[Schema.AmpInspectionResult.PageFetchStateEnum, Option[Schema.AmpInspectionResult.PageFetchStateEnum]] = (fun: Schema.AmpInspectionResult.PageFetchStateEnum) => Option(fun)
		given putSchemaAmpInspectionResultIndexingStateEnum: Conversion[Schema.AmpInspectionResult.IndexingStateEnum, Option[Schema.AmpInspectionResult.IndexingStateEnum]] = (fun: Schema.AmpInspectionResult.IndexingStateEnum) => Option(fun)
		given putSchemaAmpInspectionResultRobotsTxtStateEnum: Conversion[Schema.AmpInspectionResult.RobotsTxtStateEnum, Option[Schema.AmpInspectionResult.RobotsTxtStateEnum]] = (fun: Schema.AmpInspectionResult.RobotsTxtStateEnum) => Option(fun)
		given putSchemaAmpInspectionResultAmpIndexStatusVerdictEnum: Conversion[Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum, Option[Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum]] = (fun: Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum) => Option(fun)
		given putSchemaIndexStatusInspectionResultVerdictEnum: Conversion[Schema.IndexStatusInspectionResult.VerdictEnum, Option[Schema.IndexStatusInspectionResult.VerdictEnum]] = (fun: Schema.IndexStatusInspectionResult.VerdictEnum) => Option(fun)
		given putSchemaIndexStatusInspectionResultIndexingStateEnum: Conversion[Schema.IndexStatusInspectionResult.IndexingStateEnum, Option[Schema.IndexStatusInspectionResult.IndexingStateEnum]] = (fun: Schema.IndexStatusInspectionResult.IndexingStateEnum) => Option(fun)
		given putSchemaIndexStatusInspectionResultRobotsTxtStateEnum: Conversion[Schema.IndexStatusInspectionResult.RobotsTxtStateEnum, Option[Schema.IndexStatusInspectionResult.RobotsTxtStateEnum]] = (fun: Schema.IndexStatusInspectionResult.RobotsTxtStateEnum) => Option(fun)
		given putSchemaIndexStatusInspectionResultCrawledAsEnum: Conversion[Schema.IndexStatusInspectionResult.CrawledAsEnum, Option[Schema.IndexStatusInspectionResult.CrawledAsEnum]] = (fun: Schema.IndexStatusInspectionResult.CrawledAsEnum) => Option(fun)
		given putSchemaIndexStatusInspectionResultPageFetchStateEnum: Conversion[Schema.IndexStatusInspectionResult.PageFetchStateEnum, Option[Schema.IndexStatusInspectionResult.PageFetchStateEnum]] = (fun: Schema.IndexStatusInspectionResult.PageFetchStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
