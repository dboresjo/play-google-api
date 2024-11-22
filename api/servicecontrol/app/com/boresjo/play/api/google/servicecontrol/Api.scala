package com.boresjo.play.api.google.servicecontrol

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
		"""https://www.googleapis.com/auth/servicecontrol""" /* Manage your Google Service Control data */
	)

	private val BASE_URL = "https://servicecontrol.googleapis.com/"

	object services {
		/** Private Preview. This feature is only available for approved services. This method provides admission control for services that are integrated with [Service Infrastructure](https://cloud.google.com/service-infrastructure). It checks whether an operation should be allowed based on the service configuration and relevant policies. It must be called before the operation is executed. For more information, see [Admission Control](https://cloud.google.com/service-infrastructure/docs/admission-control). NOTE: The admission control has an expected policy propagation delay of 60s. The caller &#42;&#42;must&#42;&#42; not depend on the most recent policy changes. NOTE: The admission control has a hard limit of 1 referenced resources per call. If an operation refers to more than 1 resources, the caller must call the Check method multiple times. This method requires the `servicemanagement.services.check` permission on the specified service. For more information, see [Service Control API Access Control](https://cloud.google.com/service-infrastructure/docs/service-control/access-control). */
		class check(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/servicecontrol""")
			/** Perform the request */
			def withCheckRequest(body: Schema.CheckRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckResponse])
		}
		object check {
			def apply(serviceName: String)(using signer: RequestSigner, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"v2/services/${serviceName}:check").addQueryStringParameters())
		}
		/** Private Preview. This feature is only available for approved services. This method provides telemetry reporting for services that are integrated with [Service Infrastructure](https://cloud.google.com/service-infrastructure). It reports a list of operations that have occurred on a service. It must be called after the operations have been executed. For more information, see [Telemetry Reporting](https://cloud.google.com/service-infrastructure/docs/telemetry-reporting). NOTE: The telemetry reporting has a hard limit of 1000 operations and 1MB per Report call. It is recommended to have no more than 100 operations per call. This method requires the `servicemanagement.services.report` permission on the specified service. For more information, see [Service Control API Access Control](https://cloud.google.com/service-infrastructure/docs/service-control/access-control). */
		class report(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/servicecontrol""")
			/** Perform the request */
			def withReportRequest(body: Schema.ReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportResponse])
		}
		object report {
			def apply(serviceName: String)(using signer: RequestSigner, ec: ExecutionContext): report = new report(ws.url(BASE_URL + s"v2/services/${serviceName}:report").addQueryStringParameters())
		}
	}
}
