package com.boresjo.play.api.google.oauth2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Tokeninfo(
	  /** Who is the intended audience for this token. In general the same as issued_to. */
		audience: Option[String] = None,
	  /** The email address of the user. Present only if the email scope is present in the request. */
		email: Option[String] = None,
	  /** The expiry time of the token, as number of seconds left until expiry. */
		expires_in: Option[Int] = None,
	  /** To whom was the token issued to. In general the same as audience. */
		issued_to: Option[String] = None,
	  /** The space separated list of scopes granted to this token. */
		scope: Option[String] = None,
	  /** The obfuscated user id. */
		user_id: Option[String] = None,
	  /** Boolean flag which is true if the email address is verified. Present only if the email scope is present in the request. */
		verified_email: Option[Boolean] = None
	)
	
	case class Userinfo(
	  /** The user's email address. */
		email: Option[String] = None,
	  /** The user's last name. */
		family_name: Option[String] = None,
	  /** The user's gender. */
		gender: Option[String] = None,
	  /** The user's first name. */
		given_name: Option[String] = None,
	  /** The hosted domain e.g. example.com if the user is Google apps user. */
		hd: Option[String] = None,
	  /** The obfuscated ID of the user. */
		id: Option[String] = None,
	  /** URL of the profile page. */
		link: Option[String] = None,
	  /** The user's preferred locale. */
		locale: Option[String] = None,
	  /** The user's full name. */
		name: Option[String] = None,
	  /** URL of the user's picture image. */
		picture: Option[String] = None,
	  /** Boolean flag which is true if the email address is verified. Always verified because we only return the user's primary email address. */
		verified_email: Option[Boolean] = Some(true)
	)
}
