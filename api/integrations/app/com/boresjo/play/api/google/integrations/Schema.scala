package com.boresjo.play.api.google.integrations

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class EnterpriseCrmEventbusStats(
	  /** Dimensions that these stats have been aggregated on. */
		dimensions: Option[Schema.EnterpriseCrmEventbusStatsDimensions] = None,
	  /** Average duration in seconds. */
		durationInSeconds: Option[BigDecimal] = None,
	  /** Average error rate. */
		errorRate: Option[BigDecimal] = None,
	  /** Queries per second. */
		qps: Option[BigDecimal] = None,
	  /** Average warning rate. */
		warningRate: Option[BigDecimal] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCoordinate(
	  /** Required. X axis of the coordinate */
		x: Option[Int] = None,
	  /** Required. Y axis of the coordinate */
		y: Option[Int] = None
	)
	
	case class EnterpriseCrmEventbusProtoEventParameters(
	  /** Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameters: Option[List[Schema.EnterpriseCrmEventbusProtoParameterEntry]] = None
	)
	
	case class EnterpriseCrmEventbusProtoEventExecutionSnapshot(
	  /** Points to the event execution info this snapshot belongs to. */
		eventExecutionInfoId: Option[String] = None,
		eventExecutionSnapshotMetadata: Option[Schema.EnterpriseCrmEventbusProtoEventExecutionSnapshotEventExecutionSnapshotMetadata] = None,
	  /** Name of the workflow this event execution snapshot belongs to. */
		workflowName: Option[String] = None,
	  /** All of the computed conditions that been calculated. */
		conditionResults: Option[List[Schema.EnterpriseCrmEventbusProtoConditionResult]] = None,
	  /** Client that the execution snapshot is associated to. */
		clientId: Option[String] = None,
	  /** All of the task execution details at the given point of time. */
		taskExecutionDetails: Option[List[Schema.EnterpriseCrmEventbusProtoTaskExecutionDetails]] = None,
	  /** Auto-generated. Used as primary key for EventExecutionSnapshots table. */
		eventExecutionSnapshotId: Option[String] = None,
	  /** The parameters in Event object. */
		eventParams: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None,
	  /** The parameters in Event object that differs from last snapshot. */
		diffParams: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None,
	  /** Indicates "right after which checkpoint task's execution" this snapshot is taken. */
		checkpointTaskNumber: Option[String] = None,
	  /** indicate whether snapshot exceeded maximum size before clean up */
		exceedMaxSize: Option[Boolean] = None,
	  /** Indicates when this snapshot is taken. */
		snapshotTime: Option[String] = None,
	  /** The task name associated with this snapshot. Could be empty. */
		taskName: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoProtoArrayFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET, APPEND, SIZE, TO_SET, APPEND_ALL, TO_JSON, SET, REMOVE, REMOVE_AT, CONTAINS, FOR_EACH, FILTER }
	}
	case class EnterpriseCrmEventbusProtoProtoArrayFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoProtoArrayFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaServiceAccountCredentials(
	  /** Name of the service account that has the permission to make the request. */
		serviceAccount: Option[String] = None,
	  /** A space-delimited list of requested scope permissions. */
		scope: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoBooleanFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, TO_JSON, NOT, AND, NAND, OR, XOR, NOR, XNOR, TO_STRING, EQUALS }
	}
	case class EnterpriseCrmEventbusProtoBooleanFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoBooleanFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCreateCloudFunctionResponse(
	  /** The trigger url that will be returned */
		triggerUrl: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaLiftSuspensionResponse(
	  /** Execution Id that will be returned */
		eventExecutionInfoId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTestTaskConfig(
	  /** Optional. List of conditions or expressions which should be evaluated to true unless there is a bug/problem in the integration. These are evaluated one the task execution is completed as per the mock strategy in test case */
		assertions: Option[List[Schema.GoogleCloudIntegrationsV1alphaAssertion]] = None,
	  /** Optional. Defines how to mock the given task during test execution */
		mockConfig: Option[Schema.GoogleCloudIntegrationsV1alphaMockConfig] = None,
	  /** Optional. Auto-generated. */
		taskConfig: Option[Schema.GoogleCloudIntegrationsV1alphaTaskConfig] = None,
	  /** Required. This defines in the test case, the task in integration which will be mocked by this test task config */
		taskNumber: Option[String] = None,
	  /** Required. This defines in the test case, the task name in integration which will be mocked by this test task config */
		task: Option[String] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoStringParameterArray(
		stringValues: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaValueType(
	  /** Double Number. */
		doubleValue: Option[BigDecimal] = None,
	  /** String Array. */
		stringArray: Option[Schema.GoogleCloudIntegrationsV1alphaStringParameterArray] = None,
	  /** Json. */
		jsonValue: Option[String] = None,
	  /** Integer Array. */
		intArray: Option[Schema.GoogleCloudIntegrationsV1alphaIntParameterArray] = None,
	  /** String. */
		stringValue: Option[String] = None,
	  /** Boolean Array. */
		booleanArray: Option[Schema.GoogleCloudIntegrationsV1alphaBooleanParameterArray] = None,
	  /** Boolean. */
		booleanValue: Option[Boolean] = None,
	  /** Integer. */
		intValue: Option[String] = None,
	  /** Double Number Array. */
		doubleArray: Option[Schema.GoogleCloudIntegrationsV1alphaDoubleParameterArray] = None
	)
	
	case class EnterpriseCrmEventbusProtoCombinedCondition(
	  /** A set of individual constituent conditions. */
		conditions: Option[List[Schema.EnterpriseCrmEventbusProtoCondition]] = None
	)
	
	case class EnterpriseCrmEventbusProtoExecutionTraceInfo(
	  /** Parent event execution info id that triggers the current execution through SubWorkflowExecutorTask. */
		parentEventExecutionInfoId: Option[String] = None,
	  /** Used to aggregate ExecutionTraceInfo. */
		traceId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse(
	  /** List containing JSON for multiple file with type information. */
		files: Option[List[Schema.GoogleCloudIntegrationsV1alphaFile]] = None
	)
	
	case class EnterpriseCrmEventbusProtoConditionResult(
	  /** the result comes out after evaluate the combined condition. True if there's no combined condition specified. */
		result: Option[Boolean] = None,
	  /** the current task number. */
		currentTaskNumber: Option[String] = None,
	  /** the next task number. */
		nextTaskNumber: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	object EnterpriseCrmEventbusProtoProtoFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET_STRING_SUBFIELD, GET_INT_SUBFIELD, GET_DOUBLE_SUBFIELD, GET_BOOLEAN_SUBFIELD, GET_STRING_ARRAY_SUBFIELD, GET_INT_ARRAY_SUBFIELD, GET_DOUBLE_ARRAY_SUBFIELD, GET_BOOLEAN_ARRAY_SUBFIELD, GET_PROTO_ARRAY_SUBFIELD, GET_PROTO_SUBFIELD, TO_JSON, GET_BYTES_SUBFIELD_AS_UTF_8_STRING, GET_BYTES_SUBFIELD_AS_PROTO, EQUALS }
	}
	case class EnterpriseCrmEventbusProtoProtoFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoProtoFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudConnectorsV1Destination(
	  /** PSC service attachments. Format: projects/&#42;/regions/&#42;/serviceAttachments/&#42; */
		serviceAttachment: Option[String] = None,
	  /** The port is the target port number that is accepted by the destination. */
		port: Option[Int] = None,
	  /** For publicly routable host. */
		host: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaStringParameterArray(
	  /** String array. */
		stringValues: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDeprovisionClientRequest(
	
	)
	
	case class GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata(
	  /** List of actions. */
		actions: Option[List[String]] = None,
	  /** List of entity names. */
		entities: Option[List[String]] = None
	)
	
	case class EnterpriseCrmEventbusProtoValueType(
		stringValue: Option[String] = None,
		intArray: Option[Schema.EnterpriseCrmEventbusProtoIntArray] = None,
		doubleArray: Option[Schema.EnterpriseCrmEventbusProtoDoubleArray] = None,
		doubleValue: Option[BigDecimal] = None,
		stringArray: Option[Schema.EnterpriseCrmEventbusProtoStringArray] = None,
		booleanValue: Option[Boolean] = None,
		intValue: Option[String] = None,
		protoValue: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaCloudLoggingDetails {
		enum CloudLoggingSeverityEnum extends Enum[CloudLoggingSeverityEnum] { case CLOUD_LOGGING_SEVERITY_UNSPECIFIED, INFO, ERROR, WARNING }
	}
	case class GoogleCloudIntegrationsV1alphaCloudLoggingDetails(
	  /** Optional. Status of whether Cloud Logging is enabled or not for the integration version getting executed. */
		enableCloudLogging: Option[Boolean] = None,
	  /** Optional. Severity selected by the customer for the logs to be sent to Cloud Logging, for the integration version getting executed. */
		cloudLoggingSeverity: Option[Schema.GoogleCloudIntegrationsV1alphaCloudLoggingDetails.CloudLoggingSeverityEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaUploadTemplateRequest {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case FILE_FORMAT_UNSPECIFIED, JSON, YAML }
	}
	case class GoogleCloudIntegrationsV1alphaUploadTemplateRequest(
	  /** Required. File format for upload request. */
		fileFormat: Option[Schema.GoogleCloudIntegrationsV1alphaUploadTemplateRequest.FileFormatEnum] = None,
	  /** Required. The textproto of the template. */
		content: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoFunction(
	  /** List of parameters required for the transformation. */
		parameters: Option[List[Schema.EnterpriseCrmEventbusProtoTransformExpression]] = None,
	  /** The name of the function to perform. */
		functionType: Option[Schema.EnterpriseCrmEventbusProtoFunctionType] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaErrorCatcherConfig(
	  /** Required. The set of start tasks that are to be executed for the error catch flow */
		startErrorTasks: Option[List[Schema.GoogleCloudIntegrationsV1alphaNextTask]] = None,
	  /** Required. A number to uniquely identify each error catcher config within the workflow on UI. */
		errorCatcherNumber: Option[String] = None,
	  /** Required. An error catcher id is string representation for the error catcher config. Within a workflow, error_catcher_id uniquely identifies an error catcher config among all error catcher configs for the workflow */
		errorCatcherId: Option[String] = None,
	  /** Optional. Informs the front-end application where to draw this error catcher config on the UI. */
		position: Option[Schema.GoogleCloudIntegrationsV1alphaCoordinate] = None,
	  /** Optional. User-provided description intended to give more business context about the error catcher config. */
		description: Option[String] = None,
	  /** Optional. The user created label for a particular error catcher. Optional. */
		label: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecutionSnapshot(
	  /** Metadata of the execution snapshot. */
		executionSnapshotMetadata: Option[Schema.GoogleCloudIntegrationsV1alphaExecutionSnapshotExecutionSnapshotMetadata] = None,
	  /** Indicates "after which checkpoint task's execution" this snapshot is taken. */
		checkpointTaskNumber: Option[String] = None,
	  /** Parameters used during the execution. */
		params: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** All of the task execution details at the given point of time. */
		taskExecutionDetails: Option[List[Schema.GoogleCloudIntegrationsV1alphaTaskExecutionDetails]] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoParameterMapEntry(
		key: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterMapField] = None,
		value: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterMapField] = None
	)
	
	case class GoogleCloudConnectorsV1EventingRuntimeDataWebhookSubscriptions(
	  /** Output only. Webhook data. */
		webhookData: Option[List[Schema.GoogleCloudConnectorsV1EventingRuntimeDataWebhookData]] = None
	)
	
	object EnterpriseCrmEventbusProtoCloudLoggingDetails {
		enum CloudLoggingSeverityEnum extends Enum[CloudLoggingSeverityEnum] { case CLOUD_LOGGING_SEVERITY_UNSPECIFIED, INFO, ERROR, WARNING }
	}
	case class EnterpriseCrmEventbusProtoCloudLoggingDetails(
	  /** Severity selected by the customer for the logs to be sent to Cloud Logging, for the integration version getting executed. */
		cloudLoggingSeverity: Option[Schema.EnterpriseCrmEventbusProtoCloudLoggingDetails.CloudLoggingSeverityEnum] = None,
	  /** Status of whether Cloud Logging is enabled or not for the integration version getting executed. */
		enableCloudLogging: Option[Boolean] = None
	)
	
	case class EnterpriseCrmEventbusProtoStringArray(
		values: Option[List[String]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaOAuth2ClientCredentials {
		enum RequestTypeEnum extends Enum[RequestTypeEnum] { case REQUEST_TYPE_UNSPECIFIED, REQUEST_BODY, QUERY_PARAMETERS, ENCODED_HEADER }
	}
	case class GoogleCloudIntegrationsV1alphaOAuth2ClientCredentials(
	  /** Token parameters for the auth request. */
		tokenParams: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap] = None,
	  /** The client's ID. */
		clientId: Option[String] = None,
	  /** A space-delimited list of requested scope permissions. */
		scope: Option[String] = None,
	  /** The token endpoint is used by the client to obtain an access token by presenting its authorization grant or refresh token. */
		tokenEndpoint: Option[String] = None,
	  /** Access token fetched from the authorization server. */
		accessToken: Option[Schema.GoogleCloudIntegrationsV1alphaAccessToken] = None,
	  /** Represent how to pass parameters to fetch access token */
		requestType: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2ClientCredentials.RequestTypeEnum] = None,
	  /** The client's secret. */
		clientSecret: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectRequest(
	  /** The id of the Apps Script project to be linked. */
		scriptId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest(
	  /** Optional. Flag to determine how to should propagate errors. If this flag is set to be true, it will not throw an exception. Instead, it will return a {@link ExecuteIntegrationsResponse} with an execution id and error messages as PostWithTriggerIdExecutionException in {@link EventParameters}. The flag is set to be false by default. */
		doNotPropagateError: Option[Boolean] = None,
	  /** Optional. The id of the ON_HOLD execution to be resumed. */
		executionId: Option[String] = None,
	  /** Optional. Input parameters used by integration execution. */
		inputParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Required. Matched against all {@link TriggerConfig}s across all integrations. i.e. TriggerConfig.trigger_id.equals(trigger_id). The trigger_id is in the format of `api_trigger/TRIGGER_NAME`. */
		triggerId: Option[String] = None,
	  /** Optional. Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameterEntries: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** Optional. This is used to de-dup incoming request: if the duplicate request was detected, the response from the previous execution is returned. */
		requestId: Option[String] = None,
	  /** Optional. Passed in as parameters to each integration execution. Redacted */
		parameters: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaOidcToken(
	  /** Audience to be used when generating OIDC token. The audience claim identifies the recipients that the JWT is intended for. */
		audience: Option[String] = None,
	  /** The approximate time until the token retrieved is valid. */
		tokenExpireTime: Option[String] = None,
	  /** ID token obtained for the service account */
		token: Option[String] = None,
	  /** The service account email to be used as the identity for the token. */
		serviceAccountEmail: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoEventBusProperties(
	  /** An unordered list of property entries. */
		properties: Option[List[Schema.EnterpriseCrmEventbusProtoPropertyEntry]] = None
	)
	
	case class EnterpriseCrmEventbusProtoPropertyEntry(
	  /** Key is used to retrieve the corresponding property value. This should be unique for a given fired event. The Tasks should be aware of the keys used while firing the events for them to be able to retrieve the values. */
		key: Option[String] = None,
	  /** Values for the defined keys. Each value can either be string, int, double or any proto message. */
		value: Option[Schema.EnterpriseCrmEventbusProtoValueType] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUseTemplateRequestIntegrationDetails(
	  /** Optional. Description of the sub integration which would be created via templates. */
		integrationDescription: Option[String] = None,
	  /** Required. Name of the sub integration which would be created via templates. */
		integration: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDownloadExecutionResponse(
	  /** The content of downloaded execution. */
		content: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse(
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** Runtime action schemas. */
		runtimeActionSchemas: Option[List[Schema.GoogleCloudIntegrationsV1alphaRuntimeActionSchema]] = None
	)
	
	object EnterpriseCrmEventbusProtoTaskAlertConfig {
		enum MetricTypeEnum extends Enum[MetricTypeEnum] { case METRIC_TYPE_UNSPECIFIED, TASK_ERROR_RATE, TASK_WARNING_RATE, TASK_RATE, TASK_AVERAGE_DURATION, TASK_PERCENTILE_DURATION }
		enum ThresholdTypeEnum extends Enum[ThresholdTypeEnum] { case UNSPECIFIED_THRESHOLD_TYPE, EXPECTED_MIN, EXPECTED_MAX }
	}
	case class EnterpriseCrmEventbusProtoTaskAlertConfig(
		metricType: Option[Schema.EnterpriseCrmEventbusProtoTaskAlertConfig.MetricTypeEnum] = None,
	  /** The metric value, above or below which the alert should be triggered. */
		thresholdValue: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigThresholdValue] = None,
	  /** Set to false by default. When set to true, the metrics are not aggregated or pushed to Monarch for this workflow alert. */
		alertDisabled: Option[Boolean] = None,
	  /** Link to a playbook for resolving the issue that triggered this alert. */
		playbookUrl: Option[String] = None,
	  /** A name to identify this alert. This will be displayed in the alert subject. If set, this name should be unique in within the scope of the containing workflow. */
		alertName: Option[String] = None,
	  /** Should be specified only for TASK_AVERAGE_DURATION and TASK_PERCENTILE_DURATION metrics. This member should be used to specify what duration value the metrics should exceed for the alert to trigger. */
		durationThresholdMs: Option[String] = None,
	  /** The period over which the metric value should be aggregated and evaluated. Format is , where integer should be a positive integer and unit should be one of (s,m,h,d,w) meaning (second, minute, hour, day, week). */
		aggregationPeriod: Option[String] = None,
		warningEnumList: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList] = None,
	  /** For how many contiguous aggregation periods should the expected min or max be violated for the alert to be fired. */
		numAggregationPeriods: Option[Int] = None,
	  /** Client associated with this alert configuration. Must be a client enabled in one of the containing workflow's triggers. */
		clientId: Option[String] = None,
		errorEnumList: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList] = None,
	  /** Only count final task attempts, not retries. */
		onlyFinalAttempt: Option[Boolean] = None,
	  /** The threshold type for which this alert is being configured. If value falls below expected_min or exceeds expected_max, an alert will be fired. */
		thresholdType: Option[Schema.EnterpriseCrmEventbusProtoTaskAlertConfig.ThresholdTypeEnum] = None
	)
	
	object EnterpriseCrmEventbusProtoConnectorsGenericConnectorTaskConfig {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, EXECUTE_ACTION, LIST_ENTITIES, GET_ENTITY, CREATE_ENTITY, UPDATE_ENTITY, DELETE_ENTITY, EXECUTE_QUERY }
	}
	case class EnterpriseCrmEventbusProtoConnectorsGenericConnectorTaskConfig(
	  /** User-selected connection. */
		connection: Option[Schema.EnterpriseCrmEventbusProtoConnectorsConnection] = None,
	  /** Operation to perform using the configured connection. */
		operation: Option[Schema.EnterpriseCrmEventbusProtoConnectorsGenericConnectorTaskConfig.OperationEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoSuspensionResolutionInfoAudit(
		timestamp: Option[String] = None,
		resolvedByCpi: Option[String] = None,
		resolvedBy: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaFile {
		enum TypeEnum extends Enum[TypeEnum] { case INTEGRATION_FILE_UNSPECIFIED, INTEGRATION, INTEGRATION_CONFIG_VARIABLES }
	}
	case class GoogleCloudIntegrationsV1alphaFile(
	  /** Integration version */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None,
	  /** Integration version config file */
		integrationConfig: Option[Map[String, JsValue]] = None,
	  /** File information like Integration version, Integration Config variables etc. */
		`type`: Option[Schema.GoogleCloudIntegrationsV1alphaFile.TypeEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecuteTestCaseRequest(
	  /** Optional. Input parameters used by test case execution. */
		inputParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None
	)
	
	object EnterpriseCrmEventbusProtoStringArrayFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET, APPEND, SIZE, TO_SET, APPEND_ALL, TO_JSON, SET, REMOVE, REMOVE_AT, CONTAINS, FOR_EACH, FILTER }
	}
	case class EnterpriseCrmEventbusProtoStringArrayFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoStringArrayFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListTestCasesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The test cases corresponding to the specified filter */
		testCases: Option[List[Schema.GoogleCloudIntegrationsV1alphaTestCase]] = None
	)
	
	object EnterpriseCrmEventbusAuthconfigAuthConfigTaskParam {
		enum AllowedCredentialTypesEnum extends Enum[AllowedCredentialTypesEnum] { case CREDENTIAL_TYPE_UNSPECIFIED, USERNAME_AND_PASSWORD, API_KEY, OAUTH2_AUTHORIZATION_CODE, OAUTH2_IMPLICIT, OAUTH2_CLIENT_CREDENTIALS, OAUTH2_RESOURCE_OWNER_CREDENTIALS, JWT, AUTH_TOKEN, SERVICE_ACCOUNT, CLIENT_CERTIFICATE_ONLY, OIDC_TOKEN }
	}
	case class EnterpriseCrmEventbusAuthconfigAuthConfigTaskParam(
		allowedServiceAccountInContext: Option[Boolean] = None,
		useServiceAccountInContext: Option[Boolean] = None,
	  /** A space-delimited list of requested scope permissions. */
		scope: Option[String] = None,
	  /** UUID of the AuthConfig. */
		authConfigId: Option[String] = None,
	  /** Defines the credential types to be supported as Task may restrict specific types to use, e.g. Cloud SQL Task will use username/password type only. */
		allowedCredentialTypes: Option[List[Schema.EnterpriseCrmEventbusAuthconfigAuthConfigTaskParam.AllowedCredentialTypesEnum]] = None
	)
	
	object EnterpriseCrmEventbusProtoParameterMap {
		enum KeyTypeEnum extends Enum[KeyTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
	}
	case class EnterpriseCrmEventbusProtoParameterMap(
	  /** Option to specify key value type for all entries of the map. If provided then field types for all entries must conform to this. */
		keyType: Option[Schema.EnterpriseCrmEventbusProtoParameterMap.KeyTypeEnum] = None,
		entries: Option[List[Schema.EnterpriseCrmEventbusProtoParameterMapEntry]] = None,
		valueType: Option[Schema.EnterpriseCrmEventbusProtoParameterMap.ValueTypeEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectRequest(
	  /** The auth config id necessary to fetch the necessary credentials to create the project for external clients */
		authConfigId: Option[String] = None,
	  /** The name of the Apps Script project to be created. */
		appsScriptProject: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaResolveSuspensionResponse(
	
	)
	
	case class GoogleCloudIntegrationsV1alphaExecutionSnapshotExecutionSnapshotMetadata(
	  /** the task label associated with this snapshot. Could be empty. */
		taskLabel: Option[String] = None,
	  /** Ancestor iteration number for the task(it will only be non-empty if the task is under 'private workflow') */
		ancestorIterationNumbers: Option[List[String]] = None,
	  /** The direct integration which the event execution snapshots belongs to */
		integrationName: Option[String] = None,
	  /** Ancestor task number for the task(it will only be non-empty if the task is under 'private workflow') */
		ancestorTaskNumbers: Option[List[String]] = None,
	  /** The task number associated with this snapshot. */
		taskNumber: Option[String] = None,
	  /** the task name associated with this snapshot. */
		task: Option[String] = None,
	  /** the execution attempt number this snapshot belongs to. */
		executionAttempt: Option[Int] = None,
	  /** the task attempt number this snapshot belongs to. */
		taskAttempt: Option[Int] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse(
	  /** The detailed information of requested executions */
		executions: Option[List[Schema.GoogleCloudIntegrationsV1alphaExecution]] = None,
	  /** The token used to retrieve the next page results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse(
	  /** String representation of the requested file. */
		content: Option[String] = None,
	  /** List containing String represendation for multiple file with type. */
		files: Option[List[Schema.GoogleCloudIntegrationsV1alphaSerializedFile]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaSuspension {
		enum StateEnum extends Enum[StateEnum] { case RESOLUTION_STATE_UNSPECIFIED, PENDING, REJECTED, LIFTED }
	}
	case class GoogleCloudIntegrationsV1alphaSuspension(
	  /** Required. Task id of the associated SuspensionTask. */
		taskId: Option[String] = None,
	  /** Controls the notifications and approval permissions for this suspension. */
		approvalConfig: Option[Schema.GoogleCloudIntegrationsV1alphaSuspensionApprovalConfig] = None,
	  /** Output only. Auto-generated. */
		lastModifyTime: Option[String] = None,
	  /** Metadata pertaining to the resolution of this suspension. */
		audit: Option[Schema.GoogleCloudIntegrationsV1alphaSuspensionAudit] = None,
	  /** Required. State of this suspension, indicating what action a resolver has taken. */
		state: Option[Schema.GoogleCloudIntegrationsV1alphaSuspension.StateEnum] = None,
	  /** Output only. Auto-generated. */
		createTime: Option[String] = None,
	  /** Controls the notifications and resolver permissions for this suspension. */
		suspensionConfig: Option[Schema.EnterpriseCrmEventbusProtoSuspensionConfig] = None,
	  /** Required. ID of the associated execution. */
		eventExecutionInfoId: Option[String] = None,
	  /** Resource name for suspensions suspension/{suspension_id} */
		name: Option[String] = None,
	  /** Required. The name of the originating integration. */
		integration: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoParamSpecEntryProtoDefinition(
	  /** The fully-qualified proto name. This message, for example, would be "enterprise.crm.eventbus.proto.ParamSpecEntry.ProtoDefinition". */
		fullName: Option[String] = None,
	  /** Path to the proto file that contains the message type's definition. */
		path: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1EventingRuntimeDataWebhookData(
	  /** Output only. Name of the Webhook */
		name: Option[String] = None,
	  /** Output only. Additional webhook related field values. */
		additionalVariables: Option[List[Schema.GoogleCloudConnectorsV1ConfigVariable]] = None,
	  /** Output only. Next webhook refresh time. Will be null if refresh is not supported. */
		nextRefreshTime: Option[String] = None,
	  /** Output only. ID to uniquely identify webhook. */
		id: Option[String] = None,
	  /** Output only. Timestamp when the webhook was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. Timestamp when the webhook was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigOauth2AuthCodeFlow(
	  /** PKCE verifier to be used during the auth code exchange. */
		pkceVerifier: Option[String] = None,
	  /** Client secret for user-provided OAuth app. */
		clientSecret: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Whether to enable PKCE when the user performs the auth code flow. */
		enablePkce: Option[Boolean] = None,
	  /** Redirect URI to be provided during the auth code exchange. */
		redirectUri: Option[String] = None,
	  /** Auth URL for Authorization Code Flow */
		authUri: Option[String] = None,
	  /** Scopes the connection will request when the user performs the auth code flow. */
		scopes: Option[List[String]] = None,
	  /** Client ID for user-provided OAuth app. */
		clientId: Option[String] = None,
	  /** Authorization code to be exchanged for access and refresh tokens. */
		authCode: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoField {
		enum FieldTypeEnum extends Enum[FieldTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
		enum CardinalityEnum extends Enum[CardinalityEnum] { case UNSPECIFIED, OPTIONAL }
	}
	case class EnterpriseCrmEventbusProtoField(
	  /** Specifies the data type of the field. */
		fieldType: Option[Schema.EnterpriseCrmEventbusProtoField.FieldTypeEnum] = None,
	  /** This is the transform expression to fetch the input field value. for e.g. $param1$.CONCAT('test'). Keep points - 1. Only input field can have a transform expression. 2. If a transform expression is provided, reference_key will be ignored. 3. If no value is returned after evaluation of transform expression, default_value can be mapped if provided. 4. The field_type should be the type of the final object returned after the transform expression is evaluated. Scrubs the transform expression before logging as value provided by user so may or may not contain PII or SPII data. */
		transformExpression: Option[Schema.EnterpriseCrmEventbusProtoTransformExpression] = None,
	  /** This holds the reference key of the workflow or task parameter. 1. Any workflow parameter, for e.g. $workflowParam1$. 2. Any task input or output parameter, for e.g. $task1_param1$. 3. Any workflow or task parameters with subfield references, for e.g., $task1_param1.employee.id$ */
		referenceKey: Option[String] = None,
	  /** This holds the default values for the fields. This value is supplied by user so may or may not contain PII or SPII data. */
		defaultValue: Option[Schema.EnterpriseCrmEventbusProtoParameterValueType] = None,
	  /** Optional. The fully qualified proto name (e.g. enterprise.crm.storage.Account). Required for output field of type PROTO_VALUE or PROTO_ARRAY. For e.g., if input field_type is BYTES and output field_type is PROTO_VALUE, then fully qualified proto type url should be provided to parse the input bytes. If field_type is &#42;_ARRAY, then all the converted protos are of the same type. */
		protoDefPath: Option[String] = None,
	  /** By default, if the cardinality is unspecified the field is considered required while mapping. */
		cardinality: Option[Schema.EnterpriseCrmEventbusProtoField.CardinalityEnum] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigOauth2JwtBearer(
	  /** JwtClaims providers fields to generate the token. */
		jwtClaims: Option[Schema.GoogleCloudConnectorsV1AuthConfigOauth2JwtBearerJwtClaims] = None,
	  /** Secret version reference containing a PKCS#8 PEM-encoded private key associated with the Client Certificate. This private key will be used to sign JWTs used for the jwt-bearer authorization grant. Specified in the form as: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		clientKey: Option[Schema.GoogleCloudConnectorsV1Secret] = None
	)
	
	object EnterpriseCrmEventbusProtoJsonFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET_PROPERTY, GET_ELEMENT, APPEND_ELEMENT, SIZE, SET_PROPERTY, FLATTEN, FLATTEN_ONCE, MERGE, TO_STRING, TO_INT, TO_DOUBLE, TO_BOOLEAN, TO_PROTO, TO_STRING_ARRAY, TO_INT_ARRAY, TO_DOUBLE_ARRAY, TO_PROTO_ARRAY, TO_BOOLEAN_ARRAY, REMOVE_PROPERTY, RESOLVE_TEMPLATE, EQUALS, FOR_EACH, FILTER_ELEMENTS }
	}
	case class EnterpriseCrmEventbusProtoJsonFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoJsonFunction.FunctionNameEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaFailurePolicy {
		enum RetryStrategyEnum extends Enum[RetryStrategyEnum] { case RETRY_STRATEGY_UNSPECIFIED, IGNORE, NONE, FATAL, FIXED_INTERVAL, LINEAR_BACKOFF, EXPONENTIAL_BACKOFF, RESTART_INTEGRATION_WITH_BACKOFF }
	}
	case class GoogleCloudIntegrationsV1alphaFailurePolicy(
	  /** Defines what happens to the task upon failure. */
		retryStrategy: Option[Schema.GoogleCloudIntegrationsV1alphaFailurePolicy.RetryStrategyEnum] = None,
	  /** Optional. The string condition that will be evaluated to determine if the task should be retried with this failure policy. */
		condition: Option[String] = None,
	  /** Required if retry_strategy is FIXED_INTERVAL or LINEAR/EXPONENTIAL_BACKOFF/RESTART_INTEGRATION_WITH_BACKOFF. Defines the initial interval in seconds for backoff. */
		intervalTime: Option[String] = None,
	  /** Required if retry_strategy is FIXED_INTERVAL or LINEAR/EXPONENTIAL_BACKOFF/RESTART_INTEGRATION_WITH_BACKOFF. Defines the number of times the task will be retried if failed. */
		maxRetries: Option[Int] = None
	)
	
	case class EnterpriseCrmEventbusProtoCloudKmsConfig(
	  /** Optional. The id of GCP project where the KMS key is stored. If not provided, assume the key is stored in the same GCP project defined in Client (tag 14). */
		gcpProjectId: Option[String] = None,
	  /** A Cloud KMS key is a named object containing one or more key versions, along with metadata for the key. A key exists on exactly one key ring tied to a specific location. */
		keyName: Option[String] = None,
	  /** Optional. The service account used for authentication of this KMS key. If this is not provided, the service account in Client.clientSource will be used. */
		serviceAccount: Option[String] = None,
	  /** Location name of the key ring, e.g. "us-west1". */
		locationName: Option[String] = None,
	  /** Optional. Each version of a key contains key material used for encryption or signing. A key's version is represented by an integer, starting at 1. To decrypt data or verify a signature, you must use the same key version that was used to encrypt or sign the data. */
		keyVersionName: Option[String] = None,
	  /** A key ring organizes keys in a specific Google Cloud location and allows you to manage access control on groups of keys. A key ring's name does not need to be unique across a Google Cloud project, but must be unique within a given location. */
		keyRingName: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCancelExecutionResponse(
	  /** True if cancellation performed successfully. */
		isCanceled: Option[Boolean] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoEventParameters(
	  /** Parameters are a part of Event and can be used to communicate between different tasks that are part of the same workflow execution. */
		parameters: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUseTemplateRequest(
	  /** Required. The region of the Integration to be created. */
		integrationRegion: Option[String] = None,
	  /** Required. Integration details which would be created via templates. */
		integrationDetails: Option[Schema.GoogleCloudIntegrationsV1alphaUseTemplateRequestIntegrationDetails] = None,
	  /** Optional. Sub Integration which would be created via templates. */
		subIntegrations: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaUseTemplateRequestIntegrationDetails]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaParameterMap {
		enum KeyTypeEnum extends Enum[KeyTypeEnum] { case INTEGRATION_PARAMETER_DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, BOOLEAN_ARRAY, JSON_VALUE, PROTO_VALUE, PROTO_ARRAY, NON_SERIALIZABLE_OBJECT, PROTO_ENUM, SERIALIZED_OBJECT_VALUE, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY }
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case INTEGRATION_PARAMETER_DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, BOOLEAN_ARRAY, JSON_VALUE, PROTO_VALUE, PROTO_ARRAY, NON_SERIALIZABLE_OBJECT, PROTO_ENUM, SERIALIZED_OBJECT_VALUE, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY }
	}
	case class GoogleCloudIntegrationsV1alphaParameterMap(
	  /** A list of parameter map entries. */
		entries: Option[List[Schema.GoogleCloudIntegrationsV1alphaParameterMapEntry]] = None,
	  /** Option to specify key type for all entries of the map. If provided then field types for all entries must conform to this. */
		keyType: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap.KeyTypeEnum] = None,
	  /** Option to specify value type for all entries of the map. If provided then field types for all entries must conform to this. */
		valueType: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap.ValueTypeEnum] = None
	)
	
	object GoogleCloudConnectorsV1EncryptionKey {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_MANAGED, CUSTOMER_MANAGED }
	}
	case class GoogleCloudConnectorsV1EncryptionKey(
	  /** Type. */
		`type`: Option[Schema.GoogleCloudConnectorsV1EncryptionKey.TypeEnum] = None,
	  /** The [KMS key name] with which the content of the Operation is encrypted. The expected format: `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. Will be empty string if google managed. */
		kmsKeyName: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListExecutionsResponse(
	  /** The token used to retrieve the next page results. */
		nextPageToken: Option[String] = None,
	  /** Required. The detailed information of requested executions. */
		executionInfos: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionInfo]] = None,
	  /** The detailed information of requested executions */
		executions: Option[List[Schema.GoogleCloudIntegrationsV1alphaExecution]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaAuthToken(
	  /** Authentication type, e.g. "Basic", "Bearer", etc. */
		`type`: Option[String] = None,
	  /** The token for the auth type. */
		token: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoAttributes {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, EMAIL, URL, CURRENCY, TIMESTAMP, DOMAIN_NAME }
		enum SearchableEnum extends Enum[SearchableEnum] { case UNSPECIFIED, YES, NO }
	}
	case class EnterpriseCrmEventbusProtoAttributes(
	  /** Things like URL, Email, Currency, Timestamp (rather than string, int64...) */
		dataType: Option[Schema.EnterpriseCrmEventbusProtoAttributes.DataTypeEnum] = None,
	  /** Used to indicate if a ParameterEntry should be converted to ParamIndexes for ST-Spanner full-text search. DEPRECATED: use searchable. */
		isSearchable: Option[Boolean] = None,
	  /** See */
		logSettings: Option[Schema.EnterpriseCrmEventbusProtoLogSettings] = None,
	  /** Used to define defaults. */
		defaultValue: Option[Schema.EnterpriseCrmEventbusProtoValueType] = None,
	  /** Used to indicate if the ParameterEntry is a read only field or not. */
		readOnly: Option[Boolean] = None,
	  /** List of tasks that can view this property, if empty then all. */
		taskVisibility: Option[List[String]] = None,
	  /** Required for event execution. The validation will be done by the event bus when the event is triggered. */
		isRequired: Option[Boolean] = None,
		searchable: Option[Schema.EnterpriseCrmEventbusProtoAttributes.SearchableEnum] = None,
	  /** True if this workflow parameter should be masked in the logs */
		masked: Option[Boolean] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoSerializedObjectParameter(
		objectValue: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoIntFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, GREATER_THAN_EQUAL_TO, GREATER_THAN, LESS_THAN_EQUAL_TO, LESS_THAN, TO_DOUBLE, TO_STRING, EQUALS, TO_JSON, MOD, EPOCH_TO_HUMAN_READABLE_TIME }
	}
	case class EnterpriseCrmEventbusProtoIntFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoIntFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecuteEventResponse(
	  /** The id of the execution corresponding to this run of integration. */
		executionId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTakeoverEditLockResponse(
	  /** Version after the lock is acquired by the new user. */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaIntegrationVersionTemplate(
	  /** Required. Templatized version of integration. */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None,
	  /** Required. Unique Key of the IntegrationVersion. */
		key: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoStringParameterArray(
		stringValues: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTakeoverTestCaseEditLockRequest(
	
	)
	
	object GoogleCloudIntegrationsV1alphaTriggerConfig {
		enum TriggerTypeEnum extends Enum[TriggerTypeEnum] { case TRIGGER_TYPE_UNSPECIFIED, CRON, API, SFDC_CHANNEL, CLOUD_PUBSUB_EXTERNAL, SFDC_CDC_CHANNEL, CLOUD_SCHEDULER, INTEGRATION_CONNECTOR_TRIGGER, PRIVATE_TRIGGER, CLOUD_PUBSUB }
		enum NextTasksExecutionPolicyEnum extends Enum[NextTasksExecutionPolicyEnum] { case NEXT_TASKS_EXECUTION_POLICY_UNSPECIFIED, RUN_ALL_MATCH, RUN_FIRST_MATCH }
	}
	case class GoogleCloudIntegrationsV1alphaTriggerConfig(
	  /** Optional. Auto-generated trigger ID. The ID is based on the properties that you define in the trigger config. For example, for an API trigger, the trigger ID follows the format: api_trigger/TRIGGER_NAME Where trigger config has properties with value {"Trigger name": TRIGGER_NAME} */
		triggerId: Option[String] = None,
	  /** Optional. Optional Error catcher id of the error catch flow which will be executed when execution error happens in the task */
		errorCatcherId: Option[String] = None,
	  /** Optional. Type of trigger */
		triggerType: Option[Schema.GoogleCloudIntegrationsV1alphaTriggerConfig.TriggerTypeEnum] = None,
	  /** Optional. User-provided description intended to give additional business context about the task. */
		description: Option[String] = None,
	  /** Optional. Informs the front-end application where to draw this error catcher config on the UI. */
		position: Option[Schema.GoogleCloudIntegrationsV1alphaCoordinate] = None,
	  /** Optional. Name of the trigger. Example: "API Trigger", "Cloud Pub Sub Trigger" When set will be sent out to monitoring dashabord for tracking purpose. */
		trigger: Option[String] = None,
	  /** Optional. List of input variables for the api trigger. */
		inputVariables: Option[Schema.GoogleCloudIntegrationsV1alphaTriggerConfigVariables] = None,
	  /** Optional. Dictates how next tasks will be executed. */
		nextTasksExecutionPolicy: Option[Schema.GoogleCloudIntegrationsV1alphaTriggerConfig.NextTasksExecutionPolicyEnum] = None,
	  /** Optional. Configurable properties of the trigger, not to be confused with integration parameters. E.g. "name" is a property for API triggers and "subscription" is a property for Pub/sub triggers. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. List of output variables for the api trigger. */
		outputVariables: Option[Schema.GoogleCloudIntegrationsV1alphaTriggerConfigVariables] = None,
	  /** Optional. Cloud Scheduler Trigger related metadata */
		cloudSchedulerConfig: Option[Schema.GoogleCloudIntegrationsV1alphaCloudSchedulerConfig] = None,
	  /** Required. A number to uniquely identify each trigger config within the integration on UI. */
		triggerNumber: Option[String] = None,
	  /** Optional. Set of tasks numbers from where the integration execution is started by this trigger. If this is empty, then integration is executed with default start tasks. In the list of start tasks, none of two tasks can have direct ancestor-descendant relationships (i.e. in a same integration execution graph). */
		startTasks: Option[List[Schema.GoogleCloudIntegrationsV1alphaNextTask]] = None,
	  /** Optional. An alert threshold configuration for the [trigger + client + integration] tuple. If these values are not specified in the trigger config, default values will be populated by the system. Note that there must be exactly one alert threshold configured per [client + trigger + integration] when published. */
		alertConfig: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationAlertConfig]] = None,
	  /** Optional. The user created label for a particular trigger. */
		label: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoStringFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, CONCAT, TO_UPPERCASE, TO_LOWERCASE, CONTAINS, SPLIT, LENGTH, EQUALS, TO_INT, TO_DOUBLE, TO_BOOLEAN, TO_BASE_64, TO_JSON, EQUALS_IGNORE_CASE, REPLACE_ALL, SUBSTRING, RESOLVE_TEMPLATE, DECODE_BASE64_STRING }
	}
	case class EnterpriseCrmEventbusProtoStringFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoStringFunction.FunctionNameEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaExecution {
		enum ExecutionMethodEnum extends Enum[ExecutionMethodEnum] { case EXECUTION_METHOD_UNSPECIFIED, POST, POST_TO_QUEUE, SCHEDULE }
		enum IntegrationVersionStateEnum extends Enum[IntegrationVersionStateEnum] { case INTEGRATION_STATE_UNSPECIFIED, DRAFT, ACTIVE, ARCHIVED, SNAPSHOT }
	}
	case class GoogleCloudIntegrationsV1alphaExecution(
	  /** Output only. An increasing sequence that is set when a new snapshot is created */
		snapshotNumber: Option[String] = None,
	  /** The ways user posts this event. */
		executionMethod: Option[Schema.GoogleCloudIntegrationsV1alphaExecution.ExecutionMethodEnum] = None,
	  /** Event parameters returned as part of the response. In the case of error, the `ErrorInfo` field is returned in the following format: { "ErrorInfo": { "message": String, "code": Number } }  */
		responseParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Output only. Replay info for the execution */
		replayInfo: Option[Schema.GoogleCloudIntegrationsV1alphaExecutionReplayInfo] = None,
	  /** Auto-generated primary key. */
		name: Option[String] = None,
	  /** Detailed info of this execution. */
		executionDetails: Option[Schema.GoogleCloudIntegrationsV1alphaExecutionDetails] = None,
	  /** Output only. Created time of the execution. */
		createTime: Option[String] = None,
	  /** Direct sub executions of the following Execution. */
		directSubExecutions: Option[List[Schema.GoogleCloudIntegrationsV1alphaExecution]] = None,
		responseParams: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** Output only. Last modified time of the execution. */
		updateTime: Option[String] = None,
	  /** Event parameters come in as part of the request. */
		requestParams: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** Event parameters come in as part of the request. */
		requestParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Output only. State of the integration version */
		integrationVersionState: Option[Schema.GoogleCloudIntegrationsV1alphaExecution.IntegrationVersionStateEnum] = None,
	  /** The trigger id of the integration trigger config. If both trigger_id and client_id is present, the integration is executed from the start tasks provided by the matching trigger config otherwise it is executed from the default start tasks. */
		triggerId: Option[String] = None,
	  /** The execution info about this event. */
		eventExecutionDetails: Option[Schema.EnterpriseCrmEventbusProtoEventExecutionDetails] = None,
	  /** Cloud Logging details for the integration version */
		cloudLoggingDetails: Option[Schema.GoogleCloudIntegrationsV1alphaCloudLoggingDetails] = None
	)
	
	object GoogleCloudIntegrationsV1alphaTaskConfig {
		enum NextTasksExecutionPolicyEnum extends Enum[NextTasksExecutionPolicyEnum] { case NEXT_TASKS_EXECUTION_POLICY_UNSPECIFIED, RUN_ALL_MATCH, RUN_FIRST_MATCH }
		enum JsonValidationOptionEnum extends Enum[JsonValidationOptionEnum] { case JSON_VALIDATION_OPTION_UNSPECIFIED, SKIP, PRE_EXECUTION, POST_EXECUTION, PRE_POST_EXECUTION }
		enum ExternalTaskTypeEnum extends Enum[ExternalTaskTypeEnum] { case EXTERNAL_TASK_TYPE_UNSPECIFIED, NORMAL_TASK, ERROR_TASK }
		enum TaskExecutionStrategyEnum extends Enum[TaskExecutionStrategyEnum] { case TASK_EXECUTION_STRATEGY_UNSPECIFIED, WHEN_ALL_SUCCEED, WHEN_ANY_SUCCEED, WHEN_ALL_TASKS_AND_CONDITIONS_SUCCEED }
	}
	case class GoogleCloudIntegrationsV1alphaTaskConfig(
	  /** Optional. Optional Error catcher id of the error catch flow which will be executed when execution error happens in the task */
		errorCatcherId: Option[String] = None,
	  /** Optional. Used to define task-template name if task is of type task-template */
		taskTemplate: Option[String] = None,
	  /** Optional. Informs the front-end application where to draw this error catcher config on the UI. */
		position: Option[Schema.GoogleCloudIntegrationsV1alphaCoordinate] = None,
	  /** Optional. The policy dictating the execution of the next set of tasks for the current task. */
		nextTasksExecutionPolicy: Option[Schema.GoogleCloudIntegrationsV1alphaTaskConfig.NextTasksExecutionPolicyEnum] = None,
	  /** Optional. The set of tasks that are next in line to be executed as per the execution graph defined for the parent event, specified by `event_config_id`. Each of these next tasks are executed only if the condition associated with them evaluates to true. */
		nextTasks: Option[List[Schema.GoogleCloudIntegrationsV1alphaNextTask]] = None,
	  /** Optional. Determines what action to take upon successful task completion. */
		successPolicy: Option[Schema.GoogleCloudIntegrationsV1alphaSuccessPolicy] = None,
	  /** Optional. If set, overrides the option configured in the Task implementation class. */
		jsonValidationOption: Option[Schema.GoogleCloudIntegrationsV1alphaTaskConfig.JsonValidationOptionEnum] = None,
	  /** Optional. User-provided label that is attached to this TaskConfig in the UI. */
		displayName: Option[String] = None,
	  /** Optional. The name for the task. */
		task: Option[String] = None,
	  /** Optional. User-provided description intended to give additional business context about the task. */
		description: Option[String] = None,
	  /** Optional. Determines the number of times the task will be retried on failure and with what retry strategy. This is applicable for asynchronous calls to Eventbus alone (Post To Queue, Schedule etc.). */
		failurePolicy: Option[Schema.GoogleCloudIntegrationsV1alphaFailurePolicy] = None,
	  /** Optional. Determines the number of times the task will be retried on failure and with what retry strategy. This is applicable for synchronous calls to Eventbus alone (Post). */
		synchronousCallFailurePolicy: Option[Schema.GoogleCloudIntegrationsV1alphaFailurePolicy] = None,
	  /** Optional. External task type of the task */
		externalTaskType: Option[Schema.GoogleCloudIntegrationsV1alphaTaskConfig.ExternalTaskTypeEnum] = None,
	  /** Optional. The list of conditional failure policies that will be applied to the task in order. */
		conditionalFailurePolicies: Option[Schema.GoogleCloudIntegrationsV1alphaConditionalFailurePolicies] = None,
	  /** Optional. The policy dictating the execution strategy of this task. */
		taskExecutionStrategy: Option[Schema.GoogleCloudIntegrationsV1alphaTaskConfig.TaskExecutionStrategyEnum] = None,
	  /** Optional. The customized parameters the user can pass to this task. */
		parameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaEventParameter]] = None,
	  /** Required. The identifier of this task within its parent event config, specified by the client. This should be unique among all the tasks belong to the same event config. We use this field as the identifier to find next tasks (via field `next_tasks.task_id`). */
		taskId: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoErrorDetail {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARN, INFO }
	}
	case class EnterpriseCrmEventbusProtoErrorDetail(
	  /** The task try-number, in which, the error occurred. If zero, the error happened at the event level. */
		taskNumber: Option[Int] = None,
	  /** The severity of the error: ERROR|WARN|INFO. */
		severity: Option[Schema.EnterpriseCrmEventbusProtoErrorDetail.SeverityEnum] = None,
	  /** The associated error-code, which can be a common or internal code. */
		errorCode: Option[Schema.CrmlogErrorCode] = None,
	  /** The full text of the error message, including any parameters that were thrown along with the exception. */
		errorMessage: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleStringRegex(
	  /** The regex applied to the input value(s). */
		regex: Option[String] = None,
	  /** Whether the regex matcher is applied exclusively (if true, matching values will be rejected). */
		exclusive: Option[Boolean] = None
	)
	
	case class EnterpriseCrmEventbusProtoFieldMappingConfig(
		mappedFields: Option[List[Schema.EnterpriseCrmEventbusProtoMappedField]] = None
	)
	
	object EnterpriseCrmEventbusProtoBaseFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, NOW_IN_MILLIS, INT_LIST, ENVIRONMENT, GET_EXECUTION_ID, GET_INTEGRATION_NAME, GET_REGION, GET_UUID, GET_PROJECT_ID }
	}
	case class EnterpriseCrmEventbusProtoBaseFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoBaseFunction.FunctionNameEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoFunctionType(
		intFunction: Option[Schema.EnterpriseCrmEventbusProtoIntFunction] = None,
		stringFunction: Option[Schema.EnterpriseCrmEventbusProtoStringFunction] = None,
		booleanArrayFunction: Option[Schema.EnterpriseCrmEventbusProtoBooleanArrayFunction] = None,
		doubleArrayFunction: Option[Schema.EnterpriseCrmEventbusProtoDoubleArrayFunction] = None,
		intArrayFunction: Option[Schema.EnterpriseCrmEventbusProtoIntArrayFunction] = None,
		booleanFunction: Option[Schema.EnterpriseCrmEventbusProtoBooleanFunction] = None,
		stringArrayFunction: Option[Schema.EnterpriseCrmEventbusProtoStringArrayFunction] = None,
		doubleFunction: Option[Schema.EnterpriseCrmEventbusProtoDoubleFunction] = None,
		protoArrayFunction: Option[Schema.EnterpriseCrmEventbusProtoProtoArrayFunction] = None,
		jsonFunction: Option[Schema.EnterpriseCrmEventbusProtoJsonFunction] = None,
		protoFunction: Option[Schema.EnterpriseCrmEventbusProtoProtoFunction] = None,
	  /** LINT.IfChange */
		baseFunction: Option[Schema.EnterpriseCrmEventbusProtoBaseFunction] = None
	)
	
	object EnterpriseCrmEventbusProtoFailurePolicy {
		enum RetryStrategyEnum extends Enum[RetryStrategyEnum] { case UNSPECIFIED, IGNORE, NONE, FATAL, FIXED_INTERVAL, LINEAR_BACKOFF, EXPONENTIAL_BACKOFF, RESTART_WORKFLOW_WITH_BACKOFF }
	}
	case class EnterpriseCrmEventbusProtoFailurePolicy(
	  /** Required if retry_strategy is FIXED_INTERVAL or LINEAR/EXPONENTIAL_BACKOFF/RESTART_WORKFLOW_WITH_BACKOFF. Defines the number of times the task will be retried if failed. */
		maxNumRetries: Option[Int] = None,
	  /** Defines what happens to the task upon failure. */
		retryStrategy: Option[Schema.EnterpriseCrmEventbusProtoFailurePolicy.RetryStrategyEnum] = None,
	  /** Required if retry_strategy is FIXED_INTERVAL or LINEAR/EXPONENTIAL_BACKOFF/RESTART_WORKFLOW_WITH_BACKOFF. Defines the initial interval for backoff. */
		intervalInSeconds: Option[String] = None,
	  /** Optional. The retry condition that will be evaluated for this failure policy with the corresponding retry strategy. */
		retryCondition: Option[String] = None
	)
	
	object GoogleCloudConnectorsV1EventingStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, ERROR, INGRESS_ENDPOINT_REQUIRED }
	}
	case class GoogleCloudConnectorsV1EventingStatus(
	  /** Output only. Description of error if State is set to "ERROR". */
		description: Option[String] = None,
	  /** Output only. State. */
		state: Option[Schema.GoogleCloudConnectorsV1EventingStatus.StateEnum] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry {
		enum InOutTypeEnum extends Enum[InOutTypeEnum] { case IN_OUT_TYPE_UNSPECIFIED, IN, OUT, IN_OUT }
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry(
		producer: Option[String] = None,
	  /** This schema will be used to validate runtime JSON-typed values of this parameter. */
		jsonSchema: Option[String] = None,
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given fired event. These parameters must be predefined in the workflow definition. */
		key: Option[String] = None,
		required: Option[Boolean] = None,
	  /** Child parameters nested within this parameter. This field only applies to protobuf parameters */
		children: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry]] = None,
	  /** If the data type is of type proto or proto array, this field needs to be populated with the fully qualified proto name. This message, for example, would be "enterprise.crm.frontends.eventbus.proto.WorkflowParameterEntry". */
		protoDefPath: Option[String] = None,
	  /** Default values for the defined keys. Each value can either be string, int, double or any proto message or a serialized object. */
		defaultValue: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterValueType] = None,
	  /** Indicates whether this variable contains large data and need to be uploaded to Cloud Storage. */
		containsLargeData: Option[Boolean] = None,
	  /** The identifier of the node (TaskConfig/TriggerConfig) this parameter was produced by, if it is a transient param or a copy of an input param. */
		producedBy: Option[Schema.EnterpriseCrmEventbusProtoNodeIdentifier] = None,
	  /** Specifies the input/output type for the parameter. */
		inOutType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry.InOutTypeEnum] = None,
	  /** Whether this parameter is a transient parameter. */
		isTransient: Option[Boolean] = None,
	  /** Metadata information about the parameters. */
		attributes: Option[Schema.EnterpriseCrmEventbusProtoAttributes] = None,
	  /** The data type of the parameter. */
		dataType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry.DataTypeEnum] = None,
	  /** Optional. The description about the parameter */
		description: Option[String] = None,
	  /** The name of the protobuf type if the parameter has a protobuf data type. */
		protoDefName: Option[String] = None,
	  /** The name (without prefix) to be displayed in the UI for this parameter. E.g. if the key is "foo.bar.myName", then the name would be "myName". */
		name: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSuspensionAudit(
	  /** Time at which this suspension was resolved. */
		resolveTime: Option[String] = None,
	  /** Email address of the person who resolved this suspension. */
		resolver: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoSuspensionResolutionInfo {
		enum StatusEnum extends Enum[StatusEnum] { case PENDING_UNSPECIFIED, REJECTED, LIFTED, CANCELED }
		enum ProductEnum extends Enum[ProductEnum] { case UNSPECIFIED_PRODUCT, IP, APIGEE, SECURITY }
	}
	case class EnterpriseCrmEventbusProtoSuspensionResolutionInfo(
	  /** Auto-generated. */
		lastModifiedTimestamp: Option[String] = None,
		audit: Option[Schema.EnterpriseCrmEventbusProtoSuspensionResolutionInfoAudit] = None,
	  /** Required. Task number of the associated SuspensionTask. */
		taskNumber: Option[String] = None,
	  /** KMS info, used by cmek/gmek integration */
		cloudKmsConfig: Option[Schema.EnterpriseCrmEventbusProtoCloudKmsConfig] = None,
	  /** Required. ID of the associated execution. */
		eventExecutionInfoId: Option[String] = None,
	  /** Encrypted SuspensionResolutionInfo */
		encryptedSuspensionResolutionInfo: Option[String] = None,
	  /** Wrapped dek */
		wrappedDek: Option[String] = None,
	  /** The event data user sends as request. */
		clientId: Option[String] = None,
		status: Option[Schema.EnterpriseCrmEventbusProtoSuspensionResolutionInfo.StatusEnum] = None,
	  /** Required. The name of the originating workflow. */
		workflowName: Option[String] = None,
	  /** The origin of the suspension for periodic notifications. */
		externalTraffic: Option[Schema.EnterpriseCrmEventbusProtoExternalTraffic] = None,
	  /** Which Google product the suspension belongs to. If not set, the suspension belongs to Integration Platform by default. */
		product: Option[Schema.EnterpriseCrmEventbusProtoSuspensionResolutionInfo.ProductEnum] = None,
	  /** Auto-generated. */
		createdTimestamp: Option[String] = None,
	  /** Primary key for the SuspensionResolutionInfoTable. */
		suspensionId: Option[String] = None,
		suspensionConfig: Option[Schema.EnterpriseCrmEventbusProtoSuspensionConfig] = None
	)
	
	object EnterpriseCrmEventbusProtoBooleanArrayFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET, APPEND, SIZE, TO_SET, APPEND_ALL, TO_JSON, SET, REMOVE, REMOVE_AT, CONTAINS, FOR_EACH, FILTER }
	}
	case class EnterpriseCrmEventbusProtoBooleanArrayFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoBooleanArrayFunction.FunctionNameEnum] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoRollbackStrategy(
	  /** Required. This is the name of the task that needs to be executed upon rollback of this task. */
		rollbackTaskImplementationClassName: Option[String] = None,
	  /** Required. These are the tasks numbers of the tasks whose `rollback_strategy.rollback_task_implementation_class_name` needs to be executed upon failure of this task. */
		taskNumbersToRollback: Option[List[String]] = None,
	  /** Optional. The customized parameters the user can pass to this task. */
		parameters: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse(
	  /** The integrations which match the request. */
		integrationVersions: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = None,
	  /** Whether the user has no permission on the version or not. */
		noPermission: Option[Boolean] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaRuntimeActionSchema(
	  /** Output parameter schema for the action. */
		outputSchema: Option[String] = None,
	  /** Input parameter schema for the action. */
		inputSchema: Option[String] = None,
	  /** Name of the action. */
		action: Option[String] = None
	)
	
	object GoogleCloudConnectorsV1LogConfig {
		enum LevelEnum extends Enum[LevelEnum] { case LOG_LEVEL_UNSPECIFIED, ERROR, INFO, DEBUG }
	}
	case class GoogleCloudConnectorsV1LogConfig(
	  /** Optional. Log configuration level. */
		level: Option[Schema.GoogleCloudConnectorsV1LogConfig.LevelEnum] = None,
	  /** Enabled represents whether logging is enabled or not for a connection. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse(
	  /** String representation of the test case. */
		content: Option[String] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoTaskConfig {
		enum NextTasksExecutionPolicyEnum extends Enum[NextTasksExecutionPolicyEnum] { case UNSPECIFIED, RUN_ALL_MATCH, RUN_FIRST_MATCH }
		enum TaskExecutionStrategyEnum extends Enum[TaskExecutionStrategyEnum] { case WHEN_ALL_SUCCEED, WHEN_ANY_SUCCEED, WHEN_ALL_TASKS_AND_CONDITIONS_SUCCEED }
		enum ExternalTaskTypeEnum extends Enum[ExternalTaskTypeEnum] { case EXTERNAL_TASK_TYPE_UNSPECIFIED, NORMAL_TASK, ERROR_TASK }
		enum JsonValidationOptionEnum extends Enum[JsonValidationOptionEnum] { case UNSPECIFIED_JSON_VALIDATION_OPTION, SKIP, PRE_EXECUTION, POST_EXECUTION, PRE_POST_EXECUTION }
		enum TaskTypeEnum extends Enum[TaskTypeEnum] { case TASK, ASIS_TEMPLATE, IO_TEMPLATE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoTaskConfig(
	  /** REQUIRED: the identifier of this task within its parent event config, specified by the client. This should be unique among all the tasks belong to the same event config. We use this field as the identifier to find next tasks (via field `next_tasks.task_number`). */
		taskNumber: Option[String] = None,
	  /** Optional Error catcher id of the error catch flow which will be executed when execution error happens in the task */
		errorCatcherId: Option[String] = None,
	  /** Optional. User-provided label that is attached to precondition in the UI. */
		preconditionLabel: Option[String] = None,
	  /** The set of tasks that are next in line to be executed as per the execution graph defined for the parent event, specified by `event_config_id`. Each of these next tasks are executed only if the condition associated with them evaluates to true. */
		nextTasks: Option[List[Schema.EnterpriseCrmEventbusProtoNextTask]] = None,
	  /** User-provided description intended to give more business context about the task. */
		description: Option[String] = None,
	  /** Used to define task-template name if task is of type task-template */
		taskTemplateName: Option[String] = None,
	  /** Alert configurations on error rate, warning rate, number of runs, durations, etc. */
		alertConfigs: Option[List[Schema.EnterpriseCrmEventbusProtoTaskAlertConfig]] = None,
	  /** A string template that allows user to configure task parameters (with either literal default values or tokens which will be resolved at execution time) for the task. It will eventually replace the old "parameters" field. */
		taskSpec: Option[String] = None,
	  /** User-provided label that is attached to this TaskConfig in the UI. */
		label: Option[String] = None,
	  /** Optional. Determines the number of times the task will be retried on failure and with what retry strategy. This is applicable for synchronous calls to Eventbus alone (Post). */
		synchronousCallFailurePolicy: Option[Schema.EnterpriseCrmEventbusProtoFailurePolicy] = None,
	  /** Auto-generated. */
		lastModifiedTime: Option[String] = None,
	  /** If this config contains a TypedTask, allow validation to succeed if an input is read from the output of another TypedTask whose output type is declared as a superclass of the requested input type. For instance, if the previous task declares an output of type Message, any task with this flag enabled will pass validation when attempting to read any proto Message type from the resultant Event parameter. */
		disableStrictTypeValidation: Option[Boolean] = None,
	  /** The creator's email address. Auto-generated from the user's email. */
		creatorEmail: Option[String] = None,
	  /** Optional. Contains information about what needs to be done upon failure (either a permanent error or after it has been retried too many times). */
		rollbackStrategy: Option[Schema.EnterpriseCrmFrontendsEventbusProtoRollbackStrategy] = None,
	  /** The policy dictating the execution of the next set of tasks for the current task. */
		nextTasksExecutionPolicy: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig.NextTasksExecutionPolicyEnum] = None,
	  /** Optional. Standard filter expression evaluated before execution. Independent of other conditions and tasks. Can be used to enable rollout. e.g. "rollout(5)" will only allow 5% of incoming traffic to task. */
		precondition: Option[String] = None,
	  /** The name for the task. */
		taskName: Option[String] = None,
	  /** The policy dictating the execution strategy of this task. */
		taskExecutionStrategy: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig.TaskExecutionStrategyEnum] = None,
	  /** The customized parameters the user can pass to this task. */
		parameters: Option[Map[String, Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** Copy of the task entity that this task config is an instance of. */
		taskEntity: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskEntity] = None,
		externalTaskType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig.ExternalTaskTypeEnum] = None,
	  /** Optional. Informs the front-end application where to draw this task config on the UI. */
		position: Option[Schema.EnterpriseCrmEventbusProtoCoordinate] = None,
	  /** Determines what action to take upon successful task completion. */
		successPolicy: Option[Schema.EnterpriseCrmEventbusProtoSuccessPolicy] = None,
	  /** The number of edges leading into this TaskConfig. */
		incomingEdgeCount: Option[Int] = None,
	  /** Optional. Determines the number of times the task will be retried on failure and with what retry strategy. This is applicable for asynchronous calls to Eventbus alone (Post To Queue, Schedule etc.). */
		failurePolicy: Option[Schema.EnterpriseCrmEventbusProtoFailurePolicy] = None,
	  /** If set, overrides the option configured in the Task implementation class. */
		jsonValidationOption: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig.JsonValidationOptionEnum] = None,
	  /** Optional. Determines the number of times the task will be retried on failure and with what retry strategy. This is applicable for synchronous calls to Eventbus alone (Post). */
		conditionalFailurePolicies: Option[Schema.EnterpriseCrmEventbusProtoConditionalFailurePolicies] = None,
	  /** Defines the type of the task */
		taskType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig.TaskTypeEnum] = None,
	  /** Auto-generated. */
		createTime: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoMappedField(
	  /** The input field being mapped from. */
		inputField: Option[Schema.EnterpriseCrmEventbusProtoField] = None,
	  /** The output field being mapped to. */
		outputField: Option[Schema.EnterpriseCrmEventbusProtoField] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListSuspensionsResponse(
	  /** Token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The suspensions for the relevant execution which the caller has permissions to view and resolve. */
		suspensions: Option[List[Schema.GoogleCloudIntegrationsV1alphaSuspension]] = None
	)
	
	case class EnterpriseCrmEventbusProtoTeardown(
	  /** Required. */
		teardownTaskConfigs: Option[List[Schema.EnterpriseCrmEventbusProtoTeardownTaskConfig]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaJwt(
	  /** User's pre-shared secret to sign the token. */
		secret: Option[String] = None,
	  /** Identifies which algorithm is used to generate the signature. */
		jwtHeader: Option[String] = None,
	  /** The token calculated by the header, payload and signature. */
		jwt: Option[String] = None,
	  /** Contains a set of claims. The JWT specification defines seven Registered Claim Names which are the standard fields commonly included in tokens. Custom claims are usually also included, depending on the purpose of the token. */
		jwtPayload: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoSuspensionAuthPermissions(
	  /** Represents a Gaia identity for a person or service account. */
		gaiaIdentity: Option[Schema.EnterpriseCrmEventbusProtoSuspensionAuthPermissionsGaiaIdentity] = None,
		mdbGroup: Option[String] = None,
		googleGroup: Option[Schema.EnterpriseCrmEventbusProtoSuspensionAuthPermissionsGaiaIdentity] = None,
		loasRole: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCloudKmsConfig(
	  /** Required. A Cloud KMS key is a named object containing one or more key versions, along with metadata for the key. A key exists on exactly one key ring tied to a specific location. */
		key: Option[String] = None,
	  /** Required. Location name of the key ring, e.g. "us-west1". */
		kmsLocation: Option[String] = None,
	  /** Required. A key ring organizes keys in a specific Google Cloud location and allows you to manage access control on groups of keys. A key ring's name does not need to be unique across a Google Cloud project, but must be unique within a given location. */
		kmsRing: Option[String] = None,
	  /** Optional. The gcp project id of the project where the kms key stored. If empty, the kms key is stored at the same project as customer's project and ecrypted with CMEK, otherwise, the kms key is stored in the tenant project and encrypted with GMEK */
		kmsProjectId: Option[String] = None,
	  /** Optional. Each version of a key contains key material used for encryption or signing. A key's version is represented by an integer, starting at 1. To decrypt data or verify a signature, you must use the same key version that was used to encrypt or sign the data. */
		keyVersion: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaExecuteTestCaseResponse {
		enum TestExecutionStateEnum extends Enum[TestExecutionStateEnum] { case STATE_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudIntegrationsV1alphaExecuteTestCaseResponse(
	  /** State of the test case execution */
		testExecutionState: Option[Schema.GoogleCloudIntegrationsV1alphaExecuteTestCaseResponse.TestExecutionStateEnum] = None,
	  /** The id of the execution corresponding to this run of integration. */
		executionId: Option[String] = None,
	  /** Results of each assertions ran during execution of test case. */
		assertionResults: Option[List[Schema.GoogleCloudIntegrationsV1alphaAssertionResult]] = None,
	  /** OUTPUT parameters in format of Map. Where Key is the name of the parameter. Note: Name of the system generated parameters are wrapped by backtick(`) to distinguish them from the user defined parameters. */
		outputParameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectResponse(
	  /** The id of the linked Apps Script project. */
		scriptId: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaTestCase {
		enum DatabasePersistencePolicyEnum extends Enum[DatabasePersistencePolicyEnum] { case DATABASE_PERSISTENCE_POLICY_UNSPECIFIED, DATABASE_PERSISTENCE_DISABLED, DATABASE_PERSISTENCE_ASYNC }
	}
	case class GoogleCloudIntegrationsV1alphaTestCase(
	  /** Auto-generated. */
		createTime: Option[String] = None,
	  /** Optional. The edit lock holder's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		lockHolderEmail: Option[String] = None,
	  /** Optional. However, the test case doesn't mock or assert anything without test_task_configs. */
		testTaskConfigs: Option[List[Schema.GoogleCloudIntegrationsV1alphaTestTaskConfig]] = None,
	  /** Required. This defines the trigger ID in workflow which is considered to be executed as starting point of the test case */
		triggerId: Option[String] = None,
	  /** Optional. Auto-generated. */
		triggerConfig: Option[Schema.GoogleCloudIntegrationsV1alphaTriggerConfig] = None,
	  /** Optional. The creator's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		creatorEmail: Option[String] = None,
	  /** Optional. Parameters that are expected to be passed to the test case when the test case is triggered. This gives the user the ability to provide default values. This should include all the output variables of the trigger as input variables. */
		testInputParameters: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationParameter]] = None,
	  /** The last modifier's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		lastModifierEmail: Option[String] = None,
	  /** Optional. Description of the test case. */
		description: Option[String] = None,
	  /** Output only. Auto-generated primary key. */
		name: Option[String] = None,
	  /** Required. The display name of test case. */
		displayName: Option[String] = None,
	  /** Auto-generated. */
		updateTime: Option[String] = None,
	  /** Optional. Various policies for how to persist the test execution info including execution info, execution export info, execution metadata index and execution param index.. */
		databasePersistencePolicy: Option[Schema.GoogleCloudIntegrationsV1alphaTestCase.DatabasePersistencePolicyEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaImportTemplateResponse(
	  /** Sub integration versions which are imported. */
		subIntegrationVersions: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = None,
	  /** IntegrationVersion after the import. */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUploadTestCaseResponse(
	  /** The uploaded TestCase */
		testCase: Option[Schema.GoogleCloudIntegrationsV1alphaTestCase] = None
	)
	
	case class EnterpriseCrmEventbusProtoDoubleParameterArray(
		doubleValues: Option[List[BigDecimal]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case FILE_FORMAT_UNSPECIFIED, JSON, YAML }
	}
	case class GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest(
	  /** The textproto of the IntegrationVersion. */
		content: Option[String] = None,
	  /** File format for upload request. */
		fileFormat: Option[Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest.FileFormatEnum] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoParamSpecsMessage(
		parameters: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParamSpecEntry]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListIntegrationsResponse(
	  /** The next page token for the response. */
		nextPageToken: Option[String] = None,
	  /** The integrations which match the request. */
		integrations: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegration]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest(
	  /** Optional. Config parameters used during integration execution. */
		configParameters: Option[Map[String, JsValue]] = None
	)
	
	object EnterpriseCrmEventbusProtoEventExecutionDetails {
		enum EventExecutionStateEnum extends Enum[EventExecutionStateEnum] { case UNSPECIFIED, ON_HOLD, IN_PROCESS, SUCCEEDED, FAILED, CANCELED, RETRY_ON_HOLD, SUSPENDED }
	}
	case class EnterpriseCrmEventbusProtoEventExecutionDetails(
	  /** If the execution is manually canceled, this field will contain the reason for cancellation. */
		cancelReason: Option[String] = None,
		eventExecutionSnapshot: Option[List[Schema.EnterpriseCrmEventbusProtoEventExecutionSnapshot]] = None,
		eventAttemptStats: Option[List[Schema.EnterpriseCrmEventbusProtoEventExecutionDetailsEventAttemptStats]] = None,
		eventExecutionState: Option[Schema.EnterpriseCrmEventbusProtoEventExecutionDetails.EventExecutionStateEnum] = None,
	  /** Used internally and shouldn't be exposed to users. A counter for the cron job to record how many times this event is in in_process state but don't have a lock consecutively/ */
		ryeLockUnheldCount: Option[Int] = None,
	  /** The network address (aka. bns address) that indicates where the event executor is running. */
		networkAddress: Option[String] = None,
	  /** Indicates the number of times the execution has restarted from the beginning. */
		eventRetriesFromBeginningCount: Option[Int] = None,
	  /** The log file path (aka. cns address) for this event. */
		logFilePath: Option[String] = None,
	  /** Total size of all event_execution_snapshots for an execution */
		eventExecutionSnapshotsSize: Option[String] = None,
	  /** Next scheduled execution time in case the execution status was RETRY_ON_HOLD. */
		nextExecutionTime: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaAssertion {
		enum AssertionStrategyEnum extends Enum[AssertionStrategyEnum] { case ASSERTION_STRATEGY_UNSPECIFIED, ASSERT_SUCCESSFUL_EXECUTION, ASSERT_FAILED_EXECUTION, ASSERT_NO_EXECUTION, ASSERT_EQUALS, ASSERT_NOT_EQUALS, ASSERT_CONTAINS, ASSERT_CONDITION }
	}
	case class GoogleCloudIntegrationsV1alphaAssertion(
	  /** The type of assertion to perform. */
		assertionStrategy: Option[Schema.GoogleCloudIntegrationsV1alphaAssertion.AssertionStrategyEnum] = None,
	  /** Optional. Standard filter expression for ASSERT_CONDITION to succeed */
		condition: Option[String] = None,
	  /** Optional. Key-value pair for ASSERT_EQUALS, ASSERT_NOT_EQUALS, ASSERT_CONTAINS to succeed */
		parameter: Option[Schema.GoogleCloudIntegrationsV1alphaEventParameter] = None,
	  /** Number of times given task should be retried in case of ASSERT_FAILED_EXECUTION */
		retryCount: Option[Int] = None
	)
	
	case class GoogleCloudConnectorsV1EventingRuntimeData(
	  /** Output only. Events listener endpoint. The value will populated after provisioning the events listener. */
		eventsListenerEndpoint: Option[String] = None,
	  /** Output only. Current status of eventing. */
		status: Option[Schema.GoogleCloudConnectorsV1EventingStatus] = None,
	  /** Output only. Webhook data. */
		webhookData: Option[Schema.GoogleCloudConnectorsV1EventingRuntimeDataWebhookData] = None,
	  /** Output only. Webhook subscriptions. */
		webhookSubscriptions: Option[Schema.GoogleCloudConnectorsV1EventingRuntimeDataWebhookSubscriptions] = None,
	  /** Output only. Events listener PSC Service attachment. The value will be populated after provisioning the events listener with private connectivity enabled. */
		eventsListenerPscSa: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoNodeIdentifier {
		enum ElementTypeEnum extends Enum[ElementTypeEnum] { case UNKNOWN_TYPE, TASK_CONFIG, TRIGGER_CONFIG }
	}
	case class EnterpriseCrmEventbusProtoNodeIdentifier(
	  /** Configuration of the edge. */
		elementIdentifier: Option[String] = None,
	  /** Destination node where the edge ends. It can only be a task config. */
		elementType: Option[Schema.EnterpriseCrmEventbusProtoNodeIdentifier.ElementTypeEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaAuthConfig {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case AUTH_CONFIG_VISIBILITY_UNSPECIFIED, PRIVATE, CLIENT_VISIBLE }
		enum CredentialTypeEnum extends Enum[CredentialTypeEnum] { case CREDENTIAL_TYPE_UNSPECIFIED, USERNAME_AND_PASSWORD, API_KEY, OAUTH2_AUTHORIZATION_CODE, OAUTH2_IMPLICIT, OAUTH2_CLIENT_CREDENTIALS, OAUTH2_RESOURCE_OWNER_CREDENTIALS, JWT, AUTH_TOKEN, SERVICE_ACCOUNT, CLIENT_CERTIFICATE_ONLY, OIDC_TOKEN }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, VALID, INVALID, SOFT_DELETED, EXPIRED, UNAUTHORIZED, UNSUPPORTED }
	}
	case class GoogleCloudIntegrationsV1alphaAuthConfig(
	  /** Raw auth credentials. */
		decryptedCredential: Option[Schema.GoogleCloudIntegrationsV1alphaCredential] = None,
	  /** Resource name of the auth config. For more information, see Manage authentication profiles. projects/{project}/locations/{location}/authConfigs/{authConfig}. */
		name: Option[String] = None,
	  /** User can define the time to receive notification after which the auth config becomes invalid. Support up to 30 days. Support granularity in hours. */
		expiryNotificationDuration: Option[List[String]] = None,
	  /** The time until the auth config is valid. Empty or max value is considered the auth config won't expire. */
		validTime: Option[String] = None,
	  /** The last modifier's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		lastModifierEmail: Option[String] = None,
	  /** Output only. The timestamp when the auth config is modified. */
		updateTime: Option[String] = None,
	  /** The visibility of the auth config. */
		visibility: Option[Schema.GoogleCloudIntegrationsV1alphaAuthConfig.VisibilityEnum] = None,
	  /** The reason / details of the current status. */
		reason: Option[String] = None,
	  /** Credential type of the encrypted credential. */
		credentialType: Option[Schema.GoogleCloudIntegrationsV1alphaAuthConfig.CredentialTypeEnum] = None,
	  /** User provided expiry time to override. For the example of Salesforce, username/password credentials can be valid for 6 months depending on the instance settings. */
		overrideValidTime: Option[String] = None,
	  /** Certificate id for client certificate */
		certificateId: Option[String] = None,
	  /** Auth credential encrypted by Cloud KMS. Can be decrypted as Credential with proper KMS key. */
		encryptedCredential: Option[String] = None,
	  /** The creator's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		creatorEmail: Option[String] = None,
	  /** A description of the auth config. */
		description: Option[String] = None,
	  /** Required. The name of the auth config. */
		displayName: Option[String] = None,
	  /** Output only. The timestamp when the auth config is created. */
		createTime: Option[String] = None,
	  /** The status of the auth config. */
		state: Option[Schema.GoogleCloudIntegrationsV1alphaAuthConfig.StateEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSwitchEncryptionRequest(
	  /** Required. REQUIRED: Cloud KMS config for AuthModule to encrypt/decrypt credentials. */
		cloudKmsConfig: Option[Schema.GoogleCloudIntegrationsV1alphaCloudKmsConfig] = None
	)
	
	case class EnterpriseCrmEventbusProtoCustomSuspensionRequest(
	  /** Request to fire an event containing the SuspensionInfo message. */
		postToQueueWithTriggerIdRequest: Option[Schema.GoogleInternalCloudCrmEventbusV3PostToQueueWithTriggerIdRequest] = None,
	  /** In the fired event, set the SuspensionInfo message as the value for this key. */
		suspensionInfoEventParameterKey: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaBooleanParameterArray(
	  /** Boolean array. */
		booleanValues: Option[List[Boolean]] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoProtoParameterArray(
		protoValues: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaReplayExecutionResponse(
	  /** OUTPUT parameters in format of Map. Where Key is the name of the parameter. The parameters would only be present in case of synchrounous execution. Note: Name of the system generated parameters are wrapped by backtick(`) to distinguish them from the user defined parameters. */
		outputParameters: Option[Map[String, JsValue]] = None,
	  /** The execution id which is replayed. */
		replayedExecutionId: Option[String] = None,
	  /** Next ID: 4 The id of the execution corresponding to this run of the integration. */
		executionId: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleDoubleRange(
	  /** The inclusive minimum of the acceptable range. */
		min: Option[BigDecimal] = None,
	  /** The inclusive maximum of the acceptable range. */
		max: Option[BigDecimal] = None
	)
	
	case class EnterpriseCrmEventbusProtoConditionalFailurePolicies(
	  /** The default failure policy to be applied if no conditional failure policy matches */
		defaultFailurePolicy: Option[Schema.EnterpriseCrmEventbusProtoFailurePolicy] = None,
	  /** The list of failure policies that will be applied to the task in order. */
		failurePolicies: Option[List[Schema.EnterpriseCrmEventbusProtoFailurePolicy]] = None
	)
	
	object EnterpriseCrmEventbusStatsDimensions {
		enum RetryAttemptEnum extends Enum[RetryAttemptEnum] { case UNSPECIFIED, FINAL, RETRYABLE, CANCELED }
		enum EnumFilterTypeEnum extends Enum[EnumFilterTypeEnum] { case DEFAULT_INCLUSIVE, EXCLUSIVE }
	}
	case class EnterpriseCrmEventbusStatsDimensions(
		workflowName: Option[String] = None,
		errorEnumString: Option[String] = None,
		retryAttempt: Option[Schema.EnterpriseCrmEventbusStatsDimensions.RetryAttemptEnum] = None,
		taskNumber: Option[String] = None,
		taskName: Option[String] = None,
		clientId: Option[String] = None,
		workflowId: Option[String] = None,
	  /** Whether to include or exclude the enums matching the regex. */
		enumFilterType: Option[Schema.EnterpriseCrmEventbusStatsDimensions.EnumFilterTypeEnum] = None,
	  /** Stats have been or will be aggregated on set fields for any semantically-meaningful combination. */
		triggerId: Option[String] = None,
		warningEnumString: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaIntegrationParameter {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case INTEGRATION_PARAMETER_DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, BOOLEAN_ARRAY, JSON_VALUE, PROTO_VALUE, PROTO_ARRAY, NON_SERIALIZABLE_OBJECT, PROTO_ENUM, SERIALIZED_OBJECT_VALUE, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY }
		enum InputOutputTypeEnum extends Enum[InputOutputTypeEnum] { case IN_OUT_TYPE_UNSPECIFIED, IN, OUT, IN_OUT }
	}
	case class GoogleCloudIntegrationsV1alphaIntegrationParameter(
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given fired event. These parameters must be predefined in the integration definition. */
		key: Option[String] = None,
	  /** The identifier of the node (TaskConfig/TriggerConfig) this parameter was produced by, if it is a transient param or a copy of an input param. */
		producer: Option[String] = None,
	  /** Optional. Description of the parameter. */
		description: Option[String] = None,
	  /** Type of the parameter. */
		dataType: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationParameter.DataTypeEnum] = None,
	  /** This schema will be used to validate runtime JSON-typed values of this parameter. */
		jsonSchema: Option[String] = None,
	  /** Default values for the defined keys. Each value can either be string, int, double or any proto message or a serialized object. */
		defaultValue: Option[Schema.GoogleCloudIntegrationsV1alphaValueType] = None,
	  /** The name (without prefix) to be displayed in the UI for this parameter. E.g. if the key is "foo.bar.myName", then the name would be "myName". */
		displayName: Option[String] = None,
	  /** Indicates whether this variable contains large data and need to be uploaded to Cloud Storage. */
		containsLargeData: Option[Boolean] = None,
	  /** True if this parameter should be masked in the logs */
		masked: Option[Boolean] = None,
	  /** Searchable in the execution log or not. */
		searchable: Option[Boolean] = None,
	  /** Whether this parameter is a transient parameter. */
		isTransient: Option[Boolean] = None,
	  /** Specifies the input/output type for the parameter. */
		inputOutputType: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationParameter.InputOutputTypeEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoTaskUiConfig(
	  /** Configurations of included config modules. */
		taskUiModuleConfigs: Option[List[Schema.EnterpriseCrmEventbusProtoTaskUiModuleConfig]] = None
	)
	
	case class EnterpriseCrmEventbusProtoToken(
		value: Option[String] = None,
		name: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest(
	  /** The function region of CF to be created */
		functionRegion: Option[String] = None,
	  /** Indicates the id of the GCP project that the function will be created in. */
		projectId: Option[String] = None,
	  /** The function name of CF to be created */
		functionName: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoExternalTraffic {
		enum SourceEnum extends Enum[SourceEnum] { case SOURCE_UNSPECIFIED, APIGEE, SECURITY }
	}
	case class EnterpriseCrmEventbusProtoExternalTraffic(
	  /** Location for the user's request. */
		location: Option[String] = None,
	  /** Enqueue the execution request due to quota issue */
		retryRequestForQuota: Option[Boolean] = None,
	  /** User’s GCP project id the traffic is referring to. */
		gcpProjectId: Option[String] = None,
	  /** Indicates the client enables internal IP feature, this is applicable for internal clients only. */
		enableInternalIp: Option[Boolean] = None,
	  /** User’s GCP project number the traffic is referring to. */
		gcpProjectNumber: Option[String] = None,
		source: Option[Schema.EnterpriseCrmEventbusProtoExternalTraffic.SourceEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaRuntimeEntitySchema(
	  /** The above schema, but for an array of the associated entity. */
		arrayFieldSchema: Option[String] = None,
	  /** List of fields in the entity. */
		fieldSchema: Option[String] = None,
	  /** Name of the entity. */
		entity: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoSuccessPolicy {
		enum FinalStateEnum extends Enum[FinalStateEnum] { case UNSPECIFIED, SUCCEEDED, SUSPENDED }
	}
	case class EnterpriseCrmEventbusProtoSuccessPolicy(
	  /** State to which the execution snapshot status will be set if the task succeeds. */
		finalState: Option[Schema.EnterpriseCrmEventbusProtoSuccessPolicy.FinalStateEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaIntegrationAlertConfigThresholdValue(
	  /** Absolute value threshold. */
		absolute: Option[String] = None,
	  /** Percentage threshold. */
		percentage: Option[Int] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoParameterMap {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
		enum KeyTypeEnum extends Enum[KeyTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoParameterMap(
		entries: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterMapEntry]] = None,
		valueType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterMap.ValueTypeEnum] = None,
	  /** Option to specify key value type for all entries of the map. If provided then field types for all entries must conform to this. */
		keyType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterMap.KeyTypeEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaReplaceServiceAccountRequest(
	  /** Required. REQUIRED: Run-as service account to be updated */
		runAsServiceAccount: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaGetClientMetadataResponse(
	  /** Required. Required: The client configuration that was requested */
		properties: Option[Schema.GoogleCloudIntegrationsV1alphaProjectProperties] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigUserPassword(
	  /** Username. */
		username: Option[String] = None,
	  /** Secret version reference containing the password. */
		password: Option[Schema.GoogleCloudConnectorsV1Secret] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigOauth2ClientCredentials(
	  /** Secret version reference containing the client secret. */
		clientSecret: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** The client identifier. */
		clientId: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoTaskExecutionDetailsTaskAttemptStats(
	  /** The start time of the task execution for current attempt. This could be in the future if it's been scheduled. */
		startTime: Option[String] = None,
	  /** The end time of the task execution for current attempt. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSwitchVariableMaskingRequest(
	  /** Required. REQUIRED: True if variable masking feature should be turned on for this region */
		enableVariableMasking: Option[Boolean] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoBooleanParameterArray(
		booleanValues: Option[List[Boolean]] = None
	)
	
	case class EnterpriseCrmEventbusProtoParameterMapEntry(
		key: Option[Schema.EnterpriseCrmEventbusProtoParameterMapField] = None,
		value: Option[Schema.EnterpriseCrmEventbusProtoParameterMapField] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaImportTemplateRequest(
	  /** Optional. Sub Integration which would be created via templates. */
		subIntegrations: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaUseTemplateRequestIntegrationDetails]] = None,
	  /** Required. Name of the integration where template needs to be imported. */
		integration: Option[String] = None,
	  /** Required. The region of the Integration to be created. */
		integrationRegion: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaTemplate {
		enum CategoriesEnum extends Enum[CategoriesEnum] { case CATEGORY_UNSPECIFIED, AI_MACHINE_LEARNING, BUSINESS_INTELLIGENCE, COLLABORATION, CUSTOMER_SERVICE, DATABASES, DEVOPS_IT, CONTENT_AND_FILES, FINANCE_AND_ACCOUNTING, HUMAN_RESOURCES, OPERATIONS, PRODUCT_PROJECT_MANAGEMENT, PRODUCTIVITY, SALES_AND_MARKETING, UNIVERSAL_CONNECTORS, UTILITY, OTHERS }
		enum VisibilityEnum extends Enum[VisibilityEnum] { case VISIBILITY_UNSPECIFIED, PRIVATE, SHARED, PUBLIC }
	}
	case class GoogleCloudIntegrationsV1alphaTemplate(
	  /** Optional. Components being used in the template. This could be used to categorize and filter. */
		components: Option[List[Schema.GoogleCloudIntegrationsV1alphaTemplateComponent]] = None,
	  /** Required. Bundle which is part of the templates. The template entities in the bundle would be converted to an actual entity. */
		templateBundle: Option[Schema.GoogleCloudIntegrationsV1alphaTemplateBundle] = None,
	  /** Optional. Information on how to use the template. This should contain detailed information about usage of the template. */
		usageInfo: Option[String] = None,
	  /** Optional. Time the template was last used. */
		lastUsedTime: Option[String] = None,
	  /** Optional. Number of template usages. */
		usageCount: Option[String] = None,
	  /** Output only. Auto-generated. */
		createTime: Option[String] = None,
	  /** Identifier. Resource name of the template. */
		name: Option[String] = None,
	  /** Required. Tags which are used to identify templates. These tags could be for business use case, connectors etc. */
		tags: Option[List[String]] = None,
	  /** Required. The name of the template */
		displayName: Option[String] = None,
	  /** Optional. Link to template documentation. */
		docLink: Option[String] = None,
	  /** Required. Categories associated with the Template. The categories listed below will be utilized for the Template listing. */
		categories: Option[List[Schema.GoogleCloudIntegrationsV1alphaTemplate.CategoriesEnum]] = None,
	  /** Output only. Auto-generated */
		updateTime: Option[String] = None,
	  /** Optional. Creator of the template. */
		author: Option[String] = None,
	  /** Required. Visibility of the template. */
		visibility: Option[Schema.GoogleCloudIntegrationsV1alphaTemplate.VisibilityEnum] = None,
	  /** Optional. Description of the template. The length should not be more than 255 characters */
		description: Option[String] = None,
	  /** Required. Resource names with which the template is shared for example ProjectNumber/Ord id */
		sharedWith: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaConditionalFailurePolicies(
	  /** The default failure policy to be applied if no conditional failure policy matches. */
		defaultFailurePolicy: Option[Schema.GoogleCloudIntegrationsV1alphaFailurePolicy] = None,
	  /** The list of failure policies that will be applied to the task in order. */
		failurePolicies: Option[List[Schema.GoogleCloudIntegrationsV1alphaFailurePolicy]] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoEventExecutionInfoReplayInfo(
	  /** If this execution has been replayed, then this field contains the execution ids of the replayed executions. */
		replayedExecutionInfoIds: Option[List[String]] = None,
	  /** If this execution is a replay of another execution, then this field contains the original execution id. */
		originalExecutionInfoId: Option[String] = None,
	  /** reason for replay */
		replayReason: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaLiftSuspensionRequest(
	  /** User passed in suspension result and will be used to control workflow execution branching behavior by setting up corresponnding edge condition with suspension result. For example, if you want to lift the suspension, you can pass "Approved", or if you want to reject the suspension and terminate workfloe execution, you can pass "Rejected" and terminate the workflow execution with configuring the edge condition. */
		suspensionResult: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoSerializedObjectParameter(
		objectValue: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSearchTemplatesResponse(
	  /** List of templates retrieved. */
		templates: Option[List[Schema.GoogleCloudIntegrationsV1alphaTemplate]] = None,
	  /** The token used to retrieve the next page results. */
		nextPageToken: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoTriggerCriteria(
	  /** Optional. Implementation class name. The class should implement the “TypedTask” interface. */
		triggerCriteriaTaskImplementationClassName: Option[String] = None,
	  /** Required. Standard filter expression, when true the workflow will be executed. If there's no trigger_criteria_task_implementation_class_name specified, the condition will be validated directly. */
		condition: Option[String] = None,
	  /** Optional. To be used in TaskConfig for the implementation class. */
		parameters: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None
	)
	
	object GoogleCloudIntegrationsV1alphaUploadTestCaseRequest {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case FILE_FORMAT_UNSPECIFIED, JSON, YAML }
	}
	case class GoogleCloudIntegrationsV1alphaUploadTestCaseRequest(
	  /** File format for upload request. */
		fileFormat: Option[Schema.GoogleCloudIntegrationsV1alphaUploadTestCaseRequest.FileFormatEnum] = None,
	  /** The textproto of the test case. */
		content: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoCoordinate(
		y: Option[Int] = None,
		x: Option[Int] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListCertificatesResponse(
	  /** The token used to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of Certificates retrieved. */
		certificates: Option[List[Schema.GoogleCloudIntegrationsV1alphaCertificate]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse(
	  /** All regions where Connector Platform is provisioned. */
		regions: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTemplateBundle(
	  /** Required. Main integration templates of the template bundle. */
		integrationVersionTemplate: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersionTemplate] = None,
	  /** Optional. Sub integration templates which would be added along with main integration. */
		subIntegrationVersionTemplates: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersionTemplate]] = None
	)
	
	object GoogleCloudConnectorsV1BillingConfig {
		enum BillingCategoryEnum extends Enum[BillingCategoryEnum] { case BILLING_CATEGORY_UNSPECIFIED, GCP_AND_TECHNICAL_CONNECTOR, NON_GCP_CONNECTOR }
	}
	case class GoogleCloudConnectorsV1BillingConfig(
	  /** Output only. Billing category for the connector. */
		billingCategory: Option[Schema.GoogleCloudConnectorsV1BillingConfig.BillingCategoryEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoTeardownTaskConfig(
	  /** Required. Unique identifier of the teardown task within this Config. We use this field as the identifier to find next teardown tasks. */
		name: Option[String] = None,
		properties: Option[Schema.EnterpriseCrmEventbusProtoEventBusProperties] = None,
	  /** The parameters the user can pass to this task. */
		parameters: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None,
	  /** Required. Implementation class name. */
		teardownTaskImplementationClassName: Option[String] = None,
		nextTeardownTask: Option[Schema.EnterpriseCrmEventbusProtoNextTeardownTask] = None,
	  /** The creator's email address. */
		creatorEmail: Option[String] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoIntParameterArray(
		intValues: Option[List[String]] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoDoubleParameterArray(
		doubleValues: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaIntegration(
	  /** Optional. */
		description: Option[String] = None,
	  /** Required. Output only. Auto-generated. */
		createTime: Option[String] = None,
	  /** Required. The resource name of the integration. */
		name: Option[String] = None,
	  /** Required. The last modifier of this integration */
		lastModifierEmail: Option[String] = None,
	  /** Output only. Auto-generated. */
		updateTime: Option[String] = None,
	  /** Output only. The creator's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		creatorEmail: Option[String] = None,
	  /** Required. If any integration version is published. */
		active: Option[Boolean] = None
	)
	
	object GoogleCloudConnectorsV1ConnectorVersionInfraConfig {
		enum DeploymentModelEnum extends Enum[DeploymentModelEnum] { case DEPLOYMENT_MODEL_UNSPECIFIED, GKE_MST, CLOUD_RUN_MST }
		enum DeploymentModelMigrationStateEnum extends Enum[DeploymentModelMigrationStateEnum] { case DEPLOYMENT_MODEL_MIGRATION_STATE_UNSPECIFIED, IN_PROGRESS, COMPLETED, ROLLEDBACK, ROLLBACK_IN_PROGRESS }
		enum TlsMigrationStateEnum extends Enum[TlsMigrationStateEnum] { case TLS_MIGRATION_STATE_UNSPECIFIED, TLS_MIGRATION_NOT_STARTED, TLS_MIGRATION_COMPLETED }
	}
	case class GoogleCloudConnectorsV1ConnectorVersionInfraConfig(
	  /** Output only. Max QPS supported for internal requests originating from Connd. */
		internalclientRatelimitThreshold: Option[String] = None,
	  /** Optional. Indicates whether connector is deployed on GKE/CloudRun */
		deploymentModel: Option[Schema.GoogleCloudConnectorsV1ConnectorVersionInfraConfig.DeploymentModelEnum] = None,
	  /** Output only. The name of shared connector deployment. */
		sharedDeployment: Option[String] = None,
	  /** Output only. Max instance request concurrency. */
		maxInstanceRequestConcurrency: Option[Int] = None,
	  /** Output only. The window used for ratelimiting runtime requests to connections. */
		connectionRatelimitWindowSeconds: Option[String] = None,
	  /** Output only. Status of the deployment model migration. */
		deploymentModelMigrationState: Option[Schema.GoogleCloudConnectorsV1ConnectorVersionInfraConfig.DeploymentModelMigrationStateEnum] = None,
	  /** Output only. Status of the TLS migration. */
		tlsMigrationState: Option[Schema.GoogleCloudConnectorsV1ConnectorVersionInfraConfig.TlsMigrationStateEnum] = None,
	  /** Output only. System resource requests. */
		resourceRequests: Option[Schema.GoogleCloudConnectorsV1ResourceRequests] = None,
	  /** Output only. System resource limits. */
		resourceLimits: Option[Schema.GoogleCloudConnectorsV1ResourceLimits] = None,
	  /** Output only. HPA autoscaling config. */
		hpaConfig: Option[Schema.GoogleCloudConnectorsV1HPAConfig] = None,
	  /** Output only. Max QPS supported by the connector version before throttling of requests. */
		ratelimitThreshold: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDoubleParameterArray(
	  /** Double number array. */
		doubleValues: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest(
	  /** Optional. Input parameters used by integration execution. */
		inputParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameterEntries: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** The time that the integration should be executed. If the time is less or equal to the current time, the integration is executed immediately. */
		scheduleTime: Option[String] = None,
	  /** This is used to de-dup incoming request: if the duplicate request was detected, the response from the previous execution is returned. */
		requestId: Option[String] = None,
	  /** Optional. This is a unique id provided by the method caller. If provided this will be used as the execution_id when a new execution info is created. This is a string representation of a UUID. Must have no more than 36 characters and contain only alphanumeric characters and hyphens. */
		userGeneratedExecutionId: Option[String] = None,
	  /** Required. Matched against all {@link TriggerConfig}s across all integrations. i.e. TriggerConfig.trigger_id.equals(trigger_id) */
		triggerId: Option[String] = None,
	  /** Passed in as parameters to each integration execution. */
		parameters: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None
	)
	
	object GoogleCloudIntegrationsV1alphaTemplateComponent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TRIGGER, TASK, CONNECTOR }
	}
	case class GoogleCloudIntegrationsV1alphaTemplateComponent(
	  /** Optional. Type of the component. */
		`type`: Option[Schema.GoogleCloudIntegrationsV1alphaTemplateComponent.TypeEnum] = None,
	  /** Optional. Name of the component. */
		name: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaAssertionResult {
		enum StatusEnum extends Enum[StatusEnum] { case ASSERTION_STATUS_UNSPECIFIED, SUCCEEDED, FAILED }
	}
	case class GoogleCloudIntegrationsV1alphaAssertionResult(
	  /** Details of the assertion failure */
		failureMessage: Option[String] = None,
	  /** Task name of task where the assertion was run. */
		taskName: Option[String] = None,
	  /** Task number of task where the assertion was run. */
		taskNumber: Option[String] = None,
	  /** Assertion that was run. */
		assertion: Option[Schema.GoogleCloudIntegrationsV1alphaAssertion] = None,
	  /** Status of assertion to signify if the assertion succeeded or failed */
		status: Option[Schema.GoogleCloudIntegrationsV1alphaAssertionResult.StatusEnum] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoParameterValueType(
		stringValue: Option[String] = None,
		stringArray: Option[Schema.EnterpriseCrmFrontendsEventbusProtoStringParameterArray] = None,
		intValue: Option[String] = None,
		protoArray: Option[Schema.EnterpriseCrmFrontendsEventbusProtoProtoParameterArray] = None,
		booleanValue: Option[Boolean] = None,
		intArray: Option[Schema.EnterpriseCrmFrontendsEventbusProtoIntParameterArray] = None,
		jsonValue: Option[String] = None,
		booleanArray: Option[Schema.EnterpriseCrmFrontendsEventbusProtoBooleanParameterArray] = None,
		doubleArray: Option[Schema.EnterpriseCrmFrontendsEventbusProtoDoubleParameterArray] = None,
		serializedObjectValue: Option[Schema.EnterpriseCrmFrontendsEventbusProtoSerializedObjectParameter] = None,
		doubleValue: Option[BigDecimal] = None,
		protoValue: Option[Map[String, JsValue]] = None
	)
	
	object EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList {
		enum FilterTypeEnum extends Enum[FilterTypeEnum] { case DEFAULT_INCLUSIVE, EXCLUSIVE }
	}
	case class EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList(
		filterType: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList.FilterTypeEnum] = None,
		enumStrings: Option[List[String]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaMockConfig {
		enum MockStrategyEnum extends Enum[MockStrategyEnum] { case MOCK_STRATEGY_UNSPECIFIED, NO_MOCK_STRATEGY, SPECIFIC_MOCK_STRATEGY, FAILURE_MOCK_STRATEGY, SKIP_MOCK_STRATEGY }
	}
	case class GoogleCloudIntegrationsV1alphaMockConfig(
	  /** Optional. List of key-value pairs for specific mock strategy */
		parameters: Option[List[Schema.GoogleCloudIntegrationsV1alphaEventParameter]] = None,
	  /** Optional. Number of times the given task should fail for failure mock strategy */
		failedExecutions: Option[String] = None,
	  /** Mockstrategy defines how the particular task should be mocked during test execution */
		mockStrategy: Option[Schema.GoogleCloudIntegrationsV1alphaMockConfig.MockStrategyEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectResponse(
	  /** The created AppsScriptProject ID. */
		projectId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaNextTask(
	  /** User-provided label that is attached to this edge in the UI. */
		displayName: Option[String] = None,
	  /** Standard filter expression for this task to become an eligible next task. */
		condition: Option[String] = None,
	  /** ID of the next task. */
		taskConfigId: Option[String] = None,
	  /** Task number of the next task. */
		taskId: Option[String] = None,
	  /** User-provided description intended to give additional business context about the task. */
		description: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaShareTemplateRequest(
	  /** Optional. Project name resources to share the template. The project names is expected in resource format Ex: projects/{project-number} or organization/{org-id} */
		resourceNames: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaGenerateTokenResponse(
	  /** The message that notifies the user if the request succeeded or not. */
		message: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoIntParameterArray(
		intValues: Option[List[String]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaSuccessPolicy {
		enum FinalStateEnum extends Enum[FinalStateEnum] { case FINAL_STATE_UNSPECIFIED, SUCCEEDED, SUSPENDED }
	}
	case class GoogleCloudIntegrationsV1alphaSuccessPolicy(
	  /** State to which the execution snapshot status will be set if the task succeeds. */
		finalState: Option[Schema.GoogleCloudIntegrationsV1alphaSuccessPolicy.FinalStateEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaAccessToken(
	  /** The approximate time until the refresh token retrieved is valid. */
		refreshTokenExpireTime: Option[String] = None,
	  /** Only support "bearer" token in v1 as bearer token is the predominant type used with OAuth 2.0. */
		tokenType: Option[String] = None,
	  /** If the access token will expire, use the refresh token to obtain another access token. */
		refreshToken: Option[String] = None,
	  /** The access token encapsulating the security identity of a process or thread. */
		accessToken: Option[String] = None,
	  /** Required. The approximate time until the access token retrieved is valid. */
		accessTokenExpireTime: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoProtoParameterArray(
		protoValues: Option[List[Map[String, JsValue]]] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoTriggerConfigVariables(
	  /** Optional. List of variable names. */
		names: Option[List[String]] = None
	)
	
	object GoogleInternalCloudCrmEventbusV3PostToQueueWithTriggerIdRequest {
		enum PriorityEnum extends Enum[PriorityEnum] { case UNSPCIFIED, SHEDDABLE, SHEDDABLE_PLUS, CRITICAL, CRITICAL_PLUS }
	}
	case class GoogleInternalCloudCrmEventbusV3PostToQueueWithTriggerIdRequest(
	  /** Optional. Sets test mode in {@link enterprise/crm/eventbus/event_message.proto}. */
		testMode: Option[Boolean] = None,
	  /** This field is only required when using Admin Access. The resource name of target, or the parent resource name. For example: "projects/&#42;/locations/&#42;/integrations/&#42;" */
		resourceName: Option[String] = None,
	  /** The request priority this request should be processed at. For internal users: */
		priority: Option[Schema.GoogleInternalCloudCrmEventbusV3PostToQueueWithTriggerIdRequest.PriorityEnum] = None,
	  /** Optional. Time in milliseconds since epoch when the given event would be scheduled. */
		scheduledTime: Option[String] = None,
	  /** Matched against all {@link TriggerConfig}s across all workflows. i.e. TriggerConfig.trigger_id.equals(trigger_id) Required. */
		triggerId: Option[String] = None,
	  /** Optional. This is used to de-dup incoming request: if the duplicate request was detected, the response from the previous execution is returned. Must have no more than 36 characters and contain only alphanumeric characters and hyphens. */
		requestId: Option[String] = None,
	  /** Optional. This is a field to see the quota retry count for integration execution */
		quotaRetryCount: Option[Int] = None,
	  /** Passed in as parameters to each workflow execution. Optional. */
		parameters: Option[Schema.EnterpriseCrmEventbusProtoEventParameters] = None,
	  /** Optional. Flag to determine whether clients would suppress a warning when no ACTIVE workflows are not found. If this flag is set to be true, an error will not be thrown if the requested trigger_id or client_id is not found in any ACTIVE workflow. Otherwise, the error is always thrown. The flag is set to be false by default. */
		ignoreErrorIfNoActiveWorkflow: Option[Boolean] = None,
	  /** Optional. If provided, the workflow_name is used to filter all the matched workflows having same trigger_id+client_id. A combination of trigger_id, client_id and workflow_name identifies a unique workflow. */
		workflowName: Option[String] = None,
	  /** Optional. If the client id is provided, then the combination of trigger id and client id is matched across all the workflows. If the client id is not provided, then workflows with matching trigger id are executed for each client id in the {@link TriggerConfig}. For Api Trigger, the client id is required and will be validated against the allowed clients. */
		clientId: Option[String] = None,
	  /** This is a unique id provided by the method caller. If provided this will be used as the execution_id when a new execution info is created. This is a string representation of a UUID. Must have no more than 36 characters and contain only alphanumeric characters and hyphens. */
		userGeneratedExecutionId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSuspensionApprovalExpiration(
	  /** Output only. Time after which the suspension expires, if no action taken. */
		expireTime: Option[String] = None,
	  /** Time after the previous suspension action reminder, if any, is sent using the selected notification option, for a suspension which is still PENDING_UNSPECIFIED. */
		remindTime: Option[String] = None,
	  /** Whether the suspension will be REJECTED or LIFTED upon expiration. REJECTED is the default behavior. */
		liftWhenExpired: Option[Boolean] = None
	)
	
	case class GoogleCloudConnectorsV1HPAConfig(
	  /** Output only. Percent CPU utilization where HPA triggers autoscaling. */
		cpuUtilizationThreshold: Option[String] = None,
	  /** Output only. Percent Memory utilization where HPA triggers autoscaling. */
		memoryUtilizationThreshold: Option[String] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoParamSpecEntry {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoParamSpecEntry(
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given task. These parameters must be predefined in the workflow definition. */
		key: Option[String] = None,
	  /** If the data_type is JSON_VALUE, then this will define its schema. */
		jsonSchema: Option[String] = None,
	  /** If set, the user must provide an input value for this parameter. */
		required: Option[Boolean] = None,
	  /** If it is a collection of objects, this would be the FCQN of every individual element in the collection. If this is "java.lang.Object", the parameter is a collection of any type. */
		collectionElementClassName: Option[String] = None,
		isOutput: Option[Boolean] = None,
	  /** Default values for the defined keys. Each value can either be string, int, double or any proto message or a serialized object. */
		defaultValue: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterValueType] = None,
	  /** If set, this entry is deprecated, so further use of this parameter should be prohibited. */
		isDeprecated: Option[Boolean] = None,
	  /** Rule used to validate inputs (individual values and collection elements) for this parameter. */
		validationRule: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryValidationRule] = None,
	  /** Populated if this represents a proto or proto array. */
		protoDef: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryProtoDefinition] = None,
	  /** The FQCN of the Java object this represents. A string, for example, would be "java.lang.String". If this is "java.lang.Object", the parameter can be of any type. */
		className: Option[String] = None,
	  /** The data type of the parameter. */
		dataType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParamSpecEntry.DataTypeEnum] = None,
	  /** Optional fields, such as help text and other useful info. */
		config: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryConfig] = None
	)
	
	case class EnterpriseCrmEventbusProtoParamSpecEntryValidationRule(
		doubleRange: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleDoubleRange] = None,
		stringRegex: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleStringRegex] = None,
		intRange: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleIntRange] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoWorkflowParameters(
	  /** Parameters are a part of Event and can be used to communiticate between different tasks that are part of the same workflow execution. */
		parameters: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoWorkflowParameterEntry]] = None
	)
	
	case class EnterpriseCrmEventbusProtoAddress(
		name: Option[String] = None,
		tokens: Option[List[Schema.EnterpriseCrmEventbusProtoToken]] = None,
	  /** Required. */
		email: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoBuganizerNotification(
	  /** ID of the buganizer template to use. Optional. */
		templateId: Option[String] = None,
	  /** Whom to assign the new bug. Optional. */
		assigneeEmailAddress: Option[String] = None,
	  /** Title of the issue to be created. Required. */
		title: Option[String] = None,
	  /** ID of the buganizer component within which to create a new issue. Required. */
		componentId: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUseTemplateResponse(
	  /** IntegrationVersion which is created. */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None,
	  /** Sub integration versions which are created. */
		subIntegrationVersions: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = None
	)
	
	case class EnterpriseCrmEventbusProtoBaseValue(
	  /** Start with a reference value to dereference. */
		referenceValue: Option[String] = None,
	  /** Start with a function that does not build on existing values. Eg. CurrentTime, Min, Max, Exists, etc. */
		baseFunction: Option[Schema.EnterpriseCrmEventbusProtoFunction] = None,
	  /** Start with a literal value. */
		literalValue: Option[Schema.EnterpriseCrmEventbusProtoParameterValueType] = None
	)
	
	case class EnterpriseCrmEventbusProtoParameterEntry(
	  /** True if this parameter should be masked in the logs */
		masked: Option[Boolean] = None,
	  /** Values for the defined keys. Each value can either be string, int, double or any proto message. */
		value: Option[Schema.EnterpriseCrmEventbusProtoParameterValueType] = None,
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given fired event. These parameters must be predefined in the integration definition. */
		key: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoSuspensionAuthPermissionsGaiaIdentity(
		emailAddress: Option[String] = None,
		gaiaId: Option[String] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoTriggerConfig {
		enum NextTasksExecutionPolicyEnum extends Enum[NextTasksExecutionPolicyEnum] { case UNSPECIFIED, RUN_ALL_MATCH, RUN_FIRST_MATCH }
		enum TriggerTypeEnum extends Enum[TriggerTypeEnum] { case UNKNOWN, CLOUD_PUBSUB, GOOPS, SFDC_SYNC, CRON, API, MANIFOLD_TRIGGER, DATALAYER_DATA_CHANGE, SFDC_CHANNEL, CLOUD_PUBSUB_EXTERNAL, SFDC_CDC_CHANNEL, SFDC_PLATFORM_EVENTS_CHANNEL, CLOUD_SCHEDULER, INTEGRATION_CONNECTOR_TRIGGER, PRIVATE_TRIGGER }
	}
	case class EnterpriseCrmFrontendsEventbusProtoTriggerConfig(
	  /** Optional. Name of the trigger This is added to identify the type of trigger. This is avoid the logic on triggerId to identify the trigger_type and push the same to monitoring. */
		triggerName: Option[String] = None,
	  /** Optional. If set to true, any upcoming requests for this trigger config will be paused and the executions will be resumed later when the flag is reset. The workflow to which this trigger config belongs has to be in ACTIVE status for the executions to be paused or resumed. */
		pauseWorkflowExecutions: Option[Boolean] = None,
	  /** Optional. Informs the front-end application where to draw this trigger config on the UI. */
		position: Option[Schema.EnterpriseCrmEventbusProtoCoordinate] = None,
	  /** Set of tasks numbers from where the workflow execution is started by this trigger. If this is empty, then workflow is executed with default start tasks. In the list of start tasks, none of two tasks can have direct ancestor-descendant relationships (i.e. in a same workflow execution graph). */
		startTasks: Option[List[Schema.EnterpriseCrmEventbusProtoNextTask]] = None,
	  /** Optional. List of input variables for the api trigger. */
		inputVariables: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTriggerConfigVariables] = None,
	  /** Dictates how next tasks will be executed. */
		nextTasksExecutionPolicy: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTriggerConfig.NextTasksExecutionPolicyEnum] = None,
	  /** Optional. When set, Eventbus will run the task specified in the trigger_criteria and validate the result using the trigger_criteria.condition, and only execute the workflow when result is true. */
		triggerCriteria: Option[Schema.EnterpriseCrmEventbusProtoTriggerCriteria] = None,
	  /** The user created label for a particular trigger. */
		label: Option[String] = None,
	  /** Required. A number to uniquely identify each trigger config within the workflow on UI. */
		triggerNumber: Option[String] = None,
	  /** Optional Error catcher id of the error catch flow which will be executed when execution error happens in the task */
		errorCatcherId: Option[String] = None,
		triggerType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTriggerConfig.TriggerTypeEnum] = None,
		cloudSchedulerConfig: Option[Schema.EnterpriseCrmEventbusProtoCloudSchedulerConfig] = None,
	  /** An alert threshold configuration for the [trigger + client + workflow] tuple. If these values are not specified in the trigger config, default values will be populated by the system. Note that there must be exactly one alert threshold configured per [client + trigger + workflow] when published. */
		alertConfig: Option[List[Schema.EnterpriseCrmEventbusProtoWorkflowAlertConfig]] = None,
	  /** User-provided description intended to give more business context about the task. */
		description: Option[String] = None,
	  /** Required. The list of client ids which are enabled to execute the workflow using this trigger. In other words, these clients have the workflow execution privledges for this trigger. For API trigger, the client id in the incoming request is validated against the list of enabled clients. For non-API triggers, one workflow execution is triggered on behalf of each enabled client. */
		enabledClients: Option[List[String]] = None,
	  /** Configurable properties of the trigger, not to be confused with workflow parameters. E.g. "name" is a property for API triggers and "subscription" is a property for Cloud Pubsub triggers. */
		properties: Option[Map[String, String]] = None,
	  /** The backend trigger ID. */
		triggerId: Option[String] = None,
	  /** Optional. List of output variables for the api trigger. */
		outputVariables: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTriggerConfigVariables] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoTaskEntity {
		enum TaskTypeEnum extends Enum[TaskTypeEnum] { case TASK, ASIS_TEMPLATE, IO_TEMPLATE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoTaskEntity(
	  /** UI configuration for this task Also associated with the METADATA mask. */
		uiConfig: Option[Schema.EnterpriseCrmEventbusProtoTaskUiConfig] = None,
	  /** Deprecated - statistics from the Monarch query. */
		stats: Option[Schema.EnterpriseCrmEventbusStats] = None,
	  /** True if the task has conflict with vpcsc */
		disabledForVpcSc: Option[Boolean] = None,
	  /** Defines the type of the task */
		taskType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoTaskEntity.TaskTypeEnum] = None,
	  /** Metadata inclueds the task name, author and so on. */
		metadata: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata] = None,
	  /** Declarations for inputs/outputs for a TypedTask. This is also associated with the METADATA mask. */
		paramSpecs: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParamSpecsMessage] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUploadTemplateResponse(
	  /** The uploaded Template */
		template: Option[Schema.GoogleCloudIntegrationsV1alphaTemplate] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTriggerConfigVariables(
	  /** Optional. List of variable names. */
		names: Option[List[String]] = None
	)
	
	object EnterpriseCrmEventbusProtoLoopMetadata {
		enum FailureLocationEnum extends Enum[FailureLocationEnum] { case UNKNOWN, SUBWORKFLOW, PARAM_OVERRIDING, PARAM_AGGREGATING, SETTING_ITERATION_ELEMENT, GETTING_LIST_TO_ITERATE, CONDITION_EVALUATION, BUILDING_REQUEST }
	}
	case class EnterpriseCrmEventbusProtoLoopMetadata(
	  /** Indicates where in the loop logic did it error out. */
		failureLocation: Option[Schema.EnterpriseCrmEventbusProtoLoopMetadata.FailureLocationEnum] = None,
	  /** Starting from 1, not 0. */
		currentIterationCount: Option[String] = None,
	  /** Add the error message when loops fail. */
		errorMsg: Option[String] = None,
	  /** Needs to be set by the loop impl class before each iteration. The abstract loop class will append the request and response to it. Eg. The foreach Loop will clean up and set it as the current iteration element at the start of each loop. The post request and response will be appended to the value once they are available. */
		currentIterationDetail: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1LockConfig(
	  /** Indicates whether or not the connection is locked. */
		locked: Option[Boolean] = None,
	  /** Describes why a connection is locked. */
		reason: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSfdcChannel(
	  /** Last sfdc messsage replay id for channel */
		lastReplayId: Option[String] = None,
	  /** The Channel topic defined by salesforce once an channel is opened */
		channelTopic: Option[String] = None,
	  /** The description for this channel */
		description: Option[String] = None,
	  /** Output only. Time when the channel was deleted. Empty if not deleted. */
		deleteTime: Option[String] = None,
	  /** Client level unique name/alias to easily reference a channel. */
		displayName: Option[String] = None,
	  /** Resource name of the SFDC channel projects/{project}/locations/{location}/sfdcInstances/{sfdc_instance}/sfdcChannels/{sfdc_channel}. */
		name: Option[String] = None,
	  /** Output only. Time when the channel was last updated */
		updateTime: Option[String] = None,
	  /** Output only. Time when the channel is created */
		createTime: Option[String] = None,
	  /** Indicated if a channel has any active integrations referencing it. Set to false when the channel is created, and set to true if there is any integration published with the channel configured in it. */
		isActive: Option[Boolean] = None
	)
	
	case class EnterpriseCrmEventbusProtoParameterValueType(
		stringValue: Option[String] = None,
		intValue: Option[String] = None,
		stringArray: Option[Schema.EnterpriseCrmEventbusProtoStringParameterArray] = None,
		doubleValue: Option[BigDecimal] = None,
		intArray: Option[Schema.EnterpriseCrmEventbusProtoIntParameterArray] = None,
		booleanArray: Option[Schema.EnterpriseCrmEventbusProtoBooleanParameterArray] = None,
		booleanValue: Option[Boolean] = None,
		doubleArray: Option[Schema.EnterpriseCrmEventbusProtoDoubleParameterArray] = None,
		protoValue: Option[Map[String, JsValue]] = None,
		protoArray: Option[Schema.EnterpriseCrmEventbusProtoProtoParameterArray] = None,
		serializedObjectValue: Option[Schema.EnterpriseCrmEventbusProtoSerializedObjectParameter] = None
	)
	
	object EnterpriseCrmEventbusProtoTaskMetadata {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED_STATUS, DEFAULT_INACTIVE, ACTIVE }
		enum SystemEnum extends Enum[SystemEnum] { case UNSPECIFIED_SYSTEM, GENERIC, BUGANIZER, SALESFORCE, CLOUD_SQL, PLX, SHEETS, GOOGLE_GROUPS, EMAIL, SPANNER, DATA_BRIDGE }
		enum CategoryEnum extends Enum[CategoryEnum] { case UNSPECIFIED_CATEGORY, CUSTOM, FLOW_CONTROL, DATA_MANIPULATION, SCRIPTING, CONNECTOR, HIDDEN, CLOUD_SYSTEMS, CUSTOM_TASK_TEMPLATE, TASK_RECOMMENDATIONS }
		enum DefaultJsonValidationOptionEnum extends Enum[DefaultJsonValidationOptionEnum] { case UNSPECIFIED_JSON_VALIDATION_OPTION, SKIP, PRE_EXECUTION, POST_EXECUTION, PRE_POST_EXECUTION }
		enum ExternalCategoryEnum extends Enum[ExternalCategoryEnum] { case UNSPECIFIED_EXTERNAL_CATEGORY, CORE, CONNECTORS, EXTERNAL_HTTP, EXTERNAL_INTEGRATION_SERVICES, EXTERNAL_CUSTOMER_ACTIONS, EXTERNAL_FLOW_CONTROL, EXTERNAL_WORKSPACE, EXTERNAL_SECURITY, EXTERNAL_DATABASES, EXTERNAL_ANALYTICS, EXTERNAL_BYOC, EXTERNAL_BYOT, EXTERNAL_ARTIFICIAL_INTELIGENCE, EXTERNAL_DATA_MANIPULATION }
	}
	case class EnterpriseCrmEventbusProtoTaskMetadata(
	  /** A set of tags that pertain to a particular task. This can be used to improve the searchability of tasks with several names ("REST Caller" vs. "Call REST Endpoint") or to help users find tasks based on related words. */
		tags: Option[List[String]] = None,
	  /** Allows author to indicate if the task is ready to use or not. If not set, then it will default to INACTIVE. */
		status: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata.StatusEnum] = None,
	  /** In a few sentences, describe the purpose and usage of the task. */
		description: Option[String] = None,
	  /** External-facing documention for standalone IP in pantheon embedded in the RHP for this task. Non null only if different from external_doc_html */
		standaloneExternalDocHtml: Option[String] = None,
	  /** DEPRECATED: Use external_doc_html. */
		externalDocMarkdown: Option[String] = None,
		system: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata.SystemEnum] = None,
	  /** The new task name to replace the current task if it is deprecated. Otherwise, it is the same as the current task name. */
		activeTaskName: Option[String] = None,
		admins: Option[List[Schema.EnterpriseCrmEventbusProtoTaskMetadataAdmin]] = None,
	  /** The Code Search link to the Task Java file. */
		codeSearchLink: Option[String] = None,
		category: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata.CategoryEnum] = None,
	  /** External-facing documention embedded in the RHP for this task. */
		externalDocHtml: Option[String] = None,
	  /** The deprecation status of the current task. Default value is false; */
		isDeprecated: Option[Boolean] = None,
	  /** URL to gstatic image icon for this task. This icon shows up on the task list panel along with the task name in the Workflow Editor screen. Use the 24p, 2x, gray color icon image format. */
		iconLink: Option[String] = None,
	  /** Contains the initial configuration of the task with default values set. For now, The string should be compatible to an ASCII-proto format. */
		defaultSpec: Option[String] = None,
	  /** URL to the associated G3 Doc for the task if available */
		g3DocLink: Option[String] = None,
	  /** Controls whether JSON workflow parameters are validated against provided schemas before and/or after this task's execution. */
		defaultJsonValidationOption: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata.DefaultJsonValidationOptionEnum] = None,
	  /** The string name to show on the task list on the Workflow editor screen. This should be a very short, one to two words name for the task. (e.g. "Send Mail") */
		descriptiveName: Option[String] = None,
	  /** The actual class name or the annotated name of the task. Task Author should initialize this field with value from the getName() method of the Task class. */
		name: Option[String] = None,
	  /** Sequence with which the task in specific category to be displayed in task discovery panel for external users. */
		externalCategorySequence: Option[Int] = None,
		externalCategory: Option[Schema.EnterpriseCrmEventbusProtoTaskMetadata.ExternalCategoryEnum] = None,
	  /** Doc link for external-facing documentation (separate from g3doc). */
		externalDocLink: Option[String] = None,
	  /** Snippet of markdown documentation to embed in the RHP for this task. */
		docMarkdown: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse(
	  /** Runtime entity schemas. */
		runtimeEntitySchemas: Option[List[Schema.GoogleCloudIntegrationsV1alphaRuntimeEntitySchema]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoEventExecutionDetails {
		enum EventExecutionStateEnum extends Enum[EventExecutionStateEnum] { case UNSPECIFIED, ON_HOLD, IN_PROCESS, SUCCEEDED, FAILED, CANCELED, RETRY_ON_HOLD, SUSPENDED }
	}
	case class EnterpriseCrmFrontendsEventbusProtoEventExecutionDetails(
	  /** Indicates the number of times the execution has restarted from the beginning. */
		eventRetriesFromBeginningCount: Option[Int] = None,
	  /** The execution state of this event. */
		eventExecutionState: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionDetails.EventExecutionStateEnum] = None,
		eventAttemptStats: Option[List[Schema.EnterpriseCrmEventbusProtoEventExecutionDetailsEventAttemptStats]] = None,
	  /** If the execution is manually canceled, this field will contain the reason for cancellation. */
		cancelReason: Option[String] = None,
	  /** The log file path (aka. cns address) for this event. */
		logFilePath: Option[String] = None,
	  /** Used internally and shouldn't be exposed to users. A counter for the cron job to record how many times this event is in in_process state but don't have a lock consecutively/ */
		ryeLockUnheldCount: Option[Int] = None,
	  /** The network address (aka. bns address) that indicates where the event executor is running. */
		networkAddress: Option[String] = None,
	  /** After snapshot migration, this field will no longer be populated, but old execution snapshots will still be accessible. */
		eventExecutionSnapshot: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionSnapshot]] = None,
	  /** Total size of all event_execution_snapshots for an execution */
		eventExecutionSnapshotsSize: Option[String] = None,
	  /** Next scheduled execution time in case the execution status was RETRY_ON_HOLD. */
		nextExecutionTime: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoScatterResponse(
	  /** The execution ids of each Subworkflow fired by this scatter. */
		executionIds: Option[List[String]] = None,
	  /** A list of all the response parameters in the aggregtorMap stored with the remapped key. */
		responseParams: Option[List[Schema.EnterpriseCrmEventbusProtoParameterEntry]] = None,
	  /** If execution is sync, this is true if the execution passed and false if it failed. If the execution is async, this is true if the WF was fired off successfully, and false if it failed to execute. The success or failure of the subworkflows executed are not captured. */
		isSuccessful: Option[Boolean] = None,
	  /** The error message of the failure if applicable. */
		errorMsg: Option[String] = None,
	  /** The element that was scattered for this execution. */
		scatterElement: Option[Schema.EnterpriseCrmEventbusProtoParameterValueType] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaIntegrationConfigParameter(
	  /** Values for the defined keys. Each value can either be string, int, double or any proto message or a serialized object. */
		value: Option[Schema.GoogleCloudIntegrationsV1alphaValueType] = None,
	  /** Optional. Integration Parameter to provide the default value, data type and attributes required for the Integration config variables. */
		parameter: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationParameter] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCloudSchedulerConfig(
	  /** Optional. When the job was deleted from Pantheon UI, error_message will be populated when Get/List integrations */
		errorMessage: Option[String] = None,
	  /** Required. The location where associated cloud scheduler job will be created */
		location: Option[String] = None,
	  /** Required. Service account used by Cloud Scheduler to trigger the integration at scheduled time */
		serviceAccountEmail: Option[String] = None,
	  /** Required. The cron tab of cloud scheduler trigger. */
		cronTab: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse(
	  /** The token used to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of SfdcInstances retrieved. */
		sfdcInstances: Option[List[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]] = None
	)
	
	case class EnterpriseCrmEventbusProtoParamSpecEntryValidationRuleIntRange(
	  /** The inclusive minimum of the acceptable range. */
		min: Option[String] = None,
	  /** The inclusive maximum of the acceptable range. */
		max: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTestIntegrationsResponse(
	  /** Details for the integration that were executed. */
		eventParameters: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** Optional. Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Is true if any execution in the integration failed. False otherwise. */
		executionFailed: Option[Boolean] = None,
	  /** The id of the execution corresponding to this run of integration. */
		executionId: Option[String] = None,
	  /** Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameterEntries: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None
	)
	
	case class EnterpriseCrmEventbusProtoSuspensionExpiration(
	  /** Milliseconds after which the previous suspension action reminder, if any, is sent using the selected notification option, for a suspension which is still PENDING_UNSPECIFIED. */
		remindAfterMs: Option[Int] = None,
	  /** Milliseconds after which the suspension expires, if no action taken. */
		expireAfterMs: Option[Int] = None,
	  /** Whether the suspension will be REJECTED or LIFTED upon expiration. REJECTED is the default behavior. */
		liftWhenExpired: Option[Boolean] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoParameterEntry {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING_VALUE, INT_VALUE, DOUBLE_VALUE, BOOLEAN_VALUE, PROTO_VALUE, SERIALIZED_OBJECT_VALUE, STRING_ARRAY, INT_ARRAY, DOUBLE_ARRAY, PROTO_ARRAY, PROTO_ENUM, BOOLEAN_ARRAY, PROTO_ENUM_ARRAY, BYTES, BYTES_ARRAY, NON_SERIALIZABLE_OBJECT, JSON_VALUE }
	}
	case class EnterpriseCrmFrontendsEventbusProtoParameterEntry(
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given fired event. These parameters must be predefined in the workflow definition. */
		key: Option[String] = None,
	  /** Explicitly getting the type of the parameter. */
		dataType: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry.DataTypeEnum] = None,
	  /** True if this parameter should be masked in the logs */
		masked: Option[Boolean] = None,
	  /** Values for the defined keys. Each value can either be string, int, double or any proto message. */
		value: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterValueType] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigOauth2AuthCodeFlowGoogleManaged(
	  /** Required. Scopes the connection will request when the user performs the auth code flow. */
		scopes: Option[List[String]] = None,
	  /** Optional. Redirect URI to be provided during the auth code exchange. */
		redirectUri: Option[String] = None,
	  /** Optional. Authorization code to be exchanged for access and refresh tokens. */
		authCode: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoWorkflowAlertConfig {
		enum MetricTypeEnum extends Enum[MetricTypeEnum] { case METRIC_TYPE_UNSPECIFIED, EVENT_ERROR_RATE, EVENT_WARNING_RATE, TASK_ERROR_RATE, TASK_WARNING_RATE, TASK_RATE, EVENT_RATE, EVENT_AVERAGE_DURATION, EVENT_PERCENTILE_DURATION, TASK_AVERAGE_DURATION, TASK_PERCENTILE_DURATION }
		enum ThresholdTypeEnum extends Enum[ThresholdTypeEnum] { case UNSPECIFIED_THRESHOLD_TYPE, EXPECTED_MIN, EXPECTED_MAX }
	}
	case class EnterpriseCrmEventbusProtoWorkflowAlertConfig(
		warningEnumList: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList] = None,
	  /** For an EXPECTED_MIN threshold, this aggregation_period must be lesser than 24 hours. */
		aggregationPeriod: Option[String] = None,
	  /** A name to identify this alert. This will be displayed in the alert subject. If set, this name should be unique within the scope of the workflow. */
		alertName: Option[String] = None,
		errorEnumList: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigErrorEnumList] = None,
	  /** The metric value, above or below which the alert should be triggered. */
		thresholdValue: Option[Schema.EnterpriseCrmEventbusProtoBaseAlertConfigThresholdValue] = None,
	  /** Set to false by default. When set to true, the metrics are not aggregated or pushed to Monarch for this workflow alert. */
		alertDisabled: Option[Boolean] = None,
	  /** Should be specified only for &#42;AVERAGE_DURATION and &#42;PERCENTILE_DURATION metrics. This member should be used to specify what duration value the metrics should exceed for the alert to trigger. */
		durationThresholdMs: Option[String] = None,
		metricType: Option[Schema.EnterpriseCrmEventbusProtoWorkflowAlertConfig.MetricTypeEnum] = None,
	  /** The threshold type, whether lower(expected_min) or upper(expected_max), for which this alert is being configured. If value falls below expected_min or exceeds expected_max, an alert will be fired. */
		thresholdType: Option[Schema.EnterpriseCrmEventbusProtoWorkflowAlertConfig.ThresholdTypeEnum] = None,
	  /** Client associated with this alert configuration. */
		clientId: Option[String] = None,
	  /** Link to a playbook for resolving the issue that triggered this alert. */
		playbookUrl: Option[String] = None,
	  /** For either events or tasks, depending on the type of alert, count only final attempts, not retries. */
		onlyFinalAttempt: Option[Boolean] = None,
	  /** For how many contiguous aggregation periods should the expected min or max be violated for the alert to be fired. */
		numAggregationPeriods: Option[Int] = None
	)
	
	case class EnterpriseCrmEventbusProtoSuspensionConfig(
	  /** Optional information to provide recipients of the suspension in addition to the resolution URL, typically containing relevant parameter values from the originating workflow. */
		customMessage: Option[String] = None,
	  /** Identities able to resolve this suspension. */
		whoMayResolve: Option[List[Schema.EnterpriseCrmEventbusProtoSuspensionAuthPermissions]] = None,
	  /** Indicates the next steps when no external actions happen on the suspension. */
		suspensionExpiration: Option[Schema.EnterpriseCrmEventbusProtoSuspensionExpiration] = None,
		notifications: Option[List[Schema.EnterpriseCrmEventbusProtoNotification]] = None
	)
	
	object CrmlogErrorCode {
		enum CommonErrorCodeEnum extends Enum[CommonErrorCodeEnum] { case COMMON_ERROR_CODE_UNSPECIFIED, INVALID_CREDENTIALS, REQUIRED_FIELDS_MISSING, INVALID_FIELDS, BACKEND, GENERAL, INTERNAL, IO_ERROR, NOT_FOUND, EVENT_BUS, ALREADY_EXISTS, CONCORD, CONVERSION, FLUME, PERMISSION, SALES_FORCE, SPANNER, UNIMPLEMENTED, RELTIO, WORKFLOW_NOT_FOUND, QUOTA_THROTTLED, QUOTA_ENQUEUED, INVALID_QUOTA_CONFIGURATION, TASK_NOT_FOUND, EXECUTION_TIMEOUT, INVALID_EVENT_EXECUTION_STATE, INVALID_ATTRIBUTE, MISSING_ATTRIBUTE, CLIENT_UNAUTHORIZED_FOR_WORKFLOW, INVALID_PARAMETER, MISSING_PARAMETER, UNAUTHROIZED_WORKFLOW_EDITOR_ACTION, FAILED_PRECONDITION, INVALID_CLIENT, MISSING_CLIENT, INVALID_WORKFLOW, MISSING_QUOTA_CONFIGURATION, UNHANDLED_TASK_ERROR, SCRIPT_TASK_RUNTIME_ERROR, RPC, INVALID_PROTO, UNHANDLED_EVENTBUS_ERROR, INVALID_TASK_STATE, TYPED_TASK_INVALID_INPUT_OPERATION, TYPED_TASK_INVALID_OUTPUT_OPERATION, VALIDATION_ERROR, RESUME_ERROR, APPS_SCRIPT_EXECUTION_ERROR, INVALID_VECTOR_USER, INFORMATICA, RETRYABLE_TASK_ERROR, INVALID_TENANT, WRONG_TENANT, INFORMATICA_BACKEND_UNAVAILABLE, RPC_PERMISSION_DENIED, SYNC_EVENTBUS_EXECUTION_TIMEOUT, ASYNC_EVENTBUS_EXECUTION_TIMEOUT, NOT_SUPPORTED_DATA_TYPE, UNSANITIZED_USER_INPUT, TRANSFORM_EXPRESSION_EVALUATION_ERROR, HTTP_EXCEPTION, EXECUTION_CANCELLED }
	}
	case class CrmlogErrorCode(
		commonErrorCode: Option[Schema.CrmlogErrorCode.CommonErrorCodeEnum] = None
	)
	
	object EnterpriseCrmEventbusProtoDoubleFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, TO_JSON, TO_STRING, ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, ROUND, FLOOR, CEIL, GREATER_THAN, LESS_THAN, EQUALS, GREATER_THAN_EQUALS, LESS_THAN_EQUALS, MOD }
	}
	case class EnterpriseCrmEventbusProtoDoubleFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoDoubleFunction.FunctionNameEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoBaseAlertConfigThresholdValue(
		percentage: Option[Int] = None,
		absolute: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaIntParameterArray(
	  /** Integer array. */
		intValues: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSfdcInstance(
	  /** The SFDC Org Id. This is defined in salesforce. */
		sfdcOrgId: Option[String] = None,
	  /** Output only. Time when the instance is created */
		createTime: Option[String] = None,
	  /** URL used for API calls after authentication (the login authority is configured within the referenced AuthConfig). */
		serviceAuthority: Option[String] = None,
	  /** User selected unique name/alias to easily reference an instance. */
		displayName: Option[String] = None,
	  /** Output only. Time when the instance was last updated */
		updateTime: Option[String] = None,
	  /** Output only. Time when the instance was deleted. Empty if not deleted. */
		deleteTime: Option[String] = None,
	  /** A description of the sfdc instance. */
		description: Option[String] = None,
	  /** A list of AuthConfigs that can be tried to open the channel to SFDC */
		authConfigId: Option[List[String]] = None,
	  /** Resource name of the SFDC instance projects/{project}/locations/{location}/sfdcInstances/{sfdcInstance}. */
		name: Option[String] = None
	)
	
	object GoogleCloudConnectorsV1Connection {
		enum ConnectorVersionLaunchStageEnum extends Enum[ConnectorVersionLaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, PREVIEW, GA, DEPRECATED, TEST, PRIVATE_PREVIEW }
		enum SubscriptionTypeEnum extends Enum[SubscriptionTypeEnum] { case SUBSCRIPTION_TYPE_UNSPECIFIED, PAY_G, PAID }
		enum EventingEnablementTypeEnum extends Enum[EventingEnablementTypeEnum] { case EVENTING_ENABLEMENT_TYPE_UNSPECIFIED, EVENTING_AND_CONNECTION, ONLY_EVENTING }
	}
	case class GoogleCloudConnectorsV1Connection(
	  /** Output only. Current status of the connection. */
		status: Option[Schema.GoogleCloudConnectorsV1ConnectionStatus] = None,
	  /** Output only. Infra configs supported by Connector Version. */
		connectorVersionInfraConfig: Option[Schema.GoogleCloudConnectorsV1ConnectorVersionInfraConfig] = None,
	  /** Optional. Configuration for configuring the connection with an external system. */
		configVariables: Option[List[Schema.GoogleCloudConnectorsV1ConfigVariable]] = None,
	  /** Optional. Async operations enabled for the connection. If Async Operations is enabled, Connection allows the customers to initiate async long running operations using the actions API. */
		asyncOperationsEnabled: Option[Boolean] = None,
	  /** Output only. Is trusted tester program enabled for the project. */
		isTrustedTester: Option[Boolean] = None,
	  /** Required. Connector version on which the connection is created. The format is: projects/&#42;/locations/&#42;/providers/&#42;/connectors/&#42;/versions/&#42; Only global location is supported for ConnectorVersion resource. */
		connectorVersion: Option[String] = None,
	  /** Output only. GCR location where the runtime image is stored. formatted like: gcr.io/{bucketName}/{imageName} */
		imageLocation: Option[String] = None,
	  /** Optional. Description of the resource. */
		description: Option[String] = None,
	  /** Output only. Flag to mark the version indicating the launch stage. */
		connectorVersionLaunchStage: Option[Schema.GoogleCloudConnectorsV1Connection.ConnectorVersionLaunchStageEnum] = None,
	  /** Optional. Resource labels to represent user-provided metadata. Refer to cloud documentation on labels for more details. https://cloud.google.com/compute/docs/labeling-resources */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Node configuration for the connection. */
		nodeConfig: Option[Schema.GoogleCloudConnectorsV1NodeConfig] = None,
	  /** Output only. Eventing Runtime Data. */
		eventingRuntimeData: Option[Schema.GoogleCloudConnectorsV1EventingRuntimeData] = None,
	  /** Optional. Ssl config of a connection */
		sslConfig: Option[Schema.GoogleCloudConnectorsV1SslConfig] = None,
	  /** Output only. This subscription type enum states the subscription type of the project. */
		subscriptionType: Option[Schema.GoogleCloudConnectorsV1Connection.SubscriptionTypeEnum] = None,
	  /** Output only. The name of the Service Directory service with TLS. */
		tlsServiceDirectory: Option[String] = None,
	  /** Optional. Suspended indicates if a user has suspended a connection or not. */
		suspended: Option[Boolean] = None,
	  /** Output only. The name of the Hostname of the Service Directory service with TLS. */
		host: Option[String] = None,
	  /** Output only. GCR location where the envoy image is stored. formatted like: gcr.io/{bucketName}/{imageName} */
		envoyImageLocation: Option[String] = None,
	  /** Optional. Configuration for establishing the connection's authentication with an external system. */
		authConfig: Option[Schema.GoogleCloudConnectorsV1AuthConfig] = None,
	  /** Output only. Resource name of the Connection. Format: projects/{project}/locations/{location}/connections/{connection} */
		name: Option[String] = None,
	  /** Optional. Eventing config of a connection */
		eventingConfig: Option[Schema.GoogleCloudConnectorsV1EventingConfig] = None,
	  /** Optional. Service account needed for runtime plane to access Google Cloud resources. */
		serviceAccount: Option[String] = None,
	  /** Output only. The name of the Service Directory service name. Used for Private Harpoon to resolve the ILB address. e.g. "projects/cloud-connectors-e2e-testing/locations/us-central1/namespaces/istio-system/services/istio-ingressgateway-connectors" */
		serviceDirectory: Option[String] = None,
	  /** Optional. Configuration of the Connector's destination. Only accepted for Connectors that accepts user defined destination(s). */
		destinationConfigs: Option[List[Schema.GoogleCloudConnectorsV1DestinationConfig]] = None,
	  /** Output only. Billing config for the connection. */
		billingConfig: Option[Schema.GoogleCloudConnectorsV1BillingConfig] = None,
	  /** Optional. Eventing enablement type. Will be nil if eventing is not enabled. */
		eventingEnablementType: Option[Schema.GoogleCloudConnectorsV1Connection.EventingEnablementTypeEnum] = None,
	  /** Optional. Configuration that indicates whether or not the Connection can be edited. */
		lockConfig: Option[Schema.GoogleCloudConnectorsV1LockConfig] = None,
	  /** Output only. Connection revision. This field is only updated when the connection is created or updated by User. */
		connectionRevision: Option[String] = None,
	  /** Optional. Auth override enabled for the connection. If Auth Override is enabled, Connection allows the backend service auth to be overridden in the entities/actions API. */
		authOverrideEnabled: Option[Boolean] = None,
	  /** Output only. Updated time. */
		updateTime: Option[String] = None,
	  /** Optional. Log configuration for the connection. */
		logConfig: Option[Schema.GoogleCloudConnectorsV1LogConfig] = None,
	  /** Output only. Created time. */
		createTime: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoTaskExecutionDetails {
		enum TaskExecutionStateEnum extends Enum[TaskExecutionStateEnum] { case UNSPECIFIED, PENDING_EXECUTION, IN_PROCESS, SUCCEED, FAILED, FATAL, RETRY_ON_HOLD, SKIPPED, CANCELED, PENDING_ROLLBACK, ROLLBACK_IN_PROCESS, ROLLEDBACK, SUSPENDED }
	}
	case class EnterpriseCrmEventbusProtoTaskExecutionDetails(
		taskAttemptStats: Option[List[Schema.EnterpriseCrmEventbusProtoTaskExecutionDetailsTaskAttemptStats]] = None,
		taskExecutionState: Option[Schema.EnterpriseCrmEventbusProtoTaskExecutionDetails.TaskExecutionStateEnum] = None,
	  /** Pointer to the task config it used for execution. */
		taskNumber: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoParamSpecEntryConfig {
		enum InputDisplayOptionEnum extends Enum[InputDisplayOptionEnum] { case DEFAULT, STRING_MULTI_LINE, NUMBER_SLIDER, BOOLEAN_TOGGLE }
		enum ParameterNameOptionEnum extends Enum[ParameterNameOptionEnum] { case DEFAULT_NOT_PARAMETER_NAME, IS_PARAMETER_NAME, KEY_IS_PARAMETER_NAME, VALUE_IS_PARAMETER_NAME }
	}
	case class EnterpriseCrmEventbusProtoParamSpecEntryConfig(
		inputDisplayOption: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryConfig.InputDisplayOptionEnum] = None,
	  /** A user-friendly label for the parameter. */
		label: Option[String] = None,
	  /** A user-friendly label for subSection under which the parameter will be displayed. */
		subSectionLabel: Option[String] = None,
	  /** A short phrase to describe what this parameter contains. */
		descriptivePhrase: Option[String] = None,
	  /** Detailed help text for this parameter containing information not provided elsewhere. For example, instructions on how to migrate from a deprecated parameter. */
		helpText: Option[String] = None,
	  /** Whether this field is hidden in the UI. */
		isHidden: Option[Boolean] = None,
	  /** Placeholder text which will appear in the UI input form for this parameter. */
		uiPlaceholderText: Option[String] = None,
		parameterNameOption: Option[Schema.EnterpriseCrmEventbusProtoParamSpecEntryConfig.ParameterNameOptionEnum] = None,
	  /** Whether the default value is hidden in the UI. */
		hideDefaultValue: Option[Boolean] = None
	)
	
	case class EnterpriseCrmEventbusProtoIntArray(
		values: Option[List[String]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaIntegrationVersion {
		enum DatabasePersistencePolicyEnum extends Enum[DatabasePersistencePolicyEnum] { case DATABASE_PERSISTENCE_POLICY_UNSPECIFIED, DATABASE_PERSISTENCE_DISABLED, DATABASE_PERSISTENCE_ASYNC }
		enum OriginEnum extends Enum[OriginEnum] { case UNSPECIFIED, UI, PIPER_V2, PIPER_V3, APPLICATION_IP_PROVISIONING, TEST_CASE }
		enum StatusEnum extends Enum[StatusEnum] { case UNKNOWN, DRAFT, ACTIVE, ARCHIVED, SNAPSHOT }
		enum StateEnum extends Enum[StateEnum] { case INTEGRATION_STATE_UNSPECIFIED, DRAFT, ACTIVE, ARCHIVED, SNAPSHOT }
	}
	case class GoogleCloudIntegrationsV1alphaIntegrationVersion(
	  /** Optional. Trigger configurations. */
		triggerConfigs: Option[List[Schema.GoogleCloudIntegrationsV1alphaTriggerConfig]] = None,
	  /** Optional. Flag to disable database persistence for execution data, including event execution info, execution export info, execution metadata index and execution param index. */
		databasePersistencePolicy: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion.DatabasePersistencePolicyEnum] = None,
	  /** Output only. Auto-generated. */
		updateTime: Option[String] = None,
	  /** Optional. Parameters that are expected to be passed to the integration when an event is triggered. This consists of all the parameters that are expected in the integration execution. This gives the user the ability to provide default values, add information like PII and also provide data types of each parameter. */
		integrationParametersInternal: Option[Schema.EnterpriseCrmFrontendsEventbusProtoWorkflowParameters] = None,
	  /** Optional. Parameters that are expected to be passed to the integration when an event is triggered. This consists of all the parameters that are expected in the integration execution. This gives the user the ability to provide default values, add information like PII and also provide data types of each parameter. */
		integrationParameters: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationParameter]] = None,
	  /** Optional. The last modifier's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		lastModifierEmail: Option[String] = None,
	  /** Optional. Task configuration for the integration. It's optional, but the integration doesn't do anything without task_configs. */
		taskConfigs: Option[List[Schema.GoogleCloudIntegrationsV1alphaTaskConfig]] = None,
	  /** Optional. An increasing sequence that is set when a new snapshot is created. The last created snapshot can be identified by [workflow_name, org_id latest(snapshot_number)]. However, last created snapshot need not be same as the HEAD. So users should always use "HEAD" tag to identify the head. */
		snapshotNumber: Option[String] = None,
	  /** Optional. The origin that indicates where this integration is coming from. */
		origin: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion.OriginEnum] = None,
	  /** Optional. Error Catch Task configuration for the integration. It's optional. */
		errorCatcherConfigs: Option[List[Schema.GoogleCloudIntegrationsV1alphaErrorCatcherConfig]] = None,
	  /** Optional. Task configuration for the integration. It's optional, but the integration doesn't do anything without task_configs. */
		taskConfigsInternal: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoTaskConfig]] = None,
	  /** Optional. The id of the template which was used to create this integration_version. */
		parentTemplateId: Option[String] = None,
	  /** Output only. Generated by eventbus. User should not set it as an input. */
		status: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion.StatusEnum] = None,
	  /** Optional. Config Parameters that are expected to be passed to the integration when an integration is published. This consists of all the parameters that are expected to provide configuration in the integration execution. This gives the user the ability to provide default values, value, add information like connection url, project based configuration value and also provide data types of each parameter. */
		integrationConfigParameters: Option[List[Schema.GoogleCloudIntegrationsV1alphaIntegrationConfigParameter]] = None,
	  /** Optional. The edit lock holder's email address. Generated based on the End User Credentials/LOAS role of the user making the call. */
		lockHolder: Option[String] = None,
	  /** Output only. Auto-generated primary key. */
		name: Option[String] = None,
	  /** Optional. The integration description. */
		description: Option[String] = None,
	  /** Optional. Contains a graph of tasks that will be executed before putting the event in a terminal state (SUCCEEDED/FAILED/FATAL), regardless of success or failure, similar to "finally" in code. */
		teardown: Option[Schema.EnterpriseCrmEventbusProtoTeardown] = None,
	  /** Optional. Cloud Logging details for the integration version */
		cloudLoggingDetails: Option[Schema.GoogleCloudIntegrationsV1alphaCloudLoggingDetails] = None,
	  /** Optional. Trigger configurations. */
		triggerConfigsInternal: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoTriggerConfig]] = None,
	  /** Output only. Auto-generated. */
		createTime: Option[String] = None,
	  /** Optional. The run-as service account email, if set and auth config is not configured, that will be used to generate auth token to be used in Connector task, Rest caller task and Cloud function task. */
		runAsServiceAccount: Option[String] = None,
	  /** Optional. True if variable masking feature should be turned on for this version */
		enableVariableMasking: Option[Boolean] = None,
	  /** Optional. Optional. The resource name of the template from which the integration is created. */
		createdFromTemplate: Option[String] = None,
	  /** Optional. A user-defined label that annotates an integration version. Typically, this is only set when the integration version is created. */
		userLabel: Option[String] = None,
	  /** Output only. User should not set it as an input. */
		state: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion.StateEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaOAuth2ResourceOwnerCredentials {
		enum RequestTypeEnum extends Enum[RequestTypeEnum] { case REQUEST_TYPE_UNSPECIFIED, REQUEST_BODY, QUERY_PARAMETERS, ENCODED_HEADER }
	}
	case class GoogleCloudIntegrationsV1alphaOAuth2ResourceOwnerCredentials(
	  /** A space-delimited list of requested scope permissions. */
		scope: Option[String] = None,
	  /** Access token fetched from the authorization server. */
		accessToken: Option[Schema.GoogleCloudIntegrationsV1alphaAccessToken] = None,
	  /** Represent how to pass parameters to fetch access token */
		requestType: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2ResourceOwnerCredentials.RequestTypeEnum] = None,
	  /** The client's ID. */
		clientId: Option[String] = None,
	  /** The user's password. */
		password: Option[String] = None,
	  /** Token parameters for the auth request. */
		tokenParams: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap] = None,
	  /** The user's username. */
		username: Option[String] = None,
	  /** The token endpoint is used by the client to obtain an access token by presenting its authorization grant or refresh token. */
		tokenEndpoint: Option[String] = None,
	  /** The client's secret. */
		clientSecret: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListAuthConfigsResponse(
	  /** The list of AuthConfigs retrieved. */
		authConfigs: Option[List[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]] = None,
	  /** The token used to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoEventExecutionSnapshotEventExecutionSnapshotMetadata(
	  /** the task name associated with this snapshot. Could be empty. */
		taskName: Option[String] = None,
	  /** Ancestor task number for the task(it will only be non-empty if the task is under 'private workflow') */
		ancestorTaskNumbers: Option[List[String]] = None,
	  /** the event attempt number this snapshot belongs to. */
		eventAttemptNum: Option[Int] = None,
	  /** Ancestor iteration number for the task(it will only be non-empty if the task is under 'private workflow') */
		ancestorIterationNumbers: Option[List[String]] = None,
	  /** The task number associated with this snapshot. Could be empty. */
		taskNumber: Option[String] = None,
	  /** The direct integration which the event execution snapshots belongs to */
		integrationName: Option[String] = None,
	  /** the task label associated with this snapshot. Could be empty. */
		taskLabel: Option[String] = None,
	  /** the task attempt number this snapshot belongs to. Could be empty. */
		taskAttemptNum: Option[Int] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaClientCertificate(
	  /** The ssl certificate encoded in PEM format. This string must include the begin header and end footer lines. For example, -----BEGIN CERTIFICATE----- MIICTTCCAbagAwIBAgIJAPT0tSKNxan/MA0GCSqGSIb3DQEBCwUAMCoxFzAVBgNV BAoTDkdvb2dsZSBURVNUSU5HMQ8wDQYDVQQDEwZ0ZXN0Q0EwHhcNMTUwMTAxMDAw MDAwWhcNMjUwMTAxMDAwMDAwWjAuMRcwFQYDVQQKEw5Hb29nbGUgVEVTVElORzET MBEGA1UEAwwKam9lQGJhbmFuYTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA vDYFgMgxi5W488d9J7UpCInl0NXmZQpJDEHE4hvkaRlH7pnC71H0DLt0/3zATRP1 JzY2+eqBmbGl4/sgZKYv8UrLnNyQNUTsNx1iZAfPUflf5FwgVsai8BM0pUciq1NB xD429VFcrGZNucvFLh72RuRFIKH8WUpiK/iZNFkWhZ0CAwEAAaN3MHUwDgYDVR0P AQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAMBgNVHRMB Af8EAjAAMBkGA1UdDgQSBBCVgnFBCWgL/iwCqnGrhTPQMBsGA1UdIwQUMBKAEKey Um2o4k2WiEVA0ldQvNYwDQYJKoZIhvcNAQELBQADgYEAYK986R4E3L1v+Q6esBtW JrUwA9UmJRSQr0N5w3o9XzarU37/bkjOP0Fw0k/A6Vv1n3vlciYfBFaBIam1qRHr 5dMsYf4CZS6w50r7hyzqyrwDoyNxkLnd2PdcHT/sym1QmflsjEs7pejtnohO6N2H wQW6M0H7Zt8claGRla4fKkg= -----END CERTIFICATE----- */
		sslCertificate: Option[String] = None,
	  /** 'passphrase' should be left unset if private key is not encrypted. Note that 'passphrase' is not the password for web server, but an extra layer of security to protected private key. */
		passphrase: Option[String] = None,
	  /** The ssl certificate encoded in PEM format. This string must include the begin header and end footer lines. For example, -----BEGIN CERTIFICATE----- MIICTTCCAbagAwIBAgIJAPT0tSKNxan/MA0GCSqGSIb3DQEBCwUAMCoxFzAVBgNV BAoTDkdvb2dsZSBURVNUSU5HMQ8wDQYDVQQDEwZ0ZXN0Q0EwHhcNMTUwMTAxMDAw MDAwWhcNMjUwMTAxMDAwMDAwWjAuMRcwFQYDVQQKEw5Hb29nbGUgVEVTVElORzET MBEGA1UEAwwKam9lQGJhbmFuYTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA vDYFgMgxi5W488d9J7UpCInl0NXmZQpJDEHE4hvkaRlH7pnC71H0DLt0/3zATRP1 JzY2+eqBmbGl4/sgZKYv8UrLnNyQNUTsNx1iZAfPUflf5FwgVsai8BM0pUciq1NB xD429VFcrGZNucvFLh72RuRFIKH8WUpiK/iZNFkWhZ0CAwEAAaN3MHUwDgYDVR0P AQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAMBgNVHRMB Af8EAjAAMBkGA1UdDgQSBBCVgnFBCWgL/iwCqnGrhTPQMBsGA1UdIwQUMBKAEKey Um2o4k2WiEVA0ldQvNYwDQYJKoZIhvcNAQELBQADgYEAYK986R4E3L1v+Q6esBtW JrUwA9UmJRSQr0N5w3o9XzarU37/bkjOP0Fw0k/A6Vv1n3vlciYfBFaBIam1qRHr 5dMsYf4CZS6w50r7hyzqyrwDoyNxkLnd2PdcHT/sym1QmflsjEs7pejtnohO6N2H wQW6M0H7Zt8claGRla4fKkg= -----END CERTIFICATE----- */
		encryptedPrivateKey: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoIntArrayFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET, APPEND, SIZE, SUM, AVG, MAX, MIN, TO_SET, APPEND_ALL, TO_JSON, SET, REMOVE, REMOVE_AT, CONTAINS, FOR_EACH, FILTER }
	}
	case class EnterpriseCrmEventbusProtoIntArrayFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoIntArrayFunction.FunctionNameEnum] = None
	)
	
	object EnterpriseCrmFrontendsEventbusProtoEventExecutionInfo {
		enum PostMethodEnum extends Enum[PostMethodEnum] { case UNSPECIFIED, POST, POST_TO_QUEUE, SCHEDULE, POST_BY_EVENT_CONFIG_ID, POST_WITH_EVENT_DETAILS }
		enum ProductEnum extends Enum[ProductEnum] { case UNSPECIFIED_PRODUCT, IP, APIGEE, SECURITY }
	}
	case class EnterpriseCrmFrontendsEventbusProtoEventExecutionInfo(
	  /** The ways user posts this event. */
		postMethod: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionInfo.PostMethodEnum] = None,
	  /** Auto-generated primary key. */
		eventExecutionInfoId: Option[String] = None,
	  /** Errors, warnings, and informationals associated with the workflow/task. The order in which the errors were added by the workflow/task is maintained. */
		errors: Option[List[Schema.EnterpriseCrmEventbusProtoErrorDetail]] = None,
	  /** Execution trace info to aggregate parent-child executions. */
		executionTraceInfo: Option[Schema.EnterpriseCrmEventbusProtoExecutionTraceInfo] = None,
	  /** Event parameters come out as part of the response. */
		responseParams: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** User-defined label that annotates the executed integration version. */
		integrationVersionUserLabel: Option[String] = None,
	  /** Time interval in seconds to schedule retry of workflow in manifold when workflow is already running */
		workflowRetryBackoffIntervalSeconds: Option[String] = None,
	  /** Which Google product the execution_info belongs to. If not set, the execution_info belongs to Integration Platform by default. */
		product: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionInfo.ProductEnum] = None,
	  /** Event parameters come in as part of the request. */
		requestParams: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** Optional. This is used to de-dup incoming request. */
		requestId: Option[String] = None,
	  /** The execution info about this event. */
		eventExecutionDetails: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionDetails] = None,
	  /** The trigger id of the workflow trigger config. If both trigger_id and client_id is present, the workflow is executed from the start tasks provided by the matching trigger config otherwise it is executed from the default start tasks. */
		triggerId: Option[String] = None,
	  /** Auto-generated. */
		lastModifiedTime: Option[String] = None,
	  /** Name of the workflow. */
		workflowName: Option[String] = None,
	  /** Required. Pointer to the workflow it is executing. */
		workflowId: Option[String] = None,
	  /** Tenant this event is created. Used to reschedule the event to correct tenant. */
		tenant: Option[String] = None,
	  /** Auto-generated. */
		createTime: Option[String] = None,
	  /** Workflow snapshot number. */
		snapshotNumber: Option[String] = None,
	  /** Replay info for the execution */
		replayInfo: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventExecutionInfoReplayInfo] = None,
	  /** Cloud Logging details for execution info */
		cloudLoggingDetails: Option[Schema.EnterpriseCrmEventbusProtoCloudLoggingDetails] = None,
	  /** Final error-code if event failed. */
		errorCode: Option[Schema.CrmlogErrorCode] = None,
	  /** The event data user sends as request. */
		clientId: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1Secret(
	  /** The resource name of the secret version in the format, format as: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		secretVersion: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaParameterMapEntry(
	  /** Value of the map entry. */
		value: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMapField] = None,
	  /** Key of the map entry. */
		key: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMapField] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaGetClientResponse(
	  /** Required. Required: The client configuration that was requested */
		client: Option[Schema.GoogleCloudIntegrationsV1alphaClientConfig] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaCancelExecutionRequest(
	  /** Required. Reason for cancelling the execution. This is provided by the client requesting the cancellation, and is not used by the Platform. */
		cancelReason: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoTransformExpression(
	  /** Transformations to be applied sequentially. */
		transformationFunctions: Option[List[Schema.EnterpriseCrmEventbusProtoFunction]] = None,
	  /** Initial value upon which to perform transformations. */
		initialValue: Option[Schema.EnterpriseCrmEventbusProtoBaseValue] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaParameterMapField(
	  /** Passing a literal value. */
		literalValue: Option[Schema.GoogleCloudIntegrationsV1alphaValueType] = None,
	  /** Referencing one of the Integration variables. */
		referenceKey: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaProjectProperties {
		enum BillingTypeEnum extends Enum[BillingTypeEnum] { case BILLING_TYPE_UNSPECIFIED, APIGEE_TRIALS, APIGEE_SUBSCRIPTION, PAYG, SUBSCRIPTION, NO_BILLING }
		enum IpEnablementStateEnum extends Enum[IpEnablementStateEnum] { case IP_ENABLEMENT_STATE_UNSPECIFIED, IP_ENABLEMENT_STATE_STANDALONE, IP_ENABLEMENT_STATE_APIGEE, IP_ENABLEMENT_STATE_APIGEE_ENTITLED }
	}
	case class GoogleCloudIntegrationsV1alphaProjectProperties(
	  /** A list of provisioned regions on the current project */
		provisionedRegions: Option[List[String]] = None,
	  /** Required. Required: The client billing type that was requested */
		billingType: Option[Schema.GoogleCloudIntegrationsV1alphaProjectProperties.BillingTypeEnum] = None,
	  /** An enum value of what the enablement state is for the given project */
		ipEnablementState: Option[Schema.GoogleCloudIntegrationsV1alphaProjectProperties.IpEnablementStateEnum] = None
	)
	
	case class EnterpriseCrmEventbusProtoConnectorsConnection(
	  /** Service name Format: projects/{project}/locations/{location}/namespaces/{namespace}/services/{service} */
		serviceName: Option[String] = None,
	  /** Connector version Format: projects/{project}/locations/{location}/providers/{provider}/connectors/{connector}/versions/{version} */
		connectorVersion: Option[String] = None,
	  /** Connection name Format: projects/{project}/locations/{location}/connections/{connection} */
		connectionName: Option[String] = None,
	  /** The name of the Hostname of the Service Directory service with TLS if used. */
		host: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaAttemptStats(
	  /** The end time of the integration execution for current attempt. */
		endTime: Option[String] = None,
	  /** The start time of the integration execution for current attempt. This could be in the future if it's been scheduled. */
		startTime: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1EventingConfigDeadLetterConfig(
	  /** Optional. Project which has the topic given. */
		projectId: Option[String] = None,
	  /** Optional. Topic to push events which couldn't be processed. */
		topic: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaResolveSuspensionRequest(
	  /** Suspension, containing the event_execution_info_id, task_id, and state to set on the corresponding suspension record. */
		suspension: Option[Schema.GoogleCloudIntegrationsV1alphaSuspension] = None
	)
	
	case class EnterpriseCrmEventbusProtoParameterMapField(
	  /** Passing a literal value. */
		literalValue: Option[Schema.EnterpriseCrmEventbusProtoParameterValueType] = None,
	  /** Referencing one of the WF variables. */
		referenceKey: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaPublishIntegrationVersionResponse(
	
	)
	
	object GoogleCloudConnectorsV1ConnectionStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, INACTIVE, DELETING, UPDATING, ERROR, AUTHORIZATION_REQUIRED }
	}
	case class GoogleCloudConnectorsV1ConnectionStatus(
	  /** State. */
		state: Option[Schema.GoogleCloudConnectorsV1ConnectionStatus.StateEnum] = None,
	  /** Description. */
		description: Option[String] = None,
	  /** Status provides detailed information for the state. */
		status: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTestIntegrationsRequest(
	  /** Required. integration config to execute the workflow */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None,
	  /** Required. This is used to identify the client on whose behalf the event will be executed. */
		clientId: Option[String] = None,
	  /** Optional. Input parameters used during integration execution. */
		inputParameters: Option[Map[String, Schema.GoogleCloudIntegrationsV1alphaValueType]] = None,
	  /** Optional. Can be specified in the event request, otherwise false (default). If true, enables tasks with condition "test_mode = true". If false, disables tasks with condition "test_mode = true" if global test mode (set by platform) is also false {@link EventBusConfig}. */
		testMode: Option[Boolean] = None,
	  /** Optional. Passed in as parameters to each integration execution. */
		parameters: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** Optional. custom deadline of the rpc */
		deadlineSecondsTime: Option[String] = None,
	  /** Required. The trigger id of the integration trigger config. If both trigger_id and client_id is present, the integration is executed from the start tasks provided by the matching trigger config otherwise it is executed from the default start tasks. */
		triggerId: Option[String] = None,
	  /** Optional. Config parameters used during integration execution. */
		configParameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaEventParameter(
	  /** True if this parameter should be masked in the logs */
		masked: Option[Boolean] = None,
	  /** Values for the defined keys. Each value can either be string, int, double or any proto message. */
		value: Option[Schema.GoogleCloudIntegrationsV1alphaValueType] = None,
	  /** Key is used to retrieve the corresponding parameter value. This should be unique for a given fired event. These parameters must be predefined in the integration definition. */
		key: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoCloudSchedulerConfig(
	  /** Required. The cron tab of cloud scheduler trigger. */
		cronTab: Option[String] = None,
	  /** Required. Service account used by Cloud Scheduler to trigger the integration at scheduled time */
		serviceAccountEmail: Option[String] = None,
	  /** Optional. When the job was deleted from Pantheon UI, error_message will be populated when Get/List integrations */
		errorMessage: Option[String] = None,
	  /** Required. The location where associated cloud scheduler job will be created */
		location: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1DestinationConfig(
	  /** The destinations for the key. */
		destinations: Option[List[Schema.GoogleCloudConnectorsV1Destination]] = None,
	  /** The key is the destination identifier that is supported by the Connector. */
		key: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse(
	  /** The list of SfdcChannels retrieved. */
		sfdcChannels: Option[List[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]] = None,
	  /** The token used to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaTakeoverEditLockRequest(
	
	)
	
	case class EnterpriseCrmEventbusProtoNextTeardownTask(
	  /** Required. Name of the next teardown task. */
		name: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUnshareTemplateRequest(
	  /** Optional. Project name resources to unshare the template. The project names is expected in resource format Ex: projects/{project-number} */
		resourceNames: Option[List[String]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListTemplatesResponse(
	  /** List of templates retrieved. */
		templates: Option[List[Schema.GoogleCloudIntegrationsV1alphaTemplate]] = None,
	  /** The token used to retrieve the next page results. */
		nextPageToken: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoLogSettings {
		enum SeedScopeEnum extends Enum[SeedScopeEnum] { case SEED_SCOPE_UNSPECIFIED, EVENT_NAME, TIME_PERIOD, PARAM_NAME }
		enum SeedPeriodEnum extends Enum[SeedPeriodEnum] { case SEED_PERIOD_UNSPECIFIED, DAY, WEEK, MONTH }
	}
	case class EnterpriseCrmEventbusProtoLogSettings(
		seedScope: Option[Schema.EnterpriseCrmEventbusProtoLogSettings.SeedScopeEnum] = None,
		seedPeriod: Option[Schema.EnterpriseCrmEventbusProtoLogSettings.SeedPeriodEnum] = None,
	  /** The name of corresponding logging field of the event property. If omitted, assumes the same name as the event property key. */
		logFieldName: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUsernameAndPassword(
	  /** Password to be used */
		password: Option[String] = None,
	  /** Username to be used */
		username: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoCondition {
		enum OperatorEnum extends Enum[OperatorEnum] { case UNSET, EQUALS, CONTAINS, LESS_THAN, GREATER_THAN, EXISTS, DOES_NOT_EXIST, IS_EMPTY, IS_NOT_EMPTY }
	}
	case class EnterpriseCrmEventbusProtoCondition(
	  /** Operator used to evaluate the condition. Please note that an operator with an inappropriate key/value operand will result in IllegalArgumentException, e.g. CONTAINS with boolean key/value pair. */
		operator: Option[Schema.EnterpriseCrmEventbusProtoCondition.OperatorEnum] = None,
	  /** Key that's evaluated against the `value`. Please note the data type of the runtime value associated with the key should match the data type of `value`, else an IllegalArgumentException is thrown. */
		eventPropertyKey: Option[String] = None,
	  /** Value that's checked for the key. */
		value: Option[Schema.EnterpriseCrmEventbusProtoValueType] = None
	)
	
	object GoogleCloudConnectorsV1AuthConfig {
		enum AuthTypeEnum extends Enum[AuthTypeEnum] { case AUTH_TYPE_UNSPECIFIED, USER_PASSWORD, OAUTH2_JWT_BEARER, OAUTH2_CLIENT_CREDENTIALS, SSH_PUBLIC_KEY, OAUTH2_AUTH_CODE_FLOW, GOOGLE_AUTHENTICATION, OAUTH2_AUTH_CODE_FLOW_GOOGLE_MANAGED }
	}
	case class GoogleCloudConnectorsV1AuthConfig(
	  /** Oauth2AuthCodeFlow. */
		oauth2AuthCodeFlow: Option[Schema.GoogleCloudConnectorsV1AuthConfigOauth2AuthCodeFlow] = None,
	  /** The type of authentication configured. */
		authType: Option[Schema.GoogleCloudConnectorsV1AuthConfig.AuthTypeEnum] = None,
	  /** Identifier key for auth config */
		authKey: Option[String] = None,
	  /** Oauth2AuthCodeFlowGoogleManaged. */
		oauth2AuthCodeFlowGoogleManaged: Option[Schema.GoogleCloudConnectorsV1AuthConfigOauth2AuthCodeFlowGoogleManaged] = None,
	  /** UserPassword. */
		userPassword: Option[Schema.GoogleCloudConnectorsV1AuthConfigUserPassword] = None,
	  /** Oauth2ClientCredentials. */
		oauth2ClientCredentials: Option[Schema.GoogleCloudConnectorsV1AuthConfigOauth2ClientCredentials] = None,
	  /** List containing additional auth configs. */
		additionalVariables: Option[List[Schema.GoogleCloudConnectorsV1ConfigVariable]] = None,
	  /** SSH Public Key. */
		sshPublicKey: Option[Schema.GoogleCloudConnectorsV1AuthConfigSshPublicKey] = None,
	  /** Oauth2JwtBearer. */
		oauth2JwtBearer: Option[Schema.GoogleCloudConnectorsV1AuthConfigOauth2JwtBearer] = None
	)
	
	case class EnterpriseCrmEventbusProtoDoubleArray(
		values: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecuteIntegrationsResponse(
	  /** The id of the execution corresponding to this run of integration. */
		executionId: Option[String] = None,
	  /** OUTPUT parameters in format of Map. Where Key is the name of the parameter. Note: Name of the system generated parameters are wrapped by backtick(`) to distinguish them from the user defined parameters. */
		outputParameters: Option[Map[String, JsValue]] = None,
	  /** Parameters are a part of Event and can be used to communicate between different tasks that are part of the same integration execution. */
		parameterEntries: Option[List[Schema.EnterpriseCrmFrontendsEventbusProtoParameterEntry]] = None,
	  /** Is true if any execution in the integration failed. False otherwise. */
		executionFailed: Option[Boolean] = None,
	  /** Details for the integration that were executed. */
		eventParameters: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None
	)
	
	object GoogleCloudIntegrationsV1alphaClientConfig {
		enum ClientStateEnum extends Enum[ClientStateEnum] { case CLIENT_STATE_UNSPECIFIED, CLIENT_STATE_ACTIVE, CLIENT_STATE_DISABLED }
		enum BillingTypeEnum extends Enum[BillingTypeEnum] { case BILLING_TYPE_UNSPECIFIED, BILLING_TYPE_APIGEE_TRIALS, BILLING_TYPE_APIGEE_SUBSCRIPTION, BILLING_TYPE_PAYG }
	}
	case class GoogleCloudIntegrationsV1alphaClientConfig(
	  /** Default run-as service account email, set up during project provision time, that will be used to generate auth token to be used in Connector task, Rest caller task, Cloud function task and Subworkflows. */
		runAsServiceAccount: Option[String] = None,
	  /** The timestamp when the client was first created. */
		createTime: Option[String] = None,
	  /** The region the client is linked to. */
		region: Option[String] = None,
	  /** The GCP project id of the client associated with */
		projectId: Option[String] = None,
	  /** Indicates the activity state the client */
		clientState: Option[Schema.GoogleCloudIntegrationsV1alphaClientConfig.ClientStateEnum] = None,
	  /** Optional. Indicates the client is provisioned with CMEK or GMEK. */
		isGmek: Option[Boolean] = None,
	  /** Optional. True if variable masking feature should be turned on for this region */
		enableVariableMasking: Option[Boolean] = None,
	  /** Description of what the client is used for */
		description: Option[String] = None,
	  /** Optional. Indicates the client enables internal IP feature, this is applicable for internal clients only. */
		enableInternalIp: Option[Boolean] = None,
	  /** The service agent associated with this client */
		p4ServiceAccount: Option[String] = None,
	  /** Cloud KMS config for Auth Module to encrypt/decrypt credentials. */
		cloudKmsConfig: Option[Schema.GoogleCloudIntegrationsV1alphaCloudKmsConfig] = None,
	  /** Indicates the billing type of the client */
		billingType: Option[Schema.GoogleCloudIntegrationsV1alphaClientConfig.BillingTypeEnum] = None,
	  /** Globally unique ID (project_id + region) */
		id: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaSerializedFile {
		enum FileEnum extends Enum[FileEnum] { case INTEGRATION_FILE_UNSPECIFIED, INTEGRATION, INTEGRATION_CONFIG_VARIABLES }
	}
	case class GoogleCloudIntegrationsV1alphaSerializedFile(
	  /** String representation of the file content. */
		content: Option[String] = None,
	  /** File information like Integration version, Integration Config variables etc. */
		file: Option[Schema.GoogleCloudIntegrationsV1alphaSerializedFile.FileEnum] = None
	)
	
	case class GoogleCloudConnectorsV1ResourceRequests(
	  /** Output only. CPU request. */
		cpu: Option[String] = None,
	  /** Output only. Memory request. */
		memory: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoTaskMetadataAdmin(
		userEmail: Option[String] = None,
		googleGroupEmail: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaReplayExecutionRequest(
	  /** Required. The user provided reason for replaying the execution. */
		replayReason: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoBooleanParameterArray(
		booleanValues: Option[List[Boolean]] = None
	)
	
	object GoogleCloudIntegrationsV1alphaIntegrationAlertConfig {
		enum MetricTypeEnum extends Enum[MetricTypeEnum] { case METRIC_TYPE_UNSPECIFIED, EVENT_ERROR_RATE, EVENT_WARNING_RATE, TASK_ERROR_RATE, TASK_WARNING_RATE, TASK_RATE, EVENT_RATE, EVENT_AVERAGE_DURATION, EVENT_PERCENTILE_DURATION, TASK_AVERAGE_DURATION, TASK_PERCENTILE_DURATION }
		enum ThresholdTypeEnum extends Enum[ThresholdTypeEnum] { case THRESHOLD_TYPE_UNSPECIFIED, EXPECTED_MIN, EXPECTED_MAX }
	}
	case class GoogleCloudIntegrationsV1alphaIntegrationAlertConfig(
	  /** For how many contiguous aggregation periods should the expected min or max be violated for the alert to be fired. */
		alertThreshold: Option[Int] = None,
	  /** For either events or tasks, depending on the type of alert, count only final attempts, not retries. */
		onlyFinalAttempt: Option[Boolean] = None,
	  /** Set to false by default. When set to true, the metrics are not aggregated or pushed to Monarch for this integration alert. */
		disableAlert: Option[Boolean] = None,
	  /** The metric value, above or below which the alert should be triggered. */
		thresholdValue: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationAlertConfigThresholdValue] = None,
	  /** Should be specified only for &#42;AVERAGE_DURATION and &#42;PERCENTILE_DURATION metrics. This member should be used to specify what duration value the metrics should exceed for the alert to trigger. */
		durationThreshold: Option[String] = None,
	  /** The type of metric. */
		metricType: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationAlertConfig.MetricTypeEnum] = None,
	  /** The threshold type, whether lower(expected_min) or upper(expected_max), for which this alert is being configured. If value falls below expected_min or exceeds expected_max, an alert will be fired. */
		thresholdType: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationAlertConfig.ThresholdTypeEnum] = None,
	  /** Name of the alert. This will be displayed in the alert subject. If set, this name should be unique within the scope of the integration. */
		displayName: Option[String] = None,
	  /** The period over which the metric value should be aggregated and evaluated. Format is , where integer should be a positive integer and unit should be one of (s,m,h,d,w) meaning (second, minute, hour, day, week). For an EXPECTED_MIN threshold, this aggregation_period must be lesser than 24 hours. */
		aggregationPeriod: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigOauth2JwtBearerJwtClaims(
	  /** Value for the "iss" claim. */
		issuer: Option[String] = None,
	  /** Value for the "sub" claim. */
		subject: Option[String] = None,
	  /** Value for the "aud" claim. */
		audience: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoEventExecutionDetailsEventAttemptStats(
	  /** The start time of the event execution for current attempt. This could be in the future if it's been scheduled. */
		startTime: Option[String] = None,
	  /** The end time of the event execution for current attempt. */
		endTime: Option[String] = None
	)
	
	case class EnterpriseCrmEventbusProtoNotification(
		emailAddress: Option[Schema.EnterpriseCrmEventbusProtoAddress] = None,
		pubsubTopic: Option[String] = None,
		buganizerNotification: Option[Schema.EnterpriseCrmEventbusProtoBuganizerNotification] = None,
	  /** If the out-of-the-box email/pubsub notifications are not suitable and custom logic is required, fire a workflow containing all info needed to notify users to resume execution. */
		request: Option[Schema.EnterpriseCrmEventbusProtoCustomSuspensionRequest] = None,
		escalatorQueue: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaScheduleIntegrationsResponse(
	  /** The execution info id for the executed integrations. */
		executionInfoIds: Option[List[String]] = None
	)
	
	case class GoogleCloudConnectorsV1NodeConfig(
	  /** Maximum number of nodes in the runtime nodes. */
		maxNodeCount: Option[Int] = None,
	  /** Minimum number of nodes in the runtime nodes. */
		minNodeCount: Option[Int] = None
	)
	
	case class GoogleCloudConnectorsV1ResourceLimits(
	  /** Output only. Memory limit. */
		memory: Option[String] = None,
	  /** Output only. CPU limit. */
		cpu: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoTaskUiModuleConfig {
		enum ModuleIdEnum extends Enum[ModuleIdEnum] { case UNSPECIFIED_TASK_MODULE, LABEL, ERROR_HANDLING, TASK_PARAM_TABLE, TASK_PARAM_FORM, PRECONDITION, SCRIPT_EDITOR, RPC, TASK_SUMMARY, SUSPENSION, RPC_TYPED, SUB_WORKFLOW, APPS_SCRIPT_NAVIGATOR, SUB_WORKFLOW_FOR_EACH_LOOP, FIELD_MAPPING, README, REST_CALLER, SUB_WORKFLOW_SCATTER_GATHER, CLOUD_SQL, GENERIC_CONNECTOR_TASK }
	}
	case class EnterpriseCrmEventbusProtoTaskUiModuleConfig(
	  /** ID of the config module. */
		moduleId: Option[Schema.EnterpriseCrmEventbusProtoTaskUiModuleConfig.ModuleIdEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaOAuth2AuthorizationCode {
		enum RequestTypeEnum extends Enum[RequestTypeEnum] { case REQUEST_TYPE_UNSPECIFIED, REQUEST_BODY, QUERY_PARAMETERS, ENCODED_HEADER }
	}
	case class GoogleCloudIntegrationsV1alphaOAuth2AuthorizationCode(
	  /** The client's id. */
		clientId: Option[String] = None,
	  /** The Auth Code that is used to initially retrieve the access token. */
		authCode: Option[String] = None,
	  /** The auth parameters sent along with the auth code request. */
		authParams: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap] = None,
	  /** A space-delimited list of requested scope permissions. */
		scope: Option[String] = None,
	  /** The client's secret. */
		clientSecret: Option[String] = None,
	  /** Represent how to pass parameters to fetch access token */
		requestType: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2AuthorizationCode.RequestTypeEnum] = None,
	  /** The token url endpoint to send the token request to. */
		tokenEndpoint: Option[String] = None,
	  /** The auth url endpoint to send the auth code request to. */
		authEndpoint: Option[String] = None,
	  /** The token parameters sent along with the token request. */
		tokenParams: Option[Schema.GoogleCloudIntegrationsV1alphaParameterMap] = None,
	  /** Indicates if the user has opted in Google Reauth Policy. If opted in, the refresh token will be valid for 20 hours, after which time users must re-authenticate in order to obtain a new one. */
		applyReauthPolicy: Option[Boolean] = None,
	  /** The access token received from the token endpoint. */
		accessToken: Option[Schema.GoogleCloudIntegrationsV1alphaAccessToken] = None
	)
	
	object GoogleCloudConnectorsV1SslConfig {
		enum ServerCertTypeEnum extends Enum[ServerCertTypeEnum] { case CERT_TYPE_UNSPECIFIED, PEM }
		enum TrustModelEnum extends Enum[TrustModelEnum] { case PUBLIC, PRIVATE, INSECURE }
		enum TypeEnum extends Enum[TypeEnum] { case SSL_TYPE_UNSPECIFIED, TLS, MTLS }
		enum ClientCertTypeEnum extends Enum[ClientCertTypeEnum] { case CERT_TYPE_UNSPECIFIED, PEM }
	}
	case class GoogleCloudConnectorsV1SslConfig(
	  /** Private Server Certificate. Needs to be specified if trust model is `PRIVATE`. */
		privateServerCertificate: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Secret containing the passphrase protecting the Client Private Key */
		clientPrivateKeyPass: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Additional SSL related field values */
		additionalVariables: Option[List[Schema.GoogleCloudConnectorsV1ConfigVariable]] = None,
	  /** Type of Server Cert (PEM/JKS/.. etc.) */
		serverCertType: Option[Schema.GoogleCloudConnectorsV1SslConfig.ServerCertTypeEnum] = None,
	  /** Bool for enabling SSL */
		useSsl: Option[Boolean] = None,
	  /** Trust Model of the SSL connection */
		trustModel: Option[Schema.GoogleCloudConnectorsV1SslConfig.TrustModelEnum] = None,
	  /** Client Certificate */
		clientCertificate: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Client Private Key */
		clientPrivateKey: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Controls the ssl type for the given connector version. */
		`type`: Option[Schema.GoogleCloudConnectorsV1SslConfig.TypeEnum] = None,
	  /** Type of Client Cert (PEM/JKS/.. etc.) */
		clientCertType: Option[Schema.GoogleCloudConnectorsV1SslConfig.ClientCertTypeEnum] = None
	)
	
	object GoogleCloudIntegrationsV1alphaCertificate {
		enum CertificateStatusEnum extends Enum[CertificateStatusEnum] { case STATE_UNSPECIFIED, ACTIVE, EXPIRED }
	}
	case class GoogleCloudIntegrationsV1alphaCertificate(
	  /** Required. Name of the certificate */
		displayName: Option[String] = None,
	  /** Description of the certificate */
		description: Option[String] = None,
	  /** Immutable. Credential id that will be used to register with trawler */
		credentialId: Option[String] = None,
	  /** Immutable. Requestor ID to be used to register certificate with trawler */
		requestorId: Option[String] = None,
	  /** Output only. Auto generated primary key */
		name: Option[String] = None,
	  /** Output only. The timestamp after which certificate will expire */
		validEndTime: Option[String] = None,
	  /** Status of the certificate */
		certificateStatus: Option[Schema.GoogleCloudIntegrationsV1alphaCertificate.CertificateStatusEnum] = None,
	  /** Output only. The timestamp after which certificate will be valid */
		validStartTime: Option[String] = None,
	  /** Input only. Raw client certificate which would be registered with trawler */
		rawCertificate: Option[Schema.GoogleCloudIntegrationsV1alphaClientCertificate] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoEventExecutionSnapshot(
	  /** Indicates "right after which checkpoint task's execution" this snapshot is taken. */
		checkpointTaskNumber: Option[String] = None,
	  /** The parameters in Event object that differs from last snapshot. */
		diffParams: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** The task name associated with this snapshot. Could be empty. */
		taskName: Option[String] = None,
	  /** Indicates when this snapshot is taken. */
		snapshotTime: Option[String] = None,
	  /** All of the task execution details at the given point of time. */
		taskExecutionDetails: Option[List[Schema.EnterpriseCrmEventbusProtoTaskExecutionDetails]] = None,
	  /** Auto-generated. Used as primary key for EventExecutionSnapshots table. */
		eventExecutionSnapshotId: Option[String] = None,
		eventExecutionSnapshotMetadata: Option[Schema.EnterpriseCrmEventbusProtoEventExecutionSnapshotEventExecutionSnapshotMetadata] = None,
	  /** Points to the event execution info this snapshot belongs to. */
		eventExecutionInfoId: Option[String] = None,
	  /** The parameters in Event object. */
		eventParams: Option[Schema.EnterpriseCrmFrontendsEventbusProtoEventParameters] = None,
	  /** All of the computed conditions that been calculated. */
		conditionResults: Option[List[Schema.EnterpriseCrmEventbusProtoConditionResult]] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaDownloadTemplateResponse(
	  /** String representation of the template. */
		content: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest(
	
	)
	
	object GoogleCloudIntegrationsV1alphaTaskExecutionDetails {
		enum TaskExecutionStateEnum extends Enum[TaskExecutionStateEnum] { case TASK_EXECUTION_STATE_UNSPECIFIED, PENDING_EXECUTION, IN_PROCESS, SUCCEED, FAILED, FATAL, RETRY_ON_HOLD, SKIPPED, CANCELLED, PENDING_ROLLBACK, ROLLBACK_IN_PROCESS, ROLLEDBACK, SUSPENDED }
	}
	case class GoogleCloudIntegrationsV1alphaTaskExecutionDetails(
	  /** Status for the current task execution attempt. */
		taskAttemptStats: Option[List[Schema.GoogleCloudIntegrationsV1alphaAttemptStats]] = None,
	  /** The execution state of this task. */
		taskExecutionState: Option[Schema.GoogleCloudIntegrationsV1alphaTaskExecutionDetails.TaskExecutionStateEnum] = None,
	  /** Pointer to the task config it used for execution. */
		taskNumber: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaCredential {
		enum CredentialTypeEnum extends Enum[CredentialTypeEnum] { case CREDENTIAL_TYPE_UNSPECIFIED, USERNAME_AND_PASSWORD, API_KEY, OAUTH2_AUTHORIZATION_CODE, OAUTH2_IMPLICIT, OAUTH2_CLIENT_CREDENTIALS, OAUTH2_RESOURCE_OWNER_CREDENTIALS, JWT, AUTH_TOKEN, SERVICE_ACCOUNT, CLIENT_CERTIFICATE_ONLY, OIDC_TOKEN }
	}
	case class GoogleCloudIntegrationsV1alphaCredential(
	  /** The api_key and oauth2_implicit are not covered in v1 and will be picked up once v1 is implemented. ApiKey api_key = 3; OAuth2 authorization code credential */
		oauth2AuthorizationCode: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2AuthorizationCode] = None,
	  /** Auth token credential */
		authToken: Option[Schema.GoogleCloudIntegrationsV1alphaAuthToken] = None,
	  /** OAuth2Implicit oauth2_implicit = 5; OAuth2 client credentials */
		oauth2ClientCredentials: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2ClientCredentials] = None,
	  /** Service account credential */
		serviceAccountCredentials: Option[Schema.GoogleCloudIntegrationsV1alphaServiceAccountCredentials] = None,
	  /** Credential type associated with auth config. */
		credentialType: Option[Schema.GoogleCloudIntegrationsV1alphaCredential.CredentialTypeEnum] = None,
	  /** Username and password credential */
		usernameAndPassword: Option[Schema.GoogleCloudIntegrationsV1alphaUsernameAndPassword] = None,
	  /** Google OIDC ID Token */
		oidcToken: Option[Schema.GoogleCloudIntegrationsV1alphaOidcToken] = None,
	  /** OAuth2 resource owner credentials */
		oauth2ResourceOwnerCredentials: Option[Schema.GoogleCloudIntegrationsV1alphaOAuth2ResourceOwnerCredentials] = None,
	  /** JWT credential */
		jwt: Option[Schema.GoogleCloudIntegrationsV1alphaJwt] = None
	)
	
	case class EnterpriseCrmEventbusProtoNextTask(
	  /** User-provided label that is attached to this edge in the UI. */
		label: Option[String] = None,
	  /** Combined condition for this task to become an eligible next task. Each of these combined_conditions are joined with logical OR. DEPRECATED: use `condition` */
		combinedConditions: Option[List[Schema.EnterpriseCrmEventbusProtoCombinedCondition]] = None,
	  /** Standard filter expression for this task to become an eligible next task. */
		condition: Option[String] = None,
	  /** Task number of the next task. */
		taskNumber: Option[String] = None,
	  /** User-provided description intended to give more business context about the next task edge or condition. */
		description: Option[String] = None,
	  /** ID of the next task. */
		taskConfigId: Option[String] = None
	)
	
	case class GoogleCloudConnectorsV1EventingConfig(
	  /** Optional. Ingress endpoint of the event listener. This is used only when private connectivity is enabled. */
		eventsListenerIngressEndpoint: Option[String] = None,
	  /** Optional. Private Connectivity Enabled. */
		privateConnectivityEnabled: Option[Boolean] = None,
	  /** Optional. Auth details for the event listener. */
		listenerAuthConfig: Option[Schema.GoogleCloudConnectorsV1AuthConfig] = None,
	  /** Enrichment Enabled. */
		enrichmentEnabled: Option[Boolean] = None,
	  /** Auth details for the webhook adapter. */
		authConfig: Option[Schema.GoogleCloudConnectorsV1AuthConfig] = None,
	  /** Optional. Dead letter configuration for eventing of a connection. */
		deadLetterConfig: Option[Schema.GoogleCloudConnectorsV1EventingConfigDeadLetterConfig] = None,
	  /** Additional eventing related field values */
		additionalVariables: Option[List[Schema.GoogleCloudConnectorsV1ConfigVariable]] = None,
	  /** Optional. Proxy for Eventing auto-registration. */
		proxyDestinationConfig: Option[Schema.GoogleCloudConnectorsV1DestinationConfig] = None,
	  /** Registration endpoint for auto registration. */
		registrationDestinationConfig: Option[Schema.GoogleCloudConnectorsV1DestinationConfig] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaSuspensionApprovalConfig(
	  /** Information to provide for recipients. */
		customMessage: Option[String] = None,
	  /** Indicates the next steps when no external actions happen on the suspension. */
		expiration: Option[Schema.GoogleCloudIntegrationsV1alphaSuspensionApprovalExpiration] = None,
	  /** Email addresses to send approval request to. */
		emailAddresses: Option[List[String]] = None
	)
	
	case class GoogleCloudConnectorsV1AuthConfigSshPublicKey(
	  /** SSH Client Cert. It should contain both public and private key. */
		sshClientCert: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Format of SSH Client cert. */
		certType: Option[String] = None,
	  /** Password (passphrase) for ssh client certificate if it has one. */
		sshClientCertPass: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** The user account used to authenticate. */
		username: Option[String] = None
	)
	
	object GoogleCloudIntegrationsV1alphaExecutionDetails {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, PROCESSING, SUCCEEDED, FAILED, CANCELLED, RETRY_ON_HOLD, SUSPENDED }
	}
	case class GoogleCloudIntegrationsV1alphaExecutionDetails(
	  /** List of snapshots taken during the execution. */
		executionSnapshots: Option[List[Schema.GoogleCloudIntegrationsV1alphaExecutionSnapshot]] = None,
	  /** Status of the execution. */
		state: Option[Schema.GoogleCloudIntegrationsV1alphaExecutionDetails.StateEnum] = None,
	  /** List of Start and end time of the execution attempts. */
		attemptStats: Option[List[Schema.GoogleCloudIntegrationsV1alphaAttemptStats]] = None,
	  /** Total size of all event_execution_snapshots for an execution */
		eventExecutionSnapshotsSize: Option[String] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaUploadIntegrationVersionResponse(
	  /** The uploaded integration. */
		integrationVersion: Option[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaListConnectionsResponse(
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** Connections. */
		connections: Option[List[Schema.GoogleCloudConnectorsV1Connection]] = None
	)
	
	case class GoogleCloudConnectorsV1ConfigVariable(
	  /** Value is a secret. */
		secretValue: Option[Schema.GoogleCloudConnectorsV1Secret] = None,
	  /** Value is a Encryption Key. */
		encryptionKeyValue: Option[Schema.GoogleCloudConnectorsV1EncryptionKey] = None,
	  /** Value is a string. */
		stringValue: Option[String] = None,
	  /** Value is a bool. */
		boolValue: Option[Boolean] = None,
	  /** Key of the config variable. */
		key: Option[String] = None,
	  /** Value is an integer */
		intValue: Option[String] = None
	)
	
	case class EnterpriseCrmFrontendsEventbusProtoParameterMapField(
	  /** Referencing one of the WF variables. */
		referenceKey: Option[String] = None,
	  /** Passing a literal value. */
		literalValue: Option[Schema.EnterpriseCrmFrontendsEventbusProtoParameterValueType] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaExecutionReplayInfo(
	  /** If this execution has been replayed, then this field contains the execution ids of the replayed executions. */
		replayedExecutionInfoIds: Option[List[String]] = None,
	  /** If this execution is a replay of another execution, then this field contains the original execution id. */
		originalExecutionInfoId: Option[String] = None,
	  /** reason for replay */
		replayReason: Option[String] = None
	)
	
	object EnterpriseCrmEventbusProtoDoubleArrayFunction {
		enum FunctionNameEnum extends Enum[FunctionNameEnum] { case UNSPECIFIED, GET, APPEND, SIZE, SUM, AVG, MAX, MIN, TO_SET, APPEND_ALL, TO_JSON, SET, REMOVE, REMOVE_AT, CONTAINS, FOR_EACH, FILTER }
	}
	case class EnterpriseCrmEventbusProtoDoubleArrayFunction(
		functionName: Option[Schema.EnterpriseCrmEventbusProtoDoubleArrayFunction.FunctionNameEnum] = None
	)
	
	case class GoogleCloudIntegrationsV1alphaProvisionClientRequest(
	  /** Optional. User input run-as service account, if empty, will bring up a new default service account */
		runAsServiceAccount: Option[String] = None,
	  /** Optional. Indicates if sample workflow should be created along with provisioning */
		createSampleWorkflows: Option[Boolean] = None,
	  /** Optional. Deprecated. Indicates provision with GMEK or CMEK. This field is deprecated and the provision would always be GMEK if cloud_kms_config is not present in the request. */
		provisionGmek: Option[Boolean] = None,
	  /** Optional. Indicates if skip CP provision or not */
		skipCpProvision: Option[Boolean] = None,
	  /** Optional. OPTIONAL: Cloud KMS config for AuthModule to encrypt/decrypt credentials. */
		cloudKmsConfig: Option[Schema.GoogleCloudIntegrationsV1alphaCloudKmsConfig] = None
	)
}
