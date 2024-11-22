package com.boresjo.play.api.google.acceleratedmobilepageurl

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaBatchGetAmpUrlsRequestLookupStrategyEnum: Conversion[Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum, Option[Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum]] = (fun: Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum) => Option(fun)
		given putListSchemaAmpUrl: Conversion[List[Schema.AmpUrl], Option[List[Schema.AmpUrl]]] = (fun: List[Schema.AmpUrl]) => Option(fun)
		given putListSchemaAmpUrlError: Conversion[List[Schema.AmpUrlError], Option[List[Schema.AmpUrlError]]] = (fun: List[Schema.AmpUrlError]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAmpUrlErrorErrorCodeEnum: Conversion[Schema.AmpUrlError.ErrorCodeEnum, Option[Schema.AmpUrlError.ErrorCodeEnum]] = (fun: Schema.AmpUrlError.ErrorCodeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
