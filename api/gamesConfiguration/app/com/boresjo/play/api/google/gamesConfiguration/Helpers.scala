package com.boresjo.play.api.google.gamesConfiguration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAchievementConfigurationAchievementTypeEnum: Conversion[Schema.AchievementConfiguration.AchievementTypeEnum, Option[Schema.AchievementConfiguration.AchievementTypeEnum]] = (fun: Schema.AchievementConfiguration.AchievementTypeEnum) => Option(fun)
		given putSchemaAchievementConfigurationInitialStateEnum: Conversion[Schema.AchievementConfiguration.InitialStateEnum, Option[Schema.AchievementConfiguration.InitialStateEnum]] = (fun: Schema.AchievementConfiguration.InitialStateEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaAchievementConfigurationDetail: Conversion[Schema.AchievementConfigurationDetail, Option[Schema.AchievementConfigurationDetail]] = (fun: Schema.AchievementConfigurationDetail) => Option(fun)
		given putSchemaLocalizedStringBundle: Conversion[Schema.LocalizedStringBundle, Option[Schema.LocalizedStringBundle]] = (fun: Schema.LocalizedStringBundle) => Option(fun)
		given putListSchemaLocalizedString: Conversion[List[Schema.LocalizedString], Option[List[Schema.LocalizedString]]] = (fun: List[Schema.LocalizedString]) => Option(fun)
		given putListSchemaAchievementConfiguration: Conversion[List[Schema.AchievementConfiguration], Option[List[Schema.AchievementConfiguration]]] = (fun: List[Schema.AchievementConfiguration]) => Option(fun)
		given putSchemaLeaderboardConfigurationScoreOrderEnum: Conversion[Schema.LeaderboardConfiguration.ScoreOrderEnum, Option[Schema.LeaderboardConfiguration.ScoreOrderEnum]] = (fun: Schema.LeaderboardConfiguration.ScoreOrderEnum) => Option(fun)
		given putSchemaLeaderboardConfigurationDetail: Conversion[Schema.LeaderboardConfigurationDetail, Option[Schema.LeaderboardConfigurationDetail]] = (fun: Schema.LeaderboardConfigurationDetail) => Option(fun)
		given putSchemaGamesNumberFormatConfiguration: Conversion[Schema.GamesNumberFormatConfiguration, Option[Schema.GamesNumberFormatConfiguration]] = (fun: Schema.GamesNumberFormatConfiguration) => Option(fun)
		given putSchemaGamesNumberFormatConfigurationNumberFormatTypeEnum: Conversion[Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum, Option[Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum]] = (fun: Schema.GamesNumberFormatConfiguration.NumberFormatTypeEnum) => Option(fun)
		given putSchemaGamesNumberAffixConfiguration: Conversion[Schema.GamesNumberAffixConfiguration, Option[Schema.GamesNumberAffixConfiguration]] = (fun: Schema.GamesNumberAffixConfiguration) => Option(fun)
		given putListSchemaLeaderboardConfiguration: Conversion[List[Schema.LeaderboardConfiguration], Option[List[Schema.LeaderboardConfiguration]]] = (fun: List[Schema.LeaderboardConfiguration]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
