package com.boresjo.play.api.google.analytics

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Account {
		case class ChildLinkItem(
		  /** Link to the list of web properties for this account. */
			href: Option[String] = None,
		  /** Type of the child link. Its value is "analytics#webproperties". */
			`type`: Option[String] = Some("""analytics#webproperties""")
		)
		
		case class PermissionsItem(
		  /** All the permissions that the user has for this account. These include any implied permissions (e.g., EDIT implies VIEW). */
			effective: Option[List[String]] = None
		)
	}
	case class Account(
	  /** Child link for an account entry. Points to the list of web properties for this account. */
		childLink: Option[Schema.Account.ChildLinkItem] = None,
	  /** Time the account was created. */
		created: Option[String] = None,
	  /** Account ID. */
		id: Option[String] = None,
	  /** Resource type for Analytics account. */
		kind: Option[String] = Some("""analytics#account"""),
	  /** Account name. */
		name: Option[String] = None,
	  /** Permissions the user has for this account. */
		permissions: Option[Schema.Account.PermissionsItem] = None,
	  /** Link for this account. */
		selfLink: Option[String] = None,
	  /** Indicates whether this account is starred or not. */
		starred: Option[Boolean] = None,
	  /** Time the account was last modified. */
		updated: Option[String] = None
	)
	
	case class AccountRef(
	  /** Link for this account. */
		href: Option[String] = None,
	  /** Account ID. */
		id: Option[String] = None,
	  /** Analytics account reference. */
		kind: Option[String] = Some("""analytics#accountRef"""),
	  /** Account name. */
		name: Option[String] = None
	)
	
	case class AccountSummaries(
	  /** A list of AccountSummaries. */
		items: Option[List[Schema.AccountSummary]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#accountSummaries"""),
	  /** Link to next page for this AccountSummary collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this AccountSummary collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class AccountSummary(
	  /** Account ID. */
		id: Option[String] = None,
	  /** Resource type for Analytics AccountSummary. */
		kind: Option[String] = Some("""analytics#accountSummary"""),
	  /** Account name. */
		name: Option[String] = None,
	  /** Indicates whether this account is starred or not. */
		starred: Option[Boolean] = None,
	  /** List of web properties under this account. */
		webProperties: Option[List[Schema.WebPropertySummary]] = None
	)
	
	case class AccountTicket(
	  /** Account for this ticket. */
		account: Option[Schema.Account] = None,
	  /** Account ticket ID used to access the account ticket. */
		id: Option[String] = None,
	  /** Resource type for account ticket. */
		kind: Option[String] = Some("""analytics#accountTicket"""),
	  /** View (Profile) for the account. */
		profile: Option[Schema.Profile] = None,
	  /** Redirect URI where the user will be sent after accepting Terms of Service. Must be configured in APIs console as a callback URL. */
		redirectUri: Option[String] = None,
	  /** Web property for the account. */
		webproperty: Option[Schema.Webproperty] = None
	)
	
	case class AccountTreeRequest(
		accountName: Option[String] = None,
	  /** Resource type for account ticket. */
		kind: Option[String] = Some("""analytics#accountTreeRequest"""),
		profileName: Option[String] = None,
		timezone: Option[String] = None,
		webpropertyName: Option[String] = None,
		websiteUrl: Option[String] = None
	)
	
	case class AccountTreeResponse(
	  /** The account created. */
		account: Option[Schema.Account] = None,
	  /** Resource type for account ticket. */
		kind: Option[String] = Some("""analytics#accountTreeResponse"""),
	  /** View (Profile) for the account. */
		profile: Option[Schema.Profile] = None,
	  /** Web property for the account. */
		webproperty: Option[Schema.Webproperty] = None
	)
	
	case class Accounts(
	  /** A list of accounts. */
		items: Option[List[Schema.Account]] = None,
	  /** The maximum number of entries the response can contain, regardless of the actual number of entries returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#accounts"""),
	  /** Next link for this account collection. */
		nextLink: Option[String] = None,
	  /** Previous link for this account collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the entries, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class AdWordsAccount(
	  /** True if auto-tagging is enabled on the Google Ads account. Read-only after the insert operation. */
		autoTaggingEnabled: Option[Boolean] = None,
	  /** Customer ID. This field is required when creating a Google Ads link. */
		customerId: Option[String] = None,
	  /** Resource type for Google Ads account. */
		kind: Option[String] = Some("""analytics#adWordsAccount""")
	)
	
	case class AnalyticsDataimportDeleteUploadDataRequest(
	  /** A list of upload UIDs. */
		customDataImportUids: Option[List[String]] = None
	)
	
	case class Column(
	  /** Map of attribute name and value for this column. */
		attributes: Option[Map[String, String]] = None,
	  /** Column id. */
		id: Option[String] = None,
	  /** Resource type for Analytics column. */
		kind: Option[String] = Some("""analytics#column""")
	)
	
	case class Columns(
	  /** List of attributes names returned by columns. */
		attributeNames: Option[List[String]] = None,
	  /** Etag of collection. This etag can be compared with the last response etag to check if response has changed. */
		etag: Option[String] = None,
	  /** List of columns for a report type. */
		items: Option[List[Schema.Column]] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#columns"""),
	  /** Total number of columns returned in the response. */
		totalResults: Option[Int] = None
	)
	
	object CustomDataSource {
		case class ChildLinkItem(
		  /** Link to the list of daily uploads for this custom data source. Link to the list of uploads for this custom data source. */
			href: Option[String] = None,
		  /** Value is "analytics#dailyUploads". Value is "analytics#uploads". */
			`type`: Option[String] = None
		)
		
		case class ParentLinkItem(
		  /** Link to the web property to which this custom data source belongs. */
			href: Option[String] = None,
		  /** Value is "analytics#webproperty". */
			`type`: Option[String] = Some("""analytics#webproperty""")
		)
	}
	case class CustomDataSource(
	  /** Account ID to which this custom data source belongs. */
		accountId: Option[String] = None,
		childLink: Option[Schema.CustomDataSource.ChildLinkItem] = None,
	  /** Time this custom data source was created. */
		created: Option[String] = None,
	  /** Description of custom data source. */
		description: Option[String] = None,
	  /** Custom data source ID. */
		id: Option[String] = None,
		importBehavior: Option[String] = None,
	  /** Resource type for Analytics custom data source. */
		kind: Option[String] = Some("""analytics#customDataSource"""),
	  /** Name of this custom data source. */
		name: Option[String] = None,
	  /** Parent link for this custom data source. Points to the web property to which this custom data source belongs. */
		parentLink: Option[Schema.CustomDataSource.ParentLinkItem] = None,
	  /** IDs of views (profiles) linked to the custom data source. */
		profilesLinked: Option[List[String]] = None,
	  /** Collection of schema headers of the custom data source. */
		schema: Option[List[String]] = None,
	  /** Link for this Analytics custom data source. */
		selfLink: Option[String] = None,
	  /** Type of the custom data source. */
		`type`: Option[String] = None,
	  /** Time this custom data source was last modified. */
		updated: Option[String] = None,
	  /** Upload type of the custom data source. */
		uploadType: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY to which this custom data source belongs. */
		webPropertyId: Option[String] = None
	)
	
	case class CustomDataSources(
	  /** Collection of custom data sources. */
		items: Option[List[Schema.CustomDataSource]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#customDataSources"""),
	  /** Link to next page for this custom data source collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this custom data source collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object CustomDimension {
		case class ParentLinkItem(
		  /** Link to the property to which the custom dimension belongs. */
			href: Option[String] = None,
		  /** Type of the parent link. Set to "analytics#webproperty". */
			`type`: Option[String] = Some("""analytics#webproperty""")
		)
	}
	case class CustomDimension(
	  /** Account ID. */
		accountId: Option[String] = None,
	  /** Boolean indicating whether the custom dimension is active. */
		active: Option[Boolean] = None,
	  /** Time the custom dimension was created. */
		created: Option[String] = None,
	  /** Custom dimension ID. */
		id: Option[String] = None,
	  /** Index of the custom dimension. */
		index: Option[Int] = None,
	  /** Kind value for a custom dimension. Set to "analytics#customDimension". It is a read-only field. */
		kind: Option[String] = Some("""analytics#customDimension"""),
	  /** Name of the custom dimension. */
		name: Option[String] = None,
	  /** Parent link for the custom dimension. Points to the property to which the custom dimension belongs. */
		parentLink: Option[Schema.CustomDimension.ParentLinkItem] = None,
	  /** Scope of the custom dimension: HIT, SESSION, USER or PRODUCT. */
		scope: Option[String] = None,
	  /** Link for the custom dimension */
		selfLink: Option[String] = None,
	  /** Time the custom dimension was last modified. */
		updated: Option[String] = None,
	  /** Property ID. */
		webPropertyId: Option[String] = None
	)
	
	case class CustomDimensions(
	  /** Collection of custom dimensions. */
		items: Option[List[Schema.CustomDimension]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#customDimensions"""),
	  /** Link to next page for this custom dimension collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this custom dimension collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object CustomMetric {
		case class ParentLinkItem(
		  /** Link to the property to which the custom metric belongs. */
			href: Option[String] = None,
		  /** Type of the parent link. Set to "analytics#webproperty". */
			`type`: Option[String] = Some("""analytics#webproperty""")
		)
	}
	case class CustomMetric(
	  /** Account ID. */
		accountId: Option[String] = None,
	  /** Boolean indicating whether the custom metric is active. */
		active: Option[Boolean] = None,
	  /** Time the custom metric was created. */
		created: Option[String] = None,
	  /** Custom metric ID. */
		id: Option[String] = None,
	  /** Index of the custom metric. */
		index: Option[Int] = None,
	  /** Kind value for a custom metric. Set to "analytics#customMetric". It is a read-only field. */
		kind: Option[String] = Some("""analytics#customMetric"""),
	  /** Max value of custom metric. */
		max_value: Option[String] = None,
	  /** Min value of custom metric. */
		min_value: Option[String] = None,
	  /** Name of the custom metric. */
		name: Option[String] = None,
	  /** Parent link for the custom metric. Points to the property to which the custom metric belongs. */
		parentLink: Option[Schema.CustomMetric.ParentLinkItem] = None,
	  /** Scope of the custom metric: HIT or PRODUCT. */
		scope: Option[String] = None,
	  /** Link for the custom metric */
		selfLink: Option[String] = None,
	  /** Data type of custom metric. */
		`type`: Option[String] = None,
	  /** Time the custom metric was last modified. */
		updated: Option[String] = None,
	  /** Property ID. */
		webPropertyId: Option[String] = None
	)
	
	case class CustomMetrics(
	  /** Collection of custom metrics. */
		items: Option[List[Schema.CustomMetric]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#customMetrics"""),
	  /** Link to next page for this custom metric collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this custom metric collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object EntityAdWordsLink {
		case class EntityItem(
			webPropertyRef: Option[Schema.WebPropertyRef] = None
		)
	}
	case class EntityAdWordsLink(
	  /** A list of Google Ads client accounts. These cannot be MCC accounts. This field is required when creating a Google Ads link. It cannot be empty. */
		adWordsAccounts: Option[List[Schema.AdWordsAccount]] = None,
	  /** Web property being linked. */
		entity: Option[Schema.EntityAdWordsLink.EntityItem] = None,
	  /** Entity Google Ads link ID */
		id: Option[String] = None,
	  /** Resource type for entity Google Ads link. */
		kind: Option[String] = Some("""analytics#entityAdWordsLink"""),
	  /** Name of the link. This field is required when creating a Google Ads link. */
		name: Option[String] = None,
	  /** IDs of linked Views (Profiles) represented as strings. */
		profileIds: Option[List[String]] = None,
	  /** URL link for this Google Analytics - Google Ads link. */
		selfLink: Option[String] = None
	)
	
	case class EntityAdWordsLinks(
	  /** A list of entity Google Ads links. */
		items: Option[List[Schema.EntityAdWordsLink]] = None,
	  /** The maximum number of entries the response can contain, regardless of the actual number of entries returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#entityAdWordsLinks"""),
	  /** Next link for this Google Ads link collection. */
		nextLink: Option[String] = None,
	  /** Previous link for this Google Ads link collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the entries, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None
	)
	
	object EntityUserLink {
		case class EntityItem(
		  /** Account for this link. */
			accountRef: Option[Schema.AccountRef] = None,
		  /** View (Profile) for this link. */
			profileRef: Option[Schema.ProfileRef] = None,
		  /** Web property for this link. */
			webPropertyRef: Option[Schema.WebPropertyRef] = None
		)
		
		case class PermissionsItem(
		  /** Effective permissions represent all the permissions that a user has for this entity. These include any implied permissions (e.g., EDIT implies VIEW) or inherited permissions from the parent entity. Effective permissions are read-only. */
			effective: Option[List[String]] = None,
		  /** Permissions that a user has been assigned at this very level. Does not include any implied or inherited permissions. Local permissions are modifiable. */
			local: Option[List[String]] = None
		)
	}
	case class EntityUserLink(
	  /** Entity for this link. It can be an account, a web property, or a view (profile). */
		entity: Option[Schema.EntityUserLink.EntityItem] = None,
	  /** Entity user link ID */
		id: Option[String] = None,
	  /** Resource type for entity user link. */
		kind: Option[String] = Some("""analytics#entityUserLink"""),
	  /** Permissions the user has for this entity. */
		permissions: Option[Schema.EntityUserLink.PermissionsItem] = None,
	  /** Self link for this resource. */
		selfLink: Option[String] = None,
	  /** User reference. */
		userRef: Option[Schema.UserRef] = None
	)
	
	case class EntityUserLinks(
	  /** A list of entity user links. */
		items: Option[List[Schema.EntityUserLink]] = None,
	  /** The maximum number of entries the response can contain, regardless of the actual number of entries returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#entityUserLinks"""),
	  /** Next link for this account collection. */
		nextLink: Option[String] = None,
	  /** Previous link for this account collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the entries, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None
	)
	
	object Experiment {
		case class ParentLinkItem(
		  /** Link to the view (profile) to which this experiment belongs. This field is read-only. */
			href: Option[String] = None,
		  /** Value is "analytics#profile". This field is read-only. */
			`type`: Option[String] = Some("""analytics#profile""")
		)
		
		case class VariationsItem(
		  /** The name of the variation. This field is required when creating an experiment. This field may not be changed for an experiment whose status is ENDED. */
			name: Option[String] = None,
		  /** Status of the variation. Possible values: "ACTIVE", "INACTIVE". INACTIVE variations are not served. This field may not be changed for an experiment whose status is ENDED. */
			status: Option[String] = None,
		  /** The URL of the variation. This field may not be changed for an experiment whose status is RUNNING or ENDED. */
			url: Option[String] = None,
		  /** Weight that this variation should receive. Only present if the experiment is running. This field is read-only. */
			weight: Option[BigDecimal] = None,
		  /** True if the experiment has ended and this variation performed (statistically) significantly better than the original. This field is read-only. */
			won: Option[Boolean] = None
		)
	}
	case class Experiment(
	  /** Account ID to which this experiment belongs. This field is read-only. */
		accountId: Option[String] = None,
	  /** Time the experiment was created. This field is read-only. */
		created: Option[String] = None,
	  /** Notes about this experiment. */
		description: Option[String] = None,
	  /** If true, the end user will be able to edit the experiment via the Google Analytics user interface. */
		editableInGaUi: Option[Boolean] = None,
	  /** The ending time of the experiment (the time the status changed from RUNNING to ENDED). This field is present only if the experiment has ended. This field is read-only. */
		endTime: Option[String] = None,
	  /** Boolean specifying whether to distribute traffic evenly across all variations. If the value is False, content experiments follows the default behavior of adjusting traffic dynamically based on variation performance. Optional -- defaults to False. This field may not be changed for an experiment whose status is ENDED. */
		equalWeighting: Option[Boolean] = None,
	  /** Experiment ID. Required for patch and update. Disallowed for create. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this experiment belongs. This field is read-only. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for an Analytics experiment. This field is read-only. */
		kind: Option[String] = Some("""analytics#experiment"""),
	  /** An integer number in [3, 90]. Specifies the minimum length of the experiment. Can be changed for a running experiment. This field may not be changed for an experiments whose status is ENDED. */
		minimumExperimentLengthInDays: Option[Int] = None,
	  /** Experiment name. This field may not be changed for an experiment whose status is ENDED. This field is required when creating an experiment. */
		name: Option[String] = None,
	  /** The metric that the experiment is optimizing. Valid values: "ga:goal(n)Completions", "ga:adsenseAdsClicks", "ga:adsenseAdsViewed", "ga:adsenseRevenue", "ga:bounces", "ga:pageviews", "ga:sessionDuration", "ga:transactions", "ga:transactionRevenue". This field is required if status is "RUNNING" and servingFramework is one of "REDIRECT" or "API". */
		objectiveMetric: Option[String] = None,
	  /** Whether the objectiveMetric should be minimized or maximized. Possible values: "MAXIMUM", "MINIMUM". Optional--defaults to "MAXIMUM". Cannot be specified without objectiveMetric. Cannot be modified when status is "RUNNING" or "ENDED". */
		optimizationType: Option[String] = None,
	  /** Parent link for an experiment. Points to the view (profile) to which this experiment belongs. */
		parentLink: Option[Schema.Experiment.ParentLinkItem] = None,
	  /** View (Profile) ID to which this experiment belongs. This field is read-only. */
		profileId: Option[String] = None,
	  /** Why the experiment ended. Possible values: "STOPPED_BY_USER", "WINNER_FOUND", "EXPERIMENT_EXPIRED", "ENDED_WITH_NO_WINNER", "GOAL_OBJECTIVE_CHANGED". "ENDED_WITH_NO_WINNER" means that the experiment didn't expire but no winner was projected to be found. If the experiment status is changed via the API to ENDED this field is set to STOPPED_BY_USER. This field is read-only. */
		reasonExperimentEnded: Option[String] = None,
	  /** Boolean specifying whether variations URLS are rewritten to match those of the original. This field may not be changed for an experiments whose status is ENDED. */
		rewriteVariationUrlsAsOriginal: Option[Boolean] = None,
	  /** Link for this experiment. This field is read-only. */
		selfLink: Option[String] = None,
	  /** The framework used to serve the experiment variations and evaluate the results. One of:  
	- REDIRECT: Google Analytics redirects traffic to different variation pages, reports the chosen variation and evaluates the results.
	- API: Google Analytics chooses and reports the variation to serve and evaluates the results; the caller is responsible for serving the selected variation.
	- EXTERNAL: The variations will be served externally and the chosen variation reported to Google Analytics. The caller is responsible for serving the selected variation and evaluating the results. */
		servingFramework: Option[String] = None,
	  /** The snippet of code to include on the control page(s). This field is read-only. */
		snippet: Option[String] = None,
	  /** The starting time of the experiment (the time the status changed from READY_TO_RUN to RUNNING). This field is present only if the experiment has started. This field is read-only. */
		startTime: Option[String] = None,
	  /** Experiment status. Possible values: "DRAFT", "READY_TO_RUN", "RUNNING", "ENDED". Experiments can be created in the "DRAFT", "READY_TO_RUN" or "RUNNING" state. This field is required when creating an experiment. */
		status: Option[String] = None,
	  /** A floating-point number in (0, 1]. Specifies the fraction of the traffic that participates in the experiment. Can be changed for a running experiment. This field may not be changed for an experiments whose status is ENDED. */
		trafficCoverage: Option[BigDecimal] = None,
	  /** Time the experiment was last modified. This field is read-only. */
		updated: Option[String] = None,
	  /** Array of variations. The first variation in the array is the original. The number of variations may not change once an experiment is in the RUNNING state. At least two variations are required before status can be set to RUNNING. */
		variations: Option[List[Schema.Experiment.VariationsItem]] = None,
	  /** Web property ID to which this experiment belongs. The web property ID is of the form UA-XXXXX-YY. This field is read-only. */
		webPropertyId: Option[String] = None,
	  /** A floating-point number in (0, 1). Specifies the necessary confidence level to choose a winner. This field may not be changed for an experiments whose status is ENDED. */
		winnerConfidenceLevel: Option[BigDecimal] = None,
	  /** Boolean specifying whether a winner has been found for this experiment. This field is read-only. */
		winnerFound: Option[Boolean] = None
	)
	
	case class Experiments(
	  /** A list of experiments. */
		items: Option[List[Schema.Experiment]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#experiments"""),
	  /** Link to next page for this experiment collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this experiment collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of resources in the result. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object Filter {
		case class AdvancedDetailsItem(
		  /** Indicates if the filter expressions are case sensitive. */
			caseSensitive: Option[Boolean] = None,
		  /** Expression to extract from field A. */
			extractA: Option[String] = None,
		  /** Expression to extract from field B. */
			extractB: Option[String] = None,
		  /** Field A. */
			fieldA: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			fieldAIndex: Option[Int] = None,
		  /** Indicates if field A is required to match. */
			fieldARequired: Option[Boolean] = None,
		  /** Field B. */
			fieldB: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			fieldBIndex: Option[Int] = None,
		  /** Indicates if field B is required to match. */
			fieldBRequired: Option[Boolean] = None,
		  /** Expression used to construct the output value. */
			outputConstructor: Option[String] = None,
		  /** Output field. */
			outputToField: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			outputToFieldIndex: Option[Int] = None,
		  /** Indicates if the existing value of the output field, if any, should be overridden by the output expression. */
			overrideOutputField: Option[Boolean] = None
		)
		
		case class LowercaseDetailsItem(
		  /** Field to use in the filter. */
			field: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			fieldIndex: Option[Int] = None
		)
		
		case class ParentLinkItem(
		  /** Link to the account to which this filter belongs. */
			href: Option[String] = None,
		  /** Value is "analytics#account". */
			`type`: Option[String] = Some("""analytics#account""")
		)
		
		case class SearchAndReplaceDetailsItem(
		  /** Determines if the filter is case sensitive. */
			caseSensitive: Option[Boolean] = None,
		  /** Field to use in the filter. */
			field: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			fieldIndex: Option[Int] = None,
		  /** Term to replace the search term with. */
			replaceString: Option[String] = None,
		  /** Term to search. */
			searchString: Option[String] = None
		)
		
		case class UppercaseDetailsItem(
		  /** Field to use in the filter. */
			field: Option[String] = None,
		  /** The Index of the custom dimension. Required if field is a CUSTOM_DIMENSION. */
			fieldIndex: Option[Int] = None
		)
	}
	case class Filter(
	  /** Account ID to which this filter belongs. */
		accountId: Option[String] = None,
	  /** Details for the filter of the type ADVANCED. */
		advancedDetails: Option[Schema.Filter.AdvancedDetailsItem] = None,
	  /** Time this filter was created. */
		created: Option[String] = None,
	  /** Details for the filter of the type EXCLUDE. */
		excludeDetails: Option[Schema.FilterExpression] = None,
	  /** Filter ID. */
		id: Option[String] = None,
	  /** Details for the filter of the type INCLUDE. */
		includeDetails: Option[Schema.FilterExpression] = None,
	  /** Resource type for Analytics filter. */
		kind: Option[String] = Some("""analytics#filter"""),
	  /** Details for the filter of the type LOWER. */
		lowercaseDetails: Option[Schema.Filter.LowercaseDetailsItem] = None,
	  /** Name of this filter. */
		name: Option[String] = None,
	  /** Parent link for this filter. Points to the account to which this filter belongs. */
		parentLink: Option[Schema.Filter.ParentLinkItem] = None,
	  /** Details for the filter of the type SEARCH_AND_REPLACE. */
		searchAndReplaceDetails: Option[Schema.Filter.SearchAndReplaceDetailsItem] = None,
	  /** Link for this filter. */
		selfLink: Option[String] = None,
	  /** Type of this filter. Possible values are INCLUDE, EXCLUDE, LOWERCASE, UPPERCASE, SEARCH_AND_REPLACE and ADVANCED. */
		`type`: Option[String] = None,
	  /** Time this filter was last modified. */
		updated: Option[String] = None,
	  /** Details for the filter of the type UPPER. */
		uppercaseDetails: Option[Schema.Filter.UppercaseDetailsItem] = None
	)
	
	case class FilterExpression(
	  /** Determines if the filter is case sensitive. */
		caseSensitive: Option[Boolean] = None,
	  /** Filter expression value */
		expressionValue: Option[String] = None,
	  /** Field to filter. Possible values:  
	- Content and Traffic  
	- PAGE_REQUEST_URI, 
	- PAGE_HOSTNAME, 
	- PAGE_TITLE, 
	- REFERRAL, 
	- COST_DATA_URI (Campaign target URL), 
	- HIT_TYPE, 
	- INTERNAL_SEARCH_TERM, 
	- INTERNAL_SEARCH_TYPE, 
	- SOURCE_PROPERTY_TRACKING_ID,   
	- Campaign or AdGroup  
	- CAMPAIGN_SOURCE, 
	- CAMPAIGN_MEDIUM, 
	- CAMPAIGN_NAME, 
	- CAMPAIGN_AD_GROUP, 
	- CAMPAIGN_TERM, 
	- CAMPAIGN_CONTENT, 
	- CAMPAIGN_CODE, 
	- CAMPAIGN_REFERRAL_PATH,   
	- E-Commerce  
	- TRANSACTION_COUNTRY, 
	- TRANSACTION_REGION, 
	- TRANSACTION_CITY, 
	- TRANSACTION_AFFILIATION (Store or order location), 
	- ITEM_NAME, 
	- ITEM_CODE, 
	- ITEM_VARIATION, 
	- TRANSACTION_ID, 
	- TRANSACTION_CURRENCY_CODE, 
	- PRODUCT_ACTION_TYPE,   
	- Audience/Users  
	- BROWSER, 
	- BROWSER_VERSION, 
	- BROWSER_SIZE, 
	- PLATFORM, 
	- PLATFORM_VERSION, 
	- LANGUAGE, 
	- SCREEN_RESOLUTION, 
	- SCREEN_COLORS, 
	- JAVA_ENABLED (Boolean Field), 
	- FLASH_VERSION, 
	- GEO_SPEED (Connection speed), 
	- VISITOR_TYPE, 
	- GEO_ORGANIZATION (ISP organization), 
	- GEO_DOMAIN, 
	- GEO_IP_ADDRESS, 
	- GEO_IP_VERSION,   
	- Location  
	- GEO_COUNTRY, 
	- GEO_REGION, 
	- GEO_CITY,   
	- Event  
	- EVENT_CATEGORY, 
	- EVENT_ACTION, 
	- EVENT_LABEL,   
	- Other  
	- CUSTOM_FIELD_1, 
	- CUSTOM_FIELD_2, 
	- USER_DEFINED_VALUE,   
	- Application  
	- APP_ID, 
	- APP_INSTALLER_ID, 
	- APP_NAME, 
	- APP_VERSION, 
	- SCREEN, 
	- IS_APP (Boolean Field), 
	- IS_FATAL_EXCEPTION (Boolean Field), 
	- EXCEPTION_DESCRIPTION,   
	- Mobile device  
	- IS_MOBILE (Boolean Field, Deprecated. Use DEVICE_CATEGORY=mobile), 
	- IS_TABLET (Boolean Field, Deprecated. Use DEVICE_CATEGORY=tablet), 
	- DEVICE_CATEGORY, 
	- MOBILE_HAS_QWERTY_KEYBOARD (Boolean Field), 
	- MOBILE_HAS_NFC_SUPPORT (Boolean Field), 
	- MOBILE_HAS_CELLULAR_RADIO (Boolean Field), 
	- MOBILE_HAS_WIFI_SUPPORT (Boolean Field), 
	- MOBILE_BRAND_NAME, 
	- MOBILE_MODEL_NAME, 
	- MOBILE_MARKETING_NAME, 
	- MOBILE_POINTING_METHOD,   
	- Social  
	- SOCIAL_NETWORK, 
	- SOCIAL_ACTION, 
	- SOCIAL_ACTION_TARGET,   
	- Custom dimension  
	- CUSTOM_DIMENSION (See accompanying field index), */
		field: Option[String] = None,
	  /** The Index of the custom dimension. Set only if the field is a is CUSTOM_DIMENSION. */
		fieldIndex: Option[Int] = None,
	  /** Kind value for filter expression */
		kind: Option[String] = Some("""analytics#filterExpression"""),
	  /** Match type for this filter. Possible values are BEGINS_WITH, EQUAL, ENDS_WITH, CONTAINS, or MATCHES. GEO_DOMAIN, GEO_IP_ADDRESS, PAGE_REQUEST_URI, or PAGE_HOSTNAME filters can use any match type; all other filters must use MATCHES. */
		matchType: Option[String] = None
	)
	
	case class FilterRef(
	  /** Account ID to which this filter belongs. */
		accountId: Option[String] = None,
	  /** Link for this filter. */
		href: Option[String] = None,
	  /** Filter ID. */
		id: Option[String] = None,
	  /** Kind value for filter reference. */
		kind: Option[String] = Some("""analytics#filterRef"""),
	  /** Name of this filter. */
		name: Option[String] = None
	)
	
	case class Filters(
	  /** A list of filters. */
		items: Option[List[Schema.Filter]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1,000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#filters"""),
	  /** Link to next page for this filter collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this filter collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object GaData {
		case class ColumnHeadersItem(
		  /** Column Type. Either DIMENSION or METRIC. */
			columnType: Option[String] = None,
		  /** Data type. Dimension column headers have only STRING as the data type. Metric column headers have data types for metric values such as INTEGER, DOUBLE, CURRENCY etc. */
			dataType: Option[String] = None,
		  /** Column name. */
			name: Option[String] = None
		)
		
		object DataTableItem {
			case class ColsItem(
				id: Option[String] = None,
				label: Option[String] = None,
				`type`: Option[String] = None
			)
			
			object RowsItem {
				case class CItem(
					v: Option[String] = None
				)
			}
			case class RowsItem(
				c: Option[List[Schema.GaData.DataTableItem.RowsItem.CItem]] = None
			)
		}
		case class DataTableItem(
			cols: Option[List[Schema.GaData.DataTableItem.ColsItem]] = None,
			rows: Option[List[Schema.GaData.DataTableItem.RowsItem]] = None
		)
		
		case class ProfileInfoItem(
		  /** Account ID to which this view (profile) belongs. */
			accountId: Option[String] = None,
		  /** Internal ID for the web property to which this view (profile) belongs. */
			internalWebPropertyId: Option[String] = None,
		  /** View (Profile) ID. */
			profileId: Option[String] = None,
		  /** View (Profile) name. */
			profileName: Option[String] = None,
		  /** Table ID for view (profile). */
			tableId: Option[String] = None,
		  /** Web Property ID to which this view (profile) belongs. */
			webPropertyId: Option[String] = None
		)
		
		case class QueryItem(
		  /** List of analytics dimensions. */
			dimensions: Option[String] = None,
		  /** End date. */
			end_date: Option[String] = None,
		  /** Comma-separated list of dimension or metric filters. */
			filters: Option[String] = None,
		  /** Unique table ID. */
			ids: Option[String] = None,
		  /** Maximum results per page. */
			max_results: Option[Int] = None,
		  /** List of analytics metrics. */
			metrics: Option[List[String]] = None,
		  /** Desired sampling level */
			samplingLevel: Option[String] = None,
		  /** Analytics advanced segment. */
			segment: Option[String] = None,
		  /** List of dimensions or metrics based on which Analytics data is sorted. */
			sort: Option[List[String]] = None,
		  /** Start date. */
			start_date: Option[String] = None,
		  /** Start index. */
			start_index: Option[Int] = None
		)
	}
	case class GaData(
	  /** Column headers that list dimension names followed by the metric names. The order of dimensions and metrics is same as specified in the request. */
		columnHeaders: Option[List[Schema.GaData.ColumnHeadersItem]] = None,
	  /** Determines if Analytics data contains samples. */
		containsSampledData: Option[Boolean] = None,
	  /** The last refreshed time in seconds for Analytics data. */
		dataLastRefreshed: Option[String] = None,
		dataTable: Option[Schema.GaData.DataTableItem] = None,
	  /** Unique ID for this data response. */
		id: Option[String] = None,
	  /** The maximum number of rows the response can contain, regardless of the actual number of rows returned. Its value ranges from 1 to 10,000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Resource type. */
		kind: Option[String] = Some("""analytics#gaData"""),
	  /** Link to next page for this Analytics data query. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this Analytics data query. */
		previousLink: Option[String] = None,
	  /** Information for the view (profile), for which the Analytics data was requested. */
		profileInfo: Option[Schema.GaData.ProfileInfoItem] = None,
	  /** Analytics data request query parameters. */
		query: Option[Schema.GaData.QueryItem] = None,
	  /** Analytics data rows, where each row contains a list of dimension values followed by the metric values. The order of dimensions and metrics is same as specified in the request. */
		rows: Option[List[List[String]]] = None,
	  /** The number of samples used to calculate the result. */
		sampleSize: Option[String] = None,
	  /** Total size of the sample space from which the samples were selected. */
		sampleSpace: Option[String] = None,
	  /** Link to this page. */
		selfLink: Option[String] = None,
	  /** The total number of rows for the query, regardless of the number of rows in the response. */
		totalResults: Option[Int] = None,
	  /** Total values for the requested metrics over all the results, not just the results returned in this response. The order of the metric totals is same as the metric order specified in the request. */
		totalsForAllResults: Option[Map[String, String]] = None
	)
	
	object Goal {
		object EventDetailsItem {
			case class EventConditionsItem(
			  /** Type of comparison. Possible values are LESS_THAN, GREATER_THAN or EQUAL. */
				comparisonType: Option[String] = None,
			  /** Value used for this comparison. */
				comparisonValue: Option[String] = None,
			  /** Expression used for this match. */
				expression: Option[String] = None,
			  /** Type of the match to be performed. Possible values are REGEXP, BEGINS_WITH, or EXACT. */
				matchType: Option[String] = None,
			  /** Type of this event condition. Possible values are CATEGORY, ACTION, LABEL, or VALUE. */
				`type`: Option[String] = None
			)
		}
		case class EventDetailsItem(
		  /** List of event conditions. */
			eventConditions: Option[List[Schema.Goal.EventDetailsItem.EventConditionsItem]] = None,
		  /** Determines if the event value should be used as the value for this goal. */
			useEventValue: Option[Boolean] = None
		)
		
		case class ParentLinkItem(
		  /** Link to the view (profile) to which this goal belongs. */
			href: Option[String] = None,
		  /** Value is "analytics#profile". */
			`type`: Option[String] = Some("""analytics#profile""")
		)
		
		object UrlDestinationDetailsItem {
			case class StepsItem(
			  /** Step name. */
				name: Option[String] = None,
			  /** Step number. */
				number: Option[Int] = None,
			  /** URL for this step. */
				url: Option[String] = None
			)
		}
		case class UrlDestinationDetailsItem(
		  /** Determines if the goal URL must exactly match the capitalization of visited URLs. */
			caseSensitive: Option[Boolean] = None,
		  /** Determines if the first step in this goal is required. */
			firstStepRequired: Option[Boolean] = None,
		  /** Match type for the goal URL. Possible values are HEAD, EXACT, or REGEX. */
			matchType: Option[String] = None,
		  /** List of steps configured for this goal funnel. */
			steps: Option[List[Schema.Goal.UrlDestinationDetailsItem.StepsItem]] = None,
		  /** URL for this goal. */
			url: Option[String] = None
		)
		
		case class VisitNumPagesDetailsItem(
		  /** Type of comparison. Possible values are LESS_THAN, GREATER_THAN, or EQUAL. */
			comparisonType: Option[String] = None,
		  /** Value used for this comparison. */
			comparisonValue: Option[String] = None
		)
		
		case class VisitTimeOnSiteDetailsItem(
		  /** Type of comparison. Possible values are LESS_THAN or GREATER_THAN. */
			comparisonType: Option[String] = None,
		  /** Value used for this comparison. */
			comparisonValue: Option[String] = None
		)
	}
	case class Goal(
	  /** Account ID to which this goal belongs. */
		accountId: Option[String] = None,
	  /** Determines whether this goal is active. */
		active: Option[Boolean] = None,
	  /** Time this goal was created. */
		created: Option[String] = None,
	  /** Details for the goal of the type EVENT. */
		eventDetails: Option[Schema.Goal.EventDetailsItem] = None,
	  /** Goal ID. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this goal belongs. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for an Analytics goal. */
		kind: Option[String] = Some("""analytics#goal"""),
	  /** Goal name. */
		name: Option[String] = None,
	  /** Parent link for a goal. Points to the view (profile) to which this goal belongs. */
		parentLink: Option[Schema.Goal.ParentLinkItem] = None,
	  /** View (Profile) ID to which this goal belongs. */
		profileId: Option[String] = None,
	  /** Link for this goal. */
		selfLink: Option[String] = None,
	  /** Goal type. Possible values are URL_DESTINATION, VISIT_TIME_ON_SITE, VISIT_NUM_PAGES, AND EVENT. */
		`type`: Option[String] = None,
	  /** Time this goal was last modified. */
		updated: Option[String] = None,
	  /** Details for the goal of the type URL_DESTINATION. */
		urlDestinationDetails: Option[Schema.Goal.UrlDestinationDetailsItem] = None,
	  /** Goal value. */
		value: Option[BigDecimal] = None,
	  /** Details for the goal of the type VISIT_NUM_PAGES. */
		visitNumPagesDetails: Option[Schema.Goal.VisitNumPagesDetailsItem] = None,
	  /** Details for the goal of the type VISIT_TIME_ON_SITE. */
		visitTimeOnSiteDetails: Option[Schema.Goal.VisitTimeOnSiteDetailsItem] = None,
	  /** Web property ID to which this goal belongs. The web property ID is of the form UA-XXXXX-YY. */
		webPropertyId: Option[String] = None
	)
	
	case class Goals(
	  /** A list of goals. */
		items: Option[List[Schema.Goal]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#goals"""),
	  /** Link to next page for this goal collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this goal collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of resources in the result. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class HashClientIdRequest(
		clientId: Option[String] = None,
		kind: Option[String] = Some("""analytics#hashClientIdRequest"""),
		webPropertyId: Option[String] = None
	)
	
	case class HashClientIdResponse(
		clientId: Option[String] = None,
		hashedClientId: Option[String] = None,
		kind: Option[String] = Some("""analytics#hashClientIdResponse"""),
		webPropertyId: Option[String] = None
	)
	
	case class IncludeConditions(
	  /** The look-back window lets you specify a time frame for evaluating the behavior that qualifies users for your audience. For example, if your filters include users from Central Asia, and Transactions Greater than 2, and you set the look-back window to 14 days, then any user from Central Asia whose cumulative transactions exceed 2 during the last 14 days is added to the audience. */
		daysToLookBack: Option[Int] = None,
	  /** Boolean indicating whether this segment is a smart list. https://support.google.com/analytics/answer/4628577 */
		isSmartList: Option[Boolean] = None,
	  /** Resource type for include conditions. */
		kind: Option[String] = Some("""analytics#includeConditions"""),
	  /** Number of days (in the range 1 to 540) a user remains in the audience. */
		membershipDurationDays: Option[Int] = None,
	  /** The segment condition that will cause a user to be added to an audience. */
		segment: Option[String] = None
	)
	
	case class LinkedForeignAccount(
	  /** Account ID to which this linked foreign account belongs. */
		accountId: Option[String] = None,
	  /** Boolean indicating whether this is eligible for search. */
		eligibleForSearch: Option[Boolean] = None,
	  /** Entity ad account link ID. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this linked foreign account belongs. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for linked foreign account. */
		kind: Option[String] = Some("""analytics#linkedForeignAccount"""),
	  /** The foreign account ID. For example the an Google Ads `linkedAccountId` has the following format XXX-XXX-XXXX. */
		linkedAccountId: Option[String] = None,
	  /** Remarketing audience ID to which this linked foreign account belongs. */
		remarketingAudienceId: Option[String] = None,
	  /** The status of this foreign account link. */
		status: Option[String] = None,
	  /** The type of the foreign account. For example, `ADWORDS_LINKS`, `DBM_LINKS`, `MCC_LINKS` or `OPTIMIZE`. */
		`type`: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY to which this linked foreign account belongs. */
		webPropertyId: Option[String] = None
	)
	
	object McfData {
		case class ColumnHeadersItem(
		  /** Column Type. Either DIMENSION or METRIC. */
			columnType: Option[String] = None,
		  /** Data type. Dimension and metric values data types such as INTEGER, DOUBLE, CURRENCY, MCF_SEQUENCE etc. */
			dataType: Option[String] = None,
		  /** Column name. */
			name: Option[String] = None
		)
		
		case class ProfileInfoItem(
		  /** Account ID to which this view (profile) belongs. */
			accountId: Option[String] = None,
		  /** Internal ID for the web property to which this view (profile) belongs. */
			internalWebPropertyId: Option[String] = None,
		  /** View (Profile) ID. */
			profileId: Option[String] = None,
		  /** View (Profile) name. */
			profileName: Option[String] = None,
		  /** Table ID for view (profile). */
			tableId: Option[String] = None,
		  /** Web Property ID to which this view (profile) belongs. */
			webPropertyId: Option[String] = None
		)
		
		case class QueryItem(
		  /** List of analytics dimensions. */
			dimensions: Option[String] = None,
		  /** End date. */
			end_date: Option[String] = None,
		  /** Comma-separated list of dimension or metric filters. */
			filters: Option[String] = None,
		  /** Unique table ID. */
			ids: Option[String] = None,
		  /** Maximum results per page. */
			max_results: Option[Int] = None,
		  /** List of analytics metrics. */
			metrics: Option[List[String]] = None,
		  /** Desired sampling level */
			samplingLevel: Option[String] = None,
		  /** Analytics advanced segment. */
			segment: Option[String] = None,
		  /** List of dimensions or metrics based on which Analytics data is sorted. */
			sort: Option[List[String]] = None,
		  /** Start date. */
			start_date: Option[String] = None,
		  /** Start index. */
			start_index: Option[Int] = None
		)
		
		object RowsItem {
			case class ConversionPathValueItem(
			  /** Type of an interaction on conversion path. Such as CLICK, IMPRESSION etc. */
				interactionType: Option[String] = None,
			  /** Node value of an interaction on conversion path. Such as source, medium etc. */
				nodeValue: Option[String] = None
			)
		}
		case class RowsItem(
		  /** A conversion path dimension value, containing a list of interactions with their attributes. */
			conversionPathValue: Option[List[Schema.McfData.RowsItem.ConversionPathValueItem]] = None,
		  /** A primitive dimension value. A primitive metric value. */
			primitiveValue: Option[String] = None
		)
	}
	case class McfData(
	  /** Column headers that list dimension names followed by the metric names. The order of dimensions and metrics is same as specified in the request. */
		columnHeaders: Option[List[Schema.McfData.ColumnHeadersItem]] = None,
	  /** Determines if the Analytics data contains sampled data. */
		containsSampledData: Option[Boolean] = None,
	  /** Unique ID for this data response. */
		id: Option[String] = None,
	  /** The maximum number of rows the response can contain, regardless of the actual number of rows returned. Its value ranges from 1 to 10,000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Resource type. */
		kind: Option[String] = Some("""analytics#mcfData"""),
	  /** Link to next page for this Analytics data query. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this Analytics data query. */
		previousLink: Option[String] = None,
	  /** Information for the view (profile), for which the Analytics data was requested. */
		profileInfo: Option[Schema.McfData.ProfileInfoItem] = None,
	  /** Analytics data request query parameters. */
		query: Option[Schema.McfData.QueryItem] = None,
	  /** Analytics data rows, where each row contains a list of dimension values followed by the metric values. The order of dimensions and metrics is same as specified in the request. */
		rows: Option[List[List[Schema.McfData.RowsItem]]] = None,
	  /** The number of samples used to calculate the result. */
		sampleSize: Option[String] = None,
	  /** Total size of the sample space from which the samples were selected. */
		sampleSpace: Option[String] = None,
	  /** Link to this page. */
		selfLink: Option[String] = None,
	  /** The total number of rows for the query, regardless of the number of rows in the response. */
		totalResults: Option[Int] = None,
	  /** Total values for the requested metrics over all the results, not just the results returned in this response. The order of the metric totals is same as the metric order specified in the request. */
		totalsForAllResults: Option[Map[String, String]] = None
	)
	
	object Profile {
		case class ChildLinkItem(
		  /** Link to the list of goals for this view (profile). */
			href: Option[String] = None,
		  /** Value is "analytics#goals". */
			`type`: Option[String] = Some("""analytics#goals""")
		)
		
		case class ParentLinkItem(
		  /** Link to the web property to which this view (profile) belongs. */
			href: Option[String] = None,
		  /** Value is "analytics#webproperty". */
			`type`: Option[String] = Some("""analytics#webproperty""")
		)
		
		case class PermissionsItem(
		  /** All the permissions that the user has for this view (profile). These include any implied permissions (e.g., EDIT implies VIEW) or inherited permissions from the parent web property. */
			effective: Option[List[String]] = None
		)
	}
	case class Profile(
	  /** Account ID to which this view (profile) belongs. */
		accountId: Option[String] = None,
	  /** Indicates whether bot filtering is enabled for this view (profile). */
		botFilteringEnabled: Option[Boolean] = None,
	  /** Child link for this view (profile). Points to the list of goals for this view (profile). */
		childLink: Option[Schema.Profile.ChildLinkItem] = None,
	  /** Time this view (profile) was created. */
		created: Option[String] = None,
	  /** The currency type associated with this view (profile), defaults to USD. The supported values are:
	USD, JPY, EUR, GBP, AUD, KRW, BRL, CNY, DKK, RUB, SEK, NOK, PLN, TRY, TWD, HKD, THB, IDR, ARS, MXN, VND, PHP, INR, CHF, CAD, CZK, NZD, HUF, BGN, LTL, ZAR, UAH, AED, BOB, CLP, COP, EGP, HRK, ILS, MAD, MYR, PEN, PKR, RON, RSD, SAR, SGD, VEF, LVL */
		currency: Option[String] = None,
	  /** Default page for this view (profile). */
		defaultPage: Option[String] = None,
	  /** Indicates whether ecommerce tracking is enabled for this view (profile). */
		eCommerceTracking: Option[Boolean] = None,
	  /** Indicates whether enhanced ecommerce tracking is enabled for this view (profile). This property can only be enabled if ecommerce tracking is enabled. */
		enhancedECommerceTracking: Option[Boolean] = None,
	  /** The query parameters that are excluded from this view (profile). */
		excludeQueryParameters: Option[String] = None,
	  /** View (Profile) ID. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this view (profile) belongs. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for Analytics view (profile). */
		kind: Option[String] = Some("""analytics#profile"""),
	  /** Name of this view (profile). */
		name: Option[String] = None,
	  /** Parent link for this view (profile). Points to the web property to which this view (profile) belongs. */
		parentLink: Option[Schema.Profile.ParentLinkItem] = None,
	  /** Permissions the user has for this view (profile). */
		permissions: Option[Schema.Profile.PermissionsItem] = None,
	  /** Link for this view (profile). */
		selfLink: Option[String] = None,
	  /** Site search category parameters for this view (profile). */
		siteSearchCategoryParameters: Option[String] = None,
	  /** The site search query parameters for this view (profile). */
		siteSearchQueryParameters: Option[String] = None,
	  /** Indicates whether this view (profile) is starred or not. */
		starred: Option[Boolean] = None,
	  /** Whether or not Analytics will strip search category parameters from the URLs in your reports. */
		stripSiteSearchCategoryParameters: Option[Boolean] = None,
	  /** Whether or not Analytics will strip search query parameters from the URLs in your reports. */
		stripSiteSearchQueryParameters: Option[Boolean] = None,
	  /** Time zone for which this view (profile) has been configured. Time zones are identified by strings from the TZ database. */
		timezone: Option[String] = None,
	  /** View (Profile) type. Supported types: WEB or APP. */
		`type`: Option[String] = None,
	  /** Time this view (profile) was last modified. */
		updated: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY to which this view (profile) belongs. */
		webPropertyId: Option[String] = None,
	  /** Website URL for this view (profile). */
		websiteUrl: Option[String] = None
	)
	
	case class ProfileFilterLink(
	  /** Filter for this link. */
		filterRef: Option[Schema.FilterRef] = None,
	  /** Profile filter link ID. */
		id: Option[String] = None,
	  /** Resource type for Analytics filter. */
		kind: Option[String] = Some("""analytics#profileFilterLink"""),
	  /** View (Profile) for this link. */
		profileRef: Option[Schema.ProfileRef] = None,
	  /** The rank of this profile filter link relative to the other filters linked to the same profile.
	For readonly (i.e., list and get) operations, the rank always starts at 1.
	For write (i.e., create, update, or delete) operations, you may specify a value between 0 and 255 inclusively, [0, 255]. In order to insert a link at the end of the list, either don't specify a rank or set a rank to a number greater than the largest rank in the list. In order to insert a link to the beginning of the list specify a rank that is less than or equal to 1. The new link will move all existing filters with the same or lower rank down the list. After the link is inserted/updated/deleted all profile filter links will be renumbered starting at 1. */
		rank: Option[Int] = None,
	  /** Link for this profile filter link. */
		selfLink: Option[String] = None
	)
	
	case class ProfileFilterLinks(
	  /** A list of profile filter links. */
		items: Option[List[Schema.ProfileFilterLink]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1,000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#profileFilterLinks"""),
	  /** Link to next page for this profile filter link collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this profile filter link collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class ProfileRef(
	  /** Account ID to which this view (profile) belongs. */
		accountId: Option[String] = None,
	  /** Link for this view (profile). */
		href: Option[String] = None,
	  /** View (Profile) ID. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this view (profile) belongs. */
		internalWebPropertyId: Option[String] = None,
	  /** Analytics view (profile) reference. */
		kind: Option[String] = Some("""analytics#profileRef"""),
	  /** Name of this view (profile). */
		name: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY to which this view (profile) belongs. */
		webPropertyId: Option[String] = None
	)
	
	case class ProfileSummary(
	  /** View (profile) ID. */
		id: Option[String] = None,
	  /** Resource type for Analytics ProfileSummary. */
		kind: Option[String] = Some("""analytics#profileSummary"""),
	  /** View (profile) name. */
		name: Option[String] = None,
	  /** Indicates whether this view (profile) is starred or not. */
		starred: Option[Boolean] = None,
	  /** View (Profile) type. Supported types: WEB or APP. */
		`type`: Option[String] = None
	)
	
	case class Profiles(
	  /** A list of views (profiles). */
		items: Option[List[Schema.Profile]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#profiles"""),
	  /** Link to next page for this view (profile) collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this view (profile) collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object RealtimeData {
		case class ColumnHeadersItem(
		  /** Column Type. Either DIMENSION or METRIC. */
			columnType: Option[String] = None,
		  /** Data type. Dimension column headers have only STRING as the data type. Metric column headers have data types for metric values such as INTEGER, DOUBLE, CURRENCY etc. */
			dataType: Option[String] = None,
		  /** Column name. */
			name: Option[String] = None
		)
		
		case class ProfileInfoItem(
		  /** Account ID to which this view (profile) belongs. */
			accountId: Option[String] = None,
		  /** Internal ID for the web property to which this view (profile) belongs. */
			internalWebPropertyId: Option[String] = None,
		  /** View (Profile) ID. */
			profileId: Option[String] = None,
		  /** View (Profile) name. */
			profileName: Option[String] = None,
		  /** Table ID for view (profile). */
			tableId: Option[String] = None,
		  /** Web Property ID to which this view (profile) belongs. */
			webPropertyId: Option[String] = None
		)
		
		case class QueryItem(
		  /** List of real time dimensions. */
			dimensions: Option[String] = None,
		  /** Comma-separated list of dimension or metric filters. */
			filters: Option[String] = None,
		  /** Unique table ID. */
			ids: Option[String] = None,
		  /** Maximum results per page. */
			max_results: Option[Int] = None,
		  /** List of real time metrics. */
			metrics: Option[List[String]] = None,
		  /** List of dimensions or metrics based on which real time data is sorted. */
			sort: Option[List[String]] = None
		)
	}
	case class RealtimeData(
	  /** Column headers that list dimension names followed by the metric names. The order of dimensions and metrics is same as specified in the request. */
		columnHeaders: Option[List[Schema.RealtimeData.ColumnHeadersItem]] = None,
	  /** Unique ID for this data response. */
		id: Option[String] = None,
	  /** Resource type. */
		kind: Option[String] = Some("""analytics#realtimeData"""),
	  /** Information for the view (profile), for which the real time data was requested. */
		profileInfo: Option[Schema.RealtimeData.ProfileInfoItem] = None,
	  /** Real time data request query parameters. */
		query: Option[Schema.RealtimeData.QueryItem] = None,
	  /** Real time data rows, where each row contains a list of dimension values followed by the metric values. The order of dimensions and metrics is same as specified in the request. */
		rows: Option[List[List[String]]] = None,
	  /** Link to this page. */
		selfLink: Option[String] = None,
	  /** The total number of rows for the query, regardless of the number of rows in the response. */
		totalResults: Option[Int] = None,
	  /** Total values for the requested metrics over all the results, not just the results returned in this response. The order of the metric totals is same as the metric order specified in the request. */
		totalsForAllResults: Option[Map[String, String]] = None
	)
	
	object RemarketingAudience {
		case class AudienceDefinitionItem(
		  /** Defines the conditions to include users to the audience. */
			includeConditions: Option[Schema.IncludeConditions] = None
		)
		
		object StateBasedAudienceDefinitionItem {
			case class ExcludeConditionsItem(
			  /** Whether to make the exclusion TEMPORARY or PERMANENT. */
				exclusionDuration: Option[String] = None,
			  /** The segment condition that will cause a user to be removed from an audience. */
				segment: Option[String] = None
			)
		}
		case class StateBasedAudienceDefinitionItem(
		  /** Defines the conditions to exclude users from the audience. */
			excludeConditions: Option[Schema.RemarketingAudience.StateBasedAudienceDefinitionItem.ExcludeConditionsItem] = None,
		  /** Defines the conditions to include users to the audience. */
			includeConditions: Option[Schema.IncludeConditions] = None
		)
	}
	case class RemarketingAudience(
	  /** Account ID to which this remarketing audience belongs. */
		accountId: Option[String] = None,
	  /** The simple audience definition that will cause a user to be added to an audience. */
		audienceDefinition: Option[Schema.RemarketingAudience.AudienceDefinitionItem] = None,
	  /** The type of audience, either SIMPLE or STATE_BASED. */
		audienceType: Option[String] = None,
	  /** Time this remarketing audience was created. */
		created: Option[String] = None,
	  /** The description of this remarketing audience. */
		description: Option[String] = None,
	  /** Remarketing Audience ID. */
		id: Option[String] = None,
	  /** Internal ID for the web property to which this remarketing audience belongs. */
		internalWebPropertyId: Option[String] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#remarketingAudience"""),
	  /** The linked ad accounts associated with this remarketing audience. A remarketing audience can have only one linkedAdAccount currently. */
		linkedAdAccounts: Option[List[Schema.LinkedForeignAccount]] = None,
	  /** The views (profiles) that this remarketing audience is linked to. */
		linkedViews: Option[List[String]] = None,
	  /** The name of this remarketing audience. */
		name: Option[String] = None,
	  /** A state based audience definition that will cause a user to be added or removed from an audience. */
		stateBasedAudienceDefinition: Option[Schema.RemarketingAudience.StateBasedAudienceDefinitionItem] = None,
	  /** Time this remarketing audience was last modified. */
		updated: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY to which this remarketing audience belongs. */
		webPropertyId: Option[String] = None
	)
	
	case class RemarketingAudiences(
	  /** A list of remarketing audiences. */
		items: Option[List[Schema.RemarketingAudience]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#remarketingAudiences"""),
	  /** Link to next page for this remarketing audience collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this view (profile) collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class Segment(
	  /** Time the segment was created. */
		created: Option[String] = None,
	  /** Segment definition. */
		definition: Option[String] = None,
	  /** Segment ID. */
		id: Option[String] = None,
	  /** Resource type for Analytics segment. */
		kind: Option[String] = Some("""analytics#segment"""),
	  /** Segment name. */
		name: Option[String] = None,
	  /** Segment ID. Can be used with the 'segment' parameter in Core Reporting API. */
		segmentId: Option[String] = None,
	  /** Link for this segment. */
		selfLink: Option[String] = None,
	  /** Type for a segment. Possible values are "BUILT_IN" or "CUSTOM". */
		`type`: Option[String] = None,
	  /** Time the segment was last modified. */
		updated: Option[String] = None
	)
	
	case class Segments(
	  /** A list of segments. */
		items: Option[List[Schema.Segment]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type for segments. */
		kind: Option[String] = Some("""analytics#segments"""),
	  /** Link to next page for this segment collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this segment collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object UnsampledReport {
		case class CloudStorageDownloadDetailsItem(
		  /** Id of the bucket the file object is stored in. */
			bucketId: Option[String] = None,
		  /** Id of the file object containing the report data. */
			objectId: Option[String] = None
		)
		
		case class DriveDownloadDetailsItem(
		  /** Id of the document/file containing the report data. */
			documentId: Option[String] = None
		)
	}
	case class UnsampledReport(
	  /** Account ID to which this unsampled report belongs. */
		accountId: Option[String] = None,
	  /** Download details for a file stored in Google Cloud Storage. */
		cloudStorageDownloadDetails: Option[Schema.UnsampledReport.CloudStorageDownloadDetailsItem] = None,
	  /** Time this unsampled report was created. */
		created: Option[String] = None,
	  /** The dimensions for the unsampled report. */
		dimensions: Option[String] = None,
	  /** The type of download you need to use for the report data file. Possible values include `GOOGLE_DRIVE` and `GOOGLE_CLOUD_STORAGE`. If the value is `GOOGLE_DRIVE`, see the `driveDownloadDetails` field. If the value is `GOOGLE_CLOUD_STORAGE`, see the `cloudStorageDownloadDetails` field. */
		downloadType: Option[String] = None,
	  /** Download details for a file stored in Google Drive. */
		driveDownloadDetails: Option[Schema.UnsampledReport.DriveDownloadDetailsItem] = None,
	  /** The end date for the unsampled report. */
		end_date: Option[String] = None,
	  /** The filters for the unsampled report. */
		filters: Option[String] = None,
	  /** Unsampled report ID. */
		id: Option[String] = None,
	  /** Resource type for an Analytics unsampled report. */
		kind: Option[String] = Some("""analytics#unsampledReport"""),
	  /** The metrics for the unsampled report. */
		metrics: Option[String] = None,
	  /** View (Profile) ID to which this unsampled report belongs. */
		profileId: Option[String] = None,
	  /** The segment for the unsampled report. */
		segment: Option[String] = None,
	  /** Link for this unsampled report. */
		selfLink: Option[String] = None,
	  /** The start date for the unsampled report. */
		start_date: Option[String] = None,
	  /** Status of this unsampled report. Possible values are PENDING, COMPLETED, or FAILED. */
		status: Option[String] = None,
	  /** Title of the unsampled report. */
		title: Option[String] = None,
	  /** Time this unsampled report was last modified. */
		updated: Option[String] = None,
	  /** Web property ID to which this unsampled report belongs. The web property ID is of the form UA-XXXXX-YY. */
		webPropertyId: Option[String] = None
	)
	
	case class UnsampledReports(
	  /** A list of unsampled reports. */
		items: Option[List[Schema.UnsampledReport]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#unsampledReports"""),
	  /** Link to next page for this unsampled report collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this unsampled report collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of resources in the result. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	case class Upload(
	  /** Account Id to which this upload belongs. */
		accountId: Option[String] = None,
	  /** Custom data source Id to which this data import belongs. */
		customDataSourceId: Option[String] = None,
	  /** Data import errors collection. */
		errors: Option[List[String]] = None,
	  /** A unique ID for this upload. */
		id: Option[String] = None,
	  /** Resource type for Analytics upload. */
		kind: Option[String] = Some("""analytics#upload"""),
	  /** Upload status. Possible values: PENDING, COMPLETED, FAILED, DELETING, DELETED. */
		status: Option[String] = None,
	  /** Time this file is uploaded. */
		uploadTime: Option[String] = None
	)
	
	case class Uploads(
	  /** A list of uploads. */
		items: Option[List[Schema.Upload]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#uploads"""),
	  /** Link to next page for this upload collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this upload collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of resources in the result. */
		totalResults: Option[Int] = None
	)
	
	object UserDeletionRequest {
		case class IdItem(
		  /** Type of user */
			`type`: Option[String] = None,
		  /** The User's id */
			userId: Option[String] = None
		)
	}
	case class UserDeletionRequest(
	  /** This marks the point in time for which all user data before should be deleted */
		deletionRequestTime: Option[String] = None,
	  /** Firebase Project Id */
		firebaseProjectId: Option[String] = None,
	  /** User ID. */
		id: Option[Schema.UserDeletionRequest.IdItem] = None,
	  /** Value is "analytics#userDeletionRequest". */
		kind: Option[String] = Some("""analytics#userDeletionRequest"""),
	  /** Property ID */
		propertyId: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY. */
		webPropertyId: Option[String] = None
	)
	
	case class UserRef(
	  /** Email ID of this user. */
		email: Option[String] = None,
	  /** User ID. */
		id: Option[String] = None,
		kind: Option[String] = Some("""analytics#userRef""")
	)
	
	case class WebPropertyRef(
	  /** Account ID to which this web property belongs. */
		accountId: Option[String] = None,
	  /** Link for this web property. */
		href: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY. */
		id: Option[String] = None,
	  /** Internal ID for this web property. */
		internalWebPropertyId: Option[String] = None,
	  /** Analytics web property reference. */
		kind: Option[String] = Some("""analytics#webPropertyRef"""),
	  /** Name of this web property. */
		name: Option[String] = None
	)
	
	case class WebPropertySummary(
	  /** Web property ID of the form UA-XXXXX-YY. */
		id: Option[String] = None,
	  /** Internal ID for this web property. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for Analytics WebPropertySummary. */
		kind: Option[String] = Some("""analytics#webPropertySummary"""),
	  /** Level for this web property. Possible values are STANDARD or PREMIUM. */
		level: Option[String] = None,
	  /** Web property name. */
		name: Option[String] = None,
	  /** List of profiles under this web property. */
		profiles: Option[List[Schema.ProfileSummary]] = None,
	  /** Indicates whether this web property is starred or not. */
		starred: Option[Boolean] = None,
	  /** Website url for this web property. */
		websiteUrl: Option[String] = None
	)
	
	case class Webproperties(
	  /** A list of web properties. */
		items: Option[List[Schema.Webproperty]] = None,
	  /** The maximum number of resources the response can contain, regardless of the actual number of resources returned. Its value ranges from 1 to 1000 with a value of 1000 by default, or otherwise specified by the max-results query parameter. */
		itemsPerPage: Option[Int] = None,
	  /** Collection type. */
		kind: Option[String] = Some("""analytics#webproperties"""),
	  /** Link to next page for this web property collection. */
		nextLink: Option[String] = None,
	  /** Link to previous page for this web property collection. */
		previousLink: Option[String] = None,
	  /** The starting index of the resources, which is 1 by default or otherwise specified by the start-index query parameter. */
		startIndex: Option[Int] = None,
	  /** The total number of results for the query, regardless of the number of results in the response. */
		totalResults: Option[Int] = None,
	  /** Email ID of the authenticated user */
		username: Option[String] = None
	)
	
	object Webproperty {
		case class ChildLinkItem(
		  /** Link to the list of views (profiles) for this web property. */
			href: Option[String] = None,
		  /** Type of the parent link. Its value is "analytics#profiles". */
			`type`: Option[String] = Some("""analytics#profiles""")
		)
		
		case class ParentLinkItem(
		  /** Link to the account for this web property. */
			href: Option[String] = None,
		  /** Type of the parent link. Its value is "analytics#account". */
			`type`: Option[String] = Some("""analytics#account""")
		)
		
		case class PermissionsItem(
		  /** All the permissions that the user has for this web property. These include any implied permissions (e.g., EDIT implies VIEW) or inherited permissions from the parent account. */
			effective: Option[List[String]] = None
		)
	}
	case class Webproperty(
	  /** Account ID to which this web property belongs. */
		accountId: Option[String] = None,
	  /** Child link for this web property. Points to the list of views (profiles) for this web property. */
		childLink: Option[Schema.Webproperty.ChildLinkItem] = None,
	  /** Time this web property was created. */
		created: Option[String] = None,
	  /** Set to true to reset the retention period of the user identifier with each new event from that user (thus setting the expiration date to current time plus retention period).
	Set to false to delete data associated with the user identifier automatically after the rentention period.
	This property cannot be set on insert. */
		dataRetentionResetOnNewActivity: Option[Boolean] = None,
	  /** The length of time for which user and event data is retained.
	This property cannot be set on insert. */
		dataRetentionTtl: Option[String] = None,
	  /** Default view (profile) ID. */
		defaultProfileId: Option[String] = None,
	  /** Web property ID of the form UA-XXXXX-YY. */
		id: Option[String] = None,
	  /** The industry vertical/category selected for this web property. */
		industryVertical: Option[String] = None,
	  /** Internal ID for this web property. */
		internalWebPropertyId: Option[String] = None,
	  /** Resource type for Analytics WebProperty. */
		kind: Option[String] = Some("""analytics#webproperty"""),
	  /** Level for this web property. Possible values are STANDARD or PREMIUM. */
		level: Option[String] = None,
	  /** Name of this web property. */
		name: Option[String] = None,
	  /** Parent link for this web property. Points to the account to which this web property belongs. */
		parentLink: Option[Schema.Webproperty.ParentLinkItem] = None,
	  /** Permissions the user has for this web property. */
		permissions: Option[Schema.Webproperty.PermissionsItem] = None,
	  /** View (Profile) count for this web property. */
		profileCount: Option[Int] = None,
	  /** Link for this web property. */
		selfLink: Option[String] = None,
	  /** Indicates whether this web property is starred or not. */
		starred: Option[Boolean] = None,
	  /** Time this web property was last modified. */
		updated: Option[String] = None,
	  /** Website url for this web property. */
		websiteUrl: Option[String] = None
	)
}
