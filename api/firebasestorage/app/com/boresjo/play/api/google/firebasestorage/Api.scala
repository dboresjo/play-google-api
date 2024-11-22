package com.boresjo.play.api.google.firebasestorage

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
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */
	)

	private val BASE_URL = "https://firebasestorage.googleapis.com/"

	object projects {
		/** Gets the default bucket. */
		class getDefaultBucket(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DefaultBucket]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DefaultBucket])
		}
		object getDefaultBucket {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDefaultBucket = new getDefaultBucket(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("name" -> name.toString))
			given Conversion[getDefaultBucket, Future[Schema.DefaultBucket]] = (fun: getDefaultBucket) => fun.apply()
		}
		/** Unlinks and deletes the default bucket. */
		class deleteDefaultBucket(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteDefaultBucket {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteDefaultBucket = new deleteDefaultBucket(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteDefaultBucket, Future[Schema.Empty]] = (fun: deleteDefaultBucket) => fun.apply()
		}
		object buckets {
			/** Gets a single linked storage bucket. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Bucket])
			}
			object get {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Bucket]] = (fun: get) => fun.apply()
			}
			/** Lists the linked storage buckets for a project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
			}
			/** Links a Google Cloud Storage bucket to a Firebase project. */
			class addFirebase(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withAddFirebaseRequest(body: Schema.AddFirebaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Bucket])
			}
			object addFirebase {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, bucket: String)(using signer: RequestSigner, ec: ExecutionContext): addFirebase = new addFirebase(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}:addFirebase").addQueryStringParameters("bucket" -> bucket.toString))
			}
			/** Unlinks a linked Google Cloud Storage bucket from a Firebase project. */
			class removeFirebase(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRemoveFirebaseRequest(body: Schema.RemoveFirebaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object removeFirebase {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, bucket: String)(using signer: RequestSigner, ec: ExecutionContext): removeFirebase = new removeFirebase(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}:removeFirebase").addQueryStringParameters("bucket" -> bucket.toString))
			}
		}
		object defaultBucket {
			/** Creates a Spark tier-eligible Cloud Storage bucket and links it to your Firebase project. If the default bucket already exists, this method will re-link it to your Firebase project. See https://firebase.google.com/pricing for pricing details. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withDefaultBucket(body: Schema.DefaultBucket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DefaultBucket])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
