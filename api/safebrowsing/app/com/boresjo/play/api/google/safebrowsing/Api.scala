package com.boresjo.play.api.google.safebrowsing

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

	private val BASE_URL = "https://safebrowsing.googleapis.com/"

	object hashes {
		/** Search for full hashes matching the specified prefixes. This is a custom method as defined by https://google.aip.dev/136 (the custom method refers to this method having a custom name within Google's general API development nomenclature; it does not refer to using a custom HTTP method). */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse])
		}
		object search {
			def apply(hashPrefixes: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v5/hashes:search").addQueryStringParameters("hashPrefixes" -> hashPrefixes.toString))
			given Conversion[search, Future[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse]] = (fun: search) => fun.apply()
		}
	}
}
