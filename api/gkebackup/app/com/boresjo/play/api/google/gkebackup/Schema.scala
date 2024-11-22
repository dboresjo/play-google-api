package com.boresjo.play.api.google.gkebackup

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	object BackupPlan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CLUSTER_PENDING, PROVISIONING, READY, FAILED, DEACTIVATED, DELETING }
	}
	case class BackupPlan(
	  /** Output only. The full name of the BackupPlan resource. Format: `projects/&#42;/locations/&#42;/backupPlans/&#42;` */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) format. */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this BackupPlan resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this BackupPlan resource was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User specified descriptive string for this BackupPlan. */
		description: Option[String] = None,
	  /** Required. Immutable. The source cluster from which Backups will be created via this BackupPlan. Valid formats: - `projects/&#42;/locations/&#42;/clusters/&#42;` - `projects/&#42;/zones/&#42;/clusters/&#42;` */
		cluster: Option[String] = None,
	  /** Optional. RetentionPolicy governs lifecycle of Backups created under this plan. */
		retentionPolicy: Option[Schema.RetentionPolicy] = None,
	  /** Optional. A set of custom labels supplied by user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Defines a schedule for automatic Backup creation via this BackupPlan. */
		backupSchedule: Option[Schema.Schedule] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a backup plan from overwriting each other. It is strongly suggested that systems make use of the 'etag' in the read-modify-write cycle to perform BackupPlan updates in order to avoid race conditions: An `etag` is returned in the response to `GetBackupPlan`, and systems are expected to put that etag in the request to `UpdateBackupPlan` or `DeleteBackupPlan` to ensure that their change will be applied to the same version of the resource. */
		etag: Option[String] = None,
	  /** Optional. This flag indicates whether this BackupPlan has been deactivated. Setting this field to True locks the BackupPlan such that no further updates will be allowed (except deletes), including the deactivated field itself. It also prevents any new Backups from being created via this BackupPlan (including scheduled Backups). Default: False */
		deactivated: Option[Boolean] = None,
	  /** Optional. Defines the configuration of Backups created via this BackupPlan. */
		backupConfig: Option[Schema.BackupConfig] = None,
	  /** Output only. The number of Kubernetes Pods backed up in the last successful Backup created via this BackupPlan. */
		protectedPodCount: Option[Int] = None,
	  /** Output only. State of the BackupPlan. This State field reflects the various stages a BackupPlan can be in during the Create operation. It will be set to "DEACTIVATED" if the BackupPlan is deactivated on an Update */
		state: Option[Schema.BackupPlan.StateEnum] = None,
	  /** Output only. Human-readable description of why BackupPlan is in the current `state` */
		stateReason: Option[String] = None,
	  /** Output only. A number that represents the current risk level of this BackupPlan from RPO perspective with 1 being no risk and 5 being highest risk. */
		rpoRiskLevel: Option[Int] = None,
	  /** Output only. Human-readable description of why the BackupPlan is in the current rpo_risk_level and action items if any. */
		rpoRiskReason: Option[String] = None
	)
	
	case class RetentionPolicy(
	  /** Optional. Minimum age for Backups created via this BackupPlan (in days). This field MUST be an integer value between 0-90 (inclusive). A Backup created under this BackupPlan will NOT be deletable until it reaches Backup's (create_time + backup_delete_lock_days). Updating this field of a BackupPlan does NOT affect existing Backups under it. Backups created AFTER a successful update will inherit the new value. Default: 0 (no delete blocking) */
		backupDeleteLockDays: Option[Int] = None,
	  /** Optional. The default maximum age of a Backup created via this BackupPlan. This field MUST be an integer value >= 0 and <= 365. If specified, a Backup created under this BackupPlan will be automatically deleted after its age reaches (create_time + backup_retain_days). If not specified, Backups created under this BackupPlan will NOT be subject to automatic deletion. Updating this field does NOT affect existing Backups under it. Backups created AFTER a successful update will automatically pick up the new value. NOTE: backup_retain_days must be >= backup_delete_lock_days. If cron_schedule is defined, then this must be <= 360 &#42; the creation interval. If rpo_config is defined, then this must be <= 360 &#42; target_rpo_minutes / (1440minutes/day). Default: 0 (no automatic deletion) */
		backupRetainDays: Option[Int] = None,
	  /** Optional. This flag denotes whether the retention policy of this BackupPlan is locked. If set to True, no further update is allowed on this policy, including the `locked` field itself. Default: False */
		locked: Option[Boolean] = None
	)
	
	case class Schedule(
	  /** Optional. A standard [cron](https://wikipedia.com/wiki/cron) string that defines a repeating schedule for creating Backups via this BackupPlan. This is mutually exclusive with the rpo_config field since at most one schedule can be defined for a BackupPlan. If this is defined, then backup_retain_days must also be defined. Default (empty): no automatic backup creation will occur. */
		cronSchedule: Option[String] = None,
	  /** Optional. This flag denotes whether automatic Backup creation is paused for this BackupPlan. Default: False */
		paused: Option[Boolean] = None,
	  /** Optional. Defines the RPO schedule configuration for this BackupPlan. This is mutually exclusive with the cron_schedule field since at most one schedule can be defined for a BackupPLan. If this is defined, then backup_retain_days must also be defined. Default (empty): no automatic backup creation will occur. */
		rpoConfig: Option[Schema.RpoConfig] = None,
	  /** Output only. Start time of next scheduled backup under this BackupPlan by either cron_schedule or rpo config. */
		nextScheduledBackupTime: Option[String] = None
	)
	
	case class RpoConfig(
	  /** Required. Defines the target RPO for the BackupPlan in minutes, which means the target maximum data loss in time that is acceptable for this BackupPlan. This must be at least 60, i.e., 1 hour, and at most 86400, i.e., 60 days. */
		targetRpoMinutes: Option[Int] = None,
	  /** Optional. User specified time windows during which backup can NOT happen for this BackupPlan - backups should start and finish outside of any given exclusion window. Note: backup jobs will be scheduled to start and finish outside the duration of the window as much as possible, but running jobs will not get canceled when it runs into the window. All the time and date values in exclusion_windows entry in the API are in UTC. We only allow <=1 recurrence (daily or weekly) exclusion window for a BackupPlan while no restriction on number of single occurrence windows. */
		exclusionWindows: Option[List[Schema.ExclusionWindow]] = None
	)
	
	case class ExclusionWindow(
	  /** Required. Specifies the start time of the window using time of the day in UTC. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Required. Specifies duration of the window. Duration must be >= 5 minutes and < (target RPO - 20 minutes). Additional restrictions based on the recurrence type to allow some time for backup to happen: - single_occurrence_date: no restriction, but UI may warn about this when duration >= target RPO - daily window: duration < 24 hours - weekly window: - days of week includes all seven days of a week: duration < 24 hours - all other weekly window: duration < 168 hours (i.e., 24 &#42; 7 hours) */
		duration: Option[String] = None,
	  /** No recurrence. The exclusion window occurs only once and on this date in UTC. */
		singleOccurrenceDate: Option[Schema.Date] = None,
	  /** The exclusion window occurs every day if set to "True". Specifying this field to "False" is an error. */
		daily: Option[Boolean] = None,
	  /** The exclusion window occurs on these days of each week in UTC. */
		daysOfWeek: Option[Schema.DayOfWeekList] = None
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
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	object DayOfWeekList {
		enum DaysOfWeekEnum extends Enum[DaysOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class DayOfWeekList(
	  /** Optional. A list of days of week. */
		daysOfWeek: Option[List[Schema.DayOfWeekList.DaysOfWeekEnum]] = None
	)
	
	case class BackupConfig(
	  /** If True, include all namespaced resources */
		allNamespaces: Option[Boolean] = None,
	  /** If set, include just the resources in the listed namespaces. */
		selectedNamespaces: Option[Schema.Namespaces] = None,
	  /** If set, include just the resources referenced by the listed ProtectedApplications. */
		selectedApplications: Option[Schema.NamespacedNames] = None,
	  /** Optional. This flag specifies whether volume data should be backed up when PVCs are included in the scope of a Backup. Default: False */
		includeVolumeData: Option[Boolean] = None,
	  /** Optional. This flag specifies whether Kubernetes Secret resources should be included when they fall into the scope of Backups. Default: False */
		includeSecrets: Option[Boolean] = None,
	  /** Optional. This defines a customer managed encryption key that will be used to encrypt the "config" portion (the Kubernetes resources) of Backups created via this plan. Default (empty): Config backup artifacts will not be encrypted. */
		encryptionKey: Option[Schema.EncryptionKey] = None,
	  /** Optional. If false, Backups will fail when Backup for GKE detects Kubernetes configuration that is non-standard or requires additional setup to restore. Default: False */
		permissiveMode: Option[Boolean] = None
	)
	
	case class Namespaces(
	  /** Optional. A list of Kubernetes Namespaces. */
		namespaces: Option[List[String]] = None
	)
	
	case class NamespacedNames(
	  /** Optional. A list of namespaced Kubernetes resources. */
		namespacedNames: Option[List[Schema.NamespacedName]] = None
	)
	
	case class NamespacedName(
	  /** Optional. The Namespace of the Kubernetes resource. */
		namespace: Option[String] = None,
	  /** Optional. The name of the Kubernetes resource. */
		name: Option[String] = None
	)
	
	case class EncryptionKey(
	  /** Optional. Google Cloud KMS encryption key. Format: `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;` */
		gcpKmsEncryptionKey: Option[String] = None
	)
	
	case class ListBackupPlansResponse(
	  /** The list of BackupPlans matching the given criteria. */
		backupPlans: Option[List[Schema.BackupPlan]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListBackupPlans` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, IN_PROGRESS, SUCCEEDED, FAILED, DELETING }
	}
	case class Backup(
	  /** Output only. The fully qualified name of the Backup. `projects/&#42;/locations/&#42;/backupPlans/&#42;/backups/&#42;` */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID4](https://en.wikipedia.org/wiki/Universally_unique_identifier) */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this Backup resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this Backup resource was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. This flag indicates whether this Backup resource was created manually by a user or via a schedule in the BackupPlan. A value of True means that the Backup was created manually. */
		manual: Option[Boolean] = None,
	  /** Optional. A set of custom labels supplied by user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Minimum age for this Backup (in days). If this field is set to a non-zero value, the Backup will be "locked" against deletion (either manual or automatic deletion) for the number of days provided (measured from the creation time of the Backup). MUST be an integer value between 0-90 (inclusive). Defaults to parent BackupPlan's backup_delete_lock_days setting and may only be increased (either at creation time or in a subsequent update). */
		deleteLockDays: Option[Int] = None,
	  /** Output only. The time at which an existing delete lock will expire for this backup (calculated from create_time + delete_lock_days). */
		deleteLockExpireTime: Option[String] = None,
	  /** Optional. The age (in days) after which this Backup will be automatically deleted. Must be an integer value >= 0: - If 0, no automatic deletion will occur for this Backup. - If not 0, this must be >= delete_lock_days and <= 365. Once a Backup is created, this value may only be increased. Defaults to the parent BackupPlan's backup_retain_days value. */
		retainDays: Option[Int] = None,
	  /** Output only. The time at which this Backup will be automatically deleted (calculated from create_time + retain_days). */
		retainExpireTime: Option[String] = None,
	  /** Output only. The customer managed encryption key that was used to encrypt the Backup's artifacts. Inherited from the parent BackupPlan's encryption_key value. */
		encryptionKey: Option[Schema.EncryptionKey] = None,
	  /** Output only. If True, all namespaces were included in the Backup. */
		allNamespaces: Option[Boolean] = None,
	  /** Output only. If set, the list of namespaces that were included in the Backup. */
		selectedNamespaces: Option[Schema.Namespaces] = None,
	  /** Output only. If set, the list of ProtectedApplications whose resources were included in the Backup. */
		selectedApplications: Option[Schema.NamespacedNames] = None,
	  /** Output only. Whether or not the Backup contains volume data. Controlled by the parent BackupPlan's include_volume_data value. */
		containsVolumeData: Option[Boolean] = None,
	  /** Output only. Whether or not the Backup contains Kubernetes Secrets. Controlled by the parent BackupPlan's include_secrets value. */
		containsSecrets: Option[Boolean] = None,
	  /** Output only. Information about the GKE cluster from which this Backup was created. */
		clusterMetadata: Option[Schema.ClusterMetadata] = None,
	  /** Output only. Current state of the Backup */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. Human-readable description of why the backup is in the current `state`. */
		stateReason: Option[String] = None,
	  /** Output only. Completion time of the Backup */
		completeTime: Option[String] = None,
	  /** Output only. The total number of Kubernetes resources included in the Backup. */
		resourceCount: Option[Int] = None,
	  /** Output only. The total number of volume backups contained in the Backup. */
		volumeCount: Option[Int] = None,
	  /** Output only. The total size of the Backup in bytes = config backup size + sum(volume backup sizes) */
		sizeBytes: Option[String] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a backup from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform backup updates in order to avoid race conditions: An `etag` is returned in the response to `GetBackup`, and systems are expected to put that etag in the request to `UpdateBackup` or `DeleteBackup` to ensure that their change will be applied to the same version of the resource. */
		etag: Option[String] = None,
	  /** Optional. User specified descriptive string for this Backup. */
		description: Option[String] = None,
	  /** Output only. The total number of Kubernetes Pods contained in the Backup. */
		podCount: Option[Int] = None,
	  /** Output only. The size of the config backup in bytes. */
		configBackupSizeBytes: Option[String] = None,
	  /** Output only. If false, Backup will fail when Backup for GKE detects Kubernetes configuration that is non-standard or requires additional setup to restore. Inherited from the parent BackupPlan's permissive_mode value. */
		permissiveMode: Option[Boolean] = None
	)
	
	case class ClusterMetadata(
	  /** Output only. The source cluster from which this Backup was created. Valid formats: - `projects/&#42;/locations/&#42;/clusters/&#42;` - `projects/&#42;/zones/&#42;/clusters/&#42;` This is inherited from the parent BackupPlan's cluster field. */
		cluster: Option[String] = None,
	  /** Output only. The Kubernetes server version of the source cluster. */
		k8sVersion: Option[String] = None,
	  /** Output only. A list of the Backup for GKE CRD versions found in the cluster. */
		backupCrdVersions: Option[Map[String, String]] = None,
	  /** Output only. GKE version */
		gkeVersion: Option[String] = None,
	  /** Output only. Anthos version */
		anthosVersion: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** The list of Backups matching the given criteria. */
		backups: Option[List[Schema.Backup]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListBackups` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None
	)
	
	case class ListVolumeBackupsResponse(
	  /** The list of VolumeBackups matching the given criteria. */
		volumeBackups: Option[List[Schema.VolumeBackup]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListVolumeBackups` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None
	)
	
	object VolumeBackup {
		enum FormatEnum extends Enum[FormatEnum] { case VOLUME_BACKUP_FORMAT_UNSPECIFIED, GCE_PERSISTENT_DISK }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, SNAPSHOTTING, UPLOADING, SUCCEEDED, FAILED, DELETING }
	}
	case class VolumeBackup(
	  /** Output only. The full name of the VolumeBackup resource. Format: `projects/&#42;/locations/&#42;/backupPlans/&#42;/backups/&#42;/volumeBackups/&#42;`. */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) format. */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this VolumeBackup resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this VolumeBackup resource was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. A reference to the source Kubernetes PVC from which this VolumeBackup was created. */
		sourcePvc: Option[Schema.NamespacedName] = None,
	  /** Output only. A storage system-specific opaque handle to the underlying volume backup. */
		volumeBackupHandle: Option[String] = None,
	  /** Output only. The format used for the volume backup. */
		format: Option[Schema.VolumeBackup.FormatEnum] = None,
	  /** Output only. The aggregate size of the underlying artifacts associated with this VolumeBackup in the backup storage. This may change over time when multiple backups of the same volume share the same backup storage location. In particular, this is likely to increase in size when the immediately preceding backup of the same volume is deleted. */
		storageBytes: Option[String] = None,
	  /** Output only. The minimum size of the disk to which this VolumeBackup can be restored. */
		diskSizeBytes: Option[String] = None,
	  /** Output only. The timestamp when the associated underlying volume backup operation completed. */
		completeTime: Option[String] = None,
	  /** Output only. The current state of this VolumeBackup. */
		state: Option[Schema.VolumeBackup.StateEnum] = None,
	  /** Output only. A human readable message explaining why the VolumeBackup is in its current state. */
		stateMessage: Option[String] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a volume backup from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform volume backup updates in order to avoid race conditions. */
		etag: Option[String] = None
	)
	
	object RestorePlan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CLUSTER_PENDING, READY, FAILED, DELETING }
	}
	case class RestorePlan(
	  /** Output only. The full name of the RestorePlan resource. Format: `projects/&#42;/locations/&#42;/restorePlans/&#42;`. */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) format. */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this RestorePlan resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this RestorePlan resource was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User specified descriptive string for this RestorePlan. */
		description: Option[String] = None,
	  /** Required. Immutable. A reference to the BackupPlan from which Backups may be used as the source for Restores created via this RestorePlan. Format: `projects/&#42;/locations/&#42;/backupPlans/&#42;`. */
		backupPlan: Option[String] = None,
	  /** Required. Immutable. The target cluster into which Restores created via this RestorePlan will restore data. NOTE: the cluster's region must be the same as the RestorePlan. Valid formats: - `projects/&#42;/locations/&#42;/clusters/&#42;` - `projects/&#42;/zones/&#42;/clusters/&#42;` */
		cluster: Option[String] = None,
	  /** Required. Configuration of Restores created via this RestorePlan. */
		restoreConfig: Option[Schema.RestoreConfig] = None,
	  /** Optional. A set of custom labels supplied by user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a restore from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform restore updates in order to avoid race conditions: An `etag` is returned in the response to `GetRestorePlan`, and systems are expected to put that etag in the request to `UpdateRestorePlan` or `DeleteRestorePlan` to ensure that their change will be applied to the same version of the resource. */
		etag: Option[String] = None,
	  /** Output only. State of the RestorePlan. This State field reflects the various stages a RestorePlan can be in during the Create operation. */
		state: Option[Schema.RestorePlan.StateEnum] = None,
	  /** Output only. Human-readable description of why RestorePlan is in the current `state` */
		stateReason: Option[String] = None
	)
	
	object RestoreConfig {
		enum VolumeDataRestorePolicyEnum extends Enum[VolumeDataRestorePolicyEnum] { case VOLUME_DATA_RESTORE_POLICY_UNSPECIFIED, RESTORE_VOLUME_DATA_FROM_BACKUP, REUSE_VOLUME_HANDLE_FROM_BACKUP, NO_VOLUME_DATA_RESTORATION }
		enum ClusterResourceConflictPolicyEnum extends Enum[ClusterResourceConflictPolicyEnum] { case CLUSTER_RESOURCE_CONFLICT_POLICY_UNSPECIFIED, USE_EXISTING_VERSION, USE_BACKUP_VERSION }
		enum NamespacedResourceRestoreModeEnum extends Enum[NamespacedResourceRestoreModeEnum] { case NAMESPACED_RESOURCE_RESTORE_MODE_UNSPECIFIED, DELETE_AND_RESTORE, FAIL_ON_CONFLICT, MERGE_SKIP_ON_CONFLICT, MERGE_REPLACE_VOLUME_ON_CONFLICT, MERGE_REPLACE_ON_CONFLICT }
	}
	case class RestoreConfig(
	  /** Optional. Specifies the mechanism to be used to restore volume data. Default: VOLUME_DATA_RESTORE_POLICY_UNSPECIFIED (will be treated as NO_VOLUME_DATA_RESTORATION). */
		volumeDataRestorePolicy: Option[Schema.RestoreConfig.VolumeDataRestorePolicyEnum] = None,
	  /** Optional. Defines the behavior for handling the situation where cluster-scoped resources being restored already exist in the target cluster. This MUST be set to a value other than CLUSTER_RESOURCE_CONFLICT_POLICY_UNSPECIFIED if cluster_resource_restore_scope is not empty. */
		clusterResourceConflictPolicy: Option[Schema.RestoreConfig.ClusterResourceConflictPolicyEnum] = None,
	  /** Optional. Defines the behavior for handling the situation where sets of namespaced resources being restored already exist in the target cluster. This MUST be set to a value other than NAMESPACED_RESOURCE_RESTORE_MODE_UNSPECIFIED. */
		namespacedResourceRestoreMode: Option[Schema.RestoreConfig.NamespacedResourceRestoreModeEnum] = None,
	  /** Optional. Identifies the cluster-scoped resources to restore from the Backup. Not specifying it means NO cluster resource will be restored. */
		clusterResourceRestoreScope: Option[Schema.ClusterResourceRestoreScope] = None,
	  /** Restore all namespaced resources in the Backup if set to "True". Specifying this field to "False" is an error. */
		allNamespaces: Option[Boolean] = None,
	  /** A list of selected Namespaces to restore from the Backup. The listed Namespaces and all resources contained in them will be restored. */
		selectedNamespaces: Option[Schema.Namespaces] = None,
	  /** A list of selected ProtectedApplications to restore. The listed ProtectedApplications and all the resources to which they refer will be restored. */
		selectedApplications: Option[Schema.NamespacedNames] = None,
	  /** Do not restore any namespaced resources if set to "True". Specifying this field to "False" is not allowed. */
		noNamespaces: Option[Boolean] = None,
	  /** A list of selected namespaces excluded from restoration. All namespaces except those in this list will be restored. */
		excludedNamespaces: Option[Schema.Namespaces] = None,
	  /** Optional. A list of transformation rules to be applied against Kubernetes resources as they are selected for restoration from a Backup. Rules are executed in order defined - this order matters, as changes made by a rule may impact the filtering logic of subsequent rules. An empty list means no substitution will occur. */
		substitutionRules: Option[List[Schema.SubstitutionRule]] = None,
	  /** Optional. A list of transformation rules to be applied against Kubernetes resources as they are selected for restoration from a Backup. Rules are executed in order defined - this order matters, as changes made by a rule may impact the filtering logic of subsequent rules. An empty list means no transformation will occur. */
		transformationRules: Option[List[Schema.TransformationRule]] = None,
	  /** Optional. A table that binds volumes by their scope to a restore policy. Bindings must have a unique scope. Any volumes not scoped in the bindings are subject to the policy defined in volume_data_restore_policy. */
		volumeDataRestorePolicyBindings: Option[List[Schema.VolumeDataRestorePolicyBinding]] = None,
	  /** Optional. RestoreOrder contains custom ordering to use on a Restore. */
		restoreOrder: Option[Schema.RestoreOrder] = None
	)
	
	case class ClusterResourceRestoreScope(
	  /** Optional. A list of cluster-scoped resource group kinds to restore from the backup. If specified, only the selected resources will be restored. Mutually exclusive to any other field in the message. */
		selectedGroupKinds: Option[List[Schema.GroupKind]] = None,
	  /** Optional. A list of cluster-scoped resource group kinds to NOT restore from the backup. If specified, all valid cluster-scoped resources will be restored except for those specified in the list. Mutually exclusive to any other field in the message. */
		excludedGroupKinds: Option[List[Schema.GroupKind]] = None,
	  /** Optional. If True, all valid cluster-scoped resources will be restored. Mutually exclusive to any other field in the message. */
		allGroupKinds: Option[Boolean] = None,
	  /** Optional. If True, no cluster-scoped resources will be restored. This has the same restore scope as if the message is not defined. Mutually exclusive to any other field in the message. */
		noGroupKinds: Option[Boolean] = None
	)
	
	case class GroupKind(
	  /** Optional. API group string of a Kubernetes resource, e.g. "apiextensions.k8s.io", "storage.k8s.io", etc. Note: use empty string for core API group. */
		resourceGroup: Option[String] = None,
	  /** Optional. Kind of a Kubernetes resource, must be in UpperCamelCase (PascalCase) and singular form. E.g. "CustomResourceDefinition", "StorageClass", etc. */
		resourceKind: Option[String] = None
	)
	
	case class SubstitutionRule(
	  /** Optional. (Filtering parameter) Any resource subject to substitution must be contained within one of the listed Kubernetes Namespace in the Backup. If this field is not provided, no namespace filtering will be performed (all resources in all Namespaces, including all cluster-scoped resources, will be candidates for substitution). To mix cluster-scoped and namespaced resources in the same rule, use an empty string ("") as one of the target namespaces. */
		targetNamespaces: Option[List[String]] = None,
	  /** Optional. (Filtering parameter) Any resource subject to substitution must belong to one of the listed "types". If this field is not provided, no type filtering will be performed (all resources of all types matching previous filtering parameters will be candidates for substitution). */
		targetGroupKinds: Option[List[Schema.GroupKind]] = None,
	  /** Required. This is a [JSONPath] (https://kubernetes.io/docs/reference/kubectl/jsonpath/) expression that matches specific fields of candidate resources and it operates as both a filtering parameter (resources that are not matched with this expression will not be candidates for substitution) as well as a field identifier (identifies exactly which fields out of the candidate resources will be modified). */
		targetJsonPath: Option[String] = None,
	  /** Optional. (Filtering parameter) This is a [regular expression] (https://en.wikipedia.org/wiki/Regular_expression) that is compared against the fields matched by the target_json_path expression (and must also have passed the previous filters). Substitution will not be performed against fields whose value does not match this expression. If this field is NOT specified, then ALL fields matched by the target_json_path expression will undergo substitution. Note that an empty (e.g., "", rather than unspecified) value for this field will only match empty fields. */
		originalValuePattern: Option[String] = None,
	  /** Optional. This is the new value to set for any fields that pass the filtering and selection criteria. To remove a value from a Kubernetes resource, either leave this field unspecified, or set it to the empty string (""). */
		newValue: Option[String] = None
	)
	
	case class TransformationRule(
	  /** Required. A list of transformation rule actions to take against candidate resources. Actions are executed in order defined - this order matters, as they could potentially interfere with each other and the first operation could affect the outcome of the second operation. */
		fieldActions: Option[List[Schema.TransformationRuleAction]] = None,
	  /** Optional. This field is used to specify a set of fields that should be used to determine which resources in backup should be acted upon by the supplied transformation rule actions, and this will ensure that only specific resources are affected by transformation rule actions. */
		resourceFilter: Option[Schema.ResourceFilter] = None,
	  /** Optional. The description is a user specified string description of the transformation rule. */
		description: Option[String] = None
	)
	
	object TransformationRuleAction {
		enum OpEnum extends Enum[OpEnum] { case OP_UNSPECIFIED, REMOVE, MOVE, COPY, ADD, TEST, REPLACE }
	}
	case class TransformationRuleAction(
	  /** Required. op specifies the operation to perform. */
		op: Option[Schema.TransformationRuleAction.OpEnum] = None,
	  /** Optional. A string containing a JSON Pointer value that references the location in the target document to move the value from. */
		fromPath: Option[String] = None,
	  /** Optional. A string containing a JSON-Pointer value that references a location within the target document where the operation is performed. */
		path: Option[String] = None,
	  /** Optional. A string that specifies the desired value in string format to use for transformation. */
		value: Option[String] = None
	)
	
	case class ResourceFilter(
	  /** Optional. (Filtering parameter) Any resource subject to transformation must be contained within one of the listed Kubernetes Namespace in the Backup. If this field is not provided, no namespace filtering will be performed (all resources in all Namespaces, including all cluster-scoped resources, will be candidates for transformation). */
		namespaces: Option[List[String]] = None,
	  /** Optional. (Filtering parameter) Any resource subject to transformation must belong to one of the listed "types". If this field is not provided, no type filtering will be performed (all resources of all types matching previous filtering parameters will be candidates for transformation). */
		groupKinds: Option[List[Schema.GroupKind]] = None,
	  /** Optional. This is a [JSONPath] (https://github.com/json-path/JsonPath/blob/master/README.md) expression that matches specific fields of candidate resources and it operates as a filtering parameter (resources that are not matched with this expression will not be candidates for transformation). */
		jsonPath: Option[String] = None
	)
	
	object VolumeDataRestorePolicyBinding {
		enum PolicyEnum extends Enum[PolicyEnum] { case VOLUME_DATA_RESTORE_POLICY_UNSPECIFIED, RESTORE_VOLUME_DATA_FROM_BACKUP, REUSE_VOLUME_HANDLE_FROM_BACKUP, NO_VOLUME_DATA_RESTORATION }
		enum VolumeTypeEnum extends Enum[VolumeTypeEnum] { case VOLUME_TYPE_UNSPECIFIED, GCE_PERSISTENT_DISK }
	}
	case class VolumeDataRestorePolicyBinding(
	  /** Required. The VolumeDataRestorePolicy to apply when restoring volumes in scope. */
		policy: Option[Schema.VolumeDataRestorePolicyBinding.PolicyEnum] = None,
	  /** The volume type, as determined by the PVC's bound PV, to apply the policy to. */
		volumeType: Option[Schema.VolumeDataRestorePolicyBinding.VolumeTypeEnum] = None
	)
	
	case class RestoreOrder(
	  /** Optional. Contains a list of group kind dependency pairs provided by the customer, that is used by Backup for GKE to generate a group kind restore order. */
		groupKindDependencies: Option[List[Schema.GroupKindDependency]] = None
	)
	
	case class GroupKindDependency(
	  /** Required. The satisfying group kind must be restored first in order to satisfy the dependency. */
		satisfying: Option[Schema.GroupKind] = None,
	  /** Required. The requiring group kind requires that the other group kind be restored first. */
		requiring: Option[Schema.GroupKind] = None
	)
	
	case class ListRestorePlansResponse(
	  /** The list of RestorePlans matching the given criteria. */
		restorePlans: Option[List[Schema.RestorePlan]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListRestorePlans` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Restore {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, IN_PROGRESS, SUCCEEDED, FAILED, DELETING, VALIDATING }
	}
	case class Restore(
	  /** Output only. The full name of the Restore resource. Format: `projects/&#42;/locations/&#42;/restorePlans/&#42;/restores/&#42;` */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) format. */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this Restore resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this Restore resource was last updated. */
		updateTime: Option[String] = None,
	  /** User specified descriptive string for this Restore. */
		description: Option[String] = None,
	  /** Required. Immutable. A reference to the Backup used as the source from which this Restore will restore. Note that this Backup must be a sub-resource of the RestorePlan's backup_plan. Format: `projects/&#42;/locations/&#42;/backupPlans/&#42;/backups/&#42;`. */
		backup: Option[String] = None,
	  /** Output only. The target cluster into which this Restore will restore data. Valid formats: - `projects/&#42;/locations/&#42;/clusters/&#42;` - `projects/&#42;/zones/&#42;/clusters/&#42;` Inherited from parent RestorePlan's cluster value. */
		cluster: Option[String] = None,
	  /** Output only. Configuration of the Restore. Inherited from parent RestorePlan's restore_config. */
		restoreConfig: Option[Schema.RestoreConfig] = None,
	  /** A set of custom labels supplied by user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The current state of the Restore. */
		state: Option[Schema.Restore.StateEnum] = None,
	  /** Output only. Human-readable description of why the Restore is in its current state. */
		stateReason: Option[String] = None,
	  /** Output only. Timestamp of when the restore operation completed. */
		completeTime: Option[String] = None,
	  /** Output only. Number of resources restored during the restore execution. */
		resourcesRestoredCount: Option[Int] = None,
	  /** Output only. Number of resources excluded during the restore execution. */
		resourcesExcludedCount: Option[Int] = None,
	  /** Output only. Number of resources that failed to be restored during the restore execution. */
		resourcesFailedCount: Option[Int] = None,
	  /** Output only. Number of volumes restored during the restore execution. */
		volumesRestoredCount: Option[Int] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a restore from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform restore updates in order to avoid race conditions: An `etag` is returned in the response to `GetRestore`, and systems are expected to put that etag in the request to `UpdateRestore` or `DeleteRestore` to ensure that their change will be applied to the same version of the resource. */
		etag: Option[String] = None,
	  /** Optional. Immutable. Filters resources for `Restore`. If not specified, the scope of the restore will remain the same as defined in the `RestorePlan`. If this is specified and no resources are matched by the `inclusion_filters` or everyting is excluded by the `exclusion_filters`, nothing will be restored. This filter can only be specified if the value of namespaced_resource_restore_mode is set to `MERGE_SKIP_ON_CONFLICT`, `MERGE_REPLACE_VOLUME_ON_CONFLICT` or `MERGE_REPLACE_ON_CONFLICT`. */
		filter: Option[Schema.Filter] = None,
	  /** Optional. Immutable. Overrides the volume data restore policies selected in the Restore Config for override-scoped resources. */
		volumeDataRestorePolicyOverrides: Option[List[Schema.VolumeDataRestorePolicyOverride]] = None
	)
	
	case class Filter(
	  /** Optional. Selects resources for restoration. If specified, only resources which match `inclusion_filters` will be selected for restoration. A resource will be selected if it matches any `ResourceSelector` of the `inclusion_filters`. */
		inclusionFilters: Option[List[Schema.ResourceSelector]] = None,
	  /** Optional. Excludes resources from restoration. If specified, a resource will not be restored if it matches any `ResourceSelector` of the `exclusion_filters`. */
		exclusionFilters: Option[List[Schema.ResourceSelector]] = None
	)
	
	case class ResourceSelector(
	  /** Optional. Selects resources using their Kubernetes GroupKinds. If specified, only resources of provided GroupKind will be selected. */
		groupKind: Option[Schema.GroupKind] = None,
	  /** Optional. Selects resources using their resource names. If specified, only resources with the provided name will be selected. */
		name: Option[String] = None,
	  /** Optional. Selects resources using their namespaces. This only applies to namespace scoped resources and cannot be used for selecting cluster scoped resources. If specified, only resources in the provided namespace will be selected. If not specified, the filter will apply to both cluster scoped and namespace scoped resources (e.g. name or label). The [Namespace](https://pkg.go.dev/k8s.io/api/core/v1#Namespace) resource itself will be restored if and only if any resources within the namespace are restored. */
		namespace: Option[String] = None,
	  /** Optional. Selects resources using Kubernetes [labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/). If specified, a resource will be selected if and only if the resource has all of the provided labels and all the label values match. */
		labels: Option[Map[String, String]] = None
	)
	
	object VolumeDataRestorePolicyOverride {
		enum PolicyEnum extends Enum[PolicyEnum] { case VOLUME_DATA_RESTORE_POLICY_UNSPECIFIED, RESTORE_VOLUME_DATA_FROM_BACKUP, REUSE_VOLUME_HANDLE_FROM_BACKUP, NO_VOLUME_DATA_RESTORATION }
	}
	case class VolumeDataRestorePolicyOverride(
	  /** Required. The VolumeDataRestorePolicy to apply when restoring volumes in scope. */
		policy: Option[Schema.VolumeDataRestorePolicyOverride.PolicyEnum] = None,
	  /** A list of PVCs to apply the policy override to. */
		selectedPvcs: Option[Schema.NamespacedNames] = None
	)
	
	case class ListRestoresResponse(
	  /** The list of Restores matching the given criteria. */
		restores: Option[List[Schema.Restore]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListRestores` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListVolumeRestoresResponse(
	  /** The list of VolumeRestores matching the given criteria. */
		volumeRestores: Option[List[Schema.VolumeRestore]] = None,
	  /** A token which may be sent as page_token in a subsequent `ListVolumeRestores` call to retrieve the next page of results. If this field is omitted or empty, then there are no more results to return. */
		nextPageToken: Option[String] = None
	)
	
	object VolumeRestore {
		enum VolumeTypeEnum extends Enum[VolumeTypeEnum] { case VOLUME_TYPE_UNSPECIFIED, GCE_PERSISTENT_DISK }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, RESTORING, SUCCEEDED, FAILED, DELETING }
	}
	case class VolumeRestore(
	  /** Output only. Full name of the VolumeRestore resource. Format: `projects/&#42;/locations/&#42;/restorePlans/&#42;/restores/&#42;/volumeRestores/&#42;` */
		name: Option[String] = None,
	  /** Output only. Server generated global unique identifier of [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) format. */
		uid: Option[String] = None,
	  /** Output only. The timestamp when this VolumeRestore resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this VolumeRestore resource was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The full name of the VolumeBackup from which the volume will be restored. Format: `projects/&#42;/locations/&#42;/backupPlans/&#42;/backups/&#42;/volumeBackups/&#42;`. */
		volumeBackup: Option[String] = None,
	  /** Output only. The reference to the target Kubernetes PVC to be restored. */
		targetPvc: Option[Schema.NamespacedName] = None,
	  /** Output only. A storage system-specific opaque handler to the underlying volume created for the target PVC from the volume backup. */
		volumeHandle: Option[String] = None,
	  /** Output only. The type of volume provisioned */
		volumeType: Option[Schema.VolumeRestore.VolumeTypeEnum] = None,
	  /** Output only. The timestamp when the associated underlying volume restoration completed. */
		completeTime: Option[String] = None,
	  /** Output only. The current state of this VolumeRestore. */
		state: Option[Schema.VolumeRestore.StateEnum] = None,
	  /** Output only. A human readable message explaining why the VolumeRestore is in its current state. */
		stateMessage: Option[String] = None,
	  /** Output only. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a volume restore from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform volume restore updates in order to avoid race conditions. */
		etag: Option[String] = None
	)
	
	case class GetBackupIndexDownloadUrlResponse(
		signedUrl: Option[String] = None
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
}
