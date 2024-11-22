package com.boresjo.play.api.google.libraryagent

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleExampleLibraryagentV1Book: Conversion[List[Schema.GoogleExampleLibraryagentV1Book], Option[List[Schema.GoogleExampleLibraryagentV1Book]]] = (fun: List[Schema.GoogleExampleLibraryagentV1Book]) => Option(fun)
		given putListSchemaGoogleExampleLibraryagentV1Shelf: Conversion[List[Schema.GoogleExampleLibraryagentV1Shelf], Option[List[Schema.GoogleExampleLibraryagentV1Shelf]]] = (fun: List[Schema.GoogleExampleLibraryagentV1Shelf]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
