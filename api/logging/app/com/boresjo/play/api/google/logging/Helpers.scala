package com.boresjo.play.api.google.logging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaLogBucket: Conversion[List[Schema.LogBucket], Option[List[Schema.LogBucket]]] = (fun: List[Schema.LogBucket]) => Option(fun)
		given putSchemaLogBucketLifecycleStateEnum: Conversion[Schema.LogBucket.LifecycleStateEnum, Option[Schema.LogBucket.LifecycleStateEnum]] = (fun: Schema.LogBucket.LifecycleStateEnum) => Option(fun)
		given putListSchemaIndexConfig: Conversion[List[Schema.IndexConfig], Option[List[Schema.IndexConfig]]] = (fun: List[Schema.IndexConfig]) => Option(fun)
		given putSchemaCmekSettings: Conversion[Schema.CmekSettings, Option[Schema.CmekSettings]] = (fun: Schema.CmekSettings) => Option(fun)
		given putSchemaIndexConfigTypeEnum: Conversion[Schema.IndexConfig.TypeEnum, Option[Schema.IndexConfig.TypeEnum]] = (fun: Schema.IndexConfig.TypeEnum) => Option(fun)
		given putListSchemaLogView: Conversion[List[Schema.LogView], Option[List[Schema.LogView]]] = (fun: List[Schema.LogView]) => Option(fun)
		given putListSchemaLogScope: Conversion[List[Schema.LogScope], Option[List[Schema.LogScope]]] = (fun: List[Schema.LogScope]) => Option(fun)
		given putListSchemaLogExclusion: Conversion[List[Schema.LogExclusion], Option[List[Schema.LogExclusion]]] = (fun: List[Schema.LogExclusion]) => Option(fun)
		given putListSchemaLogSink: Conversion[List[Schema.LogSink], Option[List[Schema.LogSink]]] = (fun: List[Schema.LogSink]) => Option(fun)
		given putSchemaLogSinkOutputVersionFormatEnum: Conversion[Schema.LogSink.OutputVersionFormatEnum, Option[Schema.LogSink.OutputVersionFormatEnum]] = (fun: Schema.LogSink.OutputVersionFormatEnum) => Option(fun)
		given putSchemaBigQueryOptions: Conversion[Schema.BigQueryOptions, Option[Schema.BigQueryOptions]] = (fun: Schema.BigQueryOptions) => Option(fun)
		given putListSchemaLink: Conversion[List[Schema.Link], Option[List[Schema.Link]]] = (fun: List[Schema.Link]) => Option(fun)
		given putSchemaLinkLifecycleStateEnum: Conversion[Schema.Link.LifecycleStateEnum, Option[Schema.Link.LifecycleStateEnum]] = (fun: Schema.Link.LifecycleStateEnum) => Option(fun)
		given putSchemaBigQueryDataset: Conversion[Schema.BigQueryDataset, Option[Schema.BigQueryDataset]] = (fun: Schema.BigQueryDataset) => Option(fun)
		given putSchemaDefaultSinkConfig: Conversion[Schema.DefaultSinkConfig, Option[Schema.DefaultSinkConfig]] = (fun: Schema.DefaultSinkConfig) => Option(fun)
		given putSchemaDefaultSinkConfigModeEnum: Conversion[Schema.DefaultSinkConfig.ModeEnum, Option[Schema.DefaultSinkConfig.ModeEnum]] = (fun: Schema.DefaultSinkConfig.ModeEnum) => Option(fun)
		given putListSchemaSavedQuery: Conversion[List[Schema.SavedQuery], Option[List[Schema.SavedQuery]]] = (fun: List[Schema.SavedQuery]) => Option(fun)
		given putSchemaLoggingQuery: Conversion[Schema.LoggingQuery, Option[Schema.LoggingQuery]] = (fun: Schema.LoggingQuery) => Option(fun)
		given putSchemaOpsAnalyticsQuery: Conversion[Schema.OpsAnalyticsQuery, Option[Schema.OpsAnalyticsQuery]] = (fun: Schema.OpsAnalyticsQuery) => Option(fun)
		given putSchemaSavedQueryVisibilityEnum: Conversion[Schema.SavedQuery.VisibilityEnum, Option[Schema.SavedQuery.VisibilityEnum]] = (fun: Schema.SavedQuery.VisibilityEnum) => Option(fun)
		given putListSchemaSummaryField: Conversion[List[Schema.SummaryField], Option[List[Schema.SummaryField]]] = (fun: List[Schema.SummaryField]) => Option(fun)
		given putListSchemaRecentQuery: Conversion[List[Schema.RecentQuery], Option[List[Schema.RecentQuery]]] = (fun: List[Schema.RecentQuery]) => Option(fun)
		given putSchemaMonitoredResource: Conversion[Schema.MonitoredResource, Option[Schema.MonitoredResource]] = (fun: Schema.MonitoredResource) => Option(fun)
		given putListSchemaLogEntry: Conversion[List[Schema.LogEntry], Option[List[Schema.LogEntry]]] = (fun: List[Schema.LogEntry]) => Option(fun)
		given putSchemaLogEntrySeverityEnum: Conversion[Schema.LogEntry.SeverityEnum, Option[Schema.LogEntry.SeverityEnum]] = (fun: Schema.LogEntry.SeverityEnum) => Option(fun)
		given putSchemaHttpRequest: Conversion[Schema.HttpRequest, Option[Schema.HttpRequest]] = (fun: Schema.HttpRequest) => Option(fun)
		given putSchemaMonitoredResourceMetadata: Conversion[Schema.MonitoredResourceMetadata, Option[Schema.MonitoredResourceMetadata]] = (fun: Schema.MonitoredResourceMetadata) => Option(fun)
		given putSchemaLogEntryOperation: Conversion[Schema.LogEntryOperation, Option[Schema.LogEntryOperation]] = (fun: Schema.LogEntryOperation) => Option(fun)
		given putSchemaLogEntrySourceLocation: Conversion[Schema.LogEntrySourceLocation, Option[Schema.LogEntrySourceLocation]] = (fun: Schema.LogEntrySourceLocation) => Option(fun)
		given putSchemaLogSplit: Conversion[Schema.LogSplit, Option[Schema.LogSplit]] = (fun: Schema.LogSplit) => Option(fun)
		given putListSchemaLogErrorGroup: Conversion[List[Schema.LogErrorGroup], Option[List[Schema.LogErrorGroup]]] = (fun: List[Schema.LogErrorGroup]) => Option(fun)
		given putListSchemaMonitoredResourceDescriptor: Conversion[List[Schema.MonitoredResourceDescriptor], Option[List[Schema.MonitoredResourceDescriptor]]] = (fun: List[Schema.MonitoredResourceDescriptor]) => Option(fun)
		given putListSchemaLabelDescriptor: Conversion[List[Schema.LabelDescriptor], Option[List[Schema.LabelDescriptor]]] = (fun: List[Schema.LabelDescriptor]) => Option(fun)
		given putSchemaMonitoredResourceDescriptorLaunchStageEnum: Conversion[Schema.MonitoredResourceDescriptor.LaunchStageEnum, Option[Schema.MonitoredResourceDescriptor.LaunchStageEnum]] = (fun: Schema.MonitoredResourceDescriptor.LaunchStageEnum) => Option(fun)
		given putSchemaLabelDescriptorValueTypeEnum: Conversion[Schema.LabelDescriptor.ValueTypeEnum, Option[Schema.LabelDescriptor.ValueTypeEnum]] = (fun: Schema.LabelDescriptor.ValueTypeEnum) => Option(fun)
		given putListSchemaSuppressionInfo: Conversion[List[Schema.SuppressionInfo], Option[List[Schema.SuppressionInfo]]] = (fun: List[Schema.SuppressionInfo]) => Option(fun)
		given putSchemaSuppressionInfoReasonEnum: Conversion[Schema.SuppressionInfo.ReasonEnum, Option[Schema.SuppressionInfo.ReasonEnum]] = (fun: Schema.SuppressionInfo.ReasonEnum) => Option(fun)
		given putListSchemaLogMetric: Conversion[List[Schema.LogMetric], Option[List[Schema.LogMetric]]] = (fun: List[Schema.LogMetric]) => Option(fun)
		given putSchemaMetricDescriptor: Conversion[Schema.MetricDescriptor, Option[Schema.MetricDescriptor]] = (fun: Schema.MetricDescriptor) => Option(fun)
		given putSchemaBucketOptions: Conversion[Schema.BucketOptions, Option[Schema.BucketOptions]] = (fun: Schema.BucketOptions) => Option(fun)
		given putSchemaLogMetricVersionEnum: Conversion[Schema.LogMetric.VersionEnum, Option[Schema.LogMetric.VersionEnum]] = (fun: Schema.LogMetric.VersionEnum) => Option(fun)
		given putSchemaMetricDescriptorMetricKindEnum: Conversion[Schema.MetricDescriptor.MetricKindEnum, Option[Schema.MetricDescriptor.MetricKindEnum]] = (fun: Schema.MetricDescriptor.MetricKindEnum) => Option(fun)
		given putSchemaMetricDescriptorValueTypeEnum: Conversion[Schema.MetricDescriptor.ValueTypeEnum, Option[Schema.MetricDescriptor.ValueTypeEnum]] = (fun: Schema.MetricDescriptor.ValueTypeEnum) => Option(fun)
		given putSchemaMetricDescriptorMetadata: Conversion[Schema.MetricDescriptorMetadata, Option[Schema.MetricDescriptorMetadata]] = (fun: Schema.MetricDescriptorMetadata) => Option(fun)
		given putSchemaMetricDescriptorLaunchStageEnum: Conversion[Schema.MetricDescriptor.LaunchStageEnum, Option[Schema.MetricDescriptor.LaunchStageEnum]] = (fun: Schema.MetricDescriptor.LaunchStageEnum) => Option(fun)
		given putSchemaMetricDescriptorMetadataLaunchStageEnum: Conversion[Schema.MetricDescriptorMetadata.LaunchStageEnum, Option[Schema.MetricDescriptorMetadata.LaunchStageEnum]] = (fun: Schema.MetricDescriptorMetadata.LaunchStageEnum) => Option(fun)
		given putListSchemaMetricDescriptorMetadataTimeSeriesResourceHierarchyLevelEnum: Conversion[List[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum], Option[List[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum]]] = (fun: List[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum]) => Option(fun)
		given putSchemaLinear: Conversion[Schema.Linear, Option[Schema.Linear]] = (fun: Schema.Linear) => Option(fun)
		given putSchemaExponential: Conversion[Schema.Exponential, Option[Schema.Exponential]] = (fun: Schema.Exponential) => Option(fun)
		given putSchemaExplicit: Conversion[Schema.Explicit, Option[Schema.Explicit]] = (fun: Schema.Explicit) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListBigDecimal: Conversion[List[BigDecimal], Option[List[BigDecimal]]] = (fun: List[BigDecimal]) => Option(fun)
		given putListSchemaLogLine: Conversion[List[Schema.LogLine], Option[List[Schema.LogLine]]] = (fun: List[Schema.LogLine]) => Option(fun)
		given putListSchemaSourceReference: Conversion[List[Schema.SourceReference], Option[List[Schema.SourceReference]]] = (fun: List[Schema.SourceReference]) => Option(fun)
		given putSchemaLogLineSeverityEnum: Conversion[Schema.LogLine.SeverityEnum, Option[Schema.LogLine.SeverityEnum]] = (fun: Schema.LogLine.SeverityEnum) => Option(fun)
		given putSchemaSourceLocation: Conversion[Schema.SourceLocation, Option[Schema.SourceLocation]] = (fun: Schema.SourceLocation) => Option(fun)
		given putSchemaCopyLogEntriesMetadataStateEnum: Conversion[Schema.CopyLogEntriesMetadata.StateEnum, Option[Schema.CopyLogEntriesMetadata.StateEnum]] = (fun: Schema.CopyLogEntriesMetadata.StateEnum) => Option(fun)
		given putSchemaCopyLogEntriesRequest: Conversion[Schema.CopyLogEntriesRequest, Option[Schema.CopyLogEntriesRequest]] = (fun: Schema.CopyLogEntriesRequest) => Option(fun)
		given putSchemaBucketMetadataStateEnum: Conversion[Schema.BucketMetadata.StateEnum, Option[Schema.BucketMetadata.StateEnum]] = (fun: Schema.BucketMetadata.StateEnum) => Option(fun)
		given putSchemaCreateBucketRequest: Conversion[Schema.CreateBucketRequest, Option[Schema.CreateBucketRequest]] = (fun: Schema.CreateBucketRequest) => Option(fun)
		given putSchemaUpdateBucketRequest: Conversion[Schema.UpdateBucketRequest, Option[Schema.UpdateBucketRequest]] = (fun: Schema.UpdateBucketRequest) => Option(fun)
		given putSchemaLogBucket: Conversion[Schema.LogBucket, Option[Schema.LogBucket]] = (fun: Schema.LogBucket) => Option(fun)
		given putSchemaLinkMetadataStateEnum: Conversion[Schema.LinkMetadata.StateEnum, Option[Schema.LinkMetadata.StateEnum]] = (fun: Schema.LinkMetadata.StateEnum) => Option(fun)
		given putSchemaCreateLinkRequest: Conversion[Schema.CreateLinkRequest, Option[Schema.CreateLinkRequest]] = (fun: Schema.CreateLinkRequest) => Option(fun)
		given putSchemaDeleteLinkRequest: Conversion[Schema.DeleteLinkRequest, Option[Schema.DeleteLinkRequest]] = (fun: Schema.DeleteLinkRequest) => Option(fun)
		given putSchemaLink: Conversion[Schema.Link, Option[Schema.Link]] = (fun: Schema.Link) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
