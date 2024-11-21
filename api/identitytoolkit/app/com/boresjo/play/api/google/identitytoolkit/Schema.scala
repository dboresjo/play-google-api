package com.boresjo.play.api.google.identitytoolkit

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class CreateAuthUriResponse(
	  /** all providers the user has once used to do federated login */
		allProviders: Option[List[String]] = None,
	  /** The URI used by the IDP to authenticate the user. */
		authUri: Option[String] = None,
	  /** True if captcha is required. */
		captchaRequired: Option[Boolean] = None,
	  /** True if the authUri is for user's existing provider. */
		forExistingProvider: Option[Boolean] = None,
	  /** The fixed string identitytoolkit#CreateAuthUriResponse". */
		kind: Option[String] = Some("""identitytoolkit#CreateAuthUriResponse"""),
	  /** The provider ID of the auth URI. */
		providerId: Option[String] = None,
	  /** Whether the user is registered if the identifier is an email. */
		registered: Option[Boolean] = None,
	  /** Session ID which should be passed in the following verifyAssertion request. */
		sessionId: Option[String] = None,
	  /** All sign-in methods this user has used. */
		signinMethods: Option[List[String]] = None
	)
	
	case class DeleteAccountResponse(
	  /** The fixed string "identitytoolkit#DeleteAccountResponse". */
		kind: Option[String] = Some("""identitytoolkit#DeleteAccountResponse""")
	)
	
	case class DownloadAccountResponse(
	  /** The fixed string "identitytoolkit#DownloadAccountResponse". */
		kind: Option[String] = Some("""identitytoolkit#DownloadAccountResponse"""),
	  /** The next page token. To be used in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The user accounts data. */
		users: Option[List[Schema.UserInfo]] = None
	)
	
	case class EmailLinkSigninResponse(
	  /** The user's email. */
		email: Option[String] = None,
	  /** Expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The STS id token to login the newly signed in user. */
		idToken: Option[String] = None,
	  /** Whether the user is new. */
		isNewUser: Option[Boolean] = None,
	  /** The fixed string "identitytoolkit#EmailLinkSigninResponse". */
		kind: Option[String] = Some("""identitytoolkit#EmailLinkSigninResponse"""),
	  /** The RP local ID of the user. */
		localId: Option[String] = None,
	  /** The refresh token for the signed in user. */
		refreshToken: Option[String] = None
	)
	
	case class EmailTemplate(
	  /** Email body. */
		body: Option[String] = None,
	  /** Email body format. */
		format: Option[String] = None,
	  /** From address of the email. */
		from: Option[String] = None,
	  /** From display name. */
		fromDisplayName: Option[String] = None,
	  /** Reply-to address. */
		replyTo: Option[String] = None,
	  /** Subject of the email. */
		subject: Option[String] = None
	)
	
	case class GetAccountInfoResponse(
	  /** The fixed string "identitytoolkit#GetAccountInfoResponse". */
		kind: Option[String] = Some("""identitytoolkit#GetAccountInfoResponse"""),
	  /** The info of the users. */
		users: Option[List[Schema.UserInfo]] = None
	)
	
	case class GetOobConfirmationCodeResponse(
	  /** The email address that the email is sent to. */
		email: Option[String] = None,
	  /** The fixed string "identitytoolkit#GetOobConfirmationCodeResponse". */
		kind: Option[String] = Some("""identitytoolkit#GetOobConfirmationCodeResponse"""),
	  /** The code to be send to the user. */
		oobCode: Option[String] = None
	)
	
	case class GetRecaptchaParamResponse(
	  /** The fixed string "identitytoolkit#GetRecaptchaParamResponse". */
		kind: Option[String] = Some("""identitytoolkit#GetRecaptchaParamResponse"""),
	  /** Site key registered at recaptcha. */
		recaptchaSiteKey: Option[String] = None,
	  /** The stoken field for the recaptcha widget, used to request captcha challenge. */
		recaptchaStoken: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyCreateAuthUriRequest(
	  /** The app ID of the mobile app, base64(CERT_SHA1):PACKAGE_NAME for Android, BUNDLE_ID for iOS. */
		appId: Option[String] = None,
	  /** Explicitly specify the auth flow type. Currently only support "CODE_FLOW" type. The field is only used for Google provider. */
		authFlowType: Option[String] = None,
	  /** The relying party OAuth client ID. */
		clientId: Option[String] = None,
	  /** The opaque value used by the client to maintain context info between the authentication request and the IDP callback. */
		context: Option[String] = None,
	  /** The URI to which the IDP redirects the user after the federated login flow. */
		continueUri: Option[String] = None,
	  /** The query parameter that client can customize by themselves in auth url. The following parameters are reserved for server so that they cannot be customized by clients: client_id, response_type, scope, redirect_uri, state, oauth_token. */
		customParameter: Option[Map[String, String]] = None,
	  /** The hosted domain to restrict sign-in to accounts at that domain for Google Apps hosted accounts. */
		hostedDomain: Option[String] = None,
	  /** The email or federated ID of the user. */
		identifier: Option[String] = None,
	  /** The developer's consumer key for OpenId OAuth Extension */
		oauthConsumerKey: Option[String] = None,
	  /** Additional oauth scopes, beyond the basid user profile, that the user would be prompted to grant */
		oauthScope: Option[String] = None,
	  /** Optional realm for OpenID protocol. The sub string "scheme://domain:port" of the param "continueUri" is used if this is not set. */
		openidRealm: Option[String] = None,
	  /** The native app package for OTA installation. */
		otaApp: Option[String] = None,
	  /** The IdP ID. For white listed IdPs it's a short domain name e.g. google.com, aol.com, live.net and yahoo.com. For other OpenID IdPs it's the OP identifier. */
		providerId: Option[String] = None,
	  /** The session_id passed by client. */
		sessionId: Option[String] = None,
	  /** For multi-tenant use cases, in order to construct sign-in URL with the correct IDP parameters, Firebear needs to know which Tenant to retrieve IDP configs from. */
		tenantId: Option[String] = None,
	  /** Tenant project number to be used for idp discovery. */
		tenantProjectNumber: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyDeleteAccountRequest(
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The GITKit token or STS id token of the authenticated user. */
		idToken: Option[String] = None,
	  /** The local ID of the user. */
		localId: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyDownloadAccountRequest(
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The max number of results to return in the response. */
		maxResults: Option[Int] = None,
	  /** The token for the next page. This should be taken from the previous response. */
		nextPageToken: Option[String] = None,
	  /** Specify which project (field value is actually project id) to operate. Only used when provided credential. */
		targetProjectId: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyEmailLinkSigninRequest(
	  /** The email address of the user. */
		email: Option[String] = None,
	  /** Token for linking flow. */
		idToken: Option[String] = None,
	  /** The confirmation code. */
		oobCode: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyGetAccountInfoRequest(
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The list of emails of the users to inquiry. */
		email: Option[List[String]] = None,
	  /** The GITKit token of the authenticated user. */
		idToken: Option[String] = None,
	  /** The list of local ID's of the users to inquiry. */
		localId: Option[List[String]] = None,
	  /** Privileged caller can query users by specified phone number. */
		phoneNumber: Option[List[String]] = None
	)
	
	case class IdentitytoolkitRelyingpartyGetProjectConfigResponse(
	  /** Whether to allow password user sign in or sign up. */
		allowPasswordUser: Option[Boolean] = None,
	  /** Browser API key, needed when making http request to Apiary. */
		apiKey: Option[String] = None,
	  /** Authorized domains. */
		authorizedDomains: Option[List[String]] = None,
	  /** Change email template. */
		changeEmailTemplate: Option[Schema.EmailTemplate] = None,
		dynamicLinksDomain: Option[String] = None,
	  /** Whether anonymous user is enabled. */
		enableAnonymousUser: Option[Boolean] = None,
	  /** OAuth2 provider configuration. */
		idpConfig: Option[List[Schema.IdpConfig]] = None,
	  /** Legacy reset password email template. */
		legacyResetPasswordTemplate: Option[Schema.EmailTemplate] = None,
	  /** Project ID of the relying party. */
		projectId: Option[String] = None,
	  /** Reset password email template. */
		resetPasswordTemplate: Option[Schema.EmailTemplate] = None,
	  /** Whether to use email sending provided by Firebear. */
		useEmailSending: Option[Boolean] = None,
	  /** Verify email template. */
		verifyEmailTemplate: Option[Schema.EmailTemplate] = None
	)
	
	case class IdentitytoolkitRelyingpartyGetPublicKeysResponse(
	
	)
	
	case class IdentitytoolkitRelyingpartyResetPasswordRequest(
	  /** The email address of the user. */
		email: Option[String] = None,
	  /** The new password inputted by the user. */
		newPassword: Option[String] = None,
	  /** The old password inputted by the user. */
		oldPassword: Option[String] = None,
	  /** The confirmation code. */
		oobCode: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySendVerificationCodeRequest(
	  /** Receipt of successful app token validation with APNS. */
		iosReceipt: Option[String] = None,
	  /** Secret delivered to iOS app via APNS. */
		iosSecret: Option[String] = None,
	  /** The phone number to send the verification code to in E.164 format. */
		phoneNumber: Option[String] = None,
	  /** Recaptcha solution. */
		recaptchaToken: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySendVerificationCodeResponse(
	  /** Encrypted session information */
		sessionInfo: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySetAccountInfoRequest(
	  /** The captcha challenge. */
		captchaChallenge: Option[String] = None,
	  /** Response to the captcha. */
		captchaResponse: Option[String] = None,
	  /** The timestamp when the account is created. */
		createdAt: Option[String] = None,
	  /** The custom attributes to be set in the user's id token. */
		customAttributes: Option[String] = None,
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The attributes users request to delete. */
		deleteAttribute: Option[List[String]] = None,
	  /** The IDPs the user request to delete. */
		deleteProvider: Option[List[String]] = None,
	  /** Whether to disable the user. */
		disableUser: Option[Boolean] = None,
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** Mark the email as verified or not. */
		emailVerified: Option[Boolean] = None,
	  /** The GITKit token of the authenticated user. */
		idToken: Option[String] = None,
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** Last login timestamp. */
		lastLoginAt: Option[String] = None,
	  /** The local ID of the user. */
		localId: Option[String] = None,
	  /** The out-of-band code of the change email request. */
		oobCode: Option[String] = None,
	  /** The new password of the user. */
		password: Option[String] = None,
	  /** Privileged caller can update user with specified phone number. */
		phoneNumber: Option[String] = None,
	  /** The photo url of the user. */
		photoUrl: Option[String] = None,
	  /** The associated IDPs of the user. */
		provider: Option[List[String]] = None,
	  /** Whether return sts id token and refresh token instead of gitkit token. */
		returnSecureToken: Option[Boolean] = None,
	  /** Mark the user to upgrade to federated login. */
		upgradeToFederatedLogin: Option[Boolean] = None,
	  /** Timestamp in seconds for valid login token. */
		validSince: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySetProjectConfigRequest(
	  /** Whether to allow password user sign in or sign up. */
		allowPasswordUser: Option[Boolean] = None,
	  /** Browser API key, needed when making http request to Apiary. */
		apiKey: Option[String] = None,
	  /** Authorized domains for widget redirect. */
		authorizedDomains: Option[List[String]] = None,
	  /** Change email template. */
		changeEmailTemplate: Option[Schema.EmailTemplate] = None,
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** Whether to enable anonymous user. */
		enableAnonymousUser: Option[Boolean] = None,
	  /** Oauth2 provider configuration. */
		idpConfig: Option[List[Schema.IdpConfig]] = None,
	  /** Legacy reset password email template. */
		legacyResetPasswordTemplate: Option[Schema.EmailTemplate] = None,
	  /** Reset password email template. */
		resetPasswordTemplate: Option[Schema.EmailTemplate] = None,
	  /** Whether to use email sending provided by Firebear. */
		useEmailSending: Option[Boolean] = None,
	  /** Verify email template. */
		verifyEmailTemplate: Option[Schema.EmailTemplate] = None
	)
	
	case class IdentitytoolkitRelyingpartySetProjectConfigResponse(
	  /** Project ID of the relying party. */
		projectId: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySignOutUserRequest(
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** The local ID of the user. */
		localId: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySignOutUserResponse(
	  /** The local ID of the user. */
		localId: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartySignupNewUserRequest(
	  /** The captcha challenge. */
		captchaChallenge: Option[String] = None,
	  /** Response to the captcha. */
		captchaResponse: Option[String] = None,
	  /** Whether to disable the user. Only can be used by service account. */
		disabled: Option[Boolean] = None,
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** Mark the email as verified or not. Only can be used by service account. */
		emailVerified: Option[Boolean] = None,
	  /** The GITKit token of the authenticated user. */
		idToken: Option[String] = None,
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** Privileged caller can create user with specified user id. */
		localId: Option[String] = None,
	  /** The new password of the user. */
		password: Option[String] = None,
	  /** Privileged caller can create user with specified phone number. */
		phoneNumber: Option[String] = None,
	  /** The photo url of the user. */
		photoUrl: Option[String] = None,
	  /** For multi-tenant use cases, in order to construct sign-in URL with the correct IDP parameters, Firebear needs to know which Tenant to retrieve IDP configs from. */
		tenantId: Option[String] = None,
	  /** Tenant project number to be used for idp discovery. */
		tenantProjectNumber: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyUploadAccountRequest(
	  /** Whether allow overwrite existing account when user local_id exists. */
		allowOverwrite: Option[Boolean] = None,
		blockSize: Option[Int] = None,
	  /** The following 4 fields are for standard scrypt algorithm. */
		cpuMemCost: Option[Int] = None,
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
		dkLen: Option[Int] = None,
	  /** The password hash algorithm. */
		hashAlgorithm: Option[String] = None,
	  /** Memory cost for hash calculation. Used by scrypt similar algorithms. */
		memoryCost: Option[Int] = None,
		parallelization: Option[Int] = None,
	  /** Rounds for hash calculation. Used by scrypt and similar algorithms. */
		rounds: Option[Int] = None,
	  /** The salt separator. */
		saltSeparator: Option[String] = None,
	  /** If true, backend will do sanity check(including duplicate email and federated id) when uploading account. */
		sanityCheck: Option[Boolean] = None,
	  /** The key for to hash the password. */
		signerKey: Option[String] = None,
	  /** Specify which project (field value is actually project id) to operate. Only used when provided credential. */
		targetProjectId: Option[String] = None,
	  /** The account info to be stored. */
		users: Option[List[Schema.UserInfo]] = None
	)
	
	case class IdentitytoolkitRelyingpartyVerifyAssertionRequest(
	  /** When it's true, automatically creates a new account if the user doesn't exist. When it's false, allows existing user to sign in normally and throws exception if the user doesn't exist. */
		autoCreate: Option[Boolean] = None,
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The GITKit token of the authenticated user. */
		idToken: Option[String] = None,
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** The GITKit token for the non-trusted IDP pending to be confirmed by the user. */
		pendingIdToken: Option[String] = None,
	  /** The post body if the request is a HTTP POST. */
		postBody: Option[String] = None,
	  /** The URI to which the IDP redirects the user back. It may contain federated login result params added by the IDP. */
		requestUri: Option[String] = None,
	  /** Whether return 200 and IDP credential rather than throw exception when federated id is already linked. */
		returnIdpCredential: Option[Boolean] = None,
	  /** Whether to return refresh tokens. */
		returnRefreshToken: Option[Boolean] = None,
	  /** Whether return sts id token and refresh token instead of gitkit token. */
		returnSecureToken: Option[Boolean] = None,
	  /** Session ID, which should match the one in previous createAuthUri request. */
		sessionId: Option[String] = None,
	  /** For multi-tenant use cases, in order to construct sign-in URL with the correct IDP parameters, Firebear needs to know which Tenant to retrieve IDP configs from. */
		tenantId: Option[String] = None,
	  /** Tenant project number to be used for idp discovery. */
		tenantProjectNumber: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyVerifyCustomTokenRequest(
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** Whether return sts id token and refresh token instead of gitkit token. */
		returnSecureToken: Option[Boolean] = None,
	  /** The custom token to verify */
		token: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyVerifyPasswordRequest(
	  /** The captcha challenge. */
		captchaChallenge: Option[String] = None,
	  /** Response to the captcha. */
		captchaResponse: Option[String] = None,
	  /** GCP project number of the requesting delegated app. Currently only intended for Firebase V1 migration. */
		delegatedProjectNumber: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** The GITKit token of the authenticated user. */
		idToken: Option[String] = None,
	  /** Instance id token of the app. */
		instanceId: Option[String] = None,
	  /** The password inputed by the user. */
		password: Option[String] = None,
	  /** The GITKit token for the non-trusted IDP, which is to be confirmed by the user. */
		pendingIdToken: Option[String] = None,
	  /** Whether return sts id token and refresh token instead of gitkit token. */
		returnSecureToken: Option[Boolean] = None,
	  /** For multi-tenant use cases, in order to construct sign-in URL with the correct IDP parameters, Firebear needs to know which Tenant to retrieve IDP configs from. */
		tenantId: Option[String] = None,
	  /** Tenant project number to be used for idp discovery. */
		tenantProjectNumber: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyVerifyPhoneNumberRequest(
		code: Option[String] = None,
		idToken: Option[String] = None,
		operation: Option[String] = None,
		phoneNumber: Option[String] = None,
	  /** The session info previously returned by IdentityToolkit-SendVerificationCode. */
		sessionInfo: Option[String] = None,
		temporaryProof: Option[String] = None,
		verificationProof: Option[String] = None
	)
	
	case class IdentitytoolkitRelyingpartyVerifyPhoneNumberResponse(
		expiresIn: Option[String] = None,
		idToken: Option[String] = None,
		isNewUser: Option[Boolean] = None,
		localId: Option[String] = None,
		phoneNumber: Option[String] = None,
		refreshToken: Option[String] = None,
		temporaryProof: Option[String] = None,
		temporaryProofExpiresIn: Option[String] = None,
		verificationProof: Option[String] = None,
		verificationProofExpiresIn: Option[String] = None
	)
	
	case class IdpConfig(
	  /** OAuth2 client ID. */
		clientId: Option[String] = None,
	  /** Whether this IDP is enabled. */
		enabled: Option[Boolean] = None,
	  /** Percent of users who will be prompted/redirected federated login for this IDP. */
		experimentPercent: Option[Int] = None,
	  /** OAuth2 provider. */
		provider: Option[String] = None,
	  /** OAuth2 client secret. */
		secret: Option[String] = None,
	  /** Whitelisted client IDs for audience check. */
		whitelistedAudiences: Option[List[String]] = None
	)
	
	case class Relyingparty(
	  /** whether or not to install the android app on the device where the link is opened */
		androidInstallApp: Option[Boolean] = None,
	  /** minimum version of the app. if the version on the device is lower than this version then the user is taken to the play store to upgrade the app */
		androidMinimumVersion: Option[String] = None,
	  /** android package name of the android app to handle the action code */
		androidPackageName: Option[String] = None,
	  /** whether or not the app can handle the oob code without first going to web */
		canHandleCodeInApp: Option[Boolean] = None,
	  /** The recaptcha response from the user. */
		captchaResp: Option[String] = None,
	  /** The recaptcha challenge presented to the user. */
		challenge: Option[String] = None,
	  /** The url to continue to the Gitkit app */
		continueUrl: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** iOS app store id to download the app if it's not already installed */
		iOSAppStoreId: Option[String] = None,
	  /** the iOS bundle id of iOS app to handle the action code */
		iOSBundleId: Option[String] = None,
	  /** The user's Gitkit login token for email change. */
		idToken: Option[String] = None,
	  /** The fixed string "identitytoolkit#relyingparty". */
		kind: Option[String] = Some("""identitytoolkit#relyingparty"""),
	  /** The new email if the code is for email change. */
		newEmail: Option[String] = None,
	  /** The request type. */
		requestType: Option[String] = None,
	  /** The IP address of the user. */
		userIp: Option[String] = None
	)
	
	case class ResetPasswordResponse(
	  /** The user's email. If the out-of-band code is for email recovery, the user's original email. */
		email: Option[String] = None,
	  /** The fixed string "identitytoolkit#ResetPasswordResponse". */
		kind: Option[String] = Some("""identitytoolkit#ResetPasswordResponse"""),
	  /** If the out-of-band code is for email recovery, the user's new email. */
		newEmail: Option[String] = None,
	  /** The request type. */
		requestType: Option[String] = None
	)
	
	object SetAccountInfoResponse {
		case class ProviderUserInfoItem(
		  /** The user's display name at the IDP. */
			displayName: Option[String] = None,
		  /** User's identifier at IDP. */
			federatedId: Option[String] = None,
		  /** The user's photo url at the IDP. */
			photoUrl: Option[String] = None,
		  /** The IdP ID. For whitelisted IdPs it's a short domain name, e.g., google.com, aol.com, live.net and yahoo.com. For other OpenID IdPs it's the OP identifier. */
			providerId: Option[String] = None
		)
	}
	case class SetAccountInfoResponse(
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** If email has been verified. */
		emailVerified: Option[Boolean] = None,
	  /** If idToken is STS id token, then this field will be expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The Gitkit id token to login the newly sign up user. */
		idToken: Option[String] = None,
	  /** The fixed string "identitytoolkit#SetAccountInfoResponse". */
		kind: Option[String] = Some("""identitytoolkit#SetAccountInfoResponse"""),
	  /** The local ID of the user. */
		localId: Option[String] = None,
	  /** The new email the user attempts to change to. */
		newEmail: Option[String] = None,
	  /** The user's hashed password. */
		passwordHash: Option[String] = None,
	  /** The photo url of the user. */
		photoUrl: Option[String] = None,
	  /** The user's profiles at the associated IdPs. */
		providerUserInfo: Option[List[Schema.SetAccountInfoResponse.ProviderUserInfoItem]] = None,
	  /** If idToken is STS id token, then this field will be refresh token. */
		refreshToken: Option[String] = None
	)
	
	case class SignupNewUserResponse(
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** If idToken is STS id token, then this field will be expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The Gitkit id token to login the newly sign up user. */
		idToken: Option[String] = None,
	  /** The fixed string "identitytoolkit#SignupNewUserResponse". */
		kind: Option[String] = Some("""identitytoolkit#SignupNewUserResponse"""),
	  /** The RP local ID of the user. */
		localId: Option[String] = None,
	  /** If idToken is STS id token, then this field will be refresh token. */
		refreshToken: Option[String] = None
	)
	
	object UploadAccountResponse {
		case class ErrorItem(
		  /** The index of the malformed account, starting from 0. */
			index: Option[Int] = None,
		  /** Detailed error message for the account info. */
			message: Option[String] = None
		)
	}
	case class UploadAccountResponse(
	  /** The error encountered while processing the account info. */
		error: Option[List[Schema.UploadAccountResponse.ErrorItem]] = None,
	  /** The fixed string "identitytoolkit#UploadAccountResponse". */
		kind: Option[String] = Some("""identitytoolkit#UploadAccountResponse""")
	)
	
	object UserInfo {
		case class ProviderUserInfoItem(
		  /** The user's display name at the IDP. */
			displayName: Option[String] = None,
		  /** User's email at IDP. */
			email: Option[String] = None,
		  /** User's identifier at IDP. */
			federatedId: Option[String] = None,
		  /** User's phone number. */
			phoneNumber: Option[String] = None,
		  /** The user's photo url at the IDP. */
			photoUrl: Option[String] = None,
		  /** The IdP ID. For white listed IdPs it's a short domain name, e.g., google.com, aol.com, live.net and yahoo.com. For other OpenID IdPs it's the OP identifier. */
			providerId: Option[String] = None,
		  /** User's raw identifier directly returned from IDP. */
			rawId: Option[String] = None,
		  /** User's screen name at Twitter or login name at Github. */
			screenName: Option[String] = None
		)
	}
	case class UserInfo(
	  /** User creation timestamp. */
		createdAt: Option[String] = None,
	  /** The custom attributes to be set in the user's id token. */
		customAttributes: Option[String] = None,
	  /** Whether the user is authenticated by the developer. */
		customAuth: Option[Boolean] = None,
	  /** Whether the user is disabled. */
		disabled: Option[Boolean] = None,
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email of the user. */
		email: Option[String] = None,
	  /** Whether the email has been verified. */
		emailVerified: Option[Boolean] = None,
	  /** last login timestamp. */
		lastLoginAt: Option[String] = None,
	  /** The local ID of the user. */
		localId: Option[String] = None,
	  /** The user's hashed password. */
		passwordHash: Option[String] = None,
	  /** The timestamp when the password was last updated. */
		passwordUpdatedAt: Option[BigDecimal] = None,
	  /** User's phone number. */
		phoneNumber: Option[String] = None,
	  /** The URL of the user profile photo. */
		photoUrl: Option[String] = None,
	  /** The IDP of the user. */
		providerUserInfo: Option[List[Schema.UserInfo.ProviderUserInfoItem]] = None,
	  /** The user's plain text password. */
		rawPassword: Option[String] = None,
	  /** The user's password salt. */
		salt: Option[String] = None,
	  /** User's screen name at Twitter or login name at Github. */
		screenName: Option[String] = None,
	  /** Timestamp in seconds for valid login token. */
		validSince: Option[String] = None,
	  /** Version of the user's password. */
		version: Option[Int] = None
	)
	
	case class VerifyAssertionResponse(
	  /** The action code. */
		action: Option[String] = None,
	  /** URL for OTA app installation. */
		appInstallationUrl: Option[String] = None,
	  /** The custom scheme used by mobile app. */
		appScheme: Option[String] = None,
	  /** The opaque value used by the client to maintain context info between the authentication request and the IDP callback. */
		context: Option[String] = None,
	  /** The birth date of the IdP account. */
		dateOfBirth: Option[String] = None,
	  /** The display name of the user. */
		displayName: Option[String] = None,
	  /** The email returned by the IdP. NOTE: The federated login user may not own the email. */
		email: Option[String] = None,
	  /** It's true if the email is recycled. */
		emailRecycled: Option[Boolean] = None,
	  /** The value is true if the IDP is also the email provider. It means the user owns the email. */
		emailVerified: Option[Boolean] = None,
	  /** Client error code. */
		errorMessage: Option[String] = None,
	  /** If idToken is STS id token, then this field will be expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The unique ID identifies the IdP account. */
		federatedId: Option[String] = None,
	  /** The first name of the user. */
		firstName: Option[String] = None,
	  /** The full name of the user. */
		fullName: Option[String] = None,
	  /** The ID token. */
		idToken: Option[String] = None,
	  /** It's the identifier param in the createAuthUri request if the identifier is an email. It can be used to check whether the user input email is different from the asserted email. */
		inputEmail: Option[String] = None,
	  /** True if it's a new user sign-in, false if it's a returning user. */
		isNewUser: Option[Boolean] = None,
	  /** The fixed string "identitytoolkit#VerifyAssertionResponse". */
		kind: Option[String] = Some("""identitytoolkit#VerifyAssertionResponse"""),
	  /** The language preference of the user. */
		language: Option[String] = None,
	  /** The last name of the user. */
		lastName: Option[String] = None,
	  /** The RP local ID if it's already been mapped to the IdP account identified by the federated ID. */
		localId: Option[String] = None,
	  /** Whether the assertion is from a non-trusted IDP and need account linking confirmation. */
		needConfirmation: Option[Boolean] = None,
	  /** Whether need client to supply email to complete the federated login flow. */
		needEmail: Option[Boolean] = None,
	  /** The nick name of the user. */
		nickName: Option[String] = None,
	  /** The OAuth2 access token. */
		oauthAccessToken: Option[String] = None,
	  /** The OAuth2 authorization code. */
		oauthAuthorizationCode: Option[String] = None,
	  /** The lifetime in seconds of the OAuth2 access token. */
		oauthExpireIn: Option[Int] = None,
	  /** The OIDC id token. */
		oauthIdToken: Option[String] = None,
	  /** The user approved request token for the OpenID OAuth extension. */
		oauthRequestToken: Option[String] = None,
	  /** The scope for the OpenID OAuth extension. */
		oauthScope: Option[String] = None,
	  /** The OAuth1 access token secret. */
		oauthTokenSecret: Option[String] = None,
	  /** The original email stored in the mapping storage. It's returned when the federated ID is associated to a different email. */
		originalEmail: Option[String] = None,
	  /** The URI of the public accessible profiel picture. */
		photoUrl: Option[String] = None,
	  /** The IdP ID. For white listed IdPs it's a short domain name e.g. google.com, aol.com, live.net and yahoo.com. If the "providerId" param is set to OpenID OP identifer other than the whilte listed IdPs the OP identifier is returned. If the "identifier" param is federated ID in the createAuthUri request. The domain part of the federated ID is returned. */
		providerId: Option[String] = None,
	  /** Raw IDP-returned user info. */
		rawUserInfo: Option[String] = None,
	  /** If idToken is STS id token, then this field will be refresh token. */
		refreshToken: Option[String] = None,
	  /** The screen_name of a Twitter user or the login name at Github. */
		screenName: Option[String] = None,
	  /** The timezone of the user. */
		timeZone: Option[String] = None,
	  /** When action is 'map', contains the idps which can be used for confirmation. */
		verifiedProvider: Option[List[String]] = None
	)
	
	case class VerifyCustomTokenResponse(
	  /** If idToken is STS id token, then this field will be expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The GITKit token for authenticated user. */
		idToken: Option[String] = None,
	  /** True if it's a new user sign-in, false if it's a returning user. */
		isNewUser: Option[Boolean] = None,
	  /** The fixed string "identitytoolkit#VerifyCustomTokenResponse". */
		kind: Option[String] = Some("""identitytoolkit#VerifyCustomTokenResponse"""),
	  /** If idToken is STS id token, then this field will be refresh token. */
		refreshToken: Option[String] = None
	)
	
	case class VerifyPasswordResponse(
	  /** The name of the user. */
		displayName: Option[String] = None,
	  /** The email returned by the IdP. NOTE: The federated login user may not own the email. */
		email: Option[String] = None,
	  /** If idToken is STS id token, then this field will be expiration time of STS id token in seconds. */
		expiresIn: Option[String] = None,
	  /** The GITKit token for authenticated user. */
		idToken: Option[String] = None,
	  /** The fixed string "identitytoolkit#VerifyPasswordResponse". */
		kind: Option[String] = Some("""identitytoolkit#VerifyPasswordResponse"""),
	  /** The RP local ID if it's already been mapped to the IdP account identified by the federated ID. */
		localId: Option[String] = None,
	  /** The OAuth2 access token. */
		oauthAccessToken: Option[String] = None,
	  /** The OAuth2 authorization code. */
		oauthAuthorizationCode: Option[String] = None,
	  /** The lifetime in seconds of the OAuth2 access token. */
		oauthExpireIn: Option[Int] = None,
	  /** The URI of the user's photo at IdP */
		photoUrl: Option[String] = None,
	  /** If idToken is STS id token, then this field will be refresh token. */
		refreshToken: Option[String] = None,
	  /** Whether the email is registered. */
		registered: Option[Boolean] = None
	)
}
