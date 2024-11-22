package com.boresjo.play.api.google.fcm

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/firebase.messaging""" /* Send messages and manage messaging subscriptions for your Firebase applications */
	)

	private val BASE_URL = "https://fcm.googleapis.com/"

	object projects {
		object messages {
			/** Send a message to specified target (a registration token, topic or condition). */
			class send(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase.messaging""")
				/** Perform the request */
				def withSendMessageRequest(body: Schema.SendMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object send {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"v1/projects/${projectsId}/messages:send").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
