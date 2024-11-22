package com.boresjo.play.api.google.driveactivity

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
		"""https://www.googleapis.com/auth/drive.activity""" /* View and add to the activity record of files in your Google Drive */,
		"""https://www.googleapis.com/auth/drive.activity.readonly""" /* View the activity record of files in your Google Drive */
	)

	private val BASE_URL = "https://driveactivity.googleapis.com/"

	object activity {
		/** Query past activity in Google Drive. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.activity""", """https://www.googleapis.com/auth/drive.activity.readonly""")
			/** Perform the request */
			def withQueryDriveActivityRequest(body: Schema.QueryDriveActivityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryDriveActivityResponse])
		}
		object query {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v2/activity:query").addQueryStringParameters())
		}
	}
}
