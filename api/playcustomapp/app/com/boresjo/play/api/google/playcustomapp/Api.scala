package com.boresjo.play.api.google.playcustomapp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://playcustomapp.googleapis.com/"

	object accounts {
		object customApps {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomApp(body: Schema.CustomApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CustomApp])
			}
			object create {
				def apply(account: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"playcustomapp/v1/accounts/${account}/customApps")).addQueryStringParameters())
			}
		}
	}
}
