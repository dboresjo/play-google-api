package com.boresjo.play.api.google.manufacturers

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaProduct: Conversion[List[Schema.Product], Option[List[Schema.Product]]] = (fun: List[Schema.Product]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAttributes: Conversion[Schema.Attributes, Option[Schema.Attributes]] = (fun: Schema.Attributes) => Option(fun)
		given putListSchemaIssue: Conversion[List[Schema.Issue], Option[List[Schema.Issue]]] = (fun: List[Schema.Issue]) => Option(fun)
		given putListSchemaDestinationStatus: Conversion[List[Schema.DestinationStatus], Option[List[Schema.DestinationStatus]]] = (fun: List[Schema.DestinationStatus]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaFeatureDescription: Conversion[List[Schema.FeatureDescription], Option[List[Schema.FeatureDescription]]] = (fun: List[Schema.FeatureDescription]) => Option(fun)
		given putSchemaImage: Conversion[Schema.Image, Option[Schema.Image]] = (fun: Schema.Image) => Option(fun)
		given putListSchemaImage: Conversion[List[Schema.Image], Option[List[Schema.Image]]] = (fun: List[Schema.Image]) => Option(fun)
		given putSchemaPrice: Conversion[Schema.Price, Option[Schema.Price]] = (fun: Schema.Price) => Option(fun)
		given putListSchemaProductDetail: Conversion[List[Schema.ProductDetail], Option[List[Schema.ProductDetail]]] = (fun: List[Schema.ProductDetail]) => Option(fun)
		given putSchemaCapacity: Conversion[Schema.Capacity, Option[Schema.Capacity]] = (fun: Schema.Capacity) => Option(fun)
		given putSchemaCount: Conversion[Schema.Count, Option[Schema.Count]] = (fun: Schema.Count) => Option(fun)
		given putSchemaNutrition: Conversion[Schema.Nutrition, Option[Schema.Nutrition]] = (fun: Schema.Nutrition) => Option(fun)
		given putSchemaGrocery: Conversion[Schema.Grocery, Option[Schema.Grocery]] = (fun: Schema.Grocery) => Option(fun)
		given putListSchemaGoogleShoppingManufacturersV1ProductCertification: Conversion[List[Schema.GoogleShoppingManufacturersV1ProductCertification], Option[List[Schema.GoogleShoppingManufacturersV1ProductCertification]]] = (fun: List[Schema.GoogleShoppingManufacturersV1ProductCertification]) => Option(fun)
		given putSchemaImageTypeEnum: Conversion[Schema.Image.TypeEnum, Option[Schema.Image.TypeEnum]] = (fun: Schema.Image.TypeEnum) => Option(fun)
		given putSchemaImageStatusEnum: Conversion[Schema.Image.StatusEnum, Option[Schema.Image.StatusEnum]] = (fun: Schema.Image.StatusEnum) => Option(fun)
		given putSchemaFloatUnit: Conversion[Schema.FloatUnit, Option[Schema.FloatUnit]] = (fun: Schema.FloatUnit) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaVoluntaryNutritionFact: Conversion[List[Schema.VoluntaryNutritionFact], Option[List[Schema.VoluntaryNutritionFact]]] = (fun: List[Schema.VoluntaryNutritionFact]) => Option(fun)
		given putSchemaIssueSeverityEnum: Conversion[Schema.Issue.SeverityEnum, Option[Schema.Issue.SeverityEnum]] = (fun: Schema.Issue.SeverityEnum) => Option(fun)
		given putSchemaIssueResolutionEnum: Conversion[Schema.Issue.ResolutionEnum, Option[Schema.Issue.ResolutionEnum]] = (fun: Schema.Issue.ResolutionEnum) => Option(fun)
		given putSchemaDestinationStatusStatusEnum: Conversion[Schema.DestinationStatus.StatusEnum, Option[Schema.DestinationStatus.StatusEnum]] = (fun: Schema.DestinationStatus.StatusEnum) => Option(fun)
		given putListSchemaCertification: Conversion[List[Schema.Certification], Option[List[Schema.Certification]]] = (fun: List[Schema.Certification]) => Option(fun)
		given putListSchemaProductCertification: Conversion[List[Schema.ProductCertification], Option[List[Schema.ProductCertification]]] = (fun: List[Schema.ProductCertification]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
