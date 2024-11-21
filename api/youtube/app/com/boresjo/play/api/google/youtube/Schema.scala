package com.boresjo.play.api.google.youtube

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ChannelSectionTargeting(
	  /** The region the channel section is targeting. */
		regions: Option[List[String]] = None,
	  /** The country the channel section is targeting. */
		countries: Option[List[String]] = None,
	  /** The language the channel section is targeting. */
		languages: Option[List[String]] = None
	)
	
	case class MonitorStreamInfo(
	  /** HTML code that embeds a player that plays the monitor stream. */
		embedHtml: Option[String] = None,
	  /** If you have set the enableMonitorStream property to true, then this property determines the length of the live broadcast delay. */
		broadcastStreamDelayMs: Option[Int] = None,
	  /** This value determines whether the monitor stream is enabled for the broadcast. If the monitor stream is enabled, then YouTube will broadcast the event content on a special stream intended only for the broadcaster's consumption. The broadcaster can use the stream to review the event content and also to identify the optimal times to insert cuepoints. You need to set this value to true if you intend to have a broadcast delay for your event. &#42;Note:&#42; This property cannot be updated once the broadcast is in the testing or live state. */
		enableMonitorStream: Option[Boolean] = None
	)
	
	object VideoFileDetails {
		enum FileTypeEnum extends Enum[FileTypeEnum] { case video, audio, image, archive, document, project, other }
	}
	case class VideoFileDetails(
	  /** A list of audio streams contained in the uploaded video file. Each item in the list contains detailed metadata about an audio stream. */
		audioStreams: Option[List[Schema.VideoFileDetailsAudioStream]] = None,
	  /** The date and time when the uploaded video file was created. The value is specified in ISO 8601 format. Currently, the following ISO 8601 formats are supported: - Date only: YYYY-MM-DD - Naive time: YYYY-MM-DDTHH:MM:SS - Time with timezone: YYYY-MM-DDTHH:MM:SS+HH:MM  */
		creationTime: Option[String] = None,
	  /** The uploaded video file's container format. */
		container: Option[String] = None,
	  /** The uploaded file's type as detected by YouTube's video processing engine. Currently, YouTube only processes video files, but this field is present whether a video file or another type of file was uploaded. */
		fileType: Option[Schema.VideoFileDetails.FileTypeEnum] = None,
	  /** A list of video streams contained in the uploaded video file. Each item in the list contains detailed metadata about a video stream. */
		videoStreams: Option[List[Schema.VideoFileDetailsVideoStream]] = None,
	  /** The uploaded file's name. This field is present whether a video file or another type of file was uploaded. */
		fileName: Option[String] = None,
	  /** The uploaded file's size in bytes. This field is present whether a video file or another type of file was uploaded. */
		fileSize: Option[String] = None,
	  /** The length of the uploaded video in milliseconds. */
		durationMs: Option[String] = None,
	  /** The uploaded video file's combined (video and audio) bitrate in bits per second. */
		bitrateBps: Option[String] = None
	)
	
	case class Channel(
	  /** The snippet object contains basic details about the channel, such as its title, description, and thumbnail images. */
		snippet: Option[Schema.ChannelSnippet] = None,
	  /** The topicDetails object encapsulates information about Freebase topics associated with the channel. */
		topicDetails: Option[Schema.ChannelTopicDetails] = None,
	  /** Localizations for different languages */
		localizations: Option[Map[String, Schema.ChannelLocalization]] = None,
	  /** The status object encapsulates information about the privacy status of the channel. */
		status: Option[Schema.ChannelStatus] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The statistics object encapsulates statistics for the channel. */
		statistics: Option[Schema.ChannelStatistics] = None,
	  /** The conversionPings object encapsulates information about conversion pings that need to be respected by the channel. */
		conversionPings: Option[Schema.ChannelConversionPings] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#channel". */
		kind: Option[String] = Some("""youtube#channel"""),
	  /** The contentDetails object encapsulates information about the channel's content. */
		contentDetails: Option[Schema.ChannelContentDetails] = None,
	  /** The auditionDetails object encapsulates channel data that is relevant for YouTube Partners during the audition process. */
		auditDetails: Option[Schema.ChannelAuditDetails] = None,
	  /** The contentOwnerDetails object encapsulates channel data that is relevant for YouTube Partners linked with the channel. */
		contentOwnerDetails: Option[Schema.ChannelContentOwnerDetails] = None,
	  /** The brandingSettings object encapsulates information about the branding of the channel. */
		brandingSettings: Option[Schema.ChannelBrandingSettings] = None,
	  /** The ID that YouTube uses to uniquely identify the channel. */
		id: Option[String] = None
	)
	
	object ChannelStatus {
		enum LongUploadsStatusEnum extends Enum[LongUploadsStatusEnum] { case longUploadsUnspecified, allowed, eligible, disallowed }
		enum PrivacyStatusEnum extends Enum[PrivacyStatusEnum] { case `public`, unlisted, `private` }
	}
	case class ChannelStatus(
		madeForKids: Option[Boolean] = None,
	  /** The long uploads status of this channel. See https://support.google.com/youtube/answer/71673 for more information. */
		longUploadsStatus: Option[Schema.ChannelStatus.LongUploadsStatusEnum] = None,
	  /** Privacy status of the channel. */
		privacyStatus: Option[Schema.ChannelStatus.PrivacyStatusEnum] = None,
		selfDeclaredMadeForKids: Option[Boolean] = None,
	  /** If true, then the user is linked to either a YouTube username or G+ account. Otherwise, the user doesn't have a public YouTube identity. */
		isLinked: Option[Boolean] = None
	)
	
	case class PlaylistPlayer(
	  /** An <iframe> tag that embeds a player that will play the playlist. */
		embedHtml: Option[String] = None
	)
	
	case class CommentThreadSnippet(
	  /** The YouTube channel the comments in the thread refer to or the channel with the video the comments refer to. If video_id isn't set the comments refer to the channel itself. */
		channelId: Option[String] = None,
	  /** The top level comment of this thread. */
		topLevelComment: Option[Schema.Comment] = None,
	  /** Whether the current viewer of the thread can reply to it. This is viewer specific - other viewers may see a different value for this field. */
		canReply: Option[Boolean] = None,
	  /** Whether the thread (and therefore all its comments) is visible to all YouTube users. */
		isPublic: Option[Boolean] = None,
	  /** The total number of replies (not including the top level comment). */
		totalReplyCount: Option[Int] = None,
	  /** The ID of the video the comments refer to, if any. No video_id implies a channel discussion comment. */
		videoId: Option[String] = None
	)
	
	case class PlaylistImage(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlistImages". */
		kind: Option[String] = Some("""youtube#playlistImage"""),
		snippet: Option[Schema.PlaylistImageSnippet] = None,
	  /** Identifies this resource (playlist id and image type). */
		id: Option[String] = None
	)
	
	case class PlaylistContentDetails(
	  /** The number of videos in the playlist. */
		itemCount: Option[Int] = None
	)
	
	case class LiveStreamListResponse(
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveStreamListResponse". */
		kind: Option[String] = Some("""youtube#liveStreamListResponse"""),
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** A list of live streams that match the request criteria. */
		items: Option[List[Schema.LiveStream]] = None
	)
	
	case class CommentThreadListResponse(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** A list of comment threads that match the request criteria. */
		items: Option[List[Schema.CommentThread]] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#commentThreadListResponse". */
		kind: Option[String] = Some("""youtube#commentThreadListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None
	)
	
	case class ActivityContentDetailsChannelItem(
	  /** The resourceId object contains information that identifies the resource that was added to the channel. */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	case class LiveBroadcastMonetizationDetails(
		cuepointSchedule: Option[Schema.CuepointSchedule] = None
	)
	
	case class VideoPlayer(
	  /** An <iframe> tag that embeds a player that will play the video. */
		embedHtml: Option[String] = None,
	  /** The embed width */
		embedWidth: Option[String] = None,
		embedHeight: Option[String] = None
	)
	
	case class ResourceId(
	  /** The ID that YouTube uses to uniquely identify the referred resource, if that resource is a channel. This property is only present if the resourceId.kind value is youtube#channel. */
		channelId: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the referred resource, if that resource is a playlist. This property is only present if the resourceId.kind value is youtube#playlist. */
		playlistId: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the referred resource, if that resource is a video. This property is only present if the resourceId.kind value is youtube#video. */
		videoId: Option[String] = None,
	  /** The type of the API resource. */
		kind: Option[String] = None
	)
	
	case class InvideoBranding(
	  /** The spatial position within the video where the branding watermark will be displayed. */
		position: Option[Schema.InvideoPosition] = None,
	  /** The bytes the uploaded image. Only used in api to youtube communication. */
		imageBytes: Option[String] = None,
	  /** The temporal position within the video where watermark will be displayed. */
		timing: Option[Schema.InvideoTiming] = None,
	  /** The url of the uploaded image. Only used in apiary to api communication. */
		imageUrl: Option[String] = None,
	  /** The channel to which this branding links. If not present it defaults to the current channel. */
		targetChannelId: Option[String] = None
	)
	
	case class AbuseReport(
		relatedEntities: Option[List[Schema.RelatedEntity]] = None,
		abuseTypes: Option[List[Schema.AbuseType]] = None,
		subject: Option[Schema.Entity] = None,
		description: Option[String] = None
	)
	
	case class Thumbnail(
	  /** (Optional) Height of the thumbnail image. */
		height: Option[Int] = None,
	  /** The thumbnail image's URL. */
		url: Option[String] = None,
	  /** (Optional) Width of the thumbnail image. */
		width: Option[Int] = None
	)
	
	case class CommentListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#commentListResponse". */
		kind: Option[String] = Some("""youtube#commentListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** A list of comments that match the request criteria. */
		items: Option[List[Schema.Comment]] = None
	)
	
	case class ChannelBrandingSettings(
	  /** Branding properties for branding images. */
		image: Option[Schema.ImageSettings] = None,
	  /** Branding properties for the watch page. */
		watch: Option[Schema.WatchSettings] = None,
	  /** Additional experimental branding properties. */
		hints: Option[List[Schema.PropertyValue]] = None,
	  /** Branding properties for the channel view. */
		channel: Option[Schema.ChannelSettings] = None
	)
	
	case class LiveChatGiftMembershipReceivedDetails(
	  /** The name of the Level at which the viewer is a member. This matches the `snippet.membershipGiftingDetails.giftMembershipsLevelName` of the associated membership gifting message. The Level names are defined by the YouTube channel offering the Membership. In some situations this field isn't filled. */
		memberLevelName: Option[String] = None,
	  /** The ID of the membership gifting message that is related to this gift membership. This ID will always refer to a message whose type is 'membershipGiftingEvent'. */
		associatedMembershipGiftingMessageId: Option[String] = None,
	  /** The ID of the user that made the membership gifting purchase. This matches the `snippet.authorChannelId` of the associated membership gifting message. */
		gifterChannelId: Option[String] = None
	)
	
	case class VideoProcessingDetailsProcessingProgress(
	  /** The number of parts of the video that YouTube has already processed. You can estimate the percentage of the video that YouTube has already processed by calculating: 100 &#42; parts_processed / parts_total Note that since the estimated number of parts could increase without a corresponding increase in the number of parts that have already been processed, it is possible that the calculated progress could periodically decrease while YouTube processes a video. */
		partsProcessed: Option[String] = None,
	  /** An estimate of the total number of parts that need to be processed for the video. The number may be updated with more precise estimates while YouTube processes the video. */
		partsTotal: Option[String] = None,
	  /** An estimate of the amount of time, in millseconds, that YouTube needs to finish processing the video. */
		timeLeftMs: Option[String] = None
	)
	
	case class LiveChatMessageAuthorDetails(
	  /** The channel's URL. */
		channelUrl: Option[String] = None,
	  /** The channels's avatar URL. */
		profileImageUrl: Option[String] = None,
	  /** Whether the author is the owner of the live chat. */
		isChatOwner: Option[Boolean] = None,
	  /** Whether the author is a moderator of the live chat. */
		isChatModerator: Option[Boolean] = None,
	  /** The YouTube channel ID. */
		channelId: Option[String] = None,
	  /** Whether the author is a sponsor of the live chat. */
		isChatSponsor: Option[Boolean] = None,
	  /** The channel's display name. */
		displayName: Option[String] = None,
	  /** Whether the author's identity has been verified by YouTube. */
		isVerified: Option[Boolean] = None
	)
	
	case class VideoAbuseReport(
	  /** The high-level, or primary, reason that the content is abusive. The value is an abuse report reason ID. */
		reasonId: Option[String] = None,
	  /** The language that the content was viewed in. */
		language: Option[String] = None,
	  /** The specific, or secondary, reason that this content is abusive (if available). The value is an abuse report reason ID that is a valid secondary reason for the primary reason. */
		secondaryReasonId: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the video. */
		videoId: Option[String] = None,
	  /** Additional comments regarding the abuse report. */
		comments: Option[String] = None
	)
	
	case class Member(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#member". */
		kind: Option[String] = Some("""youtube#member"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The snippet object contains basic details about the member. */
		snippet: Option[Schema.MemberSnippet] = None
	)
	
	case class ChannelConversionPings(
	  /** Pings that the app shall fire (authenticated by biscotti cookie). Each ping has a context, in which the app must fire the ping, and a url identifying the ping. */
		pings: Option[List[Schema.ChannelConversionPing]] = None
	)
	
	case class LiveStreamContentDetails(
	  /** Indicates whether the stream is reusable, which means that it can be bound to multiple broadcasts. It is common for broadcasters to reuse the same stream for many different broadcasts if those broadcasts occur at different times. If you set this value to false, then the stream will not be reusable, which means that it can only be bound to one broadcast. Non-reusable streams differ from reusable streams in the following ways: - A non-reusable stream can only be bound to one broadcast. - A non-reusable stream might be deleted by an automated process after the broadcast ends. - The liveStreams.list method does not list non-reusable streams if you call the method and set the mine parameter to true. The only way to use that method to retrieve the resource for a non-reusable stream is to use the id parameter to identify the stream.  */
		isReusable: Option[Boolean] = None,
	  /** The ingestion URL where the closed captions of this stream are sent. */
		closedCaptionsIngestionUrl: Option[String] = None
	)
	
	case class LocalizedProperty(
		default: Option[String] = None,
	  /** The language of the default property. */
		defaultLanguage: Option[Schema.LanguageTag] = None,
		localized: Option[List[Schema.LocalizedString]] = None
	)
	
	object VideoProcessingDetails {
		enum ProcessingFailureReasonEnum extends Enum[ProcessingFailureReasonEnum] { case uploadFailed, transcodeFailed, streamingFailed, other }
		enum ProcessingStatusEnum extends Enum[ProcessingStatusEnum] { case processing, succeeded, failed, terminated }
	}
	case class VideoProcessingDetails(
	  /** This value indicates whether file details are available for the uploaded video. You can retrieve a video's file details by requesting the fileDetails part in your videos.list() request. */
		fileDetailsAvailability: Option[String] = None,
	  /** The processingProgress object contains information about the progress YouTube has made in processing the video. The values are really only relevant if the video's processing status is processing. */
		processingProgress: Option[Schema.VideoProcessingDetailsProcessingProgress] = None,
	  /** This value indicates whether keyword (tag) suggestions are available for the video. Tags can be added to a video's metadata to make it easier for other users to find the video. You can retrieve these suggestions by requesting the suggestions part in your videos.list() request. */
		tagSuggestionsAvailability: Option[String] = None,
	  /** This value indicates whether video editing suggestions, which might improve video quality or the playback experience, are available for the video. You can retrieve these suggestions by requesting the suggestions part in your videos.list() request. */
		editorSuggestionsAvailability: Option[String] = None,
	  /** The reason that YouTube failed to process the video. This property will only have a value if the processingStatus property's value is failed. */
		processingFailureReason: Option[Schema.VideoProcessingDetails.ProcessingFailureReasonEnum] = None,
	  /** This value indicates whether thumbnail images have been generated for the video. */
		thumbnailsAvailability: Option[String] = None,
	  /** The video's processing status. This value indicates whether YouTube was able to process the video or if the video is still being processed. */
		processingStatus: Option[Schema.VideoProcessingDetails.ProcessingStatusEnum] = None,
	  /** This value indicates whether the video processing engine has generated suggestions that might improve YouTube's ability to process the the video, warnings that explain video processing problems, or errors that cause video processing problems. You can retrieve these suggestions by requesting the suggestions part in your videos.list() request. */
		processingIssuesAvailability: Option[String] = None
	)
	
	case class TokenPagination(
	
	)
	
	case class MembershipsLevel(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The snippet object contains basic details about the level. */
		snippet: Option[Schema.MembershipsLevelSnippet] = None,
	  /** The ID that YouTube assigns to uniquely identify the memberships level. */
		id: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#membershipsLevelListResponse". */
		kind: Option[String] = Some("""youtube#membershipsLevel""")
	)
	
	object InvideoTiming {
		enum TypeEnum extends Enum[TypeEnum] { case offsetFromStart, offsetFromEnd }
	}
	case class InvideoTiming(
	  /** Describes a timing type. If the value is offsetFromStart, then the offsetMs field represents an offset from the start of the video. If the value is offsetFromEnd, then the offsetMs field represents an offset from the end of the video. */
		`type`: Option[Schema.InvideoTiming.TypeEnum] = None,
	  /** Defines the duration in milliseconds for which the promotion should be displayed. If missing, the client should use the default. */
		durationMs: Option[String] = None,
	  /** Defines the time at which the promotion will appear. Depending on the value of type the value of the offsetMs field will represent a time offset from the start or from the end of the video, expressed in milliseconds. */
		offsetMs: Option[String] = None
	)
	
	case class PlaylistSnippet(
	  /** The date and time that the playlist was created. */
		publishedAt: Option[String] = None,
	  /** The playlist's title. */
		title: Option[String] = None,
	  /** A map of thumbnail images associated with the playlist. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The channel title of the channel that the video belongs to. */
		channelTitle: Option[String] = None,
	  /** Note: if the playlist has a custom thumbnail, this field will not be populated. The video id selected by the user that will be used as the thumbnail of this playlist. This field defaults to the first publicly viewable video in the playlist, if: 1. The user has never selected a video to be the thumbnail of the playlist. 2. The user selects a video to be the thumbnail, and then removes that video from the playlist. 3. The user selects a non-owned video to be the thumbnail, but that video becomes private, or gets deleted. */
		thumbnailVideoId: Option[String] = None,
	  /** The playlist's description. */
		description: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the channel that published the playlist. */
		channelId: Option[String] = None,
	  /** Localized title and description, read-only. */
		localized: Option[Schema.PlaylistLocalization] = None,
	  /** Keyword tags associated with the playlist. */
		tags: Option[List[String]] = None,
	  /** The language of the playlist's default title and description. */
		defaultLanguage: Option[String] = None
	)
	
	case class LanguageTag(
		value: Option[String] = None
	)
	
	object SubscriptionContentDetails {
		enum ActivityTypeEnum extends Enum[ActivityTypeEnum] { case subscriptionActivityTypeUnspecified, all, uploads }
	}
	case class SubscriptionContentDetails(
	  /** The type of activity this subscription is for (only uploads, everything). */
		activityType: Option[Schema.SubscriptionContentDetails.ActivityTypeEnum] = None,
	  /** The number of new items in the subscription since its content was last read. */
		newItemCount: Option[Int] = None,
	  /** The approximate number of items that the subscription points to. */
		totalItemCount: Option[Int] = None
	)
	
	case class ChannelLocalization(
	  /** The localized strings for channel's description. */
		description: Option[String] = None,
	  /** The localized strings for channel's title. */
		title: Option[String] = None
	)
	
	case class TestItem(
		gaia: Option[String] = None,
		snippet: Option[Schema.TestItemTestItemSnippet] = None,
	  /** Etag for the resource. See https://en.wikipedia.org/wiki/HTTP_ETag. */
		etag: Option[String] = None,
		id: Option[String] = None,
		featuredPart: Option[Boolean] = None
	)
	
	case class VideoFileDetailsAudioStream(
	  /** A value that uniquely identifies a video vendor. Typically, the value is a four-letter vendor code. */
		vendor: Option[String] = None,
	  /** The audio stream's bitrate, in bits per second. */
		bitrateBps: Option[String] = None,
	  /** The audio codec that the stream uses. */
		codec: Option[String] = None,
	  /** The number of audio channels that the stream contains. */
		channelCount: Option[Int] = None
	)
	
	case class LiveChatNewSponsorDetails(
	  /** If the viewer just had upgraded from a lower level. For viewers that were not members at the time of purchase, this field is false. */
		isUpgrade: Option[Boolean] = None,
	  /** The name of the Level that the viewer just had joined. The Level names are defined by the YouTube channel offering the Membership. In some situations this field isn't filled. */
		memberLevelName: Option[String] = None
	)
	
	object VideoAgeGating {
		enum VideoGameRatingEnum extends Enum[VideoGameRatingEnum] { case anyone, m15Plus, m16Plus, m17Plus }
	}
	case class VideoAgeGating(
	  /** Indicates whether or not the video has alcoholic beverage content. Only users of legal purchasing age in a particular country, as identified by ICAP, can view the content. */
		alcoholContent: Option[Boolean] = None,
	  /** Age-restricted trailers. For redband trailers and adult-rated video-games. Only users aged 18+ can view the content. The the field is true the content is restricted to viewers aged 18+. Otherwise The field won't be present. */
		restricted: Option[Boolean] = None,
	  /** Video game rating, if any. */
		videoGameRating: Option[Schema.VideoAgeGating.VideoGameRatingEnum] = None
	)
	
	case class CommentSnippetAuthorChannelId(
		value: Option[String] = None
	)
	
	case class MembershipsDetails(
	  /** Ids of all levels that the user has access to. This includes the currently active level and all other levels that are included because of a higher purchase. */
		accessibleLevels: Option[List[String]] = None,
	  /** Id of the highest level that the user has access to at the moment. */
		highestAccessibleLevel: Option[String] = None,
	  /** Display name for the highest level that the user has access to at the moment. */
		highestAccessibleLevelDisplayName: Option[String] = None,
	  /** Data about memberships duration without taking into consideration pricing levels. */
		membershipsDuration: Option[Schema.MembershipsDuration] = None,
	  /** Data about memberships duration on particular pricing levels. */
		membershipsDurationAtLevels: Option[List[Schema.MembershipsDurationAtLevel]] = None
	)
	
	object ChannelSectionSnippet {
		enum StyleEnum extends Enum[StyleEnum] { case channelsectionStyleUnspecified, horizontalRow, verticalList }
		enum TypeEnum extends Enum[TypeEnum] { case channelsectionTypeUndefined, singlePlaylist, multiplePlaylists, popularUploads, recentUploads, likes, allPlaylists, likedPlaylists, recentPosts, recentActivity, liveEvents, upcomingEvents, completedEvents, multipleChannels, postedVideos, postedPlaylists, subscriptions }
	}
	case class ChannelSectionSnippet(
	  /** The style of the channel section. */
		style: Option[Schema.ChannelSectionSnippet.StyleEnum] = None,
	  /** The ID that YouTube uses to uniquely identify the channel that published the channel section. */
		channelId: Option[String] = None,
	  /** The language of the channel section's default title and description. */
		defaultLanguage: Option[String] = None,
	  /** The position of the channel section in the channel. */
		position: Option[Int] = None,
	  /** The type of the channel section. */
		`type`: Option[Schema.ChannelSectionSnippet.TypeEnum] = None,
	  /** Localized title, read-only. */
		localized: Option[Schema.ChannelSectionLocalization] = None,
	  /** The channel section's title for multiple_playlists and multiple_channels. */
		title: Option[String] = None
	)
	
	case class LiveStream(
	  /** The status object contains information about live stream's status. */
		status: Option[Schema.LiveStreamStatus] = None,
	  /** The snippet object contains basic details about the stream, including its channel, title, and description. */
		snippet: Option[Schema.LiveStreamSnippet] = None,
	  /** The ID that YouTube assigns to uniquely identify the stream. */
		id: Option[String] = None,
	  /** The cdn object defines the live stream's content delivery network (CDN) settings. These settings provide details about the manner in which you stream your content to YouTube. */
		cdn: Option[Schema.CdnSettings] = None,
	  /** The content_details object contains information about the stream, including the closed captions ingestion URL. */
		contentDetails: Option[Schema.LiveStreamContentDetails] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveStream". */
		kind: Option[String] = Some("""youtube#liveStream""")
	)
	
	case class VideoCategory(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#videoCategory". */
		kind: Option[String] = Some("""youtube#videoCategory"""),
	  /** The ID that YouTube uses to uniquely identify the video category. */
		id: Option[String] = None,
	  /** The snippet object contains basic details about the video category, including its title. */
		snippet: Option[Schema.VideoCategorySnippet] = None
	)
	
	case class PlaylistItemListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlistItemListResponse". */
		kind: Option[String] = Some("""youtube#playlistItemListResponse"""),
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
		etag: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** A list of playlist items that match the request criteria. */
		items: Option[List[Schema.PlaylistItem]] = None
	)
	
	object LiveChatBanSnippet {
		enum TypeEnum extends Enum[TypeEnum] { case liveChatBanTypeUnspecified, permanent, temporary }
	}
	case class LiveChatBanSnippet(
	  /** The type of ban. */
		`type`: Option[Schema.LiveChatBanSnippet.TypeEnum] = None,
	  /** The chat this ban is pertinent to. */
		liveChatId: Option[String] = None,
		bannedUserDetails: Option[Schema.ChannelProfileDetails] = None,
	  /** The duration of a ban, only filled if the ban has type TEMPORARY. */
		banDurationSeconds: Option[String] = None
	)
	
	case class SuperStickerMetadata(
	  /** Unique identifier of the Super Sticker. This is a shorter form of the alt_text that includes pack name and a recognizable characteristic of the sticker. */
		stickerId: Option[String] = None,
	  /** Internationalized alt text that describes the sticker image and any animation associated with it. */
		altText: Option[String] = None,
	  /** Specifies the localization language in which the alt text is returned. */
		altTextLanguage: Option[String] = None
	)
	
	case class I18nRegionSnippet(
	  /** The region code as a 2-letter ISO country code. */
		gl: Option[String] = None,
	  /** The human-readable name of the region. */
		name: Option[String] = None
	)
	
	object ActivityContentDetailsRecommendation {
		enum ReasonEnum extends Enum[ReasonEnum] { case reasonUnspecified, videoFavorited, videoLiked, videoWatched }
	}
	case class ActivityContentDetailsRecommendation(
	  /** The resourceId object contains information that identifies the recommended resource. */
		resourceId: Option[Schema.ResourceId] = None,
	  /** The reason that the resource is recommended to the user. */
		reason: Option[Schema.ActivityContentDetailsRecommendation.ReasonEnum] = None,
	  /** The seedResourceId object contains information about the resource that caused the recommendation. */
		seedResourceId: Option[Schema.ResourceId] = None
	)
	
	case class LiveChatTextMessageDetails(
	  /** The user's message. */
		messageText: Option[String] = None
	)
	
	object ContentRating {
		enum RussiaRatingEnum extends Enum[RussiaRatingEnum] { case russiaUnspecified, russia0, russia6, russia12, russia16, russia18, russiaUnrated }
		enum NkclvRatingEnum extends Enum[NkclvRatingEnum] { case nkclvUnspecified, nkclvU, nkclv7plus, nkclv12plus, nkclv16plus, nkclv18plus, nkclvUnrated }
		enum RteRatingEnum extends Enum[RteRatingEnum] { case rteUnspecified, rteGa, rteCh, rtePs, rteMa, rteUnrated }
		enum ChfilmRatingEnum extends Enum[ChfilmRatingEnum] { case chfilmUnspecified, chfilm0, chfilm6, chfilm12, chfilm16, chfilm18, chfilmUnrated }
		enum PefilmRatingEnum extends Enum[PefilmRatingEnum] { case pefilmUnspecified, pefilmPt, pefilmPg, pefilm14, pefilm18, pefilmUnrated }
		enum AnatelRatingEnum extends Enum[AnatelRatingEnum] { case anatelUnspecified, anatelF, anatelI, anatelI7, anatelI10, anatelI12, anatelR, anatelA, anatelUnrated }
		enum MenaMpaaRatingEnum extends Enum[MenaMpaaRatingEnum] { case menaMpaaUnspecified, menaMpaaG, menaMpaaPg, menaMpaaPg13, menaMpaaR, menaMpaaUnrated }
		enum CbfcRatingEnum extends Enum[CbfcRatingEnum] { case cbfcUnspecified, cbfcU, cbfcUA, cbfcUA7plus, cbfcUA13plus, cbfcUA16plus, cbfcA, cbfcS, cbfcUnrated }
		enum RcnofRatingEnum extends Enum[RcnofRatingEnum] { case rcnofUnspecified, rcnofI, rcnofIi, rcnofIii, rcnofIv, rcnofV, rcnofVi, rcnofUnrated }
		enum IcaaRatingEnum extends Enum[IcaaRatingEnum] { case icaaUnspecified, icaaApta, icaa7, icaa12, icaa13, icaa16, icaa18, icaaX, icaaUnrated }
		enum KmrbRatingEnum extends Enum[KmrbRatingEnum] { case kmrbUnspecified, kmrbAll, kmrb12plus, kmrb15plus, kmrbTeenr, kmrbR, kmrbUnrated }
		enum EefilmRatingEnum extends Enum[EefilmRatingEnum] { case eefilmUnspecified, eefilmPere, eefilmL, eefilmMs6, eefilmK6, eefilmMs12, eefilmK12, eefilmK14, eefilmK16, eefilmUnrated }
		enum KijkwijzerRatingEnum extends Enum[KijkwijzerRatingEnum] { case kijkwijzerUnspecified, kijkwijzerAl, kijkwijzer6, kijkwijzer9, kijkwijzer12, kijkwijzer16, kijkwijzer18, kijkwijzerUnrated }
		enum MibacRatingEnum extends Enum[MibacRatingEnum] { case mibacUnspecified, mibacT, mibacVap, mibacVm6, mibacVm12, mibacVm14, mibacVm16, mibacVm18, mibacUnrated }
		enum YtRatingEnum extends Enum[YtRatingEnum] { case ytUnspecified, ytAgeRestricted }
		enum MpaatRatingEnum extends Enum[MpaatRatingEnum] { case mpaatUnspecified, mpaatGb, mpaatRb }
		enum CatvfrRatingEnum extends Enum[CatvfrRatingEnum] { case catvfrUnspecified, catvfrG, catvfr8plus, catvfr13plus, catvfr16plus, catvfr18plus, catvfrUnrated, catvfrE }
		enum NbcRatingEnum extends Enum[NbcRatingEnum] { case nbcUnspecified, nbcG, nbcPg, nbc12plus, nbc15plus, nbc18plus, nbc18plusr, nbcPu, nbcUnrated }
		enum McstRatingEnum extends Enum[McstRatingEnum] { case mcstUnspecified, mcstP, mcst0, mcstC13, mcstC16, mcst16plus, mcstC18, mcstGPg, mcstUnrated }
		enum OflcRatingEnum extends Enum[OflcRatingEnum] { case oflcUnspecified, oflcG, oflcPg, oflcM, oflcR13, oflcR15, oflcR16, oflcR18, oflcUnrated, oflcRp13, oflcRp16, oflcRp18 }
		enum MocRatingEnum extends Enum[MocRatingEnum] { case mocUnspecified, mocE, mocT, moc7, moc12, moc15, moc18, mocX, mocBanned, mocUnrated }
		enum CatvRatingEnum extends Enum[CatvRatingEnum] { case catvUnspecified, catvC, catvC8, catvG, catvPg, catv14plus, catv18plus, catvUnrated, catvE }
		enum KfcbRatingEnum extends Enum[KfcbRatingEnum] { case kfcbUnspecified, kfcbG, kfcbPg, kfcb16plus, kfcbR, kfcbUnrated }
		enum NfrcRatingEnum extends Enum[NfrcRatingEnum] { case nfrcUnspecified, nfrcA, nfrcB, nfrcC, nfrcD, nfrcX, nfrcUnrated }
		enum EgfilmRatingEnum extends Enum[EgfilmRatingEnum] { case egfilmUnspecified, egfilmGn, egfilm18, egfilmBn, egfilmUnrated }
		enum CzfilmRatingEnum extends Enum[CzfilmRatingEnum] { case czfilmUnspecified, czfilmU, czfilm12, czfilm14, czfilm18, czfilmUnrated }
		enum IlfilmRatingEnum extends Enum[IlfilmRatingEnum] { case ilfilmUnspecified, ilfilmAa, ilfilm12, ilfilm14, ilfilm16, ilfilm18, ilfilmUnrated }
		enum EirinRatingEnum extends Enum[EirinRatingEnum] { case eirinUnspecified, eirinG, eirinPg12, eirinR15plus, eirinR18plus, eirinUnrated }
		enum NmcRatingEnum extends Enum[NmcRatingEnum] { case nmcUnspecified, nmcG, nmcPg, nmcPg13, nmcPg15, nmc15plus, nmc18plus, nmc18tc, nmcUnrated }
		enum ChvrsRatingEnum extends Enum[ChvrsRatingEnum] { case chvrsUnspecified, chvrsG, chvrsPg, chvrs14a, chvrs18a, chvrsR, chvrsE, chvrsUnrated }
		enum FcbmRatingEnum extends Enum[FcbmRatingEnum] { case fcbmUnspecified, fcbmU, fcbmPg13, fcbmP13, fcbm18, fcbm18sx, fcbm18pa, fcbm18sg, fcbm18pl, fcbmUnrated }
		enum GrfilmRatingEnum extends Enum[GrfilmRatingEnum] { case grfilmUnspecified, grfilmK, grfilmE, grfilmK12, grfilmK13, grfilmK15, grfilmK17, grfilmK18, grfilmUnrated }
		enum FskRatingEnum extends Enum[FskRatingEnum] { case fskUnspecified, fsk0, fsk6, fsk12, fsk16, fsk18, fskUnrated }
		enum IfcoRatingEnum extends Enum[IfcoRatingEnum] { case ifcoUnspecified, ifcoG, ifcoPg, ifco12, ifco12a, ifco15, ifco15a, ifco16, ifco18, ifcoUnrated }
		enum SmaisRatingEnum extends Enum[SmaisRatingEnum] { case smaisUnspecified, smaisL, smais7, smais12, smais14, smais16, smais18, smaisUnrated }
		enum FpbRatingReasonsEnum extends Enum[FpbRatingReasonsEnum] { case fpbRatingReasonUnspecified, fpbBlasphemy, fpbLanguage, fpbNudity, fpbPrejudice, fpbSex, fpbViolence, fpbDrugs, fpbSexualViolence, fpbHorror, fpbCriminalTechniques, fpbImitativeActsTechniques }
		enum MtrcbRatingEnum extends Enum[MtrcbRatingEnum] { case mtrcbUnspecified, mtrcbG, mtrcbPg, mtrcbR13, mtrcbR16, mtrcbR18, mtrcbX, mtrcbUnrated }
		enum CicfRatingEnum extends Enum[CicfRatingEnum] { case cicfUnspecified, cicfE, cicfKtEa, cicfKntEna, cicfUnrated }
		enum CccRatingEnum extends Enum[CccRatingEnum] { case cccUnspecified, cccTe, ccc6, ccc14, ccc18, ccc18v, ccc18s, cccUnrated }
		enum FmocRatingEnum extends Enum[FmocRatingEnum] { case fmocUnspecified, fmocU, fmoc10, fmoc12, fmoc16, fmoc18, fmocE, fmocUnrated }
		enum MccaaRatingEnum extends Enum[MccaaRatingEnum] { case mccaaUnspecified, mccaaU, mccaaPg, mccaa12a, mccaa12, mccaa14, mccaa15, mccaa16, mccaa18, mccaaUnrated }
		enum MoctwRatingEnum extends Enum[MoctwRatingEnum] { case moctwUnspecified, moctwG, moctwP, moctwPg, moctwR, moctwUnrated, moctwR12, moctwR15 }
		enum LsfRatingEnum extends Enum[LsfRatingEnum] { case lsfUnspecified, lsfSu, lsfA, lsfBo, lsf13, lsfR, lsf17, lsfD, lsf21, lsfUnrated }
		enum AgcomRatingEnum extends Enum[AgcomRatingEnum] { case agcomUnspecified, agcomT, agcomVm14, agcomVm18, agcomUnrated }
		enum CncRatingEnum extends Enum[CncRatingEnum] { case cncUnspecified, cncT, cnc10, cnc12, cnc16, cnc18, cncE, cncInterdiction, cncUnrated }
		enum RtcRatingEnum extends Enum[RtcRatingEnum] { case rtcUnspecified, rtcAa, rtcA, rtcB, rtcB15, rtcC, rtcD, rtcUnrated }
		enum IncaaRatingEnum extends Enum[IncaaRatingEnum] { case incaaUnspecified, incaaAtp, incaaSam13, incaaSam16, incaaSam18, incaaC, incaaUnrated }
		enum NbcplRatingEnum extends Enum[NbcplRatingEnum] { case nbcplUnspecified, nbcplI, nbcplIi, nbcplIii, nbcplIv, nbcpl18plus, nbcplUnrated }
		enum FcoRatingEnum extends Enum[FcoRatingEnum] { case fcoUnspecified, fcoI, fcoIia, fcoIib, fcoIi, fcoIii, fcoUnrated }
		enum MpaaRatingEnum extends Enum[MpaaRatingEnum] { case mpaaUnspecified, mpaaG, mpaaPg, mpaaPg13, mpaaR, mpaaNc17, mpaaX, mpaaUnrated }
		enum SkfilmRatingEnum extends Enum[SkfilmRatingEnum] { case skfilmUnspecified, skfilmG, skfilmP2, skfilmP5, skfilmP8, skfilmUnrated }
		enum DjctqRatingEnum extends Enum[DjctqRatingEnum] { case djctqUnspecified, djctqL, djctq10, djctq12, djctq14, djctq16, djctq18, djctqEr, djctqL10, djctqL12, djctqL14, djctqL16, djctqL18, djctq1012, djctq1014, djctq1016, djctq1018, djctq1214, djctq1216, djctq1218, djctq1416, djctq1418, djctq1618, djctqUnrated }
		enum CnaRatingEnum extends Enum[CnaRatingEnum] { case cnaUnspecified, cnaAp, cna12, cna15, cna18, cna18plus, cnaUnrated }
		enum MccypRatingEnum extends Enum[MccypRatingEnum] { case mccypUnspecified, mccypA, mccyp7, mccyp11, mccyp15, mccypUnrated }
		enum AcbRatingEnum extends Enum[AcbRatingEnum] { case acbUnspecified, acbE, acbP, acbC, acbG, acbPg, acbM, acbMa15plus, acbR18plus, acbUnrated }
		enum ResorteviolenciaRatingEnum extends Enum[ResorteviolenciaRatingEnum] { case resorteviolenciaUnspecified, resorteviolenciaA, resorteviolenciaB, resorteviolenciaC, resorteviolenciaD, resorteviolenciaE, resorteviolenciaUnrated }
		enum NfvcbRatingEnum extends Enum[NfvcbRatingEnum] { case nfvcbUnspecified, nfvcbG, nfvcbPg, nfvcb12, nfvcb12a, nfvcb15, nfvcb18, nfvcbRe, nfvcbUnrated }
		enum TvpgRatingEnum extends Enum[TvpgRatingEnum] { case tvpgUnspecified, tvpgY, tvpgY7, tvpgY7Fv, tvpgG, tvpgPg, pg14, tvpgMa, tvpgUnrated }
		enum CceRatingEnum extends Enum[CceRatingEnum] { case cceUnspecified, cceM4, cceM6, cceM12, cceM16, cceM18, cceUnrated, cceM14 }
		enum MdaRatingEnum extends Enum[MdaRatingEnum] { case mdaUnspecified, mdaG, mdaPg, mdaPg13, mdaNc16, mdaM18, mdaR21, mdaUnrated }
		enum BfvcRatingEnum extends Enum[BfvcRatingEnum] { case bfvcUnspecified, bfvcG, bfvcE, bfvc13, bfvc15, bfvc18, bfvc20, bfvcB, bfvcUnrated }
		enum CsaRatingEnum extends Enum[CsaRatingEnum] { case csaUnspecified, csaT, csa10, csa12, csa16, csa18, csaInterdiction, csaUnrated }
		enum BbfcRatingEnum extends Enum[BbfcRatingEnum] { case bbfcUnspecified, bbfcU, bbfcPg, bbfc12a, bbfc12, bbfc15, bbfc18, bbfcR18, bbfcUnrated }
		enum MedietilsynetRatingEnum extends Enum[MedietilsynetRatingEnum] { case medietilsynetUnspecified, medietilsynetA, medietilsynet6, medietilsynet7, medietilsynet9, medietilsynet11, medietilsynet12, medietilsynet15, medietilsynet18, medietilsynetUnrated }
		enum MekuRatingEnum extends Enum[MekuRatingEnum] { case mekuUnspecified, mekuS, meku7, meku12, meku16, meku18, mekuUnrated }
		enum EcbmctRatingEnum extends Enum[EcbmctRatingEnum] { case ecbmctUnspecified, ecbmctG, ecbmct7a, ecbmct7plus, ecbmct13a, ecbmct13plus, ecbmct15a, ecbmct15plus, ecbmct18plus, ecbmctUnrated }
		enum SmsaRatingEnum extends Enum[SmsaRatingEnum] { case smsaUnspecified, smsaA, smsa7, smsa11, smsa15, smsaUnrated }
		enum CscfRatingEnum extends Enum[CscfRatingEnum] { case cscfUnspecified, cscfAl, cscfA, cscf6, cscf9, cscf12, cscf16, cscf18, cscfUnrated }
		enum DjctqRatingReasonsEnum extends Enum[DjctqRatingReasonsEnum] { case djctqRatingReasonUnspecified, djctqViolence, djctqExtremeViolence, djctqSexualContent, djctqNudity, djctqSex, djctqExplicitSex, djctqDrugs, djctqLegalDrugs, djctqIllegalDrugs, djctqInappropriateLanguage, djctqCriminalActs, djctqImpactingContent }
		enum BmukkRatingEnum extends Enum[BmukkRatingEnum] { case bmukkUnspecified, bmukkAa, bmukk6, bmukk8, bmukk10, bmukk12, bmukk14, bmukk16, bmukkUnrated }
		enum FpbRatingEnum extends Enum[FpbRatingEnum] { case fpbUnspecified, fpbA, fpbPg, fpb79Pg, fpb1012Pg, fpb13, fpb16, fpb18, fpbX18, fpbXx, fpbUnrated, fpb10 }
	}
	case class ContentRating(
	  /** The video's National Film Registry of the Russian Federation (MKRF - Russia) rating. */
		russiaRating: Option[Schema.ContentRating.RussiaRatingEnum] = None,
	  /** The video's rating from the Nacionãlais Kino centrs (National Film Centre of Latvia). */
		nkclvRating: Option[Schema.ContentRating.NkclvRatingEnum] = None,
	  /** The video's rating from Ireland's Raidió Teilifís Éireann. */
		rteRating: Option[Schema.ContentRating.RteRatingEnum] = None,
	  /** The video's rating in Switzerland. */
		chfilmRating: Option[Schema.ContentRating.ChfilmRatingEnum] = None,
	  /** The video's rating in Peru. */
		pefilmRating: Option[Schema.ContentRating.PefilmRatingEnum] = None,
	  /** The video's Anatel (Asociación Nacional de Televisión) rating for Chilean television. */
		anatelRating: Option[Schema.ContentRating.AnatelRatingEnum] = None,
	  /** The rating system for MENA countries, a clone of MPAA. It is needed to prevent titles go live w/o additional QC check, since some of them can be inappropriate for the countries at all. See b/33408548 for more details. */
		menaMpaaRating: Option[Schema.ContentRating.MenaMpaaRatingEnum] = None,
	  /** The video's Central Board of Film Certification (CBFC - India) rating. */
		cbfcRating: Option[Schema.ContentRating.CbfcRatingEnum] = None,
	  /** The video's rating from the Hungarian Nemzeti Filmiroda, the Rating Committee of the National Office of Film. */
		rcnofRating: Option[Schema.ContentRating.RcnofRatingEnum] = None,
	  /** The video's Instituto de la Cinematografía y de las Artes Audiovisuales (ICAA - Spain) rating. */
		icaaRating: Option[Schema.ContentRating.IcaaRatingEnum] = None,
	  /** The video's Korea Media Rating Board (영상물등급위원회) rating. The KMRB rates videos in South Korea. */
		kmrbRating: Option[Schema.ContentRating.KmrbRatingEnum] = None,
	  /** The video's rating in Estonia. */
		eefilmRating: Option[Schema.ContentRating.EefilmRatingEnum] = None,
	  /** The video's NICAM/Kijkwijzer rating from the Nederlands Instituut voor de Classificatie van Audiovisuele Media (Netherlands). */
		kijkwijzerRating: Option[Schema.ContentRating.KijkwijzerRatingEnum] = None,
	  /** The video's rating from the Ministero dei Beni e delle Attività Culturali e del Turismo (Italy). */
		mibacRating: Option[Schema.ContentRating.MibacRatingEnum] = None,
	  /** A rating that YouTube uses to identify age-restricted content. */
		ytRating: Option[Schema.ContentRating.YtRatingEnum] = None,
	  /** The rating system for trailer, DVD, and Ad in the US. See http://movielabs.com/md/ratings/v2.3/html/US_MPAAT_Ratings.html. */
		mpaatRating: Option[Schema.ContentRating.MpaatRatingEnum] = None,
	  /** The video's rating from the Canadian Radio-Television and Telecommunications Commission (CRTC) for Canadian French-language broadcasts. For more information, see the Canadian Broadcast Standards Council website. */
		catvfrRating: Option[Schema.ContentRating.CatvfrRatingEnum] = None,
	  /** The video's rating from the Maldives National Bureau of Classification. */
		nbcRating: Option[Schema.ContentRating.NbcRatingEnum] = None,
	  /** The video's rating system for Vietnam - MCST */
		mcstRating: Option[Schema.ContentRating.McstRatingEnum] = None,
	  /** The video's Office of Film and Literature Classification (OFLC - New Zealand) rating. */
		oflcRating: Option[Schema.ContentRating.OflcRatingEnum] = None,
	  /** The video's Ministerio de Cultura (Colombia) rating. */
		mocRating: Option[Schema.ContentRating.MocRatingEnum] = None,
	  /** Rating system for Canadian TV - Canadian TV Classification System The video's rating from the Canadian Radio-Television and Telecommunications Commission (CRTC) for Canadian English-language broadcasts. For more information, see the Canadian Broadcast Standards Council website. */
		catvRating: Option[Schema.ContentRating.CatvRatingEnum] = None,
	  /** The video's rating from the Kenya Film Classification Board. */
		kfcbRating: Option[Schema.ContentRating.KfcbRatingEnum] = None,
	  /** The video's rating from the Bulgarian National Film Center. */
		nfrcRating: Option[Schema.ContentRating.NfrcRatingEnum] = None,
	  /** The video's rating in Egypt. */
		egfilmRating: Option[Schema.ContentRating.EgfilmRatingEnum] = None,
	  /** The video's rating in the Czech Republic. */
		czfilmRating: Option[Schema.ContentRating.CzfilmRatingEnum] = None,
	  /** The video's rating in Israel. */
		ilfilmRating: Option[Schema.ContentRating.IlfilmRatingEnum] = None,
	  /** The video's Eirin (映倫) rating. Eirin is the Japanese rating system. */
		eirinRating: Option[Schema.ContentRating.EirinRatingEnum] = None,
	  /** The National Media Council ratings system for United Arab Emirates. */
		nmcRating: Option[Schema.ContentRating.NmcRatingEnum] = None,
	  /** The video's Canadian Home Video Rating System (CHVRS) rating. */
		chvrsRating: Option[Schema.ContentRating.ChvrsRatingEnum] = None,
	  /** The video's rating from Malaysia's Film Censorship Board. */
		fcbmRating: Option[Schema.ContentRating.FcbmRatingEnum] = None,
	  /** The video's rating in Greece. */
		grfilmRating: Option[Schema.ContentRating.GrfilmRatingEnum] = None,
	  /** The video's Freiwillige Selbstkontrolle der Filmwirtschaft (FSK - Germany) rating. */
		fskRating: Option[Schema.ContentRating.FskRatingEnum] = None,
	  /** The video's Irish Film Classification Office (IFCO - Ireland) rating. See the IFCO website for more information. */
		ifcoRating: Option[Schema.ContentRating.IfcoRatingEnum] = None,
	  /** The video's rating in Iceland. */
		smaisRating: Option[Schema.ContentRating.SmaisRatingEnum] = None,
	  /** Reasons that explain why the video received its FPB (South Africa) rating. */
		fpbRatingReasons: Option[List[Schema.ContentRating.FpbRatingReasonsEnum]] = None,
	  /** The video's rating from the Movie and Television Review and Classification Board (Philippines). */
		mtrcbRating: Option[Schema.ContentRating.MtrcbRatingEnum] = None,
	  /** The video's rating from the Commission de Contrôle des Films (Belgium). */
		cicfRating: Option[Schema.ContentRating.CicfRatingEnum] = None,
	  /** The video's Consejo de Calificación Cinematográfica (Chile) rating. */
		cccRating: Option[Schema.ContentRating.CccRatingEnum] = None,
	  /** This property has been deprecated. Use the contentDetails.contentRating.cncRating instead. */
		fmocRating: Option[Schema.ContentRating.FmocRatingEnum] = None,
	  /** The video's rating from Malta's Film Age-Classification Board. */
		mccaaRating: Option[Schema.ContentRating.MccaaRatingEnum] = None,
	  /** The video's rating from Taiwan's Ministry of Culture (文化部). */
		moctwRating: Option[Schema.ContentRating.MoctwRatingEnum] = None,
	  /** The video's rating from Indonesia's Lembaga Sensor Film. */
		lsfRating: Option[Schema.ContentRating.LsfRatingEnum] = None,
	  /** The video's rating from Italy's Autorità per le Garanzie nelle Comunicazioni (AGCOM). */
		agcomRating: Option[Schema.ContentRating.AgcomRatingEnum] = None,
	  /** Rating system in France - Commission de classification cinematographique */
		cncRating: Option[Schema.ContentRating.CncRatingEnum] = None,
	  /** The video's General Directorate of Radio, Television and Cinematography (Mexico) rating. */
		rtcRating: Option[Schema.ContentRating.RtcRatingEnum] = None,
	  /** The video's INCAA (Instituto Nacional de Cine y Artes Audiovisuales - Argentina) rating. */
		incaaRating: Option[Schema.ContentRating.IncaaRatingEnum] = None,
	  /** The video's rating in Poland. */
		nbcplRating: Option[Schema.ContentRating.NbcplRatingEnum] = None,
	  /** The video's rating from Hong Kong's Office for Film, Newspaper and Article Administration. */
		fcoRating: Option[Schema.ContentRating.FcoRatingEnum] = None,
	  /** The video's Motion Picture Association of America (MPAA) rating. */
		mpaaRating: Option[Schema.ContentRating.MpaaRatingEnum] = None,
	  /** The video's rating in Slovakia. */
		skfilmRating: Option[Schema.ContentRating.SkfilmRatingEnum] = None,
	  /** The video's Departamento de Justiça, Classificação, Qualificação e Títulos (DJCQT - Brazil) rating. */
		djctqRating: Option[Schema.ContentRating.DjctqRatingEnum] = None,
	  /** The video's rating from Romania's CONSILIUL NATIONAL AL AUDIOVIZUALULUI (CNA). */
		cnaRating: Option[Schema.ContentRating.CnaRatingEnum] = None,
	  /** The video's rating from the Danish Film Institute's (Det Danske Filminstitut) Media Council for Children and Young People. */
		mccypRating: Option[Schema.ContentRating.MccypRatingEnum] = None,
	  /** The video's Australian Classification Board (ACB) or Australian Communications and Media Authority (ACMA) rating. ACMA ratings are used to classify children's television programming. */
		acbRating: Option[Schema.ContentRating.AcbRatingEnum] = None,
	  /** The video's rating in Venezuela. */
		resorteviolenciaRating: Option[Schema.ContentRating.ResorteviolenciaRatingEnum] = None,
	  /** The video's rating from Nigeria's National Film and Video Censors Board. */
		nfvcbRating: Option[Schema.ContentRating.NfvcbRatingEnum] = None,
	  /** The video's TV Parental Guidelines (TVPG) rating. */
		tvpgRating: Option[Schema.ContentRating.TvpgRatingEnum] = None,
	  /** The video's rating from Portugal's Comissão de Classificação de Espect´culos. */
		cceRating: Option[Schema.ContentRating.CceRatingEnum] = None,
	  /** The video's rating from Singapore's Media Development Authority (MDA) and, specifically, it's Board of Film Censors (BFC). */
		mdaRating: Option[Schema.ContentRating.MdaRatingEnum] = None,
	  /** The video's rating from Thailand's Board of Film and Video Censors. */
		bfvcRating: Option[Schema.ContentRating.BfvcRatingEnum] = None,
	  /** The video's rating from France's Conseil supérieur de l’audiovisuel, which rates broadcast content. */
		csaRating: Option[Schema.ContentRating.CsaRatingEnum] = None,
	  /** The video's British Board of Film Classification (BBFC) rating. */
		bbfcRating: Option[Schema.ContentRating.BbfcRatingEnum] = None,
	  /** The video's rating from Medietilsynet, the Norwegian Media Authority. */
		medietilsynetRating: Option[Schema.ContentRating.MedietilsynetRatingEnum] = None,
	  /** The video's rating from Finland's Kansallinen Audiovisuaalinen Instituutti (National Audiovisual Institute). */
		mekuRating: Option[Schema.ContentRating.MekuRatingEnum] = None,
	  /** Rating system in Turkey - Evaluation and Classification Board of the Ministry of Culture and Tourism */
		ecbmctRating: Option[Schema.ContentRating.EcbmctRatingEnum] = None,
	  /** The video's rating from Statens medieråd (Sweden's National Media Council). */
		smsaRating: Option[Schema.ContentRating.SmsaRatingEnum] = None,
	  /** The video's rating from Luxembourg's Commission de surveillance de la classification des films (CSCF). */
		cscfRating: Option[Schema.ContentRating.CscfRatingEnum] = None,
	  /** Reasons that explain why the video received its DJCQT (Brazil) rating. */
		djctqRatingReasons: Option[List[Schema.ContentRating.DjctqRatingReasonsEnum]] = None,
	  /** The video's rating from the Austrian Board of Media Classification (Bundesministerium für Unterricht, Kunst und Kultur). */
		bmukkRating: Option[Schema.ContentRating.BmukkRatingEnum] = None,
	  /** The video's rating from South Africa's Film and Publication Board. */
		fpbRating: Option[Schema.ContentRating.FpbRatingEnum] = None
	)
	
	case class LiveChatPollDetailsPollMetadata(
		questionText: Option[String] = None,
	  /** The options will be returned in the order that is displayed in 1P */
		options: Option[List[Schema.LiveChatPollDetailsPollMetadataPollOption]] = None
	)
	
	case class VideoListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#videoListResponse". */
		kind: Option[String] = Some("""youtube#videoListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
		items: Option[List[Schema.Video]] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	object CommentSnippet {
		enum ModerationStatusEnum extends Enum[ModerationStatusEnum] { case published, heldForReview, likelySpam, rejected }
		enum ViewerRatingEnum extends Enum[ViewerRatingEnum] { case none, like, dislike }
	}
	case class CommentSnippet(
	  /** The comment's original raw text as initially posted or last updated. The original text will only be returned if it is accessible to the viewer, which is only guaranteed if the viewer is the comment's author. */
		textOriginal: Option[String] = None,
	  /** The URL for the avatar of the user who posted the comment. */
		authorProfileImageUrl: Option[String] = None,
	  /** The date and time when the comment was last updated. */
		updatedAt: Option[String] = None,
	  /** The total number of likes this comment has received. */
		likeCount: Option[Int] = None,
	  /** Whether the current viewer can rate this comment. */
		canRate: Option[Boolean] = None,
	  /** The ID of the video the comment refers to, if any. */
		videoId: Option[String] = None,
	  /** The name of the user who posted the comment. */
		authorDisplayName: Option[String] = None,
	  /** The comment's moderation status. Will not be set if the comments were requested through the id filter. */
		moderationStatus: Option[Schema.CommentSnippet.ModerationStatusEnum] = None,
	  /** The unique id of the parent comment, only set for replies. */
		parentId: Option[String] = None,
	  /** The date and time when the comment was originally published. */
		publishedAt: Option[String] = None,
	  /** The rating the viewer has given to this comment. For the time being this will never return RATE_TYPE_DISLIKE and instead return RATE_TYPE_NONE. This may change in the future. */
		viewerRating: Option[Schema.CommentSnippet.ViewerRatingEnum] = None,
	  /** Link to the author's YouTube channel, if any. */
		authorChannelUrl: Option[String] = None,
	  /** The id of the corresponding YouTube channel. In case of a channel comment this is the channel the comment refers to. In case of a video comment it's the video's channel. */
		channelId: Option[String] = None,
		authorChannelId: Option[Schema.CommentSnippetAuthorChannelId] = None,
	  /** The comment's text. The format is either plain text or HTML dependent on what has been requested. Even the plain text representation may differ from the text originally posted in that it may replace video links with video titles etc. */
		textDisplay: Option[String] = None
	)
	
	object VideoSuggestions {
		enum ProcessingWarningsEnum extends Enum[ProcessingWarningsEnum] { case unknownContainer, unknownVideoCodec, unknownAudioCodec, inconsistentResolution, hasEditlist, problematicVideoCodec, problematicAudioCodec, unsupportedVrStereoMode, unsupportedSphericalProjectionType, unsupportedHdrPixelFormat, unsupportedHdrColorMetadata, problematicHdrLookupTable }
		enum ProcessingHintsEnum extends Enum[ProcessingHintsEnum] { case nonStreamableMov, sendBestQualityVideo, sphericalVideo, spatialAudio, vrVideo, hdrVideo }
		enum EditorSuggestionsEnum extends Enum[EditorSuggestionsEnum] { case videoAutoLevels, videoStabilize, videoCrop, audioQuietAudioSwap }
		enum ProcessingErrorsEnum extends Enum[ProcessingErrorsEnum] { case audioFile, imageFile, projectFile, notAVideoFile, docFile, archiveFile, unsupportedSpatialAudioLayout }
	}
	case class VideoSuggestions(
	  /** A list of reasons why YouTube may have difficulty transcoding the uploaded video or that might result in an erroneous transcoding. These warnings are generated before YouTube actually processes the uploaded video file. In addition, they identify issues that are unlikely to cause the video processing to fail but that might cause problems such as sync issues, video artifacts, or a missing audio track. */
		processingWarnings: Option[List[Schema.VideoSuggestions.ProcessingWarningsEnum]] = None,
	  /** A list of suggestions that may improve YouTube's ability to process the video. */
		processingHints: Option[List[Schema.VideoSuggestions.ProcessingHintsEnum]] = None,
	  /** A list of video editing operations that might improve the video quality or playback experience of the uploaded video. */
		editorSuggestions: Option[List[Schema.VideoSuggestions.EditorSuggestionsEnum]] = None,
	  /** A list of keyword tags that could be added to the video's metadata to increase the likelihood that users will locate your video when searching or browsing on YouTube. */
		tagSuggestions: Option[List[Schema.VideoSuggestionsTagSuggestion]] = None,
	  /** A list of errors that will prevent YouTube from successfully processing the uploaded video video. These errors indicate that, regardless of the video's current processing status, eventually, that status will almost certainly be failed. */
		processingErrors: Option[List[Schema.VideoSuggestions.ProcessingErrorsEnum]] = None
	)
	
	case class VideoGetRatingResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#videoGetRatingResponse". */
		kind: Option[String] = Some("""youtube#videoGetRatingResponse"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** A list of ratings that match the request criteria. */
		items: Option[List[Schema.VideoRating]] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None
	)
	
	case class VideoMonetizationDetails(
	  /** The value of access indicates whether the video can be monetized or not. */
		access: Option[Schema.AccessPolicy] = None
	)
	
	case class VideoStatistics(
	  /** The number of times the video has been viewed. */
		viewCount: Option[String] = None,
	  /** The number of users who have indicated that they liked the video by giving it a positive rating. */
		likeCount: Option[String] = None,
	  /** The number of users who currently have the video marked as a favorite video. */
		favoriteCount: Option[String] = None,
	  /** The number of comments for the video. */
		commentCount: Option[String] = None,
	  /** The number of users who have indicated that they disliked the video by giving it a negative rating. */
		dislikeCount: Option[String] = None
	)
	
	case class AccessPolicy(
	  /** A list of region codes that identify countries where the default policy do not apply. */
		exception: Option[List[String]] = None,
	  /** The value of allowed indicates whether the access to the policy is allowed or denied by default. */
		allowed: Option[Boolean] = None
	)
	
	case class LiveChatModeratorListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveChatModeratorListResponse". */
		kind: Option[String] = Some("""youtube#liveChatModeratorListResponse"""),
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** A list of moderators that match the request criteria. */
		items: Option[List[Schema.LiveChatModerator]] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None
	)
	
	case class ChannelSettings(
		defaultLanguage: Option[String] = None,
	  /** The trailer of the channel, for users that are not subscribers. */
		unsubscribedTrailer: Option[String] = None,
	  /** A prominent color that can be rendered on this channel page. */
		profileColor: Option[String] = None,
	  /** Lists keywords associated with the channel, comma-separated. */
		keywords: Option[String] = None,
	  /** The list of featured channels. */
		featuredChannelsUrls: Option[List[String]] = None,
	  /** Specifies the channel title. */
		title: Option[String] = None,
	  /** Specifies the channel description. */
		description: Option[String] = None,
	  /** The country of the channel. */
		country: Option[String] = None,
	  /** Title for the featured channels tab. */
		featuredChannelsTitle: Option[String] = None,
	  /** Whether user-submitted comments left on the channel page need to be approved by the channel owner to be publicly visible. */
		moderateComments: Option[Boolean] = None,
	  /** Which content tab users should see when viewing the channel. */
		defaultTab: Option[String] = None,
	  /** Whether related channels should be proposed. */
		showRelatedChannels: Option[Boolean] = None,
	  /** Whether the tab to browse the videos should be displayed. */
		showBrowseView: Option[Boolean] = None,
	  /** The ID for a Google Analytics account to track and measure traffic to the channels. */
		trackingAnalyticsAccountId: Option[String] = None
	)
	
	case class LiveChatSuperChatDetails(
	  /** The tier in which the amount belongs. Lower amounts belong to lower tiers. The lowest tier is 1. */
		tier: Option[Int] = None,
	  /** A rendered string that displays the fund amount and currency to the user. */
		amountDisplayString: Option[String] = None,
	  /** The amount purchased by the user, in micros (1,750,000 micros = 1.75). */
		amountMicros: Option[String] = None,
	  /** The currency in which the purchase was made. */
		currency: Option[String] = None,
	  /** The comment added by the user to this Super Chat event. */
		userComment: Option[String] = None
	)
	
	case class LiveChatBan(
	  /** The ID that YouTube assigns to uniquely identify the ban. */
		id: Option[String] = None,
	  /** The `snippet` object contains basic details about the ban. */
		snippet: Option[Schema.LiveChatBanSnippet] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"youtube#liveChatBan"`. */
		kind: Option[String] = Some("""youtube#liveChatBan"""),
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class SearchResult(
	  /** The id object contains information that can be used to uniquely identify the resource that matches the search request. */
		id: Option[Schema.ResourceId] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#searchResult". */
		kind: Option[String] = Some("""youtube#searchResult"""),
	  /** The snippet object contains basic details about a search result, such as its title or description. For example, if the search result is a video, then the title will be the video's title and the description will be the video's description. */
		snippet: Option[Schema.SearchResultSnippet] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class VideoAbuseReportReasonSnippet(
	  /** The secondary reasons associated with this reason, if any are available. (There might be 0 or more.) */
		secondaryReasons: Option[List[Schema.VideoAbuseReportSecondaryReason]] = None,
	  /** The localized label belonging to this abuse report reason. */
		label: Option[String] = None
	)
	
	object PlaylistItemStatus {
		enum PrivacyStatusEnum extends Enum[PrivacyStatusEnum] { case `public`, unlisted, `private` }
	}
	case class PlaylistItemStatus(
	  /** This resource's privacy status. */
		privacyStatus: Option[Schema.PlaylistItemStatus.PrivacyStatusEnum] = None
	)
	
	case class ChannelContentOwnerDetails(
	  /** The date and time when the channel was linked to the content owner. */
		timeLinked: Option[String] = None,
	  /** The ID of the content owner linked to the channel. */
		contentOwner: Option[String] = None
	)
	
	case class PlaylistLocalization(
	  /** The localized strings for playlist's description. */
		description: Option[String] = None,
	  /** The localized strings for playlist's title. */
		title: Option[String] = None
	)
	
	case class MemberListResponse(
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#memberListResponse". */
		kind: Option[String] = Some("""youtube#memberListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** A list of members that match the request criteria. */
		items: Option[List[Schema.Member]] = None,
		pageInfo: Option[Schema.PageInfo] = None
	)
	
	case class LiveChatFanFundingEventDetails(
	  /** The currency in which the fund was made. */
		currency: Option[String] = None,
	  /** The amount of the fund. */
		amountMicros: Option[String] = None,
	  /** A rendered string that displays the fund amount and currency to the user. */
		amountDisplayString: Option[String] = None,
	  /** The comment added by the user to this fan funding event. */
		userComment: Option[String] = None
	)
	
	case class LiveBroadcastSnippet(
	  /** The date and time that the broadcast actually started. This information is only available once the broadcast's state is live. */
		actualStartTime: Option[String] = None,
	  /** A map of thumbnail images associated with the broadcast. For each nested object in this object, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The broadcast's title. Note that the broadcast represents exactly one YouTube video. You can set this field by modifying the broadcast resource or by setting the title field of the corresponding video resource. */
		title: Option[String] = None,
	  /** Indicates whether this broadcast is the default broadcast. Internal only. */
		isDefaultBroadcast: Option[Boolean] = None,
	  /** The date and time that the broadcast is scheduled to end. */
		scheduledEndTime: Option[String] = None,
	  /** The date and time that the broadcast actually ended. This information is only available once the broadcast's state is complete. */
		actualEndTime: Option[String] = None,
	  /** The date and time that the broadcast was added to YouTube's live broadcast schedule. */
		publishedAt: Option[String] = None,
	  /** The date and time that the broadcast is scheduled to start. */
		scheduledStartTime: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the channel that is publishing the broadcast. */
		channelId: Option[String] = None,
	  /** The id of the live chat for this broadcast. */
		liveChatId: Option[String] = None,
	  /** The broadcast's description. As with the title, you can set this field by modifying the broadcast resource or by setting the description field of the corresponding video resource. */
		description: Option[String] = None
	)
	
	case class ThirdPartyLinkListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#thirdPartyLinkListResponse". */
		kind: Option[String] = Some("""youtube#thirdPartyLinkListResponse"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
		items: Option[List[Schema.ThirdPartyLink]] = None
	)
	
	case class SubscriptionSubscriberSnippet(
	  /** Thumbnails for this subscriber. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The channel ID of the subscriber. */
		channelId: Option[String] = None,
	  /** The description of the subscriber. */
		description: Option[String] = None,
	  /** The title of the subscriber. */
		title: Option[String] = None
	)
	
	case class VideoPaidProductPlacementDetails(
	  /** This boolean represents whether the video contains Paid Product Placement, Studio equivalent: https://screenshot.googleplex.com/4Me79DE6AfT2ktp.png */
		hasPaidProductPlacement: Option[Boolean] = None
	)
	
	case class I18nLanguage(
	  /** The snippet object contains basic details about the i18n language, such as language code and human-readable name. */
		snippet: Option[Schema.I18nLanguageSnippet] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#i18nLanguage". */
		kind: Option[String] = Some("""youtube#i18nLanguage"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the i18n language. */
		id: Option[String] = None
	)
	
	case class PlaylistImageListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlistImageListResponse". */
		kind: Option[String] = Some("""youtube#playlistImageListResponse"""),
		items: Option[List[Schema.PlaylistImage]] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None
	)
	
	case class ThumbnailDetails(
	  /** The high quality image for this resource. */
		high: Option[Schema.Thumbnail] = None,
	  /** The standard quality image for this resource. */
		standard: Option[Schema.Thumbnail] = None,
	  /** The maximum resolution quality image for this resource. */
		maxres: Option[Schema.Thumbnail] = None,
	  /** The medium quality image for this resource. */
		medium: Option[Schema.Thumbnail] = None,
	  /** The default image for this resource. */
		default: Option[Schema.Thumbnail] = None
	)
	
	case class ActivityContentDetailsLike(
	  /** The resourceId object contains information that identifies the rated resource. */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	case class LiveBroadcastListResponse(
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveBroadcastListResponse". */
		kind: Option[String] = Some("""youtube#liveBroadcastListResponse"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** A list of broadcasts that match the request criteria. */
		items: Option[List[Schema.LiveBroadcast]] = None
	)
	
	case class VideoCategorySnippet(
	  /** The YouTube channel that created the video category. */
		channelId: Option[String] = Some("""UCBR8-60-B28hp2BmDPdntcQ"""),
		assignable: Option[Boolean] = None,
	  /** The video category's title. */
		title: Option[String] = None
	)
	
	case class SuperChatEvent(
	  /** The ID that YouTube assigns to uniquely identify the Super Chat event. */
		id: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"youtube#superChatEvent"`. */
		kind: Option[String] = Some("""youtube#superChatEvent"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The `snippet` object contains basic details about the Super Chat event. */
		snippet: Option[Schema.SuperChatEventSnippet] = None
	)
	
	object PlaylistStatus {
		enum PrivacyStatusEnum extends Enum[PrivacyStatusEnum] { case `public`, unlisted, `private` }
		enum PodcastStatusEnum extends Enum[PodcastStatusEnum] { case enabled, disabled }
	}
	case class PlaylistStatus(
	  /** The playlist's privacy status. */
		privacyStatus: Option[Schema.PlaylistStatus.PrivacyStatusEnum] = None,
	  /** The playlist's podcast status. */
		podcastStatus: Option[Schema.PlaylistStatus.PodcastStatusEnum] = None
	)
	
	object ActivityContentDetailsPromotedItem {
		enum CtaTypeEnum extends Enum[CtaTypeEnum] { case ctaTypeUnspecified, visitAdvertiserSite }
	}
	case class ActivityContentDetailsPromotedItem(
	  /** The URL the client should ping to indicate that the user was shown this promoted item. */
		creativeViewUrl: Option[String] = None,
	  /** The URL the client should ping to indicate that the user clicked through on this promoted item. */
		clickTrackingUrl: Option[String] = None,
	  /** The text description to accompany the promoted item. */
		descriptionText: Option[String] = None,
	  /** The list of impression URLs. The client should ping all of these URLs to indicate that the user was shown this promoted item. */
		impressionUrl: Option[List[String]] = None,
	  /** The URL the client should direct the user to, if the user chooses to visit the advertiser's website. */
		destinationUrl: Option[String] = None,
	  /** The type of call-to-action, a message to the user indicating action that can be taken. */
		ctaType: Option[Schema.ActivityContentDetailsPromotedItem.CtaTypeEnum] = None,
	  /** The URL the client should fetch to request a promoted item. */
		adTag: Option[String] = None,
	  /** The custom call-to-action button text. If specified, it will override the default button text for the cta_type. */
		customCtaButtonText: Option[String] = None,
	  /** The list of forecasting URLs. The client should ping all of these URLs when a promoted item is not available, to indicate that a promoted item could have been shown. */
		forecastingUrl: Option[List[String]] = None,
	  /** The ID that YouTube uses to uniquely identify the promoted video. */
		videoId: Option[String] = None
	)
	
	case class LiveBroadcast(
	  /** The statistics object contains info about the event's current stats. These include concurrent viewers and total chat count. Statistics can change (in either direction) during the lifetime of an event. Statistics are only returned while the event is live. */
		statistics: Option[Schema.LiveBroadcastStatistics] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveBroadcast". */
		kind: Option[String] = Some("""youtube#liveBroadcast"""),
	  /** The snippet object contains basic details about the event, including its title, description, start time, and end time. */
		snippet: Option[Schema.LiveBroadcastSnippet] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The ID that YouTube assigns to uniquely identify the broadcast. */
		id: Option[String] = None,
	  /** The contentDetails object contains information about the event's video content, such as whether the content can be shown in an embedded video player or if it will be archived and therefore available for viewing after the event has concluded. */
		contentDetails: Option[Schema.LiveBroadcastContentDetails] = None,
	  /** The status object contains information about the event's status. */
		status: Option[Schema.LiveBroadcastStatus] = None,
	  /** The monetizationDetails object contains information about the event's monetization details. */
		monetizationDetails: Option[Schema.LiveBroadcastMonetizationDetails] = None
	)
	
	case class ChannelSnippet(
	  /** The country of the channel. */
		country: Option[String] = None,
	  /** The custom url of the channel. */
		customUrl: Option[String] = None,
	  /** The language of the channel's default title and description. */
		defaultLanguage: Option[String] = None,
	  /** The description of the channel. */
		description: Option[String] = None,
	  /** The channel's title. */
		title: Option[String] = None,
	  /** A map of thumbnail images associated with the channel. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. When displaying thumbnails in your application, make sure that your code uses the image URLs exactly as they are returned in API responses. For example, your application should not use the http domain instead of the https domain in a URL returned in an API response. Beginning in July 2018, channel thumbnail URLs will only be available in the https domain, which is how the URLs appear in API responses. After that time, you might see broken images in your application if it tries to load YouTube images from the http domain. Thumbnail images might be empty for newly created channels and might take up to one day to populate. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** Localized title and description, read-only. */
		localized: Option[Schema.ChannelLocalization] = None,
	  /** The date and time that the channel was created. */
		publishedAt: Option[String] = None
	)
	
	case class I18nLanguageListResponse(
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** A list of supported i18n languages. In this map, the i18n language ID is the map key, and its value is the corresponding i18nLanguage resource. */
		items: Option[List[Schema.I18nLanguage]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#i18nLanguageListResponse". */
		kind: Option[String] = Some("""youtube#i18nLanguageListResponse"""),
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class ChannelStatistics(
	  /** The number of times the channel has been viewed. */
		viewCount: Option[String] = None,
	  /** Whether or not the number of subscribers is shown for this user. */
		hiddenSubscriberCount: Option[Boolean] = None,
	  /** The number of videos uploaded to the channel. */
		videoCount: Option[String] = None,
	  /** The number of subscribers that the channel has. */
		subscriberCount: Option[String] = None,
	  /** The number of comments for the channel. */
		commentCount: Option[String] = None
	)
	
	case class Playlist(
	  /** The player object contains information that you would use to play the playlist in an embedded player. */
		player: Option[Schema.PlaylistPlayer] = None,
	  /** Localizations for different languages */
		localizations: Option[Map[String, Schema.PlaylistLocalization]] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the playlist. */
		id: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlist". */
		kind: Option[String] = Some("""youtube#playlist"""),
	  /** The snippet object contains basic details about the playlist, such as its title and description. */
		snippet: Option[Schema.PlaylistSnippet] = None,
	  /** The status object contains status information for the playlist. */
		status: Option[Schema.PlaylistStatus] = None,
	  /** The contentDetails object contains information like video count. */
		contentDetails: Option[Schema.PlaylistContentDetails] = None
	)
	
	case class LevelDetails(
	  /** The name that should be used when referring to this level. */
		displayName: Option[String] = None
	)
	
	case class VideoContentDetailsRegionRestriction(
	  /** A list of region codes that identify countries where the video is viewable. If this property is present and a country is not listed in its value, then the video is blocked from appearing in that country. If this property is present and contains an empty list, the video is blocked in all countries. */
		allowed: Option[List[String]] = None,
	  /** A list of region codes that identify countries where the video is blocked. If this property is present and a country is not listed in its value, then the video is viewable in that country. If this property is present and contains an empty list, the video is viewable in all countries. */
		blocked: Option[List[String]] = None
	)
	
	case class VideoTopicDetails(
	  /** A list of Wikipedia URLs that provide a high-level description of the video's content. */
		topicCategories: Option[List[String]] = None,
	  /** Similar to topic_id, except that these topics are merely relevant to the video. These are topics that may be mentioned in, or appear in the video. You can retrieve information about each topic using Freebase Topic API. */
		relevantTopicIds: Option[List[String]] = None,
	  /** A list of Freebase topic IDs that are centrally associated with the video. These are topics that are centrally featured in the video, and it can be said that the video is mainly about each of these. You can retrieve information about each topic using the < a href="http://wiki.freebase.com/wiki/Topic_API">Freebase Topic API. */
		topicIds: Option[List[String]] = None
	)
	
	case class SuperChatEventSnippet(
	  /** The text contents of the comment left by the user. */
		commentText: Option[String] = None,
	  /** True if this event is a Super Sticker event. */
		isSuperStickerEvent: Option[Boolean] = None,
	  /** The currency in which the purchase was made. ISO 4217. */
		currency: Option[String] = None,
	  /** The purchase amount, in micros of the purchase currency. e.g., 1 is represented as 1000000. */
		amountMicros: Option[String] = None,
	  /** A rendered string that displays the purchase amount and currency (e.g., "$1.00"). The string is rendered for the given language. */
		displayString: Option[String] = None,
	  /** The date and time when the event occurred. */
		createdAt: Option[String] = None,
	  /** If this event is a Super Sticker event, this field will contain metadata about the Super Sticker. */
		superStickerMetadata: Option[Schema.SuperStickerMetadata] = None,
	  /** Details about the supporter. */
		supporterDetails: Option[Schema.ChannelProfileDetails] = None,
	  /** The tier for the paid message, which is based on the amount of money spent to purchase the message. */
		messageType: Option[Int] = None,
	  /** Channel id where the event occurred. */
		channelId: Option[String] = None
	)
	
	object ChannelContentDetails {
		case class RelatedPlaylistsItem(
		  /** The ID of the playlist that contains the channel"s favorite videos. Use the playlistItems.insert and playlistItems.delete to add or remove items from that list. */
			favorites: Option[String] = None,
		  /** The ID of the playlist that contains the channel"s uploaded videos. Use the videos.insert method to upload new videos and the videos.delete method to delete previously uploaded videos. */
			uploads: Option[String] = None,
		  /** The ID of the playlist that contains the channel"s liked videos. Use the playlistItems.insert and playlistItems.delete to add or remove items from that list. */
			likes: Option[String] = None,
		  /** The ID of the playlist that contains the channel"s watch later playlist. Use the playlistItems.insert and playlistItems.delete to add or remove items from that list. */
			watchLater: Option[String] = None,
		  /** The ID of the playlist that contains the channel"s watch history. Use the playlistItems.insert and playlistItems.delete to add or remove items from that list. */
			watchHistory: Option[String] = None
		)
	}
	case class ChannelContentDetails(
		relatedPlaylists: Option[Schema.ChannelContentDetails.RelatedPlaylistsItem] = None
	)
	
	case class LiveChatMessageRetractedDetails(
		retractedMessageId: Option[String] = None
	)
	
	case class ChannelSectionLocalization(
	  /** The localized strings for channel section's title. */
		title: Option[String] = None
	)
	
	case class RelatedEntity(
		entity: Option[Schema.Entity] = None
	)
	
	case class LiveChatMemberMilestoneChatDetails(
	  /** The total amount of months (rounded up) the viewer has been a member that granted them this Member Milestone Chat. This is the same number of months as is being displayed to YouTube users. */
		memberMonth: Option[Int] = None,
	  /** The comment added by the member to this Member Milestone Chat. This field is empty for messages without a comment from the member. */
		userComment: Option[String] = None,
	  /** The name of the Level at which the viever is a member. The Level names are defined by the YouTube channel offering the Membership. In some situations this field isn't filled. */
		memberLevelName: Option[String] = None
	)
	
	case class PlaylistItemContentDetails(
	  /** A user-generated note for this item. */
		note: Option[String] = None,
	  /** The time, measured in seconds from the start of the video, when the video should stop playing. (The playlist owner can specify the times when the video should start and stop playing when the video is played in the context of the playlist.) By default, assume that the video.endTime is the end of the video. */
		endAt: Option[String] = None,
	  /** The time, measured in seconds from the start of the video, when the video should start playing. (The playlist owner can specify the times when the video should start and stop playing when the video is played in the context of the playlist.) The default value is 0. */
		startAt: Option[String] = None,
	  /** The date and time that the video was published to YouTube. */
		videoPublishedAt: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify a video. To retrieve the video resource, set the id query parameter to this value in your API request. */
		videoId: Option[String] = None
	)
	
	case class SubscriptionSnippet(
	  /** The subscription's title. */
		title: Option[String] = None,
	  /** The id object contains information about the channel that the user subscribed to. */
		resourceId: Option[Schema.ResourceId] = None,
	  /** A map of thumbnail images associated with the video. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The ID that YouTube uses to uniquely identify the subscriber's channel. */
		channelId: Option[String] = None,
	  /** The subscription's details. */
		description: Option[String] = None,
	  /** The date and time that the subscription was created. */
		publishedAt: Option[String] = None
	)
	
	case class ChannelSectionContentDetails(
	  /** The channel ids for type multiple_channels. */
		channels: Option[List[String]] = None,
	  /** The playlist ids for type single_playlist and multiple_playlists. For singlePlaylist, only one playlistId is allowed. */
		playlists: Option[List[String]] = None
	)
	
	case class I18nLanguageSnippet(
	  /** The human-readable name of the language in the language itself. */
		name: Option[String] = None,
	  /** A short BCP-47 code that uniquely identifies a language. */
		hl: Option[String] = None
	)
	
	case class LiveStreamSnippet(
	  /** The ID that YouTube uses to uniquely identify the channel that is transmitting the stream. */
		channelId: Option[String] = None,
		isDefaultStream: Option[Boolean] = None,
	  /** The date and time that the stream was created. */
		publishedAt: Option[String] = None,
	  /** The stream's description. The value cannot be longer than 10000 characters. */
		description: Option[String] = None,
	  /** The stream's title. The value must be between 1 and 128 characters long. */
		title: Option[String] = None
	)
	
	case class VideoRecordingDetails(
	  /** The text description of the location where the video was recorded. */
		locationDescription: Option[String] = None,
	  /** The date and time when the video was recorded. */
		recordingDate: Option[String] = None,
	  /** The geolocation information associated with the video. */
		location: Option[Schema.GeoPoint] = None
	)
	
	object ActivityContentDetailsSocial {
		enum TypeEnum extends Enum[TypeEnum] { case unspecified, googlePlus, facebook, twitter }
	}
	case class ActivityContentDetailsSocial(
	  /** The author of the social network post. */
		author: Option[String] = None,
	  /** The name of the social network. */
		`type`: Option[Schema.ActivityContentDetailsSocial.TypeEnum] = None,
	  /** The resourceId object encapsulates information that identifies the resource associated with a social network post. */
		resourceId: Option[Schema.ResourceId] = None,
	  /** The URL of the social network post. */
		referenceUrl: Option[String] = None,
	  /** An image of the post's author. */
		imageUrl: Option[String] = None
	)
	
	object ThirdPartyLinkSnippet {
		enum TypeEnum extends Enum[TypeEnum] { case linkUnspecified, channelToStoreLink }
	}
	case class ThirdPartyLinkSnippet(
	  /** Information specific to a link between a channel and a store on a merchandising platform. */
		channelToStoreLink: Option[Schema.ChannelToStoreLinkDetails] = None,
	  /** Type of the link named after the entities that are being linked. */
		`type`: Option[Schema.ThirdPartyLinkSnippet.TypeEnum] = None
	)
	
	case class IngestionInfo(
	  /** The stream name that YouTube assigns to the video stream. */
		streamName: Option[String] = None,
	  /** This ingestion url may be used instead of ingestionAddress in order to stream via RTMPS. Not applicable to non-RTMP streams. */
		rtmpsIngestionAddress: Option[String] = None,
	  /** This ingestion url may be used instead of backupIngestionAddress in order to stream via RTMPS. Not applicable to non-RTMP streams. */
		rtmpsBackupIngestionAddress: Option[String] = None,
	  /** The primary ingestion URL that you should use to stream video to YouTube. You must stream video to this URL. Depending on which application or tool you use to encode your video stream, you may need to enter the stream URL and stream name separately or you may need to concatenate them in the following format: &#42;STREAM_URL/STREAM_NAME&#42;  */
		ingestionAddress: Option[String] = None,
	  /** The backup ingestion URL that you should use to stream video to YouTube. You have the option of simultaneously streaming the content that you are sending to the ingestionAddress to this URL. */
		backupIngestionAddress: Option[String] = None
	)
	
	case class ActivityContentDetails(
	  /** The channelItem object contains details about a resource which was added to a channel. This property is only present if the snippet.type is channelItem. */
		channelItem: Option[Schema.ActivityContentDetailsChannelItem] = None,
	  /** The favorite object contains information about a video that was marked as a favorite video. This property is only present if the snippet.type is favorite. */
		favorite: Option[Schema.ActivityContentDetailsFavorite] = None,
	  /** The subscription object contains information about a channel that a user subscribed to. This property is only present if the snippet.type is subscription. */
		subscription: Option[Schema.ActivityContentDetailsSubscription] = None,
	  /** The comment object contains information about a resource that received a comment. This property is only present if the snippet.type is comment. */
		comment: Option[Schema.ActivityContentDetailsComment] = None,
	  /** The promotedItem object contains details about a resource which is being promoted. This property is only present if the snippet.type is promotedItem. */
		promotedItem: Option[Schema.ActivityContentDetailsPromotedItem] = None,
	  /** The bulletin object contains details about a channel bulletin post. This object is only present if the snippet.type is bulletin. */
		bulletin: Option[Schema.ActivityContentDetailsBulletin] = None,
	  /** The like object contains information about a resource that received a positive (like) rating. This property is only present if the snippet.type is like. */
		like: Option[Schema.ActivityContentDetailsLike] = None,
	  /** The recommendation object contains information about a recommended resource. This property is only present if the snippet.type is recommendation. */
		recommendation: Option[Schema.ActivityContentDetailsRecommendation] = None,
	  /** The social object contains details about a social network post. This property is only present if the snippet.type is social. */
		social: Option[Schema.ActivityContentDetailsSocial] = None,
	  /** The upload object contains information about the uploaded video. This property is only present if the snippet.type is upload. */
		upload: Option[Schema.ActivityContentDetailsUpload] = None,
	  /** The playlistItem object contains information about a new playlist item. This property is only present if the snippet.type is playlistItem. */
		playlistItem: Option[Schema.ActivityContentDetailsPlaylistItem] = None
	)
	
	case class VideoAbuseReportReason(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"youtube#videoAbuseReportReason"`. */
		kind: Option[String] = Some("""youtube#videoAbuseReportReason"""),
	  /** The `snippet` object contains basic details about the abuse report reason. */
		snippet: Option[Schema.VideoAbuseReportReasonSnippet] = None,
	  /** The ID of this abuse report reason. */
		id: Option[String] = None
	)
	
	object CuepointSchedule {
		enum ScheduleStrategyEnum extends Enum[ScheduleStrategyEnum] { case scheduleStrategyUnspecified, concurrent, nonConcurrent }
	}
	case class CuepointSchedule(
	  /** This field is semantically required. If it is set false or not set, other fields in this message will be ignored. */
		enabled: Option[Boolean] = None,
	  /** If set, automatic cuepoint insertion is paused until this timestamp ("No Ad Zone"). The value is specified in ISO 8601 format. */
		pauseAdsUntil: Option[String] = None,
	  /** Interval frequency in seconds that api uses to insert cuepoints automatically. */
		repeatIntervalSecs: Option[Int] = None,
	  /** The strategy to use when scheduling cuepoints. */
		scheduleStrategy: Option[Schema.CuepointSchedule.ScheduleStrategyEnum] = None
	)
	
	case class Comment(
	  /** The snippet object contains basic details about the comment. */
		snippet: Option[Schema.CommentSnippet] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#comment". */
		kind: Option[String] = Some("""youtube#comment"""),
	  /** The ID that YouTube uses to uniquely identify the comment. */
		id: Option[String] = None
	)
	
	case class ActivityContentDetailsPlaylistItem(
	  /** The resourceId object contains information about the resource that was added to the playlist. */
		resourceId: Option[Schema.ResourceId] = None,
	  /** The value that YouTube uses to uniquely identify the playlist. */
		playlistId: Option[String] = None,
	  /** ID of the item within the playlist. */
		playlistItemId: Option[String] = None
	)
	
	object PlaylistImageSnippet {
		enum TypeEnum extends Enum[TypeEnum] { case hero }
	}
	case class PlaylistImageSnippet(
	  /** The image height. */
		height: Option[Int] = None,
	  /** The image width. */
		width: Option[Int] = None,
	  /** The Playlist ID of the playlist this image is associated with. */
		playlistId: Option[String] = None,
	  /** The image type. */
		`type`: Option[Schema.PlaylistImageSnippet.TypeEnum] = None
	)
	
	case class ImageSettings(
	  /** Banner image. Mobile size (640x175). */
		bannerMobileImageUrl: Option[String] = None,
	  /** The image map script for the large banner image. */
		largeBrandedBannerImageImapScript: Option[Schema.LocalizedProperty] = None,
	  /** Banner image. TV size medium resolution (1280x720). */
		bannerTvMediumImageUrl: Option[String] = None,
	  /** Banner image. Tablet size (1707x283). */
		bannerTabletImageUrl: Option[String] = None,
		watchIconImageUrl: Option[String] = None,
	  /** Banner image. Mobile size low resolution (320x88). */
		bannerMobileLowImageUrl: Option[String] = None,
	  /** The image map script for the small banner image. */
		smallBrandedBannerImageImapScript: Option[Schema.LocalizedProperty] = None,
	  /** The URL for the background image shown on the video watch page. The image should be 1200px by 615px, with a maximum file size of 128k. */
		backgroundImageUrl: Option[Schema.LocalizedProperty] = None,
	  /** Banner image. Tablet size low resolution (1138x188). */
		bannerTabletLowImageUrl: Option[String] = None,
	  /** The URL for the 640px by 70px banner image that appears below the video player in the default view of the video watch page. The URL for the image that appears above the top-left corner of the video player. This is a 25-pixel-high image with a flexible width that cannot exceed 170 pixels. */
		smallBrandedBannerImageUrl: Option[Schema.LocalizedProperty] = None,
	  /** Banner image. TV size extra high resolution (2120x1192). */
		bannerTvImageUrl: Option[String] = None,
	  /** Banner image. Mobile size medium/high resolution (960x263). */
		bannerMobileMediumHdImageUrl: Option[String] = None,
	  /** Banner image. Desktop size (1060x175). */
		bannerImageUrl: Option[String] = None,
	  /** Banner image. TV size low resolution (854x480). */
		bannerTvLowImageUrl: Option[String] = None,
	  /** Banner image. Mobile size high resolution (1440x395). */
		bannerMobileExtraHdImageUrl: Option[String] = None,
	  /** The URL for the 854px by 70px image that appears below the video player in the expanded video view of the video watch page. */
		largeBrandedBannerImageUrl: Option[Schema.LocalizedProperty] = None,
	  /** This is generated when a ChannelBanner.Insert request has succeeded for the given channel. */
		bannerExternalUrl: Option[String] = None,
	  /** Banner image. Mobile size high resolution (1280x360). */
		bannerMobileHdImageUrl: Option[String] = None,
	  /** Banner image. Tablet size high resolution (2276x377). */
		bannerTabletHdImageUrl: Option[String] = None,
	  /** The URL for a 1px by 1px tracking pixel that can be used to collect statistics for views of the channel or video pages. */
		trackingImageUrl: Option[String] = None,
	  /** Banner image. Tablet size extra high resolution (2560x424). */
		bannerTabletExtraHdImageUrl: Option[String] = None,
	  /** Banner image. TV size high resolution (1920x1080). */
		bannerTvHighImageUrl: Option[String] = None
	)
	
	case class AbuseType(
		id: Option[String] = None
	)
	
	case class LiveChatSuperStickerDetails(
	  /** A rendered string that displays the fund amount and currency to the user. */
		amountDisplayString: Option[String] = None,
	  /** Information about the Super Sticker. */
		superStickerMetadata: Option[Schema.SuperStickerMetadata] = None,
	  /** The amount purchased by the user, in micros (1,750,000 micros = 1.75). */
		amountMicros: Option[String] = None,
	  /** The currency in which the purchase was made. */
		currency: Option[String] = None,
	  /** The tier in which the amount belongs. Lower amounts belong to lower tiers. The lowest tier is 1. */
		tier: Option[Int] = None
	)
	
	case class ChannelToStoreLinkDetails(
	  /** Name of the store. */
		storeName: Option[String] = None,
	  /** Landing page of the store. */
		storeUrl: Option[String] = None,
	  /** Google Merchant Center id of the store. */
		merchantId: Option[String] = None,
	  /** Information specific to billing (read-only). */
		billingDetails: Option[Schema.ChannelToStoreLinkDetailsBillingDetails] = None
	)
	
	case class ChannelBannerResource(
	  /** The URL of this banner image. */
		url: Option[String] = None,
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#channelBannerResource". */
		kind: Option[String] = Some("""youtube#channelBannerResource""")
	)
	
	case class LiveChatModerator(
	  /** The ID that YouTube assigns to uniquely identify the moderator. */
		id: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveChatModerator". */
		kind: Option[String] = Some("""youtube#liveChatModerator"""),
	  /** The snippet object contains basic details about the moderator. */
		snippet: Option[Schema.LiveChatModeratorSnippet] = None
	)
	
	object ThirdPartyLinkStatus {
		enum LinkStatusEnum extends Enum[LinkStatusEnum] { case unknown, failed, pending, linked }
	}
	case class ThirdPartyLinkStatus(
		linkStatus: Option[Schema.ThirdPartyLinkStatus.LinkStatusEnum] = None
	)
	
	object LiveChatMessageSnippet {
		enum TypeEnum extends Enum[TypeEnum] { case invalidType, textMessageEvent, tombstone, fanFundingEvent, chatEndedEvent, sponsorOnlyModeStartedEvent, sponsorOnlyModeEndedEvent, newSponsorEvent, memberMilestoneChatEvent, membershipGiftingEvent, giftMembershipReceivedEvent, messageDeletedEvent, messageRetractedEvent, userBannedEvent, superChatEvent, superStickerEvent, pollEvent }
	}
	case class LiveChatMessageSnippet(
		messageDeletedDetails: Option[Schema.LiveChatMessageDeletedDetails] = None,
	  /** Details about the poll event, this is only set if the type is 'pollEvent'. */
		pollDetails: Option[Schema.LiveChatPollDetails] = None,
	  /** Details about the funding event, this is only set if the type is 'fanFundingEvent'. */
		fanFundingEventDetails: Option[Schema.LiveChatFanFundingEventDetails] = None,
	  /** Details about the Super Chat event, this is only set if the type is 'superChatEvent'. */
		superChatDetails: Option[Schema.LiveChatSuperChatDetails] = None,
	  /** Details about the text message, this is only set if the type is 'textMessageEvent'. */
		textMessageDetails: Option[Schema.LiveChatTextMessageDetails] = None,
		liveChatId: Option[String] = None,
	  /** Contains a string that can be displayed to the user. If this field is not present the message is silent, at the moment only messages of type TOMBSTONE and CHAT_ENDED_EVENT are silent. */
		displayMessage: Option[String] = None,
	  /** The type of message, this will always be present, it determines the contents of the message as well as which fields will be present. */
		`type`: Option[Schema.LiveChatMessageSnippet.TypeEnum] = None,
	  /** Details about the Member Milestone Chat event, this is only set if the type is 'memberMilestoneChatEvent'. */
		memberMilestoneChatDetails: Option[Schema.LiveChatMemberMilestoneChatDetails] = None,
	  /** Details about the Membership Gifting event, this is only set if the type is 'membershipGiftingEvent'. */
		membershipGiftingDetails: Option[Schema.LiveChatMembershipGiftingDetails] = None,
	  /** Details about the Gift Membership Received event, this is only set if the type is 'giftMembershipReceivedEvent'. */
		giftMembershipReceivedDetails: Option[Schema.LiveChatGiftMembershipReceivedDetails] = None,
	  /** The date and time when the message was orignally published. */
		publishedAt: Option[String] = None,
		userBannedDetails: Option[Schema.LiveChatUserBannedMessageDetails] = None,
	  /** The ID of the user that authored this message, this field is not always filled. textMessageEvent - the user that wrote the message fanFundingEvent - the user that funded the broadcast newSponsorEvent - the user that just became a sponsor memberMilestoneChatEvent - the member that sent the message membershipGiftingEvent - the user that made the purchase giftMembershipReceivedEvent - the user that received the gift membership messageDeletedEvent - the moderator that took the action messageRetractedEvent - the author that retracted their message userBannedEvent - the moderator that took the action superChatEvent - the user that made the purchase superStickerEvent - the user that made the purchase pollEvent - the user that created the poll */
		authorChannelId: Option[String] = None,
	  /** Details about the New Member Announcement event, this is only set if the type is 'newSponsorEvent'. Please note that "member" is the new term for "sponsor". */
		newSponsorDetails: Option[Schema.LiveChatNewSponsorDetails] = None,
		messageRetractedDetails: Option[Schema.LiveChatMessageRetractedDetails] = None,
	  /** Whether the message has display content that should be displayed to users. */
		hasDisplayContent: Option[Boolean] = None,
	  /** Details about the Super Sticker event, this is only set if the type is 'superStickerEvent'. */
		superStickerDetails: Option[Schema.LiveChatSuperStickerDetails] = None
	)
	
	object CaptionSnippet {
		enum StatusEnum extends Enum[StatusEnum] { case serving, syncing, failed }
		enum AudioTrackTypeEnum extends Enum[AudioTrackTypeEnum] { case unknown, primary, commentary, descriptive }
		enum FailureReasonEnum extends Enum[FailureReasonEnum] { case unknownFormat, unsupportedFormat, processingFailed }
		enum TrackKindEnum extends Enum[TrackKindEnum] { case standard, ASR, forced }
	}
	case class CaptionSnippet(
	  /** The date and time when the caption track was last updated. */
		lastUpdated: Option[String] = None,
	  /** The language of the caption track. The property value is a BCP-47 language tag. */
		language: Option[String] = None,
	  /** Indicates whether the caption track is a draft. If the value is true, then the track is not publicly visible. The default value is false. @mutable youtube.captions.insert youtube.captions.update */
		isDraft: Option[Boolean] = None,
	  /** Indicates whether the track contains closed captions for the deaf and hard of hearing. The default value is false. */
		isCC: Option[Boolean] = None,
	  /** The caption track's status. */
		status: Option[Schema.CaptionSnippet.StatusEnum] = None,
	  /** Indicates whether YouTube synchronized the caption track to the audio track in the video. The value will be true if a sync was explicitly requested when the caption track was uploaded. For example, when calling the captions.insert or captions.update methods, you can set the sync parameter to true to instruct YouTube to sync the uploaded track to the video. If the value is false, YouTube uses the time codes in the uploaded caption track to determine when to display captions. */
		isAutoSynced: Option[Boolean] = None,
	  /** Indicates whether the caption track uses large text for the vision-impaired. The default value is false. */
		isLarge: Option[Boolean] = None,
	  /** Indicates whether caption track is formatted for "easy reader," meaning it is at a third-grade level for language learners. The default value is false. */
		isEasyReader: Option[Boolean] = None,
	  /** The ID that YouTube uses to uniquely identify the video associated with the caption track. @mutable youtube.captions.insert */
		videoId: Option[String] = None,
	  /** The type of audio track associated with the caption track. */
		audioTrackType: Option[Schema.CaptionSnippet.AudioTrackTypeEnum] = None,
	  /** The reason that YouTube failed to process the caption track. This property is only present if the state property's value is failed. */
		failureReason: Option[Schema.CaptionSnippet.FailureReasonEnum] = None,
	  /** The name of the caption track. The name is intended to be visible to the user as an option during playback. */
		name: Option[String] = None,
	  /** The caption track's type. */
		trackKind: Option[Schema.CaptionSnippet.TrackKindEnum] = None
	)
	
	object LiveChatPollDetails {
		enum StatusEnum extends Enum[StatusEnum] { case unknown, active, closed }
	}
	case class LiveChatPollDetails(
		status: Option[Schema.LiveChatPollDetails.StatusEnum] = None,
		metadata: Option[Schema.LiveChatPollDetailsPollMetadata] = None
	)
	
	case class TestItemTestItemSnippet(
	
	)
	
	case class Entity(
		typeId: Option[String] = None,
		id: Option[String] = None,
		url: Option[String] = None
	)
	
	object CdnSettings {
		enum FrameRateEnum extends Enum[FrameRateEnum] { case `30fps`, `60fps`, variable }
		enum ResolutionEnum extends Enum[ResolutionEnum] { case `240p`, `360p`, `480p`, `720p`, `1080p`, `1440p`, `2160p`, variable }
		enum IngestionTypeEnum extends Enum[IngestionTypeEnum] { case rtmp, dash, webrtc, hls }
	}
	case class CdnSettings(
	  /** The frame rate of the inbound video data. */
		frameRate: Option[Schema.CdnSettings.FrameRateEnum] = None,
	  /** The resolution of the inbound video data. */
		resolution: Option[Schema.CdnSettings.ResolutionEnum] = None,
	  /** The format of the video stream that you are sending to Youtube.  */
		format: Option[String] = None,
	  /**  The method or protocol used to transmit the video stream. */
		ingestionType: Option[Schema.CdnSettings.IngestionTypeEnum] = None,
	  /** The ingestionInfo object contains information that YouTube provides that you need to transmit your RTMP or HTTP stream to YouTube. */
		ingestionInfo: Option[Schema.IngestionInfo] = None
	)
	
	case class CaptionListResponse(
	  /** A list of captions that match the request criteria. */
		items: Option[List[Schema.Caption]] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#captionListResponse". */
		kind: Option[String] = Some("""youtube#captionListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None
	)
	
	case class LiveChatMessageDeletedDetails(
		deletedMessageId: Option[String] = None
	)
	
	case class SuperChatEventListResponse(
	  /** A list of Super Chat purchases that match the request criteria. */
		items: Option[List[Schema.SuperChatEvent]] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
		pageInfo: Option[Schema.PageInfo] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#superChatEventListResponse". */
		kind: Option[String] = Some("""youtube#superChatEventListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None
	)
	
	case class LocalizedString(
		value: Option[String] = None,
		language: Option[String] = None
	)
	
	case class Subscription(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#subscription". */
		kind: Option[String] = Some("""youtube#subscription"""),
	  /** The snippet object contains basic details about the subscription, including its title and the channel that the user subscribed to. */
		snippet: Option[Schema.SubscriptionSnippet] = None,
	  /** The ID that YouTube uses to uniquely identify the subscription. */
		id: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The subscriberSnippet object contains basic details about the subscriber. */
		subscriberSnippet: Option[Schema.SubscriptionSubscriberSnippet] = None,
	  /** The contentDetails object contains basic statistics about the subscription. */
		contentDetails: Option[Schema.SubscriptionContentDetails] = None
	)
	
	case class PlaylistListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlistListResponse". */
		kind: Option[String] = Some("""youtube#playlistListResponse"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** A list of playlists that match the request criteria */
		items: Option[List[Schema.Playlist]] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None
	)
	
	case class MembershipsLevelListResponse(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** A list of pricing levels offered by a creator to the fans. */
		items: Option[List[Schema.MembershipsLevel]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#membershipsLevelListResponse". */
		kind: Option[String] = Some("""youtube#membershipsLevelListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None
	)
	
	case class CommentThread(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#commentThread". */
		kind: Option[String] = Some("""youtube#commentThread"""),
	  /** The ID that YouTube uses to uniquely identify the comment thread. */
		id: Option[String] = None,
	  /** The snippet object contains basic details about the comment thread and also the top level comment. */
		snippet: Option[Schema.CommentThreadSnippet] = None,
	  /** The replies object contains a limited number of replies (if any) to the top level comment found in the snippet. */
		replies: Option[Schema.CommentThreadReplies] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	object LiveBroadcastStatus {
		enum PrivacyStatusEnum extends Enum[PrivacyStatusEnum] { case `public`, unlisted, `private` }
		enum LifeCycleStatusEnum extends Enum[LifeCycleStatusEnum] { case lifeCycleStatusUnspecified, created, ready, testing, live, complete, revoked, testStarting, liveStarting }
		enum RecordingStatusEnum extends Enum[RecordingStatusEnum] { case liveBroadcastRecordingStatusUnspecified, notRecording, recording, recorded }
		enum LiveBroadcastPriorityEnum extends Enum[LiveBroadcastPriorityEnum] { case liveBroadcastPriorityUnspecified, low, normal, high }
	}
	case class LiveBroadcastStatus(
	  /** This field will be set to True if the creator declares the broadcast to be kids only: go/live-cw-work. */
		selfDeclaredMadeForKids: Option[Boolean] = None,
	  /** Whether the broadcast is made for kids or not, decided by YouTube instead of the creator. This field is read only. */
		madeForKids: Option[Boolean] = None,
	  /** The broadcast's privacy status. Note that the broadcast represents exactly one YouTube video, so the privacy settings are identical to those supported for videos. In addition, you can set this field by modifying the broadcast resource or by setting the privacyStatus field of the corresponding video resource. */
		privacyStatus: Option[Schema.LiveBroadcastStatus.PrivacyStatusEnum] = None,
	  /** The broadcast's status. The status can be updated using the API's liveBroadcasts.transition method. */
		lifeCycleStatus: Option[Schema.LiveBroadcastStatus.LifeCycleStatusEnum] = None,
	  /** The broadcast's recording status. */
		recordingStatus: Option[Schema.LiveBroadcastStatus.RecordingStatusEnum] = None,
	  /** Priority of the live broadcast event (internal state). */
		liveBroadcastPriority: Option[Schema.LiveBroadcastStatus.LiveBroadcastPriorityEnum] = None
	)
	
	case class PlaylistItem(
	  /** The status object contains information about the playlist item's privacy status. */
		status: Option[Schema.PlaylistItemStatus] = None,
	  /** The ID that YouTube uses to uniquely identify the playlist item. */
		id: Option[String] = None,
	  /** The snippet object contains basic details about the playlist item, such as its title and position in the playlist. */
		snippet: Option[Schema.PlaylistItemSnippet] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The contentDetails object is included in the resource if the included item is a YouTube video. The object contains additional information about the video. */
		contentDetails: Option[Schema.PlaylistItemContentDetails] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#playlistItem". */
		kind: Option[String] = Some("""youtube#playlistItem""")
	)
	
	case class LiveChatMembershipGiftingDetails(
	  /** The number of gift memberships purchased by the user. */
		giftMembershipsCount: Option[Int] = None,
	  /** The name of the level of the gift memberships purchased by the user. The Level names are defined by the YouTube channel offering the Membership. In some situations this field isn't filled. */
		giftMembershipsLevelName: Option[String] = None
	)
	
	case class LiveChatMessage(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveChatMessage". */
		kind: Option[String] = Some("""youtube#liveChatMessage"""),
	  /** The snippet object contains basic details about the message. */
		snippet: Option[Schema.LiveChatMessageSnippet] = None,
	  /** The authorDetails object contains basic details about the user that posted this message. */
		authorDetails: Option[Schema.LiveChatMessageAuthorDetails] = None,
	  /** The ID that YouTube assigns to uniquely identify the message. */
		id: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class LiveChatModeratorSnippet(
	  /** Details about the moderator. */
		moderatorDetails: Option[Schema.ChannelProfileDetails] = None,
	  /** The ID of the live chat this moderator can act on. */
		liveChatId: Option[String] = None
	)
	
	case class ChannelSection(
	  /** Localizations for different languages */
		localizations: Option[Map[String, Schema.ChannelSectionLocalization]] = None,
	  /** The ID that YouTube uses to uniquely identify the channel section. */
		id: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#channelSection". */
		kind: Option[String] = Some("""youtube#channelSection"""),
	  /** The contentDetails object contains details about the channel section content, such as a list of playlists or channels featured in the section. */
		contentDetails: Option[Schema.ChannelSectionContentDetails] = None,
	  /** The targeting object contains basic targeting settings about the channel section. */
		targeting: Option[Schema.ChannelSectionTargeting] = None,
	  /** The snippet object contains basic details about the channel section, such as its type, style and title. */
		snippet: Option[Schema.ChannelSectionSnippet] = None
	)
	
	object LiveStreamStatus {
		enum StreamStatusEnum extends Enum[StreamStatusEnum] { case created, ready, active, inactive, error }
	}
	case class LiveStreamStatus(
	  /** The health status of the stream. */
		healthStatus: Option[Schema.LiveStreamHealthStatus] = None,
		streamStatus: Option[Schema.LiveStreamStatus.StreamStatusEnum] = None
	)
	
	case class ActivityListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#activityListResponse". */
		kind: Option[String] = Some("""youtube#activityListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
		items: Option[List[Schema.Activity]] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None
	)
	
	case class PageInfo(
	  /** The total number of results in the result set. */
		totalResults: Option[Int] = None,
	  /** The number of results included in the API response. */
		resultsPerPage: Option[Int] = None
	)
	
	case class ActivityContentDetailsBulletin(
	  /** The resourceId object contains information that identifies the resource associated with a bulletin post. @mutable youtube.activities.insert */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	case class ActivityContentDetailsUpload(
	  /** The ID that YouTube uses to uniquely identify the uploaded video. */
		videoId: Option[String] = None
	)
	
	object InvideoPosition {
		enum CornerPositionEnum extends Enum[CornerPositionEnum] { case topLeft, topRight, bottomLeft, bottomRight }
		enum TypeEnum extends Enum[TypeEnum] { case corner }
	}
	case class InvideoPosition(
	  /** Describes in which corner of the video the visual widget will appear. */
		cornerPosition: Option[Schema.InvideoPosition.CornerPositionEnum] = None,
	  /** Defines the position type. */
		`type`: Option[Schema.InvideoPosition.TypeEnum] = None
	)
	
	case class CommentThreadReplies(
	  /** A limited number of replies. Unless the number of replies returned equals total_reply_count in the snippet the returned replies are only a subset of the total number of replies. */
		comments: Option[List[Schema.Comment]] = None
	)
	
	case class ChannelSectionListResponse(
	  /** A list of ChannelSections that match the request criteria. */
		items: Option[List[Schema.ChannelSection]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#channelSectionListResponse". */
		kind: Option[String] = Some("""youtube#channelSectionListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None
	)
	
	object ActivitySnippet {
		enum TypeEnum extends Enum[TypeEnum] { case typeUnspecified, upload, like, favorite, comment, subscription, playlistItem, recommendation, bulletin, social, channelItem, promotedItem }
	}
	case class ActivitySnippet(
	  /** A map of thumbnail images associated with the resource that is primarily associated with the activity. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The description of the resource primarily associated with the activity. @mutable youtube.activities.insert */
		description: Option[String] = None,
	  /** The type of activity that the resource describes. */
		`type`: Option[Schema.ActivitySnippet.TypeEnum] = None,
	  /** The ID that YouTube uses to uniquely identify the channel associated with the activity. */
		channelId: Option[String] = None,
	  /** The group ID associated with the activity. A group ID identifies user events that are associated with the same user and resource. For example, if a user rates a video and marks the same video as a favorite, the entries for those events would have the same group ID in the user's activity feed. In your user interface, you can avoid repetition by grouping events with the same groupId value. */
		groupId: Option[String] = None,
	  /** The date and time that the video was uploaded. */
		publishedAt: Option[String] = None,
	  /** The title of the resource primarily associated with the activity. */
		title: Option[String] = None,
	  /** Channel title for the channel responsible for this activity */
		channelTitle: Option[String] = None
	)
	
	case class ChannelListResponse(
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#channelListResponse". */
		kind: Option[String] = Some("""youtube#channelListResponse"""),
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
		items: Option[List[Schema.Channel]] = None
	)
	
	object LiveStreamConfigurationIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case info, warning, error }
		enum TypeEnum extends Enum[TypeEnum] { case gopSizeOver, gopSizeLong, gopSizeShort, openGop, badContainer, audioBitrateHigh, audioBitrateLow, audioSampleRate, bitrateHigh, bitrateLow, audioCodec, videoCodec, noAudioStream, noVideoStream, multipleVideoStreams, multipleAudioStreams, audioTooManyChannels, interlacedVideo, frameRateHigh, resolutionMismatch, videoCodecMismatch, videoInterlaceMismatch, videoProfileMismatch, videoBitrateMismatch, framerateMismatch, gopMismatch, audioSampleRateMismatch, audioStereoMismatch, audioCodecMismatch, audioBitrateMismatch, videoResolutionSuboptimal, videoResolutionUnsupported, videoIngestionStarved, videoIngestionFasterThanRealtime }
	}
	case class LiveStreamConfigurationIssue(
	  /** How severe this issue is to the stream. */
		severity: Option[Schema.LiveStreamConfigurationIssue.SeverityEnum] = None,
	  /** The short-form reason for this issue. */
		reason: Option[String] = None,
	  /** The long-form description of the issue and how to resolve it. */
		description: Option[String] = None,
	  /** The kind of error happening. */
		`type`: Option[Schema.LiveStreamConfigurationIssue.TypeEnum] = None
	)
	
	object VideoFileDetailsVideoStream {
		enum RotationEnum extends Enum[RotationEnum] { case none, clockwise, upsideDown, counterClockwise, other }
	}
	case class VideoFileDetailsVideoStream(
	  /** The video stream's bitrate, in bits per second. */
		bitrateBps: Option[String] = None,
	  /** The amount that YouTube needs to rotate the original source content to properly display the video. */
		rotation: Option[Schema.VideoFileDetailsVideoStream.RotationEnum] = None,
	  /** A value that uniquely identifies a video vendor. Typically, the value is a four-letter vendor code. */
		vendor: Option[String] = None,
	  /** The video stream's frame rate, in frames per second. */
		frameRateFps: Option[BigDecimal] = None,
	  /** The video content's display aspect ratio, which specifies the aspect ratio in which the video should be displayed. */
		aspectRatio: Option[BigDecimal] = None,
	  /** The encoded video content's height in pixels. */
		heightPixels: Option[Int] = None,
	  /** The video codec that the stream uses. */
		codec: Option[String] = None,
	  /** The encoded video content's width in pixels. You can calculate the video's encoding aspect ratio as width_pixels / height_pixels. */
		widthPixels: Option[Int] = None
	)
	
	case class VideoAbuseReportReasonListResponse(
	  /** The `visitorId` identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"youtube#videoAbuseReportReasonListResponse"`. */
		kind: Option[String] = Some("""youtube#videoAbuseReportReasonListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** A list of valid abuse reasons that are used with `video.ReportAbuse`. */
		items: Option[List[Schema.VideoAbuseReportReason]] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class ActivityContentDetailsSubscription(
	  /** The resourceId object contains information that identifies the resource that the user subscribed to. */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	case class VideoLocalization(
	  /** Localized version of the video's description. */
		description: Option[String] = None,
	  /** Localized version of the video's title. */
		title: Option[String] = None
	)
	
	object LiveBroadcastContentDetails {
		enum LatencyPreferenceEnum extends Enum[LatencyPreferenceEnum] { case latencyPreferenceUnspecified, normal, low, ultraLow }
		enum StereoLayoutEnum extends Enum[StereoLayoutEnum] { case stereoLayoutUnspecified, mono, leftRight, topBottom }
		enum ClosedCaptionsTypeEnum extends Enum[ClosedCaptionsTypeEnum] { case closedCaptionsTypeUnspecified, closedCaptionsDisabled, closedCaptionsHttpPost, closedCaptionsEmbedded }
		enum ProjectionEnum extends Enum[ProjectionEnum] { case projectionUnspecified, rectangular, `360`, mesh }
	}
	case class LiveBroadcastContentDetails(
	  /** The mesh for projecting the video if projection is mesh. The mesh value must be a UTF-8 string containing the base-64 encoding of 3D mesh data that follows the Spherical Video V2 RFC specification for an mshp box, excluding the box size and type but including the following four reserved zero bytes for the version and flags. */
		mesh: Option[String] = None,
	  /** This setting indicates whether YouTube should enable content encryption for the broadcast. */
		enableContentEncryption: Option[Boolean] = None,
	  /** If both this and enable_low_latency are set, they must match. LATENCY_NORMAL should match enable_low_latency=false LATENCY_LOW should match enable_low_latency=true LATENCY_ULTRA_LOW should have enable_low_latency omitted. */
		latencyPreference: Option[Schema.LiveBroadcastContentDetails.LatencyPreferenceEnum] = None,
	  /** The 3D stereo layout of this broadcast. This defaults to mono. */
		stereoLayout: Option[Schema.LiveBroadcastContentDetails.StereoLayoutEnum] = None,
	  /** The monitorStream object contains information about the monitor stream, which the broadcaster can use to review the event content before the broadcast stream is shown publicly. */
		monitorStream: Option[Schema.MonitorStreamInfo] = None,
	  /** This value uniquely identifies the live stream bound to the broadcast. */
		boundStreamId: Option[String] = None,
	  /** This setting indicates whether the broadcast should automatically begin with an in-stream slate when you update the broadcast's status to live. After updating the status, you then need to send a liveCuepoints.insert request that sets the cuepoint's eventState to end to remove the in-stream slate and make your broadcast stream visible to viewers. */
		startWithSlate: Option[Boolean] = None,
		closedCaptionsType: Option[Schema.LiveBroadcastContentDetails.ClosedCaptionsTypeEnum] = None,
	  /** The date and time that the live stream referenced by boundStreamId was last updated. */
		boundStreamLastUpdateTimeMs: Option[String] = None,
	  /** This setting determines whether viewers can access DVR controls while watching the video. DVR controls enable the viewer to control the video playback experience by pausing, rewinding, or fast forwarding content. The default value for this property is true. &#42;Important:&#42; You must set the value to true and also set the enableArchive property's value to true if you want to make playback available immediately after the broadcast ends. */
		enableDvr: Option[Boolean] = None,
	  /** This setting indicates whether the broadcast video can be played in an embedded player. If you choose to archive the video (using the enableArchive property), this setting will also apply to the archived video. */
		enableEmbed: Option[Boolean] = None,
	  /** Indicates whether this broadcast has low latency enabled. */
		enableLowLatency: Option[Boolean] = None,
	  /** This setting indicates whether auto start is enabled for this broadcast. The default value for this property is false. This setting can only be used by Events. */
		enableAutoStart: Option[Boolean] = None,
	  /** This setting indicates whether HTTP POST closed captioning is enabled for this broadcast. The ingestion URL of the closed captions is returned through the liveStreams API. This is mutually exclusive with using the closed_captions_type property, and is equivalent to setting closed_captions_type to CLOSED_CAPTIONS_HTTP_POST. */
		enableClosedCaptions: Option[Boolean] = None,
	  /** Automatically start recording after the event goes live. The default value for this property is true. &#42;Important:&#42; You must also set the enableDvr property's value to true if you want the playback to be available immediately after the broadcast ends. If you set this property's value to true but do not also set the enableDvr property to true, there may be a delay of around one day before the archived video will be available for playback. */
		recordFromStart: Option[Boolean] = None,
	  /** The projection format of this broadcast. This defaults to rectangular. */
		projection: Option[Schema.LiveBroadcastContentDetails.ProjectionEnum] = None,
	  /** This setting indicates whether auto stop is enabled for this broadcast. The default value for this property is false. This setting can only be used by Events. */
		enableAutoStop: Option[Boolean] = None
	)
	
	case class VideoSuggestionsTagSuggestion(
	  /** The keyword tag suggested for the video. */
		tag: Option[String] = None,
	  /** A set of video categories for which the tag is relevant. You can use this information to display appropriate tag suggestions based on the video category that the video uploader associates with the video. By default, tag suggestions are relevant for all categories if there are no restricts defined for the keyword. */
		categoryRestricts: Option[List[String]] = None
	)
	
	case class MembershipsLevelSnippet(
	  /** The id of the channel that's offering channel memberships. */
		creatorChannelId: Option[String] = None,
	  /** Details about the pricing level. */
		levelDetails: Option[Schema.LevelDetails] = None
	)
	
	case class ThumbnailSetResponse(
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** A list of thumbnails. */
		items: Option[List[Schema.ThumbnailDetails]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#thumbnailSetResponse". */
		kind: Option[String] = Some("""youtube#thumbnailSetResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None
	)
	
	case class GeoPoint(
	  /** Longitude in degrees. */
		longitude: Option[BigDecimal] = None,
	  /** Latitude in degrees. */
		latitude: Option[BigDecimal] = None,
	  /** Altitude above the reference ellipsoid, in meters. */
		altitude: Option[BigDecimal] = None
	)
	
	object Cuepoint {
		enum CueTypeEnum extends Enum[CueTypeEnum] { case cueTypeUnspecified, cueTypeAd }
	}
	case class Cuepoint(
		etag: Option[String] = None,
	  /** The wall clock time at which the cuepoint should be inserted. Only one of insertion_offset_time_ms and walltime_ms may be set at a time. */
		walltimeMs: Option[String] = None,
	  /** The duration of this cuepoint. */
		durationSecs: Option[Int] = None,
	  /** The time when the cuepoint should be inserted by offset to the broadcast actual start time. */
		insertionOffsetTimeMs: Option[String] = None,
		cueType: Option[Schema.Cuepoint.CueTypeEnum] = None,
	  /** The identifier for cuepoint resource. */
		id: Option[String] = None
	)
	
	object VideoContentDetails {
		enum ProjectionEnum extends Enum[ProjectionEnum] { case rectangular, `360` }
		enum DefinitionEnum extends Enum[DefinitionEnum] { case sd, hd }
		enum CaptionEnum extends Enum[CaptionEnum] { case `true`, `false` }
	}
	case class VideoContentDetails(
	  /** Indicates whether the video uploader has provided a custom thumbnail image for the video. This property is only visible to the video uploader. */
		hasCustomThumbnail: Option[Boolean] = None,
	  /** The length of the video. The tag value is an ISO 8601 duration in the format PT#M#S, in which the letters PT indicate that the value specifies a period of time, and the letters M and S refer to length in minutes and seconds, respectively. The # characters preceding the M and S letters are both integers that specify the number of minutes (or seconds) of the video. For example, a value of PT15M51S indicates that the video is 15 minutes and 51 seconds long. */
		duration: Option[String] = None,
	  /** Specifies the projection format of the video. */
		projection: Option[Schema.VideoContentDetails.ProjectionEnum] = None,
	  /** Specifies the ratings that the video received under various rating schemes. */
		contentRating: Option[Schema.ContentRating] = None,
	  /** The value of definition indicates whether the video is available in high definition or only in standard definition. */
		definition: Option[Schema.VideoContentDetails.DefinitionEnum] = None,
	  /** The regionRestriction object contains information about the countries where a video is (or is not) viewable. The object will contain either the contentDetails.regionRestriction.allowed property or the contentDetails.regionRestriction.blocked property. */
		regionRestriction: Option[Schema.VideoContentDetailsRegionRestriction] = None,
	  /** The value of is_license_content indicates whether the video is licensed content. */
		licensedContent: Option[Boolean] = None,
	  /** The value of captions indicates whether the video has captions or not. */
		caption: Option[Schema.VideoContentDetails.CaptionEnum] = None,
	  /** The value of dimension indicates whether the video is available in 3D or in 2D. */
		dimension: Option[String] = None,
	  /** The countryRestriction object contains information about the countries where a video is (or is not) viewable. */
		countryRestriction: Option[Schema.AccessPolicy] = None
	)
	
	case class WatchSettings(
	  /** The background color for the video watch page's branded area. */
		textColor: Option[String] = None,
	  /** The text color for the video watch page's branded area. */
		backgroundColor: Option[String] = None,
	  /** An ID that uniquely identifies a playlist that displays next to the video player. */
		featuredPlaylistId: Option[String] = None
	)
	
	case class LiveBroadcastStatistics(
	  /** The number of viewers currently watching the broadcast. The property and its value will be present if the broadcast has current viewers and the broadcast owner has not hidden the viewcount for the video. Note that YouTube stops tracking the number of concurrent viewers for a broadcast when the broadcast ends. So, this property would not identify the number of viewers watching an archived video of a live broadcast that already ended. */
		concurrentViewers: Option[String] = None
	)
	
	case class ChannelTopicDetails(
	  /** A list of Freebase topic IDs associated with the channel. You can retrieve information about each topic using the Freebase Topic API. */
		topicIds: Option[List[String]] = None,
	  /** A list of Wikipedia URLs that describe the channel's content. */
		topicCategories: Option[List[String]] = None
	)
	
	case class VideoLiveStreamingDetails(
	  /** The time that the broadcast actually ended. This value will not be available until the broadcast is over. */
		actualEndTime: Option[String] = None,
	  /** The time that the broadcast is scheduled to begin. */
		scheduledStartTime: Option[String] = None,
	  /** The ID of the currently active live chat attached to this video. This field is filled only if the video is a currently live broadcast that has live chat. Once the broadcast transitions to complete this field will be removed and the live chat closed down. For persistent broadcasts that live chat id will no longer be tied to this video but rather to the new video being displayed at the persistent page. */
		activeLiveChatId: Option[String] = None,
	  /** The time that the broadcast is scheduled to end. If the value is empty or the property is not present, then the broadcast is scheduled to continue indefinitely. */
		scheduledEndTime: Option[String] = None,
	  /** The time that the broadcast actually started. This value will not be available until the broadcast begins. */
		actualStartTime: Option[String] = None,
	  /** The number of viewers currently watching the broadcast. The property and its value will be present if the broadcast has current viewers and the broadcast owner has not hidden the viewcount for the video. Note that YouTube stops tracking the number of concurrent viewers for a broadcast when the broadcast ends. So, this property would not identify the number of viewers watching an archived video of a live broadcast that already ended. */
		concurrentViewers: Option[String] = None
	)
	
	case class I18nRegion(
	  /** The ID that YouTube uses to uniquely identify the i18n region. */
		id: Option[String] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The snippet object contains basic details about the i18n region, such as region code and human-readable name. */
		snippet: Option[Schema.I18nRegionSnippet] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#i18nRegion". */
		kind: Option[String] = Some("""youtube#i18nRegion""")
	)
	
	object ChannelToStoreLinkDetailsBillingDetails {
		enum BillingStatusEnum extends Enum[BillingStatusEnum] { case billingStatusUnspecified, billingStatusPending, billingStatusActive, billingStatusInactive }
	}
	case class ChannelToStoreLinkDetailsBillingDetails(
	  /** The current billing profile status. */
		billingStatus: Option[Schema.ChannelToStoreLinkDetailsBillingDetails.BillingStatusEnum] = None
	)
	
	case class VideoAbuseReportSecondaryReason(
	  /** The localized label for this abuse report secondary reason. */
		label: Option[String] = None,
	  /** The ID of this abuse report secondary reason. */
		id: Option[String] = None
	)
	
	case class VideoCategoryListResponse(
	  /** A list of video categories that can be associated with YouTube videos. In this map, the video category ID is the map key, and its value is the corresponding videoCategory resource. */
		items: Option[List[Schema.VideoCategory]] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#videoCategoryListResponse". */
		kind: Option[String] = Some("""youtube#videoCategoryListResponse"""),
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None
	)
	
	case class ActivityContentDetailsFavorite(
	  /** The resourceId object contains information that identifies the resource that was marked as a favorite. */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	case class SubscriptionListResponse(
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** A list of subscriptions that match the request criteria. */
		items: Option[List[Schema.Subscription]] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
		pageInfo: Option[Schema.PageInfo] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#subscriptionListResponse". */
		kind: Option[String] = Some("""youtube#subscriptionListResponse"""),
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class Video(
	  /** The statistics object contains statistics about the video. */
		statistics: Option[Schema.VideoStatistics] = None,
	  /** The contentDetails object contains information about the video content, including the length of the video and its aspect ratio. */
		contentDetails: Option[Schema.VideoContentDetails] = None,
	  /** The suggestions object encapsulates suggestions that identify opportunities to improve the video quality or the metadata for the uploaded video. This data can only be retrieved by the video owner. */
		suggestions: Option[Schema.VideoSuggestions] = None,
	  /** The localizations object contains localized versions of the basic details about the video, such as its title and description. */
		localizations: Option[Map[String, Schema.VideoLocalization]] = None,
	  /** The liveStreamingDetails object contains metadata about a live video broadcast. The object will only be present in a video resource if the video is an upcoming, live, or completed live broadcast. */
		liveStreamingDetails: Option[Schema.VideoLiveStreamingDetails] = None,
	  /** The processingDetails object encapsulates information about YouTube's progress in processing the uploaded video file. The properties in the object identify the current processing status and an estimate of the time remaining until YouTube finishes processing the video. This part also indicates whether different types of data or content, such as file details or thumbnail images, are available for the video. The processingProgress object is designed to be polled so that the video uploaded can track the progress that YouTube has made in processing the uploaded video file. This data can only be retrieved by the video owner. */
		processingDetails: Option[Schema.VideoProcessingDetails] = None,
	  /** The status object contains information about the video's uploading, processing, and privacy statuses. */
		status: Option[Schema.VideoStatus] = None,
	  /** The recordingDetails object encapsulates information about the location, date and address where the video was recorded. */
		recordingDetails: Option[Schema.VideoRecordingDetails] = None,
	  /** The monetizationDetails object encapsulates information about the monetization status of the video. */
		monetizationDetails: Option[Schema.VideoMonetizationDetails] = None,
	  /** The ID that YouTube uses to uniquely identify the video. */
		id: Option[String] = None,
	  /** The topicDetails object encapsulates information about Freebase topics associated with the video. */
		topicDetails: Option[Schema.VideoTopicDetails] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#video". */
		kind: Option[String] = Some("""youtube#video"""),
	  /** The snippet object contains basic details about the video, such as its title, description, and category. */
		snippet: Option[Schema.VideoSnippet] = None,
	  /** The fileDetails object encapsulates information about the video file that was uploaded to YouTube, including the file's resolution, duration, audio and video codecs, stream bitrates, and more. This data can only be retrieved by the video owner. */
		fileDetails: Option[Schema.VideoFileDetails] = None,
	  /** Age restriction details related to a video. This data can only be retrieved by the video owner. */
		ageGating: Option[Schema.VideoAgeGating] = None,
		paidProductPlacementDetails: Option[Schema.VideoPaidProductPlacementDetails] = None,
	  /** The projectDetails object contains information about the project specific video metadata. b/157517979: This part was never populated after it was added. However, it sees non-zero traffic because there is generated client code in the wild that refers to it [1]. We keep this field and do NOT remove it because otherwise V3 would return an error when this part gets requested [2]. [1] https://developers.google.com/resources/api-libraries/documentation/youtube/v3/csharp/latest/classGoogle_1_1Apis_1_1YouTube_1_1v3_1_1Data_1_1VideoProjectDetails.html [2] http://google3/video/youtube/src/python/servers/data_api/common.py?l=1565-1569&rcl=344141677 */
		projectDetails: Option[Schema.VideoProjectDetails] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The player object contains information that you would use to play the video in an embedded player. */
		player: Option[Schema.VideoPlayer] = None
	)
	
	case class ChannelAuditDetails(
	  /** Whether or not the channel has any unresolved claims. */
		contentIdClaimsGoodStanding: Option[Boolean] = None,
	  /** Whether or not the channel respects the community guidelines. */
		communityGuidelinesGoodStanding: Option[Boolean] = None,
	  /** Whether or not the channel has any copyright strikes. */
		copyrightStrikesGoodStanding: Option[Boolean] = None
	)
	
	case class MemberSnippet(
	  /** Details about the member. */
		memberDetails: Option[Schema.ChannelProfileDetails] = None,
	  /** The id of the channel that's offering memberships. */
		creatorChannelId: Option[String] = None,
	  /** Details about the user's membership. */
		membershipsDetails: Option[Schema.MembershipsDetails] = None
	)
	
	case class Activity(
	  /** Etag of this resource */
		etag: Option[String] = None,
	  /** The contentDetails object contains information about the content associated with the activity. For example, if the snippet.type value is videoRated, then the contentDetails object's content identifies the rated video. */
		contentDetails: Option[Schema.ActivityContentDetails] = None,
	  /** The ID that YouTube uses to uniquely identify the activity. */
		id: Option[String] = None,
	  /** The snippet object contains basic details about the activity, including the activity's type and group ID. */
		snippet: Option[Schema.ActivitySnippet] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#activity". */
		kind: Option[String] = Some("""youtube#activity""")
	)
	
	object ChannelConversionPing {
		enum ContextEnum extends Enum[ContextEnum] { case subscribe, unsubscribe, cview }
	}
	case class ChannelConversionPing(
	  /** The url (without the schema) that the player shall send the ping to. It's at caller's descretion to decide which schema to use (http vs https) Example of a returned url: //googleads.g.doubleclick.net/pagead/ viewthroughconversion/962985656/?data=path%3DtHe_path%3Btype%3D cview%3Butuid%3DGISQtTNGYqaYl4sKxoVvKA&labe=default The caller must append biscotti authentication (ms param in case of mobile, for example) to this ping. */
		conversionUrl: Option[String] = None,
	  /** Defines the context of the ping. */
		context: Option[Schema.ChannelConversionPing.ContextEnum] = None
	)
	
	case class VideoProjectDetails(
	
	)
	
	case class ChannelProfileDetails(
	  /** The channel's display name. */
		displayName: Option[String] = None,
	  /** The YouTube channel ID. */
		channelId: Option[String] = None,
	  /** The channels's avatar URL. */
		profileImageUrl: Option[String] = None,
	  /** The channel's URL. */
		channelUrl: Option[String] = None
	)
	
	case class LiveChatMessageListResponse(
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The date and time when the underlying stream went offline. */
		offlineAt: Option[String] = None,
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#liveChatMessageListResponse". */
		kind: Option[String] = Some("""youtube#liveChatMessageListResponse"""),
	  /** The amount of time the client should wait before polling again. */
		pollingIntervalMillis: Option[Int] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Set when there is an active poll. */
		activePollItem: Option[Schema.LiveChatMessage] = None,
		items: Option[List[Schema.LiveChatMessage]] = None,
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None
	)
	
	case class MembershipsDuration(
	  /** The cumulative time the user has been a member across all levels in complete months (the time is rounded down to the nearest integer). */
		memberTotalDurationMonths: Option[Int] = None,
	  /** The date and time when the user became a continuous member across all levels. */
		memberSince: Option[String] = None
	)
	
	case class LiveChatPollDetailsPollMetadataPollOption(
		tally: Option[String] = None,
		optionText: Option[String] = None
	)
	
	case class SearchListResponse(
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the previous page in the result set. */
		prevPageToken: Option[String] = None,
	  /** Pagination information for token pagination. */
		items: Option[List[Schema.SearchResult]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#searchListResponse". */
		kind: Option[String] = Some("""youtube#searchListResponse"""),
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
		regionCode: Option[String] = None,
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None,
	  /** The token that can be used as the value of the pageToken parameter to retrieve the next page in the result set. */
		nextPageToken: Option[String] = None,
		tokenPagination: Option[Schema.TokenPagination] = None
	)
	
	object VideoStatus {
		enum RejectionReasonEnum extends Enum[RejectionReasonEnum] { case copyright, inappropriate, duplicate, termsOfUse, uploaderAccountSuspended, length, claim, uploaderAccountClosed, trademark, legal }
		enum LicenseEnum extends Enum[LicenseEnum] { case youtube, creativeCommon }
		enum PrivacyStatusEnum extends Enum[PrivacyStatusEnum] { case `public`, unlisted, `private` }
		enum FailureReasonEnum extends Enum[FailureReasonEnum] { case conversion, invalidFile, emptyFile, tooSmall, codec, uploadAborted }
		enum UploadStatusEnum extends Enum[UploadStatusEnum] { case uploaded, processed, failed, rejected, deleted }
	}
	case class VideoStatus(
	  /** This value indicates if the video can be embedded on another website. @mutable youtube.videos.insert youtube.videos.update */
		embeddable: Option[Boolean] = None,
	  /** This value explains why YouTube rejected an uploaded video. This property is only present if the uploadStatus property indicates that the upload was rejected. */
		rejectionReason: Option[Schema.VideoStatus.RejectionReasonEnum] = None,
		madeForKids: Option[Boolean] = None,
	  /** The date and time when the video is scheduled to publish. It can be set only if the privacy status of the video is private.. */
		publishAt: Option[String] = None,
	  /** Indicates if the video contains altered or synthetic media. */
		containsSyntheticMedia: Option[Boolean] = None,
		selfDeclaredMadeForKids: Option[Boolean] = None,
	  /** The video's license. @mutable youtube.videos.insert youtube.videos.update */
		license: Option[Schema.VideoStatus.LicenseEnum] = None,
	  /** This value indicates if the extended video statistics on the watch page can be viewed by everyone. Note that the view count, likes, etc will still be visible if this is disabled. @mutable youtube.videos.insert youtube.videos.update */
		publicStatsViewable: Option[Boolean] = None,
	  /** The video's privacy status. */
		privacyStatus: Option[Schema.VideoStatus.PrivacyStatusEnum] = None,
	  /** This value explains why a video failed to upload. This property is only present if the uploadStatus property indicates that the upload failed. */
		failureReason: Option[Schema.VideoStatus.FailureReasonEnum] = None,
	  /** The status of the uploaded video. */
		uploadStatus: Option[Schema.VideoStatus.UploadStatusEnum] = None
	)
	
	case class Caption(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#caption". */
		kind: Option[String] = Some("""youtube#caption"""),
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** The snippet object contains basic details about the caption. */
		snippet: Option[Schema.CaptionSnippet] = None,
	  /** The ID that YouTube uses to uniquely identify the caption track. */
		id: Option[String] = None
	)
	
	case class MembershipsDurationAtLevel(
	  /** The date and time when the user became a continuous member for the given level. */
		memberSince: Option[String] = None,
	  /** The cumulative time the user has been a member for the given level in complete months (the time is rounded down to the nearest integer). */
		memberTotalDurationMonths: Option[Int] = None,
	  /** Pricing level ID. */
		level: Option[String] = None
	)
	
	object VideoRating {
		enum RatingEnum extends Enum[RatingEnum] { case none, like, dislike }
	}
	case class VideoRating(
	  /** Rating of a video. */
		rating: Option[Schema.VideoRating.RatingEnum] = None,
	  /** The ID that YouTube uses to uniquely identify the video. */
		videoId: Option[String] = None
	)
	
	case class PlaylistItemSnippet(
	  /** The date and time that the item was added to the playlist. */
		publishedAt: Option[String] = None,
	  /** The item's title. */
		title: Option[String] = None,
	  /** A map of thumbnail images associated with the playlist item. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The order in which the item appears in the playlist. The value uses a zero-based index, so the first item has a position of 0, the second item has a position of 1, and so forth. */
		position: Option[Int] = None,
	  /** The item's description. */
		description: Option[String] = None,
	  /** Channel title for the channel that the playlist item belongs to. */
		channelTitle: Option[String] = None,
	  /** The id object contains information that can be used to uniquely identify the resource that is included in the playlist as the playlist item. */
		resourceId: Option[Schema.ResourceId] = None,
	  /** The ID that YouTube uses to uniquely identify thGe playlist that the playlist item is in. */
		playlistId: Option[String] = None,
	  /** The ID that YouTube uses to uniquely identify the user that added the item to the playlist. */
		channelId: Option[String] = None,
	  /** Channel id for the channel this video belongs to. */
		videoOwnerChannelId: Option[String] = None,
	  /** Channel title for the channel this video belongs to. */
		videoOwnerChannelTitle: Option[String] = None
	)
	
	case class I18nRegionListResponse(
	  /** The visitorId identifies the visitor. */
		visitorId: Option[String] = None,
	  /** A list of regions where YouTube is available. In this map, the i18n region ID is the map key, and its value is the corresponding i18nRegion resource. */
		items: Option[List[Schema.I18nRegion]] = None,
	  /** Etag of this resource. */
		etag: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#i18nRegionListResponse". */
		kind: Option[String] = Some("""youtube#i18nRegionListResponse"""),
	  /** Serialized EventId of the request which produced this response. */
		eventId: Option[String] = None
	)
	
	case class PropertyValue(
	  /** The property's value. */
		value: Option[String] = None,
	  /** A property. */
		property: Option[String] = None
	)
	
	object SearchResultSnippet {
		enum LiveBroadcastContentEnum extends Enum[LiveBroadcastContentEnum] { case none, upcoming, live, completed }
	}
	case class SearchResultSnippet(
	  /** A description of the search result. */
		description: Option[String] = None,
	  /** It indicates if the resource (video or channel) has upcoming/active live broadcast content. Or it's "none" if there is not any upcoming/active live broadcasts. */
		liveBroadcastContent: Option[Schema.SearchResultSnippet.LiveBroadcastContentEnum] = None,
	  /** A map of thumbnail images associated with the search result. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The creation date and time of the resource that the search result identifies. */
		publishedAt: Option[String] = None,
	  /** The value that YouTube uses to uniquely identify the channel that published the resource that the search result identifies. */
		channelId: Option[String] = None,
	  /** The title of the search result. */
		title: Option[String] = None,
	  /** The title of the channel that published the resource that the search result identifies. */
		channelTitle: Option[String] = None
	)
	
	object LiveChatUserBannedMessageDetails {
		enum BanTypeEnum extends Enum[BanTypeEnum] { case permanent, temporary }
	}
	case class LiveChatUserBannedMessageDetails(
	  /** The type of ban. */
		banType: Option[Schema.LiveChatUserBannedMessageDetails.BanTypeEnum] = None,
	  /** The details of the user that was banned. */
		bannedUserDetails: Option[Schema.ChannelProfileDetails] = None,
	  /** The duration of the ban. This property is only present if the banType is temporary. */
		banDurationSeconds: Option[String] = None
	)
	
	object VideoSnippet {
		enum LiveBroadcastContentEnum extends Enum[LiveBroadcastContentEnum] { case none, upcoming, live, completed }
	}
	case class VideoSnippet(
	  /** The video's description. @mutable youtube.videos.insert youtube.videos.update */
		description: Option[String] = None,
	  /** The YouTube video category associated with the video. */
		categoryId: Option[String] = None,
	  /** The date and time when the video was uploaded. */
		publishedAt: Option[String] = None,
	  /** The default_audio_language property specifies the language spoken in the video's default audio track. */
		defaultAudioLanguage: Option[String] = None,
	  /** Indicates if the video is an upcoming/active live broadcast. Or it's "none" if the video is not an upcoming/active live broadcast. */
		liveBroadcastContent: Option[Schema.VideoSnippet.LiveBroadcastContentEnum] = None,
	  /** A map of thumbnail images associated with the video. For each object in the map, the key is the name of the thumbnail image, and the value is an object that contains other information about the thumbnail. */
		thumbnails: Option[Schema.ThumbnailDetails] = None,
	  /** The video's title. @mutable youtube.videos.insert youtube.videos.update */
		title: Option[String] = None,
	  /** A list of keyword tags associated with the video. Tags may contain spaces. */
		tags: Option[List[String]] = None,
	  /** The ID that YouTube uses to uniquely identify the channel that the video was uploaded to. */
		channelId: Option[String] = None,
	  /** Localized snippet selected with the hl parameter. If no such localization exists, this field is populated with the default snippet. (Read-only) */
		localized: Option[Schema.VideoLocalization] = None,
	  /** The language of the videos's default snippet. */
		defaultLanguage: Option[String] = None,
	  /** Channel title for the channel that the video belongs to. */
		channelTitle: Option[String] = None
	)
	
	case class ActivityContentDetailsComment(
	  /** The resourceId object contains information that identifies the resource associated with the comment. */
		resourceId: Option[Schema.ResourceId] = None
	)
	
	object LiveStreamHealthStatus {
		enum StatusEnum extends Enum[StatusEnum] { case good, ok, bad, noData, revoked }
	}
	case class LiveStreamHealthStatus(
	  /** The configurations issues on this stream */
		configurationIssues: Option[List[Schema.LiveStreamConfigurationIssue]] = None,
	  /** The status code of this stream */
		status: Option[Schema.LiveStreamHealthStatus.StatusEnum] = None,
	  /** The last time this status was updated (in seconds) */
		lastUpdateTimeSeconds: Option[String] = None
	)
	
	case class ThirdPartyLink(
	  /** Identifies what kind of resource this is. Value: the fixed string "youtube#thirdPartyLink". */
		kind: Option[String] = Some("""youtube#thirdPartyLink"""),
	  /** The linking_token identifies a YouTube account and channel with which the third party account is linked. */
		linkingToken: Option[String] = None,
	  /** The status object contains information about the status of the link. */
		status: Option[Schema.ThirdPartyLinkStatus] = None,
	  /** The snippet object contains basic details about the third- party account link. */
		snippet: Option[Schema.ThirdPartyLinkSnippet] = None,
	  /** Etag of this resource */
		etag: Option[String] = None
	)
}
