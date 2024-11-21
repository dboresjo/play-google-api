package com.boresjo.play.api.google.fcm

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://fcm.googleapis.com/"

	object projects {
		object messages {
			class send(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSendMessageRequest(body: Schema.SendMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Message])
			}
			object send {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): send = new send(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/messages:send")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
