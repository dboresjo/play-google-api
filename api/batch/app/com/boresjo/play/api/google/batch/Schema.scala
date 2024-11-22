package com.boresjo.play.api.google.batch

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
	
	case class Job(
	  /** Output only. Job name. For example: "projects/123456/locations/us-central1/jobs/job01". */
		name: Option[String] = None,
	  /** Output only. A system generated unique ID for the Job. */
		uid: Option[String] = None,
	  /** Priority of the Job. The valid value range is [0, 100). Default value is 0. Higher value indicates higher priority. A job with higher priority value is more likely to run earlier if all other requirements are satisfied. */
		priority: Option[String] = None,
	  /** Required. TaskGroups in the Job. Only one TaskGroup is supported now. */
		taskGroups: Option[List[Schema.TaskGroup]] = None,
	  /** Compute resource allocation for all TaskGroups in the Job. */
		allocationPolicy: Option[Schema.AllocationPolicy] = None,
	  /** Custom labels to apply to the job and any Cloud Logging [LogEntry](https://cloud.google.com/logging/docs/reference/v2/rest/v2/LogEntry) that it generates. Use labels to group and describe the resources they are applied to. Batch automatically applies predefined labels and supports multiple `labels` fields for each job, which each let you apply custom labels to various resources. Label names that start with "goog-" or "google-" are reserved for predefined labels. For more information about labels with Batch, see [Organize resources using labels](https://cloud.google.com/batch/docs/organize-resources-using-labels). */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Job status. It is read only for users. */
		status: Option[Schema.JobStatus] = None,
	  /** Output only. When the Job was created. */
		createTime: Option[String] = None,
	  /** Output only. The last time the Job was updated. */
		updateTime: Option[String] = None,
	  /** Log preservation policy for the Job. */
		logsPolicy: Option[Schema.LogsPolicy] = None,
	  /** Notification configurations. */
		notifications: Option[List[Schema.JobNotification]] = None
	)
	
	object TaskGroup {
		enum SchedulingPolicyEnum extends Enum[SchedulingPolicyEnum] { case SCHEDULING_POLICY_UNSPECIFIED, AS_SOON_AS_POSSIBLE, IN_ORDER }
	}
	case class TaskGroup(
	  /** Output only. TaskGroup name. The system generates this field based on parent Job name. For example: "projects/123456/locations/us-west1/jobs/job01/taskGroups/group01". */
		name: Option[String] = None,
	  /** Required. Tasks in the group share the same task spec. */
		taskSpec: Option[Schema.TaskSpec] = None,
	  /** Number of Tasks in the TaskGroup. Default is 1. */
		taskCount: Option[String] = None,
	  /** Max number of tasks that can run in parallel. Default to min(task_count, parallel tasks per job limit). See: [Job Limits](https://cloud.google.com/batch/quotas#job_limits). Field parallelism must be 1 if the scheduling_policy is IN_ORDER. */
		parallelism: Option[String] = None,
	  /** Scheduling policy for Tasks in the TaskGroup. The default value is AS_SOON_AS_POSSIBLE. */
		schedulingPolicy: Option[Schema.TaskGroup.SchedulingPolicyEnum] = None,
	  /** An array of environment variable mappings, which are passed to Tasks with matching indices. If task_environments is used then task_count should not be specified in the request (and will be ignored). Task count will be the length of task_environments. Tasks get a BATCH_TASK_INDEX and BATCH_TASK_COUNT environment variable, in addition to any environment variables set in task_environments, specifying the number of Tasks in the Task's parent TaskGroup, and the specific Task's index in the TaskGroup (0 through BATCH_TASK_COUNT - 1). */
		taskEnvironments: Option[List[Schema.Environment]] = None,
	  /** Max number of tasks that can be run on a VM at the same time. If not specified, the system will decide a value based on available compute resources on a VM and task requirements. */
		taskCountPerNode: Option[String] = None,
	  /** When true, Batch will populate a file with a list of all VMs assigned to the TaskGroup and set the BATCH_HOSTS_FILE environment variable to the path of that file. Defaults to false. The host file supports up to 1000 VMs. */
		requireHostsFile: Option[Boolean] = None,
	  /** When true, Batch will configure SSH to allow passwordless login between VMs running the Batch tasks in the same TaskGroup. */
		permissiveSsh: Option[Boolean] = None,
	  /** Optional. If not set or set to false, Batch uses the root user to execute runnables. If set to true, Batch runs the runnables using a non-root user. Currently, the non-root user Batch used is generated by OS Login. For more information, see [About OS Login](https://cloud.google.com/compute/docs/oslogin). */
		runAsNonRoot: Option[Boolean] = None
	)
	
	case class TaskSpec(
	  /** Required. The sequence of one or more runnables (executable scripts, executable containers, and/or barriers) for each task in this task group to run. Each task runs this list of runnables in order. For a task to succeed, all of its script and container runnables each must meet at least one of the following conditions: + The runnable exited with a zero status. + The runnable didn't finish, but you enabled its `background` subfield. + The runnable exited with a non-zero status, but you enabled its `ignore_exit_status` subfield. */
		runnables: Option[List[Schema.Runnable]] = None,
	  /** ComputeResource requirements. */
		computeResource: Option[Schema.ComputeResource] = None,
	  /** Maximum duration the task should run before being automatically retried (if enabled) or automatically failed. Format the value of this field as a time limit in seconds followed by `s`—for example, `3600s` for 1 hour. The field accepts any value between 0 and the maximum listed for the `Duration` field type at https://protobuf.dev/reference/protobuf/google.protobuf/#duration; however, the actual maximum run time for a job will be limited to the maximum run time for a job listed at https://cloud.google.com/batch/quotas#max-job-duration. */
		maxRunDuration: Option[String] = None,
	  /** Maximum number of retries on failures. The default, 0, which means never retry. The valid value range is [0, 10]. */
		maxRetryCount: Option[Int] = None,
	  /** Lifecycle management schema when any task in a task group is failed. Currently we only support one lifecycle policy. When the lifecycle policy condition is met, the action in the policy will execute. If task execution result does not meet with the defined lifecycle policy, we consider it as the default policy. Default policy means if the exit code is 0, exit task. If task ends with non-zero exit code, retry the task with max_retry_count. */
		lifecyclePolicies: Option[List[Schema.LifecyclePolicy]] = None,
	  /** Deprecated: please use environment(non-plural) instead. */
		environments: Option[Map[String, String]] = None,
	  /** Volumes to mount before running Tasks using this TaskSpec. */
		volumes: Option[List[Schema.Volume]] = None,
	  /** Environment variables to set before running the Task. */
		environment: Option[Schema.Environment] = None
	)
	
	case class Runnable(
	  /** Container runnable. */
		container: Option[Schema.Container] = None,
	  /** Script runnable. */
		script: Option[Schema.Script] = None,
	  /** Barrier runnable. */
		barrier: Option[Schema.Barrier] = None,
	  /** Optional. DisplayName is an optional field that can be provided by the caller. If provided, it will be used in logs and other outputs to identify the script, making it easier for users to understand the logs. If not provided the index of the runnable will be used for outputs. */
		displayName: Option[String] = None,
	  /** Normally, a runnable that returns a non-zero exit status fails and causes the task to fail. However, you can set this field to `true` to allow the task to continue executing its other runnables even if this runnable fails. */
		ignoreExitStatus: Option[Boolean] = None,
	  /** Normally, a runnable that doesn't exit causes its task to fail. However, you can set this field to `true` to configure a background runnable. Background runnables are allowed continue running in the background while the task executes subsequent runnables. For example, background runnables are useful for providing services to other runnables or providing debugging-support tools like SSH servers. Specifically, background runnables are killed automatically (if they have not already exited) a short time after all foreground runnables have completed. Even though this is likely to result in a non-zero exit status for the background runnable, these automatic kills are not treated as task failures. */
		background: Option[Boolean] = None,
	  /** By default, after a Runnable fails, no further Runnable are executed. This flag indicates that this Runnable must be run even if the Task has already failed. This is useful for Runnables that copy output files off of the VM or for debugging. The always_run flag does not override the Task's overall max_run_duration. If the max_run_duration has expired then no further Runnables will execute, not even always_run Runnables. */
		alwaysRun: Option[Boolean] = None,
	  /** Environment variables for this Runnable (overrides variables set for the whole Task or TaskGroup). */
		environment: Option[Schema.Environment] = None,
	  /** Timeout for this Runnable. */
		timeout: Option[String] = None,
	  /** Labels for this Runnable. */
		labels: Option[Map[String, String]] = None
	)
	
	case class Container(
	  /** Required. The URI to pull the container image from. */
		imageUri: Option[String] = None,
	  /** Required for some container images. Overrides the `CMD` specified in the container. If there is an `ENTRYPOINT` (either in the container image or with the `entrypoint` field below) then these commands are appended as arguments to the `ENTRYPOINT`. */
		commands: Option[List[String]] = None,
	  /** Required for some container images. Overrides the `ENTRYPOINT` specified in the container. */
		entrypoint: Option[String] = None,
	  /** Volumes to mount (bind mount) from the host machine files or directories into the container, formatted to match `--volume` option for the `docker run` command—for example, `/foo:/bar` or `/foo:/bar:ro`. If the `TaskSpec.Volumes` field is specified but this field is not, Batch will mount each volume from the host machine to the container with the same mount path by default. In this case, the default mount option for containers will be read-only (`ro`) for existing persistent disks and read-write (`rw`) for other volume types, regardless of the original mount options specified in `TaskSpec.Volumes`. If you need different mount settings, you can explicitly configure them in this field. */
		volumes: Option[List[String]] = None,
	  /** Required for some container images. Arbitrary additional options to include in the `docker run` command when running this container—for example, `--network host`. For the `--volume` option, use the `volumes` field for the container. */
		options: Option[String] = None,
	  /** If set to true, external network access to and from container will be blocked, containers that are with block_external_network as true can still communicate with each other, network cannot be specified in the `container.options` field. */
		blockExternalNetwork: Option[Boolean] = None,
	  /** Required if the container image is from a private Docker registry. The username to login to the Docker registry that contains the image. You can either specify the username directly by using plain text or specify an encrypted username by using a Secret Manager secret: `projects/&#42;/secrets/&#42;/versions/&#42;`. However, using a secret is recommended for enhanced security. Caution: If you specify the username using plain text, you risk the username being exposed to any users who can view the job or its logs. To avoid this risk, specify a secret that contains the username instead. Learn more about [Secret Manager](https://cloud.google.com/secret-manager/docs/) and [using Secret Manager with Batch](https://cloud.google.com/batch/docs/create-run-job-secret-manager). */
		username: Option[String] = None,
	  /** Required if the container image is from a private Docker registry. The password to login to the Docker registry that contains the image. For security, it is strongly recommended to specify an encrypted password by using a Secret Manager secret: `projects/&#42;/secrets/&#42;/versions/&#42;`. Warning: If you specify the password using plain text, you risk the password being exposed to any users who can view the job or its logs. To avoid this risk, specify a secret that contains the password instead. Learn more about [Secret Manager](https://cloud.google.com/secret-manager/docs/) and [using Secret Manager with Batch](https://cloud.google.com/batch/docs/create-run-job-secret-manager). */
		password: Option[String] = None,
	  /** Optional. If set to true, this container runnable uses Image streaming. Use Image streaming to allow the runnable to initialize without waiting for the entire container image to download, which can significantly reduce startup time for large container images. When `enableImageStreaming` is set to true, the container runtime is [containerd](https://containerd.io/) instead of Docker. Additionally, this container runnable only supports the following `container` subfields: `imageUri`, `commands[]`, `entrypoint`, and `volumes[]`; any other `container` subfields are ignored. For more information about the requirements and limitations for using Image streaming with Batch, see the [`image-streaming` sample on GitHub](https://github.com/GoogleCloudPlatform/batch-samples/tree/main/api-samples/image-streaming). */
		enableImageStreaming: Option[Boolean] = None
	)
	
	case class Script(
	  /** The path to a script file that is accessible from the host VM(s). Unless the script file supports the default `#!/bin/sh` shell interpreter, you must specify an interpreter by including a [shebang line](https://en.wikipedia.org/wiki/Shebang_(Unix) as the first line of the file. For example, to execute the script using bash, include `#!/bin/bash` as the first line of the file. Alternatively, to execute the script using Python3, include `#!/usr/bin/env python3` as the first line of the file. */
		path: Option[String] = None,
	  /** The text for a script. Unless the script text supports the default `#!/bin/sh` shell interpreter, you must specify an interpreter by including a [shebang line](https://en.wikipedia.org/wiki/Shebang_(Unix) at the beginning of the text. For example, to execute the script using bash, include `#!/bin/bash\n` at the beginning of the text. Alternatively, to execute the script using Python3, include `#!/usr/bin/env python3\n` at the beginning of the text. */
		text: Option[String] = None
	)
	
	case class Barrier(
	  /** Barriers are identified by their index in runnable list. Names are not required, but if present should be an identifier. */
		name: Option[String] = None
	)
	
	case class Environment(
	  /** A map of environment variable names to values. */
		variables: Option[Map[String, String]] = None,
	  /** A map of environment variable names to Secret Manager secret names. The VM will access the named secrets to set the value of each environment variable. */
		secretVariables: Option[Map[String, String]] = None,
	  /** An encrypted JSON dictionary where the key/value pairs correspond to environment variable names and their values. */
		encryptedVariables: Option[Schema.KMSEnvMap] = None
	)
	
	case class KMSEnvMap(
	  /** The name of the KMS key that will be used to decrypt the cipher text. */
		keyName: Option[String] = None,
	  /** The value of the cipherText response from the `encrypt` method. */
		cipherText: Option[String] = None
	)
	
	case class ComputeResource(
	  /** The milliCPU count. `cpuMilli` defines the amount of CPU resources per task in milliCPU units. For example, `1000` corresponds to 1 vCPU per task. If undefined, the default value is `2000`. If you also define the VM's machine type using the `machineType` in [InstancePolicy](https://cloud.google.com/batch/docs/reference/rest/v1/projects.locations.jobs#instancepolicy) field or inside the `instanceTemplate` in the [InstancePolicyOrTemplate](https://cloud.google.com/batch/docs/reference/rest/v1/projects.locations.jobs#instancepolicyortemplate) field, make sure the CPU resources for both fields are compatible with each other and with how many tasks you want to allow to run on the same VM at the same time. For example, if you specify the `n2-standard-2` machine type, which has 2 vCPUs each, you are recommended to set `cpuMilli` no more than `2000`, or you are recommended to run two tasks on the same VM if you set `cpuMilli` to `1000` or less. */
		cpuMilli: Option[String] = None,
	  /** Memory in MiB. `memoryMib` defines the amount of memory per task in MiB units. If undefined, the default value is `2000`. If you also define the VM's machine type using the `machineType` in [InstancePolicy](https://cloud.google.com/batch/docs/reference/rest/v1/projects.locations.jobs#instancepolicy) field or inside the `instanceTemplate` in the [InstancePolicyOrTemplate](https://cloud.google.com/batch/docs/reference/rest/v1/projects.locations.jobs#instancepolicyortemplate) field, make sure the memory resources for both fields are compatible with each other and with how many tasks you want to allow to run on the same VM at the same time. For example, if you specify the `n2-standard-2` machine type, which has 8 GiB each, you are recommended to set `memoryMib` to no more than `8192`, or you are recommended to run two tasks on the same VM if you set `memoryMib` to `4096` or less. */
		memoryMib: Option[String] = None,
	  /** Extra boot disk size in MiB for each task. */
		bootDiskMib: Option[String] = None
	)
	
	object LifecyclePolicy {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, RETRY_TASK, FAIL_TASK }
	}
	case class LifecyclePolicy(
	  /** Action to execute when ActionCondition is true. When RETRY_TASK is specified, we will retry failed tasks if we notice any exit code match and fail tasks if no match is found. Likewise, when FAIL_TASK is specified, we will fail tasks if we notice any exit code match and retry tasks if no match is found. */
		action: Option[Schema.LifecyclePolicy.ActionEnum] = None,
	  /** Conditions that decide why a task failure is dealt with a specific action. */
		actionCondition: Option[Schema.ActionCondition] = None
	)
	
	case class ActionCondition(
	  /** Exit codes of a task execution. If there are more than 1 exit codes, when task executes with any of the exit code in the list, the condition is met and the action will be executed. */
		exitCodes: Option[List[Int]] = None
	)
	
	case class Volume(
	  /** A Network File System (NFS) volume. For example, a Filestore file share. */
		nfs: Option[Schema.NFS] = None,
	  /** A Google Cloud Storage (GCS) volume. */
		gcs: Option[Schema.GCS] = None,
	  /** Device name of an attached disk volume, which should align with a device_name specified by job.allocation_policy.instances[0].policy.disks[i].device_name or defined by the given instance template in job.allocation_policy.instances[0].instance_template. */
		deviceName: Option[String] = None,
	  /** The mount path for the volume, e.g. /mnt/disks/share. */
		mountPath: Option[String] = None,
	  /** Mount options vary based on the type of storage volume: &#42; For a Cloud Storage bucket, all the mount options provided by the [`gcsfuse` tool](https://cloud.google.com/storage/docs/gcsfuse-cli) are supported. &#42; For an existing persistent disk, all mount options provided by the [`mount` command](https://man7.org/linux/man-pages/man8/mount.8.html) except writing are supported. This is due to restrictions of [multi-writer mode](https://cloud.google.com/compute/docs/disks/sharing-disks-between-vms). &#42; For any other disk or a Network File System (NFS), all the mount options provided by the `mount` command are supported. */
		mountOptions: Option[List[String]] = None
	)
	
	case class NFS(
	  /** The IP address of the NFS. */
		server: Option[String] = None,
	  /** Remote source path exported from the NFS, e.g., "/share". */
		remotePath: Option[String] = None
	)
	
	case class GCS(
	  /** Remote path, either a bucket name or a subdirectory of a bucket, e.g.: bucket_name, bucket_name/subdirectory/ */
		remotePath: Option[String] = None
	)
	
	case class AllocationPolicy(
	  /** Location where compute resources should be allocated for the Job. */
		location: Option[Schema.LocationPolicy] = None,
	  /** Describe instances that can be created by this AllocationPolicy. Only instances[0] is supported now. */
		instances: Option[List[Schema.InstancePolicyOrTemplate]] = None,
	  /** Defines the service account for Batch-created VMs. If omitted, the [default Compute Engine service account](https://cloud.google.com/compute/docs/access/service-accounts#default_service_account) is used. Must match the service account specified in any used instance template configured in the Batch job. Includes the following fields: &#42; email: The service account's email address. If not set, the default Compute Engine service account is used. &#42; scopes: Additional OAuth scopes to grant the service account, beyond the default cloud-platform scope. (list of strings) */
		serviceAccount: Option[Schema.ServiceAccount] = None,
	  /** Custom labels to apply to the job and all the Compute Engine resources that both are created by this allocation policy and support labels. Use labels to group and describe the resources they are applied to. Batch automatically applies predefined labels and supports multiple `labels` fields for each job, which each let you apply custom labels to various resources. Label names that start with "goog-" or "google-" are reserved for predefined labels. For more information about labels with Batch, see [Organize resources using labels](https://cloud.google.com/batch/docs/organize-resources-using-labels). */
		labels: Option[Map[String, String]] = None,
	  /** The network policy. If you define an instance template in the `InstancePolicyOrTemplate` field, Batch will use the network settings in the instance template instead of this field. */
		network: Option[Schema.NetworkPolicy] = None,
	  /** The placement policy. */
		placement: Option[Schema.PlacementPolicy] = None,
	  /** Optional. Tags applied to the VM instances. The tags identify valid sources or targets for network firewalls. Each tag must be 1-63 characters long, and comply with [RFC1035](https://www.ietf.org/rfc/rfc1035.txt). */
		tags: Option[List[String]] = None
	)
	
	case class LocationPolicy(
	  /** A list of allowed location names represented by internal URLs. Each location can be a region or a zone. Only one region or multiple zones in one region is supported now. For example, ["regions/us-central1"] allow VMs in any zones in region us-central1. ["zones/us-central1-a", "zones/us-central1-c"] only allow VMs in zones us-central1-a and us-central1-c. Mixing locations from different regions would cause errors. For example, ["regions/us-central1", "zones/us-central1-a", "zones/us-central1-b", "zones/us-west1-a"] contains locations from two distinct regions: us-central1 and us-west1. This combination will trigger an error. */
		allowedLocations: Option[List[String]] = None
	)
	
	case class InstancePolicyOrTemplate(
	  /** InstancePolicy. */
		policy: Option[Schema.InstancePolicy] = None,
	  /** Name of an instance template used to create VMs. Named the field as 'instance_template' instead of 'template' to avoid C++ keyword conflict. Batch only supports global instance templates from the same project as the job. You can specify the global instance template as a full or partial URL. */
		instanceTemplate: Option[String] = None,
	  /** Set this field true if you want Batch to help fetch drivers from a third party location and install them for GPUs specified in `policy.accelerators` or `instance_template` on your behalf. Default is false. For Container-Optimized Image cases, Batch will install the accelerator driver following milestones of https://cloud.google.com/container-optimized-os/docs/release-notes. For non Container-Optimized Image cases, following https://github.com/GoogleCloudPlatform/compute-gpu-installation/blob/main/linux/install_gpu_driver.py. */
		installGpuDrivers: Option[Boolean] = None,
	  /** Optional. Set this field true if you want Batch to install Ops Agent on your behalf. Default is false. */
		installOpsAgent: Option[Boolean] = None,
	  /** Optional. Set this field to `true` if you want Batch to block project-level SSH keys from accessing this job's VMs. Alternatively, you can configure the job to specify a VM instance template that blocks project-level SSH keys. In either case, Batch blocks project-level SSH keys while creating the VMs for this job. Batch allows project-level SSH keys for a job's VMs only if all the following are true: + This field is undefined or set to `false`. + The job's VM instance template (if any) doesn't block project-level SSH keys. Notably, you can override this behavior by manually updating a VM to block or allow project-level SSH keys. For more information about blocking project-level SSH keys, see the Compute Engine documentation: https://cloud.google.com/compute/docs/connect/restrict-ssh-keys#block-keys */
		blockProjectSshKeys: Option[Boolean] = None
	)
	
	object InstancePolicy {
		enum ProvisioningModelEnum extends Enum[ProvisioningModelEnum] { case PROVISIONING_MODEL_UNSPECIFIED, STANDARD, SPOT, PREEMPTIBLE }
	}
	case class InstancePolicy(
	  /** The Compute Engine machine type. */
		machineType: Option[String] = None,
	  /** The minimum CPU platform. See https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform. */
		minCpuPlatform: Option[String] = None,
	  /** The provisioning model. */
		provisioningModel: Option[Schema.InstancePolicy.ProvisioningModelEnum] = None,
	  /** The accelerators attached to each VM instance. */
		accelerators: Option[List[Schema.Accelerator]] = None,
	  /** Boot disk to be created and attached to each VM by this InstancePolicy. Boot disk will be deleted when the VM is deleted. Batch API now only supports booting from image. */
		bootDisk: Option[Schema.Disk] = None,
	  /** Non-boot disks to be attached for each VM created by this InstancePolicy. New disks will be deleted when the VM is deleted. A non-boot disk is a disk that can be of a device with a file system or a raw storage drive that is not ready for data storage and accessing. */
		disks: Option[List[Schema.AttachedDisk]] = None,
	  /** Optional. If not specified (default), VMs will consume any applicable reservation. If "NO_RESERVATION" is specified, VMs will not consume any reservation. Otherwise, if specified, VMs will consume only the specified reservation. */
		reservation: Option[String] = None
	)
	
	case class Accelerator(
	  /** The accelerator type. For example, "nvidia-tesla-t4". See `gcloud compute accelerator-types list`. */
		`type`: Option[String] = None,
	  /** The number of accelerators of this type. */
		count: Option[String] = None,
	  /** Deprecated: please use instances[0].install_gpu_drivers instead. */
		installGpuDrivers: Option[Boolean] = None,
	  /** Optional. The NVIDIA GPU driver version that should be installed for this type. You can define the specific driver version such as "470.103.01", following the driver version requirements in https://cloud.google.com/compute/docs/gpus/install-drivers-gpu#minimum-driver. Batch will install the specific accelerator driver if qualified. */
		driverVersion: Option[String] = None
	)
	
	case class Disk(
	  /** URL for a VM image to use as the data source for this disk. For example, the following are all valid URLs: &#42; Specify the image by its family name: projects/{project}/global/images/family/{image_family} &#42; Specify the image version: projects/{project}/global/images/{image_version} You can also use Batch customized image in short names. The following image values are supported for a boot disk: &#42; `batch-debian`: use Batch Debian images. &#42; `batch-cos`: use Batch Container-Optimized images. &#42; `batch-hpc-rocky`: use Batch HPC Rocky Linux images. */
		image: Option[String] = None,
	  /** Name of a snapshot used as the data source. Snapshot is not supported as boot disk now. */
		snapshot: Option[String] = None,
	  /** Disk type as shown in `gcloud compute disk-types list`. For example, local SSD uses type "local-ssd". Persistent disks and boot disks use "pd-balanced", "pd-extreme", "pd-ssd" or "pd-standard". If not specified, "pd-standard" will be used as the default type for non-boot disks, "pd-balanced" will be used as the default type for boot disks. */
		`type`: Option[String] = None,
	  /** Disk size in GB. &#42;&#42;Non-Boot Disk&#42;&#42;: If the `type` specifies a persistent disk, this field is ignored if `data_source` is set as `image` or `snapshot`. If the `type` specifies a local SSD, this field should be a multiple of 375 GB, otherwise, the final size will be the next greater multiple of 375 GB. &#42;&#42;Boot Disk&#42;&#42;: Batch will calculate the boot disk size based on source image and task requirements if you do not speicify the size. If both this field and the `boot_disk_mib` field in task spec's `compute_resource` are defined, Batch will only honor this field. Also, this field should be no smaller than the source disk's size when the `data_source` is set as `snapshot` or `image`. For example, if you set an image as the `data_source` field and the image's default disk size 30 GB, you can only use this field to make the disk larger or equal to 30 GB. */
		sizeGb: Option[String] = None,
	  /** Local SSDs are available through both "SCSI" and "NVMe" interfaces. If not indicated, "NVMe" will be the default one for local ssds. This field is ignored for persistent disks as the interface is chosen automatically. See https://cloud.google.com/compute/docs/disks/persistent-disks#choose_an_interface. */
		diskInterface: Option[String] = None
	)
	
	case class AttachedDisk(
		newDisk: Option[Schema.Disk] = None,
	  /** Name of an existing PD. */
		existingDisk: Option[String] = None,
	  /** Device name that the guest operating system will see. It is used by Runnable.volumes field to mount disks. So please specify the device_name if you want Batch to help mount the disk, and it should match the device_name field in volumes. */
		deviceName: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** Email address of the service account. */
		email: Option[String] = None,
	  /** List of scopes to be enabled for this service account. */
		scopes: Option[List[String]] = None
	)
	
	case class NetworkPolicy(
	  /** Network configurations. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None
	)
	
	case class NetworkInterface(
	  /** The URL of an existing network resource. You can specify the network as a full or partial URL. For example, the following are all valid URLs: &#42; https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network} &#42; projects/{project}/global/networks/{network} &#42; global/networks/{network} */
		network: Option[String] = None,
	  /** The URL of an existing subnetwork resource in the network. You can specify the subnetwork as a full or partial URL. For example, the following are all valid URLs: &#42; https://www.googleapis.com/compute/v1/projects/{project}/regions/{region}/subnetworks/{subnetwork} &#42; projects/{project}/regions/{region}/subnetworks/{subnetwork} &#42; regions/{region}/subnetworks/{subnetwork} */
		subnetwork: Option[String] = None,
	  /** Default is false (with an external IP address). Required if no external public IP address is attached to the VM. If no external public IP address, additional configuration is required to allow the VM to access Google Services. See https://cloud.google.com/vpc/docs/configure-private-google-access and https://cloud.google.com/nat/docs/gce-example#create-nat for more information. */
		noExternalIpAddress: Option[Boolean] = None
	)
	
	case class PlacementPolicy(
	  /** UNSPECIFIED vs. COLLOCATED (default UNSPECIFIED). Use COLLOCATED when you want VMs to be located close to each other for low network latency between the VMs. No placement policy will be generated when collocation is UNSPECIFIED. */
		collocation: Option[String] = None,
	  /** When specified, causes the job to fail if more than max_distance logical switches are required between VMs. Batch uses the most compact possible placement of VMs even when max_distance is not specified. An explicit max_distance makes that level of compactness a strict requirement. Not yet implemented */
		maxDistance: Option[String] = None
	)
	
	object JobStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, QUEUED, SCHEDULED, RUNNING, SUCCEEDED, FAILED, DELETION_IN_PROGRESS }
	}
	case class JobStatus(
	  /** Job state */
		state: Option[Schema.JobStatus.StateEnum] = None,
	  /** Job status events */
		statusEvents: Option[List[Schema.StatusEvent]] = None,
	  /** Aggregated task status for each TaskGroup in the Job. The map key is TaskGroup ID. */
		taskGroups: Option[Map[String, Schema.TaskGroupStatus]] = None,
	  /** The duration of time that the Job spent in status RUNNING. */
		runDuration: Option[String] = None
	)
	
	object StatusEvent {
		enum TaskStateEnum extends Enum[TaskStateEnum] { case STATE_UNSPECIFIED, PENDING, ASSIGNED, RUNNING, FAILED, SUCCEEDED, UNEXECUTED }
	}
	case class StatusEvent(
	  /** Type of the event. */
		`type`: Option[String] = None,
	  /** Description of the event. */
		description: Option[String] = None,
	  /** The time this event occurred. */
		eventTime: Option[String] = None,
	  /** Task Execution. This field is only defined for task-level status events where the task fails. */
		taskExecution: Option[Schema.TaskExecution] = None,
	  /** Task State. This field is only defined for task-level status events. */
		taskState: Option[Schema.StatusEvent.TaskStateEnum] = None
	)
	
	case class TaskExecution(
	  /** The exit code of a finished task. If the task succeeded, the exit code will be 0. If the task failed but not due to the following reasons, the exit code will be 50000. Otherwise, it can be from different sources: &#42; Batch known failures: https://cloud.google.com/batch/docs/troubleshooting#reserved-exit-codes. &#42; Batch runnable execution failures; you can rely on Batch logs to further diagnose: https://cloud.google.com/batch/docs/analyze-job-using-logs. If there are multiple runnables failures, Batch only exposes the first error. */
		exitCode: Option[Int] = None
	)
	
	case class TaskGroupStatus(
	  /** Count of task in each state in the TaskGroup. The map key is task state name. */
		counts: Option[Map[String, String]] = None,
	  /** Status of instances allocated for the TaskGroup. */
		instances: Option[List[Schema.InstanceStatus]] = None
	)
	
	object InstanceStatus {
		enum ProvisioningModelEnum extends Enum[ProvisioningModelEnum] { case PROVISIONING_MODEL_UNSPECIFIED, STANDARD, SPOT, PREEMPTIBLE }
	}
	case class InstanceStatus(
	  /** The Compute Engine machine type. */
		machineType: Option[String] = None,
	  /** The VM instance provisioning model. */
		provisioningModel: Option[Schema.InstanceStatus.ProvisioningModelEnum] = None,
	  /** The max number of tasks can be assigned to this instance type. */
		taskPack: Option[String] = None,
	  /** The VM boot disk. */
		bootDisk: Option[Schema.Disk] = None
	)
	
	object LogsPolicy {
		enum DestinationEnum extends Enum[DestinationEnum] { case DESTINATION_UNSPECIFIED, CLOUD_LOGGING, PATH }
	}
	case class LogsPolicy(
	  /** If and where logs should be saved. */
		destination: Option[Schema.LogsPolicy.DestinationEnum] = None,
	  /** When `destination` is set to `PATH`, you must set this field to the path where you want logs to be saved. This path can point to a local directory on the VM or (if congifured) a directory under the mount path of any Cloud Storage bucket, network file system (NFS), or writable persistent disk that is mounted to the job. For example, if the job has a bucket with `mountPath` set to `/mnt/disks/my-bucket`, you can write logs to the root directory of the `remotePath` of that bucket by setting this field to `/mnt/disks/my-bucket/`. */
		logsPath: Option[String] = None,
	  /** Optional. When `destination` is set to `CLOUD_LOGGING`, you can optionally set this field to configure additional settings for Cloud Logging. */
		cloudLoggingOption: Option[Schema.CloudLoggingOption] = None
	)
	
	case class CloudLoggingOption(
	  /** Optional. Set this field to `true` to change the [monitored resource type](https://cloud.google.com/monitoring/api/resources) for Cloud Logging logs generated by this Batch job from the [`batch.googleapis.com/Job`](https://cloud.google.com/monitoring/api/resources#tag_batch.googleapis.com/Job) type to the formerly used [`generic_task`](https://cloud.google.com/monitoring/api/resources#tag_generic_task) type. */
		useGenericTaskMonitoredResource: Option[Boolean] = None
	)
	
	case class JobNotification(
	  /** The Pub/Sub topic where notifications for the job, like state changes, will be published. If undefined, no Pub/Sub notifications are sent for this job. Specify the topic using the following format: `projects/{project}/topics/{topic}`. Notably, if you want to specify a Pub/Sub topic that is in a different project than the job, your administrator must grant your project's Batch service agent permission to publish to that topic. For more information about configuring Pub/Sub notifications for a job, see https://cloud.google.com/batch/docs/enable-notifications. */
		pubsubTopic: Option[String] = None,
	  /** The attribute requirements of messages to be sent to this Pub/Sub topic. Without this field, no message will be sent. */
		message: Option[Schema.Message] = None
	)
	
	object Message {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, JOB_STATE_CHANGED, TASK_STATE_CHANGED }
		enum NewJobStateEnum extends Enum[NewJobStateEnum] { case STATE_UNSPECIFIED, QUEUED, SCHEDULED, RUNNING, SUCCEEDED, FAILED, DELETION_IN_PROGRESS }
		enum NewTaskStateEnum extends Enum[NewTaskStateEnum] { case STATE_UNSPECIFIED, PENDING, ASSIGNED, RUNNING, FAILED, SUCCEEDED, UNEXECUTED }
	}
	case class Message(
	  /** The message type. */
		`type`: Option[Schema.Message.TypeEnum] = None,
	  /** The new job state. */
		newJobState: Option[Schema.Message.NewJobStateEnum] = None,
	  /** The new task state. */
		newTaskState: Option[Schema.Message.NewTaskStateEnum] = None
	)
	
	case class ListJobsResponse(
	  /** Jobs. */
		jobs: Option[List[Schema.Job]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Task(
	  /** Task name. The name is generated from the parent TaskGroup name and 'id' field. For example: "projects/123456/locations/us-west1/jobs/job01/taskGroups/group01/tasks/task01". */
		name: Option[String] = None,
	  /** Task Status. */
		status: Option[Schema.TaskStatus] = None
	)
	
	object TaskStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ASSIGNED, RUNNING, FAILED, SUCCEEDED, UNEXECUTED }
	}
	case class TaskStatus(
	  /** Task state. */
		state: Option[Schema.TaskStatus.StateEnum] = None,
	  /** Detailed info about why the state is reached. */
		statusEvents: Option[List[Schema.StatusEvent]] = None
	)
	
	case class ListTasksResponse(
	  /** Tasks. */
		tasks: Option[List[Schema.Task]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ReportAgentStateRequest(
	  /** Agent metadata. */
		metadata: Option[Schema.AgentMetadata] = None,
	  /** Agent info. */
		agentInfo: Option[Schema.AgentInfo] = None,
	  /** Agent timing info. */
		agentTimingInfo: Option[Schema.AgentTimingInfo] = None
	)
	
	case class AgentMetadata(
	  /** Agent zone. */
		zone: Option[String] = None,
	  /** GCP instance name (go/instance-name). */
		instance: Option[String] = None,
	  /** GCP instance ID (go/instance-id). */
		instanceId: Option[String] = None,
	  /** When the VM agent started. Use agent_startup_time instead. */
		creationTime: Option[String] = None,
	  /** Full name of the entity that created this vm. For MIG, this path is: projects/{project}/regions/{region}/InstanceGroupManagers/{igm} The value is retrieved from the vm metadata key of "created-by". */
		creator: Option[String] = None,
	  /** agent binary version running on VM */
		version: Option[String] = None,
	  /** parsed contents of /etc/os-release */
		osRelease: Option[Map[String, String]] = None,
	  /** image version for the VM that this agent is installed on. */
		imageVersion: Option[String] = None,
	  /** If the GCP instance has received preemption notice. */
		instancePreemptionNoticeReceived: Option[Boolean] = None,
	  /** Optional. machine type of the VM */
		machineType: Option[String] = None
	)
	
	object AgentInfo {
		enum StateEnum extends Enum[StateEnum] { case AGENT_STATE_UNSPECIFIED, AGENT_STARTING, AGENT_RUNNING, AGENT_STOPPED }
	}
	case class AgentInfo(
	  /** Agent state. */
		state: Option[Schema.AgentInfo.StateEnum] = None,
	  /** Optional. The assigned Job ID */
		jobId: Option[String] = None,
	  /** Task Info. */
		tasks: Option[List[Schema.AgentTaskInfo]] = None,
	  /** When the AgentInfo is generated. */
		reportTime: Option[String] = None,
	  /** The assigned task group ID. */
		taskGroupId: Option[String] = None
	)
	
	case class AgentTaskInfo(
	  /** ID of the Task */
		taskId: Option[String] = None,
	  /** The status of the Task. If we need agent specific fields we should fork the public TaskStatus into an agent specific one. Or add them below. */
		taskStatus: Option[Schema.TaskStatus] = None,
	  /** The highest index of a runnable started by the agent for this task. The runnables are indexed from 1. Value 0 is undefined. */
		runnable: Option[String] = None
	)
	
	case class AgentTimingInfo(
	  /** Boot timestamp of the VM OS */
		bootTime: Option[String] = None,
	  /** Startup time of the Batch VM script. */
		scriptStartupTime: Option[String] = None,
	  /** Agent startup time */
		agentStartupTime: Option[String] = None
	)
	
	case class ReportAgentStateResponse(
	  /** Tasks assigned to the agent */
		tasks: Option[List[Schema.AgentTask]] = None,
	  /** Minimum report interval override */
		minReportInterval: Option[String] = None,
	  /** Default report interval override */
		defaultReportInterval: Option[String] = None,
	  /** If true, the cloud logging for batch agent will use batch.googleapis.com/Job as monitored resource for Batch job related logging. */
		useBatchMonitoredResource: Option[Boolean] = None
	)
	
	object AgentTask {
		enum IntendedStateEnum extends Enum[IntendedStateEnum] { case INTENDED_STATE_UNSPECIFIED, ASSIGNED, CANCELLED, DELETED }
		enum TaskSourceEnum extends Enum[TaskSourceEnum] { case TASK_SOURCE_UNSPECIFIED, BATCH_INTERNAL, USER }
	}
	case class AgentTask(
	  /** Task name. */
		task: Option[String] = None,
	  /** Task Spec. This field will be replaced by agent_task_spec below in future. */
		spec: Option[Schema.TaskSpec] = None,
	  /** AgentTaskSpec is the taskSpec representation between Agent and CLH communication. This field will replace the TaskSpec field above in future to have a better separation between user-facaing API and internal API. */
		agentTaskSpec: Option[Schema.AgentTaskSpec] = None,
	  /** Task status. */
		status: Option[Schema.TaskStatus] = None,
	  /** The intended state of the task. */
		intendedState: Option[Schema.AgentTask.IntendedStateEnum] = None,
	  /** The highest barrier reached by all tasks in the task's TaskGroup. */
		reachedBarrier: Option[String] = None,
	  /** TaskSource represents the source of the task. */
		taskSource: Option[Schema.AgentTask.TaskSourceEnum] = None
	)
	
	case class AgentTaskSpec(
	  /** AgentTaskRunnable is runanbles that will be executed on the agent. */
		runnables: Option[List[Schema.AgentTaskRunnable]] = None,
	  /** Maximum duration the task should run before being automatically retried (if enabled) or automatically failed. Format the value of this field as a time limit in seconds followed by `s`—for example, `3600s` for 1 hour. The field accepts any value between 0 and the maximum listed for the `Duration` field type at https://protobuf.dev/reference/protobuf/google.protobuf/#duration; however, the actual maximum run time for a job will be limited to the maximum run time for a job listed at https://cloud.google.com/batch/quotas#max-job-duration. */
		maxRunDuration: Option[String] = None,
	  /** Environment variables to set before running the Task. */
		environment: Option[Schema.AgentEnvironment] = None,
	  /** User account on the VM to run the runnables in the agentTaskSpec. If not set, the runnable will be run under root user. */
		userAccount: Option[Schema.AgentTaskUserAccount] = None,
	  /** Logging option for the task. */
		loggingOption: Option[Schema.AgentTaskLoggingOption] = None
	)
	
	case class AgentTaskRunnable(
	  /** Container runnable. */
		container: Option[Schema.AgentContainer] = None,
	  /** Script runnable. */
		script: Option[Schema.AgentScript] = None,
	  /** Normally, a non-zero exit status causes the Task to fail. This flag allows execution of other Runnables to continue instead. */
		ignoreExitStatus: Option[Boolean] = None,
	  /** This flag allows a Runnable to continue running in the background while the Task executes subsequent Runnables. This is useful to provide services to other Runnables (or to provide debugging support tools like SSH servers). */
		background: Option[Boolean] = None,
	  /** By default, after a Runnable fails, no further Runnable are executed. This flag indicates that this Runnable must be run even if the Task has already failed. This is useful for Runnables that copy output files off of the VM or for debugging. The always_run flag does not override the Task's overall max_run_duration. If the max_run_duration has expired then no further Runnables will execute, not even always_run Runnables. */
		alwaysRun: Option[Boolean] = None,
	  /** Environment variables for this Runnable (overrides variables set for the whole Task or TaskGroup). */
		environment: Option[Schema.AgentEnvironment] = None,
	  /** Timeout for this Runnable. */
		timeout: Option[String] = None
	)
	
	case class AgentContainer(
	  /** The URI to pull the container image from. */
		imageUri: Option[String] = None,
	  /** Overrides the `CMD` specified in the container. If there is an ENTRYPOINT (either in the container image or with the entrypoint field below) then commands are appended as arguments to the ENTRYPOINT. */
		commands: Option[List[String]] = None,
	  /** Overrides the `ENTRYPOINT` specified in the container. */
		entrypoint: Option[String] = None,
	  /** Volumes to mount (bind mount) from the host machine files or directories into the container, formatted to match docker run's --volume option, e.g. /foo:/bar, or /foo:/bar:ro */
		volumes: Option[List[String]] = None,
	  /** Arbitrary additional options to include in the "docker run" command when running this container, e.g. "--network host". */
		options: Option[String] = None
	)
	
	case class AgentScript(
	  /** Script file path on the host VM. To specify an interpreter, please add a `#!`(also known as [shebang line](https://en.wikipedia.org/wiki/Shebang_(Unix))) as the first line of the file.(For example, to execute the script using bash, `#!/bin/bash` should be the first line of the file. To execute the script using`Python3`, `#!/usr/bin/env python3` should be the first line of the file.) Otherwise, the file will by default be executed by `/bin/sh`. */
		path: Option[String] = None,
	  /** Shell script text. To specify an interpreter, please add a `#!\n` at the beginning of the text.(For example, to execute the script using bash, `#!/bin/bash\n` should be added. To execute the script using`Python3`, `#!/usr/bin/env python3\n` should be added.) Otherwise, the script will by default be executed by `/bin/sh`. */
		text: Option[String] = None
	)
	
	case class AgentEnvironment(
	  /** A map of environment variable names to values. */
		variables: Option[Map[String, String]] = None,
	  /** A map of environment variable names to Secret Manager secret names. The VM will access the named secrets to set the value of each environment variable. */
		secretVariables: Option[Map[String, String]] = None,
	  /** An encrypted JSON dictionary where the key/value pairs correspond to environment variable names and their values. */
		encryptedVariables: Option[Schema.AgentKMSEnvMap] = None
	)
	
	case class AgentKMSEnvMap(
	  /** The name of the KMS key that will be used to decrypt the cipher text. */
		keyName: Option[String] = None,
	  /** The value of the cipherText response from the `encrypt` method. */
		cipherText: Option[String] = None
	)
	
	case class AgentTaskUserAccount(
	  /** uid is an unique identifier of the POSIX account corresponding to the user account. */
		uid: Option[String] = None,
	  /** gid id an unique identifier of the POSIX account group corresponding to the user account. */
		gid: Option[String] = None
	)
	
	case class AgentTaskLoggingOption(
	  /** Labels to be added to the log entry. Now only cloud logging is supported. */
		labels: Option[Map[String, String]] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
