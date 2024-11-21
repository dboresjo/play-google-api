package com.boresjo.play.api.google.ml

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudMlV1__Job {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, QUEUED, PREPARING, RUNNING, SUCCEEDED, FAILED, CANCELLING, CANCELLED }
	}
	case class GoogleCloudMlV1__Job(
	  /** Required. The user-specified id of the job. */
		jobId: Option[String] = None,
	  /** Input parameters to create a training job. */
		trainingInput: Option[Schema.GoogleCloudMlV1__TrainingInput] = None,
	  /** Input parameters to create a prediction job. */
		predictionInput: Option[Schema.GoogleCloudMlV1__PredictionInput] = None,
	  /** Output only. When the job was created. */
		createTime: Option[String] = None,
	  /** Output only. When the job processing was started. */
		startTime: Option[String] = None,
	  /** Output only. When the job processing was completed. */
		endTime: Option[String] = None,
	  /** Output only. The detailed state of a job. */
		state: Option[Schema.GoogleCloudMlV1__Job.StateEnum] = None,
	  /** Output only. The details of a failure or a cancellation. */
		errorMessage: Option[String] = None,
	  /** The current training job result. */
		trainingOutput: Option[Schema.GoogleCloudMlV1__TrainingOutput] = None,
	  /** The current prediction job result. */
		predictionOutput: Option[Schema.GoogleCloudMlV1__PredictionOutput] = None,
	  /** Optional. One or more labels that you can add, to organize your jobs. Each label is a key-value pair, where both the key and the value are arbitrary strings that you supply. For more information, see the documentation on using labels. */
		labels: Option[Map[String, String]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a job from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform job updates in order to avoid race conditions: An `etag` is returned in the response to `GetJob`, and systems are expected to put that etag in the request to `UpdateJob` to ensure that their change will be applied to the same version of the job. */
		etag: Option[String] = None,
	  /** Output only. It's only effect when the job is in QUEUED state. If it's positive, it indicates the job's position in the job scheduler. It's 0 when the job is already scheduled. */
		jobPosition: Option[String] = None
	)
	
	object GoogleCloudMlV1__TrainingInput {
		enum ScaleTierEnum extends Enum[ScaleTierEnum] { case BASIC, STANDARD_1, PREMIUM_1, BASIC_GPU, BASIC_TPU, CUSTOM }
	}
	case class GoogleCloudMlV1__TrainingInput(
	  /** Required. Specifies the machine types, the number of replicas for workers and parameter servers. */
		scaleTier: Option[Schema.GoogleCloudMlV1__TrainingInput.ScaleTierEnum] = None,
	  /** Optional. Specifies the type of virtual machine to use for your training job's master worker. You must specify this field when `scaleTier` is set to `CUSTOM`. You can use certain Compute Engine machine types directly in this field. See the [list of compatible Compute Engine machine types](/ai-platform/training/docs/machine-types#compute-engine-machine-types). Alternatively, you can use the certain legacy machine types in this field. See the [list of legacy machine types](/ai-platform/training/docs/machine-types#legacy-machine-types). Finally, if you want to use a TPU for training, specify `cloud_tpu` in this field. Learn more about the [special configuration options for training with TPUs](/ai-platform/training/docs/using-tpus#configuring_a_custom_tpu_machine). */
		masterType: Option[String] = None,
	  /** Optional. The configuration for your master worker. You should only set `masterConfig.acceleratorConfig` if `masterType` is set to a Compute Engine machine type. Learn about [restrictions on accelerator configurations for training.](/ai-platform/training/docs/using-gpus#compute-engine-machine-types-with-gpu) Set `masterConfig.imageUri` only if you build a custom image. Only one of `masterConfig.imageUri` and `runtimeVersion` should be set. Learn more about [configuring custom containers](/ai-platform/training/docs/distributed-training-containers). */
		masterConfig: Option[Schema.GoogleCloudMlV1__ReplicaConfig] = None,
	  /** Optional. Specifies the type of virtual machine to use for your training job's worker nodes. The supported values are the same as those described in the entry for `masterType`. This value must be consistent with the category of machine type that `masterType` uses. In other words, both must be Compute Engine machine types or both must be legacy machine types. If you use `cloud_tpu` for this value, see special instructions for [configuring a custom TPU machine](/ml-engine/docs/tensorflow/using-tpus#configuring_a_custom_tpu_machine). This value must be present when `scaleTier` is set to `CUSTOM` and `workerCount` is greater than zero. */
		workerType: Option[String] = None,
	  /** Optional. The configuration for workers. You should only set `workerConfig.acceleratorConfig` if `workerType` is set to a Compute Engine machine type. [Learn about restrictions on accelerator configurations for training.](/ai-platform/training/docs/using-gpus#compute-engine-machine-types-with-gpu) Set `workerConfig.imageUri` only if you build a custom image for your worker. If `workerConfig.imageUri` has not been set, AI Platform uses the value of `masterConfig.imageUri`. Learn more about [configuring custom containers](/ai-platform/training/docs/distributed-training-containers). */
		workerConfig: Option[Schema.GoogleCloudMlV1__ReplicaConfig] = None,
	  /** Optional. Specifies the type of virtual machine to use for your training job's parameter server. The supported values are the same as those described in the entry for `master_type`. This value must be consistent with the category of machine type that `masterType` uses. In other words, both must be Compute Engine machine types or both must be legacy machine types. This value must be present when `scaleTier` is set to `CUSTOM` and `parameter_server_count` is greater than zero. */
		parameterServerType: Option[String] = None,
	  /** Optional. The configuration for parameter servers. You should only set `parameterServerConfig.acceleratorConfig` if `parameterServerType` is set to a Compute Engine machine type. [Learn about restrictions on accelerator configurations for training.](/ai-platform/training/docs/using-gpus#compute-engine-machine-types-with-gpu) Set `parameterServerConfig.imageUri` only if you build a custom image for your parameter server. If `parameterServerConfig.imageUri` has not been set, AI Platform uses the value of `masterConfig.imageUri`. Learn more about [configuring custom containers](/ai-platform/training/docs/distributed-training-containers). */
		parameterServerConfig: Option[Schema.GoogleCloudMlV1__ReplicaConfig] = None,
	  /** Optional. Specifies the type of virtual machine to use for your training job's evaluator nodes. The supported values are the same as those described in the entry for `masterType`. This value must be consistent with the category of machine type that `masterType` uses. In other words, both must be Compute Engine machine types or both must be legacy machine types. This value must be present when `scaleTier` is set to `CUSTOM` and `evaluatorCount` is greater than zero. */
		evaluatorType: Option[String] = None,
	  /** Optional. The configuration for evaluators. You should only set `evaluatorConfig.acceleratorConfig` if `evaluatorType` is set to a Compute Engine machine type. [Learn about restrictions on accelerator configurations for training.](/ai-platform/training/docs/using-gpus#compute-engine-machine-types-with-gpu) Set `evaluatorConfig.imageUri` only if you build a custom image for your evaluator. If `evaluatorConfig.imageUri` has not been set, AI Platform uses the value of `masterConfig.imageUri`. Learn more about [configuring custom containers](/ai-platform/training/docs/distributed-training-containers). */
		evaluatorConfig: Option[Schema.GoogleCloudMlV1__ReplicaConfig] = None,
	  /** Optional. The number of worker replicas to use for the training job. Each replica in the cluster will be of the type specified in `worker_type`. This value can only be used when `scale_tier` is set to `CUSTOM`. If you set this value, you must also set `worker_type`. The default value is zero. */
		workerCount: Option[String] = None,
	  /** Optional. The number of parameter server replicas to use for the training job. Each replica in the cluster will be of the type specified in `parameter_server_type`. This value can only be used when `scale_tier` is set to `CUSTOM`. If you set this value, you must also set `parameter_server_type`. The default value is zero. */
		parameterServerCount: Option[String] = None,
	  /** Optional. The number of evaluator replicas to use for the training job. Each replica in the cluster will be of the type specified in `evaluator_type`. This value can only be used when `scale_tier` is set to `CUSTOM`. If you set this value, you must also set `evaluator_type`. The default value is zero. */
		evaluatorCount: Option[String] = None,
	  /** Required. The Google Cloud Storage location of the packages with the training program and any additional dependencies. The maximum number of package URIs is 100. */
		packageUris: Option[List[String]] = None,
	  /** Required. The Python module name to run after installing the packages. */
		pythonModule: Option[String] = None,
	  /** Optional. Command-line arguments passed to the training application when it starts. If your job uses a custom container, then the arguments are passed to the container's `ENTRYPOINT` command. */
		args: Option[List[String]] = None,
	  /** Optional. The set of Hyperparameters to tune. */
		hyperparameters: Option[Schema.GoogleCloudMlV1__HyperparameterSpec] = None,
	  /** Required. The region to run the training job in. See the [available regions](/ai-platform/training/docs/regions) for AI Platform Training. */
		region: Option[String] = None,
	  /** Optional. A Google Cloud Storage path in which to store training outputs and other data needed for training. This path is passed to your TensorFlow program as the '--job-dir' command-line argument. The benefit of specifying this field is that Cloud ML validates the path for use in training. */
		jobDir: Option[String] = None,
	  /** Optional. The AI Platform runtime version to use for training. You must either specify this field or specify `masterConfig.imageUri`. For more information, see the [runtime version list](/ai-platform/training/docs/runtime-version-list) and learn [how to manage runtime versions](/ai-platform/training/docs/versioning). */
		runtimeVersion: Option[String] = None,
	  /** Optional. The version of Python used in training. You must either specify this field or specify `masterConfig.imageUri`. The following Python versions are available: &#42; Python '3.7' is available when `runtime_version` is set to '1.15' or later. &#42; Python '3.5' is available when `runtime_version` is set to a version from '1.4' to '1.14'. &#42; Python '2.7' is available when `runtime_version` is set to '1.15' or earlier. Read more about the Python versions available for [each runtime version](/ml-engine/docs/runtime-version-list). */
		pythonVersion: Option[String] = None,
	  /** Optional. Options for using customer-managed encryption keys (CMEK) to protect resources created by a training job, instead of using Google's default encryption. If this is set, then all resources created by the training job will be encrypted with the customer-managed encryption key that you specify. [Learn how and when to use CMEK with AI Platform Training](/ai-platform/training/docs/cmek). */
		encryptionConfig: Option[Schema.GoogleCloudMlV1__EncryptionConfig] = None,
	  /** Optional. Scheduling options for a training job. */
		scheduling: Option[Schema.GoogleCloudMlV1__Scheduling] = None,
	  /** Optional. The full name of the [Compute Engine network](/vpc/docs/vpc) to which the Job is peered. For example, `projects/12345/global/networks/myVPC`. The format of this field is `projects/{project}/global/networks/{network}`, where {project} is a project number (like `12345`) and {network} is network name. Private services access must already be configured for the network. If left unspecified, the Job is not peered with any network. [Learn about using VPC Network Peering.](/ai-platform/training/docs/vpc-peering). */
		network: Option[String] = None,
	  /** Optional. The email address of a service account to use when running the training appplication. You must have the `iam.serviceAccounts.actAs` permission for the specified service account. In addition, the AI Platform Training Google-managed service account must have the `roles/iam.serviceAccountAdmin` role for the specified service account. [Learn more about configuring a service account.](/ai-platform/training/docs/custom-service-account) If not specified, the AI Platform Training Google-managed service account is used by default. */
		serviceAccount: Option[String] = None,
	  /** Optional. Use `chief` instead of `master` in the `TF_CONFIG` environment variable when training with a custom container. Defaults to `false`. [Learn more about this field.](/ai-platform/training/docs/distributed-training-details#chief-versus-master) This field has no effect for training jobs that don't use a custom container. */
		useChiefInTfConfig: Option[Boolean] = None,
	  /** Optional. Whether you want AI Platform Training to enable [interactive shell access](https://cloud.google.com/ai-platform/training/docs/monitor-debug-interactive-shell) to training containers. If set to `true`, you can access interactive shells at the URIs given by TrainingOutput.web_access_uris or HyperparameterOutput.web_access_uris (within TrainingOutput.trials). */
		enableWebAccess: Option[Boolean] = None
	)
	
	case class GoogleCloudMlV1__ReplicaConfig(
	  /** Represents the type and number of accelerators used by the replica. [Learn about restrictions on accelerator configurations for training.](/ai-platform/training/docs/using-gpus#compute-engine-machine-types-with-gpu) */
		acceleratorConfig: Option[Schema.GoogleCloudMlV1__AcceleratorConfig] = None,
	  /** The Docker image to run on the replica. This image must be in Container Registry. Learn more about [configuring custom containers](/ai-platform/training/docs/distributed-training-containers). */
		imageUri: Option[String] = None,
	  /** The AI Platform runtime version that includes a TensorFlow version matching the one used in the custom container. This field is required if the replica is a TPU worker that uses a custom container. Otherwise, do not specify this field. This must be a [runtime version that currently supports training with TPUs](/ml-engine/docs/tensorflow/runtime-version-list#tpu-support). Note that the version of TensorFlow included in a runtime version may differ from the numbering of the runtime version itself, because it may have a different [patch version](https://www.tensorflow.org/guide/version_compat#semantic_versioning_20). In this field, you must specify the runtime version (TensorFlow minor version). For example, if your custom container runs TensorFlow `1.x.y`, specify `1.x`. */
		tpuTfVersion: Option[String] = None,
	  /** Represents the configuration of disk options. */
		diskConfig: Option[Schema.GoogleCloudMlV1__DiskConfig] = None,
	  /** The command with which the replica's custom container is run. If provided, it will override default ENTRYPOINT of the docker image. If not provided, the docker image's ENTRYPOINT is used. It cannot be set if custom container image is not provided. Note that this field and [TrainingInput.args] are mutually exclusive, i.e., both cannot be set at the same time. */
		containerCommand: Option[List[String]] = None,
	  /** Arguments to the entrypoint command. The following rules apply for container_command and container_args: - If you do not supply command or args: The defaults defined in the Docker image are used. - If you supply a command but no args: The default EntryPoint and the default Cmd defined in the Docker image are ignored. Your command is run without any arguments. - If you supply only args: The default Entrypoint defined in the Docker image is run with the args that you supplied. - If you supply a command and args: The default Entrypoint and the default Cmd defined in the Docker image are ignored. Your command is run with your args. It cannot be set if custom container image is not provided. Note that this field and [TrainingInput.args] are mutually exclusive, i.e., both cannot be set at the same time. */
		containerArgs: Option[List[String]] = None
	)
	
	object GoogleCloudMlV1__AcceleratorConfig {
		enum TypeEnum extends Enum[TypeEnum] { case ACCELERATOR_TYPE_UNSPECIFIED, NVIDIA_TESLA_K80, NVIDIA_TESLA_P100, NVIDIA_TESLA_V100, NVIDIA_TESLA_P4, NVIDIA_TESLA_T4, NVIDIA_TESLA_A100, TPU_V2, TPU_V3, TPU_V2_POD, TPU_V3_POD, TPU_V4_POD }
	}
	case class GoogleCloudMlV1__AcceleratorConfig(
	  /** The number of accelerators to attach to each machine running the job. */
		count: Option[String] = None,
	  /** The type of accelerator to use. */
		`type`: Option[Schema.GoogleCloudMlV1__AcceleratorConfig.TypeEnum] = None
	)
	
	case class GoogleCloudMlV1__DiskConfig(
	  /** Type of the boot disk (default is "pd-ssd"). Valid values: "pd-ssd" (Persistent Disk Solid State Drive) or "pd-standard" (Persistent Disk Hard Disk Drive). */
		bootDiskType: Option[String] = None,
	  /** Size in GB of the boot disk (default is 100GB). */
		bootDiskSizeGb: Option[Int] = None
	)
	
	object GoogleCloudMlV1__HyperparameterSpec {
		enum GoalEnum extends Enum[GoalEnum] { case GOAL_TYPE_UNSPECIFIED, MAXIMIZE, MINIMIZE }
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case ALGORITHM_UNSPECIFIED, GRID_SEARCH, RANDOM_SEARCH }
	}
	case class GoogleCloudMlV1__HyperparameterSpec(
	  /** Required. The type of goal to use for tuning. Available types are `MAXIMIZE` and `MINIMIZE`. Defaults to `MAXIMIZE`. */
		goal: Option[Schema.GoogleCloudMlV1__HyperparameterSpec.GoalEnum] = None,
	  /** Required. The set of parameters to tune. */
		params: Option[List[Schema.GoogleCloudMlV1__ParameterSpec]] = None,
	  /** Optional. How many training trials should be attempted to optimize the specified hyperparameters. Defaults to one. */
		maxTrials: Option[Int] = None,
	  /** Optional. The number of training trials to run concurrently. You can reduce the time it takes to perform hyperparameter tuning by adding trials in parallel. However, each trail only benefits from the information gained in completed trials. That means that a trial does not get access to the results of trials running at the same time, which could reduce the quality of the overall optimization. Each trial will use the same scale tier and machine types. Defaults to one. */
		maxParallelTrials: Option[Int] = None,
	  /** Optional. The number of failed trials that need to be seen before failing the hyperparameter tuning job. You can specify this field to override the default failing criteria for AI Platform hyperparameter tuning jobs. Defaults to zero, which means the service decides when a hyperparameter job should fail. */
		maxFailedTrials: Option[Int] = None,
	  /** Optional. The TensorFlow summary tag name to use for optimizing trials. For current versions of TensorFlow, this tag name should exactly match what is shown in TensorBoard, including all scopes. For versions of TensorFlow prior to 0.12, this should be only the tag passed to tf.Summary. By default, "training/hptuning/metric" will be used. */
		hyperparameterMetricTag: Option[String] = None,
	  /** Optional. The prior hyperparameter tuning job id that users hope to continue with. The job id will be used to find the corresponding vizier study guid and resume the study. */
		resumePreviousJobId: Option[String] = None,
	  /** Optional. Indicates if the hyperparameter tuning job enables auto trial early stopping. */
		enableTrialEarlyStopping: Option[Boolean] = None,
	  /** Optional. The search algorithm specified for the hyperparameter tuning job. Uses the default AI Platform hyperparameter tuning algorithm if unspecified. */
		algorithm: Option[Schema.GoogleCloudMlV1__HyperparameterSpec.AlgorithmEnum] = None
	)
	
	object GoogleCloudMlV1__ParameterSpec {
		enum TypeEnum extends Enum[TypeEnum] { case PARAMETER_TYPE_UNSPECIFIED, DOUBLE, INTEGER, CATEGORICAL, DISCRETE }
		enum ScaleTypeEnum extends Enum[ScaleTypeEnum] { case NONE, UNIT_LINEAR_SCALE, UNIT_LOG_SCALE, UNIT_REVERSE_LOG_SCALE }
	}
	case class GoogleCloudMlV1__ParameterSpec(
	  /** Required. The parameter name must be unique amongst all ParameterConfigs in a HyperparameterSpec message. E.g., "learning_rate". */
		parameterName: Option[String] = None,
	  /** Required. The type of the parameter. */
		`type`: Option[Schema.GoogleCloudMlV1__ParameterSpec.TypeEnum] = None,
	  /** Required if type is `DOUBLE` or `INTEGER`. This field should be unset if type is `CATEGORICAL`. This value should be integers if type is INTEGER. */
		minValue: Option[BigDecimal] = None,
	  /** Required if type is `DOUBLE` or `INTEGER`. This field should be unset if type is `CATEGORICAL`. This value should be integers if type is `INTEGER`. */
		maxValue: Option[BigDecimal] = None,
	  /** Required if type is `CATEGORICAL`. The list of possible categories. */
		categoricalValues: Option[List[String]] = None,
	  /** Required if type is `DISCRETE`. A list of feasible points. The list should be in strictly increasing order. For instance, this parameter might have possible settings of 1.5, 2.5, and 4.0. This list should not contain more than 1,000 values. */
		discreteValues: Option[List[BigDecimal]] = None,
	  /** Optional. How the parameter should be scaled to the hypercube. Leave unset for categorical parameters. Some kind of scaling is strongly recommended for real or integral parameters (e.g., `UNIT_LINEAR_SCALE`). */
		scaleType: Option[Schema.GoogleCloudMlV1__ParameterSpec.ScaleTypeEnum] = None
	)
	
	case class GoogleCloudMlV1__EncryptionConfig(
	  /** The Cloud KMS resource identifier of the customer-managed encryption key used to protect a resource, such as a training job. It has the following format: `projects/{PROJECT_ID}/locations/{REGION}/keyRings/{KEY_RING_NAME}/cryptoKeys/{KEY_NAME}` */
		kmsKeyName: Option[String] = None
	)
	
	case class GoogleCloudMlV1__Scheduling(
	  /** Optional. The maximum job running time, expressed in seconds. The field can contain up to nine fractional digits, terminated by `s`. If not specified, this field defaults to `604800s` (seven days). If the training job is still running after this duration, AI Platform Training cancels it. The duration is measured from when the job enters the `RUNNING` state; therefore it does not overlap with the duration limited by Scheduling.max_wait_time. For example, if you want to ensure your job runs for no more than 2 hours, set this field to `7200s` (2 hours &#42; 60 minutes / hour &#42; 60 seconds / minute). If you submit your training job using the `gcloud` tool, you can [specify this field in a `config.yaml` file](/ai-platform/training/docs/training-jobs#formatting_your_configuration_parameters). For example: ```yaml trainingInput: scheduling: maxRunningTime: 7200s ``` */
		maxRunningTime: Option[String] = None,
	  /** Optional. The maximum job wait time, expressed in seconds. The field can contain up to nine fractional digits, terminated by `s`. If not specified, there is no limit to the wait time. The minimum for this field is `1800s` (30 minutes). If the training job has not entered the `RUNNING` state after this duration, AI Platform Training cancels it. After the job begins running, it can no longer be cancelled due to the maximum wait time. Therefore the duration limited by this field does not overlap with the duration limited by Scheduling.max_running_time. For example, if the job temporarily stops running and retries due to a [VM restart](/ai-platform/training/docs/overview#restarts), this cannot lead to a maximum wait time cancellation. However, independently of this constraint, AI Platform Training might stop a job if there are too many retries due to exhausted resources in a region. The following example describes how you might use this field: To cancel your job if it doesn't start running within 1 hour, set this field to `3600s` (1 hour &#42; 60 minutes / hour &#42; 60 seconds / minute). If the job is still in the `QUEUED` or `PREPARING` state after an hour of waiting, AI Platform Training cancels the job. If you submit your training job using the `gcloud` tool, you can [specify this field in a `config.yaml` file](/ai-platform/training/docs/training-jobs#formatting_your_configuration_parameters). For example: ```yaml trainingInput: scheduling: maxWaitTime: 3600s ``` */
		maxWaitTime: Option[String] = None,
	  /** Optional. Job scheduling will be based on this priority, which in the range [0, 1000]. The bigger the number, the higher the priority. Default to 0 if not set. If there are multiple jobs requesting same type of accelerators, the high priority job will be scheduled prior to ones with low priority. */
		priority: Option[Int] = None
	)
	
	object GoogleCloudMlV1__PredictionInput {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, JSON, TEXT, TF_RECORD, TF_RECORD_GZIP, CSV }
		enum OutputDataFormatEnum extends Enum[OutputDataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, JSON, TEXT, TF_RECORD, TF_RECORD_GZIP, CSV }
	}
	case class GoogleCloudMlV1__PredictionInput(
	  /** Use this field if you want to use the default version for the specified model. The string must use the following format: `"projects/YOUR_PROJECT/models/YOUR_MODEL"` */
		modelName: Option[String] = None,
	  /** Use this field if you want to specify a version of the model to use. The string is formatted the same way as `model_version`, with the addition of the version information: `"projects/YOUR_PROJECT/models/YOUR_MODEL/versions/YOUR_VERSION"` */
		versionName: Option[String] = None,
	  /** Use this field if you want to specify a Google Cloud Storage path for the model to use. */
		uri: Option[String] = None,
	  /** Required. The format of the input data files. */
		dataFormat: Option[Schema.GoogleCloudMlV1__PredictionInput.DataFormatEnum] = None,
	  /** Optional. Format of the output data files, defaults to JSON. */
		outputDataFormat: Option[Schema.GoogleCloudMlV1__PredictionInput.OutputDataFormatEnum] = None,
	  /** Required. The Cloud Storage location of the input data files. May contain wildcards. */
		inputPaths: Option[List[String]] = None,
	  /** Required. The output Google Cloud Storage location. */
		outputPath: Option[String] = None,
	  /** Optional. The maximum number of workers to be used for parallel processing. Defaults to 10 if not specified. */
		maxWorkerCount: Option[String] = None,
	  /** Required. The Google Compute Engine region to run the prediction job in. See the available regions for AI Platform services. */
		region: Option[String] = None,
	  /** Optional. The AI Platform runtime version to use for this batch prediction. If not set, AI Platform will pick the runtime version used during the CreateVersion request for this model version, or choose the latest stable version when model version information is not available such as when the model is specified by uri. */
		runtimeVersion: Option[String] = None,
	  /** Optional. Number of records per batch, defaults to 64. The service will buffer batch_size number of records in memory before invoking one Tensorflow prediction call internally. So take the record size and memory available into consideration when setting this parameter. */
		batchSize: Option[String] = None,
	  /** Optional. The name of the signature defined in the SavedModel to use for this job. Please refer to [SavedModel](https://tensorflow.github.io/serving/serving_basic.html) for information about how to use signatures. Defaults to [DEFAULT_SERVING_SIGNATURE_DEF_KEY](https://www.tensorflow.org/api_docs/python/tf/saved_model/signature_constants) , which is "serving_default". */
		signatureName: Option[String] = None
	)
	
	case class GoogleCloudMlV1__TrainingOutput(
	  /** The number of hyperparameter tuning trials that completed successfully. Only set for hyperparameter tuning jobs. */
		completedTrialCount: Option[String] = None,
	  /** Results for individual Hyperparameter trials. Only set for hyperparameter tuning jobs. */
		trials: Option[List[Schema.GoogleCloudMlV1__HyperparameterOutput]] = None,
	  /** The amount of ML units consumed by the job. */
		consumedMLUnits: Option[BigDecimal] = None,
	  /** Whether this job is a hyperparameter tuning job. */
		isHyperparameterTuningJob: Option[Boolean] = None,
	  /** Whether this job is a built-in Algorithm job. */
		isBuiltInAlgorithmJob: Option[Boolean] = None,
	  /** Details related to built-in algorithms jobs. Only set for built-in algorithms jobs. */
		builtInAlgorithmOutput: Option[Schema.GoogleCloudMlV1__BuiltInAlgorithmOutput] = None,
	  /** The TensorFlow summary tag name used for optimizing hyperparameter tuning trials. See [`HyperparameterSpec.hyperparameterMetricTag`](#HyperparameterSpec.FIELDS.hyperparameter_metric_tag) for more information. Only set for hyperparameter tuning jobs. */
		hyperparameterMetricTag: Option[String] = None,
	  /** Output only. URIs for accessing [interactive shells](https://cloud.google.com/ai-platform/training/docs/monitor-debug-interactive-shell) (one URI for each training node). Only available if training_input.enable_web_access is `true`. The keys are names of each node in the training job; for example, `master-replica-0` for the master node, `worker-replica-0` for the first worker, and `ps-replica-0` for the first parameter server. The values are the URIs for each node's interactive shell. */
		webAccessUris: Option[Map[String, String]] = None
	)
	
	object GoogleCloudMlV1__HyperparameterOutput {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, QUEUED, PREPARING, RUNNING, SUCCEEDED, FAILED, CANCELLING, CANCELLED }
	}
	case class GoogleCloudMlV1__HyperparameterOutput(
	  /** The trial id for these results. */
		trialId: Option[String] = None,
	  /** The hyperparameters given to this trial. */
		hyperparameters: Option[Map[String, String]] = None,
	  /** Output only. Start time for the trial. */
		startTime: Option[String] = None,
	  /** Output only. End time for the trial. */
		endTime: Option[String] = None,
	  /** Output only. The detailed state of the trial. */
		state: Option[Schema.GoogleCloudMlV1__HyperparameterOutput.StateEnum] = None,
	  /** The final objective metric seen for this trial. */
		finalMetric: Option[Schema.GoogleCloudMlV1_HyperparameterOutput_HyperparameterMetric] = None,
	  /** True if the trial is stopped early. */
		isTrialStoppedEarly: Option[Boolean] = None,
	  /** All recorded object metrics for this trial. This field is not currently populated. */
		allMetrics: Option[List[Schema.GoogleCloudMlV1_HyperparameterOutput_HyperparameterMetric]] = None,
	  /** Details related to built-in algorithms jobs. Only set for trials of built-in algorithms jobs that have succeeded. */
		builtInAlgorithmOutput: Option[Schema.GoogleCloudMlV1__BuiltInAlgorithmOutput] = None,
	  /** URIs for accessing [interactive shells](https://cloud.google.com/ai-platform/training/docs/monitor-debug-interactive-shell) (one URI for each training node). Only available if this trial is part of a hyperparameter tuning job and the job's training_input.enable_web_access is `true`. The keys are names of each node in the training job; for example, `master-replica-0` for the master node, `worker-replica-0` for the first worker, and `ps-replica-0` for the first parameter server. The values are the URIs for each node's interactive shell. */
		webAccessUris: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudMlV1_HyperparameterOutput_HyperparameterMetric(
	  /** The global training step for this metric. */
		trainingStep: Option[String] = None,
	  /** The objective value at this training step. */
		objectiveValue: Option[BigDecimal] = None
	)
	
	case class GoogleCloudMlV1__BuiltInAlgorithmOutput(
	  /** Framework on which the built-in algorithm was trained. */
		framework: Option[String] = None,
	  /** AI Platform runtime version on which the built-in algorithm was trained. */
		runtimeVersion: Option[String] = None,
	  /** Python version on which the built-in algorithm was trained. */
		pythonVersion: Option[String] = None,
	  /** The Cloud Storage path to the `model/` directory where the training job saves the trained model. Only set for successful jobs that don't use hyperparameter tuning. */
		modelPath: Option[String] = None
	)
	
	case class GoogleCloudMlV1__PredictionOutput(
	  /** The output Google Cloud Storage location provided at the job creation time. */
		outputPath: Option[String] = None,
	  /** The number of generated predictions. */
		predictionCount: Option[String] = None,
	  /** The number of data instances which resulted in errors. */
		errorCount: Option[String] = None,
	  /** Node hours used by the batch prediction job. */
		nodeHours: Option[BigDecimal] = None
	)
	
	case class GoogleCloudMlV1__ListJobsResponse(
	  /** The list of jobs. */
		jobs: Option[List[Schema.GoogleCloudMlV1__Job]] = None,
	  /** Optional. Pass this token as the `page_token` field of the request for a subsequent call. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudMlV1__CancelJobRequest(
	
	)
	
	case class GoogleProtobuf__Empty(
	
	)
	
	case class GoogleCloudMlV1__Location(
		name: Option[String] = None,
	  /** Capabilities available in the location. */
		capabilities: Option[List[Schema.GoogleCloudMlV1__Capability]] = None
	)
	
	object GoogleCloudMlV1__Capability {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TRAINING, BATCH_PREDICTION, ONLINE_PREDICTION }
		enum AvailableAcceleratorsEnum extends Enum[AvailableAcceleratorsEnum] { case ACCELERATOR_TYPE_UNSPECIFIED, NVIDIA_TESLA_K80, NVIDIA_TESLA_P100, NVIDIA_TESLA_V100, NVIDIA_TESLA_P4, NVIDIA_TESLA_T4, NVIDIA_TESLA_A100, TPU_V2, TPU_V3, TPU_V2_POD, TPU_V3_POD, TPU_V4_POD }
	}
	case class GoogleCloudMlV1__Capability(
		`type`: Option[Schema.GoogleCloudMlV1__Capability.TypeEnum] = None,
	  /** Available accelerators for the capability. */
		availableAccelerators: Option[List[Schema.GoogleCloudMlV1__Capability.AvailableAcceleratorsEnum]] = None
	)
	
	case class GoogleCloudMlV1__ListLocationsResponse(
	  /** Locations where at least one type of CMLE capability is available. */
		locations: Option[List[Schema.GoogleCloudMlV1__Location]] = None,
	  /** Optional. Pass this token as the `page_token` field of the request for a subsequent call. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunning__ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunning__Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunning__Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpc__Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpc__Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudMlV1__Model(
	  /** Required. The name specified for the model when it was created. The model name must be unique within the project it is created in. */
		name: Option[String] = None,
	  /** Optional. The description specified for the model when it was created. */
		description: Option[String] = None,
	  /** Output only. The default version of the model. This version will be used to handle prediction requests that do not specify a version. You can change the default version by calling projects.models.versions.setDefault. */
		defaultVersion: Option[Schema.GoogleCloudMlV1__Version] = None,
	  /** Optional. The list of regions where the model is going to be deployed. Only one region per model is supported. Defaults to 'us-central1' if nothing is set. See the available regions for AI Platform services. Note: &#42; No matter where a model is deployed, it can always be accessed by users from anywhere, both for online and batch prediction. &#42; The region for a batch prediction job is set by the region field when submitting the batch prediction job and does not take its value from this field. */
		regions: Option[List[String]] = None,
	  /** Optional. If true, online prediction access logs are sent to Cloud Logging. These logs are like standard server access logs, containing information like timestamp and latency for each request. Note that [logs may incur a cost](/stackdriver/pricing), especially if your project receives prediction requests at a high queries per second rate (QPS). Estimate your costs before enabling this option. Default is false. */
		onlinePredictionLogging: Option[Boolean] = None,
	  /** Optional. If true, online prediction nodes send `stderr` and `stdout` streams to Cloud Logging. These can be more verbose than the standard access logs (see `onlinePredictionLogging`) and can incur higher cost. However, they are helpful for debugging. Note that [logs may incur a cost](/stackdriver/pricing), especially if your project receives prediction requests at a high QPS. Estimate your costs before enabling this option. Default is false. */
		onlinePredictionConsoleLogging: Option[Boolean] = None,
	  /** Optional. One or more labels that you can add, to organize your models. Each label is a key-value pair, where both the key and the value are arbitrary strings that you supply. For more information, see the documentation on using labels. Note that this field is not updatable for mls1&#42; models. */
		labels: Option[Map[String, String]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a model from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform model updates in order to avoid race conditions: An `etag` is returned in the response to `GetModel`, and systems are expected to put that etag in the request to `UpdateModel` to ensure that their change will be applied to the model as intended. */
		etag: Option[String] = None
	)
	
	object GoogleCloudMlV1__Version {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, READY, CREATING, FAILED, DELETING, UPDATING }
		enum FrameworkEnum extends Enum[FrameworkEnum] { case FRAMEWORK_UNSPECIFIED, TENSORFLOW, SCIKIT_LEARN, XGBOOST }
	}
	case class GoogleCloudMlV1__Version(
	  /** Required. The name specified for the version when it was created. The version name must be unique within the model it is created in. */
		name: Option[String] = None,
	  /** Optional. The description specified for the version when it was created. */
		description: Option[String] = None,
	  /** Output only. If true, this version will be used to handle prediction requests that do not specify a version. You can change the default version by calling projects.methods.versions.setDefault. */
		isDefault: Option[Boolean] = None,
	  /** The Cloud Storage URI of a directory containing trained model artifacts to be used to create the model version. See the [guide to deploying models](/ai-platform/prediction/docs/deploying-models) for more information. The total number of files under this directory must not exceed 1000. During projects.models.versions.create, AI Platform Prediction copies all files from the specified directory to a location managed by the service. From then on, AI Platform Prediction uses these copies of the model artifacts to serve predictions, not the original files in Cloud Storage, so this location is useful only as a historical record. If you specify container, then this field is optional. Otherwise, it is required. Learn [how to use this field with a custom container](/ai-platform/prediction/docs/custom-container-requirements#artifacts). */
		deploymentUri: Option[String] = None,
	  /** Output only. The time the version was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the version was last used for prediction. */
		lastUseTime: Option[String] = None,
	  /** Required. The AI Platform runtime version to use for this deployment. For more information, see the [runtime version list](/ml-engine/docs/runtime-version-list) and [how to manage runtime versions](/ml-engine/docs/versioning). */
		runtimeVersion: Option[String] = None,
	  /** Optional. The type of machine on which to serve the model. Currently only applies to online prediction service. To learn about valid values for this field, read [Choosing a machine type for online prediction](/ai-platform/prediction/docs/machine-types-online-prediction). If this field is not specified and you are using a [regional endpoint](/ai-platform/prediction/docs/regional-endpoints), then the machine type defaults to `n1-standard-2`. If this field is not specified and you are using the global endpoint (`ml.googleapis.com`), then the machine type defaults to `mls1-c1-m2`. */
		machineType: Option[String] = None,
	  /** Automatically scale the number of nodes used to serve the model in response to increases and decreases in traffic. Care should be taken to ramp up traffic according to the model's ability to scale or you will start seeing increases in latency and 429 response codes. */
		autoScaling: Option[Schema.GoogleCloudMlV1__AutoScaling] = None,
	  /** Manually select the number of nodes to use for serving the model. You should generally use `auto_scaling` with an appropriate `min_nodes` instead, but this option is available if you want more predictable billing. Beware that latency and error rates will increase if the traffic exceeds that capability of the system to serve it based on the selected number of nodes. */
		manualScaling: Option[Schema.GoogleCloudMlV1__ManualScaling] = None,
	  /** Output only. The state of a version. */
		state: Option[Schema.GoogleCloudMlV1__Version.StateEnum] = None,
	  /** Output only. The details of a failure or a cancellation. */
		errorMessage: Option[String] = None,
	  /** Optional. The fully qualified name (module_name.class_name) of a class that implements the Predictor interface described in this reference field. The module containing this class should be included in a package provided to the [`packageUris` field](#Version.FIELDS.package_uris). Specify this field if and only if you are deploying a [custom prediction routine (beta)](/ml-engine/docs/tensorflow/custom-prediction-routines). If you specify this field, you must set [`runtimeVersion`](#Version.FIELDS.runtime_version) to 1.4 or greater and you must set `machineType` to a [legacy (MLS1) machine type](/ml-engine/docs/machine-types-online-prediction). The following code sample provides the Predictor interface: class Predictor(object): """Interface for constructing custom predictors.""" def predict(self, instances, &#42;&#42;kwargs): """Performs custom prediction. Instances are the decoded values from the request. They have already been deserialized from JSON. Args: instances: A list of prediction input instances. &#42;&#42;kwargs: A dictionary of keyword args provided as additional fields on the predict request body. Returns: A list of outputs containing the prediction results. This list must be JSON serializable. """ raise NotImplementedError() @classmethod def from_path(cls, model_dir): """Creates an instance of Predictor using the given path. Loading of the predictor should be done in this method. Args: model_dir: The local directory that contains the exported model file along with any additional files uploaded when creating the version resource. Returns: An instance implementing this Predictor class. """ raise NotImplementedError() Learn more about [the Predictor interface and custom prediction routines](/ml-engine/docs/tensorflow/custom-prediction-routines). */
		predictionClass: Option[String] = None,
	  /** Optional. Cloud Storage paths (`gs://…`) of packages for [custom prediction routines](/ml-engine/docs/tensorflow/custom-prediction-routines) or [scikit-learn pipelines with custom code](/ml-engine/docs/scikit/exporting-for-prediction#custom-pipeline-code). For a custom prediction routine, one of these packages must contain your Predictor class (see [`predictionClass`](#Version.FIELDS.prediction_class)). Additionally, include any dependencies used by your Predictor or scikit-learn pipeline uses that are not already included in your selected [runtime version](/ml-engine/docs/tensorflow/runtime-version-list). If you specify this field, you must also set [`runtimeVersion`](#Version.FIELDS.runtime_version) to 1.4 or greater. */
		packageUris: Option[List[String]] = None,
	  /** Optional. One or more labels that you can add, to organize your model versions. Each label is a key-value pair, where both the key and the value are arbitrary strings that you supply. For more information, see the documentation on using labels. Note that this field is not updatable for mls1&#42; models. */
		labels: Option[Map[String, String]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a model from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform model updates in order to avoid race conditions: An `etag` is returned in the response to `GetVersion`, and systems are expected to put that etag in the request to `UpdateVersion` to ensure that their change will be applied to the model as intended. */
		etag: Option[String] = None,
	  /** Optional. The machine learning framework AI Platform uses to train this version of the model. Valid values are `TENSORFLOW`, `SCIKIT_LEARN`, `XGBOOST`. If you do not specify a framework, AI Platform will analyze files in the deployment_uri to determine a framework. If you choose `SCIKIT_LEARN` or `XGBOOST`, you must also set the runtime version of the model to 1.4 or greater. Do &#42;&#42;not&#42;&#42; specify a framework if you're deploying a [custom prediction routine](/ai-platform/prediction/docs/custom-prediction-routines) or if you're using a [custom container](/ai-platform/prediction/docs/use-custom-container). */
		framework: Option[Schema.GoogleCloudMlV1__Version.FrameworkEnum] = None,
	  /** Required. The version of Python used in prediction. The following Python versions are available: &#42; Python '3.7' is available when `runtime_version` is set to '1.15' or later. &#42; Python '3.5' is available when `runtime_version` is set to a version from '1.4' to '1.14'. &#42; Python '2.7' is available when `runtime_version` is set to '1.15' or earlier. Read more about the Python versions available for [each runtime version](/ml-engine/docs/runtime-version-list). */
		pythonVersion: Option[String] = None,
	  /** Optional. Accelerator config for using GPUs for online prediction (beta). Only specify this field if you have specified a Compute Engine (N1) machine type in the `machineType` field. Learn more about [using GPUs for online prediction](/ml-engine/docs/machine-types-online-prediction#gpus). */
		acceleratorConfig: Option[Schema.GoogleCloudMlV1__AcceleratorConfig] = None,
	  /** Optional. Specifies the service account for resource access control. If you specify this field, then you must also specify either the `containerSpec` or the `predictionClass` field. Learn more about [using a custom service account](/ai-platform/prediction/docs/custom-service-account). */
		serviceAccount: Option[String] = None,
	  /** Optional. &#42;Only&#42; specify this field in a projects.models.versions.patch request. Specifying it in a projects.models.versions.create request has no effect. Configures the request-response pair logging on predictions from this Version. */
		requestLoggingConfig: Option[Schema.GoogleCloudMlV1__RequestLoggingConfig] = None,
	  /** Optional. Configures explainability features on the model's version. Some explanation features require additional metadata to be loaded as part of the model payload. */
		explanationConfig: Option[Schema.GoogleCloudMlV1__ExplanationConfig] = None,
	  /** Optional. Specifies a custom container to use for serving predictions. If you specify this field, then `machineType` is required. If you specify this field, then `deploymentUri` is optional. If you specify this field, then you must not specify `runtimeVersion`, `packageUris`, `framework`, `pythonVersion`, or `predictionClass`. */
		container: Option[Schema.GoogleCloudMlV1__ContainerSpec] = None,
	  /** Optional. Specifies paths on a custom container's HTTP server where AI Platform Prediction sends certain requests. If you specify this field, then you must also specify the `container` field. If you specify the `container` field and do not specify this field, it defaults to the following: ```json { "predict": "/v1/models/MODEL/versions/VERSION:predict", "health": "/v1/models/MODEL/versions/VERSION" } ``` See RouteMap for more details about these default values. */
		routes: Option[Schema.GoogleCloudMlV1__RouteMap] = None,
	  /** Output only. The last time this version was successfully [migrated to AI Platform (Unified)](https://cloud.google.com/ai-platform-unified/docs/start/migrating-to-ai-platform-unified). */
		lastMigrationTime: Option[String] = None,
	  /** Output only. The [AI Platform (Unified) `Model`](https://cloud.google.com/ai-platform-unified/docs/reference/rest/v1beta1/projects.locations.models) ID for the last [model migration](https://cloud.google.com/ai-platform-unified/docs/start/migrating-to-ai-platform-unified). */
		lastMigrationModelId: Option[String] = None
	)
	
	case class GoogleCloudMlV1__AutoScaling(
	  /** Optional. The minimum number of nodes to allocate for this model. These nodes are always up, starting from the time the model is deployed. Therefore, the cost of operating this model will be at least `rate` &#42; `min_nodes` &#42; number of hours since last billing cycle, where `rate` is the cost per node-hour as documented in the [pricing guide](/ml-engine/docs/pricing), even if no predictions are performed. There is additional cost for each prediction performed. Unlike manual scaling, if the load gets too heavy for the nodes that are up, the service will automatically add nodes to handle the increased load as well as scale back as traffic drops, always maintaining at least `min_nodes`. You will be charged for the time in which additional nodes are used. If `min_nodes` is not specified and AutoScaling is used with a [legacy (MLS1) machine type](/ml-engine/docs/machine-types-online-prediction), `min_nodes` defaults to 0, in which case, when traffic to a model stops (and after a cool-down period), nodes will be shut down and no charges will be incurred until traffic to the model resumes. If `min_nodes` is not specified and AutoScaling is used with a [Compute Engine (N1) machine type](/ml-engine/docs/machine-types-online-prediction), `min_nodes` defaults to 1. `min_nodes` must be at least 1 for use with a Compute Engine machine type. You can set `min_nodes` when creating the model version, and you can also update `min_nodes` for an existing version: update_body.json: { 'autoScaling': { 'minNodes': 5 } } HTTP request: PATCH https://ml.googleapis.com/v1/{name=projects/&#42;/models/&#42;/versions/&#42;}?update_mask=autoScaling.minNodes -d @./update_body.json  */
		minNodes: Option[Int] = None,
	  /** The maximum number of nodes to scale this model under load. The actual value will depend on resource quota and availability. */
		maxNodes: Option[Int] = None,
	  /** MetricSpec contains the specifications to use to calculate the desired nodes count. */
		metrics: Option[List[Schema.GoogleCloudMlV1__MetricSpec]] = None
	)
	
	object GoogleCloudMlV1__MetricSpec {
		enum NameEnum extends Enum[NameEnum] { case METRIC_NAME_UNSPECIFIED, CPU_USAGE, GPU_DUTY_CYCLE }
	}
	case class GoogleCloudMlV1__MetricSpec(
	  /** metric name. */
		name: Option[Schema.GoogleCloudMlV1__MetricSpec.NameEnum] = None,
	  /** Target specifies the target value for the given metric; once real metric deviates from the threshold by a certain percentage, the node count changes. */
		target: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__ManualScaling(
	  /** The number of nodes to allocate for this model. These nodes are always up, starting from the time the model is deployed, so the cost of operating this model will be proportional to `nodes` &#42; number of hours since last billing cycle plus the cost for each prediction performed. */
		nodes: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__RequestLoggingConfig(
	  /** Percentage of requests to be logged, expressed as a fraction from 0 to 1. For example, if you want to log 10% of requests, enter `0.1`. The sampling window is the lifetime of the model version. Defaults to 0. */
		samplingPercentage: Option[BigDecimal] = None,
	  /** Required. Fully qualified BigQuery table name in the following format: " project_id.dataset_name.table_name" The specified table must already exist, and the "Cloud ML Service Agent" for your project must have permission to write to it. The table must have the following [schema](/bigquery/docs/schemas): Field nameType Mode model STRING REQUIRED model_version STRING REQUIRED time TIMESTAMP REQUIRED raw_data STRING REQUIRED raw_prediction STRING NULLABLE groundtruth STRING NULLABLE  */
		bigqueryTableName: Option[String] = None
	)
	
	case class GoogleCloudMlV1__ExplanationConfig(
	  /** Attributes credit by computing the Aumann-Shapley value taking advantage of the model's fully differentiable structure. Refer to this paper for more details: https://arxiv.org/abs/1703.01365 */
		integratedGradientsAttribution: Option[Schema.GoogleCloudMlV1__IntegratedGradientsAttribution] = None,
	  /** An attribution method that approximates Shapley values for features that contribute to the label being predicted. A sampling strategy is used to approximate the value rather than considering all subsets of features. */
		sampledShapleyAttribution: Option[Schema.GoogleCloudMlV1__SampledShapleyAttribution] = None,
	  /** Attributes credit by computing the XRAI taking advantage of the model's fully differentiable structure. Refer to this paper for more details: https://arxiv.org/abs/1906.02825 Currently only implemented for models with natural image inputs. */
		xraiAttribution: Option[Schema.GoogleCloudMlV1__XraiAttribution] = None
	)
	
	case class GoogleCloudMlV1__IntegratedGradientsAttribution(
	  /** Number of steps for approximating the path integral. A good value to start is 50 and gradually increase until the sum to diff property is met within the desired error range. */
		numIntegralSteps: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__SampledShapleyAttribution(
	  /** The number of feature permutations to consider when approximating the Shapley values. */
		numPaths: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__XraiAttribution(
	  /** Number of steps for approximating the path integral. A good value to start is 50 and gradually increase until the sum to diff property is met within the desired error range. */
		numIntegralSteps: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__ContainerSpec(
	  /** URI of the Docker image to be used as the custom container for serving predictions. This URI must identify [an image in Artifact Registry](/artifact-registry/docs/overview) and begin with the hostname `{REGION}-docker.pkg.dev`, where `{REGION}` is replaced by the region that matches AI Platform Prediction [regional endpoint](/ai-platform/prediction/docs/regional-endpoints) that you are using. For example, if you are using the `us-central1-ml.googleapis.com` endpoint, then this URI must begin with `us-central1-docker.pkg.dev`. To use a custom container, the [AI Platform Google-managed service account](/ai-platform/prediction/docs/custom-service-account#default) must have permission to pull (read) the Docker image at this URI. The AI Platform Google-managed service account has the following format: `service-{PROJECT_NUMBER}@cloud-ml.google.com.iam.gserviceaccount.com` {PROJECT_NUMBER} is replaced by your Google Cloud project number. By default, this service account has necessary permissions to pull an Artifact Registry image in the same Google Cloud project where you are using AI Platform Prediction. In this case, no configuration is necessary. If you want to use an image from a different Google Cloud project, learn how to [grant the Artifact Registry Reader (roles/artifactregistry.reader) role for a repository](/artifact-registry/docs/access-control#grant-repo) to your projet's AI Platform Google-managed service account. To learn about the requirements for the Docker image itself, read [Custom container requirements](/ai-platform/prediction/docs/custom-container-requirements). */
		image: Option[String] = None,
	  /** Immutable. Specifies the command that runs when the container starts. This overrides the container's [`ENTRYPOINT`](https://docs.docker.com/engine/reference/builder/#entrypoint). Specify this field as an array of executable and arguments, similar to a Docker `ENTRYPOINT`'s "exec" form, not its "shell" form. If you do not specify this field, then the container's `ENTRYPOINT` runs, in conjunction with the args field or the container's [`CMD`](https://docs.docker.com/engine/reference/builder/#cmd), if either exists. If this field is not specified and the container does not have an `ENTRYPOINT`, then refer to the [Docker documentation about how `CMD` and `ENTRYPOINT` interact](https://docs.docker.com/engine/reference/builder/#understand-how-cmd-and-entrypoint-interact). If you specify this field, then you can also specify the `args` field to provide additional arguments for this command. However, if you specify this field, then the container's `CMD` is ignored. See the [Kubernetes documentation about how the `command` and `args` fields interact with a container's `ENTRYPOINT` and `CMD`](https://kubernetes.io/docs/tasks/inject-data-application/define-command-argument-container/#notes). In this field, you can reference [environment variables set by AI Platform Prediction](/ai-platform/prediction/docs/custom-container-requirements#aip-variables) and environment variables set in the env field. You cannot reference environment variables set in the Docker image. In order for environment variables to be expanded, reference them by using the following syntax: $( VARIABLE_NAME) Note that this differs from Bash variable expansion, which does not use parentheses. If a variable cannot be resolved, the reference in the input string is used unchanged. To avoid variable expansion, you can escape this syntax with `$$`; for example: $$(VARIABLE_NAME) This field corresponds to the `command` field of the [Kubernetes Containers v1 core API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core). */
		command: Option[List[String]] = None,
	  /** Immutable. Specifies arguments for the command that runs when the container starts. This overrides the container's [`CMD`](https://docs.docker.com/engine/reference/builder/#cmd). Specify this field as an array of executable and arguments, similar to a Docker `CMD`'s "default parameters" form. If you don't specify this field but do specify the command field, then the command from the `command` field runs without any additional arguments. See the [Kubernetes documentation about how the `command` and `args` fields interact with a container's `ENTRYPOINT` and `CMD`](https://kubernetes.io/docs/tasks/inject-data-application/define-command-argument-container/#notes). If you don't specify this field and don't specify the `commmand` field, then the container's [`ENTRYPOINT`](https://docs.docker.com/engine/reference/builder/#cmd) and `CMD` determine what runs based on their default behavior. See the [Docker documentation about how `CMD` and `ENTRYPOINT` interact](https://docs.docker.com/engine/reference/builder/#understand-how-cmd-and-entrypoint-interact). In this field, you can reference [environment variables set by AI Platform Prediction](/ai-platform/prediction/docs/custom-container-requirements#aip-variables) and environment variables set in the env field. You cannot reference environment variables set in the Docker image. In order for environment variables to be expanded, reference them by using the following syntax: $( VARIABLE_NAME) Note that this differs from Bash variable expansion, which does not use parentheses. If a variable cannot be resolved, the reference in the input string is used unchanged. To avoid variable expansion, you can escape this syntax with `$$`; for example: $$(VARIABLE_NAME) This field corresponds to the `args` field of the [Kubernetes Containers v1 core API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core). */
		args: Option[List[String]] = None,
	  /** Immutable. List of ports to expose from the container. AI Platform Prediction sends any prediction requests that it receives to the first port on this list. AI Platform Prediction also sends [liveness and health checks](/ai-platform/prediction/docs/custom-container-requirements#health) to this port. If you do not specify this field, it defaults to following value: ```json [ { "containerPort": 8080 } ] ``` AI Platform Prediction does not use ports other than the first one listed. This field corresponds to the `ports` field of the [Kubernetes Containers v1 core API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core). */
		ports: Option[List[Schema.GoogleCloudMlV1__ContainerPort]] = None,
	  /** Immutable. List of environment variables to set in the container. After the container starts running, code running in the container can read these environment variables. Additionally, the command and args fields can reference these variables. Later entries in this list can also reference earlier entries. For example, the following example sets the variable `VAR_2` to have the value `foo bar`: ```json [ { "name": "VAR_1", "value": "foo" }, { "name": "VAR_2", "value": "$(VAR_1) bar" } ] ``` If you switch the order of the variables in the example, then the expansion does not occur. This field corresponds to the `env` field of the [Kubernetes Containers v1 core API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core). */
		env: Option[List[Schema.GoogleCloudMlV1__EnvVar]] = None
	)
	
	case class GoogleCloudMlV1__ContainerPort(
	  /** Number of the port to expose on the container. This must be a valid port number: 0 < PORT_NUMBER < 65536. */
		containerPort: Option[Int] = None
	)
	
	case class GoogleCloudMlV1__EnvVar(
	  /** Name of the environment variable. Must be a [valid C identifier](https://github.com/kubernetes/kubernetes/blob/v1.18.8/staging/src/k8s.io/apimachinery/pkg/util/validation/validation.go#L258) and must not begin with the prefix `AIP_`. */
		name: Option[String] = None,
	  /** Value of the environment variable. Defaults to an empty string. In this field, you can reference [environment variables set by AI Platform Prediction](/ai-platform/prediction/docs/custom-container-requirements#aip-variables) and environment variables set earlier in the same env field as where this message occurs. You cannot reference environment variables set in the Docker image. In order for environment variables to be expanded, reference them by using the following syntax: $(VARIABLE_NAME) Note that this differs from Bash variable expansion, which does not use parentheses. If a variable cannot be resolved, the reference in the input string is used unchanged. To avoid variable expansion, you can escape this syntax with `$$`; for example: $$(VARIABLE_NAME) */
		value: Option[String] = None
	)
	
	case class GoogleCloudMlV1__RouteMap(
	  /** HTTP path on the container to send prediction requests to. AI Platform Prediction forwards requests sent using projects.predict to this path on the container's IP address and port. AI Platform Prediction then returns the container's response in the API response. For example, if you set this field to `/foo`, then when AI Platform Prediction receives a prediction request, it forwards the request body in a POST request to the `/foo` path on the port of your container specified by the first value of Version.container.ports. If you don't specify this field, it defaults to the following value: /v1/models/MODEL/versions/VERSION:predict The placeholders in this value are replaced as follows: &#42; MODEL: The name of the parent Model. This does not include the "projects/PROJECT_ID/models/" prefix that the API returns in output; it is the bare model name, as provided to projects.models.create. &#42; VERSION: The name of the model version. This does not include the "projects/PROJECT_ID/models/MODEL/versions/" prefix that the API returns in output; it is the bare version name, as provided to projects.models.versions.create. */
		predict: Option[String] = None,
	  /** HTTP path on the container to send health checkss to. AI Platform Prediction intermittently sends GET requests to this path on the container's IP address and port to check that the container is healthy. Read more about [health checks](/ai-platform/prediction/docs/custom-container-requirements#checks). For example, if you set this field to `/bar`, then AI Platform Prediction intermittently sends a GET request to the `/bar` path on the port of your container specified by the first value of Version.container.ports. If you don't specify this field, it defaults to the following value: /v1/models/ MODEL/versions/VERSION The placeholders in this value are replaced as follows: &#42; MODEL: The name of the parent Model. This does not include the "projects/PROJECT_ID/models/" prefix that the API returns in output; it is the bare model name, as provided to projects.models.create. &#42; VERSION: The name of the model version. This does not include the "projects/PROJECT_ID /models/MODEL/versions/" prefix that the API returns in output; it is the bare version name, as provided to projects.models.versions.create. */
		health: Option[String] = None
	)
	
	case class GoogleCloudMlV1__ListModelsResponse(
	  /** The list of models. */
		models: Option[List[Schema.GoogleCloudMlV1__Model]] = None,
	  /** Optional. Pass this token as the `page_token` field of the request for a subsequent call. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudMlV1__ListVersionsResponse(
	  /** The list of versions. */
		versions: Option[List[Schema.GoogleCloudMlV1__Version]] = None,
	  /** Optional. Pass this token as the `page_token` field of the request for a subsequent call. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudMlV1__SetDefaultVersionRequest(
	
	)
	
	case class GoogleCloudMlV1__PredictRequest(
	  /**  Required. The prediction request body. Refer to the [request body details section](#request-body-details) for more information on how to structure your request. */
		httpBody: Option[Schema.GoogleApi__HttpBody] = None
	)
	
	case class GoogleApi__HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudMlV1__ExplainRequest(
	  /** Required. The explanation request body. */
		httpBody: Option[Schema.GoogleApi__HttpBody] = None
	)
	
	case class GoogleCloudMlV1__GetConfigResponse(
	  /** The service account Cloud ML uses to access resources in the project. */
		serviceAccount: Option[String] = None,
	  /** The project number for `service_account`. */
		serviceAccountProject: Option[String] = None,
		config: Option[Schema.GoogleCloudMlV1__Config] = None
	)
	
	case class GoogleCloudMlV1__Config(
	  /** The service account Cloud ML uses to run on TPU node. */
		tpuServiceAccount: Option[String] = None
	)
	
	object GoogleCloudMlV1__Study {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE, COMPLETED }
	}
	case class GoogleCloudMlV1__Study(
	  /** Output only. The name of a study. */
		name: Option[String] = None,
	  /** Required. Configuration of the study. */
		studyConfig: Option[Schema.GoogleCloudMlV1__StudyConfig] = None,
	  /** Output only. The detailed state of a study. */
		state: Option[Schema.GoogleCloudMlV1__Study.StateEnum] = None,
	  /** Output only. Time at which the study was created. */
		createTime: Option[String] = None,
	  /** Output only. A human readable reason why the Study is inactive. This should be empty if a study is ACTIVE or COMPLETED. */
		inactiveReason: Option[String] = None
	)
	
	object GoogleCloudMlV1__StudyConfig {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case ALGORITHM_UNSPECIFIED, GAUSSIAN_PROCESS_BANDIT, GRID_SEARCH, RANDOM_SEARCH }
	}
	case class GoogleCloudMlV1__StudyConfig(
	  /** Metric specs for the study. */
		metrics: Option[List[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec]] = None,
	  /** Required. The set of parameters to tune. */
		parameters: Option[List[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec]] = None,
	  /** The search algorithm specified for the study. */
		algorithm: Option[Schema.GoogleCloudMlV1__StudyConfig.AlgorithmEnum] = None,
	  /** Configuration for automated stopping of unpromising Trials. */
		automatedStoppingConfig: Option[Schema.GoogleCloudMlV1__AutomatedStoppingConfig] = None
	)
	
	object GoogleCloudMlV1_StudyConfig_MetricSpec {
		enum GoalEnum extends Enum[GoalEnum] { case GOAL_TYPE_UNSPECIFIED, MAXIMIZE, MINIMIZE }
	}
	case class GoogleCloudMlV1_StudyConfig_MetricSpec(
	  /** Required. The optimization goal of the metric. */
		goal: Option[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec.GoalEnum] = None,
	  /** Required. The name of the metric. */
		metric: Option[String] = None
	)
	
	object GoogleCloudMlV1_StudyConfig_ParameterSpec {
		enum TypeEnum extends Enum[TypeEnum] { case PARAMETER_TYPE_UNSPECIFIED, DOUBLE, INTEGER, CATEGORICAL, DISCRETE }
		enum ScaleTypeEnum extends Enum[ScaleTypeEnum] { case SCALE_TYPE_UNSPECIFIED, UNIT_LINEAR_SCALE, UNIT_LOG_SCALE, UNIT_REVERSE_LOG_SCALE }
	}
	case class GoogleCloudMlV1_StudyConfig_ParameterSpec(
	  /** Required. The parameter name must be unique amongst all ParameterSpecs. */
		parameter: Option[String] = None,
	  /** Required. The type of the parameter. */
		`type`: Option[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.TypeEnum] = None,
	  /** The value spec for a 'DOUBLE' parameter. */
		doubleValueSpec: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DoubleValueSpec] = None,
	  /** The value spec for an 'INTEGER' parameter. */
		integerValueSpec: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_IntegerValueSpec] = None,
	  /** The value spec for a 'CATEGORICAL' parameter. */
		categoricalValueSpec: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_CategoricalValueSpec] = None,
	  /** The value spec for a 'DISCRETE' parameter. */
		discreteValueSpec: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DiscreteValueSpec] = None,
	  /** How the parameter should be scaled. Leave unset for categorical parameters. */
		scaleType: Option[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.ScaleTypeEnum] = None,
		parentDiscreteValues: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentDiscreteValueSpec] = None,
		parentIntValues: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentIntValueSpec] = None,
		parentCategoricalValues: Option[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentCategoricalValueSpec] = None,
	  /** A child node is active if the parameter's value matches the child node's matching_parent_values. If two items in child_parameter_specs have the same name, they must have disjoint matching_parent_values. */
		childParameterSpecs: Option[List[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec]] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_DoubleValueSpec(
	  /** Must be specified if type is `DOUBLE`. Minimum value of the parameter. */
		minValue: Option[BigDecimal] = None,
	  /** Must be specified if type is `DOUBLE`. Maximum value of the parameter. */
		maxValue: Option[BigDecimal] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_IntegerValueSpec(
	  /** Must be specified if type is `INTEGER`. Minimum value of the parameter. */
		minValue: Option[String] = None,
	  /** Must be specified if type is `INTEGER`. Maximum value of the parameter. */
		maxValue: Option[String] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_CategoricalValueSpec(
	  /** Must be specified if type is `CATEGORICAL`. The list of possible categories. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_DiscreteValueSpec(
	  /** Must be specified if type is `DISCRETE`. A list of feasible points. The list should be in strictly increasing order. For instance, this parameter might have possible settings of 1.5, 2.5, and 4.0. This list should not contain more than 1,000 values. */
		values: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentDiscreteValueSpec(
	  /** Matches values of the parent parameter with type 'DISCRETE'. All values must exist in `discrete_value_spec` of parent parameter. */
		values: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentIntValueSpec(
	  /** Matches values of the parent parameter with type 'INTEGER'. All values must lie in `integer_value_spec` of parent parameter. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentCategoricalValueSpec(
	  /** Matches values of the parent parameter with type 'CATEGORICAL'. All values must exist in `categorical_value_spec` of parent parameter. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudMlV1__AutomatedStoppingConfig(
		decayCurveStoppingConfig: Option[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_DecayCurveAutomatedStoppingConfig] = None,
		medianAutomatedStoppingConfig: Option[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_MedianAutomatedStoppingConfig] = None
	)
	
	case class GoogleCloudMlV1_AutomatedStoppingConfig_DecayCurveAutomatedStoppingConfig(
	  /** If true, measurement.elapsed_time is used as the x-axis of each Trials Decay Curve. Otherwise, Measurement.steps will be used as the x-axis. */
		useElapsedTime: Option[Boolean] = None
	)
	
	case class GoogleCloudMlV1_AutomatedStoppingConfig_MedianAutomatedStoppingConfig(
	  /** If true, the median automated stopping rule applies to measurement.use_elapsed_time, which means the elapsed_time field of the current trial's latest measurement is used to compute the median objective value for each completed trial. */
		useElapsedTime: Option[Boolean] = None
	)
	
	case class GoogleCloudMlV1__ListStudiesResponse(
	  /** The studies associated with the project. */
		studies: Option[List[Schema.GoogleCloudMlV1__Study]] = None
	)
	
	case class GoogleCloudMlV1__SuggestTrialsRequest(
	  /** Required. The number of suggestions requested. */
		suggestionCount: Option[Int] = None,
	  /** Required. The identifier of the client that is requesting the suggestion. If multiple SuggestTrialsRequests have the same `client_id`, the service will return the identical suggested trial if the trial is pending, and provide a new trial if the last suggested trial was completed. */
		clientId: Option[String] = None
	)
	
	object GoogleCloudMlV1__Trial {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, REQUESTED, ACTIVE, COMPLETED, STOPPING }
	}
	case class GoogleCloudMlV1__Trial(
	  /** Output only. Name of the trial assigned by the service. */
		name: Option[String] = None,
	  /** The detailed state of a trial. */
		state: Option[Schema.GoogleCloudMlV1__Trial.StateEnum] = None,
	  /** The parameters of the trial. */
		parameters: Option[List[Schema.GoogleCloudMlV1_Trial_Parameter]] = None,
	  /** The final measurement containing the objective value. */
		finalMeasurement: Option[Schema.GoogleCloudMlV1__Measurement] = None,
	  /** A list of measurements that are strictly lexicographically ordered by their induced tuples (steps, elapsed_time). These are used for early stopping computations. */
		measurements: Option[List[Schema.GoogleCloudMlV1__Measurement]] = None,
	  /** Output only. Time at which the trial was started. */
		startTime: Option[String] = None,
	  /** Output only. Time at which the trial's status changed to COMPLETED. */
		endTime: Option[String] = None,
	  /** Output only. The identifier of the client that originally requested this trial. */
		clientId: Option[String] = None,
	  /** Output only. If true, the parameters in this trial are not attempted again. */
		trialInfeasible: Option[Boolean] = None,
	  /** Output only. A human readable string describing why the trial is infeasible. This should only be set if trial_infeasible is true. */
		infeasibleReason: Option[String] = None
	)
	
	case class GoogleCloudMlV1_Trial_Parameter(
	  /** The name of the parameter. */
		parameter: Option[String] = None,
	  /** Must be set if ParameterType is DOUBLE or DISCRETE. */
		floatValue: Option[BigDecimal] = None,
	  /** Must be set if ParameterType is INTEGER */
		intValue: Option[String] = None,
	  /** Must be set if ParameterTypeis CATEGORICAL */
		stringValue: Option[String] = None
	)
	
	case class GoogleCloudMlV1__Measurement(
	  /** Output only. Time that the trial has been running at the point of this measurement. */
		elapsedTime: Option[String] = None,
	  /** The number of steps a machine learning model has been trained for. Must be non-negative. */
		stepCount: Option[String] = None,
	  /** Provides a list of metrics that act as inputs into the objective function. */
		metrics: Option[List[Schema.GoogleCloudMlV1_Measurement_Metric]] = None
	)
	
	case class GoogleCloudMlV1_Measurement_Metric(
	  /** Required. Metric name. */
		metric: Option[String] = None,
	  /** Required. The value for this metric. */
		value: Option[BigDecimal] = None
	)
	
	case class GoogleCloudMlV1__ListTrialsResponse(
	  /** The trials associated with the study. */
		trials: Option[List[Schema.GoogleCloudMlV1__Trial]] = None
	)
	
	case class GoogleCloudMlV1__AddTrialMeasurementRequest(
	  /** Required. The measurement to be added to a trial. */
		measurement: Option[Schema.GoogleCloudMlV1__Measurement] = None
	)
	
	case class GoogleCloudMlV1__CompleteTrialRequest(
	  /** Optional. If provided, it will be used as the completed trial's final_measurement; Otherwise, the service will auto-select a previously reported measurement as the final-measurement */
		finalMeasurement: Option[Schema.GoogleCloudMlV1__Measurement] = None,
	  /** Optional. True if the trial cannot be run with the given Parameter, and final_measurement will be ignored. */
		trialInfeasible: Option[Boolean] = None,
	  /** Optional. A human readable reason why the trial was infeasible. This should only be provided if `trial_infeasible` is true. */
		infeasibleReason: Option[String] = None
	)
	
	case class GoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest(
	
	)
	
	case class GoogleCloudMlV1__StopTrialRequest(
	
	)
	
	case class GoogleCloudMlV1__ListOptimalTrialsRequest(
	
	)
	
	case class GoogleCloudMlV1__ListOptimalTrialsResponse(
	  /** The pareto-optimal trials for multiple objective study or the optimal trial for single objective study. The definition of pareto-optimal can be checked in wiki page. https://en.wikipedia.org/wiki/Pareto_efficiency */
		trials: Option[List[Schema.GoogleCloudMlV1__Trial]] = None
	)
	
	case class GoogleIamV1__SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1__Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class GoogleIamV1__Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1__Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1__AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class GoogleIamV1__Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleType__Expr] = None
	)
	
	case class GoogleType__Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class GoogleIamV1__AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1__AuditLogConfig]] = None
	)
	
	object GoogleIamV1__AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1__AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1__AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class GoogleIamV1__TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1__TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object GoogleCloudMlV1__OperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE_VERSION, DELETE_VERSION, DELETE_MODEL, UPDATE_MODEL, UPDATE_VERSION, UPDATE_CONFIG }
	}
	case class GoogleCloudMlV1__OperationMetadata(
	  /** The time the operation was submitted. */
		createTime: Option[String] = None,
	  /** The time operation processing started. */
		startTime: Option[String] = None,
	  /** The time operation processing completed. */
		endTime: Option[String] = None,
	  /** Indicates whether a request to cancel this operation has been made. */
		isCancellationRequested: Option[Boolean] = None,
	  /** The operation type. */
		operationType: Option[Schema.GoogleCloudMlV1__OperationMetadata.OperationTypeEnum] = None,
	  /** Contains the name of the model associated with the operation. */
		modelName: Option[String] = None,
	  /** Contains the version associated with the operation. */
		version: Option[Schema.GoogleCloudMlV1__Version] = None,
	  /** Contains the project number associated with the operation. */
		projectNumber: Option[String] = None,
	  /** The user labels, inherited from the model or the model version being operated on. */
		labels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudMlV1__SuggestTrialsMetadata(
	  /** The name of the study that the trial belongs to. */
		study: Option[String] = None,
	  /** The time operation was submitted. */
		createTime: Option[String] = None,
	  /** The number of suggestions requested. */
		suggestionCount: Option[Int] = None,
	  /** The identifier of the client that is requesting the suggestion. */
		clientId: Option[String] = None
	)
	
	object GoogleCloudMlV1__SuggestTrialsResponse {
		enum StudyStateEnum extends Enum[StudyStateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE, COMPLETED }
	}
	case class GoogleCloudMlV1__SuggestTrialsResponse(
	  /** A list of trials. */
		trials: Option[List[Schema.GoogleCloudMlV1__Trial]] = None,
	  /** The state of the study. */
		studyState: Option[Schema.GoogleCloudMlV1__SuggestTrialsResponse.StudyStateEnum] = None,
	  /** The time at which the operation was started. */
		startTime: Option[String] = None,
	  /** The time at which operation processing completed. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudMlV1__CheckTrialEarlyStoppingStateMetatdata(
	  /** The name of the study that the trial belongs to. */
		study: Option[String] = None,
	  /** The trial name. */
		trial: Option[String] = None,
	  /** The time at which the operation was submitted. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudMlV1__CheckTrialEarlyStoppingStateResponse(
	  /** True if the Trial should stop. */
		shouldStop: Option[Boolean] = None,
	  /** The time at which the operation was started. */
		startTime: Option[String] = None,
	  /** The time at which operation processing completed. */
		endTime: Option[String] = None
	)
}
