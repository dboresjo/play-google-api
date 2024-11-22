package com.boresjo.play.api.google.smartdevicemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleHomeEnterpriseSdmV1ParentRelation: Conversion[List[Schema.GoogleHomeEnterpriseSdmV1ParentRelation], Option[List[Schema.GoogleHomeEnterpriseSdmV1ParentRelation]]] = (fun: List[Schema.GoogleHomeEnterpriseSdmV1ParentRelation]) => Option(fun)
		given putListSchemaGoogleHomeEnterpriseSdmV1Room: Conversion[List[Schema.GoogleHomeEnterpriseSdmV1Room], Option[List[Schema.GoogleHomeEnterpriseSdmV1Room]]] = (fun: List[Schema.GoogleHomeEnterpriseSdmV1Room]) => Option(fun)
		given putListSchemaGoogleHomeEnterpriseSdmV1Device: Conversion[List[Schema.GoogleHomeEnterpriseSdmV1Device], Option[List[Schema.GoogleHomeEnterpriseSdmV1Device]]] = (fun: List[Schema.GoogleHomeEnterpriseSdmV1Device]) => Option(fun)
		given putListSchemaGoogleHomeEnterpriseSdmV1Structure: Conversion[List[Schema.GoogleHomeEnterpriseSdmV1Structure], Option[List[Schema.GoogleHomeEnterpriseSdmV1Structure]]] = (fun: List[Schema.GoogleHomeEnterpriseSdmV1Structure]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
