package com.boresjo.play.api.google.binaryauthorization

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://binaryauthorization.googleapis.com/"

	object projects {
		class getPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getPolicy {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPolicy = new getPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getPolicy, Future[Schema.Policy]] = (fun: getPolicy) => fun.apply()
		}
		class updatePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPolicy(body: Schema.Policy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Policy])
		}
		object updatePolicy {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updatePolicy = new updatePolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy")).addQueryStringParameters("name" -> name.toString))
		}
		object attestors {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
			class validateAttestationOccurrence(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValidateAttestationOccurrenceRequest(body: Schema.ValidateAttestationOccurrenceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ValidateAttestationOccurrenceResponse])
			}
			object validateAttestationOccurrence {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, attestor: String)(using auth: AuthToken, ec: ExecutionContext): validateAttestationOccurrence = new validateAttestationOccurrence(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:validateAttestationOccurrence")).addQueryStringParameters("attestor" -> attestor.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IamPolicy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.IamPolicy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.IamPolicy]] = (fun: getIamPolicy) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Attestor]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Attestor])
			}
			object get {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Attestor]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAttestor(body: Schema.Attestor) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Attestor])
			}
			object update {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttestorsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAttestorsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAttestorsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAttestor(body: Schema.Attestor) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Attestor])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, attestorId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors")).addQueryStringParameters("parent" -> parent.toString, "attestorId" -> attestorId.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IamPolicy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, attestorsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/attestors/${attestorsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
		}
		object platforms {
			object gke {
				object policies {
					class evaluate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEvaluateGkePolicyRequest(body: Schema.EvaluateGkePolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EvaluateGkePolicyResponse])
					}
					object evaluate {
						def apply(projectsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): evaluate = new evaluate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/gke/policies/${policiesId}:evaluate")).addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object policies {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPlatformPolicy(body: Schema.PlatformPolicy) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PlatformPolicy])
				}
				object create {
					def apply(projectsId :PlayApi, platformsId :PlayApi, parent: String, policyId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies")).addQueryStringParameters("parent" -> parent.toString, "policyId" -> policyId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Used to prevent deleting the policy when another request has updated it since it was retrieved. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlatformPolicy]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.PlatformPolicy])
				}
				object get {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PlatformPolicy]] = (fun: get) => fun.apply()
				}
				class replacePlatformPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPlatformPolicy(body: Schema.PlatformPolicy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.PlatformPolicy])
				}
				object replacePlatformPolicy {
					def apply(projectsId :PlayApi, platformsId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replacePlatformPolicy = new replacePlatformPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPlatformPoliciesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListPlatformPoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, platformsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/platforms/${platformsId}/policies")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListPlatformPoliciesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object policy {
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IamPolicy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IamPolicy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.IamPolicy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.IamPolicy]] = (fun: getIamPolicy) => fun.apply()
			}
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/policy:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
		}
	}
	object systempolicy {
		class getPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getPolicy {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPolicy = new getPolicy(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/policy")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getPolicy, Future[Schema.Policy]] = (fun: getPolicy) => fun.apply()
		}
	}
}
