package com.boresjo.play.api.google.safebrowsing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://safebrowsing.googleapis.com/"

	object hashes {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse])
		}
		object search {
			def apply(hashPrefixes: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v5/hashes:search").addQueryStringParameters("hashPrefixes" -> hashPrefixes.toString))
			given Conversion[search, Future[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse]] = (fun: search) => fun.apply()
		}
	}
}
