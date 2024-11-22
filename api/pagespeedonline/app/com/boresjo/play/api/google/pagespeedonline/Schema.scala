package com.boresjo.play.api.google.pagespeedonline

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class PagespeedApiPagespeedResponseV5(
	  /** Kind of result. */
		kind: Option[String] = None,
	  /** The captcha verify result */
		captchaResult: Option[String] = None,
	  /** Canonicalized and final URL for the document, after following page redirects (if any). */
		id: Option[String] = None,
	  /** Metrics of end users' page loading experience. */
		loadingExperience: Option[Schema.PagespeedApiLoadingExperienceV5] = None,
	  /** Metrics of the aggregated page loading experience of the origin */
		originLoadingExperience: Option[Schema.PagespeedApiLoadingExperienceV5] = None,
	  /** The UTC timestamp of this analysis. */
		analysisUTCTimestamp: Option[String] = None,
	  /** Lighthouse response for the audit url as an object. */
		lighthouseResult: Option[Schema.LighthouseResultV5] = None,
	  /** The version of PageSpeed used to generate these results. */
		version: Option[Schema.PagespeedVersion] = None
	)
	
	case class PagespeedApiLoadingExperienceV5(
	  /** The url, pattern or origin which the metrics are on. */
		id: Option[String] = None,
	  /** The map of . */
		metrics: Option[Map[String, Schema.UserPageLoadMetricV5]] = None,
	  /** The human readable speed "category" of the id. */
		overall_category: Option[String] = None,
	  /** The requested URL, which may differ from the resolved "id". */
		initial_url: Option[String] = None,
	  /** True if the result is an origin fallback from a page, false otherwise. */
		origin_fallback: Option[Boolean] = None
	)
	
	case class UserPageLoadMetricV5(
	  /** Identifies the type of the metric. */
		metricId: Option[String] = None,
	  /** Identifies the form factor of the metric being collected. */
		formFactor: Option[String] = None,
	  /** Metric distributions. Proportions should sum up to 1. */
		distributions: Option[List[Schema.Bucket]] = None,
	  /** The median number of the metric, in millisecond. */
		median: Option[Int] = None,
	  /** The category of the specific time metric. */
		category: Option[String] = None,
	  /** We use this field to store certain percentile value for this metric. For v4, this field contains pc50. For v5, this field contains pc90. */
		percentile: Option[Int] = None
	)
	
	case class Bucket(
	  /** Lower bound for a bucket's range. */
		min: Option[Int] = None,
	  /** Upper bound for a bucket's range. */
		max: Option[Int] = None,
	  /** The proportion of data in this bucket. */
		proportion: Option[BigDecimal] = None
	)
	
	case class LighthouseResultV5(
	  /** The time that this run was fetched. */
		fetchTime: Option[String] = None,
	  /** The original requested url. */
		requestedUrl: Option[String] = None,
	  /** The final resolved url that was audited. */
		finalUrl: Option[String] = None,
	  /** URL of the main document request of the final navigation. */
		mainDocumentUrl: Option[String] = None,
	  /** URL displayed on the page after Lighthouse finishes. */
		finalDisplayedUrl: Option[String] = None,
	  /** The lighthouse version that was used to generate this LHR. */
		lighthouseVersion: Option[String] = None,
	  /** The internationalization strings that are required to render the LHR. */
		i18n: Option[Schema.I18n] = None,
	  /** The user agent that was used to run this LHR. */
		userAgent: Option[String] = None,
	  /** Map of audits in the LHR. */
		audits: Option[Map[String, Schema.LighthouseAuditResultV5]] = None,
	  /** Map of category groups in the LHR. */
		categoryGroups: Option[Map[String, Schema.CategoryGroupV5]] = None,
	  /** The Stack Pack advice strings. */
		stackPacks: Option[List[Schema.StackPack]] = None,
	  /** Environment settings that were used when making this LHR. */
		environment: Option[Schema.Environment] = None,
	  /** List of all run warnings in the LHR. Will always output to at least `[]`. */
		runWarnings: Option[List[JsValue]] = None,
	  /** A top-level error message that, if present, indicates a serious enough problem that this Lighthouse result may need to be discarded. */
		runtimeError: Option[Schema.RuntimeError] = None,
	  /** Map of categories in the LHR. */
		categories: Option[Schema.Categories] = None,
	  /** Timing information for this LHR. */
		timing: Option[Schema.Timing] = None,
	  /** The configuration settings for this LHR. */
		configSettings: Option[Schema.ConfigSettings] = None,
	  /** Screenshot data of the full page, along with node rects relevant to the audit results. */
		fullPageScreenshot: Option[JsValue] = None,
	  /** Entity classification data. */
		entities: Option[List[Schema.LhrEntity]] = None
	)
	
	case class I18n(
	  /** Internationalized strings that are formatted to the locale in configSettings. */
		rendererFormattedStrings: Option[Schema.RendererFormattedStrings] = None
	)
	
	case class RendererFormattedStrings(
	  /** The disclaimer shown below a performance metric value. */
		varianceDisclaimer: Option[String] = None,
	  /** The heading for the estimated page load savings opportunity of an audit. */
		opportunityResourceColumnLabel: Option[String] = None,
	  /** The heading for the estimated page load savings of opportunity audits. */
		opportunitySavingsColumnLabel: Option[String] = None,
	  /** The error string shown next to an erroring audit. */
		errorMissingAuditInfo: Option[String] = None,
	  /** The label shown next to an audit or metric that has had an error. */
		errorLabel: Option[String] = None,
	  /** The label shown above a bulleted list of warnings. */
		warningHeader: Option[String] = None,
	  /** The tooltip text on an expandable chevron icon. */
		auditGroupExpandTooltip: Option[String] = None,
	  /** The heading that is shown above a list of audits that are passing. */
		passedAuditsGroupTitle: Option[String] = None,
	  /** The heading shown above a list of audits that do not apply to a page. */
		notApplicableAuditsGroupTitle: Option[String] = None,
	  /** The heading shown above a list of audits that were not computerd in the run. */
		manualAuditsGroupTitle: Option[String] = None,
	  /** The label shown preceding important warnings that may have invalidated an entire report. */
		toplevelWarningsMessage: Option[String] = None,
	  /** The label that explains the score gauges scale (0-49, 50-89, 90-100). */
		scorescaleLabel: Option[String] = None,
	  /** The label for values shown in the summary of critical request chains. */
		crcLongestDurationLabel: Option[String] = None,
	  /** The label for the initial request in a critical request chain. */
		crcInitialNavigation: Option[String] = None,
	  /** The disclaimer shown under performance explaining that the network can vary. */
		lsPerformanceCategoryDescription: Option[String] = None,
	  /** The title of the lab data performance category. */
		labDataTitle: Option[String] = None,
	  /** The heading that is shown above a list of audits that have warnings */
		warningAuditsGroupTitle: Option[String] = None,
	  /** The label for the button to show all lines of a snippet */
		snippetExpandButtonLabel: Option[String] = None,
	  /** The label for the button to show only a few lines of a snippet */
		snippetCollapseButtonLabel: Option[String] = None,
	  /** This label is for a filter checkbox above a table of items */
		thirdPartyResourcesLabel: Option[String] = None,
	  /** Descriptive explanation for emulation setting when emulating a generic desktop form factor, as opposed to a mobile-device like form factor. */
		runtimeDesktopEmulation: Option[String] = None,
	  /** Descriptive explanation for emulation setting when emulating a Nexus 5X mobile device. */
		runtimeMobileEmulation: Option[String] = None,
	  /** Descriptive explanation for emulation setting when no device emulation is set. */
		runtimeNoEmulation: Option[String] = None,
	  /** Label for a row in a table that shows the estimated CPU power of the machine running Lighthouse. Example row values: 532, 1492, 783. */
		runtimeSettingsBenchmark: Option[String] = None,
	  /** Label for a row in a table that describes the CPU throttling conditions that were used during a Lighthouse run, if any. */
		runtimeSettingsCPUThrottling: Option[String] = None,
	  /** Label for a row in a table that describes the kind of device that was emulated for the Lighthouse run. Example values for row elements: 'No Emulation', 'Emulated Desktop', etc. */
		runtimeSettingsDevice: Option[String] = None,
	  /** Label for a row in a table that shows the time at which a Lighthouse run was conducted; formatted as a timestamp, e.g. Jan 1, 1970 12:00 AM UTC. */
		runtimeSettingsFetchTime: Option[String] = None,
	  /** Label for a row in a table that describes the network throttling conditions that were used during a Lighthouse run, if any. */
		runtimeSettingsNetworkThrottling: Option[String] = None,
	  /** Title of the Runtime settings table in a Lighthouse report. Runtime settings are the environment configurations that a specific report used at auditing time. */
		runtimeSettingsTitle: Option[String] = None,
	  /** Label for a row in a table that shows the User Agent that was detected on the Host machine that ran Lighthouse. */
		runtimeSettingsUA: Option[String] = None,
	  /** Label for a row in a table that shows the User Agent that was used to send out all network requests during the Lighthouse run. */
		runtimeSettingsUANetwork: Option[String] = None,
	  /** Label for a row in a table that shows the URL that was audited during a Lighthouse run. */
		runtimeSettingsUrl: Option[String] = None,
	  /** Descriptive explanation for a runtime setting that is set to an unknown value. */
		runtimeUnknown: Option[String] = None,
	  /** Option in a dropdown menu that copies the Lighthouse JSON object to the system clipboard. */
		dropdownCopyJSON: Option[String] = None,
	  /** Option in a dropdown menu that toggles the themeing of the report between Light(default) and Dark themes. */
		dropdownDarkTheme: Option[String] = None,
	  /** Option in a dropdown menu that opens a full Lighthouse report in a print dialog. */
		dropdownPrintExpanded: Option[String] = None,
	  /** Option in a dropdown menu that opens a small, summary report in a print dialog. */
		dropdownPrintSummary: Option[String] = None,
	  /** Option in a dropdown menu that saves the current report as a new GitHub Gist. */
		dropdownSaveGist: Option[String] = None,
	  /** Option in a dropdown menu that saves the Lighthouse report HTML locally to the system as a '.html' file. */
		dropdownSaveHTML: Option[String] = None,
	  /** Option in a dropdown menu that saves the Lighthouse JSON object to the local system as a '.json' file. */
		dropdownSaveJSON: Option[String] = None,
	  /** Option in a dropdown menu that opens the current report in the Lighthouse Viewer Application. */
		dropdownViewer: Option[String] = None,
	  /** Label for button to create an issue against the Lighthouse GitHub project. */
		footerIssue: Option[String] = None,
	  /** Descriptive explanation for environment throttling that was provided by the runtime environment instead of provided by Lighthouse throttling. */
		throttlingProvided: Option[String] = None,
	  /** Label for a row in a table that shows in what tool Lighthouse is being run (e.g. The lighthouse CLI, Chrome DevTools, Lightrider, WebPageTest, etc). */
		runtimeSettingsChannel: Option[String] = None,
	  /** Text link pointing to the Lighthouse scoring calculator. This link immediately follows a sentence stating the performance score is calculated from the perf metrics. */
		calculatorLink: Option[String] = None,
	  /** Label for a row in a table that shows the version of the Axe library used */
		runtimeSettingsAxeVersion: Option[String] = None,
	  /** Label for a button that opens the Treemap App */
		viewTreemapLabel: Option[String] = None,
	  /** Label preceding a radio control for filtering the list of audits. The radio choices are various performance metrics (FCP, LCP, TBT), and if chosen, the audits in the report are hidden if they are not relevant to the selected metric. */
		showRelevantAudits: Option[String] = None
	)
	
	case class LighthouseAuditResultV5(
	  /** The audit's id. */
		id: Option[String] = None,
	  /** The human readable title. */
		title: Option[String] = None,
	  /** The description of the audit. */
		description: Option[String] = None,
	  /** The enumerated score display mode. */
		scoreDisplayMode: Option[String] = None,
	  /** The value that should be displayed on the UI for this audit. */
		displayValue: Option[String] = None,
	  /** An explanation of the errors in the audit. */
		explanation: Option[String] = None,
	  /** An error message from a thrown error inside the audit. */
		errorMessage: Option[String] = None,
	  /** Freeform details section of the audit. */
		details: Option[Map[String, JsValue]] = None,
	  /** The score of the audit, can be null. */
		score: Option[JsValue] = None,
	  /** Possible warnings that occurred in the audit, can be null. */
		warnings: Option[JsValue] = None,
	  /** A numeric value that has a meaning specific to the audit, e.g. the number of nodes in the DOM or the timestamp of a specific load event. More information can be found in the audit details, if present. */
		numericValue: Option[BigDecimal] = None,
	  /** The unit of the numeric_value field. Used to format the numeric value for display. */
		numericUnit: Option[String] = None,
	  /** The metric savings of the audit. */
		metricSavings: Option[Schema.MetricSavings] = None
	)
	
	case class MetricSavings(
	  /** Optional. Optional numeric value representing the audit's savings for the LCP metric. */
		LCP: Option[BigDecimal] = None,
	  /** Optional. Optional numeric value representing the audit's savings for the FCP metric. */
		FCP: Option[BigDecimal] = None,
	  /** Optional. Optional numeric value representing the audit's savings for the CLS metric. */
		CLS: Option[BigDecimal] = None,
	  /** Optional. Optional numeric value representing the audit's savings for the TBT metric. */
		TBT: Option[BigDecimal] = None,
	  /** Optional. Optional numeric value representing the audit's savings for the INP metric. */
		INP: Option[BigDecimal] = None
	)
	
	case class CategoryGroupV5(
	  /** The human readable title of the group */
		title: Option[String] = None,
	  /** The description of what the category is grouping */
		description: Option[String] = None
	)
	
	case class StackPack(
	  /** The stack pack id. */
		id: Option[String] = None,
	  /** The stack pack title. */
		title: Option[String] = None,
	  /** The stack pack icon data uri. */
		iconDataURL: Option[String] = None,
	  /** The stack pack advice strings. */
		descriptions: Option[Map[String, String]] = None
	)
	
	case class Environment(
	  /** The user agent string that was sent over the network. */
		networkUserAgent: Option[String] = None,
	  /** The user agent string of the version of Chrome used. */
		hostUserAgent: Option[String] = None,
	  /** The benchmark index number that indicates rough device class. */
		benchmarkIndex: Option[BigDecimal] = None,
	  /** The version of libraries with which these results were generated. Ex: axe-core. */
		credits: Option[Map[String, String]] = None
	)
	
	case class RuntimeError(
	  /** The enumerated Lighthouse Error code. */
		code: Option[String] = None,
	  /** A human readable message explaining the error code. */
		message: Option[String] = None
	)
	
	case class Categories(
	  /** The accessibility category, containing all accessibility related audits. */
		accessibility: Option[Schema.LighthouseCategoryV5] = None,
	  /** The best practices category, containing all best practices related audits. */
		best_practices: Option[Schema.LighthouseCategoryV5] = None,
	  /** The Search-Engine-Optimization (SEO) category, containing all seo related audits. */
		seo: Option[Schema.LighthouseCategoryV5] = None,
	  /** The Progressive-Web-App (PWA) category, containing all pwa related audits. This is deprecated in Lighthouse's 12.0 release. */
		pwa: Option[Schema.LighthouseCategoryV5] = None,
	  /** The performance category, containing all performance related audits. */
		performance: Option[Schema.LighthouseCategoryV5] = None
	)
	
	case class LighthouseCategoryV5(
	  /** The string identifier of the category. */
		id: Option[String] = None,
	  /** The human-friendly name of the category. */
		title: Option[String] = None,
	  /** A more detailed description of the category and its importance. */
		description: Option[String] = None,
	  /** A description for the manual audits in the category. */
		manualDescription: Option[String] = None,
	  /** An array of references to all the audit members of this category. */
		auditRefs: Option[List[Schema.AuditRefs]] = None,
	  /** The overall score of the category, the weighted average of all its audits. (The category's score, can be null.) */
		score: Option[JsValue] = None
	)
	
	case class AuditRefs(
	  /** The audit ref id. */
		id: Option[String] = None,
	  /** The weight this audit's score has on the overall category score. */
		weight: Option[BigDecimal] = None,
	  /** The category group that the audit belongs to (optional). */
		group: Option[String] = None,
	  /** The conventional acronym for the audit/metric. */
		acronym: Option[String] = None,
	  /** Any audit IDs closely relevant to this one. */
		relevantAudits: Option[List[String]] = None
	)
	
	case class Timing(
	  /** The total duration of Lighthouse's run. */
		total: Option[BigDecimal] = None
	)
	
	case class ConfigSettings(
	  /** List of categories of audits the run should conduct. */
		onlyCategories: Option[JsValue] = None,
	  /** The form factor the emulation should use. This field is deprecated, form_factor should be used instead. */
		emulatedFormFactor: Option[String] = None,
	  /** The locale setting. */
		locale: Option[String] = None,
	  /** How Lighthouse was run, e.g. from the Chrome extension or from the npm module. */
		channel: Option[String] = None,
	  /** How Lighthouse should interpret this run in regards to scoring performance metrics and skipping mobile-only tests in desktop. */
		formFactor: Option[String] = None
	)
	
	case class LhrEntity(
	  /** Required. Name of the entity. */
		name: Option[String] = None,
	  /** Optional. An optional homepage URL of the entity. */
		homepage: Option[String] = None,
	  /** Optional. An optional category name for the entity. */
		category: Option[String] = None,
	  /** Optional. An optional flag indicating if the entity is the first party. */
		isFirstParty: Option[Boolean] = None,
	  /** Optional. An optional flag indicating if the entity is not recognized. */
		isUnrecognized: Option[Boolean] = None,
	  /** Required. A list of URL origin strings that belong to this entity. */
		origins: Option[List[String]] = None
	)
	
	case class PagespeedVersion(
	  /** The major version number of PageSpeed used to generate these results. */
		major: Option[String] = None,
	  /** The minor version number of PageSpeed used to generate these results. */
		minor: Option[String] = None
	)
}
