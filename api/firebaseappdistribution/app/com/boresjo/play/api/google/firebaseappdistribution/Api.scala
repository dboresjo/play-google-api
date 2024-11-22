package com.boresjo.play.api.google.firebaseappdistribution

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

	private val BASE_URL = "https://firebaseappdistribution.googleapis.com/"

	object projects {
		object testers {
			/** Batch removes testers. If found, this call deletes testers for the specified emails. Returns all deleted testers. */
			class batchRemove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1BatchRemoveTestersRequest(body: Schema.GoogleFirebaseAppdistroV1BatchRemoveTestersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1BatchRemoveTestersResponse])
			}
			object batchRemove {
				def apply(projectsId :PlayApi, project: String)(using signer: RequestSigner, ec: ExecutionContext): batchRemove = new batchRemove(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers:batchRemove").addQueryStringParameters("project" -> project.toString))
			}
			/** Batch adds testers. This call adds testers for the specified emails if they don't already exist. Returns all testers specified in the request, including newly created and previously existing testers. This action is idempotent. */
			class batchAdd(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1BatchAddTestersRequest(body: Schema.GoogleFirebaseAppdistroV1BatchAddTestersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1BatchAddTestersResponse])
			}
			object batchAdd {
				def apply(projectsId :PlayApi, project: String)(using signer: RequestSigner, ec: ExecutionContext): batchAdd = new batchAdd(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers:batchAdd").addQueryStringParameters("project" -> project.toString))
			}
			/** Update a tester. If the testers joins a group they gain access to all releases that the group has access to. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1Tester(body: Schema.GoogleFirebaseAppdistroV1Tester) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Tester])
			}
			object patch {
				def apply(projectsId :PlayApi, testersId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers/${testersId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists testers and their resource ids. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListTestersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of testers to return. The service may return fewer than this value. The valid range is [1-1000]; If unspecified (0), at most 10 testers are returned. Values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListTesters` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListTesters` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The expression to filter testers listed in the response. To learn more about filtering, refer to [Google's AIP-160 standard](http://aip.dev/160). Supported fields: - `name` - `displayName` - `groups` Example: - `name = "projects/-/testers/&#42;@example.com"` - `displayName = "Joe Sixpack"` - `groups = "projects/&#42;/groups/qa-team"` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListTestersResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListTestersResponse]] = (fun: list) => fun.apply()
			}
		}
		object groups {
			/** Batch removed members from a group. The testers will lose access to all releases that the groups have access to. */
			class batchLeave(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1BatchLeaveGroupRequest(body: Schema.GoogleFirebaseAppdistroV1BatchLeaveGroupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchLeave {
				def apply(projectsId :PlayApi, groupsId :PlayApi, group: String)(using signer: RequestSigner, ec: ExecutionContext): batchLeave = new batchLeave(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}:batchLeave").addQueryStringParameters("group" -> group.toString))
			}
			/** Batch adds members to a group. The testers will gain access to all releases that the groups have access to. */
			class batchJoin(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1BatchJoinGroupRequest(body: Schema.GoogleFirebaseAppdistroV1BatchJoinGroupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchJoin {
				def apply(projectsId :PlayApi, groupsId :PlayApi, group: String)(using signer: RequestSigner, ec: ExecutionContext): batchJoin = new batchJoin(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}:batchJoin").addQueryStringParameters("group" -> group.toString))
			}
			/** Create a group. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The "alias" to use for the group, which will become the final component of the group's resource name. This value must be unique per project. The field is named `groupId` to comply with AIP guidance for user-specified IDs. This value should be 4-63 characters, and valid characters are `/a-z-/`. If not set, it will be generated based on the display name. */
				def withGroupId(groupId: String) = new create(req.addQueryStringParameters("groupId" -> groupId.toString))
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1Group(body: Schema.GoogleFirebaseAppdistroV1Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Delete a group. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Get a group. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1Group]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object get {
				def apply(projectsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1Group]] = (fun: get) => fun.apply()
			}
			/** Update a group. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleFirebaseAppdistroV1Group(body: Schema.GoogleFirebaseAppdistroV1Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object patch {
				def apply(projectsId :PlayApi, groupsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** List groups. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. A page token, received from a previous `ListGroups` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListGroups` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of groups to return. The service may return fewer than this value. The valid range is [1-1000]; If unspecified (0), at most 25 groups are returned. Values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse]] = (fun: list) => fun.apply()
			}
		}
		object apps {
			/** Gets Android App Bundle (AAB) information for a Firebase app. */
			class getAabInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1AabInfo]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1AabInfo])
			}
			object getAabInfo {
				def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAabInfo = new getAabInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/aabInfo").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAabInfo, Future[Schema.GoogleFirebaseAppdistroV1AabInfo]] = (fun: getAabInfo) => fun.apply()
			}
			object releases {
				/** Distributes a release to testers. This call does the following: 1. Creates testers for the specified emails, if none exist. 2. Adds the testers and groups to the release. 3. Sends new testers an invitation email. 4. Sends existing testers a new release email. The request will fail with a `INVALID_ARGUMENT` if it contains a group that doesn't exist. */
				class distribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleFirebaseAppdistroV1DistributeReleaseRequest(body: Schema.GoogleFirebaseAppdistroV1DistributeReleaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1DistributeReleaseResponse])
				}
				object distribute {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): distribute = new distribute(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}:distribute").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes releases. A maximum of 100 releases can be deleted per request. */
				class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleFirebaseAppdistroV1BatchDeleteReleasesRequest(body: Schema.GoogleFirebaseAppdistroV1BatchDeleteReleasesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(projectsId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets a release. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1Release]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Release])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1Release]] = (fun: get) => fun.apply()
				}
				/** Updates a release. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleFirebaseAppdistroV1Release(body: Schema.GoogleFirebaseAppdistroV1Release) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Release])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists releases. By default, sorts by `createTime` in descending order. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, appsId :PlayApi, pageSize: Int, filter: String, parent: String, orderBy: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases").addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of `1`, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object cancel {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					/** Waits until the specified long-running operation is done or reaches at most a specified timeout, returning the latest state. If the operation is already done, the latest state is immediately returned. If the timeout specified is greater than the default HTTP/RPC timeout, the HTTP/RPC timeout is used. If the server does not support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Note that this method is on a best-effort basis. It may return the latest state before the specified timeout (including immediately), meaning even an immediate response is no guarantee that the operation is done. */
					class _wait(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleLongrunningWaitOperationRequest(body: Schema.GoogleLongrunningWaitOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object _wait {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): _wait = new _wait(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}:wait").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, filter: String, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations").addQueryStringParameters("filter" -> filter.toString, "name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object feedbackReports {
					/** Deletes a feedback report. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, feedbackReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports/${feedbackReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Lists feedback reports. By default, sorts by `createTime` in descending order. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a feedback report. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1FeedbackReport]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1FeedbackReport])
					}
					object get {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, feedbackReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports/${feedbackReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1FeedbackReport]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
	object media {
		/** Uploads a binary. Uploading a binary can result in a new release being created, an update to an existing release, or a no-op if a release with the same binary already exists. */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleFirebaseAppdistroV1UploadReleaseRequest(body: Schema.GoogleFirebaseAppdistroV1UploadReleaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object upload {
			def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases:upload").addQueryStringParameters("app" -> app.toString))
		}
	}
}
