package com.boresjo.play.api.google.apphub

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
		given putSchemaServiceProjectAttachment: Conversion[Schema.ServiceProjectAttachment, Option[Schema.ServiceProjectAttachment]] = (fun: Schema.ServiceProjectAttachment) => Option(fun)
		given putSchemaServiceProjectAttachmentStateEnum: Conversion[Schema.ServiceProjectAttachment.StateEnum, Option[Schema.ServiceProjectAttachment.StateEnum]] = (fun: Schema.ServiceProjectAttachment.StateEnum) => Option(fun)
		given putListSchemaServiceProjectAttachment: Conversion[List[Schema.ServiceProjectAttachment], Option[List[Schema.ServiceProjectAttachment]]] = (fun: List[Schema.ServiceProjectAttachment]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaDiscoveredService: Conversion[List[Schema.DiscoveredService], Option[List[Schema.DiscoveredService]]] = (fun: List[Schema.DiscoveredService]) => Option(fun)
		given putSchemaServiceReference: Conversion[Schema.ServiceReference, Option[Schema.ServiceReference]] = (fun: Schema.ServiceReference) => Option(fun)
		given putSchemaServiceProperties: Conversion[Schema.ServiceProperties, Option[Schema.ServiceProperties]] = (fun: Schema.ServiceProperties) => Option(fun)
		given putSchemaDiscoveredService: Conversion[Schema.DiscoveredService, Option[Schema.DiscoveredService]] = (fun: Schema.DiscoveredService) => Option(fun)
		given putListSchemaService: Conversion[List[Schema.Service], Option[List[Schema.Service]]] = (fun: List[Schema.Service]) => Option(fun)
		given putSchemaAttributes: Conversion[Schema.Attributes, Option[Schema.Attributes]] = (fun: Schema.Attributes) => Option(fun)
		given putSchemaServiceStateEnum: Conversion[Schema.Service.StateEnum, Option[Schema.Service.StateEnum]] = (fun: Schema.Service.StateEnum) => Option(fun)
		given putSchemaCriticality: Conversion[Schema.Criticality, Option[Schema.Criticality]] = (fun: Schema.Criticality) => Option(fun)
		given putSchemaEnvironment: Conversion[Schema.Environment, Option[Schema.Environment]] = (fun: Schema.Environment) => Option(fun)
		given putListSchemaContactInfo: Conversion[List[Schema.ContactInfo], Option[List[Schema.ContactInfo]]] = (fun: List[Schema.ContactInfo]) => Option(fun)
		given putSchemaCriticalityTypeEnum: Conversion[Schema.Criticality.TypeEnum, Option[Schema.Criticality.TypeEnum]] = (fun: Schema.Criticality.TypeEnum) => Option(fun)
		given putSchemaEnvironmentTypeEnum: Conversion[Schema.Environment.TypeEnum, Option[Schema.Environment.TypeEnum]] = (fun: Schema.Environment.TypeEnum) => Option(fun)
		given putListSchemaDiscoveredWorkload: Conversion[List[Schema.DiscoveredWorkload], Option[List[Schema.DiscoveredWorkload]]] = (fun: List[Schema.DiscoveredWorkload]) => Option(fun)
		given putSchemaWorkloadReference: Conversion[Schema.WorkloadReference, Option[Schema.WorkloadReference]] = (fun: Schema.WorkloadReference) => Option(fun)
		given putSchemaWorkloadProperties: Conversion[Schema.WorkloadProperties, Option[Schema.WorkloadProperties]] = (fun: Schema.WorkloadProperties) => Option(fun)
		given putSchemaDiscoveredWorkload: Conversion[Schema.DiscoveredWorkload, Option[Schema.DiscoveredWorkload]] = (fun: Schema.DiscoveredWorkload) => Option(fun)
		given putListSchemaWorkload: Conversion[List[Schema.Workload], Option[List[Schema.Workload]]] = (fun: List[Schema.Workload]) => Option(fun)
		given putSchemaWorkloadStateEnum: Conversion[Schema.Workload.StateEnum, Option[Schema.Workload.StateEnum]] = (fun: Schema.Workload.StateEnum) => Option(fun)
		given putListSchemaApplication: Conversion[List[Schema.Application], Option[List[Schema.Application]]] = (fun: List[Schema.Application]) => Option(fun)
		given putSchemaScope: Conversion[Schema.Scope, Option[Schema.Scope]] = (fun: Schema.Scope) => Option(fun)
		given putSchemaApplicationStateEnum: Conversion[Schema.Application.StateEnum, Option[Schema.Application.StateEnum]] = (fun: Schema.Application.StateEnum) => Option(fun)
		given putSchemaScopeTypeEnum: Conversion[Schema.Scope.TypeEnum, Option[Schema.Scope.TypeEnum]] = (fun: Schema.Scope.TypeEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaReconciliationOperationMetadataExclusiveActionEnum: Conversion[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum, Option[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum]] = (fun: Schema.ReconciliationOperationMetadata.ExclusiveActionEnum) => Option(fun)
		given putSchemaIsolationExpectations: Conversion[Schema.IsolationExpectations, Option[Schema.IsolationExpectations]] = (fun: Schema.IsolationExpectations) => Option(fun)
		given putListSchemaLocationData: Conversion[List[Schema.LocationData], Option[List[Schema.LocationData]]] = (fun: List[Schema.LocationData]) => Option(fun)
		given putListSchemaCloudAsset: Conversion[List[Schema.CloudAsset], Option[List[Schema.CloudAsset]]] = (fun: List[Schema.CloudAsset]) => Option(fun)
		given putListSchemaExtraParameter: Conversion[List[Schema.ExtraParameter], Option[List[Schema.ExtraParameter]]] = (fun: List[Schema.ExtraParameter]) => Option(fun)
		given putSchemaIsolationExpectationsZoneIsolationEnum: Conversion[Schema.IsolationExpectations.ZoneIsolationEnum, Option[Schema.IsolationExpectations.ZoneIsolationEnum]] = (fun: Schema.IsolationExpectations.ZoneIsolationEnum) => Option(fun)
		given putSchemaIsolationExpectationsZoneSeparationEnum: Conversion[Schema.IsolationExpectations.ZoneSeparationEnum, Option[Schema.IsolationExpectations.ZoneSeparationEnum]] = (fun: Schema.IsolationExpectations.ZoneSeparationEnum) => Option(fun)
		given putSchemaIsolationExpectationsZsOrgPolicyEnum: Conversion[Schema.IsolationExpectations.ZsOrgPolicyEnum, Option[Schema.IsolationExpectations.ZsOrgPolicyEnum]] = (fun: Schema.IsolationExpectations.ZsOrgPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZsRegionStateEnum: Conversion[Schema.IsolationExpectations.ZsRegionStateEnum, Option[Schema.IsolationExpectations.ZsRegionStateEnum]] = (fun: Schema.IsolationExpectations.ZsRegionStateEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiOrgPolicyEnum: Conversion[Schema.IsolationExpectations.ZiOrgPolicyEnum, Option[Schema.IsolationExpectations.ZiOrgPolicyEnum]] = (fun: Schema.IsolationExpectations.ZiOrgPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiRegionPolicyEnum: Conversion[Schema.IsolationExpectations.ZiRegionPolicyEnum, Option[Schema.IsolationExpectations.ZiRegionPolicyEnum]] = (fun: Schema.IsolationExpectations.ZiRegionPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiRegionStateEnum: Conversion[Schema.IsolationExpectations.ZiRegionStateEnum, Option[Schema.IsolationExpectations.ZiRegionStateEnum]] = (fun: Schema.IsolationExpectations.ZiRegionStateEnum) => Option(fun)
		given putSchemaRequirementOverride: Conversion[Schema.RequirementOverride, Option[Schema.RequirementOverride]] = (fun: Schema.RequirementOverride) => Option(fun)
		given putSchemaRequirementOverrideZiOverrideEnum: Conversion[Schema.RequirementOverride.ZiOverrideEnum, Option[Schema.RequirementOverride.ZiOverrideEnum]] = (fun: Schema.RequirementOverride.ZiOverrideEnum) => Option(fun)
		given putSchemaRequirementOverrideZsOverrideEnum: Conversion[Schema.RequirementOverride.ZsOverrideEnum, Option[Schema.RequirementOverride.ZsOverrideEnum]] = (fun: Schema.RequirementOverride.ZsOverrideEnum) => Option(fun)
		given putSchemaDirectLocationAssignment: Conversion[Schema.DirectLocationAssignment, Option[Schema.DirectLocationAssignment]] = (fun: Schema.DirectLocationAssignment) => Option(fun)
		given putSchemaSpannerLocation: Conversion[Schema.SpannerLocation, Option[Schema.SpannerLocation]] = (fun: Schema.SpannerLocation) => Option(fun)
		given putSchemaCloudAssetComposition: Conversion[Schema.CloudAssetComposition, Option[Schema.CloudAssetComposition]] = (fun: Schema.CloudAssetComposition) => Option(fun)
		given putSchemaTenantProjectProxy: Conversion[Schema.TenantProjectProxy, Option[Schema.TenantProjectProxy]] = (fun: Schema.TenantProjectProxy) => Option(fun)
		given putSchemaBlobstoreLocation: Conversion[Schema.BlobstoreLocation, Option[Schema.BlobstoreLocation]] = (fun: Schema.BlobstoreLocation) => Option(fun)
		given putSchemaPlacerLocation: Conversion[Schema.PlacerLocation, Option[Schema.PlacerLocation]] = (fun: Schema.PlacerLocation) => Option(fun)
		given putListSchemaLocationAssignment: Conversion[List[Schema.LocationAssignment], Option[List[Schema.LocationAssignment]]] = (fun: List[Schema.LocationAssignment]) => Option(fun)
		given putSchemaLocationAssignmentLocationTypeEnum: Conversion[Schema.LocationAssignment.LocationTypeEnum, Option[Schema.LocationAssignment.LocationTypeEnum]] = (fun: Schema.LocationAssignment.LocationTypeEnum) => Option(fun)
		given putSchemaRegionalMigDistributionPolicy: Conversion[Schema.RegionalMigDistributionPolicy, Option[Schema.RegionalMigDistributionPolicy]] = (fun: Schema.RegionalMigDistributionPolicy) => Option(fun)
		given putListSchemaZoneConfiguration: Conversion[List[Schema.ZoneConfiguration], Option[List[Schema.ZoneConfiguration]]] = (fun: List[Schema.ZoneConfiguration]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
