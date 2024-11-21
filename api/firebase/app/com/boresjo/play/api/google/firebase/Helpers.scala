package com.boresjo.play.api.google.firebase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaAndroidAppStateEnum: Conversion[Schema.AndroidApp.StateEnum, Option[Schema.AndroidApp.StateEnum]] = (fun: Schema.AndroidApp.StateEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaAndroidApp: Conversion[List[Schema.AndroidApp], Option[List[Schema.AndroidApp]]] = (fun: List[Schema.AndroidApp]) => Option(fun)
		given putListSchemaShaCertificate: Conversion[List[Schema.ShaCertificate], Option[List[Schema.ShaCertificate]]] = (fun: List[Schema.ShaCertificate]) => Option(fun)
		given putSchemaShaCertificateCertTypeEnum: Conversion[Schema.ShaCertificate.CertTypeEnum, Option[Schema.ShaCertificate.CertTypeEnum]] = (fun: Schema.ShaCertificate.CertTypeEnum) => Option(fun)
		given putSchemaAnalyticsProperty: Conversion[Schema.AnalyticsProperty, Option[Schema.AnalyticsProperty]] = (fun: Schema.AnalyticsProperty) => Option(fun)
		given putListSchemaStreamMapping: Conversion[List[Schema.StreamMapping], Option[List[Schema.StreamMapping]]] = (fun: List[Schema.StreamMapping]) => Option(fun)
		given putSchemaIosAppStateEnum: Conversion[Schema.IosApp.StateEnum, Option[Schema.IosApp.StateEnum]] = (fun: Schema.IosApp.StateEnum) => Option(fun)
		given putListSchemaIosApp: Conversion[List[Schema.IosApp], Option[List[Schema.IosApp]]] = (fun: List[Schema.IosApp]) => Option(fun)
		given putSchemaDefaultResources: Conversion[Schema.DefaultResources, Option[Schema.DefaultResources]] = (fun: Schema.DefaultResources) => Option(fun)
		given putSchemaFirebaseProjectStateEnum: Conversion[Schema.FirebaseProject.StateEnum, Option[Schema.FirebaseProject.StateEnum]] = (fun: Schema.FirebaseProject.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaFirebaseProject: Conversion[List[Schema.FirebaseProject], Option[List[Schema.FirebaseProject]]] = (fun: List[Schema.FirebaseProject]) => Option(fun)
		given putListSchemaProjectInfo: Conversion[List[Schema.ProjectInfo], Option[List[Schema.ProjectInfo]]] = (fun: List[Schema.ProjectInfo]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaLocationTypeEnum: Conversion[Schema.Location.TypeEnum, Option[Schema.Location.TypeEnum]] = (fun: Schema.Location.TypeEnum) => Option(fun)
		given putListSchemaLocationFeaturesEnum: Conversion[List[Schema.Location.FeaturesEnum], Option[List[Schema.Location.FeaturesEnum]]] = (fun: List[Schema.Location.FeaturesEnum]) => Option(fun)
		given putListSchemaFirebaseAppInfo: Conversion[List[Schema.FirebaseAppInfo], Option[List[Schema.FirebaseAppInfo]]] = (fun: List[Schema.FirebaseAppInfo]) => Option(fun)
		given putSchemaFirebaseAppInfoPlatformEnum: Conversion[Schema.FirebaseAppInfo.PlatformEnum, Option[Schema.FirebaseAppInfo.PlatformEnum]] = (fun: Schema.FirebaseAppInfo.PlatformEnum) => Option(fun)
		given putSchemaFirebaseAppInfoStateEnum: Conversion[Schema.FirebaseAppInfo.StateEnum, Option[Schema.FirebaseAppInfo.StateEnum]] = (fun: Schema.FirebaseAppInfo.StateEnum) => Option(fun)
		given putSchemaWebAppStateEnum: Conversion[Schema.WebApp.StateEnum, Option[Schema.WebApp.StateEnum]] = (fun: Schema.WebApp.StateEnum) => Option(fun)
		given putListSchemaWebApp: Conversion[List[Schema.WebApp], Option[List[Schema.WebApp]]] = (fun: List[Schema.WebApp]) => Option(fun)
		given putSchemaMessageSet: Conversion[Schema.MessageSet, Option[Schema.MessageSet]] = (fun: Schema.MessageSet) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
