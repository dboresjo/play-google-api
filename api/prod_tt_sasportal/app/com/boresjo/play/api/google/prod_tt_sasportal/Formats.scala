package com.boresjo.play.api.google.prod_tt_sasportal

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSasPortalDeployment: Format[Schema.SasPortalDeployment] = Json.format[Schema.SasPortalDeployment]
	given fmtSasPortalListDevicesResponse: Format[Schema.SasPortalListDevicesResponse] = Json.format[Schema.SasPortalListDevicesResponse]
	given fmtSasPortalDevice: Format[Schema.SasPortalDevice] = Json.format[Schema.SasPortalDevice]
	given fmtSasPortalSetupSasAnalyticsRequest: Format[Schema.SasPortalSetupSasAnalyticsRequest] = Json.format[Schema.SasPortalSetupSasAnalyticsRequest]
	given fmtSasPortalGenerateSecretResponse: Format[Schema.SasPortalGenerateSecretResponse] = Json.format[Schema.SasPortalGenerateSecretResponse]
	given fmtSasPortalDpaMoveList: Format[Schema.SasPortalDpaMoveList] = Json.format[Schema.SasPortalDpaMoveList]
	given fmtSasPortalFrequencyRange: Format[Schema.SasPortalFrequencyRange] = Json.format[Schema.SasPortalFrequencyRange]
	given fmtSasPortalValidateInstallerRequest: Format[Schema.SasPortalValidateInstallerRequest] = Json.format[Schema.SasPortalValidateInstallerRequest]
	given fmtSasPortalSignDeviceRequest: Format[Schema.SasPortalSignDeviceRequest] = Json.format[Schema.SasPortalSignDeviceRequest]
	given fmtSasPortalChannelWithScore: Format[Schema.SasPortalChannelWithScore] = Json.format[Schema.SasPortalChannelWithScore]
	given fmtSasPortalProvisionDeploymentResponse: Format[Schema.SasPortalProvisionDeploymentResponse] = Json.format[Schema.SasPortalProvisionDeploymentResponse]
	given fmtSasPortalMoveDeploymentRequest: Format[Schema.SasPortalMoveDeploymentRequest] = Json.format[Schema.SasPortalMoveDeploymentRequest]
	given fmtSasPortalAssignment: Format[Schema.SasPortalAssignment] = Json.format[Schema.SasPortalAssignment]
	given fmtSasPortalListCustomersResponse: Format[Schema.SasPortalListCustomersResponse] = Json.format[Schema.SasPortalListCustomersResponse]
	given fmtSasPortalCustomer: Format[Schema.SasPortalCustomer] = Json.format[Schema.SasPortalCustomer]
	given fmtSasPortalGcpProjectDeployment: Format[Schema.SasPortalGcpProjectDeployment] = Json.format[Schema.SasPortalGcpProjectDeployment]
	given fmtSasPortalSetupSasAnalyticsMetadata: Format[Schema.SasPortalSetupSasAnalyticsMetadata] = Json.format[Schema.SasPortalSetupSasAnalyticsMetadata]
	given fmtSasPortalPolicy: Format[Schema.SasPortalPolicy] = Json.format[Schema.SasPortalPolicy]
	given fmtSasPortalProvisionDeploymentRequest: Format[Schema.SasPortalProvisionDeploymentRequest] = Json.format[Schema.SasPortalProvisionDeploymentRequest]
	given fmtSasPortalInstallationParams: Format[Schema.SasPortalInstallationParams] = Json.format[Schema.SasPortalInstallationParams]
	given fmtSasPortalInstallationParamsHeightTypeEnum: Format[Schema.SasPortalInstallationParams.HeightTypeEnum] = JsonEnumFormat[Schema.SasPortalInstallationParams.HeightTypeEnum]
	given fmtSasPortalDeviceMetadata: Format[Schema.SasPortalDeviceMetadata] = Json.format[Schema.SasPortalDeviceMetadata]
	given fmtSasPortalNrqzValidation: Format[Schema.SasPortalNrqzValidation] = Json.format[Schema.SasPortalNrqzValidation]
	given fmtSasPortalOperation: Format[Schema.SasPortalOperation] = Json.format[Schema.SasPortalOperation]
	given fmtSasPortalStatus: Format[Schema.SasPortalStatus] = Json.format[Schema.SasPortalStatus]
	given fmtSasPortalMigrateOrganizationMetadata: Format[Schema.SasPortalMigrateOrganizationMetadata] = Json.format[Schema.SasPortalMigrateOrganizationMetadata]
	given fmtSasPortalMigrateOrganizationMetadataOperationStateEnum: Format[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum] = JsonEnumFormat[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum]
	given fmtSasPortalCreateSignedDeviceRequest: Format[Schema.SasPortalCreateSignedDeviceRequest] = Json.format[Schema.SasPortalCreateSignedDeviceRequest]
	given fmtSasPortalListNodesResponse: Format[Schema.SasPortalListNodesResponse] = Json.format[Schema.SasPortalListNodesResponse]
	given fmtSasPortalNode: Format[Schema.SasPortalNode] = Json.format[Schema.SasPortalNode]
	given fmtSasPortalMoveNodeRequest: Format[Schema.SasPortalMoveNodeRequest] = Json.format[Schema.SasPortalMoveNodeRequest]
	given fmtSasPortalListLegacyOrganizationsResponse: Format[Schema.SasPortalListLegacyOrganizationsResponse] = Json.format[Schema.SasPortalListLegacyOrganizationsResponse]
	given fmtSasPortalOrganization: Format[Schema.SasPortalOrganization] = Json.format[Schema.SasPortalOrganization]
	given fmtSasPortalDeviceModel: Format[Schema.SasPortalDeviceModel] = Json.format[Schema.SasPortalDeviceModel]
	given fmtSasPortalDeviceAirInterface: Format[Schema.SasPortalDeviceAirInterface] = Json.format[Schema.SasPortalDeviceAirInterface]
	given fmtSasPortalDeviceAirInterfaceRadioTechnologyEnum: Format[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum] = JsonEnumFormat[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum]
	given fmtSasPortalMoveDeviceRequest: Format[Schema.SasPortalMoveDeviceRequest] = Json.format[Schema.SasPortalMoveDeviceRequest]
	given fmtSasPortalNrqzValidationStateEnum: Format[Schema.SasPortalNrqzValidation.StateEnum] = JsonEnumFormat[Schema.SasPortalNrqzValidation.StateEnum]
	given fmtSasPortalGetPolicyRequest: Format[Schema.SasPortalGetPolicyRequest] = Json.format[Schema.SasPortalGetPolicyRequest]
	given fmtSasPortalListGcpProjectDeploymentsResponse: Format[Schema.SasPortalListGcpProjectDeploymentsResponse] = Json.format[Schema.SasPortalListGcpProjectDeploymentsResponse]
	given fmtSasPortalDeploymentAssociation: Format[Schema.SasPortalDeploymentAssociation] = Json.format[Schema.SasPortalDeploymentAssociation]
	given fmtSasPortalTestPermissionsResponse: Format[Schema.SasPortalTestPermissionsResponse] = Json.format[Schema.SasPortalTestPermissionsResponse]
	given fmtSasPortalSetupSasAnalyticsResponse: Format[Schema.SasPortalSetupSasAnalyticsResponse] = Json.format[Schema.SasPortalSetupSasAnalyticsResponse]
	given fmtSasPortalUpdateSignedDeviceRequest: Format[Schema.SasPortalUpdateSignedDeviceRequest] = Json.format[Schema.SasPortalUpdateSignedDeviceRequest]
	given fmtSasPortalEmpty: Format[Schema.SasPortalEmpty] = Json.format[Schema.SasPortalEmpty]
	given fmtSasPortalMigrateOrganizationResponse: Format[Schema.SasPortalMigrateOrganizationResponse] = Json.format[Schema.SasPortalMigrateOrganizationResponse]
	given fmtSasPortalSetPolicyRequest: Format[Schema.SasPortalSetPolicyRequest] = Json.format[Schema.SasPortalSetPolicyRequest]
	given fmtSasPortalTestPermissionsRequest: Format[Schema.SasPortalTestPermissionsRequest] = Json.format[Schema.SasPortalTestPermissionsRequest]
	given fmtSasPortalDeviceConfig: Format[Schema.SasPortalDeviceConfig] = Json.format[Schema.SasPortalDeviceConfig]
	given fmtSasPortalDeviceStateEnum: Format[Schema.SasPortalDevice.StateEnum] = JsonEnumFormat[Schema.SasPortalDevice.StateEnum]
	given fmtSasPortalDeviceGrant: Format[Schema.SasPortalDeviceGrant] = Json.format[Schema.SasPortalDeviceGrant]
	given fmtSasPortalMigrateOrganizationRequest: Format[Schema.SasPortalMigrateOrganizationRequest] = Json.format[Schema.SasPortalMigrateOrganizationRequest]
	given fmtSasPortalGenerateSecretRequest: Format[Schema.SasPortalGenerateSecretRequest] = Json.format[Schema.SasPortalGenerateSecretRequest]
	given fmtSasPortalDeviceConfigStateEnum: Format[Schema.SasPortalDeviceConfig.StateEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.StateEnum]
	given fmtSasPortalDeviceConfigCategoryEnum: Format[Schema.SasPortalDeviceConfig.CategoryEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.CategoryEnum]
	given fmtSasPortalDeviceConfigMeasurementCapabilitiesEnum: Format[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum] = JsonEnumFormat[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum]
	given fmtSasPortalValidateInstallerResponse: Format[Schema.SasPortalValidateInstallerResponse] = Json.format[Schema.SasPortalValidateInstallerResponse]
	given fmtSasPortalDeviceGrantStateEnum: Format[Schema.SasPortalDeviceGrant.StateEnum] = JsonEnumFormat[Schema.SasPortalDeviceGrant.StateEnum]
	given fmtSasPortalDeviceGrantChannelTypeEnum: Format[Schema.SasPortalDeviceGrant.ChannelTypeEnum] = JsonEnumFormat[Schema.SasPortalDeviceGrant.ChannelTypeEnum]
	given fmtSasPortalListDeploymentsResponse: Format[Schema.SasPortalListDeploymentsResponse] = Json.format[Schema.SasPortalListDeploymentsResponse]
}
