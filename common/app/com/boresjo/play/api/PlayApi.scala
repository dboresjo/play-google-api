package com.boresjo.play.api

/**
 * Common trait for Play API classess.
 */
trait PlayApi {
  /**
   * Type alias for a path parameter, which can be either a String or a Numeric type.
   */
  type PathParam = String | Numeric[?]

  /**
   * A sequence of OAuth2 scopes associated with the API.
   */
  val scopes: Seq[String]
}