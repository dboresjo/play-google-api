package com.boresjo.play.api.google.discovery

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
		given putMapStringSchemaJsonSchema: Conversion[Map[String, Schema.JsonSchema], Option[Map[String, Schema.JsonSchema]]] = (fun: Map[String, Schema.JsonSchema]) => Option(fun)
		given putSchemaRestDescriptionIconsItem: Conversion[Schema.RestDescription.IconsItem, Option[Schema.RestDescription.IconsItem]] = (fun: Schema.RestDescription.IconsItem) => Option(fun)
		given putMapStringSchemaRestResource: Conversion[Map[String, Schema.RestResource], Option[Map[String, Schema.RestResource]]] = (fun: Map[String, Schema.RestResource]) => Option(fun)
		given putSchemaRestDescriptionAuthItem: Conversion[Schema.RestDescription.AuthItem, Option[Schema.RestDescription.AuthItem]] = (fun: Schema.RestDescription.AuthItem) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringSchemaRestMethod: Conversion[Map[String, Schema.RestMethod], Option[Map[String, Schema.RestMethod]]] = (fun: Map[String, Schema.RestMethod]) => Option(fun)
		given putListSchemaRestDescriptionEndpointsItem: Conversion[List[Schema.RestDescription.EndpointsItem], Option[List[Schema.RestDescription.EndpointsItem]]] = (fun: List[Schema.RestDescription.EndpointsItem]) => Option(fun)
		given putListSchemaDirectoryListItemsItem: Conversion[List[Schema.DirectoryList.ItemsItem], Option[List[Schema.DirectoryList.ItemsItem]]] = (fun: List[Schema.DirectoryList.ItemsItem]) => Option(fun)
		given putSchemaJsonSchema: Conversion[Schema.JsonSchema, Option[Schema.JsonSchema]] = (fun: Schema.JsonSchema) => Option(fun)
		given putSchemaJsonSchemaAnnotationsItem: Conversion[Schema.JsonSchema.AnnotationsItem, Option[Schema.JsonSchema.AnnotationsItem]] = (fun: Schema.JsonSchema.AnnotationsItem) => Option(fun)
		given putListBoolean: Conversion[List[Boolean], Option[List[Boolean]]] = (fun: List[Boolean]) => Option(fun)
		given putSchemaJsonSchemaVariantItem: Conversion[Schema.JsonSchema.VariantItem, Option[Schema.JsonSchema.VariantItem]] = (fun: Schema.JsonSchema.VariantItem) => Option(fun)
		given putSchemaRestMethodRequestItem: Conversion[Schema.RestMethod.RequestItem, Option[Schema.RestMethod.RequestItem]] = (fun: Schema.RestMethod.RequestItem) => Option(fun)
		given putSchemaRestMethodMediaUploadItem: Conversion[Schema.RestMethod.MediaUploadItem, Option[Schema.RestMethod.MediaUploadItem]] = (fun: Schema.RestMethod.MediaUploadItem) => Option(fun)
		given putSchemaRestMethodResponseItem: Conversion[Schema.RestMethod.ResponseItem, Option[Schema.RestMethod.ResponseItem]] = (fun: Schema.RestMethod.ResponseItem) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
