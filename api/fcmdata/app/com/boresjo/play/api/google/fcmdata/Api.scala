package com.boresjo.play.api.google.fcmdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://fcmdata.googleapis.com/"

	object projects {
		object androidApps {
			object deliveryData {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse])
				}
				object list {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/deliveryData")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
