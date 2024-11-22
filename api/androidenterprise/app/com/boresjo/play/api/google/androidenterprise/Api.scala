package com.boresjo.play.api.google.androidenterprise

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
		"""https://www.googleapis.com/auth/androidenterprise""" /* Manage corporate Android devices */
	)

	private val BASE_URL = "https://androidenterprise.googleapis.com/"

	object devices {
		/** Sets whether a device's access to Google services is enabled or disabled. The device state takes effect only if enforcing EMM policies on Android devices is enabled in the Google Admin Console. Otherwise, the device state is ignored and all devices are allowed access to Google services. This is only supported for Google-managed users. */
		class setState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withDeviceState(body: Schema.DeviceState) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.DeviceState])
		}
		object setState {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): setState = new setState(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/state").addQueryStringParameters())
		}
		/** Retrieves whether a device's access to Google services is enabled or disabled. The device state takes effect only if enforcing EMM policies on Android devices is enabled in the Google Admin Console. Otherwise, the device state is ignored and all devices are allowed access to Google services. This is only supported for Google-managed users. */
		class getState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeviceState]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DeviceState])
		}
		object getState {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): getState = new getState(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/state").addQueryStringParameters())
			given Conversion[getState, Future[Schema.DeviceState]] = (fun: getState) => fun.apply()
		}
		/** Uploads a report containing any changes in app states on the device since the last report was generated. You can call this method up to 3 times every 24 hours for a given device. If you exceed the quota, then the Google Play EMM API returns HTTP 429 Too Many Requests. */
		class forceReportUpload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object forceReportUpload {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): forceReportUpload = new forceReportUpload(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/forceReportUpload").addQueryStringParameters())
			given Conversion[forceReportUpload, Future[Unit]] = (fun: forceReportUpload) => fun.apply()
		}
		/** Retrieves the details of a device. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Device])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
		}
		/** Updates the device policy. To ensure the policy is properly enforced, you need to prevent unmanaged accounts from accessing Google Play by setting the allowed_accounts in the managed configuration for the Google Play package. See restrict accounts in Google Play. When provisioning a new device, you should set the device policy using this method before adding the managed Google Play Account to the device, otherwise the policy will not be applied for a short period of time after adding the account to the device. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withDevice(body: Schema.Device) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Device])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		/** Retrieves the IDs of all of a user's devices. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DevicesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DevicesListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices").addQueryStringParameters())
			given Conversion[list, Future[Schema.DevicesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object enterprises {
		/** Returns the store layout for the enterprise. If the store layout has not been set, returns "basic" as the store layout type and no homepage. */
		class getStoreLayout(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StoreLayout]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StoreLayout])
		}
		object getStoreLayout {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): getStoreLayout = new getStoreLayout(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout").addQueryStringParameters())
			given Conversion[getStoreLayout, Future[Schema.StoreLayout]] = (fun: getStoreLayout) => fun.apply()
		}
		/** Generates a sign-up URL. */
		class generateSignupUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SignupInfo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Optional. Email address used to prefill the admin field of the enterprise signup form. This value is a hint only and can be altered by the user. */
			def withAdminEmail(adminEmail: String) = new generateSignupUrl(req.addQueryStringParameters("adminEmail" -> adminEmail.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.SignupInfo])
		}
		object generateSignupUrl {
			def apply(callbackUrl: String)(using signer: RequestSigner, ec: ExecutionContext): generateSignupUrl = new generateSignupUrl(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/signupUrl").addQueryStringParameters("callbackUrl" -> callbackUrl.toString))
			given Conversion[generateSignupUrl, Future[Schema.SignupInfo]] = (fun: generateSignupUrl) => fun.apply()
		}
		/** Returns a unique token to access an embeddable UI. To generate a web UI, pass the generated token into the managed Google Play javascript API. Each token may only be used to start one UI session. See the JavaScript API documentation for further information. */
		class createWebToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withAdministratorWebTokenSpec(body: Schema.AdministratorWebTokenSpec) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AdministratorWebToken])
		}
		object createWebToken {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): createWebToken = new createWebToken(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/createWebToken").addQueryStringParameters())
		}
		/** Sets the store layout for the enterprise. By default, storeLayoutType is set to "basic" and the basic store layout is enabled. The basic layout only contains apps approved by the admin, and that have been added to the available product set for a user (using the setAvailableProductSet call). Apps on the page are sorted in order of their product ID value. If you create a custom store layout (by setting storeLayoutType = "custom" and setting a homepage), the basic store layout is disabled. */
		class setStoreLayout(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withStoreLayout(body: Schema.StoreLayout) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.StoreLayout])
		}
		object setStoreLayout {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): setStoreLayout = new setStoreLayout(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout").addQueryStringParameters())
		}
		/** Pulls and returns a notification set for the enterprises associated with the service account authenticated for the request. The notification set may be empty if no notification are pending. A notification set returned needs to be acknowledged within 20 seconds by calling Enterprises.AcknowledgeNotificationSet, unless the notification set is empty. Notifications that are not acknowledged within the 20 seconds will eventually be included again in the response to another PullNotificationSet request, and those that are never acknowledged will ultimately be deleted according to the Google Cloud Platform Pub/Sub system policy. Multiple requests might be performed concurrently to retrieve notifications, in which case the pending notifications (if any) will be split among each caller, if any are pending. If no notifications are present, an empty notification list is returned. Subsequent requests may return more notifications once they become available. */
		class pullNotificationSet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NotificationSet]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.NotificationSet])
		}
		object pullNotificationSet {
			def apply(requestMode: String)(using signer: RequestSigner, ec: ExecutionContext): pullNotificationSet = new pullNotificationSet(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/pullNotificationSet").addQueryStringParameters("requestMode" -> requestMode.toString))
			given Conversion[pullNotificationSet, Future[Schema.NotificationSet]] = (fun: pullNotificationSet) => fun.apply()
		}
		/** Unenrolls an enterprise from the calling EMM. */
		class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object unenroll {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/unenroll").addQueryStringParameters())
			given Conversion[unenroll, Future[Unit]] = (fun: unenroll) => fun.apply()
		}
		/** Completes the signup flow, by specifying the Completion token and Enterprise token. This request must not be called multiple times for a given Enterprise Token. */
		class completeSignup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Enterprise])
		}
		object completeSignup {
			def apply(completionToken: String, enterpriseToken: String)(using signer: RequestSigner, ec: ExecutionContext): completeSignup = new completeSignup(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/completeSignup").addQueryStringParameters("completionToken" -> completionToken.toString, "enterpriseToken" -> enterpriseToken.toString))
			given Conversion[completeSignup, Future[Schema.Enterprise]] = (fun: completeSignup) => fun.apply()
		}
		/** Enrolls an enterprise with the calling EMM. */
		class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withEnterprise(body: Schema.Enterprise) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Enterprise])
		}
		object enroll {
			def apply(token: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/enroll").addQueryStringParameters("token" -> token.toString))
		}
		/** Returns a token for device enrollment. The DPC can encode this token within the QR/NFC/zero-touch enrollment payload or fetch it before calling the on-device API to authenticate the user. The token can be generated for each device or reused across multiple devices. */
		class createEnrollmentToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CreateEnrollmentTokenResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** [Optional] The length of time the enrollment token is valid, ranging from 1 minute to [`Durations.MAX_VALUE`](https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/util/Durations.html#MAX_VALUE), approximately 10,000 years. If not specified, the default duration is 1 hour.<br>Format: google-duration */
			def withEnrollmentTokenDuration(enrollmentTokenDuration: String) = new createEnrollmentToken(req.addQueryStringParameters("enrollmentToken.duration" -> enrollmentTokenDuration.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.CreateEnrollmentTokenResponse])
		}
		object createEnrollmentToken {
			def apply(enterpriseId: String, deviceType: String, enrollmentTokenToken: String, enrollmentTokenEnrollmentTokenType: String)(using signer: RequestSigner, ec: ExecutionContext): createEnrollmentToken = new createEnrollmentToken(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/createEnrollmentToken").addQueryStringParameters("deviceType" -> deviceType.toString, "enrollmentToken.token" -> enrollmentTokenToken.toString, "enrollmentToken.enrollmentTokenType" -> enrollmentTokenEnrollmentTokenType.toString))
			given Conversion[createEnrollmentToken, Future[Schema.CreateEnrollmentTokenResponse]] = (fun: createEnrollmentToken) => fun.apply()
		}
		/** Sends a test notification to validate the EMM integration with the Google Cloud Pub/Sub service for this enterprise. */
		class sendTestPushNotification(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EnterprisesSendTestPushNotificationResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.EnterprisesSendTestPushNotificationResponse])
		}
		object sendTestPushNotification {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): sendTestPushNotification = new sendTestPushNotification(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/sendTestPushNotification").addQueryStringParameters())
			given Conversion[sendTestPushNotification, Future[Schema.EnterprisesSendTestPushNotificationResponse]] = (fun: sendTestPushNotification) => fun.apply()
		}
		/** Retrieves the name and domain of an enterprise. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Enterprise])
		}
		object get {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Enterprise]] = (fun: get) => fun.apply()
		}
		/** Acknowledges notifications that were received from Enterprises.PullNotificationSet to prevent subsequent calls from returning the same notifications. */
		class acknowledgeNotificationSet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object acknowledgeNotificationSet {
			def apply(notificationSetId: String)(using signer: RequestSigner, ec: ExecutionContext): acknowledgeNotificationSet = new acknowledgeNotificationSet(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/acknowledgeNotificationSet").addQueryStringParameters("notificationSetId" -> notificationSetId.toString))
			given Conversion[acknowledgeNotificationSet, Future[Unit]] = (fun: acknowledgeNotificationSet) => fun.apply()
		}
		/** Looks up an enterprise by domain name. This is only supported for enterprises created via the Google-initiated creation flow. Lookup of the id is not needed for enterprises created via the EMM-initiated flow since the EMM learns the enterprise ID in the callback specified in the Enterprises.generateSignupUrl call. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EnterprisesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EnterprisesListResponse])
		}
		object list {
			def apply(domain: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises").addQueryStringParameters("domain" -> domain.toString))
			given Conversion[list, Future[Schema.EnterprisesListResponse]] = (fun: list) => fun.apply()
		}
		/** Returns a service account and credentials. The service account can be bound to the enterprise by calling setAccount. The service account is unique to this enterprise and EMM, and will be deleted if the enterprise is unbound. The credentials contain private key data and are not stored server-side. This method can only be called after calling Enterprises.Enroll or Enterprises.CompleteSignup, and before Enterprises.SetAccount; at other times it will return an error. Subsequent calls after the first will generate a new, unique set of credentials, and invalidate the previously generated credentials. Once the service account is bound to the enterprise, it can be managed using the serviceAccountKeys resource. */
		class getServiceAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServiceAccount])
		}
		object getServiceAccount {
			def apply(enterpriseId: String, keyType: String)(using signer: RequestSigner, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccount").addQueryStringParameters("keyType" -> keyType.toString))
			given Conversion[getServiceAccount, Future[Schema.ServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		/** Sets the account that will be used to authenticate to the API as the enterprise. */
		class setAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withEnterpriseAccount(body: Schema.EnterpriseAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EnterpriseAccount])
		}
		object setAccount {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): setAccount = new setAccount(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/account").addQueryStringParameters())
		}
	}
	object entitlements {
		/** Lists all entitlements for the specified user. Only the ID is set. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntitlementsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntitlementsListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements").addQueryStringParameters())
			given Conversion[list, Future[Schema.EntitlementsListResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves details of an entitlement. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Entitlement]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Entitlement])
		}
		object get {
			def apply(enterpriseId: String, userId: String, entitlementId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Entitlement]] = (fun: get) => fun.apply()
		}
		/** Adds or updates an entitlement to an app for a user. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withEntitlement(body: Schema.Entitlement) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Entitlement])
		}
		object update {
			def apply(enterpriseId: String, userId: String, entitlementId: String, install: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}").addQueryStringParameters("install" -> install.toString))
		}
		/** Removes an entitlement to an app for a user. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, entitlementId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/entitlements/${entitlementId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object grouplicenseusers {
		/** Retrieves the IDs of the users who have been granted entitlements under the license. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GroupLicenseUsersListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GroupLicenseUsersListResponse])
		}
		object list {
			def apply(enterpriseId: String, groupLicenseId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses/${groupLicenseId}/users").addQueryStringParameters())
			given Conversion[list, Future[Schema.GroupLicenseUsersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object grouplicenses {
		/** Retrieves IDs of all products for which the enterprise has a group license. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GroupLicensesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GroupLicensesListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses").addQueryStringParameters())
			given Conversion[list, Future[Schema.GroupLicensesListResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves details of an enterprise's group license for a product. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GroupLicense]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GroupLicense])
		}
		object get {
			def apply(enterpriseId: String, groupLicenseId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/groupLicenses/${groupLicenseId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GroupLicense]] = (fun: get) => fun.apply()
		}
	}
	object installs {
		/** Retrieves the details of all apps installed on the specified device. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstallsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstallsListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs").addQueryStringParameters())
			given Conversion[list, Future[Schema.InstallsListResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves details of an installation of an app on a device. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Install]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Install])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Install]] = (fun: get) => fun.apply()
		}
		/** Requests to install the latest version of an app to a device. If the app is already installed, then it is updated to the latest version if necessary. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withInstall(body: Schema.Install) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Install])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}").addQueryStringParameters())
		}
		/** Requests to remove an app from a device. A call to get or list will still show the app as installed on the device until it is actually removed. A successful response indicates that a removal request has been sent to the device. The call will be considered successful even if the app is not present on the device (e.g. it was never installed, or was removed by the user). */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, deviceId: String, installId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/installs/${installId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationsfordevice {
		/** Lists all the per-device managed configurations for the specified device. Only the ID is set. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsForDeviceListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedConfigurationsForDeviceListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String, deviceId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice").addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsForDeviceListResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves details of a per-device managed configuration. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfiguration]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedConfiguration])
		}
		object get {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedConfiguration]] = (fun: get) => fun.apply()
		}
		/** Adds or updates a per-device managed configuration for an app for the specified device. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withManagedConfiguration(body: Schema.ManagedConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ManagedConfiguration])
		}
		object update {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}").addQueryStringParameters())
		}
		/** Removes a per-device managed configuration for an app for the specified device. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, deviceId: String, managedConfigurationForDeviceId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/devices/${deviceId}/managedConfigurationsForDevice/${managedConfigurationForDeviceId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationsforuser {
		/** Lists all the per-user managed configurations for the specified user. Only the ID is set. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsForUserListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedConfigurationsForUserListResponse])
		}
		object list {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser").addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsForUserListResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves details of a per-user managed configuration for an app for the specified user. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfiguration]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedConfiguration])
		}
		object get {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedConfiguration]] = (fun: get) => fun.apply()
		}
		/** Adds or updates the managed configuration settings for an app for the specified user. If you support the Managed configurations iframe, you can apply managed configurations to a user by specifying an mcmId and its associated configuration variables (if any) in the request. Alternatively, all EMMs can apply managed configurations by passing a list of managed properties. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withManagedConfiguration(body: Schema.ManagedConfiguration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ManagedConfiguration])
		}
		object update {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}").addQueryStringParameters())
		}
		/** Removes a per-user managed configuration for an app for the specified user. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String, managedConfigurationForUserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/managedConfigurationsForUser/${managedConfigurationForUserId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object managedconfigurationssettings {
		/** Lists all the managed configurations settings for the specified app. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedConfigurationsSettingsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedConfigurationsSettingsListResponse])
		}
		object list {
			def apply(enterpriseId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/managedConfigurationsSettings").addQueryStringParameters())
			given Conversion[list, Future[Schema.ManagedConfigurationsSettingsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object permissions {
		/** Retrieves details of an Android app permission for display to an enterprise admin. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Permission]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Permission])
		}
		object get {
			def apply(permissionId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/permissions/${permissionId}").addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Permission]] = (fun: get) => fun.apply()
		}
	}
	object products {
		/**  Approves the specified product and the relevant app permissions, if any. The maximum number of products that you can approve per enterprise customer is 1,000. To learn how to use managed Google Play to design and create a store layout to display approved products to your users, see Store Layout Design. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations.  */
		class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withProductsApproveRequest(body: Schema.ProductsApproveRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object approve {
			def apply(enterpriseId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/approve").addQueryStringParameters())
		}
		/** Generates a URL that can be rendered in an iframe to display the permissions (if any) of a product. An enterprise admin must view these permissions and accept them on behalf of their organization in order to approve that product. Admins should accept the displayed permissions by interacting with a separate UI element in the EMM console, which in turn should trigger the use of this URL as the approvalUrlInfo.approvalUrl property in a Products.approve call to approve the product. This URL can only be used to display permissions for up to 1 day. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations.  */
		class generateApprovalUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductsGenerateApprovalUrlResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ProductsGenerateApprovalUrlResponse])
		}
		object generateApprovalUrl {
			def apply(enterpriseId: String, productId: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): generateApprovalUrl = new generateApprovalUrl(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/generateApprovalUrl").addQueryStringParameters("languageCode" -> languageCode.toString))
			given Conversion[generateApprovalUrl, Future[Schema.ProductsGenerateApprovalUrlResponse]] = (fun: generateApprovalUrl) => fun.apply()
		}
		/** Retrieves the Android app permissions required by this app. */
		class getPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductPermissions]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductPermissions])
		}
		object getPermissions {
			def apply(enterpriseId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): getPermissions = new getPermissions(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/permissions").addQueryStringParameters())
			given Conversion[getPermissions, Future[Schema.ProductPermissions]] = (fun: getPermissions) => fun.apply()
		}
		/** Retrieves the schema that defines the configurable properties for this product. All products have a schema, but this schema may be empty if no managed configurations have been defined. This schema can be used to populate a UI that allows an admin to configure the product. To apply a managed configuration based on the schema obtained using this API, see Managed Configurations through Play. */
		class getAppRestrictionsSchema(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppRestrictionsSchema]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AppRestrictionsSchema])
		}
		object getAppRestrictionsSchema {
			def apply(enterpriseId: String, productId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): getAppRestrictionsSchema = new getAppRestrictionsSchema(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/appRestrictionsSchema").addQueryStringParameters("language" -> language.toString))
			given Conversion[getAppRestrictionsSchema, Future[Schema.AppRestrictionsSchema]] = (fun: getAppRestrictionsSchema) => fun.apply()
		}
		/** Retrieves details of a product for display to an enterprise admin. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Product])
		}
		object get {
			def apply(enterpriseId: String, productId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}").addQueryStringParameters("language" -> language.toString))
			given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
		}
		/** Unapproves the specified product (and the relevant app permissions, if any) &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class unapprove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object unapprove {
			def apply(enterpriseId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): unapprove = new unapprove(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products/${productId}/unapprove").addQueryStringParameters())
			given Conversion[unapprove, Future[Unit]] = (fun: unapprove) => fun.apply()
		}
		/** Finds approved products that match a query, or all approved products if there is no query. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations.  */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductsListResponse])
		}
		object list {
			def apply(enterpriseId: String, maxResults: Int, token: String, approved: Boolean, query: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/products").addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "approved" -> approved.toString, "query" -> query.toString, "language" -> language.toString))
			given Conversion[list, Future[Schema.ProductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object serviceaccountkeys {
		/** Generates new credentials for the service account associated with this enterprise. The calling service account must have been retrieved by calling Enterprises.GetServiceAccount and must have been set as the enterprise service account by calling Enterprises.SetAccount. Only the type of the key should be populated in the resource to be inserted. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withServiceAccountKey(body: Schema.ServiceAccountKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ServiceAccountKey])
		}
		object insert {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys").addQueryStringParameters())
		}
		/** Lists all active credentials for the service account associated with this enterprise. Only the ID and key type are returned. The calling service account must have been retrieved by calling Enterprises.GetServiceAccount and must have been set as the enterprise service account by calling Enterprises.SetAccount. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccountKeysListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServiceAccountKeysListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys").addQueryStringParameters())
			given Conversion[list, Future[Schema.ServiceAccountKeysListResponse]] = (fun: list) => fun.apply()
		}
		/** Removes and invalidates the specified credentials for the service account associated with this enterprise. The calling service account must have been retrieved by calling Enterprises.GetServiceAccount and must have been set as the enterprise service account by calling Enterprises.SetAccount. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, keyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/serviceAccountKeys/${keyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object storelayoutclusters {
		/** Inserts a new cluster in a page. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withStoreCluster(body: Schema.StoreCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StoreCluster])
		}
		object insert {
			def apply(enterpriseId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters").addQueryStringParameters())
		}
		/** Deletes a cluster. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves details of a cluster. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StoreCluster]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StoreCluster])
		}
		object get {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.StoreCluster]] = (fun: get) => fun.apply()
		}
		/** Updates a cluster. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withStoreCluster(body: Schema.StoreCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.StoreCluster])
		}
		object update {
			def apply(enterpriseId: String, pageId: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters/${clusterId}").addQueryStringParameters())
		}
		/** Retrieves the details of all clusters on the specified page. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StoreLayoutClustersListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StoreLayoutClustersListResponse])
		}
		object list {
			def apply(enterpriseId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}/clusters").addQueryStringParameters())
			given Conversion[list, Future[Schema.StoreLayoutClustersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object storelayoutpages {
		/** Inserts a new store page. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withStorePage(body: Schema.StorePage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StorePage])
		}
		object insert {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages").addQueryStringParameters())
		}
		/** Deletes a store page. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves details of a store page. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StorePage]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StorePage])
		}
		object get {
			def apply(enterpriseId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.StorePage]] = (fun: get) => fun.apply()
		}
		/** Updates the content of a store page. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withStorePage(body: Schema.StorePage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.StorePage])
		}
		object update {
			def apply(enterpriseId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages/${pageId}").addQueryStringParameters())
		}
		/** Retrieves the details of all pages in the store. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StoreLayoutPagesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StoreLayoutPagesListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/storeLayout/pages").addQueryStringParameters())
			given Conversion[list, Future[Schema.StoreLayoutPagesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object users {
		/** Modifies the set of products that a user is entitled to access (referred to as &#42;whitelisted&#42; products). Only products that are approved or products that were previously approved (products with revoked approval) can be whitelisted. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class setAvailableProductSet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withProductSet(body: Schema.ProductSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ProductSet])
		}
		object setAvailableProductSet {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): setAvailableProductSet = new setAvailableProductSet(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/availableProductSet").addQueryStringParameters())
		}
		/** Generates an authentication token which the device policy client can use to provision the given EMM-managed user account on a device. The generated token is single-use and expires after a few minutes. You can provision a maximum of 10 devices per user. This call only works with EMM-managed accounts. */
		class generateAuthenticationToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuthenticationToken]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AuthenticationToken])
		}
		object generateAuthenticationToken {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): generateAuthenticationToken = new generateAuthenticationToken(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/authenticationToken").addQueryStringParameters())
			given Conversion[generateAuthenticationToken, Future[Schema.AuthenticationToken]] = (fun: generateAuthenticationToken) => fun.apply()
		}
		/** Creates a new EMM-managed user. The Users resource passed in the body of the request should include an accountIdentifier and an accountType. If a corresponding user already exists with the same account identifier, the user will be updated with the resource. In this case only the displayName field can be changed. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
		}
		object insert {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users").addQueryStringParameters())
		}
		/** Revokes access to all devices currently provisioned to the user. The user will no longer be able to use the managed Play store on any of their managed devices. This call only works with EMM-managed accounts. */
		class revokeDeviceAccess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object revokeDeviceAccess {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): revokeDeviceAccess = new revokeDeviceAccess(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/deviceAccess").addQueryStringParameters())
			given Conversion[revokeDeviceAccess, Future[Unit]] = (fun: revokeDeviceAccess) => fun.apply()
		}
		/** Retrieves the set of products a user is entitled to access. &#42;&#42;Note:&#42;&#42; This item has been deprecated. New integrations cannot use this method and can refer to our new recommendations. */
		class getAvailableProductSet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductSet]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductSet])
		}
		object getAvailableProductSet {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): getAvailableProductSet = new getAvailableProductSet(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}/availableProductSet").addQueryStringParameters())
			given Conversion[getAvailableProductSet, Future[Schema.ProductSet]] = (fun: getAvailableProductSet) => fun.apply()
		}
		/** Deleted an EMM-managed user. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a user's details. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		/** Updates the details of an EMM-managed user. Can be used with EMM-managed users only (not Google managed users). Pass the new details in the Users resource in the request body. Only the displayName field can be changed. Other fields must either be unset or have the currently active value. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.User])
		}
		object update {
			def apply(enterpriseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users/${userId}").addQueryStringParameters())
		}
		/** Looks up a user by primary email address. This is only supported for Google-managed users. Lookup of the id is not needed for EMM-managed users because the id is already returned in the result of the Users.insert call. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UsersListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UsersListResponse])
		}
		object list {
			def apply(enterpriseId: String, email: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/users").addQueryStringParameters("email" -> email.toString))
			given Conversion[list, Future[Schema.UsersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object webapps {
		/** Creates a new web app for the enterprise. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WebApp])
		}
		object insert {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps").addQueryStringParameters())
		}
		/** Deletes an existing web app. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(enterpriseId: String, webAppId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets an existing web app. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebApp])
		}
		object get {
			def apply(enterpriseId: String, webAppId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
		}
		/** Updates an existing web app. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.WebApp])
		}
		object update {
			def apply(enterpriseId: String, webAppId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps/${webAppId}").addQueryStringParameters())
		}
		/** Retrieves the details of all web apps for a given enterprise. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebAppsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidenterprise""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebAppsListResponse])
		}
		object list {
			def apply(enterpriseId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidenterprise/v1/enterprises/${enterpriseId}/webApps").addQueryStringParameters())
			given Conversion[list, Future[Schema.WebAppsListResponse]] = (fun: list) => fun.apply()
		}
	}
}
