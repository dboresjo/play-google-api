package com.boresjo.play.api.google.orgpolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://orgpolicy.googleapis.com/"

	object organizations {
		object constraints {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/constraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
		object customConstraints {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2CustomConstraint(body: Schema.GoogleCloudOrgpolicyV2CustomConstraint) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2CustomConstraint]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object get {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2CustomConstraint]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2CustomConstraint(body: Schema.GoogleCloudOrgpolicyV2CustomConstraint) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object patch {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
		object policies {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class getEffectivePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object projects {
		object policies {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(projectsId :PlayApi, policiesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class getEffectivePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object constraints {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/constraints").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object folders {
		object policies {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(foldersId :PlayApi, policiesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class getEffectivePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object constraints {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/constraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
