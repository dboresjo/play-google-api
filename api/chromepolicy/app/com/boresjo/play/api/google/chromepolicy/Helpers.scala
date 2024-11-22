package com.boresjo.play.api.google.chromepolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest: Conversion[List[Schema.GoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest], Option[List[Schema.GoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1ResolvedPolicy: Conversion[List[Schema.GoogleChromePolicyVersionsV1ResolvedPolicy], Option[List[Schema.GoogleChromePolicyVersionsV1ResolvedPolicy]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1ResolvedPolicy]) => Option(fun)
		given putSchemaGoogleTypeDate: Conversion[Schema.GoogleTypeDate, Option[Schema.GoogleTypeDate]] = (fun: Schema.GoogleTypeDate) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1PolicyApiLifecyclePolicyApiLifecycleStageEnum: Conversion[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle.PolicyApiLifecycleStageEnum, Option[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle.PolicyApiLifecycleStageEnum]] = (fun: Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle.PolicyApiLifecycleStageEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1PolicyTargetKey: Conversion[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey, Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey]] = (fun: Schema.GoogleChromePolicyVersionsV1PolicyTargetKey) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1PolicyValue: Conversion[Schema.GoogleChromePolicyVersionsV1PolicyValue, Option[Schema.GoogleChromePolicyVersionsV1PolicyValue]] = (fun: Schema.GoogleChromePolicyVersionsV1PolicyValue) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1DeleteGroupPolicyRequest: Conversion[List[Schema.GoogleChromePolicyVersionsV1DeleteGroupPolicyRequest], Option[List[Schema.GoogleChromePolicyVersionsV1DeleteGroupPolicyRequest]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1DeleteGroupPolicyRequest]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaProto2EnumValueDescriptorProto: Conversion[List[Schema.Proto2EnumValueDescriptorProto], Option[List[Schema.Proto2EnumValueDescriptorProto]]] = (fun: List[Schema.Proto2EnumValueDescriptorProto]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1CertificateReference: Conversion[List[Schema.GoogleChromePolicyVersionsV1CertificateReference], Option[List[Schema.GoogleChromePolicyVersionsV1CertificateReference]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1CertificateReference]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaValidTargetResourcesEnum: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.ValidTargetResourcesEnum], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.ValidTargetResourcesEnum]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchema.ValidTargetResourcesEnum]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaSupportedPlatformsEnum: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.SupportedPlatformsEnum], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.SupportedPlatformsEnum]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchema.SupportedPlatformsEnum]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1AdditionalTargetKeyName: Conversion[List[Schema.GoogleChromePolicyVersionsV1AdditionalTargetKeyName], Option[List[Schema.GoogleChromePolicyVersionsV1AdditionalTargetKeyName]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1AdditionalTargetKeyName]) => Option(fun)
		given putSchemaProto2FileDescriptorProto: Conversion[Schema.Proto2FileDescriptorProto, Option[Schema.Proto2FileDescriptorProto]] = (fun: Schema.Proto2FileDescriptorProto) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaNoticeDescription: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaNoticeDescription], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaNoticeDescription]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchemaNoticeDescription]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaFieldDescription: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDescription], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDescription]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDescription]) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1PolicyApiLifecycle: Conversion[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle, Option[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle]] = (fun: Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1UploadedFileConstraintsSupportedContentTypesEnum: Conversion[List[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints.SupportedContentTypesEnum], Option[List[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints.SupportedContentTypesEnum]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints.SupportedContentTypesEnum]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1NetworkSetting: Conversion[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting], Option[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1ModifyGroupPolicyRequest: Conversion[List[Schema.GoogleChromePolicyVersionsV1ModifyGroupPolicyRequest], Option[List[Schema.GoogleChromePolicyVersionsV1ModifyGroupPolicyRequest]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1ModifyGroupPolicyRequest]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchema: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchema], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchema]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest: Conversion[List[Schema.GoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest], Option[List[Schema.GoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest]) => Option(fun)
		given putListSchemaProto2EnumDescriptorProto: Conversion[List[Schema.Proto2EnumDescriptorProto], Option[List[Schema.Proto2EnumDescriptorProto]]] = (fun: List[Schema.Proto2EnumDescriptorProto]) => Option(fun)
		given putListSchemaProto2DescriptorProto: Conversion[List[Schema.Proto2DescriptorProto], Option[List[Schema.Proto2DescriptorProto]]] = (fun: List[Schema.Proto2DescriptorProto]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicyModificationError: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationError], Option[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationError]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicyModificationError]) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1UploadedFileConstraints: Conversion[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints, Option[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints]] = (fun: Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1NumericRangeConstraint: Conversion[Schema.GoogleChromePolicyVersionsV1NumericRangeConstraint, Option[Schema.GoogleChromePolicyVersionsV1NumericRangeConstraint]] = (fun: Schema.GoogleChromePolicyVersionsV1NumericRangeConstraint) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicyModificationFieldError: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationFieldError], Option[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationFieldError]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicyModificationFieldError]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaProto2OneofDescriptorProto: Conversion[List[Schema.Proto2OneofDescriptorProto], Option[List[Schema.Proto2OneofDescriptorProto]]] = (fun: List[Schema.Proto2OneofDescriptorProto]) => Option(fun)
		given putListSchemaProto2FieldDescriptorProto: Conversion[List[Schema.Proto2FieldDescriptorProto], Option[List[Schema.Proto2FieldDescriptorProto]]] = (fun: List[Schema.Proto2FieldDescriptorProto]) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaFieldDependencies: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies]) => Option(fun)
		given putSchemaProto2FieldDescriptorProtoLabelEnum: Conversion[Schema.Proto2FieldDescriptorProto.LabelEnum, Option[Schema.Proto2FieldDescriptorProto.LabelEnum]] = (fun: Schema.Proto2FieldDescriptorProto.LabelEnum) => Option(fun)
		given putSchemaProto2FieldDescriptorProtoTypeEnum: Conversion[Schema.Proto2FieldDescriptorProto.TypeEnum, Option[Schema.Proto2FieldDescriptorProto.TypeEnum]] = (fun: Schema.Proto2FieldDescriptorProto.TypeEnum) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaRequiredItems: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaRequiredItems], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaRequiredItems]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchemaRequiredItems]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putListSchemaGoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription: Conversion[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription], Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription]]] = (fun: List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription]) => Option(fun)
		given putSchemaGoogleChromePolicyVersionsV1FieldConstraints: Conversion[Schema.GoogleChromePolicyVersionsV1FieldConstraints, Option[Schema.GoogleChromePolicyVersionsV1FieldConstraints]] = (fun: Schema.GoogleChromePolicyVersionsV1FieldConstraints) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
