package com.boresjo.play.api.google.orgpolicy

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

	private val BASE_URL = "https://orgpolicy.googleapis.com/"

	object organizations {
		object constraints {
			/** Lists constraints that could be applied on the specified resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/constraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
		object customConstraints {
			/** Creates a custom constraint. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the organization does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ALREADY_EXISTS` if the constraint already exists on the given organization. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2CustomConstraint(body: Schema.GoogleCloudOrgpolicyV2CustomConstraint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a custom constraint. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a custom constraint. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the custom constraint does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2CustomConstraint]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object get {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2CustomConstraint]] = (fun: get) => fun.apply()
			}
			/** Updates a custom constraint. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint does not exist. Note: the supplied policy will perform a full overwrite of all fields. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2CustomConstraint(body: Schema.GoogleCloudOrgpolicyV2CustomConstraint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2CustomConstraint])
			}
			object patch {
				def apply(organizationsId :PlayApi, customConstraintsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints/${customConstraintsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Retrieves all of the custom constraints that exist on a particular organization resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/customConstraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListCustomConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
		object policies {
			/** Creates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ALREADY_EXISTS` if the policy already exists on the given Google Cloud resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or organization policy does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a policy on a resource. If no policy is set on the resource, `NOT_FOUND` is returned. The `etag` value can be used with `UpdatePolicy()` to update a policy during read-modify-write. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			/** Updates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or the policy do not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the policy Note: the supplied policy will perform a full overwrite of all fields. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Gets the effective policy on a resource. This is the result of merging policies in the resource hierarchy and evaluating conditions. The returned policy will not have an `etag` or `condition` set because it is an evaluated policy across multiple resources. Subtrees of Resource Manager resource hierarchy with 'under:' prefix will not be expanded. */
			class getEffectivePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(organizationsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			/** Retrieves all of the policies that exist on a particular resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/policies").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object projects {
		object policies {
			/** Creates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ALREADY_EXISTS` if the policy already exists on the given Google Cloud resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or organization policy does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a policy on a resource. If no policy is set on the resource, `NOT_FOUND` is returned. The `etag` value can be used with `UpdatePolicy()` to update a policy during read-modify-write. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			/** Updates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or the policy do not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the policy Note: the supplied policy will perform a full overwrite of all fields. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(projectsId :PlayApi, policiesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Gets the effective policy on a resource. This is the result of merging policies in the resource hierarchy and evaluating conditions. The returned policy will not have an `etag` or `condition` set because it is an evaluated policy across multiple resources. Subtrees of Resource Manager resource hierarchy with 'under:' prefix will not be expanded. */
			class getEffectivePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			/** Retrieves all of the policies that exist on a particular resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/policies").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object constraints {
			/** Lists constraints that could be applied on the specified resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/constraints").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object folders {
		object policies {
			/** Creates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ALREADY_EXISTS` if the policy already exists on the given Google Cloud resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or organization policy does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The current etag of policy. If an etag is provided and does not match the current etag of the policy, deletion will be blocked and an ABORTED error will be returned. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a policy on a resource. If no policy is set on the resource, `NOT_FOUND` is returned. The `etag` value can be used with `UpdatePolicy()` to update a policy during read-modify-write. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object get {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: get) => fun.apply()
			}
			/** Updates a policy. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the constraint or the policy do not exist. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the policy Note: the supplied policy will perform a full overwrite of all fields. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudOrgpolicyV2Policy(body: Schema.GoogleCloudOrgpolicyV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object patch {
				def apply(foldersId :PlayApi, policiesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Gets the effective policy on a resource. This is the result of merging policies in the resource hierarchy and evaluating conditions. The returned policy will not have an `etag` or `condition` set because it is an evaluated policy across multiple resources. Subtrees of Resource Manager resource hierarchy with 'under:' prefix will not be expanded. */
			class getEffectivePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2Policy])
			}
			object getEffectivePolicy {
				def apply(foldersId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEffectivePolicy = new getEffectivePolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies/${policiesId}:getEffectivePolicy").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEffectivePolicy, Future[Schema.GoogleCloudOrgpolicyV2Policy]] = (fun: getEffectivePolicy) => fun.apply()
			}
			/** Retrieves all of the policies that exist on a particular resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/policies").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object constraints {
			/** Lists constraints that could be applied on the specified resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/constraints").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudOrgpolicyV2ListConstraintsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
