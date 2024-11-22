package com.boresjo.play.api.google.digitalassetlinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://digitalassetlinks.googleapis.com/"

	object assetlinks {
		class check(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CheckResponse])
		}
		object check {
			def apply(sourceWebSite: String, sourceAndroidAppPackageName: String, sourceAndroidAppCertificateSha256Fingerprint: String, relation: String, targetWebSite: String, targetAndroidAppPackageName: String, targetAndroidAppCertificateSha256Fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"v1/assetlinks:check").addQueryStringParameters("source.web.site" -> sourceWebSite.toString, "source.androidApp.packageName" -> sourceAndroidAppPackageName.toString, "source.androidApp.certificate.sha256Fingerprint" -> sourceAndroidAppCertificateSha256Fingerprint.toString, "relation" -> relation.toString, "target.web.site" -> targetWebSite.toString, "target.androidApp.packageName" -> targetAndroidAppPackageName.toString, "target.androidApp.certificate.sha256Fingerprint" -> targetAndroidAppCertificateSha256Fingerprint.toString))
			given Conversion[check, Future[Schema.CheckResponse]] = (fun: check) => fun.apply()
		}
	}
	object statements {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListResponse])
		}
		object list {
			def apply(sourceWebSite: String, sourceAndroidAppPackageName: String, sourceAndroidAppCertificateSha256Fingerprint: String, relation: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/statements:list").addQueryStringParameters("source.web.site" -> sourceWebSite.toString, "source.androidApp.packageName" -> sourceAndroidAppPackageName.toString, "source.androidApp.certificate.sha256Fingerprint" -> sourceAndroidAppCertificateSha256Fingerprint.toString, "relation" -> relation.toString))
			given Conversion[list, Future[Schema.ListResponse]] = (fun: list) => fun.apply()
		}
	}
}
