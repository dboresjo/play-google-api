package com.boresjo.play.api.google.binaryauthorization

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

	private val BASE_URL = "https://binaryauthorization.googleapis.com/"

	object projects {
		/** A policy specifies the attestors that must attest to a container image, before the project is allowed to deploy that image. There is at most one policy per project. All image admission requests are permitted if a project has no policy. Gets the policy for this project. Returns a default policy if the project does not have one. */
		class getPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getPolicy {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPolicy = new getPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy").addQueryStringParameters("name" -> name.toString))
			given Conversion[getPolicy, Future[Schema.Policy]] = (fun: getPolicy) => fun.apply()
		}
		/** Creates or updates a project's policy, and returns a copy of the new policy. A policy is always updated as a whole, to avoid race conditions with concurrent policy enforcement (or management!) requests. Returns `NOT_FOUND` if the project does not exist, `INVALID_ARGUMENT` if the request is malformed. */
		class updatePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Policy])
		}
		object updatePolicy {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updatePolicy = new updatePolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy").addQueryStringParameters("name" -> name.toString))
		}
		object attestors {
			/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Returns whether the given `Attestation` for the given image URI was signed by the given `Attestor` */
			class validateAttestationOccurrence(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withValidateAttestationOccurrenceRequest(body: Schema.ValidateAttestationOccurrenceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ValidateAttestationOccurrenceResponse])
			}
			object validateAttestationOccurrence {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, attestor: String)(using signer: RequestSigner, ec: ExecutionContext): validateAttestationOccurrence = new validateAttestationOccurrence(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:validateAttestationOccurrence").addQueryStringParameters("attestor" -> attestor.toString))
			}
			/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IamPolicy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IamPolicy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.IamPolicy]] = (fun: getIamPolicy) => fun.apply()
			}
			/** Deletes an attestor. Returns `NOT_FOUND` if the attestor does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets an attestor. Returns `NOT_FOUND` if the attestor does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attestor]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attestor])
			}
			object get {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Attestor]] = (fun: get) => fun.apply()
			}
			/** Updates an attestor. Returns `NOT_FOUND` if the attestor does not exist. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAttestor(body: Schema.Attestor) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Attestor])
			}
			object update {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists attestors. Returns `INVALID_ARGUMENT` if the project does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttestorsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttestorsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAttestorsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates an attestor, and returns a copy of the new attestor. Returns `NOT_FOUND` if the project does not exist, `INVALID_ARGUMENT` if the request is malformed, `ALREADY_EXISTS` if the attestor already exists. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAttestor(body: Schema.Attestor) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Attestor])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, attestorId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors").addQueryStringParameters("parent" -> parent.toString, "attestorId" -> attestorId.toString))
			}
			/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IamPolicy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
		}
		object platforms {
			object gke {
				object policies {
					/** Evaluates a Kubernetes object versus a GKE platform policy. Returns `NOT_FOUND` if the policy doesn't exist, `INVALID_ARGUMENT` if the policy or request is malformed and `PERMISSION_DENIED` if the client does not have sufficient permissions. */
					class evaluate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEvaluateGkePolicyRequest(body: Schema.EvaluateGkePolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EvaluateGkePolicyResponse])
					}
					object evaluate {
						def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): evaluate = new evaluate(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/gke/policies/${policiesId}:evaluate").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object policies {
				/** Creates a platform policy, and returns a copy of it. Returns `NOT_FOUND` if the project or platform doesn't exist, `INVALID_ARGUMENT` if the request is malformed, `ALREADY_EXISTS` if the policy already exists, and `INVALID_ARGUMENT` if the policy contains a platform-specific policy that does not match the platform value specified in the URL. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withPlatformPolicy(body: Schema.PlatformPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PlatformPolicy])
				}
				object create {
					def apply(projectsId :PlayApi, platformsId :PlayApi, parent: String, policyId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies").addQueryStringParameters("parent" -> parent.toString, "policyId" -> policyId.toString))
				}
				/** Deletes a platform policy. Returns `NOT_FOUND` if the policy doesn't exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Used to prevent deleting the policy when another request has updated it since it was retrieved. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a platform policy. Returns `NOT_FOUND` if the policy doesn't exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlatformPolicy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlatformPolicy])
				}
				object get {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PlatformPolicy]] = (fun: get) => fun.apply()
				}
				/** Replaces a platform policy. Returns `NOT_FOUND` if the policy doesn't exist. */
				class replacePlatformPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withPlatformPolicy(body: Schema.PlatformPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PlatformPolicy])
				}
				object replacePlatformPolicy {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replacePlatformPolicy = new replacePlatformPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists platform policies owned by a project in the specified platform. Returns `INVALID_ARGUMENT` if the project or the platform doesn't exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPlatformPoliciesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPlatformPoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, platformsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListPlatformPoliciesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object policy {
			/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IamPolicy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IamPolicy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IamPolicy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.IamPolicy]] = (fun: getIamPolicy) => fun.apply()
			}
			/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
		}
	}
	object systempolicy {
		/** Gets the current system policy in the specified location. */
		class getPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getPolicy {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPolicy = new getPolicy(ws.url(BASE_URL + s"v1/locations/${locationsId}/policy").addQueryStringParameters("name" -> name.toString))
			given Conversion[getPolicy, Future[Schema.Policy]] = (fun: getPolicy) => fun.apply()
		}
	}
}
