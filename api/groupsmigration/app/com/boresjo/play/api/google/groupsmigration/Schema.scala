package com.boresjo.play.api.google.groupsmigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Groups(
	  /** The status of the insert request. */
		responseCode: Option[String] = None,
	  /** The kind of insert resource this is. */
		kind: Option[String] = None
	)
}
