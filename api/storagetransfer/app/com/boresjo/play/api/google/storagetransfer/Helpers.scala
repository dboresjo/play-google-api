package com.boresjo.play.api.google.storagetransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaTransferSpec: Conversion[Schema.TransferSpec, Option[Schema.TransferSpec]] = (fun: Schema.TransferSpec) => Option(fun)
		given putSchemaReplicationSpec: Conversion[Schema.ReplicationSpec, Option[Schema.ReplicationSpec]] = (fun: Schema.ReplicationSpec) => Option(fun)
		given putSchemaNotificationConfig: Conversion[Schema.NotificationConfig, Option[Schema.NotificationConfig]] = (fun: Schema.NotificationConfig) => Option(fun)
		given putSchemaLoggingConfig: Conversion[Schema.LoggingConfig, Option[Schema.LoggingConfig]] = (fun: Schema.LoggingConfig) => Option(fun)
		given putSchemaSchedule: Conversion[Schema.Schedule, Option[Schema.Schedule]] = (fun: Schema.Schedule) => Option(fun)
		given putSchemaEventStream: Conversion[Schema.EventStream, Option[Schema.EventStream]] = (fun: Schema.EventStream) => Option(fun)
		given putSchemaTransferJobStatusEnum: Conversion[Schema.TransferJob.StatusEnum, Option[Schema.TransferJob.StatusEnum]] = (fun: Schema.TransferJob.StatusEnum) => Option(fun)
		given putSchemaGcsData: Conversion[Schema.GcsData, Option[Schema.GcsData]] = (fun: Schema.GcsData) => Option(fun)
		given putSchemaPosixFilesystem: Conversion[Schema.PosixFilesystem, Option[Schema.PosixFilesystem]] = (fun: Schema.PosixFilesystem) => Option(fun)
		given putSchemaAwsS3Data: Conversion[Schema.AwsS3Data, Option[Schema.AwsS3Data]] = (fun: Schema.AwsS3Data) => Option(fun)
		given putSchemaHttpData: Conversion[Schema.HttpData, Option[Schema.HttpData]] = (fun: Schema.HttpData) => Option(fun)
		given putSchemaAzureBlobStorageData: Conversion[Schema.AzureBlobStorageData, Option[Schema.AzureBlobStorageData]] = (fun: Schema.AzureBlobStorageData) => Option(fun)
		given putSchemaAwsS3CompatibleData: Conversion[Schema.AwsS3CompatibleData, Option[Schema.AwsS3CompatibleData]] = (fun: Schema.AwsS3CompatibleData) => Option(fun)
		given putSchemaHdfsData: Conversion[Schema.HdfsData, Option[Schema.HdfsData]] = (fun: Schema.HdfsData) => Option(fun)
		given putSchemaObjectConditions: Conversion[Schema.ObjectConditions, Option[Schema.ObjectConditions]] = (fun: Schema.ObjectConditions) => Option(fun)
		given putSchemaTransferOptions: Conversion[Schema.TransferOptions, Option[Schema.TransferOptions]] = (fun: Schema.TransferOptions) => Option(fun)
		given putSchemaTransferManifest: Conversion[Schema.TransferManifest, Option[Schema.TransferManifest]] = (fun: Schema.TransferManifest) => Option(fun)
		given putSchemaAwsAccessKey: Conversion[Schema.AwsAccessKey, Option[Schema.AwsAccessKey]] = (fun: Schema.AwsAccessKey) => Option(fun)
		given putSchemaAzureCredentials: Conversion[Schema.AzureCredentials, Option[Schema.AzureCredentials]] = (fun: Schema.AzureCredentials) => Option(fun)
		given putSchemaS3CompatibleMetadata: Conversion[Schema.S3CompatibleMetadata, Option[Schema.S3CompatibleMetadata]] = (fun: Schema.S3CompatibleMetadata) => Option(fun)
		given putSchemaS3CompatibleMetadataAuthMethodEnum: Conversion[Schema.S3CompatibleMetadata.AuthMethodEnum, Option[Schema.S3CompatibleMetadata.AuthMethodEnum]] = (fun: Schema.S3CompatibleMetadata.AuthMethodEnum) => Option(fun)
		given putSchemaS3CompatibleMetadataRequestModelEnum: Conversion[Schema.S3CompatibleMetadata.RequestModelEnum, Option[Schema.S3CompatibleMetadata.RequestModelEnum]] = (fun: Schema.S3CompatibleMetadata.RequestModelEnum) => Option(fun)
		given putSchemaS3CompatibleMetadataProtocolEnum: Conversion[Schema.S3CompatibleMetadata.ProtocolEnum, Option[Schema.S3CompatibleMetadata.ProtocolEnum]] = (fun: Schema.S3CompatibleMetadata.ProtocolEnum) => Option(fun)
		given putSchemaS3CompatibleMetadataListApiEnum: Conversion[Schema.S3CompatibleMetadata.ListApiEnum, Option[Schema.S3CompatibleMetadata.ListApiEnum]] = (fun: Schema.S3CompatibleMetadata.ListApiEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaTransferOptionsOverwriteWhenEnum: Conversion[Schema.TransferOptions.OverwriteWhenEnum, Option[Schema.TransferOptions.OverwriteWhenEnum]] = (fun: Schema.TransferOptions.OverwriteWhenEnum) => Option(fun)
		given putSchemaMetadataOptions: Conversion[Schema.MetadataOptions, Option[Schema.MetadataOptions]] = (fun: Schema.MetadataOptions) => Option(fun)
		given putSchemaMetadataOptionsSymlinkEnum: Conversion[Schema.MetadataOptions.SymlinkEnum, Option[Schema.MetadataOptions.SymlinkEnum]] = (fun: Schema.MetadataOptions.SymlinkEnum) => Option(fun)
		given putSchemaMetadataOptionsModeEnum: Conversion[Schema.MetadataOptions.ModeEnum, Option[Schema.MetadataOptions.ModeEnum]] = (fun: Schema.MetadataOptions.ModeEnum) => Option(fun)
		given putSchemaMetadataOptionsGidEnum: Conversion[Schema.MetadataOptions.GidEnum, Option[Schema.MetadataOptions.GidEnum]] = (fun: Schema.MetadataOptions.GidEnum) => Option(fun)
		given putSchemaMetadataOptionsUidEnum: Conversion[Schema.MetadataOptions.UidEnum, Option[Schema.MetadataOptions.UidEnum]] = (fun: Schema.MetadataOptions.UidEnum) => Option(fun)
		given putSchemaMetadataOptionsAclEnum: Conversion[Schema.MetadataOptions.AclEnum, Option[Schema.MetadataOptions.AclEnum]] = (fun: Schema.MetadataOptions.AclEnum) => Option(fun)
		given putSchemaMetadataOptionsStorageClassEnum: Conversion[Schema.MetadataOptions.StorageClassEnum, Option[Schema.MetadataOptions.StorageClassEnum]] = (fun: Schema.MetadataOptions.StorageClassEnum) => Option(fun)
		given putSchemaMetadataOptionsTemporaryHoldEnum: Conversion[Schema.MetadataOptions.TemporaryHoldEnum, Option[Schema.MetadataOptions.TemporaryHoldEnum]] = (fun: Schema.MetadataOptions.TemporaryHoldEnum) => Option(fun)
		given putSchemaMetadataOptionsKmsKeyEnum: Conversion[Schema.MetadataOptions.KmsKeyEnum, Option[Schema.MetadataOptions.KmsKeyEnum]] = (fun: Schema.MetadataOptions.KmsKeyEnum) => Option(fun)
		given putSchemaMetadataOptionsTimeCreatedEnum: Conversion[Schema.MetadataOptions.TimeCreatedEnum, Option[Schema.MetadataOptions.TimeCreatedEnum]] = (fun: Schema.MetadataOptions.TimeCreatedEnum) => Option(fun)
		given putListSchemaNotificationConfigEventTypesEnum: Conversion[List[Schema.NotificationConfig.EventTypesEnum], Option[List[Schema.NotificationConfig.EventTypesEnum]]] = (fun: List[Schema.NotificationConfig.EventTypesEnum]) => Option(fun)
		given putSchemaNotificationConfigPayloadFormatEnum: Conversion[Schema.NotificationConfig.PayloadFormatEnum, Option[Schema.NotificationConfig.PayloadFormatEnum]] = (fun: Schema.NotificationConfig.PayloadFormatEnum) => Option(fun)
		given putListSchemaLoggingConfigLogActionsEnum: Conversion[List[Schema.LoggingConfig.LogActionsEnum], Option[List[Schema.LoggingConfig.LogActionsEnum]]] = (fun: List[Schema.LoggingConfig.LogActionsEnum]) => Option(fun)
		given putListSchemaLoggingConfigLogActionStatesEnum: Conversion[List[Schema.LoggingConfig.LogActionStatesEnum], Option[List[Schema.LoggingConfig.LogActionStatesEnum]]] = (fun: List[Schema.LoggingConfig.LogActionStatesEnum]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaTransferJob: Conversion[Schema.TransferJob, Option[Schema.TransferJob]] = (fun: Schema.TransferJob) => Option(fun)
		given putListSchemaTransferJob: Conversion[List[Schema.TransferJob], Option[List[Schema.TransferJob]]] = (fun: List[Schema.TransferJob]) => Option(fun)
		given putSchemaAgentPoolStateEnum: Conversion[Schema.AgentPool.StateEnum, Option[Schema.AgentPool.StateEnum]] = (fun: Schema.AgentPool.StateEnum) => Option(fun)
		given putSchemaBandwidthLimit: Conversion[Schema.BandwidthLimit, Option[Schema.BandwidthLimit]] = (fun: Schema.BandwidthLimit) => Option(fun)
		given putListSchemaAgentPool: Conversion[List[Schema.AgentPool], Option[List[Schema.AgentPool]]] = (fun: List[Schema.AgentPool]) => Option(fun)
		given putSchemaTransferOperationStatusEnum: Conversion[Schema.TransferOperation.StatusEnum, Option[Schema.TransferOperation.StatusEnum]] = (fun: Schema.TransferOperation.StatusEnum) => Option(fun)
		given putSchemaTransferCounters: Conversion[Schema.TransferCounters, Option[Schema.TransferCounters]] = (fun: Schema.TransferCounters) => Option(fun)
		given putListSchemaErrorSummary: Conversion[List[Schema.ErrorSummary], Option[List[Schema.ErrorSummary]]] = (fun: List[Schema.ErrorSummary]) => Option(fun)
		given putSchemaErrorSummaryErrorCodeEnum: Conversion[Schema.ErrorSummary.ErrorCodeEnum, Option[Schema.ErrorSummary.ErrorCodeEnum]] = (fun: Schema.ErrorSummary.ErrorCodeEnum) => Option(fun)
		given putListSchemaErrorLogEntry: Conversion[List[Schema.ErrorLogEntry], Option[List[Schema.ErrorLogEntry]]] = (fun: List[Schema.ErrorLogEntry]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
