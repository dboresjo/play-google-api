package com.boresjo.play.api.google.pagespeedonline

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtPagespeedApiPagespeedResponseV5: Format[Schema.PagespeedApiPagespeedResponseV5] = Json.format[Schema.PagespeedApiPagespeedResponseV5]
	given fmtPagespeedApiLoadingExperienceV5: Format[Schema.PagespeedApiLoadingExperienceV5] = Json.format[Schema.PagespeedApiLoadingExperienceV5]
	given fmtLighthouseResultV5: Format[Schema.LighthouseResultV5] = Json.format[Schema.LighthouseResultV5]
	given fmtPagespeedVersion: Format[Schema.PagespeedVersion] = Json.format[Schema.PagespeedVersion]
	given fmtUserPageLoadMetricV5: Format[Schema.UserPageLoadMetricV5] = Json.format[Schema.UserPageLoadMetricV5]
	given fmtBucket: Format[Schema.Bucket] = Json.format[Schema.Bucket]
	given fmtI18n: Format[Schema.I18n] = Json.format[Schema.I18n]
	given fmtLighthouseAuditResultV5: Format[Schema.LighthouseAuditResultV5] = Json.format[Schema.LighthouseAuditResultV5]
	given fmtCategoryGroupV5: Format[Schema.CategoryGroupV5] = Json.format[Schema.CategoryGroupV5]
	given fmtStackPack: Format[Schema.StackPack] = Json.format[Schema.StackPack]
	given fmtEnvironment: Format[Schema.Environment] = Json.format[Schema.Environment]
	given fmtRuntimeError: Format[Schema.RuntimeError] = Json.format[Schema.RuntimeError]
	given fmtCategories: Format[Schema.Categories] = Json.format[Schema.Categories]
	given fmtTiming: Format[Schema.Timing] = Json.format[Schema.Timing]
	given fmtConfigSettings: Format[Schema.ConfigSettings] = Json.format[Schema.ConfigSettings]
	given fmtLhrEntity: Format[Schema.LhrEntity] = Json.format[Schema.LhrEntity]
	given fmtRendererFormattedStrings: Format[Schema.RendererFormattedStrings] = Json.format[Schema.RendererFormattedStrings]
	given fmtMetricSavings: Format[Schema.MetricSavings] = Json.format[Schema.MetricSavings]
	given fmtLighthouseCategoryV5: Format[Schema.LighthouseCategoryV5] = Json.format[Schema.LighthouseCategoryV5]
	given fmtAuditRefs: Format[Schema.AuditRefs] = Json.format[Schema.AuditRefs]
}
