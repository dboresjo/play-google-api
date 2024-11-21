package com.boresjo.play.api.google.versionhistory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListPlatformsResponse: Format[Schema.ListPlatformsResponse] = Json.format[Schema.ListPlatformsResponse]
	given fmtPlatform: Format[Schema.Platform] = Json.format[Schema.Platform]
	given fmtPlatformPlatformTypeEnum: Format[Schema.Platform.PlatformTypeEnum] = JsonEnumFormat[Schema.Platform.PlatformTypeEnum]
	given fmtListChannelsResponse: Format[Schema.ListChannelsResponse] = Json.format[Schema.ListChannelsResponse]
	given fmtChannel: Format[Schema.Channel] = Json.format[Schema.Channel]
	given fmtChannelChannelTypeEnum: Format[Schema.Channel.ChannelTypeEnum] = JsonEnumFormat[Schema.Channel.ChannelTypeEnum]
	given fmtListVersionsResponse: Format[Schema.ListVersionsResponse] = Json.format[Schema.ListVersionsResponse]
	given fmtVersion: Format[Schema.Version] = Json.format[Schema.Version]
	given fmtListReleasesResponse: Format[Schema.ListReleasesResponse] = Json.format[Schema.ListReleasesResponse]
	given fmtRelease: Format[Schema.Release] = Json.format[Schema.Release]
	given fmtInterval: Format[Schema.Interval] = Json.format[Schema.Interval]
}
