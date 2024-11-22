package com.boresjo.play.api.google.games

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AchievementDefinitionsListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementDefinitionsListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The achievement definitions. */
		items: Option[List[Schema.AchievementDefinition]] = None
	)
	
	object AchievementDefinition {
		enum AchievementTypeEnum extends Enum[AchievementTypeEnum] { case STANDARD, INCREMENTAL }
		enum InitialStateEnum extends Enum[InitialStateEnum] { case HIDDEN, REVEALED, UNLOCKED }
	}
	case class AchievementDefinition(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementDefinition`. */
		kind: Option[String] = None,
	  /** The ID of the achievement. */
		id: Option[String] = None,
	  /** The name of the achievement. */
		name: Option[String] = None,
	  /** The description of the achievement. */
		description: Option[String] = None,
	  /** The type of the achievement. */
		achievementType: Option[Schema.AchievementDefinition.AchievementTypeEnum] = None,
	  /** The total steps for an incremental achievement. */
		totalSteps: Option[Int] = None,
	  /** The total steps for an incremental achievement as a string. */
		formattedTotalSteps: Option[String] = None,
	  /** The image URL for the revealed achievement icon. */
		revealedIconUrl: Option[String] = None,
	  /** Indicates whether the revealed icon image being returned is a default image, or is provided by the game. */
		isRevealedIconUrlDefault: Option[Boolean] = None,
	  /** The image URL for the unlocked achievement icon. */
		unlockedIconUrl: Option[String] = None,
	  /** Indicates whether the unlocked icon image being returned is a default image, or is game-provided. */
		isUnlockedIconUrlDefault: Option[Boolean] = None,
	  /** The initial state of the achievement. */
		initialState: Option[Schema.AchievementDefinition.InitialStateEnum] = None,
	  /** Experience points which will be earned when unlocking this achievement. */
		experiencePoints: Option[String] = None
	)
	
	case class AchievementIncrementResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementIncrementResponse`. */
		kind: Option[String] = None,
	  /** The current steps recorded for this incremental achievement. */
		currentSteps: Option[Int] = None,
	  /** Whether the current steps for the achievement has reached the number of steps required to unlock. */
		newlyUnlocked: Option[Boolean] = None
	)
	
	case class PlayerAchievementListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerAchievementListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The achievements. */
		items: Option[List[Schema.PlayerAchievement]] = None
	)
	
	object PlayerAchievement {
		enum AchievementStateEnum extends Enum[AchievementStateEnum] { case HIDDEN, REVEALED, UNLOCKED }
	}
	case class PlayerAchievement(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerAchievement`. */
		kind: Option[String] = None,
	  /** The ID of the achievement. */
		id: Option[String] = None,
	  /** The current steps for an incremental achievement. */
		currentSteps: Option[Int] = None,
	  /** The current steps for an incremental achievement as a string. */
		formattedCurrentStepsString: Option[String] = None,
	  /** The state of the achievement. */
		achievementState: Option[Schema.PlayerAchievement.AchievementStateEnum] = None,
	  /** The timestamp of the last modification to this achievement's state. */
		lastUpdatedTimestamp: Option[String] = None,
	  /** Experience points earned for the achievement. This field is absent for achievements that have not yet been unlocked and 0 for achievements that have been unlocked by testers but that are unpublished. */
		experiencePoints: Option[String] = None
	)
	
	object AchievementRevealResponse {
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case REVEALED, UNLOCKED }
	}
	case class AchievementRevealResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementRevealResponse`. */
		kind: Option[String] = None,
	  /** The current state of the achievement for which a reveal was attempted. This might be `UNLOCKED` if the achievement was already unlocked. */
		currentState: Option[Schema.AchievementRevealResponse.CurrentStateEnum] = None
	)
	
	case class AchievementSetStepsAtLeastResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementSetStepsAtLeastResponse`. */
		kind: Option[String] = None,
	  /** The current steps recorded for this incremental achievement. */
		currentSteps: Option[Int] = None,
	  /** Whether the current steps for the achievement has reached the number of steps required to unlock. */
		newlyUnlocked: Option[Boolean] = None
	)
	
	case class AchievementUnlockResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementUnlockResponse`. */
		kind: Option[String] = None,
	  /** Whether this achievement was newly unlocked (that is, whether the unlock request for the achievement was the first for the player). */
		newlyUnlocked: Option[Boolean] = None
	)
	
	case class AchievementUpdateMultipleRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementUpdateMultipleRequest`. */
		kind: Option[String] = None,
	  /** The individual achievement update requests. */
		updates: Option[List[Schema.AchievementUpdateRequest]] = None
	)
	
	object AchievementUpdateRequest {
		enum UpdateTypeEnum extends Enum[UpdateTypeEnum] { case REVEAL, UNLOCK, INCREMENT, SET_STEPS_AT_LEAST }
	}
	case class AchievementUpdateRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementUpdateRequest`. */
		kind: Option[String] = None,
	  /** The achievement this update is being applied to. */
		achievementId: Option[String] = None,
	  /** The type of update being applied. */
		updateType: Option[Schema.AchievementUpdateRequest.UpdateTypeEnum] = None,
	  /** The payload if an update of type `INCREMENT` was requested for the achievement. */
		incrementPayload: Option[Schema.GamesAchievementIncrement] = None,
	  /** The payload if an update of type `SET_STEPS_AT_LEAST` was requested for the achievement. */
		setStepsAtLeastPayload: Option[Schema.GamesAchievementSetStepsAtLeast] = None
	)
	
	case class GamesAchievementIncrement(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#GamesAchievementIncrement`. */
		kind: Option[String] = None,
	  /** The number of steps to be incremented. */
		steps: Option[Int] = None,
	  /** The requestId associated with an increment to an achievement. */
		requestId: Option[String] = None
	)
	
	case class GamesAchievementSetStepsAtLeast(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#GamesAchievementSetStepsAtLeast`. */
		kind: Option[String] = None,
	  /** The minimum number of steps for the achievement to be set to. */
		steps: Option[Int] = None
	)
	
	case class AchievementUpdateMultipleResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementUpdateMultipleResponse`. */
		kind: Option[String] = None,
	  /** The updated state of the achievements. */
		updatedAchievements: Option[List[Schema.AchievementUpdateResponse]] = None
	)
	
	object AchievementUpdateResponse {
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case HIDDEN, REVEALED, UNLOCKED }
	}
	case class AchievementUpdateResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#achievementUpdateResponse`. */
		kind: Option[String] = None,
	  /** The achievement this update is was applied to. */
		achievementId: Option[String] = None,
	  /** Whether the requested updates actually affected the achievement. */
		updateOccurred: Option[Boolean] = None,
	  /** The current state of the achievement. */
		currentState: Option[Schema.AchievementUpdateResponse.CurrentStateEnum] = None,
	  /** The current steps recorded for this achievement if it is incremental. */
		currentSteps: Option[Int] = None,
	  /** Whether this achievement was newly unlocked (that is, whether the unlock request for the achievement was the first for the player). */
		newlyUnlocked: Option[Boolean] = None
	)
	
	object Application {
		enum EnabledFeaturesEnum extends Enum[EnabledFeaturesEnum] { case SNAPSHOTS }
	}
	case class Application(
	  /** The ID of the application. */
		id: Option[String] = None,
	  /** The name of the application. */
		name: Option[String] = None,
	  /** The author of the application. */
		author: Option[String] = None,
	  /** The description of the application. */
		description: Option[String] = None,
	  /** The category of the application. */
		category: Option[Schema.ApplicationCategory] = None,
	  /** The assets of the application. */
		assets: Option[List[Schema.ImageAsset]] = None,
	  /** The instances of the application. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#application`. */
		kind: Option[String] = None,
	  /** The last updated timestamp of the application. */
		lastUpdatedTimestamp: Option[String] = None,
	  /** The number of achievements visible to the currently authenticated player. */
		achievement_count: Option[Int] = None,
	  /** The number of leaderboards visible to the currently authenticated player. */
		leaderboard_count: Option[Int] = None,
	  /** A list of features that have been enabled for the application. */
		enabledFeatures: Option[List[Schema.Application.EnabledFeaturesEnum]] = None,
	  /** A hint to the client UI for what color to use as an app-themed color. The color is given as an RGB triplet (e.g. "E0E0E0"). */
		themeColor: Option[String] = None
	)
	
	case class ApplicationCategory(
	  /** The primary category. */
		primary: Option[String] = None,
	  /** The secondary category. */
		secondary: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#applicationCategory`. */
		kind: Option[String] = None
	)
	
	case class ImageAsset(
	  /** The name of the asset. */
		name: Option[String] = None,
	  /** The width of the asset. */
		width: Option[Int] = None,
	  /** The height of the asset. */
		height: Option[Int] = None,
	  /** The URL of the asset. */
		url: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#imageAsset`. */
		kind: Option[String] = None
	)
	
	object Instance {
		enum PlatformTypeEnum extends Enum[PlatformTypeEnum] { case ANDROID, IOS, WEB_APP }
	}
	case class Instance(
	  /** The platform type. */
		platformType: Option[Schema.Instance.PlatformTypeEnum] = None,
	  /** Localized display name. */
		name: Option[String] = None,
	  /** Flag to show if this game instance supports turn based play. */
		turnBasedPlay: Option[Boolean] = None,
	  /** Flag to show if this game instance supports realtime play. */
		realtimePlay: Option[Boolean] = None,
	  /** Platform dependent details for Android. */
		androidInstance: Option[Schema.InstanceAndroidDetails] = None,
	  /** Platform dependent details for iOS. */
		iosInstance: Option[Schema.InstanceIosDetails] = None,
	  /** Platform dependent details for Web. */
		webInstance: Option[Schema.InstanceWebDetails] = None,
	  /** URI which shows where a user can acquire this instance. */
		acquisitionUri: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#instance`. */
		kind: Option[String] = None
	)
	
	case class InstanceAndroidDetails(
	  /** Android package name which maps to Google Play URL. */
		packageName: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#instanceAndroidDetails`. */
		kind: Option[String] = None,
	  /** Flag indicating whether the anti-piracy check is enabled. */
		enablePiracyCheck: Option[Boolean] = None,
	  /** Indicates that this instance is the default for new installations. */
		preferred: Option[Boolean] = None
	)
	
	case class InstanceIosDetails(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#instanceIosDetails`. */
		kind: Option[String] = None,
	  /** Bundle identifier. */
		bundleIdentifier: Option[String] = None,
	  /** iTunes App ID. */
		itunesAppId: Option[String] = None,
	  /** Flag to indicate if this instance supports iPhone. */
		supportIphone: Option[Boolean] = None,
	  /** Flag to indicate if this instance supports iPad. */
		supportIpad: Option[Boolean] = None,
	  /** Indicates that this instance is the default for new installations on iPhone devices. */
		preferredForIphone: Option[Boolean] = None,
	  /** Indicates that this instance is the default for new installations on iPad devices. */
		preferredForIpad: Option[Boolean] = None
	)
	
	case class InstanceWebDetails(
	  /** Launch URL for the game. */
		launchUrl: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#instanceWebDetails`. */
		kind: Option[String] = None,
	  /** Indicates that this instance is the default for new installations. */
		preferred: Option[Boolean] = None
	)
	
	case class ApplicationVerifyResponse(
	  /** The ID of the player that was issued the auth token used in this request. */
		player_id: Option[String] = None,
	  /** An alternate ID that was once used for the player that was issued the auth token used in this request. (This field is not normally populated.) */
		alternate_player_id: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#applicationVerifyResponse`. */
		kind: Option[String] = None
	)
	
	case class EndPoint(
	  /** A URL suitable for loading in a web browser for the requested endpoint. */
		url: Option[String] = None
	)
	
	case class PlayerEventListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerEventListResponse`. */
		kind: Option[String] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The player events. */
		items: Option[List[Schema.PlayerEvent]] = None
	)
	
	case class PlayerEvent(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerEvent`. */
		kind: Option[String] = None,
	  /** The ID of the event definition. */
		definitionId: Option[String] = None,
	  /** The ID of the player. */
		playerId: Option[String] = None,
	  /** The current number of times this event has occurred. */
		numEvents: Option[String] = None,
	  /** The current number of times this event has occurred, as a string. The formatting of this string depends on the configuration of your event in the Play Games Developer Console. */
		formattedNumEvents: Option[String] = None
	)
	
	case class EventDefinitionListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventDefinitionListResponse`. */
		kind: Option[String] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The event definitions. */
		items: Option[List[Schema.EventDefinition]] = None
	)
	
	object EventDefinition {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case REVEALED, HIDDEN }
	}
	case class EventDefinition(
	  /** The ID of the event. */
		id: Option[String] = None,
	  /** The visibility of event being tracked in this definition. */
		visibility: Option[Schema.EventDefinition.VisibilityEnum] = None,
	  /** The name to display for the event. */
		displayName: Option[String] = None,
	  /** The base URL for the image that represents the event. */
		imageUrl: Option[String] = None,
	  /** A list of events that are a child of this event. */
		childEvents: Option[List[Schema.EventChild]] = None,
	  /** Description of what this event represents. */
		description: Option[String] = None,
	  /** Indicates whether the icon image being returned is a default image, or is game-provided. */
		isDefaultImageUrl: Option[Boolean] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventDefinition`. */
		kind: Option[String] = None
	)
	
	case class EventChild(
	  /** The ID of the child event. */
		childId: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventChild`. */
		kind: Option[String] = None
	)
	
	case class EventRecordRequest(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventRecordRequest`. */
		kind: Option[String] = None,
	  /** The request ID used to identify this attempt to record events. */
		requestId: Option[String] = None,
	  /** The current time when this update was sent, in milliseconds, since 1970 UTC (Unix Epoch). */
		currentTimeMillis: Option[String] = None,
	  /** A list of the time period updates being made in this request. */
		timePeriods: Option[List[Schema.EventPeriodUpdate]] = None
	)
	
	case class EventPeriodUpdate(
	  /** The time period being covered by this update. */
		timePeriod: Option[Schema.EventPeriodRange] = None,
	  /** The updates being made for this time period. */
		updates: Option[List[Schema.EventUpdateRequest]] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventPeriodUpdate`. */
		kind: Option[String] = None
	)
	
	case class EventPeriodRange(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventPeriodRange`. */
		kind: Option[String] = None,
	  /** The time when this update period begins, in millis, since 1970 UTC (Unix Epoch). */
		periodStartMillis: Option[String] = None,
	  /** The time when this update period ends, in millis, since 1970 UTC (Unix Epoch). */
		periodEndMillis: Option[String] = None
	)
	
	case class EventUpdateRequest(
	  /** The ID of the event being modified in this update. */
		definitionId: Option[String] = None,
	  /** The number of times this event occurred in this time period. */
		updateCount: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventUpdateRequest`. */
		kind: Option[String] = None
	)
	
	case class EventUpdateResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventUpdateResponse`. */
		kind: Option[String] = None,
	  /** Any batch-wide failures which occurred applying updates. */
		batchFailures: Option[List[Schema.EventBatchRecordFailure]] = None,
	  /** Any failures updating a particular event. */
		eventFailures: Option[List[Schema.EventRecordFailure]] = None,
	  /** The current status of any updated events */
		playerEvents: Option[List[Schema.PlayerEvent]] = None
	)
	
	object EventBatchRecordFailure {
		enum FailureCauseEnum extends Enum[FailureCauseEnum] { case TOO_LARGE, TIME_PERIOD_EXPIRED, TIME_PERIOD_SHORT, TIME_PERIOD_LONG, ALREADY_UPDATED, RECORD_RATE_HIGH }
	}
	case class EventBatchRecordFailure(
	  /** The time range which was rejected; empty for a request-wide failure. */
		range: Option[Schema.EventPeriodRange] = None,
	  /** The cause for the update failure. */
		failureCause: Option[Schema.EventBatchRecordFailure.FailureCauseEnum] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventBatchRecordFailure`. */
		kind: Option[String] = None
	)
	
	object EventRecordFailure {
		enum FailureCauseEnum extends Enum[FailureCauseEnum] { case NOT_FOUND, INVALID_UPDATE_VALUE }
	}
	case class EventRecordFailure(
	  /** The ID of the event that was not updated. */
		eventId: Option[String] = None,
	  /** The cause for the update failure. */
		failureCause: Option[Schema.EventRecordFailure.FailureCauseEnum] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#eventRecordFailure`. */
		kind: Option[String] = None
	)
	
	object Leaderboard {
		enum OrderEnum extends Enum[OrderEnum] { case LARGER_IS_BETTER, SMALLER_IS_BETTER }
	}
	case class Leaderboard(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#leaderboard`. */
		kind: Option[String] = None,
	  /** The leaderboard ID. */
		id: Option[String] = None,
	  /** The name of the leaderboard. */
		name: Option[String] = None,
	  /** The icon for the leaderboard. */
		iconUrl: Option[String] = None,
	  /** Indicates whether the icon image being returned is a default image, or is game-provided. */
		isIconUrlDefault: Option[Boolean] = None,
	  /** How scores are ordered. */
		order: Option[Schema.Leaderboard.OrderEnum] = None
	)
	
	case class LeaderboardListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#leaderboardListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The leaderboards. */
		items: Option[List[Schema.Leaderboard]] = None
	)
	
	case class MetagameConfig(
	  /** Current version of the metagame configuration data. When this data is updated, the version number will be increased by one. */
		currentVersion: Option[Int] = None,
	  /** The list of player levels. */
		playerLevels: Option[List[Schema.PlayerLevel]] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#metagameConfig`. */
		kind: Option[String] = None
	)
	
	case class PlayerLevel(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerLevel`. */
		kind: Option[String] = None,
	  /** The level for the user. */
		level: Option[Int] = None,
	  /** The minimum experience points for this level. */
		minExperiencePoints: Option[String] = None,
	  /** The maximum experience points for this level. */
		maxExperiencePoints: Option[String] = None
	)
	
	case class CategoryListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#categoryListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of categories with usage data. */
		items: Option[List[Schema.Category]] = None
	)
	
	case class Category(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#category`. */
		kind: Option[String] = None,
	  /** The category name. */
		category: Option[String] = None,
	  /** Experience points earned in this category. */
		experiencePoints: Option[String] = None
	)
	
	object Player {
		case class NameItem(
		  /** The family name of this player. In some places, this is known as the last name. */
			familyName: Option[String] = None,
		  /** The given name of this player. In some places, this is known as the first name. */
			givenName: Option[String] = None
		)
		
		enum FriendStatusEnum extends Enum[FriendStatusEnum] { case NO_RELATIONSHIP, FRIEND }
	}
	case class Player(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#player` */
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
	  /** The player ID that was used for this player the first time they signed into the game in question. This is only populated for calls to player.get for the requesting player, only if the player ID has subsequently changed, and only to clients that support remapping player IDs. */
		originalPlayerId: Option[String] = None,
	  /** The player's profile settings. Controls whether or not the player's profile is visible to other players. */
		profileSettings: Option[Schema.ProfileSettings] = None,
	  /** A representation of the individual components of the name. */
		name: Option[Schema.Player.NameItem] = None,
	  /** An object to represent Play Game experience information for the player. */
		experienceInfo: Option[Schema.PlayerExperienceInfo] = None,
	  /** The player's title rewarded for their game activities. */
		title: Option[String] = None,
	  /** The friend status of the given player, relative to the requester. This is unset if the player is not sharing their friends list with the game. */
		friendStatus: Option[Schema.Player.FriendStatusEnum] = None,
	  /** Per-application unique player identifier. */
		gamePlayerId: Option[String] = None
	)
	
	object ProfileSettings {
		enum FriendsListVisibilityEnum extends Enum[FriendsListVisibilityEnum] { case VISIBLE, REQUEST_REQUIRED, UNAVAILABLE }
	}
	case class ProfileSettings(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#profileSettings`. */
		kind: Option[String] = None,
	  /** Whether the player's profile is visible to the currently signed in player. */
		profileVisible: Option[Boolean] = None,
		friendsListVisibility: Option[Schema.ProfileSettings.FriendsListVisibilityEnum] = None
	)
	
	case class PlayerExperienceInfo(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerExperienceInfo`. */
		kind: Option[String] = None,
	  /** The current number of experience points for the player. */
		currentExperiencePoints: Option[String] = None,
	  /** The timestamp when the player was leveled up, in millis since Unix epoch UTC. */
		lastLevelUpTimestampMillis: Option[String] = None,
	  /** The current level of the player. */
		currentLevel: Option[Schema.PlayerLevel] = None,
	  /** The next level of the player. If the current level is the maximum level, this should be same as the current level. */
		nextLevel: Option[Schema.PlayerLevel] = None
	)
	
	case class ScopedPlayerIds(
	  /** Game-scoped player identifier. This is the same id that is returned in GetPlayer game_player_id field. */
		gamePlayerId: Option[String] = None,
	  /** Identifier of the player across all games of the given developer. Every player has the same developer_player_key in all games of one developer. Developer player key changes for the game if the game is transferred to another developer. Note that game_player_id will stay unchanged. */
		developerPlayerKey: Option[String] = None
	)
	
	case class GetMultipleApplicationPlayerIdsResponse(
	  /** Output only. The requested applications along with the scoped ids for tha player, if that player has an id for the application. If not, the application is not included in the response. */
		playerIds: Option[List[Schema.ApplicationPlayerId]] = None
	)
	
	case class ApplicationPlayerId(
	  /** The application that this player identifier is for. */
		applicationId: Option[String] = None,
	  /** The player identifier for the application. */
		playerId: Option[String] = None
	)
	
	case class PlayerListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The players. */
		items: Option[List[Schema.Player]] = None
	)
	
	object RevisionCheckResponse {
		enum RevisionStatusEnum extends Enum[RevisionStatusEnum] { case OK, DEPRECATED, INVALID }
	}
	case class RevisionCheckResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#revisionCheckResponse`. */
		kind: Option[String] = None,
	  /** The result of the revision check. */
		revisionStatus: Option[Schema.RevisionCheckResponse.RevisionStatusEnum] = None,
	  /** The version of the API this client revision should use when calling API methods. */
		apiVersion: Option[String] = None
	)
	
	case class PlayerLeaderboardScoreListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerLeaderboardScoreListResponse`. */
		kind: Option[String] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The Player resources for the owner of this score. */
		player: Option[Schema.Player] = None,
	  /** The leaderboard scores. */
		items: Option[List[Schema.PlayerLeaderboardScore]] = None
	)
	
	object PlayerLeaderboardScore {
		enum TimeSpanEnum extends Enum[TimeSpanEnum] { case ALL_TIME, WEEKLY, DAILY }
	}
	case class PlayerLeaderboardScore(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerLeaderboardScore`. */
		kind: Option[String] = None,
	  /** The ID of the leaderboard this score is in. */
		leaderboard_id: Option[String] = None,
	  /** The numerical value of this score. */
		scoreValue: Option[String] = None,
	  /** The formatted value of this score. */
		scoreString: Option[String] = None,
	  /** The public rank of the score in this leaderboard. This object will not be present if the user is not sharing their scores publicly. */
		publicRank: Option[Schema.LeaderboardScoreRank] = None,
	  /** The social rank of the score in this leaderboard. */
		socialRank: Option[Schema.LeaderboardScoreRank] = None,
	  /** The rank of the score in the friends collection for this leaderboard. */
		friendsRank: Option[Schema.LeaderboardScoreRank] = None,
	  /** The time span of this score. */
		timeSpan: Option[Schema.PlayerLeaderboardScore.TimeSpanEnum] = None,
	  /** The timestamp at which this score was recorded, in milliseconds since the epoch in UTC. */
		writeTimestamp: Option[String] = None,
	  /** Additional information about the score. Values must contain no more than 64 URI-safe characters as defined by section 2.3 of RFC 3986. */
		scoreTag: Option[String] = None
	)
	
	case class LeaderboardScoreRank(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#leaderboardScoreRank`. */
		kind: Option[String] = None,
	  /** The rank in the leaderboard. */
		rank: Option[String] = None,
	  /** The rank in the leaderboard as a string. */
		formattedRank: Option[String] = None,
	  /** The number of scores in the leaderboard. */
		numScores: Option[String] = None,
	  /** The number of scores in the leaderboard as a string. */
		formattedNumScores: Option[String] = None
	)
	
	case class LeaderboardScores(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#leaderboardScores`. */
		kind: Option[String] = None,
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The pagination token for the previous page of results. */
		prevPageToken: Option[String] = None,
	  /** The total number of scores in the leaderboard. */
		numScores: Option[String] = None,
	  /** The score of the requesting player on the leaderboard. The player's score may appear both here and in the list of scores above. If you are viewing a public leaderboard and the player is not sharing their gameplay information publicly, the `scoreRank`and `formattedScoreRank` values will not be present. */
		playerScore: Option[Schema.LeaderboardEntry] = None,
	  /** The scores in the leaderboard. */
		items: Option[List[Schema.LeaderboardEntry]] = None
	)
	
	object LeaderboardEntry {
		enum TimeSpanEnum extends Enum[TimeSpanEnum] { case ALL_TIME, WEEKLY, DAILY }
	}
	case class LeaderboardEntry(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#leaderboardEntry`. */
		kind: Option[String] = None,
	  /** The player who holds this score. */
		player: Option[Schema.Player] = None,
	  /** The rank of this score for this leaderboard. */
		scoreRank: Option[String] = None,
	  /** The localized string for the rank of this score for this leaderboard. */
		formattedScoreRank: Option[String] = None,
	  /** The numerical value of this score. */
		scoreValue: Option[String] = None,
	  /** The localized string for the numerical value of this score. */
		formattedScore: Option[String] = None,
	  /** The time span of this high score. */
		timeSpan: Option[Schema.LeaderboardEntry.TimeSpanEnum] = None,
	  /** The timestamp at which this score was recorded, in milliseconds since the epoch in UTC. */
		writeTimestampMillis: Option[String] = None,
	  /** Additional information about the score. Values must contain no more than 64 URI-safe characters as defined by section 2.3 of RFC 3986. */
		scoreTag: Option[String] = None
	)
	
	object PlayerScoreResponse {
		enum BeatenScoreTimeSpansEnum extends Enum[BeatenScoreTimeSpansEnum] { case ALL_TIME, WEEKLY, DAILY }
	}
	case class PlayerScoreResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerScoreResponse`. */
		kind: Option[String] = None,
	  /** The time spans where the submitted score is better than the existing score for that time span. */
		beatenScoreTimeSpans: Option[List[Schema.PlayerScoreResponse.BeatenScoreTimeSpansEnum]] = None,
	  /** The scores in time spans that have not been beaten. As an example, the submitted score may be better than the player's `DAILY` score, but not better than the player's scores for the `WEEKLY` or `ALL_TIME` time spans. */
		unbeatenScores: Option[List[Schema.PlayerScore]] = None,
	  /** The formatted value of the submitted score. */
		formattedScore: Option[String] = None,
	  /** The leaderboard ID that this score was submitted to. */
		leaderboardId: Option[String] = None,
	  /** Additional information about this score. Values will contain no more than 64 URI-safe characters as defined by section 2.3 of RFC 3986. */
		scoreTag: Option[String] = None
	)
	
	object PlayerScore {
		enum TimeSpanEnum extends Enum[TimeSpanEnum] { case ALL_TIME, WEEKLY, DAILY }
	}
	case class PlayerScore(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerScore`. */
		kind: Option[String] = None,
	  /** The time span for this player score. */
		timeSpan: Option[Schema.PlayerScore.TimeSpanEnum] = None,
	  /** The numerical value for this player score. */
		score: Option[String] = None,
	  /** The formatted score for this player score. */
		formattedScore: Option[String] = None,
	  /** Additional information about this score. Values will contain no more than 64 URI-safe characters as defined by section 2.3 of RFC 3986. */
		scoreTag: Option[String] = None
	)
	
	case class PlayerScoreSubmissionList(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerScoreSubmissionList`. */
		kind: Option[String] = None,
	  /** The score submissions. */
		scores: Option[List[Schema.ScoreSubmission]] = None
	)
	
	case class ScoreSubmission(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#scoreSubmission`. */
		kind: Option[String] = None,
	  /** The leaderboard this score is being submitted to. */
		leaderboardId: Option[String] = None,
	  /** The new score being submitted. */
		score: Option[String] = None,
	  /** Additional information about this score. Values will contain no more than 64 URI-safe characters as defined by section 2.3 of RFC 3986. */
		scoreTag: Option[String] = None,
	  /** Signature Values will contain URI-safe characters as defined by section 2.3 of RFC 3986. */
		signature: Option[String] = None
	)
	
	case class PlayerScoreListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#playerScoreListResponse`. */
		kind: Option[String] = None,
	  /** The score submissions statuses. */
		submittedScores: Option[List[Schema.PlayerScoreResponse]] = None
	)
	
	object Snapshot {
		enum TypeEnum extends Enum[TypeEnum] { case SAVE_GAME }
	}
	case class Snapshot(
	  /** The ID of the snapshot. */
		id: Option[String] = None,
	  /** The ID of the file underlying this snapshot in the Drive API. Only present if the snapshot is a view on a Drive file and the file is owned by the caller. */
		driveId: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#snapshot`. */
		kind: Option[String] = None,
	  /** The type of this snapshot. */
		`type`: Option[Schema.Snapshot.TypeEnum] = None,
	  /** The title of this snapshot. */
		title: Option[String] = None,
	  /** The description of this snapshot. */
		description: Option[String] = None,
	  /** The timestamp (in millis since Unix epoch) of the last modification to this snapshot. */
		lastModifiedMillis: Option[String] = None,
	  /** The duration associated with this snapshot, in millis. */
		durationMillis: Option[String] = None,
	  /** The cover image of this snapshot. May be absent if there is no image. */
		coverImage: Option[Schema.SnapshotImage] = None,
	  /** The unique name provided when the snapshot was created. */
		uniqueName: Option[String] = None,
	  /** The progress value (64-bit integer set by developer) associated with this snapshot. */
		progressValue: Option[String] = None
	)
	
	case class SnapshotImage(
	  /** The width of the image. */
		width: Option[Int] = None,
	  /** The height of the image. */
		height: Option[Int] = None,
	  /** The MIME type of the image. */
		mime_type: Option[String] = None,
	  /** The URL of the image. This URL may be invalidated at any time and should not be cached. */
		url: Option[String] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#snapshotImage`. */
		kind: Option[String] = None
	)
	
	case class SnapshotListResponse(
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#snapshotListResponse`. */
		kind: Option[String] = None,
	  /** Token corresponding to the next page of results. If there are no more results, the token is omitted. */
		nextPageToken: Option[String] = None,
	  /** The snapshots. */
		items: Option[List[Schema.Snapshot]] = None
	)
	
	case class StatsResponse(
	  /** The probability of the player not returning to play the game in the next day. E.g., 0, 0.1, 0.5, ..., 1.0. Not populated if there is not enough information. */
		churn_probability: Option[BigDecimal] = None,
	  /** Number of in-app purchases made by the player in this game. E.g., 0, 1, 5, 10, ... . Not populated if there is not enough information. */
		num_purchases: Option[Int] = None,
	  /** The approximate spend percentile of the player in this game. E.g., 0, 0.25, 0.5, 0.75. Not populated if there is not enough information. */
		spend_percentile: Option[BigDecimal] = None,
	  /** Number of days since the player last played this game. E.g., 0, 1, 5, 10, ... . Not populated if there is not enough information. */
		days_since_last_played: Option[Int] = None,
	  /** The approximate number of sessions of the player within the last 28 days, where a session begins when the player is connected to Play Games Services and ends when they are disconnected. E.g., 0, 1, 5, 10, ... . Not populated if there is not enough information. */
		num_sessions: Option[Int] = None,
	  /** The approximation of the sessions percentile of the player within the last 30 days, where a session begins when the player is connected to Play Games Services and ends when they are disconnected. E.g., 0, 0.25, 0.5, 0.75. Not populated if there is not enough information. */
		num_sessions_percentile: Option[BigDecimal] = None,
	  /** Average session length in minutes of the player. E.g., 1, 30, 60, ... . Not populated if there is not enough information. */
		avg_session_length_minutes: Option[BigDecimal] = None,
	  /** The probability of the player going to spend the game in the next seven days. E.g., 0, 0.25, 0.50, 0.75. Not populated if there is not enough information. */
		spend_probability: Option[BigDecimal] = None,
	  /** The probability of the player going to spend beyond a threshold amount of money. E.g., 0, 0.25, 0.50, 0.75. Not populated if there is not enough information. */
		high_spender_probability: Option[BigDecimal] = None,
	  /** The predicted amount of money that the player going to spend in the next 28 days. E.g., 1, 30, 60, ... . Not populated if there is not enough information. */
		total_spend_next_28_days: Option[BigDecimal] = None,
	  /** Uniquely identifies the type of this resource. Value is always the fixed string `games#statsResponse`. */
		kind: Option[String] = None
	)
	
	object LinkPersonaRequest {
		enum CardinalityConstraintEnum extends Enum[CardinalityConstraintEnum] { case ONE_PERSONA_TO_ONE_PLAYER }
		enum ConflictingLinksResolutionPolicyEnum extends Enum[ConflictingLinksResolutionPolicyEnum] { case KEEP_EXISTING_LINKS, CREATE_NEW_LINK }
	}
	case class LinkPersonaRequest(
	  /** Required. Opaque server-generated string that encodes all the necessary information to identify the PGS player / Google user and application. */
		sessionId: Option[String] = None,
	  /** Required. Stable identifier of the in-game account. Please refrain from re-using the same persona for different games. */
		persona: Option[String] = None,
	  /** Required. Value of the token to create. Opaque to Play Games and assumed to be non-stable (encrypted with key rotation). */
		token: Option[String] = None,
	  /** Required. Cardinality constraint to observe when linking a persona to a player in the scope of a game. */
		cardinalityConstraint: Option[Schema.LinkPersonaRequest.CardinalityConstraintEnum] = None,
	  /** Required. Resolution policy to apply when the linking of a persona to a player would result in violating the specified cardinality constraint. */
		conflictingLinksResolutionPolicy: Option[Schema.LinkPersonaRequest.ConflictingLinksResolutionPolicyEnum] = None,
	  /** Input only. Optional expiration time. */
		expireTime: Option[String] = None,
	  /** Input only. Optional time-to-live. */
		ttl: Option[String] = None
	)
	
	object LinkPersonaResponse {
		enum StateEnum extends Enum[StateEnum] { case LINK_CREATED, PERSONA_OR_PLAYER_ALREADY_LINKED }
	}
	case class LinkPersonaResponse(
	  /** Output only. State of a persona linking attempt. */
		state: Option[Schema.LinkPersonaResponse.StateEnum] = None
	)
	
	case class RetrievePlayerTokensResponse(
	  /** Required. Recall tokens associated with the requested PGS Player principal */
		tokens: Option[List[Schema.RecallToken]] = None
	)
	
	case class RecallToken(
	  /** Required. Value of the Recall token as it is provided by the client via LinkPersona RPC */
		token: Option[String] = None,
	  /** Required. Whether the persona identified by the token is linked to multiple PGS Players */
		multiPlayerPersona: Option[Boolean] = None,
	  /** Optional. Optional expiration time of the token */
		expireTime: Option[String] = None
	)
	
	case class RetrieveDeveloperGamesLastPlayerTokenResponse(
	  /** The recall token associated with the requested PGS Player principal. It can be unset if there is no recall token associated with the requested principal. */
		gamePlayerToken: Option[Schema.GamePlayerToken] = None
	)
	
	case class GamePlayerToken(
	  /** The application that this player identifier is for. */
		applicationId: Option[String] = None,
	  /** Recall token data. */
		recallToken: Option[Schema.RecallToken] = None
	)
	
	case class RetrieveGamesPlayerTokensResponse(
	  /** The requested applications along with the recall tokens for the player. If the player does not have recall tokens for an application, that application is not included in the response. */
		gamePlayerTokens: Option[List[Schema.GamePlayerToken]] = None
	)
	
	case class UnlinkPersonaRequest(
	  /** Required. Opaque server-generated string that encodes all the necessary information to identify the PGS player / Google user and application. */
		sessionId: Option[String] = None,
	  /** Value of the Recall token as it was provided by the client in LinkPersona RPC */
		token: Option[String] = None,
	  /** Value of the 'persona' field as it was provided by the client in LinkPersona RPC */
		persona: Option[String] = None
	)
	
	case class UnlinkPersonaResponse(
	  /** Required. Whether a Recall token specified by the request was deleted. Can be 'false' when there were no Recall tokens satisfied the criteria from the request. */
		unlinked: Option[Boolean] = None
	)
	
	case class ResetPersonaRequest(
	  /** Value of the 'persona' field as it was provided by the client in LinkPersona RPC */
		persona: Option[String] = None
	)
	
	case class ResetPersonaResponse(
	  /** Required. Whether any tokens were unlinked as a result of this request. */
		unlinked: Option[Boolean] = None
	)
	
	case class GeneratePlayGroupingApiTokenResponse(
	  /** Token for accessing the Play Grouping API. */
		token: Option[Schema.PlayGroupingApiToken] = None
	)
	
	case class PlayGroupingApiToken(
	  /** Value of the token. */
		tokenValue: Option[String] = None
	)
	
	case class GenerateRecallPlayGroupingApiTokenResponse(
	  /** Token for accessing the Play Grouping API. */
		token: Option[Schema.PlayGroupingApiToken] = None
	)
}
