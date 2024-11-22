package com.boresjo.play.api.google.webrisk

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putSchemaGoogleCloudWebriskV1RiceDeltaEncoding: Conversion[Schema.GoogleCloudWebriskV1RiceDeltaEncoding, Option[Schema.GoogleCloudWebriskV1RiceDeltaEncoding]] = (fun: Schema.GoogleCloudWebriskV1RiceDeltaEncoding) => Option(fun)
		given putListSchemaGoogleCloudWebriskV1RawHashes: Conversion[List[Schema.GoogleCloudWebriskV1RawHashes], Option[List[Schema.GoogleCloudWebriskV1RawHashes]]] = (fun: List[Schema.GoogleCloudWebriskV1RawHashes]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putSchemaGoogleCloudWebriskV1RawIndices: Conversion[Schema.GoogleCloudWebriskV1RawIndices, Option[Schema.GoogleCloudWebriskV1RawIndices]] = (fun: Schema.GoogleCloudWebriskV1RawIndices) => Option(fun)
		given putSchemaGoogleCloudWebriskV1SearchUrisResponseThreatUri: Conversion[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri, Option[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri]] = (fun: Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaGoogleCloudWebriskV1SearchHashesResponseThreatHash: Conversion[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash], Option[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash]]] = (fun: List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash]) => Option(fun)
		given putSchemaGoogleCloudWebriskV1ThreatEntryAdditions: Conversion[Schema.GoogleCloudWebriskV1ThreatEntryAdditions, Option[Schema.GoogleCloudWebriskV1ThreatEntryAdditions]] = (fun: Schema.GoogleCloudWebriskV1ThreatEntryAdditions) => Option(fun)
		given putSchemaGoogleCloudWebriskV1ComputeThreatListDiffResponseResponseTypeEnum: Conversion[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse.ResponseTypeEnum, Option[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse.ResponseTypeEnum]] = (fun: Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse.ResponseTypeEnum) => Option(fun)
		given putSchemaGoogleCloudWebriskV1ThreatEntryRemovals: Conversion[Schema.GoogleCloudWebriskV1ThreatEntryRemovals, Option[Schema.GoogleCloudWebriskV1ThreatEntryRemovals]] = (fun: Schema.GoogleCloudWebriskV1ThreatEntryRemovals) => Option(fun)
		given putSchemaGoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum: Conversion[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum, Option[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum]] = (fun: Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum) => Option(fun)
		given putListSchemaGoogleCloudWebriskV1SearchUrisResponseThreatUriThreatTypesEnum: Conversion[List[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri.ThreatTypesEnum], Option[List[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri.ThreatTypesEnum]]] = (fun: List[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri.ThreatTypesEnum]) => Option(fun)
		given putListSchemaGoogleCloudWebriskV1SearchHashesResponseThreatHashThreatTypesEnum: Conversion[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash.ThreatTypesEnum], Option[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash.ThreatTypesEnum]]] = (fun: List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash.ThreatTypesEnum]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
