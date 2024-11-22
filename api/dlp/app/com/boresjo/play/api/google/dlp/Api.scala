package com.boresjo.play.api.google.dlp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://dlp.googleapis.com/"

	object locations {
		object infoTypes {
			/** Returns a list of the sensitive information types that the DLP API supports. See https://cloud.google.com/sensitive-data-protection/docs/infotypes-reference to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInfoTypesResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String, languageCode: String, filter: String, locationId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/locations/${locationsId}/infoTypes").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "filter" -> filter.toString, "locationId" -> locationId.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		object locations {
			object fileStoreDataProfiles {
				/** Delete a FileStoreDataProfile. Will not prevent the profile from being regenerated if the resource is still included in a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a file store data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2FileStoreDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]] = (fun: get) => fun.apply()
				}
				/** Lists file store data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Allows filtering. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` or `OR` logical operators. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; Supported fields/values: - `project_id` - The Google Cloud project ID. - `account_id` - The AWS account ID. - `file_store_path` - The path like "gs://bucket". - `data_source_type` - The profile's data source type, like "google/storage/bucket". - `data_storage_location` - The location where the file store's data is stored, like "us-central1". - `sensitivity_level` - HIGH|MODERATE|LOW - `data_risk_level` - HIGH|MODERATE|LOW - `resource_visibility`: PUBLIC|RESTRICTED - `status_code` - an RPC status code as defined in https://github.com/googleapis/googleapis/blob/master/google/rpc/code.proto &#42; The operator must be `=` or `!=`. Examples: &#42; `project_id = 12345 AND status_code = 1` &#42; `project_id = 12345 AND sensitivity_level = HIGH` &#42; `project_id = 12345 AND resource_visibility = PUBLIC` &#42; `file_store_path = "gs://mybucket"` The length of this field should be no more than 500 characters. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token to continue retrieval. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Comma-separated list of fields to order by, followed by `asc` or `desc` postfix. This list is case insensitive. The default sorting order is ascending. Redundant space characters are insignificant. Only one order field at a time is allowed. Examples: &#42; `project_id asc` &#42; `name` &#42; `sensitivity_level desc` Supported fields are: - `project_id`: The Google Cloud project ID. - `sensitivity_level`: How sensitive the data in a table is, at most. - `data_risk_level`: How much risk is associated with this data. - `profile_last_generated`: When the profile was last updated in epoch seconds. - `last_modified`: The last time the resource was modified. - `resource_visibility`: Visibility restriction for this resource. - `name`: The name of the profile. - `create_time`: The time the file store was first created. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Size of the page. This value can be limited by the server. If zero, server returns a page of max size 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/fileStoreDataProfiles").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deidentifyTemplates {
				/** Creates a DeidentifyTemplate for reusing frequently used configuration for de-identifying content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates the DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists DeidentifyTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, orderBy: String, locationId: String, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/deidentifyTemplates").addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tableDataProfiles {
				/** Lists table data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, filter: String, orderBy: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				/** Delete a TableDataProfile. Will not prevent the profile from being regenerated if the table is still included in a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a table data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2TableDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2TableDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2TableDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object jobTriggers {
				/** Creates a job trigger to run DLP actions such as scanning storage for sensitive information on a set schedule. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
				}
				/** Updates a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists job triggers. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, orderBy: String, `type`: String, locationId: String, parent: String, filter: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/jobTriggers").addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "type" -> `type`.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
				}
			}
			object columnDataProfiles {
				/** Gets a column data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ColumnDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, columnDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/columnDataProfiles/${columnDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]] = (fun: get) => fun.apply()
				}
				/** Lists column data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, pageSize: Int, orderBy: String, filter: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/columnDataProfiles").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object inspectTemplates {
				/** Creates an InspectTemplate for reusing frequently used configuration for inspecting content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates the InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists InspectTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, orderBy: String, pageToken: String, locationId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/inspectTemplates").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object dlpJobs {
				/** Lists DlpJobs that match the specified filter in the request. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, locationId: String, pageToken: String, pageSize: Int, `type`: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/dlpJobs").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "type" -> `type`.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
				}
			}
			object discoveryConfigs {
				/** Creates a config for discovery to scan and profile storage. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2CreateDiscoveryConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a discovery configuration. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a discovery configuration. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2UpdateDiscoveryConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists discovery configurations. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, orderBy: String, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/discoveryConfigs").addQueryStringParameters("orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object connections {
				/** Create a Connection to an external data source. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateConnectionRequest(body: Schema.GooglePrivacyDlpV2CreateConnectionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Delete a Connection. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Get a Connection by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2Connection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2Connection]] = (fun: get) => fun.apply()
				}
				/** Update a Connection. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateConnectionRequest(body: Schema.GooglePrivacyDlpV2UpdateConnectionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists Connections in a parent. Use SearchConnections to see all connections within an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Supported field/value: `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListConnectionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				/** Searches for Connections in a parent. */
				class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Supported field/value: - `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2SearchConnectionsResponse])
				}
				object search {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/connections:search").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]] = (fun: search) => fun.apply()
				}
			}
			object projectDataProfiles {
				/** Gets a project data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ProjectDataProfile])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, projectDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/projectDataProfiles/${projectDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]] = (fun: get) => fun.apply()
				}
				/** Lists project data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, filter: String, orderBy: String, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/projectDataProfiles").addQueryStringParameters("filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object storedInfoTypes {
				/** Creates a pre-built stored infoType to be used for inspection. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
				}
				/** Updates the stored infoType by creating a new version. The existing version will continue to be used until the new version is ready. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists stored infoTypes. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageToken: String, locationId: String, parent: String, pageSize: Int, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/storedInfoTypes").addQueryStringParameters("pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object storedInfoTypes {
			/** Creates a pre-built stored infoType to be used for inspection. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object get {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
			}
			/** Updates the stored infoType by creating a new version. The existing version will continue to be used until the new version is ready. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object patch {
				def apply(organizationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists stored infoTypes. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int, locationId: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/storedInfoTypes").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
		object deidentifyTemplates {
			/** Creates a DeidentifyTemplate for reusing frequently used configuration for de-identifying content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object get {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
			}
			/** Updates the DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object patch {
				def apply(organizationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists DeidentifyTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, orderBy: String, locationId: String, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/deidentifyTemplates").addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object inspectTemplates {
			/** Creates an InspectTemplate for reusing frequently used configuration for inspecting content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object get {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
			}
			/** Updates the InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object patch {
				def apply(organizationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists InspectTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, locationId: String, orderBy: String, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/inspectTemplates").addQueryStringParameters("locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object infoTypes {
		/** Returns a list of the sensitive information types that the DLP API supports. See https://cloud.google.com/sensitive-data-protection/docs/infotypes-reference to learn more. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInfoTypesResponse])
		}
		object list {
			def apply(languageCode: String, filter: String, parent: String, locationId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/infoTypes").addQueryStringParameters("languageCode" -> languageCode.toString, "filter" -> filter.toString, "parent" -> parent.toString, "locationId" -> locationId.toString))
			given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInfoTypesResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object locations {
			object fileStoreDataProfiles {
				/** Delete a FileStoreDataProfile. Will not prevent the profile from being regenerated if the resource is still included in a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a file store data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2FileStoreDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, fileStoreDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles/${fileStoreDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2FileStoreDataProfile]] = (fun: get) => fun.apply()
				}
				/** Lists file store data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Comma-separated list of fields to order by, followed by `asc` or `desc` postfix. This list is case insensitive. The default sorting order is ascending. Redundant space characters are insignificant. Only one order field at a time is allowed. Examples: &#42; `project_id asc` &#42; `name` &#42; `sensitivity_level desc` Supported fields are: - `project_id`: The Google Cloud project ID. - `sensitivity_level`: How sensitive the data in a table is, at most. - `data_risk_level`: How much risk is associated with this data. - `profile_last_generated`: When the profile was last updated in epoch seconds. - `last_modified`: The last time the resource was modified. - `resource_visibility`: Visibility restriction for this resource. - `name`: The name of the profile. - `create_time`: The time the file store was first created. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Page token to continue retrieval. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Allows filtering. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` or `OR` logical operators. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; Supported fields/values: - `project_id` - The Google Cloud project ID. - `account_id` - The AWS account ID. - `file_store_path` - The path like "gs://bucket". - `data_source_type` - The profile's data source type, like "google/storage/bucket". - `data_storage_location` - The location where the file store's data is stored, like "us-central1". - `sensitivity_level` - HIGH|MODERATE|LOW - `data_risk_level` - HIGH|MODERATE|LOW - `resource_visibility`: PUBLIC|RESTRICTED - `status_code` - an RPC status code as defined in https://github.com/googleapis/googleapis/blob/master/google/rpc/code.proto &#42; The operator must be `=` or `!=`. Examples: &#42; `project_id = 12345 AND status_code = 1` &#42; `project_id = 12345 AND sensitivity_level = HIGH` &#42; `project_id = 12345 AND resource_visibility = PUBLIC` &#42; `file_store_path = "gs://mybucket"` The length of this field should be no more than 500 characters. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Size of the page. This value can be limited by the server. If zero, server returns a page of max size 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/fileStoreDataProfiles").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListFileStoreDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tableDataProfiles {
				/** Lists table data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles").addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListTableDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				/** Delete a TableDataProfile. Will not prevent the profile from being regenerated if the table is still included in a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a table data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2TableDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2TableDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tableDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/tableDataProfiles/${tableDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2TableDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object jobTriggers {
				/** Creates a job trigger to run DLP actions such as scanning storage for sensitive information on a set schedule. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Inspect hybrid content and store findings to a trigger. The inspection will be processed asynchronously. To review the findings monitor the jobs within the trigger. */
				class hybridInspect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2HybridInspectJobTriggerRequest(body: Schema.GooglePrivacyDlpV2HybridInspectJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2HybridInspectResponse])
				}
				object hybridInspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): hybridInspect = new hybridInspect(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}:hybridInspect").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
				}
				/** Activate a job trigger. Causes the immediate execute of a trigger instead of waiting on the trigger event to occur. */
				class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2ActivateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2ActivateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object activate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists job triggers. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, orderBy: String, pageToken: String, filter: String, `type`: String, locationId: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobTriggers").addQueryStringParameters("orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "type" -> `type`.toString, "locationId" -> locationId.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
				}
			}
			object content {
				/** De-identifies potentially sensitive info from a ContentItem. This method has limits on input size and output size. See https://cloud.google.com/sensitive-data-protection/docs/deidentify-sensitive-data to learn more. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
				class deidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2DeidentifyContentRequest(body: Schema.GooglePrivacyDlpV2DeidentifyContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyContentResponse])
				}
				object deidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): deidentify = new deidentify(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:deidentify").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Re-identifies content that has been de-identified. See https://cloud.google.com/sensitive-data-protection/docs/pseudonymization#re-identification_in_free_text_code_example to learn more. */
				class reidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2ReidentifyContentRequest(body: Schema.GooglePrivacyDlpV2ReidentifyContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2ReidentifyContentResponse])
				}
				object reidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): reidentify = new reidentify(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:reidentify").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Finds potentially sensitive info in content. This method has limits on input size, processing time, and output size. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. For how to guides, see https://cloud.google.com/sensitive-data-protection/docs/inspecting-images and https://cloud.google.com/sensitive-data-protection/docs/inspecting-text, */
				class inspect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2InspectContentRequest(body: Schema.GooglePrivacyDlpV2InspectContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectContentResponse])
				}
				object inspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): inspect = new inspect(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/content:inspect").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object columnDataProfiles {
				/** Gets a column data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ColumnDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, columnDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/columnDataProfiles/${columnDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ColumnDataProfile]] = (fun: get) => fun.apply()
				}
				/** Lists column data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/columnDataProfiles").addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListColumnDataProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object inspectTemplates {
				/** Creates an InspectTemplate for reusing frequently used configuration for inspecting content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates the InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists InspectTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, locationId: String, pageToken: String, parent: String, pageSize: Int, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/inspectTemplates").addQueryStringParameters("locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object dlpJobs {
				/** Starts asynchronous cancellation on a long-running DlpJob. The server makes a best effort to cancel the DlpJob, but success is not guaranteed. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CancelDlpJobRequest(body: Schema.GooglePrivacyDlpV2CancelDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new job to inspect storage or calculate risk metrics. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. When no InfoTypes or CustomInfoTypes are specified in inspect jobs, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateDlpJobRequest(body: Schema.GooglePrivacyDlpV2CreateDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Finish a running hybrid DlpJob. Triggers the finalization steps and running of any enabled actions that have not yet run. */
				class finish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2FinishDlpJobRequest(body: Schema.GooglePrivacyDlpV2FinishDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object finish {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): finish = new finish(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:finish").addQueryStringParameters("name" -> name.toString))
				}
				/** Inspect hybrid content and store findings to a job. To review the findings, inspect the job. Inspection will occur asynchronously. */
				class hybridInspect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2HybridInspectDlpJobRequest(body: Schema.GooglePrivacyDlpV2HybridInspectDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2HybridInspectResponse])
				}
				object hybridInspect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): hybridInspect = new hybridInspect(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}:hybridInspect").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a long-running DlpJob. This method indicates that the client is no longer interested in the DlpJob result. The job will be canceled if possible. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets the latest state of a long-running DlpJob. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DlpJob]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs/${dlpJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DlpJob]] = (fun: get) => fun.apply()
				}
				/** Lists DlpJobs that match the specified filter in the request. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, locationId: String, pageToken: String, orderBy: String, parent: String, `type`: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/dlpJobs").addQueryStringParameters("pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "type" -> `type`.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
				}
			}
			object discoveryConfigs {
				/** Creates a config for discovery to scan and profile storage. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2CreateDiscoveryConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a discovery configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a discovery configuration. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DiscoveryConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a discovery configuration. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateDiscoveryConfigRequest(body: Schema.GooglePrivacyDlpV2UpdateDiscoveryConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DiscoveryConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveryConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs/${discoveryConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists discovery configurations. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, orderBy: String, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/discoveryConfigs").addQueryStringParameters("orderBy" -> orderBy.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDiscoveryConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object connections {
				/** Create a Connection to an external data source. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateConnectionRequest(body: Schema.GooglePrivacyDlpV2CreateConnectionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Delete a Connection. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Get a Connection by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2Connection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2Connection]] = (fun: get) => fun.apply()
				}
				/** Update a Connection. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateConnectionRequest(body: Schema.GooglePrivacyDlpV2UpdateConnectionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2Connection])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists Connections in a parent. Use SearchConnections to see all connections within an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Supported field/value: `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				/** Searches for Connections in a parent. */
				class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Page token from a previous page to return the next set of results. If set, all other request fields must match the original request. */
					def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Number of results per page, max 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Supported field/value: - `state` - MISSING|AVAILABLE|ERROR */
					def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2SearchConnectionsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections:search").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GooglePrivacyDlpV2SearchConnectionsResponse]] = (fun: search) => fun.apply()
				}
			}
			object image {
				/** Redacts potentially sensitive info from an image. This method has limits on input size, processing time, and output size. See https://cloud.google.com/sensitive-data-protection/docs/redacting-sensitive-data-images to learn more. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
				class redact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2RedactImageRequest(body: Schema.GooglePrivacyDlpV2RedactImageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2RedactImageResponse])
				}
				object redact {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): redact = new redact(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/image:redact").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object deidentifyTemplates {
				/** Creates a DeidentifyTemplate for reusing frequently used configuration for de-identifying content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates the DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists DeidentifyTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, locationId: String, orderBy: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/deidentifyTemplates").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object projectDataProfiles {
				/** Lists project data profiles for an organization. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, orderBy: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/projectDataProfiles").addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListProjectDataProfilesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a project data profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ProjectDataProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, projectDataProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/projectDataProfiles/${projectDataProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2ProjectDataProfile]] = (fun: get) => fun.apply()
				}
			}
			object storedInfoTypes {
				/** Creates a pre-built stored infoType to be used for inspection. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
				}
				/** Updates the stored infoType by creating a new version. The existing version will continue to be used until the new version is ready. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists stored infoTypes. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, orderBy: String, parent: String, locationId: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/storedInfoTypes").addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "locationId" -> locationId.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object storedInfoTypes {
			/** Creates a pre-built stored infoType to be used for inspection. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2CreateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a stored infoType. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2StoredInfoType]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object get {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2StoredInfoType]] = (fun: get) => fun.apply()
			}
			/** Updates the stored infoType by creating a new version. The existing version will continue to be used until the new version is ready. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateStoredInfoTypeRequest(body: Schema.GooglePrivacyDlpV2UpdateStoredInfoTypeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2StoredInfoType])
			}
			object patch {
				def apply(projectsId :PlayApi, storedInfoTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes/${storedInfoTypesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists stored infoTypes. See https://cloud.google.com/sensitive-data-protection/docs/creating-stored-infotypes to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, locationId: String, parent: String, orderBy: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/storedInfoTypes").addQueryStringParameters("locationId" -> locationId.toString, "parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListStoredInfoTypesResponse]] = (fun: list) => fun.apply()
			}
		}
		object jobTriggers {
			/** Creates a job trigger to run DLP actions such as scanning storage for sensitive information on a set schedule. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2CreateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2JobTrigger]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object get {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2JobTrigger]] = (fun: get) => fun.apply()
			}
			/** Activate a job trigger. Causes the immediate execute of a trigger instead of waiting on the trigger event to occur. */
			class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2ActivateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2ActivateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object activate {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}:activate").addQueryStringParameters("name" -> name.toString))
			}
			/** Updates a job trigger. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateJobTriggerRequest(body: Schema.GooglePrivacyDlpV2UpdateJobTriggerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2JobTrigger])
			}
			object patch {
				def apply(projectsId :PlayApi, jobTriggersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers/${jobTriggersId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists job triggers. See https://cloud.google.com/sensitive-data-protection/docs/creating-job-triggers to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListJobTriggersResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, `type`: String, orderBy: String, pageToken: String, locationId: String, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/jobTriggers").addQueryStringParameters("pageSize" -> pageSize.toString, "type" -> `type`.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListJobTriggersResponse]] = (fun: list) => fun.apply()
			}
		}
		object content {
			/** Re-identifies content that has been de-identified. See https://cloud.google.com/sensitive-data-protection/docs/pseudonymization#re-identification_in_free_text_code_example to learn more. */
			class reidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2ReidentifyContentRequest(body: Schema.GooglePrivacyDlpV2ReidentifyContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2ReidentifyContentResponse])
			}
			object reidentify {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): reidentify = new reidentify(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:reidentify").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Finds potentially sensitive info in content. This method has limits on input size, processing time, and output size. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. For how to guides, see https://cloud.google.com/sensitive-data-protection/docs/inspecting-images and https://cloud.google.com/sensitive-data-protection/docs/inspecting-text, */
			class inspect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2InspectContentRequest(body: Schema.GooglePrivacyDlpV2InspectContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectContentResponse])
			}
			object inspect {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): inspect = new inspect(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:inspect").addQueryStringParameters("parent" -> parent.toString))
			}
			/** De-identifies potentially sensitive info from a ContentItem. This method has limits on input size and output size. See https://cloud.google.com/sensitive-data-protection/docs/deidentify-sensitive-data to learn more. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
			class deidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2DeidentifyContentRequest(body: Schema.GooglePrivacyDlpV2DeidentifyContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyContentResponse])
			}
			object deidentify {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): deidentify = new deidentify(ws.url(BASE_URL + s"v2/projects/${projectsId}/content:deidentify").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object inspectTemplates {
			/** Creates an InspectTemplate for reusing frequently used configuration for inspecting content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets an InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2InspectTemplate]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object get {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2InspectTemplate]] = (fun: get) => fun.apply()
			}
			/** Updates the InspectTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateInspectTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateInspectTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2InspectTemplate])
			}
			object patch {
				def apply(projectsId :PlayApi, inspectTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates/${inspectTemplatesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists InspectTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, orderBy: String, locationId: String, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/inspectTemplates").addQueryStringParameters("orderBy" -> orderBy.toString, "locationId" -> locationId.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListInspectTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object dlpJobs {
			/** Starts asynchronous cancellation on a long-running DlpJob. The server makes a best effort to cancel the DlpJob, but success is not guaranteed. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CancelDlpJobRequest(body: Schema.GooglePrivacyDlpV2CancelDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new job to inspect storage or calculate risk metrics. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. When no InfoTypes or CustomInfoTypes are specified in inspect jobs, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateDlpJobRequest(body: Schema.GooglePrivacyDlpV2CreateDlpJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a long-running DlpJob. This method indicates that the client is no longer interested in the DlpJob result. The job will be canceled if possible. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets the latest state of a long-running DlpJob. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DlpJob]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DlpJob])
			}
			object get {
				def apply(projectsId :PlayApi, dlpJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs/${dlpJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DlpJob]] = (fun: get) => fun.apply()
			}
			/** Lists DlpJobs that match the specified filter in the request. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-storage and https://cloud.google.com/sensitive-data-protection/docs/compute-risk-analysis to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDlpJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, `type`: String, orderBy: String, pageSize: Int, locationId: String, filter: String, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/dlpJobs").addQueryStringParameters("type" -> `type`.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "locationId" -> locationId.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDlpJobsResponse]] = (fun: list) => fun.apply()
			}
		}
		object image {
			/** Redacts potentially sensitive info from an image. This method has limits on input size, processing time, and output size. See https://cloud.google.com/sensitive-data-protection/docs/redacting-sensitive-data-images to learn more. When no InfoTypes or CustomInfoTypes are specified in this request, the system will automatically choose what detectors to run. By default this may be all types, but may change over time as detectors are updated. */
			class redact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2RedactImageRequest(body: Schema.GooglePrivacyDlpV2RedactImageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2RedactImageResponse])
			}
			object redact {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): redact = new redact(ws.url(BASE_URL + s"v2/projects/${projectsId}/image:redact").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object deidentifyTemplates {
			/** Creates a DeidentifyTemplate for reusing frequently used configuration for de-identifying content, images, and storage. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2CreateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2CreateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object get {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = (fun: get) => fun.apply()
			}
			/** Updates the DeidentifyTemplate. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(body: Schema.GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GooglePrivacyDlpV2DeidentifyTemplate])
			}
			object patch {
				def apply(projectsId :PlayApi, deidentifyTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates/${deidentifyTemplatesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists DeidentifyTemplates. See https://cloud.google.com/sensitive-data-protection/docs/creating-templates-deid to learn more. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, locationId: String, orderBy: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/deidentifyTemplates").addQueryStringParameters("pageToken" -> pageToken.toString, "locationId" -> locationId.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GooglePrivacyDlpV2ListDeidentifyTemplatesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
