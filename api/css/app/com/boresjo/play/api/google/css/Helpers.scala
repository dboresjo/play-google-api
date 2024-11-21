package com.boresjo.play.api.google.css

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaAccountLabel: Conversion[List[Schema.AccountLabel], Option[List[Schema.AccountLabel]]] = (fun: List[Schema.AccountLabel]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAccountLabelLabelTypeEnum: Conversion[Schema.AccountLabel.LabelTypeEnum, Option[Schema.AccountLabel.LabelTypeEnum]] = (fun: Schema.AccountLabel.LabelTypeEnum) => Option(fun)
		given putListSchemaAccount: Conversion[List[Schema.Account], Option[List[Schema.Account]]] = (fun: List[Schema.Account]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaAccountAccountTypeEnum: Conversion[Schema.Account.AccountTypeEnum, Option[Schema.Account.AccountTypeEnum]] = (fun: Schema.Account.AccountTypeEnum) => Option(fun)
		given putSchemaAttributes: Conversion[Schema.Attributes, Option[Schema.Attributes]] = (fun: Schema.Attributes) => Option(fun)
		given putListSchemaCustomAttribute: Conversion[List[Schema.CustomAttribute], Option[List[Schema.CustomAttribute]]] = (fun: List[Schema.CustomAttribute]) => Option(fun)
		given putSchemaPrice: Conversion[Schema.Price, Option[Schema.Price]] = (fun: Schema.Price) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaProductDetail: Conversion[List[Schema.ProductDetail], Option[List[Schema.ProductDetail]]] = (fun: List[Schema.ProductDetail]) => Option(fun)
		given putSchemaProductWeight: Conversion[Schema.ProductWeight, Option[Schema.ProductWeight]] = (fun: Schema.ProductWeight) => Option(fun)
		given putSchemaProductDimension: Conversion[Schema.ProductDimension, Option[Schema.ProductDimension]] = (fun: Schema.ProductDimension) => Option(fun)
		given putListSchemaCertification: Conversion[List[Schema.Certification], Option[List[Schema.Certification]]] = (fun: List[Schema.Certification]) => Option(fun)
		given putSchemaHeadlineOfferInstallment: Conversion[Schema.HeadlineOfferInstallment, Option[Schema.HeadlineOfferInstallment]] = (fun: Schema.HeadlineOfferInstallment) => Option(fun)
		given putSchemaHeadlineOfferSubscriptionCost: Conversion[Schema.HeadlineOfferSubscriptionCost, Option[Schema.HeadlineOfferSubscriptionCost]] = (fun: Schema.HeadlineOfferSubscriptionCost) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaHeadlineOfferSubscriptionCostPeriodEnum: Conversion[Schema.HeadlineOfferSubscriptionCost.PeriodEnum, Option[Schema.HeadlineOfferSubscriptionCost.PeriodEnum]] = (fun: Schema.HeadlineOfferSubscriptionCost.PeriodEnum) => Option(fun)
		given putSchemaCssProductStatus: Conversion[Schema.CssProductStatus, Option[Schema.CssProductStatus]] = (fun: Schema.CssProductStatus) => Option(fun)
		given putListSchemaDestinationStatus: Conversion[List[Schema.DestinationStatus], Option[List[Schema.DestinationStatus]]] = (fun: List[Schema.DestinationStatus]) => Option(fun)
		given putListSchemaItemLevelIssue: Conversion[List[Schema.ItemLevelIssue], Option[List[Schema.ItemLevelIssue]]] = (fun: List[Schema.ItemLevelIssue]) => Option(fun)
		given putListSchemaCssProduct: Conversion[List[Schema.CssProduct], Option[List[Schema.CssProduct]]] = (fun: List[Schema.CssProduct]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
