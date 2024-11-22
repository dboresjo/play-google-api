package com.boresjo.play.api.google.firebaserules

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */,
		"""https://www.googleapis.com/auth/firebase.readonly""" /* View all your Firebase data and settings */
	)

	private val BASE_URL = "https://firebaserules.googleapis.com/"

	object projects {
		/** Test `Source` for syntactic and semantic correctness. Issues present, if any, will be returned to the caller with a description, severity, and source location. The test method may be executed with `Source` or a `Ruleset` name. Passing `Source` is useful for unit testing new rules. Passing a `Ruleset` name is useful for regression testing an existing rule. The following is an example of `Source` that permits users to upload images to a bucket bearing their user id and matching the correct metadata: _&#42;Example&#42;_ // Users are allowed to subscribe and unsubscribe to the blog. service firebase.storage { match /users/{userId}/images/{imageName} { allow write: if userId == request.auth.uid && (imageName.matches('&#42;.png$') || imageName.matches('&#42;.jpg$')) && resource.mimeType.matches('^image/') } } */
		class test(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def withTestRulesetRequest(body: Schema.TestRulesetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestRulesetResponse])
		}
		object test {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1/projects/${projectsId}:test").addQueryStringParameters("name" -> name.toString))
		}
		object rulesets {
			/** Create a `Ruleset` from `Source`. The `Ruleset` is given a unique generated name which is returned to the caller. `Source` containing syntactic or semantics errors will result in an error response indicating the first error encountered. For a detailed view of `Source` issues, use TestRuleset. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRuleset(body: Schema.Ruleset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Ruleset])
			}
			object create {
				def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets").addQueryStringParameters("name" -> name.toString))
			}
			/** Get a `Ruleset` by name including the full `Source` contents. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Ruleset]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Ruleset])
			}
			object get {
				def apply(projectsId :PlayApi, rulesetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets/${rulesetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Ruleset]] = (fun: get) => fun.apply()
			}
			/** List `Ruleset` metadata only and optionally filter the results by `Ruleset` name. The full `Source` contents of a `Ruleset` may be retrieved with GetRuleset. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRulesetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRulesetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListRulesetsResponse]] = (fun: list) => fun.apply()
			}
			/** Delete a `Ruleset` by resource name. If the `Ruleset` is referenced by a `Release` the operation will fail. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, rulesetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/rulesets/${rulesetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object releases {
			/** Get the `Release` executable to use when enforcing rules. */
			class getExecutable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetReleaseExecutableResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetReleaseExecutableResponse])
			}
			object getExecutable {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String, executableVersion: String)(using signer: RequestSigner, ec: ExecutionContext): getExecutable = new getExecutable(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}:getExecutable").addQueryStringParameters("name" -> name.toString, "executableVersion" -> executableVersion.toString))
				given Conversion[getExecutable, Future[Schema.GetReleaseExecutableResponse]] = (fun: getExecutable) => fun.apply()
			}
			/** Create a `Release`. Release names should reflect the developer's deployment practices. For example, the release name may include the environment name, application name, application version, or any other name meaningful to the developer. Once a `Release` refers to a `Ruleset`, the rules can be enforced by Firebase Rules-enabled services. More than one `Release` may be 'live' concurrently. Consider the following three `Release` names for `projects/foo` and the `Ruleset` to which they refer. Release Name -> Ruleset Name &#42; projects/foo/releases/prod -> projects/foo/rulesets/uuid123 &#42; projects/foo/releases/prod/beta -> projects/foo/rulesets/uuid123 &#42; projects/foo/releases/prod/v23 -> projects/foo/rulesets/uuid456 The relationships reflect a `Ruleset` rollout in progress. The `prod` and `prod/beta` releases refer to the same `Ruleset`. However, `prod/v23` refers to a new `Ruleset`. The `Ruleset` reference for a `Release` may be updated using the UpdateRelease method. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRelease(body: Schema.Release) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Release])
			}
			object create {
				def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases").addQueryStringParameters("name" -> name.toString))
			}
			/** Delete a `Release` by resource name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Get a `Release` by name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Release]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Release])
			}
			object get {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Release]] = (fun: get) => fun.apply()
			}
			/** Update a `Release` via PATCH. Only updates to `ruleset_name` will be honored. `Release` rename is not supported. To create a `Release` use the CreateRelease method. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withUpdateReleaseRequest(body: Schema.UpdateReleaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Release])
			}
			object patch {
				def apply(projectsId :PlayApi, releasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases/${releasesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** List the `Release` values for a project. This list may optionally be filtered by `Release` name, `Ruleset` name, `TestSuite` name, or any combination thereof. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReleasesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReleasesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/releases").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListReleasesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
