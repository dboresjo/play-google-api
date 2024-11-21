package com.boresjo.play.api.google.customsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSearch: Format[Schema.Search] = Json.format[Schema.Search]
	given fmtPromotion: Format[Schema.Promotion] = Json.format[Schema.Promotion]
	given fmtSearchUrlItem: Format[Schema.Search.UrlItem] = Json.format[Schema.Search.UrlItem]
	given fmtSearchQueriesItem: Format[Schema.Search.QueriesItem] = Json.format[Schema.Search.QueriesItem]
	given fmtSearchQueriesItemNextPageItem: Format[Schema.Search.QueriesItem.NextPageItem] = Json.format[Schema.Search.QueriesItem.NextPageItem]
	given fmtSearchQueriesItemPreviousPageItem: Format[Schema.Search.QueriesItem.PreviousPageItem] = Json.format[Schema.Search.QueriesItem.PreviousPageItem]
	given fmtSearchQueriesItemRequestItem: Format[Schema.Search.QueriesItem.RequestItem] = Json.format[Schema.Search.QueriesItem.RequestItem]
	given fmtResult: Format[Schema.Result] = Json.format[Schema.Result]
	given fmtSearchSpellingItem: Format[Schema.Search.SpellingItem] = Json.format[Schema.Search.SpellingItem]
	given fmtSearchSearchInformationItem: Format[Schema.Search.SearchInformationItem] = Json.format[Schema.Search.SearchInformationItem]
	given fmtResultLabelsItem: Format[Schema.Result.LabelsItem] = Json.format[Schema.Result.LabelsItem]
	given fmtResultImageItem: Format[Schema.Result.ImageItem] = Json.format[Schema.Result.ImageItem]
	given fmtPromotionBodyLinesItem: Format[Schema.Promotion.BodyLinesItem] = Json.format[Schema.Promotion.BodyLinesItem]
	given fmtPromotionImageItem: Format[Schema.Promotion.ImageItem] = Json.format[Schema.Promotion.ImageItem]
}
