package com.boresjo.play.api.google.games

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://games.googleapis.com/"

	object achievementDefinitions {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementDefinitionsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AchievementDefinitionsListResponse])
		}
		object list {
			def apply(language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/achievements")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AchievementDefinitionsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object achievements {
		class reveal(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementRevealResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AchievementRevealResponse])
		}
		object reveal {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): reveal = new reveal(auth(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/reveal")).addQueryStringParameters())
			given Conversion[reveal, Future[Schema.AchievementRevealResponse]] = (fun: reveal) => fun.apply()
		}
		class setStepsAtLeast(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementSetStepsAtLeastResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AchievementSetStepsAtLeastResponse])
		}
		object setStepsAtLeast {
			def apply(achievementId: String, steps: Int)(using auth: AuthToken, ec: ExecutionContext): setStepsAtLeast = new setStepsAtLeast(auth(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/setStepsAtLeast")).addQueryStringParameters("steps" -> steps.toString))
			given Conversion[setStepsAtLeast, Future[Schema.AchievementSetStepsAtLeastResponse]] = (fun: setStepsAtLeast) => fun.apply()
		}
		class unlock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementUnlockResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AchievementUnlockResponse])
		}
		object unlock {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): unlock = new unlock(auth(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/unlock")).addQueryStringParameters())
			given Conversion[unlock, Future[Schema.AchievementUnlockResponse]] = (fun: unlock) => fun.apply()
		}
		class updateMultiple(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAchievementUpdateMultipleRequest(body: Schema.AchievementUpdateMultipleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AchievementUpdateMultipleResponse])
		}
		object updateMultiple {
			def apply()(using auth: AuthToken, ec: ExecutionContext): updateMultiple = new updateMultiple(auth(ws.url(BASE_URL + s"games/v1/achievements/updateMultiple")).addQueryStringParameters())
		}
		class increment(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementIncrementResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AchievementIncrementResponse])
		}
		object increment {
			def apply(achievementId: String, requestId: String, stepsToIncrement: Int)(using auth: AuthToken, ec: ExecutionContext): increment = new increment(auth(ws.url(BASE_URL + s"games/v1/achievements/${achievementId}/increment")).addQueryStringParameters("requestId" -> requestId.toString, "stepsToIncrement" -> stepsToIncrement.toString))
			given Conversion[increment, Future[Schema.AchievementIncrementResponse]] = (fun: increment) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerAchievementListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlayerAchievementListResponse])
		}
		object list {
			def apply(playerId: String, language: String, maxResults: Int, pageToken: String, state: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/players/${playerId}/achievements")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "state" -> state.toString))
			given Conversion[list, Future[Schema.PlayerAchievementListResponse]] = (fun: list) => fun.apply()
		}
	}
	object applications {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Application])
		}
		object get {
			def apply(applicationId: String, language: String, platformType: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/applications/${applicationId}")).addQueryStringParameters("language" -> language.toString, "platformType" -> platformType.toString))
			given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
		}
		class played(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object played {
			def apply()(using auth: AuthToken, ec: ExecutionContext): played = new played(auth(ws.url(BASE_URL + s"games/v1/applications/played")).addQueryStringParameters())
			given Conversion[played, Future[Unit]] = (fun: played) => fun.apply()
		}
		class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApplicationVerifyResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ApplicationVerifyResponse])
		}
		object verify {
			def apply(applicationId: String)(using auth: AuthToken, ec: ExecutionContext): verify = new verify(auth(ws.url(BASE_URL + s"games/v1/applications/${applicationId}/verify")).addQueryStringParameters())
			given Conversion[verify, Future[Schema.ApplicationVerifyResponse]] = (fun: verify) => fun.apply()
		}
		class getEndPoint(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EndPoint]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.EndPoint])
		}
		object getEndPoint {
			def apply(applicationId: String, endPointType: String)(using auth: AuthToken, ec: ExecutionContext): getEndPoint = new getEndPoint(auth(ws.url(BASE_URL + s"games/v1/applications/getEndPoint")).addQueryStringParameters("applicationId" -> applicationId.toString, "endPointType" -> endPointType.toString))
			given Conversion[getEndPoint, Future[Schema.EndPoint]] = (fun: getEndPoint) => fun.apply()
		}
	}
	object events {
		class listByPlayer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerEventListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlayerEventListResponse])
		}
		object listByPlayer {
			def apply(language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listByPlayer = new listByPlayer(auth(ws.url(BASE_URL + s"games/v1/events")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listByPlayer, Future[Schema.PlayerEventListResponse]] = (fun: listByPlayer) => fun.apply()
		}
		class listDefinitions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventDefinitionListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EventDefinitionListResponse])
		}
		object listDefinitions {
			def apply(language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listDefinitions = new listDefinitions(auth(ws.url(BASE_URL + s"games/v1/eventDefinitions")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listDefinitions, Future[Schema.EventDefinitionListResponse]] = (fun: listDefinitions) => fun.apply()
		}
		class record(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventRecordRequest(body: Schema.EventRecordRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventUpdateResponse])
		}
		object record {
			def apply(language: String)(using auth: AuthToken, ec: ExecutionContext): record = new record(auth(ws.url(BASE_URL + s"games/v1/events")).addQueryStringParameters("language" -> language.toString))
		}
	}
	object leaderboards {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Leaderboard]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Leaderboard])
		}
		object get {
			def apply(leaderboardId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}")).addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Leaderboard]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LeaderboardListResponse])
		}
		object list {
			def apply(language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/leaderboards")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardListResponse]] = (fun: list) => fun.apply()
		}
	}
	object metagame {
		class getMetagameConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MetagameConfig]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.MetagameConfig])
		}
		object getMetagameConfig {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getMetagameConfig = new getMetagameConfig(auth(ws.url(BASE_URL + s"games/v1/metagameConfig")).addQueryStringParameters())
			given Conversion[getMetagameConfig, Future[Schema.MetagameConfig]] = (fun: getMetagameConfig) => fun.apply()
		}
		class listCategoriesByPlayer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CategoryListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.CategoryListResponse])
		}
		object listCategoriesByPlayer {
			def apply(playerId: String, collection: String, language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listCategoriesByPlayer = new listCategoriesByPlayer(auth(ws.url(BASE_URL + s"games/v1/players/${playerId}/categories/${collection}")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listCategoriesByPlayer, Future[Schema.CategoryListResponse]] = (fun: listCategoriesByPlayer) => fun.apply()
		}
	}
	object players {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Player]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Player])
		}
		object get {
			def apply(playerId: String, language: String, playerIdConsistencyToken: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/players/${playerId}")).addQueryStringParameters("language" -> language.toString, "playerIdConsistencyToken" -> playerIdConsistencyToken.toString))
			given Conversion[get, Future[Schema.Player]] = (fun: get) => fun.apply()
		}
		class getScopedPlayerIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ScopedPlayerIds]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ScopedPlayerIds])
		}
		object getScopedPlayerIds {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getScopedPlayerIds = new getScopedPlayerIds(auth(ws.url(BASE_URL + s"games/v1/players/me/scopedIds")).addQueryStringParameters())
			given Conversion[getScopedPlayerIds, Future[Schema.ScopedPlayerIds]] = (fun: getScopedPlayerIds) => fun.apply()
		}
		class getMultipleApplicationPlayerIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetMultipleApplicationPlayerIdsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GetMultipleApplicationPlayerIdsResponse])
		}
		object getMultipleApplicationPlayerIds {
			def apply(applicationIds: String)(using auth: AuthToken, ec: ExecutionContext): getMultipleApplicationPlayerIds = new getMultipleApplicationPlayerIds(auth(ws.url(BASE_URL + s"games/v1/players/me/multipleApplicationPlayerIds")).addQueryStringParameters("applicationIds" -> applicationIds.toString))
			given Conversion[getMultipleApplicationPlayerIds, Future[Schema.GetMultipleApplicationPlayerIdsResponse]] = (fun: getMultipleApplicationPlayerIds) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlayerListResponse])
		}
		object list {
			def apply(collection: String, language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/players/me/players/${collection}")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.PlayerListResponse]] = (fun: list) => fun.apply()
		}
	}
	object revisions {
		class check(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevisionCheckResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.RevisionCheckResponse])
		}
		object check {
			def apply(clientRevision: String)(using auth: AuthToken, ec: ExecutionContext): check = new check(auth(ws.url(BASE_URL + s"games/v1/revisions/check")).addQueryStringParameters("clientRevision" -> clientRevision.toString))
			given Conversion[check, Future[Schema.RevisionCheckResponse]] = (fun: check) => fun.apply()
		}
	}
	object scores {
		class listWindow(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardScores]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LeaderboardScores])
		}
		object listWindow {
			def apply(leaderboardId: String, collection: String, language: String, timeSpan: String, maxResults: Int, resultsAbove: Int, returnTopIfAbsent: Boolean, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listWindow = new listWindow(auth(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/window/${collection}")).addQueryStringParameters("language" -> language.toString, "timeSpan" -> timeSpan.toString, "maxResults" -> maxResults.toString, "resultsAbove" -> resultsAbove.toString, "returnTopIfAbsent" -> returnTopIfAbsent.toString, "pageToken" -> pageToken.toString))
			given Conversion[listWindow, Future[Schema.LeaderboardScores]] = (fun: listWindow) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerLeaderboardScoreListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PlayerLeaderboardScoreListResponse])
		}
		object get {
			def apply(playerId: String, leaderboardId: String, timeSpan: String, language: String, includeRankType: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/players/${playerId}/leaderboards/${leaderboardId}/scores/${timeSpan}")).addQueryStringParameters("language" -> language.toString, "includeRankType" -> includeRankType.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[get, Future[Schema.PlayerLeaderboardScoreListResponse]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardScores]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LeaderboardScores])
		}
		object list {
			def apply(leaderboardId: String, collection: String, language: String, timeSpan: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/scores/${collection}")).addQueryStringParameters("language" -> language.toString, "timeSpan" -> timeSpan.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardScores]] = (fun: list) => fun.apply()
		}
		class submitMultiple(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPlayerScoreSubmissionList(body: Schema.PlayerScoreSubmissionList) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PlayerScoreListResponse])
		}
		object submitMultiple {
			def apply(language: String)(using auth: AuthToken, ec: ExecutionContext): submitMultiple = new submitMultiple(auth(ws.url(BASE_URL + s"games/v1/leaderboards/scores")).addQueryStringParameters("language" -> language.toString))
		}
		class submit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.PlayerScoreResponse])
		}
		object submit {
			def apply(leaderboardId: String, language: String, score: String, scoreTag: String)(using auth: AuthToken, ec: ExecutionContext): submit = new submit(auth(ws.url(BASE_URL + s"games/v1/leaderboards/${leaderboardId}/scores")).addQueryStringParameters("language" -> language.toString, "score" -> score.toString, "scoreTag" -> scoreTag.toString))
			given Conversion[submit, Future[Schema.PlayerScoreResponse]] = (fun: submit) => fun.apply()
		}
	}
	object snapshots {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Snapshot])
		}
		object get {
			def apply(snapshotId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/snapshots/${snapshotId}")).addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SnapshotListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SnapshotListResponse])
		}
		object list {
			def apply(playerId: String, language: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1/players/${playerId}/snapshots")).addQueryStringParameters("language" -> language.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.SnapshotListResponse]] = (fun: list) => fun.apply()
		}
	}
	object stats {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StatsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StatsResponse])
		}
		object get {
			def apply()(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1/stats")).addQueryStringParameters())
			given Conversion[get, Future[Schema.StatsResponse]] = (fun: get) => fun.apply()
		}
	}
	object recall {
		class lastTokenFromAllDeveloperGames(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse])
		}
		object lastTokenFromAllDeveloperGames {
			def apply(sessionId: String)(using auth: AuthToken, ec: ExecutionContext): lastTokenFromAllDeveloperGames = new lastTokenFromAllDeveloperGames(auth(ws.url(BASE_URL + s"games/v1/recall/developerGamesLastPlayerToken/${sessionId}")).addQueryStringParameters())
			given Conversion[lastTokenFromAllDeveloperGames, Future[Schema.RetrieveDeveloperGamesLastPlayerTokenResponse]] = (fun: lastTokenFromAllDeveloperGames) => fun.apply()
		}
		class resetPersona(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withResetPersonaRequest(body: Schema.ResetPersonaRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ResetPersonaResponse])
		}
		object resetPersona {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetPersona = new resetPersona(auth(ws.url(BASE_URL + s"games/v1/recall:resetPersona")).addQueryStringParameters())
		}
		class retrieveTokens(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrievePlayerTokensResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.RetrievePlayerTokensResponse])
		}
		object retrieveTokens {
			def apply(sessionId: String)(using auth: AuthToken, ec: ExecutionContext): retrieveTokens = new retrieveTokens(auth(ws.url(BASE_URL + s"games/v1/recall/tokens/${sessionId}")).addQueryStringParameters())
			given Conversion[retrieveTokens, Future[Schema.RetrievePlayerTokensResponse]] = (fun: retrieveTokens) => fun.apply()
		}
		class gamesPlayerTokens(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGamesPlayerTokensResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.RetrieveGamesPlayerTokensResponse])
		}
		object gamesPlayerTokens {
			def apply(sessionId: String, applicationIds: String)(using auth: AuthToken, ec: ExecutionContext): gamesPlayerTokens = new gamesPlayerTokens(auth(ws.url(BASE_URL + s"games/v1/recall/gamesPlayerTokens/${sessionId}")).addQueryStringParameters("applicationIds" -> applicationIds.toString))
			given Conversion[gamesPlayerTokens, Future[Schema.RetrieveGamesPlayerTokensResponse]] = (fun: gamesPlayerTokens) => fun.apply()
		}
		class unlinkPersona(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUnlinkPersonaRequest(body: Schema.UnlinkPersonaRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UnlinkPersonaResponse])
		}
		object unlinkPersona {
			def apply()(using auth: AuthToken, ec: ExecutionContext): unlinkPersona = new unlinkPersona(auth(ws.url(BASE_URL + s"games/v1/recall:unlinkPersona")).addQueryStringParameters())
		}
		class linkPersona(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLinkPersonaRequest(body: Schema.LinkPersonaRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LinkPersonaResponse])
		}
		object linkPersona {
			def apply()(using auth: AuthToken, ec: ExecutionContext): linkPersona = new linkPersona(auth(ws.url(BASE_URL + s"games/v1/recall:linkPersona")).addQueryStringParameters())
		}
	}
	object accesstokens {
		class generatePlayGroupingApiToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GeneratePlayGroupingApiTokenResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.GeneratePlayGroupingApiTokenResponse])
		}
		object generatePlayGroupingApiToken {
			def apply(packageName: String, persona: String)(using auth: AuthToken, ec: ExecutionContext): generatePlayGroupingApiToken = new generatePlayGroupingApiToken(auth(ws.url(BASE_URL + s"games/v1/accesstokens/generatePlayGroupingApiToken")).addQueryStringParameters("packageName" -> packageName.toString, "persona" -> persona.toString))
			given Conversion[generatePlayGroupingApiToken, Future[Schema.GeneratePlayGroupingApiTokenResponse]] = (fun: generatePlayGroupingApiToken) => fun.apply()
		}
		class generateRecallPlayGroupingApiToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenerateRecallPlayGroupingApiTokenResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.GenerateRecallPlayGroupingApiTokenResponse])
		}
		object generateRecallPlayGroupingApiToken {
			def apply(packageName: String, persona: String, recallSessionId: String)(using auth: AuthToken, ec: ExecutionContext): generateRecallPlayGroupingApiToken = new generateRecallPlayGroupingApiToken(auth(ws.url(BASE_URL + s"games/v1/accesstokens/generateRecallPlayGroupingApiToken")).addQueryStringParameters("packageName" -> packageName.toString, "persona" -> persona.toString, "recallSessionId" -> recallSessionId.toString))
			given Conversion[generateRecallPlayGroupingApiToken, Future[Schema.GenerateRecallPlayGroupingApiTokenResponse]] = (fun: generateRecallPlayGroupingApiToken) => fun.apply()
		}
	}
}
