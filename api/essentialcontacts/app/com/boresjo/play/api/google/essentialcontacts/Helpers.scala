package com.boresjo.play.api.google.essentialcontacts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleCloudEssentialcontactsV1SendTestMessageRequestNotificationCategoryEnum: Conversion[Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest.NotificationCategoryEnum, Option[Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest.NotificationCategoryEnum]] = (fun: Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest.NotificationCategoryEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudEssentialcontactsV1Contact: Conversion[List[Schema.GoogleCloudEssentialcontactsV1Contact], Option[List[Schema.GoogleCloudEssentialcontactsV1Contact]]] = (fun: List[Schema.GoogleCloudEssentialcontactsV1Contact]) => Option(fun)
		given putListSchemaGoogleCloudEssentialcontactsV1ContactNotificationCategorySubscriptionsEnum: Conversion[List[Schema.GoogleCloudEssentialcontactsV1Contact.NotificationCategorySubscriptionsEnum], Option[List[Schema.GoogleCloudEssentialcontactsV1Contact.NotificationCategorySubscriptionsEnum]]] = (fun: List[Schema.GoogleCloudEssentialcontactsV1Contact.NotificationCategorySubscriptionsEnum]) => Option(fun)
		given putSchemaGoogleCloudEssentialcontactsV1ContactValidationStateEnum: Conversion[Schema.GoogleCloudEssentialcontactsV1Contact.ValidationStateEnum, Option[Schema.GoogleCloudEssentialcontactsV1Contact.ValidationStateEnum]] = (fun: Schema.GoogleCloudEssentialcontactsV1Contact.ValidationStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
