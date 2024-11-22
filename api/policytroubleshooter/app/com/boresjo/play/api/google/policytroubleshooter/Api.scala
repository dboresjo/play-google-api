package com.boresjo.play.api.google.policytroubleshooter

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

	private val BASE_URL = "https://policytroubleshooter.googleapis.com/"

	object iam {
		/** Checks whether a principal has a specific permission for a specific resource, and explains why the principal does or does not have that permission. */
		class troubleshoot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest(body: Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse])
		}
		object troubleshoot {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): troubleshoot = new troubleshoot(ws.url(BASE_URL + s"v1/iam:troubleshoot").addQueryStringParameters())
		}
	}
}
