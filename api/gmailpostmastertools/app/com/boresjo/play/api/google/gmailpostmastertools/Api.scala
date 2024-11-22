package com.boresjo.play.api.google.gmailpostmastertools

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
		"""https://www.googleapis.com/auth/postmaster.readonly""" /* See email traffic metrics for the domains you have registered in Gmail Postmaster Tools */
	)

	private val BASE_URL = "https://gmailpostmastertools.googleapis.com/"

	object domains {
		/** Lists the domains that have been registered by the client. The order of domains in the response is unspecified and non-deterministic. Newly created domains will not necessarily be added to the end of this list. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDomainsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/postmaster.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDomainsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/domains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListDomainsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets a specific domain registered by the client. Returns NOT_FOUND if the domain does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Domain]) {
			val scopes = Seq("""https://www.googleapis.com/auth/postmaster.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Domain])
		}
		object get {
			def apply(domainsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/domains/${domainsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Domain]] = (fun: get) => fun.apply()
		}
		object trafficStats {
			/** List traffic statistics for all available days. Returns PERMISSION_DENIED if user does not have permission to access TrafficStats for the domain. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTrafficStatsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/postmaster.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTrafficStatsResponse])
			}
			object list {
				def apply(domainsId :PlayApi, pageSize: Int, endDateDay: Int, startDateYear: Int, endDateYear: Int, pageToken: String, parent: String, endDateMonth: Int, startDateMonth: Int, startDateDay: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/domains/${domainsId}/trafficStats").addQueryStringParameters("pageSize" -> pageSize.toString, "endDate.day" -> endDateDay.toString, "startDate.year" -> startDateYear.toString, "endDate.year" -> endDateYear.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "endDate.month" -> endDateMonth.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString))
				given Conversion[list, Future[Schema.ListTrafficStatsResponse]] = (fun: list) => fun.apply()
			}
			/** Get traffic statistics for a domain on a specific date. Returns PERMISSION_DENIED if user does not have permission to access TrafficStats for the domain. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TrafficStats]) {
				val scopes = Seq("""https://www.googleapis.com/auth/postmaster.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TrafficStats])
			}
			object get {
				def apply(domainsId :PlayApi, trafficStatsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/domains/${domainsId}/trafficStats/${trafficStatsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.TrafficStats]] = (fun: get) => fun.apply()
			}
		}
	}
}
