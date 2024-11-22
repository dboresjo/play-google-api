package com.boresjo.play.api.google.datafusion

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
	
	case class ListAvailableVersionsResponse(
	  /** Represents a list of versions that are supported. */
		availableVersions: Option[List[Schema.Version]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object Version {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TYPE_PREVIEW, TYPE_GENERAL_AVAILABILITY }
	}
	case class Version(
	  /** The version number of the Data Fusion instance, such as '6.0.1.0'. */
		versionNumber: Option[String] = None,
	  /** Whether this is currently the default version for Cloud Data Fusion */
		defaultVersion: Option[Boolean] = None,
	  /** Represents a list of available feature names for a given version. */
		availableFeatures: Option[List[String]] = None,
	  /** Type represents the release availability of the version */
		`type`: Option[Schema.Version.TypeEnum] = None
	)
	
	case class ListInstancesResponse(
	  /** Represents a list of Data Fusion instances. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, BASIC, ENTERPRISE, DEVELOPER }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, FAILED, DELETING, UPGRADING, RESTARTING, UPDATING, AUTO_UPDATING, AUTO_UPGRADING, DISABLED }
		enum DisabledReasonEnum extends Enum[DisabledReasonEnum] { case DISABLED_REASON_UNSPECIFIED, KMS_KEY_ISSUE }
	}
	case class Instance(
	  /** Output only. The name of this instance is in the form of projects/{project}/locations/{location}/instances/{instance}. */
		name: Option[String] = None,
	  /** A description of this instance. */
		description: Option[String] = None,
	  /** Required. Instance type. */
		`type`: Option[Schema.Instance.TypeEnum] = None,
	  /** Option to enable Stackdriver Logging. */
		enableStackdriverLogging: Option[Boolean] = None,
	  /** Option to enable Stackdriver Monitoring. */
		enableStackdriverMonitoring: Option[Boolean] = None,
	  /** Specifies whether the Data Fusion instance should be private. If set to true, all Data Fusion nodes will have private IP addresses and will not be able to access the public internet. */
		privateInstance: Option[Boolean] = None,
	  /** Network configuration options. These are required when a private Data Fusion instance is to be created. */
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** The resource labels for instance to use to annotate any related underlying resources such as Compute Engine VMs. The character '=' is not allowed to be used within the labels. */
		labels: Option[Map[String, String]] = None,
	  /** Map of additional options used to configure the behavior of Data Fusion instance. */
		options: Option[Map[String, String]] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the instance was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of this Data Fusion instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. Additional information about the current state of this Data Fusion instance if available. */
		stateMessage: Option[String] = None,
	  /** Output only. Endpoint on which the Data Fusion UI is accessible. */
		serviceEndpoint: Option[String] = None,
	  /** Name of the zone in which the Data Fusion instance will be created. Only DEVELOPER instances use this field. */
		zone: Option[String] = None,
	  /** Current version of the Data Fusion. Only specifiable in Update. */
		version: Option[String] = None,
	  /** Output only. Deprecated. Use tenant_project_id instead to extract the tenant project ID. */
		serviceAccount: Option[String] = None,
	  /** Display name for an instance. */
		displayName: Option[String] = None,
	  /** Output only. Available versions that the instance can be upgraded to using UpdateInstanceRequest. */
		availableVersion: Option[List[Schema.Version]] = None,
	  /** Output only. Endpoint on which the REST APIs is accessible. */
		apiEndpoint: Option[String] = None,
	  /** Output only. Cloud Storage bucket generated by Data Fusion in the customer project. */
		gcsBucket: Option[String] = None,
	  /** Output only. List of accelerators enabled for this CDF instance. */
		accelerators: Option[List[Schema.Accelerator]] = None,
	  /** Output only. Service agent for the customer project. */
		p4ServiceAccount: Option[String] = None,
	  /** Output only. The name of the tenant project. */
		tenantProjectId: Option[String] = None,
	  /** User-managed service account to set on Dataproc when Cloud Data Fusion creates Dataproc to run data processing pipelines. This allows users to have fine-grained access control on Dataproc's accesses to cloud resources. */
		dataprocServiceAccount: Option[String] = None,
	  /** Option to enable granular role-based access control. */
		enableRbac: Option[Boolean] = None,
	  /** The crypto key configuration. This field is used by the Customer-Managed Encryption Keys (CMEK) feature. */
		cryptoKeyConfig: Option[Schema.CryptoKeyConfig] = None,
	  /** Output only. If the instance state is DISABLED, the reason for disabling the instance. */
		disabledReason: Option[List[Schema.Instance.DisabledReasonEnum]] = None,
	  /** Option to enable and pass metadata for event publishing. */
		eventPublishConfig: Option[Schema.EventPublishConfig] = None,
	  /** Option to enable granular zone separation. */
		enableZoneSeparation: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Endpoint on which the Data Fusion UI is accessible to third-party users */
		workforceIdentityServiceEndpoint: Option[String] = None,
	  /** Optional. Current patch revision of the Data Fusion. */
		patchRevision: Option[String] = None,
	  /** Optional. Option to enable the Dataplex Lineage Integration feature. */
		dataplexDataLineageIntegrationEnabled: Option[Boolean] = None,
	  /** Optional. Configure the maintenance policy for this instance. */
		maintenancePolicy: Option[Schema.MaintenancePolicy] = None
	)
	
	object NetworkConfig {
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case CONNECTION_TYPE_UNSPECIFIED, VPC_PEERING, PRIVATE_SERVICE_CONNECT_INTERFACES }
	}
	case class NetworkConfig(
	  /** Optional. Name of the network in the customer project with which the Tenant Project will be peered for executing pipelines. In case of shared VPC where the network resides in another host project the network should specified in the form of projects/{host-project-id}/global/networks/{network}. This is only required for connectivity type VPC_PEERING. */
		network: Option[String] = None,
	  /** Optional. The IP range in CIDR notation to use for the managed Data Fusion instance nodes. This range must not overlap with any other ranges used in the Data Fusion instance network. This is required only when using connection type VPC_PEERING. Format: a.b.c.d/22 Example: 192.168.0.0/22 */
		ipAllocation: Option[String] = None,
	  /** Optional. Type of connection for establishing private IP connectivity between the Data Fusion customer project VPC and the corresponding tenant project from a predefined list of available connection modes. If this field is unspecified for a private instance, VPC peering is used. */
		connectionType: Option[Schema.NetworkConfig.ConnectionTypeEnum] = None,
	  /** Optional. Configuration for Private Service Connect. This is required only when using connection type PRIVATE_SERVICE_CONNECT_INTERFACES. */
		privateServiceConnectConfig: Option[Schema.PrivateServiceConnectConfig] = None
	)
	
	case class PrivateServiceConnectConfig(
	  /** Required. The reference to the network attachment used to establish private connectivity. It will be of the form projects/{project-id}/regions/{region}/networkAttachments/{network-attachment-id}. */
		networkAttachment: Option[String] = None,
	  /** Optional. Input only. The CIDR block to which the CDF instance can't route traffic to in the consumer project VPC. The size of this block should be at least /25. This range should not overlap with the primary address range of any subnetwork used by the network attachment. This range can be used for other purposes in the consumer VPC as long as there is no requirement for CDF to reach destinations using these addresses. If this value is not provided, the server chooses a non RFC 1918 address range. The format of this field is governed by RFC 4632. Example: 192.168.0.0/25 */
		unreachableCidrBlock: Option[String] = None,
	  /** Output only. The CIDR block to which the CDF instance can't route traffic to in the consumer project VPC. The size of this block is /25. The format of this field is governed by RFC 4632. Example: 240.0.0.0/25 */
		effectiveUnreachableCidrBlock: Option[String] = None
	)
	
	object Accelerator {
		enum AcceleratorTypeEnum extends Enum[AcceleratorTypeEnum] { case ACCELERATOR_TYPE_UNSPECIFIED, CDC, HEALTHCARE, CCAI_INSIGHTS, CLOUDSEARCH }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED, UNKNOWN }
	}
	case class Accelerator(
	  /** Optional. The type of an accelator for a Cloud Data Fusion instance. */
		acceleratorType: Option[Schema.Accelerator.AcceleratorTypeEnum] = None,
	  /** Output only. The state of the accelerator. */
		state: Option[Schema.Accelerator.StateEnum] = None
	)
	
	case class CryptoKeyConfig(
	  /** The name of the key which is used to encrypt/decrypt customer data. For key in Cloud KMS, the key should be in the format of `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		keyReference: Option[String] = None
	)
	
	case class EventPublishConfig(
	  /** Required. Option to enable Event Publishing. */
		enabled: Option[Boolean] = None,
	  /** Required. The resource name of the Pub/Sub topic. Format: projects/{project_id}/topics/{topic_id} */
		topic: Option[String] = None
	)
	
	case class MaintenancePolicy(
	  /** Optional. The maintenance window of the instance. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. The maintenance exclusion window of the instance. */
		maintenanceExclusionWindow: Option[Schema.TimeWindow] = None
	)
	
	case class MaintenanceWindow(
	  /** Required. The recurring time window of the maintenance window. */
		recurringTimeWindow: Option[Schema.RecurringTimeWindow] = None
	)
	
	case class RecurringTimeWindow(
	  /** Required. The window representing the start and end time of recurrences. This field ignores the date components of the provided timestamps. Only the time of day and duration between start and end time are relevant. */
		window: Option[Schema.TimeWindow] = None,
	  /** Required. An RRULE with format [RFC-5545](https://tools.ietf.org/html/rfc5545#section-3.8.5.3) for how this window reccurs. They go on for the span of time between the start and end time. The only supported FREQ value is "WEEKLY". To have something repeat every weekday, use: "FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR". This specifies how frequently the window starts. To have a 9 am - 5 pm UTC-4 window every weekday, use something like: ``` start time = 2019-01-01T09:00:00-0400 end time = 2019-01-01T17:00:00-0400 recurrence = FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR ``` */
		recurrence: Option[String] = None
	)
	
	case class TimeWindow(
	  /** Required. The start time of the time window provided in [RFC 3339](https://www.ietf.org/rfc/rfc3339.txt) format. Example: "2024-01-01T12:04:06-04:00" */
		startTime: Option[String] = None,
	  /** Required. The end time of the time window provided in [RFC 3339](https://www.ietf.org/rfc/rfc3339.txt) format. The end time should take place after the start time. Example: "2024-01-02T12:04:06-06:00" */
		endTime: Option[String] = None
	)
	
	case class RestartInstanceRequest(
	
	)
	
	case class DnsPeering(
	  /** Required. The resource name of the dns peering zone. Format: projects/{project}/locations/{location}/instances/{instance}/dnsPeerings/{dns_peering} */
		name: Option[String] = None,
	  /** Required. The dns name suffix of the zone. */
		domain: Option[String] = None,
	  /** Optional. Optional description of the dns zone. */
		description: Option[String] = None,
	  /** Optional. Optional target project to which dns peering should happen. */
		targetProject: Option[String] = None,
	  /** Optional. Optional target network to which dns peering should happen. */
		targetNetwork: Option[String] = None
	)
	
	case class ListDnsPeeringsResponse(
	  /** List of dns peering. */
		dnsPeerings: Option[List[Schema.DnsPeering]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
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
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation if any. */
		statusDetail: Option[String] = None,
	  /** Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Map to hold any additional status info for the operation If there is an accelerator being enabled/disabled/deleted, this will be populated with accelerator name as key and status as ENABLING, DISABLING or DELETING */
		additionalStatus: Option[Map[String, String]] = None
	)
	
	case class AssetLocation(
	  /** Defines the customer expectation around ZI/ZS for this asset and ZI/ZS state of the region at the time of asset creation. */
		expected: Option[Schema.IsolationExpectations] = None,
	  /** Contains all kinds of physical location definitions for this asset. */
		locationData: Option[List[Schema.LocationData]] = None,
	  /** Defines parents assets if any in order to allow later generation of child_asset_location data via child assets. */
		parentAsset: Option[List[Schema.CloudAsset]] = None,
	  /** Defines extra parameters required for specific asset types. */
		extraParameters: Option[List[Schema.ExtraParameter]] = None
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
		ziRegionState: Option[Schema.IsolationExpectations.ZiRegionStateEnum] = None
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
