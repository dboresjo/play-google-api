package com.boresjo.play.api.google.gamesManagement

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
		"""https://www.googleapis.com/auth/games""" /* Create, edit, and delete your Google Play Games activity */
	)

	private val BASE_URL = "https://gamesmanagement.googleapis.com/"

	object achievements {
		/** Resets all achievements for the currently authenticated player for your application. This method is only accessible to whitelisted tester accounts for your application. */
		class resetAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementResetAllResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementResetAllResponse])
		}
		object resetAll {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/achievements/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Schema.AchievementResetAllResponse]] = (fun: resetAll) => fun.apply()
		}
		/** Resets achievements with the given IDs for all players. This method is only available to user accounts for your developer console. Only draft achievements may be reset. */
		class resetMultipleForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withAchievementResetMultipleForAllRequest(body: Schema.AchievementResetMultipleForAllRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		/** Resets all draft achievements for all players. This method is only available to user accounts for your developer console. */
		class resetAllForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		/** Resets the achievement with the given ID for all players. This method is only available to user accounts for your developer console. Only draft achievements can be reset. */
		class resetForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/achievements/${achievementId}/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		/** Resets the achievement with the given ID for the currently authenticated player. This method is only accessible to whitelisted tester accounts for your application. */
		class reset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementResetResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AchievementResetResponse])
		}
		object reset {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/achievements/${achievementId}/reset").addQueryStringParameters())
			given Conversion[reset, Future[Schema.AchievementResetResponse]] = (fun: reset) => fun.apply()
		}
	}
	object events {
		/** Resets all player progress on all events for the currently authenticated player. This method is only accessible to whitelisted tester accounts for your application. */
		class resetAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetAll {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/events/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Unit]] = (fun: resetAll) => fun.apply()
		}
		/** Resets events with the given IDs for all players. This method is only available to user accounts for your developer console. Only draft events may be reset. */
		class resetMultipleForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withEventsResetMultipleForAllRequest(body: Schema.EventsResetMultipleForAllRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		/** Resets all draft events for all players. This method is only available to user accounts for your developer console. */
		class resetAllForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		/** Resets the event with the given ID for all players. This method is only available to user accounts for your developer console. Only draft events can be reset. */
		class resetForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(eventId: String)(using signer: RequestSigner, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/events/${eventId}/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		/** Resets all player progress on the event with the given ID for the currently authenticated player. This method is only accessible to whitelisted tester accounts for your application. */
		class reset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object reset {
			def apply(eventId: String)(using signer: RequestSigner, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/events/${eventId}/reset").addQueryStringParameters())
			given Conversion[reset, Future[Unit]] = (fun: reset) => fun.apply()
		}
	}
	object players {
		/** Hide the given player's leaderboard scores from the given application. This method is only available to user accounts for your developer console. */
		class hide(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object hide {
			def apply(applicationId: String, playerId: String)(using signer: RequestSigner, ec: ExecutionContext): hide = new hide(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden/${playerId}").addQueryStringParameters())
			given Conversion[hide, Future[Unit]] = (fun: hide) => fun.apply()
		}
		/** Unhide the given player's leaderboard scores from the given application. This method is only available to user accounts for your developer console. */
		class unhide(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object unhide {
			def apply(applicationId: String, playerId: String)(using signer: RequestSigner, ec: ExecutionContext): unhide = new unhide(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden/${playerId}").addQueryStringParameters())
			given Conversion[unhide, Future[Unit]] = (fun: unhide) => fun.apply()
		}
	}
	object applications {
		/** Get the list of players hidden from the given application. This method is only available to user accounts for your developer console. */
		class listHidden(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HiddenPlayerList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HiddenPlayerList])
		}
		object listHidden {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listHidden = new listHidden(ws.url(BASE_URL + s"games/v1management/applications/${applicationId}/players/hidden").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listHidden, Future[Schema.HiddenPlayerList]] = (fun: listHidden) => fun.apply()
		}
	}
	object scores {
		/** Resets all scores for all leaderboards for the currently authenticated players. This method is only accessible to whitelisted tester accounts for your application. */
		class resetAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResetAllResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.PlayerScoreResetAllResponse])
		}
		object resetAll {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAll = new resetAll(ws.url(BASE_URL + s"games/v1management/scores/reset").addQueryStringParameters())
			given Conversion[resetAll, Future[Schema.PlayerScoreResetAllResponse]] = (fun: resetAll) => fun.apply()
		}
		/** Resets scores for the leaderboards with the given IDs for all players. This method is only available to user accounts for your developer console. Only draft leaderboards may be reset. */
		class resetMultipleForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def withScoresResetMultipleForAllRequest(body: Schema.ScoresResetMultipleForAllRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resetMultipleForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetMultipleForAllPlayers = new resetMultipleForAllPlayers(ws.url(BASE_URL + s"games/v1management/scores/resetMultipleForAllPlayers").addQueryStringParameters())
		}
		/** Resets scores for all draft leaderboards for all players. This method is only available to user accounts for your developer console. */
		class resetAllForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetAllForAllPlayers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetAllForAllPlayers = new resetAllForAllPlayers(ws.url(BASE_URL + s"games/v1management/scores/resetAllForAllPlayers").addQueryStringParameters())
			given Conversion[resetAllForAllPlayers, Future[Unit]] = (fun: resetAllForAllPlayers) => fun.apply()
		}
		/** Resets scores for the leaderboard with the given ID for all players. This method is only available to user accounts for your developer console. Only draft leaderboards can be reset. */
		class resetForAllPlayers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object resetForAllPlayers {
			def apply(leaderboardId: String)(using signer: RequestSigner, ec: ExecutionContext): resetForAllPlayers = new resetForAllPlayers(ws.url(BASE_URL + s"games/v1management/leaderboards/${leaderboardId}/scores/resetForAllPlayers").addQueryStringParameters())
			given Conversion[resetForAllPlayers, Future[Unit]] = (fun: resetForAllPlayers) => fun.apply()
		}
		/** Resets scores for the leaderboard with the given ID for the currently authenticated player. This method is only accessible to whitelisted tester accounts for your application. */
		class reset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlayerScoreResetResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/games""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.PlayerScoreResetResponse])
		}
		object reset {
			def apply(leaderboardId: String)(using signer: RequestSigner, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"games/v1management/leaderboards/${leaderboardId}/scores/reset").addQueryStringParameters())
			given Conversion[reset, Future[Schema.PlayerScoreResetResponse]] = (fun: reset) => fun.apply()
		}
	}
}
