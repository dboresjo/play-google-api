package com.boresjo.play.api.google.billingbudgets

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://billingbudgets.googleapis.com/"

	object billingAccounts {
		object budgets {
			/** Creates a new budget. See [Quotas and limits](https://cloud.google.com/billing/quotas) for more information on the limits of the number of budgets you can create. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudBillingBudgetsV1Budget(body: Schema.GoogleCloudBillingBudgetsV1Budget) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a budget. Returns successfully if already deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns a budget. WARNING: There are some fields exposed on the Google Cloud Console that aren't available on this API. When reading from the API, you will not see these fields in the return value, though they may have been set in the Cloud Console. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudBillingBudgetsV1Budget]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object get {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudBillingBudgetsV1Budget]] = (fun: get) => fun.apply()
			}
			/** Updates a budget and returns the updated budget. WARNING: There are some fields exposed on the Google Cloud Console that aren't available on this API. Budget fields that are not exposed in this API will not be changed by this method. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Indicates which fields in the provided budget to update. Read-only fields (such as `name`) cannot be changed. If this is not provided, then only fields with non-default values from the request are updated. See https://developers.google.com/protocol-buffers/docs/proto3#default for more details about default values.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudBillingBudgetsV1Budget(body: Schema.GoogleCloudBillingBudgetsV1Budget) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Returns a list of budgets for a billing account. WARNING: There are some fields exposed on the Google Cloud Console that aren't available on this API. When reading from the API, you will not see these fields in the return value, though they may have been set in the Cloud Console. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-billing""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of budgets to return per page. The default and maximum value are 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The value returned by the last `ListBudgetsResponse` which indicates that this is a continuation of a prior `ListBudgets` call, and that the system should return the next page of data. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Set the scope of the budgets to be returned, in the format of the resource name. The scope of a budget is the cost that it tracks, such as costs for a single project, or the costs for all projects in a folder. Only project scope (in the format of "projects/project-id" or "projects/123") is supported in this field. When this field is set to a project's resource name, the budgets returned are tracking the costs for that project. */
				def withScope(scope: String) = new list(req.addQueryStringParameters("scope" -> scope.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
