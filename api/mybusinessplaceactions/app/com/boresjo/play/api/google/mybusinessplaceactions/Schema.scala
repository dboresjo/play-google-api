package com.boresjo.play.api.google.mybusinessplaceactions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListPlaceActionTypeMetadataResponse(
	  /** A collection of metadata for the available place action types. */
		placeActionTypeMetadata: Option[List[Schema.PlaceActionTypeMetadata]] = None,
	  /** If the number of action types exceeded the requested page size, this field will be populated with a token to fetch the next page on a subsequent call to `placeActionTypeMetadata.list`. If there are no more results, this field will not be present in the response. */
		nextPageToken: Option[String] = None
	)
	
	object PlaceActionTypeMetadata {
		enum PlaceActionTypeEnum extends Enum[PlaceActionTypeEnum] { case PLACE_ACTION_TYPE_UNSPECIFIED, APPOINTMENT, ONLINE_APPOINTMENT, DINING_RESERVATION, FOOD_ORDERING, FOOD_DELIVERY, FOOD_TAKEOUT, SHOP_ONLINE }
	}
	case class PlaceActionTypeMetadata(
	  /** The place action type. */
		placeActionType: Option[Schema.PlaceActionTypeMetadata.PlaceActionTypeEnum] = None,
	  /** The localized display name for the attribute, if available; otherwise, the English display name. */
		displayName: Option[String] = None
	)
	
	case class ListPlaceActionLinksResponse(
	  /** The returned list of place action links. */
		placeActionLinks: Option[List[Schema.PlaceActionLink]] = None,
	  /** If there are more place action links than the requested page size, then this field is populated with a token to fetch the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object PlaceActionLink {
		enum ProviderTypeEnum extends Enum[ProviderTypeEnum] { case PROVIDER_TYPE_UNSPECIFIED, MERCHANT, AGGREGATOR_3P }
		enum PlaceActionTypeEnum extends Enum[PlaceActionTypeEnum] { case PLACE_ACTION_TYPE_UNSPECIFIED, APPOINTMENT, ONLINE_APPOINTMENT, DINING_RESERVATION, FOOD_ORDERING, FOOD_DELIVERY, FOOD_TAKEOUT, SHOP_ONLINE }
	}
	case class PlaceActionLink(
	  /** Optional. The resource name, in the format `locations/{location_id}/placeActionLinks/{place_action_link_id}`. The name field will only be considered in UpdatePlaceActionLink and DeletePlaceActionLink requests for updating and deleting links respectively. However, it will be ignored in CreatePlaceActionLink request, where `place_action_link_id` will be assigned by the server on successful creation of a new link and returned as part of the response. */
		name: Option[String] = None,
	  /** Output only. Specifies the provider type. */
		providerType: Option[Schema.PlaceActionLink.ProviderTypeEnum] = None,
	  /** Output only. Indicates whether this link can be edited by the client. */
		isEditable: Option[Boolean] = None,
	  /** Required. The link uri. The same uri can be reused for different action types across different locations. However, only one place action link is allowed for each unique combination of (uri, place action type, location). */
		uri: Option[String] = None,
	  /** Required. The type of place action that can be performed using this link. */
		placeActionType: Option[Schema.PlaceActionLink.PlaceActionTypeEnum] = None,
	  /** Optional. Whether this link is preferred by the merchant. Only one link can be marked as preferred per place action type at a location. If a future request marks a different link as preferred for the same place action type, then the current preferred link (if any exists) will lose its preference. */
		isPreferred: Option[Boolean] = None,
	  /** Output only. The time when the place action link was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the place action link was last modified. */
		updateTime: Option[String] = None
	)
	
	case class Empty(
	
	)
}
