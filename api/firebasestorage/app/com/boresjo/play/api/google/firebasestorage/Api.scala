package com.boresjo.play.api.google.firebasestorage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebasestorage.googleapis.com/"

	object projects {
		class getDefaultBucket(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DefaultBucket]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DefaultBucket])
		}
		object getDefaultBucket {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDefaultBucket = new getDefaultBucket(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("name" -> name.toString))
			given Conversion[getDefaultBucket, Future[Schema.DefaultBucket]] = (fun: getDefaultBucket) => fun.apply()
		}
		class deleteDefaultBucket(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteDefaultBucket {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteDefaultBucket = new deleteDefaultBucket(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteDefaultBucket, Future[Schema.Empty]] = (fun: deleteDefaultBucket) => fun.apply()
		}
		object buckets {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bucket]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bucket])
			}
			object get {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Bucket]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBucketsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBucketsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBucketsResponse]] = (fun: list) => fun.apply()
			}
			class addFirebase(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddFirebaseRequest(body: Schema.AddFirebaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Bucket])
			}
			object addFirebase {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, bucket: String)(using auth: AuthToken, ec: ExecutionContext): addFirebase = new addFirebase(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}:addFirebase").addQueryStringParameters("bucket" -> bucket.toString))
			}
			class removeFirebase(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveFirebaseRequest(body: Schema.RemoveFirebaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object removeFirebase {
				def apply(projectsId :PlayApi, bucketsId :PlayApi, bucket: String)(using auth: AuthToken, ec: ExecutionContext): removeFirebase = new removeFirebase(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/buckets/${bucketsId}:removeFirebase").addQueryStringParameters("bucket" -> bucket.toString))
			}
		}
		object defaultBucket {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDefaultBucket(body: Schema.DefaultBucket) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DefaultBucket])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/defaultBucket").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
