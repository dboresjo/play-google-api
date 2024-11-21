package com.boresjo.play.api.google.bigquerydatatransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtDataSource: Format[Schema.DataSource] = Json.format[Schema.DataSource]
	given fmtDataSourceTransferTypeEnum: Format[Schema.DataSource.TransferTypeEnum] = JsonEnumFormat[Schema.DataSource.TransferTypeEnum]
	given fmtDataSourceParameter: Format[Schema.DataSourceParameter] = Json.format[Schema.DataSourceParameter]
	given fmtDataSourceAuthorizationTypeEnum: Format[Schema.DataSource.AuthorizationTypeEnum] = JsonEnumFormat[Schema.DataSource.AuthorizationTypeEnum]
	given fmtDataSourceDataRefreshTypeEnum: Format[Schema.DataSource.DataRefreshTypeEnum] = JsonEnumFormat[Schema.DataSource.DataRefreshTypeEnum]
	given fmtDataSourceParameterTypeEnum: Format[Schema.DataSourceParameter.TypeEnum] = JsonEnumFormat[Schema.DataSourceParameter.TypeEnum]
	given fmtListDataSourcesResponse: Format[Schema.ListDataSourcesResponse] = Json.format[Schema.ListDataSourcesResponse]
	given fmtTransferConfig: Format[Schema.TransferConfig] = Json.format[Schema.TransferConfig]
	given fmtScheduleOptions: Format[Schema.ScheduleOptions] = Json.format[Schema.ScheduleOptions]
	given fmtScheduleOptionsV2: Format[Schema.ScheduleOptionsV2] = Json.format[Schema.ScheduleOptionsV2]
	given fmtTransferConfigStateEnum: Format[Schema.TransferConfig.StateEnum] = JsonEnumFormat[Schema.TransferConfig.StateEnum]
	given fmtEmailPreferences: Format[Schema.EmailPreferences] = Json.format[Schema.EmailPreferences]
	given fmtUserInfo: Format[Schema.UserInfo] = Json.format[Schema.UserInfo]
	given fmtEncryptionConfiguration: Format[Schema.EncryptionConfiguration] = Json.format[Schema.EncryptionConfiguration]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtTimeBasedSchedule: Format[Schema.TimeBasedSchedule] = Json.format[Schema.TimeBasedSchedule]
	given fmtManualSchedule: Format[Schema.ManualSchedule] = Json.format[Schema.ManualSchedule]
	given fmtEventDrivenSchedule: Format[Schema.EventDrivenSchedule] = Json.format[Schema.EventDrivenSchedule]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListTransferConfigsResponse: Format[Schema.ListTransferConfigsResponse] = Json.format[Schema.ListTransferConfigsResponse]
	given fmtScheduleTransferRunsRequest: Format[Schema.ScheduleTransferRunsRequest] = Json.format[Schema.ScheduleTransferRunsRequest]
	given fmtScheduleTransferRunsResponse: Format[Schema.ScheduleTransferRunsResponse] = Json.format[Schema.ScheduleTransferRunsResponse]
	given fmtTransferRun: Format[Schema.TransferRun] = Json.format[Schema.TransferRun]
	given fmtTransferRunStateEnum: Format[Schema.TransferRun.StateEnum] = JsonEnumFormat[Schema.TransferRun.StateEnum]
	given fmtStartManualTransferRunsRequest: Format[Schema.StartManualTransferRunsRequest] = Json.format[Schema.StartManualTransferRunsRequest]
	given fmtTimeRange: Format[Schema.TimeRange] = Json.format[Schema.TimeRange]
	given fmtStartManualTransferRunsResponse: Format[Schema.StartManualTransferRunsResponse] = Json.format[Schema.StartManualTransferRunsResponse]
	given fmtListTransferRunsResponse: Format[Schema.ListTransferRunsResponse] = Json.format[Schema.ListTransferRunsResponse]
	given fmtListTransferLogsResponse: Format[Schema.ListTransferLogsResponse] = Json.format[Schema.ListTransferLogsResponse]
	given fmtTransferMessage: Format[Schema.TransferMessage] = Json.format[Schema.TransferMessage]
	given fmtTransferMessageSeverityEnum: Format[Schema.TransferMessage.SeverityEnum] = JsonEnumFormat[Schema.TransferMessage.SeverityEnum]
	given fmtCheckValidCredsRequest: Format[Schema.CheckValidCredsRequest] = Json.format[Schema.CheckValidCredsRequest]
	given fmtCheckValidCredsResponse: Format[Schema.CheckValidCredsResponse] = Json.format[Schema.CheckValidCredsResponse]
	given fmtEnrollDataSourcesRequest: Format[Schema.EnrollDataSourcesRequest] = Json.format[Schema.EnrollDataSourcesRequest]
	given fmtUnenrollDataSourcesRequest: Format[Schema.UnenrollDataSourcesRequest] = Json.format[Schema.UnenrollDataSourcesRequest]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
}
