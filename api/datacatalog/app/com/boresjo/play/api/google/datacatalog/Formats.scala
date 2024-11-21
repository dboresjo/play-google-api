package com.boresjo.play.api.google.datacatalog

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGoogleCloudDatacatalogV1SearchCatalogRequest: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogRequest] = Json.format[Schema.GoogleCloudDatacatalogV1SearchCatalogRequest]
	given fmtGoogleCloudDatacatalogV1SearchCatalogRequestScope: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogRequestScope] = Json.format[Schema.GoogleCloudDatacatalogV1SearchCatalogRequestScope]
	given fmtGoogleCloudDatacatalogV1SearchCatalogResponse: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogResponse] = Json.format[Schema.GoogleCloudDatacatalogV1SearchCatalogResponse]
	given fmtGoogleCloudDatacatalogV1SearchCatalogResult: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogResult] = Json.format[Schema.GoogleCloudDatacatalogV1SearchCatalogResult]
	given fmtGoogleCloudDatacatalogV1SearchCatalogResultSearchResultTypeEnum: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.SearchResultTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.SearchResultTypeEnum]
	given fmtGoogleCloudDatacatalogV1SearchCatalogResultIntegratedSystemEnum: Format[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.IntegratedSystemEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.IntegratedSystemEnum]
	given fmtGoogleCloudDatacatalogV1EntryGroup: Format[Schema.GoogleCloudDatacatalogV1EntryGroup] = Json.format[Schema.GoogleCloudDatacatalogV1EntryGroup]
	given fmtGoogleCloudDatacatalogV1SystemTimestamps: Format[Schema.GoogleCloudDatacatalogV1SystemTimestamps] = Json.format[Schema.GoogleCloudDatacatalogV1SystemTimestamps]
	given fmtGoogleCloudDatacatalogV1ListEntryGroupsResponse: Format[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse]
	given fmtGoogleCloudDatacatalogV1Entry: Format[Schema.GoogleCloudDatacatalogV1Entry] = Json.format[Schema.GoogleCloudDatacatalogV1Entry]
	given fmtGoogleCloudDatacatalogV1EntryTypeEnum: Format[Schema.GoogleCloudDatacatalogV1Entry.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1Entry.TypeEnum]
	given fmtGoogleCloudDatacatalogV1EntryIntegratedSystemEnum: Format[Schema.GoogleCloudDatacatalogV1Entry.IntegratedSystemEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1Entry.IntegratedSystemEnum]
	given fmtGoogleCloudDatacatalogV1SqlDatabaseSystemSpec: Format[Schema.GoogleCloudDatacatalogV1SqlDatabaseSystemSpec] = Json.format[Schema.GoogleCloudDatacatalogV1SqlDatabaseSystemSpec]
	given fmtGoogleCloudDatacatalogV1LookerSystemSpec: Format[Schema.GoogleCloudDatacatalogV1LookerSystemSpec] = Json.format[Schema.GoogleCloudDatacatalogV1LookerSystemSpec]
	given fmtGoogleCloudDatacatalogV1CloudBigtableSystemSpec: Format[Schema.GoogleCloudDatacatalogV1CloudBigtableSystemSpec] = Json.format[Schema.GoogleCloudDatacatalogV1CloudBigtableSystemSpec]
	given fmtGoogleCloudDatacatalogV1GcsFilesetSpec: Format[Schema.GoogleCloudDatacatalogV1GcsFilesetSpec] = Json.format[Schema.GoogleCloudDatacatalogV1GcsFilesetSpec]
	given fmtGoogleCloudDatacatalogV1BigQueryTableSpec: Format[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec] = Json.format[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec]
	given fmtGoogleCloudDatacatalogV1BigQueryDateShardedSpec: Format[Schema.GoogleCloudDatacatalogV1BigQueryDateShardedSpec] = Json.format[Schema.GoogleCloudDatacatalogV1BigQueryDateShardedSpec]
	given fmtGoogleCloudDatacatalogV1DatabaseTableSpec: Format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec]
	given fmtGoogleCloudDatacatalogV1DataSourceConnectionSpec: Format[Schema.GoogleCloudDatacatalogV1DataSourceConnectionSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DataSourceConnectionSpec]
	given fmtGoogleCloudDatacatalogV1RoutineSpec: Format[Schema.GoogleCloudDatacatalogV1RoutineSpec] = Json.format[Schema.GoogleCloudDatacatalogV1RoutineSpec]
	given fmtGoogleCloudDatacatalogV1DatasetSpec: Format[Schema.GoogleCloudDatacatalogV1DatasetSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DatasetSpec]
	given fmtGoogleCloudDatacatalogV1FilesetSpec: Format[Schema.GoogleCloudDatacatalogV1FilesetSpec] = Json.format[Schema.GoogleCloudDatacatalogV1FilesetSpec]
	given fmtGoogleCloudDatacatalogV1ServiceSpec: Format[Schema.GoogleCloudDatacatalogV1ServiceSpec] = Json.format[Schema.GoogleCloudDatacatalogV1ServiceSpec]
	given fmtGoogleCloudDatacatalogV1ModelSpec: Format[Schema.GoogleCloudDatacatalogV1ModelSpec] = Json.format[Schema.GoogleCloudDatacatalogV1ModelSpec]
	given fmtGoogleCloudDatacatalogV1FeatureOnlineStoreSpec: Format[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec] = Json.format[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec]
	given fmtGoogleCloudDatacatalogV1BusinessContext: Format[Schema.GoogleCloudDatacatalogV1BusinessContext] = Json.format[Schema.GoogleCloudDatacatalogV1BusinessContext]
	given fmtGoogleCloudDatacatalogV1Schema: Format[Schema.GoogleCloudDatacatalogV1Schema] = Json.format[Schema.GoogleCloudDatacatalogV1Schema]
	given fmtGoogleCloudDatacatalogV1UsageSignal: Format[Schema.GoogleCloudDatacatalogV1UsageSignal] = Json.format[Schema.GoogleCloudDatacatalogV1UsageSignal]
	given fmtGoogleCloudDatacatalogV1DataSource: Format[Schema.GoogleCloudDatacatalogV1DataSource] = Json.format[Schema.GoogleCloudDatacatalogV1DataSource]
	given fmtGoogleCloudDatacatalogV1PersonalDetails: Format[Schema.GoogleCloudDatacatalogV1PersonalDetails] = Json.format[Schema.GoogleCloudDatacatalogV1PersonalDetails]
	given fmtGoogleCloudDatacatalogV1GcsFileSpec: Format[Schema.GoogleCloudDatacatalogV1GcsFileSpec] = Json.format[Schema.GoogleCloudDatacatalogV1GcsFileSpec]
	given fmtGoogleCloudDatacatalogV1BigQueryTableSpecTableSourceTypeEnum: Format[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec.TableSourceTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec.TableSourceTypeEnum]
	given fmtGoogleCloudDatacatalogV1ViewSpec: Format[Schema.GoogleCloudDatacatalogV1ViewSpec] = Json.format[Schema.GoogleCloudDatacatalogV1ViewSpec]
	given fmtGoogleCloudDatacatalogV1TableSpec: Format[Schema.GoogleCloudDatacatalogV1TableSpec] = Json.format[Schema.GoogleCloudDatacatalogV1TableSpec]
	given fmtGoogleCloudDatacatalogV1DatabaseTableSpecTypeEnum: Format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec.TypeEnum]
	given fmtGoogleCloudDatacatalogV1DataplexTableSpec: Format[Schema.GoogleCloudDatacatalogV1DataplexTableSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DataplexTableSpec]
	given fmtGoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec: Format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec]
	given fmtGoogleCloudDatacatalogV1DataplexExternalTable: Format[Schema.GoogleCloudDatacatalogV1DataplexExternalTable] = Json.format[Schema.GoogleCloudDatacatalogV1DataplexExternalTable]
	given fmtGoogleCloudDatacatalogV1DataplexSpec: Format[Schema.GoogleCloudDatacatalogV1DataplexSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DataplexSpec]
	given fmtGoogleCloudDatacatalogV1DataplexExternalTableSystemEnum: Format[Schema.GoogleCloudDatacatalogV1DataplexExternalTable.SystemEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1DataplexExternalTable.SystemEnum]
	given fmtGoogleCloudDatacatalogV1PhysicalSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaAvroSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaAvroSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaAvroSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaThriftSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaThriftSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaThriftSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaProtobufSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaProtobufSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaProtobufSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaParquetSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaParquetSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaParquetSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaOrcSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaOrcSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaOrcSchema]
	given fmtGoogleCloudDatacatalogV1PhysicalSchemaCsvSchema: Format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaCsvSchema] = Json.format[Schema.GoogleCloudDatacatalogV1PhysicalSchemaCsvSchema]
	given fmtGoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpecViewTypeEnum: Format[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec.ViewTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec.ViewTypeEnum]
	given fmtGoogleCloudDatacatalogV1BigQueryConnectionSpec: Format[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec] = Json.format[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec]
	given fmtGoogleCloudDatacatalogV1BigQueryConnectionSpecConnectionTypeEnum: Format[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec.ConnectionTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec.ConnectionTypeEnum]
	given fmtGoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec: Format[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec] = Json.format[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec]
	given fmtGoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpecTypeEnum: Format[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec.TypeEnum]
	given fmtGoogleCloudDatacatalogV1RoutineSpecRoutineTypeEnum: Format[Schema.GoogleCloudDatacatalogV1RoutineSpec.RoutineTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1RoutineSpec.RoutineTypeEnum]
	given fmtGoogleCloudDatacatalogV1RoutineSpecArgument: Format[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument] = Json.format[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument]
	given fmtGoogleCloudDatacatalogV1BigQueryRoutineSpec: Format[Schema.GoogleCloudDatacatalogV1BigQueryRoutineSpec] = Json.format[Schema.GoogleCloudDatacatalogV1BigQueryRoutineSpec]
	given fmtGoogleCloudDatacatalogV1RoutineSpecArgumentModeEnum: Format[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument.ModeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument.ModeEnum]
	given fmtGoogleCloudDatacatalogV1VertexDatasetSpec: Format[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec] = Json.format[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec]
	given fmtGoogleCloudDatacatalogV1VertexDatasetSpecDataTypeEnum: Format[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec.DataTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec.DataTypeEnum]
	given fmtGoogleCloudDatacatalogV1DataplexFilesetSpec: Format[Schema.GoogleCloudDatacatalogV1DataplexFilesetSpec] = Json.format[Schema.GoogleCloudDatacatalogV1DataplexFilesetSpec]
	given fmtGoogleCloudDatacatalogV1CloudBigtableInstanceSpec: Format[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpec] = Json.format[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpec]
	given fmtGoogleCloudDatacatalogV1CloudBigtableInstanceSpecCloudBigtableClusterSpec: Format[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpecCloudBigtableClusterSpec] = Json.format[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpecCloudBigtableClusterSpec]
	given fmtGoogleCloudDatacatalogV1VertexModelSpec: Format[Schema.GoogleCloudDatacatalogV1VertexModelSpec] = Json.format[Schema.GoogleCloudDatacatalogV1VertexModelSpec]
	given fmtGoogleCloudDatacatalogV1VertexModelSourceInfo: Format[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo] = Json.format[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo]
	given fmtGoogleCloudDatacatalogV1VertexModelSourceInfoSourceTypeEnum: Format[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo.SourceTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo.SourceTypeEnum]
	given fmtGoogleCloudDatacatalogV1FeatureOnlineStoreSpecStorageTypeEnum: Format[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec.StorageTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec.StorageTypeEnum]
	given fmtGoogleCloudDatacatalogV1EntryOverview: Format[Schema.GoogleCloudDatacatalogV1EntryOverview] = Json.format[Schema.GoogleCloudDatacatalogV1EntryOverview]
	given fmtGoogleCloudDatacatalogV1Contacts: Format[Schema.GoogleCloudDatacatalogV1Contacts] = Json.format[Schema.GoogleCloudDatacatalogV1Contacts]
	given fmtGoogleCloudDatacatalogV1ContactsPerson: Format[Schema.GoogleCloudDatacatalogV1ContactsPerson] = Json.format[Schema.GoogleCloudDatacatalogV1ContactsPerson]
	given fmtGoogleCloudDatacatalogV1ColumnSchema: Format[Schema.GoogleCloudDatacatalogV1ColumnSchema] = Json.format[Schema.GoogleCloudDatacatalogV1ColumnSchema]
	given fmtGoogleCloudDatacatalogV1ColumnSchemaHighestIndexingTypeEnum: Format[Schema.GoogleCloudDatacatalogV1ColumnSchema.HighestIndexingTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1ColumnSchema.HighestIndexingTypeEnum]
	given fmtGoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec: Format[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec] = Json.format[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec]
	given fmtGoogleCloudDatacatalogV1ColumnSchemaFieldElementType: Format[Schema.GoogleCloudDatacatalogV1ColumnSchemaFieldElementType] = Json.format[Schema.GoogleCloudDatacatalogV1ColumnSchemaFieldElementType]
	given fmtGoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpecTypeEnum: Format[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec.TypeEnum]
	given fmtGoogleCloudDatacatalogV1UsageStats: Format[Schema.GoogleCloudDatacatalogV1UsageStats] = Json.format[Schema.GoogleCloudDatacatalogV1UsageStats]
	given fmtGoogleCloudDatacatalogV1CommonUsageStats: Format[Schema.GoogleCloudDatacatalogV1CommonUsageStats] = Json.format[Schema.GoogleCloudDatacatalogV1CommonUsageStats]
	given fmtGoogleCloudDatacatalogV1DataSourceServiceEnum: Format[Schema.GoogleCloudDatacatalogV1DataSource.ServiceEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1DataSource.ServiceEnum]
	given fmtGoogleCloudDatacatalogV1StorageProperties: Format[Schema.GoogleCloudDatacatalogV1StorageProperties] = Json.format[Schema.GoogleCloudDatacatalogV1StorageProperties]
	given fmtGoogleCloudDatacatalogV1ListEntriesResponse: Format[Schema.GoogleCloudDatacatalogV1ListEntriesResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ListEntriesResponse]
	given fmtGoogleCloudDatacatalogV1ModifyEntryOverviewRequest: Format[Schema.GoogleCloudDatacatalogV1ModifyEntryOverviewRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ModifyEntryOverviewRequest]
	given fmtGoogleCloudDatacatalogV1ModifyEntryContactsRequest: Format[Schema.GoogleCloudDatacatalogV1ModifyEntryContactsRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ModifyEntryContactsRequest]
	given fmtGoogleCloudDatacatalogV1TagTemplate: Format[Schema.GoogleCloudDatacatalogV1TagTemplate] = Json.format[Schema.GoogleCloudDatacatalogV1TagTemplate]
	given fmtGoogleCloudDatacatalogV1TagTemplateField: Format[Schema.GoogleCloudDatacatalogV1TagTemplateField] = Json.format[Schema.GoogleCloudDatacatalogV1TagTemplateField]
	given fmtGoogleCloudDatacatalogV1TagTemplateDataplexTransferStatusEnum: Format[Schema.GoogleCloudDatacatalogV1TagTemplate.DataplexTransferStatusEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1TagTemplate.DataplexTransferStatusEnum]
	given fmtGoogleCloudDatacatalogV1FieldType: Format[Schema.GoogleCloudDatacatalogV1FieldType] = Json.format[Schema.GoogleCloudDatacatalogV1FieldType]
	given fmtGoogleCloudDatacatalogV1FieldTypePrimitiveTypeEnum: Format[Schema.GoogleCloudDatacatalogV1FieldType.PrimitiveTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1FieldType.PrimitiveTypeEnum]
	given fmtGoogleCloudDatacatalogV1FieldTypeEnumType: Format[Schema.GoogleCloudDatacatalogV1FieldTypeEnumType] = Json.format[Schema.GoogleCloudDatacatalogV1FieldTypeEnumType]
	given fmtGoogleCloudDatacatalogV1FieldTypeEnumTypeEnumValue: Format[Schema.GoogleCloudDatacatalogV1FieldTypeEnumTypeEnumValue] = Json.format[Schema.GoogleCloudDatacatalogV1FieldTypeEnumTypeEnumValue]
	given fmtGoogleCloudDatacatalogV1RenameTagTemplateFieldRequest: Format[Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldRequest] = Json.format[Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldRequest]
	given fmtGoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest: Format[Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest] = Json.format[Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest]
	given fmtGoogleCloudDatacatalogV1Tag: Format[Schema.GoogleCloudDatacatalogV1Tag] = Json.format[Schema.GoogleCloudDatacatalogV1Tag]
	given fmtGoogleCloudDatacatalogV1TagField: Format[Schema.GoogleCloudDatacatalogV1TagField] = Json.format[Schema.GoogleCloudDatacatalogV1TagField]
	given fmtGoogleCloudDatacatalogV1TagDataplexTransferStatusEnum: Format[Schema.GoogleCloudDatacatalogV1Tag.DataplexTransferStatusEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1Tag.DataplexTransferStatusEnum]
	given fmtGoogleCloudDatacatalogV1TagFieldEnumValue: Format[Schema.GoogleCloudDatacatalogV1TagFieldEnumValue] = Json.format[Schema.GoogleCloudDatacatalogV1TagFieldEnumValue]
	given fmtGoogleCloudDatacatalogV1ListTagsResponse: Format[Schema.GoogleCloudDatacatalogV1ListTagsResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ListTagsResponse]
	given fmtGoogleCloudDatacatalogV1ReconcileTagsRequest: Format[Schema.GoogleCloudDatacatalogV1ReconcileTagsRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ReconcileTagsRequest]
	given fmtGoogleCloudDatacatalogV1StarEntryRequest: Format[Schema.GoogleCloudDatacatalogV1StarEntryRequest] = Json.format[Schema.GoogleCloudDatacatalogV1StarEntryRequest]
	given fmtGoogleCloudDatacatalogV1StarEntryResponse: Format[Schema.GoogleCloudDatacatalogV1StarEntryResponse] = Json.format[Schema.GoogleCloudDatacatalogV1StarEntryResponse]
	given fmtGoogleCloudDatacatalogV1UnstarEntryRequest: Format[Schema.GoogleCloudDatacatalogV1UnstarEntryRequest] = Json.format[Schema.GoogleCloudDatacatalogV1UnstarEntryRequest]
	given fmtGoogleCloudDatacatalogV1UnstarEntryResponse: Format[Schema.GoogleCloudDatacatalogV1UnstarEntryResponse] = Json.format[Schema.GoogleCloudDatacatalogV1UnstarEntryResponse]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtGoogleCloudDatacatalogV1ImportEntriesRequest: Format[Schema.GoogleCloudDatacatalogV1ImportEntriesRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ImportEntriesRequest]
	given fmtGoogleCloudDatacatalogV1SetConfigRequest: Format[Schema.GoogleCloudDatacatalogV1SetConfigRequest] = Json.format[Schema.GoogleCloudDatacatalogV1SetConfigRequest]
	given fmtGoogleCloudDatacatalogV1SetConfigRequestTagTemplateMigrationEnum: Format[Schema.GoogleCloudDatacatalogV1SetConfigRequest.TagTemplateMigrationEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1SetConfigRequest.TagTemplateMigrationEnum]
	given fmtGoogleCloudDatacatalogV1SetConfigRequestCatalogUiExperienceEnum: Format[Schema.GoogleCloudDatacatalogV1SetConfigRequest.CatalogUiExperienceEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1SetConfigRequest.CatalogUiExperienceEnum]
	given fmtGoogleCloudDatacatalogV1MigrationConfig: Format[Schema.GoogleCloudDatacatalogV1MigrationConfig] = Json.format[Schema.GoogleCloudDatacatalogV1MigrationConfig]
	given fmtGoogleCloudDatacatalogV1MigrationConfigTagTemplateMigrationEnum: Format[Schema.GoogleCloudDatacatalogV1MigrationConfig.TagTemplateMigrationEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1MigrationConfig.TagTemplateMigrationEnum]
	given fmtGoogleCloudDatacatalogV1MigrationConfigCatalogUiExperienceEnum: Format[Schema.GoogleCloudDatacatalogV1MigrationConfig.CatalogUiExperienceEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1MigrationConfig.CatalogUiExperienceEnum]
	given fmtGoogleCloudDatacatalogV1OrganizationConfig: Format[Schema.GoogleCloudDatacatalogV1OrganizationConfig] = Json.format[Schema.GoogleCloudDatacatalogV1OrganizationConfig]
	given fmtGoogleCloudDatacatalogV1Taxonomy: Format[Schema.GoogleCloudDatacatalogV1Taxonomy] = Json.format[Schema.GoogleCloudDatacatalogV1Taxonomy]
	given fmtGoogleCloudDatacatalogV1TaxonomyActivatedPolicyTypesEnum: Format[Schema.GoogleCloudDatacatalogV1Taxonomy.ActivatedPolicyTypesEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1Taxonomy.ActivatedPolicyTypesEnum]
	given fmtGoogleCloudDatacatalogV1TaxonomyService: Format[Schema.GoogleCloudDatacatalogV1TaxonomyService] = Json.format[Schema.GoogleCloudDatacatalogV1TaxonomyService]
	given fmtGoogleCloudDatacatalogV1TaxonomyServiceNameEnum: Format[Schema.GoogleCloudDatacatalogV1TaxonomyService.NameEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1TaxonomyService.NameEnum]
	given fmtGoogleCloudDatacatalogV1ListTaxonomiesResponse: Format[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse]
	given fmtGoogleCloudDatacatalogV1PolicyTag: Format[Schema.GoogleCloudDatacatalogV1PolicyTag] = Json.format[Schema.GoogleCloudDatacatalogV1PolicyTag]
	given fmtGoogleCloudDatacatalogV1ListPolicyTagsResponse: Format[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse]
	given fmtGoogleCloudDatacatalogV1ReplaceTaxonomyRequest: Format[Schema.GoogleCloudDatacatalogV1ReplaceTaxonomyRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ReplaceTaxonomyRequest]
	given fmtGoogleCloudDatacatalogV1SerializedTaxonomy: Format[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy] = Json.format[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy]
	given fmtGoogleCloudDatacatalogV1SerializedPolicyTag: Format[Schema.GoogleCloudDatacatalogV1SerializedPolicyTag] = Json.format[Schema.GoogleCloudDatacatalogV1SerializedPolicyTag]
	given fmtGoogleCloudDatacatalogV1SerializedTaxonomyActivatedPolicyTypesEnum: Format[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy.ActivatedPolicyTypesEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy.ActivatedPolicyTypesEnum]
	given fmtGoogleCloudDatacatalogV1ImportTaxonomiesRequest: Format[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesRequest] = Json.format[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesRequest]
	given fmtGoogleCloudDatacatalogV1InlineSource: Format[Schema.GoogleCloudDatacatalogV1InlineSource] = Json.format[Schema.GoogleCloudDatacatalogV1InlineSource]
	given fmtGoogleCloudDatacatalogV1CrossRegionalSource: Format[Schema.GoogleCloudDatacatalogV1CrossRegionalSource] = Json.format[Schema.GoogleCloudDatacatalogV1CrossRegionalSource]
	given fmtGoogleCloudDatacatalogV1ImportTaxonomiesResponse: Format[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesResponse]
	given fmtGoogleCloudDatacatalogV1ExportTaxonomiesResponse: Format[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse]
	given fmtGoogleCloudDatacatalogV1ReconcileTagsMetadata: Format[Schema.GoogleCloudDatacatalogV1ReconcileTagsMetadata] = Json.format[Schema.GoogleCloudDatacatalogV1ReconcileTagsMetadata]
	given fmtGoogleCloudDatacatalogV1ReconcileTagsMetadataStateEnum: Format[Schema.GoogleCloudDatacatalogV1ReconcileTagsMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1ReconcileTagsMetadata.StateEnum]
	given fmtGoogleCloudDatacatalogV1ReconcileTagsResponse: Format[Schema.GoogleCloudDatacatalogV1ReconcileTagsResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ReconcileTagsResponse]
	given fmtGoogleCloudDatacatalogV1ImportEntriesMetadata: Format[Schema.GoogleCloudDatacatalogV1ImportEntriesMetadata] = Json.format[Schema.GoogleCloudDatacatalogV1ImportEntriesMetadata]
	given fmtGoogleCloudDatacatalogV1ImportEntriesMetadataStateEnum: Format[Schema.GoogleCloudDatacatalogV1ImportEntriesMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogV1ImportEntriesMetadata.StateEnum]
	given fmtGoogleCloudDatacatalogV1ImportEntriesResponse: Format[Schema.GoogleCloudDatacatalogV1ImportEntriesResponse] = Json.format[Schema.GoogleCloudDatacatalogV1ImportEntriesResponse]
	given fmtGoogleCloudDatacatalogV1DumpItem: Format[Schema.GoogleCloudDatacatalogV1DumpItem] = Json.format[Schema.GoogleCloudDatacatalogV1DumpItem]
	given fmtGoogleCloudDatacatalogV1TaggedEntry: Format[Schema.GoogleCloudDatacatalogV1TaggedEntry] = Json.format[Schema.GoogleCloudDatacatalogV1TaggedEntry]
}