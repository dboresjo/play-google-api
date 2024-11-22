package com.boresjo.play.api.google.digitalassetlinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://digitalassetlinks.googleapis.com/"

	object assetlinks {
		/** Determines whether the specified (directional) relationship exists between the specified source and target assets. The relation describes the intent of the link between the two assets as claimed by the source asset. An example for such relationships is the delegation of privileges or permissions. This command is most often used by infrastructure systems to check preconditions for an action. For example, a client may want to know if it is OK to send a web URL to a particular mobile app instead. The client can check for the relevant asset link from the website to the mobile app to decide if the operation should be allowed. A note about security: if you specify a secure asset as the source, such as an HTTPS website or an Android app, the API will ensure that any statements used to generate the response have been made in a secure way by the owner of that asset. Conversely, if the source asset is an insecure HTTP website (that is, the URL starts with `http://` instead of `https://`), the API cannot verify its statements securely, and it is not possible to ensure that the website's statements have not been altered by a third party. For more information, see the [Digital Asset Links technical design specification](https://github.com/google/digitalassetlinks/blob/master/well-known/details.md). */
		class check(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckResponse])
		}
		object check {
			def apply(sourceWebSite: String, sourceAndroidAppPackageName: String, sourceAndroidAppCertificateSha256Fingerprint: String, relation: String, targetWebSite: String, targetAndroidAppPackageName: String, targetAndroidAppCertificateSha256Fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"v1/assetlinks:check").addQueryStringParameters("source.web.site" -> sourceWebSite.toString, "source.androidApp.packageName" -> sourceAndroidAppPackageName.toString, "source.androidApp.certificate.sha256Fingerprint" -> sourceAndroidAppCertificateSha256Fingerprint.toString, "relation" -> relation.toString, "target.web.site" -> targetWebSite.toString, "target.androidApp.packageName" -> targetAndroidAppPackageName.toString, "target.androidApp.certificate.sha256Fingerprint" -> targetAndroidAppCertificateSha256Fingerprint.toString))
			given Conversion[check, Future[Schema.CheckResponse]] = (fun: check) => fun.apply()
		}
	}
	object statements {
		/** Retrieves a list of all statements from a given source that match the specified target and statement string. The API guarantees that all statements with secure source assets, such as HTTPS websites or Android apps, have been made in a secure way by the owner of those assets, as described in the [Digital Asset Links technical design specification](https://github.com/google/digitalassetlinks/blob/master/well-known/details.md). Specifically, you should consider that for insecure websites (that is, where the URL starts with `http://` instead of `https://`), this guarantee cannot be made. The `List` command is most useful in cases where the API client wants to know all the ways in which two assets are related, or enumerate all the relationships from a particular source asset. Example: a feature that helps users navigate to related items. When a mobile app is running on a device, the feature would make it easy to navigate to the corresponding web site or Google+ profile. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListResponse])
		}
		object list {
			def apply(sourceWebSite: String, sourceAndroidAppPackageName: String, sourceAndroidAppCertificateSha256Fingerprint: String, relation: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/statements:list").addQueryStringParameters("source.web.site" -> sourceWebSite.toString, "source.androidApp.packageName" -> sourceAndroidAppPackageName.toString, "source.androidApp.certificate.sha256Fingerprint" -> sourceAndroidAppCertificateSha256Fingerprint.toString, "relation" -> relation.toString))
			given Conversion[list, Future[Schema.ListResponse]] = (fun: list) => fun.apply()
		}
	}
}
