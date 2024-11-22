package com.boresjo.play.api.google.gamesConfiguration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAchievementConfiguration: Format[Schema.AchievementConfiguration] = Json.format[Schema.AchievementConfiguration]
	given fmtAchievementConfigurationAchievementTypeEnum: Format[Schema.AchievementConfiguration.AchievementTypeEnum] = JsonEnumFormat[Schema.AchievementConfiguration.AchievementTypeEnum]
	given fmtAchievementConfigurationInitialStateEnum: Format[Schema.AchievementConfiguration.InitialStateEnum] = JsonEnumFormat[Schema.AchievementConfiguration.InitialStateEnum]
	given fmtAchievementConfigurationDetail: Format[Schema.AchievementConfigurationDetail] = Json.format[Schema.AchievementConfigurationDetail]
	given fmtLocalizedStringBundle: Format[Schema.LocalizedStringBundle] = Json.format[Schema.LocalizedStringBundle]
	given fmtLocalizedString: Format[Schema.LocalizedString] = Json.format[Schema.LocalizedString]
	given fmtAchievementConfigurationListResponse: Format[Schema.AchievementConfigurationListResponse] = Json.format[Schema.AchievementConfigurationListResponse]
	given fmtLeaderboardConfiguration: Format[Schema.LeaderboardConfiguration] = Json.format[Schema.LeaderboardConfiguration]
	given fmtLeaderboardConfigurationScoreOrderEnum: Format[Schema.LeaderboardConfiguration.ScoreOrderEnum] = JsonEnumFormat[Schema.LeaderboardConfiguration.ScoreOrderEnum]
	given fmtLeaderboardConfigurationDetail: Format[Schema.LeaderboardConfigurationDetail] = Json.format[Schema.LeaderboardConfigurationDetail]
	given fmtGamesNumberFormatConfiguration: Format[Schema.GamesNumberFormatConfiguration] = Json.format[Schema.GamesNumberFormatConfiguration]
	given fmtGamesNumberFormatConfigurationNumberFormatTypeEnum: Format[Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum] = JsonEnumFormat[Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum]
	given fmtGamesNumberAffixConfiguration: Format[Schema.GamesNumberAffixConfiguration] = Json.format[Schema.GamesNumberAffixConfiguration]
	given fmtLeaderboardConfigurationListResponse: Format[Schema.LeaderboardConfigurationListResponse] = Json.format[Schema.LeaderboardConfigurationListResponse]
}
