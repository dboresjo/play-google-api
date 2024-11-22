package com.boresjo.play.api.google.datastore

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGoogleDatastoreAdminV1ExportEntitiesRequest: Format[Schema.GoogleDatastoreAdminV1ExportEntitiesRequest] = Json.format[Schema.GoogleDatastoreAdminV1ExportEntitiesRequest]
	given fmtGoogleDatastoreAdminV1EntityFilter: Format[Schema.GoogleDatastoreAdminV1EntityFilter] = Json.format[Schema.GoogleDatastoreAdminV1EntityFilter]
	given fmtGoogleDatastoreAdminV1ImportEntitiesRequest: Format[Schema.GoogleDatastoreAdminV1ImportEntitiesRequest] = Json.format[Schema.GoogleDatastoreAdminV1ImportEntitiesRequest]
	given fmtGoogleDatastoreAdminV1Index: Format[Schema.GoogleDatastoreAdminV1Index] = Json.format[Schema.GoogleDatastoreAdminV1Index]
	given fmtGoogleDatastoreAdminV1IndexAncestorEnum: Format[Schema.GoogleDatastoreAdminV1Index.AncestorEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1Index.AncestorEnum]
	given fmtGoogleDatastoreAdminV1IndexedProperty: Format[Schema.GoogleDatastoreAdminV1IndexedProperty] = Json.format[Schema.GoogleDatastoreAdminV1IndexedProperty]
	given fmtGoogleDatastoreAdminV1IndexStateEnum: Format[Schema.GoogleDatastoreAdminV1Index.StateEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1Index.StateEnum]
	given fmtGoogleDatastoreAdminV1IndexedPropertyDirectionEnum: Format[Schema.GoogleDatastoreAdminV1IndexedProperty.DirectionEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1IndexedProperty.DirectionEnum]
	given fmtGoogleDatastoreAdminV1ListIndexesResponse: Format[Schema.GoogleDatastoreAdminV1ListIndexesResponse] = Json.format[Schema.GoogleDatastoreAdminV1ListIndexesResponse]
	given fmtLookupRequest: Format[Schema.LookupRequest] = Json.format[Schema.LookupRequest]
	given fmtReadOptions: Format[Schema.ReadOptions] = Json.format[Schema.ReadOptions]
	given fmtKey: Format[Schema.Key] = Json.format[Schema.Key]
	given fmtPropertyMask: Format[Schema.PropertyMask] = Json.format[Schema.PropertyMask]
	given fmtReadOptionsReadConsistencyEnum: Format[Schema.ReadOptions.ReadConsistencyEnum] = JsonEnumFormat[Schema.ReadOptions.ReadConsistencyEnum]
	given fmtTransactionOptions: Format[Schema.TransactionOptions] = Json.format[Schema.TransactionOptions]
	given fmtReadWrite: Format[Schema.ReadWrite] = Json.format[Schema.ReadWrite]
	given fmtReadOnly: Format[Schema.ReadOnly] = Json.format[Schema.ReadOnly]
	given fmtPartitionId: Format[Schema.PartitionId] = Json.format[Schema.PartitionId]
	given fmtPathElement: Format[Schema.PathElement] = Json.format[Schema.PathElement]
	given fmtLookupResponse: Format[Schema.LookupResponse] = Json.format[Schema.LookupResponse]
	given fmtEntityResult: Format[Schema.EntityResult] = Json.format[Schema.EntityResult]
	given fmtEntity: Format[Schema.Entity] = Json.format[Schema.Entity]
	given fmtValue: Format[Schema.Value] = Json.format[Schema.Value]
	given fmtValueNullValueEnum: Format[Schema.Value.NullValueEnum] = JsonEnumFormat[Schema.Value.NullValueEnum]
	given fmtLatLng: Format[Schema.LatLng] = Json.format[Schema.LatLng]
	given fmtArrayValue: Format[Schema.ArrayValue] = Json.format[Schema.ArrayValue]
	given fmtRunQueryRequest: Format[Schema.RunQueryRequest] = Json.format[Schema.RunQueryRequest]
	given fmtQuery: Format[Schema.Query] = Json.format[Schema.Query]
	given fmtGqlQuery: Format[Schema.GqlQuery] = Json.format[Schema.GqlQuery]
	given fmtExplainOptions: Format[Schema.ExplainOptions] = Json.format[Schema.ExplainOptions]
	given fmtProjection: Format[Schema.Projection] = Json.format[Schema.Projection]
	given fmtKindExpression: Format[Schema.KindExpression] = Json.format[Schema.KindExpression]
	given fmtFilter: Format[Schema.Filter] = Json.format[Schema.Filter]
	given fmtPropertyOrder: Format[Schema.PropertyOrder] = Json.format[Schema.PropertyOrder]
	given fmtPropertyReference: Format[Schema.PropertyReference] = Json.format[Schema.PropertyReference]
	given fmtFindNearest: Format[Schema.FindNearest] = Json.format[Schema.FindNearest]
	given fmtCompositeFilter: Format[Schema.CompositeFilter] = Json.format[Schema.CompositeFilter]
	given fmtPropertyFilter: Format[Schema.PropertyFilter] = Json.format[Schema.PropertyFilter]
	given fmtCompositeFilterOpEnum: Format[Schema.CompositeFilter.OpEnum] = JsonEnumFormat[Schema.CompositeFilter.OpEnum]
	given fmtPropertyFilterOpEnum: Format[Schema.PropertyFilter.OpEnum] = JsonEnumFormat[Schema.PropertyFilter.OpEnum]
	given fmtPropertyOrderDirectionEnum: Format[Schema.PropertyOrder.DirectionEnum] = JsonEnumFormat[Schema.PropertyOrder.DirectionEnum]
	given fmtFindNearestDistanceMeasureEnum: Format[Schema.FindNearest.DistanceMeasureEnum] = JsonEnumFormat[Schema.FindNearest.DistanceMeasureEnum]
	given fmtGqlQueryParameter: Format[Schema.GqlQueryParameter] = Json.format[Schema.GqlQueryParameter]
	given fmtRunQueryResponse: Format[Schema.RunQueryResponse] = Json.format[Schema.RunQueryResponse]
	given fmtQueryResultBatch: Format[Schema.QueryResultBatch] = Json.format[Schema.QueryResultBatch]
	given fmtExplainMetrics: Format[Schema.ExplainMetrics] = Json.format[Schema.ExplainMetrics]
	given fmtQueryResultBatchEntityResultTypeEnum: Format[Schema.QueryResultBatch.EntityResultTypeEnum] = JsonEnumFormat[Schema.QueryResultBatch.EntityResultTypeEnum]
	given fmtQueryResultBatchMoreResultsEnum: Format[Schema.QueryResultBatch.MoreResultsEnum] = JsonEnumFormat[Schema.QueryResultBatch.MoreResultsEnum]
	given fmtPlanSummary: Format[Schema.PlanSummary] = Json.format[Schema.PlanSummary]
	given fmtExecutionStats: Format[Schema.ExecutionStats] = Json.format[Schema.ExecutionStats]
	given fmtRunAggregationQueryRequest: Format[Schema.RunAggregationQueryRequest] = Json.format[Schema.RunAggregationQueryRequest]
	given fmtAggregationQuery: Format[Schema.AggregationQuery] = Json.format[Schema.AggregationQuery]
	given fmtAggregation: Format[Schema.Aggregation] = Json.format[Schema.Aggregation]
	given fmtCount: Format[Schema.Count] = Json.format[Schema.Count]
	given fmtSum: Format[Schema.Sum] = Json.format[Schema.Sum]
	given fmtAvg: Format[Schema.Avg] = Json.format[Schema.Avg]
	given fmtRunAggregationQueryResponse: Format[Schema.RunAggregationQueryResponse] = Json.format[Schema.RunAggregationQueryResponse]
	given fmtAggregationResultBatch: Format[Schema.AggregationResultBatch] = Json.format[Schema.AggregationResultBatch]
	given fmtAggregationResult: Format[Schema.AggregationResult] = Json.format[Schema.AggregationResult]
	given fmtAggregationResultBatchMoreResultsEnum: Format[Schema.AggregationResultBatch.MoreResultsEnum] = JsonEnumFormat[Schema.AggregationResultBatch.MoreResultsEnum]
	given fmtBeginTransactionRequest: Format[Schema.BeginTransactionRequest] = Json.format[Schema.BeginTransactionRequest]
	given fmtBeginTransactionResponse: Format[Schema.BeginTransactionResponse] = Json.format[Schema.BeginTransactionResponse]
	given fmtCommitRequest: Format[Schema.CommitRequest] = Json.format[Schema.CommitRequest]
	given fmtCommitRequestModeEnum: Format[Schema.CommitRequest.ModeEnum] = JsonEnumFormat[Schema.CommitRequest.ModeEnum]
	given fmtMutation: Format[Schema.Mutation] = Json.format[Schema.Mutation]
	given fmtMutationConflictResolutionStrategyEnum: Format[Schema.Mutation.ConflictResolutionStrategyEnum] = JsonEnumFormat[Schema.Mutation.ConflictResolutionStrategyEnum]
	given fmtPropertyTransform: Format[Schema.PropertyTransform] = Json.format[Schema.PropertyTransform]
	given fmtPropertyTransformSetToServerValueEnum: Format[Schema.PropertyTransform.SetToServerValueEnum] = JsonEnumFormat[Schema.PropertyTransform.SetToServerValueEnum]
	given fmtCommitResponse: Format[Schema.CommitResponse] = Json.format[Schema.CommitResponse]
	given fmtMutationResult: Format[Schema.MutationResult] = Json.format[Schema.MutationResult]
	given fmtRollbackRequest: Format[Schema.RollbackRequest] = Json.format[Schema.RollbackRequest]
	given fmtRollbackResponse: Format[Schema.RollbackResponse] = Json.format[Schema.RollbackResponse]
	given fmtAllocateIdsRequest: Format[Schema.AllocateIdsRequest] = Json.format[Schema.AllocateIdsRequest]
	given fmtAllocateIdsResponse: Format[Schema.AllocateIdsResponse] = Json.format[Schema.AllocateIdsResponse]
	given fmtReserveIdsRequest: Format[Schema.ReserveIdsRequest] = Json.format[Schema.ReserveIdsRequest]
	given fmtReserveIdsResponse: Format[Schema.ReserveIdsResponse] = Json.format[Schema.ReserveIdsResponse]
	given fmtGoogleDatastoreAdminV1beta1ExportEntitiesMetadata: Format[Schema.GoogleDatastoreAdminV1beta1ExportEntitiesMetadata] = Json.format[Schema.GoogleDatastoreAdminV1beta1ExportEntitiesMetadata]
	given fmtGoogleDatastoreAdminV1beta1CommonMetadata: Format[Schema.GoogleDatastoreAdminV1beta1CommonMetadata] = Json.format[Schema.GoogleDatastoreAdminV1beta1CommonMetadata]
	given fmtGoogleDatastoreAdminV1beta1Progress: Format[Schema.GoogleDatastoreAdminV1beta1Progress] = Json.format[Schema.GoogleDatastoreAdminV1beta1Progress]
	given fmtGoogleDatastoreAdminV1beta1EntityFilter: Format[Schema.GoogleDatastoreAdminV1beta1EntityFilter] = Json.format[Schema.GoogleDatastoreAdminV1beta1EntityFilter]
	given fmtGoogleDatastoreAdminV1beta1CommonMetadataOperationTypeEnum: Format[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.OperationTypeEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.OperationTypeEnum]
	given fmtGoogleDatastoreAdminV1beta1CommonMetadataStateEnum: Format[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.StateEnum]
	given fmtGoogleDatastoreAdminV1beta1ExportEntitiesResponse: Format[Schema.GoogleDatastoreAdminV1beta1ExportEntitiesResponse] = Json.format[Schema.GoogleDatastoreAdminV1beta1ExportEntitiesResponse]
	given fmtGoogleDatastoreAdminV1beta1ImportEntitiesMetadata: Format[Schema.GoogleDatastoreAdminV1beta1ImportEntitiesMetadata] = Json.format[Schema.GoogleDatastoreAdminV1beta1ImportEntitiesMetadata]
	given fmtGoogleDatastoreAdminV1ExportEntitiesMetadata: Format[Schema.GoogleDatastoreAdminV1ExportEntitiesMetadata] = Json.format[Schema.GoogleDatastoreAdminV1ExportEntitiesMetadata]
	given fmtGoogleDatastoreAdminV1CommonMetadata: Format[Schema.GoogleDatastoreAdminV1CommonMetadata] = Json.format[Schema.GoogleDatastoreAdminV1CommonMetadata]
	given fmtGoogleDatastoreAdminV1Progress: Format[Schema.GoogleDatastoreAdminV1Progress] = Json.format[Schema.GoogleDatastoreAdminV1Progress]
	given fmtGoogleDatastoreAdminV1CommonMetadataOperationTypeEnum: Format[Schema.GoogleDatastoreAdminV1CommonMetadata.OperationTypeEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1CommonMetadata.OperationTypeEnum]
	given fmtGoogleDatastoreAdminV1CommonMetadataStateEnum: Format[Schema.GoogleDatastoreAdminV1CommonMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1CommonMetadata.StateEnum]
	given fmtGoogleDatastoreAdminV1ExportEntitiesResponse: Format[Schema.GoogleDatastoreAdminV1ExportEntitiesResponse] = Json.format[Schema.GoogleDatastoreAdminV1ExportEntitiesResponse]
	given fmtGoogleDatastoreAdminV1ImportEntitiesMetadata: Format[Schema.GoogleDatastoreAdminV1ImportEntitiesMetadata] = Json.format[Schema.GoogleDatastoreAdminV1ImportEntitiesMetadata]
	given fmtGoogleDatastoreAdminV1IndexOperationMetadata: Format[Schema.GoogleDatastoreAdminV1IndexOperationMetadata] = Json.format[Schema.GoogleDatastoreAdminV1IndexOperationMetadata]
	given fmtGoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata: Format[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata] = Json.format[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata]
	given fmtGoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadataMigrationStateEnum: Format[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStateEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStateEnum]
	given fmtGoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadataMigrationStepEnum: Format[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStepEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStepEnum]
	given fmtGoogleDatastoreAdminV1MigrationProgressEvent: Format[Schema.GoogleDatastoreAdminV1MigrationProgressEvent] = Json.format[Schema.GoogleDatastoreAdminV1MigrationProgressEvent]
	given fmtGoogleDatastoreAdminV1MigrationProgressEventStepEnum: Format[Schema.GoogleDatastoreAdminV1MigrationProgressEvent.StepEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1MigrationProgressEvent.StepEnum]
	given fmtGoogleDatastoreAdminV1PrepareStepDetails: Format[Schema.GoogleDatastoreAdminV1PrepareStepDetails] = Json.format[Schema.GoogleDatastoreAdminV1PrepareStepDetails]
	given fmtGoogleDatastoreAdminV1RedirectWritesStepDetails: Format[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails] = Json.format[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails]
	given fmtGoogleDatastoreAdminV1PrepareStepDetailsConcurrencyModeEnum: Format[Schema.GoogleDatastoreAdminV1PrepareStepDetails.ConcurrencyModeEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1PrepareStepDetails.ConcurrencyModeEnum]
	given fmtGoogleDatastoreAdminV1RedirectWritesStepDetailsConcurrencyModeEnum: Format[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails.ConcurrencyModeEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails.ConcurrencyModeEnum]
	given fmtGoogleDatastoreAdminV1MigrationStateEvent: Format[Schema.GoogleDatastoreAdminV1MigrationStateEvent] = Json.format[Schema.GoogleDatastoreAdminV1MigrationStateEvent]
	given fmtGoogleDatastoreAdminV1MigrationStateEventStateEnum: Format[Schema.GoogleDatastoreAdminV1MigrationStateEvent.StateEnum] = JsonEnumFormat[Schema.GoogleDatastoreAdminV1MigrationStateEvent.StateEnum]
}
