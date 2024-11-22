package com.boresjo.play.api.google.games

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
		"""https://www.googleapis.com/auth/androidpublisher""" /* View and manage your Google Play Developer account */,
		"""https://www.googleapis.com/auth/drive.appdata""" /* See, create, and delete its own configuration data in your Google Drive */,
		"""https://www.googleapis.com/auth/games""" /* Create, edit, and delete your Google Play Games activity */
	)

	private val BASE_URL = "https://games.googleapis.com/"

	object achievementDefinitions {
		/** Lists all the achievement definitions for your application. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementDefinitionsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AchievementDefinitionsListResponse])
		}
		object list {
			def apply(language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/achievements").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AchievementDefinitionsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object achievements {
		/** Sets the state of the achievement with the given ID to `REVEALED` for the currently authenticated player. */
		class reveal(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementRevealResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementRevealResponse])
		}
		object reveal {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): reveal = new reveal(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/reveal").addQueryStringParameters())
			given Conversion[reveal, Future[Schema.AchievementRevealResponse]] = (fun: reveal) => fun.apply()
		}
		/** Sets the steps for the currently authenticated player towards unlocking an achievement. If the steps parameter is less than the current number of steps that the player already gained for the achievement, the achievement is not modified. */
		class setStepsAtLeast(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementSetStepsAtLeastResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementSetStepsAtLeastResponse])
		}
		object setStepsAtLeast {
			def apply(achievementId: String, steps: Int)(using signer: RequestSigner, ec: ExecutionContext): setStepsAtLeast = new setStepsAtLeast(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/setStepsAtLeast").addQueryStringParameters("steps" -> steps.toString))
			given Conversion[setStepsAtLeast, Future[Schema.AchievementSetStepsAtLeastResponse]] = (fun: setStepsAtLeast) => fun.apply()
		}
		/** Unlocks this achievement for the currently authenticated player. */
		class unlock(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementUnlockResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementUnlockResponse])
		}
		object unlock {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): unlock = new unlock(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/unlock").addQueryStringParameters())
			given Conversion[unlock, Future[Schema.AchievementUnlockResponse]] = (fun: unlock) => fun.apply()
		}
		/** Updates multiple achievements for the currently authenticated player. */
		class updateMultiple(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withAchievementUpdateMultipleRequest(body: Schema.AchievementUpdateMultipleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AchievementUpdateMultipleResponse])
		}
		object updateMultiple {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): updateMultiple = new updateMultiple(ws.url(BASE_URL + s"games/v1/achievements/updateMultiple").addQueryStringParameters())
		}
		/** Increments the steps of the achievement with the given ID for the currently authenticated player. */
		class increment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementIncrementResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementIncrementResponse])
		}
		object increment {
			def apply(achievementId: String, requestId: String, stepsToIncrement: Int)(using signer: RequestSigner, ec: ExecutionContext): increment = new increment(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/increment").addQueryStringParameters("requestId" -> requestId.toString, "stepsToIncrement" -> stepsToIncrement.toString))
			given Conversion[increment, Future[Schema.AchievementIncrementResponse]] = (fun: increment) => fun.apply()
		}
		/** Lists the progress for all your application's achievements for the currently authenticated player. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerAchievementListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlayerAchievementListResponse])
		}
		object list {
			def apply(playerId: String, language: String, maxResults: Int, pageToken: String, state: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/players/${playerId}/achievements").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "state" -> state.toString))
			given Conversion[list, Future[Schema.PlayerAchievementListResponse]] = (fun: list) => fun.apply()
		}
	}
	object applications {
		/** Retrieves the metadata of the application with the given ID. If the requested application is not available for the specified `platformType`, the returned response will not include any instance data. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Application])
		}
		object get {
			def apply(applicationId: String, language: String, platformType: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/applications/${applicationId}").addQueryStringParameters("language" -> language.toString, "platformType" -> platformType.toString))
			given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
		}
		/** Indicate that the currently authenticated user is playing your application. */
		class played(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object played {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): played = new played(ws.url(BASE_URL + s"games/v1/applications/played").addQueryStringParameters())
			given Conversion[played, Future[Unit]] = (fun: played) => fun.apply()
		}
		/** Verifies the auth token provided with this request is for the application with the specified ID, and returns the ID of the player it was granted for. */
		class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApplicationVerifyResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApplicationVerifyResponse])
		}
		object verify {
			def apply(applicationId: String)(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"games/v1/applications/${applicationId}/verify").addQueryStringParameters())
			given Conversion[verify, Future[Schema.ApplicationVerifyResponse]] = (fun: verify) => fun.apply()
		}
		/** Returns a URL for the requested end point type. */
		class getEndPoint(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EndPoint]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.EndPoint])
		}
		object getEndPoint {
			def apply(applicationId: String, endPointType: String)(using signer: RequestSigner, ec: ExecutionContext): getEndPoint = new getEndPoint(ws.url(BASE_URL + s"games/v1/applications/getEndPoint").addQueryStringParameters("applicationId" -> applicationId.toString, "endPointType" -> endPointType.toString))
			given Conversion[getEndPoint, Future[Schema.EndPoint]] = (fun: getEndPoint) => fun.apply()
		}
	}
	object events {
		/** Returns a list showing the current progress on events in this application for the currently authenticated user. */
		class listByPlayer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerEventListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlayerEventListResponse])
		}
		object listByPlayer {
			def apply(language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listByPlayer = new listByPlayer(ws.url(BASE_URL + s"games/v1/events").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listByPlayer, Future[Schema.PlayerEventListResponse]] = (fun: listByPlayer) => fun.apply()
		}
		/** Returns a list of the event definitions in this application. */
		class listDefinitions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventDefinitionListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventDefinitionListResponse])
		}
		object listDefinitions {
			def apply(language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listDefinitions = new listDefinitions(ws.url(BASE_URL + s"games/v1/eventDefinitions").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listDefinitions, Future[Schema.EventDefinitionListResponse]] = (fun: listDefinitions) => fun.apply()
		}
		/** Records a batch of changes to the number of times events have occurred for the currently authenticated user of this application. */
		class record(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withEventRecordRequest(body: Schema.EventRecordRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventUpdateResponse])
		}
		object record {
			def apply(language: String)(using signer: RequestSigner, ec: ExecutionContext): record = new record(ws.url(BASE_URL + s"games/v1/events").addQueryStringParameters("language" -> language.toString))
		}
	}
	object leaderboards {
		/** Retrieves the metadata of the leaderboard with the given ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Leaderboard]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Leaderboard])
		}
		object get {
			def apply(leaderboardId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}").addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Leaderboard]] = (fun: get) => fun.apply()
		}
		/** Lists all the leaderboard metadata for your application. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LeaderboardListResponse])
		}
		object list {
			def apply(language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/leaderboards").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardListResponse]] = (fun: list) => fun.apply()
		}
	}
	object metagame {
		/** Return the metagame configuration data for the calling application. */
		class getMetagameConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MetagameConfig]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MetagameConfig])
		}
		object getMetagameConfig {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getMetagameConfig = new getMetagameConfig(ws.url(BASE_URL + s"games/v1/metagameConfig").addQueryStringParameters())
			given Conversion[getMetagameConfig, Future[Schema.MetagameConfig]] = (fun: getMetagameConfig) => fun.apply()
		}
		/** List play data aggregated per category for the player corresponding to `playerId`. */
		class listCategoriesByPlayer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CategoryListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CategoryListResponse])
		}
		object listCategoriesByPlayer {
			def apply(playerId: String, collection: String, language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listCategoriesByPlayer = new listCategoriesByPlayer(ws.url(BASE_URL + s"games/v1/players/${playerId}/categories/${collection}").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listCategoriesByPlayer, Future[Schema.CategoryListResponse]] = (fun: listCategoriesByPlayer) => fun.apply()
		}
	}
	object players {
		/** Retrieves the Player resource with the given ID. To retrieve the player for the currently authenticated user, set `playerId` to `me`. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Player]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Player])
		}
		object get {
			def apply(playerId: String, language: String, playerIdConsistencyToken: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/players/${playerId}").addQueryStringParameters("language" -> language.toString, "playerIdConsistencyToken" -> playerIdConsistencyToken.toString))
			given Conversion[get, Future[Schema.Player]] = (fun: get) => fun.apply()
		}
		/** Retrieves scoped player identifiers for currently authenticated user. */
		class getScopedPlayerIds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ScopedPlayerIds]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ScopedPlayerIds])
		}
		object getScopedPlayerIds {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getScopedPlayerIds = new getScopedPlayerIds(ws.url(BASE_URL + s"games/v1/players/me/scopedIds").addQueryStringParameters())
			given Conversion[getScopedPlayerIds, Future[Schema.ScopedPlayerIds]] = (fun: getScopedPlayerIds) => fun.apply()
		}
		/** Get the application player ids for the currently authenticated player across all requested games by the same developer as the calling application. This will only return ids for players that actually have an id (scoped or otherwise) with that game. */
		class getMultipleApplicationPlayerIds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetMultipleApplicationPlayerIdsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetMultipleApplicationPlayerIdsResponse])
		}
		object getMultipleApplicationPlayerIds {
			def apply(applicationIds: String)(using signer: RequestSigner, ec: ExecutionContext): getMultipleApplicationPlayerIds = new getMultipleApplicationPlayerIds(ws.url(BASE_URL + s"games/v1/players/me/multipleApplicationPlayerIds").addQueryStringParameters("applicationIds" -> applicationIds.toString))
			given Conversion[getMultipleApplicationPlayerIds, Future[Schema.GetMultipleApplicationPlayerIdsResponse]] = (fun: getMultipleApplicationPlayerIds) => fun.apply()
		}
		/** Get the collection of players for the currently authenticated user. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlayerListResponse])
		}
		object list {
			def apply(collection: String, language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/players/me/players/${collection}").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.PlayerListResponse]] = (fun: list) => fun.apply()
		}
	}
	object revisions {
		/** Checks whether the games client is out of date. */
		class check(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevisionCheckResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RevisionCheckResponse])
		}
		object check {
			def apply(clientRevision: String)(using signer: RequestSigner, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"games/v1/revisions/check").addQueryStringParameters("clientRevision" -> clientRevision.toString))
			given Conversion[check, Future[Schema.RevisionCheckResponse]] = (fun: check) => fun.apply()
		}
	}
	object scores {
		/** Lists the scores in a leaderboard around (and including) a player's score. */
		class listWindow(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardScores]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LeaderboardScores])
		}
		object listWindow {
			def apply(leaderboardId: String, collection: String, language: String, timeSpan: String, maxResults: Int, resultsAbove: Int, returnTopIfAbsent: Boolean, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listWindow = new listWindow(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/window/${collection}").addQueryStringParameters("language" -> language.toString, "timeSpan" -> timeSpan.toString, "maxResults" -> maxResults.toString, "resultsAbove" -> resultsAbove.toString, "returnTopIfAbsent" -> returnTopIfAbsent.toString, "pageToken" -> pageToken.toString))
			given Conversion[listWindow, Future[Schema.LeaderboardScores]] = (fun: listWindow) => fun.apply()
		}
		/** Get high scores, and optionally ranks, in leaderboards for the currently authenticated player. For a specific time span, `leaderboardId` can be set to `ALL` to retrieve data for all leaderboards in a given time span. `NOTE: You cannot ask for 'ALL' leaderboards and 'ALL' timeSpans in the same request; only one parameter may be set to 'ALL'. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerLeaderboardScoreListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlayerLeaderboardScoreListResponse])
		}
		object get {
			def apply(playerId: String, leaderboardId: String, timeSpan: String, language: String, includeRankType: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/players/${playerId}/leaderboards/${leaderboardId}/scores/${timeSpan}").addQueryStringParameters("language" -> language.toString, "includeRankType" -> includeRankType.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[get, Future[Schema.PlayerLeaderboardScoreListResponse]] = (fun: get) => fun.apply()
		}
		/** Lists the scores in a leaderboard, starting from the top. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardScores]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LeaderboardScores])
		}
		object list {
			def apply(leaderboardId: String, collection: String, language: String, timeSpan: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/scores/${collection}").addQueryStringParameters("language" -> language.toString, "timeSpan" -> timeSpan.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardScores]] = (fun: list) => fun.apply()
		}
		/** Submits multiple scores to leaderboards. */
		class submitMultiple(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withPlayerScoreSubmissionList(body: Schema.PlayerScoreSubmissionList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PlayerScoreListResponse])
		}
		object submitMultiple {
			def apply(language: String)(using signer: RequestSigner, ec: ExecutionContext): submitMultiple = new submitMultiple(ws.url(BASE_URL + s"games/v1/leaderboards/scores").addQueryStringParameters("language" -> language.toString))
		}
		/** Submits a score to the specified leaderboard. */
		class submit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.PlayerScoreResponse])
		}
		object submit {
			def apply(leaderboardId: String, language: String, score: String, scoreTag: String)(using signer: RequestSigner, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/scores").addQueryStringParameters("language" -> language.toString, "score" -> score.toString, "scoreTag" -> scoreTag.toString))
			given Conversion[submit, Future[Schema.PlayerScoreResponse]] = (fun: submit) => fun.apply()
		}
	}
	object snapshots {
		/** Retrieves the metadata for a given snapshot ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Snapshot])
		}
		object get {
			def apply(snapshotId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/snapshots/${snapshotId}").addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
		}
		/** Retrieves a list of snapshots created by your application for the player corresponding to the player ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SnapshotListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SnapshotListResponse])
		}
		object list {
			def apply(playerId: String, language: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1/players/${playerId}/snapshots").addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.SnapshotListResponse]] = (fun: list) => fun.apply()
		}
	}
	object stats {
		/** Returns engagement and spend statistics in this application for the currently authenticated user. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StatsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StatsResponse])
		}
		object get {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1/stats").addQueryStringParameters())
			given Conversion[get, Future[Schema.StatsResponse]] = (fun: get) => fun.apply()
		}
	}
	object recall {
		/** Retrieve the last Recall token from all developer games that is associated with the PGS Player encoded in the provided recall session id. The API is only available for users that have active PGS Player profile. */
		class lastTokenFromAllDeveloperGames(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse])
		}
		object lastTokenFromAllDeveloperGames {
			def apply(sessionId: String)(using signer: RequestSigner, ec: ExecutionContext): lastTokenFromAllDeveloperGames = new lastTokenFromAllDeveloperGames(ws.url(BASE_URL + s"games/v1/recall/developerGamesLastPlayerToken/${sessionId}").addQueryStringParameters())
			given Conversion[lastTokenFromAllDeveloperGames, Future[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse]] = (fun: lastTokenFromAllDeveloperGames) => fun.apply()
		}
		/** Delete all Recall tokens linking the given persona to any player (with or without a profile). */
		class resetPersona(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withResetPersonaRequest(body: Schema.ResetPersonaRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResetPersonaResponse])
		}
		object resetPersona {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetPersona = new resetPersona(ws.url(BASE_URL + s"games/v1/recall:resetPersona").addQueryStringParameters())
		}
		/** Retrieve all Recall tokens associated with the PGS Player encoded in the provided recall session id. The API is only available for users that have active PGS Player profile. */
		class retrieveTokens(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrievePlayerTokensResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrievePlayerTokensResponse])
		}
		object retrieveTokens {
			def apply(sessionId: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveTokens = new retrieveTokens(ws.url(BASE_URL + s"games/v1/recall/tokens/${sessionId}").addQueryStringParameters())
			given Conversion[retrieveTokens, Future[Schema.RetrievePlayerTokensResponse]] = (fun: retrieveTokens) => fun.apply()
		}
		/** Retrieve the Recall tokens from all requested games that is associated with the PGS Player encoded in the provided recall session id. The API is only available for users that have an active PGS Player profile. */
		class gamesPlayerTokens(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGamesPlayerTokensResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveGamesPlayerTokensResponse])
		}
		object gamesPlayerTokens {
			def apply(sessionId: String, applicationIds: String)(using signer: RequestSigner, ec: ExecutionContext): gamesPlayerTokens = new gamesPlayerTokens(ws.url(BASE_URL + s"games/v1/recall/gamesPlayerTokens/${sessionId}").addQueryStringParameters("applicationIds" -> applicationIds.toString))
			given Conversion[gamesPlayerTokens, Future[Schema.RetrieveGamesPlayerTokensResponse]] = (fun: gamesPlayerTokens) => fun.apply()
		}
		/** Delete a Recall token linking the PGS Player principal identified by the Recall session and an in-game account identified either by the 'persona' or by the token value. */
		class unlinkPersona(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withUnlinkPersonaRequest(body: Schema.UnlinkPersonaRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UnlinkPersonaResponse])
		}
		object unlinkPersona {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): unlinkPersona = new unlinkPersona(ws.url(BASE_URL + s"games/v1/recall:unlinkPersona").addQueryStringParameters())
		}
		/** Associate the PGS Player principal encoded in the provided recall session id with an in-game account */
		class linkPersona(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withLinkPersonaRequest(body: Schema.LinkPersonaRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LinkPersonaResponse])
		}
		object linkPersona {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): linkPersona = new linkPersona(ws.url(BASE_URL + s"games/v1/recall:linkPersona").addQueryStringParameters())
		}
	}
	object accesstokens {
		/** Generates a Play Grouping API token for the PGS user identified by the attached credential. */
		class generatePlayGroupingApiToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GeneratePlayGroupingApiTokenResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GeneratePlayGroupingApiTokenResponse])
		}
		object generatePlayGroupingApiToken {
			def apply(packageName: String, persona: String)(using signer: RequestSigner, ec: ExecutionContext): generatePlayGroupingApiToken = new generatePlayGroupingApiToken(ws.url(BASE_URL + s"games/v1/accesstokens/generatePlayGroupingApiToken").addQueryStringParameters("packageName" -> packageName.toString, "persona" -> persona.toString))
			given Conversion[generatePlayGroupingApiToken, Future[Schema.GeneratePlayGroupingApiTokenResponse]] = (fun: generatePlayGroupingApiToken) => fun.apply()
		}
		/** Generates a Play Grouping API token for the PGS user identified by the Recall session ID provided in the request. */
		class generateRecallPlayGroupingApiToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenerateRecallPlayGroupingApiTokenResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GenerateRecallPlayGroupingApiTokenResponse])
		}
		object generateRecallPlayGroupingApiToken {
			def apply(packageName: String, persona: String, recallSessionId: String)(using signer: RequestSigner, ec: ExecutionContext): generateRecallPlayGroupingApiToken = new generateRecallPlayGroupingApiToken(ws.url(BASE_URL + s"games/v1/accesstokens/generateRecallPlayGroupingApiToken").addQueryStringParameters("packageName" -> packageName.toString, "persona" -> persona.toString, "recallSessionId" -> recallSessionId.toString))
			given Conversion[generateRecallPlayGroupingApiToken, Future[Schema.GenerateRecallPlayGroupingApiTokenResponse]] = (fun: generateRecallPlayGroupingApiToken) => fun.apply()
		}
	}
}
