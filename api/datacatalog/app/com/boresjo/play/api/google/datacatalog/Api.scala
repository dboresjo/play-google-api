package com.boresjo.play.api.google.datacatalog

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datacatalog.googleapis.com/"

	object projects {
		object locations {
			class setConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatacatalogV1SetConfigRequest(body: Schema.GoogleCloudDatacatalogV1SetConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object setConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setConfig = new setConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:setConfig").addQueryStringParameters("name" -> name.toString))
			}
			class retrieveEffectiveConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object retrieveEffectiveConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): retrieveEffectiveConfig = new retrieveEffectiveConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:retrieveEffectiveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveEffectiveConfig, Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]] = (fun: retrieveEffectiveConfig) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object tagTemplates {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1TagTemplate]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1TagTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1TagTemplate(body: Schema.GoogleCloudDatacatalogV1TagTemplate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1TagTemplate(body: Schema.GoogleCloudDatacatalogV1TagTemplate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, tagTemplateId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates").addQueryStringParameters("parent" -> parent.toString, "tagTemplateId" -> tagTemplateId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object fields {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1TagTemplateField(body: Schema.GoogleCloudDatacatalogV1TagTemplateField) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, parent: String, tagTemplateFieldId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields").addQueryStringParameters("parent" -> parent.toString, "tagTemplateFieldId" -> tagTemplateFieldId.toString))
					}
					class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1RenameTagTemplateFieldRequest(body: Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object rename {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}:rename").addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Names of fields whose values to overwrite on an individual field of a tag template. The following fields are modifiable: &#42; `display_name` &#42; `type.enum_type` &#42; `is_required` If this parameter is absent or empty, all modifiable fields are overwritten. If such fields are non-required and omitted in the request body, their values are emptied with one exception: when updating an enum type, the provided values are merged with the existing values. Therefore, enum values can only be added, existing enum values cannot be deleted or renamed. Additionally, updating a template field from optional to required is &#42;not&#42; allowed.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withGoogleCloudDatacatalogV1TagTemplateField(body: Schema.GoogleCloudDatacatalogV1TagTemplateField) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString))
					}
					object enumValues {
						class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest(body: Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
						}
						object rename {
							def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, enumValuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}/enumValues/${enumValuesId}:rename").addQueryStringParameters("name" -> name.toString))
						}
					}
				}
			}
			object entryGroups {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. If true, deletes all entries in the entry group. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1EntryGroup]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1EntryGroup]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1EntryGroup(body: Schema.GoogleCloudDatacatalogV1EntryGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse]) {
					/** Optional. The maximum number of items to return. Default is 10. Maximum limit is 1000. Throws an invalid argument if `page_size` is greater than 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token that specifies the next page to return. If empty, returns the first page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1EntryGroup(body: Schema.GoogleCloudDatacatalogV1EntryGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, entryGroupId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups").addQueryStringParameters("parent" -> parent.toString, "entryGroupId" -> entryGroupId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object tags {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags").addQueryStringParameters("parent" -> parent.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, tagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTagsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]] = (fun: list) => fun.apply()
					}
				}
				object entries {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1Entry(body: Schema.GoogleCloudDatacatalogV1Entry) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, entryId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries").addQueryStringParameters("parent" -> parent.toString, "entryId" -> entryId.toString))
					}
					class unstar(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1UnstarEntryRequest(body: Schema.GoogleCloudDatacatalogV1UnstarEntryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1UnstarEntryResponse])
					}
					object unstar {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unstar = new unstar(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:unstar").addQueryStringParameters("name" -> name.toString))
					}
					class star(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1StarEntryRequest(body: Schema.GoogleCloudDatacatalogV1StarEntryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1StarEntryResponse])
					}
					object star {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): star = new star(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:star").addQueryStringParameters("name" -> name.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1ImportEntriesRequest(body: Schema.GoogleCloudDatacatalogV1ImportEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class modifyEntryContacts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1ModifyEntryContactsRequest(body: Schema.GoogleCloudDatacatalogV1ModifyEntryContactsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Contacts])
					}
					object modifyEntryContacts {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): modifyEntryContacts = new modifyEntryContacts(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:modifyEntryContacts").addQueryStringParameters("name" -> name.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1Entry(body: Schema.GoogleCloudDatacatalogV1Entry) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListEntriesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListEntriesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, pageSize: Int, pageToken: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListEntriesResponse]] = (fun: list) => fun.apply()
					}
					class modifyEntryOverview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1ModifyEntryOverviewRequest(body: Schema.GoogleCloudDatacatalogV1ModifyEntryOverviewRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryOverview])
					}
					object modifyEntryOverview {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): modifyEntryOverview = new modifyEntryOverview(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:modifyEntryOverview").addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Entry]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1Entry]] = (fun: get) => fun.apply()
					}
					object tags {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, tagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class reconcile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatacatalogV1ReconcileTagsRequest(body: Schema.GoogleCloudDatacatalogV1ReconcileTagsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object reconcile {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): reconcile = new reconcile(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags:reconcile").addQueryStringParameters("parent" -> parent.toString))
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTagsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object taxonomies {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class replace(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1ReplaceTaxonomyRequest(body: Schema.GoogleCloudDatacatalogV1ReplaceTaxonomyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object replace {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:replace").addQueryStringParameters("name" -> name.toString))
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, taxonomies: String, serializedTaxonomies: Boolean)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies:export").addQueryStringParameters("parent" -> parent.toString, "taxonomies" -> taxonomies.toString, "serializedTaxonomies" -> serializedTaxonomies.toString))
					given Conversion[`export`, Future[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse]] = (fun: `export`) => fun.apply()
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1ImportTaxonomiesRequest(body: Schema.GoogleCloudDatacatalogV1ImportTaxonomiesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesResponse])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies:import").addQueryStringParameters("parent" -> parent.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Taxonomy]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1Taxonomy]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1Taxonomy(body: Schema.GoogleCloudDatacatalogV1Taxonomy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogV1Taxonomy(body: Schema.GoogleCloudDatacatalogV1Taxonomy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies").addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object policyTags {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1PolicyTag]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1PolicyTag]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1PolicyTag(body: Schema.GoogleCloudDatacatalogV1PolicyTag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogV1PolicyTag(body: Schema.GoogleCloudDatacatalogV1PolicyTag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags").addQueryStringParameters("parent" -> parent.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
		}
	}
	object catalog {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudDatacatalogV1SearchCatalogRequest(body: Schema.GoogleCloudDatacatalogV1SearchCatalogRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1SearchCatalogResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/catalog:search").addQueryStringParameters())
		}
	}
	object entries {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Entry]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
		}
		object lookup {
			def apply(linkedResource: String, sqlResource: String, fullyQualifiedName: String, project: String, location: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/entries:lookup").addQueryStringParameters("linkedResource" -> linkedResource.toString, "sqlResource" -> sqlResource.toString, "fullyQualifiedName" -> fullyQualifiedName.toString, "project" -> project.toString, "location" -> location.toString))
			given Conversion[lookup, Future[Schema.GoogleCloudDatacatalogV1Entry]] = (fun: lookup) => fun.apply()
		}
	}
	object organizations {
		object locations {
			class setConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatacatalogV1SetConfigRequest(body: Schema.GoogleCloudDatacatalogV1SetConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object setConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setConfig = new setConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:setConfig").addQueryStringParameters("name" -> name.toString))
			}
			class retrieveConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1OrganizationConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1OrganizationConfig])
			}
			object retrieveConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): retrieveConfig = new retrieveConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:retrieveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveConfig, Future[Schema.GoogleCloudDatacatalogV1OrganizationConfig]] = (fun: retrieveConfig) => fun.apply()
			}
			class retrieveEffectiveConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object retrieveEffectiveConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): retrieveEffectiveConfig = new retrieveEffectiveConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:retrieveEffectiveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveEffectiveConfig, Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]] = (fun: retrieveEffectiveConfig) => fun.apply()
			}
		}
	}
}
