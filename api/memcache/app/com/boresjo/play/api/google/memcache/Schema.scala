package com.boresjo.play.api.google.memcache

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListInstancesResponse(
	  /** A list of Memcached instances in the project in the specified location, or across all locations. If the `location_id` in the parent field of the request is "-", all regions available to the project are queried, and the results aggregated. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum MemcacheVersionEnum extends Enum[MemcacheVersionEnum] { case MEMCACHE_VERSION_UNSPECIFIED, MEMCACHE_1_5, MEMCACHE_1_6_15 }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, DELETING, PERFORMING_MAINTENANCE, MEMCACHE_VERSION_UPGRADING }
	}
	case class Instance(
	  /** Required. Unique name of the resource in this scope including project and location using the form: `projects/{project_id}/locations/{location_id}/instances/{instance_id}` Note: Memcached instances are managed and addressed at the regional level so `location_id` here refers to a Google Cloud region; however, users may choose which zones Memcached nodes should be provisioned in within an instance. Refer to zones field for more details. */
		name: Option[String] = None,
	  /** User provided name for the instance, which is only used for display purposes. Cannot be more than 80 characters. */
		displayName: Option[String] = None,
	  /** Resource labels to represent user-provided metadata. Refer to cloud documentation on labels for more details. https://cloud.google.com/compute/docs/labeling-resources */
		labels: Option[Map[String, String]] = None,
	  /** The full name of the Google Compute Engine [network](/compute/docs/networks-and-firewalls#networks) to which the instance is connected. If left unspecified, the `default` network will be used. */
		authorizedNetwork: Option[String] = None,
	  /** Zones in which Memcached nodes should be provisioned. Memcached nodes will be equally distributed across these zones. If not provided, the service will by default create nodes in all zones in the region for the instance. */
		zones: Option[List[String]] = None,
	  /** Required. Number of nodes in the Memcached instance. */
		nodeCount: Option[Int] = None,
	  /** Required. Configuration for Memcached nodes. */
		nodeConfig: Option[Schema.NodeConfig] = None,
	  /** The major version of Memcached software. If not provided, latest supported version will be used. Currently the latest supported major version is `MEMCACHE_1_5`. The minor version will be automatically determined by our system based on the latest supported minor version. */
		memcacheVersion: Option[Schema.Instance.MemcacheVersionEnum] = None,
	  /** User defined parameters to apply to the memcached process on each node. */
		parameters: Option[Schema.MemcacheParameters] = None,
	  /** Output only. List of Memcached nodes. Refer to Node message for more details. */
		memcacheNodes: Option[List[Schema.Node]] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the instance was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The state of this Memcached instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. The full version of memcached server running on this instance. System automatically determines the full memcached version for an instance based on the input MemcacheVersion. The full version format will be "memcached-1.5.16". */
		memcacheFullVersion: Option[String] = None,
	  /** List of messages that describe the current state of the Memcached instance. */
		instanceMessages: Option[List[Schema.InstanceMessage]] = None,
	  /** Output only. Endpoint for the Discovery API. */
		discoveryEndpoint: Option[String] = None,
	  /** The maintenance policy for the instance. If not provided, the maintenance event will be performed based on Memorystore internal rollout schedule. */
		maintenancePolicy: Option[Schema.GoogleCloudMemcacheV1MaintenancePolicy] = None,
	  /** Output only. Published maintenance schedule. */
		maintenanceSchedule: Option[Schema.MaintenanceSchedule] = None,
	  /** Optional. Contains the id of allocated IP address ranges associated with the private service access connection for example, "test-default" associated with IP range 10.0.0.0/29. */
		reservedIpRangeId: Option[List[String]] = None,
	  /** Optional. Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	case class NodeConfig(
	  /** Required. Number of cpus per Memcached node. */
		cpuCount: Option[Int] = None,
	  /** Required. Memory size in MiB for each Memcached node. */
		memorySizeMb: Option[Int] = None
	)
	
	case class MemcacheParameters(
	  /** Output only. The unique ID associated with this set of parameters. Users can use this id to determine if the parameters associated with the instance differ from the parameters associated with the nodes. A discrepancy between parameter ids can inform users that they may need to take action to apply parameters on nodes. */
		id: Option[String] = None,
	  /** User defined set of parameters to use in the memcached process. */
		params: Option[Map[String, String]] = None
	)
	
	object Node {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, DELETING, UPDATING }
		enum MemcacheVersionEnum extends Enum[MemcacheVersionEnum] { case MEMCACHE_VERSION_UNSPECIFIED, MEMCACHE_1_5, MEMCACHE_1_6_15 }
	}
	case class Node(
	  /** Output only. Identifier of the Memcached node. The node id does not include project or location like the Memcached instance name. */
		nodeId: Option[String] = None,
	  /** Output only. Location (GCP Zone) for the Memcached node. */
		zone: Option[String] = None,
	  /** Output only. Current state of the Memcached node. */
		state: Option[Schema.Node.StateEnum] = None,
	  /** Output only. Hostname or IP address of the Memcached node used by the clients to connect to the Memcached server on this node. */
		host: Option[String] = None,
	  /** Output only. The port number of the Memcached server on this node. */
		port: Option[Int] = None,
	  /** User defined parameters currently applied to the node. */
		parameters: Option[Schema.MemcacheParameters] = None,
	  /** Output only. Major version of memcached server running on this node, e.g. MEMCACHE_1_5 */
		memcacheVersion: Option[Schema.Node.MemcacheVersionEnum] = None,
	  /** Output only. The full version of memcached server running on this node. e.g. - memcached-1.5.16 */
		memcacheFullVersion: Option[String] = None
	)
	
	object InstanceMessage {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, ZONE_DISTRIBUTION_UNBALANCED }
	}
	case class InstanceMessage(
	  /** A code that correspond to one type of user-facing message. */
		code: Option[Schema.InstanceMessage.CodeEnum] = None,
	  /** Message on memcached instance which will be exposed to users. */
		message: Option[String] = None
	)
	
	case class GoogleCloudMemcacheV1MaintenancePolicy(
	  /** Output only. The time when the policy was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the policy was updated. */
		updateTime: Option[String] = None,
	  /** Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Required. Maintenance window that is applied to resources covered by this policy. Minimum 1. For the current version, the maximum number of weekly_maintenance_windows is expected to be one. */
		weeklyMaintenanceWindow: Option[List[Schema.WeeklyMaintenanceWindow]] = None
	)
	
	object WeeklyMaintenanceWindow {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeeklyMaintenanceWindow(
	  /** Required. Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.WeeklyMaintenanceWindow.DayEnum] = None,
	  /** Required. Start time of the window in UTC. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Required. Duration of the time window. */
		duration: Option[String] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class MaintenanceSchedule(
	  /** Output only. The start time of any upcoming scheduled maintenance for this instance. */
		startTime: Option[String] = None,
	  /** Output only. The end time of any upcoming scheduled maintenance for this instance. */
		endTime: Option[String] = None,
	  /** Output only. The deadline that the maintenance schedule start time can not go beyond, including reschedule. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class UpdateParametersRequest(
	  /** Required. Mask of fields to update. */
		updateMask: Option[String] = None,
	  /** The parameters to apply to the instance. */
		parameters: Option[Schema.MemcacheParameters] = None
	)
	
	case class ApplyParametersRequest(
	  /** Nodes to which the instance-level parameter group is applied. */
		nodeIds: Option[List[String]] = None,
	  /** Whether to apply instance-level parameter group to all nodes. If set to true, users are restricted from specifying individual nodes, and `ApplyParameters` updates all nodes within the instance. */
		applyAll: Option[Boolean] = None
	)
	
	object RescheduleMaintenanceRequest {
		enum RescheduleTypeEnum extends Enum[RescheduleTypeEnum] { case RESCHEDULE_TYPE_UNSPECIFIED, IMMEDIATE, NEXT_AVAILABLE_WINDOW, SPECIFIC_TIME }
	}
	case class RescheduleMaintenanceRequest(
	  /** Required. If reschedule type is SPECIFIC_TIME, must set up schedule_time as well. */
		rescheduleType: Option[Schema.RescheduleMaintenanceRequest.RescheduleTypeEnum] = None,
	  /** Timestamp when the maintenance shall be rescheduled to if reschedule_type=SPECIFIC_TIME, in RFC 3339 format, for example `2012-11-15T16:19:00.094Z`. */
		scheduleTime: Option[String] = None
	)
	
	object GoogleCloudMemcacheV1UpgradeInstanceRequest {
		enum MemcacheVersionEnum extends Enum[MemcacheVersionEnum] { case MEMCACHE_VERSION_UNSPECIFIED, MEMCACHE_1_5, MEMCACHE_1_6_15 }
	}
	case class GoogleCloudMemcacheV1UpgradeInstanceRequest(
	  /** Required. Specifies the target version of memcached engine to upgrade to. */
		memcacheVersion: Option[Schema.GoogleCloudMemcacheV1UpgradeInstanceRequest.MemcacheVersionEnum] = None
	)
	
	case class GoogleCloudMemcacheV1LocationMetadata(
	  /** Output only. The set of available zones in the location. The map is keyed by the lowercase ID of each zone, as defined by GCE. These keys can be specified in the `zones` field when creating a Memcached instance. */
		availableZones: Option[Map[String, Schema.GoogleCloudMemcacheV1ZoneMetadata]] = None
	)
	
	case class GoogleCloudMemcacheV1ZoneMetadata(
	
	)
	
	case class LocationMetadata(
	  /** Output only. The set of available zones in the location. The map is keyed by the lowercase ID of each zone, as defined by GCE. These keys can be specified in the `zones` field when creating a Memcached instance. */
		availableZones: Option[Map[String, Schema.ZoneMetadata]] = None
	)
	
	case class ZoneMetadata(
	
	)
	
	case class OperationMetadata(
	  /** Output only. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudMemcacheV1OperationMetadata(
	  /** Output only. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object GoogleCloudSaasacceleratorManagementProvidersV1Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, REPAIRING, DELETING, ERROR }
	}
	case class GoogleCloudSaasacceleratorManagementProvidersV1Instance(
	  /** Unique name of the resource. It uses the form: `projects/{project_number}/locations/{location_id}/instances/{instance_id}` Note: This name is passed, stored and logged across the rollout system. So use of consumer project_id or any other consumer PII in the name is strongly discouraged for wipeout (go/wipeout) compliance. See go/elysium/project_ids#storage-guidance for more details. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the resource was last modified. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current lifecycle state of the resource (e.g. if it's being created or ready to use). */
		state: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum] = None,
	  /** Software versions that are used to deploy this instance. This can be mutated by rollout services. */
		softwareVersions: Option[Map[String, String]] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the referenced policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_settings.maintenance_policies is set. */
		maintenancePolicyNames: Option[Map[String, String]] = None,
	  /** Output only. ID of the associated GCP tenant project. See go/get-instance-metadata. */
		tenantProjectId: Option[String] = None,
	  /** Output only. Custom string attributes used primarily to expose producer-specific information in monitoring dashboards. See go/get-instance-metadata. */
		producerMetadata: Option[Map[String, String]] = None,
	  /** Output only. The list of data plane resources provisioned for this instance, e.g. compute VMs. See go/get-instance-metadata. */
		provisionedResources: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]] = None,
	  /** Output only. SLO metadata for instance classification in the Standardized dataplane SLO platform. See go/cloud-ssa-standard-slo for feature description. */
		sloMetadata: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata] = None,
	  /** The MaintenanceSchedule contains the scheduling information of published maintenance schedule with same key as software_versions. */
		maintenanceSchedules: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]] = None,
	  /** consumer_defined_name is the name of the instance set by the service consumers. Generally this is different from the `name` field which reperesents the system-assigned id of the instance which the service consumers do not recognize. This is a required field for tenants onboarding to Maintenance Window notifications (go/slm-rollout-maintenance-policies#prerequisites). */
		consumerDefinedName: Option[String] = None,
	  /** Link to the SLM instance template. Only populated when updating SLM instances via SSA's Actuation service adaptor. Service producers with custom control plane (e.g. Cloud SQL) doesn't need to populate this field. Instead they should use software_versions. */
		slmInstanceTemplate: Option[String] = None,
	  /** Optional. The MaintenanceSettings associated with instance. */
		maintenanceSettings: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings] = None,
	  /** Optional. The instance_type of this instance of format: projects/{project_number}/locations/{location_id}/instanceTypes/{instance_type_id}. Instance Type represents a high-level tier or SKU of the service that this instance belong to. When enabled(eg: Maintenance Rollout), Rollout uses 'instance_type' along with 'software_versions' to determine whether instance needs an update or not. */
		instanceType: Option[String] = None,
	  /** Optional. notification_parameter are information that service producers may like to include that is not relevant to Rollout. This parameter will only be passed to Gamma and Cloud Logging for notification/logging purpose. */
		notificationParameters: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource(
	  /** Type of the resource. This can be either a GCP resource or a custom one (e.g. another cloud provider's VM). For GCP compute resources use singular form of the names listed in GCP compute API documentation (https://cloud.google.com/compute/docs/reference/rest/v1/), prefixed with 'compute-', for example: 'compute-instance', 'compute-disk', 'compute-autoscaler'. */
		resourceType: Option[String] = None,
	  /** URL identifying the resource, e.g. "https://www.googleapis.com/compute/v1/projects/...)". */
		resourceUrl: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata(
	  /** Name of the SLO tier the Instance belongs to. This name will be expected to match the tiers specified in the service SLO configuration. Field is mandatory and must not be empty. */
		tier: Option[String] = None,
	  /** Optional. List of nodes. Some producers need to use per-node metadata to calculate SLO. This field allows such producers to publish per-node SLO meta data, which will be consumed by SSA Eligibility Exporter and published in the form of per node metric to Monarch. */
		nodes: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]] = None,
	  /** Optional. Multiple per-instance SLI eligibilities which apply for individual SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata(
	  /** The id of the node. This should be equal to SaasInstanceNode.node_id. */
		nodeId: Option[String] = None,
	  /** The location of the node, if different from instance location. */
		location: Option[String] = None,
	  /** If present, this will override eligibility for the node coming from instance or exclusions for specified SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility(
	  /** An entry in the eligibilities map specifies an eligibility for a particular SLI for the given instance. The SLI key in the name must be a valid SLI name specified in the Eligibility Exporter binary flags otherwise an error will be emitted by Eligibility Exporter and the oncaller will be alerted. If an SLI has been defined in the binary flags but the eligibilities map does not contain it, the corresponding SLI time series will not be emitted by the Eligibility Exporter. This ensures a smooth rollout and compatibility between the data produced by different versions of the Eligibility Exporters. If eligibilities map contains a key for an SLI which has not been declared in the binary flags, there will be an error message emitted in the Eligibility Exporter log and the metric for the SLI in question will not be emitted. */
		eligibilities: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility(
	  /** Whether an instance is eligible or ineligible. */
		eligible: Option[Boolean] = None,
	  /** User-defined reason for the current value of instance eligibility. Usually, this can be directly mapped to the internal state. An empty reason is allowed. */
		reason: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule(
	  /** The scheduled start time for the maintenance. */
		startTime: Option[String] = None,
	  /** The scheduled end time for the maintenance. */
		endTime: Option[String] = None,
	  /** This field is deprecated, and will be always set to true since reschedule can happen multiple times now. This field should not be removed until all service producers remove this for their customers. */
		canReschedule: Option[Boolean] = None,
	  /** The rollout management policy this maintenance schedule is associated with. When doing reschedule update request, the reschedule should be against this given policy. */
		rolloutManagementPolicy: Option[String] = None,
	  /** schedule_deadline_time is the time deadline any schedule start time cannot go beyond, including reschedule. It's normally the initial schedule start time plus maintenance window length (1 day or 1 week). Maintenance cannot be scheduled to start beyond this deadline. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings(
	  /** Optional. Exclude instance from maintenance. When true, rollout service will not attempt maintenance on the instance. Rollout service will include the instance in reported rollout progress as not attempted. */
		exclude: Option[Boolean] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the embedded policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_policy_names is set. If only the name is needed, then only populate MaintenancePolicy.name. */
		maintenancePolicies: Option[Map[String, Schema.MaintenancePolicy]] = None,
	  /** Optional. If the update call is triggered from rollback, set the value as true. */
		isRollback: Option[Boolean] = None
	)
	
	object MaintenancePolicy {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, DELETING }
	}
	case class MaintenancePolicy(
	  /** Required. MaintenancePolicy name using the form: `projects/{project_id}/locations/{location_id}/maintenancePolicies/{maintenance_policy_id}` where {project_id} refers to a GCP consumer project ID, {location_id} refers to a GCP region/zone, {maintenance_policy_id} must be 1-63 characters long and match the regular expression `[a-z0-9]([-a-z0-9]&#42;[a-z0-9])?`. */
		name: Option[String] = None,
	  /** Output only. The time when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The state of the policy. */
		state: Option[Schema.MaintenancePolicy.StateEnum] = None,
	  /** Maintenance policy applicable to instance update. */
		updatePolicy: Option[Schema.UpdatePolicy] = None
	)
	
	object UpdatePolicy {
		enum ChannelEnum extends Enum[ChannelEnum] { case UPDATE_CHANNEL_UNSPECIFIED, EARLIER, LATER, WEEK1, WEEK2, WEEK5 }
	}
	case class UpdatePolicy(
	  /** Optional. Maintenance window that is applied to resources covered by this policy. */
		window: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. Relative scheduling channel applied to resource. */
		channel: Option[Schema.UpdatePolicy.ChannelEnum] = None,
	  /** Deny Maintenance Period that is applied to resource to indicate when maintenance is forbidden. The protocol supports zero-to-many such periods, but the current SLM Rollout implementation only supports zero-to-one. */
		denyMaintenancePeriods: Option[List[Schema.DenyMaintenancePeriod]] = None
	)
	
	case class MaintenanceWindow(
	  /** Daily cycle. */
		dailyCycle: Option[Schema.DailyCycle] = None,
	  /** Weekly cycle. */
		weeklyCycle: Option[Schema.WeeklyCycle] = None
	)
	
	case class DailyCycle(
	  /** Time within the day to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class WeeklyCycle(
	  /** User can specify multiple windows in a week. Minimum of 1 window. */
		schedule: Option[List[Schema.Schedule]] = None
	)
	
	object Schedule {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class Schedule(
	  /** Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.Schedule.DayEnum] = None,
	  /** Time within the window to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** Deny period start date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be the same or after the start. */
		startDate: Option[Schema.Date] = None,
	  /** Deny period end date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be before the end. */
		endDate: Option[Schema.Date] = None,
	  /** Time in UTC when the Blackout period starts on start_date and ends on end_date. This can be: &#42; Full time. &#42; All zeros for 00:00:00 UTC */
		time: Option[Schema.TimeOfDay] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter(
	  /** Optional. Array of string values. e.g. instance's replica information. */
		values: Option[List[String]] = None
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
