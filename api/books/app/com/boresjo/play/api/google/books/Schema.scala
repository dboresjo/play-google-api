package com.boresjo.play.api.google.books

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Bookshelf(
	  /** Id of this bookshelf, only unique by user. */
		id: Option[Int] = None,
	  /** Resource type for bookshelf metadata. */
		kind: Option[String] = None,
	  /** Title of this bookshelf. */
		title: Option[String] = None,
	  /** Description of this bookshelf. */
		description: Option[String] = None,
	  /** Whether this bookshelf is PUBLIC or PRIVATE. */
		access: Option[String] = None,
	  /** Last modified time of this bookshelf (formatted UTC timestamp with millisecond resolution). */
		updated: Option[String] = None,
	  /** Number of volumes in this bookshelf. */
		volumeCount: Option[Int] = None,
	  /** Last time a volume was added or removed from this bookshelf (formatted UTC timestamp with millisecond resolution). */
		volumesLastUpdated: Option[String] = None,
	  /** URL to this resource. */
		selfLink: Option[String] = None,
	  /** Created time for this bookshelf (formatted UTC timestamp with millisecond resolution). */
		created: Option[String] = None
	)
	
	case class Bookshelves(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of bookshelves. */
		items: Option[List[Schema.Bookshelf]] = None
	)
	
	case class Volumes(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of volumes. */
		items: Option[List[Schema.Volume]] = None,
	  /** Total number of volumes found. This might be greater than the number of volumes returned in this response if results have been paginated. */
		totalItems: Option[Int] = None
	)
	
	object Volume {
		object LayerInfoItem {
			case class LayersItem(
			  /** The layer id of this layer (e.g. "geo"). */
				layerId: Option[String] = None,
			  /** The current version of this layer's volume annotations. Note that this version applies only to the data in the books.layers.volumeAnnotations.&#42; responses. The actual annotation data is versioned separately. */
				volumeAnnotationsVersion: Option[String] = None
			)
		}
		case class LayerInfoItem(
		  /** A layer should appear here if and only if the layer exists for this book. */
			layers: Option[List[Schema.Volume.LayerInfoItem.LayersItem]] = None
		)
		
		object VolumeInfoItem {
			case class ImageLinksItem(
			  /** Image link for thumbnail size (width of ~128 pixels). (In LITE projection) */
				thumbnail: Option[String] = None,
			  /** Image link for small size (width of ~300 pixels). (In LITE projection) */
				small: Option[String] = None,
			  /** Image link for medium size (width of ~575 pixels). (In LITE projection) */
				medium: Option[String] = None,
			  /** Image link for large size (width of ~800 pixels). (In LITE projection) */
				large: Option[String] = None,
			  /** Image link for extra large size (width of ~1280 pixels). (In LITE projection) */
				extraLarge: Option[String] = None,
			  /** Image link for small thumbnail size (width of ~80 pixels). (In LITE projection) */
				smallThumbnail: Option[String] = None
			)
			
			case class IndustryIdentifiersItem(
			  /** Identifier type. Possible values are ISBN_10, ISBN_13, ISSN and OTHER. */
				`type`: Option[String] = None,
			  /** Industry specific volume identifier. */
				identifier: Option[String] = None
			)
			
			case class ReadingModesItem(
				text: Option[Boolean] = None,
				image: Option[Boolean] = None
			)
			
			case class DimensionsItem(
			  /** Height or length of this volume (in cm). */
				height: Option[String] = None,
			  /** Width of this volume (in cm). */
				width: Option[String] = None,
			  /** Thickness of this volume (in cm). */
				thickness: Option[String] = None
			)
			
			case class PanelizationSummaryItem(
				containsEpubBubbles: Option[Boolean] = None,
				containsImageBubbles: Option[Boolean] = None,
				epubBubbleVersion: Option[String] = None,
				imageBubbleVersion: Option[String] = None
			)
		}
		case class VolumeInfoItem(
		  /** Volume title. (In LITE projection.) */
			title: Option[String] = None,
		  /** Volume subtitle. (In LITE projection.) */
			subtitle: Option[String] = None,
		  /** The names of the authors and/or editors for this volume. (In LITE projection) */
			authors: Option[List[String]] = None,
		  /** Publisher of this volume. (In LITE projection.) */
			publisher: Option[String] = None,
		  /** Date of publication. (In LITE projection.) */
			publishedDate: Option[String] = None,
		  /** A synopsis of the volume. The text of the description is formatted in HTML and includes simple formatting elements, such as b, i, and br tags. (In LITE projection.) */
			description: Option[String] = None,
		  /** Total number of pages as per publisher metadata. */
			pageCount: Option[Int] = None,
		  /** Type of publication of this volume. Possible values are BOOK or MAGAZINE. */
			printType: Option[String] = None,
		  /** The mean review rating for this volume. (min = 1.0, max = 5.0) */
			averageRating: Option[BigDecimal] = None,
		  /** The number of review ratings for this volume. */
			ratingsCount: Option[Int] = None,
		  /** An identifier for the version of the volume content (text & images). (In LITE projection) */
			contentVersion: Option[String] = None,
		  /** A list of image links for all the sizes that are available. (In LITE projection.) */
			imageLinks: Option[Schema.Volume.VolumeInfoItem.ImageLinksItem] = None,
		  /** Best language for this volume (based on content). It is the two-letter ISO 639-1 code such as 'fr', 'en', etc. */
			language: Option[String] = None,
		  /** URL to preview this volume on the Google Books site. */
			previewLink: Option[String] = None,
		  /** Industry standard identifiers for this volume. */
			industryIdentifiers: Option[List[Schema.Volume.VolumeInfoItem.IndustryIdentifiersItem]] = None,
		  /** URL to view information about this volume on the Google Books site. (In LITE projection) */
			infoLink: Option[String] = None,
		  /** Canonical URL for a volume. (In LITE projection.) */
			canonicalVolumeLink: Option[String] = None,
		  /** A list of subject categories, such as "Fiction", "Suspense", etc. */
			categories: Option[List[String]] = None,
		  /** Total number of printed pages in generated pdf representation. */
			printedPageCount: Option[Int] = None,
		  /** The reading modes available for this volume. */
			readingModes: Option[Schema.Volume.VolumeInfoItem.ReadingModesItem] = None,
		  /** Total number of sample pages as per publisher metadata. */
			samplePageCount: Option[Int] = None,
			maturityRating: Option[String] = None,
		  /** Whether anonymous logging should be allowed. */
			allowAnonLogging: Option[Boolean] = None,
			seriesInfo: Option[Schema.Volumeseriesinfo] = None,
		  /** Whether the volume has comics content. */
			comicsContent: Option[Boolean] = None,
		  /** Physical dimensions of this volume. */
			dimensions: Option[Schema.Volume.VolumeInfoItem.DimensionsItem] = None,
		  /** The main category to which this volume belongs. It will be the category from the categories list returned below that has the highest weight. */
			mainCategory: Option[String] = None,
		  /** A top-level summary of the panelization info in this volume. */
			panelizationSummary: Option[Schema.Volume.VolumeInfoItem.PanelizationSummaryItem] = None
		)
		
		object UserInfoItem {
			case class FamilySharingItem(
			  /** Whether or not this volume can be shared with the family by the user. This includes sharing eligibility of both the volume and the user. If the value is true, the user can initiate a family sharing action. */
				isSharingAllowed: Option[Boolean] = None,
			  /** Whether or not sharing this volume is temporarily disabled due to issues with the Family Wallet. */
				isSharingDisabledByFop: Option[Boolean] = None,
			  /** The role of the user in the family. */
				familyRole: Option[String] = None
			)
			
			case class UserUploadedVolumeInfoItem(
				processingState: Option[String] = None
			)
			
			case class RentalPeriodItem(
				startUtcSec: Option[String] = None,
				endUtcSec: Option[String] = None
			)
			
			case class CopyItem(
				remainingCharacterCount: Option[Int] = None,
				allowedCharacterCount: Option[Int] = None,
				limitType: Option[String] = None,
				updated: Option[String] = None
			)
		}
		case class UserInfoItem(
		  /** Information on the ability to share with the family. */
			familySharing: Option[Schema.Volume.UserInfoItem.FamilySharingItem] = None,
		  /** This user's review of this volume, if one exists. */
			review: Option[Schema.Review] = None,
		  /** The user's current reading position in the volume, if one is available. (In LITE projection.) */
			readingPosition: Option[Schema.ReadingPosition] = None,
		  /** Whether or not this volume was purchased by the authenticated user making the request. (In LITE projection.) */
			isPurchased: Option[Boolean] = None,
		  /** Timestamp when this volume was last modified by a user action, such as a reading position update, volume purchase or writing a review. (RFC 3339 UTC date-time format). */
			updated: Option[String] = None,
		  /** Whether or not this volume was pre-ordered by the authenticated user making the request. (In LITE projection.) */
			isPreordered: Option[Boolean] = None,
		  /** Whether or not this volume is currently in "my books." */
			isInMyBooks: Option[Boolean] = None,
		  /** Whether or not this volume was user uploaded. */
			isUploaded: Option[Boolean] = None,
		  /** Whether this book is an active or an expired rental. */
			rentalState: Option[String] = None,
		  /** How this volume was acquired. */
			acquisitionType: Option[Int] = None,
		  /** Whether this volume is purchased, sample, pd download etc. */
			entitlementType: Option[Int] = None,
		  /** Timestamp when this volume was acquired by the user. (RFC 3339 UTC date-time format) Acquiring includes purchase, user upload, receiving family sharing, etc. */
			acquiredTime: Option[String] = None,
		  /** Whether or not the user shared this volume with the family. */
			isFamilySharedFromUser: Option[Boolean] = None,
		  /** Whether or not the user received this volume through family sharing. */
			isFamilySharedToUser: Option[Boolean] = None,
		  /** Deprecated: Replaced by familySharing. */
			isFamilySharingAllowed: Option[Boolean] = None,
		  /** Deprecated: Replaced by familySharing. */
			isFamilySharingDisabledByFop: Option[Boolean] = None,
			userUploadedVolumeInfo: Option[Schema.Volume.UserInfoItem.UserUploadedVolumeInfoItem] = None,
		  /** Period during this book is/was a valid rental. */
			rentalPeriod: Option[Schema.Volume.UserInfoItem.RentalPeriodItem] = None,
		  /** Copy/Paste accounting information. */
			copy: Option[Schema.Volume.UserInfoItem.CopyItem] = None
		)
		
		object SaleInfoItem {
			object OffersItem {
				case class RetailPriceItem(
					currencyCode: Option[String] = None,
					amountInMicros: Option[BigDecimal] = None
				)
				
				case class RentalDurationItem(
					unit: Option[String] = None,
					count: Option[BigDecimal] = None
				)
				
				case class ListPriceItem(
					currencyCode: Option[String] = None,
					amountInMicros: Option[BigDecimal] = None
				)
			}
			case class OffersItem(
			  /** The finsky offer type (e.g., PURCHASE=0 RENTAL=3) */
				finskyOfferType: Option[Int] = None,
			  /** Indicates whether the offer is giftable. */
				giftable: Option[Boolean] = None,
			  /** Offer retail (=discounted) price in Micros */
				retailPrice: Option[Schema.Volume.SaleInfoItem.OffersItem.RetailPriceItem] = None,
			  /** The rental duration (for rental offers only). */
				rentalDuration: Option[Schema.Volume.SaleInfoItem.OffersItem.RentalDurationItem] = None,
			  /** Offer list (=undiscounted) price in Micros. */
				listPrice: Option[Schema.Volume.SaleInfoItem.OffersItem.ListPriceItem] = None
			)
			
			case class ListPriceItem(
			  /** Amount in the currency listed below. (In LITE projection.) */
				amount: Option[BigDecimal] = None,
			  /** An ISO 4217, three-letter currency code. (In LITE projection.) */
				currencyCode: Option[String] = None
			)
			
			case class RetailPriceItem(
			  /** Amount in the currency listed below. (In LITE projection.) */
				amount: Option[BigDecimal] = None,
			  /** An ISO 4217, three-letter currency code. (In LITE projection.) */
				currencyCode: Option[String] = None
			)
		}
		case class SaleInfoItem(
		  /** The two-letter ISO_3166-1 country code for which this sale information is valid. (In LITE projection.) */
			country: Option[String] = None,
		  /** Whether or not this book is available for sale or offered for free in the Google eBookstore for the country listed above. Possible values are FOR_SALE, FOR_RENTAL_ONLY, FOR_SALE_AND_RENTAL, FREE, NOT_FOR_SALE, or FOR_PREORDER. */
			saleability: Option[String] = None,
		  /** Whether or not this volume is an eBook (can be added to the My eBooks shelf). */
			isEbook: Option[Boolean] = None,
		  /** URL to purchase this volume on the Google Books site. (In LITE projection) */
			buyLink: Option[String] = None,
		  /** Offers available for this volume (sales and rentals). */
			offers: Option[List[Schema.Volume.SaleInfoItem.OffersItem]] = None,
		  /** The date on which this book is available for sale. */
			onSaleDate: Option[String] = None,
		  /** Suggested retail price. (In LITE projection.) */
			listPrice: Option[Schema.Volume.SaleInfoItem.ListPriceItem] = None,
		  /** The actual selling price of the book. This is the same as the suggested retail or list price unless there are offers or discounts on this volume. (In LITE projection.) */
			retailPrice: Option[Schema.Volume.SaleInfoItem.RetailPriceItem] = None
		)
		
		object AccessInfoItem {
			case class EpubItem(
			  /** URL to download epub. (In LITE projection.) */
				downloadLink: Option[String] = None,
			  /** URL to retrieve ACS token for epub download. (In LITE projection.) */
				acsTokenLink: Option[String] = None,
			  /** Is a flowing text epub available either as public domain or for purchase. (In LITE projection.) */
				isAvailable: Option[Boolean] = None
			)
			
			case class PdfItem(
			  /** URL to download pdf. (In LITE projection.) */
				downloadLink: Option[String] = None,
			  /** URL to retrieve ACS token for pdf download. (In LITE projection.) */
				acsTokenLink: Option[String] = None,
			  /** Is a scanned image pdf available either as public domain or for purchase. (In LITE projection.) */
				isAvailable: Option[Boolean] = None
			)
		}
		case class AccessInfoItem(
		  /** The two-letter ISO_3166-1 country code for which this access information is valid. (In LITE projection.) */
			country: Option[String] = None,
		  /** The read access of a volume. Possible values are PARTIAL, ALL_PAGES, NO_PAGES or UNKNOWN. This value depends on the country listed above. A value of PARTIAL means that the publisher has allowed some portion of the volume to be viewed publicly, without purchase. This can apply to eBooks as well as non-eBooks. Public domain books will always have a value of ALL_PAGES. */
			viewability: Option[String] = None,
		  /** Whether this volume can be embedded in a viewport using the Embedded Viewer API. */
			embeddable: Option[Boolean] = None,
		  /** Whether or not this book is public domain in the country listed above. */
			publicDomain: Option[Boolean] = None,
		  /** Combines the access and viewability of this volume into a single status field for this user. Values can be FULL_PURCHASED, FULL_PUBLIC_DOMAIN, SAMPLE or NONE. (In LITE projection.) */
			accessViewStatus: Option[String] = None,
		  /** Information about a volume's download license access restrictions. */
			downloadAccess: Option[Schema.DownloadAccessRestriction] = None,
		  /** Whether text-to-speech is permitted for this volume. Values can be ALLOWED, ALLOWED_FOR_ACCESSIBILITY, or NOT_ALLOWED. */
			textToSpeechPermission: Option[String] = None,
		  /** URL to read this volume on the Google Books site. Link will not allow users to read non-viewable volumes. */
			webReaderLink: Option[String] = None,
		  /** Information about epub content. (In LITE projection.) */
			epub: Option[Schema.Volume.AccessInfoItem.EpubItem] = None,
		  /** Information about pdf content. (In LITE projection.) */
			pdf: Option[Schema.Volume.AccessInfoItem.PdfItem] = None,
		  /** For ordered but not yet processed orders, we give a URL that can be used to go to the appropriate Google Wallet page. */
			viewOrderUrl: Option[String] = None,
		  /** Whether this volume requires that the client explicitly request offline download license rather than have it done automatically when loading the content, if the client supports it. */
			explicitOfflineLicenseManagement: Option[Boolean] = None,
		  /** Whether quote sharing is allowed for this volume. */
			quoteSharingAllowed: Option[Boolean] = None,
		  /** URL to the Google Drive viewer if this volume is uploaded by the user by selecting the file from Google Drive. */
			driveImportedContentLink: Option[String] = None
		)
		
		case class SearchInfoItem(
		  /** A text snippet containing the search query. */
			textSnippet: Option[String] = None
		)
		
		case class RecommendedInfoItem(
		  /** A text explaining why this volume is recommended. */
			explanation: Option[String] = None
		)
	}
	case class Volume(
	  /** Unique identifier for a volume. (In LITE projection.) */
		id: Option[String] = None,
	  /** Resource type for a volume. (In LITE projection.) */
		kind: Option[String] = None,
	  /** URL to this resource. (In LITE projection.) */
		selfLink: Option[String] = None,
	  /** What layers exist in this volume and high level information about them. */
		layerInfo: Option[Schema.Volume.LayerInfoItem] = None,
	  /** Opaque identifier for a specific version of a volume resource. (In LITE projection) */
		etag: Option[String] = None,
	  /** General volume information. */
		volumeInfo: Option[Schema.Volume.VolumeInfoItem] = None,
	  /** User specific information related to this volume. (e.g. page this user last read or whether they purchased this book) */
		userInfo: Option[Schema.Volume.UserInfoItem] = None,
	  /** Any information about a volume related to the eBookstore and/or purchaseability. This information can depend on the country where the request originates from (i.e. books may not be for sale in certain countries). */
		saleInfo: Option[Schema.Volume.SaleInfoItem] = None,
	  /** Any information about a volume related to reading or obtaining that volume text. This information can depend on country (books may be public domain in one country but not in another, e.g.). */
		accessInfo: Option[Schema.Volume.AccessInfoItem] = None,
	  /** Search result information related to this volume. */
		searchInfo: Option[Schema.Volume.SearchInfoItem] = None,
	  /** Recommendation related information for this volume. */
		recommendedInfo: Option[Schema.Volume.RecommendedInfoItem] = None
	)
	
	object Volumeseriesinfo {
		object VolumeSeriesItem {
			case class IssueItem(
				issueOrderNumber: Option[Int] = None,
				issueDisplayNumber: Option[String] = None
			)
		}
		case class VolumeSeriesItem(
		  /** The series id. */
			seriesId: Option[String] = None,
		  /** The book type in the context of series. Examples - Single Issue, Collection Edition, etc. */
			seriesBookType: Option[String] = None,
		  /** The book order number in the series. */
			orderNumber: Option[Int] = None,
		  /** List of issues. Applicable only for Collection Edition and Omnibus. */
			issue: Option[List[Schema.Volumeseriesinfo.VolumeSeriesItem.IssueItem]] = None
		)
	}
	case class Volumeseriesinfo(
	  /** Short book title in the context of the series. */
		shortSeriesBookTitle: Option[String] = None,
		volumeSeries: Option[List[Schema.Volumeseriesinfo.VolumeSeriesItem]] = None,
	  /** The display number string. This should be used only for display purposes and the actual sequence should be inferred from the below orderNumber. */
		bookDisplayNumber: Option[String] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	object Review {
		case class AuthorItem(
		  /** Name of this person. */
			displayName: Option[String] = None
		)
		
		case class SourceItem(
		  /** Name of the source. */
			description: Option[String] = None,
		  /** URL of the source of the review. */
			url: Option[String] = None,
		  /** Extra text about the source of the review. */
			extraDescription: Option[String] = None
		)
	}
	case class Review(
	  /** Volume that this review is for. */
		volumeId: Option[String] = None,
	  /** Source type for this review. Possible values are EDITORIAL, WEB_USER or GOOGLE_USER. */
		`type`: Option[String] = None,
	  /** Resource type for a review. */
		kind: Option[String] = None,
	  /** Date of this review. */
		date: Option[String] = None,
	  /** Title for this review. */
		title: Option[String] = None,
	  /** Review text. */
		content: Option[String] = None,
	  /** Star rating for this review. Possible values are ONE, TWO, THREE, FOUR, FIVE or NOT_RATED. */
		rating: Option[String] = None,
	  /** URL for the full review text, for reviews gathered from the web. */
		fullTextUrl: Option[String] = None,
	  /** Author of this review. */
		author: Option[Schema.Review.AuthorItem] = None,
	  /** Information regarding the source of this review, when the review is not from a Google Books user. */
		source: Option[Schema.Review.SourceItem] = None
	)
	
	case class ReadingPosition(
	  /** Volume id associated with this reading position. */
		volumeId: Option[String] = None,
	  /** Resource type for a reading position. */
		kind: Option[String] = None,
	  /** Position in a volume for text-based content. */
		gbTextPosition: Option[String] = None,
	  /** Position in a volume for image-based content. */
		gbImagePosition: Option[String] = None,
	  /** Position in a PDF file. */
		pdfPosition: Option[String] = None,
	  /** Position in an EPUB as a CFI. */
		epubCfiPosition: Option[String] = None,
	  /** Timestamp when this reading position was last updated (formatted UTC timestamp with millisecond resolution). */
		updated: Option[String] = None
	)
	
	case class DownloadAccessRestriction(
	  /** If restricted, whether access is granted for this (user, device, volume). */
		deviceAllowed: Option[Boolean] = None,
	  /** Identifies the volume for which this entry applies. */
		volumeId: Option[String] = None,
	  /** If restricted, the maximum number of content download licenses for this volume. */
		maxDownloadDevices: Option[Int] = None,
	  /** Error/warning reason code. Additional codes may be added in the future. 0 OK 100 ACCESS_DENIED_PUBLISHER_LIMIT 101 ACCESS_DENIED_LIMIT 200 WARNING_USED_LAST_ACCESS */
		reasonCode: Option[String] = None,
	  /** Error/warning message. */
		message: Option[String] = None,
	  /** Client nonce for verification. Download access and client-validation only. */
		nonce: Option[String] = None,
	  /** Client app identifier for verification. Download access and client-validation only. */
		source: Option[String] = None,
	  /** Response signature. */
		signature: Option[String] = None,
	  /** If restricted, the number of content download licenses already acquired (including the requesting client, if licensed). */
		downloadsAcquired: Option[Int] = None,
	  /** Whether this volume has any download access restrictions. */
		restricted: Option[Boolean] = None,
	  /** If deviceAllowed, whether access was just acquired with this request. */
		justAcquired: Option[Boolean] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	case class BooksCloudloadingResource(
		volumeId: Option[String] = None,
		title: Option[String] = None,
		author: Option[String] = None,
		processingState: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object Metadata {
		case class ItemsItem(
			language: Option[String] = None,
			size: Option[String] = None,
			version: Option[String] = None,
			download_url: Option[String] = None,
			encrypted_key: Option[String] = None
		)
	}
	case class Metadata(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of offline dictionary metadata. */
		items: Option[List[Schema.Metadata.ItemsItem]] = None
	)
	
	object FamilyInfo {
		case class MembershipItem(
			isInFamily: Option[Boolean] = None,
		  /** The role of the user in the family. */
			role: Option[String] = None,
		  /** The age group of the user. */
			ageGroup: Option[String] = None,
		  /** Restrictions on user buying and acquiring content. */
			acquirePermission: Option[String] = None,
		  /** The maximum allowed maturity rating for the user. */
			allowedMaturityRating: Option[String] = None
		)
	}
	case class FamilyInfo(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** Family membership info of the user that made the request. */
		membership: Option[Schema.FamilyInfo.MembershipItem] = None
	)
	
	case class Layersummary(
	  /** Unique id of this layer summary. */
		id: Option[String] = None,
	  /** The layer id for this summary. */
		layerId: Option[String] = None,
	  /** The volume id this resource is for. */
		volumeId: Option[String] = None,
	  /** The content version this resource is for. */
		contentVersion: Option[String] = None,
	  /** The list of annotation types contained for this layer. */
		annotationTypes: Option[List[String]] = None,
	  /** The number of annotations for this layer. */
		annotationCount: Option[Int] = None,
	  /** The number of data items for this layer. */
		dataCount: Option[Int] = None,
	  /** Resource Type */
		kind: Option[String] = None,
	  /** URL to this resource. */
		selfLink: Option[String] = None,
	  /** Timestamp for the last time an item in this layer was updated. (RFC 3339 UTC date-time format). */
		updated: Option[String] = None,
	  /** The current version of this layer's volume annotations. Note that this version applies only to the data in the books.layers.volumeAnnotations.&#42; responses. The actual annotation data is versioned separately. */
		volumeAnnotationsVersion: Option[String] = None,
	  /** The link to get the annotations for this layer. */
		annotationsLink: Option[String] = None,
	  /** Link to get data for this annotation. */
		annotationsDataLink: Option[String] = None
	)
	
	case class Layersummaries(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of layer summary items. */
		items: Option[List[Schema.Layersummary]] = None,
	  /** The total number of layer summaries found. */
		totalItems: Option[Int] = None
	)
	
	case class DictionaryAnnotationdata(
	  /** Unique id for this annotation data. */
		id: Option[String] = None,
	  /** The Layer id for this data. &#42; */
		layerId: Option[String] = None,
	  /** URL for this resource. &#42; */
		selfLink: Option[String] = None,
	  /** The type of annotation this data is for. */
		annotationType: Option[String] = None,
	  /** Resource Type */
		kind: Option[String] = None,
	  /** Base64 encoded data for this annotation data. */
		encodedData: Option[String] = None,
	  /** Timestamp for the last time this data was updated. (RFC 3339 UTC date-time format). */
		updated: Option[String] = None,
	  /** The volume id for this data. &#42; */
		volumeId: Option[String] = None,
	  /** JSON encoded data for this dictionary annotation data. Emitted with name 'data' in JSON output. Either this or geo_data will be populated. */
		data: Option[Schema.Dictlayerdata] = None
	)
	
	object Dictlayerdata {
		object DictItem {
			object WordsItem {
				object SensesItem {
					object DefinitionsItem {
						object ExamplesItem {
							case class SourceItem(
								url: Option[String] = None,
								attribution: Option[String] = None
							)
						}
						case class ExamplesItem(
							text: Option[String] = None,
							source: Option[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.DefinitionsItem.ExamplesItem.SourceItem] = None
						)
					}
					case class DefinitionsItem(
						definition: Option[String] = None,
						examples: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.DefinitionsItem.ExamplesItem]] = None
					)
					
					object SynonymsItem {
						case class SourceItem(
							url: Option[String] = None,
							attribution: Option[String] = None
						)
					}
					case class SynonymsItem(
						text: Option[String] = None,
						source: Option[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.SynonymsItem.SourceItem] = None
					)
					
					case class ConjugationsItem(
						value: Option[String] = None,
						`type`: Option[String] = None
					)
					
					case class SourceItem(
						url: Option[String] = None,
						attribution: Option[String] = None
					)
				}
				case class SensesItem(
					syllabification: Option[String] = None,
					pronunciation: Option[String] = None,
					partOfSpeech: Option[String] = None,
					pronunciationUrl: Option[String] = None,
					definitions: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.DefinitionsItem]] = None,
					synonyms: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.SynonymsItem]] = None,
					conjugations: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.ConjugationsItem]] = None,
					source: Option[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem.SourceItem] = None
				)
				
				object ExamplesItem {
					case class SourceItem(
						url: Option[String] = None,
						attribution: Option[String] = None
					)
				}
				case class ExamplesItem(
					text: Option[String] = None,
					source: Option[Schema.Dictlayerdata.DictItem.WordsItem.ExamplesItem.SourceItem] = None
				)
				
				object DerivativesItem {
					case class SourceItem(
						url: Option[String] = None,
						attribution: Option[String] = None
					)
				}
				case class DerivativesItem(
					text: Option[String] = None,
					source: Option[Schema.Dictlayerdata.DictItem.WordsItem.DerivativesItem.SourceItem] = None
				)
				
				case class SourceItem(
					url: Option[String] = None,
					attribution: Option[String] = None
				)
			}
			case class WordsItem(
				senses: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.SensesItem]] = None,
				examples: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.ExamplesItem]] = None,
				derivatives: Option[List[Schema.Dictlayerdata.DictItem.WordsItem.DerivativesItem]] = None,
			  /** The words with different meanings but not related words, e.g. "go" (game) and "go" (verb). */
				source: Option[Schema.Dictlayerdata.DictItem.WordsItem.SourceItem] = None
			)
			
			case class SourceItem(
				url: Option[String] = None,
				attribution: Option[String] = None
			)
		}
		case class DictItem(
			words: Option[List[Schema.Dictlayerdata.DictItem.WordsItem]] = None,
		  /** The source, url and attribution for this dictionary data. */
			source: Option[Schema.Dictlayerdata.DictItem.SourceItem] = None
		)
		
		case class CommonItem(
		  /** The display title and localized canonical name to use when searching for this entity on Google search. */
			title: Option[String] = None
		)
	}
	case class Dictlayerdata(
		kind: Option[String] = None,
		dict: Option[Schema.Dictlayerdata.DictItem] = None,
		common: Option[Schema.Dictlayerdata.CommonItem] = None
	)
	
	case class Annotationsdata(
	  /** Resource type */
		kind: Option[String] = None,
	  /** A list of Annotation Data. */
		items: Option[List[Schema.GeoAnnotationdata]] = None,
	  /** The total number of volume annotations found. */
		totalItems: Option[Int] = None,
	  /** Token to pass in for pagination for the next page. This will not be present if this request does not have more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GeoAnnotationdata(
	  /** Unique id for this annotation data. */
		id: Option[String] = None,
	  /** The Layer id for this data. &#42; */
		layerId: Option[String] = None,
	  /** URL for this resource. &#42; */
		selfLink: Option[String] = None,
	  /** The type of annotation this data is for. */
		annotationType: Option[String] = None,
	  /** Resource Type */
		kind: Option[String] = None,
	  /** Base64 encoded data for this annotation data. */
		encodedData: Option[String] = None,
	  /** Timestamp for the last time this data was updated. (RFC 3339 UTC date-time format). */
		updated: Option[String] = None,
	  /** The volume id for this data. &#42; */
		volumeId: Option[String] = None,
	  /** JSON encoded data for this geo annotation data. Emitted with name 'data' in JSON output. Either this or dict_data will be populated. */
		data: Option[Schema.Geolayerdata] = None
	)
	
	object Geolayerdata {
		object GeoItem {
			object ViewportItem {
				case class LoItem(
					longitude: Option[BigDecimal] = None,
					latitude: Option[BigDecimal] = None
				)
				
				case class HiItem(
					longitude: Option[BigDecimal] = None,
					latitude: Option[BigDecimal] = None
				)
			}
			case class ViewportItem(
				lo: Option[Schema.Geolayerdata.GeoItem.ViewportItem.LoItem] = None,
				hi: Option[Schema.Geolayerdata.GeoItem.ViewportItem.HiItem] = None
			)
		}
		case class GeoItem(
		  /** The viewport for showing this location. This is a latitude, longitude rectangle. */
			viewport: Option[Schema.Geolayerdata.GeoItem.ViewportItem] = None,
		  /** The country code of the location. */
			countryCode: Option[String] = None,
		  /** The type of map that should be used for this location. EX: HYBRID, ROADMAP, SATELLITE, TERRAIN */
			mapType: Option[String] = None,
		  /** The Zoom level to use for the map. Zoom levels between 0 (the lowest zoom level, in which the entire world can be seen on one map) to 21+ (down to individual buildings). See: https: //developers.google.com/maps/documentation/staticmaps/#Zoomlevels */
			zoom: Option[Int] = None,
		  /** The cache policy active for this data. EX: UNRESTRICTED, RESTRICTED, NEVER */
			cachePolicy: Option[String] = None,
		  /** The longitude of the location. */
			longitude: Option[BigDecimal] = None,
		  /** The latitude of the location. */
			latitude: Option[BigDecimal] = None,
		  /** The boundary of the location as a set of loops containing pairs of latitude, longitude coordinates. */
			boundary: Option[List[String]] = None
		)
		
		case class CommonItem(
		  /** The language of the information url and description. */
			lang: Option[String] = None,
		  /** The URL for information for this location. Ex: wikipedia link. */
			snippetUrl: Option[String] = None,
		  /** The description for this location. */
			snippet: Option[String] = None,
		  /** The URL for the preview image information. */
			previewImageUrl: Option[String] = None,
		  /** The display title and localized canonical name to use when searching for this entity on Google search. */
			title: Option[String] = None
		)
	}
	case class Geolayerdata(
		kind: Option[String] = None,
		geo: Option[Schema.Geolayerdata.GeoItem] = None,
		common: Option[Schema.Geolayerdata.CommonItem] = None
	)
	
	object Volumeannotation {
		case class ContentRangesItem(
		  /** Content version applicable to ranges below. */
			contentVersion: Option[String] = None,
		  /** Range in GB text format for this annotation for version above. */
			gbTextRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in GB image format for this annotation for version above. */
			gbImageRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in CFI format for this annotation for version above. */
			cfiRange: Option[Schema.BooksAnnotationsRange] = None
		)
	}
	case class Volumeannotation(
	  /** Unique id of this volume annotation. */
		id: Option[String] = None,
	  /** URL to this resource. */
		selfLink: Option[String] = None,
	  /** The Layer this annotation is for. */
		layerId: Option[String] = None,
	  /** The type of annotation this is. */
		annotationType: Option[String] = None,
	  /** Pages the annotation spans. */
		pageIds: Option[List[String]] = None,
	  /** Excerpt from the volume. */
		selectedText: Option[String] = None,
	  /** Data for this annotation. */
		data: Option[String] = None,
	  /** The annotation data id for this volume annotation. */
		annotationDataId: Option[String] = None,
	  /** Resource Type */
		kind: Option[String] = None,
	  /** Timestamp for the last time this anntoation was updated. (RFC 3339 UTC date-time format). */
		updated: Option[String] = None,
	  /** Indicates that this annotation is deleted. */
		deleted: Option[Boolean] = None,
	  /** The Volume this annotation is for. */
		volumeId: Option[String] = None,
	  /** Link to get data for this annotation. */
		annotationDataLink: Option[String] = None,
	  /** The content ranges to identify the selected text. */
		contentRanges: Option[Schema.Volumeannotation.ContentRangesItem] = None
	)
	
	case class BooksAnnotationsRange(
	  /** The starting position for the range. */
		startPosition: Option[String] = None,
	  /** The offset from the starting position. */
		startOffset: Option[String] = None,
	  /** The ending position for the range. */
		endPosition: Option[String] = None,
	  /** The offset from the ending position. */
		endOffset: Option[String] = None
	)
	
	case class Volumeannotations(
	  /** The version string for all of the volume annotations in this layer (not just the ones in this response). Note: the version string doesn't apply to the annotation data, just the information in this response (e.g. the location of annotations in the book). */
		version: Option[String] = None,
	  /** Resource type */
		kind: Option[String] = None,
	  /** A list of volume annotations. */
		items: Option[List[Schema.Volumeannotation]] = None,
	  /** The total number of volume annotations found. */
		totalItems: Option[Int] = None,
	  /** Token to pass in for pagination for the next page. This will not be present if this request does not have more results. */
		nextPageToken: Option[String] = None
	)
	
	object Usersettings {
		object NotificationItem {
			case class MoreFromAuthorsItem(
				opted_state: Option[String] = None
			)
			
			case class MoreFromSeriesItem(
				opted_state: Option[String] = None
			)
			
			case class RewardExpirationsItem(
				opted_state: Option[String] = None
			)
			
			case class PriceDropItem(
				opted_state: Option[String] = None
			)
			
			case class MatchMyInterestsItem(
				opted_state: Option[String] = None
			)
		}
		case class NotificationItem(
			moreFromAuthors: Option[Schema.Usersettings.NotificationItem.MoreFromAuthorsItem] = None,
			moreFromSeries: Option[Schema.Usersettings.NotificationItem.MoreFromSeriesItem] = None,
			rewardExpirations: Option[Schema.Usersettings.NotificationItem.RewardExpirationsItem] = None,
			priceDrop: Option[Schema.Usersettings.NotificationItem.PriceDropItem] = None,
			matchMyInterests: Option[Schema.Usersettings.NotificationItem.MatchMyInterestsItem] = None
		)
		
		case class NotesExportItem(
			isEnabled: Option[Boolean] = None,
			folderName: Option[String] = None
		)
	}
	case class Usersettings(
	  /** Resource type. */
		kind: Option[String] = None,
		notification: Option[Schema.Usersettings.NotificationItem] = None,
	  /** User settings in sub-objects, each for different purposes. */
		notesExport: Option[Schema.Usersettings.NotesExportItem] = None
	)
	
	case class DownloadAccesses(
	  /** A list of download access responses. */
		downloadAccessList: Option[List[Schema.DownloadAccessRestriction]] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	case class RequestAccessData(
	  /** A concurrent access response. */
		concurrentAccess: Option[Schema.ConcurrentAccessRestriction] = None,
	  /** A download access response. */
		downloadAccess: Option[Schema.DownloadAccessRestriction] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	case class ConcurrentAccessRestriction(
	  /** Whether access is granted for this (user, device, volume). */
		deviceAllowed: Option[Boolean] = None,
	  /** Time in seconds for license auto-expiration. */
		timeWindowSeconds: Option[Int] = None,
	  /** The maximum number of concurrent access licenses for this volume. */
		maxConcurrentDevices: Option[Int] = None,
	  /** Error/warning reason code. */
		reasonCode: Option[String] = None,
	  /** Error/warning message. */
		message: Option[String] = None,
	  /** Identifies the volume for which this entry applies. */
		volumeId: Option[String] = None,
	  /** Whether this volume has any concurrent access restrictions. */
		restricted: Option[Boolean] = None,
	  /** Client nonce for verification. Download access and client-validation only. */
		nonce: Option[String] = None,
	  /** Client app identifier for verification. Download access and client-validation only. */
		source: Option[String] = None,
	  /** Response signature. */
		signature: Option[String] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	object Annotation {
		case class CurrentVersionRangesItem(
		  /** Content version applicable to ranges below. */
			contentVersion: Option[String] = None,
		  /** Range in GB image format for this annotation for version above. */
			gbImageRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in GB text format for this annotation for version above. */
			gbTextRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in CFI format for this annotation for version above. */
			cfiRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in image CFI format for this annotation for version above. */
			imageCfiRange: Option[Schema.BooksAnnotationsRange] = None
		)
		
		case class LayerSummaryItem(
		  /** Remaining allowed characters on this layer, especially for the "copy" layer. */
			remainingCharacterCount: Option[Int] = None,
		  /** Maximum allowed characters on this layer, especially for the "copy" layer. */
			allowedCharacterCount: Option[Int] = None,
		  /** Type of limitation on this layer. "limited" or "unlimited" for the "copy" layer. */
			limitType: Option[String] = None
		)
		
		case class ClientVersionRangesItem(
		  /** Range in GB image format for this annotation sent by client. */
			gbImageRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in GB text format for this annotation sent by client. */
			gbTextRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Range in CFI format for this annotation sent by client. */
			cfiRange: Option[Schema.BooksAnnotationsRange] = None,
		  /** Content version the client sent in. */
			contentVersion: Option[String] = None,
		  /** Range in image CFI format for this annotation sent by client. */
			imageCfiRange: Option[Schema.BooksAnnotationsRange] = None
		)
	}
	case class Annotation(
	  /** Id of this annotation, in the form of a GUID. */
		id: Option[String] = None,
	  /** The volume that this annotation belongs to. */
		volumeId: Option[String] = None,
	  /** Selection ranges for the most recent content version. */
		currentVersionRanges: Option[Schema.Annotation.CurrentVersionRangesItem] = None,
	  /** Pages that this annotation spans. */
		pageIds: Option[List[String]] = None,
	  /** Excerpt from the volume. */
		selectedText: Option[String] = None,
	  /** Anchor text before excerpt. For requests, if the user bookmarked a screen that has no flowing text on it, then this field should be empty. */
		beforeSelectedText: Option[String] = None,
	  /** Anchor text after excerpt. For requests, if the user bookmarked a screen that has no flowing text on it, then this field should be empty. */
		afterSelectedText: Option[String] = None,
	  /** User-created data for this annotation. */
		data: Option[String] = None,
	  /** Resource type. */
		kind: Option[String] = None,
	  /** URL to this resource. */
		selfLink: Option[String] = None,
		layerSummary: Option[Schema.Annotation.LayerSummaryItem] = None,
	  /** The highlight style for this annotation. */
		highlightStyle: Option[String] = None,
	  /** Timestamp for the last time this annotation was modified. */
		updated: Option[String] = None,
	  /** Timestamp for the created time of this annotation. */
		created: Option[String] = None,
	  /** The layer this annotation is for. */
		layerId: Option[String] = None,
	  /** Indicates that this annotation is deleted. */
		deleted: Option[Boolean] = None,
	  /** Selection ranges sent from the client. */
		clientVersionRanges: Option[Schema.Annotation.ClientVersionRangesItem] = None
	)
	
	case class Annotations(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of annotations. */
		items: Option[List[Schema.Annotation]] = None,
	  /** Total number of annotations found. This may be greater than the number of notes returned in this response if results have been paginated. */
		totalItems: Option[Int] = None,
	  /** Token to pass in for pagination for the next page. This will not be present if this request does not have more results. */
		nextPageToken: Option[String] = None
	)
	
	object AnnotationsSummary {
		case class LayersItem(
			layerId: Option[String] = None,
			remainingCharacterCount: Option[Int] = None,
			allowedCharacterCount: Option[Int] = None,
			limitType: Option[String] = None,
			updated: Option[String] = None
		)
	}
	case class AnnotationsSummary(
		layers: Option[List[Schema.AnnotationsSummary.LayersItem]] = None,
		kind: Option[String] = None
	)
	
	case class Notification(
	  /** Resource type. */
		kind: Option[String] = None,
		title: Option[String] = None,
		body: Option[String] = None,
		iconUrl: Option[String] = None,
		targetUrl: Option[String] = None,
		notification_type: Option[String] = None,
		show_notification_settings_action: Option[Boolean] = None,
		pcampaign_id: Option[String] = None,
		dont_show_notification: Option[Boolean] = None,
		reason: Option[String] = None,
		doc_type: Option[String] = None,
		doc_id: Option[String] = None,
	  /** The list of crm experiment ids. */
		crmExperimentIds: Option[List[String]] = None,
		notificationGroup: Option[String] = None,
		is_document_mature: Option[Boolean] = None,
		timeToExpireMs: Option[String] = None
	)
	
	object Category {
		case class ItemsItem(
			name: Option[String] = None,
			categoryId: Option[String] = None,
			badgeUrl: Option[String] = None
		)
	}
	case class Category(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of onboarding categories. */
		items: Option[List[Schema.Category.ItemsItem]] = None
	)
	
	case class Volume2(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of volumes. */
		items: Option[List[Schema.Volume]] = None,
		nextPageToken: Option[String] = None
	)
	
	object Discoveryclusters {
		object ClustersItem {
			case class Banner_with_content_containerItem(
				imageUrl: Option[String] = None,
				moreButtonUrl: Option[String] = None,
				moreButtonText: Option[String] = None,
				maskColorArgb: Option[String] = None,
				fillColorArgb: Option[String] = None,
				textColorArgb: Option[String] = None
			)
		}
		case class ClustersItem(
			totalVolumes: Option[Int] = None,
			uid: Option[String] = None,
			title: Option[String] = None,
			subTitle: Option[String] = None,
			volumes: Option[List[Schema.Volume]] = None,
			banner_with_content_container: Option[Schema.Discoveryclusters.ClustersItem.Banner_with_content_containerItem] = None
		)
	}
	case class Discoveryclusters(
		totalClusters: Option[Int] = None,
	  /** Resorce type. */
		kind: Option[String] = None,
		clusters: Option[List[Schema.Discoveryclusters.ClustersItem]] = None
	)
	
	object Offers {
		object ItemsItem {
			case class ItemsItem(
				volumeId: Option[String] = None,
				canonicalVolumeLink: Option[String] = None,
				coverUrl: Option[String] = None,
				title: Option[String] = None,
				author: Option[String] = None,
				description: Option[String] = None
			)
		}
		case class ItemsItem(
			id: Option[String] = None,
			artUrl: Option[String] = None,
			items: Option[List[Schema.Offers.ItemsItem.ItemsItem]] = None,
			gservicesKey: Option[String] = None
		)
	}
	case class Offers(
	  /** Resource type. */
		kind: Option[String] = None,
	  /** A list of offers. */
		items: Option[List[Schema.Offers.ItemsItem]] = None
	)
	
	object Series {
		object SeriesItem {
			object SeriesSubscriptionReleaseInfoItem {
				case class CurrentReleaseInfoItem(
					releaseNumber: Option[String] = None,
					releaseTime: Option[String] = None,
					currencyCode: Option[String] = None,
					amountInMicros: Option[BigDecimal] = None
				)
				
				case class NextReleaseInfoItem(
					releaseNumber: Option[String] = None,
					releaseTime: Option[String] = None,
					currencyCode: Option[String] = None,
					amountInMicros: Option[BigDecimal] = None
				)
			}
			case class SeriesSubscriptionReleaseInfoItem(
				seriesSubscriptionType: Option[String] = None,
				currentReleaseInfo: Option[Schema.Series.SeriesItem.SeriesSubscriptionReleaseInfoItem.CurrentReleaseInfoItem] = None,
				nextReleaseInfo: Option[Schema.Series.SeriesItem.SeriesSubscriptionReleaseInfoItem.NextReleaseInfoItem] = None,
				cancelTime: Option[String] = None
			)
		}
		case class SeriesItem(
			seriesId: Option[String] = None,
			title: Option[String] = None,
			imageUrl: Option[String] = None,
			bannerImageUrl: Option[String] = None,
			seriesType: Option[String] = None,
			eligibleForSubscription: Option[Boolean] = None,
			isComplete: Option[Boolean] = None,
			subscriptionId: Option[String] = None,
			seriesSubscriptionReleaseInfo: Option[Schema.Series.SeriesItem.SeriesSubscriptionReleaseInfoItem] = None,
			seriesFormatType: Option[String] = None
		)
	}
	case class Series(
		series: Option[List[Schema.Series.SeriesItem]] = None,
	  /** Resource type. */
		kind: Option[String] = None
	)
	
	case class Seriesmembership(
		member: Option[List[Schema.Volume]] = None,
		nextPageToken: Option[String] = None,
	  /** Resorce type. */
		kind: Option[String] = None
	)
	
	case class BooksVolumesRecommendedRateResponse(
		consistency_token: Option[String] = None
	)
}
