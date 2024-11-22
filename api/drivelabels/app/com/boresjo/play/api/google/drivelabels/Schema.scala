package com.boresjo.play.api.google.drivelabels

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest(
	  /** Required. ID of the Field to delete. */
		id: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2UserCapabilities(
	  /** Output only. Whether the user is an administrator for the shared labels feature. */
		canAdministrateLabels: Option[Boolean] = None,
	  /** Output only. Whether the user is allowed to create new shared labels. */
		canCreateSharedLabels: Option[Boolean] = None,
	  /** Output only. Resource name for the user capabilities. */
		name: Option[String] = None,
	  /** Output only. Whether the user is allowed to create new admin labels. */
		canCreateAdminLabels: Option[Boolean] = None,
	  /** Output only. Whether the user is allowed access to the label manager. */
		canAccessLabelManager: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelSchemaCapabilities(
	  /** Whether the user can change this label. */
		canUpdate: Option[Boolean] = None,
	  /** Whether the user can enable this label. The user must have permission and this label must be disabled. */
		canEnable: Option[Boolean] = None,
	  /** Whether the user can disable this label. The user must have permission and this label must not already be disabled. */
		canDisable: Option[Boolean] = None,
	  /** Whether the user can delete this label. The user must have permission and the label must be disabled. */
		canDelete: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse(
	  /** Creates a new selection list option to add to a Selection Field. */
		createSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse] = None,
	  /** Enables a Choice within a Selection Field. */
		enableSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse] = None,
	  /** Update Field type and/or type options. */
		updateFieldType: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse] = None,
	  /** Updates a Choice within a Selection Field. */
		updateSelectionChoiceProperties: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse] = None,
	  /** Deletes a Choice from a Selection Field. */
		deleteSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse] = None,
	  /** Updated basic properties of a Label. */
		updateLabel: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse] = None,
	  /** Enables Field. */
		enableField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse] = None,
	  /** Updates basic properties of a Field. */
		updateField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse] = None,
	  /** Deletes a Field from the label. */
		deleteField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse] = None,
	  /** Disables a Choice within a Selection Field. */
		disableSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse] = None,
	  /** Creates a new Field. */
		createField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse] = None,
	  /** Disables Field. */
		disableField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse] = None
	)
	
	object GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest {
		enum ViewEnum extends Enum[ViewEnum] { case LABEL_VIEW_BASIC, LABEL_VIEW_FULL }
	}
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest(
	  /** Provides control over how write requests are executed. */
		writeControl: Option[Schema.GoogleAppsDriveLabelsV2WriteControl] = None,
	  /** The BCP-47 language code to use for evaluating localized Field labels when `include_label_in_response` is `true`. */
		languageCode: Option[String] = None,
	  /** When specified, only certain fields belonging to the indicated view will be returned. */
		view: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest.ViewEnum] = None,
	  /** A list of updates to apply to the Label. Requests will be applied in the order they are specified. */
		requests: Option[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest]] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest(
	  /** The fields that should be updated. At least one field must be specified. The root `disabled_policy` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None,
	  /** Required. Key of the Field to disable. */
		id: Option[String] = None,
	  /** Required. Field Disabled Policy. */
		disabledPolicy: Option[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy] = None
	)
	
	object GoogleAppsDriveLabelsV2FieldDateOptions {
		enum DateFormatTypeEnum extends Enum[DateFormatTypeEnum] { case DATE_FORMAT_UNSPECIFIED, LONG_DATE, SHORT_DATE }
	}
	case class GoogleAppsDriveLabelsV2FieldDateOptions(
	  /** Output only. ICU date format. */
		dateFormat: Option[String] = None,
	  /** Output only. Minimum valid value (year, month, day). */
		minValue: Option[Schema.GoogleTypeDate] = None,
	  /** Output only. Maximum valid value (year, month, day). */
		maxValue: Option[Schema.GoogleTypeDate] = None,
	  /** Localized date formatting option. Field values are rendered in this format according to their locale. */
		dateFormatType: Option[Schema.GoogleAppsDriveLabelsV2FieldDateOptions.DateFormatTypeEnum] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelLimits(
	  /** Resource name. */
		name: Option[String] = None,
	  /** The limits for Fields. */
		fieldLimits: Option[Schema.GoogleAppsDriveLabelsV2FieldLimits] = None,
	  /** The maximum number of characters allowed for the description. */
		maxDescriptionLength: Option[Int] = None,
	  /** The maximum number of Fields allowed within the label. */
		maxFields: Option[Int] = None,
	  /** The maximum number of published Fields that can be deleted. */
		maxDeletedFields: Option[Int] = None,
	  /** The maximum number of characters allowed for the title. */
		maxTitleLength: Option[Int] = None,
	  /** The maximum number of draft revisions that will be kept before deleting old drafts. */
		maxDraftRevisions: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableSelectionChoiceResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2TextLimits(
	  /** Maximum length allowed for a text Field type. */
		maxLength: Option[Int] = None,
	  /** Minimum length allowed for a text Field type. */
		minLength: Option[Int] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleAppsDriveLabelsV2DateLimits(
	  /** Maximum value for the date Field type. */
		maxValue: Option[Schema.GoogleTypeDate] = None,
	  /** Minimum value for the date Field type. */
		minValue: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest(
	  /** Required. ID of the Field to enable. */
		id: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest(
	  /** Required. The Selection Field in which a Choice will be enabled. */
		fieldId: Option[String] = None,
	  /** Required. Choice to enable. */
		id: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateSelectionChoiceResponse(
	  /** The server-generated ID of the created choice within the Field */
		id: Option[String] = None,
	  /** The server-generated id of the field. */
		fieldId: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2ListLabelLocksResponse(
	  /** The token of the next page in the response. */
		nextPageToken: Option[String] = None,
	  /** LabelLocks. */
		labelLocks: Option[List[Schema.GoogleAppsDriveLabelsV2LabelLock]] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSchemaCapabilities(
	  /** Whether the user can delete this field. The user must have permission and the field must be deprecated. */
		canDelete: Option[Boolean] = None,
	  /** Whether the user can disable this field. The user must have permission and this field must not already be disabled. */
		canDisable: Option[Boolean] = None,
	  /** Whether the user can enable this field. The user must have permission and this field must be disabled. */
		canEnable: Option[Boolean] = None,
	  /** Whether the user can change this field. */
		canUpdate: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2ListLabelPermissionsResponse(
	  /** Label permissions. */
		labelPermissions: Option[List[Schema.GoogleAppsDriveLabelsV2LabelPermission]] = None,
	  /** The token of the next page in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2ListLabelsResponse(
	  /** Labels. */
		labels: Option[List[Schema.GoogleAppsDriveLabelsV2Label]] = None,
	  /** The token of the next page in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldPropertiesResponse(
	  /** The priority of the updated field. The priority may change from what was specified to assure contiguous priorities between fields (1-n). */
		priority: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2BadgeColors(
	  /** Output only. Badge foreground that pairs with the background. */
		foregroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** Output only. Badge background that pairs with the foreground. */
		backgroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** Output only. Color that can be used for text without a background. */
		soloColor: Option[Schema.GoogleTypeColor] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest(
	  /** Required. Field to create. */
		field: Option[Schema.GoogleAppsDriveLabelsV2Field] = None
	)
	
	object GoogleAppsDriveLabelsV2LabelPermission {
		enum RoleEnum extends Enum[RoleEnum] { case LABEL_ROLE_UNSPECIFIED, READER, APPLIER, ORGANIZER, EDITOR }
	}
	case class GoogleAppsDriveLabelsV2LabelPermission(
	  /** The role the principal should have. */
		role: Option[Schema.GoogleAppsDriveLabelsV2LabelPermission.RoleEnum] = None,
	  /** Audience to grant a role to. The magic value of `audiences/default` may be used to apply the role to the default audience in the context of the organization that owns the Label. */
		audience: Option[String] = None,
	  /** Person resource name. */
		person: Option[String] = None,
	  /** Specifies the email address for a user or group pricinpal. Not populated for audience principals. User and Group permissions may only be inserted using email address. On update requests, if email address is specified, no principal should be specified. */
		email: Option[String] = None,
	  /** Resource name of this permission. */
		name: Option[String] = None,
	  /** Group resource name. */
		group: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest(
	  /** Required. The Field to update. */
		id: Option[String] = None,
	  /** Required. Basic Field properties. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2FieldProperties] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `properties` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldAppliedCapabilities(
	  /** Whether the user can search for Drive items referencing this field. */
		canSearch: Option[Boolean] = None,
	  /** Whether the user can read related applied metadata on items. */
		canRead: Option[Boolean] = None,
	  /** Whether the user can set this field on Drive items. */
		canWrite: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest(
	  /** Required. Choice to disable. */
		id: Option[String] = None,
	  /** Required. The disabled policy to update. */
		disabledPolicy: Option[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy] = None,
	  /** Required. The Selection Field in which a Choice will be disabled. */
		fieldId: Option[String] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `disabled_policy` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteFieldResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest(
	  /** The fields that should be updated. At least one field must be specified. The root `label_properties` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None,
	  /** Required. Label properties to update. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2LabelProperties] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldLimits(
	  /** Selection Field limits. */
		selectionLimits: Option[Schema.GoogleAppsDriveLabelsV2SelectionLimits] = None,
	  /** Limits for Field description, also called help text. */
		maxDescriptionLength: Option[Int] = None,
	  /** User Field limits. */
		userLimits: Option[Schema.GoogleAppsDriveLabelsV2UserLimits] = None,
	  /** Long text Field limits. */
		longTextLimits: Option[Schema.GoogleAppsDriveLabelsV2LongTextLimits] = None,
	  /** The relevant limits for the specified Field.Type. Text Field limits. */
		textLimits: Option[Schema.GoogleAppsDriveLabelsV2TextLimits] = None,
	  /** Limits for Field title. */
		maxDisplayNameLength: Option[Int] = None,
	  /** Integer Field limits. */
		integerLimits: Option[Schema.GoogleAppsDriveLabelsV2IntegerLimits] = None,
	  /** Date Field limits. */
		dateLimits: Option[Schema.GoogleAppsDriveLabelsV2DateLimits] = None,
	  /** Max length for the id. */
		maxIdLength: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelProperties(
	  /** Required. Title of the label. */
		title: Option[String] = None,
	  /** The description of the label. */
		description: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest(
	  /** Required. Choice to delete. */
		id: Option[String] = None,
	  /** Required. The Selection Field from which a Choice will be deleted. */
		fieldId: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2LockStatus(
	  /** Output only. Indicates whether this label component is the (direct) target of a LabelLock. A label component can be implicitly locked even if it's not the direct target of a LabelLock, in which case this field is set to false. */
		locked: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldUserOptions(
	  /** When specified, indicates that this field supports a list of values. Once the field is published, this cannot be changed. */
		listOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldListOptions] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities(
	  /** Whether the user can enable this choice. */
		canEnable: Option[Boolean] = None,
	  /** Whether the user can update this choice. */
		canUpdate: Option[Boolean] = None,
	  /** Whether the user can disable this choice. */
		canDisable: Option[Boolean] = None,
	  /** Whether the user can delete this choice. */
		canDelete: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest(
	  /** Required. The request message specifying the resources to update. */
		requests: Option[List[Schema.GoogleAppsDriveLabelsV2UpdateLabelPermissionRequest]] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. If this is set, the use_admin_access field in the UpdateLabelPermissionRequest messages must either be empty or match this field. */
		useAdminAccess: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableSelectionChoiceResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest(
	  /** Required. The Selection Field in which a Choice will be created. */
		fieldId: Option[String] = None,
	  /** Required. The Choice to create. */
		choice: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice] = None
	)
	
	case class GoogleAppsDriveLabelsV2UserLimits(
	  /** Limits for list-variant of a Field type. */
		listLimits: Option[Schema.GoogleAppsDriveLabelsV2ListLimits] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldDisplayHints(
	  /** Whether the field should be shown in the UI as disabled. */
		disabled: Option[Boolean] = None,
	  /** This field should be shown in the apply menu when applying values to a Drive item. */
		shownInApply: Option[Boolean] = None,
	  /** This field should be hidden in the search menu when searching for Drive items. */
		hiddenInSearch: Option[Boolean] = None,
	  /** Whether the field should be shown as required in the UI. */
		required: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest(
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. If this is set, the use_admin_access field in the DeleteLabelPermissionRequest messages must either be empty or match this field. */
		useAdminAccess: Option[Boolean] = None,
	  /** Required. The request message specifying the resources to update. */
		requests: Option[List[Schema.GoogleAppsDriveLabelsV2DeleteLabelPermissionRequest]] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseEnableFieldResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponse(
	  /** The label after updates were applied. This is only set if [BatchUpdateLabelResponse2.include_label_in_response] is `true` and there were no errors. */
		updatedLabel: Option[Schema.GoogleAppsDriveLabelsV2Label] = None,
	  /** The reply of the updates. This maps 1:1 with the updates, although responses to some requests may be empty. */
		responses: Option[List[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseResponse]] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities(
	  /** Whether the user can use this choice in search queries. */
		canSearch: Option[Boolean] = None,
	  /** Whether the user can read related applied metadata on items. */
		canRead: Option[Boolean] = None,
	  /** Whether the user can select this choice on an item. */
		canSelect: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice(
	  /** Output only. Lifecycle of the choice. */
		lifecycle: Option[Schema.GoogleAppsDriveLabelsV2Lifecycle] = None,
	  /** Output only. The capabilities related to this option when editing the option. */
		schemaCapabilities: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceSchemaCapabilities] = None,
	  /** Output only. The time this choice was created. */
		createTime: Option[String] = None,
	  /** The unique value of the choice. This ID is autogenerated. Matches the regex: `([a-zA-Z0-9_])+`. */
		id: Option[String] = None,
	  /** Output only. The time this choice was published. This value has no meaning when the choice is not published. */
		publishTime: Option[String] = None,
	  /** Basic properties of the choice. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties] = None,
	  /** Output only. The user who disabled this choice. This value has no meaning when the option is not disabled. */
		disabler: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The LockStatus of this choice. */
		lockStatus: Option[Schema.GoogleAppsDriveLabelsV2LockStatus] = None,
	  /** Output only. UI display hints for rendering a choice. */
		displayHints: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints] = None,
	  /** Output only. The user who published this choice. This value has no meaning when the choice is not published. */
		publisher: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The user who created this choice. */
		creator: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The time this choice was disabled. This value has no meaning when the choice is not disabled. */
		disableTime: Option[String] = None,
	  /** Output only. The time this choice was updated last. */
		updateTime: Option[String] = None,
	  /** Output only. The capabilities related to this choice on applied metadata. */
		appliedCapabilities: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceAppliedCapabilities] = None,
	  /** Output only. The user who updated this choice last. */
		updater: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateSelectionChoicePropertiesResponse(
	  /** The priority of the updated choice. The priority may change from what was specified to assure contiguous priorities between choices (1-n). */
		priority: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldProperties(
	  /** Required. The display text to show in the UI identifying this field. */
		displayName: Option[String] = None,
	  /** Whether the field should be marked as required. */
		required: Option[Boolean] = None,
	  /** Input only. Insert or move this field before the indicated field. If empty, the field is placed at the end of the list. */
		insertBeforeField: Option[String] = None
	)
	
	object GoogleAppsDriveLabelsV2Lifecycle {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNPUBLISHED_DRAFT, PUBLISHED, DISABLED, DELETED }
	}
	case class GoogleAppsDriveLabelsV2Lifecycle(
	  /** The policy that governs how to show a disabled label, field, or selection choice. */
		disabledPolicy: Option[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy] = None,
	  /** Output only. Whether the object associated with this lifecycle has unpublished changes. */
		hasUnpublishedChanges: Option[Boolean] = None,
	  /** Output only. The state of the object associated with this lifecycle. */
		state: Option[Schema.GoogleAppsDriveLabelsV2Lifecycle.StateEnum] = None
	)
	
	case class GoogleAppsDriveLabelsV2LongTextLimits(
	  /** Minimum length allowed for a long text Field type. */
		minLength: Option[Int] = None,
	  /** Maximum length allowed for a long text Field type. */
		maxLength: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2EnableLabelRequest(
	  /** Provides control over how write requests are executed. Defaults to unset, which means last write wins. */
		writeControl: Option[Schema.GoogleAppsDriveLabelsV2WriteControl] = None,
	  /** The BCP-47 language code to use for evaluating localized field labels. When not specified, values in the default configured language will be used. */
		languageCode: Option[String] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2Field(
	  /** Output only. The lifecycle of this field. */
		lifecycle: Option[Schema.GoogleAppsDriveLabelsV2Lifecycle] = None,
	  /** Output only. The key to use when constructing Drive search queries to find files based on values defined for this field on files. For example, "`{query_key}` > 2001-01-01". */
		queryKey: Option[String] = None,
	  /** Output only. The time this field was created. */
		createTime: Option[String] = None,
	  /** Output only. The user who published this field. This value has no meaning when the field is not published. */
		publisher: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** User field options. */
		userOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldUserOptions] = None,
	  /** Output only. The key of a field, unique within a label or library. This value is autogenerated. Matches the regex: `([a-zA-Z0-9])+` */
		id: Option[String] = None,
	  /** Output only. UI display hints for rendering a field. */
		displayHints: Option[Schema.GoogleAppsDriveLabelsV2FieldDisplayHints] = None,
	  /** Output only. The capabilities this user has on this field and its value when the label is applied on Drive items. */
		appliedCapabilities: Option[Schema.GoogleAppsDriveLabelsV2FieldAppliedCapabilities] = None,
	  /** Selection field options. */
		selectionOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptions] = None,
	  /** The basic properties of the field. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2FieldProperties] = None,
	  /** Output only. The user who created this field. */
		creator: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Text field options. */
		textOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldTextOptions] = None,
	  /** Output only. The user who disabled this field. This value has no meaning when the field is not disabled. */
		disabler: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The capabilities this user has when editing this field. */
		schemaCapabilities: Option[Schema.GoogleAppsDriveLabelsV2FieldSchemaCapabilities] = None,
	  /** Date field options. */
		dateOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldDateOptions] = None,
	  /** Output only. The user who modified this field. */
		updater: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The time this field was disabled. This value has no meaning when the field is not disabled. */
		disableTime: Option[String] = None,
	  /** Integer field options. */
		integerOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldIntegerOptions] = None,
	  /** Output only. The time this field was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The LockStatus of this field. */
		lockStatus: Option[Schema.GoogleAppsDriveLabelsV2LockStatus] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest(
	  /** Required. The Choice to update. */
		id: Option[String] = None,
	  /** Required. The Selection Field to update. */
		fieldId: Option[String] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `properties` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None,
	  /** Required. The Choice properties to update. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties] = None
	)
	
	case class GoogleAppsDriveLabelsV2SelectionLimits(
	  /** Limits for list-variant of a Field type. */
		listLimits: Option[Schema.GoogleAppsDriveLabelsV2ListLimits] = None,
	  /** The max number of choices. */
		maxChoices: Option[Int] = None,
	  /** Maximum ID length for a selection options. */
		maxIdLength: Option[Int] = None,
	  /** Maximum length for display name. */
		maxDisplayNameLength: Option[Int] = None,
	  /** Maximum number of deleted choices. */
		maxDeletedChoices: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestRequest(
	  /** Delete a Choice within a Selection Field. */
		deleteSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteSelectionChoiceRequest] = None,
	  /** Deletes a Field from the label. */
		deleteField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDeleteFieldRequest] = None,
	  /** Update Field type and/or type options. */
		updateFieldType: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest] = None,
	  /** Creates Choice within a Selection field. */
		createSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateSelectionChoiceRequest] = None,
	  /** Creates a new Field. */
		createField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestCreateFieldRequest] = None,
	  /** Updates the Label properties. */
		updateLabel: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateLabelPropertiesRequest] = None,
	  /** Disable a Choice within a Selection Field. */
		disableSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableSelectionChoiceRequest] = None,
	  /** Enables the Field. */
		enableField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableFieldRequest] = None,
	  /** Enable a Choice within a Selection Field. */
		enableSelectionChoice: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestEnableSelectionChoiceRequest] = None,
	  /** Updates basic properties of a Field. */
		updateField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldPropertiesRequest] = None,
	  /** Update a Choice properties within a Selection Field. */
		updateSelectionChoiceProperties: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateSelectionChoicePropertiesRequest] = None,
	  /** Disables the Field. */
		disableField: Option[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestDisableFieldRequest] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDeleteSelectionChoiceResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseDisableFieldResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelRequestUpdateFieldTypeRequest(
	  /** Required. The Field to update. */
		id: Option[String] = None,
	  /** Update field to Date. */
		dateOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldDateOptions] = None,
	  /** Update field to Text. */
		textOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldTextOptions] = None,
	  /** Update field to Integer. */
		integerOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldIntegerOptions] = None,
	  /** Update field to User. */
		userOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldUserOptions] = None,
	  /** Update field to Selection. */
		selectionOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptions] = None,
	  /** The fields that should be updated. At least one field must be specified. The root of `type_options` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelLockCapabilities(
	  /** True if the user is authorized to view the policy. */
		canViewPolicy: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsResponse(
	  /** Required. Permissions updated. */
		permissions: Option[List[Schema.GoogleAppsDriveLabelsV2LabelPermission]] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelDisplayHints(
	  /** Order to display label in a list. */
		priority: Option[String] = None,
	  /** This label should be shown in the apply menu when applying values to a Drive item. */
		shownInApply: Option[Boolean] = None,
	  /** This label should be hidden in the search menu when searching for Drive items. */
		hiddenInSearch: Option[Boolean] = None,
	  /** Whether the label should be shown in the UI as disabled. */
		disabled: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2ListLimits(
	  /** Maximum number of values allowed for the Field type. */
		maxEntries: Option[Int] = None
	)
	
	object GoogleAppsDriveLabelsV2Label {
		enum LabelTypeEnum extends Enum[LabelTypeEnum] { case LABEL_TYPE_UNSPECIFIED, SHARED, ADMIN, GOOGLE_APP }
	}
	case class GoogleAppsDriveLabelsV2Label(
	  /** List of fields in descending priority order. */
		fields: Option[List[Schema.GoogleAppsDriveLabelsV2Field]] = None,
	  /** Output only. The time this label revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Output only. Behavior of this label when it's applied to Drive items. */
		appliedLabelPolicy: Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy] = None,
	  /** Output only. The time this label was published. This value has no meaning when the label is not published. */
		publishTime: Option[String] = None,
	  /** Custom URL to present to users to allow them to learn more about this label and how it should be used. */
		learnMoreUri: Option[String] = None,
	  /** Output only. Globally unique identifier of this label. ID makes up part of the label `name`, but unlike `name`, ID is consistent between revisions. Matches the regex: `([a-zA-Z0-9])+` */
		id: Option[String] = None,
	  /** Output only. The lifecycle state of the label including whether it's published, deprecated, and has draft changes. */
		lifecycle: Option[Schema.GoogleAppsDriveLabelsV2Lifecycle] = None,
	  /** Output only. Resource name of the label. Will be in the form of either: `labels/{id}` or `labels/{id}@{revision_id}` depending on the request. See `id` and `revision_id` below. */
		name: Option[String] = None,
	  /** Output only. Revision ID of the label. Revision ID might be part of the label `name` depending on the request issued. A new revision is created whenever revisioned properties of a label are changed. Matches the regex: `([a-zA-Z0-9])+` */
		revisionId: Option[String] = None,
	  /** Output only. The user who created this label revision. */
		revisionCreator: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The capabilities the user has on this label. */
		schemaCapabilities: Option[Schema.GoogleAppsDriveLabelsV2LabelSchemaCapabilities] = None,
	  /** Output only. The user who published this label. This value has no meaning when the label is not published. */
		publisher: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Required. The type of label. */
		labelType: Option[Schema.GoogleAppsDriveLabelsV2Label.LabelTypeEnum] = None,
	  /** Output only. The customer this label belongs to. For example: "customers/123abc789." */
		customer: Option[String] = None,
	  /** Output only. The time this label was created. */
		createTime: Option[String] = None,
	  /** Output only. The user who created this label. */
		creator: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. UI display hints for rendering the label. */
		displayHints: Option[Schema.GoogleAppsDriveLabelsV2LabelDisplayHints] = None,
	  /** Required. The basic properties of the label. */
		properties: Option[Schema.GoogleAppsDriveLabelsV2LabelProperties] = None,
	  /** Output only. The capabilities related to this label on applied metadata. */
		appliedCapabilities: Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedCapabilities] = None,
	  /** Output only. The user who disabled this label. This value has no meaning when the label is not disabled. */
		disabler: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. The LockStatus of this label. */
		lockStatus: Option[Schema.GoogleAppsDriveLabelsV2LockStatus] = None,
	  /** Output only. The time this label was disabled. This value has no meaning when the label is not disabled. */
		disableTime: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DisableLabelRequest(
	  /** Provides control over how write requests are executed. Defaults to unset, which means last write wins. */
		writeControl: Option[Schema.GoogleAppsDriveLabelsV2WriteControl] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `disabled_policy` is implied and should not be specified. A single `&#42;` can be used as short-hand for updating every field. */
		updateMask: Option[String] = None,
	  /** The BCP-47 language code to use for evaluating localized field labels. When not specified, values in the default configured language will be used. */
		languageCode: Option[String] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None,
	  /** Disabled policy to use. */
		disabledPolicy: Option[Schema.GoogleAppsDriveLabelsV2LifecycleDisabledPolicy] = None
	)
	
	case class GoogleAppsDriveLabelsV2UserInfo(
	  /** The identifier for this user that can be used with the People API to get more information. For example, people/12345678. */
		person: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldIntegerOptions(
	  /** Output only. The minimum valid value for the integer field. */
		minValue: Option[String] = None,
	  /** Output only. The maximum valid value for the integer field. */
		maxValue: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2PublishLabelRequest(
	  /** Provides control over how write requests are executed. Defaults to unset, which means last write wins. */
		writeControl: Option[Schema.GoogleAppsDriveLabelsV2WriteControl] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None,
	  /** The BCP-47 language code to use for evaluating localized field labels. When not specified, values in the default configured language will be used. */
		languageCode: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseCreateFieldResponse(
	  /** The field of the created field. When left blank in a create request, a key will be autogenerated and can be identified here. */
		id: Option[String] = None,
	  /** The priority of the created field. The priority may change from what was specified to assure contiguous priorities between fields (1-n). */
		priority: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldTextOptions(
	  /** Output only. The maximum valid length of values for the text field. */
		maxLength: Option[Int] = None,
	  /** Output only. The minimum valid length of values for the text field. */
		minLength: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2LabelAppliedCapabilities(
	  /** Whether the user can read applied metadata related to this label. */
		canRead: Option[Boolean] = None,
	  /** Whether the user can apply this label to items. */
		canApply: Option[Boolean] = None,
	  /** Whether the user can remove this label from items. */
		canRemove: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2BadgeConfig(
	  /** Override the default global priority of this badge. When set to 0, the default priority heuristic is used. */
		priorityOverride: Option[String] = None,
	  /** The color of the badge. When not specified, no badge is rendered. The background, foreground, and solo (light and dark mode) colors set here are changed in the Drive UI into the closest recommended supported color. */
		color: Option[Schema.GoogleTypeColor] = None
	)
	
	object GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest {
		enum CopyModeEnum extends Enum[CopyModeEnum] { case COPY_MODE_UNSPECIFIED, DO_NOT_COPY, ALWAYS_COPY, COPY_APPLIABLE }
		enum ViewEnum extends Enum[ViewEnum] { case LABEL_VIEW_BASIC, LABEL_VIEW_FULL }
	}
	case class GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest(
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None,
	  /** Required. Indicates how the applied Label, and Field values should be copied when a Drive item is copied. */
		copyMode: Option[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.CopyModeEnum] = None,
	  /** The BCP-47 language code to use for evaluating localized field labels. When not specified, values in the default configured language will be used. */
		languageCode: Option[String] = None,
	  /** When specified, only certain fields belonging to the indicated view will be returned. */
		view: Option[Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest.ViewEnum] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceProperties(
	  /** Input only. Insert or move this choice before the indicated choice. If empty, the choice is placed at the end of the list. */
		insertBeforeChoice: Option[String] = None,
	  /** The description of this label. */
		description: Option[String] = None,
	  /** Required. The display text to show in the UI identifying this field. */
		displayName: Option[String] = None,
	  /** The badge configuration for this choice. When set, the label that owns this choice is considered a "badged label". */
		badgeConfig: Option[Schema.GoogleAppsDriveLabelsV2BadgeConfig] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateLabelPropertiesResponse(
	
	)
	
	object GoogleAppsDriveLabelsV2LabelLock {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETING }
	}
	case class GoogleAppsDriveLabelsV2LabelLock(
	  /** Output only. This LabelLock's state. */
		state: Option[Schema.GoogleAppsDriveLabelsV2LabelLock.StateEnum] = None,
	  /** Output only. The time this LabelLock was created. */
		createTime: Option[String] = None,
	  /** Output only. The user whose credentials were used to create the LabelLock. This will not be present if no user was responsible for creating the LabelLock. */
		creator: Option[Schema.GoogleAppsDriveLabelsV2UserInfo] = None,
	  /** Output only. A timestamp indicating when this LabelLock was scheduled for deletion. This will be present only if this LabelLock is in the DELETING state. */
		deleteTime: Option[String] = None,
	  /** Output only. Resource name of this LabelLock. */
		name: Option[String] = None,
	  /** The ID of the Field that should be locked. Empty if the whole Label should be locked. */
		fieldId: Option[String] = None,
	  /** The ID of the Selection Field Choice that should be locked. If present, `field_id` must also be present. */
		choiceId: Option[String] = None,
	  /** Output only. The user's capabilities on this LabelLock. */
		capabilities: Option[Schema.GoogleAppsDriveLabelsV2LabelLockCapabilities] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldListOptions(
	  /** Maximum number of entries permitted. */
		maxEntries: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeleteLabelPermissionRequest(
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None,
	  /** Required. Label Permission resource name. */
		name: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptions(
	  /** When specified, indicates this field supports a list of values. Once the field is published, this cannot be changed. */
		listOptions: Option[Schema.GoogleAppsDriveLabelsV2FieldListOptions] = None,
	  /** The options available for this selection field. The list order is consistent, and modified with `insert_before_choice`. */
		choices: Option[List[Schema.GoogleAppsDriveLabelsV2FieldSelectionOptionsChoice]] = None
	)
	
	case class GoogleTypeColor(
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	case class GoogleAppsDriveLabelsV2WriteControl(
	  /** The revision_id of the label that the write request will be applied to. If this is not the latest revision of the label, the request will not be processed and will return a 400 Bad Request error. */
		requiredRevisionId: Option[String] = None
	)
	
	case class GoogleAppsDriveLabelsV2LifecycleDisabledPolicy(
	  /** Whether to show this disabled object in the apply menu on Drive items. &#42; When `true`, the object is generally shown in the UI as disabled and is unselectable. &#42; When `false`, the object is generally hidden in the UI. */
		showInApply: Option[Boolean] = None,
	  /** Whether to hide this disabled object in the search menu for Drive items. &#42; When `false`, the object is generally shown in the UI as disabled but it appears in the search results when searching for Drive items. &#42; When `true`, the object is generally hidden in the UI when searching for Drive items. */
		hideInSearch: Option[Boolean] = None
	)
	
	object GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy {
		enum CopyModeEnum extends Enum[CopyModeEnum] { case COPY_MODE_UNSPECIFIED, DO_NOT_COPY, ALWAYS_COPY, COPY_APPLIABLE }
	}
	case class GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy(
	  /** Indicates how the applied label and field values should be copied when a Drive item is copied. */
		copyMode: Option[Schema.GoogleAppsDriveLabelsV2LabelAppliedLabelPolicy.CopyModeEnum] = None
	)
	
	case class GoogleAppsDriveLabelsV2UpdateLabelPermissionRequest(
	  /** Required. The permission to create or update on the Label. */
		labelPermission: Option[Schema.GoogleAppsDriveLabelsV2LabelPermission] = None,
	  /** Required. The parent Label resource name. */
		parent: Option[String] = None,
	  /** Set to `true` in order to use the user's admin credentials. The server will verify the user is an admin for the Label before allowing access. */
		useAdminAccess: Option[Boolean] = None
	)
	
	case class GoogleTypeDate(
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleAppsDriveLabelsV2DeltaUpdateLabelResponseUpdateFieldTypeResponse(
	
	)
	
	case class GoogleAppsDriveLabelsV2FieldSelectionOptionsChoiceDisplayHints(
	  /** This option should be hidden in the search menu when searching for Drive items. */
		hiddenInSearch: Option[Boolean] = None,
	  /** Whether the option should be shown in the UI as disabled. */
		disabled: Option[Boolean] = None,
	  /** The dark-mode color to use for the badge. Changed to Google Material colors based on the chosen `properties.badge_config.color`. */
		darkBadgeColors: Option[Schema.GoogleAppsDriveLabelsV2BadgeColors] = None,
	  /** The colors to use for the badge. Changed to Google Material colors based on the chosen `properties.badge_config.color`. */
		badgeColors: Option[Schema.GoogleAppsDriveLabelsV2BadgeColors] = None,
	  /** The priority of this badge. Used to compare and sort between multiple badges. A lower number means the badge should be shown first. When a badging configuration is not present, this will be 0. Otherwise, this will be set to `BadgeConfig.priority_override` or the default heuristic which prefers creation date of the label, and field and option priority. */
		badgePriority: Option[String] = None,
	  /** This option should be shown in the apply menu when applying values to a Drive item. */
		shownInApply: Option[Boolean] = None
	)
	
	case class GoogleAppsDriveLabelsV2IntegerLimits(
	  /** Maximum value for an integer Field type. */
		maxValue: Option[String] = None,
	  /** Minimum value for an integer Field type. */
		minValue: Option[String] = None
	)
}
