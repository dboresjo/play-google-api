package com.boresjo.play.api.google.books

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
		given putListSchemaBookshelf: Conversion[List[Schema.Bookshelf], Option[List[Schema.Bookshelf]]] = (fun: List[Schema.Bookshelf]) => Option(fun)
		given putListSchemaVolume: Conversion[List[Schema.Volume], Option[List[Schema.Volume]]] = (fun: List[Schema.Volume]) => Option(fun)
		given putSchemaVolumeLayerInfoItem: Conversion[Schema.Volume.LayerInfoItem, Option[Schema.Volume.LayerInfoItem]] = (fun: Schema.Volume.LayerInfoItem) => Option(fun)
		given putSchemaVolumeVolumeInfoItem: Conversion[Schema.Volume.VolumeInfoItem, Option[Schema.Volume.VolumeInfoItem]] = (fun: Schema.Volume.VolumeInfoItem) => Option(fun)
		given putSchemaVolumeUserInfoItem: Conversion[Schema.Volume.UserInfoItem, Option[Schema.Volume.UserInfoItem]] = (fun: Schema.Volume.UserInfoItem) => Option(fun)
		given putSchemaVolumeSaleInfoItem: Conversion[Schema.Volume.SaleInfoItem, Option[Schema.Volume.SaleInfoItem]] = (fun: Schema.Volume.SaleInfoItem) => Option(fun)
		given putSchemaVolumeAccessInfoItem: Conversion[Schema.Volume.AccessInfoItem, Option[Schema.Volume.AccessInfoItem]] = (fun: Schema.Volume.AccessInfoItem) => Option(fun)
		given putSchemaVolumeSearchInfoItem: Conversion[Schema.Volume.SearchInfoItem, Option[Schema.Volume.SearchInfoItem]] = (fun: Schema.Volume.SearchInfoItem) => Option(fun)
		given putSchemaVolumeRecommendedInfoItem: Conversion[Schema.Volume.RecommendedInfoItem, Option[Schema.Volume.RecommendedInfoItem]] = (fun: Schema.Volume.RecommendedInfoItem) => Option(fun)
		given putListSchemaVolumeseriesinfoVolumeSeriesItem: Conversion[List[Schema.Volumeseriesinfo.VolumeSeriesItem], Option[List[Schema.Volumeseriesinfo.VolumeSeriesItem]]] = (fun: List[Schema.Volumeseriesinfo.VolumeSeriesItem]) => Option(fun)
		given putSchemaReviewAuthorItem: Conversion[Schema.Review.AuthorItem, Option[Schema.Review.AuthorItem]] = (fun: Schema.Review.AuthorItem) => Option(fun)
		given putSchemaReviewSourceItem: Conversion[Schema.Review.SourceItem, Option[Schema.Review.SourceItem]] = (fun: Schema.Review.SourceItem) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaMetadataItemsItem: Conversion[List[Schema.Metadata.ItemsItem], Option[List[Schema.Metadata.ItemsItem]]] = (fun: List[Schema.Metadata.ItemsItem]) => Option(fun)
		given putSchemaFamilyInfoMembershipItem: Conversion[Schema.FamilyInfo.MembershipItem, Option[Schema.FamilyInfo.MembershipItem]] = (fun: Schema.FamilyInfo.MembershipItem) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaLayersummary: Conversion[List[Schema.Layersummary], Option[List[Schema.Layersummary]]] = (fun: List[Schema.Layersummary]) => Option(fun)
		given putSchemaDictlayerdata: Conversion[Schema.Dictlayerdata, Option[Schema.Dictlayerdata]] = (fun: Schema.Dictlayerdata) => Option(fun)
		given putSchemaDictlayerdataDictItem: Conversion[Schema.Dictlayerdata.DictItem, Option[Schema.Dictlayerdata.DictItem]] = (fun: Schema.Dictlayerdata.DictItem) => Option(fun)
		given putSchemaDictlayerdataCommonItem: Conversion[Schema.Dictlayerdata.CommonItem, Option[Schema.Dictlayerdata.CommonItem]] = (fun: Schema.Dictlayerdata.CommonItem) => Option(fun)
		given putListSchemaGeoAnnotationdata: Conversion[List[Schema.GeoAnnotationdata], Option[List[Schema.GeoAnnotationdata]]] = (fun: List[Schema.GeoAnnotationdata]) => Option(fun)
		given putSchemaGeolayerdata: Conversion[Schema.Geolayerdata, Option[Schema.Geolayerdata]] = (fun: Schema.Geolayerdata) => Option(fun)
		given putSchemaGeolayerdataGeoItem: Conversion[Schema.Geolayerdata.GeoItem, Option[Schema.Geolayerdata.GeoItem]] = (fun: Schema.Geolayerdata.GeoItem) => Option(fun)
		given putSchemaGeolayerdataCommonItem: Conversion[Schema.Geolayerdata.CommonItem, Option[Schema.Geolayerdata.CommonItem]] = (fun: Schema.Geolayerdata.CommonItem) => Option(fun)
		given putSchemaVolumeannotationContentRangesItem: Conversion[Schema.Volumeannotation.ContentRangesItem, Option[Schema.Volumeannotation.ContentRangesItem]] = (fun: Schema.Volumeannotation.ContentRangesItem) => Option(fun)
		given putListSchemaVolumeannotation: Conversion[List[Schema.Volumeannotation], Option[List[Schema.Volumeannotation]]] = (fun: List[Schema.Volumeannotation]) => Option(fun)
		given putSchemaUsersettingsNotificationItem: Conversion[Schema.Usersettings.NotificationItem, Option[Schema.Usersettings.NotificationItem]] = (fun: Schema.Usersettings.NotificationItem) => Option(fun)
		given putSchemaUsersettingsNotesExportItem: Conversion[Schema.Usersettings.NotesExportItem, Option[Schema.Usersettings.NotesExportItem]] = (fun: Schema.Usersettings.NotesExportItem) => Option(fun)
		given putListSchemaDownloadAccessRestriction: Conversion[List[Schema.DownloadAccessRestriction], Option[List[Schema.DownloadAccessRestriction]]] = (fun: List[Schema.DownloadAccessRestriction]) => Option(fun)
		given putSchemaConcurrentAccessRestriction: Conversion[Schema.ConcurrentAccessRestriction, Option[Schema.ConcurrentAccessRestriction]] = (fun: Schema.ConcurrentAccessRestriction) => Option(fun)
		given putSchemaDownloadAccessRestriction: Conversion[Schema.DownloadAccessRestriction, Option[Schema.DownloadAccessRestriction]] = (fun: Schema.DownloadAccessRestriction) => Option(fun)
		given putSchemaAnnotationCurrentVersionRangesItem: Conversion[Schema.Annotation.CurrentVersionRangesItem, Option[Schema.Annotation.CurrentVersionRangesItem]] = (fun: Schema.Annotation.CurrentVersionRangesItem) => Option(fun)
		given putSchemaAnnotationLayerSummaryItem: Conversion[Schema.Annotation.LayerSummaryItem, Option[Schema.Annotation.LayerSummaryItem]] = (fun: Schema.Annotation.LayerSummaryItem) => Option(fun)
		given putSchemaAnnotationClientVersionRangesItem: Conversion[Schema.Annotation.ClientVersionRangesItem, Option[Schema.Annotation.ClientVersionRangesItem]] = (fun: Schema.Annotation.ClientVersionRangesItem) => Option(fun)
		given putListSchemaAnnotation: Conversion[List[Schema.Annotation], Option[List[Schema.Annotation]]] = (fun: List[Schema.Annotation]) => Option(fun)
		given putListSchemaAnnotationsSummaryLayersItem: Conversion[List[Schema.AnnotationsSummary.LayersItem], Option[List[Schema.AnnotationsSummary.LayersItem]]] = (fun: List[Schema.AnnotationsSummary.LayersItem]) => Option(fun)
		given putListSchemaCategoryItemsItem: Conversion[List[Schema.Category.ItemsItem], Option[List[Schema.Category.ItemsItem]]] = (fun: List[Schema.Category.ItemsItem]) => Option(fun)
		given putListSchemaDiscoveryclustersClustersItem: Conversion[List[Schema.Discoveryclusters.ClustersItem], Option[List[Schema.Discoveryclusters.ClustersItem]]] = (fun: List[Schema.Discoveryclusters.ClustersItem]) => Option(fun)
		given putListSchemaOffersItemsItem: Conversion[List[Schema.Offers.ItemsItem], Option[List[Schema.Offers.ItemsItem]]] = (fun: List[Schema.Offers.ItemsItem]) => Option(fun)
		given putListSchemaSeriesSeriesItem: Conversion[List[Schema.Series.SeriesItem], Option[List[Schema.Series.SeriesItem]]] = (fun: List[Schema.Series.SeriesItem]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
