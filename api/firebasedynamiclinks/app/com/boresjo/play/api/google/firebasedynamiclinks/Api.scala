package com.boresjo.play.api.google.firebasedynamiclinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebasedynamiclinks.googleapis.com/"

	object shortLinks {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateShortDynamicLinkRequest(body: Schema.CreateShortDynamicLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateShortDynamicLinkResponse])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/shortLinks").addQueryStringParameters())
		}
	}
	object v1 {
		class installAttribution(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIosPostInstallAttributionRequest(body: Schema.GetIosPostInstallAttributionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetIosPostInstallAttributionResponse])
		}
		object installAttribution {
			def apply()(using auth: AuthToken, ec: ExecutionContext): installAttribution = new installAttribution(ws.url(BASE_URL + s"v1/installAttribution").addQueryStringParameters())
		}
		class getLinkStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DynamicLinkStats]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DynamicLinkStats])
		}
		object getLinkStats {
			def apply(dynamicLink: String, durationDays: String, sdkVersion: String)(using auth: AuthToken, ec: ExecutionContext): getLinkStats = new getLinkStats(ws.url(BASE_URL + s"v1/${dynamicLink}/linkStats").addQueryStringParameters("durationDays" -> durationDays.toString, "sdkVersion" -> sdkVersion.toString))
			given Conversion[getLinkStats, Future[Schema.DynamicLinkStats]] = (fun: getLinkStats) => fun.apply()
		}
		class reopenAttribution(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIosReopenAttributionRequest(body: Schema.GetIosReopenAttributionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetIosReopenAttributionResponse])
		}
		object reopenAttribution {
			def apply()(using auth: AuthToken, ec: ExecutionContext): reopenAttribution = new reopenAttribution(ws.url(BASE_URL + s"v1/reopenAttribution").addQueryStringParameters())
		}
	}
	object managedShortLinks {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateManagedShortLinkRequest(body: Schema.CreateManagedShortLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateManagedShortLinkResponse])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/managedShortLinks:create").addQueryStringParameters())
		}
	}
}
