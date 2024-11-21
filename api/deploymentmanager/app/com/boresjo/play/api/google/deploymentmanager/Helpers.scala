package com.boresjo.play.api.google.deploymentmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaOperation: Conversion[Schema.Operation, Option[Schema.Operation]] = (fun: Schema.Operation) => Option(fun)
		given putSchemaDeploymentUpdate: Conversion[Schema.DeploymentUpdate, Option[Schema.DeploymentUpdate]] = (fun: Schema.DeploymentUpdate) => Option(fun)
		given putSchemaTargetConfiguration: Conversion[Schema.TargetConfiguration, Option[Schema.TargetConfiguration]] = (fun: Schema.TargetConfiguration) => Option(fun)
		given putListSchemaDeploymentLabelEntry: Conversion[List[Schema.DeploymentLabelEntry], Option[List[Schema.DeploymentLabelEntry]]] = (fun: List[Schema.DeploymentLabelEntry]) => Option(fun)
		given putSchemaOperationStatusEnum: Conversion[Schema.Operation.StatusEnum, Option[Schema.Operation.StatusEnum]] = (fun: Schema.Operation.StatusEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaOperationErrorItem: Conversion[Schema.Operation.ErrorItem, Option[Schema.Operation.ErrorItem]] = (fun: Schema.Operation.ErrorItem) => Option(fun)
		given putListSchemaOperationWarningsItem: Conversion[List[Schema.Operation.WarningsItem], Option[List[Schema.Operation.WarningsItem]]] = (fun: List[Schema.Operation.WarningsItem]) => Option(fun)
		given putSchemaSetCommonInstanceMetadataOperationMetadata: Conversion[Schema.SetCommonInstanceMetadataOperationMetadata, Option[Schema.SetCommonInstanceMetadataOperationMetadata]] = (fun: Schema.SetCommonInstanceMetadataOperationMetadata) => Option(fun)
		given putSchemaInstancesBulkInsertOperationMetadata: Conversion[Schema.InstancesBulkInsertOperationMetadata, Option[Schema.InstancesBulkInsertOperationMetadata]] = (fun: Schema.InstancesBulkInsertOperationMetadata) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaQuotaExceededInfoRolloutStatusEnum: Conversion[Schema.QuotaExceededInfo.RolloutStatusEnum, Option[Schema.QuotaExceededInfo.RolloutStatusEnum]] = (fun: Schema.QuotaExceededInfo.RolloutStatusEnum) => Option(fun)
		given putListSchemaHelpLink: Conversion[List[Schema.HelpLink], Option[List[Schema.HelpLink]]] = (fun: List[Schema.HelpLink]) => Option(fun)
		given putMapStringSchemaSetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo: Conversion[Map[String, Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo], Option[Map[String, Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo]]] = (fun: Map[String, Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo]) => Option(fun)
		given putSchemaSetCommonInstanceMetadataOperationMetadataPerLocationOperationInfoStateEnum: Conversion[Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo.StateEnum, Option[Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo.StateEnum]] = (fun: Schema.SetCommonInstanceMetadataOperationMetadataPerLocationOperationInfo.StateEnum) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putMapStringSchemaBulkInsertOperationStatus: Conversion[Map[String, Schema.BulkInsertOperationStatus], Option[Map[String, Schema.BulkInsertOperationStatus]]] = (fun: Map[String, Schema.BulkInsertOperationStatus]) => Option(fun)
		given putSchemaBulkInsertOperationStatusStatusEnum: Conversion[Schema.BulkInsertOperationStatus.StatusEnum, Option[Schema.BulkInsertOperationStatus.StatusEnum]] = (fun: Schema.BulkInsertOperationStatus.StatusEnum) => Option(fun)
		given putListSchemaDeploymentUpdateLabelEntry: Conversion[List[Schema.DeploymentUpdateLabelEntry], Option[List[Schema.DeploymentUpdateLabelEntry]]] = (fun: List[Schema.DeploymentUpdateLabelEntry]) => Option(fun)
		given putSchemaConfigFile: Conversion[Schema.ConfigFile, Option[Schema.ConfigFile]] = (fun: Schema.ConfigFile) => Option(fun)
		given putListSchemaImportFile: Conversion[List[Schema.ImportFile], Option[List[Schema.ImportFile]]] = (fun: List[Schema.ImportFile]) => Option(fun)
		given putListSchemaDeployment: Conversion[List[Schema.Deployment], Option[List[Schema.Deployment]]] = (fun: List[Schema.Deployment]) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaManifest: Conversion[List[Schema.Manifest], Option[List[Schema.Manifest]]] = (fun: List[Schema.Manifest]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putSchemaResourceAccessControl: Conversion[Schema.ResourceAccessControl, Option[Schema.ResourceAccessControl]] = (fun: Schema.ResourceAccessControl) => Option(fun)
		given putSchemaResourceUpdate: Conversion[Schema.ResourceUpdate, Option[Schema.ResourceUpdate]] = (fun: Schema.ResourceUpdate) => Option(fun)
		given putListSchemaResourceWarningsItem: Conversion[List[Schema.Resource.WarningsItem], Option[List[Schema.Resource.WarningsItem]]] = (fun: List[Schema.Resource.WarningsItem]) => Option(fun)
		given putSchemaResourceUpdateErrorItem: Conversion[Schema.ResourceUpdate.ErrorItem, Option[Schema.ResourceUpdate.ErrorItem]] = (fun: Schema.ResourceUpdate.ErrorItem) => Option(fun)
		given putListSchemaResourceUpdateWarningsItem: Conversion[List[Schema.ResourceUpdate.WarningsItem], Option[List[Schema.ResourceUpdate.WarningsItem]]] = (fun: List[Schema.ResourceUpdate.WarningsItem]) => Option(fun)
		given putSchemaResourceUpdateStateEnum: Conversion[Schema.ResourceUpdate.StateEnum, Option[Schema.ResourceUpdate.StateEnum]] = (fun: Schema.ResourceUpdate.StateEnum) => Option(fun)
		given putSchemaResourceUpdateIntentEnum: Conversion[Schema.ResourceUpdate.IntentEnum, Option[Schema.ResourceUpdate.IntentEnum]] = (fun: Schema.ResourceUpdate.IntentEnum) => Option(fun)
		given putListSchemaResource: Conversion[List[Schema.Resource], Option[List[Schema.Resource]]] = (fun: List[Schema.Resource]) => Option(fun)
		given putListSchemaType: Conversion[List[Schema.Type], Option[List[Schema.Type]]] = (fun: List[Schema.Type]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
