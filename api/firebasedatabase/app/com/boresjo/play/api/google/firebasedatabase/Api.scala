package com.boresjo.play.api.google.firebasedatabase

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
		"""https://www.googleapis.com/auth/firebase.readonly""" /* View all your Firebase data and settings */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */
	)

	private val BASE_URL = "https://firebasedatabase.googleapis.com/"

	object projects {
		object locations {
			object instances {
				/** Restores a DatabaseInstance that was previously marked to be deleted. After the delete method is used, DatabaseInstances are set to the DELETED state for 20 days, and will be purged within 30 days. Databases in the DELETED state can be undeleted without losing any data. This method may only be used on a DatabaseInstance in the DELETED state. Purged DatabaseInstances may not be recovered. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withUndeleteDatabaseInstanceRequest(body: Schema.UndeleteDatabaseInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Enables a DatabaseInstance. The database must have been disabled previously using DisableDatabaseInstance. The state of a successfully reenabled DatabaseInstance is ACTIVE. */
				class reenable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withReenableDatabaseInstanceRequest(body: Schema.ReenableDatabaseInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object reenable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reenable = new reenable(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reenable").addQueryStringParameters("name" -> name.toString))
				}
				/** Requests that a new DatabaseInstance be created. The state of a successfully created DatabaseInstance is ACTIVE. Only available for projects on the Blaze plan. Projects can be upgraded using the Cloud Billing API https://cloud.google.com/billing/reference/rest/v1/projects/updateBillingInfo. Note that it might take a few minutes for billing enablement state to propagate to Firebase systems. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withDatabaseInstance(body: Schema.DatabaseInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, databaseId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Marks a DatabaseInstance to be deleted. The DatabaseInstance will be set to the DELETED state for 20 days, and will be purged within 30 days. The default database cannot be deleted. IDs for deleted database instances may never be recovered or re-used. The Database may only be deleted if it is already in a DISABLED state. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DatabaseInstance])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.DatabaseInstance]] = (fun: delete) => fun.apply()
				}
				/** Disables a DatabaseInstance. The database can be re-enabled later using ReenableDatabaseInstance. When a database is disabled, all reads and writes are denied, including view access in the Firebase console. */
				class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withDisableDatabaseInstanceRequest(body: Schema.DisableDatabaseInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the DatabaseInstance identified by the specified resource name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatabaseInstance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DatabaseInstance]] = (fun: get) => fun.apply()
				}
				/** Lists each DatabaseInstance associated with the specified parent project. The list items are returned in no particular order, but will be a consistent view of the database instances when additional requests are made with a `pageToken`. The resulting list contains instances in any STATE. The list results may be stale by a few seconds. Use GetDatabaseInstance for consistent reads. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseInstancesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, showDeleted: Boolean, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabaseInstancesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
