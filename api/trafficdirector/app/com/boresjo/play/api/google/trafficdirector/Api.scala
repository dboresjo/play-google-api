package com.boresjo.play.api.google.trafficdirector

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

	private val BASE_URL = "https://trafficdirector.googleapis.com/"

	object discovery {
		/**  */
		class client_status(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withClientStatusRequest(body: Schema.ClientStatusRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientStatusResponse])
		}
		object client_status {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): client_status = new client_status(ws.url(BASE_URL + s"v3/discovery:client_status").addQueryStringParameters())
		}
	}
}
