package com.boresjo.play.api.google.redis

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
	  /** { `createTime`: The time the operation was created. `endTime`: The time the operation finished running. `target`: Server-defined resource path for the target of the operation. `verb`: Name of the verb executed by the operation. `statusDetail`: Human-readable status of the operation, if any. `cancelRequested`: Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. `apiVersion`: API version used to start the operation. } */
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
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Full resource name for the region. For example: "projects/example-project/locations/us-east1". */
		name: Option[String] = None,
	  /** Resource ID for the region. For example: "us-east1". */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The set of available zones in the location. The map is keyed by the lowercase ID of each zone, as defined by Compute Engine. These keys can be specified in `location_id` or `alternative_location_id` fields when creating a Redis instance. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListClustersResponse(
	  /** A list of Redis clusters in the project in the specified location, or across all locations. If the `location_id` in the parent field of the request is "-", all regions available to the project are queried, and the results aggregated. If in such an aggregated query a location is unavailable, a placeholder Redis entry is included in the response with the `name` field set to a value of the form `projects/{project_id}/locations/{location_id}/clusters/`- and the `status` field set to ERROR and `status_message` field set to "location not available for ListClusters". */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Cluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, UPDATING, DELETING }
		enum AuthorizationModeEnum extends Enum[AuthorizationModeEnum] { case AUTH_MODE_UNSPECIFIED, AUTH_MODE_IAM_AUTH, AUTH_MODE_DISABLED }
		enum TransitEncryptionModeEnum extends Enum[TransitEncryptionModeEnum] { case TRANSIT_ENCRYPTION_MODE_UNSPECIFIED, TRANSIT_ENCRYPTION_MODE_DISABLED, TRANSIT_ENCRYPTION_MODE_SERVER_AUTHENTICATION }
		enum NodeTypeEnum extends Enum[NodeTypeEnum] { case NODE_TYPE_UNSPECIFIED, REDIS_SHARED_CORE_NANO, REDIS_HIGHMEM_MEDIUM, REDIS_HIGHMEM_XLARGE, REDIS_STANDARD_SMALL }
	}
	case class Cluster(
	  /** Required. Identifier. Unique name of the resource in this scope including project and location using the form: `projects/{project_id}/locations/{location_id}/clusters/{cluster_id}` */
		name: Option[String] = None,
	  /** Output only. The timestamp associated with the cluster creation request. */
		createTime: Option[String] = None,
	  /** Output only. The current state of this cluster. Can be CREATING, READY, UPDATING, DELETING and SUSPENDED */
		state: Option[Schema.Cluster.StateEnum] = None,
	  /** Output only. System assigned, unique identifier for the cluster. */
		uid: Option[String] = None,
	  /** Optional. The number of replica nodes per shard. */
		replicaCount: Option[Int] = None,
	  /** Optional. The authorization mode of the Redis cluster. If not provided, auth feature is disabled for the cluster. */
		authorizationMode: Option[Schema.Cluster.AuthorizationModeEnum] = None,
	  /** Optional. The in-transit encryption for the Redis cluster. If not provided, encryption is disabled for the cluster. */
		transitEncryptionMode: Option[Schema.Cluster.TransitEncryptionModeEnum] = None,
	  /** Output only. Redis memory size in GB for the entire cluster rounded up to the next integer. */
		sizeGb: Option[Int] = None,
	  /** Optional. Number of shards for the Redis cluster. */
		shardCount: Option[Int] = None,
	  /** Optional. Each PscConfig configures the consumer network where IPs will be designated to the cluster for client access through Private Service Connect Automation. Currently, only one PscConfig is supported. */
		pscConfigs: Option[List[Schema.PscConfig]] = None,
	  /** Output only. Endpoints created on each given network, for Redis clients to connect to the cluster. Currently only one discovery endpoint is supported. */
		discoveryEndpoints: Option[List[Schema.DiscoveryEndpoint]] = None,
	  /** Output only. The list of PSC connections that are auto-created through service connectivity automation. */
		pscConnections: Option[List[Schema.PscConnection]] = None,
	  /** Output only. Additional information about the current state of the cluster. */
		stateInfo: Option[Schema.StateInfo] = None,
	  /** Optional. The type of a redis node in the cluster. NodeType determines the underlying machine-type of a redis node. */
		nodeType: Option[Schema.Cluster.NodeTypeEnum] = None,
	  /** Optional. Persistence config (RDB, AOF) for the cluster. */
		persistenceConfig: Option[Schema.ClusterPersistenceConfig] = None,
	  /** Optional. Key/Value pairs of customer overrides for mutable Redis Configs */
		redisConfigs: Option[Map[String, String]] = None,
	  /** Output only. Precise value of redis memory size in GB for the entire cluster. */
		preciseSizeGb: Option[BigDecimal] = None,
	  /** Optional. This config will be used to determine how the customer wants us to distribute cluster resources within the region. */
		zoneDistributionConfig: Option[Schema.ZoneDistributionConfig] = None,
	  /** Optional. Cross cluster replication config. */
		crossClusterReplicationConfig: Option[Schema.CrossClusterReplicationConfig] = None,
	  /** Optional. The delete operation will fail when the value is set to true. */
		deletionProtectionEnabled: Option[Boolean] = None,
	  /** Optional. ClusterMaintenancePolicy determines when to allow or deny updates. */
		maintenancePolicy: Option[Schema.ClusterMaintenancePolicy] = None,
	  /** Output only. ClusterMaintenanceSchedule Output only Published maintenance schedule. */
		maintenanceSchedule: Option[Schema.ClusterMaintenanceSchedule] = None,
	  /** Output only. Service attachment details to configure Psc connections */
		pscServiceAttachments: Option[List[Schema.PscServiceAttachment]] = None,
	  /** Optional. A list of cluster enpoints. */
		clusterEndpoints: Option[List[Schema.ClusterEndpoint]] = None
	)
	
	case class PscConfig(
	  /** Required. The network where the IP address of the discovery endpoint will be reserved, in the form of projects/{network_project}/global/networks/{network_id}. */
		network: Option[String] = None
	)
	
	case class DiscoveryEndpoint(
	  /** Output only. Address of the exposed Redis endpoint used by clients to connect to the service. The address could be either IP or hostname. */
		address: Option[String] = None,
	  /** Output only. The port number of the exposed Redis endpoint. */
		port: Option[Int] = None,
	  /** Output only. Customer configuration for where the endpoint is created and accessed from. */
		pscConfig: Option[Schema.PscConfig] = None
	)
	
	object PscConnection {
		enum PscConnectionStatusEnum extends Enum[PscConnectionStatusEnum] { case PSC_CONNECTION_STATUS_UNSPECIFIED, PSC_CONNECTION_STATUS_ACTIVE, PSC_CONNECTION_STATUS_NOT_FOUND }
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case CONNECTION_TYPE_UNSPECIFIED, CONNECTION_TYPE_DISCOVERY, CONNECTION_TYPE_PRIMARY, CONNECTION_TYPE_READER }
	}
	case class PscConnection(
	  /** Required. The PSC connection id of the forwarding rule connected to the service attachment. */
		pscConnectionId: Option[String] = None,
	  /** Required. The IP allocated on the consumer network for the PSC forwarding rule. */
		address: Option[String] = None,
	  /** Required. The URI of the consumer side forwarding rule. Example: projects/{projectNumOrId}/regions/us-east1/forwardingRules/{resourceId}. */
		forwardingRule: Option[String] = None,
	  /** Optional. Project ID of the consumer project where the forwarding rule is created in. */
		projectId: Option[String] = None,
	  /** Required. The consumer network where the IP address resides, in the form of projects/{project_id}/global/networks/{network_id}. */
		network: Option[String] = None,
	  /** Required. The service attachment which is the target of the PSC connection, in the form of projects/{project-id}/regions/{region}/serviceAttachments/{service-attachment-id}. */
		serviceAttachment: Option[String] = None,
	  /** Output only. The status of the PSC connection. Please note that this value is updated periodically. To get the latest status of a PSC connection, follow https://cloud.google.com/vpc/docs/configure-private-service-connect-services#endpoint-details. */
		pscConnectionStatus: Option[Schema.PscConnection.PscConnectionStatusEnum] = None,
	  /** Output only. Type of the PSC connection. */
		connectionType: Option[Schema.PscConnection.ConnectionTypeEnum] = None
	)
	
	case class StateInfo(
	  /** Describes ongoing update on the cluster when cluster state is UPDATING. */
		updateInfo: Option[Schema.UpdateInfo] = None
	)
	
	case class UpdateInfo(
	  /** Target number of shards for redis cluster */
		targetShardCount: Option[Int] = None,
	  /** Target number of replica nodes per shard. */
		targetReplicaCount: Option[Int] = None
	)
	
	object ClusterPersistenceConfig {
		enum ModeEnum extends Enum[ModeEnum] { case PERSISTENCE_MODE_UNSPECIFIED, DISABLED, RDB, AOF }
	}
	case class ClusterPersistenceConfig(
	  /** Optional. The mode of persistence. */
		mode: Option[Schema.ClusterPersistenceConfig.ModeEnum] = None,
	  /** Optional. RDB configuration. This field will be ignored if mode is not RDB. */
		rdbConfig: Option[Schema.RDBConfig] = None,
	  /** Optional. AOF configuration. This field will be ignored if mode is not AOF. */
		aofConfig: Option[Schema.AOFConfig] = None
	)
	
	object RDBConfig {
		enum RdbSnapshotPeriodEnum extends Enum[RdbSnapshotPeriodEnum] { case SNAPSHOT_PERIOD_UNSPECIFIED, ONE_HOUR, SIX_HOURS, TWELVE_HOURS, TWENTY_FOUR_HOURS }
	}
	case class RDBConfig(
	  /** Optional. Period between RDB snapshots. */
		rdbSnapshotPeriod: Option[Schema.RDBConfig.RdbSnapshotPeriodEnum] = None,
	  /** Optional. The time that the first snapshot was/will be attempted, and to which future snapshots will be aligned. If not provided, the current time will be used. */
		rdbSnapshotStartTime: Option[String] = None
	)
	
	object AOFConfig {
		enum AppendFsyncEnum extends Enum[AppendFsyncEnum] { case APPEND_FSYNC_UNSPECIFIED, NO, EVERYSEC, ALWAYS }
	}
	case class AOFConfig(
	  /** Optional. fsync configuration. */
		appendFsync: Option[Schema.AOFConfig.AppendFsyncEnum] = None
	)
	
	object ZoneDistributionConfig {
		enum ModeEnum extends Enum[ModeEnum] { case ZONE_DISTRIBUTION_MODE_UNSPECIFIED, MULTI_ZONE, SINGLE_ZONE }
	}
	case class ZoneDistributionConfig(
	  /** Optional. The mode of zone distribution. Defaults to MULTI_ZONE, when not specified. */
		mode: Option[Schema.ZoneDistributionConfig.ModeEnum] = None,
	  /** Optional. When SINGLE ZONE distribution is selected, zone field would be used to allocate all resources in that zone. This is not applicable to MULTI_ZONE, and would be ignored for MULTI_ZONE clusters. */
		zone: Option[String] = None
	)
	
	object CrossClusterReplicationConfig {
		enum ClusterRoleEnum extends Enum[ClusterRoleEnum] { case CLUSTER_ROLE_UNSPECIFIED, NONE, PRIMARY, SECONDARY }
	}
	case class CrossClusterReplicationConfig(
	  /** The role of the cluster in cross cluster replication. */
		clusterRole: Option[Schema.CrossClusterReplicationConfig.ClusterRoleEnum] = None,
	  /** Details of the primary cluster that is used as the replication source for this secondary cluster. This field is only set for a secondary cluster. */
		primaryCluster: Option[Schema.RemoteCluster] = None,
	  /** List of secondary clusters that are replicating from this primary cluster. This field is only set for a primary cluster. */
		secondaryClusters: Option[List[Schema.RemoteCluster]] = None,
	  /** Output only. The last time cross cluster replication config was updated. */
		updateTime: Option[String] = None,
	  /** Output only. An output only view of all the member clusters participating in the cross cluster replication. This view will be provided by every member cluster irrespective of its cluster role(primary or secondary). A primary cluster can provide information about all the secondary clusters replicating from it. However, a secondary cluster only knows about the primary cluster from which it is replicating. However, for scenarios, where the primary cluster is unavailable(e.g. regional outage), a GetCluster request can be sent to any other member cluster and this field will list all the member clusters participating in cross cluster replication. */
		membership: Option[Schema.Membership] = None
	)
	
	case class RemoteCluster(
	  /** The full resource path of the remote cluster in the format: projects//locations//clusters/ */
		cluster: Option[String] = None,
	  /** Output only. The unique identifier of the remote cluster. */
		uid: Option[String] = None
	)
	
	case class Membership(
	  /** Output only. The primary cluster that acts as the source of replication for the secondary clusters. */
		primaryCluster: Option[Schema.RemoteCluster] = None,
	  /** Output only. The list of secondary clusters replicating from the primary cluster. */
		secondaryClusters: Option[List[Schema.RemoteCluster]] = None
	)
	
	case class ClusterMaintenancePolicy(
	  /** Output only. The time when the policy was created i.e. Maintenance Window or Deny Period was assigned. */
		createTime: Option[String] = None,
	  /** Output only. The time when the policy was updated i.e. Maintenance Window or Deny Period was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Maintenance window that is applied to resources covered by this policy. Minimum 1. For the current version, the maximum number of weekly_maintenance_window is expected to be one. */
		weeklyMaintenanceWindow: Option[List[Schema.ClusterWeeklyMaintenanceWindow]] = None
	)
	
	object ClusterWeeklyMaintenanceWindow {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class ClusterWeeklyMaintenanceWindow(
	  /** Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.ClusterWeeklyMaintenanceWindow.DayEnum] = None,
	  /** Start time of the window in UTC. */
		startTime: Option[Schema.TimeOfDay] = None
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
	
	case class ClusterMaintenanceSchedule(
	  /** Output only. The start time of any upcoming scheduled maintenance for this instance. */
		startTime: Option[String] = None,
	  /** Output only. The end time of any upcoming scheduled maintenance for this instance. */
		endTime: Option[String] = None
	)
	
	object PscServiceAttachment {
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case CONNECTION_TYPE_UNSPECIFIED, CONNECTION_TYPE_DISCOVERY, CONNECTION_TYPE_PRIMARY, CONNECTION_TYPE_READER }
	}
	case class PscServiceAttachment(
	  /** Output only. Service attachment URI which your self-created PscConnection should use as target */
		serviceAttachment: Option[String] = None,
	  /** Output only. Type of a PSC connection targeting this service attachment. */
		connectionType: Option[Schema.PscServiceAttachment.ConnectionTypeEnum] = None
	)
	
	case class ClusterEndpoint(
	  /** A group of PSC connections. They are created in the same VPC network, one for each service attachment in the cluster. */
		connections: Option[List[Schema.ConnectionDetail]] = None
	)
	
	case class ConnectionDetail(
	  /** Detailed information of a PSC connection that is created by the customer who owns the cluster. */
		pscConnection: Option[Schema.PscConnection] = None
	)
	
	case class CertificateAuthority(
		managedServerCa: Option[Schema.ManagedCertificateAuthority] = None,
	  /** Identifier. Unique name of the resource in this scope including project, location and cluster using the form: `projects/{project}/locations/{location}/clusters/{cluster}/certificateAuthority` */
		name: Option[String] = None
	)
	
	case class ManagedCertificateAuthority(
	  /** The PEM encoded CA certificate chains for redis managed server authentication */
		caCerts: Option[List[Schema.CertChain]] = None
	)
	
	case class CertChain(
	  /** The certificates that form the CA chain, from leaf to root order. */
		certificates: Option[List[String]] = None
	)
	
	object RescheduleClusterMaintenanceRequest {
		enum RescheduleTypeEnum extends Enum[RescheduleTypeEnum] { case RESCHEDULE_TYPE_UNSPECIFIED, IMMEDIATE, SPECIFIC_TIME }
	}
	case class RescheduleClusterMaintenanceRequest(
	  /** Required. If reschedule type is SPECIFIC_TIME, must set up schedule_time as well. */
		rescheduleType: Option[Schema.RescheduleClusterMaintenanceRequest.RescheduleTypeEnum] = None,
	  /** Optional. Timestamp when the maintenance shall be rescheduled to if reschedule_type=SPECIFIC_TIME, in RFC 3339 format, for example `2012-11-15T16:19:00.094Z`. */
		scheduleTime: Option[String] = None
	)
	
	case class ListInstancesResponse(
	  /** A list of Redis instances in the project in the specified location, or across all locations. If the `location_id` in the parent field of the request is "-", all regions available to the project are queried, and the results aggregated. If in such an aggregated query a location is unavailable, a placeholder Redis entry is included in the response with the `name` field set to a value of the form `projects/{project_id}/locations/{location_id}/instances/`- and the `status` field set to ERROR and `status_message` field set to "location not available for ListInstances". */
		instances: Option[List[Schema.Instance]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, DELETING, REPAIRING, MAINTENANCE, IMPORTING, FAILING_OVER }
		enum TierEnum extends Enum[TierEnum] { case TIER_UNSPECIFIED, BASIC, STANDARD_HA }
		enum ConnectModeEnum extends Enum[ConnectModeEnum] { case CONNECT_MODE_UNSPECIFIED, DIRECT_PEERING, PRIVATE_SERVICE_ACCESS }
		enum TransitEncryptionModeEnum extends Enum[TransitEncryptionModeEnum] { case TRANSIT_ENCRYPTION_MODE_UNSPECIFIED, SERVER_AUTHENTICATION, DISABLED }
		enum ReadReplicasModeEnum extends Enum[ReadReplicasModeEnum] { case READ_REPLICAS_MODE_UNSPECIFIED, READ_REPLICAS_DISABLED, READ_REPLICAS_ENABLED }
		enum SuspensionReasonsEnum extends Enum[SuspensionReasonsEnum] { case SUSPENSION_REASON_UNSPECIFIED, CUSTOMER_MANAGED_KEY_ISSUE }
	}
	case class Instance(
	  /** Required. Unique name of the resource in this scope including project and location using the form: `projects/{project_id}/locations/{location_id}/instances/{instance_id}` Note: Redis instances are managed and addressed at regional level so location_id here refers to a GCP region; however, users may choose which specific zone (or collection of zones for cross-zone instances) an instance should be provisioned in. Refer to location_id and alternative_location_id fields for more details. */
		name: Option[String] = None,
	  /** An arbitrary and optional user-provided name for the instance. */
		displayName: Option[String] = None,
	  /** Resource labels to represent user provided metadata */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The zone where the instance will be provisioned. If not provided, the service will choose a zone from the specified region for the instance. For standard tier, additional nodes will be added across multiple zones for protection against zonal failures. If specified, at least one node will be provisioned in this zone. */
		locationId: Option[String] = None,
	  /** Optional. If specified, at least one node will be provisioned in this zone in addition to the zone specified in location_id. Only applicable to standard tier. If provided, it must be a different zone from the one provided in [location_id]. Additional nodes beyond the first 2 will be placed in zones selected by the service. */
		alternativeLocationId: Option[String] = None,
	  /** Optional. The version of Redis software. If not provided, latest supported version will be used. Currently, the supported values are: &#42; `REDIS_3_2` for Redis 3.2 compatibility &#42; `REDIS_4_0` for Redis 4.0 compatibility (default) &#42; `REDIS_5_0` for Redis 5.0 compatibility &#42; `REDIS_6_X` for Redis 6.x compatibility &#42; `REDIS_7_0` for Redis 7.0 compatibility */
		redisVersion: Option[String] = None,
	  /** Optional. For DIRECT_PEERING mode, the CIDR range of internal addresses that are reserved for this instance. Range must be unique and non-overlapping with existing subnets in an authorized network. For PRIVATE_SERVICE_ACCESS mode, the name of one allocated IP address ranges associated with this private service access connection. If not provided, the service will choose an unused /29 block, for example, 10.0.0.0/29 or 192.168.0.0/29. For READ_REPLICAS_ENABLED the default block size is /28. */
		reservedIpRange: Option[String] = None,
	  /** Optional. Additional IP range for node placement. Required when enabling read replicas on an existing instance. For DIRECT_PEERING mode value must be a CIDR range of size /28, or "auto". For PRIVATE_SERVICE_ACCESS mode value must be the name of an allocated address range associated with the private service access connection, or "auto". */
		secondaryIpRange: Option[String] = None,
	  /** Output only. Hostname or IP address of the exposed Redis endpoint used by clients to connect to the service. */
		host: Option[String] = None,
	  /** Output only. The port number of the exposed Redis endpoint. */
		port: Option[Int] = None,
	  /** Output only. The current zone where the Redis primary node is located. In basic tier, this will always be the same as [location_id]. In standard tier, this can be the zone of any node in the instance. */
		currentLocationId: Option[String] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The current state of this instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. Additional information about the current status of this instance, if available. */
		statusMessage: Option[String] = None,
	  /** Optional. Redis configuration parameters, according to http://redis.io/topics/config. Currently, the only supported parameters are: Redis version 3.2 and newer: &#42; maxmemory-policy &#42; notify-keyspace-events Redis version 4.0 and newer: &#42; activedefrag &#42; lfu-decay-time &#42; lfu-log-factor &#42; maxmemory-gb Redis version 5.0 and newer: &#42; stream-node-max-bytes &#42; stream-node-max-entries */
		redisConfigs: Option[Map[String, String]] = None,
	  /** Required. The service tier of the instance. */
		tier: Option[Schema.Instance.TierEnum] = None,
	  /** Required. Redis memory size in GiB. */
		memorySizeGb: Option[Int] = None,
	  /** Optional. The full name of the Google Compute Engine [network](https://cloud.google.com/vpc/docs/vpc) to which the instance is connected. If left unspecified, the `default` network will be used. */
		authorizedNetwork: Option[String] = None,
	  /** Output only. Cloud IAM identity used by import / export operations to transfer data to/from Cloud Storage. Format is "serviceAccount:". The value may change over time for a given instance so should be checked before each import/export operation. */
		persistenceIamIdentity: Option[String] = None,
	  /** Optional. The network connect mode of the Redis instance. If not provided, the connect mode defaults to DIRECT_PEERING. */
		connectMode: Option[Schema.Instance.ConnectModeEnum] = None,
	  /** Optional. Indicates whether OSS Redis AUTH is enabled for the instance. If set to "true" AUTH is enabled on the instance. Default value is "false" meaning AUTH is disabled. */
		authEnabled: Option[Boolean] = None,
	  /** Output only. List of server CA certificates for the instance. */
		serverCaCerts: Option[List[Schema.TlsCertificate]] = None,
	  /** Optional. The TLS mode of the Redis instance. If not provided, TLS is disabled for the instance. */
		transitEncryptionMode: Option[Schema.Instance.TransitEncryptionModeEnum] = None,
	  /** Optional. The maintenance policy for the instance. If not provided, maintenance events can be performed at any time. */
		maintenancePolicy: Option[Schema.MaintenancePolicy] = None,
	  /** Output only. Date and time of upcoming maintenance events which have been scheduled. */
		maintenanceSchedule: Option[Schema.MaintenanceSchedule] = None,
	  /** Optional. The number of replica nodes. The valid range for the Standard Tier with read replicas enabled is [1-5] and defaults to 2. If read replicas are not enabled for a Standard Tier instance, the only valid value is 1 and the default is 1. The valid value for basic tier is 0 and the default is also 0. */
		replicaCount: Option[Int] = None,
	  /** Output only. Info per node. */
		nodes: Option[List[Schema.NodeInfo]] = None,
	  /** Output only. Hostname or IP address of the exposed readonly Redis endpoint. Standard tier only. Targets all healthy replica nodes in instance. Replication is asynchronous and replica nodes will exhibit some lag behind the primary. Write requests must target 'host'. */
		readEndpoint: Option[String] = None,
	  /** Output only. The port number of the exposed readonly redis endpoint. Standard tier only. Write requests should target 'port'. */
		readEndpointPort: Option[Int] = None,
	  /** Optional. Read replicas mode for the instance. Defaults to READ_REPLICAS_DISABLED. */
		readReplicasMode: Option[Schema.Instance.ReadReplicasModeEnum] = None,
	  /** Optional. The KMS key reference that the customer provides when trying to create the instance. */
		customerManagedKey: Option[String] = None,
	  /** Optional. Persistence configuration parameters */
		persistenceConfig: Option[Schema.PersistenceConfig] = None,
	  /** Optional. reasons that causes instance in "SUSPENDED" state. */
		suspensionReasons: Option[List[Schema.Instance.SuspensionReasonsEnum]] = None,
	  /** Optional. The self service update maintenance version. The version is date based such as "20210712_00_00". */
		maintenanceVersion: Option[String] = None,
	  /** Optional. The available maintenance versions that an instance could update to. */
		availableMaintenanceVersions: Option[List[String]] = None,
	  /** Optional. Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	case class TlsCertificate(
	  /** Serial number, as extracted from the certificate. */
		serialNumber: Option[String] = None,
	  /** PEM representation. */
		cert: Option[String] = None,
	  /** Output only. The time when the certificate was created in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2020-05-18T00:00:00.094Z`. */
		createTime: Option[String] = None,
	  /** Output only. The time when the certificate expires in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2020-05-18T00:00:00.094Z`. */
		expireTime: Option[String] = None,
	  /** Sha1 Fingerprint of the certificate. */
		sha1Fingerprint: Option[String] = None
	)
	
	case class MaintenancePolicy(
	  /** Output only. The time when the policy was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the policy was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Optional. Maintenance window that is applied to resources covered by this policy. Minimum 1. For the current version, the maximum number of weekly_window is expected to be one. */
		weeklyMaintenanceWindow: Option[List[Schema.WeeklyMaintenanceWindow]] = None
	)
	
	object WeeklyMaintenanceWindow {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeeklyMaintenanceWindow(
	  /** Required. The day of week that maintenance updates occur. */
		day: Option[Schema.WeeklyMaintenanceWindow.DayEnum] = None,
	  /** Required. Start time of the window in UTC time. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the maintenance window. The current window is fixed at 1 hour. */
		duration: Option[String] = None
	)
	
	case class MaintenanceSchedule(
	  /** Output only. The start time of any upcoming scheduled maintenance for this instance. */
		startTime: Option[String] = None,
	  /** Output only. The end time of any upcoming scheduled maintenance for this instance. */
		endTime: Option[String] = None,
	  /** If the scheduled maintenance can be rescheduled, default is true. */
		canReschedule: Option[Boolean] = None,
	  /** Output only. The deadline that the maintenance schedule start time can not go beyond, including reschedule. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class NodeInfo(
	  /** Output only. Node identifying string. e.g. 'node-0', 'node-1' */
		id: Option[String] = None,
	  /** Output only. Location of the node. */
		zone: Option[String] = None
	)
	
	object PersistenceConfig {
		enum PersistenceModeEnum extends Enum[PersistenceModeEnum] { case PERSISTENCE_MODE_UNSPECIFIED, DISABLED, RDB }
		enum RdbSnapshotPeriodEnum extends Enum[RdbSnapshotPeriodEnum] { case SNAPSHOT_PERIOD_UNSPECIFIED, ONE_HOUR, SIX_HOURS, TWELVE_HOURS, TWENTY_FOUR_HOURS }
	}
	case class PersistenceConfig(
	  /** Optional. Controls whether Persistence features are enabled. If not provided, the existing value will be used. */
		persistenceMode: Option[Schema.PersistenceConfig.PersistenceModeEnum] = None,
	  /** Optional. Period between RDB snapshots. Snapshots will be attempted every period starting from the provided snapshot start time. For example, a start time of 01/01/2033 06:45 and SIX_HOURS snapshot period will do nothing until 01/01/2033, and then trigger snapshots every day at 06:45, 12:45, 18:45, and 00:45 the next day, and so on. If not provided, TWENTY_FOUR_HOURS will be used as default. */
		rdbSnapshotPeriod: Option[Schema.PersistenceConfig.RdbSnapshotPeriodEnum] = None,
	  /** Output only. The next time that a snapshot attempt is scheduled to occur. */
		rdbNextSnapshotTime: Option[String] = None,
	  /** Optional. Date and time that the first snapshot was/will be attempted, and to which future snapshots will be aligned. If not provided, the current time will be used. */
		rdbSnapshotStartTime: Option[String] = None
	)
	
	case class InstanceAuthString(
	  /** AUTH string set on the instance. */
		authString: Option[String] = None
	)
	
	case class UpgradeInstanceRequest(
	  /** Required. Specifies the target version of Redis software to upgrade to. */
		redisVersion: Option[String] = None
	)
	
	case class ImportInstanceRequest(
	  /** Required. Specify data to be imported. */
		inputConfig: Option[Schema.InputConfig] = None
	)
	
	case class InputConfig(
	  /** Google Cloud Storage location where input content is located. */
		gcsSource: Option[Schema.GcsSource] = None
	)
	
	case class GcsSource(
	  /** Required. Source data URI. (e.g. 'gs://my_bucket/my_object'). */
		uri: Option[String] = None
	)
	
	case class ExportInstanceRequest(
	  /** Required. Specify data to be exported. */
		outputConfig: Option[Schema.OutputConfig] = None
	)
	
	case class OutputConfig(
	  /** Google Cloud Storage destination for output content. */
		gcsDestination: Option[Schema.GcsDestination] = None
	)
	
	case class GcsDestination(
	  /** Required. Data destination URI (e.g. 'gs://my_bucket/my_object'). Existing files will be overwritten. */
		uri: Option[String] = None
	)
	
	object FailoverInstanceRequest {
		enum DataProtectionModeEnum extends Enum[DataProtectionModeEnum] { case DATA_PROTECTION_MODE_UNSPECIFIED, LIMITED_DATA_LOSS, FORCE_DATA_LOSS }
	}
	case class FailoverInstanceRequest(
	  /** Optional. Available data protection modes that the user can choose. If it's unspecified, data protection mode will be LIMITED_DATA_LOSS by default. */
		dataProtectionMode: Option[Schema.FailoverInstanceRequest.DataProtectionModeEnum] = None
	)
	
	object RescheduleMaintenanceRequest {
		enum RescheduleTypeEnum extends Enum[RescheduleTypeEnum] { case RESCHEDULE_TYPE_UNSPECIFIED, IMMEDIATE, NEXT_AVAILABLE_WINDOW, SPECIFIC_TIME }
	}
	case class RescheduleMaintenanceRequest(
	  /** Required. If reschedule type is SPECIFIC_TIME, must set up schedule_time as well. */
		rescheduleType: Option[Schema.RescheduleMaintenanceRequest.RescheduleTypeEnum] = None,
	  /** Optional. Timestamp when the maintenance shall be rescheduled to if reschedule_type=SPECIFIC_TIME, in RFC 3339 format, for example `2012-11-15T16:19:00.094Z`. */
		scheduleTime: Option[String] = None
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
	
	object DatabaseResourceFeed {
		enum FeedTypeEnum extends Enum[FeedTypeEnum] { case FEEDTYPE_UNSPECIFIED, RESOURCE_METADATA, OBSERVABILITY_DATA, SECURITY_FINDING_DATA, RECOMMENDATION_SIGNAL_DATA }
	}
	case class DatabaseResourceFeed(
	  /** Primary key associated with the Resource. resource_id is available in individual feed level as well. */
		resourceId: Option[Schema.DatabaseResourceId] = None,
	  /** Required. Timestamp when feed is generated. */
		feedTimestamp: Option[String] = None,
	  /** Required. Type feed to be ingested into condor */
		feedType: Option[Schema.DatabaseResourceFeed.FeedTypeEnum] = None,
		resourceMetadata: Option[Schema.DatabaseResourceMetadata] = None,
		resourceHealthSignalData: Option[Schema.DatabaseResourceHealthSignalData] = None,
		recommendationSignalData: Option[Schema.DatabaseResourceRecommendationSignalData] = None,
		observabilityMetricData: Option[Schema.ObservabilityMetricData] = None
	)
	
	object DatabaseResourceId {
		enum ProviderEnum extends Enum[ProviderEnum] { case PROVIDER_UNSPECIFIED, GCP, AWS, AZURE, ONPREM, SELFMANAGED, PROVIDER_OTHER }
	}
	case class DatabaseResourceId(
	  /** Required. Cloud provider name. Ex: GCP/AWS/Azure/OnPrem/SelfManaged */
		provider: Option[Schema.DatabaseResourceId.ProviderEnum] = None,
	  /** Optional. Needs to be used only when the provider is PROVIDER_OTHER. */
		providerDescription: Option[String] = None,
	  /** Required. A service-local token that distinguishes this resource from other resources within the same service. */
		uniqueId: Option[String] = None,
	  /** Required. The type of resource this ID is identifying. Ex redis.googleapis.com/Instance, redis.googleapis.com/Cluster, alloydb.googleapis.com/Cluster, alloydb.googleapis.com/Instance, spanner.googleapis.com/Instance, spanner.googleapis.com/Database, firestore.googleapis.com/Database, sqladmin.googleapis.com/Instance, bigtableadmin.googleapis.com/Cluster, bigtableadmin.googleapis.com/Instance REQUIRED Please refer go/condor-common-datamodel */
		resourceType: Option[String] = None
	)
	
	object DatabaseResourceMetadata {
		enum ExpectedStateEnum extends Enum[ExpectedStateEnum] { case STATE_UNSPECIFIED, HEALTHY, UNHEALTHY, SUSPENDED, DELETED, STATE_OTHER }
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case STATE_UNSPECIFIED, HEALTHY, UNHEALTHY, SUSPENDED, DELETED, STATE_OTHER }
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, SUB_RESOURCE_TYPE_UNSPECIFIED, PRIMARY, SECONDARY, READ_REPLICA, OTHER, SUB_RESOURCE_TYPE_PRIMARY, SUB_RESOURCE_TYPE_SECONDARY, SUB_RESOURCE_TYPE_READ_REPLICA, SUB_RESOURCE_TYPE_OTHER }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, EDITION_ENTERPRISE, EDITION_ENTERPRISE_PLUS }
	}
	case class DatabaseResourceMetadata(
	  /** Required. Unique identifier for a Database resource */
		id: Option[Schema.DatabaseResourceId] = None,
	  /** Required. Different from DatabaseResourceId.unique_id, a resource name can be reused over time. That is, after a resource named "ABC" is deleted, the name "ABC" can be used to to create a new resource within the same source. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Identifier for this resource's immediate parent/primary resource if the current resource is a replica or derived form of another Database resource. Else it would be NULL. REQUIRED if the immediate parent exists when first time resource is getting ingested, otherwise optional. */
		primaryResourceId: Option[Schema.DatabaseResourceId] = None,
	  /** Primary resource location. REQUIRED if the immediate parent exists when first time resource is getting ingested, otherwise optional. */
		primaryResourceLocation: Option[String] = None,
	  /** Closest parent Cloud Resource Manager container of this resource. It must be resource name of a Cloud Resource Manager project with the format of "/", such as "projects/123". For GCP provided resources, number should be project number. */
		resourceContainer: Option[String] = None,
	  /** The resource location. REQUIRED */
		location: Option[String] = None,
	  /** The creation time of the resource, i.e. the time when resource is created and recorded in partner service. */
		creationTime: Option[String] = None,
	  /** The time at which the resource was updated and recorded at partner service. */
		updationTime: Option[String] = None,
	  /** The state that the instance is expected to be in. For example, an instance state can transition to UNHEALTHY due to wrong patch update, while the expected state will remain at the HEALTHY. */
		expectedState: Option[Schema.DatabaseResourceMetadata.ExpectedStateEnum] = None,
	  /** Current state of the instance. */
		currentState: Option[Schema.DatabaseResourceMetadata.CurrentStateEnum] = None,
	  /** The type of the instance. Specified at creation time. */
		instanceType: Option[Schema.DatabaseResourceMetadata.InstanceTypeEnum] = None,
	  /** The product this resource represents. */
		product: Option[Schema.Product] = None,
	  /** Availability configuration for this instance */
		availabilityConfiguration: Option[Schema.AvailabilityConfiguration] = None,
	  /** Backup configuration for this instance */
		backupConfiguration: Option[Schema.BackupConfiguration] = None,
	  /** Latest backup run information for this instance */
		backupRun: Option[Schema.BackupRun] = None,
	  /** Any custom metadata associated with the resource */
		customMetadata: Option[Schema.CustomMetadataData] = None,
	  /** Entitlements associated with the resource */
		entitlements: Option[List[Schema.Entitlement]] = None,
	  /** User-provided labels associated with the resource */
		userLabelSet: Option[Schema.UserLabels] = None,
	  /** Machine configuration for this resource. */
		machineConfiguration: Option[Schema.MachineConfiguration] = None,
	  /** Optional. Tags associated with this resources. */
		tagsSet: Option[Schema.Tags] = None,
	  /** Optional. Edition represents whether the instance is ENTERPRISE or ENTERPRISE_PLUS. This information is core to Cloud SQL only and is used to identify the edition of the instance. */
		edition: Option[Schema.DatabaseResourceMetadata.EditionEnum] = None
	)
	
	object Product {
		enum TypeEnum extends Enum[TypeEnum] { case PRODUCT_TYPE_UNSPECIFIED, PRODUCT_TYPE_CLOUD_SQL, CLOUD_SQL, PRODUCT_TYPE_ALLOYDB, ALLOYDB, PRODUCT_TYPE_SPANNER, PRODUCT_TYPE_ON_PREM, ON_PREM, PRODUCT_TYPE_MEMORYSTORE, PRODUCT_TYPE_BIGTABLE, PRODUCT_TYPE_OTHER, PRODUCT_TYPE_FIRESTORE }
		enum EngineEnum extends Enum[EngineEnum] { case ENGINE_UNSPECIFIED, ENGINE_MYSQL, MYSQL, ENGINE_POSTGRES, POSTGRES, ENGINE_SQL_SERVER, SQL_SERVER, ENGINE_NATIVE, NATIVE, ENGINE_CLOUD_SPANNER_WITH_POSTGRES_DIALECT, ENGINE_CLOUD_SPANNER_WITH_GOOGLESQL_DIALECT, ENGINE_MEMORYSTORE_FOR_REDIS, ENGINE_MEMORYSTORE_FOR_REDIS_CLUSTER, ENGINE_OTHER, ENGINE_FIRESTORE_WITH_NATIVE_MODE, ENGINE_FIRESTORE_WITH_DATASTORE_MODE }
	}
	case class Product(
	  /** Type of specific database product. It could be CloudSQL, AlloyDB etc.. */
		`type`: Option[Schema.Product.TypeEnum] = None,
	  /** The specific engine that the underlying database is running. */
		engine: Option[Schema.Product.EngineEnum] = None,
	  /** Version of the underlying database engine. Example values: For MySQL, it could be "8.0", "5.7" etc.. For Postgres, it could be "14", "15" etc.. */
		version: Option[String] = None
	)
	
	object AvailabilityConfiguration {
		enum AvailabilityTypeEnum extends Enum[AvailabilityTypeEnum] { case AVAILABILITY_TYPE_UNSPECIFIED, ZONAL, REGIONAL, MULTI_REGIONAL, AVAILABILITY_TYPE_OTHER }
	}
	case class AvailabilityConfiguration(
	  /** Availability type. Potential values: &#42; `ZONAL`: The instance serves data from only one zone. Outages in that zone affect data accessibility. &#42; `REGIONAL`: The instance can serve data from more than one zone in a region (it is highly available). */
		availabilityType: Option[Schema.AvailabilityConfiguration.AvailabilityTypeEnum] = None,
		externalReplicaConfigured: Option[Boolean] = None,
		promotableReplicaConfigured: Option[Boolean] = None,
	  /** Checks for resources that are configured to have redundancy, and ongoing replication across regions */
		crossRegionReplicaConfigured: Option[Boolean] = None,
	  /** Checks for existence of (multi-cluster) routing configuration that allows automatic failover to a different zone/region in case of an outage. Applicable to Bigtable resources. */
		automaticFailoverRoutingConfigured: Option[Boolean] = None
	)
	
	case class BackupConfiguration(
	  /** Whether customer visible automated backups are enabled on the instance. */
		automatedBackupEnabled: Option[Boolean] = None,
	  /** Backup retention settings. */
		backupRetentionSettings: Option[Schema.RetentionSettings] = None,
	  /** Whether point-in-time recovery is enabled. This is optional field, if the database service does not have this feature or metadata is not available in control plane, this can be omitted. */
		pointInTimeRecoveryEnabled: Option[Boolean] = None
	)
	
	object RetentionSettings {
		enum RetentionUnitEnum extends Enum[RetentionUnitEnum] { case RETENTION_UNIT_UNSPECIFIED, COUNT, TIME, DURATION, RETENTION_UNIT_OTHER }
	}
	case class RetentionSettings(
	  /** The unit that 'retained_backups' represents. */
		retentionUnit: Option[Schema.RetentionSettings.RetentionUnitEnum] = None,
		timeBasedRetention: Option[String] = None,
		quantityBasedRetention: Option[Int] = None,
	  /** Duration based retention period i.e. 172800 seconds (2 days) */
		durationBasedRetention: Option[String] = None,
	  /** Timestamp based retention period i.e. 2024-05-01T00:00:00Z */
		timestampBasedRetentionTime: Option[String] = None
	)
	
	object BackupRun {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, SUCCESSFUL, FAILED }
	}
	case class BackupRun(
	  /** The time the backup operation started. REQUIRED */
		startTime: Option[String] = None,
	  /** The time the backup operation completed. REQUIRED */
		endTime: Option[String] = None,
	  /** The status of this run. REQUIRED */
		status: Option[Schema.BackupRun.StatusEnum] = None,
	  /** Information about why the backup operation failed. This is only present if the run has the FAILED status. OPTIONAL */
		error: Option[Schema.OperationError] = None
	)
	
	object OperationError {
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case OPERATION_ERROR_TYPE_UNSPECIFIED, KMS_KEY_ERROR, DATABASE_ERROR, STOCKOUT_ERROR, CANCELLATION_ERROR, SQLSERVER_ERROR, INTERNAL_ERROR }
	}
	case class OperationError(
	  /** Identifies the specific error that occurred. REQUIRED */
		code: Option[String] = None,
	  /** Additional information about the error encountered. REQUIRED */
		message: Option[String] = None,
		errorType: Option[Schema.OperationError.ErrorTypeEnum] = None
	)
	
	case class CustomMetadataData(
	  /** Metadata for individual internal resources in an instance. e.g. spanner instance can have multiple databases with unique configuration. */
		internalResourceMetadata: Option[List[Schema.InternalResourceMetadata]] = None
	)
	
	case class InternalResourceMetadata(
		resourceId: Option[Schema.DatabaseResourceId] = None,
	  /** Required. internal resource name for spanner this will be database name e.g."spanner.googleapis.com/projects/123/abc/instances/inst1/databases/db1" */
		resourceName: Option[String] = None,
		product: Option[Schema.Product] = None,
	  /** Backup configuration for this database */
		backupConfiguration: Option[Schema.BackupConfiguration] = None,
	  /** Information about the last backup attempt for this database */
		backupRun: Option[Schema.BackupRun] = None
	)
	
	object Entitlement {
		enum TypeEnum extends Enum[TypeEnum] { case ENTITLEMENT_TYPE_UNSPECIFIED, GEMINI }
		enum EntitlementStateEnum extends Enum[EntitlementStateEnum] { case ENTITLEMENT_STATE_UNSPECIFIED, ENTITLED, REVOKED }
	}
	case class Entitlement(
	  /** An enum that represents the type of this entitlement. */
		`type`: Option[Schema.Entitlement.TypeEnum] = None,
	  /** The current state of user's accessibility to a feature/benefit. */
		entitlementState: Option[Schema.Entitlement.EntitlementStateEnum] = None
	)
	
	case class UserLabels(
		labels: Option[Map[String, String]] = None
	)
	
	case class MachineConfiguration(
	  /** The number of CPUs. TODO(b/342344482, b/342346271) add proto validations again after bug fix. */
		cpuCount: Option[Int] = None,
	  /** Memory size in bytes. TODO(b/342344482, b/342346271) add proto validations again after bug fix. */
		memorySizeInBytes: Option[String] = None,
	  /** Optional. Number of shards (if applicable). */
		shardCount: Option[Int] = None
	)
	
	case class Tags(
	  /** The Tag key/value mappings. */
		tags: Option[Map[String, String]] = None
	)
	
	object DatabaseResourceHealthSignalData {
		enum ProviderEnum extends Enum[ProviderEnum] { case PROVIDER_UNSPECIFIED, GCP, AWS, AZURE, ONPREM, SELFMANAGED, PROVIDER_OTHER }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, RESOLVED, MUTED }
		enum SignalClassEnum extends Enum[SignalClassEnum] { case CLASS_UNSPECIFIED, THREAT, VULNERABILITY, MISCONFIGURATION, OBSERVATION, ERROR }
		enum SignalSeverityEnum extends Enum[SignalSeverityEnum] { case SIGNAL_SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
		enum SignalTypeEnum extends Enum[SignalTypeEnum] { case SIGNAL_TYPE_UNSPECIFIED, SIGNAL_TYPE_NOT_PROTECTED_BY_AUTOMATIC_FAILOVER, SIGNAL_TYPE_GROUP_NOT_REPLICATING_ACROSS_REGIONS, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_ZONES, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_REGIONS, SIGNAL_TYPE_NO_PROMOTABLE_REPLICA, SIGNAL_TYPE_NO_AUTOMATED_BACKUP_POLICY, SIGNAL_TYPE_SHORT_BACKUP_RETENTION, SIGNAL_TYPE_LAST_BACKUP_FAILED, SIGNAL_TYPE_LAST_BACKUP_OLD, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_2_0, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_3, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_2, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_1, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_0, SIGNAL_TYPE_VIOLATES_CIS_CONTROLS_V8_0, SIGNAL_TYPE_VIOLATES_NIST_800_53, SIGNAL_TYPE_VIOLATES_NIST_800_53_R5, SIGNAL_TYPE_VIOLATES_NIST_CYBERSECURITY_FRAMEWORK_V1_0, SIGNAL_TYPE_VIOLATES_ISO_27001, SIGNAL_TYPE_VIOLATES_ISO_27001_V2022, SIGNAL_TYPE_VIOLATES_PCI_DSS_V3_2_1, SIGNAL_TYPE_VIOLATES_PCI_DSS_V4_0, SIGNAL_TYPE_VIOLATES_CLOUD_CONTROLS_MATRIX_V4, SIGNAL_TYPE_VIOLATES_HIPAA, SIGNAL_TYPE_VIOLATES_SOC2_V2017, SIGNAL_TYPE_LOGS_NOT_OPTIMIZED_FOR_TROUBLESHOOTING, SIGNAL_TYPE_QUERY_DURATIONS_NOT_LOGGED, SIGNAL_TYPE_VERBOSE_ERROR_LOGGING, SIGNAL_TYPE_QUERY_LOCK_WAITS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_MOST_ERRORS, SIGNAL_TYPE_LOGGING_ONLY_CRITICAL_ERRORS, SIGNAL_TYPE_MINIMAL_ERROR_LOGGING, SIGNAL_TYPE_QUERY_STATISTICS_LOGGED, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_CLIENT_HOSTNAME, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PARSER_STATISTICS, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PLANNER_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_ONLY_DDL_STATEMENTS, SIGNAL_TYPE_LOGGING_QUERY_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_TEMPORARY_FILES, SIGNAL_TYPE_CONNECTION_MAX_NOT_CONFIGURED, SIGNAL_TYPE_USER_OPTIONS_CONFIGURED, SIGNAL_TYPE_EXPOSED_TO_PUBLIC_ACCESS, SIGNAL_TYPE_UNENCRYPTED_CONNECTIONS, SIGNAL_TYPE_NO_ROOT_PASSWORD, SIGNAL_TYPE_WEAK_ROOT_PASSWORD, SIGNAL_TYPE_ENCRYPTION_KEY_NOT_CUSTOMER_MANAGED, SIGNAL_TYPE_SERVER_AUTHENTICATION_NOT_REQUIRED, SIGNAL_TYPE_EXPOSED_BY_OWNERSHIP_CHAINING, SIGNAL_TYPE_EXPOSED_TO_EXTERNAL_SCRIPTS, SIGNAL_TYPE_EXPOSED_TO_LOCAL_DATA_LOADS, SIGNAL_TYPE_CONNECTION_ATTEMPTS_NOT_LOGGED, SIGNAL_TYPE_DISCONNECTIONS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_EXCESSIVE_STATEMENT_INFO, SIGNAL_TYPE_EXPOSED_TO_REMOTE_ACCESS, SIGNAL_TYPE_DATABASE_NAMES_EXPOSED, SIGNAL_TYPE_SENSITIVE_TRACE_INFO_NOT_MASKED, SIGNAL_TYPE_PUBLIC_IP_ENABLED, SIGNAL_TYPE_IDLE, SIGNAL_TYPE_OVERPROVISIONED, SIGNAL_TYPE_HIGH_NUMBER_OF_OPEN_TABLES, SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES, SIGNAL_TYPE_HIGH_TRANSACTION_ID_UTILIZATION, SIGNAL_TYPE_UNDERPROVISIONED, SIGNAL_TYPE_OUT_OF_DISK, SIGNAL_TYPE_SERVER_CERTIFICATE_NEAR_EXPIRY, SIGNAL_TYPE_DATABASE_AUDITING_DISABLED, SIGNAL_TYPE_RESTRICT_AUTHORIZED_NETWORKS, SIGNAL_TYPE_VIOLATE_POLICY_RESTRICT_PUBLIC_IP, SIGNAL_TYPE_QUOTA_LIMIT, SIGNAL_TYPE_NO_PASSWORD_POLICY, SIGNAL_TYPE_CONNECTIONS_PERFORMANCE_IMPACT, SIGNAL_TYPE_TMP_TABLES_PERFORMANCE_IMPACT, SIGNAL_TYPE_TRANS_LOGS_PERFORMANCE_IMPACT, SIGNAL_TYPE_HIGH_JOINS_WITHOUT_INDEXES, SIGNAL_TYPE_SUPERUSER_WRITING_TO_USER_TABLES, SIGNAL_TYPE_USER_GRANTED_ALL_PERMISSIONS, SIGNAL_TYPE_DATA_EXPORT_TO_EXTERNAL_CLOUD_STORAGE_BUCKET, SIGNAL_TYPE_DATA_EXPORT_TO_PUBLIC_CLOUD_STORAGE_BUCKET }
	}
	case class DatabaseResourceHealthSignalData(
	  /** Required. Unique identifier for the signal. This is an unique id which would be mainatined by partner to identify a signal. */
		signalId: Option[String] = None,
	  /** Required. The name of the signal, ex: PUBLIC_SQL_INSTANCE, SQL_LOG_ERROR_VERBOSITY etc. */
		name: Option[String] = None,
	  /** The external-uri of the signal, using which more information about this signal can be obtained. In GCP, this will take user to SCC page to get more details about signals. */
		externalUri: Option[String] = None,
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Cloud provider name. Ex: GCP/AWS/Azure/OnPrem/SelfManaged */
		provider: Option[Schema.DatabaseResourceHealthSignalData.ProviderEnum] = None,
	  /** Closest parent container of this resource. In GCP, 'container' refers to a Cloud Resource Manager project. It must be resource name of a Cloud Resource Manager project with the format of "provider//", such as "projects/123". For GCP provided resources, number should be project number. */
		resourceContainer: Option[String] = None,
	  /** Description associated with signal */
		description: Option[String] = None,
	  /** Required. The last time at which the event described by this signal took place */
		eventTime: Option[String] = None,
		state: Option[Schema.DatabaseResourceHealthSignalData.StateEnum] = None,
	  /** Required. The class of the signal, such as if it's a THREAT or VULNERABILITY. */
		signalClass: Option[Schema.DatabaseResourceHealthSignalData.SignalClassEnum] = None,
	  /** The severity of the signal, such as if it's a HIGH or LOW severity. */
		signalSeverity: Option[Schema.DatabaseResourceHealthSignalData.SignalSeverityEnum] = None,
	  /** Industry standards associated with this signal; if this signal is an issue, that could be a violation of the associated industry standard(s). For example, AUTO_BACKUP_DISABLED signal is associated with CIS GCP 1.1, CIS GCP 1.2, CIS GCP 1.3, NIST 800-53 and ISO-27001 compliance standards. If a database resource does not have automated backup enable, it will violate these following industry standards. */
		compliance: Option[List[Schema.Compliance]] = None,
	  /** Any other additional metadata */
		additionalMetadata: Option[Map[String, JsValue]] = None,
	  /** Required. Type of signal, for example, `AVAILABLE_IN_MULTIPLE_ZONES`, `LOGGING_MOST_ERRORS`, etc. */
		signalType: Option[Schema.DatabaseResourceHealthSignalData.SignalTypeEnum] = None
	)
	
	case class Compliance(
	  /** Industry-wide compliance standards or benchmarks, such as CIS, PCI, and OWASP. */
		standard: Option[String] = None,
	  /** Version of the standard or benchmark, for example, 1.1 */
		version: Option[String] = None
	)
	
	object DatabaseResourceRecommendationSignalData {
		enum SignalTypeEnum extends Enum[SignalTypeEnum] { case SIGNAL_TYPE_UNSPECIFIED, SIGNAL_TYPE_NOT_PROTECTED_BY_AUTOMATIC_FAILOVER, SIGNAL_TYPE_GROUP_NOT_REPLICATING_ACROSS_REGIONS, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_ZONES, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_REGIONS, SIGNAL_TYPE_NO_PROMOTABLE_REPLICA, SIGNAL_TYPE_NO_AUTOMATED_BACKUP_POLICY, SIGNAL_TYPE_SHORT_BACKUP_RETENTION, SIGNAL_TYPE_LAST_BACKUP_FAILED, SIGNAL_TYPE_LAST_BACKUP_OLD, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_2_0, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_3, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_2, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_1, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_0, SIGNAL_TYPE_VIOLATES_CIS_CONTROLS_V8_0, SIGNAL_TYPE_VIOLATES_NIST_800_53, SIGNAL_TYPE_VIOLATES_NIST_800_53_R5, SIGNAL_TYPE_VIOLATES_NIST_CYBERSECURITY_FRAMEWORK_V1_0, SIGNAL_TYPE_VIOLATES_ISO_27001, SIGNAL_TYPE_VIOLATES_ISO_27001_V2022, SIGNAL_TYPE_VIOLATES_PCI_DSS_V3_2_1, SIGNAL_TYPE_VIOLATES_PCI_DSS_V4_0, SIGNAL_TYPE_VIOLATES_CLOUD_CONTROLS_MATRIX_V4, SIGNAL_TYPE_VIOLATES_HIPAA, SIGNAL_TYPE_VIOLATES_SOC2_V2017, SIGNAL_TYPE_LOGS_NOT_OPTIMIZED_FOR_TROUBLESHOOTING, SIGNAL_TYPE_QUERY_DURATIONS_NOT_LOGGED, SIGNAL_TYPE_VERBOSE_ERROR_LOGGING, SIGNAL_TYPE_QUERY_LOCK_WAITS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_MOST_ERRORS, SIGNAL_TYPE_LOGGING_ONLY_CRITICAL_ERRORS, SIGNAL_TYPE_MINIMAL_ERROR_LOGGING, SIGNAL_TYPE_QUERY_STATISTICS_LOGGED, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_CLIENT_HOSTNAME, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PARSER_STATISTICS, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PLANNER_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_ONLY_DDL_STATEMENTS, SIGNAL_TYPE_LOGGING_QUERY_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_TEMPORARY_FILES, SIGNAL_TYPE_CONNECTION_MAX_NOT_CONFIGURED, SIGNAL_TYPE_USER_OPTIONS_CONFIGURED, SIGNAL_TYPE_EXPOSED_TO_PUBLIC_ACCESS, SIGNAL_TYPE_UNENCRYPTED_CONNECTIONS, SIGNAL_TYPE_NO_ROOT_PASSWORD, SIGNAL_TYPE_WEAK_ROOT_PASSWORD, SIGNAL_TYPE_ENCRYPTION_KEY_NOT_CUSTOMER_MANAGED, SIGNAL_TYPE_SERVER_AUTHENTICATION_NOT_REQUIRED, SIGNAL_TYPE_EXPOSED_BY_OWNERSHIP_CHAINING, SIGNAL_TYPE_EXPOSED_TO_EXTERNAL_SCRIPTS, SIGNAL_TYPE_EXPOSED_TO_LOCAL_DATA_LOADS, SIGNAL_TYPE_CONNECTION_ATTEMPTS_NOT_LOGGED, SIGNAL_TYPE_DISCONNECTIONS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_EXCESSIVE_STATEMENT_INFO, SIGNAL_TYPE_EXPOSED_TO_REMOTE_ACCESS, SIGNAL_TYPE_DATABASE_NAMES_EXPOSED, SIGNAL_TYPE_SENSITIVE_TRACE_INFO_NOT_MASKED, SIGNAL_TYPE_PUBLIC_IP_ENABLED, SIGNAL_TYPE_IDLE, SIGNAL_TYPE_OVERPROVISIONED, SIGNAL_TYPE_HIGH_NUMBER_OF_OPEN_TABLES, SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES, SIGNAL_TYPE_HIGH_TRANSACTION_ID_UTILIZATION, SIGNAL_TYPE_UNDERPROVISIONED, SIGNAL_TYPE_OUT_OF_DISK, SIGNAL_TYPE_SERVER_CERTIFICATE_NEAR_EXPIRY, SIGNAL_TYPE_DATABASE_AUDITING_DISABLED, SIGNAL_TYPE_RESTRICT_AUTHORIZED_NETWORKS, SIGNAL_TYPE_VIOLATE_POLICY_RESTRICT_PUBLIC_IP, SIGNAL_TYPE_QUOTA_LIMIT, SIGNAL_TYPE_NO_PASSWORD_POLICY, SIGNAL_TYPE_CONNECTIONS_PERFORMANCE_IMPACT, SIGNAL_TYPE_TMP_TABLES_PERFORMANCE_IMPACT, SIGNAL_TYPE_TRANS_LOGS_PERFORMANCE_IMPACT, SIGNAL_TYPE_HIGH_JOINS_WITHOUT_INDEXES, SIGNAL_TYPE_SUPERUSER_WRITING_TO_USER_TABLES, SIGNAL_TYPE_USER_GRANTED_ALL_PERMISSIONS, SIGNAL_TYPE_DATA_EXPORT_TO_EXTERNAL_CLOUD_STORAGE_BUCKET, SIGNAL_TYPE_DATA_EXPORT_TO_PUBLIC_CLOUD_STORAGE_BUCKET }
		enum RecommendationStateEnum extends Enum[RecommendationStateEnum] { case UNSPECIFIED, ACTIVE, CLAIMED, SUCCEEDED, FAILED, DISMISSED }
	}
	case class DatabaseResourceRecommendationSignalData(
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Required. Type of signal, for example, `SIGNAL_TYPE_IDLE`, `SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES`, etc. */
		signalType: Option[Schema.DatabaseResourceRecommendationSignalData.SignalTypeEnum] = None,
	  /** Required. last time recommendationw as refreshed */
		lastRefreshTime: Option[String] = None,
	  /** Required. Recommendation state */
		recommendationState: Option[Schema.DatabaseResourceRecommendationSignalData.RecommendationStateEnum] = None,
	  /** Required. Name of recommendation. Examples: organizations/1234/locations/us-central1/recommenders/google.cloudsql.instance.PerformanceRecommender/recommendations/9876 */
		recommender: Option[String] = None,
	  /** Required. ID of recommender. Examples: "google.cloudsql.instance.PerformanceRecommender" */
		recommenderId: Option[String] = None,
	  /** Required. Contains an identifier for a subtype of recommendations produced for the same recommender. Subtype is a function of content and impact, meaning a new subtype might be added when significant changes to `content` or `primary_impact.category` are introduced. See the Recommenders section to see a list of subtypes for a given Recommender. Examples: For recommender = "google.cloudsql.instance.PerformanceRecommender", recommender_subtype can be "MYSQL_HIGH_NUMBER_OF_OPEN_TABLES_BEST_PRACTICE"/"POSTGRES_HIGH_TRANSACTION_ID_UTILIZATION_BEST_PRACTICE" */
		recommenderSubtype: Option[String] = None,
	  /** Optional. Any other additional metadata specific to recommendation */
		additionalMetadata: Option[Map[String, JsValue]] = None
	)
	
	object ObservabilityMetricData {
		enum MetricTypeEnum extends Enum[MetricTypeEnum] { case METRIC_TYPE_UNSPECIFIED, CPU_UTILIZATION, MEMORY_UTILIZATION, NETWORK_CONNECTIONS, STORAGE_UTILIZATION, STORAGE_USED_BYTES }
		enum AggregationTypeEnum extends Enum[AggregationTypeEnum] { case AGGREGATION_TYPE_UNSPECIFIED, PEAK, P99, P95, CURRENT }
	}
	case class ObservabilityMetricData(
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Required. Type of metric like CPU, Memory, etc. */
		metricType: Option[Schema.ObservabilityMetricData.MetricTypeEnum] = None,
	  /** Required. Type of aggregation performed on the metric. */
		aggregationType: Option[Schema.ObservabilityMetricData.AggregationTypeEnum] = None,
	  /** Required. Value of the metric type. */
		value: Option[Schema.TypedValue] = None,
	  /** Required. The time the metric value was observed. */
		observationTime: Option[String] = None
	)
	
	case class TypedValue(
	  /** For double value */
		doubleValue: Option[BigDecimal] = None,
	  /** For integer value */
		int64Value: Option[String] = None,
	  /** For string value */
		stringValue: Option[String] = None,
	  /** For boolean value */
		boolValue: Option[Boolean] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudRedisV1OperationMetadata(
	  /** Creation timestamp. */
		createTime: Option[String] = None,
	  /** End timestamp. */
		endTime: Option[String] = None,
	  /** Operation target. */
		target: Option[String] = None,
	  /** Operation verb. */
		verb: Option[String] = None,
	  /** Operation status details. */
		statusDetail: Option[String] = None,
	  /** Specifies if cancellation was requested for the operation. */
		cancelRequested: Option[Boolean] = None,
	  /** API version. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudRedisV1LocationMetadata(
	  /** Output only. The set of available zones in the location. The map is keyed by the lowercase ID of each zone, as defined by GCE. These keys can be specified in `location_id` or `alternative_location_id` fields when creating a Redis instance. */
		availableZones: Option[Map[String, Schema.GoogleCloudRedisV1ZoneMetadata]] = None
	)
	
	case class GoogleCloudRedisV1ZoneMetadata(
	
	)
}
