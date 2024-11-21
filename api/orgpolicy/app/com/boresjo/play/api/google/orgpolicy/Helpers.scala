package com.boresjo.play.api.google.orgpolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2CustomConstraint: Conversion[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint], Option[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2CustomConstraint]) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2Policy: Conversion[List[Schema.GoogleCloudOrgpolicyV2Policy], Option[List[Schema.GoogleCloudOrgpolicyV2Policy]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2Policy]) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintBooleanConstraint: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintBooleanConstraint, Option[Schema.GoogleCloudOrgpolicyV2ConstraintBooleanConstraint]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintBooleanConstraint) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintListConstraint: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintListConstraint, Option[Schema.GoogleCloudOrgpolicyV2ConstraintListConstraint]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintListConstraint) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintConstraintDefaultEnum: Conversion[Schema.GoogleCloudOrgpolicyV2Constraint.ConstraintDefaultEnum, Option[Schema.GoogleCloudOrgpolicyV2Constraint.ConstraintDefaultEnum]] = (fun: Schema.GoogleCloudOrgpolicyV2Constraint.ConstraintDefaultEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2CustomConstraintMethodTypesEnum: Conversion[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint.MethodTypesEnum], Option[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint.MethodTypesEnum]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2CustomConstraint.MethodTypesEnum]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2CustomConstraintActionTypeEnum: Conversion[Schema.GoogleCloudOrgpolicyV2CustomConstraint.ActionTypeEnum, Option[Schema.GoogleCloudOrgpolicyV2CustomConstraint.ActionTypeEnum]] = (fun: Schema.GoogleCloudOrgpolicyV2CustomConstraint.ActionTypeEnum) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2PolicySpec: Conversion[Schema.GoogleCloudOrgpolicyV2PolicySpec, Option[Schema.GoogleCloudOrgpolicyV2PolicySpec]] = (fun: Schema.GoogleCloudOrgpolicyV2PolicySpec) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterTypeEnum: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.TypeEnum, Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.TypeEnum]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.TypeEnum) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata, Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterItemEnum: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.ItemEnum, Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.ItemEnum]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.ItemEnum) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition, Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues: Conversion[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues, Option[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues]] = (fun: Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues) => Option(fun)
		given putSchemaGoogleTypeExpr: Conversion[Schema.GoogleTypeExpr, Option[Schema.GoogleTypeExpr]] = (fun: Schema.GoogleTypeExpr) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionMethodTypesEnum: Conversion[List[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.MethodTypesEnum], Option[List[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.MethodTypesEnum]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.MethodTypesEnum]) => Option(fun)
		given putMapStringSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter: Conversion[Map[String, Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter], Option[Map[String, Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter]]] = (fun: Map[String, Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter]) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionActionTypeEnum: Conversion[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.ActionTypeEnum, Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.ActionTypeEnum]] = (fun: Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.ActionTypeEnum) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2Constraint: Conversion[List[Schema.GoogleCloudOrgpolicyV2Constraint], Option[List[Schema.GoogleCloudOrgpolicyV2Constraint]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2Constraint]) => Option(fun)
		given putListSchemaGoogleCloudOrgpolicyV2PolicySpecPolicyRule: Conversion[List[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRule], Option[List[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRule]]] = (fun: List[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRule]) => Option(fun)
		given putSchemaGoogleCloudOrgpolicyV2AlternatePolicySpec: Conversion[Schema.GoogleCloudOrgpolicyV2AlternatePolicySpec, Option[Schema.GoogleCloudOrgpolicyV2AlternatePolicySpec]] = (fun: Schema.GoogleCloudOrgpolicyV2AlternatePolicySpec) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
