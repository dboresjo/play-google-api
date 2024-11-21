package com.boresjo.play.api.google.mybusinessplaceactions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListPlaceActionTypeMetadataResponse: Format[Schema.ListPlaceActionTypeMetadataResponse] = Json.format[Schema.ListPlaceActionTypeMetadataResponse]
	given fmtPlaceActionTypeMetadata: Format[Schema.PlaceActionTypeMetadata] = Json.format[Schema.PlaceActionTypeMetadata]
	given fmtPlaceActionTypeMetadataPlaceActionTypeEnum: Format[Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum] = JsonEnumFormat[Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum]
	given fmtListPlaceActionLinksResponse: Format[Schema.ListPlaceActionLinksResponse] = Json.format[Schema.ListPlaceActionLinksResponse]
	given fmtPlaceActionLink: Format[Schema.PlaceActionLink] = Json.format[Schema.PlaceActionLink]
	given fmtPlaceActionLinkProviderTypeEnum: Format[Schema.PlaceActionLink.ProviderTypeEnum] = JsonEnumFormat[Schema.PlaceActionLink.ProviderTypeEnum]
	given fmtPlaceActionLinkPlaceActionTypeEnum: Format[Schema.PlaceActionLink.PlaceActionTypeEnum] = JsonEnumFormat[Schema.PlaceActionLink.PlaceActionTypeEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
}
