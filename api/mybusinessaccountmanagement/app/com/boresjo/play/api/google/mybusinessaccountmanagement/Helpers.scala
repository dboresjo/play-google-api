package com.boresjo.play.api.google.mybusinessaccountmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaInvitation: Conversion[List[Schema.Invitation], Option[List[Schema.Invitation]]] = (fun: List[Schema.Invitation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAccount: Conversion[Schema.Account, Option[Schema.Account]] = (fun: Schema.Account) => Option(fun)
		given putSchemaTargetLocation: Conversion[Schema.TargetLocation, Option[Schema.TargetLocation]] = (fun: Schema.TargetLocation) => Option(fun)
		given putSchemaInvitationRoleEnum: Conversion[Schema.Invitation.RoleEnum, Option[Schema.Invitation.RoleEnum]] = (fun: Schema.Invitation.RoleEnum) => Option(fun)
		given putSchemaInvitationTargetTypeEnum: Conversion[Schema.Invitation.TargetTypeEnum, Option[Schema.Invitation.TargetTypeEnum]] = (fun: Schema.Invitation.TargetTypeEnum) => Option(fun)
		given putSchemaAccountTypeEnum: Conversion[Schema.Account.TypeEnum, Option[Schema.Account.TypeEnum]] = (fun: Schema.Account.TypeEnum) => Option(fun)
		given putSchemaAccountRoleEnum: Conversion[Schema.Account.RoleEnum, Option[Schema.Account.RoleEnum]] = (fun: Schema.Account.RoleEnum) => Option(fun)
		given putSchemaAccountVerificationStateEnum: Conversion[Schema.Account.VerificationStateEnum, Option[Schema.Account.VerificationStateEnum]] = (fun: Schema.Account.VerificationStateEnum) => Option(fun)
		given putSchemaAccountVettedStateEnum: Conversion[Schema.Account.VettedStateEnum, Option[Schema.Account.VettedStateEnum]] = (fun: Schema.Account.VettedStateEnum) => Option(fun)
		given putSchemaAccountPermissionLevelEnum: Conversion[Schema.Account.PermissionLevelEnum, Option[Schema.Account.PermissionLevelEnum]] = (fun: Schema.Account.PermissionLevelEnum) => Option(fun)
		given putSchemaOrganizationInfo: Conversion[Schema.OrganizationInfo, Option[Schema.OrganizationInfo]] = (fun: Schema.OrganizationInfo) => Option(fun)
		given putSchemaPostalAddress: Conversion[Schema.PostalAddress, Option[Schema.PostalAddress]] = (fun: Schema.PostalAddress) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaAdmin: Conversion[List[Schema.Admin], Option[List[Schema.Admin]]] = (fun: List[Schema.Admin]) => Option(fun)
		given putSchemaAdminRoleEnum: Conversion[Schema.Admin.RoleEnum, Option[Schema.Admin.RoleEnum]] = (fun: Schema.Admin.RoleEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaAccount: Conversion[List[Schema.Account], Option[List[Schema.Account]]] = (fun: List[Schema.Account]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
