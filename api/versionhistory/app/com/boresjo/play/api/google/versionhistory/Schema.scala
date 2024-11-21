package com.boresjo.play.api.google.versionhistory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListPlatformsResponse(
	  /** The list of platforms. */
		platforms: Option[List[Schema.Platform]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Platform {
		enum PlatformTypeEnum extends Enum[PlatformTypeEnum] { case PLATFORM_TYPE_UNSPECIFIED, WIN, WIN64, MAC, LINUX, ANDROID, WEBVIEW, IOS, ALL, MAC_ARM64, LACROS, LACROS_ARM32, CHROMEOS, LACROS_ARM64, FUCHSIA, WIN_ARM64 }
	}
	case class Platform(
	  /** Platform name. Format is "{product}/platforms/{platform}" */
		name: Option[String] = None,
	  /** Type of platform. */
		platformType: Option[Schema.Platform.PlatformTypeEnum] = None
	)
	
	case class ListChannelsResponse(
	  /** The list of channels. */
		channels: Option[List[Schema.Channel]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Channel {
		enum ChannelTypeEnum extends Enum[ChannelTypeEnum] { case CHANNEL_TYPE_UNSPECIFIED, STABLE, BETA, DEV, CANARY, CANARY_ASAN, ALL, EXTENDED, LTS, LTC }
	}
	case class Channel(
	  /** Channel name. Format is "{product}/platforms/{platform}/channels/{channel}" */
		name: Option[String] = None,
	  /** Type of channel. */
		channelType: Option[Schema.Channel.ChannelTypeEnum] = None
	)
	
	case class ListVersionsResponse(
	  /** The list of versions. */
		versions: Option[List[Schema.Version]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Version(
	  /** Version name. Format is "{product}/platforms/{platform}/channels/{channel}/versions/{version}" e.g. "chrome/platforms/win/channels/beta/versions/84.0.4147.38" */
		name: Option[String] = None,
	  /** String containing just the version number. e.g. "84.0.4147.38" */
		version: Option[String] = None
	)
	
	case class ListReleasesResponse(
	  /** The list of releases. */
		releases: Option[List[Schema.Release]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Release(
	  /** Release name. Format is "{product}/platforms/{platform}/channels/{channel}/versions/{version}/releases/{release}" */
		name: Option[String] = None,
	  /** Timestamp interval of when the release was live. If end_time is unspecified, the release is currently live. */
		serving: Option[Schema.Interval] = None,
	  /** Rollout fraction. This fraction indicates the fraction of people that should receive this version in this release. If the fraction is not specified in ReleaseManager, the API will assume fraction is 1. */
		fraction: Option[BigDecimal] = None,
	  /** String containing just the version number. e.g. "84.0.4147.38" */
		version: Option[String] = None,
	  /** Rollout fraction group. Only fractions with the same fraction_group are statistically comparable: there may be non-fractional differences between different fraction groups. */
		fractionGroup: Option[String] = None,
	  /** Whether or not the release was available for version pinning. */
		pinnable: Option[Boolean] = None
	)
	
	case class Interval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
}
