package com.boresjo.play.api.google.tagmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaAccount: Conversion[List[Schema.Account], Option[List[Schema.Account]]] = (fun: List[Schema.Account]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAccountFeatures: Conversion[Schema.AccountFeatures, Option[Schema.AccountFeatures]] = (fun: Schema.AccountFeatures) => Option(fun)
		given putSchemaAccountAccess: Conversion[Schema.AccountAccess, Option[Schema.AccountAccess]] = (fun: Schema.AccountAccess) => Option(fun)
		given putListSchemaContainerAccess: Conversion[List[Schema.ContainerAccess], Option[List[Schema.ContainerAccess]]] = (fun: List[Schema.ContainerAccess]) => Option(fun)
		given putSchemaAccountAccessPermissionEnum: Conversion[Schema.AccountAccess.PermissionEnum, Option[Schema.AccountAccess.PermissionEnum]] = (fun: Schema.AccountAccess.PermissionEnum) => Option(fun)
		given putSchemaContainerAccessPermissionEnum: Conversion[Schema.ContainerAccess.PermissionEnum, Option[Schema.ContainerAccess.PermissionEnum]] = (fun: Schema.ContainerAccess.PermissionEnum) => Option(fun)
		given putListSchemaUserPermission: Conversion[List[Schema.UserPermission], Option[List[Schema.UserPermission]]] = (fun: List[Schema.UserPermission]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaContainerFeatures: Conversion[Schema.ContainerFeatures, Option[Schema.ContainerFeatures]] = (fun: Schema.ContainerFeatures) => Option(fun)
		given putListSchemaContainerUsageContextEnum: Conversion[List[Schema.Container.UsageContextEnum], Option[List[Schema.Container.UsageContextEnum]]] = (fun: List[Schema.Container.UsageContextEnum]) => Option(fun)
		given putListSchemaContainer: Conversion[List[Schema.Container], Option[List[Schema.Container]]] = (fun: List[Schema.Container]) => Option(fun)
		given putListSchemaDestination: Conversion[List[Schema.Destination], Option[List[Schema.Destination]]] = (fun: List[Schema.Destination]) => Option(fun)
		given putListSchemaWorkspace: Conversion[List[Schema.Workspace], Option[List[Schema.Workspace]]] = (fun: List[Schema.Workspace]) => Option(fun)
		given putSchemaSyncStatus: Conversion[Schema.SyncStatus, Option[Schema.SyncStatus]] = (fun: Schema.SyncStatus) => Option(fun)
		given putListSchemaMergeConflict: Conversion[List[Schema.MergeConflict], Option[List[Schema.MergeConflict]]] = (fun: List[Schema.MergeConflict]) => Option(fun)
		given putSchemaEntity: Conversion[Schema.Entity, Option[Schema.Entity]] = (fun: Schema.Entity) => Option(fun)
		given putSchemaTag: Conversion[Schema.Tag, Option[Schema.Tag]] = (fun: Schema.Tag) => Option(fun)
		given putSchemaTrigger: Conversion[Schema.Trigger, Option[Schema.Trigger]] = (fun: Schema.Trigger) => Option(fun)
		given putSchemaVariable: Conversion[Schema.Variable, Option[Schema.Variable]] = (fun: Schema.Variable) => Option(fun)
		given putSchemaFolder: Conversion[Schema.Folder, Option[Schema.Folder]] = (fun: Schema.Folder) => Option(fun)
		given putSchemaClient: Conversion[Schema.Client, Option[Schema.Client]] = (fun: Schema.Client) => Option(fun)
		given putSchemaTransformation: Conversion[Schema.Transformation, Option[Schema.Transformation]] = (fun: Schema.Transformation) => Option(fun)
		given putSchemaZone: Conversion[Schema.Zone, Option[Schema.Zone]] = (fun: Schema.Zone) => Option(fun)
		given putSchemaCustomTemplate: Conversion[Schema.CustomTemplate, Option[Schema.CustomTemplate]] = (fun: Schema.CustomTemplate) => Option(fun)
		given putSchemaBuiltInVariable: Conversion[Schema.BuiltInVariable, Option[Schema.BuiltInVariable]] = (fun: Schema.BuiltInVariable) => Option(fun)
		given putSchemaGtagConfig: Conversion[Schema.GtagConfig, Option[Schema.GtagConfig]] = (fun: Schema.GtagConfig) => Option(fun)
		given putSchemaEntityChangeStatusEnum: Conversion[Schema.Entity.ChangeStatusEnum, Option[Schema.Entity.ChangeStatusEnum]] = (fun: Schema.Entity.ChangeStatusEnum) => Option(fun)
		given putSchemaParameter: Conversion[Schema.Parameter, Option[Schema.Parameter]] = (fun: Schema.Parameter) => Option(fun)
		given putListSchemaParameter: Conversion[List[Schema.Parameter], Option[List[Schema.Parameter]]] = (fun: List[Schema.Parameter]) => Option(fun)
		given putListSchemaSetupTag: Conversion[List[Schema.SetupTag], Option[List[Schema.SetupTag]]] = (fun: List[Schema.SetupTag]) => Option(fun)
		given putListSchemaTeardownTag: Conversion[List[Schema.TeardownTag], Option[List[Schema.TeardownTag]]] = (fun: List[Schema.TeardownTag]) => Option(fun)
		given putSchemaTagTagFiringOptionEnum: Conversion[Schema.Tag.TagFiringOptionEnum, Option[Schema.Tag.TagFiringOptionEnum]] = (fun: Schema.Tag.TagFiringOptionEnum) => Option(fun)
		given putSchemaTagConsentSetting: Conversion[Schema.TagConsentSetting, Option[Schema.TagConsentSetting]] = (fun: Schema.TagConsentSetting) => Option(fun)
		given putSchemaParameterTypeEnum: Conversion[Schema.Parameter.TypeEnum, Option[Schema.Parameter.TypeEnum]] = (fun: Schema.Parameter.TypeEnum) => Option(fun)
		given putSchemaTagConsentSettingConsentStatusEnum: Conversion[Schema.TagConsentSetting.ConsentStatusEnum, Option[Schema.TagConsentSetting.ConsentStatusEnum]] = (fun: Schema.TagConsentSetting.ConsentStatusEnum) => Option(fun)
		given putSchemaTriggerTypeEnum: Conversion[Schema.Trigger.TypeEnum, Option[Schema.Trigger.TypeEnum]] = (fun: Schema.Trigger.TypeEnum) => Option(fun)
		given putListSchemaCondition: Conversion[List[Schema.Condition], Option[List[Schema.Condition]]] = (fun: List[Schema.Condition]) => Option(fun)
		given putSchemaConditionTypeEnum: Conversion[Schema.Condition.TypeEnum, Option[Schema.Condition.TypeEnum]] = (fun: Schema.Condition.TypeEnum) => Option(fun)
		given putSchemaVariableFormatValue: Conversion[Schema.VariableFormatValue, Option[Schema.VariableFormatValue]] = (fun: Schema.VariableFormatValue) => Option(fun)
		given putSchemaVariableFormatValueCaseConversionTypeEnum: Conversion[Schema.VariableFormatValue.CaseConversionTypeEnum, Option[Schema.VariableFormatValue.CaseConversionTypeEnum]] = (fun: Schema.VariableFormatValue.CaseConversionTypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaZoneChildContainer: Conversion[List[Schema.ZoneChildContainer], Option[List[Schema.ZoneChildContainer]]] = (fun: List[Schema.ZoneChildContainer]) => Option(fun)
		given putSchemaZoneBoundary: Conversion[Schema.ZoneBoundary, Option[Schema.ZoneBoundary]] = (fun: Schema.ZoneBoundary) => Option(fun)
		given putSchemaZoneTypeRestriction: Conversion[Schema.ZoneTypeRestriction, Option[Schema.ZoneTypeRestriction]] = (fun: Schema.ZoneTypeRestriction) => Option(fun)
		given putSchemaGalleryReference: Conversion[Schema.GalleryReference, Option[Schema.GalleryReference]] = (fun: Schema.GalleryReference) => Option(fun)
		given putSchemaBuiltInVariableTypeEnum: Conversion[Schema.BuiltInVariable.TypeEnum, Option[Schema.BuiltInVariable.TypeEnum]] = (fun: Schema.BuiltInVariable.TypeEnum) => Option(fun)
		given putListSchemaEntity: Conversion[List[Schema.Entity], Option[List[Schema.Entity]]] = (fun: List[Schema.Entity]) => Option(fun)
		given putSchemaContainerVersion: Conversion[Schema.ContainerVersion, Option[Schema.ContainerVersion]] = (fun: Schema.ContainerVersion) => Option(fun)
		given putSchemaContainer: Conversion[Schema.Container, Option[Schema.Container]] = (fun: Schema.Container) => Option(fun)
		given putListSchemaTag: Conversion[List[Schema.Tag], Option[List[Schema.Tag]]] = (fun: List[Schema.Tag]) => Option(fun)
		given putListSchemaTrigger: Conversion[List[Schema.Trigger], Option[List[Schema.Trigger]]] = (fun: List[Schema.Trigger]) => Option(fun)
		given putListSchemaVariable: Conversion[List[Schema.Variable], Option[List[Schema.Variable]]] = (fun: List[Schema.Variable]) => Option(fun)
		given putListSchemaFolder: Conversion[List[Schema.Folder], Option[List[Schema.Folder]]] = (fun: List[Schema.Folder]) => Option(fun)
		given putListSchemaBuiltInVariable: Conversion[List[Schema.BuiltInVariable], Option[List[Schema.BuiltInVariable]]] = (fun: List[Schema.BuiltInVariable]) => Option(fun)
		given putListSchemaZone: Conversion[List[Schema.Zone], Option[List[Schema.Zone]]] = (fun: List[Schema.Zone]) => Option(fun)
		given putListSchemaCustomTemplate: Conversion[List[Schema.CustomTemplate], Option[List[Schema.CustomTemplate]]] = (fun: List[Schema.CustomTemplate]) => Option(fun)
		given putListSchemaClient: Conversion[List[Schema.Client], Option[List[Schema.Client]]] = (fun: List[Schema.Client]) => Option(fun)
		given putListSchemaGtagConfig: Conversion[List[Schema.GtagConfig], Option[List[Schema.GtagConfig]]] = (fun: List[Schema.GtagConfig]) => Option(fun)
		given putListSchemaTransformation: Conversion[List[Schema.Transformation], Option[List[Schema.Transformation]]] = (fun: List[Schema.Transformation]) => Option(fun)
		given putListSchemaContainerVersionHeader: Conversion[List[Schema.ContainerVersionHeader], Option[List[Schema.ContainerVersionHeader]]] = (fun: List[Schema.ContainerVersionHeader]) => Option(fun)
		given putSchemaEnvironmentTypeEnum: Conversion[Schema.Environment.TypeEnum, Option[Schema.Environment.TypeEnum]] = (fun: Schema.Environment.TypeEnum) => Option(fun)
		given putListSchemaEnvironment: Conversion[List[Schema.Environment], Option[List[Schema.Environment]]] = (fun: List[Schema.Environment]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
