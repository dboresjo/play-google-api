package com.boresjo.play.api.google.gamesManagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAchievementResetResponse: Format[Schema.AchievementResetResponse] = Json.format[Schema.AchievementResetResponse]
	given fmtAchievementResetAllResponse: Format[Schema.AchievementResetAllResponse] = Json.format[Schema.AchievementResetAllResponse]
	given fmtAchievementResetMultipleForAllRequest: Format[Schema.AchievementResetMultipleForAllRequest] = Json.format[Schema.AchievementResetMultipleForAllRequest]
	given fmtEventsResetMultipleForAllRequest: Format[Schema.EventsResetMultipleForAllRequest] = Json.format[Schema.EventsResetMultipleForAllRequest]
	given fmtHiddenPlayerList: Format[Schema.HiddenPlayerList] = Json.format[Schema.HiddenPlayerList]
	given fmtHiddenPlayer: Format[Schema.HiddenPlayer] = Json.format[Schema.HiddenPlayer]
	given fmtPlayer: Format[Schema.Player] = Json.format[Schema.Player]
	given fmtProfileSettings: Format[Schema.ProfileSettings] = Json.format[Schema.ProfileSettings]
	given fmtPlayerNameItem: Format[Schema.Player.NameItem] = Json.format[Schema.Player.NameItem]
	given fmtGamesPlayerExperienceInfoResource: Format[Schema.GamesPlayerExperienceInfoResource] = Json.format[Schema.GamesPlayerExperienceInfoResource]
	given fmtGamesPlayerLevelResource: Format[Schema.GamesPlayerLevelResource] = Json.format[Schema.GamesPlayerLevelResource]
	given fmtPlayerScoreResetResponse: Format[Schema.PlayerScoreResetResponse] = Json.format[Schema.PlayerScoreResetResponse]
	given fmtPlayerScoreResetAllResponse: Format[Schema.PlayerScoreResetAllResponse] = Json.format[Schema.PlayerScoreResetAllResponse]
	given fmtScoresResetMultipleForAllRequest: Format[Schema.ScoresResetMultipleForAllRequest] = Json.format[Schema.ScoresResetMultipleForAllRequest]
}
