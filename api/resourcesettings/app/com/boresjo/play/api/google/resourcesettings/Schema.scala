package com.boresjo.play.api.google.resourcesettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudResourcesettingsV1ValueStringSet(
	  /** The strings in the set */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudResourcesettingsV1ValueStringMap(
	  /** The key-value pairs in the map */
		mappings: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudResourcesettingsV1ValueEnumValue(
	  /** The value of this enum */
		value: Option[String] = None
	)
	
	case class GoogleCloudResourcesettingsV1Value(
	  /** Defines this value as being a StringSet. */
		stringSetValue: Option[Schema.GoogleCloudResourcesettingsV1ValueStringSet] = None,
	  /** Defines this value as being a string value. */
		stringValue: Option[String] = None,
	  /** Defines this value as being a StringMap. */
		stringMapValue: Option[Schema.GoogleCloudResourcesettingsV1ValueStringMap] = None,
	  /** Defines this value as being a Duration. */
		durationValue: Option[String] = None,
	  /** Defines this value as being a Enum. */
		enumValue: Option[Schema.GoogleCloudResourcesettingsV1ValueEnumValue] = None,
	  /** Defines this value as being a boolean value. */
		booleanValue: Option[Boolean] = None
	)
	
	case class GoogleCloudResourcesettingsV1ListSettingsResponse(
	  /** Unused. A page token used to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** A list of settings that are available at the specified Cloud resource. */
		settings: Option[List[Schema.GoogleCloudResourcesettingsV1Setting]] = None
	)
	
	case class GoogleCloudResourcesettingsV1Setting(
	  /** The resource name of the setting. Must be in one of the following forms: &#42; `projects/{project_number}/settings/{setting_name}` &#42; `folders/{folder_id}/settings/{setting_name}` &#42; `organizations/{organization_id}/settings/{setting_name}` For example, "/projects/123/settings/gcp-enableMyFeature" */
		name: Option[String] = None,
	  /** The configured value of the setting at the given parent resource, ignoring the resource hierarchy. The data type of Value must always be consistent with the data type defined in Setting.metadata. */
		localValue: Option[Schema.GoogleCloudResourcesettingsV1Value] = None,
	  /** Output only. The effective value of the setting at the given parent resource, evaluated based on the resource hierarchy The effective value evaluates to one of the following options, in this order. If an option is not valid or doesn't exist, then the next option is used: 1. The local setting value on the given resource: Setting.local_value 2. If one of the given resource's ancestors in the resource hierarchy have a local setting value, the local value at the nearest such ancestor. 3. The setting's default value: SettingMetadata.default_value 4. An empty value, defined as a `Value` with all fields unset. The data type of Value must always be consistent with the data type defined in Setting.metadata. */
		effectiveValue: Option[Schema.GoogleCloudResourcesettingsV1Value] = None,
	  /** Output only. Metadata about a setting which is not editable by the end user. */
		metadata: Option[Schema.GoogleCloudResourcesettingsV1SettingMetadata] = None,
	  /** A fingerprint used for optimistic concurrency. See UpdateSetting for more details. */
		etag: Option[String] = None
	)
	
	object GoogleCloudResourcesettingsV1SettingMetadata {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, BOOLEAN, STRING, STRING_SET, ENUM_VALUE, DURATION_VALUE, STRING_MAP }
	}
	case class GoogleCloudResourcesettingsV1SettingMetadata(
	  /** A detailed description of what this setting does. */
		description: Option[String] = None,
	  /** The value provided by Setting.effective_value if no setting value is explicitly set. Note: not all settings have a default value. */
		defaultValue: Option[Schema.GoogleCloudResourcesettingsV1Value] = None,
	  /** A flag indicating that values of this setting cannot be modified. See documentation for the specific setting for updates and reasons. */
		readOnly: Option[Boolean] = None,
	  /** The data type for this setting. */
		dataType: Option[Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum] = None,
	  /** The human readable name for this setting. */
		displayName: Option[String] = None
	)
}
