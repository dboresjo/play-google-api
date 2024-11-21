package com.boresjo.play.api.google.analytics

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/analytics/v3/"

	object data {
		object ga {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GaData]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GaData])
			}
			object get {
				def apply(dimensions: String, end_date: String, filters: String, ids: String, include_empty_rows: Boolean, max_results: Int, metrics: String, output: String, samplingLevel: String, segment: String, sort: String, start_date: String, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"data/ga")).addQueryStringParameters("dimensions" -> dimensions.toString, "end-date" -> end_date.toString, "filters" -> filters.toString, "ids" -> ids.toString, "include-empty-rows" -> include_empty_rows.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "output" -> output.toString, "samplingLevel" -> samplingLevel.toString, "segment" -> segment.toString, "sort" -> sort.toString, "start-date" -> start_date.toString, "start-index" -> start_index.toString))
				given Conversion[get, Future[Schema.GaData]] = (fun: get) => fun.apply()
			}
		}
		object mcf {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.McfData]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.McfData])
			}
			object get {
				def apply(dimensions: String, end_date: String, filters: String, ids: String, max_results: Int, metrics: String, samplingLevel: String, sort: String, start_date: String, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"data/mcf")).addQueryStringParameters("dimensions" -> dimensions.toString, "end-date" -> end_date.toString, "filters" -> filters.toString, "ids" -> ids.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "samplingLevel" -> samplingLevel.toString, "sort" -> sort.toString, "start-date" -> start_date.toString, "start-index" -> start_index.toString))
				given Conversion[get, Future[Schema.McfData]] = (fun: get) => fun.apply()
			}
		}
		object realtime {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RealtimeData]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.RealtimeData])
			}
			object get {
				def apply(dimensions: String, filters: String, ids: String, max_results: Int, metrics: String, sort: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"data/realtime")).addQueryStringParameters("dimensions" -> dimensions.toString, "filters" -> filters.toString, "ids" -> ids.toString, "max-results" -> max_results.toString, "metrics" -> metrics.toString, "sort" -> sort.toString))
				given Conversion[get, Future[Schema.RealtimeData]] = (fun: get) => fun.apply()
			}
		}
	}
	object management {
		object experiments {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExperiment(body: Schema.Experiment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Experiment])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Experiment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Experiment])
			}
			object get {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Experiment]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExperiment(body: Schema.Experiment) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Experiment])
			}
			object update {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExperiment(body: Schema.Experiment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Experiment])
			}
			object patch {
				def apply(accountId: String, experimentId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments/${experimentId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Experiments]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Experiments])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/experiments")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Experiments]] = (fun: list) => fun.apply()
			}
		}
		object profileFilterLinks {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ProfileFilterLink])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProfileFilterLink]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ProfileFilterLink])
			}
			object get {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.ProfileFilterLink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ProfileFilterLink])
			}
			object update {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfileFilterLink(body: Schema.ProfileFilterLink) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ProfileFilterLink])
			}
			object patch {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks/${linkId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProfileFilterLinks]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ProfileFilterLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/profileFilterLinks")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.ProfileFilterLinks]] = (fun: list) => fun.apply()
			}
		}
		object uploads {
			class deleteUploadData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnalyticsDataimportDeleteUploadDataRequest(body: Schema.AnalyticsDataimportDeleteUploadDataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
			}
			object deleteUploadData {
				def apply(accountId: String, customDataSourceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): deleteUploadData = new deleteUploadData(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/deleteUploadData")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Upload]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Upload])
			}
			object get {
				def apply(accountId: String, customDataSourceId: String, uploadId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads/${uploadId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Upload]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Uploads]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Uploads])
			}
			object list {
				def apply(accountId: String, customDataSourceId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Uploads]] = (fun: list) => fun.apply()
			}
			class uploadData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Upload]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.Upload])
			}
			object uploadData {
				def apply(accountId: String, customDataSourceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): uploadData = new uploadData(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources/${customDataSourceId}/uploads")).addQueryStringParameters())
				given Conversion[uploadData, Future[Schema.Upload]] = (fun: uploadData) => fun.apply()
			}
		}
		object clientId {
			class hashClientId(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withHashClientIdRequest(body: Schema.HashClientIdRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HashClientIdResponse])
			}
			object hashClientId {
				def apply()(using auth: AuthToken, ec: ExecutionContext): hashClientId = new hashClientId(auth(ws.url(BASE_URL + s"management/clientId:hashClientId")).addQueryStringParameters())
			}
		}
		object webpropertyUserLinks {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks/${linkId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityUserLinks/${linkId}")).addQueryStringParameters())
			}
		}
		object webPropertyAdWordsLinks {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EntityAdWordsLink])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityAdWordsLink]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EntityAdWordsLink])
			}
			object get {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.EntityAdWordsLink]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EntityAdWordsLink])
			}
			object update {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityAdWordsLink(body: Schema.EntityAdWordsLink) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EntityAdWordsLink])
			}
			object patch {
				def apply(accountId: String, webPropertyAdWordsLinkId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks/${webPropertyAdWordsLinkId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityAdWordsLinks]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EntityAdWordsLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/entityAdWordsLinks")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityAdWordsLinks]] = (fun: list) => fun.apply()
			}
		}
		object webproperties {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebproperty(body: Schema.Webproperty) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Webproperty])
			}
			object insert {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Webproperty]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Webproperty])
			}
			object get {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Webproperty]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebproperty(body: Schema.Webproperty) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Webproperty])
			}
			object update {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebproperty(body: Schema.Webproperty) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Webproperty])
			}
			object patch {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Webproperties]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Webproperties])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Webproperties]] = (fun: list) => fun.apply()
			}
		}
		object customDataSources {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomDataSources]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.CustomDataSources])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDataSources")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomDataSources]] = (fun: list) => fun.apply()
			}
		}
		object filters {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFilter(body: Schema.Filter) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Filter])
			}
			object insert {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Filter])
			}
			object delete {
				def apply(accountId: String, filterId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}")).addQueryStringParameters())
				given Conversion[delete, Future[Schema.Filter]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Filter])
			}
			object get {
				def apply(accountId: String, filterId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Filter]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFilter(body: Schema.Filter) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Filter])
			}
			object update {
				def apply(accountId: String, filterId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFilter(body: Schema.Filter) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Filter])
			}
			object patch {
				def apply(accountId: String, filterId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters/${filterId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Filters]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Filters])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/filters")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Filters]] = (fun: list) => fun.apply()
			}
		}
		object segments {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Segments]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Segments])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/segments")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Segments]] = (fun: list) => fun.apply()
			}
		}
		object customMetrics {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomMetric(body: Schema.CustomMetric) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CustomMetric])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomMetric]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.CustomMetric])
			}
			object get {
				def apply(accountId: String, customMetricId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.CustomMetric]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomMetric(body: Schema.CustomMetric) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.CustomMetric])
			}
			object update {
				def apply(accountId: String, customMetricId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}")).addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomMetric(body: Schema.CustomMetric) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.CustomMetric])
			}
			object patch {
				def apply(accountId: String, customMetricId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics/${customMetricId}")).addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomMetrics]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.CustomMetrics])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customMetrics")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomMetrics]] = (fun: list) => fun.apply()
			}
		}
		object unsampledReports {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, profileId: String, unsampledReportId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports/${unsampledReportId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UnsampledReport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.UnsampledReport])
			}
			object get {
				def apply(accountId: String, profileId: String, unsampledReportId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports/${unsampledReportId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.UnsampledReport]] = (fun: get) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnsampledReport(body: Schema.UnsampledReport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UnsampledReport])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UnsampledReports]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.UnsampledReports])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/unsampledReports")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.UnsampledReports]] = (fun: list) => fun.apply()
			}
		}
		object accountUserLinks {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks/${linkId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/entityUserLinks/${linkId}")).addQueryStringParameters())
			}
		}
		object goals {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoal(body: Schema.Goal) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Goal])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Goal]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Goal])
			}
			object get {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Goal]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoal(body: Schema.Goal) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Goal])
			}
			object update {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoal(body: Schema.Goal) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Goal])
			}
			object patch {
				def apply(accountId: String, goalId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals/${goalId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Goals]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Goals])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/goals")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Goals]] = (fun: list) => fun.apply()
			}
		}
		object remarketingAudience {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemarketingAudience(body: Schema.RemarketingAudience) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RemarketingAudience])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RemarketingAudience]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.RemarketingAudience])
			}
			object get {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.RemarketingAudience]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemarketingAudience(body: Schema.RemarketingAudience) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.RemarketingAudience])
			}
			object update {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemarketingAudience(body: Schema.RemarketingAudience) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.RemarketingAudience])
			}
			object patch {
				def apply(accountId: String, remarketingAudienceId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences/${remarketingAudienceId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RemarketingAudiences]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.RemarketingAudiences])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, `type`: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/remarketingAudiences")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString, "type" -> `type`.toString))
				given Conversion[list, Future[Schema.RemarketingAudiences]] = (fun: list) => fun.apply()
			}
		}
		object profileUserLinks {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks/${linkId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EntityUserLink])
			}
			object insert {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityUserLinks]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EntityUserLinks])
			}
			object list {
				def apply(accountId: String, max_results: Int, profileId: String, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.EntityUserLinks]] = (fun: list) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEntityUserLink(body: Schema.EntityUserLink) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EntityUserLink])
			}
			object update {
				def apply(accountId: String, linkId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}/entityUserLinks/${linkId}")).addQueryStringParameters())
			}
		}
		object accountSummaries {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountSummaries]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.AccountSummaries])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accountSummaries")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.AccountSummaries]] = (fun: list) => fun.apply()
			}
		}
		object customDimensions {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomDimension(body: Schema.CustomDimension) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CustomDimension])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomDimension]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.CustomDimension])
			}
			object get {
				def apply(accountId: String, customDimensionId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.CustomDimension]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomDimension(body: Schema.CustomDimension) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.CustomDimension])
			}
			object update {
				def apply(accountId: String, customDimensionId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}")).addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomDimension(body: Schema.CustomDimension) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.CustomDimension])
			}
			object patch {
				def apply(accountId: String, customDimensionId: String, ignoreCustomDataSourceLinks: Boolean, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions/${customDimensionId}")).addQueryStringParameters("ignoreCustomDataSourceLinks" -> ignoreCustomDataSourceLinks.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomDimensions]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.CustomDimensions])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/customDimensions")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.CustomDimensions]] = (fun: list) => fun.apply()
			}
		}
		object profiles {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfile(body: Schema.Profile) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Profile])
			}
			object insert {
				def apply(accountId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Profile]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Profile])
			}
			object get {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Profile]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfile(body: Schema.Profile) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Profile])
			}
			object update {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}")).addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProfile(body: Schema.Profile) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Profile])
			}
			object patch {
				def apply(accountId: String, profileId: String, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles/${profileId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Profiles]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Profiles])
			}
			object list {
				def apply(accountId: String, max_results: Int, start_index: Int, webPropertyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts/${accountId}/webproperties/${webPropertyId}/profiles")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Profiles]] = (fun: list) => fun.apply()
			}
		}
		object accounts {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Accounts]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Accounts])
			}
			object list {
				def apply(max_results: Int, start_index: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"management/accounts")).addQueryStringParameters("max-results" -> max_results.toString, "start-index" -> start_index.toString))
				given Conversion[list, Future[Schema.Accounts]] = (fun: list) => fun.apply()
			}
		}
	}
	object metadata {
		object columns {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Columns]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Columns])
			}
			object list {
				def apply(reportType: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"metadata/${reportType}/columns")).addQueryStringParameters())
				given Conversion[list, Future[Schema.Columns]] = (fun: list) => fun.apply()
			}
		}
	}
	object provisioning {
		class createAccountTicket(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountTicket(body: Schema.AccountTicket) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AccountTicket])
		}
		object createAccountTicket {
			def apply()(using auth: AuthToken, ec: ExecutionContext): createAccountTicket = new createAccountTicket(auth(ws.url(BASE_URL + s"provisioning/createAccountTicket")).addQueryStringParameters())
		}
		class createAccountTree(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountTreeRequest(body: Schema.AccountTreeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AccountTreeResponse])
		}
		object createAccountTree {
			def apply()(using auth: AuthToken, ec: ExecutionContext): createAccountTree = new createAccountTree(auth(ws.url(BASE_URL + s"provisioning/createAccountTree")).addQueryStringParameters())
		}
	}
	object userDeletion {
		object userDeletionRequest {
			class upsert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUserDeletionRequest(body: Schema.UserDeletionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UserDeletionRequest])
			}
			object upsert {
				def apply()(using auth: AuthToken, ec: ExecutionContext): upsert = new upsert(auth(ws.url(BASE_URL + s"userDeletion/userDeletionRequests:upsert")).addQueryStringParameters())
			}
		}
	}
}
