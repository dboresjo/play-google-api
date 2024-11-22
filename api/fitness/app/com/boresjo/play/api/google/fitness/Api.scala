package com.boresjo.play.api.google.fitness

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
		"""https://www.googleapis.com/auth/fitness.body.write""" /* Add info about your body measurements to Google Fit */,
		"""https://www.googleapis.com/auth/fitness.body.read""" /* See info about your body measurements in Google Fit */,
		"""https://www.googleapis.com/auth/fitness.heart_rate.write""" /* Add to your heart rate data in Google Fit. I consent to Google using my heart rate information with this app. */,
		"""https://www.googleapis.com/auth/fitness.heart_rate.read""" /* See your heart rate data in Google Fit. I consent to Google sharing my heart rate information with this app. */,
		"""https://www.googleapis.com/auth/fitness.blood_glucose.write""" /* Add info about your blood glucose to Google Fit. I consent to Google using my blood glucose information with this app. */,
		"""https://www.googleapis.com/auth/fitness.nutrition.write""" /* Add to info about your nutrition in Google Fit */,
		"""https://www.googleapis.com/auth/fitness.nutrition.read""" /* See info about your nutrition in Google Fit */,
		"""https://www.googleapis.com/auth/fitness.reproductive_health.read""" /* See info about your reproductive health in Google Fit. I consent to Google sharing my reproductive health information with this app. */,
		"""https://www.googleapis.com/auth/fitness.blood_pressure.read""" /* See info about your blood pressure in Google Fit. I consent to Google sharing my blood pressure information with this app. */,
		"""https://www.googleapis.com/auth/fitness.location.read""" /* See your Google Fit speed and distance data */,
		"""https://www.googleapis.com/auth/fitness.sleep.write""" /* Add to your sleep data in Google Fit. I consent to Google using my sleep information with this app. */,
		"""https://www.googleapis.com/auth/fitness.activity.read""" /* Use Google Fit to see and store your physical activity data */,
		"""https://www.googleapis.com/auth/fitness.body_temperature.read""" /* See info about your body temperature in Google Fit. I consent to Google sharing my body temperature information with this app. */,
		"""https://www.googleapis.com/auth/fitness.activity.write""" /* Add to your Google Fit physical activity data */,
		"""https://www.googleapis.com/auth/fitness.location.write""" /* Add to your Google Fit location data */,
		"""https://www.googleapis.com/auth/fitness.sleep.read""" /* See your sleep data in Google Fit. I consent to Google sharing my sleep information with this app. */,
		"""https://www.googleapis.com/auth/fitness.oxygen_saturation.write""" /* Add info about your oxygen saturation in Google Fit. I consent to Google using my oxygen saturation information with this app. */,
		"""https://www.googleapis.com/auth/fitness.reproductive_health.write""" /* Add info about your reproductive health in Google Fit. I consent to Google using my reproductive health information with this app. */,
		"""https://www.googleapis.com/auth/fitness.oxygen_saturation.read""" /* See info about your oxygen saturation in Google Fit. I consent to Google sharing my oxygen saturation information with this app. */,
		"""https://www.googleapis.com/auth/fitness.blood_pressure.write""" /* Add info about your blood pressure in Google Fit. I consent to Google using my blood pressure information with this app. */,
		"""https://www.googleapis.com/auth/fitness.body_temperature.write""" /* Add to info about your body temperature in Google Fit. I consent to Google using my body temperature information with this app. */,
		"""https://www.googleapis.com/auth/fitness.blood_glucose.read""" /* See info about your blood glucose in Google Fit. I consent to Google sharing my blood glucose information with this app. */
	)

	private val BASE_URL = "https://fitness.googleapis.com/fitness/v1/users/"

	object users {
		object dataSources {
			/** Creates a new data source that is unique across all data sources belonging to this user. A data source is a unique source of sensor data. Data sources can expose raw data coming from hardware sensors on local or companion devices. They can also expose derived data, created by transforming or merging other data sources. Multiple data sources can exist for the same data type. Every data point in every dataset inserted into or read from the Fitness API has an associated data source. Each data source produces a unique stream of dataset updates, with a unique data source identifier. Not all changes to data source affect the data stream ID, so that data collected by updated versions of the same application/device can still be considered to belong to the same data source. Data sources are identified using a string generated by the server, based on the contents of the source being created. The dataStreamId field should not be set when invoking this method. It will be automatically generated by the server with the correct format. If a dataStreamId is set, it must match the format that the server would generate. This format is a combination of some fields from the data source, and has a specific order. If it doesn't match, the request will fail with an error. Specifying a DataType which is not a known type (beginning with "com.google.") will create a DataSource with a &#42;custom data type&#42;. Custom data types are only readable by the application that created them. Custom data types are &#42;deprecated&#42;; use standard data types instead. In addition to the data source fields included in the data source ID, the developer project number that is authenticated when creating the data source is included. This developer project number is obfuscated when read by any other developer reading public data types. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def withDataSource(body: Schema.DataSource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DataSource])
			}
			object create {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${userId}/dataSources").addQueryStringParameters())
			}
			/** Deletes the specified data source. The request will fail if the data source contains any data points. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DataSource])
			}
			object delete {
				def apply(dataSourceId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.DataSource]] = (fun: delete) => fun.apply()
			}
			/** Returns the specified data source. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DataSource])
			}
			object get {
				def apply(dataSourceId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
			}
			/** Updates the specified data source. The dataStreamId, dataType, type, dataStreamName, and device properties with the exception of version, cannot be modified. Data sources are identified by their dataStreamId. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def withDataSource(body: Schema.DataSource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.DataSource])
			}
			object update {
				def apply(userId: String, dataSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}").addQueryStringParameters())
			}
			/** Lists all data sources that are visible to the developer, using the OAuth scopes provided. The list is not exhaustive; the user may have private data sources that are only visible to other developers, or calls using other scopes. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataSourcesResponse])
			}
			object list {
				def apply(userId: String, dataTypeName: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${userId}/dataSources").addQueryStringParameters("dataTypeName" -> dataTypeName.toString))
				given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
			}
			object dataPointChanges {
				/** Queries for user's data point changes for a particular data source. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataPointChangesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataPointChangesResponse])
				}
				object list {
					def apply(userId: String, pageToken: String, dataSourceId: String, limit: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/dataPointChanges").addQueryStringParameters("pageToken" -> pageToken.toString, "limit" -> limit.toString))
					given Conversion[list, Future[Schema.ListDataPointChangesResponse]] = (fun: list) => fun.apply()
				}
			}
			object datasets {
				/** Adds data points to a dataset. The dataset need not be previously created. All points within the given dataset will be returned with subsquent calls to retrieve this dataset. Data points can belong to more than one dataset. This method does not use patch semantics: the data points provided are merely inserted, with no existing data replaced. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
					/** Perform the request */
					def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Dataset])
				}
				object patch {
					def apply(datasetId: String, dataSourceId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}").addQueryStringParameters())
				}
				/** Performs an inclusive delete of all data points whose start and end times have any overlap with the time range specified by the dataset ID. For most data types, the entire data point will be deleted. For data types where the time span represents a consistent value (such as com.google.activity.segment), and a data point straddles either end point of the dataset, only the overlapping portion of the data point will be deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(dataSourceId: String, datasetId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Returns a dataset containing all data points whose start and end times overlap with the specified range of the dataset minimum start time and maximum end time. Specifically, any data point whose start time is less than or equal to the dataset end time and whose end time is greater than or equal to the dataset start time. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
					val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Dataset])
				}
				object get {
					def apply(datasetId: String, pageToken: String, userId: String, dataSourceId: String, limit: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}").addQueryStringParameters("pageToken" -> pageToken.toString, "limit" -> limit.toString))
					given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
				}
			}
		}
		object sessions {
			/** Lists sessions previously created. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSessionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSessionsResponse])
			}
			object list {
				def apply(activityType: Int, endTime: String, userId: String, startTime: String, pageToken: String, includeDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${userId}/sessions").addQueryStringParameters("activityType" -> activityType.toString, "endTime" -> endTime.toString, "startTime" -> startTime.toString, "pageToken" -> pageToken.toString, "includeDeleted" -> includeDeleted.toString))
				given Conversion[list, Future[Schema.ListSessionsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates or insert a given session. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def withSession(body: Schema.Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Session])
			}
			object update {
				def apply(userId: String, sessionId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${userId}/sessions/${sessionId}").addQueryStringParameters())
			}
			/** Deletes a session specified by the given session ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(sessionId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${userId}/sessions/${sessionId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
		object dataset {
			/** Aggregates data of a certain type or stream into buckets divided by a given type of boundary. Multiple data sets of multiple types and from multiple sources can be aggregated into exactly one bucket type per request. */
			class aggregate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/fitness.activity.read""", """https://www.googleapis.com/auth/fitness.activity.write""", """https://www.googleapis.com/auth/fitness.blood_glucose.read""", """https://www.googleapis.com/auth/fitness.blood_glucose.write""", """https://www.googleapis.com/auth/fitness.blood_pressure.read""", """https://www.googleapis.com/auth/fitness.blood_pressure.write""", """https://www.googleapis.com/auth/fitness.body.read""", """https://www.googleapis.com/auth/fitness.body.write""", """https://www.googleapis.com/auth/fitness.body_temperature.read""", """https://www.googleapis.com/auth/fitness.body_temperature.write""", """https://www.googleapis.com/auth/fitness.heart_rate.read""", """https://www.googleapis.com/auth/fitness.heart_rate.write""", """https://www.googleapis.com/auth/fitness.location.read""", """https://www.googleapis.com/auth/fitness.location.write""", """https://www.googleapis.com/auth/fitness.nutrition.read""", """https://www.googleapis.com/auth/fitness.nutrition.write""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.read""", """https://www.googleapis.com/auth/fitness.oxygen_saturation.write""", """https://www.googleapis.com/auth/fitness.reproductive_health.read""", """https://www.googleapis.com/auth/fitness.reproductive_health.write""", """https://www.googleapis.com/auth/fitness.sleep.read""", """https://www.googleapis.com/auth/fitness.sleep.write""")
				/** Perform the request */
				def withAggregateRequest(body: Schema.AggregateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AggregateResponse])
			}
			object aggregate {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): aggregate = new aggregate(ws.url(BASE_URL + s"${userId}/dataset:aggregate").addQueryStringParameters())
			}
		}
	}
}
