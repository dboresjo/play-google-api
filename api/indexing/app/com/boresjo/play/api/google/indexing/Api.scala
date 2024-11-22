package com.boresjo.play.api.google.indexing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://indexing.googleapis.com/"

	object urlNotifications {
		class publish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUrlNotification(body: Schema.UrlNotification) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PublishUrlNotificationResponse])
		}
		object publish {
			def apply()(using auth: AuthToken, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v3/urlNotifications:publish").addQueryStringParameters())
		}
		class getMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UrlNotificationMetadata]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UrlNotificationMetadata])
		}
		object getMetadata {
			def apply(url: String)(using auth: AuthToken, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v3/urlNotifications/metadata").addQueryStringParameters("url" -> url.toString))
			given Conversion[getMetadata, Future[Schema.UrlNotificationMetadata]] = (fun: getMetadata) => fun.apply()
		}
	}
}
