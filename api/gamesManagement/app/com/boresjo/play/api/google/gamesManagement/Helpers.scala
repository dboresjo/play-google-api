package com.boresjo.play.api.google.gamesManagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaAchievementResetResponse: Conversion[List[Schema.AchievementResetResponse], Option[List[Schema.AchievementResetResponse]]] = (fun: List[Schema.AchievementResetResponse]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaHiddenPlayer: Conversion[List[Schema.HiddenPlayer], Option[List[Schema.HiddenPlayer]]] = (fun: List[Schema.HiddenPlayer]) => Option(fun)
		given putSchemaPlayer: Conversion[Schema.Player, Option[Schema.Player]] = (fun: Schema.Player) => Option(fun)
		given putSchemaProfileSettings: Conversion[Schema.ProfileSettings, Option[Schema.ProfileSettings]] = (fun: Schema.ProfileSettings) => Option(fun)
		given putSchemaPlayerNameItem: Conversion[Schema.Player.NameItem, Option[Schema.Player.NameItem]] = (fun: Schema.Player.NameItem) => Option(fun)
		given putSchemaGamesPlayerExperienceInfoResource: Conversion[Schema.GamesPlayerExperienceInfoResource, Option[Schema.GamesPlayerExperienceInfoResource]] = (fun: Schema.GamesPlayerExperienceInfoResource) => Option(fun)
		given putSchemaGamesPlayerLevelResource: Conversion[Schema.GamesPlayerLevelResource, Option[Schema.GamesPlayerLevelResource]] = (fun: Schema.GamesPlayerLevelResource) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaPlayerScoreResetResponse: Conversion[List[Schema.PlayerScoreResetResponse], Option[List[Schema.PlayerScoreResetResponse]]] = (fun: List[Schema.PlayerScoreResetResponse]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
