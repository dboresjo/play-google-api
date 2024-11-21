package com.boresjo.play.api.google.manufacturers

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListProductsResponse(
	  /** List of the products. */
		products: Option[List[Schema.Product]] = None,
	  /** The token for the retrieval of the next page of product statuses. */
		nextPageToken: Option[String] = None
	)
	
	case class Product(
	  /** Parent ID in the format `accounts/{account_id}`. `account_id` - The ID of the Manufacturer Center account. */
		parent: Option[String] = None,
	  /** Name in the format `{target_country}:{content_language}:{product_id}`. `target_country` - The target country of the product as a CLDR territory code (for example, US). `content_language` - The content language of the product as a two-letter ISO 639-1 language code (for example, en). `product_id` - The ID of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#id. */
		name: Option[String] = None,
	  /** The target country of the product as a CLDR territory code (for example, US). */
		targetCountry: Option[String] = None,
	  /** Optional. The feed label for the product. */
		feedLabel: Option[String] = None,
	  /** The content language of the product as a two-letter ISO 639-1 language code (for example, en). */
		contentLanguage: Option[String] = None,
	  /** The ID of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#id. */
		productId: Option[String] = None,
	  /** Attributes of the product uploaded to the Manufacturer Center. Manually edited attributes are taken into account. */
		attributes: Option[Schema.Attributes] = None,
	  /** A server-generated list of issues associated with the product. */
		issues: Option[List[Schema.Issue]] = None,
	  /** The status of the destinations. */
		destinationStatuses: Option[List[Schema.DestinationStatus]] = None
	)
	
	case class Attributes(
	  /** The Global Trade Item Number (GTIN) of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#gtin. */
		gtin: Option[List[String]] = None,
	  /** The Manufacturer Part Number (MPN) of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#mpn. */
		mpn: Option[String] = None,
	  /** The title of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#title. */
		title: Option[String] = None,
	  /** The brand name of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#brand. */
		brand: Option[String] = None,
	  /** The canonical name of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#productname. */
		productName: Option[String] = None,
	  /** The name of the group of products related to the product. For more information, see https://support.google.com/manufacturers/answer/6124116#productline. */
		productLine: Option[String] = None,
	  /** The type or category of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#producttype. */
		productType: Option[List[String]] = None,
	  /** The description of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#description. */
		description: Option[String] = None,
	  /** The rich format description of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#featuredesc. */
		featureDescription: Option[List[Schema.FeatureDescription]] = None,
	  /** The image of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#image. */
		imageLink: Option[Schema.Image] = None,
	  /** The additional images of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#addlimage. */
		additionalImageLink: Option[List[Schema.Image]] = None,
	  /** The videos of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#video. */
		videoLink: Option[List[String]] = None,
	  /** The URL of the detail page of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#productpage. */
		productPageUrl: Option[String] = None,
	  /** The disclosure date of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#disclosure. */
		disclosureDate: Option[String] = None,
	  /** The release date of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#release. */
		releaseDate: Option[String] = None,
	  /** The suggested retail price (MSRP) of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#price. */
		suggestedRetailPrice: Option[Schema.Price] = None,
	  /** The details of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#productdetail. */
		productDetail: Option[List[Schema.ProductDetail]] = None,
	  /** The item group id of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#itemgroupid. */
		itemGroupId: Option[String] = None,
	  /** The scent of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#scent. */
		scent: Option[String] = None,
	  /** The flavor of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#flavor. */
		flavor: Option[String] = None,
	  /** The format of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#format. */
		format: Option[String] = None,
	  /** The capacity of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#capacity. */
		capacity: Option[Schema.Capacity] = None,
	  /** The target gender of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#gender. */
		gender: Option[String] = None,
	  /** The target age group of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#agegroup. */
		ageGroup: Option[String] = None,
	  /** The size of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#size. */
		size: Option[String] = None,
	  /** The size type of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#sizetype. */
		sizeType: Option[List[String]] = None,
	  /** The size system of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#sizesystem. */
		sizeSystem: Option[String] = None,
	  /** The color of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#color. */
		color: Option[String] = None,
	  /** The theme of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#theme. */
		theme: Option[String] = None,
	  /** The pattern of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#pattern. */
		pattern: Option[String] = None,
	  /** The material of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#material. */
		material: Option[String] = None,
	  /** The count of the product. For more information, see https://support.google.com/manufacturers/answer/6124116#count. */
		count: Option[Schema.Count] = None,
	  /** The target client id. Should only be used in the accounts of the data partners. For more information, see https://support.google.com/manufacturers/answer/10857344 */
		targetClientId: Option[String] = None,
	  /** A list of excluded destinations such as "ClientExport", "ClientShoppingCatalog" or "PartnerShoppingCatalog". For more information, see https://support.google.com/manufacturers/answer/7443550 */
		excludedDestination: Option[List[String]] = None,
	  /** A list of included destinations such as "ClientExport", "ClientShoppingCatalog" or "PartnerShoppingCatalog". For more information, see https://support.google.com/manufacturers/answer/7443550 */
		includedDestination: Option[List[String]] = None,
	  /** The product highlights. For more information, see https://support.google.com/manufacturers/answer/10066942 */
		productHighlight: Option[List[String]] = None,
	  /** Rich product content. For more information, see https://support.google.com/manufacturers/answer/9389865 */
		richProductContent: Option[List[String]] = None,
	  /** Nutrition Attributes. See more at https://support.google.com/manufacturers/answer/12098458#food-servings. */
		nutrition: Option[Schema.Nutrition] = None,
	  /** Grocery Attributes. See more at https://support.google.com/manufacturers/answer/12098458#grocery. */
		grocery: Option[Schema.Grocery] = None,
	  /** Virtual Model (3d) asset link. */
		virtualModelLink: Option[String] = None,
	  /** Optional. List of certifications claimed by this product. */
		certification: Option[List[Schema.GoogleShoppingManufacturersV1ProductCertification]] = None,
	  /** Optional. List of countries to show this product in. Countries provided in this attribute will override any of the countries configured at feed level. The values should be: the [CLDR territory code](http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) of the countries in which this item will be shown. */
		intendedCountry: Option[List[String]] = None
	)
	
	case class FeatureDescription(
	  /** A short description of the feature. */
		headline: Option[String] = None,
	  /** A detailed description of the feature. */
		text: Option[String] = None,
	  /** An optional image describing the feature. */
		image: Option[Schema.Image] = None
	)
	
	object Image {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CRAWLED, UPLOADED }
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PENDING_PROCESSING, PENDING_CRAWL, OK, ROBOTED, XROBOTED, CRAWL_ERROR, PROCESSING_ERROR, DECODING_ERROR, TOO_BIG, CRAWL_SKIPPED, HOSTLOADED, HTTP_404 }
	}
	case class Image(
	  /** The type of the image, i.e., crawled or uploaded. @OutputOnly */
		`type`: Option[Schema.Image.TypeEnum] = None,
	  /** The status of the image. @OutputOnly */
		status: Option[Schema.Image.StatusEnum] = None,
	  /** The URL of the image. For crawled images, this is the provided URL. For uploaded images, this is a serving URL from Google if the image has been processed successfully. */
		imageUrl: Option[String] = None
	)
	
	case class Price(
	  /** The numeric value of the price. */
		amount: Option[String] = None,
	  /** The currency in which the price is denoted. */
		currency: Option[String] = None
	)
	
	case class ProductDetail(
	  /** A short section name that can be reused between multiple product details. */
		sectionName: Option[String] = None,
	  /** The name of the attribute. */
		attributeName: Option[String] = None,
	  /** The value of the attribute. */
		attributeValue: Option[String] = None
	)
	
	case class Capacity(
	  /** The numeric value of the capacity. */
		value: Option[String] = None,
	  /** The unit of the capacity, i.e., MB, GB, or TB. */
		unit: Option[String] = None
	)
	
	case class Count(
	  /** The numeric value of the number of products in a package. */
		value: Option[String] = None,
	  /** The unit in which these products are counted. */
		unit: Option[String] = None
	)
	
	case class Nutrition(
	  /** Food Serving Size. Serving size description. */
		servingSizeDescription: Option[String] = None,
	  /** Servings per container. */
		servingsPerContainer: Option[String] = None,
	  /** Serving size measure. */
		servingSizeMeasure: Option[Schema.FloatUnit] = None,
	  /** Prepared size description. */
		preparedSizeDescription: Option[String] = None,
	  /** Nutrition fact measure. */
		nutritionFactMeasure: Option[String] = None,
	  /** Mandatory Nutrition Facts. Energy. */
		energy: Option[Schema.FloatUnit] = None,
	  /** Energy from fat. */
		energyFromFat: Option[Schema.FloatUnit] = None,
	  /** Total fat. */
		totalFat: Option[Schema.FloatUnit] = None,
	  /** Total fat daily percentage. */
		totalFatDailyPercentage: Option[BigDecimal] = None,
	  /** Saturated fat. */
		saturatedFat: Option[Schema.FloatUnit] = None,
	  /** Saturated fat daily percentage. */
		saturatedFatDailyPercentage: Option[BigDecimal] = None,
	  /** Monounsaturated fat. */
		monounsaturatedFat: Option[Schema.FloatUnit] = None,
	  /** Polyunsaturated fat. */
		polyunsaturatedFat: Option[Schema.FloatUnit] = None,
	  /** Trans fat. */
		transFat: Option[Schema.FloatUnit] = None,
	  /** Trans fat daily percentage. */
		transFatDailyPercentage: Option[BigDecimal] = None,
	  /** Cholesterol. */
		cholesterol: Option[Schema.FloatUnit] = None,
	  /** Cholesterol daily percentage. */
		cholesterolDailyPercentage: Option[BigDecimal] = None,
	  /** Sodium. */
		sodium: Option[Schema.FloatUnit] = None,
	  /** Sodium daily percentage. */
		sodiumDailyPercentage: Option[BigDecimal] = None,
	  /** Total carbohydrate. */
		totalCarbohydrate: Option[Schema.FloatUnit] = None,
	  /** Total carbohydrate daily percentage. */
		totalCarbohydrateDailyPercentage: Option[BigDecimal] = None,
	  /** Dietary fiber. */
		dietaryFiber: Option[Schema.FloatUnit] = None,
	  /** Dietary fiber daily percentage. */
		dietaryFiberDailyPercentage: Option[BigDecimal] = None,
	  /** Total sugars. */
		totalSugars: Option[Schema.FloatUnit] = None,
	  /** Total sugars daily percentage. */
		totalSugarsDailyPercentage: Option[BigDecimal] = None,
	  /** Added sugars. */
		addedSugars: Option[Schema.FloatUnit] = None,
	  /** Added sugars daily percentage. */
		addedSugarsDailyPercentage: Option[BigDecimal] = None,
	  /** Protein. */
		protein: Option[Schema.FloatUnit] = None,
	  /** Protein daily percentage. */
		proteinDailyPercentage: Option[BigDecimal] = None,
	  /** Polyols. */
		polyols: Option[Schema.FloatUnit] = None,
	  /** Starch. */
		starch: Option[Schema.FloatUnit] = None,
	  /** Vitamin D. */
		vitaminD: Option[Schema.FloatUnit] = None,
	  /** Vitamin D daily percentage. */
		vitaminDDailyPercentage: Option[BigDecimal] = None,
	  /** Calcium. */
		calcium: Option[Schema.FloatUnit] = None,
	  /** Calcium daily percentage. */
		calciumDailyPercentage: Option[BigDecimal] = None,
	  /** Iron. */
		iron: Option[Schema.FloatUnit] = None,
	  /** Iron daily percentage. */
		ironDailyPercentage: Option[BigDecimal] = None,
	  /** Potassium. */
		potassium: Option[Schema.FloatUnit] = None,
	  /** Potassium daily percentage. */
		potassiumDailyPercentage: Option[BigDecimal] = None,
	  /** Folate mcg DFE. */
		folateMcgDfe: Option[BigDecimal] = None,
	  /** Folate daily percentage. */
		folateDailyPercentage: Option[BigDecimal] = None,
	  /** Folate folic acid. */
		folateFolicAcid: Option[Schema.FloatUnit] = None,
	  /** Voluntary nutrition fact. */
		voluntaryNutritionFact: Option[List[Schema.VoluntaryNutritionFact]] = None
	)
	
	case class FloatUnit(
	  /** amount. */
		amount: Option[BigDecimal] = None,
	  /** unit. */
		unit: Option[String] = None
	)
	
	case class VoluntaryNutritionFact(
	  /** Name. */
		name: Option[String] = None,
	  /** Value. */
		value: Option[Schema.FloatUnit] = None,
	  /** Daily percentage. */
		dailyPercentage: Option[BigDecimal] = None
	)
	
	case class Grocery(
	  /** Ingredients. */
		ingredients: Option[String] = None,
	  /** Active ingredients. */
		activeIngredients: Option[String] = None,
	  /** Allergens. */
		allergens: Option[String] = None,
	  /** Directions. */
		directions: Option[String] = None,
	  /** Storage instructions. */
		storageInstructions: Option[String] = None,
	  /** Indications. */
		indications: Option[String] = None,
	  /** Nutrition claim. */
		nutritionClaim: Option[List[String]] = None,
	  /** Derived nutrition claim. */
		derivedNutritionClaim: Option[List[String]] = None,
	  /** Alcohol by volume. */
		alcoholByVolume: Option[BigDecimal] = None
	)
	
	case class GoogleShoppingManufacturersV1ProductCertification(
	  /** Required. Name of the certification. */
		name: Option[String] = None,
	  /** Required. Name of the certification body. */
		authority: Option[String] = None,
	  /** Required. A unique code to identify the certification. */
		code: Option[String] = None
	)
	
	object Issue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
		enum ResolutionEnum extends Enum[ResolutionEnum] { case RESOLUTION_UNSPECIFIED, USER_ACTION, PENDING_PROCESSING }
	}
	case class Issue(
	  /** The server-generated type of the issue, for example, “INCORRECT_TEXT_FORMATTING”, “IMAGE_NOT_SERVEABLE”, etc. */
		`type`: Option[String] = None,
	  /** The severity of the issue. */
		severity: Option[Schema.Issue.SeverityEnum] = None,
	  /** If present, the attribute that triggered the issue. For more information about attributes, see https://support.google.com/manufacturers/answer/6124116. */
		attribute: Option[String] = None,
	  /** Short title describing the nature of the issue. */
		title: Option[String] = None,
	  /** Longer description of the issue focused on how to resolve it. */
		description: Option[String] = None,
	  /** What needs to happen to resolve the issue. */
		resolution: Option[Schema.Issue.ResolutionEnum] = None,
	  /** The destination this issue applies to. */
		destination: Option[String] = None,
	  /** The timestamp when this issue appeared. */
		timestamp: Option[String] = None,
	  /** Output only. List of country codes (ISO 3166-1 alpha-2) where issue applies to the manufacturer product. */
		applicableCountries: Option[List[String]] = None
	)
	
	object DestinationStatus {
		enum StatusEnum extends Enum[StatusEnum] { case UNKNOWN, ACTIVE, PENDING, DISAPPROVED }
	}
	case class DestinationStatus(
	  /** The name of the destination. */
		destination: Option[String] = None,
	  /** The status of the destination. */
		status: Option[Schema.DestinationStatus.StatusEnum] = None,
	  /** Output only. List of country codes (ISO 3166-1 alpha-2) where the offer is approved. */
		approvedCountries: Option[List[String]] = None,
	  /** Output only. List of country codes (ISO 3166-1 alpha-2) where the offer is pending approval. */
		pendingCountries: Option[List[String]] = None,
	  /** Output only. List of country codes (ISO 3166-1 alpha-2) where the offer is disapproved. */
		disapprovedCountries: Option[List[String]] = None
	)
	
	case class Empty(
	
	)
	
	case class ProductCertification(
	  /** Required. The unique name identifier of a product certification Format: accounts/{account}/languages/{language_code}/productCertifications/{id} Where `id` is a some unique identifier and `language_code` is a 2-letter ISO 639-1 code of a Shopping supported language according to https://support.google.com/merchants/answer/160637. */
		name: Option[String] = None,
	  /** Required. This is to clearly identify the product you are certifying. */
		title: Option[String] = None,
	  /** Required. This is the product's brand name. The brand is used to help identify your product. */
		brand: Option[String] = None,
	  /** Optional. These are the Manufacturer Part Numbers (MPN). MPNs are used to uniquely identify a specific product among all products from the same manufacturer */
		mpn: Option[List[String]] = None,
	  /** Optional. Another name for GTIN. */
		productCode: Option[List[String]] = None,
	  /** Optional. A 2-letter country code (ISO 3166-1 Alpha 2). */
		countryCode: Option[List[String]] = None,
	  /** Optional. These are your own product categorization system in your product data. */
		productType: Option[List[String]] = None,
	  /** Required. A list of certifications to link to the described product. */
		certification: Option[List[Schema.Certification]] = None,
	  /** Output only. A server-generated list of issues associated with the product. */
		issues: Option[List[Schema.Issue]] = None,
	  /** Output only. The statuses of the destinations. */
		destinationStatuses: Option[List[Schema.DestinationStatus]] = None
	)
	
	case class Certification(
	  /** Required. Name of the certification. */
		name: Option[String] = None,
	  /** Required. Name of the certification body. */
		authority: Option[String] = None,
	  /** Required. A unique code to identify the certification. */
		code: Option[String] = None,
	  /** Optional. A custom value of the certification. */
		value: Option[String] = None,
	  /** Optional. A URL link to the certification. */
		link: Option[String] = None,
	  /** Optional. A URL link to the certification logo. */
		logo: Option[String] = None,
	  /** Optional. The expiration date (UTC). */
		validUntil: Option[String] = None
	)
	
	case class ListProductCertificationsResponse(
	  /** The product certifications from the specified certification body. */
		productCertifications: Option[List[Schema.ProductCertification]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
}
