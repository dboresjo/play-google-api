package com.boresjo.play.api.google.firebasedynamiclinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class CreateManagedShortLinkResponse(
	  /** Information about potential warnings on link creation. */
		warning: Option[List[Schema.DynamicLinkWarning]] = None,
	  /** Short Dynamic Link value. e.g. https://abcd.app.goo.gl/wxyz */
		managedShortLink: Option[Schema.ManagedShortLink] = None,
	  /** Preview link to show the link flow chart. (debug info.) */
		previewLink: Option[String] = None
	)
	
	case class CreateShortDynamicLinkRequest(
	  /** Full long Dynamic Link URL with desired query parameters specified. For example, "https://sample.app.goo.gl/?link=http://www.google.com&apn=com.sample", [Learn more](https://firebase.google.com/docs/reference/dynamic-links/link-shortener). */
		longDynamicLink: Option[String] = None,
	  /** Google SDK version. Version takes the form "$major.$minor.$patch" */
		sdkVersion: Option[String] = None,
	  /** Short Dynamic Link suffix. Optional. */
		suffix: Option[Schema.Suffix] = None,
	  /** Information about the Dynamic Link to be shortened. [Learn more](https://firebase.google.com/docs/reference/dynamic-links/link-shortener). */
		dynamicLinkInfo: Option[Schema.DynamicLinkInfo] = None
	)
	
	case class CreateManagedShortLinkRequest(
	  /** Short Dynamic Link suffix. Optional. */
		suffix: Option[Schema.Suffix] = None,
	  /** Full long Dynamic Link URL with desired query parameters specified. For example, "https://sample.app.goo.gl/?link=http://www.google.com&apn=com.sample", [Learn more](https://firebase.google.com/docs/reference/dynamic-links/link-shortener). */
		longDynamicLink: Option[String] = None,
	  /** Google SDK version. Version takes the form "$major.$minor.$patch" */
		sdkVersion: Option[String] = None,
	  /** Link name to associate with the link. It's used for marketer to identify manually-created links in the Firebase console (https://console.firebase.google.com/). Links must be named to be tracked. */
		name: Option[String] = None,
	  /** Information about the Dynamic Link to be shortened. [Learn more](https://firebase.google.com/docs/reference/dynamic-links/link-shortener). */
		dynamicLinkInfo: Option[Schema.DynamicLinkInfo] = None
	)
	
	case class GetIosReopenAttributionRequest(
	  /** Google SDK version. Version takes the form "$major.$minor.$patch" */
		sdkVersion: Option[String] = None,
	  /** FDL link to be verified from an app universal link open. The FDL link can be one of: 1) short FDL. e.g. .page.link/, or 2) long FDL. e.g. .page.link/?{query params}, or 3) Invite FDL. e.g. .page.link/i/ */
		requestedLink: Option[String] = None,
	  /** APP bundle ID. */
		bundleId: Option[String] = None
	)
	
	case class NavigationInfo(
	  /** If this option is on, FDL click will be forced to redirect rather than show an interstitial page. */
		enableForcedRedirect: Option[Boolean] = None
	)
	
	case class ITunesConnectAnalytics(
	  /** iTune media types, including music, podcasts, audiobooks and so on. */
		mt: Option[String] = None,
	  /** Affiliate token used to create affiliate-coded links. */
		at: Option[String] = None,
	  /** Provider token that enables analytics for Dynamic Links from within iTunes Connect. */
		pt: Option[String] = None,
	  /** Campaign text that developers can optionally add to any link in order to track sales from a specific marketing campaign. */
		ct: Option[String] = None
	)
	
	object GetIosPostInstallAttributionResponse {
		enum RequestIpVersionEnum extends Enum[RequestIpVersionEnum] { case UNKNOWN_IP_VERSION, IP_V4, IP_V6 }
		enum AttributionConfidenceEnum extends Enum[AttributionConfidenceEnum] { case UNKNOWN_ATTRIBUTION_CONFIDENCE, WEAK, DEFAULT, UNIQUE }
	}
	case class GetIosPostInstallAttributionResponse(
	  /** Which IP version the request was made from. */
		requestIpVersion: Option[Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum] = None,
	  /** Scion content value to be propagated by iSDK to Scion at app-reopen. */
		utmContent: Option[String] = None,
	  /** Scion medium value to be propagated by iSDK to Scion at post-install. */
		utmMedium: Option[String] = None,
	  /** Instruction for iSDK to attemmpt to perform strong match. For instance, if browser does not support/allow cookie or outside of support browsers, this will be false. */
		isStrongMatchExecutable: Option[Boolean] = None,
	  /** The entire FDL, expanded from a short link. It is the same as the requested_link, if it is long. Parameters from this should not be used directly (ie: server can default utm_[campaign|medium|source] to a value when requested_link lack them, server determine the best fallback_link when requested_link specifies >1 fallback links). */
		resolvedLink: Option[String] = None,
	  /** The confidence of the returned attribution. */
		attributionConfidence: Option[Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum] = None,
	  /** The minimum version for app, specified by dev through ?imv= parameter. Return to iSDK to allow app to evaluate if current version meets this. */
		appMinimumVersion: Option[String] = None,
	  /** Scion campaign value to be propagated by iSDK to Scion at post-install. */
		utmCampaign: Option[String] = None,
	  /** Entire FDL (short or long) attributed post-install via one of several techniques (device heuristics, copy unique). */
		requestedLink: Option[String] = None,
	  /** The deep-link attributed post-install via one of several techniques (device heuristics, copy unique). */
		deepLink: Option[String] = None,
	  /** Describes why match failed, ie: "discarded due to low confidence". This message will be publicly visible. */
		matchMessage: Option[String] = None,
	  /** Invitation ID attributed post-install via one of several techniques (device heuristics, copy unique). */
		invitationId: Option[String] = None,
	  /** The link to navigate to update the app if min version is not met. This is either (in order): 1) fallback link (from ?ifl= parameter, if specified by developer) or 2) AppStore URL (from ?isi= parameter, if specified), or 3) the payload link (from required link= parameter). */
		fallbackLink: Option[String] = None,
	  /** User-agent specific custom-scheme URIs for iSDK to open. This will be set according to the user-agent tha the click was originally made in. There is no Safari-equivalent custom-scheme open URLs. ie: googlechrome://www.example.com ie: firefox://open-url?url=http://www.example.com ie: opera-http://example.com */
		externalBrowserDestinationLink: Option[String] = None,
	  /** Scion source value to be propagated by iSDK to Scion at post-install. */
		utmSource: Option[String] = None,
	  /** Scion term value to be propagated by iSDK to Scion at app-reopen. */
		utmTerm: Option[String] = None
	)
	
	case class GetIosReopenAttributionResponse(
	  /** Scion content value to be propagated by iSDK to Scion at app-reopen. */
		utmContent: Option[String] = None,
	  /** FDL input value of the "&imv=" parameter, minimum app version to be returned to Google Firebase SDK running on iOS-9. */
		iosMinAppVersion: Option[String] = None,
	  /** Optional warnings associated this API request. */
		warning: Option[List[Schema.DynamicLinkWarning]] = None,
	  /** Scion term value to be propagated by iSDK to Scion at app-reopen. */
		utmTerm: Option[String] = None,
	  /** Scion source value to be propagated by iSDK to Scion at app-reopen. */
		utmSource: Option[String] = None,
	  /** Optional invitation ID, for only invite typed requested FDL links. */
		invitationId: Option[String] = None,
	  /** Scion medium value to be propagated by iSDK to Scion at app-reopen. */
		utmMedium: Option[String] = None,
	  /** The entire FDL, expanded from a short link. It is the same as the requested_link, if it is long. */
		resolvedLink: Option[String] = None,
	  /** Scion campaign value to be propagated by iSDK to Scion at app-reopen. */
		utmCampaign: Option[String] = None,
	  /** The deep-link attributed the app universal link open. For both regular FDL links and invite FDL links. */
		deepLink: Option[String] = None
	)
	
	case class IosInfo(
	  /** iOS App Store ID. */
		iosAppStoreId: Option[String] = None,
	  /** If specified, this overrides the ios_fallback_link value on iPads. */
		iosIpadFallbackLink: Option[String] = None,
	  /** Custom (destination) scheme to use for iOS. By default, we’ll use the bundle ID as the custom scheme. Developer can override this behavior using this param. */
		iosCustomScheme: Option[String] = None,
	  /** iOS bundle ID of the app. */
		iosBundleId: Option[String] = None,
	  /** iOS minimum version. */
		iosMinimumVersion: Option[String] = None,
	  /** iPad bundle ID of the app. */
		iosIpadBundleId: Option[String] = None,
	  /** Link to open on iOS if the app is not installed. */
		iosFallbackLink: Option[String] = None
	)
	
	case class DynamicLinkStats(
	  /** Optional warnings associated this API request. */
		warnings: Option[List[Schema.DynamicLinkWarning]] = None,
	  /** Dynamic Link event stats. */
		linkEventStats: Option[List[Schema.DynamicLinkEventStat]] = None
	)
	
	object ManagedShortLink {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case UNSPECIFIED_VISIBILITY, UNARCHIVED, ARCHIVED, NEVER_SHOWN }
		enum FlaggedAttributeEnum extends Enum[FlaggedAttributeEnum] { case UNSPECIFIED_ATTRIBUTE, SPAM }
	}
	case class ManagedShortLink(
	  /** Link name defined by the creator. Required. */
		linkName: Option[String] = None,
	  /** Visibility status of link. */
		visibility: Option[Schema.ManagedShortLink.VisibilityEnum] = None,
	  /** Short durable link url, for example, "https://sample.app.goo.gl/xyz123". Required. */
		link: Option[String] = None,
	  /** Attributes that have been flagged about this short url. */
		flaggedAttribute: Option[List[Schema.ManagedShortLink.FlaggedAttributeEnum]] = None,
	  /** Full Dyamic Link info */
		info: Option[Schema.DynamicLinkInfo] = None,
	  /** Creation timestamp of the short link. */
		creationTime: Option[String] = None
	)
	
	case class AnalyticsInfo(
	  /** iTunes Connect App Analytics. */
		itunesConnectAnalytics: Option[Schema.ITunesConnectAnalytics] = None,
	  /** Google Play Campaign Measurements. */
		googlePlayAnalytics: Option[Schema.GooglePlayAnalytics] = None
	)
	
	case class AndroidInfo(
	  /** If specified, this overrides the ‘link’ parameter on Android. */
		androidLink: Option[String] = None,
	  /** Link to open on Android if the app is not installed. */
		androidFallbackLink: Option[String] = None,
	  /** Android package name of the app. */
		androidPackageName: Option[String] = None,
	  /** Minimum version code for the Android app. If the installed app’s version code is lower, then the user is taken to the Play Store. */
		androidMinPackageVersionCode: Option[String] = None
	)
	
	object DynamicLinkWarning {
		enum WarningCodeEnum extends Enum[WarningCodeEnum] { case CODE_UNSPECIFIED, NOT_IN_PROJECT_ANDROID_PACKAGE_NAME, NOT_INTEGER_ANDROID_PACKAGE_MIN_VERSION, UNNECESSARY_ANDROID_PACKAGE_MIN_VERSION, NOT_URI_ANDROID_LINK, UNNECESSARY_ANDROID_LINK, NOT_URI_ANDROID_FALLBACK_LINK, BAD_URI_SCHEME_ANDROID_FALLBACK_LINK, NOT_IN_PROJECT_IOS_BUNDLE_ID, NOT_IN_PROJECT_IPAD_BUNDLE_ID, UNNECESSARY_IOS_URL_SCHEME, NOT_NUMERIC_IOS_APP_STORE_ID, UNNECESSARY_IOS_APP_STORE_ID, NOT_URI_IOS_FALLBACK_LINK, BAD_URI_SCHEME_IOS_FALLBACK_LINK, NOT_URI_IPAD_FALLBACK_LINK, BAD_URI_SCHEME_IPAD_FALLBACK_LINK, BAD_DEBUG_PARAM, BAD_AD_PARAM, DEPRECATED_PARAM, UNRECOGNIZED_PARAM, TOO_LONG_PARAM, NOT_URI_SOCIAL_IMAGE_LINK, BAD_URI_SCHEME_SOCIAL_IMAGE_LINK, NOT_URI_SOCIAL_URL, BAD_URI_SCHEME_SOCIAL_URL, LINK_LENGTH_TOO_LONG, LINK_WITH_FRAGMENTS, NOT_MATCHING_IOS_BUNDLE_ID_AND_STORE_ID, API_DEPRECATED }
	}
	case class DynamicLinkWarning(
	  /** The document describing the warning, and helps resolve. */
		warningDocumentLink: Option[String] = None,
	  /** The warning message to help developers improve their requests. */
		warningMessage: Option[String] = None,
	  /** The warning code. */
		warningCode: Option[Schema.DynamicLinkWarning.WarningCodeEnum] = None
	)
	
	case class DesktopInfo(
	  /** Link to open on desktop. */
		desktopFallbackLink: Option[String] = None
	)
	
	object GetIosPostInstallAttributionRequest {
		enum RetrievalMethodEnum extends Enum[RetrievalMethodEnum] { case UNKNOWN_PAYLOAD_RETRIEVAL_METHOD, IMPLICIT_WEAK_MATCH, EXPLICIT_WEAK_MATCH, EXPLICIT_STRONG_AFTER_WEAK_MATCH }
		enum VisualStyleEnum extends Enum[VisualStyleEnum] { case UNKNOWN_VISUAL_STYLE, DEFAULT_STYLE, CUSTOM_STYLE }
	}
	case class GetIosPostInstallAttributionRequest(
	  /** Device information. */
		device: Option[Schema.DeviceInfo] = None,
	  /** iOS version, ie: 9.3.5. Consider adding "build". */
		iosVersion: Option[String] = None,
	  /** App post install attribution retrieval information. Disambiguates mechanism (iSDK or developer invoked) to retrieve payload from clicked link. */
		retrievalMethod: Option[Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum] = None,
	  /** Google SDK version. Version takes the form "$major.$minor.$patch" */
		sdkVersion: Option[String] = None,
	  /** Strong match page information. Disambiguates between default UI and custom page to present when strong match succeeds/fails to find cookie. */
		visualStyle: Option[Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum] = None,
	  /** APP bundle ID. */
		bundleId: Option[String] = None,
	  /** Possible unique matched link that server need to check before performing device heuristics match. If passed link is short server need to expand the link. If link is long server need to vslidate the link. */
		uniqueMatchLinkToCheck: Option[String] = None,
	  /** App installation epoch time (https://en.wikipedia.org/wiki/Unix_time). This is a client signal for a more accurate weak match. */
		appInstallationTime: Option[String] = None
	)
	
	object Suffix {
		enum OptionEnum extends Enum[OptionEnum] { case OPTION_UNSPECIFIED, UNGUESSABLE, SHORT, CUSTOM }
	}
	case class Suffix(
	  /** Suffix option. */
		option: Option[Schema.Suffix.OptionEnum] = None,
	  /** Only applies to Option.CUSTOM. */
		customSuffix: Option[String] = None
	)
	
	case class DeviceInfo(
	  /** Device timezone setting. */
		timezone: Option[String] = None,
	  /** Device display resolution width. */
		screenResolutionWidth: Option[String] = None,
	  /** Device language code raw setting. iOS does returns language code in different format than iOS WebView. For example WebView returns en_US, but iOS returns en-US. Field below will return raw value returned by iOS. */
		languageCodeRaw: Option[String] = None,
	  /** Device display resolution height. */
		screenResolutionHeight: Option[String] = None,
	  /** Device language code setting obtained by executing JavaScript code in WebView. */
		languageCodeFromWebview: Option[String] = None,
	  /** Device language code setting. */
		languageCode: Option[String] = None,
	  /** Device model name. */
		deviceModelName: Option[String] = None
	)
	
	case class GooglePlayAnalytics(
	  /** Campaign medium; used to identify a medium such as email or cost-per-click. */
		utmMedium: Option[String] = None,
	  /** Campaign source; used to identify a search engine, newsletter, or other source. */
		utmSource: Option[String] = None,
	  /** Deprecated; FDL SDK does not process nor log it. */
		gclid: Option[String] = None,
	  /** Campaign name; used for keyword analysis to identify a specific product promotion or strategic campaign. */
		utmCampaign: Option[String] = None,
	  /** Campaign content; used for A/B testing and content-targeted ads to differentiate ads or links that point to the same URL. */
		utmContent: Option[String] = None,
	  /** Campaign term; used with paid search to supply the keywords for ads. */
		utmTerm: Option[String] = None
	)
	
	case class DynamicLinkInfo(
	  /** Desktop related information. See desktop related parameters in the [documentation](https://firebase.google.com/docs/dynamic-links/create-manually). */
		desktopInfo: Option[Schema.DesktopInfo] = None,
	  /** Parameters used for tracking. See all tracking parameters in the [documentation](https://firebase.google.com/docs/dynamic-links/create-manually). */
		analyticsInfo: Option[Schema.AnalyticsInfo] = None,
	  /** Android related information. See Android related parameters in the [documentation](https://firebase.google.com/docs/dynamic-links/create-manually). */
		androidInfo: Option[Schema.AndroidInfo] = None,
	  /** The link your app will open, You can specify any URL your app can handle. This link must be a well-formatted URL, be properly URL-encoded, and use the HTTP or HTTPS scheme. See 'link' parameters in the [documentation](https://firebase.google.com/docs/dynamic-links/create-manually). Required. */
		link: Option[String] = None,
	  /** Information of navigation behavior of a Firebase Dynamic Links. */
		navigationInfo: Option[Schema.NavigationInfo] = None,
	  /** Parameters for social meta tag params. Used to set meta tag data for link previews on social sites. */
		socialMetaTagInfo: Option[Schema.SocialMetaTagInfo] = None,
	  /** E.g. https://maps.app.goo.gl, https://maps.page.link, https://g.co/maps More examples can be found in description of getNormalizedUriPrefix in j/c/g/firebase/dynamiclinks/uri/DdlDomain.java Will fallback to dynamic_link_domain is this field is missing */
		domainUriPrefix: Option[String] = None,
	  /** Dynamic Links domain that the project owns, e.g. abcd.app.goo.gl [Learn more](https://firebase.google.com/docs/dynamic-links/android/receive) on how to set up Dynamic Link domain associated with your Firebase project. Required if missing domain_uri_prefix. */
		dynamicLinkDomain: Option[String] = None,
	  /** iOS related information. See iOS related parameters in the [documentation](https://firebase.google.com/docs/dynamic-links/create-manually). */
		iosInfo: Option[Schema.IosInfo] = None
	)
	
	object DynamicLinkEventStat {
		enum PlatformEnum extends Enum[PlatformEnum] { case DYNAMIC_LINK_PLATFORM_UNSPECIFIED, ANDROID, IOS, DESKTOP, OTHER }
		enum EventEnum extends Enum[EventEnum] { case DYNAMIC_LINK_EVENT_UNSPECIFIED, CLICK, REDIRECT, APP_INSTALL, APP_FIRST_OPEN, APP_RE_OPEN }
	}
	case class DynamicLinkEventStat(
	  /** Requested platform. */
		platform: Option[Schema.DynamicLinkEventStat.PlatformEnum] = None,
	  /** The number of times this event occurred. */
		count: Option[String] = None,
	  /** Link event. */
		event: Option[Schema.DynamicLinkEventStat.EventEnum] = None
	)
	
	case class SocialMetaTagInfo(
	  /** A short description of the link. Optional. */
		socialDescription: Option[String] = None,
	  /** An image url string. Optional. */
		socialImageLink: Option[String] = None,
	  /** Title to be displayed. Optional. */
		socialTitle: Option[String] = None
	)
	
	case class CreateShortDynamicLinkResponse(
	  /** Short Dynamic Link value. e.g. https://abcd.app.goo.gl/wxyz */
		shortLink: Option[String] = None,
	  /** Preview link to show the link flow chart. (debug info.) */
		previewLink: Option[String] = None,
	  /** Information about potential warnings on link creation. */
		warning: Option[List[Schema.DynamicLinkWarning]] = None
	)
}
