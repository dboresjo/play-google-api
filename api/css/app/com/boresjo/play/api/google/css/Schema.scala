package com.boresjo.play.api.google.css

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListAccountLabelsResponse(
	  /** The labels from the specified account. */
		accountLabels: Option[List[Schema.AccountLabel]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object AccountLabel {
		enum LabelTypeEnum extends Enum[LabelTypeEnum] { case LABEL_TYPE_UNSPECIFIED, MANUAL, AUTOMATIC }
	}
	case class AccountLabel(
	  /** The resource name of the label. Format: accounts/{account}/labels/{label} */
		name: Option[String] = None,
	  /** Output only. The ID of the label. */
		labelId: Option[String] = None,
	  /** Output only. The ID of account this label belongs to. */
		accountId: Option[String] = None,
	  /** The display name of this label. */
		displayName: Option[String] = None,
	  /** The description of this label. */
		description: Option[String] = None,
	  /** Output only. The type of this label. */
		labelType: Option[Schema.AccountLabel.LabelTypeEnum] = None
	)
	
	case class Empty(
	
	)
	
	case class ListChildAccountsResponse(
	  /** The CSS/MC accounts returned for the specified CSS parent account. */
		accounts: Option[List[Schema.Account]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Account {
		enum AccountTypeEnum extends Enum[AccountTypeEnum] { case ACCOUNT_TYPE_UNSPECIFIED, CSS_GROUP, CSS_DOMAIN, MC_PRIMARY_CSS_MCA, MC_CSS_MCA, MC_MARKETPLACE_MCA, MC_OTHER_MCA, MC_STANDALONE, MC_MCA_SUBACCOUNT }
	}
	case class Account(
	  /** The label resource name. Format: accounts/{account} */
		name: Option[String] = None,
	  /** Output only. Immutable. The CSS/MC account's full name. */
		fullName: Option[String] = None,
	  /** The CSS/MC account's short display name. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. The CSS/MC account's homepage. */
		homepageUri: Option[String] = None,
	  /** The CSS/MC account's parent resource. CSS group for CSS domains; CSS domain for MC accounts. Returned only if the user has access to the parent account. */
		parent: Option[String] = None,
	  /** Manually created label IDs assigned to the CSS/MC account by a CSS parent account. */
		labelIds: Option[List[String]] = None,
	  /** Automatically created label IDs assigned to the MC account by CSS Center. */
		automaticLabelIds: Option[List[String]] = None,
	  /** Output only. The type of this account. */
		accountType: Option[Schema.Account.AccountTypeEnum] = None
	)
	
	case class UpdateAccountLabelsRequest(
	  /** The list of label IDs to overwrite the existing account label IDs. If the list is empty, all currently assigned label IDs will be deleted. */
		labelIds: Option[List[String]] = None,
	  /** Optional. Only required when updating MC account labels. The CSS domain that is the parent resource of the MC account. Format: accounts/{account} */
		parent: Option[String] = None
	)
	
	case class CssProductInput(
	  /** The name of the CSS Product input. Format: `accounts/{account}/cssProductInputs/{css_product_input}` */
		name: Option[String] = None,
	  /** Output only. The name of the processed CSS Product. Format: `accounts/{account}/cssProducts/{css_product}` " */
		finalName: Option[String] = None,
	  /** Required. Your unique identifier for the CSS Product. This is the same for the CSS Product input and processed CSS Product. We only allow ids with alphanumerics, underscores and dashes. See the [products feed specification](https://support.google.com/merchants/answer/188494#id) for details. */
		rawProvidedId: Option[String] = None,
	  /** Required. The two-letter [ISO 639-1](http://en.wikipedia.org/wiki/ISO_639-1) language code for the CSS Product. */
		contentLanguage: Option[String] = None,
	  /** Required. The [feed label](https://developers.google.com/shopping-content/guides/products/feed-labels) for the CSS Product. Feed Label is synonymous to "target country" and hence should always be a valid region code. For example: 'DE' for Germany, 'FR' for France. */
		feedLabel: Option[String] = None,
	  /** Represents the existing version (freshness) of the CSS Product, which can be used to preserve the right order when multiple updates are done at the same time. This field must not be set to the future time. If set, the update is prevented if a newer version of the item already exists in our system (that is the last update time of the existing CSS products is later than the freshness time set in the update). If the update happens, the last update time is then set to this freshness time. If not set, the update will not be prevented and the last update time will default to when this request was received by the CSS API. If the operation is prevented, the aborted exception will be thrown. */
		freshnessTime: Option[String] = None,
	  /** A list of CSS Product attributes. */
		attributes: Option[Schema.Attributes] = None,
	  /** A list of custom (CSS-provided) attributes. It can also be used for submitting any attribute of the feed specification in its generic form (for example: `{ "name": "size type", "value": "regular" }`). This is useful for submitting attributes not explicitly exposed by the API, such as additional attributes used for Buy on Google. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None
	)
	
	case class Attributes(
	  /** URL directly linking to your the Product Detail Page of the CSS. */
		cppLink: Option[String] = None,
	  /** URL for the mobile-optimized version of the Product Detail Page of the CSS. */
		cppMobileLink: Option[String] = None,
	  /** Allows advertisers to override the item URL when the product is shown within the context of Product Ads. */
		cppAdsRedirect: Option[String] = None,
	  /** Low Price of the aggregate offer. */
		lowPrice: Option[Schema.Price] = None,
	  /** High Price of the aggregate offer. */
		highPrice: Option[Schema.Price] = None,
	  /** The number of aggregate offers. */
		numberOfOffers: Option[String] = None,
	  /** Condition of the headline offer. */
		headlineOfferCondition: Option[String] = None,
	  /** Headline Price of the aggregate offer. */
		headlineOfferPrice: Option[Schema.Price] = None,
	  /** Link to the headline offer. */
		headlineOfferLink: Option[String] = None,
	  /** Mobile Link to the headline offer. */
		headlineOfferMobileLink: Option[String] = None,
	  /** Headline Price of the aggregate offer. */
		headlineOfferShippingPrice: Option[Schema.Price] = None,
	  /** Title of the item. */
		title: Option[String] = None,
	  /** URL of an image of the item. */
		imageLink: Option[String] = None,
	  /** Additional URL of images of the item. */
		additionalImageLinks: Option[List[String]] = None,
	  /** Description of the item. */
		description: Option[String] = None,
	  /** Product Related Attributes.[14-36] Brand of the item. */
		brand: Option[String] = None,
	  /** Manufacturer Part Number ([MPN](https://support.google.com/merchants/answer/188494#mpn)) of the item. */
		mpn: Option[String] = None,
	  /** Global Trade Item Number ([GTIN](https://support.google.com/merchants/answer/188494#gtin)) of the item. */
		gtin: Option[String] = None,
	  /** Categories of the item (formatted as in [products data specification](https://support.google.com/merchants/answer/6324406)). */
		productTypes: Option[List[String]] = None,
	  /** Google's category of the item (see [Google product taxonomy](https://support.google.com/merchants/answer/1705911)). When querying products, this field will contain the user provided value. There is currently no way to get back the auto assigned google product categories through the API. */
		googleProductCategory: Option[String] = None,
	  /** Set to true if the item is targeted towards adults. */
		adult: Option[Boolean] = None,
	  /** The number of identical products in a merchant-defined multipack. */
		multipack: Option[String] = None,
	  /** Whether the item is a merchant-defined bundle. A bundle is a custom grouping of different products sold by a merchant for a single price. */
		isBundle: Option[Boolean] = None,
	  /** Target age group of the item. */
		ageGroup: Option[String] = None,
	  /** Color of the item. */
		color: Option[String] = None,
	  /** Target gender of the item. */
		gender: Option[String] = None,
	  /** The material of which the item is made. */
		material: Option[String] = None,
	  /** The item's pattern (e.g. polka dots). */
		pattern: Option[String] = None,
	  /** Size of the item. Only one value is allowed. For variants with different sizes, insert a separate product for each size with the same `itemGroupId` value (see [https://support.google.com/merchants/answer/6324492](size definition)). */
		size: Option[String] = None,
	  /** System in which the size is specified. Recommended for apparel items. */
		sizeSystem: Option[String] = None,
	  /** The cut of the item. It can be used to represent combined size types for apparel items. Maximum two of size types can be provided (see [https://support.google.com/merchants/answer/6324497](size type)). */
		sizeTypes: Option[List[String]] = None,
	  /** Shared identifier for all variants of the same product. */
		itemGroupId: Option[String] = None,
	  /** Technical specification or additional product details. */
		productDetails: Option[List[Schema.ProductDetail]] = None,
	  /** The weight of the product in the units provided. The value must be between 0 (exclusive) and 2000 (inclusive). */
		productWeight: Option[Schema.ProductWeight] = None,
	  /** The length of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productLength: Option[Schema.ProductDimension] = None,
	  /** The width of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productWidth: Option[Schema.ProductDimension] = None,
	  /** The height of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productHeight: Option[Schema.ProductDimension] = None,
	  /** Bullet points describing the most relevant highlights of a product. */
		productHighlights: Option[List[String]] = None,
	  /** A list of certificates claimed by the CSS for the given product. */
		certifications: Option[List[Schema.Certification]] = None,
	  /** Date on which the item should expire, as specified upon insertion, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. The actual expiration date is exposed in `productstatuses` as [googleExpirationDate](https://support.google.com/merchants/answer/6324499) and might be earlier if `expirationDate` is too far in the future. Note: It may take 2+ days from the expiration date for the item to actually get deleted. */
		expirationDate: Option[String] = None,
	  /** The list of destinations to include for this target (corresponds to checked check boxes in Merchant Center). Default destinations are always included unless provided in `excludedDestinations`. */
		includedDestinations: Option[List[String]] = None,
	  /** The list of destinations to exclude for this target (corresponds to unchecked check boxes in Merchant Center). */
		excludedDestinations: Option[List[String]] = None,
	  /** Publication of this item will be temporarily paused. */
		pause: Option[String] = None,
	  /** Custom label 0 for custom grouping of items in a Shopping campaign. */
		customLabel0: Option[String] = None,
	  /** Custom label 1 for custom grouping of items in a Shopping campaign. */
		customLabel1: Option[String] = None,
	  /** Custom label 2 for custom grouping of items in a Shopping campaign. */
		customLabel2: Option[String] = None,
	  /** Custom label 3 for custom grouping of items in a Shopping campaign. */
		customLabel3: Option[String] = None,
	  /** Custom label 4 for custom grouping of items in a Shopping campaign. */
		customLabel4: Option[String] = None,
	  /** Number and amount of installments to pay for an item. */
		headlineOfferInstallment: Option[Schema.HeadlineOfferInstallment] = None,
	  /** Number of periods (months or years) and amount of payment per period for an item with an associated subscription contract. */
		headlineOfferSubscriptionCost: Option[Schema.HeadlineOfferSubscriptionCost] = None
	)
	
	case class Price(
	  /** The price represented as a number in micros (1 million micros is an equivalent to one's currency standard unit, for example, 1 USD = 1000000 micros). */
		amountMicros: Option[String] = None,
	  /** The currency of the price using three-letter acronyms according to [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217). */
		currencyCode: Option[String] = None
	)
	
	case class ProductDetail(
	  /** The section header used to group a set of product details. */
		sectionName: Option[String] = None,
	  /** The name of the product detail. */
		attributeName: Option[String] = None,
	  /** The value of the product detail. */
		attributeValue: Option[String] = None
	)
	
	case class ProductWeight(
	  /** Required. The weight represented as a number. The weight can have a maximum precision of four decimal places. */
		value: Option[BigDecimal] = None,
	  /** Required. The weight unit. Acceptable values are: &#42; "`g`" &#42; "`kg`" &#42; "`oz`" &#42; "`lb`" */
		unit: Option[String] = None
	)
	
	case class ProductDimension(
	  /** Required. The dimension value represented as a number. The value can have a maximum precision of four decimal places. */
		value: Option[BigDecimal] = None,
	  /** Required. The dimension units. Acceptable values are: &#42; "`in`" &#42; "`cm`" */
		unit: Option[String] = None
	)
	
	case class Certification(
	  /** The name of the certification. At this time, the most common value is "EPREL", which represents energy efficiency certifications in the EU European Registry for Energy Labeling (EPREL) database. */
		name: Option[String] = None,
	  /** The authority or certification body responsible for issuing the certification. At this time, the most common value is "EC" or “European_Commission” for energy labels in the EU. */
		authority: Option[String] = None,
	  /** The code of the certification. For example, for the EPREL certificate with the link https://eprel.ec.europa.eu/screen/product/dishwashers2019/123456 the code is 123456. The code is required for European Energy Labels. */
		code: Option[String] = None
	)
	
	case class HeadlineOfferInstallment(
	  /** The number of installments the buyer has to pay. */
		months: Option[String] = None,
	  /** The amount the buyer has to pay per month. */
		amount: Option[Schema.Price] = None,
	  /** The up-front down payment amount the buyer has to pay. */
		downpayment: Option[Schema.Price] = None
	)
	
	object HeadlineOfferSubscriptionCost {
		enum PeriodEnum extends Enum[PeriodEnum] { case SUBSCRIPTION_PERIOD_UNSPECIFIED, MONTH, YEAR }
	}
	case class HeadlineOfferSubscriptionCost(
	  /** The type of subscription period. Supported values are: &#42; "`month`" &#42; "`year`" */
		period: Option[Schema.HeadlineOfferSubscriptionCost.PeriodEnum] = None,
	  /** The number of subscription periods the buyer has to pay. */
		periodLength: Option[String] = None,
	  /** The amount the buyer has to pay per subscription period. */
		amount: Option[Schema.Price] = None
	)
	
	case class CustomAttribute(
	  /** The name of the attribute. */
		name: Option[String] = None,
	  /** The value of the attribute. If `value` is not empty, `group_values` must be empty. */
		value: Option[String] = None,
	  /** Subattributes within this attribute group. If `group_values` is not empty, `value` must be empty. */
		groupValues: Option[List[Schema.CustomAttribute]] = None
	)
	
	case class CssProduct(
	  /** The name of the CSS Product. Format: `"accounts/{account}/cssProducts/{css_product}"` */
		name: Option[String] = None,
	  /** Output only. Your unique raw identifier for the product. */
		rawProvidedId: Option[String] = None,
	  /** Output only. The two-letter [ISO 639-1](http://en.wikipedia.org/wiki/ISO_639-1) language code for the product. */
		contentLanguage: Option[String] = None,
	  /** Output only. The feed label for the product. */
		feedLabel: Option[String] = None,
	  /** Output only. A list of product attributes. */
		attributes: Option[Schema.Attributes] = None,
	  /** Output only. A list of custom (CSS-provided) attributes. It can also be used to submit any attribute of the feed specification in its generic form (for example, `{ "name": "size type", "value": "regular" }`). This is useful for submitting attributes not explicitly exposed by the API, such as additional attributes used for Buy on Google. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None,
	  /** Output only. The status of a product, data validation issues, that is, information about a product computed asynchronously. */
		cssProductStatus: Option[Schema.CssProductStatus] = None
	)
	
	case class CssProductStatus(
	  /** The intended destinations for the product. */
		destinationStatuses: Option[List[Schema.DestinationStatus]] = None,
	  /** A list of all issues associated with the product. */
		itemLevelIssues: Option[List[Schema.ItemLevelIssue]] = None,
	  /** Date on which the item has been created, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		creationDate: Option[String] = None,
	  /** Date on which the item has been last updated, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		lastUpdateDate: Option[String] = None,
	  /** Date on which the item expires, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		googleExpirationDate: Option[String] = None
	)
	
	case class DestinationStatus(
	  /** The name of the destination */
		destination: Option[String] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the aggregate offer is approved. */
		approvedCountries: Option[List[String]] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the aggregate offer is pending approval. */
		pendingCountries: Option[List[String]] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the aggregate offer is disapproved. */
		disapprovedCountries: Option[List[String]] = None
	)
	
	case class ItemLevelIssue(
	  /** The error code of the issue. */
		code: Option[String] = None,
	  /** How this issue affects serving of the aggregate offer. */
		servability: Option[String] = None,
	  /** Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** The attribute's name, if the issue is caused by a single attribute. */
		attribute: Option[String] = None,
	  /** The destination the issue applies to. */
		destination: Option[String] = None,
	  /** A short issue description in English. */
		description: Option[String] = None,
	  /** A detailed issue description in English. */
		detail: Option[String] = None,
	  /** The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where issue applies to the aggregate offer. */
		applicableCountries: Option[List[String]] = None
	)
	
	case class ListCssProductsResponse(
	  /** The processed CSS products from the specified account. These are your processed CSS products after applying rules and supplemental feeds. */
		cssProducts: Option[List[Schema.CssProduct]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
}
