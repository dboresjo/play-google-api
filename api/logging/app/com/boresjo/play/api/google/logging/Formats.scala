package com.boresjo.play.api.google.logging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListBucketsResponse: Format[Schema.ListBucketsResponse] = Json.format[Schema.ListBucketsResponse]
	given fmtLogBucket: Format[Schema.LogBucket] = Json.format[Schema.LogBucket]
	given fmtLogBucketLifecycleStateEnum: Format[Schema.LogBucket.LifecycleStateEnum] = JsonEnumFormat[Schema.LogBucket.LifecycleStateEnum]
	given fmtIndexConfig: Format[Schema.IndexConfig] = Json.format[Schema.IndexConfig]
	given fmtCmekSettings: Format[Schema.CmekSettings] = Json.format[Schema.CmekSettings]
	given fmtIndexConfigTypeEnum: Format[Schema.IndexConfig.TypeEnum] = JsonEnumFormat[Schema.IndexConfig.TypeEnum]
	given fmtUndeleteBucketRequest: Format[Schema.UndeleteBucketRequest] = Json.format[Schema.UndeleteBucketRequest]
	given fmtListViewsResponse: Format[Schema.ListViewsResponse] = Json.format[Schema.ListViewsResponse]
	given fmtLogView: Format[Schema.LogView] = Json.format[Schema.LogView]
	given fmtListLogScopesResponse: Format[Schema.ListLogScopesResponse] = Json.format[Schema.ListLogScopesResponse]
	given fmtLogScope: Format[Schema.LogScope] = Json.format[Schema.LogScope]
	given fmtListExclusionsResponse: Format[Schema.ListExclusionsResponse] = Json.format[Schema.ListExclusionsResponse]
	given fmtLogExclusion: Format[Schema.LogExclusion] = Json.format[Schema.LogExclusion]
	given fmtListSinksResponse: Format[Schema.ListSinksResponse] = Json.format[Schema.ListSinksResponse]
	given fmtLogSink: Format[Schema.LogSink] = Json.format[Schema.LogSink]
	given fmtLogSinkOutputVersionFormatEnum: Format[Schema.LogSink.OutputVersionFormatEnum] = JsonEnumFormat[Schema.LogSink.OutputVersionFormatEnum]
	given fmtBigQueryOptions: Format[Schema.BigQueryOptions] = Json.format[Schema.BigQueryOptions]
	given fmtListLinksResponse: Format[Schema.ListLinksResponse] = Json.format[Schema.ListLinksResponse]
	given fmtLink: Format[Schema.Link] = Json.format[Schema.Link]
	given fmtLinkLifecycleStateEnum: Format[Schema.Link.LifecycleStateEnum] = JsonEnumFormat[Schema.Link.LifecycleStateEnum]
	given fmtBigQueryDataset: Format[Schema.BigQueryDataset] = Json.format[Schema.BigQueryDataset]
	given fmtSettings: Format[Schema.Settings] = Json.format[Schema.Settings]
	given fmtDefaultSinkConfig: Format[Schema.DefaultSinkConfig] = Json.format[Schema.DefaultSinkConfig]
	given fmtDefaultSinkConfigModeEnum: Format[Schema.DefaultSinkConfig.ModeEnum] = JsonEnumFormat[Schema.DefaultSinkConfig.ModeEnum]
	given fmtListSavedQueriesResponse: Format[Schema.ListSavedQueriesResponse] = Json.format[Schema.ListSavedQueriesResponse]
	given fmtSavedQuery: Format[Schema.SavedQuery] = Json.format[Schema.SavedQuery]
	given fmtLoggingQuery: Format[Schema.LoggingQuery] = Json.format[Schema.LoggingQuery]
	given fmtOpsAnalyticsQuery: Format[Schema.OpsAnalyticsQuery] = Json.format[Schema.OpsAnalyticsQuery]
	given fmtSavedQueryVisibilityEnum: Format[Schema.SavedQuery.VisibilityEnum] = JsonEnumFormat[Schema.SavedQuery.VisibilityEnum]
	given fmtSummaryField: Format[Schema.SummaryField] = Json.format[Schema.SummaryField]
	given fmtListRecentQueriesResponse: Format[Schema.ListRecentQueriesResponse] = Json.format[Schema.ListRecentQueriesResponse]
	given fmtRecentQuery: Format[Schema.RecentQuery] = Json.format[Schema.RecentQuery]
	given fmtCopyLogEntriesRequest: Format[Schema.CopyLogEntriesRequest] = Json.format[Schema.CopyLogEntriesRequest]
	given fmtWriteLogEntriesRequest: Format[Schema.WriteLogEntriesRequest] = Json.format[Schema.WriteLogEntriesRequest]
	given fmtMonitoredResource: Format[Schema.MonitoredResource] = Json.format[Schema.MonitoredResource]
	given fmtLogEntry: Format[Schema.LogEntry] = Json.format[Schema.LogEntry]
	given fmtLogEntrySeverityEnum: Format[Schema.LogEntry.SeverityEnum] = JsonEnumFormat[Schema.LogEntry.SeverityEnum]
	given fmtHttpRequest: Format[Schema.HttpRequest] = Json.format[Schema.HttpRequest]
	given fmtMonitoredResourceMetadata: Format[Schema.MonitoredResourceMetadata] = Json.format[Schema.MonitoredResourceMetadata]
	given fmtLogEntryOperation: Format[Schema.LogEntryOperation] = Json.format[Schema.LogEntryOperation]
	given fmtLogEntrySourceLocation: Format[Schema.LogEntrySourceLocation] = Json.format[Schema.LogEntrySourceLocation]
	given fmtLogSplit: Format[Schema.LogSplit] = Json.format[Schema.LogSplit]
	given fmtLogErrorGroup: Format[Schema.LogErrorGroup] = Json.format[Schema.LogErrorGroup]
	given fmtWriteLogEntriesResponse: Format[Schema.WriteLogEntriesResponse] = Json.format[Schema.WriteLogEntriesResponse]
	given fmtListLogEntriesRequest: Format[Schema.ListLogEntriesRequest] = Json.format[Schema.ListLogEntriesRequest]
	given fmtListLogEntriesResponse: Format[Schema.ListLogEntriesResponse] = Json.format[Schema.ListLogEntriesResponse]
	given fmtListMonitoredResourceDescriptorsResponse: Format[Schema.ListMonitoredResourceDescriptorsResponse] = Json.format[Schema.ListMonitoredResourceDescriptorsResponse]
	given fmtMonitoredResourceDescriptor: Format[Schema.MonitoredResourceDescriptor] = Json.format[Schema.MonitoredResourceDescriptor]
	given fmtLabelDescriptor: Format[Schema.LabelDescriptor] = Json.format[Schema.LabelDescriptor]
	given fmtMonitoredResourceDescriptorLaunchStageEnum: Format[Schema.MonitoredResourceDescriptor.LaunchStageEnum] = JsonEnumFormat[Schema.MonitoredResourceDescriptor.LaunchStageEnum]
	given fmtLabelDescriptorValueTypeEnum: Format[Schema.LabelDescriptor.ValueTypeEnum] = JsonEnumFormat[Schema.LabelDescriptor.ValueTypeEnum]
	given fmtListLogsResponse: Format[Schema.ListLogsResponse] = Json.format[Schema.ListLogsResponse]
	given fmtTailLogEntriesRequest: Format[Schema.TailLogEntriesRequest] = Json.format[Schema.TailLogEntriesRequest]
	given fmtTailLogEntriesResponse: Format[Schema.TailLogEntriesResponse] = Json.format[Schema.TailLogEntriesResponse]
	given fmtSuppressionInfo: Format[Schema.SuppressionInfo] = Json.format[Schema.SuppressionInfo]
	given fmtSuppressionInfoReasonEnum: Format[Schema.SuppressionInfo.ReasonEnum] = JsonEnumFormat[Schema.SuppressionInfo.ReasonEnum]
	given fmtListLogMetricsResponse: Format[Schema.ListLogMetricsResponse] = Json.format[Schema.ListLogMetricsResponse]
	given fmtLogMetric: Format[Schema.LogMetric] = Json.format[Schema.LogMetric]
	given fmtMetricDescriptor: Format[Schema.MetricDescriptor] = Json.format[Schema.MetricDescriptor]
	given fmtBucketOptions: Format[Schema.BucketOptions] = Json.format[Schema.BucketOptions]
	given fmtLogMetricVersionEnum: Format[Schema.LogMetric.VersionEnum] = JsonEnumFormat[Schema.LogMetric.VersionEnum]
	given fmtMetricDescriptorMetricKindEnum: Format[Schema.MetricDescriptor.MetricKindEnum] = JsonEnumFormat[Schema.MetricDescriptor.MetricKindEnum]
	given fmtMetricDescriptorValueTypeEnum: Format[Schema.MetricDescriptor.ValueTypeEnum] = JsonEnumFormat[Schema.MetricDescriptor.ValueTypeEnum]
	given fmtMetricDescriptorMetadata: Format[Schema.MetricDescriptorMetadata] = Json.format[Schema.MetricDescriptorMetadata]
	given fmtMetricDescriptorLaunchStageEnum: Format[Schema.MetricDescriptor.LaunchStageEnum] = JsonEnumFormat[Schema.MetricDescriptor.LaunchStageEnum]
	given fmtMetricDescriptorMetadataLaunchStageEnum: Format[Schema.MetricDescriptorMetadata.LaunchStageEnum] = JsonEnumFormat[Schema.MetricDescriptorMetadata.LaunchStageEnum]
	given fmtMetricDescriptorMetadataTimeSeriesResourceHierarchyLevelEnum: Format[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum] = JsonEnumFormat[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum]
	given fmtLinear: Format[Schema.Linear] = Json.format[Schema.Linear]
	given fmtExponential: Format[Schema.Exponential] = Json.format[Schema.Exponential]
	given fmtExplicit: Format[Schema.Explicit] = Json.format[Schema.Explicit]
	given fmtRequestLog: Format[Schema.RequestLog] = Json.format[Schema.RequestLog]
	given fmtLogLine: Format[Schema.LogLine] = Json.format[Schema.LogLine]
	given fmtSourceReference: Format[Schema.SourceReference] = Json.format[Schema.SourceReference]
	given fmtLogLineSeverityEnum: Format[Schema.LogLine.SeverityEnum] = JsonEnumFormat[Schema.LogLine.SeverityEnum]
	given fmtSourceLocation: Format[Schema.SourceLocation] = Json.format[Schema.SourceLocation]
	given fmtCopyLogEntriesMetadata: Format[Schema.CopyLogEntriesMetadata] = Json.format[Schema.CopyLogEntriesMetadata]
	given fmtCopyLogEntriesMetadataStateEnum: Format[Schema.CopyLogEntriesMetadata.StateEnum] = JsonEnumFormat[Schema.CopyLogEntriesMetadata.StateEnum]
	given fmtCopyLogEntriesResponse: Format[Schema.CopyLogEntriesResponse] = Json.format[Schema.CopyLogEntriesResponse]
	given fmtBucketMetadata: Format[Schema.BucketMetadata] = Json.format[Schema.BucketMetadata]
	given fmtBucketMetadataStateEnum: Format[Schema.BucketMetadata.StateEnum] = JsonEnumFormat[Schema.BucketMetadata.StateEnum]
	given fmtCreateBucketRequest: Format[Schema.CreateBucketRequest] = Json.format[Schema.CreateBucketRequest]
	given fmtUpdateBucketRequest: Format[Schema.UpdateBucketRequest] = Json.format[Schema.UpdateBucketRequest]
	given fmtLinkMetadata: Format[Schema.LinkMetadata] = Json.format[Schema.LinkMetadata]
	given fmtLinkMetadataStateEnum: Format[Schema.LinkMetadata.StateEnum] = JsonEnumFormat[Schema.LinkMetadata.StateEnum]
	given fmtCreateLinkRequest: Format[Schema.CreateLinkRequest] = Json.format[Schema.CreateLinkRequest]
	given fmtDeleteLinkRequest: Format[Schema.DeleteLinkRequest] = Json.format[Schema.DeleteLinkRequest]
	given fmtLocationMetadata: Format[Schema.LocationMetadata] = Json.format[Schema.LocationMetadata]
}
