package com.boresjo.play.api.google.bigquery

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AggregateClassificationMetrics(
	  /** Accuracy is the fraction of predictions given the correct label. For multiclass this is a micro-averaged metric. */
		accuracy: Option[BigDecimal] = None,
	  /** The F1 score is an average of recall and precision. For multiclass this is a macro-averaged metric. */
		f1Score: Option[BigDecimal] = None,
	  /** Logarithmic Loss. For multiclass this is a macro-averaged metric. */
		logLoss: Option[BigDecimal] = None,
	  /** Precision is the fraction of actual positive predictions that had positive actual labels. For multiclass this is a macro-averaged metric treating each class as a binary classifier. */
		precision: Option[BigDecimal] = None,
	  /** Recall is the fraction of actual positive labels that were given a positive prediction. For multiclass this is a macro-averaged metric. */
		recall: Option[BigDecimal] = None,
	  /** Area Under a ROC Curve. For multiclass this is a macro-averaged metric. */
		rocAuc: Option[BigDecimal] = None,
	  /** Threshold at which the metrics are computed. For binary classification models this is the positive class threshold. For multi-class classfication models this is the confidence threshold. */
		threshold: Option[BigDecimal] = None
	)
	
	case class AggregationThresholdPolicy(
	  /** Optional. The privacy unit column(s) associated with this policy. For now, only one column per data source object (table, view) is allowed as a privacy unit column. Representing as a repeated field in metadata for extensibility to multiple columns in future. Duplicates and Repeated struct fields are not allowed. For nested fields, use dot notation ("outer.inner") */
		privacyUnitColumns: Option[List[String]] = None,
	  /** Optional. The threshold for the "aggregation threshold" policy. */
		threshold: Option[String] = None
	)
	
	object Argument {
		enum ArgumentKindEnum extends Enum[ArgumentKindEnum] { case ARGUMENT_KIND_UNSPECIFIED, FIXED_TYPE, ANY_TYPE }
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, IN, OUT, INOUT }
	}
	case class Argument(
	  /** Optional. Defaults to FIXED_TYPE. */
		argumentKind: Option[Schema.Argument.ArgumentKindEnum] = None,
	  /** Required unless argument_kind = ANY_TYPE. */
		dataType: Option[Schema.StandardSqlDataType] = None,
	  /** Optional. Whether the argument is an aggregate function parameter. Must be Unset for routine types other than AGGREGATE_FUNCTION. For AGGREGATE_FUNCTION, if set to false, it is equivalent to adding "NOT AGGREGATE" clause in DDL; Otherwise, it is equivalent to omitting "NOT AGGREGATE" clause in DDL. */
		isAggregate: Option[Boolean] = None,
	  /** Optional. Specifies whether the argument is input or output. Can be set for procedures only. */
		mode: Option[Schema.Argument.ModeEnum] = None,
	  /** Optional. The name of this argument. Can be absent for function return argument. */
		name: Option[String] = None
	)
	
	case class ArimaCoefficients(
	  /** Auto-regressive coefficients, an array of double. */
		autoRegressiveCoefficients: Option[List[BigDecimal]] = None,
	  /** Intercept coefficient, just a double not an array. */
		interceptCoefficient: Option[BigDecimal] = None,
	  /** Moving-average coefficients, an array of double. */
		movingAverageCoefficients: Option[List[BigDecimal]] = None
	)
	
	case class ArimaFittingMetrics(
	  /** AIC. */
		aic: Option[BigDecimal] = None,
	  /** Log-likelihood. */
		logLikelihood: Option[BigDecimal] = None,
	  /** Variance. */
		variance: Option[BigDecimal] = None
	)
	
	object ArimaForecastingMetrics {
		enum SeasonalPeriodsEnum extends Enum[SeasonalPeriodsEnum] { case SEASONAL_PERIOD_TYPE_UNSPECIFIED, NO_SEASONALITY, DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
	}
	case class ArimaForecastingMetrics(
	  /** Arima model fitting metrics. */
		arimaFittingMetrics: Option[List[Schema.ArimaFittingMetrics]] = None,
	  /** Repeated as there can be many metric sets (one for each model) in auto-arima and the large-scale case. */
		arimaSingleModelForecastingMetrics: Option[List[Schema.ArimaSingleModelForecastingMetrics]] = None,
	  /** Whether Arima model fitted with drift or not. It is always false when d is not 1. */
		hasDrift: Option[List[Boolean]] = None,
	  /** Non-seasonal order. */
		nonSeasonalOrder: Option[List[Schema.ArimaOrder]] = None,
	  /** Seasonal periods. Repeated because multiple periods are supported for one time series. */
		seasonalPeriods: Option[List[Schema.ArimaForecastingMetrics.SeasonalPeriodsEnum]] = None,
	  /** Id to differentiate different time series for the large-scale case. */
		timeSeriesId: Option[List[String]] = None
	)
	
	object ArimaModelInfo {
		enum SeasonalPeriodsEnum extends Enum[SeasonalPeriodsEnum] { case SEASONAL_PERIOD_TYPE_UNSPECIFIED, NO_SEASONALITY, DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
	}
	case class ArimaModelInfo(
	  /** Arima coefficients. */
		arimaCoefficients: Option[Schema.ArimaCoefficients] = None,
	  /** Arima fitting metrics. */
		arimaFittingMetrics: Option[Schema.ArimaFittingMetrics] = None,
	  /** Whether Arima model fitted with drift or not. It is always false when d is not 1. */
		hasDrift: Option[Boolean] = None,
	  /** If true, holiday_effect is a part of time series decomposition result. */
		hasHolidayEffect: Option[Boolean] = None,
	  /** If true, spikes_and_dips is a part of time series decomposition result. */
		hasSpikesAndDips: Option[Boolean] = None,
	  /** If true, step_changes is a part of time series decomposition result. */
		hasStepChanges: Option[Boolean] = None,
	  /** Non-seasonal order. */
		nonSeasonalOrder: Option[Schema.ArimaOrder] = None,
	  /** Seasonal periods. Repeated because multiple periods are supported for one time series. */
		seasonalPeriods: Option[List[Schema.ArimaModelInfo.SeasonalPeriodsEnum]] = None,
	  /** The time_series_id value for this time series. It will be one of the unique values from the time_series_id_column specified during ARIMA model training. Only present when time_series_id_column training option was used. */
		timeSeriesId: Option[String] = None,
	  /** The tuple of time_series_ids identifying this time series. It will be one of the unique tuples of values present in the time_series_id_columns specified during ARIMA model training. Only present when time_series_id_columns training option was used and the order of values here are same as the order of time_series_id_columns. */
		timeSeriesIds: Option[List[String]] = None
	)
	
	case class ArimaOrder(
	  /** Order of the differencing part. */
		d: Option[String] = None,
	  /** Order of the autoregressive part. */
		p: Option[String] = None,
	  /** Order of the moving-average part. */
		q: Option[String] = None
	)
	
	object ArimaResult {
		enum SeasonalPeriodsEnum extends Enum[SeasonalPeriodsEnum] { case SEASONAL_PERIOD_TYPE_UNSPECIFIED, NO_SEASONALITY, DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
	}
	case class ArimaResult(
	  /** This message is repeated because there are multiple arima models fitted in auto-arima. For non-auto-arima model, its size is one. */
		arimaModelInfo: Option[List[Schema.ArimaModelInfo]] = None,
	  /** Seasonal periods. Repeated because multiple periods are supported for one time series. */
		seasonalPeriods: Option[List[Schema.ArimaResult.SeasonalPeriodsEnum]] = None
	)
	
	object ArimaSingleModelForecastingMetrics {
		enum SeasonalPeriodsEnum extends Enum[SeasonalPeriodsEnum] { case SEASONAL_PERIOD_TYPE_UNSPECIFIED, NO_SEASONALITY, DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
	}
	case class ArimaSingleModelForecastingMetrics(
	  /** Arima fitting metrics. */
		arimaFittingMetrics: Option[Schema.ArimaFittingMetrics] = None,
	  /** Is arima model fitted with drift or not. It is always false when d is not 1. */
		hasDrift: Option[Boolean] = None,
	  /** If true, holiday_effect is a part of time series decomposition result. */
		hasHolidayEffect: Option[Boolean] = None,
	  /** If true, spikes_and_dips is a part of time series decomposition result. */
		hasSpikesAndDips: Option[Boolean] = None,
	  /** If true, step_changes is a part of time series decomposition result. */
		hasStepChanges: Option[Boolean] = None,
	  /** Non-seasonal order. */
		nonSeasonalOrder: Option[Schema.ArimaOrder] = None,
	  /** Seasonal periods. Repeated because multiple periods are supported for one time series. */
		seasonalPeriods: Option[List[Schema.ArimaSingleModelForecastingMetrics.SeasonalPeriodsEnum]] = None,
	  /** The time_series_id value for this time series. It will be one of the unique values from the time_series_id_column specified during ARIMA model training. Only present when time_series_id_column training option was used. */
		timeSeriesId: Option[String] = None,
	  /** The tuple of time_series_ids identifying this time series. It will be one of the unique tuples of values present in the time_series_id_columns specified during ARIMA model training. Only present when time_series_id_columns training option was used and the order of values here are same as the order of time_series_id_columns. */
		timeSeriesIds: Option[List[String]] = None
	)
	
	case class AuditConfig(
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None,
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None,
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None
	)
	
	case class AvroOptions(
	  /** Optional. If sourceFormat is set to "AVRO", indicates whether to interpret logical types as the corresponding BigQuery data type (for example, TIMESTAMP), instead of using the raw type (for example, INTEGER). */
		useAvroLogicalTypes: Option[Boolean] = None
	)
	
	object BiEngineReason {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, NO_RESERVATION, INSUFFICIENT_RESERVATION, UNSUPPORTED_SQL_TEXT, INPUT_TOO_LARGE, OTHER_REASON, TABLE_EXCLUDED }
	}
	case class BiEngineReason(
	  /** Output only. High-level BI Engine reason for partial or disabled acceleration */
		code: Option[Schema.BiEngineReason.CodeEnum] = None,
	  /** Output only. Free form human-readable reason for partial or disabled acceleration. */
		message: Option[String] = None
	)
	
	object BiEngineStatistics {
		enum AccelerationModeEnum extends Enum[AccelerationModeEnum] { case BI_ENGINE_ACCELERATION_MODE_UNSPECIFIED, BI_ENGINE_DISABLED, PARTIAL_INPUT, FULL_INPUT, FULL_QUERY }
		enum BiEngineModeEnum extends Enum[BiEngineModeEnum] { case ACCELERATION_MODE_UNSPECIFIED, DISABLED, PARTIAL, FULL }
	}
	case class BiEngineStatistics(
	  /** Output only. Specifies which mode of BI Engine acceleration was performed (if any). */
		accelerationMode: Option[Schema.BiEngineStatistics.AccelerationModeEnum] = None,
	  /** Output only. Specifies which mode of BI Engine acceleration was performed (if any). */
		biEngineMode: Option[Schema.BiEngineStatistics.BiEngineModeEnum] = None,
	  /** In case of DISABLED or PARTIAL bi_engine_mode, these contain the explanatory reasons as to why BI Engine could not accelerate. In case the full query was accelerated, this field is not populated. */
		biEngineReasons: Option[List[Schema.BiEngineReason]] = None
	)
	
	object BigLakeConfiguration {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case FILE_FORMAT_UNSPECIFIED, PARQUET }
		enum TableFormatEnum extends Enum[TableFormatEnum] { case TABLE_FORMAT_UNSPECIFIED, ICEBERG }
	}
	case class BigLakeConfiguration(
	  /** Required. The connection specifying the credentials to be used to read and write to external storage, such as Cloud Storage. The connection_id can have the form `{project}.{location}.{connection_id}` or `projects/{project}/locations/{location}/connections/{connection_id}". */
		connectionId: Option[String] = None,
	  /** Required. The file format the table data is stored in. */
		fileFormat: Option[Schema.BigLakeConfiguration.FileFormatEnum] = None,
	  /** Required. The fully qualified location prefix of the external folder where table data is stored. The '&#42;' wildcard character is not allowed. The URI should be in the format `gs://bucket/path_to_table/` */
		storageUri: Option[String] = None,
	  /** Required. The table format the metadata only snapshots are stored in. */
		tableFormat: Option[Schema.BigLakeConfiguration.TableFormatEnum] = None
	)
	
	case class BigQueryModelTraining(
	  /** Deprecated. */
		currentIteration: Option[Int] = None,
	  /** Deprecated. */
		expectedTotalIterations: Option[String] = None
	)
	
	case class BigtableColumn(
	  /** Optional. The encoding of the values when the type is not STRING. Acceptable encoding values are: TEXT - indicates values are alphanumeric text strings. BINARY - indicates values are encoded using HBase Bytes.toBytes family of functions. 'encoding' can also be set at the column family level. However, the setting at this level takes precedence if 'encoding' is set at both levels. */
		encoding: Option[String] = None,
	  /** Optional. If the qualifier is not a valid BigQuery field identifier i.e. does not match a-zA-Z&#42;, a valid identifier must be provided as the column field name and is used as field name in queries. */
		fieldName: Option[String] = None,
	  /** Optional. If this is set, only the latest version of value in this column are exposed. 'onlyReadLatest' can also be set at the column family level. However, the setting at this level takes precedence if 'onlyReadLatest' is set at both levels. */
		onlyReadLatest: Option[Boolean] = None,
	  /** [Required] Qualifier of the column. Columns in the parent column family that has this exact qualifier are exposed as `.` field. If the qualifier is valid UTF-8 string, it can be specified in the qualifier_string field. Otherwise, a base-64 encoded value must be set to qualifier_encoded. The column field name is the same as the column qualifier. However, if the qualifier is not a valid BigQuery field identifier i.e. does not match a-zA-Z&#42;, a valid identifier must be provided as field_name. */
		qualifierEncoded: Option[String] = None,
	  /** Qualifier string. */
		qualifierString: Option[String] = None,
	  /** Optional. The type to convert the value in cells of this column. The values are expected to be encoded using HBase Bytes.toBytes function when using the BINARY encoding value. Following BigQuery types are allowed (case-sensitive): &#42; BYTES &#42; STRING &#42; INTEGER &#42; FLOAT &#42; BOOLEAN &#42; JSON Default type is BYTES. 'type' can also be set at the column family level. However, the setting at this level takes precedence if 'type' is set at both levels. */
		`type`: Option[String] = None
	)
	
	case class BigtableColumnFamily(
	  /** Optional. Lists of columns that should be exposed as individual fields as opposed to a list of (column name, value) pairs. All columns whose qualifier matches a qualifier in this list can be accessed as `.`. Other columns can be accessed as a list through the `.Column` field. */
		columns: Option[List[Schema.BigtableColumn]] = None,
	  /** Optional. The encoding of the values when the type is not STRING. Acceptable encoding values are: TEXT - indicates values are alphanumeric text strings. BINARY - indicates values are encoded using HBase Bytes.toBytes family of functions. This can be overridden for a specific column by listing that column in 'columns' and specifying an encoding for it. */
		encoding: Option[String] = None,
	  /** Identifier of the column family. */
		familyId: Option[String] = None,
	  /** Optional. If this is set only the latest version of value are exposed for all columns in this column family. This can be overridden for a specific column by listing that column in 'columns' and specifying a different setting for that column. */
		onlyReadLatest: Option[Boolean] = None,
	  /** Optional. The type to convert the value in cells of this column family. The values are expected to be encoded using HBase Bytes.toBytes function when using the BINARY encoding value. Following BigQuery types are allowed (case-sensitive): &#42; BYTES &#42; STRING &#42; INTEGER &#42; FLOAT &#42; BOOLEAN &#42; JSON Default type is BYTES. This can be overridden for a specific column by listing that column in 'columns' and specifying a type for it. */
		`type`: Option[String] = None
	)
	
	case class BigtableOptions(
	  /** Optional. List of column families to expose in the table schema along with their types. This list restricts the column families that can be referenced in queries and specifies their value types. You can use this list to do type conversions - see the 'type' field for more details. If you leave this list empty, all column families are present in the table schema and their values are read as BYTES. During a query only the column families referenced in that query are read from Bigtable. */
		columnFamilies: Option[List[Schema.BigtableColumnFamily]] = None,
	  /** Optional. If field is true, then the column families that are not specified in columnFamilies list are not exposed in the table schema. Otherwise, they are read with BYTES type values. The default value is false. */
		ignoreUnspecifiedColumnFamilies: Option[Boolean] = None,
	  /** Optional. If field is true, then each column family will be read as a single JSON column. Otherwise they are read as a repeated cell structure containing timestamp/value tuples. The default value is false. */
		outputColumnFamiliesAsJson: Option[Boolean] = None,
	  /** Optional. If field is true, then the rowkey column families will be read and converted to string. Otherwise they are read with BYTES type values and users need to manually cast them with CAST if necessary. The default value is false. */
		readRowkeyAsString: Option[Boolean] = None
	)
	
	case class BinaryClassificationMetrics(
	  /** Aggregate classification metrics. */
		aggregateClassificationMetrics: Option[Schema.AggregateClassificationMetrics] = None,
	  /** Binary confusion matrix at multiple thresholds. */
		binaryConfusionMatrixList: Option[List[Schema.BinaryConfusionMatrix]] = None,
	  /** Label representing the negative class. */
		negativeLabel: Option[String] = None,
	  /** Label representing the positive class. */
		positiveLabel: Option[String] = None
	)
	
	case class BinaryConfusionMatrix(
	  /** The fraction of predictions given the correct label. */
		accuracy: Option[BigDecimal] = None,
	  /** The equally weighted average of recall and precision. */
		f1Score: Option[BigDecimal] = None,
	  /** Number of false samples predicted as false. */
		falseNegatives: Option[String] = None,
	  /** Number of false samples predicted as true. */
		falsePositives: Option[String] = None,
	  /** Threshold value used when computing each of the following metric. */
		positiveClassThreshold: Option[BigDecimal] = None,
	  /** The fraction of actual positive predictions that had positive actual labels. */
		precision: Option[BigDecimal] = None,
	  /** The fraction of actual positive labels that were given a positive prediction. */
		recall: Option[BigDecimal] = None,
	  /** Number of true samples predicted as false. */
		trueNegatives: Option[String] = None,
	  /** Number of true samples predicted as true. */
		truePositives: Option[String] = None
	)
	
	case class Binding(
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None
	)
	
	case class BqmlIterationResult(
	  /** Deprecated. */
		durationMs: Option[String] = None,
	  /** Deprecated. */
		evalLoss: Option[BigDecimal] = None,
	  /** Deprecated. */
		index: Option[Int] = None,
	  /** Deprecated. */
		learnRate: Option[BigDecimal] = None,
	  /** Deprecated. */
		trainingLoss: Option[BigDecimal] = None
	)
	
	object BqmlTrainingRun {
		case class TrainingOptionsItem(
			earlyStop: Option[Boolean] = None,
			l1Reg: Option[BigDecimal] = None,
			l2Reg: Option[BigDecimal] = None,
			learnRate: Option[BigDecimal] = None,
			learnRateStrategy: Option[String] = None,
			lineSearchInitLearnRate: Option[BigDecimal] = None,
			maxIteration: Option[String] = None,
			minRelProgress: Option[BigDecimal] = None,
			warmStart: Option[Boolean] = None
		)
	}
	case class BqmlTrainingRun(
	  /** Deprecated. */
		iterationResults: Option[List[Schema.BqmlIterationResult]] = None,
	  /** Deprecated. */
		startTime: Option[String] = None,
	  /** Deprecated. */
		state: Option[String] = None,
	  /** Deprecated. */
		trainingOptions: Option[Schema.BqmlTrainingRun.TrainingOptionsItem] = None
	)
	
	case class CategoricalValue(
	  /** Counts of all categories for the categorical feature. If there are more than ten categories, we return top ten (by count) and return one more CategoryCount with category "_OTHER_" and count as aggregate counts of remaining categories. */
		categoryCounts: Option[List[Schema.CategoryCount]] = None
	)
	
	case class CategoryCount(
	  /** The name of category. */
		category: Option[String] = None,
	  /** The count of training samples matching the category within the cluster. */
		count: Option[String] = None
	)
	
	case class CloneDefinition(
	  /** Required. Reference describing the ID of the table that was cloned. */
		baseTableReference: Option[Schema.TableReference] = None,
	  /** Required. The time at which the base table was cloned. This value is reported in the JSON response using RFC3339 format. */
		cloneTime: Option[String] = None
	)
	
	case class Cluster(
	  /** Centroid id. */
		centroidId: Option[String] = None,
	  /** Count of training data rows that were assigned to this cluster. */
		count: Option[String] = None,
	  /** Values of highly variant features for this cluster. */
		featureValues: Option[List[Schema.FeatureValue]] = None
	)
	
	case class ClusterInfo(
	  /** Centroid id. */
		centroidId: Option[String] = None,
	  /** Cluster radius, the average distance from centroid to each point assigned to the cluster. */
		clusterRadius: Option[BigDecimal] = None,
	  /** Cluster size, the total number of points assigned to the cluster. */
		clusterSize: Option[String] = None
	)
	
	case class Clustering(
	  /** One or more fields on which data should be clustered. Only top-level, non-repeated, simple-type fields are supported. The ordering of the clustering fields should be prioritized from most to least important for filtering purposes. Additional information on limitations can be found here: https://cloud.google.com/bigquery/docs/creating-clustered-tables#limitations */
		fields: Option[List[String]] = None
	)
	
	case class ClusteringMetrics(
	  /** Information for all clusters. */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** Davies-Bouldin index. */
		daviesBouldinIndex: Option[BigDecimal] = None,
	  /** Mean of squared distances between each sample to its cluster centroid. */
		meanSquaredDistance: Option[BigDecimal] = None
	)
	
	case class ConfusionMatrix(
	  /** Confidence threshold used when computing the entries of the confusion matrix. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** One row per actual label. */
		rows: Option[List[Schema.Row]] = None
	)
	
	case class ConnectionProperty(
	  /** The key of the property to set. */
		key: Option[String] = None,
	  /** The value of the property to set. */
		value: Option[String] = None
	)
	
	case class CsvOptions(
	  /** Optional. Indicates if BigQuery should accept rows that are missing trailing optional columns. If true, BigQuery treats missing trailing columns as null values. If false, records with missing trailing columns are treated as bad records, and if there are too many bad records, an invalid error is returned in the job result. The default value is false. */
		allowJaggedRows: Option[Boolean] = None,
	  /** Optional. Indicates if BigQuery should allow quoted data sections that contain newline characters in a CSV file. The default value is false. */
		allowQuotedNewlines: Option[Boolean] = None,
	  /** Optional. The character encoding of the data. The supported values are UTF-8, ISO-8859-1, UTF-16BE, UTF-16LE, UTF-32BE, and UTF-32LE. The default value is UTF-8. BigQuery decodes the data after the raw, binary data has been split using the values of the quote and fieldDelimiter properties. */
		encoding: Option[String] = None,
	  /** Optional. The separator character for fields in a CSV file. The separator is interpreted as a single byte. For files encoded in ISO-8859-1, any single character can be used as a separator. For files encoded in UTF-8, characters represented in decimal range 1-127 (U+0001-U+007F) can be used without any modification. UTF-8 characters encoded with multiple bytes (i.e. U+0080 and above) will have only the first byte used for separating fields. The remaining bytes will be treated as a part of the field. BigQuery also supports the escape sequence "\t" (U+0009) to specify a tab separator. The default value is comma (",", U+002C). */
		fieldDelimiter: Option[String] = None,
	  /** Optional. Specifies a string that represents a null value in a CSV file. For example, if you specify "\N", BigQuery interprets "\N" as a null value when querying a CSV file. The default value is the empty string. If you set this property to a custom value, BigQuery throws an error if an empty string is present for all data types except for STRING and BYTE. For STRING and BYTE columns, BigQuery interprets the empty string as an empty value. */
		nullMarker: Option[String] = None,
	  /** Optional. Indicates if the embedded ASCII control characters (the first 32 characters in the ASCII-table, from '\x00' to '\x1F') are preserved. */
		preserveAsciiControlCharacters: Option[Boolean] = None,
	  /** Optional. The value that is used to quote data sections in a CSV file. BigQuery converts the string to ISO-8859-1 encoding, and then uses the first byte of the encoded string to split the data in its raw, binary state. The default value is a double-quote ("). If your data does not contain quoted sections, set the property value to an empty string. If your data contains quoted newline characters, you must also set the allowQuotedNewlines property to true. To include the specific quote character within a quoted value, precede it with an additional matching quote character. For example, if you want to escape the default character ' " ', use ' "" '. */
		quote: Option[String] = Some("""""""),
	  /** Optional. The number of rows at the top of a CSV file that BigQuery will skip when reading the data. The default value is 0. This property is useful if you have header rows in the file that should be skipped. When autodetect is on, the behavior is the following: &#42; skipLeadingRows unspecified - Autodetect tries to detect headers in the first row. If they are not detected, the row is read as data. Otherwise data is read starting from the second row. &#42; skipLeadingRows is 0 - Instructs autodetect that there are no headers and data should be read starting from the first row. &#42; skipLeadingRows = N > 0 - Autodetect skips N-1 rows and tries to detect headers in row N. If headers are not detected, row N is just skipped. Otherwise row N is used to extract column names for the detected schema. */
		skipLeadingRows: Option[String] = None
	)
	
	case class DataFormatOptions(
	  /** Optional. Output timestamp as usec int64. Default is false. */
		useInt64Timestamp: Option[Boolean] = None
	)
	
	case class DataMaskingStatistics(
	  /** Whether any accessed data was protected by the data masking. */
		dataMaskingApplied: Option[Boolean] = None
	)
	
	case class DataPolicyOption(
	  /** Data policy resource name in the form of projects/project_id/locations/location_id/dataPolicies/data_policy_id. */
		name: Option[String] = None
	)
	
	case class DataSplitResult(
	  /** Table reference of the evaluation data after split. */
		evaluationTable: Option[Schema.TableReference] = None,
	  /** Table reference of the test data after split. */
		testTable: Option[Schema.TableReference] = None,
	  /** Table reference of the training data after split. */
		trainingTable: Option[Schema.TableReference] = None
	)
	
	object Dataset {
		case class AccessItem(
		  /** Optional. condition for the binding. If CEL expression in this field is true, this access binding will be considered */
			condition: Option[Schema.Expr] = None,
		  /** [Pick one] A grant authorizing all resources of a particular type in a particular dataset access to this dataset. Only views are supported for now. The role field is not required when this field is set. If that dataset is deleted and re-created, its access needs to be granted again via an update operation. */
			dataset: Option[Schema.DatasetAccessEntry] = None,
		  /** [Pick one] A domain to grant access to. Any users signed in with the domain specified will be granted the specified access. Example: "example.com". Maps to IAM policy member "domain:DOMAIN". */
			domain: Option[String] = None,
		  /** [Pick one] An email address of a Google Group to grant access to. Maps to IAM policy member "group:GROUP". */
			groupByEmail: Option[String] = None,
		  /** [Pick one] Some other type of member that appears in the IAM Policy but isn't a user, group, domain, or special group. */
			iamMember: Option[String] = None,
		  /** An IAM role ID that should be granted to the user, group, or domain specified in this access entry. The following legacy mappings will be applied: &#42; `OWNER`: `roles/bigquery.dataOwner` &#42; `WRITER`: `roles/bigquery.dataEditor` &#42; `READER`: `roles/bigquery.dataViewer` This field will accept any of the above formats, but will return only the legacy format. For example, if you set this field to "roles/bigquery.dataOwner", it will be returned back as "OWNER". */
			role: Option[String] = None,
		  /** [Pick one] A routine from a different dataset to grant access to. Queries executed against that routine will have read access to views/tables/routines in this dataset. Only UDF is supported for now. The role field is not required when this field is set. If that routine is updated by any user, access to the routine needs to be granted again via an update operation. */
			routine: Option[Schema.RoutineReference] = None,
		  /** [Pick one] A special group to grant access to. Possible values include: &#42; projectOwners: Owners of the enclosing project. &#42; projectReaders: Readers of the enclosing project. &#42; projectWriters: Writers of the enclosing project. &#42; allAuthenticatedUsers: All authenticated BigQuery users. Maps to similarly-named IAM members. */
			specialGroup: Option[String] = None,
		  /** [Pick one] An email address of a user to grant access to. For example: fred@example.com. Maps to IAM policy member "user:EMAIL" or "serviceAccount:EMAIL". */
			userByEmail: Option[String] = None,
		  /** [Pick one] A view from a different dataset to grant access to. Queries executed against that view will have read access to views/tables/routines in this dataset. The role field is not required when this field is set. If that view is updated by any user, access to the view needs to be granted again via an update operation. */
			view: Option[Schema.TableReference] = None
		)
		
		enum DefaultRoundingModeEnum extends Enum[DefaultRoundingModeEnum] { case ROUNDING_MODE_UNSPECIFIED, ROUND_HALF_AWAY_FROM_ZERO, ROUND_HALF_EVEN }
		enum StorageBillingModelEnum extends Enum[StorageBillingModelEnum] { case STORAGE_BILLING_MODEL_UNSPECIFIED, LOGICAL, PHYSICAL }
		case class TagsItem(
		  /** Required. The namespaced friendly name of the tag key, e.g. "12345/environment" where 12345 is org id. */
			tagKey: Option[String] = None,
		  /** Required. The friendly short name of the tag value, e.g. "production". */
			tagValue: Option[String] = None
		)
	}
	case class Dataset(
	  /** Optional. An array of objects that define dataset access for one or more entities. You can set this property when inserting or updating a dataset in order to control who is allowed to access the data. If unspecified at dataset creation time, BigQuery adds default dataset access for the following entities: access.specialGroup: projectReaders; access.role: READER; access.specialGroup: projectWriters; access.role: WRITER; access.specialGroup: projectOwners; access.role: OWNER; access.userByEmail: [dataset creator email]; access.role: OWNER; If you patch a dataset, then this field is overwritten by the patched dataset's access field. To add entities, you must supply the entire existing access array in addition to any new entities that you want to add. */
		access: Option[List[Schema.Dataset.AccessItem]] = None,
	  /** Output only. The time when this dataset was created, in milliseconds since the epoch. */
		creationTime: Option[String] = None,
	  /** Required. A reference that identifies the dataset. */
		datasetReference: Option[Schema.DatasetReference] = None,
	  /** Optional. Defines the default collation specification of future tables created in the dataset. If a table is created in this dataset without table-level default collation, then the table inherits the dataset default collation, which is applied to the string fields that do not have explicit collation specified. A change to this field affects only tables created afterwards, and does not alter the existing tables. The following values are supported: &#42; 'und:ci': undetermined locale, case insensitive. &#42; '': empty string. Default to case-sensitive behavior. */
		defaultCollation: Option[String] = None,
	  /** The default encryption key for all tables in the dataset. After this property is set, the encryption key of all newly-created tables in the dataset is set to this value unless the table creation request or query explicitly overrides the key. */
		defaultEncryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** This default partition expiration, expressed in milliseconds. When new time-partitioned tables are created in a dataset where this property is set, the table will inherit this value, propagated as the `TimePartitioning.expirationMs` property on the new table. If you set `TimePartitioning.expirationMs` explicitly when creating a table, the `defaultPartitionExpirationMs` of the containing dataset is ignored. When creating a partitioned table, if `defaultPartitionExpirationMs` is set, the `defaultTableExpirationMs` value is ignored and the table will not be inherit a table expiration deadline. */
		defaultPartitionExpirationMs: Option[String] = None,
	  /** Optional. Defines the default rounding mode specification of new tables created within this dataset. During table creation, if this field is specified, the table within this dataset will inherit the default rounding mode of the dataset. Setting the default rounding mode on a table overrides this option. Existing tables in the dataset are unaffected. If columns are defined during that table creation, they will immediately inherit the table's default rounding mode, unless otherwise specified. */
		defaultRoundingMode: Option[Schema.Dataset.DefaultRoundingModeEnum] = None,
	  /** Optional. The default lifetime of all tables in the dataset, in milliseconds. The minimum lifetime value is 3600000 milliseconds (one hour). To clear an existing default expiration with a PATCH request, set to 0. Once this property is set, all newly-created tables in the dataset will have an expirationTime property set to the creation time plus the value in this property, and changing the value will only affect new tables, not existing ones. When the expirationTime for a given table is reached, that table will be deleted automatically. If a table's expirationTime is modified or removed before the table expires, or if you provide an explicit expirationTime when creating a table, that value takes precedence over the default expiration time indicated by this property. */
		defaultTableExpirationMs: Option[String] = None,
	  /** Optional. A user-friendly description of the dataset. */
		description: Option[String] = None,
	  /** Output only. A hash of the resource. */
		etag: Option[String] = None,
	  /** Optional. Options defining open source compatible datasets living in the BigQuery catalog. Contains metadata of open source database, schema or namespace represented by the current dataset. */
		externalCatalogDatasetOptions: Option[Schema.ExternalCatalogDatasetOptions] = None,
	  /** Optional. Reference to a read-only external dataset defined in data catalogs outside of BigQuery. Filled out when the dataset type is EXTERNAL. */
		externalDatasetReference: Option[Schema.ExternalDatasetReference] = None,
	  /** Optional. A descriptive name for the dataset. */
		friendlyName: Option[String] = None,
	  /** Output only. The fully-qualified unique name of the dataset in the format projectId:datasetId. The dataset name without the project name is given in the datasetId field. When creating a new dataset, leave this field blank, and instead specify the datasetId field. */
		id: Option[String] = None,
	  /** Optional. TRUE if the dataset and its table names are case-insensitive, otherwise FALSE. By default, this is FALSE, which means the dataset and its table names are case-sensitive. This field does not affect routine references. */
		isCaseInsensitive: Option[Boolean] = None,
	  /** Output only. The resource type. */
		kind: Option[String] = Some("""bigquery#dataset"""),
	  /** The labels associated with this dataset. You can use these to organize and group your datasets. You can set this property when inserting or updating a dataset. See [Creating and Updating Dataset Labels](https://cloud.google.com/bigquery/docs/creating-managing-labels#creating_and_updating_dataset_labels) for more information. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The date when this dataset was last modified, in milliseconds since the epoch. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. Metadata about the LinkedDataset. Filled out when the dataset type is LINKED. */
		linkedDatasetMetadata: Option[Schema.LinkedDatasetMetadata] = None,
	  /** Optional. The source dataset reference when the dataset is of type LINKED. For all other dataset types it is not set. This field cannot be updated once it is set. Any attempt to update this field using Update and Patch API Operations will be ignored. */
		linkedDatasetSource: Option[Schema.LinkedDatasetSource] = None,
	  /** The geographic location where the dataset should reside. See https://cloud.google.com/bigquery/docs/locations for supported locations. */
		location: Option[String] = None,
	  /** Optional. Defines the time travel window in hours. The value can be from 48 to 168 hours (2 to 7 days). The default value is 168 hours if this is not set. */
		maxTimeTravelHours: Option[String] = None,
	  /** Optional. The [tags](https://cloud.google.com/bigquery/docs/tags) attached to this dataset. Tag keys are globally unique. Tag key is expected to be in the namespaced format, for example "123456789012/environment" where 123456789012 is the ID of the parent organization or project resource for this tag key. Tag value is expected to be the short name, for example "Production". See [Tag definitions](https://cloud.google.com/iam/docs/tags-access-control#definitions) for more details. */
		resourceTags: Option[Map[String, String]] = None,
	  /** Optional. Output only. Restriction config for all tables and dataset. If set, restrict certain accesses on the dataset and all its tables based on the config. See [Data egress](https://cloud.google.com/bigquery/docs/analytics-hub-introduction#data_egress) for more details. */
		restrictions: Option[Schema.RestrictionConfig] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. A URL that can be used to access the resource again. You can use this URL in Get or Update requests to the resource. */
		selfLink: Option[String] = None,
	  /** Optional. Updates storage_billing_model for the dataset. */
		storageBillingModel: Option[Schema.Dataset.StorageBillingModelEnum] = None,
	  /** Output only. Tags for the dataset. To provide tags as inputs, use the `resourceTags` field. */
		tags: Option[List[Schema.Dataset.TagsItem]] = None,
	  /** Output only. Same as `type` in `ListFormatDataset`. The type of the dataset, one of: &#42; DEFAULT - only accessible by owner and authorized accounts, &#42; PUBLIC - accessible by everyone, &#42; LINKED - linked dataset, &#42; EXTERNAL - dataset with definition in external metadata catalog. */
		`type`: Option[String] = None
	)
	
	object DatasetAccessEntry {
		enum TargetTypesEnum extends Enum[TargetTypesEnum] { case TARGET_TYPE_UNSPECIFIED, VIEWS, ROUTINES }
	}
	case class DatasetAccessEntry(
	  /** The dataset this entry applies to */
		dataset: Option[Schema.DatasetReference] = None,
	  /** Which resources in the dataset this entry applies to. Currently, only views are supported, but additional target types may be added in the future. */
		targetTypes: Option[List[Schema.DatasetAccessEntry.TargetTypesEnum]] = None
	)
	
	object DatasetList {
		case class DatasetsItem(
		  /** The dataset reference. Use this property to access specific parts of the dataset's ID, such as project ID or dataset ID. */
			datasetReference: Option[Schema.DatasetReference] = None,
		  /** An alternate name for the dataset. The friendly name is purely decorative in nature. */
			friendlyName: Option[String] = None,
		  /** The fully-qualified, unique, opaque ID of the dataset. */
			id: Option[String] = None,
		  /** The resource type. This property always returns the value "bigquery#dataset" */
			kind: Option[String] = None,
		  /** The labels associated with this dataset. You can use these to organize and group your datasets. */
			labels: Option[Map[String, String]] = None,
		  /** The geographic location where the dataset resides. */
			location: Option[String] = None
		)
	}
	case class DatasetList(
	  /** An array of the dataset resources in the project. Each resource contains basic information. For full information about a particular dataset resource, use the Datasets: get method. This property is omitted when there are no datasets in the project. */
		datasets: Option[List[Schema.DatasetList.DatasetsItem]] = None,
	  /** Output only. A hash value of the results page. You can use this property to determine if the page has changed since the last request. */
		etag: Option[String] = None,
	  /** Output only. The resource type. This property always returns the value "bigquery#datasetList" */
		kind: Option[String] = Some("""bigquery#datasetList"""),
	  /** A token that can be used to request the next results page. This property is omitted on the final results page. */
		nextPageToken: Option[String] = None,
	  /** A list of skipped locations that were unreachable. For more information about BigQuery locations, see: https://cloud.google.com/bigquery/docs/locations. Example: "europe-west5" */
		unreachable: Option[List[String]] = None
	)
	
	case class DatasetReference(
	  /** Required. A unique ID for this dataset, without the project name. The ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). The maximum length is 1,024 characters. */
		datasetId: Option[String] = None,
	  /** Optional. The ID of the project containing this dataset. */
		projectId: Option[String] = None
	)
	
	case class DestinationTableProperties(
	  /** Optional. The description for the destination table. This will only be used if the destination table is newly created. If the table already exists and a value different than the current description is provided, the job will fail. */
		description: Option[String] = None,
	  /** Internal use only. */
		expirationTime: Option[String] = None,
	  /** Optional. Friendly name for the destination table. If the table already exists, it should be same as the existing friendly name. */
		friendlyName: Option[String] = None,
	  /** Optional. The labels associated with this table. You can use these to organize and group your tables. This will only be used if the destination table is newly created. If the table already exists and labels are different than the current labels are provided, the job will fail. */
		labels: Option[Map[String, String]] = None
	)
	
	case class DifferentialPrivacyPolicy(
	  /** Optional. The total delta budget for all queries against the privacy-protected view. Each subscriber query against this view charges the amount of delta that is pre-defined by the contributor through the privacy policy delta_per_query field. If there is sufficient budget, then the subscriber query attempts to complete. It might still fail due to other reasons, in which case the charge is refunded. If there is insufficient budget the query is rejected. There might be multiple charge attempts if a single query references multiple views. In this case there must be sufficient budget for all charges or the query is rejected and charges are refunded in best effort. The budget does not have a refresh policy and can only be updated via ALTER VIEW or circumvented by creating a new view that can be queried with a fresh budget. */
		deltaBudget: Option[BigDecimal] = None,
	  /** Output only. The delta budget remaining. If budget is exhausted, no more queries are allowed. Note that the budget for queries that are in progress is deducted before the query executes. If the query fails or is cancelled then the budget is refunded. In this case the amount of budget remaining can increase. */
		deltaBudgetRemaining: Option[BigDecimal] = None,
	  /** Optional. The delta value that is used per query. Delta represents the probability that any row will fail to be epsilon differentially private. Indicates the risk associated with exposing aggregate rows in the result of a query. */
		deltaPerQuery: Option[BigDecimal] = None,
	  /** Optional. The total epsilon budget for all queries against the privacy-protected view. Each subscriber query against this view charges the amount of epsilon they request in their query. If there is sufficient budget, then the subscriber query attempts to complete. It might still fail due to other reasons, in which case the charge is refunded. If there is insufficient budget the query is rejected. There might be multiple charge attempts if a single query references multiple views. In this case there must be sufficient budget for all charges or the query is rejected and charges are refunded in best effort. The budget does not have a refresh policy and can only be updated via ALTER VIEW or circumvented by creating a new view that can be queried with a fresh budget. */
		epsilonBudget: Option[BigDecimal] = None,
	  /** Output only. The epsilon budget remaining. If budget is exhausted, no more queries are allowed. Note that the budget for queries that are in progress is deducted before the query executes. If the query fails or is cancelled then the budget is refunded. In this case the amount of budget remaining can increase. */
		epsilonBudgetRemaining: Option[BigDecimal] = None,
	  /** Optional. The maximum epsilon value that a query can consume. If the subscriber specifies epsilon as a parameter in a SELECT query, it must be less than or equal to this value. The epsilon parameter controls the amount of noise that is added to the groups — a higher epsilon means less noise. */
		maxEpsilonPerQuery: Option[BigDecimal] = None,
	  /** Optional. The maximum groups contributed value that is used per query. Represents the maximum number of groups to which each protected entity can contribute. Changing this value does not improve or worsen privacy. The best value for accuracy and utility depends on the query and data. */
		maxGroupsContributed: Option[String] = None,
	  /** Optional. The privacy unit column associated with this policy. Differential privacy policies can only have one privacy unit column per data source object (table, view). */
		privacyUnitColumn: Option[String] = None
	)
	
	case class DimensionalityReductionMetrics(
	  /** Total percentage of variance explained by the selected principal components. */
		totalExplainedVarianceRatio: Option[BigDecimal] = None
	)
	
	case class DmlStatistics(
	  /** Output only. Number of deleted Rows. populated by DML DELETE, MERGE and TRUNCATE statements. */
		deletedRowCount: Option[String] = None,
	  /** Output only. Number of inserted Rows. Populated by DML INSERT and MERGE statements */
		insertedRowCount: Option[String] = None,
	  /** Output only. Number of updated Rows. Populated by DML UPDATE and MERGE statements. */
		updatedRowCount: Option[String] = None
	)
	
	case class DoubleCandidates(
	  /** Candidates for the double parameter in increasing order. */
		candidates: Option[List[BigDecimal]] = None
	)
	
	case class DoubleHparamSearchSpace(
	  /** Candidates of the double hyperparameter. */
		candidates: Option[Schema.DoubleCandidates] = None,
	  /** Range of the double hyperparameter. */
		range: Option[Schema.DoubleRange] = None
	)
	
	case class DoubleRange(
	  /** Max value of the double parameter. */
		max: Option[BigDecimal] = None,
	  /** Min value of the double parameter. */
		min: Option[BigDecimal] = None
	)
	
	case class EncryptionConfiguration(
	  /** Optional. Describes the Cloud KMS encryption key that will be used to protect destination BigQuery table. The BigQuery Service Account associated with your project requires access to this encryption key. */
		kmsKeyName: Option[String] = None
	)
	
	case class Entry(
	  /** Number of items being predicted as this label. */
		itemCount: Option[String] = None,
	  /** The predicted label. For confidence_threshold > 0, we will also add an entry indicating the number of items under the confidence threshold. */
		predictedLabel: Option[String] = None
	)
	
	case class ErrorProto(
	  /** Debugging information. This property is internal to Google and should not be used. */
		debugInfo: Option[String] = None,
	  /** Specifies where the error occurred, if present. */
		location: Option[String] = None,
	  /** A human-readable description of the error. */
		message: Option[String] = None,
	  /** A short error code that summarizes the error. */
		reason: Option[String] = None
	)
	
	case class EvaluationMetrics(
	  /** Populated for ARIMA models. */
		arimaForecastingMetrics: Option[Schema.ArimaForecastingMetrics] = None,
	  /** Populated for binary classification/classifier models. */
		binaryClassificationMetrics: Option[Schema.BinaryClassificationMetrics] = None,
	  /** Populated for clustering models. */
		clusteringMetrics: Option[Schema.ClusteringMetrics] = None,
	  /** Evaluation metrics when the model is a dimensionality reduction model, which currently includes PCA. */
		dimensionalityReductionMetrics: Option[Schema.DimensionalityReductionMetrics] = None,
	  /** Populated for multi-class classification/classifier models. */
		multiClassClassificationMetrics: Option[Schema.MultiClassClassificationMetrics] = None,
	  /** Populated for implicit feedback type matrix factorization models. */
		rankingMetrics: Option[Schema.RankingMetrics] = None,
	  /** Populated for regression models and explicit feedback type matrix factorization models. */
		regressionMetrics: Option[Schema.RegressionMetrics] = None
	)
	
	object ExplainQueryStage {
		enum ComputeModeEnum extends Enum[ComputeModeEnum] { case COMPUTE_MODE_UNSPECIFIED, BIGQUERY, BI_ENGINE }
	}
	case class ExplainQueryStage(
	  /** Number of parallel input segments completed. */
		completedParallelInputs: Option[String] = None,
	  /** Output only. Compute mode for this stage. */
		computeMode: Option[Schema.ExplainQueryStage.ComputeModeEnum] = None,
	  /** Milliseconds the average shard spent on CPU-bound tasks. */
		computeMsAvg: Option[String] = None,
	  /** Milliseconds the slowest shard spent on CPU-bound tasks. */
		computeMsMax: Option[String] = None,
	  /** Relative amount of time the average shard spent on CPU-bound tasks. */
		computeRatioAvg: Option[BigDecimal] = None,
	  /** Relative amount of time the slowest shard spent on CPU-bound tasks. */
		computeRatioMax: Option[BigDecimal] = None,
	  /** Stage end time represented as milliseconds since the epoch. */
		endMs: Option[String] = None,
	  /** Unique ID for the stage within the plan. */
		id: Option[String] = None,
	  /** IDs for stages that are inputs to this stage. */
		inputStages: Option[List[String]] = None,
	  /** Human-readable name for the stage. */
		name: Option[String] = None,
	  /** Number of parallel input segments to be processed */
		parallelInputs: Option[String] = None,
	  /** Milliseconds the average shard spent reading input. */
		readMsAvg: Option[String] = None,
	  /** Milliseconds the slowest shard spent reading input. */
		readMsMax: Option[String] = None,
	  /** Relative amount of time the average shard spent reading input. */
		readRatioAvg: Option[BigDecimal] = None,
	  /** Relative amount of time the slowest shard spent reading input. */
		readRatioMax: Option[BigDecimal] = None,
	  /** Number of records read into the stage. */
		recordsRead: Option[String] = None,
	  /** Number of records written by the stage. */
		recordsWritten: Option[String] = None,
	  /** Total number of bytes written to shuffle. */
		shuffleOutputBytes: Option[String] = None,
	  /** Total number of bytes written to shuffle and spilled to disk. */
		shuffleOutputBytesSpilled: Option[String] = None,
	  /** Slot-milliseconds used by the stage. */
		slotMs: Option[String] = None,
	  /** Stage start time represented as milliseconds since the epoch. */
		startMs: Option[String] = None,
	  /** Current status for this stage. */
		status: Option[String] = None,
	  /** List of operations within the stage in dependency order (approximately chronological). */
		steps: Option[List[Schema.ExplainQueryStep]] = None,
	  /** Milliseconds the average shard spent waiting to be scheduled. */
		waitMsAvg: Option[String] = None,
	  /** Milliseconds the slowest shard spent waiting to be scheduled. */
		waitMsMax: Option[String] = None,
	  /** Relative amount of time the average shard spent waiting to be scheduled. */
		waitRatioAvg: Option[BigDecimal] = None,
	  /** Relative amount of time the slowest shard spent waiting to be scheduled. */
		waitRatioMax: Option[BigDecimal] = None,
	  /** Milliseconds the average shard spent on writing output. */
		writeMsAvg: Option[String] = None,
	  /** Milliseconds the slowest shard spent on writing output. */
		writeMsMax: Option[String] = None,
	  /** Relative amount of time the average shard spent on writing output. */
		writeRatioAvg: Option[BigDecimal] = None,
	  /** Relative amount of time the slowest shard spent on writing output. */
		writeRatioMax: Option[BigDecimal] = None
	)
	
	case class ExplainQueryStep(
	  /** Machine-readable operation type. */
		kind: Option[String] = None,
	  /** Human-readable description of the step(s). */
		substeps: Option[List[String]] = None
	)
	
	case class Explanation(
	  /** Attribution of feature. */
		attribution: Option[BigDecimal] = None,
	  /** The full feature name. For non-numerical features, will be formatted like `.`. Overall size of feature name will always be truncated to first 120 characters. */
		featureName: Option[String] = None
	)
	
	case class ExportDataStatistics(
	  /** Number of destination files generated in case of EXPORT DATA statement only. */
		fileCount: Option[String] = None,
	  /** [Alpha] Number of destination rows generated in case of EXPORT DATA statement only. */
		rowCount: Option[String] = None
	)
	
	case class Expr(
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None
	)
	
	case class ExternalCatalogDatasetOptions(
	  /** Optional. The storage location URI for all tables in the dataset. Equivalent to hive metastore's database locationUri. Maximum length of 1024 characters. */
		defaultStorageLocationUri: Option[String] = None,
	  /** Optional. A map of key value pairs defining the parameters and properties of the open source schema. Maximum size of 2Mib. */
		parameters: Option[Map[String, String]] = None
	)
	
	case class ExternalCatalogTableOptions(
	  /** Optional. The connection specifying the credentials to be used to read external storage, such as Azure Blob, Cloud Storage, or S3. The connection is needed to read the open source table from BigQuery Engine. The connection_id can have the form `..` or `projects//locations//connections/`. */
		connectionId: Option[String] = None,
	  /** Optional. A map of key value pairs defining the parameters and properties of the open source table. Corresponds with hive meta store table parameters. Maximum size of 4Mib. */
		parameters: Option[Map[String, String]] = None,
	  /** Optional. A storage descriptor containing information about the physical storage of this table. */
		storageDescriptor: Option[Schema.StorageDescriptor] = None
	)
	
	object ExternalDataConfiguration {
		enum DecimalTargetTypesEnum extends Enum[DecimalTargetTypesEnum] { case DECIMAL_TARGET_TYPE_UNSPECIFIED, NUMERIC, BIGNUMERIC, STRING }
		enum FileSetSpecTypeEnum extends Enum[FileSetSpecTypeEnum] { case FILE_SET_SPEC_TYPE_FILE_SYSTEM_MATCH, FILE_SET_SPEC_TYPE_NEW_LINE_DELIMITED_MANIFEST }
		enum JsonExtensionEnum extends Enum[JsonExtensionEnum] { case JSON_EXTENSION_UNSPECIFIED, GEOJSON }
		enum MetadataCacheModeEnum extends Enum[MetadataCacheModeEnum] { case METADATA_CACHE_MODE_UNSPECIFIED, AUTOMATIC, MANUAL }
		enum ObjectMetadataEnum extends Enum[ObjectMetadataEnum] { case OBJECT_METADATA_UNSPECIFIED, DIRECTORY, SIMPLE }
	}
	case class ExternalDataConfiguration(
	  /** Try to detect schema and format options automatically. Any option specified explicitly will be honored. */
		autodetect: Option[Boolean] = None,
	  /** Optional. Additional properties to set if sourceFormat is set to AVRO. */
		avroOptions: Option[Schema.AvroOptions] = None,
	  /** Optional. Additional options if sourceFormat is set to BIGTABLE. */
		bigtableOptions: Option[Schema.BigtableOptions] = None,
	  /** Optional. The compression type of the data source. Possible values include GZIP and NONE. The default value is NONE. This setting is ignored for Google Cloud Bigtable, Google Cloud Datastore backups, Avro, ORC and Parquet formats. An empty string is an invalid value. */
		compression: Option[String] = None,
	  /** Optional. The connection specifying the credentials to be used to read external storage, such as Azure Blob, Cloud Storage, or S3. The connection_id can have the form `{project_id}.{location_id};{connection_id}` or `projects/{project_id}/locations/{location_id}/connections/{connection_id}`. */
		connectionId: Option[String] = None,
	  /** Optional. Additional properties to set if sourceFormat is set to CSV. */
		csvOptions: Option[Schema.CsvOptions] = None,
	  /** Defines the list of possible SQL data types to which the source decimal values are converted. This list and the precision and the scale parameters of the decimal field determine the target type. In the order of NUMERIC, BIGNUMERIC, and STRING, a type is picked if it is in the specified list and if it supports the precision and the scale. STRING supports all precision and scale values. If none of the listed types supports the precision and the scale, the type supporting the widest range in the specified list is picked, and if a value exceeds the supported range when reading the data, an error will be thrown. Example: Suppose the value of this field is ["NUMERIC", "BIGNUMERIC"]. If (precision,scale) is: &#42; (38,9) -> NUMERIC; &#42; (39,9) -> BIGNUMERIC (NUMERIC cannot hold 30 integer digits); &#42; (38,10) -> BIGNUMERIC (NUMERIC cannot hold 10 fractional digits); &#42; (76,38) -> BIGNUMERIC; &#42; (77,38) -> BIGNUMERIC (error if value exeeds supported range). This field cannot contain duplicate types. The order of the types in this field is ignored. For example, ["BIGNUMERIC", "NUMERIC"] is the same as ["NUMERIC", "BIGNUMERIC"] and NUMERIC always takes precedence over BIGNUMERIC. Defaults to ["NUMERIC", "STRING"] for ORC and ["NUMERIC"] for the other file formats. */
		decimalTargetTypes: Option[List[Schema.ExternalDataConfiguration.DecimalTargetTypesEnum]] = None,
	  /** Optional. Specifies how source URIs are interpreted for constructing the file set to load. By default source URIs are expanded against the underlying storage. Other options include specifying manifest files. Only applicable to object storage systems. */
		fileSetSpecType: Option[Schema.ExternalDataConfiguration.FileSetSpecTypeEnum] = None,
	  /** Optional. Additional options if sourceFormat is set to GOOGLE_SHEETS. */
		googleSheetsOptions: Option[Schema.GoogleSheetsOptions] = None,
	  /** Optional. When set, configures hive partitioning support. Not all storage formats support hive partitioning -- requesting hive partitioning on an unsupported format will lead to an error, as will providing an invalid specification. */
		hivePartitioningOptions: Option[Schema.HivePartitioningOptions] = None,
	  /** Optional. Indicates if BigQuery should allow extra values that are not represented in the table schema. If true, the extra values are ignored. If false, records with extra columns are treated as bad records, and if there are too many bad records, an invalid error is returned in the job result. The default value is false. The sourceFormat property determines what BigQuery treats as an extra value: CSV: Trailing columns JSON: Named values that don't match any column names Google Cloud Bigtable: This setting is ignored. Google Cloud Datastore backups: This setting is ignored. Avro: This setting is ignored. ORC: This setting is ignored. Parquet: This setting is ignored. */
		ignoreUnknownValues: Option[Boolean] = None,
	  /** Optional. Load option to be used together with source_format newline-delimited JSON to indicate that a variant of JSON is being loaded. To load newline-delimited GeoJSON, specify GEOJSON (and source_format must be set to NEWLINE_DELIMITED_JSON). */
		jsonExtension: Option[Schema.ExternalDataConfiguration.JsonExtensionEnum] = None,
	  /** Optional. Additional properties to set if sourceFormat is set to JSON. */
		jsonOptions: Option[Schema.JsonOptions] = None,
	  /** Optional. The maximum number of bad records that BigQuery can ignore when reading data. If the number of bad records exceeds this value, an invalid error is returned in the job result. The default value is 0, which requires that all records are valid. This setting is ignored for Google Cloud Bigtable, Google Cloud Datastore backups, Avro, ORC and Parquet formats. */
		maxBadRecords: Option[Int] = None,
	  /** Optional. Metadata Cache Mode for the table. Set this to enable caching of metadata from external data source. */
		metadataCacheMode: Option[Schema.ExternalDataConfiguration.MetadataCacheModeEnum] = None,
	  /** Optional. ObjectMetadata is used to create Object Tables. Object Tables contain a listing of objects (with their metadata) found at the source_uris. If ObjectMetadata is set, source_format should be omitted. Currently SIMPLE is the only supported Object Metadata type. */
		objectMetadata: Option[Schema.ExternalDataConfiguration.ObjectMetadataEnum] = None,
	  /** Optional. Additional properties to set if sourceFormat is set to PARQUET. */
		parquetOptions: Option[Schema.ParquetOptions] = None,
	  /** Optional. When creating an external table, the user can provide a reference file with the table schema. This is enabled for the following formats: AVRO, PARQUET, ORC. */
		referenceFileSchemaUri: Option[String] = None,
	  /** Optional. The schema for the data. Schema is required for CSV and JSON formats if autodetect is not on. Schema is disallowed for Google Cloud Bigtable, Cloud Datastore backups, Avro, ORC and Parquet formats. */
		schema: Option[Schema.TableSchema] = None,
	  /** [Required] The data format. For CSV files, specify "CSV". For Google sheets, specify "GOOGLE_SHEETS". For newline-delimited JSON, specify "NEWLINE_DELIMITED_JSON". For Avro files, specify "AVRO". For Google Cloud Datastore backups, specify "DATASTORE_BACKUP". For Apache Iceberg tables, specify "ICEBERG". For ORC files, specify "ORC". For Parquet files, specify "PARQUET". [Beta] For Google Cloud Bigtable, specify "BIGTABLE". */
		sourceFormat: Option[String] = None,
	  /** [Required] The fully-qualified URIs that point to your data in Google Cloud. For Google Cloud Storage URIs: Each URI can contain one '&#42;' wildcard character and it must come after the 'bucket' name. Size limits related to load jobs apply to external data sources. For Google Cloud Bigtable URIs: Exactly one URI can be specified and it has be a fully specified and valid HTTPS URL for a Google Cloud Bigtable table. For Google Cloud Datastore backups, exactly one URI can be specified. Also, the '&#42;' wildcard character is not allowed. */
		sourceUris: Option[List[String]] = None
	)
	
	case class ExternalDatasetReference(
	  /** Required. The connection id that is used to access the external_source. Format: projects/{project_id}/locations/{location_id}/connections/{connection_id} */
		connection: Option[String] = None,
	  /** Required. External source that backs this dataset. */
		externalSource: Option[String] = None
	)
	
	case class ExternalServiceCost(
	  /** External service cost in terms of bigquery bytes billed. */
		bytesBilled: Option[String] = None,
	  /** External service cost in terms of bigquery bytes processed. */
		bytesProcessed: Option[String] = None,
	  /** External service name. */
		externalService: Option[String] = None,
	  /** Non-preemptable reserved slots used for external job. For example, reserved slots for Cloua AI Platform job are the VM usages converted to BigQuery slot with equivalent mount of price. */
		reservedSlotCount: Option[String] = None,
	  /** External service cost in terms of bigquery slot milliseconds. */
		slotMs: Option[String] = None
	)
	
	case class FeatureValue(
	  /** The categorical feature value. */
		categoricalValue: Option[Schema.CategoricalValue] = None,
	  /** The feature column name. */
		featureColumn: Option[String] = None,
	  /** The numerical feature value. This is the centroid value for this feature. */
		numericalValue: Option[BigDecimal] = None
	)
	
	object ForeignTypeInfo {
		enum TypeSystemEnum extends Enum[TypeSystemEnum] { case TYPE_SYSTEM_UNSPECIFIED, HIVE }
	}
	case class ForeignTypeInfo(
	  /** Required. Specifies the system which defines the foreign data type. */
		typeSystem: Option[Schema.ForeignTypeInfo.TypeSystemEnum] = None
	)
	
	case class ForeignViewDefinition(
	  /** Optional. Represents the dialect of the query. */
		dialect: Option[String] = None,
	  /** Required. The query that defines the view. */
		query: Option[String] = None
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class GetQueryResultsResponse(
	  /** Whether the query result was fetched from the query cache. */
		cacheHit: Option[Boolean] = None,
	  /** Output only. The first errors or warnings encountered during the running of the job. The final message includes the number of errors that caused the process to stop. Errors here do not necessarily mean that the job has completed or was unsuccessful. For more information about error messages, see [Error messages](https://cloud.google.com/bigquery/docs/error-messages). */
		errors: Option[List[Schema.ErrorProto]] = None,
	  /** A hash of this response. */
		etag: Option[String] = None,
	  /** Whether the query has completed or not. If rows or totalRows are present, this will always be true. If this is false, totalRows will not be available. */
		jobComplete: Option[Boolean] = None,
	  /** Reference to the BigQuery Job that was created to run the query. This field will be present even if the original request timed out, in which case GetQueryResults can be used to read the results once the query has completed. Since this API only returns the first page of results, subsequent pages can be fetched via the same mechanism (GetQueryResults). */
		jobReference: Option[Schema.JobReference] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#getQueryResultsResponse"""),
	  /** Output only. The number of rows affected by a DML statement. Present only for DML statements INSERT, UPDATE or DELETE. */
		numDmlAffectedRows: Option[String] = None,
	  /** A token used for paging results. When this token is non-empty, it indicates additional results are available. */
		pageToken: Option[String] = None,
	  /** An object with as many results as can be contained within the maximum permitted reply size. To get any additional rows, you can call GetQueryResults and specify the jobReference returned above. Present only when the query completes successfully. The REST-based representation of this data leverages a series of JSON f,v objects for indicating fields and values. */
		rows: Option[List[Schema.TableRow]] = None,
	  /** The schema of the results. Present only when the query completes successfully. */
		schema: Option[Schema.TableSchema] = None,
	  /** The total number of bytes processed for this query. */
		totalBytesProcessed: Option[String] = None,
	  /** The total number of rows in the complete query result set, which can be more than the number of rows in this single page of results. Present only when the query completes successfully. */
		totalRows: Option[String] = None
	)
	
	case class GetServiceAccountResponse(
	  /** The service account email address. */
		email: Option[String] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#getServiceAccountResponse""")
	)
	
	case class GlobalExplanation(
	  /** Class label for this set of global explanations. Will be empty/null for binary logistic and linear regression models. Sorted alphabetically in descending order. */
		classLabel: Option[String] = None,
	  /** A list of the top global explanations. Sorted by absolute value of attribution in descending order. */
		explanations: Option[List[Schema.Explanation]] = None
	)
	
	case class GoogleSheetsOptions(
	  /** Optional. Range of a sheet to query from. Only used when non-empty. Typical format: sheet_name!top_left_cell_id:bottom_right_cell_id For example: sheet1!A1:B20 */
		range: Option[String] = None,
	  /** Optional. The number of rows at the top of a sheet that BigQuery will skip when reading the data. The default value is 0. This property is useful if you have header rows that should be skipped. When autodetect is on, the behavior is the following: &#42; skipLeadingRows unspecified - Autodetect tries to detect headers in the first row. If they are not detected, the row is read as data. Otherwise data is read starting from the second row. &#42; skipLeadingRows is 0 - Instructs autodetect that there are no headers and data should be read starting from the first row. &#42; skipLeadingRows = N > 0 - Autodetect skips N-1 rows and tries to detect headers in row N. If headers are not detected, row N is just skipped. Otherwise row N is used to extract column names for the detected schema. */
		skipLeadingRows: Option[String] = None
	)
	
	case class HighCardinalityJoin(
	  /** Output only. Count of left input rows. */
		leftRows: Option[String] = None,
	  /** Output only. Count of the output rows. */
		outputRows: Option[String] = None,
	  /** Output only. Count of right input rows. */
		rightRows: Option[String] = None,
	  /** Output only. The index of the join operator in the ExplainQueryStep lists. */
		stepIndex: Option[Int] = None
	)
	
	case class HivePartitioningOptions(
	  /** Output only. For permanent external tables, this field is populated with the hive partition keys in the order they were inferred. The types of the partition keys can be deduced by checking the table schema (which will include the partition keys). Not every API will populate this field in the output. For example, Tables.Get will populate it, but Tables.List will not contain this field. */
		fields: Option[List[String]] = None,
	  /** Optional. When set, what mode of hive partitioning to use when reading data. The following modes are supported: &#42; AUTO: automatically infer partition key name(s) and type(s). &#42; STRINGS: automatically infer partition key name(s). All types are strings. &#42; CUSTOM: partition key schema is encoded in the source URI prefix. Not all storage formats support hive partitioning. Requesting hive partitioning on an unsupported format will lead to an error. Currently supported formats are: JSON, CSV, ORC, Avro and Parquet. */
		mode: Option[String] = None,
	  /** Optional. If set to true, queries over this table require a partition filter that can be used for partition elimination to be specified. Note that this field should only be true when creating a permanent external table or querying a temporary external table. Hive-partitioned loads with require_partition_filter explicitly set to true will fail. */
		requirePartitionFilter: Option[Boolean] = Some(false),
	  /** Optional. When hive partition detection is requested, a common prefix for all source uris must be required. The prefix must end immediately before the partition key encoding begins. For example, consider files following this data layout: gs://bucket/path_to_table/dt=2019-06-01/country=USA/id=7/file.avro gs://bucket/path_to_table/dt=2019-05-31/country=CA/id=3/file.avro When hive partitioning is requested with either AUTO or STRINGS detection, the common prefix can be either of gs://bucket/path_to_table or gs://bucket/path_to_table/. CUSTOM detection requires encoding the partitioning schema immediately after the common prefix. For CUSTOM, any of &#42; gs://bucket/path_to_table/{dt:DATE}/{country:STRING}/{id:INTEGER} &#42; gs://bucket/path_to_table/{dt:STRING}/{country:STRING}/{id:INTEGER} &#42; gs://bucket/path_to_table/{dt:DATE}/{country:STRING}/{id:STRING} would all be valid source URI prefixes. */
		sourceUriPrefix: Option[String] = None
	)
	
	case class HparamSearchSpaces(
	  /** Activation functions of neural network models. */
		activationFn: Option[Schema.StringHparamSearchSpace] = None,
	  /** Mini batch sample size. */
		batchSize: Option[Schema.IntHparamSearchSpace] = None,
	  /** Booster type for boosted tree models. */
		boosterType: Option[Schema.StringHparamSearchSpace] = None,
	  /** Subsample ratio of columns for each level for boosted tree models. */
		colsampleBylevel: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Subsample ratio of columns for each node(split) for boosted tree models. */
		colsampleBynode: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Subsample ratio of columns when constructing each tree for boosted tree models. */
		colsampleBytree: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Dart normalization type for boosted tree models. */
		dartNormalizeType: Option[Schema.StringHparamSearchSpace] = None,
	  /** Dropout probability for dnn model training and boosted tree models using dart booster. */
		dropout: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Hidden units for neural network models. */
		hiddenUnits: Option[Schema.IntArrayHparamSearchSpace] = None,
	  /** L1 regularization coefficient. */
		l1Reg: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** L2 regularization coefficient. */
		l2Reg: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Learning rate of training jobs. */
		learnRate: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Maximum depth of a tree for boosted tree models. */
		maxTreeDepth: Option[Schema.IntHparamSearchSpace] = None,
	  /** Minimum split loss for boosted tree models. */
		minSplitLoss: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Minimum sum of instance weight needed in a child for boosted tree models. */
		minTreeChildWeight: Option[Schema.IntHparamSearchSpace] = None,
	  /** Number of clusters for k-means. */
		numClusters: Option[Schema.IntHparamSearchSpace] = None,
	  /** Number of latent factors to train on. */
		numFactors: Option[Schema.IntHparamSearchSpace] = None,
	  /** Number of parallel trees for boosted tree models. */
		numParallelTree: Option[Schema.IntHparamSearchSpace] = None,
	  /** Optimizer of TF models. */
		optimizer: Option[Schema.StringHparamSearchSpace] = None,
	  /** Subsample the training data to grow tree to prevent overfitting for boosted tree models. */
		subsample: Option[Schema.DoubleHparamSearchSpace] = None,
	  /** Tree construction algorithm for boosted tree models. */
		treeMethod: Option[Schema.StringHparamSearchSpace] = None,
	  /** Hyperparameter for matrix factoration when implicit feedback type is specified. */
		walsAlpha: Option[Schema.DoubleHparamSearchSpace] = None
	)
	
	object HparamTuningTrial {
		enum StatusEnum extends Enum[StatusEnum] { case TRIAL_STATUS_UNSPECIFIED, NOT_STARTED, RUNNING, SUCCEEDED, FAILED, INFEASIBLE, STOPPED_EARLY }
	}
	case class HparamTuningTrial(
	  /** Ending time of the trial. */
		endTimeMs: Option[String] = None,
	  /** Error message for FAILED and INFEASIBLE trial. */
		errorMessage: Option[String] = None,
	  /** Loss computed on the eval data at the end of trial. */
		evalLoss: Option[BigDecimal] = None,
	  /** Evaluation metrics of this trial calculated on the test data. Empty in Job API. */
		evaluationMetrics: Option[Schema.EvaluationMetrics] = None,
	  /** Hyperparameter tuning evaluation metrics of this trial calculated on the eval data. Unlike evaluation_metrics, only the fields corresponding to the hparam_tuning_objectives are set. */
		hparamTuningEvaluationMetrics: Option[Schema.EvaluationMetrics] = None,
	  /** The hyperprameters selected for this trial. */
		hparams: Option[Schema.TrainingOptions] = None,
	  /** Starting time of the trial. */
		startTimeMs: Option[String] = None,
	  /** The status of the trial. */
		status: Option[Schema.HparamTuningTrial.StatusEnum] = None,
	  /** Loss computed on the training data at the end of trial. */
		trainingLoss: Option[BigDecimal] = None,
	  /** 1-based index of the trial. */
		trialId: Option[String] = None
	)
	
	object IndexUnusedReason {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, INDEX_CONFIG_NOT_AVAILABLE, PENDING_INDEX_CREATION, BASE_TABLE_TRUNCATED, INDEX_CONFIG_MODIFIED, TIME_TRAVEL_QUERY, NO_PRUNING_POWER, UNINDEXED_SEARCH_FIELDS, UNSUPPORTED_SEARCH_PATTERN, OPTIMIZED_WITH_MATERIALIZED_VIEW, SECURED_BY_DATA_MASKING, MISMATCHED_TEXT_ANALYZER, BASE_TABLE_TOO_SMALL, BASE_TABLE_TOO_LARGE, ESTIMATED_PERFORMANCE_GAIN_TOO_LOW, NOT_SUPPORTED_IN_STANDARD_EDITION, INDEX_SUPPRESSED_BY_FUNCTION_OPTION, QUERY_CACHE_HIT, STALE_INDEX, INTERNAL_ERROR, OTHER_REASON }
	}
	case class IndexUnusedReason(
	  /** Specifies the base table involved in the reason that no search index was used. */
		baseTable: Option[Schema.TableReference] = None,
	  /** Specifies the high-level reason for the scenario when no search index was used. */
		code: Option[Schema.IndexUnusedReason.CodeEnum] = None,
	  /** Specifies the name of the unused search index, if available. */
		indexName: Option[String] = None,
	  /** Free form human-readable reason for the scenario when no search index was used. */
		message: Option[String] = None
	)
	
	case class InputDataChange(
	  /** Output only. Records read difference percentage compared to a previous run. */
		recordsReadDiffPercentage: Option[BigDecimal] = None
	)
	
	case class IntArray(
	  /** Elements in the int array. */
		elements: Option[List[String]] = None
	)
	
	case class IntArrayHparamSearchSpace(
	  /** Candidates for the int array parameter. */
		candidates: Option[List[Schema.IntArray]] = None
	)
	
	case class IntCandidates(
	  /** Candidates for the int parameter in increasing order. */
		candidates: Option[List[String]] = None
	)
	
	case class IntHparamSearchSpace(
	  /** Candidates of the int hyperparameter. */
		candidates: Option[Schema.IntCandidates] = None,
	  /** Range of the int hyperparameter. */
		range: Option[Schema.IntRange] = None
	)
	
	case class IntRange(
	  /** Max value of the int parameter. */
		max: Option[String] = None,
	  /** Min value of the int parameter. */
		min: Option[String] = None
	)
	
	case class IterationResult(
	  /** Arima result. */
		arimaResult: Option[Schema.ArimaResult] = None,
	  /** Information about top clusters for clustering models. */
		clusterInfos: Option[List[Schema.ClusterInfo]] = None,
	  /** Time taken to run the iteration in milliseconds. */
		durationMs: Option[String] = None,
	  /** Loss computed on the eval data at the end of iteration. */
		evalLoss: Option[BigDecimal] = None,
	  /** Index of the iteration, 0 based. */
		index: Option[Int] = None,
	  /** Learn rate used for this iteration. */
		learnRate: Option[BigDecimal] = None,
	  /** The information of the principal components. */
		principalComponentInfos: Option[List[Schema.PrincipalComponentInfo]] = None,
	  /** Loss computed on the training data at the end of iteration. */
		trainingLoss: Option[BigDecimal] = None
	)
	
	case class Job(
	  /** Required. Describes the job configuration. */
		configuration: Option[Schema.JobConfiguration] = None,
	  /** Output only. A hash of this resource. */
		etag: Option[String] = None,
	  /** Output only. Opaque ID field of the job. */
		id: Option[String] = None,
	  /** Output only. The reason why a Job was created. [Preview](https://cloud.google.com/products/#product-launch-stages) */
		jobCreationReason: Option[Schema.JobCreationReason] = None,
	  /** Optional. Reference describing the unique-per-user name of the job. */
		jobReference: Option[Schema.JobReference] = None,
	  /** Output only. The type of the resource. */
		kind: Option[String] = Some("""bigquery#job"""),
	  /** Output only. [Full-projection-only] String representation of identity of requesting party. Populated for both first- and third-party identities. Only present for APIs that support third-party identities. */
		principal_subject: Option[String] = None,
	  /** Output only. A URL that can be used to access the resource again. */
		selfLink: Option[String] = None,
	  /** Output only. Information about the job, including starting time and ending time of the job. */
		statistics: Option[Schema.JobStatistics] = None,
	  /** Output only. The status of this job. Examine this value when polling an asynchronous job to see if the job is complete. */
		status: Option[Schema.JobStatus] = None,
	  /** Output only. Email address of the user who ran the job. */
		user_email: Option[String] = None
	)
	
	case class JobCancelResponse(
	  /** The final state of the job. */
		job: Option[Schema.Job] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#jobCancelResponse""")
	)
	
	case class JobConfiguration(
	  /** [Pick one] Copies a table. */
		copy: Option[Schema.JobConfigurationTableCopy] = None,
	  /** Optional. If set, don't actually run this job. A valid query will return a mostly empty response with some processing statistics, while an invalid query will return the same error it would if it wasn't a dry run. Behavior of non-query jobs is undefined. */
		dryRun: Option[Boolean] = None,
	  /** [Pick one] Configures an extract job. */
		extract: Option[Schema.JobConfigurationExtract] = None,
	  /** Optional. Job timeout in milliseconds. If this time limit is exceeded, BigQuery will attempt to stop a longer job, but may not always succeed in canceling it before the job completes. For example, a job that takes more than 60 seconds to complete has a better chance of being stopped than a job that takes 10 seconds to complete. */
		jobTimeoutMs: Option[String] = None,
	  /** Output only. The type of the job. Can be QUERY, LOAD, EXTRACT, COPY or UNKNOWN. */
		jobType: Option[String] = None,
	  /** The labels associated with this job. You can use these to organize and group your jobs. Label keys and values can be no longer than 63 characters, can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter and each label in the list must have a different key. */
		labels: Option[Map[String, String]] = None,
	  /** [Pick one] Configures a load job. */
		load: Option[Schema.JobConfigurationLoad] = None,
	  /** [Pick one] Configures a query job. */
		query: Option[Schema.JobConfigurationQuery] = None
	)
	
	case class JobConfigurationExtract(
	  /** Optional. The compression type to use for exported files. Possible values include DEFLATE, GZIP, NONE, SNAPPY, and ZSTD. The default value is NONE. Not all compression formats are support for all file formats. DEFLATE is only supported for Avro. ZSTD is only supported for Parquet. Not applicable when extracting models. */
		compression: Option[String] = None,
	  /** Optional. The exported file format. Possible values include CSV, NEWLINE_DELIMITED_JSON, PARQUET, or AVRO for tables and ML_TF_SAVED_MODEL or ML_XGBOOST_BOOSTER for models. The default value for tables is CSV. Tables with nested or repeated fields cannot be exported as CSV. The default value for models is ML_TF_SAVED_MODEL. */
		destinationFormat: Option[String] = None,
	  /** [Pick one] DEPRECATED: Use destinationUris instead, passing only one URI as necessary. The fully-qualified Google Cloud Storage URI where the extracted table should be written. */
		destinationUri: Option[String] = None,
	  /** [Pick one] A list of fully-qualified Google Cloud Storage URIs where the extracted table should be written. */
		destinationUris: Option[List[String]] = None,
	  /** Optional. When extracting data in CSV format, this defines the delimiter to use between fields in the exported data. Default is ','. Not applicable when extracting models. */
		fieldDelimiter: Option[String] = None,
	  /** Optional. Model extract options only applicable when extracting models. */
		modelExtractOptions: Option[Schema.ModelExtractOptions] = None,
	  /** Optional. Whether to print out a header row in the results. Default is true. Not applicable when extracting models. */
		printHeader: Option[Boolean] = Some(true),
	  /** A reference to the model being exported. */
		sourceModel: Option[Schema.ModelReference] = None,
	  /** A reference to the table being exported. */
		sourceTable: Option[Schema.TableReference] = None,
	  /** Whether to use logical types when extracting to AVRO format. Not applicable when extracting models. */
		useAvroLogicalTypes: Option[Boolean] = None
	)
	
	object JobConfigurationLoad {
		enum ColumnNameCharacterMapEnum extends Enum[ColumnNameCharacterMapEnum] { case COLUMN_NAME_CHARACTER_MAP_UNSPECIFIED, STRICT, V1, V2 }
		enum DecimalTargetTypesEnum extends Enum[DecimalTargetTypesEnum] { case DECIMAL_TARGET_TYPE_UNSPECIFIED, NUMERIC, BIGNUMERIC, STRING }
		enum FileSetSpecTypeEnum extends Enum[FileSetSpecTypeEnum] { case FILE_SET_SPEC_TYPE_FILE_SYSTEM_MATCH, FILE_SET_SPEC_TYPE_NEW_LINE_DELIMITED_MANIFEST }
		enum JsonExtensionEnum extends Enum[JsonExtensionEnum] { case JSON_EXTENSION_UNSPECIFIED, GEOJSON }
	}
	case class JobConfigurationLoad(
	  /** Optional. Accept rows that are missing trailing optional columns. The missing values are treated as nulls. If false, records with missing trailing columns are treated as bad records, and if there are too many bad records, an invalid error is returned in the job result. The default value is false. Only applicable to CSV, ignored for other formats. */
		allowJaggedRows: Option[Boolean] = None,
	  /** Indicates if BigQuery should allow quoted data sections that contain newline characters in a CSV file. The default value is false. */
		allowQuotedNewlines: Option[Boolean] = None,
	  /** Optional. Indicates if we should automatically infer the options and schema for CSV and JSON sources. */
		autodetect: Option[Boolean] = None,
	  /** Clustering specification for the destination table. */
		clustering: Option[Schema.Clustering] = None,
	  /** Optional. Character map supported for column names in CSV/Parquet loads. Defaults to STRICT and can be overridden by Project Config Service. Using this option with unsupporting load formats will result in an error. */
		columnNameCharacterMap: Option[Schema.JobConfigurationLoad.ColumnNameCharacterMapEnum] = None,
	  /** Optional. Connection properties which can modify the load job behavior. Currently, only the 'session_id' connection property is supported, and is used to resolve _SESSION appearing as the dataset id. */
		connectionProperties: Option[List[Schema.ConnectionProperty]] = None,
	  /** Optional. [Experimental] Configures the load job to copy files directly to the destination BigLake managed table, bypassing file content reading and rewriting. Copying files only is supported when all the following are true: &#42; `source_uris` are located in the same Cloud Storage location as the destination table's `storage_uri` location. &#42; `source_format` is `PARQUET`. &#42; `destination_table` is an existing BigLake managed table. The table's schema does not have flexible column names. The table's columns do not have type parameters other than precision and scale. &#42; No options other than the above are specified. */
		copyFilesOnly: Option[Boolean] = None,
	  /** Optional. Specifies whether the job is allowed to create new tables. The following values are supported: &#42; CREATE_IF_NEEDED: If the table does not exist, BigQuery creates the table. &#42; CREATE_NEVER: The table must already exist. If it does not, a 'notFound' error is returned in the job result. The default value is CREATE_IF_NEEDED. Creation, truncation and append actions occur as one atomic update upon job completion. */
		createDisposition: Option[String] = None,
	  /** Optional. If this property is true, the job creates a new session using a randomly generated session_id. To continue using a created session with subsequent queries, pass the existing session identifier as a `ConnectionProperty` value. The session identifier is returned as part of the `SessionInfo` message within the query statistics. The new session's location will be set to `Job.JobReference.location` if it is present, otherwise it's set to the default location based on existing routing logic. */
		createSession: Option[Boolean] = None,
	  /** Defines the list of possible SQL data types to which the source decimal values are converted. This list and the precision and the scale parameters of the decimal field determine the target type. In the order of NUMERIC, BIGNUMERIC, and STRING, a type is picked if it is in the specified list and if it supports the precision and the scale. STRING supports all precision and scale values. If none of the listed types supports the precision and the scale, the type supporting the widest range in the specified list is picked, and if a value exceeds the supported range when reading the data, an error will be thrown. Example: Suppose the value of this field is ["NUMERIC", "BIGNUMERIC"]. If (precision,scale) is: &#42; (38,9) -> NUMERIC; &#42; (39,9) -> BIGNUMERIC (NUMERIC cannot hold 30 integer digits); &#42; (38,10) -> BIGNUMERIC (NUMERIC cannot hold 10 fractional digits); &#42; (76,38) -> BIGNUMERIC; &#42; (77,38) -> BIGNUMERIC (error if value exeeds supported range). This field cannot contain duplicate types. The order of the types in this field is ignored. For example, ["BIGNUMERIC", "NUMERIC"] is the same as ["NUMERIC", "BIGNUMERIC"] and NUMERIC always takes precedence over BIGNUMERIC. Defaults to ["NUMERIC", "STRING"] for ORC and ["NUMERIC"] for the other file formats. */
		decimalTargetTypes: Option[List[Schema.JobConfigurationLoad.DecimalTargetTypesEnum]] = None,
	  /** Custom encryption configuration (e.g., Cloud KMS keys) */
		destinationEncryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** [Required] The destination table to load the data into. */
		destinationTable: Option[Schema.TableReference] = None,
	  /** Optional. [Experimental] Properties with which to create the destination table if it is new. */
		destinationTableProperties: Option[Schema.DestinationTableProperties] = None,
	  /** Optional. The character encoding of the data. The supported values are UTF-8, ISO-8859-1, UTF-16BE, UTF-16LE, UTF-32BE, and UTF-32LE. The default value is UTF-8. BigQuery decodes the data after the raw, binary data has been split using the values of the `quote` and `fieldDelimiter` properties. If you don't specify an encoding, or if you specify a UTF-8 encoding when the CSV file is not UTF-8 encoded, BigQuery attempts to convert the data to UTF-8. Generally, your data loads successfully, but it may not match byte-for-byte what you expect. To avoid this, specify the correct encoding by using the `--encoding` flag. If BigQuery can't convert a character other than the ASCII `0` character, BigQuery converts the character to the standard Unicode replacement character: �. */
		encoding: Option[String] = None,
	  /** Optional. The separator character for fields in a CSV file. The separator is interpreted as a single byte. For files encoded in ISO-8859-1, any single character can be used as a separator. For files encoded in UTF-8, characters represented in decimal range 1-127 (U+0001-U+007F) can be used without any modification. UTF-8 characters encoded with multiple bytes (i.e. U+0080 and above) will have only the first byte used for separating fields. The remaining bytes will be treated as a part of the field. BigQuery also supports the escape sequence "\t" (U+0009) to specify a tab separator. The default value is comma (",", U+002C). */
		fieldDelimiter: Option[String] = None,
	  /** Optional. Specifies how source URIs are interpreted for constructing the file set to load. By default, source URIs are expanded against the underlying storage. You can also specify manifest files to control how the file set is constructed. This option is only applicable to object storage systems. */
		fileSetSpecType: Option[Schema.JobConfigurationLoad.FileSetSpecTypeEnum] = None,
	  /** Optional. When set, configures hive partitioning support. Not all storage formats support hive partitioning -- requesting hive partitioning on an unsupported format will lead to an error, as will providing an invalid specification. */
		hivePartitioningOptions: Option[Schema.HivePartitioningOptions] = None,
	  /** Optional. Indicates if BigQuery should allow extra values that are not represented in the table schema. If true, the extra values are ignored. If false, records with extra columns are treated as bad records, and if there are too many bad records, an invalid error is returned in the job result. The default value is false. The sourceFormat property determines what BigQuery treats as an extra value: CSV: Trailing columns JSON: Named values that don't match any column names in the table schema Avro, Parquet, ORC: Fields in the file schema that don't exist in the table schema. */
		ignoreUnknownValues: Option[Boolean] = None,
	  /** Optional. Load option to be used together with source_format newline-delimited JSON to indicate that a variant of JSON is being loaded. To load newline-delimited GeoJSON, specify GEOJSON (and source_format must be set to NEWLINE_DELIMITED_JSON). */
		jsonExtension: Option[Schema.JobConfigurationLoad.JsonExtensionEnum] = None,
	  /** Optional. The maximum number of bad records that BigQuery can ignore when running the job. If the number of bad records exceeds this value, an invalid error is returned in the job result. The default value is 0, which requires that all records are valid. This is only supported for CSV and NEWLINE_DELIMITED_JSON file formats. */
		maxBadRecords: Option[Int] = None,
	  /** Optional. Specifies a string that represents a null value in a CSV file. For example, if you specify "\N", BigQuery interprets "\N" as a null value when loading a CSV file. The default value is the empty string. If you set this property to a custom value, BigQuery throws an error if an empty string is present for all data types except for STRING and BYTE. For STRING and BYTE columns, BigQuery interprets the empty string as an empty value. */
		nullMarker: Option[String] = None,
	  /** Optional. Additional properties to set if sourceFormat is set to PARQUET. */
		parquetOptions: Option[Schema.ParquetOptions] = None,
	  /** Optional. When sourceFormat is set to "CSV", this indicates whether the embedded ASCII control characters (the first 32 characters in the ASCII-table, from '\x00' to '\x1F') are preserved. */
		preserveAsciiControlCharacters: Option[Boolean] = None,
	  /** If sourceFormat is set to "DATASTORE_BACKUP", indicates which entity properties to load into BigQuery from a Cloud Datastore backup. Property names are case sensitive and must be top-level properties. If no properties are specified, BigQuery loads all properties. If any named property isn't found in the Cloud Datastore backup, an invalid error is returned in the job result. */
		projectionFields: Option[List[String]] = None,
	  /** Optional. The value that is used to quote data sections in a CSV file. BigQuery converts the string to ISO-8859-1 encoding, and then uses the first byte of the encoded string to split the data in its raw, binary state. The default value is a double-quote ('"'). If your data does not contain quoted sections, set the property value to an empty string. If your data contains quoted newline characters, you must also set the allowQuotedNewlines property to true. To include the specific quote character within a quoted value, precede it with an additional matching quote character. For example, if you want to escape the default character ' " ', use ' "" '. @default " */
		quote: Option[String] = Some("""""""),
	  /** Range partitioning specification for the destination table. Only one of timePartitioning and rangePartitioning should be specified. */
		rangePartitioning: Option[Schema.RangePartitioning] = None,
	  /** Optional. The user can provide a reference file with the reader schema. This file is only loaded if it is part of source URIs, but is not loaded otherwise. It is enabled for the following formats: AVRO, PARQUET, ORC. */
		referenceFileSchemaUri: Option[String] = None,
	  /** Optional. The schema for the destination table. The schema can be omitted if the destination table already exists, or if you're loading data from Google Cloud Datastore. */
		schema: Option[Schema.TableSchema] = None,
	  /** [Deprecated] The inline schema. For CSV schemas, specify as "Field1:Type1[,Field2:Type2]&#42;". For example, "foo:STRING, bar:INTEGER, baz:FLOAT". */
		schemaInline: Option[String] = None,
	  /** [Deprecated] The format of the schemaInline property. */
		schemaInlineFormat: Option[String] = None,
	  /** Allows the schema of the destination table to be updated as a side effect of the load job if a schema is autodetected or supplied in the job configuration. Schema update options are supported in two cases: when writeDisposition is WRITE_APPEND; when writeDisposition is WRITE_TRUNCATE and the destination table is a partition of a table, specified by partition decorators. For normal tables, WRITE_TRUNCATE will always overwrite the schema. One or more of the following values are specified: &#42; ALLOW_FIELD_ADDITION: allow adding a nullable field to the schema. &#42; ALLOW_FIELD_RELAXATION: allow relaxing a required field in the original schema to nullable. */
		schemaUpdateOptions: Option[List[String]] = None,
	  /** Optional. The number of rows at the top of a CSV file that BigQuery will skip when loading the data. The default value is 0. This property is useful if you have header rows in the file that should be skipped. When autodetect is on, the behavior is the following: &#42; skipLeadingRows unspecified - Autodetect tries to detect headers in the first row. If they are not detected, the row is read as data. Otherwise data is read starting from the second row. &#42; skipLeadingRows is 0 - Instructs autodetect that there are no headers and data should be read starting from the first row. &#42; skipLeadingRows = N > 0 - Autodetect skips N-1 rows and tries to detect headers in row N. If headers are not detected, row N is just skipped. Otherwise row N is used to extract column names for the detected schema. */
		skipLeadingRows: Option[Int] = None,
	  /** Optional. The format of the data files. For CSV files, specify "CSV". For datastore backups, specify "DATASTORE_BACKUP". For newline-delimited JSON, specify "NEWLINE_DELIMITED_JSON". For Avro, specify "AVRO". For parquet, specify "PARQUET". For orc, specify "ORC". The default value is CSV. */
		sourceFormat: Option[String] = None,
	  /** [Required] The fully-qualified URIs that point to your data in Google Cloud. For Google Cloud Storage URIs: Each URI can contain one '&#42;' wildcard character and it must come after the 'bucket' name. Size limits related to load jobs apply to external data sources. For Google Cloud Bigtable URIs: Exactly one URI can be specified and it has be a fully specified and valid HTTPS URL for a Google Cloud Bigtable table. For Google Cloud Datastore backups: Exactly one URI can be specified. Also, the '&#42;' wildcard character is not allowed. */
		sourceUris: Option[List[String]] = None,
	  /** Time-based partitioning specification for the destination table. Only one of timePartitioning and rangePartitioning should be specified. */
		timePartitioning: Option[Schema.TimePartitioning] = None,
	  /** Optional. If sourceFormat is set to "AVRO", indicates whether to interpret logical types as the corresponding BigQuery data type (for example, TIMESTAMP), instead of using the raw type (for example, INTEGER). */
		useAvroLogicalTypes: Option[Boolean] = None,
	  /** Optional. Specifies the action that occurs if the destination table already exists. The following values are supported: &#42; WRITE_TRUNCATE: If the table already exists, BigQuery overwrites the data, removes the constraints and uses the schema from the load job. &#42; WRITE_APPEND: If the table already exists, BigQuery appends the data to the table. &#42; WRITE_EMPTY: If the table already exists and contains data, a 'duplicate' error is returned in the job result. The default value is WRITE_APPEND. Each action is atomic and only occurs if BigQuery is able to complete the job successfully. Creation, truncation and append actions occur as one atomic update upon job completion. */
		writeDisposition: Option[String] = None
	)
	
	case class JobConfigurationQuery(
	  /** Optional. If true and query uses legacy SQL dialect, allows the query to produce arbitrarily large result tables at a slight cost in performance. Requires destinationTable to be set. For GoogleSQL queries, this flag is ignored and large results are always allowed. However, you must still set destinationTable when result size exceeds the allowed maximum response size. */
		allowLargeResults: Option[Boolean] = Some(false),
	  /** Clustering specification for the destination table. */
		clustering: Option[Schema.Clustering] = None,
	  /** Connection properties which can modify the query behavior. */
		connectionProperties: Option[List[Schema.ConnectionProperty]] = None,
	  /** [Optional] Specifies whether the query should be executed as a continuous query. The default value is false. */
		continuous: Option[Boolean] = None,
	  /** Optional. Specifies whether the job is allowed to create new tables. The following values are supported: &#42; CREATE_IF_NEEDED: If the table does not exist, BigQuery creates the table. &#42; CREATE_NEVER: The table must already exist. If it does not, a 'notFound' error is returned in the job result. The default value is CREATE_IF_NEEDED. Creation, truncation and append actions occur as one atomic update upon job completion. */
		createDisposition: Option[String] = None,
	  /** If this property is true, the job creates a new session using a randomly generated session_id. To continue using a created session with subsequent queries, pass the existing session identifier as a `ConnectionProperty` value. The session identifier is returned as part of the `SessionInfo` message within the query statistics. The new session's location will be set to `Job.JobReference.location` if it is present, otherwise it's set to the default location based on existing routing logic. */
		createSession: Option[Boolean] = None,
	  /** Optional. Specifies the default dataset to use for unqualified table names in the query. This setting does not alter behavior of unqualified dataset names. Setting the system variable `@@dataset_id` achieves the same behavior. See https://cloud.google.com/bigquery/docs/reference/system-variables for more information on system variables. */
		defaultDataset: Option[Schema.DatasetReference] = None,
	  /** Custom encryption configuration (e.g., Cloud KMS keys) */
		destinationEncryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** Optional. Describes the table where the query results should be stored. This property must be set for large results that exceed the maximum response size. For queries that produce anonymous (cached) results, this field will be populated by BigQuery. */
		destinationTable: Option[Schema.TableReference] = None,
	  /** Optional. If true and query uses legacy SQL dialect, flattens all nested and repeated fields in the query results. allowLargeResults must be true if this is set to false. For GoogleSQL queries, this flag is ignored and results are never flattened. */
		flattenResults: Option[Boolean] = Some(true),
	  /** Optional. [Deprecated] Maximum billing tier allowed for this query. The billing tier controls the amount of compute resources allotted to the query, and multiplies the on-demand cost of the query accordingly. A query that runs within its allotted resources will succeed and indicate its billing tier in statistics.query.billingTier, but if the query exceeds its allotted resources, it will fail with billingTierLimitExceeded. WARNING: The billed byte amount can be multiplied by an amount up to this number! Most users should not need to alter this setting, and we recommend that you avoid introducing new uses of it. */
		maximumBillingTier: Option[Int] = Some(1),
	  /** Limits the bytes billed for this job. Queries that will have bytes billed beyond this limit will fail (without incurring a charge). If unspecified, this will be set to your project default. */
		maximumBytesBilled: Option[String] = None,
	  /** GoogleSQL only. Set to POSITIONAL to use positional (?) query parameters or to NAMED to use named (@myparam) query parameters in this query. */
		parameterMode: Option[String] = None,
	  /** [Deprecated] This property is deprecated. */
		preserveNulls: Option[Boolean] = None,
	  /** Optional. Specifies a priority for the query. Possible values include INTERACTIVE and BATCH. The default value is INTERACTIVE. */
		priority: Option[String] = None,
	  /** [Required] SQL query text to execute. The useLegacySql field can be used to indicate whether the query uses legacy SQL or GoogleSQL. */
		query: Option[String] = None,
	  /** Query parameters for GoogleSQL queries. */
		queryParameters: Option[List[Schema.QueryParameter]] = None,
	  /** Range partitioning specification for the destination table. Only one of timePartitioning and rangePartitioning should be specified. */
		rangePartitioning: Option[Schema.RangePartitioning] = None,
	  /** Allows the schema of the destination table to be updated as a side effect of the query job. Schema update options are supported in two cases: when writeDisposition is WRITE_APPEND; when writeDisposition is WRITE_TRUNCATE and the destination table is a partition of a table, specified by partition decorators. For normal tables, WRITE_TRUNCATE will always overwrite the schema. One or more of the following values are specified: &#42; ALLOW_FIELD_ADDITION: allow adding a nullable field to the schema. &#42; ALLOW_FIELD_RELAXATION: allow relaxing a required field in the original schema to nullable. */
		schemaUpdateOptions: Option[List[String]] = None,
	  /** Options controlling the execution of scripts. */
		scriptOptions: Option[Schema.ScriptOptions] = None,
	  /** Output only. System variables for GoogleSQL queries. A system variable is output if the variable is settable and its value differs from the system default. "@@" prefix is not included in the name of the System variables. */
		systemVariables: Option[Schema.SystemVariables] = None,
	  /** Optional. You can specify external table definitions, which operate as ephemeral tables that can be queried. These definitions are configured using a JSON map, where the string key represents the table identifier, and the value is the corresponding external data configuration object. */
		tableDefinitions: Option[Map[String, Schema.ExternalDataConfiguration]] = None,
	  /** Time-based partitioning specification for the destination table. Only one of timePartitioning and rangePartitioning should be specified. */
		timePartitioning: Option[Schema.TimePartitioning] = None,
	  /** Optional. Specifies whether to use BigQuery's legacy SQL dialect for this query. The default value is true. If set to false, the query will use BigQuery's GoogleSQL: https://cloud.google.com/bigquery/sql-reference/ When useLegacySql is set to false, the value of flattenResults is ignored; query will be run as if flattenResults is false. */
		useLegacySql: Option[Boolean] = Some(true),
	  /** Optional. Whether to look for the result in the query cache. The query cache is a best-effort cache that will be flushed whenever tables in the query are modified. Moreover, the query cache is only available when a query does not have a destination table specified. The default value is true. */
		useQueryCache: Option[Boolean] = Some(true),
	  /** Describes user-defined function resources used in the query. */
		userDefinedFunctionResources: Option[List[Schema.UserDefinedFunctionResource]] = None,
	  /** Optional. Specifies the action that occurs if the destination table already exists. The following values are supported: &#42; WRITE_TRUNCATE: If the table already exists, BigQuery overwrites the data, removes the constraints, and uses the schema from the query result. &#42; WRITE_APPEND: If the table already exists, BigQuery appends the data to the table. &#42; WRITE_EMPTY: If the table already exists and contains data, a 'duplicate' error is returned in the job result. The default value is WRITE_EMPTY. Each action is atomic and only occurs if BigQuery is able to complete the job successfully. Creation, truncation and append actions occur as one atomic update upon job completion. */
		writeDisposition: Option[String] = None
	)
	
	object JobConfigurationTableCopy {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, COPY, SNAPSHOT, RESTORE, CLONE }
	}
	case class JobConfigurationTableCopy(
	  /** Optional. Specifies whether the job is allowed to create new tables. The following values are supported: &#42; CREATE_IF_NEEDED: If the table does not exist, BigQuery creates the table. &#42; CREATE_NEVER: The table must already exist. If it does not, a 'notFound' error is returned in the job result. The default value is CREATE_IF_NEEDED. Creation, truncation and append actions occur as one atomic update upon job completion. */
		createDisposition: Option[String] = None,
	  /** Custom encryption configuration (e.g., Cloud KMS keys). */
		destinationEncryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** Optional. The time when the destination table expires. Expired tables will be deleted and their storage reclaimed. */
		destinationExpirationTime: Option[String] = None,
	  /** [Required] The destination table. */
		destinationTable: Option[Schema.TableReference] = None,
	  /** Optional. Supported operation types in table copy job. */
		operationType: Option[Schema.JobConfigurationTableCopy.OperationTypeEnum] = None,
	  /** [Pick one] Source table to copy. */
		sourceTable: Option[Schema.TableReference] = None,
	  /** [Pick one] Source tables to copy. */
		sourceTables: Option[List[Schema.TableReference]] = None,
	  /** Optional. Specifies the action that occurs if the destination table already exists. The following values are supported: &#42; WRITE_TRUNCATE: If the table already exists, BigQuery overwrites the table data and uses the schema and table constraints from the source table. &#42; WRITE_APPEND: If the table already exists, BigQuery appends the data to the table. &#42; WRITE_EMPTY: If the table already exists and contains data, a 'duplicate' error is returned in the job result. The default value is WRITE_EMPTY. Each action is atomic and only occurs if BigQuery is able to complete the job successfully. Creation, truncation and append actions occur as one atomic update upon job completion. */
		writeDisposition: Option[String] = None
	)
	
	object JobCreationReason {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, REQUESTED, LONG_RUNNING, LARGE_RESULTS, OTHER }
	}
	case class JobCreationReason(
	  /** Output only. Specifies the high level reason why a Job was created. */
		code: Option[Schema.JobCreationReason.CodeEnum] = None
	)
	
	object JobList {
		case class JobsItem(
		  /** Required. Describes the job configuration. */
			configuration: Option[Schema.JobConfiguration] = None,
		  /** A result object that will be present only if the job has failed. */
			errorResult: Option[Schema.ErrorProto] = None,
		  /** Unique opaque ID of the job. */
			id: Option[String] = None,
		  /** Unique opaque ID of the job. */
			jobReference: Option[Schema.JobReference] = None,
		  /** The resource type. */
			kind: Option[String] = None,
		  /** [Full-projection-only] String representation of identity of requesting party. Populated for both first- and third-party identities. Only present for APIs that support third-party identities. */
			principal_subject: Option[String] = None,
		  /** Running state of the job. When the state is DONE, errorResult can be checked to determine whether the job succeeded or failed. */
			state: Option[String] = None,
		  /** Output only. Information about the job, including starting time and ending time of the job. */
			statistics: Option[Schema.JobStatistics] = None,
		  /** [Full-projection-only] Describes the status of this job. */
			status: Option[Schema.JobStatus] = None,
		  /** [Full-projection-only] Email address of the user who ran the job. */
			user_email: Option[String] = None
		)
	}
	case class JobList(
	  /** A hash of this page of results. */
		etag: Option[String] = None,
	  /** List of jobs that were requested. */
		jobs: Option[List[Schema.JobList.JobsItem]] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#jobList"""),
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of skipped locations that were unreachable. For more information about BigQuery locations, see: https://cloud.google.com/bigquery/docs/locations. Example: "europe-west5" */
		unreachable: Option[List[String]] = None
	)
	
	case class JobReference(
	  /** Required. The ID of the job. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), or dashes (-). The maximum length is 1,024 characters. */
		jobId: Option[String] = None,
	  /** Optional. The geographic location of the job. The default value is US. For more information about BigQuery locations, see: https://cloud.google.com/bigquery/docs/locations */
		location: Option[String] = None,
	  /** Required. The ID of the project containing this job. */
		projectId: Option[String] = None
	)
	
	object JobStatistics {
		enum EditionEnum extends Enum[EditionEnum] { case RESERVATION_EDITION_UNSPECIFIED, STANDARD, ENTERPRISE, ENTERPRISE_PLUS }
		case class ReservationUsageItem(
		  /** Reservation name or "unreserved" for on-demand resource usage and multi-statement queries. */
			name: Option[String] = None,
		  /** Total slot milliseconds used by the reservation for a particular job. */
			slotMs: Option[String] = None
		)
	}
	case class JobStatistics(
	  /** Output only. [TrustedTester] Job progress (0.0 -> 1.0) for LOAD and EXTRACT jobs. */
		completionRatio: Option[BigDecimal] = None,
	  /** Output only. Statistics for a copy job. */
		copy: Option[Schema.JobStatistics5] = None,
	  /** Output only. Creation time of this job, in milliseconds since the epoch. This field will be present on all jobs. */
		creationTime: Option[String] = None,
	  /** Output only. Statistics for data-masking. Present only for query and extract jobs. */
		dataMaskingStatistics: Option[Schema.DataMaskingStatistics] = None,
	  /** Output only. Name of edition corresponding to the reservation for this job at the time of this update. */
		edition: Option[Schema.JobStatistics.EditionEnum] = None,
	  /** Output only. End time of this job, in milliseconds since the epoch. This field will be present whenever a job is in the DONE state. */
		endTime: Option[String] = None,
	  /** Output only. Statistics for an extract job. */
		extract: Option[Schema.JobStatistics4] = None,
	  /** Output only. The duration in milliseconds of the execution of the final attempt of this job, as BigQuery may internally re-attempt to execute the job. */
		finalExecutionDurationMs: Option[String] = None,
	  /** Output only. Statistics for a load job. */
		load: Option[Schema.JobStatistics3] = None,
	  /** Output only. Number of child jobs executed. */
		numChildJobs: Option[String] = None,
	  /** Output only. If this is a child job, specifies the job ID of the parent. */
		parentJobId: Option[String] = None,
	  /** Output only. Statistics for a query job. */
		query: Option[Schema.JobStatistics2] = None,
	  /** Output only. Quotas which delayed this job's start time. */
		quotaDeferments: Option[List[String]] = None,
	  /** Output only. Job resource usage breakdown by reservation. This field reported misleading information and will no longer be populated. */
		reservationUsage: Option[List[Schema.JobStatistics.ReservationUsageItem]] = None,
	  /** Output only. Name of the primary reservation assigned to this job. Note that this could be different than reservations reported in the reservation usage field if parent reservations were used to execute this job. */
		reservation_id: Option[String] = None,
	  /** Output only. Statistics for row-level security. Present only for query and extract jobs. */
		rowLevelSecurityStatistics: Option[Schema.RowLevelSecurityStatistics] = None,
	  /** Output only. If this a child job of a script, specifies information about the context of this job within the script. */
		scriptStatistics: Option[Schema.ScriptStatistics] = None,
	  /** Output only. Information of the session if this job is part of one. */
		sessionInfo: Option[Schema.SessionInfo] = None,
	  /** Output only. Start time of this job, in milliseconds since the epoch. This field will be present when the job transitions from the PENDING state to either RUNNING or DONE. */
		startTime: Option[String] = None,
	  /** Output only. Total bytes processed for the job. */
		totalBytesProcessed: Option[String] = None,
	  /** Output only. Slot-milliseconds for the job. */
		totalSlotMs: Option[String] = None,
	  /** Output only. [Alpha] Information of the multi-statement transaction if this job is part of one. This property is only expected on a child job or a job that is in a session. A script parent job is not part of the transaction started in the script. */
		transactionInfo: Option[Schema.TransactionInfo] = None
	)
	
	object JobStatistics2 {
		case class ReservationUsageItem(
		  /** Reservation name or "unreserved" for on-demand resource usage and multi-statement queries. */
			name: Option[String] = None,
		  /** Total slot milliseconds used by the reservation for a particular job. */
			slotMs: Option[String] = None
		)
	}
	case class JobStatistics2(
	  /** Output only. BI Engine specific Statistics. */
		biEngineStatistics: Option[Schema.BiEngineStatistics] = None,
	  /** Output only. Billing tier for the job. This is a BigQuery-specific concept which is not related to the Google Cloud notion of "free tier". The value here is a measure of the query's resource consumption relative to the amount of data scanned. For on-demand queries, the limit is 100, and all queries within this limit are billed at the standard on-demand rates. On-demand queries that exceed this limit will fail with a billingTierLimitExceeded error. */
		billingTier: Option[Int] = None,
	  /** Output only. Whether the query result was fetched from the query cache. */
		cacheHit: Option[Boolean] = None,
	  /** Output only. Referenced dataset for DCL statement. */
		dclTargetDataset: Option[Schema.DatasetReference] = None,
	  /** Output only. Referenced table for DCL statement. */
		dclTargetTable: Option[Schema.TableReference] = None,
	  /** Output only. Referenced view for DCL statement. */
		dclTargetView: Option[Schema.TableReference] = None,
	  /** Output only. The number of row access policies affected by a DDL statement. Present only for DROP ALL ROW ACCESS POLICIES queries. */
		ddlAffectedRowAccessPolicyCount: Option[String] = None,
	  /** Output only. The table after rename. Present only for ALTER TABLE RENAME TO query. */
		ddlDestinationTable: Option[Schema.TableReference] = None,
	  /** Output only. The DDL operation performed, possibly dependent on the pre-existence of the DDL target. */
		ddlOperationPerformed: Option[String] = None,
	  /** Output only. The DDL target dataset. Present only for CREATE/ALTER/DROP SCHEMA(dataset) queries. */
		ddlTargetDataset: Option[Schema.DatasetReference] = None,
	  /** Output only. [Beta] The DDL target routine. Present only for CREATE/DROP FUNCTION/PROCEDURE queries. */
		ddlTargetRoutine: Option[Schema.RoutineReference] = None,
	  /** Output only. The DDL target row access policy. Present only for CREATE/DROP ROW ACCESS POLICY queries. */
		ddlTargetRowAccessPolicy: Option[Schema.RowAccessPolicyReference] = None,
	  /** Output only. The DDL target table. Present only for CREATE/DROP TABLE/VIEW and DROP ALL ROW ACCESS POLICIES queries. */
		ddlTargetTable: Option[Schema.TableReference] = None,
	  /** Output only. Detailed statistics for DML statements INSERT, UPDATE, DELETE, MERGE or TRUNCATE. */
		dmlStats: Option[Schema.DmlStatistics] = None,
	  /** Output only. The original estimate of bytes processed for the job. */
		estimatedBytesProcessed: Option[String] = None,
	  /** Output only. Stats for EXPORT DATA statement. */
		exportDataStatistics: Option[Schema.ExportDataStatistics] = None,
	  /** Output only. Job cost breakdown as bigquery internal cost and external service costs. */
		externalServiceCosts: Option[List[Schema.ExternalServiceCost]] = None,
	  /** Output only. Statistics for a LOAD query. */
		loadQueryStatistics: Option[Schema.LoadQueryStatistics] = None,
	  /** Output only. Statistics of materialized views of a query job. */
		materializedViewStatistics: Option[Schema.MaterializedViewStatistics] = None,
	  /** Output only. Statistics of metadata cache usage in a query for BigLake tables. */
		metadataCacheStatistics: Option[Schema.MetadataCacheStatistics] = None,
	  /** Output only. Statistics of a BigQuery ML training job. */
		mlStatistics: Option[Schema.MlStatistics] = None,
	  /** Deprecated. */
		modelTraining: Option[Schema.BigQueryModelTraining] = None,
	  /** Deprecated. */
		modelTrainingCurrentIteration: Option[Int] = None,
	  /** Deprecated. */
		modelTrainingExpectedTotalIteration: Option[String] = None,
	  /** Output only. The number of rows affected by a DML statement. Present only for DML statements INSERT, UPDATE or DELETE. */
		numDmlAffectedRows: Option[String] = None,
	  /** Output only. Performance insights. */
		performanceInsights: Option[Schema.PerformanceInsights] = None,
	  /** Output only. Query optimization information for a QUERY job. */
		queryInfo: Option[Schema.QueryInfo] = None,
	  /** Output only. Describes execution plan for the query. */
		queryPlan: Option[List[Schema.ExplainQueryStage]] = None,
	  /** Output only. Referenced routines for the job. */
		referencedRoutines: Option[List[Schema.RoutineReference]] = None,
	  /** Output only. Referenced tables for the job. Queries that reference more than 50 tables will not have a complete list. */
		referencedTables: Option[List[Schema.TableReference]] = None,
	  /** Output only. Job resource usage breakdown by reservation. This field reported misleading information and will no longer be populated. */
		reservationUsage: Option[List[Schema.JobStatistics2.ReservationUsageItem]] = None,
	  /** Output only. The schema of the results. Present only for successful dry run of non-legacy SQL queries. */
		schema: Option[Schema.TableSchema] = None,
	  /** Output only. Search query specific statistics. */
		searchStatistics: Option[Schema.SearchStatistics] = None,
	  /** Output only. Statistics of a Spark procedure job. */
		sparkStatistics: Option[Schema.SparkStatistics] = None,
	  /** Output only. The type of query statement, if valid. Possible values: &#42; `SELECT`: [`SELECT`](https://cloud.google.com/bigquery/docs/reference/standard-sql/query-syntax#select_list) statement. &#42; `ASSERT`: [`ASSERT`](https://cloud.google.com/bigquery/docs/reference/standard-sql/debugging-statements#assert) statement. &#42; `INSERT`: [`INSERT`](https://cloud.google.com/bigquery/docs/reference/standard-sql/dml-syntax#insert_statement) statement. &#42; `UPDATE`: [`UPDATE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/query-syntax#update_statement) statement. &#42; `DELETE`: [`DELETE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-manipulation-language) statement. &#42; `MERGE`: [`MERGE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-manipulation-language) statement. &#42; `CREATE_TABLE`: [`CREATE TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_table_statement) statement, without `AS SELECT`. &#42; `CREATE_TABLE_AS_SELECT`: [`CREATE TABLE AS SELECT`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#query_statement) statement. &#42; `CREATE_VIEW`: [`CREATE VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_view_statement) statement. &#42; `CREATE_MODEL`: [`CREATE MODEL`](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-create#create_model_statement) statement. &#42; `CREATE_MATERIALIZED_VIEW`: [`CREATE MATERIALIZED VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_materialized_view_statement) statement. &#42; `CREATE_FUNCTION`: [`CREATE FUNCTION`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_function_statement) statement. &#42; `CREATE_TABLE_FUNCTION`: [`CREATE TABLE FUNCTION`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_table_function_statement) statement. &#42; `CREATE_PROCEDURE`: [`CREATE PROCEDURE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_procedure) statement. &#42; `CREATE_ROW_ACCESS_POLICY`: [`CREATE ROW ACCESS POLICY`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_row_access_policy_statement) statement. &#42; `CREATE_SCHEMA`: [`CREATE SCHEMA`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_schema_statement) statement. &#42; `CREATE_SNAPSHOT_TABLE`: [`CREATE SNAPSHOT TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_snapshot_table_statement) statement. &#42; `CREATE_SEARCH_INDEX`: [`CREATE SEARCH INDEX`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_search_index_statement) statement. &#42; `DROP_TABLE`: [`DROP TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_table_statement) statement. &#42; `DROP_EXTERNAL_TABLE`: [`DROP EXTERNAL TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_external_table_statement) statement. &#42; `DROP_VIEW`: [`DROP VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_view_statement) statement. &#42; `DROP_MODEL`: [`DROP MODEL`](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-drop-model) statement. &#42; `DROP_MATERIALIZED_VIEW`: [`DROP MATERIALIZED VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_materialized_view_statement) statement. &#42; `DROP_FUNCTION` : [`DROP FUNCTION`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_function_statement) statement. &#42; `DROP_TABLE_FUNCTION` : [`DROP TABLE FUNCTION`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_table_function) statement. &#42; `DROP_PROCEDURE`: [`DROP PROCEDURE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_procedure_statement) statement. &#42; `DROP_SEARCH_INDEX`: [`DROP SEARCH INDEX`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_search_index) statement. &#42; `DROP_SCHEMA`: [`DROP SCHEMA`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_schema_statement) statement. &#42; `DROP_SNAPSHOT_TABLE`: [`DROP SNAPSHOT TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_snapshot_table_statement) statement. &#42; `DROP_ROW_ACCESS_POLICY`: [`DROP [ALL] ROW ACCESS POLICY|POLICIES`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#drop_row_access_policy_statement) statement. &#42; `ALTER_TABLE`: [`ALTER TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#alter_table_set_options_statement) statement. &#42; `ALTER_VIEW`: [`ALTER VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#alter_view_set_options_statement) statement. &#42; `ALTER_MATERIALIZED_VIEW`: [`ALTER MATERIALIZED VIEW`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#alter_materialized_view_set_options_statement) statement. &#42; `ALTER_SCHEMA`: [`ALTER SCHEMA`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#aalter_schema_set_options_statement) statement. &#42; `SCRIPT`: [`SCRIPT`](https://cloud.google.com/bigquery/docs/reference/standard-sql/procedural-language). &#42; `TRUNCATE_TABLE`: [`TRUNCATE TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/dml-syntax#truncate_table_statement) statement. &#42; `CREATE_EXTERNAL_TABLE`: [`CREATE EXTERNAL TABLE`](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#create_external_table_statement) statement. &#42; `EXPORT_DATA`: [`EXPORT DATA`](https://cloud.google.com/bigquery/docs/reference/standard-sql/other-statements#export_data_statement) statement. &#42; `EXPORT_MODEL`: [`EXPORT MODEL`](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-export-model) statement. &#42; `LOAD_DATA`: [`LOAD DATA`](https://cloud.google.com/bigquery/docs/reference/standard-sql/other-statements#load_data_statement) statement. &#42; `CALL`: [`CALL`](https://cloud.google.com/bigquery/docs/reference/standard-sql/procedural-language#call) statement. */
		statementType: Option[String] = None,
	  /** Output only. Describes a timeline of job execution. */
		timeline: Option[List[Schema.QueryTimelineSample]] = None,
	  /** Output only. If the project is configured to use on-demand pricing, then this field contains the total bytes billed for the job. If the project is configured to use flat-rate pricing, then you are not billed for bytes and this field is informational only. */
		totalBytesBilled: Option[String] = None,
	  /** Output only. Total bytes processed for the job. */
		totalBytesProcessed: Option[String] = None,
	  /** Output only. For dry-run jobs, totalBytesProcessed is an estimate and this field specifies the accuracy of the estimate. Possible values can be: UNKNOWN: accuracy of the estimate is unknown. PRECISE: estimate is precise. LOWER_BOUND: estimate is lower bound of what the query would cost. UPPER_BOUND: estimate is upper bound of what the query would cost. */
		totalBytesProcessedAccuracy: Option[String] = None,
	  /** Output only. Total number of partitions processed from all partitioned tables referenced in the job. */
		totalPartitionsProcessed: Option[String] = None,
	  /** Output only. Slot-milliseconds for the job. */
		totalSlotMs: Option[String] = None,
	  /** Output only. Total bytes transferred for cross-cloud queries such as Cross Cloud Transfer and CREATE TABLE AS SELECT (CTAS). */
		transferredBytes: Option[String] = None,
	  /** Output only. GoogleSQL only: list of undeclared query parameters detected during a dry run validation. */
		undeclaredQueryParameters: Option[List[Schema.QueryParameter]] = None,
	  /** Output only. Vector Search query specific statistics. */
		vectorSearchStatistics: Option[Schema.VectorSearchStatistics] = None
	)
	
	case class JobStatistics3(
	  /** Output only. The number of bad records encountered. Note that if the job has failed because of more bad records encountered than the maximum allowed in the load job configuration, then this number can be less than the total number of bad records present in the input data. */
		badRecords: Option[String] = None,
	  /** Output only. Number of bytes of source data in a load job. */
		inputFileBytes: Option[String] = None,
	  /** Output only. Number of source files in a load job. */
		inputFiles: Option[String] = None,
	  /** Output only. Size of the loaded data in bytes. Note that while a load job is in the running state, this value may change. */
		outputBytes: Option[String] = None,
	  /** Output only. Number of rows imported in a load job. Note that while an import job is in the running state, this value may change. */
		outputRows: Option[String] = None,
	  /** Output only. Describes a timeline of job execution. */
		timeline: Option[List[Schema.QueryTimelineSample]] = None
	)
	
	case class JobStatistics4(
	  /** Output only. Number of files per destination URI or URI pattern specified in the extract configuration. These values will be in the same order as the URIs specified in the 'destinationUris' field. */
		destinationUriFileCounts: Option[List[String]] = None,
	  /** Output only. Number of user bytes extracted into the result. This is the byte count as computed by BigQuery for billing purposes and doesn't have any relationship with the number of actual result bytes extracted in the desired format. */
		inputBytes: Option[String] = None,
	  /** Output only. Describes a timeline of job execution. */
		timeline: Option[List[Schema.QueryTimelineSample]] = None
	)
	
	case class JobStatistics5(
	  /** Output only. Number of logical bytes copied to the destination table. */
		copiedLogicalBytes: Option[String] = None,
	  /** Output only. Number of rows copied to the destination table. */
		copiedRows: Option[String] = None
	)
	
	case class JobStatus(
	  /** Output only. Final error result of the job. If present, indicates that the job has completed and was unsuccessful. */
		errorResult: Option[Schema.ErrorProto] = None,
	  /** Output only. The first errors encountered during the running of the job. The final message includes the number of errors that caused the process to stop. Errors here do not necessarily mean that the job has not completed or was unsuccessful. */
		errors: Option[List[Schema.ErrorProto]] = None,
	  /** Output only. Running state of the job. Valid states include 'PENDING', 'RUNNING', and 'DONE'. */
		state: Option[String] = None
	)
	
	object JoinRestrictionPolicy {
		enum JoinConditionEnum extends Enum[JoinConditionEnum] { case JOIN_CONDITION_UNSPECIFIED, JOIN_ANY, JOIN_ALL, JOIN_NOT_REQUIRED, JOIN_BLOCKED }
	}
	case class JoinRestrictionPolicy(
	  /** Optional. The only columns that joins are allowed on. This field is must be specified for join_conditions JOIN_ANY and JOIN_ALL and it cannot be set for JOIN_BLOCKED. */
		joinAllowedColumns: Option[List[String]] = None,
	  /** Optional. Specifies if a join is required or not on queries for the view. Default is JOIN_CONDITION_UNSPECIFIED. */
		joinCondition: Option[Schema.JoinRestrictionPolicy.JoinConditionEnum] = None
	)
	
	case class JsonObject(
	
	)
	
	case class JsonOptions(
	  /** Optional. The character encoding of the data. The supported values are UTF-8, UTF-16BE, UTF-16LE, UTF-32BE, and UTF-32LE. The default value is UTF-8. */
		encoding: Option[String] = None
	)
	
	case class JsonValue(
	
	)
	
	object LinkedDatasetMetadata {
		enum LinkStateEnum extends Enum[LinkStateEnum] { case LINK_STATE_UNSPECIFIED, LINKED, UNLINKED }
	}
	case class LinkedDatasetMetadata(
	  /** Output only. Specifies whether Linked Dataset is currently in a linked state or not. */
		linkState: Option[Schema.LinkedDatasetMetadata.LinkStateEnum] = None
	)
	
	case class LinkedDatasetSource(
	  /** The source dataset reference contains project numbers and not project ids. */
		sourceDataset: Option[Schema.DatasetReference] = None
	)
	
	case class ListModelsResponse(
	  /** Models in the requested dataset. Only the following fields are populated: model_reference, model_type, creation_time, last_modified_time and labels. */
		models: Option[List[Schema.Model]] = None,
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListRoutinesResponse(
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Routines in the requested dataset. Unless read_mask is set in the request, only the following fields are populated: etag, project_id, dataset_id, routine_id, routine_type, creation_time, last_modified_time, language, and remote_function_options. */
		routines: Option[List[Schema.Routine]] = None
	)
	
	case class ListRowAccessPoliciesResponse(
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Row access policies on the requested table. */
		rowAccessPolicies: Option[List[Schema.RowAccessPolicy]] = None
	)
	
	case class LoadQueryStatistics(
	  /** Output only. The number of bad records encountered while processing a LOAD query. Note that if the job has failed because of more bad records encountered than the maximum allowed in the load job configuration, then this number can be less than the total number of bad records present in the input data. */
		badRecords: Option[String] = None,
	  /** Output only. This field is deprecated. The number of bytes of source data copied over the network for a `LOAD` query. `transferred_bytes` has the canonical value for physical transferred bytes, which is used for BigQuery Omni billing. */
		bytesTransferred: Option[String] = None,
	  /** Output only. Number of bytes of source data in a LOAD query. */
		inputFileBytes: Option[String] = None,
	  /** Output only. Number of source files in a LOAD query. */
		inputFiles: Option[String] = None,
	  /** Output only. Size of the loaded data in bytes. Note that while a LOAD query is in the running state, this value may change. */
		outputBytes: Option[String] = None,
	  /** Output only. Number of rows imported in a LOAD query. Note that while a LOAD query is in the running state, this value may change. */
		outputRows: Option[String] = None
	)
	
	case class LocationMetadata(
	  /** The legacy BigQuery location ID, e.g. “EU” for the “europe” location. This is for any API consumers that need the legacy “US” and “EU” locations. */
		legacyLocationId: Option[String] = None
	)
	
	object MaterializedView {
		enum RejectedReasonEnum extends Enum[RejectedReasonEnum] { case REJECTED_REASON_UNSPECIFIED, NO_DATA, COST, BASE_TABLE_TRUNCATED, BASE_TABLE_DATA_CHANGE, BASE_TABLE_PARTITION_EXPIRATION_CHANGE, BASE_TABLE_EXPIRED_PARTITION, BASE_TABLE_INCOMPATIBLE_METADATA_CHANGE, TIME_ZONE, OUT_OF_TIME_TRAVEL_WINDOW, BASE_TABLE_FINE_GRAINED_SECURITY_POLICY, BASE_TABLE_TOO_STALE }
	}
	case class MaterializedView(
	  /** Whether the materialized view is chosen for the query. A materialized view can be chosen to rewrite multiple parts of the same query. If a materialized view is chosen to rewrite any part of the query, then this field is true, even if the materialized view was not chosen to rewrite others parts. */
		chosen: Option[Boolean] = None,
	  /** If present, specifies a best-effort estimation of the bytes saved by using the materialized view rather than its base tables. */
		estimatedBytesSaved: Option[String] = None,
	  /** If present, specifies the reason why the materialized view was not chosen for the query. */
		rejectedReason: Option[Schema.MaterializedView.RejectedReasonEnum] = None,
	  /** The candidate materialized view. */
		tableReference: Option[Schema.TableReference] = None
	)
	
	case class MaterializedViewDefinition(
	  /** Optional. This option declares the intention to construct a materialized view that isn't refreshed incrementally. */
		allowNonIncrementalDefinition: Option[Boolean] = None,
	  /** Optional. Enable automatic refresh of the materialized view when the base table is updated. The default value is "true". */
		enableRefresh: Option[Boolean] = None,
	  /** Output only. The time when this materialized view was last refreshed, in milliseconds since the epoch. */
		lastRefreshTime: Option[String] = None,
	  /** [Optional] Max staleness of data that could be returned when materizlized view is queried (formatted as Google SQL Interval type). */
		maxStaleness: Option[String] = None,
	  /** Required. A query whose results are persisted. */
		query: Option[String] = None,
	  /** Optional. The maximum frequency at which this materialized view will be refreshed. The default value is "1800000" (30 minutes). */
		refreshIntervalMs: Option[String] = None
	)
	
	case class MaterializedViewStatistics(
	  /** Materialized views considered for the query job. Only certain materialized views are used. For a detailed list, see the child message. If many materialized views are considered, then the list might be incomplete. */
		materializedView: Option[List[Schema.MaterializedView]] = None
	)
	
	case class MaterializedViewStatus(
	  /** Output only. Error result of the last automatic refresh. If present, indicates that the last automatic refresh was unsuccessful. */
		lastRefreshStatus: Option[Schema.ErrorProto] = None,
	  /** Output only. Refresh watermark of materialized view. The base tables' data were collected into the materialized view cache until this time. */
		refreshWatermark: Option[String] = None
	)
	
	case class MetadataCacheStatistics(
	  /** Set for the Metadata caching eligible tables referenced in the query. */
		tableMetadataCacheUsage: Option[List[Schema.TableMetadataCacheUsage]] = None
	)
	
	object MlStatistics {
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, LINEAR_REGRESSION, LOGISTIC_REGRESSION, KMEANS, MATRIX_FACTORIZATION, DNN_CLASSIFIER, TENSORFLOW, DNN_REGRESSOR, XGBOOST, BOOSTED_TREE_REGRESSOR, BOOSTED_TREE_CLASSIFIER, ARIMA, AUTOML_REGRESSOR, AUTOML_CLASSIFIER, PCA, DNN_LINEAR_COMBINED_CLASSIFIER, DNN_LINEAR_COMBINED_REGRESSOR, AUTOENCODER, ARIMA_PLUS, ARIMA_PLUS_XREG, RANDOM_FOREST_REGRESSOR, RANDOM_FOREST_CLASSIFIER, TENSORFLOW_LITE, ONNX, TRANSFORM_ONLY, CONTRIBUTION_ANALYSIS }
		enum TrainingTypeEnum extends Enum[TrainingTypeEnum] { case TRAINING_TYPE_UNSPECIFIED, SINGLE_TRAINING, HPARAM_TUNING }
	}
	case class MlStatistics(
	  /** Output only. Trials of a [hyperparameter tuning job](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) sorted by trial_id. */
		hparamTrials: Option[List[Schema.HparamTuningTrial]] = None,
	  /** Results for all completed iterations. Empty for [hyperparameter tuning jobs](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview). */
		iterationResults: Option[List[Schema.IterationResult]] = None,
	  /** Output only. Maximum number of iterations specified as max_iterations in the 'CREATE MODEL' query. The actual number of iterations may be less than this number due to early stop. */
		maxIterations: Option[String] = None,
	  /** Output only. The type of the model that is being trained. */
		modelType: Option[Schema.MlStatistics.ModelTypeEnum] = None,
	  /** Output only. Training type of the job. */
		trainingType: Option[Schema.MlStatistics.TrainingTypeEnum] = None
	)
	
	object Model {
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, LINEAR_REGRESSION, LOGISTIC_REGRESSION, KMEANS, MATRIX_FACTORIZATION, DNN_CLASSIFIER, TENSORFLOW, DNN_REGRESSOR, XGBOOST, BOOSTED_TREE_REGRESSOR, BOOSTED_TREE_CLASSIFIER, ARIMA, AUTOML_REGRESSOR, AUTOML_CLASSIFIER, PCA, DNN_LINEAR_COMBINED_CLASSIFIER, DNN_LINEAR_COMBINED_REGRESSOR, AUTOENCODER, ARIMA_PLUS, ARIMA_PLUS_XREG, RANDOM_FOREST_REGRESSOR, RANDOM_FOREST_CLASSIFIER, TENSORFLOW_LITE, ONNX, TRANSFORM_ONLY, CONTRIBUTION_ANALYSIS }
	}
	case class Model(
	  /** The best trial_id across all training runs. */
		bestTrialId: Option[String] = None,
	  /** Output only. The time when this model was created, in millisecs since the epoch. */
		creationTime: Option[String] = None,
	  /** Output only. The default trial_id to use in TVFs when the trial_id is not passed in. For single-objective [hyperparameter tuning](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) models, this is the best trial ID. For multi-objective [hyperparameter tuning](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) models, this is the smallest trial ID among all Pareto optimal trials. */
		defaultTrialId: Option[String] = None,
	  /** Optional. A user-friendly description of this model. */
		description: Option[String] = None,
	  /** Custom encryption configuration (e.g., Cloud KMS keys). This shows the encryption configuration of the model data while stored in BigQuery storage. This field can be used with PatchModel to update encryption key for an already encrypted model. */
		encryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** Output only. A hash of this resource. */
		etag: Option[String] = None,
	  /** Optional. The time when this model expires, in milliseconds since the epoch. If not present, the model will persist indefinitely. Expired models will be deleted and their storage reclaimed. The defaultTableExpirationMs property of the encapsulating dataset can be used to set a default expirationTime on newly created models. */
		expirationTime: Option[String] = None,
	  /** Output only. Input feature columns for the model inference. If the model is trained with TRANSFORM clause, these are the input of the TRANSFORM clause. */
		featureColumns: Option[List[Schema.StandardSqlField]] = None,
	  /** Optional. A descriptive name for this model. */
		friendlyName: Option[String] = None,
	  /** Output only. All hyperparameter search spaces in this model. */
		hparamSearchSpaces: Option[Schema.HparamSearchSpaces] = None,
	  /** Output only. Trials of a [hyperparameter tuning](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) model sorted by trial_id. */
		hparamTrials: Option[List[Schema.HparamTuningTrial]] = None,
	  /** Output only. Label columns that were used to train this model. The output of the model will have a "predicted_" prefix to these columns. */
		labelColumns: Option[List[Schema.StandardSqlField]] = None,
	  /** The labels associated with this model. You can use these to organize and group your models. Label keys and values can be no longer than 63 characters, can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter and each label in the list must have a different key. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when this model was last modified, in millisecs since the epoch. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. The geographic location where the model resides. This value is inherited from the dataset. */
		location: Option[String] = None,
	  /** Required. Unique identifier for this model. */
		modelReference: Option[Schema.ModelReference] = None,
	  /** Output only. Type of the model resource. */
		modelType: Option[Schema.Model.ModelTypeEnum] = None,
	  /** Output only. For single-objective [hyperparameter tuning](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) models, it only contains the best trial. For multi-objective [hyperparameter tuning](https://cloud.google.com/bigquery-ml/docs/reference/standard-sql/bigqueryml-syntax-hp-tuning-overview) models, it contains all Pareto optimal trials sorted by trial_id. */
		optimalTrialIds: Option[List[String]] = None,
	  /** Output only. Remote model info */
		remoteModelInfo: Option[Schema.RemoteModelInfo] = None,
	  /** Information for all training runs in increasing order of start_time. */
		trainingRuns: Option[List[Schema.TrainingRun]] = None,
	  /** Output only. This field will be populated if a TRANSFORM clause was used to train a model. TRANSFORM clause (if used) takes feature_columns as input and outputs transform_columns. transform_columns then are used to train the model. */
		transformColumns: Option[List[Schema.TransformColumn]] = None
	)
	
	object ModelDefinition {
		case class ModelOptionsItem(
			labels: Option[List[String]] = None,
			lossType: Option[String] = None,
			modelType: Option[String] = None
		)
	}
	case class ModelDefinition(
	  /** Deprecated. */
		modelOptions: Option[Schema.ModelDefinition.ModelOptionsItem] = None,
	  /** Deprecated. */
		trainingRuns: Option[List[Schema.BqmlTrainingRun]] = None
	)
	
	case class ModelExtractOptions(
	  /** The 1-based ID of the trial to be exported from a hyperparameter tuning model. If not specified, the trial with id = [Model](https://cloud.google.com/bigquery/docs/reference/rest/v2/models#resource:-model).defaultTrialId is exported. This field is ignored for models not trained with hyperparameter tuning. */
		trialId: Option[String] = None
	)
	
	case class ModelReference(
	  /** Required. The ID of the dataset containing this model. */
		datasetId: Option[String] = None,
	  /** Required. The ID of the model. The ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). The maximum length is 1,024 characters. */
		modelId: Option[String] = None,
	  /** Required. The ID of the project containing this model. */
		projectId: Option[String] = None
	)
	
	case class MultiClassClassificationMetrics(
	  /** Aggregate classification metrics. */
		aggregateClassificationMetrics: Option[Schema.AggregateClassificationMetrics] = None,
	  /** Confusion matrix at different thresholds. */
		confusionMatrixList: Option[List[Schema.ConfusionMatrix]] = None
	)
	
	object ParquetOptions {
		enum MapTargetTypeEnum extends Enum[MapTargetTypeEnum] { case MAP_TARGET_TYPE_UNSPECIFIED, ARRAY_OF_STRUCT }
	}
	case class ParquetOptions(
	  /** Optional. Indicates whether to use schema inference specifically for Parquet LIST logical type. */
		enableListInference: Option[Boolean] = None,
	  /** Optional. Indicates whether to infer Parquet ENUM logical type as STRING instead of BYTES by default. */
		enumAsString: Option[Boolean] = None,
	  /** Optional. Indicates how to represent a Parquet map if present. */
		mapTargetType: Option[Schema.ParquetOptions.MapTargetTypeEnum] = None
	)
	
	case class PartitionSkew(
	  /** Output only. Source stages which produce skewed data. */
		skewSources: Option[List[Schema.SkewSource]] = None
	)
	
	case class PartitionedColumn(
	  /** Required. The name of the partition column. */
		field: Option[String] = None
	)
	
	case class PartitioningDefinition(
	  /** Optional. Details about each partitioning column. This field is output only for all partitioning types other than metastore partitioned tables. BigQuery native tables only support 1 partitioning column. Other table types may support 0, 1 or more partitioning columns. For metastore partitioned tables, the order must match the definition order in the Hive Metastore, where it must match the physical layout of the table. For example, CREATE TABLE a_table(id BIGINT, name STRING) PARTITIONED BY (city STRING, state STRING). In this case the values must be ['city', 'state'] in that order. */
		partitionedColumn: Option[List[Schema.PartitionedColumn]] = None
	)
	
	case class PerformanceInsights(
	  /** Output only. Average execution ms of previous runs. Indicates the job ran slow compared to previous executions. To find previous executions, use INFORMATION_SCHEMA tables and filter jobs with same query hash. */
		avgPreviousExecutionMs: Option[String] = None,
	  /** Output only. Query stage performance insights compared to previous runs, for diagnosing performance regression. */
		stagePerformanceChangeInsights: Option[List[Schema.StagePerformanceChangeInsight]] = None,
	  /** Output only. Standalone query stage performance insights, for exploring potential improvements. */
		stagePerformanceStandaloneInsights: Option[List[Schema.StagePerformanceStandaloneInsight]] = None
	)
	
	case class Policy(
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None
	)
	
	case class PrincipalComponentInfo(
	  /** The explained_variance is pre-ordered in the descending order to compute the cumulative explained variance ratio. */
		cumulativeExplainedVarianceRatio: Option[BigDecimal] = None,
	  /** Explained variance by this principal component, which is simply the eigenvalue. */
		explainedVariance: Option[BigDecimal] = None,
	  /** Explained_variance over the total explained variance. */
		explainedVarianceRatio: Option[BigDecimal] = None,
	  /** Id of the principal component. */
		principalComponentId: Option[String] = None
	)
	
	case class PrivacyPolicy(
	  /** Optional. Policy used for aggregation thresholds. */
		aggregationThresholdPolicy: Option[Schema.AggregationThresholdPolicy] = None,
	  /** Optional. Policy used for differential privacy. */
		differentialPrivacyPolicy: Option[Schema.DifferentialPrivacyPolicy] = None,
	  /** Optional. Join restriction policy is outside of the one of policies, since this policy can be set along with other policies. This policy gives data providers the ability to enforce joins on the 'join_allowed_columns' when data is queried from a privacy protected view. */
		joinRestrictionPolicy: Option[Schema.JoinRestrictionPolicy] = None
	)
	
	object ProjectList {
		case class ProjectsItem(
		  /** A descriptive name for this project. A wrapper is used here because friendlyName can be set to the empty string. */
			friendlyName: Option[String] = None,
		  /** An opaque ID of this project. */
			id: Option[String] = None,
		  /** The resource type. */
			kind: Option[String] = None,
		  /** The numeric ID of this project. */
			numericId: Option[String] = None,
		  /** A unique reference to this project. */
			projectReference: Option[Schema.ProjectReference] = None
		)
	}
	case class ProjectList(
	  /** A hash of the page of results. */
		etag: Option[String] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#projectList"""),
	  /** Use this token to request the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Projects to which the user has at least READ access. */
		projects: Option[List[Schema.ProjectList.ProjectsItem]] = None,
	  /** The total number of projects in the page. A wrapper is used here because the field should still be in the response when the value is 0. */
		totalItems: Option[Int] = None
	)
	
	case class ProjectReference(
	  /** Required. ID of the project. Can be either the numeric ID or the assigned ID of the project. */
		projectId: Option[String] = None
	)
	
	case class QueryInfo(
	  /** Output only. Information about query optimizations. */
		optimizationDetails: Option[Map[String, JsValue]] = None
	)
	
	case class QueryParameter(
	  /** Optional. If unset, this is a positional parameter. Otherwise, should be unique within a query. */
		name: Option[String] = None,
	  /** Required. The type of this parameter. */
		parameterType: Option[Schema.QueryParameterType] = None,
	  /** Required. The value of this parameter. */
		parameterValue: Option[Schema.QueryParameterValue] = None
	)
	
	object QueryParameterType {
		case class StructTypesItem(
		  /** Optional. Human-oriented description of the field. */
			description: Option[String] = None,
		  /** Optional. The name of this field. */
			name: Option[String] = None,
		  /** Required. The type of this field. */
			`type`: Option[Schema.QueryParameterType] = None
		)
	}
	case class QueryParameterType(
	  /** Optional. The type of the array's elements, if this is an array. */
		arrayType: Option[Schema.QueryParameterType] = None,
	  /** Optional. The element type of the range, if this is a range. */
		rangeElementType: Option[Schema.QueryParameterType] = None,
	  /** Optional. The types of the fields of this struct, in order, if this is a struct. */
		structTypes: Option[List[Schema.QueryParameterType.StructTypesItem]] = None,
	  /** Required. The top level type of this field. */
		`type`: Option[String] = None
	)
	
	case class QueryParameterValue(
	  /** Optional. The array values, if this is an array type. */
		arrayValues: Option[List[Schema.QueryParameterValue]] = None,
	  /** Optional. The range value, if this is a range type. */
		rangeValue: Option[Schema.RangeValue] = None,
	  /** The struct field values. */
		structValues: Option[Map[String, Schema.QueryParameterValue]] = None,
	  /** Optional. The value of this value, if a simple scalar type. */
		value: Option[String] = None
	)
	
	object QueryRequest {
		enum JobCreationModeEnum extends Enum[JobCreationModeEnum] { case JOB_CREATION_MODE_UNSPECIFIED, JOB_CREATION_REQUIRED, JOB_CREATION_OPTIONAL }
	}
	case class QueryRequest(
	  /** Optional. Connection properties which can modify the query behavior. */
		connectionProperties: Option[List[Schema.ConnectionProperty]] = None,
	  /** [Optional] Specifies whether the query should be executed as a continuous query. The default value is false. */
		continuous: Option[Boolean] = None,
	  /** Optional. If true, creates a new session using a randomly generated session_id. If false, runs query with an existing session_id passed in ConnectionProperty, otherwise runs query in non-session mode. The session location will be set to QueryRequest.location if it is present, otherwise it's set to the default location based on existing routing logic. */
		createSession: Option[Boolean] = None,
	  /** Optional. Specifies the default datasetId and projectId to assume for any unqualified table names in the query. If not set, all table names in the query string must be qualified in the format 'datasetId.tableId'. */
		defaultDataset: Option[Schema.DatasetReference] = None,
	  /** Optional. If set to true, BigQuery doesn't run the job. Instead, if the query is valid, BigQuery returns statistics about the job such as how many bytes would be processed. If the query is invalid, an error returns. The default value is false. */
		dryRun: Option[Boolean] = None,
	  /** Optional. Output format adjustments. */
		formatOptions: Option[Schema.DataFormatOptions] = None,
	  /** Optional. If not set, jobs are always required. If set, the query request will follow the behavior described JobCreationMode. [Preview](https://cloud.google.com/products/#product-launch-stages) */
		jobCreationMode: Option[Schema.QueryRequest.JobCreationModeEnum] = None,
	  /** The resource type of the request. */
		kind: Option[String] = Some("""bigquery#queryRequest"""),
	  /** Optional. The labels associated with this query. Labels can be used to organize and group query jobs. Label keys and values can be no longer than 63 characters, can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label keys must start with a letter and each label in the list must have a different key. */
		labels: Option[Map[String, String]] = None,
	  /** The geographic location where the job should run. See details at https://cloud.google.com/bigquery/docs/locations#specifying_your_location. */
		location: Option[String] = None,
	  /** Optional. The maximum number of rows of data to return per page of results. Setting this flag to a small value such as 1000 and then paging through results might improve reliability when the query result set is large. In addition to this limit, responses are also limited to 10 MB. By default, there is no maximum row count, and only the byte limit applies. */
		maxResults: Option[Int] = None,
	  /** Optional. Limits the bytes billed for this query. Queries with bytes billed above this limit will fail (without incurring a charge). If unspecified, the project default is used. */
		maximumBytesBilled: Option[String] = None,
	  /** GoogleSQL only. Set to POSITIONAL to use positional (?) query parameters or to NAMED to use named (@myparam) query parameters in this query. */
		parameterMode: Option[String] = None,
	  /** This property is deprecated. */
		preserveNulls: Option[Boolean] = None,
	  /** Required. A query string to execute, using Google Standard SQL or legacy SQL syntax. Example: "SELECT COUNT(f1) FROM myProjectId.myDatasetId.myTableId". */
		query: Option[String] = None,
	  /** Query parameters for GoogleSQL queries. */
		queryParameters: Option[List[Schema.QueryParameter]] = None,
	  /** Optional. A unique user provided identifier to ensure idempotent behavior for queries. Note that this is different from the job_id. It has the following properties: 1. It is case-sensitive, limited to up to 36 ASCII characters. A UUID is recommended. 2. Read only queries can ignore this token since they are nullipotent by definition. 3. For the purposes of idempotency ensured by the request_id, a request is considered duplicate of another only if they have the same request_id and are actually duplicates. When determining whether a request is a duplicate of another request, all parameters in the request that may affect the result are considered. For example, query, connection_properties, query_parameters, use_legacy_sql are parameters that affect the result and are considered when determining whether a request is a duplicate, but properties like timeout_ms don't affect the result and are thus not considered. Dry run query requests are never considered duplicate of another request. 4. When a duplicate mutating query request is detected, it returns: a. the results of the mutation if it completes successfully within the timeout. b. the running operation if it is still in progress at the end of the timeout. 5. Its lifetime is limited to 15 minutes. In other words, if two requests are sent with the same request_id, but more than 15 minutes apart, idempotency is not guaranteed. */
		requestId: Option[String] = None,
	  /** Optional. Optional: Specifies the maximum amount of time, in milliseconds, that the client is willing to wait for the query to complete. By default, this limit is 10 seconds (10,000 milliseconds). If the query is complete, the jobComplete field in the response is true. If the query has not yet completed, jobComplete is false. You can request a longer timeout period in the timeoutMs field. However, the call is not guaranteed to wait for the specified timeout; it typically returns after around 200 seconds (200,000 milliseconds), even if the query is not complete. If jobComplete is false, you can continue to wait for the query to complete by calling the getQueryResults method until the jobComplete field in the getQueryResults response is true. */
		timeoutMs: Option[Int] = None,
	  /** Specifies whether to use BigQuery's legacy SQL dialect for this query. The default value is true. If set to false, the query will use BigQuery's GoogleSQL: https://cloud.google.com/bigquery/sql-reference/ When useLegacySql is set to false, the value of flattenResults is ignored; query will be run as if flattenResults is false. */
		useLegacySql: Option[Boolean] = Some(true),
	  /** Optional. Whether to look for the result in the query cache. The query cache is a best-effort cache that will be flushed whenever tables in the query are modified. The default value is true. */
		useQueryCache: Option[Boolean] = Some(true)
	)
	
	case class QueryResponse(
	  /** Whether the query result was fetched from the query cache. */
		cacheHit: Option[Boolean] = None,
	  /** Output only. Detailed statistics for DML statements INSERT, UPDATE, DELETE, MERGE or TRUNCATE. */
		dmlStats: Option[Schema.DmlStatistics] = None,
	  /** Output only. The first errors or warnings encountered during the running of the job. The final message includes the number of errors that caused the process to stop. Errors here do not necessarily mean that the job has completed or was unsuccessful. For more information about error messages, see [Error messages](https://cloud.google.com/bigquery/docs/error-messages). */
		errors: Option[List[Schema.ErrorProto]] = None,
	  /** Whether the query has completed or not. If rows or totalRows are present, this will always be true. If this is false, totalRows will not be available. */
		jobComplete: Option[Boolean] = None,
	  /** Optional. The reason why a Job was created. Only relevant when a job_reference is present in the response. If job_reference is not present it will always be unset. [Preview](https://cloud.google.com/products/#product-launch-stages) */
		jobCreationReason: Option[Schema.JobCreationReason] = None,
	  /** Reference to the Job that was created to run the query. This field will be present even if the original request timed out, in which case GetQueryResults can be used to read the results once the query has completed. Since this API only returns the first page of results, subsequent pages can be fetched via the same mechanism (GetQueryResults). If job_creation_mode was set to `JOB_CREATION_OPTIONAL` and the query completes without creating a job, this field will be empty. */
		jobReference: Option[Schema.JobReference] = None,
	  /** The resource type. */
		kind: Option[String] = Some("""bigquery#queryResponse"""),
	  /** Output only. The number of rows affected by a DML statement. Present only for DML statements INSERT, UPDATE or DELETE. */
		numDmlAffectedRows: Option[String] = None,
	  /** A token used for paging results. A non-empty token indicates that additional results are available. To see additional results, query the [`jobs.getQueryResults`](https://cloud.google.com/bigquery/docs/reference/rest/v2/jobs/getQueryResults) method. For more information, see [Paging through table data](https://cloud.google.com/bigquery/docs/paging-results). */
		pageToken: Option[String] = None,
	  /** Auto-generated ID for the query. [Preview](https://cloud.google.com/products/#product-launch-stages) */
		queryId: Option[String] = None,
	  /** An object with as many results as can be contained within the maximum permitted reply size. To get any additional rows, you can call GetQueryResults and specify the jobReference returned above. */
		rows: Option[List[Schema.TableRow]] = None,
	  /** The schema of the results. Present only when the query completes successfully. */
		schema: Option[Schema.TableSchema] = None,
	  /** Output only. Information of the session if this job is part of one. */
		sessionInfo: Option[Schema.SessionInfo] = None,
	  /** The total number of bytes processed for this query. If this query was a dry run, this is the number of bytes that would be processed if the query were run. */
		totalBytesProcessed: Option[String] = None,
	  /** The total number of rows in the complete query result set, which can be more than the number of rows in this single page of results. */
		totalRows: Option[String] = None
	)
	
	case class QueryTimelineSample(
	  /** Total number of active workers. This does not correspond directly to slot usage. This is the largest value observed since the last sample. */
		activeUnits: Option[String] = None,
	  /** Total parallel units of work completed by this query. */
		completedUnits: Option[String] = None,
	  /** Milliseconds elapsed since the start of query execution. */
		elapsedMs: Option[String] = None,
	  /** Units of work that can be scheduled immediately. Providing additional slots for these units of work will accelerate the query, if no other query in the reservation needs additional slots. */
		estimatedRunnableUnits: Option[String] = None,
	  /** Total units of work remaining for the query. This number can be revised (increased or decreased) while the query is running. */
		pendingUnits: Option[String] = None,
	  /** Cumulative slot-ms consumed by the query. */
		totalSlotMs: Option[String] = None
	)
	
	object RangePartitioning {
		case class RangeItem(
		  /** [Experimental] The end of range partitioning, exclusive. */
			end: Option[String] = None,
		  /** [Experimental] The width of each interval. */
			interval: Option[String] = None,
		  /** [Experimental] The start of range partitioning, inclusive. */
			start: Option[String] = None
		)
	}
	case class RangePartitioning(
	  /** Required. The name of the column to partition the table on. It must be a top-level, INT64 column whose mode is NULLABLE or REQUIRED. */
		field: Option[String] = None,
	  /** [Experimental] Defines the ranges for range partitioning. */
		range: Option[Schema.RangePartitioning.RangeItem] = None
	)
	
	case class RangeValue(
	  /** Optional. The end value of the range. A missing value represents an unbounded end. */
		end: Option[Schema.QueryParameterValue] = None,
	  /** Optional. The start value of the range. A missing value represents an unbounded start. */
		start: Option[Schema.QueryParameterValue] = None
	)
	
	case class RankingMetrics(
	  /** Determines the goodness of a ranking by computing the percentile rank from the predicted confidence and dividing it by the original rank. */
		averageRank: Option[BigDecimal] = None,
	  /** Calculates a precision per user for all the items by ranking them and then averages all the precisions across all the users. */
		meanAveragePrecision: Option[BigDecimal] = None,
	  /** Similar to the mean squared error computed in regression and explicit recommendation models except instead of computing the rating directly, the output from evaluate is computed against a preference which is 1 or 0 depending on if the rating exists or not. */
		meanSquaredError: Option[BigDecimal] = None,
	  /** A metric to determine the goodness of a ranking calculated from the predicted confidence by comparing it to an ideal rank measured by the original ratings. */
		normalizedDiscountedCumulativeGain: Option[BigDecimal] = None
	)
	
	case class RegressionMetrics(
	  /** Mean absolute error. */
		meanAbsoluteError: Option[BigDecimal] = None,
	  /** Mean squared error. */
		meanSquaredError: Option[BigDecimal] = None,
	  /** Mean squared log error. */
		meanSquaredLogError: Option[BigDecimal] = None,
	  /** Median absolute error. */
		medianAbsoluteError: Option[BigDecimal] = None,
	  /** R^2 score. This corresponds to r2_score in ML.EVALUATE. */
		rSquared: Option[BigDecimal] = None
	)
	
	case class RemoteFunctionOptions(
	  /** Fully qualified name of the user-provided connection object which holds the authentication information to send requests to the remote service. Format: ```"projects/{projectId}/locations/{locationId}/connections/{connectionId}"``` */
		connection: Option[String] = None,
	  /** Endpoint of the user-provided remote service, e.g. ```https://us-east1-my_gcf_project.cloudfunctions.net/remote_add``` */
		endpoint: Option[String] = None,
	  /** Max number of rows in each batch sent to the remote service. If absent or if 0, BigQuery dynamically decides the number of rows in a batch. */
		maxBatchingRows: Option[String] = None,
	  /** User-defined context as a set of key/value pairs, which will be sent as function invocation context together with batched arguments in the requests to the remote service. The total number of bytes of keys and values must be less than 8KB. */
		userDefinedContext: Option[Map[String, String]] = None
	)
	
	object RemoteModelInfo {
		enum RemoteServiceTypeEnum extends Enum[RemoteServiceTypeEnum] { case REMOTE_SERVICE_TYPE_UNSPECIFIED, CLOUD_AI_TRANSLATE_V3, CLOUD_AI_VISION_V1, CLOUD_AI_NATURAL_LANGUAGE_V1, CLOUD_AI_SPEECH_TO_TEXT_V2 }
	}
	case class RemoteModelInfo(
	  /** Output only. Fully qualified name of the user-provided connection object of the remote model. Format: ```"projects/{project_id}/locations/{location_id}/connections/{connection_id}"``` */
		connection: Option[String] = None,
	  /** Output only. The endpoint for remote model. */
		endpoint: Option[String] = None,
	  /** Output only. Max number of rows in each batch sent to the remote service. If unset, the number of rows in each batch is set dynamically. */
		maxBatchingRows: Option[String] = None,
	  /** Output only. The model version for LLM. */
		remoteModelVersion: Option[String] = None,
	  /** Output only. The remote service type for remote model. */
		remoteServiceType: Option[Schema.RemoteModelInfo.RemoteServiceTypeEnum] = None,
	  /** Output only. The name of the speech recognizer to use for speech recognition. The expected format is `projects/{project}/locations/{location}/recognizers/{recognizer}`. Customers can specify this field at model creation. If not specified, a default recognizer `projects/{model project}/locations/global/recognizers/_` will be used. See more details at [recognizers](https://cloud.google.com/speech-to-text/v2/docs/reference/rest/v2/projects.locations.recognizers) */
		speechRecognizer: Option[String] = None
	)
	
	object RestrictionConfig {
		enum TypeEnum extends Enum[TypeEnum] { case RESTRICTION_TYPE_UNSPECIFIED, RESTRICTED_DATA_EGRESS }
	}
	case class RestrictionConfig(
	  /** Output only. Specifies the type of dataset/table restriction. */
		`type`: Option[Schema.RestrictionConfig.TypeEnum] = None
	)
	
	object Routine {
		enum DataGovernanceTypeEnum extends Enum[DataGovernanceTypeEnum] { case DATA_GOVERNANCE_TYPE_UNSPECIFIED, DATA_MASKING }
		enum DeterminismLevelEnum extends Enum[DeterminismLevelEnum] { case DETERMINISM_LEVEL_UNSPECIFIED, DETERMINISTIC, NOT_DETERMINISTIC }
		enum LanguageEnum extends Enum[LanguageEnum] { case LANGUAGE_UNSPECIFIED, SQL, JAVASCRIPT, PYTHON, JAVA, SCALA }
		enum RoutineTypeEnum extends Enum[RoutineTypeEnum] { case ROUTINE_TYPE_UNSPECIFIED, SCALAR_FUNCTION, PROCEDURE, TABLE_VALUED_FUNCTION, AGGREGATE_FUNCTION }
		enum SecurityModeEnum extends Enum[SecurityModeEnum] { case SECURITY_MODE_UNSPECIFIED, DEFINER, INVOKER }
	}
	case class Routine(
	  /** Optional. */
		arguments: Option[List[Schema.Argument]] = None,
	  /** Output only. The time when this routine was created, in milliseconds since the epoch. */
		creationTime: Option[String] = None,
	  /** Optional. If set to `DATA_MASKING`, the function is validated and made available as a masking function. For more information, see [Create custom masking routines](https://cloud.google.com/bigquery/docs/user-defined-functions#custom-mask). */
		dataGovernanceType: Option[Schema.Routine.DataGovernanceTypeEnum] = None,
	  /** Required. The body of the routine. For functions, this is the expression in the AS clause. If language=SQL, it is the substring inside (but excluding) the parentheses. For example, for the function created with the following statement: `CREATE FUNCTION JoinLines(x string, y string) as (concat(x, "\n", y))` The definition_body is `concat(x, "\n", y)` (\n is not replaced with linebreak). If language=JAVASCRIPT, it is the evaluated string in the AS clause. For example, for the function created with the following statement: `CREATE FUNCTION f() RETURNS STRING LANGUAGE js AS 'return "\n";\n'` The definition_body is `return "\n";\n` Note that both \n are replaced with linebreaks. */
		definitionBody: Option[String] = None,
	  /** Optional. The description of the routine, if defined. */
		description: Option[String] = None,
	  /** Optional. The determinism level of the JavaScript UDF, if defined. */
		determinismLevel: Option[Schema.Routine.DeterminismLevelEnum] = None,
	  /** Output only. A hash of this resource. */
		etag: Option[String] = None,
	  /** Optional. If language = "JAVASCRIPT", this field stores the path of the imported JAVASCRIPT libraries. */
		importedLibraries: Option[List[String]] = None,
	  /** Optional. Defaults to "SQL" if remote_function_options field is absent, not set otherwise. */
		language: Option[Schema.Routine.LanguageEnum] = None,
	  /** Output only. The time when this routine was last modified, in milliseconds since the epoch. */
		lastModifiedTime: Option[String] = None,
	  /** Optional. Remote function specific options. */
		remoteFunctionOptions: Option[Schema.RemoteFunctionOptions] = None,
	  /** Optional. Can be set only if routine_type = "TABLE_VALUED_FUNCTION". If absent, the return table type is inferred from definition_body at query time in each query that references this routine. If present, then the columns in the evaluated table result will be cast to match the column types specified in return table type, at query time. */
		returnTableType: Option[Schema.StandardSqlTableType] = None,
	  /** Optional if language = "SQL"; required otherwise. Cannot be set if routine_type = "TABLE_VALUED_FUNCTION". If absent, the return type is inferred from definition_body at query time in each query that references this routine. If present, then the evaluated result will be cast to the specified returned type at query time. For example, for the functions created with the following statements: &#42; `CREATE FUNCTION Add(x FLOAT64, y FLOAT64) RETURNS FLOAT64 AS (x + y);` &#42; `CREATE FUNCTION Increment(x FLOAT64) AS (Add(x, 1));` &#42; `CREATE FUNCTION Decrement(x FLOAT64) RETURNS FLOAT64 AS (Add(x, -1));` The return_type is `{type_kind: "FLOAT64"}` for `Add` and `Decrement`, and is absent for `Increment` (inferred as FLOAT64 at query time). Suppose the function `Add` is replaced by `CREATE OR REPLACE FUNCTION Add(x INT64, y INT64) AS (x + y);` Then the inferred return type of `Increment` is automatically changed to INT64 at query time, while the return type of `Decrement` remains FLOAT64. */
		returnType: Option[Schema.StandardSqlDataType] = None,
	  /** Required. Reference describing the ID of this routine. */
		routineReference: Option[Schema.RoutineReference] = None,
	  /** Required. The type of routine. */
		routineType: Option[Schema.Routine.RoutineTypeEnum] = None,
	  /** Optional. The security mode of the routine, if defined. If not defined, the security mode is automatically determined from the routine's configuration. */
		securityMode: Option[Schema.Routine.SecurityModeEnum] = None,
	  /** Optional. Spark specific options. */
		sparkOptions: Option[Schema.SparkOptions] = None,
	  /** Optional. Use this option to catch many common errors. Error checking is not exhaustive, and successfully creating a procedure doesn't guarantee that the procedure will successfully execute at runtime. If `strictMode` is set to `TRUE`, the procedure body is further checked for errors such as non-existent tables or columns. The `CREATE PROCEDURE` statement fails if the body fails any of these checks. If `strictMode` is set to `FALSE`, the procedure body is checked only for syntax. For procedures that invoke themselves recursively, specify `strictMode=FALSE` to avoid non-existent procedure errors during validation. Default value is `TRUE`. */
		strictMode: Option[Boolean] = None
	)
	
	case class RoutineReference(
	  /** Required. The ID of the dataset containing this routine. */
		datasetId: Option[String] = None,
	  /** Required. The ID of the project containing this routine. */
		projectId: Option[String] = None,
	  /** Required. The ID of the routine. The ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). The maximum length is 256 characters. */
		routineId: Option[String] = None
	)
	
	case class Row(
	  /** The original label of this row. */
		actualLabel: Option[String] = None,
	  /** Info describing predicted label distribution. */
		entries: Option[List[Schema.Entry]] = None
	)
	
	case class RowAccessPolicy(
	  /** Output only. The time when this row access policy was created, in milliseconds since the epoch. */
		creationTime: Option[String] = None,
	  /** Output only. A hash of this resource. */
		etag: Option[String] = None,
	  /** Required. A SQL boolean expression that represents the rows defined by this row access policy, similar to the boolean expression in a WHERE clause of a SELECT query on a table. References to other tables, routines, and temporary functions are not supported. Examples: region="EU" date_field = CAST('2019-9-27' as DATE) nullable_field is not NULL numeric_field BETWEEN 1.0 AND 5.0 */
		filterPredicate: Option[String] = None,
	  /** Output only. The time when this row access policy was last modified, in milliseconds since the epoch. */
		lastModifiedTime: Option[String] = None,
	  /** Required. Reference describing the ID of this row access policy. */
		rowAccessPolicyReference: Option[Schema.RowAccessPolicyReference] = None
	)
	
	case class RowAccessPolicyReference(
	  /** Required. The ID of the dataset containing this row access policy. */
		datasetId: Option[String] = None,
	  /** Required. The ID of the row access policy. The ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). The maximum length is 256 characters. */
		policyId: Option[String] = None,
	  /** Required. The ID of the project containing this row access policy. */
		projectId: Option[String] = None,
	  /** Required. The ID of the table containing this row access policy. */
		tableId: Option[String] = None
	)
	
	case class RowLevelSecurityStatistics(
	  /** Whether any accessed data was protected by row access policies. */
		rowLevelSecurityApplied: Option[Boolean] = None
	)
	
	object ScriptOptions {
		enum KeyResultStatementEnum extends Enum[KeyResultStatementEnum] { case KEY_RESULT_STATEMENT_KIND_UNSPECIFIED, LAST, FIRST_SELECT }
	}
	case class ScriptOptions(
	  /** Determines which statement in the script represents the "key result", used to populate the schema and query results of the script job. Default is LAST. */
		keyResultStatement: Option[Schema.ScriptOptions.KeyResultStatementEnum] = None,
	  /** Limit on the number of bytes billed per statement. Exceeding this budget results in an error. */
		statementByteBudget: Option[String] = None,
	  /** Timeout period for each statement in a script. */
		statementTimeoutMs: Option[String] = None
	)
	
	case class ScriptStackFrame(
	  /** Output only. One-based end column. */
		endColumn: Option[Int] = None,
	  /** Output only. One-based end line. */
		endLine: Option[Int] = None,
	  /** Output only. Name of the active procedure, empty if in a top-level script. */
		procedureId: Option[String] = None,
	  /** Output only. One-based start column. */
		startColumn: Option[Int] = None,
	  /** Output only. One-based start line. */
		startLine: Option[Int] = None,
	  /** Output only. Text of the current statement/expression. */
		text: Option[String] = None
	)
	
	object ScriptStatistics {
		enum EvaluationKindEnum extends Enum[EvaluationKindEnum] { case EVALUATION_KIND_UNSPECIFIED, STATEMENT, EXPRESSION }
	}
	case class ScriptStatistics(
	  /** Whether this child job was a statement or expression. */
		evaluationKind: Option[Schema.ScriptStatistics.EvaluationKindEnum] = None,
	  /** Stack trace showing the line/column/procedure name of each frame on the stack at the point where the current evaluation happened. The leaf frame is first, the primary script is last. Never empty. */
		stackFrames: Option[List[Schema.ScriptStackFrame]] = None
	)
	
	object SearchStatistics {
		enum IndexUsageModeEnum extends Enum[IndexUsageModeEnum] { case INDEX_USAGE_MODE_UNSPECIFIED, UNUSED, PARTIALLY_USED, FULLY_USED }
	}
	case class SearchStatistics(
	  /** When `indexUsageMode` is `UNUSED` or `PARTIALLY_USED`, this field explains why indexes were not used in all or part of the search query. If `indexUsageMode` is `FULLY_USED`, this field is not populated. */
		indexUnusedReasons: Option[List[Schema.IndexUnusedReason]] = None,
	  /** Specifies the index usage mode for the query. */
		indexUsageMode: Option[Schema.SearchStatistics.IndexUsageModeEnum] = None
	)
	
	case class SerDeInfo(
	  /** Optional. Name of the SerDe. The maximum length is 256 characters. */
		name: Option[String] = None,
	  /** Optional. Key-value pairs that define the initialization parameters for the serialization library. Maximum size 10 Kib. */
		parameters: Option[Map[String, String]] = None,
	  /** Required. Specifies a fully-qualified class name of the serialization library that is responsible for the translation of data between table representation and the underlying low-level input and output format structures. The maximum length is 256 characters. */
		serializationLibrary: Option[String] = None
	)
	
	case class SessionInfo(
	  /** Output only. The id of the session. */
		sessionId: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class SkewSource(
	  /** Output only. Stage id of the skew source stage. */
		stageId: Option[String] = None
	)
	
	case class SnapshotDefinition(
	  /** Required. Reference describing the ID of the table that was snapshot. */
		baseTableReference: Option[Schema.TableReference] = None,
	  /** Required. The time at which the base table was snapshot. This value is reported in the JSON response using RFC3339 format. */
		snapshotTime: Option[String] = None
	)
	
	case class SparkLoggingInfo(
	  /** Output only. Project ID where the Spark logs were written. */
		projectId: Option[String] = None,
	  /** Output only. Resource type used for logging. */
		resourceType: Option[String] = None
	)
	
	case class SparkOptions(
	  /** Archive files to be extracted into the working directory of each executor. For more information about Apache Spark, see [Apache Spark](https://spark.apache.org/docs/latest/index.html). */
		archiveUris: Option[List[String]] = None,
	  /** Fully qualified name of the user-provided Spark connection object. Format: ```"projects/{project_id}/locations/{location_id}/connections/{connection_id}"``` */
		connection: Option[String] = None,
	  /** Custom container image for the runtime environment. */
		containerImage: Option[String] = None,
	  /** Files to be placed in the working directory of each executor. For more information about Apache Spark, see [Apache Spark](https://spark.apache.org/docs/latest/index.html). */
		fileUris: Option[List[String]] = None,
	  /** JARs to include on the driver and executor CLASSPATH. For more information about Apache Spark, see [Apache Spark](https://spark.apache.org/docs/latest/index.html). */
		jarUris: Option[List[String]] = None,
	  /** The fully qualified name of a class in jar_uris, for example, com.example.wordcount. Exactly one of main_class and main_jar_uri field should be set for Java/Scala language type. */
		mainClass: Option[String] = None,
	  /** The main file/jar URI of the Spark application. Exactly one of the definition_body field and the main_file_uri field must be set for Python. Exactly one of main_class and main_file_uri field should be set for Java/Scala language type. */
		mainFileUri: Option[String] = None,
	  /** Configuration properties as a set of key/value pairs, which will be passed on to the Spark application. For more information, see [Apache Spark](https://spark.apache.org/docs/latest/index.html) and the [procedure option list](https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language#procedure_option_list). */
		properties: Option[Map[String, String]] = None,
	  /** Python files to be placed on the PYTHONPATH for PySpark application. Supported file types: `.py`, `.egg`, and `.zip`. For more information about Apache Spark, see [Apache Spark](https://spark.apache.org/docs/latest/index.html). */
		pyFileUris: Option[List[String]] = None,
	  /** Runtime version. If not specified, the default runtime version is used. */
		runtimeVersion: Option[String] = None
	)
	
	case class SparkStatistics(
	  /** Output only. Endpoints returned from Dataproc. Key list: - history_server_endpoint: A link to Spark job UI. */
		endpoints: Option[Map[String, String]] = None,
	  /** Output only. The Google Cloud Storage bucket that is used as the default file system by the Spark application. This field is only filled when the Spark procedure uses the invoker security mode. The `gcsStagingBucket` bucket is inferred from the `@@spark_proc_properties.staging_bucket` system variable (if it is provided). Otherwise, BigQuery creates a default staging bucket for the job and returns the bucket name in this field. Example: &#42; `gs://[bucket_name]` */
		gcsStagingBucket: Option[String] = None,
	  /** Output only. The Cloud KMS encryption key that is used to protect the resources created by the Spark job. If the Spark procedure uses the invoker security mode, the Cloud KMS encryption key is either inferred from the provided system variable, `@@spark_proc_properties.kms_key_name`, or the default key of the BigQuery job's project (if the CMEK organization policy is enforced). Otherwise, the Cloud KMS key is either inferred from the Spark connection associated with the procedure (if it is provided), or from the default key of the Spark connection's project if the CMEK organization policy is enforced. Example: &#42; `projects/[kms_project_id]/locations/[region]/keyRings/[key_region]/cryptoKeys/[key]` */
		kmsKeyName: Option[String] = None,
	  /** Output only. Logging info is used to generate a link to Cloud Logging. */
		loggingInfo: Option[Schema.SparkLoggingInfo] = None,
	  /** Output only. Spark job ID if a Spark job is created successfully. */
		sparkJobId: Option[String] = None,
	  /** Output only. Location where the Spark job is executed. A location is selected by BigQueury for jobs configured to run in a multi-region. */
		sparkJobLocation: Option[String] = None
	)
	
	case class StagePerformanceChangeInsight(
	  /** Output only. Input data change insight of the query stage. */
		inputDataChange: Option[Schema.InputDataChange] = None,
	  /** Output only. The stage id that the insight mapped to. */
		stageId: Option[String] = None
	)
	
	case class StagePerformanceStandaloneInsight(
	  /** Output only. If present, the stage had the following reasons for being disqualified from BI Engine execution. */
		biEngineReasons: Option[List[Schema.BiEngineReason]] = None,
	  /** Output only. High cardinality joins in the stage. */
		highCardinalityJoins: Option[List[Schema.HighCardinalityJoin]] = None,
	  /** Output only. True if the stage has insufficient shuffle quota. */
		insufficientShuffleQuota: Option[Boolean] = None,
	  /** Output only. Partition skew in the stage. */
		partitionSkew: Option[Schema.PartitionSkew] = None,
	  /** Output only. True if the stage has a slot contention issue. */
		slotContention: Option[Boolean] = None,
	  /** Output only. The stage id that the insight mapped to. */
		stageId: Option[String] = None
	)
	
	object StandardSqlDataType {
		enum TypeKindEnum extends Enum[TypeKindEnum] { case TYPE_KIND_UNSPECIFIED, INT64, BOOL, FLOAT64, STRING, BYTES, TIMESTAMP, DATE, TIME, DATETIME, INTERVAL, GEOGRAPHY, NUMERIC, BIGNUMERIC, JSON, ARRAY, STRUCT, RANGE }
	}
	case class StandardSqlDataType(
	  /** The type of the array's elements, if type_kind = "ARRAY". */
		arrayElementType: Option[Schema.StandardSqlDataType] = None,
	  /** The type of the range's elements, if type_kind = "RANGE". */
		rangeElementType: Option[Schema.StandardSqlDataType] = None,
	  /** The fields of this struct, in order, if type_kind = "STRUCT". */
		structType: Option[Schema.StandardSqlStructType] = None,
	  /** Required. The top level type of this field. Can be any GoogleSQL data type (e.g., "INT64", "DATE", "ARRAY"). */
		typeKind: Option[Schema.StandardSqlDataType.TypeKindEnum] = None
	)
	
	case class StandardSqlField(
	  /** Optional. The name of this field. Can be absent for struct fields. */
		name: Option[String] = None,
	  /** Optional. The type of this parameter. Absent if not explicitly specified (e.g., CREATE FUNCTION statement can omit the return type; in this case the output parameter does not have this "type" field). */
		`type`: Option[Schema.StandardSqlDataType] = None
	)
	
	case class StandardSqlStructType(
	  /** Fields within the struct. */
		fields: Option[List[Schema.StandardSqlField]] = None
	)
	
	case class StandardSqlTableType(
	  /** The columns in this table type */
		columns: Option[List[Schema.StandardSqlField]] = None
	)
	
	case class StorageDescriptor(
	  /** Optional. Specifies the fully qualified class name of the InputFormat (e.g. "org.apache.hadoop.hive.ql.io.orc.OrcInputFormat"). The maximum length is 128 characters. */
		inputFormat: Option[String] = None,
	  /** Optional. The physical location of the table (e.g. `gs://spark-dataproc-data/pangea-data/case_sensitive/` or `gs://spark-dataproc-data/pangea-data/&#42;`). The maximum length is 2056 bytes. */
		locationUri: Option[String] = None,
	  /** Optional. Specifies the fully qualified class name of the OutputFormat (e.g. "org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat"). The maximum length is 128 characters. */
		outputFormat: Option[String] = None,
	  /** Optional. Serializer and deserializer information. */
		serdeInfo: Option[Schema.SerDeInfo] = None
	)
	
	case class Streamingbuffer(
	  /** Output only. A lower-bound estimate of the number of bytes currently in the streaming buffer. */
		estimatedBytes: Option[String] = None,
	  /** Output only. A lower-bound estimate of the number of rows currently in the streaming buffer. */
		estimatedRows: Option[String] = None,
	  /** Output only. Contains the timestamp of the oldest entry in the streaming buffer, in milliseconds since the epoch, if the streaming buffer is available. */
		oldestEntryTime: Option[String] = None
	)
	
	case class StringHparamSearchSpace(
	  /** Canididates for the string or enum parameter in lower case. */
		candidates: Option[List[String]] = None
	)
	
	case class SystemVariables(
	  /** Output only. Data type for each system variable. */
		types: Option[Map[String, Schema.StandardSqlDataType]] = None,
	  /** Output only. Value for each system variable. */
		values: Option[Map[String, JsValue]] = None
	)
	
	object Table {
		enum DefaultRoundingModeEnum extends Enum[DefaultRoundingModeEnum] { case ROUNDING_MODE_UNSPECIFIED, ROUND_HALF_AWAY_FROM_ZERO, ROUND_HALF_EVEN }
	}
	case class Table(
	  /** Optional. Specifies the configuration of a BigLake managed table. */
		biglakeConfiguration: Option[Schema.BigLakeConfiguration] = None,
	  /** Output only. Contains information about the clone. This value is set via the clone operation. */
		cloneDefinition: Option[Schema.CloneDefinition] = None,
	  /** Clustering specification for the table. Must be specified with time-based partitioning, data in the table will be first partitioned and subsequently clustered. */
		clustering: Option[Schema.Clustering] = None,
	  /** Output only. The time when this table was created, in milliseconds since the epoch. */
		creationTime: Option[String] = None,
	  /** Optional. Defines the default collation specification of new STRING fields in the table. During table creation or update, if a STRING field is added to this table without explicit collation specified, then the table inherits the table default collation. A change to this field affects only fields added afterwards, and does not alter the existing fields. The following values are supported: &#42; 'und:ci': undetermined locale, case insensitive. &#42; '': empty string. Default to case-sensitive behavior. */
		defaultCollation: Option[String] = None,
	  /** Optional. Defines the default rounding mode specification of new decimal fields (NUMERIC OR BIGNUMERIC) in the table. During table creation or update, if a decimal field is added to this table without an explicit rounding mode specified, then the field inherits the table default rounding mode. Changing this field doesn't affect existing fields. */
		defaultRoundingMode: Option[Schema.Table.DefaultRoundingModeEnum] = None,
	  /** Optional. A user-friendly description of this table. */
		description: Option[String] = None,
	  /** Custom encryption configuration (e.g., Cloud KMS keys). */
		encryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** Output only. A hash of this resource. */
		etag: Option[String] = None,
	  /** Optional. The time when this table expires, in milliseconds since the epoch. If not present, the table will persist indefinitely. Expired tables will be deleted and their storage reclaimed. The defaultTableExpirationMs property of the encapsulating dataset can be used to set a default expirationTime on newly created tables. */
		expirationTime: Option[String] = None,
	  /** Optional. Options defining open source compatible table. */
		externalCatalogTableOptions: Option[Schema.ExternalCatalogTableOptions] = None,
	  /** Optional. Describes the data format, location, and other properties of a table stored outside of BigQuery. By defining these properties, the data source can then be queried as if it were a standard BigQuery table. */
		externalDataConfiguration: Option[Schema.ExternalDataConfiguration] = None,
	  /** Optional. A descriptive name for this table. */
		friendlyName: Option[String] = None,
	  /** Output only. An opaque ID uniquely identifying the table. */
		id: Option[String] = None,
	  /** The type of resource ID. */
		kind: Option[String] = Some("""bigquery#table"""),
	  /** The labels associated with this table. You can use these to organize and group your tables. Label keys and values can be no longer than 63 characters, can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter and each label in the list must have a different key. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when this table was last modified, in milliseconds since the epoch. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. The geographic location where the table resides. This value is inherited from the dataset. */
		location: Option[String] = None,
	  /** Optional. The materialized view definition. */
		materializedView: Option[Schema.MaterializedViewDefinition] = None,
	  /** Output only. The materialized view status. */
		materializedViewStatus: Option[Schema.MaterializedViewStatus] = None,
	  /** Optional. The maximum staleness of data that could be returned when the table (or stale MV) is queried. Staleness encoded as a string encoding of sql IntervalValue type. */
		maxStaleness: Option[String] = None,
	  /** Deprecated. */
		model: Option[Schema.ModelDefinition] = None,
	  /** Output only. Number of logical bytes that are less than 90 days old. */
		numActiveLogicalBytes: Option[String] = None,
	  /** Output only. Number of physical bytes less than 90 days old. This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numActivePhysicalBytes: Option[String] = None,
	  /** Output only. The size of this table in logical bytes, excluding any data in the streaming buffer. */
		numBytes: Option[String] = None,
	  /** Output only. Number of physical bytes used by current live data storage. This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numCurrentPhysicalBytes: Option[String] = None,
	  /** Output only. The number of logical bytes in the table that are considered "long-term storage". */
		numLongTermBytes: Option[String] = None,
	  /** Output only. Number of logical bytes that are more than 90 days old. */
		numLongTermLogicalBytes: Option[String] = None,
	  /** Output only. Number of physical bytes more than 90 days old. This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numLongTermPhysicalBytes: Option[String] = None,
	  /** Output only. The number of partitions present in the table or materialized view. This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numPartitions: Option[String] = None,
	  /** Output only. The physical size of this table in bytes. This includes storage used for time travel. */
		numPhysicalBytes: Option[String] = None,
	  /** Output only. The number of rows of data in this table, excluding any data in the streaming buffer. */
		numRows: Option[String] = None,
	  /** Output only. Number of physical bytes used by time travel storage (deleted or changed data). This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numTimeTravelPhysicalBytes: Option[String] = None,
	  /** Output only. Total number of logical bytes in the table or materialized view. */
		numTotalLogicalBytes: Option[String] = None,
	  /** Output only. The physical size of this table in bytes. This also includes storage used for time travel. This data is not kept in real time, and might be delayed by a few seconds to a few minutes. */
		numTotalPhysicalBytes: Option[String] = None,
	  /** Optional. The partition information for all table formats, including managed partitioned tables, hive partitioned tables, iceberg partitioned, and metastore partitioned tables. This field is only populated for metastore partitioned tables. For other table formats, this is an output only field. */
		partitionDefinition: Option[Schema.PartitioningDefinition] = None,
	  /** If specified, configures range partitioning for this table. */
		rangePartitioning: Option[Schema.RangePartitioning] = None,
	  /** Optional. Output only. Table references of all replicas currently active on the table. */
		replicas: Option[List[Schema.TableReference]] = None,
	  /** Optional. If set to true, queries over this table require a partition filter that can be used for partition elimination to be specified. */
		requirePartitionFilter: Option[Boolean] = Some(false),
	  /** [Optional] The tags associated with this table. Tag keys are globally unique. See additional information on [tags](https://cloud.google.com/iam/docs/tags-access-control#definitions). An object containing a list of "key": value pairs. The key is the namespaced friendly name of the tag key, e.g. "12345/environment" where 12345 is parent id. The value is the friendly short name of the tag value, e.g. "production". */
		resourceTags: Option[Map[String, String]] = None,
	  /** Optional. Output only. Restriction config for table. If set, restrict certain accesses on the table based on the config. See [Data egress](https://cloud.google.com/bigquery/docs/analytics-hub-introduction#data_egress) for more details. */
		restrictions: Option[Schema.RestrictionConfig] = None,
	  /** Optional. Describes the schema of this table. */
		schema: Option[Schema.TableSchema] = None,
	  /** Output only. A URL that can be used to access this resource again. */
		selfLink: Option[String] = None,
	  /** Output only. Contains information about the snapshot. This value is set via snapshot creation. */
		snapshotDefinition: Option[Schema.SnapshotDefinition] = None,
	  /** Output only. Contains information regarding this table's streaming buffer, if one is present. This field will be absent if the table is not being streamed to or if there is no data in the streaming buffer. */
		streamingBuffer: Option[Schema.Streamingbuffer] = None,
	  /** Optional. Tables Primary Key and Foreign Key information */
		tableConstraints: Option[Schema.TableConstraints] = None,
	  /** Required. Reference describing the ID of this table. */
		tableReference: Option[Schema.TableReference] = None,
	  /** Optional. Table replication info for table created `AS REPLICA` DDL like: `CREATE MATERIALIZED VIEW mv1 AS REPLICA OF src_mv` */
		tableReplicationInfo: Option[Schema.TableReplicationInfo] = None,
	  /** If specified, configures time-based partitioning for this table. */
		timePartitioning: Option[Schema.TimePartitioning] = None,
	  /** Output only. Describes the table type. The following values are supported: &#42; `TABLE`: A normal BigQuery table. &#42; `VIEW`: A virtual table defined by a SQL query. &#42; `EXTERNAL`: A table that references data stored in an external storage system, such as Google Cloud Storage. &#42; `MATERIALIZED_VIEW`: A precomputed view defined by a SQL query. &#42; `SNAPSHOT`: An immutable BigQuery table that preserves the contents of a base table at a particular time. See additional information on [table snapshots](https://cloud.google.com/bigquery/docs/table-snapshots-intro). The default value is `TABLE`. */
		`type`: Option[String] = None,
	  /** Optional. The view definition. */
		view: Option[Schema.ViewDefinition] = None
	)
	
	case class TableCell(
		v: Option[JsValue] = None
	)
	
	object TableConstraints {
		object ForeignKeysItem {
			case class ColumnReferencesItem(
			  /** Required. The column in the primary key that are referenced by the referencing_column. */
				referencedColumn: Option[String] = None,
			  /** Required. The column that composes the foreign key. */
				referencingColumn: Option[String] = None
			)
			
			case class ReferencedTableItem(
				datasetId: Option[String] = None,
				projectId: Option[String] = None,
				tableId: Option[String] = None
			)
		}
		case class ForeignKeysItem(
		  /** Required. The columns that compose the foreign key. */
			columnReferences: Option[List[Schema.TableConstraints.ForeignKeysItem.ColumnReferencesItem]] = None,
		  /** Optional. Set only if the foreign key constraint is named. */
			name: Option[String] = None,
			referencedTable: Option[Schema.TableConstraints.ForeignKeysItem.ReferencedTableItem] = None
		)
		
		case class PrimaryKeyItem(
		  /** Required. The columns that are composed of the primary key constraint. */
			columns: Option[List[String]] = None
		)
	}
	case class TableConstraints(
	  /** Optional. Present only if the table has a foreign key. The foreign key is not enforced. */
		foreignKeys: Option[List[Schema.TableConstraints.ForeignKeysItem]] = None,
	  /** Represents the primary key constraint on a table's columns. */
		primaryKey: Option[Schema.TableConstraints.PrimaryKeyItem] = None
	)
	
	object TableDataInsertAllRequest {
		case class RowsItem(
		  /** Insertion ID for best-effort deduplication. This feature is not recommended, and users seeking stronger insertion semantics are encouraged to use other mechanisms such as the BigQuery Write API. */
			insertId: Option[String] = None,
		  /** Data for a single row. */
			json: Option[Schema.JsonObject] = None
		)
	}
	case class TableDataInsertAllRequest(
	  /** Optional. Accept rows that contain values that do not match the schema. The unknown values are ignored. Default is false, which treats unknown values as errors. */
		ignoreUnknownValues: Option[Boolean] = None,
	  /** Optional. The resource type of the response. The value is not checked at the backend. Historically, it has been set to "bigquery#tableDataInsertAllRequest" but you are not required to set it. */
		kind: Option[String] = Some("""bigquery#tableDataInsertAllRequest"""),
		rows: Option[List[Schema.TableDataInsertAllRequest.RowsItem]] = None,
	  /** Optional. Insert all valid rows of a request, even if invalid rows exist. The default value is false, which causes the entire request to fail if any invalid rows exist. */
		skipInvalidRows: Option[Boolean] = None,
	  /** Optional. If specified, treats the destination table as a base template, and inserts the rows into an instance table named "{destination}{templateSuffix}". BigQuery will manage creation of the instance table, using the schema of the base template table. See https://cloud.google.com/bigquery/streaming-data-into-bigquery#template-tables for considerations when working with templates tables. */
		templateSuffix: Option[String] = None,
	  /** Optional. Unique request trace id. Used for debugging purposes only. It is case-sensitive, limited to up to 36 ASCII characters. A UUID is recommended. */
		traceId: Option[String] = None
	)
	
	object TableDataInsertAllResponse {
		case class InsertErrorsItem(
		  /** Error information for the row indicated by the index property. */
			errors: Option[List[Schema.ErrorProto]] = None,
		  /** The index of the row that error applies to. */
			index: Option[Int] = None
		)
	}
	case class TableDataInsertAllResponse(
	  /** Describes specific errors encountered while processing the request. */
		insertErrors: Option[List[Schema.TableDataInsertAllResponse.InsertErrorsItem]] = None,
	  /** Returns "bigquery#tableDataInsertAllResponse". */
		kind: Option[String] = Some("""bigquery#tableDataInsertAllResponse""")
	)
	
	case class TableDataList(
	  /** A hash of this page of results. */
		etag: Option[String] = None,
	  /** The resource type of the response. */
		kind: Option[String] = Some("""bigquery#tableDataList"""),
	  /** A token used for paging results. Providing this token instead of the startIndex parameter can help you retrieve stable results when an underlying table is changing. */
		pageToken: Option[String] = None,
	  /** Rows of results. */
		rows: Option[List[Schema.TableRow]] = None,
	  /** Total rows of the entire table. In order to show default value 0 we have to present it as string. */
		totalRows: Option[String] = None
	)
	
	object TableFieldSchema {
		case class CategoriesItem(
		  /** Deprecated. */
			names: Option[List[String]] = None
		)
		
		case class PolicyTagsItem(
		  /** A list of policy tag resource names. For example, "projects/1/locations/eu/taxonomies/2/policyTags/3". At most 1 policy tag is currently allowed. */
			names: Option[List[String]] = None
		)
		
		case class RangeElementTypeItem(
		  /** Required. The type of a field element. For more information, see TableFieldSchema.type. */
			`type`: Option[String] = None
		)
		
		enum RoundingModeEnum extends Enum[RoundingModeEnum] { case ROUNDING_MODE_UNSPECIFIED, ROUND_HALF_AWAY_FROM_ZERO, ROUND_HALF_EVEN }
	}
	case class TableFieldSchema(
	  /** Deprecated. */
		categories: Option[Schema.TableFieldSchema.CategoriesItem] = None,
	  /** Optional. Field collation can be set only when the type of field is STRING. The following values are supported: &#42; 'und:ci': undetermined locale, case insensitive. &#42; '': empty string. Default to case-sensitive behavior. */
		collation: Option[String] = None,
	  /** Optional. Data policy options, will replace the data_policies. */
		dataPolicies: Option[List[Schema.DataPolicyOption]] = None,
	  /** Optional. A SQL expression to specify the [default value] (https://cloud.google.com/bigquery/docs/default-values) for this field. */
		defaultValueExpression: Option[String] = None,
	  /** Optional. The field description. The maximum length is 1,024 characters. */
		description: Option[String] = None,
	  /** Optional. Describes the nested schema fields if the type property is set to RECORD. */
		fields: Option[List[Schema.TableFieldSchema]] = None,
	  /** Optional. Definition of the foreign data type. Only valid for top-level schema fields (not nested fields). If the type is FOREIGN, this field is required. */
		foreignTypeDefinition: Option[String] = None,
	  /** Optional. Maximum length of values of this field for STRINGS or BYTES. If max_length is not specified, no maximum length constraint is imposed on this field. If type = "STRING", then max_length represents the maximum UTF-8 length of strings in this field. If type = "BYTES", then max_length represents the maximum number of bytes in this field. It is invalid to set this field if type ≠ "STRING" and ≠ "BYTES". */
		maxLength: Option[String] = None,
	  /** Optional. The field mode. Possible values include NULLABLE, REQUIRED and REPEATED. The default value is NULLABLE. */
		mode: Option[String] = None,
	  /** Required. The field name. The name must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_), and must start with a letter or underscore. The maximum length is 300 characters. */
		name: Option[String] = None,
	  /** Optional. The policy tags attached to this field, used for field-level access control. If not set, defaults to empty policy_tags. */
		policyTags: Option[Schema.TableFieldSchema.PolicyTagsItem] = None,
	  /** Optional. Precision (maximum number of total digits in base 10) and scale (maximum number of digits in the fractional part in base 10) constraints for values of this field for NUMERIC or BIGNUMERIC. It is invalid to set precision or scale if type ≠ "NUMERIC" and ≠ "BIGNUMERIC". If precision and scale are not specified, no value range constraint is imposed on this field insofar as values are permitted by the type. Values of this NUMERIC or BIGNUMERIC field must be in this range when: &#42; Precision (P) and scale (S) are specified: [-10P-S + 10-S, 10P-S - 10-S] &#42; Precision (P) is specified but not scale (and thus scale is interpreted to be equal to zero): [-10P + 1, 10P - 1]. Acceptable values for precision and scale if both are specified: &#42; If type = "NUMERIC": 1 ≤ precision - scale ≤ 29 and 0 ≤ scale ≤ 9. &#42; If type = "BIGNUMERIC": 1 ≤ precision - scale ≤ 38 and 0 ≤ scale ≤ 38. Acceptable values for precision if only precision is specified but not scale (and thus scale is interpreted to be equal to zero): &#42; If type = "NUMERIC": 1 ≤ precision ≤ 29. &#42; If type = "BIGNUMERIC": 1 ≤ precision ≤ 38. If scale is specified but not precision, then it is invalid. */
		precision: Option[String] = None,
	  /** Represents the type of a field element. */
		rangeElementType: Option[Schema.TableFieldSchema.RangeElementTypeItem] = None,
	  /** Optional. Specifies the rounding mode to be used when storing values of NUMERIC and BIGNUMERIC type. */
		roundingMode: Option[Schema.TableFieldSchema.RoundingModeEnum] = None,
	  /** Optional. See documentation for precision. */
		scale: Option[String] = None,
	  /** Required. The field data type. Possible values include: &#42; STRING &#42; BYTES &#42; INTEGER (or INT64) &#42; FLOAT (or FLOAT64) &#42; BOOLEAN (or BOOL) &#42; TIMESTAMP &#42; DATE &#42; TIME &#42; DATETIME &#42; GEOGRAPHY &#42; NUMERIC &#42; BIGNUMERIC &#42; JSON &#42; RECORD (or STRUCT) &#42; RANGE Use of RECORD/STRUCT indicates that the field contains a nested schema. */
		`type`: Option[String] = None
	)
	
	object TableList {
		object TablesItem {
			case class ViewItem(
			  /** Specifices the privacy policy for the view. */
				privacyPolicy: Option[Schema.PrivacyPolicy] = None,
			  /** True if view is defined in legacy SQL dialect, false if in GoogleSQL. */
				useLegacySql: Option[Boolean] = None
			)
		}
		case class TablesItem(
		  /** Clustering specification for this table, if configured. */
			clustering: Option[Schema.Clustering] = None,
		  /** Output only. The time when this table was created, in milliseconds since the epoch. */
			creationTime: Option[String] = None,
		  /** The time when this table expires, in milliseconds since the epoch. If not present, the table will persist indefinitely. Expired tables will be deleted and their storage reclaimed. */
			expirationTime: Option[String] = None,
		  /** The user-friendly name for this table. */
			friendlyName: Option[String] = None,
		  /** An opaque ID of the table. */
			id: Option[String] = None,
		  /** The resource type. */
			kind: Option[String] = None,
		  /** The labels associated with this table. You can use these to organize and group your tables. */
			labels: Option[Map[String, String]] = None,
		  /** The range partitioning for this table. */
			rangePartitioning: Option[Schema.RangePartitioning] = None,
		  /** Optional. If set to true, queries including this table must specify a partition filter. This filter is used for partition elimination. */
			requirePartitionFilter: Option[Boolean] = Some(false),
		  /** A reference uniquely identifying table. */
			tableReference: Option[Schema.TableReference] = None,
		  /** The time-based partitioning for this table. */
			timePartitioning: Option[Schema.TimePartitioning] = None,
		  /** The type of table. */
			`type`: Option[String] = None,
		  /** Information about a logical view. */
			view: Option[Schema.TableList.TablesItem.ViewItem] = None
		)
	}
	case class TableList(
	  /** A hash of this page of results. */
		etag: Option[String] = None,
	  /** The type of list. */
		kind: Option[String] = Some("""bigquery#tableList"""),
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Tables in the requested dataset. */
		tables: Option[List[Schema.TableList.TablesItem]] = None,
	  /** The total number of tables in the dataset. */
		totalItems: Option[Int] = None
	)
	
	object TableMetadataCacheUsage {
		enum UnusedReasonEnum extends Enum[UnusedReasonEnum] { case UNUSED_REASON_UNSPECIFIED, EXCEEDED_MAX_STALENESS, METADATA_CACHING_NOT_ENABLED, OTHER_REASON }
	}
	case class TableMetadataCacheUsage(
	  /** Free form human-readable reason metadata caching was unused for the job. */
		explanation: Option[String] = None,
	  /** Duration since last refresh as of this job for managed tables (indicates metadata cache staleness as seen by this job). */
		staleness: Option[String] = None,
	  /** Metadata caching eligible table referenced in the query. */
		tableReference: Option[Schema.TableReference] = None,
	  /** [Table type](https://cloud.google.com/bigquery/docs/reference/rest/v2/tables#Table.FIELDS.type). */
		tableType: Option[String] = None,
	  /** Reason for not using metadata caching for the table. */
		unusedReason: Option[Schema.TableMetadataCacheUsage.UnusedReasonEnum] = None
	)
	
	case class TableReference(
	  /** Required. The ID of the dataset containing this table. */
		datasetId: Option[String] = None,
	  /** Required. The ID of the project containing this table. */
		projectId: Option[String] = None,
	  /** Required. The ID of the table. The ID can contain Unicode characters in category L (letter), M (mark), N (number), Pc (connector, including underscore), Pd (dash), and Zs (space). For more information, see [General Category](https://wikipedia.org/wiki/Unicode_character_property#General_Category). The maximum length is 1,024 characters. Certain operations allow suffixing of the table ID with a partition decorator, such as `sample_table$20190123`. */
		tableId: Option[String] = None
	)
	
	object TableReplicationInfo {
		enum ReplicationStatusEnum extends Enum[ReplicationStatusEnum] { case REPLICATION_STATUS_UNSPECIFIED, ACTIVE, SOURCE_DELETED, PERMISSION_DENIED, UNSUPPORTED_CONFIGURATION }
	}
	case class TableReplicationInfo(
	  /** Optional. Output only. If source is a materialized view, this field signifies the last refresh time of the source. */
		replicatedSourceLastRefreshTime: Option[String] = None,
	  /** Optional. Output only. Replication error that will permanently stopped table replication. */
		replicationError: Option[Schema.ErrorProto] = None,
	  /** Optional. Specifies the interval at which the source table is polled for updates. It's Optional. If not specified, default replication interval would be applied. */
		replicationIntervalMs: Option[String] = None,
	  /** Optional. Output only. Replication status of configured replication. */
		replicationStatus: Option[Schema.TableReplicationInfo.ReplicationStatusEnum] = None,
	  /** Required. Source table reference that is replicated. */
		sourceTable: Option[Schema.TableReference] = None
	)
	
	case class TableRow(
	  /** Represents a single row in the result set, consisting of one or more fields. */
		f: Option[List[Schema.TableCell]] = None
	)
	
	case class TableSchema(
	  /** Describes the fields in a table. */
		fields: Option[List[Schema.TableFieldSchema]] = None,
	  /** Optional. Specifies metadata of the foreign data type definition in field schema (TableFieldSchema.foreign_type_definition). */
		foreignTypeInfo: Option[Schema.ForeignTypeInfo] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class TimePartitioning(
	  /** Optional. Number of milliseconds for which to keep the storage for a partition. A wrapper is used here because 0 is an invalid value. */
		expirationMs: Option[String] = None,
	  /** Optional. If not set, the table is partitioned by pseudo column '_PARTITIONTIME'; if set, the table is partitioned by this field. The field must be a top-level TIMESTAMP or DATE field. Its mode must be NULLABLE or REQUIRED. A wrapper is used here because an empty string is an invalid value. */
		field: Option[String] = None,
	  /** If set to true, queries over this table require a partition filter that can be used for partition elimination to be specified. This field is deprecated; please set the field with the same name on the table itself instead. This field needs a wrapper because we want to output the default value, false, if the user explicitly set it. */
		requirePartitionFilter: Option[Boolean] = Some(false),
	  /** Required. The supported types are DAY, HOUR, MONTH, and YEAR, which will generate one partition per day, hour, month, and year, respectively. */
		`type`: Option[String] = None
	)
	
	object TrainingOptions {
		enum BoosterTypeEnum extends Enum[BoosterTypeEnum] { case BOOSTER_TYPE_UNSPECIFIED, GBTREE, DART }
		enum CategoryEncodingMethodEnum extends Enum[CategoryEncodingMethodEnum] { case ENCODING_METHOD_UNSPECIFIED, ONE_HOT_ENCODING, LABEL_ENCODING, DUMMY_ENCODING }
		enum ColorSpaceEnum extends Enum[ColorSpaceEnum] { case COLOR_SPACE_UNSPECIFIED, RGB, HSV, YIQ, YUV, GRAYSCALE }
		enum DartNormalizeTypeEnum extends Enum[DartNormalizeTypeEnum] { case DART_NORMALIZE_TYPE_UNSPECIFIED, TREE, FOREST }
		enum DataFrequencyEnum extends Enum[DataFrequencyEnum] { case DATA_FREQUENCY_UNSPECIFIED, AUTO_FREQUENCY, YEARLY, QUARTERLY, MONTHLY, WEEKLY, DAILY, HOURLY, PER_MINUTE }
		enum DataSplitMethodEnum extends Enum[DataSplitMethodEnum] { case DATA_SPLIT_METHOD_UNSPECIFIED, RANDOM, CUSTOM, SEQUENTIAL, NO_SPLIT, AUTO_SPLIT }
		enum DistanceTypeEnum extends Enum[DistanceTypeEnum] { case DISTANCE_TYPE_UNSPECIFIED, EUCLIDEAN, COSINE }
		enum FeedbackTypeEnum extends Enum[FeedbackTypeEnum] { case FEEDBACK_TYPE_UNSPECIFIED, IMPLICIT, EXPLICIT }
		enum HolidayRegionEnum extends Enum[HolidayRegionEnum] { case HOLIDAY_REGION_UNSPECIFIED, GLOBAL, NA, JAPAC, EMEA, LAC, AE, AR, AT, AU, BE, BR, CA, CH, CL, CN, CO, CS, CZ, DE, DK, DZ, EC, EE, EG, ES, FI, FR, GB, GR, HK, HU, ID, IE, IL, IN, IR, IT, JP, KR, LV, MA, MX, MY, NG, NL, NO, NZ, PE, PH, PK, PL, PT, RO, RS, RU, SA, SE, SG, SI, SK, TH, TR, TW, UA, US, VE, VN, ZA }
		enum HolidayRegionsEnum extends Enum[HolidayRegionsEnum] { case HOLIDAY_REGION_UNSPECIFIED, GLOBAL, NA, JAPAC, EMEA, LAC, AE, AR, AT, AU, BE, BR, CA, CH, CL, CN, CO, CS, CZ, DE, DK, DZ, EC, EE, EG, ES, FI, FR, GB, GR, HK, HU, ID, IE, IL, IN, IR, IT, JP, KR, LV, MA, MX, MY, NG, NL, NO, NZ, PE, PH, PK, PL, PT, RO, RS, RU, SA, SE, SG, SI, SK, TH, TR, TW, UA, US, VE, VN, ZA }
		enum HparamTuningObjectivesEnum extends Enum[HparamTuningObjectivesEnum] { case HPARAM_TUNING_OBJECTIVE_UNSPECIFIED, MEAN_ABSOLUTE_ERROR, MEAN_SQUARED_ERROR, MEAN_SQUARED_LOG_ERROR, MEDIAN_ABSOLUTE_ERROR, R_SQUARED, EXPLAINED_VARIANCE, PRECISION, RECALL, ACCURACY, F1_SCORE, LOG_LOSS, ROC_AUC, DAVIES_BOULDIN_INDEX, MEAN_AVERAGE_PRECISION, NORMALIZED_DISCOUNTED_CUMULATIVE_GAIN, AVERAGE_RANK }
		enum KmeansInitializationMethodEnum extends Enum[KmeansInitializationMethodEnum] { case KMEANS_INITIALIZATION_METHOD_UNSPECIFIED, RANDOM, CUSTOM, KMEANS_PLUS_PLUS }
		enum LearnRateStrategyEnum extends Enum[LearnRateStrategyEnum] { case LEARN_RATE_STRATEGY_UNSPECIFIED, LINE_SEARCH, CONSTANT }
		enum LossTypeEnum extends Enum[LossTypeEnum] { case LOSS_TYPE_UNSPECIFIED, MEAN_SQUARED_LOSS, MEAN_LOG_LOSS }
		enum ModelRegistryEnum extends Enum[ModelRegistryEnum] { case MODEL_REGISTRY_UNSPECIFIED, VERTEX_AI }
		enum OptimizationStrategyEnum extends Enum[OptimizationStrategyEnum] { case OPTIMIZATION_STRATEGY_UNSPECIFIED, BATCH_GRADIENT_DESCENT, NORMAL_EQUATION }
		enum PcaSolverEnum extends Enum[PcaSolverEnum] { case UNSPECIFIED, FULL, RANDOMIZED, AUTO }
		enum TreeMethodEnum extends Enum[TreeMethodEnum] { case TREE_METHOD_UNSPECIFIED, AUTO, EXACT, APPROX, HIST }
	}
	case class TrainingOptions(
	  /** Activation function of the neural nets. */
		activationFn: Option[String] = None,
	  /** If true, detect step changes and make data adjustment in the input time series. */
		adjustStepChanges: Option[Boolean] = None,
	  /** Whether to use approximate feature contribution method in XGBoost model explanation for global explain. */
		approxGlobalFeatureContrib: Option[Boolean] = None,
	  /** Whether to enable auto ARIMA or not. */
		autoArima: Option[Boolean] = None,
	  /** The max value of the sum of non-seasonal p and q. */
		autoArimaMaxOrder: Option[String] = None,
	  /** The min value of the sum of non-seasonal p and q. */
		autoArimaMinOrder: Option[String] = None,
	  /** Whether to calculate class weights automatically based on the popularity of each label. */
		autoClassWeights: Option[Boolean] = None,
	  /** Batch size for dnn models. */
		batchSize: Option[String] = None,
	  /** Booster type for boosted tree models. */
		boosterType: Option[Schema.TrainingOptions.BoosterTypeEnum] = None,
	  /** Budget in hours for AutoML training. */
		budgetHours: Option[BigDecimal] = None,
	  /** Whether or not p-value test should be computed for this model. Only available for linear and logistic regression models. */
		calculatePValues: Option[Boolean] = None,
	  /** Categorical feature encoding method. */
		categoryEncodingMethod: Option[Schema.TrainingOptions.CategoryEncodingMethodEnum] = None,
	  /** If true, clean spikes and dips in the input time series. */
		cleanSpikesAndDips: Option[Boolean] = None,
	  /** Enums for color space, used for processing images in Object Table. See more details at https://www.tensorflow.org/io/tutorials/colorspace. */
		colorSpace: Option[Schema.TrainingOptions.ColorSpaceEnum] = None,
	  /** Subsample ratio of columns for each level for boosted tree models. */
		colsampleBylevel: Option[BigDecimal] = None,
	  /** Subsample ratio of columns for each node(split) for boosted tree models. */
		colsampleBynode: Option[BigDecimal] = None,
	  /** Subsample ratio of columns when constructing each tree for boosted tree models. */
		colsampleBytree: Option[BigDecimal] = None,
	  /** The contribution metric. Applies to contribution analysis models. Allowed formats supported are for summable and summable ratio contribution metrics. These include expressions such as "SUM(x)" or "SUM(x)/SUM(y)", where x and y are column names from the base table. */
		contributionMetric: Option[String] = None,
	  /** Type of normalization algorithm for boosted tree models using dart booster. */
		dartNormalizeType: Option[Schema.TrainingOptions.DartNormalizeTypeEnum] = None,
	  /** The data frequency of a time series. */
		dataFrequency: Option[Schema.TrainingOptions.DataFrequencyEnum] = None,
	  /** The column to split data with. This column won't be used as a feature. 1. When data_split_method is CUSTOM, the corresponding column should be boolean. The rows with true value tag are eval data, and the false are training data. 2. When data_split_method is SEQ, the first DATA_SPLIT_EVAL_FRACTION rows (from smallest to largest) in the corresponding column are used as training data, and the rest are eval data. It respects the order in Orderable data types: https://cloud.google.com/bigquery/docs/reference/standard-sql/data-types#data-type-properties */
		dataSplitColumn: Option[String] = None,
	  /** The fraction of evaluation data over the whole input data. The rest of data will be used as training data. The format should be double. Accurate to two decimal places. Default value is 0.2. */
		dataSplitEvalFraction: Option[BigDecimal] = None,
	  /** The data split type for training and evaluation, e.g. RANDOM. */
		dataSplitMethod: Option[Schema.TrainingOptions.DataSplitMethodEnum] = None,
	  /** If true, perform decompose time series and save the results. */
		decomposeTimeSeries: Option[Boolean] = None,
	  /** Optional. Names of the columns to slice on. Applies to contribution analysis models. */
		dimensionIdColumns: Option[List[String]] = None,
	  /** Distance type for clustering models. */
		distanceType: Option[Schema.TrainingOptions.DistanceTypeEnum] = None,
	  /** Dropout probability for dnn models. */
		dropout: Option[BigDecimal] = None,
	  /** Whether to stop early when the loss doesn't improve significantly any more (compared to min_relative_progress). Used only for iterative training algorithms. */
		earlyStop: Option[Boolean] = None,
	  /** If true, enable global explanation during training. */
		enableGlobalExplain: Option[Boolean] = None,
	  /** Feedback type that specifies which algorithm to run for matrix factorization. */
		feedbackType: Option[Schema.TrainingOptions.FeedbackTypeEnum] = None,
	  /** Whether the model should include intercept during model training. */
		fitIntercept: Option[Boolean] = None,
	  /** Hidden units for dnn models. */
		hiddenUnits: Option[List[String]] = None,
	  /** The geographical region based on which the holidays are considered in time series modeling. If a valid value is specified, then holiday effects modeling is enabled. */
		holidayRegion: Option[Schema.TrainingOptions.HolidayRegionEnum] = None,
	  /** A list of geographical regions that are used for time series modeling. */
		holidayRegions: Option[List[Schema.TrainingOptions.HolidayRegionsEnum]] = None,
	  /** The number of periods ahead that need to be forecasted. */
		horizon: Option[String] = None,
	  /** The target evaluation metrics to optimize the hyperparameters for. */
		hparamTuningObjectives: Option[List[Schema.TrainingOptions.HparamTuningObjectivesEnum]] = None,
	  /** Include drift when fitting an ARIMA model. */
		includeDrift: Option[Boolean] = None,
	  /** Specifies the initial learning rate for the line search learn rate strategy. */
		initialLearnRate: Option[BigDecimal] = None,
	  /** Name of input label columns in training data. */
		inputLabelColumns: Option[List[String]] = None,
	  /** Name of the instance weight column for training data. This column isn't be used as a feature. */
		instanceWeightColumn: Option[String] = None,
	  /** Number of integral steps for the integrated gradients explain method. */
		integratedGradientsNumSteps: Option[String] = None,
	  /** Name of the column used to determine the rows corresponding to control and test. Applies to contribution analysis models. */
		isTestColumn: Option[String] = None,
	  /** Item column specified for matrix factorization models. */
		itemColumn: Option[String] = None,
	  /** The column used to provide the initial centroids for kmeans algorithm when kmeans_initialization_method is CUSTOM. */
		kmeansInitializationColumn: Option[String] = None,
	  /** The method used to initialize the centroids for kmeans algorithm. */
		kmeansInitializationMethod: Option[Schema.TrainingOptions.KmeansInitializationMethodEnum] = None,
	  /** L1 regularization coefficient to activations. */
		l1RegActivation: Option[BigDecimal] = None,
	  /** L1 regularization coefficient. */
		l1Regularization: Option[BigDecimal] = None,
	  /** L2 regularization coefficient. */
		l2Regularization: Option[BigDecimal] = None,
	  /** Weights associated with each label class, for rebalancing the training data. Only applicable for classification models. */
		labelClassWeights: Option[Map[String, BigDecimal]] = None,
	  /** Learning rate in training. Used only for iterative training algorithms. */
		learnRate: Option[BigDecimal] = None,
	  /** The strategy to determine learn rate for the current iteration. */
		learnRateStrategy: Option[Schema.TrainingOptions.LearnRateStrategyEnum] = None,
	  /** Type of loss function used during training run. */
		lossType: Option[Schema.TrainingOptions.LossTypeEnum] = None,
	  /** The maximum number of iterations in training. Used only for iterative training algorithms. */
		maxIterations: Option[String] = None,
	  /** Maximum number of trials to run in parallel. */
		maxParallelTrials: Option[String] = None,
	  /** The maximum number of time points in a time series that can be used in modeling the trend component of the time series. Don't use this option with the `timeSeriesLengthFraction` or `minTimeSeriesLength` options. */
		maxTimeSeriesLength: Option[String] = None,
	  /** Maximum depth of a tree for boosted tree models. */
		maxTreeDepth: Option[String] = None,
	  /** The apriori support minimum. Applies to contribution analysis models. */
		minAprioriSupport: Option[BigDecimal] = None,
	  /** When early_stop is true, stops training when accuracy improvement is less than 'min_relative_progress'. Used only for iterative training algorithms. */
		minRelativeProgress: Option[BigDecimal] = None,
	  /** Minimum split loss for boosted tree models. */
		minSplitLoss: Option[BigDecimal] = None,
	  /** The minimum number of time points in a time series that are used in modeling the trend component of the time series. If you use this option you must also set the `timeSeriesLengthFraction` option. This training option ensures that enough time points are available when you use `timeSeriesLengthFraction` in trend modeling. This is particularly important when forecasting multiple time series in a single query using `timeSeriesIdColumn`. If the total number of time points is less than the `minTimeSeriesLength` value, then the query uses all available time points. */
		minTimeSeriesLength: Option[String] = None,
	  /** Minimum sum of instance weight needed in a child for boosted tree models. */
		minTreeChildWeight: Option[String] = None,
	  /** The model registry. */
		modelRegistry: Option[Schema.TrainingOptions.ModelRegistryEnum] = None,
	  /** Google Cloud Storage URI from which the model was imported. Only applicable for imported models. */
		modelUri: Option[String] = None,
	  /** A specification of the non-seasonal part of the ARIMA model: the three components (p, d, q) are the AR order, the degree of differencing, and the MA order. */
		nonSeasonalOrder: Option[Schema.ArimaOrder] = None,
	  /** Number of clusters for clustering models. */
		numClusters: Option[String] = None,
	  /** Num factors specified for matrix factorization models. */
		numFactors: Option[String] = None,
	  /** Number of parallel trees constructed during each iteration for boosted tree models. */
		numParallelTree: Option[String] = None,
	  /** Number of principal components to keep in the PCA model. Must be <= the number of features. */
		numPrincipalComponents: Option[String] = None,
	  /** Number of trials to run this hyperparameter tuning job. */
		numTrials: Option[String] = None,
	  /** Optimization strategy for training linear regression models. */
		optimizationStrategy: Option[Schema.TrainingOptions.OptimizationStrategyEnum] = None,
	  /** Optimizer used for training the neural nets. */
		optimizer: Option[String] = None,
	  /** The minimum ratio of cumulative explained variance that needs to be given by the PCA model. */
		pcaExplainedVarianceRatio: Option[BigDecimal] = None,
	  /** The solver for PCA. */
		pcaSolver: Option[Schema.TrainingOptions.PcaSolverEnum] = None,
	  /** Number of paths for the sampled Shapley explain method. */
		sampledShapleyNumPaths: Option[String] = None,
	  /** If true, scale the feature values by dividing the feature standard deviation. Currently only apply to PCA. */
		scaleFeatures: Option[Boolean] = None,
	  /** Whether to standardize numerical features. Default to true. */
		standardizeFeatures: Option[Boolean] = None,
	  /** Subsample fraction of the training data to grow tree to prevent overfitting for boosted tree models. */
		subsample: Option[BigDecimal] = None,
	  /** Based on the selected TF version, the corresponding docker image is used to train external models. */
		tfVersion: Option[String] = None,
	  /** Column to be designated as time series data for ARIMA model. */
		timeSeriesDataColumn: Option[String] = None,
	  /** The time series id column that was used during ARIMA model training. */
		timeSeriesIdColumn: Option[String] = None,
	  /** The time series id columns that were used during ARIMA model training. */
		timeSeriesIdColumns: Option[List[String]] = None,
	  /** The fraction of the interpolated length of the time series that's used to model the time series trend component. All of the time points of the time series are used to model the non-trend component. This training option accelerates modeling training without sacrificing much forecasting accuracy. You can use this option with `minTimeSeriesLength` but not with `maxTimeSeriesLength`. */
		timeSeriesLengthFraction: Option[BigDecimal] = None,
	  /** Column to be designated as time series timestamp for ARIMA model. */
		timeSeriesTimestampColumn: Option[String] = None,
	  /** Tree construction algorithm for boosted tree models. */
		treeMethod: Option[Schema.TrainingOptions.TreeMethodEnum] = None,
	  /** Smoothing window size for the trend component. When a positive value is specified, a center moving average smoothing is applied on the history trend. When the smoothing window is out of the boundary at the beginning or the end of the trend, the first element or the last element is padded to fill the smoothing window before the average is applied. */
		trendSmoothingWindowSize: Option[String] = None,
	  /** User column specified for matrix factorization models. */
		userColumn: Option[String] = None,
	  /** The version aliases to apply in Vertex AI model registry. Always overwrite if the version aliases exists in a existing model. */
		vertexAiModelVersionAliases: Option[List[String]] = None,
	  /** Hyperparameter for matrix factoration when implicit feedback type is specified. */
		walsAlpha: Option[BigDecimal] = None,
	  /** Whether to train a model from the last checkpoint. */
		warmStart: Option[Boolean] = None,
	  /** User-selected XGBoost versions for training of XGBoost models. */
		xgboostVersion: Option[String] = None
	)
	
	case class TrainingRun(
	  /** Output only. Global explanation contains the explanation of top features on the class level. Applies to classification models only. */
		classLevelGlobalExplanations: Option[List[Schema.GlobalExplanation]] = None,
	  /** Output only. Data split result of the training run. Only set when the input data is actually split. */
		dataSplitResult: Option[Schema.DataSplitResult] = None,
	  /** Output only. The evaluation metrics over training/eval data that were computed at the end of training. */
		evaluationMetrics: Option[Schema.EvaluationMetrics] = None,
	  /** Output only. Global explanation contains the explanation of top features on the model level. Applies to both regression and classification models. */
		modelLevelGlobalExplanation: Option[Schema.GlobalExplanation] = None,
	  /** Output only. Output of each iteration run, results.size() <= max_iterations. */
		results: Option[List[Schema.IterationResult]] = None,
	  /** Output only. The start time of this training run. */
		startTime: Option[String] = None,
	  /** Output only. Options that were used for this training run, includes user specified and default options that were used. */
		trainingOptions: Option[Schema.TrainingOptions] = None,
	  /** Output only. The start time of this training run, in milliseconds since epoch. */
		trainingStartTime: Option[String] = None,
	  /** The model id in the [Vertex AI Model Registry](https://cloud.google.com/vertex-ai/docs/model-registry/introduction) for this training run. */
		vertexAiModelId: Option[String] = None,
	  /** Output only. The model version in the [Vertex AI Model Registry](https://cloud.google.com/vertex-ai/docs/model-registry/introduction) for this training run. */
		vertexAiModelVersion: Option[String] = None
	)
	
	case class TransactionInfo(
	  /** Output only. [Alpha] Id of the transaction. */
		transactionId: Option[String] = None
	)
	
	case class TransformColumn(
	  /** Output only. Name of the column. */
		name: Option[String] = None,
	  /** Output only. The SQL expression used in the column transform. */
		transformSql: Option[String] = None,
	  /** Output only. Data type of the column after the transform. */
		`type`: Option[Schema.StandardSqlDataType] = None
	)
	
	case class UndeleteDatasetRequest(
	  /** Optional. The exact time when the dataset was deleted. If not specified, the most recently deleted version is undeleted. Undeleting a dataset using deletion time is not supported. */
		deletionTime: Option[String] = None
	)
	
	case class UserDefinedFunctionResource(
	  /** [Pick one] An inline resource that contains code for a user-defined function (UDF). Providing a inline code resource is equivalent to providing a URI for a file containing the same code. */
		inlineCode: Option[String] = None,
	  /** [Pick one] A code resource to load from a Google Cloud Storage URI (gs://bucket/path). */
		resourceUri: Option[String] = None
	)
	
	object VectorSearchStatistics {
		enum IndexUsageModeEnum extends Enum[IndexUsageModeEnum] { case INDEX_USAGE_MODE_UNSPECIFIED, UNUSED, PARTIALLY_USED, FULLY_USED }
	}
	case class VectorSearchStatistics(
	  /** When `indexUsageMode` is `UNUSED` or `PARTIALLY_USED`, this field explains why indexes were not used in all or part of the vector search query. If `indexUsageMode` is `FULLY_USED`, this field is not populated. */
		indexUnusedReasons: Option[List[Schema.IndexUnusedReason]] = None,
	  /** Specifies the index usage mode for the query. */
		indexUsageMode: Option[Schema.VectorSearchStatistics.IndexUsageModeEnum] = None
	)
	
	case class ViewDefinition(
	  /** Optional. Foreign view representations. */
		foreignDefinitions: Option[List[Schema.ForeignViewDefinition]] = None,
	  /** Optional. Specifices the privacy policy for the view. */
		privacyPolicy: Option[Schema.PrivacyPolicy] = None,
	  /** Required. A query that BigQuery executes when the view is referenced. */
		query: Option[String] = None,
	  /** True if the column names are explicitly specified. For example by using the 'CREATE VIEW v(c1, c2) AS ...' syntax. Can only be set for GoogleSQL views. */
		useExplicitColumnNames: Option[Boolean] = None,
	  /** Specifies whether to use BigQuery's legacy SQL for this view. The default value is true. If set to false, the view will use BigQuery's GoogleSQL: https://cloud.google.com/bigquery/sql-reference/ Queries and views that reference this view must use the same flag value. A wrapper is used here because the default value is True. */
		useLegacySql: Option[Boolean] = None,
	  /** Describes user-defined function resources used in the query. */
		userDefinedFunctionResources: Option[List[Schema.UserDefinedFunctionResource]] = None
	)
}
