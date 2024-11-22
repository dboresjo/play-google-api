package com.boresjo.play.api.google.firebasedatabase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaDatabaseInstanceStateEnum: Conversion[Schema.DatabaseInstance.StateEnum, Option[Schema.DatabaseInstance.StateEnum]] = (fun: Schema.DatabaseInstance.StateEnum) => Option(fun)
		given putSchemaDatabaseInstanceTypeEnum: Conversion[Schema.DatabaseInstance.TypeEnum, Option[Schema.DatabaseInstance.TypeEnum]] = (fun: Schema.DatabaseInstance.TypeEnum) => Option(fun)
		given putListSchemaDatabaseInstance: Conversion[List[Schema.DatabaseInstance], Option[List[Schema.DatabaseInstance]]] = (fun: List[Schema.DatabaseInstance]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
