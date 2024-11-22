package com.boresjo.play.api.google.kgsearch

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

	private val BASE_URL = "https://kgsearch.googleapis.com/"

	object entities {
		/** Searches Knowledge Graph for entities that match the constraints. A list of matched entities will be returned in response, which will be in JSON-LD format and compatible with http://schema.org */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(indent: Boolean, types: String, query: String, limit: Int, ids: String, prefix: Boolean, languages: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/entities:search").addQueryStringParameters("indent" -> indent.toString, "types" -> types.toString, "query" -> query.toString, "limit" -> limit.toString, "ids" -> ids.toString, "prefix" -> prefix.toString, "languages" -> languages.toString))
			given Conversion[search, Future[Schema.SearchResponse]] = (fun: search) => fun.apply()
		}
	}
}
