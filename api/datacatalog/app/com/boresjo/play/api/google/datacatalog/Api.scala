package com.boresjo.play.api.google.datacatalog

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

	private val BASE_URL = "https://datacatalog.googleapis.com/"

	object projects {
		object locations {
			/** Sets the configuration related to the migration to Dataplex for an organization or project. */
			class setConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatacatalogV1SetConfigRequest(body: Schema.GoogleCloudDatacatalogV1SetConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object setConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setConfig = new setConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:setConfig").addQueryStringParameters("name" -> name.toString))
			}
			/** Retrieves the effective configuration related to the migration from Data Catalog to Dataplex for a specific organization or project. If there is no specific configuration set for the resource, the setting is checked hierarchicahlly through the ancestors of the resource, starting from the resource itself. */
			class retrieveEffectiveConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object retrieveEffectiveConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveEffectiveConfig = new retrieveEffectiveConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:retrieveEffectiveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveEffectiveConfig, Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]] = (fun: retrieveEffectiveConfig) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object tagTemplates {
				/** Gets your permissions on a resource. Returns an empty set of permissions if the resource doesn't exist. Supported resources are: - Tag templates - Entry groups Note: This method gets policies only within Data Catalog and can't be used to get policies from BigQuery, Pub/Sub, Dataproc Metastore, and any external Google Cloud Platform resources ingested into Data Catalog. No Google IAM permissions are required to call this method. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. May return: &#42; A`NOT_FOUND` error if the resource doesn't exist or you don't have the permission to view it. &#42; An empty policy if the resource exists but doesn't have a set policy. Supported resources are: - Tag templates - Entry groups Note: This method doesn't get policies from Google Cloud Platform resources ingested into Data Catalog. To call this method, you must have the following Google IAM permissions: - `datacatalog.tagTemplates.getIamPolicy` to get policies on tag templates. - `datacatalog.entryGroups.getIamPolicy` to get policies on entry groups. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a tag template and all tags that use it. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a tag template. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1TagTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1TagTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates a tag template. You can't update template fields with this method. These fields are separate resources with their own create, update, and delete methods. You must enable the Data Catalog API in the project identified by the `tag_template.name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1TagTemplate(body: Schema.GoogleCloudDatacatalogV1TagTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a tag template. You must enable the Data Catalog API in the project identified by the `parent` parameter. For more information, see [Data Catalog resource project] (https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1TagTemplate(body: Schema.GoogleCloudDatacatalogV1TagTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, tagTemplateId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates").addQueryStringParameters("parent" -> parent.toString, "tagTemplateId" -> tagTemplateId.toString))
				}
				/** Sets an access control policy for a resource. Replaces any existing policy. Supported resources are: - Tag templates - Entry groups Note: This method sets policies only within Data Catalog and can't be used to manage policies in BigQuery, Pub/Sub, Dataproc Metastore, and any external Google Cloud Platform resources synced with the Data Catalog. To call this method, you must have the following Google IAM permissions: - `datacatalog.tagTemplates.setIamPolicy` to set policies on tag templates. - `datacatalog.entryGroups.setIamPolicy` to set policies on entry groups. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object fields {
					/** Creates a field in a tag template. You must enable the Data Catalog API in the project identified by the `parent` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1TagTemplateField(body: Schema.GoogleCloudDatacatalogV1TagTemplateField) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, parent: String, tagTemplateFieldId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields").addQueryStringParameters("parent" -> parent.toString, "tagTemplateFieldId" -> tagTemplateFieldId.toString))
					}
					/** Renames a field in a tag template. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project] (https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1RenameTagTemplateFieldRequest(body: Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object rename {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}:rename").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a field in a tag template and all uses of this field from the tags based on this template. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Updates a field in a tag template. You can't update the field type with this method. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Names of fields whose values to overwrite on an individual field of a tag template. The following fields are modifiable: &#42; `display_name` &#42; `type.enum_type` &#42; `is_required` If this parameter is absent or empty, all modifiable fields are overwritten. If such fields are non-required and omitted in the request body, their values are emptied with one exception: when updating an enum type, the provided values are merged with the existing values. Therefore, enum values can only be added, existing enum values cannot be deleted or renamed. Additionally, updating a template field from optional to required is &#42;not&#42; allowed.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withGoogleCloudDatacatalogV1TagTemplateField(body: Schema.GoogleCloudDatacatalogV1TagTemplateField) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString))
					}
					object enumValues {
						/** Renames an enum value in a tag template. Within a single enum field, enum values must be unique. */
						class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest(body: Schema.GoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1TagTemplateField])
						}
						object rename {
							def apply(projectsId :PlayApi, locationsId :PlayApi, tagTemplatesId :PlayApi, fieldsId :PlayApi, enumValuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/tagTemplates/${tagTemplatesId}/fields/${fieldsId}/enumValues/${enumValuesId}:rename").addQueryStringParameters("name" -> name.toString))
						}
					}
				}
			}
			object entryGroups {
				/** Gets your permissions on a resource. Returns an empty set of permissions if the resource doesn't exist. Supported resources are: - Tag templates - Entry groups Note: This method gets policies only within Data Catalog and can't be used to get policies from BigQuery, Pub/Sub, Dataproc Metastore, and any external Google Cloud Platform resources ingested into Data Catalog. No Google IAM permissions are required to call this method. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. May return: &#42; A`NOT_FOUND` error if the resource doesn't exist or you don't have the permission to view it. &#42; An empty policy if the resource exists but doesn't have a set policy. Supported resources are: - Tag templates - Entry groups Note: This method doesn't get policies from Google Cloud Platform resources ingested into Data Catalog. To call this method, you must have the following Google IAM permissions: - `datacatalog.tagTemplates.getIamPolicy` to get policies on tag templates. - `datacatalog.entryGroups.getIamPolicy` to get policies on entry groups. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes an entry group. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, deletes all entries in the entry group. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets an entry group. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1EntryGroup]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1EntryGroup]] = (fun: get) => fun.apply()
				}
				/** Updates an entry group. You must enable the Data Catalog API in the project identified by the `entry_group.name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1EntryGroup(body: Schema.GoogleCloudDatacatalogV1EntryGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists entry groups. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of items to return. Default is 10. Maximum limit is 1000. Throws an invalid argument if `page_size` is greater than 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token that specifies the next page to return. If empty, returns the first page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListEntryGroupsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates an entry group. An entry group contains logically related entries together with [Cloud Identity and Access Management](/data-catalog/docs/concepts/iam) policies. These policies specify users who can create, edit, and view entries within entry groups. Data Catalog automatically creates entry groups with names that start with the `@` symbol for the following resources: &#42; BigQuery entries (`@bigquery`) &#42; Pub/Sub topics (`@pubsub`) &#42; Dataproc Metastore services (`@dataproc_metastore_{SERVICE_NAME_HASH}`) You can create your own entry groups for Cloud Storage fileset entries and custom entries together with the corresponding IAM policies. User-created entry groups can't contain the `@` symbol, it is reserved for automatically created groups. Entry groups, like entries, can be searched. A maximum of 10,000 entry groups may be created per organization across all locations. You must enable the Data Catalog API in the project identified by the `parent` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1EntryGroup(body: Schema.GoogleCloudDatacatalogV1EntryGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryGroup])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, entryGroupId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups").addQueryStringParameters("parent" -> parent.toString, "entryGroupId" -> entryGroupId.toString))
				}
				/** Sets an access control policy for a resource. Replaces any existing policy. Supported resources are: - Tag templates - Entry groups Note: This method sets policies only within Data Catalog and can't be used to manage policies in BigQuery, Pub/Sub, Dataproc Metastore, and any external Google Cloud Platform resources synced with the Data Catalog. To call this method, you must have the following Google IAM permissions: - `datacatalog.tagTemplates.setIamPolicy` to set policies on tag templates. - `datacatalog.entryGroups.setIamPolicy` to set policies on entry groups. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object tags {
					/** Creates a tag and assigns it to: &#42; An Entry if the method name is `projects.locations.entryGroups.entries.tags.create`. &#42; Or EntryGroupif the method name is `projects.locations.entryGroups.tags.create`. Note: The project identified by the `parent` parameter for the [tag] (https://cloud.google.com/data-catalog/docs/reference/rest/v1/projects.locations.entryGroups.entries.tags/create#path-parameters) and the [tag template] (https://cloud.google.com/data-catalog/docs/reference/rest/v1/projects.locations.tagTemplates/create#path-parameters) used to create the tag must be in the same organization. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Updates an existing tag. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Deletes a tag. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, tagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Lists tags assigned to an Entry. The columns in the response are lowercased. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTagsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]] = (fun: list) => fun.apply()
					}
				}
				object entries {
					/** Gets your permissions on a resource. Returns an empty set of permissions if the resource doesn't exist. Supported resources are: - Tag templates - Entry groups Note: This method gets policies only within Data Catalog and can't be used to get policies from BigQuery, Pub/Sub, Dataproc Metastore, and any external Google Cloud Platform resources ingested into Data Catalog. No Google IAM permissions are required to call this method. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Creates an entry. You can create entries only with 'FILESET', 'CLUSTER', 'DATA_STREAM', or custom types. Data Catalog automatically creates entries with other types during metadata ingestion from integrated systems. You must enable the Data Catalog API in the project identified by the `parent` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). An entry group can have a maximum of 100,000 entries. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1Entry(body: Schema.GoogleCloudDatacatalogV1Entry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, entryId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries").addQueryStringParameters("parent" -> parent.toString, "entryId" -> entryId.toString))
					}
					/** Marks an Entry as NOT starred by the current user. Starring information is private to each user. */
					class unstar(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1UnstarEntryRequest(body: Schema.GoogleCloudDatacatalogV1UnstarEntryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1UnstarEntryResponse])
					}
					object unstar {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unstar = new unstar(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:unstar").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks an Entry as starred by the current user. Starring information is private to each user. */
					class star(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1StarEntryRequest(body: Schema.GoogleCloudDatacatalogV1StarEntryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1StarEntryResponse])
					}
					object star {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): star = new star(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:star").addQueryStringParameters("name" -> name.toString))
					}
					/** Imports entries from a source, such as data previously dumped into a Cloud Storage bucket, into Data Catalog. Import of entries is a sync operation that reconciles the state of the third-party system with the Data Catalog. `ImportEntries` accepts source data snapshots of a third-party system. Snapshot should be delivered as a .wire or base65-encoded .txt file containing a sequence of Protocol Buffer messages of DumpItem type. `ImportEntries` returns a long-running operation resource that can be queried with Operations.GetOperation to return ImportEntriesMetadata and an ImportEntriesResponse message. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1ImportEntriesRequest(body: Schema.GoogleCloudDatacatalogV1ImportEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Gets the access control policy for a resource. May return: &#42; A`NOT_FOUND` error if the resource doesn't exist or you don't have the permission to view it. &#42; An empty policy if the resource exists but doesn't have a set policy. Supported resources are: - Tag templates - Entry groups Note: This method doesn't get policies from Google Cloud Platform resources ingested into Data Catalog. To call this method, you must have the following Google IAM permissions: - `datacatalog.tagTemplates.getIamPolicy` to get policies on tag templates. - `datacatalog.entryGroups.getIamPolicy` to get policies on entry groups. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes an existing entry. You can delete only the entries created by the CreateEntry method. You must enable the Data Catalog API in the project identified by the `name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Modifies contacts, part of the business context of an Entry. To call this method, you must have the `datacatalog.entries.updateContacts` IAM permission on the corresponding project. */
					class modifyEntryContacts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1ModifyEntryContactsRequest(body: Schema.GoogleCloudDatacatalogV1ModifyEntryContactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Contacts])
					}
					object modifyEntryContacts {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): modifyEntryContacts = new modifyEntryContacts(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:modifyEntryContacts").addQueryStringParameters("name" -> name.toString))
					}
					/** Updates an existing entry. You must enable the Data Catalog API in the project identified by the `entry.name` parameter. For more information, see [Data Catalog resource project](https://cloud.google.com/data-catalog/docs/concepts/resource-project). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1Entry(body: Schema.GoogleCloudDatacatalogV1Entry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists entries. Note: Currently, this method can list only custom entries. To get a list of both custom and automatically created entries, use SearchCatalog. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListEntriesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListEntriesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, parent: String, pageSize: Int, pageToken: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListEntriesResponse]] = (fun: list) => fun.apply()
					}
					/** Modifies entry overview, part of the business context of an Entry. To call this method, you must have the `datacatalog.entries.updateOverview` IAM permission on the corresponding project. */
					class modifyEntryOverview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1ModifyEntryOverviewRequest(body: Schema.GoogleCloudDatacatalogV1ModifyEntryOverviewRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1EntryOverview])
					}
					object modifyEntryOverview {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): modifyEntryOverview = new modifyEntryOverview(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}:modifyEntryOverview").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets an entry. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Entry]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1Entry]] = (fun: get) => fun.apply()
					}
					object tags {
						/** Creates a tag and assigns it to: &#42; An Entry if the method name is `projects.locations.entryGroups.entries.tags.create`. &#42; Or EntryGroupif the method name is `projects.locations.entryGroups.tags.create`. Note: The project identified by the `parent` parameter for the [tag] (https://cloud.google.com/data-catalog/docs/reference/rest/v1/projects.locations.entryGroups.entries.tags/create#path-parameters) and the [tag template] (https://cloud.google.com/data-catalog/docs/reference/rest/v1/projects.locations.tagTemplates/create#path-parameters) used to create the tag must be in the same organization. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a tag. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, tagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** `ReconcileTags` creates or updates a list of tags on the entry. If the ReconcileTagsRequest.force_delete_missing parameter is set, the operation deletes tags not included in the input tag list. `ReconcileTags` returns a long-running operation resource that can be queried with Operations.GetOperation to return ReconcileTagsMetadata and a ReconcileTagsResponse message. */
						class reconcile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatacatalogV1ReconcileTagsRequest(body: Schema.GoogleCloudDatacatalogV1ReconcileTagsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object reconcile {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): reconcile = new reconcile(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags:reconcile").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Updates an existing tag. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatacatalogV1Tag(body: Schema.GoogleCloudDatacatalogV1Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Tag])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists tags assigned to an Entry. The columns in the response are lowercased. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTagsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, entryGroupsId :PlayApi, entriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/entryGroups/${entryGroupsId}/entries/${entriesId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTagsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object taxonomies {
				/** Returns your permissions on a specified policy tag or taxonomy. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Replaces (updates) a taxonomy and all its policy tags. The taxonomy and its entire hierarchy of policy tags must be represented literally by `SerializedTaxonomy` and the nested `SerializedPolicyTag` messages. This operation automatically does the following: - Deletes the existing policy tags that are missing from the `SerializedPolicyTag`. - Creates policy tags that don't have resource names. They are considered new. - Updates policy tags with valid resources names accordingly. */
				class replace(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1ReplaceTaxonomyRequest(body: Schema.GoogleCloudDatacatalogV1ReplaceTaxonomyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object replace {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:replace").addQueryStringParameters("name" -> name.toString))
				}
				/** Exports taxonomies in the requested type and returns them, including their policy tags. The requested taxonomies must belong to the same project. This method generates `SerializedTaxonomy` protocol buffers with nested policy tags that can be used as input for `ImportTaxonomies` calls. */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, taxonomies: String, serializedTaxonomies: Boolean)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies:export").addQueryStringParameters("parent" -> parent.toString, "taxonomies" -> taxonomies.toString, "serializedTaxonomies" -> serializedTaxonomies.toString))
					given Conversion[`export`, Future[Schema.GoogleCloudDatacatalogV1ExportTaxonomiesResponse]] = (fun: `export`) => fun.apply()
				}
				/** Creates new taxonomies (including their policy tags) in a given project by importing from inlined or cross-regional sources. For a cross-regional source, new taxonomies are created by copying from a source in another region. For an inlined source, taxonomies and policy tags are created in bulk using nested protocol buffer structures. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1ImportTaxonomiesRequest(body: Schema.GoogleCloudDatacatalogV1ImportTaxonomiesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ImportTaxonomiesResponse])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies:import").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the IAM policy for a policy tag or a taxonomy. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a taxonomy, including all policy tags in this taxonomy, their associated policies, and the policy tags references from BigQuery columns. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a taxonomy. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Taxonomy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1Taxonomy]] = (fun: get) => fun.apply()
				}
				/** Updates a taxonomy, including its display name, description, and activated policy types. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1Taxonomy(body: Schema.GoogleCloudDatacatalogV1Taxonomy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all taxonomies in a project in a particular location that you have a permission to view. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListTaxonomiesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a taxonomy in a specified project. The taxonomy is initially empty, that is, it doesn't contain policy tags. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogV1Taxonomy(body: Schema.GoogleCloudDatacatalogV1Taxonomy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Taxonomy])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the IAM policy for a policy tag or a taxonomy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object policyTags {
					/** Returns your permissions on a specified policy tag or taxonomy. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the IAM policy for a policy tag or a taxonomy. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a policy tag together with the following: &#42; All of its descendant policy tags, if any &#42; Policies associated with the policy tag and its descendants &#42; References from BigQuery table schema of the policy tag and its descendants */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a policy tag. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1PolicyTag]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogV1PolicyTag]] = (fun: get) => fun.apply()
					}
					/** Updates a policy tag, including its display name, description, and parent policy tag. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1PolicyTag(body: Schema.GoogleCloudDatacatalogV1PolicyTag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all policy tags in a taxonomy. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogV1ListPolicyTagsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a policy tag in a taxonomy. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogV1PolicyTag(body: Schema.GoogleCloudDatacatalogV1PolicyTag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1PolicyTag])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Sets the IAM policy for a policy tag or a taxonomy. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, taxonomiesId :PlayApi, policyTagsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/taxonomies/${taxonomiesId}/policyTags/${policyTagsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
		}
	}
	object catalog {
		/** Searches Data Catalog for multiple resources like entries and tags that match a query. This is a [Custom Method] (https://cloud.google.com/apis/design/custom_methods) that doesn't return all information on a resource, only its ID and high level fields. To get more information, you can subsequently call specific get methods. Note: Data Catalog search queries don't guarantee full recall. Results that match your query might not be returned, even in subsequent result pages. Additionally, returned (and not returned) results can vary if you repeat search queries. For more information, see [Data Catalog search syntax] (https://cloud.google.com/data-catalog/docs/how-to/search-reference). */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudDatacatalogV1SearchCatalogRequest(body: Schema.GoogleCloudDatacatalogV1SearchCatalogRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1SearchCatalogResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/catalog:search").addQueryStringParameters())
		}
	}
	object entries {
		/** Gets an entry by its target resource name. The resource name comes from the source Google Cloud Platform service. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1Entry]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1Entry])
		}
		object lookup {
			def apply(linkedResource: String, sqlResource: String, fullyQualifiedName: String, project: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/entries:lookup").addQueryStringParameters("linkedResource" -> linkedResource.toString, "sqlResource" -> sqlResource.toString, "fullyQualifiedName" -> fullyQualifiedName.toString, "project" -> project.toString, "location" -> location.toString))
			given Conversion[lookup, Future[Schema.GoogleCloudDatacatalogV1Entry]] = (fun: lookup) => fun.apply()
		}
	}
	object organizations {
		object locations {
			/** Sets the configuration related to the migration to Dataplex for an organization or project. */
			class setConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatacatalogV1SetConfigRequest(body: Schema.GoogleCloudDatacatalogV1SetConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object setConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setConfig = new setConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:setConfig").addQueryStringParameters("name" -> name.toString))
			}
			/** Retrieves the configuration related to the migration from Data Catalog to Dataplex for a specific organization, including all the projects under it which have a separate configuration set. */
			class retrieveConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1OrganizationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1OrganizationConfig])
			}
			object retrieveConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveConfig = new retrieveConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:retrieveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveConfig, Future[Schema.GoogleCloudDatacatalogV1OrganizationConfig]] = (fun: retrieveConfig) => fun.apply()
			}
			/** Retrieves the effective configuration related to the migration from Data Catalog to Dataplex for a specific organization or project. If there is no specific configuration set for the resource, the setting is checked hierarchicahlly through the ancestors of the resource, starting from the resource itself. */
			class retrieveEffectiveConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogV1MigrationConfig])
			}
			object retrieveEffectiveConfig {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveEffectiveConfig = new retrieveEffectiveConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}:retrieveEffectiveConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[retrieveEffectiveConfig, Future[Schema.GoogleCloudDatacatalogV1MigrationConfig]] = (fun: retrieveEffectiveConfig) => fun.apply()
			}
		}
	}
}
