package com.boresjo.play.api.google.playcustomapp

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
		"""https://www.googleapis.com/auth/androidpublisher""" /* View and manage your Google Play Developer account */
	)

	private val BASE_URL = "https://playcustomapp.googleapis.com/"

	object accounts {
		object customApps {
			/** Creates a new custom app. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withCustomApp(body: Schema.CustomApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomApp])
			}
			object create {
				def apply(account: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"playcustomapp/v1/accounts/${account}/customApps").addQueryStringParameters())
			}
		}
	}
}
