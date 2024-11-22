package com.boresjo.play.api.google.fcmdata

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

	private val BASE_URL = "https://fcmdata.googleapis.com/"

	object projects {
		object androidApps {
			object deliveryData {
				/** List aggregate delivery data for the given Android application. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse])
				}
				object list {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/deliveryData").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
