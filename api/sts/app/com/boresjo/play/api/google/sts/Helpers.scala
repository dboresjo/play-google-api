package com.boresjo.play.api.google.sts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleTypeExpr: Conversion[Schema.GoogleTypeExpr, Option[Schema.GoogleTypeExpr]] = (fun: Schema.GoogleTypeExpr) => Option(fun)
		given putSchemaGoogleIdentityStsV1AccessBoundary: Conversion[Schema.GoogleIdentityStsV1AccessBoundary, Option[Schema.GoogleIdentityStsV1AccessBoundary]] = (fun: Schema.GoogleIdentityStsV1AccessBoundary) => Option(fun)
		given putListSchemaGoogleIdentityStsV1AccessBoundaryRule: Conversion[List[Schema.GoogleIdentityStsV1AccessBoundaryRule], Option[List[Schema.GoogleIdentityStsV1AccessBoundaryRule]]] = (fun: List[Schema.GoogleIdentityStsV1AccessBoundaryRule]) => Option(fun)
		given putSchemaGoogleIdentityStsV1betaAccessBoundary: Conversion[Schema.GoogleIdentityStsV1betaAccessBoundary, Option[Schema.GoogleIdentityStsV1betaAccessBoundary]] = (fun: Schema.GoogleIdentityStsV1betaAccessBoundary) => Option(fun)
		given putListSchemaGoogleIdentityStsV1betaAccessBoundaryRule: Conversion[List[Schema.GoogleIdentityStsV1betaAccessBoundaryRule], Option[List[Schema.GoogleIdentityStsV1betaAccessBoundaryRule]]] = (fun: List[Schema.GoogleIdentityStsV1betaAccessBoundaryRule]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
