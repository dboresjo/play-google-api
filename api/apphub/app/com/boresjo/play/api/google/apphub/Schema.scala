package com.boresjo.play.api.google.apphub

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class LookupServiceProjectAttachmentResponse(
	  /** Service project attachment for a project if exists, empty otherwise. */
		serviceProjectAttachment: Option[Schema.ServiceProjectAttachment] = None
	)
	
	object ServiceProjectAttachment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING }
	}
	case class ServiceProjectAttachment(
	  /** Identifier. The resource name of a ServiceProjectAttachment. Format: `"projects/{host-project-id}/locations/global/serviceProjectAttachments/{service-project-id}."` */
		name: Option[String] = None,
	  /** Required. Immutable. Service project name in the format: `"projects/abc"` or `"projects/123"`. As input, project name with either project id or number are accepted. As output, this field will contain project number. */
		serviceProject: Option[String] = None,
	  /** Output only. Create time. */
		createTime: Option[String] = None,
	  /** Output only. A globally unique identifier (in UUID4 format) for the `ServiceProjectAttachment`. */
		uid: Option[String] = None,
	  /** Output only. ServiceProjectAttachment state. */
		state: Option[Schema.ServiceProjectAttachment.StateEnum] = None
	)
	
	case class ListServiceProjectAttachmentsResponse(
	  /** List of service project attachments. */
		serviceProjectAttachments: Option[List[Schema.ServiceProjectAttachment]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class DetachServiceProjectAttachmentRequest(
	
	)
	
	case class DetachServiceProjectAttachmentResponse(
	
	)
	
	case class ListDiscoveredServicesResponse(
	  /** List of Discovered Services. */
		discoveredServices: Option[List[Schema.DiscoveredService]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class DiscoveredService(
	  /** Identifier. The resource name of the discovered service. Format: `"projects/{host-project-id}/locations/{location}/discoveredServices/{uuid}"` */
		name: Option[String] = None,
	  /** Output only. Reference to an underlying networking resource that can comprise a Service. These are immutable. */
		serviceReference: Option[Schema.ServiceReference] = None,
	  /** Output only. Properties of an underlying compute resource that can comprise a Service. These are immutable. */
		serviceProperties: Option[Schema.ServiceProperties] = None
	)
	
	case class ServiceReference(
	  /** Output only. The underlying resource URI. For example, URI of Forwarding Rule, URL Map, and Backend Service. */
		uri: Option[String] = None
	)
	
	case class ServiceProperties(
	  /** Output only. The service project identifier that the underlying cloud resource resides in. */
		gcpProject: Option[String] = None,
	  /** Output only. The location that the underlying resource resides in, for example, us-west1. */
		location: Option[String] = None,
	  /** Output only. The location that the underlying resource resides in if it is zonal, for example, us-west1-a). */
		zone: Option[String] = None
	)
	
	case class LookupDiscoveredServiceResponse(
	  /** Discovered Service if exists, empty otherwise. */
		discoveredService: Option[Schema.DiscoveredService] = None
	)
	
	case class ListServicesResponse(
	  /** List of Services. */
		services: Option[List[Schema.Service]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Service {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, DETACHED }
	}
	case class Service(
	  /** Identifier. The resource name of a Service. Format: `"projects/{host-project-id}/locations/{location}/applications/{application-id}/services/{service-id}"` */
		name: Option[String] = None,
	  /** Optional. User-defined name for the Service. Can have a maximum length of 63 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-defined description of a Service. Can have a maximum length of 2048 characters. */
		description: Option[String] = None,
	  /** Output only. Reference to an underlying networking resource that can comprise a Service. These are immutable. */
		serviceReference: Option[Schema.ServiceReference] = None,
	  /** Output only. Properties of an underlying compute resource that can comprise a Service. These are immutable. */
		serviceProperties: Option[Schema.ServiceProperties] = None,
	  /** Optional. Consumer provided attributes. */
		attributes: Option[Schema.Attributes] = None,
	  /** Required. Immutable. The resource name of the original discovered service. */
		discoveredService: Option[String] = None,
	  /** Output only. Create time. */
		createTime: Option[String] = None,
	  /** Output only. Update time. */
		updateTime: Option[String] = None,
	  /** Output only. A universally unique identifier (UUID) for the `Service` in the UUID4 format. */
		uid: Option[String] = None,
	  /** Output only. Service state. */
		state: Option[Schema.Service.StateEnum] = None
	)
	
	case class Attributes(
	  /** Optional. User-defined criticality information. */
		criticality: Option[Schema.Criticality] = None,
	  /** Optional. User-defined environment information. */
		environment: Option[Schema.Environment] = None,
	  /** Optional. Developer team that owns development and coding. */
		developerOwners: Option[List[Schema.ContactInfo]] = None,
	  /** Optional. Operator team that ensures runtime and operations. */
		operatorOwners: Option[List[Schema.ContactInfo]] = None,
	  /** Optional. Business team that ensures user needs are met and value is delivered */
		businessOwners: Option[List[Schema.ContactInfo]] = None
	)
	
	object Criticality {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, MISSION_CRITICAL, HIGH, MEDIUM, LOW }
	}
	case class Criticality(
	  /** Required. Criticality Type. */
		`type`: Option[Schema.Criticality.TypeEnum] = None
	)
	
	object Environment {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PRODUCTION, STAGING, TEST, DEVELOPMENT }
	}
	case class Environment(
	  /** Required. Environment Type. */
		`type`: Option[Schema.Environment.TypeEnum] = None
	)
	
	case class ContactInfo(
	  /** Optional. Contact's name. Can have a maximum length of 63 characters. */
		displayName: Option[String] = None,
	  /** Required. Email address of the contacts. */
		email: Option[String] = None
	)
	
	case class ListDiscoveredWorkloadsResponse(
	  /** List of Discovered Workloads. */
		discoveredWorkloads: Option[List[Schema.DiscoveredWorkload]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class DiscoveredWorkload(
	  /** Identifier. The resource name of the discovered workload. Format: `"projects/{host-project-id}/locations/{location}/discoveredWorkloads/{uuid}"` */
		name: Option[String] = None,
	  /** Output only. Reference of an underlying compute resource represented by the Workload. These are immutable. */
		workloadReference: Option[Schema.WorkloadReference] = None,
	  /** Output only. Properties of an underlying compute resource represented by the Workload. These are immutable. */
		workloadProperties: Option[Schema.WorkloadProperties] = None
	)
	
	case class WorkloadReference(
	  /** Output only. The underlying compute resource uri. */
		uri: Option[String] = None
	)
	
	case class WorkloadProperties(
	  /** Output only. The service project identifier that the underlying cloud resource resides in. Empty for non-cloud resources. */
		gcpProject: Option[String] = None,
	  /** Output only. The location that the underlying compute resource resides in (for example, us-west1). */
		location: Option[String] = None,
	  /** Output only. The location that the underlying compute resource resides in if it is zonal (for example, us-west1-a). */
		zone: Option[String] = None
	)
	
	case class LookupDiscoveredWorkloadResponse(
	  /** Discovered Workload if exists, empty otherwise. */
		discoveredWorkload: Option[Schema.DiscoveredWorkload] = None
	)
	
	case class ListWorkloadsResponse(
	  /** List of Workloads. */
		workloads: Option[List[Schema.Workload]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Workload {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, DETACHED }
	}
	case class Workload(
	  /** Identifier. The resource name of the Workload. Format: `"projects/{host-project-id}/locations/{location}/applications/{application-id}/workloads/{workload-id}"` */
		name: Option[String] = None,
	  /** Optional. User-defined name for the Workload. Can have a maximum length of 63 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-defined description of a Workload. Can have a maximum length of 2048 characters. */
		description: Option[String] = None,
	  /** Output only. Reference of an underlying compute resource represented by the Workload. These are immutable. */
		workloadReference: Option[Schema.WorkloadReference] = None,
	  /** Output only. Properties of an underlying compute resource represented by the Workload. These are immutable. */
		workloadProperties: Option[Schema.WorkloadProperties] = None,
	  /** Required. Immutable. The resource name of the original discovered workload. */
		discoveredWorkload: Option[String] = None,
	  /** Optional. Consumer provided attributes. */
		attributes: Option[Schema.Attributes] = None,
	  /** Output only. Create time. */
		createTime: Option[String] = None,
	  /** Output only. Update time. */
		updateTime: Option[String] = None,
	  /** Output only. A universally unique identifier (UUID) for the `Workload` in the UUID4 format. */
		uid: Option[String] = None,
	  /** Output only. Workload state. */
		state: Option[Schema.Workload.StateEnum] = None
	)
	
	case class ListApplicationsResponse(
	  /** List of Applications. */
		applications: Option[List[Schema.Application]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Application {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING }
	}
	case class Application(
	  /** Identifier. The resource name of an Application. Format: `"projects/{host-project-id}/locations/{location}/applications/{application-id}"` */
		name: Option[String] = None,
	  /** Optional. User-defined name for the Application. Can have a maximum length of 63 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-defined description of an Application. Can have a maximum length of 2048 characters. */
		description: Option[String] = None,
	  /** Optional. Consumer provided attributes. */
		attributes: Option[Schema.Attributes] = None,
	  /** Output only. Create time. */
		createTime: Option[String] = None,
	  /** Output only. Update time. */
		updateTime: Option[String] = None,
	  /** Required. Immutable. Defines what data can be included into this Application. Limits which Services and Workloads can be registered. */
		scope: Option[Schema.Scope] = None,
	  /** Output only. A universally unique identifier (in UUID4 format) for the `Application`. */
		uid: Option[String] = None,
	  /** Output only. Application state. */
		state: Option[Schema.Application.StateEnum] = None
	)
	
	object Scope {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, REGIONAL, GLOBAL }
	}
	case class Scope(
	  /** Required. Scope Type. */
		`type`: Option[Schema.Scope.TypeEnum] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object ReconciliationOperationMetadata {
		enum ExclusiveActionEnum extends Enum[ExclusiveActionEnum] { case UNKNOWN_REPAIR_ACTION, DELETE, RETRY }
	}
	case class ReconciliationOperationMetadata(
	  /** DEPRECATED. Use exclusive_action instead. */
		deleteResource: Option[Boolean] = None,
	  /** Excluisive action returned by the CLH. */
		exclusiveAction: Option[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class AssetLocation(
	  /** Defines the customer expectation around ZI/ZS for this asset and ZI/ZS state of the region at the time of asset creation. */
		expected: Option[Schema.IsolationExpectations] = None,
	  /** Contains all kinds of physical location definitions for this asset. */
		locationData: Option[List[Schema.LocationData]] = None,
	  /** Defines parents assets if any in order to allow later generation of child_asset_location data via child assets. */
		parentAsset: Option[List[Schema.CloudAsset]] = None,
	  /** Defines extra parameters required for specific asset types. */
		extraParameters: Option[List[Schema.ExtraParameter]] = None,
	  /** Spanner path of the CCFE RMS database. It is only applicable for CCFE tenants that use CCFE RMS for storing resource metadata. */
		ccfeRmsPath: Option[String] = None
	)
	
	object IsolationExpectations {
		enum ZoneIsolationEnum extends Enum[ZoneIsolationEnum] { case ZI_UNSPECIFIED, ZI_UNKNOWN, ZI_NOT_REQUIRED, ZI_PREFERRED, ZI_REQUIRED }
		enum ZoneSeparationEnum extends Enum[ZoneSeparationEnum] { case ZS_UNSPECIFIED, ZS_UNKNOWN, ZS_NOT_REQUIRED, ZS_REQUIRED }
		enum ZsOrgPolicyEnum extends Enum[ZsOrgPolicyEnum] { case ZS_UNSPECIFIED, ZS_UNKNOWN, ZS_NOT_REQUIRED, ZS_REQUIRED }
		enum ZsRegionStateEnum extends Enum[ZsRegionStateEnum] { case ZS_REGION_UNSPECIFIED, ZS_REGION_UNKNOWN, ZS_REGION_NOT_ENABLED, ZS_REGION_ENABLED }
		enum ZiOrgPolicyEnum extends Enum[ZiOrgPolicyEnum] { case ZI_UNSPECIFIED, ZI_UNKNOWN, ZI_NOT_REQUIRED, ZI_PREFERRED, ZI_REQUIRED }
		enum ZiRegionPolicyEnum extends Enum[ZiRegionPolicyEnum] { case ZI_REGION_POLICY_UNSPECIFIED, ZI_REGION_POLICY_UNKNOWN, ZI_REGION_POLICY_NOT_SET, ZI_REGION_POLICY_FAIL_OPEN, ZI_REGION_POLICY_FAIL_CLOSED }
		enum ZiRegionStateEnum extends Enum[ZiRegionStateEnum] { case ZI_REGION_UNSPECIFIED, ZI_REGION_UNKNOWN, ZI_REGION_NOT_ENABLED, ZI_REGION_ENABLED }
	}
	case class IsolationExpectations(
	  /** Deprecated: use zi_org_policy, zi_region_policy and zi_region_state instead for setting ZI expectations as per go/zicy-publish-physical-location. */
		zoneIsolation: Option[Schema.IsolationExpectations.ZoneIsolationEnum] = None,
	  /** Deprecated: use zs_org_policy, and zs_region_stateinstead for setting Zs expectations as per go/zicy-publish-physical-location. */
		zoneSeparation: Option[Schema.IsolationExpectations.ZoneSeparationEnum] = None,
		zsOrgPolicy: Option[Schema.IsolationExpectations.ZsOrgPolicyEnum] = None,
		zsRegionState: Option[Schema.IsolationExpectations.ZsRegionStateEnum] = None,
		ziOrgPolicy: Option[Schema.IsolationExpectations.ZiOrgPolicyEnum] = None,
		ziRegionPolicy: Option[Schema.IsolationExpectations.ZiRegionPolicyEnum] = None,
		ziRegionState: Option[Schema.IsolationExpectations.ZiRegionStateEnum] = None,
	  /** Explicit overrides for ZI and ZS requirements to be used for resources that should be excluded from ZI/ZS verification logic. */
		requirementOverride: Option[Schema.RequirementOverride] = None
	)
	
	object RequirementOverride {
		enum ZiOverrideEnum extends Enum[ZiOverrideEnum] { case ZI_UNSPECIFIED, ZI_UNKNOWN, ZI_NOT_REQUIRED, ZI_PREFERRED, ZI_REQUIRED }
		enum ZsOverrideEnum extends Enum[ZsOverrideEnum] { case ZS_UNSPECIFIED, ZS_UNKNOWN, ZS_NOT_REQUIRED, ZS_REQUIRED }
	}
	case class RequirementOverride(
		ziOverride: Option[Schema.RequirementOverride.ZiOverrideEnum] = None,
		zsOverride: Option[Schema.RequirementOverride.ZsOverrideEnum] = None
	)
	
	case class LocationData(
		directLocation: Option[Schema.DirectLocationAssignment] = None,
		spannerLocation: Option[Schema.SpannerLocation] = None,
		childAssetLocation: Option[Schema.CloudAssetComposition] = None,
		gcpProjectProxy: Option[Schema.TenantProjectProxy] = None,
		blobstoreLocation: Option[Schema.BlobstoreLocation] = None,
		placerLocation: Option[Schema.PlacerLocation] = None
	)
	
	case class DirectLocationAssignment(
		location: Option[List[Schema.LocationAssignment]] = None
	)
	
	object LocationAssignment {
		enum LocationTypeEnum extends Enum[LocationTypeEnum] { case UNSPECIFIED, CLUSTER, POP, CLOUD_ZONE, CLOUD_REGION, MULTI_REGION_GEO, MULTI_REGION_JURISDICTION, GLOBAL, OTHER }
	}
	case class LocationAssignment(
		location: Option[String] = None,
		locationType: Option[Schema.LocationAssignment.LocationTypeEnum] = None
	)
	
	case class SpannerLocation(
	  /** Set of databases used by the resource in format /span// */
		dbName: Option[List[String]] = None,
	  /** Set of backups used by the resource with name in the same format as what is available at http://table/spanner_automon.backup_metadata */
		backupName: Option[List[String]] = None
	)
	
	case class CloudAssetComposition(
		childAsset: Option[List[Schema.CloudAsset]] = None
	)
	
	case class CloudAsset(
		assetType: Option[String] = None,
		assetName: Option[String] = None
	)
	
	case class TenantProjectProxy(
		projectNumbers: Option[List[String]] = None
	)
	
	case class BlobstoreLocation(
		policyId: Option[List[String]] = None
	)
	
	case class PlacerLocation(
	  /** Directory with a config related to it in placer (e.g. "/placer/prod/home/my-root/my-dir") */
		placerConfig: Option[String] = None
	)
	
	case class ExtraParameter(
	  /** Details about zones used by regional compute.googleapis.com/InstanceGroupManager to create instances. */
		regionalMigDistributionPolicy: Option[Schema.RegionalMigDistributionPolicy] = None
	)
	
	case class RegionalMigDistributionPolicy(
	  /** Cloud zones used by regional MIG to create instances. */
		zones: Option[List[Schema.ZoneConfiguration]] = None,
	  /** The shape in which the group converges around distribution of resources. Instance of proto2 enum */
		targetShape: Option[Int] = None
	)
	
	case class ZoneConfiguration(
		zone: Option[String] = None
	)
}
