package com.boresjo.play.api.google.androiddeviceprovisioning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaClaimDeviceRequestSectionTypeEnum: Conversion[Schema.ClaimDeviceRequest.SectionTypeEnum, Option[Schema.ClaimDeviceRequest.SectionTypeEnum]] = (fun: Schema.ClaimDeviceRequest.SectionTypeEnum) => Option(fun)
		given putSchemaDeviceIdentifier: Conversion[Schema.DeviceIdentifier, Option[Schema.DeviceIdentifier]] = (fun: Schema.DeviceIdentifier) => Option(fun)
		given putSchemaDeviceMetadata: Conversion[Schema.DeviceMetadata, Option[Schema.DeviceMetadata]] = (fun: Schema.DeviceMetadata) => Option(fun)
		given putListSchemaCompany: Conversion[List[Schema.Company], Option[List[Schema.Company]]] = (fun: List[Schema.Company]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaConfiguration: Conversion[List[Schema.Configuration], Option[List[Schema.Configuration]]] = (fun: List[Schema.Configuration]) => Option(fun)
		given putListSchemaDevice: Conversion[List[Schema.Device], Option[List[Schema.Device]]] = (fun: List[Schema.Device]) => Option(fun)
		given putListSchemaOperationPerDevice: Conversion[List[Schema.OperationPerDevice], Option[List[Schema.OperationPerDevice]]] = (fun: List[Schema.OperationPerDevice]) => Option(fun)
		given putSchemaPartnerClaimSectionTypeEnum: Conversion[Schema.PartnerClaim.SectionTypeEnum, Option[Schema.PartnerClaim.SectionTypeEnum]] = (fun: Schema.PartnerClaim.SectionTypeEnum) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaGetDeviceSimLockStateResponseSimLockStateEnum: Conversion[Schema.GetDeviceSimLockStateResponse.SimLockStateEnum, Option[Schema.GetDeviceSimLockStateResponse.SimLockStateEnum]] = (fun: Schema.GetDeviceSimLockStateResponse.SimLockStateEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaPartnerUnclaimSectionTypeEnum: Conversion[Schema.PartnerUnclaim.SectionTypeEnum, Option[Schema.PartnerUnclaim.SectionTypeEnum]] = (fun: Schema.PartnerUnclaim.SectionTypeEnum) => Option(fun)
		given putListSchemaDeviceClaim: Conversion[List[Schema.DeviceClaim], Option[List[Schema.DeviceClaim]]] = (fun: List[Schema.DeviceClaim]) => Option(fun)
		given putListSchemaPartnerClaim: Conversion[List[Schema.PartnerClaim], Option[List[Schema.PartnerClaim]]] = (fun: List[Schema.PartnerClaim]) => Option(fun)
		given putSchemaDeviceReference: Conversion[Schema.DeviceReference, Option[Schema.DeviceReference]] = (fun: Schema.DeviceReference) => Option(fun)
		given putSchemaPartnerClaim: Conversion[Schema.PartnerClaim, Option[Schema.PartnerClaim]] = (fun: Schema.PartnerClaim) => Option(fun)
		given putSchemaPerDeviceStatusInBatch: Conversion[Schema.PerDeviceStatusInBatch, Option[Schema.PerDeviceStatusInBatch]] = (fun: Schema.PerDeviceStatusInBatch) => Option(fun)
		given putSchemaPartnerUnclaim: Conversion[Schema.PartnerUnclaim, Option[Schema.PartnerUnclaim]] = (fun: Schema.PartnerUnclaim) => Option(fun)
		given putSchemaUpdateMetadataArguments: Conversion[Schema.UpdateMetadataArguments, Option[Schema.UpdateMetadataArguments]] = (fun: Schema.UpdateMetadataArguments) => Option(fun)
		given putSchemaDeviceIdentifierDeviceTypeEnum: Conversion[Schema.DeviceIdentifier.DeviceTypeEnum, Option[Schema.DeviceIdentifier.DeviceTypeEnum]] = (fun: Schema.DeviceIdentifier.DeviceTypeEnum) => Option(fun)
		given putSchemaDevicesLongRunningOperationMetadataProcessingStatusEnum: Conversion[Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum, Option[Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum]] = (fun: Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum) => Option(fun)
		given putListSchemaPartnerUnclaim: Conversion[List[Schema.PartnerUnclaim], Option[List[Schema.PartnerUnclaim]]] = (fun: List[Schema.PartnerUnclaim]) => Option(fun)
		given putSchemaUnclaimDeviceRequestSectionTypeEnum: Conversion[Schema.UnclaimDeviceRequest.SectionTypeEnum, Option[Schema.UnclaimDeviceRequest.SectionTypeEnum]] = (fun: Schema.UnclaimDeviceRequest.SectionTypeEnum) => Option(fun)
		given putListSchemaUpdateMetadataArguments: Conversion[List[Schema.UpdateMetadataArguments], Option[List[Schema.UpdateMetadataArguments]]] = (fun: List[Schema.UpdateMetadataArguments]) => Option(fun)
		given putSchemaFindDevicesByOwnerRequestSectionTypeEnum: Conversion[Schema.FindDevicesByOwnerRequest.SectionTypeEnum, Option[Schema.FindDevicesByOwnerRequest.SectionTypeEnum]] = (fun: Schema.FindDevicesByOwnerRequest.SectionTypeEnum) => Option(fun)
		given putListSchemaDpc: Conversion[List[Schema.Dpc], Option[List[Schema.Dpc]]] = (fun: List[Schema.Dpc]) => Option(fun)
		given putSchemaCompany: Conversion[Schema.Company, Option[Schema.Company]] = (fun: Schema.Company) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleWorkspaceAccount: Conversion[Schema.GoogleWorkspaceAccount, Option[Schema.GoogleWorkspaceAccount]] = (fun: Schema.GoogleWorkspaceAccount) => Option(fun)
		given putSchemaCompanyTermsStatusEnum: Conversion[Schema.Company.TermsStatusEnum, Option[Schema.Company.TermsStatusEnum]] = (fun: Schema.Company.TermsStatusEnum) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaPerDeviceStatusInBatchStatusEnum: Conversion[Schema.PerDeviceStatusInBatch.StatusEnum, Option[Schema.PerDeviceStatusInBatch.StatusEnum]] = (fun: Schema.PerDeviceStatusInBatch.StatusEnum) => Option(fun)
		given putSchemaDeviceClaimSectionTypeEnum: Conversion[Schema.DeviceClaim.SectionTypeEnum, Option[Schema.DeviceClaim.SectionTypeEnum]] = (fun: Schema.DeviceClaim.SectionTypeEnum) => Option(fun)
		given putSchemaDeviceClaimAdditionalServiceEnum: Conversion[Schema.DeviceClaim.AdditionalServiceEnum, Option[Schema.DeviceClaim.AdditionalServiceEnum]] = (fun: Schema.DeviceClaim.AdditionalServiceEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
