package com.boresjo.play.api.google.games

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaAchievementDefinition: Conversion[List[Schema.AchievementDefinition], Option[List[Schema.AchievementDefinition]]] = (fun: List[Schema.AchievementDefinition]) => Option(fun)
		given putSchemaAchievementDefinitionAchievementTypeEnum: Conversion[Schema.AchievementDefinition.AchievementTypeEnum, Option[Schema.AchievementDefinition.AchievementTypeEnum]] = (fun: Schema.AchievementDefinition.AchievementTypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAchievementDefinitionInitialStateEnum: Conversion[Schema.AchievementDefinition.InitialStateEnum, Option[Schema.AchievementDefinition.InitialStateEnum]] = (fun: Schema.AchievementDefinition.InitialStateEnum) => Option(fun)
		given putListSchemaPlayerAchievement: Conversion[List[Schema.PlayerAchievement], Option[List[Schema.PlayerAchievement]]] = (fun: List[Schema.PlayerAchievement]) => Option(fun)
		given putSchemaPlayerAchievementAchievementStateEnum: Conversion[Schema.PlayerAchievement.AchievementStateEnum, Option[Schema.PlayerAchievement.AchievementStateEnum]] = (fun: Schema.PlayerAchievement.AchievementStateEnum) => Option(fun)
		given putSchemaAchievementRevealResponseCurrentStateEnum: Conversion[Schema.AchievementRevealResponse.CurrentStateEnum, Option[Schema.AchievementRevealResponse.CurrentStateEnum]] = (fun: Schema.AchievementRevealResponse.CurrentStateEnum) => Option(fun)
		given putListSchemaAchievementUpdateRequest: Conversion[List[Schema.AchievementUpdateRequest], Option[List[Schema.AchievementUpdateRequest]]] = (fun: List[Schema.AchievementUpdateRequest]) => Option(fun)
		given putSchemaAchievementUpdateRequestUpdateTypeEnum: Conversion[Schema.AchievementUpdateRequest.UpdateTypeEnum, Option[Schema.AchievementUpdateRequest.UpdateTypeEnum]] = (fun: Schema.AchievementUpdateRequest.UpdateTypeEnum) => Option(fun)
		given putSchemaGamesAchievementIncrement: Conversion[Schema.GamesAchievementIncrement, Option[Schema.GamesAchievementIncrement]] = (fun: Schema.GamesAchievementIncrement) => Option(fun)
		given putSchemaGamesAchievementSetStepsAtLeast: Conversion[Schema.GamesAchievementSetStepsAtLeast, Option[Schema.GamesAchievementSetStepsAtLeast]] = (fun: Schema.GamesAchievementSetStepsAtLeast) => Option(fun)
		given putListSchemaAchievementUpdateResponse: Conversion[List[Schema.AchievementUpdateResponse], Option[List[Schema.AchievementUpdateResponse]]] = (fun: List[Schema.AchievementUpdateResponse]) => Option(fun)
		given putSchemaAchievementUpdateResponseCurrentStateEnum: Conversion[Schema.AchievementUpdateResponse.CurrentStateEnum, Option[Schema.AchievementUpdateResponse.CurrentStateEnum]] = (fun: Schema.AchievementUpdateResponse.CurrentStateEnum) => Option(fun)
		given putSchemaApplicationCategory: Conversion[Schema.ApplicationCategory, Option[Schema.ApplicationCategory]] = (fun: Schema.ApplicationCategory) => Option(fun)
		given putListSchemaImageAsset: Conversion[List[Schema.ImageAsset], Option[List[Schema.ImageAsset]]] = (fun: List[Schema.ImageAsset]) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListSchemaApplicationEnabledFeaturesEnum: Conversion[List[Schema.Application.EnabledFeaturesEnum], Option[List[Schema.Application.EnabledFeaturesEnum]]] = (fun: List[Schema.Application.EnabledFeaturesEnum]) => Option(fun)
		given putSchemaInstancePlatformTypeEnum: Conversion[Schema.Instance.PlatformTypeEnum, Option[Schema.Instance.PlatformTypeEnum]] = (fun: Schema.Instance.PlatformTypeEnum) => Option(fun)
		given putSchemaInstanceAndroidDetails: Conversion[Schema.InstanceAndroidDetails, Option[Schema.InstanceAndroidDetails]] = (fun: Schema.InstanceAndroidDetails) => Option(fun)
		given putSchemaInstanceIosDetails: Conversion[Schema.InstanceIosDetails, Option[Schema.InstanceIosDetails]] = (fun: Schema.InstanceIosDetails) => Option(fun)
		given putSchemaInstanceWebDetails: Conversion[Schema.InstanceWebDetails, Option[Schema.InstanceWebDetails]] = (fun: Schema.InstanceWebDetails) => Option(fun)
		given putListSchemaPlayerEvent: Conversion[List[Schema.PlayerEvent], Option[List[Schema.PlayerEvent]]] = (fun: List[Schema.PlayerEvent]) => Option(fun)
		given putListSchemaEventDefinition: Conversion[List[Schema.EventDefinition], Option[List[Schema.EventDefinition]]] = (fun: List[Schema.EventDefinition]) => Option(fun)
		given putSchemaEventDefinitionVisibilityEnum: Conversion[Schema.EventDefinition.VisibilityEnum, Option[Schema.EventDefinition.VisibilityEnum]] = (fun: Schema.EventDefinition.VisibilityEnum) => Option(fun)
		given putListSchemaEventChild: Conversion[List[Schema.EventChild], Option[List[Schema.EventChild]]] = (fun: List[Schema.EventChild]) => Option(fun)
		given putListSchemaEventPeriodUpdate: Conversion[List[Schema.EventPeriodUpdate], Option[List[Schema.EventPeriodUpdate]]] = (fun: List[Schema.EventPeriodUpdate]) => Option(fun)
		given putSchemaEventPeriodRange: Conversion[Schema.EventPeriodRange, Option[Schema.EventPeriodRange]] = (fun: Schema.EventPeriodRange) => Option(fun)
		given putListSchemaEventUpdateRequest: Conversion[List[Schema.EventUpdateRequest], Option[List[Schema.EventUpdateRequest]]] = (fun: List[Schema.EventUpdateRequest]) => Option(fun)
		given putListSchemaEventBatchRecordFailure: Conversion[List[Schema.EventBatchRecordFailure], Option[List[Schema.EventBatchRecordFailure]]] = (fun: List[Schema.EventBatchRecordFailure]) => Option(fun)
		given putListSchemaEventRecordFailure: Conversion[List[Schema.EventRecordFailure], Option[List[Schema.EventRecordFailure]]] = (fun: List[Schema.EventRecordFailure]) => Option(fun)
		given putSchemaEventBatchRecordFailureFailureCauseEnum: Conversion[Schema.EventBatchRecordFailure.FailureCauseEnum, Option[Schema.EventBatchRecordFailure.FailureCauseEnum]] = (fun: Schema.EventBatchRecordFailure.FailureCauseEnum) => Option(fun)
		given putSchemaEventRecordFailureFailureCauseEnum: Conversion[Schema.EventRecordFailure.FailureCauseEnum, Option[Schema.EventRecordFailure.FailureCauseEnum]] = (fun: Schema.EventRecordFailure.FailureCauseEnum) => Option(fun)
		given putSchemaLeaderboardOrderEnum: Conversion[Schema.Leaderboard.OrderEnum, Option[Schema.Leaderboard.OrderEnum]] = (fun: Schema.Leaderboard.OrderEnum) => Option(fun)
		given putListSchemaLeaderboard: Conversion[List[Schema.Leaderboard], Option[List[Schema.Leaderboard]]] = (fun: List[Schema.Leaderboard]) => Option(fun)
		given putListSchemaPlayerLevel: Conversion[List[Schema.PlayerLevel], Option[List[Schema.PlayerLevel]]] = (fun: List[Schema.PlayerLevel]) => Option(fun)
		given putListSchemaCategory: Conversion[List[Schema.Category], Option[List[Schema.Category]]] = (fun: List[Schema.Category]) => Option(fun)
		given putSchemaProfileSettings: Conversion[Schema.ProfileSettings, Option[Schema.ProfileSettings]] = (fun: Schema.ProfileSettings) => Option(fun)
		given putSchemaPlayerNameItem: Conversion[Schema.Player.NameItem, Option[Schema.Player.NameItem]] = (fun: Schema.Player.NameItem) => Option(fun)
		given putSchemaPlayerExperienceInfo: Conversion[Schema.PlayerExperienceInfo, Option[Schema.PlayerExperienceInfo]] = (fun: Schema.PlayerExperienceInfo) => Option(fun)
		given putSchemaPlayerFriendStatusEnum: Conversion[Schema.Player.FriendStatusEnum, Option[Schema.Player.FriendStatusEnum]] = (fun: Schema.Player.FriendStatusEnum) => Option(fun)
		given putSchemaProfileSettingsFriendsListVisibilityEnum: Conversion[Schema.ProfileSettings.FriendsListVisibilityEnum, Option[Schema.ProfileSettings.FriendsListVisibilityEnum]] = (fun: Schema.ProfileSettings.FriendsListVisibilityEnum) => Option(fun)
		given putSchemaPlayerLevel: Conversion[Schema.PlayerLevel, Option[Schema.PlayerLevel]] = (fun: Schema.PlayerLevel) => Option(fun)
		given putListSchemaApplicationPlayerId: Conversion[List[Schema.ApplicationPlayerId], Option[List[Schema.ApplicationPlayerId]]] = (fun: List[Schema.ApplicationPlayerId]) => Option(fun)
		given putListSchemaPlayer: Conversion[List[Schema.Player], Option[List[Schema.Player]]] = (fun: List[Schema.Player]) => Option(fun)
		given putSchemaRevisionCheckResponseRevisionStatusEnum: Conversion[Schema.RevisionCheckResponse.RevisionStatusEnum, Option[Schema.RevisionCheckResponse.RevisionStatusEnum]] = (fun: Schema.RevisionCheckResponse.RevisionStatusEnum) => Option(fun)
		given putSchemaPlayer: Conversion[Schema.Player, Option[Schema.Player]] = (fun: Schema.Player) => Option(fun)
		given putListSchemaPlayerLeaderboardScore: Conversion[List[Schema.PlayerLeaderboardScore], Option[List[Schema.PlayerLeaderboardScore]]] = (fun: List[Schema.PlayerLeaderboardScore]) => Option(fun)
		given putSchemaLeaderboardScoreRank: Conversion[Schema.LeaderboardScoreRank, Option[Schema.LeaderboardScoreRank]] = (fun: Schema.LeaderboardScoreRank) => Option(fun)
		given putSchemaPlayerLeaderboardScoreTimeSpanEnum: Conversion[Schema.PlayerLeaderboardScore.TimeSpanEnum, Option[Schema.PlayerLeaderboardScore.TimeSpanEnum]] = (fun: Schema.PlayerLeaderboardScore.TimeSpanEnum) => Option(fun)
		given putSchemaLeaderboardEntry: Conversion[Schema.LeaderboardEntry, Option[Schema.LeaderboardEntry]] = (fun: Schema.LeaderboardEntry) => Option(fun)
		given putListSchemaLeaderboardEntry: Conversion[List[Schema.LeaderboardEntry], Option[List[Schema.LeaderboardEntry]]] = (fun: List[Schema.LeaderboardEntry]) => Option(fun)
		given putSchemaLeaderboardEntryTimeSpanEnum: Conversion[Schema.LeaderboardEntry.TimeSpanEnum, Option[Schema.LeaderboardEntry.TimeSpanEnum]] = (fun: Schema.LeaderboardEntry.TimeSpanEnum) => Option(fun)
		given putListSchemaPlayerScoreResponseBeatenScoreTimeSpansEnum: Conversion[List[Schema.PlayerScoreResponse.BeatenScoreTimeSpansEnum], Option[List[Schema.PlayerScoreResponse.BeatenScoreTimeSpansEnum]]] = (fun: List[Schema.PlayerScoreResponse.BeatenScoreTimeSpansEnum]) => Option(fun)
		given putListSchemaPlayerScore: Conversion[List[Schema.PlayerScore], Option[List[Schema.PlayerScore]]] = (fun: List[Schema.PlayerScore]) => Option(fun)
		given putSchemaPlayerScoreTimeSpanEnum: Conversion[Schema.PlayerScore.TimeSpanEnum, Option[Schema.PlayerScore.TimeSpanEnum]] = (fun: Schema.PlayerScore.TimeSpanEnum) => Option(fun)
		given putListSchemaScoreSubmission: Conversion[List[Schema.ScoreSubmission], Option[List[Schema.ScoreSubmission]]] = (fun: List[Schema.ScoreSubmission]) => Option(fun)
		given putListSchemaPlayerScoreResponse: Conversion[List[Schema.PlayerScoreResponse], Option[List[Schema.PlayerScoreResponse]]] = (fun: List[Schema.PlayerScoreResponse]) => Option(fun)
		given putSchemaSnapshotTypeEnum: Conversion[Schema.Snapshot.TypeEnum, Option[Schema.Snapshot.TypeEnum]] = (fun: Schema.Snapshot.TypeEnum) => Option(fun)
		given putSchemaSnapshotImage: Conversion[Schema.SnapshotImage, Option[Schema.SnapshotImage]] = (fun: Schema.SnapshotImage) => Option(fun)
		given putListSchemaSnapshot: Conversion[List[Schema.Snapshot], Option[List[Schema.Snapshot]]] = (fun: List[Schema.Snapshot]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaLinkPersonaRequestCardinalityConstraintEnum: Conversion[Schema.LinkPersonaRequest.CardinalityConstraintEnum, Option[Schema.LinkPersonaRequest.CardinalityConstraintEnum]] = (fun: Schema.LinkPersonaRequest.CardinalityConstraintEnum) => Option(fun)
		given putSchemaLinkPersonaRequestConflictingLinksResolutionPolicyEnum: Conversion[Schema.LinkPersonaRequest.ConflictingLinksResolutionPolicyEnum, Option[Schema.LinkPersonaRequest.ConflictingLinksResolutionPolicyEnum]] = (fun: Schema.LinkPersonaRequest.ConflictingLinksResolutionPolicyEnum) => Option(fun)
		given putSchemaLinkPersonaResponseStateEnum: Conversion[Schema.LinkPersonaResponse.StateEnum, Option[Schema.LinkPersonaResponse.StateEnum]] = (fun: Schema.LinkPersonaResponse.StateEnum) => Option(fun)
		given putListSchemaRecallToken: Conversion[List[Schema.RecallToken], Option[List[Schema.RecallToken]]] = (fun: List[Schema.RecallToken]) => Option(fun)
		given putSchemaGamePlayerToken: Conversion[Schema.GamePlayerToken, Option[Schema.GamePlayerToken]] = (fun: Schema.GamePlayerToken) => Option(fun)
		given putSchemaRecallToken: Conversion[Schema.RecallToken, Option[Schema.RecallToken]] = (fun: Schema.RecallToken) => Option(fun)
		given putListSchemaGamePlayerToken: Conversion[List[Schema.GamePlayerToken], Option[List[Schema.GamePlayerToken]]] = (fun: List[Schema.GamePlayerToken]) => Option(fun)
		given putSchemaPlayGroupingApiToken: Conversion[Schema.PlayGroupingApiToken, Option[Schema.PlayGroupingApiToken]] = (fun: Schema.PlayGroupingApiToken) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
