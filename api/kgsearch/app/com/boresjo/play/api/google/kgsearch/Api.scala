package com.boresjo.play.api.google.kgsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://kgsearch.googleapis.com/"

	object entities {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(indent: Boolean, types: String, query: String, limit: Int, ids: String, prefix: Boolean, languages: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/entities:search").addQueryStringParameters("indent" -> indent.toString, "types" -> types.toString, "query" -> query.toString, "limit" -> limit.toString, "ids" -> ids.toString, "prefix" -> prefix.toString, "languages" -> languages.toString))
			given Conversion[search, Future[Schema.SearchResponse]] = (fun: search) => fun.apply()
		}
	}
}
