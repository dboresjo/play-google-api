package com.boresjo.play.api.google.chromepolicy

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
		"""https://www.googleapis.com/auth/chrome.management.policy""" /* See, edit, create or delete policies applied to ChromeOS and Chrome Browsers managed within your organization */,
		"""https://www.googleapis.com/auth/chrome.management.policy.readonly""" /* See policies applied to ChromeOS and Chrome Browsers managed within your organization */
	)

	private val BASE_URL = "https://chromepolicy.googleapis.com/"

	object media {
		/** Creates an enterprise file from the content provided by user. Returns a public download url for end user. */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
			/** Perform the request */
			def withGoogleChromePolicyVersionsV1UploadPolicyFileRequest(body: Schema.GoogleChromePolicyVersionsV1UploadPolicyFileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1UploadPolicyFileResponse])
		}
		object upload {
			def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/files:uploadPolicyFile").addQueryStringParameters("customer" -> customer.toString))
		}
	}
	object customers {
		object policies {
			/** Gets the resolved policy values for a list of policies that match a search query. */
			class resolve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""", """https://www.googleapis.com/auth/chrome.management.policy.readonly""")
				/** Perform the request */
				def withGoogleChromePolicyVersionsV1ResolveRequest(body: Schema.GoogleChromePolicyVersionsV1ResolveRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1ResolveResponse])
			}
			object resolve {
				def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"v1/customers/${customersId}/policies:resolve").addQueryStringParameters("customer" -> customer.toString))
			}
			object orgunits {
				/** Modify multiple policy values that are applied to a specific org unit. All targets must have the same target format. That is to say that they must point to the same target resource and must have the same keys specified in `additionalTargetKeyNames`, though the values for those keys may be different. On failure the request will return the error details as part of the google.rpc.Status. */
				class batchModify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1BatchModifyOrgUnitPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchModifyOrgUnitPoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchModify {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): batchModify = new batchModify(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/orgunits:batchModify").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Modify multiple policy values that are applied to a specific org unit so that they now inherit the value from a parent (if applicable). All targets must have the same target format. That is to say that they must point to the same target resource and must have the same keys specified in `additionalTargetKeyNames`, though the values for those keys may be different. On failure the request will return the error details as part of the google.rpc.Status. */
				class batchInherit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1BatchInheritOrgUnitPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchInheritOrgUnitPoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchInherit {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): batchInherit = new batchInherit(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/orgunits:batchInherit").addQueryStringParameters("customer" -> customer.toString))
				}
			}
			object groups {
				/** Modify multiple policy values that are applied to a specific group. All targets must have the same target format. That is to say that they must point to the same target resource and must have the same keys specified in `additionalTargetKeyNames`, though the values for those keys may be different. On failure the request will return the error details as part of the google.rpc.Status. */
				class batchModify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1BatchModifyGroupPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchModifyGroupPoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchModify {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): batchModify = new batchModify(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:batchModify").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Retrieve a group priority ordering for an app. The target app must be supplied in `additionalTargetKeyNames` in the PolicyTargetKey. On failure the request will return the error details as part of the google.rpc.Status. */
				class listGroupPriorityOrdering(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""", """https://www.googleapis.com/auth/chrome.management.policy.readonly""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1ListGroupPriorityOrderingRequest(body: Schema.GoogleChromePolicyVersionsV1ListGroupPriorityOrderingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1ListGroupPriorityOrderingResponse])
				}
				object listGroupPriorityOrdering {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): listGroupPriorityOrdering = new listGroupPriorityOrdering(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:listGroupPriorityOrdering").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Delete multiple policy values that are applied to a specific group. All targets must have the same target format. That is to say that they must point to the same target resource and must have the same keys specified in `additionalTargetKeyNames`, though the values for those keys may be different. On failure the request will return the error details as part of the google.rpc.Status. */
				class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1BatchDeleteGroupPoliciesRequest(body: Schema.GoogleChromePolicyVersionsV1BatchDeleteGroupPoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:batchDelete").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Update a group priority ordering for an app. The target app must be supplied in `additionalTargetKeyNames` in the PolicyTargetKey. On failure the request will return the error details as part of the google.rpc.Status. */
				class updateGroupPriorityOrdering(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1UpdateGroupPriorityOrderingRequest(body: Schema.GoogleChromePolicyVersionsV1UpdateGroupPriorityOrderingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object updateGroupPriorityOrdering {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): updateGroupPriorityOrdering = new updateGroupPriorityOrdering(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/groups:updateGroupPriorityOrdering").addQueryStringParameters("customer" -> customer.toString))
				}
			}
			object networks {
				/** Define a new network. */
				class defineNetwork(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1DefineNetworkRequest(body: Schema.GoogleChromePolicyVersionsV1DefineNetworkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1DefineNetworkResponse])
				}
				object defineNetwork {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): defineNetwork = new defineNetwork(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:defineNetwork").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Remove an existing network by guid. */
				class removeNetwork(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1RemoveNetworkRequest(body: Schema.GoogleChromePolicyVersionsV1RemoveNetworkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1RemoveNetworkResponse])
				}
				object removeNetwork {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): removeNetwork = new removeNetwork(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:removeNetwork").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Remove an existing certificate by guid. */
				class removeCertificate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1RemoveCertificateRequest(body: Schema.GoogleChromePolicyVersionsV1RemoveCertificateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1RemoveCertificateResponse])
				}
				object removeCertificate {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): removeCertificate = new removeCertificate(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:removeCertificate").addQueryStringParameters("customer" -> customer.toString))
				}
				/** Creates a certificate at a specified OU for a customer. */
				class defineCertificate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""")
					/** Perform the request */
					def withGoogleChromePolicyVersionsV1DefineCertificateRequest(body: Schema.GoogleChromePolicyVersionsV1DefineCertificateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1DefineCertificateResponse])
				}
				object defineCertificate {
					def apply(customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): defineCertificate = new defineCertificate(ws.url(BASE_URL + s"v1/customers/${customersId}/policies/networks:defineCertificate").addQueryStringParameters("customer" -> customer.toString))
				}
			}
		}
		object policySchemas {
			/** Gets a list of policy schemas that match a specified filter value for a given customer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""", """https://www.googleapis.com/auth/chrome.management.policy.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/policySchemas").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleChromePolicyVersionsV1ListPolicySchemasResponse]] = (fun: list) => fun.apply()
			}
			/** Get a specific policy schema for a customer by its resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromePolicyVersionsV1PolicySchema]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chrome.management.policy""", """https://www.googleapis.com/auth/chrome.management.policy.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromePolicyVersionsV1PolicySchema])
			}
			object get {
				def apply(customersId :PlayApi, policySchemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/policySchemas/${policySchemasId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleChromePolicyVersionsV1PolicySchema]] = (fun: get) => fun.apply()
			}
		}
	}
}
