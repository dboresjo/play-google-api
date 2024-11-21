package com.boresjo.play.api.google.billingbudgets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://billingbudgets.googleapis.com/"

	object billingAccounts {
		object budgets {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudBillingBudgetsV1Budget(body: Schema.GoogleCloudBillingBudgetsV1Budget) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudBillingBudgetsV1Budget]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object get {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudBillingBudgetsV1Budget]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Indicates which fields in the provided budget to update. Read-only fields (such as `name`) cannot be changed. If this is not provided, then only fields with non-default values from the request are updated. See https://developers.google.com/protocol-buffers/docs/proto3#default for more details about default values.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudBillingBudgetsV1Budget(body: Schema.GoogleCloudBillingBudgetsV1Budget) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudBillingBudgetsV1Budget])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, budgetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets/${budgetsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse]) {
				/** Optional. The maximum number of budgets to return per page. The default and maximum value are 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The value returned by the last `ListBudgetsResponse` which indicates that this is a continuation of a prior `ListBudgets` call, and that the system should return the next page of data. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Set the scope of the budgets to be returned, in the format of the resource name. The scope of a budget is the cost that it tracks, such as costs for a single project, or the costs for all projects in a folder. Only project scope (in the format of "projects/project-id" or "projects/123") is supported in this field. When this field is set to a project's resource name, the budgets returned are tracking the costs for that project. */
				def withScope(scope: String) = new list(req.addQueryStringParameters("scope" -> scope.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/budgets")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
