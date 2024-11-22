package com.boresjo.play.api.google.dataplex

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the name should be a resource name ending with operations/{unique_id}. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is false, it means the operation is still in progress. If true, the operation is completed, and either error or response is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as Delete, the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type XxxResponse, where Xxx is the original method name. For example, if the original method name is TakeSnapshot(), the inferred response type is TakeSnapshotResponse. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleCloudDataplexV1EntryType(
	  /** Output only. The relative resource name of the EntryType, of the form: projects/{project_number}/locations/{location_id}/entryTypes/{entry_type_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the EntryType. This ID will be different if the EntryType is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the EntryType was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the EntryType was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the EntryType. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the EntryType. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. This checksum is computed by the service, and might be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Indicates the classes this Entry Type belongs to, for example, TABLE, DATABASE, MODEL. */
		typeAliases: Option[List[String]] = None,
	  /** Optional. The platform that Entries of this type belongs to. */
		platform: Option[String] = None,
	  /** Optional. The system that Entries of this type belongs to. Examples include CloudSQL, MariaDB etc */
		system: Option[String] = None,
	  /** AspectInfo for the entry type. */
		requiredAspects: Option[List[Schema.GoogleCloudDataplexV1EntryTypeAspectInfo]] = None,
	  /** Immutable. Authorization defined for this type. */
		authorization: Option[Schema.GoogleCloudDataplexV1EntryTypeAuthorization] = None
	)
	
	case class GoogleCloudDataplexV1EntryTypeAspectInfo(
	  /** Required aspect type for the entry type. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1EntryTypeAuthorization(
	  /** Immutable. The IAM permission grantable on the Entry Group to allow access to instantiate Entries of Dataplex owned Entry Types, only settable for Dataplex owned Types. */
		alternateUsePermission: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListEntryTypesResponse(
	  /** EntryTypes under the given parent location. */
		entryTypes: Option[List[Schema.GoogleCloudDataplexV1EntryType]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that the service couldn't reach. */
		unreachableLocations: Option[List[String]] = None
	)
	
	object GoogleCloudDataplexV1AspectType {
		enum TransferStatusEnum extends Enum[TransferStatusEnum] { case TRANSFER_STATUS_UNSPECIFIED, TRANSFER_STATUS_MIGRATED, TRANSFER_STATUS_TRANSFERRED }
	}
	case class GoogleCloudDataplexV1AspectType(
	  /** Output only. The relative resource name of the AspectType, of the form: projects/{project_number}/locations/{location_id}/aspectTypes/{aspect_type_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the AspectType. If you delete and recreate the AspectType with the same name, then this ID will be different. */
		uid: Option[String] = None,
	  /** Output only. The time when the AspectType was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the AspectType was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the AspectType. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the AspectType. */
		labels: Option[Map[String, String]] = None,
	  /** The service computes this checksum. The client may send it on update and delete requests to ensure it has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Immutable. Defines the Authorization for this type. */
		authorization: Option[Schema.GoogleCloudDataplexV1AspectTypeAuthorization] = None,
	  /** Required. MetadataTemplate of the aspect. */
		metadataTemplate: Option[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplate] = None,
	  /** Output only. Denotes the transfer status of the Aspect Type. It is unspecified for Aspect Types created from Dataplex API. */
		transferStatus: Option[Schema.GoogleCloudDataplexV1AspectType.TransferStatusEnum] = None
	)
	
	case class GoogleCloudDataplexV1AspectTypeAuthorization(
	  /** Immutable. The IAM permission grantable on the EntryGroup to allow access to instantiate Aspects of Dataplex owned AspectTypes, only settable for Dataplex owned Types. */
		alternateUsePermission: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AspectTypeMetadataTemplate(
	  /** Optional. Index is used to encode Template messages. The value of index can range between 1 and 2,147,483,647. Index must be unique within all fields in a Template. (Nested Templates can reuse indexes). Once a Template is defined, the index cannot be changed, because it identifies the field in the actual storage format. Index is a mandatory field, but it is optional for top level fields, and map/array "values" definitions. */
		index: Option[Int] = None,
	  /** Required. The name of the field. */
		name: Option[String] = None,
	  /** Required. The datatype of this field. The following values are supported:Primitive types: string integer boolean double datetime. Must be of the format RFC3339 UTC "Zulu" (Examples: "2014-10-02T15:01:23Z" and "2014-10-02T15:01:23.045123456Z").Complex types: enum array map record */
		`type`: Option[String] = None,
	  /** Optional. Field definition. You must specify it if the type is record. It defines the nested fields. */
		recordFields: Option[List[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplate]] = None,
	  /** Optional. The list of values for an enum type. You must define it if the type is enum. */
		enumValues: Option[List[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplateEnumValue]] = None,
	  /** Optional. If the type is map, set map_items. map_items can refer to a primitive field or a complex (record only) field. To specify a primitive field, you only need to set name and type in the nested MetadataTemplate. The recommended value for the name field is item, as this isn't used in the actual payload. */
		mapItems: Option[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplate] = None,
	  /** Optional. If the type is array, set array_items. array_items can refer to a primitive field or a complex (record only) field. To specify a primitive field, you only need to set name and type in the nested MetadataTemplate. The recommended value for the name field is item, as this isn't used in the actual payload. */
		arrayItems: Option[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplate] = None,
	  /** Optional. You can use type id if this definition of the field needs to be reused later. The type id must be unique across the entire template. You can only specify it if the field type is record. */
		typeId: Option[String] = None,
	  /** Optional. A reference to another field definition (not an inline definition). The value must be equal to the value of an id field defined elsewhere in the MetadataTemplate. Only fields with record type can refer to other fields. */
		typeRef: Option[String] = None,
	  /** Optional. Specifies the constraints on this field. */
		constraints: Option[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplateConstraints] = None,
	  /** Optional. Specifies annotations on this field. */
		annotations: Option[Schema.GoogleCloudDataplexV1AspectTypeMetadataTemplateAnnotations] = None
	)
	
	case class GoogleCloudDataplexV1AspectTypeMetadataTemplateEnumValue(
	  /** Required. Index for the enum value. It can't be modified. */
		index: Option[Int] = None,
	  /** Required. Name of the enumvalue. This is the actual value that the aspect can contain. */
		name: Option[String] = None,
	  /** Optional. You can set this message if you need to deprecate an enum value. */
		deprecated: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AspectTypeMetadataTemplateConstraints(
	  /** Optional. Marks this field as optional or required. */
		required: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1AspectTypeMetadataTemplateAnnotations(
	  /** Optional. Marks a field as deprecated. You can include a deprecation message. */
		deprecated: Option[String] = None,
	  /** Optional. Display name for a field. */
		displayName: Option[String] = None,
	  /** Optional. Description for a field. */
		description: Option[String] = None,
	  /** Optional. Display order for a field. You can use this to reorder where a field is rendered. */
		displayOrder: Option[Int] = None,
	  /** Optional. You can use String Type annotations to specify special meaning to string fields. The following values are supported: richText: The field must be interpreted as a rich text field. url: A fully qualified URL link. resource: A service qualified resource reference. */
		stringType: Option[String] = None,
	  /** Optional. Suggested hints for string fields. You can use them to suggest values to users through console. */
		stringValues: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListAspectTypesResponse(
	  /** AspectTypes under the given parent location. */
		aspectTypes: Option[List[Schema.GoogleCloudDataplexV1AspectType]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that the service couldn't reach. */
		unreachableLocations: Option[List[String]] = None
	)
	
	object GoogleCloudDataplexV1EntryGroup {
		enum TransferStatusEnum extends Enum[TransferStatusEnum] { case TRANSFER_STATUS_UNSPECIFIED, TRANSFER_STATUS_MIGRATED, TRANSFER_STATUS_TRANSFERRED }
	}
	case class GoogleCloudDataplexV1EntryGroup(
	  /** Output only. The relative resource name of the EntryGroup, in the format projects/{project_id_or_number}/locations/{location_id}/entryGroups/{entry_group_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the EntryGroup. If you delete and recreate the EntryGroup with the same name, this ID will be different. */
		uid: Option[String] = None,
	  /** Output only. The time when the EntryGroup was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the EntryGroup was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the EntryGroup. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the EntryGroup. */
		labels: Option[Map[String, String]] = None,
	  /** This checksum is computed by the service, and might be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. Denotes the transfer status of the Entry Group. It is unspecified for Entry Group created from Dataplex API. */
		transferStatus: Option[Schema.GoogleCloudDataplexV1EntryGroup.TransferStatusEnum] = None
	)
	
	case class GoogleCloudDataplexV1ListEntryGroupsResponse(
	  /** Entry groups under the given parent location. */
		entryGroups: Option[List[Schema.GoogleCloudDataplexV1EntryGroup]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that the service couldn't reach. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1Entry(
	  /** Identifier. The relative resource name of the entry, in the format projects/{project_id_or_number}/locations/{location_id}/entryGroups/{entry_group_id}/entries/{entry_id}. */
		name: Option[String] = None,
	  /** Required. Immutable. The relative resource name of the entry type that was used to create this entry, in the format projects/{project_id_or_number}/locations/{location_id}/entryTypes/{entry_type_id}. */
		entryType: Option[String] = None,
	  /** Output only. The time when the entry was created in Dataplex. */
		createTime: Option[String] = None,
	  /** Output only. The time when the entry was last updated in Dataplex. */
		updateTime: Option[String] = None,
	  /** Optional. The aspects that are attached to the entry. Depending on how the aspect is attached to the entry, the format of the aspect key can be one of the following: If the aspect is attached directly to the entry: {project_id_or_number}.{location_id}.{aspect_type_id} If the aspect is attached to an entry's path: {project_id_or_number}.{location_id}.{aspect_type_id}@{path} */
		aspects: Option[Map[String, Schema.GoogleCloudDataplexV1Aspect]] = None,
	  /** Optional. Immutable. The resource name of the parent entry. */
		parentEntry: Option[String] = None,
	  /** Optional. A name for the entry that can be referenced by an external system. For more information, see Fully qualified names (https://cloud.google.com/data-catalog/docs/fully-qualified-names). The maximum size of the field is 4000 characters. */
		fullyQualifiedName: Option[String] = None,
	  /** Optional. Information related to the source system of the data resource that is represented by the entry. */
		entrySource: Option[Schema.GoogleCloudDataplexV1EntrySource] = None
	)
	
	case class GoogleCloudDataplexV1Aspect(
	  /** Output only. The resource name of the type used to create this Aspect. */
		aspectType: Option[String] = None,
	  /** Output only. The path in the entry under which the aspect is attached. */
		path: Option[String] = None,
	  /** Output only. The time when the Aspect was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the Aspect was last updated. */
		updateTime: Option[String] = None,
	  /** Required. The content of the aspect, according to its aspect type schema. The maximum size of the field is 120KB (encoded as UTF-8). */
		data: Option[Map[String, JsValue]] = None,
	  /** Optional. Information related to the source system of the aspect. */
		aspectSource: Option[Schema.GoogleCloudDataplexV1AspectSource] = None
	)
	
	case class GoogleCloudDataplexV1AspectSource(
	  /** The time the aspect was created in the source system. */
		createTime: Option[String] = None,
	  /** The time the aspect was last updated in the source system. */
		updateTime: Option[String] = None,
	  /** The version of the data format used to produce this data. This field is used to indicated when the underlying data format changes (e.g., schema modifications, changes to the source URL format definition, etc). */
		dataVersion: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1EntrySource(
	  /** The name of the resource in the source system. Maximum length is 4,000 characters. */
		resource: Option[String] = None,
	  /** The name of the source system. Maximum length is 64 characters. */
		system: Option[String] = None,
	  /** The platform containing the source system. Maximum length is 64 characters. */
		platform: Option[String] = None,
	  /** A user-friendly display name. Maximum length is 500 characters. */
		displayName: Option[String] = None,
	  /** A description of the data resource. Maximum length is 2,000 characters. */
		description: Option[String] = None,
	  /** User-defined labels. The maximum size of keys and values is 128 characters each. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The entries representing the ancestors of the data resource in the source system. */
		ancestors: Option[List[Schema.GoogleCloudDataplexV1EntrySourceAncestor]] = None,
	  /** The time when the resource was created in the source system. */
		createTime: Option[String] = None,
	  /** The time when the resource was last updated in the source system. If the entry exists in the system and its EntrySource has update_time populated, further updates to the EntrySource of the entry must provide incremental updates to its update_time. */
		updateTime: Option[String] = None,
	  /** Output only. Location of the resource in the source system. You can search the entry by this location. By default, this should match the location of the entry group containing this entry. A different value allows capturing the source location for data external to Google Cloud. */
		location: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1EntrySourceAncestor(
	  /** Optional. The name of the ancestor resource. */
		name: Option[String] = None,
	  /** Optional. The type of the ancestor resource. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListEntriesResponse(
	  /** The list of entries under the given parent location. */
		entries: Option[List[Schema.GoogleCloudDataplexV1Entry]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1SearchEntriesResponse(
	  /** The results matching the search query. */
		results: Option[List[Schema.GoogleCloudDataplexV1SearchEntriesResult]] = None,
	  /** The estimated total number of matching entries. This number isn't guaranteed to be accurate. */
		totalSize: Option[Int] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that the service couldn't reach. Search results don't include data from these locations. */
		unreachable: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1SearchEntriesResult(
	  /** Linked resource name. */
		linkedResource: Option[String] = None,
		dataplexEntry: Option[Schema.GoogleCloudDataplexV1Entry] = None,
	  /** Snippets. */
		snippets: Option[Schema.GoogleCloudDataplexV1SearchEntriesResultSnippets] = None
	)
	
	case class GoogleCloudDataplexV1SearchEntriesResultSnippets(
	  /** Entry */
		dataplexEntry: Option[Schema.GoogleCloudDataplexV1Entry] = None
	)
	
	object GoogleCloudDataplexV1MetadataJob {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, IMPORT }
	}
	case class GoogleCloudDataplexV1MetadataJob(
	  /** Output only. Identifier. The name of the resource that the configuration is applied to, in the format projects/{project_number}/locations/{location_id}/metadataJobs/{metadata_job_id}. */
		name: Option[String] = None,
	  /** Output only. A system-generated, globally unique ID for the metadata job. If the metadata job is deleted and then re-created with the same name, this ID is different. */
		uid: Option[String] = None,
	  /** Output only. The time when the metadata job was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the metadata job was updated. */
		updateTime: Option[String] = None,
	  /** Optional. User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Metadata job type. */
		`type`: Option[Schema.GoogleCloudDataplexV1MetadataJob.TypeEnum] = None,
	  /** Import job specification. */
		importSpec: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobSpec] = None,
	  /** Output only. Import job result. */
		importResult: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobResult] = None,
	  /** Output only. Metadata job status. */
		status: Option[Schema.GoogleCloudDataplexV1MetadataJobStatus] = None
	)
	
	object GoogleCloudDataplexV1MetadataJobImportJobSpec {
		enum EntrySyncModeEnum extends Enum[EntrySyncModeEnum] { case SYNC_MODE_UNSPECIFIED, FULL, INCREMENTAL }
		enum AspectSyncModeEnum extends Enum[AspectSyncModeEnum] { case SYNC_MODE_UNSPECIFIED, FULL, INCREMENTAL }
		enum LogLevelEnum extends Enum[LogLevelEnum] { case LOG_LEVEL_UNSPECIFIED, DEBUG, INFO }
	}
	case class GoogleCloudDataplexV1MetadataJobImportJobSpec(
	  /** Optional. The URI of a Cloud Storage bucket or folder (beginning with gs:// and ending with /) that contains the metadata import files for this job.A metadata import file defines the values to set for each of the entries and aspects in a metadata job. For more information about how to create a metadata import file and the file requirements, see Metadata import file (https://cloud.google.com/dataplex/docs/import-metadata#metadata-import-file).You can provide multiple metadata import files in the same metadata job. The bucket or folder must contain at least one metadata import file, in JSON Lines format (either .json or .jsonl file extension).In FULL entry sync mode, don't save the metadata import file in a folder named SOURCE_STORAGE_URI/deletions/.Caution: If the metadata import file contains no data, all entries and aspects that belong to the job's scope are deleted. */
		sourceStorageUri: Option[String] = None,
	  /** Optional. The time when the process that created the metadata import files began. */
		sourceCreateTime: Option[String] = None,
	  /** Required. A boundary on the scope of impact that the metadata import job can have. */
		scope: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobSpecImportJobScope] = None,
	  /** Required. The sync mode for entries. Only FULL mode is supported for entries. All entries in the job's scope are modified. If an entry exists in Dataplex but isn't included in the metadata import file, the entry is deleted when you run the metadata job. */
		entrySyncMode: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobSpec.EntrySyncModeEnum] = None,
	  /** Required. The sync mode for aspects. Only INCREMENTAL mode is supported for aspects. An aspect is modified only if the metadata import file includes a reference to the aspect in the update_mask field and the aspect_keys field. */
		aspectSyncMode: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobSpec.AspectSyncModeEnum] = None,
	  /** Optional. The level of logs to write to Cloud Logging for this job.Debug-level logs provide highly-detailed information for troubleshooting, but their increased verbosity could incur additional costs (https://cloud.google.com/stackdriver/pricing) that might not be merited for all jobs.If unspecified, defaults to INFO. */
		logLevel: Option[Schema.GoogleCloudDataplexV1MetadataJobImportJobSpec.LogLevelEnum] = None
	)
	
	case class GoogleCloudDataplexV1MetadataJobImportJobSpecImportJobScope(
	  /** Required. The entry group that is in scope for the import job, specified as a relative resource name in the format projects/{project_number_or_id}/locations/{location_id}/entryGroups/{entry_group_id}. Only entries that belong to the specified entry group are affected by the job.Must contain exactly one element. The entry group and the job must be in the same location. */
		entryGroups: Option[List[String]] = None,
	  /** Required. The entry types that are in scope for the import job, specified as relative resource names in the format projects/{project_number_or_id}/locations/{location_id}/entryTypes/{entry_type_id}. The job modifies only the entries that belong to these entry types.If the metadata import file attempts to modify an entry whose type isn't included in this list, the import job is halted before modifying any entries or aspects.The location of an entry type must either match the location of the job, or the entry type must be global. */
		entryTypes: Option[List[String]] = None,
	  /** Optional. The aspect types that are in scope for the import job, specified as relative resource names in the format projects/{project_number_or_id}/locations/{location_id}/aspectTypes/{aspect_type_id}. The job modifies only the aspects that belong to these aspect types.If the metadata import file attempts to modify an aspect whose type isn't included in this list, the import job is halted before modifying any entries or aspects.The location of an aspect type must either match the location of the job, or the aspect type must be global. */
		aspectTypes: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1MetadataJobImportJobResult(
	  /** Output only. The total number of entries that were deleted. */
		deletedEntries: Option[String] = None,
	  /** Output only. The total number of entries that were updated. */
		updatedEntries: Option[String] = None,
	  /** Output only. The total number of entries that were created. */
		createdEntries: Option[String] = None,
	  /** Output only. The total number of entries that were unchanged. */
		unchangedEntries: Option[String] = None,
	  /** Output only. The total number of entries that were recreated. */
		recreatedEntries: Option[String] = None,
	  /** Output only. The time when the status was updated. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudDataplexV1MetadataJobStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, QUEUED, RUNNING, CANCELING, CANCELED, SUCCEEDED, FAILED, SUCCEEDED_WITH_ERRORS }
	}
	case class GoogleCloudDataplexV1MetadataJobStatus(
	  /** Output only. State of the metadata job. */
		state: Option[Schema.GoogleCloudDataplexV1MetadataJobStatus.StateEnum] = None,
	  /** Output only. Message relating to the progression of a metadata job. */
		message: Option[String] = None,
	  /** Output only. Progress tracking. */
		completionPercent: Option[Int] = None,
	  /** Output only. The time when the status was updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListMetadataJobsResponse(
	  /** Metadata jobs under the specified parent location. */
		metadataJobs: Option[List[Schema.GoogleCloudDataplexV1MetadataJob]] = None,
	  /** A token to retrieve the next page of results. If there are no more results in the list, the value is empty. */
		nextPageToken: Option[String] = None,
	  /** Locations that the service couldn't reach. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1CancelMetadataJobRequest(
	
	)
	
	case class GoogleIamV1SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the resource. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used:paths: "bindings, etag" */
		updateMask: Option[String] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy.Valid values are 0, 1, and 3. Requests that specify an invalid value are rejected.Any operation that affects conditional role bindings must specify version 3. This requirement applies to the following operations: Getting a policy that includes a conditional role binding Adding a conditional role binding to a policy Changing a conditional role binding in a policy Removing any role binding, with or without a condition, from a policy that includes conditionsImportant: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost.If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of members, or principals, with a role. Optionally, may specify a condition that determines how and when the bindings are applied. Each of the bindings must contain at least one principal.The bindings in a Policy can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the bindings grant 50 different roles to user:alice@example.com, and not to any other principal, then you can add another 1,450 principals to the bindings in the Policy. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An etag is returned in the response to getIamPolicy, and systems are expected to put that etag in the request to setIamPolicy to ensure that their change will be applied to the same version of the policy.Important: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost. */
		etag: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
	  /** Role that is assigned to the list of members, or principals. For example, roles/viewer, roles/editor, or roles/owner.For an overview of the IAM roles and permissions, see the IAM documentation (https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see here (https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. members can have the following values: allUsers: A special identifier that represents anyone who is on the internet; with or without a Google account. allAuthenticatedUsers: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. user:{emailid}: An email address that represents a specific Google account. For example, alice@example.com . serviceAccount:{emailid}: An email address that represents a Google service account. For example, my-other-app@appspot.gserviceaccount.com. serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]: An identifier for a Kubernetes service account (https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, my-project.svc.id.goog[my-namespace/my-kubernetes-sa]. group:{emailid}: An email address that represents a Google group. For example, admins@example.com. domain:{domain}: The G Suite domain (primary) that represents all the users of that domain. For example, google.com or example.com. principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workforce identity pool. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}: All workforce identities in a group. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All workforce identities with a specific attribute value. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;: All identities in a workforce identity pool. principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workload identity pool. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}: A workload identity pool group. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All identities in a workload identity pool with a certain attribute. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;: All identities in a workload identity pool. deleted:user:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a user that has been recently deleted. For example, alice@example.com?uid=123456789012345678901. If the user is recovered, this value reverts to user:{emailid} and the recovered user retains the role in the binding. deleted:serviceAccount:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901. If the service account is undeleted, this value reverts to serviceAccount:{emailid} and the undeleted service account retains the role in the binding. deleted:group:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, admins@example.com?uid=123456789012345678901. If the group is recovered, this value reverts to group:{emailid} and the recovered group retains the role in the binding. deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: Deleted single identity in a workforce identity pool. For example, deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding.If the condition evaluates to true, then this binding applies to the current request.If the condition evaluates to false, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleTypeExpr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class GoogleIamV1AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, storage.googleapis.com, cloudsql.googleapis.com. allServices is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsRequest(
	  /** The set of permissions to check for the resource. Permissions with wildcards (such as &#42; or storage.&#42;) are not allowed. For more information see IAM Overview (https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsResponse(
	  /** A subset of TestPermissionsRequest.permissions that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1Content(
	  /** Output only. The relative resource name of the content, of the form: projects/{project_id}/locations/{location_id}/lakes/{lake_id}/content/{content_id} */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the content. This ID will be different if the content is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Required. The path for the Content file, represented as directory structure. Unique within a lake. Limited to alphanumerics, hyphens, underscores, dots and slashes. */
		path: Option[String] = None,
	  /** Output only. Content creation time. */
		createTime: Option[String] = None,
	  /** Output only. The time when the content was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User defined labels for the content. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the content. */
		description: Option[String] = None,
	  /** Required. Content data in string format. */
		dataText: Option[String] = None,
	  /** Sql Script related configurations. */
		sqlScript: Option[Schema.GoogleCloudDataplexV1ContentSqlScript] = None,
	  /** Notebook related configurations. */
		notebook: Option[Schema.GoogleCloudDataplexV1ContentNotebook] = None
	)
	
	object GoogleCloudDataplexV1ContentSqlScript {
		enum EngineEnum extends Enum[EngineEnum] { case QUERY_ENGINE_UNSPECIFIED, SPARK }
	}
	case class GoogleCloudDataplexV1ContentSqlScript(
	  /** Required. Query Engine to be used for the Sql Query. */
		engine: Option[Schema.GoogleCloudDataplexV1ContentSqlScript.EngineEnum] = None
	)
	
	object GoogleCloudDataplexV1ContentNotebook {
		enum KernelTypeEnum extends Enum[KernelTypeEnum] { case KERNEL_TYPE_UNSPECIFIED, PYTHON3 }
	}
	case class GoogleCloudDataplexV1ContentNotebook(
	  /** Required. Kernel Type of the notebook. */
		kernelType: Option[Schema.GoogleCloudDataplexV1ContentNotebook.KernelTypeEnum] = None
	)
	
	case class GoogleCloudDataplexV1ListContentResponse(
	  /** Content under the given parent lake. */
		content: Option[List[Schema.GoogleCloudDataplexV1Content]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataTaxonomy(
	  /** Output only. The relative resource name of the DataTaxonomy, of the form: projects/{project_number}/locations/{location_id}/dataTaxonomies/{data_taxonomy_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the dataTaxonomy. This ID will be different if the DataTaxonomy is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the DataTaxonomy was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the DataTaxonomy was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the DataTaxonomy. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the DataTaxonomy. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The number of attributes in the DataTaxonomy. */
		attributeCount: Option[Int] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. The number of classes in the DataTaxonomy. */
		classCount: Option[Int] = None
	)
	
	case class GoogleCloudDataplexV1ListDataTaxonomiesResponse(
	  /** DataTaxonomies under the given parent location. */
		dataTaxonomies: Option[List[Schema.GoogleCloudDataplexV1DataTaxonomy]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataAttributeBinding(
	  /** Output only. The relative resource name of the Data Attribute Binding, of the form: projects/{project_number}/locations/{location}/dataAttributeBindings/{data_attribute_binding_id} */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the DataAttributeBinding. This ID will be different if the DataAttributeBinding is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the DataAttributeBinding was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the DataAttributeBinding was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the DataAttributeBinding. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the DataAttributeBinding. */
		labels: Option[Map[String, String]] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Etags must be used when calling the DeleteDataAttributeBinding and the UpdateDataAttributeBinding method. */
		etag: Option[String] = None,
	  /** Optional. Immutable. The resource name of the resource that is associated to attributes. Presently, only entity resource is supported in the form: projects/{project}/locations/{location}/lakes/{lake}/zones/{zone}/entities/{entity_id} Must belong in the same project and region as the attribute binding, and there can only exist one active binding for a resource. */
		resource: Option[String] = None,
	  /** Optional. List of attributes to be associated with the resource, provided in the form: projects/{project}/locations/{location}/dataTaxonomies/{dataTaxonomy}/attributes/{data_attribute_id} */
		attributes: Option[List[String]] = None,
	  /** Optional. The list of paths for items within the associated resource (eg. columns and partitions within a table) along with attribute bindings. */
		paths: Option[List[Schema.GoogleCloudDataplexV1DataAttributeBindingPath]] = None
	)
	
	case class GoogleCloudDataplexV1DataAttributeBindingPath(
	  /** Required. The name identifier of the path. Nested columns should be of the form: 'address.city'. */
		name: Option[String] = None,
	  /** Optional. List of attributes to be associated with the path of the resource, provided in the form: projects/{project}/locations/{location}/dataTaxonomies/{dataTaxonomy}/attributes/{data_attribute_id} */
		attributes: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListDataAttributeBindingsResponse(
	  /** DataAttributeBindings under the given parent Location. */
		dataAttributeBindings: Option[List[Schema.GoogleCloudDataplexV1DataAttributeBinding]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataAttribute(
	  /** Output only. The relative resource name of the dataAttribute, of the form: projects/{project_number}/locations/{location_id}/dataTaxonomies/{dataTaxonomy}/attributes/{data_attribute_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the DataAttribute. This ID will be different if the DataAttribute is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the DataAttribute was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the DataAttribute was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the DataAttribute. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the DataAttribute. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The ID of the parent DataAttribute resource, should belong to the same data taxonomy. Circular dependency in parent chain is not valid. Maximum depth of the hierarchy allowed is 4. a -> b -> c -> d -> e, depth = 4 */
		parentId: Option[String] = None,
	  /** Output only. The number of child attributes present for this attribute. */
		attributeCount: Option[Int] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Specified when applied to a resource (eg: Cloud Storage bucket, BigQuery dataset, BigQuery table). */
		resourceAccessSpec: Option[Schema.GoogleCloudDataplexV1ResourceAccessSpec] = None,
	  /** Optional. Specified when applied to data stored on the resource (eg: rows, columns in BigQuery Tables). */
		dataAccessSpec: Option[Schema.GoogleCloudDataplexV1DataAccessSpec] = None
	)
	
	case class GoogleCloudDataplexV1ResourceAccessSpec(
	  /** Optional. The format of strings follows the pattern followed by IAM in the bindings. user:{email}, serviceAccount:{email} group:{email}. The set of principals to be granted reader role on the resource. */
		readers: Option[List[String]] = None,
	  /** Optional. The set of principals to be granted writer role on the resource. */
		writers: Option[List[String]] = None,
	  /** Optional. The set of principals to be granted owner role on the resource. */
		owners: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataAccessSpec(
	  /** Optional. The format of strings follows the pattern followed by IAM in the bindings. user:{email}, serviceAccount:{email} group:{email}. The set of principals to be granted reader role on data stored within resources. */
		readers: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListDataAttributesResponse(
	  /** DataAttributes under the given parent DataTaxonomy. */
		dataAttributes: Option[List[Schema.GoogleCloudDataplexV1DataAttribute]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachableLocations: Option[List[String]] = None
	)
	
	object GoogleCloudDataplexV1DataScan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
		enum TypeEnum extends Enum[TypeEnum] { case DATA_SCAN_TYPE_UNSPECIFIED, DATA_QUALITY, DATA_PROFILE, DATA_DISCOVERY }
	}
	case class GoogleCloudDataplexV1DataScan(
	  /** Output only. The relative resource name of the scan, of the form: projects/{project}/locations/{location_id}/dataScans/{datascan_id}, where project refers to a project_id or project_number and location_id refers to a GCP region. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the scan. This ID will be different if the scan is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Optional. Description of the scan. Must be between 1-1024 characters. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. Must be between 1-256 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-defined labels for the scan. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the DataScan. */
		state: Option[Schema.GoogleCloudDataplexV1DataScan.StateEnum] = None,
	  /** Output only. The time when the scan was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the scan was last updated. */
		updateTime: Option[String] = None,
	  /** Required. The data source for DataScan. */
		data: Option[Schema.GoogleCloudDataplexV1DataSource] = None,
	  /** Optional. DataScan execution settings.If not specified, the fields in it will use their default values. */
		executionSpec: Option[Schema.GoogleCloudDataplexV1DataScanExecutionSpec] = None,
	  /** Output only. Status of the data scan execution. */
		executionStatus: Option[Schema.GoogleCloudDataplexV1DataScanExecutionStatus] = None,
	  /** Output only. The type of DataScan. */
		`type`: Option[Schema.GoogleCloudDataplexV1DataScan.TypeEnum] = None,
	  /** Settings for a data quality scan. */
		dataQualitySpec: Option[Schema.GoogleCloudDataplexV1DataQualitySpec] = None,
	  /** Settings for a data profile scan. */
		dataProfileSpec: Option[Schema.GoogleCloudDataplexV1DataProfileSpec] = None,
	  /** Settings for a data discovery scan. */
		dataDiscoverySpec: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpec] = None,
	  /** Output only. The result of a data quality scan. */
		dataQualityResult: Option[Schema.GoogleCloudDataplexV1DataQualityResult] = None,
	  /** Output only. The result of a data profile scan. */
		dataProfileResult: Option[Schema.GoogleCloudDataplexV1DataProfileResult] = None,
	  /** Output only. The result of a data discovery scan. */
		dataDiscoveryResult: Option[Schema.GoogleCloudDataplexV1DataDiscoveryResult] = None
	)
	
	case class GoogleCloudDataplexV1DataSource(
	  /** Immutable. The Dataplex entity that represents the data source (e.g. BigQuery table) for DataScan, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}/entities/{entity_id}. */
		entity: Option[String] = None,
	  /** Immutable. The service-qualified full resource name of the cloud resource for a DataScan job to scan against. The field could be: BigQuery table of type "TABLE" for DataProfileScan/DataQualityScan Format: //bigquery.googleapis.com/projects/PROJECT_ID/datasets/DATASET_ID/tables/TABLE_ID */
		resource: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataScanExecutionSpec(
	  /** Optional. Spec related to how often and when a scan should be triggered.If not specified, the default is OnDemand, which means the scan will not run until the user calls RunDataScan API. */
		trigger: Option[Schema.GoogleCloudDataplexV1Trigger] = None,
	  /** Immutable. The unnested field (of type Date or Timestamp) that contains values which monotonically increase over time.If not specified, a data scan will run for all data in the table. */
		field: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1Trigger(
	  /** The scan runs once via RunDataScan API. */
		onDemand: Option[Schema.GoogleCloudDataplexV1TriggerOnDemand] = None,
	  /** The scan is scheduled to run periodically. */
		schedule: Option[Schema.GoogleCloudDataplexV1TriggerSchedule] = None
	)
	
	case class GoogleCloudDataplexV1TriggerOnDemand(
	
	)
	
	case class GoogleCloudDataplexV1TriggerSchedule(
	  /** Required. Cron (https://en.wikipedia.org/wiki/Cron) schedule for running scans periodically.To explicitly set a timezone in the cron tab, apply a prefix in the cron tab: "CRON_TZ=${IANA_TIME_ZONE}" or "TZ=${IANA_TIME_ZONE}". The ${IANA_TIME_ZONE} may only be a valid string from IANA time zone database (wikipedia (https://en.wikipedia.org/wiki/List_of_tz_database_time_zones#List)). For example, CRON_TZ=America/New_York 1 &#42; &#42; &#42; &#42;, or TZ=America/New_York 1 &#42; &#42; &#42; &#42;.This field is required for Schedule scans. */
		cron: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataScanExecutionStatus(
	  /** The time when the latest DataScanJob started. */
		latestJobStartTime: Option[String] = None,
	  /** The time when the latest DataScanJob ended. */
		latestJobEndTime: Option[String] = None,
	  /** Optional. The time when the DataScanJob execution was created. */
		latestJobCreateTime: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpec(
	  /** Required. The list of rules to evaluate against a data source. At least one rule is required. */
		rules: Option[List[Schema.GoogleCloudDataplexV1DataQualityRule]] = None,
	  /** Optional. The percentage of the records to be selected from the dataset for DataScan. Value can range between 0.0 and 100.0 with up to 3 significant decimal digits. Sampling is not applied if sampling_percent is not specified, 0 or 100. */
		samplingPercent: Option[BigDecimal] = None,
	  /** Optional. A filter applied to all rows in a single DataScan job. The filter needs to be a valid SQL expression for a WHERE clause in BigQuery standard SQL syntax. Example: col1 >= 0 AND col2 < 10 */
		rowFilter: Option[String] = None,
	  /** Optional. Actions to take upon job completion. */
		postScanActions: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActions] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRule(
	  /** Row-level rule which evaluates whether each column value lies between a specified range. */
		rangeExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleRangeExpectation] = None,
	  /** Row-level rule which evaluates whether each column value is null. */
		nonNullExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleNonNullExpectation] = None,
	  /** Row-level rule which evaluates whether each column value is contained by a specified set. */
		setExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleSetExpectation] = None,
	  /** Row-level rule which evaluates whether each column value matches a specified regex. */
		regexExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleRegexExpectation] = None,
	  /** Row-level rule which evaluates whether each column value is unique. */
		uniquenessExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleUniquenessExpectation] = None,
	  /** Aggregate rule which evaluates whether the column aggregate statistic lies between a specified range. */
		statisticRangeExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleStatisticRangeExpectation] = None,
	  /** Row-level rule which evaluates whether each row in a table passes the specified condition. */
		rowConditionExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleRowConditionExpectation] = None,
	  /** Aggregate rule which evaluates whether the provided expression is true for a table. */
		tableConditionExpectation: Option[Schema.GoogleCloudDataplexV1DataQualityRuleTableConditionExpectation] = None,
	  /** Aggregate rule which evaluates the number of rows returned for the provided statement. If any rows are returned, this rule fails. */
		sqlAssertion: Option[Schema.GoogleCloudDataplexV1DataQualityRuleSqlAssertion] = None,
	  /** Optional. The unnested column which this rule is evaluated against. */
		column: Option[String] = None,
	  /** Optional. Rows with null values will automatically fail a rule, unless ignore_null is true. In that case, such null rows are trivially considered passing.This field is only valid for the following type of rules: RangeExpectation RegexExpectation SetExpectation UniquenessExpectation */
		ignoreNull: Option[Boolean] = None,
	  /** Required. The dimension a rule belongs to. Results are also aggregated at the dimension level. Supported dimensions are "COMPLETENESS", "ACCURACY", "CONSISTENCY", "VALIDITY", "UNIQUENESS", "FRESHNESS", "VOLUME" */
		dimension: Option[String] = None,
	  /** Optional. The minimum ratio of passing_rows / total_rows required to pass this rule, with a range of 0.0, 1.0.0 indicates default value (i.e. 1.0).This field is only valid for row-level type rules. */
		threshold: Option[BigDecimal] = None,
	  /** Optional. A mutable name for the rule. The name must contain only letters (a-z, A-Z), numbers (0-9), or hyphens (-). The maximum length is 63 characters. Must start with a letter. Must end with a number or a letter. */
		name: Option[String] = None,
	  /** Optional. Description of the rule. The maximum length is 1,024 characters. */
		description: Option[String] = None,
	  /** Optional. Whether the Rule is active or suspended. Default is false. */
		suspended: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleRangeExpectation(
	  /** Optional. The minimum column value allowed for a row to pass this validation. At least one of min_value and max_value need to be provided. */
		minValue: Option[String] = None,
	  /** Optional. The maximum column value allowed for a row to pass this validation. At least one of min_value and max_value need to be provided. */
		maxValue: Option[String] = None,
	  /** Optional. Whether each value needs to be strictly greater than ('>') the minimum, or if equality is allowed.Only relevant if a min_value has been defined. Default = false. */
		strictMinEnabled: Option[Boolean] = None,
	  /** Optional. Whether each value needs to be strictly lesser than ('<') the maximum, or if equality is allowed.Only relevant if a max_value has been defined. Default = false. */
		strictMaxEnabled: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleNonNullExpectation(
	
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleSetExpectation(
	  /** Optional. Expected values for the column value. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleRegexExpectation(
	  /** Optional. A regular expression the column value is expected to match. */
		regex: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleUniquenessExpectation(
	
	)
	
	object GoogleCloudDataplexV1DataQualityRuleStatisticRangeExpectation {
		enum StatisticEnum extends Enum[StatisticEnum] { case STATISTIC_UNDEFINED, MEAN, MIN, MAX }
	}
	case class GoogleCloudDataplexV1DataQualityRuleStatisticRangeExpectation(
	  /** Optional. The aggregate metric to evaluate. */
		statistic: Option[Schema.GoogleCloudDataplexV1DataQualityRuleStatisticRangeExpectation.StatisticEnum] = None,
	  /** Optional. The minimum column statistic value allowed for a row to pass this validation.At least one of min_value and max_value need to be provided. */
		minValue: Option[String] = None,
	  /** Optional. The maximum column statistic value allowed for a row to pass this validation.At least one of min_value and max_value need to be provided. */
		maxValue: Option[String] = None,
	  /** Optional. Whether column statistic needs to be strictly greater than ('>') the minimum, or if equality is allowed.Only relevant if a min_value has been defined. Default = false. */
		strictMinEnabled: Option[Boolean] = None,
	  /** Optional. Whether column statistic needs to be strictly lesser than ('<') the maximum, or if equality is allowed.Only relevant if a max_value has been defined. Default = false. */
		strictMaxEnabled: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleRowConditionExpectation(
	  /** Optional. The SQL expression. */
		sqlExpression: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleTableConditionExpectation(
	  /** Optional. The SQL expression. */
		sqlExpression: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleSqlAssertion(
	  /** Optional. The SQL statement. */
		sqlStatement: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActions(
	  /** Optional. If set, results will be exported to the provided BigQuery table. */
		bigqueryExport: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsBigQueryExport] = None,
	  /** Optional. If set, results will be sent to the provided notification receipts upon triggers. */
		notificationReport: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsNotificationReport] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsBigQueryExport(
	  /** Optional. The BigQuery table to export DataQualityScan results to. Format: //bigquery.googleapis.com/projects/PROJECT_ID/datasets/DATASET_ID/tables/TABLE_ID */
		resultsTable: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsNotificationReport(
	  /** Required. The recipients who will receive the notification report. */
		recipients: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsRecipients] = None,
	  /** Optional. If set, report will be sent when score threshold is met. */
		scoreThresholdTrigger: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsScoreThresholdTrigger] = None,
	  /** Optional. If set, report will be sent when a scan job fails. */
		jobFailureTrigger: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsJobFailureTrigger] = None,
	  /** Optional. If set, report will be sent when a scan job ends. */
		jobEndTrigger: Option[Schema.GoogleCloudDataplexV1DataQualitySpecPostScanActionsJobEndTrigger] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsRecipients(
	  /** Optional. The email recipients who will receive the DataQualityScan results report. */
		emails: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsScoreThresholdTrigger(
	  /** Optional. The score range is in 0,100. */
		scoreThreshold: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsJobFailureTrigger(
	
	)
	
	case class GoogleCloudDataplexV1DataQualitySpecPostScanActionsJobEndTrigger(
	
	)
	
	case class GoogleCloudDataplexV1DataProfileSpec(
	  /** Optional. The percentage of the records to be selected from the dataset for DataScan. Value can range between 0.0 and 100.0 with up to 3 significant decimal digits. Sampling is not applied if sampling_percent is not specified, 0 or 100. */
		samplingPercent: Option[BigDecimal] = None,
	  /** Optional. A filter applied to all rows in a single DataScan job. The filter needs to be a valid SQL expression for a WHERE clause in BigQuery standard SQL syntax. Example: col1 >= 0 AND col2 < 10 */
		rowFilter: Option[String] = None,
	  /** Optional. Actions to take upon job completion.. */
		postScanActions: Option[Schema.GoogleCloudDataplexV1DataProfileSpecPostScanActions] = None,
	  /** Optional. The fields to include in data profile.If not specified, all fields at the time of profile scan job execution are included, except for ones listed in exclude_fields. */
		includeFields: Option[Schema.GoogleCloudDataplexV1DataProfileSpecSelectedFields] = None,
	  /** Optional. The fields to exclude from data profile.If specified, the fields will be excluded from data profile, regardless of include_fields value. */
		excludeFields: Option[Schema.GoogleCloudDataplexV1DataProfileSpecSelectedFields] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileSpecPostScanActions(
	  /** Optional. If set, results will be exported to the provided BigQuery table. */
		bigqueryExport: Option[Schema.GoogleCloudDataplexV1DataProfileSpecPostScanActionsBigQueryExport] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileSpecPostScanActionsBigQueryExport(
	  /** Optional. The BigQuery table to export DataProfileScan results to. Format: //bigquery.googleapis.com/projects/PROJECT_ID/datasets/DATASET_ID/tables/TABLE_ID */
		resultsTable: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileSpecSelectedFields(
	  /** Optional. Expected input is a list of fully qualified names of fields as in the schema.Only top-level field names for nested fields are supported. For instance, if 'x' is of nested field type, listing 'x' is supported but 'x.y.z' is not supported. Here 'y' and 'y.z' are nested fields of 'x'. */
		fieldNames: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoverySpec(
	  /** Optional. Configuration for metadata publishing. */
		bigqueryPublishingConfig: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpecBigQueryPublishingConfig] = None,
	  /** Cloud Storage related configurations. */
		storageConfig: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpecStorageConfig] = None
	)
	
	object GoogleCloudDataplexV1DataDiscoverySpecBigQueryPublishingConfig {
		enum TableTypeEnum extends Enum[TableTypeEnum] { case TABLE_TYPE_UNSPECIFIED, EXTERNAL, BIGLAKE }
	}
	case class GoogleCloudDataplexV1DataDiscoverySpecBigQueryPublishingConfig(
	  /** Optional. Determines whether to publish discovered tables as BigLake external tables or non-BigLake external tables. */
		tableType: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpecBigQueryPublishingConfig.TableTypeEnum] = None,
	  /** Optional. The BigQuery connection used to create BigLake tables. Must be in the form projects/{project_id}/locations/{location_id}/connections/{connection_id} */
		connection: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoverySpecStorageConfig(
	  /** Optional. Defines the data to include during discovery when only a subset of the data should be considered. Provide a list of patterns that identify the data to include. For Cloud Storage bucket assets, these patterns are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these patterns are interpreted as patterns to match table names. */
		includePatterns: Option[List[String]] = None,
	  /** Optional. Defines the data to exclude during discovery. Provide a list of patterns that identify the data to exclude. For Cloud Storage bucket assets, these patterns are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these patterns are interpreted as patterns to match table names. */
		excludePatterns: Option[List[String]] = None,
	  /** Optional. Configuration for CSV data. */
		csvOptions: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpecStorageConfigCsvOptions] = None,
	  /** Optional. Configuration for JSON data. */
		jsonOptions: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpecStorageConfigJsonOptions] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoverySpecStorageConfigCsvOptions(
	  /** Optional. The number of rows to interpret as header rows that should be skipped when reading data rows. */
		headerRows: Option[Int] = None,
	  /** Optional. The delimiter that is used to separate values. The default is , (comma). */
		delimiter: Option[String] = None,
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data types for CSV data. If true, all columns are registered as strings. */
		typeInferenceDisabled: Option[Boolean] = None,
	  /** Optional. The character used to quote column values. Accepts " (double quotation mark) or ' (single quotation mark). If unspecified, defaults to " (double quotation mark). */
		quote: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoverySpecStorageConfigJsonOptions(
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data types for JSON data. If true, all columns are registered as their primitive types (strings, number, or boolean). */
		typeInferenceDisabled: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityResult(
	  /** Overall data quality result -- true if all rules passed. */
		passed: Option[Boolean] = None,
	  /** Output only. The overall data quality score.The score ranges between 0, 100 (up to two decimal points). */
		score: Option[BigDecimal] = None,
	  /** A list of results at the dimension level.A dimension will have a corresponding DataQualityDimensionResult if and only if there is at least one rule with the 'dimension' field set to it. */
		dimensions: Option[List[Schema.GoogleCloudDataplexV1DataQualityDimensionResult]] = None,
	  /** Output only. A list of results at the column level.A column will have a corresponding DataQualityColumnResult if and only if there is at least one rule with the 'column' field set to it. */
		columns: Option[List[Schema.GoogleCloudDataplexV1DataQualityColumnResult]] = None,
	  /** A list of all the rules in a job, and their results. */
		rules: Option[List[Schema.GoogleCloudDataplexV1DataQualityRuleResult]] = None,
	  /** The count of rows processed. */
		rowCount: Option[String] = None,
	  /** The data scanned for this result. */
		scannedData: Option[Schema.GoogleCloudDataplexV1ScannedData] = None,
	  /** Output only. The result of post scan actions. */
		postScanActionsResult: Option[Schema.GoogleCloudDataplexV1DataQualityResultPostScanActionsResult] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityDimensionResult(
	  /** Output only. The dimension config specified in the DataQualitySpec, as is. */
		dimension: Option[Schema.GoogleCloudDataplexV1DataQualityDimension] = None,
	  /** Whether the dimension passed or failed. */
		passed: Option[Boolean] = None,
	  /** Output only. The dimension-level data quality score for this data scan job if and only if the 'dimension' field is set.The score ranges between 0, 100 (up to two decimal points). */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityDimension(
	  /** The dimension name a rule belongs to. Supported dimensions are "COMPLETENESS", "ACCURACY", "CONSISTENCY", "VALIDITY", "UNIQUENESS", "FRESHNESS", "VOLUME" */
		name: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityColumnResult(
	  /** Output only. The column specified in the DataQualityRule. */
		column: Option[String] = None,
	  /** Output only. The column-level data quality score for this data scan job if and only if the 'column' field is set.The score ranges between between 0, 100 (up to two decimal points). */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityRuleResult(
	  /** The rule specified in the DataQualitySpec, as is. */
		rule: Option[Schema.GoogleCloudDataplexV1DataQualityRule] = None,
	  /** Whether the rule passed or failed. */
		passed: Option[Boolean] = None,
	  /** The number of rows a rule was evaluated against.This field is only valid for row-level type rules.Evaluated count can be configured to either include all rows (default) - with null rows automatically failing rule evaluation, or exclude null rows from the evaluated_count, by setting ignore_nulls = true. */
		evaluatedCount: Option[String] = None,
	  /** The number of rows which passed a rule evaluation.This field is only valid for row-level type rules. */
		passedCount: Option[String] = None,
	  /** The number of rows with null values in the specified column. */
		nullCount: Option[String] = None,
	  /** The ratio of passed_count / evaluated_count.This field is only valid for row-level type rules. */
		passRatio: Option[BigDecimal] = None,
	  /** The query to find rows that did not pass this rule.This field is only valid for row-level type rules. */
		failingRowsQuery: Option[String] = None,
	  /** Output only. The number of rows returned by the SQL statement in a SQL assertion rule.This field is only valid for SQL assertion rules. */
		assertionRowCount: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ScannedData(
	  /** The range denoted by values of an incremental field */
		incrementalField: Option[Schema.GoogleCloudDataplexV1ScannedDataIncrementalField] = None
	)
	
	case class GoogleCloudDataplexV1ScannedDataIncrementalField(
	  /** The field that contains values which monotonically increases over time (e.g. a timestamp column). */
		field: Option[String] = None,
	  /** Value that marks the start of the range. */
		start: Option[String] = None,
	  /** Value that marks the end of the range. */
		end: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataQualityResultPostScanActionsResult(
	  /** Output only. The result of BigQuery export post scan action. */
		bigqueryExportResult: Option[Schema.GoogleCloudDataplexV1DataQualityResultPostScanActionsResultBigQueryExportResult] = None
	)
	
	object GoogleCloudDataplexV1DataQualityResultPostScanActionsResultBigQueryExportResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, SKIPPED }
	}
	case class GoogleCloudDataplexV1DataQualityResultPostScanActionsResultBigQueryExportResult(
	  /** Output only. Execution state for the BigQuery exporting. */
		state: Option[Schema.GoogleCloudDataplexV1DataQualityResultPostScanActionsResultBigQueryExportResult.StateEnum] = None,
	  /** Output only. Additional information about the BigQuery exporting. */
		message: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResult(
	  /** The count of rows scanned. */
		rowCount: Option[String] = None,
	  /** The profile information per field. */
		profile: Option[Schema.GoogleCloudDataplexV1DataProfileResultProfile] = None,
	  /** The data scanned for this result. */
		scannedData: Option[Schema.GoogleCloudDataplexV1ScannedData] = None,
	  /** Output only. The result of post scan actions. */
		postScanActionsResult: Option[Schema.GoogleCloudDataplexV1DataProfileResultPostScanActionsResult] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfile(
	  /** List of fields with structural and profile information for each field. */
		fields: Option[List[Schema.GoogleCloudDataplexV1DataProfileResultProfileField]] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileField(
	  /** The name of the field. */
		name: Option[String] = None,
	  /** The data type retrieved from the schema of the data source. For instance, for a BigQuery native table, it is the BigQuery Table Schema (https://cloud.google.com/bigquery/docs/reference/rest/v2/tables#tablefieldschema). For a Dataplex Entity, it is the Entity Schema (https://cloud.google.com/dataplex/docs/reference/rpc/google.cloud.dataplex.v1#type_3). */
		`type`: Option[String] = None,
	  /** The mode of the field. Possible values include: REQUIRED, if it is a required field. NULLABLE, if it is an optional field. REPEATED, if it is a repeated field. */
		mode: Option[String] = None,
	  /** Profile information for the corresponding field. */
		profile: Option[Schema.GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfo] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfo(
	  /** Ratio of rows with null value against total scanned rows. */
		nullRatio: Option[BigDecimal] = None,
	  /** Ratio of rows with distinct values against total scanned rows. Not available for complex non-groupable field type, including RECORD, ARRAY, GEOGRAPHY, and JSON, as well as fields with REPEATABLE mode. */
		distinctRatio: Option[BigDecimal] = None,
	  /** The list of top N non-null values, frequency and ratio with which they occur in the scanned data. N is 10 or equal to the number of distinct values in the field, whichever is smaller. Not available for complex non-groupable field type, including RECORD, ARRAY, GEOGRAPHY, and JSON, as well as fields with REPEATABLE mode. */
		topNValues: Option[List[Schema.GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoTopNValue]] = None,
	  /** String type field information. */
		stringProfile: Option[Schema.GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoStringFieldInfo] = None,
	  /** Integer type field information. */
		integerProfile: Option[Schema.GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoIntegerFieldInfo] = None,
	  /** Double type field information. */
		doubleProfile: Option[Schema.GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoDoubleFieldInfo] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoTopNValue(
	  /** String value of a top N non-null value. */
		value: Option[String] = None,
	  /** Count of the corresponding value in the scanned data. */
		count: Option[String] = None,
	  /** Ratio of the corresponding value in the field against the total number of rows in the scanned data. */
		ratio: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoStringFieldInfo(
	  /** Minimum length of non-null values in the scanned data. */
		minLength: Option[String] = None,
	  /** Maximum length of non-null values in the scanned data. */
		maxLength: Option[String] = None,
	  /** Average length of non-null values in the scanned data. */
		averageLength: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoIntegerFieldInfo(
	  /** Average of non-null values in the scanned data. NaN, if the field has a NaN. */
		average: Option[BigDecimal] = None,
	  /** Standard deviation of non-null values in the scanned data. NaN, if the field has a NaN. */
		standardDeviation: Option[BigDecimal] = None,
	  /** Minimum of non-null values in the scanned data. NaN, if the field has a NaN. */
		min: Option[String] = None,
	  /** A quartile divides the number of data points into four parts, or quarters, of more-or-less equal size. Three main quartiles used are: The first quartile (Q1) splits off the lowest 25% of data from the highest 75%. It is also known as the lower or 25th empirical quartile, as 25% of the data is below this point. The second quartile (Q2) is the median of a data set. So, 50% of the data lies below this point. The third quartile (Q3) splits off the highest 25% of data from the lowest 75%. It is known as the upper or 75th empirical quartile, as 75% of the data lies below this point. Here, the quartiles is provided as an ordered list of approximate quartile values for the scanned data, occurring in order Q1, median, Q3. */
		quartiles: Option[List[String]] = None,
	  /** Maximum of non-null values in the scanned data. NaN, if the field has a NaN. */
		max: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultProfileFieldProfileInfoDoubleFieldInfo(
	  /** Average of non-null values in the scanned data. NaN, if the field has a NaN. */
		average: Option[BigDecimal] = None,
	  /** Standard deviation of non-null values in the scanned data. NaN, if the field has a NaN. */
		standardDeviation: Option[BigDecimal] = None,
	  /** Minimum of non-null values in the scanned data. NaN, if the field has a NaN. */
		min: Option[BigDecimal] = None,
	  /** A quartile divides the number of data points into four parts, or quarters, of more-or-less equal size. Three main quartiles used are: The first quartile (Q1) splits off the lowest 25% of data from the highest 75%. It is also known as the lower or 25th empirical quartile, as 25% of the data is below this point. The second quartile (Q2) is the median of a data set. So, 50% of the data lies below this point. The third quartile (Q3) splits off the highest 25% of data from the lowest 75%. It is known as the upper or 75th empirical quartile, as 75% of the data lies below this point. Here, the quartiles is provided as an ordered list of quartile values for the scanned data, occurring in order Q1, median, Q3. */
		quartiles: Option[List[BigDecimal]] = None,
	  /** Maximum of non-null values in the scanned data. NaN, if the field has a NaN. */
		max: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDataplexV1DataProfileResultPostScanActionsResult(
	  /** Output only. The result of BigQuery export post scan action. */
		bigqueryExportResult: Option[Schema.GoogleCloudDataplexV1DataProfileResultPostScanActionsResultBigQueryExportResult] = None
	)
	
	object GoogleCloudDataplexV1DataProfileResultPostScanActionsResultBigQueryExportResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, SKIPPED }
	}
	case class GoogleCloudDataplexV1DataProfileResultPostScanActionsResultBigQueryExportResult(
	  /** Output only. Execution state for the BigQuery exporting. */
		state: Option[Schema.GoogleCloudDataplexV1DataProfileResultPostScanActionsResultBigQueryExportResult.StateEnum] = None,
	  /** Output only. Additional information about the BigQuery exporting. */
		message: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoveryResult(
	  /** Output only. Configuration for metadata publishing. */
		bigqueryPublishing: Option[Schema.GoogleCloudDataplexV1DataDiscoveryResultBigQueryPublishing] = None
	)
	
	case class GoogleCloudDataplexV1DataDiscoveryResultBigQueryPublishing(
	  /** Output only. The BigQuery dataset to publish to. It takes the form projects/{project_id}/datasets/{dataset_id}. If not set, the service creates a default publishing dataset. */
		dataset: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListDataScansResponse(
	  /** DataScans (BASIC view only) under the given parent location. */
		dataScans: Option[List[Schema.GoogleCloudDataplexV1DataScan]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1RunDataScanRequest(
	
	)
	
	case class GoogleCloudDataplexV1RunDataScanResponse(
	  /** DataScanJob created by RunDataScan request. */
		job: Option[Schema.GoogleCloudDataplexV1DataScanJob] = None
	)
	
	object GoogleCloudDataplexV1DataScanJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELING, CANCELLED, SUCCEEDED, FAILED, PENDING }
		enum TypeEnum extends Enum[TypeEnum] { case DATA_SCAN_TYPE_UNSPECIFIED, DATA_QUALITY, DATA_PROFILE, DATA_DISCOVERY }
	}
	case class GoogleCloudDataplexV1DataScanJob(
	  /** Output only. The relative resource name of the DataScanJob, of the form: projects/{project}/locations/{location_id}/dataScans/{datascan_id}/jobs/{job_id}, where project refers to a project_id or project_number and location_id refers to a GCP region. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the DataScanJob. */
		uid: Option[String] = None,
	  /** Output only. The time when the DataScanJob was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the DataScanJob was started. */
		startTime: Option[String] = None,
	  /** Output only. The time when the DataScanJob ended. */
		endTime: Option[String] = None,
	  /** Output only. Execution state for the DataScanJob. */
		state: Option[Schema.GoogleCloudDataplexV1DataScanJob.StateEnum] = None,
	  /** Output only. Additional information about the current state. */
		message: Option[String] = None,
	  /** Output only. The type of the parent DataScan. */
		`type`: Option[Schema.GoogleCloudDataplexV1DataScanJob.TypeEnum] = None,
	  /** Output only. Settings for a data quality scan. */
		dataQualitySpec: Option[Schema.GoogleCloudDataplexV1DataQualitySpec] = None,
	  /** Output only. Settings for a data profile scan. */
		dataProfileSpec: Option[Schema.GoogleCloudDataplexV1DataProfileSpec] = None,
	  /** Output only. Settings for a data discovery scan. */
		dataDiscoverySpec: Option[Schema.GoogleCloudDataplexV1DataDiscoverySpec] = None,
	  /** Output only. The result of a data quality scan. */
		dataQualityResult: Option[Schema.GoogleCloudDataplexV1DataQualityResult] = None,
	  /** Output only. The result of a data profile scan. */
		dataProfileResult: Option[Schema.GoogleCloudDataplexV1DataProfileResult] = None,
	  /** Output only. The result of a data discovery scan. */
		dataDiscoveryResult: Option[Schema.GoogleCloudDataplexV1DataDiscoveryResult] = None
	)
	
	case class GoogleCloudDataplexV1ListDataScanJobsResponse(
	  /** DataScanJobs (BASIC view only) under a given dataScan. */
		dataScanJobs: Option[List[Schema.GoogleCloudDataplexV1DataScanJob]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1GenerateDataQualityRulesRequest(
	
	)
	
	case class GoogleCloudDataplexV1GenerateDataQualityRulesResponse(
	  /** The data quality rules that Dataplex generates based on the results of a data profiling scan. */
		rule: Option[List[Schema.GoogleCloudDataplexV1DataQualityRule]] = None
	)
	
	object GoogleCloudDataplexV1Entity {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TABLE, FILESET }
		enum SystemEnum extends Enum[SystemEnum] { case STORAGE_SYSTEM_UNSPECIFIED, CLOUD_STORAGE, BIGQUERY }
	}
	case class GoogleCloudDataplexV1Entity(
	  /** Output only. The resource name of the entity, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}/entities/{id}. */
		name: Option[String] = None,
	  /** Optional. Display name must be shorter than or equal to 256 characters. */
		displayName: Option[String] = None,
	  /** Optional. User friendly longer description text. Must be shorter than or equal to 1024 characters. */
		description: Option[String] = None,
	  /** Output only. The time when the entity was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the entity was last updated. */
		updateTime: Option[String] = None,
	  /** Required. A user-provided entity ID. It is mutable, and will be used as the published table name. Specifying a new ID in an update entity request will override the existing value. The ID must contain only letters (a-z, A-Z), numbers (0-9), and underscores, and consist of 256 or fewer characters. */
		id: Option[String] = None,
	  /** Optional. The etag associated with the entity, which can be retrieved with a GetEntity request. Required for update and delete requests. */
		etag: Option[String] = None,
	  /** Required. Immutable. The type of entity. */
		`type`: Option[Schema.GoogleCloudDataplexV1Entity.TypeEnum] = None,
	  /** Required. Immutable. The ID of the asset associated with the storage location containing the entity data. The entity must be with in the same zone with the asset. */
		asset: Option[String] = None,
	  /** Required. Immutable. The storage path of the entity data. For Cloud Storage data, this is the fully-qualified path to the entity, such as gs://bucket/path/to/data. For BigQuery data, this is the name of the table resource, such as projects/project_id/datasets/dataset_id/tables/table_id. */
		dataPath: Option[String] = None,
	  /** Optional. The set of items within the data path constituting the data in the entity, represented as a glob path. Example: gs://bucket/path/to/data/&#42;&#42;/&#42;.csv. */
		dataPathPattern: Option[String] = None,
	  /** Output only. The name of the associated Data Catalog entry. */
		catalogEntry: Option[String] = None,
	  /** Required. Immutable. Identifies the storage system of the entity data. */
		system: Option[Schema.GoogleCloudDataplexV1Entity.SystemEnum] = None,
	  /** Required. Identifies the storage format of the entity data. It does not apply to entities with data stored in BigQuery. */
		format: Option[Schema.GoogleCloudDataplexV1StorageFormat] = None,
	  /** Output only. Metadata stores that the entity is compatible with. */
		compatibility: Option[Schema.GoogleCloudDataplexV1EntityCompatibilityStatus] = None,
	  /** Output only. Identifies the access mechanism to the entity. Not user settable. */
		access: Option[Schema.GoogleCloudDataplexV1StorageAccess] = None,
	  /** Output only. System generated unique ID for the Entity. This ID will be different if the Entity is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Required. The description of the data structure and layout. The schema is not included in list responses. It is only included in SCHEMA and FULL entity views of a GetEntity response. */
		schema: Option[Schema.GoogleCloudDataplexV1Schema] = None
	)
	
	object GoogleCloudDataplexV1StorageFormat {
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, PARQUET, AVRO, ORC, CSV, JSON, IMAGE, AUDIO, VIDEO, TEXT, TFRECORD, OTHER, UNKNOWN }
		enum CompressionFormatEnum extends Enum[CompressionFormatEnum] { case COMPRESSION_FORMAT_UNSPECIFIED, GZIP, BZIP2 }
	}
	case class GoogleCloudDataplexV1StorageFormat(
	  /** Output only. The data format associated with the stored data, which represents content type values. The value is inferred from mime type. */
		format: Option[Schema.GoogleCloudDataplexV1StorageFormat.FormatEnum] = None,
	  /** Optional. The compression type associated with the stored data. If unspecified, the data is uncompressed. */
		compressionFormat: Option[Schema.GoogleCloudDataplexV1StorageFormat.CompressionFormatEnum] = None,
	  /** Required. The mime type descriptor for the data. Must match the pattern {type}/{subtype}. Supported values: application/x-parquet application/x-avro application/x-orc application/x-tfrecord application/x-parquet+iceberg application/x-avro+iceberg application/x-orc+iceberg application/json application/{subtypes} text/csv text/ image/{image subtype} video/{video subtype} audio/{audio subtype} */
		mimeType: Option[String] = None,
	  /** Optional. Additional information about CSV formatted data. */
		csv: Option[Schema.GoogleCloudDataplexV1StorageFormatCsvOptions] = None,
	  /** Optional. Additional information about CSV formatted data. */
		json: Option[Schema.GoogleCloudDataplexV1StorageFormatJsonOptions] = None,
	  /** Optional. Additional information about iceberg tables. */
		iceberg: Option[Schema.GoogleCloudDataplexV1StorageFormatIcebergOptions] = None
	)
	
	case class GoogleCloudDataplexV1StorageFormatCsvOptions(
	  /** Optional. The character encoding of the data. Accepts "US-ASCII", "UTF-8", and "ISO-8859-1". Defaults to UTF-8 if unspecified. */
		encoding: Option[String] = None,
	  /** Optional. The number of rows to interpret as header rows that should be skipped when reading data rows. Defaults to 0. */
		headerRows: Option[Int] = None,
	  /** Optional. The delimiter used to separate values. Defaults to ','. */
		delimiter: Option[String] = None,
	  /** Optional. The character used to quote column values. Accepts '"' (double quotation mark) or ''' (single quotation mark). Defaults to '"' (double quotation mark) if unspecified. */
		quote: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1StorageFormatJsonOptions(
	  /** Optional. The character encoding of the data. Accepts "US-ASCII", "UTF-8" and "ISO-8859-1". Defaults to UTF-8 if not specified. */
		encoding: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1StorageFormatIcebergOptions(
	  /** Optional. The location of where the iceberg metadata is present, must be within the table path */
		metadataLocation: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1EntityCompatibilityStatus(
	  /** Output only. Whether this entity is compatible with Hive Metastore. */
		hiveMetastore: Option[Schema.GoogleCloudDataplexV1EntityCompatibilityStatusCompatibility] = None,
	  /** Output only. Whether this entity is compatible with BigQuery. */
		bigquery: Option[Schema.GoogleCloudDataplexV1EntityCompatibilityStatusCompatibility] = None
	)
	
	case class GoogleCloudDataplexV1EntityCompatibilityStatusCompatibility(
	  /** Output only. Whether the entity is compatible and can be represented in the metadata store. */
		compatible: Option[Boolean] = None,
	  /** Output only. Provides additional detail if the entity is incompatible with the metadata store. */
		reason: Option[String] = None
	)
	
	object GoogleCloudDataplexV1StorageAccess {
		enum ReadEnum extends Enum[ReadEnum] { case ACCESS_MODE_UNSPECIFIED, DIRECT, MANAGED }
	}
	case class GoogleCloudDataplexV1StorageAccess(
	  /** Output only. Describes the read access mechanism of the data. Not user settable. */
		read: Option[Schema.GoogleCloudDataplexV1StorageAccess.ReadEnum] = None
	)
	
	object GoogleCloudDataplexV1Schema {
		enum PartitionStyleEnum extends Enum[PartitionStyleEnum] { case PARTITION_STYLE_UNSPECIFIED, HIVE_COMPATIBLE }
	}
	case class GoogleCloudDataplexV1Schema(
	  /** Required. Set to true if user-managed or false if managed by Dataplex. The default is false (managed by Dataplex). Set to falseto enable Dataplex discovery to update the schema. including new data discovery, schema inference, and schema evolution. Users retain the ability to input and edit the schema. Dataplex treats schema input by the user as though produced by a previous Dataplex discovery operation, and it will evolve the schema and take action based on that treatment. Set to true to fully manage the entity schema. This setting guarantees that Dataplex will not change schema fields. */
		userManaged: Option[Boolean] = None,
	  /** Optional. The sequence of fields describing data in table entities. Note: BigQuery SchemaFields are immutable. */
		fields: Option[List[Schema.GoogleCloudDataplexV1SchemaSchemaField]] = None,
	  /** Optional. The sequence of fields describing the partition structure in entities. If this field is empty, there are no partitions within the data. */
		partitionFields: Option[List[Schema.GoogleCloudDataplexV1SchemaPartitionField]] = None,
	  /** Optional. The structure of paths containing partition data within the entity. */
		partitionStyle: Option[Schema.GoogleCloudDataplexV1Schema.PartitionStyleEnum] = None
	)
	
	object GoogleCloudDataplexV1SchemaSchemaField {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, BOOLEAN, BYTE, INT16, INT32, INT64, FLOAT, DOUBLE, DECIMAL, STRING, BINARY, TIMESTAMP, DATE, TIME, RECORD, NULL }
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, REQUIRED, NULLABLE, REPEATED }
	}
	case class GoogleCloudDataplexV1SchemaSchemaField(
	  /** Required. The name of the field. Must contain only letters, numbers and underscores, with a maximum length of 767 characters, and must begin with a letter or underscore. */
		name: Option[String] = None,
	  /** Optional. User friendly field description. Must be less than or equal to 1024 characters. */
		description: Option[String] = None,
	  /** Required. The type of field. */
		`type`: Option[Schema.GoogleCloudDataplexV1SchemaSchemaField.TypeEnum] = None,
	  /** Required. Additional field semantics. */
		mode: Option[Schema.GoogleCloudDataplexV1SchemaSchemaField.ModeEnum] = None,
	  /** Optional. Any nested field for complex types. */
		fields: Option[List[Schema.GoogleCloudDataplexV1SchemaSchemaField]] = None
	)
	
	object GoogleCloudDataplexV1SchemaPartitionField {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, BOOLEAN, BYTE, INT16, INT32, INT64, FLOAT, DOUBLE, DECIMAL, STRING, BINARY, TIMESTAMP, DATE, TIME, RECORD, NULL }
	}
	case class GoogleCloudDataplexV1SchemaPartitionField(
	  /** Required. Partition field name must consist of letters, numbers, and underscores only, with a maximum of length of 256 characters, and must begin with a letter or underscore.. */
		name: Option[String] = None,
	  /** Required. Immutable. The type of field. */
		`type`: Option[Schema.GoogleCloudDataplexV1SchemaPartitionField.TypeEnum] = None
	)
	
	case class GoogleCloudDataplexV1ListEntitiesResponse(
	  /** Entities in the specified parent zone. */
		entities: Option[List[Schema.GoogleCloudDataplexV1Entity]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no remaining results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1Partition(
	  /** Output only. Partition values used in the HTTP URL must be double encoded. For example, url_encode(url_encode(value)) can be used to encode "US:CA/CA#Sunnyvale so that the request URL ends with "/partitions/US%253ACA/CA%2523Sunnyvale". The name field in the response retains the encoded format. */
		name: Option[String] = None,
	  /** Required. Immutable. The set of values representing the partition, which correspond to the partition schema defined in the parent entity. */
		values: Option[List[String]] = None,
	  /** Required. Immutable. The location of the entity data within the partition, for example, gs://bucket/path/to/entity/key1=value1/key2=value2. Or projects//datasets//tables/ */
		location: Option[String] = None,
	  /** Optional. The etag for this partition. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListPartitionsResponse(
	  /** Partitions under the specified parent entity. */
		partitions: Option[List[Schema.GoogleCloudDataplexV1Partition]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no remaining results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDataplexV1Lake {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
	}
	case class GoogleCloudDataplexV1Lake(
	  /** Output only. The relative resource name of the lake, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}. */
		name: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Output only. System generated globally unique ID for the lake. This ID will be different if the lake is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the lake was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the lake was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User-defined labels for the lake. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the lake. */
		description: Option[String] = None,
	  /** Output only. Current state of the lake. */
		state: Option[Schema.GoogleCloudDataplexV1Lake.StateEnum] = None,
	  /** Output only. Service account associated with this lake. This service account must be authorized to access or operate on resources managed by the lake. */
		serviceAccount: Option[String] = None,
	  /** Optional. Settings to manage lake and Dataproc Metastore service instance association. */
		metastore: Option[Schema.GoogleCloudDataplexV1LakeMetastore] = None,
	  /** Output only. Aggregated status of the underlying assets of the lake. */
		assetStatus: Option[Schema.GoogleCloudDataplexV1AssetStatus] = None,
	  /** Output only. Metastore status of the lake. */
		metastoreStatus: Option[Schema.GoogleCloudDataplexV1LakeMetastoreStatus] = None
	)
	
	case class GoogleCloudDataplexV1LakeMetastore(
	  /** Optional. A relative reference to the Dataproc Metastore (https://cloud.google.com/dataproc-metastore/docs) service associated with the lake: projects/{project_id}/locations/{location_id}/services/{service_id} */
		service: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AssetStatus(
	  /** Last update time of the status. */
		updateTime: Option[String] = None,
	  /** Number of active assets. */
		activeAssets: Option[Int] = None,
	  /** Number of assets that are in process of updating the security policy on attached resources. */
		securityPolicyApplyingAssets: Option[Int] = None
	)
	
	object GoogleCloudDataplexV1LakeMetastoreStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NONE, READY, UPDATING, ERROR }
	}
	case class GoogleCloudDataplexV1LakeMetastoreStatus(
	  /** Current state of association. */
		state: Option[Schema.GoogleCloudDataplexV1LakeMetastoreStatus.StateEnum] = None,
	  /** Additional information about the current status. */
		message: Option[String] = None,
	  /** Last update time of the metastore status of the lake. */
		updateTime: Option[String] = None,
	  /** The URI of the endpoint used to access the Metastore service. */
		endpoint: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListLakesResponse(
	  /** Lakes under the given parent location. */
		lakes: Option[List[Schema.GoogleCloudDataplexV1Lake]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListActionsResponse(
	  /** Actions under the given parent lake/zone/asset. */
		actions: Option[List[Schema.GoogleCloudDataplexV1Action]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDataplexV1Action {
		enum CategoryEnum extends Enum[CategoryEnum] { case CATEGORY_UNSPECIFIED, RESOURCE_MANAGEMENT, SECURITY_POLICY, DATA_DISCOVERY }
	}
	case class GoogleCloudDataplexV1Action(
	  /** The category of issue associated with the action. */
		category: Option[Schema.GoogleCloudDataplexV1Action.CategoryEnum] = None,
	  /** Detailed description of the issue requiring action. */
		issue: Option[String] = None,
	  /** The time that the issue was detected. */
		detectTime: Option[String] = None,
	  /** Output only. The relative resource name of the action, of the form: projects/{project}/locations/{location}/lakes/{lake}/actions/{action} projects/{project}/locations/{location}/lakes/{lake}/zones/{zone}/actions/{action} projects/{project}/locations/{location}/lakes/{lake}/zones/{zone}/assets/{asset}/actions/{action}. */
		name: Option[String] = None,
	  /** Output only. The relative resource name of the lake, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}. */
		lake: Option[String] = None,
	  /** Output only. The relative resource name of the zone, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}. */
		zone: Option[String] = None,
	  /** Output only. The relative resource name of the asset, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}/assets/{asset_id}. */
		asset: Option[String] = None,
	  /** The list of data locations associated with this action. Cloud Storage locations are represented as URI paths(E.g. gs://bucket/table1/year=2020/month=Jan/). BigQuery locations refer to resource names(E.g. bigquery.googleapis.com/projects/project-id/datasets/dataset-id). */
		dataLocations: Option[List[String]] = None,
	  /** Details for issues related to invalid or unsupported data formats. */
		invalidDataFormat: Option[Schema.GoogleCloudDataplexV1ActionInvalidDataFormat] = None,
	  /** Details for issues related to incompatible schemas detected within data. */
		incompatibleDataSchema: Option[Schema.GoogleCloudDataplexV1ActionIncompatibleDataSchema] = None,
	  /** Details for issues related to invalid or unsupported data partition structure. */
		invalidDataPartition: Option[Schema.GoogleCloudDataplexV1ActionInvalidDataPartition] = None,
	  /** Details for issues related to absence of data within managed resources. */
		missingData: Option[Schema.GoogleCloudDataplexV1ActionMissingData] = None,
	  /** Details for issues related to absence of a managed resource. */
		missingResource: Option[Schema.GoogleCloudDataplexV1ActionMissingResource] = None,
	  /** Details for issues related to lack of permissions to access data resources. */
		unauthorizedResource: Option[Schema.GoogleCloudDataplexV1ActionUnauthorizedResource] = None,
	  /** Details for issues related to applying security policy. */
		failedSecurityPolicyApply: Option[Schema.GoogleCloudDataplexV1ActionFailedSecurityPolicyApply] = None,
	  /** Details for issues related to invalid data arrangement. */
		invalidDataOrganization: Option[Schema.GoogleCloudDataplexV1ActionInvalidDataOrganization] = None
	)
	
	case class GoogleCloudDataplexV1ActionInvalidDataFormat(
	  /** The list of data locations sampled and used for format/schema inference. */
		sampledDataLocations: Option[List[String]] = None,
	  /** The expected data format of the entity. */
		expectedFormat: Option[String] = None,
	  /** The new unexpected data format within the entity. */
		newFormat: Option[String] = None
	)
	
	object GoogleCloudDataplexV1ActionIncompatibleDataSchema {
		enum SchemaChangeEnum extends Enum[SchemaChangeEnum] { case SCHEMA_CHANGE_UNSPECIFIED, INCOMPATIBLE, MODIFIED }
	}
	case class GoogleCloudDataplexV1ActionIncompatibleDataSchema(
	  /** The name of the table containing invalid data. */
		table: Option[String] = None,
	  /** The existing and expected schema of the table. The schema is provided as a JSON formatted structure listing columns and data types. */
		existingSchema: Option[String] = None,
	  /** The new and incompatible schema within the table. The schema is provided as a JSON formatted structured listing columns and data types. */
		newSchema: Option[String] = None,
	  /** The list of data locations sampled and used for format/schema inference. */
		sampledDataLocations: Option[List[String]] = None,
	  /** Whether the action relates to a schema that is incompatible or modified. */
		schemaChange: Option[Schema.GoogleCloudDataplexV1ActionIncompatibleDataSchema.SchemaChangeEnum] = None
	)
	
	object GoogleCloudDataplexV1ActionInvalidDataPartition {
		enum ExpectedStructureEnum extends Enum[ExpectedStructureEnum] { case PARTITION_STRUCTURE_UNSPECIFIED, CONSISTENT_KEYS, HIVE_STYLE_KEYS }
	}
	case class GoogleCloudDataplexV1ActionInvalidDataPartition(
	  /** The issue type of InvalidDataPartition. */
		expectedStructure: Option[Schema.GoogleCloudDataplexV1ActionInvalidDataPartition.ExpectedStructureEnum] = None
	)
	
	case class GoogleCloudDataplexV1ActionMissingData(
	
	)
	
	case class GoogleCloudDataplexV1ActionMissingResource(
	
	)
	
	case class GoogleCloudDataplexV1ActionUnauthorizedResource(
	
	)
	
	case class GoogleCloudDataplexV1ActionFailedSecurityPolicyApply(
	  /** Resource name of one of the assets with failing security policy application. Populated for a lake or zone resource only. */
		asset: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ActionInvalidDataOrganization(
	
	)
	
	object GoogleCloudDataplexV1Zone {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, RAW, CURATED }
	}
	case class GoogleCloudDataplexV1Zone(
	  /** Output only. The relative resource name of the zone, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}. */
		name: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Output only. System generated globally unique ID for the zone. This ID will be different if the zone is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the zone was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the zone was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User defined labels for the zone. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the zone. */
		description: Option[String] = None,
	  /** Output only. Current state of the zone. */
		state: Option[Schema.GoogleCloudDataplexV1Zone.StateEnum] = None,
	  /** Required. Immutable. The type of the zone. */
		`type`: Option[Schema.GoogleCloudDataplexV1Zone.TypeEnum] = None,
	  /** Optional. Specification of the discovery feature applied to data in this zone. */
		discoverySpec: Option[Schema.GoogleCloudDataplexV1ZoneDiscoverySpec] = None,
	  /** Required. Specification of the resources that are referenced by the assets within this zone. */
		resourceSpec: Option[Schema.GoogleCloudDataplexV1ZoneResourceSpec] = None,
	  /** Output only. Aggregated status of the underlying assets of the zone. */
		assetStatus: Option[Schema.GoogleCloudDataplexV1AssetStatus] = None
	)
	
	case class GoogleCloudDataplexV1ZoneDiscoverySpec(
	  /** Required. Whether discovery is enabled. */
		enabled: Option[Boolean] = None,
	  /** Optional. The list of patterns to apply for selecting data to include during discovery if only a subset of the data should considered. For Cloud Storage bucket assets, these are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these are interpreted as patterns to match table names. */
		includePatterns: Option[List[String]] = None,
	  /** Optional. The list of patterns to apply for selecting data to exclude during discovery. For Cloud Storage bucket assets, these are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these are interpreted as patterns to match table names. */
		excludePatterns: Option[List[String]] = None,
	  /** Optional. Configuration for CSV data. */
		csvOptions: Option[Schema.GoogleCloudDataplexV1ZoneDiscoverySpecCsvOptions] = None,
	  /** Optional. Configuration for Json data. */
		jsonOptions: Option[Schema.GoogleCloudDataplexV1ZoneDiscoverySpecJsonOptions] = None,
	  /** Optional. Cron schedule (https://en.wikipedia.org/wiki/Cron) for running discovery periodically. Successive discovery runs must be scheduled at least 60 minutes apart. The default value is to run discovery every 60 minutes. To explicitly set a timezone to the cron tab, apply a prefix in the cron tab: "CRON_TZ=${IANA_TIME_ZONE}" or TZ=${IANA_TIME_ZONE}". The ${IANA_TIME_ZONE} may only be a valid string from IANA time zone database. For example, CRON_TZ=America/New_York 1 &#42; &#42; &#42; &#42;, or TZ=America/New_York 1 &#42; &#42; &#42; &#42;. */
		schedule: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ZoneDiscoverySpecCsvOptions(
	  /** Optional. The number of rows to interpret as header rows that should be skipped when reading data rows. */
		headerRows: Option[Int] = None,
	  /** Optional. The delimiter being used to separate values. This defaults to ','. */
		delimiter: Option[String] = None,
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data type for CSV data. If true, all columns will be registered as strings. */
		disableTypeInference: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1ZoneDiscoverySpecJsonOptions(
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data type for Json data. If true, all columns will be registered as their primitive types (strings, number or boolean). */
		disableTypeInference: Option[Boolean] = None
	)
	
	object GoogleCloudDataplexV1ZoneResourceSpec {
		enum LocationTypeEnum extends Enum[LocationTypeEnum] { case LOCATION_TYPE_UNSPECIFIED, SINGLE_REGION, MULTI_REGION }
	}
	case class GoogleCloudDataplexV1ZoneResourceSpec(
	  /** Required. Immutable. The location type of the resources that are allowed to be attached to the assets within this zone. */
		locationType: Option[Schema.GoogleCloudDataplexV1ZoneResourceSpec.LocationTypeEnum] = None
	)
	
	case class GoogleCloudDataplexV1ListZonesResponse(
	  /** Zones under the given parent lake. */
		zones: Option[List[Schema.GoogleCloudDataplexV1Zone]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDataplexV1Asset {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
	}
	case class GoogleCloudDataplexV1Asset(
	  /** Output only. The relative resource name of the asset, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}/assets/{asset_id}. */
		name: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Output only. System generated globally unique ID for the asset. This ID will be different if the asset is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the asset was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the asset was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User defined labels for the asset. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the asset. */
		description: Option[String] = None,
	  /** Output only. Current state of the asset. */
		state: Option[Schema.GoogleCloudDataplexV1Asset.StateEnum] = None,
	  /** Required. Specification of the resource that is referenced by this asset. */
		resourceSpec: Option[Schema.GoogleCloudDataplexV1AssetResourceSpec] = None,
	  /** Output only. Status of the resource referenced by this asset. */
		resourceStatus: Option[Schema.GoogleCloudDataplexV1AssetResourceStatus] = None,
	  /** Output only. Status of the security policy applied to resource referenced by this asset. */
		securityStatus: Option[Schema.GoogleCloudDataplexV1AssetSecurityStatus] = None,
	  /** Optional. Specification of the discovery feature applied to data referenced by this asset. When this spec is left unset, the asset will use the spec set on the parent zone. */
		discoverySpec: Option[Schema.GoogleCloudDataplexV1AssetDiscoverySpec] = None,
	  /** Output only. Status of the discovery feature applied to data referenced by this asset. */
		discoveryStatus: Option[Schema.GoogleCloudDataplexV1AssetDiscoveryStatus] = None
	)
	
	object GoogleCloudDataplexV1AssetResourceSpec {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STORAGE_BUCKET, BIGQUERY_DATASET }
		enum ReadAccessModeEnum extends Enum[ReadAccessModeEnum] { case ACCESS_MODE_UNSPECIFIED, DIRECT, MANAGED }
	}
	case class GoogleCloudDataplexV1AssetResourceSpec(
	  /** Immutable. Relative name of the cloud resource that contains the data that is being managed within a lake. For example: projects/{project_number}/buckets/{bucket_id} projects/{project_number}/datasets/{dataset_id} */
		name: Option[String] = None,
	  /** Required. Immutable. Type of resource. */
		`type`: Option[Schema.GoogleCloudDataplexV1AssetResourceSpec.TypeEnum] = None,
	  /** Optional. Determines how read permissions are handled for each asset and their associated tables. Only available to storage buckets assets. */
		readAccessMode: Option[Schema.GoogleCloudDataplexV1AssetResourceSpec.ReadAccessModeEnum] = None
	)
	
	object GoogleCloudDataplexV1AssetResourceStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, ERROR }
	}
	case class GoogleCloudDataplexV1AssetResourceStatus(
	  /** The current state of the managed resource. */
		state: Option[Schema.GoogleCloudDataplexV1AssetResourceStatus.StateEnum] = None,
	  /** Additional information about the current state. */
		message: Option[String] = None,
	  /** Last update time of the status. */
		updateTime: Option[String] = None,
	  /** Output only. Service account associated with the BigQuery Connection. */
		managedAccessIdentity: Option[String] = None
	)
	
	object GoogleCloudDataplexV1AssetSecurityStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, APPLYING, ERROR }
	}
	case class GoogleCloudDataplexV1AssetSecurityStatus(
	  /** The current state of the security policy applied to the attached resource. */
		state: Option[Schema.GoogleCloudDataplexV1AssetSecurityStatus.StateEnum] = None,
	  /** Additional information about the current state. */
		message: Option[String] = None,
	  /** Last update time of the status. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AssetDiscoverySpec(
	  /** Optional. Whether discovery is enabled. */
		enabled: Option[Boolean] = None,
	  /** Optional. The list of patterns to apply for selecting data to include during discovery if only a subset of the data should considered. For Cloud Storage bucket assets, these are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these are interpreted as patterns to match table names. */
		includePatterns: Option[List[String]] = None,
	  /** Optional. The list of patterns to apply for selecting data to exclude during discovery. For Cloud Storage bucket assets, these are interpreted as glob patterns used to match object names. For BigQuery dataset assets, these are interpreted as patterns to match table names. */
		excludePatterns: Option[List[String]] = None,
	  /** Optional. Configuration for CSV data. */
		csvOptions: Option[Schema.GoogleCloudDataplexV1AssetDiscoverySpecCsvOptions] = None,
	  /** Optional. Configuration for Json data. */
		jsonOptions: Option[Schema.GoogleCloudDataplexV1AssetDiscoverySpecJsonOptions] = None,
	  /** Optional. Cron schedule (https://en.wikipedia.org/wiki/Cron) for running discovery periodically. Successive discovery runs must be scheduled at least 60 minutes apart. The default value is to run discovery every 60 minutes. To explicitly set a timezone to the cron tab, apply a prefix in the cron tab: "CRON_TZ=${IANA_TIME_ZONE}" or TZ=${IANA_TIME_ZONE}". The ${IANA_TIME_ZONE} may only be a valid string from IANA time zone database. For example, CRON_TZ=America/New_York 1 &#42; &#42; &#42; &#42;, or TZ=America/New_York 1 &#42; &#42; &#42; &#42;. */
		schedule: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AssetDiscoverySpecCsvOptions(
	  /** Optional. The number of rows to interpret as header rows that should be skipped when reading data rows. */
		headerRows: Option[Int] = None,
	  /** Optional. The delimiter being used to separate values. This defaults to ','. */
		delimiter: Option[String] = None,
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data type for CSV data. If true, all columns will be registered as strings. */
		disableTypeInference: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1AssetDiscoverySpecJsonOptions(
	  /** Optional. The character encoding of the data. The default is UTF-8. */
		encoding: Option[String] = None,
	  /** Optional. Whether to disable the inference of data type for Json data. If true, all columns will be registered as their primitive types (strings, number or boolean). */
		disableTypeInference: Option[Boolean] = None
	)
	
	object GoogleCloudDataplexV1AssetDiscoveryStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SCHEDULED, IN_PROGRESS, PAUSED, DISABLED }
	}
	case class GoogleCloudDataplexV1AssetDiscoveryStatus(
	  /** The current status of the discovery feature. */
		state: Option[Schema.GoogleCloudDataplexV1AssetDiscoveryStatus.StateEnum] = None,
	  /** Additional information about the current state. */
		message: Option[String] = None,
	  /** Last update time of the status. */
		updateTime: Option[String] = None,
	  /** The start time of the last discovery run. */
		lastRunTime: Option[String] = None,
	  /** Data Stats of the asset reported by discovery. */
		stats: Option[Schema.GoogleCloudDataplexV1AssetDiscoveryStatusStats] = None,
	  /** The duration of the last discovery run. */
		lastRunDuration: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1AssetDiscoveryStatusStats(
	  /** The count of data items within the referenced resource. */
		dataItems: Option[String] = None,
	  /** The number of stored data bytes within the referenced resource. */
		dataSize: Option[String] = None,
	  /** The count of table entities within the referenced resource. */
		tables: Option[String] = None,
	  /** The count of fileset entities within the referenced resource. */
		filesets: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListAssetsResponse(
	  /** Asset under the given parent zone. */
		assets: Option[List[Schema.GoogleCloudDataplexV1Asset]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDataplexV1Task {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
	}
	case class GoogleCloudDataplexV1Task(
	  /** Output only. The relative resource name of the task, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/ tasks/{task_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the task. This ID will be different if the task is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. The time when the task was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the task was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of the task. */
		description: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Output only. Current state of the task. */
		state: Option[Schema.GoogleCloudDataplexV1Task.StateEnum] = None,
	  /** Optional. User-defined labels for the task. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Spec related to how often and when a task should be triggered. */
		triggerSpec: Option[Schema.GoogleCloudDataplexV1TaskTriggerSpec] = None,
	  /** Required. Spec related to how a task is executed. */
		executionSpec: Option[Schema.GoogleCloudDataplexV1TaskExecutionSpec] = None,
	  /** Output only. Status of the latest task executions. */
		executionStatus: Option[Schema.GoogleCloudDataplexV1TaskExecutionStatus] = None,
	  /** Config related to running custom Spark tasks. */
		spark: Option[Schema.GoogleCloudDataplexV1TaskSparkTaskConfig] = None,
	  /** Config related to running scheduled Notebooks. */
		notebook: Option[Schema.GoogleCloudDataplexV1TaskNotebookTaskConfig] = None
	)
	
	object GoogleCloudDataplexV1TaskTriggerSpec {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ON_DEMAND, RECURRING }
	}
	case class GoogleCloudDataplexV1TaskTriggerSpec(
	  /** Required. Immutable. Trigger type of the user-specified Task. */
		`type`: Option[Schema.GoogleCloudDataplexV1TaskTriggerSpec.TypeEnum] = None,
	  /** Optional. The first run of the task will be after this time. If not specified, the task will run shortly after being submitted if ON_DEMAND and based on the schedule if RECURRING. */
		startTime: Option[String] = None,
	  /** Optional. Prevent the task from executing. This does not cancel already running tasks. It is intended to temporarily disable RECURRING tasks. */
		disabled: Option[Boolean] = None,
	  /** Optional. Number of retry attempts before aborting. Set to zero to never attempt to retry a failed task. */
		maxRetries: Option[Int] = None,
	  /** Optional. Cron schedule (https://en.wikipedia.org/wiki/Cron) for running tasks periodically. To explicitly set a timezone to the cron tab, apply a prefix in the cron tab: "CRON_TZ=${IANA_TIME_ZONE}" or "TZ=${IANA_TIME_ZONE}". The ${IANA_TIME_ZONE} may only be a valid string from IANA time zone database. For example, CRON_TZ=America/New_York 1 &#42; &#42; &#42; &#42;, or TZ=America/New_York 1 &#42; &#42; &#42; &#42;. This field is required for RECURRING tasks. */
		schedule: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1TaskExecutionSpec(
	  /** Optional. The arguments to pass to the task. The args can use placeholders of the format ${placeholder} as part of key/value string. These will be interpolated before passing the args to the driver. Currently supported placeholders: - ${task_id} - ${job_time} To pass positional args, set the key as TASK_ARGS. The value should be a comma-separated string of all the positional arguments. To use a delimiter other than comma, refer to https://cloud.google.com/sdk/gcloud/reference/topic/escaping. In case of other keys being present in the args, then TASK_ARGS will be passed as the last argument. */
		args: Option[Map[String, String]] = None,
	  /** Required. Service account to use to execute a task. If not provided, the default Compute service account for the project is used. */
		serviceAccount: Option[String] = None,
	  /** Optional. The project in which jobs are run. By default, the project containing the Lake is used. If a project is provided, the ExecutionSpec.service_account must belong to this project. */
		project: Option[String] = None,
	  /** Optional. The maximum duration after which the job execution is expired. */
		maxJobExecutionLifetime: Option[String] = None,
	  /** Optional. The Cloud KMS key to use for encryption, of the form: projects/{project_number}/locations/{location_id}/keyRings/{key-ring-name}/cryptoKeys/{key-name}. */
		kmsKey: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1TaskExecutionStatus(
	  /** Output only. Last update time of the status. */
		updateTime: Option[String] = None,
	  /** Output only. latest job execution */
		latestJob: Option[Schema.GoogleCloudDataplexV1Job] = None
	)
	
	object GoogleCloudDataplexV1Job {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELLING, CANCELLED, SUCCEEDED, FAILED, ABORTED }
		enum ServiceEnum extends Enum[ServiceEnum] { case SERVICE_UNSPECIFIED, DATAPROC }
		enum TriggerEnum extends Enum[TriggerEnum] { case TRIGGER_UNSPECIFIED, TASK_CONFIG, RUN_REQUEST }
	}
	case class GoogleCloudDataplexV1Job(
	  /** Output only. The relative resource name of the job, of the form: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/tasks/{task_id}/jobs/{job_id}. */
		name: Option[String] = None,
	  /** Output only. System generated globally unique ID for the job. */
		uid: Option[String] = None,
	  /** Output only. The time when the job was started. */
		startTime: Option[String] = None,
	  /** Output only. The time when the job ended. */
		endTime: Option[String] = None,
	  /** Output only. Execution state for the job. */
		state: Option[Schema.GoogleCloudDataplexV1Job.StateEnum] = None,
	  /** Output only. The number of times the job has been retried (excluding the initial attempt). */
		retryCount: Option[Int] = None,
	  /** Output only. The underlying service running a job. */
		service: Option[Schema.GoogleCloudDataplexV1Job.ServiceEnum] = None,
	  /** Output only. The full resource name for the job run under a particular service. */
		serviceJob: Option[String] = None,
	  /** Output only. Additional information about the current state. */
		message: Option[String] = None,
	  /** Output only. User-defined labels for the task. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Job execution trigger. */
		trigger: Option[Schema.GoogleCloudDataplexV1Job.TriggerEnum] = None,
	  /** Output only. Spec related to how a task is executed. */
		executionSpec: Option[Schema.GoogleCloudDataplexV1TaskExecutionSpec] = None
	)
	
	case class GoogleCloudDataplexV1TaskSparkTaskConfig(
	  /** The Cloud Storage URI of the jar file that contains the main class. The execution args are passed in as a sequence of named process arguments (--key=value). */
		mainJarFileUri: Option[String] = None,
	  /** The name of the driver's main class. The jar file that contains the class must be in the default CLASSPATH or specified in jar_file_uris. The execution args are passed in as a sequence of named process arguments (--key=value). */
		mainClass: Option[String] = None,
	  /** The Gcloud Storage URI of the main Python file to use as the driver. Must be a .py file. The execution args are passed in as a sequence of named process arguments (--key=value). */
		pythonScriptFile: Option[String] = None,
	  /** A reference to a query file. This should be the Cloud Storage URI of the query file. The execution args are used to declare a set of script variables (set key="value";). */
		sqlScriptFile: Option[String] = None,
	  /** The query text. The execution args are used to declare a set of script variables (set key="value";). */
		sqlScript: Option[String] = None,
	  /** Optional. Cloud Storage URIs of files to be placed in the working directory of each executor. */
		fileUris: Option[List[String]] = None,
	  /** Optional. Cloud Storage URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None,
	  /** Optional. Infrastructure specification for the execution. */
		infrastructureSpec: Option[Schema.GoogleCloudDataplexV1TaskInfrastructureSpec] = None
	)
	
	case class GoogleCloudDataplexV1TaskInfrastructureSpec(
	  /** Compute resources needed for a Task when using Dataproc Serverless. */
		batch: Option[Schema.GoogleCloudDataplexV1TaskInfrastructureSpecBatchComputeResources] = None,
	  /** Container Image Runtime Configuration. */
		containerImage: Option[Schema.GoogleCloudDataplexV1TaskInfrastructureSpecContainerImageRuntime] = None,
	  /** Vpc network. */
		vpcNetwork: Option[Schema.GoogleCloudDataplexV1TaskInfrastructureSpecVpcNetwork] = None
	)
	
	case class GoogleCloudDataplexV1TaskInfrastructureSpecBatchComputeResources(
	  /** Optional. Total number of job executors. Executor Count should be between 2 and 100. Default=2 */
		executorsCount: Option[Int] = None,
	  /** Optional. Max configurable executors. If max_executors_count > executors_count, then auto-scaling is enabled. Max Executor Count should be between 2 and 1000. Default=1000 */
		maxExecutorsCount: Option[Int] = None
	)
	
	case class GoogleCloudDataplexV1TaskInfrastructureSpecContainerImageRuntime(
	  /** Optional. Container image to use. */
		image: Option[String] = None,
	  /** Optional. A list of Java JARS to add to the classpath. Valid input includes Cloud Storage URIs to Jar binaries. For example, gs://bucket-name/my/path/to/file.jar */
		javaJars: Option[List[String]] = None,
	  /** Optional. A list of python packages to be installed. Valid formats include Cloud Storage URI to a PIP installable library. For example, gs://bucket-name/my/path/to/lib.tar.gz */
		pythonPackages: Option[List[String]] = None,
	  /** Optional. Override to common configuration of open source components installed on the Dataproc cluster. The properties to set on daemon config files. Property keys are specified in prefix:property format, for example core:hadoop.tmp.dir. For more information, see Cluster properties (https://cloud.google.com/dataproc/docs/concepts/cluster-properties). */
		properties: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDataplexV1TaskInfrastructureSpecVpcNetwork(
	  /** Optional. The Cloud VPC network in which the job is run. By default, the Cloud VPC network named Default within the project is used. */
		network: Option[String] = None,
	  /** Optional. The Cloud VPC sub-network in which the job is run. */
		subNetwork: Option[String] = None,
	  /** Optional. List of network tags to apply to the job. */
		networkTags: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1TaskNotebookTaskConfig(
	  /** Required. Path to input notebook. This can be the Cloud Storage URI of the notebook file or the path to a Notebook Content. The execution args are accessible as environment variables (TASK_key=value). */
		notebook: Option[String] = None,
	  /** Optional. Infrastructure specification for the execution. */
		infrastructureSpec: Option[Schema.GoogleCloudDataplexV1TaskInfrastructureSpec] = None,
	  /** Optional. Cloud Storage URIs of files to be placed in the working directory of each executor. */
		fileUris: Option[List[String]] = None,
	  /** Optional. Cloud Storage URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListTasksResponse(
	  /** Tasks under the given parent lake. */
		tasks: Option[List[Schema.GoogleCloudDataplexV1Task]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachableLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1ListJobsResponse(
	  /** Jobs under a given task. */
		jobs: Option[List[Schema.GoogleCloudDataplexV1Job]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1RunTaskRequest(
	  /** Optional. User-defined labels for the task. If the map is left empty, the task will run with existing labels from task definition. If the map contains an entry with a new key, the same will be added to existing set of labels. If the map contains an entry with an existing label key in task definition, the task will run with new label value for that entry. Clearing an existing label will require label value to be explicitly set to a hyphen "-". The label value cannot be empty. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Execution spec arguments. If the map is left empty, the task will run with existing execution spec args from task definition. If the map contains an entry with a new key, the same will be added to existing set of args. If the map contains an entry with an existing arg key in task definition, the task will run with new arg value for that entry. Clearing an existing arg will require arg value to be explicitly set to a hyphen "-". The arg value cannot be empty. */
		args: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDataplexV1RunTaskResponse(
	  /** Jobs created by RunTask API. */
		job: Option[Schema.GoogleCloudDataplexV1Job] = None
	)
	
	case class GoogleCloudDataplexV1CancelJobRequest(
	
	)
	
	object GoogleCloudDataplexV1Environment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
	}
	case class GoogleCloudDataplexV1Environment(
	  /** Output only. The relative resource name of the environment, of the form: projects/{project_id}/locations/{location_id}/lakes/{lake_id}/environment/{environment_id} */
		name: Option[String] = None,
	  /** Optional. User friendly display name. */
		displayName: Option[String] = None,
	  /** Output only. System generated globally unique ID for the environment. This ID will be different if the environment is deleted and re-created with the same name. */
		uid: Option[String] = None,
	  /** Output only. Environment creation time. */
		createTime: Option[String] = None,
	  /** Output only. The time when the environment was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. User defined labels for the environment. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Description of the environment. */
		description: Option[String] = None,
	  /** Output only. Current state of the environment. */
		state: Option[Schema.GoogleCloudDataplexV1Environment.StateEnum] = None,
	  /** Required. Infrastructure specification for the Environment. */
		infrastructureSpec: Option[Schema.GoogleCloudDataplexV1EnvironmentInfrastructureSpec] = None,
	  /** Optional. Configuration for sessions created for this environment. */
		sessionSpec: Option[Schema.GoogleCloudDataplexV1EnvironmentSessionSpec] = None,
	  /** Output only. Status of sessions created for this environment. */
		sessionStatus: Option[Schema.GoogleCloudDataplexV1EnvironmentSessionStatus] = None,
	  /** Output only. URI Endpoints to access sessions associated with the Environment. */
		endpoints: Option[Schema.GoogleCloudDataplexV1EnvironmentEndpoints] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentInfrastructureSpec(
	  /** Optional. Compute resources needed for analyze interactive workloads. */
		compute: Option[Schema.GoogleCloudDataplexV1EnvironmentInfrastructureSpecComputeResources] = None,
	  /** Required. Software Runtime Configuration for analyze interactive workloads. */
		osImage: Option[Schema.GoogleCloudDataplexV1EnvironmentInfrastructureSpecOsImageRuntime] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentInfrastructureSpecComputeResources(
	  /** Optional. Size in GB of the disk. Default is 100 GB. */
		diskSizeGb: Option[Int] = None,
	  /** Optional. Total number of nodes in the sessions created for this environment. */
		nodeCount: Option[Int] = None,
	  /** Optional. Max configurable nodes. If max_node_count > node_count, then auto-scaling is enabled. */
		maxNodeCount: Option[Int] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentInfrastructureSpecOsImageRuntime(
	  /** Required. Dataplex Image version. */
		imageVersion: Option[String] = None,
	  /** Optional. List of Java jars to be included in the runtime environment. Valid input includes Cloud Storage URIs to Jar binaries. For example, gs://bucket-name/my/path/to/file.jar */
		javaLibraries: Option[List[String]] = None,
	  /** Optional. A list of python packages to be installed. Valid formats include Cloud Storage URI to a PIP installable library. For example, gs://bucket-name/my/path/to/lib.tar.gz */
		pythonPackages: Option[List[String]] = None,
	  /** Optional. Spark properties to provide configuration for use in sessions created for this environment. The properties to set on daemon config files. Property keys are specified in prefix:property format. The prefix must be "spark". */
		properties: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentSessionSpec(
	  /** Optional. The idle time configuration of the session. The session will be auto-terminated at the end of this period. */
		maxIdleDuration: Option[String] = None,
	  /** Optional. If True, this causes sessions to be pre-created and available for faster startup to enable interactive exploration use-cases. This defaults to False to avoid additional billed charges. These can only be set to True for the environment with name set to "default", and with default configuration. */
		enableFastStartup: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentSessionStatus(
	  /** Output only. Queries over sessions to mark whether the environment is currently active or not */
		active: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1EnvironmentEndpoints(
	  /** Output only. URI to serve notebook APIs */
		notebooks: Option[String] = None,
	  /** Output only. URI to serve SQL APIs */
		sql: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListEnvironmentsResponse(
	  /** Environments under the given parent lake. */
		environments: Option[List[Schema.GoogleCloudDataplexV1Environment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ListSessionsResponse(
	  /** Sessions under a given environment. */
		sessions: Option[List[Schema.GoogleCloudDataplexV1Session]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDataplexV1Session {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, DELETING, ACTION_REQUIRED }
	}
	case class GoogleCloudDataplexV1Session(
	  /** Output only. The relative resource name of the content, of the form: projects/{project_id}/locations/{location_id}/lakes/{lake_id}/environment/{environment_id}/sessions/{session_id} */
		name: Option[String] = None,
	  /** Output only. Email of user running the session. */
		userId: Option[String] = None,
	  /** Output only. Session start time. */
		createTime: Option[String] = None,
	  /** Output only. State of Session */
		state: Option[Schema.GoogleCloudDataplexV1Session.StateEnum] = None
	)
	
	case class GoogleCloudLocationListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.GoogleCloudLocationLocation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudLocationLocation(
	  /** Resource name for the location, which may vary between implementations. For example: "projects/example-project/locations/us-east1" */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: "us-east1". */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"}  */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDataplexV1OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object GoogleCloudDataplexV1DiscoveryEvent {
		enum TypeEnum extends Enum[TypeEnum] { case EVENT_TYPE_UNSPECIFIED, CONFIG, ENTITY_CREATED, ENTITY_UPDATED, ENTITY_DELETED, PARTITION_CREATED, PARTITION_UPDATED, PARTITION_DELETED, TABLE_PUBLISHED, TABLE_UPDATED, TABLE_IGNORED, TABLE_DELETED }
	}
	case class GoogleCloudDataplexV1DiscoveryEvent(
	  /** The log message. */
		message: Option[String] = None,
	  /** The id of the associated lake. */
		lakeId: Option[String] = None,
	  /** The id of the associated zone. */
		zoneId: Option[String] = None,
	  /** The id of the associated asset. */
		assetId: Option[String] = None,
	  /** The data location associated with the event. */
		dataLocation: Option[String] = None,
	  /** The id of the associated datascan for standalone discovery. */
		datascanId: Option[String] = None,
	  /** The type of the event being logged. */
		`type`: Option[Schema.GoogleCloudDataplexV1DiscoveryEvent.TypeEnum] = None,
	  /** Details about discovery configuration in effect. */
		config: Option[Schema.GoogleCloudDataplexV1DiscoveryEventConfigDetails] = None,
	  /** Details about the entity associated with the event. */
		entity: Option[Schema.GoogleCloudDataplexV1DiscoveryEventEntityDetails] = None,
	  /** Details about the partition associated with the event. */
		partition: Option[Schema.GoogleCloudDataplexV1DiscoveryEventPartitionDetails] = None,
	  /** Details about the action associated with the event. */
		action: Option[Schema.GoogleCloudDataplexV1DiscoveryEventActionDetails] = None,
	  /** Details about the BigQuery table publishing associated with the event. */
		table: Option[Schema.GoogleCloudDataplexV1DiscoveryEventTableDetails] = None
	)
	
	case class GoogleCloudDataplexV1DiscoveryEventConfigDetails(
	  /** A list of discovery configuration parameters in effect. The keys are the field paths within DiscoverySpec. Eg. includePatterns, excludePatterns, csvOptions.disableTypeInference, etc. */
		parameters: Option[Map[String, String]] = None
	)
	
	object GoogleCloudDataplexV1DiscoveryEventEntityDetails {
		enum TypeEnum extends Enum[TypeEnum] { case ENTITY_TYPE_UNSPECIFIED, TABLE, FILESET }
	}
	case class GoogleCloudDataplexV1DiscoveryEventEntityDetails(
	  /** The name of the entity resource. The name is the fully-qualified resource name. */
		entity: Option[String] = None,
	  /** The type of the entity resource. */
		`type`: Option[Schema.GoogleCloudDataplexV1DiscoveryEventEntityDetails.TypeEnum] = None
	)
	
	object GoogleCloudDataplexV1DiscoveryEventPartitionDetails {
		enum TypeEnum extends Enum[TypeEnum] { case ENTITY_TYPE_UNSPECIFIED, TABLE, FILESET }
	}
	case class GoogleCloudDataplexV1DiscoveryEventPartitionDetails(
	  /** The name to the partition resource. The name is the fully-qualified resource name. */
		partition: Option[String] = None,
	  /** The name to the containing entity resource. The name is the fully-qualified resource name. */
		entity: Option[String] = None,
	  /** The type of the containing entity resource. */
		`type`: Option[Schema.GoogleCloudDataplexV1DiscoveryEventPartitionDetails.TypeEnum] = None,
	  /** The locations of the data items (e.g., a Cloud Storage objects) sampled for metadata inference. */
		sampledDataLocations: Option[List[String]] = None
	)
	
	case class GoogleCloudDataplexV1DiscoveryEventActionDetails(
	  /** The type of action. Eg. IncompatibleDataSchema, InvalidDataFormat */
		`type`: Option[String] = None,
	  /** The human readable issue associated with the action. */
		issue: Option[String] = None
	)
	
	object GoogleCloudDataplexV1DiscoveryEventTableDetails {
		enum TypeEnum extends Enum[TypeEnum] { case TABLE_TYPE_UNSPECIFIED, EXTERNAL_TABLE, BIGLAKE_TABLE, OBJECT_TABLE }
	}
	case class GoogleCloudDataplexV1DiscoveryEventTableDetails(
	  /** The fully-qualified resource name of the table resource. */
		table: Option[String] = None,
	  /** The type of the table resource. */
		`type`: Option[Schema.GoogleCloudDataplexV1DiscoveryEventTableDetails.TypeEnum] = None
	)
	
	object GoogleCloudDataplexV1JobEvent {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, CANCELLED, ABORTED }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SPARK, NOTEBOOK }
		enum ServiceEnum extends Enum[ServiceEnum] { case SERVICE_UNSPECIFIED, DATAPROC }
		enum ExecutionTriggerEnum extends Enum[ExecutionTriggerEnum] { case EXECUTION_TRIGGER_UNSPECIFIED, TASK_CONFIG, RUN_REQUEST }
	}
	case class GoogleCloudDataplexV1JobEvent(
	  /** The log message. */
		message: Option[String] = None,
	  /** The unique id identifying the job. */
		jobId: Option[String] = None,
	  /** The time when the job started running. */
		startTime: Option[String] = None,
	  /** The time when the job ended running. */
		endTime: Option[String] = None,
	  /** The job state on completion. */
		state: Option[Schema.GoogleCloudDataplexV1JobEvent.StateEnum] = None,
	  /** The number of retries. */
		retries: Option[Int] = None,
	  /** The type of the job. */
		`type`: Option[Schema.GoogleCloudDataplexV1JobEvent.TypeEnum] = None,
	  /** The service used to execute the job. */
		service: Option[Schema.GoogleCloudDataplexV1JobEvent.ServiceEnum] = None,
	  /** The reference to the job within the service. */
		serviceJob: Option[String] = None,
	  /** Job execution trigger. */
		executionTrigger: Option[Schema.GoogleCloudDataplexV1JobEvent.ExecutionTriggerEnum] = None
	)
	
	object GoogleCloudDataplexV1SessionEvent {
		enum TypeEnum extends Enum[TypeEnum] { case EVENT_TYPE_UNSPECIFIED, START, STOP, QUERY, CREATE }
	}
	case class GoogleCloudDataplexV1SessionEvent(
	  /** The log message. */
		message: Option[String] = None,
	  /** The information about the user that created the session. It will be the email address of the user. */
		userId: Option[String] = None,
	  /** Unique identifier for the session. */
		sessionId: Option[String] = None,
	  /** The type of the event. */
		`type`: Option[Schema.GoogleCloudDataplexV1SessionEvent.TypeEnum] = None,
	  /** The execution details of the query. */
		query: Option[Schema.GoogleCloudDataplexV1SessionEventQueryDetail] = None,
	  /** The status of the event. */
		eventSucceeded: Option[Boolean] = None,
	  /** If the session is associated with an environment with fast startup enabled, and was created before being assigned to a user. */
		fastStartupEnabled: Option[Boolean] = None,
	  /** The idle duration of a warm pooled session before it is assigned to user. */
		unassignedDuration: Option[String] = None
	)
	
	object GoogleCloudDataplexV1SessionEventQueryDetail {
		enum EngineEnum extends Enum[EngineEnum] { case ENGINE_UNSPECIFIED, SPARK_SQL, BIGQUERY }
	}
	case class GoogleCloudDataplexV1SessionEventQueryDetail(
	  /** The unique Query id identifying the query. */
		queryId: Option[String] = None,
	  /** The query text executed. */
		queryText: Option[String] = None,
	  /** Query Execution engine. */
		engine: Option[Schema.GoogleCloudDataplexV1SessionEventQueryDetail.EngineEnum] = None,
	  /** Time taken for execution of the query. */
		duration: Option[String] = None,
	  /** The size of results the query produced. */
		resultSizeBytes: Option[String] = None,
	  /** The data processed by the query. */
		dataProcessedBytes: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1ImportItem(
	  /** Information about an entry and its attached aspects. */
		entry: Option[Schema.GoogleCloudDataplexV1Entry] = None,
	  /** The fields to update, in paths that are relative to the Entry resource. Separate each field with a comma.In FULL entry sync mode, Dataplex includes the paths of all of the fields for an entry that can be modified, including aspects. This means that Dataplex replaces the existing entry with the entry in the metadata import file. All modifiable fields are updated, regardless of the fields that are listed in the update mask, and regardless of whether a field is present in the entry object.The update_mask field is ignored when an entry is created or re-created.Dataplex also determines which entries and aspects to modify by comparing the values and timestamps that you provide in the metadata import file with the values and timestamps that exist in your project. For more information, see Comparison logic (https://cloud.google.com/dataplex/docs/import-metadata#data-modification-logic). */
		updateMask: Option[String] = None,
	  /** The aspects to modify. Supports the following syntaxes: {aspect_type_reference}: matches aspects that belong to the specified aspect type and are attached directly to the entry. {aspect_type_reference}@{path}: matches aspects that belong to the specified aspect type and path. {aspect_type_reference}@&#42;: matches aspects that belong to the specified aspect type for all paths.Replace {aspect_type_reference} with a reference to the aspect type, in the format {project_id_or_number}.{location_id}.{aspect_type_id}.If you leave this field empty, it is treated as specifying exactly those aspects that are present within the specified entry.In FULL entry sync mode, Dataplex implicitly adds the keys for all of the required aspects of an entry. */
		aspectKeys: Option[List[String]] = None
	)
	
	object GoogleCloudDataplexV1DataScanEvent {
		enum TypeEnum extends Enum[TypeEnum] { case SCAN_TYPE_UNSPECIFIED, DATA_PROFILE, DATA_QUALITY, DATA_DISCOVERY }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, SUCCEEDED, FAILED, CANCELLED, CREATED }
		enum TriggerEnum extends Enum[TriggerEnum] { case TRIGGER_UNSPECIFIED, ON_DEMAND, SCHEDULE }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, FULL, INCREMENTAL }
	}
	case class GoogleCloudDataplexV1DataScanEvent(
	  /** The data source of the data scan */
		dataSource: Option[String] = None,
	  /** The identifier of the specific data scan job this log entry is for. */
		jobId: Option[String] = None,
	  /** The time when the data scan job was created. */
		createTime: Option[String] = None,
	  /** The time when the data scan job started to run. */
		startTime: Option[String] = None,
	  /** The time when the data scan job finished. */
		endTime: Option[String] = None,
	  /** The type of the data scan. */
		`type`: Option[Schema.GoogleCloudDataplexV1DataScanEvent.TypeEnum] = None,
	  /** The status of the data scan job. */
		state: Option[Schema.GoogleCloudDataplexV1DataScanEvent.StateEnum] = None,
	  /** The message describing the data scan job event. */
		message: Option[String] = None,
	  /** A version identifier of the spec which was used to execute this job. */
		specVersion: Option[String] = None,
	  /** The trigger type of the data scan job. */
		trigger: Option[Schema.GoogleCloudDataplexV1DataScanEvent.TriggerEnum] = None,
	  /** The scope of the data scan (e.g. full, incremental). */
		scope: Option[Schema.GoogleCloudDataplexV1DataScanEvent.ScopeEnum] = None,
	  /** Data profile result for data profile type data scan. */
		dataProfile: Option[Schema.GoogleCloudDataplexV1DataScanEventDataProfileResult] = None,
	  /** Data quality result for data quality type data scan. */
		dataQuality: Option[Schema.GoogleCloudDataplexV1DataScanEventDataQualityResult] = None,
	  /** Applied configs for data profile type data scan. */
		dataProfileConfigs: Option[Schema.GoogleCloudDataplexV1DataScanEventDataProfileAppliedConfigs] = None,
	  /** Applied configs for data quality type data scan. */
		dataQualityConfigs: Option[Schema.GoogleCloudDataplexV1DataScanEventDataQualityAppliedConfigs] = None,
	  /** The result of post scan actions. */
		postScanActionsResult: Option[Schema.GoogleCloudDataplexV1DataScanEventPostScanActionsResult] = None
	)
	
	case class GoogleCloudDataplexV1DataScanEventDataProfileResult(
	  /** The count of rows processed in the data scan job. */
		rowCount: Option[String] = None
	)
	
	case class GoogleCloudDataplexV1DataScanEventDataQualityResult(
	  /** The count of rows processed in the data scan job. */
		rowCount: Option[String] = None,
	  /** Whether the data quality result was pass or not. */
		passed: Option[Boolean] = None,
	  /** The result of each dimension for data quality result. The key of the map is the name of the dimension. The value is the bool value depicting whether the dimension result was pass or not. */
		dimensionPassed: Option[Map[String, Boolean]] = None,
	  /** The table-level data quality score for the data scan job.The data quality score ranges between 0, 100 (up to two decimal points). */
		score: Option[BigDecimal] = None,
	  /** The score of each dimension for data quality result. The key of the map is the name of the dimension. The value is the data quality score for the dimension.The score ranges between 0, 100 (up to two decimal points). */
		dimensionScore: Option[Map[String, BigDecimal]] = None,
	  /** The score of each column scanned in the data scan job. The key of the map is the name of the column. The value is the data quality score for the column.The score ranges between 0, 100 (up to two decimal points). */
		columnScore: Option[Map[String, BigDecimal]] = None
	)
	
	case class GoogleCloudDataplexV1DataScanEventDataProfileAppliedConfigs(
	  /** The percentage of the records selected from the dataset for DataScan. Value ranges between 0.0 and 100.0. Value 0.0 or 100.0 imply that sampling was not applied. */
		samplingPercent: Option[BigDecimal] = None,
	  /** Boolean indicating whether a row filter was applied in the DataScan job. */
		rowFilterApplied: Option[Boolean] = None,
	  /** Boolean indicating whether a column filter was applied in the DataScan job. */
		columnFilterApplied: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataScanEventDataQualityAppliedConfigs(
	  /** The percentage of the records selected from the dataset for DataScan. Value ranges between 0.0 and 100.0. Value 0.0 or 100.0 imply that sampling was not applied. */
		samplingPercent: Option[BigDecimal] = None,
	  /** Boolean indicating whether a row filter was applied in the DataScan job. */
		rowFilterApplied: Option[Boolean] = None
	)
	
	case class GoogleCloudDataplexV1DataScanEventPostScanActionsResult(
	  /** The result of BigQuery export post scan action. */
		bigqueryExportResult: Option[Schema.GoogleCloudDataplexV1DataScanEventPostScanActionsResultBigQueryExportResult] = None
	)
	
	object GoogleCloudDataplexV1DataScanEventPostScanActionsResultBigQueryExportResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, SKIPPED }
	}
	case class GoogleCloudDataplexV1DataScanEventPostScanActionsResultBigQueryExportResult(
	  /** Execution state for the BigQuery exporting. */
		state: Option[Schema.GoogleCloudDataplexV1DataScanEventPostScanActionsResultBigQueryExportResult.StateEnum] = None,
	  /** Additional information about the BigQuery exporting. */
		message: Option[String] = None
	)
	
	object GoogleCloudDataplexV1DataQualityScanRuleResult {
		enum RuleTypeEnum extends Enum[RuleTypeEnum] { case RULE_TYPE_UNSPECIFIED, NON_NULL_EXPECTATION, RANGE_EXPECTATION, REGEX_EXPECTATION, ROW_CONDITION_EXPECTATION, SET_EXPECTATION, STATISTIC_RANGE_EXPECTATION, TABLE_CONDITION_EXPECTATION, UNIQUENESS_EXPECTATION, SQL_ASSERTION }
		enum EvalutionTypeEnum extends Enum[EvalutionTypeEnum] { case EVALUATION_TYPE_UNSPECIFIED, PER_ROW, AGGREGATE }
		enum ResultEnum extends Enum[ResultEnum] { case RESULT_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudDataplexV1DataQualityScanRuleResult(
	  /** Identifier of the specific data scan job this log entry is for. */
		jobId: Option[String] = None,
	  /** The data source of the data scan (e.g. BigQuery table name). */
		dataSource: Option[String] = None,
	  /** The column which this rule is evaluated against. */
		column: Option[String] = None,
	  /** The name of the data quality rule. */
		ruleName: Option[String] = None,
	  /** The type of the data quality rule. */
		ruleType: Option[Schema.GoogleCloudDataplexV1DataQualityScanRuleResult.RuleTypeEnum] = None,
	  /** The evaluation type of the data quality rule. */
		evalutionType: Option[Schema.GoogleCloudDataplexV1DataQualityScanRuleResult.EvalutionTypeEnum] = None,
	  /** The dimension of the data quality rule. */
		ruleDimension: Option[String] = None,
	  /** The passing threshold (0.0, 100.0) of the data quality rule. */
		thresholdPercent: Option[BigDecimal] = None,
	  /** The result of the data quality rule. */
		result: Option[Schema.GoogleCloudDataplexV1DataQualityScanRuleResult.ResultEnum] = None,
	  /** The number of rows evaluated against the data quality rule. This field is only valid for rules of PER_ROW evaluation type. */
		evaluatedRowCount: Option[String] = None,
	  /** The number of rows which passed a rule evaluation. This field is only valid for rules of PER_ROW evaluation type. */
		passedRowCount: Option[String] = None,
	  /** The number of rows with null values in the specified column. */
		nullRowCount: Option[String] = None,
	  /** The number of rows returned by the SQL statement in a SQL assertion rule. This field is only valid for SQL assertion rules. */
		assertionRowCount: Option[String] = None
	)
	
	object GoogleCloudDataplexV1GovernanceEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case EVENT_TYPE_UNSPECIFIED, RESOURCE_IAM_POLICY_UPDATE, BIGQUERY_TABLE_CREATE, BIGQUERY_TABLE_UPDATE, BIGQUERY_TABLE_DELETE, BIGQUERY_CONNECTION_CREATE, BIGQUERY_CONNECTION_UPDATE, BIGQUERY_CONNECTION_DELETE, BIGQUERY_TAXONOMY_CREATE, BIGQUERY_POLICY_TAG_CREATE, BIGQUERY_POLICY_TAG_DELETE, BIGQUERY_POLICY_TAG_SET_IAM_POLICY, ACCESS_POLICY_UPDATE, GOVERNANCE_RULE_MATCHED_RESOURCES, GOVERNANCE_RULE_SEARCH_LIMIT_EXCEEDS, GOVERNANCE_RULE_ERRORS, GOVERNANCE_RULE_PROCESSING }
	}
	case class GoogleCloudDataplexV1GovernanceEvent(
	  /** The log message. */
		message: Option[String] = None,
	  /** The type of the event. */
		eventType: Option[Schema.GoogleCloudDataplexV1GovernanceEvent.EventTypeEnum] = None,
	  /** Entity resource information if the log event is associated with a specific entity. */
		entity: Option[Schema.GoogleCloudDataplexV1GovernanceEventEntity] = None
	)
	
	object GoogleCloudDataplexV1GovernanceEventEntity {
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case ENTITY_TYPE_UNSPECIFIED, TABLE, FILESET }
	}
	case class GoogleCloudDataplexV1GovernanceEventEntity(
	  /** The Entity resource the log event is associated with. Format: projects/{project_number}/locations/{location_id}/lakes/{lake_id}/zones/{zone_id}/entities/{entity_id} */
		entity: Option[String] = None,
	  /** Type of entity. */
		entityType: Option[Schema.GoogleCloudDataplexV1GovernanceEventEntity.EntityTypeEnum] = None
	)
}
