package com.boresjo.play.api.google.kgsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
	}
	object OptionDefault {
	}
}
