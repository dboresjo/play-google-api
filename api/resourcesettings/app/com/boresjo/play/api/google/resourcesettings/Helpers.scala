package com.boresjo.play.api.google.resourcesettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1ValueStringSet: Conversion[Schema.GoogleCloudResourcesettingsV1ValueStringSet, Option[Schema.GoogleCloudResourcesettingsV1ValueStringSet]] = (fun: Schema.GoogleCloudResourcesettingsV1ValueStringSet) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1ValueStringMap: Conversion[Schema.GoogleCloudResourcesettingsV1ValueStringMap, Option[Schema.GoogleCloudResourcesettingsV1ValueStringMap]] = (fun: Schema.GoogleCloudResourcesettingsV1ValueStringMap) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1ValueEnumValue: Conversion[Schema.GoogleCloudResourcesettingsV1ValueEnumValue, Option[Schema.GoogleCloudResourcesettingsV1ValueEnumValue]] = (fun: Schema.GoogleCloudResourcesettingsV1ValueEnumValue) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaGoogleCloudResourcesettingsV1Setting: Conversion[List[Schema.GoogleCloudResourcesettingsV1Setting], Option[List[Schema.GoogleCloudResourcesettingsV1Setting]]] = (fun: List[Schema.GoogleCloudResourcesettingsV1Setting]) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1Value: Conversion[Schema.GoogleCloudResourcesettingsV1Value, Option[Schema.GoogleCloudResourcesettingsV1Value]] = (fun: Schema.GoogleCloudResourcesettingsV1Value) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1SettingMetadata: Conversion[Schema.GoogleCloudResourcesettingsV1SettingMetadata, Option[Schema.GoogleCloudResourcesettingsV1SettingMetadata]] = (fun: Schema.GoogleCloudResourcesettingsV1SettingMetadata) => Option(fun)
		given putSchemaGoogleCloudResourcesettingsV1SettingMetadataDataTypeEnum: Conversion[Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum, Option[Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum]] = (fun: Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
