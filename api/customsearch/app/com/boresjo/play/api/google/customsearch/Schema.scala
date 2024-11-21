package com.boresjo.play.api.google.customsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Search {
		case class UrlItem(
		  /** The MIME type of the OpenSearch URL template for the Custom Search JSON API. */
			`type`: Option[String] = None,
		  /** The actual [OpenSearch template](http://www.opensearch.org/specifications/opensearch/1.1#opensearch_url_template_syntax) for this API. */
			template: Option[String] = None
		)
		
		object QueriesItem {
			case class NextPageItem(
			  /** The page number of this set of results, where the page length is set by the `count` property. */
				startPage: Option[Int] = None,
			  /** Restricts results to URLs based on date. Supported values include: &#42; `d[number]`: requests results from the specified number of past days. &#42; `w[number]`: requests results from the specified number of past weeks. &#42; `m[number]`: requests results from the specified number of past months. &#42; `y[number]`: requests results from the specified number of past years. */
				dateRestrict: Option[String] = None,
			  /** Restricts results to images of a specified size. Supported values are: &#42; `icon` (small) &#42; `small | medium | large | xlarge` (medium) &#42; `xxlarge` (large) &#42; `huge` (extra-large) */
				imgSize: Option[String] = None,
			  /** Provides additional search terms to check for in a document, where each document in the search results must contain at least one of the additional search terms. You can also use the [Boolean OR](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) query term for this type of query. */
				orTerms: Option[String] = None,
			  /** The index of the current set of search results into the total set of results, where the index of the first result is 1. */
				startIndex: Option[Int] = None,
			  /** Identifies a phrase that all documents in the search results must contain. */
				exactTerms: Option[String] = None,
			  /** Specifies the [SafeSearch level](https://developers.google.com/custom-search/docs/json_api_reference#safeSearchLevels) used for filtering out adult results. This is a custom property not defined in the OpenSearch spec. Valid parameter values are: &#42; `"off"`: Disable SafeSearch &#42; `"active"`: Enable SafeSearch */
				safe: Option[String] = None,
			  /** Specifies that results should be sorted according to the specified expression. For example, sort by date. */
				sort: Option[String] = None,
			  /** A description of the query. */
				title: Option[String] = None,
			  /** Restricts results to URLs from a specified site. */
				siteSearch: Option[String] = None,
			  /** Enables or disables the [Simplified and Traditional Chinese Search](https://developers.google.com/custom-search/docs/json_api_reference#chineseSearch) feature. Supported values are: &#42; `0`: enabled (default) &#42; `1`: disabled */
				disableCnTwTranslation: Option[String] = None,
			  /** Specifies the ending value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				highRange: Option[String] = None,
			  /** Specifies the Google domain (for example, google.com, google.de, or google.fr) to which the search should be limited. */
				googleHost: Option[String] = None,
			  /** Restricts results to images of a specified type. Supported values are: &#42; `clipart` (Clip art) &#42; `face` (Face) &#42; `lineart` (Line drawing) &#42; `photo` (Photo) &#42; `animated` (Animated) &#42; `stock` (Stock) */
				imgType: Option[String] = None,
			  /** Identifies a word or phrase that should not appear in any documents in the search results. */
				excludeTerms: Option[String] = None,
			  /** Specifies the interface language (host language) of your user interface. Explicitly setting this parameter improves the performance and the quality of your search results. See the [Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#wsInterfaceLanguages) section of [Internationalizing Queries and Results Presentation](https://developers.google.com/custom-search/docs/json_api_reference#wsInternationalizing) for more information, and [Supported Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#interfaceLanguages) for a list of supported languages. */
				hl: Option[String] = None,
			  /** Number of search results returned in this set. */
				count: Option[Int] = None,
			  /** Restricts results to images with a specific dominant color. Supported values are: &#42; `red` &#42; `orange` &#42; `yellow` &#42; `green` &#42; `teal` &#42; `blue` &#42; `purple` &#42; `pink` &#42; `white` &#42; `gray` &#42; `black` &#42; `brown` */
				imgDominantColor: Option[String] = None,
			  /** Specifies that all search results should be pages that are related to the specified URL. The parameter value should be a URL. */
				relatedSite: Option[String] = None,
			  /** Allowed values are `web` or `image`. If unspecified, results are limited to webpages. */
				searchType: Option[String] = None,
			  /** The identifier of an engine created using the Programmable Search Engine [Control Panel](https://programmablesearchengine.google.com/). This is a custom property not defined in the OpenSearch spec. This parameter is &#42;&#42;required&#42;&#42;. */
				cx: Option[String] = None,
			  /** Specifies that all results should contain a link to a specific URL. */
				linkSite: Option[String] = None,
			  /** The language of the search results. */
				language: Option[String] = None,
			  /** Boosts search results whose country of origin matches the parameter value. See [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) for a list of valid values. Specifying a `gl` parameter value in WebSearch requests should improve the relevance of results. This is particularly true for international customers and, even more specifically, for customers in English-speaking countries other than the United States. */
				gl: Option[String] = None,
			  /** The character encoding supported for search results. */
				outputEncoding: Option[String] = None,
			  /** Restricts results to images of a specified color type. Supported values are: &#42; `mono` (black and white) &#42; `gray` (grayscale) &#42; `color` (color) */
				imgColorType: Option[String] = None,
			  /** Activates or deactivates the automatic filtering of Google search results. See [Automatic Filtering](https://developers.google.com/custom-search/docs/json_api_reference#automaticFiltering) for more information about Google's search results filters. Valid values for this parameter are: &#42; `0`: Disabled &#42; `1`: Enabled (default) &#42;&#42;Note&#42;&#42;: By default, Google applies filtering to all search results to improve the quality of those results. */
				filter: Option[String] = None,
			  /** The character encoding supported for search requests. */
				inputEncoding: Option[String] = None,
			  /** Specifies the starting value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				lowRange: Option[String] = None,
			  /** The search terms entered by the user. */
				searchTerms: Option[String] = None,
			  /** Appends the specified query terms to the query, as if they were combined with a logical `AND` operator. */
				hq: Option[String] = None,
			  /** Restricts search results to documents originating in a particular country. You may use [Boolean operators](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) in the `cr` parameter's value. Google WebSearch determines the country of a document by analyzing the following: &#42; The top-level domain (TLD) of the document's URL. &#42; The geographic location of the web server's IP address. See [Country (cr) Parameter Values](https://developers.google.com/custom-search/docs/json_api_reference#countryCollections) for a list of valid values for this parameter. */
				cr: Option[String] = None,
			  /** Specifies whether to include or exclude results from the site named in the `sitesearch` parameter. Supported values are: &#42; `i`: include content from site &#42; `e`: exclude content from site */
				siteSearchFilter: Option[String] = None,
			  /** Estimated number of total search results. May not be accurate. */
				totalResults: Option[String] = None,
			  /** Restricts results to files of a specified extension. Filetypes supported by Google include: &#42; Adobe Portable Document Format (`pdf`) &#42; Adobe PostScript (`ps`) &#42; Lotus 1-2-3 (`wk1`, `wk2`, `wk3`, `wk4`, `wk5`, `wki`, `wks`, `wku`) &#42; Lotus WordPro (`lwp`) &#42; Macwrite (`mw`) &#42; Microsoft Excel (`xls`) &#42; Microsoft PowerPoint (`ppt`) &#42; Microsoft Word (`doc`) &#42; Microsoft Works (`wks`, `wps`, `wdb`) &#42; Microsoft Write (`wri`) &#42; Rich Text Format (`rtf`) &#42; Shockwave Flash (`swf`) &#42; Text (`ans`, `txt`). Additional filetypes may be added in the future. An up-to-date list can always be found in Google's [file type FAQ](https://support.google.com/webmasters/answer/35287). */
				fileType: Option[String] = None,
			  /** Filters based on licensing. Supported values include: &#42; `cc_publicdomain` &#42; `cc_attribute` &#42; `cc_sharealike` &#42; `cc_noncommercial` &#42; `cc_nonderived` */
				rights: Option[String] = None
			)
			
			case class PreviousPageItem(
			  /** The character encoding supported for search results. */
				outputEncoding: Option[String] = None,
			  /** Restricts results to files of a specified extension. Filetypes supported by Google include: &#42; Adobe Portable Document Format (`pdf`) &#42; Adobe PostScript (`ps`) &#42; Lotus 1-2-3 (`wk1`, `wk2`, `wk3`, `wk4`, `wk5`, `wki`, `wks`, `wku`) &#42; Lotus WordPro (`lwp`) &#42; Macwrite (`mw`) &#42; Microsoft Excel (`xls`) &#42; Microsoft PowerPoint (`ppt`) &#42; Microsoft Word (`doc`) &#42; Microsoft Works (`wks`, `wps`, `wdb`) &#42; Microsoft Write (`wri`) &#42; Rich Text Format (`rtf`) &#42; Shockwave Flash (`swf`) &#42; Text (`ans`, `txt`). Additional filetypes may be added in the future. An up-to-date list can always be found in Google's [file type FAQ](https://support.google.com/webmasters/answer/35287). */
				fileType: Option[String] = None,
			  /** The identifier of an engine created using the Programmable Search Engine [Control Panel](https://programmablesearchengine.google.com/). This is a custom property not defined in the OpenSearch spec. This parameter is &#42;&#42;required&#42;&#42;. */
				cx: Option[String] = None,
			  /** Restricts results to images with a specific dominant color. Supported values are: &#42; `red` &#42; `orange` &#42; `yellow` &#42; `green` &#42; `teal` &#42; `blue` &#42; `purple` &#42; `pink` &#42; `white` &#42; `gray` &#42; `black` &#42; `brown` */
				imgDominantColor: Option[String] = None,
			  /** Estimated number of total search results. May not be accurate. */
				totalResults: Option[String] = None,
			  /** Restricts results to URLs from a specified site. */
				siteSearch: Option[String] = None,
			  /** Filters based on licensing. Supported values include: &#42; `cc_publicdomain` &#42; `cc_attribute` &#42; `cc_sharealike` &#42; `cc_noncommercial` &#42; `cc_nonderived` */
				rights: Option[String] = None,
			  /** Specifies the interface language (host language) of your user interface. Explicitly setting this parameter improves the performance and the quality of your search results. See the [Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#wsInterfaceLanguages) section of [Internationalizing Queries and Results Presentation](https://developers.google.com/custom-search/docs/json_api_reference#wsInternationalizing) for more information, and [Supported Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#interfaceLanguages) for a list of supported languages. */
				hl: Option[String] = None,
			  /** Enables or disables the [Simplified and Traditional Chinese Search](https://developers.google.com/custom-search/docs/json_api_reference#chineseSearch) feature. Supported values are: &#42; `0`: enabled (default) &#42; `1`: disabled */
				disableCnTwTranslation: Option[String] = None,
			  /** Specifies the [SafeSearch level](https://developers.google.com/custom-search/docs/json_api_reference#safeSearchLevels) used for filtering out adult results. This is a custom property not defined in the OpenSearch spec. Valid parameter values are: &#42; `"off"`: Disable SafeSearch &#42; `"active"`: Enable SafeSearch */
				safe: Option[String] = None,
			  /** Appends the specified query terms to the query, as if they were combined with a logical `AND` operator. */
				hq: Option[String] = None,
			  /** The page number of this set of results, where the page length is set by the `count` property. */
				startPage: Option[Int] = None,
			  /** Specifies the ending value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				highRange: Option[String] = None,
			  /** Restricts search results to documents originating in a particular country. You may use [Boolean operators](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) in the `cr` parameter's value. Google WebSearch determines the country of a document by analyzing the following: &#42; The top-level domain (TLD) of the document's URL. &#42; The geographic location of the web server's IP address. See [Country (cr) Parameter Values](https://developers.google.com/custom-search/docs/json_api_reference#countryCollections) for a list of valid values for this parameter. */
				cr: Option[String] = None,
			  /** Boosts search results whose country of origin matches the parameter value. See [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) for a list of valid values. Specifying a `gl` parameter value in WebSearch requests should improve the relevance of results. This is particularly true for international customers and, even more specifically, for customers in English-speaking countries other than the United States. */
				gl: Option[String] = None,
			  /** Restricts results to images of a specified type. Supported values are: &#42; `clipart` (Clip art) &#42; `face` (Face) &#42; `lineart` (Line drawing) &#42; `photo` (Photo) &#42; `animated` (Animated) &#42; `stock` (Stock) */
				imgType: Option[String] = None,
			  /** Specifies the Google domain (for example, google.com, google.de, or google.fr) to which the search should be limited. */
				googleHost: Option[String] = None,
			  /** Allowed values are `web` or `image`. If unspecified, results are limited to webpages. */
				searchType: Option[String] = None,
			  /** Identifies a word or phrase that should not appear in any documents in the search results. */
				excludeTerms: Option[String] = None,
			  /** A description of the query. */
				title: Option[String] = None,
			  /** Specifies that all search results should be pages that are related to the specified URL. The parameter value should be a URL. */
				relatedSite: Option[String] = None,
			  /** The language of the search results. */
				language: Option[String] = None,
			  /** Provides additional search terms to check for in a document, where each document in the search results must contain at least one of the additional search terms. You can also use the [Boolean OR](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) query term for this type of query. */
				orTerms: Option[String] = None,
			  /** Activates or deactivates the automatic filtering of Google search results. See [Automatic Filtering](https://developers.google.com/custom-search/docs/json_api_reference#automaticFiltering) for more information about Google's search results filters. Valid values for this parameter are: &#42; `0`: Disabled &#42; `1`: Enabled (default) &#42;&#42;Note&#42;&#42;: By default, Google applies filtering to all search results to improve the quality of those results. */
				filter: Option[String] = None,
			  /** Restricts results to images of a specified size. Supported values are: &#42; `icon` (small) &#42; `small | medium | large | xlarge` (medium) &#42; `xxlarge` (large) &#42; `huge` (extra-large) */
				imgSize: Option[String] = None,
			  /** Number of search results returned in this set. */
				count: Option[Int] = None,
			  /** Restricts results to URLs based on date. Supported values include: &#42; `d[number]`: requests results from the specified number of past days. &#42; `w[number]`: requests results from the specified number of past weeks. &#42; `m[number]`: requests results from the specified number of past months. &#42; `y[number]`: requests results from the specified number of past years. */
				dateRestrict: Option[String] = None,
			  /** Specifies the starting value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				lowRange: Option[String] = None,
			  /** Specifies that results should be sorted according to the specified expression. For example, sort by date. */
				sort: Option[String] = None,
			  /** The index of the current set of search results into the total set of results, where the index of the first result is 1. */
				startIndex: Option[Int] = None,
			  /** Identifies a phrase that all documents in the search results must contain. */
				exactTerms: Option[String] = None,
			  /** Specifies that all results should contain a link to a specific URL. */
				linkSite: Option[String] = None,
			  /** The search terms entered by the user. */
				searchTerms: Option[String] = None,
			  /** The character encoding supported for search requests. */
				inputEncoding: Option[String] = None,
			  /** Restricts results to images of a specified color type. Supported values are: &#42; `mono` (black and white) &#42; `gray` (grayscale) &#42; `color` (color) */
				imgColorType: Option[String] = None,
			  /** Specifies whether to include or exclude results from the site named in the `sitesearch` parameter. Supported values are: &#42; `i`: include content from site &#42; `e`: exclude content from site */
				siteSearchFilter: Option[String] = None
			)
			
			case class RequestItem(
			  /** Provides additional search terms to check for in a document, where each document in the search results must contain at least one of the additional search terms. You can also use the [Boolean OR](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) query term for this type of query. */
				orTerms: Option[String] = None,
			  /** Specifies the ending value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				highRange: Option[String] = None,
			  /** The identifier of an engine created using the Programmable Search Engine [Control Panel](https://programmablesearchengine.google.com/). This is a custom property not defined in the OpenSearch spec. This parameter is &#42;&#42;required&#42;&#42;. */
				cx: Option[String] = None,
			  /** Allowed values are `web` or `image`. If unspecified, results are limited to webpages. */
				searchType: Option[String] = None,
			  /** Restricts results to images of a specified color type. Supported values are: &#42; `mono` (black and white) &#42; `gray` (grayscale) &#42; `color` (color) */
				imgColorType: Option[String] = None,
			  /** Number of search results returned in this set. */
				count: Option[Int] = None,
			  /** Specifies the interface language (host language) of your user interface. Explicitly setting this parameter improves the performance and the quality of your search results. See the [Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#wsInterfaceLanguages) section of [Internationalizing Queries and Results Presentation](https://developers.google.com/custom-search/docs/json_api_reference#wsInternationalizing) for more information, and [Supported Interface Languages](https://developers.google.com/custom-search/docs/json_api_reference#interfaceLanguages) for a list of supported languages. */
				hl: Option[String] = None,
			  /** Specifies the [SafeSearch level](https://developers.google.com/custom-search/docs/json_api_reference#safeSearchLevels) used for filtering out adult results. This is a custom property not defined in the OpenSearch spec. Valid parameter values are: &#42; `"off"`: Disable SafeSearch &#42; `"active"`: Enable SafeSearch */
				safe: Option[String] = None,
			  /** Boosts search results whose country of origin matches the parameter value. See [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) for a list of valid values. Specifying a `gl` parameter value in WebSearch requests should improve the relevance of results. This is particularly true for international customers and, even more specifically, for customers in English-speaking countries other than the United States. */
				gl: Option[String] = None,
			  /** The language of the search results. */
				language: Option[String] = None,
			  /** Specifies that all results should contain a link to a specific URL. */
				linkSite: Option[String] = None,
			  /** Estimated number of total search results. May not be accurate. */
				totalResults: Option[String] = None,
			  /** Restricts results to files of a specified extension. Filetypes supported by Google include: &#42; Adobe Portable Document Format (`pdf`) &#42; Adobe PostScript (`ps`) &#42; Lotus 1-2-3 (`wk1`, `wk2`, `wk3`, `wk4`, `wk5`, `wki`, `wks`, `wku`) &#42; Lotus WordPro (`lwp`) &#42; Macwrite (`mw`) &#42; Microsoft Excel (`xls`) &#42; Microsoft PowerPoint (`ppt`) &#42; Microsoft Word (`doc`) &#42; Microsoft Works (`wks`, `wps`, `wdb`) &#42; Microsoft Write (`wri`) &#42; Rich Text Format (`rtf`) &#42; Shockwave Flash (`swf`) &#42; Text (`ans`, `txt`). Additional filetypes may be added in the future. An up-to-date list can always be found in Google's [file type FAQ](https://support.google.com/webmasters/answer/35287). */
				fileType: Option[String] = None,
			  /** A description of the query. */
				title: Option[String] = None,
			  /** Restricts results to images with a specific dominant color. Supported values are: &#42; `red` &#42; `orange` &#42; `yellow` &#42; `green` &#42; `teal` &#42; `blue` &#42; `purple` &#42; `pink` &#42; `white` &#42; `gray` &#42; `black` &#42; `brown` */
				imgDominantColor: Option[String] = None,
			  /** Restricts results to URLs based on date. Supported values include: &#42; `d[number]`: requests results from the specified number of past days. &#42; `w[number]`: requests results from the specified number of past weeks. &#42; `m[number]`: requests results from the specified number of past months. &#42; `y[number]`: requests results from the specified number of past years. */
				dateRestrict: Option[String] = None,
			  /** The page number of this set of results, where the page length is set by the `count` property. */
				startPage: Option[Int] = None,
			  /** Activates or deactivates the automatic filtering of Google search results. See [Automatic Filtering](https://developers.google.com/custom-search/docs/json_api_reference#automaticFiltering) for more information about Google's search results filters. Valid values for this parameter are: &#42; `0`: Disabled &#42; `1`: Enabled (default) &#42;&#42;Note&#42;&#42;: By default, Google applies filtering to all search results to improve the quality of those results. */
				filter: Option[String] = None,
			  /** Filters based on licensing. Supported values include: &#42; `cc_publicdomain` &#42; `cc_attribute` &#42; `cc_sharealike` &#42; `cc_noncommercial` &#42; `cc_nonderived` */
				rights: Option[String] = None,
			  /** Specifies whether to include or exclude results from the site named in the `sitesearch` parameter. Supported values are: &#42; `i`: include content from site &#42; `e`: exclude content from site */
				siteSearchFilter: Option[String] = None,
			  /** Enables or disables the [Simplified and Traditional Chinese Search](https://developers.google.com/custom-search/docs/json_api_reference#chineseSearch) feature. Supported values are: &#42; `0`: enabled (default) &#42; `1`: disabled */
				disableCnTwTranslation: Option[String] = None,
			  /** Restricts results to images of a specified type. Supported values are: &#42; `clipart` (Clip art) &#42; `face` (Face) &#42; `lineart` (Line drawing) &#42; `photo` (Photo) &#42; `animated` (Animated) &#42; `stock` (Stock) */
				imgType: Option[String] = None,
			  /** The character encoding supported for search results. */
				outputEncoding: Option[String] = None,
			  /** Specifies the starting value for a search range. Use `cse:lowRange` and `cse:highrange` to append an inclusive search range of `lowRange...highRange` to the query. */
				lowRange: Option[String] = None,
			  /** The search terms entered by the user. */
				searchTerms: Option[String] = None,
			  /** Specifies that all search results should be pages that are related to the specified URL. The parameter value should be a URL. */
				relatedSite: Option[String] = None,
			  /** Identifies a word or phrase that should not appear in any documents in the search results. */
				excludeTerms: Option[String] = None,
			  /** Restricts results to URLs from a specified site. */
				siteSearch: Option[String] = None,
			  /** Specifies the Google domain (for example, google.com, google.de, or google.fr) to which the search should be limited. */
				googleHost: Option[String] = None,
			  /** Restricts search results to documents originating in a particular country. You may use [Boolean operators](https://developers.google.com/custom-search/docs/json_api_reference#BooleanOrSearch) in the `cr` parameter's value. Google WebSearch determines the country of a document by analyzing the following: &#42; The top-level domain (TLD) of the document's URL. &#42; The geographic location of the web server's IP address. See [Country (cr) Parameter Values](https://developers.google.com/custom-search/docs/json_api_reference#countryCollections) for a list of valid values for this parameter. */
				cr: Option[String] = None,
			  /** The character encoding supported for search requests. */
				inputEncoding: Option[String] = None,
			  /** Identifies a phrase that all documents in the search results must contain. */
				exactTerms: Option[String] = None,
			  /** Specifies that results should be sorted according to the specified expression. For example, sort by date. */
				sort: Option[String] = None,
			  /** Appends the specified query terms to the query, as if they were combined with a logical `AND` operator. */
				hq: Option[String] = None,
			  /** Restricts results to images of a specified size. Supported values are: &#42; `icon` (small) &#42; `small | medium | large | xlarge` (medium) &#42; `xxlarge` (large) &#42; `huge` (extra-large) */
				imgSize: Option[String] = None,
			  /** The index of the current set of search results into the total set of results, where the index of the first result is 1. */
				startIndex: Option[Int] = None
			)
		}
		case class QueriesItem(
		  /** Metadata representing the next page of results, if applicable. */
			nextPage: Option[List[Schema.Search.QueriesItem.NextPageItem]] = None,
		  /** Metadata representing the previous page of results, if applicable. */
			previousPage: Option[List[Schema.Search.QueriesItem.PreviousPageItem]] = None,
		  /** Metadata representing the current request. */
			request: Option[List[Schema.Search.QueriesItem.RequestItem]] = None
		)
		
		case class SpellingItem(
		  /** The corrected query, formatted in HTML. */
			htmlCorrectedQuery: Option[String] = None,
		  /** The corrected query. */
			correctedQuery: Option[String] = None
		)
		
		case class SearchInformationItem(
		  /** The total number of search results, formatted according to locale style. */
			formattedTotalResults: Option[String] = None,
		  /** The time taken for the server to return search results, formatted according to locale style. */
			formattedSearchTime: Option[String] = None,
		  /** The time taken for the server to return search results. */
			searchTime: Option[BigDecimal] = None,
		  /** The total number of search results returned by the query. */
			totalResults: Option[String] = None
		)
	}
	case class Search(
	  /** The set of [promotions](https://developers.google.com/custom-search/docs/promotions). Present only if the custom search engine's configuration files define any promotions for the given query. */
		promotions: Option[List[Schema.Promotion]] = None,
	  /** Metadata and refinements associated with the given search engine, including: &#42; The name of the search engine that was used for the query. &#42; A set of [facet objects](https://developers.google.com/custom-search/docs/refinements#create) (refinements) you can use for refining a search. */
		context: Option[Map[String, JsValue]] = None,
	  /** OpenSearch template and URL. */
		url: Option[Schema.Search.UrlItem] = None,
	  /** Unique identifier for the type of current object. For this API, it is customsearch#search. */
		kind: Option[String] = None,
	  /** Query metadata for the previous, current, and next pages of results. */
		queries: Option[Schema.Search.QueriesItem] = None,
	  /** The current set of custom search results. */
		items: Option[List[Schema.Result]] = None,
	  /** Spell correction information for a query. */
		spelling: Option[Schema.Search.SpellingItem] = None,
	  /** Metadata about a search operation. */
		searchInformation: Option[Schema.Search.SearchInformationItem] = None
	)
	
	object Result {
		case class LabelsItem(
		  /** The display name of a refinement label. This is the name you should display in your user interface. */
			displayName: Option[String] = None,
		  /** Refinement label and the associated refinement operation. */
			label_with_op: Option[String] = None,
		  /** The name of a refinement label, which you can use to refine searches. Don't display this in your user interface; instead, use displayName. */
			name: Option[String] = None
		)
		
		case class ImageItem(
		  /** The height of the thumbnail image, in pixels. */
			thumbnailHeight: Option[Int] = None,
		  /** A URL pointing to the webpage hosting the image. */
			contextLink: Option[String] = None,
		  /** A URL to the thumbnail image. */
			thumbnailLink: Option[String] = None,
		  /** The width of the image, in pixels. */
			width: Option[Int] = None,
		  /** The size of the image, in bytes. */
			byteSize: Option[Int] = None,
		  /** The width of the thumbnail image, in pixels. */
			thumbnailWidth: Option[Int] = None,
		  /** The height of the image, in pixels. */
			height: Option[Int] = None
		)
	}
	case class Result(
	  /** Contains [PageMap](https://developers.google.com/custom-search/docs/structured_data#pagemaps) information for this search result. */
		pagemap: Option[Map[String, JsValue]] = None,
	  /** The URL displayed after the snippet for each search result. */
		formattedUrl: Option[String] = None,
	  /** An abridged version of this search result’s URL, e.g. www.example.com. */
		displayLink: Option[String] = None,
	  /** Indicates the ID of Google's cached version of the search result. */
		cacheId: Option[String] = None,
	  /** The title of the search result, in HTML. */
		htmlTitle: Option[String] = None,
	  /** The full URL to which the search result is pointing, e.g. http://www.example.com/foo/bar. */
		link: Option[String] = None,
	  /** The file format of the search result. */
		fileFormat: Option[String] = None,
	  /** Encapsulates all information about refinement labels. */
		labels: Option[List[Schema.Result.LabelsItem]] = None,
	  /** A unique identifier for the type of current object. For this API, it is `customsearch#result.` */
		kind: Option[String] = None,
	  /** The MIME type of the search result. */
		mime: Option[String] = None,
	  /** The HTML-formatted URL displayed after the snippet for each search result. */
		htmlFormattedUrl: Option[String] = None,
	  /** The snippet of the search result, in HTML. */
		htmlSnippet: Option[String] = None,
	  /** The snippet of the search result, in plain text. */
		snippet: Option[String] = None,
	  /** Image belonging to a custom search result. */
		image: Option[Schema.Result.ImageItem] = None,
	  /** The title of the search result, in plain text. */
		title: Option[String] = None
	)
	
	object Promotion {
		case class BodyLinesItem(
		  /** The block object's text, if it has text. */
			title: Option[String] = None,
		  /** The URL of the block object's link, if it has one. */
			url: Option[String] = None,
		  /** The block object's text in HTML, if it has text. */
			htmlTitle: Option[String] = None,
		  /** The anchor text of the block object's link, if it has a link. */
			link: Option[String] = None
		)
		
		case class ImageItem(
		  /** Image width in pixels. */
			width: Option[Int] = None,
		  /** URL of the image for this promotion link. */
			source: Option[String] = None,
		  /** Image height in pixels. */
			height: Option[Int] = None
		)
	}
	case class Promotion(
	  /** The URL of the promotion. */
		link: Option[String] = None,
	  /** An abridged version of this search's result URL, e.g. www.example.com. */
		displayLink: Option[String] = None,
	  /** An array of block objects for this promotion. */
		bodyLines: Option[List[Schema.Promotion.BodyLinesItem]] = None,
	  /** Image belonging to a promotion. */
		image: Option[Schema.Promotion.ImageItem] = None,
	  /** The title of the promotion. */
		title: Option[String] = None,
	  /** The title of the promotion, in HTML. */
		htmlTitle: Option[String] = None
	)
}
