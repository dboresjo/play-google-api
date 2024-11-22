package com.boresjo.play.api.google.vision

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class ProductSet(
	  /** The resource name of the ProductSet. Format is: `projects/PROJECT_ID/locations/LOC_ID/productSets/PRODUCT_SET_ID`. This field is ignored when creating a ProductSet. */
		name: Option[String] = None,
	  /** The user-provided name for this ProductSet. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** Output only. The time at which this ProductSet was last indexed. Query results will reflect all updates before this time. If this ProductSet has never been indexed, this timestamp is the default value "1970-01-01T00:00:00Z". This field is ignored when creating a ProductSet. */
		indexTime: Option[String] = None,
	  /** Output only. If there was an error with indexing the product set, the field is populated. This field is ignored when creating a ProductSet. */
		indexError: Option[Schema.Status] = None
	)
	
	case class ListProductSetsResponse(
	  /** List of ProductSets. */
		productSets: Option[List[Schema.ProductSet]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class Product(
	  /** The resource name of the product. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID`. This field is ignored when creating a product. */
		name: Option[String] = None,
	  /** The user-provided name for this Product. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** User-provided metadata to be stored with this product. Must be at most 4096 characters long. */
		description: Option[String] = None,
	  /** Immutable. The category for the product identified by the reference image. This should be one of "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1" or "general-v1". The legacy categories "homegoods", "apparel", and "toys" are still supported, but these should not be used for new products. */
		productCategory: Option[String] = None,
	  /** Key-value pairs that can be attached to a product. At query time, constraints can be specified based on the product_labels. Note that integer values can be provided as strings, e.g. "1199". Only strings with integer values can match a range-based restriction which is to be supported soon. Multiple values can be assigned to the same key. One product may have up to 500 product_labels. Notice that the total number of distinct product_labels over all products in one ProductSet cannot exceed 1M, otherwise the product search pipeline will refuse to work for that ProductSet. */
		productLabels: Option[List[Schema.KeyValue]] = None
	)
	
	case class KeyValue(
	  /** The key of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		key: Option[String] = None,
	  /** The value of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		value: Option[String] = None
	)
	
	case class ListProductsResponse(
	  /** List of products. */
		products: Option[List[Schema.Product]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ReferenceImage(
	  /** The resource name of the reference image. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID/referenceImages/IMAGE_ID`. This field is ignored when creating a reference image. */
		name: Option[String] = None,
	  /** Required. The Google Cloud Storage URI of the reference image. The URI must start with `gs://`. */
		uri: Option[String] = None,
	  /** Optional. Bounding polygons around the areas of interest in the reference image. If this field is empty, the system will try to detect regions of interest. At most 10 bounding polygons will be used. The provided shape is converted into a non-rotated rectangle. Once converted, the small edge of the rectangle must be greater than or equal to 300 pixels. The aspect ratio must be 1:4 or less (i.e. 1:3 is ok; 1:5 is not). */
		boundingPolys: Option[List[Schema.BoundingPoly]] = None
	)
	
	case class BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.NormalizedVertex]] = None
	)
	
	case class Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class ListReferenceImagesResponse(
	  /** The list of reference images. */
		referenceImages: Option[List[Schema.ReferenceImage]] = None,
	  /** The maximum number of items to return. Default 10, maximum 100. */
		pageSize: Option[Int] = None,
	  /** The next_page_token returned from a previous List request, if any. */
		nextPageToken: Option[String] = None
	)
	
	case class AddProductToProductSetRequest(
	  /** Required. The resource name for the Product to be added to this ProductSet. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID` */
		product: Option[String] = None
	)
	
	case class RemoveProductFromProductSetRequest(
	  /** Required. The resource name for the Product to be removed from this ProductSet. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID` */
		product: Option[String] = None
	)
	
	case class ListProductsInProductSetResponse(
	  /** The list of Products. */
		products: Option[List[Schema.Product]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ImportProductSetsRequest(
	  /** Required. The input content for the list of requests. */
		inputConfig: Option[Schema.ImportProductSetsInputConfig] = None
	)
	
	case class ImportProductSetsInputConfig(
	  /** The Google Cloud Storage location for a csv file which preserves a list of ImportProductSetRequests in each line. */
		gcsSource: Option[Schema.ImportProductSetsGcsSource] = None
	)
	
	case class ImportProductSetsGcsSource(
	  /** The Google Cloud Storage URI of the input csv file. The URI must start with `gs://`. The format of the input csv file should be one image per line. In each line, there are 8 columns. 1. image-uri 2. image-id 3. product-set-id 4. product-id 5. product-category 6. product-display-name 7. labels 8. bounding-poly The `image-uri`, `product-set-id`, `product-id`, and `product-category` columns are required. All other columns are optional. If the `ProductSet` or `Product` specified by the `product-set-id` and `product-id` values does not exist, then the system will create a new `ProductSet` or `Product` for the image. In this case, the `product-display-name` column refers to display_name, the `product-category` column refers to product_category, and the `labels` column refers to product_labels. The `image-id` column is optional but must be unique if provided. If it is empty, the system will automatically assign a unique id to the image. The `product-display-name` column is optional. If it is empty, the system sets the display_name field for the product to a space (" "). You can update the `display_name` later by using the API. If a `Product` with the specified `product-id` already exists, then the system ignores the `product-display-name`, `product-category`, and `labels` columns. The `labels` column (optional) is a line containing a list of comma-separated key-value pairs, in the following format: "key_1=value_1,key_2=value_2,...,key_n=value_n" The `bounding-poly` column (optional) identifies one region of interest from the image in the same manner as `CreateReferenceImage`. If you do not specify the `bounding-poly` column, then the system will try to detect regions of interest automatically. At most one `bounding-poly` column is allowed per line. If the image contains multiple regions of interest, add a line to the CSV file that includes the same product information, and the `bounding-poly` values for each region of interest. The `bounding-poly` column must contain an even number of comma-separated numbers, in the format "p1_x,p1_y,p2_x,p2_y,...,pn_x,pn_y". Use non-negative integers for absolute bounding polygons, and float values in [0, 1] for normalized bounding polygons. The system will resize the image if the image resolution is too large to process (larger than 20MP). */
		csvFileUri: Option[String] = None
	)
	
	case class PurgeProductsRequest(
	  /** Specify which ProductSet contains the Products to be deleted. */
		productSetPurgeConfig: Option[Schema.ProductSetPurgeConfig] = None,
	  /** If delete_orphan_products is true, all Products that are not in any ProductSet will be deleted. */
		deleteOrphanProducts: Option[Boolean] = None,
	  /** The default value is false. Override this value to true to actually perform the purge. */
		force: Option[Boolean] = None
	)
	
	case class ProductSetPurgeConfig(
	  /** The ProductSet that contains the Products to delete. If a Product is a member of product_set_id in addition to other ProductSets, the Product will still be deleted. */
		productSetId: Option[String] = None
	)
	
	case class BatchAnnotateImagesRequest(
	  /** Required. Individual image annotation requests for this batch. */
		requests: Option[List[Schema.AnnotateImageRequest]] = None,
	  /** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
		parent: Option[String] = None,
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None
	)
	
	case class AnnotateImageRequest(
	  /** The image to be processed. */
		image: Option[Schema.Image] = None,
	  /** Requested features. */
		features: Option[List[Schema.Feature]] = None,
	  /** Additional context that may accompany the image. */
		imageContext: Option[Schema.ImageContext] = None
	)
	
	case class Image(
	  /** Image content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateImages requests. It does not work for AsyncBatchAnnotateImages requests. */
		content: Option[String] = None,
	  /** Google Cloud Storage image location, or publicly-accessible image URL. If both `content` and `source` are provided for an image, `content` takes precedence and is used to perform the image annotation request. */
		source: Option[Schema.ImageSource] = None
	)
	
	case class ImageSource(
	  /** &#42;&#42;Use `image_uri` instead.&#42;&#42; The Google Cloud Storage URI of the form `gs://bucket_name/object_name`. Object versioning is not supported. See [Google Cloud Storage Request URIs](https://cloud.google.com/storage/docs/reference-uris) for more info. */
		gcsImageUri: Option[String] = None,
	  /** The URI of the source image. Can be either: 1. A Google Cloud Storage URI of the form `gs://bucket_name/object_name`. Object versioning is not supported. See [Google Cloud Storage Request URIs](https://cloud.google.com/storage/docs/reference-uris) for more info. 2. A publicly-accessible image HTTP/HTTPS URL. When fetching images from HTTP/HTTPS URLs, Google cannot guarantee that the request will be completed. Your request may fail if the specified host denies the request (e.g. due to request throttling or DOS prevention), or if Google throttles requests to the site for abuse prevention. You should not depend on externally-hosted images for production applications. When both `gcs_image_uri` and `image_uri` are specified, `image_uri` takes precedence. */
		imageUri: Option[String] = None
	)
	
	object Feature {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, FACE_DETECTION, LANDMARK_DETECTION, LOGO_DETECTION, LABEL_DETECTION, TEXT_DETECTION, DOCUMENT_TEXT_DETECTION, SAFE_SEARCH_DETECTION, IMAGE_PROPERTIES, CROP_HINTS, WEB_DETECTION, PRODUCT_SEARCH, OBJECT_LOCALIZATION }
	}
	case class Feature(
	  /** The feature type. */
		`type`: Option[Schema.Feature.TypeEnum] = None,
	  /** Maximum number of results of this type. Does not apply to `TEXT_DETECTION`, `DOCUMENT_TEXT_DETECTION`, or `CROP_HINTS`. */
		maxResults: Option[Int] = None,
	  /** Model to use for the feature. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". `DOCUMENT_TEXT_DETECTION` and `TEXT_DETECTION` also support "builtin/weekly" for the bleeding edge release updated weekly. */
		model: Option[String] = None
	)
	
	case class ImageContext(
	  /** Not used. */
		latLongRect: Option[Schema.LatLongRect] = None,
	  /** List of languages to use for TEXT_DETECTION. In most cases, an empty value yields the best results since it enables automatic language detection. For languages based on the Latin alphabet, setting `language_hints` is not needed. In rare cases, when the language of the text in the image is known, setting a hint will help get better results (although it will be a significant hindrance if the hint is wrong). Text detection returns an error if one or more of the specified languages is not one of the [supported languages](https://cloud.google.com/vision/docs/languages). */
		languageHints: Option[List[String]] = None,
	  /** Parameters for crop hints annotation request. */
		cropHintsParams: Option[Schema.CropHintsParams] = None,
	  /** Parameters for product search. */
		productSearchParams: Option[Schema.ProductSearchParams] = None,
	  /** Parameters for web detection. */
		webDetectionParams: Option[Schema.WebDetectionParams] = None,
	  /** Parameters for text detection and document text detection. */
		textDetectionParams: Option[Schema.TextDetectionParams] = None
	)
	
	case class LatLongRect(
	  /** Min lat/long pair. */
		minLatLng: Option[Schema.LatLng] = None,
	  /** Max lat/long pair. */
		maxLatLng: Option[Schema.LatLng] = None
	)
	
	case class LatLng(
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None,
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None
	)
	
	case class CropHintsParams(
	  /** Aspect ratios in floats, representing the ratio of the width to the height of the image. For example, if the desired aspect ratio is 4/3, the corresponding float value should be 1.33333. If not specified, the best possible crop is returned. The number of provided aspect ratios is limited to a maximum of 16; any aspect ratios provided after the 16th are ignored. */
		aspectRatios: Option[List[BigDecimal]] = None
	)
	
	case class ProductSearchParams(
	  /** The bounding polygon around the area of interest in the image. If it is not specified, system discretion will be applied. */
		boundingPoly: Option[Schema.BoundingPoly] = None,
	  /** The resource name of a ProductSet to be searched for similar images. Format is: `projects/PROJECT_ID/locations/LOC_ID/productSets/PRODUCT_SET_ID`. */
		productSet: Option[String] = None,
	  /** The list of product categories to search in. Currently, we only consider the first category, and either "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1", or "general-v1" should be specified. The legacy categories "homegoods", "apparel", and "toys" are still supported but will be deprecated. For new products, please use "homegoods-v2", "apparel-v2", or "toys-v2" for better product search accuracy. It is recommended to migrate existing products to these categories as well. */
		productCategories: Option[List[String]] = None,
	  /** The filtering expression. This can be used to restrict search results based on Product labels. We currently support an AND of OR of key-value expressions, where each expression within an OR must have the same key. An '=' should be used to connect the key and value. For example, "(color = red OR color = blue) AND brand = Google" is acceptable, but "(color = red OR brand = Google)" is not acceptable. "color: red" is not acceptable because it uses a ':' instead of an '='. */
		filter: Option[String] = None
	)
	
	case class WebDetectionParams(
	  /** This field has no effect on results. */
		includeGeoResults: Option[Boolean] = None
	)
	
	case class TextDetectionParams(
	  /** By default, Cloud Vision API only includes confidence score for DOCUMENT_TEXT_DETECTION result. Set the flag to true to include confidence score for TEXT_DETECTION as well. */
		enableTextDetectionConfidenceScore: Option[Boolean] = None,
	  /** A list of advanced OCR options to further fine-tune OCR behavior. Current valid values are: - `legacy_layout`: a heuristics layout detection algorithm, which serves as an alternative to the current ML-based layout detection algorithm. Customers can choose the best suitable layout algorithm based on their situation. */
		advancedOcrOptions: Option[List[String]] = None
	)
	
	case class BatchAnnotateImagesResponse(
	  /** Individual responses to image annotation requests within the batch. */
		responses: Option[List[Schema.AnnotateImageResponse]] = None
	)
	
	case class AnnotateImageResponse(
	  /** If present, face detection has completed successfully. */
		faceAnnotations: Option[List[Schema.FaceAnnotation]] = None,
	  /** If present, landmark detection has completed successfully. */
		landmarkAnnotations: Option[List[Schema.EntityAnnotation]] = None,
	  /** If present, logo detection has completed successfully. */
		logoAnnotations: Option[List[Schema.EntityAnnotation]] = None,
	  /** If present, label detection has completed successfully. */
		labelAnnotations: Option[List[Schema.EntityAnnotation]] = None,
	  /** If present, localized object detection has completed successfully. This will be sorted descending by confidence score. */
		localizedObjectAnnotations: Option[List[Schema.LocalizedObjectAnnotation]] = None,
	  /** If present, text (OCR) detection has completed successfully. */
		textAnnotations: Option[List[Schema.EntityAnnotation]] = None,
	  /** If present, text (OCR) detection or document (OCR) text detection has completed successfully. This annotation provides the structural hierarchy for the OCR detected text. */
		fullTextAnnotation: Option[Schema.TextAnnotation] = None,
	  /** If present, safe-search annotation has completed successfully. */
		safeSearchAnnotation: Option[Schema.SafeSearchAnnotation] = None,
	  /** If present, image properties were extracted successfully. */
		imagePropertiesAnnotation: Option[Schema.ImageProperties] = None,
	  /** If present, crop hints have completed successfully. */
		cropHintsAnnotation: Option[Schema.CropHintsAnnotation] = None,
	  /** If present, web detection has completed successfully. */
		webDetection: Option[Schema.WebDetection] = None,
	  /** If present, product search has completed successfully. */
		productSearchResults: Option[Schema.ProductSearchResults] = None,
	  /** If set, represents the error message for the operation. Note that filled-in image annotations are guaranteed to be correct, even when `error` is set. */
		error: Option[Schema.Status] = None,
	  /** If present, contextual information is needed to understand where this image comes from. */
		context: Option[Schema.ImageAnnotationContext] = None
	)
	
	object FaceAnnotation {
		enum JoyLikelihoodEnum extends Enum[JoyLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SorrowLikelihoodEnum extends Enum[SorrowLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum AngerLikelihoodEnum extends Enum[AngerLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SurpriseLikelihoodEnum extends Enum[SurpriseLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum UnderExposedLikelihoodEnum extends Enum[UnderExposedLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum BlurredLikelihoodEnum extends Enum[BlurredLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum HeadwearLikelihoodEnum extends Enum[HeadwearLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class FaceAnnotation(
	  /** The bounding polygon around the face. The coordinates of the bounding box are in the original image's scale. The bounding box is computed to "frame" the face in accordance with human expectations. It is based on the landmarker results. Note that one or more x and/or y coordinates may not be generated in the `BoundingPoly` (the polygon will be unbounded) if only a partial face appears in the image to be annotated. */
		boundingPoly: Option[Schema.BoundingPoly] = None,
	  /** The `fd_bounding_poly` bounding polygon is tighter than the `boundingPoly`, and encloses only the skin part of the face. Typically, it is used to eliminate the face from any image analysis that detects the "amount of skin" visible in an image. It is not based on the landmarker results, only on the initial face detection, hence the fd (face detection) prefix. */
		fdBoundingPoly: Option[Schema.BoundingPoly] = None,
	  /** Detected face landmarks. */
		landmarks: Option[List[Schema.Landmark]] = None,
	  /** Roll angle, which indicates the amount of clockwise/anti-clockwise rotation of the face relative to the image vertical about the axis perpendicular to the face. Range [-180,180]. */
		rollAngle: Option[BigDecimal] = None,
	  /** Yaw angle, which indicates the leftward/rightward angle that the face is pointing relative to the vertical plane perpendicular to the image. Range [-180,180]. */
		panAngle: Option[BigDecimal] = None,
	  /** Pitch angle, which indicates the upwards/downwards angle that the face is pointing relative to the image's horizontal plane. Range [-180,180]. */
		tiltAngle: Option[BigDecimal] = None,
	  /** Detection confidence. Range [0, 1]. */
		detectionConfidence: Option[BigDecimal] = None,
	  /** Face landmarking confidence. Range [0, 1]. */
		landmarkingConfidence: Option[BigDecimal] = None,
	  /** Joy likelihood. */
		joyLikelihood: Option[Schema.FaceAnnotation.JoyLikelihoodEnum] = None,
	  /** Sorrow likelihood. */
		sorrowLikelihood: Option[Schema.FaceAnnotation.SorrowLikelihoodEnum] = None,
	  /** Anger likelihood. */
		angerLikelihood: Option[Schema.FaceAnnotation.AngerLikelihoodEnum] = None,
	  /** Surprise likelihood. */
		surpriseLikelihood: Option[Schema.FaceAnnotation.SurpriseLikelihoodEnum] = None,
	  /** Under-exposed likelihood. */
		underExposedLikelihood: Option[Schema.FaceAnnotation.UnderExposedLikelihoodEnum] = None,
	  /** Blurred likelihood. */
		blurredLikelihood: Option[Schema.FaceAnnotation.BlurredLikelihoodEnum] = None,
	  /** Headwear likelihood. */
		headwearLikelihood: Option[Schema.FaceAnnotation.HeadwearLikelihoodEnum] = None
	)
	
	object Landmark {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN_LANDMARK, LEFT_EYE, RIGHT_EYE, LEFT_OF_LEFT_EYEBROW, RIGHT_OF_LEFT_EYEBROW, LEFT_OF_RIGHT_EYEBROW, RIGHT_OF_RIGHT_EYEBROW, MIDPOINT_BETWEEN_EYES, NOSE_TIP, UPPER_LIP, LOWER_LIP, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_CENTER, NOSE_BOTTOM_RIGHT, NOSE_BOTTOM_LEFT, NOSE_BOTTOM_CENTER, LEFT_EYE_TOP_BOUNDARY, LEFT_EYE_RIGHT_CORNER, LEFT_EYE_BOTTOM_BOUNDARY, LEFT_EYE_LEFT_CORNER, RIGHT_EYE_TOP_BOUNDARY, RIGHT_EYE_RIGHT_CORNER, RIGHT_EYE_BOTTOM_BOUNDARY, RIGHT_EYE_LEFT_CORNER, LEFT_EYEBROW_UPPER_MIDPOINT, RIGHT_EYEBROW_UPPER_MIDPOINT, LEFT_EAR_TRAGION, RIGHT_EAR_TRAGION, LEFT_EYE_PUPIL, RIGHT_EYE_PUPIL, FOREHEAD_GLABELLA, CHIN_GNATHION, CHIN_LEFT_GONION, CHIN_RIGHT_GONION, LEFT_CHEEK_CENTER, RIGHT_CHEEK_CENTER }
	}
	case class Landmark(
	  /** Face landmark type. */
		`type`: Option[Schema.Landmark.TypeEnum] = None,
	  /** Face landmark position. */
		position: Option[Schema.Position] = None
	)
	
	case class Position(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None,
	  /** Z coordinate (or depth). */
		z: Option[BigDecimal] = None
	)
	
	case class EntityAnnotation(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		mid: Option[String] = None,
	  /** The language code for the locale in which the entity textual `description` is expressed. */
		locale: Option[String] = None,
	  /** Entity textual description, expressed in its `locale` language. */
		description: Option[String] = None,
	  /** Overall score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** &#42;&#42;Deprecated. Use `score` instead.&#42;&#42; The accuracy of the entity detection in an image. For example, for an image in which the "Eiffel Tower" entity is detected, this field represents the confidence that there is a tower in the query image. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** The relevancy of the ICA (Image Content Annotation) label to the image. For example, the relevancy of "tower" is likely higher to an image containing the detected "Eiffel Tower" than to an image containing a detected distant towering building, even though the confidence that there is a tower in each image may be the same. Range [0, 1]. */
		topicality: Option[BigDecimal] = None,
	  /** Image region to which this entity belongs. Not produced for `LABEL_DETECTION` features. */
		boundingPoly: Option[Schema.BoundingPoly] = None,
	  /** The location information for the detected entity. Multiple `LocationInfo` elements can be present because one location may indicate the location of the scene in the image, and another location may indicate the location of the place where the image was taken. Location information is usually present for landmarks. */
		locations: Option[List[Schema.LocationInfo]] = None,
	  /** Some entities may have optional user-supplied `Property` (name/value) fields, such a score or string that qualifies the entity. */
		properties: Option[List[Schema.Property]] = None
	)
	
	case class LocationInfo(
	  /** lat/long location coordinates. */
		latLng: Option[Schema.LatLng] = None
	)
	
	case class Property(
	  /** Name of the property. */
		name: Option[String] = None,
	  /** Value of the property. */
		value: Option[String] = None,
	  /** Value of numeric properties. */
		uint64Value: Option[String] = None
	)
	
	case class LocalizedObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** Image region to which this object belongs. This must be populated. */
		boundingPoly: Option[Schema.BoundingPoly] = None
	)
	
	case class TextAnnotation(
	  /** List of pages detected by OCR. */
		pages: Option[List[Schema.Page]] = None,
	  /** UTF-8 text detected on the pages. */
		text: Option[String] = None
	)
	
	case class Page(
	  /** Additional information detected on the page. */
		property: Option[Schema.TextProperty] = None,
	  /** Page width. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		width: Option[Int] = None,
	  /** Page height. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		height: Option[Int] = None,
	  /** List of blocks of text, images etc on this page. */
		blocks: Option[List[Schema.Block]] = None,
	  /** Confidence of the OCR results on the page. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class TextProperty(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.DetectedLanguage]] = None,
	  /** Detected start or end of a text segment. */
		detectedBreak: Option[Schema.DetectedBreak] = None
	)
	
	case class DetectedLanguage(
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Confidence of detected language. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object DetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, SPACE, SURE_SPACE, EOL_SURE_SPACE, HYPHEN, LINE_BREAK }
	}
	case class DetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.DetectedBreak.TypeEnum] = None,
	  /** True if break prepends the element. */
		isPrefix: Option[Boolean] = None
	)
	
	object Block {
		enum BlockTypeEnum extends Enum[BlockTypeEnum] { case UNKNOWN, TEXT, TABLE, PICTURE, RULER, BARCODE }
	}
	case class Block(
	  /** Additional information detected for the block. */
		property: Option[Schema.TextProperty] = None,
	  /** The bounding box for the block. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.BoundingPoly] = None,
	  /** List of paragraphs in this block (if this blocks is of type text). */
		paragraphs: Option[List[Schema.Paragraph]] = None,
	  /** Detected block type (text, image etc) for this block. */
		blockType: Option[Schema.Block.BlockTypeEnum] = None,
	  /** Confidence of the OCR results on the block. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class Paragraph(
	  /** Additional information detected for the paragraph. */
		property: Option[Schema.TextProperty] = None,
	  /** The bounding box for the paragraph. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.BoundingPoly] = None,
	  /** List of all words in this paragraph. */
		words: Option[List[Schema.Word]] = None,
	  /** Confidence of the OCR results for the paragraph. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class Word(
	  /** Additional information detected for the word. */
		property: Option[Schema.TextProperty] = None,
	  /** The bounding box for the word. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.BoundingPoly] = None,
	  /** List of symbols in the word. The order of the symbols follows the natural reading order. */
		symbols: Option[List[Schema.Symbol]] = None,
	  /** Confidence of the OCR results for the word. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class Symbol(
	  /** Additional information detected for the symbol. */
		property: Option[Schema.TextProperty] = None,
	  /** The bounding box for the symbol. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.BoundingPoly] = None,
	  /** The actual UTF-8 representation of the symbol. */
		text: Option[String] = None,
	  /** Confidence of the OCR results for the symbol. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object SafeSearchAnnotation {
		enum AdultEnum extends Enum[AdultEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SpoofEnum extends Enum[SpoofEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum MedicalEnum extends Enum[MedicalEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ViolenceEnum extends Enum[ViolenceEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum RacyEnum extends Enum[RacyEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class SafeSearchAnnotation(
	  /** Represents the adult content likelihood for the image. Adult content may contain elements such as nudity, pornographic images or cartoons, or sexual activities. */
		adult: Option[Schema.SafeSearchAnnotation.AdultEnum] = None,
	  /** Spoof likelihood. The likelihood that an modification was made to the image's canonical version to make it appear funny or offensive. */
		spoof: Option[Schema.SafeSearchAnnotation.SpoofEnum] = None,
	  /** Likelihood that this is a medical image. */
		medical: Option[Schema.SafeSearchAnnotation.MedicalEnum] = None,
	  /** Likelihood that this image contains violent content. Violent content may include death, serious harm, or injury to individuals or groups of individuals. */
		violence: Option[Schema.SafeSearchAnnotation.ViolenceEnum] = None,
	  /** Likelihood that the request image contains racy content. Racy content may include (but is not limited to) skimpy or sheer clothing, strategically covered nudity, lewd or provocative poses, or close-ups of sensitive body areas. */
		racy: Option[Schema.SafeSearchAnnotation.RacyEnum] = None
	)
	
	case class ImageProperties(
	  /** If present, dominant colors completed successfully. */
		dominantColors: Option[Schema.DominantColorsAnnotation] = None
	)
	
	case class DominantColorsAnnotation(
	  /** RGB color values with their score and pixel fraction. */
		colors: Option[List[Schema.ColorInfo]] = None
	)
	
	case class ColorInfo(
	  /** RGB components of the color. */
		color: Option[Schema.Color] = None,
	  /** Image-specific score for this color. Value in range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** The fraction of pixels the color occupies in the image. Value in range [0, 1]. */
		pixelFraction: Option[BigDecimal] = None
	)
	
	case class Color(
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	case class CropHintsAnnotation(
	  /** Crop hint results. */
		cropHints: Option[List[Schema.CropHint]] = None
	)
	
	case class CropHint(
	  /** The bounding polygon for the crop region. The coordinates of the bounding box are in the original image's scale. */
		boundingPoly: Option[Schema.BoundingPoly] = None,
	  /** Confidence of this being a salient region. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Fraction of importance of this salient region with respect to the original image. */
		importanceFraction: Option[BigDecimal] = None
	)
	
	case class WebDetection(
	  /** Deduced entities from similar images on the Internet. */
		webEntities: Option[List[Schema.WebEntity]] = None,
	  /** Fully matching images from the Internet. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.WebImage]] = None,
	  /** Partial matching images from the Internet. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.WebImage]] = None,
	  /** Web pages containing the matching images from the Internet. */
		pagesWithMatchingImages: Option[List[Schema.WebPage]] = None,
	  /** The visually similar image results. */
		visuallySimilarImages: Option[List[Schema.WebImage]] = None,
	  /** The service's best guess as to the topic of the request image. Inferred from similar images on the open web. */
		bestGuessLabels: Option[List[Schema.WebLabel]] = None
	)
	
	case class WebEntity(
	  /** Opaque entity ID. */
		entityId: Option[String] = None,
	  /** Overall relevancy score for the entity. Not normalized and not comparable across different image queries. */
		score: Option[BigDecimal] = None,
	  /** Canonical description of the entity, in English. */
		description: Option[String] = None
	)
	
	case class WebImage(
	  /** The result image URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the image. */
		score: Option[BigDecimal] = None
	)
	
	case class WebPage(
	  /** The result web page URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the web page. */
		score: Option[BigDecimal] = None,
	  /** Title for the web page, may contain HTML markups. */
		pageTitle: Option[String] = None,
	  /** Fully matching images on the page. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.WebImage]] = None,
	  /** Partial matching images on the page. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.WebImage]] = None
	)
	
	case class WebLabel(
	  /** Label for extra metadata. */
		label: Option[String] = None,
	  /** The BCP-47 language code for `label`, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None
	)
	
	case class ProductSearchResults(
	  /** Timestamp of the index which provided these results. Products added to the product set and products removed from the product set after this time are not reflected in the current results. */
		indexTime: Option[String] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.Result]] = None,
	  /** List of results grouped by products detected in the query image. Each entry corresponds to one bounding polygon in the query image, and contains the matching products specific to that region. There may be duplicate product matches in the union of all the per-product results. */
		productGroupedResults: Option[List[Schema.GroupedResult]] = None
	)
	
	case class Result(
	  /** The Product. */
		product: Option[Schema.Product] = None,
	  /** A confidence level on the match, ranging from 0 (no confidence) to 1 (full confidence). */
		score: Option[BigDecimal] = None,
	  /** The resource name of the image from the product that is the closest match to the query. */
		image: Option[String] = None
	)
	
	case class GroupedResult(
	  /** The bounding polygon around the product detected in the query image. */
		boundingPoly: Option[Schema.BoundingPoly] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.Result]] = None,
	  /** List of generic predictions for the object in the bounding box. */
		objectAnnotations: Option[List[Schema.ObjectAnnotation]] = None
	)
	
	case class ObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None
	)
	
	case class ImageAnnotationContext(
	  /** The URI of the file used to produce the image. */
		uri: Option[String] = None,
	  /** If the file was a PDF or TIFF, this field gives the page number within the file used to produce the image. */
		pageNumber: Option[Int] = None
	)
	
	case class BatchAnnotateFilesRequest(
	  /** Required. The list of file annotation requests. Right now we support only one AnnotateFileRequest in BatchAnnotateFilesRequest. */
		requests: Option[List[Schema.AnnotateFileRequest]] = None,
	  /** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
		parent: Option[String] = None,
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None
	)
	
	case class AnnotateFileRequest(
	  /** Required. Information about the input file. */
		inputConfig: Option[Schema.InputConfig] = None,
	  /** Required. Requested features. */
		features: Option[List[Schema.Feature]] = None,
	  /** Additional context that may accompany the image(s) in the file. */
		imageContext: Option[Schema.ImageContext] = None,
	  /** Pages of the file to perform image annotation. Pages starts from 1, we assume the first page of the file is page 1. At most 5 pages are supported per request. Pages can be negative. Page 1 means the first page. Page 2 means the second page. Page -1 means the last page. Page -2 means the second to the last page. If the file is GIF instead of PDF or TIFF, page refers to GIF frames. If this field is empty, by default the service performs image annotation for the first 5 pages of the file. */
		pages: Option[List[Int]] = None
	)
	
	case class InputConfig(
	  /** The Google Cloud Storage location to read the input from. */
		gcsSource: Option[Schema.GcsSource] = None,
	  /** File content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateFiles requests. It does not work for AsyncBatchAnnotateFiles requests. */
		content: Option[String] = None,
	  /** The type of the file. Currently only "application/pdf", "image/tiff" and "image/gif" are supported. Wildcards are not supported. */
		mimeType: Option[String] = None
	)
	
	case class GcsSource(
	  /** Google Cloud Storage URI for the input file. This must only be a Google Cloud Storage object. Wildcards are not currently supported. */
		uri: Option[String] = None
	)
	
	case class BatchAnnotateFilesResponse(
	  /** The list of file annotation responses, each response corresponding to each AnnotateFileRequest in BatchAnnotateFilesRequest. */
		responses: Option[List[Schema.AnnotateFileResponse]] = None
	)
	
	case class AnnotateFileResponse(
	  /** Information about the file for which this response is generated. */
		inputConfig: Option[Schema.InputConfig] = None,
	  /** Individual responses to images found within the file. This field will be empty if the `error` field is set. */
		responses: Option[List[Schema.AnnotateImageResponse]] = None,
	  /** This field gives the total number of pages in the file. */
		totalPages: Option[Int] = None,
	  /** If set, represents the error message for the failed request. The `responses` field will not be set in this case. */
		error: Option[Schema.Status] = None
	)
	
	case class AsyncBatchAnnotateImagesRequest(
	  /** Required. Individual image annotation requests for this batch. */
		requests: Option[List[Schema.AnnotateImageRequest]] = None,
	  /** Required. The desired output location and metadata (e.g. format). */
		outputConfig: Option[Schema.OutputConfig] = None,
	  /** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
		parent: Option[String] = None,
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None
	)
	
	case class OutputConfig(
	  /** The Google Cloud Storage location to write the output(s) to. */
		gcsDestination: Option[Schema.GcsDestination] = None,
	  /** The max number of response protos to put into each output JSON file on Google Cloud Storage. The valid range is [1, 100]. If not specified, the default value is 20. For example, for one pdf file with 100 pages, 100 response protos will be generated. If `batch_size` = 20, then 5 json files each containing 20 response protos will be written under the prefix `gcs_destination`.`uri`. Currently, batch_size only applies to GcsDestination, with potential future support for other output configurations. */
		batchSize: Option[Int] = None
	)
	
	case class GcsDestination(
	  /** Google Cloud Storage URI prefix where the results will be stored. Results will be in JSON format and preceded by its corresponding input URI prefix. This field can either represent a gcs file prefix or gcs directory. In either case, the uri should be unique because in order to get all of the output files, you will need to do a wildcard gcs search on the uri prefix you provide. Examples: &#42; File Prefix: gs://bucket-name/here/filenameprefix The output files will be created in gs://bucket-name/here/ and the names of the output files will begin with "filenameprefix". &#42; Directory Prefix: gs://bucket-name/some/location/ The output files will be created in gs://bucket-name/some/location/ and the names of the output files could be anything because there was no filename prefix specified. If multiple outputs, each response is still AnnotateFileResponse, each of which contains some subset of the full list of AnnotateImageResponse. Multiple outputs can happen if, for example, the output JSON is too large and overflows into multiple sharded files. */
		uri: Option[String] = None
	)
	
	case class AsyncBatchAnnotateFilesRequest(
	  /** Required. Individual async file annotation requests for this batch. */
		requests: Option[List[Schema.AsyncAnnotateFileRequest]] = None,
	  /** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
		parent: Option[String] = None,
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None
	)
	
	case class AsyncAnnotateFileRequest(
	  /** Required. Information about the input file. */
		inputConfig: Option[Schema.InputConfig] = None,
	  /** Required. Requested features. */
		features: Option[List[Schema.Feature]] = None,
	  /** Additional context that may accompany the image(s) in the file. */
		imageContext: Option[Schema.ImageContext] = None,
	  /** Required. The desired output location and metadata (e.g. format). */
		outputConfig: Option[Schema.OutputConfig] = None
	)
	
	case class AsyncBatchAnnotateFilesResponse(
	  /** The list of file annotation responses, one for each request in AsyncBatchAnnotateFilesRequest. */
		responses: Option[List[Schema.AsyncAnnotateFileResponse]] = None
	)
	
	case class AsyncAnnotateFileResponse(
	  /** The output location and metadata from AsyncAnnotateFileRequest. */
		outputConfig: Option[Schema.OutputConfig] = None
	)
	
	case class AsyncBatchAnnotateImagesResponse(
	  /** The output location and metadata from AsyncBatchAnnotateImagesRequest. */
		outputConfig: Option[Schema.OutputConfig] = None
	)
	
	object BatchOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROCESSING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class BatchOperationMetadata(
	  /** The current state of the batch operation. */
		state: Option[Schema.BatchOperationMetadata.StateEnum] = None,
	  /** The time when the batch request was submitted to the server. */
		submitTime: Option[String] = None,
	  /** The time when the batch request is finished and google.longrunning.Operation.done is set to true. */
		endTime: Option[String] = None
	)
	
	case class ImportProductSetsResponse(
	  /** The list of reference_images that are imported successfully. */
		referenceImages: Option[List[Schema.ReferenceImage]] = None,
	  /** The rpc status for each ImportProductSet request, including both successes and errors. The number of statuses here matches the number of lines in the csv file, and statuses[i] stores the success or failure status of processing the i-th line of the csv, starting from line 0. */
		statuses: Option[List[Schema.Status]] = None
	)
	
	object OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, RUNNING, DONE, CANCELLED }
	}
	case class OperationMetadata(
	  /** Current state of the batch operation. */
		state: Option[Schema.OperationMetadata.StateEnum] = None,
	  /** The time when the batch request was received. */
		createTime: Option[String] = None,
	  /** The time when the operation result was last updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1AnnotateFileResponse(
	  /** Information about the file for which this response is generated. */
		inputConfig: Option[Schema.GoogleCloudVisionV1p1beta1InputConfig] = None,
	  /** Individual responses to images found within the file. This field will be empty if the `error` field is set. */
		responses: Option[List[Schema.GoogleCloudVisionV1p1beta1AnnotateImageResponse]] = None,
	  /** This field gives the total number of pages in the file. */
		totalPages: Option[Int] = None,
	  /** If set, represents the error message for the failed request. The `responses` field will not be set in this case. */
		error: Option[Schema.Status] = None
	)
	
	case class GoogleCloudVisionV1p1beta1InputConfig(
	  /** The Google Cloud Storage location to read the input from. */
		gcsSource: Option[Schema.GoogleCloudVisionV1p1beta1GcsSource] = None,
	  /** File content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateFiles requests. It does not work for AsyncBatchAnnotateFiles requests. */
		content: Option[String] = None,
	  /** The type of the file. Currently only "application/pdf", "image/tiff" and "image/gif" are supported. Wildcards are not supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1GcsSource(
	  /** Google Cloud Storage URI for the input file. This must only be a Google Cloud Storage object. Wildcards are not currently supported. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1AnnotateImageResponse(
	  /** If present, face detection has completed successfully. */
		faceAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation]] = None,
	  /** If present, landmark detection has completed successfully. */
		landmarkAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1EntityAnnotation]] = None,
	  /** If present, logo detection has completed successfully. */
		logoAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1EntityAnnotation]] = None,
	  /** If present, label detection has completed successfully. */
		labelAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1EntityAnnotation]] = None,
	  /** If present, localized object detection has completed successfully. This will be sorted descending by confidence score. */
		localizedObjectAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1LocalizedObjectAnnotation]] = None,
	  /** If present, text (OCR) detection has completed successfully. */
		textAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1EntityAnnotation]] = None,
	  /** If present, text (OCR) detection or document (OCR) text detection has completed successfully. This annotation provides the structural hierarchy for the OCR detected text. */
		fullTextAnnotation: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotation] = None,
	  /** If present, safe-search annotation has completed successfully. */
		safeSearchAnnotation: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation] = None,
	  /** If present, image properties were extracted successfully. */
		imagePropertiesAnnotation: Option[Schema.GoogleCloudVisionV1p1beta1ImageProperties] = None,
	  /** If present, crop hints have completed successfully. */
		cropHintsAnnotation: Option[Schema.GoogleCloudVisionV1p1beta1CropHintsAnnotation] = None,
	  /** If present, web detection has completed successfully. */
		webDetection: Option[Schema.GoogleCloudVisionV1p1beta1WebDetection] = None,
	  /** If present, product search has completed successfully. */
		productSearchResults: Option[Schema.GoogleCloudVisionV1p1beta1ProductSearchResults] = None,
	  /** If set, represents the error message for the operation. Note that filled-in image annotations are guaranteed to be correct, even when `error` is set. */
		error: Option[Schema.Status] = None,
	  /** If present, contextual information is needed to understand where this image comes from. */
		context: Option[Schema.GoogleCloudVisionV1p1beta1ImageAnnotationContext] = None
	)
	
	object GoogleCloudVisionV1p1beta1FaceAnnotation {
		enum JoyLikelihoodEnum extends Enum[JoyLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SorrowLikelihoodEnum extends Enum[SorrowLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum AngerLikelihoodEnum extends Enum[AngerLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SurpriseLikelihoodEnum extends Enum[SurpriseLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum UnderExposedLikelihoodEnum extends Enum[UnderExposedLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum BlurredLikelihoodEnum extends Enum[BlurredLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum HeadwearLikelihoodEnum extends Enum[HeadwearLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p1beta1FaceAnnotation(
	  /** The bounding polygon around the face. The coordinates of the bounding box are in the original image's scale. The bounding box is computed to "frame" the face in accordance with human expectations. It is based on the landmarker results. Note that one or more x and/or y coordinates may not be generated in the `BoundingPoly` (the polygon will be unbounded) if only a partial face appears in the image to be annotated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** The `fd_bounding_poly` bounding polygon is tighter than the `boundingPoly`, and encloses only the skin part of the face. Typically, it is used to eliminate the face from any image analysis that detects the "amount of skin" visible in an image. It is not based on the landmarker results, only on the initial face detection, hence the fd (face detection) prefix. */
		fdBoundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** Detected face landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVisionV1p1beta1FaceAnnotationLandmark]] = None,
	  /** Roll angle, which indicates the amount of clockwise/anti-clockwise rotation of the face relative to the image vertical about the axis perpendicular to the face. Range [-180,180]. */
		rollAngle: Option[BigDecimal] = None,
	  /** Yaw angle, which indicates the leftward/rightward angle that the face is pointing relative to the vertical plane perpendicular to the image. Range [-180,180]. */
		panAngle: Option[BigDecimal] = None,
	  /** Pitch angle, which indicates the upwards/downwards angle that the face is pointing relative to the image's horizontal plane. Range [-180,180]. */
		tiltAngle: Option[BigDecimal] = None,
	  /** Detection confidence. Range [0, 1]. */
		detectionConfidence: Option[BigDecimal] = None,
	  /** Face landmarking confidence. Range [0, 1]. */
		landmarkingConfidence: Option[BigDecimal] = None,
	  /** Joy likelihood. */
		joyLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.JoyLikelihoodEnum] = None,
	  /** Sorrow likelihood. */
		sorrowLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.SorrowLikelihoodEnum] = None,
	  /** Anger likelihood. */
		angerLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.AngerLikelihoodEnum] = None,
	  /** Surprise likelihood. */
		surpriseLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.SurpriseLikelihoodEnum] = None,
	  /** Under-exposed likelihood. */
		underExposedLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.UnderExposedLikelihoodEnum] = None,
	  /** Blurred likelihood. */
		blurredLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.BlurredLikelihoodEnum] = None,
	  /** Headwear likelihood. */
		headwearLikelihood: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotation.HeadwearLikelihoodEnum] = None
	)
	
	case class GoogleCloudVisionV1p1beta1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudVisionV1p1beta1Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudVisionV1p1beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p1beta1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p1beta1FaceAnnotationLandmark {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN_LANDMARK, LEFT_EYE, RIGHT_EYE, LEFT_OF_LEFT_EYEBROW, RIGHT_OF_LEFT_EYEBROW, LEFT_OF_RIGHT_EYEBROW, RIGHT_OF_RIGHT_EYEBROW, MIDPOINT_BETWEEN_EYES, NOSE_TIP, UPPER_LIP, LOWER_LIP, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_CENTER, NOSE_BOTTOM_RIGHT, NOSE_BOTTOM_LEFT, NOSE_BOTTOM_CENTER, LEFT_EYE_TOP_BOUNDARY, LEFT_EYE_RIGHT_CORNER, LEFT_EYE_BOTTOM_BOUNDARY, LEFT_EYE_LEFT_CORNER, RIGHT_EYE_TOP_BOUNDARY, RIGHT_EYE_RIGHT_CORNER, RIGHT_EYE_BOTTOM_BOUNDARY, RIGHT_EYE_LEFT_CORNER, LEFT_EYEBROW_UPPER_MIDPOINT, RIGHT_EYEBROW_UPPER_MIDPOINT, LEFT_EAR_TRAGION, RIGHT_EAR_TRAGION, LEFT_EYE_PUPIL, RIGHT_EYE_PUPIL, FOREHEAD_GLABELLA, CHIN_GNATHION, CHIN_LEFT_GONION, CHIN_RIGHT_GONION, LEFT_CHEEK_CENTER, RIGHT_CHEEK_CENTER }
	}
	case class GoogleCloudVisionV1p1beta1FaceAnnotationLandmark(
	  /** Face landmark type. */
		`type`: Option[Schema.GoogleCloudVisionV1p1beta1FaceAnnotationLandmark.TypeEnum] = None,
	  /** Face landmark position. */
		position: Option[Schema.GoogleCloudVisionV1p1beta1Position] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Position(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None,
	  /** Z coordinate (or depth). */
		z: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1EntityAnnotation(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		mid: Option[String] = None,
	  /** The language code for the locale in which the entity textual `description` is expressed. */
		locale: Option[String] = None,
	  /** Entity textual description, expressed in its `locale` language. */
		description: Option[String] = None,
	  /** Overall score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** &#42;&#42;Deprecated. Use `score` instead.&#42;&#42; The accuracy of the entity detection in an image. For example, for an image in which the "Eiffel Tower" entity is detected, this field represents the confidence that there is a tower in the query image. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** The relevancy of the ICA (Image Content Annotation) label to the image. For example, the relevancy of "tower" is likely higher to an image containing the detected "Eiffel Tower" than to an image containing a detected distant towering building, even though the confidence that there is a tower in each image may be the same. Range [0, 1]. */
		topicality: Option[BigDecimal] = None,
	  /** Image region to which this entity belongs. Not produced for `LABEL_DETECTION` features. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** The location information for the detected entity. Multiple `LocationInfo` elements can be present because one location may indicate the location of the scene in the image, and another location may indicate the location of the place where the image was taken. Location information is usually present for landmarks. */
		locations: Option[List[Schema.GoogleCloudVisionV1p1beta1LocationInfo]] = None,
	  /** Some entities may have optional user-supplied `Property` (name/value) fields, such a score or string that qualifies the entity. */
		properties: Option[List[Schema.GoogleCloudVisionV1p1beta1Property]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1LocationInfo(
	  /** lat/long location coordinates. */
		latLng: Option[Schema.LatLng] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Property(
	  /** Name of the property. */
		name: Option[String] = None,
	  /** Value of the property. */
		value: Option[String] = None,
	  /** Value of numeric properties. */
		uint64Value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1LocalizedObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** Image region to which this object belongs. This must be populated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None
	)
	
	case class GoogleCloudVisionV1p1beta1TextAnnotation(
	  /** List of pages detected by OCR. */
		pages: Option[List[Schema.GoogleCloudVisionV1p1beta1Page]] = None,
	  /** UTF-8 text detected on the pages. */
		text: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Page(
	  /** Additional information detected on the page. */
		property: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationTextProperty] = None,
	  /** Page width. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		width: Option[Int] = None,
	  /** Page height. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		height: Option[Int] = None,
	  /** List of blocks of text, images etc on this page. */
		blocks: Option[List[Schema.GoogleCloudVisionV1p1beta1Block]] = None,
	  /** Confidence of the OCR results on the page. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1TextAnnotationTextProperty(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudVisionV1p1beta1TextAnnotationDetectedLanguage]] = None,
	  /** Detected start or end of a text segment. */
		detectedBreak: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationDetectedBreak] = None
	)
	
	case class GoogleCloudVisionV1p1beta1TextAnnotationDetectedLanguage(
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Confidence of detected language. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p1beta1TextAnnotationDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, SPACE, SURE_SPACE, EOL_SURE_SPACE, HYPHEN, LINE_BREAK }
	}
	case class GoogleCloudVisionV1p1beta1TextAnnotationDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationDetectedBreak.TypeEnum] = None,
	  /** True if break prepends the element. */
		isPrefix: Option[Boolean] = None
	)
	
	object GoogleCloudVisionV1p1beta1Block {
		enum BlockTypeEnum extends Enum[BlockTypeEnum] { case UNKNOWN, TEXT, TABLE, PICTURE, RULER, BARCODE }
	}
	case class GoogleCloudVisionV1p1beta1Block(
	  /** Additional information detected for the block. */
		property: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the block. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** List of paragraphs in this block (if this blocks is of type text). */
		paragraphs: Option[List[Schema.GoogleCloudVisionV1p1beta1Paragraph]] = None,
	  /** Detected block type (text, image etc) for this block. */
		blockType: Option[Schema.GoogleCloudVisionV1p1beta1Block.BlockTypeEnum] = None,
	  /** Confidence of the OCR results on the block. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Paragraph(
	  /** Additional information detected for the paragraph. */
		property: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the paragraph. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** List of all words in this paragraph. */
		words: Option[List[Schema.GoogleCloudVisionV1p1beta1Word]] = None,
	  /** Confidence of the OCR results for the paragraph. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Word(
	  /** Additional information detected for the word. */
		property: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the word. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** List of symbols in the word. The order of the symbols follows the natural reading order. */
		symbols: Option[List[Schema.GoogleCloudVisionV1p1beta1Symbol]] = None,
	  /** Confidence of the OCR results for the word. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Symbol(
	  /** Additional information detected for the symbol. */
		property: Option[Schema.GoogleCloudVisionV1p1beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the symbol. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** The actual UTF-8 representation of the symbol. */
		text: Option[String] = None,
	  /** Confidence of the OCR results for the symbol. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p1beta1SafeSearchAnnotation {
		enum AdultEnum extends Enum[AdultEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SpoofEnum extends Enum[SpoofEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum MedicalEnum extends Enum[MedicalEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ViolenceEnum extends Enum[ViolenceEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum RacyEnum extends Enum[RacyEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p1beta1SafeSearchAnnotation(
	  /** Represents the adult content likelihood for the image. Adult content may contain elements such as nudity, pornographic images or cartoons, or sexual activities. */
		adult: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation.AdultEnum] = None,
	  /** Spoof likelihood. The likelihood that an modification was made to the image's canonical version to make it appear funny or offensive. */
		spoof: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation.SpoofEnum] = None,
	  /** Likelihood that this is a medical image. */
		medical: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation.MedicalEnum] = None,
	  /** Likelihood that this image contains violent content. Violent content may include death, serious harm, or injury to individuals or groups of individuals. */
		violence: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation.ViolenceEnum] = None,
	  /** Likelihood that the request image contains racy content. Racy content may include (but is not limited to) skimpy or sheer clothing, strategically covered nudity, lewd or provocative poses, or close-ups of sensitive body areas. */
		racy: Option[Schema.GoogleCloudVisionV1p1beta1SafeSearchAnnotation.RacyEnum] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ImageProperties(
	  /** If present, dominant colors completed successfully. */
		dominantColors: Option[Schema.GoogleCloudVisionV1p1beta1DominantColorsAnnotation] = None
	)
	
	case class GoogleCloudVisionV1p1beta1DominantColorsAnnotation(
	  /** RGB color values with their score and pixel fraction. */
		colors: Option[List[Schema.GoogleCloudVisionV1p1beta1ColorInfo]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ColorInfo(
	  /** RGB components of the color. */
		color: Option[Schema.Color] = None,
	  /** Image-specific score for this color. Value in range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** The fraction of pixels the color occupies in the image. Value in range [0, 1]. */
		pixelFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1CropHintsAnnotation(
	  /** Crop hint results. */
		cropHints: Option[List[Schema.GoogleCloudVisionV1p1beta1CropHint]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1CropHint(
	  /** The bounding polygon for the crop region. The coordinates of the bounding box are in the original image's scale. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** Confidence of this being a salient region. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Fraction of importance of this salient region with respect to the original image. */
		importanceFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1WebDetection(
	  /** Deduced entities from similar images on the Internet. */
		webEntities: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebEntity]] = None,
	  /** Fully matching images from the Internet. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebImage]] = None,
	  /** Partial matching images from the Internet. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebImage]] = None,
	  /** Web pages containing the matching images from the Internet. */
		pagesWithMatchingImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebPage]] = None,
	  /** The visually similar image results. */
		visuallySimilarImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebImage]] = None,
	  /** The service's best guess as to the topic of the request image. Inferred from similar images on the open web. */
		bestGuessLabels: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebLabel]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1WebDetectionWebEntity(
	  /** Opaque entity ID. */
		entityId: Option[String] = None,
	  /** Overall relevancy score for the entity. Not normalized and not comparable across different image queries. */
		score: Option[BigDecimal] = None,
	  /** Canonical description of the entity, in English. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1WebDetectionWebImage(
	  /** The result image URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the image. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1WebDetectionWebPage(
	  /** The result web page URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the web page. */
		score: Option[BigDecimal] = None,
	  /** Title for the web page, may contain HTML markups. */
		pageTitle: Option[String] = None,
	  /** Fully matching images on the page. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebImage]] = None,
	  /** Partial matching images on the page. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p1beta1WebDetectionWebImage]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1WebDetectionWebLabel(
	  /** Label for extra metadata. */
		label: Option[String] = None,
	  /** The BCP-47 language code for `label`, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ProductSearchResults(
	  /** Timestamp of the index which provided these results. Products added to the product set and products removed from the product set after this time are not reflected in the current results. */
		indexTime: Option[String] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p1beta1ProductSearchResultsResult]] = None,
	  /** List of results grouped by products detected in the query image. Each entry corresponds to one bounding polygon in the query image, and contains the matching products specific to that region. There may be duplicate product matches in the union of all the per-product results. */
		productGroupedResults: Option[List[Schema.GoogleCloudVisionV1p1beta1ProductSearchResultsGroupedResult]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ProductSearchResultsResult(
	  /** The Product. */
		product: Option[Schema.GoogleCloudVisionV1p1beta1Product] = None,
	  /** A confidence level on the match, ranging from 0 (no confidence) to 1 (full confidence). */
		score: Option[BigDecimal] = None,
	  /** The resource name of the image from the product that is the closest match to the query. */
		image: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1Product(
	  /** The resource name of the product. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID`. This field is ignored when creating a product. */
		name: Option[String] = None,
	  /** The user-provided name for this Product. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** User-provided metadata to be stored with this product. Must be at most 4096 characters long. */
		description: Option[String] = None,
	  /** Immutable. The category for the product identified by the reference image. This should be one of "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1" or "general-v1". The legacy categories "homegoods", "apparel", and "toys" are still supported, but these should not be used for new products. */
		productCategory: Option[String] = None,
	  /** Key-value pairs that can be attached to a product. At query time, constraints can be specified based on the product_labels. Note that integer values can be provided as strings, e.g. "1199". Only strings with integer values can match a range-based restriction which is to be supported soon. Multiple values can be assigned to the same key. One product may have up to 500 product_labels. Notice that the total number of distinct product_labels over all products in one ProductSet cannot exceed 1M, otherwise the product search pipeline will refuse to work for that ProductSet. */
		productLabels: Option[List[Schema.GoogleCloudVisionV1p1beta1ProductKeyValue]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ProductKeyValue(
	  /** The key of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		key: Option[String] = None,
	  /** The value of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ProductSearchResultsGroupedResult(
	  /** The bounding polygon around the product detected in the query image. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p1beta1BoundingPoly] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p1beta1ProductSearchResultsResult]] = None,
	  /** List of generic predictions for the object in the bounding box. */
		objectAnnotations: Option[List[Schema.GoogleCloudVisionV1p1beta1ProductSearchResultsObjectAnnotation]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ProductSearchResultsObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p1beta1ImageAnnotationContext(
	  /** The URI of the file used to produce the image. */
		uri: Option[String] = None,
	  /** If the file was a PDF or TIFF, this field gives the page number within the file used to produce the image. */
		pageNumber: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p1beta1AsyncBatchAnnotateFilesResponse(
	  /** The list of file annotation responses, one for each request in AsyncBatchAnnotateFilesRequest. */
		responses: Option[List[Schema.GoogleCloudVisionV1p1beta1AsyncAnnotateFileResponse]] = None
	)
	
	case class GoogleCloudVisionV1p1beta1AsyncAnnotateFileResponse(
	  /** The output location and metadata from AsyncAnnotateFileRequest. */
		outputConfig: Option[Schema.GoogleCloudVisionV1p1beta1OutputConfig] = None
	)
	
	case class GoogleCloudVisionV1p1beta1OutputConfig(
	  /** The Google Cloud Storage location to write the output(s) to. */
		gcsDestination: Option[Schema.GoogleCloudVisionV1p1beta1GcsDestination] = None,
	  /** The max number of response protos to put into each output JSON file on Google Cloud Storage. The valid range is [1, 100]. If not specified, the default value is 20. For example, for one pdf file with 100 pages, 100 response protos will be generated. If `batch_size` = 20, then 5 json files each containing 20 response protos will be written under the prefix `gcs_destination`.`uri`. Currently, batch_size only applies to GcsDestination, with potential future support for other output configurations. */
		batchSize: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p1beta1GcsDestination(
	  /** Google Cloud Storage URI prefix where the results will be stored. Results will be in JSON format and preceded by its corresponding input URI prefix. This field can either represent a gcs file prefix or gcs directory. In either case, the uri should be unique because in order to get all of the output files, you will need to do a wildcard gcs search on the uri prefix you provide. Examples: &#42; File Prefix: gs://bucket-name/here/filenameprefix The output files will be created in gs://bucket-name/here/ and the names of the output files will begin with "filenameprefix". &#42; Directory Prefix: gs://bucket-name/some/location/ The output files will be created in gs://bucket-name/some/location/ and the names of the output files could be anything because there was no filename prefix specified. If multiple outputs, each response is still AnnotateFileResponse, each of which contains some subset of the full list of AnnotateImageResponse. Multiple outputs can happen if, for example, the output JSON is too large and overflows into multiple sharded files. */
		uri: Option[String] = None
	)
	
	object GoogleCloudVisionV1p1beta1OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, RUNNING, DONE, CANCELLED }
	}
	case class GoogleCloudVisionV1p1beta1OperationMetadata(
	  /** Current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p1beta1OperationMetadata.StateEnum] = None,
	  /** The time when the batch request was received. */
		createTime: Option[String] = None,
	  /** The time when the operation result was last updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1AnnotateFileResponse(
	  /** Information about the file for which this response is generated. */
		inputConfig: Option[Schema.GoogleCloudVisionV1p2beta1InputConfig] = None,
	  /** Individual responses to images found within the file. This field will be empty if the `error` field is set. */
		responses: Option[List[Schema.GoogleCloudVisionV1p2beta1AnnotateImageResponse]] = None,
	  /** This field gives the total number of pages in the file. */
		totalPages: Option[Int] = None,
	  /** If set, represents the error message for the failed request. The `responses` field will not be set in this case. */
		error: Option[Schema.Status] = None
	)
	
	case class GoogleCloudVisionV1p2beta1InputConfig(
	  /** The Google Cloud Storage location to read the input from. */
		gcsSource: Option[Schema.GoogleCloudVisionV1p2beta1GcsSource] = None,
	  /** File content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateFiles requests. It does not work for AsyncBatchAnnotateFiles requests. */
		content: Option[String] = None,
	  /** The type of the file. Currently only "application/pdf", "image/tiff" and "image/gif" are supported. Wildcards are not supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1GcsSource(
	  /** Google Cloud Storage URI for the input file. This must only be a Google Cloud Storage object. Wildcards are not currently supported. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1AnnotateImageResponse(
	  /** If present, face detection has completed successfully. */
		faceAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation]] = None,
	  /** If present, landmark detection has completed successfully. */
		landmarkAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1EntityAnnotation]] = None,
	  /** If present, logo detection has completed successfully. */
		logoAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1EntityAnnotation]] = None,
	  /** If present, label detection has completed successfully. */
		labelAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1EntityAnnotation]] = None,
	  /** If present, localized object detection has completed successfully. This will be sorted descending by confidence score. */
		localizedObjectAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1LocalizedObjectAnnotation]] = None,
	  /** If present, text (OCR) detection has completed successfully. */
		textAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1EntityAnnotation]] = None,
	  /** If present, text (OCR) detection or document (OCR) text detection has completed successfully. This annotation provides the structural hierarchy for the OCR detected text. */
		fullTextAnnotation: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotation] = None,
	  /** If present, safe-search annotation has completed successfully. */
		safeSearchAnnotation: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation] = None,
	  /** If present, image properties were extracted successfully. */
		imagePropertiesAnnotation: Option[Schema.GoogleCloudVisionV1p2beta1ImageProperties] = None,
	  /** If present, crop hints have completed successfully. */
		cropHintsAnnotation: Option[Schema.GoogleCloudVisionV1p2beta1CropHintsAnnotation] = None,
	  /** If present, web detection has completed successfully. */
		webDetection: Option[Schema.GoogleCloudVisionV1p2beta1WebDetection] = None,
	  /** If present, product search has completed successfully. */
		productSearchResults: Option[Schema.GoogleCloudVisionV1p2beta1ProductSearchResults] = None,
	  /** If set, represents the error message for the operation. Note that filled-in image annotations are guaranteed to be correct, even when `error` is set. */
		error: Option[Schema.Status] = None,
	  /** If present, contextual information is needed to understand where this image comes from. */
		context: Option[Schema.GoogleCloudVisionV1p2beta1ImageAnnotationContext] = None
	)
	
	object GoogleCloudVisionV1p2beta1FaceAnnotation {
		enum JoyLikelihoodEnum extends Enum[JoyLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SorrowLikelihoodEnum extends Enum[SorrowLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum AngerLikelihoodEnum extends Enum[AngerLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SurpriseLikelihoodEnum extends Enum[SurpriseLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum UnderExposedLikelihoodEnum extends Enum[UnderExposedLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum BlurredLikelihoodEnum extends Enum[BlurredLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum HeadwearLikelihoodEnum extends Enum[HeadwearLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p2beta1FaceAnnotation(
	  /** The bounding polygon around the face. The coordinates of the bounding box are in the original image's scale. The bounding box is computed to "frame" the face in accordance with human expectations. It is based on the landmarker results. Note that one or more x and/or y coordinates may not be generated in the `BoundingPoly` (the polygon will be unbounded) if only a partial face appears in the image to be annotated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** The `fd_bounding_poly` bounding polygon is tighter than the `boundingPoly`, and encloses only the skin part of the face. Typically, it is used to eliminate the face from any image analysis that detects the "amount of skin" visible in an image. It is not based on the landmarker results, only on the initial face detection, hence the fd (face detection) prefix. */
		fdBoundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** Detected face landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVisionV1p2beta1FaceAnnotationLandmark]] = None,
	  /** Roll angle, which indicates the amount of clockwise/anti-clockwise rotation of the face relative to the image vertical about the axis perpendicular to the face. Range [-180,180]. */
		rollAngle: Option[BigDecimal] = None,
	  /** Yaw angle, which indicates the leftward/rightward angle that the face is pointing relative to the vertical plane perpendicular to the image. Range [-180,180]. */
		panAngle: Option[BigDecimal] = None,
	  /** Pitch angle, which indicates the upwards/downwards angle that the face is pointing relative to the image's horizontal plane. Range [-180,180]. */
		tiltAngle: Option[BigDecimal] = None,
	  /** Detection confidence. Range [0, 1]. */
		detectionConfidence: Option[BigDecimal] = None,
	  /** Face landmarking confidence. Range [0, 1]. */
		landmarkingConfidence: Option[BigDecimal] = None,
	  /** Joy likelihood. */
		joyLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.JoyLikelihoodEnum] = None,
	  /** Sorrow likelihood. */
		sorrowLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.SorrowLikelihoodEnum] = None,
	  /** Anger likelihood. */
		angerLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.AngerLikelihoodEnum] = None,
	  /** Surprise likelihood. */
		surpriseLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.SurpriseLikelihoodEnum] = None,
	  /** Under-exposed likelihood. */
		underExposedLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.UnderExposedLikelihoodEnum] = None,
	  /** Blurred likelihood. */
		blurredLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.BlurredLikelihoodEnum] = None,
	  /** Headwear likelihood. */
		headwearLikelihood: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotation.HeadwearLikelihoodEnum] = None
	)
	
	case class GoogleCloudVisionV1p2beta1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudVisionV1p2beta1Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudVisionV1p2beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p2beta1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p2beta1FaceAnnotationLandmark {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN_LANDMARK, LEFT_EYE, RIGHT_EYE, LEFT_OF_LEFT_EYEBROW, RIGHT_OF_LEFT_EYEBROW, LEFT_OF_RIGHT_EYEBROW, RIGHT_OF_RIGHT_EYEBROW, MIDPOINT_BETWEEN_EYES, NOSE_TIP, UPPER_LIP, LOWER_LIP, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_CENTER, NOSE_BOTTOM_RIGHT, NOSE_BOTTOM_LEFT, NOSE_BOTTOM_CENTER, LEFT_EYE_TOP_BOUNDARY, LEFT_EYE_RIGHT_CORNER, LEFT_EYE_BOTTOM_BOUNDARY, LEFT_EYE_LEFT_CORNER, RIGHT_EYE_TOP_BOUNDARY, RIGHT_EYE_RIGHT_CORNER, RIGHT_EYE_BOTTOM_BOUNDARY, RIGHT_EYE_LEFT_CORNER, LEFT_EYEBROW_UPPER_MIDPOINT, RIGHT_EYEBROW_UPPER_MIDPOINT, LEFT_EAR_TRAGION, RIGHT_EAR_TRAGION, LEFT_EYE_PUPIL, RIGHT_EYE_PUPIL, FOREHEAD_GLABELLA, CHIN_GNATHION, CHIN_LEFT_GONION, CHIN_RIGHT_GONION, LEFT_CHEEK_CENTER, RIGHT_CHEEK_CENTER }
	}
	case class GoogleCloudVisionV1p2beta1FaceAnnotationLandmark(
	  /** Face landmark type. */
		`type`: Option[Schema.GoogleCloudVisionV1p2beta1FaceAnnotationLandmark.TypeEnum] = None,
	  /** Face landmark position. */
		position: Option[Schema.GoogleCloudVisionV1p2beta1Position] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Position(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None,
	  /** Z coordinate (or depth). */
		z: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1EntityAnnotation(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		mid: Option[String] = None,
	  /** The language code for the locale in which the entity textual `description` is expressed. */
		locale: Option[String] = None,
	  /** Entity textual description, expressed in its `locale` language. */
		description: Option[String] = None,
	  /** Overall score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** &#42;&#42;Deprecated. Use `score` instead.&#42;&#42; The accuracy of the entity detection in an image. For example, for an image in which the "Eiffel Tower" entity is detected, this field represents the confidence that there is a tower in the query image. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** The relevancy of the ICA (Image Content Annotation) label to the image. For example, the relevancy of "tower" is likely higher to an image containing the detected "Eiffel Tower" than to an image containing a detected distant towering building, even though the confidence that there is a tower in each image may be the same. Range [0, 1]. */
		topicality: Option[BigDecimal] = None,
	  /** Image region to which this entity belongs. Not produced for `LABEL_DETECTION` features. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** The location information for the detected entity. Multiple `LocationInfo` elements can be present because one location may indicate the location of the scene in the image, and another location may indicate the location of the place where the image was taken. Location information is usually present for landmarks. */
		locations: Option[List[Schema.GoogleCloudVisionV1p2beta1LocationInfo]] = None,
	  /** Some entities may have optional user-supplied `Property` (name/value) fields, such a score or string that qualifies the entity. */
		properties: Option[List[Schema.GoogleCloudVisionV1p2beta1Property]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1LocationInfo(
	  /** lat/long location coordinates. */
		latLng: Option[Schema.LatLng] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Property(
	  /** Name of the property. */
		name: Option[String] = None,
	  /** Value of the property. */
		value: Option[String] = None,
	  /** Value of numeric properties. */
		uint64Value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1LocalizedObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** Image region to which this object belongs. This must be populated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None
	)
	
	case class GoogleCloudVisionV1p2beta1TextAnnotation(
	  /** List of pages detected by OCR. */
		pages: Option[List[Schema.GoogleCloudVisionV1p2beta1Page]] = None,
	  /** UTF-8 text detected on the pages. */
		text: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Page(
	  /** Additional information detected on the page. */
		property: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationTextProperty] = None,
	  /** Page width. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		width: Option[Int] = None,
	  /** Page height. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		height: Option[Int] = None,
	  /** List of blocks of text, images etc on this page. */
		blocks: Option[List[Schema.GoogleCloudVisionV1p2beta1Block]] = None,
	  /** Confidence of the OCR results on the page. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1TextAnnotationTextProperty(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudVisionV1p2beta1TextAnnotationDetectedLanguage]] = None,
	  /** Detected start or end of a text segment. */
		detectedBreak: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationDetectedBreak] = None
	)
	
	case class GoogleCloudVisionV1p2beta1TextAnnotationDetectedLanguage(
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Confidence of detected language. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p2beta1TextAnnotationDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, SPACE, SURE_SPACE, EOL_SURE_SPACE, HYPHEN, LINE_BREAK }
	}
	case class GoogleCloudVisionV1p2beta1TextAnnotationDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationDetectedBreak.TypeEnum] = None,
	  /** True if break prepends the element. */
		isPrefix: Option[Boolean] = None
	)
	
	object GoogleCloudVisionV1p2beta1Block {
		enum BlockTypeEnum extends Enum[BlockTypeEnum] { case UNKNOWN, TEXT, TABLE, PICTURE, RULER, BARCODE }
	}
	case class GoogleCloudVisionV1p2beta1Block(
	  /** Additional information detected for the block. */
		property: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the block. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** List of paragraphs in this block (if this blocks is of type text). */
		paragraphs: Option[List[Schema.GoogleCloudVisionV1p2beta1Paragraph]] = None,
	  /** Detected block type (text, image etc) for this block. */
		blockType: Option[Schema.GoogleCloudVisionV1p2beta1Block.BlockTypeEnum] = None,
	  /** Confidence of the OCR results on the block. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Paragraph(
	  /** Additional information detected for the paragraph. */
		property: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the paragraph. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** List of all words in this paragraph. */
		words: Option[List[Schema.GoogleCloudVisionV1p2beta1Word]] = None,
	  /** Confidence of the OCR results for the paragraph. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Word(
	  /** Additional information detected for the word. */
		property: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the word. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** List of symbols in the word. The order of the symbols follows the natural reading order. */
		symbols: Option[List[Schema.GoogleCloudVisionV1p2beta1Symbol]] = None,
	  /** Confidence of the OCR results for the word. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Symbol(
	  /** Additional information detected for the symbol. */
		property: Option[Schema.GoogleCloudVisionV1p2beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the symbol. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** The actual UTF-8 representation of the symbol. */
		text: Option[String] = None,
	  /** Confidence of the OCR results for the symbol. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p2beta1SafeSearchAnnotation {
		enum AdultEnum extends Enum[AdultEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SpoofEnum extends Enum[SpoofEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum MedicalEnum extends Enum[MedicalEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ViolenceEnum extends Enum[ViolenceEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum RacyEnum extends Enum[RacyEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p2beta1SafeSearchAnnotation(
	  /** Represents the adult content likelihood for the image. Adult content may contain elements such as nudity, pornographic images or cartoons, or sexual activities. */
		adult: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation.AdultEnum] = None,
	  /** Spoof likelihood. The likelihood that an modification was made to the image's canonical version to make it appear funny or offensive. */
		spoof: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation.SpoofEnum] = None,
	  /** Likelihood that this is a medical image. */
		medical: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation.MedicalEnum] = None,
	  /** Likelihood that this image contains violent content. Violent content may include death, serious harm, or injury to individuals or groups of individuals. */
		violence: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation.ViolenceEnum] = None,
	  /** Likelihood that the request image contains racy content. Racy content may include (but is not limited to) skimpy or sheer clothing, strategically covered nudity, lewd or provocative poses, or close-ups of sensitive body areas. */
		racy: Option[Schema.GoogleCloudVisionV1p2beta1SafeSearchAnnotation.RacyEnum] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ImageProperties(
	  /** If present, dominant colors completed successfully. */
		dominantColors: Option[Schema.GoogleCloudVisionV1p2beta1DominantColorsAnnotation] = None
	)
	
	case class GoogleCloudVisionV1p2beta1DominantColorsAnnotation(
	  /** RGB color values with their score and pixel fraction. */
		colors: Option[List[Schema.GoogleCloudVisionV1p2beta1ColorInfo]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ColorInfo(
	  /** RGB components of the color. */
		color: Option[Schema.Color] = None,
	  /** Image-specific score for this color. Value in range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** The fraction of pixels the color occupies in the image. Value in range [0, 1]. */
		pixelFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1CropHintsAnnotation(
	  /** Crop hint results. */
		cropHints: Option[List[Schema.GoogleCloudVisionV1p2beta1CropHint]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1CropHint(
	  /** The bounding polygon for the crop region. The coordinates of the bounding box are in the original image's scale. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** Confidence of this being a salient region. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Fraction of importance of this salient region with respect to the original image. */
		importanceFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1WebDetection(
	  /** Deduced entities from similar images on the Internet. */
		webEntities: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebEntity]] = None,
	  /** Fully matching images from the Internet. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebImage]] = None,
	  /** Partial matching images from the Internet. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebImage]] = None,
	  /** Web pages containing the matching images from the Internet. */
		pagesWithMatchingImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebPage]] = None,
	  /** The visually similar image results. */
		visuallySimilarImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebImage]] = None,
	  /** The service's best guess as to the topic of the request image. Inferred from similar images on the open web. */
		bestGuessLabels: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebLabel]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1WebDetectionWebEntity(
	  /** Opaque entity ID. */
		entityId: Option[String] = None,
	  /** Overall relevancy score for the entity. Not normalized and not comparable across different image queries. */
		score: Option[BigDecimal] = None,
	  /** Canonical description of the entity, in English. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1WebDetectionWebImage(
	  /** The result image URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the image. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1WebDetectionWebPage(
	  /** The result web page URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the web page. */
		score: Option[BigDecimal] = None,
	  /** Title for the web page, may contain HTML markups. */
		pageTitle: Option[String] = None,
	  /** Fully matching images on the page. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebImage]] = None,
	  /** Partial matching images on the page. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p2beta1WebDetectionWebImage]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1WebDetectionWebLabel(
	  /** Label for extra metadata. */
		label: Option[String] = None,
	  /** The BCP-47 language code for `label`, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ProductSearchResults(
	  /** Timestamp of the index which provided these results. Products added to the product set and products removed from the product set after this time are not reflected in the current results. */
		indexTime: Option[String] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p2beta1ProductSearchResultsResult]] = None,
	  /** List of results grouped by products detected in the query image. Each entry corresponds to one bounding polygon in the query image, and contains the matching products specific to that region. There may be duplicate product matches in the union of all the per-product results. */
		productGroupedResults: Option[List[Schema.GoogleCloudVisionV1p2beta1ProductSearchResultsGroupedResult]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ProductSearchResultsResult(
	  /** The Product. */
		product: Option[Schema.GoogleCloudVisionV1p2beta1Product] = None,
	  /** A confidence level on the match, ranging from 0 (no confidence) to 1 (full confidence). */
		score: Option[BigDecimal] = None,
	  /** The resource name of the image from the product that is the closest match to the query. */
		image: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1Product(
	  /** The resource name of the product. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID`. This field is ignored when creating a product. */
		name: Option[String] = None,
	  /** The user-provided name for this Product. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** User-provided metadata to be stored with this product. Must be at most 4096 characters long. */
		description: Option[String] = None,
	  /** Immutable. The category for the product identified by the reference image. This should be one of "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1" or "general-v1". The legacy categories "homegoods", "apparel", and "toys" are still supported, but these should not be used for new products. */
		productCategory: Option[String] = None,
	  /** Key-value pairs that can be attached to a product. At query time, constraints can be specified based on the product_labels. Note that integer values can be provided as strings, e.g. "1199". Only strings with integer values can match a range-based restriction which is to be supported soon. Multiple values can be assigned to the same key. One product may have up to 500 product_labels. Notice that the total number of distinct product_labels over all products in one ProductSet cannot exceed 1M, otherwise the product search pipeline will refuse to work for that ProductSet. */
		productLabels: Option[List[Schema.GoogleCloudVisionV1p2beta1ProductKeyValue]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ProductKeyValue(
	  /** The key of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		key: Option[String] = None,
	  /** The value of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ProductSearchResultsGroupedResult(
	  /** The bounding polygon around the product detected in the query image. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p2beta1BoundingPoly] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p2beta1ProductSearchResultsResult]] = None,
	  /** List of generic predictions for the object in the bounding box. */
		objectAnnotations: Option[List[Schema.GoogleCloudVisionV1p2beta1ProductSearchResultsObjectAnnotation]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ProductSearchResultsObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p2beta1ImageAnnotationContext(
	  /** The URI of the file used to produce the image. */
		uri: Option[String] = None,
	  /** If the file was a PDF or TIFF, this field gives the page number within the file used to produce the image. */
		pageNumber: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p2beta1AsyncBatchAnnotateFilesResponse(
	  /** The list of file annotation responses, one for each request in AsyncBatchAnnotateFilesRequest. */
		responses: Option[List[Schema.GoogleCloudVisionV1p2beta1AsyncAnnotateFileResponse]] = None
	)
	
	case class GoogleCloudVisionV1p2beta1AsyncAnnotateFileResponse(
	  /** The output location and metadata from AsyncAnnotateFileRequest. */
		outputConfig: Option[Schema.GoogleCloudVisionV1p2beta1OutputConfig] = None
	)
	
	case class GoogleCloudVisionV1p2beta1OutputConfig(
	  /** The Google Cloud Storage location to write the output(s) to. */
		gcsDestination: Option[Schema.GoogleCloudVisionV1p2beta1GcsDestination] = None,
	  /** The max number of response protos to put into each output JSON file on Google Cloud Storage. The valid range is [1, 100]. If not specified, the default value is 20. For example, for one pdf file with 100 pages, 100 response protos will be generated. If `batch_size` = 20, then 5 json files each containing 20 response protos will be written under the prefix `gcs_destination`.`uri`. Currently, batch_size only applies to GcsDestination, with potential future support for other output configurations. */
		batchSize: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p2beta1GcsDestination(
	  /** Google Cloud Storage URI prefix where the results will be stored. Results will be in JSON format and preceded by its corresponding input URI prefix. This field can either represent a gcs file prefix or gcs directory. In either case, the uri should be unique because in order to get all of the output files, you will need to do a wildcard gcs search on the uri prefix you provide. Examples: &#42; File Prefix: gs://bucket-name/here/filenameprefix The output files will be created in gs://bucket-name/here/ and the names of the output files will begin with "filenameprefix". &#42; Directory Prefix: gs://bucket-name/some/location/ The output files will be created in gs://bucket-name/some/location/ and the names of the output files could be anything because there was no filename prefix specified. If multiple outputs, each response is still AnnotateFileResponse, each of which contains some subset of the full list of AnnotateImageResponse. Multiple outputs can happen if, for example, the output JSON is too large and overflows into multiple sharded files. */
		uri: Option[String] = None
	)
	
	object GoogleCloudVisionV1p2beta1OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, RUNNING, DONE, CANCELLED }
	}
	case class GoogleCloudVisionV1p2beta1OperationMetadata(
	  /** Current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p2beta1OperationMetadata.StateEnum] = None,
	  /** The time when the batch request was received. */
		createTime: Option[String] = None,
	  /** The time when the operation result was last updated. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudVisionV1p3beta1BatchOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROCESSING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleCloudVisionV1p3beta1BatchOperationMetadata(
	  /** The current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p3beta1BatchOperationMetadata.StateEnum] = None,
	  /** The time when the batch request was submitted to the server. */
		submitTime: Option[String] = None,
	  /** The time when the batch request is finished and google.longrunning.Operation.done is set to true. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ImportProductSetsResponse(
	  /** The list of reference_images that are imported successfully. */
		referenceImages: Option[List[Schema.GoogleCloudVisionV1p3beta1ReferenceImage]] = None,
	  /** The rpc status for each ImportProductSet request, including both successes and errors. The number of statuses here matches the number of lines in the csv file, and statuses[i] stores the success or failure status of processing the i-th line of the csv, starting from line 0. */
		statuses: Option[List[Schema.Status]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ReferenceImage(
	  /** The resource name of the reference image. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID/referenceImages/IMAGE_ID`. This field is ignored when creating a reference image. */
		name: Option[String] = None,
	  /** Required. The Google Cloud Storage URI of the reference image. The URI must start with `gs://`. */
		uri: Option[String] = None,
	  /** Optional. Bounding polygons around the areas of interest in the reference image. If this field is empty, the system will try to detect regions of interest. At most 10 bounding polygons will be used. The provided shape is converted into a non-rotated rectangle. Once converted, the small edge of the rectangle must be greater than or equal to 300 pixels. The aspect ratio must be 1:4 or less (i.e. 1:3 is ok; 1:5 is not). */
		boundingPolys: Option[List[Schema.GoogleCloudVisionV1p3beta1BoundingPoly]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudVisionV1p3beta1Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudVisionV1p3beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p3beta1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1AnnotateFileResponse(
	  /** Information about the file for which this response is generated. */
		inputConfig: Option[Schema.GoogleCloudVisionV1p3beta1InputConfig] = None,
	  /** Individual responses to images found within the file. This field will be empty if the `error` field is set. */
		responses: Option[List[Schema.GoogleCloudVisionV1p3beta1AnnotateImageResponse]] = None,
	  /** This field gives the total number of pages in the file. */
		totalPages: Option[Int] = None,
	  /** If set, represents the error message for the failed request. The `responses` field will not be set in this case. */
		error: Option[Schema.Status] = None
	)
	
	case class GoogleCloudVisionV1p3beta1InputConfig(
	  /** The Google Cloud Storage location to read the input from. */
		gcsSource: Option[Schema.GoogleCloudVisionV1p3beta1GcsSource] = None,
	  /** File content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateFiles requests. It does not work for AsyncBatchAnnotateFiles requests. */
		content: Option[String] = None,
	  /** The type of the file. Currently only "application/pdf", "image/tiff" and "image/gif" are supported. Wildcards are not supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1GcsSource(
	  /** Google Cloud Storage URI for the input file. This must only be a Google Cloud Storage object. Wildcards are not currently supported. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1AnnotateImageResponse(
	  /** If present, face detection has completed successfully. */
		faceAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation]] = None,
	  /** If present, landmark detection has completed successfully. */
		landmarkAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1EntityAnnotation]] = None,
	  /** If present, logo detection has completed successfully. */
		logoAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1EntityAnnotation]] = None,
	  /** If present, label detection has completed successfully. */
		labelAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1EntityAnnotation]] = None,
	  /** If present, localized object detection has completed successfully. This will be sorted descending by confidence score. */
		localizedObjectAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1LocalizedObjectAnnotation]] = None,
	  /** If present, text (OCR) detection has completed successfully. */
		textAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1EntityAnnotation]] = None,
	  /** If present, text (OCR) detection or document (OCR) text detection has completed successfully. This annotation provides the structural hierarchy for the OCR detected text. */
		fullTextAnnotation: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotation] = None,
	  /** If present, safe-search annotation has completed successfully. */
		safeSearchAnnotation: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation] = None,
	  /** If present, image properties were extracted successfully. */
		imagePropertiesAnnotation: Option[Schema.GoogleCloudVisionV1p3beta1ImageProperties] = None,
	  /** If present, crop hints have completed successfully. */
		cropHintsAnnotation: Option[Schema.GoogleCloudVisionV1p3beta1CropHintsAnnotation] = None,
	  /** If present, web detection has completed successfully. */
		webDetection: Option[Schema.GoogleCloudVisionV1p3beta1WebDetection] = None,
	  /** If present, product search has completed successfully. */
		productSearchResults: Option[Schema.GoogleCloudVisionV1p3beta1ProductSearchResults] = None,
	  /** If set, represents the error message for the operation. Note that filled-in image annotations are guaranteed to be correct, even when `error` is set. */
		error: Option[Schema.Status] = None,
	  /** If present, contextual information is needed to understand where this image comes from. */
		context: Option[Schema.GoogleCloudVisionV1p3beta1ImageAnnotationContext] = None
	)
	
	object GoogleCloudVisionV1p3beta1FaceAnnotation {
		enum JoyLikelihoodEnum extends Enum[JoyLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SorrowLikelihoodEnum extends Enum[SorrowLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum AngerLikelihoodEnum extends Enum[AngerLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SurpriseLikelihoodEnum extends Enum[SurpriseLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum UnderExposedLikelihoodEnum extends Enum[UnderExposedLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum BlurredLikelihoodEnum extends Enum[BlurredLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum HeadwearLikelihoodEnum extends Enum[HeadwearLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p3beta1FaceAnnotation(
	  /** The bounding polygon around the face. The coordinates of the bounding box are in the original image's scale. The bounding box is computed to "frame" the face in accordance with human expectations. It is based on the landmarker results. Note that one or more x and/or y coordinates may not be generated in the `BoundingPoly` (the polygon will be unbounded) if only a partial face appears in the image to be annotated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** The `fd_bounding_poly` bounding polygon is tighter than the `boundingPoly`, and encloses only the skin part of the face. Typically, it is used to eliminate the face from any image analysis that detects the "amount of skin" visible in an image. It is not based on the landmarker results, only on the initial face detection, hence the fd (face detection) prefix. */
		fdBoundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** Detected face landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVisionV1p3beta1FaceAnnotationLandmark]] = None,
	  /** Roll angle, which indicates the amount of clockwise/anti-clockwise rotation of the face relative to the image vertical about the axis perpendicular to the face. Range [-180,180]. */
		rollAngle: Option[BigDecimal] = None,
	  /** Yaw angle, which indicates the leftward/rightward angle that the face is pointing relative to the vertical plane perpendicular to the image. Range [-180,180]. */
		panAngle: Option[BigDecimal] = None,
	  /** Pitch angle, which indicates the upwards/downwards angle that the face is pointing relative to the image's horizontal plane. Range [-180,180]. */
		tiltAngle: Option[BigDecimal] = None,
	  /** Detection confidence. Range [0, 1]. */
		detectionConfidence: Option[BigDecimal] = None,
	  /** Face landmarking confidence. Range [0, 1]. */
		landmarkingConfidence: Option[BigDecimal] = None,
	  /** Joy likelihood. */
		joyLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.JoyLikelihoodEnum] = None,
	  /** Sorrow likelihood. */
		sorrowLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.SorrowLikelihoodEnum] = None,
	  /** Anger likelihood. */
		angerLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.AngerLikelihoodEnum] = None,
	  /** Surprise likelihood. */
		surpriseLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.SurpriseLikelihoodEnum] = None,
	  /** Under-exposed likelihood. */
		underExposedLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.UnderExposedLikelihoodEnum] = None,
	  /** Blurred likelihood. */
		blurredLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.BlurredLikelihoodEnum] = None,
	  /** Headwear likelihood. */
		headwearLikelihood: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotation.HeadwearLikelihoodEnum] = None
	)
	
	object GoogleCloudVisionV1p3beta1FaceAnnotationLandmark {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN_LANDMARK, LEFT_EYE, RIGHT_EYE, LEFT_OF_LEFT_EYEBROW, RIGHT_OF_LEFT_EYEBROW, LEFT_OF_RIGHT_EYEBROW, RIGHT_OF_RIGHT_EYEBROW, MIDPOINT_BETWEEN_EYES, NOSE_TIP, UPPER_LIP, LOWER_LIP, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_CENTER, NOSE_BOTTOM_RIGHT, NOSE_BOTTOM_LEFT, NOSE_BOTTOM_CENTER, LEFT_EYE_TOP_BOUNDARY, LEFT_EYE_RIGHT_CORNER, LEFT_EYE_BOTTOM_BOUNDARY, LEFT_EYE_LEFT_CORNER, RIGHT_EYE_TOP_BOUNDARY, RIGHT_EYE_RIGHT_CORNER, RIGHT_EYE_BOTTOM_BOUNDARY, RIGHT_EYE_LEFT_CORNER, LEFT_EYEBROW_UPPER_MIDPOINT, RIGHT_EYEBROW_UPPER_MIDPOINT, LEFT_EAR_TRAGION, RIGHT_EAR_TRAGION, LEFT_EYE_PUPIL, RIGHT_EYE_PUPIL, FOREHEAD_GLABELLA, CHIN_GNATHION, CHIN_LEFT_GONION, CHIN_RIGHT_GONION, LEFT_CHEEK_CENTER, RIGHT_CHEEK_CENTER }
	}
	case class GoogleCloudVisionV1p3beta1FaceAnnotationLandmark(
	  /** Face landmark type. */
		`type`: Option[Schema.GoogleCloudVisionV1p3beta1FaceAnnotationLandmark.TypeEnum] = None,
	  /** Face landmark position. */
		position: Option[Schema.GoogleCloudVisionV1p3beta1Position] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Position(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None,
	  /** Z coordinate (or depth). */
		z: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1EntityAnnotation(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		mid: Option[String] = None,
	  /** The language code for the locale in which the entity textual `description` is expressed. */
		locale: Option[String] = None,
	  /** Entity textual description, expressed in its `locale` language. */
		description: Option[String] = None,
	  /** Overall score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** &#42;&#42;Deprecated. Use `score` instead.&#42;&#42; The accuracy of the entity detection in an image. For example, for an image in which the "Eiffel Tower" entity is detected, this field represents the confidence that there is a tower in the query image. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** The relevancy of the ICA (Image Content Annotation) label to the image. For example, the relevancy of "tower" is likely higher to an image containing the detected "Eiffel Tower" than to an image containing a detected distant towering building, even though the confidence that there is a tower in each image may be the same. Range [0, 1]. */
		topicality: Option[BigDecimal] = None,
	  /** Image region to which this entity belongs. Not produced for `LABEL_DETECTION` features. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** The location information for the detected entity. Multiple `LocationInfo` elements can be present because one location may indicate the location of the scene in the image, and another location may indicate the location of the place where the image was taken. Location information is usually present for landmarks. */
		locations: Option[List[Schema.GoogleCloudVisionV1p3beta1LocationInfo]] = None,
	  /** Some entities may have optional user-supplied `Property` (name/value) fields, such a score or string that qualifies the entity. */
		properties: Option[List[Schema.GoogleCloudVisionV1p3beta1Property]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1LocationInfo(
	  /** lat/long location coordinates. */
		latLng: Option[Schema.LatLng] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Property(
	  /** Name of the property. */
		name: Option[String] = None,
	  /** Value of the property. */
		value: Option[String] = None,
	  /** Value of numeric properties. */
		uint64Value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1LocalizedObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** Image region to which this object belongs. This must be populated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None
	)
	
	case class GoogleCloudVisionV1p3beta1TextAnnotation(
	  /** List of pages detected by OCR. */
		pages: Option[List[Schema.GoogleCloudVisionV1p3beta1Page]] = None,
	  /** UTF-8 text detected on the pages. */
		text: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Page(
	  /** Additional information detected on the page. */
		property: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationTextProperty] = None,
	  /** Page width. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		width: Option[Int] = None,
	  /** Page height. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		height: Option[Int] = None,
	  /** List of blocks of text, images etc on this page. */
		blocks: Option[List[Schema.GoogleCloudVisionV1p3beta1Block]] = None,
	  /** Confidence of the OCR results on the page. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1TextAnnotationTextProperty(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudVisionV1p3beta1TextAnnotationDetectedLanguage]] = None,
	  /** Detected start or end of a text segment. */
		detectedBreak: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationDetectedBreak] = None
	)
	
	case class GoogleCloudVisionV1p3beta1TextAnnotationDetectedLanguage(
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Confidence of detected language. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p3beta1TextAnnotationDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, SPACE, SURE_SPACE, EOL_SURE_SPACE, HYPHEN, LINE_BREAK }
	}
	case class GoogleCloudVisionV1p3beta1TextAnnotationDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationDetectedBreak.TypeEnum] = None,
	  /** True if break prepends the element. */
		isPrefix: Option[Boolean] = None
	)
	
	object GoogleCloudVisionV1p3beta1Block {
		enum BlockTypeEnum extends Enum[BlockTypeEnum] { case UNKNOWN, TEXT, TABLE, PICTURE, RULER, BARCODE }
	}
	case class GoogleCloudVisionV1p3beta1Block(
	  /** Additional information detected for the block. */
		property: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the block. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** List of paragraphs in this block (if this blocks is of type text). */
		paragraphs: Option[List[Schema.GoogleCloudVisionV1p3beta1Paragraph]] = None,
	  /** Detected block type (text, image etc) for this block. */
		blockType: Option[Schema.GoogleCloudVisionV1p3beta1Block.BlockTypeEnum] = None,
	  /** Confidence of the OCR results on the block. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Paragraph(
	  /** Additional information detected for the paragraph. */
		property: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the paragraph. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** List of all words in this paragraph. */
		words: Option[List[Schema.GoogleCloudVisionV1p3beta1Word]] = None,
	  /** Confidence of the OCR results for the paragraph. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Word(
	  /** Additional information detected for the word. */
		property: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the word. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** List of symbols in the word. The order of the symbols follows the natural reading order. */
		symbols: Option[List[Schema.GoogleCloudVisionV1p3beta1Symbol]] = None,
	  /** Confidence of the OCR results for the word. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Symbol(
	  /** Additional information detected for the symbol. */
		property: Option[Schema.GoogleCloudVisionV1p3beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the symbol. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** The actual UTF-8 representation of the symbol. */
		text: Option[String] = None,
	  /** Confidence of the OCR results for the symbol. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p3beta1SafeSearchAnnotation {
		enum AdultEnum extends Enum[AdultEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SpoofEnum extends Enum[SpoofEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum MedicalEnum extends Enum[MedicalEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ViolenceEnum extends Enum[ViolenceEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum RacyEnum extends Enum[RacyEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p3beta1SafeSearchAnnotation(
	  /** Represents the adult content likelihood for the image. Adult content may contain elements such as nudity, pornographic images or cartoons, or sexual activities. */
		adult: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation.AdultEnum] = None,
	  /** Spoof likelihood. The likelihood that an modification was made to the image's canonical version to make it appear funny or offensive. */
		spoof: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation.SpoofEnum] = None,
	  /** Likelihood that this is a medical image. */
		medical: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation.MedicalEnum] = None,
	  /** Likelihood that this image contains violent content. Violent content may include death, serious harm, or injury to individuals or groups of individuals. */
		violence: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation.ViolenceEnum] = None,
	  /** Likelihood that the request image contains racy content. Racy content may include (but is not limited to) skimpy or sheer clothing, strategically covered nudity, lewd or provocative poses, or close-ups of sensitive body areas. */
		racy: Option[Schema.GoogleCloudVisionV1p3beta1SafeSearchAnnotation.RacyEnum] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ImageProperties(
	  /** If present, dominant colors completed successfully. */
		dominantColors: Option[Schema.GoogleCloudVisionV1p3beta1DominantColorsAnnotation] = None
	)
	
	case class GoogleCloudVisionV1p3beta1DominantColorsAnnotation(
	  /** RGB color values with their score and pixel fraction. */
		colors: Option[List[Schema.GoogleCloudVisionV1p3beta1ColorInfo]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ColorInfo(
	  /** RGB components of the color. */
		color: Option[Schema.Color] = None,
	  /** Image-specific score for this color. Value in range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** The fraction of pixels the color occupies in the image. Value in range [0, 1]. */
		pixelFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1CropHintsAnnotation(
	  /** Crop hint results. */
		cropHints: Option[List[Schema.GoogleCloudVisionV1p3beta1CropHint]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1CropHint(
	  /** The bounding polygon for the crop region. The coordinates of the bounding box are in the original image's scale. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** Confidence of this being a salient region. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Fraction of importance of this salient region with respect to the original image. */
		importanceFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1WebDetection(
	  /** Deduced entities from similar images on the Internet. */
		webEntities: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebEntity]] = None,
	  /** Fully matching images from the Internet. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebImage]] = None,
	  /** Partial matching images from the Internet. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebImage]] = None,
	  /** Web pages containing the matching images from the Internet. */
		pagesWithMatchingImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebPage]] = None,
	  /** The visually similar image results. */
		visuallySimilarImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebImage]] = None,
	  /** The service's best guess as to the topic of the request image. Inferred from similar images on the open web. */
		bestGuessLabels: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebLabel]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1WebDetectionWebEntity(
	  /** Opaque entity ID. */
		entityId: Option[String] = None,
	  /** Overall relevancy score for the entity. Not normalized and not comparable across different image queries. */
		score: Option[BigDecimal] = None,
	  /** Canonical description of the entity, in English. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1WebDetectionWebImage(
	  /** The result image URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the image. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1WebDetectionWebPage(
	  /** The result web page URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the web page. */
		score: Option[BigDecimal] = None,
	  /** Title for the web page, may contain HTML markups. */
		pageTitle: Option[String] = None,
	  /** Fully matching images on the page. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebImage]] = None,
	  /** Partial matching images on the page. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p3beta1WebDetectionWebImage]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1WebDetectionWebLabel(
	  /** Label for extra metadata. */
		label: Option[String] = None,
	  /** The BCP-47 language code for `label`, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ProductSearchResults(
	  /** Timestamp of the index which provided these results. Products added to the product set and products removed from the product set after this time are not reflected in the current results. */
		indexTime: Option[String] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p3beta1ProductSearchResultsResult]] = None,
	  /** List of results grouped by products detected in the query image. Each entry corresponds to one bounding polygon in the query image, and contains the matching products specific to that region. There may be duplicate product matches in the union of all the per-product results. */
		productGroupedResults: Option[List[Schema.GoogleCloudVisionV1p3beta1ProductSearchResultsGroupedResult]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ProductSearchResultsResult(
	  /** The Product. */
		product: Option[Schema.GoogleCloudVisionV1p3beta1Product] = None,
	  /** A confidence level on the match, ranging from 0 (no confidence) to 1 (full confidence). */
		score: Option[BigDecimal] = None,
	  /** The resource name of the image from the product that is the closest match to the query. */
		image: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1Product(
	  /** The resource name of the product. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID`. This field is ignored when creating a product. */
		name: Option[String] = None,
	  /** The user-provided name for this Product. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** User-provided metadata to be stored with this product. Must be at most 4096 characters long. */
		description: Option[String] = None,
	  /** Immutable. The category for the product identified by the reference image. This should be one of "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1" or "general-v1". The legacy categories "homegoods", "apparel", and "toys" are still supported, but these should not be used for new products. */
		productCategory: Option[String] = None,
	  /** Key-value pairs that can be attached to a product. At query time, constraints can be specified based on the product_labels. Note that integer values can be provided as strings, e.g. "1199". Only strings with integer values can match a range-based restriction which is to be supported soon. Multiple values can be assigned to the same key. One product may have up to 500 product_labels. Notice that the total number of distinct product_labels over all products in one ProductSet cannot exceed 1M, otherwise the product search pipeline will refuse to work for that ProductSet. */
		productLabels: Option[List[Schema.GoogleCloudVisionV1p3beta1ProductKeyValue]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ProductKeyValue(
	  /** The key of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		key: Option[String] = None,
	  /** The value of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ProductSearchResultsGroupedResult(
	  /** The bounding polygon around the product detected in the query image. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p3beta1BoundingPoly] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p3beta1ProductSearchResultsResult]] = None,
	  /** List of generic predictions for the object in the bounding box. */
		objectAnnotations: Option[List[Schema.GoogleCloudVisionV1p3beta1ProductSearchResultsObjectAnnotation]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ProductSearchResultsObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p3beta1ImageAnnotationContext(
	  /** The URI of the file used to produce the image. */
		uri: Option[String] = None,
	  /** If the file was a PDF or TIFF, this field gives the page number within the file used to produce the image. */
		pageNumber: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p3beta1AsyncBatchAnnotateFilesResponse(
	  /** The list of file annotation responses, one for each request in AsyncBatchAnnotateFilesRequest. */
		responses: Option[List[Schema.GoogleCloudVisionV1p3beta1AsyncAnnotateFileResponse]] = None
	)
	
	case class GoogleCloudVisionV1p3beta1AsyncAnnotateFileResponse(
	  /** The output location and metadata from AsyncAnnotateFileRequest. */
		outputConfig: Option[Schema.GoogleCloudVisionV1p3beta1OutputConfig] = None
	)
	
	case class GoogleCloudVisionV1p3beta1OutputConfig(
	  /** The Google Cloud Storage location to write the output(s) to. */
		gcsDestination: Option[Schema.GoogleCloudVisionV1p3beta1GcsDestination] = None,
	  /** The max number of response protos to put into each output JSON file on Google Cloud Storage. The valid range is [1, 100]. If not specified, the default value is 20. For example, for one pdf file with 100 pages, 100 response protos will be generated. If `batch_size` = 20, then 5 json files each containing 20 response protos will be written under the prefix `gcs_destination`.`uri`. Currently, batch_size only applies to GcsDestination, with potential future support for other output configurations. */
		batchSize: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p3beta1GcsDestination(
	  /** Google Cloud Storage URI prefix where the results will be stored. Results will be in JSON format and preceded by its corresponding input URI prefix. This field can either represent a gcs file prefix or gcs directory. In either case, the uri should be unique because in order to get all of the output files, you will need to do a wildcard gcs search on the uri prefix you provide. Examples: &#42; File Prefix: gs://bucket-name/here/filenameprefix The output files will be created in gs://bucket-name/here/ and the names of the output files will begin with "filenameprefix". &#42; Directory Prefix: gs://bucket-name/some/location/ The output files will be created in gs://bucket-name/some/location/ and the names of the output files could be anything because there was no filename prefix specified. If multiple outputs, each response is still AnnotateFileResponse, each of which contains some subset of the full list of AnnotateImageResponse. Multiple outputs can happen if, for example, the output JSON is too large and overflows into multiple sharded files. */
		uri: Option[String] = None
	)
	
	object GoogleCloudVisionV1p3beta1OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, RUNNING, DONE, CANCELLED }
	}
	case class GoogleCloudVisionV1p3beta1OperationMetadata(
	  /** Current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p3beta1OperationMetadata.StateEnum] = None,
	  /** The time when the batch request was received. */
		createTime: Option[String] = None,
	  /** The time when the operation result was last updated. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudVisionV1p4beta1BatchOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROCESSING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleCloudVisionV1p4beta1BatchOperationMetadata(
	  /** The current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p4beta1BatchOperationMetadata.StateEnum] = None,
	  /** The time when the batch request was submitted to the server. */
		submitTime: Option[String] = None,
	  /** The time when the batch request is finished and google.longrunning.Operation.done is set to true. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ImportProductSetsResponse(
	  /** The list of reference_images that are imported successfully. */
		referenceImages: Option[List[Schema.GoogleCloudVisionV1p4beta1ReferenceImage]] = None,
	  /** The rpc status for each ImportProductSet request, including both successes and errors. The number of statuses here matches the number of lines in the csv file, and statuses[i] stores the success or failure status of processing the i-th line of the csv, starting from line 0. */
		statuses: Option[List[Schema.Status]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ReferenceImage(
	  /** The resource name of the reference image. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID/referenceImages/IMAGE_ID`. This field is ignored when creating a reference image. */
		name: Option[String] = None,
	  /** Required. The Google Cloud Storage URI of the reference image. The URI must start with `gs://`. */
		uri: Option[String] = None,
	  /** Optional. Bounding polygons around the areas of interest in the reference image. If this field is empty, the system will try to detect regions of interest. At most 10 bounding polygons will be used. The provided shape is converted into a non-rotated rectangle. Once converted, the small edge of the rectangle must be greater than or equal to 300 pixels. The aspect ratio must be 1:4 or less (i.e. 1:3 is ok; 1:5 is not). */
		boundingPolys: Option[List[Schema.GoogleCloudVisionV1p4beta1BoundingPoly]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudVisionV1p4beta1Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudVisionV1p4beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p4beta1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1AnnotateFileResponse(
	  /** Information about the file for which this response is generated. */
		inputConfig: Option[Schema.GoogleCloudVisionV1p4beta1InputConfig] = None,
	  /** Individual responses to images found within the file. This field will be empty if the `error` field is set. */
		responses: Option[List[Schema.GoogleCloudVisionV1p4beta1AnnotateImageResponse]] = None,
	  /** This field gives the total number of pages in the file. */
		totalPages: Option[Int] = None,
	  /** If set, represents the error message for the failed request. The `responses` field will not be set in this case. */
		error: Option[Schema.Status] = None
	)
	
	case class GoogleCloudVisionV1p4beta1InputConfig(
	  /** The Google Cloud Storage location to read the input from. */
		gcsSource: Option[Schema.GoogleCloudVisionV1p4beta1GcsSource] = None,
	  /** File content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. Currently, this field only works for BatchAnnotateFiles requests. It does not work for AsyncBatchAnnotateFiles requests. */
		content: Option[String] = None,
	  /** The type of the file. Currently only "application/pdf", "image/tiff" and "image/gif" are supported. Wildcards are not supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1GcsSource(
	  /** Google Cloud Storage URI for the input file. This must only be a Google Cloud Storage object. Wildcards are not currently supported. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1AnnotateImageResponse(
	  /** If present, face detection has completed successfully. */
		faceAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation]] = None,
	  /** If present, landmark detection has completed successfully. */
		landmarkAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1EntityAnnotation]] = None,
	  /** If present, logo detection has completed successfully. */
		logoAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1EntityAnnotation]] = None,
	  /** If present, label detection has completed successfully. */
		labelAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1EntityAnnotation]] = None,
	  /** If present, localized object detection has completed successfully. This will be sorted descending by confidence score. */
		localizedObjectAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1LocalizedObjectAnnotation]] = None,
	  /** If present, text (OCR) detection has completed successfully. */
		textAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1EntityAnnotation]] = None,
	  /** If present, text (OCR) detection or document (OCR) text detection has completed successfully. This annotation provides the structural hierarchy for the OCR detected text. */
		fullTextAnnotation: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotation] = None,
	  /** If present, safe-search annotation has completed successfully. */
		safeSearchAnnotation: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation] = None,
	  /** If present, image properties were extracted successfully. */
		imagePropertiesAnnotation: Option[Schema.GoogleCloudVisionV1p4beta1ImageProperties] = None,
	  /** If present, crop hints have completed successfully. */
		cropHintsAnnotation: Option[Schema.GoogleCloudVisionV1p4beta1CropHintsAnnotation] = None,
	  /** If present, web detection has completed successfully. */
		webDetection: Option[Schema.GoogleCloudVisionV1p4beta1WebDetection] = None,
	  /** If present, product search has completed successfully. */
		productSearchResults: Option[Schema.GoogleCloudVisionV1p4beta1ProductSearchResults] = None,
	  /** If set, represents the error message for the operation. Note that filled-in image annotations are guaranteed to be correct, even when `error` is set. */
		error: Option[Schema.Status] = None,
	  /** If present, contextual information is needed to understand where this image comes from. */
		context: Option[Schema.GoogleCloudVisionV1p4beta1ImageAnnotationContext] = None
	)
	
	object GoogleCloudVisionV1p4beta1FaceAnnotation {
		enum JoyLikelihoodEnum extends Enum[JoyLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SorrowLikelihoodEnum extends Enum[SorrowLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum AngerLikelihoodEnum extends Enum[AngerLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SurpriseLikelihoodEnum extends Enum[SurpriseLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum UnderExposedLikelihoodEnum extends Enum[UnderExposedLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum BlurredLikelihoodEnum extends Enum[BlurredLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum HeadwearLikelihoodEnum extends Enum[HeadwearLikelihoodEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p4beta1FaceAnnotation(
	  /** The bounding polygon around the face. The coordinates of the bounding box are in the original image's scale. The bounding box is computed to "frame" the face in accordance with human expectations. It is based on the landmarker results. Note that one or more x and/or y coordinates may not be generated in the `BoundingPoly` (the polygon will be unbounded) if only a partial face appears in the image to be annotated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** The `fd_bounding_poly` bounding polygon is tighter than the `boundingPoly`, and encloses only the skin part of the face. Typically, it is used to eliminate the face from any image analysis that detects the "amount of skin" visible in an image. It is not based on the landmarker results, only on the initial face detection, hence the fd (face detection) prefix. */
		fdBoundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** Detected face landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVisionV1p4beta1FaceAnnotationLandmark]] = None,
	  /** Roll angle, which indicates the amount of clockwise/anti-clockwise rotation of the face relative to the image vertical about the axis perpendicular to the face. Range [-180,180]. */
		rollAngle: Option[BigDecimal] = None,
	  /** Yaw angle, which indicates the leftward/rightward angle that the face is pointing relative to the vertical plane perpendicular to the image. Range [-180,180]. */
		panAngle: Option[BigDecimal] = None,
	  /** Pitch angle, which indicates the upwards/downwards angle that the face is pointing relative to the image's horizontal plane. Range [-180,180]. */
		tiltAngle: Option[BigDecimal] = None,
	  /** Detection confidence. Range [0, 1]. */
		detectionConfidence: Option[BigDecimal] = None,
	  /** Face landmarking confidence. Range [0, 1]. */
		landmarkingConfidence: Option[BigDecimal] = None,
	  /** Joy likelihood. */
		joyLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.JoyLikelihoodEnum] = None,
	  /** Sorrow likelihood. */
		sorrowLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.SorrowLikelihoodEnum] = None,
	  /** Anger likelihood. */
		angerLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.AngerLikelihoodEnum] = None,
	  /** Surprise likelihood. */
		surpriseLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.SurpriseLikelihoodEnum] = None,
	  /** Under-exposed likelihood. */
		underExposedLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.UnderExposedLikelihoodEnum] = None,
	  /** Blurred likelihood. */
		blurredLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.BlurredLikelihoodEnum] = None,
	  /** Headwear likelihood. */
		headwearLikelihood: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotation.HeadwearLikelihoodEnum] = None,
	  /** Additional recognition information. Only computed if image_context.face_recognition_params is provided, &#42;&#42;and&#42;&#42; a match is found to a Celebrity in the input CelebritySet. This field is sorted in order of decreasing confidence values. */
		recognitionResult: Option[List[Schema.GoogleCloudVisionV1p4beta1FaceRecognitionResult]] = None
	)
	
	object GoogleCloudVisionV1p4beta1FaceAnnotationLandmark {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN_LANDMARK, LEFT_EYE, RIGHT_EYE, LEFT_OF_LEFT_EYEBROW, RIGHT_OF_LEFT_EYEBROW, LEFT_OF_RIGHT_EYEBROW, RIGHT_OF_RIGHT_EYEBROW, MIDPOINT_BETWEEN_EYES, NOSE_TIP, UPPER_LIP, LOWER_LIP, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_CENTER, NOSE_BOTTOM_RIGHT, NOSE_BOTTOM_LEFT, NOSE_BOTTOM_CENTER, LEFT_EYE_TOP_BOUNDARY, LEFT_EYE_RIGHT_CORNER, LEFT_EYE_BOTTOM_BOUNDARY, LEFT_EYE_LEFT_CORNER, RIGHT_EYE_TOP_BOUNDARY, RIGHT_EYE_RIGHT_CORNER, RIGHT_EYE_BOTTOM_BOUNDARY, RIGHT_EYE_LEFT_CORNER, LEFT_EYEBROW_UPPER_MIDPOINT, RIGHT_EYEBROW_UPPER_MIDPOINT, LEFT_EAR_TRAGION, RIGHT_EAR_TRAGION, LEFT_EYE_PUPIL, RIGHT_EYE_PUPIL, FOREHEAD_GLABELLA, CHIN_GNATHION, CHIN_LEFT_GONION, CHIN_RIGHT_GONION, LEFT_CHEEK_CENTER, RIGHT_CHEEK_CENTER }
	}
	case class GoogleCloudVisionV1p4beta1FaceAnnotationLandmark(
	  /** Face landmark type. */
		`type`: Option[Schema.GoogleCloudVisionV1p4beta1FaceAnnotationLandmark.TypeEnum] = None,
	  /** Face landmark position. */
		position: Option[Schema.GoogleCloudVisionV1p4beta1Position] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Position(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None,
	  /** Z coordinate (or depth). */
		z: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1FaceRecognitionResult(
	  /** The Celebrity that this face was matched to. */
		celebrity: Option[Schema.GoogleCloudVisionV1p4beta1Celebrity] = None,
	  /** Recognition confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Celebrity(
	  /** The resource name of the preloaded Celebrity. Has the format `builtin/{mid}`. */
		name: Option[String] = None,
	  /** The Celebrity's display name. */
		displayName: Option[String] = None,
	  /** The Celebrity's description. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1EntityAnnotation(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		mid: Option[String] = None,
	  /** The language code for the locale in which the entity textual `description` is expressed. */
		locale: Option[String] = None,
	  /** Entity textual description, expressed in its `locale` language. */
		description: Option[String] = None,
	  /** Overall score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** &#42;&#42;Deprecated. Use `score` instead.&#42;&#42; The accuracy of the entity detection in an image. For example, for an image in which the "Eiffel Tower" entity is detected, this field represents the confidence that there is a tower in the query image. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** The relevancy of the ICA (Image Content Annotation) label to the image. For example, the relevancy of "tower" is likely higher to an image containing the detected "Eiffel Tower" than to an image containing a detected distant towering building, even though the confidence that there is a tower in each image may be the same. Range [0, 1]. */
		topicality: Option[BigDecimal] = None,
	  /** Image region to which this entity belongs. Not produced for `LABEL_DETECTION` features. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** The location information for the detected entity. Multiple `LocationInfo` elements can be present because one location may indicate the location of the scene in the image, and another location may indicate the location of the place where the image was taken. Location information is usually present for landmarks. */
		locations: Option[List[Schema.GoogleCloudVisionV1p4beta1LocationInfo]] = None,
	  /** Some entities may have optional user-supplied `Property` (name/value) fields, such a score or string that qualifies the entity. */
		properties: Option[List[Schema.GoogleCloudVisionV1p4beta1Property]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1LocationInfo(
	  /** lat/long location coordinates. */
		latLng: Option[Schema.LatLng] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Property(
	  /** Name of the property. */
		name: Option[String] = None,
	  /** Value of the property. */
		value: Option[String] = None,
	  /** Value of numeric properties. */
		uint64Value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1LocalizedObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** Image region to which this object belongs. This must be populated. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None
	)
	
	case class GoogleCloudVisionV1p4beta1TextAnnotation(
	  /** List of pages detected by OCR. */
		pages: Option[List[Schema.GoogleCloudVisionV1p4beta1Page]] = None,
	  /** UTF-8 text detected on the pages. */
		text: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Page(
	  /** Additional information detected on the page. */
		property: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationTextProperty] = None,
	  /** Page width. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		width: Option[Int] = None,
	  /** Page height. For PDFs the unit is points. For images (including TIFFs) the unit is pixels. */
		height: Option[Int] = None,
	  /** List of blocks of text, images etc on this page. */
		blocks: Option[List[Schema.GoogleCloudVisionV1p4beta1Block]] = None,
	  /** Confidence of the OCR results on the page. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1TextAnnotationTextProperty(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudVisionV1p4beta1TextAnnotationDetectedLanguage]] = None,
	  /** Detected start or end of a text segment. */
		detectedBreak: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationDetectedBreak] = None
	)
	
	case class GoogleCloudVisionV1p4beta1TextAnnotationDetectedLanguage(
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Confidence of detected language. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p4beta1TextAnnotationDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, SPACE, SURE_SPACE, EOL_SURE_SPACE, HYPHEN, LINE_BREAK }
	}
	case class GoogleCloudVisionV1p4beta1TextAnnotationDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationDetectedBreak.TypeEnum] = None,
	  /** True if break prepends the element. */
		isPrefix: Option[Boolean] = None
	)
	
	object GoogleCloudVisionV1p4beta1Block {
		enum BlockTypeEnum extends Enum[BlockTypeEnum] { case UNKNOWN, TEXT, TABLE, PICTURE, RULER, BARCODE }
	}
	case class GoogleCloudVisionV1p4beta1Block(
	  /** Additional information detected for the block. */
		property: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the block. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** List of paragraphs in this block (if this blocks is of type text). */
		paragraphs: Option[List[Schema.GoogleCloudVisionV1p4beta1Paragraph]] = None,
	  /** Detected block type (text, image etc) for this block. */
		blockType: Option[Schema.GoogleCloudVisionV1p4beta1Block.BlockTypeEnum] = None,
	  /** Confidence of the OCR results on the block. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Paragraph(
	  /** Additional information detected for the paragraph. */
		property: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the paragraph. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** List of all words in this paragraph. */
		words: Option[List[Schema.GoogleCloudVisionV1p4beta1Word]] = None,
	  /** Confidence of the OCR results for the paragraph. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Word(
	  /** Additional information detected for the word. */
		property: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the word. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** List of symbols in the word. The order of the symbols follows the natural reading order. */
		symbols: Option[List[Schema.GoogleCloudVisionV1p4beta1Symbol]] = None,
	  /** Confidence of the OCR results for the word. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Symbol(
	  /** Additional information detected for the symbol. */
		property: Option[Schema.GoogleCloudVisionV1p4beta1TextAnnotationTextProperty] = None,
	  /** The bounding box for the symbol. The vertices are in the order of top-left, top-right, bottom-right, bottom-left. When a rotation of the bounding box is detected the rotation is represented as around the top-left corner as defined when the text is read in the 'natural' orientation. For example: &#42; when the text is horizontal it might look like: 0----1 | | 3----2 &#42; when it's rotated 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the vertex order will still be (0, 1, 2, 3). */
		boundingBox: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** The actual UTF-8 representation of the symbol. */
		text: Option[String] = None,
	  /** Confidence of the OCR results for the symbol. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudVisionV1p4beta1SafeSearchAnnotation {
		enum AdultEnum extends Enum[AdultEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum SpoofEnum extends Enum[SpoofEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum MedicalEnum extends Enum[MedicalEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ViolenceEnum extends Enum[ViolenceEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum RacyEnum extends Enum[RacyEnum] { case UNKNOWN, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVisionV1p4beta1SafeSearchAnnotation(
	  /** Represents the adult content likelihood for the image. Adult content may contain elements such as nudity, pornographic images or cartoons, or sexual activities. */
		adult: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation.AdultEnum] = None,
	  /** Spoof likelihood. The likelihood that an modification was made to the image's canonical version to make it appear funny or offensive. */
		spoof: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation.SpoofEnum] = None,
	  /** Likelihood that this is a medical image. */
		medical: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation.MedicalEnum] = None,
	  /** Likelihood that this image contains violent content. Violent content may include death, serious harm, or injury to individuals or groups of individuals. */
		violence: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation.ViolenceEnum] = None,
	  /** Likelihood that the request image contains racy content. Racy content may include (but is not limited to) skimpy or sheer clothing, strategically covered nudity, lewd or provocative poses, or close-ups of sensitive body areas. */
		racy: Option[Schema.GoogleCloudVisionV1p4beta1SafeSearchAnnotation.RacyEnum] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ImageProperties(
	  /** If present, dominant colors completed successfully. */
		dominantColors: Option[Schema.GoogleCloudVisionV1p4beta1DominantColorsAnnotation] = None
	)
	
	case class GoogleCloudVisionV1p4beta1DominantColorsAnnotation(
	  /** RGB color values with their score and pixel fraction. */
		colors: Option[List[Schema.GoogleCloudVisionV1p4beta1ColorInfo]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ColorInfo(
	  /** RGB components of the color. */
		color: Option[Schema.Color] = None,
	  /** Image-specific score for this color. Value in range [0, 1]. */
		score: Option[BigDecimal] = None,
	  /** The fraction of pixels the color occupies in the image. Value in range [0, 1]. */
		pixelFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1CropHintsAnnotation(
	  /** Crop hint results. */
		cropHints: Option[List[Schema.GoogleCloudVisionV1p4beta1CropHint]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1CropHint(
	  /** The bounding polygon for the crop region. The coordinates of the bounding box are in the original image's scale. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** Confidence of this being a salient region. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Fraction of importance of this salient region with respect to the original image. */
		importanceFraction: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1WebDetection(
	  /** Deduced entities from similar images on the Internet. */
		webEntities: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebEntity]] = None,
	  /** Fully matching images from the Internet. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebImage]] = None,
	  /** Partial matching images from the Internet. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebImage]] = None,
	  /** Web pages containing the matching images from the Internet. */
		pagesWithMatchingImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebPage]] = None,
	  /** The visually similar image results. */
		visuallySimilarImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebImage]] = None,
	  /** The service's best guess as to the topic of the request image. Inferred from similar images on the open web. */
		bestGuessLabels: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebLabel]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1WebDetectionWebEntity(
	  /** Opaque entity ID. */
		entityId: Option[String] = None,
	  /** Overall relevancy score for the entity. Not normalized and not comparable across different image queries. */
		score: Option[BigDecimal] = None,
	  /** Canonical description of the entity, in English. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1WebDetectionWebImage(
	  /** The result image URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the image. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1WebDetectionWebPage(
	  /** The result web page URL. */
		url: Option[String] = None,
	  /** (Deprecated) Overall relevancy score for the web page. */
		score: Option[BigDecimal] = None,
	  /** Title for the web page, may contain HTML markups. */
		pageTitle: Option[String] = None,
	  /** Fully matching images on the page. Can include resized copies of the query image. */
		fullMatchingImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebImage]] = None,
	  /** Partial matching images on the page. Those images are similar enough to share some key-point features. For example an original image will likely have partial matching for its crops. */
		partialMatchingImages: Option[List[Schema.GoogleCloudVisionV1p4beta1WebDetectionWebImage]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1WebDetectionWebLabel(
	  /** Label for extra metadata. */
		label: Option[String] = None,
	  /** The BCP-47 language code for `label`, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ProductSearchResults(
	  /** Timestamp of the index which provided these results. Products added to the product set and products removed from the product set after this time are not reflected in the current results. */
		indexTime: Option[String] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p4beta1ProductSearchResultsResult]] = None,
	  /** List of results grouped by products detected in the query image. Each entry corresponds to one bounding polygon in the query image, and contains the matching products specific to that region. There may be duplicate product matches in the union of all the per-product results. */
		productGroupedResults: Option[List[Schema.GoogleCloudVisionV1p4beta1ProductSearchResultsGroupedResult]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ProductSearchResultsResult(
	  /** The Product. */
		product: Option[Schema.GoogleCloudVisionV1p4beta1Product] = None,
	  /** A confidence level on the match, ranging from 0 (no confidence) to 1 (full confidence). */
		score: Option[BigDecimal] = None,
	  /** The resource name of the image from the product that is the closest match to the query. */
		image: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1Product(
	  /** The resource name of the product. Format is: `projects/PROJECT_ID/locations/LOC_ID/products/PRODUCT_ID`. This field is ignored when creating a product. */
		name: Option[String] = None,
	  /** The user-provided name for this Product. Must not be empty. Must be at most 4096 characters long. */
		displayName: Option[String] = None,
	  /** User-provided metadata to be stored with this product. Must be at most 4096 characters long. */
		description: Option[String] = None,
	  /** Immutable. The category for the product identified by the reference image. This should be one of "homegoods-v2", "apparel-v2", "toys-v2", "packagedgoods-v1" or "general-v1". The legacy categories "homegoods", "apparel", and "toys" are still supported, but these should not be used for new products. */
		productCategory: Option[String] = None,
	  /** Key-value pairs that can be attached to a product. At query time, constraints can be specified based on the product_labels. Note that integer values can be provided as strings, e.g. "1199". Only strings with integer values can match a range-based restriction which is to be supported soon. Multiple values can be assigned to the same key. One product may have up to 500 product_labels. Notice that the total number of distinct product_labels over all products in one ProductSet cannot exceed 1M, otherwise the product search pipeline will refuse to work for that ProductSet. */
		productLabels: Option[List[Schema.GoogleCloudVisionV1p4beta1ProductKeyValue]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ProductKeyValue(
	  /** The key of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		key: Option[String] = None,
	  /** The value of the label attached to the product. Cannot be empty and cannot exceed 128 bytes. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ProductSearchResultsGroupedResult(
	  /** The bounding polygon around the product detected in the query image. */
		boundingPoly: Option[Schema.GoogleCloudVisionV1p4beta1BoundingPoly] = None,
	  /** List of results, one for each product match. */
		results: Option[List[Schema.GoogleCloudVisionV1p4beta1ProductSearchResultsResult]] = None,
	  /** List of generic predictions for the object in the bounding box. */
		objectAnnotations: Option[List[Schema.GoogleCloudVisionV1p4beta1ProductSearchResultsObjectAnnotation]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ProductSearchResultsObjectAnnotation(
	  /** Object ID that should align with EntityAnnotation mid. */
		mid: Option[String] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Object name, expressed in its `language_code` language. */
		name: Option[String] = None,
	  /** Score of the result. Range [0, 1]. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVisionV1p4beta1ImageAnnotationContext(
	  /** The URI of the file used to produce the image. */
		uri: Option[String] = None,
	  /** If the file was a PDF or TIFF, this field gives the page number within the file used to produce the image. */
		pageNumber: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p4beta1AsyncBatchAnnotateFilesResponse(
	  /** The list of file annotation responses, one for each request in AsyncBatchAnnotateFilesRequest. */
		responses: Option[List[Schema.GoogleCloudVisionV1p4beta1AsyncAnnotateFileResponse]] = None
	)
	
	case class GoogleCloudVisionV1p4beta1AsyncAnnotateFileResponse(
	  /** The output location and metadata from AsyncAnnotateFileRequest. */
		outputConfig: Option[Schema.GoogleCloudVisionV1p4beta1OutputConfig] = None
	)
	
	case class GoogleCloudVisionV1p4beta1OutputConfig(
	  /** The Google Cloud Storage location to write the output(s) to. */
		gcsDestination: Option[Schema.GoogleCloudVisionV1p4beta1GcsDestination] = None,
	  /** The max number of response protos to put into each output JSON file on Google Cloud Storage. The valid range is [1, 100]. If not specified, the default value is 20. For example, for one pdf file with 100 pages, 100 response protos will be generated. If `batch_size` = 20, then 5 json files each containing 20 response protos will be written under the prefix `gcs_destination`.`uri`. Currently, batch_size only applies to GcsDestination, with potential future support for other output configurations. */
		batchSize: Option[Int] = None
	)
	
	case class GoogleCloudVisionV1p4beta1GcsDestination(
	  /** Google Cloud Storage URI prefix where the results will be stored. Results will be in JSON format and preceded by its corresponding input URI prefix. This field can either represent a gcs file prefix or gcs directory. In either case, the uri should be unique because in order to get all of the output files, you will need to do a wildcard gcs search on the uri prefix you provide. Examples: &#42; File Prefix: gs://bucket-name/here/filenameprefix The output files will be created in gs://bucket-name/here/ and the names of the output files will begin with "filenameprefix". &#42; Directory Prefix: gs://bucket-name/some/location/ The output files will be created in gs://bucket-name/some/location/ and the names of the output files could be anything because there was no filename prefix specified. If multiple outputs, each response is still AnnotateFileResponse, each of which contains some subset of the full list of AnnotateImageResponse. Multiple outputs can happen if, for example, the output JSON is too large and overflows into multiple sharded files. */
		uri: Option[String] = None
	)
	
	object GoogleCloudVisionV1p4beta1OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, RUNNING, DONE, CANCELLED }
	}
	case class GoogleCloudVisionV1p4beta1OperationMetadata(
	  /** Current state of the batch operation. */
		state: Option[Schema.GoogleCloudVisionV1p4beta1OperationMetadata.StateEnum] = None,
	  /** The time when the batch request was received. */
		createTime: Option[String] = None,
	  /** The time when the operation result was last updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudVisionV1p4beta1AsyncBatchAnnotateImagesResponse(
	  /** The output location and metadata from AsyncBatchAnnotateImagesRequest. */
		outputConfig: Option[Schema.GoogleCloudVisionV1p4beta1OutputConfig] = None
	)
	
	case class GoogleCloudVisionV1p4beta1BatchAnnotateFilesResponse(
	  /** The list of file annotation responses, each response corresponding to each AnnotateFileRequest in BatchAnnotateFilesRequest. */
		responses: Option[List[Schema.GoogleCloudVisionV1p4beta1AnnotateFileResponse]] = None
	)
}
