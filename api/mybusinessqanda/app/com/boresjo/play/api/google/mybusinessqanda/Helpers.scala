package com.boresjo.play.api.google.mybusinessqanda

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaQuestion: Conversion[List[Schema.Question], Option[List[Schema.Question]]] = (fun: List[Schema.Question]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAuthor: Conversion[Schema.Author, Option[Schema.Author]] = (fun: Schema.Author) => Option(fun)
		given putListSchemaAnswer: Conversion[List[Schema.Answer], Option[List[Schema.Answer]]] = (fun: List[Schema.Answer]) => Option(fun)
		given putSchemaAuthorTypeEnum: Conversion[Schema.Author.TypeEnum, Option[Schema.Author.TypeEnum]] = (fun: Schema.Author.TypeEnum) => Option(fun)
		given putSchemaAnswer: Conversion[Schema.Answer, Option[Schema.Answer]] = (fun: Schema.Answer) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
