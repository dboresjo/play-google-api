package com.boresjo.play.api.google.mybusinessverifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtVoiceOfMerchantState: Format[Schema.VoiceOfMerchantState] = Json.format[Schema.VoiceOfMerchantState]
	given fmtWaitForVoiceOfMerchant: Format[Schema.WaitForVoiceOfMerchant] = Json.format[Schema.WaitForVoiceOfMerchant]
	given fmtVerify: Format[Schema.Verify] = Json.format[Schema.Verify]
	given fmtResolveOwnershipConflict: Format[Schema.ResolveOwnershipConflict] = Json.format[Schema.ResolveOwnershipConflict]
	given fmtComplyWithGuidelines: Format[Schema.ComplyWithGuidelines] = Json.format[Schema.ComplyWithGuidelines]
	given fmtComplyWithGuidelinesRecommendationReasonEnum: Format[Schema.ComplyWithGuidelines.RecommendationReasonEnum] = JsonEnumFormat[Schema.ComplyWithGuidelines.RecommendationReasonEnum]
	given fmtCompleteVerificationRequest: Format[Schema.CompleteVerificationRequest] = Json.format[Schema.CompleteVerificationRequest]
	given fmtCompleteVerificationResponse: Format[Schema.CompleteVerificationResponse] = Json.format[Schema.CompleteVerificationResponse]
	given fmtVerification: Format[Schema.Verification] = Json.format[Schema.Verification]
	given fmtVerificationMethodEnum: Format[Schema.Verification.MethodEnum] = JsonEnumFormat[Schema.Verification.MethodEnum]
	given fmtVerificationStateEnum: Format[Schema.Verification.StateEnum] = JsonEnumFormat[Schema.Verification.StateEnum]
	given fmtListVerificationsResponse: Format[Schema.ListVerificationsResponse] = Json.format[Schema.ListVerificationsResponse]
	given fmtFetchVerificationOptionsRequest: Format[Schema.FetchVerificationOptionsRequest] = Json.format[Schema.FetchVerificationOptionsRequest]
	given fmtServiceBusinessContext: Format[Schema.ServiceBusinessContext] = Json.format[Schema.ServiceBusinessContext]
	given fmtPostalAddress: Format[Schema.PostalAddress] = Json.format[Schema.PostalAddress]
	given fmtFetchVerificationOptionsResponse: Format[Schema.FetchVerificationOptionsResponse] = Json.format[Schema.FetchVerificationOptionsResponse]
	given fmtVerificationOption: Format[Schema.VerificationOption] = Json.format[Schema.VerificationOption]
	given fmtVerificationOptionVerificationMethodEnum: Format[Schema.VerificationOption.VerificationMethodEnum] = JsonEnumFormat[Schema.VerificationOption.VerificationMethodEnum]
	given fmtAddressVerificationData: Format[Schema.AddressVerificationData] = Json.format[Schema.AddressVerificationData]
	given fmtEmailVerificationData: Format[Schema.EmailVerificationData] = Json.format[Schema.EmailVerificationData]
	given fmtVerifyLocationRequest: Format[Schema.VerifyLocationRequest] = Json.format[Schema.VerifyLocationRequest]
	given fmtVerifyLocationRequestMethodEnum: Format[Schema.VerifyLocationRequest.MethodEnum] = JsonEnumFormat[Schema.VerifyLocationRequest.MethodEnum]
	given fmtVerificationToken: Format[Schema.VerificationToken] = Json.format[Schema.VerificationToken]
	given fmtVerifyLocationResponse: Format[Schema.VerifyLocationResponse] = Json.format[Schema.VerifyLocationResponse]
}
