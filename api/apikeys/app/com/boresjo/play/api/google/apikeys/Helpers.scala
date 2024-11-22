package com.boresjo.play.api.google.apikeys

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaV2ServerKeyRestrictions: Conversion[Schema.V2ServerKeyRestrictions, Option[Schema.V2ServerKeyRestrictions]] = (fun: Schema.V2ServerKeyRestrictions) => Option(fun)
		given putSchemaV2BrowserKeyRestrictions: Conversion[Schema.V2BrowserKeyRestrictions, Option[Schema.V2BrowserKeyRestrictions]] = (fun: Schema.V2BrowserKeyRestrictions) => Option(fun)
		given putSchemaV2IosKeyRestrictions: Conversion[Schema.V2IosKeyRestrictions, Option[Schema.V2IosKeyRestrictions]] = (fun: Schema.V2IosKeyRestrictions) => Option(fun)
		given putListSchemaV2ApiTarget: Conversion[List[Schema.V2ApiTarget], Option[List[Schema.V2ApiTarget]]] = (fun: List[Schema.V2ApiTarget]) => Option(fun)
		given putSchemaV2AndroidKeyRestrictions: Conversion[Schema.V2AndroidKeyRestrictions, Option[Schema.V2AndroidKeyRestrictions]] = (fun: Schema.V2AndroidKeyRestrictions) => Option(fun)
		given putListSchemaV2AndroidApplication: Conversion[List[Schema.V2AndroidApplication], Option[List[Schema.V2AndroidApplication]]] = (fun: List[Schema.V2AndroidApplication]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaV2Restrictions: Conversion[Schema.V2Restrictions, Option[Schema.V2Restrictions]] = (fun: Schema.V2Restrictions) => Option(fun)
		given putListSchemaV2Key: Conversion[List[Schema.V2Key], Option[List[Schema.V2Key]]] = (fun: List[Schema.V2Key]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
