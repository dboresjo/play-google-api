package com.boresjo.play.api.google.storage

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* View and manage your data across Google Cloud Platform services */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud Platform services */,
		"""https://www.googleapis.com/auth/devstorage.full_control""" /* Manage your data and permissions in Google Cloud Storage */,
		"""https://www.googleapis.com/auth/devstorage.read_only""" /* View your data in Google Cloud Storage */,
		"""https://www.googleapis.com/auth/devstorage.read_write""" /* Manage your data in Google Cloud Storage */
	)

	private val BASE_URL = "https://storage.googleapis.com/storage/v1/"

	object anywhereCaches {
		/** Resumes a paused or disabled Anywhere Cache instance. */
		class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AnywhereCache])
		}
		object resume {
			def apply(bucket: String, anywhereCacheId: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/resume").addQueryStringParameters())
			given Conversion[resume, Future[Schema.AnywhereCache]] = (fun: resume) => fun.apply()
		}
		/** Creates an Anywhere Cache instance. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withAnywhereCache(body: Schema.AnywhereCache) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object insert {
			def apply(bucket: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches").addQueryStringParameters())
		}
		/** Pauses an Anywhere Cache instance. */
		class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AnywhereCache])
		}
		object pause {
			def apply(bucket: String, anywhereCacheId: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/pause").addQueryStringParameters())
			given Conversion[pause, Future[Schema.AnywhereCache]] = (fun: pause) => fun.apply()
		}
		/** Disables an Anywhere Cache instance. */
		class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AnywhereCache])
		}
		object disable {
			def apply(bucket: String, anywhereCacheId: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}/disable").addQueryStringParameters())
			given Conversion[disable, Future[Schema.AnywhereCache]] = (fun: disable) => fun.apply()
		}
		/** Returns the metadata of an Anywhere Cache instance. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCache]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnywhereCache])
		}
		object get {
			def apply(bucket: String, anywhereCacheId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AnywhereCache]] = (fun: get) => fun.apply()
		}
		/** Updates the config(ttl and admissionPolicy) of an Anywhere Cache instance. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withAnywhereCache(body: Schema.AnywhereCache) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object update {
			def apply(bucket: String, anywhereCacheId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches/${anywhereCacheId}").addQueryStringParameters())
		}
		/** Returns a list of Anywhere Cache instances of the bucket matching the criteria. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnywhereCaches]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnywhereCaches])
		}
		object list {
			def apply(bucket: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/anywhereCaches").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AnywhereCaches]] = (fun: list) => fun.apply()
		}
	}
	object bucketAccessControls {
		/** Creates a new ACL entry on the specified bucket. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withBucketAccessControl(body: Schema.BucketAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BucketAccessControl])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/acl").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Permanently deletes the ACL entry for the specified entity on the specified bucket. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the ACL entry for the specified entity on the specified bucket. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BucketAccessControl]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BucketAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.BucketAccessControl]] = (fun: get) => fun.apply()
		}
		/** Updates an ACL entry on the specified bucket. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withBucketAccessControl(body: Schema.BucketAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.BucketAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Patches an ACL entry on the specified bucket. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withBucketAccessControl(body: Schema.BucketAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BucketAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"b/${bucket}/acl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Retrieves ACL entries on the specified bucket. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BucketAccessControls]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BucketAccessControls])
		}
		object list {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/acl").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.BucketAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object buckets {
		/** Tests a set of permissions on the given bucket to see which, if any, are held by the caller. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, permissions: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"b/${bucket}/iam/testPermissions").addQueryStringParameters("permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
		/** Creates a new bucket. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withBucket(body: Schema.Bucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Bucket])
		}
		object insert {
			def apply(predefinedAcl: String, predefinedDefaultObjectAcl: String, project: String, projection: String, userProject: String, enableObjectRetention: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b").addQueryStringParameters("predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "project" -> project.toString, "projection" -> projection.toString, "userProject" -> userProject.toString, "enableObjectRetention" -> enableObjectRetention.toString))
		}
		/** Restores a soft-deleted bucket. */
		class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object restore {
			def apply(bucket: String, generation: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"b/${bucket}/restore").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[restore, Future[Unit]] = (fun: restore) => fun.apply()
		}
		/** Updates an IAM policy for the specified bucket. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"b/${bucket}/iam").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Initiates a long-running Relocate Bucket operation on the specified bucket. */
		class relocate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withRelocateBucketRequest(body: Schema.RelocateBucketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object relocate {
			def apply(bucket: String)(using signer: RequestSigner, ec: ExecutionContext): relocate = new relocate(ws.url(BASE_URL + s"b/${bucket}/relocate").addQueryStringParameters())
		}
		/** Returns an IAM policy for the specified bucket. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, optionsRequestedPolicyVersion: Int, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"b/${bucket}/iam").addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		/** Deletes an empty bucket. Deletions are permanent unless soft delete is enabled on the bucket. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the storage layout configuration for the specified bucket. Note that this operation requires storage.objects.list permission. */
		class getStorageLayout(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BucketStorageLayout]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** An optional prefix used for permission check. It is useful when the caller only has storage.objects.list permission under a specific prefix. */
			def withPrefix(prefix: String) = new getStorageLayout(req.addQueryStringParameters("prefix" -> prefix.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BucketStorageLayout])
		}
		object getStorageLayout {
			def apply(bucket: String)(using signer: RequestSigner, ec: ExecutionContext): getStorageLayout = new getStorageLayout(ws.url(BASE_URL + s"b/${bucket}/storageLayout").addQueryStringParameters())
			given Conversion[getStorageLayout, Future[Schema.BucketStorageLayout]] = (fun: getStorageLayout) => fun.apply()
		}
		/** Returns metadata for the specified bucket. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Bucket])
		}
		object get {
			def apply(bucket: String, generation: String, softDeleted: Boolean, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}").addQueryStringParameters("generation" -> generation.toString, "softDeleted" -> softDeleted.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.Bucket]] = (fun: get) => fun.apply()
		}
		/** Updates a bucket. Changes to the bucket will be readable immediately after writing, but configuration changes may take time to propagate. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withBucket(body: Schema.Bucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Bucket])
		}
		object update {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, predefinedAcl: String, predefinedDefaultObjectAcl: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		/** Retrieves a list of buckets for a given project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Buckets]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Buckets])
		}
		object list {
			def apply(maxResults: Int, pageToken: String, prefix: String, softDeleted: Boolean, project: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "softDeleted" -> softDeleted.toString, "project" -> project.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.Buckets]] = (fun: list) => fun.apply()
		}
		/** Patches a bucket. Changes to the bucket will be readable immediately after writing, but configuration changes may take time to propagate. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withBucket(body: Schema.Bucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Bucket])
		}
		object patch {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, predefinedAcl: String, predefinedDefaultObjectAcl: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"b/${bucket}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "predefinedAcl" -> predefinedAcl.toString, "predefinedDefaultObjectAcl" -> predefinedDefaultObjectAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		/** Locks retention policy on a bucket. */
		class lockRetentionPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Bucket])
		}
		object lockRetentionPolicy {
			def apply(bucket: String, ifMetagenerationMatch: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): lockRetentionPolicy = new lockRetentionPolicy(ws.url(BASE_URL + s"b/${bucket}/lockRetentionPolicy").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "userProject" -> userProject.toString))
			given Conversion[lockRetentionPolicy, Future[Schema.Bucket]] = (fun: lockRetentionPolicy) => fun.apply()
		}
	}
	object operations {
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object cancel {
			def apply(bucket: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}/cancel").addQueryStringParameters())
			given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object get {
			def apply(bucket: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		/** Starts asynchronous advancement of the relocate bucket operation in the case of required write downtime, to allow it to lock the bucket at the source location, and proceed with the bucket location swap. The server makes a best effort to advance the relocate bucket operation, but success is not guaranteed. */
		class advanceRelocateBucket(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withAdvanceRelocateBucketOperationRequest(body: Schema.AdvanceRelocateBucketOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object advanceRelocateBucket {
			def apply(bucket: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): advanceRelocateBucket = new advanceRelocateBucket(ws.url(BASE_URL + s"b/${bucket}/operations/${operationId}/advanceRelocateBucket").addQueryStringParameters())
		}
		/** Lists operations that match the specified filter in the request. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(filter: String, bucket: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/operations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object channels {
		/** Stop watching resources through this channel */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"channels/stop").addQueryStringParameters())
		}
	}
	object defaultObjectAccessControls {
		/** Creates a new default object ACL entry on the specified bucket. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Permanently deletes the default object ACL entry for the specified entity on the specified bucket. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the default object ACL entry for the specified entity on the specified bucket. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControl]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.ObjectAccessControl]] = (fun: get) => fun.apply()
		}
		/** Updates a default object ACL entry on the specified bucket. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Patches a default object ACL entry on the specified bucket. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl/${entity}").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Retrieves default object ACL entries on the specified bucket. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControls]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObjectAccessControls])
		}
		object list {
			def apply(bucket: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/defaultObjectAcl").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.ObjectAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object folders {
		/** Creates a new folder. Only applicable to buckets with hierarchical namespace enabled. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Folder])
		}
		object insert {
			def apply(bucket: String, recursive: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/folders").addQueryStringParameters("recursive" -> recursive.toString))
		}
		/** Renames a source folder to a destination folder. Only applicable to buckets with hierarchical namespace enabled. */
		class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object rename {
			def apply(bucket: String, destinationFolder: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, sourceFolder: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"b/${bucket}/folders/${sourceFolder}/renameTo/folders/${destinationFolder}").addQueryStringParameters("ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString))
			given Conversion[rename, Future[Schema.GoogleLongrunningOperation]] = (fun: rename) => fun.apply()
		}
		/** Permanently deletes a folder. Only applicable to buckets with hierarchical namespace enabled. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, folder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/folders/${folder}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns metadata for the specified folder. Only applicable to buckets with hierarchical namespace enabled. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Folder])
		}
		object get {
			def apply(bucket: String, folder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/folders/${folder}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
		}
		/** Retrieves a list of folders matching the criteria. Only applicable to buckets with hierarchical namespace enabled. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Folders]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Folders])
		}
		object list {
			def apply(bucket: String, delimiter: String, endOffset: String, pageSize: Int, pageToken: String, prefix: String, startOffset: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/folders").addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "startOffset" -> startOffset.toString))
			given Conversion[list, Future[Schema.Folders]] = (fun: list) => fun.apply()
		}
	}
	object managedFolders {
		/** Tests a set of permissions on the given managed folder to see which, if any, are held by the caller. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, managedFolder: String, permissions: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam/testPermissions").addQueryStringParameters("permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
		/** Creates a new managed folder. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withManagedFolder(body: Schema.ManagedFolder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ManagedFolder])
		}
		object insert {
			def apply(bucket: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/managedFolders").addQueryStringParameters())
		}
		/** Updates an IAM policy for the specified managed folder. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, managedFolder: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Returns an IAM policy for the specified managed folder. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, optionsRequestedPolicyVersion: Int, managedFolder: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}/iam").addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		/** Permanently deletes a managed folder. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, managedFolder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, allowNonEmpty: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "allowNonEmpty" -> allowNonEmpty.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns metadata of the specified managed folder. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedFolder]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedFolder])
		}
		object get {
			def apply(bucket: String, managedFolder: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/managedFolders/${managedFolder}").addQueryStringParameters("ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString))
			given Conversion[get, Future[Schema.ManagedFolder]] = (fun: get) => fun.apply()
		}
		/** Lists managed folders in the given bucket. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedFolders]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedFolders])
		}
		object list {
			def apply(bucket: String, pageSize: Int, pageToken: String, prefix: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/managedFolders").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString))
			given Conversion[list, Future[Schema.ManagedFolders]] = (fun: list) => fun.apply()
		}
	}
	object notifications {
		/** Permanently deletes a notification subscription. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, notification: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs/${notification}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** View a notification configuration. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Notification]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Notification])
		}
		object get {
			def apply(bucket: String, notification: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs/${notification}").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.Notification]] = (fun: get) => fun.apply()
		}
		/** Creates a notification subscription for a given bucket. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withNotification(body: Schema.Notification) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Notification])
		}
		object insert {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs").addQueryStringParameters("userProject" -> userProject.toString))
		}
		/** Retrieves a list of notification subscriptions for a given bucket. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Notifications]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Notifications])
		}
		object list {
			def apply(bucket: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/notificationConfigs").addQueryStringParameters("userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.Notifications]] = (fun: list) => fun.apply()
		}
	}
	object objectAccessControls {
		/** Creates a new ACL entry on the specified object. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object insert {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		/** Permanently deletes the ACL entry for the specified entity on the specified object. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the ACL entry for the specified entity on the specified object. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControl]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object get {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[get, Future[Schema.ObjectAccessControl]] = (fun: get) => fun.apply()
		}
		/** Updates an ACL entry on the specified object. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object update {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		/** Patches an ACL entry on the specified object. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObjectAccessControl(body: Schema.ObjectAccessControl) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ObjectAccessControl])
		}
		object patch {
			def apply(bucket: String, entity: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl/${entity}").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		/** Retrieves ACL entries on the specified object. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObjectAccessControls]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObjectAccessControls])
		}
		object list {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/acl").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[list, Future[Schema.ObjectAccessControls]] = (fun: list) => fun.apply()
		}
	}
	object objects {
		/** Concatenates a list of existing objects into a new object in the same bucket. */
		class compose(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withComposeRequest(body: Schema.ComposeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Object])
		}
		object compose {
			def apply(destinationBucket: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifMetagenerationMatch: String, kmsKeyName: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): compose = new compose(ws.url(BASE_URL + s"b/${destinationBucket}/o/${destinationObject}/compose").addQueryStringParameters("destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "kmsKeyName" -> kmsKeyName.toString, "userProject" -> userProject.toString))
		}
		/** Stores a new object and metadata. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withObject(body: Schema.Object) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Object])
		}
		object insert {
			def apply(bucket: String, contentEncoding: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, kmsKeyName: String, name: String, predefinedAcl: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"b/${bucket}/o").addQueryStringParameters("contentEncoding" -> contentEncoding.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "kmsKeyName" -> kmsKeyName.toString, "name" -> name.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		/** Restores a soft-deleted object. */
		class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Object]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Restore token used to differentiate sof-deleted objects with the same name and generation. Only applicable for hierarchical namespace buckets. This parameter is optional, and is only required in the rare case when there are multiple soft-deleted objects with the same name and generation. */
			def withRestoreToken(restoreToken: String) = new restore(req.addQueryStringParameters("restoreToken" -> restoreToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Object])
		}
		object restore {
			def apply(bucket: String, generation: String, `object`: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, copySourceAcl: Boolean, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/restore").addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "copySourceAcl" -> copySourceAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
			given Conversion[restore, Future[Schema.Object]] = (fun: restore) => fun.apply()
		}
		/** Watch for changes on all objects in a bucket. */
		class watchAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watchAll {
			def apply(bucket: String, delimiter: String, endOffset: String, includeTrailingDelimiter: Boolean, maxResults: Int, pageToken: String, prefix: String, projection: String, startOffset: String, userProject: String, versions: Boolean)(using signer: RequestSigner, ec: ExecutionContext): watchAll = new watchAll(ws.url(BASE_URL + s"b/${bucket}/o/watch").addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "includeTrailingDelimiter" -> includeTrailingDelimiter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "projection" -> projection.toString, "startOffset" -> startOffset.toString, "userProject" -> userProject.toString, "versions" -> versions.toString))
		}
		/** Updates an IAM policy for the specified object. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
		}
		/** Returns an IAM policy for the specified object. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(bucket: String, generation: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam").addQueryStringParameters("generation" -> generation.toString, "userProject" -> userProject.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		/** Deletes an object and its metadata. Deletions are permanent if versioning is not enabled for the bucket, or if the generation parameter is used. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, `object`: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}").addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "userProject" -> userProject.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Rewrites a source object to a destination object. Optionally overrides metadata. */
		class rewrite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withObject(body: Schema.Object) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RewriteResponse])
		}
		object rewrite {
			def apply(destinationBucket: String, destinationKmsKeyName: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, ifSourceGenerationMatch: String, ifSourceGenerationNotMatch: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, maxBytesRewrittenPerCall: String, projection: String, rewriteToken: String, sourceBucket: String, sourceGeneration: String, sourceObject: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): rewrite = new rewrite(ws.url(BASE_URL + s"b/${sourceBucket}/o/${sourceObject}/rewriteTo/b/${destinationBucket}/o/${destinationObject}").addQueryStringParameters("destinationKmsKeyName" -> destinationKmsKeyName.toString, "destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "ifSourceGenerationMatch" -> ifSourceGenerationMatch.toString, "ifSourceGenerationNotMatch" -> ifSourceGenerationNotMatch.toString, "ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString, "maxBytesRewrittenPerCall" -> maxBytesRewrittenPerCall.toString, "projection" -> projection.toString, "rewriteToken" -> rewriteToken.toString, "sourceGeneration" -> sourceGeneration.toString, "userProject" -> userProject.toString))
		}
		/** Copies a source object to a destination object. Optionally overrides metadata. */
		class copy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withObject(body: Schema.Object) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Object])
		}
		object copy {
			def apply(destinationBucket: String, destinationKmsKeyName: String, destinationObject: String, destinationPredefinedAcl: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, ifSourceGenerationMatch: String, ifSourceGenerationNotMatch: String, ifSourceMetagenerationMatch: String, ifSourceMetagenerationNotMatch: String, projection: String, sourceBucket: String, sourceGeneration: String, sourceObject: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"b/${sourceBucket}/o/${sourceObject}/copyTo/b/${destinationBucket}/o/${destinationObject}").addQueryStringParameters("destinationKmsKeyName" -> destinationKmsKeyName.toString, "destinationPredefinedAcl" -> destinationPredefinedAcl.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "ifSourceGenerationMatch" -> ifSourceGenerationMatch.toString, "ifSourceGenerationNotMatch" -> ifSourceGenerationNotMatch.toString, "ifSourceMetagenerationMatch" -> ifSourceMetagenerationMatch.toString, "ifSourceMetagenerationNotMatch" -> ifSourceMetagenerationNotMatch.toString, "projection" -> projection.toString, "sourceGeneration" -> sourceGeneration.toString, "userProject" -> userProject.toString))
		}
		/** Retrieves an object or its metadata. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Object]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Restore token used to differentiate soft-deleted objects with the same name and generation. Only applicable for hierarchical namespace buckets and if softDeleted is set to true. This parameter is optional, and is only required in the rare case when there are multiple soft-deleted objects with the same name and generation. */
			def withRestoreToken(restoreToken: String) = new get(req.addQueryStringParameters("restoreToken" -> restoreToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Object])
		}
		object get {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, `object`: String, projection: String, userProject: String, softDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}").addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "projection" -> projection.toString, "userProject" -> userProject.toString, "softDeleted" -> softDeleted.toString))
			given Conversion[get, Future[Schema.Object]] = (fun: get) => fun.apply()
		}
		/** Updates an object's metadata. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObject(body: Schema.Object) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Object])
		}
		object update {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, overrideUnlockedRetention: Boolean, `object`: String, predefinedAcl: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}").addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "overrideUnlockedRetention" -> overrideUnlockedRetention.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		/** Patches an object's metadata. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
			/** Perform the request */
			def withObject(body: Schema.Object) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Object])
		}
		object patch {
			def apply(bucket: String, generation: String, ifGenerationMatch: String, ifGenerationNotMatch: String, ifMetagenerationMatch: String, ifMetagenerationNotMatch: String, overrideUnlockedRetention: Boolean, `object`: String, predefinedAcl: String, projection: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}").addQueryStringParameters("generation" -> generation.toString, "ifGenerationMatch" -> ifGenerationMatch.toString, "ifGenerationNotMatch" -> ifGenerationNotMatch.toString, "ifMetagenerationMatch" -> ifMetagenerationMatch.toString, "ifMetagenerationNotMatch" -> ifMetagenerationNotMatch.toString, "overrideUnlockedRetention" -> overrideUnlockedRetention.toString, "predefinedAcl" -> predefinedAcl.toString, "projection" -> projection.toString, "userProject" -> userProject.toString))
		}
		/** Retrieves a list of objects matching the criteria. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Objects]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Objects])
		}
		object list {
			def apply(bucket: String, delimiter: String, endOffset: String, includeTrailingDelimiter: Boolean, maxResults: Int, pageToken: String, prefix: String, projection: String, startOffset: String, userProject: String, versions: Boolean, matchGlob: String, softDeleted: Boolean, includeFoldersAsPrefixes: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"b/${bucket}/o").addQueryStringParameters("delimiter" -> delimiter.toString, "endOffset" -> endOffset.toString, "includeTrailingDelimiter" -> includeTrailingDelimiter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "prefix" -> prefix.toString, "projection" -> projection.toString, "startOffset" -> startOffset.toString, "userProject" -> userProject.toString, "versions" -> versions.toString, "matchGlob" -> matchGlob.toString, "softDeleted" -> softDeleted.toString, "includeFoldersAsPrefixes" -> includeFoldersAsPrefixes.toString))
			given Conversion[list, Future[Schema.Objects]] = (fun: list) => fun.apply()
		}
		/** Initiates a long-running bulk restore operation on the specified bucket. */
		class bulkRestore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withBulkRestoreObjectsRequest(body: Schema.BulkRestoreObjectsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object bulkRestore {
			def apply(bucket: String)(using signer: RequestSigner, ec: ExecutionContext): bulkRestore = new bulkRestore(ws.url(BASE_URL + s"b/${bucket}/o/bulkRestore").addQueryStringParameters())
		}
		/** Tests a set of permissions on the given object to see which, if any, are held by the caller. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestIamPermissionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(bucket: String, generation: String, `object`: String, permissions: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"b/${bucket}/o/${`object`}/iam/testPermissions").addQueryStringParameters("generation" -> generation.toString, "permissions" -> permissions.toString, "userProject" -> userProject.toString))
			given Conversion[testIamPermissions, Future[Schema.TestIamPermissionsResponse]] = (fun: testIamPermissions) => fun.apply()
		}
	}
	object projects {
		object hmacKeys {
			/** Creates a new HMAC key for the specified service account. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HmacKey]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.HmacKey])
			}
			object create {
				def apply(projectId: String, serviceAccountEmail: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys").addQueryStringParameters("serviceAccountEmail" -> serviceAccountEmail.toString, "userProject" -> userProject.toString))
				given Conversion[create, Future[Schema.HmacKey]] = (fun: create) => fun.apply()
			}
			/** Deletes an HMAC key. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accessId: String, projectId: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}").addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Retrieves an HMAC key's metadata */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HmacKeyMetadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HmacKeyMetadata])
			}
			object get {
				def apply(accessId: String, projectId: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}").addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[get, Future[Schema.HmacKeyMetadata]] = (fun: get) => fun.apply()
			}
			/** Updates the state of an HMAC key. See the [HMAC Key resource descriptor](https://cloud.google.com/storage/docs/json_api/v1/projects/hmacKeys/update#request-body) for valid states. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""")
				/** Perform the request */
				def withHmacKeyMetadata(body: Schema.HmacKeyMetadata) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.HmacKeyMetadata])
			}
			object update {
				def apply(accessId: String, projectId: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys/${accessId}").addQueryStringParameters("userProject" -> userProject.toString))
			}
			/** Retrieves a list of HMAC keys matching the criteria. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HmacKeysMetadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HmacKeysMetadata])
			}
			object list {
				def apply(maxResults: Int, pageToken: String, projectId: String, serviceAccountEmail: String, showDeletedKeys: Boolean, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectId}/hmacKeys").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "serviceAccountEmail" -> serviceAccountEmail.toString, "showDeletedKeys" -> showDeletedKeys.toString, "userProject" -> userProject.toString))
				given Conversion[list, Future[Schema.HmacKeysMetadata]] = (fun: list) => fun.apply()
			}
		}
		object serviceAccount {
			/** Get the email address of this project's Google Cloud Storage service account. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccount]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServiceAccount])
			}
			object get {
				def apply(projectId: String, userProject: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectId}/serviceAccount").addQueryStringParameters("userProject" -> userProject.toString))
				given Conversion[get, Future[Schema.ServiceAccount]] = (fun: get) => fun.apply()
			}
		}
	}
}
