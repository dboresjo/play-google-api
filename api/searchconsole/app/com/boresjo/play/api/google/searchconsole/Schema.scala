package com.boresjo.play.api.google.searchconsole

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object RichResultsInspectionResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, PASS, PARTIAL, FAIL, NEUTRAL }
	}
	case class RichResultsInspectionResult(
	  /** A list of zero or more rich results detected on this page. Rich results that cannot even be parsed due to syntactic issues will not be listed here. */
		detectedItems: Option[List[Schema.DetectedItems]] = None,
	  /** High-level rich results inspection result for this URL. */
		verdict: Option[Schema.RichResultsInspectionResult.VerdictEnum] = None
	)
	
	case class SitesListResponse(
	  /** Contains permission level information about a Search Console site. For more information, see [Permissions in Search Console](https://support.google.com/webmasters/answer/2451999). */
		siteEntry: Option[List[Schema.WmxSite]] = None
	)
	
	object MobileFriendlyIssue {
		enum RuleEnum extends Enum[RuleEnum] { case MOBILE_FRIENDLY_RULE_UNSPECIFIED, USES_INCOMPATIBLE_PLUGINS, CONFIGURE_VIEWPORT, FIXED_WIDTH_VIEWPORT, SIZE_CONTENT_TO_VIEWPORT, USE_LEGIBLE_FONT_SIZES, TAP_TARGETS_TOO_CLOSE }
	}
	case class MobileFriendlyIssue(
	  /** Rule violated. */
		rule: Option[Schema.MobileFriendlyIssue.RuleEnum] = None
	)
	
	object ApiDimensionFilterGroup {
		enum GroupTypeEnum extends Enum[GroupTypeEnum] { case AND }
	}
	case class ApiDimensionFilterGroup(
		groupType: Option[Schema.ApiDimensionFilterGroup.GroupTypeEnum] = None,
		filters: Option[List[Schema.ApiDimensionFilter]] = None
	)
	
	case class BlockedResource(
	  /** URL of the blocked resource. */
		url: Option[String] = None
	)
	
	object SearchAnalyticsQueryRequest {
		enum AggregationTypeEnum extends Enum[AggregationTypeEnum] { case AUTO, BY_PROPERTY, BY_PAGE, BY_NEWS_SHOWCASE_PANEL }
		enum TypeEnum extends Enum[TypeEnum] { case WEB, IMAGE, VIDEO, NEWS, DISCOVER, GOOGLE_NEWS }
		enum SearchTypeEnum extends Enum[SearchTypeEnum] { case WEB, IMAGE, VIDEO, NEWS, DISCOVER, GOOGLE_NEWS }
		enum DimensionsEnum extends Enum[DimensionsEnum] { case DATE, QUERY, PAGE, COUNTRY, DEVICE, SEARCH_APPEARANCE }
		enum DataStateEnum extends Enum[DataStateEnum] { case DATA_STATE_UNSPECIFIED, FINAL, ALL }
	}
	case class SearchAnalyticsQueryRequest(
	  /** [Optional; Default is 1000] The maximum number of rows to return. Must be a number from 1 to 25,000 (inclusive). */
		rowLimit: Option[Int] = None,
	  /**  [Required] Start date of the requested date range, in YYYY-MM-DD format, in PST time (UTC - 8:00). Must be less than or equal to the end date. This value is included in the range. */
		startDate: Option[String] = None,
	  /** [Optional; Default is \"auto\"] How data is aggregated. If aggregated by property, all data for the same property is aggregated; if aggregated by page, all data is aggregated by canonical URI. If you filter or group by page, choose AUTO; otherwise you can aggregate either by property or by page, depending on how you want your data calculated; see the help documentation to learn how data is calculated differently by site versus by page. &#42;&#42;Note:&#42;&#42; If you group or filter by page, you cannot aggregate by property. If you specify any value other than AUTO, the aggregation type in the result will match the requested type, or if you request an invalid type, you will get an error. The API will never change your aggregation type if the requested type is invalid. */
		aggregationType: Option[Schema.SearchAnalyticsQueryRequest.AggregationTypeEnum] = None,
	  /** [Required] End date of the requested date range, in YYYY-MM-DD format, in PST (UTC - 8:00). Must be greater than or equal to the start date. This value is included in the range. */
		endDate: Option[String] = None,
	  /** Optional. [Optional; Default is \"web\"] Type of report: search type, or either Discover or Gnews. */
		`type`: Option[Schema.SearchAnalyticsQueryRequest.TypeEnum] = None,
	  /** [Optional; Default is 0] Zero-based index of the first row in the response. Must be a non-negative number. */
		startRow: Option[Int] = None,
	  /** [Optional; Default is \"web\"] The search type to filter for. */
		searchType: Option[Schema.SearchAnalyticsQueryRequest.SearchTypeEnum] = None,
	  /** [Optional] Zero or more dimensions to group results by. Dimensions are the group-by values in the Search Analytics page. Dimensions are combined to create a unique row key for each row. Results are grouped in the order that you supply these dimensions. */
		dimensions: Option[List[Schema.SearchAnalyticsQueryRequest.DimensionsEnum]] = None,
	  /** [Optional] Zero or more filters to apply to the dimension grouping values; for example, 'query contains \"buy\"' to see only data where the query string contains the substring \"buy\" (not case-sensitive). You can filter by a dimension without grouping by it. */
		dimensionFilterGroups: Option[List[Schema.ApiDimensionFilterGroup]] = None,
	  /** The data state to be fetched, can be full or all, the latter including full and partial data. */
		dataState: Option[Schema.SearchAnalyticsQueryRequest.DataStateEnum] = None
	)
	
	case class DetectedItems(
	  /** Rich Results type */
		richResultType: Option[String] = None,
	  /** List of Rich Results items. */
		items: Option[List[Schema.Item]] = None
	)
	
	case class InspectUrlIndexRequest(
	  /** Optional. An [IETF BCP-47](https://en.wikipedia.org/wiki/IETF_language_tag) language code representing the requested language for translated issue messages, e.g. "en-US", "or "de-CH". Default value is "en-US". */
		languageCode: Option[String] = None,
	  /** Required. The URL of the property as defined in Search Console. &#42;&#42;Examples:&#42;&#42; `http://www.example.com/` for a URL-prefix property, or `sc-domain:example.com` for a Domain property. */
		siteUrl: Option[String] = None,
	  /** Required. URL to inspect. Must be under the property specified in "site_url". */
		inspectionUrl: Option[String] = None
	)
	
	case class UrlInspectionResult(
	  /** Result of the AMP analysis. Absent if the page is not an AMP page. */
		ampResult: Option[Schema.AmpInspectionResult] = None,
	  /** Result of the index status analysis. */
		indexStatusResult: Option[Schema.IndexStatusInspectionResult] = None,
	  /** Result of the Rich Results analysis. Absent if there are no rich results found. */
		richResultsResult: Option[Schema.RichResultsInspectionResult] = None,
	  /** Result of the Mobile usability analysis. */
		mobileUsabilityResult: Option[Schema.MobileUsabilityInspectionResult] = None,
	  /** Link to Search Console URL inspection. */
		inspectionResultLink: Option[String] = None
	)
	
	object WmxSitemap {
		enum TypeEnum extends Enum[TypeEnum] { case NOT_SITEMAP, URL_LIST, SITEMAP, RSS_FEED, ATOM_FEED, PATTERN_SITEMAP, OCEANFRONT }
	}
	case class WmxSitemap(
	  /** Date & time in which this sitemap was last downloaded. Date format is in RFC 3339 format (yyyy-mm-dd). */
		lastDownloaded: Option[String] = None,
	  /** The url of the sitemap. */
		path: Option[String] = None,
	  /** Date & time in which this sitemap was submitted. Date format is in RFC 3339 format (yyyy-mm-dd). */
		lastSubmitted: Option[String] = None,
	  /** Number of errors in the sitemap. These are issues with the sitemap itself that need to be fixed before it can be processed correctly. */
		errors: Option[String] = None,
	  /** Number of warnings for the sitemap. These are generally non-critical issues with URLs in the sitemaps. */
		warnings: Option[String] = None,
	  /** If true, the sitemap is a collection of sitemaps. */
		isSitemapsIndex: Option[Boolean] = None,
	  /** The various content types in the sitemap. */
		contents: Option[List[Schema.WmxSitemapContent]] = None,
	  /** If true, the sitemap has not been processed. */
		isPending: Option[Boolean] = None,
	  /** The type of the sitemap. For example: `rssFeed`. */
		`type`: Option[Schema.WmxSitemap.TypeEnum] = None
	)
	
	object AmpIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, WARNING, ERROR }
	}
	case class AmpIssue(
	  /** Severity of this issue: WARNING or ERROR. */
		severity: Option[Schema.AmpIssue.SeverityEnum] = None,
	  /** Brief description of this issue. */
		issueMessage: Option[String] = None
	)
	
	object ApiDimensionFilter {
		enum OperatorEnum extends Enum[OperatorEnum] { case EQUALS, NOT_EQUALS, CONTAINS, NOT_CONTAINS, INCLUDING_REGEX, EXCLUDING_REGEX }
		enum DimensionEnum extends Enum[DimensionEnum] { case QUERY, PAGE, COUNTRY, DEVICE, SEARCH_APPEARANCE }
	}
	case class ApiDimensionFilter(
		operator: Option[Schema.ApiDimensionFilter.OperatorEnum] = None,
		expression: Option[String] = None,
		dimension: Option[Schema.ApiDimensionFilter.DimensionEnum] = None
	)
	
	case class InspectUrlIndexResponse(
	  /** URL inspection results. */
		inspectionResult: Option[Schema.UrlInspectionResult] = None
	)
	
	object WmxSitemapContent {
		enum TypeEnum extends Enum[TypeEnum] { case WEB, IMAGE, VIDEO, NEWS, MOBILE, ANDROID_APP, PATTERN, IOS_APP, DATA_FEED_ELEMENT }
	}
	case class WmxSitemapContent(
	  /** The number of URLs in the sitemap (of the content type). */
		submitted: Option[String] = None,
	  /** &#42;Deprecated; do not use.&#42; */
		indexed: Option[String] = None,
	  /** The specific type of content in this sitemap. For example: `web`. */
		`type`: Option[Schema.WmxSitemapContent.TypeEnum] = None
	)
	
	case class ApiDataRow(
		clicks: Option[BigDecimal] = None,
		position: Option[BigDecimal] = None,
		impressions: Option[BigDecimal] = None,
		keys: Option[List[String]] = None,
		ctr: Option[BigDecimal] = None
	)
	
	object RunMobileFriendlyTestResponse {
		enum MobileFriendlinessEnum extends Enum[MobileFriendlinessEnum] { case MOBILE_FRIENDLY_TEST_RESULT_UNSPECIFIED, MOBILE_FRIENDLY, NOT_MOBILE_FRIENDLY }
	}
	case class RunMobileFriendlyTestResponse(
	  /** Information about embedded resources issues. */
		resourceIssues: Option[List[Schema.ResourceIssue]] = None,
	  /** Final state of the test, can be either complete or an error. */
		testStatus: Option[Schema.TestStatus] = None,
	  /** List of mobile-usability issues. */
		mobileFriendlyIssues: Option[List[Schema.MobileFriendlyIssue]] = None,
	  /** Test verdict, whether the page is mobile friendly or not. */
		mobileFriendliness: Option[Schema.RunMobileFriendlyTestResponse.MobileFriendlinessEnum] = None,
	  /** Screenshot of the requested URL. */
		screenshot: Option[Schema.Image] = None
	)
	
	case class Image(
	  /** The mime-type of the image data. */
		mimeType: Option[String] = None,
	  /** Image data in format determined by the mime type. Currently, the format will always be "image/png", but this might change in the future. */
		data: Option[String] = None
	)
	
	case class Item(
	  /** A list of zero or more rich result issues found for this instance. */
		issues: Option[List[Schema.RichResultsIssue]] = None,
	  /** The user-provided name of this item. */
		name: Option[String] = None
	)
	
	object SearchAnalyticsQueryResponse {
		enum ResponseAggregationTypeEnum extends Enum[ResponseAggregationTypeEnum] { case AUTO, BY_PROPERTY, BY_PAGE, BY_NEWS_SHOWCASE_PANEL }
	}
	case class SearchAnalyticsQueryResponse(
	  /** How the results were aggregated. */
		responseAggregationType: Option[Schema.SearchAnalyticsQueryResponse.ResponseAggregationTypeEnum] = None,
	  /** A list of rows grouped by the key values in the order given in the query. */
		rows: Option[List[Schema.ApiDataRow]] = None
	)
	
	object MobileUsabilityInspectionResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, PASS, PARTIAL, FAIL, NEUTRAL }
	}
	case class MobileUsabilityInspectionResult(
	  /** High-level mobile-usability inspection result for this URL. */
		verdict: Option[Schema.MobileUsabilityInspectionResult.VerdictEnum] = None,
	  /** A list of zero or more mobile-usability issues detected for this URL. */
		issues: Option[List[Schema.MobileUsabilityIssue]] = None
	)
	
	object TestStatus {
		enum StatusEnum extends Enum[StatusEnum] { case TEST_STATUS_UNSPECIFIED, COMPLETE, INTERNAL_ERROR, PAGE_UNREACHABLE }
	}
	case class TestStatus(
	  /** Error details if applicable. */
		details: Option[String] = None,
	  /** Status of the test. */
		status: Option[Schema.TestStatus.StatusEnum] = None
	)
	
	case class ResourceIssue(
	  /** Describes a blocked resource issue. */
		blockedResource: Option[Schema.BlockedResource] = None
	)
	
	case class RunMobileFriendlyTestRequest(
	  /** Whether or not screenshot is requested. Default is false. */
		requestScreenshot: Option[Boolean] = None,
	  /** URL for inspection. */
		url: Option[String] = None
	)
	
	object RichResultsIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, WARNING, ERROR }
	}
	case class RichResultsIssue(
	  /** Rich Results issue type. */
		issueMessage: Option[String] = None,
	  /** Severity of this issue: WARNING, or ERROR. Items with an issue of status ERROR cannot appear with rich result features in Google Search results. */
		severity: Option[Schema.RichResultsIssue.SeverityEnum] = None
	)
	
	case class SitemapsListResponse(
	  /** Contains detailed information about a specific URL submitted as a [sitemap](https://support.google.com/webmasters/answer/156184). */
		sitemap: Option[List[Schema.WmxSitemap]] = None
	)
	
	object MobileUsabilityIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, WARNING, ERROR }
		enum IssueTypeEnum extends Enum[IssueTypeEnum] { case MOBILE_USABILITY_ISSUE_TYPE_UNSPECIFIED, USES_INCOMPATIBLE_PLUGINS, CONFIGURE_VIEWPORT, FIXED_WIDTH_VIEWPORT, SIZE_CONTENT_TO_VIEWPORT, USE_LEGIBLE_FONT_SIZES, TAP_TARGETS_TOO_CLOSE }
	}
	case class MobileUsabilityIssue(
	  /** Not returned; reserved for future use. */
		severity: Option[Schema.MobileUsabilityIssue.SeverityEnum] = None,
	  /** Mobile-usability issue type. */
		issueType: Option[Schema.MobileUsabilityIssue.IssueTypeEnum] = None,
	  /** Additional information regarding the issue. */
		message: Option[String] = None
	)
	
	object WmxSite {
		enum PermissionLevelEnum extends Enum[PermissionLevelEnum] { case SITE_PERMISSION_LEVEL_UNSPECIFIED, SITE_OWNER, SITE_FULL_USER, SITE_RESTRICTED_USER, SITE_UNVERIFIED_USER }
	}
	case class WmxSite(
	  /** The user's permission level for the site. */
		permissionLevel: Option[Schema.WmxSite.PermissionLevelEnum] = None,
	  /** The URL of the site. */
		siteUrl: Option[String] = None
	)
	
	object AmpInspectionResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, PASS, PARTIAL, FAIL, NEUTRAL }
		enum PageFetchStateEnum extends Enum[PageFetchStateEnum] { case PAGE_FETCH_STATE_UNSPECIFIED, SUCCESSFUL, SOFT_404, BLOCKED_ROBOTS_TXT, NOT_FOUND, ACCESS_DENIED, SERVER_ERROR, REDIRECT_ERROR, ACCESS_FORBIDDEN, BLOCKED_4XX, INTERNAL_CRAWL_ERROR, INVALID_URL }
		enum IndexingStateEnum extends Enum[IndexingStateEnum] { case AMP_INDEXING_STATE_UNSPECIFIED, AMP_INDEXING_ALLOWED, BLOCKED_DUE_TO_NOINDEX, BLOCKED_DUE_TO_EXPIRED_UNAVAILABLE_AFTER }
		enum RobotsTxtStateEnum extends Enum[RobotsTxtStateEnum] { case ROBOTS_TXT_STATE_UNSPECIFIED, ALLOWED, DISALLOWED }
		enum AmpIndexStatusVerdictEnum extends Enum[AmpIndexStatusVerdictEnum] { case VERDICT_UNSPECIFIED, PASS, PARTIAL, FAIL, NEUTRAL }
	}
	case class AmpInspectionResult(
	  /** Last time this AMP version was crawled by Google. Absent if the URL was never crawled successfully. */
		lastCrawlTime: Option[String] = None,
	  /** A list of zero or more AMP issues found for the inspected URL. */
		issues: Option[List[Schema.AmpIssue]] = None,
	  /** URL of the AMP that was inspected. If the submitted URL is a desktop page that refers to an AMP version, the AMP version will be inspected. */
		ampUrl: Option[String] = None,
	  /** The status of the most severe error on the page. If a page has both warnings and errors, the page status is error. Error status means the page cannot be shown in Search results. */
		verdict: Option[Schema.AmpInspectionResult.VerdictEnum] = None,
	  /** Whether or not Google could fetch the AMP. */
		pageFetchState: Option[Schema.AmpInspectionResult.PageFetchStateEnum] = None,
	  /** Whether or not the page blocks indexing through a noindex rule. */
		indexingState: Option[Schema.AmpInspectionResult.IndexingStateEnum] = None,
	  /** Whether or not the page is blocked to Google by a robots.txt rule. */
		robotsTxtState: Option[Schema.AmpInspectionResult.RobotsTxtStateEnum] = None,
	  /** Index status of the AMP URL. */
		ampIndexStatusVerdict: Option[Schema.AmpInspectionResult.AmpIndexStatusVerdictEnum] = None
	)
	
	object IndexStatusInspectionResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, PASS, PARTIAL, FAIL, NEUTRAL }
		enum IndexingStateEnum extends Enum[IndexingStateEnum] { case INDEXING_STATE_UNSPECIFIED, INDEXING_ALLOWED, BLOCKED_BY_META_TAG, BLOCKED_BY_HTTP_HEADER, BLOCKED_BY_ROBOTS_TXT }
		enum RobotsTxtStateEnum extends Enum[RobotsTxtStateEnum] { case ROBOTS_TXT_STATE_UNSPECIFIED, ALLOWED, DISALLOWED }
		enum CrawledAsEnum extends Enum[CrawledAsEnum] { case CRAWLING_USER_AGENT_UNSPECIFIED, DESKTOP, MOBILE }
		enum PageFetchStateEnum extends Enum[PageFetchStateEnum] { case PAGE_FETCH_STATE_UNSPECIFIED, SUCCESSFUL, SOFT_404, BLOCKED_ROBOTS_TXT, NOT_FOUND, ACCESS_DENIED, SERVER_ERROR, REDIRECT_ERROR, ACCESS_FORBIDDEN, BLOCKED_4XX, INTERNAL_CRAWL_ERROR, INVALID_URL }
	}
	case class IndexStatusInspectionResult(
	  /** High level verdict about whether the URL &#42;is&#42; indexed (indexed status), or &#42;can be&#42; indexed (live inspection). */
		verdict: Option[Schema.IndexStatusInspectionResult.VerdictEnum] = None,
	  /** Whether or not the page blocks indexing through a noindex rule. */
		indexingState: Option[Schema.IndexStatusInspectionResult.IndexingStateEnum] = None,
	  /** Whether or not the page is blocked to Google by a robots.txt rule. */
		robotsTxtState: Option[Schema.IndexStatusInspectionResult.RobotsTxtStateEnum] = None,
	  /** The URL of the page that Google selected as canonical. If the page was not indexed, this field is absent. */
		googleCanonical: Option[String] = None,
	  /** Primary crawler that was used by Google to crawl your site. */
		crawledAs: Option[Schema.IndexStatusInspectionResult.CrawledAsEnum] = None,
	  /** Last time this URL was crawled by Google using the [primary crawler](https://support.google.com/webmasters/answer/7440203#primary_crawler). Absent if the URL was never crawled successfully. */
		lastCrawlTime: Option[String] = None,
	  /** Could Google find and index the page. More details about page indexing appear in 'indexing_state'. */
		coverageState: Option[String] = None,
	  /** Any sitemaps that this URL was listed in, as known by Google. Not guaranteed to be an exhaustive list, especially if Google did not discover this URL through a sitemap. Absent if no sitemaps were found. */
		sitemap: Option[List[String]] = None,
	  /** The URL that your page or site [declares as canonical](https://developers.google.com/search/docs/advanced/crawling/consolidate-duplicate-urls?#define-canonical). If you did not declare a canonical URL, this field is absent. */
		userCanonical: Option[String] = None,
	  /** URLs that link to the inspected URL, directly and indirectly. */
		referringUrls: Option[List[String]] = None,
	  /** Whether or not Google could retrieve the page from your server. Equivalent to ["page fetch"](https://support.google.com/webmasters/answer/9012289#index_coverage) in the URL inspection report. */
		pageFetchState: Option[Schema.IndexStatusInspectionResult.PageFetchStateEnum] = None
	)
}
