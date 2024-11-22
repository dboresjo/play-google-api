package com.boresjo.play.api.google.gamesConfiguration

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
		"""https://www.googleapis.com/auth/androidpublisher""" /* View and manage your Google Play Developer account */
	)

	private val BASE_URL = "https://gamesconfiguration.googleapis.com/"

	object achievementConfigurations {
		/** Insert a new achievement configuration in this application. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withAchievementConfiguration(body: Schema.AchievementConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AchievementConfiguration])
		}
		object insert {
			def apply(applicationId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/achievements").addQueryStringParameters())
		}
		/** Delete the achievement configuration with the given ID. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves the metadata of the achievement configuration with the given ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementConfiguration]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AchievementConfiguration])
		}
		object get {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AchievementConfiguration]] = (fun: get) => fun.apply()
		}
		/** Update the metadata of the achievement configuration with the given ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withAchievementConfiguration(body: Schema.AchievementConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AchievementConfiguration])
		}
		object update {
			def apply(achievementId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}").addQueryStringParameters())
		}
		/** Returns a list of the achievement configurations in this application. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AchievementConfigurationListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AchievementConfigurationListResponse])
		}
		object list {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/achievements").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AchievementConfigurationListResponse]] = (fun: list) => fun.apply()
		}
	}
	object leaderboardConfigurations {
		/** Insert a new leaderboard configuration in this application. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withLeaderboardConfiguration(body: Schema.LeaderboardConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object insert {
			def apply(applicationId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/leaderboards").addQueryStringParameters())
		}
		/** Delete the leaderboard configuration with the given ID. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(leaderboardId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves the metadata of the leaderboard configuration with the given ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardConfiguration]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object get {
			def apply(leaderboardId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LeaderboardConfiguration]] = (fun: get) => fun.apply()
		}
		/** Update the metadata of the leaderboard configuration with the given ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withLeaderboardConfiguration(body: Schema.LeaderboardConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object update {
			def apply(leaderboardId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}").addQueryStringParameters())
		}
		/** Returns a list of the leaderboard configurations in this application. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardConfigurationListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LeaderboardConfigurationListResponse])
		}
		object list {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/leaderboards").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardConfigurationListResponse]] = (fun: list) => fun.apply()
		}
	}
}
