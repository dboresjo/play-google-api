package com.boresjo.play.api.google.bigqueryreservation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://bigqueryreservation.googleapis.com/"

	object projects {
		object locations {
			class updateBiReservation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBiReservation(body: Schema.BiReservation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BiReservation])
			}
			object updateBiReservation {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateBiReservation = new updateBiReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/biReservation").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class getBiReservation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BiReservation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BiReservation])
			}
			object getBiReservation {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getBiReservation = new getBiReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/biReservation").addQueryStringParameters("name" -> name.toString))
				given Conversion[getBiReservation, Future[Schema.BiReservation]] = (fun: getBiReservation) => fun.apply()
			}
			class searchAssignments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchAssignmentsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchAssignmentsResponse])
			}
			object searchAssignments {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): searchAssignments = new searchAssignments(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchAssignments").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchAssignments, Future[Schema.SearchAssignmentsResponse]] = (fun: searchAssignments) => fun.apply()
			}
			class searchAllAssignments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchAllAssignmentsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchAllAssignmentsResponse])
			}
			object searchAllAssignments {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): searchAllAssignments = new searchAllAssignments(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchAllAssignments").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchAllAssignments, Future[Schema.SearchAllAssignmentsResponse]] = (fun: searchAllAssignments) => fun.apply()
			}
			object capacityCommitments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** The optional capacity commitment ID. Capacity commitment name will be generated automatically if this field is empty. This field must only contain lower case alphanumeric characters or dashes. The first and last character cannot be a dash. Max length is 64 characters. NOTE: this ID won't be kept if the capacity commitment is split or merged. */
					def withCapacityCommitmentId(capacityCommitmentId: String) = new create(req.addQueryStringParameters("capacityCommitmentId" -> capacityCommitmentId.toString))
					def withCapacityCommitment(body: Schema.CapacityCommitment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CapacityCommitment])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, enforceSingleAdminProjectPerOrg: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments").addQueryStringParameters("parent" -> parent.toString, "enforceSingleAdminProjectPerOrg" -> enforceSingleAdminProjectPerOrg.toString))
				}
				class merge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withMergeCapacityCommitmentsRequest(body: Schema.MergeCapacityCommitmentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CapacityCommitment])
				}
				object merge {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): merge = new merge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments:merge").addQueryStringParameters("parent" -> parent.toString))
				}
				class split(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSplitCapacityCommitmentRequest(body: Schema.SplitCapacityCommitmentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SplitCapacityCommitmentResponse])
				}
				object split {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): split = new split(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}:split").addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CapacityCommitment]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CapacityCommitment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CapacityCommitment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCapacityCommitment(body: Schema.CapacityCommitment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CapacityCommitment])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCapacityCommitmentsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCapacityCommitmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCapacityCommitmentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object reservations {
				class failoverReservation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFailoverReservationRequest(body: Schema.FailoverReservationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reservation])
				}
				object failoverReservation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): failoverReservation = new failoverReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}:failoverReservation").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReservation(body: Schema.Reservation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reservation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, reservationId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "reservationId" -> reservationId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Reservation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Reservation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Reservation]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReservation(body: Schema.Reservation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Reservation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReservationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListReservationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListReservationsResponse]] = (fun: list) => fun.apply()
				}
				object assignments {
					class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMoveAssignmentRequest(body: Schema.MoveAssignmentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Assignment])
					}
					object move {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}:move").addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** The optional assignment ID. Assignment name will be generated automatically if this field is empty. This field must only contain lower case alphanumeric characters or dashes. Max length is 64 characters. */
						def withAssignmentId(assignmentId: String) = new create(req.addQueryStringParameters("assignmentId" -> assignmentId.toString))
						def withAssignment(body: Schema.Assignment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Assignment])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAssignment(body: Schema.Assignment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Assignment])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssignmentsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAssignmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAssignmentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
