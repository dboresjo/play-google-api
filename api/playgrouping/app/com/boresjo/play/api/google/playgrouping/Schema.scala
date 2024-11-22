package com.boresjo.play.api.google.playgrouping

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Tag(
	  /** A boolean value of the tag. */
		booleanValue: Option[Boolean] = None,
	  /** Required. Key for the tag. */
		key: Option[String] = None,
	  /** A time value of the tag. */
		timeValue: Option[String] = None,
	  /** A string value of the tag. */
		stringValue: Option[String] = None,
	  /** A signed 64-bit integer value of the tag. */
		int64Value: Option[String] = None
	)
	
	case class VerifyTokenRequest(
	  /** Required. Persona represented by the token. Format: personas/{persona} */
		persona: Option[String] = None
	)
	
	case class VerifyTokenResponse(
	
	)
	
	case class CreateOrUpdateTagsResponse(
	  /** All requested tags are returned, including pre-existing ones. */
		tags: Option[List[Schema.Tag]] = None
	)
	
	case class CreateOrUpdateTagsRequest(
	  /** Tags to be inserted or updated. */
		tags: Option[List[Schema.Tag]] = None
	)
}
