package com.boresjo.play.api.google.iam

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
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaGoogleIamV2Policy: Conversion[List[Schema.GoogleIamV2Policy], Option[List[Schema.GoogleIamV2Policy]]] = (fun: List[Schema.GoogleIamV2Policy]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaGoogleIamV2PolicyRule: Conversion[List[Schema.GoogleIamV2PolicyRule], Option[List[Schema.GoogleIamV2PolicyRule]]] = (fun: List[Schema.GoogleIamV2PolicyRule]) => Option(fun)
		given putSchemaGoogleIamV2DenyRule: Conversion[Schema.GoogleIamV2DenyRule, Option[Schema.GoogleIamV2DenyRule]] = (fun: Schema.GoogleIamV2DenyRule) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleTypeExpr: Conversion[Schema.GoogleTypeExpr, Option[Schema.GoogleTypeExpr]] = (fun: Schema.GoogleTypeExpr) => Option(fun)
		given putSchemaGoogleIamAdminV1AuditDataPermissionDelta: Conversion[Schema.GoogleIamAdminV1AuditDataPermissionDelta, Option[Schema.GoogleIamAdminV1AuditDataPermissionDelta]] = (fun: Schema.GoogleIamAdminV1AuditDataPermissionDelta) => Option(fun)
		given putSchemaGoogleIamV1PolicyDelta: Conversion[Schema.GoogleIamV1PolicyDelta, Option[Schema.GoogleIamV1PolicyDelta]] = (fun: Schema.GoogleIamV1PolicyDelta) => Option(fun)
		given putListSchemaGoogleIamV1BindingDelta: Conversion[List[Schema.GoogleIamV1BindingDelta], Option[List[Schema.GoogleIamV1BindingDelta]]] = (fun: List[Schema.GoogleIamV1BindingDelta]) => Option(fun)
		given putSchemaGoogleIamV1BindingDeltaActionEnum: Conversion[Schema.GoogleIamV1BindingDelta.ActionEnum, Option[Schema.GoogleIamV1BindingDelta.ActionEnum]] = (fun: Schema.GoogleIamV1BindingDelta.ActionEnum) => Option(fun)
		given putSchemaCloudControl2SharedOperationsReconciliationOperationMetadataExclusiveActionEnum: Conversion[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum, Option[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum]] = (fun: Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
