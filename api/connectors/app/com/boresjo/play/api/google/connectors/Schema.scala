package com.boresjo.play.api.google.connectors

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ExecuteActionRequest(
	  /** Parameters for executing the action. The parameters can be key/value pairs or nested structs. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class ExecuteActionResponse(
	  /** In the case of successful invocation of the specified action, the results Struct contains values based on the response of the action invoked. 1. If the action execution produces any entities as a result, they are returned as an array of Structs with the 'key' being the field name and the 'value' being the value of that field in each result row. { 'results': [{'key': 'value'}, ...] } */
		results: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ListActionsResponse(
	  /** List of action metadata. */
		actions: Option[List[Schema.Action]] = None,
	  /** List of actions which contain unsupported Datatypes. Check datatype.proto for more information. */
		unsupportedActionNames: Option[List[String]] = None,
	  /** Next page token if more actions available. */
		nextPageToken: Option[String] = None
	)
	
	case class Action(
	  /** Name of the action. */
		name: Option[String] = None,
	  /** List containing input parameter metadata. */
		inputParameters: Option[List[Schema.InputParameter]] = None,
	  /** List containing the metadata of result fields. */
		resultMetadata: Option[List[Schema.ResultMetadata]] = None,
	  /** JsonSchema representation of this actions's input schema */
		inputJsonSchema: Option[Schema.JsonSchema] = None,
	  /** JsonSchema representation of this actions's result schema */
		resultJsonSchema: Option[Schema.JsonSchema] = None,
	  /** Display Name of action to be shown on client side */
		displayName: Option[String] = None,
	  /** Brief Description of action */
		description: Option[String] = None
	)
	
	object InputParameter {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, INT, SMALLINT, DOUBLE, DATE, DATETIME, TIME, STRING, LONG, BOOLEAN, DECIMAL, UUID, BLOB, BIT, TINYINT, INTEGER, BIGINT, FLOAT, REAL, NUMERIC, CHAR, VARCHAR, LONGVARCHAR, TIMESTAMP, NCHAR, NVARCHAR, LONGNVARCHAR, NULL, OTHER, JAVA_OBJECT, DISTINCT, STRUCT, ARRAY, CLOB, REF, DATALINK, ROWID, BINARY, VARBINARY, LONGVARBINARY, NCLOB, SQLXML, REF_CURSOR, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE }
	}
	case class InputParameter(
	  /** Name of the Parameter. */
		name: Option[String] = None,
	  /** A brief description of the Parameter. */
		description: Option[String] = None,
	  /** The data type of the Parameter */
		dataType: Option[Schema.InputParameter.DataTypeEnum] = None,
	  /** Specifies whether a null value is allowed. */
		nullable: Option[Boolean] = None,
	  /** The following field specifies the default value of the Parameter provided by the external system if a value is not provided. */
		defaultValue: Option[JsValue] = None,
	  /** JsonSchema of the parameter, applicable only if parameter is of type `STRUCT` */
		jsonSchema: Option[Schema.JsonSchema] = None,
	  /** The following map contains fields that are not explicitly mentioned above,this give connectors the flexibility to add new metadata fields. */
		additionalDetails: Option[Map[String, JsValue]] = None
	)
	
	object JsonSchema {
		enum JdbcTypeEnum extends Enum[JdbcTypeEnum] { case DATA_TYPE_UNSPECIFIED, INT, SMALLINT, DOUBLE, DATE, DATETIME, TIME, STRING, LONG, BOOLEAN, DECIMAL, UUID, BLOB, BIT, TINYINT, INTEGER, BIGINT, FLOAT, REAL, NUMERIC, CHAR, VARCHAR, LONGVARCHAR, TIMESTAMP, NCHAR, NVARCHAR, LONGNVARCHAR, NULL, OTHER, JAVA_OBJECT, DISTINCT, STRUCT, ARRAY, CLOB, REF, DATALINK, ROWID, BINARY, VARBINARY, LONGVARBINARY, NCLOB, SQLXML, REF_CURSOR, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE }
	}
	case class JsonSchema(
	  /** A description of this schema. */
		description: Option[String] = None,
	  /** The default value of the field or object described by this schema. */
		default: Option[JsValue] = None,
	  /** Whether this property is required. */
		required: Option[List[String]] = None,
	  /** JSON Schema Validation: A Vocabulary for Structural Validation of JSON */
		`type`: Option[List[String]] = None,
	  /** Schema that applies to array values, applicable only if this is of type `array`. */
		items: Option[Schema.JsonSchema] = None,
	  /** The child schemas, applicable only if this is of type `object`. The key is the name of the property and the value is the json schema that describes that property */
		properties: Option[Map[String, Schema.JsonSchema]] = None,
	  /** Possible values for an enumeration. This works in conjunction with `type` to represent types with a fixed set of legal values */
		`enum`: Option[List[JsValue]] = None,
	  /** JDBC datatype of the field. */
		jdbcType: Option[Schema.JsonSchema.JdbcTypeEnum] = None,
	  /** Format of the value as per https://json-schema.org/understanding-json-schema/reference/string.html#format */
		format: Option[String] = None,
	  /** Additional details apart from standard json schema fields, this gives flexibility to store metadata about the schema */
		additionalDetails: Option[Map[String, JsValue]] = None
	)
	
	object ResultMetadata {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, INT, SMALLINT, DOUBLE, DATE, DATETIME, TIME, STRING, LONG, BOOLEAN, DECIMAL, UUID, BLOB, BIT, TINYINT, INTEGER, BIGINT, FLOAT, REAL, NUMERIC, CHAR, VARCHAR, LONGVARCHAR, TIMESTAMP, NCHAR, NVARCHAR, LONGNVARCHAR, NULL, OTHER, JAVA_OBJECT, DISTINCT, STRUCT, ARRAY, CLOB, REF, DATALINK, ROWID, BINARY, VARBINARY, LONGVARBINARY, NCLOB, SQLXML, REF_CURSOR, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE }
	}
	case class ResultMetadata(
	  /** Name of the metadata field. */
		name: Option[String] = None,
	  /** A brief description of the metadata field. */
		description: Option[String] = None,
	  /** The data type of the metadata field */
		dataType: Option[Schema.ResultMetadata.DataTypeEnum] = None,
	  /** JsonSchema of the result, applicable only if parameter is of type `STRUCT` */
		jsonSchema: Option[Schema.JsonSchema] = None,
	  /** Specifies whether a null value is allowed. */
		nullable: Option[Boolean] = None,
	  /** The following field specifies the default value of the Parameter provided by the external system if a value is not provided. */
		defaultValue: Option[JsValue] = None
	)
	
	object CheckStatusResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, ERROR, AUTH_ERROR }
	}
	case class CheckStatusResponse(
	  /** State of the connector. */
		state: Option[Schema.CheckStatusResponse.StateEnum] = None,
	  /** When the connector is not in ACTIVE state, the description must be populated to specify the reason why it's not in ACTIVE state. */
		description: Option[String] = None
	)
	
	case class CheckReadinessResponse(
		status: Option[String] = None
	)
	
	case class ExchangeAuthCodeRequest(
	  /** Optional. AuthCodeData contains the data the runtime requires to exchange for access and refresh tokens. If the data is not provided, the runtime will read the data from the secret manager. */
		authCodeData: Option[Schema.AuthCodeData] = None
	)
	
	case class AuthCodeData(
	  /** OAuth authorization code. */
		authCode: Option[String] = None,
	  /** OAuth PKCE verifier, needed if PKCE is enabled for this particular connection. */
		pkceVerifier: Option[String] = None,
	  /** OAuth redirect URI passed in during the auth code flow, required by some OAuth backends. */
		redirectUri: Option[String] = None
	)
	
	case class ExchangeAuthCodeResponse(
		accessCredentials: Option[Schema.AccessCredentials] = None
	)
	
	case class AccessCredentials(
	  /** OAuth access token. */
		accessToken: Option[String] = None,
	  /** OAuth refresh token. */
		refreshToken: Option[String] = None,
	  /** Duration till the access token expires. */
		expiresIn: Option[String] = None
	)
	
	case class RefreshAccessTokenRequest(
	  /** Optional. Refresh Token String. If the Refresh Token is not provided, the runtime will read the data from the secret manager. */
		refreshToken: Option[String] = None
	)
	
	case class RefreshAccessTokenResponse(
		accessCredentials: Option[Schema.AccessCredentials] = None
	)
	
	object EntityType {
		enum OperationsEnum extends Enum[OperationsEnum] { case OPERATION_UNSPECIFIED, LIST, GET, CREATE, UPDATE, DELETE }
	}
	case class EntityType(
	  /** The name of the entity type. */
		name: Option[String] = None,
	  /** List containing metadata information about each field of the entity type. */
		fields: Option[List[Schema.Field]] = None,
	  /** JsonSchema representation of this entity's schema */
		jsonSchema: Option[Schema.JsonSchema] = None,
		operations: Option[List[Schema.EntityType.OperationsEnum]] = None
	)
	
	object Field {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, INT, SMALLINT, DOUBLE, DATE, DATETIME, TIME, STRING, LONG, BOOLEAN, DECIMAL, UUID, BLOB, BIT, TINYINT, INTEGER, BIGINT, FLOAT, REAL, NUMERIC, CHAR, VARCHAR, LONGVARCHAR, TIMESTAMP, NCHAR, NVARCHAR, LONGNVARCHAR, NULL, OTHER, JAVA_OBJECT, DISTINCT, STRUCT, ARRAY, CLOB, REF, DATALINK, ROWID, BINARY, VARBINARY, LONGVARBINARY, NCLOB, SQLXML, REF_CURSOR, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE }
	}
	case class Field(
	  /** Name of the Field. */
		name: Option[String] = None,
	  /** A brief description of the Field. */
		description: Option[String] = None,
	  /** The data type of the Field. */
		dataType: Option[Schema.Field.DataTypeEnum] = None,
	  /** The following boolean field specifies if the current Field acts as a primary key or id if the parent is of type entity. */
		key: Option[Boolean] = None,
	  /** Specifies whether a null value is allowed. */
		nullable: Option[Boolean] = None,
	  /** The following field specifies the default value of the Field provided by the external system if a value is not provided. */
		defaultValue: Option[JsValue] = None,
	  /** The following map contains fields that are not explicitly mentioned above,this give connectors the flexibility to add new metadata fields. */
		additionalDetails: Option[Map[String, JsValue]] = None,
	  /** Reference captures the association between two different entity types. Value links to the reference of another entity type. */
		reference: Option[Schema.Reference] = None,
	  /** JsonSchema of the field, applicable only if field is of type `STRUCT` */
		jsonSchema: Option[Schema.JsonSchema] = None
	)
	
	case class Reference(
	  /** Name of reference entity type. */
		`type`: Option[String] = None,
	  /** Name of the reference field. */
		name: Option[String] = None
	)
	
	case class ListEntityTypesResponse(
	  /** List of metadata related to all entity types. */
		types: Option[List[Schema.EntityType]] = None,
	  /** Next page token if more entity types available. */
		nextPageToken: Option[String] = None,
	  /** List of entity type names which contain unsupported Datatypes. Check datatype.proto for more information. */
		unsupportedTypeNames: Option[List[String]] = None
	)
	
	case class ListEntitiesResponse(
	  /** List containing entity rows. */
		entities: Option[List[Schema.Entity]] = None,
	  /** Next page token if more records are available. */
		nextPageToken: Option[String] = None
	)
	
	case class Entity(
	  /** Output only. Resource name of the Entity. Format: projects/{project}/locations/{location}/connections/{connection}/entityTypes/{type}/entities/{id} */
		name: Option[String] = None,
	  /** Fields of the entity. The key is name of the field and the value contains the applicable `google.protobuf.Value` entry for this field. */
		fields: Option[Map[String, JsValue]] = None
	)
	
	case class UpdateEntitiesWithConditionsResponse(
	  /** Response returned by the external system. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Empty(
	
	)
	
	case class ExecuteSqlQueryRequest(
	  /** Required. SQL statement passed by clients like Integration Platform, the query is passed as-is to the driver used for interfacing with external systems. */
		query: Option[Schema.Query] = None
	)
	
	case class Query(
	  /** Required. Sql query to execute. */
		query: Option[String] = None,
	  /** Sets the number of seconds the driver will wait for a query to execute. */
		timeout: Option[String] = None,
	  /** Sets the limit for the maximum number of rows returned after the query execution. */
		maxRows: Option[String] = None,
	  /** In the struct, the value corresponds to the value of query parameter and date type corresponds to the date type of the query parameter. */
		queryParameters: Option[List[Schema.QueryParameter]] = None
	)
	
	object QueryParameter {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, INT, SMALLINT, DOUBLE, DATE, DATETIME, TIME, STRING, LONG, BOOLEAN, DECIMAL, UUID, BLOB, BIT, TINYINT, INTEGER, BIGINT, FLOAT, REAL, NUMERIC, CHAR, VARCHAR, LONGVARCHAR, TIMESTAMP, NCHAR, NVARCHAR, LONGNVARCHAR, NULL, OTHER, JAVA_OBJECT, DISTINCT, STRUCT, ARRAY, CLOB, REF, DATALINK, ROWID, BINARY, VARBINARY, LONGVARBINARY, NCLOB, SQLXML, REF_CURSOR, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE }
	}
	case class QueryParameter(
		value: Option[JsValue] = None,
		dataType: Option[Schema.QueryParameter.DataTypeEnum] = None
	)
	
	case class ExecuteSqlQueryResponse(
	  /** In the case of successful execution of the query the response contains results returned by the external system. For example, the result rows of the query are contained in the 'results' Struct list - "results": [ { "field1": "val1", "field2": "val2",.. },.. ] Each Struct row can contain fields any type of like nested Structs or lists. */
		results: Option[List[Map[String, JsValue]]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, REPAIRING, DELETING, ERROR }
	}
	case class Instance(
	  /** Unique name of the resource. It uses the form: `projects/{project_number}/locations/{location_id}/instances/{instance_id}` Note: This name is passed, stored and logged across the rollout system. So use of consumer project_id or any other consumer PII in the name is strongly discouraged for wipeout (go/wipeout) compliance. See go/elysium/project_ids#storage-guidance for more details. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the resource was last modified. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current lifecycle state of the resource (e.g. if it's being created or ready to use). */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Software versions that are used to deploy this instance. This can be mutated by rollout services. */
		softwareVersions: Option[Map[String, String]] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the referenced policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_settings.maintenance_policies is set. */
		maintenancePolicyNames: Option[Map[String, String]] = None,
	  /** Output only. ID of the associated GCP tenant project. See go/get-instance-metadata. */
		tenantProjectId: Option[String] = None,
	  /** Output only. Custom string attributes used primarily to expose producer-specific information in monitoring dashboards. See go/get-instance-metadata. */
		producerMetadata: Option[Map[String, String]] = None,
	  /** Output only. The list of data plane resources provisioned for this instance, e.g. compute VMs. See go/get-instance-metadata. */
		provisionedResources: Option[List[Schema.ProvisionedResource]] = None,
	  /** Output only. SLO metadata for instance classification in the Standardized dataplane SLO platform. See go/cloud-ssa-standard-slo for feature description. */
		sloMetadata: Option[Schema.SloMetadata] = None,
	  /** The MaintenanceSchedule contains the scheduling information of published maintenance schedule with same key as software_versions. */
		maintenanceSchedules: Option[Map[String, Schema.MaintenanceSchedule]] = None,
	  /** consumer_defined_name is the name of the instance set by the service consumers. Generally this is different from the `name` field which reperesents the system-assigned id of the instance which the service consumers do not recognize. This is a required field for tenants onboarding to Maintenance Window notifications (go/slm-rollout-maintenance-policies#prerequisites). */
		consumerDefinedName: Option[String] = None,
	  /** Link to the SLM instance template. Only populated when updating SLM instances via SSA's Actuation service adaptor. Service producers with custom control plane (e.g. Cloud SQL) doesn't need to populate this field. Instead they should use software_versions. */
		slmInstanceTemplate: Option[String] = None,
	  /** Optional. The MaintenanceSettings associated with instance. */
		maintenanceSettings: Option[Schema.MaintenanceSettings] = None,
	  /** Optional. The instance_type of this instance of format: projects/{project_number}/locations/{location_id}/instanceTypes/{instance_type_id}. Instance Type represents a high-level tier or SKU of the service that this instance belong to. When enabled(eg: Maintenance Rollout), Rollout uses 'instance_type' along with 'software_versions' to determine whether instance needs an update or not. */
		instanceType: Option[String] = None,
	  /** Optional. notification_parameter are information that service producers may like to include that is not relevant to Rollout. This parameter will only be passed to Gamma and Cloud Logging for notification/logging purpose. */
		notificationParameters: Option[Map[String, Schema.NotificationParameter]] = None
	)
	
	case class ProvisionedResource(
	  /** Type of the resource. This can be either a GCP resource or a custom one (e.g. another cloud provider's VM). For GCP compute resources use singular form of the names listed in GCP compute API documentation (https://cloud.google.com/compute/docs/reference/rest/v1/), prefixed with 'compute-', for example: 'compute-instance', 'compute-disk', 'compute-autoscaler'. */
		resourceType: Option[String] = None,
	  /** URL identifying the resource, e.g. "https://www.googleapis.com/compute/v1/projects/...)". */
		resourceUrl: Option[String] = None
	)
	
	case class SloMetadata(
	  /** Name of the SLO tier the Instance belongs to. This name will be expected to match the tiers specified in the service SLO configuration. Field is mandatory and must not be empty. */
		tier: Option[String] = None,
	  /** Optional. List of nodes. Some producers need to use per-node metadata to calculate SLO. This field allows such producers to publish per-node SLO meta data, which will be consumed by SSA Eligibility Exporter and published in the form of per node metric to Monarch. */
		nodes: Option[List[Schema.NodeSloMetadata]] = None,
	  /** Optional. Multiple per-instance SLI eligibilities which apply for individual SLIs. */
		perSliEligibility: Option[Schema.PerSliSloEligibility] = None
	)
	
	case class NodeSloMetadata(
	  /** The id of the node. This should be equal to SaasInstanceNode.node_id. */
		nodeId: Option[String] = None,
	  /** The location of the node, if different from instance location. */
		location: Option[String] = None,
	  /** If present, this will override eligibility for the node coming from instance or exclusions for specified SLIs. */
		perSliEligibility: Option[Schema.PerSliSloEligibility] = None
	)
	
	case class PerSliSloEligibility(
	  /** An entry in the eligibilities map specifies an eligibility for a particular SLI for the given instance. The SLI key in the name must be a valid SLI name specified in the Eligibility Exporter binary flags otherwise an error will be emitted by Eligibility Exporter and the oncaller will be alerted. If an SLI has been defined in the binary flags but the eligibilities map does not contain it, the corresponding SLI time series will not be emitted by the Eligibility Exporter. This ensures a smooth rollout and compatibility between the data produced by different versions of the Eligibility Exporters. If eligibilities map contains a key for an SLI which has not been declared in the binary flags, there will be an error message emitted in the Eligibility Exporter log and the metric for the SLI in question will not be emitted. */
		eligibilities: Option[Map[String, Schema.SloEligibility]] = None
	)
	
	case class SloEligibility(
	  /** Whether an instance is eligible or ineligible. */
		eligible: Option[Boolean] = None,
	  /** User-defined reason for the current value of instance eligibility. Usually, this can be directly mapped to the internal state. An empty reason is allowed. */
		reason: Option[String] = None
	)
	
	case class MaintenanceSchedule(
	  /** The scheduled start time for the maintenance. */
		startTime: Option[String] = None,
	  /** The scheduled end time for the maintenance. */
		endTime: Option[String] = None,
	  /** This field is deprecated, and will be always set to true since reschedule can happen multiple times now. This field should not be removed until all service producers remove this for their customers. */
		canReschedule: Option[Boolean] = None,
	  /** The rollout management policy this maintenance schedule is associated with. When doing reschedule update request, the reschedule should be against this given policy. */
		rolloutManagementPolicy: Option[String] = None,
	  /** schedule_deadline_time is the time deadline any schedule start time cannot go beyond, including reschedule. It's normally the initial schedule start time plus maintenance window length (1 day or 1 week). Maintenance cannot be scheduled to start beyond this deadline. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class MaintenanceSettings(
	  /** Optional. Exclude instance from maintenance. When true, rollout service will not attempt maintenance on the instance. Rollout service will include the instance in reported rollout progress as not attempted. */
		exclude: Option[Boolean] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the embedded policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_policy_names is set. If only the name is needed, then only populate MaintenancePolicy.name. */
		maintenancePolicies: Option[Map[String, Schema.MaintenancePolicy]] = None,
	  /** Optional. If the update call is triggered from rollback, set the value as true. */
		isRollback: Option[Boolean] = None
	)
	
	object MaintenancePolicy {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, DELETING }
	}
	case class MaintenancePolicy(
	  /** Required. MaintenancePolicy name using the form: `projects/{project_id}/locations/{location_id}/maintenancePolicies/{maintenance_policy_id}` where {project_id} refers to a GCP consumer project ID, {location_id} refers to a GCP region/zone, {maintenance_policy_id} must be 1-63 characters long and match the regular expression `[a-z0-9]([-a-z0-9]&#42;[a-z0-9])?`. */
		name: Option[String] = None,
	  /** Output only. The time when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The state of the policy. */
		state: Option[Schema.MaintenancePolicy.StateEnum] = None,
	  /** Maintenance policy applicable to instance update. */
		updatePolicy: Option[Schema.UpdatePolicy] = None
	)
	
	object UpdatePolicy {
		enum ChannelEnum extends Enum[ChannelEnum] { case UPDATE_CHANNEL_UNSPECIFIED, EARLIER, LATER, WEEK1, WEEK2, WEEK5 }
	}
	case class UpdatePolicy(
	  /** Optional. Maintenance window that is applied to resources covered by this policy. */
		window: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. Relative scheduling channel applied to resource. */
		channel: Option[Schema.UpdatePolicy.ChannelEnum] = None,
	  /** Deny Maintenance Period that is applied to resource to indicate when maintenance is forbidden. The protocol supports zero-to-many such periods, but the current SLM Rollout implementation only supports zero-to-one. */
		denyMaintenancePeriods: Option[List[Schema.DenyMaintenancePeriod]] = None
	)
	
	case class MaintenanceWindow(
	  /** Daily cycle. */
		dailyCycle: Option[Schema.DailyCycle] = None,
	  /** Weekly cycle. */
		weeklyCycle: Option[Schema.WeeklyCycle] = None
	)
	
	case class DailyCycle(
	  /** Time within the day to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
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
	
	case class WeeklyCycle(
	  /** User can specify multiple windows in a week. Minimum of 1 window. */
		schedule: Option[List[Schema.Schedule]] = None
	)
	
	object Schedule {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class Schedule(
	  /** Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.Schedule.DayEnum] = None,
	  /** Time within the window to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** Deny period start date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be the same or after the start. */
		startDate: Option[Schema.Date] = None,
	  /** Deny period end date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be before the end. */
		endDate: Option[Schema.Date] = None,
	  /** Time in UTC when the Blackout period starts on start_date and ends on end_date. This can be: &#42; Full time. &#42; All zeros for 00:00:00 UTC */
		time: Option[Schema.TimeOfDay] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class NotificationParameter(
	  /** Optional. Array of string values. e.g. instance's replica information. */
		values: Option[List[String]] = None
	)
}
