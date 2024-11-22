package com.boresjo.play.api.google.localservices

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://localservices.googleapis.com/"

	object detailedLeadReports {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsHomeservicesLocalservicesV1SearchDetailedLeadReportsResponse]) {
			/** The `next_page_token` value returned from a previous request to SearchDetailedLeadReports that indicates where listing should continue. Optional. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** The maximum number of accounts to return. If the page size is unset, page size will default to 1000. Maximum page_size is 10000. Optional.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsHomeservicesLocalservicesV1SearchDetailedLeadReportsResponse])
		}
		object search {
			def apply(endDateMonth: Int, endDateDay: Int, startDateDay: Int, query: String, endDateYear: Int, startDateYear: Int, startDateMonth: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/detailedLeadReports:search").addQueryStringParameters("endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "startDate.day" -> startDateDay.toString, "query" -> query.toString, "endDate.year" -> endDateYear.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString))
			given Conversion[search, Future[Schema.GoogleAdsHomeservicesLocalservicesV1SearchDetailedLeadReportsResponse]] = (fun: search) => fun.apply()
		}
	}
	object accountReports {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsHomeservicesLocalservicesV1SearchAccountReportsResponse]) {
			/** The `next_page_token` value returned from a previous request to SearchAccountReports that indicates where listing should continue. Optional. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** The maximum number of accounts to return. If the page size is unset, page size will default to 1000. Maximum page_size is 10000. Optional.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsHomeservicesLocalservicesV1SearchAccountReportsResponse])
		}
		object search {
			def apply(startDateMonth: Int, endDateDay: Int, startDateDay: Int, endDateMonth: Int, endDateYear: Int, query: String, startDateYear: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/accountReports:search").addQueryStringParameters("startDate.month" -> startDateMonth.toString, "endDate.day" -> endDateDay.toString, "startDate.day" -> startDateDay.toString, "endDate.month" -> endDateMonth.toString, "endDate.year" -> endDateYear.toString, "query" -> query.toString, "startDate.year" -> startDateYear.toString))
			given Conversion[search, Future[Schema.GoogleAdsHomeservicesLocalservicesV1SearchAccountReportsResponse]] = (fun: search) => fun.apply()
		}
	}
}
