package com.boresjo.play.api.google.mybusinessverifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaWaitForVoiceOfMerchant: Conversion[Schema.WaitForVoiceOfMerchant, Option[Schema.WaitForVoiceOfMerchant]] = (fun: Schema.WaitForVoiceOfMerchant) => Option(fun)
		given putSchemaVerify: Conversion[Schema.Verify, Option[Schema.Verify]] = (fun: Schema.Verify) => Option(fun)
		given putSchemaResolveOwnershipConflict: Conversion[Schema.ResolveOwnershipConflict, Option[Schema.ResolveOwnershipConflict]] = (fun: Schema.ResolveOwnershipConflict) => Option(fun)
		given putSchemaComplyWithGuidelines: Conversion[Schema.ComplyWithGuidelines, Option[Schema.ComplyWithGuidelines]] = (fun: Schema.ComplyWithGuidelines) => Option(fun)
		given putSchemaComplyWithGuidelinesRecommendationReasonEnum: Conversion[Schema.ComplyWithGuidelines.RecommendationReasonEnum, Option[Schema.ComplyWithGuidelines.RecommendationReasonEnum]] = (fun: Schema.ComplyWithGuidelines.RecommendationReasonEnum) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaVerification: Conversion[Schema.Verification, Option[Schema.Verification]] = (fun: Schema.Verification) => Option(fun)
		given putSchemaVerificationMethodEnum: Conversion[Schema.Verification.MethodEnum, Option[Schema.Verification.MethodEnum]] = (fun: Schema.Verification.MethodEnum) => Option(fun)
		given putSchemaVerificationStateEnum: Conversion[Schema.Verification.StateEnum, Option[Schema.Verification.StateEnum]] = (fun: Schema.Verification.StateEnum) => Option(fun)
		given putListSchemaVerification: Conversion[List[Schema.Verification], Option[List[Schema.Verification]]] = (fun: List[Schema.Verification]) => Option(fun)
		given putSchemaServiceBusinessContext: Conversion[Schema.ServiceBusinessContext, Option[Schema.ServiceBusinessContext]] = (fun: Schema.ServiceBusinessContext) => Option(fun)
		given putSchemaPostalAddress: Conversion[Schema.PostalAddress, Option[Schema.PostalAddress]] = (fun: Schema.PostalAddress) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaVerificationOption: Conversion[List[Schema.VerificationOption], Option[List[Schema.VerificationOption]]] = (fun: List[Schema.VerificationOption]) => Option(fun)
		given putSchemaVerificationOptionVerificationMethodEnum: Conversion[Schema.VerificationOption.VerificationMethodEnum, Option[Schema.VerificationOption.VerificationMethodEnum]] = (fun: Schema.VerificationOption.VerificationMethodEnum) => Option(fun)
		given putSchemaAddressVerificationData: Conversion[Schema.AddressVerificationData, Option[Schema.AddressVerificationData]] = (fun: Schema.AddressVerificationData) => Option(fun)
		given putSchemaEmailVerificationData: Conversion[Schema.EmailVerificationData, Option[Schema.EmailVerificationData]] = (fun: Schema.EmailVerificationData) => Option(fun)
		given putSchemaVerifyLocationRequestMethodEnum: Conversion[Schema.VerifyLocationRequest.MethodEnum, Option[Schema.VerifyLocationRequest.MethodEnum]] = (fun: Schema.VerifyLocationRequest.MethodEnum) => Option(fun)
		given putSchemaVerificationToken: Conversion[Schema.VerificationToken, Option[Schema.VerificationToken]] = (fun: Schema.VerificationToken) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
