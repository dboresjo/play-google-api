package com.boresjo.play.api.google.chromepolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://chromepolicy.googleapis.com/"

	object media {
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleChromePolicyVersionsV1UploadPolicyFileRequest(body: Schema.GoogleChromePolicyVersionsV1UploadPolicyFileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1UploadPolicyFileResponse])
		}
		object upload {
			def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/files:uploadPolicyFile")).addQueryStringParameters("customer" -> customer.toString))
		}
	}
	object customers {
		object policies {
			class resolve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleChromePolicyVersionsV1ResolveRequest(body: Schema.GoogleChromePolicyVersionsV1ResolveRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1ResolveResponse])
			}
			object resolve {
				def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): resolve = new resolve(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies:resolve")).addQueryStringParameters("customer" -> customer.toString))
			}
			object orgunits {
				class batchModify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1BatchModifyOrgUnitPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchModifyOrgUnitPoliciesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchModify {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): batchModify = new batchModify(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/orgunits:batchModify")).addQueryStringParameters("customer" -> customer.toString))
				}
				class batchInherit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1BatchInheritOrgUnitPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchInheritOrgUnitPoliciesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchInherit {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): batchInherit = new batchInherit(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/orgunits:batchInherit")).addQueryStringParameters("customer" -> customer.toString))
				}
			}
			object groups {
				class batchModify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1BatchModifyGroupPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchModifyGroupPoliciesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchModify {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): batchModify = new batchModify(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:batchModify")).addQueryStringParameters("customer" -> customer.toString))
				}
				class listGroupPriorityOrdering(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1ListGroupPriorityOrderingRequest(body: Schema.GoogleChromePolicyVersionsV1ListGroupPriorityOrderingRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1ListGroupPriorityOrderingResponse])
				}
				object listGroupPriorityOrdering {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): listGroupPriorityOrdering = new listGroupPriorityOrdering(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:listGroupPriorityOrdering")).addQueryStringParameters("customer" -> customer.toString))
				}
				class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1BatchDeleteGroupPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchDeleteGroupPoliciesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:batchDelete")).addQueryStringParameters("customer" -> customer.toString))
				}
				class updateGroupPriorityOrdering(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1UpdateGroupPriorityOrderingRequest(body: Schema.GoogleChromePolicyVersionsV1UpdateGroupPriorityOrderingRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object updateGroupPriorityOrdering {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): updateGroupPriorityOrdering = new updateGroupPriorityOrdering(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:updateGroupPriorityOrdering")).addQueryStringParameters("customer" -> customer.toString))
				}
			}
			object networks {
				class defineNetwork(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1DefineNetworkRequest(body: Schema.GoogleChromePolicyVersionsV1DefineNetworkRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1DefineNetworkResponse])
				}
				object defineNetwork {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): defineNetwork = new defineNetwork(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:defineNetwork")).addQueryStringParameters("customer" -> customer.toString))
				}
				class removeNetwork(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1RemoveNetworkRequest(body: Schema.GoogleChromePolicyVersionsV1RemoveNetworkRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1RemoveNetworkResponse])
				}
				object removeNetwork {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): removeNetwork = new removeNetwork(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:removeNetwork")).addQueryStringParameters("customer" -> customer.toString))
				}
				class removeCertificate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1RemoveCertificateRequest(body: Schema.GoogleChromePolicyVersionsV1RemoveCertificateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1RemoveCertificateResponse])
				}
				object removeCertificate {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): removeCertificate = new removeCertificate(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:removeCertificate")).addQueryStringParameters("customer" -> customer.toString))
				}
				class defineCertificate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromePolicyVersionsV1DefineCertificateRequest(body: Schema.GoogleChromePolicyVersionsV1DefineCertificateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChromePolicyVersionsV1DefineCertificateResponse])
				}
				object defineCertificate {
					def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): defineCertificate = new defineCertificate(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:defineCertificate")).addQueryStringParameters("customer" -> customer.toString))
				}
			}
		}
		object policySchemas {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policySchemas")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromePolicyVersionsV1PolicySchema]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChromePolicyVersionsV1PolicySchema])
			}
			object get {
				def apply(customersId :PlayApi, policySchemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/customers/${customersId}/policySchemas/${policySchemasId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleChromePolicyVersionsV1PolicySchema]] = (fun: get) => fun.apply()
			}
		}
	}
}
