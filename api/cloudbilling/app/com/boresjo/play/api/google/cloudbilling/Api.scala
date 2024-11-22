package com.boresjo.play.api.google.cloudbilling

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
		"""https://www.googleapis.com/auth/cloud-billing""" /* View and manage your Google Cloud Platform billing accounts */,
		"""https://www.googleapis.com/auth/cloud-billing.readonly""" /* View your Google Cloud Platform billing accounts */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://cloudbilling.googleapis.com/"

	object billingAccounts {
		/** Tests the access control policy for a billing account. This method takes the resource and a set of permissions as input and returns the subset of the input permissions that the caller is allowed for that resource. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(billingAccountsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Changes which parent organization a billing account belongs to. */
		class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withMoveBillingAccountRequest(body: Schema.MoveBillingAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
		}
		object move {
			def apply(billingAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:move").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets the access control policy for a billing account. The caller must have the `billing.accounts.getIamPolicy` permission on the account, which is often given to billing account [viewers](https://cloud.google.com/billing/docs/how-to/billing-access). */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
			def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(billingAccountsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		/** Updates a billing account's fields. Currently the only field that can be edited is `display_name`. The current authenticated user must have the `billing.accounts.update` IAM permission, which is typically given to the [administrator](https://cloud.google.com/billing/docs/how-to/billing-access) of the billing account. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withBillingAccount(body: Schema.BillingAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BillingAccount])
		}
		object patch {
			def apply(billingAccountsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists the billing accounts that the current authenticated user has permission to [view](https://cloud.google.com/billing/docs/how-to/billing-access). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
			def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
		}
		/** This method creates [billing subaccounts](https://cloud.google.com/billing/docs/concepts#subaccounts). Google Cloud resellers should use the Channel Services APIs, [accounts.customers.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers/create) and [accounts.customers.entitlements.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers.entitlements/create). When creating a subaccount, the current authenticated user must have the `billing.accounts.update` IAM permission on the parent account, which is typically given to billing account [administrators](https://cloud.google.com/billing/docs/how-to/billing-access). This method will return an error if the parent account has not been provisioned for subaccounts. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
			def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
			/** Perform the request */
			def withBillingAccount(body: Schema.BillingAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/billingAccounts").addQueryStringParameters())
		}
		/** Sets the access control policy for a billing account. Replaces any existing policy. The caller must have the `billing.accounts.setIamPolicy` permission on the account, which is often given to billing account [administrators](https://cloud.google.com/billing/docs/how-to/billing-access). */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(billingAccountsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets information about a billing account. The current authenticated user must be a [viewer of the billing account](https://cloud.google.com/billing/docs/how-to/billing-access). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BillingAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BillingAccount])
		}
		object get {
			def apply(billingAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.BillingAccount]] = (fun: get) => fun.apply()
		}
		object subAccounts {
			/** Lists the billing accounts that the current authenticated user has permission to [view](https://cloud.google.com/billing/docs/how-to/billing-access). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/subAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
			}
			/** This method creates [billing subaccounts](https://cloud.google.com/billing/docs/concepts#subaccounts). Google Cloud resellers should use the Channel Services APIs, [accounts.customers.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers/create) and [accounts.customers.entitlements.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers.entitlements/create). When creating a subaccount, the current authenticated user must have the `billing.accounts.update` IAM permission on the parent account, which is typically given to billing account [administrators](https://cloud.google.com/billing/docs/how-to/billing-access). This method will return an error if the parent account has not been provisioned for subaccounts. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withBillingAccount(body: Schema.BillingAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
			}
			object create {
				def apply(billingAccountsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/subAccounts").addQueryStringParameters())
			}
		}
		object projects {
			/** Lists the projects associated with a billing account. The current authenticated user must have the `billing.resourceAssociations.list` IAM permission, which is often given to billing account [viewers](https://cloud.google.com/billing/docs/how-to/billing-access). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProjectBillingInfoResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProjectBillingInfoResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/projects").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProjectBillingInfoResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		object billingAccounts {
			/** Lists the billing accounts that the current authenticated user has permission to [view](https://cloud.google.com/billing/docs/how-to/billing-access). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBillingAccountsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The parent resource to list billing accounts from. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new list(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBillingAccountsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListBillingAccountsResponse]] = (fun: list) => fun.apply()
			}
			/** This method creates [billing subaccounts](https://cloud.google.com/billing/docs/concepts#subaccounts). Google Cloud resellers should use the Channel Services APIs, [accounts.customers.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers/create) and [accounts.customers.entitlements.create](https://cloud.google.com/channel/docs/reference/rest/v1/accounts.customers.entitlements/create). When creating a subaccount, the current authenticated user must have the `billing.accounts.update` IAM permission on the parent account, which is typically given to billing account [administrators](https://cloud.google.com/billing/docs/how-to/billing-access). This method will return an error if the parent account has not been provisioned for subaccounts. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The parent to create a billing account from. Format: - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
				def withParent(parent: String) = new create(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withBillingAccount(body: Schema.BillingAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BillingAccount])
			}
			object create {
				def apply(organizationsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts").addQueryStringParameters())
			}
			/** Changes which parent organization a billing account belongs to. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BillingAccount]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BillingAccount])
			}
			object move {
				def apply(organizationsId :PlayApi, billingAccountsId :PlayApi, destinationParent: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/billingAccounts/${billingAccountsId}:move").addQueryStringParameters("destinationParent" -> destinationParent.toString, "name" -> name.toString))
				given Conversion[move, Future[Schema.BillingAccount]] = (fun: move) => fun.apply()
			}
		}
	}
	object projects {
		/** Gets the billing information for a project. The current authenticated user must have the `resourcemanager.projects.get` permission for the project, which can be granted by assigning the [Project Viewer](https://cloud.google.com/iam/docs/understanding-roles#predefined_roles) role. */
		class getBillingInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectBillingInfo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProjectBillingInfo])
		}
		object getBillingInfo {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getBillingInfo = new getBillingInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/billingInfo").addQueryStringParameters("name" -> name.toString))
			given Conversion[getBillingInfo, Future[Schema.ProjectBillingInfo]] = (fun: getBillingInfo) => fun.apply()
		}
		/** Sets or updates the billing account associated with a project. You specify the new billing account by setting the `billing_account_name` in the `ProjectBillingInfo` resource to the resource name of a billing account. Associating a project with an open billing account enables billing on the project and allows charges for resource usage. If the project already had a billing account, this method changes the billing account used for resource usage charges. &#42;Note:&#42; Incurred charges that have not yet been reported in the transaction history of the Google Cloud Console might be billed to the new billing account, even if the charge occurred before the new billing account was assigned to the project. The current authenticated user must have ownership privileges for both the [project](https://cloud.google.com/docs/permissions-overview#h.bgs0oxofvnoo ) and the [billing account](https://cloud.google.com/billing/docs/how-to/billing-access). You can disable billing on the project by setting the `billing_account_name` field to empty. This action disassociates the current billing account from the project. Any billable activity of your in-use services will stop, and your application could stop functioning as expected. Any unbilled charges to date will be billed to the previously associated account. The current authenticated user must be either an owner of the project or an owner of the billing account for the project. Note that associating a project with a &#42;closed&#42; billing account will have much the same effect as disabling billing on the project: any paid resources used by the project will be shut down. Thus, unless you wish to disable billing, you should always call this method with the name of an &#42;open&#42; billing account. */
		class updateBillingInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withProjectBillingInfo(body: Schema.ProjectBillingInfo) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ProjectBillingInfo])
		}
		object updateBillingInfo {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateBillingInfo = new updateBillingInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/billingInfo").addQueryStringParameters("name" -> name.toString))
		}
	}
	object services {
		/** Lists all public cloud services. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
		}
		object skus {
			/** Lists all publicly available SKUs for a given cloud service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSkusResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-billing.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional inclusive start time of the time range for which the pricing versions will be returned. Timestamps in the future are not allowed. The time range has to be within a single calendar month in America/Los_Angeles timezone. Time range as a whole is optional. If not specified, the latest pricing will be returned (up to 12 hours old at most).<br>Format: google-datetime */
				def withStartTime(startTime: String) = new list(req.addQueryStringParameters("startTime" -> startTime.toString))
				/** Optional exclusive end time of the time range for which the pricing versions will be returned. Timestamps in the future are not allowed. The time range has to be within a single calendar month in America/Los_Angeles timezone. Time range as a whole is optional. If not specified, the latest pricing will be returned (up to 12 hours old at most).<br>Format: google-datetime */
				def withEndTime(endTime: String) = new list(req.addQueryStringParameters("endTime" -> endTime.toString))
				/** The ISO 4217 currency code for the pricing info in the response proto. Will use the conversion rate as of start_time. Optional. If not specified USD will be used. */
				def withCurrencyCode(currencyCode: String) = new list(req.addQueryStringParameters("currencyCode" -> currencyCode.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSkusResponse])
			}
			object list {
				def apply(servicesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/services/${servicesId}/skus").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSkusResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
