package com.boresjo.play.api.google.solar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class FinancialDetails(
	  /** Total cost of electricity the user would have paid over the lifetime period if they didn't install solar. */
		costOfElectricityWithoutSolar: Option[Schema.Money] = None,
	  /** Whether net metering is allowed. */
		netMeteringAllowed: Option[Boolean] = None,
	  /** Amount of money the user will receive from Solar Renewable Energy Credits over the panel lifetime; this applies if the user buys (with or without a loan) the panels. */
		lifetimeSrecTotal: Option[Schema.Money] = None,
	  /** How many AC kWh we think the solar panels will generate in their first year. */
		initialAcKwhPerYear: Option[BigDecimal] = None,
	  /** Amount of money available from state incentives; this applies if the user buys (with or without a loan) the panels. */
		stateIncentive: Option[Schema.Money] = None,
	  /** Amount of money available from federal incentives; this applies if the user buys (with or without a loan) the panels. */
		federalIncentive: Option[Schema.Money] = None,
	  /** Percentage (0-100) of the user's power supplied by solar. Valid for the first year but approximately correct for future years. */
		solarPercentage: Option[BigDecimal] = None,
	  /** Amount of money available from utility incentives; this applies if the user buys (with or without a loan) the panels. */
		utilityIncentive: Option[Schema.Money] = None,
	  /** The percentage (0-100) of solar electricity production we assumed was exported to the grid, based on the first quarter of production. This affects the calculations if net metering is not allowed. */
		percentageExportedToGrid: Option[BigDecimal] = None,
	  /** Utility bill for electricity not produced by solar, for the lifetime of the panels. */
		remainingLifetimeUtilityBill: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class RoofSegmentSizeAndSunshineStats(
	  /** The bounding box of the roof segment. */
		boundingBox: Option[Schema.LatLngBox] = None,
	  /** Total size and sunlight quantiles for the roof segment. */
		stats: Option[Schema.SizeAndSunshineStats] = None,
	  /** Compass direction the roof segment is pointing in. 0 = North, 90 = East, 180 = South. For a "flat" roof segment (`pitch_degrees` very near 0), azimuth is not well defined, so for consistency, we define it arbitrarily to be 0 (North). */
		azimuthDegrees: Option[BigDecimal] = None,
	  /** Angle of the roof segment relative to the theoretical ground plane. 0 = parallel to the ground, 90 = perpendicular to the ground. */
		pitchDegrees: Option[BigDecimal] = None,
	  /** A point near the center of the roof segment. */
		center: Option[Schema.LatLng] = None,
	  /** The height of the roof segment plane, in meters above sea level, at the point designated by `center`. Together with the pitch, azimuth, and center location, this fully defines the roof segment plane. */
		planeHeightAtCenterMeters: Option[BigDecimal] = None
	)
	
	case class SavingsOverTime(
	  /** Using the assumed discount rate, what is the present value of the cumulative lifetime savings? */
		presentValueOfSavingsLifetime: Option[Schema.Money] = None,
	  /** Indicates whether this scenario is financially viable. Will be false for scenarios with poor financial viability (e.g., money-losing). */
		financiallyViable: Option[Boolean] = None,
	  /** Savings in the first year after panel installation. */
		savingsYear1: Option[Schema.Money] = None,
	  /** Using the assumed discount rate, what is the present value of the cumulative 20-year savings? */
		presentValueOfSavingsYear20: Option[Schema.Money] = None,
	  /** Savings in the first twenty years after panel installation. */
		savingsYear20: Option[Schema.Money] = None,
	  /** Savings in the entire panel lifetime. */
		savingsLifetime: Option[Schema.Money] = None
	)
	
	case class SolarPanelConfig(
	  /** Information about the production of each roof segment that is carrying at least one panel in this layout. `roof_segment_summaries[i]` describes the i-th roof segment, including its size, expected production and orientation. */
		roofSegmentSummaries: Option[List[Schema.RoofSegmentSummary]] = None,
	  /** Total number of panels. Note that this is redundant to (the sum of) the corresponding fields in roof_segment_summaries. */
		panelsCount: Option[Int] = None,
	  /** How much sunlight energy this layout captures over the course of a year, in DC kWh, assuming the panels described above. */
		yearlyEnergyDcKwh: Option[BigDecimal] = None
	)
	
	object BuildingInsights {
		enum ImageryQualityEnum extends Enum[ImageryQualityEnum] { case IMAGERY_QUALITY_UNSPECIFIED, HIGH, MEDIUM, LOW, BASE }
	}
	case class BuildingInsights(
	  /** Region code for the country (or region) this building is in. */
		regionCode: Option[String] = None,
	  /** When processing was completed on this imagery. */
		imageryProcessedDate: Option[Schema.Date] = None,
	  /** Administrative area 1 (e.g., in the US, the state) that contains this building. For example, in the US, the abbreviation might be "MA" or "CA." */
		administrativeArea: Option[String] = None,
	  /** Date that the underlying imagery was acquired. This is approximate. */
		imageryDate: Option[Schema.Date] = None,
	  /** Statistical area (e.g., US census tract) this building is in. */
		statisticalArea: Option[String] = None,
	  /** A point near the center of the building. */
		center: Option[Schema.LatLng] = None,
	  /** Solar potential of the building. */
		solarPotential: Option[Schema.SolarPotential] = None,
	  /** Postal code (e.g., US zip code) this building is contained by. */
		postalCode: Option[String] = None,
	  /** The bounding box of the building. */
		boundingBox: Option[Schema.LatLngBox] = None,
	  /** The resource name for the building, of the format `building/`. */
		name: Option[String] = None,
	  /** The quality of the imagery used to compute the data for this building. */
		imageryQuality: Option[Schema.BuildingInsights.ImageryQualityEnum] = None
	)
	
	object SolarPanel {
		enum OrientationEnum extends Enum[OrientationEnum] { case SOLAR_PANEL_ORIENTATION_UNSPECIFIED, LANDSCAPE, PORTRAIT }
	}
	case class SolarPanel(
	  /** How much sunlight energy this layout captures over the course of a year, in DC kWh. */
		yearlyEnergyDcKwh: Option[BigDecimal] = None,
	  /** The centre of the panel. */
		center: Option[Schema.LatLng] = None,
	  /** Index in roof_segment_stats of the `RoofSegmentSizeAndSunshineStats` which corresponds to the roof segment that this panel is placed on. */
		segmentIndex: Option[Int] = None,
	  /** The orientation of the panel. */
		orientation: Option[Schema.SolarPanel.OrientationEnum] = None
	)
	
	case class LatLngBox(
	  /** The northeast corner of the box. */
		ne: Option[Schema.LatLng] = None,
	  /** The southwest corner of the box. */
		sw: Option[Schema.LatLng] = None
	)
	
	case class RoofSegmentSummary(
	  /** Compass direction the roof segment is pointing in. 0 = North, 90 = East, 180 = South. For a "flat" roof segment (`pitch_degrees` very near 0), azimuth is not well defined, so for consistency, we define it arbitrarily to be 0 (North). */
		azimuthDegrees: Option[BigDecimal] = None,
	  /** Index in roof_segment_stats of the corresponding `RoofSegmentSizeAndSunshineStats`. */
		segmentIndex: Option[Int] = None,
	  /** Angle of the roof segment relative to the theoretical ground plane. 0 = parallel to the ground, 90 = perpendicular to the ground. */
		pitchDegrees: Option[BigDecimal] = None,
	  /** The total number of panels on this segment. */
		panelsCount: Option[Int] = None,
	  /** How much sunlight energy this part of the layout captures over the course of a year, in DC kWh, assuming the panels described above. */
		yearlyEnergyDcKwh: Option[BigDecimal] = None
	)
	
	case class CashPurchaseSavings(
	  /** Number of years until payback occurs. A negative value means payback never occurs within the lifetime period. */
		paybackYears: Option[BigDecimal] = None,
	  /** How much is saved (or not) over the lifetime period. */
		savings: Option[Schema.SavingsOverTime] = None,
	  /** Initial cost after tax incentives: it's the amount that must be paid during first year. Contrast with `out_of_pocket_cost`, which is before tax incentives. */
		upfrontCost: Option[Schema.Money] = None,
	  /** The value of all tax rebates. */
		rebateValue: Option[Schema.Money] = None,
	  /** Initial cost before tax incentives: the amount that must be paid out-of-pocket. Contrast with `upfront_cost`, which is after tax incentives. */
		outOfPocketCost: Option[Schema.Money] = None
	)
	
	case class HttpBody(
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None,
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None
	)
	
	case class FinancedPurchaseSavings(
	  /** The value of all tax rebates (including Federal Investment Tax Credit (ITC)). */
		rebateValue: Option[Schema.Money] = None,
	  /** How much is saved (or not) over the lifetime period. */
		savings: Option[Schema.SavingsOverTime] = None,
	  /** The interest rate on loans assumed in this set of calculations. */
		loanInterestRate: Option[BigDecimal] = None,
	  /** Annual loan payments. */
		annualLoanPayment: Option[Schema.Money] = None
	)
	
	object DataLayers {
		enum ImageryQualityEnum extends Enum[ImageryQualityEnum] { case IMAGERY_QUALITY_UNSPECIFIED, HIGH, MEDIUM, LOW, BASE }
	}
	case class DataLayers(
	  /** The quality of the result's imagery. */
		imageryQuality: Option[Schema.DataLayers.ImageryQualityEnum] = None,
	  /** When processing was completed on this imagery. */
		imageryProcessedDate: Option[Schema.Date] = None,
	  /** The URL for the annual flux map (annual sunlight on roofs) of the region. Values are kWh/kW/year. This is &#42;unmasked flux&#42;: flux is computed for every location, not just building rooftops. Invalid locations are stored as -9999: locations outside our coverage area will be invalid, and a few locations inside the coverage area, where we were unable to calculate flux, will also be invalid. */
		annualFluxUrl: Option[String] = None,
	  /** The URL for an image of RGB data (aerial photo) of the region. */
		rgbUrl: Option[String] = None,
	  /** The URL for an image of the DSM (Digital Surface Model) of the region. Values are in meters above EGM96 geoid (i.e., sea level). Invalid locations (where we don't have data) are stored as -9999. */
		dsmUrl: Option[String] = None,
	  /** When the source imagery (from which all the other data are derived) in this region was taken. It is necessarily somewhat approximate, as the images may have been taken over more than one day. */
		imageryDate: Option[Schema.Date] = None,
	  /** Twelve URLs for hourly shade, corresponding to January...December, in order. Each GeoTIFF will contain 24 bands, corresponding to the 24 hours of the day. Each pixel is a 32 bit integer, corresponding to the (up to) 31 days of that month; a 1 bit means that the corresponding location is able to see the sun at that day, of that hour, of that month. Invalid locations are stored as -9999 (since this is negative, it has bit 31 set, and no valid value could have bit 31 set as that would correspond to the 32nd day of the month). An example may be useful. If you want to know whether a point (at pixel location (x, y)) saw sun at 4pm on the 22nd of June you would: 1. fetch the sixth URL in this list (corresponding to June). 1. look up the 17th channel (corresponding to 4pm). 1. read the 32-bit value at (x, y). 1. read bit 21 of the value (corresponding to the 22nd of the month). 1. if that bit is a 1, then that spot saw the sun at 4pm 22 June. More formally: Given `month` (1-12), `day` (1...month max; February has 28 days) and `hour` (0-23), the shade/sun for that month/day/hour at a position `(x, y)` is the bit ``` (hourly_shade[month - 1])(x, y)[hour] & (1 << (day - 1)) ``` where `(x, y)` is spatial indexing, `[month - 1]` refers to fetching the `month - 1`st URL (indexing from zero), `[hour]` is indexing into the channels, and a final non-zero result means "sunny". There are no leap days, and DST doesn't exist (all days are 24 hours long; noon is always "standard time" noon). */
		hourlyShadeUrls: Option[List[String]] = None,
	  /** The URL for the monthly flux map (sunlight on roofs, broken down by month) of the region. Values are kWh/kW/year. The GeoTIFF pointed to by this URL will contain twelve bands, corresponding to January...December, in order. */
		monthlyFluxUrl: Option[String] = None,
	  /** The URL for the building mask image: one bit per pixel saying whether that pixel is considered to be part of a rooftop or not. */
		maskUrl: Option[String] = None
	)
	
	case class LatLng(
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None,
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None
	)
	
	case class FinancialAnalysis(
	  /** How much electricity the house uses in an average month, based on the bill size and the local electricity rates. */
		averageKwhPerMonth: Option[BigDecimal] = None,
	  /** Cost and benefit of buying the solar panels by financing the purchase. */
		financedPurchaseSavings: Option[Schema.FinancedPurchaseSavings] = None,
	  /** Index in solar_panel_configs of the optimum solar layout for this bill size. This can be -1 indicating that there is no layout. In this case, the remaining submessages will be omitted. */
		panelConfigIndex: Option[Int] = None,
	  /** Financial information that applies regardless of the financing method used. */
		financialDetails: Option[Schema.FinancialDetails] = None,
	  /** Cost and benefit of buying the solar panels with cash. */
		cashPurchaseSavings: Option[Schema.CashPurchaseSavings] = None,
	  /** Whether this is the bill size selected to be the default bill for the area this building is in. Exactly one `FinancialAnalysis` in `BuildingSolarPotential` should have `default_bill` set. */
		defaultBill: Option[Boolean] = None,
	  /** Cost and benefit of leasing the solar panels. */
		leasingSavings: Option[Schema.LeasingSavings] = None,
	  /** The monthly electric bill this analysis assumes. */
		monthlyBill: Option[Schema.Money] = None
	)
	
	case class SizeAndSunshineStats(
	  /** The ground footprint area covered by the roof or roof segment, in m^2. */
		groundAreaMeters2: Option[BigDecimal] = None,
	  /** Quantiles of the pointwise sunniness across the area. If there are N values here, this represents the (N-1)-iles. For example, if there are 5 values, then they would be the quartiles (min, 25%, 50%, 75%, max). Values are in annual kWh/kW like max_sunshine_hours_per_year. */
		sunshineQuantiles: Option[List[BigDecimal]] = None,
	  /** The area of the roof or roof segment, in m^2. This is the roof area (accounting for tilt), not the ground footprint area. */
		areaMeters2: Option[BigDecimal] = None
	)
	
	case class LeasingSavings(
	  /** Whether leases are supported in this juristiction by the financial calculation engine. If this field is false, then the values in this message should probably be ignored. This is independent of `leases_allowed`: in some areas leases are allowed, but under conditions that aren't handled by the financial models. */
		leasesSupported: Option[Boolean] = None,
	  /** Whether leases are allowed in this juristiction (leases are not allowed in some states). If this field is false, then the values in this message should probably be ignored. */
		leasesAllowed: Option[Boolean] = None,
	  /** How much is saved (or not) over the lifetime period. */
		savings: Option[Schema.SavingsOverTime] = None,
	  /** Estimated annual leasing cost. */
		annualLeasingCost: Option[Schema.Money] = None
	)
	
	case class SolarPotential(
	  /** Maximum number of sunshine hours received per year, by any point on the roof. Sunshine hours are a measure of the total amount of insolation (energy) received per year. 1 sunshine hour = 1 kWh per kW (where kW refers to kW of capacity under Standard Testing Conditions). */
		maxSunshineHoursPerYear: Option[BigDecimal] = None,
	  /** Height, in meters in portrait orientation, of the panel used in the calculations. */
		panelHeightMeters: Option[BigDecimal] = None,
	  /** Size of the maximum array - that is, the maximum number of panels that can fit on the roof. */
		maxArrayPanelsCount: Option[Int] = None,
	  /** Size and sunlight quantiles for the entire building, including parts of the roof that were not assigned to some roof segment. Because the orientations of these parts are not well characterised, the roof area estimate is unreliable, but the ground area estimate is reliable. It may be that a more reliable whole building roof area can be obtained by scaling the roof area from whole_roof_stats by the ratio of the ground areas of `building_stats` and `whole_roof_stats`. */
		buildingStats: Option[Schema.SizeAndSunshineStats] = None,
	  /** Each SolarPanel describes a single solar panel. They are listed in the order that the panel layout algorithm placed this. This is usually, though not always, in decreasing order of annual energy production. */
		solarPanels: Option[List[Schema.SolarPanel]] = None,
	  /** Each SolarPanelConfig describes a different arrangement of solar panels on the roof. They are in order of increasing number of panels. The `SolarPanelConfig` with panels_count=N is based on the first N panels in the `solar_panels` list. This field is only populated if at least 4 panels can fit on a roof. */
		solarPanelConfigs: Option[List[Schema.SolarPanelConfig]] = None,
	  /** Size and sunlight quantiles for each roof segment. */
		roofSegmentStats: Option[List[Schema.RoofSegmentSizeAndSunshineStats]] = None,
	  /** Equivalent amount of CO2 produced per MWh of grid electricity. This is a measure of the carbon intensity of grid electricity displaced by solar electricity. */
		carbonOffsetFactorKgPerMwh: Option[BigDecimal] = None,
	  /** The expected lifetime, in years, of the solar panels. This is used in the financial calculations. */
		panelLifetimeYears: Option[Int] = None,
	  /** Size, in square meters, of the maximum array. */
		maxArrayAreaMeters2: Option[BigDecimal] = None,
	  /** Width, in meters in portrait orientation, of the panel used in the calculations. */
		panelWidthMeters: Option[BigDecimal] = None,
	  /** Capacity, in watts, of the panel used in the calculations. */
		panelCapacityWatts: Option[BigDecimal] = None,
	  /** A FinancialAnalysis gives the savings from going solar assuming a given monthly bill and a given electricity provider. They are in order of increasing order of monthly bill amount. This field will be empty for buildings in areas for which the Solar API does not have enough information to perform financial computations. */
		financialAnalyses: Option[List[Schema.FinancialAnalysis]] = None,
	  /** Total size and sunlight quantiles for the part of the roof that was assigned to some roof segment. Despite the name, this may not include the entire building. See building_stats. */
		wholeRoofStats: Option[Schema.SizeAndSunshineStats] = None
	)
	
	case class Date(
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
}
