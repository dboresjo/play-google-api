package com.boresjo.play.api.google.androidenterprise

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://androidenterprise.googleapis.com/"

	object devices {
		class setState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeviceState(body: Schema.DeviceState) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.DeviceState])
		}
		object setState {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): setState = new setState(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/state")).addQueryStringParameters())
		}
		class getState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeviceState]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.DeviceState])
		}
		object getState {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): getState = new getState(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/state")).addQueryStringParameters())
			given Conversion[getState, Future[Schema.DeviceState]] = (fun: getState) => fun.apply()
		}
		class forceReportUpload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object forceReportUpload {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): forceReportUpload = new forceReportUpload(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/forceReportUpload")).addQueryStringParameters())
			given Conversion[forceReportUpload, Future[Unit]] = (fun: forceReportUpload) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Device])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDevice(body: Schema.Device) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Device])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}")).addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DevicesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.DevicesListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices")).addQueryStringParameters())
			given Conversion[list, Future[Schema.DevicesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object enterprises {
		class getStoreLayout(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StoreLayout]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StoreLayout])
		}
		object getStoreLayout {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): getStoreLayout = new getStoreLayout(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout")).addQueryStringParameters())
			given Conversion[getStoreLayout, Future[Schema.StoreLayout]] = (fun: getStoreLayout) => fun.apply()
		}
		class generateSignupUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SignupInfo]) {
			/** Optional. Email address used to prefill the admin field of the enterprise signup form. This value is a hint only and can be altered by the user. */
			def withAdminEmail(adminEmail: String) = new generateSignupUrl(req.addQueryStringParameters("adminEmail" -> adminEmail.toString))
			def apply() = req.execute("POST").map(_.json.as[Schema.SignupInfo])
		}
		object generateSignupUrl {
			def apply(callbackUrl: String)(using auth: AuthToken, ec: ExecutionContext): generateSignupUrl = new generateSignupUrl(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/signupUrl")).addQueryStringParameters("callbackUrl" -> callbackUrl.toString))
			given Conversion[generateSignupUrl, Future[Schema.SignupInfo]] = (fun: generateSignupUrl) => fun.apply()
		}
		class createWebToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAdministratorWebTokenSpec(body: Schema.AdministratorWebTokenSpec) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AdministratorWebToken])
		}
		object createWebToken {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): createWebToken = new createWebToken(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/createWebToken")).addQueryStringParameters())
		}
		class setStoreLayout(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withStoreLayout(body: Schema.StoreLayout) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.StoreLayout])
		}
		object setStoreLayout {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): setStoreLayout = new setStoreLayout(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout")).addQueryStringParameters())
		}
		class pullNotificationSet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NotificationSet]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.NotificationSet])
		}
		object pullNotificationSet {
			def apply(requestMode: String)(using auth: AuthToken, ec: ExecutionContext): pullNotificationSet = new pullNotificationSet(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/pullNotificationSet")).addQueryStringParameters("requestMode" -> requestMode.toString))
			given Conversion[pullNotificationSet, Future[Schema.NotificationSet]] = (fun: pullNotificationSet) => fun.apply()
		}
		class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object unenroll {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/unenroll")).addQueryStringParameters())
			given Conversion[unenroll, Future[Unit]] = (fun: unenroll) => fun.apply()
		}
		class completeSignup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.Enterprise])
		}
		object completeSignup {
			def apply(completionToken: String, enterpriseToken: String)(using auth: AuthToken, ec: ExecutionContext): completeSignup = new completeSignup(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/completeSignup")).addQueryStringParameters("completionToken" -> completionToken.toString, "enterpriseToken" -> enterpriseToken.toString))
			given Conversion[completeSignup, Future[Schema.Enterprise]] = (fun: completeSignup) => fun.apply()
		}
		class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEnterprise(body: Schema.Enterprise) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Enterprise])
		}
		object enroll {
			def apply(token: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/enroll")).addQueryStringParameters("token" -> token.toString))
		}
		class createEnrollmentToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CreateEnrollmentTokenResponse]) {
			/** [Optional] The length of time the enrollment token is valid, ranging from 1 minute to [`Durations.MAX_VALUE`](https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/util/Durations.html#MAX_VALUE), approximately 10,000 years. If not specified, the default duration is 1 hour.<br>Format: google-duration */
			def withEnrollmentTokenDuration(enrollmentTokenDuration: String) = new createEnrollmentToken(req.addQueryStringParameters("enrollmentToken.duration" -> enrollmentTokenDuration.toString))
			def apply() = req.execute("POST").map(_.json.as[Schema.CreateEnrollmentTokenResponse])
		}
		object createEnrollmentToken {
			def apply(enterpriseId: String, deviceType: String, enrollmentTokenToken: String, enrollmentTokenEnrollmentTokenType: String)(using auth: AuthToken, ec: ExecutionContext): createEnrollmentToken = new createEnrollmentToken(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/createEnrollmentToken")).addQueryStringParameters("deviceType" -> deviceType.toString, "enrollmentToken.token" -> enrollmentTokenToken.toString, "enrollmentToken.enrollmentTokenType" -> enrollmentTokenEnrollmentTokenType.toString))
			given Conversion[createEnrollmentToken, Future[Schema.CreateEnrollmentTokenResponse]] = (fun: createEnrollmentToken) => fun.apply()
		}
		class sendTestPushNotification(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EnterprisesSendTestPushNotificationResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.EnterprisesSendTestPushNotificationResponse])
		}
		object sendTestPushNotification {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): sendTestPushNotification = new sendTestPushNotification(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/sendTestPushNotification")).addQueryStringParameters())
			given Conversion[sendTestPushNotification, Future[Schema.EnterprisesSendTestPushNotificationResponse]] = (fun: sendTestPushNotification) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Enterprise])
		}
		object get {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Enterprise]] = (fun: get) => fun.apply()
		}
		class acknowledgeNotificationSet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object acknowledgeNotificationSet {
			def apply(notificationSetId: String)(using auth: AuthToken, ec: ExecutionContext): acknowledgeNotificationSet = new acknowledgeNotificationSet(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/acknowledgeNotificationSet")).addQueryStringParameters("notificationSetId" -> notificationSetId.toString))
			given Conversion[acknowledgeNotificationSet, Future[Unit]] = (fun: acknowledgeNotificationSet) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EnterprisesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EnterprisesListResponse])
		}
		object list {
			def apply(domain: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises")).addQueryStringParameters("domain" -> domain.toString))
			given Conversion[list, Future[Schema.EnterprisesListResponse]] = (fun: list) => fun.apply()
		}
		class getServiceAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccount]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ServiceAccount])
		}
		object getServiceAccount {
			def apply(enterpriseId: String, keyType: String)(using auth: AuthToken, ec: ExecutionContext): getServiceAccount = new getServiceAccount(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccount")).addQueryStringParameters("keyType" -> keyType.toString))
			given Conversion[getServiceAccount, Future[Schema.ServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		class setAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEnterpriseAccount(body: Schema.EnterpriseAccount) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EnterpriseAccount])
		}
		object setAccount {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): setAccount = new setAccount(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/account")).addQueryStringParameters())
		}
	}
	object entitlements {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntitlementsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EntitlementsListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements")).addQueryStringParameters())
			given Conversion[list, Future[Schema.EntitlementsListResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Entitlement]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Entitlement])
		}
		object get {
			def apply(enterpriseId: String, userId: String, entitlementId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Entitlement]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEntitlement(body: Schema.Entitlement) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Entitlement])
		}
		object update {
			def apply(enterpriseId: String, userId: String, entitlementId: String, install: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}")).addQueryStringParameters("install" -> install.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, entitlementId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object grouplicenseusers {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GroupLicenseUsersListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GroupLicenseUsersListResponse])
		}
		object list {
			def apply(enterpriseId: String, groupLicenseId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses/${groupLicenseId}/users")).addQueryStringParameters())
			given Conversion[list, Future[Schema.GroupLicenseUsersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object grouplicenses {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GroupLicensesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GroupLicensesListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses")).addQueryStringParameters())
			given Conversion[list, Future[Schema.GroupLicensesListResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GroupLicense]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GroupLicense])
		}
		object get {
			def apply(enterpriseId: String, groupLicenseId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses/${groupLicenseId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GroupLicense]] = (fun: get) => fun.apply()
		}
	}
	object installs {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstallsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.InstallsListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs")).addQueryStringParameters())
			given Conversion[list, Future[Schema.InstallsListResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Install]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Install])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Install]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstall(body: Schema.Install) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Install])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationsfordevice {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsForDeviceListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedConfigurationsForDeviceListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsForDeviceListResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfiguration]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedConfiguration])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedConfiguration]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withManagedConfiguration(body: Schema.ManagedConfiguration) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ManagedConfiguration])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationsforuser {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsForUserListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedConfigurationsForUserListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsForUserListResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfiguration]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedConfiguration])
		}
		object get {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedConfiguration]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withManagedConfiguration(body: Schema.ManagedConfiguration) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ManagedConfiguration])
		}
		object update {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationssettings {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsSettingsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManagedConfigurationsSettingsListResponse])
		}
		object list {
			def apply(enterpriseId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/managedConfigurationsSettings")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsSettingsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object permissions {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Permission]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Permission])
		}
		object get {
			def apply(permissionId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/permissions/${permissionId}")).addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Permission]] = (fun: get) => fun.apply()
		}
	}
	object products {
		class approve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProductsApproveRequest(body: Schema.ProductsApproveRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_ => ())
		}
		object approve {
			def apply(enterpriseId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): approve = new approve(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/approve")).addQueryStringParameters())
		}
		class generateApprovalUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductsGenerateApprovalUrlResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.ProductsGenerateApprovalUrlResponse])
		}
		object generateApprovalUrl {
			def apply(enterpriseId: String, productId: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): generateApprovalUrl = new generateApprovalUrl(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/generateApprovalUrl")).addQueryStringParameters("languageCode" -> languageCode.toString))
			given Conversion[generateApprovalUrl, Future[Schema.ProductsGenerateApprovalUrlResponse]] = (fun: generateApprovalUrl) => fun.apply()
		}
		class getPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductPermissions]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ProductPermissions])
		}
		object getPermissions {
			def apply(enterpriseId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): getPermissions = new getPermissions(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/permissions")).addQueryStringParameters())
			given Conversion[getPermissions, Future[Schema.ProductPermissions]] = (fun: getPermissions) => fun.apply()
		}
		class getAppRestrictionsSchema(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppRestrictionsSchema]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AppRestrictionsSchema])
		}
		object getAppRestrictionsSchema {
			def apply(enterpriseId: String, productId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): getAppRestrictionsSchema = new getAppRestrictionsSchema(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/appRestrictionsSchema")).addQueryStringParameters("language" -> language.toString))
			given Conversion[getAppRestrictionsSchema, Future[Schema.AppRestrictionsSchema]] = (fun: getAppRestrictionsSchema) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Product])
		}
		object get {
			def apply(enterpriseId: String, productId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}")).addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
		}
		class unapprove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("POST").map(_ => ())
		}
		object unapprove {
			def apply(enterpriseId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): unapprove = new unapprove(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/unapprove")).addQueryStringParameters())
			given Conversion[unapprove, Future[Unit]] = (fun: unapprove) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ProductsListResponse])
		}
		object list {
			def apply(enterpriseId: String, maxResults: Int, token: String, approved: Boolean, query: String, language: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products")).addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "approved" -> approved.toString, "query" -> query.toString, "language" -> language.toString))
			given Conversion[list, Future[Schema.ProductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object serviceaccountkeys {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withServiceAccountKey(body: Schema.ServiceAccountKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ServiceAccountKey])
		}
		object insert {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccountKeysListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ServiceAccountKeysListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ServiceAccountKeysListResponse]] = (fun: list) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, keyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys/${keyId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object storelayoutclusters {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withStoreCluster(body: Schema.StoreCluster) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.StoreCluster])
		}
		object insert {
			def apply(enterpriseId: String, pageId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StoreCluster]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StoreCluster])
		}
		object get {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.StoreCluster]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withStoreCluster(body: Schema.StoreCluster) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.StoreCluster])
		}
		object update {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StoreLayoutClustersListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StoreLayoutClustersListResponse])
		}
		object list {
			def apply(enterpriseId: String, pageId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters")).addQueryStringParameters())
			given Conversion[list, Future[Schema.StoreLayoutClustersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object storelayoutpages {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withStorePage(body: Schema.StorePage) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.StorePage])
		}
		object insert {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, pageId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StorePage]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StorePage])
		}
		object get {
			def apply(enterpriseId: String, pageId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.StorePage]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withStorePage(body: Schema.StorePage) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.StorePage])
		}
		object update {
			def apply(enterpriseId: String, pageId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StoreLayoutPagesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.StoreLayoutPagesListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages")).addQueryStringParameters())
			given Conversion[list, Future[Schema.StoreLayoutPagesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object users {
		class setAvailableProductSet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProductSet(body: Schema.ProductSet) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ProductSet])
		}
		object setAvailableProductSet {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): setAvailableProductSet = new setAvailableProductSet(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/availableProductSet")).addQueryStringParameters())
		}
		class generateAuthenticationToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuthenticationToken]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.AuthenticationToken])
		}
		object generateAuthenticationToken {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): generateAuthenticationToken = new generateAuthenticationToken(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/authenticationToken")).addQueryStringParameters())
			given Conversion[generateAuthenticationToken, Future[Schema.AuthenticationToken]] = (fun: generateAuthenticationToken) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.User])
		}
		object insert {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users")).addQueryStringParameters())
		}
		class revokeDeviceAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object revokeDeviceAccess {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): revokeDeviceAccess = new revokeDeviceAccess(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/deviceAccess")).addQueryStringParameters())
			given Conversion[revokeDeviceAccess, Future[Unit]] = (fun: revokeDeviceAccess) => fun.apply()
		}
		class getAvailableProductSet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductSet]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ProductSet])
		}
		object getAvailableProductSet {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): getAvailableProductSet = new getAvailableProductSet(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/availableProductSet")).addQueryStringParameters())
			given Conversion[getAvailableProductSet, Future[Schema.ProductSet]] = (fun: getAvailableProductSet) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.User])
		}
		object get {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.User])
		}
		object update {
			def apply(enterpriseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UsersListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.UsersListResponse])
		}
		object list {
			def apply(enterpriseId: String, email: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users")).addQueryStringParameters("email" -> email.toString))
			given Conversion[list, Future[Schema.UsersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object webapps {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withWebApp(body: Schema.WebApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WebApp])
		}
		object insert {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, webAppId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.WebApp])
		}
		object get {
			def apply(enterpriseId: String, webAppId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withWebApp(body: Schema.WebApp) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.WebApp])
		}
		object update {
			def apply(enterpriseId: String, webAppId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WebAppsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.WebAppsListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps")).addQueryStringParameters())
			given Conversion[list, Future[Schema.WebAppsListResponse]] = (fun: list) => fun.apply()
		}
	}
}
