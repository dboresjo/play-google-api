package com.boresjo.play.api.google.kgsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SearchResponse(
	  /** The item list of search results. */
		itemListElement: Option[List[JsValue]] = None,
	  /** The schema type of top-level JSON-LD object, e.g. ItemList. */
		`@type`: Option[JsValue] = None,
	  /** The local context applicable for the response. See more details at http://www.w3.org/TR/json-ld/#context-definitions. */
		`@context`: Option[JsValue] = None
	)
}
