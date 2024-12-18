package com.boresjo.play.api.google.bigqueryconnection

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Connection(
	  /** Output only. The resource name of the connection in the form of: `projects/{project_id}/locations/{location_id}/connections/{connection_id}` */
		name: Option[String] = None,
	  /** User provided display name for the connection. */
		friendlyName: Option[String] = None,
	  /** User provided description. */
		description: Option[String] = None,
	  /** Cloud SQL properties. */
		cloudSql: Option[Schema.CloudSqlProperties] = None,
	  /** Amazon Web Services (AWS) properties. */
		aws: Option[Schema.AwsProperties] = None,
	  /** Azure properties. */
		azure: Option[Schema.AzureProperties] = None,
	  /** Cloud Spanner properties. */
		cloudSpanner: Option[Schema.CloudSpannerProperties] = None,
	  /** Cloud Resource properties. */
		cloudResource: Option[Schema.CloudResourceProperties] = None,
	  /** Spark properties. */
		spark: Option[Schema.SparkProperties] = None,
	  /** Optional. Salesforce DataCloud properties. This field is intended for use only by Salesforce partner projects. This field contains properties for your Salesforce DataCloud connection. */
		salesforceDataCloud: Option[Schema.SalesforceDataCloudProperties] = None,
	  /** Optional. Connector configuration. */
		configuration: Option[Schema.ConnectorConfiguration] = None,
	  /** Output only. The creation timestamp of the connection. */
		creationTime: Option[String] = None,
	  /** Output only. The last update timestamp of the connection. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. True, if credential is configured for this connection. */
		hasCredential: Option[Boolean] = None,
	  /** Optional. The Cloud KMS key that is used for credentials encryption. If omitted, internal Google owned encryption keys are used. Example: `projects/[kms_project_id]/locations/[region]/keyRings/[key_region]/cryptoKeys/[key]` */
		kmsKeyName: Option[String] = None
	)
	
	object CloudSqlProperties {
		enum TypeEnum extends Enum[TypeEnum] { case DATABASE_TYPE_UNSPECIFIED, POSTGRES, MYSQL }
	}
	case class CloudSqlProperties(
	  /** Cloud SQL instance ID in the form `project:location:instance`. */
		instanceId: Option[String] = None,
	  /** Database name. */
		database: Option[String] = None,
	  /** Type of the Cloud SQL database. */
		`type`: Option[Schema.CloudSqlProperties.TypeEnum] = None,
	  /** Input only. Cloud SQL credential. */
		credential: Option[Schema.CloudSqlCredential] = None,
	  /** Output only. The account ID of the service used for the purpose of this connection. When the connection is used in the context of an operation in BigQuery, this service account will serve as the identity being used for connecting to the CloudSQL instance specified in this connection. */
		serviceAccountId: Option[String] = None
	)
	
	case class CloudSqlCredential(
	  /** The username for the credential. */
		username: Option[String] = None,
	  /** The password for the credential. */
		password: Option[String] = None
	)
	
	case class AwsProperties(
	  /** Authentication using Google owned service account to assume into customer's AWS IAM Role. */
		accessRole: Option[Schema.AwsAccessRole] = None
	)
	
	case class AwsAccessRole(
	  /** The user’s AWS IAM Role that trusts the Google-owned AWS IAM user Connection. */
		iamRoleId: Option[String] = None,
	  /** A unique Google-owned and Google-generated identity for the Connection. This identity will be used to access the user's AWS IAM Role. */
		identity: Option[String] = None
	)
	
	case class AzureProperties(
	  /** Output only. The name of the Azure Active Directory Application. */
		application: Option[String] = None,
	  /** Output only. The client id of the Azure Active Directory Application. */
		clientId: Option[String] = None,
	  /** Output only. The object id of the Azure Active Directory Application. */
		objectId: Option[String] = None,
	  /** The id of customer's directory that host the data. */
		customerTenantId: Option[String] = None,
	  /** The URL user will be redirected to after granting consent during connection setup. */
		redirectUri: Option[String] = None,
	  /** The client ID of the user's Azure Active Directory Application used for a federated connection. */
		federatedApplicationClientId: Option[String] = None,
	  /** Output only. A unique Google-owned and Google-generated identity for the Connection. This identity will be used to access the user's Azure Active Directory Application. */
		identity: Option[String] = None
	)
	
	case class CloudSpannerProperties(
	  /** Cloud Spanner database in the form `project/instance/database' */
		database: Option[String] = None,
	  /** If parallelism should be used when reading from Cloud Spanner */
		useParallelism: Option[Boolean] = None,
	  /** Allows setting max parallelism per query when executing on Spanner independent compute resources. If unspecified, default values of parallelism are chosen that are dependent on the Cloud Spanner instance configuration. REQUIRES: `use_parallelism` must be set. REQUIRES: `use_data_boost` must be set. */
		maxParallelism: Option[Int] = None,
	  /** Deprecated: prefer use_data_boost instead. If the serverless analytics service should be used to read data from Cloud Spanner. Note: `use_parallelism` must be set when using serverless analytics. */
		useServerlessAnalytics: Option[Boolean] = None,
	  /** If set, the request will be executed via Spanner independent compute resources. REQUIRES: `use_parallelism` must be set. */
		useDataBoost: Option[Boolean] = None,
	  /** Optional. Cloud Spanner database role for fine-grained access control. The Cloud Spanner admin should have provisioned the database role with appropriate permissions, such as `SELECT` and `INSERT`. Other users should only use roles provided by their Cloud Spanner admins. For more details, see [About fine-grained access control] (https://cloud.google.com/spanner/docs/fgac-about). REQUIRES: The database role name must start with a letter, and can only contain letters, numbers, and underscores. */
		databaseRole: Option[String] = None
	)
	
	case class CloudResourceProperties(
	  /** Output only. The account ID of the service created for the purpose of this connection. The service account does not have any permissions associated with it when it is created. After creation, customers delegate permissions to the service account. When the connection is used in the context of an operation in BigQuery, the service account will be used to connect to the desired resources in GCP. The account ID is in the form of: @gcp-sa-bigquery-cloudresource.iam.gserviceaccount.com */
		serviceAccountId: Option[String] = None
	)
	
	case class SparkProperties(
	  /** Output only. The account ID of the service created for the purpose of this connection. The service account does not have any permissions associated with it when it is created. After creation, customers delegate permissions to the service account. When the connection is used in the context of a stored procedure for Apache Spark in BigQuery, the service account is used to connect to the desired resources in Google Cloud. The account ID is in the form of: bqcx--@gcp-sa-bigquery-consp.iam.gserviceaccount.com */
		serviceAccountId: Option[String] = None,
	  /** Optional. Dataproc Metastore Service configuration for the connection. */
		metastoreServiceConfig: Option[Schema.MetastoreServiceConfig] = None,
	  /** Optional. Spark History Server configuration for the connection. */
		sparkHistoryServerConfig: Option[Schema.SparkHistoryServerConfig] = None
	)
	
	case class MetastoreServiceConfig(
	  /** Optional. Resource name of an existing Dataproc Metastore service. Example: &#42; `projects/[project_id]/locations/[region]/services/[service_id]` */
		metastoreService: Option[String] = None
	)
	
	case class SparkHistoryServerConfig(
	  /** Optional. Resource name of an existing Dataproc Cluster to act as a Spark History Server for the connection. Example: &#42; `projects/[project_id]/regions/[region]/clusters/[cluster_name]` */
		dataprocCluster: Option[String] = None
	)
	
	case class SalesforceDataCloudProperties(
	  /** The URL to the user's Salesforce DataCloud instance. */
		instanceUri: Option[String] = None,
	  /** Output only. A unique Google-owned and Google-generated service account identity for the connection. */
		identity: Option[String] = None,
	  /** The ID of the user's Salesforce tenant. */
		tenantId: Option[String] = None
	)
	
	case class ConnectorConfiguration(
	  /** Required. Immutable. The ID of the Connector these parameters are configured for. */
		connectorId: Option[String] = None,
	  /** Specifies how to reach the remote system this connection is pointing to. */
		endpoint: Option[Schema.ConnectorConfigurationEndpoint] = None,
	  /** Client authentication. */
		authentication: Option[Schema.ConnectorConfigurationAuthentication] = None,
	  /** Networking configuration. */
		network: Option[Schema.ConnectorConfigurationNetwork] = None,
	  /** Data asset. */
		asset: Option[Schema.ConnectorConfigurationAsset] = None
	)
	
	case class ConnectorConfigurationEndpoint(
	  /** Host and port in a format of `hostname:port` as defined in https://www.ietf.org/rfc/rfc3986.html#section-3.2.2 and https://www.ietf.org/rfc/rfc3986.html#section-3.2.3. */
		hostPort: Option[String] = None
	)
	
	case class ConnectorConfigurationAuthentication(
	  /** Username/password authentication. */
		usernamePassword: Option[Schema.ConnectorConfigurationUsernamePassword] = None,
	  /** Output only. Google-managed service account associated with this connection, e.g., `service-{project_number}@gcp-sa-bigqueryconnection.iam.gserviceaccount.com`. BigQuery jobs using this connection will act as `service_account` identity while connecting to the datasource. */
		serviceAccount: Option[String] = None
	)
	
	case class ConnectorConfigurationUsernamePassword(
	  /** Required. Username. */
		username: Option[String] = None,
	  /** Required. Password. */
		password: Option[Schema.ConnectorConfigurationSecret] = None
	)
	
	object ConnectorConfigurationSecret {
		enum SecretTypeEnum extends Enum[SecretTypeEnum] { case SECRET_TYPE_UNSPECIFIED, PLAINTEXT }
	}
	case class ConnectorConfigurationSecret(
	  /** Input only. Secret as plaintext. */
		plaintext: Option[String] = None,
	  /** Output only. Indicates type of secret. Can be used to check type of stored secret value even if it's `INPUT_ONLY`. */
		secretType: Option[Schema.ConnectorConfigurationSecret.SecretTypeEnum] = None
	)
	
	case class ConnectorConfigurationNetwork(
	  /** Private Service Connect networking configuration. */
		privateServiceConnect: Option[Schema.ConnectorConfigurationPrivateServiceConnect] = None
	)
	
	case class ConnectorConfigurationPrivateServiceConnect(
	  /** Required. Network Attachment name in the format of `projects/{project}/regions/{region}/networkAttachments/{networkattachment}`. */
		networkAttachment: Option[String] = None
	)
	
	case class ConnectorConfigurationAsset(
	  /** Name of the database. */
		database: Option[String] = None,
	  /** Full Google Cloud resource name - https://cloud.google.com/apis/design/resource_names#full_resource_name. Example: `//library.googleapis.com/shelves/shelf1/books/book2` */
		googleCloudResource: Option[String] = None
	)
	
	case class ListConnectionsResponse(
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** List of connections. */
		connections: Option[List[Schema.Connection]] = None
	)
	
	case class Empty(
	
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
}
