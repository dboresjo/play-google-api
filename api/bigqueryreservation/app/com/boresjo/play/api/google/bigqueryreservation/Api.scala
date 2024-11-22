package com.boresjo.play.api.google.bigqueryreservation

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://bigqueryreservation.googleapis.com/"

	object projects {
		object locations {
			/** Updates a BI reservation. Only fields specified in the `field_mask` are updated. A singleton BI reservation always exists with default size 0. In order to reserve BI capacity it needs to be updated to an amount greater than 0. In order to release BI capacity reservation size must be set to 0. */
			class updateBiReservation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBiReservation(body: Schema.BiReservation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BiReservation])
			}
			object updateBiReservation {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateBiReservation = new updateBiReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/biReservation").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Retrieves a BI reservation. */
			class getBiReservation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BiReservation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BiReservation])
			}
			object getBiReservation {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getBiReservation = new getBiReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/biReservation").addQueryStringParameters("name" -> name.toString))
				given Conversion[getBiReservation, Future[Schema.BiReservation]] = (fun: getBiReservation) => fun.apply()
			}
			/** Deprecated: Looks up assignments for a specified resource for a particular region. If the request is about a project: 1. Assignments created on the project will be returned if they exist. 2. Otherwise assignments created on the closest ancestor will be returned. 3. Assignments for different JobTypes will all be returned. The same logic applies if the request is about a folder. If the request is about an organization, then assignments created on the organization will be returned (organization doesn't have ancestors). Comparing to ListAssignments, there are some behavior differences: 1. permission on the assignee will be verified in this API. 2. Hierarchy lookup (project->folder->organization) happens in this API. 3. Parent here is `projects/&#42;/locations/&#42;`, instead of `projects/&#42;/locations/&#42;reservations/&#42;`. &#42;&#42;Note&#42;&#42; "-" cannot be used for projects nor locations. */
			class searchAssignments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchAssignmentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchAssignmentsResponse])
			}
			object searchAssignments {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): searchAssignments = new searchAssignments(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchAssignments").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchAssignments, Future[Schema.SearchAssignmentsResponse]] = (fun: searchAssignments) => fun.apply()
			}
			/** Looks up assignments for a specified resource for a particular region. If the request is about a project: 1. Assignments created on the project will be returned if they exist. 2. Otherwise assignments created on the closest ancestor will be returned. 3. Assignments for different JobTypes will all be returned. The same logic applies if the request is about a folder. If the request is about an organization, then assignments created on the organization will be returned (organization doesn't have ancestors). Comparing to ListAssignments, there are some behavior differences: 1. permission on the assignee will be verified in this API. 2. Hierarchy lookup (project->folder->organization) happens in this API. 3. Parent here is `projects/&#42;/locations/&#42;`, instead of `projects/&#42;/locations/&#42;reservations/&#42;`. */
			class searchAllAssignments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchAllAssignmentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchAllAssignmentsResponse])
			}
			object searchAllAssignments {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): searchAllAssignments = new searchAllAssignments(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchAllAssignments").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchAllAssignments, Future[Schema.SearchAllAssignmentsResponse]] = (fun: searchAllAssignments) => fun.apply()
			}
			object capacityCommitments {
				/** Creates a new capacity commitment resource. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** The optional capacity commitment ID. Capacity commitment name will be generated automatically if this field is empty. This field must only contain lower case alphanumeric characters or dashes. The first and last character cannot be a dash. Max length is 64 characters. NOTE: this ID won't be kept if the capacity commitment is split or merged. */
					def withCapacityCommitmentId(capacityCommitmentId: String) = new create(req.addQueryStringParameters("capacityCommitmentId" -> capacityCommitmentId.toString))
					/** Perform the request */
					def withCapacityCommitment(body: Schema.CapacityCommitment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CapacityCommitment])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, enforceSingleAdminProjectPerOrg: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments").addQueryStringParameters("parent" -> parent.toString, "enforceSingleAdminProjectPerOrg" -> enforceSingleAdminProjectPerOrg.toString))
				}
				/** Merges capacity commitments of the same plan into a single commitment. The resulting capacity commitment has the greater commitment_end_time out of the to-be-merged capacity commitments. Attempting to merge capacity commitments of different plan will fail with the error code `google.rpc.Code.FAILED_PRECONDITION`. */
				class merge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withMergeCapacityCommitmentsRequest(body: Schema.MergeCapacityCommitmentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CapacityCommitment])
				}
				object merge {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): merge = new merge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments:merge").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Splits capacity commitment to two commitments of the same plan and `commitment_end_time`. A common use case is to enable downgrading commitments. For example, in order to downgrade from 10000 slots to 8000, you might split a 10000 capacity commitment into commitments of 2000 and 8000. Then, you delete the first one after the commitment end time passes. */
				class split(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSplitCapacityCommitmentRequest(body: Schema.SplitCapacityCommitmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SplitCapacityCommitmentResponse])
				}
				object split {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): split = new split(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}:split").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a capacity commitment. Attempting to delete capacity commitment before its commitment_end_time will fail with the error code `google.rpc.Code.FAILED_PRECONDITION`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns information about the capacity commitment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CapacityCommitment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CapacityCommitment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CapacityCommitment]] = (fun: get) => fun.apply()
				}
				/** Updates an existing capacity commitment. Only `plan` and `renewal_plan` fields can be updated. Plan can only be changed to a plan of a longer commitment period. Attempting to change to a plan with shorter commitment period will fail with the error code `google.rpc.Code.FAILED_PRECONDITION`. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCapacityCommitment(body: Schema.CapacityCommitment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CapacityCommitment])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, capacityCommitmentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments/${capacityCommitmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all the capacity commitments for the admin project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCapacityCommitmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCapacityCommitmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/capacityCommitments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCapacityCommitmentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object reservations {
				/** Failover a reservation to the secondary location. The operation should be done in the current secondary location, which will be promoted to the new primary location for the reservation. Attempting to failover a reservation in the current primary location will fail with the error code `google.rpc.Code.FAILED_PRECONDITION`. */
				class failoverReservation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFailoverReservationRequest(body: Schema.FailoverReservationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reservation])
				}
				object failoverReservation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): failoverReservation = new failoverReservation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}:failoverReservation").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new reservation resource. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withReservation(body: Schema.Reservation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reservation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, reservationId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "reservationId" -> reservationId.toString))
				}
				/** Deletes a reservation. Returns `google.rpc.Code.FAILED_PRECONDITION` when reservation has assignments. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns information about the reservation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Reservation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Reservation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Reservation]] = (fun: get) => fun.apply()
				}
				/** Updates an existing reservation resource. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withReservation(body: Schema.Reservation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Reservation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all the reservations for the project in the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReservationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReservationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListReservationsResponse]] = (fun: list) => fun.apply()
				}
				object assignments {
					/** Moves an assignment under a new reservation. This differs from removing an existing assignment and recreating a new one by providing a transactional change that ensures an assignee always has an associated reservation. */
					class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMoveAssignmentRequest(body: Schema.MoveAssignmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Assignment])
					}
					object move {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}:move").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates an assignment object which allows the given project to submit jobs of a certain type using slots from the specified reservation. Currently a resource (project, folder, organization) can only have one assignment per each (job_type, location) combination, and that reservation will be used for all jobs of the matching type. Different assignments can be created on different levels of the projects, folders or organization hierarchy. During query execution, the assignment is looked up at the project, folder and organization levels in that order. The first assignment found is applied to the query. When creating assignments, it does not matter if other assignments exist at higher levels. Example: &#42; The organization `organizationA` contains two projects, `project1` and `project2`. &#42; Assignments for all three entities (`organizationA`, `project1`, and `project2`) could all be created and mapped to the same or different reservations. "None" assignments represent an absence of the assignment. Projects assigned to None use on-demand pricing. To create a "None" assignment, use "none" as a reservation_id in the parent. Example parent: `projects/myproject/locations/US/reservations/none`. Returns `google.rpc.Code.PERMISSION_DENIED` if user does not have 'bigquery.admin' permissions on the project using the reservation and the project that owns this reservation. Returns `google.rpc.Code.INVALID_ARGUMENT` when location of the assignment does not match location of the reservation. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** The optional assignment ID. Assignment name will be generated automatically if this field is empty. This field must only contain lower case alphanumeric characters or dashes. Max length is 64 characters. */
						def withAssignmentId(assignmentId: String) = new create(req.addQueryStringParameters("assignmentId" -> assignmentId.toString))
						/** Perform the request */
						def withAssignment(body: Schema.Assignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Assignment])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a assignment. No expansion will happen. Example: &#42; Organization `organizationA` contains two projects, `project1` and `project2`. &#42; Reservation `res1` exists and was created previously. &#42; CreateAssignment was used previously to define the following associations between entities and reservations: `` and `` In this example, deletion of the `` assignment won't affect the other assignment ``. After said deletion, queries from `project1` will still use `res1` while queries from `project2` will switch to use on-demand mode. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Updates an existing assignment. Only the `priority` field can be updated. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withAssignment(body: Schema.Assignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Assignment])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, assignmentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments/${assignmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists assignments. Only explicitly created assignments will be returned. Example: &#42; Organization `organizationA` contains two projects, `project1` and `project2`. &#42; Reservation `res1` exists and was created previously. &#42; CreateAssignment was used previously to define the following associations between entities and reservations: `` and `` In this example, ListAssignments will just return the above two assignments for reservation `res1`, and no expansion/merge will happen. The wildcard "-" can be used for reservations in the request. In that case all assignments belongs to the specified project and location will be listed. &#42;&#42;Note&#42;&#42; "-" cannot be used for projects nor locations. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssignmentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssignmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/assignments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAssignmentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
