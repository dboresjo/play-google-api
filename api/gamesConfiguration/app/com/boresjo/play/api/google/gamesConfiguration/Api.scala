package com.boresjo.play.api.google.gamesConfiguration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://gamesconfiguration.googleapis.com/"

	object achievementConfigurations {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAchievementConfiguration(body: Schema.AchievementConfiguration) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AchievementConfiguration])
		}
		object insert {
			def apply(applicationId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/achievements")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementConfiguration]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AchievementConfiguration])
		}
		object get {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.AchievementConfiguration]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAchievementConfiguration(body: Schema.AchievementConfiguration) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.AchievementConfiguration])
		}
		object update {
			def apply(achievementId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"games/v1configuration/achievements/${achievementId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AchievementConfigurationListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AchievementConfigurationListResponse])
		}
		object list {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/achievements")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AchievementConfigurationListResponse]] = (fun: list) => fun.apply()
		}
	}
	object leaderboardConfigurations {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLeaderboardConfiguration(body: Schema.LeaderboardConfiguration) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object insert {
			def apply(applicationId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/leaderboards")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(leaderboardId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardConfiguration]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object get {
			def apply(leaderboardId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.LeaderboardConfiguration]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLeaderboardConfiguration(body: Schema.LeaderboardConfiguration) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LeaderboardConfiguration])
		}
		object update {
			def apply(leaderboardId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"games/v1configuration/leaderboards/${leaderboardId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LeaderboardConfigurationListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LeaderboardConfigurationListResponse])
		}
		object list {
			def apply(applicationId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"games/v1configuration/applications/${applicationId}/leaderboards")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LeaderboardConfigurationListResponse]] = (fun: list) => fun.apply()
		}
	}
}
