package com.boresjo.play.api.google.youtubeAnalytics

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGroupItem: Conversion[List[Schema.GroupItem], Option[List[Schema.GroupItem]]] = (fun: List[Schema.GroupItem]) => Option(fun)
		given putSchemaErrors: Conversion[Schema.Errors, Option[Schema.Errors]] = (fun: Schema.Errors) => Option(fun)
		given putListSchemaErrorProto: Conversion[List[Schema.ErrorProto], Option[List[Schema.ErrorProto]]] = (fun: List[Schema.ErrorProto]) => Option(fun)
		given putSchemaErrorsCodeEnum: Conversion[Schema.Errors.CodeEnum, Option[Schema.Errors.CodeEnum]] = (fun: Schema.Errors.CodeEnum) => Option(fun)
		given putListListJsValue: Conversion[List[List[JsValue]], Option[List[List[JsValue]]]] = (fun: List[List[JsValue]]) => Option(fun)
		given putListSchemaResultTableColumnHeader: Conversion[List[Schema.ResultTableColumnHeader], Option[List[Schema.ResultTableColumnHeader]]] = (fun: List[Schema.ResultTableColumnHeader]) => Option(fun)
		given putSchemaGroupItemResource: Conversion[Schema.GroupItemResource, Option[Schema.GroupItemResource]] = (fun: Schema.GroupItemResource) => Option(fun)
		given putSchemaGroupContentDetails: Conversion[Schema.GroupContentDetails, Option[Schema.GroupContentDetails]] = (fun: Schema.GroupContentDetails) => Option(fun)
		given putSchemaGroupSnippet: Conversion[Schema.GroupSnippet, Option[Schema.GroupSnippet]] = (fun: Schema.GroupSnippet) => Option(fun)
		given putSchemaErrorProtoLocationTypeEnum: Conversion[Schema.ErrorProto.LocationTypeEnum, Option[Schema.ErrorProto.LocationTypeEnum]] = (fun: Schema.ErrorProto.LocationTypeEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaGroup: Conversion[List[Schema.Group], Option[List[Schema.Group]]] = (fun: List[Schema.Group]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
