package com.boresjo.play.api.google.policytroubleshooter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://policytroubleshooter.googleapis.com/"

	object iam {
		class troubleshoot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest(body: Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse])
		}
		object troubleshoot {
			def apply()(using auth: AuthToken, ec: ExecutionContext): troubleshoot = new troubleshoot(auth(ws.url(BASE_URL + s"v1/iam:troubleshoot")).addQueryStringParameters())
		}
	}
}
