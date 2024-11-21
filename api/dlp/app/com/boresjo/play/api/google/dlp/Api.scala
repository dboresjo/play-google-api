package com.boresjo.play.api.google.dlp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dlp.googleapis.com/"

	object locations {
		object infoTypes {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInfoTypesResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String, languageCode: String, filter: String, locationId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/locations/${locationsId}/infoTypes")).addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "filter" -> filter.toString, "locationId" -> locationId.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		object locations {
			object fileStoreDataProfiles {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2FileStoreDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]) {
					/** Optional. Allows filtering. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` or `OR` logical operators. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; Supported fields/values: - `project_id` - The Google Cloud project ID. - `account_id` - The AWS account ID. - `file_store_path` - The path like "gs://bucket". - `data_source_type` - The profile's data source type, like "google/storage/bucket". - `data_storage_location` - The location where the file store's data is stored, like "us-central1". - `sensitivity_level` - HIGH|MODERATE|LOW - `data_risk_level` - HIGH|MODERATE|LOW - `resource_visibility`: PUBLIC|RESTRICTED - `status_code` - an RPC status code as defined in https://github.com/googleapis/googleapis/blob/master/google/rpc/code.proto &#42; The operator must be `=` or `!=`. Examples: &#42; `project_id = 12345 AND status_code = 1` &#42; `project_id = 12345 AND sensitivity_level = HIGH` &#42; `project_id = 12345 AND resource_visibility = PUBLIC` &#42; `file_store_path = "gs://mybucket"` The length of this field should be no more than 500 characters. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token to continue retrieval. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Comma-separated list of fields to order by, followed by `asc` or `desc` postfix. This list is case insensitive. The default sorting order is ascending. Redundant space characters are insignificant. Only one order field at a time is allowed. Examples: &#42; `project_id asc` &#42; `name` &#42; `sensitivity_level desc` Supported fields are: - `project_id`: The Google Cloud project ID. - `sensitivity_level`: How sensitive the data in a table is, at most. - `data_risk_level`: How much risk is associated with this data. - `profile_last_generated`: When the profile was last updated in epoch seconds. - `last_modified`: The last time the resource was modified. - `resource_visibility`: Visibility restriction for this resource. - `name`: The name of the profile. - `create_time`: The time the file store was first created. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Size of the page. This value can be limited by the server. If zero, server returns a page of max size 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deidentifyTemplates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, orderBy: String, locationId: String, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates")).addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tableDataProfiles {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, filter: String, orderBy: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2TableDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2TableDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2TableDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object jobTriggers {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, orderBy: String, `type`: String, locationId: String, parent: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers")).addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "type" -> `type`.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
				}
			}
			object columnDataProfiles {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ColumnDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, columnDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/columnDataProfiles/${columnDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, pageSize: Int, orderBy: String, filter: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/columnDataProfiles")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object inspectTemplates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, orderBy: String, pageToken: String, locationId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object dlpJobs {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, locationId: String, pageToken: String, pageSize: Int, `type`: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/dlpJobs")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "type" -> `type`.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
				}
			}
			object discoveryConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2CreateDiscoveryConfigRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2UpdateDiscoveryConfigRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, orderBy: String, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs")).addQueryStringParameters("orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object connections {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateConnectionRequest(body: Schema.GooglePrivacyDlpV2CreateConnectionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2Connection]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2Connection]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateConnectionRequest(body: Schema.GooglePrivacyDlpV2UpdateConnectionRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]) {
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Supported field/value: `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListConnectionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]) {
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Supported field/value: - `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2SearchConnectionsResponse])
				}
				object search {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections:search")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]] = (fun: search) => fun.apply()
				}
			}
			object projectDataProfiles {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ProjectDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, projectDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/projectDataProfiles/${projectDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, filter: String, orderBy: String, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/projectDataProfiles")).addQueryStringParameters("filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object storedInfoTypes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, locationId: String, parent: String, pageSize: Int, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes")).addQueryStringParameters("pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object storedInfoTypes {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object get {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object patch {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int, locationId: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
		object deidentifyTemplates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object get {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object patch {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, orderBy: String, locationId: String, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates")).addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object inspectTemplates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object get {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object patch {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, locationId: String, orderBy: String, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates")).addQueryStringParameters("locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object infoTypes {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInfoTypesResponse])
		}
		object list {
			def apply(languageCode: String, filter: String, parent: String, locationId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/infoTypes")).addQueryStringParameters("languageCode" -> languageCode.toString, "filter" -> filter.toString, "parent" -> parent.toString, "locationId" -> locationId.toString))
			given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object locations {
			object fileStoreDataProfiles {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2FileStoreDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]) {
					/** Optional. Comma-separated list of fields to order by, followed by `asc` or `desc` postfix. This list is case insensitive. The default sorting order is ascending. Redundant space characters are insignificant. Only one order field at a time is allowed. Examples: &#42; `project_id asc` &#42; `name` &#42; `sensitivity_level desc` Supported fields are: - `project_id`: The Google Cloud project ID. - `sensitivity_level`: How sensitive the data in a table is, at most. - `data_risk_level`: How much risk is associated with this data. - `profile_last_generated`: When the profile was last updated in epoch seconds. - `last_modified`: The last time the resource was modified. - `resource_visibility`: Visibility restriction for this resource. - `name`: The name of the profile. - `create_time`: The time the file store was first created. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Page token to continue retrieval. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Allows filtering. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` or `OR` logical operators. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; Supported fields/values: - `project_id` - The Google Cloud project ID. - `account_id` - The AWS account ID. - `file_store_path` - The path like "gs://bucket". - `data_source_type` - The profile's data source type, like "google/storage/bucket". - `data_storage_location` - The location where the file store's data is stored, like "us-central1". - `sensitivity_level` - HIGH|MODERATE|LOW - `data_risk_level` - HIGH|MODERATE|LOW - `resource_visibility`: PUBLIC|RESTRICTED - `status_code` - an RPC status code as defined in https://github.com/googleapis/googleapis/blob/master/google/rpc/code.proto &#42; The operator must be `=` or `!=`. Examples: &#42; `project_id = 12345 AND status_code = 1` &#42; `project_id = 12345 AND sensitivity_level = HIGH` &#42; `project_id = 12345 AND resource_visibility = PUBLIC` &#42; `file_store_path = "gs://mybucket"` The length of this field should be no more than 500 characters. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Size of the page. This value can be limited by the server. If zero, server returns a page of max size 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tableDataProfiles {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles")).addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2TableDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2TableDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2TableDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object jobTriggers {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers")).addQueryStringParameters("parent" -> parent.toString))
				}
				class hybridInspect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2HybridInspectJobTriggerRequest(body: Schema.GooglePrivacyDlpV2HybridInspectJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2HybridInspectResponse])
				}
				object hybridInspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): hybridInspect = new hybridInspect(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}:hybridInspect")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
				}
				class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2ActivateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2ActivateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object activate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}:activate")).addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, orderBy: String, pageToken: String, filter: String, `type`: String, locationId: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers")).addQueryStringParameters("orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "type" -> `type`.toString, "locationId" -> locationId.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
				}
			}
			object content {
				class deidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2DeidentifyContentRequest(body: Schema.GooglePrivacyDlpV2DeidentifyContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyContentResponse])
				}
				object deidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): deidentify = new deidentify(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:deidentify")).addQueryStringParameters("parent" -> parent.toString))
				}
				class reidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2ReidentifyContentRequest(body: Schema.GooglePrivacyDlpV2ReidentifyContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2ReidentifyContentResponse])
				}
				object reidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): reidentify = new reidentify(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:reidentify")).addQueryStringParameters("parent" -> parent.toString))
				}
				class inspect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2InspectContentRequest(body: Schema.GooglePrivacyDlpV2InspectContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectContentResponse])
				}
				object inspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): inspect = new inspect(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:inspect")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object columnDataProfiles {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ColumnDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, columnDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/columnDataProfiles/${columnDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/columnDataProfiles")).addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object inspectTemplates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, locationId: String, pageToken: String, parent: String, pageSize: Int, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates")).addQueryStringParameters("locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object dlpJobs {
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CancelDlpJobRequest(body: Schema.GooglePrivacyDlpV2CancelDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateDlpJobRequest(body: Schema.GooglePrivacyDlpV2CreateDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs")).addQueryStringParameters("parent" -> parent.toString))
				}
				class finish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2FinishDlpJobRequest(body: Schema.GooglePrivacyDlpV2FinishDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object finish {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): finish = new finish(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:finish")).addQueryStringParameters("name" -> name.toString))
				}
				class hybridInspect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2HybridInspectDlpJobRequest(body: Schema.GooglePrivacyDlpV2HybridInspectDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2HybridInspectResponse])
				}
				object hybridInspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): hybridInspect = new hybridInspect(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:hybridInspect")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DlpJob]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DlpJob]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, locationId: String, pageToken: String, orderBy: String, parent: String, `type`: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs")).addQueryStringParameters("pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "type" -> `type`.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
				}
			}
			object discoveryConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2CreateDiscoveryConfigRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2UpdateDiscoveryConfigRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, orderBy: String, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs")).addQueryStringParameters("orderBy" -> orderBy.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object connections {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateConnectionRequest(body: Schema.GooglePrivacyDlpV2CreateConnectionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2Connection]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2Connection]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateConnectionRequest(body: Schema.GooglePrivacyDlpV2UpdateConnectionRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]) {
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Supported field/value: `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]) {
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Supported field/value: - `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2SearchConnectionsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections:search")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]] = (fun: search) => fun.apply()
				}
			}
			object image {
				class redact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2RedactImageRequest(body: Schema.GooglePrivacyDlpV2RedactImageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2RedactImageResponse])
				}
				object redact {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): redact = new redact(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/image:redact")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object deidentifyTemplates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, locationId: String, orderBy: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object projectDataProfiles {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/projectDataProfiles")).addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ProjectDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, projectDataProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/projectDataProfiles/${projectDataProfilesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object storedInfoTypes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, orderBy: String, parent: String, locationId: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes")).addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "locationId" -> locationId.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object storedInfoTypes {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object get {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object patch {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, locationId: String, parent: String, orderBy: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes")).addQueryStringParameters("locationId" -> locationId.toString, "parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
		object jobTriggers {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object get {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
			}
			class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2ActivateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2ActivateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object activate {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}:activate")).addQueryStringParameters("name" -> name.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object patch {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, `type`: String, orderBy: String, pageToken: String, locationId: String, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers")).addQueryStringParameters("pageSize" -> pageSize.toString, "type" -> `type`.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
			}
		}
		object content {
			class reidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2ReidentifyContentRequest(body: Schema.GooglePrivacyDlpV2ReidentifyContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2ReidentifyContentResponse])
			}
			object reidentify {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): reidentify = new reidentify(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:reidentify")).addQueryStringParameters("parent" -> parent.toString))
			}
			class inspect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2InspectContentRequest(body: Schema.GooglePrivacyDlpV2InspectContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectContentResponse])
			}
			object inspect {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): inspect = new inspect(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:inspect")).addQueryStringParameters("parent" -> parent.toString))
			}
			class deidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2DeidentifyContentRequest(body: Schema.GooglePrivacyDlpV2DeidentifyContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyContentResponse])
			}
			object deidentify {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): deidentify = new deidentify(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:deidentify")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object inspectTemplates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object get {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object patch {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, orderBy: String, locationId: String, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates")).addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object dlpJobs {
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CancelDlpJobRequest(body: Schema.GooglePrivacyDlpV2CancelDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateDlpJobRequest(body: Schema.GooglePrivacyDlpV2CreateDlpJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DlpJob]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object get {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DlpJob]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, `type`: String, orderBy: String, pageSize: Int, locationId: String, filter: String, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs")).addQueryStringParameters("type" -> `type`.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
			}
		}
		object image {
			class redact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2RedactImageRequest(body: Schema.GooglePrivacyDlpV2RedactImageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2RedactImageResponse])
			}
			object redact {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): redact = new redact(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/image:redact")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object deidentifyTemplates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object get {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object patch {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, locationId: String, orderBy: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates")).addQueryStringParameters("pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
