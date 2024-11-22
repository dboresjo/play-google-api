package com.boresjo.play.api.google.drivelabels

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2WriteControl: Conversion[Schema.GoogleAppsDriveLabelsV2WriteControl, Option[Schema.GoogleAppsDriveLabelsV2WriteControl]] = (fun: Schema.GoogleAppsDriveLabelsV2WriteControl) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestViewEnum: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest.ViewEnum, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest.ViewEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest.ViewEnum) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest: Conversion[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest], Option[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest]) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LifecycleDisabledPolicy: Conversion[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy, Option[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy]] = (fun: Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy) => Option(fun)
		given putSchemaGoogleTypeDate: Conversion[Schema.GoogleTypeDate, Option[Schema.GoogleTypeDate]] = (fun: Schema.GoogleTypeDate) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldDateOptionsDateFormatTypeEnum: Conversion[Schema.GoogleAppsDriveLabelsV2FieldDateOptions.DateFormatTypeEnum, Option[Schema.GoogleAppsDriveLabelsV2FieldDateOptions.DateFormatTypeEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldDateOptions.DateFormatTypeEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldLimits: Conversion[Schema.GoogleAppsDriveLabelsV2FieldLimits, Option[Schema.GoogleAppsDriveLabelsV2FieldLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldLimits) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2LabelLock: Conversion[List[Schema.GoogleAppsDriveLabelsV2LabelLock], Option[List[Schema.GoogleAppsDriveLabelsV2LabelLock]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2LabelLock]) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2LabelPermission: Conversion[List[Schema.GoogleAppsDriveLabelsV2LabelPermission], Option[List[Schema.GoogleAppsDriveLabelsV2LabelPermission]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2LabelPermission]) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2Label: Conversion[List[Schema.GoogleAppsDriveLabelsV2Label], Option[List[Schema.GoogleAppsDriveLabelsV2Label]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2Label]) => Option(fun)
		given putSchemaGoogleTypeColor: Conversion[Schema.GoogleTypeColor, Option[Schema.GoogleTypeColor]] = (fun: Schema.GoogleTypeColor) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2Field: Conversion[Schema.GoogleAppsDriveLabelsV2Field, Option[Schema.GoogleAppsDriveLabelsV2Field]] = (fun: Schema.GoogleAppsDriveLabelsV2Field) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelPermissionRoleEnum: Conversion[Schema.GoogleAppsDriveLabelsV2LabelPermission.RoleEnum, Option[Schema.GoogleAppsDriveLabelsV2LabelPermission.RoleEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelPermission.RoleEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldProperties: Conversion[Schema.GoogleAppsDriveLabelsV2FieldProperties, Option[Schema.GoogleAppsDriveLabelsV2FieldProperties]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldProperties) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelProperties: Conversion[Schema.GoogleAppsDriveLabelsV2LabelProperties, Option[Schema.GoogleAppsDriveLabelsV2LabelProperties]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelProperties) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2SelectionLimits: Conversion[Schema.GoogleAppsDriveLabelsV2SelectionLimits, Option[Schema.GoogleAppsDriveLabelsV2SelectionLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2SelectionLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2UserLimits: Conversion[Schema.GoogleAppsDriveLabelsV2UserLimits, Option[Schema.GoogleAppsDriveLabelsV2UserLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2UserLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LongTextLimits: Conversion[Schema.GoogleAppsDriveLabelsV2LongTextLimits, Option[Schema.GoogleAppsDriveLabelsV2LongTextLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2LongTextLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2TextLimits: Conversion[Schema.GoogleAppsDriveLabelsV2TextLimits, Option[Schema.GoogleAppsDriveLabelsV2TextLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2TextLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2IntegerLimits: Conversion[Schema.GoogleAppsDriveLabelsV2IntegerLimits, Option[Schema.GoogleAppsDriveLabelsV2IntegerLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2IntegerLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DateLimits: Conversion[Schema.GoogleAppsDriveLabelsV2DateLimits, Option[Schema.GoogleAppsDriveLabelsV2DateLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2DateLimits) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldListOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldListOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldListOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldListOptions) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2UpdateLabelPermissionRequest: Conversion[List[Schema.GoogleAppsDriveLabelsV2UpdateLabelPermissionRequest], Option[List[Schema.GoogleAppsDriveLabelsV2UpdateLabelPermissionRequest]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2UpdateLabelPermissionRequest]) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoice: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2ListLimits: Conversion[Schema.GoogleAppsDriveLabelsV2ListLimits, Option[Schema.GoogleAppsDriveLabelsV2ListLimits]] = (fun: Schema.GoogleAppsDriveLabelsV2ListLimits) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2DeleteLabelPermissionRequest: Conversion[List[Schema.GoogleAppsDriveLabelsV2DeleteLabelPermissionRequest], Option[List[Schema.GoogleAppsDriveLabelsV2DeleteLabelPermissionRequest]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2DeleteLabelPermissionRequest]) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2Label: Conversion[Schema.GoogleAppsDriveLabelsV2Label, Option[Schema.GoogleAppsDriveLabelsV2Label]] = (fun: Schema.GoogleAppsDriveLabelsV2Label) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse: Conversion[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse], Option[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse]) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2Lifecycle: Conversion[Schema.GoogleAppsDriveLabelsV2Lifecycle, Option[Schema.GoogleAppsDriveLabelsV2Lifecycle]] = (fun: Schema.GoogleAppsDriveLabelsV2Lifecycle) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2UserInfo: Conversion[Schema.GoogleAppsDriveLabelsV2UserInfo, Option[Schema.GoogleAppsDriveLabelsV2UserInfo]] = (fun: Schema.GoogleAppsDriveLabelsV2UserInfo) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LockStatus: Conversion[Schema.GoogleAppsDriveLabelsV2LockStatus, Option[Schema.GoogleAppsDriveLabelsV2LockStatus]] = (fun: Schema.GoogleAppsDriveLabelsV2LockStatus) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LifecycleStateEnum: Conversion[Schema.GoogleAppsDriveLabelsV2Lifecycle.StateEnum, Option[Schema.GoogleAppsDriveLabelsV2Lifecycle.StateEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2Lifecycle.StateEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldUserOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldUserOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldUserOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldUserOptions) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldDisplayHints: Conversion[Schema.GoogleAppsDriveLabelsV2FieldDisplayHints, Option[Schema.GoogleAppsDriveLabelsV2FieldDisplayHints]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldDisplayHints) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldAppliedCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2FieldAppliedCapabilities, Option[Schema.GoogleAppsDriveLabelsV2FieldAppliedCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldAppliedCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSelectionOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSelectionOptions) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldTextOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldTextOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldTextOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldTextOptions) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldSchemaCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2FieldSchemaCapabilities, Option[Schema.GoogleAppsDriveLabelsV2FieldSchemaCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldSchemaCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldDateOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldDateOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldDateOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldDateOptions) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2FieldIntegerOptions: Conversion[Schema.GoogleAppsDriveLabelsV2FieldIntegerOptions, Option[Schema.GoogleAppsDriveLabelsV2FieldIntegerOptions]] = (fun: Schema.GoogleAppsDriveLabelsV2FieldIntegerOptions) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest: Conversion[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest, Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest]] = (fun: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2Field: Conversion[List[Schema.GoogleAppsDriveLabelsV2Field], Option[List[Schema.GoogleAppsDriveLabelsV2Field]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2Field]) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelAppliedLabelPolicy: Conversion[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy, Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelSchemaCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2LabelSchemaCapabilities, Option[Schema.GoogleAppsDriveLabelsV2LabelSchemaCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelSchemaCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelLabelTypeEnum: Conversion[Schema.GoogleAppsDriveLabelsV2Label.LabelTypeEnum, Option[Schema.GoogleAppsDriveLabelsV2Label.LabelTypeEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2Label.LabelTypeEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelDisplayHints: Conversion[Schema.GoogleAppsDriveLabelsV2LabelDisplayHints, Option[Schema.GoogleAppsDriveLabelsV2LabelDisplayHints]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelDisplayHints) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelAppliedCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2LabelAppliedCapabilities, Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelAppliedCapabilities) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2UpdateLabelCopyModeRequestCopyModeEnum: Conversion[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.CopyModeEnum, Option[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.CopyModeEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.CopyModeEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2UpdateLabelCopyModeRequestViewEnum: Conversion[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.ViewEnum, Option[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.ViewEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.ViewEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2BadgeConfig: Conversion[Schema.GoogleAppsDriveLabelsV2BadgeConfig, Option[Schema.GoogleAppsDriveLabelsV2BadgeConfig]] = (fun: Schema.GoogleAppsDriveLabelsV2BadgeConfig) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelLockStateEnum: Conversion[Schema.GoogleAppsDriveLabelsV2LabelLock.StateEnum, Option[Schema.GoogleAppsDriveLabelsV2LabelLock.StateEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelLock.StateEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelLockCapabilities: Conversion[Schema.GoogleAppsDriveLabelsV2LabelLockCapabilities, Option[Schema.GoogleAppsDriveLabelsV2LabelLockCapabilities]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelLockCapabilities) => Option(fun)
		given putListSchemaGoogleAppsDriveLabelsV2FieldSelectionOptionsChoice: Conversion[List[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice], Option[List[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice]]] = (fun: List[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelAppliedLabelPolicyCopyModeEnum: Conversion[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy.CopyModeEnum, Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy.CopyModeEnum]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy.CopyModeEnum) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2LabelPermission: Conversion[Schema.GoogleAppsDriveLabelsV2LabelPermission, Option[Schema.GoogleAppsDriveLabelsV2LabelPermission]] = (fun: Schema.GoogleAppsDriveLabelsV2LabelPermission) => Option(fun)
		given putSchemaGoogleAppsDriveLabelsV2BadgeColors: Conversion[Schema.GoogleAppsDriveLabelsV2BadgeColors, Option[Schema.GoogleAppsDriveLabelsV2BadgeColors]] = (fun: Schema.GoogleAppsDriveLabelsV2BadgeColors) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
