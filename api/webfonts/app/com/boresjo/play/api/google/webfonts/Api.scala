package com.boresjo.play.api.google.webfonts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://webfonts.googleapis.com/"

	object webfonts {
		/** Retrieves the list of fonts currently served by the Google Fonts Developer API. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebfontList]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebfontList])
		}
		object list {
			def apply(sort: String, capability: String, family: String, subset: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/webfonts").addQueryStringParameters("sort" -> sort.toString, "capability" -> capability.toString, "family" -> family.toString, "subset" -> subset.toString))
			given Conversion[list, Future[Schema.WebfontList]] = (fun: list) => fun.apply()
		}
	}
}
