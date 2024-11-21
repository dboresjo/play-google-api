package com.boresjo.play.api.google.adsense

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaTimeZone: Conversion[Schema.TimeZone, Option[Schema.TimeZone]] = (fun: Schema.TimeZone) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaAccountStateEnum: Conversion[Schema.Account.StateEnum, Option[Schema.Account.StateEnum]] = (fun: Schema.Account.StateEnum) => Option(fun)
		given putListSchemaAccount: Conversion[List[Schema.Account], Option[List[Schema.Account]]] = (fun: List[Schema.Account]) => Option(fun)
		given putListSchemaAdClient: Conversion[List[Schema.AdClient], Option[List[Schema.AdClient]]] = (fun: List[Schema.AdClient]) => Option(fun)
		given putSchemaAdClientStateEnum: Conversion[Schema.AdClient.StateEnum, Option[Schema.AdClient.StateEnum]] = (fun: Schema.AdClient.StateEnum) => Option(fun)
		given putSchemaAdUnitStateEnum: Conversion[Schema.AdUnit.StateEnum, Option[Schema.AdUnit.StateEnum]] = (fun: Schema.AdUnit.StateEnum) => Option(fun)
		given putSchemaContentAdsSettings: Conversion[Schema.ContentAdsSettings, Option[Schema.ContentAdsSettings]] = (fun: Schema.ContentAdsSettings) => Option(fun)
		given putSchemaContentAdsSettingsTypeEnum: Conversion[Schema.ContentAdsSettings.TypeEnum, Option[Schema.ContentAdsSettings.TypeEnum]] = (fun: Schema.ContentAdsSettings.TypeEnum) => Option(fun)
		given putListSchemaAdUnit: Conversion[List[Schema.AdUnit], Option[List[Schema.AdUnit]]] = (fun: List[Schema.AdUnit]) => Option(fun)
		given putListSchemaAlert: Conversion[List[Schema.Alert], Option[List[Schema.Alert]]] = (fun: List[Schema.Alert]) => Option(fun)
		given putSchemaAlertSeverityEnum: Conversion[Schema.Alert.SeverityEnum, Option[Schema.Alert.SeverityEnum]] = (fun: Schema.Alert.SeverityEnum) => Option(fun)
		given putListSchemaCustomChannel: Conversion[List[Schema.CustomChannel], Option[List[Schema.CustomChannel]]] = (fun: List[Schema.CustomChannel]) => Option(fun)
		given putListSchemaPayment: Conversion[List[Schema.Payment], Option[List[Schema.Payment]]] = (fun: List[Schema.Payment]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaPolicyIssueEntityTypeEnum: Conversion[Schema.PolicyIssue.EntityTypeEnum, Option[Schema.PolicyIssue.EntityTypeEnum]] = (fun: Schema.PolicyIssue.EntityTypeEnum) => Option(fun)
		given putListSchemaPolicyTopic: Conversion[List[Schema.PolicyTopic], Option[List[Schema.PolicyTopic]]] = (fun: List[Schema.PolicyTopic]) => Option(fun)
		given putSchemaPolicyIssueActionEnum: Conversion[Schema.PolicyIssue.ActionEnum, Option[Schema.PolicyIssue.ActionEnum]] = (fun: Schema.PolicyIssue.ActionEnum) => Option(fun)
		given putListSchemaPolicyIssue: Conversion[List[Schema.PolicyIssue], Option[List[Schema.PolicyIssue]]] = (fun: List[Schema.PolicyIssue]) => Option(fun)
		given putListSchemaHeader: Conversion[List[Schema.Header], Option[List[Schema.Header]]] = (fun: List[Schema.Header]) => Option(fun)
		given putListSchemaRow: Conversion[List[Schema.Row], Option[List[Schema.Row]]] = (fun: List[Schema.Row]) => Option(fun)
		given putSchemaRow: Conversion[Schema.Row, Option[Schema.Row]] = (fun: Schema.Row) => Option(fun)
		given putSchemaHeaderTypeEnum: Conversion[Schema.Header.TypeEnum, Option[Schema.Header.TypeEnum]] = (fun: Schema.Header.TypeEnum) => Option(fun)
		given putListSchemaCell: Conversion[List[Schema.Cell], Option[List[Schema.Cell]]] = (fun: List[Schema.Cell]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaSavedReport: Conversion[List[Schema.SavedReport], Option[List[Schema.SavedReport]]] = (fun: List[Schema.SavedReport]) => Option(fun)
		given putSchemaSiteStateEnum: Conversion[Schema.Site.StateEnum, Option[Schema.Site.StateEnum]] = (fun: Schema.Site.StateEnum) => Option(fun)
		given putListSchemaSite: Conversion[List[Schema.Site], Option[List[Schema.Site]]] = (fun: List[Schema.Site]) => Option(fun)
		given putListSchemaUrlChannel: Conversion[List[Schema.UrlChannel], Option[List[Schema.UrlChannel]]] = (fun: List[Schema.UrlChannel]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
