package com.boresjo.play.api.google.jobs

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaCompanySizeEnum: Conversion[Schema.Company.SizeEnum, Option[Schema.Company.SizeEnum]] = (fun: Schema.Company.SizeEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaCompanyDerivedInfo: Conversion[Schema.CompanyDerivedInfo, Option[Schema.CompanyDerivedInfo]] = (fun: Schema.CompanyDerivedInfo) => Option(fun)
		given putSchemaLocation: Conversion[Schema.Location, Option[Schema.Location]] = (fun: Schema.Location) => Option(fun)
		given putSchemaLocationLocationTypeEnum: Conversion[Schema.Location.LocationTypeEnum, Option[Schema.Location.LocationTypeEnum]] = (fun: Schema.Location.LocationTypeEnum) => Option(fun)
		given putSchemaPostalAddress: Conversion[Schema.PostalAddress, Option[Schema.PostalAddress]] = (fun: Schema.PostalAddress) => Option(fun)
		given putSchemaLatLng: Conversion[Schema.LatLng, Option[Schema.LatLng]] = (fun: Schema.LatLng) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaCompany: Conversion[List[Schema.Company], Option[List[Schema.Company]]] = (fun: List[Schema.Company]) => Option(fun)
		given putSchemaResponseMetadata: Conversion[Schema.ResponseMetadata, Option[Schema.ResponseMetadata]] = (fun: Schema.ResponseMetadata) => Option(fun)
		given putListSchemaCompletionResult: Conversion[List[Schema.CompletionResult], Option[List[Schema.CompletionResult]]] = (fun: List[Schema.CompletionResult]) => Option(fun)
		given putSchemaCompletionResultTypeEnum: Conversion[Schema.CompletionResult.TypeEnum, Option[Schema.CompletionResult.TypeEnum]] = (fun: Schema.CompletionResult.TypeEnum) => Option(fun)
		given putSchemaJobEvent: Conversion[Schema.JobEvent, Option[Schema.JobEvent]] = (fun: Schema.JobEvent) => Option(fun)
		given putSchemaJobEventTypeEnum: Conversion[Schema.JobEvent.TypeEnum, Option[Schema.JobEvent.TypeEnum]] = (fun: Schema.JobEvent.TypeEnum) => Option(fun)
		given putSchemaApplicationInfo: Conversion[Schema.ApplicationInfo, Option[Schema.ApplicationInfo]] = (fun: Schema.ApplicationInfo) => Option(fun)
		given putListSchemaJobJobBenefitsEnum: Conversion[List[Schema.Job.JobBenefitsEnum], Option[List[Schema.Job.JobBenefitsEnum]]] = (fun: List[Schema.Job.JobBenefitsEnum]) => Option(fun)
		given putSchemaCompensationInfo: Conversion[Schema.CompensationInfo, Option[Schema.CompensationInfo]] = (fun: Schema.CompensationInfo) => Option(fun)
		given putMapStringSchemaCustomAttribute: Conversion[Map[String, Schema.CustomAttribute], Option[Map[String, Schema.CustomAttribute]]] = (fun: Map[String, Schema.CustomAttribute]) => Option(fun)
		given putListSchemaJobDegreeTypesEnum: Conversion[List[Schema.Job.DegreeTypesEnum], Option[List[Schema.Job.DegreeTypesEnum]]] = (fun: List[Schema.Job.DegreeTypesEnum]) => Option(fun)
		given putListSchemaJobEmploymentTypesEnum: Conversion[List[Schema.Job.EmploymentTypesEnum], Option[List[Schema.Job.EmploymentTypesEnum]]] = (fun: List[Schema.Job.EmploymentTypesEnum]) => Option(fun)
		given putSchemaJobJobLevelEnum: Conversion[Schema.Job.JobLevelEnum, Option[Schema.Job.JobLevelEnum]] = (fun: Schema.Job.JobLevelEnum) => Option(fun)
		given putSchemaJobPostingRegionEnum: Conversion[Schema.Job.PostingRegionEnum, Option[Schema.Job.PostingRegionEnum]] = (fun: Schema.Job.PostingRegionEnum) => Option(fun)
		given putSchemaJobVisibilityEnum: Conversion[Schema.Job.VisibilityEnum, Option[Schema.Job.VisibilityEnum]] = (fun: Schema.Job.VisibilityEnum) => Option(fun)
		given putSchemaJobDerivedInfo: Conversion[Schema.JobDerivedInfo, Option[Schema.JobDerivedInfo]] = (fun: Schema.JobDerivedInfo) => Option(fun)
		given putSchemaProcessingOptions: Conversion[Schema.ProcessingOptions, Option[Schema.ProcessingOptions]] = (fun: Schema.ProcessingOptions) => Option(fun)
		given putListSchemaCompensationEntry: Conversion[List[Schema.CompensationEntry], Option[List[Schema.CompensationEntry]]] = (fun: List[Schema.CompensationEntry]) => Option(fun)
		given putSchemaCompensationRange: Conversion[Schema.CompensationRange, Option[Schema.CompensationRange]] = (fun: Schema.CompensationRange) => Option(fun)
		given putSchemaCompensationEntryTypeEnum: Conversion[Schema.CompensationEntry.TypeEnum, Option[Schema.CompensationEntry.TypeEnum]] = (fun: Schema.CompensationEntry.TypeEnum) => Option(fun)
		given putSchemaCompensationEntryUnitEnum: Conversion[Schema.CompensationEntry.UnitEnum, Option[Schema.CompensationEntry.UnitEnum]] = (fun: Schema.CompensationEntry.UnitEnum) => Option(fun)
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putListSchemaJobDerivedInfoJobCategoriesEnum: Conversion[List[Schema.JobDerivedInfo.JobCategoriesEnum], Option[List[Schema.JobDerivedInfo.JobCategoriesEnum]]] = (fun: List[Schema.JobDerivedInfo.JobCategoriesEnum]) => Option(fun)
		given putSchemaProcessingOptionsHtmlSanitizationEnum: Conversion[Schema.ProcessingOptions.HtmlSanitizationEnum, Option[Schema.ProcessingOptions.HtmlSanitizationEnum]] = (fun: Schema.ProcessingOptions.HtmlSanitizationEnum) => Option(fun)
		given putListSchemaJob: Conversion[List[Schema.Job], Option[List[Schema.Job]]] = (fun: List[Schema.Job]) => Option(fun)
		given putSchemaSearchJobsRequestSearchModeEnum: Conversion[Schema.SearchJobsRequest.SearchModeEnum, Option[Schema.SearchJobsRequest.SearchModeEnum]] = (fun: Schema.SearchJobsRequest.SearchModeEnum) => Option(fun)
		given putSchemaRequestMetadata: Conversion[Schema.RequestMetadata, Option[Schema.RequestMetadata]] = (fun: Schema.RequestMetadata) => Option(fun)
		given putSchemaJobQuery: Conversion[Schema.JobQuery, Option[Schema.JobQuery]] = (fun: Schema.JobQuery) => Option(fun)
		given putListSchemaHistogramQuery: Conversion[List[Schema.HistogramQuery], Option[List[Schema.HistogramQuery]]] = (fun: List[Schema.HistogramQuery]) => Option(fun)
		given putSchemaSearchJobsRequestJobViewEnum: Conversion[Schema.SearchJobsRequest.JobViewEnum, Option[Schema.SearchJobsRequest.JobViewEnum]] = (fun: Schema.SearchJobsRequest.JobViewEnum) => Option(fun)
		given putSchemaSearchJobsRequestDiversificationLevelEnum: Conversion[Schema.SearchJobsRequest.DiversificationLevelEnum, Option[Schema.SearchJobsRequest.DiversificationLevelEnum]] = (fun: Schema.SearchJobsRequest.DiversificationLevelEnum) => Option(fun)
		given putSchemaCustomRankingInfo: Conversion[Schema.CustomRankingInfo, Option[Schema.CustomRankingInfo]] = (fun: Schema.CustomRankingInfo) => Option(fun)
		given putSchemaSearchJobsRequestKeywordMatchModeEnum: Conversion[Schema.SearchJobsRequest.KeywordMatchModeEnum, Option[Schema.SearchJobsRequest.KeywordMatchModeEnum]] = (fun: Schema.SearchJobsRequest.KeywordMatchModeEnum) => Option(fun)
		given putSchemaDeviceInfo: Conversion[Schema.DeviceInfo, Option[Schema.DeviceInfo]] = (fun: Schema.DeviceInfo) => Option(fun)
		given putSchemaDeviceInfoDeviceTypeEnum: Conversion[Schema.DeviceInfo.DeviceTypeEnum, Option[Schema.DeviceInfo.DeviceTypeEnum]] = (fun: Schema.DeviceInfo.DeviceTypeEnum) => Option(fun)
		given putListSchemaLocationFilter: Conversion[List[Schema.LocationFilter], Option[List[Schema.LocationFilter]]] = (fun: List[Schema.LocationFilter]) => Option(fun)
		given putListSchemaJobQueryJobCategoriesEnum: Conversion[List[Schema.JobQuery.JobCategoriesEnum], Option[List[Schema.JobQuery.JobCategoriesEnum]]] = (fun: List[Schema.JobQuery.JobCategoriesEnum]) => Option(fun)
		given putSchemaCommuteFilter: Conversion[Schema.CommuteFilter, Option[Schema.CommuteFilter]] = (fun: Schema.CommuteFilter) => Option(fun)
		given putSchemaCompensationFilter: Conversion[Schema.CompensationFilter, Option[Schema.CompensationFilter]] = (fun: Schema.CompensationFilter) => Option(fun)
		given putListSchemaJobQueryEmploymentTypesEnum: Conversion[List[Schema.JobQuery.EmploymentTypesEnum], Option[List[Schema.JobQuery.EmploymentTypesEnum]]] = (fun: List[Schema.JobQuery.EmploymentTypesEnum]) => Option(fun)
		given putSchemaTimestampRange: Conversion[Schema.TimestampRange, Option[Schema.TimestampRange]] = (fun: Schema.TimestampRange) => Option(fun)
		given putSchemaLocationFilterTelecommutePreferenceEnum: Conversion[Schema.LocationFilter.TelecommutePreferenceEnum, Option[Schema.LocationFilter.TelecommutePreferenceEnum]] = (fun: Schema.LocationFilter.TelecommutePreferenceEnum) => Option(fun)
		given putSchemaCommuteFilterCommuteMethodEnum: Conversion[Schema.CommuteFilter.CommuteMethodEnum, Option[Schema.CommuteFilter.CommuteMethodEnum]] = (fun: Schema.CommuteFilter.CommuteMethodEnum) => Option(fun)
		given putSchemaCommuteFilterRoadTrafficEnum: Conversion[Schema.CommuteFilter.RoadTrafficEnum, Option[Schema.CommuteFilter.RoadTrafficEnum]] = (fun: Schema.CommuteFilter.RoadTrafficEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaCompensationFilterTypeEnum: Conversion[Schema.CompensationFilter.TypeEnum, Option[Schema.CompensationFilter.TypeEnum]] = (fun: Schema.CompensationFilter.TypeEnum) => Option(fun)
		given putListSchemaCompensationFilterUnitsEnum: Conversion[List[Schema.CompensationFilter.UnitsEnum], Option[List[Schema.CompensationFilter.UnitsEnum]]] = (fun: List[Schema.CompensationFilter.UnitsEnum]) => Option(fun)
		given putSchemaCustomRankingInfoImportanceLevelEnum: Conversion[Schema.CustomRankingInfo.ImportanceLevelEnum, Option[Schema.CustomRankingInfo.ImportanceLevelEnum]] = (fun: Schema.CustomRankingInfo.ImportanceLevelEnum) => Option(fun)
		given putListSchemaMatchingJob: Conversion[List[Schema.MatchingJob], Option[List[Schema.MatchingJob]]] = (fun: List[Schema.MatchingJob]) => Option(fun)
		given putListSchemaHistogramQueryResult: Conversion[List[Schema.HistogramQueryResult], Option[List[Schema.HistogramQueryResult]]] = (fun: List[Schema.HistogramQueryResult]) => Option(fun)
		given putSchemaSpellingCorrection: Conversion[Schema.SpellingCorrection, Option[Schema.SpellingCorrection]] = (fun: Schema.SpellingCorrection) => Option(fun)
		given putSchemaJob: Conversion[Schema.Job, Option[Schema.Job]] = (fun: Schema.Job) => Option(fun)
		given putSchemaCommuteInfo: Conversion[Schema.CommuteInfo, Option[Schema.CommuteInfo]] = (fun: Schema.CommuteInfo) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaTenant: Conversion[List[Schema.Tenant], Option[List[Schema.Tenant]]] = (fun: List[Schema.Tenant]) => Option(fun)
		given putSchemaBatchOperationMetadataStateEnum: Conversion[Schema.BatchOperationMetadata.StateEnum, Option[Schema.BatchOperationMetadata.StateEnum]] = (fun: Schema.BatchOperationMetadata.StateEnum) => Option(fun)
		given putListSchemaJobResult: Conversion[List[Schema.JobResult], Option[List[Schema.JobResult]]] = (fun: List[Schema.JobResult]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
