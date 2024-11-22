package com.boresjo.play.api.google.analytics

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
		"""https://www.googleapis.com/auth/analytics""" /* View and manage your Google Analytics data */,
		"""https://www.googleapis.com/auth/analytics.edit""" /* Edit Google Analytics management entities */,
		"""https://www.googleapis.com/auth/analytics.manage.users""" /* Manage Google Analytics Account users by email address */,
		"""https://www.googleapis.com/auth/analytics.manage.users.readonly""" /* View Google Analytics user permissions */,
		"""https://www.googleapis.com/auth/analytics.provision""" /* Create a new Google Analytics account along with its default property and view */,
		"""https://www.googleapis.com/auth/analytics.readonly""" /* View your Google Analytics data */,
		"""https://www.googleapis.com/auth/analytics.user.deletion""" /* Manage Google Analytics user deletion requests */
	)

	private val BASE_URL = "https://www.googleapis.com/analytics/v3/"

	object data {
		object ga {
			/** Returns Analytics data for a view (profile). */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GaData]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GaData])
			}
			object get {
				def apply(dimensions: String, end_date: String, filters: String, ids: String, include_empty_rows: Boolean, max_results: Int, metrics: String, output: String, samplingLevel: String, segment: String, sort: String, start_date: String, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"data/ga").addQueryStringParameters("dimensions" -> dimensions.toString, "end-date" -> end_date.toString, "filters" -> filters.toString, "ids" -> ids.toString, "include-empty-rows" -> include_empty_rows.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "output" -> output.toString, "samplingLevel" -> samplingLevel.toString, "segment" -> segment.toString, "sort" -> sort.toString, "start-date" -> start_date.toString, "start-index" -> start_index.toString))
				given Conversion[get, Future[Schema.GaData]] = (fun: get) => fun.apply()
			}
		}
		object mcf {
			/** Returns Analytics Multi-Channel Funnels data for a view (profile). */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.McfData]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.McfData])
			}
			object get {
				def apply(dimensions: String, end_date: String, filters: String, ids: String, max_results: Int, metrics: String, samplingLevel: String, sort: String, start_date: String, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"data/mcf").addQueryStringParameters("dimensions" -> dimensions.toString, "end-date" -> end_date.toString, "filters" -> filters.toString, "ids" -> ids.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "samplingLevel" -> samplingLevel.toString, "sort" -> sort.toString, "start-date" -> start_date.toString, "start-index" -> start_index.toString))
				given Conversion[get, Future[Schema.McfData]] = (fun: get) => fun.apply()
			}
		}
		object realtime {
			/** Returns real time data for a view (profile). */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RealtimeData]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RealtimeData])
			}
			object get {
				def apply(dimensions: String, filters: String, ids: String, max_results: Int, metrics: String, sort: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"data/realtime").addQueryStringParameters("dimensions" -> dimensions.toString, "filters" -> filters.toString, "ids" -> ids.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "sort" -> sort.toString))
				given Conversion[get, Future[Schema.RealtimeData]] = (fun: get) => fun.apply()
			}
		}
	}
	object management {
		object experiments {
			/** Create a new experiment. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withExperiment(body: Schema.Experiment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Experiment])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments").addQueryStringParameters())
			}
			/** Delete an experiment. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Returns an experiment to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Experiment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Experiment])
			}
			object get {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Experiment]] = (fun: get) => fun.apply()
			}
			/** Update an existing experiment. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withExperiment(body: Schema.Experiment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Experiment])
			}
			object update {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}").addQueryStringParameters())
			}
			/** Update an existing experiment. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withExperiment(body: Schema.Experiment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Experiment])
			}
			object patch {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}").addQueryStringParameters())
			}
			/** Lists experiments to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Experiments]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Experiments])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Experiments]] = (fun: list) => fun.apply()
			}
		}
		object profileFilterLinks {
			/** Create a new profile filter link. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProfileFilterLink])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks").addQueryStringParameters())
			}
			/** Delete a profile filter link. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Returns a single profile filter link. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProfileFilterLink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProfileFilterLink])
			}
			object get {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.ProfileFilterLink]] = (fun: get) => fun.apply()
			}
			/** Update an existing profile filter link. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ProfileFilterLink])
			}
			object update {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}").addQueryStringParameters())
			}
			/** Update an existing profile filter link. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProfileFilterLink])
			}
			object patch {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}").addQueryStringParameters())
			}
			/** Lists all profile filter links for a profile. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProfileFilterLinks]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProfileFilterLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.ProfileFilterLinks]] = (fun: list) => fun.apply()
			}
		}
		object uploads {
			/** Delete data associated with a previous upload. */
			class deleteUploadData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withAnalyticsDataimportDeleteUploadDataRequest(body: Schema.AnalyticsDataimportDeleteUploadDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object deleteUploadData {
				def apply(accountId: String, customDataSourceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): deleteUploadData = new deleteUploadData(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/deleteUploadData").addQueryStringParameters())
			}
			/** List uploads to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Upload]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Upload])
			}
			object get {
				def apply(accountId: String, customDataSourceId: String, uploadId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads/${uploadId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Upload]] = (fun: get) => fun.apply()
			}
			/** List uploads to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Uploads]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Uploads])
			}
			object list {
				def apply(accountId: String, customDataSourceId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Uploads]] = (fun: list) => fun.apply()
			}
			/** Upload data for a custom data source. */
			class uploadData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Upload]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Upload])
			}
			object uploadData {
				def apply(accountId: String, customDataSourceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): uploadData = new uploadData(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads").addQueryStringParameters())
				given Conversion[uploadData, Future[Schema.Upload]] = (fun: uploadData) => fun.apply()
			}
		}
		object clientId {
			/** Hashes the given Client ID. */
			class hashClientId(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def withHashClientIdRequest(body: Schema.HashClientIdRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HashClientIdResponse])
			}
			object hashClientId {
				def apply()(using signer: RequestSigner, ec: ExecutionContext): hashClientId = new hashClientId(ws.url(BASE_URL + s"management/clientId:hashClientId").addQueryStringParameters())
			}
		}
		object webpropertyUserLinks {
			/** Removes a user from the given web property. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks/${linkId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Adds a new user to the given web property. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks").addQueryStringParameters())
			}
			/** Lists webProperty-user links for a given web property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""", """https://www.googleapis.com/auth/analytics.manage.users.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			/** Updates permissions for an existing user on the given web property. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks/${linkId}").addQueryStringParameters())
			}
		}
		object webPropertyAdWordsLinks {
			/** Creates a webProperty-Google Ads link. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EntityAdWordsLink])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks").addQueryStringParameters())
			}
			/** Deletes a web property-Google Ads link. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Returns a web property-Google Ads link to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityAdWordsLink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityAdWordsLink])
			}
			object get {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.EntityAdWordsLink]] = (fun: get) => fun.apply()
			}
			/** Updates an existing webProperty-Google Ads link. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EntityAdWordsLink])
			}
			object update {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}").addQueryStringParameters())
			}
			/** Updates an existing webProperty-Google Ads link. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EntityAdWordsLink])
			}
			object patch {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}").addQueryStringParameters())
			}
			/** Lists webProperty-Google Ads links for a given web property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityAdWordsLinks]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityAdWordsLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityAdWordsLinks]] = (fun: list) => fun.apply()
			}
		}
		object webproperties {
			/** Create a new property if the account has fewer than 20 properties. Web properties are visible in the Google Analytics interface only if they have at least one profile. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withWebproperty(body: Schema.Webproperty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Webproperty])
			}
			object insert {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties").addQueryStringParameters())
			}
			/** Gets a web property to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Webproperty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Webproperty])
			}
			object get {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Webproperty]] = (fun: get) => fun.apply()
			}
			/** Updates an existing web property. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withWebproperty(body: Schema.Webproperty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Webproperty])
			}
			object update {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}").addQueryStringParameters())
			}
			/** Updates an existing web property. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withWebproperty(body: Schema.Webproperty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Webproperty])
			}
			object patch {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}").addQueryStringParameters())
			}
			/** Lists web properties to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Webproperties]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Webproperties])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Webproperties]] = (fun: list) => fun.apply()
			}
		}
		object customDataSources {
			/** List custom data sources to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomDataSources]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomDataSources])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomDataSources]] = (fun: list) => fun.apply()
			}
		}
		object filters {
			/** Create a new filter. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withFilter(body: Schema.Filter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Filter])
			}
			object insert {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/filters").addQueryStringParameters())
			}
			/** Delete a filter. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Filter])
			}
			object delete {
				def apply(accountId: String, filterId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Filter]] = (fun: delete) => fun.apply()
			}
			/** Returns filters to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Filter])
			}
			object get {
				def apply(accountId: String, filterId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Filter]] = (fun: get) => fun.apply()
			}
			/** Updates an existing filter. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withFilter(body: Schema.Filter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Filter])
			}
			object update {
				def apply(accountId: String, filterId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}").addQueryStringParameters())
			}
			/** Updates an existing filter. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withFilter(body: Schema.Filter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Filter])
			}
			object patch {
				def apply(accountId: String, filterId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}").addQueryStringParameters())
			}
			/** Lists all filters for an account */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Filters]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Filters])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/filters").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Filters]] = (fun: list) => fun.apply()
			}
		}
		object segments {
			/** Lists segments to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Segments]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Segments])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/segments").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Segments]] = (fun: list) => fun.apply()
			}
		}
		object customMetrics {
			/** Create a new custom metric. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomMetric(body: Schema.CustomMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomMetric])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics").addQueryStringParameters())
			}
			/** Get a custom metric to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomMetric]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomMetric])
			}
			object get {
				def apply(accountId: String, customMetricId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CustomMetric]] = (fun: get) => fun.apply()
			}
			/** Updates an existing custom metric. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomMetric(body: Schema.CustomMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CustomMetric])
			}
			object update {
				def apply(accountId: String, customMetricId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}").addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			/** Updates an existing custom metric. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomMetric(body: Schema.CustomMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomMetric])
			}
			object patch {
				def apply(accountId: String, customMetricId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}").addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			/** Lists custom metrics to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomMetrics]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomMetrics])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomMetrics]] = (fun: list) => fun.apply()
			}
		}
		object unsampledReports {
			/** Deletes an unsampled report. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, profileId: String, unsampledReportId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports/${unsampledReportId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Returns a single unsampled report. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UnsampledReport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UnsampledReport])
			}
			object get {
				def apply(accountId: String, profileId: String, unsampledReportId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports/${unsampledReportId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.UnsampledReport]] = (fun: get) => fun.apply()
			}
			/** Create a new unsampled report. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withUnsampledReport(body: Schema.UnsampledReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UnsampledReport])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports").addQueryStringParameters())
			}
			/** Lists unsampled reports to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UnsampledReports]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UnsampledReports])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.UnsampledReports]] = (fun: list) => fun.apply()
			}
		}
		object accountUserLinks {
			/** Removes a user from the given account. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks/${linkId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Adds a new user to the given account. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks").addQueryStringParameters())
			}
			/** Lists account-user links for a given account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""", """https://www.googleapis.com/auth/analytics.manage.users.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			/** Updates permissions for an existing user on the given account. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks/${linkId}").addQueryStringParameters())
			}
		}
		object goals {
			/** Create a new goal. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoal(body: Schema.Goal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Goal])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals").addQueryStringParameters())
			}
			/** Gets a goal to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Goal]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Goal])
			}
			object get {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Goal]] = (fun: get) => fun.apply()
			}
			/** Updates an existing goal. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoal(body: Schema.Goal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Goal])
			}
			object update {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}").addQueryStringParameters())
			}
			/** Updates an existing goal. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoal(body: Schema.Goal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Goal])
			}
			object patch {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}").addQueryStringParameters())
			}
			/** Lists goals to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Goals]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Goals])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Goals]] = (fun: list) => fun.apply()
			}
		}
		object remarketingAudience {
			/** Creates a new remarketing audience. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withRemarketingAudience(body: Schema.RemarketingAudience) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RemarketingAudience])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences").addQueryStringParameters())
			}
			/** Delete a remarketing audience. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets a remarketing audience to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RemarketingAudience]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RemarketingAudience])
			}
			object get {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.RemarketingAudience]] = (fun: get) => fun.apply()
			}
			/** Updates an existing remarketing audience. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withRemarketingAudience(body: Schema.RemarketingAudience) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.RemarketingAudience])
			}
			object update {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}").addQueryStringParameters())
			}
			/** Updates an existing remarketing audience. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withRemarketingAudience(body: Schema.RemarketingAudience) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.RemarketingAudience])
			}
			object patch {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}").addQueryStringParameters())
			}
			/** Lists remarketing audiences to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RemarketingAudiences]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RemarketingAudiences])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, `type`: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString, "type" -> `type`.toString))
				given Conversion[list, Future[Schema.RemarketingAudiences]] = (fun: list) => fun.apply()
			}
		}
		object profileUserLinks {
			/** Removes a user from the given view (profile). */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks/${linkId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Adds a new user to the given view (profile). */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks").addQueryStringParameters())
			}
			/** Lists profile-user links for a given view (profile). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""", """https://www.googleapis.com/auth/analytics.manage.users.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			/** Updates permissions for an existing user on the given view (profile). */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.manage.users""")
				/** Perform the request */
				def withEntityUserLink(body: Schema.EntityUserLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks/${linkId}").addQueryStringParameters())
			}
		}
		object accountSummaries {
			/** Lists account summaries (lightweight tree comprised of accounts/properties/profiles) to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountSummaries]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountSummaries])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accountSummaries").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.AccountSummaries]] = (fun: list) => fun.apply()
			}
		}
		object customDimensions {
			/** Create a new custom dimension. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomDimension(body: Schema.CustomDimension) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomDimension])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions").addQueryStringParameters())
			}
			/** Get a custom dimension to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomDimension]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomDimension])
			}
			object get {
				def apply(accountId: String, customDimensionId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CustomDimension]] = (fun: get) => fun.apply()
			}
			/** Updates an existing custom dimension. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomDimension(body: Schema.CustomDimension) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CustomDimension])
			}
			object update {
				def apply(accountId: String, customDimensionId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}").addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			/** Updates an existing custom dimension. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withCustomDimension(body: Schema.CustomDimension) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomDimension])
			}
			object patch {
				def apply(accountId: String, customDimensionId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}").addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			/** Lists custom dimensions to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomDimensions]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomDimensions])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomDimensions]] = (fun: list) => fun.apply()
			}
		}
		object profiles {
			/** Create a new view (profile). */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfile(body: Schema.Profile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Profile])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles").addQueryStringParameters())
			}
			/** Deletes a view (profile). */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets a view (profile) to which the user has access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Profile]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Profile])
			}
			object get {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Profile]] = (fun: get) => fun.apply()
			}
			/** Updates an existing view (profile). */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfile(body: Schema.Profile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Profile])
			}
			object update {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}").addQueryStringParameters())
			}
			/** Updates an existing view (profile). This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withProfile(body: Schema.Profile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Profile])
			}
			object patch {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}").addQueryStringParameters())
			}
			/** Lists views (profiles) to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Profiles]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Profiles])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Profiles]] = (fun: list) => fun.apply()
			}
		}
		object accounts {
			/** Lists all accounts to which the user has access. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Accounts]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Accounts])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"management/accounts").addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Accounts]] = (fun: list) => fun.apply()
			}
		}
	}
	object metadata {
		object columns {
			/** Lists all columns for a report type */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Columns]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Columns])
			}
			object list {
				def apply(reportType: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"metadata/${reportType}/columns").addQueryStringParameters())
				given Conversion[list, Future[Schema.Columns]] = (fun: list) => fun.apply()
			}
		}
	}
	object provisioning {
		/** Creates an account ticket. */
		class createAccountTicket(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.provision""")
			/** Perform the request */
			def withAccountTicket(body: Schema.AccountTicket) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountTicket])
		}
		object createAccountTicket {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): createAccountTicket = new createAccountTicket(ws.url(BASE_URL + s"provisioning/createAccountTicket").addQueryStringParameters())
		}
		/** Provision account. */
		class createAccountTree(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.provision""")
			/** Perform the request */
			def withAccountTreeRequest(body: Schema.AccountTreeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountTreeResponse])
		}
		object createAccountTree {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): createAccountTree = new createAccountTree(ws.url(BASE_URL + s"provisioning/createAccountTree").addQueryStringParameters())
		}
	}
	object userDeletion {
		object userDeletionRequest {
			/** Insert or update a user deletion requests. */
			class upsert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.user.deletion""")
				/** Perform the request */
				def withUserDeletionRequest(body: Schema.UserDeletionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserDeletionRequest])
			}
			object upsert {
				def apply()(using signer: RequestSigner, ec: ExecutionContext): upsert = new upsert(ws.url(BASE_URL + s"userDeletion/userDeletionRequests:upsert").addQueryStringParameters())
			}
		}
	}
}
