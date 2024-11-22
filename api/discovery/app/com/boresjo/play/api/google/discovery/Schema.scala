package com.boresjo.play.api.google.discovery

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object RestDescription {
		case class IconsItem(
		  /** The URL of the 16x16 icon. */
			x16: Option[String] = None,
		  /** The URL of the 32x32 icon. */
			x32: Option[String] = None
		)
		
		object AuthItem {
			object Oauth2Item {
				case class ScopesItem(
				  /** Description of scope. */
					description: Option[String] = None
				)
			}
			case class Oauth2Item(
			  /** Available OAuth 2.0 scopes. */
				scopes: Option[Map[String, Schema.RestDescription.AuthItem.Oauth2Item.ScopesItem]] = None
			)
		}
		case class AuthItem(
		  /** OAuth 2.0 authentication information. */
			oauth2: Option[Schema.RestDescription.AuthItem.Oauth2Item] = None
		)
		
		case class EndpointsItem(
		  /** The location of the endpoint */
			location: Option[String] = None,
		  /** The URL of the endpoint target host */
			endpointUrl: Option[String] = None,
		  /** A string describing the host designated by the URL */
			description: Option[String] = None,
		  /** Whether this endpoint is deprecated */
			deprecated: Option[Boolean] = None
		)
	}
	case class RestDescription(
	  /** Enable exponential backoff for suitable methods in the generated clients. */
		exponentialBackoffDefault: Option[Boolean] = None,
	  /** The version of this API. */
		version: Option[String] = None,
	  /** The description of this API. */
		description: Option[String] = None,
	  /** The kind for this response. */
		kind: Option[String] = Some("""discovery#restDescription"""),
	  /** Common parameters that apply across all apis. */
		parameters: Option[Map[String, Schema.JsonSchema]] = None,
	  /** The version of this API. */
		revision: Option[String] = None,
	  /** [DEPRECATED] The base path for REST requests. */
		basePath: Option[String] = None,
	  /** The name of the owner of this API. See ownerDomain. */
		ownerName: Option[String] = None,
	  /** [DEPRECATED] The base URL for REST requests. */
		baseUrl: Option[String] = None,
	  /** Links to 16x16 and 32x32 icons representing the API. */
		icons: Option[Schema.RestDescription.IconsItem] = None,
	  /** The protocol described by this document. */
		protocol: Option[String] = Some("""rest"""),
	  /** The resources in this API. */
		resources: Option[Map[String, Schema.RestResource]] = None,
	  /** The name of this API. */
		name: Option[String] = None,
	  /** The package of the owner of this API. See ownerDomain. */
		packagePath: Option[String] = None,
	  /** The schemas for this API. */
		schemas: Option[Map[String, Schema.JsonSchema]] = None,
	  /** Authentication information. */
		auth: Option[Schema.RestDescription.AuthItem] = None,
	  /** Indicates how the API name should be capitalized and split into various parts. Useful for generating pretty class names. */
		canonicalName: Option[String] = None,
	  /** The domain of the owner of this API. Together with the ownerName and a packagePath values, this can be used to generate a library for this API which would have a unique fully qualified name. */
		ownerDomain: Option[String] = None,
	  /** Labels for the status of this API, such as labs or deprecated. */
		labels: Option[List[String]] = None,
	  /** The path for REST batch requests. */
		batchPath: Option[String] = None,
		version_module: Option[Boolean] = None,
	  /** The title of this API. */
		title: Option[String] = None,
	  /** Indicate the version of the Discovery API used to generate this doc. */
		discoveryVersion: Option[String] = Some("""v1"""),
	  /** The base path for all REST requests. */
		servicePath: Option[String] = None,
	  /** API-level methods for this API. */
		methods: Option[Map[String, Schema.RestMethod]] = None,
	  /** A list of location-based endpoint objects for this API. Each object contains the endpoint URL, location, description and deprecation status. */
		endpoints: Option[List[Schema.RestDescription.EndpointsItem]] = None,
	  /** The ETag for this response. */
		etag: Option[String] = None,
	  /** A list of supported features for this API. */
		features: Option[List[String]] = None,
	  /** A link to human readable documentation for the API. */
		documentationLink: Option[String] = None,
	  /** The root URL under which all API services live. */
		rootUrl: Option[String] = None,
	  /** The ID of this API. */
		id: Option[String] = None
	)
	
	case class RestResource(
	  /** Methods on this resource. */
		methods: Option[Map[String, Schema.RestMethod]] = None,
	  /** Whether this resource is deprecated. */
		deprecated: Option[Boolean] = None,
	  /** Sub-resources on this resource. */
		resources: Option[Map[String, Schema.RestResource]] = None
	)
	
	object DirectoryList {
		object ItemsItem {
			case class IconsItem(
			  /** The URL of the 16x16 icon. */
				x16: Option[String] = None,
			  /** The URL of the 32x32 icon. */
				x32: Option[String] = None
			)
		}
		case class ItemsItem(
		  /** The description of this API. */
			description: Option[String] = None,
		  /** The URL for the discovery REST document. */
			discoveryRestUrl: Option[String] = None,
		  /** Links to 16x16 and 32x32 icons representing the API. */
			icons: Option[Schema.DirectoryList.ItemsItem.IconsItem] = None,
		  /** True if this version is the preferred version to use. */
			preferred: Option[Boolean] = None,
		  /** The kind for this response. */
			kind: Option[String] = Some("""discovery#directoryItem"""),
		  /** The name of the API. */
			name: Option[String] = None,
		  /** The title of this API. */
			title: Option[String] = None,
		  /** A link to the discovery document. */
			discoveryLink: Option[String] = None,
		  /** The id of this API. */
			id: Option[String] = None,
		  /** The version of the API. */
			version: Option[String] = None,
		  /** A link to human readable documentation for the API. */
			documentationLink: Option[String] = None,
		  /** Labels for the status of this API, such as labs or deprecated. */
			labels: Option[List[String]] = None
		)
	}
	case class DirectoryList(
	  /** The kind for this response. */
		kind: Option[String] = Some("""discovery#directoryList"""),
	  /** Indicate the version of the Discovery API used to generate this doc. */
		discoveryVersion: Option[String] = Some("""v1"""),
	  /** The individual directory entries. One entry per api/version pair. */
		items: Option[List[Schema.DirectoryList.ItemsItem]] = None
	)
	
	object JsonSchema {
		case class AnnotationsItem(
		  /** A list of methods for which this property is required on requests. */
			required: Option[List[String]] = None
		)
		
		object VariantItem {
			case class MapItem(
				type_value: Option[String] = None,
				$ref: Option[String] = None
			)
		}
		case class VariantItem(
		  /** The map of discriminant value to schema to use for parsing.. */
			map: Option[List[Schema.JsonSchema.VariantItem.MapItem]] = None,
		  /** The name of the type discriminant property. */
			discriminant: Option[String] = None
		)
	}
	case class JsonSchema(
	  /** Whether the parameter is deprecated. */
		deprecated: Option[Boolean] = None,
	  /** If this is a schema for an array, this property is the schema for each element in the array. */
		items: Option[Schema.JsonSchema] = None,
	  /** Whether this parameter may appear multiple times. */
		repeated: Option[Boolean] = None,
	  /** If this is a schema for an object, this property is the schema for any additional properties with dynamic keys on this object. */
		additionalProperties: Option[Schema.JsonSchema] = None,
	  /** Additional information about this property. */
		annotations: Option[Schema.JsonSchema.AnnotationsItem] = None,
	  /** The value type for this schema. A list of values can be found here: http://tools.ietf.org/html/draft-zyp-json-schema-03#section-5.1 */
		`type`: Option[String] = None,
	  /** An additional regular expression or key that helps constrain the value. For more details see: http://tools.ietf.org/html/draft-zyp-json-schema-03#section-5.23 */
		format: Option[String] = None,
	  /** A description of this object. */
		description: Option[String] = None,
	  /** Values this parameter may take (if it is an enum). */
		`enum`: Option[List[String]] = None,
	  /** The default value of this property (if one exists). */
		default: Option[String] = None,
	  /** The deprecation status for the enums. Each position maps to the corresponding value in the "enum" array. */
		enumDeprecated: Option[List[Boolean]] = None,
	  /** Whether this parameter goes in the query or the path for REST requests. */
		location: Option[String] = None,
	  /** The regular expression this parameter must conform to. Uses Java 6 regex format: http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html */
		pattern: Option[String] = None,
	  /** The descriptions for the enums. Each position maps to the corresponding value in the "enum" array. */
		enumDescriptions: Option[List[String]] = None,
	  /** Whether the parameter is required. */
		required: Option[Boolean] = None,
	  /** The maximum value of this parameter. */
		maximum: Option[String] = None,
	  /** A reference to another schema. The value of this property is the "id" of another schema. */
		$ref: Option[String] = None,
	  /** In a variant data type, the value of one property is used to determine how to interpret the entire entity. Its value must exist in a map of descriminant values to schema names. */
		variant: Option[Schema.JsonSchema.VariantItem] = None,
	  /** The value is read-only, generated by the service. The value cannot be modified by the client. If the value is included in a POST, PUT, or PATCH request, it is ignored by the service. */
		readOnly: Option[Boolean] = None,
	  /** Unique identifier for this schema. */
		id: Option[String] = None,
	  /** If this is a schema for an object, list the schema for each property of this object. */
		properties: Option[Map[String, Schema.JsonSchema]] = None,
	  /** The minimum value of this parameter. */
		minimum: Option[String] = None
	)
	
	object RestMethod {
		case class RequestItem(
		  /** Schema ID for the request schema. */
			$ref: Option[String] = None,
		  /** parameter name. */
			parameterName: Option[String] = None
		)
		
		object MediaUploadItem {
			object ProtocolsItem {
				case class ResumableItem(
				  /** True if this endpoint supports uploading multipart media. */
					multipart: Option[Boolean] = Some(true),
				  /** The URI path to be used for upload. Should be used in conjunction with the basePath property at the api-level. */
					path: Option[String] = None
				)
				
				case class SimpleItem(
				  /** True if this endpoint supports upload multipart media. */
					multipart: Option[Boolean] = Some(true),
				  /** The URI path to be used for upload. Should be used in conjunction with the basePath property at the api-level. */
					path: Option[String] = None
				)
			}
			case class ProtocolsItem(
			  /** Supports the Resumable Media Upload protocol. */
				resumable: Option[Schema.RestMethod.MediaUploadItem.ProtocolsItem.ResumableItem] = None,
			  /** Supports uploading as a single HTTP request. */
				simple: Option[Schema.RestMethod.MediaUploadItem.ProtocolsItem.SimpleItem] = None
			)
		}
		case class MediaUploadItem(
		  /** Supported upload protocols. */
			protocols: Option[Schema.RestMethod.MediaUploadItem.ProtocolsItem] = None,
		  /** Maximum size of a media upload, such as "1MB", "2GB" or "3TB". */
			maxSize: Option[String] = None,
		  /** MIME Media Ranges for acceptable media uploads to this method. */
			accept: Option[List[String]] = None
		)
		
		case class ResponseItem(
		  /** Schema ID for the response schema. */
			$ref: Option[String] = None
		)
	}
	case class RestMethod(
	  /** Details for all parameters in this method. */
		parameters: Option[Map[String, Schema.JsonSchema]] = None,
	  /** The URI path of this REST method in (RFC 6570) format without level 2 features ({+var}). Supplementary to the path property. */
		flatPath: Option[String] = None,
	  /** Description of this method. */
		description: Option[String] = None,
	  /** The schema for the request. */
		request: Option[Schema.RestMethod.RequestItem] = None,
	  /** Whether this method supports subscriptions. */
		supportsSubscription: Option[Boolean] = None,
	  /** The URI path of this REST method. Should be used in conjunction with the basePath property at the api-level. */
		path: Option[String] = None,
	  /** Ordered list of required parameters, serves as a hint to clients on how to structure their method signatures. The array is ordered such that the "most-significant" parameter appears first. */
		parameterOrder: Option[List[String]] = None,
	  /** OAuth 2.0 scopes applicable to this method. */
		scopes: Option[List[String]] = None,
	  /** Whether this method is deprecated. */
		deprecated: Option[Boolean] = None,
	  /** Media upload parameters. */
		mediaUpload: Option[Schema.RestMethod.MediaUploadItem] = None,
	  /** Whether this method requires an ETag to be specified. The ETag is sent as an HTTP If-Match or If-None-Match header. */
		etagRequired: Option[Boolean] = None,
	  /** Whether this method supports media uploads. */
		supportsMediaUpload: Option[Boolean] = None,
	  /** Whether this method supports media downloads. */
		supportsMediaDownload: Option[Boolean] = None,
	  /** Indicates that downloads from this method should use the download service URL (i.e. "/download"). Only applies if the method supports media download. */
		useMediaDownloadService: Option[Boolean] = None,
	  /** A unique ID for this method. This property can be used to match methods between different versions of Discovery. */
		id: Option[String] = None,
	  /** HTTP method used by this method. */
		httpMethod: Option[String] = None,
	  /** The API Version of this method, as passed in via the `X-Goog-Api-Version` header or `$apiVersion` query parameter. */
		apiVersion: Option[String] = None,
	  /** The schema for the response. */
		response: Option[Schema.RestMethod.ResponseItem] = None
	)
}
