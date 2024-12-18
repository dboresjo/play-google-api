package com.boresjo.play.api.google.firestore

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGoogleLongrunningCancelOperationRequest: Format[Schema.GoogleLongrunningCancelOperationRequest] = Json.format[Schema.GoogleLongrunningCancelOperationRequest]
	given fmtGoogleFirestoreAdminV1Index: Format[Schema.GoogleFirestoreAdminV1Index] = Json.format[Schema.GoogleFirestoreAdminV1Index]
	given fmtGoogleFirestoreAdminV1IndexQueryScopeEnum: Format[Schema.GoogleFirestoreAdminV1Index.QueryScopeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Index.QueryScopeEnum]
	given fmtGoogleFirestoreAdminV1IndexApiScopeEnum: Format[Schema.GoogleFirestoreAdminV1Index.ApiScopeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Index.ApiScopeEnum]
	given fmtGoogleFirestoreAdminV1IndexField: Format[Schema.GoogleFirestoreAdminV1IndexField] = Json.format[Schema.GoogleFirestoreAdminV1IndexField]
	given fmtGoogleFirestoreAdminV1IndexStateEnum: Format[Schema.GoogleFirestoreAdminV1Index.StateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Index.StateEnum]
	given fmtGoogleFirestoreAdminV1IndexFieldOrderEnum: Format[Schema.GoogleFirestoreAdminV1IndexField.OrderEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1IndexField.OrderEnum]
	given fmtGoogleFirestoreAdminV1IndexFieldArrayConfigEnum: Format[Schema.GoogleFirestoreAdminV1IndexField.ArrayConfigEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1IndexField.ArrayConfigEnum]
	given fmtGoogleFirestoreAdminV1VectorConfig: Format[Schema.GoogleFirestoreAdminV1VectorConfig] = Json.format[Schema.GoogleFirestoreAdminV1VectorConfig]
	given fmtGoogleFirestoreAdminV1FlatIndex: Format[Schema.GoogleFirestoreAdminV1FlatIndex] = Json.format[Schema.GoogleFirestoreAdminV1FlatIndex]
	given fmtGoogleFirestoreAdminV1ListIndexesResponse: Format[Schema.GoogleFirestoreAdminV1ListIndexesResponse] = Json.format[Schema.GoogleFirestoreAdminV1ListIndexesResponse]
	given fmtGoogleFirestoreAdminV1Field: Format[Schema.GoogleFirestoreAdminV1Field] = Json.format[Schema.GoogleFirestoreAdminV1Field]
	given fmtGoogleFirestoreAdminV1IndexConfig: Format[Schema.GoogleFirestoreAdminV1IndexConfig] = Json.format[Schema.GoogleFirestoreAdminV1IndexConfig]
	given fmtGoogleFirestoreAdminV1TtlConfig: Format[Schema.GoogleFirestoreAdminV1TtlConfig] = Json.format[Schema.GoogleFirestoreAdminV1TtlConfig]
	given fmtGoogleFirestoreAdminV1TtlConfigStateEnum: Format[Schema.GoogleFirestoreAdminV1TtlConfig.StateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1TtlConfig.StateEnum]
	given fmtGoogleFirestoreAdminV1ListFieldsResponse: Format[Schema.GoogleFirestoreAdminV1ListFieldsResponse] = Json.format[Schema.GoogleFirestoreAdminV1ListFieldsResponse]
	given fmtGoogleFirestoreAdminV1ExportDocumentsRequest: Format[Schema.GoogleFirestoreAdminV1ExportDocumentsRequest] = Json.format[Schema.GoogleFirestoreAdminV1ExportDocumentsRequest]
	given fmtGoogleFirestoreAdminV1ImportDocumentsRequest: Format[Schema.GoogleFirestoreAdminV1ImportDocumentsRequest] = Json.format[Schema.GoogleFirestoreAdminV1ImportDocumentsRequest]
	given fmtGoogleFirestoreAdminV1BulkDeleteDocumentsRequest: Format[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsRequest] = Json.format[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsRequest]
	given fmtGoogleFirestoreAdminV1Database: Format[Schema.GoogleFirestoreAdminV1Database] = Json.format[Schema.GoogleFirestoreAdminV1Database]
	given fmtGoogleFirestoreAdminV1DatabaseTypeEnum: Format[Schema.GoogleFirestoreAdminV1Database.TypeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Database.TypeEnum]
	given fmtGoogleFirestoreAdminV1DatabaseConcurrencyModeEnum: Format[Schema.GoogleFirestoreAdminV1Database.ConcurrencyModeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Database.ConcurrencyModeEnum]
	given fmtGoogleFirestoreAdminV1DatabasePointInTimeRecoveryEnablementEnum: Format[Schema.GoogleFirestoreAdminV1Database.PointInTimeRecoveryEnablementEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Database.PointInTimeRecoveryEnablementEnum]
	given fmtGoogleFirestoreAdminV1DatabaseAppEngineIntegrationModeEnum: Format[Schema.GoogleFirestoreAdminV1Database.AppEngineIntegrationModeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Database.AppEngineIntegrationModeEnum]
	given fmtGoogleFirestoreAdminV1DatabaseDeleteProtectionStateEnum: Format[Schema.GoogleFirestoreAdminV1Database.DeleteProtectionStateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Database.DeleteProtectionStateEnum]
	given fmtGoogleFirestoreAdminV1CmekConfig: Format[Schema.GoogleFirestoreAdminV1CmekConfig] = Json.format[Schema.GoogleFirestoreAdminV1CmekConfig]
	given fmtGoogleFirestoreAdminV1SourceInfo: Format[Schema.GoogleFirestoreAdminV1SourceInfo] = Json.format[Schema.GoogleFirestoreAdminV1SourceInfo]
	given fmtGoogleFirestoreAdminV1BackupSource: Format[Schema.GoogleFirestoreAdminV1BackupSource] = Json.format[Schema.GoogleFirestoreAdminV1BackupSource]
	given fmtGoogleFirestoreAdminV1ListDatabasesResponse: Format[Schema.GoogleFirestoreAdminV1ListDatabasesResponse] = Json.format[Schema.GoogleFirestoreAdminV1ListDatabasesResponse]
	given fmtGoogleFirestoreAdminV1Backup: Format[Schema.GoogleFirestoreAdminV1Backup] = Json.format[Schema.GoogleFirestoreAdminV1Backup]
	given fmtGoogleFirestoreAdminV1Stats: Format[Schema.GoogleFirestoreAdminV1Stats] = Json.format[Schema.GoogleFirestoreAdminV1Stats]
	given fmtGoogleFirestoreAdminV1BackupStateEnum: Format[Schema.GoogleFirestoreAdminV1Backup.StateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1Backup.StateEnum]
	given fmtGoogleFirestoreAdminV1ListBackupsResponse: Format[Schema.GoogleFirestoreAdminV1ListBackupsResponse] = Json.format[Schema.GoogleFirestoreAdminV1ListBackupsResponse]
	given fmtGoogleFirestoreAdminV1RestoreDatabaseRequest: Format[Schema.GoogleFirestoreAdminV1RestoreDatabaseRequest] = Json.format[Schema.GoogleFirestoreAdminV1RestoreDatabaseRequest]
	given fmtGoogleFirestoreAdminV1EncryptionConfig: Format[Schema.GoogleFirestoreAdminV1EncryptionConfig] = Json.format[Schema.GoogleFirestoreAdminV1EncryptionConfig]
	given fmtGoogleFirestoreAdminV1GoogleDefaultEncryptionOptions: Format[Schema.GoogleFirestoreAdminV1GoogleDefaultEncryptionOptions] = Json.format[Schema.GoogleFirestoreAdminV1GoogleDefaultEncryptionOptions]
	given fmtGoogleFirestoreAdminV1SourceEncryptionOptions: Format[Schema.GoogleFirestoreAdminV1SourceEncryptionOptions] = Json.format[Schema.GoogleFirestoreAdminV1SourceEncryptionOptions]
	given fmtGoogleFirestoreAdminV1CustomerManagedEncryptionOptions: Format[Schema.GoogleFirestoreAdminV1CustomerManagedEncryptionOptions] = Json.format[Schema.GoogleFirestoreAdminV1CustomerManagedEncryptionOptions]
	given fmtGoogleFirestoreAdminV1BackupSchedule: Format[Schema.GoogleFirestoreAdminV1BackupSchedule] = Json.format[Schema.GoogleFirestoreAdminV1BackupSchedule]
	given fmtGoogleFirestoreAdminV1DailyRecurrence: Format[Schema.GoogleFirestoreAdminV1DailyRecurrence] = Json.format[Schema.GoogleFirestoreAdminV1DailyRecurrence]
	given fmtGoogleFirestoreAdminV1WeeklyRecurrence: Format[Schema.GoogleFirestoreAdminV1WeeklyRecurrence] = Json.format[Schema.GoogleFirestoreAdminV1WeeklyRecurrence]
	given fmtGoogleFirestoreAdminV1WeeklyRecurrenceDayEnum: Format[Schema.GoogleFirestoreAdminV1WeeklyRecurrence.DayEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1WeeklyRecurrence.DayEnum]
	given fmtGoogleFirestoreAdminV1ListBackupSchedulesResponse: Format[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse] = Json.format[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse]
	given fmtDocument: Format[Schema.Document] = Json.format[Schema.Document]
	given fmtValue: Format[Schema.Value] = Json.format[Schema.Value]
	given fmtValueNullValueEnum: Format[Schema.Value.NullValueEnum] = JsonEnumFormat[Schema.Value.NullValueEnum]
	given fmtLatLng: Format[Schema.LatLng] = Json.format[Schema.LatLng]
	given fmtArrayValue: Format[Schema.ArrayValue] = Json.format[Schema.ArrayValue]
	given fmtMapValue: Format[Schema.MapValue] = Json.format[Schema.MapValue]
	given fmtListDocumentsResponse: Format[Schema.ListDocumentsResponse] = Json.format[Schema.ListDocumentsResponse]
	given fmtBatchGetDocumentsRequest: Format[Schema.BatchGetDocumentsRequest] = Json.format[Schema.BatchGetDocumentsRequest]
	given fmtDocumentMask: Format[Schema.DocumentMask] = Json.format[Schema.DocumentMask]
	given fmtTransactionOptions: Format[Schema.TransactionOptions] = Json.format[Schema.TransactionOptions]
	given fmtReadOnly: Format[Schema.ReadOnly] = Json.format[Schema.ReadOnly]
	given fmtReadWrite: Format[Schema.ReadWrite] = Json.format[Schema.ReadWrite]
	given fmtBatchGetDocumentsResponse: Format[Schema.BatchGetDocumentsResponse] = Json.format[Schema.BatchGetDocumentsResponse]
	given fmtBeginTransactionRequest: Format[Schema.BeginTransactionRequest] = Json.format[Schema.BeginTransactionRequest]
	given fmtBeginTransactionResponse: Format[Schema.BeginTransactionResponse] = Json.format[Schema.BeginTransactionResponse]
	given fmtCommitRequest: Format[Schema.CommitRequest] = Json.format[Schema.CommitRequest]
	given fmtWrite: Format[Schema.Write] = Json.format[Schema.Write]
	given fmtDocumentTransform: Format[Schema.DocumentTransform] = Json.format[Schema.DocumentTransform]
	given fmtFieldTransform: Format[Schema.FieldTransform] = Json.format[Schema.FieldTransform]
	given fmtPrecondition: Format[Schema.Precondition] = Json.format[Schema.Precondition]
	given fmtFieldTransformSetToServerValueEnum: Format[Schema.FieldTransform.SetToServerValueEnum] = JsonEnumFormat[Schema.FieldTransform.SetToServerValueEnum]
	given fmtCommitResponse: Format[Schema.CommitResponse] = Json.format[Schema.CommitResponse]
	given fmtWriteResult: Format[Schema.WriteResult] = Json.format[Schema.WriteResult]
	given fmtRollbackRequest: Format[Schema.RollbackRequest] = Json.format[Schema.RollbackRequest]
	given fmtRunQueryRequest: Format[Schema.RunQueryRequest] = Json.format[Schema.RunQueryRequest]
	given fmtStructuredQuery: Format[Schema.StructuredQuery] = Json.format[Schema.StructuredQuery]
	given fmtExplainOptions: Format[Schema.ExplainOptions] = Json.format[Schema.ExplainOptions]
	given fmtProjection: Format[Schema.Projection] = Json.format[Schema.Projection]
	given fmtCollectionSelector: Format[Schema.CollectionSelector] = Json.format[Schema.CollectionSelector]
	given fmtFilter: Format[Schema.Filter] = Json.format[Schema.Filter]
	given fmtOrder: Format[Schema.Order] = Json.format[Schema.Order]
	given fmtCursor: Format[Schema.Cursor] = Json.format[Schema.Cursor]
	given fmtFindNearest: Format[Schema.FindNearest] = Json.format[Schema.FindNearest]
	given fmtFieldReference: Format[Schema.FieldReference] = Json.format[Schema.FieldReference]
	given fmtCompositeFilter: Format[Schema.CompositeFilter] = Json.format[Schema.CompositeFilter]
	given fmtFieldFilter: Format[Schema.FieldFilter] = Json.format[Schema.FieldFilter]
	given fmtUnaryFilter: Format[Schema.UnaryFilter] = Json.format[Schema.UnaryFilter]
	given fmtCompositeFilterOpEnum: Format[Schema.CompositeFilter.OpEnum] = JsonEnumFormat[Schema.CompositeFilter.OpEnum]
	given fmtFieldFilterOpEnum: Format[Schema.FieldFilter.OpEnum] = JsonEnumFormat[Schema.FieldFilter.OpEnum]
	given fmtUnaryFilterOpEnum: Format[Schema.UnaryFilter.OpEnum] = JsonEnumFormat[Schema.UnaryFilter.OpEnum]
	given fmtOrderDirectionEnum: Format[Schema.Order.DirectionEnum] = JsonEnumFormat[Schema.Order.DirectionEnum]
	given fmtFindNearestDistanceMeasureEnum: Format[Schema.FindNearest.DistanceMeasureEnum] = JsonEnumFormat[Schema.FindNearest.DistanceMeasureEnum]
	given fmtRunQueryResponse: Format[Schema.RunQueryResponse] = Json.format[Schema.RunQueryResponse]
	given fmtExplainMetrics: Format[Schema.ExplainMetrics] = Json.format[Schema.ExplainMetrics]
	given fmtPlanSummary: Format[Schema.PlanSummary] = Json.format[Schema.PlanSummary]
	given fmtExecutionStats: Format[Schema.ExecutionStats] = Json.format[Schema.ExecutionStats]
	given fmtRunAggregationQueryRequest: Format[Schema.RunAggregationQueryRequest] = Json.format[Schema.RunAggregationQueryRequest]
	given fmtStructuredAggregationQuery: Format[Schema.StructuredAggregationQuery] = Json.format[Schema.StructuredAggregationQuery]
	given fmtAggregation: Format[Schema.Aggregation] = Json.format[Schema.Aggregation]
	given fmtCount: Format[Schema.Count] = Json.format[Schema.Count]
	given fmtSum: Format[Schema.Sum] = Json.format[Schema.Sum]
	given fmtAvg: Format[Schema.Avg] = Json.format[Schema.Avg]
	given fmtRunAggregationQueryResponse: Format[Schema.RunAggregationQueryResponse] = Json.format[Schema.RunAggregationQueryResponse]
	given fmtAggregationResult: Format[Schema.AggregationResult] = Json.format[Schema.AggregationResult]
	given fmtPartitionQueryRequest: Format[Schema.PartitionQueryRequest] = Json.format[Schema.PartitionQueryRequest]
	given fmtPartitionQueryResponse: Format[Schema.PartitionQueryResponse] = Json.format[Schema.PartitionQueryResponse]
	given fmtWriteRequest: Format[Schema.WriteRequest] = Json.format[Schema.WriteRequest]
	given fmtWriteResponse: Format[Schema.WriteResponse] = Json.format[Schema.WriteResponse]
	given fmtListenRequest: Format[Schema.ListenRequest] = Json.format[Schema.ListenRequest]
	given fmtTarget: Format[Schema.Target] = Json.format[Schema.Target]
	given fmtQueryTarget: Format[Schema.QueryTarget] = Json.format[Schema.QueryTarget]
	given fmtDocumentsTarget: Format[Schema.DocumentsTarget] = Json.format[Schema.DocumentsTarget]
	given fmtListenResponse: Format[Schema.ListenResponse] = Json.format[Schema.ListenResponse]
	given fmtTargetChange: Format[Schema.TargetChange] = Json.format[Schema.TargetChange]
	given fmtDocumentChange: Format[Schema.DocumentChange] = Json.format[Schema.DocumentChange]
	given fmtDocumentDelete: Format[Schema.DocumentDelete] = Json.format[Schema.DocumentDelete]
	given fmtDocumentRemove: Format[Schema.DocumentRemove] = Json.format[Schema.DocumentRemove]
	given fmtExistenceFilter: Format[Schema.ExistenceFilter] = Json.format[Schema.ExistenceFilter]
	given fmtTargetChangeTargetChangeTypeEnum: Format[Schema.TargetChange.TargetChangeTypeEnum] = JsonEnumFormat[Schema.TargetChange.TargetChangeTypeEnum]
	given fmtBloomFilter: Format[Schema.BloomFilter] = Json.format[Schema.BloomFilter]
	given fmtBitSequence: Format[Schema.BitSequence] = Json.format[Schema.BitSequence]
	given fmtListCollectionIdsRequest: Format[Schema.ListCollectionIdsRequest] = Json.format[Schema.ListCollectionIdsRequest]
	given fmtListCollectionIdsResponse: Format[Schema.ListCollectionIdsResponse] = Json.format[Schema.ListCollectionIdsResponse]
	given fmtBatchWriteRequest: Format[Schema.BatchWriteRequest] = Json.format[Schema.BatchWriteRequest]
	given fmtBatchWriteResponse: Format[Schema.BatchWriteResponse] = Json.format[Schema.BatchWriteResponse]
	given fmtGoogleFirestoreAdminV1FieldOperationMetadata: Format[Schema.GoogleFirestoreAdminV1FieldOperationMetadata] = Json.format[Schema.GoogleFirestoreAdminV1FieldOperationMetadata]
	given fmtGoogleFirestoreAdminV1IndexConfigDelta: Format[Schema.GoogleFirestoreAdminV1IndexConfigDelta] = Json.format[Schema.GoogleFirestoreAdminV1IndexConfigDelta]
	given fmtGoogleFirestoreAdminV1FieldOperationMetadataStateEnum: Format[Schema.GoogleFirestoreAdminV1FieldOperationMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1FieldOperationMetadata.StateEnum]
	given fmtGoogleFirestoreAdminV1Progress: Format[Schema.GoogleFirestoreAdminV1Progress] = Json.format[Schema.GoogleFirestoreAdminV1Progress]
	given fmtGoogleFirestoreAdminV1TtlConfigDelta: Format[Schema.GoogleFirestoreAdminV1TtlConfigDelta] = Json.format[Schema.GoogleFirestoreAdminV1TtlConfigDelta]
	given fmtGoogleFirestoreAdminV1IndexConfigDeltaChangeTypeEnum: Format[Schema.GoogleFirestoreAdminV1IndexConfigDelta.ChangeTypeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1IndexConfigDelta.ChangeTypeEnum]
	given fmtGoogleFirestoreAdminV1TtlConfigDeltaChangeTypeEnum: Format[Schema.GoogleFirestoreAdminV1TtlConfigDelta.ChangeTypeEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1TtlConfigDelta.ChangeTypeEnum]
	given fmtGoogleFirestoreAdminV1IndexOperationMetadata: Format[Schema.GoogleFirestoreAdminV1IndexOperationMetadata] = Json.format[Schema.GoogleFirestoreAdminV1IndexOperationMetadata]
	given fmtGoogleFirestoreAdminV1IndexOperationMetadataStateEnum: Format[Schema.GoogleFirestoreAdminV1IndexOperationMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1IndexOperationMetadata.StateEnum]
	given fmtGoogleFirestoreAdminV1LocationMetadata: Format[Schema.GoogleFirestoreAdminV1LocationMetadata] = Json.format[Schema.GoogleFirestoreAdminV1LocationMetadata]
	given fmtGoogleFirestoreAdminV1ExportDocumentsMetadata: Format[Schema.GoogleFirestoreAdminV1ExportDocumentsMetadata] = Json.format[Schema.GoogleFirestoreAdminV1ExportDocumentsMetadata]
	given fmtGoogleFirestoreAdminV1ExportDocumentsMetadataOperationStateEnum: Format[Schema.GoogleFirestoreAdminV1ExportDocumentsMetadata.OperationStateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1ExportDocumentsMetadata.OperationStateEnum]
	given fmtGoogleFirestoreAdminV1BulkDeleteDocumentsMetadata: Format[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata] = Json.format[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata]
	given fmtGoogleFirestoreAdminV1BulkDeleteDocumentsMetadataOperationStateEnum: Format[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata.OperationStateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata.OperationStateEnum]
	given fmtGoogleFirestoreAdminV1ImportDocumentsMetadata: Format[Schema.GoogleFirestoreAdminV1ImportDocumentsMetadata] = Json.format[Schema.GoogleFirestoreAdminV1ImportDocumentsMetadata]
	given fmtGoogleFirestoreAdminV1ImportDocumentsMetadataOperationStateEnum: Format[Schema.GoogleFirestoreAdminV1ImportDocumentsMetadata.OperationStateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1ImportDocumentsMetadata.OperationStateEnum]
	given fmtGoogleFirestoreAdminV1ExportDocumentsResponse: Format[Schema.GoogleFirestoreAdminV1ExportDocumentsResponse] = Json.format[Schema.GoogleFirestoreAdminV1ExportDocumentsResponse]
	given fmtGoogleFirestoreAdminV1CreateDatabaseMetadata: Format[Schema.GoogleFirestoreAdminV1CreateDatabaseMetadata] = Json.format[Schema.GoogleFirestoreAdminV1CreateDatabaseMetadata]
	given fmtGoogleFirestoreAdminV1DeleteDatabaseMetadata: Format[Schema.GoogleFirestoreAdminV1DeleteDatabaseMetadata] = Json.format[Schema.GoogleFirestoreAdminV1DeleteDatabaseMetadata]
	given fmtGoogleFirestoreAdminV1UpdateDatabaseMetadata: Format[Schema.GoogleFirestoreAdminV1UpdateDatabaseMetadata] = Json.format[Schema.GoogleFirestoreAdminV1UpdateDatabaseMetadata]
	given fmtGoogleFirestoreAdminV1RestoreDatabaseMetadata: Format[Schema.GoogleFirestoreAdminV1RestoreDatabaseMetadata] = Json.format[Schema.GoogleFirestoreAdminV1RestoreDatabaseMetadata]
	given fmtGoogleFirestoreAdminV1RestoreDatabaseMetadataOperationStateEnum: Format[Schema.GoogleFirestoreAdminV1RestoreDatabaseMetadata.OperationStateEnum] = JsonEnumFormat[Schema.GoogleFirestoreAdminV1RestoreDatabaseMetadata.OperationStateEnum]
}
