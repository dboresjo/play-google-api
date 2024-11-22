package com.boresjo.play.api.google.pollen

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class HttpBody(
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None,
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None
	)
	
	case class Date(
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class Color(
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None
	)
	
	object PollenTypeInfo {
		enum CodeEnum extends Enum[CodeEnum] { case POLLEN_TYPE_UNSPECIFIED, GRASS, TREE, WEED }
	}
	case class PollenTypeInfo(
	  /** The pollen type's code name. For example: "GRASS" */
		code: Option[Schema.PollenTypeInfo.CodeEnum] = None,
	  /** Contains the Universal Pollen Index (UPI) data for the pollen type. */
		indexInfo: Option[Schema.IndexInfo] = None,
	  /** Textual list of explanations, related to health insights based on the current pollen levels. */
		healthRecommendations: Option[List[String]] = None,
	  /** A human readable representation of the pollen type name. Example: "Grass" */
		displayName: Option[String] = None,
	  /** Indication whether the plant is in season or not. */
		inSeason: Option[Boolean] = None
	)
	
	case class DayInfo(
	  /** The date in UTC at which the pollen forecast data is represented. */
		date: Option[Schema.Date] = None,
	  /** This list will include up to three pollen types (GRASS, WEED, TREE) affecting the location specified in the request. */
		pollenTypeInfo: Option[List[Schema.PollenTypeInfo]] = None,
	  /** This list will include up to 15 pollen species affecting the location specified in the request. */
		plantInfo: Option[List[Schema.PlantInfo]] = None
	)
	
	case class LookupForecastResponse(
	  /** Optional. The token to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** The ISO_3166-1 alpha-2 code of the country/region corresponding to the location provided in the request. This field might be omitted from the response if the location provided in the request resides in a disputed territory. */
		regionCode: Option[String] = None,
	  /** Required. This object contains the daily forecast information for each day requested. */
		dailyInfo: Option[List[Schema.DayInfo]] = None
	)
	
	object PlantInfo {
		enum CodeEnum extends Enum[CodeEnum] { case PLANT_UNSPECIFIED, ALDER, ASH, BIRCH, COTTONWOOD, ELM, MAPLE, OLIVE, JUNIPER, OAK, PINE, CYPRESS_PINE, HAZEL, GRAMINALES, RAGWEED, MUGWORT, JAPANESE_CEDAR, JAPANESE_CYPRESS }
	}
	case class PlantInfo(
	  /** Indication of either the plant is in season or not. */
		inSeason: Option[Boolean] = None,
	  /** The plant code name. For example: "COTTONWOOD". A list of all available codes could be found here. */
		code: Option[Schema.PlantInfo.CodeEnum] = None,
	  /** A human readable representation of the plant name. Example: “Cottonwood". */
		displayName: Option[String] = None,
	  /** Contains general information about plants, including details on their seasonality, special shapes and colors, information about allergic cross-reactions, and plant photos. */
		plantDescription: Option[Schema.PlantDescription] = None,
	  /** This object contains data representing specific pollen index value, category and description. */
		indexInfo: Option[Schema.IndexInfo] = None
	)
	
	object IndexInfo {
		enum CodeEnum extends Enum[CodeEnum] { case INDEX_UNSPECIFIED, UPI }
	}
	case class IndexInfo(
	  /** Textual explanation of current index level. */
		indexDescription: Option[String] = None,
	  /** Text classification of index numerical score interpretation. The index consists of six categories: &#42; 0: "None" &#42; 1: "Very low" &#42; 2: "Low" &#42; 3: "Moderate" &#42; 4: "High" &#42; 5: "Very high */
		category: Option[String] = None,
	  /** The color used to represent the Pollen Index numeric score. */
		color: Option[Schema.Color] = None,
	  /** The index's numeric score. Numeric range is between 0 and 5. */
		value: Option[Int] = None,
	  /** The index's code. This field represents the index for programming purposes by using snake cases instead of spaces. Example: "UPI". */
		code: Option[Schema.IndexInfo.CodeEnum] = None,
	  /** A human readable representation of the index name. Example: "Universal Pollen Index". */
		displayName: Option[String] = None
	)
	
	object PlantDescription {
		enum TypeEnum extends Enum[TypeEnum] { case POLLEN_TYPE_UNSPECIFIED, GRASS, TREE, WEED }
	}
	case class PlantDescription(
	  /** Link to a closeup picture of the plant. */
		pictureCloseup: Option[String] = None,
	  /** A human readable representation of the plant family name. Example: "Betulaceae (the Birch family)". */
		family: Option[String] = None,
	  /** Textual description of the plants' colors of leaves, bark, flowers or seeds that helps identify the plant. */
		specialColors: Option[String] = None,
	  /** Link to the picture of the plant. */
		picture: Option[String] = None,
	  /** Textual description of the plants' shapes of leaves, bark, flowers or seeds that helps identify the plant. */
		specialShapes: Option[String] = None,
	  /** Textual list of explanations of seasons where the pollen is active. Example: "Late winter, spring". */
		season: Option[String] = None,
	  /** The plant's pollen type. For example: "GRASS". A list of all available codes could be found here. */
		`type`: Option[Schema.PlantDescription.TypeEnum] = None,
	  /** Textual description of pollen cross reaction plants. Example: Alder, Hazel, Hornbeam, Beech, Willow, and Oak pollen. */
		crossReaction: Option[String] = None
	)
}
