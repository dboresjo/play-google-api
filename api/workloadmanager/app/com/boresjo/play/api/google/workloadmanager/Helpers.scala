package com.boresjo.play.api.google.workloadmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaEvaluation: Conversion[List[Schema.Evaluation], Option[List[Schema.Evaluation]]] = (fun: List[Schema.Evaluation]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaResourceFilter: Conversion[Schema.ResourceFilter, Option[Schema.ResourceFilter]] = (fun: Schema.ResourceFilter) => Option(fun)
		given putSchemaResourceStatus: Conversion[Schema.ResourceStatus, Option[Schema.ResourceStatus]] = (fun: Schema.ResourceStatus) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaBigQueryDestination: Conversion[Schema.BigQueryDestination, Option[Schema.BigQueryDestination]] = (fun: Schema.BigQueryDestination) => Option(fun)
		given putSchemaGceInstanceFilter: Conversion[Schema.GceInstanceFilter, Option[Schema.GceInstanceFilter]] = (fun: Schema.GceInstanceFilter) => Option(fun)
		given putSchemaResourceStatusStateEnum: Conversion[Schema.ResourceStatus.StateEnum, Option[Schema.ResourceStatus.StateEnum]] = (fun: Schema.ResourceStatus.StateEnum) => Option(fun)
		given putListSchemaExecution: Conversion[List[Schema.Execution], Option[List[Schema.Execution]]] = (fun: List[Schema.Execution]) => Option(fun)
		given putSchemaExecutionStateEnum: Conversion[Schema.Execution.StateEnum, Option[Schema.Execution.StateEnum]] = (fun: Schema.Execution.StateEnum) => Option(fun)
		given putSchemaExecutionRunTypeEnum: Conversion[Schema.Execution.RunTypeEnum, Option[Schema.Execution.RunTypeEnum]] = (fun: Schema.Execution.RunTypeEnum) => Option(fun)
		given putListSchemaRuleExecutionResult: Conversion[List[Schema.RuleExecutionResult], Option[List[Schema.RuleExecutionResult]]] = (fun: List[Schema.RuleExecutionResult]) => Option(fun)
		given putListSchemaExternalDataSources: Conversion[List[Schema.ExternalDataSources], Option[List[Schema.ExternalDataSources]]] = (fun: List[Schema.ExternalDataSources]) => Option(fun)
		given putListSchemaNotice: Conversion[List[Schema.Notice], Option[List[Schema.Notice]]] = (fun: List[Schema.Notice]) => Option(fun)
		given putSchemaRuleExecutionResultStateEnum: Conversion[Schema.RuleExecutionResult.StateEnum, Option[Schema.RuleExecutionResult.StateEnum]] = (fun: Schema.RuleExecutionResult.StateEnum) => Option(fun)
		given putSchemaExternalDataSourcesTypeEnum: Conversion[Schema.ExternalDataSources.TypeEnum, Option[Schema.ExternalDataSources.TypeEnum]] = (fun: Schema.ExternalDataSources.TypeEnum) => Option(fun)
		given putSchemaExecution: Conversion[Schema.Execution, Option[Schema.Execution]] = (fun: Schema.Execution) => Option(fun)
		given putListSchemaExecutionResult: Conversion[List[Schema.ExecutionResult], Option[List[Schema.ExecutionResult]]] = (fun: List[Schema.ExecutionResult]) => Option(fun)
		given putSchemaResource: Conversion[Schema.Resource, Option[Schema.Resource]] = (fun: Schema.Resource) => Option(fun)
		given putSchemaViolationDetails: Conversion[Schema.ViolationDetails, Option[Schema.ViolationDetails]] = (fun: Schema.ViolationDetails) => Option(fun)
		given putListSchemaCommand: Conversion[List[Schema.Command], Option[List[Schema.Command]]] = (fun: List[Schema.Command]) => Option(fun)
		given putSchemaExecutionResultTypeEnum: Conversion[Schema.ExecutionResult.TypeEnum, Option[Schema.ExecutionResult.TypeEnum]] = (fun: Schema.ExecutionResult.TypeEnum) => Option(fun)
		given putSchemaAgentCommand: Conversion[Schema.AgentCommand, Option[Schema.AgentCommand]] = (fun: Schema.AgentCommand) => Option(fun)
		given putSchemaShellCommand: Conversion[Schema.ShellCommand, Option[Schema.ShellCommand]] = (fun: Schema.ShellCommand) => Option(fun)
		given putListSchemaRule: Conversion[List[Schema.Rule], Option[List[Schema.Rule]]] = (fun: List[Schema.Rule]) => Option(fun)
		given putListSchemaScannedResource: Conversion[List[Schema.ScannedResource], Option[List[Schema.ScannedResource]]] = (fun: List[Schema.ScannedResource]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaInsight: Conversion[Schema.Insight, Option[Schema.Insight]] = (fun: Schema.Insight) => Option(fun)
		given putSchemaSapValidation: Conversion[Schema.SapValidation, Option[Schema.SapValidation]] = (fun: Schema.SapValidation) => Option(fun)
		given putSchemaSapDiscovery: Conversion[Schema.SapDiscovery, Option[Schema.SapDiscovery]] = (fun: Schema.SapDiscovery) => Option(fun)
		given putSchemaSqlserverValidation: Conversion[Schema.SqlserverValidation, Option[Schema.SqlserverValidation]] = (fun: Schema.SqlserverValidation) => Option(fun)
		given putListSchemaSapValidationValidationDetail: Conversion[List[Schema.SapValidationValidationDetail], Option[List[Schema.SapValidationValidationDetail]]] = (fun: List[Schema.SapValidationValidationDetail]) => Option(fun)
		given putSchemaSapValidationValidationDetailSapValidationTypeEnum: Conversion[Schema.SapValidationValidationDetail.SapValidationTypeEnum, Option[Schema.SapValidationValidationDetail.SapValidationTypeEnum]] = (fun: Schema.SapValidationValidationDetail.SapValidationTypeEnum) => Option(fun)
		given putSchemaSapDiscoveryMetadata: Conversion[Schema.SapDiscoveryMetadata, Option[Schema.SapDiscoveryMetadata]] = (fun: Schema.SapDiscoveryMetadata) => Option(fun)
		given putSchemaSapDiscoveryComponent: Conversion[Schema.SapDiscoveryComponent, Option[Schema.SapDiscoveryComponent]] = (fun: Schema.SapDiscoveryComponent) => Option(fun)
		given putSchemaSapDiscoveryWorkloadProperties: Conversion[Schema.SapDiscoveryWorkloadProperties, Option[Schema.SapDiscoveryWorkloadProperties]] = (fun: Schema.SapDiscoveryWorkloadProperties) => Option(fun)
		given putListSchemaSapDiscoveryResource: Conversion[List[Schema.SapDiscoveryResource], Option[List[Schema.SapDiscoveryResource]]] = (fun: List[Schema.SapDiscoveryResource]) => Option(fun)
		given putSchemaSapDiscoveryComponentApplicationProperties: Conversion[Schema.SapDiscoveryComponentApplicationProperties, Option[Schema.SapDiscoveryComponentApplicationProperties]] = (fun: Schema.SapDiscoveryComponentApplicationProperties) => Option(fun)
		given putSchemaSapDiscoveryComponentDatabaseProperties: Conversion[Schema.SapDiscoveryComponentDatabaseProperties, Option[Schema.SapDiscoveryComponentDatabaseProperties]] = (fun: Schema.SapDiscoveryComponentDatabaseProperties) => Option(fun)
		given putSchemaSapDiscoveryComponentTopologyTypeEnum: Conversion[Schema.SapDiscoveryComponent.TopologyTypeEnum, Option[Schema.SapDiscoveryComponent.TopologyTypeEnum]] = (fun: Schema.SapDiscoveryComponent.TopologyTypeEnum) => Option(fun)
		given putListSchemaSapDiscoveryComponent: Conversion[List[Schema.SapDiscoveryComponent], Option[List[Schema.SapDiscoveryComponent]]] = (fun: List[Schema.SapDiscoveryComponent]) => Option(fun)
		given putSchemaSapDiscoveryResourceResourceTypeEnum: Conversion[Schema.SapDiscoveryResource.ResourceTypeEnum, Option[Schema.SapDiscoveryResource.ResourceTypeEnum]] = (fun: Schema.SapDiscoveryResource.ResourceTypeEnum) => Option(fun)
		given putSchemaSapDiscoveryResourceResourceKindEnum: Conversion[Schema.SapDiscoveryResource.ResourceKindEnum, Option[Schema.SapDiscoveryResource.ResourceKindEnum]] = (fun: Schema.SapDiscoveryResource.ResourceKindEnum) => Option(fun)
		given putSchemaSapDiscoveryResourceInstanceProperties: Conversion[Schema.SapDiscoveryResourceInstanceProperties, Option[Schema.SapDiscoveryResourceInstanceProperties]] = (fun: Schema.SapDiscoveryResourceInstanceProperties) => Option(fun)
		given putSchemaSapDiscoveryResourceInstancePropertiesInstanceRoleEnum: Conversion[Schema.SapDiscoveryResourceInstanceProperties.InstanceRoleEnum, Option[Schema.SapDiscoveryResourceInstanceProperties.InstanceRoleEnum]] = (fun: Schema.SapDiscoveryResourceInstanceProperties.InstanceRoleEnum) => Option(fun)
		given putListSchemaSapDiscoveryResourceInstancePropertiesAppInstance: Conversion[List[Schema.SapDiscoveryResourceInstancePropertiesAppInstance], Option[List[Schema.SapDiscoveryResourceInstancePropertiesAppInstance]]] = (fun: List[Schema.SapDiscoveryResourceInstancePropertiesAppInstance]) => Option(fun)
		given putSchemaSapDiscoveryComponentApplicationPropertiesApplicationTypeEnum: Conversion[Schema.SapDiscoveryComponentApplicationProperties.ApplicationTypeEnum, Option[Schema.SapDiscoveryComponentApplicationProperties.ApplicationTypeEnum]] = (fun: Schema.SapDiscoveryComponentApplicationProperties.ApplicationTypeEnum) => Option(fun)
		given putSchemaSapDiscoveryComponentDatabasePropertiesDatabaseTypeEnum: Conversion[Schema.SapDiscoveryComponentDatabaseProperties.DatabaseTypeEnum, Option[Schema.SapDiscoveryComponentDatabaseProperties.DatabaseTypeEnum]] = (fun: Schema.SapDiscoveryComponentDatabaseProperties.DatabaseTypeEnum) => Option(fun)
		given putListSchemaSapDiscoveryWorkloadPropertiesProductVersion: Conversion[List[Schema.SapDiscoveryWorkloadPropertiesProductVersion], Option[List[Schema.SapDiscoveryWorkloadPropertiesProductVersion]]] = (fun: List[Schema.SapDiscoveryWorkloadPropertiesProductVersion]) => Option(fun)
		given putListSchemaSapDiscoveryWorkloadPropertiesSoftwareComponentProperties: Conversion[List[Schema.SapDiscoveryWorkloadPropertiesSoftwareComponentProperties], Option[List[Schema.SapDiscoveryWorkloadPropertiesSoftwareComponentProperties]]] = (fun: List[Schema.SapDiscoveryWorkloadPropertiesSoftwareComponentProperties]) => Option(fun)
		given putListSchemaSqlserverValidationValidationDetail: Conversion[List[Schema.SqlserverValidationValidationDetail], Option[List[Schema.SqlserverValidationValidationDetail]]] = (fun: List[Schema.SqlserverValidationValidationDetail]) => Option(fun)
		given putSchemaSqlserverValidationValidationDetailTypeEnum: Conversion[Schema.SqlserverValidationValidationDetail.TypeEnum, Option[Schema.SqlserverValidationValidationDetail.TypeEnum]] = (fun: Schema.SqlserverValidationValidationDetail.TypeEnum) => Option(fun)
		given putListSchemaSqlserverValidationDetails: Conversion[List[Schema.SqlserverValidationDetails], Option[List[Schema.SqlserverValidationDetails]]] = (fun: List[Schema.SqlserverValidationDetails]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
