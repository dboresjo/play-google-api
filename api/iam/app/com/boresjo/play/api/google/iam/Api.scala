package com.boresjo.play.api.google.iam

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

	private val BASE_URL = "https://iam.googleapis.com/"

	object policies {
		/** Creates a policy. */
		class createPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleIamV2Policy(body: Schema.GoogleIamV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object createPolicy {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, parent: String, policyId: String)(using signer: RequestSigner, ec: ExecutionContext): createPolicy = new createPolicy(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}").addQueryStringParameters("parent" -> parent.toString, "policyId" -> policyId.toString))
		}
		/** Deletes a policy. This action is permanent. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The expected `etag` of the policy to delete. If the value does not match the value that is stored in IAM, the request fails with a `409` error code and `ABORTED` status. If you omit this field, the policy is deleted regardless of its current `etag`. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object delete {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
		}
		/** Gets a policy. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV2Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV2Policy])
		}
		object get {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleIamV2Policy]] = (fun: get) => fun.apply()
		}
		/** Updates the specified policy. You can update only the rules and the display name for the policy. To update a policy, you should use a read-modify-write loop: 1. Use GetPolicy to read the current version of the policy. 2. Modify the policy as needed. 3. Use `UpdatePolicy` to write the updated policy. This pattern helps prevent conflicts between concurrent updates. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleIamV2Policy(body: Schema.GoogleIamV2Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object update {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}").addQueryStringParameters("name" -> name.toString))
		}
		/** Retrieves the policies of the specified kind that are attached to a resource. The response lists only policy metadata. In particular, policy rules are omitted. */
		class listPolicies(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV2ListPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV2ListPoliciesResponse])
		}
		object listPolicies {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listPolicies = new listPolicies(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[listPolicies, Future[Schema.GoogleIamV2ListPoliciesResponse]] = (fun: listPolicies) => fun.apply()
		}
		object operations {
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
	}
}
