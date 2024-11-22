package com.boresjo.play.api.google.cloudidentity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudidentity.googleapis.com/"

	object devices {
		class wipe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsCloudidentityDevicesV1WipeDeviceRequest(body: Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object wipe {
			def apply(devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): wipe = new wipe(ws.url(BASE_URL + s"v1/devices/${devicesId}:wipe").addQueryStringParameters("name" -> name.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new create(req.addQueryStringParameters("customer" -> customer.toString))
			def withGoogleAppsCloudidentityDevicesV1Device(body: Schema.GoogleAppsCloudidentityDevicesV1Device) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/devices").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new delete(req.addQueryStringParameters("customer" -> customer.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1Device]) {
			/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the Customer in the format: `customers/{customer}`, where customer is the customer to whom the device belongs. If you're using this API for your own organization, use `customers/my_customer`. If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
			def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1Device])
		}
		object get {
			def apply(devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1Device]] = (fun: get) => fun.apply()
		}
		class cancelWipe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest(body: Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object cancelWipe {
			def apply(devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancelWipe = new cancelWipe(ws.url(BASE_URL + s"v1/devices/${devicesId}:cancelWipe").addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse]) {
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
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices").addQueryStringParameters())
			given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse]] = (fun: list) => fun.apply()
		}
		object deviceUsers {
			class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse])
			}
			object lookup {
				def apply(devicesId :PlayApi, parent: String, pageSize: Int, pageToken: String, androidId: String, rawResourceId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers:lookup").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "androidId" -> androidId.toString, "rawResourceId" -> rawResourceId.toString, "userId" -> userId.toString))
				given Conversion[lookup, Future[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse]] = (fun: lookup) => fun.apply()
			}
			class approve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object approve {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			class wipe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object wipe {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): wipe = new wipe(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:wipe").addQueryStringParameters("name" -> name.toString))
			}
			class block(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object block {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): block = new block(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:block").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
				def withCustomer(customer: String) = new delete(req.addQueryStringParameters("customer" -> customer.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]) {
				/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
				def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser])
			}
			object get {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]] = (fun: get) => fun.apply()
			}
			class cancelWipe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest(body: Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object cancelWipe {
				def apply(devicesId :PlayApi, deviceUsersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancelWipe = new cancelWipe(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}:cancelWipe").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse]) {
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
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse])
			}
			object list {
				def apply(devicesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse]] = (fun: list) => fun.apply()
			}
			object clientStates {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ClientState]) {
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new get(req.addQueryStringParameters("customer" -> customer.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ClientState])
				}
				object get {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, clientStatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates/${clientStatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleAppsCloudidentityDevicesV1ClientState]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse]) {
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new list(req.addQueryStringParameters("customer" -> customer.toString))
					/** Optional. Additional restrictions when fetching list of client states. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token, received from a previous `ListClientStates` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListClientStates` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Order specification for client states in the response. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse])
				}
				object list {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse]] = (fun: list) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
					def withCustomer(customer: String) = new patch(req.addQueryStringParameters("customer" -> customer.toString))
					/** Optional. Comma-separated list of fully qualified names of fields to be updated. If not specified, all updatable fields in ClientState are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleAppsCloudidentityDevicesV1ClientState(body: Schema.GoogleAppsCloudidentityDevicesV1ClientState) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(devicesId :PlayApi, deviceUsersId :PlayApi, clientStatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/devices/${devicesId}/deviceUsers/${deviceUsersId}/clientStates/${clientStatesId}").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
	object groups {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LookupGroupNameResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LookupGroupNameResponse])
		}
		object lookup {
			def apply(groupKeyId: String, groupKeyNamespace: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/groups:lookup").addQueryStringParameters("groupKey.id" -> groupKeyId.toString, "groupKey.namespace" -> groupKeyNamespace.toString))
			given Conversion[lookup, Future[Schema.LookupGroupNameResponse]] = (fun: lookup) => fun.apply()
		}
		class updateSecuritySettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSecuritySettings(body: Schema.SecuritySettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object updateSecuritySettings {
			def apply(groupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateSecuritySettings = new updateSecuritySettings(ws.url(BASE_URL + s"v1/groups/${groupsId}/securitySettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Group]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Group])
		}
		object get {
			def apply(groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Group]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroup(body: Schema.Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(groupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/groups/${groupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class getSecuritySettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SecuritySettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SecuritySettings])
		}
		object getSecuritySettings {
			def apply(groupsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): getSecuritySettings = new getSecuritySettings(ws.url(BASE_URL + s"v1/groups/${groupsId}/securitySettings").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getSecuritySettings, Future[Schema.SecuritySettings]] = (fun: getSecuritySettings) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The initial configuration option for the `Group`.<br>Possible values:<br>INITIAL_GROUP_CONFIG_UNSPECIFIED: Default. Should not be used.<br>WITH_INITIAL_OWNER: The end user making the request will be added as the initial owner of the `Group`.<br>EMPTY: An empty group is created without any initial owners. This can only be used by admins of the domain. */
			def withInitialGroupConfig(initialGroupConfig: String) = new create(req.addQueryStringParameters("initialGroupConfig" -> initialGroupConfig.toString))
			def withGroup(body: Schema.Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/groups").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGroupsResponse])
		}
		object list {
			def apply(parent: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/groups").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchGroupsResponse]) {
			/** Required. The search query. &#42; Must be specified in [Common Expression Language](https://opensource.google/projects/cel). &#42; Must contain equality operators on the parent, e.g. `parent == 'customers/{customer_id}'`. The `customer_id` must begin with "C" (for example, 'C046psxkn'). [Find your customer ID.] (https://support.google.com/cloudidentity/answer/10070793) &#42; Can contain optional inclusion operators on `labels` such as `'cloudidentity.googleapis.com/groups.discussion_forum' in labels`). &#42; Can contain an optional equality operator on `domain_name`. e.g. `domain_name == 'examplepetstore.com'` &#42; Can contain optional `startsWith/contains/equality` operators on `group_key`, e.g. `group_key.startsWith('dev')`, `group_key.contains('dev'), group_key == 'dev@examplepetstore.com'` &#42; Can contain optional `startsWith/contains/equality` operators on `display_name`, such as `display_name.startsWith('dev')` , `display_name.contains('dev')`, `display_name == 'dev'` */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchGroupsResponse])
		}
		object search {
			def apply(view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/groups:search").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[search, Future[Schema.SearchGroupsResponse]] = (fun: search) => fun.apply()
		}
		object memberships {
			class modifyMembershipRoles(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyMembershipRolesRequest(body: Schema.ModifyMembershipRolesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModifyMembershipRolesResponse])
			}
			object modifyMembershipRoles {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): modifyMembershipRoles = new modifyMembershipRoles(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}:modifyMembershipRoles").addQueryStringParameters("name" -> name.toString))
			}
			class getMembershipGraph(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object getMembershipGraph {
				def apply(groupsId :PlayApi, parent: String, query: String)(using auth: AuthToken, ec: ExecutionContext): getMembershipGraph = new getMembershipGraph(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:getMembershipGraph").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString))
				given Conversion[getMembershipGraph, Future[Schema.Operation]] = (fun: getMembershipGraph) => fun.apply()
			}
			class checkTransitiveMembership(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckTransitiveMembershipResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CheckTransitiveMembershipResponse])
			}
			object checkTransitiveMembership {
				def apply(groupsId :PlayApi, parent: String, query: String)(using auth: AuthToken, ec: ExecutionContext): checkTransitiveMembership = new checkTransitiveMembership(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:checkTransitiveMembership").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString))
				given Conversion[checkTransitiveMembership, Future[Schema.CheckTransitiveMembershipResponse]] = (fun: checkTransitiveMembership) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMembership(body: Schema.Membership) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(groupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class searchTransitiveMemberships(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchTransitiveMembershipsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchTransitiveMembershipsResponse])
			}
			object searchTransitiveMemberships {
				def apply(groupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): searchTransitiveMemberships = new searchTransitiveMemberships(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchTransitiveMemberships").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchTransitiveMemberships, Future[Schema.SearchTransitiveMembershipsResponse]] = (fun: searchTransitiveMemberships) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Membership]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Membership])
			}
			object get {
				def apply(groupsId :PlayApi, membershipsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships/${membershipsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Membership]] = (fun: get) => fun.apply()
			}
			class searchTransitiveGroups(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchTransitiveGroupsResponse]) {
				/** Required. A CEL expression that MUST include member specification AND label(s). This is a `required` field. Users can search on label attributes of groups. CONTAINS match ('in') is supported on labels. Identity-mapped groups are uniquely identified by both a `member_key_id` and a `member_key_namespace`, which requires an additional query input: `member_key_namespace`. Example query: `member_key_id == 'member_key_id_value' && in labels` Query may optionally contain equality operators on the parent of the group restricting the search within a particular customer, e.g. `parent == 'customers/{customer_id}'`. The `customer_id` must begin with "C" (for example, 'C046psxkn'). This filtering is only supported for Admins with groups read permissons on the input customer. Example query: `member_key_id == 'member_key_id_value' && in labels && parent == 'customers/C046psxkn'` */
				def withQuery(query: String) = new searchTransitiveGroups(req.addQueryStringParameters("query" -> query.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchTransitiveGroupsResponse])
			}
			object searchTransitiveGroups {
				def apply(groupsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): searchTransitiveGroups = new searchTransitiveGroups(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchTransitiveGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[searchTransitiveGroups, Future[Schema.SearchTransitiveGroupsResponse]] = (fun: searchTransitiveGroups) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMembershipsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMembershipsResponse])
			}
			object list {
				def apply(groupsId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMembershipsResponse]] = (fun: list) => fun.apply()
			}
			class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LookupMembershipNameResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LookupMembershipNameResponse])
			}
			object lookup {
				def apply(groupsId :PlayApi, parent: String, memberKeyId: String, memberKeyNamespace: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:lookup").addQueryStringParameters("parent" -> parent.toString, "memberKey.id" -> memberKeyId.toString, "memberKey.namespace" -> memberKeyNamespace.toString))
				given Conversion[lookup, Future[Schema.LookupMembershipNameResponse]] = (fun: lookup) => fun.apply()
			}
			class searchDirectGroups(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchDirectGroupsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchDirectGroupsResponse])
			}
			object searchDirectGroups {
				def apply(groupsId :PlayApi, parent: String, query: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): searchDirectGroups = new searchDirectGroups(ws.url(BASE_URL + s"v1/groups/${groupsId}/memberships:searchDirectGroups").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[searchDirectGroups, Future[Schema.SearchDirectGroupsResponse]] = (fun: searchDirectGroups) => fun.apply()
			}
		}
	}
	object inboundSamlSsoProfiles {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInboundSamlSsoProfile(body: Schema.InboundSamlSsoProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInboundSamlSsoProfile(body: Schema.InboundSamlSsoProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInboundSamlSsoProfilesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInboundSamlSsoProfilesResponse])
		}
		object list {
			def apply(filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInboundSamlSsoProfilesResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InboundSamlSsoProfile]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InboundSamlSsoProfile])
		}
		object get {
			def apply(inboundSamlSsoProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.InboundSamlSsoProfile]] = (fun: get) => fun.apply()
		}
		object idpCredentials {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(inboundSamlSsoProfilesId :PlayApi, idpCredentialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials/${idpCredentialsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IdpCredential]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.IdpCredential])
			}
			object get {
				def apply(inboundSamlSsoProfilesId :PlayApi, idpCredentialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials/${idpCredentialsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.IdpCredential]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListIdpCredentialsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListIdpCredentialsResponse])
			}
			object list {
				def apply(inboundSamlSsoProfilesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListIdpCredentialsResponse]] = (fun: list) => fun.apply()
			}
			class add(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddIdpCredentialRequest(body: Schema.AddIdpCredentialRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object add {
				def apply(inboundSamlSsoProfilesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): add = new add(ws.url(BASE_URL + s"v1/inboundSamlSsoProfiles/${inboundSamlSsoProfilesId}/idpCredentials:add").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object inboundSsoAssignments {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInboundSsoAssignment(body: Schema.InboundSsoAssignment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/inboundSsoAssignments").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InboundSsoAssignment]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InboundSsoAssignment])
		}
		object get {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.InboundSsoAssignment]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInboundSsoAssignment(body: Schema.InboundSsoAssignment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(inboundSsoAssignmentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/inboundSsoAssignments/${inboundSsoAssignmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInboundSsoAssignmentsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInboundSsoAssignmentsResponse])
		}
		object list {
			def apply(filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/inboundSsoAssignments").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInboundSsoAssignmentsResponse]] = (fun: list) => fun.apply()
		}
	}
	object customers {
		object userinvitations {
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelUserInvitationRequest(body: Schema.CancelUserInvitationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object cancel {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			class isInvitableUser(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IsInvitableUserResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.IsInvitableUserResponse])
			}
			object isInvitableUser {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): isInvitableUser = new isInvitableUser(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:isInvitableUser").addQueryStringParameters("name" -> name.toString))
				given Conversion[isInvitableUser, Future[Schema.IsInvitableUserResponse]] = (fun: isInvitableUser) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserInvitation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UserInvitation])
			}
			object get {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.UserInvitation]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserInvitationsResponse]) {
				/** Optional. The maximum number of UserInvitation resources to return. If unspecified, at most 100 resources will be returned. The maximum value is 200; values above 200 will be set to 200.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListUserInvitations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListBooks` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A query string for filtering `UserInvitation` results by their current state, in the format: `"state=='invited'"`. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The sort order of the list results. You can sort the results in descending order based on either email or last update timestamp but not both, using `order_by="email desc"`. Currently, sorting is supported for `update_time asc`, `update_time desc`, `email asc`, and `email desc`. If not specified, results will be returned based on `email asc` order. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUserInvitationsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListUserInvitationsResponse]] = (fun: list) => fun.apply()
			}
			class send(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSendUserInvitationRequest(body: Schema.SendUserInvitationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object send {
				def apply(customersId :PlayApi, userinvitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"v1/customers/${customersId}/userinvitations/${userinvitationsId}:send").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
