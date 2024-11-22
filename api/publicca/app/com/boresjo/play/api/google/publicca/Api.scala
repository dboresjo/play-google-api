package com.boresjo.play.api.google.publicca

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://publicca.googleapis.com/"

	object projects {
		object locations {
			object externalAccountKeys {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExternalAccountKey(body: Schema.ExternalAccountKey) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExternalAccountKey])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/externalAccountKeys").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
