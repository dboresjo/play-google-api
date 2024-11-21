package com.boresjo.play.api.google.chromemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleChromeManagementV1OsUpdateStatus {
		enum UpdateStateEnum extends Enum[UpdateStateEnum] { case UPDATE_STATE_UNSPECIFIED, OS_IMAGE_DOWNLOAD_NOT_STARTED, OS_IMAGE_DOWNLOAD_IN_PROGRESS, OS_UPDATE_NEED_REBOOT }
	}
	case class GoogleChromeManagementV1OsUpdateStatus(
	  /** Output only. New platform version of the os image being downloaded and applied. It is only set when update status is OS_IMAGE_DOWNLOAD_IN_PROGRESS or OS_UPDATE_NEED_REBOOT. Note this could be a dummy "0.0.0.0" for OS_UPDATE_NEED_REBOOT status for some edge cases, e.g. update engine is restarted without a reboot. */
		newPlatformVersion: Option[String] = None,
	  /** Output only. Timestamp of the last successful update. */
		lastUpdateTime: Option[String] = None,
	  /** Output only. Timestamp of the last update check. */
		lastUpdateCheckTime: Option[String] = None,
	  /** Output only. New requested platform version from the pending updated kiosk app. */
		newRequestedPlatformVersion: Option[String] = None,
	  /** Output only. Current state of the os update. */
		updateState: Option[Schema.GoogleChromeManagementV1OsUpdateStatus.UpdateStateEnum] = None,
	  /** Output only. Timestamp of the last reboot. */
		lastRebootTime: Option[String] = None
	)
	
	object GoogleChromeManagementV1ChromeAppInfo {
		enum TypeEnum extends Enum[TypeEnum] { case ITEM_TYPE_UNSPECIFIED, EXTENSION, OTHERS }
	}
	case class GoogleChromeManagementV1ChromeAppInfo(
	  /** Output only. Whether the app or extension is a theme. */
		isTheme: Option[Boolean] = None,
	  /** Output only. Whether the app or extension is in a published state in the Chrome Web Store. */
		isCwsHosted: Option[Boolean] = None,
	  /** Output only. Every custom permission requested by the app. Version-specific field that will only be set when the requested app version is found. */
		permissions: Option[List[Schema.GoogleChromeManagementV1ChromeAppPermission]] = None,
	  /** Output only. Whether the app is only for Kiosk mode on ChromeOS devices */
		isKioskOnly: Option[Boolean] = None,
	  /** Output only. The app developer has enabled support for their app. Version-specific field that will only be set when the requested app version is found. */
		supportEnabled: Option[Boolean] = None,
	  /** Output only. Whether an app supports policy for extensions. */
		isExtensionPolicySupported: Option[Boolean] = None,
	  /** Output only. Types of an item in the Chrome Web Store */
		`type`: Option[Schema.GoogleChromeManagementV1ChromeAppInfo.TypeEnum] = None,
	  /** Output only. Every permission giving access to domains or broad host patterns. ( e.g. www.google.com). This includes the matches from content scripts as well as hosts in the permissions node of the manifest. Version-specific field that will only be set when the requested app version is found. */
		siteAccess: Option[List[Schema.GoogleChromeManagementV1ChromeAppSiteAccess]] = None,
	  /** Output only. Whether the app or extension is built and maintained by Google. Version-specific field that will only be set when the requested app version is found. */
		googleOwned: Option[Boolean] = None,
	  /** Output only. The minimum number of users using this app. */
		minUserCount: Option[Int] = None,
	  /** Output only. Whether this app is enabled for Kiosk mode on ChromeOS devices */
		kioskEnabled: Option[Boolean] = None
	)
	
	case class GoogleChromeManagementV1FindInstalledAppDevicesResponse(
	  /** Token to specify the next page of the request. */
		nextPageToken: Option[String] = None,
	  /** Total number of devices matching request. */
		totalSize: Option[Int] = None,
	  /** A list of devices which have the app installed. Sorted in ascending alphabetical order on the Device.machine field. */
		devices: Option[List[Schema.GoogleChromeManagementV1Device]] = None
	)
	
	case class GoogleChromeManagementV1CountChromeCrashEventsResponseCrashEventCount(
	  /** Date of the crash event. */
		date: Option[Schema.GoogleTypeDate] = None,
	  /** Total count of crash events. */
		count: Option[String] = None,
	  /** Browser version this is counting. */
		browserVersion: Option[String] = None
	)
	
	case class GoogleChromeManagementV1AndroidAppInfo(
	  /** Output only. Permissions requested by an Android app. */
		permissions: Option[List[Schema.GoogleChromeManagementV1AndroidAppPermission]] = None
	)
	
	case class GoogleChromeManagementV1CountChromeBrowsersNeedingAttentionResponse(
	  /** Number of browsers that have been recently enrolled */
		recentlyEnrolledCount: Option[String] = None,
	  /** Number of browsers that are pending an OS update */
		pendingBrowserUpdateCount: Option[String] = None,
	  /** Number of browsers that haven’t had any recent activity */
		noRecentActivityCount: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ListTelemetryUsersResponse(
	  /** Token to specify next page in the list. */
		nextPageToken: Option[String] = None,
	  /** Telemetry users returned in the response. */
		telemetryUsers: Option[List[Schema.GoogleChromeManagementV1TelemetryUser]] = None
	)
	
	case class GoogleChromeManagementV1StorageInfo(
	  /** The available space for user data storage in the device in bytes. */
		availableDiskBytes: Option[String] = None,
	  /** Information for disk volumes */
		volume: Option[List[Schema.GoogleChromeManagementV1StorageInfoDiskVolume]] = None,
	  /** The total space for user data storage in the device in bytes. */
		totalDiskBytes: Option[String] = None
	)
	
	object GoogleChromeManagementV1TotalMemoryEncryptionInfo {
		enum EncryptionStateEnum extends Enum[EncryptionStateEnum] { case MEMORY_ENCRYPTION_STATE_UNSPECIFIED, MEMORY_ENCRYPTION_STATE_UNKNOWN, MEMORY_ENCRYPTION_STATE_DISABLED, MEMORY_ENCRYPTION_STATE_TME, MEMORY_ENCRYPTION_STATE_MKTME }
		enum EncryptionAlgorithmEnum extends Enum[EncryptionAlgorithmEnum] { case MEMORY_ENCRYPTION_ALGORITHM_UNSPECIFIED, MEMORY_ENCRYPTION_ALGORITHM_UNKNOWN, MEMORY_ENCRYPTION_ALGORITHM_AES_XTS_128, MEMORY_ENCRYPTION_ALGORITHM_AES_XTS_256 }
	}
	case class GoogleChromeManagementV1TotalMemoryEncryptionInfo(
	  /** The state of memory encryption on the device. */
		encryptionState: Option[Schema.GoogleChromeManagementV1TotalMemoryEncryptionInfo.EncryptionStateEnum] = None,
	  /** The maximum number of keys that can be used for encryption. */
		maxKeys: Option[String] = None,
	  /** Memory encryption algorithm. */
		encryptionAlgorithm: Option[Schema.GoogleChromeManagementV1TotalMemoryEncryptionInfo.EncryptionAlgorithmEnum] = None,
	  /** The length of the encryption keys. */
		keyLength: Option[String] = None
	)
	
	case class GoogleChromeManagementV1TelemetryUserDevice(
	  /** Output only. Network bandwidth reports collected periodically sorted in a decreasing order of report_time. */
		networkBandwidthReport: Option[List[Schema.GoogleChromeManagementV1NetworkBandwidthReport]] = None,
	  /** Output only. Peripherals reports collected periodically sorted in a decreasing order of report_time. */
		peripheralsReport: Option[List[Schema.GoogleChromeManagementV1PeripheralsReport]] = None,
	  /** Output only. App reports collected periodically sorted in a decreasing order of report_time. */
		appReport: Option[List[Schema.GoogleChromeManagementV1AppReport]] = None,
	  /** Output only. Device activity reports collected periodically sorted in a decreasing order of report_time. */
		deviceActivityReport: Option[List[Schema.GoogleChromeManagementV1DeviceActivityReport]] = None,
	  /** The unique Directory API ID of the device. This value is the same as the Admin Console's Directory API ID in the ChromeOS Devices tab. */
		deviceId: Option[String] = None,
	  /** Output only. Audio reports collected periodically sorted in a decreasing order of report_time. */
		audioStatusReport: Option[List[Schema.GoogleChromeManagementV1AudioStatusReport]] = None
	)
	
	case class GoogleChromeManagementV1NetworkInfo(
	  /** Output only. List of network devices. */
		networkDevices: Option[List[Schema.GoogleChromeManagementV1NetworkDevice]] = None
	)
	
	case class GoogleChromeManagementV1StorageInfoDiskVolume(
	  /** Total storage space in bytes. */
		storageTotalBytes: Option[String] = None,
	  /** Free storage space in bytes. */
		storageFreeBytes: Option[String] = None,
	  /** Disk volume id. */
		volumeId: Option[String] = None
	)
	
	case class GoogleChromeManagementV1DeviceRequestingExtensionDetails(
	  /** Request justification as entered by the user. */
		justification: Option[String] = None,
	  /** The name of a device that has requested the extension. */
		deviceName: Option[String] = None
	)
	
	object GoogleChromeManagementV1DeviceActivityReport {
		enum DeviceActivityStateEnum extends Enum[DeviceActivityStateEnum] { case DEVICE_ACTIVITY_STATE_UNSPECIFIED, ACTIVE, IDLE, LOCKED }
	}
	case class GoogleChromeManagementV1DeviceActivityReport(
	  /** Output only. Device activity state. */
		deviceActivityState: Option[Schema.GoogleChromeManagementV1DeviceActivityReport.DeviceActivityStateEnum] = None,
	  /** Output only. Timestamp of when the report was collected. */
		reportTime: Option[String] = None
	)
	
	object GoogleChromeManagementV1RiskAssessmentData {
		enum OverallRiskLevelEnum extends Enum[OverallRiskLevelEnum] { case RISK_LEVEL_UNSPECIFIED, RISK_LEVEL_LOW, RISK_LEVEL_MEDIUM, RISK_LEVEL_HIGH }
	}
	case class GoogleChromeManagementV1RiskAssessmentData(
	  /** Individual risk assessments. */
		entries: Option[List[Schema.GoogleChromeManagementV1RiskAssessmentEntry]] = None,
	  /** Overall assessed risk level across all entries. This will be the highest risk level from all entries. */
		overallRiskLevel: Option[Schema.GoogleChromeManagementV1RiskAssessmentData.OverallRiskLevelEnum] = None
	)
	
	object GoogleChromeManagementV1ThunderboltInfo {
		enum SecurityLevelEnum extends Enum[SecurityLevelEnum] { case THUNDERBOLT_SECURITY_LEVEL_UNSPECIFIED, THUNDERBOLT_SECURITY_NONE_LEVEL, THUNDERBOLT_SECURITY_USER_LEVEL, THUNDERBOLT_SECURITY_SECURE_LEVEL, THUNDERBOLT_SECURITY_DP_ONLY_LEVEL, THUNDERBOLT_SECURITY_USB_ONLY_LEVEL, THUNDERBOLT_SECURITY_NO_PCIE_LEVEL }
	}
	case class GoogleChromeManagementV1ThunderboltInfo(
	  /** Security level of the Thunderbolt bus. */
		securityLevel: Option[Schema.GoogleChromeManagementV1ThunderboltInfo.SecurityLevelEnum] = None
	)
	
	case class GoogleChromeManagementV1CountChromeAppRequestsResponse(
	  /** Count of requested apps matching request. */
		requestedApps: Option[List[Schema.GoogleChromeManagementV1ChromeAppRequest]] = None,
	  /** Token to specify the next page in the list. */
		nextPageToken: Option[String] = None,
	  /** Total number of matching app requests. */
		totalSize: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1UsbPeripheralReport(
	  /** Output only. Vendor name */
		vendor: Option[String] = None,
	  /** Output only. Firmware version */
		firmwareVersion: Option[String] = None,
	  /** Output only. Device name, model name, or product name */
		name: Option[String] = None,
	  /** Output only. Subclass ID https://www.usb.org/defined-class-codes */
		subclassId: Option[Int] = None,
	  /** Output only. Vendor ID */
		vid: Option[Int] = None,
	  /** Output only. Categories the device belongs to https://www.usb.org/defined-class-codes */
		categories: Option[List[String]] = None,
	  /** Output only. Class ID https://www.usb.org/defined-class-codes */
		classId: Option[Int] = None,
	  /** Output only. Product ID */
		pid: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1TelemetryAudioSevereUnderrunEvent(
	
	)
	
	case class GoogleChromeManagementV1DisplayInfo(
	  /** Output only. Resolution width in pixels. */
		resolutionWidth: Option[Int] = None,
	  /** Output only. Represents the graphics card device id. */
		deviceId: Option[String] = None,
	  /** Output only. Resolution height in pixels. */
		resolutionHeight: Option[Int] = None,
	  /** Output only. Indicates if display is internal or not. */
		isInternal: Option[Boolean] = None,
	  /** Output only. Display device name. */
		displayName: Option[String] = None,
	  /** Output only. Refresh rate in Hz. */
		refreshRate: Option[Int] = None
	)
	
	object GoogleChromeManagementV1TelemetryHttpsLatencyChangeEvent {
		enum HttpsLatencyStateEnum extends Enum[HttpsLatencyStateEnum] { case HTTPS_LATENCY_STATE_UNSPECIFIED, RECOVERY, PROBLEM }
	}
	case class GoogleChromeManagementV1TelemetryHttpsLatencyChangeEvent(
	  /** HTTPS latency routine data that triggered the event. */
		httpsLatencyRoutineData: Option[Schema.GoogleChromeManagementV1HttpsLatencyRoutineData] = None,
	  /** Current HTTPS latency state. */
		httpsLatencyState: Option[Schema.GoogleChromeManagementV1TelemetryHttpsLatencyChangeEvent.HttpsLatencyStateEnum] = None
	)
	
	case class GoogleChromeManagementV1DisplayDevice(
	  /** Output only. Three letter manufacturer ID. */
		manufacturerId: Option[String] = None,
	  /** Output only. Display height in millimeters. */
		displayHeightMm: Option[Int] = None,
	  /** Output only. Display device name. */
		displayName: Option[String] = None,
	  /** Output only. Display width in millimeters. */
		displayWidthMm: Option[Int] = None,
	  /** Output only. Year of manufacture. */
		manufactureYear: Option[Int] = None,
	  /** Output only. Manufacturer product code. */
		modelId: Option[Int] = None,
	  /** Output only. Is display internal or not. */
		internal: Option[Boolean] = None
	)
	
	case class GoogleChromeManagementV1CountInstalledAppsResponse(
	  /** List of installed apps matching request. */
		installedApps: Option[List[Schema.GoogleChromeManagementV1InstalledApp]] = None,
	  /** Token to specify the next page of the request. */
		nextPageToken: Option[String] = None,
	  /** Total number of installed apps matching request. */
		totalSize: Option[Int] = None
	)
	
	object GoogleChromeManagementV1AppDetails {
		enum TypeEnum extends Enum[TypeEnum] { case APP_ITEM_TYPE_UNSPECIFIED, CHROME, ANDROID, WEB }
	}
	case class GoogleChromeManagementV1AppDetails(
	  /** Output only. App type. */
		`type`: Option[Schema.GoogleChromeManagementV1AppDetails.TypeEnum] = None,
	  /** Output only. The publisher of the item. */
		publisher: Option[String] = None,
	  /** Output only. The uri for the detail page of the item. */
		detailUri: Option[String] = None,
	  /** Output only. Android app information. */
		androidAppInfo: Option[Schema.GoogleChromeManagementV1AndroidAppInfo] = None,
	  /** Output only. Indicates if the app has to be paid for OR has paid content. */
		isPaidApp: Option[Boolean] = None,
	  /** Output only. Information about a partial service error if applicable. */
		serviceError: Option[Schema.GoogleRpcStatus] = None,
	  /** Output only. The rating of the app (on 5 stars). Chrome Web Store review information will always be for the latest version of an app. */
		reviewRating: Option[BigDecimal] = None,
	  /** Output only. Number of reviews received. Chrome Web Store review information will always be for the latest version of an app. */
		reviewNumber: Option[String] = None,
	  /** Output only. App version. A new revision is committed whenever a new version of the app is published. */
		revisionId: Option[String] = None,
	  /** Output only. Home page or Website uri. */
		homepageUri: Option[String] = None,
	  /** Output only. Unique store identifier for the item. Examples: "gmbmikajjgmnabiglmofipeabaddhgne" for the Save to Google Drive Chrome extension, "com.google.android.apps.docs" for the Google Drive Android app. */
		appId: Option[String] = None,
	  /** Output only. Format: name=customers/{customer_id}/apps/{chrome|android|web}/{app_id}@{version} */
		name: Option[String] = None,
	  /** Output only. First published time. */
		firstPublishTime: Option[String] = None,
	  /** Output only. App's description. */
		description: Option[String] = None,
	  /** Output only. Latest published time. */
		latestPublishTime: Option[String] = None,
	  /** Output only. A link to an image that can be used as an icon for the product. */
		iconUri: Option[String] = None,
	  /** Output only. Chrome Web Store app information. */
		chromeAppInfo: Option[Schema.GoogleChromeManagementV1ChromeAppInfo] = None,
	  /** Output only. The URI pointing to the privacy policy of the app, if it was provided by the developer. Version-specific field that will only be set when the requested app version is found. */
		privacyPolicyUri: Option[String] = None,
	  /** Output only. App's display name. */
		displayName: Option[String] = None
	)
	
	case class GoogleChromeManagementV1FetchDevicesRequestingExtensionResponse(
	  /** Details of devices that have requested the queried extension. */
		deviceDetails: Option[List[Schema.GoogleChromeManagementV1DeviceRequestingExtensionDetails]] = None,
	  /** Optional. Total number of devices in response. */
		totalSize: Option[Int] = None,
	  /** Optional. Token to specify the next page in the list. Token expires after 1 day. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleRpcStatus(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleChromeManagementV1UserPrintReport(
	  /** Number of chrome devices that have been used to initiate print jobs by the user. */
		deviceCount: Option[String] = None,
	  /** Number of printers used by the user. */
		printerCount: Option[String] = None,
	  /** The unique Directory API ID of the user. */
		userId: Option[String] = None,
	  /** Number of print jobs initiated by the user. */
		jobCount: Option[String] = None,
	  /** The primary e-mail address of the user. */
		userEmail: Option[String] = None
	)
	
	case class GoogleChromeManagementV1GraphicsInfo(
	  /** Output only. Is ePrivacy screen supported or not. */
		eprivacySupported: Option[Boolean] = None,
	  /** Output only. Information about the internal touch screen(s) of the device. */
		touchScreenInfo: Option[Schema.GoogleChromeManagementV1TouchScreenInfo] = None,
	  /** Output only. Information about the display(s) of the device. */
		displayDevices: Option[List[Schema.GoogleChromeManagementV1DisplayDevice]] = None,
	  /** Output only. Information about the graphics adapter (GPU). */
		adapterInfo: Option[Schema.GoogleChromeManagementV1GraphicsAdapterInfo] = None
	)
	
	case class GoogleChromeManagementV1ChromeAppRequest(
	  /** Output only. Total count of requests for this app. */
		requestCount: Option[String] = None,
	  /** Output only. Unique store identifier for the app. Example: "gmbmikajjgmnabiglmofipeabaddhgne" for the Save to Google Drive Chrome extension. */
		appId: Option[String] = None,
	  /** Output only. A link to an image that can be used as an icon for the product. */
		iconUri: Option[String] = None,
	  /** Output only. The uri for the detail page of the item. */
		detailUri: Option[String] = None,
	  /** Output only. Format: app_details=customers/{customer_id}/apps/chrome/{app_id} */
		appDetails: Option[String] = None,
	  /** Output only. The timestamp of the most recently made request for this app. */
		latestRequestTime: Option[String] = None,
	  /** Output only. App's display name. */
		displayName: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ListTelemetryEventsResponse(
	  /** Telemetry events returned in the response. */
		telemetryEvents: Option[List[Schema.GoogleChromeManagementV1TelemetryEvent]] = None,
	  /** Token to specify next page in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleChromeManagementV1TelemetryNotificationConfig(
	  /** Output only. Resource name of the notification configuration. */
		name: Option[String] = None,
	  /** Output only. Google Workspace customer that owns the resource. */
		customer: Option[String] = None,
	  /** Only send notifications for telemetry data matching this filter. */
		filter: Option[Schema.GoogleChromeManagementV1TelemetryNotificationFilter] = None,
	  /** The pubsub topic to which notifications are published to. */
		googleCloudPubsubTopic: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ChromeAppPermission(
	  /** Output only. The type of the permission. */
		`type`: Option[String] = None,
	  /** Output only. If available, whether this permissions grants the app/extension access to user data. */
		accessUserData: Option[Boolean] = None,
	  /** Output only. If available, a URI to a page that has documentation for the current permission. */
		documentationUri: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CountPrintJobsByPrinterResponse(
	  /** List of PrinterReports matching request. */
		printerReports: Option[List[Schema.GoogleChromeManagementV1PrinterReport]] = None,
	  /** Total number of printers matching request. */
		totalSize: Option[String] = None,
	  /** Pagination token for requesting the next page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleChromeManagementV1AndroidAppPermission(
	  /** Output only. The type of the permission. */
		`type`: Option[String] = None
	)
	
	object GoogleChromeManagementV1NetworkStatusReport {
		enum ConnectionStateEnum extends Enum[ConnectionStateEnum] { case NETWORK_CONNECTION_STATE_UNSPECIFIED, ONLINE, CONNECTED, PORTAL, CONNECTING, NOT_CONNECTED }
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case NETWORK_TYPE_UNSPECIFIED, CELLULAR, ETHERNET, TETHER, VPN, WIFI }
	}
	case class GoogleChromeManagementV1NetworkStatusReport(
	  /** Output only. Transmission power measured in decibels. */
		transmissionPowerDbm: Option[Int] = None,
	  /** Output only. Current connection state of the network. */
		connectionState: Option[Schema.GoogleChromeManagementV1NetworkStatusReport.ConnectionStateEnum] = None,
	  /** Output only. Wifi link quality. Value ranges from [0, 70]. 0 indicates no signal and 70 indicates a strong signal. */
		wifiLinkQuality: Option[String] = None,
	  /** Output only. Signal strength for wireless networks measured in decibels. */
		signalStrengthDbm: Option[Int] = None,
	  /** Output only. Receiving bit rate measured in Megabits per second. */
		receivingBitRateMbps: Option[String] = None,
	  /** Output only. Network connection type. */
		connectionType: Option[Schema.GoogleChromeManagementV1NetworkStatusReport.ConnectionTypeEnum] = None,
	  /** Output only. Gateway IP address. */
		gatewayIpAddress: Option[String] = None,
	  /** Output only. Network connection guid. */
		guid: Option[String] = None,
	  /** Output only. Frequency the report is sampled. */
		sampleFrequency: Option[String] = None,
	  /** Output only. LAN IP address. */
		lanIpAddress: Option[String] = None,
	  /** Output only. Transmission bit rate measured in Megabits per second. */
		transmissionBitRateMbps: Option[String] = None,
	  /** Output only. Wifi power management enabled */
		wifiPowerManagementEnabled: Option[Boolean] = None,
	  /** Output only. Whether the wifi encryption key is turned off. */
		encryptionOn: Option[Boolean] = None,
	  /** Output only. Time at which the network state was reported. */
		reportTime: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ListTelemetryNotificationConfigsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The telemetry notification configs from the specified customer. */
		telemetryNotificationConfigs: Option[List[Schema.GoogleChromeManagementV1TelemetryNotificationConfig]] = None
	)
	
	object GoogleChromeManagementV1HeartbeatStatusReport {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNKNOWN, ONLINE, OFFLINE }
	}
	case class GoogleChromeManagementV1HeartbeatStatusReport(
	  /** State the device changed to */
		state: Option[Schema.GoogleChromeManagementV1HeartbeatStatusReport.StateEnum] = None,
	  /** Timestamp of when status changed was detected */
		reportTime: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CpuStatusReport(
	  /** Output only. Frequency the report is sampled. */
		sampleFrequency: Option[String] = None,
	  /** Output only. The timestamp in milliseconds representing time at which this report was sampled. */
		reportTime: Option[String] = None,
	  /** Output only. Sample of CPU utilization (0-100 percent). */
		cpuUtilizationPct: Option[Int] = None,
	  /** Output only. CPU temperature sample info per CPU core in Celsius */
		cpuTemperatureInfo: Option[List[Schema.GoogleChromeManagementV1CpuTemperatureInfo]] = None
	)
	
	case class GoogleChromeManagementV1StorageStatusReport(
	  /** Output only. Timestamp of when the sample was collected on device */
		reportTime: Option[String] = None,
	  /** Output only. Reports on disk. */
		disk: Option[List[Schema.GoogleChromeManagementV1DiskInfo]] = None
	)
	
	object GoogleChromeManagementV1PrintJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PRINTED, CANCELLED, FAILED }
		enum ColorModeEnum extends Enum[ColorModeEnum] { case COLOR_MODE_UNSPECIFIED, BLACK_AND_WHITE, COLOR }
		enum DuplexModeEnum extends Enum[DuplexModeEnum] { case DUPLEX_MODE_UNSPECIFIED, ONE_SIDED, TWO_SIDED_LONG_EDGE, TWO_SIDED_SHORT_EDGE }
	}
	case class GoogleChromeManagementV1PrintJob(
	  /** Number of pages in the document. */
		documentPageCount: Option[Int] = None,
	  /** Print job creation timestamp. */
		createTime: Option[String] = None,
	  /** The final state of the job. */
		state: Option[Schema.GoogleChromeManagementV1PrintJob.StateEnum] = None,
	  /** Unique ID of the print job. */
		id: Option[String] = None,
	  /** API ID of the printer used for printing. */
		printerId: Option[String] = None,
	  /** Color mode. */
		colorMode: Option[Schema.GoogleChromeManagementV1PrintJob.ColorModeEnum] = None,
	  /** Number of copies. */
		copyCount: Option[Int] = None,
	  /** The unique Directory API ID of the user who submitted the print job. */
		userId: Option[String] = None,
	  /** Print job completion timestamp. */
		completeTime: Option[String] = None,
	  /** Duplex mode. */
		duplexMode: Option[Schema.GoogleChromeManagementV1PrintJob.DuplexModeEnum] = None,
	  /** The primary e-mail address of the user who submitted the print job. */
		userEmail: Option[String] = None,
	  /** Name of the printer used for printing. */
		printer: Option[String] = None,
	  /** The title of the document. */
		title: Option[String] = None
	)
	
	object GoogleChromeManagementV1BootPerformanceReport {
		enum ShutdownReasonEnum extends Enum[ShutdownReasonEnum] { case SHUTDOWN_REASON_UNSPECIFIED, USER_REQUEST, SYSTEM_UPDATE, LOW_BATTERY, OTHER }
	}
	case class GoogleChromeManagementV1BootPerformanceReport(
	  /** Total time to boot up. */
		bootUpDuration: Option[String] = None,
	  /** Total time since shutdown start to power off. */
		shutdownDuration: Option[String] = None,
	  /** The shutdown reason. */
		shutdownReason: Option[Schema.GoogleChromeManagementV1BootPerformanceReport.ShutdownReasonEnum] = None,
	  /** Timestamp when the report was collected. */
		reportTime: Option[String] = None,
	  /** The timestamp when shutdown. */
		shutdownTime: Option[String] = None,
	  /** The timestamp when power came on. */
		bootUpTime: Option[String] = None
	)
	
	object GoogleChromeManagementV1TelemetryAppUninstallEvent {
		enum AppUninstallSourceEnum extends Enum[AppUninstallSourceEnum] { case APPLICATION_UNINSTALL_SOURCE_UNSPECIFIED, APPLICATION_UNINSTALL_SOURCE_APP_LIST, APPLICATION_UNINSTALL_SOURCE_APP_MANAGEMENT, APPLICATION_UNINSTALL_SOURCE_SHELF, APPLICATION_UNINSTALL_SOURCE_MIGRATION }
		enum AppTypeEnum extends Enum[AppTypeEnum] { case TELEMETRY_APPLICATION_TYPE_UNSPECIFIED, APPLICATION_TYPE_ARC, APPLICATION_TYPE_BUILT_IN, APPLICATION_TYPE_CROSTINI, APPLICATION_TYPE_CHROME_APP, APPLICATION_TYPE_WEB, APPLICATION_TYPE_MAC_OS, APPLICATION_TYPE_PLUGIN_VM, APPLICATION_TYPE_STANDALONE_BROWSER, APPLICATION_TYPE_REMOTE, APPLICATION_TYPE_BOREALIS, APPLICATION_TYPE_SYSTEM_WEB, APPLICATION_TYPE_STANDALONE_BROWSER_CHROME_APP, APPLICATION_TYPE_EXTENSION, APPLICATION_TYPE_STANDALONE_BROWSER_EXTENSION, APPLICATION_TYPE_BRUSCHETTA }
	}
	case class GoogleChromeManagementV1TelemetryAppUninstallEvent(
	  /** App id. For PWAs this is the start URL, and for extensions this is the extension id. */
		appId: Option[String] = None,
	  /** App uninstall source. */
		appUninstallSource: Option[Schema.GoogleChromeManagementV1TelemetryAppUninstallEvent.AppUninstallSourceEnum] = None,
	  /** Type of app. */
		appType: Option[Schema.GoogleChromeManagementV1TelemetryAppUninstallEvent.AppTypeEnum] = None
	)
	
	case class GoogleChromeManagementV1NetworkBandwidthReport(
	  /** Output only. Download speed in kilobits per second. */
		downloadSpeedKbps: Option[String] = None,
	  /** Output only. Timestamp of when the report was collected. */
		reportTime: Option[String] = None
	)
	
	case class GoogleChromeManagementV1PrinterReport(
	  /** Printer name. */
		printer: Option[String] = None,
	  /** Number of users that have sent print jobs to the printer. */
		userCount: Option[String] = None,
	  /** Printer model. */
		printerModel: Option[String] = None,
	  /** Number of chrome devices that have been used to send print jobs to the specified printer. */
		deviceCount: Option[String] = None,
	  /** Number of print jobs sent to the printer. */
		jobCount: Option[String] = None,
	  /** Printer API ID. */
		printerId: Option[String] = None
	)
	
	object GoogleChromeManagementV1HttpsLatencyRoutineData {
		enum ProblemEnum extends Enum[ProblemEnum] { case HTTPS_LATENCY_PROBLEM_UNSPECIFIED, FAILED_DNS_RESOLUTIONS, FAILED_HTTPS_REQUESTS, HIGH_LATENCY, VERY_HIGH_LATENCY }
	}
	case class GoogleChromeManagementV1HttpsLatencyRoutineData(
	  /** Output only. HTTPS latency if routine succeeded or failed because of HIGH_LATENCY or VERY_HIGH_LATENCY. */
		latency: Option[String] = None,
	  /** Output only. HTTPS latency routine problem if a problem occurred. */
		problem: Option[Schema.GoogleChromeManagementV1HttpsLatencyRoutineData.ProblemEnum] = None
	)
	
	case class GoogleChromeManagementV1TelemetryDevice(
	  /** Output only. Graphics reports collected periodically. */
		graphicsStatusReport: Option[List[Schema.GoogleChromeManagementV1GraphicsStatusReport]] = None,
	  /** Output only. Runtime counters reports collected device lifetime runtime, as well as the counts of S0->S3, S0->S4, and S0->S5 transitions, meaning entering into sleep, hibernation, and power-off states */
		runtimeCountersReport: Option[List[Schema.GoogleChromeManagementV1RuntimeCountersReport]] = None,
	  /** Output only. Boot performance reports of the device. */
		bootPerformanceReport: Option[List[Schema.GoogleChromeManagementV1BootPerformanceReport]] = None,
	  /** Output only. CPU status reports collected periodically sorted in a decreasing order of report_time. */
		cpuStatusReport: Option[List[Schema.GoogleChromeManagementV1CpuStatusReport]] = None,
	  /** Output only. Contains information regarding Graphic peripherals for the device. */
		graphicsInfo: Option[Schema.GoogleChromeManagementV1GraphicsInfo] = None,
	  /** Output only. Information regarding memory specs for the device. */
		memoryInfo: Option[Schema.GoogleChromeManagementV1MemoryInfo] = None,
	  /** Output only. Network specs collected periodically. */
		networkStatusReport: Option[List[Schema.GoogleChromeManagementV1NetworkStatusReport]] = None,
	  /** Output only. Google Workspace Customer whose enterprise enrolled the device. */
		customer: Option[String] = None,
	  /** Output only. Heartbeat status report containing timestamps periodically sorted in decreasing order of report_time */
		heartbeatStatusReport: Option[List[Schema.GoogleChromeManagementV1HeartbeatStatusReport]] = None,
	  /** Output only. Network devices information. */
		networkInfo: Option[Schema.GoogleChromeManagementV1NetworkInfo] = None,
	  /** Output only. Information on battery specs for the device. */
		batteryInfo: Option[List[Schema.GoogleChromeManagementV1BatteryInfo]] = None,
	  /** Output only. Battery reports collected periodically. */
		batteryStatusReport: Option[List[Schema.GoogleChromeManagementV1BatteryStatusReport]] = None,
	  /** Output only. Network bandwidth reports collected periodically sorted in a decreasing order of report_time. */
		networkBandwidthReport: Option[List[Schema.GoogleChromeManagementV1NetworkBandwidthReport]] = None,
	  /** Output only. Memory status reports collected periodically sorted decreasing by report_time. */
		memoryStatusReport: Option[List[Schema.GoogleChromeManagementV1MemoryStatusReport]] = None,
	  /** Output only. Information on Thunderbolt bus. */
		thunderboltInfo: Option[List[Schema.GoogleChromeManagementV1ThunderboltInfo]] = None,
	  /** Output only. Audio reports collected periodically sorted in a decreasing order of report_time. */
		audioStatusReport: Option[List[Schema.GoogleChromeManagementV1AudioStatusReport]] = None,
	  /** Output only. Contains relevant information regarding ChromeOS update status. */
		osUpdateStatus: Option[List[Schema.GoogleChromeManagementV1OsUpdateStatus]] = None,
	  /** Output only. Kiosk app status report for the kiosk device */
		kioskAppStatusReport: Option[List[Schema.GoogleChromeManagementV1KioskAppStatusReport]] = None,
	  /** Output only. Information of storage specs for the device. */
		storageInfo: Option[Schema.GoogleChromeManagementV1StorageInfo] = None,
	  /** Output only. Device serial number. This value is the same as the Admin Console's Serial Number in the ChromeOS Devices tab. */
		serialNumber: Option[String] = None,
	  /** Output only. Network diagnostics collected periodically. */
		networkDiagnosticsReport: Option[List[Schema.GoogleChromeManagementV1NetworkDiagnosticsReport]] = None,
	  /** Output only. Peripherals reports collected periodically sorted in a decreasing order of report_time. */
		peripheralsReport: Option[List[Schema.GoogleChromeManagementV1PeripheralsReport]] = None,
	  /** Output only. App reports collected periodically sorted in a decreasing order of report_time. */
		appReport: Option[List[Schema.GoogleChromeManagementV1AppReport]] = None,
	  /** Output only. Organization unit ID of the device. */
		orgUnitId: Option[String] = None,
	  /** Output only. Resource name of the device. */
		name: Option[String] = None,
	  /** Output only. Storage reports collected periodically. */
		storageStatusReport: Option[List[Schema.GoogleChromeManagementV1StorageStatusReport]] = None,
	  /** Output only. The unique Directory API ID of the device. This value is the same as the Admin Console's Directory API ID in the ChromeOS Devices tab */
		deviceId: Option[String] = None,
	  /** Output only. Information regarding CPU specs for the device. */
		cpuInfo: Option[List[Schema.GoogleChromeManagementV1CpuInfo]] = None
	)
	
	case class GoogleChromeManagementV1NetworkDiagnosticsReport(
	  /** Output only. HTTPS latency test data. */
		httpsLatencyData: Option[Schema.GoogleChromeManagementV1HttpsLatencyRoutineData] = None,
	  /** Output only. Timestamp of when the diagnostics were collected. */
		reportTime: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ListTelemetryDevicesResponse(
	  /** Telemetry devices returned in the response. */
		devices: Option[List[Schema.GoogleChromeManagementV1TelemetryDevice]] = None,
	  /** Token to specify next page in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleChromeManagementV1AppUsageData {
		enum AppTypeEnum extends Enum[AppTypeEnum] { case TELEMETRY_APPLICATION_TYPE_UNSPECIFIED, APPLICATION_TYPE_ARC, APPLICATION_TYPE_BUILT_IN, APPLICATION_TYPE_CROSTINI, APPLICATION_TYPE_CHROME_APP, APPLICATION_TYPE_WEB, APPLICATION_TYPE_MAC_OS, APPLICATION_TYPE_PLUGIN_VM, APPLICATION_TYPE_STANDALONE_BROWSER, APPLICATION_TYPE_REMOTE, APPLICATION_TYPE_BOREALIS, APPLICATION_TYPE_SYSTEM_WEB, APPLICATION_TYPE_STANDALONE_BROWSER_CHROME_APP, APPLICATION_TYPE_EXTENSION, APPLICATION_TYPE_STANDALONE_BROWSER_EXTENSION, APPLICATION_TYPE_BRUSCHETTA }
	}
	case class GoogleChromeManagementV1AppUsageData(
	  /** Type of app. */
		appType: Option[Schema.GoogleChromeManagementV1AppUsageData.AppTypeEnum] = None,
	  /** App id. */
		appId: Option[String] = None,
	  /** App foreground running time. */
		runningDuration: Option[String] = None,
	  /** Application instance id. This will be unique per window/instance. */
		appInstanceId: Option[String] = None
	)
	
	case class GoogleChromeManagementV1TelemetryNotificationFilter(
	  /** If set, only sends notifications for telemetry data coming from devices in this org unit. */
		deviceOrgUnitId: Option[String] = None,
	  /** If set, only sends notifications for telemetry data coming from devices owned by users in this org unit. */
		userOrgUnitId: Option[String] = None,
	  /** If set, only sends notifications for telemetry data coming from devices owned by this user. */
		userEmail: Option[String] = None,
	  /** Only sends notifications for the telemetry events matching this filter. */
		telemetryEventNotificationFilter: Option[Schema.GoogleChromeManagementV1TelemetryEventNotificationFilter] = None,
	  /** If set, only sends notifications for telemetry data coming from this device. */
		deviceId: Option[String] = None
	)
	
	case class GoogleChromeManagementV1GraphicsAdapterInfo(
	  /** Output only. Version of the GPU driver. */
		driverVersion: Option[String] = None,
	  /** Output only. Represents the graphics card device id. */
		deviceId: Option[String] = None,
	  /** Output only. Adapter name. Example: Mesa DRI Intel(R) UHD Graphics 620 (Kabylake GT2). */
		adapter: Option[String] = None
	)
	
	case class GoogleChromeManagementV1TelemetryNetworkSignalStrengthEvent(
	  /** Signal strength RSSI value. */
		signalStrengthDbm: Option[Int] = None,
	  /** Unique identifier of the network. */
		guid: Option[String] = None
	)
	
	object GoogleChromeManagementV1RiskAssessmentEntry {
		enum RiskLevelEnum extends Enum[RiskLevelEnum] { case RISK_LEVEL_UNSPECIFIED, RISK_LEVEL_LOW, RISK_LEVEL_MEDIUM, RISK_LEVEL_HIGH }
		enum ProviderEnum extends Enum[ProviderEnum] { case RISK_ASSESSMENT_PROVIDER_UNSPECIFIED, RISK_ASSESSMENT_PROVIDER_CRXCAVATOR, RISK_ASSESSMENT_PROVIDER_SPIN_AI }
	}
	case class GoogleChromeManagementV1RiskAssessmentEntry(
	  /** The bucketed risk level for the risk assessment. */
		riskLevel: Option[Schema.GoogleChromeManagementV1RiskAssessmentEntry.RiskLevelEnum] = None,
	  /** The details of the provider's risk assessment. */
		riskAssessment: Option[Schema.GoogleChromeManagementV1RiskAssessment] = None,
	  /** The risk assessment provider from which this entry comes from. */
		provider: Option[Schema.GoogleChromeManagementV1RiskAssessmentEntry.ProviderEnum] = None
	)
	
	case class GoogleChromeManagementV1TelemetryUser(
	  /** Telemetry data collected from a managed user and device. */
		userDevice: Option[List[Schema.GoogleChromeManagementV1TelemetryUserDevice]] = None,
	  /** Directory ID of the user. */
		userId: Option[String] = None,
	  /** Email address of the user. */
		userEmail: Option[String] = None,
	  /** Resource name of the user. */
		name: Option[String] = None,
	  /** G Suite Customer whose enterprise enrolled the device. */
		customer: Option[String] = None,
	  /** Organization unit of the user. */
		orgUnitId: Option[String] = None
	)
	
	object GoogleChromeManagementV1NetworkDevice {
		enum TypeEnum extends Enum[TypeEnum] { case NETWORK_DEVICE_TYPE_UNSPECIFIED, CELLULAR_DEVICE, ETHERNET_DEVICE, WIFI_DEVICE }
	}
	case class GoogleChromeManagementV1NetworkDevice(
	  /** Output only. The mobile directory number associated with the device's sim card. */
		mdn: Option[String] = None,
	  /** Output only. IMEI (if applicable) of the corresponding network device. */
		imei: Option[String] = None,
	  /** Output only. Network device type. */
		`type`: Option[Schema.GoogleChromeManagementV1NetworkDevice.TypeEnum] = None,
	  /** Output only. The integrated circuit card ID associated with the device's sim card. */
		iccid: Option[String] = None,
	  /** Output only. MAC address (if applicable) of the corresponding network device. */
		macAddress: Option[String] = None,
	  /** Output only. MEID (if applicable) of the corresponding network device. */
		meid: Option[String] = None
	)
	
	object GoogleChromeManagementV1BatteryStatusReport {
		enum BatteryHealthEnum extends Enum[BatteryHealthEnum] { case BATTERY_HEALTH_UNSPECIFIED, BATTERY_HEALTH_NORMAL, BATTERY_REPLACE_SOON, BATTERY_REPLACE_NOW }
	}
	case class GoogleChromeManagementV1BatteryStatusReport(
	  /** Output only. Timestamp of when the sample was collected on device */
		reportTime: Option[String] = None,
	  /** Output only. Battery serial number. */
		serialNumber: Option[String] = None,
	  /** Output only. Sampling data for the battery sorted in a decreasing order of report_time. */
		sample: Option[List[Schema.GoogleChromeManagementV1BatterySampleReport]] = None,
	  /** Output only. Full charge capacity (mAmpere-hours). */
		fullChargeCapacity: Option[String] = None,
	  /** Output only. Cycle count. */
		cycleCount: Option[Int] = None,
	  /** Output only. Battery health. */
		batteryHealth: Option[Schema.GoogleChromeManagementV1BatteryStatusReport.BatteryHealthEnum] = None
	)
	
	object GoogleChromeManagementV1InstalledApp {
		enum AppInstallTypeEnum extends Enum[AppInstallTypeEnum] { case APP_INSTALL_TYPE_UNSPECIFIED, MULTIPLE, NORMAL, ADMIN, DEVELOPMENT, SIDELOAD, OTHER }
		enum AppTypeEnum extends Enum[AppTypeEnum] { case APP_TYPE_UNSPECIFIED, EXTENSION, APP, THEME, HOSTED_APP, ANDROID_APP }
		enum AppSourceEnum extends Enum[AppSourceEnum] { case APP_SOURCE_UNSPECIFIED, CHROME_WEBSTORE, PLAY_STORE }
	}
	case class GoogleChromeManagementV1InstalledApp(
	  /** Output only. How the app was installed. */
		appInstallType: Option[Schema.GoogleChromeManagementV1InstalledApp.AppInstallTypeEnum] = None,
	  /** Output only. Name of the installed app. */
		displayName: Option[String] = None,
	  /** Output only. Unique identifier of the app. For Chrome apps and extensions, the 32-character id (e.g. ehoadneljpdggcbbknedodolkkjodefl). For Android apps, the package name (e.g. com.evernote). */
		appId: Option[String] = None,
	  /** Output only. Description of the installed app. */
		description: Option[String] = None,
	  /** Output only. Whether the app is disabled. */
		disabled: Option[Boolean] = None,
	  /** Output only. Homepage uri of the installed app. */
		homepageUri: Option[String] = None,
	  /** Output only. Count of browser devices with this app installed. */
		browserDeviceCount: Option[String] = None,
	  /** Output only. Type of the app. */
		appType: Option[Schema.GoogleChromeManagementV1InstalledApp.AppTypeEnum] = None,
	  /** Output only. Permissions of the installed app. */
		permissions: Option[List[String]] = None,
	  /** Output only. Source of the installed app. */
		appSource: Option[Schema.GoogleChromeManagementV1InstalledApp.AppSourceEnum] = None,
	  /** Output only. Count of ChromeOS users with this app installed. */
		osUserCount: Option[String] = None,
	  /** Output only. If available, the risk assessment data about this extension. */
		riskAssessment: Option[Schema.GoogleChromeManagementV1RiskAssessmentData] = None
	)
	
	case class GoogleChromeManagementV1EnumeratePrintJobsResponse(
	  /** List of requested print jobs. */
		printJobs: Option[List[Schema.GoogleChromeManagementV1PrintJob]] = None,
	  /** Total number of print jobs matching request. */
		totalSize: Option[String] = None,
	  /** A token, which can be used in a subsequent request to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CountChromeCrashEventsResponse(
	  /** Crash event counts grouped by date and browser version. */
		crashEventCounts: Option[List[Schema.GoogleChromeManagementV1CountChromeCrashEventsResponseCrashEventCount]] = None
	)
	
	case class GoogleChromeManagementV1MemoryStatusReport(
	  /** Output only. Frequency the report is sampled. */
		sampleFrequency: Option[String] = None,
	  /** Output only. The timestamp in milliseconds representing time at which this report was sampled. */
		reportTime: Option[String] = None,
	  /** Output only. Amount of free RAM in bytes (unreliable due to Garbage Collection). */
		systemRamFreeBytes: Option[String] = None,
	  /** Output only. Number of page faults during this collection */
		pageFaults: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1CountPrintJobsByUserResponse(
	  /** Total number of users matching request. */
		totalSize: Option[String] = None,
	  /** Pagination token for requesting the next page. */
		nextPageToken: Option[String] = None,
	  /** List of UserPrintReports matching request. */
		userPrintReports: Option[List[Schema.GoogleChromeManagementV1UserPrintReport]] = None
	)
	
	case class GoogleChromeManagementV1AudioStatusReport(
	  /** Output only. Is active input device mute or not. */
		inputMute: Option[Boolean] = None,
	  /** Output only. Active output device's volume in [0, 100]. */
		outputVolume: Option[Int] = None,
	  /** Output only. Active input device's name. */
		inputDevice: Option[String] = None,
	  /** Output only. Is active output device mute or not. */
		outputMute: Option[Boolean] = None,
	  /** Output only. Timestamp of when the sample was collected on device. */
		reportTime: Option[String] = None,
	  /** Output only. Active input device's gain in [0, 100]. */
		inputGain: Option[Int] = None,
	  /** Output only. Active output device's name. */
		outputDevice: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CountChromeDevicesThatNeedAttentionResponse(
	  /** Number of devices whose OS version is not compliant. */
		osVersionNotCompliantCount: Option[String] = None,
	  /** Number of devices that are unable to apply a policy due to an OS version mismatch. */
		unsupportedPolicyCount: Option[String] = None,
	  /** Number of ChromeOS devices that have not seen any user activity in the past 28 days. */
		noRecentUserActivityCount: Option[String] = None,
	  /** Number of ChromeOS devices have not synced policies in the past 28 days. */
		noRecentPolicySyncCount: Option[String] = None,
	  /** Number of devices that are pending an OS update. */
		pendingUpdate: Option[String] = None
	)
	
	case class GoogleChromeManagementV1RiskAssessment(
	  /** A URL that a user can navigate to for more information about the risk assessment. */
		detailsUrl: Option[String] = None,
	  /** Risk assessment for the extension. Currently, this is a numerical value, and its interpretation is specific to each risk assessment provider. */
		assessment: Option[String] = None,
	  /** The version of the extension that this assessment applies to. */
		version: Option[String] = None
	)
	
	case class GoogleChromeManagementV1ChromeAppSiteAccess(
	  /** Output only. This can contain very specific hosts, or patterns like "&#42;.com" for instance. */
		hostMatch: Option[String] = None
	)
	
	case class GoogleChromeManagementV1MemoryInfo(
	  /** Output only. Total RAM in bytes. */
		totalRamBytes: Option[String] = None,
	  /** Output only. Total memory encryption info for the device. */
		totalMemoryEncryption: Option[Schema.GoogleChromeManagementV1TotalMemoryEncryptionInfo] = None,
	  /** Output only. Amount of available RAM in bytes. */
		availableRamBytes: Option[String] = None
	)
	
	object GoogleChromeManagementV1TelemetryAppLaunchEvent {
		enum AppLaunchSourceEnum extends Enum[AppLaunchSourceEnum] { case APPLICATION_LAUNCH_SOURCE_UNSPECIFIED, APPLICATION_LAUNCH_SOURCE_APP_LIST_GRID, APPLICATION_LAUNCH_SOURCE_APP_LIST_GRID_CONTEXT_MENU, APPLICATION_LAUNCH_SOURCE_APP_LIST_QUERY, APPLICATION_LAUNCH_SOURCE_APP_LIST_QUERY_CONTEXT_MENU, APPLICATION_LAUNCH_SOURCE_APP_LIST_RECOMMENDATION, APPLICATION_LAUNCH_SOURCE_PARENTAL_CONTROLS, APPLICATION_LAUNCH_SOURCE_SHELF, APPLICATION_LAUNCH_SOURCE_FILE_MANAGER, APPLICATION_LAUNCH_SOURCE_LINK, APPLICATION_LAUNCH_SOURCE_OMNIBOX, APPLICATION_LAUNCH_SOURCE_CHROME_INTERNAL, APPLICATION_LAUNCH_SOURCE_KEYBOARD, APPLICATION_LAUNCH_SOURCE_OTHER_APP, APPLICATION_LAUNCH_SOURCE_MENU, APPLICATION_LAUNCH_SOURCE_INSTALLED_NOTIFICATION, APPLICATION_LAUNCH_SOURCE_TEST, APPLICATION_LAUNCH_SOURCE_ARC, APPLICATION_LAUNCH_SOURCE_SHARESHEET, APPLICATION_LAUNCH_SOURCE_RELEASE_NOTES_NOTIFICATION, APPLICATION_LAUNCH_SOURCE_FULL_RESTORE, APPLICATION_LAUNCH_SOURCE_SMART_TEXT_CONTEXT_MENU, APPLICATION_LAUNCH_SOURCE_DISCOVER_TAB_NOTIFICATION, APPLICATION_LAUNCH_SOURCE_MANAGEMENT_API, APPLICATION_LAUNCH_SOURCE_KIOSK, APPLICATION_LAUNCH_SOURCE_COMMAND_LINE, APPLICATION_LAUNCH_SOURCE_BACKGROUND_MODE, APPLICATION_LAUNCH_SOURCE_NEW_TAB_PAGE, APPLICATION_LAUNCH_SOURCE_INTENT_URL, APPLICATION_LAUNCH_SOURCE_OS_LOGIN, APPLICATION_LAUNCH_SOURCE_PROTOCOL_HANDLER, APPLICATION_LAUNCH_SOURCE_URL_HANDLER, APPLICATION_LAUNCH_SOURCE_LOCK_SCREEN, APPLICATION_LAUNCH_SOURCE_APP_HOME_PAGE, APPLICATION_LAUNCH_SOURCE_REPARENTING, APPLICATION_LAUNCH_SOURCE_PROFILE_MENU, APPLICATION_LAUNCH_SOURCE_SYSTEM_TRAY_CALENDAR, APPLICATION_LAUNCH_SOURCE_INSTALLER, APPLICATION_LAUNCH_SOURCE_FIRST_RUN, APPLICATION_LAUNCH_SOURCE_WELCOME_TOUR, APPLICATION_LAUNCH_SOURCE_FOCUS_MODE, APPLICATION_LAUNCH_SOURCE_SPARKY, APPLICATION_LAUNCH_SOURCE_NAVIGATION_CAPTURING, APPLICATION_LAUNCH_SOURCE_WEB_INSTALL_API }
		enum AppTypeEnum extends Enum[AppTypeEnum] { case TELEMETRY_APPLICATION_TYPE_UNSPECIFIED, APPLICATION_TYPE_ARC, APPLICATION_TYPE_BUILT_IN, APPLICATION_TYPE_CROSTINI, APPLICATION_TYPE_CHROME_APP, APPLICATION_TYPE_WEB, APPLICATION_TYPE_MAC_OS, APPLICATION_TYPE_PLUGIN_VM, APPLICATION_TYPE_STANDALONE_BROWSER, APPLICATION_TYPE_REMOTE, APPLICATION_TYPE_BOREALIS, APPLICATION_TYPE_SYSTEM_WEB, APPLICATION_TYPE_STANDALONE_BROWSER_CHROME_APP, APPLICATION_TYPE_EXTENSION, APPLICATION_TYPE_STANDALONE_BROWSER_EXTENSION, APPLICATION_TYPE_BRUSCHETTA }
	}
	case class GoogleChromeManagementV1TelemetryAppLaunchEvent(
	  /** App launch source. */
		appLaunchSource: Option[Schema.GoogleChromeManagementV1TelemetryAppLaunchEvent.AppLaunchSourceEnum] = None,
	  /** App id. For PWAs this is the start URL, and for extensions this is the extension id. */
		appId: Option[String] = None,
	  /** Type of app. */
		appType: Option[Schema.GoogleChromeManagementV1TelemetryAppLaunchEvent.AppTypeEnum] = None
	)
	
	case class GoogleChromeManagementV1DeviceHardwareCountReport(
	  /** Count of devices with a unique hardware specification. */
		count: Option[String] = None,
	  /** Public name of the hardware specification. */
		bucket: Option[String] = None
	)
	
	object GoogleChromeManagementV1DeviceAueCountReport {
		enum AueMonthEnum extends Enum[AueMonthEnum] { case MONTH_UNSPECIFIED, JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER }
	}
	case class GoogleChromeManagementV1DeviceAueCountReport(
	  /** Public model name of the devices. */
		model: Option[String] = None,
	  /** Enum value of month corresponding to the auto update expiration date in UTC time zone. If the device is already expired, this field is empty. */
		aueMonth: Option[Schema.GoogleChromeManagementV1DeviceAueCountReport.AueMonthEnum] = None,
	  /** Count of devices of this model. */
		count: Option[String] = None,
	  /** Boolean value for whether or not the device has already expired. */
		expired: Option[Boolean] = None,
	  /** Int value of year corresponding to the Auto Update Expiration date in UTC time zone. If the device is already expired, this field is empty. */
		aueYear: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CountChromeDevicesReachingAutoExpirationDateResponse(
	  /** The list of reports sorted by auto update expiration date in ascending order. */
		deviceAueCountReports: Option[List[Schema.GoogleChromeManagementV1DeviceAueCountReport]] = None
	)
	
	case class GoogleChromeManagementV1RuntimeCountersReport(
	  /** Timestamp when the report was collected. */
		reportTime: Option[String] = None,
	  /** Number of times that the device has entered into the sleep state. Currently obtained via the PSR, count from S0->S3. */
		enterSleepCount: Option[String] = None,
	  /** Number of times that the device has entered into the power-off state. Currently obtained via the PSR, count from S0->S5. */
		enterPoweroffCount: Option[String] = None,
	  /** Number of times that the device has entered into the hibernation state. Currently obtained via the PSR, count from S0->S4. */
		enterHibernationCount: Option[String] = None,
	  /** Total lifetime runtime. Currently always S0 runtime from Intel vPro PSR. */
		uptimeRuntimeDuration: Option[String] = None
	)
	
	case class GoogleChromeManagementV1PeripheralsReport(
	  /** Reports of all usb connected devices. */
		usbPeripheralReport: Option[List[Schema.GoogleChromeManagementV1UsbPeripheralReport]] = None,
	  /** Output only. Timestamp of when the report was collected. */
		reportTime: Option[String] = None
	)
	
	case class GoogleChromeManagementV1CountChromeVersionsResponse(
	  /** List of all browser versions and their install counts. */
		browserVersions: Option[List[Schema.GoogleChromeManagementV1BrowserVersion]] = None,
	  /** Total number browser versions matching request. */
		totalSize: Option[Int] = None,
	  /** Token to specify the next page of the request. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleChromeManagementV1TelemetryNetworkConnectionStateChangeEvent {
		enum ConnectionStateEnum extends Enum[ConnectionStateEnum] { case NETWORK_CONNECTION_STATE_UNSPECIFIED, ONLINE, CONNECTED, PORTAL, CONNECTING, NOT_CONNECTED }
	}
	case class GoogleChromeManagementV1TelemetryNetworkConnectionStateChangeEvent(
	  /** Current connection state of the network. */
		connectionState: Option[Schema.GoogleChromeManagementV1TelemetryNetworkConnectionStateChangeEvent.ConnectionStateEnum] = None,
	  /** Unique identifier of the network. */
		guid: Option[String] = None
	)
	
	case class GoogleChromeManagementV1BatteryInfo(
	  /** Output only. Design capacity (mAmpere-hours). */
		designCapacity: Option[String] = None,
	  /** Output only. Designed minimum output voltage (mV) */
		designMinVoltage: Option[Int] = None,
	  /** Output only. Battery serial number. */
		serialNumber: Option[String] = None,
	  /** Output only. Battery manufacturer. */
		manufacturer: Option[String] = None,
	  /** Output only. Technology of the battery. Example: Li-ion */
		technology: Option[String] = None,
	  /** Output only. The date the battery was manufactured. */
		manufactureDate: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleChromeManagementV1BatterySampleReport(
	  /** Output only. Battery charge percentage. */
		chargeRate: Option[Int] = None,
	  /** Output only. Battery status read from sysfs. Example: Discharging */
		status: Option[String] = None,
	  /** Output only. Timestamp of when the sample was collected on device */
		reportTime: Option[String] = None,
	  /** Output only. Battery current (mA). */
		current: Option[String] = None,
	  /** Output only. The battery discharge rate measured in mW. Positive if the battery is being discharged, negative if it's being charged. */
		dischargeRate: Option[Int] = None,
	  /** Output only. Battery voltage (millivolt). */
		voltage: Option[String] = None,
	  /** Output only. Temperature in Celsius degrees. */
		temperature: Option[Int] = None,
	  /** Output only. Battery remaining capacity (mAmpere-hours). */
		remainingCapacity: Option[String] = None
	)
	
	case class GoogleChromeManagementV1AppReport(
	  /** Timestamp when the report was collected. */
		reportTime: Option[String] = None,
	  /** App usage data. */
		usageData: Option[List[Schema.GoogleChromeManagementV1AppUsageData]] = None
	)
	
	case class GoogleChromeManagementV1GraphicsStatusReport(
	  /** Output only. Time at which the graphics data was reported. */
		reportTime: Option[String] = None,
	  /** Output only. Information about the displays for the device. */
		displays: Option[List[Schema.GoogleChromeManagementV1DisplayInfo]] = None
	)
	
	object GoogleChromeManagementV1BrowserVersion {
		enum SystemEnum extends Enum[SystemEnum] { case DEVICE_SYSTEM_UNSPECIFIED, SYSTEM_OTHER, SYSTEM_ANDROID, SYSTEM_IOS, SYSTEM_CROS, SYSTEM_WINDOWS, SYSTEM_MAC, SYSTEM_LINUX }
		enum ChannelEnum extends Enum[ChannelEnum] { case RELEASE_CHANNEL_UNSPECIFIED, CANARY, DEV, BETA, STABLE }
	}
	case class GoogleChromeManagementV1BrowserVersion(
	  /** Output only. Count grouped by device_system and major version */
		count: Option[String] = None,
	  /** Output only. The full version of the installed browser. */
		version: Option[String] = None,
	  /** Output only. Version of the system-specified operating system. */
		deviceOsVersion: Option[String] = None,
	  /** Output only. The device operating system. */
		system: Option[Schema.GoogleChromeManagementV1BrowserVersion.SystemEnum] = None,
	  /** Output only. The release channel of the installed browser. */
		channel: Option[Schema.GoogleChromeManagementV1BrowserVersion.ChannelEnum] = None
	)
	
	case class GoogleChromeManagementV1TouchScreenInfo(
	  /** Output only. List of the internal touch screen devices. */
		devices: Option[List[Schema.GoogleChromeManagementV1TouchScreenDevice]] = None,
	  /** Output only. Touchpad library name used by the input stack. */
		touchpadLibrary: Option[String] = None
	)
	
	object GoogleChromeManagementV1CpuInfo {
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case ARCHITECTURE_UNSPECIFIED, X64 }
	}
	case class GoogleChromeManagementV1CpuInfo(
	  /** Output only. The CPU model name. Example: Intel(R) Core(TM) i5-8250U CPU @ 1.60GHz */
		model: Option[String] = None,
	  /** Output only. Architecture type for the CPU. &#42; This field provides device information, which is static and will not change over time. &#42; Data for this field is controlled via policy: [ReportDeviceCpuInfo](https://chromeenterprise.google/policies/#ReportDeviceCpuInfo) &#42; Data Collection Frequency: Only at Upload &#42; Default Data Reporting Frequency: 3 hours - Policy Controlled: Yes &#42; Cache: If the device is offline, the collected data is stored locally, and will be reported when the device is next online: No &#42; Reported for affiliated users only: N/A */
		architecture: Option[Schema.GoogleChromeManagementV1CpuInfo.ArchitectureEnum] = None,
	  /** Output only. Whether keylocker is configured.`TRUE` = Enabled; `FALSE` = disabled. Only reported if keylockerSupported = `TRUE`. */
		keylockerConfigured: Option[Boolean] = None,
	  /** Output only. Whether keylocker is supported. */
		keylockerSupported: Option[Boolean] = None,
	  /** Output only. The max CPU clock speed in kHz. */
		maxClockSpeed: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1TelemetryUserInfo(
	  /** Output only. Organization unit ID of the user. */
		orgUnitId: Option[String] = None,
	  /** Output only. User's email. */
		email: Option[String] = None
	)
	
	case class GoogleChromeManagementV1FetchUsersRequestingExtensionResponse(
	  /** Token to specify the next page in the list. */
		nextPageToken: Option[String] = None,
	  /** Details of users that have requested the queried extension. */
		userDetails: Option[List[Schema.GoogleChromeManagementV1UserRequestingExtensionDetails]] = None,
	  /** Total number of users in response. */
		totalSize: Option[Int] = None
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1DiskInfo(
	  /** Output only. Number of bytes read since last boot. */
		bytesReadThisSession: Option[String] = None,
	  /** Output only. Number of bytes written since last boot. */
		bytesWrittenThisSession: Option[String] = None,
	  /** Output only. Counts the time the disk and queue were busy, so unlike the fields above, parallel requests are not counted multiple times. */
		ioTimeThisSession: Option[String] = None,
	  /** Output only. Disk volumes. */
		volumeIds: Option[List[String]] = None,
	  /** Output only. Time spent reading from disk since last boot. */
		readTimeThisSession: Option[String] = None,
	  /** Output only. Disk manufacturer. */
		manufacturer: Option[String] = None,
	  /** Output only. Disk health. */
		health: Option[String] = None,
	  /** Output only. Disk size. */
		sizeBytes: Option[String] = None,
	  /** Output only. Time spent discarding since last boot. Discarding is writing to clear blocks which are no longer in use. Supported on kernels 4.18+. */
		discardTimeThisSession: Option[String] = None,
	  /** Output only. Disk model. */
		model: Option[String] = None,
	  /** Output only. Time spent writing to disk since last boot. */
		writeTimeThisSession: Option[String] = None,
	  /** Output only. Disk serial number. */
		serialNumber: Option[String] = None,
	  /** Output only. Disk type: eMMC / NVMe / ATA / SCSI. */
		`type`: Option[String] = None
	)
	
	case class GoogleChromeManagementV1Device(
	  /** Output only. The name of the machine within its local network. */
		machine: Option[String] = None,
	  /** Output only. The ID of the device that reported this Chrome browser information. */
		deviceId: Option[String] = None
	)
	
	case class GoogleChromeManagementV1TelemetryUsbPeripheralsEvent(
	  /** List of usb devices that were either added or removed. */
		usbPeripheralReport: Option[List[Schema.GoogleChromeManagementV1UsbPeripheralReport]] = None
	)
	
	case class GoogleChromeManagementV1TouchScreenDevice(
	  /** Output only. Touch screen device is stylus capable or not. */
		stylusCapable: Option[Boolean] = None,
	  /** Output only. Touch screen device display name. */
		displayName: Option[String] = None,
	  /** Output only. Number of touch points supported on the device. */
		touchPointCount: Option[Int] = None
	)
	
	case class GoogleChromeManagementV1CountChromeHardwareFleetDevicesResponse(
	  /** The DeviceHardwareCountReport for device cpu type (for example Intel(R) Core(TM) i7-10610U CPU @ 1.80GHz). */
		cpuReports: Option[List[Schema.GoogleChromeManagementV1DeviceHardwareCountReport]] = None,
	  /** The DeviceHardwareCountReport for device memory amount in gigabytes (for example 16). */
		memoryReports: Option[List[Schema.GoogleChromeManagementV1DeviceHardwareCountReport]] = None,
	  /** The DeviceHardwareCountReport for device model type (for example Acer C7 Chromebook). */
		modelReports: Option[List[Schema.GoogleChromeManagementV1DeviceHardwareCountReport]] = None,
	  /** The DeviceHardwareCountReport for device storage amount in gigabytes (for example 128). */
		storageReports: Option[List[Schema.GoogleChromeManagementV1DeviceHardwareCountReport]] = None
	)
	
	case class GoogleChromeManagementV1CpuTemperatureInfo(
	  /** Output only. CPU temperature in Celsius. */
		temperatureCelsius: Option[Int] = None,
	  /** Output only. CPU label. Example: Core 0 */
		label: Option[String] = None
	)
	
	object GoogleChromeManagementV1TelemetryEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case EVENT_TYPE_UNSPECIFIED, AUDIO_SEVERE_UNDERRUN, NETWORK_STATE_CHANGE, USB_ADDED, USB_REMOVED, NETWORK_HTTPS_LATENCY_CHANGE, WIFI_SIGNAL_STRENGTH_LOW, WIFI_SIGNAL_STRENGTH_RECOVERED, VPN_CONNECTION_STATE_CHANGE, APP_INSTALLED, APP_UNINSTALLED, APP_LAUNCHED, OS_CRASH }
	}
	case class GoogleChromeManagementV1TelemetryEvent(
	  /** Output only. Payload for network connection state change event. Present only when `event_type` is `NETWORK_STATE_CHANGE`. */
		networkStateChangeEvent: Option[Schema.GoogleChromeManagementV1TelemetryNetworkConnectionStateChangeEvent] = None,
	  /** Output only. Payload for audio severe underrun event. Present only when the `event_type` field is `AUDIO_SEVERE_UNDERRUN`. */
		audioSevereUnderrunEvent: Option[Schema.GoogleChromeManagementV1TelemetryAudioSevereUnderrunEvent] = None,
	  /** Output only. Payload for HTTPS latency change event. Present only when `event_type` is `NETWORK_HTTPS_LATENCY_CHANGE`. */
		httpsLatencyChangeEvent: Option[Schema.GoogleChromeManagementV1TelemetryHttpsLatencyChangeEvent] = None,
	  /** Output only. Payload for usb peripherals event. Present only when the `event_type` field is either `USB_ADDED` or `USB_REMOVED`. */
		usbPeripheralsEvent: Option[Schema.GoogleChromeManagementV1TelemetryUsbPeripheralsEvent] = None,
	  /** Output only. Information about the device associated with the event. */
		device: Option[Schema.GoogleChromeManagementV1TelemetryDeviceInfo] = None,
	  /** Output only. Resource name of the event. */
		name: Option[String] = None,
	  /** Timestamp that represents when the event was reported. */
		reportTime: Option[String] = None,
	  /** Output only. Payload for app install event. Present only when `event_type` is `APP_INSTALLED`. */
		appInstallEvent: Option[Schema.GoogleChromeManagementV1TelemetryAppInstallEvent] = None,
	  /** Output only. Payload for app launch event.Present only when `event_type` is `APP_LAUNCHED`. */
		appLaunchEvent: Option[Schema.GoogleChromeManagementV1TelemetryAppLaunchEvent] = None,
	  /** Output only. Payload for VPN connection state change event. Present only when `event_type` is `VPN_CONNECTION_STATE_CHANGE`. */
		vpnConnectionStateChangeEvent: Option[Schema.GoogleChromeManagementV1TelemetryNetworkConnectionStateChangeEvent] = None,
	  /** Output only. Information about the user associated with the event. */
		user: Option[Schema.GoogleChromeManagementV1TelemetryUserInfo] = None,
	  /** Output only. Payload for app uninstall event. Present only when `event_type` is `APP_UNINSTALLED`. */
		appUninstallEvent: Option[Schema.GoogleChromeManagementV1TelemetryAppUninstallEvent] = None,
	  /** The event type of the current event. */
		eventType: Option[Schema.GoogleChromeManagementV1TelemetryEvent.EventTypeEnum] = None,
	  /** Output only. Payload for WiFi signal strength events. Present only when `event_type` is `WIFI_SIGNAL_STRENGTH_LOW` or `WIFI_SIGNAL_STRENGTH_RECOVERED`. */
		wifiSignalStrengthEvent: Option[Schema.GoogleChromeManagementV1TelemetryNetworkSignalStrengthEvent] = None
	)
	
	case class GoogleChromeManagementV1TelemetryDeviceInfo(
	  /** Output only. Organization unit ID of the device. */
		orgUnitId: Option[String] = None,
	  /** Output only. The unique Directory API ID of the device. This value is the same as the Admin Console's Directory API ID in the ChromeOS Devices tab. */
		deviceId: Option[String] = None
	)
	
	object GoogleChromeManagementV1TelemetryEventNotificationFilter {
		enum EventTypesEnum extends Enum[EventTypesEnum] { case EVENT_TYPE_UNSPECIFIED, AUDIO_SEVERE_UNDERRUN, NETWORK_STATE_CHANGE, USB_ADDED, USB_REMOVED, NETWORK_HTTPS_LATENCY_CHANGE, WIFI_SIGNAL_STRENGTH_LOW, WIFI_SIGNAL_STRENGTH_RECOVERED, VPN_CONNECTION_STATE_CHANGE, APP_INSTALLED, APP_UNINSTALLED, APP_LAUNCHED, OS_CRASH }
	}
	case class GoogleChromeManagementV1TelemetryEventNotificationFilter(
	  /** Only sends the notifications for events of these types. Must not be empty. */
		eventTypes: Option[List[Schema.GoogleChromeManagementV1TelemetryEventNotificationFilter.EventTypesEnum]] = None
	)
	
	case class GoogleChromeManagementV1KioskAppStatusReport(
	  /** App id of kiosk app for example "mdmkkicfmmkgmpkmkdikhlbggogpicma" */
		appId: Option[String] = None,
	  /** Timestamp of when report was collected */
		reportTime: Option[String] = None,
	  /** App version number of kiosk app for example "1.10.118" */
		appVersion: Option[String] = None
	)
	
	case class GoogleChromeManagementV1UserRequestingExtensionDetails(
	  /** Request justification as entered by the user. */
		justification: Option[String] = None,
	  /** The e-mail address of a user that has requested the extension. */
		email: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	object GoogleChromeManagementV1TelemetryAppInstallEvent {
		enum AppTypeEnum extends Enum[AppTypeEnum] { case TELEMETRY_APPLICATION_TYPE_UNSPECIFIED, APPLICATION_TYPE_ARC, APPLICATION_TYPE_BUILT_IN, APPLICATION_TYPE_CROSTINI, APPLICATION_TYPE_CHROME_APP, APPLICATION_TYPE_WEB, APPLICATION_TYPE_MAC_OS, APPLICATION_TYPE_PLUGIN_VM, APPLICATION_TYPE_STANDALONE_BROWSER, APPLICATION_TYPE_REMOTE, APPLICATION_TYPE_BOREALIS, APPLICATION_TYPE_SYSTEM_WEB, APPLICATION_TYPE_STANDALONE_BROWSER_CHROME_APP, APPLICATION_TYPE_EXTENSION, APPLICATION_TYPE_STANDALONE_BROWSER_EXTENSION, APPLICATION_TYPE_BRUSCHETTA }
		enum AppInstallSourceEnum extends Enum[AppInstallSourceEnum] { case APPLICATION_INSTALL_SOURCE_UNSPECIFIED, APPLICATION_INSTALL_SOURCE_SYSTEM, APPLICATION_INSTALL_SOURCE_SYNC, APPLICATION_INSTALL_SOURCE_PLAY_STORE, APPLICATION_INSTALL_SOURCE_CHROME_WEB_STORE, APPLICATION_INSTALL_SOURCE_BROWSER }
		enum AppInstallReasonEnum extends Enum[AppInstallReasonEnum] { case APPLICATION_INSTALL_REASON_UNSPECIFIED, APPLICATION_INSTALL_REASON_SYSTEM, APPLICATION_INSTALL_REASON_POLICY, APPLICATION_INSTALL_REASON_OEM, APPLICATION_INSTALL_REASON_DEFAULT, APPLICATION_INSTALL_REASON_SYNC, APPLICATION_INSTALL_REASON_USER, APPLICATION_INSTALL_REASON_SUB_APP, APPLICATION_INSTALL_REASON_KIOSK, APPLICATION_INSTALL_REASON_COMMAND_LINE }
		enum AppInstallTimeEnum extends Enum[AppInstallTimeEnum] { case APPLICATION_INSTALL_TIME_UNSPECIFIED, APPLICATION_INSTALL_TIME_INIT, APPLICATION_INSTALL_TIME_RUNNING }
	}
	case class GoogleChromeManagementV1TelemetryAppInstallEvent(
	  /** Type of app. */
		appType: Option[Schema.GoogleChromeManagementV1TelemetryAppInstallEvent.AppTypeEnum] = None,
	  /** App installation source. */
		appInstallSource: Option[Schema.GoogleChromeManagementV1TelemetryAppInstallEvent.AppInstallSourceEnum] = None,
	  /** App installation reason. */
		appInstallReason: Option[Schema.GoogleChromeManagementV1TelemetryAppInstallEvent.AppInstallReasonEnum] = None,
	  /** App installation time depending on the app lifecycle. */
		appInstallTime: Option[Schema.GoogleChromeManagementV1TelemetryAppInstallEvent.AppInstallTimeEnum] = None,
	  /** App id. For PWAs this is the start URL, and for extensions this is the extension id. */
		appId: Option[String] = None
	)
}
