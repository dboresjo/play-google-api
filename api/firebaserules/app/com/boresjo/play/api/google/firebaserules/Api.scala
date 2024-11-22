package com.boresjo.play.api.google.firebaserules

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebaserules.googleapis.com/"

	object projects {
		class test(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestRulesetRequest(body: Schema.TestRulesetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestRulesetResponse])
		}
		object test {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1/projects/${projectsId}:test").addQueryStringParameters("name" -> name.toString))
		}
		object rulesets {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRuleset(body: Schema.Ruleset) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Ruleset])
			}
			object create {
				def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Ruleset]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Ruleset])
			}
			object get {
				def apply(projectsId :PlayApi, rulesetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets/${rulesetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Ruleset]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRulesetsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRulesetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListRulesetsResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, rulesetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets/${rulesetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object releases {
			class getExecutable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetReleaseExecutableResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetReleaseExecutableResponse])
			}
			object getExecutable {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String, executableVersion: String)(using auth: AuthToken, ec: ExecutionContext): getExecutable = new getExecutable(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}:getExecutable").addQueryStringParameters("name" -> name.toString, "executableVersion" -> executableVersion.toString))
				given Conversion[getExecutable, Future[Schema.GetReleaseExecutableResponse]] = (fun: getExecutable) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRelease(body: Schema.Release) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Release])
			}
			object create {
				def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Release]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Release])
			}
			object get {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Release]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateReleaseRequest(body: Schema.UpdateReleaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Release])
			}
			object patch {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReleasesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListReleasesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListReleasesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
