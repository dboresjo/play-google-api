package com.boresjo.play.api.google.cloudresourcemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLien: Conversion[List[Schema.Lien], Option[List[Schema.Lien]]] = (fun: List[Schema.Lien]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaFolderStateEnum: Conversion[Schema.Folder.StateEnum, Option[Schema.Folder.StateEnum]] = (fun: Schema.Folder.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaFolder: Conversion[List[Schema.Folder], Option[List[Schema.Folder]]] = (fun: List[Schema.Folder]) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putSchemaOrganizationStateEnum: Conversion[Schema.Organization.StateEnum, Option[Schema.Organization.StateEnum]] = (fun: Schema.Organization.StateEnum) => Option(fun)
		given putListSchemaOrganization: Conversion[List[Schema.Organization], Option[List[Schema.Organization]]] = (fun: List[Schema.Organization]) => Option(fun)
		given putSchemaProjectStateEnum: Conversion[Schema.Project.StateEnum, Option[Schema.Project.StateEnum]] = (fun: Schema.Project.StateEnum) => Option(fun)
		given putListSchemaProject: Conversion[List[Schema.Project], Option[List[Schema.Project]]] = (fun: List[Schema.Project]) => Option(fun)
		given putListSchemaTagBinding: Conversion[List[Schema.TagBinding], Option[List[Schema.TagBinding]]] = (fun: List[Schema.TagBinding]) => Option(fun)
		given putListSchemaEffectiveTag: Conversion[List[Schema.EffectiveTag], Option[List[Schema.EffectiveTag]]] = (fun: List[Schema.EffectiveTag]) => Option(fun)
		given putListSchemaTagHold: Conversion[List[Schema.TagHold], Option[List[Schema.TagHold]]] = (fun: List[Schema.TagHold]) => Option(fun)
		given putListSchemaTagKey: Conversion[List[Schema.TagKey], Option[List[Schema.TagKey]]] = (fun: List[Schema.TagKey]) => Option(fun)
		given putSchemaTagKeyPurposeEnum: Conversion[Schema.TagKey.PurposeEnum, Option[Schema.TagKey.PurposeEnum]] = (fun: Schema.TagKey.PurposeEnum) => Option(fun)
		given putListSchemaTagValue: Conversion[List[Schema.TagValue], Option[List[Schema.TagValue]]] = (fun: List[Schema.TagValue]) => Option(fun)
		given putSchemaCloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperationOperationTypeEnum: Conversion[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation.OperationTypeEnum, Option[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation.OperationTypeEnum]] = (fun: Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation.OperationTypeEnum) => Option(fun)
		given putSchemaFolderOperationErrorErrorMessageIdEnum: Conversion[Schema.FolderOperationError.ErrorMessageIdEnum, Option[Schema.FolderOperationError.ErrorMessageIdEnum]] = (fun: Schema.FolderOperationError.ErrorMessageIdEnum) => Option(fun)
		given putSchemaCloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperationOperationTypeEnum: Conversion[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation.OperationTypeEnum, Option[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation.OperationTypeEnum]] = (fun: Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation.OperationTypeEnum) => Option(fun)
		given putSchemaFolderOperationOperationTypeEnum: Conversion[Schema.FolderOperation.OperationTypeEnum, Option[Schema.FolderOperation.OperationTypeEnum]] = (fun: Schema.FolderOperation.OperationTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
