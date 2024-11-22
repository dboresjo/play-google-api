package com.boresjo.play.api.google.contactcenteraiplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListContactCentersResponse: Format[Schema.ListContactCentersResponse] = Json.format[Schema.ListContactCentersResponse]
	given fmtContactCenter: Format[Schema.ContactCenter] = Json.format[Schema.ContactCenter]
	given fmtURIs: Format[Schema.URIs] = Json.format[Schema.URIs]
	given fmtContactCenterStateEnum: Format[Schema.ContactCenter.StateEnum] = JsonEnumFormat[Schema.ContactCenter.StateEnum]
	given fmtInstanceConfig: Format[Schema.InstanceConfig] = Json.format[Schema.InstanceConfig]
	given fmtSAMLParams: Format[Schema.SAMLParams] = Json.format[Schema.SAMLParams]
	given fmtAdminUser: Format[Schema.AdminUser] = Json.format[Schema.AdminUser]
	given fmtPrivateAccess: Format[Schema.PrivateAccess] = Json.format[Schema.PrivateAccess]
	given fmtEarly: Format[Schema.Early] = Json.format[Schema.Early]
	given fmtNormal: Format[Schema.Normal] = Json.format[Schema.Normal]
	given fmtCritical: Format[Schema.Critical] = Json.format[Schema.Critical]
	given fmtInstanceConfigInstanceSizeEnum: Format[Schema.InstanceConfig.InstanceSizeEnum] = JsonEnumFormat[Schema.InstanceConfig.InstanceSizeEnum]
	given fmtSAMLParamsAuthenticationContextsEnum: Format[Schema.SAMLParams.AuthenticationContextsEnum] = JsonEnumFormat[Schema.SAMLParams.AuthenticationContextsEnum]
	given fmtComponent: Format[Schema.Component] = Json.format[Schema.Component]
	given fmtPscSetting: Format[Schema.PscSetting] = Json.format[Schema.PscSetting]
	given fmtWeeklySchedule: Format[Schema.WeeklySchedule] = Json.format[Schema.WeeklySchedule]
	given fmtWeeklyScheduleDaysEnum: Format[Schema.WeeklySchedule.DaysEnum] = JsonEnumFormat[Schema.WeeklySchedule.DaysEnum]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtContactCenterQuota: Format[Schema.ContactCenterQuota] = Json.format[Schema.ContactCenterQuota]
	given fmtQuota: Format[Schema.Quota] = Json.format[Schema.Quota]
	given fmtQuotaContactCenterInstanceSizeEnum: Format[Schema.Quota.ContactCenterInstanceSizeEnum] = JsonEnumFormat[Schema.Quota.ContactCenterInstanceSizeEnum]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtGoogleCloudCommonOperationMetadata: Format[Schema.GoogleCloudCommonOperationMetadata] = Json.format[Schema.GoogleCloudCommonOperationMetadata]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
