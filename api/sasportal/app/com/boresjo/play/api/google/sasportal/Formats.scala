package com.boresjo.play.api.google.sasportal

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSasPortalUpdateSignedDeviceRequest: Format[Schema.SasPortalUpdateSignedDeviceRequest] = Json.format[Schema.SasPortalUpdateSignedDeviceRequest]
	given fmtSasPortalDeviceMetadata: Format[Schema.SasPortalDeviceMetadata] = Json.format[Schema.SasPortalDeviceMetadata]
	given fmtSasPortalNrqzValidation: Format[Schema.SasPortalNrqzValidation] = Json.format[Schema.SasPortalNrqzValidation]
	given fmtSasPortalListGcpProjectDeploymentsResponse: Format[Schema.SasPortalListGcpProjectDeploymentsResponse] = Json.format[Schema.SasPortalListGcpProjectDeploymentsResponse]
	given fmtSasPortalGcpProjectDeployment: Format[Schema.SasPortalGcpProjectDeployment] = Json.format[Schema.SasPortalGcpProjectDeployment]
	given fmtSasPortalDeviceGrant: Format[Schema.SasPortalDeviceGrant] = Json.format[Schema.SasPortalDeviceGrant]
	given fmtSasPortalDeviceGrantChannelTypeEnum: Format[Schema.SasPortalDeviceGrant.ChannelTypeEnum] = JsonEnumFormat[Schema.SasPortalDeviceGrant.ChannelTypeEnum]
	given fmtSasPortalFrequencyRange: Format[Schema.SasPortalFrequencyRange] = Json.format[Schema.SasPortalFrequencyRange]
	given fmtSasPortalDpaMoveList: Format[Schema.SasPortalDpaMoveList] = Json.format[Schema.SasPortalDpaMoveList]
	given fmtSasPortalDeviceGrantStateEnum: Format[Schema.SasPortalDeviceGrant.StateEnum] = JsonEnumFormat[Schema.SasPortalDeviceGrant.StateEnum]
	given fmtSasPortalTestPermissionsRequest: Format[Schema.SasPortalTestPermissionsRequest] = Json.format[Schema.SasPortalTestPermissionsRequest]
	given fmtSasPortalDevice: Format[Schema.SasPortalDevice] = Json.format[Schema.SasPortalDevice]
	given fmtSasPortalDeviceConfig: Format[Schema.SasPortalDeviceConfig] = Json.format[Schema.SasPortalDeviceConfig]
	given fmtSasPortalDeviceStateEnum: Format[Schema.SasPortalDevice.StateEnum] = JsonEnumFormat[Schema.SasPortalDevice.StateEnum]
	given fmtSasPortalChannelWithScore: Format[Schema.SasPortalChannelWithScore] = Json.format[Schema.SasPortalChannelWithScore]
	given fmtSasPortalMigrateOrganizationRequest: Format[Schema.SasPortalMigrateOrganizationRequest] = Json.format[Schema.SasPortalMigrateOrganizationRequest]
	given fmtSasPortalSignDeviceRequest: Format[Schema.SasPortalSignDeviceRequest] = Json.format[Schema.SasPortalSignDeviceRequest]
	given fmtSasPortalGenerateSecretResponse: Format[Schema.SasPortalGenerateSecretResponse] = Json.format[Schema.SasPortalGenerateSecretResponse]
	given fmtSasPortalListCustomersResponse: Format[Schema.SasPortalListCustomersResponse] = Json.format[Schema.SasPortalListCustomersResponse]
	given fmtSasPortalCustomer: Format[Schema.SasPortalCustomer] = Json.format[Schema.SasPortalCustomer]
	given fmtSasPortalInstallationParams: Format[Schema.SasPortalInstallationParams] = Json.format[Schema.SasPortalInstallationParams]
	given fmtSasPortalDeviceConfigStateEnum: Format[Schema.SasPortalDeviceConfig.StateEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.StateEnum]
	given fmtSasPortalDeviceModel: Format[Schema.SasPortalDeviceModel] = Json.format[Schema.SasPortalDeviceModel]
	given fmtSasPortalDeviceConfigCategoryEnum: Format[Schema.SasPortalDeviceConfig.CategoryEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.CategoryEnum]
	given fmtSasPortalDeviceAirInterface: Format[Schema.SasPortalDeviceAirInterface] = Json.format[Schema.SasPortalDeviceAirInterface]
	given fmtSasPortalDeviceConfigMeasurementCapabilitiesEnum: Format[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum]
	given fmtSasPortalSetupSasAnalyticsRequest: Format[Schema.SasPortalSetupSasAnalyticsRequest] = Json.format[Schema.SasPortalSetupSasAnalyticsRequest]
	given fmtSasPortalProvisionDeploymentResponse: Format[Schema.SasPortalProvisionDeploymentResponse] = Json.format[Schema.SasPortalProvisionDeploymentResponse]
	given fmtSasPortalOrganization: Format[Schema.SasPortalOrganization] = Json.format[Schema.SasPortalOrganization]
	given fmtSasPortalListDeploymentsResponse: Format[Schema.SasPortalListDeploymentsResponse] = Json.format[Schema.SasPortalListDeploymentsResponse]
	given fmtSasPortalDeployment: Format[Schema.SasPortalDeployment] = Json.format[Schema.SasPortalDeployment]
	given fmtSasPortalMoveDeploymentRequest: Format[Schema.SasPortalMoveDeploymentRequest] = Json.format[Schema.SasPortalMoveDeploymentRequest]
	given fmtSasPortalDeviceAirInterfaceRadioTechnologyEnum: Format[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum] = JsonEnumFormat[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum]
	given fmtSasPortalOperation: Format[Schema.SasPortalOperation] = Json.format[Schema.SasPortalOperation]
	given fmtSasPortalStatus: Format[Schema.SasPortalStatus] = Json.format[Schema.SasPortalStatus]
	given fmtSasPortalGenerateSecretRequest: Format[Schema.SasPortalGenerateSecretRequest] = Json.format[Schema.SasPortalGenerateSecretRequest]
	given fmtSasPortalMigrateOrganizationResponse: Format[Schema.SasPortalMigrateOrganizationResponse] = Json.format[Schema.SasPortalMigrateOrganizationResponse]
	given fmtSasPortalDeploymentAssociation: Format[Schema.SasPortalDeploymentAssociation] = Json.format[Schema.SasPortalDeploymentAssociation]
	given fmtSasPortalNode: Format[Schema.SasPortalNode] = Json.format[Schema.SasPortalNode]
	given fmtSasPortalProvisionDeploymentRequest: Format[Schema.SasPortalProvisionDeploymentRequest] = Json.format[Schema.SasPortalProvisionDeploymentRequest]
	given fmtSasPortalPolicy: Format[Schema.SasPortalPolicy] = Json.format[Schema.SasPortalPolicy]
	given fmtSasPortalAssignment: Format[Schema.SasPortalAssignment] = Json.format[Schema.SasPortalAssignment]
	given fmtSasPortalGetPolicyRequest: Format[Schema.SasPortalGetPolicyRequest] = Json.format[Schema.SasPortalGetPolicyRequest]
	given fmtSasPortalValidateInstallerResponse: Format[Schema.SasPortalValidateInstallerResponse] = Json.format[Schema.SasPortalValidateInstallerResponse]
	given fmtSasPortalSetupSasAnalyticsResponse: Format[Schema.SasPortalSetupSasAnalyticsResponse] = Json.format[Schema.SasPortalSetupSasAnalyticsResponse]
	given fmtSasPortalListNodesResponse: Format[Schema.SasPortalListNodesResponse] = Json.format[Schema.SasPortalListNodesResponse]
	given fmtSasPortalMoveNodeRequest: Format[Schema.SasPortalMoveNodeRequest] = Json.format[Schema.SasPortalMoveNodeRequest]
	given fmtSasPortalNrqzValidationStateEnum: Format[Schema.SasPortalNrqzValidation.StateEnum] = JsonEnumFormat[Schema.SasPortalNrqzValidation.StateEnum]
	given fmtSasPortalMigrateOrganizationMetadata: Format[Schema.SasPortalMigrateOrganizationMetadata] = Json.format[Schema.SasPortalMigrateOrganizationMetadata]
	given fmtSasPortalMigrateOrganizationMetadataOperationStateEnum: Format[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum] = JsonEnumFormat[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum]
	given fmtSasPortalEmpty: Format[Schema.SasPortalEmpty] = Json.format[Schema.SasPortalEmpty]
	given fmtSasPortalListDevicesResponse: Format[Schema.SasPortalListDevicesResponse] = Json.format[Schema.SasPortalListDevicesResponse]
	given fmtSasPortalSetupSasAnalyticsMetadata: Format[Schema.SasPortalSetupSasAnalyticsMetadata] = Json.format[Schema.SasPortalSetupSasAnalyticsMetadata]
	given fmtSasPortalTestPermissionsResponse: Format[Schema.SasPortalTestPermissionsResponse] = Json.format[Schema.SasPortalTestPermissionsResponse]
	given fmtSasPortalSetPolicyRequest: Format[Schema.SasPortalSetPolicyRequest] = Json.format[Schema.SasPortalSetPolicyRequest]
	given fmtSasPortalCreateSignedDeviceRequest: Format[Schema.SasPortalCreateSignedDeviceRequest] = Json.format[Schema.SasPortalCreateSignedDeviceRequest]
	given fmtSasPortalMoveDeviceRequest: Format[Schema.SasPortalMoveDeviceRequest] = Json.format[Schema.SasPortalMoveDeviceRequest]
	given fmtSasPortalListLegacyOrganizationsResponse: Format[Schema.SasPortalListLegacyOrganizationsResponse] = Json.format[Schema.SasPortalListLegacyOrganizationsResponse]
	given fmtSasPortalInstallationParamsHeightTypeEnum: Format[Schema.SasPortalInstallationParams.HeightTypeEnum] = JsonEnumFormat[Schema.SasPortalInstallationParams.HeightTypeEnum]
	given fmtSasPortalValidateInstallerRequest: Format[Schema.SasPortalValidateInstallerRequest] = Json.format[Schema.SasPortalValidateInstallerRequest]
}
