package com.boresjo.play.api.google.firebaseappdistribution

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebaseappdistribution.googleapis.com/"

	object projects {
		object testers {
			class batchRemove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1BatchRemoveTestersRequest(body: Schema.GoogleFirebaseAppdistroV1BatchRemoveTestersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1BatchRemoveTestersResponse])
			}
			object batchRemove {
				def apply(projectsId :PlayApi, project: String)(using auth: AuthToken, ec: ExecutionContext): batchRemove = new batchRemove(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers:batchRemove").addQueryStringParameters("project" -> project.toString))
			}
			class batchAdd(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1BatchAddTestersRequest(body: Schema.GoogleFirebaseAppdistroV1BatchAddTestersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1BatchAddTestersResponse])
			}
			object batchAdd {
				def apply(projectsId :PlayApi, project: String)(using auth: AuthToken, ec: ExecutionContext): batchAdd = new batchAdd(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers:batchAdd").addQueryStringParameters("project" -> project.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1Tester(body: Schema.GoogleFirebaseAppdistroV1Tester) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Tester])
			}
			object patch {
				def apply(projectsId :PlayApi, testersId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers/${testersId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListTestersResponse]) {
				/** Optional. The maximum number of testers to return. The service may return fewer than this value. The valid range is [1-1000]; If unspecified (0), at most 10 testers are returned. Values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListTesters` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListTesters` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The expression to filter testers listed in the response. To learn more about filtering, refer to [Google's AIP-160 standard](http://aip.dev/160). Supported fields: - `name` - `displayName` - `groups` Example: - `name = "projects/-/testers/&#42;@example.com"` - `displayName = "Joe Sixpack"` - `groups = "projects/&#42;/groups/qa-team"` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListTestersResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/testers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListTestersResponse]] = (fun: list) => fun.apply()
			}
		}
		object groups {
			class batchLeave(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1BatchLeaveGroupRequest(body: Schema.GoogleFirebaseAppdistroV1BatchLeaveGroupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchLeave {
				def apply(projectsId :PlayApi, groupsId :PlayApi, group: String)(using auth: AuthToken, ec: ExecutionContext): batchLeave = new batchLeave(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}:batchLeave").addQueryStringParameters("group" -> group.toString))
			}
			class batchJoin(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1BatchJoinGroupRequest(body: Schema.GoogleFirebaseAppdistroV1BatchJoinGroupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchJoin {
				def apply(projectsId :PlayApi, groupsId :PlayApi, group: String)(using auth: AuthToken, ec: ExecutionContext): batchJoin = new batchJoin(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}:batchJoin").addQueryStringParameters("group" -> group.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The "alias" to use for the group, which will become the final component of the group's resource name. This value must be unique per project. The field is named `groupId` to comply with AIP guidance for user-specified IDs. This value should be 4-63 characters, and valid characters are `/a-z-/`. If not set, it will be generated based on the display name. */
				def withGroupId(groupId: String) = new create(req.addQueryStringParameters("groupId" -> groupId.toString))
				def withGoogleFirebaseAppdistroV1Group(body: Schema.GoogleFirebaseAppdistroV1Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1Group]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object get {
				def apply(projectsId :PlayApi, groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1Group]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppdistroV1Group(body: Schema.GoogleFirebaseAppdistroV1Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Group])
			}
			object patch {
				def apply(projectsId :PlayApi, groupsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse]) {
				/** Optional. A page token, received from a previous `ListGroups` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListGroups` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of groups to return. The service may return fewer than this value. The valid range is [1-1000]; If unspecified (0), at most 25 groups are returned. Values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/groups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListGroupsResponse]] = (fun: list) => fun.apply()
			}
		}
		object apps {
			class getAabInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1AabInfo]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1AabInfo])
			}
			object getAabInfo {
				def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAabInfo = new getAabInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/aabInfo").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAabInfo, Future[Schema.GoogleFirebaseAppdistroV1AabInfo]] = (fun: getAabInfo) => fun.apply()
			}
			object releases {
				class distribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppdistroV1DistributeReleaseRequest(body: Schema.GoogleFirebaseAppdistroV1DistributeReleaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1DistributeReleaseResponse])
				}
				object distribute {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): distribute = new distribute(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}:distribute").addQueryStringParameters("name" -> name.toString))
				}
				class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppdistroV1BatchDeleteReleasesRequest(body: Schema.GoogleFirebaseAppdistroV1BatchDeleteReleasesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(projectsId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1Release]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Release])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1Release]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppdistroV1Release(body: Schema.GoogleFirebaseAppdistroV1Release) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1Release])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, appsId :PlayApi, pageSize: Int, filter: String, parent: String, orderBy: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases").addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListReleasesResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object cancel {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					class _wait(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleLongrunningWaitOperationRequest(body: Schema.GoogleLongrunningWaitOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object _wait {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): _wait = new _wait(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}:wait").addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, filter: String, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/operations").addQueryStringParameters("filter" -> filter.toString, "name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object feedbackReports {
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, feedbackReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports/${feedbackReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleFirebaseAppdistroV1ListFeedbackReportsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppdistroV1FeedbackReport]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppdistroV1FeedbackReport])
					}
					object get {
						def apply(projectsId :PlayApi, appsId :PlayApi, releasesId :PlayApi, feedbackReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases/${releasesId}/feedbackReports/${feedbackReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirebaseAppdistroV1FeedbackReport]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
	object media {
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFirebaseAppdistroV1UploadReleaseRequest(body: Schema.GoogleFirebaseAppdistroV1UploadReleaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object upload {
			def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/releases:upload").addQueryStringParameters("app" -> app.toString))
		}
	}
}
