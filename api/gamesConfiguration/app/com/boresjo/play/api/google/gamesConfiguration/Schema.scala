package com.boresjo.play.api.google.gamesConfiguration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object AchievementConfiguration {
		enum AchievementTypeEnum extends Enum[AchievementTypeEnum] { case ACHIEVEMENT_TYPE_UNSPECIFIED, STANDARD, INCREMENTAL }
		enum InitialStateEnum extends Enum[InitialStateEnum] { case INITIAL_STATE_UNSPECIFIED, HIDDEN, REVEALED }
	}
	case class AchievementConfiguration(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#achievementConfiguration`. */
		kind: Option[String] = None,
	  /** The token for this resource. */
		token: Option[String] = None,
	  /** The ID of the achievement. */
		id: Option[String] = None,
	  /** The type of the achievement. */
		achievementType: Option[Schema.AchievementConfiguration.AchievementTypeEnum] = None,
	  /** The initial state of the achievement. */
		initialState: Option[Schema.AchievementConfiguration.InitialStateEnum] = None,
	  /** Steps to unlock. Only applicable to incremental achievements. */
		stepsToUnlock: Option[Int] = None,
	  /** The draft data of the achievement. */
		draft: Option[Schema.AchievementConfigurationDetail] = None,
	  /** The read-only published data of the achievement. */
		published: Option[Schema.AchievementConfigurationDetail] = None
	)
	
	case class AchievementConfigurationDetail(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#achievementConfigurationDetail`. */
		kind: Option[String] = None,
	  /** Localized strings for the achievement name. */
		name: Option[Schema.LocalizedStringBundle] = None,
	  /** Localized strings for the achievement description. */
		description: Option[Schema.LocalizedStringBundle] = None,
	  /** Point value for the achievement. */
		pointValue: Option[Int] = None,
	  /** The icon url of this achievement. Writes to this field are ignored. */
		iconUrl: Option[String] = None,
	  /** The sort rank of this achievement. Writes to this field are ignored. */
		sortRank: Option[Int] = None
	)
	
	case class LocalizedStringBundle(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#localizedStringBundle`. */
		kind: Option[String] = None,
	  /** The locale strings. */
		translations: Option[List[Schema.LocalizedString]] = None
	)
	
	case class LocalizedString(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#localizedString`. */
		kind: Option[String] = None,
	  /** The locale string. */
		locale: Option[String] = None,
	  /** The string value. */
		value: Option[String] = None
	)
	
	case class AchievementConfigurationListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#achievementConfigurationListResponse`. */
		kind: Option[String] = None,
	  /** The achievement configurations. */
		items: Option[List[Schema.AchievementConfiguration]] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object LeaderboardConfiguration {
		enum ScoreOrderEnum extends Enum[ScoreOrderEnum] { case SCORE_ORDER_UNSPECIFIED, LARGER_IS_BETTER, SMALLER_IS_BETTER }
	}
	case class LeaderboardConfiguration(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#leaderboardConfiguration`. */
		kind: Option[String] = None,
	  /** The token for this resource. */
		token: Option[String] = None,
	  /** The ID of the leaderboard. */
		id: Option[String] = None,
		scoreOrder: Option[Schema.LeaderboardConfiguration.ScoreOrderEnum] = None,
	  /** Minimum score that can be posted to this leaderboard. */
		scoreMin: Option[String] = None,
	  /** Maximum score that can be posted to this leaderboard. */
		scoreMax: Option[String] = None,
	  /** The draft data of the leaderboard. */
		draft: Option[Schema.LeaderboardConfigurationDetail] = None,
	  /** The read-only published data of the leaderboard. */
		published: Option[Schema.LeaderboardConfigurationDetail] = None
	)
	
	case class LeaderboardConfigurationDetail(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#leaderboardConfigurationDetail`. */
		kind: Option[String] = None,
	  /** Localized strings for the leaderboard name. */
		name: Option[Schema.LocalizedStringBundle] = None,
	  /** The icon url of this leaderboard. Writes to this field are ignored. */
		iconUrl: Option[String] = None,
	  /** The sort rank of this leaderboard. Writes to this field are ignored. */
		sortRank: Option[Int] = None,
	  /** The score formatting for the leaderboard. */
		scoreFormat: Option[Schema.GamesNumberFormatConfiguration] = None
	)
	
	object GamesNumberFormatConfiguration {
		enum NumberFormatTypeEnum extends Enum[NumberFormatTypeEnum] { case NUMBER_FORMAT_TYPE_UNSPECIFIED, NUMERIC, TIME_DURATION, CURRENCY }
	}
	case class GamesNumberFormatConfiguration(
	  /** The formatting for the number. */
		numberFormatType: Option[Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum] = None,
	  /** An optional suffix for the NUMERIC format type. These strings follow the same plural rules as all Android string resources. */
		suffix: Option[Schema.GamesNumberAffixConfiguration] = None,
	  /** The number of decimal places for number. Only used for NUMERIC format type. */
		numDecimalPlaces: Option[Int] = None,
	  /** The curreny code string. Only used for CURRENCY format type. */
		currencyCode: Option[String] = None
	)
	
	case class GamesNumberAffixConfiguration(
	  /** When the language requires special treatment of the number 0 (as in Arabic). */
		zero: Option[Schema.LocalizedStringBundle] = None,
	  /** When the language requires special treatment of numbers like one (as with the number 1 in English and most other languages; in Russian, any number ending in 1 but not ending in 11 is in this class). */
		one: Option[Schema.LocalizedStringBundle] = None,
	  /** When the language requires special treatment of numbers like two (as with 2 in Welsh, or 102 in Slovenian). */
		two: Option[Schema.LocalizedStringBundle] = None,
	  /** When the language requires special treatment of "small" numbers (as with 2, 3, and 4 in Czech; or numbers ending 2, 3, or 4 but not 12, 13, or 14 in Polish). */
		few: Option[Schema.LocalizedStringBundle] = None,
	  /** When the language requires special treatment of "large" numbers (as with numbers ending 11-99 in Maltese). */
		many: Option[Schema.LocalizedStringBundle] = None,
	  /** When the language does not require special treatment of the given quantity (as with all numbers in Chinese, or 42 in English). */
		other: Option[Schema.LocalizedStringBundle] = None
	)
	
	case class LeaderboardConfigurationListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesConfiguration#leaderboardConfigurationListResponse`. */
		kind: Option[String] = None,
	  /** The leaderboard configurations. */
		items: Option[List[Schema.LeaderboardConfiguration]] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None
	)
}
