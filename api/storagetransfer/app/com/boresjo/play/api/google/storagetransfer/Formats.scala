package com.boresjo.play.api.google.storagetransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGoogleServiceAccount: Format[Schema.GoogleServiceAccount] = Json.format[Schema.GoogleServiceAccount]
	given fmtTransferJob: Format[Schema.TransferJob] = Json.format[Schema.TransferJob]
	given fmtTransferSpec: Format[Schema.TransferSpec] = Json.format[Schema.TransferSpec]
	given fmtReplicationSpec: Format[Schema.ReplicationSpec] = Json.format[Schema.ReplicationSpec]
	given fmtNotificationConfig: Format[Schema.NotificationConfig] = Json.format[Schema.NotificationConfig]
	given fmtLoggingConfig: Format[Schema.LoggingConfig] = Json.format[Schema.LoggingConfig]
	given fmtSchedule: Format[Schema.Schedule] = Json.format[Schema.Schedule]
	given fmtEventStream: Format[Schema.EventStream] = Json.format[Schema.EventStream]
	given fmtTransferJobStatusEnum: Format[Schema.TransferJob.StatusEnum] = JsonEnumFormat[Schema.TransferJob.StatusEnum]
	given fmtGcsData: Format[Schema.GcsData] = Json.format[Schema.GcsData]
	given fmtPosixFilesystem: Format[Schema.PosixFilesystem] = Json.format[Schema.PosixFilesystem]
	given fmtAwsS3Data: Format[Schema.AwsS3Data] = Json.format[Schema.AwsS3Data]
	given fmtHttpData: Format[Schema.HttpData] = Json.format[Schema.HttpData]
	given fmtAzureBlobStorageData: Format[Schema.AzureBlobStorageData] = Json.format[Schema.AzureBlobStorageData]
	given fmtAwsS3CompatibleData: Format[Schema.AwsS3CompatibleData] = Json.format[Schema.AwsS3CompatibleData]
	given fmtHdfsData: Format[Schema.HdfsData] = Json.format[Schema.HdfsData]
	given fmtObjectConditions: Format[Schema.ObjectConditions] = Json.format[Schema.ObjectConditions]
	given fmtTransferOptions: Format[Schema.TransferOptions] = Json.format[Schema.TransferOptions]
	given fmtTransferManifest: Format[Schema.TransferManifest] = Json.format[Schema.TransferManifest]
	given fmtAwsAccessKey: Format[Schema.AwsAccessKey] = Json.format[Schema.AwsAccessKey]
	given fmtAzureCredentials: Format[Schema.AzureCredentials] = Json.format[Schema.AzureCredentials]
	given fmtS3CompatibleMetadata: Format[Schema.S3CompatibleMetadata] = Json.format[Schema.S3CompatibleMetadata]
	given fmtS3CompatibleMetadataAuthMethodEnum: Format[Schema.S3CompatibleMetadata.AuthMethodEnum] = JsonEnumFormat[Schema.S3CompatibleMetadata.AuthMethodEnum]
	given fmtS3CompatibleMetadataRequestModelEnum: Format[Schema.S3CompatibleMetadata.RequestModelEnum] = JsonEnumFormat[Schema.S3CompatibleMetadata.RequestModelEnum]
	given fmtS3CompatibleMetadataProtocolEnum: Format[Schema.S3CompatibleMetadata.ProtocolEnum] = JsonEnumFormat[Schema.S3CompatibleMetadata.ProtocolEnum]
	given fmtS3CompatibleMetadataListApiEnum: Format[Schema.S3CompatibleMetadata.ListApiEnum] = JsonEnumFormat[Schema.S3CompatibleMetadata.ListApiEnum]
	given fmtTransferOptionsOverwriteWhenEnum: Format[Schema.TransferOptions.OverwriteWhenEnum] = JsonEnumFormat[Schema.TransferOptions.OverwriteWhenEnum]
	given fmtMetadataOptions: Format[Schema.MetadataOptions] = Json.format[Schema.MetadataOptions]
	given fmtMetadataOptionsSymlinkEnum: Format[Schema.MetadataOptions.SymlinkEnum] = JsonEnumFormat[Schema.MetadataOptions.SymlinkEnum]
	given fmtMetadataOptionsModeEnum: Format[Schema.MetadataOptions.ModeEnum] = JsonEnumFormat[Schema.MetadataOptions.ModeEnum]
	given fmtMetadataOptionsGidEnum: Format[Schema.MetadataOptions.GidEnum] = JsonEnumFormat[Schema.MetadataOptions.GidEnum]
	given fmtMetadataOptionsUidEnum: Format[Schema.MetadataOptions.UidEnum] = JsonEnumFormat[Schema.MetadataOptions.UidEnum]
	given fmtMetadataOptionsAclEnum: Format[Schema.MetadataOptions.AclEnum] = JsonEnumFormat[Schema.MetadataOptions.AclEnum]
	given fmtMetadataOptionsStorageClassEnum: Format[Schema.MetadataOptions.StorageClassEnum] = JsonEnumFormat[Schema.MetadataOptions.StorageClassEnum]
	given fmtMetadataOptionsTemporaryHoldEnum: Format[Schema.MetadataOptions.TemporaryHoldEnum] = JsonEnumFormat[Schema.MetadataOptions.TemporaryHoldEnum]
	given fmtMetadataOptionsKmsKeyEnum: Format[Schema.MetadataOptions.KmsKeyEnum] = JsonEnumFormat[Schema.MetadataOptions.KmsKeyEnum]
	given fmtMetadataOptionsTimeCreatedEnum: Format[Schema.MetadataOptions.TimeCreatedEnum] = JsonEnumFormat[Schema.MetadataOptions.TimeCreatedEnum]
	given fmtNotificationConfigEventTypesEnum: Format[Schema.NotificationConfig.EventTypesEnum] = JsonEnumFormat[Schema.NotificationConfig.EventTypesEnum]
	given fmtNotificationConfigPayloadFormatEnum: Format[Schema.NotificationConfig.PayloadFormatEnum] = JsonEnumFormat[Schema.NotificationConfig.PayloadFormatEnum]
	given fmtLoggingConfigLogActionsEnum: Format[Schema.LoggingConfig.LogActionsEnum] = JsonEnumFormat[Schema.LoggingConfig.LogActionsEnum]
	given fmtLoggingConfigLogActionStatesEnum: Format[Schema.LoggingConfig.LogActionStatesEnum] = JsonEnumFormat[Schema.LoggingConfig.LogActionStatesEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtUpdateTransferJobRequest: Format[Schema.UpdateTransferJobRequest] = Json.format[Schema.UpdateTransferJobRequest]
	given fmtListTransferJobsResponse: Format[Schema.ListTransferJobsResponse] = Json.format[Schema.ListTransferJobsResponse]
	given fmtPauseTransferOperationRequest: Format[Schema.PauseTransferOperationRequest] = Json.format[Schema.PauseTransferOperationRequest]
	given fmtResumeTransferOperationRequest: Format[Schema.ResumeTransferOperationRequest] = Json.format[Schema.ResumeTransferOperationRequest]
	given fmtRunTransferJobRequest: Format[Schema.RunTransferJobRequest] = Json.format[Schema.RunTransferJobRequest]
	given fmtAgentPool: Format[Schema.AgentPool] = Json.format[Schema.AgentPool]
	given fmtAgentPoolStateEnum: Format[Schema.AgentPool.StateEnum] = JsonEnumFormat[Schema.AgentPool.StateEnum]
	given fmtBandwidthLimit: Format[Schema.BandwidthLimit] = Json.format[Schema.BandwidthLimit]
	given fmtListAgentPoolsResponse: Format[Schema.ListAgentPoolsResponse] = Json.format[Schema.ListAgentPoolsResponse]
	given fmtTransferOperation: Format[Schema.TransferOperation] = Json.format[Schema.TransferOperation]
	given fmtTransferOperationStatusEnum: Format[Schema.TransferOperation.StatusEnum] = JsonEnumFormat[Schema.TransferOperation.StatusEnum]
	given fmtTransferCounters: Format[Schema.TransferCounters] = Json.format[Schema.TransferCounters]
	given fmtErrorSummary: Format[Schema.ErrorSummary] = Json.format[Schema.ErrorSummary]
	given fmtErrorSummaryErrorCodeEnum: Format[Schema.ErrorSummary.ErrorCodeEnum] = JsonEnumFormat[Schema.ErrorSummary.ErrorCodeEnum]
	given fmtErrorLogEntry: Format[Schema.ErrorLogEntry] = Json.format[Schema.ErrorLogEntry]
}
