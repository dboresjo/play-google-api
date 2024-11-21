package com.boresjo.play.api.google.oslogin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSshPublicKey: Format[Schema.SshPublicKey] = Json.format[Schema.SshPublicKey]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtLoginProfile: Format[Schema.LoginProfile] = Json.format[Schema.LoginProfile]
	given fmtPosixAccount: Format[Schema.PosixAccount] = Json.format[Schema.PosixAccount]
	given fmtPosixAccountOperatingSystemTypeEnum: Format[Schema.PosixAccount.OperatingSystemTypeEnum] = JsonEnumFormat[Schema.PosixAccount.OperatingSystemTypeEnum]
	given fmtImportSshPublicKeyResponse: Format[Schema.ImportSshPublicKeyResponse] = Json.format[Schema.ImportSshPublicKeyResponse]
}
