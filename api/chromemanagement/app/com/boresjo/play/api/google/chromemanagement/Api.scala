package com.boresjo.play.api.google.chromemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://chromemanagement.googleapis.com/"

	object customers {
		object telemetry {
			object users {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1ListTelemetryUsersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1ListTelemetryUsersResponse])
				}
				object list {
					def apply(customersId :PlayApi, pageSize: Int, readMask: String, filter: String, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/users").addQueryStringParameters("pageSize" -> pageSize.toString, "readMask" -> readMask.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleChromeManagementV1ListTelemetryUsersResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1TelemetryUser]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1TelemetryUser])
				}
				object get {
					def apply(customersId :PlayApi, usersId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/users/${usersId}").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
					given Conversion[get, Future[Schema.GoogleChromeManagementV1TelemetryUser]] = (fun: get) => fun.apply()
				}
			}
			object notificationConfigs {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1ListTelemetryNotificationConfigsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1ListTelemetryNotificationConfigsResponse])
				}
				object list {
					def apply(customersId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/notificationConfigs").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleChromeManagementV1ListTelemetryNotificationConfigsResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(customersId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleChromeManagementV1TelemetryNotificationConfig(body: Schema.GoogleChromeManagementV1TelemetryNotificationConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChromeManagementV1TelemetryNotificationConfig])
				}
				object create {
					def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/notificationConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object events {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1ListTelemetryEventsResponse]) {
					/** Optional. Token to specify next page in the list. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Maximum number of results to return. Default value is 100. Maximum value is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Only include resources that match the filter. Although this parameter is currently optional, this parameter will be required- please specify at least 1 event type. Supported filter fields: - device_id - user_id - device_org_unit_id - user_org_unit_id - timestamp - event_type The "timestamp" filter accepts either the Unix Epoch milliseconds format or the RFC3339 UTC "Zulu" format with nanosecond resolution and up to nine fractional digits. Both formats should be surrounded by simple double quotes. Examples: "2014-10-02T15:01:23Z", "2014-10-02T15:01:23.045123456Z", "1679283943823". */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Required. Read mask to specify which fields to return. Although currently required, this field will become optional, while the filter parameter with an event type will be come required. Supported read_mask paths are: - device - user - audio_severe_underrun_event - usb_peripherals_event - https_latency_change_event - network_state_change_event - wifi_signal_strength_event - vpn_connection_state_change_event - app_install_event - app_uninstall_event - app_launch_event - os_crash_event <br>Format: google-fieldmask */
					def withReadMask(readMask: String) = new list(req.addQueryStringParameters("readMask" -> readMask.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1ListTelemetryEventsResponse])
				}
				object list {
					def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/events").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleChromeManagementV1ListTelemetryEventsResponse]] = (fun: list) => fun.apply()
				}
			}
			object devices {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1ListTelemetryDevicesResponse]) {
					/** Optional. Only include resources that match the filter. Requests that don't specify a "reports_timestamp" value will default to returning only recent reports. Specify "reports_timestamp>=0" to get all report data. Supported filter fields: - org_unit_id - serial_number - device_id - reports_timestamp The "reports_timestamp" filter accepts either the Unix Epoch milliseconds format or the RFC3339 UTC "Zulu" format with nanosecond resolution and up to nine fractional digits. Both formats should be surrounded by simple double quotes. Examples: "2014-10-02T15:01:23Z", "2014-10-02T15:01:23.045123456Z", "1679283943823". */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1ListTelemetryDevicesResponse])
				}
				object list {
					def apply(customersId :PlayApi, readMask: String, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/devices").addQueryStringParameters("readMask" -> readMask.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleChromeManagementV1ListTelemetryDevicesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1TelemetryDevice]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1TelemetryDevice])
				}
				object get {
					def apply(customersId :PlayApi, devicesId :PlayApi, readMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/telemetry/devices/${devicesId}").addQueryStringParameters("readMask" -> readMask.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChromeManagementV1TelemetryDevice]] = (fun: get) => fun.apply()
				}
			}
		}
		object reports {
			class countPrintJobsByPrinter(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountPrintJobsByPrinterResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountPrintJobsByPrinterResponse])
			}
			object countPrintJobsByPrinter {
				def apply(customersId :PlayApi, pageToken: String, printerOrgUnitId: String, customer: String, orderBy: String, pageSize: Int, filter: String)(using auth: AuthToken, ec: ExecutionContext): countPrintJobsByPrinter = new countPrintJobsByPrinter(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countPrintJobsByPrinter").addQueryStringParameters("pageToken" -> pageToken.toString, "printerOrgUnitId" -> printerOrgUnitId.toString, "customer" -> customer.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
				given Conversion[countPrintJobsByPrinter, Future[Schema.GoogleChromeManagementV1CountPrintJobsByPrinterResponse]] = (fun: countPrintJobsByPrinter) => fun.apply()
			}
			class countChromeHardwareFleetDevices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeHardwareFleetDevicesResponse]) {
				/** Optional. The ID of the organizational unit. If omitted, all data will be returned. */
				def withOrgUnitId(orgUnitId: String) = new countChromeHardwareFleetDevices(req.addQueryStringParameters("orgUnitId" -> orgUnitId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeHardwareFleetDevicesResponse])
			}
			object countChromeHardwareFleetDevices {
				def apply(customersId :PlayApi, customer: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): countChromeHardwareFleetDevices = new countChromeHardwareFleetDevices(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeHardwareFleetDevices").addQueryStringParameters("customer" -> customer.toString, "readMask" -> readMask.toString))
				given Conversion[countChromeHardwareFleetDevices, Future[Schema.GoogleChromeManagementV1CountChromeHardwareFleetDevicesResponse]] = (fun: countChromeHardwareFleetDevices) => fun.apply()
			}
			class countChromeBrowsersNeedingAttention(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeBrowsersNeedingAttentionResponse]) {
				/** Optional. The ID of the organizational unit. If omitted, all data will be returned. */
				def withOrgUnitId(orgUnitId: String) = new countChromeBrowsersNeedingAttention(req.addQueryStringParameters("orgUnitId" -> orgUnitId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeBrowsersNeedingAttentionResponse])
			}
			object countChromeBrowsersNeedingAttention {
				def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): countChromeBrowsersNeedingAttention = new countChromeBrowsersNeedingAttention(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeBrowsersNeedingAttention").addQueryStringParameters("customer" -> customer.toString))
				given Conversion[countChromeBrowsersNeedingAttention, Future[Schema.GoogleChromeManagementV1CountChromeBrowsersNeedingAttentionResponse]] = (fun: countChromeBrowsersNeedingAttention) => fun.apply()
			}
			class countInstalledApps(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountInstalledAppsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountInstalledAppsResponse])
			}
			object countInstalledApps {
				def apply(customersId :PlayApi, customer: String, orderBy: String, orgUnitId: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): countInstalledApps = new countInstalledApps(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countInstalledApps").addQueryStringParameters("customer" -> customer.toString, "orderBy" -> orderBy.toString, "orgUnitId" -> orgUnitId.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[countInstalledApps, Future[Schema.GoogleChromeManagementV1CountInstalledAppsResponse]] = (fun: countInstalledApps) => fun.apply()
			}
			class findInstalledAppDevices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1FindInstalledAppDevicesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1FindInstalledAppDevicesResponse])
			}
			object findInstalledAppDevices {
				def apply(customersId :PlayApi, appType: String, orderBy: String, appId: String, customer: String, filter: String, pageToken: String, pageSize: Int, orgUnitId: String)(using auth: AuthToken, ec: ExecutionContext): findInstalledAppDevices = new findInstalledAppDevices(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:findInstalledAppDevices").addQueryStringParameters("appType" -> appType.toString, "orderBy" -> orderBy.toString, "appId" -> appId.toString, "customer" -> customer.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "orgUnitId" -> orgUnitId.toString))
				given Conversion[findInstalledAppDevices, Future[Schema.GoogleChromeManagementV1FindInstalledAppDevicesResponse]] = (fun: findInstalledAppDevices) => fun.apply()
			}
			class countChromeVersions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeVersionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeVersionsResponse])
			}
			object countChromeVersions {
				def apply(customersId :PlayApi, orgUnitId: String, pageToken: String, pageSize: Int, customer: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): countChromeVersions = new countChromeVersions(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeVersions").addQueryStringParameters("orgUnitId" -> orgUnitId.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "customer" -> customer.toString, "filter" -> filter.toString))
				given Conversion[countChromeVersions, Future[Schema.GoogleChromeManagementV1CountChromeVersionsResponse]] = (fun: countChromeVersions) => fun.apply()
			}
			class countChromeCrashEvents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeCrashEventsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeCrashEventsResponse])
			}
			object countChromeCrashEvents {
				def apply(customersId :PlayApi, orgUnitId: String, customer: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): countChromeCrashEvents = new countChromeCrashEvents(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeCrashEvents").addQueryStringParameters("orgUnitId" -> orgUnitId.toString, "customer" -> customer.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
				given Conversion[countChromeCrashEvents, Future[Schema.GoogleChromeManagementV1CountChromeCrashEventsResponse]] = (fun: countChromeCrashEvents) => fun.apply()
			}
			class countPrintJobsByUser(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountPrintJobsByUserResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountPrintJobsByUserResponse])
			}
			object countPrintJobsByUser {
				def apply(customersId :PlayApi, printerOrgUnitId: String, pageToken: String, customer: String, orderBy: String, pageSize: Int, filter: String)(using auth: AuthToken, ec: ExecutionContext): countPrintJobsByUser = new countPrintJobsByUser(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countPrintJobsByUser").addQueryStringParameters("printerOrgUnitId" -> printerOrgUnitId.toString, "pageToken" -> pageToken.toString, "customer" -> customer.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
				given Conversion[countPrintJobsByUser, Future[Schema.GoogleChromeManagementV1CountPrintJobsByUserResponse]] = (fun: countPrintJobsByUser) => fun.apply()
			}
			class countChromeDevicesThatNeedAttention(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeDevicesThatNeedAttentionResponse]) {
				/** Optional. The ID of the organizational unit. If omitted, all data will be returned. */
				def withOrgUnitId(orgUnitId: String) = new countChromeDevicesThatNeedAttention(req.addQueryStringParameters("orgUnitId" -> orgUnitId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeDevicesThatNeedAttentionResponse])
			}
			object countChromeDevicesThatNeedAttention {
				def apply(customersId :PlayApi, readMask: String, customer: String)(using auth: AuthToken, ec: ExecutionContext): countChromeDevicesThatNeedAttention = new countChromeDevicesThatNeedAttention(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeDevicesThatNeedAttention").addQueryStringParameters("readMask" -> readMask.toString, "customer" -> customer.toString))
				given Conversion[countChromeDevicesThatNeedAttention, Future[Schema.GoogleChromeManagementV1CountChromeDevicesThatNeedAttentionResponse]] = (fun: countChromeDevicesThatNeedAttention) => fun.apply()
			}
			class enumeratePrintJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1EnumeratePrintJobsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1EnumeratePrintJobsResponse])
			}
			object enumeratePrintJobs {
				def apply(customersId :PlayApi, pageSize: Int, customer: String, printerOrgUnitId: String, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): enumeratePrintJobs = new enumeratePrintJobs(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:enumeratePrintJobs").addQueryStringParameters("pageSize" -> pageSize.toString, "customer" -> customer.toString, "printerOrgUnitId" -> printerOrgUnitId.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[enumeratePrintJobs, Future[Schema.GoogleChromeManagementV1EnumeratePrintJobsResponse]] = (fun: enumeratePrintJobs) => fun.apply()
			}
			class countChromeDevicesReachingAutoExpirationDate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeDevicesReachingAutoExpirationDateResponse]) {
				/** Optional. Maximum expiration date in format yyyy-mm-dd in UTC timezone. If included returns all devices that have already expired and devices with auto expiration date equal to or earlier than the maximum date. */
				def withMaxAueDate(maxAueDate: String) = new countChromeDevicesReachingAutoExpirationDate(req.addQueryStringParameters("maxAueDate" -> maxAueDate.toString))
				/** Optional. Maximum expiration date in format yyyy-mm-dd in UTC timezone. If included returns all devices that have already expired and devices with auto expiration date equal to or later than the minimum date. */
				def withMinAueDate(minAueDate: String) = new countChromeDevicesReachingAutoExpirationDate(req.addQueryStringParameters("minAueDate" -> minAueDate.toString))
				/** Optional. The organizational unit ID, if omitted, will return data for all organizational units. */
				def withOrgUnitId(orgUnitId: String) = new countChromeDevicesReachingAutoExpirationDate(req.addQueryStringParameters("orgUnitId" -> orgUnitId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeDevicesReachingAutoExpirationDateResponse])
			}
			object countChromeDevicesReachingAutoExpirationDate {
				def apply(customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): countChromeDevicesReachingAutoExpirationDate = new countChromeDevicesReachingAutoExpirationDate(ws.url(BASE_URL + s"v1/customers/${customersId}/reports:countChromeDevicesReachingAutoExpirationDate").addQueryStringParameters("customer" -> customer.toString))
				given Conversion[countChromeDevicesReachingAutoExpirationDate, Future[Schema.GoogleChromeManagementV1CountChromeDevicesReachingAutoExpirationDateResponse]] = (fun: countChromeDevicesReachingAutoExpirationDate) => fun.apply()
			}
		}
		object apps {
			class countChromeAppRequests(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1CountChromeAppRequestsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1CountChromeAppRequestsResponse])
			}
			object countChromeAppRequests {
				def apply(customersId :PlayApi, pageSize: Int, orderBy: String, orgUnitId: String, pageToken: String, customer: String)(using auth: AuthToken, ec: ExecutionContext): countChromeAppRequests = new countChromeAppRequests(ws.url(BASE_URL + s"v1/customers/${customersId}/apps:countChromeAppRequests").addQueryStringParameters("pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "orgUnitId" -> orgUnitId.toString, "pageToken" -> pageToken.toString, "customer" -> customer.toString))
				given Conversion[countChromeAppRequests, Future[Schema.GoogleChromeManagementV1CountChromeAppRequestsResponse]] = (fun: countChromeAppRequests) => fun.apply()
			}
			class fetchDevicesRequestingExtension(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1FetchDevicesRequestingExtensionResponse]) {
				/** Optional. Token to specify the page of the request to be returned. Token expires after 1 day. */
				def withPageToken(pageToken: String) = new fetchDevicesRequestingExtension(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Maximum number of results to return. Maximum and default are 50. Any page size larger than 50 will be coerced to 50.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new fetchDevicesRequestingExtension(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1FetchDevicesRequestingExtensionResponse])
			}
			object fetchDevicesRequestingExtension {
				def apply(customersId :PlayApi, orgUnitId: String, customer: String, extensionId: String)(using auth: AuthToken, ec: ExecutionContext): fetchDevicesRequestingExtension = new fetchDevicesRequestingExtension(ws.url(BASE_URL + s"v1/customers/${customersId}/apps:fetchDevicesRequestingExtension").addQueryStringParameters("orgUnitId" -> orgUnitId.toString, "customer" -> customer.toString, "extensionId" -> extensionId.toString))
				given Conversion[fetchDevicesRequestingExtension, Future[Schema.GoogleChromeManagementV1FetchDevicesRequestingExtensionResponse]] = (fun: fetchDevicesRequestingExtension) => fun.apply()
			}
			class fetchUsersRequestingExtension(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1FetchUsersRequestingExtensionResponse]) {
				/** Optional. Token to specify the page of the request to be returned. Token expires after 1 day. */
				def withPageToken(pageToken: String) = new fetchUsersRequestingExtension(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Maximum number of results to return. Maximum and default are 50. Any page size larger than 50 will be coerced to 50.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new fetchUsersRequestingExtension(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1FetchUsersRequestingExtensionResponse])
			}
			object fetchUsersRequestingExtension {
				def apply(customersId :PlayApi, extensionId: String, orgUnitId: String, customer: String)(using auth: AuthToken, ec: ExecutionContext): fetchUsersRequestingExtension = new fetchUsersRequestingExtension(ws.url(BASE_URL + s"v1/customers/${customersId}/apps:fetchUsersRequestingExtension").addQueryStringParameters("extensionId" -> extensionId.toString, "orgUnitId" -> orgUnitId.toString, "customer" -> customer.toString))
				given Conversion[fetchUsersRequestingExtension, Future[Schema.GoogleChromeManagementV1FetchUsersRequestingExtensionResponse]] = (fun: fetchUsersRequestingExtension) => fun.apply()
			}
			object chrome {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1AppDetails]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1AppDetails])
				}
				object get {
					def apply(customersId :PlayApi, chromeId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/apps/chrome/${chromeId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChromeManagementV1AppDetails]] = (fun: get) => fun.apply()
				}
			}
			object web {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1AppDetails]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1AppDetails])
				}
				object get {
					def apply(customersId :PlayApi, webId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/apps/web/${webId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChromeManagementV1AppDetails]] = (fun: get) => fun.apply()
				}
			}
			object android {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChromeManagementV1AppDetails]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleChromeManagementV1AppDetails])
				}
				object get {
					def apply(customersId :PlayApi, androidId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/apps/android/${androidId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChromeManagementV1AppDetails]] = (fun: get) => fun.apply()
				}
			}
		}
	}
}
