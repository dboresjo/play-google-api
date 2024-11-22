package com.boresjo.play.api.google.customsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaPromotion: Conversion[List[Schema.Promotion], Option[List[Schema.Promotion]]] = (fun: List[Schema.Promotion]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaSearchUrlItem: Conversion[Schema.Search.UrlItem, Option[Schema.Search.UrlItem]] = (fun: Schema.Search.UrlItem) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaSearchQueriesItem: Conversion[Schema.Search.QueriesItem, Option[Schema.Search.QueriesItem]] = (fun: Schema.Search.QueriesItem) => Option(fun)
		given putListSchemaResult: Conversion[List[Schema.Result], Option[List[Schema.Result]]] = (fun: List[Schema.Result]) => Option(fun)
		given putSchemaSearchSpellingItem: Conversion[Schema.Search.SpellingItem, Option[Schema.Search.SpellingItem]] = (fun: Schema.Search.SpellingItem) => Option(fun)
		given putSchemaSearchSearchInformationItem: Conversion[Schema.Search.SearchInformationItem, Option[Schema.Search.SearchInformationItem]] = (fun: Schema.Search.SearchInformationItem) => Option(fun)
		given putListSchemaResultLabelsItem: Conversion[List[Schema.Result.LabelsItem], Option[List[Schema.Result.LabelsItem]]] = (fun: List[Schema.Result.LabelsItem]) => Option(fun)
		given putSchemaResultImageItem: Conversion[Schema.Result.ImageItem, Option[Schema.Result.ImageItem]] = (fun: Schema.Result.ImageItem) => Option(fun)
		given putListSchemaPromotionBodyLinesItem: Conversion[List[Schema.Promotion.BodyLinesItem], Option[List[Schema.Promotion.BodyLinesItem]]] = (fun: List[Schema.Promotion.BodyLinesItem]) => Option(fun)
		given putSchemaPromotionImageItem: Conversion[Schema.Promotion.ImageItem, Option[Schema.Promotion.ImageItem]] = (fun: Schema.Promotion.ImageItem) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
