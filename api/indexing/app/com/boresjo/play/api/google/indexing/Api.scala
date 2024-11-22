package com.boresjo.play.api.google.indexing

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
		"""https://www.googleapis.com/auth/indexing""" /* Submit data to Google for indexing */
	)

	private val BASE_URL = "https://indexing.googleapis.com/"

	object urlNotifications {
		/** Notifies that a URL has been updated or deleted. */
		class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/indexing""")
			/** Perform the request */
			def withUrlNotification(body: Schema.UrlNotification) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PublishUrlNotificationResponse])
		}
		object publish {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v3/urlNotifications:publish").addQueryStringParameters())
		}
		/** Gets metadata about a Web Document. This method can _only_ be used to query URLs that were previously seen in successful Indexing API notifications. Includes the latest `UrlNotification` received via this API. */
		class getMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UrlNotificationMetadata]) {
			val scopes = Seq("""https://www.googleapis.com/auth/indexing""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UrlNotificationMetadata])
		}
		object getMetadata {
			def apply(url: String)(using signer: RequestSigner, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v3/urlNotifications/metadata").addQueryStringParameters("url" -> url.toString))
			given Conversion[getMetadata, Future[Schema.UrlNotificationMetadata]] = (fun: getMetadata) => fun.apply()
		}
	}
}
