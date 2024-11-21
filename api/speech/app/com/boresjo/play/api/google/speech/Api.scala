package com.boresjo.play.api.google.speech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://speech.googleapis.com/"

	object projects {
		object locations {
			object phraseSets {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreatePhraseSetRequest(body: Schema.CreatePhraseSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PhraseSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseSets")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseSets/${phraseSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PhraseSet]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.PhraseSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseSets/${phraseSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PhraseSet]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPhraseSet(body: Schema.PhraseSet) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.PhraseSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseSetsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseSets/${phraseSetsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPhraseSetResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListPhraseSetResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseSets")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListPhraseSetResponse]] = (fun: list) => fun.apply()
				}
			}
			object customClasses {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateCustomClassRequest(body: Schema.CreateCustomClassRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CustomClass])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/customClasses")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, customClassesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/customClasses/${customClassesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomClass]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.CustomClass])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, customClassesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/customClasses/${customClassesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CustomClass]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCustomClass(body: Schema.CustomClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.CustomClass])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, customClassesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/customClasses/${customClassesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomClassesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListCustomClassesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/customClasses")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCustomClassesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object speech {
		class recognize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRecognizeRequest(body: Schema.RecognizeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RecognizeResponse])
		}
		object recognize {
			def apply()(using auth: AuthToken, ec: ExecutionContext): recognize = new recognize(auth(ws.url(BASE_URL + s"v1/speech:recognize")).addQueryStringParameters())
		}
		class longrunningrecognize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLongRunningRecognizeRequest(body: Schema.LongRunningRecognizeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object longrunningrecognize {
			def apply()(using auth: AuthToken, ec: ExecutionContext): longrunningrecognize = new longrunningrecognize(auth(ws.url(BASE_URL + s"v1/speech:longrunningrecognize")).addQueryStringParameters())
		}
	}
}
