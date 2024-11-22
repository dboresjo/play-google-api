package com.boresjo.play.api.google.logging

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
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/logging.admin""" /* Administrate log data for your projects */,
		"""https://www.googleapis.com/auth/logging.read""" /* View log data for your projects */,
		"""https://www.googleapis.com/auth/logging.write""" /* Submit log data for your projects */
	)

	private val BASE_URL = "https://logging.googleapis.com/"

	object locations {
		/** Lists information about the supported locations for this service. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets information about a location. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
		}
		object buckets {
			/** Creates a log bucket asynchronously that can be used to store log entries.After a bucket has been created, the bucket's location cannot be changed. */
			class createAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object createAsync {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
			}
			/** Undeletes a log bucket. A bucket that has been deleted can be undeleted within the grace period of 7 days. */
			class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object undelete {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			/** Updates a log bucket asynchronously.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
			class updateAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object updateAsync {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Creates a log bucket that can be used to store log entries. After a bucket has been created, the bucket's location cannot be changed. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
			}
			object create {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
			}
			/** Deletes a log bucket.Changes the bucket's lifecycle_state to the DELETE_REQUESTED state. After 7 days, the bucket will be purged and all log entries in the bucket will be permanently deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a log bucket. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
			}
			object get {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
			}
			/** Updates a log bucket.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
			}
			object patch {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists log buckets. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
			}
			object links {
				/** Lists links. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
				}
				object list {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a link. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Link])
				}
				object get {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
				}
				/** Asynchronously creates a linked dataset in BigQuery which makes it possible to use BigQuery to read the logs stored in the log bucket. A log bucket may currently only contain one link. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLink(body: Schema.Link) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
				}
				/** Deletes a link. This will also delete the corresponding BigQuery linked dataset. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
			object views {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a NOT_FOUND error.Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a view on a log bucket. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can delete the view. If this occurs, please try again in a few minutes. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a view on a log bucket. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogView])
				}
				object get {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
				}
				/** Updates a view on a log bucket. This method replaces the value of the filter field from the existing view with the corresponding value from the new view. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can update the view. If this occurs, please try again in a few minutes. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
				}
				object patch {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists views on a log bucket. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
				}
				object list {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a view over log entries in a log bucket. A bucket may contain a maximum of 30 views. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
				}
				object create {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy.Can return NOT_FOUND, INVALID_ARGUMENT, and PERMISSION_DENIED errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
	object exclusions {
		/** Creates a new exclusion in the _Default sink in a specified parent resource. Only log entries belonging to that resource can be excluded. You can have up to 10 exclusions in a resource. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Deletes an exclusion in the _Default sink. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets the description of an exclusion in the _Default sink. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
		}
		/** Changes one or more properties of an existing exclusion in the _Default sink. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
		}
		object patch {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists all the exclusions on the _Default sink in a parent resource. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
		}
	}
	object sinks {
		/** Creates a sink that exports specified log entries to a destination. The export begins upon ingress, unless the sink's writer_identity is not permitted to write to the destination. A sink can export log entries only from the resource owning the sink. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
			def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
			/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
			def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
			/** Perform the request */
			def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Deletes a sink. If the sink has a unique writer_identity, then that service account is also deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets a sink. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
		}
		/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
			def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
			/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
			def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
			/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
		}
		object update {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
		}
		/** Lists sinks. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
		}
	}
	object v2 {
		/** Gets the Logging CMEK settings for the given resource.Note: CMEK for the Log Router can be configured for Google Cloud projects, folders, organizations, and billing accounts. Once configured for an organization, it applies to all projects and folders in the Google Cloud organization.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class getCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		/** Updates the Log Router CMEK settings for the given resource.Note: CMEK for the Log Router can currently only be configured for Google Cloud organizations. Once configured, it applies to all projects and folders in the Google Cloud organization.UpdateCmekSettings fails when any of the following are true: The value of kms_key_name is invalid. The associated service account doesn't have the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key. Access to the key is disabled.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class updateCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Field mask identifying which fields from cmek_settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateCmekSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withCmekSettings(body: Schema.CmekSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CmekSettings])
		}
		object updateCmekSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateCmekSettings = new updateCmekSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cmekSettings").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets the settings for the given resource.Note: Settings can be retrieved for Google Cloud projects, folders, organizations, and billing accounts.See View default resource settings for Logging (https://cloud.google.com/logging/docs/default-settings#view-org-settings) for more information. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		/** Updates the settings for the given resource. This method applies to all feature configurations for organization and folders.UpdateSettings fails when any of the following are true: The value of storage_location either isn't supported by Logging or violates the location OrgPolicy. The default_sink_config field is set, but it has an unspecified filter write mode. The value of kms_key_name is invalid. The associated service account doesn't have the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key. Access to the key is disabled.See Configure default settings for organizations and folders (https://cloud.google.com/logging/docs/default-settings) for more information. */
		class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withSettings(body: Schema.Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/settings").addQueryStringParameters("name" -> name.toString))
		}
	}
	object projects {
		/** Gets the Logging CMEK settings for the given resource.Note: CMEK for the Log Router can be configured for Google Cloud projects, folders, organizations, and billing accounts. Once configured for an organization, it applies to all projects and folders in the Google Cloud organization.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class getCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/projects/${projectsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		/** Gets the settings for the given resource.Note: Settings can be retrieved for Google Cloud projects, folders, organizations, and billing accounts.See View default resource settings for Logging (https://cloud.google.com/logging/docs/default-settings#view-org-settings) for more information. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/projects/${projectsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			/** Creates a sink that exports specified log entries to a destination. The export begins upon ingress, unless the sink's writer_identity is not permitted to write to the destination. A sink can export log entries only from the resource owning the sink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a sink. If the sink has a unique writer_identity, then that service account is also deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Lists sinks. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			/** Creates a new exclusion in the _Default sink in a specified parent resource. Only log entries belonging to that resource can be excluded. You can have up to 10 exclusions in a resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an exclusion in the _Default sink. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the description of an exclusion in the _Default sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			/** Changes one or more properties of an existing exclusion in the _Default sink. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all the exclusions on the _Default sink in a parent resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object metrics {
			/** Creates a logs-based metric. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.write""")
				/** Perform the request */
				def withLogMetric(body: Schema.LogMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogMetric])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a logs-based metric. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a logs-based metric. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogMetric]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogMetric])
			}
			object get {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
				given Conversion[get, Future[Schema.LogMetric]] = (fun: get) => fun.apply()
			}
			/** Creates or updates a logs-based metric. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.write""")
				/** Perform the request */
				def withLogMetric(body: Schema.LogMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogMetric])
			}
			object update {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
			}
			/** Lists logs-based metrics. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogMetricsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogMetricsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogMetricsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			/** Deletes all the log entries in a log for the _Default Log Bucket. The log reappears if it receives new entries. Log entries written shortly before the delete operation might not be deleted. Entries received after the delete operation with a timestamp before the operation will be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, logsId :PlayApi, logName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				/** Creates a log scope. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				/** Deletes a log scope. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log scope. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				/** Updates a log scope. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists log scopes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				/** Lists the RecentQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				/** Creates a log bucket asynchronously that can be used to store log entries.After a bucket has been created, the bucket's location cannot be changed. */
				class createAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Undeletes a log bucket. A bucket that has been deleted can be undeleted within the grace period of 7 days. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a log bucket asynchronously.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class updateAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a log bucket that can be used to store log entries. After a bucket has been created, the bucket's location cannot be changed. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Deletes a log bucket.Changes the bucket's lifecycle_state to the DELETE_REQUESTED state. After 7 days, the bucket will be purged and all log entries in the bucket will be permanently deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log bucket. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				/** Updates a log bucket.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists log buckets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					/** Lists links. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a link. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					/** Asynchronously creates a linked dataset in BigQuery which makes it possible to use BigQuery to read the logs stored in the log bucket. A log bucket may currently only contain one link. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLink(body: Schema.Link) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					/** Deletes a link. This will also delete the corresponding BigQuery linked dataset. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a NOT_FOUND error.Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a view on a log bucket. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can delete the view. If this occurs, please try again in a few minutes. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a view on a log bucket. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					/** Lists views on a log bucket. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a view over log entries in a log bucket. A bucket may contain a maximum of 30 views. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy.Can return NOT_FOUND, INVALID_ARGUMENT, and PERMISSION_DENIED errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Updates a view on a log bucket. This method replaces the value of the filter field from the existing view with the corresponding value from the new view. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can update the view. If this occurs, please try again in a few minutes. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				/** Creates a new SavedQuery for the user making the request. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing SavedQuery that was created by the user making the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns all data associated with the requested query. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				/** Updates an existing SavedQuery. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists the SavedQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		/** Gets the Logging CMEK settings for the given resource.Note: CMEK for the Log Router can be configured for Google Cloud projects, folders, organizations, and billing accounts. Once configured for an organization, it applies to all projects and folders in the Google Cloud organization.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class getCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		/** Updates the Log Router CMEK settings for the given resource.Note: CMEK for the Log Router can currently only be configured for Google Cloud organizations. Once configured, it applies to all projects and folders in the Google Cloud organization.UpdateCmekSettings fails when any of the following are true: The value of kms_key_name is invalid. The associated service account doesn't have the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key. Access to the key is disabled.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class updateCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Field mask identifying which fields from cmek_settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateCmekSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withCmekSettings(body: Schema.CmekSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CmekSettings])
		}
		object updateCmekSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateCmekSettings = new updateCmekSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
		}
		/** Updates the settings for the given resource. This method applies to all feature configurations for organization and folders.UpdateSettings fails when any of the following are true: The value of storage_location either isn't supported by Logging or violates the location OrgPolicy. The default_sink_config field is set, but it has an unspecified filter write mode. The value of kms_key_name is invalid. The associated service account doesn't have the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key. Access to the key is disabled.See Configure default settings for organizations and folders (https://cloud.google.com/logging/docs/default-settings) for more information. */
		class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withSettings(body: Schema.Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/settings").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets the settings for the given resource.Note: Settings can be retrieved for Google Cloud projects, folders, organizations, and billing accounts.See View default resource settings for Logging (https://cloud.google.com/logging/docs/default-settings#view-org-settings) for more information. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			/** Creates a sink that exports specified log entries to a destination. The export begins upon ingress, unless the sink's writer_identity is not permitted to write to the destination. A sink can export log entries only from the resource owning the sink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a sink. If the sink has a unique writer_identity, then that service account is also deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Lists sinks. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			/** Creates a new exclusion in the _Default sink in a specified parent resource. Only log entries belonging to that resource can be excluded. You can have up to 10 exclusions in a resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an exclusion in the _Default sink. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the description of an exclusion in the _Default sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			/** Changes one or more properties of an existing exclusion in the _Default sink. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all the exclusions on the _Default sink in a parent resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			/** Deletes all the log entries in a log for the _Default Log Bucket. The log reappears if it receives new entries. Log entries written shortly before the delete operation might not be deleted. Entries received after the delete operation with a timestamp before the operation will be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, logsId :PlayApi, logName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				/** Creates a log scope. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				/** Deletes a log scope. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log scope. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				/** Updates a log scope. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists log scopes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				/** Lists the RecentQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				/** Creates a log bucket asynchronously that can be used to store log entries.After a bucket has been created, the bucket's location cannot be changed. */
				class createAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Undeletes a log bucket. A bucket that has been deleted can be undeleted within the grace period of 7 days. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a log bucket asynchronously.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class updateAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a log bucket that can be used to store log entries. After a bucket has been created, the bucket's location cannot be changed. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Deletes a log bucket.Changes the bucket's lifecycle_state to the DELETE_REQUESTED state. After 7 days, the bucket will be purged and all log entries in the bucket will be permanently deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log bucket. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				/** Updates a log bucket.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists log buckets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					/** Lists links. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a link. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					/** Asynchronously creates a linked dataset in BigQuery which makes it possible to use BigQuery to read the logs stored in the log bucket. A log bucket may currently only contain one link. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLink(body: Schema.Link) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					/** Deletes a link. This will also delete the corresponding BigQuery linked dataset. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a NOT_FOUND error.Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a view on a log bucket. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can delete the view. If this occurs, please try again in a few minutes. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a view on a log bucket. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					/** Lists views on a log bucket. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a view over log entries in a log bucket. A bucket may contain a maximum of 30 views. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy.Can return NOT_FOUND, INVALID_ARGUMENT, and PERMISSION_DENIED errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Updates a view on a log bucket. This method replaces the value of the filter field from the existing view with the corresponding value from the new view. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can update the view. If this occurs, please try again in a few minutes. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				/** Creates a new SavedQuery for the user making the request. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing SavedQuery that was created by the user making the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns all data associated with the requested query. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				/** Updates an existing SavedQuery. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists the SavedQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object folders {
		/** Gets the Logging CMEK settings for the given resource.Note: CMEK for the Log Router can be configured for Google Cloud projects, folders, organizations, and billing accounts. Once configured for an organization, it applies to all projects and folders in the Google Cloud organization.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class getCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		/** Updates the settings for the given resource. This method applies to all feature configurations for organization and folders.UpdateSettings fails when any of the following are true: The value of storage_location either isn't supported by Logging or violates the location OrgPolicy. The default_sink_config field is set, but it has an unspecified filter write mode. The value of kms_key_name is invalid. The associated service account doesn't have the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key. Access to the key is disabled.See Configure default settings for organizations and folders (https://cloud.google.com/logging/docs/default-settings) for more information. */
		class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withSettings(body: Schema.Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/settings").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets the settings for the given resource.Note: Settings can be retrieved for Google Cloud projects, folders, organizations, and billing accounts.See View default resource settings for Logging (https://cloud.google.com/logging/docs/default-settings#view-org-settings) for more information. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			/** Creates a sink that exports specified log entries to a destination. The export begins upon ingress, unless the sink's writer_identity is not permitted to write to the destination. A sink can export log entries only from the resource owning the sink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a sink. If the sink has a unique writer_identity, then that service account is also deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Lists sinks. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			/** Creates a new exclusion in the _Default sink in a specified parent resource. Only log entries belonging to that resource can be excluded. You can have up to 10 exclusions in a resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an exclusion in the _Default sink. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the description of an exclusion in the _Default sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			/** Changes one or more properties of an existing exclusion in the _Default sink. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all the exclusions on the _Default sink in a parent resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			/** Deletes all the log entries in a log for the _Default Log Bucket. The log reappears if it receives new entries. Log entries written shortly before the delete operation might not be deleted. Entries received after the delete operation with a timestamp before the operation will be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, logsId :PlayApi, logName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(foldersId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(foldersId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				/** Creates a log scope. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				/** Deletes a log scope. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log scope. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				/** Updates a log scope. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withLogScope(body: Schema.LogScope) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists log scopes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				/** Lists the RecentQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				/** Creates a log bucket asynchronously that can be used to store log entries.After a bucket has been created, the bucket's location cannot be changed. */
				class createAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Undeletes a log bucket. A bucket that has been deleted can be undeleted within the grace period of 7 days. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a log bucket asynchronously.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class updateAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a log bucket that can be used to store log entries. After a bucket has been created, the bucket's location cannot be changed. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Deletes a log bucket.Changes the bucket's lifecycle_state to the DELETE_REQUESTED state. After 7 days, the bucket will be purged and all log entries in the bucket will be permanently deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log bucket. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				/** Updates a log bucket.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists log buckets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					/** Lists links. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a link. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					/** Asynchronously creates a linked dataset in BigQuery which makes it possible to use BigQuery to read the logs stored in the log bucket. A log bucket may currently only contain one link. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLink(body: Schema.Link) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					/** Deletes a link. This will also delete the corresponding BigQuery linked dataset. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a NOT_FOUND error.Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a view on a log bucket. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can delete the view. If this occurs, please try again in a few minutes. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a view on a log bucket. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					/** Lists views on a log bucket. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a view over log entries in a log bucket. A bucket may contain a maximum of 30 views. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy.Can return NOT_FOUND, INVALID_ARGUMENT, and PERMISSION_DENIED errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Updates a view on a log bucket. This method replaces the value of the filter field from the existing view with the corresponding value from the new view. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can update the view. If this occurs, please try again in a few minutes. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				/** Creates a new SavedQuery for the user making the request. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing SavedQuery that was created by the user making the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns all data associated with the requested query. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				/** Updates an existing SavedQuery. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists the SavedQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object billingAccounts {
		/** Gets the Logging CMEK settings for the given resource.Note: CMEK for the Log Router can be configured for Google Cloud projects, folders, organizations, and billing accounts. Once configured for an organization, it applies to all projects and folders in the Google Cloud organization.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		class getCmekSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(billingAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		/** Gets the settings for the given resource.Note: Settings can be retrieved for Google Cloud projects, folders, organizations, and billing accounts.See View default resource settings for Logging (https://cloud.google.com/logging/docs/default-settings#view-org-settings) for more information. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(billingAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			/** Creates a sink that exports specified log entries to a destination. The export begins upon ingress, unless the sink's writer_identity is not permitted to write to the destination. A sink can export log entries only from the resource owning the sink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a sink. If the sink has a unique writer_identity, then that service account is also deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Updates a sink. This method replaces the values of the destination and filter fields of the existing sink with the corresponding values from the new sink.The updated sink might also have a new writer_identity; see the unique_writer_identity field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withLogSink(body: Schema.LogSink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			/** Lists sinks. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			/** Creates a new exclusion in the _Default sink in a specified parent resource. Only log entries belonging to that resource can be excluded. You can have up to 10 exclusions in a resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an exclusion in the _Default sink. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the description of an exclusion in the _Default sink. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			/** Changes one or more properties of an existing exclusion in the _Default sink. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def withLogExclusion(body: Schema.LogExclusion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all the exclusions on the _Default sink in a parent resource. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			/** Deletes all the log entries in a log for the _Default Log Bucket. The log reappears if it receives new entries. Log entries written shortly before the delete operation might not be deleted. Entries received after the delete operation with a timestamp before the operation will be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, logsId :PlayApi, logName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(billingAccountsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object recentQueries {
				/** Lists the RecentQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				/** Creates a log bucket asynchronously that can be used to store log entries.After a bucket has been created, the bucket's location cannot be changed. */
				class createAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Undeletes a log bucket. A bucket that has been deleted can be undeleted within the grace period of 7 days. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a log bucket asynchronously.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class updateAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a log bucket that can be used to store log entries. After a bucket has been created, the bucket's location cannot be changed. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				/** Deletes a log bucket.Changes the bucket's lifecycle_state to the DELETE_REQUESTED state. After 7 days, the bucket will be purged and all log entries in the bucket will be permanently deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a log bucket. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				/** Updates a log bucket.If the bucket has a lifecycle_state of DELETE_REQUESTED, then FAILED_PRECONDITION will be returned.After a bucket has been created, the bucket's location cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withLogBucket(body: Schema.LogBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists log buckets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					/** Lists links. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a link. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					/** Asynchronously creates a linked dataset in BigQuery which makes it possible to use BigQuery to read the logs stored in the log bucket. A log bucket may currently only contain one link. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLink(body: Schema.Link) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					/** Deletes a link. This will also delete the corresponding BigQuery linked dataset. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					/** Creates a view over log entries in a log bucket. A bucket may contain a maximum of 30 views. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					/** Deletes a view on a log bucket. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can delete the view. If this occurs, please try again in a few minutes. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a view on a log bucket. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					/** Lists views on a log bucket. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					/** Updates a view on a log bucket. This method replaces the value of the filter field from the existing view with the corresponding value from the new view. If an UNAVAILABLE error is returned, this indicates that system is not in a state where it can update the view. If this occurs, please try again in a few minutes. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withLogView(body: Schema.LogView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				/** Creates a new SavedQuery for the user making the request. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing SavedQuery that was created by the user making the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns all data associated with the requested query. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				/** Updates an existing SavedQuery. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
					/** Perform the request */
					def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists the SavedQueries that were created by the user making the request. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object entries {
		/** Copies a set of log entries from a log bucket to a Cloud Storage bucket. */
		class copy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def withCopyLogEntriesRequest(body: Schema.CopyLogEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object copy {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v2/entries:copy").addQueryStringParameters())
		}
		/** Writes log entries to Logging. This API method is the only way to send log entries to Logging. This method is used, directly or indirectly, by the Logging agent (fluentd) and all logging libraries configured to use Logging. A single request may contain log entries for a maximum of 1000 different resource names (projects, organizations, billing accounts or folders), where the resource name for a log entry is determined from its logName field. */
		class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.write""")
			/** Perform the request */
			def withWriteLogEntriesRequest(body: Schema.WriteLogEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteLogEntriesResponse])
		}
		object write {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v2/entries:write").addQueryStringParameters())
		}
		/** Lists log entries. Use this method to retrieve log entries that originated from a project/folder/organization/billing account. For ways to export log entries, see Exporting Logs (https://cloud.google.com/logging/docs/export). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def withListLogEntriesRequest(body: Schema.ListLogEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListLogEntriesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/entries:list").addQueryStringParameters())
		}
		/** Streaming read of log entries as they are received. Until the stream is terminated, it will continue reading logs. */
		class tail(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Perform the request */
			def withTailLogEntriesRequest(body: Schema.TailLogEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TailLogEntriesResponse])
		}
		object tail {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): tail = new tail(ws.url(BASE_URL + s"v2/entries:tail").addQueryStringParameters())
		}
	}
	object logs {
		/** Deletes all the log entries in a log for the _Default Log Bucket. The log reappears if it receives new entries. Log entries written shortly before the delete operation might not be deleted. Entries received after the delete operation with a timestamp before the operation will be deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/logging.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, logsId :PlayApi, logName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Lists the logs in projects, organizations, folders, or billing accounts. Only logs that have entries are listed. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
			def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/logs").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
		}
	}
	object monitoredResourceDescriptors {
		/** Lists the descriptors for monitored resource types used by Logging. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMonitoredResourceDescriptorsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/logging.admin""", """https://www.googleapis.com/auth/logging.read""")
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMonitoredResourceDescriptorsResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/monitoredResourceDescriptors").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListMonitoredResourceDescriptorsResponse]] = (fun: list) => fun.apply()
		}
	}
}
