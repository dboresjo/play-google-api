package com.boresjo.play.api.google.netapp

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
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class ListStoragePoolsResponse(
	  /** The list of StoragePools */
		storagePools: Option[List[Schema.StoragePool]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object StoragePool {
		enum ServiceLevelEnum extends Enum[ServiceLevelEnum] { case SERVICE_LEVEL_UNSPECIFIED, PREMIUM, EXTREME, STANDARD, FLEX }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, DELETING, UPDATING, RESTORING, DISABLED, ERROR }
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, SERVICE_MANAGED, CLOUD_KMS }
	}
	case class StoragePool(
	  /** Identifier. Name of the storage pool */
		name: Option[String] = None,
	  /** Required. Service level of the storage pool */
		serviceLevel: Option[Schema.StoragePool.ServiceLevelEnum] = None,
	  /** Required. Capacity in GIB of the pool */
		capacityGib: Option[String] = None,
	  /** Output only. Allocated size of all volumes in GIB in the storage pool */
		volumeCapacityGib: Option[String] = None,
	  /** Output only. Volume count of the storage pool */
		volumeCount: Option[Int] = None,
	  /** Output only. State of the storage pool */
		state: Option[Schema.StoragePool.StateEnum] = None,
	  /** Output only. State details of the storage pool */
		stateDetails: Option[String] = None,
	  /** Output only. Create time of the storage pool */
		createTime: Option[String] = None,
	  /** Optional. Description of the storage pool */
		description: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Required. VPC Network name. Format: projects/{project}/global/networks/{network} */
		network: Option[String] = None,
	  /** Optional. Specifies the Active Directory to be used for creating a SMB volume. */
		activeDirectory: Option[String] = None,
	  /** Optional. Specifies the KMS config to be used for volume encryption. */
		kmsConfig: Option[String] = None,
	  /** Optional. Flag indicating if the pool is NFS LDAP enabled or not. */
		ldapEnabled: Option[Boolean] = None,
	  /** Optional. This field is not implemented. The values provided in this field are ignored. */
		psaRange: Option[String] = None,
	  /** Output only. Specifies the current pool encryption key source. */
		encryptionType: Option[Schema.StoragePool.EncryptionTypeEnum] = None,
	  /** Deprecated. Used to allow SO pool to access AD or DNS server from other regions. */
		globalAccessAllowed: Option[Boolean] = None,
	  /** Optional. True if the storage pool supports Auto Tiering enabled volumes. Default is false. Auto-tiering can be enabled after storage pool creation but it can't be disabled once enabled. */
		allowAutoTiering: Option[Boolean] = None,
	  /** Optional. Specifies the replica zone for regional storagePool. */
		replicaZone: Option[String] = None,
	  /** Optional. Specifies the active zone for regional storagePool. */
		zone: Option[String] = None
	)
	
	case class SwitchActiveReplicaZoneRequest(
	
	)
	
	case class ListVolumesResponse(
	  /** The list of Volume */
		volumes: Option[List[Schema.Volume]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Volume {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, DELETING, UPDATING, RESTORING, DISABLED, ERROR, PREPARING, READ_ONLY }
		enum ServiceLevelEnum extends Enum[ServiceLevelEnum] { case SERVICE_LEVEL_UNSPECIFIED, PREMIUM, EXTREME, STANDARD, FLEX }
		enum ProtocolsEnum extends Enum[ProtocolsEnum] { case PROTOCOLS_UNSPECIFIED, NFSV3, NFSV4, SMB }
		enum SmbSettingsEnum extends Enum[SmbSettingsEnum] { case SMB_SETTINGS_UNSPECIFIED, ENCRYPT_DATA, BROWSABLE, CHANGE_NOTIFY, NON_BROWSABLE, OPLOCKS, SHOW_SNAPSHOT, SHOW_PREVIOUS_VERSIONS, ACCESS_BASED_ENUMERATION, CONTINUOUSLY_AVAILABLE }
		enum SecurityStyleEnum extends Enum[SecurityStyleEnum] { case SECURITY_STYLE_UNSPECIFIED, NTFS, UNIX }
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, SERVICE_MANAGED, CLOUD_KMS }
		enum RestrictedActionsEnum extends Enum[RestrictedActionsEnum] { case RESTRICTED_ACTION_UNSPECIFIED, DELETE }
	}
	case class Volume(
	  /** Identifier. Name of the volume */
		name: Option[String] = None,
	  /** Output only. State of the volume */
		state: Option[Schema.Volume.StateEnum] = None,
	  /** Output only. State details of the volume */
		stateDetails: Option[String] = None,
	  /** Output only. Create time of the volume */
		createTime: Option[String] = None,
	  /** Required. Share name of the volume */
		shareName: Option[String] = None,
	  /** Output only. This field is not implemented. The values provided in this field are ignored. */
		psaRange: Option[String] = None,
	  /** Required. StoragePool name of the volume */
		storagePool: Option[String] = None,
	  /** Output only. VPC Network name. Format: projects/{project}/global/networks/{network} */
		network: Option[String] = None,
	  /** Output only. Service level of the volume */
		serviceLevel: Option[Schema.Volume.ServiceLevelEnum] = None,
	  /** Required. Capacity in GIB of the volume */
		capacityGib: Option[String] = None,
	  /** Optional. Export policy of the volume */
		exportPolicy: Option[Schema.ExportPolicy] = None,
	  /** Required. Protocols required for the volume */
		protocols: Option[List[Schema.Volume.ProtocolsEnum]] = None,
	  /** Optional. SMB share settings for the volume. */
		smbSettings: Option[List[Schema.Volume.SmbSettingsEnum]] = None,
	  /** Output only. Mount options of this volume */
		mountOptions: Option[List[Schema.MountOption]] = None,
	  /** Optional. Default unix style permission (e.g. 777) the mount point will be created with. Applicable for NFS protocol types only. */
		unixPermissions: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the volume */
		description: Option[String] = None,
	  /** Optional. SnapshotPolicy for a volume. */
		snapshotPolicy: Option[Schema.SnapshotPolicy] = None,
	  /** Optional. Snap_reserve specifies percentage of volume storage reserved for snapshot storage. Default is 0 percent. */
		snapReserve: Option[BigDecimal] = None,
	  /** Optional. Snapshot_directory if enabled (true) the volume will contain a read-only .snapshot directory which provides access to each of the volume's snapshots. */
		snapshotDirectory: Option[Boolean] = None,
	  /** Output only. Used capacity in GIB of the volume. This is computed periodically and it does not represent the realtime usage. */
		usedGib: Option[String] = None,
	  /** Optional. Security Style of the Volume */
		securityStyle: Option[Schema.Volume.SecurityStyleEnum] = None,
	  /** Optional. Flag indicating if the volume is a kerberos volume or not, export policy rules control kerberos security modes (krb5, krb5i, krb5p). */
		kerberosEnabled: Option[Boolean] = None,
	  /** Output only. Flag indicating if the volume is NFS LDAP enabled or not. */
		ldapEnabled: Option[Boolean] = None,
	  /** Output only. Specifies the ActiveDirectory name of a SMB volume. */
		activeDirectory: Option[String] = None,
	  /** Optional. Specifies the source of the volume to be created from. */
		restoreParameters: Option[Schema.RestoreParameters] = None,
	  /** Output only. Specifies the KMS config to be used for volume encryption. */
		kmsConfig: Option[String] = None,
	  /** Output only. Specified the current volume encryption key source. */
		encryptionType: Option[Schema.Volume.EncryptionTypeEnum] = None,
	  /** Output only. Indicates whether the volume is part of a replication relationship. */
		hasReplication: Option[Boolean] = None,
	  /** BackupConfig of the volume. */
		backupConfig: Option[Schema.BackupConfig] = None,
	  /** Optional. List of actions that are restricted on this volume. */
		restrictedActions: Option[List[Schema.Volume.RestrictedActionsEnum]] = None,
	  /** Optional. Flag indicating if the volume will be a large capacity volume or a regular volume. */
		largeCapacity: Option[Boolean] = None,
	  /** Optional. Flag indicating if the volume will have an IP address per node for volumes supporting multiple IP endpoints. Only the volume with large_capacity will be allowed to have multiple endpoints. */
		multipleEndpoints: Option[Boolean] = None,
	  /** Tiering policy for the volume. */
		tieringPolicy: Option[Schema.TieringPolicy] = None,
	  /** Output only. Specifies the replica zone for regional volume. */
		replicaZone: Option[String] = None,
	  /** Output only. Specifies the active zone for regional volume. */
		zone: Option[String] = None,
	  /** Output only. Size of the volume cold tier data in GiB. */
		coldTierSizeGib: Option[String] = None,
	  /** Optional. The Hybrid Replication parameters for the volume. */
		hybridReplicationParameters: Option[Schema.HybridReplicationParameters] = None
	)
	
	case class ExportPolicy(
	  /** Required. List of export policy rules */
		rules: Option[List[Schema.SimpleExportPolicyRule]] = None
	)
	
	object SimpleExportPolicyRule {
		enum AccessTypeEnum extends Enum[AccessTypeEnum] { case ACCESS_TYPE_UNSPECIFIED, READ_ONLY, READ_WRITE, READ_NONE }
	}
	case class SimpleExportPolicyRule(
	  /** Comma separated list of allowed clients IP addresses */
		allowedClients: Option[String] = None,
	  /** Whether Unix root access will be granted. */
		hasRootAccess: Option[String] = None,
	  /** Access type (ReadWrite, ReadOnly, None) */
		accessType: Option[Schema.SimpleExportPolicyRule.AccessTypeEnum] = None,
	  /** NFS V3 protocol. */
		nfsv3: Option[Boolean] = None,
	  /** NFS V4 protocol. */
		nfsv4: Option[Boolean] = None,
	  /** If enabled (true) the rule defines a read only access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'authentication' kerberos security mode. */
		kerberos5ReadOnly: Option[Boolean] = None,
	  /** If enabled (true) the rule defines read and write access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'authentication' kerberos security mode. The 'kerberos5ReadOnly' value be ignored if this is enabled. */
		kerberos5ReadWrite: Option[Boolean] = None,
	  /** If enabled (true) the rule defines a read only access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'integrity' kerberos security mode. */
		kerberos5iReadOnly: Option[Boolean] = None,
	  /** If enabled (true) the rule defines read and write access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'integrity' kerberos security mode. The 'kerberos5iReadOnly' value be ignored if this is enabled. */
		kerberos5iReadWrite: Option[Boolean] = None,
	  /** If enabled (true) the rule defines a read only access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'privacy' kerberos security mode. */
		kerberos5pReadOnly: Option[Boolean] = None,
	  /** If enabled (true) the rule defines read and write access for clients matching the 'allowedClients' specification. It enables nfs clients to mount using 'privacy' kerberos security mode. The 'kerberos5pReadOnly' value be ignored if this is enabled. */
		kerberos5pReadWrite: Option[Boolean] = None
	)
	
	object MountOption {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOLS_UNSPECIFIED, NFSV3, NFSV4, SMB }
	}
	case class MountOption(
	  /** Export string */
		`export`: Option[String] = None,
	  /** Full export string */
		exportFull: Option[String] = None,
	  /** Protocol to mount with. */
		protocol: Option[Schema.MountOption.ProtocolEnum] = None,
	  /** Instructions for mounting */
		instructions: Option[String] = None
	)
	
	case class SnapshotPolicy(
	  /** If enabled, make snapshots automatically according to the schedules. Default is false. */
		enabled: Option[Boolean] = None,
	  /** Hourly schedule policy. */
		hourlySchedule: Option[Schema.HourlySchedule] = None,
	  /** Daily schedule policy. */
		dailySchedule: Option[Schema.DailySchedule] = None,
	  /** Weekly schedule policy. */
		weeklySchedule: Option[Schema.WeeklySchedule] = None,
	  /** Monthly schedule policy. */
		monthlySchedule: Option[Schema.MonthlySchedule] = None
	)
	
	case class HourlySchedule(
	  /** The maximum number of Snapshots to keep for the hourly schedule */
		snapshotsToKeep: Option[BigDecimal] = None,
	  /** Set the minute of the hour to start the snapshot (0-59), defaults to the top of the hour (0). */
		minute: Option[BigDecimal] = None
	)
	
	case class DailySchedule(
	  /** The maximum number of Snapshots to keep for the hourly schedule */
		snapshotsToKeep: Option[BigDecimal] = None,
	  /** Set the minute of the hour to start the snapshot (0-59), defaults to the top of the hour (0). */
		minute: Option[BigDecimal] = None,
	  /** Set the hour to start the snapshot (0-23), defaults to midnight (0). */
		hour: Option[BigDecimal] = None
	)
	
	case class WeeklySchedule(
	  /** The maximum number of Snapshots to keep for the hourly schedule */
		snapshotsToKeep: Option[BigDecimal] = None,
	  /** Set the minute of the hour to start the snapshot (0-59), defaults to the top of the hour (0). */
		minute: Option[BigDecimal] = None,
	  /** Set the hour to start the snapshot (0-23), defaults to midnight (0). */
		hour: Option[BigDecimal] = None,
	  /** Set the day or days of the week to make a snapshot. Accepts a comma separated days of the week. Defaults to 'Sunday'. */
		day: Option[String] = None
	)
	
	case class MonthlySchedule(
	  /** The maximum number of Snapshots to keep for the hourly schedule */
		snapshotsToKeep: Option[BigDecimal] = None,
	  /** Set the minute of the hour to start the snapshot (0-59), defaults to the top of the hour (0). */
		minute: Option[BigDecimal] = None,
	  /** Set the hour to start the snapshot (0-23), defaults to midnight (0). */
		hour: Option[BigDecimal] = None,
	  /** Set the day or days of the month to make a snapshot (1-31). Accepts a comma separated number of days. Defaults to '1'. */
		daysOfMonth: Option[String] = None
	)
	
	case class RestoreParameters(
	  /** Full name of the snapshot resource. Format: projects/{project}/locations/{location}/volumes/{volume}/snapshots/{snapshot} */
		sourceSnapshot: Option[String] = None,
	  /** Full name of the backup resource. Format: projects/{project}/locations/{location}/backupVaults/{backup_vault_id}/backups/{backup_id} */
		sourceBackup: Option[String] = None
	)
	
	case class BackupConfig(
	  /** Optional. When specified, schedule backups will be created based on the policy configuration. */
		backupPolicies: Option[List[String]] = None,
	  /** Optional. Name of backup vault. Format: projects/{project_id}/locations/{location}/backupVaults/{backup_vault_id} */
		backupVault: Option[String] = None,
	  /** Optional. When set to true, scheduled backup is enabled on the volume. This field should be nil when there's no backup policy attached. */
		scheduledBackupEnabled: Option[Boolean] = None,
	  /** Output only. Total size of all backups in a chain in bytes = baseline backup size + sum(incremental backup size). */
		backupChainBytes: Option[String] = None
	)
	
	object TieringPolicy {
		enum TierActionEnum extends Enum[TierActionEnum] { case TIER_ACTION_UNSPECIFIED, ENABLED, PAUSED }
	}
	case class TieringPolicy(
	  /** Optional. Flag indicating if the volume has tiering policy enable/pause. Default is PAUSED. */
		tierAction: Option[Schema.TieringPolicy.TierActionEnum] = None,
	  /** Optional. Time in days to mark the volume's data block as cold and make it eligible for tiering, can be range from 7-183. Default is 31. */
		coolingThresholdDays: Option[Int] = None
	)
	
	case class HybridReplicationParameters(
	  /** Required. Desired name for the replication of this volume. */
		replication: Option[String] = None,
	  /** Required. Name of the user's local source volume to be peered with the destination volume. */
		peerVolumeName: Option[String] = None,
	  /** Required. Name of the user's local source cluster to be peered with the destination cluster. */
		peerClusterName: Option[String] = None,
	  /** Required. Name of the user's local source vserver svm to be peered with the destination vserver svm. */
		peerSvmName: Option[String] = None,
	  /** Required. List of node ip addresses to be peered with. */
		peerIpAddresses: Option[List[String]] = None,
	  /** Optional. Name of source cluster location associated with the Hybrid replication. This is a free-form field for the display purpose only. */
		clusterLocation: Option[String] = None,
	  /** Optional. Description of the replication. */
		description: Option[String] = None,
	  /** Optional. Labels to be added to the replication as the key value pairs. */
		labels: Option[Map[String, String]] = None
	)
	
	case class RevertVolumeRequest(
	  /** Required. The snapshot resource ID, in the format 'my-snapshot', where the specified ID is the {snapshot_id} of the fully qualified name like projects/{project_id}/locations/{location_id}/volumes/{volume_id}/snapshots/{snapshot_id} */
		snapshotId: Option[String] = None
	)
	
	case class ListSnapshotsResponse(
	  /** A list of snapshots in the project for the specified volume. */
		snapshots: Option[List[Schema.Snapshot]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Snapshot {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, DELETING, UPDATING, DISABLED, ERROR }
	}
	case class Snapshot(
	  /** Identifier. The resource name of the snapshot. Format: `projects/{project_id}/locations/{location}/volumes/{volume_id}/snapshots/{snapshot_id}`. */
		name: Option[String] = None,
	  /** Output only. The snapshot state. */
		state: Option[Schema.Snapshot.StateEnum] = None,
	  /** Output only. State details of the storage pool */
		stateDetails: Option[String] = None,
	  /** A description of the snapshot with 2048 characters or less. Requests with longer descriptions will be rejected. */
		description: Option[String] = None,
	  /** Output only. Current storage usage for the snapshot in bytes. */
		usedBytes: Option[BigDecimal] = None,
	  /** Output only. The time when the snapshot was created. */
		createTime: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListActiveDirectoriesResponse(
	  /** The list of active directories. */
		activeDirectories: Option[List[Schema.ActiveDirectory]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ActiveDirectory {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, IN_USE, DELETING, ERROR, DIAGNOSING }
	}
	case class ActiveDirectory(
	  /** Identifier. The resource name of the active directory. Format: `projects/{project_number}/locations/{location_id}/activeDirectories/{active_directory_id}`. */
		name: Option[String] = None,
	  /** Output only. Create time of the active directory. */
		createTime: Option[String] = None,
	  /** Output only. The state of the AD. */
		state: Option[Schema.ActiveDirectory.StateEnum] = None,
	  /** Required. Name of the Active Directory domain */
		domain: Option[String] = None,
	  /** The Active Directory site the service will limit Domain Controller discovery too. */
		site: Option[String] = None,
	  /** Required. Comma separated list of DNS server IP addresses for the Active Directory domain. */
		dns: Option[String] = None,
	  /** Required. NetBIOSPrefix is used as a prefix for SMB server name. */
		netBiosPrefix: Option[String] = None,
	  /** The Organizational Unit (OU) within the Windows Active Directory the user belongs to. */
		organizationalUnit: Option[String] = None,
	  /** If enabled, AES encryption will be enabled for SMB communication. */
		aesEncryption: Option[Boolean] = None,
	  /** Required. Username of the Active Directory domain administrator. */
		username: Option[String] = None,
	  /** Required. Password of the Active Directory domain administrator. */
		password: Option[String] = None,
	  /** Optional. Users to be added to the Built-in Backup Operator active directory group. */
		backupOperators: Option[List[String]] = None,
	  /** Optional. Users to be added to the Built-in Admininstrators group. */
		administrators: Option[List[String]] = None,
	  /** Optional. Domain users to be given the SeSecurityPrivilege. */
		securityOperators: Option[List[String]] = None,
	  /** Name of the active directory machine. This optional parameter is used only while creating kerberos volume */
		kdcHostname: Option[String] = None,
	  /** KDC server IP address for the active directory machine. */
		kdcIp: Option[String] = None,
	  /** If enabled, will allow access to local users and LDAP users. If access is needed for only LDAP users, it has to be disabled. */
		nfsUsersWithLdap: Option[Boolean] = None,
	  /** Description of the active directory. */
		description: Option[String] = None,
	  /** Specifies whether or not the LDAP traffic needs to be signed. */
		ldapSigning: Option[Boolean] = None,
	  /** If enabled, traffic between the SMB server to Domain Controller (DC) will be encrypted. */
		encryptDcConnections: Option[Boolean] = None,
	  /** Labels for the active directory. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The state details of the Active Directory. */
		stateDetails: Option[String] = None
	)
	
	case class ListKmsConfigsResponse(
	  /** The list of KmsConfigs */
		kmsConfigs: Option[List[Schema.KmsConfig]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object KmsConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, DELETING, UPDATING, IN_USE, ERROR, KEY_CHECK_PENDING, KEY_NOT_REACHABLE, DISABLING, DISABLED, MIGRATING }
	}
	case class KmsConfig(
	  /** Identifier. Name of the KmsConfig. */
		name: Option[String] = None,
	  /** Required. Customer managed crypto key resource full name. Format: projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{key}. */
		cryptoKeyName: Option[String] = None,
	  /** Output only. State of the KmsConfig. */
		state: Option[Schema.KmsConfig.StateEnum] = None,
	  /** Output only. State details of the KmsConfig. */
		stateDetails: Option[String] = None,
	  /** Output only. Create time of the KmsConfig. */
		createTime: Option[String] = None,
	  /** Description of the KmsConfig. */
		description: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Instructions to provide the access to the customer provided encryption key. */
		instructions: Option[String] = None,
	  /** Output only. The Service account which will have access to the customer provided encryption key. */
		serviceAccount: Option[String] = None
	)
	
	case class EncryptVolumesRequest(
	
	)
	
	case class VerifyKmsConfigRequest(
	
	)
	
	case class VerifyKmsConfigResponse(
	  /** Output only. If the customer key configured correctly to the encrypt volume. */
		healthy: Option[Boolean] = None,
	  /** Output only. Error message if config is not healthy. */
		healthError: Option[String] = None,
	  /** Output only. Instructions for the customers to provide the access to the encryption key. */
		instructions: Option[String] = None
	)
	
	case class ListReplicationsResponse(
	  /** A list of replications in the project for the specified volume. */
		replications: Option[List[Schema.Replication]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Replication {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, DELETING, ERROR, PENDING_CLUSTER_PEERING, PENDING_SVM_PEERING }
		enum RoleEnum extends Enum[RoleEnum] { case REPLICATION_ROLE_UNSPECIFIED, SOURCE, DESTINATION }
		enum ReplicationScheduleEnum extends Enum[ReplicationScheduleEnum] { case REPLICATION_SCHEDULE_UNSPECIFIED, EVERY_10_MINUTES, HOURLY, DAILY }
		enum MirrorStateEnum extends Enum[MirrorStateEnum] { case MIRROR_STATE_UNSPECIFIED, PREPARING, MIRRORED, STOPPED, TRANSFERRING, BASELINE_TRANSFERRING, ABORTED }
		enum HybridReplicationTypeEnum extends Enum[HybridReplicationTypeEnum] { case HYBRID_REPLICATION_TYPE_UNSPECIFIED, MIGRATION, CONTINUOUS_REPLICATION }
	}
	case class Replication(
	  /** Identifier. The resource name of the Replication. Format: `projects/{project_id}/locations/{location}/volumes/{volume_id}/replications/{replication_id}`. */
		name: Option[String] = None,
	  /** Output only. State of the replication. */
		state: Option[Schema.Replication.StateEnum] = None,
	  /** Output only. State details of the replication. */
		stateDetails: Option[String] = None,
	  /** Output only. Indicates whether this points to source or destination. */
		role: Option[Schema.Replication.RoleEnum] = None,
	  /** Required. Indicates the schedule for replication. */
		replicationSchedule: Option[Schema.Replication.ReplicationScheduleEnum] = None,
	  /** Output only. Indicates the state of mirroring. */
		mirrorState: Option[Schema.Replication.MirrorStateEnum] = None,
	  /** Output only. Condition of the relationship. Can be one of the following: - true: The replication relationship is healthy. It has not missed the most recent scheduled transfer. - false: The replication relationship is not healthy. It has missed the most recent scheduled transfer. */
		healthy: Option[Boolean] = None,
	  /** Output only. Replication create time. */
		createTime: Option[String] = None,
	  /** Output only. Full name of destination volume resource. Example : "projects/{project}/locations/{location}/volumes/{volume_id}" */
		destinationVolume: Option[String] = None,
	  /** Output only. Replication transfer statistics. */
		transferStats: Option[Schema.TransferStats] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** A description about this replication relationship. */
		description: Option[String] = None,
	  /** Required. Input only. Destination volume parameters */
		destinationVolumeParameters: Option[Schema.DestinationVolumeParameters] = None,
	  /** Output only. Full name of source volume resource. Example : "projects/{project}/locations/{location}/volumes/{volume_id}" */
		sourceVolume: Option[String] = None,
	  /** Output only. Hybrid peering details. */
		hybridPeeringDetails: Option[Schema.HybridPeeringDetails] = None,
	  /** Optional. Location of the user cluster. */
		clusterLocation: Option[String] = None,
	  /** Output only. Type of the hybrid replication. */
		hybridReplicationType: Option[Schema.Replication.HybridReplicationTypeEnum] = None
	)
	
	case class TransferStats(
	  /** Cumulative bytes trasferred so far for the replication relatinonship. */
		transferBytes: Option[String] = None,
	  /** Cumulative time taken across all transfers for the replication relationship. */
		totalTransferDuration: Option[String] = None,
	  /** Last transfer size in bytes. */
		lastTransferBytes: Option[String] = None,
	  /** Time taken during last transfer. */
		lastTransferDuration: Option[String] = None,
	  /** Lag duration indicates the duration by which Destination region volume content lags behind the primary region volume content. */
		lagDuration: Option[String] = None,
	  /** Time when progress was updated last. */
		updateTime: Option[String] = None,
	  /** Time when last transfer completed. */
		lastTransferEndTime: Option[String] = None,
	  /** A message describing the cause of the last transfer failure. */
		lastTransferError: Option[String] = None
	)
	
	case class DestinationVolumeParameters(
	  /** Required. Existing destination StoragePool name. */
		storagePool: Option[String] = None,
	  /** Desired destination volume resource id. If not specified, source volume's resource id will be used. This value must start with a lowercase letter followed by up to 62 lowercase letters, numbers, or hyphens, and cannot end with a hyphen. */
		volumeId: Option[String] = None,
	  /** Destination volume's share name. If not specified, source volume's share name will be used. */
		shareName: Option[String] = None,
	  /** Description for the destination volume. */
		description: Option[String] = None
	)
	
	case class HybridPeeringDetails(
	  /** Optional. IP address of the subnet. */
		subnetIp: Option[String] = None,
	  /** Optional. Copy-paste-able commands to be used on user's ONTAP to accept peering requests. */
		command: Option[String] = None,
	  /** Optional. Expiration time for the peering command to be executed on user's ONTAP. */
		commandExpiryTime: Option[String] = None,
	  /** Optional. Temporary passphrase generated to accept cluster peering command. */
		passphrase: Option[String] = None
	)
	
	case class StopReplicationRequest(
	  /** Indicates whether to stop replication forcefully while data transfer is in progress. Warning! if force is true, this will abort any current transfers and can lead to data loss due to partial transfer. If force is false, stop replication will fail while data transfer is in progress and you will need to retry later. */
		force: Option[Boolean] = None
	)
	
	case class ResumeReplicationRequest(
	
	)
	
	case class ReverseReplicationDirectionRequest(
	
	)
	
	case class EstablishPeeringRequest(
	  /** Required. Name of the user's local source cluster to be peered with the destination cluster. */
		peerClusterName: Option[String] = None,
	  /** Required. Name of the user's local source vserver svm to be peered with the destination vserver svm. */
		peerSvmName: Option[String] = None,
	  /** Optional. List of IPv4 ip addresses to be used for peering. */
		peerIpAddresses: Option[List[String]] = None,
	  /** Required. Name of the user's local source volume to be peered with the destination volume. */
		peerVolumeName: Option[String] = None
	)
	
	case class SyncReplicationRequest(
	
	)
	
	object BackupVault {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, DELETING, ERROR, UPDATING }
	}
	case class BackupVault(
	  /** Identifier. The resource name of the backup vault. Format: `projects/{project_id}/locations/{location}/backupVaults/{backup_vault_id}`. */
		name: Option[String] = None,
	  /** Output only. The backup vault state. */
		state: Option[Schema.BackupVault.StateEnum] = None,
	  /** Output only. Create time of the backup vault. */
		createTime: Option[String] = None,
	  /** Description of the backup vault. */
		description: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListBackupVaultsResponse(
	  /** A list of backupVaults in the project for the specified location. */
		backupVaults: Option[List[Schema.BackupVault]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, UPLOADING, READY, DELETING, ERROR, UPDATING }
		enum BackupTypeEnum extends Enum[BackupTypeEnum] { case TYPE_UNSPECIFIED, MANUAL, SCHEDULED }
	}
	case class Backup(
	  /** Identifier. The resource name of the backup. Format: `projects/{project_id}/locations/{location}/backupVaults/{backup_vault_id}/backups/{backup_id}`. */
		name: Option[String] = None,
	  /** Output only. The backup state. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** A description of the backup with 2048 characters or less. Requests with longer descriptions will be rejected. */
		description: Option[String] = None,
	  /** Output only. Size of the file system when the backup was created. When creating a new volume from the backup, the volume capacity will have to be at least as big. */
		volumeUsageBytes: Option[String] = None,
	  /** Output only. Type of backup, manually created or created by a backup policy. */
		backupType: Option[Schema.Backup.BackupTypeEnum] = None,
	  /** Volume full name of this backup belongs to. Format: `projects/{projects_id}/locations/{location}/volumes/{volume_id}` */
		sourceVolume: Option[String] = None,
	  /** If specified, backup will be created from the given snapshot. If not specified, there will be a new snapshot taken to initiate the backup creation. Format: `projects/{project_id}/locations/{location}/volumes/{volume_id}/snapshots/{snapshot_id}` */
		sourceSnapshot: Option[String] = None,
	  /** Output only. The time when the backup was created. */
		createTime: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Total size of all backups in a chain in bytes = baseline backup size + sum(incremental backup size) */
		chainStorageBytes: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** A list of backups in the project. */
		backups: Option[List[Schema.Backup]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object BackupPolicy {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, DELETING, ERROR, UPDATING }
	}
	case class BackupPolicy(
	  /** Identifier. The resource name of the backup policy. Format: `projects/{project_id}/locations/{location}/backupPolicies/{backup_policy_id}`. */
		name: Option[String] = None,
	  /** Number of daily backups to keep. Note that the minimum daily backup limit is 2. */
		dailyBackupLimit: Option[Int] = None,
	  /** Number of weekly backups to keep. Note that the sum of daily, weekly and monthly backups should be greater than 1. */
		weeklyBackupLimit: Option[Int] = None,
	  /** Number of monthly backups to keep. Note that the sum of daily, weekly and monthly backups should be greater than 1. */
		monthlyBackupLimit: Option[Int] = None,
	  /** Description of the backup policy. */
		description: Option[String] = None,
	  /** If enabled, make backups automatically according to the schedules. This will be applied to all volumes that have this policy attached and enforced on volume level. If not specified, default is true. */
		enabled: Option[Boolean] = None,
	  /** Output only. The total number of volumes assigned by this backup policy. */
		assignedVolumeCount: Option[Int] = None,
	  /** Output only. The time when the backup policy was created. */
		createTime: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The backup policy state. */
		state: Option[Schema.BackupPolicy.StateEnum] = None
	)
	
	case class ListBackupPoliciesResponse(
	  /** The list of backup policies. */
		backupPolicies: Option[List[Schema.BackupPolicy]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListQuotaRulesResponse(
	  /** List of quota rules */
		quotaRules: Option[List[Schema.QuotaRule]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object QuotaRule {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INDIVIDUAL_USER_QUOTA, INDIVIDUAL_GROUP_QUOTA, DEFAULT_USER_QUOTA, DEFAULT_GROUP_QUOTA }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, UPDATING, DELETING, READY, ERROR }
	}
	case class QuotaRule(
	  /** Identifier. The resource name of the active directory. Format: `projects/{project_number}/locations/{location_id}/quotaRules/{quota_rule_id}`. */
		name: Option[String] = None,
	  /** Optional. The quota rule applies to the specified user or group, identified by a Unix UID/GID, Windows SID, or null for default. */
		target: Option[String] = None,
	  /** Required. The type of quota rule. */
		`type`: Option[Schema.QuotaRule.TypeEnum] = None,
	  /** Required. The maximum allowed disk space in MiB. */
		diskLimitMib: Option[Int] = None,
	  /** Output only. State of the quota rule */
		state: Option[Schema.QuotaRule.StateEnum] = None,
	  /** Output only. State details of the quota rule */
		stateDetails: Option[String] = None,
	  /** Output only. Create time of the quota rule */
		createTime: Option[String] = None,
	  /** Optional. Description of the quota rule */
		description: Option[String] = None,
	  /** Optional. Labels of the quota rule */
		labels: Option[Map[String, String]] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been canceled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object LocationMetadata {
		enum SupportedServiceLevelsEnum extends Enum[SupportedServiceLevelsEnum] { case SERVICE_LEVEL_UNSPECIFIED, PREMIUM, EXTREME, STANDARD, FLEX }
	}
	case class LocationMetadata(
	  /** Output only. Supported service levels in a location. */
		supportedServiceLevels: Option[List[Schema.LocationMetadata.SupportedServiceLevelsEnum]] = None
	)
}
