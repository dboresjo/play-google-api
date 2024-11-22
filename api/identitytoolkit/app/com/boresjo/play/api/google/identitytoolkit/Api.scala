package com.boresjo.play.api.google.identitytoolkit

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* View and manage your data across Google Cloud Platform services */,
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */
	)

	private val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"

	object relyingparty {
		/** Set account info for a user. */
		class setAccountInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartySetAccountInfoRequest(body: Schema.IdentitytoolkitRelyingpartySetAccountInfoRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SetAccountInfoResponse])
		}
		object setAccountInfo {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): setAccountInfo = new setAccountInfo(ws.url(BASE_URL + s"setAccountInfo").addQueryStringParameters())
		}
		/** Sign out user. */
		class signOutUser(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartySignOutUserRequest(body: Schema.IdentitytoolkitRelyingpartySignOutUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentitytoolkitRelyingpartySignOutUserResponse])
		}
		object signOutUser {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): signOutUser = new signOutUser(ws.url(BASE_URL + s"signOutUser").addQueryStringParameters())
		}
		/** Verifies ownership of a phone number and creates/updates the user account accordingly. */
		class verifyPhoneNumber(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyVerifyPhoneNumberRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyPhoneNumberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentitytoolkitRelyingpartyVerifyPhoneNumberResponse])
		}
		object verifyPhoneNumber {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): verifyPhoneNumber = new verifyPhoneNumber(ws.url(BASE_URL + s"verifyPhoneNumber").addQueryStringParameters())
		}
		/** Returns the account info. */
		class getAccountInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyGetAccountInfoRequest(body: Schema.IdentitytoolkitRelyingpartyGetAccountInfoRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetAccountInfoResponse])
		}
		object getAccountInfo {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getAccountInfo = new getAccountInfo(ws.url(BASE_URL + s"getAccountInfo").addQueryStringParameters())
		}
		/** Get project configuration. */
		class getProjectConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse])
		}
		object getProjectConfig {
			def apply(delegatedProjectNumber: String, projectNumber: String)(using signer: RequestSigner, ec: ExecutionContext): getProjectConfig = new getProjectConfig(ws.url(BASE_URL + s"getProjectConfig").addQueryStringParameters("delegatedProjectNumber" -> delegatedProjectNumber.toString, "projectNumber" -> projectNumber.toString))
			given Conversion[getProjectConfig, Future[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse]] = (fun: getProjectConfig) => fun.apply()
		}
		/** Set project configuration. */
		class setProjectConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartySetProjectConfigRequest(body: Schema.IdentitytoolkitRelyingpartySetProjectConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentitytoolkitRelyingpartySetProjectConfigResponse])
		}
		object setProjectConfig {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): setProjectConfig = new setProjectConfig(ws.url(BASE_URL + s"setProjectConfig").addQueryStringParameters())
		}
		/** Signup new user. */
		class signupNewUser(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartySignupNewUserRequest(body: Schema.IdentitytoolkitRelyingpartySignupNewUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SignupNewUserResponse])
		}
		object signupNewUser {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): signupNewUser = new signupNewUser(ws.url(BASE_URL + s"signupNewUser").addQueryStringParameters())
		}
		/** Get recaptcha secure param. */
		class getRecaptchaParam(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetRecaptchaParamResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetRecaptchaParamResponse])
		}
		object getRecaptchaParam {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getRecaptchaParam = new getRecaptchaParam(ws.url(BASE_URL + s"getRecaptchaParam").addQueryStringParameters())
			given Conversion[getRecaptchaParam, Future[Schema.GetRecaptchaParamResponse]] = (fun: getRecaptchaParam) => fun.apply()
		}
		/** Verifies the assertion returned by the IdP. */
		class verifyAssertion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyVerifyAssertionRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyAssertionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyAssertionResponse])
		}
		object verifyAssertion {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): verifyAssertion = new verifyAssertion(ws.url(BASE_URL + s"verifyAssertion").addQueryStringParameters())
		}
		/** Verifies the user entered password. */
		class verifyPassword(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyVerifyPasswordRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyPasswordRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyPasswordResponse])
		}
		object verifyPassword {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): verifyPassword = new verifyPassword(ws.url(BASE_URL + s"verifyPassword").addQueryStringParameters())
		}
		/** Creates the URI used by the IdP to authenticate the user. */
		class createAuthUri(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyCreateAuthUriRequest(body: Schema.IdentitytoolkitRelyingpartyCreateAuthUriRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateAuthUriResponse])
		}
		object createAuthUri {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): createAuthUri = new createAuthUri(ws.url(BASE_URL + s"createAuthUri").addQueryStringParameters())
		}
		/** Batch upload existing user accounts. */
		class uploadAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyUploadAccountRequest(body: Schema.IdentitytoolkitRelyingpartyUploadAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadAccountResponse])
		}
		object uploadAccount {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): uploadAccount = new uploadAccount(ws.url(BASE_URL + s"uploadAccount").addQueryStringParameters())
		}
		/** Get token signing public key. */
		class getPublicKeys(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse])
		}
		object getPublicKeys {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getPublicKeys = new getPublicKeys(ws.url(BASE_URL + s"publicKeys").addQueryStringParameters())
			given Conversion[getPublicKeys, Future[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse]] = (fun: getPublicKeys) => fun.apply()
		}
		/** Delete user account. */
		class deleteAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyDeleteAccountRequest(body: Schema.IdentitytoolkitRelyingpartyDeleteAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeleteAccountResponse])
		}
		object deleteAccount {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): deleteAccount = new deleteAccount(ws.url(BASE_URL + s"deleteAccount").addQueryStringParameters())
		}
		/** Verifies the developer asserted ID token. */
		class verifyCustomToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyVerifyCustomTokenRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyCustomTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyCustomTokenResponse])
		}
		object verifyCustomToken {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): verifyCustomToken = new verifyCustomToken(ws.url(BASE_URL + s"verifyCustomToken").addQueryStringParameters())
		}
		/** Get a code for user action confirmation. */
		class getOobConfirmationCode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withRelyingparty(body: Schema.Relyingparty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetOobConfirmationCodeResponse])
		}
		object getOobConfirmationCode {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getOobConfirmationCode = new getOobConfirmationCode(ws.url(BASE_URL + s"getOobConfirmationCode").addQueryStringParameters())
		}
		/** Reset password for a user. */
		class resetPassword(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyResetPasswordRequest(body: Schema.IdentitytoolkitRelyingpartyResetPasswordRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResetPasswordResponse])
		}
		object resetPassword {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): resetPassword = new resetPassword(ws.url(BASE_URL + s"resetPassword").addQueryStringParameters())
		}
		/** Batch download user accounts. */
		class downloadAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyDownloadAccountRequest(body: Schema.IdentitytoolkitRelyingpartyDownloadAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DownloadAccountResponse])
		}
		object downloadAccount {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): downloadAccount = new downloadAccount(ws.url(BASE_URL + s"downloadAccount").addQueryStringParameters())
		}
		/** Send SMS verification code. */
		class sendVerificationCode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartySendVerificationCodeRequest(body: Schema.IdentitytoolkitRelyingpartySendVerificationCodeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentitytoolkitRelyingpartySendVerificationCodeResponse])
		}
		object sendVerificationCode {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): sendVerificationCode = new sendVerificationCode(ws.url(BASE_URL + s"sendVerificationCode").addQueryStringParameters())
		}
		/** Reset password for a user. */
		class emailLinkSignin(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIdentitytoolkitRelyingpartyEmailLinkSigninRequest(body: Schema.IdentitytoolkitRelyingpartyEmailLinkSigninRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EmailLinkSigninResponse])
		}
		object emailLinkSignin {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): emailLinkSignin = new emailLinkSignin(ws.url(BASE_URL + s"emailLinkSignin").addQueryStringParameters())
		}
	}
}
