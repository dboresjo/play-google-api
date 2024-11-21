package com.boresjo.play.api.google.prod_tt_sasportal

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaSasPortalDevice: Conversion[List[Schema.SasPortalDevice], Option[List[Schema.SasPortalDevice]]] = (fun: List[Schema.SasPortalDevice]) => Option(fun)
		given putSchemaSasPortalFrequencyRange: Conversion[Schema.SasPortalFrequencyRange, Option[Schema.SasPortalFrequencyRange]] = (fun: Schema.SasPortalFrequencyRange) => Option(fun)
		given putSchemaSasPortalDevice: Conversion[Schema.SasPortalDevice, Option[Schema.SasPortalDevice]] = (fun: Schema.SasPortalDevice) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaSasPortalCustomer: Conversion[List[Schema.SasPortalCustomer], Option[List[Schema.SasPortalCustomer]]] = (fun: List[Schema.SasPortalCustomer]) => Option(fun)
		given putSchemaSasPortalDeployment: Conversion[Schema.SasPortalDeployment, Option[Schema.SasPortalDeployment]] = (fun: Schema.SasPortalDeployment) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaSasPortalAssignment: Conversion[List[Schema.SasPortalAssignment], Option[List[Schema.SasPortalAssignment]]] = (fun: List[Schema.SasPortalAssignment]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaSasPortalInstallationParamsHeightTypeEnum: Conversion[Schema.SasPortalInstallationParams.HeightTypeEnum, Option[Schema.SasPortalInstallationParams.HeightTypeEnum]] = (fun: Schema.SasPortalInstallationParams.HeightTypeEnum) => Option(fun)
		given putSchemaSasPortalNrqzValidation: Conversion[Schema.SasPortalNrqzValidation, Option[Schema.SasPortalNrqzValidation]] = (fun: Schema.SasPortalNrqzValidation) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaSasPortalStatus: Conversion[Schema.SasPortalStatus, Option[Schema.SasPortalStatus]] = (fun: Schema.SasPortalStatus) => Option(fun)
		given putSchemaSasPortalMigrateOrganizationMetadataOperationStateEnum: Conversion[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum, Option[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum]] = (fun: Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum) => Option(fun)
		given putListSchemaSasPortalNode: Conversion[List[Schema.SasPortalNode], Option[List[Schema.SasPortalNode]]] = (fun: List[Schema.SasPortalNode]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaSasPortalOrganization: Conversion[List[Schema.SasPortalOrganization], Option[List[Schema.SasPortalOrganization]]] = (fun: List[Schema.SasPortalOrganization]) => Option(fun)
		given putSchemaSasPortalDeviceAirInterfaceRadioTechnologyEnum: Conversion[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum, Option[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum]] = (fun: Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum) => Option(fun)
		given putSchemaSasPortalNrqzValidationStateEnum: Conversion[Schema.SasPortalNrqzValidation.StateEnum, Option[Schema.SasPortalNrqzValidation.StateEnum]] = (fun: Schema.SasPortalNrqzValidation.StateEnum) => Option(fun)
		given putListSchemaSasPortalGcpProjectDeployment: Conversion[List[Schema.SasPortalGcpProjectDeployment], Option[List[Schema.SasPortalGcpProjectDeployment]]] = (fun: List[Schema.SasPortalGcpProjectDeployment]) => Option(fun)
		given putListSchemaSasPortalDeploymentAssociation: Conversion[List[Schema.SasPortalDeploymentAssociation], Option[List[Schema.SasPortalDeploymentAssociation]]] = (fun: List[Schema.SasPortalDeploymentAssociation]) => Option(fun)
		given putSchemaSasPortalPolicy: Conversion[Schema.SasPortalPolicy, Option[Schema.SasPortalPolicy]] = (fun: Schema.SasPortalPolicy) => Option(fun)
		given putSchemaSasPortalDeviceConfig: Conversion[Schema.SasPortalDeviceConfig, Option[Schema.SasPortalDeviceConfig]] = (fun: Schema.SasPortalDeviceConfig) => Option(fun)
		given putSchemaSasPortalDeviceStateEnum: Conversion[Schema.SasPortalDevice.StateEnum, Option[Schema.SasPortalDevice.StateEnum]] = (fun: Schema.SasPortalDevice.StateEnum) => Option(fun)
		given putSchemaSasPortalDeviceMetadata: Conversion[Schema.SasPortalDeviceMetadata, Option[Schema.SasPortalDeviceMetadata]] = (fun: Schema.SasPortalDeviceMetadata) => Option(fun)
		given putListSchemaSasPortalDeviceGrant: Conversion[List[Schema.SasPortalDeviceGrant], Option[List[Schema.SasPortalDeviceGrant]]] = (fun: List[Schema.SasPortalDeviceGrant]) => Option(fun)
		given putListSchemaSasPortalFrequencyRange: Conversion[List[Schema.SasPortalFrequencyRange], Option[List[Schema.SasPortalFrequencyRange]]] = (fun: List[Schema.SasPortalFrequencyRange]) => Option(fun)
		given putListSchemaSasPortalChannelWithScore: Conversion[List[Schema.SasPortalChannelWithScore], Option[List[Schema.SasPortalChannelWithScore]]] = (fun: List[Schema.SasPortalChannelWithScore]) => Option(fun)
		given putSchemaSasPortalInstallationParams: Conversion[Schema.SasPortalInstallationParams, Option[Schema.SasPortalInstallationParams]] = (fun: Schema.SasPortalInstallationParams) => Option(fun)
		given putSchemaSasPortalDeviceModel: Conversion[Schema.SasPortalDeviceModel, Option[Schema.SasPortalDeviceModel]] = (fun: Schema.SasPortalDeviceModel) => Option(fun)
		given putSchemaSasPortalDeviceConfigStateEnum: Conversion[Schema.SasPortalDeviceConfig.StateEnum, Option[Schema.SasPortalDeviceConfig.StateEnum]] = (fun: Schema.SasPortalDeviceConfig.StateEnum) => Option(fun)
		given putSchemaSasPortalDeviceAirInterface: Conversion[Schema.SasPortalDeviceAirInterface, Option[Schema.SasPortalDeviceAirInterface]] = (fun: Schema.SasPortalDeviceAirInterface) => Option(fun)
		given putSchemaSasPortalDeviceConfigCategoryEnum: Conversion[Schema.SasPortalDeviceConfig.CategoryEnum, Option[Schema.SasPortalDeviceConfig.CategoryEnum]] = (fun: Schema.SasPortalDeviceConfig.CategoryEnum) => Option(fun)
		given putListSchemaSasPortalDeviceConfigMeasurementCapabilitiesEnum: Conversion[List[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum], Option[List[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum]]] = (fun: List[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum]) => Option(fun)
		given putListSchemaSasPortalDpaMoveList: Conversion[List[Schema.SasPortalDpaMoveList], Option[List[Schema.SasPortalDpaMoveList]]] = (fun: List[Schema.SasPortalDpaMoveList]) => Option(fun)
		given putSchemaSasPortalDeviceGrantStateEnum: Conversion[Schema.SasPortalDeviceGrant.StateEnum, Option[Schema.SasPortalDeviceGrant.StateEnum]] = (fun: Schema.SasPortalDeviceGrant.StateEnum) => Option(fun)
		given putSchemaSasPortalDeviceGrantChannelTypeEnum: Conversion[Schema.SasPortalDeviceGrant.ChannelTypeEnum, Option[Schema.SasPortalDeviceGrant.ChannelTypeEnum]] = (fun: Schema.SasPortalDeviceGrant.ChannelTypeEnum) => Option(fun)
		given putListSchemaSasPortalDeployment: Conversion[List[Schema.SasPortalDeployment], Option[List[Schema.SasPortalDeployment]]] = (fun: List[Schema.SasPortalDeployment]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
