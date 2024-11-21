package com.boresjo.play.api.google.storage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://storage.googleapis.com/storage/v1/"

	object anywhereCaches {
		class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AnywhereCache])
		}
		object resume {
			def apply(bucket: String, anywhereCacheId: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/resume")).addQueryStringParameters())
			given Conversion[resume, Future[Schema.AnywhereCache]] = (fun: resume) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAnywhereCache(body: Schema.AnywhereCache) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object insert {
			def apply(bucket: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches")).addQueryStringParameters())
		}
		class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AnywhereCache])
		}
		object pause {
			def apply(bucket: String, anywhereCacheId: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/pause")).addQueryStringParameters())
			given Conversion[pause, Future[Schema.AnywhereCache]] = (fun: pause) => fun.apply()
		}
		class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AnywhereCache])
		}
		object disable {
			def apply(bucket: String, anywhereCacheId: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/disable")).addQueryStringParameters())
			given Conversion[disable, Future[Schema.AnywhereCache]] = (fun: disable) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AnywhereCache])
		}
		object get {
			def apply(bucket: String, anywhereCacheId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.AnywhereCache]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAnywhereCache(body: Schema.AnywhereCache) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object update {
			def apply(bucket: String, anywhereCacheId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCaches]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AnywhereCaches])
		}
		object list {
			def apply(bucket: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AnywhereCaches]] = (fun: list) => fun.apply()
		}
	}
	object bucketAccessControls {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucketAccessControl(body: Schema.BucketAccessControl) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BucketAccessControl])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/acl")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BucketAccessControl]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.BucketAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.BucketAccessControl]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucketAccessControl(body: Schema.BucketAccessControl) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.BucketAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucketAccessControl(body: Schema.BucketAccessControl) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.BucketAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BucketAccessControls]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.BucketAccessControls])
		}
		object list {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/acl")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.BucketAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object buckets {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, permissions: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"b/${bucket}/iam/testPermissions")).addQueryStringParameters("permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucket(body: Schema.Bucket) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Bucket])
		}
		object insert {
			def apply(predefinedAcl: String, predefinedDefaultObjectAcl: String, project: String, projection: String, userProject: String, enableObjectRetention: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b")).addQueryStringParameters("predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "project" -> project.toString, "projection" -> projection.toString, "userProject" -> userProject.toString, "enableObjectRetention" -> enableObjectRetention.toString))
		}
		class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object restore {
			def apply(bucket: String, generation: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"b/${bucket}/restore")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[restore, Future[Unit]] = (fun: restore) => fun.apply()
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPolicy(body: Schema.Policy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/iam")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class relocate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRelocateBucketRequest(body: Schema.RelocateBucketRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object relocate {
			def apply(bucket: String)(using auth: AuthToken, ec: ExecutionContext): relocate = new relocate(auth(ws.url(BASE_URL + s"b/${bucket}/relocate")).addQueryStringParameters())
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, optionsRequestedPolicyVersion: Int, userProject: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/iam")).addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class getStorageLayout(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BucketStorageLayout]) {
			/** An optional prefix used for permission check. It is useful when the caller only has storage.objects.list permission under a specific prefix. */
			def withPrefix(prefix: String) = new getStorageLayout(req.addQueryStringParameters("prefix" -> prefix.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.BucketStorageLayout])
		}
		object getStorageLayout {
			def apply(bucket: String)(using auth: AuthToken, ec: ExecutionContext): getStorageLayout = new getStorageLayout(auth(ws.url(BASE_URL + s"b/${bucket}/storageLayout")).addQueryStringParameters())
			given Conversion[getStorageLayout, Future[Schema.BucketStorageLayout]] = (fun: getStorageLayout) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Bucket])
		}
		object get {
			def apply(bucket: String, generation: String, softDeleted: Boolean, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}")).addQueryStringParameters("generation" -> generation.toString, "softDeleted" -> softDeleted.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.Bucket]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucket(body: Schema.Bucket) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Bucket])
		}
		object update {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, predefinedAcl: String, predefinedDefaultObjectAcl: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Buckets]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Buckets])
		}
		object list {
			def apply(maxResults: Int, pageToken: String, prefix: String, softDeleted: Boolean, project: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "softDeleted" -> softDeleted.toString, "project" -> project.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.Buckets]] = (fun: list) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBucket(body: Schema.Bucket) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Bucket])
		}
		object patch {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, predefinedAcl: String, predefinedDefaultObjectAcl: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"b/${bucket}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		class lockRetentionPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.Bucket])
		}
		object lockRetentionPolicy {
			def apply(bucket: String, ifMetagenerationMatch: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): lockRetentionPolicy = new lockRetentionPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/lockRetentionPolicy")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "userProject" -> userProject.toString))
			given Conversion[lockRetentionPolicy, Future[Schema.Bucket]] = (fun: lockRetentionPolicy) => fun.apply()
		}
	}
	object operations {
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object cancel {
			def apply(bucket: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}/cancel")).addQueryStringParameters())
			given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object get {
			def apply(bucket: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		class advanceRelocateBucket(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAdvanceRelocateBucketOperationRequest(body: Schema.AdvanceRelocateBucketOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
		}
		object advanceRelocateBucket {
			def apply(bucket: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): advanceRelocateBucket = new advanceRelocateBucket(auth(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}/advanceRelocateBucket")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(filter: String, bucket: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/operations")).addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object channels {
		class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
		}
		object stop {
			def apply()(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"channels/stop")).addQueryStringParameters())
		}
	}
	object defaultObjectAccessControls {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ObjectAccessControl])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControl]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ObjectAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.ObjectAccessControl]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ObjectAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ObjectAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControls]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ObjectAccessControls])
		}
		object list {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.ObjectAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object folders {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFolder(body: Schema.Folder) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Folder])
		}
		object insert {
			def apply(bucket: String, recursive: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/folders")).addQueryStringParameters("recursive" -> recursive.toString))
		}
		class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object rename {
			def apply(bucket: String, destinationFolder: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, sourceFolder: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(auth(ws.url(BASE_URL + s"b/${bucket}/folders/${sourceFolder}/renameTo/folders/${destinationFolder}")).addQueryStringParameters("ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString))
			given Conversion[rename, Future[Schema.GoogleLongrunningOperation]] = (fun: rename) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, folder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/folders/${folder}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Folder])
		}
		object get {
			def apply(bucket: String, folder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/folders/${folder}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Folders]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Folders])
		}
		object list {
			def apply(bucket: String, delimiter: String, endOffset: String, pageSize: Int, pageToken: String, prefix: String, startOffset: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/folders")).addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "startOffset" -> startOffset.toString))
			given Conversion[list, Future[Schema.Folders]] = (fun: list) => fun.apply()
		}
	}
	object managedFolders {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, managedFolder: String, permissions: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam/testPermissions")).addQueryStringParameters("permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withManagedFolder(body: Schema.ManagedFolder) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ManagedFolder])
		}
		object insert {
			def apply(bucket: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders")).addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPolicy(body: Schema.Policy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, managedFolder: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, optionsRequestedPolicyVersion: Int, managedFolder: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam")).addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, managedFolder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, allowNonEmpty: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "allowNonEmpty" -> allowNonEmpty.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedFolder]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedFolder])
		}
		object get {
			def apply(bucket: String, managedFolder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}")).addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[get, Future[Schema.ManagedFolder]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedFolders]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedFolders])
		}
		object list {
			def apply(bucket: String, pageSize: Int, pageToken: String, prefix: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/managedFolders")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString))
			given Conversion[list, Future[Schema.ManagedFolders]] = (fun: list) => fun.apply()
		}
	}
	object notifications {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, notification: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs/${notification}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Notification]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Notification])
		}
		object get {
			def apply(bucket: String, notification: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs/${notification}")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.Notification]] = (fun: get) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withNotification(body: Schema.Notification) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Notification])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs")).addQueryStringParameters("userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Notifications]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Notifications])
		}
		object list {
			def apply(bucket: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs")).addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.Notifications]] = (fun: list) => fun.apply()
		}
	}
	object objectAccessControls {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ObjectAccessControl])
		}
		object insert {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControl]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ObjectAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.ObjectAccessControl]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ObjectAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ObjectAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControls]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ObjectAccessControls])
		}
		object list {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.ObjectAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object objects {
		class compose(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComposeRequest(body: Schema.ComposeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Object])
		}
		object compose {
			def apply(destinationBucket: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifMetagenerationMatch: String, kmsKeyName: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): compose = new compose(auth(ws.url(BASE_URL + s"b/${destinationBucket}/o/${destinationObject}/compose")).addQueryStringParameters("destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "kmsKeyName" -> kmsKeyName.toString, "userProject" -> userProject.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObject(body: Schema.Object) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Object])
		}
		object insert {
			def apply(bucket: String, contentEncoding: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, kmsKeyName: String, name: String, predefinedAcl: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"b/${bucket}/o")).addQueryStringParameters("contentEncoding" -> contentEncoding.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "kmsKeyName" -> kmsKeyName.toString, "name" -> name.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Object]) {
			/** Restore token used to differentiate sof-deleted objects with the same name and generation. Only applicable for hierarchical namespace buckets. This parameter is optional, and is only required in the rare case when there are multiple soft-deleted objects with the same name and generation. */
			def withRestoreToken(restoreToken: String) = new restore(req.addQueryStringParameters("restoreToken" -> restoreToken.toString))
			def apply() = req.execute("POST").map(_.json.as[Schema.Object])
		}
		object restore {
			def apply(bucket: String, generation: String, `object`: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, copySourceAcl: Boolean, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/restore")).addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "copySourceAcl" -> copySourceAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[restore, Future[Schema.Object]] = (fun: restore) => fun.apply()
		}
		class watchAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Channel])
		}
		object watchAll {
			def apply(bucket: String, delimiter: String, endOffset: String, includeTrailingDelimiter: Boolean, maxResults: Int, pageToken: String, prefix: String, projection: String, startOffset: String, userProject: String, versions: Boolean)(using auth: AuthToken, ec: ExecutionContext): watchAll = new watchAll(auth(ws.url(BASE_URL + s"b/${bucket}/o/watch")).addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "includeTrailingDelimiter" -> includeTrailingDelimiter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "projection" -> projection.toString, "startOffset" -> startOffset.toString, "userProject" -> userProject.toString, "versions" -> versions.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPolicy(body: Schema.Policy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam")).addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, `object`: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}")).addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class rewrite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObject(body: Schema.Object) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RewriteResponse])
		}
		object rewrite {
			def apply(destinationBucket: String, destinationKmsKeyName: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, ifSourceGenerationMatch: String, ifSourceGenerationNotMatch: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, maxBytesRewrittenPerCall: String, projection: String, rewriteToken: String, sourceBucket: String, sourceGeneration: String, sourceObject: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): rewrite = new rewrite(auth(ws.url(BASE_URL + s"b/${sourceBucket}/o/${sourceObject}/rewriteTo/b/${destinationBucket}/o/${destinationObject}")).addQueryStringParameters("destinationKmsKeyName" -> destinationKmsKeyName.toString, "destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "ifSourceGenerationMatch" -> ifSourceGenerationMatch.toString, "ifSourceGenerationNotMatch" -> ifSourceGenerationNotMatch.toString, "ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString, "maxBytesRewrittenPerCall" -> maxBytesRewrittenPerCall.toString, "projection" -> projection.toString, "rewriteToken" -> rewriteToken.toString, "sourceGeneration" -> sourceGeneration.toString, "userProject" -> userProject.toString))
		}
		class copy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObject(body: Schema.Object) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Object])
		}
		object copy {
			def apply(destinationBucket: String, destinationKmsKeyName: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, ifSourceGenerationMatch: String, ifSourceGenerationNotMatch: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, projection: String, sourceBucket: String, sourceGeneration: String, sourceObject: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): copy = new copy(auth(ws.url(BASE_URL + s"b/${sourceBucket}/o/${sourceObject}/copyTo/b/${destinationBucket}/o/${destinationObject}")).addQueryStringParameters("destinationKmsKeyName" -> destinationKmsKeyName.toString, "destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "ifSourceGenerationMatch" -> ifSourceGenerationMatch.toString, "ifSourceGenerationNotMatch" -> ifSourceGenerationNotMatch.toString, "ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString, "projection" -> projection.toString, "sourceGeneration" -> sourceGeneration.toString, "userProject" -> userProject.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Object]) {
			/** Restore token used to differentiate soft-deleted objects with the same name and generation. Only applicable for hierarchical namespace buckets and if softDeleted is set to true. This parameter is optional, and is only required in the rare case when there are multiple soft-deleted objects with the same name and generation. */
			def withRestoreToken(restoreToken: String) = new get(req.addQueryStringParameters("restoreToken" -> restoreToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.Object])
		}
		object get {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, `object`: String, projection: String, userProject: String, softDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}")).addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "projection" -> projection.toString, "userProject" -> userProject.toString, "softDeleted" -> softDeleted.toString))
			given Conversion[get, Future[Schema.Object]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObject(body: Schema.Object) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Object])
		}
		object update {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, overrideUnlockedRetention: Boolean, `object`: String, predefinedAcl: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}")).addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "overrideUnlockedRetention" -> overrideUnlockedRetention.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withObject(body: Schema.Object) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Object])
		}
		object patch {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, overrideUnlockedRetention: Boolean, `object`: String, predefinedAcl: String, projection: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}")).addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "overrideUnlockedRetention" -> overrideUnlockedRetention.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Objects]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Objects])
		}
		object list {
			def apply(bucket: String, delimiter: String, endOffset: String, includeTrailingDelimiter: Boolean, maxResults: Int, pageToken: String, prefix: String, projection: String, startOffset: String, userProject: String, versions: Boolean, matchGlob: String, softDeleted: Boolean, includeFoldersAsPrefixes: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"b/${bucket}/o")).addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "includeTrailingDelimiter" -> includeTrailingDelimiter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "projection" -> projection.toString, "startOffset" -> startOffset.toString, "userProject" -> userProject.toString, "versions" -> versions.toString, "matchGlob" -> matchGlob.toString, "softDeleted" -> softDeleted.toString, "includeFoldersAsPrefixes" -> includeFoldersAsPrefixes.toString))
			given Conversion[list, Future[Schema.Objects]] = (fun: list) => fun.apply()
		}
		class bulkRestore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBulkRestoreObjectsRequest(body: Schema.BulkRestoreObjectsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object bulkRestore {
			def apply(bucket: String)(using auth: AuthToken, ec: ExecutionContext): bulkRestore = new bulkRestore(auth(ws.url(BASE_URL + s"b/${bucket}/o/bulkRestore")).addQueryStringParameters())
		}
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, generation: String, `object`: String, permissions: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam/testPermissions")).addQueryStringParameters("generation" -> generation.toString, "permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
	}
	object projects {
		object hmacKeys {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HmacKey]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.HmacKey])
			}
			object create {
				def apply(projectId: String, serviceAccountEmail: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys")).addQueryStringParameters("serviceAccountEmail" -> serviceAccountEmail.toString, "userProject" -> userProject.toString))
				given Conversion[create, Future[Schema.HmacKey]] = (fun: create) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accessId: String, projectId: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}")).addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HmacKeyMetadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.HmacKeyMetadata])
			}
			object get {
				def apply(accessId: String, projectId: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}")).addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[get, Future[Schema.HmacKeyMetadata]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withHmacKeyMetadata(body: Schema.HmacKeyMetadata) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.HmacKeyMetadata])
			}
			object update {
				def apply(accessId: String, projectId: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}")).addQueryStringParameters("userProject" -> userProject.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HmacKeysMetadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.HmacKeysMetadata])
			}
			object list {
				def apply(maxResults: Int, pageToken: String, projectId: String, serviceAccountEmail: String, showDeletedKeys: Boolean, userProject: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "serviceAccountEmail" -> serviceAccountEmail.toString, "showDeletedKeys" -> showDeletedKeys.toString, "userProject" -> userProject.toString))
				given Conversion[list, Future[Schema.HmacKeysMetadata]] = (fun: list) => fun.apply()
			}
		}
		object serviceAccount {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccount]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ServiceAccount])
			}
			object get {
				def apply(projectId: String, userProject: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"projects/${projectId}/serviceAccount")).addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[get, Future[Schema.ServiceAccount]] = (fun: get) => fun.apply()
			}
		}
	}
}
