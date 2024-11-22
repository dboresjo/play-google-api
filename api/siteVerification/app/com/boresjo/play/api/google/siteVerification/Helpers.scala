package com.boresjo.play.api.google.siteVerification

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaSiteVerificationWebResourceGettokenRequestSiteItem: Conversion[Schema.SiteVerificationWebResourceGettokenRequest.SiteItem, Option[Schema.SiteVerificationWebResourceGettokenRequest.SiteItem]] = (fun: Schema.SiteVerificationWebResourceGettokenRequest.SiteItem) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaSiteVerificationWebResourceResource: Conversion[List[Schema.SiteVerificationWebResourceResource], Option[List[Schema.SiteVerificationWebResourceResource]]] = (fun: List[Schema.SiteVerificationWebResourceResource]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaSiteVerificationWebResourceResourceSiteItem: Conversion[Schema.SiteVerificationWebResourceResource.SiteItem, Option[Schema.SiteVerificationWebResourceResource.SiteItem]] = (fun: Schema.SiteVerificationWebResourceResource.SiteItem) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
