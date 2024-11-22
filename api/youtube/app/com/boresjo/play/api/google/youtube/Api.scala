package com.boresjo.play.api.google.youtube

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/youtube.upload""" /* Manage your YouTube videos */,
		"""https://www.googleapis.com/auth/youtube.readonly""" /* View your YouTube account */,
		"""https://www.googleapis.com/auth/youtube""" /* Manage your YouTube account */,
		"""https://www.googleapis.com/auth/youtubepartner""" /* View and manage your assets and associated content on YouTube */,
		"""https://www.googleapis.com/auth/youtube.channel-memberships.creator""" /* See a list of your current active channel members, their current level, and when they became a member */,
		"""https://www.googleapis.com/auth/youtubepartner-channel-audit""" /* View private information of your YouTube channel relevant during the audit process with a YouTube partner */,
		"""https://www.googleapis.com/auth/youtube.force-ssl""" /* See, edit, and permanently delete your YouTube videos, ratings, comments and captions */
	)

	private val BASE_URL = "https://youtube.googleapis.com/"

	object membershipsLevels {
		/** Retrieves a list of all pricing levels offered by a creator to the fans. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MembershipsLevelListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.channel-memberships.creator""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MembershipsLevelListResponse])
		}
		object list {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/membershipsLevels").addQueryStringParameters("part" -> part.toString))
			given Conversion[list, Future[Schema.MembershipsLevelListResponse]] = (fun: list) => fun.apply()
		}
	}
	object superChatEvents {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SuperChatEventListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SuperChatEventListResponse])
		}
		object list {
			def apply(pageToken: String, hl: String, maxResults: Int, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/superChatEvents").addQueryStringParameters("pageToken" -> pageToken.toString, "hl" -> hl.toString, "maxResults" -> maxResults.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.SuperChatEventListResponse]] = (fun: list) => fun.apply()
		}
	}
	object thirdPartyLinks {
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withThirdPartyLink(body: Schema.ThirdPartyLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ThirdPartyLink])
		}
		object update {
			def apply(part: String, externalChannelId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks").addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(part: String, `type`: String, linkingToken: String, externalChannelId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks").addQueryStringParameters("part" -> part.toString, "type" -> `type`.toString, "linkingToken" -> linkingToken.toString, "externalChannelId" -> externalChannelId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withThirdPartyLink(body: Schema.ThirdPartyLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ThirdPartyLink])
		}
		object insert {
			def apply(part: String, externalChannelId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks").addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ThirdPartyLinkListResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ThirdPartyLinkListResponse])
		}
		object list {
			def apply(externalChannelId: String, `type`: String, linkingToken: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/thirdPartyLinks").addQueryStringParameters("externalChannelId" -> externalChannelId.toString, "type" -> `type`.toString, "linkingToken" -> linkingToken.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.ThirdPartyLinkListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveChatMessages {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveChatMessage(body: Schema.LiveChatMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiveChatMessage])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/liveChat/messages").addQueryStringParameters("part" -> part.toString))
		}
		/** Deletes a chat message. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/liveChat/messages").addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Transition a durable chat event. */
		class transition(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessage]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiveChatMessage])
		}
		object transition {
			def apply(status: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): transition = new transition(ws.url(BASE_URL + s"youtube/v3/liveChat/messages/transition").addQueryStringParameters("status" -> status.toString, "id" -> id.toString))
			given Conversion[transition, Future[Schema.LiveChatMessage]] = (fun: transition) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessageListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiveChatMessageListResponse])
		}
		object list {
			def apply(part: String, pageToken: String, hl: String, profileImageSize: Int, maxResults: Int, liveChatId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/liveChat/messages").addQueryStringParameters("part" -> part.toString, "pageToken" -> pageToken.toString, "hl" -> hl.toString, "profileImageSize" -> profileImageSize.toString, "maxResults" -> maxResults.toString, "liveChatId" -> liveChatId.toString))
			given Conversion[list, Future[Schema.LiveChatMessageListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlistItems {
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylistItem(body: Schema.PlaylistItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PlaylistItem])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/playlistItems").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylistItem(body: Schema.PlaylistItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PlaylistItem])
		}
		object insert {
			def apply(part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/playlistItems").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/playlistItems").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlaylistItemListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlaylistItemListResponse])
		}
		object list {
			def apply(playlistId: String, part: String, onBehalfOfContentOwner: String, videoId: String, maxResults: Int, pageToken: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/playlistItems").addQueryStringParameters("playlistId" -> playlistId.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "videoId" -> videoId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "id" -> id.toString))
			given Conversion[list, Future[Schema.PlaylistItemListResponse]] = (fun: list) => fun.apply()
		}
	}
	object commentThreads {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withCommentThread(body: Schema.CommentThread) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommentThread])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/commentThreads").addQueryStringParameters("part" -> part.toString))
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CommentThreadListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CommentThreadListResponse])
		}
		object list {
			def apply(order: String, videoId: String, id: String, part: String, channelId: String, pageToken: String, searchTerms: String, maxResults: Int, moderationStatus: String, textFormat: String, allThreadsRelatedToChannelId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/commentThreads").addQueryStringParameters("order" -> order.toString, "videoId" -> videoId.toString, "id" -> id.toString, "part" -> part.toString, "channelId" -> channelId.toString, "pageToken" -> pageToken.toString, "searchTerms" -> searchTerms.toString, "maxResults" -> maxResults.toString, "moderationStatus" -> moderationStatus.toString, "textFormat" -> textFormat.toString, "allThreadsRelatedToChannelId" -> allThreadsRelatedToChannelId.toString))
			given Conversion[list, Future[Schema.CommentThreadListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveBroadcasts {
		/** Inserts a new stream for the authenticated user. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveBroadcast(body: Schema.LiveBroadcast) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiveBroadcast])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts").addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		/** Delete a given broadcast. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Transition a broadcast to a given status. */
		class transition(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcast]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiveBroadcast])
		}
		object transition {
			def apply(id: String, broadcastStatus: String, part: String, onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): transition = new transition(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/transition").addQueryStringParameters("id" -> id.toString, "broadcastStatus" -> broadcastStatus.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[transition, Future[Schema.LiveBroadcast]] = (fun: transition) => fun.apply()
		}
		/** Updates an existing broadcast for the authenticated user. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveBroadcast(body: Schema.LiveBroadcast) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LiveBroadcast])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString))
		}
		/** Retrieve the list of broadcasts associated with the given channel. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcastListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiveBroadcastListResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, broadcastStatus: String, part: String, broadcastType: String, id: String, maxResults: Int, mine: Boolean, pageToken: String, onBehalfOfContentOwnerChannel: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "broadcastStatus" -> broadcastStatus.toString, "part" -> part.toString, "broadcastType" -> broadcastType.toString, "id" -> id.toString, "maxResults" -> maxResults.toString, "mine" -> mine.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
			given Conversion[list, Future[Schema.LiveBroadcastListResponse]] = (fun: list) => fun.apply()
		}
		/** Insert cuepoints in a broadcast */
		class insertCuepoint(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withCuepoint(body: Schema.Cuepoint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Cuepoint])
		}
		object insertCuepoint {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, part: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): insertCuepoint = new insertCuepoint(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/cuepoint").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString, "id" -> id.toString))
		}
		/** Bind a broadcast to a stream. */
		class bind(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveBroadcast]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiveBroadcast])
		}
		object bind {
			def apply(onBehalfOfContentOwner: String, streamId: String, id: String, onBehalfOfContentOwnerChannel: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): bind = new bind(ws.url(BASE_URL + s"youtube/v3/liveBroadcasts/bind").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "streamId" -> streamId.toString, "id" -> id.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString))
			given Conversion[bind, Future[Schema.LiveBroadcast]] = (fun: bind) => fun.apply()
		}
	}
	object videoAbuseReportReasons {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VideoAbuseReportReasonListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VideoAbuseReportReasonListResponse])
		}
		object list {
			def apply(part: String, hl: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/videoAbuseReportReasons").addQueryStringParameters("part" -> part.toString, "hl" -> hl.toString))
			given Conversion[list, Future[Schema.VideoAbuseReportReasonListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlists {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlaylistListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlaylistListResponse])
		}
		object list {
			def apply(channelId: String, mine: Boolean, maxResults: Int, pageToken: String, id: String, part: String, hl: String, onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/playlists").addQueryStringParameters("channelId" -> channelId.toString, "mine" -> mine.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "id" -> id.toString, "part" -> part.toString, "hl" -> hl.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.PlaylistListResponse]] = (fun: list) => fun.apply()
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylist(body: Schema.Playlist) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Playlist])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/playlists").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylist(body: Schema.Playlist) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Playlist])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String, part: String, onBehalfOfContentOwnerChannel: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/playlists").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/playlists").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object activities {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ActivityListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ActivityListResponse])
		}
		object list {
			def apply(channelId: String, mine: Boolean, publishedBefore: String, regionCode: String, maxResults: Int, pageToken: String, part: String, publishedAfter: String, home: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/activities").addQueryStringParameters("channelId" -> channelId.toString, "mine" -> mine.toString, "publishedBefore" -> publishedBefore.toString, "regionCode" -> regionCode.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "part" -> part.toString, "publishedAfter" -> publishedAfter.toString, "home" -> home.toString))
			given Conversion[list, Future[Schema.ActivityListResponse]] = (fun: list) => fun.apply()
		}
	}
	object tests {
		/** POST method. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def withTestItem(body: Schema.TestItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestItem])
		}
		object insert {
			def apply(part: String, externalChannelId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/tests").addQueryStringParameters("part" -> part.toString, "externalChannelId" -> externalChannelId.toString))
		}
	}
	object liveChatBans {
		/** Deletes a chat ban. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/liveChat/bans").addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveChatBan(body: Schema.LiveChatBan) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiveChatBan])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/liveChat/bans").addQueryStringParameters("part" -> part.toString))
		}
	}
	object comments {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withComment(body: Schema.Comment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/comments").addQueryStringParameters("part" -> part.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/comments").addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Expresses the caller's opinion that one or more comments should be flagged as spam. */
		class markAsSpam(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object markAsSpam {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): markAsSpam = new markAsSpam(ws.url(BASE_URL + s"youtube/v3/comments/markAsSpam").addQueryStringParameters("id" -> id.toString))
			given Conversion[markAsSpam, Future[Unit]] = (fun: markAsSpam) => fun.apply()
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withComment(body: Schema.Comment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Comment])
		}
		object update {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/comments").addQueryStringParameters("part" -> part.toString))
		}
		/** Sets the moderation status of one or more comments. */
		class setModerationStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object setModerationStatus {
			def apply(moderationStatus: String, banAuthor: Boolean, id: String)(using signer: RequestSigner, ec: ExecutionContext): setModerationStatus = new setModerationStatus(ws.url(BASE_URL + s"youtube/v3/comments/setModerationStatus").addQueryStringParameters("moderationStatus" -> moderationStatus.toString, "banAuthor" -> banAuthor.toString, "id" -> id.toString))
			given Conversion[setModerationStatus, Future[Unit]] = (fun: setModerationStatus) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CommentListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CommentListResponse])
		}
		object list {
			def apply(part: String, parentId: String, pageToken: String, id: String, textFormat: String, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/comments").addQueryStringParameters("part" -> part.toString, "parentId" -> parentId.toString, "pageToken" -> pageToken.toString, "id" -> id.toString, "textFormat" -> textFormat.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.CommentListResponse]] = (fun: list) => fun.apply()
		}
	}
	object watermarks {
		/** Allows upload of watermark image and setting it for a channel. */
		class set(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.upload""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withInvideoBranding(body: Schema.InvideoBranding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object set {
			def apply(onBehalfOfContentOwner: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): set = new set(ws.url(BASE_URL + s"youtube/v3/watermarks/set").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "channelId" -> channelId.toString))
		}
		/** Allows removal of channel watermark. */
		class unset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object unset {
			def apply(channelId: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): unset = new unset(ws.url(BASE_URL + s"youtube/v3/watermarks/unset").addQueryStringParameters("channelId" -> channelId.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[unset, Future[Unit]] = (fun: unset) => fun.apply()
		}
	}
	object i18nRegions {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.I18nRegionListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.I18nRegionListResponse])
		}
		object list {
			def apply(hl: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/i18nRegions").addQueryStringParameters("hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.I18nRegionListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveStreams {
		/** Deletes an existing stream for the authenticated user. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/liveStreams").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieve the list of streams associated with the given channel. -- */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveStreamListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiveStreamListResponse])
		}
		object list {
			def apply(id: String, part: String, maxResults: Int, mine: Boolean, onBehalfOfContentOwnerChannel: String, pageToken: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/liveStreams").addQueryStringParameters("id" -> id.toString, "part" -> part.toString, "maxResults" -> maxResults.toString, "mine" -> mine.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.LiveStreamListResponse]] = (fun: list) => fun.apply()
		}
		/** Updates an existing stream for the authenticated user. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveStream(body: Schema.LiveStream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LiveStream])
		}
		object update {
			def apply(onBehalfOfContentOwnerChannel: String, part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/liveStreams").addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Inserts a new stream for the authenticated user. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveStream(body: Schema.LiveStream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiveStream])
		}
		object insert {
			def apply(part: String, onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/liveStreams").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString))
		}
	}
	object abuseReports {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withAbuseReport(body: Schema.AbuseReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AbuseReport])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/abuseReports").addQueryStringParameters("part" -> part.toString))
		}
	}
	object videoCategories {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VideoCategoryListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VideoCategoryListResponse])
		}
		object list {
			def apply(id: String, regionCode: String, hl: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/videoCategories").addQueryStringParameters("id" -> id.toString, "regionCode" -> regionCode.toString, "hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.VideoCategoryListResponse]] = (fun: list) => fun.apply()
		}
	}
	object videos {
		/** Adds a like or dislike rating to a video or removes a rating from a video. */
		class rate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object rate {
			def apply(rating: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): rate = new rate(ws.url(BASE_URL + s"youtube/v3/videos/rate").addQueryStringParameters("rating" -> rating.toString, "id" -> id.toString))
			given Conversion[rate, Future[Unit]] = (fun: rate) => fun.apply()
		}
		/** Report abuse for a video. */
		class reportAbuse(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withVideoAbuseReport(body: Schema.VideoAbuseReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object reportAbuse {
			def apply(onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): reportAbuse = new reportAbuse(ws.url(BASE_URL + s"youtube/v3/videos/reportAbuse").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.upload""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withVideo(body: Schema.Video) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Video])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, autoLevels: Boolean, onBehalfOfContentOwner: String, stabilize: Boolean, part: String, notifySubscribers: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/videos").addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "autoLevels" -> autoLevels.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "stabilize" -> stabilize.toString, "part" -> part.toString, "notifySubscribers" -> notifySubscribers.toString))
		}
		/** Retrieves the ratings that the authorized user gave to a list of specified videos. */
		class getRating(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VideoGetRatingResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VideoGetRatingResponse])
		}
		object getRating {
			def apply(onBehalfOfContentOwner: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): getRating = new getRating(ws.url(BASE_URL + s"youtube/v3/videos/getRating").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[getRating, Future[Schema.VideoGetRatingResponse]] = (fun: getRating) => fun.apply()
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/videos").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withVideo(body: Schema.Video) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Video])
		}
		object update {
			def apply(onBehalfOfContentOwner: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/videos").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VideoListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VideoListResponse])
		}
		object list {
			def apply(myRating: String, id: String, hl: String, locale: String, maxHeight: Int, chart: String, onBehalfOfContentOwner: String, maxResults: Int, pageToken: String, maxWidth: Int, part: String, regionCode: String, videoCategoryId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/videos").addQueryStringParameters("myRating" -> myRating.toString, "id" -> id.toString, "hl" -> hl.toString, "locale" -> locale.toString, "maxHeight" -> maxHeight.toString, "chart" -> chart.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "maxWidth" -> maxWidth.toString, "part" -> part.toString, "regionCode" -> regionCode.toString, "videoCategoryId" -> videoCategoryId.toString))
			given Conversion[list, Future[Schema.VideoListResponse]] = (fun: list) => fun.apply()
		}
	}
	object channelSections {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ChannelSectionListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ChannelSectionListResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, mine: Boolean, id: String, channelId: String, hl: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/channelSections").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "mine" -> mine.toString, "id" -> id.toString, "channelId" -> channelId.toString, "hl" -> hl.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.ChannelSectionListResponse]] = (fun: list) => fun.apply()
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withChannelSection(body: Schema.ChannelSection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ChannelSection])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/channelSections").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withChannelSection(body: Schema.ChannelSection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ChannelSection])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/channelSections").addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/channelSections").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object channels {
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Channel])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/channels").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ChannelListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/youtubepartner-channel-audit""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ChannelListResponse])
		}
		object list {
			def apply(pageToken: String, forHandle: String, part: String, id: String, hl: String, mine: Boolean, forUsername: String, mySubscribers: Boolean, categoryId: String, maxResults: Int, managedByMe: Boolean, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/channels").addQueryStringParameters("pageToken" -> pageToken.toString, "forHandle" -> forHandle.toString, "part" -> part.toString, "id" -> id.toString, "hl" -> hl.toString, "mine" -> mine.toString, "forUsername" -> forUsername.toString, "mySubscribers" -> mySubscribers.toString, "categoryId" -> categoryId.toString, "maxResults" -> maxResults.toString, "managedByMe" -> managedByMe.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[list, Future[Schema.ChannelListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liveChatModerators {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def withLiveChatModerator(body: Schema.LiveChatModerator) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiveChatModerator])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators").addQueryStringParameters("part" -> part.toString))
		}
		/** Deletes a chat moderator. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators").addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveChatModeratorListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiveChatModeratorListResponse])
		}
		object list {
			def apply(liveChatId: String, maxResults: Int, pageToken: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/liveChat/moderators").addQueryStringParameters("liveChatId" -> liveChatId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.LiveChatModeratorListResponse]] = (fun: list) => fun.apply()
		}
	}
	object thumbnails {
		/** As this is not an insert in a strict sense (it supports uploading/setting of a thumbnail for multiple videos, which doesn't result in creation of a single resource), I use a custom verb here. */
		class set(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ThumbnailSetResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.upload""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ThumbnailSetResponse])
		}
		object set {
			def apply(onBehalfOfContentOwner: String, videoId: String)(using signer: RequestSigner, ec: ExecutionContext): set = new set(ws.url(BASE_URL + s"youtube/v3/thumbnails/set").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "videoId" -> videoId.toString))
			given Conversion[set, Future[Schema.ThumbnailSetResponse]] = (fun: set) => fun.apply()
		}
	}
	object search {
		/** Retrieves a list of search resources */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchListResponse])
		}
		object list {
			def apply(forDeveloper: Boolean, publishedAfter: String, locationRadius: String, onBehalfOfContentOwner: String, safeSearch: String, `type`: String, eventType: String, channelType: String, topicId: String, location: String, videoPaidProductPlacement: String, videoDefinition: String, videoCaption: String, forMine: Boolean, pageToken: String, videoType: String, relevanceLanguage: String, videoDimension: String, videoCategoryId: String, videoDuration: String, videoSyndicated: String, forContentOwner: Boolean, videoLicense: String, channelId: String, q: String, maxResults: Int, part: String, videoEmbeddable: String, publishedBefore: String, order: String, regionCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/search").addQueryStringParameters("forDeveloper" -> forDeveloper.toString, "publishedAfter" -> publishedAfter.toString, "locationRadius" -> locationRadius.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "safeSearch" -> safeSearch.toString, "type" -> `type`.toString, "eventType" -> eventType.toString, "channelType" -> channelType.toString, "topicId" -> topicId.toString, "location" -> location.toString, "videoPaidProductPlacement" -> videoPaidProductPlacement.toString, "videoDefinition" -> videoDefinition.toString, "videoCaption" -> videoCaption.toString, "forMine" -> forMine.toString, "pageToken" -> pageToken.toString, "videoType" -> videoType.toString, "relevanceLanguage" -> relevanceLanguage.toString, "videoDimension" -> videoDimension.toString, "videoCategoryId" -> videoCategoryId.toString, "videoDuration" -> videoDuration.toString, "videoSyndicated" -> videoSyndicated.toString, "forContentOwner" -> forContentOwner.toString, "videoLicense" -> videoLicense.toString, "channelId" -> channelId.toString, "q" -> q.toString, "maxResults" -> maxResults.toString, "part" -> part.toString, "videoEmbeddable" -> videoEmbeddable.toString, "publishedBefore" -> publishedBefore.toString, "order" -> order.toString, "regionCode" -> regionCode.toString))
			given Conversion[list, Future[Schema.SearchListResponse]] = (fun: list) => fun.apply()
		}
	}
	object members {
		/** Retrieves a list of members that match the request criteria for a channel. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MemberListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.channel-memberships.creator""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MemberListResponse])
		}
		object list {
			def apply(hasAccessToLevel: String, maxResults: Int, pageToken: String, filterByMemberChannelId: String, part: String, mode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/members").addQueryStringParameters("hasAccessToLevel" -> hasAccessToLevel.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filterByMemberChannelId" -> filterByMemberChannelId.toString, "part" -> part.toString, "mode" -> mode.toString))
			given Conversion[list, Future[Schema.MemberListResponse]] = (fun: list) => fun.apply()
		}
	}
	object captions {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withCaption(body: Schema.Caption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Caption])
		}
		object insert {
			def apply(sync: Boolean, part: String, onBehalfOf: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/captions").addQueryStringParameters("sync" -> sync.toString, "part" -> part.toString, "onBehalfOf" -> onBehalfOf.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(onBehalfOf: String, id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/captions").addQueryStringParameters("onBehalfOf" -> onBehalfOf.toString, "id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withCaption(body: Schema.Caption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Caption])
		}
		object update {
			def apply(onBehalfOf: String, part: String, onBehalfOfContentOwner: String, sync: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/captions").addQueryStringParameters("onBehalfOf" -> onBehalfOf.toString, "part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "sync" -> sync.toString))
		}
		/** Downloads a caption track. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_ => ())
		}
		object download {
			def apply(tlang: String, id: String, tfmt: String, onBehalfOf: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"youtube/v3/captions/${id}").addQueryStringParameters("tlang" -> tlang.toString, "tfmt" -> tfmt.toString, "onBehalfOf" -> onBehalfOf.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CaptionListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CaptionListResponse])
		}
		object list {
			def apply(part: String, onBehalfOf: String, videoId: String, onBehalfOfContentOwner: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/captions").addQueryStringParameters("part" -> part.toString, "onBehalfOf" -> onBehalfOf.toString, "videoId" -> videoId.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[list, Future[Schema.CaptionListResponse]] = (fun: list) => fun.apply()
		}
	}
	object youtube {
		object v3 {
			/** Updates an existing resource. */
			class updateCommentThreads(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCommentThread(body: Schema.CommentThread) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CommentThread])
			}
			object updateCommentThreads {
				def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): updateCommentThreads = new updateCommentThreads(ws.url(BASE_URL + s"youtube/v3/commentThreads").addQueryStringParameters("part" -> part.toString))
			}
			object liveChat {
				object messages {
					/** Allows a user to load live chat through a server-streamed RPC. */
					class stream(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiveChatMessageListResponse]) {
						val scopes = Seq()
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiveChatMessageListResponse])
					}
					object stream {
						def apply(hl: String, pageToken: String, profileImageSize: Int, part: String, maxResults: Int, liveChatId: String)(using signer: RequestSigner, ec: ExecutionContext): stream = new stream(ws.url(BASE_URL + s"youtube/v3/liveChat/messages/stream").addQueryStringParameters("hl" -> hl.toString, "pageToken" -> pageToken.toString, "profileImageSize" -> profileImageSize.toString, "part" -> part.toString, "maxResults" -> maxResults.toString, "liveChatId" -> liveChatId.toString))
						given Conversion[stream, Future[Schema.LiveChatMessageListResponse]] = (fun: stream) => fun.apply()
					}
				}
			}
		}
	}
	object i18nLanguages {
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.I18nLanguageListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.I18nLanguageListResponse])
		}
		object list {
			def apply(part: String, hl: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/i18nLanguages").addQueryStringParameters("part" -> part.toString, "hl" -> hl.toString))
			given Conversion[list, Future[Schema.I18nLanguageListResponse]] = (fun: list) => fun.apply()
		}
	}
	object playlistImages {
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylistImage(body: Schema.PlaylistImage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PlaylistImage])
		}
		object update {
			def apply(part: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"youtube/v3/playlistImages").addQueryStringParameters("part" -> part.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/playlistImages").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlaylistImageListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlaylistImageListResponse])
		}
		object list {
			def apply(maxResults: Int, part: String, onBehalfOfContentOwnerChannel: String, pageToken: String, onBehalfOfContentOwner: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/playlistImages").addQueryStringParameters("maxResults" -> maxResults.toString, "part" -> part.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "pageToken" -> pageToken.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "parent" -> parent.toString))
			given Conversion[list, Future[Schema.PlaylistImageListResponse]] = (fun: list) => fun.apply()
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withPlaylistImage(body: Schema.PlaylistImage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PlaylistImage])
		}
		object insert {
			def apply(onBehalfOfContentOwnerChannel: String, onBehalfOfContentOwner: String, part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/playlistImages").addQueryStringParameters("onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "part" -> part.toString))
		}
	}
	object channelBanners {
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.upload""")
			/** Perform the request */
			def withChannelBannerResource(body: Schema.ChannelBannerResource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ChannelBannerResource])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String, onBehalfOfContentOwnerChannel: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/channelBanners/insert").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "channelId" -> channelId.toString))
		}
	}
	object subscriptions {
		/** Deletes a resource. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"youtube/v3/subscriptions").addQueryStringParameters("id" -> id.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a list of resources, possibly filtered. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionListResponse])
		}
		object list {
			def apply(id: String, myRecentSubscribers: Boolean, onBehalfOfContentOwner: String, pageToken: String, order: String, mySubscribers: Boolean, mine: Boolean, forChannelId: String, onBehalfOfContentOwnerChannel: String, channelId: String, maxResults: Int, part: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"youtube/v3/subscriptions").addQueryStringParameters("id" -> id.toString, "myRecentSubscribers" -> myRecentSubscribers.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageToken" -> pageToken.toString, "order" -> order.toString, "mySubscribers" -> mySubscribers.toString, "mine" -> mine.toString, "forChannelId" -> forChannelId.toString, "onBehalfOfContentOwnerChannel" -> onBehalfOfContentOwnerChannel.toString, "channelId" -> channelId.toString, "maxResults" -> maxResults.toString, "part" -> part.toString))
			given Conversion[list, Future[Schema.SubscriptionListResponse]] = (fun: list) => fun.apply()
		}
		/** Inserts a new resource into this collection. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.force-ssl""", """https://www.googleapis.com/auth/youtubepartner""")
			/** Perform the request */
			def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object insert {
			def apply(part: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"youtube/v3/subscriptions").addQueryStringParameters("part" -> part.toString))
		}
	}
}
