package com.boresjo.play.api.google.apim

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaGclbObservationSource: Conversion[Schema.GclbObservationSource, Option[Schema.GclbObservationSource]] = (fun: Schema.GclbObservationSource) => Option(fun)
		given putSchemaObservationSourceStateEnum: Conversion[Schema.ObservationSource.StateEnum, Option[Schema.ObservationSource.StateEnum]] = (fun: Schema.ObservationSource.StateEnum) => Option(fun)
		given putListSchemaGclbObservationSourcePscNetworkConfig: Conversion[List[Schema.GclbObservationSourcePscNetworkConfig], Option[List[Schema.GclbObservationSourcePscNetworkConfig]]] = (fun: List[Schema.GclbObservationSourcePscNetworkConfig]) => Option(fun)
		given putListSchemaObservationSource: Conversion[List[Schema.ObservationSource], Option[List[Schema.ObservationSource]]] = (fun: List[Schema.ObservationSource]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaObservationJobStateEnum: Conversion[Schema.ObservationJob.StateEnum, Option[Schema.ObservationJob.StateEnum]] = (fun: Schema.ObservationJob.StateEnum) => Option(fun)
		given putListSchemaObservationJob: Conversion[List[Schema.ObservationJob], Option[List[Schema.ObservationJob]]] = (fun: List[Schema.ObservationJob]) => Option(fun)
		given putSchemaApiObservationStyleEnum: Conversion[Schema.ApiObservation.StyleEnum, Option[Schema.ApiObservation.StyleEnum]] = (fun: Schema.ApiObservation.StyleEnum) => Option(fun)
		given putListSchemaApiObservation: Conversion[List[Schema.ApiObservation], Option[List[Schema.ApiObservation]]] = (fun: List[Schema.ApiObservation]) => Option(fun)
		given putSchemaHttpOperation: Conversion[Schema.HttpOperation, Option[Schema.HttpOperation]] = (fun: Schema.HttpOperation) => Option(fun)
		given putListSchemaHttpOperationPathParam: Conversion[List[Schema.HttpOperationPathParam], Option[List[Schema.HttpOperationPathParam]]] = (fun: List[Schema.HttpOperationPathParam]) => Option(fun)
		given putMapStringSchemaHttpOperationQueryParam: Conversion[Map[String, Schema.HttpOperationQueryParam], Option[Map[String, Schema.HttpOperationQueryParam]]] = (fun: Map[String, Schema.HttpOperationQueryParam]) => Option(fun)
		given putSchemaHttpOperationMethodEnum: Conversion[Schema.HttpOperation.MethodEnum, Option[Schema.HttpOperation.MethodEnum]] = (fun: Schema.HttpOperation.MethodEnum) => Option(fun)
		given putSchemaHttpOperationHttpRequest: Conversion[Schema.HttpOperationHttpRequest, Option[Schema.HttpOperationHttpRequest]] = (fun: Schema.HttpOperationHttpRequest) => Option(fun)
		given putSchemaHttpOperationHttpResponse: Conversion[Schema.HttpOperationHttpResponse, Option[Schema.HttpOperationHttpResponse]] = (fun: Schema.HttpOperationHttpResponse) => Option(fun)
		given putSchemaHttpOperationPathParamDataTypeEnum: Conversion[Schema.HttpOperationPathParam.DataTypeEnum, Option[Schema.HttpOperationPathParam.DataTypeEnum]] = (fun: Schema.HttpOperationPathParam.DataTypeEnum) => Option(fun)
		given putSchemaHttpOperationQueryParamDataTypeEnum: Conversion[Schema.HttpOperationQueryParam.DataTypeEnum, Option[Schema.HttpOperationQueryParam.DataTypeEnum]] = (fun: Schema.HttpOperationQueryParam.DataTypeEnum) => Option(fun)
		given putMapStringSchemaHttpOperationHeader: Conversion[Map[String, Schema.HttpOperationHeader], Option[Map[String, Schema.HttpOperationHeader]]] = (fun: Map[String, Schema.HttpOperationHeader]) => Option(fun)
		given putSchemaHttpOperationHeaderDataTypeEnum: Conversion[Schema.HttpOperationHeader.DataTypeEnum, Option[Schema.HttpOperationHeader.DataTypeEnum]] = (fun: Schema.HttpOperationHeader.DataTypeEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaApiOperation: Conversion[List[Schema.ApiOperation], Option[List[Schema.ApiOperation]]] = (fun: List[Schema.ApiOperation]) => Option(fun)
		given putListSchemaEditTagsApiObservationsRequest: Conversion[List[Schema.EditTagsApiObservationsRequest], Option[List[Schema.EditTagsApiObservationsRequest]]] = (fun: List[Schema.EditTagsApiObservationsRequest]) => Option(fun)
		given putListSchemaTagAction: Conversion[List[Schema.TagAction], Option[List[Schema.TagAction]]] = (fun: List[Schema.TagAction]) => Option(fun)
		given putSchemaTagActionActionEnum: Conversion[Schema.TagAction.ActionEnum, Option[Schema.TagAction.ActionEnum]] = (fun: Schema.TagAction.ActionEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
