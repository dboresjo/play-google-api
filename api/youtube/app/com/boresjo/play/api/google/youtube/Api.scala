package com.boresjo.play.api.google.youtube

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://youtube.googleapis.com/"

	object membershipsLevels {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MembershipsLevelListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.MembershipsLevelListResponse])
		}
		object list {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/membershipsLevels")).addQueryStringParameters("part" -> part.toString))
			given Conversion[list, Future[Schema.MembershipsLevelListResponse]] = (fun: list) => fun.apply()
		}
	}
	object superChatEvents {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SuperChatEventListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SuperChatEventListResponse])
		}
		object list {
			def apply(pageToken: String, hl: String, maxResults: Int, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/superChatEvents")).addQueryStringParameters("pageToken" -> pageToken.toString, "hl" -> hl.toString, "maxResults" -> maxResults.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.SuperChatEventListResponse]] = (fun: list) => fun.apply()
		}
	}
	object thirdPartyLinks {
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withThirdPartyLink(body: Schema.ThirdPartyLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ThirdPartyLink])
		}
		object update {
			def apply(part: String, externalChannelId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks")).addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(part: String, `type`: String, linkingToken: String, externalChannelId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks")).addQueryStringParameters("part" -> part.toString, "type" -> `type`.toString, "linkingToken" -> linkingToken.toString, "externalChannelId" -> externalChannelId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withThirdPartyLink(body: Schema.ThirdPartyLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ThirdPartyLink])
		}
		object insert {
			def apply(part: String, externalChannelId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks")).addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ThirdPartyLinkListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ThirdPartyLinkListResponse])
		}
		object list {
			def apply(externalChannelId: String, `type`: String, linkingToken: String, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks")).addQueryStringParameters("externalChannelId" -> externalChannelId.toString, "type" -> `type`.toString, "linkingToken" -> linkingToken.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.ThirdPartyLinkListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveChatMessages {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveChatMessage(body: Schema.LiveChatMessage) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LiveChatMessage])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/messages")).addQueryStringParameters("part" -> part.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/messages")).addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class transition(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessage]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.LiveChatMessage])
		}
		object transition {
			def apply(status: String, id: String)(using auth: AuthToken, ec: ExecutionContext): transition = new transition(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/messages/transition")).addQueryStringParameters("status" -> status.toString, "id" -> id.toString))
			given Conversion[transition, Future[Schema.LiveChatMessage]] = (fun: transition) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessageListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LiveChatMessageListResponse])
		}
		object list {
			def apply(part: String, pageToken: String, hl: String, profileImageSize: Int, maxResults: Int, liveChatId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/messages")).addQueryStringParameters("part" -> part.toString, "pageToken" -> pageToken.toString, "hl" -> hl.toString, "profileImageSize" -> profileImageSize.toString, "maxResults" -> maxResults.toString, "liveChatId" -> liveChatId.toString))
			given Conversion[list, Future[Schema.LiveChatMessageListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlistItems {
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylistItem(body: Schema.PlaylistItem) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.PlaylistItem])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, part: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/playlistItems")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylistItem(body: Schema.PlaylistItem) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PlaylistItem])
		}
		object insert {
			def apply(part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/playlistItems")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/playlistItems")).addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlaylistItemListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlaylistItemListResponse])
		}
		object list {
			def apply(playlistId: String, part: String, onBehalfOfContentOwner: String, videoId: String, maxResults: Int, pageToken: String, id: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/playlistItems")).addQueryStringParameters("playlistId" -> playlistId.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "videoId" -> videoId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "id" -> id.toString))
			given Conversion[list, Future[Schema.PlaylistItemListResponse]] = (fun: list) => fun.apply()
		}
	}
	object commentThreads {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCommentThread(body: Schema.CommentThread) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CommentThread])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/commentThreads")).addQueryStringParameters("part" -> part.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CommentThreadListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.CommentThreadListResponse])
		}
		object list {
			def apply(order: String, videoId: String, id: String, part: String, channelId: String, pageToken: String, searchTerms: String, maxResults: Int, moderationStatus: String, textFormat: String, allThreadsRelatedToChannelId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/commentThreads")).addQueryStringParameters("order" -> order.toString, "videoId" -> videoId.toString, "id" -> id.toString, "part" -> part.toString, "channelId" -> channelId.toString, "pageToken" -> pageToken.toString, "searchTerms" -> searchTerms.toString, "maxResults" -> maxResults.toString, "moderationStatus" -> moderationStatus.toString, "textFormat" -> textFormat.toString, "allThreadsRelatedToChannelId" -> allThreadsRelatedToChannelId.toString))
			given Conversion[list, Future[Schema.CommentThreadListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveBroadcasts {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveBroadcast(body: Schema.LiveBroadcast) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LiveBroadcast])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts")).addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts")).addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class transition(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcast]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.LiveBroadcast])
		}
		object transition {
			def apply(id: String, broadcastStatus: String, part: String, onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): transition = new transition(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/transition")).addQueryStringParameters("id" -> id.toString, "broadcastStatus" -> broadcastStatus.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[transition, Future[Schema.LiveBroadcast]] = (fun: transition) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveBroadcast(body: Schema.LiveBroadcast) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LiveBroadcast])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, part: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcastListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LiveBroadcastListResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, broadcastStatus: String, part: String, broadcastType: String, id: String, maxResults: Int, mine: Boolean, pageToken: String, onBehalfOfContentOwnerChannel: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "broadcastStatus" -> broadcastStatus.toString, "part" -> part.toString, "broadcastType" -> broadcastType.toString, "id" -> id.toString, "maxResults" -> maxResults.toString, "mine" -> mine.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
			given Conversion[list, Future[Schema.LiveBroadcastListResponse]] = (fun: list) => fun.apply()
		}
		class insertCuepoint(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCuepoint(body: Schema.Cuepoint) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Cuepoint])
		}
		object insertCuepoint {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, part: String, id: String)(using auth: AuthToken, ec: ExecutionContext): insertCuepoint = new insertCuepoint(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/cuepoint")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString, "id" -> id.toString))
		}
		class bind(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcast]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.LiveBroadcast])
		}
		object bind {
			def apply(onBehalfOfContentOwner: String, streamId: String, id: String, onBehalfOfContentOwnerChannel: String, part: String)(using auth: AuthToken, ec: ExecutionContext): bind = new bind(auth(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/bind")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "streamId" -> streamId.toString, "id" -> id.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString))
			given Conversion[bind, Future[Schema.LiveBroadcast]] = (fun: bind) => fun.apply()
		}
	}
	object videoAbuseReportReasons {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VideoAbuseReportReasonListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.VideoAbuseReportReasonListResponse])
		}
		object list {
			def apply(part: String, hl: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/videoAbuseReportReasons")).addQueryStringParameters("part" -> part.toString, "hl" -> hl.toString))
			given Conversion[list, Future[Schema.VideoAbuseReportReasonListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlists {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlaylistListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlaylistListResponse])
		}
		object list {
			def apply(channelId: String, mine: Boolean, maxResults: Int, pageToken: String, id: String, part: String, hl: String, onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/playlists")).addQueryStringParameters("channelId" -> channelId.toString, "mine" -> mine.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "id" -> id.toString, "part" -> part.toString, "hl" -> hl.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.PlaylistListResponse]] = (fun: list) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylist(body: Schema.Playlist) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Playlist])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/playlists")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylist(body: Schema.Playlist) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Playlist])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String, part: String, onBehalfOfContentOwnerChannel: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/playlists")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/playlists")).addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object activities {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ActivityListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ActivityListResponse])
		}
		object list {
			def apply(channelId: String, mine: Boolean, publishedBefore: String, regionCode: String, maxResults: Int, pageToken: String, part: String, publishedAfter: String, home: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/activities")).addQueryStringParameters("channelId" -> channelId.toString, "mine" -> mine.toString, "publishedBefore" -> publishedBefore.toString, "regionCode" -> regionCode.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "part" -> part.toString, "publishedAfter" -> publishedAfter.toString, "home" -> home.toString))
			given Conversion[list, Future[Schema.ActivityListResponse]] = (fun: list) => fun.apply()
		}
	}
	object tests {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestItem(body: Schema.TestItem) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestItem])
		}
		object insert {
			def apply(part: String, externalChannelId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/tests")).addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
	}
	object liveChatBans {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/bans")).addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveChatBan(body: Schema.LiveChatBan) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LiveChatBan])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/bans")).addQueryStringParameters("part" -> part.toString))
		}
	}
	object comments {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComment(body: Schema.Comment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Comment])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/comments")).addQueryStringParameters("part" -> part.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/comments")).addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class markAsSpam(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object markAsSpam {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): markAsSpam = new markAsSpam(auth(ws.url(BASE_URL + s"youtube/v3/comments/markAsSpam")).addQueryStringParameters("id" -> id.toString))
			given Conversion[markAsSpam, Future[Unit]] = (fun: markAsSpam) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComment(body: Schema.Comment) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Comment])
		}
		object update {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/comments")).addQueryStringParameters("part" -> part.toString))
		}
		class setModerationStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object setModerationStatus {
			def apply(moderationStatus: String, banAuthor: Boolean, id: String)(using auth: AuthToken, ec: ExecutionContext): setModerationStatus = new setModerationStatus(auth(ws.url(BASE_URL + s"youtube/v3/comments/setModerationStatus")).addQueryStringParameters("moderationStatus" -> moderationStatus.toString, "banAuthor" -> banAuthor.toString, "id" -> id.toString))
			given Conversion[setModerationStatus, Future[Unit]] = (fun: setModerationStatus) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CommentListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.CommentListResponse])
		}
		object list {
			def apply(part: String, parentId: String, pageToken: String, id: String, textFormat: String, maxResults: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/comments")).addQueryStringParameters("part" -> part.toString, "parentId" -> parentId.toString, "pageToken" -> pageToken.toString, "id" -> id.toString, "textFormat" -> textFormat.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.CommentListResponse]] = (fun: list) => fun.apply()
		}
	}
	object watermarks {
		class set(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInvideoBranding(body: Schema.InvideoBranding) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
		}
		object set {
			def apply(onBehalfOfContentOwner: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): set = new set(auth(ws.url(BASE_URL + s"youtube/v3/watermarks/set")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "channelId" -> channelId.toString))
		}
		class unset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object unset {
			def apply(channelId: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): unset = new unset(auth(ws.url(BASE_URL + s"youtube/v3/watermarks/unset")).addQueryStringParameters("channelId" -> channelId.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[unset, Future[Unit]] = (fun: unset) => fun.apply()
		}
	}
	object i18nRegions {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.I18nRegionListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.I18nRegionListResponse])
		}
		object list {
			def apply(hl: String, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/i18nRegions")).addQueryStringParameters("hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.I18nRegionListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveStreams {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/liveStreams")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveStreamListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LiveStreamListResponse])
		}
		object list {
			def apply(id: String, part: String, maxResults: Int, mine: Boolean, onBehalfOfContentOwnerChannel: String, pageToken: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/liveStreams")).addQueryStringParameters("id" -> id.toString, "part" -> part.toString, "maxResults" -> maxResults.toString, "mine" -> mine.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.LiveStreamListResponse]] = (fun: list) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveStream(body: Schema.LiveStream) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LiveStream])
		}
		object update {
			def apply(onBehalfOfContentOwnerChannel: String, part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/liveStreams")).addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveStream(body: Schema.LiveStream) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LiveStream])
		}
		object insert {
			def apply(part: String, onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/liveStreams")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
		}
	}
	object abuseReports {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAbuseReport(body: Schema.AbuseReport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AbuseReport])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/abuseReports")).addQueryStringParameters("part" -> part.toString))
		}
	}
	object videoCategories {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VideoCategoryListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.VideoCategoryListResponse])
		}
		object list {
			def apply(id: String, regionCode: String, hl: String, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/videoCategories")).addQueryStringParameters("id" -> id.toString, "regionCode" -> regionCode.toString, "hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.VideoCategoryListResponse]] = (fun: list) => fun.apply()
		}
	}
	object videos {
		class rate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object rate {
			def apply(rating: String, id: String)(using auth: AuthToken, ec: ExecutionContext): rate = new rate(auth(ws.url(BASE_URL + s"youtube/v3/videos/rate")).addQueryStringParameters("rating" -> rating.toString, "id" -> id.toString))
			given Conversion[rate, Future[Unit]] = (fun: rate) => fun.apply()
		}
		class reportAbuse(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVideoAbuseReport(body: Schema.VideoAbuseReport) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
		}
		object reportAbuse {
			def apply(onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): reportAbuse = new reportAbuse(auth(ws.url(BASE_URL + s"youtube/v3/videos/reportAbuse")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVideo(body: Schema.Video) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Video])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, autoLevels: Boolean, onBehalfOfContentOwner: String, stabilize: Boolean, part: String, notifySubscribers: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/videos")).addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "autoLevels" -> autoLevels.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "stabilize" -> stabilize.toString, "part" -> part.toString, "notifySubscribers" -> notifySubscribers.toString))
		}
		class getRating(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VideoGetRatingResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.VideoGetRatingResponse])
		}
		object getRating {
			def apply(onBehalfOfContentOwner: String, id: String)(using auth: AuthToken, ec: ExecutionContext): getRating = new getRating(auth(ws.url(BASE_URL + s"youtube/v3/videos/getRating")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[getRating, Future[Schema.VideoGetRatingResponse]] = (fun: getRating) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/videos")).addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVideo(body: Schema.Video) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Video])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, part: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/videos")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VideoListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.VideoListResponse])
		}
		object list {
			def apply(myRating: String, id: String, hl: String, locale: String, maxHeight: Int, chart: String, onBehalfOfContentOwner: String, maxResults: Int, pageToken: String, maxWidth: Int, part: String, regionCode: String, videoCategoryId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/videos")).addQueryStringParameters("myRating" -> myRating.toString, "id" -> id.toString, "hl" -> hl.toString, "locale" -> locale.toString, "maxHeight" -> maxHeight.toString, "chart" -> chart.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "maxWidth" -> maxWidth.toString, "part" -> part.toString, "regionCode" -> regionCode.toString, "videoCategoryId" -> videoCategoryId.toString))
			given Conversion[list, Future[Schema.VideoListResponse]] = (fun: list) => fun.apply()
		}
	}
	object channelSections {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ChannelSectionListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ChannelSectionListResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, mine: Boolean, id: String, channelId: String, hl: String, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/channelSections")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "mine" -> mine.toString, "id" -> id.toString, "channelId" -> channelId.toString, "hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.ChannelSectionListResponse]] = (fun: list) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannelSection(body: Schema.ChannelSection) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ChannelSection])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/channelSections")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannelSection(body: Schema.ChannelSection) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ChannelSection])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/channelSections")).addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/channelSections")).addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object channels {
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Channel])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/channels")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ChannelListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ChannelListResponse])
		}
		object list {
			def apply(pageToken: String, forHandle: String, part: String, id: String, hl: String, mine: Boolean, forUsername: String, mySubscribers: Boolean, categoryId: String, maxResults: Int, managedByMe: Boolean, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/channels")).addQueryStringParameters("pageToken" -> pageToken.toString, "forHandle" -> forHandle.toString, "part" -> part.toString, "id" -> id.toString, "hl" -> hl.toString, "mine" -> mine.toString, "forUsername" -> forUsername.toString, "mySubscribers" -> mySubscribers.toString, "categoryId" -> categoryId.toString, "maxResults" -> maxResults.toString, "managedByMe" -> managedByMe.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.ChannelListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveChatModerators {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiveChatModerator(body: Schema.LiveChatModerator) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LiveChatModerator])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators")).addQueryStringParameters("part" -> part.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators")).addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveChatModeratorListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LiveChatModeratorListResponse])
		}
		object list {
			def apply(liveChatId: String, maxResults: Int, pageToken: String, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators")).addQueryStringParameters("liveChatId" -> liveChatId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.LiveChatModeratorListResponse]] = (fun: list) => fun.apply()
		}
	}
	object thumbnails {
		class set(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ThumbnailSetResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.ThumbnailSetResponse])
		}
		object set {
			def apply(onBehalfOfContentOwner: String, videoId: String)(using auth: AuthToken, ec: ExecutionContext): set = new set(auth(ws.url(BASE_URL + s"youtube/v3/thumbnails/set")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "videoId" -> videoId.toString))
			given Conversion[set, Future[Schema.ThumbnailSetResponse]] = (fun: set) => fun.apply()
		}
	}
	object search {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchListResponse])
		}
		object list {
			def apply(forDeveloper: Boolean, publishedAfter: String, locationRadius: String, onBehalfOfContentOwner: String, safeSearch: String, `type`: String, eventType: String, channelType: String, topicId: String, location: String, videoPaidProductPlacement: String, videoDefinition: String, videoCaption: String, forMine: Boolean, pageToken: String, videoType: String, relevanceLanguage: String, videoDimension: String, videoCategoryId: String, videoDuration: String, videoSyndicated: String, forContentOwner: Boolean, videoLicense: String, channelId: String, q: String, maxResults: Int, part: String, videoEmbeddable: String, publishedBefore: String, order: String, regionCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/search")).addQueryStringParameters("forDeveloper" -> forDeveloper.toString, "publishedAfter" -> publishedAfter.toString, "locationRadius" -> locationRadius.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "safeSearch" -> safeSearch.toString, "type" -> `type`.toString, "eventType" -> eventType.toString, "channelType" -> channelType.toString, "topicId" -> topicId.toString, "location" -> location.toString, "videoPaidProductPlacement" -> videoPaidProductPlacement.toString, "videoDefinition" -> videoDefinition.toString, "videoCaption" -> videoCaption.toString, "forMine" -> forMine.toString, "pageToken" -> pageToken.toString, "videoType" -> videoType.toString, "relevanceLanguage" -> relevanceLanguage.toString, "videoDimension" -> videoDimension.toString, "videoCategoryId" -> videoCategoryId.toString, "videoDuration" -> videoDuration.toString, "videoSyndicated" -> videoSyndicated.toString, "forContentOwner" -> forContentOwner.toString, "videoLicense" -> videoLicense.toString, "channelId" -> channelId.toString, "q" -> q.toString, "maxResults" -> maxResults.toString, "part" -> part.toString, "videoEmbeddable" -> videoEmbeddable.toString, "publishedBefore" -> publishedBefore.toString, "order" -> order.toString, "regionCode" -> regionCode.toString))
			given Conversion[list, Future[Schema.SearchListResponse]] = (fun: list) => fun.apply()
		}
	}
	object members {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MemberListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.MemberListResponse])
		}
		object list {
			def apply(hasAccessToLevel: String, maxResults: Int, pageToken: String, filterByMemberChannelId: String, part: String, mode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/members")).addQueryStringParameters("hasAccessToLevel" -> hasAccessToLevel.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filterByMemberChannelId" -> filterByMemberChannelId.toString, "part" -> part.toString, "mode" -> mode.toString))
			given Conversion[list, Future[Schema.MemberListResponse]] = (fun: list) => fun.apply()
		}
	}
	object captions {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCaption(body: Schema.Caption) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Caption])
		}
		object insert {
			def apply(sync: Boolean, part: String, onBehalfOf: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/captions")).addQueryStringParameters("sync" -> sync.toString, "part" -> part.toString, "onBehalfOf" -> onBehalfOf.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(onBehalfOf: String, id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/captions")).addQueryStringParameters("onBehalfOf" -> onBehalfOf.toString, "id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCaption(body: Schema.Caption) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Caption])
		}
		object update {
			def apply(onBehalfOf: String, part: String, onBehalfOfContentOwner: String, sync: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/captions")).addQueryStringParameters("onBehalfOf" -> onBehalfOf.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "sync" -> sync.toString))
		}
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("GET").map(_ => ())
		}
		object download {
			def apply(tlang: String, id: String, tfmt: String, onBehalfOf: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"youtube/v3/captions/${id}")).addQueryStringParameters("tlang" -> tlang.toString, "tfmt" -> tfmt.toString, "onBehalfOf" -> onBehalfOf.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CaptionListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.CaptionListResponse])
		}
		object list {
			def apply(part: String, onBehalfOf: String, videoId: String, onBehalfOfContentOwner: String, id: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/captions")).addQueryStringParameters("part" -> part.toString, "onBehalfOf" -> onBehalfOf.toString, "videoId" -> videoId.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[list, Future[Schema.CaptionListResponse]] = (fun: list) => fun.apply()
		}
	}
	object youtube {
		object v3 {
			class updateCommentThreads(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCommentThread(body: Schema.CommentThread) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.CommentThread])
			}
			object updateCommentThreads {
				def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): updateCommentThreads = new updateCommentThreads(auth(ws.url(BASE_URL + s"youtube/v3/commentThreads")).addQueryStringParameters("part" -> part.toString))
			}
			object liveChat {
				object messages {
					class stream(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessageListResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.LiveChatMessageListResponse])
					}
					object stream {
						def apply(hl: String, pageToken: String, profileImageSize: Int, part: String, maxResults: Int, liveChatId: String)(using auth: AuthToken, ec: ExecutionContext): stream = new stream(auth(ws.url(BASE_URL + s"youtube/v3/liveChat/messages/stream")).addQueryStringParameters("hl" -> hl.toString, "pageToken" -> pageToken.toString, "profileImageSize" -> profileImageSize.toString, "part" -> part.toString, "maxResults" -> maxResults.toString, "liveChatId" -> liveChatId.toString))
						given Conversion[stream, Future[Schema.LiveChatMessageListResponse]] = (fun: stream) => fun.apply()
					}
				}
			}
		}
	}
	object i18nLanguages {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.I18nLanguageListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.I18nLanguageListResponse])
		}
		object list {
			def apply(part: String, hl: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/i18nLanguages")).addQueryStringParameters("part" -> part.toString, "hl" -> hl.toString))
			given Conversion[list, Future[Schema.I18nLanguageListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlistImages {
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylistImage(body: Schema.PlaylistImage) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.PlaylistImage])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"youtube/v3/playlistImages")).addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/playlistImages")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlaylistImageListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlaylistImageListResponse])
		}
		object list {
			def apply(maxResults: Int, part: String, onBehalfOfContentOwnerChannel: String, pageToken: String, onBehalfOfContentOwner: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/playlistImages")).addQueryStringParameters("maxResults" -> maxResults.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "parent" -> parent.toString))
			given Conversion[list, Future[Schema.PlaylistImageListResponse]] = (fun: list) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlaylistImage(body: Schema.PlaylistImage) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PlaylistImage])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/playlistImages")).addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
	}
	object channelBanners {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannelBannerResource(body: Schema.ChannelBannerResource) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ChannelBannerResource])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/channelBanners/insert")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "channelId" -> channelId.toString))
		}
	}
	object subscriptions {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"youtube/v3/subscriptions")).addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SubscriptionListResponse])
		}
		object list {
			def apply(id: String, myRecentSubscribers: Boolean, onBehalfOfContentOwner: String, pageToken: String, order: String, mySubscribers: Boolean, mine: Boolean, forChannelId: String, onBehalfOfContentOwnerChannel: String, channelId: String, maxResults: Int, part: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"youtube/v3/subscriptions")).addQueryStringParameters("id" -> id.toString, "myRecentSubscribers" -> myRecentSubscribers.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageToken" -> pageToken.toString, "order" -> order.toString, "mySubscribers" -> mySubscribers.toString, "mine" -> mine.toString, "forChannelId" -> forChannelId.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "channelId" -> channelId.toString, "maxResults" -> maxResults.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.SubscriptionListResponse]] = (fun: list) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSubscription(body: Schema.Subscription) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Subscription])
		}
		object insert {
			def apply(part: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"youtube/v3/subscriptions")).addQueryStringParameters("part" -> part.toString))
		}
	}
}
