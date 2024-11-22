package com.boresjo.play.api.google.playcustomapp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Organization(
	  /** Required. ID of the organization. */
		organizationId: Option[String] = None,
	  /** Optional. A human-readable name of the organization, to help recognize the organization. */
		organizationName: Option[String] = None
	)
	
	case class CustomApp(
	  /** Output only. Package name of the created Android app. Only present in the API response. */
		packageName: Option[String] = None,
	  /** Default listing language in BCP 47 format. */
		languageCode: Option[String] = None,
	  /** Organizations to which the custom app should be made available. If the request contains any organizations, then the app will be restricted to only these organizations. To support the organization linked to the developer account, the organization ID should be provided explicitly together with other organizations. If no organizations are provided, then the app is only available to the organization linked to the developer account. */
		organizations: Option[List[Schema.Organization]] = None,
	  /** Title for the Android app. */
		title: Option[String] = None
	)
}
