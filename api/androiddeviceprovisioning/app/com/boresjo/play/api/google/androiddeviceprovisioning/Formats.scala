package com.boresjo.play.api.google.androiddeviceprovisioning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtDeviceMetadata: Format[Schema.DeviceMetadata] = Json.format[Schema.DeviceMetadata]
	given fmtClaimDeviceRequest: Format[Schema.ClaimDeviceRequest] = Json.format[Schema.ClaimDeviceRequest]
	given fmtClaimDeviceRequestSectionTypeEnum: Format[Schema.ClaimDeviceRequest.SectionTypeEnum] = JsonEnumFormat[Schema.ClaimDeviceRequest.SectionTypeEnum]
	given fmtDeviceIdentifier: Format[Schema.DeviceIdentifier] = Json.format[Schema.DeviceIdentifier]
	given fmtListCustomersResponse: Format[Schema.ListCustomersResponse] = Json.format[Schema.ListCustomersResponse]
	given fmtCompany: Format[Schema.Company] = Json.format[Schema.Company]
	given fmtCustomerListConfigurationsResponse: Format[Schema.CustomerListConfigurationsResponse] = Json.format[Schema.CustomerListConfigurationsResponse]
	given fmtConfiguration: Format[Schema.Configuration] = Json.format[Schema.Configuration]
	given fmtCustomerListDevicesResponse: Format[Schema.CustomerListDevicesResponse] = Json.format[Schema.CustomerListDevicesResponse]
	given fmtDevice: Format[Schema.Device] = Json.format[Schema.Device]
	given fmtDevicesLongRunningOperationResponse: Format[Schema.DevicesLongRunningOperationResponse] = Json.format[Schema.DevicesLongRunningOperationResponse]
	given fmtOperationPerDevice: Format[Schema.OperationPerDevice] = Json.format[Schema.OperationPerDevice]
	given fmtGetDeviceSimLockStateRequest: Format[Schema.GetDeviceSimLockStateRequest] = Json.format[Schema.GetDeviceSimLockStateRequest]
	given fmtPartnerClaim: Format[Schema.PartnerClaim] = Json.format[Schema.PartnerClaim]
	given fmtPartnerClaimSectionTypeEnum: Format[Schema.PartnerClaim.SectionTypeEnum] = JsonEnumFormat[Schema.PartnerClaim.SectionTypeEnum]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtDeviceReference: Format[Schema.DeviceReference] = Json.format[Schema.DeviceReference]
	given fmtGetDeviceSimLockStateResponse: Format[Schema.GetDeviceSimLockStateResponse] = Json.format[Schema.GetDeviceSimLockStateResponse]
	given fmtGetDeviceSimLockStateResponseSimLockStateEnum: Format[Schema.GetDeviceSimLockStateResponse.SimLockStateEnum] = JsonEnumFormat[Schema.GetDeviceSimLockStateResponse.SimLockStateEnum]
	given fmtGoogleWorkspaceAccount: Format[Schema.GoogleWorkspaceAccount] = Json.format[Schema.GoogleWorkspaceAccount]
	given fmtPartnerUnclaim: Format[Schema.PartnerUnclaim] = Json.format[Schema.PartnerUnclaim]
	given fmtPartnerUnclaimSectionTypeEnum: Format[Schema.PartnerUnclaim.SectionTypeEnum] = JsonEnumFormat[Schema.PartnerUnclaim.SectionTypeEnum]
	given fmtDeviceClaim: Format[Schema.DeviceClaim] = Json.format[Schema.DeviceClaim]
	given fmtClaimDevicesRequest: Format[Schema.ClaimDevicesRequest] = Json.format[Schema.ClaimDevicesRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListVendorCustomersResponse: Format[Schema.ListVendorCustomersResponse] = Json.format[Schema.ListVendorCustomersResponse]
	given fmtCustomerUnclaimDeviceRequest: Format[Schema.CustomerUnclaimDeviceRequest] = Json.format[Schema.CustomerUnclaimDeviceRequest]
	given fmtCustomerRemoveConfigurationRequest: Format[Schema.CustomerRemoveConfigurationRequest] = Json.format[Schema.CustomerRemoveConfigurationRequest]
	given fmtDpc: Format[Schema.Dpc] = Json.format[Schema.Dpc]
	given fmtClaimDeviceResponse: Format[Schema.ClaimDeviceResponse] = Json.format[Schema.ClaimDeviceResponse]
	given fmtUpdateDeviceMetadataRequest: Format[Schema.UpdateDeviceMetadataRequest] = Json.format[Schema.UpdateDeviceMetadataRequest]
	given fmtListVendorsResponse: Format[Schema.ListVendorsResponse] = Json.format[Schema.ListVendorsResponse]
	given fmtUpdateMetadataArguments: Format[Schema.UpdateMetadataArguments] = Json.format[Schema.UpdateMetadataArguments]
	given fmtCustomerListCustomersResponse: Format[Schema.CustomerListCustomersResponse] = Json.format[Schema.CustomerListCustomersResponse]
	given fmtPerDeviceStatusInBatch: Format[Schema.PerDeviceStatusInBatch] = Json.format[Schema.PerDeviceStatusInBatch]
	given fmtDeviceIdentifierDeviceTypeEnum: Format[Schema.DeviceIdentifier.DeviceTypeEnum] = JsonEnumFormat[Schema.DeviceIdentifier.DeviceTypeEnum]
	given fmtDevicesLongRunningOperationMetadata: Format[Schema.DevicesLongRunningOperationMetadata] = Json.format[Schema.DevicesLongRunningOperationMetadata]
	given fmtDevicesLongRunningOperationMetadataProcessingStatusEnum: Format[Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum] = JsonEnumFormat[Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum]
	given fmtUnclaimDevicesRequest: Format[Schema.UnclaimDevicesRequest] = Json.format[Schema.UnclaimDevicesRequest]
	given fmtUnclaimDeviceRequest: Format[Schema.UnclaimDeviceRequest] = Json.format[Schema.UnclaimDeviceRequest]
	given fmtUnclaimDeviceRequestSectionTypeEnum: Format[Schema.UnclaimDeviceRequest.SectionTypeEnum] = JsonEnumFormat[Schema.UnclaimDeviceRequest.SectionTypeEnum]
	given fmtFindDevicesByOwnerResponse: Format[Schema.FindDevicesByOwnerResponse] = Json.format[Schema.FindDevicesByOwnerResponse]
	given fmtUpdateDeviceMetadataInBatchRequest: Format[Schema.UpdateDeviceMetadataInBatchRequest] = Json.format[Schema.UpdateDeviceMetadataInBatchRequest]
	given fmtFindDevicesByOwnerRequest: Format[Schema.FindDevicesByOwnerRequest] = Json.format[Schema.FindDevicesByOwnerRequest]
	given fmtFindDevicesByOwnerRequestSectionTypeEnum: Format[Schema.FindDevicesByOwnerRequest.SectionTypeEnum] = JsonEnumFormat[Schema.FindDevicesByOwnerRequest.SectionTypeEnum]
	given fmtFindDevicesByDeviceIdentifierResponse: Format[Schema.FindDevicesByDeviceIdentifierResponse] = Json.format[Schema.FindDevicesByDeviceIdentifierResponse]
	given fmtCustomerListDpcsResponse: Format[Schema.CustomerListDpcsResponse] = Json.format[Schema.CustomerListDpcsResponse]
	given fmtCreateCustomerRequest: Format[Schema.CreateCustomerRequest] = Json.format[Schema.CreateCustomerRequest]
	given fmtCustomerApplyConfigurationRequest: Format[Schema.CustomerApplyConfigurationRequest] = Json.format[Schema.CustomerApplyConfigurationRequest]
	given fmtCompanyTermsStatusEnum: Format[Schema.Company.TermsStatusEnum] = JsonEnumFormat[Schema.Company.TermsStatusEnum]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtPerDeviceStatusInBatchStatusEnum: Format[Schema.PerDeviceStatusInBatch.StatusEnum] = JsonEnumFormat[Schema.PerDeviceStatusInBatch.StatusEnum]
	given fmtDeviceClaimSectionTypeEnum: Format[Schema.DeviceClaim.SectionTypeEnum] = JsonEnumFormat[Schema.DeviceClaim.SectionTypeEnum]
	given fmtDeviceClaimAdditionalServiceEnum: Format[Schema.DeviceClaim.AdditionalServiceEnum] = JsonEnumFormat[Schema.DeviceClaim.AdditionalServiceEnum]
	given fmtFindDevicesByDeviceIdentifierRequest: Format[Schema.FindDevicesByDeviceIdentifierRequest] = Json.format[Schema.FindDevicesByDeviceIdentifierRequest]
}