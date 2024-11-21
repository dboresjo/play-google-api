package com.boresjo.play.api.google.admob

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtPublisherAccount: Format[Schema.PublisherAccount] = Json.format[Schema.PublisherAccount]
	given fmtListPublisherAccountsResponse: Format[Schema.ListPublisherAccountsResponse] = Json.format[Schema.ListPublisherAccountsResponse]
	given fmtGenerateNetworkReportRequest: Format[Schema.GenerateNetworkReportRequest] = Json.format[Schema.GenerateNetworkReportRequest]
	given fmtNetworkReportSpec: Format[Schema.NetworkReportSpec] = Json.format[Schema.NetworkReportSpec]
	given fmtDateRange: Format[Schema.DateRange] = Json.format[Schema.DateRange]
	given fmtNetworkReportSpecDimensionsEnum: Format[Schema.NetworkReportSpec.DimensionsEnum] = JsonEnumFormat[Schema.NetworkReportSpec.DimensionsEnum]
	given fmtNetworkReportSpecMetricsEnum: Format[Schema.NetworkReportSpec.MetricsEnum] = JsonEnumFormat[Schema.NetworkReportSpec.MetricsEnum]
	given fmtNetworkReportSpecDimensionFilter: Format[Schema.NetworkReportSpecDimensionFilter] = Json.format[Schema.NetworkReportSpecDimensionFilter]
	given fmtNetworkReportSpecSortCondition: Format[Schema.NetworkReportSpecSortCondition] = Json.format[Schema.NetworkReportSpecSortCondition]
	given fmtLocalizationSettings: Format[Schema.LocalizationSettings] = Json.format[Schema.LocalizationSettings]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtStringList: Format[Schema.StringList] = Json.format[Schema.StringList]
	given fmtNetworkReportSpecDimensionFilterDimensionEnum: Format[Schema.NetworkReportSpecDimensionFilter.DimensionEnum] = JsonEnumFormat[Schema.NetworkReportSpecDimensionFilter.DimensionEnum]
	given fmtNetworkReportSpecSortConditionDimensionEnum: Format[Schema.NetworkReportSpecSortCondition.DimensionEnum] = JsonEnumFormat[Schema.NetworkReportSpecSortCondition.DimensionEnum]
	given fmtNetworkReportSpecSortConditionMetricEnum: Format[Schema.NetworkReportSpecSortCondition.MetricEnum] = JsonEnumFormat[Schema.NetworkReportSpecSortCondition.MetricEnum]
	given fmtNetworkReportSpecSortConditionOrderEnum: Format[Schema.NetworkReportSpecSortCondition.OrderEnum] = JsonEnumFormat[Schema.NetworkReportSpecSortCondition.OrderEnum]
	given fmtGenerateNetworkReportResponse: Format[Schema.GenerateNetworkReportResponse] = Json.format[Schema.GenerateNetworkReportResponse]
	given fmtReportHeader: Format[Schema.ReportHeader] = Json.format[Schema.ReportHeader]
	given fmtReportRow: Format[Schema.ReportRow] = Json.format[Schema.ReportRow]
	given fmtReportFooter: Format[Schema.ReportFooter] = Json.format[Schema.ReportFooter]
	given fmtReportRowDimensionValue: Format[Schema.ReportRowDimensionValue] = Json.format[Schema.ReportRowDimensionValue]
	given fmtReportRowMetricValue: Format[Schema.ReportRowMetricValue] = Json.format[Schema.ReportRowMetricValue]
	given fmtReportWarning: Format[Schema.ReportWarning] = Json.format[Schema.ReportWarning]
	given fmtReportWarningTypeEnum: Format[Schema.ReportWarning.TypeEnum] = JsonEnumFormat[Schema.ReportWarning.TypeEnum]
	given fmtGenerateMediationReportRequest: Format[Schema.GenerateMediationReportRequest] = Json.format[Schema.GenerateMediationReportRequest]
	given fmtMediationReportSpec: Format[Schema.MediationReportSpec] = Json.format[Schema.MediationReportSpec]
	given fmtMediationReportSpecDimensionsEnum: Format[Schema.MediationReportSpec.DimensionsEnum] = JsonEnumFormat[Schema.MediationReportSpec.DimensionsEnum]
	given fmtMediationReportSpecMetricsEnum: Format[Schema.MediationReportSpec.MetricsEnum] = JsonEnumFormat[Schema.MediationReportSpec.MetricsEnum]
	given fmtMediationReportSpecDimensionFilter: Format[Schema.MediationReportSpecDimensionFilter] = Json.format[Schema.MediationReportSpecDimensionFilter]
	given fmtMediationReportSpecSortCondition: Format[Schema.MediationReportSpecSortCondition] = Json.format[Schema.MediationReportSpecSortCondition]
	given fmtMediationReportSpecDimensionFilterDimensionEnum: Format[Schema.MediationReportSpecDimensionFilter.DimensionEnum] = JsonEnumFormat[Schema.MediationReportSpecDimensionFilter.DimensionEnum]
	given fmtMediationReportSpecSortConditionDimensionEnum: Format[Schema.MediationReportSpecSortCondition.DimensionEnum] = JsonEnumFormat[Schema.MediationReportSpecSortCondition.DimensionEnum]
	given fmtMediationReportSpecSortConditionMetricEnum: Format[Schema.MediationReportSpecSortCondition.MetricEnum] = JsonEnumFormat[Schema.MediationReportSpecSortCondition.MetricEnum]
	given fmtMediationReportSpecSortConditionOrderEnum: Format[Schema.MediationReportSpecSortCondition.OrderEnum] = JsonEnumFormat[Schema.MediationReportSpecSortCondition.OrderEnum]
	given fmtGenerateMediationReportResponse: Format[Schema.GenerateMediationReportResponse] = Json.format[Schema.GenerateMediationReportResponse]
	given fmtListAppsResponse: Format[Schema.ListAppsResponse] = Json.format[Schema.ListAppsResponse]
	given fmtApp: Format[Schema.App] = Json.format[Schema.App]
	given fmtAppManualAppInfo: Format[Schema.AppManualAppInfo] = Json.format[Schema.AppManualAppInfo]
	given fmtAppLinkedAppInfo: Format[Schema.AppLinkedAppInfo] = Json.format[Schema.AppLinkedAppInfo]
	given fmtAppAppApprovalStateEnum: Format[Schema.App.AppApprovalStateEnum] = JsonEnumFormat[Schema.App.AppApprovalStateEnum]
	given fmtListAdUnitsResponse: Format[Schema.ListAdUnitsResponse] = Json.format[Schema.ListAdUnitsResponse]
	given fmtAdUnit: Format[Schema.AdUnit] = Json.format[Schema.AdUnit]
}
