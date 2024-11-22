package com.boresjo.play.api.google.cloudcontrolspartner

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

	private val BASE_URL = "https://cloudcontrolspartner.googleapis.com/"

	object organizations {
		object locations {
			/** Get details of a Partner. */
			class getPartner(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Partner]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Partner])
			}
			object getPartner {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPartner = new getPartner(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/partner").addQueryStringParameters("name" -> name.toString))
				given Conversion[getPartner, Future[Schema.Partner]] = (fun: getPartner) => fun.apply()
			}
			object customers {
				/** Creates a new customer. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCustomer(body: Schema.Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Customer])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, customerId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers").addQueryStringParameters("parent" -> parent.toString, "customerId" -> customerId.toString))
				}
				/** Delete details of a single customer */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single customer */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Customer]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Customer])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Customer]] = (fun: get) => fun.apply()
				}
				/** Update details of a single customer */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The list of fields to update<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withCustomer(body: Schema.Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Customer])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists customers of a partner identified by its Google Cloud organization ID */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Filtering results */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomersResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCustomersResponse]] = (fun: list) => fun.apply()
				}
				object workloads {
					/** Gets the partner permissions granted for a workload */
					class getPartnerPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PartnerPermissions]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PartnerPermissions])
					}
					object getPartnerPermissions {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPartnerPermissions = new getPartnerPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/partnerPermissions").addQueryStringParameters("name" -> name.toString))
						given Conversion[getPartnerPermissions, Future[Schema.PartnerPermissions]] = (fun: getPartnerPermissions) => fun.apply()
					}
					/** Gets the EKM connections associated with a workload */
					class getEkmConnections(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EkmConnections]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EkmConnections])
					}
					object getEkmConnections {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEkmConnections = new getEkmConnections(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/ekmConnections").addQueryStringParameters("name" -> name.toString))
						given Conversion[getEkmConnections, Future[Schema.EkmConnections]] = (fun: getEkmConnections) => fun.apply()
					}
					/** Gets details of a single workload */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workload]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workload])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Workload]] = (fun: get) => fun.apply()
					}
					/** Lists customer workloads for a given customer org id */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkloadsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Filtering results. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkloadsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListWorkloadsResponse]] = (fun: list) => fun.apply()
					}
					object accessApprovalRequests {
						/** Deprecated: Only returns access approval requests directly associated with an assured workload folder. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccessApprovalRequestsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of access requests to return. The service may return fewer than this value. If unspecified, at most 500 access requests will be returned.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListAccessApprovalRequests` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering results. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Hint for how to order the results. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccessApprovalRequestsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/accessApprovalRequests").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListAccessApprovalRequestsResponse]] = (fun: list) => fun.apply()
						}
					}
					object violations {
						/** Lists Violations for a workload Callers may also choose to read across multiple Customers or for a single customer as per [AIP-159](https://google.aip.dev/159) by using '-' (the hyphen or dash character) as a wildcard character instead of {customer} & {workload}. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}` */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViolationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViolationsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/violations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListViolationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets details of a single Violation. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Violation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Violation])
						}
						object get {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, customersId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/customers/${customersId}/workloads/${workloadsId}/violations/${violationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Violation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
