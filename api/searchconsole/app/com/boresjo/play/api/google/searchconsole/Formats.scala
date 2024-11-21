package com.boresjo.play.api.google.searchconsole

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtRichResultsInspectionResult: Format[Schema.RichResultsInspectionResult] = Json.format[Schema.RichResultsInspectionResult]
	given fmtDetectedItems: Format[Schema.DetectedItems] = Json.format[Schema.DetectedItems]
	given fmtRichResultsInspectionResultVerdictEnum: Format[Schema.RichResultsInspectionResult.VerdictEnum] = JsonEnumFormat[Schema.RichResultsInspectionResult.VerdictEnum]
	given fmtSitesListResponse: Format[Schema.SitesListResponse] = Json.format[Schema.SitesListResponse]
	given fmtWmxSite: Format[Schema.WmxSite] = Json.format[Schema.WmxSite]
	given fmtMobileFriendlyIssue: Format[Schema.MobileFriendlyIssue] = Json.format[Schema.MobileFriendlyIssue]
	given fmtMobileFriendlyIssueRuleEnum: Format[Schema.MobileFriendlyIssue.RuleEnum] = JsonEnumFormat[Schema.MobileFriendlyIssue.RuleEnum]
	given fmtApiDimensionFilterGroup: Format[Schema.ApiDimensionFilterGroup] = Json.format[Schema.ApiDimensionFilterGroup]
	given fmtApiDimensionFilterGroupGroupTypeEnum: Format[Schema.ApiDimensionFilterGroup.GroupTypeEnum] = JsonEnumFormat[Schema.ApiDimensionFilterGroup.GroupTypeEnum]
	given fmtApiDimensionFilter: Format[Schema.ApiDimensionFilter] = Json.format[Schema.ApiDimensionFilter]
	given fmtBlockedResource: Format[Schema.BlockedResource] = Json.format[Schema.BlockedResource]
	given fmtSearchAnalyticsQueryRequest: Format[Schema.SearchAnalyticsQueryRequest] = Json.format[Schema.SearchAnalyticsQueryRequest]
	given fmtSearchAnalyticsQueryRequestAggregationTypeEnum: Format[Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum]
	given fmtSearchAnalyticsQueryRequestTypeEnum: Format[Schema.SearchAnalyticsQueryRequest.TypeEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryRequest.TypeEnum]
	given fmtSearchAnalyticsQueryRequestSearchTypeEnum: Format[Schema.SearchAnalyticsQueryRequest.SearchTypeEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryRequest.SearchTypeEnum]
	given fmtSearchAnalyticsQueryRequestDimensionsEnum: Format[Schema.SearchAnalyticsQueryRequest.DimensionsEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryRequest.DimensionsEnum]
	given fmtSearchAnalyticsQueryRequestDataStateEnum: Format[Schema.SearchAnalyticsQueryRequest.DataStateEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryRequest.DataStateEnum]
	given fmtItem: Format[Schema.Item] = Json.format[Schema.Item]
	given fmtInspectUrlIndexRequest: Format[Schema.InspectUrlIndexRequest] = Json.format[Schema.InspectUrlIndexRequest]
	given fmtUrlInspectionResult: Format[Schema.UrlInspectionResult] = Json.format[Schema.UrlInspectionResult]
	given fmtAmpInspectionResult: Format[Schema.AmpInspectionResult] = Json.format[Schema.AmpInspectionResult]
	given fmtIndexStatusInspectionResult: Format[Schema.IndexStatusInspectionResult] = Json.format[Schema.IndexStatusInspectionResult]
	given fmtMobileUsabilityInspectionResult: Format[Schema.MobileUsabilityInspectionResult] = Json.format[Schema.MobileUsabilityInspectionResult]
	given fmtWmxSitemap: Format[Schema.WmxSitemap] = Json.format[Schema.WmxSitemap]
	given fmtWmxSitemapContent: Format[Schema.WmxSitemapContent] = Json.format[Schema.WmxSitemapContent]
	given fmtWmxSitemapTypeEnum: Format[Schema.WmxSitemap.TypeEnum] = JsonEnumFormat[Schema.WmxSitemap.TypeEnum]
	given fmtAmpIssue: Format[Schema.AmpIssue] = Json.format[Schema.AmpIssue]
	given fmtAmpIssueSeverityEnum: Format[Schema.AmpIssue.SeverityEnum] = JsonEnumFormat[Schema.AmpIssue.SeverityEnum]
	given fmtApiDimensionFilterOperatorEnum: Format[Schema.ApiDimensionFilter.OperatorEnum] = JsonEnumFormat[Schema.ApiDimensionFilter.OperatorEnum]
	given fmtApiDimensionFilterDimensionEnum: Format[Schema.ApiDimensionFilter.DimensionEnum] = JsonEnumFormat[Schema.ApiDimensionFilter.DimensionEnum]
	given fmtInspectUrlIndexResponse: Format[Schema.InspectUrlIndexResponse] = Json.format[Schema.InspectUrlIndexResponse]
	given fmtWmxSitemapContentTypeEnum: Format[Schema.WmxSitemapContent.TypeEnum] = JsonEnumFormat[Schema.WmxSitemapContent.TypeEnum]
	given fmtApiDataRow: Format[Schema.ApiDataRow] = Json.format[Schema.ApiDataRow]
	given fmtRunMobileFriendlyTestResponse: Format[Schema.RunMobileFriendlyTestResponse] = Json.format[Schema.RunMobileFriendlyTestResponse]
	given fmtResourceIssue: Format[Schema.ResourceIssue] = Json.format[Schema.ResourceIssue]
	given fmtTestStatus: Format[Schema.TestStatus] = Json.format[Schema.TestStatus]
	given fmtRunMobileFriendlyTestResponseMobileFriendlinessEnum: Format[Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum] = JsonEnumFormat[Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum]
	given fmtImage: Format[Schema.Image] = Json.format[Schema.Image]
	given fmtRichResultsIssue: Format[Schema.RichResultsIssue] = Json.format[Schema.RichResultsIssue]
	given fmtSearchAnalyticsQueryResponse: Format[Schema.SearchAnalyticsQueryResponse] = Json.format[Schema.SearchAnalyticsQueryResponse]
	given fmtSearchAnalyticsQueryResponseResponseAggregationTypeEnum: Format[Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum] = JsonEnumFormat[Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum]
	given fmtMobileUsabilityInspectionResultVerdictEnum: Format[Schema.MobileUsabilityInspectionResult.VerdictEnum] = JsonEnumFormat[Schema.MobileUsabilityInspectionResult.VerdictEnum]
	given fmtMobileUsabilityIssue: Format[Schema.MobileUsabilityIssue] = Json.format[Schema.MobileUsabilityIssue]
	given fmtTestStatusStatusEnum: Format[Schema.TestStatus.StatusEnum] = JsonEnumFormat[Schema.TestStatus.StatusEnum]
	given fmtRunMobileFriendlyTestRequest: Format[Schema.RunMobileFriendlyTestRequest] = Json.format[Schema.RunMobileFriendlyTestRequest]
	given fmtRichResultsIssueSeverityEnum: Format[Schema.RichResultsIssue.SeverityEnum] = JsonEnumFormat[Schema.RichResultsIssue.SeverityEnum]
	given fmtSitemapsListResponse: Format[Schema.SitemapsListResponse] = Json.format[Schema.SitemapsListResponse]
	given fmtMobileUsabilityIssueSeverityEnum: Format[Schema.MobileUsabilityIssue.SeverityEnum] = JsonEnumFormat[Schema.MobileUsabilityIssue.SeverityEnum]
	given fmtMobileUsabilityIssueIssueTypeEnum: Format[Schema.MobileUsabilityIssue.IssueTypeEnum] = JsonEnumFormat[Schema.MobileUsabilityIssue.IssueTypeEnum]
	given fmtWmxSitePermissionLevelEnum: Format[Schema.WmxSite.PermissionLevelEnum] = JsonEnumFormat[Schema.WmxSite.PermissionLevelEnum]
	given fmtAmpInspectionResultVerdictEnum: Format[Schema.AmpInspectionResult.VerdictEnum] = JsonEnumFormat[Schema.AmpInspectionResult.VerdictEnum]
	given fmtAmpInspectionResultPageFetchStateEnum: Format[Schema.AmpInspectionResult.PageFetchStateEnum] = JsonEnumFormat[Schema.AmpInspectionResult.PageFetchStateEnum]
	given fmtAmpInspectionResultIndexingStateEnum: Format[Schema.AmpInspectionResult.IndexingStateEnum] = JsonEnumFormat[Schema.AmpInspectionResult.IndexingStateEnum]
	given fmtAmpInspectionResultRobotsTxtStateEnum: Format[Schema.AmpInspectionResult.RobotsTxtStateEnum] = JsonEnumFormat[Schema.AmpInspectionResult.RobotsTxtStateEnum]
	given fmtAmpInspectionResultAmpIndexStatusVerdictEnum: Format[Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum] = JsonEnumFormat[Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum]
	given fmtIndexStatusInspectionResultVerdictEnum: Format[Schema.IndexStatusInspectionResult.VerdictEnum] = JsonEnumFormat[Schema.IndexStatusInspectionResult.VerdictEnum]
	given fmtIndexStatusInspectionResultIndexingStateEnum: Format[Schema.IndexStatusInspectionResult.IndexingStateEnum] = JsonEnumFormat[Schema.IndexStatusInspectionResult.IndexingStateEnum]
	given fmtIndexStatusInspectionResultRobotsTxtStateEnum: Format[Schema.IndexStatusInspectionResult.RobotsTxtStateEnum] = JsonEnumFormat[Schema.IndexStatusInspectionResult.RobotsTxtStateEnum]
	given fmtIndexStatusInspectionResultCrawledAsEnum: Format[Schema.IndexStatusInspectionResult.CrawledAsEnum] = JsonEnumFormat[Schema.IndexStatusInspectionResult.CrawledAsEnum]
	given fmtIndexStatusInspectionResultPageFetchStateEnum: Format[Schema.IndexStatusInspectionResult.PageFetchStateEnum] = JsonEnumFormat[Schema.IndexStatusInspectionResult.PageFetchStateEnum]
}