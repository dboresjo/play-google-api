package com.boresjo.play.api.google.texttospeech

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://texttospeech.googleapis.com/"

	object voices {
		/** Returns a list of Voice supported for synthesis. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVoicesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Recommended. [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. If not specified, the API will return all supported voices. If specified, the ListVoices call will only return voices that can be used to synthesize this language_code. For example, if you specify `"en-NZ"`, all `"en-NZ"` voices will be returned. If you specify `"no"`, both `"no-\&#42;"` (Norwegian) and `"nb-\&#42;"` (Norwegian Bokmal) voices will be returned. */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVoicesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/voices").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListVoicesResponse]] = (fun: list) => fun.apply()
		}
	}
	object text {
		/** Synthesizes speech synchronously: receive results after all text input has been processed. */
		class synthesize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSynthesizeSpeechRequest(body: Schema.SynthesizeSpeechRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SynthesizeSpeechResponse])
		}
		object synthesize {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): synthesize = new synthesize(ws.url(BASE_URL + s"v1/text:synthesize").addQueryStringParameters())
		}
	}
	object projects {
		object locations {
			/** Synthesizes long form text asynchronously. */
			class synthesizeLongAudio(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSynthesizeLongAudioRequest(body: Schema.SynthesizeLongAudioRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object synthesizeLongAudio {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): synthesizeLongAudio = new synthesizeLongAudio(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:synthesizeLongAudio").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
		}
	}
	object operations {
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
}
