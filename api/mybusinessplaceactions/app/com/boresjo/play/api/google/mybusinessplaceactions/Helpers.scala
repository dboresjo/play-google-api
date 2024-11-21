package com.boresjo.play.api.google.mybusinessplaceactions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaPlaceActionTypeMetadata: Conversion[List[Schema.PlaceActionTypeMetadata], Option[List[Schema.PlaceActionTypeMetadata]]] = (fun: List[Schema.PlaceActionTypeMetadata]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPlaceActionTypeMetadataPlaceActionTypeEnum: Conversion[Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum, Option[Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum]] = (fun: Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum) => Option(fun)
		given putListSchemaPlaceActionLink: Conversion[List[Schema.PlaceActionLink], Option[List[Schema.PlaceActionLink]]] = (fun: List[Schema.PlaceActionLink]) => Option(fun)
		given putSchemaPlaceActionLinkProviderTypeEnum: Conversion[Schema.PlaceActionLink.ProviderTypeEnum, Option[Schema.PlaceActionLink.ProviderTypeEnum]] = (fun: Schema.PlaceActionLink.ProviderTypeEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaPlaceActionLinkPlaceActionTypeEnum: Conversion[Schema.PlaceActionLink.PlaceActionTypeEnum, Option[Schema.PlaceActionLink.PlaceActionTypeEnum]] = (fun: Schema.PlaceActionLink.PlaceActionTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
