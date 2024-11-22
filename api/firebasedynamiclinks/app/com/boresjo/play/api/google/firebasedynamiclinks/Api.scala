package com.boresjo.play.api.google.firebasedynamiclinks

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
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */
	)

	private val BASE_URL = "https://firebasedynamiclinks.googleapis.com/"

	object shortLinks {
		/** Creates a short Dynamic Link given either a valid long Dynamic Link or details such as Dynamic Link domain, Android and iOS app information. The created short Dynamic Link will not expire. Repeated calls with the same long Dynamic Link or Dynamic Link information will produce the same short Dynamic Link. The Dynamic Link domain in the request must be owned by requester's Firebase project. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withCreateShortDynamicLinkRequest(body: Schema.CreateShortDynamicLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateShortDynamicLinkResponse])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/shortLinks").addQueryStringParameters())
		}
	}
	object v1 {
		/** Get iOS strong/weak-match info for post-install attribution. */
		class installAttribution(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGetIosPostInstallAttributionRequest(body: Schema.GetIosPostInstallAttributionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetIosPostInstallAttributionResponse])
		}
		object installAttribution {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): installAttribution = new installAttribution(ws.url(BASE_URL + s"v1/installAttribution").addQueryStringParameters())
		}
		/** Fetches analytics stats of a short Dynamic Link for a given duration. Metrics include number of clicks, redirects, installs, app first opens, and app reopens. */
		class getLinkStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DynamicLinkStats]) {
			val scopes = Seq("""https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DynamicLinkStats])
		}
		object getLinkStats {
			def apply(dynamicLink: String, durationDays: String, sdkVersion: String)(using signer: RequestSigner, ec: ExecutionContext): getLinkStats = new getLinkStats(ws.url(BASE_URL + s"v1/${dynamicLink}/linkStats").addQueryStringParameters("durationDays" -> durationDays.toString, "sdkVersion" -> sdkVersion.toString))
			given Conversion[getLinkStats, Future[Schema.DynamicLinkStats]] = (fun: getLinkStats) => fun.apply()
		}
		/** Get iOS reopen attribution for app universal link open deeplinking. */
		class reopenAttribution(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGetIosReopenAttributionRequest(body: Schema.GetIosReopenAttributionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetIosReopenAttributionResponse])
		}
		object reopenAttribution {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): reopenAttribution = new reopenAttribution(ws.url(BASE_URL + s"v1/reopenAttribution").addQueryStringParameters())
		}
	}
	object managedShortLinks {
		/** Creates a managed short Dynamic Link given either a valid long Dynamic Link or details such as Dynamic Link domain, Android and iOS app information. The created short Dynamic Link will not expire. This differs from CreateShortDynamicLink in the following ways: - The request will also contain a name for the link (non unique name for the front end). - The response must be authenticated with an auth token (generated with the admin service account). - The link will appear in the FDL list of links in the console front end. The Dynamic Link domain in the request must be owned by requester's Firebase project. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withCreateManagedShortLinkRequest(body: Schema.CreateManagedShortLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateManagedShortLinkResponse])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/managedShortLinks:create").addQueryStringParameters())
		}
	}
}
