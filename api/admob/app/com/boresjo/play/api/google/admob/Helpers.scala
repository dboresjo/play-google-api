package com.boresjo.play.api.google.admob

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaPublisherAccount: Conversion[List[Schema.PublisherAccount], Option[List[Schema.PublisherAccount]]] = (fun: List[Schema.PublisherAccount]) => Option(fun)
		given putSchemaNetworkReportSpec: Conversion[Schema.NetworkReportSpec, Option[Schema.NetworkReportSpec]] = (fun: Schema.NetworkReportSpec) => Option(fun)
		given putSchemaDateRange: Conversion[Schema.DateRange, Option[Schema.DateRange]] = (fun: Schema.DateRange) => Option(fun)
		given putListSchemaNetworkReportSpecDimensionsEnum: Conversion[List[Schema.NetworkReportSpec.DimensionsEnum], Option[List[Schema.NetworkReportSpec.DimensionsEnum]]] = (fun: List[Schema.NetworkReportSpec.DimensionsEnum]) => Option(fun)
		given putListSchemaNetworkReportSpecMetricsEnum: Conversion[List[Schema.NetworkReportSpec.MetricsEnum], Option[List[Schema.NetworkReportSpec.MetricsEnum]]] = (fun: List[Schema.NetworkReportSpec.MetricsEnum]) => Option(fun)
		given putListSchemaNetworkReportSpecDimensionFilter: Conversion[List[Schema.NetworkReportSpecDimensionFilter], Option[List[Schema.NetworkReportSpecDimensionFilter]]] = (fun: List[Schema.NetworkReportSpecDimensionFilter]) => Option(fun)
		given putListSchemaNetworkReportSpecSortCondition: Conversion[List[Schema.NetworkReportSpecSortCondition], Option[List[Schema.NetworkReportSpecSortCondition]]] = (fun: List[Schema.NetworkReportSpecSortCondition]) => Option(fun)
		given putSchemaLocalizationSettings: Conversion[Schema.LocalizationSettings, Option[Schema.LocalizationSettings]] = (fun: Schema.LocalizationSettings) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaStringList: Conversion[Schema.StringList, Option[Schema.StringList]] = (fun: Schema.StringList) => Option(fun)
		given putSchemaNetworkReportSpecDimensionFilterDimensionEnum: Conversion[Schema.NetworkReportSpecDimensionFilter.DimensionEnum, Option[Schema.NetworkReportSpecDimensionFilter.DimensionEnum]] = (fun: Schema.NetworkReportSpecDimensionFilter.DimensionEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaNetworkReportSpecSortConditionDimensionEnum: Conversion[Schema.NetworkReportSpecSortCondition.DimensionEnum, Option[Schema.NetworkReportSpecSortCondition.DimensionEnum]] = (fun: Schema.NetworkReportSpecSortCondition.DimensionEnum) => Option(fun)
		given putSchemaNetworkReportSpecSortConditionMetricEnum: Conversion[Schema.NetworkReportSpecSortCondition.MetricEnum, Option[Schema.NetworkReportSpecSortCondition.MetricEnum]] = (fun: Schema.NetworkReportSpecSortCondition.MetricEnum) => Option(fun)
		given putSchemaNetworkReportSpecSortConditionOrderEnum: Conversion[Schema.NetworkReportSpecSortCondition.OrderEnum, Option[Schema.NetworkReportSpecSortCondition.OrderEnum]] = (fun: Schema.NetworkReportSpecSortCondition.OrderEnum) => Option(fun)
		given putSchemaReportHeader: Conversion[Schema.ReportHeader, Option[Schema.ReportHeader]] = (fun: Schema.ReportHeader) => Option(fun)
		given putSchemaReportRow: Conversion[Schema.ReportRow, Option[Schema.ReportRow]] = (fun: Schema.ReportRow) => Option(fun)
		given putSchemaReportFooter: Conversion[Schema.ReportFooter, Option[Schema.ReportFooter]] = (fun: Schema.ReportFooter) => Option(fun)
		given putMapStringSchemaReportRowDimensionValue: Conversion[Map[String, Schema.ReportRowDimensionValue], Option[Map[String, Schema.ReportRowDimensionValue]]] = (fun: Map[String, Schema.ReportRowDimensionValue]) => Option(fun)
		given putMapStringSchemaReportRowMetricValue: Conversion[Map[String, Schema.ReportRowMetricValue], Option[Map[String, Schema.ReportRowMetricValue]]] = (fun: Map[String, Schema.ReportRowMetricValue]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaReportWarning: Conversion[List[Schema.ReportWarning], Option[List[Schema.ReportWarning]]] = (fun: List[Schema.ReportWarning]) => Option(fun)
		given putSchemaReportWarningTypeEnum: Conversion[Schema.ReportWarning.TypeEnum, Option[Schema.ReportWarning.TypeEnum]] = (fun: Schema.ReportWarning.TypeEnum) => Option(fun)
		given putSchemaMediationReportSpec: Conversion[Schema.MediationReportSpec, Option[Schema.MediationReportSpec]] = (fun: Schema.MediationReportSpec) => Option(fun)
		given putListSchemaMediationReportSpecDimensionsEnum: Conversion[List[Schema.MediationReportSpec.DimensionsEnum], Option[List[Schema.MediationReportSpec.DimensionsEnum]]] = (fun: List[Schema.MediationReportSpec.DimensionsEnum]) => Option(fun)
		given putListSchemaMediationReportSpecMetricsEnum: Conversion[List[Schema.MediationReportSpec.MetricsEnum], Option[List[Schema.MediationReportSpec.MetricsEnum]]] = (fun: List[Schema.MediationReportSpec.MetricsEnum]) => Option(fun)
		given putListSchemaMediationReportSpecDimensionFilter: Conversion[List[Schema.MediationReportSpecDimensionFilter], Option[List[Schema.MediationReportSpecDimensionFilter]]] = (fun: List[Schema.MediationReportSpecDimensionFilter]) => Option(fun)
		given putListSchemaMediationReportSpecSortCondition: Conversion[List[Schema.MediationReportSpecSortCondition], Option[List[Schema.MediationReportSpecSortCondition]]] = (fun: List[Schema.MediationReportSpecSortCondition]) => Option(fun)
		given putSchemaMediationReportSpecDimensionFilterDimensionEnum: Conversion[Schema.MediationReportSpecDimensionFilter.DimensionEnum, Option[Schema.MediationReportSpecDimensionFilter.DimensionEnum]] = (fun: Schema.MediationReportSpecDimensionFilter.DimensionEnum) => Option(fun)
		given putSchemaMediationReportSpecSortConditionDimensionEnum: Conversion[Schema.MediationReportSpecSortCondition.DimensionEnum, Option[Schema.MediationReportSpecSortCondition.DimensionEnum]] = (fun: Schema.MediationReportSpecSortCondition.DimensionEnum) => Option(fun)
		given putSchemaMediationReportSpecSortConditionMetricEnum: Conversion[Schema.MediationReportSpecSortCondition.MetricEnum, Option[Schema.MediationReportSpecSortCondition.MetricEnum]] = (fun: Schema.MediationReportSpecSortCondition.MetricEnum) => Option(fun)
		given putSchemaMediationReportSpecSortConditionOrderEnum: Conversion[Schema.MediationReportSpecSortCondition.OrderEnum, Option[Schema.MediationReportSpecSortCondition.OrderEnum]] = (fun: Schema.MediationReportSpecSortCondition.OrderEnum) => Option(fun)
		given putListSchemaApp: Conversion[List[Schema.App], Option[List[Schema.App]]] = (fun: List[Schema.App]) => Option(fun)
		given putSchemaAppManualAppInfo: Conversion[Schema.AppManualAppInfo, Option[Schema.AppManualAppInfo]] = (fun: Schema.AppManualAppInfo) => Option(fun)
		given putSchemaAppLinkedAppInfo: Conversion[Schema.AppLinkedAppInfo, Option[Schema.AppLinkedAppInfo]] = (fun: Schema.AppLinkedAppInfo) => Option(fun)
		given putSchemaAppAppApprovalStateEnum: Conversion[Schema.App.AppApprovalStateEnum, Option[Schema.App.AppApprovalStateEnum]] = (fun: Schema.App.AppApprovalStateEnum) => Option(fun)
		given putListSchemaAdUnit: Conversion[List[Schema.AdUnit], Option[List[Schema.AdUnit]]] = (fun: List[Schema.AdUnit]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
