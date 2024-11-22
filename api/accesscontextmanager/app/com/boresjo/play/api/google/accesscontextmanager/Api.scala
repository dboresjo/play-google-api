package com.boresjo.play.api.google.accesscontextmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://accesscontextmanager.googleapis.com/"

	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object accessPolicies {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(accessPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(accessPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(accessPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessPolicy]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessPolicy])
		}
		object get {
			def apply(accessPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.AccessPolicy]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccessPolicy(body: Schema.AccessPolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(accessPoliciesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccessPolicy(body: Schema.AccessPolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(accessPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccessPoliciesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccessPoliciesResponse])
		}
		object list {
			def apply(parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccessPoliciesResponse]] = (fun: list) => fun.apply()
		}
		object authorizedOrgsDescs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAuthorizedOrgsDesc(body: Schema.AuthorizedOrgsDesc) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedOrgsDesc]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedOrgsDesc])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AuthorizedOrgsDesc]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAuthorizedOrgsDesc(body: Schema.AuthorizedOrgsDesc) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedOrgsDescsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedOrgsDescsResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedOrgsDescsResponse]] = (fun: list) => fun.apply()
			}
		}
		object servicePerimeters {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCommitServicePerimetersRequest(body: Schema.CommitServicePerimetersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object commit {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters:commit").addQueryStringParameters("parent" -> parent.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withServicePerimeter(body: Schema.ServicePerimeter) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServicePerimeter]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ServicePerimeter])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ServicePerimeter]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withServicePerimeter(body: Schema.ServicePerimeter) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class replaceAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withReplaceServicePerimetersRequest(body: Schema.ReplaceServicePerimetersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object replaceAll {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): replaceAll = new replaceAll(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters:replaceAll").addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListServicePerimetersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListServicePerimetersResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListServicePerimetersResponse]] = (fun: list) => fun.apply()
			}
		}
		object accessLevels {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccessLevel(body: Schema.AccessLevel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessLevel]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessLevel])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String, accessLevelFormat: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString, "accessLevelFormat" -> accessLevelFormat.toString))
				given Conversion[get, Future[Schema.AccessLevel]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccessLevel(body: Schema.AccessLevel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class replaceAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withReplaceAccessLevelsRequest(body: Schema.ReplaceAccessLevelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object replaceAll {
				def apply(accessPoliciesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): replaceAll = new replaceAll(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels:replaceAll").addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccessLevelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccessLevelsResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String, accessLevelFormat: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "accessLevelFormat" -> accessLevelFormat.toString))
				given Conversion[list, Future[Schema.ListAccessLevelsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object services {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSupportedServicesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSupportedServicesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListSupportedServicesResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SupportedService]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SupportedService])
		}
		object get {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/services/${name}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SupportedService]] = (fun: get) => fun.apply()
		}
	}
	object organizations {
		object gcpUserAccessBindings {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGcpUserAccessBinding(body: Schema.GcpUserAccessBinding) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GcpUserAccessBinding]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GcpUserAccessBinding])
			}
			object get {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GcpUserAccessBinding]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. This field controls whether or not certain repeated settings in the update request overwrite or append to existing settings on the binding. If true, then append. Otherwise overwrite. So far, only scoped_access_settings with reauth_settings supports appending. Global access_levels, access_levels in scoped_access_settings, dry_run_access_levels, reauth_settings, and session_settings are not compatible with append functionality, and the request will return an error if append=true when these settings are in the update_mask. The request will also return an error if append=true when "scoped_access_settings" is not set in the update_mask. */
				def withAppend(append: Boolean) = new patch(req.addQueryStringParameters("append" -> append.toString))
				def withGcpUserAccessBinding(body: Schema.GcpUserAccessBinding) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGcpUserAccessBindingsResponse]) {
				/** Optional. Maximum number of items to return. The server may return fewer items. If left blank, the server may return any number of items.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If left blank, returns the first page. To enumerate all items, use the next_page_token from your previous list operation. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGcpUserAccessBindingsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListGcpUserAccessBindingsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
