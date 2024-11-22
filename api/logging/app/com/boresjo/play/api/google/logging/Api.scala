package com.boresjo.play.api.google.logging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://logging.googleapis.com/"

	object locations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
		}
		object buckets {
			class createAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object createAsync {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
			}
			class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object undelete {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			class updateAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object updateAsync {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
			}
			object create {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
			}
			object get {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
			}
			object patch {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
			}
			object links {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
				}
				object list {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Link])
				}
				object get {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLink(body: Schema.Link) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
			object views {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogView])
				}
				object get {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
				}
				object patch {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
				}
				object list {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
				}
				object create {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(v2Id :PlayApi, v2Id1 :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
	object exclusions {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions").addQueryStringParameters("parent" -> parent.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
		}
		object patch {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
		}
	}
	object sinks {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
			def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
			/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
			def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
			def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks").addQueryStringParameters("parent" -> parent.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
			def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
			/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
			def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
			/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
		}
		object update {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/sinks").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
		}
	}
	object v2 {
		class getCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		class updateCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask identifying which fields from cmek_settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateCmekSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withCmekSettings(body: Schema.CmekSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CmekSettings])
		}
		object updateCmekSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateCmekSettings = new updateCmekSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cmekSettings").addQueryStringParameters("name" -> name.toString))
		}
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		class updateSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withSettings(body: Schema.Settings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/settings").addQueryStringParameters("name" -> name.toString))
		}
	}
	object projects {
		class getCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/projects/${projectsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/projects/${projectsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(projectsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(projectsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object metrics {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogMetric(body: Schema.LogMetric) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogMetric])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogMetric]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogMetric])
			}
			object get {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
				given Conversion[get, Future[Schema.LogMetric]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogMetric(body: Schema.LogMetric) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogMetric])
			}
			object update {
				def apply(projectsId :PlayApi, metricsId :PlayApi, metricName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics/${metricsId}").addQueryStringParameters("metricName" -> metricName.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogMetricsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogMetricsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/metrics").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogMetricsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, logsId :PlayApi, logName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				class createAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class updateAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLink(body: Schema.Link) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		class getCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		class updateCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask identifying which fields from cmek_settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateCmekSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withCmekSettings(body: Schema.CmekSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CmekSettings])
		}
		object updateCmekSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateCmekSettings = new updateCmekSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
		}
		class updateSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withSettings(body: Schema.Settings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/settings").addQueryStringParameters("name" -> name.toString))
		}
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(organizationsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(organizationsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, logsId :PlayApi, logName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				class createAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class updateAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLink(body: Schema.Link) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/organizations/${organizationsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object folders {
		class getCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		class updateSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask identifying which fields from settings should be updated. A field will be overwritten if and only if it is in the update mask. Output only fields cannot be updated.See FieldMask for more information.For example: "updateMask=kmsKeyName"<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withSettings(body: Schema.Settings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/settings").addQueryStringParameters("name" -> name.toString))
		}
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/folders/${foldersId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(foldersId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(foldersId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, logsId :PlayApi, logName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(foldersId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(foldersId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object logScopes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogScope])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, logScopeId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString, "logScopeId" -> logScopeId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogScope]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogScope])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogScope]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that specifies the fields in log_scope that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=description<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withLogScope(body: Schema.LogScope) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogScope])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, logScopesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes/${logScopesId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogScopesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogScopesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/logScopes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListLogScopesResponse]] = (fun: list) => fun.apply()
				}
			}
			object recentQueries {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				class createAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class updateAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLink(body: Schema.Link) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(foldersId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/folders/${foldersId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object billingAccounts {
		class getCmekSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekSettings])
		}
		object getCmekSettings {
			def apply(billingAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekSettings = new getCmekSettings(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/cmekSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getCmekSettings, Future[Schema.CmekSettings]] = (fun: getCmekSettings) => fun.apply()
		}
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply(billingAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/settings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		object sinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Determines the kind of IAM identity returned as writer_identity in the new sink. If this value is omitted or set to false, and if the sink's parent is a project, then the value returned as writer_identity is the same group or service account used by Cloud Logging before the addition of writer identities to this API. The sink's destination must be in the same project as the sink itself.If this field is set to true, or if the sink is owned by a non-project resource such as an organization, then the value of writer_identity will be a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) used by the sinks with the same parent. For more information, see writer_identity in LogSink. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new create(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new create(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogSink])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogSink]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogSink])
			}
			object get {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
				given Conversion[get, Future[Schema.LogSink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new update(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new update(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new update(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LogSink])
			}
			object update {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. See sinks.create for a description of this field. When updating a sink, the effect of this field on the value of writer_identity in the updated sink depends on both the old and new values of this field: If the old and new values of this field are both false or both true, then there is no change to the sink's writer_identity. If the old value is false and the new value is true, then writer_identity is changed to a service agent (https://cloud.google.com/iam/docs/service-account-types#service-agents) owned by Cloud Logging. It is an error if the old value is true and the new value is set to false or defaulted to false. */
				def withUniqueWriterIdentity(uniqueWriterIdentity: Boolean) = new patch(req.addQueryStringParameters("uniqueWriterIdentity" -> uniqueWriterIdentity.toString))
				/** Optional. The service account provided by the caller that will be used to write the log entries. The format must be serviceAccount:some@email. This field can only be specified when you are routing logs to a log bucket that is in a different project than the sink. When not specified, a Logging service account will automatically be generated. */
				def withCustomWriterIdentity(customWriterIdentity: String) = new patch(req.addQueryStringParameters("customWriterIdentity" -> customWriterIdentity.toString))
				/** Optional. Field mask that specifies the fields in sink that need an update. A sink field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.An empty updateMask is temporarily treated as using the following mask for backwards compatibility purposes:destination,filter,includeChildrenAt some point in the future, behavior will be removed and specifying an empty updateMask will be an error.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withLogSink(body: Schema.LogSink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogSink])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, sinksId :PlayApi, sinkName: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks/${sinksId}").addQueryStringParameters("sinkName" -> sinkName.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSinksResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A filter expression to constrain the sinks returned. Today, this only supports the following strings: '' 'in_scope("ALL")', 'in_scope("ANCESTOR")', 'in_scope("DEFAULT")'.Description of scopes below. ALL: Includes all of the sinks which can be returned in any other scope. ANCESTOR: Includes intercepting sinks owned by ancestor resources. DEFAULT: Includes sinks owned by parent.When the empty string is provided, then the filter 'in_scope("DEFAULT")' is applied. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSinksResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/sinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSinksResponse]] = (fun: list) => fun.apply()
			}
		}
		object exclusions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogExclusion])
			}
			object create {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogExclusion]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogExclusion])
			}
			object get {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.LogExclusion]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLogExclusion(body: Schema.LogExclusion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogExclusion])
			}
			object patch {
				def apply(billingAccountsId :PlayApi, exclusionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions/${exclusionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExclusionsResponse]) {
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExclusionsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/exclusions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListExclusionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object logs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(billingAccountsId :PlayApi, logsId :PlayApi, logName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
				/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
				def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/logs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(billingAccountsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(billingAccountsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object recentQueries {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRecentQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") of the recent queries to list. The only valid value for this field is one of the two allowable type function calls, which are the following: type("Logging") type("OpsAnalytics") */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRecentQueriesResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/recentQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRecentQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object buckets {
				class createAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createAsync {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): createAsync = new createAsync(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets:createAsync").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteBucketRequest(body: Schema.UndeleteBucketRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object undelete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class updateAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateAsync {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAsync = new updateAsync(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}:updateAsync").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogBucket])
				}
				object create {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String, bucketId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "bucketId" -> bucketId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogBucket]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogBucket])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.LogBucket]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLogBucket(body: Schema.LogBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogBucket])
				}
				object patch {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
				}
				object links {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinksResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinksResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListLinksResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Link]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Link])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Link]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLink(body: Schema.Link) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links").addQueryStringParameters("parent" -> parent.toString, "linkId" -> linkId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, linksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/links/${linksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object views {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LogView])
					}
					object create {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String, viewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString, "viewId" -> viewId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LogView]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LogView])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.LogView]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListViewsResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListViewsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListViewsResponse]] = (fun: list) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask that specifies the fields in view that need an update. A field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=filter<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withLogView(body: Schema.LogView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LogView])
					}
					object patch {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					}
					object logs {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
							/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
							def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
							/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
						}
						object list {
							def apply(billingAccountsId :PlayApi, locationsId :PlayApi, bucketsId :PlayApi, viewsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/buckets/${bucketsId}/views/${viewsId}/logs").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object savedQueries {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to use for the saved query, which will become the final component of the saved query's resource name.If the saved_query_id is not provided, the system will generate an alphanumeric ID.The saved_query_id is limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
					def withSavedQueryId(savedQueryId: String) = new create(req.addQueryStringParameters("savedQueryId" -> savedQueryId.toString))
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
				}
				object create {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
				}
				object get {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
				}
				object patch {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
					/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of results to return from this request.Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Specifies the type ("Logging" or "OpsAnalytics") and the visibility (PRIVATE or SHARED) of the saved queries to list. If provided, the filter must contain either the type function or a visibility token, or both. If both are chosen, they can be placed in any order, but they must be joined by the AND operator or the empty character.The two supported type function calls are: type("Logging") type("OpsAnalytics")The two supported visibility tokens are: visibility = PRIVATE visibility = SHAREDFor example:type("Logging") AND visibility = PRIVATE visibility=SHARED type("OpsAnalytics") type("OpsAnalytics)" visibility = PRIVATE visibility = SHARED */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
				}
				object list {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/billingAccounts/${billingAccountsId}/locations/${locationsId}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object entries {
		class copy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCopyLogEntriesRequest(body: Schema.CopyLogEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object copy {
			def apply()(using auth: AuthToken, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v2/entries:copy").addQueryStringParameters())
		}
		class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withWriteLogEntriesRequest(body: Schema.WriteLogEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteLogEntriesResponse])
		}
		object write {
			def apply()(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v2/entries:write").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withListLogEntriesRequest(body: Schema.ListLogEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListLogEntriesResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/entries:list").addQueryStringParameters())
		}
		class tail(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTailLogEntriesRequest(body: Schema.TailLogEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TailLogEntriesResponse])
		}
		object tail {
			def apply()(using auth: AuthToken, ec: ExecutionContext): tail = new tail(ws.url(BASE_URL + s"v2/entries:tail").addQueryStringParameters())
		}
	}
	object logs {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, logsId :PlayApi, logName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/logs/${logsId}").addQueryStringParameters("logName" -> logName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLogsResponse]) {
			/** Optional. List of resource names to list logs for: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]To support legacy queries, it could also be: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]The resource name in the parent field is added to this list. */
			def withResourceNames(resourceNames: String) = new list(req.addQueryStringParameters("resourceNames" -> resourceNames.toString))
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLogsResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/logs").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListLogsResponse]] = (fun: list) => fun.apply()
		}
	}
	object monitoredResourceDescriptors {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMonitoredResourceDescriptorsResponse]) {
			/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of nextPageToken in the response indicates that more results might be available.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. pageToken must be the value of nextPageToken from the previous response. The values of other method parameters should be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMonitoredResourceDescriptorsResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/monitoredResourceDescriptors").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListMonitoredResourceDescriptorsResponse]] = (fun: list) => fun.apply()
		}
	}
}
