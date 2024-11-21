package com.boresjo.play.api.google.datalineage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleCloudDatacatalogLineageV1Link: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1Link], Option[List[Schema.GoogleCloudDatacatalogLineageV1Link]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1Link]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1ProcessLinkInfo: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo], Option[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1OriginSourceTypeEnum: Conversion[Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum, Option[Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum]] = (fun: Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1OperationMetadataStateEnum: Conversion[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum, Option[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum]] = (fun: Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1OperationMetadataOperationTypeEnum: Conversion[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum, Option[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum]] = (fun: Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1EntityReference: Conversion[Schema.GoogleCloudDatacatalogLineageV1EntityReference, Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference]] = (fun: Schema.GoogleCloudDatacatalogLineageV1EntityReference) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1Origin: Conversion[Schema.GoogleCloudDatacatalogLineageV1Origin, Option[Schema.GoogleCloudDatacatalogLineageV1Origin]] = (fun: Schema.GoogleCloudDatacatalogLineageV1Origin) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1EventLink: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1EventLink], Option[List[Schema.GoogleCloudDatacatalogLineageV1EventLink]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1EventLink]) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1Run: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1Run], Option[List[Schema.GoogleCloudDatacatalogLineageV1Run]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1Run]) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1LineageEvent: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1LineageEvent], Option[List[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1ProcessLinks: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks], Option[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks]) => Option(fun)
		given putSchemaGoogleCloudDatacatalogLineageV1RunStateEnum: Conversion[Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum, Option[Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum]] = (fun: Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum) => Option(fun)
		given putListSchemaGoogleCloudDatacatalogLineageV1Process: Conversion[List[Schema.GoogleCloudDatacatalogLineageV1Process], Option[List[Schema.GoogleCloudDatacatalogLineageV1Process]]] = (fun: List[Schema.GoogleCloudDatacatalogLineageV1Process]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
