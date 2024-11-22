package com.boresjo.play.api.google.resourcesettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudResourcesettingsV1ValueStringSet: Format[Schema.GoogleCloudResourcesettingsV1ValueStringSet] = Json.format[Schema.GoogleCloudResourcesettingsV1ValueStringSet]
	given fmtGoogleCloudResourcesettingsV1ValueStringMap: Format[Schema.GoogleCloudResourcesettingsV1ValueStringMap] = Json.format[Schema.GoogleCloudResourcesettingsV1ValueStringMap]
	given fmtGoogleCloudResourcesettingsV1ValueEnumValue: Format[Schema.GoogleCloudResourcesettingsV1ValueEnumValue] = Json.format[Schema.GoogleCloudResourcesettingsV1ValueEnumValue]
	given fmtGoogleCloudResourcesettingsV1Value: Format[Schema.GoogleCloudResourcesettingsV1Value] = Json.format[Schema.GoogleCloudResourcesettingsV1Value]
	given fmtGoogleCloudResourcesettingsV1ListSettingsResponse: Format[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse] = Json.format[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]
	given fmtGoogleCloudResourcesettingsV1Setting: Format[Schema.GoogleCloudResourcesettingsV1Setting] = Json.format[Schema.GoogleCloudResourcesettingsV1Setting]
	given fmtGoogleCloudResourcesettingsV1SettingMetadata: Format[Schema.GoogleCloudResourcesettingsV1SettingMetadata] = Json.format[Schema.GoogleCloudResourcesettingsV1SettingMetadata]
	given fmtGoogleCloudResourcesettingsV1SettingMetadataDataTypeEnum: Format[Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum] = JsonEnumFormat[Schema.GoogleCloudResourcesettingsV1SettingMetadata.DataTypeEnum]
}
