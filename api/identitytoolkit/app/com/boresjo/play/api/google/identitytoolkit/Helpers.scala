package com.boresjo.play.api.google.identitytoolkit

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaUserInfo: Conversion[List[Schema.UserInfo], Option[List[Schema.UserInfo]]] = (fun: List[Schema.UserInfo]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaEmailTemplate: Conversion[Schema.EmailTemplate, Option[Schema.EmailTemplate]] = (fun: Schema.EmailTemplate) => Option(fun)
		given putListSchemaIdpConfig: Conversion[List[Schema.IdpConfig], Option[List[Schema.IdpConfig]]] = (fun: List[Schema.IdpConfig]) => Option(fun)
		given putListSchemaSetAccountInfoResponseProviderUserInfoItem: Conversion[List[Schema.SetAccountInfoResponse.ProviderUserInfoItem], Option[List[Schema.SetAccountInfoResponse.ProviderUserInfoItem]]] = (fun: List[Schema.SetAccountInfoResponse.ProviderUserInfoItem]) => Option(fun)
		given putListSchemaUploadAccountResponseErrorItem: Conversion[List[Schema.UploadAccountResponse.ErrorItem], Option[List[Schema.UploadAccountResponse.ErrorItem]]] = (fun: List[Schema.UploadAccountResponse.ErrorItem]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaUserInfoProviderUserInfoItem: Conversion[List[Schema.UserInfo.ProviderUserInfoItem], Option[List[Schema.UserInfo.ProviderUserInfoItem]]] = (fun: List[Schema.UserInfo.ProviderUserInfoItem]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
