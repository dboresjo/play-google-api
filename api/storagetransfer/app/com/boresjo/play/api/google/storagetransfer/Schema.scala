package com.boresjo.play.api.google.storagetransfer

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
	  /** The server-assigned unique name. The format of `name` is `transferOperations/some/unique/name`. */
		name: Option[String] = None,
	  /** Represents the transfer operation object. To request a TransferOperation object, use transferOperations.get. */
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
	
	case class GoogleServiceAccount(
	  /** Email address of the service account. */
		accountEmail: Option[String] = None,
	  /** Unique identifier for the service account. */
		subjectId: Option[String] = None
	)
	
	object TransferJob {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, ENABLED, DISABLED, DELETED }
	}
	case class TransferJob(
	  /** A unique name (within the transfer project) assigned when the job is created. If this field is empty in a CreateTransferJobRequest, Storage Transfer Service assigns a unique name. Otherwise, the specified name is used as the unique name for this job. If the specified name is in use by a job, the creation request fails with an ALREADY_EXISTS error. This name must start with `"transferJobs/"` prefix and end with a letter or a number, and should be no more than 128 characters. For transfers involving PosixFilesystem, this name must start with `transferJobs/OPI` specifically. For all other transfer types, this name must not start with `transferJobs/OPI`. Non-PosixFilesystem example: `"transferJobs/^(?!OPI)[A-Za-z0-9-._~]&#42;[A-Za-z0-9]$"` PosixFilesystem example: `"transferJobs/OPI^[A-Za-z0-9-._~]&#42;[A-Za-z0-9]$"` Applications must not rely on the enforcement of naming requirements involving OPI. Invalid job names fail with an INVALID_ARGUMENT error. */
		name: Option[String] = None,
	  /** A description provided by the user for the job. Its max length is 1024 bytes when Unicode-encoded. */
		description: Option[String] = None,
	  /** The ID of the Google Cloud project that owns the job. */
		projectId: Option[String] = None,
	  /** Transfer specification. */
		transferSpec: Option[Schema.TransferSpec] = None,
	  /** Replication specification. */
		replicationSpec: Option[Schema.ReplicationSpec] = None,
	  /** Notification configuration. */
		notificationConfig: Option[Schema.NotificationConfig] = None,
	  /** Logging configuration. */
		loggingConfig: Option[Schema.LoggingConfig] = None,
	  /** Specifies schedule for the transfer job. This is an optional field. When the field is not set, the job never executes a transfer, unless you invoke RunTransferJob or update the job to have a non-empty schedule. */
		schedule: Option[Schema.Schedule] = None,
	  /** Specifies the event stream for the transfer job for event-driven transfers. When EventStream is specified, the Schedule fields are ignored. */
		eventStream: Option[Schema.EventStream] = None,
	  /** Status of the job. This value MUST be specified for `CreateTransferJobRequests`. &#42;&#42;Note:&#42;&#42; The effect of the new job status takes place during a subsequent job run. For example, if you change the job status from ENABLED to DISABLED, and an operation spawned by the transfer is running, the status change would not affect the current operation. */
		status: Option[Schema.TransferJob.StatusEnum] = None,
	  /** Output only. The time that the transfer job was created. */
		creationTime: Option[String] = None,
	  /** Output only. The time that the transfer job was last modified. */
		lastModificationTime: Option[String] = None,
	  /** Output only. The time that the transfer job was deleted. */
		deletionTime: Option[String] = None,
	  /** The name of the most recently started TransferOperation of this JobConfig. Present if a TransferOperation has been created for this JobConfig. */
		latestOperationName: Option[String] = None
	)
	
	case class TransferSpec(
	  /** A Cloud Storage data sink. */
		gcsDataSink: Option[Schema.GcsData] = None,
	  /** A POSIX Filesystem data sink. */
		posixDataSink: Option[Schema.PosixFilesystem] = None,
	  /** A Cloud Storage data source. */
		gcsDataSource: Option[Schema.GcsData] = None,
	  /** An AWS S3 data source. */
		awsS3DataSource: Option[Schema.AwsS3Data] = None,
	  /** An HTTP URL data source. */
		httpDataSource: Option[Schema.HttpData] = None,
	  /** A POSIX Filesystem data source. */
		posixDataSource: Option[Schema.PosixFilesystem] = None,
	  /** An Azure Blob Storage data source. */
		azureBlobStorageDataSource: Option[Schema.AzureBlobStorageData] = None,
	  /** An AWS S3 compatible data source. */
		awsS3CompatibleDataSource: Option[Schema.AwsS3CompatibleData] = None,
	  /** An HDFS cluster data source. */
		hdfsDataSource: Option[Schema.HdfsData] = None,
	  /** For transfers between file systems, specifies a Cloud Storage bucket to be used as an intermediate location through which to transfer data. See [Transfer data between file systems](https://cloud.google.com/storage-transfer/docs/file-to-file) for more information. */
		gcsIntermediateDataLocation: Option[Schema.GcsData] = None,
	  /** Only objects that satisfy these object conditions are included in the set of data source and data sink objects. Object conditions based on objects' "last modification time" do not exclude objects in a data sink. */
		objectConditions: Option[Schema.ObjectConditions] = None,
	  /** If the option delete_objects_unique_in_sink is `true` and time-based object conditions such as 'last modification time' are specified, the request fails with an INVALID_ARGUMENT error. */
		transferOptions: Option[Schema.TransferOptions] = None,
	  /** A manifest file provides a list of objects to be transferred from the data source. This field points to the location of the manifest file. Otherwise, the entire source bucket is used. ObjectConditions still apply. */
		transferManifest: Option[Schema.TransferManifest] = None,
	  /** Specifies the agent pool name associated with the posix data source. When unspecified, the default name is used. */
		sourceAgentPoolName: Option[String] = None,
	  /** Specifies the agent pool name associated with the posix data sink. When unspecified, the default name is used. */
		sinkAgentPoolName: Option[String] = None
	)
	
	case class GcsData(
	  /** Required. Cloud Storage bucket name. Must meet [Bucket Name Requirements](/storage/docs/naming#requirements). */
		bucketName: Option[String] = None,
	  /** Root path to transfer objects. Must be an empty string or full path name that ends with a '/'. This field is treated as an object prefix. As such, it should generally not begin with a '/'. The root path value must meet [Object Name Requirements](/storage/docs/naming#objectnames). */
		path: Option[String] = None,
	  /** Preview. Enables the transfer of managed folders between Cloud Storage buckets. Set this option on the gcs_data_source. If set to true: - Managed folders in the source bucket are transferred to the destination bucket. - Managed folders in the destination bucket are overwritten. Other OVERWRITE options are not supported. See [Transfer Cloud Storage managed folders](/storage-transfer/docs/managed-folders). */
		managedFolderTransferEnabled: Option[Boolean] = None
	)
	
	case class PosixFilesystem(
	  /** Root directory path to the filesystem. */
		rootDirectory: Option[String] = None
	)
	
	case class AwsS3Data(
	  /** Required. S3 Bucket name (see [Creating a bucket](https://docs.aws.amazon.com/AmazonS3/latest/dev/create-bucket-get-location-example.html)). */
		bucketName: Option[String] = None,
	  /** Input only. AWS access key used to sign the API requests to the AWS S3 bucket. Permissions on the bucket must be granted to the access ID of the AWS access key. For information on our data retention policy for user credentials, see [User credentials](/storage-transfer/docs/data-retention#user-credentials). */
		awsAccessKey: Option[Schema.AwsAccessKey] = None,
	  /** Root path to transfer objects. Must be an empty string or full path name that ends with a '/'. This field is treated as an object prefix. As such, it should generally not begin with a '/'. */
		path: Option[String] = None,
	  /** The Amazon Resource Name (ARN) of the role to support temporary credentials via `AssumeRoleWithWebIdentity`. For more information about ARNs, see [IAM ARNs](https://docs.aws.amazon.com/IAM/latest/UserGuide/reference_identifiers.html#identifiers-arns). When a role ARN is provided, Transfer Service fetches temporary credentials for the session using a `AssumeRoleWithWebIdentity` call for the provided role using the GoogleServiceAccount for this project. */
		roleArn: Option[String] = None,
	  /** Optional. The CloudFront distribution domain name pointing to this bucket, to use when fetching. See [Transfer from S3 via CloudFront](https://cloud.google.com/storage-transfer/docs/s3-cloudfront) for more information. Format: `https://{id}.cloudfront.net` or any valid custom domain. Must begin with `https://`. */
		cloudfrontDomain: Option[String] = None,
	  /** Optional. The Resource name of a secret in Secret Manager. AWS credentials must be stored in Secret Manager in JSON format: { "access_key_id": "ACCESS_KEY_ID", "secret_access_key": "SECRET_ACCESS_KEY" } GoogleServiceAccount must be granted `roles/secretmanager.secretAccessor` for the resource. See [Configure access to a source: Amazon S3] (https://cloud.google.com/storage-transfer/docs/source-amazon-s3#secret_manager) for more information. If `credentials_secret` is specified, do not specify role_arn or aws_access_key. Format: `projects/{project_number}/secrets/{secret_name}` */
		credentialsSecret: Option[String] = None,
	  /** Egress bytes over a Google-managed private network. This network is shared between other users of Storage Transfer Service. */
		managedPrivateNetwork: Option[Boolean] = None
	)
	
	case class AwsAccessKey(
	  /** Required. AWS access key ID. */
		accessKeyId: Option[String] = None,
	  /** Required. AWS secret access key. This field is not returned in RPC responses. */
		secretAccessKey: Option[String] = None
	)
	
	case class HttpData(
	  /** Required. The URL that points to the file that stores the object list entries. This file must allow public access. Currently, only URLs with HTTP and HTTPS schemes are supported. */
		listUrl: Option[String] = None
	)
	
	case class AzureBlobStorageData(
	  /** Required. The name of the Azure Storage account. */
		storageAccount: Option[String] = None,
	  /** Required. Input only. Credentials used to authenticate API requests to Azure. For information on our data retention policy for user credentials, see [User credentials](/storage-transfer/docs/data-retention#user-credentials). */
		azureCredentials: Option[Schema.AzureCredentials] = None,
	  /** Required. The container to transfer from the Azure Storage account. */
		container: Option[String] = None,
	  /** Root path to transfer objects. Must be an empty string or full path name that ends with a '/'. This field is treated as an object prefix. As such, it should generally not begin with a '/'. */
		path: Option[String] = None,
	  /** Optional. The Resource name of a secret in Secret Manager. The Azure SAS token must be stored in Secret Manager in JSON format: { "sas_token" : "SAS_TOKEN" } GoogleServiceAccount must be granted `roles/secretmanager.secretAccessor` for the resource. See [Configure access to a source: Microsoft Azure Blob Storage] (https://cloud.google.com/storage-transfer/docs/source-microsoft-azure#secret_manager) for more information. If `credentials_secret` is specified, do not specify azure_credentials. Format: `projects/{project_number}/secrets/{secret_name}` */
		credentialsSecret: Option[String] = None
	)
	
	case class AzureCredentials(
	  /** Required. Azure shared access signature (SAS). For more information about SAS, see [Grant limited access to Azure Storage resources using shared access signatures (SAS)](https://docs.microsoft.com/en-us/azure/storage/common/storage-sas-overview). */
		sasToken: Option[String] = None
	)
	
	case class AwsS3CompatibleData(
	  /** Required. Specifies the name of the bucket. */
		bucketName: Option[String] = None,
	  /** Specifies the root path to transfer objects. Must be an empty string or full path name that ends with a '/'. This field is treated as an object prefix. As such, it should generally not begin with a '/'. */
		path: Option[String] = None,
	  /** Required. Specifies the endpoint of the storage service. */
		endpoint: Option[String] = None,
	  /** Specifies the region to sign requests with. This can be left blank if requests should be signed with an empty region. */
		region: Option[String] = None,
	  /** A S3 compatible metadata. */
		s3Metadata: Option[Schema.S3CompatibleMetadata] = None
	)
	
	object S3CompatibleMetadata {
		enum AuthMethodEnum extends Enum[AuthMethodEnum] { case AUTH_METHOD_UNSPECIFIED, AUTH_METHOD_AWS_SIGNATURE_V4, AUTH_METHOD_AWS_SIGNATURE_V2 }
		enum RequestModelEnum extends Enum[RequestModelEnum] { case REQUEST_MODEL_UNSPECIFIED, REQUEST_MODEL_VIRTUAL_HOSTED_STYLE, REQUEST_MODEL_PATH_STYLE }
		enum ProtocolEnum extends Enum[ProtocolEnum] { case NETWORK_PROTOCOL_UNSPECIFIED, NETWORK_PROTOCOL_HTTPS, NETWORK_PROTOCOL_HTTP }
		enum ListApiEnum extends Enum[ListApiEnum] { case LIST_API_UNSPECIFIED, LIST_OBJECTS_V2, LIST_OBJECTS }
	}
	case class S3CompatibleMetadata(
	  /** Specifies the authentication and authorization method used by the storage service. When not specified, Transfer Service will attempt to determine right auth method to use. */
		authMethod: Option[Schema.S3CompatibleMetadata.AuthMethodEnum] = None,
	  /** Specifies the API request model used to call the storage service. When not specified, the default value of RequestModel REQUEST_MODEL_VIRTUAL_HOSTED_STYLE is used. */
		requestModel: Option[Schema.S3CompatibleMetadata.RequestModelEnum] = None,
	  /** Specifies the network protocol of the agent. When not specified, the default value of NetworkProtocol NETWORK_PROTOCOL_HTTPS is used. */
		protocol: Option[Schema.S3CompatibleMetadata.ProtocolEnum] = None,
	  /** The Listing API to use for discovering objects. When not specified, Transfer Service will attempt to determine the right API to use. */
		listApi: Option[Schema.S3CompatibleMetadata.ListApiEnum] = None
	)
	
	case class HdfsData(
	  /** Root path to transfer files. */
		path: Option[String] = None
	)
	
	case class ObjectConditions(
	  /** Ensures that objects are not transferred until a specific minimum time has elapsed after the "last modification time". When a TransferOperation begins, objects with a "last modification time" are transferred only if the elapsed time between the start_time of the `TransferOperation` and the "last modification time" of the object is equal to or greater than the value of min_time_elapsed_since_last_modification`. Objects that do not have a "last modification time" are also transferred. */
		minTimeElapsedSinceLastModification: Option[String] = None,
	  /** Ensures that objects are not transferred if a specific maximum time has elapsed since the "last modification time". When a TransferOperation begins, objects with a "last modification time" are transferred only if the elapsed time between the start_time of the `TransferOperation`and the "last modification time" of the object is less than the value of max_time_elapsed_since_last_modification`. Objects that do not have a "last modification time" are also transferred. */
		maxTimeElapsedSinceLastModification: Option[String] = None,
	  /** If you specify `include_prefixes`, Storage Transfer Service uses the items in the `include_prefixes` array to determine which objects to include in a transfer. Objects must start with one of the matching `include_prefixes` for inclusion in the transfer. If exclude_prefixes is specified, objects must not start with any of the `exclude_prefixes` specified for inclusion in the transfer. The following are requirements of `include_prefixes`: &#42; Each include-prefix can contain any sequence of Unicode characters, to a max length of 1024 bytes when UTF8-encoded, and must not contain Carriage Return or Line Feed characters. Wildcard matching and regular expression matching are not supported. &#42; Each include-prefix must omit the leading slash. For example, to include the object `s3://my-aws-bucket/logs/y=2015/requests.gz`, specify the include-prefix as `logs/y=2015/requests.gz`. &#42; None of the include-prefix values can be empty, if specified. &#42; Each include-prefix must include a distinct portion of the object namespace. No include-prefix may be a prefix of another include-prefix. The max size of `include_prefixes` is 1000. For more information, see [Filtering objects from transfers](/storage-transfer/docs/filtering-objects-from-transfers). */
		includePrefixes: Option[List[String]] = None,
	  /** If you specify `exclude_prefixes`, Storage Transfer Service uses the items in the `exclude_prefixes` array to determine which objects to exclude from a transfer. Objects must not start with one of the matching `exclude_prefixes` for inclusion in a transfer. The following are requirements of `exclude_prefixes`: &#42; Each exclude-prefix can contain any sequence of Unicode characters, to a max length of 1024 bytes when UTF8-encoded, and must not contain Carriage Return or Line Feed characters. Wildcard matching and regular expression matching are not supported. &#42; Each exclude-prefix must omit the leading slash. For example, to exclude the object `s3://my-aws-bucket/logs/y=2015/requests.gz`, specify the exclude-prefix as `logs/y=2015/requests.gz`. &#42; None of the exclude-prefix values can be empty, if specified. &#42; Each exclude-prefix must exclude a distinct portion of the object namespace. No exclude-prefix may be a prefix of another exclude-prefix. &#42; If include_prefixes is specified, then each exclude-prefix must start with the value of a path explicitly included by `include_prefixes`. The max size of `exclude_prefixes` is 1000. For more information, see [Filtering objects from transfers](/storage-transfer/docs/filtering-objects-from-transfers). */
		excludePrefixes: Option[List[String]] = None,
	  /** If specified, only objects with a "last modification time" on or after this timestamp and objects that don't have a "last modification time" are transferred. The `last_modified_since` and `last_modified_before` fields can be used together for chunked data processing. For example, consider a script that processes each day's worth of data at a time. For that you'd set each of the fields as follows: &#42; `last_modified_since` to the start of the day &#42; `last_modified_before` to the end of the day */
		lastModifiedSince: Option[String] = None,
	  /** If specified, only objects with a "last modification time" before this timestamp and objects that don't have a "last modification time" are transferred. */
		lastModifiedBefore: Option[String] = None
	)
	
	object TransferOptions {
		enum OverwriteWhenEnum extends Enum[OverwriteWhenEnum] { case OVERWRITE_WHEN_UNSPECIFIED, DIFFERENT, NEVER, ALWAYS }
	}
	case class TransferOptions(
	  /** When to overwrite objects that already exist in the sink. The default is that only objects that are different from the source are ovewritten. If true, all objects in the sink whose name matches an object in the source are overwritten with the source object. */
		overwriteObjectsAlreadyExistingInSink: Option[Boolean] = None,
	  /** Whether objects that exist only in the sink should be deleted. &#42;&#42;Note:&#42;&#42; This option and delete_objects_from_source_after_transfer are mutually exclusive. */
		deleteObjectsUniqueInSink: Option[Boolean] = None,
	  /** Whether objects should be deleted from the source after they are transferred to the sink. &#42;&#42;Note:&#42;&#42; This option and delete_objects_unique_in_sink are mutually exclusive. */
		deleteObjectsFromSourceAfterTransfer: Option[Boolean] = None,
	  /** When to overwrite objects that already exist in the sink. If not set, overwrite behavior is determined by overwrite_objects_already_existing_in_sink. */
		overwriteWhen: Option[Schema.TransferOptions.OverwriteWhenEnum] = None,
	  /** Represents the selected metadata options for a transfer job. */
		metadataOptions: Option[Schema.MetadataOptions] = None
	)
	
	object MetadataOptions {
		enum SymlinkEnum extends Enum[SymlinkEnum] { case SYMLINK_UNSPECIFIED, SYMLINK_SKIP, SYMLINK_PRESERVE }
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, MODE_SKIP, MODE_PRESERVE }
		enum GidEnum extends Enum[GidEnum] { case GID_UNSPECIFIED, GID_SKIP, GID_NUMBER }
		enum UidEnum extends Enum[UidEnum] { case UID_UNSPECIFIED, UID_SKIP, UID_NUMBER }
		enum AclEnum extends Enum[AclEnum] { case ACL_UNSPECIFIED, ACL_DESTINATION_BUCKET_DEFAULT, ACL_PRESERVE }
		enum StorageClassEnum extends Enum[StorageClassEnum] { case STORAGE_CLASS_UNSPECIFIED, STORAGE_CLASS_DESTINATION_BUCKET_DEFAULT, STORAGE_CLASS_PRESERVE, STORAGE_CLASS_STANDARD, STORAGE_CLASS_NEARLINE, STORAGE_CLASS_COLDLINE, STORAGE_CLASS_ARCHIVE }
		enum TemporaryHoldEnum extends Enum[TemporaryHoldEnum] { case TEMPORARY_HOLD_UNSPECIFIED, TEMPORARY_HOLD_SKIP, TEMPORARY_HOLD_PRESERVE }
		enum KmsKeyEnum extends Enum[KmsKeyEnum] { case KMS_KEY_UNSPECIFIED, KMS_KEY_DESTINATION_BUCKET_DEFAULT, KMS_KEY_PRESERVE }
		enum TimeCreatedEnum extends Enum[TimeCreatedEnum] { case TIME_CREATED_UNSPECIFIED, TIME_CREATED_SKIP, TIME_CREATED_PRESERVE_AS_CUSTOM_TIME }
	}
	case class MetadataOptions(
	  /** Specifies how symlinks should be handled by the transfer. By default, symlinks are not preserved. Only applicable to transfers involving POSIX file systems, and ignored for other transfers. */
		symlink: Option[Schema.MetadataOptions.SymlinkEnum] = None,
	  /** Specifies how each file's mode attribute should be handled by the transfer. By default, mode is not preserved. Only applicable to transfers involving POSIX file systems, and ignored for other transfers. */
		mode: Option[Schema.MetadataOptions.ModeEnum] = None,
	  /** Specifies how each file's POSIX group ID (GID) attribute should be handled by the transfer. By default, GID is not preserved. Only applicable to transfers involving POSIX file systems, and ignored for other transfers. */
		gid: Option[Schema.MetadataOptions.GidEnum] = None,
	  /** Specifies how each file's POSIX user ID (UID) attribute should be handled by the transfer. By default, UID is not preserved. Only applicable to transfers involving POSIX file systems, and ignored for other transfers. */
		uid: Option[Schema.MetadataOptions.UidEnum] = None,
	  /** Specifies how each object's ACLs should be preserved for transfers between Google Cloud Storage buckets. If unspecified, the default behavior is the same as ACL_DESTINATION_BUCKET_DEFAULT. */
		acl: Option[Schema.MetadataOptions.AclEnum] = None,
	  /** Specifies the storage class to set on objects being transferred to Google Cloud Storage buckets. If unspecified, the default behavior is the same as STORAGE_CLASS_DESTINATION_BUCKET_DEFAULT. */
		storageClass: Option[Schema.MetadataOptions.StorageClassEnum] = None,
	  /** Specifies how each object's temporary hold status should be preserved for transfers between Google Cloud Storage buckets. If unspecified, the default behavior is the same as TEMPORARY_HOLD_PRESERVE. */
		temporaryHold: Option[Schema.MetadataOptions.TemporaryHoldEnum] = None,
	  /** Specifies how each object's Cloud KMS customer-managed encryption key (CMEK) is preserved for transfers between Google Cloud Storage buckets. If unspecified, the default behavior is the same as KMS_KEY_DESTINATION_BUCKET_DEFAULT. */
		kmsKey: Option[Schema.MetadataOptions.KmsKeyEnum] = None,
	  /** Specifies how each object's `timeCreated` metadata is preserved for transfers. If unspecified, the default behavior is the same as TIME_CREATED_SKIP. This behavior is supported for transfers to Cloud Storage buckets from Cloud Storage, Amazon S3, S3-compatible storage, and Azure sources. */
		timeCreated: Option[Schema.MetadataOptions.TimeCreatedEnum] = None
	)
	
	case class TransferManifest(
	  /** Specifies the path to the manifest in Cloud Storage. The Google-managed service account for the transfer must have `storage.objects.get` permission for this object. An example path is `gs://bucket_name/path/manifest.csv`. */
		location: Option[String] = None
	)
	
	case class ReplicationSpec(
	  /** The Cloud Storage bucket from which to replicate objects. */
		gcsDataSource: Option[Schema.GcsData] = None,
	  /** The Cloud Storage bucket to which to replicate objects. */
		gcsDataSink: Option[Schema.GcsData] = None,
	  /** Object conditions that determine which objects are transferred. For replication jobs, only `include_prefixes` and `exclude_prefixes` are supported. */
		objectConditions: Option[Schema.ObjectConditions] = None,
	  /** Specifies the metadata options to be applied during replication. Delete options are not supported. If a delete option is specified, the request fails with an INVALID_ARGUMENT error. */
		transferOptions: Option[Schema.TransferOptions] = None
	)
	
	object NotificationConfig {
		enum EventTypesEnum extends Enum[EventTypesEnum] { case EVENT_TYPE_UNSPECIFIED, TRANSFER_OPERATION_SUCCESS, TRANSFER_OPERATION_FAILED, TRANSFER_OPERATION_ABORTED }
		enum PayloadFormatEnum extends Enum[PayloadFormatEnum] { case PAYLOAD_FORMAT_UNSPECIFIED, NONE, JSON }
	}
	case class NotificationConfig(
	  /** Required. The `Topic.name` of the Pub/Sub topic to which to publish notifications. Must be of the format: `projects/{project}/topics/{topic}`. Not matching this format results in an INVALID_ARGUMENT error. */
		pubsubTopic: Option[String] = None,
	  /** Event types for which a notification is desired. If empty, send notifications for all event types. */
		eventTypes: Option[List[Schema.NotificationConfig.EventTypesEnum]] = None,
	  /** Required. The desired format of the notification message payloads. */
		payloadFormat: Option[Schema.NotificationConfig.PayloadFormatEnum] = None
	)
	
	object LoggingConfig {
		enum LogActionsEnum extends Enum[LogActionsEnum] { case LOGGABLE_ACTION_UNSPECIFIED, FIND, DELETE, COPY }
		enum LogActionStatesEnum extends Enum[LogActionStatesEnum] { case LOGGABLE_ACTION_STATE_UNSPECIFIED, SUCCEEDED, FAILED }
	}
	case class LoggingConfig(
	  /** Specifies the actions to be logged. If empty, no logs are generated. */
		logActions: Option[List[Schema.LoggingConfig.LogActionsEnum]] = None,
	  /** States in which `log_actions` are logged. If empty, no logs are generated. */
		logActionStates: Option[List[Schema.LoggingConfig.LogActionStatesEnum]] = None,
	  /** For PosixFilesystem transfers, enables [file system transfer logs](https://cloud.google.com/storage-transfer/docs/on-prem-transfer-log-format) instead of, or in addition to, Cloud Logging. This option ignores [LoggableAction] and [LoggableActionState]. If these are set, Cloud Logging will also be enabled for this transfer. */
		enableOnpremGcsTransferLogs: Option[Boolean] = None
	)
	
	case class Schedule(
	  /** Required. The start date of a transfer. Date boundaries are determined relative to UTC time. If `schedule_start_date` and start_time_of_day are in the past relative to the job's creation time, the transfer starts the day after you schedule the transfer request. &#42;&#42;Note:&#42;&#42; When starting jobs at or near midnight UTC it is possible that a job starts later than expected. For example, if you send an outbound request on June 1 one millisecond prior to midnight UTC and the Storage Transfer Service server receives the request on June 2, then it creates a TransferJob with `schedule_start_date` set to June 2 and a `start_time_of_day` set to midnight UTC. The first scheduled TransferOperation takes place on June 3 at midnight UTC. */
		scheduleStartDate: Option[Schema.Date] = None,
	  /** The last day a transfer runs. Date boundaries are determined relative to UTC time. A job runs once per 24 hours within the following guidelines: &#42; If `schedule_end_date` and schedule_start_date are the same and in the future relative to UTC, the transfer is executed only one time. &#42; If `schedule_end_date` is later than `schedule_start_date` and `schedule_end_date` is in the future relative to UTC, the job runs each day at start_time_of_day through `schedule_end_date`. */
		scheduleEndDate: Option[Schema.Date] = None,
	  /** The time in UTC that a transfer job is scheduled to run. Transfers may start later than this time. If `start_time_of_day` is not specified: &#42; One-time transfers run immediately. &#42; Recurring transfers run immediately, and each day at midnight UTC, through schedule_end_date. If `start_time_of_day` is specified: &#42; One-time transfers run at the specified time. &#42; Recurring transfers run at the specified time each day, through `schedule_end_date`. */
		startTimeOfDay: Option[Schema.TimeOfDay] = None,
	  /** The time in UTC that no further transfer operations are scheduled. Combined with schedule_end_date, `end_time_of_day` specifies the end date and time for starting new transfer operations. This field must be greater than or equal to the timestamp corresponding to the combintation of schedule_start_date and start_time_of_day, and is subject to the following: &#42; If `end_time_of_day` is not set and `schedule_end_date` is set, then a default value of `23:59:59` is used for `end_time_of_day`. &#42; If `end_time_of_day` is set and `schedule_end_date` is not set, then INVALID_ARGUMENT is returned. */
		endTimeOfDay: Option[Schema.TimeOfDay] = None,
	  /** Interval between the start of each scheduled TransferOperation. If unspecified, the default value is 24 hours. This value may not be less than 1 hour. */
		repeatInterval: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
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
	
	case class EventStream(
	  /** Required. Specifies a unique name of the resource such as AWS SQS ARN in the form 'arn:aws:sqs:region:account_id:queue_name', or Pub/Sub subscription resource name in the form 'projects/{project}/subscriptions/{sub}'. */
		name: Option[String] = None,
	  /** Specifies the date and time that Storage Transfer Service starts listening for events from this stream. If no start time is specified or start time is in the past, Storage Transfer Service starts listening immediately. */
		eventStreamStartTime: Option[String] = None,
	  /** Specifies the data and time at which Storage Transfer Service stops listening for events from this stream. After this time, any transfers in progress will complete, but no new transfers are initiated. */
		eventStreamExpirationTime: Option[String] = None
	)
	
	case class UpdateTransferJobRequest(
	  /** Required. The ID of the Google Cloud project that owns the job. */
		projectId: Option[String] = None,
	  /** Required. The job to update. `transferJob` is expected to specify one or more of five fields: description, transfer_spec, notification_config, logging_config, and status. An `UpdateTransferJobRequest` that specifies other fields are rejected with the error INVALID_ARGUMENT. Updating a job status to DELETED requires `storagetransfer.jobs.delete` permission. */
		transferJob: Option[Schema.TransferJob] = None,
	  /** The field mask of the fields in `transferJob` that are to be updated in this request. Fields in `transferJob` that can be updated are: description, transfer_spec, notification_config, logging_config, and status. To update the `transfer_spec` of the job, a complete transfer specification must be provided. An incomplete specification missing any required fields is rejected with the error INVALID_ARGUMENT. */
		updateTransferJobFieldMask: Option[String] = None
	)
	
	case class ListTransferJobsResponse(
	  /** A list of transfer jobs. */
		transferJobs: Option[List[Schema.TransferJob]] = None,
	  /** The list next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class PauseTransferOperationRequest(
	
	)
	
	case class ResumeTransferOperationRequest(
	
	)
	
	case class RunTransferJobRequest(
	  /** Required. The ID of the Google Cloud project that owns the transfer job. */
		projectId: Option[String] = None
	)
	
	object AgentPool {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, CREATED, DELETING }
	}
	case class AgentPool(
	  /** Required. Specifies a unique string that identifies the agent pool. Format: `projects/{project_id}/agentPools/{agent_pool_id}` */
		name: Option[String] = None,
	  /** Specifies the client-specified AgentPool description. */
		displayName: Option[String] = None,
	  /** Output only. Specifies the state of the AgentPool. */
		state: Option[Schema.AgentPool.StateEnum] = None,
	  /** Specifies the bandwidth limit details. If this field is unspecified, the default value is set as 'No Limit'. */
		bandwidthLimit: Option[Schema.BandwidthLimit] = None
	)
	
	case class BandwidthLimit(
	  /** Bandwidth rate in megabytes per second, distributed across all the agents in the pool. */
		limitMbps: Option[String] = None
	)
	
	case class ListAgentPoolsResponse(
	  /** A list of agent pools. */
		agentPools: Option[List[Schema.AgentPool]] = None,
	  /** The list next page token. */
		nextPageToken: Option[String] = None
	)
	
	object TransferOperation {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, IN_PROGRESS, PAUSED, SUCCESS, FAILED, ABORTED, QUEUED, SUSPENDING }
	}
	case class TransferOperation(
	  /** A globally unique ID assigned by the system. */
		name: Option[String] = None,
	  /** The ID of the Google Cloud project that owns the operation. */
		projectId: Option[String] = None,
	  /** Transfer specification. */
		transferSpec: Option[Schema.TransferSpec] = None,
	  /** Notification configuration. */
		notificationConfig: Option[Schema.NotificationConfig] = None,
	  /** Cloud Logging configuration. */
		loggingConfig: Option[Schema.LoggingConfig] = None,
	  /** Start time of this transfer execution. */
		startTime: Option[String] = None,
	  /** End time of this transfer execution. */
		endTime: Option[String] = None,
	  /** Status of the transfer operation. */
		status: Option[Schema.TransferOperation.StatusEnum] = None,
	  /** Information about the progress of the transfer operation. */
		counters: Option[Schema.TransferCounters] = None,
	  /** Summarizes errors encountered with sample error log entries. */
		errorBreakdowns: Option[List[Schema.ErrorSummary]] = None,
	  /** The name of the transfer job that triggers this transfer operation. */
		transferJobName: Option[String] = None
	)
	
	case class TransferCounters(
	  /** Objects found in the data source that are scheduled to be transferred, excluding any that are filtered based on object conditions or skipped due to sync. */
		objectsFoundFromSource: Option[String] = None,
	  /** Bytes found in the data source that are scheduled to be transferred, excluding any that are filtered based on object conditions or skipped due to sync. */
		bytesFoundFromSource: Option[String] = None,
	  /** Objects found only in the data sink that are scheduled to be deleted. */
		objectsFoundOnlyFromSink: Option[String] = None,
	  /** Bytes found only in the data sink that are scheduled to be deleted. */
		bytesFoundOnlyFromSink: Option[String] = None,
	  /** Objects in the data source that are not transferred because they already exist in the data sink. */
		objectsFromSourceSkippedBySync: Option[String] = None,
	  /** Bytes in the data source that are not transferred because they already exist in the data sink. */
		bytesFromSourceSkippedBySync: Option[String] = None,
	  /** Objects that are copied to the data sink. */
		objectsCopiedToSink: Option[String] = None,
	  /** Bytes that are copied to the data sink. */
		bytesCopiedToSink: Option[String] = None,
	  /** Objects that are deleted from the data source. */
		objectsDeletedFromSource: Option[String] = None,
	  /** Bytes that are deleted from the data source. */
		bytesDeletedFromSource: Option[String] = None,
	  /** Objects that are deleted from the data sink. */
		objectsDeletedFromSink: Option[String] = None,
	  /** Bytes that are deleted from the data sink. */
		bytesDeletedFromSink: Option[String] = None,
	  /** Objects in the data source that failed to be transferred or that failed to be deleted after being transferred. */
		objectsFromSourceFailed: Option[String] = None,
	  /** Bytes in the data source that failed to be transferred or that failed to be deleted after being transferred. */
		bytesFromSourceFailed: Option[String] = None,
	  /** Objects that failed to be deleted from the data sink. */
		objectsFailedToDeleteFromSink: Option[String] = None,
	  /** Bytes that failed to be deleted from the data sink. */
		bytesFailedToDeleteFromSink: Option[String] = None,
	  /** For transfers involving PosixFilesystem only. Number of directories found while listing. For example, if the root directory of the transfer is `base/` and there are two other directories, `a/` and `b/` under this directory, the count after listing `base/`, `base/a/` and `base/b/` is 3. */
		directoriesFoundFromSource: Option[String] = None,
	  /** For transfers involving PosixFilesystem only. Number of listing failures for each directory found at the source. Potential failures when listing a directory include permission failure or block failure. If listing a directory fails, no files in the directory are transferred. */
		directoriesFailedToListFromSource: Option[String] = None,
	  /** For transfers involving PosixFilesystem only. Number of successful listings for each directory found at the source. */
		directoriesSuccessfullyListedFromSource: Option[String] = None,
	  /** Number of successfully cleaned up intermediate objects. */
		intermediateObjectsCleanedUp: Option[String] = None,
	  /** Number of intermediate objects failed cleaned up. */
		intermediateObjectsFailedCleanedUp: Option[String] = None
	)
	
	object ErrorSummary {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class ErrorSummary(
	  /** Required. */
		errorCode: Option[Schema.ErrorSummary.ErrorCodeEnum] = None,
	  /** Required. Count of this type of error. */
		errorCount: Option[String] = None,
	  /** Error samples. At most 5 error log entries are recorded for a given error code for a single transfer operation. */
		errorLogEntries: Option[List[Schema.ErrorLogEntry]] = None
	)
	
	case class ErrorLogEntry(
	  /** Required. A URL that refers to the target (a data source, a data sink, or an object) with which the error is associated. */
		url: Option[String] = None,
	  /** A list of messages that carry the error details. */
		errorDetails: Option[List[String]] = None
	)
}
