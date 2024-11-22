package com.boresjo.play.api.google.discovery

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtRestDescription: Format[Schema.RestDescription] = Json.format[Schema.RestDescription]
	given fmtJsonSchema: Format[Schema.JsonSchema] = Json.format[Schema.JsonSchema]
	given fmtRestDescriptionIconsItem: Format[Schema.RestDescription.IconsItem] = Json.format[Schema.RestDescription.IconsItem]
	given fmtRestResource: Format[Schema.RestResource] = Json.format[Schema.RestResource]
	given fmtRestDescriptionAuthItem: Format[Schema.RestDescription.AuthItem] = Json.format[Schema.RestDescription.AuthItem]
	given fmtRestDescriptionAuthItemOauth2Item: Format[Schema.RestDescription.AuthItem.Oauth2Item] = Json.format[Schema.RestDescription.AuthItem.Oauth2Item]
	given fmtRestDescriptionAuthItemOauth2ItemScopesItem: Format[Schema.RestDescription.AuthItem.Oauth2Item.ScopesItem] = Json.format[Schema.RestDescription.AuthItem.Oauth2Item.ScopesItem]
	given fmtRestMethod: Format[Schema.RestMethod] = Json.format[Schema.RestMethod]
	given fmtRestDescriptionEndpointsItem: Format[Schema.RestDescription.EndpointsItem] = Json.format[Schema.RestDescription.EndpointsItem]
	given fmtDirectoryList: Format[Schema.DirectoryList] = Json.format[Schema.DirectoryList]
	given fmtDirectoryListItemsItem: Format[Schema.DirectoryList.ItemsItem] = Json.format[Schema.DirectoryList.ItemsItem]
	given fmtDirectoryListItemsItemIconsItem: Format[Schema.DirectoryList.ItemsItem.IconsItem] = Json.format[Schema.DirectoryList.ItemsItem.IconsItem]
	given fmtJsonSchemaAnnotationsItem: Format[Schema.JsonSchema.AnnotationsItem] = Json.format[Schema.JsonSchema.AnnotationsItem]
	given fmtJsonSchemaVariantItem: Format[Schema.JsonSchema.VariantItem] = Json.format[Schema.JsonSchema.VariantItem]
	given fmtJsonSchemaVariantItemMapItem: Format[Schema.JsonSchema.VariantItem.MapItem] = Json.format[Schema.JsonSchema.VariantItem.MapItem]
	given fmtRestMethodRequestItem: Format[Schema.RestMethod.RequestItem] = Json.format[Schema.RestMethod.RequestItem]
	given fmtRestMethodMediaUploadItem: Format[Schema.RestMethod.MediaUploadItem] = Json.format[Schema.RestMethod.MediaUploadItem]
	given fmtRestMethodMediaUploadItemProtocolsItem: Format[Schema.RestMethod.MediaUploadItem.ProtocolsItem] = Json.format[Schema.RestMethod.MediaUploadItem.ProtocolsItem]
	given fmtRestMethodMediaUploadItemProtocolsItemResumableItem: Format[Schema.RestMethod.MediaUploadItem.ProtocolsItem.ResumableItem] = Json.format[Schema.RestMethod.MediaUploadItem.ProtocolsItem.ResumableItem]
	given fmtRestMethodMediaUploadItemProtocolsItemSimpleItem: Format[Schema.RestMethod.MediaUploadItem.ProtocolsItem.SimpleItem] = Json.format[Schema.RestMethod.MediaUploadItem.ProtocolsItem.SimpleItem]
	given fmtRestMethodResponseItem: Format[Schema.RestMethod.ResponseItem] = Json.format[Schema.RestMethod.ResponseItem]
}
