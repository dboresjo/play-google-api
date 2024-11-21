package com.boresjo.play.api.google.gamesManagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AchievementResetResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#achievementResetResponse`. */
		kind: Option[String] = None,
	  /** The ID of an achievement for which player state has been updated. */
		definitionId: Option[String] = None,
	  /** Flag to indicate if the requested update actually occurred. */
		updateOccurred: Option[Boolean] = None,
	  /** The current state of the achievement. This is the same as the initial state of the achievement. Possible values are: - "`HIDDEN`"- Achievement is hidden. - "`REVEALED`" - Achievement is revealed. - "`UNLOCKED`" - Achievement is unlocked.  */
		currentState: Option[String] = None
	)
	
	case class AchievementResetAllResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#achievementResetAllResponse`. */
		kind: Option[String] = None,
	  /** The achievement reset results. */
		results: Option[List[Schema.AchievementResetResponse]] = None
	)
	
	case class AchievementResetMultipleForAllRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#achievementResetMultipleForAllRequest`. */
		kind: Option[String] = None,
	  /** The IDs of achievements to reset. */
		achievement_ids: Option[List[String]] = None
	)
	
	case class EventsResetMultipleForAllRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#eventsResetMultipleForAllRequest`. */
		kind: Option[String] = None,
	  /** The IDs of events to reset. */
		event_ids: Option[List[String]] = None
	)
	
	case class HiddenPlayerList(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#hiddenPlayerList`. */
		kind: Option[String] = None,
	  /** The players. */
		items: Option[List[Schema.HiddenPlayer]] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class HiddenPlayer(
	  /** Output only. Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#hiddenPlayer`. */
		kind: Option[String] = None,
	  /** Output only. The player information. */
		player: Option[Schema.Player] = None,
	  /** Output only. The time this player was hidden. */
		hiddenTimeMillis: Option[String] = None
	)
	
	object Player {
		case class NameItem(
		  /** The given name of this player. In some places, this is known as the first name. */
			givenName: Option[String] = None,
		  /** The family name of this player. In some places, this is known as the last name. */
			familyName: Option[String] = None
		)
	}
	case class Player(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#player`. */
		kind: Option[String] = None,
	  /** The ID of the player. */
		playerId: Option[String] = None,
	  /** The name to display for the player. */
		displayName: Option[String] = None,
	  /** The base URL for the image that represents the player. */
		avatarImageUrl: Option[String] = None,
	  /** The url to the portrait mode player banner image. */
		bannerUrlPortrait: Option[String] = None,
	  /** The url to the landscape mode player banner image. */
		bannerUrlLandscape: Option[String] = None,
	  /** The player's profile settings. Controls whether or not the player's profile is visible to other players. */
		profileSettings: Option[Schema.ProfileSettings] = None,
	  /** An object representation of the individual components of the player's name. For some players, these fields may not be present. */
		name: Option[Schema.Player.NameItem] = None,
	  /** An object to represent Play Game experience information for the player. */
		experienceInfo: Option[Schema.GamesPlayerExperienceInfoResource] = None,
	  /** The player's title rewarded for their game activities. */
		title: Option[String] = None,
	  /** The player ID that was used for this player the first time they signed into the game in question. This is only populated for calls to player.get for the requesting player, only if the player ID has subsequently changed, and only to clients that support remapping player IDs. */
		originalPlayerId: Option[String] = None
	)
	
	case class ProfileSettings(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#profileSettings`. */
		kind: Option[String] = None,
		profileVisible: Option[Boolean] = None
	)
	
	case class GamesPlayerExperienceInfoResource(
	  /** The current number of experience points for the player. */
		currentExperiencePoints: Option[String] = None,
	  /** The timestamp when the player was leveled up, in millis since Unix epoch UTC. */
		lastLevelUpTimestampMillis: Option[String] = None,
	  /** The current level of the player. */
		currentLevel: Option[Schema.GamesPlayerLevelResource] = None,
	  /** The next level of the player. If the current level is the maximum level, this should be same as the current level. */
		nextLevel: Option[Schema.GamesPlayerLevelResource] = None
	)
	
	case class GamesPlayerLevelResource(
	  /** The level for the user. */
		level: Option[Int] = None,
	  /** The minimum experience points for this level. */
		minExperiencePoints: Option[String] = None,
	  /** The maximum experience points for this level. */
		maxExperiencePoints: Option[String] = None
	)
	
	case class PlayerScoreResetResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#playerScoreResetResponse`. */
		kind: Option[String] = None,
	  /** The ID of an leaderboard for which player state has been updated. */
		definitionId: Option[String] = None,
	  /** The time spans of the updated score. Possible values are: - "`ALL_TIME`" - The score is an all-time score. - "`WEEKLY`" - The score is a weekly score. - "`DAILY`" - The score is a daily score.  */
		resetScoreTimeSpans: Option[List[String]] = None
	)
	
	case class PlayerScoreResetAllResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#playerScoreResetAllResponse`. */
		kind: Option[String] = None,
	  /** The leaderboard reset results. */
		results: Option[List[Schema.PlayerScoreResetResponse]] = None
	)
	
	case class ScoresResetMultipleForAllRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `gamesManagement#scoresResetMultipleForAllRequest`. */
		kind: Option[String] = None,
	  /** The IDs of leaderboards to reset. */
		leaderboard_ids: Option[List[String]] = None
	)
}
