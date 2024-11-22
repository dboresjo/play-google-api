package com.boresjo.play.api.google.testing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://testing.googleapis.com/"

	object projects {
		object testMatrices {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** A string id used to detect duplicated requests. Ids are automatically scoped to a project, so users should ensure the ID is unique per-project. A UUID is recommended. Optional, but strongly recommended. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				def withTestMatrix(body: Schema.TestMatrix) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestMatrix])
			}
			object create {
				def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices").addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestMatrix]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TestMatrix])
			}
			object get {
				def apply(projectId: String, testMatrixId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices/${testMatrixId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.TestMatrix]] = (fun: get) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CancelTestMatrixResponse]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.CancelTestMatrixResponse])
			}
			object cancel {
				def apply(projectId: String, testMatrixId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices/${testMatrixId}:cancel").addQueryStringParameters())
				given Conversion[cancel, Future[Schema.CancelTestMatrixResponse]] = (fun: cancel) => fun.apply()
			}
		}
		object deviceSessions {
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelDeviceSessionRequest(body: Schema.CancelDeviceSessionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDeviceSession(body: Schema.DeviceSession) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceSession])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeviceSession]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DeviceSession])
			}
			object get {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.DeviceSession]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Name of the DeviceSession, e.g. "projects/{project_id}/deviceSessions/{session_id}" */
				def withName(name: String) = new patch(req.addQueryStringParameters("name" -> name.toString))
				def withDeviceSession(body: Schema.DeviceSession) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.DeviceSession])
			}
			object patch {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDeviceSessionsResponse]) {
				/** Optional. The maximum number of DeviceSessions to return.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A continuation token for paging. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. If specified, responses will be filtered by the given filter. Allowed fields are: session_state. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDeviceSessionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListDeviceSessionsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object applicationDetailService {
		class getApkDetails(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFileReference(body: Schema.FileReference) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetApkDetailsResponse])
		}
		object getApkDetails {
			def apply(bundleLocationGcsPath: String)(using auth: AuthToken, ec: ExecutionContext): getApkDetails = new getApkDetails(ws.url(BASE_URL + s"v1/applicationDetailService/getApkDetails").addQueryStringParameters("bundleLocation.gcsPath" -> bundleLocationGcsPath.toString))
		}
	}
	object testEnvironmentCatalog {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestEnvironmentCatalog]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TestEnvironmentCatalog])
		}
		object get {
			def apply(environmentType: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/testEnvironmentCatalog/${environmentType}").addQueryStringParameters("projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.TestEnvironmentCatalog]] = (fun: get) => fun.apply()
		}
	}
}
