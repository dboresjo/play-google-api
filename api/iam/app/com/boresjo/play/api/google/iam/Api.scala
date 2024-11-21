package com.boresjo.play.api.google.iam

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://iam.googleapis.com/"

	object policies {
		class createPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIamV2Policy(body: Schema.GoogleIamV2Policy) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object createPolicy {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, parent: String, policyId: String)(using auth: AuthToken, ec: ExecutionContext): createPolicy = new createPolicy(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}")).addQueryStringParameters("parent" -> parent.toString, "policyId" -> policyId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			/** Optional. The expected `etag` of the policy to delete. If the value does not match the value that is stored in IAM, the request fails with a `409` error code and `ABORTED` status. If you omit this field, the policy is deleted regardless of its current `etag`. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object delete {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV2Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV2Policy])
		}
		object get {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleIamV2Policy]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIamV2Policy(body: Schema.GoogleIamV2Policy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object update {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}")).addQueryStringParameters("name" -> name.toString))
		}
		class listPolicies(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV2ListPoliciesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV2ListPoliciesResponse])
		}
		object listPolicies {
			def apply(policiesId :PlayApi, policiesId1 :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listPolicies = new listPolicies(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[listPolicies, Future[Schema.GoogleIamV2ListPoliciesResponse]] = (fun: listPolicies) => fun.apply()
		}
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(policiesId :PlayApi, policiesId1 :PlayApi, policiesId2 :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/policies/${policiesId}/${policiesId1}/${policiesId2}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
	}
}
