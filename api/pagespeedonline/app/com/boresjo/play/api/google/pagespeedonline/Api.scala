package com.boresjo.play.api.google.pagespeedonline

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
		"""openid""" /* Associate you with your personal info on Google */
	)

	private val BASE_URL = "https://pagespeedonline.googleapis.com/"

	object pagespeedapi {
		/** Runs PageSpeed analysis on the page at the specified URL, and returns PageSpeed scores, a list of suggestions to make that page faster, and other information. */
		class runpagespeed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PagespeedApiPagespeedResponseV5]) {
			val scopes = Seq("""openid""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PagespeedApiPagespeedResponseV5])
		}
		object runpagespeed {
			def apply(category: String, locale: String, strategy: String, url: String, utm_campaign: String, utm_source: String, captchaToken: String)(using signer: RequestSigner, ec: ExecutionContext): runpagespeed = new runpagespeed(ws.url(BASE_URL + s"pagespeedonline/v5/runPagespeed").addQueryStringParameters("category" -> category.toString, "locale" -> locale.toString, "strategy" -> strategy.toString, "url" -> url.toString, "utm_campaign" -> utm_campaign.toString, "utm_source" -> utm_source.toString, "captchaToken" -> captchaToken.toString))
			given Conversion[runpagespeed, Future[Schema.PagespeedApiPagespeedResponseV5]] = (fun: runpagespeed) => fun.apply()
		}
	}
}
