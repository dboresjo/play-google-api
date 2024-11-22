package com.boresjo.play.api.google.customsearch

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

	private val BASE_URL = "https://customsearch.googleapis.com/"

	object cse {
		/** Returns metadata about the search performed, metadata about the engine used for the search, and the search results. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Search]) {
			val scopes = Seq()
			/** Optional. Enables routing of Programmable Search Engine requests to an alternate search handler. */
			def withEnableAlternateSearchHandler(enableAlternateSearchHandler: Boolean) = new list(req.addQueryStringParameters("enableAlternateSearchHandler" -> enableAlternateSearchHandler.toString))
			/** Optional. Maximum length of snippet text, in characters, to be returned with results. Note: this feature is limited to specific engines. &#42; Valid values are integers between 161 and 1000, inclusive.<br>Format: int32 */
			def withSnippetLength(snippetLength: Int) = new list(req.addQueryStringParameters("snippetLength" -> snippetLength.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Search])
		}
		object list {
			def apply(sort: String, rights: String, dateRestrict: String, imgDominantColor: String, fileType: String, searchType: String, hl: String, linkSite: String, relatedSite: String, q: String, excludeTerms: String, highRange: String, imgSize: String, hq: String, safe: String, num: Int, imgColorType: String, cr: String, start: Int, imgType: String, c2coff: String, siteSearchFilter: String, siteSearch: String, googlehost: String, lowRange: String, gl: String, cx: String, filter: String, orTerms: String, exactTerms: String, lr: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"customsearch/v1").addQueryStringParameters("sort" -> sort.toString, "rights" -> rights.toString, "dateRestrict" -> dateRestrict.toString, "imgDominantColor" -> imgDominantColor.toString, "fileType" -> fileType.toString, "searchType" -> searchType.toString, "hl" -> hl.toString, "linkSite" -> linkSite.toString, "relatedSite" -> relatedSite.toString, "q" -> q.toString, "excludeTerms" -> excludeTerms.toString, "highRange" -> highRange.toString, "imgSize" -> imgSize.toString, "hq" -> hq.toString, "safe" -> safe.toString, "num" -> num.toString, "imgColorType" -> imgColorType.toString, "cr" -> cr.toString, "start" -> start.toString, "imgType" -> imgType.toString, "c2coff" -> c2coff.toString, "siteSearchFilter" -> siteSearchFilter.toString, "siteSearch" -> siteSearch.toString, "googlehost" -> googlehost.toString, "lowRange" -> lowRange.toString, "gl" -> gl.toString, "cx" -> cx.toString, "filter" -> filter.toString, "orTerms" -> orTerms.toString, "exactTerms" -> exactTerms.toString, "lr" -> lr.toString))
			given Conversion[list, Future[Schema.Search]] = (fun: list) => fun.apply()
		}
		object siterestrict {
			/** Returns metadata about the search performed, metadata about the engine used for the search, and the search results. Uses a small set of url patterns. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Search]) {
				val scopes = Seq()
				/** Optional. Enables routing of Programmable Search Engine requests to an alternate search handler. */
				def withEnableAlternateSearchHandler(enableAlternateSearchHandler: Boolean) = new list(req.addQueryStringParameters("enableAlternateSearchHandler" -> enableAlternateSearchHandler.toString))
				/** Optional. Maximum length of snippet text, in characters, to be returned with results. Note: this feature is limited to specific engines. &#42; Valid values are integers between 161 and 1000, inclusive.<br>Format: int32 */
				def withSnippetLength(snippetLength: Int) = new list(req.addQueryStringParameters("snippetLength" -> snippetLength.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Search])
			}
			object list {
				def apply(relatedSite: String, num: Int, exactTerms: String, imgColorType: String, cx: String, fileType: String, gl: String, q: String, lr: String, orTerms: String, start: Int, imgType: String, hl: String, lowRange: String, googlehost: String, rights: String, siteSearch: String, searchType: String, filter: String, linkSite: String, excludeTerms: String, highRange: String, hq: String, dateRestrict: String, sort: String, siteSearchFilter: String, imgSize: String, imgDominantColor: String, cr: String, safe: String, c2coff: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"customsearch/v1/siterestrict").addQueryStringParameters("relatedSite" -> relatedSite.toString, "num" -> num.toString, "exactTerms" -> exactTerms.toString, "imgColorType" -> imgColorType.toString, "cx" -> cx.toString, "fileType" -> fileType.toString, "gl" -> gl.toString, "q" -> q.toString, "lr" -> lr.toString, "orTerms" -> orTerms.toString, "start" -> start.toString, "imgType" -> imgType.toString, "hl" -> hl.toString, "lowRange" -> lowRange.toString, "googlehost" -> googlehost.toString, "rights" -> rights.toString, "siteSearch" -> siteSearch.toString, "searchType" -> searchType.toString, "filter" -> filter.toString, "linkSite" -> linkSite.toString, "excludeTerms" -> excludeTerms.toString, "highRange" -> highRange.toString, "hq" -> hq.toString, "dateRestrict" -> dateRestrict.toString, "sort" -> sort.toString, "siteSearchFilter" -> siteSearchFilter.toString, "imgSize" -> imgSize.toString, "imgDominantColor" -> imgDominantColor.toString, "cr" -> cr.toString, "safe" -> safe.toString, "c2coff" -> c2coff.toString))
				given Conversion[list, Future[Schema.Search]] = (fun: list) => fun.apply()
			}
		}
	}
}
