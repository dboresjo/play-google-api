package com.boresjo.play.api.google.identitytoolkit

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"

	object relyingparty {
		class setAccountInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartySetAccountInfoRequest(body: Schema.IdentitytoolkitRelyingpartySetAccountInfoRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SetAccountInfoResponse])
		}
		object setAccountInfo {
			def apply()(using auth: AuthToken, ec: ExecutionContext): setAccountInfo = new setAccountInfo(auth(ws.url(BASE_URL + s"setAccountInfo")).addQueryStringParameters())
		}
		class signOutUser(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartySignOutUserRequest(body: Schema.IdentitytoolkitRelyingpartySignOutUserRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentitytoolkitRelyingpartySignOutUserResponse])
		}
		object signOutUser {
			def apply()(using auth: AuthToken, ec: ExecutionContext): signOutUser = new signOutUser(auth(ws.url(BASE_URL + s"signOutUser")).addQueryStringParameters())
		}
		class verifyPhoneNumber(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyVerifyPhoneNumberRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyPhoneNumberRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentitytoolkitRelyingpartyVerifyPhoneNumberResponse])
		}
		object verifyPhoneNumber {
			def apply()(using auth: AuthToken, ec: ExecutionContext): verifyPhoneNumber = new verifyPhoneNumber(auth(ws.url(BASE_URL + s"verifyPhoneNumber")).addQueryStringParameters())
		}
		class getAccountInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyGetAccountInfoRequest(body: Schema.IdentitytoolkitRelyingpartyGetAccountInfoRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GetAccountInfoResponse])
		}
		object getAccountInfo {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getAccountInfo = new getAccountInfo(auth(ws.url(BASE_URL + s"getAccountInfo")).addQueryStringParameters())
		}
		class getProjectConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse])
		}
		object getProjectConfig {
			def apply(delegatedProjectNumber: String, projectNumber: String)(using auth: AuthToken, ec: ExecutionContext): getProjectConfig = new getProjectConfig(auth(ws.url(BASE_URL + s"getProjectConfig")).addQueryStringParameters("delegatedProjectNumber" -> delegatedProjectNumber.toString, "projectNumber" -> projectNumber.toString))
			given Conversion[getProjectConfig, Future[Schema.IdentitytoolkitRelyingpartyGetProjectConfigResponse]] = (fun: getProjectConfig) => fun.apply()
		}
		class setProjectConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartySetProjectConfigRequest(body: Schema.IdentitytoolkitRelyingpartySetProjectConfigRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentitytoolkitRelyingpartySetProjectConfigResponse])
		}
		object setProjectConfig {
			def apply()(using auth: AuthToken, ec: ExecutionContext): setProjectConfig = new setProjectConfig(auth(ws.url(BASE_URL + s"setProjectConfig")).addQueryStringParameters())
		}
		class signupNewUser(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartySignupNewUserRequest(body: Schema.IdentitytoolkitRelyingpartySignupNewUserRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SignupNewUserResponse])
		}
		object signupNewUser {
			def apply()(using auth: AuthToken, ec: ExecutionContext): signupNewUser = new signupNewUser(auth(ws.url(BASE_URL + s"signupNewUser")).addQueryStringParameters())
		}
		class getRecaptchaParam(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetRecaptchaParamResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GetRecaptchaParamResponse])
		}
		object getRecaptchaParam {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getRecaptchaParam = new getRecaptchaParam(auth(ws.url(BASE_URL + s"getRecaptchaParam")).addQueryStringParameters())
			given Conversion[getRecaptchaParam, Future[Schema.GetRecaptchaParamResponse]] = (fun: getRecaptchaParam) => fun.apply()
		}
		class verifyAssertion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyVerifyAssertionRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyAssertionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.VerifyAssertionResponse])
		}
		object verifyAssertion {
			def apply()(using auth: AuthToken, ec: ExecutionContext): verifyAssertion = new verifyAssertion(auth(ws.url(BASE_URL + s"verifyAssertion")).addQueryStringParameters())
		}
		class verifyPassword(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyVerifyPasswordRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyPasswordRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.VerifyPasswordResponse])
		}
		object verifyPassword {
			def apply()(using auth: AuthToken, ec: ExecutionContext): verifyPassword = new verifyPassword(auth(ws.url(BASE_URL + s"verifyPassword")).addQueryStringParameters())
		}
		class createAuthUri(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyCreateAuthUriRequest(body: Schema.IdentitytoolkitRelyingpartyCreateAuthUriRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CreateAuthUriResponse])
		}
		object createAuthUri {
			def apply()(using auth: AuthToken, ec: ExecutionContext): createAuthUri = new createAuthUri(auth(ws.url(BASE_URL + s"createAuthUri")).addQueryStringParameters())
		}
		class uploadAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyUploadAccountRequest(body: Schema.IdentitytoolkitRelyingpartyUploadAccountRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UploadAccountResponse])
		}
		object uploadAccount {
			def apply()(using auth: AuthToken, ec: ExecutionContext): uploadAccount = new uploadAccount(auth(ws.url(BASE_URL + s"uploadAccount")).addQueryStringParameters())
		}
		class getPublicKeys(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse])
		}
		object getPublicKeys {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getPublicKeys = new getPublicKeys(auth(ws.url(BASE_URL + s"publicKeys")).addQueryStringParameters())
			given Conversion[getPublicKeys, Future[Schema.IdentitytoolkitRelyingpartyGetPublicKeysResponse]] = (fun: getPublicKeys) => fun.apply()
		}
		class deleteAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyDeleteAccountRequest(body: Schema.IdentitytoolkitRelyingpartyDeleteAccountRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DeleteAccountResponse])
		}
		object deleteAccount {
			def apply()(using auth: AuthToken, ec: ExecutionContext): deleteAccount = new deleteAccount(auth(ws.url(BASE_URL + s"deleteAccount")).addQueryStringParameters())
		}
		class verifyCustomToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyVerifyCustomTokenRequest(body: Schema.IdentitytoolkitRelyingpartyVerifyCustomTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.VerifyCustomTokenResponse])
		}
		object verifyCustomToken {
			def apply()(using auth: AuthToken, ec: ExecutionContext): verifyCustomToken = new verifyCustomToken(auth(ws.url(BASE_URL + s"verifyCustomToken")).addQueryStringParameters())
		}
		class getOobConfirmationCode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRelyingparty(body: Schema.Relyingparty) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GetOobConfirmationCodeResponse])
		}
		object getOobConfirmationCode {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getOobConfirmationCode = new getOobConfirmationCode(auth(ws.url(BASE_URL + s"getOobConfirmationCode")).addQueryStringParameters())
		}
		class resetPassword(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyResetPasswordRequest(body: Schema.IdentitytoolkitRelyingpartyResetPasswordRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ResetPasswordResponse])
		}
		object resetPassword {
			def apply()(using auth: AuthToken, ec: ExecutionContext): resetPassword = new resetPassword(auth(ws.url(BASE_URL + s"resetPassword")).addQueryStringParameters())
		}
		class downloadAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyDownloadAccountRequest(body: Schema.IdentitytoolkitRelyingpartyDownloadAccountRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DownloadAccountResponse])
		}
		object downloadAccount {
			def apply()(using auth: AuthToken, ec: ExecutionContext): downloadAccount = new downloadAccount(auth(ws.url(BASE_URL + s"downloadAccount")).addQueryStringParameters())
		}
		class sendVerificationCode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartySendVerificationCodeRequest(body: Schema.IdentitytoolkitRelyingpartySendVerificationCodeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentitytoolkitRelyingpartySendVerificationCodeResponse])
		}
		object sendVerificationCode {
			def apply()(using auth: AuthToken, ec: ExecutionContext): sendVerificationCode = new sendVerificationCode(auth(ws.url(BASE_URL + s"sendVerificationCode")).addQueryStringParameters())
		}
		class emailLinkSignin(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIdentitytoolkitRelyingpartyEmailLinkSigninRequest(body: Schema.IdentitytoolkitRelyingpartyEmailLinkSigninRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EmailLinkSigninResponse])
		}
		object emailLinkSignin {
			def apply()(using auth: AuthToken, ec: ExecutionContext): emailLinkSignin = new emailLinkSignin(auth(ws.url(BASE_URL + s"emailLinkSignin")).addQueryStringParameters())
		}
	}
}
