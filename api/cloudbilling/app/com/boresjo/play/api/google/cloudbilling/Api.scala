package com.boresjo.play.api.google.cloudbilling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudbilling.googleapis.com/"

	object billingAccounts {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(billingAccountsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withMoveBillingAccountRequest(body: Schema.MoveBillingAccountRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
		}
		object move {
			def apply(billingAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:move").addQueryStringParameters("name" -> name.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
			def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(billingAccountsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBillingAccount(body: Schema.BillingAccount) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BillingAccount])
		}
		object patch {
			def apply(billingAccountsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
			/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
			def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
			def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
			def withBillingAccount(body: Schema.BillingAccount) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/billingAccounts").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(billingAccountsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BillingAccount]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BillingAccount])
		}
		object get {
			def apply(billingAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.BillingAccount]] = (fun: get) => fun.apply()
		}
		object subAccounts {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
				/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/subAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
				def withBillingAccount(body: Schema.BillingAccount) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
			}
			object create {
				def apply(billingAccountsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/subAccounts").addQueryStringParameters())
			}
		}
		object projects {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProjectBillingInfoResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListProjectBillingInfoResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/projects").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProjectBillingInfoResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		object billingAccounts {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
				/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
				def withBillingAccount(body: Schema.BillingAccount) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
			}
			object create {
				def apply(organizationsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts").addQueryStringParameters())
			}
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BillingAccount]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BillingAccount])
			}
			object move {
				def apply(organizationsId :PlayApi, billingAccountsId :PlayApi, destinationParent: String, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts/${billingAccountsId}:move").addQueryStringParameters("destinationParent" -> destinationParent.toString, "name" -> name.toString))
				given Conversion[move, Future[Schema.BillingAccount]] = (fun: move) => fun.apply()
			}
		}
	}
	object projects {
		class getBillingInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectBillingInfo]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProjectBillingInfo])
		}
		object getBillingInfo {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getBillingInfo = new getBillingInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/billingInfo").addQueryStringParameters("name" -> name.toString))
			given Conversion[getBillingInfo, Future[Schema.ProjectBillingInfo]] = (fun: getBillingInfo) => fun.apply()
		}
		class updateBillingInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProjectBillingInfo(body: Schema.ProjectBillingInfo) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ProjectBillingInfo])
		}
		object updateBillingInfo {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateBillingInfo = new updateBillingInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/billingInfo").addQueryStringParameters("name" -> name.toString))
		}
	}
	object services {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
		}
		object skus {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSkusResponse]) {
				/** Optional inclusive start time of the time range for which the pricing versions will be returned. Timestamps in the future are not allowed. The time range has to be within a single calendar month in America/Los_Angeles timezone. Time range as a whole is optional. If not specified, the latest pricing will be returned (up to 12 hours old at most).<br>Format: google-datetime */
				def withStartTime(startTime: String) = new list(req.addQueryStringParameters("startTime" -> startTime.toString))
				/** Optional exclusive end time of the time range for which the pricing versions will be returned. Timestamps in the future are not allowed. The time range has to be within a single calendar month in America/Los_Angeles timezone. Time range as a whole is optional. If not specified, the latest pricing will be returned (up to 12 hours old at most).<br>Format: google-datetime */
				def withEndTime(endTime: String) = new list(req.addQueryStringParameters("endTime" -> endTime.toString))
				/** The ISO 4217 currency code for the pricing info in the response proto. Will use the conversion rate as of start_time. Optional. If not specified USD will be used. */
				def withCurrencyCode(currencyCode: String) = new list(req.addQueryStringParameters("currencyCode" -> currencyCode.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSkusResponse])
			}
			object list {
				def apply(servicesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services/${servicesId}/skus").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSkusResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
