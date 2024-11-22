package com.boresjo.play.api.google.accesscontextmanager

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

	private val BASE_URL = "https://accesscontextmanager.googleapis.com/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object accessPolicies {
		/** Returns the IAM permissions that the caller has on the specified Access Context Manager resource. The resource can be an AccessPolicy, AccessLevel, or ServicePerimeter. This method does not support other resources. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(accessPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the IAM policy for the specified Access Context Manager access policy. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(accessPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes an access policy based on the resource name. The long-running operation has a successful status after the access policy is removed from long-lasting storage. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(accessPoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Returns an access policy based on the name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessPolicy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessPolicy])
		}
		object get {
			def apply(accessPoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.AccessPolicy]] = (fun: get) => fun.apply()
		}
		/** Updates an access policy. The long-running operation from this RPC has a successful status after the changes to the access policy propagate to long-lasting storage. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAccessPolicy(body: Schema.AccessPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(accessPoliciesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Creates an access policy. This method fails if the organization already has an access policy. The long-running operation has a successful status after the access policy propagates to long-lasting storage. Syntactic and basic semantic errors are returned in `metadata` as a BadRequest proto. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAccessPolicy(body: Schema.AccessPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies").addQueryStringParameters())
		}
		/** Sets the IAM policy for the specified Access Context Manager access policy. This method replaces the existing IAM policy on the access policy. The IAM policy controls the set of users who can perform specific operations on the Access Context Manager access policy. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(accessPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Lists all access policies in an organization. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccessPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccessPoliciesResponse])
		}
		object list {
			def apply(parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccessPoliciesResponse]] = (fun: list) => fun.apply()
		}
		object authorizedOrgsDescs {
			/** Creates an authorized orgs desc. The long-running operation from this RPC has a successful status after the authorized orgs desc propagates to long-lasting storage. If a authorized orgs desc contains errors, an error response is returned for the first error encountered. The name of this `AuthorizedOrgsDesc` will be assigned during creation. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAuthorizedOrgsDesc(body: Schema.AuthorizedOrgsDesc) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an authorized orgs desc based on the resource name. The long-running operation from this RPC has a successful status after the authorized orgs desc is removed from long-lasting storage. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets an authorized orgs desc based on the resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedOrgsDesc]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedOrgsDesc])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AuthorizedOrgsDesc]] = (fun: get) => fun.apply()
			}
			/** Updates an authorized orgs desc. The long-running operation from this RPC has a successful status after the authorized orgs desc propagates to long-lasting storage. If a authorized orgs desc contains errors, an error response is returned for the first error encountered. Only the organization list in `AuthorizedOrgsDesc` can be updated. The name, authorization_type, asset_type and authorization_direction cannot be updated. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAuthorizedOrgsDesc(body: Schema.AuthorizedOrgsDesc) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, authorizedOrgsDescsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs/${authorizedOrgsDescsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all authorized orgs descs for an access policy. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedOrgsDescsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedOrgsDescsResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/authorizedOrgsDescs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedOrgsDescsResponse]] = (fun: list) => fun.apply()
			}
		}
		object servicePerimeters {
			/** Returns the IAM permissions that the caller has on the specified Access Context Manager resource. The resource can be an AccessPolicy, AccessLevel, or ServicePerimeter. This method does not support other resources. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Commits the dry-run specification for all the service perimeters in an access policy. A commit operation on a service perimeter involves copying its `spec` field to the `status` field of the service perimeter. Only service perimeters with `use_explicit_dry_run_spec` field set to true are affected by a commit operation. The long-running operation from this RPC has a successful status after the dry-run specifications for all the service perimeters have been committed. If a commit fails, it causes the long-running operation to return an error response and the entire commit operation is cancelled. When successful, the Operation.response field contains CommitServicePerimetersResponse. The `dry_run` and the `spec` fields are cleared after a successful commit operation. */
			class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withCommitServicePerimetersRequest(body: Schema.CommitServicePerimetersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object commit {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters:commit").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Creates a service perimeter. The long-running operation from this RPC has a successful status after the service perimeter propagates to long-lasting storage. If a service perimeter contains errors, an error response is returned for the first error encountered. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withServicePerimeter(body: Schema.ServicePerimeter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a service perimeter based on the resource name. The long-running operation from this RPC has a successful status after the service perimeter is removed from long-lasting storage. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets a service perimeter based on the resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServicePerimeter]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServicePerimeter])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ServicePerimeter]] = (fun: get) => fun.apply()
			}
			/** Updates a service perimeter. The long-running operation from this RPC has a successful status after the service perimeter propagates to long-lasting storage. If a service perimeter contains errors, an error response is returned for the first error encountered. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withServicePerimeter(body: Schema.ServicePerimeter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, servicePerimetersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters/${servicePerimetersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Replace all existing service perimeters in an access policy with the service perimeters provided. This is done atomically. The long-running operation from this RPC has a successful status after all replacements propagate to long-lasting storage. Replacements containing errors result in an error response for the first error encountered. Upon an error, replacement are cancelled and existing service perimeters are not affected. The Operation.response field contains ReplaceServicePerimetersResponse. */
			class replaceAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withReplaceServicePerimetersRequest(body: Schema.ReplaceServicePerimetersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object replaceAll {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): replaceAll = new replaceAll(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters:replaceAll").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists all service perimeters for an access policy. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServicePerimetersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServicePerimetersResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/servicePerimeters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListServicePerimetersResponse]] = (fun: list) => fun.apply()
			}
		}
		object accessLevels {
			/** Returns the IAM permissions that the caller has on the specified Access Context Manager resource. The resource can be an AccessPolicy, AccessLevel, or ServicePerimeter. This method does not support other resources. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Creates an access level. The long-running operation from this RPC has a successful status after the access level propagates to long-lasting storage. If access levels contain errors, an error response is returned for the first error encountered. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAccessLevel(body: Schema.AccessLevel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an access level based on the resource name. The long-running operation from this RPC has a successful status after the access level has been removed from long-lasting storage. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets an access level based on the resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessLevel]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessLevel])
			}
			object get {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String, accessLevelFormat: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString, "accessLevelFormat" -> accessLevelFormat.toString))
				given Conversion[get, Future[Schema.AccessLevel]] = (fun: get) => fun.apply()
			}
			/** Updates an access level. The long-running operation from this RPC has a successful status after the changes to the access level propagate to long-lasting storage. If access levels contain errors, an error response is returned for the first error encountered. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAccessLevel(body: Schema.AccessLevel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(accessPoliciesId :PlayApi, accessLevelsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels/${accessLevelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Replaces all existing access levels in an access policy with the access levels provided. This is done atomically. The long-running operation from this RPC has a successful status after all replacements propagate to long-lasting storage. If the replacement contains errors, an error response is returned for the first error encountered. Upon error, the replacement is cancelled, and existing access levels are not affected. The Operation.response field contains ReplaceAccessLevelsResponse. Removing access levels contained in existing service perimeters result in an error. */
			class replaceAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withReplaceAccessLevelsRequest(body: Schema.ReplaceAccessLevelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object replaceAll {
				def apply(accessPoliciesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): replaceAll = new replaceAll(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels:replaceAll").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists all access levels for an access policy. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccessLevelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccessLevelsResponse])
			}
			object list {
				def apply(accessPoliciesId :PlayApi, parent: String, pageSize: Int, pageToken: String, accessLevelFormat: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accessPolicies/${accessPoliciesId}/accessLevels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "accessLevelFormat" -> accessLevelFormat.toString))
				given Conversion[list, Future[Schema.ListAccessLevelsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object services {
		/** Lists all VPC-SC supported services. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSupportedServicesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSupportedServicesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListSupportedServicesResponse]] = (fun: list) => fun.apply()
		}
		/** Returns a VPC-SC supported service based on the service name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SupportedService]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SupportedService])
		}
		object get {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/services/${name}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SupportedService]] = (fun: get) => fun.apply()
		}
	}
	object organizations {
		object gcpUserAccessBindings {
			/** Creates a GcpUserAccessBinding. If the client specifies a name, the server ignores it. Fails if a resource already exists with the same group_key. Completion of this long-running operation does not necessarily signify that the new binding is deployed onto all affected users, which may take more time. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGcpUserAccessBinding(body: Schema.GcpUserAccessBinding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a GcpUserAccessBinding. Completion of this long-running operation does not necessarily signify that the binding deletion is deployed onto all affected users, which may take more time. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets the GcpUserAccessBinding with the given name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GcpUserAccessBinding]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GcpUserAccessBinding])
			}
			object get {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GcpUserAccessBinding]] = (fun: get) => fun.apply()
			}
			/** Updates a GcpUserAccessBinding. Completion of this long-running operation does not necessarily signify that the changed binding is deployed onto all affected users, which may take more time. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. This field controls whether or not certain repeated settings in the update request overwrite or append to existing settings on the binding. If true, then append. Otherwise overwrite. So far, only scoped_access_settings with reauth_settings supports appending. Global access_levels, access_levels in scoped_access_settings, dry_run_access_levels, reauth_settings, and session_settings are not compatible with append functionality, and the request will return an error if append=true when these settings are in the update_mask. The request will also return an error if append=true when "scoped_access_settings" is not set in the update_mask. */
				def withAppend(append: Boolean) = new patch(req.addQueryStringParameters("append" -> append.toString))
				/** Perform the request */
				def withGcpUserAccessBinding(body: Schema.GcpUserAccessBinding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(organizationsId :PlayApi, gcpUserAccessBindingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings/${gcpUserAccessBindingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all GcpUserAccessBindings for a Google Cloud organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGcpUserAccessBindingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Maximum number of items to return. The server may return fewer items. If left blank, the server may return any number of items.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If left blank, returns the first page. To enumerate all items, use the next_page_token from your previous list operation. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGcpUserAccessBindingsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/gcpUserAccessBindings").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListGcpUserAccessBindingsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
