package com.boresjo.play.api.google.digitalassetlinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object CheckResponse {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, ERROR_CODE_INVALID_QUERY, ERROR_CODE_FETCH_ERROR, ERROR_CODE_FAILED_SSL_VALIDATION, ERROR_CODE_REDIRECT, ERROR_CODE_TOO_LARGE, ERROR_CODE_MALFORMED_HTTP_RESPONSE, ERROR_CODE_WRONG_CONTENT_TYPE, ERROR_CODE_MALFORMED_CONTENT, ERROR_CODE_SECURE_ASSET_INCLUDES_INSECURE, ERROR_CODE_FETCH_BUDGET_EXHAUSTED }
	}
	case class CheckResponse(
	  /** Set to true if the assets specified in the request are linked by the relation specified in the request. */
		linked: Option[Boolean] = None,
	  /** From serving time, how much longer the response should be considered valid barring further updates. REQUIRED */
		maxAge: Option[String] = None,
	  /** Human-readable message containing information intended to help end users understand, reproduce and debug the result. The message will be in English and we are currently not planning to offer any translations. Please note that no guarantees are made about the contents or format of this string. Any aspect of it may be subject to change without notice. You should not attempt to programmatically parse this data. For programmatic access, use the error_code field below. */
		debugString: Option[String] = None,
	  /** Error codes that describe the result of the Check operation. */
		errorCode: Option[List[Schema.CheckResponse.ErrorCodeEnum]] = None
	)
	
	object ListResponse {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, ERROR_CODE_INVALID_QUERY, ERROR_CODE_FETCH_ERROR, ERROR_CODE_FAILED_SSL_VALIDATION, ERROR_CODE_REDIRECT, ERROR_CODE_TOO_LARGE, ERROR_CODE_MALFORMED_HTTP_RESPONSE, ERROR_CODE_WRONG_CONTENT_TYPE, ERROR_CODE_MALFORMED_CONTENT, ERROR_CODE_SECURE_ASSET_INCLUDES_INSECURE, ERROR_CODE_FETCH_BUDGET_EXHAUSTED }
	}
	case class ListResponse(
	  /** A list of all the matching statements that have been found. */
		statements: Option[List[Schema.Statement]] = None,
	  /** From serving time, how much longer the response should be considered valid barring further updates. REQUIRED */
		maxAge: Option[String] = None,
	  /** Human-readable message containing information intended to help end users understand, reproduce and debug the result. The message will be in English and we are currently not planning to offer any translations. Please note that no guarantees are made about the contents or format of this string. Any aspect of it may be subject to change without notice. You should not attempt to programmatically parse this data. For programmatic access, use the error_code field below. */
		debugString: Option[String] = None,
	  /** Error codes that describe the result of the List operation. */
		errorCode: Option[List[Schema.ListResponse.ErrorCodeEnum]] = None
	)
	
	case class Statement(
	  /** Every statement has a source asset. REQUIRED */
		source: Option[Schema.Asset] = None,
	  /** The relation identifies the use of the statement as intended by the source asset's owner (that is, the person or entity who issued the statement). Every complete statement has a relation. We identify relations with strings of the format `/`, where `` must be one of a set of pre-defined purpose categories, and `` is a free-form lowercase alphanumeric string that describes the specific use case of the statement. Refer to [our API documentation](/digital-asset-links/v1/relation-strings) for the current list of supported relations. Example: `delegate_permission/common.handle_all_urls` REQUIRED */
		relation: Option[String] = None,
	  /** Every statement has a target asset. REQUIRED */
		target: Option[Schema.Asset] = None
	)
	
	case class Asset(
	  /** Set if this is a web asset. */
		web: Option[Schema.WebAsset] = None,
	  /** Set if this is an Android App asset. */
		androidApp: Option[Schema.AndroidAppAsset] = None
	)
	
	case class WebAsset(
	  /** Web assets are identified by a URL that contains only the scheme, hostname and port parts. The format is http[s]://[:] Hostnames must be fully qualified: they must end in a single period ("`.`"). Only the schemes "http" and "https" are currently allowed. Port numbers are given as a decimal number, and they must be omitted if the standard port numbers are used: 80 for http and 443 for https. We call this limited URL the "site". All URLs that share the same scheme, hostname and port are considered to be a part of the site and thus belong to the web asset. Example: the asset with the site `https://www.google.com` contains all these URLs: &#42; `https://www.google.com/` &#42; `https://www.google.com:443/` &#42; `https://www.google.com/foo` &#42; `https://www.google.com/foo?bar` &#42; `https://www.google.com/foo#bar` &#42; `https://user@password:www.google.com/` But it does not contain these URLs: &#42; `http://www.google.com/` (wrong scheme) &#42; `https://google.com/` (hostname does not match) &#42; `https://www.google.com:444/` (port does not match) REQUIRED */
		site: Option[String] = None
	)
	
	case class AndroidAppAsset(
	  /** Android App assets are naturally identified by their Java package name. For example, the Google Maps app uses the package name `com.google.android.apps.maps`. REQUIRED */
		packageName: Option[String] = None,
	  /** Because there is no global enforcement of package name uniqueness, we also require a signing certificate, which in combination with the package name uniquely identifies an app. Some apps' signing keys are rotated, so they may be signed by different keys over time. We treat these as distinct assets, since we use (package name, cert) as the unique ID. This should not normally pose any problems as both versions of the app will make the same or similar statements. Other assets making statements about the app will have to be updated when a key is rotated, however. (Note that the syntaxes for publishing and querying for statements contain syntactic sugar to easily let you specify apps that are known by multiple certificates.) REQUIRED */
		certificate: Option[Schema.CertificateInfo] = None
	)
	
	case class CertificateInfo(
	  /** The uppercase SHA-265 fingerprint of the certificate. From the PEM certificate, it can be acquired like this: $ keytool -printcert -file $CERTFILE | grep SHA256: SHA256: 14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83: \ 42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5 or like this: $ openssl x509 -in $CERTFILE -noout -fingerprint -sha256 SHA256 Fingerprint=14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64: \ 16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5 In this example, the contents of this field would be `14:6D:E9:83:C5:73: 06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF: 44:E5`. If these tools are not available to you, you can convert the PEM certificate into the DER format, compute the SHA-256 hash of that string and represent the result as a hexstring (that is, uppercase hexadecimal representations of each octet, separated by colons). */
		sha256Fingerprint: Option[String] = None
	)
}
