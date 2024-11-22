package com.boresjo.play.api.google.oslogin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaPosixAccount: Conversion[List[Schema.PosixAccount], Option[List[Schema.PosixAccount]]] = (fun: List[Schema.PosixAccount]) => Option(fun)
		given putMapStringSchemaSshPublicKey: Conversion[Map[String, Schema.SshPublicKey], Option[Map[String, Schema.SshPublicKey]]] = (fun: Map[String, Schema.SshPublicKey]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaPosixAccountOperatingSystemTypeEnum: Conversion[Schema.PosixAccount.OperatingSystemTypeEnum, Option[Schema.PosixAccount.OperatingSystemTypeEnum]] = (fun: Schema.PosixAccount.OperatingSystemTypeEnum) => Option(fun)
		given putSchemaLoginProfile: Conversion[Schema.LoginProfile, Option[Schema.LoginProfile]] = (fun: Schema.LoginProfile) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
