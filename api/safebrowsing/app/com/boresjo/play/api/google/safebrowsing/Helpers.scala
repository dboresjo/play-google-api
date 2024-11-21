package com.boresjo.play.api.google.safebrowsing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleSecuritySafebrowsingV5FullHashFullHashDetailThreatTypeEnum: Conversion[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum, Option[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum]] = (fun: Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum) => Option(fun)
		given putListSchemaGoogleSecuritySafebrowsingV5FullHashFullHashDetailAttributesEnum: Conversion[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum], Option[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum]]] = (fun: List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleSecuritySafebrowsingV5FullHash: Conversion[List[Schema.GoogleSecuritySafebrowsingV5FullHash], Option[List[Schema.GoogleSecuritySafebrowsingV5FullHash]]] = (fun: List[Schema.GoogleSecuritySafebrowsingV5FullHash]) => Option(fun)
		given putListSchemaGoogleSecuritySafebrowsingV5FullHashFullHashDetail: Conversion[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail], Option[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail]]] = (fun: List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
