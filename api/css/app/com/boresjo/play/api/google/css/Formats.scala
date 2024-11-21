package com.boresjo.play.api.google.css

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListAccountLabelsResponse: Format[Schema.ListAccountLabelsResponse] = Json.format[Schema.ListAccountLabelsResponse]
	given fmtAccountLabel: Format[Schema.AccountLabel] = Json.format[Schema.AccountLabel]
	given fmtAccountLabelLabelTypeEnum: Format[Schema.AccountLabel.LabelTypeEnum] = JsonEnumFormat[Schema.AccountLabel.LabelTypeEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListChildAccountsResponse: Format[Schema.ListChildAccountsResponse] = Json.format[Schema.ListChildAccountsResponse]
	given fmtAccount: Format[Schema.Account] = Json.format[Schema.Account]
	given fmtAccountAccountTypeEnum: Format[Schema.Account.AccountTypeEnum] = JsonEnumFormat[Schema.Account.AccountTypeEnum]
	given fmtUpdateAccountLabelsRequest: Format[Schema.UpdateAccountLabelsRequest] = Json.format[Schema.UpdateAccountLabelsRequest]
	given fmtCssProductInput: Format[Schema.CssProductInput] = Json.format[Schema.CssProductInput]
	given fmtAttributes: Format[Schema.Attributes] = Json.format[Schema.Attributes]
	given fmtCustomAttribute: Format[Schema.CustomAttribute] = Json.format[Schema.CustomAttribute]
	given fmtPrice: Format[Schema.Price] = Json.format[Schema.Price]
	given fmtProductDetail: Format[Schema.ProductDetail] = Json.format[Schema.ProductDetail]
	given fmtProductWeight: Format[Schema.ProductWeight] = Json.format[Schema.ProductWeight]
	given fmtProductDimension: Format[Schema.ProductDimension] = Json.format[Schema.ProductDimension]
	given fmtCertification: Format[Schema.Certification] = Json.format[Schema.Certification]
	given fmtHeadlineOfferInstallment: Format[Schema.HeadlineOfferInstallment] = Json.format[Schema.HeadlineOfferInstallment]
	given fmtHeadlineOfferSubscriptionCost: Format[Schema.HeadlineOfferSubscriptionCost] = Json.format[Schema.HeadlineOfferSubscriptionCost]
	given fmtHeadlineOfferSubscriptionCostPeriodEnum: Format[Schema.HeadlineOfferSubscriptionCost.PeriodEnum] = JsonEnumFormat[Schema.HeadlineOfferSubscriptionCost.PeriodEnum]
	given fmtCssProduct: Format[Schema.CssProduct] = Json.format[Schema.CssProduct]
	given fmtCssProductStatus: Format[Schema.CssProductStatus] = Json.format[Schema.CssProductStatus]
	given fmtDestinationStatus: Format[Schema.DestinationStatus] = Json.format[Schema.DestinationStatus]
	given fmtItemLevelIssue: Format[Schema.ItemLevelIssue] = Json.format[Schema.ItemLevelIssue]
	given fmtListCssProductsResponse: Format[Schema.ListCssProductsResponse] = Json.format[Schema.ListCssProductsResponse]
}
