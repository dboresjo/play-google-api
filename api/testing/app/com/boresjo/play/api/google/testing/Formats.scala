package com.boresjo.play.api.google.testing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTestMatrix: Format[Schema.TestMatrix] = Json.format[Schema.TestMatrix]
	given fmtClientInfo: Format[Schema.ClientInfo] = Json.format[Schema.ClientInfo]
	given fmtTestSpecification: Format[Schema.TestSpecification] = Json.format[Schema.TestSpecification]
	given fmtEnvironmentMatrix: Format[Schema.EnvironmentMatrix] = Json.format[Schema.EnvironmentMatrix]
	given fmtTestExecution: Format[Schema.TestExecution] = Json.format[Schema.TestExecution]
	given fmtResultStorage: Format[Schema.ResultStorage] = Json.format[Schema.ResultStorage]
	given fmtTestMatrixStateEnum: Format[Schema.TestMatrix.StateEnum] = JsonEnumFormat[Schema.TestMatrix.StateEnum]
	given fmtTestMatrixInvalidMatrixDetailsEnum: Format[Schema.TestMatrix.InvalidMatrixDetailsEnum] = JsonEnumFormat[Schema.TestMatrix.InvalidMatrixDetailsEnum]
	given fmtMatrixErrorDetail: Format[Schema.MatrixErrorDetail] = Json.format[Schema.MatrixErrorDetail]
	given fmtTestMatrixOutcomeSummaryEnum: Format[Schema.TestMatrix.OutcomeSummaryEnum] = JsonEnumFormat[Schema.TestMatrix.OutcomeSummaryEnum]
	given fmtClientInfoDetail: Format[Schema.ClientInfoDetail] = Json.format[Schema.ClientInfoDetail]
	given fmtTestSetup: Format[Schema.TestSetup] = Json.format[Schema.TestSetup]
	given fmtIosTestSetup: Format[Schema.IosTestSetup] = Json.format[Schema.IosTestSetup]
	given fmtAndroidInstrumentationTest: Format[Schema.AndroidInstrumentationTest] = Json.format[Schema.AndroidInstrumentationTest]
	given fmtAndroidRoboTest: Format[Schema.AndroidRoboTest] = Json.format[Schema.AndroidRoboTest]
	given fmtAndroidTestLoop: Format[Schema.AndroidTestLoop] = Json.format[Schema.AndroidTestLoop]
	given fmtIosXcTest: Format[Schema.IosXcTest] = Json.format[Schema.IosXcTest]
	given fmtIosTestLoop: Format[Schema.IosTestLoop] = Json.format[Schema.IosTestLoop]
	given fmtIosRoboTest: Format[Schema.IosRoboTest] = Json.format[Schema.IosRoboTest]
	given fmtDeviceFile: Format[Schema.DeviceFile] = Json.format[Schema.DeviceFile]
	given fmtApk: Format[Schema.Apk] = Json.format[Schema.Apk]
	given fmtAccount: Format[Schema.Account] = Json.format[Schema.Account]
	given fmtEnvironmentVariable: Format[Schema.EnvironmentVariable] = Json.format[Schema.EnvironmentVariable]
	given fmtSystraceSetup: Format[Schema.SystraceSetup] = Json.format[Schema.SystraceSetup]
	given fmtObbFile: Format[Schema.ObbFile] = Json.format[Schema.ObbFile]
	given fmtRegularFile: Format[Schema.RegularFile] = Json.format[Schema.RegularFile]
	given fmtFileReference: Format[Schema.FileReference] = Json.format[Schema.FileReference]
	given fmtGoogleAuto: Format[Schema.GoogleAuto] = Json.format[Schema.GoogleAuto]
	given fmtIosDeviceFile: Format[Schema.IosDeviceFile] = Json.format[Schema.IosDeviceFile]
	given fmtAppBundle: Format[Schema.AppBundle] = Json.format[Schema.AppBundle]
	given fmtAndroidInstrumentationTestOrchestratorOptionEnum: Format[Schema.AndroidInstrumentationTest.OrchestratorOptionEnum] = JsonEnumFormat[Schema.AndroidInstrumentationTest.OrchestratorOptionEnum]
	given fmtShardingOption: Format[Schema.ShardingOption] = Json.format[Schema.ShardingOption]
	given fmtUniformSharding: Format[Schema.UniformSharding] = Json.format[Schema.UniformSharding]
	given fmtManualSharding: Format[Schema.ManualSharding] = Json.format[Schema.ManualSharding]
	given fmtSmartSharding: Format[Schema.SmartSharding] = Json.format[Schema.SmartSharding]
	given fmtTestTargetsForShard: Format[Schema.TestTargetsForShard] = Json.format[Schema.TestTargetsForShard]
	given fmtRoboDirective: Format[Schema.RoboDirective] = Json.format[Schema.RoboDirective]
	given fmtAndroidRoboTestRoboModeEnum: Format[Schema.AndroidRoboTest.RoboModeEnum] = JsonEnumFormat[Schema.AndroidRoboTest.RoboModeEnum]
	given fmtRoboStartingIntent: Format[Schema.RoboStartingIntent] = Json.format[Schema.RoboStartingIntent]
	given fmtRoboDirectiveActionTypeEnum: Format[Schema.RoboDirective.ActionTypeEnum] = JsonEnumFormat[Schema.RoboDirective.ActionTypeEnum]
	given fmtLauncherActivityIntent: Format[Schema.LauncherActivityIntent] = Json.format[Schema.LauncherActivityIntent]
	given fmtStartActivityIntent: Format[Schema.StartActivityIntent] = Json.format[Schema.StartActivityIntent]
	given fmtNoActivityIntent: Format[Schema.NoActivityIntent] = Json.format[Schema.NoActivityIntent]
	given fmtAndroidMatrix: Format[Schema.AndroidMatrix] = Json.format[Schema.AndroidMatrix]
	given fmtAndroidDeviceList: Format[Schema.AndroidDeviceList] = Json.format[Schema.AndroidDeviceList]
	given fmtIosDeviceList: Format[Schema.IosDeviceList] = Json.format[Schema.IosDeviceList]
	given fmtAndroidDevice: Format[Schema.AndroidDevice] = Json.format[Schema.AndroidDevice]
	given fmtIosDevice: Format[Schema.IosDevice] = Json.format[Schema.IosDevice]
	given fmtShard: Format[Schema.Shard] = Json.format[Schema.Shard]
	given fmtEnvironment: Format[Schema.Environment] = Json.format[Schema.Environment]
	given fmtTestExecutionStateEnum: Format[Schema.TestExecution.StateEnum] = JsonEnumFormat[Schema.TestExecution.StateEnum]
	given fmtToolResultsStep: Format[Schema.ToolResultsStep] = Json.format[Schema.ToolResultsStep]
	given fmtTestDetails: Format[Schema.TestDetails] = Json.format[Schema.TestDetails]
	given fmtGoogleCloudStorage: Format[Schema.GoogleCloudStorage] = Json.format[Schema.GoogleCloudStorage]
	given fmtToolResultsHistory: Format[Schema.ToolResultsHistory] = Json.format[Schema.ToolResultsHistory]
	given fmtToolResultsExecution: Format[Schema.ToolResultsExecution] = Json.format[Schema.ToolResultsExecution]
	given fmtCancelTestMatrixResponse: Format[Schema.CancelTestMatrixResponse] = Json.format[Schema.CancelTestMatrixResponse]
	given fmtCancelTestMatrixResponseTestStateEnum: Format[Schema.CancelTestMatrixResponse.TestStateEnum] = JsonEnumFormat[Schema.CancelTestMatrixResponse.TestStateEnum]
	given fmtGetApkDetailsResponse: Format[Schema.GetApkDetailsResponse] = Json.format[Schema.GetApkDetailsResponse]
	given fmtApkDetail: Format[Schema.ApkDetail] = Json.format[Schema.ApkDetail]
	given fmtApkManifest: Format[Schema.ApkManifest] = Json.format[Schema.ApkManifest]
	given fmtIntentFilter: Format[Schema.IntentFilter] = Json.format[Schema.IntentFilter]
	given fmtUsesPermissionTag: Format[Schema.UsesPermissionTag] = Json.format[Schema.UsesPermissionTag]
	given fmtMetadata: Format[Schema.Metadata] = Json.format[Schema.Metadata]
	given fmtUsesFeature: Format[Schema.UsesFeature] = Json.format[Schema.UsesFeature]
	given fmtService: Format[Schema.Service] = Json.format[Schema.Service]
	given fmtDeviceSession: Format[Schema.DeviceSession] = Json.format[Schema.DeviceSession]
	given fmtDeviceSessionStateEnum: Format[Schema.DeviceSession.StateEnum] = JsonEnumFormat[Schema.DeviceSession.StateEnum]
	given fmtSessionStateEvent: Format[Schema.SessionStateEvent] = Json.format[Schema.SessionStateEvent]
	given fmtSessionStateEventSessionStateEnum: Format[Schema.SessionStateEvent.SessionStateEnum] = JsonEnumFormat[Schema.SessionStateEvent.SessionStateEnum]
	given fmtListDeviceSessionsResponse: Format[Schema.ListDeviceSessionsResponse] = Json.format[Schema.ListDeviceSessionsResponse]
	given fmtCancelDeviceSessionRequest: Format[Schema.CancelDeviceSessionRequest] = Json.format[Schema.CancelDeviceSessionRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtTestEnvironmentCatalog: Format[Schema.TestEnvironmentCatalog] = Json.format[Schema.TestEnvironmentCatalog]
	given fmtAndroidDeviceCatalog: Format[Schema.AndroidDeviceCatalog] = Json.format[Schema.AndroidDeviceCatalog]
	given fmtIosDeviceCatalog: Format[Schema.IosDeviceCatalog] = Json.format[Schema.IosDeviceCatalog]
	given fmtNetworkConfigurationCatalog: Format[Schema.NetworkConfigurationCatalog] = Json.format[Schema.NetworkConfigurationCatalog]
	given fmtProvidedSoftwareCatalog: Format[Schema.ProvidedSoftwareCatalog] = Json.format[Schema.ProvidedSoftwareCatalog]
	given fmtDeviceIpBlockCatalog: Format[Schema.DeviceIpBlockCatalog] = Json.format[Schema.DeviceIpBlockCatalog]
	given fmtAndroidModel: Format[Schema.AndroidModel] = Json.format[Schema.AndroidModel]
	given fmtAndroidVersion: Format[Schema.AndroidVersion] = Json.format[Schema.AndroidVersion]
	given fmtAndroidRuntimeConfiguration: Format[Schema.AndroidRuntimeConfiguration] = Json.format[Schema.AndroidRuntimeConfiguration]
	given fmtAndroidModelFormEnum: Format[Schema.AndroidModel.FormEnum] = JsonEnumFormat[Schema.AndroidModel.FormEnum]
	given fmtAndroidModelFormFactorEnum: Format[Schema.AndroidModel.FormFactorEnum] = JsonEnumFormat[Schema.AndroidModel.FormFactorEnum]
	given fmtPerAndroidVersionInfo: Format[Schema.PerAndroidVersionInfo] = Json.format[Schema.PerAndroidVersionInfo]
	given fmtLabInfo: Format[Schema.LabInfo] = Json.format[Schema.LabInfo]
	given fmtPerAndroidVersionInfoDeviceCapacityEnum: Format[Schema.PerAndroidVersionInfo.DeviceCapacityEnum] = JsonEnumFormat[Schema.PerAndroidVersionInfo.DeviceCapacityEnum]
	given fmtDirectAccessVersionInfo: Format[Schema.DirectAccessVersionInfo] = Json.format[Schema.DirectAccessVersionInfo]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtDistribution: Format[Schema.Distribution] = Json.format[Schema.Distribution]
	given fmtLocale: Format[Schema.Locale] = Json.format[Schema.Locale]
	given fmtOrientation: Format[Schema.Orientation] = Json.format[Schema.Orientation]
	given fmtIosModel: Format[Schema.IosModel] = Json.format[Schema.IosModel]
	given fmtIosVersion: Format[Schema.IosVersion] = Json.format[Schema.IosVersion]
	given fmtXcodeVersion: Format[Schema.XcodeVersion] = Json.format[Schema.XcodeVersion]
	given fmtIosRuntimeConfiguration: Format[Schema.IosRuntimeConfiguration] = Json.format[Schema.IosRuntimeConfiguration]
	given fmtIosModelFormFactorEnum: Format[Schema.IosModel.FormFactorEnum] = JsonEnumFormat[Schema.IosModel.FormFactorEnum]
	given fmtPerIosVersionInfo: Format[Schema.PerIosVersionInfo] = Json.format[Schema.PerIosVersionInfo]
	given fmtPerIosVersionInfoDeviceCapacityEnum: Format[Schema.PerIosVersionInfo.DeviceCapacityEnum] = JsonEnumFormat[Schema.PerIosVersionInfo.DeviceCapacityEnum]
	given fmtNetworkConfiguration: Format[Schema.NetworkConfiguration] = Json.format[Schema.NetworkConfiguration]
	given fmtTrafficRule: Format[Schema.TrafficRule] = Json.format[Schema.TrafficRule]
	given fmtDeviceIpBlock: Format[Schema.DeviceIpBlock] = Json.format[Schema.DeviceIpBlock]
	given fmtDeviceIpBlockFormEnum: Format[Schema.DeviceIpBlock.FormEnum] = JsonEnumFormat[Schema.DeviceIpBlock.FormEnum]
}
