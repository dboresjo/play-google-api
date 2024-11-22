package com.boresjo.play.api.google.dns

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ResourceRecordSetsListResponse(
	  /** The resource record set resources. */
		rrsets: Option[List[Schema.ResourceRecordSet]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#resourceRecordSetsListResponse""")
	)
	
	case class ResourceRecordSet(
	  /** For example, www.example.com. */
		name: Option[String] = None,
	  /** The identifier of a supported record type. See the list of Supported DNS record types. */
		`type`: Option[String] = None,
	  /** Number of seconds that this `ResourceRecordSet` can be cached by resolvers. */
		ttl: Option[Int] = None,
	  /** As defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1) -- see examples. */
		rrdatas: Option[List[String]] = None,
	  /** As defined in RFC 4034 (section 3.2). */
		signatureRrdatas: Option[List[String]] = None,
	  /** Configures dynamic query responses based on either the geo location of the querying user or a weighted round robin based routing policy. A valid `ResourceRecordSet` contains only `rrdata` (for static resolution) or a `routing_policy` (for dynamic resolution). */
		routingPolicy: Option[Schema.RRSetRoutingPolicy] = None,
		kind: Option[String] = Some("""dns#resourceRecordSet""")
	)
	
	case class RRSetRoutingPolicy(
		geo: Option[Schema.RRSetRoutingPolicyGeoPolicy] = None,
		wrr: Option[Schema.RRSetRoutingPolicyWrrPolicy] = None,
		primaryBackup: Option[Schema.RRSetRoutingPolicyPrimaryBackupPolicy] = None,
	  /** The selfLink attribute of the HealthCheck resource to use for this RRSetRoutingPolicy. https://cloud.google.com/compute/docs/reference/rest/v1/healthChecks */
		healthCheck: Option[String] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicy""")
	)
	
	case class RRSetRoutingPolicyGeoPolicy(
	  /** The primary geo routing configuration. If there are multiple items with the same location, an error is returned instead. */
		items: Option[List[Schema.RRSetRoutingPolicyGeoPolicyGeoPolicyItem]] = None,
	  /** Without fencing, if health check fails for all configured items in the current geo bucket, we failover to the next nearest geo bucket. With fencing, if health checking is enabled, as long as some targets in the current geo bucket are healthy, we return only the healthy targets. However, if all targets are unhealthy, we don't failover to the next nearest bucket; instead, we return all the items in the current bucket even when all targets are unhealthy. */
		enableFencing: Option[Boolean] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyGeoPolicy""")
	)
	
	case class RRSetRoutingPolicyGeoPolicyGeoPolicyItem(
	  /** The geo-location granularity is a GCP region. This location string should correspond to a GCP region. e.g. "us-east1", "southamerica-east1", "asia-east1", etc. */
		location: Option[String] = None,
		rrdatas: Option[List[String]] = None,
	  /** DNSSEC generated signatures for all the `rrdata` within this item. If health checked targets are provided for DNSSEC enabled zones, there's a restriction of 1 IP address per item. */
		signatureRrdatas: Option[List[String]] = None,
	  /** For A and AAAA types only. Endpoints to return in the query result only if they are healthy. These can be specified along with `rrdata` within this item. */
		healthCheckedTargets: Option[Schema.RRSetRoutingPolicyHealthCheckTargets] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyGeoPolicyGeoPolicyItem""")
	)
	
	case class RRSetRoutingPolicyHealthCheckTargets(
	  /** Configuration for internal load balancers to be health checked. */
		internalLoadBalancers: Option[List[Schema.RRSetRoutingPolicyLoadBalancerTarget]] = None,
	  /** The Internet IP addresses to be health checked. The format matches the format of ResourceRecordSet.rrdata as defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1) */
		externalEndpoints: Option[List[String]] = None
	)
	
	object RRSetRoutingPolicyLoadBalancerTarget {
		enum LoadBalancerTypeEnum extends Enum[LoadBalancerTypeEnum] { case none, globalL7ilb, regionalL4ilb, regionalL7ilb }
		enum IpProtocolEnum extends Enum[IpProtocolEnum] { case undefined, tcp, udp }
	}
	case class RRSetRoutingPolicyLoadBalancerTarget(
	  /** The type of load balancer specified by this target. This value must match the configuration of the load balancer located at the LoadBalancerTarget's IP address, port, and region. Use the following: - &#42;regionalL4ilb&#42;: for a regional internal passthrough Network Load Balancer. - &#42;regionalL7ilb&#42;: for a regional internal Application Load Balancer. - &#42;globalL7ilb&#42;: for a global internal Application Load Balancer.  */
		loadBalancerType: Option[Schema.RRSetRoutingPolicyLoadBalancerTarget.LoadBalancerTypeEnum] = None,
	  /** The frontend IP address of the load balancer to health check. */
		ipAddress: Option[String] = None,
	  /** The configured port of the load balancer. */
		port: Option[String] = None,
	  /** The protocol of the load balancer to health check. */
		ipProtocol: Option[Schema.RRSetRoutingPolicyLoadBalancerTarget.IpProtocolEnum] = None,
	  /** The fully qualified URL of the network that the load balancer is attached to. This should be formatted like `https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network}`. */
		networkUrl: Option[String] = None,
	  /** The project ID in which the load balancer is located. */
		project: Option[String] = None,
	  /** The region in which the load balancer is located. */
		region: Option[String] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyLoadBalancerTarget""")
	)
	
	case class RRSetRoutingPolicyWrrPolicy(
		items: Option[List[Schema.RRSetRoutingPolicyWrrPolicyWrrPolicyItem]] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyWrrPolicy""")
	)
	
	case class RRSetRoutingPolicyWrrPolicyWrrPolicyItem(
	  /** The weight corresponding to this `WrrPolicyItem` object. When multiple `WrrPolicyItem` objects are configured, the probability of returning an `WrrPolicyItem` object's data is proportional to its weight relative to the sum of weights configured for all items. This weight must be non-negative. */
		weight: Option[BigDecimal] = None,
		rrdatas: Option[List[String]] = None,
	  /** DNSSEC generated signatures for all the `rrdata` within this item. Note that if health checked targets are provided for DNSSEC enabled zones, there's a restriction of 1 IP address per item. */
		signatureRrdatas: Option[List[String]] = None,
	  /** Endpoints that are health checked before making the routing decision. The unhealthy endpoints are omitted from the result. If all endpoints within a bucket are unhealthy, we choose a different bucket (sampled with respect to its weight) for responding. If DNSSEC is enabled for this zone, only one of `rrdata` or `health_checked_targets` can be set. */
		healthCheckedTargets: Option[Schema.RRSetRoutingPolicyHealthCheckTargets] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyWrrPolicyWrrPolicyItem""")
	)
	
	case class RRSetRoutingPolicyPrimaryBackupPolicy(
	  /** Endpoints that are health checked before making the routing decision. Unhealthy endpoints are omitted from the results. If all endpoints are unhealthy, we serve a response based on the `backup_geo_targets`. */
		primaryTargets: Option[Schema.RRSetRoutingPolicyHealthCheckTargets] = None,
	  /** Backup targets provide a regional failover policy for the otherwise global primary targets. If serving state is set to `BACKUP`, this policy essentially becomes a geo routing policy. */
		backupGeoTargets: Option[Schema.RRSetRoutingPolicyGeoPolicy] = None,
	  /** When serving state is `PRIMARY`, this field provides the option of sending a small percentage of the traffic to the backup targets. */
		trickleTraffic: Option[BigDecimal] = None,
		kind: Option[String] = Some("""dns#rRSetRoutingPolicyPrimaryBackupPolicy""")
	)
	
	case class ResourceRecordSetsDeleteResponse(
	
	)
	
	object Change {
		enum StatusEnum extends Enum[StatusEnum] { case pending, done }
	}
	case class Change(
	  /** Which ResourceRecordSets to add? */
		additions: Option[List[Schema.ResourceRecordSet]] = None,
	  /** Which ResourceRecordSets to remove? Must match existing data exactly. */
		deletions: Option[List[Schema.ResourceRecordSet]] = None,
	  /** The time that this operation was started by the server (output only). This is in RFC3339 text format. */
		startTime: Option[String] = None,
	  /** Unique identifier for the resource; defined by the server (output only). */
		id: Option[String] = None,
	  /** Status of the operation (output only). A status of "done" means that the request to update the authoritative servers has been sent, but the servers might not be updated yet. */
		status: Option[Schema.Change.StatusEnum] = None,
	  /** If the DNS queries for the zone will be served. */
		isServing: Option[Boolean] = None,
		kind: Option[String] = Some("""dns#change""")
	)
	
	case class ChangesListResponse(
	  /** The requested changes. */
		changes: Option[List[Schema.Change]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#changesListResponse""")
	)
	
	object DnsKey {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case rsasha1, rsasha256, rsasha512, ecdsap256sha256, ecdsap384sha384 }
		enum TypeEnum extends Enum[TypeEnum] { case keySigning, zoneSigning }
	}
	case class DnsKey(
	  /** Unique identifier for the resource; defined by the server (output only). */
		id: Option[String] = None,
	  /** String mnemonic specifying the DNSSEC algorithm of this key. Immutable after creation time. */
		algorithm: Option[Schema.DnsKey.AlgorithmEnum] = None,
	  /** Length of the key in bits. Specified at creation time, and then immutable. */
		keyLength: Option[Int] = None,
	  /** Base64 encoded public half of this key. Output only. */
		publicKey: Option[String] = None,
	  /** The time that this resource was created in the control plane. This is in RFC3339 text format. Output only. */
		creationTime: Option[String] = None,
	  /** Active keys are used to sign subsequent changes to the ManagedZone. Inactive keys are still present as DNSKEY Resource Records for the use of resolvers validating existing signatures. */
		isActive: Option[Boolean] = None,
	  /** One of "KEY_SIGNING" or "ZONE_SIGNING". Keys of type KEY_SIGNING have the Secure Entry Point flag set and, when active, are used to sign only resource record sets of type DNSKEY. Otherwise, the Secure Entry Point flag is cleared, and this key is used to sign only resource record sets of other types. Immutable after creation time. */
		`type`: Option[Schema.DnsKey.TypeEnum] = None,
	  /** The key tag is a non-cryptographic hash of the a DNSKEY resource record associated with this DnsKey. The key tag can be used to identify a DNSKEY more quickly (but it is not a unique identifier). In particular, the key tag is used in a parent zone's DS record to point at the DNSKEY in this child ManagedZone. The key tag is a number in the range [0, 65535] and the algorithm to calculate it is specified in RFC4034 Appendix B. Output only. */
		keyTag: Option[Int] = None,
	  /** Cryptographic hashes of the DNSKEY resource record associated with this DnsKey. These digests are needed to construct a DS record that points at this DNS key. Output only. */
		digests: Option[List[Schema.DnsKeyDigest]] = None,
	  /** A mutable string of at most 1024 characters associated with this resource for the user's convenience. Has no effect on the resource's function. */
		description: Option[String] = None,
		kind: Option[String] = Some("""dns#dnsKey""")
	)
	
	object DnsKeyDigest {
		enum TypeEnum extends Enum[TypeEnum] { case sha1, sha256, sha384 }
	}
	case class DnsKeyDigest(
	  /** Specifies the algorithm used to calculate this digest. */
		`type`: Option[Schema.DnsKeyDigest.TypeEnum] = None,
	  /** The base-16 encoded bytes of this digest. Suitable for use in a DS resource record. */
		digest: Option[String] = None
	)
	
	case class DnsKeysListResponse(
	  /** The requested resources. */
		dnsKeys: Option[List[Schema.DnsKey]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#dnsKeysListResponse""")
	)
	
	case class Project(
	  /** Unique numeric identifier for the resource; defined by the server (output only). */
		number: Option[String] = None,
	  /** User assigned unique identifier for the resource (output only). */
		id: Option[String] = None,
	  /** Quotas assigned to this project (output only). */
		quota: Option[Schema.Quota] = None,
		kind: Option[String] = Some("""dns#project""")
	)
	
	case class Quota(
	  /** Maximum allowed number of managed zones in the project. */
		managedZones: Option[Int] = None,
	  /** Maximum allowed number of ResourceRecordSets per zone in the project. */
		rrsetsPerManagedZone: Option[Int] = None,
	  /** Maximum allowed number of ResourceRecordSets to add per ChangesCreateRequest. */
		rrsetAdditionsPerChange: Option[Int] = None,
	  /** Maximum allowed number of ResourceRecordSets to delete per ChangesCreateRequest. */
		rrsetDeletionsPerChange: Option[Int] = None,
	  /** Maximum allowed size for total rrdata in one ChangesCreateRequest in bytes. */
		totalRrdataSizePerChange: Option[Int] = None,
	  /** Maximum allowed number of ResourceRecords per ResourceRecordSet. */
		resourceRecordsPerRrset: Option[Int] = None,
	  /** Maximum allowed number of DnsKeys per ManagedZone. */
		dnsKeysPerManagedZone: Option[Int] = None,
	  /** DNSSEC algorithm and key length types that can be used for DnsKeys. */
		whitelistedKeySpecs: Option[List[Schema.DnsKeySpec]] = None,
	  /** Maximum allowed number of networks to which a privately scoped zone can be attached. */
		networksPerManagedZone: Option[Int] = None,
	  /** Maximum allowed number of managed zones which can be attached to a network. */
		managedZonesPerNetwork: Option[Int] = None,
	  /** Maximum allowed number of policies per project. */
		policies: Option[Int] = None,
	  /** Maximum allowed number of networks per policy. */
		networksPerPolicy: Option[Int] = None,
	  /** Maximum allowed number of alternative target name servers per policy. */
		targetNameServersPerPolicy: Option[Int] = None,
	  /** Maximum allowed number of target name servers per managed forwarding zone. */
		targetNameServersPerManagedZone: Option[Int] = None,
	  /** Maximum allowed number of consumer peering zones per target network owned by this producer project */
		peeringZonesPerTargetNetwork: Option[Int] = None,
	  /** Maximum allowed number of response policies per project. */
		responsePolicies: Option[Int] = None,
	  /** Maximum allowed number of networks per response policy. */
		networksPerResponsePolicy: Option[Int] = None,
	  /** Maximum number of nameservers per delegation, meant to prevent abuse */
		nameserversPerDelegation: Option[Int] = None,
	  /** Maximum allowed number of GKE clusters to which a privately scoped zone can be attached. */
		gkeClustersPerManagedZone: Option[Int] = None,
	  /** Maximum allowed number of managed zones which can be attached to a GKE cluster. */
		managedZonesPerGkeCluster: Option[Int] = None,
	  /** Maximum allowed number of GKE clusters per policy. */
		gkeClustersPerPolicy: Option[Int] = None,
	  /** Maximum allowed number of rules per response policy. */
		responsePolicyRulesPerResponsePolicy: Option[Int] = None,
	  /** Maximum allowed number of GKE clusters per response policy. */
		gkeClustersPerResponsePolicy: Option[Int] = None,
	  /** Maximum allowed number of items per routing policy. */
		itemsPerRoutingPolicy: Option[Int] = None,
		internetHealthChecksPerManagedZone: Option[Int] = None,
		kind: Option[String] = Some("""dns#quota""")
	)
	
	object DnsKeySpec {
		enum KeyTypeEnum extends Enum[KeyTypeEnum] { case keySigning, zoneSigning }
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case rsasha1, rsasha256, rsasha512, ecdsap256sha256, ecdsap384sha384 }
	}
	case class DnsKeySpec(
	  /** Specifies whether this is a key signing key (KSK) or a zone signing key (ZSK). Key signing keys have the Secure Entry Point flag set and, when active, are only used to sign resource record sets of type DNSKEY. Zone signing keys do not have the Secure Entry Point flag set and are used to sign all other types of resource record sets. */
		keyType: Option[Schema.DnsKeySpec.KeyTypeEnum] = None,
	  /** String mnemonic specifying the DNSSEC algorithm of this key. */
		algorithm: Option[Schema.DnsKeySpec.AlgorithmEnum] = None,
	  /** Length of the keys in bits. */
		keyLength: Option[Int] = None,
		kind: Option[String] = Some("""dns#dnsKeySpec""")
	)
	
	object Operation {
		enum StatusEnum extends Enum[StatusEnum] { case pending, done }
	}
	case class Operation(
	  /** Unique identifier for the resource. This is the client_operation_id if the client specified it when the mutation was initiated, otherwise, it is generated by the server. The name must be 1-63 characters long and match the regular expression [-a-z0-9]? (output only) */
		id: Option[String] = None,
	  /** The time that this operation was started by the server. This is in RFC3339 text format (output only). */
		startTime: Option[String] = None,
	  /** Status of the operation. Can be one of the following: "PENDING" or "DONE" (output only). A status of "DONE" means that the request to update the authoritative servers has been sent, but the servers might not be updated yet. */
		status: Option[Schema.Operation.StatusEnum] = None,
	  /** User who requested the operation, for example: user@example.com. cloud-dns-system for operations automatically done by the system. (output only) */
		user: Option[String] = None,
	  /** Type of the operation. Operations include insert, update, and delete (output only). */
		`type`: Option[String] = None,
	  /** Only populated if the operation targeted a ManagedZone (output only). */
		zoneContext: Option[Schema.OperationManagedZoneContext] = None,
	  /** Only populated if the operation targeted a DnsKey (output only). */
		dnsKeyContext: Option[Schema.OperationDnsKeyContext] = None,
		kind: Option[String] = Some("""dns#operation""")
	)
	
	case class OperationManagedZoneContext(
	  /** The pre-operation ManagedZone resource. */
		oldValue: Option[Schema.ManagedZone] = None,
	  /** The post-operation ManagedZone resource. */
		newValue: Option[Schema.ManagedZone] = None
	)
	
	object ManagedZone {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case `public`, `private` }
	}
	case class ManagedZone(
	  /** User assigned name for this resource. Must be unique within the project. The name must be 1-63 characters long, must begin with a letter, end with a letter or digit, and only contain lowercase letters, digits or dashes. */
		name: Option[String] = None,
	  /** The DNS name of this managed zone, for instance "example.com.". */
		dnsName: Option[String] = None,
	  /** A mutable string of at most 1024 characters associated with this resource for the user's convenience. Has no effect on the managed zone's function. */
		description: Option[String] = None,
	  /** Unique identifier for the resource; defined by the server (output only) */
		id: Option[String] = None,
	  /** Delegate your managed_zone to these virtual name servers; defined by the server (output only) */
		nameServers: Option[List[String]] = None,
	  /** The time that this resource was created on the server. This is in RFC3339 text format. Output only. */
		creationTime: Option[String] = None,
	  /** DNSSEC configuration. */
		dnssecConfig: Option[Schema.ManagedZoneDnsSecConfig] = None,
	  /** Optionally specifies the NameServerSet for this ManagedZone. A NameServerSet is a set of DNS name servers that all host the same ManagedZones. Most users leave this field unset. If you need to use this field, contact your account team. */
		nameServerSet: Option[String] = None,
	  /** The zone's visibility: public zones are exposed to the Internet, while private zones are visible only to Virtual Private Cloud resources. */
		visibility: Option[Schema.ManagedZone.VisibilityEnum] = None,
	  /** For privately visible zones, the set of Virtual Private Cloud resources that the zone is visible from. */
		privateVisibilityConfig: Option[Schema.ManagedZonePrivateVisibilityConfig] = None,
	  /** The presence for this field indicates that outbound forwarding is enabled for this zone. The value of this field contains the set of destinations to forward to. */
		forwardingConfig: Option[Schema.ManagedZoneForwardingConfig] = None,
	  /** User labels. */
		labels: Option[Map[String, String]] = None,
	  /** The presence of this field indicates that DNS Peering is enabled for this zone. The value of this field contains the network to peer with. */
		peeringConfig: Option[Schema.ManagedZonePeeringConfig] = None,
	  /** The presence of this field indicates that this is a managed reverse lookup zone and Cloud DNS resolves reverse lookup queries using automatically configured records for VPC resources. This only applies to networks listed under private_visibility_config. */
		reverseLookupConfig: Option[Schema.ManagedZoneReverseLookupConfig] = None,
	  /** This field links to the associated service directory namespace. Do not set this field for public zones or forwarding zones. */
		serviceDirectoryConfig: Option[Schema.ManagedZoneServiceDirectoryConfig] = None,
		cloudLoggingConfig: Option[Schema.ManagedZoneCloudLoggingConfig] = None,
		kind: Option[String] = Some("""dns#managedZone""")
	)
	
	object ManagedZoneDnsSecConfig {
		enum StateEnum extends Enum[StateEnum] { case off, on, transfer }
		enum NonExistenceEnum extends Enum[NonExistenceEnum] { case nsec, nsec3 }
	}
	case class ManagedZoneDnsSecConfig(
	  /** Specifies whether DNSSEC is enabled, and what mode it is in. */
		state: Option[Schema.ManagedZoneDnsSecConfig.StateEnum] = None,
	  /** Specifies parameters for generating initial DnsKeys for this ManagedZone. Can only be changed while the state is OFF. */
		defaultKeySpecs: Option[List[Schema.DnsKeySpec]] = None,
	  /** Specifies the mechanism for authenticated denial-of-existence responses. Can only be changed while the state is OFF. */
		nonExistence: Option[Schema.ManagedZoneDnsSecConfig.NonExistenceEnum] = None,
		kind: Option[String] = Some("""dns#managedZoneDnsSecConfig""")
	)
	
	case class ManagedZonePrivateVisibilityConfig(
	  /** The list of VPC networks that can see this zone. */
		networks: Option[List[Schema.ManagedZonePrivateVisibilityConfigNetwork]] = None,
	  /** The list of Google Kubernetes Engine clusters that can see this zone. */
		gkeClusters: Option[List[Schema.ManagedZonePrivateVisibilityConfigGKECluster]] = None,
		kind: Option[String] = Some("""dns#managedZonePrivateVisibilityConfig""")
	)
	
	case class ManagedZonePrivateVisibilityConfigNetwork(
	  /** The fully qualified URL of the VPC network to bind to. Format this URL like `https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network}` */
		networkUrl: Option[String] = None,
		kind: Option[String] = Some("""dns#managedZonePrivateVisibilityConfigNetwork""")
	)
	
	case class ManagedZonePrivateVisibilityConfigGKECluster(
	  /** The resource name of the cluster to bind this ManagedZone to. This should be specified in the format like: projects/&#42;/locations/&#42;/clusters/&#42;. This is referenced from GKE projects.locations.clusters.get API: https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters/get */
		gkeClusterName: Option[String] = None,
		kind: Option[String] = Some("""dns#managedZonePrivateVisibilityConfigGKECluster""")
	)
	
	case class ManagedZoneForwardingConfig(
	  /** List of target name servers to forward to. Cloud DNS selects the best available name server if more than one target is given. */
		targetNameServers: Option[List[Schema.ManagedZoneForwardingConfigNameServerTarget]] = None,
		kind: Option[String] = Some("""dns#managedZoneForwardingConfig""")
	)
	
	object ManagedZoneForwardingConfigNameServerTarget {
		enum ForwardingPathEnum extends Enum[ForwardingPathEnum] { case default, `private` }
	}
	case class ManagedZoneForwardingConfigNameServerTarget(
	  /** IPv4 address of a target name server. */
		ipv4Address: Option[String] = None,
	  /** Forwarding path for this NameServerTarget. If unset or set to DEFAULT, Cloud DNS makes forwarding decisions based on IP address ranges; that is, RFC1918 addresses go to the VPC network, non-RFC1918 addresses go to the internet. When set to PRIVATE, Cloud DNS always sends queries through the VPC network for this target. */
		forwardingPath: Option[Schema.ManagedZoneForwardingConfigNameServerTarget.ForwardingPathEnum] = None,
	  /** IPv6 address of a target name server. Does not accept both fields (ipv4 & ipv6) being populated. Public preview as of November 2022. */
		ipv6Address: Option[String] = None,
		kind: Option[String] = Some("""dns#managedZoneForwardingConfigNameServerTarget""")
	)
	
	case class ManagedZonePeeringConfig(
	  /** The network with which to peer. */
		targetNetwork: Option[Schema.ManagedZonePeeringConfigTargetNetwork] = None,
		kind: Option[String] = Some("""dns#managedZonePeeringConfig""")
	)
	
	case class ManagedZonePeeringConfigTargetNetwork(
	  /** The fully qualified URL of the VPC network to forward queries to. This should be formatted like `https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network}` */
		networkUrl: Option[String] = None,
	  /** The time at which the zone was deactivated, in RFC 3339 date-time format. An empty string indicates that the peering connection is active. The producer network can deactivate a zone. The zone is automatically deactivated if the producer network that the zone targeted is deleted. Output only. */
		deactivateTime: Option[String] = None,
		kind: Option[String] = Some("""dns#managedZonePeeringConfigTargetNetwork""")
	)
	
	case class ManagedZoneReverseLookupConfig(
		kind: Option[String] = Some("""dns#managedZoneReverseLookupConfig""")
	)
	
	case class ManagedZoneServiceDirectoryConfig(
	  /** Contains information about the namespace associated with the zone. */
		namespace: Option[Schema.ManagedZoneServiceDirectoryConfigNamespace] = None,
		kind: Option[String] = Some("""dns#managedZoneServiceDirectoryConfig""")
	)
	
	case class ManagedZoneServiceDirectoryConfigNamespace(
	  /** The fully qualified URL of the namespace associated with the zone. Format must be `https://servicedirectory.googleapis.com/v1/projects/{project}/locations/{location}/namespaces/{namespace}` */
		namespaceUrl: Option[String] = None,
	  /** The time that the namespace backing this zone was deleted; an empty string if it still exists. This is in RFC3339 text format. Output only. */
		deletionTime: Option[String] = None,
		kind: Option[String] = Some("""dns#managedZoneServiceDirectoryConfigNamespace""")
	)
	
	case class ManagedZoneCloudLoggingConfig(
	  /** If set, enable query logging for this ManagedZone. False by default, making logging opt-in. */
		enableLogging: Option[Boolean] = None,
		kind: Option[String] = Some("""dns#managedZoneCloudLoggingConfig""")
	)
	
	case class OperationDnsKeyContext(
	  /** The pre-operation DnsKey resource. */
		oldValue: Option[Schema.DnsKey] = None,
	  /** The post-operation DnsKey resource. */
		newValue: Option[Schema.DnsKey] = None
	)
	
	case class ManagedZoneOperationsListResponse(
	  /** The operation resources. */
		operations: Option[List[Schema.Operation]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#managedZoneOperationsListResponse""")
	)
	
	case class ManagedZonesListResponse(
	  /** The managed zone resources. */
		managedZones: Option[List[Schema.ManagedZone]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#managedZonesListResponse""")
	)
	
	case class GoogleIamV1SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
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
	
	case class GoogleIamV1AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class GoogleIamV1GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GoogleIamV1GetPolicyOptions] = None
	)
	
	case class GoogleIamV1GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class GoogleIamV1TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class Policy(
	  /** Unique identifier for the resource; defined by the server (output only). */
		id: Option[String] = None,
	  /** User-assigned name for this policy. */
		name: Option[String] = None,
	  /** Allows networks bound to this policy to receive DNS queries sent by VMs or applications over VPN connections. When enabled, a virtual IP address is allocated from each of the subnetworks that are bound to this policy. */
		enableInboundForwarding: Option[Boolean] = None,
	  /** A mutable string of at most 1024 characters associated with this resource for the user's convenience. Has no effect on the policy's function. */
		description: Option[String] = None,
	  /** List of network names specifying networks to which this policy is applied. */
		networks: Option[List[Schema.PolicyNetwork]] = None,
	  /** Sets an alternative name server for the associated networks. When specified, all DNS queries are forwarded to a name server that you choose. Names such as .internal are not available when an alternative name server is specified. */
		alternativeNameServerConfig: Option[Schema.PolicyAlternativeNameServerConfig] = None,
	  /** Controls whether logging is enabled for the networks bound to this policy. Defaults to no logging if not set. */
		enableLogging: Option[Boolean] = None,
		kind: Option[String] = Some("""dns#policy""")
	)
	
	case class PolicyNetwork(
	  /** The fully qualified URL of the VPC network to bind to. This should be formatted like https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network} */
		networkUrl: Option[String] = None,
		kind: Option[String] = Some("""dns#policyNetwork""")
	)
	
	case class PolicyAlternativeNameServerConfig(
	  /** Sets an alternative name server for the associated networks. When specified, all DNS queries are forwarded to a name server that you choose. Names such as .internal are not available when an alternative name server is specified. */
		targetNameServers: Option[List[Schema.PolicyAlternativeNameServerConfigTargetNameServer]] = None,
		kind: Option[String] = Some("""dns#policyAlternativeNameServerConfig""")
	)
	
	object PolicyAlternativeNameServerConfigTargetNameServer {
		enum ForwardingPathEnum extends Enum[ForwardingPathEnum] { case default, `private` }
	}
	case class PolicyAlternativeNameServerConfigTargetNameServer(
	  /** IPv4 address to forward queries to. */
		ipv4Address: Option[String] = None,
	  /** Forwarding path for this TargetNameServer. If unset or set to DEFAULT, Cloud DNS makes forwarding decisions based on address ranges; that is, RFC1918 addresses go to the VPC network, non-RFC1918 addresses go to the internet. When set to PRIVATE, Cloud DNS always sends queries through the VPC network for this target. */
		forwardingPath: Option[Schema.PolicyAlternativeNameServerConfigTargetNameServer.ForwardingPathEnum] = None,
	  /** IPv6 address to forward to. Does not accept both fields (ipv4 & ipv6) being populated. Public preview as of November 2022. */
		ipv6Address: Option[String] = None,
		kind: Option[String] = Some("""dns#policyAlternativeNameServerConfigTargetNameServer""")
	)
	
	case class PoliciesListResponse(
	  /** The policy resources. */
		policies: Option[List[Schema.Policy]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None,
	  /** Type of resource. */
		kind: Option[String] = Some("""dns#policiesListResponse""")
	)
	
	case class PoliciesPatchResponse(
		policy: Option[Schema.Policy] = None
	)
	
	case class PoliciesUpdateResponse(
		policy: Option[Schema.Policy] = None
	)
	
	case class ResponsePolicy(
	  /** Unique identifier for the resource; defined by the server (output only). */
		id: Option[String] = None,
	  /** User assigned name for this Response Policy. */
		responsePolicyName: Option[String] = None,
	  /** User-provided description for this Response Policy. */
		description: Option[String] = None,
	  /** List of network names specifying networks to which this policy is applied. */
		networks: Option[List[Schema.ResponsePolicyNetwork]] = None,
	  /** The list of Google Kubernetes Engine clusters to which this response policy is applied. */
		gkeClusters: Option[List[Schema.ResponsePolicyGKECluster]] = None,
	  /** User labels. */
		labels: Option[Map[String, String]] = None,
		kind: Option[String] = Some("""dns#responsePolicy""")
	)
	
	case class ResponsePolicyNetwork(
	  /** The fully qualified URL of the VPC network to bind to. This should be formatted like `https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network}` */
		networkUrl: Option[String] = None,
		kind: Option[String] = Some("""dns#responsePolicyNetwork""")
	)
	
	case class ResponsePolicyGKECluster(
	  /** The resource name of the cluster to bind this response policy to. This should be specified in the format like: projects/&#42;/locations/&#42;/clusters/&#42;. This is referenced from GKE projects.locations.clusters.get API: https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters/get */
		gkeClusterName: Option[String] = None,
		kind: Option[String] = Some("""dns#responsePolicyGKECluster""")
	)
	
	case class ResponsePoliciesListResponse(
	  /** The Response Policy resources. */
		responsePolicies: Option[List[Schema.ResponsePolicy]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None
	)
	
	case class ResponsePoliciesPatchResponse(
		responsePolicy: Option[Schema.ResponsePolicy] = None
	)
	
	case class ResponsePoliciesUpdateResponse(
		responsePolicy: Option[Schema.ResponsePolicy] = None
	)
	
	object ResponsePolicyRule {
		enum BehaviorEnum extends Enum[BehaviorEnum] { case behaviorUnspecified, bypassResponsePolicy }
	}
	case class ResponsePolicyRule(
	  /** An identifier for this rule. Must be unique with the ResponsePolicy. */
		ruleName: Option[String] = None,
	  /** The DNS name (wildcard or exact) to apply this rule to. Must be unique within the Response Policy Rule. */
		dnsName: Option[String] = None,
	  /** Answer this query directly with DNS data. These ResourceRecordSets override any other DNS behavior for the matched name; in particular they override private zones, the public internet, and GCP internal DNS. No SOA nor NS types are allowed. */
		localData: Option[Schema.ResponsePolicyRuleLocalData] = None,
	  /** Answer this query with a behavior rather than DNS data. */
		behavior: Option[Schema.ResponsePolicyRule.BehaviorEnum] = None,
		kind: Option[String] = Some("""dns#responsePolicyRule""")
	)
	
	case class ResponsePolicyRuleLocalData(
	  /** All resource record sets for this selector, one per resource record type. The name must match the dns_name. */
		localDatas: Option[List[Schema.ResourceRecordSet]] = None
	)
	
	case class ResponsePolicyRulesListResponse(
	  /** The Response Policy Rule resources. */
		responsePolicyRules: Option[List[Schema.ResponsePolicyRule]] = None,
	  /** This field indicates that more results are available beyond the last page displayed. To fetch the results, make another list request and use this value as your page token. This lets you retrieve the complete contents of a very large collection one page at a time. However, if the contents of the collection change between the first and last paginated list request, the set of all elements returned are an inconsistent view of the collection. You can't retrieve a consistent snapshot of a collection larger than the maximum page size. */
		nextPageToken: Option[String] = None
	)
	
	case class ResponsePolicyRulesPatchResponse(
		responsePolicyRule: Option[Schema.ResponsePolicyRule] = None
	)
	
	case class ResponsePolicyRulesUpdateResponse(
		responsePolicyRule: Option[Schema.ResponsePolicyRule] = None
	)
}
