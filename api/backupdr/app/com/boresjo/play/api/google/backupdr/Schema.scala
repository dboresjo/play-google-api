package com.boresjo.play.api.google.backupdr

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListManagementServersResponse(
	  /** The list of ManagementServer instances in the project for the specified location. If the '{location}' value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return management servers in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		managementServers: Option[List[Schema.ManagementServer]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ManagementServer {
		enum TypeEnum extends Enum[TypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, BACKUP_RESTORE }
		enum StateEnum extends Enum[StateEnum] { case INSTANCE_STATE_UNSPECIFIED, CREATING, READY, UPDATING, DELETING, REPAIRING, MAINTENANCE, ERROR }
	}
	case class ManagementServer(
	  /** Output only. Identifier. The resource name. */
		name: Option[String] = None,
	  /** Optional. The description of the ManagementServer instance (2048 characters or less). */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Labels currently defined: 1. migrate_from_go= If set to true, the MS is created in migration ready mode. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the instance was updated. */
		updateTime: Option[String] = None,
	  /** Optional. The type of the ManagementServer resource. */
		`type`: Option[Schema.ManagementServer.TypeEnum] = None,
	  /** Output only. The hostname or ip address of the exposed AGM endpoints, used by clients to connect to AGM/RD graphical user interface and APIs. */
		managementUri: Option[Schema.ManagementURI] = None,
	  /** Output only. The hostnames of the exposed AGM endpoints for both types of user i.e. 1p and 3p, used to connect AGM/RM UI. */
		workforceIdentityBasedManagementUri: Option[Schema.WorkforceIdentityBasedManagementURI] = None,
	  /** Output only. The ManagementServer state. */
		state: Option[Schema.ManagementServer.StateEnum] = None,
	  /** Optional. VPC networks to which the ManagementServer instance is connected. For this version, only a single network is supported. This field is optional if MS is created without PSA */
		networks: Option[List[Schema.NetworkConfig]] = None,
	  /** Optional. Server specified ETag for the ManagementServer resource to prevent simultaneous updates from overwiting each other. */
		etag: Option[String] = None,
	  /** Output only. The OAuth 2.0 client id is required to make API calls to the BackupDR instance API of this ManagementServer. This is the value that should be provided in the 'aud' field of the OIDC ID Token (see openid specification https://openid.net/specs/openid-connect-core-1_0.html#IDToken). */
		oauth2ClientId: Option[String] = None,
	  /** Output only. The OAuth client IDs for both types of user i.e. 1p and 3p. */
		workforceIdentityBasedOauth2ClientId: Option[Schema.WorkforceIdentityBasedOAuth2ClientID] = None,
	  /** Output only. The hostname or ip address of the exposed AGM endpoints, used by BAs to connect to BA proxy. */
		baProxyUri: Option[List[String]] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	case class ManagementURI(
	  /** Output only. The ManagementServer AGM/RD WebUI URL. */
		webUi: Option[String] = None,
	  /** Output only. The ManagementServer AGM/RD API URL. */
		api: Option[String] = None
	)
	
	case class WorkforceIdentityBasedManagementURI(
	  /** Output only. First party Management URI for Google Identities. */
		firstPartyManagementUri: Option[String] = None,
	  /** Output only. Third party Management URI for External Identity Providers. */
		thirdPartyManagementUri: Option[String] = None
	)
	
	object NetworkConfig {
		enum PeeringModeEnum extends Enum[PeeringModeEnum] { case PEERING_MODE_UNSPECIFIED, PRIVATE_SERVICE_ACCESS }
	}
	case class NetworkConfig(
	  /** Optional. The resource name of the Google Compute Engine VPC network to which the ManagementServer instance is connected. */
		network: Option[String] = None,
	  /** Optional. The network connect mode of the ManagementServer instance. For this version, only PRIVATE_SERVICE_ACCESS is supported. */
		peeringMode: Option[Schema.NetworkConfig.PeeringModeEnum] = None
	)
	
	case class WorkforceIdentityBasedOAuth2ClientID(
	  /** Output only. First party OAuth Client ID for Google Identities. */
		firstPartyOauth2ClientId: Option[String] = None,
	  /** Output only. Third party OAuth Client ID for External Identity Providers. */
		thirdPartyOauth2ClientId: Option[String] = None
	)
	
	object BackupVault {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ERROR }
		enum AccessRestrictionEnum extends Enum[AccessRestrictionEnum] { case ACCESS_RESTRICTION_UNSPECIFIED, WITHIN_PROJECT, WITHIN_ORGANIZATION, UNRESTRICTED, WITHIN_ORG_BUT_UNRESTRICTED_FOR_BA }
	}
	case class BackupVault(
	  /** Output only. Identifier. Name of the backup vault to create. It must have the format`"projects/{project}/locations/{location}/backupVaults/{backupvault}"`. `{backupvault}` cannot be changed after creation. It must be between 3-63 characters long and must be unique within the project and location. */
		name: Option[String] = None,
	  /** Optional. The description of the BackupVault instance (2048 characters or less). */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. No labels currently defined: */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the instance was updated. */
		updateTime: Option[String] = None,
	  /** Required. The default and minimum enforced retention for each backup within the backup vault. The enforced retention for each backup can be extended. */
		backupMinimumEnforcedRetentionDuration: Option[String] = None,
	  /** Output only. Set to true when there are no backups nested under this resource. */
		deletable: Option[Boolean] = None,
	  /** Optional. Server specified ETag for the backup vault resource to prevent simultaneous updates from overwiting each other. */
		etag: Option[String] = None,
	  /** Output only. The BackupVault resource instance state. */
		state: Option[Schema.BackupVault.StateEnum] = None,
	  /** Optional. Time after which the BackupVault resource is locked. */
		effectiveTime: Option[String] = None,
	  /** Output only. The number of backups in this backup vault. */
		backupCount: Option[String] = None,
	  /** Output only. Service account used by the BackupVault Service for this BackupVault. The user should grant this account permissions in their workload project to enable the service to run backups and restores there. */
		serviceAccount: Option[String] = None,
	  /** Output only. Total size of the storage used by all backup resources. */
		totalStoredBytes: Option[String] = None,
	  /** Output only. Output only Immutable after resource creation until resource deletion. */
		uid: Option[String] = None,
	  /** Optional. User annotations. See https://google.aip.dev/128#annotations Stores small amounts of arbitrary data. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Note: This field is added for future use case and will not be supported in the current release. Optional. Access restriction for the backup vault. Default value is WITHIN_ORGANIZATION if not provided during creation. */
		accessRestriction: Option[Schema.BackupVault.AccessRestrictionEnum] = None
	)
	
	case class ListBackupVaultsResponse(
	  /** The list of BackupVault instances in the project for the specified location. If the '{location}' value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return backup vaults in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		backupVaults: Option[List[Schema.BackupVault]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class FetchUsableBackupVaultsResponse(
	  /** The list of BackupVault instances in the project for the specified location. If the '{location}' value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return backup vaults in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		backupVaults: Option[List[Schema.BackupVault]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListDataSourcesResponse(
	  /** The list of DataSource instances in the project for the specified location. If the '{location}' value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return data sources in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		dataSources: Option[List[Schema.DataSource]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object DataSource {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ERROR }
		enum ConfigStateEnum extends Enum[ConfigStateEnum] { case BACKUP_CONFIG_STATE_UNSPECIFIED, ACTIVE, PASSIVE }
	}
	case class DataSource(
	  /** Output only. Identifier. Name of the datasource to create. It must have the format`"projects/{project}/locations/{location}/backupVaults/{backupvault}/dataSources/{datasource}"`. `{datasource}` cannot be changed after creation. It must be between 3-63 characters long and must be unique within the backup vault. */
		name: Option[String] = None,
	  /** Output only. The DataSource resource instance state. */
		state: Option[Schema.DataSource.StateEnum] = None,
	  /** Optional. Resource labels to represent user provided metadata. No labels currently defined: */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the instance was updated. */
		updateTime: Option[String] = None,
	  /** Number of backups in the data source. */
		backupCount: Option[String] = None,
	  /** Server specified ETag for the ManagementServer resource to prevent simultaneous updates from overwiting each other. */
		etag: Option[String] = None,
	  /** The number of bytes (metadata and data) stored in this datasource. */
		totalStoredBytes: Option[String] = None,
	  /** Output only. The backup configuration state. */
		configState: Option[Schema.DataSource.ConfigStateEnum] = None,
	  /** Output only. Details of how the resource is configured for backup. */
		backupConfigInfo: Option[Schema.BackupConfigInfo] = None,
	  /** The backed up resource is a Google Cloud resource. The word 'DataSource' was included in the names to indicate that this is the representation of the Google Cloud resource used within the DataSource object. */
		dataSourceGcpResource: Option[Schema.DataSourceGcpResource] = None,
	  /** The backed up resource is a backup appliance application. */
		dataSourceBackupApplianceApplication: Option[Schema.DataSourceBackupApplianceApplication] = None
	)
	
	object BackupConfigInfo {
		enum LastBackupStateEnum extends Enum[LastBackupStateEnum] { case LAST_BACKUP_STATE_UNSPECIFIED, FIRST_BACKUP_PENDING, SUCCEEDED, FAILED, PERMISSION_DENIED }
	}
	case class BackupConfigInfo(
	  /** Output only. The status of the last backup to this BackupVault */
		lastBackupState: Option[Schema.BackupConfigInfo.LastBackupStateEnum] = None,
	  /** Output only. If the last backup were successful, this field has the consistency date. */
		lastSuccessfulBackupConsistencyTime: Option[String] = None,
	  /** Output only. If the last backup failed, this field has the error message. */
		lastBackupError: Option[Schema.Status] = None,
	  /** Configuration for a Google Cloud resource. */
		gcpBackupConfig: Option[Schema.GcpBackupConfig] = None,
	  /** Configuration for an application backed up by a Backup Appliance. */
		backupApplianceBackupConfig: Option[Schema.BackupApplianceBackupConfig] = None
	)
	
	case class GcpBackupConfig(
	  /** The name of the backup plan. */
		backupPlan: Option[String] = None,
	  /** The description of the backup plan. */
		backupPlanDescription: Option[String] = None,
	  /** The name of the backup plan association. */
		backupPlanAssociation: Option[String] = None,
	  /** The names of the backup plan rules which point to this backupvault */
		backupPlanRules: Option[List[String]] = None
	)
	
	case class BackupApplianceBackupConfig(
	  /** The name of the backup appliance. */
		backupApplianceName: Option[String] = None,
	  /** The ID of the backup appliance. */
		backupApplianceId: Option[String] = None,
	  /** The ID of the SLA of this application. */
		slaId: Option[String] = None,
	  /** The name of the application. */
		applicationName: Option[String] = None,
	  /** The name of the host where the application is running. */
		hostName: Option[String] = None,
	  /** The name of the SLT associated with the application. */
		sltName: Option[String] = None,
	  /** The name of the SLP associated with the application. */
		slpName: Option[String] = None
	)
	
	case class DataSourceGcpResource(
	  /** Output only. Full resource pathname URL of the source Google Cloud resource. */
		gcpResourcename: Option[String] = None,
	  /** Location of the resource: //"global"/"unspecified". */
		location: Option[String] = None,
	  /** The type of the Google Cloud resource. Use the Unified Resource Type, eg. compute.googleapis.com/Instance. */
		`type`: Option[String] = None,
	  /** ComputeInstanceDataSourceProperties has a subset of Compute Instance properties that are useful at the Datasource level. */
		computeInstanceDatasourceProperties: Option[Schema.ComputeInstanceDataSourceProperties] = None
	)
	
	case class ComputeInstanceDataSourceProperties(
	  /** Name of the compute instance backed up by the datasource. */
		name: Option[String] = None,
	  /** The description of the Compute Engine instance. */
		description: Option[String] = None,
	  /** The machine type of the instance. */
		machineType: Option[String] = None,
	  /** The total number of disks attached to the Instance. */
		totalDiskCount: Option[String] = None,
	  /** The sum of all the disk sizes. */
		totalDiskSizeGb: Option[String] = None
	)
	
	case class DataSourceBackupApplianceApplication(
	  /** The name of the Application as known to the Backup Appliance. */
		applicationName: Option[String] = None,
	  /** Appliance name. */
		backupAppliance: Option[String] = None,
	  /** Appliance Id of the Backup Appliance. */
		applianceId: Option[String] = None,
	  /** The type of the application. e.g. VMBackup */
		`type`: Option[String] = None,
	  /** The appid field of the application within the Backup Appliance. */
		applicationId: Option[String] = None,
	  /** Hostname of the host where the application is running. */
		hostname: Option[String] = None,
	  /** Hostid of the application host. */
		hostId: Option[String] = None
	)
	
	case class RemoveDataSourceRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object SetInternalStatusRequest {
		enum BackupConfigStateEnum extends Enum[BackupConfigStateEnum] { case BACKUP_CONFIG_STATE_UNSPECIFIED, ACTIVE, PASSIVE }
	}
	case class SetInternalStatusRequest(
	  /** Required. The value required for this method to work. This field must be the 32-byte SHA256 hash of the DataSourceID. The DataSourceID used here is only the final piece of the fully qualified resource path for this DataSource (i.e. the part after '.../dataSources/'). This field exists to make this method difficult to call since it is intended for use only by Backup Appliances. */
		value: Option[String] = None,
	  /** Required. Output only. The new BackupConfigState to set for the DataSource. */
		backupConfigState: Option[Schema.SetInternalStatusRequest.BackupConfigStateEnum] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class InitiateBackupRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Required. Resource ID of the Backup resource. */
		backupId: Option[String] = None
	)
	
	case class InitiateBackupResponse(
	  /** The name of the backup that was created. */
		backup: Option[String] = None,
	  /** The generation id of the new backup. */
		newBackupGenerationId: Option[Int] = None,
	  /** The generation id of the base backup. It is needed for the incremental backups. */
		baseBackupGenerationId: Option[Int] = None
	)
	
	case class AbandonBackupRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class FinalizeBackupRequest(
	  /** This will be assigned to the description field of the newly created Backup. */
		description: Option[String] = None,
	  /** The point in time when this backup was captured from the source. This will be assigned to the consistency_time field of the newly created Backup. */
		consistencyTime: Option[String] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Required. Resource ID of the Backup resource to be finalized. This must be the same backup_id that was used in the InitiateBackupRequest. */
		backupId: Option[String] = None,
	  /** The earliest timestamp of data available in this Backup. This will set on the newly created Backup. */
		recoveryRangeStartTime: Option[String] = None,
	  /** The latest timestamp of data available in this Backup. This will be set on the newly created Backup. */
		recoveryRangeEndTime: Option[String] = None,
	  /** The ExpireTime on the backup will be set to FinalizeTime plus this duration. If the resulting ExpireTime is less than EnforcedRetentionEndTime, then ExpireTime is set to EnforcedRetentionEndTime. */
		retentionDuration: Option[String] = None
	)
	
	case class FetchAccessTokenRequest(
	  /** Required. The generation of the backup to update. */
		generationId: Option[Int] = None
	)
	
	case class FetchAccessTokenResponse(
	  /** The location in bucket that can be used for reading. */
		readLocation: Option[String] = None,
	  /** The location in bucket that can be used for writing. */
		writeLocation: Option[String] = None,
	  /** The downscoped token that was created. */
		token: Option[String] = None,
	  /** The token is valid until this time. */
		expireTime: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** The list of Backup instances in the project for the specified location. If the '{location}' value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return data sources in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		backups: Option[List[Schema.Backup]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ERROR }
		enum BackupTypeEnum extends Enum[BackupTypeEnum] { case BACKUP_TYPE_UNSPECIFIED, SCHEDULED, ON_DEMAND }
	}
	case class Backup(
	  /** Output only. Identifier. Name of the backup to create. It must have the format`"projects//locations//backupVaults//dataSources/{datasource}/backups/{backup}"`. `{backup}` cannot be changed after creation. It must be between 3-63 characters long and must be unique within the datasource. */
		name: Option[String] = None,
	  /** Output only. The description of the Backup instance (2048 characters or less). */
		description: Option[String] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the instance was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. No labels currently defined. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The backup can not be deleted before this time. */
		enforcedRetentionEndTime: Option[String] = None,
	  /** Optional. When this backup is automatically expired. */
		expireTime: Option[String] = None,
	  /** Output only. The point in time when this backup was captured from the source. */
		consistencyTime: Option[String] = None,
	  /** Optional. Server specified ETag to prevent updates from overwriting each other. */
		etag: Option[String] = None,
	  /** Output only. The Backup resource instance state. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. The list of BackupLocks taken by the service to prevent the deletion of the backup. */
		serviceLocks: Option[List[Schema.BackupLock]] = None,
	  /** Optional. The list of BackupLocks taken by the accessor Backup Appliance. */
		backupApplianceLocks: Option[List[Schema.BackupLock]] = None,
	  /** Output only. Compute Engine specific backup properties. */
		computeInstanceBackupProperties: Option[Schema.ComputeInstanceBackupProperties] = None,
	  /** Output only. Backup Appliance specific backup properties. */
		backupApplianceBackupProperties: Option[Schema.BackupApplianceBackupProperties] = None,
	  /** Output only. Type of the backup, unspecified, scheduled or ondemand. */
		backupType: Option[Schema.Backup.BackupTypeEnum] = None,
	  /** Output only. Configuration for a Google Cloud resource. */
		gcpBackupPlanInfo: Option[Schema.GCPBackupPlanInfo] = None,
	  /** Output only. source resource size in bytes at the time of the backup. */
		resourceSizeBytes: Option[String] = None
	)
	
	case class BackupLock(
	  /** Required. The time after which this lock is not considered valid and will no longer protect the Backup from deletion. */
		lockUntilTime: Option[String] = None,
	  /** If the client is a backup and recovery appliance, this contains metadata about why the lock exists. */
		backupApplianceLockInfo: Option[Schema.BackupApplianceLockInfo] = None,
	  /** Output only. Contains metadata about the lock exist for Google Cloud native backups. */
		serviceLockInfo: Option[Schema.ServiceLockInfo] = None
	)
	
	case class BackupApplianceLockInfo(
	  /** Required. The ID of the backup/recovery appliance that created this lock. */
		backupApplianceId: Option[String] = None,
	  /** Required. The name of the backup/recovery appliance that created this lock. */
		backupApplianceName: Option[String] = None,
	  /** Required. The reason for the lock: e.g. MOUNT/RESTORE/BACKUP/etc. The value of this string is only meaningful to the client and it is not interpreted by the BackupVault service. */
		lockReason: Option[String] = None,
	  /** The job name on the backup/recovery appliance that created this lock. */
		jobName: Option[String] = None,
	  /** The image name that depends on this Backup. */
		backupImage: Option[String] = None,
	  /** The SLA on the backup/recovery appliance that owns the lock. */
		slaId: Option[String] = None
	)
	
	case class ServiceLockInfo(
	  /** Output only. The name of the operation that created this lock. The lock will automatically be released when the operation completes. */
		operation: Option[String] = None
	)
	
	object ComputeInstanceBackupProperties {
		enum KeyRevocationActionTypeEnum extends Enum[KeyRevocationActionTypeEnum] { case KEY_REVOCATION_ACTION_TYPE_UNSPECIFIED, NONE, STOP }
	}
	case class ComputeInstanceBackupProperties(
	  /** An optional text description for the instances that are created from these properties. */
		description: Option[String] = None,
	  /** A list of tags to apply to the instances that are created from these properties. The tags identify valid sources or targets for network firewalls. The setTags method can modify this list of tags. Each tag within the list must comply with RFC1035 (https://www.ietf.org/rfc/rfc1035.txt). */
		tags: Option[Schema.Tags] = None,
	  /** The machine type to use for instances that are created from these properties. */
		machineType: Option[String] = None,
	  /** Enables instances created based on these properties to send packets with source IP addresses other than their own and receive packets with destination IP addresses other than their own. If these instances will be used as an IP gateway or it will be set as the next-hop in a Route resource, specify `true`. If unsure, leave this set to `false`. See the https://cloud.google.com/vpc/docs/using-routes#canipforward documentation for more information. */
		canIpForward: Option[Boolean] = None,
	  /** An array of network access configurations for this interface. */
		networkInterface: Option[List[Schema.NetworkInterface]] = None,
	  /** An array of disks that are associated with the instances that are created from these properties. */
		disk: Option[List[Schema.AttachedDisk]] = None,
	  /** The metadata key/value pairs to assign to instances that are created from these properties. These pairs can consist of custom metadata or predefined keys. See https://cloud.google.com/compute/docs/metadata/overview for more information. */
		metadata: Option[Schema.Metadata] = None,
	  /** A list of service accounts with specified scopes. Access tokens for these service accounts are available to the instances that are created from these properties. Use metadata queries to obtain the access tokens for these instances. */
		serviceAccount: Option[List[Schema.ServiceAccount]] = None,
	  /** Specifies the scheduling options for the instances that are created from these properties. */
		scheduling: Option[Schema.Scheduling] = None,
	  /** A list of guest accelerator cards' type and count to use for instances created from these properties. */
		guestAccelerator: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Minimum cpu/platform to be used by instances. The instance may be scheduled on the specified or newer cpu/platform. Applicable values are the friendly names of CPU platforms, such as `minCpuPlatform: Intel Haswell` or `minCpuPlatform: Intel Sandy Bridge`. For more information, read https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform. */
		minCpuPlatform: Option[String] = None,
	  /** KeyRevocationActionType of the instance. Supported options are "STOP" and "NONE". The default value is "NONE" if it is not specified. */
		keyRevocationActionType: Option[Schema.ComputeInstanceBackupProperties.KeyRevocationActionTypeEnum] = None,
	  /** The source instance used to create this backup. This can be a partial or full URL to the resource. For example, the following are valid values: -https://www.googleapis.com/compute/v1/projects/project/zones/zone/instances/instance -projects/project/zones/zone/instances/instance */
		sourceInstance: Option[String] = None,
	  /** Labels to apply to instances that are created from these properties. */
		labels: Option[Map[String, String]] = None
	)
	
	case class Tags(
	  /** Optional. An array of tags. Each tag must be 1-63 characters long, and comply with RFC1035. */
		items: Option[List[String]] = None
	)
	
	object NetworkInterface {
		enum StackTypeEnum extends Enum[StackTypeEnum] { case STACK_TYPE_UNSPECIFIED, IPV4_ONLY, IPV4_IPV6 }
		enum Ipv6AccessTypeEnum extends Enum[Ipv6AccessTypeEnum] { case UNSPECIFIED_IPV6_ACCESS_TYPE, INTERNAL, EXTERNAL }
		enum NicTypeEnum extends Enum[NicTypeEnum] { case NIC_TYPE_UNSPECIFIED, VIRTIO_NET, GVNIC }
	}
	case class NetworkInterface(
	  /** Optional. URL of the VPC network resource for this instance. */
		network: Option[String] = None,
	  /** Optional. The URL of the Subnetwork resource for this instance. */
		subnetwork: Option[String] = None,
	  /** Optional. An IPv4 internal IP address to assign to the instance for this network interface. If not specified by the user, an unused internal IP is assigned by the system. */
		networkIP: Option[String] = None,
	  /** Optional. An IPv6 internal network address for this network interface. To use a static internal IP address, it must be unused and in the same region as the instance's zone. If not specified, Google Cloud will automatically assign an internal IPv6 address from the instance's subnetwork. */
		ipv6Address: Option[String] = None,
	  /** Optional. The prefix length of the primary internal IPv6 range. */
		internalIpv6PrefixLength: Option[Int] = None,
	  /** Output only. [Output Only] The name of the network interface, which is generated by the server. */
		name: Option[String] = None,
	  /** Optional. An array of configurations for this interface. Currently, only one access config,ONE_TO_ONE_NAT is supported. If there are no accessConfigs specified, then this instance will have no external internet access. */
		accessConfigs: Option[List[Schema.AccessConfig]] = None,
	  /** Optional. An array of IPv6 access configurations for this interface. Currently, only one IPv6 access config, DIRECT_IPV6, is supported. If there is no ipv6AccessConfig specified, then this instance will have no external IPv6 Internet access. */
		ipv6AccessConfigs: Option[List[Schema.AccessConfig]] = None,
	  /** Optional. An array of alias IP ranges for this network interface. You can only specify this field for network interfaces in VPC networks. */
		aliasIpRanges: Option[List[Schema.AliasIpRange]] = None,
	  /** The stack type for this network interface. */
		stackType: Option[Schema.NetworkInterface.StackTypeEnum] = None,
	  /** Optional. [Output Only] One of EXTERNAL, INTERNAL to indicate whether the IP can be accessed from the Internet. This field is always inherited from its subnetwork. */
		ipv6AccessType: Option[Schema.NetworkInterface.Ipv6AccessTypeEnum] = None,
	  /** Optional. The networking queue count that's specified by users for the network interface. Both Rx and Tx queues will be set to this number. It'll be empty if not specified by the users. */
		queueCount: Option[Int] = None,
	  /** Optional. The type of vNIC to be used on this interface. This may be gVNIC or VirtioNet. */
		nicType: Option[Schema.NetworkInterface.NicTypeEnum] = None,
	  /** Optional. The URL of the network attachment that this interface should connect to in the following format: projects/{project_number}/regions/{region_name}/networkAttachments/{network_attachment_name}. */
		networkAttachment: Option[String] = None
	)
	
	object AccessConfig {
		enum TypeEnum extends Enum[TypeEnum] { case ACCESS_TYPE_UNSPECIFIED, ONE_TO_ONE_NAT, DIRECT_IPV6 }
		enum NetworkTierEnum extends Enum[NetworkTierEnum] { case NETWORK_TIER_UNSPECIFIED, PREMIUM, STANDARD }
	}
	case class AccessConfig(
	  /** Optional. In accessConfigs (IPv4), the default and only option is ONE_TO_ONE_NAT. In ipv6AccessConfigs, the default and only option is DIRECT_IPV6. */
		`type`: Option[Schema.AccessConfig.TypeEnum] = None,
	  /** Optional. The name of this access configuration. */
		name: Option[String] = None,
	  /** Optional. The external IP address of this access configuration. */
		natIP: Option[String] = None,
	  /** Optional. The external IPv6 address of this access configuration. */
		externalIpv6: Option[String] = None,
	  /** Optional. The prefix length of the external IPv6 range. */
		externalIpv6PrefixLength: Option[Int] = None,
	  /** Optional. Specifies whether a public DNS 'PTR' record should be created to map the external IP address of the instance to a DNS domain name. */
		setPublicPtr: Option[Boolean] = None,
	  /** Optional. The DNS domain name for the public PTR record. */
		publicPtrDomainName: Option[String] = None,
	  /** Optional. This signifies the networking tier used for configuring this access */
		networkTier: Option[Schema.AccessConfig.NetworkTierEnum] = None
	)
	
	case class AliasIpRange(
	  /** Optional. The IP alias ranges to allocate for this interface. */
		ipCidrRange: Option[String] = None,
	  /** Optional. The name of a subnetwork secondary IP range from which to allocate an IP alias range. If not specified, the primary range of the subnetwork is used. */
		subnetworkRangeName: Option[String] = None
	)
	
	object AttachedDisk {
		enum DiskTypeDeprecatedEnum extends Enum[DiskTypeDeprecatedEnum] { case DISK_TYPE_UNSPECIFIED, SCRATCH, PERSISTENT }
		enum ModeEnum extends Enum[ModeEnum] { case DISK_MODE_UNSPECIFIED, READ_WRITE, READ_ONLY, LOCKED }
		enum DiskInterfaceEnum extends Enum[DiskInterfaceEnum] { case DISK_INTERFACE_UNSPECIFIED, SCSI, NVME, NVDIMM, ISCSI }
		enum SavedStateEnum extends Enum[SavedStateEnum] { case DISK_SAVED_STATE_UNSPECIFIED, PRESERVED }
		enum TypeEnum extends Enum[TypeEnum] { case DISK_TYPE_UNSPECIFIED, SCRATCH, PERSISTENT }
	}
	case class AttachedDisk(
	  /** Optional. Specifies the parameters to initialize this disk. */
		initializeParams: Option[Schema.InitializeParams] = None,
	  /** Optional. This is used as an identifier for the disks. This is the unique name has to provided to modify disk parameters like disk_name and replica_zones (in case of RePDs) */
		deviceName: Option[String] = None,
	  /** Optional. Type of the resource. */
		kind: Option[String] = None,
	  /** Specifies the type of the disk. */
		diskTypeDeprecated: Option[Schema.AttachedDisk.DiskTypeDeprecatedEnum] = None,
	  /** Optional. The mode in which to attach this disk. */
		mode: Option[Schema.AttachedDisk.ModeEnum] = None,
	  /** Optional. Specifies a valid partial or full URL to an existing Persistent Disk resource. */
		source: Option[String] = None,
	  /** Optional. A zero-based index to this disk, where 0 is reserved for the boot disk. */
		index: Option[String] = None,
	  /** Optional. Indicates that this is a boot disk. The virtual machine will use the first partition of the disk for its root filesystem. */
		boot: Option[Boolean] = None,
	  /** Optional. Specifies whether the disk will be auto-deleted when the instance is deleted (but not when the disk is detached from the instance). */
		autoDelete: Option[Boolean] = None,
	  /** Optional. Any valid publicly visible licenses. */
		license: Option[List[String]] = None,
	  /** Optional. Specifies the disk interface to use for attaching this disk. */
		diskInterface: Option[Schema.AttachedDisk.DiskInterfaceEnum] = None,
	  /** Optional. A list of features to enable on the guest operating system. Applicable only for bootable images. */
		guestOsFeature: Option[List[Schema.GuestOsFeature]] = None,
	  /** Optional. Encrypts or decrypts a disk using a customer-supplied encryption key. */
		diskEncryptionKey: Option[Schema.CustomerEncryptionKey] = None,
	  /** Optional. The size of the disk in GB. */
		diskSizeGb: Option[String] = None,
	  /** Optional. Output only. The state of the disk. */
		savedState: Option[Schema.AttachedDisk.SavedStateEnum] = None,
	  /** Optional. Output only. The URI of the disk type resource. For example: projects/project/zones/zone/diskTypes/pd-standard or pd-ssd */
		diskType: Option[String] = None,
	  /** Optional. Specifies the type of the disk. */
		`type`: Option[Schema.AttachedDisk.TypeEnum] = None
	)
	
	case class InitializeParams(
	  /** Optional. Specifies the disk name. If not specified, the default is to use the name of the instance. */
		diskName: Option[String] = None,
	  /** Optional. URL of the zone where the disk should be created. Required for each regional disk associated with the instance. */
		replicaZones: Option[List[String]] = None
	)
	
	object GuestOsFeature {
		enum TypeEnum extends Enum[TypeEnum] { case FEATURE_TYPE_UNSPECIFIED, VIRTIO_SCSI_MULTIQUEUE, WINDOWS, MULTI_IP_SUBNET, UEFI_COMPATIBLE, SECURE_BOOT, GVNIC, SEV_CAPABLE, BARE_METAL_LINUX_COMPATIBLE, SUSPEND_RESUME_COMPATIBLE, SEV_LIVE_MIGRATABLE, SEV_SNP_CAPABLE, TDX_CAPABLE, IDPF, SEV_LIVE_MIGRATABLE_V2 }
	}
	case class GuestOsFeature(
	  /** The ID of a supported feature. */
		`type`: Option[Schema.GuestOsFeature.TypeEnum] = None
	)
	
	case class CustomerEncryptionKey(
	  /** Optional. Specifies a 256-bit customer-supplied encryption key. */
		rawKey: Option[String] = None,
	  /** Optional. RSA-wrapped 2048-bit customer-supplied encryption key to either encrypt or decrypt this resource. */
		rsaEncryptedKey: Option[String] = None,
	  /** Optional. The name of the encryption key that is stored in Google Cloud KMS. */
		kmsKeyName: Option[String] = None,
	  /** Optional. The service account being used for the encryption request for the given KMS key. If absent, the Compute Engine default service account is used. */
		kmsKeyServiceAccount: Option[String] = None
	)
	
	case class Metadata(
	  /** Optional. Array of key/value pairs. The total size of all keys and values must be less than 512 KB. */
		items: Option[List[Schema.Entry]] = None
	)
	
	case class Entry(
	  /** Optional. Key for the metadata entry. */
		key: Option[String] = None,
	  /** Optional. Value for the metadata entry. These are free-form strings, and only have meaning as interpreted by the image running in the instance. The only restriction placed on values is that their size must be less than or equal to 262144 bytes (256 KiB). */
		value: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** Optional. Email address of the service account. */
		email: Option[String] = None,
	  /** Optional. The list of scopes to be made available for this service account. */
		scopes: Option[List[String]] = None
	)
	
	object Scheduling {
		enum OnHostMaintenanceEnum extends Enum[OnHostMaintenanceEnum] { case ON_HOST_MAINTENANCE_UNSPECIFIED, TERMINATE, MIGRATE }
		enum ProvisioningModelEnum extends Enum[ProvisioningModelEnum] { case PROVISIONING_MODEL_UNSPECIFIED, STANDARD, SPOT }
		enum InstanceTerminationActionEnum extends Enum[InstanceTerminationActionEnum] { case INSTANCE_TERMINATION_ACTION_UNSPECIFIED, DELETE, STOP }
	}
	case class Scheduling(
	  /** Optional. Defines the maintenance behavior for this instance. */
		onHostMaintenance: Option[Schema.Scheduling.OnHostMaintenanceEnum] = None,
	  /** Optional. Specifies whether the instance should be automatically restarted if it is terminated by Compute Engine (not terminated by a user). */
		automaticRestart: Option[Boolean] = None,
	  /** Optional. Defines whether the instance is preemptible. */
		preemptible: Option[Boolean] = None,
	  /** Optional. A set of node affinity and anti-affinity configurations. Overrides reservationAffinity. */
		nodeAffinities: Option[List[Schema.NodeAffinity]] = None,
	  /** Optional. The minimum number of virtual CPUs this instance will consume when running on a sole-tenant node. */
		minNodeCpus: Option[Int] = None,
	  /** Optional. Specifies the provisioning model of the instance. */
		provisioningModel: Option[Schema.Scheduling.ProvisioningModelEnum] = None,
	  /** Optional. Specifies the termination action for the instance. */
		instanceTerminationAction: Option[Schema.Scheduling.InstanceTerminationActionEnum] = None,
	  /** Optional. Specifies the maximum amount of time a Local Ssd Vm should wait while recovery of the Local Ssd state is attempted. Its value should be in between 0 and 168 hours with hour granularity and the default value being 1 hour. */
		localSsdRecoveryTimeout: Option[Schema.SchedulingDuration] = None
	)
	
	object NodeAffinity {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, IN, NOT_IN }
	}
	case class NodeAffinity(
	  /** Optional. Corresponds to the label key of Node resource. */
		key: Option[String] = None,
	  /** Optional. Defines the operation of node selection. */
		operator: Option[Schema.NodeAffinity.OperatorEnum] = None,
	  /** Optional. Corresponds to the label values of Node resource. */
		values: Option[List[String]] = None
	)
	
	case class SchedulingDuration(
	  /** Optional. Span of time at a resolution of a second. */
		seconds: Option[String] = None,
	  /** Optional. Span of time that's a fraction of a second at nanosecond resolution. */
		nanos: Option[Int] = None
	)
	
	case class AcceleratorConfig(
	  /** Optional. Full or partial URL of the accelerator type resource to attach to this instance. */
		acceleratorType: Option[String] = None,
	  /** Optional. The number of the guest accelerator cards exposed to this instance. */
		acceleratorCount: Option[Int] = None
	)
	
	case class BackupApplianceBackupProperties(
	  /** Output only. The numeric generation ID of the backup (monotonically increasing). */
		generationId: Option[Int] = None,
	  /** Output only. The time when this backup object was finalized (if none, backup is not finalized). */
		finalizeTime: Option[String] = None,
	  /** Optional. The earliest timestamp of data available in this Backup. */
		recoveryRangeStartTime: Option[String] = None,
	  /** Optional. The latest timestamp of data available in this Backup. */
		recoveryRangeEndTime: Option[String] = None
	)
	
	case class GCPBackupPlanInfo(
	  /** Resource name of backup plan by which workload is protected at the time of the backup. Format: projects/{project}/locations/{location}/backupPlans/{backupPlanId} */
		backupPlan: Option[String] = None,
	  /** The rule id of the backup plan which triggered this backup in case of scheduled backup or used for */
		backupPlanRuleId: Option[String] = None
	)
	
	case class RestoreBackupRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Compute Engine target environment to be used during restore. */
		computeInstanceTargetEnvironment: Option[Schema.ComputeInstanceTargetEnvironment] = None,
	  /** Compute Engine instance properties to be overridden during restore. */
		computeInstanceRestoreProperties: Option[Schema.ComputeInstanceRestoreProperties] = None
	)
	
	case class ComputeInstanceTargetEnvironment(
	  /** Required. Target project for the Compute Engine instance. */
		project: Option[String] = None,
	  /** Required. The zone of the Compute Engine instance. */
		zone: Option[String] = None
	)
	
	object ComputeInstanceRestoreProperties {
		enum KeyRevocationActionTypeEnum extends Enum[KeyRevocationActionTypeEnum] { case KEY_REVOCATION_ACTION_TYPE_UNSPECIFIED, NONE, STOP }
		enum PrivateIpv6GoogleAccessEnum extends Enum[PrivateIpv6GoogleAccessEnum] { case INSTANCE_PRIVATE_IPV6_GOOGLE_ACCESS_UNSPECIFIED, INHERIT_FROM_SUBNETWORK, ENABLE_OUTBOUND_VM_ACCESS_TO_GOOGLE, ENABLE_BIDIRECTIONAL_ACCESS_TO_GOOGLE }
	}
	case class ComputeInstanceRestoreProperties(
	  /** Required. Name of the compute instance. */
		name: Option[String] = None,
	  /** Optional. Controls for advanced machine-related behavior features. */
		advancedMachineFeatures: Option[Schema.AdvancedMachineFeatures] = None,
	  /** Optional. Allows this instance to send and receive packets with non-matching destination or source IPs. */
		canIpForward: Option[Boolean] = None,
	  /** Optional. Controls Confidential compute options on the instance */
		confidentialInstanceConfig: Option[Schema.ConfidentialInstanceConfig] = None,
	  /** Optional. Whether the resource should be protected against deletion. */
		deletionProtection: Option[Boolean] = None,
	  /** Optional. An optional description of this resource. Provide this property when you create the resource. */
		description: Option[String] = None,
	  /** Optional. Array of disks associated with this instance. Persistent disks must be created before you can assign them. */
		disks: Option[List[Schema.AttachedDisk]] = None,
	  /** Optional. Enables display device for the instance. */
		displayDevice: Option[Schema.DisplayDevice] = None,
	  /** Optional. A list of the type and count of accelerator cards attached to the instance. */
		guestAccelerators: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Optional. Specifies the hostname of the instance. The specified hostname must be RFC1035 compliant. If hostname is not specified, the default hostname is [INSTANCE_NAME].c.[PROJECT_ID].internal when using the global DNS, and [INSTANCE_NAME].[ZONE].c.[PROJECT_ID].internal when using zonal DNS. */
		hostname: Option[String] = None,
	  /** Optional. Encrypts suspended data for an instance with a customer-managed encryption key. */
		instanceEncryptionKey: Option[Schema.CustomerEncryptionKey] = None,
	  /** Optional. KeyRevocationActionType of the instance. */
		keyRevocationActionType: Option[Schema.ComputeInstanceRestoreProperties.KeyRevocationActionTypeEnum] = None,
	  /** Optional. Labels to apply to this instance. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Full or partial URL of the machine type resource to use for this instance. */
		machineType: Option[String] = None,
	  /** Optional. This includes custom metadata and predefined keys. */
		metadata: Option[Schema.Metadata] = None,
	  /** Optional. Minimum CPU platform to use for this instance. */
		minCpuPlatform: Option[String] = None,
	  /** Optional. An array of network configurations for this instance. These specify how interfaces are configured to interact with other network services, such as connecting to the internet. Multiple interfaces are supported per instance. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None,
	  /** Optional. Configure network performance such as egress bandwidth tier. */
		networkPerformanceConfig: Option[Schema.NetworkPerformanceConfig] = None,
	  /** Input only. Additional params passed with the request, but not persisted as part of resource payload. */
		params: Option[Schema.InstanceParams] = None,
	  /** Optional. The private IPv6 google access type for the VM. If not specified, use INHERIT_FROM_SUBNETWORK as default. */
		privateIpv6GoogleAccess: Option[Schema.ComputeInstanceRestoreProperties.PrivateIpv6GoogleAccessEnum] = None,
	  /** Optional. Specifies the reservations that this instance can consume from. */
		reservationAffinity: Option[Schema.AllocationAffinity] = None,
	  /** Optional. Resource policies applied to this instance. */
		resourcePolicies: Option[List[String]] = None,
	  /** Optional. Sets the scheduling options for this instance. */
		scheduling: Option[Schema.Scheduling] = None,
	  /** Optional. A list of service accounts, with their specified scopes, authorized for this instance. Only one service account per VM instance is supported. */
		serviceAccounts: Option[List[Schema.ServiceAccount]] = None,
	  /** Optional. Tags to apply to this instance. Tags are used to identify valid sources or targets for network firewalls and are specified by the client during instance creation. */
		tags: Option[Schema.Tags] = None
	)
	
	case class AdvancedMachineFeatures(
	  /** Optional. Whether to enable nested virtualization or not (default is false). */
		enableNestedVirtualization: Option[Boolean] = None,
	  /** Optional. The number of threads per physical core. To disable simultaneous multithreading (SMT) set this to 1. If unset, the maximum number of threads supported per core by the underlying processor is assumed. */
		threadsPerCore: Option[Int] = None,
	  /** Optional. The number of physical cores to expose to an instance. Multiply by the number of threads per core to compute the total number of virtual CPUs to expose to the instance. If unset, the number of cores is inferred from the instance's nominal CPU count and the underlying platform's SMT width. */
		visibleCoreCount: Option[Int] = None,
	  /** Optional. Whether to enable UEFI networking for instance creation. */
		enableUefiNetworking: Option[Boolean] = None
	)
	
	case class ConfidentialInstanceConfig(
	  /** Optional. Defines whether the instance should have confidential compute enabled. */
		enableConfidentialCompute: Option[Boolean] = None
	)
	
	case class DisplayDevice(
	  /** Optional. Enables display for the Compute Engine VM */
		enableDisplay: Option[Boolean] = None
	)
	
	object NetworkPerformanceConfig {
		enum TotalEgressBandwidthTierEnum extends Enum[TotalEgressBandwidthTierEnum] { case TIER_UNSPECIFIED, DEFAULT, TIER_1 }
	}
	case class NetworkPerformanceConfig(
	  /** Optional. The tier of the total egress bandwidth. */
		totalEgressBandwidthTier: Option[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum] = None
	)
	
	case class InstanceParams(
	  /** Optional. Resource manager tags to be bound to the instance. */
		resourceManagerTags: Option[Map[String, String]] = None
	)
	
	object AllocationAffinity {
		enum ConsumeReservationTypeEnum extends Enum[ConsumeReservationTypeEnum] { case TYPE_UNSPECIFIED, NO_RESERVATION, ANY_RESERVATION, SPECIFIC_RESERVATION }
	}
	case class AllocationAffinity(
	  /** Optional. Specifies the type of reservation from which this instance can consume */
		consumeReservationType: Option[Schema.AllocationAffinity.ConsumeReservationTypeEnum] = None,
	  /** Optional. Corresponds to the label key of a reservation resource. */
		key: Option[String] = None,
	  /** Optional. Corresponds to the label values of a reservation resource. */
		values: Option[List[String]] = None
	)
	
	object BackupPlan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, INACTIVE }
	}
	case class BackupPlan(
	  /** Output only. Identifier. The resource name of the `BackupPlan`. Format: `projects/{project}/locations/{location}/backupPlans/{backup_plan}` */
		name: Option[String] = None,
	  /** Optional. The description of the `BackupPlan` resource. The description allows for additional details about `BackupPlan` and its use cases to be provided. An example description is the following: "This is a backup plan that performs a daily backup at 6pm and retains data for 3 months". The description must be at most 2048 characters. */
		description: Option[String] = None,
	  /** Optional. This collection of key/value pairs allows for custom labels to be supplied by the user. Example, {"tag": "Weekly"}. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. When the `BackupPlan` was created. */
		createTime: Option[String] = None,
	  /** Output only. When the `BackupPlan` was last updated. */
		updateTime: Option[String] = None,
	  /** Required. The backup rules for this `BackupPlan`. There must be at least one `BackupRule` message. */
		backupRules: Option[List[Schema.BackupRule]] = None,
	  /** Output only. The `State` for the `BackupPlan`. */
		state: Option[Schema.BackupPlan.StateEnum] = None,
	  /** Required. The resource type to which the `BackupPlan` will be applied. Examples include, "compute.googleapis.com/Instance", "sqladmin.googleapis.com/Instance" and "storage.googleapis.com/Bucket". */
		resourceType: Option[String] = None,
	  /** Optional. `etag` is returned from the service in the response. As a user of the service, you may provide an etag value in this field to prevent stale resources. */
		etag: Option[String] = None,
	  /** Required. Resource name of backup vault which will be used as storage location for backups. Format: projects/{project}/locations/{location}/backupVaults/{backupvault} */
		backupVault: Option[String] = None,
	  /** Output only. The Google Cloud Platform Service Account to be used by the BackupVault for taking backups. Specify the email address of the Backup Vault Service Account. */
		backupVaultServiceAccount: Option[String] = None
	)
	
	case class BackupRule(
	  /** Required. Immutable. The unique id of this `BackupRule`. The `rule_id` is unique per `BackupPlan`.The `rule_id` must start with a lowercase letter followed by up to 62 lowercase letters, numbers, or hyphens. Pattern, /a-z{,62}/. */
		ruleId: Option[String] = None,
	  /** Required. Configures the duration for which backup data will be kept. It is defined in “days”. The value should be greater than or equal to minimum enforced retention of the backup vault. Minimum value is 1 and maximum value is 90 for hourly backups. Minimum value is 1 and maximum value is 90 for daily backups. Minimum value is 7 and maximum value is 186 for weekly backups. Minimum value is 30 and maximum value is 732 for monthly backups. Minimum value is 30 and maximum value is 36159 for yearly backups. */
		backupRetentionDays: Option[Int] = None,
	  /** Required. Defines a schedule that runs within the confines of a defined window of time. */
		standardSchedule: Option[Schema.StandardSchedule] = None
	)
	
	object StandardSchedule {
		enum RecurrenceTypeEnum extends Enum[RecurrenceTypeEnum] { case RECURRENCE_TYPE_UNSPECIFIED, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY }
		enum DaysOfWeekEnum extends Enum[DaysOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum MonthsEnum extends Enum[MonthsEnum] { case MONTH_UNSPECIFIED, JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER }
	}
	case class StandardSchedule(
	  /** Required. Specifies the `RecurrenceType` for the schedule. */
		recurrenceType: Option[Schema.StandardSchedule.RecurrenceTypeEnum] = None,
	  /** Optional. Specifies frequency for hourly backups. A hourly frequency of 2 means jobs will run every 2 hours from start time till end time defined. This is required for `recurrence_type`, `HOURLY` and is not applicable otherwise. A validation error will occur if a value is supplied and `recurrence_type` is not `HOURLY`. Value of hourly frequency should be between 6 and 23. Reason for limit : We found that there is bandwidth limitation of 3GB/S for GMI while taking a backup and 5GB/S while doing a restore. Given the amount of parallel backups and restore we are targeting, this will potentially take the backup time to mins and hours (in worst case scenario). */
		hourlyFrequency: Option[Int] = None,
	  /** Optional. Specifies days of week like, MONDAY or TUESDAY, on which jobs will run. This is required for `recurrence_type`, `WEEKLY` and is not applicable otherwise. A validation error will occur if a value is supplied and `recurrence_type` is not `WEEKLY`. */
		daysOfWeek: Option[List[Schema.StandardSchedule.DaysOfWeekEnum]] = None,
	  /** Optional. Specifies days of months like 1, 5, or 14 on which jobs will run. Values for `days_of_month` are only applicable for `recurrence_type`, `MONTHLY` and `YEARLY`. A validation error will occur if other values are supplied. */
		daysOfMonth: Option[List[Int]] = None,
	  /** Optional. Specifies a week day of the month like, FIRST SUNDAY or LAST MONDAY, on which jobs will run. This will be specified by two fields in `WeekDayOfMonth`, one for the day, e.g. `MONDAY`, and one for the week, e.g. `LAST`. This field is only applicable for `recurrence_type`, `MONTHLY` and `YEARLY`. A validation error will occur if other values are supplied. */
		weekDayOfMonth: Option[Schema.WeekDayOfMonth] = None,
	  /** Optional. Specifies the months of year, like `FEBRUARY` and/or `MAY`, on which jobs will run. This field is only applicable when `recurrence_type` is `YEARLY`. A validation error will occur if other values are supplied. */
		months: Option[List[Schema.StandardSchedule.MonthsEnum]] = None,
	  /** Required. A BackupWindow defines the window of day during which backup jobs will run. Jobs are queued at the beginning of the window and will be marked as `NOT_RUN` if they do not start by the end of the window. Note: running jobs will not be cancelled at the end of the window. */
		backupWindow: Option[Schema.BackupWindow] = None,
	  /** Required. The time zone to be used when interpreting the schedule. The value of this field must be a time zone name from the IANA tz database. See https://en.wikipedia.org/wiki/List_of_tz_database_time_zones for the list of valid timezone names. For e.g., Europe/Paris. */
		timeZone: Option[String] = None
	)
	
	object WeekDayOfMonth {
		enum WeekOfMonthEnum extends Enum[WeekOfMonthEnum] { case WEEK_OF_MONTH_UNSPECIFIED, FIRST, SECOND, THIRD, FOURTH, LAST }
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeekDayOfMonth(
	  /** Required. Specifies the week of the month. */
		weekOfMonth: Option[Schema.WeekDayOfMonth.WeekOfMonthEnum] = None,
	  /** Required. Specifies the day of the week. */
		dayOfWeek: Option[Schema.WeekDayOfMonth.DayOfWeekEnum] = None
	)
	
	case class BackupWindow(
	  /** Required. The hour of day (0-23) when the window starts for e.g. if value of start hour of day is 6 that mean backup window start at 6:00. */
		startHourOfDay: Option[Int] = None,
	  /** Required. The hour of day (1-24) when the window end for e.g. if value of end hour of day is 10 that mean backup window end time is 10:00. End hour of day should be greater than start hour of day. 0 <= start_hour_of_day < end_hour_of_day <= 24 End hour of day is not include in backup window that mean if end_hour_of_day= 10 jobs should start before 10:00. */
		endHourOfDay: Option[Int] = None
	)
	
	case class ListBackupPlansResponse(
	  /** The list of `BackupPlans` in the project for the specified location. If the `{location}` value in the request is "-", the response contains a list of resources from all locations. In case any location is unreachable, the response will only return backup plans in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. BackupPlan */
		backupPlans: Option[List[Schema.BackupPlan]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListBackupPlans` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object BackupPlanAssociation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, INACTIVE, UPDATING }
	}
	case class BackupPlanAssociation(
	  /** Output only. Identifier. The resource name of BackupPlanAssociation in below format Format : projects/{project}/locations/{location}/backupPlanAssociations/{backupPlanAssociationId} */
		name: Option[String] = None,
	  /** Optional. Required. Resource type of workload on which backupplan is applied */
		resourceType: Option[String] = None,
	  /** Required. Immutable. Resource name of workload on which backupplan is applied */
		resource: Option[String] = None,
	  /** Required. Resource name of backup plan which needs to be applied on workload. Format: projects/{project}/locations/{location}/backupPlans/{backupPlanId} */
		backupPlan: Option[String] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the instance was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The BackupPlanAssociation resource state. */
		state: Option[Schema.BackupPlanAssociation.StateEnum] = None,
	  /** Output only. The config info related to backup rules. */
		rulesConfigInfo: Option[List[Schema.RuleConfigInfo]] = None,
	  /** Output only. Output Only. Resource name of data source which will be used as storage location for backups taken. Format : projects/{project}/locations/{location}/backupVaults/{backupvault}/dataSources/{datasource} */
		dataSource: Option[String] = None
	)
	
	object RuleConfigInfo {
		enum LastBackupStateEnum extends Enum[LastBackupStateEnum] { case LAST_BACKUP_STATE_UNSPECIFIED, FIRST_BACKUP_PENDING, PERMISSION_DENIED, SUCCEEDED, FAILED }
	}
	case class RuleConfigInfo(
	  /** Output only. Output Only. Backup Rule id fetched from backup plan. */
		ruleId: Option[String] = None,
	  /** Output only. The last backup state for rule. */
		lastBackupState: Option[Schema.RuleConfigInfo.LastBackupStateEnum] = None,
	  /** Output only. Output Only. google.rpc.Status object to store the last backup error. */
		lastBackupError: Option[Schema.Status] = None,
	  /** Output only. The point in time when the last successful backup was captured from the source. */
		lastSuccessfulBackupConsistencyTime: Option[String] = None
	)
	
	case class ListBackupPlanAssociationsResponse(
	  /** The list of Backup Plan Associations in the project for the specified location. If the `{location}` value in the request is "-", the response contains a list of instances from all locations. In case any location is unreachable, the response will only return backup plan associations in reachable locations and the 'unreachable' field will be populated with a list of unreachable locations. */
		backupPlanAssociations: Option[List[Schema.BackupPlanAssociation]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class TriggerBackupRequest(
	  /** Required. backup rule_id for which a backup needs to be triggered. */
		ruleId: Option[String] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to 'Code.CANCELLED'. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. AdditionalInfo contains additional Info related to backup plan association resource. */
		additionalInfo: Option[Map[String, String]] = None
	)
	
	case class SetInternalStatusResponse(
	
	)
	
	case class RestoreBackupResponse(
	  /** Details of the target resource created/modified as part of restore. */
		targetResource: Option[Schema.TargetResource] = None
	)
	
	case class TargetResource(
	  /** Details of the native Google Cloud resource created as part of restore. */
		gcpResource: Option[Schema.GcpResource] = None
	)
	
	case class GcpResource(
	  /** Name of the Google Cloud resource. */
		gcpResourcename: Option[String] = None,
	  /** Location of the resource: //"global"/"unspecified". */
		location: Option[String] = None,
	  /** Type of the resource. Use the Unified Resource Type, eg. compute.googleapis.com/Instance. */
		`type`: Option[String] = None
	)
}
