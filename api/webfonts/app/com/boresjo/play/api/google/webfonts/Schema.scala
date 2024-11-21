package com.boresjo.play.api.google.webfonts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class WebfontList(
	  /** This kind represents a list of webfont objects in the webfonts service. */
		kind: Option[String] = None,
	  /** The list of fonts currently served by the Google Fonts API. */
		items: Option[List[Schema.Webfont]] = None
	)
	
	case class Webfont(
	  /** The name of the font. */
		family: Option[String] = None,
	  /** The available variants for the font. */
		variants: Option[List[String]] = None,
	  /** The scripts supported by the font. */
		subsets: Option[List[String]] = None,
	  /** The font version. */
		version: Option[String] = None,
	  /** The date (format "yyyy-MM-dd") the font was modified for the last time. */
		lastModified: Option[String] = None,
	  /** The font files (with all supported scripts) for each one of the available variants, as a key : value map. */
		files: Option[Map[String, String]] = None,
	  /** The category of the font. */
		category: Option[String] = None,
	  /** This kind represents a webfont object in the webfonts service. */
		kind: Option[String] = None,
	  /** Font URL for menu subset, a subset of the font that is enough to display the font name */
		menu: Option[String] = None,
	  /** Axis for variable fonts. */
		axes: Option[List[Schema.Axis]] = None,
	  /** The color format(s) available for this family. */
		colorCapabilities: Option[List[String]] = None
	)
	
	case class Axis(
	  /** tag name. */
		tag: Option[String] = None,
	  /** minimum value */
		start: Option[BigDecimal] = None,
	  /** maximum value */
		end: Option[BigDecimal] = None
	)
}
