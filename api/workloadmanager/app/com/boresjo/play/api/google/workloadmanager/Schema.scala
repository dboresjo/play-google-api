package com.boresjo.play.api.google.workloadmanager

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
	
	case class ListEvaluationsResponse(
	  /** The list of Evaluation */
		evaluations: Option[List[Schema.Evaluation]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Evaluation(
	  /** name of resource names have the form 'projects/{project_id}/locations/{location_id}/evaluations/{evaluation_id}' */
		name: Option[String] = None,
	  /** Description of the Evaluation */
		description: Option[String] = None,
	  /** annotations as key value pairs */
		resourceFilter: Option[Schema.ResourceFilter] = None,
	  /** the name of the rule */
		ruleNames: Option[List[String]] = None,
	  /** Output only. [Output only] The updated rule ids if exist. */
		ruleVersions: Option[List[String]] = None,
	  /** Output only. [Output only] The updated rule ids if exist. */
		resourceStatus: Option[Schema.ResourceStatus] = None,
	  /** Output only. [Output only] Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update time stamp */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** crontab format schedule for scheduled evaluation, currently only support the following schedule: "0 &#42;/1 &#42; &#42; &#42;", "0 &#42;/6 &#42; &#42; &#42;", "0 &#42;/12 &#42; &#42; &#42;", "0 0 &#42;/1 &#42; &#42;", "0 0 &#42;/7 &#42; &#42;", */
		schedule: Option[String] = None,
	  /** The Cloud Storage bucket name for custom rules. */
		customRulesBucket: Option[String] = None,
	  /** Optional. BigQuery destination */
		bigQueryDestination: Option[Schema.BigQueryDestination] = None
	)
	
	case class ResourceFilter(
	  /** The scopes of evaluation resource */
		scopes: Option[List[String]] = None,
	  /** The id pattern for filter resource */
		resourceIdPatterns: Option[List[String]] = None,
	  /** The label used for filter resource */
		inclusionLabels: Option[Map[String, String]] = None,
	  /** Filter compute engine resource */
		gceInstanceFilter: Option[Schema.GceInstanceFilter] = None
	)
	
	case class GceInstanceFilter(
	  /** Service account of compute engine */
		serviceAccounts: Option[List[String]] = None
	)
	
	object ResourceStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING }
	}
	case class ResourceStatus(
	  /** Historical: Used before 2023-05-22 the new version of rule id if exists */
		rulesNewerVersions: Option[List[String]] = None,
	  /** State of the resource */
		state: Option[Schema.ResourceStatus.StateEnum] = None
	)
	
	case class BigQueryDestination(
	  /** Optional. destination dataset to save evaluation results */
		destinationDataset: Option[String] = None,
	  /** Optional. determine if results will be saved in a new table */
		createNewResultsTable: Option[Boolean] = None
	)
	
	case class ListExecutionsResponse(
	  /** The list of Execution */
		executions: Option[List[Schema.Execution]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Execution {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, FAILED }
		enum RunTypeEnum extends Enum[RunTypeEnum] { case TYPE_UNSPECIFIED, ONE_TIME, SCHEDULED }
	}
	case class Execution(
	  /** The name of execution resource. The format is projects/{project}/locations/{location}/evaluations/{evaluation}/executions/{execution} */
		name: Option[String] = None,
	  /** Output only. [Output only] Start time stamp */
		startTime: Option[String] = None,
	  /** Output only. [Output only] End time stamp */
		endTime: Option[String] = None,
	  /** Output only. [Output only] Inventory time stamp */
		inventoryTime: Option[String] = None,
	  /** Output only. [Output only] State */
		state: Option[Schema.Execution.StateEnum] = None,
	  /** Output only. [Output only] Evaluation ID */
		evaluationId: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** type represent whether the execution executed directly by user or scheduled according evaluation.schedule field. */
		runType: Option[Schema.Execution.RunTypeEnum] = None,
	  /** Output only. execution result summary per rule */
		ruleResults: Option[List[Schema.RuleExecutionResult]] = None,
	  /** Optional. External data sources */
		externalDataSources: Option[List[Schema.ExternalDataSources]] = None,
	  /** Output only. Additional information generated by the execution */
		notices: Option[List[Schema.Notice]] = None
	)
	
	object RuleExecutionResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_SUCCESS, STATE_FAILURE, STATE_SKIPPED }
	}
	case class RuleExecutionResult(
	  /** rule name */
		rule: Option[String] = None,
	  /** Output only. The execution status */
		state: Option[Schema.RuleExecutionResult.StateEnum] = None,
	  /** Execution message, if any */
		message: Option[String] = None,
	  /** Number of violations */
		resultCount: Option[String] = None,
	  /** Number of total scanned resources */
		scannedResourceCount: Option[String] = None
	)
	
	object ExternalDataSources {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, BIG_QUERY_TABLE }
	}
	case class ExternalDataSources(
	  /** Optional. Name of external data source. The name will be used inside the rego/sql to refer the external data */
		name: Option[String] = None,
	  /** Required. URI of external data source. example of bq table {project_ID}.{dataset_ID}.{table_ID} */
		uri: Option[String] = None,
	  /** Required. Type of external data source */
		`type`: Option[Schema.ExternalDataSources.TypeEnum] = None,
	  /** Required. The asset type of the external data source this can be one of go/cai-asset-types to override the default asset type or it can be a custom type defined by the user custom type must match the asset type in the rule */
		assetType: Option[String] = None
	)
	
	case class Notice(
	  /** Output only. Message of the notice */
		message: Option[String] = None
	)
	
	case class RunEvaluationRequest(
	  /** Required. Id of the requesting object If auto-generating Id server-side, remove this field and execution_id from the method_signature of Create RPC */
		executionId: Option[String] = None,
	  /** Required. The resource being created */
		execution: Option[Schema.Execution] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class ListExecutionResultsResponse(
	  /** The versions from the specified publisher. */
		executionResults: Option[List[Schema.ExecutionResult]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object ExecutionResult {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TYPE_PASSED, TYPE_VIOLATED }
	}
	case class ExecutionResult(
	  /** The violation message of an execution. */
		violationMessage: Option[String] = None,
	  /** The severity of violation. */
		severity: Option[String] = None,
	  /** The rule that is violated in an evaluation. */
		rule: Option[String] = None,
	  /** The URL for the documentation of the rule. */
		documentationUrl: Option[String] = None,
	  /** The resource that violates the rule. */
		resource: Option[Schema.Resource] = None,
	  /** The details of violation in an evaluation result. */
		violationDetails: Option[Schema.ViolationDetails] = None,
	  /** The commands to remediate the violation. */
		commands: Option[List[Schema.Command]] = None,
	  /** Execution result type of the scanned resource */
		`type`: Option[Schema.ExecutionResult.TypeEnum] = None
	)
	
	case class Resource(
	  /** The type of resource. */
		`type`: Option[String] = None,
	  /** The name of the resource. */
		name: Option[String] = None,
	  /** The service account associated with the resource. */
		serviceAccount: Option[String] = None
	)
	
	case class ViolationDetails(
	  /** The name of the asset. */
		asset: Option[String] = None,
	  /** The service account associated with the resource. */
		serviceAccount: Option[String] = None,
	  /** Details of the violation. */
		observed: Option[Map[String, String]] = None
	)
	
	case class Command(
	  /** AgentCommand specifies a one-time executable program for the agent to run. */
		agentCommand: Option[Schema.AgentCommand] = None,
	  /** ShellCommand is invoked via the agent's command line executor. */
		shellCommand: Option[Schema.ShellCommand] = None
	)
	
	case class AgentCommand(
	  /** command is the name of the agent one-time executable that will be invoked. */
		command: Option[String] = None,
	  /** parameters is a map of key/value pairs that can be used to specify additional one-time executable settings. */
		parameters: Option[Map[String, String]] = None
	)
	
	case class ShellCommand(
	  /** command is the name of the command to be executed. */
		command: Option[String] = None,
	  /** args is a string of arguments to be passed to the command. */
		args: Option[String] = None,
	  /** Optional. If not specified, the default timeout is 60 seconds. */
		timeoutSeconds: Option[Int] = None
	)
	
	case class ListRulesResponse(
	  /** all rules in response */
		rules: Option[List[Schema.Rule]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class Rule(
	  /** rule name */
		name: Option[String] = None,
	  /** Output only. the version of the rule */
		revisionId: Option[String] = None,
	  /** the name display in UI */
		displayName: Option[String] = None,
	  /** descrite rule in plain language */
		description: Option[String] = None,
	  /** the severity of the rule */
		severity: Option[String] = None,
	  /** the primary category */
		primaryCategory: Option[String] = None,
	  /** the secondary category */
		secondaryCategory: Option[String] = None,
	  /** the message template for rule */
		errorMessage: Option[String] = None,
	  /** the docuement url for the rule */
		uri: Option[String] = None,
	  /** the remediation for the rule */
		remediation: Option[String] = None,
	  /** List of user-defined tags */
		tags: Option[List[String]] = None
	)
	
	case class ListScannedResourcesResponse(
	  /** All scanned resources in response */
		scannedResources: Option[List[Schema.ScannedResource]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ScannedResource(
	  /** resource name */
		resource: Option[String] = None,
	  /** resource type */
		`type`: Option[String] = None
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
	
	case class WriteInsightRequest(
	  /** Required. The metrics data details. */
		insight: Option[Schema.Insight] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. The agent version collected this data point. */
		agentVersion: Option[String] = None
	)
	
	case class Insight(
	  /** Output only. [Output only] Create time stamp */
		sentTime: Option[String] = None,
	  /** The insights data for the SAP workload validation. */
		sapValidation: Option[Schema.SapValidation] = None,
	  /** The insights data for SAP system discovery. This is a copy of SAP System proto and should get updated whenever that one changes. */
		sapDiscovery: Option[Schema.SapDiscovery] = None,
	  /** The insights data for the sqlserver workload validation. */
		sqlserverValidation: Option[Schema.SqlserverValidation] = None,
	  /** Required. The instance id where the insight is generated from */
		instanceId: Option[String] = None
	)
	
	case class SapValidation(
	  /** Optional. A list of SAP validation metrics data. */
		validationDetails: Option[List[Schema.SapValidationValidationDetail]] = None,
	  /** Required. The project_id of the cloud project that the Insight data comes from. */
		projectId: Option[String] = None,
	  /** Optional. The zone of the instance that the Insight data comes from. */
		zone: Option[String] = None
	)
	
	object SapValidationValidationDetail {
		enum SapValidationTypeEnum extends Enum[SapValidationTypeEnum] { case SAP_VALIDATION_TYPE_UNSPECIFIED, SYSTEM, COROSYNC, PACEMAKER, HANA, NETWEAVER, HANA_SECURITY, CUSTOM }
	}
	case class SapValidationValidationDetail(
	  /** Optional. The SAP system that the validation data is from. */
		sapValidationType: Option[Schema.SapValidationValidationDetail.SapValidationTypeEnum] = None,
	  /** Optional. The pairs of metrics data: field name & field value. */
		details: Option[Map[String, String]] = None,
	  /** Optional. Was there a SAP system detected for this validation type. */
		isPresent: Option[Boolean] = None
	)
	
	case class SapDiscovery(
	  /** Optional. The metadata for SAP system discovery data. */
		metadata: Option[Schema.SapDiscoveryMetadata] = None,
	  /** Required. An SAP System must have a database. */
		databaseLayer: Option[Schema.SapDiscoveryComponent] = None,
	  /** Optional. An SAP system may run without an application layer. */
		applicationLayer: Option[Schema.SapDiscoveryComponent] = None,
	  /** Output only. A combination of database SID, database instance URI and tenant DB name to make a unique identifier per-system. */
		systemId: Option[String] = None,
	  /** Required. Unix timestamp this system has been updated last. */
		updateTime: Option[String] = None,
	  /** Optional. The GCP project number that this SapSystem belongs to. */
		projectNumber: Option[String] = None,
	  /** Optional. The properties of the workload. */
		workloadProperties: Option[Schema.SapDiscoveryWorkloadProperties] = None
	)
	
	case class SapDiscoveryMetadata(
	  /** Optional. Customer defined, something like "E-commerce pre prod" */
		definedSystem: Option[String] = None,
	  /** Optional. This SAP product name */
		sapProduct: Option[String] = None,
	  /** Optional. Should be "prod", "QA", "dev", "staging", etc. */
		environmentType: Option[String] = None,
	  /** Optional. Customer region string for customer's use. Does not represent GCP region. */
		customerRegion: Option[String] = None
	)
	
	object SapDiscoveryComponent {
		enum TopologyTypeEnum extends Enum[TopologyTypeEnum] { case TOPOLOGY_TYPE_UNSPECIFIED, TOPOLOGY_SCALE_UP, TOPOLOGY_SCALE_OUT }
	}
	case class SapDiscoveryComponent(
	  /** Optional. The resources in a component. */
		resources: Option[List[Schema.SapDiscoveryResource]] = None,
	  /** Optional. The component is a SAP application. */
		applicationProperties: Option[Schema.SapDiscoveryComponentApplicationProperties] = None,
	  /** Optional. The component is a SAP database. */
		databaseProperties: Option[Schema.SapDiscoveryComponentDatabaseProperties] = None,
	  /** Required. Pantheon Project in which the resources reside. */
		hostProject: Option[String] = None,
	  /** Optional. The SAP identifier, used by the SAP software and helps differentiate systems for customers. */
		sid: Option[String] = None,
	  /** Optional. The detected topology of the component. */
		topologyType: Option[Schema.SapDiscoveryComponent.TopologyTypeEnum] = None,
	  /** Optional. A list of host URIs that are part of the HA configuration if present. An empty list indicates the component is not configured for HA. */
		haHosts: Option[List[String]] = None,
	  /** Optional. A list of replication sites used in Disaster Recovery (DR) configurations. */
		replicationSites: Option[List[Schema.SapDiscoveryComponent]] = None
	)
	
	object SapDiscoveryResource {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, RESOURCE_TYPE_COMPUTE, RESOURCE_TYPE_STORAGE, RESOURCE_TYPE_NETWORK }
		enum ResourceKindEnum extends Enum[ResourceKindEnum] { case RESOURCE_KIND_UNSPECIFIED, RESOURCE_KIND_INSTANCE, RESOURCE_KIND_DISK, RESOURCE_KIND_ADDRESS, RESOURCE_KIND_FILESTORE, RESOURCE_KIND_HEALTH_CHECK, RESOURCE_KIND_FORWARDING_RULE, RESOURCE_KIND_BACKEND_SERVICE, RESOURCE_KIND_SUBNETWORK, RESOURCE_KIND_NETWORK, RESOURCE_KIND_PUBLIC_ADDRESS, RESOURCE_KIND_INSTANCE_GROUP }
	}
	case class SapDiscoveryResource(
	  /** Required. The type of this resource. */
		resourceType: Option[Schema.SapDiscoveryResource.ResourceTypeEnum] = None,
	  /** Required. ComputeInstance, ComputeDisk, VPC, Bare Metal server, etc. */
		resourceKind: Option[Schema.SapDiscoveryResource.ResourceKindEnum] = None,
	  /** Required. URI of the resource, includes project, location, and name. */
		resourceUri: Option[String] = None,
	  /** Optional. A list of resource URIs related to this resource. */
		relatedResources: Option[List[String]] = None,
	  /** Required. Unix timestamp of when this resource last had its discovery data updated. */
		updateTime: Option[String] = None,
	  /** Optional. A set of properties only applying to instance type resources. */
		instanceProperties: Option[Schema.SapDiscoveryResourceInstanceProperties] = None
	)
	
	object SapDiscoveryResourceInstanceProperties {
		enum InstanceRoleEnum extends Enum[InstanceRoleEnum] { case INSTANCE_ROLE_UNSPECIFIED, INSTANCE_ROLE_ASCS, INSTANCE_ROLE_ERS, INSTANCE_ROLE_APP_SERVER, INSTANCE_ROLE_DATABASE, INSTANCE_ROLE_ASCS_ERS, INSTANCE_ROLE_ASCS_APP_SERVER, INSTANCE_ROLE_ASCS_DATABASE, INSTANCE_ROLE_ERS_APP_SERVER, INSTANCE_ROLE_ERS_DATABASE, INSTANCE_ROLE_APP_SERVER_DATABASE, INSTANCE_ROLE_ASCS_ERS_APP_SERVER, INSTANCE_ROLE_ASCS_ERS_DATABASE, INSTANCE_ROLE_ASCS_APP_SERVER_DATABASE, INSTANCE_ROLE_ERS_APP_SERVER_DATABASE, INSTANCE_ROLE_ASCS_ERS_APP_SERVER_DATABASE }
	}
	case class SapDiscoveryResourceInstanceProperties(
	  /** Optional. A virtual hostname of the instance if it has one. */
		virtualHostname: Option[String] = None,
	  /** Optional. A list of instance URIs that are part of a cluster with this one. */
		clusterInstances: Option[List[String]] = None,
	  /** Optional. The VM's instance number. */
		instanceNumber: Option[String] = None,
	  /** Optional. Bitmask of instance role, a resource may have multiple roles at once. */
		instanceRole: Option[Schema.SapDiscoveryResourceInstanceProperties.InstanceRoleEnum] = None,
	  /** Optional. App server instances on the host */
		appInstances: Option[List[Schema.SapDiscoveryResourceInstancePropertiesAppInstance]] = None,
	  /** Optional. Instance is part of a DR site. */
		isDrSite: Option[Boolean] = None
	)
	
	case class SapDiscoveryResourceInstancePropertiesAppInstance(
	  /** Optional. Instance name of the SAP application instance. */
		name: Option[String] = None,
	  /** Optional. Instance number of the SAP application instance. */
		number: Option[String] = None
	)
	
	object SapDiscoveryComponentApplicationProperties {
		enum ApplicationTypeEnum extends Enum[ApplicationTypeEnum] { case APPLICATION_TYPE_UNSPECIFIED, NETWEAVER, NETWEAVER_ABAP, NETWEAVER_JAVA }
	}
	case class SapDiscoveryComponentApplicationProperties(
	  /** Required. Type of the application. Netweaver, etc. */
		applicationType: Option[Schema.SapDiscoveryComponentApplicationProperties.ApplicationTypeEnum] = None,
	  /** Optional. Resource URI of the recognized ASCS host of the application. */
		ascsUri: Option[String] = None,
	  /** Optional. Resource URI of the recognized shared NFS of the application. May be empty if the application server has only a single node. */
		nfsUri: Option[String] = None,
	  /** Optional. Kernel version for Netweaver running in the system. */
		kernelVersion: Option[String] = None,
	  /** Optional. Deprecated: ApplicationType now tells you whether this is ABAP or Java. */
		abap: Option[Boolean] = None,
	  /** Optional. Instance number of the SAP application instance. */
		appInstanceNumber: Option[String] = None,
	  /** Optional. Instance number of the ASCS instance. */
		ascsInstanceNumber: Option[String] = None,
	  /** Optional. Instance number of the ERS instance. */
		ersInstanceNumber: Option[String] = None
	)
	
	object SapDiscoveryComponentDatabaseProperties {
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DATABASE_TYPE_UNSPECIFIED, HANA, MAX_DB, DB2 }
	}
	case class SapDiscoveryComponentDatabaseProperties(
	  /** Required. Type of the database. HANA, DB2, etc. */
		databaseType: Option[Schema.SapDiscoveryComponentDatabaseProperties.DatabaseTypeEnum] = None,
	  /** Required. URI of the recognized primary instance of the database. */
		primaryInstanceUri: Option[String] = None,
	  /** Optional. URI of the recognized shared NFS of the database. May be empty if the database has only a single node. */
		sharedNfsUri: Option[String] = None,
	  /** Optional. The version of the database software running in the system. */
		databaseVersion: Option[String] = None,
	  /** Optional. Instance number of the SAP instance. */
		instanceNumber: Option[String] = None,
	  /** Optional. SID of the system database. */
		databaseSid: Option[String] = None
	)
	
	case class SapDiscoveryWorkloadProperties(
	  /** Optional. List of SAP Products and their versions running on the system. */
		productVersions: Option[List[Schema.SapDiscoveryWorkloadPropertiesProductVersion]] = None,
	  /** Optional. A list of SAP software components and their versions running on the system. */
		softwareComponentVersions: Option[List[Schema.SapDiscoveryWorkloadPropertiesSoftwareComponentProperties]] = None
	)
	
	case class SapDiscoveryWorkloadPropertiesProductVersion(
	  /** Optional. Name of the product. */
		name: Option[String] = None,
	  /** Optional. Version of the product. */
		version: Option[String] = None
	)
	
	case class SapDiscoveryWorkloadPropertiesSoftwareComponentProperties(
	  /** Optional. Name of the component. */
		name: Option[String] = None,
	  /** Optional. The component's major version. */
		version: Option[String] = None,
	  /** Optional. The component's minor version. */
		extVersion: Option[String] = None,
	  /** Optional. The component's type. */
		`type`: Option[String] = None
	)
	
	case class SqlserverValidation(
	  /** Optional. The agent version collected this data point */
		agentVersion: Option[String] = None,
	  /** Optional. A list of SqlServer validation metrics data. */
		validationDetails: Option[List[Schema.SqlserverValidationValidationDetail]] = None,
	  /** Required. The project_id of the cloud project that the Insight data comes from. */
		projectId: Option[String] = None,
	  /** Required. The instance_name of the instance that the Insight data comes from. According to https://linter.aip.dev/122/name-suffix: field names should not use the _name suffix unless the field would be ambiguous without it. */
		instance: Option[String] = None
	)
	
	object SqlserverValidationValidationDetail {
		enum TypeEnum extends Enum[TypeEnum] { case SQLSERVER_VALIDATION_TYPE_UNSPECIFIED, OS, DB_LOG_DISK_SEPARATION, DB_MAX_PARALLELISM, DB_CXPACKET_WAITS, DB_TRANSACTION_LOG_HANDLING, DB_VIRTUAL_LOG_FILE_COUNT, DB_BUFFER_POOL_EXTENSION, DB_MAX_SERVER_MEMORY, INSTANCE_METRICS, DB_INDEX_FRAGMENTATION, DB_TABLE_INDEX_COMPRESSION, DB_BACKUP_POLICY }
	}
	case class SqlserverValidationValidationDetail(
	  /** Optional. The Sqlserver system that the validation data is from. */
		`type`: Option[Schema.SqlserverValidationValidationDetail.TypeEnum] = None,
	  /** Required. Details wraps map that represents collected data names and values. */
		details: Option[List[Schema.SqlserverValidationDetails]] = None
	)
	
	case class SqlserverValidationDetails(
	  /** Required. Collected data is in format. */
		fields: Option[Map[String, String]] = None
	)
	
	case class WriteInsightResponse(
	
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
}
