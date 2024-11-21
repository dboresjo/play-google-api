package com.boresjo.play.api.google.pagespeedonline

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://pagespeedonline.googleapis.com/"

	object pagespeedapi {
		class runpagespeed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PagespeedApiPagespeedResponseV5]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PagespeedApiPagespeedResponseV5])
		}
		object runpagespeed {
			def apply(category: String, locale: String, strategy: String, url: String, utm_campaign: String, utm_source: String, captchaToken: String)(using auth: AuthToken, ec: ExecutionContext): runpagespeed = new runpagespeed(auth(ws.url(BASE_URL + s"pagespeedonline/v5/runPagespeed")).addQueryStringParameters("category" -> category.toString, "locale" -> locale.toString, "strategy" -> strategy.toString, "url" -> url.toString, "utm_campaign" -> utm_campaign.toString, "utm_source" -> utm_source.toString, "captchaToken" -> captchaToken.toString))
			given Conversion[runpagespeed, Future[Schema.PagespeedApiPagespeedResponseV5]] = (fun: runpagespeed) => fun.apply()
		}
	}
}
