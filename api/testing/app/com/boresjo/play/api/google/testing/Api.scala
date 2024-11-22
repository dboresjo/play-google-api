package com.boresjo.play.api.google.testing

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://testing.googleapis.com/"

	object projects {
		object testMatrices {
			/** Creates and runs a matrix of tests according to the given specifications. Unsupported environments will be returned in the state UNSUPPORTED. A test matrix is limited to use at most 2000 devices in parallel. The returned matrix will not yet contain the executions that will be created for this matrix. Execution creation happens later on and will require a call to GetTestMatrix. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed or if the matrix tries to use too many simultaneous devices. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** A string id used to detect duplicated requests. Ids are automatically scoped to a project, so users should ensure the ID is unique per-project. A UUID is recommended. Optional, but strongly recommended. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				/** Perform the request */
				def withTestMatrix(body: Schema.TestMatrix) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestMatrix])
			}
			object create {
				def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices").addQueryStringParameters())
			}
			/** Checks the status of a test matrix and the executions once they are created. The test matrix will contain the list of test executions to run if and only if the resultStorage.toolResultsExecution fields have been populated. Note: Flaky test executions may be added to the matrix at a later stage. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the Test Matrix does not exist */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestMatrix]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestMatrix])
			}
			object get {
				def apply(projectId: String, testMatrixId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices/${testMatrixId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.TestMatrix]] = (fun: get) => fun.apply()
			}
			/** Cancels unfinished test executions in a test matrix. This call returns immediately and cancellation proceeds asynchronously. If the matrix is already final, this operation will have no effect. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the Test Matrix does not exist */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CancelTestMatrixResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.CancelTestMatrixResponse])
			}
			object cancel {
				def apply(projectId: String, testMatrixId: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectId}/testMatrices/${testMatrixId}:cancel").addQueryStringParameters())
				given Conversion[cancel, Future[Schema.CancelTestMatrixResponse]] = (fun: cancel) => fun.apply()
			}
		}
		object deviceSessions {
			/** POST /v1/projects/{project_id}/deviceSessions/{device_session_id}:cancel Changes the DeviceSession to state FINISHED and terminates all connections. Canceled sessions are not deleted and can be retrieved or listed by the user until they expire based on the 28 day deletion policy. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withCancelDeviceSessionRequest(body: Schema.CancelDeviceSessionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** POST /v1/projects/{project_id}/deviceSessions */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDeviceSession(body: Schema.DeviceSession) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceSession])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** GET /v1/projects/{project_id}/deviceSessions/{device_session_id} Return a DeviceSession, which documents the allocation status and whether the device is allocated. Clients making requests from this API must poll GetDeviceSession. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeviceSession]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DeviceSession])
			}
			object get {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.DeviceSession]] = (fun: get) => fun.apply()
			}
			/** PATCH /v1/projects/{projectId}/deviceSessions/deviceSessionId}:updateDeviceSession Updates the current device session to the fields described by the update_mask. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Name of the DeviceSession, e.g. "projects/{project_id}/deviceSessions/{session_id}" */
				def withName(name: String) = new patch(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def withDeviceSession(body: Schema.DeviceSession) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.DeviceSession])
			}
			object patch {
				def apply(projectsId :PlayApi, deviceSessionsId :PlayApi, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions/${deviceSessionsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			/** GET /v1/projects/{project_id}/deviceSessions Lists device Sessions owned by the project user. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDeviceSessionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of DeviceSessions to return.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A continuation token for paging. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. If specified, responses will be filtered by the given filter. Allowed fields are: session_state. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDeviceSessionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/deviceSessions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListDeviceSessionsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object applicationDetailService {
		/** Gets the details of an Android application APK. */
		class getApkDetails(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withFileReference(body: Schema.FileReference) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetApkDetailsResponse])
		}
		object getApkDetails {
			def apply(bundleLocationGcsPath: String)(using signer: RequestSigner, ec: ExecutionContext): getApkDetails = new getApkDetails(ws.url(BASE_URL + s"v1/applicationDetailService/getApkDetails").addQueryStringParameters("bundleLocation.gcsPath" -> bundleLocationGcsPath.toString))
		}
	}
	object testEnvironmentCatalog {
		/** Gets the catalog of supported test environments. May return any of the following canonical error codes: - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the environment type does not exist - INTERNAL - if an internal error occurred */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestEnvironmentCatalog]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestEnvironmentCatalog])
		}
		object get {
			def apply(environmentType: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/testEnvironmentCatalog/${environmentType}").addQueryStringParameters("projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.TestEnvironmentCatalog]] = (fun: get) => fun.apply()
		}
	}
}
