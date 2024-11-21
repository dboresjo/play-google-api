package com.boresjo.play.api.google.digitalassetlinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaCheckResponseErrorCodeEnum: Conversion[List[Schema.CheckResponse.ErrorCodeEnum], Option[List[Schema.CheckResponse.ErrorCodeEnum]]] = (fun: List[Schema.CheckResponse.ErrorCodeEnum]) => Option(fun)
		given putListSchemaStatement: Conversion[List[Schema.Statement], Option[List[Schema.Statement]]] = (fun: List[Schema.Statement]) => Option(fun)
		given putListSchemaListResponseErrorCodeEnum: Conversion[List[Schema.ListResponse.ErrorCodeEnum], Option[List[Schema.ListResponse.ErrorCodeEnum]]] = (fun: List[Schema.ListResponse.ErrorCodeEnum]) => Option(fun)
		given putSchemaAsset: Conversion[Schema.Asset, Option[Schema.Asset]] = (fun: Schema.Asset) => Option(fun)
		given putSchemaWebAsset: Conversion[Schema.WebAsset, Option[Schema.WebAsset]] = (fun: Schema.WebAsset) => Option(fun)
		given putSchemaAndroidAppAsset: Conversion[Schema.AndroidAppAsset, Option[Schema.AndroidAppAsset]] = (fun: Schema.AndroidAppAsset) => Option(fun)
		given putSchemaCertificateInfo: Conversion[Schema.CertificateInfo, Option[Schema.CertificateInfo]] = (fun: Schema.CertificateInfo) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
