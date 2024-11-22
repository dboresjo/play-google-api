package com.boresjo.play.api.google.osconfig

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class CancelOperationRequest(
	
	)
	
	case class Empty(
	
	)
	
	object ProjectFeatureSettings {
		enum PatchAndConfigFeatureSetEnum extends Enum[PatchAndConfigFeatureSetEnum] { case PATCH_AND_CONFIG_FEATURE_SET_UNSPECIFIED, OSCONFIG_B, OSCONFIG_C }
	}
	case class ProjectFeatureSettings(
	  /** Required. Immutable. Name specifies the URL for the ProjectFeatureSettings resource: projects/project_id/locations/global/projectFeatureSettings. */
		name: Option[String] = None,
	  /** Set PatchAndConfigFeatureSet for the project. */
		patchAndConfigFeatureSet: Option[Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum] = None
	)
	
	case class ExecutePatchJobRequest(
	  /** Description of the patch job. Length of the description is limited to 1024 characters. */
		description: Option[String] = None,
	  /** Required. Instances to patch, either explicitly or filtered by some criteria such as zone or labels. */
		instanceFilter: Option[Schema.PatchInstanceFilter] = None,
	  /** Patch configuration being applied. If omitted, instances are patched using the default configurations. */
		patchConfig: Option[Schema.PatchConfig] = None,
	  /** Duration of the patch job. After the duration ends, the patch job times out. */
		duration: Option[String] = None,
	  /** If this patch is a dry-run only, instances are contacted but will do nothing. */
		dryRun: Option[Boolean] = None,
	  /** Display name for this patch job. This does not have to be unique. */
		displayName: Option[String] = None,
	  /** Rollout strategy of the patch job. */
		rollout: Option[Schema.PatchRollout] = None
	)
	
	case class PatchInstanceFilter(
	  /** Target all VM instances in the project. If true, no other criteria is permitted. */
		all: Option[Boolean] = None,
	  /** Targets VM instances matching ANY of these GroupLabels. This allows targeting of disparate groups of VM instances. */
		groupLabels: Option[List[Schema.PatchInstanceFilterGroupLabel]] = None,
	  /** Targets VM instances in ANY of these zones. Leave empty to target VM instances in any zone. */
		zones: Option[List[String]] = None,
	  /** Targets any of the VM instances specified. Instances are specified by their URI in the form `zones/[ZONE]/instances/[INSTANCE_NAME]`, `projects/[PROJECT_ID]/zones/[ZONE]/instances/[INSTANCE_NAME]`, or `https://www.googleapis.com/compute/v1/projects/[PROJECT_ID]/zones/[ZONE]/instances/[INSTANCE_NAME]` */
		instances: Option[List[String]] = None,
	  /** Targets VMs whose name starts with one of these prefixes. Similar to labels, this is another way to group VMs when targeting configs, for example prefix="prod-". */
		instanceNamePrefixes: Option[List[String]] = None
	)
	
	case class PatchInstanceFilterGroupLabel(
	  /** Compute Engine instance labels that must be present for a VM instance to be targeted by this filter. */
		labels: Option[Map[String, String]] = None
	)
	
	object PatchConfig {
		enum RebootConfigEnum extends Enum[RebootConfigEnum] { case REBOOT_CONFIG_UNSPECIFIED, DEFAULT, ALWAYS, NEVER }
	}
	case class PatchConfig(
	  /** Post-patch reboot settings. */
		rebootConfig: Option[Schema.PatchConfig.RebootConfigEnum] = None,
	  /** Apt update settings. Use this setting to override the default `apt` patch rules. */
		apt: Option[Schema.AptSettings] = None,
	  /** Yum update settings. Use this setting to override the default `yum` patch rules. */
		yum: Option[Schema.YumSettings] = None,
	  /** Goo update settings. Use this setting to override the default `goo` patch rules. */
		goo: Option[Schema.GooSettings] = None,
	  /** Zypper update settings. Use this setting to override the default `zypper` patch rules. */
		zypper: Option[Schema.ZypperSettings] = None,
	  /** Windows update settings. Use this override the default windows patch rules. */
		windowsUpdate: Option[Schema.WindowsUpdateSettings] = None,
	  /** The `ExecStep` to run before the patch update. */
		preStep: Option[Schema.ExecStep] = None,
	  /** The `ExecStep` to run after the patch update. */
		postStep: Option[Schema.ExecStep] = None,
	  /** Allows the patch job to run on Managed instance groups (MIGs). */
		migInstancesAllowed: Option[Boolean] = None
	)
	
	object AptSettings {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, DIST, UPGRADE }
	}
	case class AptSettings(
	  /** By changing the type to DIST, the patching is performed using `apt-get dist-upgrade` instead. */
		`type`: Option[Schema.AptSettings.TypeEnum] = None,
	  /** List of packages to exclude from update. These packages will be excluded */
		excludes: Option[List[String]] = None,
	  /** An exclusive list of packages to be updated. These are the only packages that will be updated. If these packages are not installed, they will be ignored. This field cannot be specified with any other patch configuration fields. */
		exclusivePackages: Option[List[String]] = None
	)
	
	case class YumSettings(
	  /** Adds the `--security` flag to `yum update`. Not supported on all platforms. */
		security: Option[Boolean] = None,
	  /** Will cause patch to run `yum update-minimal` instead. */
		minimal: Option[Boolean] = None,
	  /** List of packages to exclude from update. These packages are excluded by using the yum `--exclude` flag. */
		excludes: Option[List[String]] = None,
	  /** An exclusive list of packages to be updated. These are the only packages that will be updated. If these packages are not installed, they will be ignored. This field must not be specified with any other patch configuration fields. */
		exclusivePackages: Option[List[String]] = None
	)
	
	case class GooSettings(
	
	)
	
	case class ZypperSettings(
	  /** Adds the `--with-optional` flag to `zypper patch`. */
		withOptional: Option[Boolean] = None,
	  /** Adds the `--with-update` flag, to `zypper patch`. */
		withUpdate: Option[Boolean] = None,
	  /** Install only patches with these categories. Common categories include security, recommended, and feature. */
		categories: Option[List[String]] = None,
	  /** Install only patches with these severities. Common severities include critical, important, moderate, and low. */
		severities: Option[List[String]] = None,
	  /** List of patches to exclude from update. */
		excludes: Option[List[String]] = None,
	  /** An exclusive list of patches to be updated. These are the only patches that will be installed using 'zypper patch patch:' command. This field must not be used with any other patch configuration fields. */
		exclusivePatches: Option[List[String]] = None
	)
	
	object WindowsUpdateSettings {
		enum ClassificationsEnum extends Enum[ClassificationsEnum] { case CLASSIFICATION_UNSPECIFIED, CRITICAL, SECURITY, DEFINITION, DRIVER, FEATURE_PACK, SERVICE_PACK, TOOL, UPDATE_ROLLUP, UPDATE }
	}
	case class WindowsUpdateSettings(
	  /** Only apply updates of these windows update classifications. If empty, all updates are applied. */
		classifications: Option[List[Schema.WindowsUpdateSettings.ClassificationsEnum]] = None,
	  /** List of KBs to exclude from update. */
		excludes: Option[List[String]] = None,
	  /** An exclusive list of kbs to be updated. These are the only patches that will be updated. This field must not be used with other patch configurations. */
		exclusivePatches: Option[List[String]] = None
	)
	
	case class ExecStep(
	  /** The ExecStepConfig for all Linux VMs targeted by the PatchJob. */
		linuxExecStepConfig: Option[Schema.ExecStepConfig] = None,
	  /** The ExecStepConfig for all Windows VMs targeted by the PatchJob. */
		windowsExecStepConfig: Option[Schema.ExecStepConfig] = None
	)
	
	object ExecStepConfig {
		enum InterpreterEnum extends Enum[InterpreterEnum] { case INTERPRETER_UNSPECIFIED, NONE, SHELL, POWERSHELL }
	}
	case class ExecStepConfig(
	  /** An absolute path to the executable on the VM. */
		localPath: Option[String] = None,
	  /** A Cloud Storage object containing the executable. */
		gcsObject: Option[Schema.GcsObject] = None,
	  /** Defaults to [0]. A list of possible return values that the execution can return to indicate a success. */
		allowedSuccessCodes: Option[List[Int]] = None,
	  /** The script interpreter to use to run the script. If no interpreter is specified the script will be executed directly, which will likely only succeed for scripts with [shebang lines] (https://en.wikipedia.org/wiki/Shebang_\(Unix\)). */
		interpreter: Option[Schema.ExecStepConfig.InterpreterEnum] = None
	)
	
	case class GcsObject(
	  /** Required. Bucket of the Cloud Storage object. */
		bucket: Option[String] = None,
	  /** Required. Name of the Cloud Storage object. */
		`object`: Option[String] = None,
	  /** Required. Generation number of the Cloud Storage object. This is used to ensure that the ExecStep specified by this PatchJob does not change. */
		generationNumber: Option[String] = None
	)
	
	object PatchRollout {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, ZONE_BY_ZONE, CONCURRENT_ZONES }
	}
	case class PatchRollout(
	  /** Mode of the patch rollout. */
		mode: Option[Schema.PatchRollout.ModeEnum] = None,
	  /** The maximum number (or percentage) of VMs per zone to disrupt at any given moment. The number of VMs calculated from multiplying the percentage by the total number of VMs in a zone is rounded up. During patching, a VM is considered disrupted from the time the agent is notified to begin until patching has completed. This disruption time includes the time to complete reboot and any post-patch steps. A VM contributes to the disruption budget if its patching operation fails either when applying the patches, running pre or post patch steps, or if it fails to respond with a success notification before timing out. VMs that are not running or do not have an active agent do not count toward this disruption budget. For zone-by-zone rollouts, if the disruption budget in a zone is exceeded, the patch job stops, because continuing to the next zone requires completion of the patch process in the previous zone. For example, if the disruption budget has a fixed value of `10`, and 8 VMs fail to patch in the current zone, the patch job continues to patch 2 VMs at a time until the zone is completed. When that zone is completed successfully, patching begins with 10 VMs at a time in the next zone. If 10 VMs in the next zone fail to patch, the patch job stops. */
		disruptionBudget: Option[Schema.FixedOrPercent] = None
	)
	
	case class FixedOrPercent(
	  /** Specifies a fixed value. */
		fixed: Option[Int] = None,
	  /** Specifies the relative value defined as a percentage, which will be multiplied by a reference value. */
		percent: Option[Int] = None
	)
	
	object PatchJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, INSTANCE_LOOKUP, PATCHING, SUCCEEDED, COMPLETED_WITH_ERRORS, CANCELED, TIMED_OUT }
	}
	case class PatchJob(
	  /** Unique identifier for this patch job in the form `projects/&#42;/patchJobs/&#42;` */
		name: Option[String] = None,
	  /** Display name for this patch job. This is not a unique identifier. */
		displayName: Option[String] = None,
	  /** Description of the patch job. Length of the description is limited to 1024 characters. */
		description: Option[String] = None,
	  /** Time this patch job was created. */
		createTime: Option[String] = None,
	  /** Last time this patch job was updated. */
		updateTime: Option[String] = None,
	  /** The current state of the PatchJob. */
		state: Option[Schema.PatchJob.StateEnum] = None,
	  /** Instances to patch. */
		instanceFilter: Option[Schema.PatchInstanceFilter] = None,
	  /** Patch configuration being applied. */
		patchConfig: Option[Schema.PatchConfig] = None,
	  /** Duration of the patch job. After the duration ends, the patch job times out. */
		duration: Option[String] = None,
	  /** Summary of instance details. */
		instanceDetailsSummary: Option[Schema.PatchJobInstanceDetailsSummary] = None,
	  /** If this patch job is a dry run, the agent reports that it has finished without running any updates on the VM instance. */
		dryRun: Option[Boolean] = None,
	  /** If this patch job failed, this message provides information about the failure. */
		errorMessage: Option[String] = None,
	  /** Reflects the overall progress of the patch job in the range of 0.0 being no progress to 100.0 being complete. */
		percentComplete: Option[BigDecimal] = None,
	  /** Output only. Name of the patch deployment that created this patch job. */
		patchDeployment: Option[String] = None,
	  /** Rollout strategy being applied. */
		rollout: Option[Schema.PatchRollout] = None
	)
	
	case class PatchJobInstanceDetailsSummary(
	  /** Number of instances pending patch job. */
		pendingInstanceCount: Option[String] = None,
	  /** Number of instances that are inactive. */
		inactiveInstanceCount: Option[String] = None,
	  /** Number of instances notified about patch job. */
		notifiedInstanceCount: Option[String] = None,
	  /** Number of instances that have started. */
		startedInstanceCount: Option[String] = None,
	  /** Number of instances that are downloading patches. */
		downloadingPatchesInstanceCount: Option[String] = None,
	  /** Number of instances that are applying patches. */
		applyingPatchesInstanceCount: Option[String] = None,
	  /** Number of instances rebooting. */
		rebootingInstanceCount: Option[String] = None,
	  /** Number of instances that have completed successfully. */
		succeededInstanceCount: Option[String] = None,
	  /** Number of instances that require reboot. */
		succeededRebootRequiredInstanceCount: Option[String] = None,
	  /** Number of instances that failed. */
		failedInstanceCount: Option[String] = None,
	  /** Number of instances that have acked and will start shortly. */
		ackedInstanceCount: Option[String] = None,
	  /** Number of instances that exceeded the time out while applying the patch. */
		timedOutInstanceCount: Option[String] = None,
	  /** Number of instances that are running the pre-patch step. */
		prePatchStepInstanceCount: Option[String] = None,
	  /** Number of instances that are running the post-patch step. */
		postPatchStepInstanceCount: Option[String] = None,
	  /** Number of instances that do not appear to be running the agent. Check to ensure that the agent is installed, running, and able to communicate with the service. */
		noAgentDetectedInstanceCount: Option[String] = None
	)
	
	case class CancelPatchJobRequest(
	
	)
	
	case class ListPatchJobsResponse(
	  /** The list of patch jobs. */
		patchJobs: Option[List[Schema.PatchJob]] = None,
	  /** A pagination token that can be used to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListPatchJobInstanceDetailsResponse(
	  /** A list of instance status. */
		patchJobInstanceDetails: Option[List[Schema.PatchJobInstanceDetails]] = None,
	  /** A pagination token that can be used to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object PatchJobInstanceDetails {
		enum StateEnum extends Enum[StateEnum] { case PATCH_STATE_UNSPECIFIED, PENDING, INACTIVE, NOTIFIED, STARTED, DOWNLOADING_PATCHES, APPLYING_PATCHES, REBOOTING, SUCCEEDED, SUCCEEDED_REBOOT_REQUIRED, FAILED, ACKED, TIMED_OUT, RUNNING_PRE_PATCH_STEP, RUNNING_POST_PATCH_STEP, NO_AGENT_DETECTED }
	}
	case class PatchJobInstanceDetails(
	  /** The instance name in the form `projects/&#42;/zones/&#42;/instances/&#42;` */
		name: Option[String] = None,
	  /** The unique identifier for the instance. This identifier is defined by the server. */
		instanceSystemId: Option[String] = None,
	  /** Current state of instance patch. */
		state: Option[Schema.PatchJobInstanceDetails.StateEnum] = None,
	  /** If the patch fails, this field provides the reason. */
		failureReason: Option[String] = None,
	  /** The number of times the agent that the agent attempts to apply the patch. */
		attemptCount: Option[String] = None
	)
	
	object PatchDeployment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, PAUSED }
	}
	case class PatchDeployment(
	  /** Unique name for the patch deployment resource in a project. The patch deployment name is in the form: `projects/{project_id}/patchDeployments/{patch_deployment_id}`. This field is ignored when you create a new patch deployment. */
		name: Option[String] = None,
	  /** Optional. Description of the patch deployment. Length of the description is limited to 1024 characters. */
		description: Option[String] = None,
	  /** Required. VM instances to patch. */
		instanceFilter: Option[Schema.PatchInstanceFilter] = None,
	  /** Optional. Patch configuration that is applied. */
		patchConfig: Option[Schema.PatchConfig] = None,
	  /** Optional. Duration of the patch. After the duration ends, the patch times out. */
		duration: Option[String] = None,
	  /** Required. Schedule a one-time execution. */
		oneTimeSchedule: Option[Schema.OneTimeSchedule] = None,
	  /** Required. Schedule recurring executions. */
		recurringSchedule: Option[Schema.RecurringSchedule] = None,
	  /** Output only. Time the patch deployment was created. Timestamp is in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		createTime: Option[String] = None,
	  /** Output only. Time the patch deployment was last updated. Timestamp is in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		updateTime: Option[String] = None,
	  /** Output only. The last time a patch job was started by this deployment. Timestamp is in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		lastExecuteTime: Option[String] = None,
	  /** Optional. Rollout strategy of the patch job. */
		rollout: Option[Schema.PatchRollout] = None,
	  /** Output only. Current state of the patch deployment. */
		state: Option[Schema.PatchDeployment.StateEnum] = None
	)
	
	case class OneTimeSchedule(
	  /** Required. The desired patch job execution time. */
		executeTime: Option[String] = None
	)
	
	object RecurringSchedule {
		enum FrequencyEnum extends Enum[FrequencyEnum] { case FREQUENCY_UNSPECIFIED, WEEKLY, MONTHLY, DAILY }
	}
	case class RecurringSchedule(
	  /** Required. Defines the time zone that `time_of_day` is relative to. The rules for daylight saving time are determined by the chosen time zone. */
		timeZone: Option[Schema.TimeZone] = None,
	  /** Optional. The time that the recurring schedule becomes effective. Defaults to `create_time` of the patch deployment. */
		startTime: Option[String] = None,
	  /** Optional. The end time at which a recurring patch deployment schedule is no longer active. */
		endTime: Option[String] = None,
	  /** Required. Time of the day to run a recurring deployment. */
		timeOfDay: Option[Schema.TimeOfDay] = None,
	  /** Required. The frequency unit of this recurring schedule. */
		frequency: Option[Schema.RecurringSchedule.FrequencyEnum] = None,
	  /** Required. Schedule with weekly executions. */
		weekly: Option[Schema.WeeklySchedule] = None,
	  /** Required. Schedule with monthly executions. */
		monthly: Option[Schema.MonthlySchedule] = None,
	  /** Output only. The time the last patch job ran successfully. */
		lastExecuteTime: Option[String] = None,
	  /** Output only. The time the next patch job is scheduled to run. */
		nextExecuteTime: Option[String] = None
	)
	
	case class TimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
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
	
	object WeeklySchedule {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeeklySchedule(
	  /** Required. Day of the week. */
		dayOfWeek: Option[Schema.WeeklySchedule.DayOfWeekEnum] = None
	)
	
	case class MonthlySchedule(
	  /** Required. Week day in a month. */
		weekDayOfMonth: Option[Schema.WeekDayOfMonth] = None,
	  /** Required. One day of the month. 1-31 indicates the 1st to the 31st day. -1 indicates the last day of the month. Months without the target day will be skipped. For example, a schedule to run "every month on the 31st" will not run in February, April, June, etc. */
		monthDay: Option[Int] = None
	)
	
	object WeekDayOfMonth {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeekDayOfMonth(
	  /** Required. Week number in a month. 1-4 indicates the 1st to 4th week of the month. -1 indicates the last week of the month. */
		weekOrdinal: Option[Int] = None,
	  /** Required. A day of the week. */
		dayOfWeek: Option[Schema.WeekDayOfMonth.DayOfWeekEnum] = None,
	  /** Optional. Represents the number of days before or after the given week day of month that the patch deployment is scheduled for. For example if `week_ordinal` and `day_of_week` values point to the second Tuesday of the month and the `day_offset` value is set to `3`, patch deployment takes place three days after the second Tuesday of the month. If this value is negative, for example -5, patches are deployed five days before the second Tuesday of the month. Allowed values are in range [-30, 30]. */
		dayOffset: Option[Int] = None
	)
	
	case class ListPatchDeploymentsResponse(
	  /** The list of patch deployments. */
		patchDeployments: Option[List[Schema.PatchDeployment]] = None,
	  /** A pagination token that can be used to get the next page of patch deployments. */
		nextPageToken: Option[String] = None
	)
	
	case class PausePatchDeploymentRequest(
	
	)
	
	case class ResumePatchDeploymentRequest(
	
	)
	
	object OSPolicyAssignment {
		enum RolloutStateEnum extends Enum[RolloutStateEnum] { case ROLLOUT_STATE_UNSPECIFIED, IN_PROGRESS, CANCELLING, CANCELLED, SUCCEEDED }
	}
	case class OSPolicyAssignment(
	  /** Resource name. Format: `projects/{project_number}/locations/{location}/osPolicyAssignments/{os_policy_assignment_id}` This field is ignored when you create an OS policy assignment. */
		name: Option[String] = None,
	  /** OS policy assignment description. Length of the description is limited to 1024 characters. */
		description: Option[String] = None,
	  /** Required. List of OS policies to be applied to the VMs. */
		osPolicies: Option[List[Schema.OSPolicy]] = None,
	  /** Required. Filter to select VMs. */
		instanceFilter: Option[Schema.OSPolicyAssignmentInstanceFilter] = None,
	  /** Required. Rollout to deploy the OS policy assignment. A rollout is triggered in the following situations: 1) OSPolicyAssignment is created. 2) OSPolicyAssignment is updated and the update contains changes to one of the following fields: - instance_filter - os_policies 3) OSPolicyAssignment is deleted. */
		rollout: Option[Schema.OSPolicyAssignmentRollout] = None,
	  /** Output only. The assignment revision ID A new revision is committed whenever a rollout is triggered for a OS policy assignment */
		revisionId: Option[String] = None,
	  /** Output only. The timestamp that the revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** The etag for this OS policy assignment. If this is provided on update, it must match the server's etag. */
		etag: Option[String] = None,
	  /** Output only. OS policy assignment rollout state */
		rolloutState: Option[Schema.OSPolicyAssignment.RolloutStateEnum] = None,
	  /** Output only. Indicates that this revision has been successfully rolled out in this zone and new VMs will be assigned OS policies from this revision. For a given OS policy assignment, there is only one revision with a value of `true` for this field. */
		baseline: Option[Boolean] = None,
	  /** Output only. Indicates that this revision deletes the OS policy assignment. */
		deleted: Option[Boolean] = None,
	  /** Output only. Indicates that reconciliation is in progress for the revision. This value is `true` when the `rollout_state` is one of: &#42; IN_PROGRESS &#42; CANCELLING */
		reconciling: Option[Boolean] = None,
	  /** Output only. Server generated unique id for the OS policy assignment resource. */
		uid: Option[String] = None
	)
	
	object OSPolicy {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, VALIDATION, ENFORCEMENT }
	}
	case class OSPolicy(
	  /** Required. The id of the OS policy with the following restrictions: &#42; Must contain only lowercase letters, numbers, and hyphens. &#42; Must start with a letter. &#42; Must be between 1-63 characters. &#42; Must end with a number or a letter. &#42; Must be unique within the assignment. */
		id: Option[String] = None,
	  /** Policy description. Length of the description is limited to 1024 characters. */
		description: Option[String] = None,
	  /** Required. Policy mode */
		mode: Option[Schema.OSPolicy.ModeEnum] = None,
	  /** Required. List of resource groups for the policy. For a particular VM, resource groups are evaluated in the order specified and the first resource group that is applicable is selected and the rest are ignored. If none of the resource groups are applicable for a VM, the VM is considered to be non-compliant w.r.t this policy. This behavior can be toggled by the flag `allow_no_resource_group_match` */
		resourceGroups: Option[List[Schema.OSPolicyResourceGroup]] = None,
	  /** This flag determines the OS policy compliance status when none of the resource groups within the policy are applicable for a VM. Set this value to `true` if the policy needs to be reported as compliant even if the policy has nothing to validate or enforce. */
		allowNoResourceGroupMatch: Option[Boolean] = None
	)
	
	case class OSPolicyResourceGroup(
	  /** List of inventory filters for the resource group. The resources in this resource group are applied to the target VM if it satisfies at least one of the following inventory filters. For example, to apply this resource group to VMs running either `RHEL` or `CentOS` operating systems, specify 2 items for the list with following values: inventory_filters[0].os_short_name='rhel' and inventory_filters[1].os_short_name='centos' If the list is empty, this resource group will be applied to the target VM unconditionally. */
		inventoryFilters: Option[List[Schema.OSPolicyInventoryFilter]] = None,
	  /** Required. List of resources configured for this resource group. The resources are executed in the exact order specified here. */
		resources: Option[List[Schema.OSPolicyResource]] = None
	)
	
	case class OSPolicyInventoryFilter(
	  /** Required. The OS short name */
		osShortName: Option[String] = None,
	  /** The OS version Prefix matches are supported if asterisk(&#42;) is provided as the last character. For example, to match all versions with a major version of `7`, specify the following value for this field `7.&#42;` An empty string matches all OS versions. */
		osVersion: Option[String] = None
	)
	
	case class OSPolicyResource(
	  /** Required. The id of the resource with the following restrictions: &#42; Must contain only lowercase letters, numbers, and hyphens. &#42; Must start with a letter. &#42; Must be between 1-63 characters. &#42; Must end with a number or a letter. &#42; Must be unique within the OS policy. */
		id: Option[String] = None,
	  /** Package resource */
		pkg: Option[Schema.OSPolicyResourcePackageResource] = None,
	  /** Package repository resource */
		repository: Option[Schema.OSPolicyResourceRepositoryResource] = None,
	  /** Exec resource */
		exec: Option[Schema.OSPolicyResourceExecResource] = None,
	  /** File resource */
		file: Option[Schema.OSPolicyResourceFileResource] = None
	)
	
	object OSPolicyResourcePackageResource {
		enum DesiredStateEnum extends Enum[DesiredStateEnum] { case DESIRED_STATE_UNSPECIFIED, INSTALLED, REMOVED }
	}
	case class OSPolicyResourcePackageResource(
	  /** Required. The desired state the agent should maintain for this package. */
		desiredState: Option[Schema.OSPolicyResourcePackageResource.DesiredStateEnum] = None,
	  /** A package managed by Apt. */
		apt: Option[Schema.OSPolicyResourcePackageResourceAPT] = None,
	  /** A deb package file. */
		deb: Option[Schema.OSPolicyResourcePackageResourceDeb] = None,
	  /** A package managed by YUM. */
		yum: Option[Schema.OSPolicyResourcePackageResourceYUM] = None,
	  /** A package managed by Zypper. */
		zypper: Option[Schema.OSPolicyResourcePackageResourceZypper] = None,
	  /** An rpm package file. */
		rpm: Option[Schema.OSPolicyResourcePackageResourceRPM] = None,
	  /** A package managed by GooGet. */
		googet: Option[Schema.OSPolicyResourcePackageResourceGooGet] = None,
	  /** An MSI package. */
		msi: Option[Schema.OSPolicyResourcePackageResourceMSI] = None
	)
	
	case class OSPolicyResourcePackageResourceAPT(
	  /** Required. Package name. */
		name: Option[String] = None
	)
	
	case class OSPolicyResourcePackageResourceDeb(
	  /** Required. A deb package. */
		source: Option[Schema.OSPolicyResourceFile] = None,
	  /** Whether dependencies should also be installed. - install when false: `dpkg -i package` - install when true: `apt-get update && apt-get -y install package.deb` */
		pullDeps: Option[Boolean] = None
	)
	
	case class OSPolicyResourceFile(
	  /** A generic remote file. */
		remote: Option[Schema.OSPolicyResourceFileRemote] = None,
	  /** A Cloud Storage object. */
		gcs: Option[Schema.OSPolicyResourceFileGcs] = None,
	  /** A local path within the VM to use. */
		localPath: Option[String] = None,
	  /** Defaults to false. When false, files are subject to validations based on the file type: Remote: A checksum must be specified. Cloud Storage: An object generation number must be specified. */
		allowInsecure: Option[Boolean] = None
	)
	
	case class OSPolicyResourceFileRemote(
	  /** Required. URI from which to fetch the object. It should contain both the protocol and path following the format `{protocol}://{location}`. */
		uri: Option[String] = None,
	  /** SHA256 checksum of the remote file. */
		sha256Checksum: Option[String] = None
	)
	
	case class OSPolicyResourceFileGcs(
	  /** Required. Bucket of the Cloud Storage object. */
		bucket: Option[String] = None,
	  /** Required. Name of the Cloud Storage object. */
		`object`: Option[String] = None,
	  /** Generation number of the Cloud Storage object. */
		generation: Option[String] = None
	)
	
	case class OSPolicyResourcePackageResourceYUM(
	  /** Required. Package name. */
		name: Option[String] = None
	)
	
	case class OSPolicyResourcePackageResourceZypper(
	  /** Required. Package name. */
		name: Option[String] = None
	)
	
	case class OSPolicyResourcePackageResourceRPM(
	  /** Required. An rpm package. */
		source: Option[Schema.OSPolicyResourceFile] = None,
	  /** Whether dependencies should also be installed. - install when false: `rpm --upgrade --replacepkgs package.rpm` - install when true: `yum -y install package.rpm` or `zypper -y install package.rpm` */
		pullDeps: Option[Boolean] = None
	)
	
	case class OSPolicyResourcePackageResourceGooGet(
	  /** Required. Package name. */
		name: Option[String] = None
	)
	
	case class OSPolicyResourcePackageResourceMSI(
	  /** Required. The MSI package. */
		source: Option[Schema.OSPolicyResourceFile] = None,
	  /** Additional properties to use during installation. This should be in the format of Property=Setting. Appended to the defaults of `ACTION=INSTALL REBOOT=ReallySuppress`. */
		properties: Option[List[String]] = None
	)
	
	case class OSPolicyResourceRepositoryResource(
	  /** An Apt Repository. */
		apt: Option[Schema.OSPolicyResourceRepositoryResourceAptRepository] = None,
	  /** A Yum Repository. */
		yum: Option[Schema.OSPolicyResourceRepositoryResourceYumRepository] = None,
	  /** A Zypper Repository. */
		zypper: Option[Schema.OSPolicyResourceRepositoryResourceZypperRepository] = None,
	  /** A Goo Repository. */
		goo: Option[Schema.OSPolicyResourceRepositoryResourceGooRepository] = None
	)
	
	object OSPolicyResourceRepositoryResourceAptRepository {
		enum ArchiveTypeEnum extends Enum[ArchiveTypeEnum] { case ARCHIVE_TYPE_UNSPECIFIED, DEB, DEB_SRC }
	}
	case class OSPolicyResourceRepositoryResourceAptRepository(
	  /** Required. Type of archive files in this repository. */
		archiveType: Option[Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum] = None,
	  /** Required. URI for this repository. */
		uri: Option[String] = None,
	  /** Required. Distribution of this repository. */
		distribution: Option[String] = None,
	  /** Required. List of components for this repository. Must contain at least one item. */
		components: Option[List[String]] = None,
	  /** URI of the key file for this repository. The agent maintains a keyring at `/etc/apt/trusted.gpg.d/osconfig_agent_managed.gpg`. */
		gpgKey: Option[String] = None
	)
	
	case class OSPolicyResourceRepositoryResourceYumRepository(
	  /** Required. A one word, unique name for this repository. This is the `repo id` in the yum config file and also the `display_name` if `display_name` is omitted. This id is also used as the unique identifier when checking for resource conflicts. */
		id: Option[String] = None,
	  /** The display name of the repository. */
		displayName: Option[String] = None,
	  /** Required. The location of the repository directory. */
		baseUrl: Option[String] = None,
	  /** URIs of GPG keys. */
		gpgKeys: Option[List[String]] = None
	)
	
	case class OSPolicyResourceRepositoryResourceZypperRepository(
	  /** Required. A one word, unique name for this repository. This is the `repo id` in the zypper config file and also the `display_name` if `display_name` is omitted. This id is also used as the unique identifier when checking for GuestPolicy conflicts. */
		id: Option[String] = None,
	  /** The display name of the repository. */
		displayName: Option[String] = None,
	  /** Required. The location of the repository directory. */
		baseUrl: Option[String] = None,
	  /** URIs of GPG keys. */
		gpgKeys: Option[List[String]] = None
	)
	
	case class OSPolicyResourceRepositoryResourceGooRepository(
	  /** Required. The name of the repository. */
		name: Option[String] = None,
	  /** Required. The url of the repository. */
		url: Option[String] = None
	)
	
	case class OSPolicyResourceExecResource(
	  /** Required. What to run to validate this resource is in the desired state. An exit code of 100 indicates "in desired state", and exit code of 101 indicates "not in desired state". Any other exit code indicates a failure running validate. */
		validate: Option[Schema.OSPolicyResourceExecResourceExec] = None,
	  /** What to run to bring this resource into the desired state. An exit code of 100 indicates "success", any other exit code indicates a failure running enforce. */
		enforce: Option[Schema.OSPolicyResourceExecResourceExec] = None
	)
	
	object OSPolicyResourceExecResourceExec {
		enum InterpreterEnum extends Enum[InterpreterEnum] { case INTERPRETER_UNSPECIFIED, NONE, SHELL, POWERSHELL }
	}
	case class OSPolicyResourceExecResourceExec(
	  /** A remote or local file. */
		file: Option[Schema.OSPolicyResourceFile] = None,
	  /** An inline script. The size of the script is limited to 32KiB. */
		script: Option[String] = None,
	  /** Optional arguments to pass to the source during execution. */
		args: Option[List[String]] = None,
	  /** Required. The script interpreter to use. */
		interpreter: Option[Schema.OSPolicyResourceExecResourceExec.InterpreterEnum] = None,
	  /** Only recorded for enforce Exec. Path to an output file (that is created by this Exec) whose content will be recorded in OSPolicyResourceCompliance after a successful run. Absence or failure to read this file will result in this ExecResource being non-compliant. Output file size is limited to 500K bytes. */
		outputFilePath: Option[String] = None
	)
	
	object OSPolicyResourceFileResource {
		enum StateEnum extends Enum[StateEnum] { case DESIRED_STATE_UNSPECIFIED, PRESENT, ABSENT, CONTENTS_MATCH }
	}
	case class OSPolicyResourceFileResource(
	  /** A remote or local source. */
		file: Option[Schema.OSPolicyResourceFile] = None,
	  /** A a file with this content. The size of the content is limited to 32KiB. */
		content: Option[String] = None,
	  /** Required. The absolute path of the file within the VM. */
		path: Option[String] = None,
	  /** Required. Desired state of the file. */
		state: Option[Schema.OSPolicyResourceFileResource.StateEnum] = None,
	  /** Consists of three octal digits which represent, in order, the permissions of the owner, group, and other users for the file (similarly to the numeric mode used in the linux chmod utility). Each digit represents a three bit number with the 4 bit corresponding to the read permissions, the 2 bit corresponds to the write bit, and the one bit corresponds to the execute permission. Default behavior is 755. Below are some examples of permissions and their associated values: read, write, and execute: 7 read and execute: 5 read and write: 6 read only: 4 */
		permissions: Option[String] = None
	)
	
	case class OSPolicyAssignmentInstanceFilter(
	  /** Target all VMs in the project. If true, no other criteria is permitted. */
		all: Option[Boolean] = None,
	  /** List of label sets used for VM inclusion. If the list has more than one `LabelSet`, the VM is included if any of the label sets are applicable for the VM. */
		inclusionLabels: Option[List[Schema.OSPolicyAssignmentLabelSet]] = None,
	  /** List of label sets used for VM exclusion. If the list has more than one label set, the VM is excluded if any of the label sets are applicable for the VM. */
		exclusionLabels: Option[List[Schema.OSPolicyAssignmentLabelSet]] = None,
	  /** List of inventories to select VMs. A VM is selected if its inventory data matches at least one of the following inventories. */
		inventories: Option[List[Schema.OSPolicyAssignmentInstanceFilterInventory]] = None
	)
	
	case class OSPolicyAssignmentLabelSet(
	  /** Labels are identified by key/value pairs in this map. A VM should contain all the key/value pairs specified in this map to be selected. */
		labels: Option[Map[String, String]] = None
	)
	
	case class OSPolicyAssignmentInstanceFilterInventory(
	  /** Required. The OS short name */
		osShortName: Option[String] = None,
	  /** The OS version Prefix matches are supported if asterisk(&#42;) is provided as the last character. For example, to match all versions with a major version of `7`, specify the following value for this field `7.&#42;` An empty string matches all OS versions. */
		osVersion: Option[String] = None
	)
	
	case class OSPolicyAssignmentRollout(
	  /** Required. The maximum number (or percentage) of VMs per zone to disrupt at any given moment. */
		disruptionBudget: Option[Schema.FixedOrPercent] = None,
	  /** Required. This determines the minimum duration of time to wait after the configuration changes are applied through the current rollout. A VM continues to count towards the `disruption_budget` at least until this duration of time has passed after configuration changes are applied. */
		minWaitDuration: Option[String] = None
	)
	
	case class ListOSPolicyAssignmentsResponse(
	  /** The list of assignments */
		osPolicyAssignments: Option[List[Schema.OSPolicyAssignment]] = None,
	  /** The pagination token to retrieve the next page of OS policy assignments. */
		nextPageToken: Option[String] = None
	)
	
	case class ListOSPolicyAssignmentRevisionsResponse(
	  /** The OS policy assignment revisions */
		osPolicyAssignments: Option[List[Schema.OSPolicyAssignment]] = None,
	  /** The pagination token to retrieve the next page of OS policy assignment revisions. */
		nextPageToken: Option[String] = None
	)
	
	case class OSPolicyAssignmentReport(
	  /** The `OSPolicyAssignmentReport` API resource name. Format: `projects/{project_number}/locations/{location}/instances/{instance_id}/osPolicyAssignments/{os_policy_assignment_id}/report` */
		name: Option[String] = None,
	  /** The Compute Engine VM instance name. */
		instance: Option[String] = None,
	  /** Reference to the `OSPolicyAssignment` API resource that the `OSPolicy` belongs to. Format: `projects/{project_number}/locations/{location}/osPolicyAssignments/{os_policy_assignment_id@revision_id}` */
		osPolicyAssignment: Option[String] = None,
	  /** Compliance data for each `OSPolicy` that is applied to the VM. */
		osPolicyCompliances: Option[List[Schema.OSPolicyAssignmentReportOSPolicyCompliance]] = None,
	  /** Timestamp for when the report was last generated. */
		updateTime: Option[String] = None,
	  /** Unique identifier of the last attempted run to apply the OS policies associated with this assignment on the VM. This ID is logged by the OS Config agent while applying the OS policies associated with this assignment on the VM. NOTE: If the service is unable to successfully connect to the agent for this run, then this id will not be available in the agent logs. */
		lastRunId: Option[String] = None
	)
	
	object OSPolicyAssignmentReportOSPolicyCompliance {
		enum ComplianceStateEnum extends Enum[ComplianceStateEnum] { case UNKNOWN, COMPLIANT, NON_COMPLIANT }
	}
	case class OSPolicyAssignmentReportOSPolicyCompliance(
	  /** The OS policy id */
		osPolicyId: Option[String] = None,
	  /** The compliance state of the OS policy. */
		complianceState: Option[Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum] = None,
	  /** The reason for the OS policy to be in an unknown compliance state. This field is always populated when `compliance_state` is `UNKNOWN`. If populated, the field can contain one of the following values: &#42; `vm-not-running`: The VM was not running. &#42; `os-policies-not-supported-by-agent`: The version of the OS Config agent running on the VM does not support running OS policies. &#42; `no-agent-detected`: The OS Config agent is not detected for the VM. &#42; `resource-execution-errors`: The OS Config agent encountered errors while executing one or more resources in the policy. See `os_policy_resource_compliances` for details. &#42; `task-timeout`: The task sent to the agent to apply the policy timed out. &#42; `unexpected-agent-state`: The OS Config agent did not report the final status of the task that attempted to apply the policy. Instead, the agent unexpectedly started working on a different task. This mostly happens when the agent or VM unexpectedly restarts while applying OS policies. &#42; `internal-service-errors`: Internal service errors were encountered while attempting to apply the policy. &#42; `os-policy-execution-pending`: OS policy was assigned to the given VM, but was not executed yet. Typically this is a transient condition that will go away after the next policy execution cycle. */
		complianceStateReason: Option[String] = None,
	  /** Compliance data for each resource within the policy that is applied to the VM. */
		osPolicyResourceCompliances: Option[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance]] = None
	)
	
	object OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance {
		enum ComplianceStateEnum extends Enum[ComplianceStateEnum] { case UNKNOWN, COMPLIANT, NON_COMPLIANT }
	}
	case class OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance(
	  /** The ID of the OS policy resource. */
		osPolicyResourceId: Option[String] = None,
	  /** Ordered list of configuration completed by the agent for the OS policy resource. */
		configSteps: Option[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep]] = None,
	  /** The compliance state of the resource. */
		complianceState: Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum] = None,
	  /** A reason for the resource to be in the given compliance state. This field is always populated when `compliance_state` is `UNKNOWN`. The following values are supported when `compliance_state == UNKNOWN` &#42; `execution-errors`: Errors were encountered by the agent while executing the resource and the compliance state couldn't be determined. &#42; `execution-skipped-by-agent`: Resource execution was skipped by the agent because errors were encountered while executing prior resources in the OS policy. &#42; `os-policy-execution-attempt-failed`: The execution of the OS policy containing this resource failed and the compliance state couldn't be determined. &#42; `os-policy-execution-pending`: OS policy that owns this resource was assigned to the given VM, but was not executed yet. */
		complianceStateReason: Option[String] = None,
	  /** ExecResource specific output. */
		execResourceOutput: Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput] = None
	)
	
	object OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, VALIDATION, DESIRED_STATE_CHECK, DESIRED_STATE_ENFORCEMENT, DESIRED_STATE_CHECK_POST_ENFORCEMENT }
	}
	case class OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep(
	  /** Configuration step type. */
		`type`: Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum] = None,
	  /** An error message recorded during the execution of this step. Only populated if errors were encountered during this step execution. */
		errorMessage: Option[String] = None
	)
	
	case class OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput(
	  /** Output from enforcement phase output file (if run). Output size is limited to 100K bytes. */
		enforcementOutput: Option[String] = None
	)
	
	case class ListOSPolicyAssignmentReportsResponse(
	  /** List of OS policy assignment reports. */
		osPolicyAssignmentReports: Option[List[Schema.OSPolicyAssignmentReport]] = None,
	  /** The pagination token to retrieve the next page of OS policy assignment report objects. */
		nextPageToken: Option[String] = None
	)
	
	case class Inventory(
	  /** Output only. The `Inventory` API resource name. Format: `projects/{project_number}/locations/{location}/instances/{instance_id}/inventory` */
		name: Option[String] = None,
	  /** Base level operating system information for the VM. */
		osInfo: Option[Schema.InventoryOsInfo] = None,
	  /** Inventory items related to the VM keyed by an opaque unique identifier for each inventory item. The identifier is unique to each distinct and addressable inventory item and will change, when there is a new package version. */
		items: Option[Map[String, Schema.InventoryItem]] = None,
	  /** Output only. Timestamp of the last reported inventory for the VM. */
		updateTime: Option[String] = None
	)
	
	case class InventoryOsInfo(
	  /** The VM hostname. */
		hostname: Option[String] = None,
	  /** The operating system long name. For example 'Debian GNU/Linux 9' or 'Microsoft Window Server 2019 Datacenter'. */
		longName: Option[String] = None,
	  /** The operating system short name. For example, 'windows' or 'debian'. */
		shortName: Option[String] = None,
	  /** The version of the operating system. */
		version: Option[String] = None,
	  /** The system architecture of the operating system. */
		architecture: Option[String] = None,
	  /** The kernel version of the operating system. */
		kernelVersion: Option[String] = None,
	  /** The kernel release of the operating system. */
		kernelRelease: Option[String] = None,
	  /** The current version of the OS Config agent running on the VM. */
		osconfigAgentVersion: Option[String] = None
	)
	
	object InventoryItem {
		enum OriginTypeEnum extends Enum[OriginTypeEnum] { case ORIGIN_TYPE_UNSPECIFIED, INVENTORY_REPORT }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INSTALLED_PACKAGE, AVAILABLE_PACKAGE }
	}
	case class InventoryItem(
	  /** Identifier for this item, unique across items for this VM. */
		id: Option[String] = None,
	  /** The origin of this inventory item. */
		originType: Option[Schema.InventoryItem.OriginTypeEnum] = None,
	  /** When this inventory item was first detected. */
		createTime: Option[String] = None,
	  /** When this inventory item was last modified. */
		updateTime: Option[String] = None,
	  /** The specific type of inventory, correlating to its specific details. */
		`type`: Option[Schema.InventoryItem.TypeEnum] = None,
	  /** Software package present on the VM instance. */
		installedPackage: Option[Schema.InventorySoftwarePackage] = None,
	  /** Software package available to be installed on the VM instance. */
		availablePackage: Option[Schema.InventorySoftwarePackage] = None
	)
	
	case class InventorySoftwarePackage(
	  /** Yum package info. For details about the yum package manager, see https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/6/html/deployment_guide/ch-yum. */
		yumPackage: Option[Schema.InventoryVersionedPackage] = None,
	  /** Details of an APT package. For details about the apt package manager, see https://wiki.debian.org/Apt. */
		aptPackage: Option[Schema.InventoryVersionedPackage] = None,
	  /** Details of a Zypper package. For details about the Zypper package manager, see https://en.opensuse.org/SDB:Zypper_manual. */
		zypperPackage: Option[Schema.InventoryVersionedPackage] = None,
	  /** Details of a Googet package. For details about the googet package manager, see https://github.com/google/googet. */
		googetPackage: Option[Schema.InventoryVersionedPackage] = None,
	  /** Details of a Zypper patch. For details about the Zypper package manager, see https://en.opensuse.org/SDB:Zypper_manual. */
		zypperPatch: Option[Schema.InventoryZypperPatch] = None,
	  /** Details of a Windows Update package. See https://docs.microsoft.com/en-us/windows/win32/api/_wua/ for information about Windows Update. */
		wuaPackage: Option[Schema.InventoryWindowsUpdatePackage] = None,
	  /** Details of a Windows Quick Fix engineering package. See https://docs.microsoft.com/en-us/windows/win32/cimwin32prov/win32-quickfixengineering for info in Windows Quick Fix Engineering. */
		qfePackage: Option[Schema.InventoryWindowsQuickFixEngineeringPackage] = None,
	  /** Details of a COS package. */
		cosPackage: Option[Schema.InventoryVersionedPackage] = None,
	  /** Details of Windows Application. */
		windowsApplication: Option[Schema.InventoryWindowsApplication] = None
	)
	
	case class InventoryVersionedPackage(
	  /** The name of the package. */
		packageName: Option[String] = None,
	  /** The system architecture this package is intended for. */
		architecture: Option[String] = None,
	  /** The version of the package. */
		version: Option[String] = None
	)
	
	case class InventoryZypperPatch(
	  /** The name of the patch. */
		patchName: Option[String] = None,
	  /** The category of the patch. */
		category: Option[String] = None,
	  /** The severity specified for this patch */
		severity: Option[String] = None,
	  /** Any summary information provided about this patch. */
		summary: Option[String] = None
	)
	
	case class InventoryWindowsUpdatePackage(
	  /** The localized title of the update package. */
		title: Option[String] = None,
	  /** The localized description of the update package. */
		description: Option[String] = None,
	  /** The categories that are associated with this update package. */
		categories: Option[List[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory]] = None,
	  /** A collection of Microsoft Knowledge Base article IDs that are associated with the update package. */
		kbArticleIds: Option[List[String]] = None,
	  /** A hyperlink to the language-specific support information for the update. */
		supportUrl: Option[String] = None,
	  /** A collection of URLs that provide more information about the update package. */
		moreInfoUrls: Option[List[String]] = None,
	  /** Gets the identifier of an update package. Stays the same across revisions. */
		updateId: Option[String] = None,
	  /** The revision number of this update package. */
		revisionNumber: Option[Int] = None,
	  /** The last published date of the update, in (UTC) date and time. */
		lastDeploymentChangeTime: Option[String] = None
	)
	
	case class InventoryWindowsUpdatePackageWindowsUpdateCategory(
	  /** The identifier of the windows update category. */
		id: Option[String] = None,
	  /** The name of the windows update category. */
		name: Option[String] = None
	)
	
	case class InventoryWindowsQuickFixEngineeringPackage(
	  /** A short textual description of the QFE update. */
		caption: Option[String] = None,
	  /** A textual description of the QFE update. */
		description: Option[String] = None,
	  /** Unique identifier associated with a particular QFE update. */
		hotFixId: Option[String] = None,
	  /** Date that the QFE update was installed. Mapped from installed_on field. */
		installTime: Option[String] = None
	)
	
	case class InventoryWindowsApplication(
	  /** The name of the application or product. */
		displayName: Option[String] = None,
	  /** The version of the product or application in string format. */
		displayVersion: Option[String] = None,
	  /** The name of the manufacturer for the product or application. */
		publisher: Option[String] = None,
	  /** The last time this product received service. The value of this property is replaced each time a patch is applied or removed from the product or the command-line option is used to repair the product. */
		installDate: Option[Schema.Date] = None,
	  /** The internet address for technical support. */
		helpLink: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class ListInventoriesResponse(
	  /** List of inventory objects. */
		inventories: Option[List[Schema.Inventory]] = None,
	  /** The pagination token to retrieve the next page of inventory objects. */
		nextPageToken: Option[String] = None
	)
	
	case class VulnerabilityReport(
	  /** Output only. The `vulnerabilityReport` API resource name. Format: `projects/{project_number}/locations/{location}/instances/{instance_id}/vulnerabilityReport` */
		name: Option[String] = None,
	  /** Output only. List of vulnerabilities affecting the VM. */
		vulnerabilities: Option[List[Schema.VulnerabilityReportVulnerability]] = None,
	  /** Output only. The timestamp for when the last vulnerability report was generated for the VM. */
		updateTime: Option[String] = None
	)
	
	case class VulnerabilityReportVulnerability(
	  /** Contains metadata as per the upstream feed of the operating system and NVD. */
		details: Option[Schema.VulnerabilityReportVulnerabilityDetails] = None,
	  /** Corresponds to the `INSTALLED_PACKAGE` inventory item on the VM. This field displays the inventory items affected by this vulnerability. If the vulnerability report was not updated after the VM inventory update, these values might not display in VM inventory. For some distros, this field may be empty. */
		installedInventoryItemIds: Option[List[String]] = None,
	  /** Corresponds to the `AVAILABLE_PACKAGE` inventory item on the VM. If the vulnerability report was not updated after the VM inventory update, these values might not display in VM inventory. If there is no available fix, the field is empty. The `inventory_item` value specifies the latest `SoftwarePackage` available to the VM that fixes the vulnerability. */
		availableInventoryItemIds: Option[List[String]] = None,
	  /** The timestamp for when the vulnerability was first detected. */
		createTime: Option[String] = None,
	  /** The timestamp for when the vulnerability was last modified. */
		updateTime: Option[String] = None,
	  /** List of items affected by the vulnerability. */
		items: Option[List[Schema.VulnerabilityReportVulnerabilityItem]] = None
	)
	
	case class VulnerabilityReportVulnerabilityDetails(
	  /** The CVE of the vulnerability. CVE cannot be empty and the combination of should be unique across vulnerabilities for a VM. */
		cve: Option[String] = None,
	  /** The CVSS V2 score of this vulnerability. CVSS V2 score is on a scale of 0 - 10 where 0 indicates low severity and 10 indicates high severity. */
		cvssV2Score: Option[BigDecimal] = None,
	  /** The full description of the CVSSv3 for this vulnerability from NVD. */
		cvssV3: Option[Schema.CVSSv3] = None,
	  /** Assigned severity/impact ranking from the distro. */
		severity: Option[String] = None,
	  /** The note or description describing the vulnerability from the distro. */
		description: Option[String] = None,
	  /** Corresponds to the references attached to the `VulnerabilityDetails`. */
		references: Option[List[Schema.VulnerabilityReportVulnerabilityDetailsReference]] = None
	)
	
	object CVSSv3 {
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
	}
	case class CVSSv3(
	  /** The base score is a function of the base metric scores. https://www.first.org/cvss/specification-document#Base-Metrics */
		baseScore: Option[BigDecimal] = None,
	  /** The Exploitability sub-score equation is derived from the Base Exploitability metrics. https://www.first.org/cvss/specification-document#2-1-Exploitability-Metrics */
		exploitabilityScore: Option[BigDecimal] = None,
	  /** The Impact sub-score equation is derived from the Base Impact metrics. */
		impactScore: Option[BigDecimal] = None,
	  /** This metric reflects the context by which vulnerability exploitation is possible. */
		attackVector: Option[Schema.CVSSv3.AttackVectorEnum] = None,
	  /** This metric describes the conditions beyond the attacker's control that must exist in order to exploit the vulnerability. */
		attackComplexity: Option[Schema.CVSSv3.AttackComplexityEnum] = None,
	  /** This metric describes the level of privileges an attacker must possess before successfully exploiting the vulnerability. */
		privilegesRequired: Option[Schema.CVSSv3.PrivilegesRequiredEnum] = None,
	  /** This metric captures the requirement for a human user, other than the attacker, to participate in the successful compromise of the vulnerable component. */
		userInteraction: Option[Schema.CVSSv3.UserInteractionEnum] = None,
	  /** The Scope metric captures whether a vulnerability in one vulnerable component impacts resources in components beyond its security scope. */
		scope: Option[Schema.CVSSv3.ScopeEnum] = None,
	  /** This metric measures the impact to the confidentiality of the information resources managed by a software component due to a successfully exploited vulnerability. */
		confidentialityImpact: Option[Schema.CVSSv3.ConfidentialityImpactEnum] = None,
	  /** This metric measures the impact to integrity of a successfully exploited vulnerability. */
		integrityImpact: Option[Schema.CVSSv3.IntegrityImpactEnum] = None,
	  /** This metric measures the impact to the availability of the impacted component resulting from a successfully exploited vulnerability. */
		availabilityImpact: Option[Schema.CVSSv3.AvailabilityImpactEnum] = None
	)
	
	case class VulnerabilityReportVulnerabilityDetailsReference(
	  /** The url of the reference. */
		url: Option[String] = None,
	  /** The source of the reference e.g. NVD. */
		source: Option[String] = None
	)
	
	case class VulnerabilityReportVulnerabilityItem(
	  /** Corresponds to the `INSTALLED_PACKAGE` inventory item on the VM. This field displays the inventory items affected by this vulnerability. If the vulnerability report was not updated after the VM inventory update, these values might not display in VM inventory. For some operating systems, this field might be empty. */
		installedInventoryItemId: Option[String] = None,
	  /** Corresponds to the `AVAILABLE_PACKAGE` inventory item on the VM. If the vulnerability report was not updated after the VM inventory update, these values might not display in VM inventory. If there is no available fix, the field is empty. The `inventory_item` value specifies the latest `SoftwarePackage` available to the VM that fixes the vulnerability. */
		availableInventoryItemId: Option[String] = None,
	  /** The recommended [CPE URI](https://cpe.mitre.org/specification/) update that contains a fix for this vulnerability. */
		fixedCpeUri: Option[String] = None,
	  /** The upstream OS patch, packages or KB that fixes the vulnerability. */
		upstreamFix: Option[String] = None
	)
	
	case class ListVulnerabilityReportsResponse(
	  /** List of vulnerabilityReport objects. */
		vulnerabilityReports: Option[List[Schema.VulnerabilityReport]] = None,
	  /** The pagination token to retrieve the next page of vulnerabilityReports object. */
		nextPageToken: Option[String] = None
	)
	
	object OSPolicyAssignmentOperationMetadata {
		enum ApiMethodEnum extends Enum[ApiMethodEnum] { case API_METHOD_UNSPECIFIED, CREATE, UPDATE, DELETE }
		enum RolloutStateEnum extends Enum[RolloutStateEnum] { case ROLLOUT_STATE_UNSPECIFIED, IN_PROGRESS, CANCELLING, CANCELLED, SUCCEEDED }
	}
	case class OSPolicyAssignmentOperationMetadata(
	  /** Reference to the `OSPolicyAssignment` API resource. Format: `projects/{project_number}/locations/{location}/osPolicyAssignments/{os_policy_assignment_id@revision_id}` */
		osPolicyAssignment: Option[String] = None,
	  /** The OS policy assignment API method. */
		apiMethod: Option[Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum] = None,
	  /** State of the rollout */
		rolloutState: Option[Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum] = None,
	  /** Rollout start time */
		rolloutStartTime: Option[String] = None,
	  /** Rollout update time */
		rolloutUpdateTime: Option[String] = None
	)
	
	object GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata {
		enum ApiMethodEnum extends Enum[ApiMethodEnum] { case API_METHOD_UNSPECIFIED, CREATE, UPDATE, DELETE }
		enum RolloutStateEnum extends Enum[RolloutStateEnum] { case ROLLOUT_STATE_UNSPECIFIED, IN_PROGRESS, CANCELLING, CANCELLED, SUCCEEDED }
	}
	case class GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata(
	  /** Reference to the `OSPolicyAssignment` API resource. Format: `projects/{project_number}/locations/{location}/osPolicyAssignments/{os_policy_assignment_id@revision_id}` */
		osPolicyAssignment: Option[String] = None,
	  /** The OS policy assignment API method. */
		apiMethod: Option[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum] = None,
	  /** State of the rollout */
		rolloutState: Option[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum] = None,
	  /** Rollout start time */
		rolloutStartTime: Option[String] = None,
	  /** Rollout update time */
		rolloutUpdateTime: Option[String] = None
	)
	
	case class GoogleCloudOsconfigV2beta__OperationMetadata(
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
