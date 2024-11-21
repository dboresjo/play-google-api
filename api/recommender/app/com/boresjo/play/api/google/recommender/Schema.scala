package com.boresjo.play.api.google.recommender

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudRecommenderV1ReliabilityProjection {
		enum RisksEnum extends Enum[RisksEnum] { case RISK_TYPE_UNSPECIFIED, SERVICE_DISRUPTION, DATA_LOSS, ACCESS_DENY }
	}
	case class GoogleCloudRecommenderV1ReliabilityProjection(
	  /** Reliability risks mitigated by this recommendation. */
		risks: Option[List[Schema.GoogleCloudRecommenderV1ReliabilityProjection.RisksEnum]] = None,
	  /** Per-recommender projection. */
		details: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudRecommenderV1Recommendation {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, P4, P3, P2, P1 }
	}
	case class GoogleCloudRecommenderV1Recommendation(
	  /** Last time this recommendation was refreshed by the system that created it in the first place. */
		lastRefreshTime: Option[String] = None,
	  /** Information for state. Contains state and metadata. */
		stateInfo: Option[Schema.GoogleCloudRecommenderV1RecommendationStateInfo] = None,
	  /** Insights that led to this recommendation. */
		associatedInsights: Option[List[Schema.GoogleCloudRecommenderV1RecommendationInsightReference]] = None,
	  /** Free-form human readable summary in English. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** Corresponds to a mutually exclusive group ID within a recommender. A non-empty ID indicates that the recommendation belongs to a mutually exclusive group. This means that only one recommendation within the group is suggested to be applied. */
		xorGroupId: Option[String] = None,
	  /** Identifier. Name of recommendation. */
		name: Option[String] = None,
	  /** Content of the recommendation describing recommended changes to resources. */
		content: Option[Schema.GoogleCloudRecommenderV1RecommendationContent] = None,
	  /** Contains an identifier for a subtype of recommendations produced for the same recommender. Subtype is a function of content and impact, meaning a new subtype might be added when significant changes to `content` or `primary_impact.category` are introduced. See the Recommenders section to see a list of subtypes for a given Recommender. Examples: For recommender = "google.iam.policy.Recommender", recommender_subtype can be one of "REMOVE_ROLE"/"REPLACE_ROLE" */
		recommenderSubtype: Option[String] = None,
	  /** Recommendation's priority. */
		priority: Option[Schema.GoogleCloudRecommenderV1Recommendation.PriorityEnum] = None,
	  /** Optional set of additional impact that this recommendation may have when trying to optimize for the primary category. These may be positive or negative. */
		additionalImpact: Option[List[Schema.GoogleCloudRecommenderV1Impact]] = None,
	  /** The primary impact that this recommendation can have while trying to optimize for one category. */
		primaryImpact: Option[Schema.GoogleCloudRecommenderV1Impact] = None,
	  /** Fully qualified resource names that this recommendation is targeting. */
		targetResources: Option[List[String]] = None,
	  /** Fingerprint of the Recommendation. Provides optimistic locking when updating states. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1InsightRecommendationReference(
	  /** Recommendation resource name, e.g. projects/[PROJECT_NUMBER]/locations/[LOCATION]/recommenders/[RECOMMENDER_ID]/recommendations/[RECOMMENDATION_ID] */
		recommendation: Option[String] = None
	)
	
	object GoogleCloudRecommenderV1RecommendationStateInfo {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CLAIMED, SUCCEEDED, FAILED, DISMISSED }
	}
	case class GoogleCloudRecommenderV1RecommendationStateInfo(
	  /** The state of the recommendation, Eg ACTIVE, SUCCEEDED, FAILED. */
		state: Option[Schema.GoogleCloudRecommenderV1RecommendationStateInfo.StateEnum] = None,
	  /** A map of metadata for the state, provided by user or automations systems. */
		stateMetadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRecommenderV1InsightTypeConfig(
	  /** Identifier. Name of insight type config. Eg, projects/[PROJECT_NUMBER]/locations/[LOCATION]/insightTypes/[INSIGHT_TYPE_ID]/config */
		name: Option[String] = None,
	  /** Fingerprint of the InsightTypeConfig. Provides optimistic locking when updating. */
		etag: Option[String] = None,
	  /** Last time when the config was updated. */
		updateTime: Option[String] = None,
	  /** A user-settable field to provide a human-readable name to be used in user interfaces. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. The revision ID of the config. A new revision is committed whenever the config is changed in any way. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Allows clients to store small amounts of arbitrary data. Annotations must follow the Kubernetes syntax. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** InsightTypeGenerationConfig which configures the generation of insights for this insight type. */
		insightTypeGenerationConfig: Option[Schema.GoogleCloudRecommenderV1InsightTypeGenerationConfig] = None
	)
	
	case class GoogleCloudRecommenderV1InsightTypeGenerationConfig(
	  /** Parameters for this InsightTypeGenerationConfig. These configs can be used by or are applied to all subtypes. */
		params: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudRecommenderV1RecommendationInsightReference(
	  /** Insight resource name, e.g. projects/[PROJECT_NUMBER]/locations/[LOCATION]/insightTypes/[INSIGHT_TYPE_ID]/insights/[INSIGHT_ID] */
		insight: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1MarkRecommendationSucceededRequest(
	  /** State properties to include with this state. Overwrites any existing `state_metadata`. Keys must match the regex `/^a-z0-9{0,62}$/`. Values must match the regex `/^[a-zA-Z0-9_./-]{0,255}$/`. */
		stateMetadata: Option[Map[String, String]] = None,
	  /** Required. Fingerprint of the Recommendation. Provides optimistic locking. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1MarkRecommendationClaimedRequest(
	  /** Required. Fingerprint of the Recommendation. Provides optimistic locking. */
		etag: Option[String] = None,
	  /** State properties to include with this state. Overwrites any existing `state_metadata`. Keys must match the regex `/^a-z0-9{0,62}$/`. Values must match the regex `/^[a-zA-Z0-9_./-]{0,255}$/`. */
		stateMetadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRecommenderV1RecommenderConfig(
	  /** A user-settable field to provide a human-readable name to be used in user interfaces. */
		displayName: Option[String] = None,
	  /** Last time when the config was updated. */
		updateTime: Option[String] = None,
	  /** Fingerprint of the RecommenderConfig. Provides optimistic locking when updating. */
		etag: Option[String] = None,
	  /** Output only. Immutable. The revision ID of the config. A new revision is committed whenever the config is changed in any way. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Identifier. Name of recommender config. Eg, projects/[PROJECT_NUMBER]/locations/[LOCATION]/recommenders/[RECOMMENDER_ID]/config */
		name: Option[String] = None,
	  /** Allows clients to store small amounts of arbitrary data. Annotations must follow the Kubernetes syntax. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** RecommenderGenerationConfig which configures the Generation of recommendations for this recommender. */
		recommenderGenerationConfig: Option[Schema.GoogleCloudRecommenderV1RecommenderGenerationConfig] = None
	)
	
	case class GoogleCloudRecommenderV1MarkRecommendationDismissedRequest(
	  /** Fingerprint of the Recommendation. Provides optimistic locking. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1ListInsightsResponse(
	  /** A token that can be used to request the next page of results. This field is empty if there are no additional results. */
		nextPageToken: Option[String] = None,
	  /** The set of insights for the `parent` resource. */
		insights: Option[List[Schema.GoogleCloudRecommenderV1Insight]] = None
	)
	
	case class GoogleCloudRecommenderV1MarkRecommendationFailedRequest(
	  /** Required. Fingerprint of the Recommendation. Provides optimistic locking. */
		etag: Option[String] = None,
	  /** State properties to include with this state. Overwrites any existing `state_metadata`. Keys must match the regex `/^a-z0-9{0,62}$/`. Values must match the regex `/^[a-zA-Z0-9_./-]{0,255}$/`. */
		stateMetadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRecommenderV1ValueMatcher(
	  /** To be used for full regex matching. The regular expression is using the Google RE2 syntax (https://github.com/google/re2/wiki/Syntax), so to be used with RE2::FullMatch */
		matchesPattern: Option[String] = None
	)
	
	object GoogleCloudRecommenderV1InsightStateInfo {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, ACCEPTED, DISMISSED }
	}
	case class GoogleCloudRecommenderV1InsightStateInfo(
	  /** A map of metadata for the state, provided by user or automations systems. */
		stateMetadata: Option[Map[String, String]] = None,
	  /** Insight state. */
		state: Option[Schema.GoogleCloudRecommenderV1InsightStateInfo.StateEnum] = None
	)
	
	case class GoogleCloudRecommenderV1Operation(
	  /** Can be set for action 'test' for advanced matching for the value of 'path' field. Either this or `value` will be set for 'test' operation. */
		valueMatcher: Option[Schema.GoogleCloudRecommenderV1ValueMatcher] = None,
	  /** Path to the target field being operated on. If the operation is at the resource level, then path should be "/". This field is always populated. */
		path: Option[String] = None,
	  /** Contains the fully qualified resource name. This field is always populated. ex: //cloudresourcemanager.googleapis.com/projects/foo. */
		resource: Option[String] = None,
	  /** Set of filters to apply if `path` refers to array elements or nested array elements in order to narrow down to a single unique element that is being tested/modified. This is intended to be an exact match per filter. To perform advanced matching, use path_value_matchers. &#42; Example: ``` { "/versions/&#42;/name" : "it-123" "/versions/&#42;/targetSize/percent": 20 } ``` &#42; Example: ``` { "/bindings/&#42;/role": "roles/owner" "/bindings/&#42;/condition" : null } ``` &#42; Example: ``` { "/bindings/&#42;/role": "roles/owner" "/bindings/&#42;/members/&#42;" : ["x@example.com", "y@example.com"] } ``` When both path_filters and path_value_matchers are set, an implicit AND must be performed. */
		pathFilters: Option[Map[String, JsValue]] = None,
	  /** Can be set with action 'copy' or 'move' to indicate the source field within resource or source_resource, ignored if provided for other operation types. */
		sourcePath: Option[String] = None,
	  /** Type of GCP resource being modified/tested. This field is always populated. Example: cloudresourcemanager.googleapis.com/Project, compute.googleapis.com/Instance */
		resourceType: Option[String] = None,
	  /** Type of this operation. Contains one of 'add', 'remove', 'replace', 'move', 'copy', 'test' and custom operations. This field is case-insensitive and always populated. */
		action: Option[String] = None,
	  /** Similar to path_filters, this contains set of filters to apply if `path` field refers to array elements. This is meant to support value matching beyond exact match. To perform exact match, use path_filters. When both path_filters and path_value_matchers are set, an implicit AND must be performed. */
		pathValueMatchers: Option[Map[String, Schema.GoogleCloudRecommenderV1ValueMatcher]] = None,
	  /** Can be set with action 'copy' to copy resource configuration across different resources of the same type. Example: A resource clone can be done via action = 'copy', path = "/", from = "/", source_resource = and resource_name = . This field is empty for all other values of `action`. */
		sourceResource: Option[String] = None,
	  /** Value for the `path` field. Will be set for actions:'add'/'replace'. Maybe set for action: 'test'. Either this or `value_matcher` will be set for 'test' operation. An exact match must be performed. */
		value: Option[JsValue] = None
	)
	
	case class GoogleCloudRecommenderV1SustainabilityProjection(
	  /** Carbon Footprint generated in kg of CO2 equivalent. Chose kg_c_o2e so that the name renders correctly in camelCase (kgCO2e). */
		kgCO2e: Option[BigDecimal] = None,
	  /** Duration for which this sustainability applies. */
		duration: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1OperationGroup(
	  /** List of operations across one or more resources that belong to this group. Loosely based on RFC6902 and should be performed in the order they appear. */
		operations: Option[List[Schema.GoogleCloudRecommenderV1Operation]] = None
	)
	
	case class GoogleCloudRecommenderV1ListRecommendationsResponse(
	  /** A token that can be used to request the next page of results. This field is empty if there are no additional results. */
		nextPageToken: Option[String] = None,
	  /** The set of recommendations for the `parent` resource. */
		recommendations: Option[List[Schema.GoogleCloudRecommenderV1Recommendation]] = None
	)
	
	case class GoogleCloudRecommenderV1RecommendationContent(
	  /** Condensed overview information about the recommendation. */
		overview: Option[Map[String, JsValue]] = None,
	  /** Operations to one or more Google Cloud resources grouped in such a way that, all operations within one group are expected to be performed atomically and in an order. */
		operationGroups: Option[List[Schema.GoogleCloudRecommenderV1OperationGroup]] = None
	)
	
	object GoogleCloudRecommenderV1Insight {
		enum CategoryEnum extends Enum[CategoryEnum] { case CATEGORY_UNSPECIFIED, COST, SECURITY, PERFORMANCE, MANAGEABILITY, SUSTAINABILITY, RELIABILITY }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class GoogleCloudRecommenderV1Insight(
	  /** Category being targeted by the insight. */
		category: Option[Schema.GoogleCloudRecommenderV1Insight.CategoryEnum] = None,
	  /** Identifier. Name of the insight. */
		name: Option[String] = None,
	  /** Observation period that led to the insight. The source data used to generate the insight ends at last_refresh_time and begins at (last_refresh_time - observation_period). */
		observationPeriod: Option[String] = None,
	  /** Timestamp of the latest data used to generate the insight. */
		lastRefreshTime: Option[String] = None,
	  /** Fingerprint of the Insight. Provides optimistic locking when updating states. */
		etag: Option[String] = None,
	  /** Insight subtype. Insight content schema will be stable for a given subtype. */
		insightSubtype: Option[String] = None,
	  /** Insight's severity. */
		severity: Option[Schema.GoogleCloudRecommenderV1Insight.SeverityEnum] = None,
	  /** Recommendations derived from this insight. */
		associatedRecommendations: Option[List[Schema.GoogleCloudRecommenderV1InsightRecommendationReference]] = None,
	  /** Fully qualified resource names that this insight is targeting. */
		targetResources: Option[List[String]] = None,
	  /** Free-form human readable summary in English. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** Information state and metadata. */
		stateInfo: Option[Schema.GoogleCloudRecommenderV1InsightStateInfo] = None,
	  /** A struct of custom fields to explain the insight. Example: "grantedPermissionsCount": "1000" */
		content: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudRecommenderV1MarkInsightAcceptedRequest(
	  /** Optional. State properties user wish to include with this state. Full replace of the current state_metadata. */
		stateMetadata: Option[Map[String, String]] = None,
	  /** Required. Fingerprint of the Insight. Provides optimistic locking. */
		etag: Option[String] = None
	)
	
	case class GoogleTypeMoney(
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None,
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None
	)
	
	case class GoogleCloudRecommenderV1SecurityProjection(
	  /** Additional security impact details that is provided by the recommender. */
		details: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudRecommenderV1Impact {
		enum CategoryEnum extends Enum[CategoryEnum] { case CATEGORY_UNSPECIFIED, COST, SECURITY, PERFORMANCE, MANAGEABILITY, SUSTAINABILITY, RELIABILITY }
	}
	case class GoogleCloudRecommenderV1Impact(
	  /** Use with CategoryType.SECURITY */
		securityProjection: Option[Schema.GoogleCloudRecommenderV1SecurityProjection] = None,
	  /** The service that this impact is associated with. */
		service: Option[String] = None,
	  /** If populated, the impact contains multiple components. In this case, the top-level impact contains aggregated values and each component contains per-service details. */
		impactComponents: Option[List[Schema.GoogleCloudRecommenderV1Impact]] = None,
	  /** Use with CategoryType.SUSTAINABILITY */
		sustainabilityProjection: Option[Schema.GoogleCloudRecommenderV1SustainabilityProjection] = None,
	  /** Category that is being targeted. */
		category: Option[Schema.GoogleCloudRecommenderV1Impact.CategoryEnum] = None,
	  /** Use with CategoryType.RELIABILITY */
		reliabilityProjection: Option[Schema.GoogleCloudRecommenderV1ReliabilityProjection] = None,
	  /** Use with CategoryType.COST */
		costProjection: Option[Schema.GoogleCloudRecommenderV1CostProjection] = None
	)
	
	case class GoogleCloudRecommenderV1CostProjection(
	  /** An approximate projection on amount saved or amount incurred. Negative cost units indicate cost savings and positive cost units indicate increase. See google.type.Money documentation for positive/negative units. A user's permissions may affect whether the cost is computed using list prices or custom contract prices. */
		cost: Option[Schema.GoogleTypeMoney] = None,
	  /** Duration for which this cost applies. */
		duration: Option[String] = None,
	  /** The approximate cost savings in the billing account's local currency. */
		costInLocalCurrency: Option[Schema.GoogleTypeMoney] = None
	)
	
	case class GoogleCloudRecommenderV1RecommenderGenerationConfig(
	  /** Parameters for this RecommenderGenerationConfig. These configs can be used by or are applied to all subtypes. */
		params: Option[Map[String, JsValue]] = None
	)
}
