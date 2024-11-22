package com.boresjo.play.api.google.places

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/maps-platform.places""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places */,
		"""https://www.googleapis.com/auth/maps-platform.places.details""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places.details */,
		"""https://www.googleapis.com/auth/maps-platform.places.autocomplete""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places.autocomplete */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/maps-platform.places.getphotomedia""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places.getphotomedia */,
		"""https://www.googleapis.com/auth/maps-platform.places.nearbysearch""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places.nearbysearch */,
		"""https://www.googleapis.com/auth/maps-platform.places.textsearch""" /* Private Service: https://www.googleapis.com/auth/maps-platform.places.textsearch */
	)

	private val BASE_URL = "https://places.googleapis.com/"

	object places {
		/** Search for places near locations. */
		class searchNearby(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.places""", """https://www.googleapis.com/auth/maps-platform.places.nearbysearch""")
			/** Perform the request */
			def withGoogleMapsPlacesV1SearchNearbyRequest(body: Schema.GoogleMapsPlacesV1SearchNearbyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleMapsPlacesV1SearchNearbyResponse])
		}
		object searchNearby {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): searchNearby = new searchNearby(ws.url(BASE_URL + s"v1/places:searchNearby").addQueryStringParameters())
		}
		/** Get the details of a place based on its resource name, which is a string in the `places/{place_id}` format. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleMapsPlacesV1Place]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.places""", """https://www.googleapis.com/auth/maps-platform.places.details""")
			/** Optional. A string which identifies an Autocomplete session for billing purposes. Must be a URL and filename safe base64 string with at most 36 ASCII characters in length. Otherwise an INVALID_ARGUMENT error is returned. The session begins when the user starts typing a query, and concludes when they select a place and a call to Place Details or Address Validation is made. Each session can have multiple queries, followed by one Place Details or Address Validation request. The credentials used for each request within a session must belong to the same Google Cloud Console project. Once a session has concluded, the token is no longer valid; your app must generate a fresh token for each session. If the `session_token` parameter is omitted, or if you reuse a session token, the session is charged as if no session token was provided (each request is billed separately). We recommend the following guidelines: &#42; Use session tokens for all Place Autocomplete calls. &#42; Generate a fresh token for each session. Using a version 4 UUID is recommended. &#42; Ensure that the credentials used for all Place Autocomplete, Place Details, and Address Validation requests within a session belong to the same Cloud Console project. &#42; Be sure to pass a unique session token for each new session. Using the same token for more than one session will result in each request being billed individually. */
			def withSessionToken(sessionToken: String) = new get(req.addQueryStringParameters("sessionToken" -> sessionToken.toString))
			/** Optional. Place details will be displayed with the preferred language if available. Current list of supported languages: https://developers.google.com/maps/faq#languagesupport. */
			def withLanguageCode(languageCode: String) = new get(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The Unicode country/region code (CLDR) of the location where the request is coming from. This parameter is used to display the place details, like region-specific place name, if available. The parameter can affect results based on applicable law. For more information, see https://www.unicode.org/cldr/charts/latest/supplemental/territory_language_information.html. Note that 3-digit region codes are not currently supported. */
			def withRegionCode(regionCode: String) = new get(req.addQueryStringParameters("regionCode" -> regionCode.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleMapsPlacesV1Place])
		}
		object get {
			def apply(placesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/places/${placesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleMapsPlacesV1Place]] = (fun: get) => fun.apply()
		}
		/** Text query based place search. */
		class searchText(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.places""", """https://www.googleapis.com/auth/maps-platform.places.textsearch""")
			/** Perform the request */
			def withGoogleMapsPlacesV1SearchTextRequest(body: Schema.GoogleMapsPlacesV1SearchTextRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleMapsPlacesV1SearchTextResponse])
		}
		object searchText {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): searchText = new searchText(ws.url(BASE_URL + s"v1/places:searchText").addQueryStringParameters())
		}
		/** Returns predictions for the given input. */
		class autocomplete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.places""", """https://www.googleapis.com/auth/maps-platform.places.autocomplete""")
			/** Perform the request */
			def withGoogleMapsPlacesV1AutocompletePlacesRequest(body: Schema.GoogleMapsPlacesV1AutocompletePlacesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleMapsPlacesV1AutocompletePlacesResponse])
		}
		object autocomplete {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): autocomplete = new autocomplete(ws.url(BASE_URL + s"v1/places:autocomplete").addQueryStringParameters())
		}
		object photos {
			/** Get a photo media with a photo reference string. */
			class getMedia(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleMapsPlacesV1PhotoMedia]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.places""", """https://www.googleapis.com/auth/maps-platform.places.getphotomedia""")
				/** Optional. Specifies the maximum desired height, in pixels, of the image. If the image is smaller than the values specified, the original image will be returned. If the image is larger in either dimension, it will be scaled to match the smaller of the two dimensions, restricted to its original aspect ratio. Both the max_height_px and max_width_px properties accept an integer between 1 and 4800, inclusively. If the value is not within the allowed range, an INVALID_ARGUMENT error will be returned. At least one of max_height_px or max_width_px needs to be specified. If neither max_height_px nor max_width_px is specified, an INVALID_ARGUMENT error will be returned.<br>Format: int32 */
				def withMaxHeightPx(maxHeightPx: Int) = new getMedia(req.addQueryStringParameters("maxHeightPx" -> maxHeightPx.toString))
				/** Optional. Specifies the maximum desired width, in pixels, of the image. If the image is smaller than the values specified, the original image will be returned. If the image is larger in either dimension, it will be scaled to match the smaller of the two dimensions, restricted to its original aspect ratio. Both the max_height_px and max_width_px properties accept an integer between 1 and 4800, inclusively. If the value is not within the allowed range, an INVALID_ARGUMENT error will be returned. At least one of max_height_px or max_width_px needs to be specified. If neither max_height_px nor max_width_px is specified, an INVALID_ARGUMENT error will be returned.<br>Format: int32 */
				def withMaxWidthPx(maxWidthPx: Int) = new getMedia(req.addQueryStringParameters("maxWidthPx" -> maxWidthPx.toString))
				/** Optional. If set, skip the default HTTP redirect behavior and render a text format (for example, in JSON format for HTTP use case) response. If not set, an HTTP redirect will be issued to redirect the call to the image media. This option is ignored for non-HTTP requests. */
				def withSkipHttpRedirect(skipHttpRedirect: Boolean) = new getMedia(req.addQueryStringParameters("skipHttpRedirect" -> skipHttpRedirect.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleMapsPlacesV1PhotoMedia])
			}
			object getMedia {
				def apply(placesId :PlayApi, photosId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getMedia = new getMedia(ws.url(BASE_URL + s"v1/places/${placesId}/photos/${photosId}/media").addQueryStringParameters("name" -> name.toString))
				given Conversion[getMedia, Future[Schema.GoogleMapsPlacesV1PhotoMedia]] = (fun: getMedia) => fun.apply()
			}
		}
	}
}
