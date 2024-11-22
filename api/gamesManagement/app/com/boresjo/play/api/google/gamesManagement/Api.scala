package com.boresjo.play.api.google.gamesManagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://gamesmanagement.googleapis.com/"

	object achievements {
		class resetAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementResetAllResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AchievementResetAllResponse])
		}
		object resetAll {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/achievements/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Schema.AchievementResetAllResponse]] = (fun: resetAll) => fun.apply()
		}
		class resetMultipleForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAchievementResetMultipleForAllRequest(body: Schema.AchievementResetMultipleForAllRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		class resetAllForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		class resetForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/${achievementId}/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementResetResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AchievementResetResponse])
		}
		object reset {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/achievements/${achievementId}/reset").addQueryStringParameters())
			given Conversion[reset, Future[Schema.AchievementResetResponse]] = (fun: reset) => fun.apply()
		}
	}
	object events {
		class resetAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetAll {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/events/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Unit]] = (fun: resetAll) => fun.apply()
		}
		class resetMultipleForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventsResetMultipleForAllRequest(body: Schema.EventsResetMultipleForAllRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		class resetAllForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		class resetForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(eventId: String)(using auth: AuthToken, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/${eventId}/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object reset {
			def apply(eventId: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/events/${eventId}/reset").addQueryStringParameters())
			given Conversion[reset, Future[Unit]] = (fun: reset) => fun.apply()
		}
	}
	object players {
		class hide(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object hide {
			def apply(applicationId: String, playerId: String)(using auth: AuthToken, ec: ExecutionContext): hide = new hide(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden/${playerId}").addQueryStringParameters())
			given Conversion[hide, Future[Unit]] = (fun: hide) => fun.apply()
		}
		class unhide(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object unhide {
			def apply(applicationId: String, playerId: String)(using auth: AuthToken, ec: ExecutionContext): unhide = new unhide(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden/${playerId}").addQueryStringParameters())
			given Conversion[unhide, Future[Unit]] = (fun: unhide) => fun.apply()
		}
	}
	object applications {
		class listHidden(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HiddenPlayerList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HiddenPlayerList])
		}
		object listHidden {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listHidden = new listHidden(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listHidden, Future[Schema.HiddenPlayerList]] = (fun: listHidden) => fun.apply()
		}
	}
	object scores {
		class resetAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResetAllResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.PlayerScoreResetAllResponse])
		}
		object resetAll {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/scores/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Schema.PlayerScoreResetAllResponse]] = (fun: resetAll) => fun.apply()
		}
		class resetMultipleForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withScoresResetMultipleForAllRequest(body: Schema.ScoresResetMultipleForAllRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/scores/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		class resetAllForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/scores/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		class resetForAllPlayers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(leaderboardId: String)(using auth: AuthToken, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/leaderboards/${leaderboardId}/scores/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResetResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.PlayerScoreResetResponse])
		}
		object reset {
			def apply(leaderboardId: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/leaderboards/${leaderboardId}/scores/reset").addQueryStringParameters())
			given Conversion[reset, Future[Schema.PlayerScoreResetResponse]] = (fun: reset) => fun.apply()
		}
	}
}
