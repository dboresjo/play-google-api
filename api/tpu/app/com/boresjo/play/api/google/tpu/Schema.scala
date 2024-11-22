package com.boresjo.play.api.google.tpu

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
	
	case class ListNodesResponse(
	  /** The listed nodes. */
		nodes: Option[List[Schema.Node]] = None,
	  /** The next page token or empty if none. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Node {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, RESTARTING, REIMAGING, DELETING, REPAIRING, STOPPED, STOPPING, STARTING, PREEMPTED, TERMINATED, HIDING, HIDDEN, UNHIDING, UNKNOWN }
		enum HealthEnum extends Enum[HealthEnum] { case HEALTH_UNSPECIFIED, HEALTHY, TIMEOUT, UNHEALTHY_TENSORFLOW, UNHEALTHY_MAINTENANCE }
		enum ApiVersionEnum extends Enum[ApiVersionEnum] { case API_VERSION_UNSPECIFIED, V1_ALPHA1, V1, V2_ALPHA1, V2 }
	}
	case class Node(
	  /** Output only. Immutable. The name of the TPU. */
		name: Option[String] = None,
	  /** The user-supplied description of the TPU. Maximum of 512 characters. */
		description: Option[String] = None,
	  /** Optional. The type of hardware accelerators associated with this node. */
		acceleratorType: Option[String] = None,
	  /** Output only. The current state for the TPU Node. */
		state: Option[Schema.Node.StateEnum] = None,
	  /** Output only. If this field is populated, it contains a description of why the TPU Node is unhealthy. */
		healthDescription: Option[String] = None,
	  /** Required. The runtime version running in the Node. */
		runtimeVersion: Option[String] = None,
	  /** Network configurations for the TPU node. */
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** The CIDR block that the TPU node will use when selecting an IP address. This CIDR block must be a /29 block; the Compute Engine networks API forbids a smaller block, and using a larger block would be wasteful (a node can only consume one IP address). Errors will occur if the CIDR block has already been used for a currently existing TPU node, the CIDR block conflicts with any subnetworks in the user's provided network, or the provided network is peered with another network that is using that CIDR block. */
		cidrBlock: Option[String] = None,
	  /** The Google Cloud Platform Service Account to be used by the TPU node VMs. If None is specified, the default compute service account will be used. */
		serviceAccount: Option[Schema.ServiceAccount] = None,
	  /** Output only. The time when the node was created. */
		createTime: Option[String] = None,
	  /** The scheduling options for this node. */
		schedulingConfig: Option[Schema.SchedulingConfig] = None,
	  /** Output only. The network endpoints where TPU workers can be accessed and sent work. It is recommended that runtime clients of the node reach out to the 0th entry in this map first. */
		networkEndpoints: Option[List[Schema.NetworkEndpoint]] = None,
	  /** The health status of the TPU node. */
		health: Option[Schema.Node.HealthEnum] = None,
	  /** Resource labels to represent user-provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Custom metadata to apply to the TPU Node. Can set startup-script and shutdown-script */
		metadata: Option[Map[String, String]] = None,
	  /** Tags to apply to the TPU Node. Tags are used to identify valid sources or targets for network firewalls. */
		tags: Option[List[String]] = None,
	  /** Output only. The unique identifier for the TPU Node. */
		id: Option[String] = None,
	  /** The additional data disks for the Node. */
		dataDisks: Option[List[Schema.AttachedDisk]] = None,
	  /** Output only. The API version that created this Node. */
		apiVersion: Option[Schema.Node.ApiVersionEnum] = None,
	  /** Output only. The Symptoms that have occurred to the TPU Node. */
		symptoms: Option[List[Schema.Symptom]] = None,
	  /** Shielded Instance options. */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** The AccleratorConfig for the TPU Node. */
		acceleratorConfig: Option[Schema.AcceleratorConfig] = None,
	  /** Output only. The qualified name of the QueuedResource that requested this Node. */
		queuedResource: Option[String] = None,
	  /** Output only. Whether the Node belongs to a Multislice group. */
		multisliceNode: Option[Boolean] = None
	)
	
	case class NetworkConfig(
	  /** The name of the network for the TPU node. It must be a preexisting Google Compute Engine network. If none is provided, "default" will be used. */
		network: Option[String] = None,
	  /** The name of the subnetwork for the TPU node. It must be a preexisting Google Compute Engine subnetwork. If none is provided, "default" will be used. */
		subnetwork: Option[String] = None,
	  /** Indicates that external IP addresses would be associated with the TPU workers. If set to false, the specified subnetwork or network should have Private Google Access enabled. */
		enableExternalIps: Option[Boolean] = None,
	  /** Allows the TPU node to send and receive packets with non-matching destination or source IPs. This is required if you plan to use the TPU workers to forward routes. */
		canIpForward: Option[Boolean] = None,
	  /** Optional. Specifies networking queue count for TPU VM instance's network interface. */
		queueCount: Option[Int] = None
	)
	
	case class ServiceAccount(
	  /** Email address of the service account. If empty, default Compute service account will be used. */
		email: Option[String] = None,
	  /** The list of scopes to be made available for this service account. If empty, access to all Cloud APIs will be allowed. */
		scope: Option[List[String]] = None
	)
	
	case class SchedulingConfig(
	  /** Defines whether the node is preemptible. */
		preemptible: Option[Boolean] = None,
	  /** Whether the node is created under a reservation. */
		reserved: Option[Boolean] = None,
	  /** Optional. Defines whether the node is Spot VM. */
		spot: Option[Boolean] = None
	)
	
	case class NetworkEndpoint(
	  /** The internal IP address of this network endpoint. */
		ipAddress: Option[String] = None,
	  /** The port of this network endpoint. */
		port: Option[Int] = None,
	  /** The access config for the TPU worker. */
		accessConfig: Option[Schema.AccessConfig] = None
	)
	
	case class AccessConfig(
	  /** Output only. An external IP address associated with the TPU worker. */
		externalIp: Option[String] = None
	)
	
	object AttachedDisk {
		enum ModeEnum extends Enum[ModeEnum] { case DISK_MODE_UNSPECIFIED, READ_WRITE, READ_ONLY }
	}
	case class AttachedDisk(
	  /** Specifies the full path to an existing disk. For example: "projects/my-project/zones/us-central1-c/disks/my-disk". */
		sourceDisk: Option[String] = None,
	  /** The mode in which to attach this disk. If not specified, the default is READ_WRITE mode. Only applicable to data_disks. */
		mode: Option[Schema.AttachedDisk.ModeEnum] = None
	)
	
	object Symptom {
		enum SymptomTypeEnum extends Enum[SymptomTypeEnum] { case SYMPTOM_TYPE_UNSPECIFIED, LOW_MEMORY, OUT_OF_MEMORY, EXECUTE_TIMED_OUT, MESH_BUILD_FAIL, HBM_OUT_OF_MEMORY, PROJECT_ABUSE }
	}
	case class Symptom(
	  /** Timestamp when the Symptom is created. */
		createTime: Option[String] = None,
	  /** Type of the Symptom. */
		symptomType: Option[Schema.Symptom.SymptomTypeEnum] = None,
	  /** Detailed information of the current Symptom. */
		details: Option[String] = None,
	  /** A string used to uniquely distinguish a worker within a TPU node. */
		workerId: Option[String] = None
	)
	
	case class ShieldedInstanceConfig(
	  /** Defines whether the instance has Secure Boot enabled. */
		enableSecureBoot: Option[Boolean] = None
	)
	
	object AcceleratorConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, V2, V3, V4, V5LITE_POD, V5P }
	}
	case class AcceleratorConfig(
	  /** Required. Type of TPU. */
		`type`: Option[Schema.AcceleratorConfig.TypeEnum] = None,
	  /** Required. Topology of TPU in chips. */
		topology: Option[String] = None
	)
	
	case class StopNodeRequest(
	
	)
	
	case class StartNodeRequest(
	
	)
	
	case class ListQueuedResourcesResponse(
	  /** The listed queued resources. */
		queuedResources: Option[List[Schema.QueuedResource]] = None,
	  /** The next page token or empty if none. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class QueuedResource(
	  /** Output only. Immutable. The name of the QueuedResource. */
		name: Option[String] = None,
	  /** Output only. The time when the QueuedResource was created. */
		createTime: Option[String] = None,
	  /** Optional. Defines a TPU resource. */
		tpu: Option[Schema.Tpu] = None,
	  /** Optional. The Spot tier. */
		spot: Option[Schema.Spot] = None,
	  /** Optional. The Guaranteed tier */
		guaranteed: Option[Schema.Guaranteed] = None,
	  /** Optional. The queueing policy of the QueuedRequest. */
		queueingPolicy: Option[Schema.QueueingPolicy] = None,
	  /** Output only. State of the QueuedResource request. */
		state: Option[Schema.QueuedResourceState] = None,
	  /** Optional. Name of the reservation in which the resource should be provisioned. Format: projects/{project}/locations/{zone}/reservations/{reservation} */
		reservationName: Option[String] = None
	)
	
	case class Tpu(
	  /** Optional. The TPU node(s) being requested. */
		nodeSpec: Option[List[Schema.NodeSpec]] = None
	)
	
	case class NodeSpec(
	  /** Required. The parent resource name. */
		parent: Option[String] = None,
	  /** Optional. The unqualified resource name. Should follow the `^[A-Za-z0-9_.~+%-]+$` regex format. This is only specified when requesting a single node. In case of multislice requests, multislice_params must be populated instead. */
		nodeId: Option[String] = None,
	  /** Optional. Fields to specify in case of multislice request. */
		multisliceParams: Option[Schema.MultisliceParams] = None,
	  /** Required. The node. */
		node: Option[Schema.Node] = None
	)
	
	case class MultisliceParams(
	  /** Required. Number of nodes with this spec. The system will attempt to provison "node_count" nodes as part of the request. This needs to be > 1. */
		nodeCount: Option[Int] = None,
	  /** Optional. Prefix of node_ids in case of multislice request. Should follow the `^[A-Za-z0-9_.~+%-]+$` regex format. If node_count = 3 and node_id_prefix = "np", node ids of nodes created will be "np-0", "np-1", "np-2". If this field is not provided we use queued_resource_id as the node_id_prefix. */
		nodeIdPrefix: Option[String] = None
	)
	
	case class Spot(
	
	)
	
	case class Guaranteed(
	  /** Optional. Defines the minimum duration of the guarantee. If specified, the requested resources will only be provisioned if they can be allocated for at least the given duration. */
		minDuration: Option[String] = None
	)
	
	case class QueueingPolicy(
	  /** Optional. A relative time after which resources should not be created. If the request cannot be fulfilled by this time the request will be failed. */
		validUntilDuration: Option[String] = None,
	  /** Optional. An absolute time after which resources should not be created. If the request cannot be fulfilled by this time the request will be failed. */
		validUntilTime: Option[String] = None,
	  /** Optional. A relative time after which resources may be created. */
		validAfterDuration: Option[String] = None,
	  /** Optional. An absolute time after which resources may be created. */
		validAfterTime: Option[String] = None,
	  /** Optional. An absolute time interval within which resources may be created. */
		validInterval: Option[Schema.Interval] = None
	)
	
	case class Interval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	object QueuedResourceState {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACCEPTED, PROVISIONING, FAILED, DELETING, ACTIVE, SUSPENDING, SUSPENDED, WAITING_FOR_RESOURCES }
		enum StateInitiatorEnum extends Enum[StateInitiatorEnum] { case STATE_INITIATOR_UNSPECIFIED, USER, SERVICE }
	}
	case class QueuedResourceState(
	  /** Output only. State of the QueuedResource request. */
		state: Option[Schema.QueuedResourceState.StateEnum] = None,
	  /** Output only. Further data for the creating state. */
		creatingData: Option[Schema.CreatingData] = None,
	  /** Output only. Further data for the accepted state. */
		acceptedData: Option[Schema.AcceptedData] = None,
	  /** Output only. Further data for the provisioning state. */
		provisioningData: Option[Schema.ProvisioningData] = None,
	  /** Output only. Further data for the failed state. */
		failedData: Option[Schema.FailedData] = None,
	  /** Output only. Further data for the deleting state. */
		deletingData: Option[Schema.DeletingData] = None,
	  /** Output only. Further data for the active state. */
		activeData: Option[Schema.ActiveData] = None,
	  /** Output only. Further data for the suspending state. */
		suspendingData: Option[Schema.SuspendingData] = None,
	  /** Output only. Further data for the suspended state. */
		suspendedData: Option[Schema.SuspendedData] = None,
	  /** Output only. The initiator of the QueuedResources's current state. Used to indicate whether the SUSPENDING/SUSPENDED state was initiated by the user or the service. */
		stateInitiator: Option[Schema.QueuedResourceState.StateInitiatorEnum] = None
	)
	
	case class CreatingData(
	
	)
	
	case class AcceptedData(
	
	)
	
	case class ProvisioningData(
	
	)
	
	case class FailedData(
	  /** Output only. The error that caused the queued resource to enter the FAILED state. */
		error: Option[Schema.Status] = None
	)
	
	case class DeletingData(
	
	)
	
	case class ActiveData(
	
	)
	
	case class SuspendingData(
	
	)
	
	case class SuspendedData(
	
	)
	
	case class ResetQueuedResourceRequest(
	
	)
	
	case class GenerateServiceIdentityRequest(
	
	)
	
	case class GenerateServiceIdentityResponse(
	  /** ServiceIdentity that was created or retrieved. */
		identity: Option[Schema.ServiceIdentity] = None
	)
	
	case class ServiceIdentity(
	  /** The email address of the service identity. */
		email: Option[String] = None
	)
	
	case class ListAcceleratorTypesResponse(
	  /** The listed nodes. */
		acceleratorTypes: Option[List[Schema.AcceleratorType]] = None,
	  /** The next page token or empty if none. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class AcceleratorType(
	  /** The resource name. */
		name: Option[String] = None,
	  /** The accelerator type. */
		`type`: Option[String] = None,
	  /** The accelerator config. */
		acceleratorConfigs: Option[List[Schema.AcceleratorConfig]] = None
	)
	
	case class ListRuntimeVersionsResponse(
	  /** The listed nodes. */
		runtimeVersions: Option[List[Schema.RuntimeVersion]] = None,
	  /** The next page token or empty if none. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class RuntimeVersion(
	  /** The resource name. */
		name: Option[String] = None,
	  /** The runtime version. */
		version: Option[String] = None
	)
	
	case class GetGuestAttributesRequest(
	  /** The guest attributes path to be queried. */
		queryPath: Option[String] = None,
	  /** The 0-based worker ID. If it is empty, all workers' GuestAttributes will be returned. */
		workerIds: Option[List[String]] = None
	)
	
	case class GetGuestAttributesResponse(
	  /** The guest attributes for the TPU workers. */
		guestAttributes: Option[List[Schema.GuestAttributes]] = None
	)
	
	case class GuestAttributes(
	  /** The path to be queried. This can be the default namespace ('/') or a nested namespace ('/\/') or a specified key ('/\/\') */
		queryPath: Option[String] = None,
	  /** The value of the requested queried path. */
		queryValue: Option[Schema.GuestAttributesValue] = None
	)
	
	case class GuestAttributesValue(
	  /** The list of guest attributes entries. */
		items: Option[List[Schema.GuestAttributesEntry]] = None
	)
	
	case class GuestAttributesEntry(
	  /** Namespace for the guest attribute entry. */
		namespace: Option[String] = None,
	  /** Key for the guest attribute entry. */
		key: Option[String] = None,
	  /** Value for the guest attribute entry. */
		value: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Target of the operation - for example projects/project-1/connectivityTests/test-1 */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Specifies if cancellation was requested for the operation. */
		cancelRequested: Option[Boolean] = None,
	  /** API version. */
		apiVersion: Option[String] = None
	)
}
