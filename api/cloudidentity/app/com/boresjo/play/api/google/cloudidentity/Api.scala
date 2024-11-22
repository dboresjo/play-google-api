package com.boresjo.play.api.google.cloudidentity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/cloud-identity.devices""" /* Private Service: https://www.googleapis.com/auth/cloud-identity.devices */,
		"""https://www.googleapis.com/auth/cloud-identity.devices.lookup""" /* See your device details */,
		"""https://www.googleapis.com/auth/cloud-identity.devices.readonly""" /* Private Service: https://www.googleapis.com/auth/cloud-identity.devices.readonly */,
		"""https://www.googleapis.com/auth/cloud-identity.groups""" /* See, change, create, and delete any of the Cloud Identity Groups that you can access, including the members of each group */,
		"""https://www.googleapis.com/auth/cloud-identity.groups.readonly""" /* See any Cloud Identity Groups that you can access, including group members and their emails */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://cloudidentity.googleapis.com/"

	object devices {
		/** Wipes all data on the specified device. */
		class wipe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
			/** Perform the request */
			def withGoogleAppsCloudidentityDevicesV1WipeDeviceRequest(body: Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object wipe {
			def apply(devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): wipe = new wipe(ws.url(BASE_URL + s"v1/devices/${devicesId}:wipe").addQueryStringParameters("name" -> name.toString))
		}
		/** Creates a device. Only company-owned device may be created. &#42;&#42;Note&#42;&#42;: This method is available only to customers who have one of the following SKUs: Enterprise Standard, Enterprise Plus, Enterprise for Education, and Cloud Identity Premium */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new create(req.addQueryStringParameters("customer" -> customer.toString))
			/** Perform the request */
			def withGoogleAppsCloudidentityDevicesV1Device(body: Schema.GoogleAppsCloudidentityDevicesV1Device) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/devices").addQueryStringParameters())
		}
		/** Deletes the specified device. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new delete(req.addQueryStringParameters("customer" -> customer.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves the specified device. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1Device]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the Customer in the format: `customers/{customer}`, where customer is the customer to whom the device belongs. If you're using this API for your own organization, use `customers/my_customer`. If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1Device])
		}
		object get {
			def apply(devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1Device]] = (fun: get) => fun.apply()
		}
		/** Cancels an unfinished device wipe. This operation can be used to cancel device wipe in the gap between the wipe operation returning success and the device being wiped. This operation is possible when the device is in a "pending wipe" state. The device enters the "pending wipe" state when a wipe device command is issued, but has not yet been sent to the device. The cancel wipe will fail if the wipe command has already been issued to the device. */
		class cancelWipe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
			/** Perform the request */
			def withGoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest(body: Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object cancelWipe {
			def apply(devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancelWipe = new cancelWipe(ws.url(BASE_URL + s"v1/devices/${devicesId}:cancelWipe").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists/Searches devices. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer in the format: `customers/{customer}`, where customer is the customer to whom the device belongs. If you're using this API for your own organization, use `customers/my_customer`. If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new list(req.addQueryStringParameters("customer" -> customer.toString))
			/** Optional. Additional restrictions when fetching list of devices. For a list of search fields, refer to [Mobile device search fields](https://developers.google.com/admin-sdk/directory/v1/search-operators). Multiple search fields are separated by the space character. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of Devices to return. If unspecified, at most 20 Devices will be returned. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListDevices` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListDevices` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Order specification for devices in the response. Only one of the following field names may be used to specify the order: `create_time`, `last_sync_time`, `model`, `os_version`, `device_type` and `serial_number`. `desc` may be specified optionally at the end to specify results to be sorted in descending order. Default order is ascending. */
			def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Optional. The view to use for the List request.<br>Possible values:<br>VIEW_UNSPECIFIED: Default value. The value is unused.<br>COMPANY_INVENTORY: This view contains all devices imported by the company admin. Each device in the response contains all information specified by the company admin when importing the device (i.e. asset tags). This includes devices that may be unassigned or assigned to users.<br>USER_ASSIGNED_DEVICES: This view contains all devices with at least one user registered on the device. Each device in the response contains all device information, except for asset tags. */
			def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices").addQueryStringParameters())
			given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse]] = (fun: list) => fun.apply()
		}
		object deviceUsers {
			/** Looks up resource names of the DeviceUsers associated with the caller's credentials, as well as the properties provided in the request. This method must be called with end-user credentials with the scope: https://www.googleapis.com/auth/cloud-identity.devices.lookup If multiple properties are provided, only DeviceUsers having all of these properties are considered as matches - i.e. the query behaves like an AND. Different platforms require different amounts of information from the caller to ensure that the DeviceUser is uniquely identified. - iOS: No properties need to be passed, the caller's credentials are sufficient to identify the corresponding DeviceUser. - Android: Specifying the 'android_id' field is required. - Desktop: Specifying the 'raw_resource_id' field is required. */
			class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices.lookup""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse])
			}
			object lookup {
				def apply(devicesId :PlayApi, parent: String, pageSize: Int, pageToken: String, androidId: String, rawResourceId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers:lookup").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "androidId" -> androidId.toString, "rawResourceId" -> rawResourceId.toString, "userId" -> userId.toString))
				given Conversion[lookup, Future[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse]] = (fun: lookup) => fun.apply()
			}
			/** Approves device to access user data. */
			class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
				/** Perform the request */
				def withGoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object approve {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			/** Wipes the user's account on a device. Other data on the device that is not associated with the user's work account is not affected. For example, if a Gmail app is installed on a device that is used for personal and work purposes, and the user is logged in to the Gmail app with their personal account as well as their work account, wiping the "deviceUser" by their work administrator will not affect their personal account within Gmail or other apps such as Photos. */
			class wipe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
				/** Perform the request */
				def withGoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object wipe {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): wipe = new wipe(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:wipe").addQueryStringParameters("name" -> name.toString))
			}
			/** Blocks device from accessing user data */
			class block(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
				/** Perform the request */
				def withGoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object block {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): block = new block(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:block").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes the specified DeviceUser. This also revokes the user's access to device data. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
				/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
				def withCustomer(customer: String) = new delete(req.addQueryStringParameters("customer" -> customer.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Retrieves the specified DeviceUser */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
				/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
				def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser])
			}
			object get {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]] = (fun: get) => fun.apply()
			}
			/** Cancels an unfinished user account wipe. This operation can be used to cancel device wipe in the gap between the wipe operation returning success and the device being wiped. */
			class cancelWipe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
				/** Perform the request */
				def withGoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object cancelWipe {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancelWipe = new cancelWipe(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:cancelWipe").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists/Searches DeviceUsers. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
				/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
				def withCustomer(customer: String) = new list(req.addQueryStringParameters("customer" -> customer.toString))
				/** Optional. Additional restrictions when fetching list of devices. For a list of search fields, refer to [Mobile device search fields](https://developers.google.com/admin-sdk/directory/v1/search-operators). Multiple search fields are separated by the space character. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The maximum number of DeviceUsers to return. If unspecified, at most 5 DeviceUsers will be returned. The maximum value is 20; values above 20 will be coerced to 20.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListDeviceUsers` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListBooks` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Order specification for devices in the response. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse])
			}
			object list {
				def apply(devicesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse]] = (fun: list) => fun.apply()
			}
			object clientStates {
				/** Gets the client state for the device user */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ClientState]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ClientState])
				}
				object get {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, clientStatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates/${clientStatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1ClientState]] = (fun: get) => fun.apply()
				}
				/** Lists the client states for the given search query. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""", """https://www.googleapis.com/auth/cloud-identity.devices.readonly""")
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new list(req.addQueryStringParameters("customer" -> customer.toString))
					/** Optional. Additional restrictions when fetching list of client states. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token, received from a previous `ListClientStates` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListClientStates` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Order specification for client states in the response. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse])
				}
				object list {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse]] = (fun: list) => fun.apply()
				}
				/** Updates the client state for the device user &#42;&#42;Note&#42;&#42;: This method is available only to customers who have one of the following SKUs: Enterprise Standard, Enterprise Plus, Enterprise for Education, and Cloud Identity Premium */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.devices""")
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new patch(req.addQueryStringParameters("customer" -> customer.toString))
					/** Optional. Comma-separated list of fully qualified names of fields to be updated. If not specified, all updatable fields in ClientState are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleAppsCloudidentityDevicesV1ClientState(body: Schema.GoogleAppsCloudidentityDevicesV1ClientState) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, clientStatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates/${clientStatesId}").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
	object groups {
		/** Looks up the [resource name](https://cloud.google.com/apis/design/resource_names) of a `Group` by its `EntityKey`. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupGroupNameResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupGroupNameResponse])
		}
		object lookup {
			def apply(groupKeyId: String, groupKeyNamespace: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/groups:lookup").addQueryStringParameters("groupKey.id" -> groupKeyId.toString, "groupKey.namespace" -> groupKeyNamespace.toString))
			given Conversion[lookup, Future[Schema.LookupGroupNameResponse]] = (fun: lookup) => fun.apply()
		}
		/** Update Security Settings */
		class updateSecuritySettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSecuritySettings(body: Schema.SecuritySettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object updateSecuritySettings {
			def apply(groupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecuritySettings = new updateSecuritySettings(ws.url(BASE_URL + s"v1/groups/${groupsId}/securitySettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Deletes a `Group`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a `Group`. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Group]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Group])
		}
		object get {
			def apply(groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Group]] = (fun: get) => fun.apply()
		}
		/** Updates a `Group`. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(groupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Get Security Settings */
		class getSecuritySettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SecuritySettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SecuritySettings])
		}
		object getSecuritySettings {
			def apply(groupsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): getSecuritySettings = new getSecuritySettings(ws.url(BASE_URL + s"v1/groups/${groupsId}/securitySettings").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getSecuritySettings, Future[Schema.SecuritySettings]] = (fun: getSecuritySettings) => fun.apply()
		}
		/** Creates a Group. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The initial configuration option for the `Group`.<br>Possible values:<br>INITIAL_GROUP_CONFIG_UNSPECIFIED: Default. Should not be used.<br>WITH_INITIAL_OWNER: The end user making the request will be added as the initial owner of the `Group`.<br>EMPTY: An empty group is created without any initial owners. This can only be used by admins of the domain. */
			def withInitialGroupConfig(initialGroupConfig: String) = new create(req.addQueryStringParameters("initialGroupConfig" -> initialGroupConfig.toString))
			/** Perform the request */
			def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/groups").addQueryStringParameters())
		}
		/** Lists the `Group` resources under a customer or namespace. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupsResponse])
		}
		object list {
			def apply(parent: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/groups").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
		}
		/** Searches for `Group` resources matching a specified query. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Required. The search query. &#42; Must be specified in [Common Expression Language](https://opensource.google/projects/cel). &#42; Must contain equality operators on the parent, e.g. `parent == 'customers/{customer_id}'`. The `customer_id` must begin with "C" (for example, 'C046psxkn'). [Find your customer ID.] (https://support.google.com/cloudidentity/answer/10070793) &#42; Can contain optional inclusion operators on `labels` such as `'cloudidentity.googleapis.com/groups.discussion_forum' in labels`). &#42; Can contain an optional equality operator on `domain_name`. e.g. `domain_name == 'examplepetstore.com'` &#42; Can contain optional `startsWith/contains/equality` operators on `group_key`, e.g. `group_key.startsWith('dev')`, `group_key.contains('dev'), group_key == 'dev@examplepetstore.com'` &#42; Can contain optional `startsWith/contains/equality` operators on `display_name`, such as `display_name.startsWith('dev')` , `display_name.contains('dev')`, `display_name == 'dev'` */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchGroupsResponse])
		}
		object search {
			def apply(view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/groups:search").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[search, Future[Schema.SearchGroupsResponse]] = (fun: search) => fun.apply()
		}
		object memberships {
			/** Modifies the `MembershipRole`s of a `Membership`. */
			class modifyMembershipRoles(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withModifyMembershipRolesRequest(body: Schema.ModifyMembershipRolesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModifyMembershipRolesResponse])
			}
			object modifyMembershipRoles {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): modifyMembershipRoles = new modifyMembershipRoles(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}:modifyMembershipRoles").addQueryStringParameters("name" -> name.toString))
			}
			/** Get a membership graph of just a member or both a member and a group. &#42;&#42;Note:&#42;&#42; This feature is only available to Google Workspace Enterprise Standard, Enterprise Plus, and Enterprise for Education; and Cloud Identity Premium accounts. If the account of the member is not one of these, a 403 (PERMISSION_DENIED) HTTP status code will be returned. Given a member, the response will contain all membership paths from the member. Given both a group and a member, the response will contain all membership paths between the group and the member. */
			class getMembershipGraph(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object getMembershipGraph {
				def apply(groupsId :PlayApi, parent: String, query: String)(using signer: RequestSigner, ec: ExecutionContext): getMembershipGraph = new getMembershipGraph(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:getMembershipGraph").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString))
				given Conversion[getMembershipGraph, Future[Schema.Operation]] = (fun: getMembershipGraph) => fun.apply()
			}
			/** Check a potential member for membership in a group. &#42;&#42;Note:&#42;&#42; This feature is only available to Google Workspace Enterprise Standard, Enterprise Plus, and Enterprise for Education; and Cloud Identity Premium accounts. If the account of the member is not one of these, a 403 (PERMISSION_DENIED) HTTP status code will be returned. A member has membership to a group as long as there is a single viewable transitive membership between the group and the member. The actor must have view permissions to at least one transitive membership between the member and group. */
			class checkTransitiveMembership(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckTransitiveMembershipResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckTransitiveMembershipResponse])
			}
			object checkTransitiveMembership {
				def apply(groupsId :PlayApi, parent: String, query: String)(using signer: RequestSigner, ec: ExecutionContext): checkTransitiveMembership = new checkTransitiveMembership(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:checkTransitiveMembership").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString))
				given Conversion[checkTransitiveMembership, Future[Schema.CheckTransitiveMembershipResponse]] = (fun: checkTransitiveMembership) => fun.apply()
			}
			/** Creates a `Membership`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withMembership(body: Schema.Membership) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(groupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a `Membership`. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Search transitive memberships of a group. &#42;&#42;Note:&#42;&#42; This feature is only available to Google Workspace Enterprise Standard, Enterprise Plus, and Enterprise for Education; and Cloud Identity Premium accounts. If the account of the group is not one of these, a 403 (PERMISSION_DENIED) HTTP status code will be returned. A transitive membership is any direct or indirect membership of a group. Actor must have view permissions to all transitive memberships. */
			class searchTransitiveMemberships(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchTransitiveMembershipsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchTransitiveMembershipsResponse])
			}
			object searchTransitiveMemberships {
				def apply(groupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): searchTransitiveMemberships = new searchTransitiveMemberships(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchTransitiveMemberships").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchTransitiveMemberships, Future[Schema.SearchTransitiveMembershipsResponse]] = (fun: searchTransitiveMemberships) => fun.apply()
			}
			/** Retrieves a `Membership`. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Membership]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Membership])
			}
			object get {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Membership]] = (fun: get) => fun.apply()
			}
			/** Search transitive groups of a member. &#42;&#42;Note:&#42;&#42; This feature is only available to Google Workspace Enterprise Standard, Enterprise Plus, and Enterprise for Education; and Cloud Identity Premium accounts. If the account of the member is not one of these, a 403 (PERMISSION_DENIED) HTTP status code will be returned. A transitive group is any group that has a direct or indirect membership to the member. Actor must have view permissions all transitive groups. */
			class searchTransitiveGroups(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchTransitiveGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Required. A CEL expression that MUST include member specification AND label(s). This is a `required` field. Users can search on label attributes of groups. CONTAINS match ('in') is supported on labels. Identity-mapped groups are uniquely identified by both a `member_key_id` and a `member_key_namespace`, which requires an additional query input: `member_key_namespace`. Example query: `member_key_id == 'member_key_id_value' && in labels` Query may optionally contain equality operators on the parent of the group restricting the search within a particular customer, e.g. `parent == 'customers/{customer_id}'`. The `customer_id` must begin with "C" (for example, 'C046psxkn'). This filtering is only supported for Admins with groups read permissons on the input customer. Example query: `member_key_id == 'member_key_id_value' && in labels && parent == 'customers/C046psxkn'` */
				def withQuery(query: String) = new searchTransitiveGroups(req.addQueryStringParameters("query" -> query.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchTransitiveGroupsResponse])
			}
			object searchTransitiveGroups {
				def apply(groupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): searchTransitiveGroups = new searchTransitiveGroups(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchTransitiveGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchTransitiveGroups, Future[Schema.SearchTransitiveGroupsResponse]] = (fun: searchTransitiveGroups) => fun.apply()
			}
			/** Lists the `Membership`s within a `Group`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMembershipsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMembershipsResponse])
			}
			object list {
				def apply(groupsId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMembershipsResponse]] = (fun: list) => fun.apply()
			}
			/** Looks up the [resource name](https://cloud.google.com/apis/design/resource_names) of a `Membership` by its `EntityKey`. */
			class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupMembershipNameResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupMembershipNameResponse])
			}
			object lookup {
				def apply(groupsId :PlayApi, parent: String, memberKeyId: String, memberKeyNamespace: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:lookup").addQueryStringParameters("parent" -> parent.toString, "memberKey.id" -> memberKeyId.toString, "memberKey.namespace" -> memberKeyNamespace.toString))
				given Conversion[lookup, Future[Schema.LookupMembershipNameResponse]] = (fun: lookup) => fun.apply()
			}
			/** Searches direct groups of a member. */
			class searchDirectGroups(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchDirectGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-identity.groups""", """https://www.googleapis.com/auth/cloud-identity.groups.readonly""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchDirectGroupsResponse])
			}
			object searchDirectGroups {
				def apply(groupsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): searchDirectGroups = new searchDirectGroups(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchDirectGroups").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[searchDirectGroups, Future[Schema.SearchDirectGroupsResponse]] = (fun: searchDirectGroups) => fun.apply()
			}
		}
	}
	object inboundSamlSsoProfiles {
		/** Creates an InboundSamlSsoProfile for a customer. When the target customer has enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448), the `Operation` in the response will have `"done": false`, it will not have a response, and the metadata will have `"state": "awaiting-multi-party-approval"`. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInboundSamlSsoProfile(body: Schema.InboundSamlSsoProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles").addQueryStringParameters())
		}
		/** Deletes an InboundSamlSsoProfile. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Updates an InboundSamlSsoProfile. When the target customer has enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448), the `Operation` in the response will have `"done": false`, it will not have a response, and the metadata will have `"state": "awaiting-multi-party-approval"`. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInboundSamlSsoProfile(body: Schema.InboundSamlSsoProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists InboundSamlSsoProfiles for a customer. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInboundSamlSsoProfilesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInboundSamlSsoProfilesResponse])
		}
		object list {
			def apply(filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInboundSamlSsoProfilesResponse]] = (fun: list) => fun.apply()
		}
		/** Gets an InboundSamlSsoProfile. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InboundSamlSsoProfile]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InboundSamlSsoProfile])
		}
		object get {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.InboundSamlSsoProfile]] = (fun: get) => fun.apply()
		}
		object idpCredentials {
			/** Deletes an IdpCredential. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(inboundSamlSsoProfilesId :PlayApi, idpCredentialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials/${idpCredentialsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets an IdpCredential. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IdpCredential]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IdpCredential])
			}
			object get {
				def apply(inboundSamlSsoProfilesId :PlayApi, idpCredentialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials/${idpCredentialsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.IdpCredential]] = (fun: get) => fun.apply()
			}
			/** Returns a list of IdpCredentials in an InboundSamlSsoProfile. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListIdpCredentialsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListIdpCredentialsResponse])
			}
			object list {
				def apply(inboundSamlSsoProfilesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListIdpCredentialsResponse]] = (fun: list) => fun.apply()
			}
			/** Adds an IdpCredential. Up to 2 credentials are allowed. When the target customer has enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448), the `Operation` in the response will have `"done": false`, it will not have a response, and the metadata will have `"state": "awaiting-multi-party-approval"`. */
			class add(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAddIdpCredentialRequest(body: Schema.AddIdpCredentialRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object add {
				def apply(inboundSamlSsoProfilesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): add = new add(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials:add").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object inboundSsoAssignments {
		/** Creates an InboundSsoAssignment for users and devices in a `Customer` under a given `Group` or `OrgUnit`. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInboundSsoAssignment(body: Schema.InboundSsoAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/inboundSsoAssignments").addQueryStringParameters())
		}
		/** Deletes an InboundSsoAssignment. To disable SSO, Create (or Update) an assignment that has `sso_mode` == `SSO_OFF`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Gets an InboundSsoAssignment. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InboundSsoAssignment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InboundSsoAssignment])
		}
		object get {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.InboundSsoAssignment]] = (fun: get) => fun.apply()
		}
		/** Updates an InboundSsoAssignment. The body of this request is the `inbound_sso_assignment` field and the `update_mask` is relative to that. For example: a PATCH to `/v1/inboundSsoAssignments/0abcdefg1234567&update_mask=rank` with a body of `{ "rank": 1 }` moves that (presumably group-targeted) SSO assignment to the highest priority and shifts any other group-targeted assignments down in priority. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInboundSsoAssignment(body: Schema.InboundSsoAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists the InboundSsoAssignments for a `Customer`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInboundSsoAssignmentsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInboundSsoAssignmentsResponse])
		}
		object list {
			def apply(filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSsoAssignments").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInboundSsoAssignmentsResponse]] = (fun: list) => fun.apply()
		}
	}
	object customers {
		object userinvitations {
			/** Cancels a UserInvitation that was already sent. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCancelUserInvitationRequest(body: Schema.CancelUserInvitationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object cancel {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Verifies whether a user account is eligible to receive a UserInvitation (is an unmanaged account). Eligibility is based on the following criteria: &#42; the email address is a consumer account and it's the primary email address of the account, and &#42; the domain of the email address matches an existing verified Google Workspace or Cloud Identity domain If both conditions are met, the user is eligible. &#42;&#42;Note:&#42;&#42; This method is not supported for Workspace Essentials customers. */
			class isInvitableUser(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IsInvitableUserResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IsInvitableUserResponse])
			}
			object isInvitableUser {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): isInvitableUser = new isInvitableUser(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:isInvitableUser").addQueryStringParameters("name" -> name.toString))
				given Conversion[isInvitableUser, Future[Schema.IsInvitableUserResponse]] = (fun: isInvitableUser) => fun.apply()
			}
			/** Retrieves a UserInvitation resource. &#42;&#42;Note:&#42;&#42; New consumer accounts with the customer's verified domain created within the previous 48 hours will not appear in the result. This delay also applies to newly-verified domains. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserInvitation]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserInvitation])
			}
			object get {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.UserInvitation]] = (fun: get) => fun.apply()
			}
			/** Retrieves a list of UserInvitation resources. &#42;&#42;Note:&#42;&#42; New consumer accounts with the customer's verified domain created within the previous 48 hours will not appear in the result. This delay also applies to newly-verified domains. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserInvitationsResponse]) {
				val scopes = Seq()
				/** Optional. The maximum number of UserInvitation resources to return. If unspecified, at most 100 resources will be returned. The maximum value is 200; values above 200 will be set to 200.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListUserInvitations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListBooks` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A query string for filtering `UserInvitation` results by their current state, in the format: `"state=='invited'"`. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The sort order of the list results. You can sort the results in descending order based on either email or last update timestamp but not both, using `order_by="email desc"`. Currently, sorting is supported for `update_time asc`, `update_time desc`, `email asc`, and `email desc`. If not specified, results will be returned based on `email asc` order. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserInvitationsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListUserInvitationsResponse]] = (fun: list) => fun.apply()
			}
			/** Sends a UserInvitation to email. If the `UserInvitation` does not exist for this request and it is a valid request, the request creates a `UserInvitation`. &#42;&#42;Note:&#42;&#42; The `get` and `list` methods have a 48-hour delay where newly-created consumer accounts will not appear in the results. You can still send a `UserInvitation` to those accounts if you know the unmanaged email address and IsInvitableUser==True. */
			class send(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withSendUserInvitationRequest(body: Schema.SendUserInvitationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object send {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:send").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
