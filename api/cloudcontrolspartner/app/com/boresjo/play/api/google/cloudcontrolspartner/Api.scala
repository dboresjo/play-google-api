package com.boresjo.play.api.google.cloudcontrolspartner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudcontrolspartner.googleapis.com/"

	object organizations {
		object locations {
			class getPartner(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Partner]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Partner])
			}
			object getPartner {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPartner = new getPartner(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/partner").addQueryStringParameters("name" -> name.toString))
				given Conversion[getPartner, Future[Schema.Partner]] = (fun: getPartner) => fun.apply()
			}
			object customers {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCustomer(body: Schema.Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Customer])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, customerId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers").addQueryStringParameters("parent" -> parent.toString, "customerId" -> customerId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Customer]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Customer])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Customer]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The list of fields to update<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withCustomer(body: Schema.Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Customer])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomersResponse]) {
					/** Optional. Filtering results */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomersResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCustomersResponse]] = (fun: list) => fun.apply()
				}
				object workloads {
					class getPartnerPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PartnerPermissions]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PartnerPermissions])
					}
					object getPartnerPermissions {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPartnerPermissions = new getPartnerPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/partnerPermissions").addQueryStringParameters("name" -> name.toString))
						given Conversion[getPartnerPermissions, Future[Schema.PartnerPermissions]] = (fun: getPartnerPermissions) => fun.apply()
					}
					class getEkmConnections(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EkmConnections]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.EkmConnections])
					}
					object getEkmConnections {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEkmConnections = new getEkmConnections(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/ekmConnections").addQueryStringParameters("name" -> name.toString))
						given Conversion[getEkmConnections, Future[Schema.EkmConnections]] = (fun: getEkmConnections) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Workload]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Workload])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Workload]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkloadsResponse]) {
						/** Optional. Filtering results. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWorkloadsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListWorkloadsResponse]] = (fun: list) => fun.apply()
					}
					object accessApprovalRequests {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccessApprovalRequestsResponse]) {
							/** Optional. The maximum number of access requests to return. The service may return fewer than this value. If unspecified, at most 500 access requests will be returned.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListAccessApprovalRequests` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering results. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Hint for how to order the results. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccessApprovalRequestsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/accessApprovalRequests").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListAccessApprovalRequestsResponse]] = (fun: list) => fun.apply()
						}
					}
					object violations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViolationsResponse]) {
							/** Optional. The maximum number of customers row to return. The service may return fewer than this value. If unspecified, at most 10 customers will be returned.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListViolations` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering results */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Hint for how to order the results */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start.<br>Format: google-datetime */
							def withIntervalStartTime(intervalStartTime: String) = new list(req.addQueryStringParameters("interval.startTime" -> intervalStartTime.toString))
							/** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end.<br>Format: google-datetime */
							def withIntervalEndTime(intervalEndTime: String) = new list(req.addQueryStringParameters("interval.endTime" -> intervalEndTime.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViolationsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/violations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListViolationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Violation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Violation])
						}
						object get {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/violations/${violationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Violation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
