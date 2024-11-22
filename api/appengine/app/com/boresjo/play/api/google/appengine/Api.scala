package com.boresjo.play.api.google.appengine

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
		"""https://www.googleapis.com/auth/appengine.admin""" /* View and manage your applications deployed on Google App Engine */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://appengine.googleapis.com/"

	object apps {
		/** Creates an App Engine application for a Google Cloud Platform project. Required fields: id - The ID of the target Cloud Platform project. location - The region (https://cloud.google.com/appengine/docs/locations) where you want the App Engine application located.For more information about App Engine applications, see Managing Projects, Applications, and Billing (https://cloud.google.com/appengine/docs/standard/python/console/). */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withApplication(body: Schema.Application) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps").addQueryStringParameters())
		}
		/** Recreates the required App Engine features for the specified App Engine application, for example a Cloud Storage bucket or App Engine service account. Use this method if you receive an error message about a missing feature, for example, Error retrieving the App Engine service account. If you have deleted your App Engine service account, this will not be able to recreate it. Instead, you should attempt to use the IAM undelete API if possible at https://cloud.google.com/iam/reference/rest/v1/projects.serviceAccounts/undelete?apix_params=%7B"name"%3A"projects%2F-%2FserviceAccounts%2Funique_id"%2C"resource"%3A%7B%7D%7D . If the deletion was recent, the numeric ID can be found in the Cloud Console Activity Log. */
		class repair(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withRepairApplicationRequest(body: Schema.RepairApplicationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object repair {
			def apply(appsId: String)(using signer: RequestSigner, ec: ExecutionContext): repair = new repair(ws.url(BASE_URL + s"v1/apps/${appsId}:repair").addQueryStringParameters())
		}
		/** Gets information about an application. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
			val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Application])
		}
		object get {
			def apply(appsId: String, includeExtraData: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}").addQueryStringParameters("includeExtraData" -> includeExtraData.toString))
			given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
		}
		/** Updates the specified Application resource. You can update the following fields: auth_domain - Google authentication domain for controlling user access to the application. default_cookie_expiration - Cookie expiration policy for the application. iap - Identity-Aware Proxy properties for the application. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withApplication(body: Schema.Application) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(appsId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		/** Lists all the available runtimes for the application. */
		class listRuntimes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRuntimesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The environment of the Application.<br>Possible values:<br>ENVIRONMENT_UNSPECIFIED: Default value.<br>STANDARD: App Engine Standard.<br>FLEXIBLE: App Engine Flexible */
			def withEnvironment(environment: String) = new listRuntimes(req.addQueryStringParameters("environment" -> environment.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRuntimesResponse])
		}
		object listRuntimes {
			def apply(appsId: String)(using signer: RequestSigner, ec: ExecutionContext): listRuntimes = new listRuntimes(ws.url(BASE_URL + s"v1/apps/${appsId}:listRuntimes").addQueryStringParameters())
			given Conversion[listRuntimes, Future[Schema.ListRuntimesResponse]] = (fun: listRuntimes) => fun.apply()
		}
		object services {
			/** Deletes the specified service and all enclosed versions. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(appsId: String, servicesId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets the current configuration of the specified service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Service]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Service])
			}
			object get {
				def apply(appsId: String, servicesId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Service]] = (fun: get) => fun.apply()
			}
			/** Updates the configuration of the specified service. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withService(body: Schema.Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(appsId: String, servicesId: String, updateMask: String, migrateTraffic: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "migrateTraffic" -> migrateTraffic.toString))
			}
			/** Lists all the services in the application. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
			}
			object versions {
				/** Deletes an existing Version resource. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(appsId: String, servicesId: String, versionsId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets the specified Version resource. By default, only a BASIC_VIEW will be returned. Specify the FULL_VIEW parameter to get the full resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
					val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Version])
				}
				object get {
					def apply(appsId: String, servicesId: String, versionsId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters("view" -> view.toString))
					given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
				}
				/** Updates the specified Version resource. You can specify the following fields depending on the App Engine environment and type of scaling that the version resource uses:Standard environment instance_class (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.instance_class)automatic scaling in the standard environment: automatic_scaling.min_idle_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling) automatic_scaling.max_idle_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling) automaticScaling.standard_scheduler_settings.max_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#StandardSchedulerSettings) automaticScaling.standard_scheduler_settings.min_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#StandardSchedulerSettings) automaticScaling.standard_scheduler_settings.target_cpu_utilization (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#StandardSchedulerSettings) automaticScaling.standard_scheduler_settings.target_throughput_utilization (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#StandardSchedulerSettings)basic scaling or manual scaling in the standard environment: serving_status (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.serving_status) manual_scaling.instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#manualscaling)Flexible environment serving_status (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.serving_status)automatic scaling in the flexible environment: automatic_scaling.min_total_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling) automatic_scaling.max_total_instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling) automatic_scaling.cool_down_period_sec (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling) automatic_scaling.cpu_utilization.target_utilization (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#Version.FIELDS.automatic_scaling)manual scaling in the flexible environment: manual_scaling.instances (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#manualscaling) */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVersion(body: Schema.Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(appsId: String, servicesId: String, versionsId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				/** Lists the versions of a service. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVersionsResponse])
				}
				object list {
					def apply(appsId: String, servicesId: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
				}
				/** Deploys code and resource files to a new version. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVersion(body: Schema.Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(appsId: String, servicesId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions").addQueryStringParameters())
				}
				object instances {
					/** Lists the instances of a version.Tip: To aggregate details about instances over time, see the Stackdriver Monitoring API (https://cloud.google.com/monitoring/api/ref_v3/rest/v3/projects.timeSeries/list). */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
					}
					object list {
						def apply(appsId: String, servicesId: String, versionsId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets instance information. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
						val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
					}
					object get {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
					}
					/** Stops a running instance.The instance might be automatically recreated based on the scaling settings of the version. For more information, see "How Instances are Managed" (standard environment (https://cloud.google.com/appengine/docs/standard/python/how-instances-are-managed) | flexible environment (https://cloud.google.com/appengine/docs/flexible/python/how-instances-are-managed)).To ensure that instances are not re-created and avoid getting billed, you can stop all instances within the target version by changing the serving status of the version to STOPPED with the apps.services.versions.patch (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions/patch) method. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}").addQueryStringParameters())
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Enables debugging on a VM instance. This allows you to use the SSH command to connect to the virtual machine where the instance lives. While in "debug mode", the instance continues to serve live traffic. You should delete the instance when you are done debugging and then allow the system to take over and determine if another instance should be started.Only applicable for instances in App Engine flexible environment. */
					class debug(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDebugInstanceRequest(body: Schema.DebugInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object debug {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using signer: RequestSigner, ec: ExecutionContext): debug = new debug(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}:debug").addQueryStringParameters())
					}
				}
			}
		}
		object authorizedDomains {
			/** Lists all domains the user is authorized to administer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedDomainsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedDomainsResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedDomains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedDomainsResponse]] = (fun: list) => fun.apply()
			}
		}
		object authorizedCertificates {
			/** Uploads the specified SSL certificate. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAuthorizedCertificate(body: Schema.AuthorizedCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object create {
				def apply(appsId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates").addQueryStringParameters())
			}
			/** Deletes the specified SSL certificate. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(appsId: String, authorizedCertificatesId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified SSL certificate. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedCertificate]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object get {
				def apply(appsId: String, authorizedCertificatesId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters("view" -> view.toString))
				given Conversion[get, Future[Schema.AuthorizedCertificate]] = (fun: get) => fun.apply()
			}
			/** Updates the specified SSL certificate. To renew a certificate and maintain its existing domain mappings, update certificate_data with a new certificate. The new certificate must be applicable to the same domains as the original certificate. The certificate display_name may also be updated. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAuthorizedCertificate(body: Schema.AuthorizedCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object patch {
				def apply(appsId: String, authorizedCertificatesId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			/** Lists all SSL certificates the user is authorized to administer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedCertificatesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedCertificatesResponse])
			}
			object list {
				def apply(appsId: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedCertificatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(appsId: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/locations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(appsId: String, locationsId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/locations/${locationsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
		}
		object domainMappings {
			/** Maps a domain to an application. A user must be authorized to administer a domain in order to map it to an application. For a list of available authorized domains, see AuthorizedDomains.ListAuthorizedDomains. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDomainMapping(body: Schema.DomainMapping) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(appsId: String, overrideStrategy: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings").addQueryStringParameters("overrideStrategy" -> overrideStrategy.toString))
			}
			/** Deletes the specified domain mapping. A user must be authorized to administer the associated domain in order to delete a DomainMapping resource. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(appsId: String, domainMappingsId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified domain mapping. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DomainMapping]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DomainMapping])
			}
			object get {
				def apply(appsId: String, domainMappingsId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DomainMapping]] = (fun: get) => fun.apply()
			}
			/** Updates the specified domain mapping. To map an SSL certificate to a domain mapping, update certificate_id to point to an AuthorizedCertificate resource. A user must be authorized to administer the associated domain in order to update a DomainMapping resource. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDomainMapping(body: Schema.DomainMapping) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(appsId: String, domainMappingsId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			/** Lists the domain mappings on an application. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDomainMappingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDomainMappingsResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDomainMappingsResponse]] = (fun: list) => fun.apply()
			}
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(appsId: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/operations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(appsId: String, operationsId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/operations/${operationsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object firewall {
			object ingressRules {
				/** Creates a firewall rule for the application. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFirewallRule(body: Schema.FirewallRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FirewallRule])
				}
				object create {
					def apply(appsId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules").addQueryStringParameters())
				}
				/** Replaces the entire firewall ruleset in one bulk operation. This overrides and replaces the rules of an existing firewall with the new rules.If the final rule does not match traffic with the '&#42;' wildcard IP range, then an "allow all" rule is explicitly added to the end of the list. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withBatchUpdateIngressRulesRequest(body: Schema.BatchUpdateIngressRulesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateIngressRulesResponse])
				}
				object batchUpdate {
					def apply(appsId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules:batchUpdate").addQueryStringParameters())
				}
				/** Deletes the specified firewall rule. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(appsId: String, ingressRulesId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets the specified firewall rule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FirewallRule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FirewallRule])
				}
				object get {
					def apply(appsId: String, ingressRulesId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.FirewallRule]] = (fun: get) => fun.apply()
				}
				/** Updates the specified firewall rule. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFirewallRule(body: Schema.FirewallRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirewallRule])
				}
				object patch {
					def apply(appsId: String, ingressRulesId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				/** Lists the firewall rules of an application. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListIngressRulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListIngressRulesResponse])
				}
				object list {
					def apply(appsId: String, pageSize: Int, pageToken: String, matchingAddress: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "matchingAddress" -> matchingAddress.toString))
					given Conversion[list, Future[Schema.ListIngressRulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object locations {
			object applications {
				object services {
					object versions {
						/** Deletes an existing Version resource. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId: String, locationsId: String, applicationsId: String, servicesId: String, versionsId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters())
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
					}
				}
				object authorizedDomains {
					/** Lists all domains the user is authorized to administer. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedDomainsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/appengine.admin""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedDomainsResponse])
					}
					object list {
						def apply(projectsId: String, locationsId: String, applicationsId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/authorizedDomains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAuthorizedDomainsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
