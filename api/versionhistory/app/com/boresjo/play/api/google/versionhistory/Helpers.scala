package com.boresjo.play.api.google.versionhistory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaPlatform: Conversion[List[Schema.Platform], Option[List[Schema.Platform]]] = (fun: List[Schema.Platform]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPlatformPlatformTypeEnum: Conversion[Schema.Platform.PlatformTypeEnum, Option[Schema.Platform.PlatformTypeEnum]] = (fun: Schema.Platform.PlatformTypeEnum) => Option(fun)
		given putListSchemaChannel: Conversion[List[Schema.Channel], Option[List[Schema.Channel]]] = (fun: List[Schema.Channel]) => Option(fun)
		given putSchemaChannelChannelTypeEnum: Conversion[Schema.Channel.ChannelTypeEnum, Option[Schema.Channel.ChannelTypeEnum]] = (fun: Schema.Channel.ChannelTypeEnum) => Option(fun)
		given putListSchemaVersion: Conversion[List[Schema.Version], Option[List[Schema.Version]]] = (fun: List[Schema.Version]) => Option(fun)
		given putListSchemaRelease: Conversion[List[Schema.Release], Option[List[Schema.Release]]] = (fun: List[Schema.Release]) => Option(fun)
		given putSchemaInterval: Conversion[Schema.Interval, Option[Schema.Interval]] = (fun: Schema.Interval) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
