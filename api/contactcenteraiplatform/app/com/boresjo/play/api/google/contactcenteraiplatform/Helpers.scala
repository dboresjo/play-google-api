package com.boresjo.play.api.google.contactcenteraiplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaContactCenter: Conversion[List[Schema.ContactCenter], Option[List[Schema.ContactCenter]]] = (fun: List[Schema.ContactCenter]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaURIs: Conversion[Schema.URIs, Option[Schema.URIs]] = (fun: Schema.URIs) => Option(fun)
		given putSchemaContactCenterStateEnum: Conversion[Schema.ContactCenter.StateEnum, Option[Schema.ContactCenter.StateEnum]] = (fun: Schema.ContactCenter.StateEnum) => Option(fun)
		given putSchemaInstanceConfig: Conversion[Schema.InstanceConfig, Option[Schema.InstanceConfig]] = (fun: Schema.InstanceConfig) => Option(fun)
		given putSchemaSAMLParams: Conversion[Schema.SAMLParams, Option[Schema.SAMLParams]] = (fun: Schema.SAMLParams) => Option(fun)
		given putSchemaAdminUser: Conversion[Schema.AdminUser, Option[Schema.AdminUser]] = (fun: Schema.AdminUser) => Option(fun)
		given putSchemaPrivateAccess: Conversion[Schema.PrivateAccess, Option[Schema.PrivateAccess]] = (fun: Schema.PrivateAccess) => Option(fun)
		given putSchemaEarly: Conversion[Schema.Early, Option[Schema.Early]] = (fun: Schema.Early) => Option(fun)
		given putSchemaNormal: Conversion[Schema.Normal, Option[Schema.Normal]] = (fun: Schema.Normal) => Option(fun)
		given putSchemaCritical: Conversion[Schema.Critical, Option[Schema.Critical]] = (fun: Schema.Critical) => Option(fun)
		given putSchemaInstanceConfigInstanceSizeEnum: Conversion[Schema.InstanceConfig.InstanceSizeEnum, Option[Schema.InstanceConfig.InstanceSizeEnum]] = (fun: Schema.InstanceConfig.InstanceSizeEnum) => Option(fun)
		given putListSchemaSAMLParamsAuthenticationContextsEnum: Conversion[List[Schema.SAMLParams.AuthenticationContextsEnum], Option[List[Schema.SAMLParams.AuthenticationContextsEnum]]] = (fun: List[Schema.SAMLParams.AuthenticationContextsEnum]) => Option(fun)
		given putListSchemaComponent: Conversion[List[Schema.Component], Option[List[Schema.Component]]] = (fun: List[Schema.Component]) => Option(fun)
		given putSchemaPscSetting: Conversion[Schema.PscSetting, Option[Schema.PscSetting]] = (fun: Schema.PscSetting) => Option(fun)
		given putListSchemaWeeklySchedule: Conversion[List[Schema.WeeklySchedule], Option[List[Schema.WeeklySchedule]]] = (fun: List[Schema.WeeklySchedule]) => Option(fun)
		given putListSchemaWeeklyScheduleDaysEnum: Conversion[List[Schema.WeeklySchedule.DaysEnum], Option[List[Schema.WeeklySchedule.DaysEnum]]] = (fun: List[Schema.WeeklySchedule.DaysEnum]) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putListSchemaQuota: Conversion[List[Schema.Quota], Option[List[Schema.Quota]]] = (fun: List[Schema.Quota]) => Option(fun)
		given putSchemaQuotaContactCenterInstanceSizeEnum: Conversion[Schema.Quota.ContactCenterInstanceSizeEnum, Option[Schema.Quota.ContactCenterInstanceSizeEnum]] = (fun: Schema.Quota.ContactCenterInstanceSizeEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaContactCenter: Conversion[Schema.ContactCenter, Option[Schema.ContactCenter]] = (fun: Schema.ContactCenter) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
